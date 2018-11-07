package ibator.util;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import ibator.Globar;

public class PageBeanUtil
{
  private static void addProperty(TopLevelClass topLevelClass, String name, String type)
  {
    Field field = new Field();
    field.setName(name);
    field.setType(new FullyQualifiedJavaType(type));
    field.setVisibility(JavaVisibility.PRIVATE);
    topLevelClass.addField(field);

    Method method = new Method();
    method.setName(JavaBeansUtil.getGetterMethodName(field.getName(), field.getType()));
    method.setVisibility(JavaVisibility.PUBLIC);
    method.addBodyLine("return " + field.getName() + ";");
    method.setReturnType(field.getType());
    topLevelClass.addMethod(method);

    method = new Method();
    method.setName(JavaBeansUtil.getSetterMethodName(field.getName()));
    method.setVisibility(JavaVisibility.PUBLIC);
    method.addBodyLine("this." + field.getName() + "=" + field.getName() + ";");
    method.addParameter(new Parameter(field.getType(), field.getName()));
    topLevelClass.addMethod(method);
  }

  public static void getPageBean(IProject project)
  {
    StringBuffer sb = new StringBuffer();

    sb.append(Globar.pojoPath).append(".PageBean");
    TopLevelClass topLevelClass = new TopLevelClass(sb.toString());
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    //Field field = null;
    //Method method = null;

    addProperty(topLevelClass, "currentPage", "java.lang.Integer");
    addProperty(topLevelClass, "pageSize", "java.lang.Integer");
    addProperty(topLevelClass, "recordCount", "java.lang.Integer");
    addProperty(topLevelClass, "pageCount", "java.lang.Integer");
    addProperty(topLevelClass, "resultList", "java.lang.Object");

    sb.setLength(0);
    sb.append("src/").append(Globar.pojoPath.replaceAll("\\.", "/")).append("/PageBean.java");

   ProjectFileWriteUtil.write(project, topLevelClass, sb.toString());
    getResultBean(project);
  }
  private static void getResultBean(IProject project)
  {
    StringBuffer sb = new StringBuffer();

    sb.append(Globar.pojoPath).append(".ResultVo");
    TopLevelClass topLevelClass = new TopLevelClass(sb.toString());
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    //Field field = null;
    //Method method = null;
    Field field = new Field();
	field.setVisibility(JavaVisibility.PUBLIC);
	field.setStatic(true);
	field.setType(new FullyQualifiedJavaType("java.lang.Integer"));
	field.setName("SUCCESSFUL");
	field.setInitializationString("1");
    topLevelClass.addField(field);
    
    field = new Field();
	field.setVisibility(JavaVisibility.PUBLIC);
	field.setStatic(true);
	field.setType(new FullyQualifiedJavaType("java.lang.Integer"));
	field.setName("FAUIL");
	field.setInitializationString("-1");
    topLevelClass.addField(field);
    
    addProperty(topLevelClass, "code", "java.lang.Integer");
    addProperty(topLevelClass, "message", "java.lang.String");
    addProperty(topLevelClass, "result", "java.lang.Object");
    sb.setLength(0);
    sb.append("src/").append(Globar.pojoPath.replaceAll("\\.", "/")).append("/ResultVo.java");
    ProjectFileWriteUtil.write(project, topLevelClass, sb.toString());
  }
}