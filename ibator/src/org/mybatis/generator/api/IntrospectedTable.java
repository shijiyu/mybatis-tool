package org.mybatis.generator.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.rules.ConditionalModelRules;
import org.mybatis.generator.internal.rules.FlatModelRules;
import org.mybatis.generator.internal.rules.HierarchicalModelRules;
import org.mybatis.generator.internal.rules.Rules;
import org.mybatis.generator.internal.util.StringUtility;

import ibator.Globar;

public abstract class IntrospectedTable
{
  protected TableConfiguration tableConfiguration;
  protected FullyQualifiedTable fullyQualifiedTable;
  protected Context context;
  protected Rules rules;
  protected List<IntrospectedColumn> primaryKeyColumns;
  protected List<IntrospectedColumn> baseColumns;
  protected List<IntrospectedColumn> blobColumns;
  protected TargetRuntime targetRuntime;
  protected Map<String, Object> attributes;
  protected Map<InternalAttribute, String> internalAttributes;

  public IntrospectedTable(TargetRuntime targetRuntime)
  {
    this.targetRuntime = targetRuntime;
    this.primaryKeyColumns = new ArrayList<>();
    this.baseColumns = new ArrayList<>();
    this.blobColumns = new ArrayList<>();
    this.attributes = new HashMap<>();
    this.internalAttributes = new HashMap<>();
  }

  public FullyQualifiedTable getFullyQualifiedTable() {
    return this.fullyQualifiedTable;
  }

  public String getSelectByExampleQueryId() {
    return this.tableConfiguration.getSelectByExampleQueryId();
  }

  public String getSelectByPrimaryKeyQueryId() {
    return this.tableConfiguration.getSelectByPrimaryKeyQueryId();
  }

  public GeneratedKey getGeneratedKey() {
    return this.tableConfiguration.getGeneratedKey();
  }

  public IntrospectedColumn getColumn(String columnName) {
    if (columnName == null) {
      return null;
    }

    for (IntrospectedColumn introspectedColumn : this.primaryKeyColumns) {
      if (introspectedColumn.isColumnNameDelimited()) {
        if (introspectedColumn.getActualColumnName().equals(
          columnName)) {
          return introspectedColumn;
        }

      }
      else if (introspectedColumn.getActualColumnName()
        .equalsIgnoreCase(columnName)) {
        return introspectedColumn;
      }

    }

    for (IntrospectedColumn introspectedColumn : this.baseColumns) {
      if (introspectedColumn.isColumnNameDelimited()) {
        if (introspectedColumn.getActualColumnName().equals(
          columnName)) {
          return introspectedColumn;
        }

      }
      else if (introspectedColumn.getActualColumnName()
        .equalsIgnoreCase(columnName)) {
        return introspectedColumn;
      }

    }

    for (IntrospectedColumn introspectedColumn : this.blobColumns) {
      if (introspectedColumn.isColumnNameDelimited()) {
        if (introspectedColumn.getActualColumnName().equals(
          columnName)) {
          return introspectedColumn;
        }

      }
      else if (introspectedColumn.getActualColumnName()
        .equalsIgnoreCase(columnName)) {
        return introspectedColumn;
      }

    }

    return null;
  }

  public boolean hasJDBCDateColumns()
  {
    boolean rc = false;

    for (IntrospectedColumn introspectedColumn : this.primaryKeyColumns) {
      if (introspectedColumn.isJDBCDateColumn()) {
        rc = true;
        break;
      }
    }

    if (!rc) {
      for (IntrospectedColumn introspectedColumn : this.baseColumns) {
        if (introspectedColumn.isJDBCDateColumn()) {
          rc = true;
          break;
        }
      }
    }

    return rc;
  }

  public boolean hasJDBCTimeColumns()
  {
    boolean rc = false;

    for (IntrospectedColumn introspectedColumn : this.primaryKeyColumns) {
      if (introspectedColumn.isJDBCTimeColumn()) {
        rc = true;
        break;
      }
    }

    if (!rc) {
      for (IntrospectedColumn introspectedColumn : this.baseColumns) {
        if (introspectedColumn.isJDBCTimeColumn()) {
          rc = true;
          break;
        }
      }
    }

    return rc;
  }

