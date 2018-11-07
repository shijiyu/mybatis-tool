package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CountByExampleElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("select");

    String fqjt = this.introspectedTable.getExampleType();

    answer.addAttribute(new Attribute(
      "id", this.introspectedTable.getCountByExampleStatementId()));
    answer.addAttribute(new Attribute("parameterType", fqjt));
    answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));

    this.context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("select count(*) from ");
    sb.append(
      this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));
    answer.addElement(getExampleIncludeElement());

    if (this.context.getPlugins().sqlMapCountByExampleElementGenerated(
      answer, this.introspectedTable))
      parentElement.addElement(answer);
  }
}