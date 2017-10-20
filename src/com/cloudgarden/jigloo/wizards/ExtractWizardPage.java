package com.cloudgarden.jigloo.wizards;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Button;

public class ExtractWizardPage extends NewFormWizardPage {

	public ExtractWizardPage(StructuredSelection selection,
			String templateName, String capitalize, Class mainClass,
			String packageName) {
		super(selection, templateName, capitalize, mainClass, packageName);
	}
	
    protected void initExtraButtons() {
        final Button replaceWithClassButton = getWizardComposite().getExtraButton1();
        final Button addAsCustomClassButton = getWizardComposite().getExtraButton2();

        replaceWithClassButton.setVisible(true);
        replaceWithClassButton.setText("Replace original element with instance of extracted class");
        replaceWithClassButton.setSelection(true);
//        replaceWithClassButton.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//                jiglooPlugin.getDefault().setCreateMain(replaceWithClassButton.getSelection());
//            }
//        });

        addAsCustomClassButton.setVisible(true);
        addAsCustomClassButton.setText("Add extracted class to custom component palette");
        addAsCustomClassButton.setSelection(true);
        
//        addAsCustomClassButton.setSelection(jiglooPlugin.getDefault().getCreateShowGUI());
//        addAsCustomClassButton.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//                jiglooPlugin.getDefault().setCreateShowGUI(
//                        addAsCustomClassButton.getSelection());
//                if (!addAsCustomClassButton.getSelection()) {
//                    replaceWithClassButton.setSelection(false);
//                    jiglooPlugin.getDefault().setCreateMain(false);
//                }
//            }
//        });

	}


}
