package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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

public class ClassCreationPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;
    private Group allowed;
    private Group forbidden;

    public ClassCreationPage() {
        super(GRID);
        setDescription(" ");
        setPreferenceStore(jiglooPlugin.getDefault().getPreferenceStore());
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */

    public void createFieldEditors() {

        GridData gd;
        GridLayout gl = null;

        setTitle("Class Creation");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		{
		    Label label1 = new Label(prefsComp, SWT.WRAP);
			GridData label1LData = new GridData();
			label1LData.widthHint = 419;
			label1.setLayoutData(label1LData);
			label1.setText(
				"This page controls which classes will and will not be instantiated by Jigloo as it "
					+ "parses the java code. Jigloo will normally instantiate classes which extend a Swing" +
							" or SWT visual class, or a class which is used as a property for a visual class." +
							" However, if you wish to restrict this even further then you can add classes to the" +
							" \"Forbidden\" list, or replace the \"*\" in the \"Allowed\" list.");
		}
		{
			allowed = new Group(prefsComp, SWT.NONE);
			allowed.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			allowed.setLayout(new GridLayout());
			allowed.setText("Allowed Superclasses");
		}
		{
			forbidden = new Group(prefsComp, SWT.NONE);
			forbidden.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			forbidden.setLayout(new GridLayout());
			forbidden.setText("Forbidden Superclasses");
		}        

        //CREATION
        addField(new CustomListEditor(MainPreferencePage.P_OK_NV_CLASSES, "",
                allowed, true));
        addField(new CustomListEditor(MainPreferencePage.P_NOT_OK_NV_CLASSES, "",
                forbidden, true));

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}