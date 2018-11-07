package org.apache.ibatis.ibator.eclipse.ui.content;

import org.eclipse.jdt.core.IJavaProject;

public class JavaProjectAdapter
{
  private IJavaProject javaProject;

  public JavaProjectAdapter(IJavaProject javaProject)
  {
    this.javaProject = javaProject;
  }

  public IJavaProject getJavaProject() {
    return this.javaProject;
  }
}