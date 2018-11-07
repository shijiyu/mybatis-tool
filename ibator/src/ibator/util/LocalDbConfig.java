package ibator.util;

import ibator.ui.EclipseUI;
import ibator.vo.ConfigVO;
import ibator.vo.DBVO;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jface.dialogs.MessageDialog;

public class LocalDbConfig
{
  private String file;

  public void changeDefault(String defaultName)
  {
    Document document = Dom4jUtil.readDocument(this.file);
    if (document == null)
      document = DocumentHelper.createDocument();
    Element root = document.getRootElement();
    if (root == null) {
      root = document.addElement("root");
    }
    Element dfName = (Element)root.selectSingleNode("/root/default");
    if (dfName == null)
      dfName = root.addElement("default");
    dfName.setText(defaultName);
    Dom4jUtil.writeDocument(document, this.file);
  }

  public LocalDbConfig(String file)
  {
    this.file = file;
  }

  public void writeLanguage(String language)
  {
    Document document = Dom4jUtil.readDocument(this.file);
    if (document == null)
      document = DocumentHelper.createDocument();
    Element root = document.getRootElement();
    if (root == null) {
      root = document.addElement("root");
    }
    Element param = (Element)document.selectSingleNode("/root/param");

    if (param == null)
    {
      param = root.addElement("param");
    }
    Element languageE = (Element)param.selectSingleNode("language");
    if (languageE == null)
      languageE = param.addElement("language");
    languageE.setText(language);

    Dom4jUtil.writeDocument(document, this.file);
  }

  @SuppressWarnings("unchecked")
public void writeDbConfig(String defaultName, DBVO vo, boolean isparam, boolean right)
  {
    Document document = Dom4jUtil.readDocument(this.file);
    if (document == null)
      document = DocumentHelper.createDocument();
    Element root = document.getRootElement();
    if (root == null) {
      root = document.addElement("root");
    }
    if (!isparam) {
      Element dfName = (Element)root.selectSingleNode("/root/default");
      if (dfName == null)
        dfName = root.addElement("default");
      dfName.setText(defaultName);

      Element element = (Element)document
        .selectSingleNode("/root/config[@name='" + vo.getName() + 
        "']");
      Element password;
      if (element == null)
      {
        element = root.addElement("config");
        Element config = element.addAttribute("name", vo.getName());
        Element username = config.addElement("username");
        username.setText(vo.getUsername());
        password = config.addElement("password");
        password.setText(vo.getPassword());
        Element driver = config.addElement("driver");
        driver.setText(vo.getDriver());
        Element url = config.addElement("url");
        url.setText(vo.getUrl());
        Element dialect = config.addElement("dialect");
        dialect.setText(vo.getDialect());
        Element driverUrl = config.addElement("driverUrl");
        driverUrl.setText(vo.getDriverUrl());
      }
      else {
        List<Element> list = element.elements();
        for (Element e : list) {
          if (e.getName().equals("username"))
            e.setText(vo.getUsername());
          else if (e.getName().equals("password"))
            e.setText(vo.getPassword());
          else if (e.getName().equals("driver"))
            e.setText(vo.getDriver());
          else if (e.getName().equals("url"))
            e.setText(vo.getUrl());
          else if (e.getName().equals("dialect"))
            e.setText(vo.getDialect());
          else if ((e.getName() != null) && 
            (e.getName().equals("driverUrl")) && (vo != null) && 
            (vo.getDriverUrl() != null) && 
            (vo.getDriverUrl().length() > 0)) {
            e.setText(vo.getDriverUrl());
          }
        }
      }
    }
    else
    {
      Element param = (Element)document.selectSingleNode("/root/param");

      if (param == null)
      {
        param = root.addElement("param");
      }

      if ((vo.getPojoPath() != null) && (vo.getPojoPath().length() > 0)) {
        Element pojoPath = (Element)param.selectSingleNode("pojoPath");
        if (pojoPath == null) {
          pojoPath = param.addElement("pojoPath");
        }
        pojoPath.setText(vo.getPojoPath());
      }

      if ((vo.getDaoPath() != null) && (vo.getDaoPath().length() > 0))
      {
        Element daoPath = (Element)param.selectSingleNode("daoPath");
        if (daoPath == null) {
          daoPath = param.addElement("daoPath");
        }
        daoPath.setText(vo.getDaoPath());
      }

      if ((vo.getSqlPath() != null) && (vo.getSqlPath().length() > 0))
      {
        Element sqlPath = (Element)param.selectSingleNode("sqlPath");
        if (sqlPath == null)
          sqlPath = param.addElement("sqlPath");
        sqlPath.setText(vo.getSqlPath());
      }

      Element comment = (Element)param.selectSingleNode("comment");
      if (comment == null)
        comment = param.addElement("comment");
      comment.setText(vo.isComment()+"");

      Element over = (Element)param.selectSingleNode("over");
      if (over == null)
        over = param.addElement("over");
      over.setText(vo.isOver()+"");

      Element cache = (Element)param.selectSingleNode("cache");
      if (cache == null)
        cache = param.addElement("cache");
      cache.setText(vo.isCache()+"");

      Element spring = (Element)param.selectSingleNode("spring");
      if (spring == null)
        spring = param.addElement("spring");
      spring.setText(vo.isSpring()+"");

      Element client = (Element)param.selectSingleNode("client");
      if (client == null)
        client = param.addElement("client");
      client.setText(vo.getClient());

      Element simple = (Element)param.selectSingleNode("simple");
      if (simple == null)
        simple = param.addElement("simple");
      simple.setText(vo.isSimple()+"");

      Element language = (Element)param.selectSingleNode("language");
      if (language == null)
        language = param.addElement("language");
      language.setText(vo.getLanguage());

      Element daoName = (Element)param.selectSingleNode("daoName");
      if (daoName == null)
        daoName = param.addElement("daoName");
      daoName.setText(vo.getDaoName());

      Element exampleName = (Element)param.selectSingleNode("exampleName");
      if (exampleName == null)
        exampleName = param.addElement("exampleName");
      exampleName.setText(vo.getExampleName());
    }
    Dom4jUtil.writeDocument(document, this.file);
  }

