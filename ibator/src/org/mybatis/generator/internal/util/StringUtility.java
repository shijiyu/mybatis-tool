package org.mybatis.generator.internal.util;

import java.util.StringTokenizer;

public class StringUtility
{
  public static String getTable(String table)
  {
    int index = table.indexOf("_");
    if (index == -1) {
      table = table.toLowerCase();
      table = Character.toUpperCase(table.charAt(0)) + table.substring(1);
      return table;
    }

    String[] temp = table.split("_");
    StringBuffer sb = new StringBuffer();
    for (String str : temp) {
      table = str.toLowerCase();
      table = Character.toUpperCase(table.charAt(0)) + table.substring(1);
      sb.append(table);
    }
    return sb.toString();
  }

  public static String getColumn(String column)
  {
    int index = column.indexOf("_");
    if (index == -1) {
      column = column.toLowerCase();
      return column;
    }

    String[] temp = column.split("_");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < temp.length; i++) {
      column = temp[i].toLowerCase();
      if (i > 0)
        column = Character.toUpperCase(column.charAt(0)) + column.substring(1);
      sb.append(column);
    }
    return sb.toString();
  }

  public static boolean stringHasValue(String s)
  {
    return (s != null) && (s.length() > 0);
  }

  public static String composeFullyQualifiedTableName(String catalog, String schema, String tableName, char separator)
  {
    StringBuilder sb = new StringBuilder();

    if (stringHasValue(catalog)) {
      sb.append(catalog);
      sb.append(separator);
    }

    if (stringHasValue(schema)) {
      sb.append(schema);
      sb.append(separator);
    }
    else if (sb.length() > 0) {
      sb.append(separator);
    }

    sb.append(tableName);

    return sb.toString();
  }

  public static boolean stringContainsSpace(String s) {
    return (s != null) && (s.indexOf(' ') != -1);
  }

  public static String escapeStringForJava(String s) {
    StringTokenizer st = new StringTokenizer(s, "\"", true);
    StringBuilder sb = new StringBuilder();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if ("\"".equals(token))
        sb.append("\\\"");
      else {
        sb.append(token);
      }
    }

    return sb.toString();
  }

  public static String escapeStringForXml(String s) {
    StringTokenizer st = new StringTokenizer(s, "\"", true);
    StringBuilder sb = new StringBuilder();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if ("\"".equals(token))
        sb.append("&quot;");
      else {
        sb.append(token);
      }
    }

    return sb.toString();
  }

  public static boolean isTrue(String s) {
    return "true".equalsIgnoreCase(s);
  }

  public static boolean stringContainsSQLWildcard(String s) {
    if (s == null) {
      return false;
    }

    return (s.indexOf('%') != -1) || (s.indexOf('_') != -1);
  }
}