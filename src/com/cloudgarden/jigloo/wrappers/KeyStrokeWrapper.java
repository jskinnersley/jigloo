/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import javax.swing.KeyStroke;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class KeyStrokeWrapper implements IWrapper {

    private KeyStroke keyStroke;
    private FormComponent comp;

    public KeyStrokeWrapper(Node node, FormComponent comp) {
        Node cn = NodeUtils.getChildNodeByName("KeyStroke", node);
        if (cn == null)
            cn = node;
        try {
            String c = NodeUtils.getAttribute("keyStroke", cn);
            if (c == null)
                c = "";
            keyStroke = KeyStroke.getKeyStroke(c);
        } catch (NumberFormatException e) {
        }
        this.comp = comp;
    }

    public KeyStrokeWrapper(KeyStroke locale, FormComponent comp) {
        this.keyStroke = locale;
        this.comp = comp;
    }

    public void generateXML(Element prop, Document document, String indent1,
            String indent2) {
        prop.setAttribute("type", "KeyStroke");
        Element loc = document.createElement("KeyStroke");
        prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
        prop.appendChild(loc);
        loc.setAttribute("keyStroke", keyStroke.toString());
        prop.appendChild(document.createTextNode("\n" + indent1));
    }

    public IWrapper getCopy(FormComponent fc) {
        if (fc == null)
            fc = comp;
        return new KeyStrokeWrapper(keyStroke, fc);
    }

    public Object getValue(Object obj) {
        return keyStroke;
    }

    public String getJavaConstructor(IJavaCodeManager jcm) {
        if(jiglooPlugin.useImports()) {
            jcm.addImport("javax.swing.KeyStroke");
            return "KeyStroke.getKeyStroke(\"" + toString()+"\")";
        } else {
            return "javax.swing.KeyStroke.getKeyStroke(\"" + toString()+"\")";
        }
    }

    public String toString() {
    	if(keyStroke == null)
    		return "";
//        Field[] fields = KeyEvent.class.getFields();
//        String str = ""+KeyEvent.getKeyText( keyStroke.getKeyCode());
//        for(int i=0;i<fields.length;i++) {
//            Object o = null;
//            try {
//                o = fields[i].get(null);
//            } catch (Exception e) {            }
//            if(o instanceof Integer && ((Integer)o).intValue() == keyStroke.getKeyCode()) {
//                str = fields[i].getName();
//            }
//        }
//        if(str.startsWith("VK_"))
//            str = str.substring(3);
//        int mods = keyStroke.getModifiers();
//        if((mods & KeyEvent.SHIFT_MASK) != 0)
//            str = "shift "+str;
//        if((mods & KeyEvent.ALT_MASK) != 0)
//            str = "alt "+str;
//        if((mods & KeyEvent.CTRL_MASK) != 0)
//            str = "ctrl "+str;
//        return str;
        return keyStroke.toString();
    }

	public KeyStroke getKeyStroke() {
		return keyStroke;
	}

}