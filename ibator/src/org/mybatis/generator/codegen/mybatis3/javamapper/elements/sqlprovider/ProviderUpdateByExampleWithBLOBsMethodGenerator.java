package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderUpdateByExampleWithBLOBsMethodGenerator extends ProviderUpdateByExampleWithoutBLOBsMethodGenerator
{
  public String getMethodName()
  {
    return this.introspectedTable.getUpdateByExampleWithBLOBsStatementId();
  }

  public List<IntrospectedColumn> getColumns()
  {
    return this.introspectedTable.getAllColumns();
  }

  public boolean callPlugins(Method method, TopLevelClass topLevelClass)
  {
    return this.context.getPlugins().providerUpdateByExampleWithBLOBsMethodGenerated(method, topLevelClass, 
      this.introspectedTable);
  }
}