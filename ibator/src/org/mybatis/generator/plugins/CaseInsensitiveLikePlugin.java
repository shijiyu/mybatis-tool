package org.mybatis.generator.plugins;

import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class CaseInsensitiveLikePlugin extends PluginAdapter
{
  public boolean validate(List<String> warnings)
  {
    return true;
  }

  public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
  {
    InnerClass criteria = null;

    for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
      if ("Criteria".equals(innerClass.getType().getShortName())) {
        criteria = innerClass;
        break;
      }
    }

    if (criteria == null)
    {
      return true;
    }

    Iterator<IntrospectedColumn> talbes = introspectedTable
      .getNonBLOBColumns().iterator();

    while (talbes.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)talbes.next();
      if ((introspectedColumn.isJdbcCharacterColumn()) && 
        (introspectedColumn.isStringColumn()))
      {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(
          introspectedColumn.getFullyQualifiedJavaType(), "value"));

        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "and");
        sb.append("LikeInsensitive");
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());

        sb.setLength(0);
        sb.append("addCriterion(\"upper(");

        sb.append(") like\", value.toUpperCase(), \"");
        sb.append(introspectedColumn.getJavaProperty());
        sb.append("\");");
        method.addBodyLine(sb.toString());
        method.addBodyLine("return this;");

        criteria.addMethod(method);
      }
    }
    return true;
  }
}