package org.mybatis.generator.api;

import org.mybatis.generator.internal.NullProgressCallback;

public class VerboseProgressCallback extends NullProgressCallback
{
  public void startTask(String taskName)
  {
    System.out.println(taskName);
  }
}