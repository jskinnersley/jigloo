package com.cloudgarden.jigloo.cache;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.cloudgarden.jigloo.classloader.NoMethod;
import com.cloudgarden.jigloo.util.ClassUtils;

public class Cacher {

    private static HashMap subclasses = new HashMap();
    private static HashMap methods = new HashMap();
    
    public static boolean isAssignableFrom(Class cls1, Class cls2) {
    	HashMap classMap = (HashMap)subclasses.get(cls1.getName());
    	if(classMap == null) {
    		classMap = new HashMap();
    		subclasses.put(cls1.getName(), classMap);
    	}
    	Boolean is = (Boolean) classMap.get(cls2.getName());
    	if(is == null) {
    		is = new Boolean(cls1.isAssignableFrom(cls2));
    		classMap.put(cls2.getName(), is);
    	}
    	return is.booleanValue();
    }
    
    public static void clearMethods() {
    	methods.clear();
    }
    
    public static Method getMethod(Class cls, String name, Class[] params) {
    	
//    	if(true) {
//        	try {
//    			return cls.getMethod(name, params);
//    		} catch (Throwable e) {
//    			return null;
//    		}
//    	}
    	
    	String skey;
    	if(params == null) {
    		skey = cls.getName()+"|"+name;
    	} else if(params.length == 1) {
    		skey = cls.getName()+"|"+name+"|"+params[0].getName();
    	} else if(params.length == 2) {
    		skey = cls.getName()+"|"+name+"|"+params[0].getName()+params[1].getName();
    	} else if(params.length == 3) {
    		skey = cls.getName()+"|"+name+"|"+params[0].getName()+params[1].getName()+params[2].getName();
    	} else if(params.length == 4) {
    		skey = cls.getName()+"|"+name+"|"+params[0].getName()+params[1].getName()+params[2].getName()+params[3].getName();
    	} else {
        	try {
    			return cls.getMethod(name, params);
    		} catch (Throwable e) {
    			return null;
    		}
    	}
    	
    	Object mo = methods.get(skey);
    	
    	if(mo instanceof Method)
    		return (Method)mo;
    	else if(mo instanceof NoMethod)
    		return null;
    	
    	try {
			Method m = cls.getMethod(name, params);
			methods.put(skey, m);
			return m;
    	} catch (Throwable e) {
    		Method[] meths = cls.getMethods();
    		for (int i = 0; i < meths.length; i++) {
    			Method m = meths[i];
    			if(!name.equals(m.getName()))
    				continue;
    			Class[] types = m.getParameterTypes();
    			if(types.length != params.length)
    				continue;
    			for (int j = 0; j < types.length; j++) {
    				Class t = types[j];
    				if(!ClassUtils.isAssignableFrom(t, params[j]))
    					break;
    				if(j == types.length -1) {
    					methods.put(skey, m);
    					return m;	                   
    				}
    			}
    		}

    		methods.put(skey, new NoMethod());
		}
		return null;
    }
}
