package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.DeleteByExampleMethodGenerator;

public class AnnotatedDeleteByExampleMethodGenerator extends DeleteByExampleMethodGenerator
{
  public void addMapperAnnotations(Interface interfaze, Method method)
  {
    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.DeleteProvider"));
    StringBuilder sb = new StringBuilder();
    sb.append("@DeleteProvider(type=");
    sb.append(fqjt.getShortName());
    sb.append(".class, method=\"");
    sb.append(this.introspectedTable.getDeleteByExampleStatementId());
    sb.append("\")");

    method.addAnnotation(sb.toString());
  }
}