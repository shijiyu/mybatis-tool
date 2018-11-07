package ibator.util;

import java.util.ArrayList;
import java.util.List;

public class TableVo
{
  private static List<KeyInfo> pkeys = new ArrayList<>();

  private static List<KeyInfo> fkeys = new ArrayList<>();
  private static boolean isUnionKey;

  public static boolean isUnionKey()
  {
    return isUnionKey;
  }

  public static void setUnionKey(boolean isUnionKey) {
	  TableVo.isUnionKey = isUnionKey;
  }

  public static boolean isPk() {
    return pkeys.size() > 0;
  }

  public static boolean isFk() {
    return fkeys.size() > 0;
  }

  public static List<KeyInfo> getPkeys()
  {
    return pkeys;
  }

  public static void setPkeys(List<KeyInfo> pkeys) {
	  TableVo.pkeys = pkeys;
  }

  public static List<KeyInfo> getFkeys() {
    return fkeys;
  }

  public static void setFkeys(List<KeyInfo> fkeys) {
	  TableVo.fkeys = fkeys;
  }

  public static class KeyInfo
  {
    private String pkname;
    private String pktablename;
    private String fkname;
    private String fktablename;

    public boolean equals(Object obj)
    {
      if ((obj instanceof KeyInfo)) {
        KeyInfo info = (KeyInfo)obj;
        return (this.pkname.equals(info.getPkname())) && (this.pktablename.equals(info.getPktablename())) && (this.fkname.equals(info.getFkname())) && (this.fktablename.equals(info.getFktablename()));
      }

      return false;
    }

    public int hashCode()
    {
      return this.pkname.hashCode() * 13 + this.pktablename.hashCode() + 17 + this.fkname.hashCode() + this.fktablename.hashCode();
    }

    public String getPkname() {
      return this.pkname;
    }

    public void setPkname(String pkname) {
      this.pkname = pkname;
    }

    public String getPktablename() {
      return this.pktablename;
    }

    public void setPktablename(String pktablename) {
      this.pktablename = pktablename;
    }

    public String getFkname() {
      return this.fkname;
    }

    public void setFkname(String fkname) {
      this.fkname = fkname;
    }

    public String getFktablename() {
      return this.fktablename;
    }

    public void setFktablename(String fktablename) {
      this.fktablename = fktablename;
    }
  }
}