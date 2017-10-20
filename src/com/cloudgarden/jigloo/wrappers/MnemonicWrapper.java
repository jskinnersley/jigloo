/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.event.KeyEvent;
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
public class MnemonicWrapper implements IWrapper {

    private Integer mnem;
    private FormComponent comp;

    public MnemonicWrapper(Node node, FormComponent comp) {
        Node cn = NodeUtils.getChildNodeByName("Mnemonic", node);
        if (cn == null)
            cn = node;
        try {
            String c = NodeUtils.getAttribute("mnemonic", cn);
            if (c == null)
                c = "";
            mnem = new Integer(c);
        } catch (NumberFormatException e) {
        }
        this.comp = comp;
    }

    public MnemonicWrapper(Integer mnem, FormComponent comp) {
        this.mnem = mnem;
        this.comp = comp;
    }

    public void generateXML(Element prop, Document document, String indent1,
            String indent2) {
        prop.setAttribute("type", "Mnemonic");
        Element loc = document.createElement("Mnemonic");
        prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
        prop.appendChild(loc);
        loc.setAttribute("mnemonic", mnem.toString());
        prop.appendChild(document.createTextNode("\n" + indent1));
    }

    public IWrapper getCopy(FormComponent fc) {
        if (fc == null)
            fc = comp;
        return new MnemonicWrapper(mnem, fc);
    }

    public Object getValue(Object obj) {
        return mnem;
    }

    public void setValue(Integer mnem) {
    	this.mnem = mnem;
    }
    
    public String getJavaConstructor(IJavaCodeManager jcm) {
        return "java.awt.event.KeyEvent." + toString();
    }

    private static final Field[] fields = KeyEvent.class.getFields();
    private static Object[] fieldVals = null;

    public String toString() {
        if (fieldVals == null) {
            fieldVals = new Object[fields.length];
            for (int j = 0; j < fields.length; j++) {
                try {
                    fieldVals[j] = fields[j].get(null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                if (mnem.equals(fieldVals[i]))
                    return fields[i].getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mnem.toString();
    }

	/**
	 * @return
	 */
	public FormComponent getFormComponent() {
		return comp;
	}

}