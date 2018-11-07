package org.apache.ibatis.ibator.eclipse.ui.content;

import org.eclipse.core.resources.IFile;

public class IbatorConfigurationFileAdapter
{
  private IFile baseFile;

  public IbatorConfigurationFileAdapter(IFile baseFile)
  {
    this.baseFile = baseFile;
  }

  public IFile getBaseFile() {
    return this.baseFile;
  }
}