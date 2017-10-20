/*
 * Created on Aug 30, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.classloader;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormClassLoader extends URLClassLoader {

	public FormClassLoader(URL[] urls, ClassLoader parent) {
//		super(urls, null);
		super(urls, parent);
	}

//	public URL locateClassContainer(Class cls) {
//        URL[] urls = getURLs();
//        for (int i = 0; i < urls.length; i++) {
//            try {
//                if (new FormClassLoader(new URL[] { urls[i] }, getParent()).findClass(cls.getName()) != null)
//                    return urls[i];
//            } catch (Throwable t) {
//
//            }
//        }
//        return null;
//	}
	
	protected synchronized Class loadClass(String name, boolean resolve)
	throws ClassNotFoundException
	{
		Class c = null;
//		try {
			c = super.loadClass(name, resolve);
//		} catch(ClassNotFoundException t) {
//			System.err.println("FormClassLoader: Unable to load class "+name);
//			throw t;
//		}
		return c;
	}

	public Class findClass(String name) throws ClassNotFoundException {
		Class cls = super.findClass(name);
//		if(cls != null)
//			System.out.println("FormClassLoader: found Class "+name);
		return cls;
	}
		
/*
	public URL getResource(String name) {
		//System.out.println("GET RES " + name);
		URL res = super.getResource(name);
		if (res == null && !name.startsWith("/"))
			res = super.getResource("/" + name);
		int pos = 0;
		while (res == null && name != null && (pos = name.indexOf("/")) >= 0) {
			name = name.substring(pos);
			res = super.getResource(name);
			if (res == null) {
				name = name.substring(1);
				res = super.getResource(name);
			}
		}
		//System.out.println("GOT RES " + name+", "+res);
		return res;
	}
	*/
	
	public URL findResource(String name) {
//		System.out.println("FIND RES " + name);
		URL res = super.findResource(name);
		if (res == null && !name.startsWith("/"))
			res = super.findResource("/" + name);
		int pos = 0;
		while (res == null && name != null && (pos = name.indexOf("/")) >= 0) {
			name = name.substring(pos);
			res = super.findResource(name);
			if (res == null) {
				name = name.substring(1);
				res = super.findResource(name);
			}
		}
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
