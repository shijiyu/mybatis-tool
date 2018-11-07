package org.mybatis.generator.config;

import java.util.List;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class ColumnRenamingRule
{
  private String searchString;
  private String replaceString;

  public String getReplaceString()
  {
    return this.replaceString;
  }

  public void setReplaceString(String replaceString) {
    this.replaceString = replaceString;
  }

  public String getSearchString() {
    return this.searchString;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }

  public void validate(List<String> errors, String tableName) {
    if (!StringUtility.stringHasValue(this.searchString))
      errors.add(Messages.getString("ValidationError.14", tableName));
  }

  public XmlElement toXmlElement()
  {
    XmlElement xmlElement = new XmlElement("columnRenamingRule");
    xmlElement.addAttribute(new Attribute("searchString", this.searchString));

    if (this.replaceString != null) {
      xmlElement.addAttribute(new Attribute(
        "replaceString", this.replaceString));
    }

    return xmlElement;
  }
}