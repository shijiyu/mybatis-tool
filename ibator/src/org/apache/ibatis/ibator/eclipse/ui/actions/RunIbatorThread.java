package org.apache.ibatis.ibator.eclipse.ui.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.EclipseProgressCallback;
import org.apache.ibatis.ibator.api.EclipseShellCallback;
import org.apache.ibatis.ibator.internal.db.ConnectionFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.db.SqlReservedWords;

import ibator.Activator;
import ibator.Globar;
import ibator.util.DBUtil;

public class RunIbatorThread
  implements IWorkspaceRunnable
{
  private IProject iproject;
  protected List<String> warnings;

  public RunIbatorThread(IProject iproject, List<String> warnings)
  {
    this.iproject = iproject;
    this.warnings = warnings;
  }

  public void run(IProgressMonitor monitor)
    throws CoreException
  {
    SubMonitor subMonitor = SubMonitor.convert(monitor, 1000);
    subMonitor.beginTask("Generating mybatis :", 1000);

    Globar.isWord = true;
    try
    {
      String projectName = this.iproject.getName();
      subMonitor.subTask("Parsing Configuration");

      //Properties properties = new Properties();

      Configuration config = new Configuration();

      for (String path : Globar.classpath) {
        config.addClasspathEntry(path);
      }

      Context context = new Context(
        ModelType.CONDITIONAL);

      if (Globar.isWord) {
        context.addProperty("autoDelimitKeywords", "true");

        if (Globar.global.getDbVo().getDialect().toUpperCase()
          .startsWith("MYSQL")) {
          context.addProperty("beginningDelimiter", "`");
          context.addProperty("endingDelimiter", "`");
        }
        else if (Globar.global.getDbVo().getDialect().toUpperCase()
          .startsWith("SQLSERVER")) {
          context.addProperty("beginningDelimiter", "[");
          context.addProperty("endingDelimiter", "]");
        } else {
          context.addProperty("beginningDelimiter", "\"");
          context.addProperty("endingDelimiter", "\"");
        }

      }

      JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();

      jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
      jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
      jdbc.setPassword(Globar.global.getDbVo().getPassword());
      jdbc.setUserId(Globar.global.getDbVo().getUsername());
      context.setJdbcConnectionConfiguration(jdbc);
      Connection con = 
        ConnectionFactory.getInstance().getConnection(jdbc);
      String catalog = con.getCatalog();
      String name;
      if (Globar.tables.size() == 0)
      {
        if (Globar.global.getDbVo().getDialect()
          .startsWith("SQLServer")) {
          ResultSet rs = con.getMetaData()
            .getTables(catalog, null, "%", 
            new String[] { "TABLE" });
          while ((rs != null) && (rs.next())) {
            name = rs.getString("table_name");
            TableConfiguration tconfig = new TableConfiguration(
              context);
            tconfig.setTableName(name);

            context.addTableConfiguration(tconfig);

            if ((Globar.isWord) && 
              (SqlReservedWords.containsWord(name))) {
              tconfig.setDelimitIdentifiers(Globar.isWord);
            }

            DBUtil.setTableConfig(con, tconfig);
          }
          rs.close();
        } else {
          TableConfiguration configuration = new TableConfiguration(
            context);
          configuration.setSchema(
            Globar.global.getDbVo().getUsername());

          if ((Globar.isWord) && 
            (SqlReservedWords.containsWord(configuration.getTableName()))) {
            configuration.setDelimitIdentifiers(Globar.isWord);
          }

          DBUtil.setTableConfig(con, configuration);

          context.addTableConfiguration(configuration);
        }
      }
      else
      {
        for (String table : Globar.tables)
        {
          TableConfiguration configuration = new TableConfiguration(
            context);

          if (!Globar.global.getDbVo().getDialect().startsWith(
            "SQLServer")) {
            configuration.setSchema(
              Globar.global.getDbVo().getUsername());
          }

          if ((Globar.isWord) && 
            (SqlReservedWords.containsWord(table))) {
            configuration.setDelimitIdentifiers(Globar.isWord);
          }

          configuration.setTableName(table);
          DBUtil.setTableConfig(con, configuration);
          context.addTableConfiguration(configuration);
        }
      }

      context.setId("ibator");

      JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
      javaModelGeneratorConfiguration.setTargetPackage(Globar.pojoPath);
      javaModelGeneratorConfiguration.setTargetProject(projectName);
      context
        .setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

      JavaClientGeneratorConfiguration daoGeneratorConfiguration = new JavaClientGeneratorConfiguration();
      daoGeneratorConfiguration.setTargetPackage(Globar.daoPath);
      daoGeneratorConfiguration.setTargetProject(projectName);

      String type = Globar.global.getDaoType();
      if (type.equals("xml"))
        daoGeneratorConfiguration.setConfigurationType("XMLMAPPER");
      else if (type.equals("mixed-mapper"))
        daoGeneratorConfiguration.setConfigurationType("MIXEDMAPPER");
      else if (type.equals("annotation")) {
        daoGeneratorConfiguration
          .setConfigurationType("ANNOTATEDMAPPER");
      }

      context
        .setJavaClientGeneratorConfiguration(daoGeneratorConfiguration);

      context.setTargetRuntime(IntrospectedTable.TargetRuntime.MYBATIS3.toString());

      SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
      sqlMapGeneratorConfiguration.setTargetPackage(Globar.xmlPath);
      sqlMapGeneratorConfiguration.setTargetProject(projectName);
      context
        .setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

      CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();

      context
        .setCommentGeneratorConfiguration(commentGeneratorConfiguration);

      if (Globar.global.isComment())
        org.mybatis.generator.internal.DefaultCommentGenerator.suppressAllComments = false;
      else {
        org.mybatis.generator.internal.DefaultCommentGenerator.suppressAllComments = true;
      }

      config.addContext(context);

      context.getJdbcConnectionConfiguration().addProperty("remarks", "true");

      subMonitor.worked(50);

      con.close();
      monitor.subTask("正在生成文件，请稍候...");
      EclipseShellCallback callback = new EclipseShellCallback();
      if (Globar.global.isOverride())
        callback.setOverwriteEnabled(true);
      else {
        callback.setMergeSupported(true);
      }
      SubMonitor spm = subMonitor.newChild(950);
      MyBatisGenerator ibator = new MyBatisGenerator(config, callback, 
        new ArrayList<>());
      ibator.generate(new EclipseProgressCallback(spm));
    }
    catch (Exception e)
    {
      Status status = new Status(4, "org.apache.ibatis.ibator.eclipse.ui", 
        4, e.getMessage(), e);
      Activator.getDefault().getLog().log(status);

      MultiStatus multiStatus = new MultiStatus(
        "org.apache.ibatis.ibator.eclipse.ui", 
        4, 
        "Invalid Configuration\n  See Details for more Information", 
        null);

      throw new CoreException(multiStatus);
    } finally {
      monitor.done();
    }
  }
}