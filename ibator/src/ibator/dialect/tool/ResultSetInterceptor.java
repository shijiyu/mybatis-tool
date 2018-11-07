package ibator.dialect.tool;

import java.util.Properties;
import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;

@Intercepts({@org.apache.ibatis.plugin.Signature(type=org.apache.ibatis.executor.resultset.ResultSetHandler.class, method="handleResultSets", args={java.sql.Statement.class})})
public class ResultSetInterceptor
  implements Interceptor
{
  public Object intercept(Invocation invocation)
    throws Throwable
  {
    FastResultSetHandler resultSet = (FastResultSetHandler)invocation.getTarget();

    RowBounds rowBounds = (RowBounds)ReflectUtil.getFieldValue(resultSet, 
      "rowBounds");

    if ((rowBounds.getLimit() > 0) && 
      (rowBounds.getLimit() < 2147483647))
    {
      ReflectUtil.setFieldValue(resultSet, "rowBounds", new RowBounds());
    }
    return invocation.proceed();
  }

  public Object plugin(Object target)
  {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties)
  {
  }
}