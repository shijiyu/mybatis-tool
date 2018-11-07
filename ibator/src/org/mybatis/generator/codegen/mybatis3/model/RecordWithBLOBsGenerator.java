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
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.internal.util.messages.Messages;

public class RecordWithBLOBsGenerator extends AbstractJavaGenerator
{
  public List<CompilationUnit> getCompilationUnits()
  {
    FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
    this.progressCallback.startTask(Messages.getString(
      "Progress.9", table.toString()));
    Plugin plugins = this.context.getPlugins();
    CommentGenerator commentGenerator = this.context.getCommentGenerator();

    TopLevelClass topLevelClass = new TopLevelClass(
      this.introspectedTable.getRecordWithBLOBsType());
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(topLevelClass);

    String rootClass = getRootClass();
    if (this.introspectedTable.getRules().generateBaseRecordClass())
      topLevelClass.setSuperClass(this.introspectedTable.getBaseRecordType());
    else {
      topLevelClass.setSuperClass(this.introspectedTable.getPrimaryKeyType());
    }

    if (this.introspectedTable.isConstructorBased()) {
      addParameterizedConstructor(topLevelClass);

      if (!this.introspectedTable.isImmutable()) {
        addDefaultConstructor(topLevelClass);
      }

    }

    Iterator<?> localIterator = this.introspectedTable
      .getBLOBColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();

      if (!RootClassInfo.getInstance(rootClass, this.warnings)
        .containsProperty(introspectedColumn))
      {
        Field field = getJavaBeansField(introspectedColumn);
        if (plugins.modelFieldGenerated(field, topLevelClass, 
          introspectedColumn, this.introspectedTable, 
          Plugin.ModelClassType.RECORD_WITH_BLOBS)) {
          topLevelClass.addField(field);
          topLevelClass.addImportedType(field.getType());
        }

        Method method = getJavaBeansGetter(introspectedColumn);
        if (plugins.modelGetterMethodGenerated(method, topLevelClass, 
          introspectedColumn, this.introspectedTable, 
          Plugin.ModelClassType.RECORD_WITH_BLOBS)) {
          topLevelClass.addMethod(method);
        }

        if (!this.introspectedTable.isImmutable()) {
          method = getJavaBeansSetter(introspectedColumn);
          if (plugins.modelSetterMethodGenerated(method, topLevelClass, 
            introspectedColumn, this.introspectedTable, 
            Plugin.ModelClassType.RECORD_WITH_BLOBS)) {
            topLevelClass.addMethod(method);
          }
        }
      }
    }
    List<CompilationUnit> answer = new ArrayList<>();
    if (this.context.getPlugins().modelRecordWithBLOBsClassGenerated(
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

    Iterator<?> localIterator1 = this.introspectedTable
      .getAllColumns().iterator();

    while (localIterator1.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator1.next();
      method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), 
        introspectedColumn.getJavaProperty()));
    }

    boolean comma = false;
    StringBuilder sb = new StringBuilder();
    sb.append("super(");

    Iterator<?> localIterator2 = this.introspectedTable
      .getNonBLOBColumns().iterator();

    while (localIterator2.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator2.next();
      if (comma)
        sb.append(", ");
      else {
        comma = true;
      }
      sb.append(introspectedColumn.getJavaProperty());
    }
    sb.append(");");
    method.addBodyLine(sb.toString());

    localIterator2 = this.introspectedTable
      .getBLOBColumns().iterator();

    while (localIterator2.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator2.next();
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