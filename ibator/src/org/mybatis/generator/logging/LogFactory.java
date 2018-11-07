package org.mybatis.generator.logging;

import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.messages.Messages;

public class LogFactory
{
  private static AbstractLogFactory logFactory;

  static
  {
    try
    {
      ObjectFactory.internalClassForName("org.apache.log4j.Logger");
      logFactory = new Log4jLoggingLogFactory();
    } catch (Exception e) {
      logFactory = new JdkLoggingLogFactory();
    }
  }

  public static Log getLog(Class<?> clazz) {
    try {
      return logFactory.getLog(clazz);
    } catch (Throwable t) {
      throw new RuntimeException(
        Messages.getString("RuntimeError.21", 
        clazz.getName(), t.getMessage()), t);
    }
  }

  public static synchronized void forceJavaLogging()
  {
    logFactory = new JdkLoggingLogFactory();
  }

  public static void setLogFactory(AbstractLogFactory logFactory)
  {
	  LogFactory.logFactory = logFactory;
  }

  private static class JdkLoggingLogFactory
    implements AbstractLogFactory
  {
    public Log getLog(Class<?> clazz)
    {
      return new JdkLoggingImpl(clazz);
    }
  }

  private static class Log4jLoggingLogFactory implements AbstractLogFactory {
    public Log getLog(Class<?> clazz) {
      return new Log4jImpl(clazz);
    }
  }
}