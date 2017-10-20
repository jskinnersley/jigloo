package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.eval.JavaCodeParser;


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

public class CodeGenPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;

    private RadioGroupFieldEditor codeStyleField1 = null;
    private RadioGroupFieldEditor codeStyleField2 = null;

    public CodeGenPage() {
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

        setTitle("Code Generation");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());

        //CODE GEN
        addField(new BooleanFieldEditor(MainPreferencePage.P_USE_IMPORTS,
                "&Use imports instead of qualified names", prefsComp
                        ));
        addField(new BooleanFieldEditor(MainPreferencePage.P_DONT_USE_JAVA6_GROUP_LAYOUT,
                "Always generate org.jdesktop.layout.GroupLayout code", prefsComp
                        ));

        Group stubModelComp = new Group(prefsComp, SWT.NULL);
        stubModelComp.setText("Generate stub models for:");

        addField(new BooleanFieldEditor(MainPreferencePage.P_GENERATE_STUB_MODEL_JCOMBO,
                "JComboBox", stubModelComp));
        addField(new BooleanFieldEditor(MainPreferencePage.P_GENERATE_STUB_MODEL_JLIST,
                "JList", stubModelComp));
        addField(new BooleanFieldEditor(MainPreferencePage.P_GENERATE_STUB_MODEL_JSPINNER,
                "JSpinner", stubModelComp));
        addField(new BooleanFieldEditor(MainPreferencePage.P_GENERATE_STUB_MODEL_JTABLE,
                "JTable", stubModelComp));
        stubModelComp.setLayout(new GridLayout(4, true));

        Label sep = new Label(prefsComp, SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        codeStyleField1 = new RadioGroupFieldEditor(
                MainPreferencePage.P_GETTERS,
                "Generate code for new components:",
                1,
                new String[][] {
                        { "Using getters (VAJ/VEP/JBuilder style)", "0" },
                        {
                                "As code blocks (generally inside initialization method)",
                                "1" },
                        {
                                "Decide based on context (use getters if getters used already)",
                                "2" } }, new Composite(prefsComp
                        , SWT.NULL));
        addField(codeStyleField1);

        sep = new Label(prefsComp, SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        codeStyleField2 = new RadioGroupFieldEditor(
                MainPreferencePage.P_USE_BRACES,
                "Enclose GUI elements with...",
                1,
                new String[][] {
                        { "Braces", "1" },
                        {
                                "Tagged comments ("
                                        + JavaCodeParser.START_BLOCK + ", "
                                        + JavaCodeParser.END_BLOCK + ")", "2" },
                        {
                                "Blank lines (may leave user-defined code behind when cutting/deleting)",
                                "3" } }, prefsComp);
        addField(codeStyleField2);
        
//        RadioGroupFieldEditor eventDefault = new RadioGroupFieldEditor(
//                MainPreferencePage.P_DEFAULT_EVENT_TYPE,
//                "Default event handler type...",
//                1,
//                new String[][] {
//                        { "Inline", "1" },
//                        { "Handler method", "2" },
//                        { "this", "3" } }, prefsComp);
//        addField(eventDefault);

        sep = new Label(prefsComp, SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        addField(new BooleanFieldEditor(MainPreferencePage.P_LOOKANDFEEL,
                "Generate Look and Feel code", prefsComp
                        ));
        
//        addField(new BooleanFieldEditor(MainPreferencePage.P_EXPORT_FORM,
//                "Generate XML .form file (for external use).", prefsComp
//                        ));

        //addField(
        //new BooleanFieldEditor(
        //MainPreferencePage.P_USE_BRACES,
        //"&Use braces to enclose form element code blocks",
        //prefsComp));


        /*
        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        prefsComp.setLayout(gl);
*/
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

    public void propertyChange(PropertyChangeEvent event) {
        try {
            if (event.getSource().equals(codeStyleField1)) {
                if (event.getNewValue().equals("0")) {
                    codeStyleField2.setEnabled(false, prefsComp);
                } else {
                    codeStyleField2.setEnabled(true, prefsComp);
                }
            }
            //setEvalLabel();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.propertyChange(event);
    }

}