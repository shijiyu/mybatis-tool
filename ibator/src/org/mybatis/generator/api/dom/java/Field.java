package org.mybatis.generator.api.dom.java;

import org.mybatis.generator.api.dom.OutputUtilities;

public class Field extends JavaElement
{
  private FullyQualifiedJavaType type;
  private String name;
  private String initializationString;

  public Field()
  {
    this("foo", FullyQualifiedJavaType.getIntInstance());
  }

  public Field(String name, FullyQualifiedJavaType type)
  {
    this.name = name;
    this.type = type;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public FullyQualifiedJavaType getType()
  {
    return this.type;
  }

  public void setType(FullyQualifiedJavaType type)
  {
    this.type = type;
  }

  public String getInitializationString()
  {
    return this.initializationString;
  }

  public void setInitializationString(String initializationString)
  {
    this.initializationString = initializationString;
  }

  public String getFormattedContent(int indentLevel) {
    StringBuilder sb = new StringBuilder();

    addFormattedJavadoc(sb, indentLevel);
    addFormattedAnnotations(sb, indentLevel);

    OutputUtilities.javaIndent(sb, indentLevel);
    sb.append(getVisibility().getValue());

    if (isStatic()) {
      sb.append("static ");
    }

    if (isFinal()) {
      sb.append("final ");
    }

    sb.append(this.type.getShortName());

    sb.append(' ');
    sb.append(this.name);

    if ((this.initializationString != null) && (this.initializationString.length() > 0)) {
      sb.append(" = ");
      sb.append(this.initializationString);
    }

    sb.append(';');

    return sb.toString();
  }
}