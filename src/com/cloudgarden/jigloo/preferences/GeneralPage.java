package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;


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

public class GeneralPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;
    private Group appearance;
    private Group behaviour;

    private RadioGroupFieldEditor codeStyleField1 = null;
    private RadioGroupFieldEditor codeStyleField2 = null;

    public GeneralPage() {
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

        setTitle("Appearance and Behaviour");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		{
			appearance = new Group(prefsComp, SWT.NONE);
			appearance.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			appearance.setLayout(new GridLayout());
			appearance.setText("Appearance");
		}
		{
			behaviour = new Group(prefsComp, SWT.NONE);
			behaviour.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
			behaviour.setLayout(new GridLayout());
			behaviour.setText("Behaviour");
		}        

        //GENERAL
        addField(new BooleanFieldEditor(MainPreferencePage.P_NAME_PROMPT,
                "&Prompt for name when adding components", behaviour));
        addField(new BooleanFieldEditor(MainPreferencePage.P_PALETTE,
                "Show Component/Layout palette", behaviour));
        addField(new BooleanFieldEditor(MainPreferencePage.P_CONTEXT_MENU,
                "Show \"&Add...\" options in context menu", behaviour));
        addField(new BooleanFieldEditor(MainPreferencePage.P_DOUBLE_CLICK,
                "&Double-click to change boolean properties", behaviour));
        /*
         * addField( new BooleanFieldEditor( P_IMG_FILES, "&Use relative file
         * name (as opposed to resource URL) for images.",
         * prefsComp.getBehaviourGroup()));
         * 
         * addField( new BooleanFieldEditor( P_NETBEANS_CMNT, "Delete Netbeans
         * comments on import.", prefsComp.getBehaviourGroup()));
         */
        addField(new RadioGroupFieldEditor(
                        MainPreferencePage.P_EDITOR_LAYOUT,
                        "Form/Source Editor layout (only effective after editor reopened)",
                        1, new String[][] { { "Tabbed panels", FormEditor.EDITOR_MODE_TABBED+"" },
                                { "Split-pane (vertical)", FormEditor.EDITOR_MODE_SPLIT_VERT+"" },
                                { "Split-pane (horizontal)", FormEditor.EDITOR_MODE_SPLIT_HORIZ+"" } },
                        new Composite(appearance, SWT.NULL)));

        addField(new ColorFieldEditor(MainPreferencePage.P_BGCOL, "Background color",
                new Composite(appearance, SWT.NULL)));

        addField(new BooleanFieldEditor(MainPreferencePage.P_PROPERTY_CATEGORIES_ENABLED,
                "Enable property categories (requires editor restart)", appearance));
        
        addField(new BooleanFieldEditor(MainPreferencePage.P_ADVANCED_PROPS,
                "Show advanced properties in separate tab", appearance));
        
        Composite comp;
        comp = new Composite(appearance, SWT.NULL);
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addField(new IntegerFieldEditor(MainPreferencePage.P_MAX_WIDTH,
                "Max form width (pixels)", comp));
        
        comp = new Composite(appearance, SWT.NULL);
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addField(new IntegerFieldEditor(MainPreferencePage.P_MAX_HEIGHT,
                "Max form height (pixels)", comp));
        
        comp = new Composite(appearance, SWT.NULL);
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addField(new IntegerFieldEditor(MainPreferencePage.P_GRID_SIZE,
                "Snap-grid size (pixels)", comp));
        
        comp = new Composite(appearance, SWT.NULL);
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addField(new StringFieldEditor(MainPreferencePage.P_DEFAULT_GRID_SIZES,
                "Grid-type layout default sizes", comp));
        
//        addField(new BooleanFieldEditor(MainPreferencePage.P_GRID_SHOW_AUTO,
//                "Show grid automatically, based on selected layout", appearance));
        
        /*
        //PARSING
        addField(new IntegerFieldEditor(PreferencePage.P_PARSE_DELAY,
                "Code parse delay (ms)", new Composite(prefsComp
                        .getCodeParseGroup(), SWT.NULL)));

        addField(new BooleanFieldEditor(PreferencePage.P_PARSE_ERRORS,
                "Parse code despite build errors", prefsComp
                        .getCodeParseGroup()));

//      addField(new BooleanFieldEditor(P_DONT_PARSE_INITCOMPS,
//      "Do not parse \"initComponents()\" method if \"initGUI()\" method exists", prefsComp
//              .getCodeParseGroup()));

        Group initGUIComp = new Group(prefsComp.getCodeParseGroup(), SWT.NULL);
        initGUIComp.setText("Init GUI methods");
        Label lab = new Label(initGUIComp, SWT.NULL);
        lab
                .setText("Methods which may contain the GUI initialization code.");
        addField(new StringFieldEditor(PreferencePage.P_INITGUI_METHODS,
                "Names:", 50,  initGUIComp));
        gl = new GridLayout(2, false);
        gl.marginWidth = 5;
        gl.marginHeight = 5;
        initGUIComp.setLayout(gl);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        initGUIComp.setLayoutData(gd);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        lab.setLayoutData(gd);
        
//        addField(new BooleanFieldEditor(P_PARSE_EVERYTHING,
//      "Parse all methods (i.e. not just the \"Init GUI\" methods defined above)", initGUIComp));

        addField(
                1,
                new BooleanFieldEditor(
                        PreferencePage.P_PARSE_EXT_SETTERS,
                        "Parse property setters outside of method containing element's assignment",
                        prefsComp.getCodeParseGroup()));

        addField(
                1,
                new BooleanFieldEditor(
                        PreferencePage.P_PARSE_CONS,
                        "Parse constructors and main method before GUI init methods",
                        prefsComp.getCodeParseGroup()));

        Group protComp = new Group(prefsComp.getCodeParseGroup(), SWT.NULL);
        protComp.setText("CODE-PROTECT TAGS");
        lab = new Label(protComp, SWT.NULL);
        lab
                .setText("Comment tags which mark code which will not be modified by Jigloo.");
        addField(new StringFieldEditor(PreferencePage.P_PROTECT_START_TAG,
                "Start Tag: (marks start of block)", 30, protComp));
        addField(new StringFieldEditor(PreferencePage.P_PROTECT_END_TAG,
                "End Tag: (marks end of block)", 30, protComp));
        addField(new StringFieldEditor(PreferencePage.P_PROTECT_LINE_TAG,
                "Line Tag: (marks line)", 30, protComp));
        gl = new GridLayout(2, false);
        gl.marginWidth = 5;
        gl.marginHeight = 5;
        protComp.setLayout(gl);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        protComp.setLayoutData(gd);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        lab.setLayoutData(gd);

        Group hideComp = new Group(prefsComp.getCodeParseGroup(), SWT.NULL);
        hideComp.setText("CODE-HIDE TAGS");
        lab = new Label(hideComp, SWT.NULL);
        lab
                .setText("Comment tags which mark code which will not be parsed by Jigloo.");
        addField(new StringFieldEditor(PreferencePage.P_HIDE_START_TAG,
                "Start Tag: (marks start of block)", 30, hideComp));
        addField(new StringFieldEditor(PreferencePage.P_HIDE_END_TAG,
                "End Tag: (marks end of block)", 30, hideComp));
        addField(new StringFieldEditor(PreferencePage.P_HIDE_LINE_TAG,
                "Line Tag: (marks line)", 30, hideComp));
        gl = new GridLayout(2, false);
        gl.marginWidth = 5;
        gl.marginHeight = 5;
        hideComp.setLayout(gl);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        hideComp.setLayoutData(gd);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        lab.setLayoutData(gd);

        //CODE GEN
        addField(new BooleanFieldEditor(PreferencePage.P_USE_IMPORTS,
                "&Use imports instead of qualified names", prefsComp
                        .getCodeGenGroup()));

        Group stubModelComp = new Group(prefsComp.getCodeGenGroup(), SWT.NULL);
        stubModelComp.setText("Generate stub models for:");

        addField(new BooleanFieldEditor(PreferencePage.P_GENERATE_STUB_MODEL_JCOMBO,
                "JComboBox", stubModelComp));
        addField(new BooleanFieldEditor(PreferencePage.P_GENERATE_STUB_MODEL_JLIST,
                "JList", stubModelComp));
        addField(new BooleanFieldEditor(PreferencePage.P_GENERATE_STUB_MODEL_JSPINNER,
                "JSpinner", stubModelComp));
        addField(new BooleanFieldEditor(PreferencePage.P_GENERATE_STUB_MODEL_JTABLE,
                "JTable", stubModelComp));
        stubModelComp.setLayout(new GridLayout(4, true));

        Label sep = new Label(prefsComp.getCodeGenGroup(), SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        codeStyleField1 = new RadioGroupFieldEditor(
                PreferencePage.P_GETTERS,
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
                        .getCodeGenGroup(), SWT.NULL));
        addField(codeStyleField1);

        sep = new Label(prefsComp.getCodeGenGroup(), SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        codeStyleField2 = new RadioGroupFieldEditor(
                PreferencePage.P_USE_BRACES,
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
                                "3" } }, prefsComp.getCodeGenGroup());
        addField(codeStyleField2);

        sep = new Label(prefsComp.getCodeGenGroup(), SWT.SEPARATOR
                | SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        sep.setLayoutData(gd);

        addField(new BooleanFieldEditor(PreferencePage.P_LOOKANDFEEL,
                "Generate Look and Feel code", prefsComp
                        .getCodeGenGroup()));
        
        addField(new BooleanFieldEditor(PreferencePage.P_EXPORT_FORM,
                "Generate XML .form file (for external use).", prefsComp
                        .getCodeGenGroup()));

        //addField(
        //new BooleanFieldEditor(
        //PreferencePage.P_USE_BRACES,
        //"&Use braces to enclose form element code blocks",
        //prefsComp.getCodeGenGroup()));

        //CUSTOM
        addField(new CustomListEditor(PreferencePage.P_SWING_CUSTOMS, "", new Composite(
                prefsComp.getSwingCustomGroup(), SWT.NULL), false));
        addField(new CustomListEditor(PreferencePage.P_SWT_CUSTOMS, "", new Composite(
                prefsComp.getSWTCustomGroup(), SWT.NULL), false));

        //CREATION
        addField(new CustomListEditor(PreferencePage.P_OK_NV_CLASSES, "",
                prefsComp.allowedClassGroup, true));
        addField(new CustomListEditor(PreferencePage.P_NOT_OK_NV_CLASSES, "",
                prefsComp.forbiddenClassGroup, true));

        //LICENSING
        licLabel = new Label(prefsComp.getLicenseGroup(), SWT.NULL);
        licLabel.setAlignment(SWT.LEFT);
        gd = new GridData(GridData.FILL_BOTH);
        licLabel.setLayoutData(gd);
        addField(new StringFieldEditor(PreferencePage.P_LICENSE_CODE, "&License Code:",
                prefsComp.getLicenseGroup()));

        setEvalLabel();

        //addField(
        //new StringFieldEditor(
        //PreferencePage.P_EMAIL,
        //"&Email:",
        //prefsComp.getLicenseGroup()));

//        
//          addField( new DirectoryFieldEditor( PreferencePage.P_PATH, "&Directory preference:",
//          getFieldEditorParent())); addField(new RadioGroupFieldEditor(
//          PreferencePage.P_CHOICE, "An example of a multiple-choice preference", 1, new
//          String[][] { { "&Choice 1", "choice1" }, { "C&hoice 2", "choice2" } },
//          getFieldEditorParent())); addField( new StringFieldEditor( PreferencePage.P_STRING,
//          "A &text preference:", getFieldEditorParent()));
*/
        //need to re-set layouts after addField calls
        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        appearance.setLayout(gl);

        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        behaviour.setLayout(gl);

        /*
        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        prefsComp.getCodeGenGroup().setLayout(gl);

        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        prefsComp.getCodeParseGroup().setLayout(gl);

        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        prefsComp.getLicenseGroup().setLayout(gl);
*/
    }

    private Label licLabel;

    private void setEvalLabel() {
        if(licLabel == null)
            return;
        boolean valid = jiglooPlugin.getDefault().licenseValid();
        //CLabel label1 = prefsComp.getLabel1();
        //CLabel label2 = prefsComp.getLabel2();
        if (valid) {
            licLabel.setText("\n\nValid professional license code.\n\n"
                    + "Thank you for supporting CloudGarden.\n\n\n\n\n\n\n");
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
    
/*
    public void propertyChange(PropertyChangeEvent event) {
        try {
            if (event.getSource().equals(codeStyleField1)) {
                if (event.getNewValue().equals("0")) {
                    codeStyleField2.setEnabled(false, prefsComp
                            .getCodeGenGroup());
                } else {
                    codeStyleField2.setEnabled(true, prefsComp
                            .getCodeGenGroup());
                }
            }
            //setEvalLabel();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.propertyChange(event);
    }
*/
    protected void adjustGridLayout() {
        //super.adjustGridLayout();
    }

}