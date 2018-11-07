package org.mybatis.generator.internal.util;

import java.util.Locale;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class JavaBeansUtil
{
  public static String firstLetterToLower(String str)
  {
    return Character.toLowerCase(str.charAt(0)) + str.substring(1);
  }

  public static String firstLetterToUpper(String str)
  {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static void generatorJavaBean(String name, String type, TopLevelClass topLevelClass) {
    Field field = new Field();
    field.setName(name);
    field.setType(new FullyQualifiedJavaType(type));
    field.setVisibility(JavaVisibility.PRIVATE);

    if (type.startsWith("java.util.Set"))
    {
      field.setInitializationString("new HashSet(0);");
    }
    topLevelClass.addField(field);

    StringBuffer sb = new StringBuffer();
    Method method = new Method();

    String temp = type;
    if (temp.startsWith("java.util.Set"))
      temp = "java.util.Set";
    method.setName(getSetterMethodName(name));
    method.setVisibility(JavaVisibility.PUBLIC);
    sb.setLength(0);
    sb.append("this.").append(name).append("=").append(name).append(";");
    method.addBodyLine(sb.toString());
    method.addParameter(new Parameter(new FullyQualifiedJavaType(temp), name));
    topLevelClass.addMethod(method);

    method = new Method();
    method.setName(getGetterMethodName(name, field.getType()));
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(field.getType());
    sb.setLength(0);
    sb.append("return ").append(name).append(";");
    method.addBodyLine(sb.toString());
    topLevelClass.addMethod(method);
  }

  public static String getGetterMethodName(String property, FullyQualifiedJavaType fullyQualifiedJavaType)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(property);
    if ((Character.isLowerCase(sb.charAt(0))) && (
      (sb.length() == 1) || (!Character.isUpperCase(sb.charAt(1))))) {
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    }

    if (fullyQualifiedJavaType.equals(
      FullyQualifiedJavaType.getBooleanPrimitiveInstance()))
      sb.insert(0, "is");
    else {
      sb.insert(0, "get");
    }

    return sb.toString();
  }

  public static String getSetterMethodName(String property)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(property);
    if ((Character.isLowerCase(sb.charAt(0))) && (
      (sb.length() == 1) || (!Character.isUpperCase(sb.charAt(1))))) {
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    }

    sb.insert(0, "set");

    return sb.toString();
  }

  public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase)
  {
    StringBuilder sb = new StringBuilder();

    boolean nextUpperCase = false;
    for (int i = 0; i < inputString.length(); i++) {
      char c = inputString.charAt(i);

      switch (c) {
      case ' ':
      case '#':
      case '$':
      case '&':
      case '-':
      case '/':
      case '@':
      case '_':
        if (sb.length() > 0) {
          nextUpperCase = true;
        }
        break;
      default:
        if (nextUpperCase) {
          sb.append(Character.toUpperCase(c));
          nextUpperCase = false;
        } else {
          sb.append(Character.toLowerCase(c));
        }
        break;
      }
    }

    if (firstCharacterUppercase) {
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    }

    return sb.toString();
  }

  public static String getValidPropertyName(String inputString)
  {
    String answer;
    if (inputString == null) {
      answer = null;
    }
    else
    {
      if (inputString.length() < 2) {
        answer = inputString.toLowerCase(Locale.US);
      }
      else
      {
        if ((Character.isUpperCase(inputString.charAt(0))) && 
          (!Character.isUpperCase(inputString.charAt(1))))
          answer = inputString.substring(0, 1).toLowerCase(Locale.US) + 
            inputString.substring(1);
        else {
          answer = inputString;
        }
      }
    }
    return answer;
  }
}