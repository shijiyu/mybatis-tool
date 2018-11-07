package ibator.dialect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SQLServerDialect extends Dialect
{
  public boolean supportsLimitOffset()
  {
    return false;
  }

  public boolean supportsLimit() {
    return true;
  }

  static int getAfterSelectInsertPoint(String sql) {
    int selectIndex = sql.toLowerCase().indexOf("select");
    int selectDistinctIndex = sql.toLowerCase().indexOf(
      "select distinct");
    return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
  }

  public String getLimitString(String sql, int offset, int limit) {
    return getLimitString(sql, offset, null, limit, null);
  }

  public String getLimitString(String querySelect, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    querySelect = querySelect.toLowerCase();

    int index = querySelect.indexOf("from");
    String table = querySelect.substring(index + 4);
    String tempTable = table.trim();
    if (tempTable.indexOf(" ") > -1)
      table = table.trim().substring(0, table.trim().indexOf(" "));
    table = table.trim();

    String pk_name = null;
    Object obj = getExample();
    Class<?> c = obj.getClass();
    try {
      Field pk_field = c.getDeclaredField("pk_name");
      pk_field.setAccessible(true);
      pk_name = (String)pk_field.get(obj);
    }
    catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(c.getName() + "类缺少pk_name属性值");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("pk_name属性类型必须是java.lang.String型");
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    String orderbyString = "";
    int orderIndex = querySelect.indexOf("order");
    if (orderIndex > -1) {
      orderbyString = querySelect.substring(orderIndex);
    }

    String whereString = "";
    int whereIndex = querySelect.indexOf("where");
    //int endWhereIndex = -1;
    if (whereIndex > -1) {
      try
      {
        Method method = c.getDeclaredMethod("getSQL", new Class[0]);
        whereString = (String)method.invoke(obj, new Object[0]);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }

    }

    String tempQuString = querySelect;

    tempQuString = tempQuString.substring(0, index);
    tempQuString = tempQuString + " from " + table;

    StringBuffer sb = new StringBuffer(tempQuString);

    sb.insert(7, "top " + limit + " ");

    if (whereIndex > -1) {
      sb.append(" ").append("where ").append(whereString);
    }

    if (offset < 1) {
      if (orderIndex > -1) {
        sb.append(" ").append(orderbyString);
      }
      return sb.toString();
    }

    if (whereIndex > -1)
      sb.append(" and ");
    else {
      sb.append(" ").append("where ");
    }

    sb.append(pk_name)
      .append(" not in (select top ")
      .append(offset)
      .append(" ").append(pk_name).append(" ")
      .append(" from ").append(table).append(" ");

    if (whereIndex > -1) {
      sb.append(" where ").append(whereString);
    }

    if (orderIndex > -1) {
      sb.append(" ").append(orderbyString);
    }

    sb.append(")");

    if (orderIndex > -1) {
      sb.append(" ").append(orderbyString);
    }

    return sb.toString();
  }
}