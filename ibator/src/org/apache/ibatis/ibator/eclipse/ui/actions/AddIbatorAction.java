package org.apache.ibatis.ibator.eclipse.ui.actions;

import org.apache.ibatis.ibator.eclipse.ui.content.JavaProjectAdapter;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class AddIbatorAction
  implements IObjectActionDelegate
{
  private IJavaProject iJavaProject;

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
  }

  public void run(IAction action)
  {
    IPath jarPath = new Path("IBATOR_JAR");
    IPath srcPath = new Path("IBATOR_JAR_SRC");

    IClasspathEntry newEntry = JavaCore.newVariableEntry(jarPath, srcPath, null);
    try
    {
      IClasspathEntry[] oldClasspath = this.iJavaProject.getRawClasspath();
      IClasspathEntry[] newClasspath = new IClasspathEntry[oldClasspath.length + 1];
      System.arraycopy(oldClasspath, 0, newClasspath, 0, oldClasspath.length);
      newClasspath[oldClasspath.length] = newEntry;
      this.iJavaProject.setRawClasspath(newClasspath, null);
    }
    catch (Exception localException)
    {
    }
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    StructuredSelection ss = (StructuredSelection)selection;
    JavaProjectAdapter project = (JavaProjectAdapter)ss.getFirstElement();
    if (project != null)
      this.iJavaProject = project.getJavaProject();
  }
}