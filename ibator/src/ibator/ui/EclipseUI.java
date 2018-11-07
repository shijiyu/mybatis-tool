package ibator.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ibator.Activator;
import ibator.Globar;
import ibator.popup.actions.GO;
import ibator.util.BrowerUtil;
import ibator.util.DBUtil;
import ibator.util.LocalDbConfig;
import ibator.util.Util;
import ibator.vo.DBVO;

public class EclipseUI
{
  private Group group2;
  //private static String projectName;
  public static IProject project = null;
  private static final String BROWERSURI = "http://ibator.com/";
  static String file = Activator.getDefault().getStateLocation()
    .toOSString() + 
    "/db.config.xml";

  static LocalDbConfig batisConfig = new LocalDbConfig(file);
  private Button chineseLink;
  private Button englishLink;
  private Label lblRightDAO;
  private Text txtRightDAO;
  private Label lblRightExample;
  //private Button btnRightOk;
  private Text txtRightExample;
  public static Shell sShell = null;
  public static Combo cboConfig = null;
  private Button btnConfig = null;
  private Group group = null;
  private Label lblPojo = null;
  private Text txtPojo = null;
  private Label lblMap = null;
  private Text txtSqlMap = null;
  private Button chkComment = null;
  private Button chkPage = null;
 // private Group group1 = null;
  private Label label = null;
  private Button chkOverride = null;
  private Button btnOk = null;
  private Button btnCancel = null;
  private Link link = null;
  private Button rad1 = null;
  private Button rad2 = null;
  private Button rad3 = null;

 // private Canvas canvas = null;
  private Button button = null;
 // private Button button1 = null;
  private Label lblMap1 = null;
  private Text txtDAO = null;
//  private Browser browser1 = null;
  private Button ts = null;
  private Group group4;
  private Button cache;
//  private Button keyword;
  private Button simple;

  static
  {
    Activator.getDefault();
  }

  public static String getMessage(String key)
  {
    Locale locale = new Locale(Globar.language);
    if (Globar.language.equals("zh_CN")) {
      locale = new Locale("zh", "CN");
    }

    ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
    try
    {
      return bundle.getString(key);
    }
    catch (RuntimeException localRuntimeException)
    {
    }
    return null;
  }

  private void i18n()
  {
    Locale locale = new Locale(Globar.language);
    if (Globar.language.equals("zh_CN")) {
      locale = new Locale("zh", "CN");
    }
    ResourceBundle bundle = ResourceBundle.getBundle("message", locale);

    String main_lbldbconfig = bundle.getString("main_lbldbconfig");
    String main_btndbconfig = bundle.getString("main_btndbconfig");
    String main_btntable = bundle.getString("main_btntable");
    String main_group1 = bundle.getString("main_group1");
    String main_group2 = bundle.getString("main_group2");
    String main_group3 = bundle.getString("main_group3");
    String main_lblPojoPath = bundle.getString("main_lblPojoPath");
    String main_lblDaoPath = bundle.getString("main_lblDaoPath");
    String main_lblSqlPath = bundle.getString("main_lblSqlPath");
    String main_rad1 = bundle.getString("main_rad1");
    String main_rad2 = bundle.getString("main_rad2");
    String main_rad3 = bundle.getString("main_rad3");

    String main_ok = bundle.getString("main_ok");
    String main_cancel = bundle.getString("main_cancel");

    String main_lblRightDao = bundle.getString("main_lblRightDao");
    String main_lblRightExample = bundle.getString("main_lblRightExample");

    String main_chkspring = bundle.getString("main_chkspring");
    String main_chkover = bundle.getString("main_chkover");
    String main_chksimple = bundle.getString("main_chksimple");
    String main_chkcache = bundle.getString("main_chkcache");
    String main_chkcomment = bundle.getString("main_chkcomment");

    this.lblRightDAO.setText(main_lblRightDao);
    this.lblRightExample.setText(main_lblRightExample);

    this.chkComment.setText(main_chkcomment);
    this.chkOverride.setText(main_chkover);
    this.chkPage.setText(main_chkspring);
    this.cache.setText(main_chkcache);
    this.simple.setText(main_chksimple);

    this.label.setText(main_lbldbconfig);
    this.btnConfig.setText(main_btndbconfig);
    this.ts.setText(main_btntable);
    this.group.setText(main_group1);
    this.group2.setText(main_group2);
    this.lblPojo.setText(main_lblPojoPath);
    this.lblMap1.setText(main_lblDaoPath);
    this.lblMap.setText(main_lblSqlPath);
    this.group4.setText(main_group3);

    this.rad1.setText(main_rad1);
    this.rad2.setText(main_rad2);
    this.rad3.setText(main_rad3);

    this.btnOk.setText(main_ok);
    this.btnCancel.setText(main_cancel);
  }

