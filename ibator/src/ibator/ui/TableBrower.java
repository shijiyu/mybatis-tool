package ibator.ui;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import ibator.Activator;
import ibator.Globar;
import ibator.util.LocalDbConfig;

public class TableBrower extends Shell
{
  static IProject project;
  Shell sShell;
  static String file = Activator.getDefault().getStateLocation()
    .toOSString() + 
    "/db.config.xml";

  static LocalDbConfig batisConfig = new LocalDbConfig(file);
  private Button chineseLink;
  private Button englishLink;

  static
  {
    Activator.getDefault();
  }

  public static void show(IProject project)
  {
	TableBrower.project = project;
    try {
      Display display = Display.getDefault();
      TableBrower t = new TableBrower(display);

      while (!t.sShell.isDisposed())
        if (!display.readAndDispatch())
          display.sleep();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public TableBrower(Display display)
  {
    super(display, 2144);
    createContents();
  }

  public TableBrower() {
    super(131072);
    createContents();
  }

  protected void createContents()
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
    this.sShell.setSize(300, 200);
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
    this.sShell.setText("For the first time,select language");
    this.sShell.open();

    Group group1 = new Group(this.sShell, 0);
    group1.setLayout(null);
    group1.setText("语言选择(language select)");
    group1.setBounds(new Rectangle(10, 16, 280, 130));

    this.chineseLink = new Button(group1, 16);
    this.chineseLink.setBounds(new Rectangle(30, 26, 80, 13));
    this.chineseLink.setText("简体中文");

    this.englishLink = new Button(group1, 16);
    this.englishLink.setBounds(new Rectangle(130, 26, 80, 13));
    this.englishLink.setText("English");

    this.englishLink.setSelection(true);

    final Button btnNext = new Button(group1, 0);
    btnNext.setBounds(new Rectangle(160, 80, 70, 25));
    btnNext.setText("next");

    this.chineseLink.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        btnNext.setText("下一步");
        TableBrower.this.sShell.setText("首次运行，请选择语言");
        Globar.language = "zh_CN";
      }
    });
    this.englishLink.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        btnNext.setText("next");
        TableBrower.this.sShell.setText("For the first time,select language");
        Globar.language = "en";
      }
    });
    btnNext.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        TableBrower.this.sShell.close();
        TableBrower.batisConfig.writeLanguage(Globar.language);
        EclipseUI.showUI(TableBrower.project);
      }
    });
  }

  protected void checkSubclass()
  {
  }
}