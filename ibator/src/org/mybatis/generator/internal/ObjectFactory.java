package org.mybatis.generator.internal;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class ObjectFactory
{
  private static ClassLoader externalClassLoader;

  private static ClassLoader getClassLoader()
  {
    if (externalClassLoader != null) {
      return externalClassLoader;
    }
    return Thread.currentThread().getContextClassLoader();
  }

  public static synchronized void setExternalClassLoader(ClassLoader classLoader)
  {
    externalClassLoader = classLoader;
  }

  public static Class<?> externalClassForName(String type)
    throws ClassNotFoundException
  {
    Class<?> clazz;
    try
    {
      clazz = getClassLoader().loadClass(type);
    }
    catch (Throwable e)
    {
      clazz = null;
    }

    if (clazz == null) {
      clazz = Class.forName(type);
    }

    return clazz;
  }

  public static Object createExternalObject(String type)
  {
	Object answer;
    try
    {
      Class<?> clazz = externalClassForName(type);

      answer = clazz.newInstance();
    }
    catch (Exception e)
    {
      throw new RuntimeException(Messages.getString(
        "RuntimeError.6", type), e);
    }
    
    return answer;
  }

  public static Class<?> internalClassForName(String type) throws ClassNotFoundException
  {
    Class<?> clazz = null;
    try
    {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      clazz = cl.loadClass(type);
    }
    catch (Exception localException)
    {
    }
    if (clazz == null) {
      clazz = Class.forName(type);
    }

    return clazz;
  }

  public static Object createInternalObject(String type) {
    Object answer = null;
    try
    {
      Class<?> clazz = internalClassForName(type);

      answer = clazz.newInstance();
    }
    catch (Exception localException)
    {
    }

    return answer;
  }

  public static JavaTypeResolver createJavaTypeResolver(Context context, List<String> warnings)
  {
    JavaTypeResolverConfiguration config = context
      .getJavaTypeResolverConfiguration();
    String type;
    if ((config != null) && (config.getConfigurationType() != null)) {
      type = config.getConfigurationType();
      if ("DEFAULT".equalsIgnoreCase(type))
        type = JavaTypeResolverDefaultImpl.class.getName();
    }
    else {
      type = JavaTypeResolverDefaultImpl.class.getName();
    }

    JavaTypeResolver answer = (JavaTypeResolver)createInternalObject(type);
    answer.setWarnings(warnings);

    if (config != null) {
      answer.addConfigurationProperties(config.getProperties());
    }

    answer.setContext(context);

    return answer;
  }

  public static Plugin createPlugin(Context context, PluginConfiguration pluginConfiguration)
  {
    Plugin plugin = (Plugin)createInternalObject(pluginConfiguration
      .getConfigurationType());
    plugin.setContext(context);
    plugin.setProperties(pluginConfiguration.getProperties());
    return plugin;
  }

  public static CommentGenerator createCommentGenerator(Context context)
  {
    CommentGeneratorConfiguration config = context
      .getCommentGeneratorConfiguration();
    String type;
    if ((config == null) || (config.getConfigurationType() == null))
      type = DefaultCommentGenerator.class.getName();
    else {
      type = config.getConfigurationType();
    }

    CommentGenerator answer = (CommentGenerator)createInternalObject(type);

    if (config != null) {
      answer.addConfigurationProperties(config.getProperties());
    }

    return answer;
  }

  public static IntrospectedTable createIntrospectedTable(TableConfiguration tableConfiguration, FullyQualifiedTable table, Context context)
  {
    IntrospectedTable answer = createIntrospectedTableForValidation(context);
    answer.setFullyQualifiedTable(table);
    answer.setTableConfiguration(tableConfiguration);

    return answer;
  }

  public static IntrospectedTable createIntrospectedTableForValidation(Context context)
  {
    String type = context.getTargetRuntime();
    if (!StringUtility.stringHasValue(type))
      type = IntrospectedTableMyBatis3Impl.class.getName();
    else if (!"Ibatis2Java2".equalsIgnoreCase(type))
    {
      if (!"Ibatis2Java5".equalsIgnoreCase(type))
      {
        if ("Ibatis3".equalsIgnoreCase(type))
          type = IntrospectedTableMyBatis3Impl.class.getName();
        else if ("MyBatis3".equalsIgnoreCase(type))
          type = IntrospectedTableMyBatis3Impl.class.getName();
      }
    }
    IntrospectedTable answer = (IntrospectedTable)createInternalObject(type);
    answer.setContext(context);

    return answer;
  }

  public static IntrospectedColumn createIntrospectedColumn(Context context) {
    String type = context.getIntrospectedColumnImpl();
    if (!StringUtility.stringHasValue(type)) {
      type = IntrospectedColumn.class.getName();
    }

    IntrospectedColumn answer = (IntrospectedColumn)createInternalObject(type);
    answer.setContext(context);

    return answer;
  }
}