package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeyWithBLOBsMethodGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class AnnotatedUpdateByPrimaryKeyWithBLOBsMethodGenerator extends UpdateByPrimaryKeyWithBLOBsMethodGenerator
{
  public void addMapperAnnotations(Interface interfaze, Method method)
  {
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Update"));

    method.addAnnotation("@Update({");

    StringBuilder sb = new StringBuilder();
    OutputUtilities.javaIndent(sb, 1);
    sb.append("\"update ");
    sb.append(StringUtility.escapeStringForJava(this.introspectedTable.getFullyQualifiedTableNameAtRuntime()));
    sb.append("\",");
    method.addAnnotation(sb.toString());

    sb.setLength(0);
    OutputUtilities.javaIndent(sb, 1);
    sb.append("\"set ");

    Iterator<?> iter = this.introspectedTable
      .getNonPrimaryKeyColumns().iterator();
    while (iter.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();

      sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
      sb.append(" = ");
      sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

      if (iter.hasNext()) {
        sb.append(',');
      }

      sb.append("\",");
      method.addAnnotation(sb.toString());

      if (iter.hasNext()) {
        sb.setLength(0);
        OutputUtilities.javaIndent(sb, 1);
        sb.append("  \"");
      }
    }

    boolean and = false;
    iter = this.introspectedTable.getPrimaryKeyColumns().iterator();
    while (iter.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
      sb.setLength(0);
      OutputUtilities.javaIndent(sb, 1);
      if (and) {
        sb.append("  \"and ");
      } else {
        sb.append("\"where ");
        and = true;
      }

      sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
      sb.append(" = ");
      sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      sb.append('"');
      if (iter.hasNext()) {
        sb.append(',');
      }
      method.addAnnotation(sb.toString());
    }

    method.addAnnotation("})");
  }
}