package ibator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtil
{
  public static Document readDocument(String file)
  {
    return readDocument(new File(file));
  }

  public static Document readDocument(File file)
  {
    SAXReader saxReader = new SAXReader();
    try
    {
      return saxReader.read(file);
    } catch (Exception e) {
    }
    return null;
  }

  public static void writeDocument(Document document, File file)
  {
    try
    {
      OutputFormat format = new OutputFormat("  ", true);
      format.setLineSeparator("\n\r");

      OutputFormat outputFormat = OutputFormat.createPrettyPrint();
      Writer os = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
      outputFormat.setNewlines(true);
      outputFormat.setIndent(true);
      outputFormat.setIndentSize(4);

      XMLWriter xmlWriter = new XMLWriter(os, outputFormat);
      xmlWriter.write(document);
      xmlWriter.flush();
      os.close();
      xmlWriter.close();
    } catch (Exception e) {
      throw new RuntimeException("写xml出错了:" + e.getMessage());
    }
  }

  public static void writeDocument(Document document, String file) {
    writeDocument(document, new File(file));
  }

  public static String getDialect3(Document document)
  {
    Element dialectProperty = (Element)document.selectSingleNode("/configuration/properties/property[@name='dialect']");
    if (dialectProperty == null)
      throw new RuntimeException("方言获取失败，请重新配置");
    return dialectProperty.getTextTrim();
  }

  public static Map<String,String> getConnectionProperty3(Document document)
  {
    Element username = (Element)document.selectSingleNode("/configuration/environments/environment/property[@name='username']");
    Element password = (Element)document.selectSingleNode("/configuration/environments/environment/property[@name='password']");
    Element url = (Element)document.selectSingleNode("/configuration/environments/environment/property[@name='url']");
    Element driver = (Element)document.selectSingleNode("/configuration/environments/environment/property[@name='driver']");
    if ((username == null) || (password == null) || (url == null) || (driver == null)) {
      throw new RuntimeException("配置文件没有:用户名，密码，连接字符串，驱动类一个或多个");
    }
    Map<String,String> map = new HashMap<>();
    map.put("username", username.attributeValue("value"));
    map.put("password", password.attributeValue("value"));
    map.put("url", url.attributeValue("value"));
    map.put("driver", driver.attributeValue("value"));

    return map;
  }

  public static Element getDom(Document document, String path, Element[] parent)
  {
    Node node = document.selectSingleNode(path);
    if (node == null) {
      int end = path.indexOf('[');
      if (end == -1)
        path = path.substring(path.lastIndexOf("/") + 1);
      else
        path = path.substring(path.lastIndexOf("/") + 1, end);
      if ((parent == null) || (parent.length == 0))
        node = document.addElement(path);
      else {
        node = parent[0].addElement(path);
      }
    }
    return (Element)node;
  }
}