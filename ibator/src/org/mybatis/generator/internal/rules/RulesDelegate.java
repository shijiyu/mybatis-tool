package org.mybatis.generator.internal.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class RulesDelegate
  implements Rules
{
  protected Rules rules;

  public RulesDelegate(Rules rules)
  {
    this.rules = rules;
  }

  public FullyQualifiedJavaType calculateAllFieldsClass() {
    return this.rules.calculateAllFieldsClass();
  }

  public boolean generateBaseRecordClass() {
    return this.rules.generateBaseRecordClass();
  }

  public boolean generateBaseResultMap() {
    return this.rules.generateBaseResultMap();
  }

  public boolean generateCountByExample() {
    return this.rules.generateCountByExample();
  }

  public boolean generateDeleteByExample() {
    return this.rules.generateDeleteByExample();
  }

  public boolean generateDeleteByPrimaryKey() {
    return this.rules.generateDeleteByPrimaryKey();
  }

  public boolean generateExampleClass() {
    return this.rules.generateExampleClass();
  }

  public boolean generateInsert() {
    return this.rules.generateInsert();
  }

  public boolean generateInsertSelective() {
    return this.rules.generateInsertSelective();
  }

  public boolean generatePrimaryKeyClass() {
    return this.rules.generatePrimaryKeyClass();
  }

  public boolean generateRecordWithBLOBsClass() {
    return this.rules.generateRecordWithBLOBsClass();
  }

  public boolean generateResultMapWithBLOBs() {
    return this.rules.generateResultMapWithBLOBs();
  }

  public boolean generateSelectByExampleWithBLOBs() {
    return this.rules.generateSelectByExampleWithBLOBs();
  }

  public boolean generateSelectByExampleWithoutBLOBs() {
    return this.rules.generateSelectByExampleWithoutBLOBs();
  }

  public boolean generateSelectByPrimaryKey() {
    return this.rules.generateSelectByPrimaryKey();
  }

  public boolean generateSQLExampleWhereClause() {
    return this.rules.generateSQLExampleWhereClause();
  }

  public boolean generateMyBatis3UpdateByExampleWhereClause() {
    return this.rules.generateMyBatis3UpdateByExampleWhereClause();
  }

  public boolean generateUpdateByExampleSelective() {
    return this.rules.generateUpdateByExampleSelective();
  }

  public boolean generateUpdateByExampleWithBLOBs() {
    return this.rules.generateUpdateByExampleWithBLOBs();
  }

  public boolean generateUpdateByExampleWithoutBLOBs() {
    return this.rules.generateUpdateByExampleWithoutBLOBs();
  }

  public boolean generateUpdateByPrimaryKeySelective() {
    return this.rules.generateUpdateByPrimaryKeySelective();
  }

  public boolean generateUpdateByPrimaryKeyWithBLOBs() {
    return this.rules.generateUpdateByPrimaryKeyWithBLOBs();
  }

  public boolean generateUpdateByPrimaryKeyWithoutBLOBs() {
    return this.rules.generateUpdateByPrimaryKeyWithoutBLOBs();
  }

  public IntrospectedTable getIntrospectedTable() {
    return this.rules.getIntrospectedTable();
  }

  public boolean generateBaseColumnList() {
    return this.rules.generateBaseColumnList();
  }

  public boolean generateBlobColumnList() {
    return this.rules.generateBlobColumnList();
  }
}