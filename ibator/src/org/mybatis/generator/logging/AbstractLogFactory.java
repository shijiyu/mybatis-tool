package org.mybatis.generator.logging;

public abstract interface AbstractLogFactory
{
  public abstract Log getLog(Class<?> paramClass);
}