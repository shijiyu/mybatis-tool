package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.InsertSelectiveMethodGenerator;
import org.mybatis.generator.config.GeneratedKey;

public class AnnotatedInsertSelectiveMethodGenerator extends InsertSelectiveMethodGenerator
{
  public void addMapperAnnotations(Interface interfaze, Method method)
  {
    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.InsertProvider"));
    StringBuilder sb = new StringBuilder();
    sb.append("@InsertProvider(type=");
    sb.append(fqjt.getShortName());
    sb.append(".class, method=\"");
    sb.append(this.introspectedTable.getInsertSelectiveStatementId());
    sb.append("\")");

    method.addAnnotation(sb.toString());

    GeneratedKey gk = this.introspectedTable.getGeneratedKey();
    if (gk != null)
      addGeneratedKeyAnnotation(interfaze, method, gk);
  }
}