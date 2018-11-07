package ibator;

import ibator.vo.ConfigVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Globar
{
  public static final ConfigVO global = new ConfigVO();
  public static final List<String> classpath = new ArrayList<>();
  public static String pojoPath = "com.ibator.pojos";
  public static String xmlPath = "com.ibator.pojos";
  public static String daoPath = "com.ibator.dao";
  public static boolean spring = false;
  public static boolean isWord = true;
  public static boolean isCache = false;
  public static String daoName = "Mapper";
  public static boolean ISSIMPLE = false;
  public static String language = "en";
  public static String exampleName = "Example";

  public static final Set<String> tables = new HashSet<>();

  public static boolean hasTable(String table) {
    for (String t : tables) {
      if (t.equalsIgnoreCase(table))
        return true;
    }
    return false;
  }
}