package org.mybatis.generator.api.dom.java;

import java.util.ArrayList;
import java.util.List;

public class Parameter
{
  private String name;
  private FullyQualifiedJavaType type;
  private List<String> annotations;

  public Parameter(FullyQualifiedJavaType type, String name)
  {
    this.name = name;
    this.type = type;
    this.annotations = new ArrayList<>();
  }

  public Parameter(FullyQualifiedJavaType type, String name, String annotation) {
    this(type, name);
    addAnnotation(annotation);
  }

  public String getName()
  {
    return this.name;
  }

  public FullyQualifiedJavaType getType()
  {
    return this.type;
  }

  public List<String> getAnnotations() {
    return this.annotations;
  }

  public void addAnnotation(String annotation) {
    this.annotations.add(annotation);
  }

  public String getFormattedContent() {
    StringBuilder sb = new StringBuilder();

    for (String annotation : this.annotations) {
      sb.append(annotation);
      sb.append(' ');
    }

    sb.append(this.type.getShortName());
    sb.append(' ');
    sb.append(this.name);

    return sb.toString();
  }

  public String toString()
  {
    return getFormattedContent();
  }
}