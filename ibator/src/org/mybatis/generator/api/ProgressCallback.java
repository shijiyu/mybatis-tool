package org.mybatis.generator.api;

public abstract interface ProgressCallback
{
  public abstract void introspectionStarted(int paramInt);

  public abstract void generationStarted(int paramInt);

  public abstract void saveStarted(int paramInt);

  public abstract void startTask(String paramString);

  public abstract void done();

  public abstract void checkCancel()
    throws InterruptedException;
}