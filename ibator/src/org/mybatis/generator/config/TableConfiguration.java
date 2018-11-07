package org.mybatis.generator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.EqualsUtil;
import org.mybatis.generator.internal.util.HashCodeUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TableConfiguration extends PropertyHolder
  implements Cloneable
{
  private boolean insertStatementEnabled;
  private boolean selectByPrimaryKeyStatementEnabled;
  private boolean selectByExampleStatementEnabled;
  private boolean updateByPrimaryKeyStatementEnabled;
  private boolean deleteByPrimaryKeyStatementEnabled;
  private boolean deleteByExampleStatementEnabled;
  private boolean countByExampleStatementEnabled;
  private boolean updateByExampleStatementEnabled;
  private List<ColumnOverride> columnOverrides;
  private Map<IgnoredColumn, Boolean> ignoredColumns;
  private GeneratedKey generatedKey;
  private String selectByPrimaryKeyQueryId;
  private String selectByExampleQueryId;
  private String catalog;
  private String schema;
  private String tableName;
  private String domainObjectName;
  private String alias;
  private ModelType modelType;
  private boolean wildcardEscapingEnabled;
  private String configuredModelType;
  private boolean delimitIdentifiers;
  private ColumnRenamingRule columnRenamingRule;
  private boolean isAllColumnDelimitingEnabled;

  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }

  public TableConfiguration(Context context)
  {
    this.modelType = context.getDefaultModelType();

    this.columnOverrides = new ArrayList<>();
    this.ignoredColumns = new HashMap<>();

    this.insertStatementEnabled = true;
    this.selectByPrimaryKeyStatementEnabled = true;
    this.selectByExampleStatementEnabled = true;
    this.updateByPrimaryKeyStatementEnabled = true;
    this.deleteByPrimaryKeyStatementEnabled = true;
    this.deleteByExampleStatementEnabled = true;
    this.countByExampleStatementEnabled = true;
    this.updateByExampleStatementEnabled = true;
  }

  public boolean isDeleteByPrimaryKeyStatementEnabled() {
    return this.deleteByPrimaryKeyStatementEnabled;
  }

  public void setDeleteByPrimaryKeyStatementEnabled(boolean deleteByPrimaryKeyStatementEnabled)
  {
    this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
  }

  public boolean isInsertStatementEnabled() {
    return this.insertStatementEnabled;
  }

  public void setInsertStatementEnabled(boolean insertStatementEnabled) {
    this.insertStatementEnabled = insertStatementEnabled;
  }

  public boolean isSelectByPrimaryKeyStatementEnabled() {
    return this.selectByPrimaryKeyStatementEnabled;
  }

  public void setSelectByPrimaryKeyStatementEnabled(boolean selectByPrimaryKeyStatementEnabled)
  {
    this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
  }

  public boolean isUpdateByPrimaryKeyStatementEnabled() {
    return this.updateByPrimaryKeyStatementEnabled;
  }

  public void setUpdateByPrimaryKeyStatementEnabled(boolean updateByPrimaryKeyStatementEnabled)
  {
    this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
  }


