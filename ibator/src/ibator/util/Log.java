package ibator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log
{
  private static File f = new File("e:/a.log");
  private static FileWriter fileWriter = null;

  public static void openLog() {
    if (!f.exists())
      try {
        f.createNewFile();
        fileWriter = new FileWriter(f);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    else
      try {
        fileWriter = new FileWriter(f, true);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
  }

  public static void info(String msg)
  {
    try {
      if (msg == null)
        msg = "无";
      fileWriter.write(msg + "\n\r");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void closeFile() {
    try {
      if (fileWriter != null)
        fileWriter.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}