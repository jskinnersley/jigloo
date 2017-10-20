/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import javax.swing.JLayeredPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JLayerWrapper {

    private Object value;
    private FormComponent comp;

    public JLayerWrapper(Node node, FormComponent comp) {
    }

    public JLayerWrapper(Object value, FormComponent comp) {
    	this.comp = comp;
    	setValue(value);
    	if(comp == null)
    		System.out.println("COMP IS NULL");
    }

    public void generateXML(Element prop, Document document, String indent1,
            String indent2) {
    }

    public JLayerWrapper getCopy(FormComponent fc) {
        return new JLayerWrapper(value, fc);
    }

    public Object getDisplayValue() {
    	if(value instanceof FormComponent)
    		return ((FormComponent)value).getNameInCode();
    	if(JLayeredPane.DEFAULT_LAYER.equals(value))
    		return "DEFAULT_LAYER";
    	if(JLayeredPane.DRAG_LAYER.equals(value))
    		return "DRAG_LAYER";
    	if(JLayeredPane.FRAME_CONTENT_LAYER.equals(value))
    		return "FRAME_CONTENT_LAYER";
    	if(JLayeredPane.MODAL_LAYER.equals(value))
    		return "MODAL_LAYER";
    	if(JLayeredPane.PALETTE_LAYER.equals(value))
    		return "PALETTE_LAYER";
    	if(JLayeredPane.POPUP_LAYER.equals(value))
    		return "POPUP_LAYER";
        return value+"";
    }

    public Object getValue() {
    	if(value instanceof String) {
    		FormComponent ival = comp.getEditor().getJavaCodeParser().findFormComponent((String) value);
    		if(ival != null)
    			return ival.getNonVisualObject();
    	}
    	if(!(value instanceof Integer)) {
    		System.out.println("This should be an integer "+value);
    		return JLayeredPane.DEFAULT_LAYER;
    	}
        return value;
    }

    public void setValue(Object value) {
    	if("DEFAULT_LAYER".equals(value))
    		value = JLayeredPane.DEFAULT_LAYER;
    	if("DRAG_LAYER".equals(value))
    		value = JLayeredPane.DRAG_LAYER;
    	if("FRAME_CONTENT_LAYER".equals(value))
    		value = JLayeredPane.FRAME_CONTENT_LAYER;
    	if("MODAL_LAYER".equals(value))
    		value = JLayeredPane.MODAL_LAYER;
    	if("PALETTE_LAYER".equals(value))
    		value = JLayeredPane.PALETTE_LAYER;
    	if("POPUP_LAYER".equals(value))
    		value = JLayeredPane.POPUP_LAYER;
    	this.value = value;
    }
    
    public String getJavaConstructor(IJavaCodeManager jcm) {
    	return toString();
    }

    public String toString() {
    	if(JLayeredPane.DEFAULT_LAYER.equals(value))
    		return "JLayeredPane.DEFAULT_LAYER";
    	if(JLayeredPane.DRAG_LAYER.equals(value))
    		return "JLayeredPane.DRAG_LAYER";
    	if(JLayeredPane.FRAME_CONTENT_LAYER.equals(value))
    		return "JLayeredPane.FRAME_CONTENT_LAYER";
    	if(JLayeredPane.MODAL_LAYER.equals(value))
    		return "JLayeredPane.MODAL_LAYER";
    	if(JLayeredPane.PALETTE_LAYER.equals(value))
    		return "JLayeredPane.PALETTE_LAYER";
    	if(JLayeredPane.POPUP_LAYER.equals(value))
    		return "JLayeredPane.POPUP_LAYER";
    	if(value instanceof Integer)
    		return "new Integer("+value+")";
		return value+"";
    }

}