/*
 * Created on Oct 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.jface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ApplicationWindowManager {

    public static String translatePropName(String pName) {
        if( pName.equals("addCoolBar"))
            pName = JavaCodeParser.METHOD_PREFIX + "addCoolBar";
        else if( pName.equals("addToolBar"))
            pName = JavaCodeParser.METHOD_PREFIX + "addToolBar";
        return pName;
    }
    
    public static void addSynthProperties(FormComponent fc) {
        fc.addSynthProperty("size", Point.class, new Point(-1,-1));
        fc.addSynthProperty("addCoolBar", Boolean.class, Boolean.FALSE);
        fc.addSynthProperty("addToolBar", Boolean.class, Boolean.FALSE);
        fc.addSynthProperty("addMenuBar", Boolean.class, Boolean.FALSE);
        fc.addSynthProperty("addStatusLine", Boolean.class, Boolean.FALSE);
        fc.addSynthProperty("showTopSeparator", Boolean.class, Boolean.FALSE);
    }
    
    public static String getJavaCodeForProperty(Object id, Object o) {
        if(id.equals("addCoolBar")) {
            if (o == null || o.equals(Boolean.FALSE))
                return "";
            return "\t\taddCoolBar(SWT.FLAT | SWT.WRAP);\n";
        } else if(id.equals("addToolBar")) {
            if (o == null || o.equals(Boolean.FALSE))
                return "";
            return "\t\taddToolBar(SWT.FLAT | SWT.WRAP);\n";
        }
        return "";
    }
    
    public static void initProperties(FormComponent fc) {
        fc.setPropertyValueInternal("size", new Point(400,300), true, false);
        fc.setPropertyValueInternal("addCoolBar", Boolean.TRUE, true, false);
        fc.setPropertyValueInternal("addToolBar", Boolean.FALSE, true, false);
        fc.setPropertyValueInternal("addMenuBar", Boolean.TRUE, true, false);
        fc.setPropertyValueInternal("addStatusLine", Boolean.TRUE, true, false);
        fc.setPropertyValueInternal("showTopSeparator", Boolean.TRUE, true, false);
    }
    
    public static void addSubControls(FormComponent fc) {
        GridLayout gl = new GridLayout(1,true);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.verticalSpacing = 0;
        fc.getLayoutWrapper().setObject(gl);
        
        FormComponent sep = FormComponent.newFormComponent(fc, Label.class.getName(), "separator", false, null);
        sep.setLayoutDataWrapper(
                new LayoutDataWrapper(new GridData(GridData.FILL_HORIZONTAL), sep));
        sep.setExistsInCode(true);
        
        GridData gd;
        FormComponent cb = FormComponent.newFormComponent(fc, CoolBar.class.getName(), "coolBar", false, null);
        cb.setExistsInCode(true);
        cb.setLayoutDataWrapper(
                new LayoutDataWrapper(new GridData(GridData.FILL_HORIZONTAL), cb));
        
        FormComponent tb = FormComponent.newFormComponent(fc, ToolBar.class.getName(), "toolBar", false, null);
        tb.setExistsInCode(false);
        tb.setVisible(false);
        gd= new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 0;
        tb.setLayoutDataWrapper(new LayoutDataWrapper(gd, tb));
        
        FormComponent cont = FormComponent.newFormComponent(fc, 
                OrderableComposite.class.getName(), "contents", false, null);
        cont.setExistsInCode(true);
        cont.setLayoutDataWrapper(
                new LayoutDataWrapper(new GridData(GridData.FILL_BOTH), cont));
        
        FormComponent stat = FormComponent.newFormComponent(fc, 
                OrderableComposite.class.getName(), "statusLine", false, null);
        stat.setExistsInCode(true);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 23;
        stat.setLayoutDataWrapper(new LayoutDataWrapper(gd, stat));
        
        FormComponent ci = FormComponent.newFormComponent(cb, CoolItem.class.getName(), "coolItem1", false, null);
        ci.setExistsInCode(true);
        
        FormComponent tb1 = FormComponent.newFormComponent(ci, ToolBar.class.getName(), "toolBar1", false, null);
        tb1.setExistsInCode(true);
//        
//        FormComponent ti = new FormComponent(tb, ToolItem.class.getName(), "toolItem1");
//        ti.setExistsInCode(true);
        
        sep.setStyle(SWT.SEPARATOR | SWT.HORIZONTAL, true);
    }

    public static void handlePropertySet(Object id, Object value, FormComponent app) {
        if (id.equals("addCoolBar")) {
            handleAddComponent("coolBar", value, app.getEditor());
        } else if (id.equals("addToolBar")) {
            handleAddComponent("toolBar", value, app.getEditor());
        }
    }
    
    private static void handleAddComponent(String compName, Object value, FormEditor editor) {
        FormComponent fc = editor.getComponentByName(compName, true);
        boolean val = ((Boolean)value).booleanValue();
        fc.setVisible(val);
        fc.setExistsInCode(val);
        if(!val) {
            fc.getLayoutDataWrapper().setPropertyValue("heightHint", new Integer(0));
        } else {
            fc.getLayoutDataWrapper().setPropertyValue("heightHint", new Integer(23));                    
        }        
    }
}
