package ibator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import ibator.Activator;

public class Util
{
  public static void delFile(IProject project, String file)
  {
    IFile file2 = project.getFile(file);
    if (file2.exists())
      try {
        file2.delete(true, null);
      }
      catch (CoreException e) {
        e.printStackTrace();
        handleException(e);
      }
  }

  private static void copyNormalFile(String src, String des, IProject project)
  {
    int pos = src.lastIndexOf("/");

    String jarfile = src.substring(pos + 1);
    //IJavaProject iJavaProject = JavaCore.create(project);
    URL url = Activator.getDefault().getBundle().getEntry(src);

    Activator.getDefault().getLog().log(
      new Status(4, "ibator", 0, "path:" + 
      url.getPath(), null));
    Activator.getDefault().getLog().log(
      new Status(4, "ibator", 0, "file:" + 
      url.getFile(), null));

    des = des + "/" + jarfile;
    copyURLToFile(url, project, des);
  }

  public static void copyJarToClassPath2(String path, String despath, IProject project)
  {
    //IFile dir = project.getFile(despath);
    if (!path.toLowerCase().endsWith(".jar")) return;
    int pos = path.lastIndexOf("/");
    String jarfile = path.substring(pos + 1);
    String des = despath + "/" + jarfile;
    IJavaProject iJavaProject = JavaCore.create(project);

    IClasspathEntry[] oldPaths = iJavaProject.readRawClasspath();

    IClasspathEntry checkPaths = JavaCore.newLibraryEntry(project.getFile(
      des).getFullPath(), null, null, false);
    if (classPathExists(oldPaths, checkPaths))
    {
      return;
    }

    IClasspathEntry[] newPaths = new IClasspathEntry[oldPaths.length + 1];

    System.arraycopy(oldPaths, 0, newPaths, 0, oldPaths.length);
    IFolder iFolder = project.getFolder(despath);
    if (!iFolder.exists()) {
      try {
        iFolder.create(true, true, null);
      }
      catch (CoreException e) {
        e.printStackTrace();
      }
    }
    IFile file = project.getFile(des);
    try {
      if (file.exists()) {
        file.delete(true, null);
      }
      if (!file.exists()) {
        File srcFile = new File(path);
        InputStream inStream = new FileInputStream(srcFile);
        file.create(inStream, true, null);
        file.setCharset("utf-8", null);
      }
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }

    newPaths[oldPaths.length] = checkPaths;
    try {
      iJavaProject.setRawClasspath(newPaths, null);
    } catch (JavaModelException e) {
      e.printStackTrace();
      handleException(e);
    }
  }

  private static void copyJarToClassPath(String path, String despath, IProject project)
  {
    //IFile dir = project.getFile(despath);
    if (!path.toLowerCase().endsWith(".jar")) return;
    int pos = path.lastIndexOf("/");
    String jarfile = path.substring(pos + 1);
    String des = despath + "/" + jarfile;
    IJavaProject iJavaProject = JavaCore.create(project);

    IClasspathEntry[] oldPaths = iJavaProject.readRawClasspath();

    IClasspathEntry checkPaths = JavaCore.newLibraryEntry(project.getFile(
      des).getFullPath(), null, null, false);
    if (classPathExists(oldPaths, checkPaths))
    {
      return;
    }

    URL url = Activator.getDefault().getBundle().getEntry(path);

    IClasspathEntry[] newPaths = new IClasspathEntry[oldPaths.length + 1];

    System.arraycopy(oldPaths, 0, newPaths, 0, oldPaths.length);
    IFolder iFolder = project.getFolder(despath);
    if (!iFolder.exists()) {
      try {
        iFolder.create(true, true, null);
      }
      catch (CoreException e) {
        e.printStackTrace();
      }
    }
    copyURLToFile(url, project, des);
    newPaths[oldPaths.length] = checkPaths;
    try {
      iJavaProject.setRawClasspath(newPaths, null);
    } catch (JavaModelException e) {
      e.printStackTrace();
      handleException(e);
    }
  }

