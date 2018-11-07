package ibator.popup.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.ibator.eclipse.ui.actions.RunIbatorThread;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import ibator.Activator;

public class GO
{
  private IProject iProject;

  public GO(IProject project)
  {
    this.iProject = project;
    run();
  }

  public void run() {
    Shell shell = new Shell();
    try
    {
      List<String> warnings = new ArrayList<>();
      ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

      IRunnableWithProgress thread = new IbatorRunner(warnings);

      dialog.run(true, true, thread);

      if (warnings.size() > 0) {
        MultiStatus ms = new MultiStatus("org.apache.ibatis.ibator.eclipse.ui", 
          2, "Generation Warnings Occured", null);

        Iterator<?> iter = warnings.iterator();
        while (iter.hasNext()) {
          Status status = new Status(2, "org.apache.ibatis.ibator.eclipse.ui", 
            2, (String)iter.next(), null);
          ms.add(status);
        }

        ErrorDialog.openError(shell, "Ibator for iBATIS", 
          "Run Complete With Warninigs", ms, 2);
      }
    } catch (Exception e) {
      handleException(e, shell);
    }
  }

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
  }

  private void handleException(Exception exception, Shell shell)
  {
    Throwable exceptionToHandle;
    if ((exception instanceof InvocationTargetException))
      exceptionToHandle = ((InvocationTargetException)exception)
        .getCause();
    else
      exceptionToHandle = exception;
    IStatus status;
    if ((exceptionToHandle instanceof InterruptedException)) {
      status = new Status(8, "org.apache.ibatis.ibator.eclipse.ui", 
        8, "Cancelled by User", exceptionToHandle);
    }
    else
    {
      if ((exceptionToHandle instanceof CoreException)) {
        status = ((CoreException)exceptionToHandle).getStatus();
      } else {
        String message = "Unexpected error while running Ibator.";

        status = new Status(4, "org.apache.ibatis.ibator.eclipse.ui", 
          4, message, exceptionToHandle);
        Activator.getDefault().getLog().log(status);
      }
    }

    ErrorDialog.openError(shell, "Ibator for iBATIS", "Generation Failed", 
      status, 12);
  }

  private class IbatorRunner implements IRunnableWithProgress {
    private List<String> warnings;

    public IbatorRunner(List<String> warnings) {
      this.warnings = warnings;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
    {
      try {
        RunIbatorThread thread = new RunIbatorThread(GO.this.iProject, this.warnings);

        ResourcesPlugin.getWorkspace().run(thread, monitor);
      } catch (CoreException e) {
        throw new InvocationTargetException(e);
      }
    }
  }
}