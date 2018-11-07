package org.mybatis.generator.internal.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.TableConfiguration;

public abstract class BaseRules
  implements Rules
{
  protected TableConfiguration tableConfiguration;
  protected IntrospectedTable introspectedTable;

  public BaseRules(IntrospectedTable introspectedTable)
  {
    this.introspectedTable = introspectedTable;
    this.tableConfiguration = introspectedTable.getTableConfiguration();
  }

  public boolean generateInsert()
  {
    return this.tableConfiguration.isInsertStatementEnabled();
  }

  public boolean generateInsertSelective()
  {
    return this.tableConfiguration.isInsertStatementEnabled();
  }

  public FullyQualifiedJavaType calculateAllFieldsClass()
  {
    String answer;
    if (generateRecordWithBLOBsClass()) {
      answer = this.introspectedTable.getRecordWithBLOBsType();
    }
    else
    {
      if (generateBaseRecordClass())
        answer = this.introspectedTable.getBaseRecordType();
      else {
        answer = this.introspectedTable.getPrimaryKeyType();
      }
    }
    return new FullyQualifiedJavaType(answer);
  }

  public boolean generateUpdateByPrimaryKeyWithoutBLOBs()
  {
    boolean rc = (this.tableConfiguration.isUpdateByPrimaryKeyStatementEnabled()) && 
      (this.introspectedTable.hasPrimaryKeyColumns()) && 
      (this.introspectedTable.hasBaseColumns());

    return rc;
  }

  public boolean generateUpdateByPrimaryKeyWithBLOBs()
  {
    boolean rc = (this.tableConfiguration.isUpdateByPrimaryKeyStatementEnabled()) && 
      (this.introspectedTable.hasPrimaryKeyColumns()) && 
      (this.introspectedTable.hasBLOBColumns());

    return rc;
  }

  public boolean generateUpdateByPrimaryKeySelective()
  {
    boolean rc = (this.tableConfiguration.isUpdateByPrimaryKeyStatementEnabled()) && 
      (this.introspectedTable.hasPrimaryKeyColumns()) && (
      (this.introspectedTable.hasBLOBColumns()) || 
      (this.introspectedTable.hasBaseColumns()));

    return rc;
  }

  public boolean generateDeleteByPrimaryKey()
  {
    boolean rc = (this.tableConfiguration.isDeleteByPrimaryKeyStatementEnabled()) && 
      (this.introspectedTable.hasPrimaryKeyColumns());

    return rc;
  }

  public boolean generateDeleteByExample()
  {
    boolean rc = this.tableConfiguration.isDeleteByExampleStatementEnabled();

    return rc;
  }

  public boolean generateBaseResultMap()
  {
    boolean rc = (this.tableConfiguration.isSelectByExampleStatementEnabled()) || 
      (this.tableConfiguration.isSelectByPrimaryKeyStatementEnabled());

    return rc;
  }

  public boolean generateResultMapWithBLOBs()
  {
    boolean rc = ((this.tableConfiguration.isSelectByExampleStatementEnabled()) || 
      (this.tableConfiguration.isSelectByPrimaryKeyStatementEnabled())) && 
      (this.introspectedTable.hasBLOBColumns());

    return rc;
  }

  public boolean generateSQLExampleWhereClause()
  {
    boolean rc = (this.tableConfiguration.isSelectByExampleStatementEnabled()) || 
      (this.tableConfiguration.isDeleteByExampleStatementEnabled()) || 
      (this.tableConfiguration.isCountByExampleStatementEnabled());

    if (this.introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.IBATIS2) {
      rc |= this.tableConfiguration.isUpdateByExampleStatementEnabled();
    }

    return rc;
  }

  public boolean generateMyBatis3UpdateByExampleWhereClause()
  {
    return (this.introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) && 
      (this.tableConfiguration.isUpdateByExampleStatementEnabled());
  }

  public boolean generateSelectByPrimaryKey()
  {
    boolean rc = (this.tableConfiguration.isSelectByPrimaryKeyStatementEnabled()) && 
      (this.introspectedTable.hasPrimaryKeyColumns()) && (
      (this.introspectedTable.hasBaseColumns()) || 
      (this.introspectedTable.hasBLOBColumns()));

    return rc;
  }

  public boolean generateSelectByExampleWithoutBLOBs()
  {
    return this.tableConfiguration.isSelectByExampleStatementEnabled();
  }

  public boolean generateSelectByExampleWithBLOBs()
  {
    boolean rc = (this.tableConfiguration.isSelectByExampleStatementEnabled()) && 
      (this.introspectedTable.hasBLOBColumns());

    return rc;
  }

  public boolean generateExampleClass()
  {
    boolean rc = (this.tableConfiguration.isSelectByExampleStatementEnabled()) || 
      (this.tableConfiguration.isDeleteByExampleStatementEnabled()) || 
      (this.tableConfiguration.isCountByExampleStatementEnabled()) || 
      (this.tableConfiguration.isUpdateByExampleStatementEnabled());

    return rc;
  }

  public boolean generateCountByExample() {
    boolean rc = this.tableConfiguration.isCountByExampleStatementEnabled();

    return rc;
  }

  public boolean generateUpdateByExampleSelective() {
    boolean rc = this.tableConfiguration.isUpdateByExampleStatementEnabled();

    return rc;
  }

  public boolean generateUpdateByExampleWithoutBLOBs() {
    boolean rc = (this.tableConfiguration.isUpdateByExampleStatementEnabled()) && (
      (this.introspectedTable.hasPrimaryKeyColumns()) || 
      (this.introspectedTable.hasBaseColumns()));

    return rc;
  }

  public boolean generateUpdateByExampleWithBLOBs() {
    boolean rc = (this.tableConfiguration.isUpdateByExampleStatementEnabled()) && 
      (this.introspectedTable.hasBLOBColumns());

    return rc;
  }

  public IntrospectedTable getIntrospectedTable() {
    return this.introspectedTable;
  }

  public boolean generateBaseColumnList()
  {
    return (generateSelectByPrimaryKey()) || 
      (generateSelectByExampleWithoutBLOBs());
  }

  public boolean generateBlobColumnList()
  {
    return (this.introspectedTable.hasBLOBColumns()) && (
      (this.tableConfiguration.isSelectByExampleStatementEnabled()) || 
      (this.tableConfiguration.isSelectByPrimaryKeyStatementEnabled()));
  }
}