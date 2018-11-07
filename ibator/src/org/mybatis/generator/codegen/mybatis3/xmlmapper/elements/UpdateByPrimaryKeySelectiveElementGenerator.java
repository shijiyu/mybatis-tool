package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class UpdateByPrimaryKeySelectiveElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("update");

    answer
      .addAttribute(new Attribute(
      "id", this.introspectedTable.getUpdateByPrimaryKeySelectiveStatementId()));
    String parameterType;
    if (this.introspectedTable.getRules().generateRecordWithBLOBsClass())
      parameterType = this.introspectedTable.getRecordWithBLOBsType();
    else {
      parameterType = this.introspectedTable.getBaseRecordType();
    }

    answer.addAttribute(new Attribute("parameterType", 
      parameterType));

    this.context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();

    sb.append("update ");
    sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));

    XmlElement dynamicElement = new XmlElement("set");
    answer.addElement(dynamicElement);

    Iterator<?> localIterator = this.introspectedTable
      .getNonPrimaryKeyColumns().iterator();

   
	while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
      XmlElement isNotNullElement = new XmlElement("if");
      sb.setLength(0);
      sb.append(introspectedColumn.getJavaProperty());
      sb.append(" != null");
      isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
      dynamicElement.addElement(isNotNullElement);

      sb.setLength(0);
      sb.append(
        MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
      sb.append(" = ");
      sb.append(
        MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      sb.append(',');

      isNotNullElement.addElement(new TextElement(sb.toString()));
    }

    boolean and = false;

    Iterator<IntrospectedColumn> isNotNullElement = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (isNotNullElement.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)isNotNullElement.next();
      sb.setLength(0);
      if (and) {
        sb.append("  and ");
      } else {
        sb.append("where ");
        and = true;
      }

      sb.append(
        MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
      sb.append(" = ");
      sb.append(
        MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      answer.addElement(new TextElement(sb.toString()));
    }

    if (this.context.getPlugins()
      .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer, 
      this.introspectedTable))
      parentElement.addElement(answer);
  }
}