package ibator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.internal.IbatorObjectFactory;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.ClassloaderUtility;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import ibator.Globar;
import ibator.vo.DBVO;

public class DBUtil
{
  private Connection con = null;
  private String catalog = null;
  private String schema = null;

  private List<String> all = new ArrayList<>();
  private List<String> list = new ArrayList<>();

  private static List<DBVO> vos = new ArrayList<>();

  static { DBVO MYSQL = new DBVO();
    MYSQL.setDialect("MySQL");
    MYSQL.setName("MySQL");
    MYSQL.setDriver("com.mysql.jdbc.Driver");
    MYSQL.setUrl("jdbc:mysql://127.0.0.1:3306/test");
    MYSQL.setPassword("admin");
    MYSQL.setUsername("root");
    MYSQL.setDialectClass("ibator.dialect.MySQLDialect");
    vos.add(MYSQL);

    DBVO MSSQLSERVER = new DBVO();
    MSSQLSERVER.setDialect("SQLServer2000");
    MSSQLSERVER.setName("SQLServer2000");
    MSSQLSERVER.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    MSSQLSERVER.setUrl("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=mydb");
    MSSQLSERVER.setPassword("sasa");
    MSSQLSERVER.setUsername("sa");
    MSSQLSERVER.setDialectClass("ibator.dialect.SQLServerDialect");
    vos.add(MSSQLSERVER);

    DBVO MSSQLSERVER2005 = new DBVO();
    MSSQLSERVER2005.setDialect("SQLServer2005");
    MSSQLSERVER2005.setName("SQLServer2005");
    MSSQLSERVER2005
      .setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    MSSQLSERVER2005
      .setUrl("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=mydb");
    MSSQLSERVER2005.setPassword("sasa");
    MSSQLSERVER2005.setUsername("sa");
    MSSQLSERVER2005.setDialectClass("ibator.dialect.SQLServer2005Dialect");
    vos.add(MSSQLSERVER2005);

    DBVO MSSQLSERVER2008 = new DBVO();
    MSSQLSERVER2008.setDialect("SQLServer2008");
    MSSQLSERVER2008.setName("SQLServer2008");
    MSSQLSERVER2008
      .setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    MSSQLSERVER2008
      .setUrl("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=mydb");
    MSSQLSERVER2008.setPassword("sasa");
    MSSQLSERVER2008.setUsername("sa");
    MSSQLSERVER2008.setDialectClass("ibator.dialect.SQLServer2008Dialect");
    vos.add(MSSQLSERVER2008);

    DBVO ORACLE = new DBVO();
    ORACLE.setDialect("Oracle");
    ORACLE.setName("Oracle");
    ORACLE.setDriver("oracle.jdbc.driver.OracleDriver");
    ORACLE.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ora92");
    ORACLE.setPassword("tiger");
    ORACLE.setUsername("scott");
    ORACLE.setDialectClass("ibator.dialect.OracleDialect");
    vos.add(ORACLE);

    DBVO DB2 = new DBVO();
    DB2.setDialect("DB2");
    DB2.setName("DB2");
    DB2.setDriver("com.ibm.db2.jdbc.app.DB2Driver");
    DB2.setUrl("jdbc:db2://127.0.0.1/50000:mydb");
    DB2.setPassword("password");
    DB2.setUsername("username");
    DB2.setDialectClass("ibator.dialect.DB2Dialect");
    vos.add(DB2);

    DBVO Sybase = new DBVO();
    Sybase.setDialect("Sybase");
    Sybase.setName("Sybase");
    Sybase.setDriver("com.ibm.db2.jdbc.app.DB2Driver");
    Sybase.setUrl("jdbc:sybase:Tds:127.0.0.1:4500/mydb");
    Sybase.setPassword("password");
    Sybase.setUsername("username");
    Sybase.setDialectClass("ibator.dialect.SybaseDialect");
    vos.add(Sybase);

    DBVO Informix = new DBVO();
    Informix.setDialect("Informix");
    Informix.setName("Informix");
    Informix.setDriver("com.informix.jdbc.IfxDriver");
    Informix
      .setUrl("jdbc:informix-sqli://127.0.0.1:1533/myDB:INFORMIXSERVER=myserver");
    Informix.setPassword("password");
    Informix.setUsername("username");
    Informix.setDialectClass("");
    vos.add(Informix);

    DBVO PostgreSQL = new DBVO();
    PostgreSQL.setDialect("PostgreSQL");
    PostgreSQL.setName("PostgreSQL");
    PostgreSQL.setDriver("org.postgresql.Driver");
    PostgreSQL.setUrl("jdbc:postgresql://localhost/mydb");
    PostgreSQL.setPassword("password");
    PostgreSQL.setUsername("username");
    PostgreSQL.setDialectClass("ibator.dialect.PostgreSQLDialect");
    vos.add(PostgreSQL);

    DBVO Access = new DBVO();
    Access.setDialect("Access");
    Access.setName("Access");
    Access.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
    Access
      .setUrl("dbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=F:\\data.mdb");
    Access.setPassword("password");
    Access.setUsername("username");
    vos.add(Access);

    DBVO HSqlDb = new DBVO();
    HSqlDb.setDialect("HSqlDb");
    HSqlDb.setName("HSqlDb");
    HSqlDb.setDriver("org.hsqldb.jdbcDriver");
    HSqlDb.setUrl("jdbc:hsqldb:mem:aname");
    HSqlDb.setPassword("password");
    HSqlDb.setUsername("sa");
    HSqlDb.setDialectClass("ibator.dialect.HSQLDialect");
    vos.add(HSqlDb);
  }

