package org.mybatis.generator.config;

import java.util.List;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class PluginConfiguration extends TypedPropertyHolder
{
  public XmlElement toXmlElement()
  {
    XmlElement answer = new XmlElement("plugin");
    if (getConfigurationType() != null) {
      answer.addAttribute(new Attribute("type", getConfigurationType()));
    }

    addPropertyXmlElements(answer);

    return answer;
  }

  public void validate(List<String> errors, String contextId) {
    if (!StringUtility.stringHasValue(getConfigurationType()))
      errors.add(
        Messages.getString("ValidationError.17", 
        contextId));
  }
}