package ibator.dialect;

public class SQLServer2008Dialect extends SQLServerDialect
{
  public boolean supportsLimit()
  {
    return true;
  }

  public boolean supportsLimitOffset()
  {
    return true;
  }

  public String getLimitString(String querySqlString, int offset, int limit)
  {
    if (offset == 0) return super.getLimitString(querySqlString, offset, limit);

    StringBuilder sb = new StringBuilder(querySqlString.trim());

    String querySqlLowered = querySqlString.trim().toLowerCase();
    int orderByIndex = querySqlLowered.toLowerCase().indexOf("order by");
    String orderby = orderByIndex > 0 ? querySqlString.substring(orderByIndex) : "ORDER BY CURRENT_TIMESTAMP";

    if (orderByIndex > 0) sb.delete(orderByIndex, orderByIndex + orderby.length());

    int selectIndex = querySqlLowered.trim().startsWith("select distinct") ? 15 : 6;

    sb.insert(selectIndex, " ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__,");

    sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
    sb.append("WHERE __hibernate_row_nr__ ");
    if (offset > 0) sb.append("BETWEEN ").append(offset).append(" AND ").append(limit); else {
      sb.append(" <= ").append(limit);
    }

    return sb.toString();
  }
}