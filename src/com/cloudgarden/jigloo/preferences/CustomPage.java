package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
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

public class CustomPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;
    private Group swtCustom;
    private Group swingCustom;

    private RadioGroupFieldEditor codeStyleField1 = null;
    private RadioGroupFieldEditor codeStyleField2 = null;

    public CustomPage() {
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

        setTitle("Component palettes and custom classes (beans)");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		prefsComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			Label label = new Label(prefsComp, SWT.NONE);
            label.setText("Add, move (drag to re-arrange) or delete palettes or beans");
		}

        addField(new PaletteEditor(MainPreferencePage.P_PALETTE_CLASSES,
                prefsComp));

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }
}