package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.cloudgarden.jigloo.jiglooPlugin;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage </samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class AppFrameworkPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;

    public AppFrameworkPage() {
        super(GRID);
        setPreferenceStore(jiglooPlugin.getDefault().getPreferenceStore());
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */

    public void createFieldEditors() {

        GridData gd;
        GridLayout gl = new GridLayout();

        setTitle("AppFramework settings");
//        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
//		prefsComp.setLayout(gl);
		CLabel label = new CLabel(getFieldEditorParent(), SWT.NULL);
		label.setText(
				"Enter the project-relative path of a folder which must exist, and which\n" +
				"will be used to store the properties files for any appFramerork\n" +
				"applications.\n\n" +
				"For example: resources, or src/main/resources\n\n" +
				"If no path is specified the properties files will be stored in a resources\n" +
				"folder in the same folder as the Java source.\n");
		gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
        addField(new StringFieldEditor(MainPreferencePage.P_RESOURCE_PATH, 
                "&Properties resource path:",
                getFieldEditorParent()));
    }

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}