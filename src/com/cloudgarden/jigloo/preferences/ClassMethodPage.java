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
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage </samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ClassMethodPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;
    private Group allowed;
    private Group forbidden;

    public ClassMethodPage() {
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

        setTitle("Non-setter methods");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		{
		    Label label1 = new Label(prefsComp, SWT.WRAP);
			GridData label1LData = new GridData();
			label1LData.widthHint = 419;
			label1.setLayoutData(label1LData);
			label1.setText(
				"This page controls which classes will and will not have non-setter methods invoked "
					+ "on them as jigloo parses the java code. Jigloo will not normally invoke non-setter methods." +
							" However, if you wish to change this then you can add classes to the" +
							" \"Allowed\" or \"Forbidden\" lists.");
		}
		{
			allowed = new Group(prefsComp, SWT.NONE);
			allowed.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			allowed.setLayout(new GridLayout());
			allowed.setText("\"Allowed\" Superclasses");
		}
		{
			forbidden = new Group(prefsComp, SWT.NONE);
			forbidden.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			forbidden.setLayout(new GridLayout());
			forbidden.setText("\"Forbidden\" Superclasses");
		}        

        //CREATION
        addField(new CustomListEditor(MainPreferencePage.P_OK_NON_SETTER_CLASSES, "",
                allowed, true));
        addField(new CustomListEditor(MainPreferencePage.P_NOT_OK_NON_SETTER_CLASSES, "",
                forbidden, true));

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}