package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.ibator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import ibator.Globar;
import ibator.util.DBUtil;

public class InsertSelectiveElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("insert");

    answer.addAttribute(new Attribute(
      "id", this.introspectedTable.getInsertSelectiveStatementId()));

    FullyQualifiedJavaType parameterType = this.introspectedTable.getRules()
      .calculateAllFieldsClass();

    answer.addAttribute(new Attribute("parameterType", 
      parameterType.getFullyQualifiedName()));

    this.context.getCommentGenerator().addComment(answer);

    List<String> list = DBUtil.getPrimarykey(this.introspectedTable
      .getFullyQualifiedTable().getIntrospectedTableName());
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

    StringBuilder sb = new StringBuilder();

    sb.append("insert into ");
    sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));

    XmlElement insertTrimElement = new XmlElement("trim");
    insertTrimElement.addAttribute(new Attribute("prefix", "("));
    insertTrimElement.addAttribute(new Attribute("suffix", ")"));
    insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
    answer.addElement(insertTrimElement);

    XmlElement valuesTrimElement = new XmlElement("trim");
    valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
    valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
    valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
    answer.addElement(valuesTrimElement);
    JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());

    Iterator<?> localIterator = this.introspectedTable
      .getAllColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
      if (introspectedColumn.isIdentity()) {
        introspectedColumn.setIdentity(true);
      }
      else if ((introspectedColumn.isSequenceColumn()) || 
        (introspectedColumn.getFullyQualifiedJavaType()
        .isPrimitive()))
      {
        sb.setLength(0);
        sb.append(
          MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        sb.append(',');
        insertTrimElement.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append(
          MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
        sb.append(',');
        valuesTrimElement.addElement(new TextElement(sb.toString()));
      }
      else
      {
        XmlElement insertNotNullElement = new XmlElement("if");
        sb.setLength(0);
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(" != null");
        insertNotNullElement.addAttribute(new Attribute(
          "test", sb.toString()));

        sb.setLength(0);
        sb.append(
          MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        sb.append(',');
        insertNotNullElement.addElement(new TextElement(sb.toString()));
        insertTrimElement.addElement(insertNotNullElement);

        XmlElement valuesNotNullElement = new XmlElement("if");
        sb.setLength(0);
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(" != null");
        valuesNotNullElement.addAttribute(new Attribute(
          "test", sb.toString()));

        sb.setLength(0);
        sb.append(
          MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
        sb.append(',');
        valuesNotNullElement.addElement(new TextElement(sb.toString()));
        valuesTrimElement.addElement(valuesNotNullElement);
      }
    }
    if (this.context.getPlugins().sqlMapInsertSelectiveElementGenerated(answer, 
      this.introspectedTable))
      parentElement.addElement(answer);
  }
}