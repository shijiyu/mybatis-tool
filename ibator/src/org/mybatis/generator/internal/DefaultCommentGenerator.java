package org.mybatis.generator.internal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import ibator.Globar;

public class DefaultCommentGenerator
  implements CommentGenerator
{
  private Properties properties;
  private boolean suppressDate;
  public static boolean suppressAllComments = false;

  public DefaultCommentGenerator()
  {
    this.properties = new Properties();
    this.suppressDate = false;
  }

  public void addJavaFileComment(CompilationUnit compilationUnit)
  {
  }

  public void addComment(XmlElement xmlElement)
  {
    if (suppressAllComments);
  }

  public void addRootComment(XmlElement rootElement)
  {
  }

  public void addConfigurationProperties(Properties properties)
  {
    this.properties.putAll(properties);

    this.suppressDate = StringUtility.isTrue(
      properties.getProperty("suppressDate"));
  }

  protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append("@ibatorgenerated");
    if (markAsDoNotDelete) {
      sb.append(" do_not_delete_during_merge");
    }
    String s = getDateString();
    if (s != null) {
      sb.append(' ');
      sb.append(s);
    }
    javaElement.addJavaDocLine(sb.toString());
  }

  protected String getDateString()
  {
    if (this.suppressDate) {
      return null;
    }
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      .format(new Date());
  }

  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable)
  {
    if (suppressAllComments) {
      return;
    }

    innerClass.addJavaDocLine("/**");
    innerClass.addJavaDocLine(" * ");
    innerClass.addJavaDocLine(" * 内类部，系统内部调用1");

    addJavadocTag(innerClass, false);

    innerClass.addJavaDocLine(" */");
  }

  public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable)
  {
    if (suppressAllComments) {
      return;
    }

    //StringBuilder sb = new StringBuilder();

    innerEnum.addJavaDocLine("/**");
    innerEnum.addJavaDocLine(" * ");
    innerEnum.addJavaDocLine(" * 内类部，系统内部调用2");

    addJavadocTag(innerEnum, false);

    innerEnum.addJavaDocLine(" */");
  }

  public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
  {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    field.addJavaDocLine("/**");
    sb.append(" * ");
    String remarks = introspectedColumn.getRemarks();

    sb.append("").append(
      introspectedTable.getFullyQualifiedTableNameAtRuntime())
      .append(".").append(introspectedColumn.getActualColumnName());

    if ((remarks != null) && (remarks.length() > 0)) {
      sb.append(" (").append(remarks).append(")");
    }
    field.addJavaDocLine(sb.toString());
    addJavadocTag(field, false);
    field.addJavaDocLine(" */");
  }

  public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    sb.append(" * ");
    if (field.getName().equals("pk_name"))
      sb.append("主键字段");
    else if (field.getName().equals("orderByClause"))
      sb.append("排序字段");
    else if (field.getName().equals("distinct"))
      sb.append("去重复");
    else if (field.getName().equals("oredCriteria")) {
      sb.append("条件集");
    }

    if (sb.length() == 3)
      return;
    field.addJavaDocLine("/**");
    field.addJavaDocLine(sb.toString());
    addJavadocTag(field, false);
    field.addJavaDocLine(" */");
  }

  public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable)
  {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    sb.append(" * ");
    // FIXME sjy 方法名称替换成统一examleName 
    String name = method.getName();
    if (name.equals("setOrderByClause")) {
      sb.append("排序字段");
    } else if (name.equals("setDistinct")) {
      sb.append("设置去重复");
    } else if (name.equals("createCriteria")) {
      sb.append("条件查询要先创建Criteria");
    } else if (name.equals("countBy"+Globar.exampleName)) {
      sb.append("条件统计\r\n");
      sb.append("     * 参数:查询条件,null为整张表\r\n");
      sb.append("     * 返回:查询个数");
    } else if (name.equals("deleteBy"+Globar.exampleName)) {
      sb.append("批量条件删除\r\n");
      sb.append("     * 参数:删除条件,null为整张表\r\n");
      sb.append("     * 返回:删除个数");
    } else if (name.equals("deleteByPrimaryKey")) {
      sb.append("根据主键删除\r\n");
      sb.append("     * 参数:主键\r\n");
      sb.append("     * 返回:删除个数");
    } else if (name.equals("insert")) {
      sb.append("插入，空属性也会插入\r\n");
      sb.append("     * 参数:pojo对象\r\n");
      sb.append("     * 返回:删除个数");
    } else if (name.equals("insertSelective")) {
      sb.append("插入，空属性不会插入\r\n");
      sb.append("     * 参数:pojo对象\r\n");
      sb.append("     * 返回:删除个数");
    } else if (name.equals("selectBy"+Globar.exampleName)) {
      sb.append("批量条件查询\r\n");
      sb.append("     * 参数:查询条件,null查整张表\r\n");
      sb.append("     * 返回:对象集合");
    } else if (name.equals("selectByPrimaryKey")) {
      sb.append("根据主键查询\r\n");
      sb.append("     * 参数:查询条件,主键值\r\n");
      sb.append("     * 返回:对象");
    } else if (name.equals("updateBy"+Globar.exampleName+"Selective")) {
      sb.append("批量条件修改，空值条件不修改\r\n");
      sb.append("     * 参数:1.要修改成的值，2.要修改条件\r\n");
      sb.append("     * 返回:成功修改个数");
    } else if (name.equals("updateBy"+Globar.exampleName)) {
      sb.append("批量条件修改，空值条件会修改成null\r\n");
      sb.append("     * 参数:1.要修改成的值，2.要修改条件\r\n");
      sb.append("     * 返回:成功修改个数");
    } else if (name.equals("updateByPrimaryKeySelective")) {
      sb.append("根据主键修改，空值条件不会修改成null\r\n");
      sb.append("     * 参数:1.要修改成的值\r\n");
      sb.append("     * 返回:成功修改个数");
    } else if (name.equals("updateByPrimaryKey")) {
      sb.append("根据主键修改，空值条件会修改成null\r\n");
      sb.append("     * 参数:1.要修改成的值\r\n");
      sb.append("     * 返回:成功修改个数");
    } else if (name.equals("selectBy"+Globar.exampleName+"AndPage")) {
      sb.append("物理分页条件查询\r\n");
      sb.append("     * 参数:1.查询条件 2.分页条件 new RowBounds(2,3) \r\n");
      sb.append("            从第2条开始显示，显示3条(从0开始编号)\r\n");
      sb.append("     * 返回:成功修改个数");
    } else if (name.equals("selectBy"+Globar.exampleName+"WithBLOBs")) {
      sb.append("批量条件查询,支持大字段类型\r\n");
      sb.append("     * 参数:查询条件,null查整张表\r\n");
      sb.append("     * 返回:对象集合");
    }
    else if (name.equals("selectBy"+Globar.exampleName+"WithBLOBs")) {
      sb.append("批量条件查询,支持大字段类型\r\n");
      sb.append("     * 参数:查询条件,null查整张表\r\n");
      sb.append("     * 返回:对象集合");
    }
    else if (name.equals("updateBy"+Globar.exampleName+"WithBLOBs")) {
      sb.append("批量条件修改，空值条件会修改成null,支持大字段类型\r\n");
      sb.append("     * 参数:1.要修改成的值，2.要修改条件\r\n");
      sb.append("     * 返回:成功修改个数");
    }
    else if (name.equals("updateByPrimaryKeyWithBLOBs")) {
      sb.append("根据主键修改，空值条件会修改成null,支持大字段类型\r\n");
      sb.append("     * 参数:1.要修改成的值\r\n");
      sb.append("     * 返回:成功修改个数");
    }
    else if (name.equals("selectBy"+Globar.exampleName+"WithBLOBsAndPage")) {
      sb.append("物理分页条件查询,支持大字段\r\n");
      sb.append("     * 参数:1.查询条件 2.分页条件 new RowBounds(2,3) \r\n");
      sb.append("            从第2条开始显示，显示3条(从0开始编号)\r\n");
      sb.append("     * 返回:成功修改个数");
    }

    if (sb.length() == 3)
      return;
    method.addJavaDocLine("/**");
    method.addJavaDocLine(sb.toString());

    addJavadocTag(method, false);

    method.addJavaDocLine(" */");
  }

  public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
  {
    if (suppressAllComments);
  }

  public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
  {
    if (suppressAllComments);
  }

  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete)
  {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerClass.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerClass.addJavaDocLine(sb.toString());

    addJavadocTag(innerClass, markAsDoNotDelete);

    innerClass.addJavaDocLine(" */");
  }
}