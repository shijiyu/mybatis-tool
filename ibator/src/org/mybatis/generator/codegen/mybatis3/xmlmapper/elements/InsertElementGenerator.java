package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import ibator.util.DBUtil;

public class InsertElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("insert");

    answer.addAttribute(new Attribute(
      "id", this.introspectedTable.getInsertStatementId()));

    FullyQualifiedJavaType parameterType = this.introspectedTable.getRules()
      .calculateAllFieldsClass();

    answer.addAttribute(new Attribute("parameterType", 
      parameterType.getFullyQualifiedName()));

    this.context.getCommentGenerator().addComment(answer);

    List<?> list = DBUtil.getPrimarykey(this.introspectedTable
      .getFullyQualifiedTable()
      .getIntrospectedTableName());
    String gk = null;
    if ((list != null) && (list.size() == 1)) {
      gk = (String)list.get(0);
    }
    if (gk != null) {
      IntrospectedColumn introspectedColumn = this.introspectedTable
        .getColumn(gk);

      if ((introspectedColumn != null) && 
        (introspectedColumn.getActualColumnName().equalsIgnoreCase(
        DBUtil.getIdentity(this.introspectedTable
        .getFullyQualifiedTable()
        .getIntrospectedTableName()))))
      {
        answer.addAttribute(new Attribute(
          "useGeneratedKeys", "true"));
        answer
          .addAttribute(new Attribute(
          "keyProperty", introspectedColumn.getJavaProperty()));
      }

    }

    StringBuilder insertClause = new StringBuilder();
    StringBuilder valuesClause = new StringBuilder();

    insertClause.append("insert into ");
    insertClause.append(
      this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
    insertClause.append(" (");

    valuesClause.append("values (");

    List<String> valuesClauses = new ArrayList<>();
    Iterator<?> iter = this.introspectedTable.getAllColumns()
      .iterator();

    while (iter.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();

      if (introspectedColumn.isIdentity()) {
        introspectedColumn.setIdentity(true);
      }
      else
      {
        insertClause.append(
          MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        valuesClause.append(
          MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

        if (iter.hasNext()) {
          insertClause.append(", ");
          valuesClause.append(", ");
        }

        if (valuesClause.length() > 80) {
          answer.addElement(new TextElement(insertClause.toString()));
          insertClause.setLength(0);
          OutputUtilities.xmlIndent(insertClause, 1);

          valuesClauses.add(valuesClause.toString());
          valuesClause.setLength(0);
          OutputUtilities.xmlIndent(valuesClause, 1);
        }
      }
    }
    insertClause.append(')');
    answer.addElement(new TextElement(insertClause.toString()));

    valuesClause.append(')');
    valuesClauses.add(valuesClause.toString());

    for (String clause : valuesClauses) {
      answer.addElement(new TextElement(clause));
    }

    if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, 
      this.introspectedTable))
      parentElement.addElement(answer);
  }
}