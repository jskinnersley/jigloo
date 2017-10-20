/*
 * Created on May 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;

/**
 * @author jonathan
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUtils {
	/**
	 * @return
	 */
	public static IFile getResourceFile(String resName, FormEditor editor) {
		IFile file = getResourceFile(resName, editor.getProject(), editor.getPackageName(),
				editor.getJavaFile().getParent());
		return file;
	}

	public static void createFolder(IContainer file) throws CoreException {
		if (!file.exists() && file instanceof IFolder) {
			Vector pars = new Vector();
			pars.add(file);
			IContainer par = file.getParent();
			while (par instanceof IFolder && !par.exists()) {
				pars.add(par);
				par = par.getParent();
			}
			for (int i = pars.size() - 1; i >= 0; i--)
				((IFolder) pars.get(i)).create(true, true, null);
		}
	}

	public static IFile getResourceFile(String resName, IProject proj, String packageName, IContainer cont) {
		IContainer eif = null;
		try {
			String resPath = jiglooPlugin.getDefault().getStringPreference(MainPreferencePage.P_RESOURCE_PATH);
			if (resPath != null && !"".equals(resPath)) {
				IFolder resFold = proj.getFolder(resPath);
				if (resFold.exists()) {
					eif = resFold.getFolder(new Path(packageName.replace('.', '/')));
					try {
						createFolder(eif);
						createFolder(eif.getFolder(new Path("resources")));
					} catch (CoreException e) {
						e.printStackTrace();
						eif = null;
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (eif == null) {
			eif = cont;
		}
		IFile msgFile = null;
		String propFileName = "resources/" + resName;
		msgFile = eif.getFile(new Path(propFileName));
		return msgFile;
	}

	/**
	 * @param file
	 * @param sourceContainer
	 * @return
	 */
	public static String getRelativePath(IResource child, IResource parent) {
		String parPath = parent.getLocation().toString();
		String childPath = child.getLocation().toString();
		if (!childPath.startsWith(parPath)) {
			System.err.println("Child path is not child of parent " + childPath + ", " + parPath);
			return childPath;
		}
		return childPath.substring(parPath.length());
	}

	public static void addJarsToClassPath(Shell shell, IJavaProject proj, String[] jarNames, String testClassName) {
		try {
			try {
				// Class cls = JiglooUtils.loadClass(proj.getProject(), testClassName, null,
				// false);
				// ClassLoader cl = cls.getClassLoader();
				// System.out.println("GOT CL "+cl);
				// if(!(cl instanceof FormClassLoader))
				// cls = null;
				ClassLoader cl = ClassLoaderCache.createClassLoader(proj.getProject(), null);
				Class cls = cl.loadClass(testClassName);
				if (cls != null)
					return;
			} catch (Throwable t) {
			}
			IClasspathEntry[] rcps = proj.getRawClasspath();
			Vector newRcpVec = new Vector();
			boolean cont = false;
			for (int i = 0; i < jarNames.length; i++) {
				IFolder libDir = ((IContainer) proj.getResource()).getFolder(new Path("lib"));
				IFile file;
				if (!libDir.exists())
					libDir.create(true, true, null);

				file = libDir.getFile(new Path(jarNames[i]));
				if (!file.exists()) {
					cont = MessageDialog.openQuestion(shell, "Add library?",
							"In order to resolve all needed classes, a library (" + jarNames[i] + ") needs "
									+ "to be added to your project.\n\n"
									+ "Hit \"Yes\" if you want this to be done automatically now.");
					if (!cont)
						return;
					InputStream stream = FileUtils.getPluginResourceAsStream("/lib/" + jarNames[i]);
					file.create(stream, true, null);
					stream.close();
				}
				IClasspathEntry cpe = JavaCore.newLibraryEntry(file.getFullPath(), null, null);
				newRcpVec.add(cpe);
			}
			IClasspathEntry[] newRcps = new IClasspathEntry[rcps.length + newRcpVec.size()];
			System.arraycopy(rcps, 0, newRcps, 0, rcps.length);
			for (int i = 0; i < newRcpVec.size(); i++)
				newRcps[i + rcps.length] = (IClasspathEntry) newRcpVec.elementAt(i);
			proj.setRawClasspath(newRcps, null);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void addJarToClassPath(IJavaProject proj, File jarFile) {
		try {
			IClasspathEntry[] rcps = proj.getRawClasspath();
			for (int i = 0; i < rcps.length; i++) {
				IClasspathEntry entry = rcps[i];
				if (jarFile.getName().equals(entry.getPath().lastSegment()))
					return;
			}
			IClasspathEntry[] newRcps = new IClasspathEntry[rcps.length + 1];
			System.arraycopy(rcps, 0, newRcps, 0, rcps.length);
			newRcps[rcps.length] = JavaCore.newLibraryEntry(new Path(jarFile.getAbsolutePath()), null, null);
			proj.setRawClasspath(newRcps, null);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// public static DataInputStream getTemplateResourceAsStream(Class cls, String
	// templateName) {
	// DataInputStream strm;
	// strm = new DataInputStream(cls.getClassLoader()
	// .getResourceAsStream( "com/cloudgarden/jigloo/wizards/templates/"+
	// templateName));
	// //System.out.println("got stream " + strm);
	// return strm;
	// }

	public static DataInputStream getTemplateResourceAsStream(Class cls, String templateName) {
		return getPluginResourceAsStream("/templates/" + templateName);
	}

	public static DataInputStream getPluginResourceAsStream(String templateName) {
		DataInputStream strm = null;
		try {
			URL url = jiglooPlugin.getDefault().getBundle().getResource("/resources/"+templateName);
			if (url != null)
				strm = new DataInputStream(new BufferedInputStream(url.openStream()));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return strm;
	}

	/**
	 * @param proj
	 * @param string
	 */
	public static void addSWTToClassPath(IJavaProject proj) {
		try {

			BundleContext bundleContext = jiglooPlugin.getDefault().getBundleContext();
			Bundle[] bunds = bundleContext.getBundles();
			for (int i = 0; i < bunds.length; i++) {
				Bundle bundle = bunds[i];
				if (bundle.getSymbolicName().contains("org.eclipse.swt")) {
					String swtBundlePath = bundle.getLocation();
					if (swtBundlePath.endsWith("/"))
						swtBundlePath = swtBundlePath.substring(0, swtBundlePath.length() - 1);
					if (swtBundlePath.endsWith("!"))
						swtBundlePath = swtBundlePath.substring(0, swtBundlePath.length() - 1);
					swtBundlePath = JiglooUtils.replace(swtBundlePath, "\\", "/");
					swtBundlePath = JiglooUtils.replace(swtBundlePath, "jar:", "");
					swtBundlePath = JiglooUtils.replace(swtBundlePath, "reference:", "");
					swtBundlePath = JiglooUtils.replace(swtBundlePath, "file:", "");
					File swtBundleFile = new java.io.File(swtBundlePath);
					
					if (swtBundleFile.isDirectory()) {
						Vector fvec = new Vector();
						locateFiles(swtBundleFile, "jar", fvec);
						for (int k = 0; k < fvec.size(); k++) {
							File f = (File) fvec.elementAt(k);
							if (f.getName().equals("swt.jar")) {
								addJarToClassPath(proj, f);
							}
						}
						fvec.clear();
						locateFiles(swtBundleFile, "dll,so,jnilib", fvec);
						for (int k = 0; k < fvec.size(); k++) {
							File f = (File) fvec.elementAt(k);
							IContainer res = (IContainer) proj.getResource();
							IFile dllFile = res.getFile(new Path(f.getName()));
							if (!dllFile.exists())
								dllFile.create(new FileInputStream(f), true, null);
						}

					} else {
						boolean isSwtImp = false;
						JarFile jf = new JarFile(swtBundleFile);
						Enumeration e = jf.entries();
						while (e.hasMoreElements()) {
							JarEntry jentry = (JarEntry) e.nextElement();
							String fileName = jentry.getName();
							if (fileName.endsWith(".dll") || fileName.endsWith(".so") || fileName.endsWith(".jnilib")) {
								isSwtImp = true;
								IContainer res = (IContainer) proj.getResource();
								IFile dllFile = res.getFile(new Path(fileName));
								if (!dllFile.exists())
									dllFile.create(jf.getInputStream(jentry), true, null);
							}
						}
						jf.close();
						if(isSwtImp) {
							addJarToClassPath(proj, swtBundleFile);
						}
					}

				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * @param file
	 * @param string
	 * @return
	 */
	private static void locateFiles(File file, String type, Vector files) {
		File[] list = file.listFiles();
		for (int i = 0; i < list.length; i++) {
			File file2 = list[i];
			if (file2.isDirectory())
				locateFiles(file2, type, files);
			else {
				String name = file2.getName();
				int pos = name.lastIndexOf(".");
				if (pos > 0) {
					name = name.substring(pos + 1);
					if (type.indexOf(name) >= 0)
						files.add(file2);
				}
			}
		}
	}

	/**
	 * @param movedFromPath
	 * @param fullPath
	 */
	public static void resourceMoved(IProject project, IPath fromPath, IPath toPath) {
		IPath oldPath = (IPath) fromPath.clone();
		if (!"java".equals(oldPath.getFileExtension()))
			return;
		oldPath = oldPath.removeFileExtension();
		String className = oldPath.lastSegment();
		oldPath = oldPath.removeLastSegments(1);
		oldPath = oldPath.append("resources");
		oldPath = oldPath.append(className + ".properties");
		final IFile oldPropFile = project.getWorkspace().getRoot().getFile(oldPath);
		if (oldPropFile.exists()) {
			IPath newPath = (IPath) toPath.clone();
			newPath = newPath.removeFileExtension();
			className = newPath.lastSegment();
			newPath = newPath.removeLastSegments(1);
			newPath = newPath.append("resources");
			newPath = newPath.append(className + ".properties");
			final IFile nf = project.getWorkspace().getRoot().getFile(newPath);
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						// System.out.println("moving "+oldPropFile.getLocation()+" to
						// "+nf.getLocation());
						if (!nf.getParent().exists()) {
							((IFolder) nf.getParent()).create(true, true, null);
						}
						oldPropFile.move(nf.getFullPath(), true, true, null);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
