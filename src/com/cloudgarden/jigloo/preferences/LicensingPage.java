package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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

public class LicensingPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;

    public LicensingPage() {
        super(GRID);
//        setDescription("Jigloo general preferences");
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

        setTitle("Licensing Jigloo");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		
        //LICENSING
        licLabel = new Label(prefsComp, SWT.NULL);
        licLabel.setAlignment(SWT.LEFT);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        licLabel.setLayoutData(gd);
        
//        Label lab = new Label(prefsComp, SWT.NULL);
//        lab.setAlignment(SWT.LEFT);
//        gd = new GridData(GridData.FILL_BOTH);
//        gd.horizontalSpan = 2;
//        lab.setLayoutData(gd);
//        lab.setText("Before you can start using Jigloo you need to obtain an activation" +
//        		" key by entering a valid email address (which will not be shared in any way)");
//        addField(new StringFieldEditor(MainPreferencePage.P_EMAIL,
//                "&Email:",
//                prefsComp));
//        Button b = new Button(prefsComp, SWT.NULL);
//        b.setText("Get Activation Key");
        addField(new StringFieldEditor(MainPreferencePage.P_LICENSE_CODE, 
                "&License Code:",
                prefsComp));
        
        setEvalLabel();

    }

    private Label licLabel;

    private void setEvalLabel() {
        if(licLabel == null)
            return;
        boolean valid = jiglooPlugin.getDefault().licenseValid();
        //CLabel label1 = prefsComp.getLabel1();
        //CLabel label2 = prefsComp.getLabel2();
        if (valid) {
            licLabel.setText("\n\nValid professional license code.\n\n\n\n\n\n\n");
            //label2.setText("");
        } else {
            licLabel
                    .setText("\n\nNO VALID PROFESSIONAL LICENSE.\n\n\n"
                            + "JIGLOO CANNOT BE USED COMMERCIALLY !!!\n\n\n"
                            + "If you are using Jigloo in the course of your work for a business\n"
                            + "or company, then a professional license must be purchased for\n"
                            + "each developer using Jigloo.\n\n\n"
                            + "To purchase licenses, please visit www.cloudgarden.com");
            //long left = jiglooPlugin.getDefault().msLeft();
            //				if (left < 0) {
            //					label1.setText("Professional feature evalation expired.");
            //					label2.setText("Visit www.cloudgarden.com to purchase a
            // license");
            //				} else {
            //	DecimalFormat decFmt = new DecimalFormat("0.0");
            //label1.setText(
            //"Days left for evalation of professional features: " +
            // decFmt.format(left / (24.0 * 3600000)));
            //label2.setText("Number of Netbeans/Sun One files converted: " +
            // jiglooPlugin.getDefault().numNbUsed());
            //}
        }
        licLabel.pack(true);
        //label2.pack(true);
    }

    public void init(IWorkbench workbench) {
    }

    public boolean performOk() {
        boolean val = super.performOk();
        setEvalLabel();
        return val;
    }
    
}