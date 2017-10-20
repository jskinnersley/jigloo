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
public class NewInheritanceExampleWizard extends NewFormWizard {

    protected String getTemplateName() {
        return "FlowerShop";
    }

    protected String getFormSuperclassName() {
        return "ShopFrame";
    }

    protected String[] getTemplateNames() {
        return new String[] { "icons/flower_panel", "FlowerPanel", "ShopFrame",
                "FlowerPanelBeanInfo", "FlowerPanelCustomizer",
                "FlowerFamilyEditor", "FlowerShop" };
    }

    protected boolean isSingleFile() {
        return false;
    }

    protected boolean isExample() {
        return true;
    }
    protected String getPackageName() {
        return "examples.swing.flowershop";
    }
    protected String getTemplatePackage() {
        return "swingflowershop/";
    }

}