package org.apache.ibatis.ibator.eclipse.ui.content;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;

public class AdapterFactory implements IAdapterFactory {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (((adaptableObject instanceof IFile)) && (adapterType == IbatorConfigurationFileAdapter.class)) {
			if (isIbatorConfigurationFile((IFile) adaptableObject))
				return new IbatorConfigurationFileAdapter((IFile) adaptableObject);
		} else if (((adaptableObject instanceof IJavaProject)) && (adapterType == JavaProjectAdapter.class)
				&& (!isIbatorProject((IJavaProject) adaptableObject))) {
			return new JavaProjectAdapter((IJavaProject) adaptableObject);
		}

		return null;
	}

	public Class<?>[] getAdapterList() {
		return new Class[] { IbatorConfigurationFileAdapter.class, JavaProjectAdapter.class };
	}

	private boolean isIbatorConfigurationFile(IFile file) {
		String fileName = file.getName();
		if (fileName.length() > 4) {
			String extension = fileName.substring(fileName.length() - 4);
			if (!extension.equalsIgnoreCase(".xml"))
				return false;
		} else {
			return false;
		}
		InputStream is;
		try {
			is = file.getContents();
		} catch (CoreException e) {

			return false;
		}
		IbatorConfigVerifyer verifyer = new IbatorConfigVerifyer(is);

		boolean rc = verifyer.isIbatorConfigFile();
		try {
			is.close();
		} catch (IOException localIOException) {
		}

		return rc;
	}

	private boolean isIbatorProject(IJavaProject project) {
		boolean rc = false;
		try {
			IClasspathEntry[] classpath = project.getRawClasspath();
			for (IClasspathEntry iClasspathEntry : classpath) {
				if (iClasspathEntry.getEntryKind() == 4) {
					IPath path = iClasspathEntry.getPath();
					if (path.segment(0).equals("IBATOR_JAR")) {
						rc = true;
						break;
					}
				}
			}
		} catch (Exception localException) {
		}
		return rc;
	}
}