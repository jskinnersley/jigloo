/*
 * Created on Jul 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wizards;

import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.util.FileUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NewSingleFrameAppWizard extends NewFormWizard {

    protected String getTemplateName() {
        return "SampleApplication";
    }
    
    protected String getTemplatePackage() {
        return "appFramework/";
    }

    protected String getFormSuperclassName() {
        return AppUtils.JAVAX_SWING_SFAPP_NAME;
    }

    protected String[] getTemplateNames() {
        return new String[] { 
                "resources/icons/exit.png", 
                "resources/icons/open.png", 
                "resources/icons/save.png", 
                "resources/icons/new.png", 
                "resources/SampleApplication.properties",
                "SampleApplication"};
    }

    protected boolean isSingleFile() {
        return false;
    }
    
    protected void updateProjectClassPath() {
        FileUtils.addJarsToClassPath(getShell(), proj, 
        		new String[] {AppUtils.APPFRAMEWORK_JAR, "jnlp.jar"}, AppUtils.JAVAX_SWING_APP_NAME);
    }

}
