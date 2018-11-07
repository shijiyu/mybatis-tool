package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

public class ProviderSelectByExampleWithoutBLOBsMethodGenerator extends AbstractJavaProviderMethodGenerator
{
  public void addClassElements(TopLevelClass topLevelClass)
  {
    Set<String> staticImports = new TreeSet<>();
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.FROM");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY");
    staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL");

    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getExampleType());
    importedTypes.add(fqjt);

    Method method = new Method(getMethodName());
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getStringInstance());
    method.addParameter(new Parameter(fqjt, "example"));

    this.context.getCommentGenerator().addGeneralMethodComment(method, 
      this.introspectedTable);

    method.addBodyLine("BEGIN();");

    boolean distinctCheck = true;
    for (IntrospectedColumn introspectedColumn : getColumns()) {
      if (distinctCheck) {
        method.addBodyLine("if (example != null && example.isDistinct()) {");
        method.addBodyLine(
          String.format("SELECT_DISTINCT(\"%s\");", new Object[] { 
          StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn)) }));
        method.addBodyLine("} else {");
        method.addBodyLine(
          String.format("SELECT(\"%s\");", new Object[] { 
          StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn)) }));
        method.addBodyLine("}");
      } else {
        method.addBodyLine(
          String.format("SELECT(\"%s\");", new Object[] { 
          StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn)) }));
      }

      distinctCheck = false;
    }

    method.addBodyLine(
      String.format("FROM(\"%s\");", new Object[] { 
      StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()) }));
    method.addBodyLine("applyWhere(example, false);");

    method.addBodyLine("");
    method.addBodyLine("if (example != null && example.getOrderByClause() != null) {");
    method.addBodyLine("ORDER_BY(example.getOrderByClause());");
    method.addBodyLine("}");

    method.addBodyLine("");
    method.addBodyLine("return SQL();");

    if (callPlugins(method, topLevelClass)) {
      topLevelClass.addStaticImports(staticImports);
      topLevelClass.addImportedTypes(importedTypes);
      topLevelClass.addMethod(method);
    }
  }

  public List<IntrospectedColumn> getColumns() {
    return this.introspectedTable.getNonBLOBColumns();
  }

  public String getMethodName() {
    return this.introspectedTable.getSelectByExampleStatementId();
  }

  public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
    return this.context.getPlugins().providerSelectByExampleWithoutBLOBsMethodGenerated(method, topLevelClass, 
      this.introspectedTable);
  }
}