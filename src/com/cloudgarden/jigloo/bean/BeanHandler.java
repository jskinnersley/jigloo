package com.cloudgarden.jigloo.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Image;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
import com.cloudgarden.jigloo.images.ImageDisplayer;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.util.JiglooUtils;

public class BeanHandler {

	private static HashMap beanInfos = new HashMap();
	private static HashMap beanImgs = new HashMap();
	private static Thread imgGetterThread = null;
	private static boolean imgGetterThreadDead = true;
	private static Vector imgGetterQueue = new Vector();
	
	public static void clear(){
		beanInfos.clear();
	}
	
	public static void addBeanInfoURLS(Vector urls) {
		try {
			String beanInfoDirName = jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_INFO_FOLDER);
			if(beanInfoDirName == null || "".equals(beanInfoDirName) || jiglooPlugin.getActiveEditor() == null)
				return;
			
			File beanInfoDir = new File(beanInfoDirName);
			addJars(beanInfoDir, urls);
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	private static void addJars(File file, Vector urls) {
		try {
			urls.add(file.toURL());
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].isFile() && (files[i].getName().endsWith(".jar") || files[i].getName().endsWith(".zip")))
					urls.add(files[i].toURL());
//				if(files[i].isDirectory())
//					addJars(files[i], urls);
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static Image getBeanImage(Class cls, ImageDisplayer action, final IProject proj) {
//	    if(true)
//	        return getBeanImage(cls);
	    
		if (!jiglooPlugin.canUseSwing() || cls == null)
			return null;
		String clsName = cls.getName();
		if (beanImgs.containsKey(clsName)) {
//			System.err.println("FOUND IMG FOR CLASS "+clsName+", "+beanImgs.get(clsName));
			return (Image) beanImgs.get(clsName);
		}
		
//		System.out.println("adding image "+cls+" to imgGetterQueue");
		imgGetterQueue.add(new Object[] {cls, action});
		
		if(imgGetterThread == null || imgGetterThreadDead) {
            imgGetterThreadDead = false;
		    imgGetterThread = new Thread() {
                public void run() {
                    while(imgGetterQueue.size() > 0) {
                        Object[] obj = (Object[])imgGetterQueue.elementAt(0);
                        imgGetterQueue.remove(0);
                        Class cls = (Class)obj[0];
                        ImageDisplayer action = (ImageDisplayer)obj[1];
                        obj[0] = null;
                        obj[1] = null;
                        Image img = getBeanImage(cls, proj);
                        if(img != null) {
                            action.setImage(img);
                        }
                    }
                    imgGetterThreadDead = true;
                }
		    };
		    imgGetterThread.start();
		}
		return null;
	}

	public static Image getBeanImage(Class cls, IProject proj) {
		try {
			if (!jiglooPlugin.canUseSwing() || cls == null)
				return null;
			String clsName = cls.getName();
			if (beanImgs.containsKey(clsName)) {
//				System.err.println("FOUND IMG FOR CLASS "+clsName+", "+beanImgs.get(clsName));
				return (Image) beanImgs.get(clsName);
			}
			BeanInfo bi = getBeanInfo(cls, proj);
			if (bi == null) {
//				System.out.println("No bean info for " + cls);
				return null;
			}
			java.awt.Image bimg = getAwtBeanIcon(bi);
			if(bimg == null)
				return null;
			Image img = JiglooUtils.getSWTImage(bimg);
			if (img != null)
				beanImgs.put(clsName, img);
//			System.err.println("MADE IMG FOR CLASS "+clsName+", "+beanImgs.get(clsName));
			return img;
		} catch (Throwable t) {
			System.out.println("Error getting bean icon for "+cls);
//			jiglooPlugin.handleError(t, "Error getting bean icon");
			return null;
		}
	}
	/**
	 * @param bi
	 * @return
	 */
	public static java.awt.Image getAwtBeanIcon(BeanInfo bi) {
		BeanInfo[] addBI = bi.getAdditionalBeanInfo();
		java.awt.Image bimg = bi.getIcon(BeanInfo.ICON_COLOR_16x16);
		if(bimg == null && addBI != null) {
		    for (int i = 0; i < addBI.length; i++) {
                bimg = addBI[i].getIcon(BeanInfo.ICON_COLOR_16x16);
                if(bimg != null)
                    break;
            }
		}
		if (bimg == null)
			bimg = bi.getIcon(BeanInfo.ICON_MONO_16x16);
		if (bimg == null)
			bimg = bi.getIcon(BeanInfo.ICON_COLOR_32x32);
		if (bimg == null)
			bimg = bi.getIcon(BeanInfo.ICON_MONO_32x32);
		if (bimg == null) {
//			System.out.println("No bean image found for " + cls);
			return null;
		}
		return bimg;
	}


	public static BeanInfo getBeanInfo(Class cls, IProject proj) {
		try {
			return getBeanInfo(cls, proj, false);
		} catch (Throwable e) {
			jiglooPlugin.handleError(e, "Error in getBeanInfo for class " + cls);
		}
		return null;
	}

	public static BeanInfo getBeanInfo(Class cls, IProject proj, boolean throwErrors) throws Throwable {
		if (cls == null)
			return null;
		//4.0.6 - don't re-call getBeanInfo
		Object obj = beanInfos.get(cls.getName());
		if (obj instanceof NoBeanInfo)
			return null;
		if(obj != null)
			return (BeanInfo) obj;
		try {
			BeanInfo bi = null;
			Class biClass;
			
			try {
				biClass = ClassLoaderCache.loadClass(proj, cls.getName()+"BeanInfo", cls.getClassLoader(), false);
				if(biClass != null)
					bi = (BeanInfo) biClass.newInstance();
			} catch(Throwable t) {}
			bi = testForGenericBeanInfo(bi);
			
			if(bi == null)
				bi = Introspector.getBeanInfo(cls);
			bi = testForGenericBeanInfo(bi);
			
//			if(bi == null) {
//				try {
//					biClass = cls.getClassLoader().loadClass(cls.getName()+"BeanInfo");
//					if(biClass != null)
//						bi = (BeanInfo) biClass.newInstance();
//				} catch(Throwable t) {}
//			}
//			bi = testForGenericBeanInfo(bi);
						
//			if(bi == null) {
//				String beanInfoDirName = getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_INFO_FOLDER);
//				if(beanInfoLoader == null) {
//					ClassLoader cl = getActiveEditor().getClassLoader();
//					File beanInfoDir = new File(beanInfoDirName);
//					File[] jars = beanInfoDir.listFiles();
//					Vector urls = new Vector();
//					urls.add(beanInfoDir.toURL());
//					for (int i = 0; i < jars.length; i++) {
//						if(jars[i].getName().endsWith(".jar") || jars[i].getName().endsWith(".zip"))
//							urls.add(jars[i].toURL());
//					}
//					URL[] urlArray = new URL[urls.size()];
//					urls.copyInto(urlArray);
//					beanInfoLoader = new FormClassLoader(urlArray, cl);
//				}
//				try {
//					Class biClass = beanInfoLoader.loadClass(cls.getName()+"BeanInfo");
//					if(biClass != null) {
//						try {
//							bi = (BeanInfo) biClass.newInstance();
//							bi = testForGenericBeanInfo(bi);
//							if(bi != null)
//								System.out.println("Loaded "+biClass+" from bean info path: "+beanInfoDirName);
//						} catch(Throwable t2) {
//						}
//					}
//				} catch(Throwable t) {
//				}
//			}
			
			if(bi != null) {
//				System.out.println("ADDING NON-GENERIC BEANINFO "+bi+" for "+cls);
			    beanInfos.put(cls.getName(), bi);
			    return bi;
			} else {
				beanInfos.put(cls.getName(), new NoBeanInfo());
			}
		} catch (Throwable e) {
			beanInfos.put(cls.getName(), new NoBeanInfo());
			if (throwErrors)
				throw e;
		}
		return null;
	}

	private static BeanInfo testForGenericBeanInfo(BeanInfo bi) {
		if(bi == null)
			return bi;
		if(bi.getClass().getName().equals("java.beans.GenericBeanInfo"))
			return null;
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (int i = 0; i < pds.length; i++) {
		    PropertyDescriptor descriptor = pds[i];
		    if("class".equals(descriptor.getName())) {
		        bi = null;
		        break;
		    }
		}
		return bi;
	}


}
