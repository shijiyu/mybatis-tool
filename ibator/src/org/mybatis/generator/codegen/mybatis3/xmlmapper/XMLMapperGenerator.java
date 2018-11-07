package org.mybatis.generator.codegen.mybatis3.xmlmapper;

import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BlobColumnListElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.CountByExampleElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByExampleElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeyElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ExampleWhereClauseElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertSelectiveElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithoutBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithBLOBsElementGenerator2;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithoutBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithoutBLOBsElementGenerator2;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPrimaryKeyElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByExampleSelectiveElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByExampleWithBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByExampleWithoutBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeyWithBLOBsElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeyWithoutBLOBsElementGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import ibator.Globar;

public class XMLMapperGenerator extends AbstractXmlGenerator
{
  protected XmlElement getSqlMapElement()
  {
    FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
    this.progressCallback.startTask(Messages.getString("Progress.12", table.toString()));
    XmlElement answer = new XmlElement("mapper");
    String namespace = this.introspectedTable.getMyBatis3JavaMapperType();
    if (namespace == null)
    {
      namespace = this.introspectedTable.getIbatis2SqlMapNamespace();
    }
    answer.addAttribute(new Attribute("namespace", 
      namespace));

    this.context.getCommentGenerator().addRootComment(answer);

    addCacheElement(answer);

    addResultMapWithoutBLOBsElement(answer);
    addResultMapWithBLOBsElement(answer);

    addBaseColumnListElement(answer);
    addBlobColumnListElement(answer);

    if (!Globar.ISSIMPLE) {
      addExampleWhereClauseElement(answer);
      addMyBatis3UpdateByExampleWhereClauseElement(answer);
      addSelectByExampleWithBLOBsElement(answer);
      addSelectByExampleWithoutBLOBsElement(answer);
      addSelectByExampleWithBLOBsElement2(answer);
      addSelectByExampleWithoutBLOBsElement2(answer);
      addDeleteByExampleElement(answer);
      addCountByExampleElement(answer);
      addUpdateByExampleSelectiveElement(answer);
      addUpdateByExampleWithBLOBsElement(answer);
      addUpdateByExampleWithoutBLOBsElement(answer);
    }

    addSelectByPrimaryKeyElement(answer);
    addDeleteByPrimaryKeyElement(answer);

    addInsertElement(answer);
    addInsertSelectiveElement(answer);
    addUpdateByPrimaryKeySelectiveElement(answer);
    addUpdateByPrimaryKeyWithBLOBsElement(answer);
    addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

    addFlushCach(answer);
    return answer;
  }

  protected void addCacheElement(XmlElement parentElement)
  {
    if ((Globar.isCache) && 
      (!Globar.global.getDaoType().equalsIgnoreCase("annotation"))) {
      XmlElement element = new XmlElement("cache");
      element.addAttribute(new Attribute("type", 
        "org.mybatis.caches.ehcache.EhcacheCache"));
      parentElement.addElement(element);
    }
  }

  protected void addFlushCach(XmlElement parentElement)
  {
    if ((Globar.isCache) && 
      (!Globar.global.getDaoType().equalsIgnoreCase("annotation"))) {
      List<Element> list = parentElement.getElements();
      for (Element element : list)
        if ((element instanceof XmlElement)) {
          XmlElement xmlElement = (XmlElement)element;
          String name = xmlElement.getName();
          if ((name.startsWith("insert")) || (name.startsWith("update")) || (name.startsWith("delete")))
            xmlElement.addAttribute(new Attribute("flushCache", "true"));
        }
    }
  }

  protected void addResultMapWithoutBLOBsElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addResultMapWithBLOBsElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateResultMapWithBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new ResultMapWithBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addExampleWhereClauseElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateSQLExampleWhereClause()) {
      AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(
        false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addMyBatis3UpdateByExampleWhereClauseElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules()
      .generateMyBatis3UpdateByExampleWhereClause()) {
      AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(
        true);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addBaseColumnListElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateBaseColumnList()) {
      AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addBlobColumnListElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateBlobColumnList()) {
      AbstractXmlElementGenerator elementGenerator = new BlobColumnListElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByExampleWithoutBLOBsElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithoutBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByExampleWithBLOBsElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByExampleWithoutBLOBsElement2(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithoutBLOBsElementGenerator2();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByExampleWithBLOBsElement2(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithBLOBsElementGenerator2();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateSelectByPrimaryKey()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addDeleteByExampleElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateDeleteByExample()) {
      AbstractXmlElementGenerator elementGenerator = new DeleteByExampleElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateDeleteByPrimaryKey()) {
      AbstractXmlElementGenerator elementGenerator = new DeleteByPrimaryKeyElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addInsertElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateInsert()) {
      AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addInsertSelectiveElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateInsertSelective()) {
      AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addCountByExampleElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateCountByExample()) {
      AbstractXmlElementGenerator elementGenerator = new CountByExampleElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByExampleSelectiveElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateUpdateByExampleSelective()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByExampleSelectiveElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByExampleWithBLOBsElement(XmlElement parentElement) {
    if (this.introspectedTable.getRules().generateUpdateByExampleWithBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByExampleWithoutBLOBsElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithoutBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeySelectiveElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByPrimaryKeyWithBLOBsElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addUpdateByPrimaryKeyWithoutBLOBsElement(XmlElement parentElement)
  {
    if (this.introspectedTable.getRules()
      .generateUpdateByPrimaryKeyWithoutBLOBs()) {
      AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithoutBLOBsElementGenerator();
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement)
  {
    elementGenerator.setContext(this.context);
    elementGenerator.setIntrospectedTable(this.introspectedTable);
    elementGenerator.setProgressCallback(this.progressCallback);
    elementGenerator.setWarnings(this.warnings);
    elementGenerator.addElements(parentElement);
  }

  public Document getDocument()
  {
    Document document = new Document(
      "-//mybatis.org//DTD Mapper 3.0//EN", 
      "mybatis-3-mapper.dtd");
    document.setRootElement(getSqlMapElement());

    if (!this.context.getPlugins().sqlMapDocumentGenerated(document, 
      this.introspectedTable)) {
      document = null;
    }

    return document;
  }
}