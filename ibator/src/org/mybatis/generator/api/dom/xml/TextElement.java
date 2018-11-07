package org.mybatis.generator.api.dom.xml;

import org.mybatis.generator.api.dom.OutputUtilities;

public class TextElement extends Element
{
  private String content;

  public TextElement(String content)
  {
    this.content = content;
  }

  public String getFormattedContent(int indentLevel)
  {
    StringBuilder sb = new StringBuilder();
    OutputUtilities.xmlIndent(sb, indentLevel);
    sb.append(this.content);
    return sb.toString();
  }
}