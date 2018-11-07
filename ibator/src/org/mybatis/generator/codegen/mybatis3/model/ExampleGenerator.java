package org.mybatis.generator.codegen.mybatis3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

import ibator.Globar;

public class ExampleGenerator extends AbstractJavaGenerator
{
  public List<CompilationUnit> getCompilationUnits()
  {
    FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
    this.progressCallback.startTask(Messages.getString(
      "Progress.6", table.toString()));
    CommentGenerator commentGenerator = this.context.getCommentGenerator();

    FullyQualifiedJavaType type = new FullyQualifiedJavaType(
      this.introspectedTable.getExampleType());
    TopLevelClass topLevelClass = new TopLevelClass(type);
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(topLevelClass);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setConstructor(true);
    method.setName(type.getShortName());
    method.addBodyLine("oredCriteria = new ArrayList<Criteria>();");

    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    Field field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);
    field.setType(FullyQualifiedJavaType.getStringInstance());
    field.setName("pk_name");

    List<IntrospectedColumn> list = this.introspectedTable.getPrimaryKeyColumns();
    if ((list != null) && (list.size() == 1))
    {
      String value = ((IntrospectedColumn)list.get(0)).getActualColumnName();
      field.setInitializationString("\"" + value + "\"");
    }
    commentGenerator.addFieldComment(field, this.introspectedTable);
    topLevelClass.addField(field);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("setPk_name");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "pk_name"));
    method.addBodyLine("this.pk_name = pk_name;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getStringInstance());
    method.setName("getPk_name");
    method.addBodyLine("return pk_name;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    if (Globar.global.getDbVo().getDialect().toUpperCase().startsWith("SQLSERVER2000")) {
      topLevelClass.addMethod(getWhere());
    }

    field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);
    field.setType(FullyQualifiedJavaType.getStringInstance());
    field.setName("orderByClause");
    commentGenerator.addFieldComment(field, this.introspectedTable);
    topLevelClass.addField(field);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("setOrderByClause");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "orderByClause"));
    method.addBodyLine("this.orderByClause = orderByClause;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getStringInstance());
    method.setName("getOrderByClause");
    method.addBodyLine("return orderByClause;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);
    field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    field.setName("distinct");
    commentGenerator.addFieldComment(field, this.introspectedTable);
    topLevelClass.addField(field);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("setDistinct");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "distinct"));
    method.addBodyLine("this.distinct = distinct;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(
      FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    method.setName("isDistinct");
    method.addBodyLine("return distinct;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);

    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
      "java.util.List<Criteria>");
    field.setType(fqjt);
    field.setName("oredCriteria");
    commentGenerator.addFieldComment(field, this.introspectedTable);
    topLevelClass.addField(field);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(fqjt);
    method.setName("getOredCriteria");
    method.addBodyLine("return oredCriteria;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("or");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getCriteriaInstance(), "criteria"));
    method.addBodyLine("oredCriteria.add(criteria);");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("or");
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    method.addBodyLine("Criteria criteria = createCriteriaInternal();");
    method.addBodyLine("oredCriteria.add(criteria);");
    method.addBodyLine("return criteria;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("createCriteria");
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    method.addBodyLine("Criteria criteria = createCriteriaInternal();");
    method.addBodyLine("if (oredCriteria.size() == 0) {");
    method.addBodyLine("oredCriteria.add(criteria);");
    method.addBodyLine("}");
    method.addBodyLine("return criteria;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("createCriteriaInternal");
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    method.addBodyLine("Criteria criteria = new Criteria();");
    method.addBodyLine("return criteria;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("clear");
    method.addBodyLine("oredCriteria.clear();");
    method.addBodyLine("orderByClause = null;");
    method.addBodyLine("distinct = false;");
    commentGenerator.addGeneralMethodComment(method, this.introspectedTable);
    topLevelClass.addMethod(method);

    topLevelClass
      .addInnerClass(getGeneratedCriteriaInnerClass(topLevelClass));

    topLevelClass.addInnerClass(getCriteriaInnerClass(topLevelClass));

    topLevelClass.addInnerClass(getCriterionInnerClass(topLevelClass));

    List<CompilationUnit> answer = new ArrayList<>();
    if (this.context.getPlugins().modelExampleClassGenerated(
      topLevelClass, this.introspectedTable)) {
      answer.add(topLevelClass);
    }
    return answer;
  }

  private InnerClass getCriterionInnerClass(TopLevelClass topLevelClass)
  {
    InnerClass answer = new InnerClass(new FullyQualifiedJavaType(
      "Criterion"));
    answer.setVisibility(JavaVisibility.PUBLIC);
    answer.setStatic(true);
    this.context.getCommentGenerator().addClassComment(answer, 
      this.introspectedTable);

    Field field = new Field();
    field.setName("condition");
    field.setType(FullyQualifiedJavaType.getStringInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("value");
    field.setType(FullyQualifiedJavaType.getObjectInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("secondValue");
    field.setType(FullyQualifiedJavaType.getObjectInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("noValue");
    field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("singleValue");
    field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("betweenValue");
    field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("listValue");
    field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    field = new Field();
    field.setName("typeHandler");
    field.setType(FullyQualifiedJavaType.getStringInstance());
    field.setVisibility(JavaVisibility.PRIVATE);
    answer.addField(field);
    answer.addMethod(getGetter(field));

    Method method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criterion");
    method.setConstructor(true);
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addBodyLine("super();");
    method.addBodyLine("this.condition = condition;");
    method.addBodyLine("this.typeHandler = null;");
    method.addBodyLine("this.noValue = true;");
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criterion");
    method.setConstructor(true);
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "typeHandler"));
    method.addBodyLine("super();");
    method.addBodyLine("this.condition = condition;");
    method.addBodyLine("this.value = value;");
    method.addBodyLine("this.typeHandler = typeHandler;");
    method.addBodyLine("if (value instanceof List<?>) {");
    method.addBodyLine("this.listValue = true;");
    method.addBodyLine("} else {");
    method.addBodyLine("this.singleValue = true;");
    method.addBodyLine("}");
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criterion");
    method.setConstructor(true);
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addBodyLine("this(condition, value, null);");
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criterion");
    method.setConstructor(true);
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "secondValue"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "typeHandler"));
    method.addBodyLine("super();");
    method.addBodyLine("this.condition = condition;");
    method.addBodyLine("this.value = value;");
    method.addBodyLine("this.secondValue = secondValue;");
    method.addBodyLine("this.typeHandler = typeHandler;");
    method.addBodyLine("this.betweenValue = true;");
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criterion");
    method.setConstructor(true);
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "secondValue"));
    method.addBodyLine("this(condition, value, secondValue, null);");
    answer.addMethod(method);

    return answer;
  }

  private InnerClass getCriteriaInnerClass(TopLevelClass topLevelClass)
  {
    InnerClass answer = new InnerClass(
      FullyQualifiedJavaType.getCriteriaInstance());

    answer.setVisibility(JavaVisibility.PUBLIC);
    answer.setStatic(true);
    answer.setSuperClass(
      FullyQualifiedJavaType.getGeneratedCriteriaInstance());

    this.context.getCommentGenerator().addClassComment(answer, 
      this.introspectedTable, true);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("Criteria");
    method.setConstructor(true);
    method.addBodyLine("super();");
    answer.addMethod(method);

    return answer;
  }

  private InnerClass getGeneratedCriteriaInnerClass(TopLevelClass topLevelClass)
  {
    InnerClass answer = new InnerClass(
      FullyQualifiedJavaType.getGeneratedCriteriaInstance());

    answer.setVisibility(JavaVisibility.PROTECTED);
    answer.setStatic(true);
    answer.setAbstract(true);
    this.context.getCommentGenerator().addClassComment(answer, 
      this.introspectedTable);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("GeneratedCriteria");
    method.setConstructor(true);
    method.addBodyLine("super();");
    method.addBodyLine("criteria = new ArrayList<Criterion>();");
    answer.addMethod(method);

    List<String> criteriaLists = new ArrayList<>();
    criteriaLists.add("criteria");

    Iterator<?> localIterator1 = this.introspectedTable
      .getNonBLOBColumns().iterator();

    while (localIterator1.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator1.next();
      if (StringUtility.stringHasValue(introspectedColumn
        .getTypeHandler())) {
        String name = addtypeHandledObjectsAndMethods(
          introspectedColumn, method, answer);
        criteriaLists.add(name);
      }

    }

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("isValid");
    method.setReturnType(
      FullyQualifiedJavaType.getBooleanPrimitiveInstance());
    StringBuilder sb = new StringBuilder();
    Iterator<?> strIter = criteriaLists.iterator();
    sb.append("return ");
    sb.append((String)strIter.next());
    sb.append(".size() > 0");
    if (!strIter.hasNext()) {
      sb.append(';');
    }
    method.addBodyLine(sb.toString());
    while (strIter.hasNext()) {
      sb.setLength(0);
      OutputUtilities.javaIndent(sb, 1);
      sb.append("|| ");
      sb.append((String)strIter.next());
      sb.append(".size() > 0");
      if (!strIter.hasNext()) {
        sb.append(';');
      }
      method.addBodyLine(sb.toString());
    }
    answer.addMethod(method);

    if (criteriaLists.size() > 1) {
      Field field = new Field();
      field.setName("allCriteria");
      field.setType(new FullyQualifiedJavaType("List<Criterion>"));
      field.setVisibility(JavaVisibility.PROTECTED);
      answer.addField(field);
    }

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("getAllCriteria");
    method.setReturnType(new FullyQualifiedJavaType("List<Criterion>"));
    if (criteriaLists.size() < 2) {
      method.addBodyLine("return criteria;");
    } else {
      method.addBodyLine("if (allCriteria == null) {");
      method.addBodyLine("allCriteria = new ArrayList<Criterion>();");

      strIter = criteriaLists.iterator();
      while (strIter.hasNext()) {
        method.addBodyLine(String.format("allCriteria.addAll(%s);", new Object[] { strIter.next() }));
      }

      method.addBodyLine("}");
      method.addBodyLine("return allCriteria;");
    }
    answer.addMethod(method);

    topLevelClass.addImportedType(
      FullyQualifiedJavaType.getNewListInstance());
    topLevelClass.addImportedType(
      FullyQualifiedJavaType.getNewArrayListInstance());

    Field field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);
    FullyQualifiedJavaType listOfCriterion = new FullyQualifiedJavaType(
      "java.util.List<Criterion>");
    field.setType(listOfCriterion);
    field.setName("criteria");
    answer.addField(field);

    method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(field.getType());
    method.setName(
      JavaBeansUtil.getGetterMethodName(field.getName(), 
      field.getType()));
    method.addBodyLine("return criteria;");
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("addCriterion");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addBodyLine("if (condition == null) {");
    method
      .addBodyLine("throw new RuntimeException(\"Value for condition cannot be null\");");
    method.addBodyLine("}");
    method.addBodyLine("criteria.add(new Criterion(condition));");
    if (criteriaLists.size() > 1) {
      method.addBodyLine("allCriteria = null;");
    }
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("addCriterion");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "property"));
    method.addBodyLine("if (value == null) {");
    method
      .addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");
    method.addBodyLine("}");
    method.addBodyLine("criteria.add(new Criterion(condition, value));");
    if (criteriaLists.size() > 1) {
      method.addBodyLine("allCriteria = null;");
    }
    answer.addMethod(method);

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName("addCriterion");
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value1"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value2"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "property"));
    method.addBodyLine("if (value1 == null || value2 == null) {");
    method
      .addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");");
    method.addBodyLine("}");
    method
      .addBodyLine("criteria.add(new Criterion(condition, value1, value2));");
    if (criteriaLists.size() > 1) {
      method.addBodyLine("allCriteria = null;");
    }
    answer.addMethod(method);

    FullyQualifiedJavaType listOfDates = new FullyQualifiedJavaType(
      "java.util.List<java.util.Date>");

    if (this.introspectedTable.hasJDBCDateColumns()) {
      topLevelClass.addImportedType(
        FullyQualifiedJavaType.getDateInstance());
      topLevelClass.addImportedType(
        FullyQualifiedJavaType.getNewIteratorInstance());
      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCDate");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (value == null) {");
      method
        .addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");
      method.addBodyLine("}");
      method
        .addBodyLine("addCriterion(condition, new java.sql.Date(value.getTime()), property);");
      answer.addMethod(method);

      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCDate");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(listOfDates, "values"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (values == null || values.size() == 0) {");
      method
        .addBodyLine("throw new RuntimeException(\"Value list for \" + property + \" cannot be null or empty\");");
      method.addBodyLine("}");
      method
        .addBodyLine("List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();");
      method.addBodyLine("Iterator<Date> iter = values.iterator();");
      method.addBodyLine("while (iter.hasNext()) {");
      method
        .addBodyLine("dateList.add(new java.sql.Date(iter.next().getTime()));");
      method.addBodyLine("}");
      method.addBodyLine("addCriterion(condition, dateList, property);");
      answer.addMethod(method);

      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCDate");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value1"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value2"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (value1 == null || value2 == null) {");
      method
        .addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");");
      method.addBodyLine("}");
      method
        .addBodyLine("addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);");
      answer.addMethod(method);
    }

    if (this.introspectedTable.hasJDBCTimeColumns()) {
      topLevelClass.addImportedType(
        FullyQualifiedJavaType.getDateInstance());
      topLevelClass.addImportedType(
        FullyQualifiedJavaType.getNewIteratorInstance());
      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCTime");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (value == null) {");
      method
        .addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");
      method.addBodyLine("}");
      method
        .addBodyLine("addCriterion(condition, new java.sql.Time(value.getTime()), property);");
      answer.addMethod(method);

      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCTime");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(listOfDates, "values"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (values == null || values.size() == 0) {");
      method
        .addBodyLine("throw new RuntimeException(\"Value list for \" + property + \" cannot be null or empty\");");
      method.addBodyLine("}");
      method
        .addBodyLine("List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();");
      method.addBodyLine("Iterator<Date> iter = values.iterator();");
      method.addBodyLine("while (iter.hasNext()) {");
      method
        .addBodyLine("timeList.add(new java.sql.Time(iter.next().getTime()));");
      method.addBodyLine("}");
      method.addBodyLine("addCriterion(condition, timeList, property);");
      answer.addMethod(method);

      method = new Method();
      method.setVisibility(JavaVisibility.PROTECTED);
      method.setName("addCriterionForJDBCTime");
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "condition"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value1"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getDateInstance(), "value2"));
      method.addParameter(new Parameter(
        FullyQualifiedJavaType.getStringInstance(), "property"));
      method.addBodyLine("if (value1 == null || value2 == null) {");
      method
        .addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");");
      method.addBodyLine("}");
      method
        .addBodyLine("addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);");
      answer.addMethod(method);
    }

    Iterator<?> localIterator2 = this.introspectedTable
      .getNonBLOBColumns().iterator();

    while (localIterator2.hasNext()) {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)localIterator2.next();
      topLevelClass.addImportedType(
        introspectedColumn.getFullyQualifiedJavaType());

      answer.addMethod(getSetNullMethod(introspectedColumn));
      answer.addMethod(getSetNotNullMethod(introspectedColumn));
      answer.addMethod(getSetEqualMethod(introspectedColumn));
      answer.addMethod(getSetNotEqualMethod(introspectedColumn));
      answer.addMethod(getSetGreaterThanMethod(introspectedColumn));
      answer
        .addMethod(getSetGreaterThenOrEqualMethod(introspectedColumn));
      answer.addMethod(getSetLessThanMethod(introspectedColumn));
      answer.addMethod(getSetLessThanOrEqualMethod(introspectedColumn));

      if (introspectedColumn.isJdbcCharacterColumn()) {
        answer.addMethod(getSetLikeMethod(introspectedColumn));
        answer.addMethod(getSetNotLikeMethod(introspectedColumn));
      }

      answer.addMethod(getSetInOrNotInMethod(introspectedColumn, true));
      answer.addMethod(getSetInOrNotInMethod(introspectedColumn, false));
      answer.addMethod(
        getSetBetweenOrNotBetweenMethod(introspectedColumn, true));
      answer.addMethod(
        getSetBetweenOrNotBetweenMethod(introspectedColumn, false));
    }

    return answer;
  }

  private Method getSetNullMethod(IntrospectedColumn introspectedColumn) {
    return getNoValueMethod(introspectedColumn, "IsNull", "is null");
  }

  private Method getSetNotNullMethod(IntrospectedColumn introspectedColumn) {
    return getNoValueMethod(introspectedColumn, "IsNotNull", "is not null");
  }

  private Method getSetEqualMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "EqualTo", "=");
  }

  private Method getSetNotEqualMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "NotEqualTo", "<>");
  }

  private Method getSetGreaterThanMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "GreaterThan", ">");
  }

  private Method getSetGreaterThenOrEqualMethod(IntrospectedColumn introspectedColumn)
  {
    return getSingleValueMethod(introspectedColumn, 
      "GreaterThanOrEqualTo", ">=");
  }

  private Method getSetLessThanMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "LessThan", "<");
  }

  private Method getSetLessThanOrEqualMethod(IntrospectedColumn introspectedColumn)
  {
    return getSingleValueMethod(introspectedColumn, 
      "LessThanOrEqualTo", "<=");
  }

  private Method getSetLikeMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "Like", "like");
  }

  private Method getSetNotLikeMethod(IntrospectedColumn introspectedColumn) {
    return getSingleValueMethod(introspectedColumn, "NotLike", "not like");
  }

  private Method getWhere()
  {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);

    StringBuilder sb = new StringBuilder();
    method.setName("getSQL");
    method.setReturnType(FullyQualifiedJavaType.getStringInstance());
    sb.setLength(0);
    method.addBodyLine("StringBuffer sbAll = new StringBuffer();");
    method.addBodyLine("for (Criteria criteria : this.getOredCriteria()) {");
    method.addBodyLine("StringBuffer sb = new StringBuffer();");
    method.addBodyLine("if (criteria.isValid()) {");
    method.addBodyLine("for (Criterion c : criteria.criteria) {");
    method.addBodyLine("if (c.noValue)");
    method.addBodyLine("sb.append(\" and \").append(c.condition);");
    method.addBodyLine("else if (c.isSingleValue())");
    method.addBodyLine("sb.append(\" and \").append(c.condition).append(\" '\").append(c.value).append(\"'\");");
    method.addBodyLine("else if (c.isBetweenValue())");
    method.addBodyLine("sb.append(\" and \").append(c.condition).append(\" '\").append(c.value).append(\"' and '\").append(c.getSecondValue()).append(\"'\");");
    method.addBodyLine("else if (c.listValue) {");
    method.addBodyLine("List cs = (List) c.value;");
    method.addBodyLine("sb.append(\" and \").append(c.condition);");
    method.addBodyLine("sb.append(\"(\");");
    method.addBodyLine("for (Object cc : cs) {");
    method.addBodyLine("sb.append(\"'\").append(cc).append(\"',\");");
    method.addBodyLine("}");
    method.addBodyLine("sb.setCharAt(sb.length() - 1, ')');");
    method.addBodyLine("}");
    method.addBodyLine("}");
    method.addBodyLine("sb.append(\" or \");");
    method.addBodyLine("String answer = sb.toString().trim();");
    method.addBodyLine("answer = answer.replaceFirst(\"and\", \"\");");
    method.addBodyLine("sbAll.append(answer);");
    method.addBodyLine("}}");

    method.addBodyLine("if (sbAll.length() > 0) {");
    method.addBodyLine("sbAll.setLength(sbAll.length()-2);");
    method.addBodyLine("}");
    method.addBodyLine("return sbAll.toString();");

    return method;
  }

  private Method getSingleValueMethod(IntrospectedColumn introspectedColumn, String nameFragment, String operator)
  {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.addParameter(new Parameter(
      introspectedColumn.getFullyQualifiedJavaType(), "value"));
    StringBuilder sb = new StringBuilder();
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    sb.insert(0, "and");
    sb.append(nameFragment);
    method.setName(sb.toString());
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    sb.setLength(0);

    if (introspectedColumn.isJDBCDateColumn()) {
      sb.append("addCriterionForJDBCDate(\"");
    } else if (introspectedColumn.isJDBCTimeColumn()) {
      sb.append("addCriterionForJDBCTime(\"");
    } else if (StringUtility.stringHasValue(introspectedColumn
      .getTypeHandler())) {
      sb.append("add");
      sb.append(introspectedColumn.getJavaProperty());
      sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
      sb.append("Criterion(\"");
    } else {
      sb.append("addCriterion(\"");
    }

    sb.append(
      MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
    sb.append(' ');
    sb.append(operator);
    sb.append("\", ");

    if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
      sb.append("new ");
      sb.append(
        introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper().getShortName());
      sb.append("(value)");
    } else {
      sb.append("value");
    }

    sb.append(", \"");
    sb.append(introspectedColumn.getJavaProperty());
    sb.append("\");");
    method.addBodyLine(sb.toString());
    method.addBodyLine("return (Criteria) this;");

    return method;
  }

  private Method getSetBetweenOrNotBetweenMethod(IntrospectedColumn introspectedColumn, boolean betweenMethod)
  {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    FullyQualifiedJavaType type = introspectedColumn
      .getFullyQualifiedJavaType();

    method.addParameter(new Parameter(type, "value1"));
    method.addParameter(new Parameter(type, "value2"));
    StringBuilder sb = new StringBuilder();
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    sb.insert(0, "and");
    if (betweenMethod)
      sb.append("Between");
    else {
      sb.append("NotBetween");
    }
    method.setName(sb.toString());
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    sb.setLength(0);

    if (introspectedColumn.isJDBCDateColumn()) {
      sb.append("addCriterionForJDBCDate(\"");
    } else if (introspectedColumn.isJDBCTimeColumn()) {
      sb.append("addCriterionForJDBCTime(\"");
    } else if (StringUtility.stringHasValue(introspectedColumn
      .getTypeHandler())) {
      sb.append("add");
      sb.append(introspectedColumn.getJavaProperty());
      sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
      sb.append("Criterion(\"");
    } else {
      sb.append("addCriterion(\"");
    }

    sb.append(
      MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
    if (betweenMethod)
      sb.append(" between");
    else {
      sb.append(" not between");
    }
    sb.append("\", ");
    if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
      sb.append("new ");
      sb.append(
        introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper().getShortName());
      sb.append("(value1), ");
      sb.append("new ");
      sb.append(
        introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper().getShortName());
      sb.append("(value2)");
    } else {
      sb.append("value1, value2");
    }

    sb.append(", \"");
    sb.append(introspectedColumn.getJavaProperty());
    sb.append("\");");
    method.addBodyLine(sb.toString());
    method.addBodyLine("return (Criteria) this;");

    return method;
  }

  private Method getSetInOrNotInMethod(IntrospectedColumn introspectedColumn, boolean inMethod)
  {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    FullyQualifiedJavaType type = 
      FullyQualifiedJavaType.getNewListInstance();
    if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive())
      type.addTypeArgument(
        introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper());
    else {
      type
        .addTypeArgument(introspectedColumn
        .getFullyQualifiedJavaType());
    }

    method.addParameter(new Parameter(type, "values"));
    StringBuilder sb = new StringBuilder();
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    sb.insert(0, "and");
    if (inMethod)
      sb.append("In");
    else {
      sb.append("NotIn");
    }
    method.setName(sb.toString());
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    sb.setLength(0);

    if (introspectedColumn.isJDBCDateColumn()) {
      sb.append("addCriterionForJDBCDate(\"");
    } else if (introspectedColumn.isJDBCTimeColumn()) {
      sb.append("addCriterionForJDBCTime(\"");
    } else if (StringUtility.stringHasValue(introspectedColumn
      .getTypeHandler())) {
      sb.append("add");
      sb.append(introspectedColumn.getJavaProperty());
      sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
      sb.append("Criterion(\"");
    } else {
      sb.append("addCriterion(\"");
    }

    sb.append(
      MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
    if (inMethod)
      sb.append(" in");
    else {
      sb.append(" not in");
    }
    sb.append("\", values, \"");
    sb.append(introspectedColumn.getJavaProperty());
    sb.append("\");");
    method.addBodyLine(sb.toString());
    method.addBodyLine("return (Criteria) this;");

    return method;
  }

  private Method getNoValueMethod(IntrospectedColumn introspectedColumn, String nameFragment, String operator)
  {
    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    StringBuilder sb = new StringBuilder();
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    sb.insert(0, "and");
    sb.append(nameFragment);
    method.setName(sb.toString());
    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
    sb.setLength(0);
    sb.append("addCriterion(\"");
    sb.append(
      MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
    sb.append(' ');
    sb.append(operator);
    sb.append("\");");
    method.addBodyLine(sb.toString());
    method.addBodyLine("return (Criteria) this;");

    return method;
  }

  private String addtypeHandledObjectsAndMethods(IntrospectedColumn introspectedColumn, Method constructor, InnerClass innerClass)
  {
    StringBuilder sb = new StringBuilder();

    sb.setLength(0);
    sb.append(introspectedColumn.getJavaProperty());
    sb.append("Criteria");
    String answer = sb.toString();

    Field field = new Field();
    field.setVisibility(JavaVisibility.PROTECTED);
    field.setType(new FullyQualifiedJavaType("java.util.List<Criterion>"));
    field.setName(answer);
    innerClass.addField(field);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(field.getType());
    method.setName(
      JavaBeansUtil.getGetterMethodName(field.getName(), 
      field.getType()));
    sb.insert(0, "return ");
    sb.append(';');
    method.addBodyLine(sb.toString());
    innerClass.addMethod(method);

    sb.setLength(0);
    sb.append(field.getName());
    sb.append(" = new ArrayList<Criterion>();");
    constructor.addBodyLine(sb.toString());

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    sb.setLength(0);
    sb.append("add");
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
    sb.append("Criterion");

    method.setName(sb.toString());
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getObjectInstance(), "value"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "property"));
    method.addBodyLine("if (value == null) {");
    method
      .addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");
    method.addBodyLine("}");

    method.addBodyLine(
      String.format("%s.add(new Criterion(condition, value, \"%s\"));", new Object[] { 
      field.getName(), introspectedColumn.getTypeHandler() }));
    method.addBodyLine("allCriteria = null;");
    innerClass.addMethod(method);

    sb.setLength(0);
    sb.append("add");
    sb.append(introspectedColumn.getJavaProperty());
    sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
    sb.append("Criterion");

    method = new Method();
    method.setVisibility(JavaVisibility.PROTECTED);
    method.setName(sb.toString());
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "condition"));
    method.addParameter(new Parameter(
      introspectedColumn.getFullyQualifiedJavaType(), "value1"));
    method.addParameter(new Parameter(
      introspectedColumn.getFullyQualifiedJavaType(), "value2"));
    method.addParameter(new Parameter(
      FullyQualifiedJavaType.getStringInstance(), "property"));
    method.addBodyLine("if (value1 == null || value2 == null) {");
    method
      .addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");");
    method.addBodyLine("}");

    method.addBodyLine(
      String.format("%s.add(new Criterion(condition, value1, value2, \"%s\"));", new Object[] { 
      field.getName(), introspectedColumn.getTypeHandler() }));

    method.addBodyLine("allCriteria = null;");
    innerClass.addMethod(method);

    return answer;
  }
}