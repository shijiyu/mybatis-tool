package ibator.vo;

public class ConfigVO
{
  private DBVO dbVo;
  private String daoType = "xml";
  private boolean comment;
  private String projectName;
  private boolean override;
  private boolean page;
  private String sqlMapPath;
  private String pojoPath;
  private int version;

  public int getVersion()
  {
    return this.version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public DBVO getDbVo() {
    return this.dbVo;
  }

  public void setDbVo(DBVO dbVo) {
    this.dbVo = dbVo;
  }

  public String getDaoType() {
    return this.daoType;
  }

  public void setDaoType(String daoType) {
    this.daoType = daoType;
  }

  public boolean isComment() {
    return this.comment;
  }

  public void setComment(boolean comment) {
    this.comment = comment;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public boolean isOverride() {
    return this.override;
  }

  public void setOverride(boolean override) {
    this.override = override;
  }

  public boolean isPage() {
    return this.page;
  }

  public void setPage(boolean page) {
    this.page = page;
  }

  public String getSqlMapPath() {
    return this.sqlMapPath;
  }

  public void setSqlMapPath(String sqlMapPath) {
    this.sqlMapPath = sqlMapPath;
  }

  public String getPojoPath() {
    return this.pojoPath;
  }

  public void setPojoPath(String pojoPath) {
    this.pojoPath = pojoPath;
  }
}