package org.mybatis.generator.internal;

import org.mybatis.generator.api.ProgressCallback;

public class NullProgressCallback
  implements ProgressCallback
{
  public void generationStarted(int totalTasks)
  {
  }

  public void introspectionStarted(int totalTasks)
  {
  }

  public void saveStarted(int totalTasks)
  {
  }

  public void startTask(String taskName)
  {
  }

  public void checkCancel()
    throws InterruptedException
  {
  }

  public void done()
  {
  }
}