package ibator.util;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import ibator.Globar;

public class TestFileUtil
{
  public static boolean createTestFileWithoutSpring(IProject project, String pojo, String filename)
  {
    StringBuffer sb = new StringBuffer();

    String daoName = pojo + Globar.daoName;

    if (DBUtil.isUnionKey(pojo))
      pojo = StringUtility.getTable(pojo) + "Key";
    TopLevelClass topLevelClass = new TopLevelClass("com.test.TestDAOWithoutSpring");

    sb.setLength(0);
    sb.append("/*\n\nNo spring by page query\n");
    sb.append("\n");
    sb.append("Example parameter:\n");
    sb.append("     ShopExample example = new ShopExample();\n");
    sb.append("     Criteria c1 = example.createCriteria(); \n");
    sb.append("     Criteria c2 = example.createCriteria(); \n");
    sb.append("     example.or(c2); \n");
    sb.append("     c1.andSidBetween(1, 100); \n");
    sb.append("     c1.andSnameLike(\"my%\"); \n");
    sb.append("     c2.andSidIsNotNull(); \n");
    sb.append("     example.setOrderByClause(\"sprice desc\"); //sort field\t\t\n\n");
    sb.append("Page query\n");
    sb.append("     List list = mapper.selectByExampleAndPage(example, new RowBounds(0, 3));\n");
    sb.append("\n\n*/");

    topLevelClass.addJavaDocLine(sb.toString());

    topLevelClass.addImportedType("java.io.IOException");
    topLevelClass.addImportedType("java.io.Reader");
    topLevelClass.addImportedType("org.apache.ibatis.io.Resources");
    topLevelClass.addImportedType("org.apache.ibatis.session.RowBounds");
    topLevelClass.addImportedType("org.apache.ibatis.session.SqlSession");
    topLevelClass.addImportedType("java.util.List");
    topLevelClass.addImportedType("com.db.ConnectionFactory");
    topLevelClass.addImportedType("org.apache.ibatis.session.SqlSessionFactory");
    topLevelClass.addImportedType("org.apache.ibatis.session.SqlSessionFactoryBuilder");
    topLevelClass.addImportedType(Globar.pojoPath + "." + pojo);
    if ((daoName != null) && (daoName.length() > 0)) {
      topLevelClass.addImportedType(Globar.daoPath + "." + daoName);
    }
    sb.setLength(0);
    sb.append(Globar.pojoPath).append(".").append(pojo).append(Globar.exampleName);
    topLevelClass.addImportedType(sb.toString());

    topLevelClass.setVisibility(JavaVisibility.PUBLIC);

    Method method = new Method();
    method.setName("main");
    method.setStatic(true);
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(null);
    method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String[]"), "args"));
    method.addException(new FullyQualifiedJavaType("java.lang.Exception"));

    method.addBodyLine("SqlSession  session  = ConnectionFactory.getSession();");

    if (Globar.global.getDaoType().equalsIgnoreCase("annotation")) {
      method.addBodyLine("session.getConfiguration().addMapper(" + pojo + Globar.daoName + ".class);");
    }

    method.addBodyLine("//Get DAO");
    sb.setLength(0);
    String tempDaoName = daoName.substring(daoName.lastIndexOf(".") + 1);
    sb.append(tempDaoName).append(" ").append(Globar.daoName.toLowerCase()).append("  = session.getMapper(").append(
      tempDaoName).append(".class);");
    method.addBodyLine(sb.toString());

    method.addBodyLine("//Invoke DAO");
    sb.setLength(0);
    sb.append(pojo).append(Globar.exampleName).append(" example = new ").append(pojo).append(Globar.exampleName).append("();");
    method.addBodyLine(sb.toString());

    sb.setLength(0);
    sb
      .append("List ")
      .append(
      " list = ").append(Globar.daoName.toLowerCase()).append(".selectByExampleAndPage(example, new RowBounds(0,3));");
    method.addBodyLine(sb.toString());

    topLevelClass.addMethod(method);

    return ProjectFileWriteUtil.write(project, topLevelClass, filename);
    //return false;
  }

