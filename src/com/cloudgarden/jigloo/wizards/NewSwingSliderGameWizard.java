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
public class NewSwingSliderGameWizard extends NewFormWizard {

	protected String getTemplateName() {
		return "SliderGame";
	}

	protected String getFormSuperclassName() {
		return "javax.swing.JFrame";
	}

	protected boolean isSingleFile() {
		return true;
	}
	protected boolean isExample() {
		return true;
	}
    protected String getPackageName() {
        return "examples.swing.slidergame";
    }
    protected String getTemplatePackage() {
        return "swingslidergame/";
    }

}
