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
public class NewEditorPartWizard extends NewFormWizard {

	protected String getTemplateName() {
		return "EditorPart";
	}
    protected String getTemplatePackage() {
        return "rcp/";
    }
	protected String getFormSuperclassName() {
		return "org.eclipse.ui.part.EditorPart";
	}
	protected boolean addAbstractMethods() {
	    return false;
	}
}
