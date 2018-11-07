package org.mybatis.generator.config;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class Configuration
{
  private List<Context> contexts;
  private List<String> classPathEntries;

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    for (String s : getClassPathEntries()) {
      sb.append(s).append("*");
    }

    Context c = getContext("ibator");
    sb.append(c.getJavaModelGeneratorConfiguration());
    sb.append(c.getJavaClientGeneratorConfiguration());
    sb.append(c.getJdbcConnectionConfiguration().getConnectionURL());

    return sb.toString();
  }

  public Configuration()
  {
    this.contexts = new ArrayList<>();
    this.classPathEntries = new ArrayList<>();
  }

  public void addClasspathEntry(String entry) {
    this.classPathEntries.add(entry);
  }

  public List<String> getClassPathEntries()
  {
    return this.classPathEntries;
  }

  public void validate()
    throws InvalidConfigurationException
  {
    List<String> errors = new ArrayList<>();

    for (String classPathEntry : this.classPathEntries) {
      if (!StringUtility.stringHasValue(classPathEntry)) {
        errors.add(Messages.getString("ValidationError.19"));

        break;
      }
    }

    if (this.contexts.size() == 0)
      errors.add(Messages.getString("ValidationError.11"));
    else {
      for (Context context : this.contexts) {
        context.validate(errors);
      }
    }

    if (errors.size() > 0)
      throw new InvalidConfigurationException(errors);
  }

  public List<Context> getContexts()
  {
    return this.contexts;
  }

  public void addContext(Context context) {
    this.contexts.add(context);
  }

  public Context getContext(String id) {
    for (Context context : this.contexts) {
      if (id.equals(context.getId())) {
        return context;
      }
    }

    return null;
  }

  public Document toDocument()
  {
    Document document = new Document(
      "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN", 
      "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
    XmlElement rootElement = new XmlElement("generatorConfiguration");
    document.setRootElement(rootElement);

    for (String classPathEntry : this.classPathEntries) {
      XmlElement cpeElement = new XmlElement("classPathEntry");
      cpeElement.addAttribute(new Attribute("location", classPathEntry));
      rootElement.addElement(cpeElement);
    }

    for (Context context : this.contexts) {
      rootElement.addElement(context.toXmlElement());
    }

    return document;
  }
}