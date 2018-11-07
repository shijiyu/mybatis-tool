package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectByExampleWithBLOBsMethodGenerator;

public class AnnotatedSelectByExampleWithBLOBsMethodGenerator extends SelectByExampleWithBLOBsMethodGenerator
{
  public void addMapperAnnotations(Interface interfaze, Method method)
  {
    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectProvider"));
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));

    if (this.introspectedTable.isConstructorBased()) {
      interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Arg"));
      interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ConstructorArgs"));
    } else {
      interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Result"));
      interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Results"));
    }

    StringBuilder sb = new StringBuilder();
    sb.append("@SelectProvider(type=");
    sb.append(fqjt.getShortName());
    sb.append(".class, method=\"");
    sb.append(this.introspectedTable.getSelectByExampleWithBLOBsStatementId());
    sb.append("\")");

    method.addAnnotation(sb.toString());

    if (this.introspectedTable.isConstructorBased())
      method.addAnnotation("@ConstructorArgs({");
    else {
      method.addAnnotation("@Results({");
    }

    Iterator<?> iterPk = this.introspectedTable.getPrimaryKeyColumns().iterator();
    Iterator<?> iterNonPk = this.introspectedTable.getNonPrimaryKeyColumns().iterator();
    while (iterPk.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iterPk.next();
      sb.setLength(0);
      OutputUtilities.javaIndent(sb, 1);
      sb.append(
        getResultAnnotation(interfaze, introspectedColumn, true, 
        this.introspectedTable.isConstructorBased()));

      if ((iterPk.hasNext()) || (iterNonPk.hasNext())) {
        sb.append(',');
      }

      method.addAnnotation(sb.toString());
    }

    while (iterNonPk.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iterNonPk.next();
      sb.setLength(0);
      OutputUtilities.javaIndent(sb, 1);
      sb.append(
        getResultAnnotation(interfaze, introspectedColumn, false, 
        this.introspectedTable.isConstructorBased()));

      if (iterNonPk.hasNext()) {
        sb.append(',');
      }

      method.addAnnotation(sb.toString());
    }

    method.addAnnotation("})");
  }
}