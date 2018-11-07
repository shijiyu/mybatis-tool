package org.mybatis.generator.api;

import java.io.File;
import org.mybatis.generator.exception.ShellException;

public abstract interface ShellCallback
{
  public abstract File getDirectory(String paramString1, String paramString2)
    throws ShellException;

  public abstract String mergeJavaFile(String paramString1, String paramString2, String[] paramArrayOfString)
    throws ShellException;

  public abstract void refreshProject(String paramString);

  public abstract boolean isMergeSupported();

  public abstract boolean isOverwriteEnabled();
}