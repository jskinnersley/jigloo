/*
 * Created on Aug 30, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.classloader;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormClassLoader2 extends URLClassLoader {

	public static FormClassLoader2 createClassLoader(URL[] urls, ClassLoader parent) {
		Vector folders = new Vector();
		Vector jars = new Vector();
		for (int i = 0; i < urls.length; i++) {
			URL url = urls[i];
			File file = new File(url.getFile());
			if(file.exists()) {
				if(file.isDirectory()) {
					folders.add(url);
				} else {
					jars.add(url);
				}
			} else {
				System.out.println("File "+file +" doesn't exist");
			}
		}
		URL[] jarURls = new URL[jars.size()];
		jars.toArray(jarURls);
		FormClassLoader2 jarLoader = new FormClassLoader2(jarURls, parent);
		URL[] folderURls = new URL[folders.size()];
		folders.toArray(folderURls);
		FormClassLoader2 folderLoader = new FormClassLoader2(folderURls, jarLoader);
		return folderLoader;
	}
	
	public FormClassLoader2(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public FormClassLoader2 createFreshCopy() {
		return new FormClassLoader2(getURLs(), getParent());
	}
	
	public Class findClass(String name) throws ClassNotFoundException {
		Class cls = super.findClass(name);
//		if(cls != null) {
//			System.out.println("FormClassLoader: found Class "+name);
//		}
		return cls;
	}
		
	public URL findResource(String name) {
//		System.out.println("FIND RES " + name);
		String nameCopy = name;
		URL res = super.findResource(nameCopy);
		if (res == null && !nameCopy.startsWith("/"))
			res = super.findResource("/" + nameCopy);
		int pos = 0;
		while (res == null && nameCopy != null && (pos = nameCopy.indexOf("/")) >= 0) {
			nameCopy = nameCopy.substring(pos);
			res = super.findResource(nameCopy);
			if (res == null) {
				nameCopy = nameCopy.substring(1);
				res = super.findResource(nameCopy);
			}
		}
		if(res == null && getParent() instanceof FormClassLoader2)
			res = ((FormClassLoader2)getParent()).findResource(name);
		return res;
	}

	private static Object getFieldObject(Class cls, Object inst, String name) throws Exception {
		Field fld = cls.getDeclaredField(name);
		fld.setAccessible(true);
		return fld.get(inst);
	}
	
	private static void setFieldObject(Class cls, Object inst, String name, Object val) throws Exception {
		Field fld = cls.getDeclaredField(name);
		fld.setAccessible(true);
		fld.set(inst, val);
	}
	
	private static void invokeMethod(Object inst, String name) throws Exception {
		inst.getClass().getMethod(name, null).invoke(inst, null);
	}
	
	public void dispose() {
		/*
		try {
			Disposer disp = (Disposer) getFieldObject(Disposer.class, null, "disposerInstance");
			if(disp != null) {
			}
			ReferenceQueue queue = (ReferenceQueue) getFieldObject(Disposer.class, null, "queue");
			if(queue != null) {
				while(queue.poll() != null);
			}
			Hashtable recs = (Hashtable) getFieldObject(Disposer.class, null, "records");
			if(recs != null) {
				recs.clear();
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
		*/
		/*
		try {
			loadedClasses.clear();

			//			invokeMethod(getFieldObject(ClassLoader.class, this, "classes"), "clear");
			
			invokeMethod(getFieldObject(ClassLoader.class, this, "packages"), "clear");

			Set set = (Set) getFieldObject(ClassLoader.class, this, "domains");
//			Iterator it = set.iterator();
//			while(it.hasNext()) {
//				setFieldObject(ProtectionDomain.class, (ProtectionDomain)it.next(), "classloader", null);
//			}
			invokeMethod(set, "clear");
			
			invokeMethod(getFieldObject(SecureClassLoader.class, this, "pdcache"), "clear");
			
		} catch(Throwable t) {
			t.printStackTrace();
		}
		*/
	}


	//	public InputStream getResourceAsStream(String name) {
	//		System.out.println("GET RES AS STREAM " + name);
	//		return super.getResourceAsStream(name);
	//	}

}
