package org.apache.ibatis.ibator.api;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.mybatis.generator.api.ProgressCallback;

public class EclipseProgressCallback
  implements ProgressCallback
{
  private static final int INTROSPECTION_FACTOR = 2000;
  private static final int GENERATION_FACTOR = 4000;
  private static final int SAVE_FACTOR = 4000;
  private SubMonitor parentProgress;
  private SubMonitor currentChildProgress;
  private int currentTick;

  public EclipseProgressCallback(IProgressMonitor progressMonitor)
  {
    this.parentProgress = 
      SubMonitor.convert(progressMonitor, 
      10000);
  }

  public void checkCancel()
    throws InterruptedException
  {
    if (this.currentChildProgress.isCanceled())
      throw new InterruptedException();
  }

  public void generationStarted(int totalTasks)
  {
    this.currentChildProgress = this.parentProgress.newChild(GENERATION_FACTOR);
    this.currentTick = (totalTasks <= 0 ? GENERATION_FACTOR : GENERATION_FACTOR / totalTasks);
    if (this.currentTick == 0) {
      this.currentTick = 1;
    }

    this.currentChildProgress.beginTask("Generating Files", GENERATION_FACTOR);
  }

  public void introspectionStarted(int totalTasks) {
    this.currentChildProgress = this.parentProgress.newChild(INTROSPECTION_FACTOR);
    this.currentTick = (totalTasks <= 0 ? INTROSPECTION_FACTOR : INTROSPECTION_FACTOR / totalTasks);
    if (this.currentTick == 0) {
      this.currentTick = 1;
    }

    this.currentChildProgress.beginTask("Introspecting Tables", INTROSPECTION_FACTOR);
  }

  public void saveStarted(int totalTasks) {
    this.currentChildProgress = this.parentProgress.newChild(SAVE_FACTOR);
    this.currentTick = (totalTasks <= 0 ? SAVE_FACTOR : SAVE_FACTOR / totalTasks);
    if (this.currentTick == 0) {
      this.currentTick = 1;
    }

    this.currentChildProgress.beginTask("Saving Generated Files", SAVE_FACTOR);
  }

  public void startTask(String taskName) {
    this.currentChildProgress.subTask(taskName);
    this.currentChildProgress.worked(this.currentTick);
  }

  public void done()
  {
  }
}