  public EclipseUI()
  {
    Globar.isCache = false;
    Globar.isWord = true;

    Globar.spring = false;
    Globar.ISSIMPLE = false;
  }

  private void initParam()
  {
    DBVO vo = batisConfig.getParam();
    if (vo == null) {
      return;
    }
    if (vo.getLanguage() != null) {
      Globar.language = vo.getLanguage();
    }
    if ("zh_CN".equals(Globar.language))
      this.chineseLink.setSelection(true);
    else {
      this.englishLink.setSelection(true);
    }
    i18n();

    if (vo.getDaoPath() != null) {
      this.txtDAO.setText(vo.getDaoPath());
    }
    if (vo.getPojoPath() != null) {
      this.txtPojo.setText(vo.getPojoPath());
    }
    if (vo.getSqlPath() != null)
    {
      this.txtSqlMap.setText(vo.getSqlPath());
      this.chkComment.setSelection(vo.isComment());

      this.chkOverride.setSelection(vo.isOver());
      this.chkPage.setSelection(vo.isSpring());
      this.cache.setSelection(vo.isCache());
      this.simple.setSelection(vo.isSimple());
    }

    this.rad1.setSelection(false);
    this.rad2.setSelection(false);
    this.rad3.setSelection(false);
    if ("annotation".equals(vo.getClient()))
      this.rad3.setSelection(true);
    else if ("mixed-mapper".equals(vo.getClient()))
      this.rad2.setSelection(true);
    else {
      this.rad1.setSelection(true);
    }
    this.chineseLink.setSelection(false);
    this.englishLink.setSelection(false);

    if ("zh_CN".equals(vo.getLanguage()))
      this.chineseLink.setSelection(true);
    else if ("en".equals(vo.getLanguage()))
      this.englishLink.setSelection(true);
    else if ("zh_CN".equals(Globar.language))
      this.chineseLink.setSelection(true);
    else {
      this.englishLink.setSelection(true);
    }
    if ((vo.getDaoName() != null) && (vo.getDaoName().trim().length() > 0))
    {
      Globar.daoName = vo.getDaoName();
      this.txtRightDAO.setText(vo.getDaoName().trim());
    }

    if ((vo.getExampleName() != null) && (vo.getExampleName().trim().length() > 0))
    {
      Globar.exampleName = vo.getExampleName();
      this.txtRightExample.setText(vo.getExampleName().trim());
    }
  }

  public static void close()
  {
    sShell.dispose();
  }

  private void createCanvas()
  {
  }

