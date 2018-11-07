package org.mybatis.generator.config;

public abstract class TypedPropertyHolder extends PropertyHolder
{
  private String configurationType;

  public String getConfigurationType()
  {
    return this.configurationType;
  }

  public void setConfigurationType(String configurationType)
  {
    if (!"DEFAULT".equalsIgnoreCase(configurationType))
      this.configurationType = configurationType;
  }
}