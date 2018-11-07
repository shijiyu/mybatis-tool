package org.mybatis.generator.internal.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.internal.util.messages.Messages;

public class ClassloaderUtility
{
  public static ClassLoader getCustomClassloader(List<String> entries)
  {
    List<URL> urls = new ArrayList<>();

    if (entries != null) {
      for (String classPathEntry : entries)
        if (classPathEntry != null)
        {
          File file = new File(classPathEntry);
          if (file.exists())
          {
            try
            {
              urls.add(file.toURI().toURL());
            }
            catch (MalformedURLException e) {
              throw new RuntimeException(Messages.getString(
                "RuntimeError.9", classPathEntry));
            }
          }
        }
    }
    ClassLoader parent = Thread.currentThread().getContextClassLoader();

    URLClassLoader ucl = new URLClassLoader((URL[])urls.toArray(new URL[
      urls.size()]), parent);

    return ucl;
  }
}