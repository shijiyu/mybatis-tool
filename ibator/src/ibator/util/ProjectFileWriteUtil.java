package ibator.util;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.mybatis.generator.api.dom.java.TopLevelClass;

@SuppressWarnings("deprecation")
public class ProjectFileWriteUtil {
  public static boolean write(IProject project,TopLevelClass topLevelClass,String filename){
	  IFile ifile = project.getFile(filename);
	    try
	    {
	      if (ifile.exists())
	        ifile.delete(true, null);
	      InputStream is = new StringBufferInputStream(topLevelClass.getFormattedContent());
	      ifile.create(is, true, null);
	      return true;
	    } catch (CoreException e) {
	      return false;
	    }
  }
}
