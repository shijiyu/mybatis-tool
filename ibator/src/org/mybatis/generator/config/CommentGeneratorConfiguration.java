package org.mybatis.generator.config;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CommentGeneratorConfiguration extends TypedPropertyHolder
{
  public XmlElement toXmlElement()
  {
    XmlElement answer = new XmlElement("commentGenerator");
    if (getConfigurationType() != null) {
      answer.addAttribute(new Attribute("type", getConfigurationType()));
    }

    addPropertyXmlElements(answer);

    return answer;
  }
}