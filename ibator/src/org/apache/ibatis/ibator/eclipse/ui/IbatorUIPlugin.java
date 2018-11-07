package org.apache.ibatis.ibator.eclipse.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class IbatorUIPlugin extends AbstractUIPlugin
{
  public static final String PLUGIN_ID = "org.apache.ibatis.ibator.eclipse.ui";
  private static IbatorUIPlugin plugin;

  public void start(BundleContext context)
    throws Exception
  {
    super.start(context);
    plugin = this;
  }

  public void stop(BundleContext context)
    throws Exception
  {
    plugin = null;
    super.stop(context);
  }

  public static IbatorUIPlugin getDefault()
  {
    return plugin;
  }
}