package ibator.popup.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.ibatis.ibator.eclipse.ui.IbatorUIPlugin;
import org.apache.ibatis.ibator.eclipse.ui.actions.RunIbatorThread;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import ibator.Activator;
import ibator.ui.EclipseUI;
import ibator.ui.TableBrower;
import ibator.util.LocalDbConfig;
import ibator.vo.DBVO;

public class RunAction
  implements IObjectActionDelegate
{
  //private IFile selectedFile;
  private IProject iProject;

  public void run(IAction action)
  {
    Activator.getDefault();

    File file = new File(
      Activator.getDefault().getStateLocation().toOSString());
    if (!file.exists()) {
      file.mkdir();
    }
    Activator.getDefault(); String file2 = Activator.getDefault().getStateLocation()
      .toOSString() + 
      "/db.config.xml";

    LocalDbConfig batisConfig = new LocalDbConfig(file2);

    DBVO vo = batisConfig.getParam();
    if (vo == null) {
      TableBrower.show(this.iProject);
    }
    else
      EclipseUI.showUI(this.iProject);
  }

  public void selectionChanged(IAction action, ISelection selection) {
    StructuredSelection ss = (StructuredSelection)selection;
    if ((ss.getFirstElement() instanceof IProject))
      this.iProject = ((IProject)ss.getFirstElement());
  }

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
  }

  public void handleException(Exception exception, Shell shell)
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

        IbatorUIPlugin.getDefault().getLog().log(status);
      }
    }
    ErrorDialog.openError(shell, "Ibator for iBATIS", "Generation Failed", 
      status, 12);
  }

  public class IbatorRunner implements IRunnableWithProgress {
    private List<String> warnings;

    public IbatorRunner(List<String> warnings) {
      this.warnings = warnings;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
    {
      try {
        RunIbatorThread thread = new RunIbatorThread(RunAction.this.iProject, this.warnings);

        ResourcesPlugin.getWorkspace().run(thread, monitor);
      } catch (CoreException e) {
        throw new InvocationTargetException(e);
      }
    }
  }
}