/*
 * Created on Oct 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.layoutHandler;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.eval.JavaCodeParser;

import de.gebit.s2j.smalltalk.gui.EnfinLayout;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnfinLayoutHandler {

    private static final String LAYOUT_CLASS_NAME = "de.gebit.s2j.smalltalk.gui.EnfinLayout";

	public static String name = "alignment";
    
    public static String[] optionNames = {
            "Static",
            "HMOVE",
            "HMOVE_VMOVE",
            "HMOVE_VRESIZE",
            "HRESIZE",
            "HRESIZE_VMOVE",
            "HRESIZE_VRESIZE",
            "VMOVE",
    		"VRESIZE"};
    
    public static Short[] options;
    static {
    	try {
    		options = new Short[] {
    				new Short((short)0),
    				(Short) getLayoutClass().getField("HMOVE").get(null),
    				(Short) getLayoutClass().getField("HMOVE_VMOVE").get(null),
    				(Short) getLayoutClass().getField("HMOVE_VRESIZE").get(null),
    				(Short) getLayoutClass().getField("HRESIZE").get(null),
    				(Short) getLayoutClass().getField("HRESIZE_VMOVE").get(null),
    				(Short) getLayoutClass().getField("HRESIZE_VRESIZE").get(null),
    				(Short) getLayoutClass().getField("VMOVE").get(null),
    				(Short) getLayoutClass().getField("VRESIZE").get(null)
    		};
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    }
    
    /**
     * @param layoutConstraint
     * @return
     */
    public static String getJavaString(String layoutConstraint, JavaCodeParser jcp) {
        String code = null;
        try {
            for (int i = 0; i < optionNames.length; i++) {
                if(layoutConstraint == null || layoutConstraint.equals("Static")) {
                    code = "new Short(EnfinLayout.Static)";
                    break;
                } else if(layoutConstraint.equals(optionNames[i])) {
                    code = "EnfinLayout."+optionNames[i];
                    break;
                }
            }
            if(code != null)
                jcp.addImport(LAYOUT_CLASS_NAME);
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return code;
    }

    public static Object toFieldName(Object constraint) {
        if(constraint == null)
            return "Static";
        for (int i = 0; i < options.length; i++) {
            if(options[i].toString().equals(constraint.toString()))
                return optionNames[i];
        }
        return constraint;
    }

    public static Object toFieldValue(String constraint) {
        if(constraint == null)
            return null;
        for (int i = 0; i < optionNames.length; i++) {
            if(optionNames[i].equals(constraint))
                return options[i];
        }
        return options[0];
    }

    private static Class enfinClass = null;
    
	public static Class getLayoutClass() {
		if(enfinClass == null)
			enfinClass = jiglooPlugin.getActiveEditor().loadClass(LAYOUT_CLASS_NAME);
		return enfinClass;
	}

}
