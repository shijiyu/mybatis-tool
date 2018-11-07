package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import ibator.Globar;

public class UpdateByExampleWithBLOBsMethodGenerator extends AbstractJavaMapperMethodGenerator
{
  public void addInterfaceElements(Interface interfaze)
  {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName(
      this.introspectedTable.getUpdateByExampleWithBLOBsStatementId());
    FullyQualifiedJavaType parameterType;
    if (this.introspectedTable.getRules().generateRecordWithBLOBsClass())
      parameterType = new FullyQualifiedJavaType(
        this.introspectedTable.getRecordWithBLOBsType());
    else {
      parameterType = new FullyQualifiedJavaType(
        this.introspectedTable.getBaseRecordType());
    }
    method.addParameter(new Parameter(parameterType, 
      "record", "@Param(\"record\")"));
    importedTypes.add(parameterType);

    FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(
      this.introspectedTable.getExampleType());
 // FIXME sjy替换成统一examleName 
    method.addParameter(new Parameter(exampleType, 
    		Globar.exampleName.toLowerCase(), "@Param(\""+Globar.exampleName.toLowerCase()+"\")"));
    importedTypes.add(exampleType);

    importedTypes.add(new FullyQualifiedJavaType(
      "org.apache.ibatis.annotations.Param"));

    this.context.getCommentGenerator().addGeneralMethodComment(method, 
      this.introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (this.context.getPlugins()
      .clientUpdateByExampleWithBLOBsMethodGenerated(method, interfaze, 
      this.introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method)
  {
  }
}