  private void createBrowser1()
  {
    Group group1 = new Group(sShell, 0);
    group1.setLayout(null);
    group1.setText("语言选择(language select)");
    group1.setBounds(new Rectangle(555, 16, 200, 60));

    this.chineseLink = new Button(group1, 16);
    this.chineseLink.setBounds(new Rectangle(30, 26, 80, 13));
    this.chineseLink.setText("简体中文");

    this.englishLink = new Button(group1, 16);
    this.englishLink.setBounds(new Rectangle(130, 26, 80, 13));
    this.englishLink.setText("English");

    this.chineseLink.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Globar.language = "zh_CN";
        EclipseUI.this.i18n();
        EclipseUI.batisConfig.writeLanguage(Globar.language);
      }
    });
    this.englishLink.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Globar.language = "en";
        EclipseUI.this.i18n();
        EclipseUI.batisConfig.writeLanguage(Globar.language);
      }
    });
    this.group2 = new Group(sShell, 0);
    this.group2.setLayout(null);

    this.group2.setBounds(new Rectangle(555, 100, 200, 100));

    this.lblRightDAO = new Label(this.group2, 0);
    this.lblRightDAO.setBounds(new Rectangle(30, 26, 80, 13));

    this.txtRightDAO = new Text(this.group2, 0);
    this.txtRightDAO.setBounds(new Rectangle(130, 26, 60, 13));
    this.txtRightDAO.setText(Globar.daoName);

    this.lblRightExample = new Label(this.group2, 0);
    this.lblRightExample.setBounds(new Rectangle(30, 56, 80, 13));

    this.txtRightExample = new Text(this.group2, 0);
    this.txtRightExample.setBounds(new Rectangle(130, 56, 60, 13));
    this.txtRightExample.setText(Globar.exampleName);
  }

  public static void main(String[] args)
  {
  }

  public static void setComboConfig()
  {
    List<DBVO> list = batisConfig.readDbConfig();

    cboConfig.removeAll();
    if (list != null)
      for (DBVO dbvo : list)
      {
        cboConfig.add(dbvo.getName());
      }
    if ((list != null) && (list.size() > 0))
      cboConfig.setText(((DBVO)list.get(0)).getDname());
  }

  private void createCboConfig()
  {
    cboConfig = new Combo(sShell, 8);
    cboConfig.setBounds(new Rectangle(130, 9, 145, 21));
    setComboConfig();
  }

  private void createGroup()
  {
    this.group = new Group(sShell, 0);
    this.group.setLayout(null);

    this.group.setBounds(new Rectangle(32, 40, 464, 127));
    this.lblPojo = new Label(this.group, 0);
    this.lblPojo.setBounds(new Rectangle(29, 29, 187, 14));

    this.txtPojo = new Text(this.group, 2048);
    this.txtPojo.setBounds(new Rectangle(228, 28, 213, 19));
    this.txtPojo.setText("com.pojo");
    this.lblMap = new Label(this.group, 0);
    this.lblMap.setBounds(new Rectangle(28, 54, 186, 13));

    this.txtSqlMap = new Text(this.group, 2048);
    this.txtSqlMap.setBounds(new Rectangle(230, 53, 212, 18));
    this.txtSqlMap.setText("com.pojo.sql");

    this.lblMap1 = new Label(this.group, 0);
    this.lblMap1.setBounds(new Rectangle(29, 78, 170, 13));

    this.txtDAO = new Text(this.group, 2048);
    this.txtDAO.setBounds(new Rectangle(230, 77, 211, 18));
    this.txtDAO.setText("com.dao");

    this.chkComment = new Button(this.group, 32);
    this.chkComment.setBounds(new Rectangle(37, 105, 70, 18));

    this.simple = new Button(this.group, 32);
    this.simple.setBounds(new Rectangle(300, 105, 70, 18));
    this.simple.setSelection(false);

    this.chkPage = new Button(this.group, 32);
    this.chkPage.setBounds(new Rectangle(380, 105, 80, 18));
    this.chkPage.setSelection(false);

    this.chkOverride = new Button(this.group, 32);
    this.chkOverride.setBounds(new Rectangle(214, 105, 70, 18));

    this.cache = new Button(this.group, 32);
    this.cache.setBounds(new Rectangle(121, 105, 70, 18));

    this.txtPojo.addModifyListener(new ModifyListener()
    {
      public void modifyText(ModifyEvent arg0) {
        EclipseUI.this.txtSqlMap.setText(EclipseUI.this.txtPojo.getText() + ".sql");
      }
    });
    this.cache.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Globar.isCache = EclipseUI.this.cache.getSelection();
      }
    });
    this.simple.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Globar.ISSIMPLE = EclipseUI.this.simple.getSelection();
      }
    });
  }

  private void createGroup1()
  {
    this.group4 = new Group(sShell, 0);
    this.group4.setLayout(null);

    this.group4.setBounds(new Rectangle(31, 170, 463, 56));
    this.rad1 = new Button(this.group4, 16);
    this.rad1.setBounds(new Rectangle(31, 25, 70, 16));

    this.rad1.setSelection(true);

    this.rad2 = new Button(this.group4, 16);
    this.rad2.setBounds(new Rectangle(238, 27, 100, 13));

    this.rad3 = new Button(this.group4, 16);
    this.rad3.setBounds(new Rectangle(138, 27, 70, 16));
  }

  public static void showUI(IProject iproject)
  {
    project = iproject;
    /*projectName =*/ iproject.getName();

    Display display = Display.getDefault();
    EclipseUI thisClass = new EclipseUI();
    thisClass.createSShell();
    sShell.open();

    while (!sShell.isDisposed())
      if (!display.readAndDispatch())
        display.sleep();
  }

  private void createSShell()
  {
    sShell = new Shell(131136);
    URL url = Activator.getDefault().getBundle().getEntry("resource/images/compute.ico");
    Image image = null;
    try {
      image = new Image(sShell.getDisplay(), url.openStream());
    }
    catch (IOException e2) {
      e2.printStackTrace();
    }
    sShell.setImage(image);
    sShell.setText("MyBatis可视化生成代码工具");
    sShell.setSize(new Point(534, 289));
    int width = sShell.getMonitor().getClientArea().width;
    int height = sShell.getMonitor().getClientArea().height;
    int x = sShell.getSize().x;
    int y = sShell.getSize().y;
    if (x > width) {
      sShell.getSize().x = width;
    }
    if (y > height) {
      sShell.getSize().y = height;
    }

    sShell.setLocation((width - x) / 2, (height - y) / 2);
    createCboConfig();

    sShell.setLayout(null);
    this.btnConfig = new Button(sShell, 0);
    this.btnConfig.setBounds(new Rectangle(275, 9, 76, 23));

    this.btnConfig
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        EclipseDbConfig.showDbConfig(EclipseUI.cboConfig.getText());
      }
    });
    createGroup();
    createGroup1();

    this.label = new Label(sShell, 0);
    this.label.setBounds(new Rectangle(34, 13, 95, 16));

    this.btnOk = new Button(sShell, 0);
    this.btnOk.setBounds(new Rectangle(103, 228, 99, 19));

    this.btnOk
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        String name = EclipseUI.cboConfig.getText();

        if ((name == null) || (name.length() < 1)) {
          MessageDialog.openError(null, "Ibator.com", EclipseUI.getMessage("main_msg1"));
          return;
        }

        Activator.getDefault(); String file = Activator.getDefault()
          .getStateLocation().toOSString() + 
          "/db.config.xml";

        LocalDbConfig batisConfig = new LocalDbConfig(file);
        List<DBVO> list = batisConfig.readDbConfig();
        if ((list == null) || (list.size() < 1)) {
          MessageDialog.openError(null, "Ibator.com", EclipseUI.getMessage("main_msg1"));
          return;
        }
        DBVO vo = null;
        for (DBVO dbvo : list) {
          if (dbvo.getName().equalsIgnoreCase(name)) {
            Globar.global.setDbVo(dbvo);
            vo = dbvo;
          }
          if (Globar.classpath.contains(dbvo.getDriverUrl())) {
            Globar.classpath.remove(dbvo.getDriverUrl());
          }
          Globar.classpath.add(0, dbvo.getDriverUrl());
        }

        if (vo == null) {
          MessageDialog.openError(null, "Ibator.com", EclipseUI.getMessage("main_msg1"));
          return;
        }

        File f = new File(vo.getDriverUrl());
        if ((!f.exists()) && 
          (!vo.getDialect().equalsIgnoreCase("MySQL"))) {
          MessageDialog.openError(null, "Ibator.com", EclipseUI.getMessage("main_msg2"));
          return;
        }

        boolean fCon = DBUtil.connectionDb(vo);
        if (!fCon) {
          MessageDialog.openError(null, "Ibator.com", 
            EclipseUI.getMessage("main_msg3"));
          return;
        }

        Globar.global.getDbVo().setDname(name);

        if (Globar.tables.size() < 1) {
          boolean ff = MessageDialog.openConfirm(EclipseUI.sShell, 
            "Ibator.com", EclipseUI.getMessage("main_msg4"));
          if (ff) {
            SelectTableUI.showTable();
          }
        }

        String des = "WebRoot/WEB-INF/lib/";
        IFolder fold = EclipseUI.project.getFolder("WebRoot/WEB-INF/lib");
        if (!fold.exists()) {
          fold = EclipseUI.project.getFolder("WebContext/WEB-INF/lib");
          des = "WebContext/WEB-INF/lib/";
        }
        if (!fold.exists()) {
          fold = EclipseUI.project.getFolder("lib/");
          des = "lib";
        }

        String driverPath = Globar.global.getDbVo()
          .getDriverUrl();

        Util.copyJarToClassPath2(driverPath, des, EclipseUI.project);

        Globar.global.setComment(EclipseUI.this.chkComment.getSelection());
        Globar.global.setOverride(EclipseUI.this.chkOverride.getSelection());
        Globar.global.setPage(EclipseUI.this.chkPage.getSelection());
        Globar.pojoPath = EclipseUI.this.txtPojo.getText().trim();
        Globar.xmlPath = EclipseUI.this.txtSqlMap.getText().trim();
        Globar.daoPath = EclipseUI.this.txtDAO.getText().trim();
        Globar.isCache = EclipseUI.this.cache.getSelection();
        Globar.ISSIMPLE = EclipseUI.this.simple.getSelection();

        if (EclipseUI.this.rad1.getSelection())
          Globar.global.setDaoType("xml");
        else if (EclipseUI.this.rad2.getSelection())
          Globar.global.setDaoType("mixed-mapper");
        else if (EclipseUI.this.rad3.getSelection()) {
          Globar.global.setDaoType("annotation");
        }
        if (EclipseUI.this.chkPage.getSelection())
          Globar.spring = true;
        else {
          Globar.spring = false;
        }
        if ((EclipseUI.this.txtRightDAO.getText() == null) || (EclipseUI.this.txtRightDAO.getText().trim().length() == 0)) {
          Globar.daoName = "Mapper";
        }
        else
          Globar.daoName = EclipseUI.this.txtRightDAO.getText().trim();
        if ((EclipseUI.this.txtRightExample.getText() == null) || (EclipseUI.this.txtRightExample.getText().trim().length() == 0))
          Globar.exampleName = "Example";
        else {
          Globar.exampleName = EclipseUI.this.txtRightExample.getText().trim();
        }

        DBVO dbvo = new DBVO();
        dbvo.setCache(Globar.isCache);
        dbvo.setComment(Globar.global.isComment());
        dbvo.setClient(Globar.global.getDaoType());
        dbvo.setDaoPath(Globar.daoPath);
        dbvo.setOver(Globar.global.isOverride());
        dbvo.setPojoPath(Globar.pojoPath);
        dbvo.setSqlPath(Globar.xmlPath);
        dbvo.setSpring(Globar.spring);
        dbvo.setSimple(EclipseUI.this.simple.getSelection());
        if (EclipseUI.this.chineseLink.getSelection())
          dbvo.setLanguage("zh_CN");
        else if (EclipseUI.this.englishLink.getSelection()) {
          dbvo.setLanguage("en");
        }
        Globar.language = dbvo.getLanguage();
        dbvo.setDaoName(Globar.daoName);
        dbvo.setExampleName(Globar.exampleName);

        batisConfig.writeDbConfig(null, dbvo, true, false);

        batisConfig.changeDefault(EclipseUI.cboConfig.getText().trim());

        new GO(EclipseUI.project);

        if (!fold.exists()) {
          Util.createFolder(EclipseUI.project, fold.getName());
        }
        try
        {
          EclipseUI.project
            .refreshLocal(2, 
            null);
        }
        catch (CoreException e1) {
          e1.printStackTrace();
        }

        EclipseUI.close();
      }
    });
    this.btnCancel = new Button(sShell, 0);
    this.btnCancel.setBounds(new Rectangle(300, 226, 104, 21));

    this.btnCancel
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e)
      {
        EclipseUI.close();
      }
    });
    this.link = new Link(sShell, 0);
    this.link.setBounds(new Rectangle(405, 12, 114, 16));
    this.link.setText("<a>"+BROWERSURI+"</a>");

    createCanvas();

    this.button = new Button(sShell, 0);
    this.button.setBounds(new Rectangle(503, 101, 21, 15));
    this.button.setText(">");
    this.button
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        if (EclipseUI.this.button.getText().equals(">"))
        {
          EclipseUI.sShell.setSize(777, 289);
          EclipseUI.this.button.setText("<");
        }
        else
        {
          EclipseUI.sShell.setSize(534, 289);
          EclipseUI.this.button.setText(">");
        }
      }
    });
    this.link
      .addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e)
      {
        /*boolean f =*/ BrowerUtil.openURL(BROWERSURI);
      }
    });
    this.txtPojo.setTabs(1);
    this.txtSqlMap.setTabs(2);
    this.txtSqlMap.setTabs(3);

    this.chkOverride.setSelection(true);

    this.chkPage.setEnabled(true);

    createBrowser1();
    this.ts = new Button(sShell, 0);
    this.ts.setBounds(new Rectangle(353, 10, 43, 20));

    this.ts.addMouseListener(new MouseAdapter()
    {
      public void mouseDown(MouseEvent e)
      {
        String name = EclipseUI.cboConfig.getText();

        if ((name == null) || (name.length() < 1)) {
          MessageDialog.openError(null, "IBator.com", EclipseUI.getMessage("main_msg1"));
          return;
        }

        Activator.getDefault(); String file = Activator.getDefault()
          .getStateLocation().toOSString() + 
          "/db.config.xml";

        LocalDbConfig batisConfig = new LocalDbConfig(file);
        List<DBVO> list = batisConfig.readDbConfig();
        DBVO vo = null;
        for (DBVO dbvo : list) {
          if (dbvo.getName().equalsIgnoreCase(name)) {
            Globar.global.setDbVo(dbvo);
            vo = dbvo;
          }
          if (Globar.classpath.contains(dbvo.getDriverUrl())) {
            Globar.classpath.remove(dbvo.getDriverUrl());
          }
          Globar.classpath.add(0, dbvo.getDriverUrl());
        }

        if (vo == null) {
          MessageDialog.openError(null, "IBator.com", EclipseUI.getMessage("main_msg3"));
          return;
        }

        File f = new File(vo.getDriverUrl());
        if ((!f.exists()) && 
          (!vo.getDialect().equalsIgnoreCase("MySQL"))) {
          MessageDialog.openError(null, "IBator.com", EclipseUI.getMessage("main_msg2"));
          return;
        }

        boolean fCon = DBUtil.connectionDb(vo);
        if (!fCon) {
          MessageDialog.openInformation(EclipseUI.sShell, "IBator.com", EclipseUI.getMessage("main_msg3"));
          return;
        }

        Globar.global.setComment(EclipseUI.this.chkComment.getSelection());
        Globar.global.setOverride(EclipseUI.this.chkOverride.getSelection());
        Globar.global.setPage(EclipseUI.this.chkPage.getSelection());
        Globar.pojoPath = EclipseUI.this.txtPojo.getText().trim();
        Globar.xmlPath = EclipseUI.this.txtSqlMap.getText().trim();
        Globar.daoPath = EclipseUI.this.txtDAO.getText().trim();

        SelectTableUI.showTable();
      }
    });
    initParam();
  }

  class NoPage extends SelectionAdapter
  {
    NoPage()
    {
    }

    public void widgetSelected(SelectionEvent e)
    {
      EclipseUI.this.chkPage.setSelection(false);
    }
  }
}