  private static boolean classPathExists(IClasspathEntry[] oldPaths, IClasspathEntry newLibEntry)
  {
    if (oldPaths != null) {
      for (int i = 0; i < oldPaths.length; i++) {
        if (oldPaths[i].getPath().equals(newLibEntry.getPath())) {
          return true;
        }
      }
    }
    return false;
  }

  public static IFolder createFolder(IProject project, String f)
  {
    IFolder libFolder = project.getFolder(f);
    if (!libFolder.exists()) {
      try {
        libFolder.create(true, true, null);
      } catch (CoreException e) {
        e.printStackTrace();
      }
    }
    return libFolder;
  }

  private static void copyURLToFile(URL url, IProject project, String destFileName)
  {
    InputStream inStream = null;
    try {
      inStream = url.openStream();
      IFile file = project.getFile(destFileName);
      if ((file.exists()) && (destFileName.indexOf(".jar") < 0)) {
        file.delete(true, null);
      }
      if (!file.exists()) {
        file.create(inStream, true, null);
        file.setCharset("utf-8", null);
      }
    }
    catch (IOException e) {
      handleException(e);
    }
    catch (CoreException e2)
    {
      handleException(e2);
    }
    finally
    {
      try
      {
        if (inStream != null)
          inStream.close();
      } catch (IOException e) {
        handleException(e);
      }
    }
  }

  private static void handleException(Exception e)
  {
    e.printStackTrace();
    Activator.getDefault().getLog().log(
      new Status(4, "ibator", 0, 
      e.getMessage(), e));
  }

  protected static List<String> getAllFile(String path)
  {
    List<String> lst = new ArrayList<>();
    File file = new File(path);

    if ((file.exists()) && (file.isFile())) {
      String ff = file.getAbsolutePath();
      ff = ff.replace('\\', '/');
      ff = ff.replaceAll(getRoot(), "");
      lst.add(ff);
    } else if (file.exists()) {
      File[] files = file.listFiles();
      for (File f : files) {
        if (f.isFile()) {
          String ff = f.getAbsolutePath();

          ff = ff.replace('\\', '/');

          lst.add(ff);
        } else {
          getAllFile(f.getAbsolutePath());
        }
      }
    }

    return lst;
  }

  public static String getRoot()
  {
    String root = "";
    root = Activator.getDefault().getBundle().getLocation();
    int pos = root.indexOf("@");
    root = root.substring(pos + 1);
    Activator.getDefault().getLog().log(
      new Status(4, "ibator", 0, root, null));

    return root;
  }

  public static void copyFile(String file, String des, boolean b, IProject iProject)
  {
    boolean dir = false;
    try
    {
      if (file.endsWith(".jar"))
        copyJarToClassPath(file, des, iProject);
      else if ((file.endsWith(".properties")) || (file.endsWith(".java")))
        copyNormalFile(file, des, iProject);
      else
        dir = true;
    }
    catch (Exception e) {
      dir = true;
    }

    if (dir)
    {
      Enumeration<?> enumeration = Activator.getDefault()
        .getBundle().getEntryPaths(file);
      if (enumeration != null)
        while (enumeration.hasMoreElements()) {
          String url2 = (String)enumeration.nextElement();

          if ((url2.lastIndexOf(".jar") > 0) && (b))
            copyJarToClassPath(url2, des, iProject);
          else
            copyNormalFile(url2, des, iProject);
        }
    }
  }

  public static IProject getProject(ISelection selection)
  {
    IProject project = null;
    if ((selection instanceof IStructuredSelection))
    {
      IStructuredSelection structuredSelect = (IStructuredSelection)selection;
      if (!structuredSelect.isEmpty()) {
        Object obj = structuredSelect.getFirstElement();
        if ((obj instanceof IJavaProject)) {
          //IJavaProject iJavaProject = (IJavaProject)obj;
          project = ((IJavaProject)obj).getProject();
        }
        else if ((obj instanceof IProject)) {
          project = (IProject)obj;
        }
      }
    }

    return project;
  }
}