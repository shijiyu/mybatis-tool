package ibator.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.actions.ActionGroup;

import ibator.Activator;
import ibator.Globar;
import ibator.util.DBUtil;
import ibator.util.TableVo;

public class SelectTableUI extends ActionGroup
{
  static Shell sShell;
  List<String> tables = new ArrayList<>();
  private TableViewer tv;
  private CheckboxTableViewer ctv;

  public void getBoyGirl(String table)
  {
    new DBUtil(); 
    //TableVo vo = DBUtil.getKeys(table);
    List<TableVo.KeyInfo> info1 = TableVo.getFkeys();
    List<TableVo.KeyInfo> info2 = TableVo.getPkeys();
    if (TableVo.isPk())
      for (TableVo.KeyInfo keyInfo : info1) {
        String name = keyInfo.getFktablename();
        if (!this.tables.contains(name)) {
          this.tables.add(name);
          getBoyGirl(name);
        }
      }
    if (TableVo.isFk())
      for (TableVo.KeyInfo keyInfo : info2) {
        String name = keyInfo.getPktablename();
        if (!this.tables.contains(name)) {
          this.tables.add(name);
          getBoyGirl(name);
        }
      }
  }

  public List<String> getAllSelectTable()
  {
    List<String> list = new ArrayList<>();
    Globar.tables.clear();
    if (this.ctv != null)
    {
      Object[] checkObj = this.ctv.getCheckedElements();
      if (checkObj.length == 0) {
        MessageDialog.openInformation(null, "Ibator.com", EclipseUI.getMessage("table_msg1"));
      }
      for (int i = 0; i < checkObj.length; i++) {
        Object o = checkObj[i];
        Globar.tables.add((String)o);
        list.add((String)o);
      }
    } else {
      IStructuredSelection s = (IStructuredSelection)this.tv.getSelection();
      if (s.isEmpty()) {
        MessageDialog.openInformation(null, "Ibator.com", EclipseUI.getMessage("table_msg1"));
      }

      for (Iterator<?> it = s.iterator(); it.hasNext(); ) {
        Object o = it.next();
        Globar.tables.add((String)o);
        list.add((String)o);
      }

    }

    return list;
  }

  public void fillActionToolBars(ToolBarManager actionBarManager)
  {
    Action selAllAction = new SelectAllAction();
    Action deselAction = new DeselectAction();
    Action othAction = new OtherAction();
    Action okAction = new OkAction();
    actionBarManager.add(selAllAction);
    actionBarManager.add(deselAction);
    actionBarManager.add(othAction);
    actionBarManager.add(okAction);
    actionBarManager.update(true);
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
    sShell.setText(EclipseUI.getMessage("table_title"));
    sShell.setSize(new Point(411, 549));
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

    sShell.setLayout(new FillLayout());
    ViewForm viewForm = new ViewForm(sShell, 0);
    viewForm.setLayout(new FillLayout());
    TableViewer tv = new TableViewer(viewForm, 65570);

    CheckboxTableViewer ctv = new CheckboxTableViewer(tv.getTable());

    //int old = 0;
    tv.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent arg0)
      {
      }
    });
    ToolBar toolBar = new ToolBar(viewForm, 8388608);
    ToolBarManager toolBarManager = new ToolBarManager(toolBar);

    viewForm.setContent(tv.getControl());
    viewForm.setTopLeft(toolBar);
    fillActionToolBars(toolBarManager);

    this.tv = tv;
    this.ctv = ctv;

    Table table = tv.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    TableLayout layout = new TableLayout();
    table.setLayout(layout);

    layout.addColumnData(new ColumnWeightData(13));
    new TableColumn(table, 0).setText(EclipseUI.getMessage("table_id"));
    layout.addColumnData(new ColumnWeightData(40));
    new TableColumn(table, 0).setText(EclipseUI.getMessage("table_name"));
    final List<String> data = DBUtil.getAllTable();

    tv.setContentProvider(new IStructuredContentProvider()
    {
      public Object[] getElements(Object element) {
        if ((element instanceof List)) {
          return ((List<?>)element).toArray();
        }
        return new Object[0];
      }

      public void dispose()
      {
      }

      public void inputChanged(Viewer arg0, Object arg1, Object arg2)
      {
      }
    });
    tv.setLabelProvider(new ITableLabelProvider()
    {
      public Image getColumnImage(Object arg0, int arg1)
      {
        return null;
      }

      public String getColumnText(Object arg0, int col) {
        if (col == 0) {
          return (data.indexOf((String)arg0) + 1)+"";
        }
        return (String)arg0;
      }

      public void addListener(ILabelProviderListener arg0)
      {
      }

      public void dispose()
      {
      }

      public boolean isLabelProperty(Object arg0, String arg1)
      {
        return false;
      }

      public void removeListener(ILabelProviderListener arg0)
      {
      }
    });
    tv.setInput(data);
  }

  public SelectTableUI()
  {
  }

  public SelectTableUI(TableViewer v, CheckboxTableViewer ctv)
  {
    this.tv = v;
    this.ctv = ctv;
  }

  public static void showTable()
  {
    SelectTableUI thisClass = new SelectTableUI();
    thisClass.createSShell();
    sShell.open();
    Display display = sShell.getDisplay();
    while (!sShell.isDisposed())
      if (!display.readAndDispatch())
        display.sleep();
  }

  public static void main(String[] args)
  {
    showTable();
  }

  private class DeselectAction extends Action
  {
    public DeselectAction()
    {
      setText(EclipseUI.getMessage("table_UncheckAll"));
    }

    public void run() {
      if (SelectTableUI.this.ctv != null)
        SelectTableUI.this.ctv.setAllChecked(false);
    }
  }

  private class OkAction extends Action
  {
    public OkAction()
    {
      setText(EclipseUI.getMessage("table_ok"));
    }

    public void run() {
      SelectTableUI.this.getAllSelectTable();

      SelectTableUI.sShell.dispose();
    }
  }

  private class OtherAction extends Action
  {
    public OtherAction()
    {
      setText(EclipseUI.getMessage("table_Relationship_Table"));
    }

    public void run()
    {
      List<String> list = SelectTableUI.this.getAllSelectTable();

      DBUtil dbUtil = new DBUtil();
      dbUtil.getCon();
      for (String str : list)
      {
        dbUtil.getAllTable2(str.trim());
      }

      Globar.tables.clear();
      Globar.tables.addAll(dbUtil.getAll());
      dbUtil.closeDb();
      SelectTableUI.this.ctv.setCheckedElements(Globar.tables.toArray());
    }
  }

  private class SelectAllAction extends Action
  {
    public SelectAllAction()
    {
      setText(EclipseUI.getMessage("table_CheckAll"));
    }

    public void run() {
      if (SelectTableUI.this.ctv != null)
        SelectTableUI.this.ctv.setAllChecked(true);
    }
  }
}