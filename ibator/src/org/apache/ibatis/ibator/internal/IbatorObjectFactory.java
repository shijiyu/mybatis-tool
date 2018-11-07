package org.apache.ibatis.ibator.internal;

import org.mybatis.generator.internal.util.messages.Messages;

public class IbatorObjectFactory
{
  private static ClassLoader externalClassLoader;

  private static ClassLoader getClassLoader()
  {
    if (externalClassLoader != null) {
      return externalClassLoader;
    }
    return Thread.currentThread().getContextClassLoader();
  }

  public static synchronized void setExternalClassLoader(ClassLoader classLoader)
  {
    externalClassLoader = classLoader;
  }

  public static Class<?> externalClassForName(String type)
    throws ClassNotFoundException
  {
    Class<?> clazz;
    try
    {
      clazz = getClassLoader().loadClass(type);
    }
    catch (Throwable e)
    {
      clazz = null;
    }

    if (clazz == null) {
      clazz = Class.forName(type);
    }

    return clazz;
  }

  public static Object createExternalObject(String type)
  {
      Object answer;
    try
    {
      Class<?> clazz = externalClassForName(type);
      answer = clazz.newInstance();
    }
    catch (Exception e)
    {

      throw new RuntimeException(
        Messages.getString("RuntimeError.6", type), e);
    }
    return answer;
  }

  public static Object createInternalObject(String type) {
    Class<?> clazz = null;
    try
    {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      clazz = cl.loadClass(type);
    }
    catch (Exception localException1)
    {
    }
    Object answer;
    try
    {
      if (clazz == null) {
        clazz = Class.forName(type);
      }

      answer = clazz.newInstance();
    }
    catch (Exception e)
    {

      throw new RuntimeException(
        Messages.getString("RuntimeError.6", type), e);
    }
    return answer;
  }
}