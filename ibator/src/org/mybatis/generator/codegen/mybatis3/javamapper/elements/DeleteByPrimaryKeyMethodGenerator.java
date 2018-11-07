package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class DeleteByPrimaryKeyMethodGenerator extends AbstractJavaMapperMethodGenerator
{
  public void addInterfaceElements(Interface interfaze)
  {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName(this.introspectedTable.getDeleteByPrimaryKeyStatementId());

    if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
      FullyQualifiedJavaType type = new FullyQualifiedJavaType(
        this.introspectedTable.getPrimaryKeyType());
      importedTypes.add(type);
      method.addParameter(new Parameter(type, "key"));
    }
    else
    {
      List<IntrospectedColumn> introspectedColumns = this.introspectedTable
        .getPrimaryKeyColumns();
      boolean annotate = introspectedColumns.size() > 1;
      if (annotate) {
        importedTypes.add(new FullyQualifiedJavaType(
          "org.apache.ibatis.annotations.Param"));
      }
      StringBuilder sb = new StringBuilder();
      for (IntrospectedColumn introspectedColumn : introspectedColumns) {
        FullyQualifiedJavaType type = introspectedColumn
          .getFullyQualifiedJavaType();
        importedTypes.add(type);
        Parameter parameter = new Parameter(type, 
          introspectedColumn.getJavaProperty());
        if (annotate) {
          sb.setLength(0);
          sb.append("@Param(\"");
          sb.append(introspectedColumn.getJavaProperty());
          sb.append("\")");
          parameter.addAnnotation(sb.toString());
        }
        method.addParameter(parameter);
      }
    }

    this.context.getCommentGenerator().addGeneralMethodComment(method, 
      this.introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (this.context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(
      method, interfaze, this.introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method)
  {
  }
}