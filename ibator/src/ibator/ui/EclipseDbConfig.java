package ibator.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.internal.IbatorObjectFactory;
import org.apache.ibatis.ibator.internal.db.ConnectionFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.internal.util.ClassloaderUtility;

import ibator.Activator;
import ibator.Globar;
import ibator.util.DBUtil;
import ibator.util.LocalDbConfig;
import ibator.vo.DBVO;

public class EclipseDbConfig
{
  private static String file = Activator.getDefault()
    .getStateLocation().toOSString() + 
    "/db.config.xml";
  static LocalDbConfig config = new LocalDbConfig(file);
  private static String projectName;
  List<DBVO> dbvos = DBUtil.getDb();
  private static final String[] FILTER_NAMES = { "jdbc driver(*.jar)", 
    "jdbc driver (*.zip)" };

  private static final String[] FILTER_EXTS = { "*.jar", "*.zip" };

  private Shell sShell = null;
  private Label lblConfig = null;
  private Button btnRefresh = null;
  private Combo cboConfig = null;
  private Group group = null;
  private Label lblUsername = null;
  private Text txtUsername = null;
  private Label lblPassword = null;
  private Text txtPassword = null;
  private Label lblDriver = null;
  private Text txtDriver = null;
  private Button btnBrower = null;
  private Label lblUrl = null;
  private Text txtUrl = null;
  private Label lblDbType = null;
  private Combo cboDialect = null;
  private Button btnSave = null;
  private Button btnTest = null;
  private Label lblMsg = null;

  DBVO temp = null;

  String fn = "";

  static
  {
    Activator.getDefault();
  }

  public static void showDbConfig(String name)
  {
    projectName = name;

    EclipseDbConfig thisClass = new EclipseDbConfig();
    thisClass.createSShell();
    thisClass.sShell.open();
  }

  private void createCboConfig()
  {
    this.cboConfig = new Combo(this.sShell, 0);
    this.cboConfig.setBounds(new Rectangle(169, 9, 161, 18));

    List<DBVO> list = config.readDbConfig();

    this.cboConfig.removeAll();

    if (list != null)
      for (DBVO dbvo : list) {
        this.cboConfig.add(dbvo.getName());
        if (dbvo.getName().equalsIgnoreCase(projectName))
          this.temp = dbvo;
      }
    this.cboConfig.setText(projectName);
    this.cboConfig.addSelectionListener(new SelectionListener()
    {
      public void widgetDefaultSelected(SelectionEvent arg0)
      {
        if (ConnectionFactory.getDriver(EclipseDbConfig.this.txtDriver.getText().trim())) {
          EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driversuccess"));
        }
        else
          EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driverfailure"));
      }

      public void widgetSelected(SelectionEvent arg0)
      {
        if (ConnectionFactory.getDriver(EclipseDbConfig.this.txtDriver.getText().trim())) {
          EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driversuccess"));
        }
        else
          EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driverfailure"));
      }
    });
  }

