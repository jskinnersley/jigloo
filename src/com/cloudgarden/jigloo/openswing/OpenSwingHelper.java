/*
 * Created on Nov 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.openswing;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.GenericDialog;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OpenSwingHelper {

    private static Class columnClass;
    private static Class gridControlClass;
    
    /**
     * @param clsName
     * @param smName
     * @return
     */
    public static boolean isForbiddenMethod(String clsName, String smName) {
        if(clsName.equals("org.openswing.swing.mdi.client.MDIFrame") && smName.equals("add"))
            return true;
        return false;
    }

    /**
     * @param component
     * @return
     */
    public static boolean showCustomizer(FormComponent fc) {
        try {
            if(!fc.getEditor().canUseOpenSwing())
                return false;
            FormComponent gridFC = null;
            if(isColumn(fc)) {
                gridFC = fc.getParent().getParent();
            } else if(fc.getParent() != null && isGridControl(fc.getParent())) {
                gridFC = fc.getParent();
            } else if(isGridControl(fc)) {
                gridFC = fc;
            } else {
                return false;
            }
            
            if(gridFC == null) {
                System.err.println("openswing helper - grid control is null");
            }
            final FormComponent fgridFC = gridFC;
            GenericDialog ad =
                new GenericDialog(fc.getEditor().getSite().getShell()) {
                public Composite createComposite(Shell dialogShell) {
                    return new GridControlEditor(dialogShell, fgridFC);
                }
            };
            ad.open();
            return true;
        } catch(Throwable t) {
            System.err.println("OpenSwingHelper.showCustomizer "+t);
            return false;
        }
    }

    /**
     * @param component
     * @param size
     * @return
     */
    public static boolean setBounds(FormComponent fc, Point size) {
        if(!fc.getEditor().canUseOpenSwing())
            return false;
        if(isColumn(fc)) {
            fc.setPropertyValueInternal("preferredWidth", new Integer(size.x), true, true);
            return true;
        }
        return false;
    }

    /**
     * @param id
     * @return
     */
    public static boolean ignoreJavaInitialisationString(FormComponent fc, String id) {
        if(!fc.getEditor().canUseOpenSwing())
            return false;
        return id.equals("columnName") && isColumn(fc);
    }

    public static boolean isColumn(FormComponent fc) {
        if(!fc.getEditor().canUseOpenSwing())
            return false;
        if(columnClass == null || !columnClass.getClassLoader().equals(fc.getMainClass().getClassLoader())) {
            columnClass = fc.getEditor().loadClass("org.openswing.swing.table.columns.client.Column");
        }
        if(columnClass == null) {
            return false;
        }
        return fc.isSubclassOf(columnClass);
    }

    public static boolean isGridControl(FormComponent fc) {
        if(!fc.getEditor().canUseOpenSwing())
            return false;
        if(gridControlClass == null || !gridControlClass.getClassLoader().equals(fc.getMainClass().getClassLoader())) {
            gridControlClass = fc.getEditor().loadClass("org.openswing.swing.client.GridControl");
        }
        if(gridControlClass == null) {
            return false;
        }
        return fc.isSubclassOf(gridControlClass);
    }
    
    public static String getDisplayText(FormComponent fc) {
        if(!isColumn(fc))
            return null;
//        String txt = (String) fc.getRawPropertyValue("text");
//        if(!"...".equals(txt))
//            return txt;
        return (String) fc.getRawPropertyValue("columnName");
    }
}