  public List<IntrospectedColumn> getPrimaryKeyColumns()
  {
    return this.primaryKeyColumns;
  }

  public boolean hasPrimaryKeyColumns() {
    return this.primaryKeyColumns.size() > 0;
  }

  public List<IntrospectedColumn> getBaseColumns() {
    return this.baseColumns;
  }

  public List<IntrospectedColumn> getAllColumns()
  {
    List<IntrospectedColumn> answer = new ArrayList<>();
    answer.addAll(this.primaryKeyColumns);
    answer.addAll(this.baseColumns);
    answer.addAll(this.blobColumns);

    return answer;
  }

  public List<IntrospectedColumn> getNonBLOBColumns()
  {
    List<IntrospectedColumn> answer = new ArrayList<>();
    answer.addAll(this.primaryKeyColumns);
    answer.addAll(this.baseColumns);

    return answer;
  }

  public int getNonBLOBColumnCount() {
    return this.primaryKeyColumns.size() + this.baseColumns.size();
  }

  public List<IntrospectedColumn> getNonPrimaryKeyColumns() {
    List<IntrospectedColumn> answer = new ArrayList<>();
    answer.addAll(this.baseColumns);
    answer.addAll(this.blobColumns);

    return answer;
  }

  public List<IntrospectedColumn> getBLOBColumns() {
    return this.blobColumns;
  }

  public boolean hasBLOBColumns() {
    return this.blobColumns.size() > 0;
  }

  public boolean hasBaseColumns() {
    return this.baseColumns.size() > 0;
  }

  public Rules getRules() {
    return this.rules;
  }

  public String getTableConfigurationProperty(String property) {
    return this.tableConfiguration.getProperty(property);
  }

  public String getPrimaryKeyType() {
    return (String)this.internalAttributes.get(InternalAttribute.ATTR_PRIMARY_KEY_TYPE);
  }

  public String getBaseRecordType()
  {
    return (String)this.internalAttributes.get(InternalAttribute.ATTR_BASE_RECORD_TYPE);
  }

  public String getExampleType()
  {
    return (String)this.internalAttributes.get(InternalAttribute.ATTR_EXAMPLE_TYPE);
  }

