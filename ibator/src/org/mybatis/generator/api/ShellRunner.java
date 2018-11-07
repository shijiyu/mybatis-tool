package org.mybatis.generator.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.messages.Messages;
import org.mybatis.generator.logging.LogFactory;

public class ShellRunner
{
  private static final String CONFIG_FILE = "-configfile";
  private static final String OVERWRITE = "-overwrite";
  private static final String CONTEXT_IDS = "-contextids";
  private static final String TABLES = "-tables";
  private static final String VERBOSE = "-verbose";
  private static final String FORCE_JAVA_LOGGING = "-forceJavaLogging";
  private static final String HELP_1 = "-?";
  private static final String HELP_2 = "-h";

  public static void main(String[] args)
  {
    if (args.length == 0) {
      usage();
      System.exit(0);
      return;
    }

    Map<String,String> arguments = parseCommandLine(args);

    if (arguments.containsKey(HELP_1)) {
      usage();
      System.exit(0);
      return;
    }

    if (!arguments.containsKey(CONFIG_FILE)) {
      writeLine(Messages.getString("RuntimeError.0"));
      return;
    }

    List<String> warnings = new ArrayList<>();

    String configfile = (String)arguments.get("CONFIG_FILE");
    File configurationFile = new File(configfile);
    if (!configurationFile.exists()) {
      writeLine(Messages.getString("RuntimeError.1", configfile));
      return;
    }

    Set<String> fullyqualifiedTables = new HashSet<String>();
    if (arguments.containsKey(TABLES)) {
      StringTokenizer st = new StringTokenizer((String)arguments.get(TABLES), ",");
      while (st.hasMoreTokens()) {
        String s = st.nextToken().trim();
        if (s.length() > 0) {
          fullyqualifiedTables.add(s);
        }
      }
    }

    Set<String> contexts = new HashSet<>();
    if (arguments.containsKey(CONTEXT_IDS)) {
      StringTokenizer st = new StringTokenizer(
        (String)arguments.get(CONTEXT_IDS), ",");
      while (st.hasMoreTokens()) {
        String s = st.nextToken().trim();
        if (s.length() > 0)
          contexts.add(s);
      }
    }
    //String error;
    try
    {
      ConfigurationParser cp = new ConfigurationParser(warnings);
      Configuration config = cp.parseConfiguration(configurationFile);

      DefaultShellCallback shellCallback = new DefaultShellCallback(
        arguments.containsKey(OVERWRITE));

      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

      ProgressCallback progressCallback = arguments.containsKey(VERBOSE) ? new VerboseProgressCallback() : 
        null;

      myBatisGenerator.generate(progressCallback, contexts, fullyqualifiedTables);
    }
    catch (XMLParserException e) {
      writeLine(Messages.getString("Progress.3"));
      writeLine();
      for (String error1 : e.getErrors()) {
        writeLine(error1);
      }

      return;
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    } catch (IOException e) {
      e.printStackTrace();
      return;
    } catch (InvalidConfigurationException e) {
      writeLine(Messages.getString("Progress.16"));
      /*for (DefaultShellCallback shellCallback = e.getErrors().iterator(); shellCallback.hasNext(); ) { error = (String)shellCallback.next();
        writeLine(error);
      }*/
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
    }

    for (String warning : warnings) {
      writeLine(warning);
    }

    if (warnings.size() == 0) {
      writeLine(Messages.getString("Progress.4"));
    } else {
      writeLine();
      writeLine(Messages.getString("Progress.5"));
    }
  }

  private static void usage() {
    String lines = Messages.getString("Usage.Lines");
    int iLines = Integer.parseInt(lines);
    for (int i = 0; i < iLines; i++) {
      String key = "Usage." + i;
      writeLine(Messages.getString(key));
    }
  }

  private static void writeLine(String message) {
    System.out.println(message);
  }

  private static void writeLine() {
    System.out.println();
  }

  private static Map<String, String> parseCommandLine(String[] args) {
    List<String> errors = new ArrayList<>();
    Map<String,String> arguments = new HashMap<>();

    for (int i = 0; i < args.length; i++) {
      if ("CONFIG_FILE".equalsIgnoreCase(args[i])) {
        if (i + 1 < args.length)
          arguments.put("CONFIG_FILE", args[(i + 1)]);
        else {
          errors.add(Messages.getString(
            "RuntimeError.19", "CONFIG_FILE"));
        }
        i++;
      } else if (OVERWRITE.equalsIgnoreCase(args[i])) {
        arguments.put("OVERWRITE", "Y");
      } else if (VERBOSE.equalsIgnoreCase(args[i])) {
        arguments.put(VERBOSE, "Y");
      } else if (HELP_1.equalsIgnoreCase(args[i])) {
        arguments.put(HELP_1, "Y");
      } else if (HELP_2.equalsIgnoreCase(args[i]))
      {
        arguments.put(HELP_1, "Y");
      } else if (FORCE_JAVA_LOGGING.equalsIgnoreCase(args[i])) {
        LogFactory.forceJavaLogging();
      } else if (CONTEXT_IDS.equalsIgnoreCase(args[i])) {
        if (i + 1 < args.length)
          arguments.put(CONTEXT_IDS, args[(i + 1)]);
        else {
          errors.add(Messages.getString(
            "RuntimeError.19", CONTEXT_IDS));
        }
        i++;
      } else if (TABLES.equalsIgnoreCase(args[i])) {
        if (i + 1 < args.length)
          arguments.put(TABLES, args[(i + 1)]);
        else {
          errors.add(Messages.getString("RuntimeError.19", "TABLES"));
        }
        i++;
      } else {
        errors.add(Messages.getString("RuntimeError.20", args[i]));
      }
    }

    if (!errors.isEmpty()) {
      for (String error : errors) {
        writeLine(error);
      }

      System.exit(-1);
    }

    return arguments;
  }
}