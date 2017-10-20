/*
 * Created on May 18, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JInternalFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DefaultValueManager {

	private static final HashMap cache = new HashMap();
	private static Vector failedClasses = new Vector();

	public static boolean addClassObject(Object obj, boolean newInst) {
		
		Class clazz = obj.getClass();
		
		String clazzName = clazz.getName();
		int pos = clazzName.indexOf("$");
		if(pos > 0) {
		    //this accounts for cases where an abstract class might have been
		    //implemented by an anonymous class in the getGUIBuilderInstance
		    //method. eg, vetest.NewJChildFrame2$2 extends 
		    //vetest.NewJChildFrame2
		    clazzName = clazzName.substring(0, pos);
			Class sc = clazz.getSuperclass();
			if(sc.getName().equals(clazzName))
			    clazz = sc;
		}
		if (cache.containsKey(clazzName) && !failedClasses.contains(clazzName))
			return false;
		if(!jiglooPlugin.getDefault().canMakeNVClass(clazz))
			return false;
		HashMap vals = new HashMap();

		try {
			//try to make a new, "clean" object (since the one passed
			//in might have properties already set by the constructor,
			//eg, GridData).
		    //System.out.println("DEF VAL MAN CREATING "+clazz);
			//Object nobj = clazz.newInstance();
		    if(newInst) {
				Object nobj = ClassUtils.newInstance(clazz, null, null, false);
				obj = nobj;
		    }
		} catch (Throwable e) {}

		Vector names = PropertySourceFactory.findPropertyNames(clazz);
		for (int i = 0; i < names.size(); i++) {
			String n = (String) names.elementAt(i);

			//handle special case(s) (where properties may be set
			//by constructor arguments.
			if ("visible".equals(n)) {
			    if( Cacher.isAssignableFrom(JInternalFrame.class, clazz))
					vals.put(n, Boolean.FALSE);
			    else
			        vals.put(n, Boolean.TRUE);
			} else if ("alignment".equals(n) && obj instanceof Control) {
				vals.put(n, new Integer(SWT.LEFT));
			} else {
				vals.put(n, getPropVal(n, obj));
			}
		}
		names = PropertySourceFactory.findFieldNames(clazz);
		for (int i = 0; i < names.size(); i++) {
			String n = (String) names.elementAt(i);
			vals.put(n, getFieldVal(n, obj));
		}
		cache.put(clazzName, vals);
		//we might have succeeded now when failed previously
		//(eg, for abstract classes implementing the getGUIBuilderInstance
		//method)
		failedClasses.remove(clazzName);
		return true;
	}

	public static boolean hasClass(Class cls) {
		if(cls == null)
			return false;
		return cache.containsKey(cls.getName());
	}

	public static Object getDefault(String propName, Object obj) {
		Class cls = obj.getClass();
		if (!hasClass(cls)) {
		    addClassObject(obj, true);
		}
		return getDefault(propName, cls);
	}

	public static Object getDefault(String propName, Class cls) {
	    return getDefault(propName, cls, null);
	}
	
	public static Object getDefault(String propName, Class cls, Object guess) {
	    //if we fail to get a default value, then use guess as a guess, and place
	    //it in the vals hash map so we will *always* return guess (ie, the default
	    //value is at least constant, even if it might not be right)
		if(!jiglooPlugin.getDefault().canMakeNVClass(cls))
			return null;
		String clsName = cls.getName();
		HashMap vals = (HashMap) cache.get(clsName);
		if (failedClasses.contains(clsName)) {
		    if(vals == null)
		        return null;
		    return vals.get(propName);
		}
		try {
			if (vals == null) {
			    Object obj = null;
                try {
                    Method gbm = cls.getMethod("getGUIBuilderInstance", null);
                    obj = (Component)gbm.invoke(null, null);
                } catch(Throwable t1) { }
                if(obj == null) {
				    try {
				        obj = cls.newInstance();
					} catch (Throwable e) {	}
                }
				if(obj == null) {
				    if(!failedClasses.contains(clsName))
				        failedClasses.add(clsName);
//					if(guess != null)
//					    vals.put(propName, guess);
					//System.err.println("[DefaultValueManager] Error trying to create " + cls + ", "+propName);
					return guess;
				} else {
					addClassObject(obj, false);
					vals = (HashMap) cache.get(clsName);
				}
			}
			Object val = null;
			if(vals != null)
			    val = vals.get(propName);
			//if(propName.equals("border"))
			//System.out.println("GOT DEF "+propName+", "+cls+", "+vals.get(propName)+", "+guess);
			if(val == null)
			    val = guess;
			return val;
		} catch (Throwable e) {
		    if(!failedClasses.contains(clsName))
		    	failedClasses.add(clsName);
			if(guess != null && vals != null)
			    vals.put(propName, guess);
			return guess;
		}
	}

	private static Object getFieldVal(String name, Object obj) {
		try {
			return obj.getClass().getField(name).get(obj);
		} catch (Exception e) {
			return null;
		}
	}
	private static Object getPropVal(String name, Object obj) {
		try {
			Method f = null;
			String cname= JiglooUtils.capitalize(name);
			try {
				f = Cacher.getMethod(obj.getClass(), "get" + cname, null);
			} catch (Exception e) {
				f = Cacher.getMethod(obj.getClass(), "is" + cname, null);
			}
//			JiglooUtils.objVec.add(f);
			Object val = f.invoke(obj, null);
//			JiglooUtils.objVec.remove(JiglooUtils.objVec.lastElement());
			return val;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 */
	public static void clearCache() {
		failedClasses.clear();
		Iterator it = cache.keySet().iterator();
		while(it.hasNext()) {
			HashMap map = (HashMap) cache.get(it.next());
			map.clear();
		}
		cache.clear();
	}
}
