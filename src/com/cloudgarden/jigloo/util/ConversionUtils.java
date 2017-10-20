package com.cloudgarden.jigloo.util;

import java.awt.Dimension;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.eval.ArrayHolder;
import com.cloudgarden.jigloo.eval.ConstructorHolder;
import com.cloudgarden.jigloo.eval.RootMethodCall;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupLayoutUtils;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.sources.BorderPropertySource;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;
import com.cloudgarden.jigloo.properties.sources.RectanglePropertySource;
import com.cloudgarden.jigloo.properties.sources.SizePropertySource;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;

public class ConversionUtils {

	/**
	 * @param param
	 * @return
	 */
	public static Object convertParamToObject(Object param, boolean createIfNecessary) {
	    if(param instanceof FormComponent) {
	        return ((FormComponent)param).getObject(createIfNecessary);
	    } else if(param instanceof RootMethodCall) {
	        return ((RootMethodCall)param).invoke();
	    } else if(param instanceof ArrayHolder) {
	        return ((ArrayHolder)param).getRawArray();                		
	    } else if(param instanceof ConstructorHolder) {
	        return ((ConstructorHolder)param).newInstance();
	    }
	    return param;
	}

	public static void convertToDisplayCoords(MouseEvent e) {
	    Control ctrl = (Control) e.widget;
	    Point pt = ctrl.toDisplay(e.x, e.y);
	    e.x = pt.x;
	    e.y = pt.y;
	}

	public static Object convertObject(Object obj, Class dType) {
	    String dtName = dType.getName();
	
	    if (obj instanceof ClassWrapper) {
	        ClassWrapper cw = (ClassWrapper) obj;
	        return cw.getValue(null);
	    }
	
	    if (obj instanceof FormComponent) {
	        FormComponent fc = (FormComponent) obj;
	        return fc.getObject(true);
	    }
	    
	    //for Composite.tabList property.
	    if (obj instanceof FormComponent[] && dType.equals(Control[].class)) {
	        FormComponent[] fcs = (FormComponent[])obj;
	        Control[] cons = new Control[fcs.length];
	        for (int i = 0; i < cons.length; i++) {
	            cons[i] = (Control) fcs[i].getControl();
	        }
	        return cons;
	    }
	
	    if (dtName.startsWith("java.awt") || dtName.startsWith("javax.swing"))
	        return convertToAWT(obj, dType, true);
	    return convertToSWT(dType, true);
	}

	public static Object convertToSWT(Object value) {
	    return convertToSWT(value, false);
	}

	public static Object convertToSWT(Object value, boolean strict) {
	    if (value instanceof java.awt.Point) {
	        java.awt.Point pt = (java.awt.Point) value;
	        value = new Point(pt.x, pt.y);
	    } else if (value instanceof java.awt.Rectangle) {
	        java.awt.Rectangle rec = (java.awt.Rectangle) value;
	        value = new Rectangle(rec.x, rec.y, rec.width, rec.height);
	    } else if (value instanceof Dimension) {
	        Dimension dim = (Dimension) value;
	        value = new Point(dim.width, dim.height);
	    }
	    if (strict)
	        return null;
	    return value;
	}

	public static Object convertToRawValue(Object value, Object control) {
	    try {
	        if (value instanceof SizePropertySource) {
	            value = ((SizePropertySource) value).getValue();
	        } else if (value instanceof RectanglePropertySource) {
	            value = ((RectanglePropertySource) value).getValue();
	        } else if (value instanceof BorderPropertySource) {
	            value = ((BorderPropertySource) value).getValue();
	        } else if (value instanceof InsetsPropertySource) {
	            value = ((InsetsPropertySource) value).getValue();
	        } else if (value instanceof FontWrapper) {
	            value = ((FontWrapper) value).getFont(control);
	        } else if (value instanceof IconWrapper) {
	            value = ((IconWrapper) value).getIcon();
	        } else if (value instanceof ImageWrapper) {
	            value = ((ImageWrapper) value).getImage(control);
	        } else if (value instanceof ColorWrapper) {
	            value = ((ColorWrapper) value).getColor(control);
	        } else if (value instanceof IWrapper) {
	            value = ((IWrapper) value).getValue(null);
	        } else if (value instanceof FieldWrapper) {
	            value = ((FieldWrapper) value).getValue();
	        } else if (value instanceof FormComponent) {
	            value = ((FormComponent) value).getObject(false);
	        }
	        return value;
	    } catch (Throwable e) {
	        jiglooPlugin.handleError(e);
	        return null;
	    }
	}

	public static Object[] convertToAWT(Object[] params) {
	    if (params == null)
	        return null;
	    Object[] nps = new Object[params.length];
	    for (int i = 0; i < params.length; i++) {
	        nps[i] = convertToAWT(params[i], Dimension.class);
	        //			Object o = params[i];
	        //			if (o instanceof Point) {
	        //				Point pt = (Point) o;
	        //				nps[i] = new Dimension(pt.x, pt.y);
	        //			} else {
	        //				nps[i] = params[i];
	        //			}
	    }
	    return nps;
	}

	public static Object convertToAWT(Object value, Class type) {
	    return convertToAWT(value, type, false);
	}

	public static Object convertToAWT(Object value, Class type, boolean strict) {
	    if (value instanceof Point) {
	        Point pt = (Point) value;
	        if (java.awt.Point.class.equals(type))
	            value = new java.awt.Point(pt.x, pt.y);
	        else
	            value = new Dimension(pt.x, pt.y);
	        return value;
	    }
	    if (value instanceof Rectangle) {
	        Rectangle rec = (Rectangle) value;
	        return new java.awt.Rectangle(rec.x, rec.y, rec.width, rec.height);
	    }
	    if (value instanceof Font) {
	        Font val = (Font) value;
	        FontData fd = val.getFontData()[0];
	        return new java.awt.Font(fd.getName(), fd.getStyle(), fd
	                .getHeight());
	    }
	    if (value instanceof Color) {
	        Color val = (Color) value;
	        return new java.awt.Color(val.getRed(), val.getGreen(), val
	                .getBlue());
	    }
	    if (strict)
	        return null;
	    return value;
	}

	public static String convertUnavailableClassName(String clsName) {
		clsName = GroupLayoutUtils.convertUnavailableClassName(clsName);
	    //v4.0M3
	    if(clsName.equals("javax.swing.Action") || clsName.equals("javax.swing.AbstractAction")) {
	        clsName =  "com.cloudgarden.jigloo.actions.ActionStub";
	    }
	    return clsName;
	}

	public static Class convertPrimitiveClassToObjectClass(Class type) {
		if (type.equals(int.class))
		    type = Integer.class;
		else if (type.equals(float.class))
		    type = Float.class;
		else if (type.equals(long.class))
		    type = Long.class;
		else if (type.equals(short.class))
		    type = Short.class;
		else if (type.equals(double.class))
		    type = Double.class;
		else if (type.equals(boolean.class))
		    type = Boolean.class;			
		return type;
	}

}
