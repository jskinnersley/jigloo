/*
 * Created on Jul 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wizards;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NewSWTDummyEclipseExampleWizard extends NewFormWizard {

    protected String getTemplateName() {
        return "DummyEclipse";
    }

    protected String getFormSuperclassName() {
        return "org.eclipse.swt.widgets.Composite";
    }

    protected String[] getTemplateNames() {
        return new String[] { 
        		"icons/package_obj", 
        		"icons/build_exec",
                "icons/close_view", 
                "icons/debug_exec",
                "icons/emptyBox", 
                "icons/form",
                "icons/jcu_obj",
                "icons/jperspective",
                "icons/new_persp",
                "icons/new_wiz", 
                "icons/package",
                "icons/package_obj", 
                "icons/print_edit",
                "icons/prop_ps",
                "icons/resource_persp",
                "icons/run_exec", 
                "icons/sample",
                "icons/save",
                "icons/save_edit",
                "icons/saveas_edit",
                "icons/stipple", 
                "icons/tickBox",
                "icons/view_menu",
                "DummyEclipse" };
    }

    protected boolean needsSWRResMan() {
        return true;
    }
    protected boolean isSingleFile() {
        return false;
    }

    protected boolean isExample() {
        return true;
    }
    
    protected String getPackageName() {
        return "examples.swt.dummyeclipse";
    }
    protected String getTemplatePackage() {
        return "swtdummyeclipse/";
    }
}