public boolean isColumnIgnored(String columnName)
  {
    Iterator localIterator = this.ignoredColumns
      .entrySet().iterator();

    while (localIterator.hasNext()) {
      Map.Entry entry = (Map.Entry)localIterator.next();
      IgnoredColumn ic = (IgnoredColumn)entry.getKey();
      if (ic.isColumnNameDelimited()) {
        if (columnName.equals(ic.getColumnName())) {
          entry.setValue(Boolean.TRUE);
          return true;
        }
      }
      else if (columnName.equalsIgnoreCase(ic.getColumnName())) {
        entry.setValue(Boolean.TRUE);
        return true;
      }

    }

    return false;
  }

  public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
    this.ignoredColumns.put(ignoredColumn, Boolean.FALSE);
  }

  public void addColumnOverride(ColumnOverride columnOverride) {
    this.columnOverrides.add(columnOverride);
  }

  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof TableConfiguration)) {
      return false;
    }

    TableConfiguration other = (TableConfiguration)obj;

    return (EqualsUtil.areEqual(this.catalog, other.catalog)) && 
      (EqualsUtil.areEqual(this.schema, other.schema)) && 
      (EqualsUtil.areEqual(this.tableName, other.tableName));
  }

  public int hashCode()
  {
    int result = 23;
    result = HashCodeUtil.hash(result, this.catalog);
    result = HashCodeUtil.hash(result, this.schema);
    result = HashCodeUtil.hash(result, this.tableName);

    return result;
  }

  public boolean isSelectByExampleStatementEnabled() {
    return this.selectByExampleStatementEnabled;
  }

  public void setSelectByExampleStatementEnabled(boolean selectByExampleStatementEnabled)
  {
    this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
  }

  public ColumnOverride getColumnOverride(String columnName)
  {
    for (ColumnOverride co : this.columnOverrides) {
      if (co.isColumnNameDelimited()) {
        if (columnName.equals(co.getColumnName())) {
          return co;
        }
      }
      else if (columnName.equalsIgnoreCase(co.getColumnName())) {
        return co;
      }

    }

    return null;
  }

  public GeneratedKey getGeneratedKey() {
    return this.generatedKey;
  }

  public String getSelectByExampleQueryId() {
    return this.selectByExampleQueryId;
  }

  public void setSelectByExampleQueryId(String selectByExampleQueryId) {
    this.selectByExampleQueryId = selectByExampleQueryId;
  }

  public String getSelectByPrimaryKeyQueryId() {
    return this.selectByPrimaryKeyQueryId;
  }

  public void setSelectByPrimaryKeyQueryId(String selectByPrimaryKeyQueryId) {
    this.selectByPrimaryKeyQueryId = selectByPrimaryKeyQueryId;
  }

  public boolean isDeleteByExampleStatementEnabled() {
    return this.deleteByExampleStatementEnabled;
  }

  public void setDeleteByExampleStatementEnabled(boolean deleteByExampleStatementEnabled)
  {
    this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
  }

  public boolean areAnyStatementsEnabled()
  {
    return (this.selectByExampleStatementEnabled) || 
      (this.selectByPrimaryKeyStatementEnabled) || (this.insertStatementEnabled) || 
      (this.updateByPrimaryKeyStatementEnabled) || 
      (this.deleteByExampleStatementEnabled) || 
      (this.deleteByPrimaryKeyStatementEnabled) || 
      (this.countByExampleStatementEnabled) || 
      (this.updateByExampleStatementEnabled);
  }

  public void setGeneratedKey(GeneratedKey generatedKey) {
    this.generatedKey = generatedKey;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getCatalog() {
    return this.catalog;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public String getDomainObjectName() {
    return this.domainObjectName;
  }

  public void setDomainObjectName(String domainObjectName) {
    this.domainObjectName = domainObjectName;
  }

  public String getSchema() {
    return this.schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<ColumnOverride> getColumnOverrides() {
    return this.columnOverrides;
  }

  public List<String> getIgnoredColumnsInError()
  {
    List answer = new ArrayList();

    Iterator localIterator = this.ignoredColumns
      .entrySet().iterator();

    while (localIterator.hasNext()) {
      Map.Entry entry = (Map.Entry)localIterator.next();
      if (Boolean.FALSE.equals(entry.getValue())) {
        answer.add(((IgnoredColumn)entry.getKey()).getColumnName());
      }
    }

    return answer;
  }

  public ModelType getModelType() {
    return this.modelType;
  }

  public void setConfiguredModelType(String configuredModelType) {
    this.configuredModelType = configuredModelType;
    this.modelType = ModelType.getModelType(configuredModelType);
  }

  public boolean isWildcardEscapingEnabled() {
    return this.wildcardEscapingEnabled;
  }

  public void setWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
    this.wildcardEscapingEnabled = wildcardEscapingEnabled;
  }

  public XmlElement toXmlElement() {
    XmlElement xmlElement = new XmlElement("table");
    xmlElement.addAttribute(new Attribute("tableName", this.tableName));

    if (StringUtility.stringHasValue(this.catalog)) {
      xmlElement.addAttribute(new Attribute("catalog", this.catalog));
    }

    if (StringUtility.stringHasValue(this.schema)) {
      xmlElement.addAttribute(new Attribute("schema", this.schema));
    }

    if (StringUtility.stringHasValue(this.alias)) {
      xmlElement.addAttribute(new Attribute("alias", this.alias));
    }

    if (StringUtility.stringHasValue(this.domainObjectName)) {
      xmlElement.addAttribute(new Attribute(
        "domainObjectName", this.domainObjectName));
    }

    if (!this.insertStatementEnabled) {
      xmlElement.addAttribute(new Attribute("enableInsert", "false"));
    }

    if (!this.selectByPrimaryKeyStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableSelectByPrimaryKey", "false"));
    }

    if (!this.selectByExampleStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableSelectByExample", "false"));
    }

    if (!this.updateByPrimaryKeyStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableUpdateByPrimaryKey", "false"));
    }

    if (!this.deleteByPrimaryKeyStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableDeleteByPrimaryKey", "false"));
    }

    if (!this.deleteByExampleStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableDeleteByExample", "false"));
    }

    if (!this.countByExampleStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableCountByExample", "false"));
    }

    if (!this.updateByExampleStatementEnabled) {
      xmlElement.addAttribute(new Attribute(
        "enableUpdateByExample", "false"));
    }

    if (StringUtility.stringHasValue(this.selectByPrimaryKeyQueryId)) {
      xmlElement.addAttribute(new Attribute(
        "selectByPrimaryKeyQueryId", this.selectByPrimaryKeyQueryId));
    }

    if (StringUtility.stringHasValue(this.selectByExampleQueryId)) {
      xmlElement.addAttribute(new Attribute(
        "selectByExampleQueryId", this.selectByExampleQueryId));
    }

    if (this.configuredModelType != null) {
      xmlElement.addAttribute(new Attribute(
        "modelType", this.configuredModelType));
    }

    if (this.wildcardEscapingEnabled) {
      xmlElement.addAttribute(new Attribute("escapeWildcards", "true"));
    }

    if (this.isAllColumnDelimitingEnabled) {
      xmlElement.addAttribute(new Attribute("delimitAllColumns", "true"));
    }

    if (this.delimitIdentifiers) {
      xmlElement
        .addAttribute(new Attribute("delimitIdentifiers", "true"));
    }

    addPropertyXmlElements(xmlElement);

    if (this.generatedKey != null) {
      xmlElement.addElement(this.generatedKey.toXmlElement());
    }

    if (this.columnRenamingRule != null) {
      xmlElement.addElement(this.columnRenamingRule.toXmlElement());
    }

    if (this.ignoredColumns.size() > 0) {
      for (IgnoredColumn ignoredColumn : this.ignoredColumns.keySet()) {
        xmlElement.addElement(ignoredColumn.toXmlElement());
      }
    }

    if (this.columnOverrides.size() > 0) {
      for (ColumnOverride columnOverride : this.columnOverrides) {
        xmlElement.addElement(columnOverride.toXmlElement());
      }
    }

    return xmlElement;
  }

  public String toString()
  {
    return StringUtility.composeFullyQualifiedTableName(this.catalog, this.schema, 
      this.tableName, '.');
  }

  public boolean isDelimitIdentifiers() {
    return this.delimitIdentifiers;
  }

  public void setDelimitIdentifiers(boolean delimitIdentifiers) {
    this.delimitIdentifiers = delimitIdentifiers;
  }

  public boolean isCountByExampleStatementEnabled() {
    return this.countByExampleStatementEnabled;
  }

  public void setCountByExampleStatementEnabled(boolean countByExampleStatementEnabled)
  {
    this.countByExampleStatementEnabled = countByExampleStatementEnabled;
  }

  public boolean isUpdateByExampleStatementEnabled() {
    return this.updateByExampleStatementEnabled;
  }

  public void setUpdateByExampleStatementEnabled(boolean updateByExampleStatementEnabled)
  {
    this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
  }

  public void validate(List<String> errors, int listPosition) {
    if (!StringUtility.stringHasValue(this.tableName)) {
      errors.add(Messages.getString(
        "ValidationError.6", Integer.toString(listPosition)));
    }

    String fqTableName = StringUtility.composeFullyQualifiedTableName(
      this.catalog, this.schema, this.tableName, '.');

    if (this.generatedKey != null)
      this.generatedKey.validate(errors, fqTableName);
    boolean queryId2Set;
    if (StringUtility.isTrue(getProperty("useColumnIndexes")))
    {
      if ((this.selectByExampleStatementEnabled) && 
        (this.selectByPrimaryKeyStatementEnabled)) {
        boolean queryId1Set = StringUtility.stringHasValue(this.selectByExampleQueryId);
        queryId2Set = StringUtility.stringHasValue(this.selectByPrimaryKeyQueryId);

        if (queryId1Set != queryId2Set) {
          errors.add(
            Messages.getString("ValidationError.13", 
            fqTableName));
        }
      }
    }

    if (this.columnRenamingRule != null) {
      this.columnRenamingRule.validate(errors, fqTableName);
    }

    for (ColumnOverride columnOverride : this.columnOverrides) {
      columnOverride.validate(errors, fqTableName);
    }

    for (IgnoredColumn ignoredColumn : this.ignoredColumns.keySet())
      ignoredColumn.validate(errors, fqTableName);
  }

  public ColumnRenamingRule getColumnRenamingRule()
  {
    return this.columnRenamingRule;
  }

  public void setColumnRenamingRule(ColumnRenamingRule columnRenamingRule) {
    this.columnRenamingRule = columnRenamingRule;
  }

  public boolean isAllColumnDelimitingEnabled() {
    return this.isAllColumnDelimitingEnabled;
  }

  public void setAllColumnDelimitingEnabled(boolean isAllColumnDelimitingEnabled)
  {
    this.isAllColumnDelimitingEnabled = isAllColumnDelimitingEnabled;
  }
}