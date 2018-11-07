package ibator.dialect;

public abstract class Dialect
{
  private Object example;

  public Object getExample()
  {
    return this.example;
  }

  public void setExample(Object example) {
    this.example = example;
  }

  public abstract boolean supportsLimit();

  public abstract boolean supportsLimitOffset();

  public String getLimitString(String sql, int offset, int limit)
  {
    return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
  }

  public abstract String getLimitString(String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3);
}