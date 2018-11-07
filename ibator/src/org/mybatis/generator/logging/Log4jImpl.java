package org.mybatis.generator.logging;

import org.apache.log4j.Logger;

public class Log4jImpl
  implements Log
{
  private Logger log;

  public Log4jImpl(Class<?> clazz)
  {
    this.log = Logger.getLogger(clazz);
  }

  public boolean isDebugEnabled() {
    return this.log.isDebugEnabled();
  }

  public void error(String s, Throwable e) {
    this.log.error(s, e);
  }

  public void error(String s) {
    this.log.error(s);
  }

  public void debug(String s) {
    this.log.debug(s);
  }

  public void warn(String s) {
    this.log.warn(s);
  }
}