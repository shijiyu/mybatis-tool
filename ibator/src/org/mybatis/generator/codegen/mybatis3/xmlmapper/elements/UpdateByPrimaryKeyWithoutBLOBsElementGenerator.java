package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class UpdateByPrimaryKeyWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("update");

    answer.addAttribute(new Attribute(
      "id", this.introspectedTable.getUpdateByPrimaryKeyStatementId()));
    answer.addAttribute(new Attribute("parameterType", 
      this.introspectedTable.getBaseRecordType()));

    this.context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("update ");
    sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));

    sb.setLength(0);
    sb.append("set ");

    Iterator<?> iter = this.introspectedTable.getBaseColumns()
      .iterator();
    while (iter.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();

      sb.append(
        MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
      sb.append(" = ");
      sb.append(
        MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

      if (iter.hasNext()) {
        sb.append(',');
      }

      answer.addElement(new TextElement(sb.toString()));

      if (iter.hasNext()) {
        sb.setLength(0);
        OutputUtilities.xmlIndent(sb, 1);
      }
    }

    boolean and = false;

    Iterator<?> localIterator1 = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator1.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator1.next();
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
      .sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(answer, 
      this.introspectedTable))
      parentElement.addElement(answer);
  }
}