  public void closeDb()
  {
    try
    {
      if ((this.con != null) && (!this.con.isClosed()))
        this.con.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public synchronized void getCon()
  {
    org.mybatis.generator.config.JDBCConnectionConfiguration jdbc = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());
    try
    {
      this.con = 
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc);
      if (!Globar.global.getDbVo().getDialect().startsWith("SQLServer")) {
        this.schema = Globar.global.getDbVo().getUsername();
      }

      this.catalog = this.con.getCatalog();
    }
    catch (Exception localException)
    {
    }
  }

  public List<String> getList()
  {
    return this.list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }

  public static Map<String, String> updateOracle(String catalog, String schema, String table)
  {
    Map<String,String> map = new HashMap<>();

    if (Globar.global.getDbVo().getDialect().toUpperCase().startsWith("ORACLE")) {
      catalog = null;
      schema = null;
      if (table != null)
        table = table.toUpperCase();
      int index = table.lastIndexOf(".");
      if (index > -1)
        table = table.substring(index + 1);
    }
    map.put("catalog", catalog);
    map.put("schema", schema);
    map.put("table", table);
    return map;
  }

  public synchronized void getAllTable2(String table)
  {
    Map<String,String> map = updateOracle(this.catalog, this.schema, table);
    table = (String)map.get("table");
    this.catalog = ((String)map.get("catalog"));
    this.schema = ((String)map.get("schema"));

    if (!this.all.contains(table))
      this.list.add(table);
    if (!this.all.contains(table))
      this.all.add(table);
    try
    {
      DatabaseMetaData dmd = this.con.getMetaData();
      ResultSet rs = dmd.getExportedKeys(this.catalog, this.schema, table);
      while (rs.next()) {
        String temp = rs.getString(7);

        if (!this.all.contains(temp))
          this.list.add(temp);
      }
      rs = dmd.getImportedKeys(this.catalog, this.schema, table);
      while (rs.next()) {
        String temp = rs.getString(3);

        if (!this.all.contains(temp))
          this.list.add(temp);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    this.list.remove(table);

    for (int i = 0; i < this.list.size(); i++)
      getAllTable2((String)this.list.get(i));
  }

  public static synchronized void generatorKey(String table, TopLevelClass topLevelClass)
  {
    //TableVo vo = getKeys(table);
    if ((Globar.tables.size() > 0) && (!Globar.hasTable(table))) {
      return;
    }

    if (TableVo.isPk()) {
      topLevelClass.addImportedType("java.util.Set");
      topLevelClass.addImportedType("java.util.HashSet");
      for (TableVo.KeyInfo keyInfo : TableVo.getPkeys()) {
        String fkTableName = keyInfo.getFktablename();
        String fkKey = keyInfo.getFkname();
        String fieldName = StringUtility.getColumn(fkTableName) + "s" + StringUtility.getTable(fkKey);
        String type = "java.util.Set<" + 
          StringUtility.getTable(fkTableName) + ">";
        if (isUnionKey(fkTableName)) {
          type = "java.util.Set<" + 
            StringUtility.getTable(fkTableName) + 
            "Key>";
        }
        JavaBeansUtil.generatorJavaBean(fieldName, type, topLevelClass);
      }

    }

    if (TableVo.isFk())
    {
      for (TableVo.KeyInfo keyInfo : TableVo.getFkeys()) {
        String pkTableName = keyInfo.getPktablename();
       // String pkKey = StringUtility.getColumn(keyInfo.getPkname());
        String fieldType = StringUtility.getTable(pkTableName);
        String pojo = Globar.pojoPath + "." + fieldType;
        topLevelClass.addImportedType(pojo);
        JavaBeansUtil.generatorJavaBean(StringUtility.getColumn(pkTableName) + StringUtility.getTable(keyInfo.getFkname()), pojo, 
          topLevelClass);
      }
    }
  }

  public static List<String> getPrimarykey(String table)
  {
    List<String> list = new ArrayList<String>();
    String catalog = null;
    String schema = null;

    Map<String,String> map = updateOracle(catalog, schema, table);
    table = (String)map.get("table");

    org.mybatis.generator.config.JDBCConnectionConfiguration jdbc = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());
    Connection con = null;
    try {
      con = 
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc);
      if (!Globar.global.getDbVo().getDialect().startsWith("SQLServer")) {
        schema = Globar.global.getDbVo().getUsername();
      }

      catalog = con.getCatalog();

      DatabaseMetaData db = con.getMetaData();

      ResultSet rsPk = db.getPrimaryKeys(null, null, table);

      //int count = 0;

      while (rsPk.next()) {
        list.add(rsPk.getString("COLUMN_NAME"));
      }

      rsPk.close();

      con.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public static synchronized boolean isUnionKey(String table)
  {
    return getPrimarykey(table).size() == 2;
  }

  public static synchronized TableVo getKeys(String tablename)
  {
    String catalog = null;
    String schema = null;

    Map<String,String> map = updateOracle(catalog, schema, tablename);
    tablename = (String)map.get("table");
    catalog = (String)map.get("catalog");
    schema = (String)map.get("schema");

    org.mybatis.generator.config.JDBCConnectionConfiguration jdbc = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());
    Connection con = null;
    try {
      con = 
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc);

      catalog = con.getCatalog();

      DatabaseMetaData db = con.getMetaData();
      ResultSet rsPk = db.getExportedKeys(catalog, schema, tablename);
      ResultSet rsFk = db.getImportedKeys(catalog, schema, tablename);
      TableVo.getFkeys().clear();
      TableVo.getPkeys().clear();

      while (rsFk.next()) {
        TableVo.KeyInfo keyInfo = new TableVo.KeyInfo();
        keyInfo.setPkname(rsFk.getString(4));
        keyInfo.setPktablename(rsFk.getString(3));
        keyInfo.setFkname(rsFk.getString(8));
        keyInfo.setFktablename(rsFk.getString(7));

        if ((Globar.tables.size() == 0) || 
          (Globar.hasTable(keyInfo.getPktablename())))
        {
          TableVo.getFkeys().add(keyInfo);
        }
      }
      int count = 0;
      while (rsPk.next()) {
        TableVo.KeyInfo keyInfo = new TableVo.KeyInfo();
        keyInfo.setPkname(rsPk.getString(4));
        keyInfo.setPktablename(rsPk.getString(3));
        keyInfo.setFkname(rsPk.getString(8));
        keyInfo.setFktablename(rsPk.getString(7));

        if ((Globar.tables.size() == 0) || 
          (Globar.hasTable(keyInfo.getFktablename())))
        {
          TableVo.getPkeys().add(keyInfo);
        }count++;
        if (count >= 2) {
          TableVo.setUnionKey(true);
        }

      }

      rsPk.close();
      rsFk.close();
      con.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    return new TableVo();
  }

  public static String getDialect(DBVO vo)
  {
    List<DBVO> list = getDb();
    if (list != null) {
      for (DBVO v : list) {
        if (v.getDialect().equalsIgnoreCase(vo.getDialect())) {
          return v.getDialectClass();
        }
      }
    }
    return null;
  }

  public static List<DBVO> getDb()
  {
    return vos;
  }

  public static List<String> getAllTable()
  {
    org.mybatis.generator.config.JDBCConnectionConfiguration jdbc = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());
    List<String> list = new ArrayList<>();
    Globar.tables.clear();
    try {
      String catalog = 
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc).getCatalog();
      ResultSet rs = 
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc).getMetaData().getTables(
        catalog, null, "%", new String[] { "TABLE" });
      if (rs == null) return list; 
    	while(rs.next()){
    		String name = rs.getString("table_name");
    		list.add(name);
    	 }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      try
      {
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().close();
      }
      catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    finally
    {
      try
      {
        org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public static void setTableConfig(Connection con, TableConfiguration tc)
  {
    String table = tc.getTableName();
    //String catlog = tc.getCatalog();
    //String schema = tc.getSchema();

    List<String> keys = new ArrayList<>();
    ResultSet rs = null;
    DatabaseMetaData databaseMetaData = null;
    try {
      databaseMetaData = con.getMetaData();
      rs = databaseMetaData.getPrimaryKeys(
        null, null, table);
      while (rs.next()){
          String columnName = rs.getString("COLUMN_NAME");
          keys.add(columnName);
        }
     
      rs.close();
    } catch (SQLException e) {
      return;
    }
    if (keys.size() != 1) return;
    String key = (String)keys.get(0);
    String column = getIdentity(table);
    boolean isIdentiy = false;
    if (key.equalsIgnoreCase(column)) {
      isIdentiy = true;
    }
    String dialect = Globar.global.getDbVo().getDialect();
    String dialectDB = null;
    String[] dialects = { "DB2", "MySQL", "SqlServer", "Derby", "Cloudscape", "HSQLDB", "SYBASE", "Informix" };
    for (String str : dialects) {
      if (dialect.toUpperCase().startsWith(str.toUpperCase())) {
        dialectDB = str;
        break;
      }
    }
    String type = isIdentiy ? "AFTER" : "BEFORE";

    GeneratedKey gk = new GeneratedKey(key, dialectDB, isIdentiy, type);
    tc.setGeneratedKey(gk);
  }

  public static String getIdentity(String tablename)
  {
    org.mybatis.generator.internal.db.ConnectionFactory connectionFactory = org.mybatis.generator.internal.db.ConnectionFactory.getInstance();
    org.apache.ibatis.ibator.config.JDBCConnectionConfiguration jdbc = new org.apache.ibatis.ibator.config.JDBCConnectionConfiguration();
    jdbc.setDriverClass(Globar.global.getDbVo().getDriver());
    jdbc.setPassword(Globar.global.getDbVo().getPassword());
    jdbc.setConnectionURL(Globar.global.getDbVo().getUrl());
    jdbc.setUserId(Globar.global.getDbVo().getUsername());
    try
    {
      Connection con = connectionFactory.getConnection(jdbc);
      PreparedStatement ps = con.prepareStatement("select   *   from   " + 
        tablename);
      ResultSet rs = ps.executeQuery();
      ResultSetMetaData resultSetMetaData = rs.getMetaData();
      int count = resultSetMetaData.getColumnCount();
      for (int i = 1; i <= count; i++) {
        boolean f = resultSetMetaData.isAutoIncrement(i);
        if (f) {
          return resultSetMetaData.getColumnLabel(i);
        }
      }

      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static boolean connectionDb(DBVO vo)
  {
    org.mybatis.generator.config.JDBCConnectionConfiguration jdbc = new org.mybatis.generator.config.JDBCConnectionConfiguration();
    jdbc.setConnectionURL(vo.getUrl());
    jdbc.setUserId(vo.getUsername());
    jdbc.setPassword(vo.getPassword());
    jdbc.setDriverClass(vo.getDriver());

    if ((vo != null) && (vo.getDriverUrl() != null) && 
      (vo.getDriverUrl().length() > 0))
    {
      IbatorConfiguration configuration = new IbatorConfiguration();
      configuration.addClasspathEntry(vo.getDriverUrl());
      ClassLoader classLoader = 
        ClassloaderUtility.getCustomClassloader(configuration.getClassPathEntries());
      IbatorObjectFactory.setExternalClassLoader(classLoader);
    }

    try
    {
      org.apache.ibatis.ibator.internal.db.ConnectionFactory.getInstance().getConnection(jdbc);

      return true;
    } catch (Exception e1) {
    }
    return false;
  }

  public List<String> getAll()
  {
    return this.all;
  }

  public void setAll(List<String> all) {
    this.all = all;
  }
}