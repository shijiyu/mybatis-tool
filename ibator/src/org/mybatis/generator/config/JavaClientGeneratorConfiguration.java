package org.mybatis.generator.config;

import java.util.List;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class JavaClientGeneratorConfiguration extends TypedPropertyHolder
{
  private String targetPackage;
  private String implementationPackage;
  private String targetProject;

  public String getTargetProject()
  {
    return this.targetProject;
  }

  public void setTargetProject(String targetProject) {
    this.targetProject = targetProject;
  }

  public String getTargetPackage() {
    return this.targetPackage;
  }

  public void setTargetPackage(String targetPackage) {
    this.targetPackage = targetPackage;
  }

  public XmlElement toXmlElement() {
    XmlElement answer = new XmlElement("javaClientGenerator");
    if (getConfigurationType() != null) {
      answer.addAttribute(new Attribute("type", getConfigurationType()));
    }

    if (this.targetPackage != null) {
      answer.addAttribute(new Attribute("targetPackage", this.targetPackage));
    }

    if (this.targetProject != null) {
      answer.addAttribute(new Attribute("targetProject", this.targetProject));
    }

    if (this.implementationPackage != null) {
      answer.addAttribute(new Attribute(
        "implementationPackage", this.targetProject));
    }

    addPropertyXmlElements(answer);

    return answer;
  }

  public String getImplementationPackage() {
    return this.implementationPackage;
  }

  public void setImplementationPackage(String implementationPackage) {
    this.implementationPackage = implementationPackage;
  }

  public void validate(List<String> errors, String contextId) {
    if (!StringUtility.stringHasValue(this.targetProject)) {
      errors.add(Messages.getString("ValidationError.2", contextId));
    }

    if (!StringUtility.stringHasValue(this.targetPackage)) {
      errors.add(
        Messages.getString("ValidationError.12", 
        "javaClientGenerator", contextId));
    }

    if (!StringUtility.stringHasValue(getConfigurationType()))
      errors.add(
        Messages.getString("ValidationError.20", 
        contextId));
  }
}