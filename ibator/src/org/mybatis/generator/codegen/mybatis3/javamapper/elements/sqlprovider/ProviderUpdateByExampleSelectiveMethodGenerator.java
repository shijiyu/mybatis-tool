package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

public class ProviderUpdateByExampleSelectiveMethodGenerator extends AbstractJavaProviderMethodGenerator
{
  public void addClassElements(TopLevelClass topLevelClass)
  {
    Set<String> staticImports = new TreeSet<>();
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.UPDATE");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SET");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL");

    importedTypes.add(new FullyQualifiedJavaType("java.util.Map"));

    Method method = new Method(this.introspectedTable.getUpdateByExampleSelectiveStatementId());
    method.setReturnType(FullyQualifiedJavaType.getStringInstance());
    method.setVisibility(JavaVisibility.PUBLIC);
    method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), 
      "parameter"));

    FullyQualifiedJavaType record = 
      this.introspectedTable.getRules().calculateAllFieldsClass();
    importedTypes.add(record);
    method.addBodyLine(
      String.format("%s record = (%s) parameter.get(\"record\");", new Object[] { 
      record.getShortName(), record.getShortName() }));

    FullyQualifiedJavaType example = 
      new FullyQualifiedJavaType(this.introspectedTable.getExampleType());
    importedTypes.add(example);
    method.addBodyLine(
      String.format("%s example = (%s) parameter.get(\"example\");", new Object[] { 
      example.getShortName(), example.getShortName() }));

    this.context.getCommentGenerator().addGeneralMethodComment(method, 
      this.introspectedTable);

    method.addBodyLine("");
    method.addBodyLine("BEGIN();");

    method.addBodyLine(
      String.format("UPDATE(\"%s\");", new Object[] { 
      StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()) }));
    method.addBodyLine("");

    for (IntrospectedColumn introspectedColumn : this.introspectedTable.getAllColumns()) {
      if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
        method.addBodyLine(
          String.format("if (record.%s() != null) {", new Object[] { 
          JavaBeansUtil.getGetterMethodName(introspectedColumn.getJavaProperty(), 
          introspectedColumn.getFullyQualifiedJavaType()) }));
      }

      StringBuilder sb = new StringBuilder();
      sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      sb.insert(2, "record.");

      method.addBodyLine(
        String.format("SET(\"%s = %s\");", new Object[] { 
        StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)), 
        sb.toString() }));

      if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
        method.addBodyLine("}");
      }

      method.addBodyLine("");
    }

    method.addBodyLine("applyWhere(example, true);");
    method.addBodyLine("return SQL();");

    if (this.context.getPlugins().providerUpdateByExampleSelectiveMethodGenerated(method, topLevelClass, 
      this.introspectedTable)) {
      topLevelClass.addStaticImports(staticImports);
      topLevelClass.addImportedTypes(importedTypes);
      topLevelClass.addMethod(method);
    }
  }
}