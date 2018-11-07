package ibator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultNamespace;

import ibator.Globar;

public class SpringConfigUtil
{
  public static void writeSpring(File dir, List<String> springDao)
  {
    if ((dir == null) || (!dir.exists())) {
      return;
    }

    File file = new File(dir, "applicationContext.xml");

    Map<String,String> map = new HashMap<>();
    map.put("lgh", "http://www.springframework.org/schema/beans");
    map.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    map.put("xmlns:aop", "http://www.springframework.org/schema/aop");
    map.put("xmlns:tx", "http://www.springframework.org/schema/tx");
    map.put("xmlns:p", "http://www.springframework.org/schema/p");
    map.put("xmlns:context", "http://www.springframework.org/schema/context");
    map
      .put(
      "xsi:schemaLocation", 
      "http://www.springframework.org/schema/beans  http://www.springframework.org/schema/beans/spring-beans-2.5.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd  http://www.springframework.org/schema/aop  http://www.springframework.org/schema/aop/spring-aop-2.5.xsd  http://www.springframework.org/schema/tx  http://www.springframework.org/schema/tx/spring-tx-2.5.xsd");

    SAXReader saxReader = new SAXReader();

    saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
    Document document = null;
    try
    {
      document = saxReader.read(new InputStreamReader(new FileInputStream(file), "utf-8"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (document == null) {
      document = DocumentHelper.createDocument();
    }

    Element beans = document.getRootElement();

    if ((beans == null) || (!beans.getName().equals("beans"))) {
      beans = document.addElement(
        QName.get("beans", "", 
        (String)map.get("lgh")));
      //Namespace namespace1 = DocumentFactory.getInstance().createNamespace("",  "http://www.springframework.org/schema/beans");
      Namespace namespace2 = new DefaultNamespace("xsi", 
        (String)map.get("xmlns:xsi"));
      Namespace namespace3 = new DefaultNamespace("aop", 
        (String)map.get("xmlns:aop"));
      Namespace namespace4 = new DefaultNamespace("tx", 
        (String)map.get("xmlns:tx"));

      Namespace namespace5 = new DefaultNamespace("p", 
        (String)map.get("xmlns:p"));

      Namespace namespace6 = new DefaultNamespace("context", 
        (String)map.get("xmlns:context"));

      beans.add(namespace3);
      beans.add(namespace4);
      beans.add(namespace5);
      beans.add(namespace6);
      beans.add(namespace2);
      beans.addAttribute(
        QName.get("schemaLocation", "xsi", 
        "http://www.w3.org/2001/XMLSchema-instance"), 
        (String)map.get("xsi:schemaLocation"));
    }

    Element servicElement = (Element)beans.selectSingleNode("context:component-scan");
    if (servicElement == null) {
      beans.addComment(" - - - - - 扫描业务层包，支持织入DAO -  - - -  - ");
      servicElement = beans.addElement("context:component-scan");
      servicElement.addAttribute("base-package", getPackage());
    }

    XPath path = document.createXPath("//lgh:bean[@id='datasource'");
    path.setNamespaceURIs(map);

    Element datasource = (Element)path.selectSingleNode(document);
    if (datasource == null) {
      beans.addComment(" - - - - - - - Apache 连接池 - - - - - - - ");
      datasource = beans.addElement("bean");
    }

    datasource.addAttribute("id", "datasource");
    datasource.addAttribute("class", 
      "org.apache.commons.dbcp.BasicDataSource");

    path = document.createXPath("//lgh:property[@name='driverClassName']");
    Element driver = (Element)path.selectSingleNode(document);
    if (driver == null)
      driver = datasource.addElement("property");
    driver.addAttribute("name", "driverClassName");
    driver.addAttribute("value", Globar.global.getDbVo().getDriver());

    path = document.createXPath("//lgh:property[@name='username']");
    Element username = (Element)path.selectSingleNode(document);
    if (username == null)
      username = datasource.addElement("property");
    username.addAttribute("name", "username");
    username.addAttribute("value", Globar.global.getDbVo().getUsername());

    path = document.createXPath("//lgh:property[@name='password']");
    Element password = (Element)path.selectSingleNode(document);

    if (password == null)
      password = datasource.addElement("property");
    password.addAttribute("name", "password");
    password.addAttribute("value", Globar.global.getDbVo().getPassword());

    path = document.createXPath("//lgh:property[@name='url']");
    Element url = (Element)path.selectSingleNode(document);
    if (url == null)
      url = datasource.addElement("property");
    url.addAttribute("name", "url");
    url.addAttribute("value", Globar.global.getDbVo().getUrl());

    path = document.createXPath("//lgh:bean[@id='sqlSessionFactory']");
    Element sessionFactory = (Element)path.selectSingleNode(document);

    if (sessionFactory == null) {
      beans
        .addComment(" - - - - - - - sessionFactory - - - - - - - ");
      sessionFactory = beans.addElement("bean");
    }

    sessionFactory.addAttribute("id", "sqlSessionFactory");
    sessionFactory.addAttribute("class", 
      "org.mybatis.spring.SqlSessionFactoryBean");

    path = document.createXPath("//lgh:property[@name='dataSource']");
    Element ds = (Element)path.selectSingleNode(document);

    if (ds == null)
      ds = sessionFactory.addElement("property");
    ds.addAttribute("name", "dataSource");
    ds.addAttribute("ref", "datasource");

    path = document.createXPath("//lgh:property[@name='configLocation']");
    Element configLocation = (Element)path.selectSingleNode(document);

    if (configLocation == null)
      configLocation = sessionFactory.addElement("property");
    configLocation.addAttribute("name", "configLocation");
    configLocation.addAttribute("value", "classpath:sqlMapConfig.xml");

    path = document.createXPath("//lgh:bean[@id='transactionManager']");
    Element transactionManager = (Element)path.selectSingleNode(document);
    if (transactionManager == null) {
      beans
        .addComment(" - - - - - - spring 声明式事务 - - - - - - - ");
      transactionManager = beans.addElement("bean");
    }
    transactionManager.addAttribute("id", "transactionManager");
    transactionManager
      .addAttribute("class", 
      "org.springframework.jdbc.datasource.DataSourceTransactionManager");
    transactionManager.clearContent();

    Element pDataSource = transactionManager.addElement("property");
    pDataSource.addAttribute("name", "dataSource");
    pDataSource.addAttribute("ref", "datasource");

    Element weave = null;
    Element tx = null;
    @SuppressWarnings("unchecked")
	List<Element> list = beans.elements();
    if (list != null)
      for (Element element : list) {
        if ((element.getName().equals("advice")) && 
          (element.attributeValue("id") != null) && 
          (element.attributeValue("id").equals(
          "transactionAdvice")))
          tx = element;
        if ((element.getName().equals("config")) && 
          (element.element("advisor") != null))
        {
          if (element.element("advisor").attributeValue(
            "advice-ref").equals("transactionAdvice"))
            weave = element; 
        }
      }
    if (tx == null) {
      beans
        .addComment(" - - - - - - spring 事务属性 - - - - - - - ");
      tx = beans.addElement("tx:advice");
    }
    tx.addAttribute("id", "transactionAdvice");
    tx.addAttribute("transaction-manager", "transactionManager");
    tx.clearContent();

    Element txAttribute = tx.addElement("tx:attributes");
    txAttribute.clearContent();
    txAttribute.addElement("tx:method").addAttribute("name", "*");

    if (weave == null) {
      beans.addComment("******    织入，请修改成实际的业务层包名  *********");

      weave = beans.addElement("aop:config");
    }

    weave.clearContent();
    weave.addElement("aop:advisor").addAttribute("advice-ref", 
      "transactionAdvice").addAttribute("pointcut", 
      "execution(* com.service.*.*(..))");

    int i = 0;
    if (springDao != null)
    {
      for (String dao : springDao) {
        if ((dao != null) && (dao.length() >= 1))
        {
          i++;
          String daoName = dao.replaceFirst(dao.charAt(0)+"", (char)(dao.charAt(0) + ' ')+"");

          path = document
            .createXPath("//lgh:bean[@id='" + daoName + "']");
          Element beanDao = (Element)path.selectSingleNode(document);

          if (beanDao == null) {
            if (i == 1)
              beans.addComment(" - - - - - - DAO - - - - - - - ");
            beanDao = beans.addElement("bean");
            beanDao.addAttribute("id", daoName);
            beanDao.addAttribute("class", 
              "org.mybatis.spring.mapper.MapperFactoryBean");
            Element baseDao2 = (Element)beanDao
              .selectSingleNode("property[@name='sessionFactory']");
            if (baseDao2 == null)
              baseDao2 = beanDao.addElement("property");
            baseDao2.addAttribute("name", "sqlSessionFactory");
            baseDao2.addAttribute("ref", "sqlSessionFactory");

            Element mapperInterface = (Element)beanDao
              .selectSingleNode("property[@name='mapperInterface']");
            if (mapperInterface == null)
              mapperInterface = beanDao.addElement("property");
            mapperInterface.addAttribute("name", "mapperInterface");
            mapperInterface.addAttribute("value", Globar.daoPath + "." + 
              dao);
          }
        }
      }
    }
    Dom4jUtil.writeDocument(document, file);
  }
  public static String getPackage() {
		String packageName = Globar.daoPath;
		packageName = packageName.substring(0, packageName.lastIndexOf("."));
		String servicePackageName = packageName + ".service";
		return servicePackageName;
	}
}