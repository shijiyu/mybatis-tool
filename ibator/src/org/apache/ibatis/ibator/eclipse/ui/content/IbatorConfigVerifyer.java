package org.apache.ibatis.ibator.eclipse.ui.content;

import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IbatorConfigVerifyer extends DefaultHandler
{
  private InputStream inputStream;
  private boolean isIbatorConfig;
  private boolean rootElementRead;

  public IbatorConfigVerifyer(InputStream inputStream)
  {
    this.inputStream = inputStream;
    this.isIbatorConfig = false;
  }

  public boolean isIbatorConfigFile() {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(false);
      SAXParser parser = factory.newSAXParser();

      parser.parse(this.inputStream, this);
    }
    catch (Exception localException)
    {
    }

    return this.isIbatorConfig;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (this.rootElementRead) {
      throw new SAXException("Root element was not ibatorConfiguration");
    }

    this.rootElementRead = true;

    if ("ibatorConfiguration".equals(qName)) {
      this.isIbatorConfig = true;
      throw new SAXException("Ignore the rest of the file");
    }
  }

  public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
    if (!"-//Apache Software Foundation//DTD Apache iBATIS Ibator Configuration 1.0//EN".equals(publicId)) {
      throw new SAXException("Not an Ibator configuration file");
    }

    StringReader nullStringReader = new StringReader("");
    return new InputSource(nullStringReader);
  }
}