  public DBVO getParam()
  {
    Document document = Dom4jUtil.readDocument(this.file);
    DBVO vo = null;
    if (document == null) {
      return null;
    }
    vo = new DBVO();
    Element param = (Element)document.selectSingleNode("/root/param");
    if (param == null) {
      return null;
    }
    if (param.elementText("cache") != null) {
      vo.setCache(new Boolean(param.elementText("cache")).booleanValue());
    }
    if (param.elementText("comment") != null) {
      vo.setComment(new Boolean(param.elementText("comment")).booleanValue());
    }
    if (param.elementText("over") != null) {
      vo.setOver(new Boolean(param.elementText("over")).booleanValue());
    }
    if (param.elementText("spring") != null) {
      vo.setSpring(new Boolean(param.elementText("spring")).booleanValue());
    }
    if (param.elementText("client") != null) {
      vo.setClient(param.elementText("client"));
    }
    if (param.elementText("daoPath") != null) {
      vo.setDaoPath(param.elementText("daoPath"));
    }
    if (param.elementText("pojoPath") != null) {
      vo.setPojoPath(param.elementText("pojoPath"));
    }
    if (param.elementText("sqlPath") != null) {
      vo.setSqlPath(param.elementText("sqlPath"));
    }
    if (param.elementText("simple") != null) {
      vo.setSimple(new Boolean(param.elementText("simple")).booleanValue());
    }
    if (param.elementText("language") != null) {
      vo.setLanguage(param.elementText("language"));
    }
    if (param.elementText("daoName") != null) {
      vo.setDaoName(param.elementText("daoName"));
    }
    if (param.elementText("exampleName") != null)
      vo.setExampleName(param.elementText("exampleName"));
    return vo;
  }

  public List<DBVO> readDbConfig()
  {
    List<DBVO> list = new ArrayList<>();
    Document document = Dom4jUtil.readDocument(this.file);
    String defaultName = "";
    if (document != null) {
      Element dName = (Element)document
        .selectSingleNode("/root/default");
      if (dName != null) {
        defaultName = dName.getText();
      }
     @SuppressWarnings("unchecked")
	List<Element> es = document.selectNodes("/root/config");
      if (es != null)
        for (Element e : es) {
          DBVO vo = new DBVO();
          String username = e.elementText("username");
          String password = e.elementText("password");
          String driver = e.elementText("driver");
          String url = e.elementText("url");
          String name = e.attributeValue("name");
          String dialect = e.elementText("dialect");
          String driverUrl = e.elementText("driverUrl");

          vo.setName(name);
          vo.setUrl(url);
          vo.setUsername(username);
          vo.setPassword(password);
          vo.setDriver(driver);
          vo.setDriverUrl(driverUrl);
          vo.setDialect(dialect);
          vo.setDname(defaultName);
          list.add(vo);
        }
    }
    return list;
  }

  public void createIbatsConfig3(ConfigVO vo)
  {
  }

  public void removeDbConfig(String name)
  {
    Document document = Dom4jUtil.readDocument(this.file);
    if (document == null) {
      return;
    }
    Element element = (Element)document
      .selectSingleNode("/root/config[@name='" + name + "']");
    if (element != null) {
      boolean f = MessageDialog.openConfirm(null, "Ibator.com", EclipseUI.getMessage("dbconfig_msg4"));
      if (f)
        element.getParent().remove(element);
      Dom4jUtil.writeDocument(document, this.file);
    }
  }
}