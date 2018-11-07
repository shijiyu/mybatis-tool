package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import ibator.Globar;
import ibator.util.DBUtil;
import ibator.util.TableVo;

public class ResultMapWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator
{
  public void addSelectByFk(XmlElement parentElement)
  {
    StringBuffer sb = new StringBuffer();
    new DBUtil(); 
    /*TableVo vo =*/ DBUtil.getKeys(this.introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
    if (TableVo.isFk())
      for (TableVo.KeyInfo info : TableVo.getFkeys()) {
        XmlElement select = new XmlElement("select");
        //String tableString = info.getPktablename();
        String columnString = info.getFkname();
        select.addAttribute(new Attribute("id", "selectBy" + StringUtility.getTable(columnString)));
        select.addAttribute(new Attribute("resultMap", this.introspectedTable.getBaseResultMapId()));

        sb.setLength(0);
        sb.append("select <include refid=\"Base_Column_List\" /> from ")
          .append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime())
          .append(" where ")
          .append(columnString)
          .append(" =#{")
          .append(columnString)
          .append("}");
        select.addElement(new TextElement(sb.toString()));
        parentElement.addElement(select);
      }
  }

  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("resultMap");
    answer.addAttribute(new Attribute("id", 
      this.introspectedTable.getBaseResultMapId()));
    String returnType;
    if (this.introspectedTable.getRules().generateBaseRecordClass())
      returnType = this.introspectedTable.getBaseRecordType();
    else {
      returnType = this.introspectedTable.getPrimaryKeyType();
    }

    answer.addAttribute(new Attribute("type", 
      returnType));

    this.context.getCommentGenerator().addComment(answer);

    if (this.introspectedTable.isConstructorBased())
      addResultMapConstructorElements(answer);
    else {
      addResultMapElements(answer);
    }

    if (this.context.getPlugins().sqlMapResultMapWithoutBLOBsElementGenerated(
      answer, this.introspectedTable)) {
      parentElement.addElement(answer);
    }

    addSelectByFk(parentElement);
  }

  private void addResultMapElements(XmlElement answer)
  {
    Iterator<?> localIterator1 = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator1.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator1.next();
      XmlElement resultElement = new XmlElement("id");

      resultElement
        .addAttribute(new Attribute(
        "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
      resultElement.addAttribute(new Attribute(
        "property", introspectedColumn.getJavaProperty()));
      resultElement.addAttribute(new Attribute("jdbcType", 
        introspectedColumn.getJdbcTypeName()));

      if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
        resultElement.addAttribute(new Attribute(
          "typeHandler", introspectedColumn.getTypeHandler()));
      }

      answer.addElement(resultElement);
    }

    localIterator1 = this.introspectedTable
      .getBaseColumns().iterator();

    while (localIterator1.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator1.next();
      XmlElement resultElement = new XmlElement("result");

      resultElement
        .addAttribute(new Attribute(
        "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
      resultElement.addAttribute(new Attribute(
        "property", introspectedColumn.getJavaProperty()));
      resultElement.addAttribute(new Attribute("jdbcType", 
        introspectedColumn.getJdbcTypeName()));

      if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
        resultElement.addAttribute(new Attribute(
          "typeHandler", introspectedColumn.getTypeHandler()));
      }

      answer.addElement(resultElement);
    }

    StringBuffer sb = new StringBuffer();
    new DBUtil(); 
   /* TableVo vo = */DBUtil.getKeys(this.introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
    if (TableVo.isFk()) {
      for (TableVo.KeyInfo info : TableVo.getFkeys()) {
        XmlElement association = new XmlElement("association");
        String tableString = info.getPktablename();
        String columnString = info.getFkname();
        association.addAttribute(new Attribute("property", StringUtility.getColumn(tableString) + StringUtility.getTable(columnString)));
        association.addAttribute(new Attribute("column", StringUtility.getColumn(columnString)));
        sb.setLength(0);
        sb.append(Globar.daoPath).append(".").append(StringUtility.getTable(tableString)).append(Globar.daoName).append(".selectByPrimaryKey");

        association.addAttribute(new Attribute("select", sb.toString()));
        answer.addElement(association);
      }

    }

    if (TableVo.isPk())
      for (TableVo.KeyInfo info : TableVo.getPkeys()) {
        XmlElement collection = new XmlElement("collection");
        String fkTable = info.getFktablename();
        String fkName = info.getFkname();
        String pkName = info.getPkname();

        collection.addAttribute(new Attribute("property", StringUtility.getColumn(fkTable) + "s" + StringUtility.getTable(fkName)));

        if (DBUtil.isUnionKey(fkName))
          fkName = fkName + "Key";
        collection.addAttribute(new Attribute("ofType", Globar.pojoPath + "." + StringUtility.getTable(fkTable)));
        collection.addAttribute(new Attribute("column", pkName));
        collection.addAttribute(new Attribute("select", Globar.daoPath + "." + StringUtility.getTable(fkTable) + Globar.daoName + ".selectBy" + StringUtility.getTable(fkName)));
        answer.addElement(collection);
      }
  }

  private void addResultMapConstructorElements(XmlElement answer)
  {
    XmlElement constructor = new XmlElement("constructor");

    Iterator<?> localIterator = this.introspectedTable
      .getPrimaryKeyColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
      XmlElement resultElement = new XmlElement("idArg");

      resultElement
        .addAttribute(new Attribute(
        "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
      resultElement.addAttribute(new Attribute("jdbcType", 
        introspectedColumn.getJdbcTypeName()));
      resultElement.addAttribute(new Attribute("javaType", 
        introspectedColumn.getFullyQualifiedJavaType()
        .getFullyQualifiedName()));

      if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
        resultElement.addAttribute(new Attribute(
          "typeHandler", introspectedColumn.getTypeHandler()));
      }

      constructor.addElement(resultElement);
    }

    localIterator = this.introspectedTable
      .getBaseColumns().iterator();

    while (localIterator.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator.next();
      XmlElement resultElement = new XmlElement("arg");

      resultElement
        .addAttribute(new Attribute(
        "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
      resultElement.addAttribute(new Attribute("jdbcType", 
        introspectedColumn.getJdbcTypeName()));
      resultElement.addAttribute(new Attribute("javaType", 
        introspectedColumn.getFullyQualifiedJavaType()
        .getFullyQualifiedName()));

      if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
        resultElement.addAttribute(new Attribute(
          "typeHandler", introspectedColumn.getTypeHandler()));
      }

      constructor.addElement(resultElement);
    }

    answer.addElement(constructor);
  }
}