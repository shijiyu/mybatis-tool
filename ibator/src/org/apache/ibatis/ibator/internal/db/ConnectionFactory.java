package org.apache.ibatis.ibator.internal.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.ibator.internal.IbatorObjectFactory;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class ConnectionFactory
{
  private static ConnectionFactory instance = new ConnectionFactory();
  private static Connection con;

  public static ConnectionFactory getInstance()
  {
    return instance;
  }

  public Connection getConnection(JDBCConnectionConfiguration config)
    throws SQLException
  {
    Driver driver = getDriver(config);

    Properties props = new Properties();

    if (StringUtility.stringHasValue(config.getUserId())) {
      props.setProperty("user", config.getUserId());
    }

    if (StringUtility.stringHasValue(config.getPassword())) {
      props.setProperty("password", config.getPassword());
    }

    props.putAll(config.getProperties());

    Connection conn = driver.connect(config.getConnectionURL(), props);

    if (conn == null) {
      throw new SQLException(Messages.getString("RuntimeError.7"));
    }
    con = conn;
    return conn;
  }

  public static boolean getDriver(String driverClass)
  {
    try
    {
      IbatorObjectFactory.externalClassForName(driverClass);
      return true; } catch (Exception e) {
    }
    return false;
  }

  private Driver getDriver(JDBCConnectionConfiguration connectionInformation)
  {
   Driver driver;
    String driverClass = connectionInformation.getDriverClass();
    try
    {
      Class<?> clazz = IbatorObjectFactory.externalClassForName(driverClass);
      driver = (Driver)clazz.newInstance();
    }
    catch (Exception e)
    {

      e.printStackTrace();
      throw new RuntimeException(Messages.getString("RuntimeError.8"), e);
    }
    return driver;
  }

  public void close() {
    try {
      if ((con != null) && (!con.isClosed()))
        try {
          con.close();
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
}