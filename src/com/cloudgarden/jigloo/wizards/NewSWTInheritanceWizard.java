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
public class NewSWTInheritanceWizard extends NewFormWizard {

    protected String getTemplateName() {
        return "ChildComposite";
    }

    protected String getFormSuperclassName() {
        return "AbstractParentComposite";
    }

    protected String[] getTemplateNames() {
        return new String[] { "AbstractParentComposite", "ChildComposite" };
    }

    protected boolean isSingleFile() {
        return false;
    }

    protected boolean isExample() {
        return true;
    }
    protected String getPackageName() {
        return "examples.swt.inheritance";
    }
    protected boolean needsSWRResMan() {
        return true;
    }
    protected String getTemplatePackage() {
        return "swtinheritance/";
    }


}