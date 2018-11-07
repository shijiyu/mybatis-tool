package ibator.dialect;

public class TestDalect
{
  public static void main(String[] args)
  {
    String sql = "select * from dep";
    Dialect dialect = new OracleDialect();
    String newSQL = dialect.getLimitString(sql, 1, 3);
    System.out.println(newSQL);
  }
}