package com.cloudgarden.jigloo.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
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

public class ParsingPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    private Composite prefsComp;

    public ParsingPage() {
        super(GRID);
//        setDescription("Jigloo parsing preferences");
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

        setTitle("Parsing");
        prefsComp = new Composite(getFieldEditorParent(), SWT.NULL);
		prefsComp.setLayout(new GridLayout());
		
        //PARSING
        addField(new IntegerFieldEditor(MainPreferencePage.P_PARSE_DELAY,
                "Code parse delay (ms)", new Composite(prefsComp, SWT.NULL)));

        addField(new BooleanFieldEditor(MainPreferencePage.P_PARSE_ERRORS,
                "Parse code despite build errors", prefsComp));

        addField(new BooleanFieldEditor(MainPreferencePage.P_TIMEOUTS,
                "Use timeouts when setting properties (can avoid freezing of Eclipse)", prefsComp));

//      addField(new BooleanFieldEditor(P_DONT_PARSE_INITCOMPS,
//      "Do not parse \"initComponents()\" method if \"initGUI()\" method exists", prefsComp
//              ));

        Group initGUIComp = new Group(prefsComp, SWT.NULL);
        initGUIComp.setText("Init GUI methods");
        Label lab = new Label(initGUIComp, SWT.NULL);
        lab
                .setText("Methods which may contain the GUI initialization code.");
        addField(new StringFieldEditor(MainPreferencePage.P_INITGUI_METHODS,
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

        addField(new BooleanFieldEditor(
                        MainPreferencePage.P_PARSE_EXT_SETTERS,
                        "Parse property setters outside of method containing element's assignment",
                        prefsComp));

        addField(new BooleanFieldEditor(
                        MainPreferencePage.P_PARSE_CONS,
                        "Parse constructors and main method before GUI init methods",
                        prefsComp));

        Group protComp = new Group(prefsComp, SWT.NULL);
        protComp.setText("CODE-PROTECT TAGS");
        lab = new Label(protComp, SWT.NULL);
        lab
                .setText("Comment tags which mark code which will not be modified by Jigloo.");
        addField(new StringFieldEditor(MainPreferencePage.P_PROTECT_START_TAG,
                "Start Tag: (marks start of block)", 30, protComp));
        addField(new StringFieldEditor(MainPreferencePage.P_PROTECT_END_TAG,
                "End Tag: (marks end of block)", 30, protComp));
        addField(new StringFieldEditor(MainPreferencePage.P_PROTECT_LINE_TAG,
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

        Group hideComp = new Group(prefsComp, SWT.NULL);
        hideComp.setText("CODE-HIDE TAGS");
        lab = new Label(hideComp, SWT.NULL);
        lab
                .setText("Comment tags which mark code which will not be parsed by Jigloo.");
        addField(new StringFieldEditor(MainPreferencePage.P_HIDE_START_TAG,
                "Start Tag: (marks start of block)", 30, hideComp));
        addField(new StringFieldEditor(MainPreferencePage.P_HIDE_END_TAG,
                "End Tag: (marks end of block)", 30, hideComp));
        addField(new StringFieldEditor(MainPreferencePage.P_HIDE_LINE_TAG,
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

        /*

        gl = new GridLayout(1, false);
        gl.marginWidth = 10;
        gl.marginHeight = 10;
        gl.verticalSpacing = 10;
        prefsComp.setLayout(gl);
*/
    }

    public void init(IWorkbench workbench) {
    }

}