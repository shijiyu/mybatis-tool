package ibator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ibator.Globar;
import ibator.vo.ConfigVO;

public class SqlConfigUtil
{
  public static void createIbatsConfig3(ConfigVO vo, File file, List<String> map)
  {
    Document document = null;
    try {
      document = new SAXReader().read(new InputStreamReader(new FileInputStream(file), "utf-8"));
    } catch (Exception localException) {
    }
    if (document == null) {
      document = DocumentHelper.createDocument();

      document.addDocType("configuration", 
        "-//mybatis.org//DTD Config 3.0//EN", 
        "mybatis-3-config.dtd");
    }
    Element root = document.getRootElement();
    if (root == null) {
      root = document.addElement("configuration");
    }

    Element settings = (Element)root.selectSingleNode("/configuration/settings");
    if (settings == null) {
      root.addComment(" - - - - - - 懒加载和缓存 - - - - - - - - - - ");
      settings = root.addElement("settings");
    }

    Element setting3 = (Element)settings.selectSingleNode("setting[@name='cacheEnabled']");
    if (setting3 == null) {
      setting3 = settings.addElement("setting");
      setting3.addAttribute("name", "cacheEnabled");
      setting3.addAttribute("value", "true");
    }

    Element setting1 = (Element)settings.selectSingleNode("setting[@name='lazyLoadingEnabled']");
    if (setting1 == null) {
      setting1 = settings.addElement("setting");
      setting1.addAttribute("name", "lazyLoadingEnabled");
      setting1.addAttribute("value", "true");
    }

    Element setting2 = (Element)settings.selectSingleNode("setting[@name='aggressiveLazyLoading']");
    if (setting2 == null) {
      setting2 = settings.addElement("setting");
      setting2.addAttribute("name", "aggressiveLazyLoading");
      setting2.addAttribute("value", "false");
    }

    String dialectClass = DBUtil.getDialect(vo.getDbVo());
    if (dialectClass != null) {
      Element plugins = (Element)root.selectSingleNode("plugins");
      if (plugins == null)
      {
        root.addComment(" - - - - - - - 物理分页拦截器 - - - - - - - ");
        plugins = root.addElement("plugins");
      }

      Element plugin1 = (Element)plugins
        .selectSingleNode("plugin[@interceptor='ibator.dialect.tool.ResultSetInterceptor']");
      if (plugin1 == null) {
        plugin1 = plugins.addElement("plugin");
        plugin1.addAttribute("interceptor", 
          "ibator.dialect.tool.ResultSetInterceptor");
      }

      Element plugin2 = (Element)plugins
        .selectSingleNode("plugin[@interceptor='ibator.dialect.tool.StatementInterceptor']");
      if (plugin2 == null) {
        plugin2 = plugins.addElement("plugin");
        plugin2.addAttribute("interceptor", 
          "ibator.dialect.tool.StatementInterceptor");
      }

      Element dialect = (Element)plugin2
        .selectSingleNode("property[@name='dialect']");
      if (dialect == null) {
        dialect = plugin2.addElement("property");
        dialect.addAttribute("name", "dialect");
        dialect.addAttribute("value", dialectClass);
      }
    }
    Element transactionManager;
    if (!Globar.spring)
    {
      Element environments = (Element)root
        .selectSingleNode("environments");
      if (environments == null) {
        root.addComment(" - - - - - - 数据库环境配置- - - - - - - - - ");
        environments = root.addElement("environments");
      }
      environments.addAttribute("default", "environments");

      Element environment = (Element)environments
        .selectSingleNode("environment");
      if (environment == null) {
        environment = environments.addElement("environment");
      }
      environment.addAttribute("id", vo.getDbVo().getDname());

      transactionManager = (Element)environment
        .selectSingleNode("transactionManager");
      if (transactionManager == null)
        transactionManager = environment
          .addElement("transactionManager");
      transactionManager.addAttribute("type", "JDBC");

      Element datasource = (Element)environment
        .selectSingleNode("dataSource");
      if (datasource == null)
        datasource = environment.addElement("dataSource");
      datasource.addAttribute("type", "POOLED");

      Element driver = (Element)datasource
        .selectSingleNode("property[@name='driver']");
      Element url = (Element)datasource
        .selectSingleNode("property[@name='url']");
      Element username = (Element)datasource
        .selectSingleNode("property[@name='username'");
      Element password = (Element)datasource
        .selectSingleNode("property[@name='password']");
      if (driver == null)
        driver = datasource.addElement("property");
      if (url == null)
        url = datasource.addElement("property");
      if (username == null)
        username = datasource.addElement("property");
      if (password == null)
        password = datasource.addElement("property");
      username.addAttribute("name", "username");
      username.addAttribute("value", vo.getDbVo().getUsername());
      password.addAttribute("name", "password");
      password.addAttribute("value", vo.getDbVo().getPassword());
      driver.addAttribute("name", "driver");
      driver.addAttribute("value", vo.getDbVo().getDriver());
      url.addAttribute("name", "url");
      url.addAttribute("value", vo.getDbVo().getUrl());
    }

    if ((map != null) && 
      (!Globar.global.getDaoType().equalsIgnoreCase("annotation")))
    {
      Element mappers = (Element)root.selectSingleNode("mappers");
      if (mappers == null) {
        root.addComment(" - - - - - - -映射文件路径- - - - - - ");
        mappers = root.addElement("mappers");
      }

      for (String m : map)
        if (mappers.selectSingleNode("mapper[@resource='" + m + "']") == null)
        {
          Element mm = mappers.addElement("mapper");
          mm.addAttribute("resource", m);
        }
    }
    Dom4jUtil.writeDocument(document, file);
  }
}