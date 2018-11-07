package org.mybatis.generator.internal.rules;

import org.mybatis.generator.api.IntrospectedTable;

public class FlatModelRules extends BaseRules
{
  public FlatModelRules(IntrospectedTable introspectedTable)
  {
    super(introspectedTable);
  }

  public boolean generatePrimaryKeyClass()
  {
    return false;
  }

  public boolean generateBaseRecordClass()
  {
    return true;
  }

  public boolean generateRecordWithBLOBsClass()
  {
    return false;
  }
}