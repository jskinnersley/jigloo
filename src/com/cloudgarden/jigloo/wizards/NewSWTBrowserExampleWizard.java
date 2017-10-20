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
public class NewSWTBrowserExampleWizard extends NewFormWizard {

    protected String getTemplateName() {
        return "SWTBrowserExample";
    }

    protected String getFormSuperclassName() {
        return "org.eclipse.swt.widgets.Composite";
    }

    protected boolean isSingleFile() {
        return true;
    }

    protected boolean isExample() {
        return true;
    }
    protected String getPackageName() {
        return "examples.swt.browser";
    }
    protected String getTemplatePackage() {
        return "swtbrowser/";
    }
}