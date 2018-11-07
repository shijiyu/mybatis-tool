package ibator.util;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InitializationBlock;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import ibator.Globar;
public class ConnectionFactoryUtil
{

public static boolean createFile(IProject project, String filename)
  {
    //StringBuffer sb = new StringBuffer();

    TopLevelClass topLevelClass = new TopLevelClass(
      new FullyQualifiedJavaType("com.db.ConnectionFactory"));
    topLevelClass.addImportedType("java.io.IOException");
    topLevelClass.addImportedType("org.apache.ibatis.io.Resources");
    topLevelClass.addImportedType("org.apache.ibatis.session.SqlSession");
    topLevelClass
      .addImportedType("org.apache.ibatis.session.SqlSessionFactory");
    topLevelClass
      .addImportedType("org.apache.ibatis.session.SqlSessionFactoryBuilder");
    topLevelClass.addImportedType("java.io.Reader");
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    Field field = new Field();
    field.setName("factory");
    field.setStatic(true);
    field.setType(new FullyQualifiedJavaType(
      "org.apache.ibatis.session.SqlSessionFactory"));
    field.setVisibility(JavaVisibility.PRIVATE);
    topLevelClass.addField(field);

    InitializationBlock block = new InitializationBlock(true);
    block.addBodyLine("try {");
    block
      .addBodyLine("Reader reader = Resources.getResourceAsReader(\"sqlMapConfig.xml\");");
    block
      .addBodyLine("factory = new SqlSessionFactoryBuilder().build(reader, \"" + 
      Globar.global.getDbVo().getDname() + "\");");
    block.addBodyLine("} catch (IOException e) {");
    block.addBodyLine("e.printStackTrace();");
    block.addBodyLine("}");
    topLevelClass.addInitializationBlock(block);

    Method method = new Method();
    method.setName("getSession");
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(new FullyQualifiedJavaType(
      "org.apache.ibatis.session.SqlSession"));
    method.setStatic(true);
    method.addBodyLine("return factory.openSession();");
    topLevelClass.addMethod(method);

    method = new Method();
    method.setName("T getMapper");
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(new FullyQualifiedJavaType("<T>"));
    method.setStatic(true);
    method.addParameter(new Parameter(new FullyQualifiedJavaType(
      "java.lang.Class"), "mapper"));
    method.addBodyLine("SqlSession session = getSession();");
    if (Globar.global.getDaoType().equals("annotation")) {
      method.addBodyLine("session.getConfiguration().addMapper(mapper);");
    }
    method.addBodyLine("return (T) session.getMapper(mapper);");
    topLevelClass.addMethod(method);

   /* IFile ifile = project.getFile(filename);
    try
    {
      if (ifile.exists())
        ifile.delete(true, null);
      InputStream is = new StringBufferInputStream(
        topLevelClass.getFormattedContent());
      ifile.create(is, true, null);

      return true;
    } catch (CoreException e) {
      e.printStackTrace();
    }
*/
     return ProjectFileWriteUtil.write(project, topLevelClass, filename);
  }
}