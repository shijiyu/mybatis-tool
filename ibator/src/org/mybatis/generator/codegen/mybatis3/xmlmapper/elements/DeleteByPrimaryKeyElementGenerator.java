package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class DeleteByPrimaryKeyElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("delete");

    answer.addAttribute(new Attribute(
      "id", this.introspectedTable.getDeleteByPrimaryKeyStatementId()));
    String parameterClass;
    if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
      parameterClass = this.introspectedTable.getPrimaryKeyType();
    }
    else
    {
      if (this.introspectedTable.getPrimaryKeyColumns().size() > 1)
        parameterClass = "map";
      else {
        parameterClass = 
          ((IntrospectedColumn)this.introspectedTable.getPrimaryKeyColumns()
          .get(0)).getFullyQualifiedJavaType().toString();
      }
    }
    answer.addAttribute(new Attribute("parameterType", 
      parameterClass));

    this.context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("delete from ");
    sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));

    boolean and = false;

    Iterator<?> localIterator = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
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
      .sqlMapDeleteByPrimaryKeyElementGenerated(answer, 
      this.introspectedTable))
      parentElement.addElement(answer);
  }
}