  public static boolean createTestFileWithSpring(IProject project, String pojo, String filename)
  {
    StringBuffer sb = new StringBuffer();
    //String daoName = pojo + Globar.daoName;

    if (DBUtil.isUnionKey(pojo)) {
      pojo = StringUtility.getTable(pojo) + "Key";
    }

    TopLevelClass topLevelClass = new TopLevelClass("com.test.TestDAOWithSpring");

    sb.setLength(0);
    sb.append("/*\n\nWith spring by page query\n");
    sb.append("\n");
    sb.append("Example parameter:\n");
    sb.append("     ShopExample example = new ShopExample();\n");
    sb.append("     Criteria c1 = example.createCriteria(); \n");
    sb.append("     Criteria c2 = example.createCriteria(); \n");
    sb.append("     example.or(c2); \n");
    sb.append("     c1.andSidBetween(1, 100); \n");
    sb.append("     c1.andSnameLike(\"my%\"); \n");
    sb.append("     c2.andSidIsNotNull(); \n");
    sb.append("     example.setOrderByClause(\"sprice desc\"); //sort field\t\t\n\n");
    sb.append("Page query\n");
    sb.append("     List list = service.selectByExampleAndPage(example, new RowBounds(0, 3));\n");
    sb.append("     \n\n*/");
    topLevelClass.addJavaDocLine(sb.toString());

    //topLevelClass.addImportedType("java.io.IOException");
    topLevelClass.addImportedType("java.util.List");
    //topLevelClass.addImportedType("java.util.Map");
    topLevelClass.addImportedType("org.springframework.context.ApplicationContext");
    //topLevelClass.addImportedType("org.apache.ibatis.session.RowBounds");
    topLevelClass.addImportedType("org.springframework.context.support.ClassPathXmlApplicationContext");

    topLevelClass.addImportedType(Globar.pojoPath + "." + pojo);
    /* if ((daoName != null) && (daoName.length() > 0)) {
      topLevelClass.addImportedType(Globar.daoPath + "." + daoName);
    }*/
    sb.setLength(0);
    sb.append(Globar.pojoPath).append(".").append(pojo).append(Globar.exampleName);
    topLevelClass.addImportedType(sb.toString());

    sb.setLength(0);
    sb.append(Globar.pojoPath).append(".").append("PageBean");
    topLevelClass.addImportedType(sb.toString());

    topLevelClass.setVisibility(JavaVisibility.PUBLIC);

    Method method = new Method();
    method.setName("main");
    method.setStatic(true);
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(null);
    method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String[]"), "args"));
    method.addException(new FullyQualifiedJavaType("java.lang.Exception"));

    method.addBodyLine("ApplicationContext context = new ClassPathXmlApplicationContext(\"applicationContext.xml\");");

    method.addBodyLine("// Get service");

    sb.setLength(0);
    sb.append(pojo).append("Service");

    String tempServiceName = sb.toString();
    topLevelClass.addImportedType(getPackage() +"."+ tempServiceName);
    sb.setLength(0);
    sb.append(tempServiceName)
      .append(" service = (")
      .append(tempServiceName)
      .append(")context.getBean(")
      .append(tempServiceName+".class")
      .append(");");
    method.addBodyLine(sb.toString());

    method.addBodyLine("// Invoke Service");

    sb.setLength(0);
    sb.append(pojo).append(Globar.exampleName).append(" example = new ").append(pojo).append(Globar.exampleName).append("();");
    method.addBodyLine(sb.toString());

    sb.setLength(0);
    sb
      .append("PageBean")
      .append(
      " pageBean = service.queryByPage(1,3,example);");
    method.addBodyLine(sb.toString());

    method.addBodyLine("System.out.println(\"record count:\"+pageBean.getRecordCount());");
    method.addBodyLine("System.out.println(\"page count:\"+pageBean.getPageCount());");
    method.addBodyLine("System.out.println(\"page size:\"+pageBean.getPageSize());");
    method.addBodyLine("System.out.println(\"current page:\"+pageBean.getCurrentPage());");

    sb.setLength(0);
    sb.append("List<").append(pojo).append("> list = (List<"+pojo+">)pageBean.getResultList();");
    method.addBodyLine(sb.toString());

    sb.setLength(0);
    sb.append("for (").append(pojo).append(" obj : list ){");
    method.addBodyLine(sb.toString());
    method.addBodyLine("System.out.println(obj);");
    method.addBodyLine("}");

    topLevelClass.addMethod(method);

    //IFile ifile = project.getFile(filename);
    return ProjectFileWriteUtil.write(project, topLevelClass, filename);
   //return false;
  }
  public static String getPackage() {
		String packageName = Globar.daoPath;
		packageName = packageName.substring(0, packageName.lastIndexOf("."));
		String servicePackageName = packageName + ".service";
		return servicePackageName;
	}
  public static void main(String[] args)
  {
  }
}