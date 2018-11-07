package ibator.vo;

public class DBVO
{
  private String username;
  private String password;
  private String url;
  private String driver;
  private String dialect;
  private String name;
  private String dname;
  private String driverUrl;
  private String pojoPath;
  private String daoPath;
  private String sqlPath;
  private boolean comment;
  private boolean over;
  private boolean cache;
  private boolean spring;
  private boolean simple;
  private String client;
  private String language;
  private String daoName;
  private String exampleName;
  private String dialectClass;

  public String getDialectClass()
  {
    return this.dialectClass;
  }

  public void setDialectClass(String dialectClass) {
    this.dialectClass = dialectClass;
  }

  public String getDname() {
    return this.dname;
  }

  public void setDname(String dname) {
    this.dname = dname;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDriver() {
    return this.driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getDialect() {
    return this.dialect;
  }

  public void setDialect(String dialect) {
    this.dialect = dialect;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DBVO()
  {
  }

  public DBVO(String username, String password, String url, String driver, String dialect, String name)
  {
    this.username = username;
    this.password = password;
    this.url = url;
    this.driver = driver;
    this.dialect = dialect;
    this.name = name;
  }

  public String getDriverUrl() {
    return this.driverUrl;
  }

  public void setDriverUrl(String driverUrl) {
    this.driverUrl = driverUrl;
  }

  public String getPojoPath() {
    return this.pojoPath;
  }

  public void setPojoPath(String pojoPath) {
    this.pojoPath = pojoPath;
  }

  public String getDaoPath() {
    return this.daoPath;
  }

  public void setDaoPath(String daoPath) {
    this.daoPath = daoPath;
  }

  public String getSqlPath() {
    return this.sqlPath;
  }

  public void setSqlPath(String sqlPath) {
    this.sqlPath = sqlPath;
  }

  public boolean isComment() {
    return this.comment;
  }

  public void setComment(boolean comment) {
    this.comment = comment;
  }

  public boolean isOver() {
    return this.over;
  }

  public void setOver(boolean over) {
    this.over = over;
  }

  public boolean isCache() {
    return this.cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public boolean isSpring() {
    return this.spring;
  }

  public void setSpring(boolean spring) {
    this.spring = spring;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public boolean isSimple() {
    return this.simple;
  }

  public void setSimple(boolean simple) {
    this.simple = simple;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getDaoName() {
    return this.daoName;
  }

  public void setDaoName(String daoName) {
    this.daoName = daoName;
  }

  public String getExampleName() {
    return this.exampleName;
  }

  public void setExampleName(String exampleName) {
    this.exampleName = exampleName;
  }
}