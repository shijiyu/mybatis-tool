package org.apache.ibatis.ibator.exception;

@SuppressWarnings("serial")
public class ShellException extends RuntimeException
{
  public ShellException()
  {
  }

  public ShellException(String str)
  {
    super(str);
  }

  public ShellException(String message, Exception e) {
    super(message, e);
  }
}