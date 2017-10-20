/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.Cursor;
import java.lang.reflect.Field;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AWTCursorWrapper implements IWrapper {

    private java.awt.Cursor cursor;
    private FormComponent comp;

    public AWTCursorWrapper(Node node, FormComponent comp) {
        Node cn = NodeUtils.getChildNodeByName("Cursor", node);
        if (cn == null)
            cn = node;
        try {
            String c = NodeUtils.getAttribute("cursor", cn);
            if (c == null)
                c = "";
            throw new RuntimeException("Not implemented");
//            locale = new Integer(c);
        } catch (NumberFormatException e) {
        }
        this.comp = comp;
    }

    public AWTCursorWrapper(java.awt.Cursor locale, FormComponent comp) {
        this.cursor = locale;
        this.comp = comp;
    }

    public void generateXML(Element prop, Document document, String indent1,
            String indent2) {
        prop.setAttribute("type", "Cursor");
        Element loc = document.createElement("Cursor");
        prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
        prop.appendChild(loc);
        loc.setAttribute("cursor", cursor.toString());
        prop.appendChild(document.createTextNode("\n" + indent1));
    }

    public IWrapper getCopy(FormComponent fc) {
        if (fc == null)
            fc = comp;
        return new AWTCursorWrapper(cursor, fc);
    }

    public Object getValue(Object obj) {
        return cursor;
    }

    public String getJavaConstructor(IJavaCodeManager jcm) {
   		return "java.awt.Cursor.getPredefinedCursor(java.awt.Cursor."+toString()+")";
    }

    private static Field[] fields = null;
    static {
    	try {
    		fields = Cursor.class.getFields();
    	} catch (Throwable t) {
    		
    	}
    }
    private Object[] fieldVals = null;

    public String toString() {
        if (fieldVals == null) {
            fieldVals = new Object[fields.length];
            for (int j = 0; j < fields.length; j++) {
            	Integer val = null;
                try {
                    val = (Integer)fields[j].get(null);
                    if(val.intValue() >= 0)
                    	fieldVals[j] = Cursor.getPredefinedCursor(val.intValue());
                } catch (Exception e1) {
                	System.err.println("Error getting predefined cursor "+val);
                }
            }
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                if (cursor.equals(fieldVals[i]))
                    return fields[i].getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cursor.toString();
    }

}