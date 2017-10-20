/*
 * Created on Nov 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.layoutHandler;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XYLayoutHandler {

    private static final String CLASS_NAME = 
        "com.borland.jbcl.layout.XYLayout";
    
    public static final String CONSTRAINT_NAME = 
        "com.borland.jbcl.layout.XYConstraints";
    
    /**
     * @param mainClassName
     * @return
     */
    public static boolean usesAbsoluteTypeLayout() {
        return true;
    }

    /**
     * @param parent
     * @return
     */
    public static boolean handlesLayout(FormComponent parent) {
        return CLASS_NAME.equals(parent.getLayoutWrapper().getMainClassName());
    }

    /**
     * @param component
     * @param bounds
     */
    public static void setBounds(FormComponent fc, Rectangle bounds) {
        LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
        ldw.setPropertyValue("x", new Integer(bounds.x));
        ldw.setPropertyValue("y", new Integer(bounds.y));
        ldw.setPropertyValue("width", new Integer(bounds.width));
        ldw.setPropertyValue("height", new Integer(bounds.height));
        fc.executeSetLayoutDataWrapper(ldw);
    }

    /**
     * @param wrapper
     * @param jcm
     * @return
     */
    public static String getJavaCode(LayoutDataWrapper ldw, IJavaCodeManager jcm) {
	    jcm.addImport(CONSTRAINT_NAME);
		
		String code = "new XYConstraints("+
		ldw.getPropertyValue("x")+", "+
		ldw.getPropertyValue("y")+", "+
		ldw.getPropertyValue("width")+", "+
		ldw.getPropertyValue("height")+")";
		return code;
    }

}
