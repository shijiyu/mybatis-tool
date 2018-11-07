package org.apache.ibatis.ibator.eclipse.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ClasspathVariableInitializer;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.osgi.framework.Bundle;

public class IbatorClasspathVariableInitializer extends ClasspathVariableInitializer
{
  public static final String IBATOR_JAR = "IBATOR_JAR";
  public static final String IBATOR_JAR_SRC = "IBATOR_JAR_SRC";

  public void initialize(String variable)
  {
    if ("IBATOR_JAR".equals(variable))
      initializeIbatorJar();
    else if ("IBATOR_JAR_SRC".equals(variable))
      initializeIbatorJarSource();
  }

  private void initializeIbatorJar()
  {
    Bundle bundle = Platform.getBundle("ibator");
    if (bundle == null) {
      JavaCore.removeClasspathVariable("IBATOR_JAR", null);
      return;
    }
    URL installLocation = bundle.getEntry("/resource/lib/ibatis/ibatis-2.3.4.726.jar");
    URL local = null;
    try {
      local = FileLocator.toFileURL(installLocation);
    } catch (IOException e) {
      JavaCore.removeClasspathVariable("IBATOR_JAR", null);
      return;
    }
    try {
      String fullPath = new File(local.getPath()).getAbsolutePath();
      JavaCore.setClasspathVariable("IBATOR_JAR", new Path(fullPath), null);
    } catch (JavaModelException e1) {
      JavaCore.removeClasspathVariable("IBATOR_JAR", null);
    }
  }

  private void initializeIbatorJarSource() {
    Bundle bundle = Platform.getBundle("org.apache.ibatis.ibator.core");
    if (bundle == null) {
      JavaCore.removeClasspathVariable("IBATOR_JAR_SRC", null);
      return;
    }
    URL installLocation = bundle.getEntry("/ibator-src.zip");
    URL local = null;
    try {
      local = FileLocator.toFileURL(installLocation);
    } catch (IOException e) {
      JavaCore.removeClasspathVariable("IBATOR_JAR_SRC", null);
      return;
    }
    try {
      String fullPath = new File(local.getPath()).getAbsolutePath();
      JavaCore.setClasspathVariable("IBATOR_JAR_SRC", new Path(fullPath), null);
    } catch (JavaModelException e1) {
      JavaCore.removeClasspathVariable("IBATOR_JAR_SRC", null);
    }
  }
}