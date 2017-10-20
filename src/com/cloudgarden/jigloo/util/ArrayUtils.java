/*
 * Created on Dec 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.util;

import java.lang.reflect.Array;
import java.util.Vector;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ArrayUtils {

    private static void getDims(Object array, Vector dims) {
    	if(array != null && array.getClass().isArray()) {
    		int len = Array.getLength(array);
            dims.add(new Integer(len));
            if(len > 0)
            	getDims(Array.get(array, 0), dims);
    	}
//        if(array instanceof Object[]) {
//            Object[] a2 = (Object[])array;
//            dims.add(new Integer(a2.length));
//            if(a2.length > 0)
//                getDims(a2[0], dims);
//        }
    }
    
    public static int[] getDims(Object array) {
        Vector dims = new Vector();
        getDims(array, dims);
        int[] dimArray = new int[dims.size()];
        for (int i = 0; i < dimArray.length; i++) {
            dimArray[i] = ((Integer)dims.elementAt(i)).intValue();    
        }
        return dimArray;
    }
    
    public static Object convertToPrimitiveArray(Object vals, String typeName, FormEditor editor) {
        int[] dimArray = getDims(vals);
        Class cls = null;
        Object ret = null;
        if(typeName.equals("double"))
            cls = double.class;
        else if(typeName.equals("int"))
            cls = int.class;
        else if(typeName.equals("long"))
            cls = long.class;
        else if(typeName.equals("short"))
            cls = short.class;
        else if(typeName.equals("float"))
            cls = float.class;
        else if(typeName.equals("byte"))
            cls = byte.class;
        else if(typeName.equals("char"))
            cls = char.class;
        else if(typeName.equals("java.lang.String"))
            cls = String.class;
        else if(typeName.equals("boolean"))
            cls = boolean.class;

        if(cls == null)
            return null;
        
        ret = Array.newInstance(cls, dimArray);

        int len = -1;
        if(vals.getClass().isArray()) {
        	len = Array.getLength(vals);
        }
        for (int i = 0; i < len; i++) {
        	Object vali = Array.get(vals, i);
        	if(vali != null && vali.getClass().isArray()) {
                Object con = convertToPrimitiveArray(vali, typeName, editor);
                Array.set(ret, i, con);
            } else {
                fillInArray(vali, ret, i);
            }
        }
        
        return ret;
    }
    
    private static String tidyNumberString(Object val) {
        String str = val.toString();
        if (str.endsWith("f") || str.endsWith("d") || str.endsWith("L"))
            return str.substring(0, str.length() - 1);
        return str;
    }

    private static void fillInArray(Object val, Object array, int pos) {
        try {
            if(array instanceof double[]) {
                ((double[])array)[pos] = Double.parseDouble(tidyNumberString(val));
            } else if(array instanceof int[]) {
                ((int[])array)[pos] = Integer.parseInt(tidyNumberString(val));
            } else if(array instanceof float[]) {
                ((float[])array)[pos] = Float.parseFloat(tidyNumberString(val));
            } else if(array instanceof long[]) {
                ((long[])array)[pos] = Long.parseLong(tidyNumberString(val));
            } else if(array instanceof short[]) {
                ((short[])array)[pos] = Short.parseShort(tidyNumberString(val));
            } else if(array instanceof boolean[]) {
                ((boolean[])array)[pos] = Boolean.valueOf(val+"").booleanValue();
            } else if(array instanceof byte[]) {
                ((byte[])array)[pos] = Byte.decode(val+"").byteValue();
            } else if(array instanceof char[]) {
                ((char[])array)[pos] = ("" + val).toCharArray()[0];
            } else if(array instanceof String[]) {
                ((String[])array)[pos] = ""+val;
            }

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

	public static double[] stringToDoubleArray(String o) {
	    o = JiglooUtils.replace(o, " ", "");
	    o = JiglooUtils.replace(o, "[", "");
	    o = JiglooUtils.replace(o, "]", "");
	    try {
	        String[] parts = JiglooUtils.split(",", o);
	        double[] val = new double[parts.length];
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            val[i] = Double.parseDouble(part);
	        }
	        return val;
	    } catch (NumberFormatException e) {
	        //e.printStackTrace();
	    }
	    return new double[0];
	}

	public static String[] stringToStringArray(String o) {
	    o = JiglooUtils.replace(o, "[", "");
	    o = JiglooUtils.replace(o, "]", "");
	    String[] parts = JiglooUtils.split(",", o);
	    if (parts.length == 0)
	        return parts;
	    return parts;
	}

	public static int[] stringToIntArray(String o) {
	    o = JiglooUtils.replace(o, " ", "");
	    o = JiglooUtils.replace(o, "[", "");
	    o = JiglooUtils.replace(o, "]", "");
	    try {
	        String[] parts = JiglooUtils.split(",", o);
	        int[] val = new int[parts.length];
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            val[i] = Integer.parseInt(part);
	        }
	        return val;
	    } catch (NumberFormatException e) {
	        //e.printStackTrace();
	    }
	    return new int[0];
	}

	public static FormComponent[] stringToFormComponentArray(String o, JavaCodeParser jcp) {
	    o = JiglooUtils.replace(o, " ", "");
	    o = JiglooUtils.replace(o, "[", "");
	    o = JiglooUtils.replace(o, "]", "");
	    String[] parts = JiglooUtils.split(",", o);
	    FormComponent[] val = new FormComponent[parts.length];
	    for (int i = 0; i < parts.length; i++) {
	        String part = parts[i];
	        val[i] = jcp.findFormComponent(part);
	    }
	    return val;
	}


}
