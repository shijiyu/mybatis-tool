package org.mybatis.generator.exception;

import java.util.List;

public class InvalidConfigurationException extends Exception
{
  static final long serialVersionUID = 4902307610148543411L;
  private List<String> errors;

  public InvalidConfigurationException(List<String> errors)
  {
    this.errors = errors;
  }

  public List<String> getErrors() {
    return this.errors;
  }
}