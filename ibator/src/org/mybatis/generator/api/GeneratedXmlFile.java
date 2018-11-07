package org.mybatis.generator.api;

import org.mybatis.generator.api.dom.xml.Document;

public class GeneratedXmlFile extends GeneratedFile
{
  private Document document;
  private String fileName;
  private String targetPackage;
  private boolean isMergeable;

  public GeneratedXmlFile(Document document, String fileName, String targetPackage, String targetProject, boolean isMergeable)
  {
    super(targetProject);
    this.document = document;
    this.fileName = fileName;
    this.targetPackage = targetPackage;
    this.isMergeable = isMergeable;
  }

  public String getFormattedContent()
  {
    return this.document.getFormattedContent();
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public String getTargetPackage()
  {
    return this.targetPackage;
  }

  public boolean isMergeable()
  {
    return this.isMergeable;
  }
}