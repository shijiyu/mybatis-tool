package org.mybatis.generator.api;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

public abstract interface Plugin
{
  public abstract void setContext(Context paramContext);

  public abstract void setProperties(Properties paramProperties);

  public abstract void initialized(IntrospectedTable paramIntrospectedTable);

  public abstract boolean validate(List<String> paramList);

  public abstract List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles();

  public abstract List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable paramIntrospectedTable);

  public abstract List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles();

  public abstract List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientGenerated(Interface paramInterface, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientCountByExampleMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientDeleteByExampleMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientDeleteByPrimaryKeyMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientInsertMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientInsertSelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByExampleWithBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByPrimaryKeyMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleSelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientCountByExampleMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientDeleteByExampleMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientDeleteByPrimaryKeyMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientInsertMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientInsertSelectiveMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByExampleWithBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientSelectByPrimaryKeyMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleSelectiveMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method paramMethod, Interface paramInterface, IntrospectedTable paramIntrospectedTable);

  public abstract boolean modelFieldGenerated(Field paramField, TopLevelClass paramTopLevelClass, IntrospectedColumn paramIntrospectedColumn, IntrospectedTable paramIntrospectedTable, ModelClassType paramModelClassType);

  public abstract boolean modelGetterMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedColumn paramIntrospectedColumn, IntrospectedTable paramIntrospectedTable, ModelClassType paramModelClassType);

  public abstract boolean modelSetterMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedColumn paramIntrospectedColumn, IntrospectedTable paramIntrospectedTable, ModelClassType paramModelClassType);

  public abstract boolean modelPrimaryKeyClassGenerated(TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean modelBaseRecordClassGenerated(TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean modelRecordWithBLOBsClassGenerated(TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean modelExampleClassGenerated(TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapGenerated(GeneratedXmlFile paramGeneratedXmlFile, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapDocumentGenerated(Document paramDocument, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapCountByExampleElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapDeleteByExampleElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapExampleWhereClauseElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapBaseColumnListElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapBlobColumnListElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapInsertElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapInsertSelectiveElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement paramXmlElement, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerGenerated(TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerApplyWhereMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerCountByExampleMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerDeleteByExampleMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerInsertSelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerSelectByExampleWithBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerUpdateByExampleSelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public abstract boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method paramMethod, TopLevelClass paramTopLevelClass, IntrospectedTable paramIntrospectedTable);

  public static enum ModelClassType
  {
    PRIMARY_KEY, BASE_RECORD, RECORD_WITH_BLOBS;
  }
}