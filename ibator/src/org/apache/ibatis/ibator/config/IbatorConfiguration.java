package org.apache.ibatis.ibator.config;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.exception.InvalidConfigurationException;

public class IbatorConfiguration
{
  private List<String> classPathEntries;

  public IbatorConfiguration()
  {
    this.classPathEntries = new ArrayList<>();
  }

  public void addClasspathEntry(String entry) {
    this.classPathEntries.add(entry);
  }

  public List<String> getClassPathEntries()
  {
    return this.classPathEntries;
  }

  public void validate()
    throws InvalidConfigurationException
  {
  }
}