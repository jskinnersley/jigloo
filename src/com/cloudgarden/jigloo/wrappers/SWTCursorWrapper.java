/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.lang.reflect.Field;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.resource.CursorManager;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SWTCursorWrapper implements IWrapper {

    private Cursor cursor;
    private int type;
    private FormComponent comp;

    public SWTCursorWrapper(Node node, FormComponent comp) {
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

    public SWTCursorWrapper(int type, FormComponent comp) {
        this.cursor = CursorManager.getCursor(type);
        this.type = type;
        this.comp = comp;
    }

    public SWTCursorWrapper(Cursor cursor, FormComponent comp) {
        this.cursor = cursor;
        this.type = SWT.CURSOR_ARROW;
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
        return new SWTCursorWrapper(type, fc);
    }

    public Object getValue(Object obj) {
        return cursor;
    }

    public String getJavaConstructor(IJavaCodeManager jcm) {
    	jcm.addImport("com.cloudgarden.resource.SWTResourceManager");
    	jcm.addImport(SWT.class.getName());
   		return "SWTResourceManager.getCursor(SWT."+toString()+")";
    }

    private static Field[] fields = null;
    static {
    	try {
    		Vector names = new Vector();
    		Field[] flds = SWT.class.getFields();
    		for (int i = 0; i < flds.length; i++) {
    			Field field = flds[i];
    			if(field.getName().startsWith("CURSOR_"))
    				names.add(field);
    		}
    		fields = new Field[names.size()];
    		names.copyInto(fields);
    		
    	} catch (Throwable t) {
    		
    	}
    }
    private Object[] fieldVals = null;

    public String toString() {
        if (fieldVals == null) {
            fieldVals = new Object[fields.length];
            for (int j = 0; j < fields.length; j++) {
                try {
                    Integer val = (Integer)fields[j].get(null);
                    fieldVals[j] = CursorManager.getCursor(val.intValue());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                if (cursor == null || cursor.equals(fieldVals[i]))
                    return fields[i].getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cursor.toString();
    }

}