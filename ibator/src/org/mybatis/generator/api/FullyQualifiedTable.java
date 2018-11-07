package org.mybatis.generator.api;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.EqualsUtil;
import org.mybatis.generator.internal.util.HashCodeUtil;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

public class FullyQualifiedTable
{
  private String introspectedCatalog;
  private String introspectedSchema;
  private String introspectedTableName;
  private String runtimeCatalog;
  private String runtimeSchema;
  private String runtimeTableName;
  private String domainObjectName;
  private String alias;
  private boolean ignoreQualifiersAtRuntime;
  private String beginningDelimiter;
  private String endingDelimiter;

  public FullyQualifiedTable(String introspectedCatalog, String introspectedSchema, String introspectedTableName, String domainObjectName, String alias, boolean ignoreQualifiersAtRuntime, String runtimeCatalog, String runtimeSchema, String runtimeTableName, boolean delimitIdentifiers, Context context)
  {
    this.introspectedCatalog = introspectedCatalog;
    this.introspectedSchema = introspectedSchema;
    this.introspectedTableName = introspectedTableName;
    this.domainObjectName = domainObjectName;
    this.ignoreQualifiersAtRuntime = ignoreQualifiersAtRuntime;
    this.runtimeCatalog = runtimeCatalog;
    this.runtimeSchema = runtimeSchema;
    this.runtimeTableName = runtimeTableName;

    if (alias == null)
      this.alias = null;
    else {
      this.alias = alias.trim();
    }

    this.beginningDelimiter = (delimitIdentifiers ? 
      context.getBeginningDelimiter() : "");
    this.endingDelimiter = (delimitIdentifiers ? context.getEndingDelimiter() : 
      "");
  }

  public String getIntrospectedCatalog() {
    return this.introspectedCatalog;
  }

  public String getIntrospectedSchema() {
    return this.introspectedSchema;
  }

  public String getIntrospectedTableName() {
    return this.introspectedTableName;
  }

  public static void main(String[] args)
  {
    String tString = StringUtility.composeFullyQualifiedTableName(null, null, "BBS_TBL", '.');
    System.out.println(tString);
  }

  public String getFullyQualifiedTableNameAtRuntime()
  {
    StringBuilder localCatalog = new StringBuilder();
    if (!this.ignoreQualifiersAtRuntime) {
      if (StringUtility.stringHasValue(this.runtimeCatalog))
        localCatalog.append(this.runtimeCatalog);
      else if (StringUtility.stringHasValue(this.introspectedCatalog)) {
        localCatalog.append(this.introspectedCatalog);
      }
    }
    if (localCatalog.length() > 0) {
      addDelimiters(localCatalog);
    }

    StringBuilder localSchema = new StringBuilder();
    if (!this.ignoreQualifiersAtRuntime) {
      if (StringUtility.stringHasValue(this.runtimeSchema))
        localSchema.append(this.runtimeSchema);
      else if (StringUtility.stringHasValue(this.introspectedSchema)) {
        localSchema.append(this.introspectedSchema);
      }
    }
    if (localSchema.length() > 0) {
      addDelimiters(localSchema);
    }

    StringBuilder localTableName = new StringBuilder();
    if (StringUtility.stringHasValue(this.runtimeTableName))
      localTableName.append(this.runtimeTableName);
    else {
      localTableName.append(this.introspectedTableName);
    }
    addDelimiters(localTableName);

    return StringUtility.composeFullyQualifiedTableName(localCatalog
      .toString(), localSchema.toString(), localTableName.toString(), 
      '.');
  }

  public String getAliasedFullyQualifiedTableNameAtRuntime()
  {
    StringBuilder sb = new StringBuilder();

    sb.append(getFullyQualifiedTableNameAtRuntime());

    if (StringUtility.stringHasValue(this.alias)) {
      sb.append(' ');
      sb.append(this.alias);
    }

    return sb.toString();
  }

  public String getIbatis2SqlMapNamespace()
  {
    String localCatalog = StringUtility.stringHasValue(this.runtimeCatalog) ? this.runtimeCatalog : 
      this.introspectedCatalog;
    String localSchema = StringUtility.stringHasValue(this.runtimeSchema) ? this.runtimeSchema : 
      this.introspectedSchema;
    String localTable = StringUtility.stringHasValue(this.runtimeTableName) ? this.runtimeTableName : 
      this.introspectedTableName;

    return StringUtility.composeFullyQualifiedTableName(
      this.ignoreQualifiersAtRuntime ? null : localCatalog, 
      this.ignoreQualifiersAtRuntime ? null : localSchema, 
      localTable, '_');
  }

  public String getDomainObjectName() {
    if (StringUtility.stringHasValue(this.domainObjectName))
      return this.domainObjectName;
    if (StringUtility.stringHasValue(this.runtimeTableName)) {
      return JavaBeansUtil.getCamelCaseString(this.runtimeTableName, true);
    }
    return JavaBeansUtil.getCamelCaseString(this.introspectedTableName, true);
  }

  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof FullyQualifiedTable)) {
      return false;
    }

    FullyQualifiedTable other = (FullyQualifiedTable)obj;

    if (EqualsUtil.areEqual(this.introspectedTableName, 
      other.introspectedTableName))
      if (EqualsUtil.areEqual(this.introspectedCatalog, 
        other.introspectedCatalog))
        if (EqualsUtil.areEqual(this.introspectedSchema, 
          other.introspectedSchema))
          return true;
    return 
      false;
  }

  public int hashCode()
  {
    int result = 23;
    result = HashCodeUtil.hash(result, this.introspectedTableName);
    result = HashCodeUtil.hash(result, this.introspectedCatalog);
    result = HashCodeUtil.hash(result, this.introspectedSchema);

    return result;
  }

  public String toString()
  {
    return StringUtility.composeFullyQualifiedTableName(
      this.introspectedCatalog, this.introspectedSchema, this.introspectedTableName, 
      '.');
  }

  public String getAlias() {
    return this.alias;
  }

  public String getSubPackage()
  {
    StringBuilder sb = new StringBuilder();
    if (!this.ignoreQualifiersAtRuntime) {
      if (StringUtility.stringHasValue(this.runtimeCatalog)) {
        sb.append('.');
        sb.append(this.runtimeCatalog.toLowerCase());
      } else if (StringUtility.stringHasValue(this.introspectedCatalog)) {
        sb.append('.');
        sb.append(this.introspectedCatalog.toLowerCase());
      }

      if (StringUtility.stringHasValue(this.runtimeSchema)) {
        sb.append('.');
        sb.append(this.runtimeSchema.toLowerCase());
      } else if (StringUtility.stringHasValue(this.introspectedSchema)) {
        sb.append('.');
        sb.append(this.introspectedSchema.toLowerCase());
      }

    }

    return sb.toString();
  }

  private void addDelimiters(StringBuilder sb) {
    if (StringUtility.stringHasValue(this.beginningDelimiter)) {
      sb.insert(0, this.beginningDelimiter);
    }

    if (StringUtility.stringHasValue(this.endingDelimiter))
      sb.append(this.endingDelimiter);
  }
}