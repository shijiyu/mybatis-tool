package org.mybatis.generator.codegen.mybatis3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.internal.util.messages.Messages;

public class PrimaryKeyGenerator extends AbstractJavaGenerator
{
  public List<CompilationUnit> getCompilationUnits()
  {
    FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
    this.progressCallback.startTask(Messages.getString(
      "Progress.7", table.toString()));
    Plugin plugins = this.context.getPlugins();
    CommentGenerator commentGenerator = this.context.getCommentGenerator();

    TopLevelClass topLevelClass = new TopLevelClass(
      this.introspectedTable.getPrimaryKeyType());
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(topLevelClass);

    String rootClass = getRootClass();
    if (rootClass != null) {
      topLevelClass.setSuperClass(new FullyQualifiedJavaType(rootClass));
      topLevelClass.addImportedType(topLevelClass.getSuperClass());
    }

    if (this.introspectedTable.isConstructorBased()) {
      addParameterizedConstructor(topLevelClass);

      if (!this.introspectedTable.isImmutable()) {
        addDefaultConstructor(topLevelClass);
      }

    }

    Iterator<?> localIterator = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();

      if (!RootClassInfo.getInstance(rootClass, this.warnings)
        .containsProperty(introspectedColumn))
      {
        Field field = getJavaBeansField(introspectedColumn);
        if (plugins.modelFieldGenerated(field, topLevelClass, 
          introspectedColumn, this.introspectedTable, 
          Plugin.ModelClassType.PRIMARY_KEY)) {
          topLevelClass.addField(field);
          topLevelClass.addImportedType(field.getType());
        }

        Method method = getJavaBeansGetter(introspectedColumn);
        if (plugins.modelGetterMethodGenerated(method, topLevelClass, 
          introspectedColumn, this.introspectedTable, 
          Plugin.ModelClassType.PRIMARY_KEY)) {
          topLevelClass.addMethod(method);
        }

        if (!this.introspectedTable.isImmutable()) {
          method = getJavaBeansSetter(introspectedColumn);
          if (plugins.modelSetterMethodGenerated(method, topLevelClass, 
            introspectedColumn, this.introspectedTable, 
            Plugin.ModelClassType.PRIMARY_KEY)) {
            topLevelClass.addMethod(method);
          }
        }
      }
    }
    List<CompilationUnit> answer = new ArrayList<>();
    if (this.context.getPlugins().modelPrimaryKeyClassGenerated(
      topLevelClass, this.introspectedTable)) {
      answer.add(topLevelClass);
    }

    return answer;
  }

  private void addParameterizedConstructor(TopLevelClass topLevelClass) {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setConstructor(true);
    method.setName(topLevelClass.getType().getShortName());

    StringBuilder sb = new StringBuilder();

    Iterator<?> localIterator = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
      method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), 
        introspectedColumn.getJavaProperty()));
      sb.setLength(0);
      sb.append("this.");
      sb.append(introspectedColumn.getJavaProperty());
      sb.append(" = ");
      sb.append(introspectedColumn.getJavaProperty());
      sb.append(';');
      method.addBodyLine(sb.toString());
    }

    topLevelClass.addMethod(method);
  }
}