  private void createGroup()
  {
    this.group = new Group(this.sShell, 0);
    this.group.setLayout(null);
    this.group.setText(EclipseUI.getMessage("dbconfig_dbconinfo"));

    this.group.setBounds(new Rectangle(62, 32, 480, 234));
    this.lblUsername = new Label(this.group, 0);
    this.lblUsername.setBounds(new Rectangle(33, 66, 62, 19));
    this.lblUsername.setText(EclipseUI.getMessage("dbconfig_username"));
    this.txtUsername = new Text(this.group, 2048);
    this.txtUsername.setBounds(new Rectangle(108, 65, 249, 20));
    this.lblPassword = new Label(this.group, 0);
    this.lblPassword.setBounds(new Rectangle(34, 104, 58, 17));
    this.lblPassword.setText(EclipseUI.getMessage("dbconfig_password"));
    this.txtPassword = new Text(this.group, 2048);
    this.txtPassword.setBounds(new Rectangle(107, 103, 251, 22));
    this.lblDriver = new Label(this.group, 0);
    this.lblDriver.setBounds(new Rectangle(35, 146, 58, 23));
    this.lblDriver.setText(EclipseUI.getMessage("dbconfig_driver"));
    this.txtDriver = new Text(this.group, 2048);
    this.txtDriver.setBounds(new Rectangle(108, 146, 248, 21));
    this.txtDriver.setEditable(false);

    this.btnBrower = new Button(this.group, 0);
    this.btnBrower.setBounds(new Rectangle(389, 147, 65, 22));
    this.btnBrower.setText(EclipseUI.getMessage("dbconfig_brower"));

    this.btnBrower
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        FileDialog dlg = new FileDialog(EclipseDbConfig.this.sShell, 2);
        dlg.setFilterNames(EclipseDbConfig.FILTER_NAMES);
        dlg.setFilterExtensions(EclipseDbConfig.FILTER_EXTS);
        EclipseDbConfig.this.fn = dlg.open();
        if (EclipseDbConfig.this.fn != null) {
          EclipseDbConfig.this.fn = EclipseDbConfig.this.fn.replaceAll("\\\\", "\\/");
          IbatorConfiguration configuration = new IbatorConfiguration();
          configuration.addClasspathEntry(EclipseDbConfig.this.fn);
          ClassLoader classLoader = 
            ClassloaderUtility.getCustomClassloader(configuration
            .getClassPathEntries());

          IbatorObjectFactory.setExternalClassLoader(classLoader);

          if (ConnectionFactory.getDriver(EclipseDbConfig.this.txtDriver.getText().trim())) {
            EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driversuccess"));
            if (Globar.classpath.contains(EclipseDbConfig.this.fn)) {
              Globar.classpath.remove(EclipseDbConfig.this.fn);
            }
            Globar.classpath.add(0, EclipseDbConfig.this.fn);
          } else {
            EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driverfailure"));
          }
        }
      }
    });
    this.lblUrl = new Label(this.group, 0);
    this.lblUrl.setBounds(new Rectangle(35, 193, 62, 22));
    this.lblUrl.setText(EclipseUI.getMessage("dbconfig_url"));
    this.txtUrl = new Text(this.group, 2048);
    this.txtUrl.setBounds(new Rectangle(109, 194, 360, 19));
    this.lblDbType = new Label(this.group, 0);
    this.lblDbType.setBounds(new Rectangle(33, 26, 63, 20));
    this.lblDbType.setText(EclipseUI.getMessage("dbconfig_dbtype"));
    createCboDialect();
    this.lblMsg = new Label(this.group, 0);
    this.lblMsg.setBounds(new Rectangle(364, 29, 103, 15));
    this.lblMsg.setText("");
  }

  private void createCboDialect()
  {
    this.cboDialect = new Combo(this.group, 8);
    this.cboDialect.setBounds(new Rectangle(109, 25, 251, 25));
    this.cboDialect.removeAll();

    if (this.dbvos != null) {
      for (DBVO vo : this.dbvos) {
        this.cboDialect.add(vo.getName());
      }
    }
    this.cboDialect
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        String dialect = EclipseDbConfig.this.cboDialect.getText();
        if (EclipseDbConfig.this.dbvos != null)
          for (DBVO vo : EclipseDbConfig.this.dbvos)
            if (vo.getDialect().equalsIgnoreCase(dialect)) {
              EclipseDbConfig.this.txtUsername.setText(vo.getUsername());
              EclipseDbConfig.this.txtPassword.setText(vo.getPassword());
              EclipseDbConfig.this.txtDriver.setText(vo.getDriver());
              EclipseDbConfig.this.txtUrl.setText(vo.getUrl());

              if (ConnectionFactory.getDriver(vo.getDriverUrl())) {
                EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driversuccess")); break;
              }
              if (ConnectionFactory.getDriver(EclipseDbConfig.this.txtDriver.getText()
                .trim())) {
                EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driversuccess")); break;
              }
              EclipseDbConfig.this.lblMsg.setText(EclipseUI.getMessage("dbconfig_driverfailure"));
              break;
            }
      }
    });
  }

  public static void main(String[] args)
  {
    showDbConfig("");
  }

  private void createSShell()
  {
    this.sShell = new Shell(131136);
    URL url = Activator.getDefault().getBundle().getEntry("resource/images/compute.ico");
    Image image = null;
    try {
      image = new Image(this.sShell.getDisplay(), url.openStream());
    }
    catch (IOException e2) {
      e2.printStackTrace();
    }
    this.sShell.setImage(image);
    this.sShell.setText(EclipseUI.getMessage("dbconfig_title"));
    this.sShell.setSize(new Point(611, 349));
    int width = this.sShell.getMonitor().getClientArea().width;
    int height = this.sShell.getMonitor().getClientArea().height;
    int x = this.sShell.getSize().x;
    int y = this.sShell.getSize().y;
    if (x > width) {
      this.sShell.getSize().x = width;
    }
    if (y > height) {
      this.sShell.getSize().y = height;
    }

    this.sShell.setLocation((width - x) / 2, (height - y) / 2);
    this.sShell.setLayout(null);
    this.lblConfig = new Label(this.sShell, 0);
    this.lblConfig.setBounds(new Rectangle(94, 10, 53, 16));
    this.lblConfig.setText(EclipseUI.getMessage("dbconfig_configname"));
    this.btnRefresh = new Button(this.sShell, 0);
    this.btnRefresh.setBounds(new Rectangle(357, 9, 62, 23));
    this.btnRefresh.setText(EclipseUI.getMessage("dbconfig_del"));

    this.btnRefresh
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        EclipseDbConfig.config.removeDbConfig(EclipseDbConfig.this.cboConfig.getText().trim());
        List<DBVO> list = EclipseDbConfig.config.readDbConfig();

        EclipseDbConfig.this.cboConfig.removeAll();
        if (list != null) {
          for (DBVO dbvo : list)
            EclipseDbConfig.this.cboConfig.add(dbvo.getName());
        }
        EclipseUI.setComboConfig();
        EclipseDbConfig.this.cboConfig.select(0);
        if ((list != null) && (list.size() > 0))
          EclipseDbConfig.this.cboConfig.setText(((DBVO)list.get(0)).getDname());
        EclipseDbConfig.this.temp = null;

        for (DBVO dbvo : list) {
          if (dbvo.getName().equalsIgnoreCase(
            EclipseDbConfig.this.cboConfig.getText())) {
            EclipseDbConfig.this.temp = dbvo;
            break;
          }
        }
        if (EclipseDbConfig.this.temp != null) {
          EclipseDbConfig.this.cboDialect.setText(EclipseDbConfig.this.temp.getDialect());
          EclipseDbConfig.this.txtUsername.setText(EclipseDbConfig.this.temp.getUsername());
          EclipseDbConfig.this.txtUrl.setText(EclipseDbConfig.this.temp.getUrl());
          EclipseDbConfig.this.txtPassword.setText(EclipseDbConfig.this.temp.getPassword());
          EclipseDbConfig.this.txtDriver.setText(EclipseDbConfig.this.temp.getDriver());
        }
      }
    });
    createCboConfig();
    createGroup();

    this.btnSave = new Button(this.sShell, 0);
    this.btnSave.setBounds(new Rectangle(340, 278, 85, 21));
    this.btnSave.setText(EclipseUI.getMessage("dbconfig_save"));
    this.btnSave
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        String username = EclipseDbConfig.this.txtUsername.getText().trim();
        String password = EclipseDbConfig.this.txtPassword.getText().trim();
        String url = EclipseDbConfig.this.txtUrl.getText().trim();
        String driver = EclipseDbConfig.this.txtDriver.getText().trim();
        String dialect = EclipseDbConfig.this.cboDialect.getText().trim();
        String name = EclipseDbConfig.this.cboConfig.getText().trim();
        if ((name == null) || (name.length() < 1) || (driver == null) || 
          (driver.length() < 1)) {
          MessageDialog.openInformation(EclipseDbConfig.this.sShell, "Ibator.com", 
            EclipseUI.getMessage("dbconfig_msg1"));
          return;
        }
        DBVO vo = new DBVO(username, password, url, driver, 
          dialect, name);
        vo.setDriverUrl(EclipseDbConfig.this.fn);
        LocalDbConfig config = new LocalDbConfig(EclipseDbConfig.file);
        config.writeDbConfig(EclipseDbConfig.this.cboConfig.getText().trim(), vo, false, false);
        List<DBVO> list = config.readDbConfig();

        EclipseDbConfig.this.cboConfig.removeAll();
        if (list != null) {
          for (DBVO dbvo : list)
            EclipseDbConfig.this.cboConfig.add(dbvo.getName());
        }
        EclipseDbConfig.this.cboConfig.setText(vo.getName());
        EclipseUI.setComboConfig();
        EclipseUI.cboConfig.setText(name);
        EclipseDbConfig.this.sShell.dispose();
      }
    });
    this.btnTest = new Button(this.sShell, 0);
    this.btnTest.setBounds(new Rectangle(213, 279, 85, 20));
    this.btnTest.setText(EclipseUI.getMessage("dbconfig_test"));
    this.btnTest
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
        String username = EclipseDbConfig.this.txtUsername.getText().trim();
        String password = EclipseDbConfig.this.txtPassword.getText().trim();
        String url = EclipseDbConfig.this.txtUrl.getText().trim();
        String driver = EclipseDbConfig.this.txtDriver.getText().trim();
        if ((username == null) || (username.length() < 1) || 
          (url == null) || (url.length() < 1) || 
          (driver == null) || (driver.length() < 1)) {
          MessageDialog.openInformation(EclipseDbConfig.this.sShell, "Ibator.com", 
            EclipseUI.getMessage("dbconfig_msg1"));
          return;
        }

        jdbc.setConnectionURL(url);
        jdbc.setPassword(password);
        jdbc.setUserId(username);
        jdbc.setDriverClass(driver);

        EclipseDbConfig.this.temp = null;
        List<DBVO> list = EclipseDbConfig.config.readDbConfig();
        for (DBVO dbvo : list) {
          if (dbvo.getName().equalsIgnoreCase(
            EclipseDbConfig.this.cboConfig.getText())) {
            EclipseDbConfig.this.temp = dbvo;
            break;
          }
        }

        String driver2 = EclipseDbConfig.this.fn;
        if ((EclipseDbConfig.this.fn != null) && (EclipseDbConfig.this.fn.length() > 0))
          driver2 = EclipseDbConfig.this.fn;
        else if ((EclipseDbConfig.this.temp != null) && (EclipseDbConfig.this.temp.getDriverUrl() != null) && 
          (EclipseDbConfig.this.temp.getDriverUrl().length() > 0))
          driver2 = EclipseDbConfig.this.temp.getDriverUrl();
        ClassLoader classLoader;
        if ((driver2 != null) && (driver2.length() > 0)) {
          IbatorConfiguration configuration = new IbatorConfiguration();
          configuration.addClasspathEntry(driver2);
          classLoader = 
            ClassloaderUtility.getCustomClassloader(configuration
            .getClassPathEntries());

          IbatorObjectFactory.setExternalClassLoader(classLoader);
        }
        else if ((driver2 == null) || (driver2.length() == 0)) {
          for (String fn : Globar.classpath) {
            if ((fn != null) && (fn.length() > 0)) {
              IbatorConfiguration configuration = new IbatorConfiguration();
              configuration.addClasspathEntry(fn);
              classLoader = 
                ClassloaderUtility.getCustomClassloader(configuration
                .getClassPathEntries());

              IbatorObjectFactory.setExternalClassLoader(classLoader);
            }
          }
        }

        try
        {
          ConnectionFactory.getInstance().getConnection(jdbc);
          MessageDialog.openInformation(EclipseDbConfig.this.sShell, "Ibator.com", 
            EclipseUI.getMessage("dbconfig_msg2"));
        }
        catch (Exception e1) {
          MessageDialog.openInformation(EclipseDbConfig.this.sShell, "Ibator.com", EclipseUI.getMessage("dbconfig_msg3") + e1.getMessage());
        }
      }
    });
    if (this.temp != null) {
      this.cboDialect.setText(this.temp.getDialect());
      this.txtUsername.setText(this.temp.getUsername());
      this.txtUrl.setText(this.temp.getUrl());
      this.txtPassword.setText(this.temp.getPassword());
      this.txtDriver.setText(this.temp.getDriver());
    }
    change();
  }

  public void change()
  {
    this.cboConfig
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        EclipseDbConfig.this.temp = null;
        List<DBVO> list = EclipseDbConfig.config.readDbConfig();
        for (DBVO dbvo : list) {
          if (dbvo.getName().equalsIgnoreCase(
            EclipseDbConfig.this.cboConfig.getText())) {
            EclipseDbConfig.this.temp = dbvo;
            break;
          }
        }
        if (EclipseDbConfig.this.temp != null) {
          EclipseDbConfig.this.cboDialect.setText(EclipseDbConfig.this.temp.getDialect());
          EclipseDbConfig.this.txtUsername.setText(EclipseDbConfig.this.temp.getUsername());
          EclipseDbConfig.this.txtUrl.setText(EclipseDbConfig.this.temp.getUrl());
          EclipseDbConfig.this.txtPassword.setText(EclipseDbConfig.this.temp.getPassword());
          EclipseDbConfig.this.txtDriver.setText(EclipseDbConfig.this.temp.getDriver());
          EclipseDbConfig.this.temp = null;

          for (DBVO dbvo : list) {
            if (dbvo.getName().equalsIgnoreCase(
              EclipseDbConfig.this.cboConfig.getText())) {
              EclipseDbConfig.this.temp = dbvo;
              break;
            }
          }
          if (EclipseDbConfig.this.temp != null) {
            EclipseDbConfig.this.cboDialect.setText(EclipseDbConfig.this.temp.getDialect());
            EclipseDbConfig.this.txtUsername.setText(EclipseDbConfig.this.temp.getUsername());
            EclipseDbConfig.this.txtUrl.setText(EclipseDbConfig.this.temp.getUrl());
            EclipseDbConfig.this.txtPassword.setText(EclipseDbConfig.this.temp.getPassword());
            EclipseDbConfig.this.txtDriver.setText(EclipseDbConfig.this.temp.getDriver());
            EclipseUI.cboConfig
              .setText(EclipseDbConfig.this.cboConfig.getText());
          }
        }
      }
    });
  }
}