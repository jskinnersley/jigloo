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
public class NewJDialogWizard extends NewFormWizard {

	protected String getTemplateName() {
		return "JDialog";
	}
	protected String getFormSuperclassName() {
		return "javax.swing.JDialog";
	}
    protected String getTemplatePackage() {
        return "swing/";
    }

}
