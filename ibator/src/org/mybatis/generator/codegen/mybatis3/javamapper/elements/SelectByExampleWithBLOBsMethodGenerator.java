package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import ibator.Globar;

public class SelectByExampleWithBLOBsMethodGenerator extends AbstractJavaMapperMethodGenerator
{
  public void addInterfaceElements(Interface interfaze)
  {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
    FullyQualifiedJavaType type = new FullyQualifiedJavaType(
      this.introspectedTable.getExampleType());
    importedTypes.add(type);
    importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);

    FullyQualifiedJavaType returnType = 
      FullyQualifiedJavaType.getNewListInstance();
    FullyQualifiedJavaType listType;
    if (this.introspectedTable.getRules().generateRecordWithBLOBsClass()) {
      listType = new FullyQualifiedJavaType(
        this.introspectedTable.getRecordWithBLOBsType());
    }
    else {
      listType = new FullyQualifiedJavaType(
        this.introspectedTable.getBaseRecordType());
    }

    importedTypes.add(listType);
    returnType.addTypeArgument(listType);
    method.setReturnType(returnType);
    method.setName(
      this.introspectedTable.getSelectByExampleWithBLOBsStatementId());
    // FIXME sjy替换成统一examleName 
    method.addParameter(new Parameter(type, Globar.exampleName.toLowerCase()));

    this.context.getCommentGenerator().addGeneralMethodComment(method, 
      this.introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (this.context.getPlugins()
      .clientSelectByExampleWithBLOBsMethodGenerated(method, interfaze, 
      this.introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method)
  {
  }
}