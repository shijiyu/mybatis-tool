package ibator.dialect.tool;

import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;

import ibator.dialect.Dialect;

@Intercepts({@org.apache.ibatis.plugin.Signature(type=org.apache.ibatis.executor.statement.StatementHandler.class, method="prepare", args={java.sql.Connection.class})})
public class StatementInterceptor
  implements Interceptor
{
  private String DIALECT;

  public Object intercept(Invocation invocation)
    throws Throwable
  {
    RoutingStatementHandler statement = (RoutingStatementHandler)invocation.getTarget();
    PreparedStatementHandler handler = (PreparedStatementHandler)ReflectUtil.getFieldValue(statement, 
      "delegate");
    RowBounds rowBounds = (RowBounds)ReflectUtil.getFieldValue(handler, 
      "rowBounds");

    if ((rowBounds.getLimit() > 0) && 
      (rowBounds.getLimit() < 2147483647))
    {
      BoundSql boundSql = statement.getBoundSql();
      String sql = boundSql.getSql();

      Dialect dialect = 
        (Dialect)Class.forName(this.DIALECT)
        .newInstance();

      Object obj = statement.getParameterHandler().getParameterObject();
      if ("ibator.dialect.SQLServerDialect".equalsIgnoreCase(this.DIALECT)) {
        if (obj == null) {
          throw new RuntimeException("SQL2000 第一个参数不能为空");
        }
        dialect.setExample(obj);
      }

      sql = dialect.getLimitString(sql, 
        rowBounds.getOffset(), 
        rowBounds.getLimit());

      if ("ibator.dialect.SQLServerDialect".equalsIgnoreCase(this.DIALECT))
      {
        ReflectUtil.setFieldValue(boundSql, "additionalParameters", null);
        ReflectUtil.setFieldValue(boundSql, "metaParameters", null);
        ReflectUtil.setFieldValue(boundSql, "parameterMappings", null);
        ReflectUtil.setFieldValue(boundSql, "parameterObject", null);
      }

      ReflectUtil.setFieldValue(boundSql, "sql", sql);
    }
    return invocation.proceed();
  }

  public Object plugin(Object target)
  {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties)
  {
    this.DIALECT = properties.getProperty("dialect");
  }
}