  public String getRecordWithBLOBsType()
  {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_RECORD_WITH_BLOBS_TYPE);
  }

  public String getIbatis2SqlMapFileName()
  {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_IBATIS2_SQL_MAP_FILE_NAME);
  }

  public String getIbatis2SqlMapNamespace() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_IBATIS2_SQL_MAP_NAMESPACE);
  }

  public String getIbatis2SqlMapPackage()
  {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_IBATIS2_SQL_MAP_PACKAGE);
  }

  public String getDAOImplementationType() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_DAO_IMPLEMENTATION_TYPE);
  }

  public String getDAOInterfaceType() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_DAO_INTERFACE_TYPE);
  }

  public boolean hasAnyColumns()
  {
    return (this.primaryKeyColumns.size() > 0) || (this.baseColumns.size() > 0) || 
      (this.blobColumns.size() > 0);
  }

  public void setTableConfiguration(TableConfiguration tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }

  public void setFullyQualifiedTable(FullyQualifiedTable fullyQualifiedTable) {
    this.fullyQualifiedTable = fullyQualifiedTable;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public void addColumn(IntrospectedColumn introspectedColumn) {
    if (introspectedColumn.isBLOBColumn())
      this.blobColumns.add(introspectedColumn);
    else {
      this.baseColumns.add(introspectedColumn);
    }

    introspectedColumn.setIntrospectedTable(this);
  }

  public void addPrimaryKeyColumn(String columnName) {
    boolean found = false;

    Iterator<?> iter = this.baseColumns.iterator();
    while (iter.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
      if (introspectedColumn.getActualColumnName().equals(columnName)) {
        this.primaryKeyColumns.add(introspectedColumn);
        iter.remove();
        found = true;
        break;
      }

    }

    if (!found) {
      iter = this.blobColumns.iterator();
      while (iter.hasNext()) {
        IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
        if (introspectedColumn.getActualColumnName().equals(columnName)) {
          this.primaryKeyColumns.add(introspectedColumn);
          iter.remove();
          found = true;
          break;
        }
      }
    }
  }

  public Object getAttribute(String name) {
    return this.attributes.get(name);
  }

  public void removeAttribute(String name) {
    this.attributes.remove(name);
  }

  public void setAttribute(String name, Object value) {
    this.attributes.put(name, value);
  }

  public void initialize() {
    calculateJavaClientAttributes();
    calculateModelAttributes();
    calculateXmlAttributes();
    // FIXME sjy 后续增加service和controller主要初始化

    if (this.tableConfiguration.getModelType() == ModelType.HIERARCHICAL)
      this.rules = new HierarchicalModelRules(this);
    else if (this.tableConfiguration.getModelType() == ModelType.FLAT)
      this.rules = new FlatModelRules(this);
    else {
      this.rules = new ConditionalModelRules(this);
    }

    this.context.getPlugins().initialized(this);
  }

  protected void calculateXmlAttributes()
  {
    setIbatis2SqlMapPackage(calculateIbatis2SqlMapPackage());
    setIbatis2SqlMapFileName(calculateIbatis2SqlMapFileName());
    setMyBatis3XmlMapperFileName(calculateMyBatis3XmlMapperFileName());
    setMyBatis3XmlMapperPackage(getIbatis2SqlMapPackage());

    setIbatis2SqlMapNamespace(calculateIbatis2SqlMapNamespace());
    setSqlMapFullyQualifiedRuntimeTableName(calculateSqlMapFullyQualifiedRuntimeTableName());
    setSqlMapAliasedFullyQualifiedRuntimeTableName(calculateSqlMapAliasedFullyQualifiedRuntimeTableName());
    // FIXME sjy 方法名称替换成统一examleName 
    setCountByExampleStatementId("countBy"+Globar.exampleName);
    setDeleteByExampleStatementId("deleteBy"+Globar.exampleName);
    setDeleteByPrimaryKeyStatementId("deleteByPrimaryKey");
    setInsertStatementId("insert");
    setInsertSelectiveStatementId("insertSelective");
    setSelectByExampleStatementId("selectBy"+Globar.exampleName);
    setSelectByExampleWithBLOBsStatementId("selectBy"+Globar.exampleName+"WithBLOBs");
    setSelectByPrimaryKeyStatementId("selectByPrimaryKey");
    setUpdateByExampleStatementId("updateBy"+Globar.exampleName);
    setUpdateByExampleSelectiveStatementId("updateBy"+Globar.exampleName+"Selective");
    setUpdateByExampleWithBLOBsStatementId("updateBy"+Globar.exampleName+"WithBLOBs");
    setUpdateByPrimaryKeyStatementId("updateByPrimaryKey");
    setUpdateByPrimaryKeySelectiveStatementId("updateByPrimaryKeySelective");
    setUpdateByPrimaryKeyWithBLOBsStatementId("updateByPrimaryKeyWithBLOBs");
    setBaseResultMapId("BaseResultMap");
    setResultMapWithBLOBsId("ResultMapWithBLOBs");
    setExampleWhereClauseId(Globar.exampleName+"_Where_Clause");
    setBaseColumnListId("Base_Column_List");
    setBlobColumnListId("Blob_Column_List");
    setMyBatis3UpdateByExampleWhereClauseId("Update_By_"+Globar.exampleName+"_Where_Clause");
  }

  public void setBlobColumnListId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_BLOB_COLUMN_LIST_ID, s);
  }

  public void setBaseColumnListId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_BASE_COLUMN_LIST_ID, s);
  }

  public void setExampleWhereClauseId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_EXAMPLE_WHERE_CLAUSE_ID, 
      s);
  }

  public void setMyBatis3UpdateByExampleWhereClauseId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID, 
      s);
  }

  public void setResultMapWithBLOBsId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_RESULT_MAP_WITH_BLOBS_ID, 
      s);
  }

  public void setBaseResultMapId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_BASE_RESULT_MAP_ID, s);
  }

  public void setUpdateByPrimaryKeyWithBLOBsStatementId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID, 
      s);
  }

  public void setUpdateByPrimaryKeySelectiveStatementId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID, 
      s);
  }

  public void setUpdateByPrimaryKeyStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID, s);
  }

  public void setUpdateByExampleWithBLOBsStatementId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, 
      s);
  }

  public void setUpdateByExampleSelectiveStatementId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID, 
      s);
  }

  public void setUpdateByExampleStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID, s);
  }

  public void setSelectByPrimaryKeyStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID, s);
  }

  public void setSelectByExampleWithBLOBsStatementId(String s) {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, 
      s);
  }

  public void setSelectByExampleStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID, s);
  }

  public void setInsertSelectiveStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_INSERT_SELECTIVE_STATEMENT_ID, s);
  }

  public void setInsertStatementId(String s) {
    this.internalAttributes.put(InternalAttribute.ATTR_INSERT_STATEMENT_ID, s);
  }

  public void setDeleteByPrimaryKeyStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID, s);
  }

  public void setDeleteByExampleStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID, s);
  }

  public void setCountByExampleStatementId(String s) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID, s);
  }

  public String getBlobColumnListId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_BLOB_COLUMN_LIST_ID);
  }

  public String getBaseColumnListId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_BASE_COLUMN_LIST_ID);
  }

  public String getExampleWhereClauseId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_EXAMPLE_WHERE_CLAUSE_ID);
  }

  public String getMyBatis3UpdateByExampleWhereClauseId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID);
  }

  public String getResultMapWithBLOBsId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_RESULT_MAP_WITH_BLOBS_ID);
  }

  public String getBaseResultMapId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_BASE_RESULT_MAP_ID);
  }

  public String getUpdateByPrimaryKeyWithBLOBsStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID);
  }

  public String getUpdateByPrimaryKeySelectiveStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID);
  }

  public String getUpdateByPrimaryKeyStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID);
  }

  public String getUpdateByExampleWithBLOBsStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID);
  }

  public String getUpdateByExampleSelectiveStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID);
  }

  public String getUpdateByExampleStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID);
  }

  public String getSelectByPrimaryKeyStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID);
  }

  public String getSelectByExampleWithBLOBsStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID);
  }

  public String getSelectByExampleStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID);
  }

  public String getInsertSelectiveStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_INSERT_SELECTIVE_STATEMENT_ID);
  }

  public String getInsertStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_INSERT_STATEMENT_ID);
  }

  public String getDeleteByPrimaryKeyStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID);
  }

  public String getDeleteByExampleStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID);
  }

  public String getCountByExampleStatementId() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID);
  }

  protected String calculateJavaClientImplementationPackage() {
    JavaClientGeneratorConfiguration config = this.context
      .getJavaClientGeneratorConfiguration();
    if (config == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    if (StringUtility.stringHasValue(config.getImplementationPackage()))
      sb.append(config.getImplementationPackage());
    else {
      sb.append(config.getTargetPackage());
    }
    if (StringUtility.isTrue(config
      .getProperty("enableSubPackages"))) {
      sb.append(this.fullyQualifiedTable.getSubPackage());
    }

    return sb.toString();
  }

  protected String calculateJavaClientInterfacePackage() {
    JavaClientGeneratorConfiguration config = this.context
      .getJavaClientGeneratorConfiguration();
    if (config == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    sb.append(config.getTargetPackage());
    if (StringUtility.isTrue(config
      .getProperty("enableSubPackages"))) {
      sb.append(this.fullyQualifiedTable.getSubPackage());
    }

    return sb.toString();
  }

  protected void calculateJavaClientAttributes() {
    if (this.context.getJavaClientGeneratorConfiguration() == null) {
      return;
    }

    StringBuilder sb = new StringBuilder();
    sb.append(calculateJavaClientImplementationPackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("DAOImpl");
    setDAOImplementationType(sb.toString());

    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("DAO");
    setDAOInterfaceType(sb.toString());

    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append(Globar.daoName);
    setMyBatis3JavaMapperType(sb.toString());

    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("SqlProvider");
    setMyBatis3SqlProviderType(sb.toString());
  }

  protected String calculateJavaModelPackage() {
    JavaModelGeneratorConfiguration config = this.context
      .getJavaModelGeneratorConfiguration();

    StringBuilder sb = new StringBuilder();
    sb.append(config.getTargetPackage());
    if (StringUtility.isTrue(config
      .getProperty("enableSubPackages"))) {
      sb.append(this.fullyQualifiedTable.getSubPackage());
    }

    return sb.toString();
  }

  protected void calculateModelAttributes() {
    String pakkage = calculateJavaModelPackage();

    StringBuilder sb = new StringBuilder();
    sb.append(pakkage);
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("Key");
    setPrimaryKeyType(sb.toString());

    sb.setLength(0);
    sb.append(pakkage);
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    setBaseRecordType(sb.toString());

    sb.setLength(0);
    sb.append(pakkage);
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("WithBLOBs");
    setRecordWithBLOBsType(sb.toString());

    sb.setLength(0);
    sb.append(pakkage);
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append(Globar.exampleName);
    setExampleType(sb.toString());
  }

  protected String calculateIbatis2SqlMapPackage() {
    StringBuilder sb = new StringBuilder();
    SqlMapGeneratorConfiguration config = this.context
      .getSqlMapGeneratorConfiguration();

    if (config != null) {
      sb.append(config.getTargetPackage());
      if (StringUtility.isTrue(config
        .getProperty("enableSubPackages"))) {
        sb.append(this.fullyQualifiedTable.getSubPackage());
      }
    }

    return sb.toString();
  }

  protected String calculateIbatis2SqlMapFileName() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.fullyQualifiedTable.getIbatis2SqlMapNamespace());
    sb.append("_SqlMap.xml");
    return sb.toString();
  }

  protected String calculateMyBatis3XmlMapperFileName() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("Mapper.xml");
    return sb.toString();
  }

  protected String calculateIbatis2SqlMapNamespace() {
    return this.fullyQualifiedTable.getIbatis2SqlMapNamespace();
  }

  protected String calculateSqlMapFullyQualifiedRuntimeTableName() {
    return this.fullyQualifiedTable.getFullyQualifiedTableNameAtRuntime();
  }

  protected String calculateSqlMapAliasedFullyQualifiedRuntimeTableName() {
    return this.fullyQualifiedTable.getAliasedFullyQualifiedTableNameAtRuntime();
  }

  public String getFullyQualifiedTableNameAtRuntime() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
  }

  public String getAliasedFullyQualifiedTableNameAtRuntime() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
  }

  public abstract void calculateGenerators(List<String> paramList, ProgressCallback paramProgressCallback);

  public abstract List<GeneratedJavaFile> getGeneratedJavaFiles();

  public abstract List<GeneratedXmlFile> getGeneratedXmlFiles();

  public abstract boolean isJava5Targeted();

  public abstract int getGenerationSteps();

  public void setRules(Rules rules)
  {
    this.rules = rules;
  }

  public TableConfiguration getTableConfiguration() {
    return this.tableConfiguration;
  }

  public void setDAOImplementationType(String DAOImplementationType) {
    this.internalAttributes.put(InternalAttribute.ATTR_DAO_IMPLEMENTATION_TYPE, 
      DAOImplementationType);
  }

  public void setDAOInterfaceType(String DAOInterfaceType) {
    this.internalAttributes.put(InternalAttribute.ATTR_DAO_INTERFACE_TYPE, 
      DAOInterfaceType);
  }

  public void setPrimaryKeyType(String primaryKeyType) {
    this.internalAttributes.put(InternalAttribute.ATTR_PRIMARY_KEY_TYPE, 
      primaryKeyType);
  }

  public void setBaseRecordType(String baseRecordType) {
    this.internalAttributes.put(InternalAttribute.ATTR_BASE_RECORD_TYPE, 
      baseRecordType);
  }

  public void setRecordWithBLOBsType(String recordWithBLOBsType) {
    this.internalAttributes.put(InternalAttribute.ATTR_RECORD_WITH_BLOBS_TYPE, 
      recordWithBLOBsType);
  }

  public void setExampleType(String exampleType) {
    this.internalAttributes
      .put(InternalAttribute.ATTR_EXAMPLE_TYPE, exampleType);
  }

  public void setIbatis2SqlMapPackage(String sqlMapPackage) {
    this.internalAttributes.put(InternalAttribute.ATTR_IBATIS2_SQL_MAP_PACKAGE, 
      sqlMapPackage);
  }

  public void setIbatis2SqlMapFileName(String sqlMapFileName) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_IBATIS2_SQL_MAP_FILE_NAME, 
      sqlMapFileName);
  }

  public void setIbatis2SqlMapNamespace(String sqlMapNamespace) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_IBATIS2_SQL_MAP_NAMESPACE, 
      sqlMapNamespace);
  }

  public void setSqlMapFullyQualifiedRuntimeTableName(String fullyQualifiedRuntimeTableName)
  {
    this.internalAttributes.put(
      InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, 
      fullyQualifiedRuntimeTableName);
  }

  public void setSqlMapAliasedFullyQualifiedRuntimeTableName(String aliasedFullyQualifiedRuntimeTableName)
  {
    this.internalAttributes
      .put(
      InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, 
      aliasedFullyQualifiedRuntimeTableName);
  }

  public String getMyBatis3XmlMapperPackage() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE);
  }

  public void setMyBatis3XmlMapperPackage(String mybatis3XmlMapperPackage) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE, 
      mybatis3XmlMapperPackage);
  }

  public String getMyBatis3XmlMapperFileName() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME);
  }

  public void setMyBatis3XmlMapperFileName(String mybatis3XmlMapperFileName) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME, 
      mybatis3XmlMapperFileName);
  }

  public String getMyBatis3JavaMapperType() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE);
  }

  public void setMyBatis3JavaMapperType(String mybatis3JavaMapperType) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE, 
      mybatis3JavaMapperType);
  }

  public String getMyBatis3SqlProviderType() {
    return 
      (String)this.internalAttributes
      .get(InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE);
  }

  public void setMyBatis3SqlProviderType(String mybatis3SqlProviderType) {
    this.internalAttributes.put(
      InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE, 
      mybatis3SqlProviderType);
  }

  public TargetRuntime getTargetRuntime() {
    return this.targetRuntime;
  }

  public boolean isImmutable()
  {
    Properties properties;
    if (this.tableConfiguration.getProperties().containsKey("immutable"))
      properties = this.tableConfiguration.getProperties();
    else {
      properties = this.context.getJavaModelGeneratorConfiguration().getProperties();
    }

    return StringUtility.isTrue(properties.getProperty("immutable"));
  }

  public boolean isConstructorBased() {
    if (isImmutable())
      return true;
    Properties properties;
    if (this.tableConfiguration.getProperties().containsKey("constructorBased"))
      properties = this.tableConfiguration.getProperties();
    else {
      properties = this.context.getJavaModelGeneratorConfiguration().getProperties();
    }

    return StringUtility.isTrue(properties.getProperty("constructorBased"));
  }

  public abstract boolean requiresXMLGenerator();

  protected static enum InternalAttribute
  {
    ATTR_DAO_IMPLEMENTATION_TYPE, 
    ATTR_DAO_INTERFACE_TYPE, 
    ATTR_PRIMARY_KEY_TYPE, 
    ATTR_BASE_RECORD_TYPE, 
    ATTR_RECORD_WITH_BLOBS_TYPE, 
    ATTR_EXAMPLE_TYPE, 
    ATTR_IBATIS2_SQL_MAP_PACKAGE, 
    ATTR_IBATIS2_SQL_MAP_FILE_NAME, 
    ATTR_IBATIS2_SQL_MAP_NAMESPACE, 
    ATTR_MYBATIS3_XML_MAPPER_PACKAGE, 
    ATTR_MYBATIS3_XML_MAPPER_FILE_NAME, 

    ATTR_MYBATIS3_JAVA_MAPPER_TYPE, 
    ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, 
    ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, 
    ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID, 
    ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID, 
    ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID, 
    ATTR_INSERT_STATEMENT_ID, 
    ATTR_INSERT_SELECTIVE_STATEMENT_ID, 
    ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID, 
    ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, 
    ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID, 
    ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID, 
    ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID, 
    ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, 
    ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID, 
    ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID, 
    ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID, 
    ATTR_BASE_RESULT_MAP_ID, 
    ATTR_RESULT_MAP_WITH_BLOBS_ID, 
    ATTR_EXAMPLE_WHERE_CLAUSE_ID, 
    ATTR_BASE_COLUMN_LIST_ID, 
    ATTR_BLOB_COLUMN_LIST_ID, 
    ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID, 
    ATTR_MYBATIS3_SQL_PROVIDER_TYPE;
  }

  public static enum TargetRuntime
  {
    IBATIS2, MYBATIS3;
  }
}