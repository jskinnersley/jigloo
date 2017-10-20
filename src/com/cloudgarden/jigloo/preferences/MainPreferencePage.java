package com.cloudgarden.jigloo.preferences;

import java.util.Vector;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.harness.HarnessManager;
import com.cloudgarden.jigloo.harness.PaletteHelper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

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

public class MainPreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {
    public static final String P_PATH = "pathPreference";
    public static final String P_SASH_WEIGHT = "sashWeight";
    //public static final String P_FORMAT = "formatPreference";
    public static final String P_EDITOR_LAYOUT = "layoutPreference";
    public static final String P_COMMENTS = "commentPreference";
    public static final String P_SYNC = "syncPreference";
    public static final String P_NAME_PROMPT = "namePromptPreference";
    public static final String P_IMG_FILES = "imgFilePreference";
    public static final String P_PALETTE = "palettePreference";
    public static final String P_CONTEXT_MENU = "ctxtMenuPreference";
    public static final String P_DOUBLE_CLICK = "booleanDCPreference";
    public static final String P_NETBEANS_CMNT = "nbCmntPreference";
    public static final String P_TIMEOUTS = "useTimeouts";
    public static final String P_USE_BRACES = "useBraces";
    //    public static final String P_DEFAULT_EVENT_TYPE = "defaultEventType";
    public static final String P_LAF_CLASSES = "lafClasses";
    public static final String P_EXPORT_FORM = "exportForm";
    public static final String P_INITGUI_METHODS = "initGUIMethods";
    public static final String P_PARSE_EXT_SETTERS = "parseExternalSetters";
    public static final String P_DONT_PARSE_INITCOMPS = "parseInitComponents";
    public static final String P_PARSE_EVERYTHING = "parseEverything";
    public static final String P_PARSE_CONS = "parseConsFirst";
    public static final String P_ADVANCED_PROPS = "advancedProps";
    public static final String P_MAX_WIDTH = "maxWidth";
    public static final String P_MAX_HEIGHT = "maxHeight";
    public static final String P_OK_NON_SETTER_CLASSES = "okNonSetterClasses";
    public static final String P_NOT_OK_NON_SETTER_CLASSES = "notOkNonSetterClasses";

    public static final String P_PROTECT_START_TAG = "protectStartTag";
    public static final String P_PROTECT_END_TAG = "protectEndTag";
    public static final String P_PROTECT_LINE_TAG = "protectLineTag";

    public static final String P_HIDE_START_TAG = "hideStartTag";
    public static final String P_HIDE_END_TAG = "hideEndTag";
    public static final String P_HIDE_LINE_TAG = "hideLineTag";

    public static final String P_ADD_SWT_LIBS = "addSwtLibs";
    public static final String P_SWING_CUSTOMS = "swingCustomClasses";
    public static final String P_SWT_CUSTOMS = "swtCustomClasses";
    public static final String P_OK_NV_CLASSES = "okNonVisualClasses";
    public static final String P_NOT_OK_NV_CLASSES = "notOkNonVisualClasses";

    public static final String P_PARSE_DELAY = "parseDelay";
    public static final String P_USE_IMPORTS = "useImports";
    public static final String P_PARSE_ERRORS = "parseErrors";
    public static final String P_GETTERS = "getters";
    public static final String P_LOOKANDFEEL = "lookandfeel";

    public static final String P_GENERATE_STUB_MODEL_JLIST = "stubModelJList";
    public static final String P_GENERATE_STUB_MODEL_JTABLE = "stubModelJTable";
    public static final String P_GENERATE_STUB_MODEL_JCOMBO = "stubModelJCombo";
    public static final String P_GENERATE_STUB_MODEL_JSPINNER = "stubModelJSpinner";
    public static final String P_DONT_USE_JAVA6_GROUP_LAYOUT = "dontUseJava6GroupLayout";

    public static final String P_CHOICE = "choicePreference";
    public static final String P_BGCOL = "bgColorPreference";
    public static final String P_FRAMECOL = "frameColorPreference";
    public static final String P_PALETTE_CLASSES = "paletteClasses";

    public static final String P_PROPERTY_CATEGORIES = "propertyCategories";
    public static final String P_PROPERTY_CATEGORIES_ENABLED = "propertyCategoriesEnabled";
    public static final String P_PROP_CATS_EXPANDED = "propertyCategoriesExpanded";
    public static final String P_PROP_CATS_EXPERT = "propertyExpertCategory";

    public static final String P_GRID_SIZE = "gridSize";
    public static final String P_DEFAULT_GRID_SIZES = "defaultGridSizes";
//    public static final String P_GRID_SHOW_AUTO = "gridShowAuto";

    public static final String P_LICENSE_CODE = "licenseCode";
    public static final String P_EMAIL = "email";

    public static final String P_VERSION = "version";
	public static final String P_BEAN_ICON_FOLDER = "beanIconFolder";
	public static final String P_BEAN_INFO_FOLDER = "beanInfoFolder";
	public static final String P_RESOURCE_PATH = "resourcePath";

    private IPreferenceStore pstore;

    public MainPreferencePage() {
        super(GRID);
        pstore = jiglooPlugin.getDefault().getPreferenceStore();
        setPreferenceStore(pstore);
        setDescription("Jigloo GUI Builder");
    }

    private static boolean inited = false;

    public static void init(IPreferenceStore pstore) {
        if (inited)
            return;
        inited = true;
        pstore.setDefault(MainPreferencePage.P_RESOURCE_PATH, "");
        pstore.setDefault(MainPreferencePage.P_BEAN_ICON_FOLDER, "");
        pstore.setDefault(MainPreferencePage.P_BEAN_INFO_FOLDER, "");
        
        pstore.setDefault(MainPreferencePage.P_BGCOL, "222,222,255");
        pstore.setDefault(MainPreferencePage.P_EDITOR_LAYOUT, "2");
        pstore.setDefault(MainPreferencePage.P_PARSE_DELAY, "2000");
        pstore.setDefault(MainPreferencePage.P_PARSE_ERRORS, true);
        pstore.setDefault(MainPreferencePage.P_ADVANCED_PROPS, false);
        pstore.setDefault(MainPreferencePage.P_DONT_PARSE_INITCOMPS, true);
        pstore.setDefault(MainPreferencePage.P_PARSE_EVERYTHING, false);
        pstore.setDefault(MainPreferencePage.P_GETTERS, "2");
        pstore.setDefault(MainPreferencePage.P_LOOKANDFEEL, true);
        pstore.setDefault(MainPreferencePage.P_ADD_SWT_LIBS, true);
        pstore
                .setDefault(MainPreferencePage.P_GENERATE_STUB_MODEL_JCOMBO,
                        true);
        pstore
                .setDefault(MainPreferencePage.P_GENERATE_STUB_MODEL_JTABLE,
                        true);
        pstore.setDefault(MainPreferencePage.P_GENERATE_STUB_MODEL_JLIST, true);
        pstore.setDefault(MainPreferencePage.P_GENERATE_STUB_MODEL_JSPINNER,
                true);
        pstore.setDefault(MainPreferencePage.P_DONT_USE_JAVA6_GROUP_LAYOUT, false);
        pstore.setDefault(MainPreferencePage.P_USE_IMPORTS, true);
        //pstore.setDefault(PreferencePage.P_FORMAT, true);
        pstore.setDefault(MainPreferencePage.P_SYNC, true);
        pstore.setDefault(MainPreferencePage.P_NAME_PROMPT, true);
        pstore.setDefault(MainPreferencePage.P_PALETTE, true);
        pstore.setDefault(MainPreferencePage.P_CONTEXT_MENU, true);
        pstore.setDefault(MainPreferencePage.P_DOUBLE_CLICK, false);
        pstore.setDefault(MainPreferencePage.P_IMG_FILES, false);
        pstore.setDefault(MainPreferencePage.P_COMMENTS, false);
        pstore.setDefault(MainPreferencePage.P_EXPORT_FORM, false);
        pstore.setDefault(MainPreferencePage.P_NETBEANS_CMNT, false);
        pstore.setDefault(MainPreferencePage.P_TIMEOUTS, false);
        pstore.setDefault(MainPreferencePage.P_PARSE_EXT_SETTERS, false);
        pstore.setDefault(MainPreferencePage.P_PARSE_CONS, true);
        pstore.setDefault(MainPreferencePage.P_MAX_HEIGHT, "4000");
        pstore.setDefault(MainPreferencePage.P_MAX_WIDTH, "3000");
        pstore.setDefault(MainPreferencePage.P_INITGUI_METHODS,
                "initGUI;initComponents;initialize;jbInit;open;createPartControl;"
                        + "createContents;createControl;createDialogArea");
        pstore.setDefault(MainPreferencePage.P_USE_BRACES, "1");
        pstore.setDefault(MainPreferencePage.P_PROTECT_START_TAG,
                "//$protect>>$");
        pstore
                .setDefault(MainPreferencePage.P_PROTECT_END_TAG,
                        "//$protect<<$");
        pstore.setDefault(MainPreferencePage.P_PROTECT_LINE_TAG, "//$protect$");
        pstore.setDefault(MainPreferencePage.P_HIDE_START_TAG, "//$hide>>$");
        pstore.setDefault(MainPreferencePage.P_HIDE_END_TAG, "//$hide<<$");
        pstore.setDefault(MainPreferencePage.P_HIDE_LINE_TAG, "//$hide$");
        pstore.setDefault(MainPreferencePage.P_OK_NON_SETTER_CLASSES, "");
        pstore.setDefault(MainPreferencePage.P_NOT_OK_NON_SETTER_CLASSES, "");
        pstore.setDefault(MainPreferencePage.P_OK_NV_CLASSES, "*");
        //;java.awt.*;javax.swing.*;java.util.*;java.beans.*;org.eclipse.swt.*
        pstore.setDefault(MainPreferencePage.P_NOT_OK_NV_CLASSES, "");

//        pstore.setDefault(MainPreferencePage.P_GRID_SHOW_AUTO, true);
        pstore.setDefault(MainPreferencePage.P_GRID_SIZE, "7");
        pstore.setDefault(MainPreferencePage.P_DEFAULT_GRID_SIZES, "3, 6, 10, 15, 50");
        pstore.setDefault(MainPreferencePage.P_SASH_WEIGHT, "50");
        
        String codedPropCats = "Basic|" + "action|"+ "alignment|" + "autoResizeMode|"
                + "background|" + "border|" + "checked|" + "editable|"
                + "enabled|" + "expanded|" + "font|" + "foreground|"+ "focusTraversalPolicy|"
                + "grayed|" + "icon|" + "image|" + "locale|" + "maximum|"
                + "minimum|" + "model|" + "opaque|" + "orientation|"
                + "preferredSize|" + "showHorizontalLines|" + "title|"+ "iconImage|"
                + "showVerticalLines|" + "size|" + "string|" + "stringPainted|"
                + "tabTitle|" + "text|" + "toolTipText|" + "value"
                + "#"
                + "Hidden|" + "rootPane|" + "glassPane|" + "contentPane|"
                + "componentOrientation|" 
                + "layeredPane|" + "";
        pstore.setDefault(MainPreferencePage.P_PROPERTY_CATEGORIES,
                codedPropCats);
        
        pstore.setDefault(MainPreferencePage.P_PROP_CATS_EXPANDED, "Basic");

        pstore.setDefault(MainPreferencePage.P_PROP_CATS_EXPERT, "Expert");
        pstore.setDefault(MainPreferencePage.P_PROPERTY_CATEGORIES_ENABLED, true);

        pstore
                .setDefault(
                        MainPreferencePage.P_LAF_CLASSES,
                        "com.jgoodies.looks.plastic.Plastic3DLookAndFeel~!JGoodies Plastic 3D~!null~!Y;"
                                + "com.jgoodies.looks.plastic.PlasticLookAndFeel~!JGoodies Plastic~!null~!N;"
                                + "com.jgoodies.looks.plastic.PlasticXPLookAndFeel~!JGoodies Plastic XP~!null~!N");

        try {

        	String codedPalette = 
        		"Containers|Components|More Components|Menu|Custom|Layout"
        		+PaletteComposite.PREF_SEP_2 + "Containers|Controls|Items|Menu|JFace|Custom|Layout";
        	
        	if(HarnessManager.ENABLE_ANDROID)
        		codedPalette += PaletteComposite.PREF_SEP_2+PaletteHelper.getPaletteCategories();

        	codedPalette += PaletteComposite.PREF_SEP_3;

            codedPalette += "Custom|2|||Add custom class or layout...|n|"+PaletteComposite.PREF_SEP_2
                    +"Controls|2|org.eclipse.swt.widgets.Button|"
                    + (SWT.PUSH | SWT.CENTER)
                    + "|Button|n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Button|"
                    + (SWT.RADIO | SWT.LEFT)
                    + "|RadioButton|n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Button|"
                    + (SWT.CHECK | SWT.LEFT)
                    + "|CheckBox|n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Button|"
                    + (SWT.TOGGLE | SWT.CENTER)
                    + "|Toggled Button|n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Combo|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.custom.CCombo|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Label|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.custom.CLabel|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Text|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Text|"
                    + (SWT.MULTI | SWT.WRAP)
                    + "|Text Area|n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.custom.StyledText|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.browser.Browser|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.custom.CBanner|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Link|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.List|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.ProgressBar|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Scale|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Slider|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Controls|2|org.eclipse.swt.widgets.Canvas|0||n|"+PaletteComposite.PREF_SEP_2
                    +

                    "JFace|2|org.eclipse.jface.viewers.ListViewer|0||n|"+PaletteComposite.PREF_SEP_2
                    + "JFace|2|org.eclipse.jface.viewers.TreeViewer|0||n|"+PaletteComposite.PREF_SEP_2
                    + "JFace|2|org.eclipse.jface.viewers.TableViewer|0||n|"+PaletteComposite.PREF_SEP_2
                    + "JFace|2|org.eclipse.jface.viewers.TableTreeViewer|0||n|"+PaletteComposite.PREF_SEP_2
                    + "JFace|2|org.eclipse.jface.dialogs.ProgressIndicator|0||n|"+PaletteComposite.PREF_SEP_2
                    +

                    "Containers|2|org.eclipse.swt.widgets.Composite|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.Group|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.custom.ScrolledComposite|"
                    + (SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER)
                    + "|ScrolledComposite|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.custom.SashForm|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.custom.CTabFolder|0|Add CTabFolder\nYou can only add CTabItems (from the 'Items' palette)\nas direct children of a CTabFolder|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.TabFolder|0|Add TabFolder\nYou can only add TabItems (from the 'Items' palette)\nas direct children of a TabFolder|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.Table|0|Add Table\nYou can only add TableItems (from the 'Items' palette)\nas direct children of a Table|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.TableColumn|0|Add TableColumn to Table|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.custom.TableTree|0|Add TableTree\nYou can only add TableTreeItems (from the 'Items' palette)\nas direct children of a TableTree|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.Tree|0|Add Tree\nYou can only add TreeColumns (from the 'Items' palette)\nas direct children of a Tree|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.TreeColumn|0|Add TreeColumn to Tree|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.CoolBar|0|Add CoolBar\nYou can only add CoolItems (from the 'Items' palette)\nas direct children of a CoolBar|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.widgets.ToolBar|0|Add ToolBar\nYou can only add ToolItems (from the 'Items' palette)\nas direct children of a ToolBar|n|"+PaletteComposite.PREF_SEP_2
                    + "Containers|2|org.eclipse.swt.awt.SWT_AWT|0|SWT_AWT bridge\nAllows Swing components to be\nembedded inside a SWT container|n|"+PaletteComposite.PREF_SEP_2
                    +

                    "Menu|2|org.eclipse.swt.widgets.Menu|"
                    + SWT.BAR
                    + "|Add MenuBar|n|"+PaletteComposite.PREF_SEP_2
                    + "Menu|2|org.eclipse.swt.widgets.MenuItem|"
                    + SWT.CASCADE
                    + "|Add MenuItem - CASCADE|n|"+PaletteComposite.PREF_SEP_2
                    + "Menu|2|org.eclipse.swt.widgets.MenuItem|"
                    + SWT.PUSH
                    + "|Add MenuItem - PUSH|n|"+PaletteComposite.PREF_SEP_2
                    + "Menu|2|org.eclipse.swt.widgets.MenuItem|"
                    + SWT.CHECK
                    + "|Add MenuItem - CHECK|n|"+PaletteComposite.PREF_SEP_2
                    + "Menu|2|org.eclipse.swt.widgets.MenuItem|"
                    + SWT.RADIO
                    + "|Add MenuItem - RADIO|n|"+PaletteComposite.PREF_SEP_2
                    + "Menu|2|org.eclipse.swt.widgets.MenuItem|"
                    + SWT.SEPARATOR
                    + "|Add MenuItem - SEPARATOR|n|"+PaletteComposite.PREF_SEP_2
                    +

                    "Items|2|org.eclipse.swt.custom.CTabItem|0|Add CTabItem to CTabFolder|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.widgets.TabItem|0|Add TabItem to TabFolder|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.widgets.TableItem|0|Add TableItem to Table|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.widgets.ToolItem|0|Add ToolItem to ToolBar|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.widgets.TreeItem|0|Add TreeItem to Tree|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.custom.TableTreeItem|0|Add TableTreeItem to TableTree|n|"+PaletteComposite.PREF_SEP_2
                    + "Items|2|org.eclipse.swt.widgets.CoolItem|0|Add CoolItem to CoolBar|n|"+PaletteComposite.PREF_SEP_2
                    +

                    "Layout|2|com.cloudgarden.jigloo.properties.AbsoluteLayout|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Layout|2|org.eclipse.swt.layout.FillLayout|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Layout|2|org.eclipse.swt.layout.FormLayout|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Layout|2|org.eclipse.swt.layout.GridLayout|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Layout|2|org.eclipse.swt.layout.RowLayout|0||n|"+PaletteComposite.PREF_SEP_2
                    + "Layout|2|org.eclipse.swt.custom.StackLayout|0||n|"+PaletteComposite.PREF_SEP_2;

            if (jiglooPlugin.canUseSwing()) {

                codedPalette += "Custom|1|||Add custom class or layout...|n|"+PaletteComposite.PREF_SEP_2+

                        "Containers|1|javax.swing.JScrollPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JDesktopPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JInternalFrame|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JSplitPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JTabbedPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JToolBar|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JPanel|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JWindow|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JDialog|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|javax.swing.JFrame|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Containers|1|org.eclipse.swt.awt.SWT_AWT|0|SWT_AWT bridge\nAllows SWT components to be\nembedded inside a Swing container|n|"+PaletteComposite.PREF_SEP_2
                        +

                        "Components|1|javax.swing.JButton|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JRadioButton|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.ButtonGroup|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JCheckBox|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JToggleButton|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JComboBox|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JSpinner|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JLabel|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JList|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JTextField|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JEditorPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JTextArea|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JTextPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JProgressBar|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JTable|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JTree|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|javax.swing.JSeparator|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Components|1|java.awt.Canvas|0||n|"+PaletteComposite.PREF_SEP_2
                        +

                        "More Components|1|javax.swing.JFormattedTextField|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JPasswordField|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JScrollBar|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JSlider|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JOptionPane|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JColorChooser|0||n|"+PaletteComposite.PREF_SEP_2
                        + "More Components|1|javax.swing.JFileChooser|0||n|"+PaletteComposite.PREF_SEP_2
                        +

                        "Menu|1|javax.swing.JMenuBar|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JMenu|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JCheckBoxMenuItem|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JMenuItem|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JRadioButtonMenuItem|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JSeparator|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Menu|1|javax.swing.JPopupMenu|0||n|"+PaletteComposite.PREF_SEP_2
                        +

                        "Layout|1|com.cloudgarden.jigloo.properties.AbsoluteLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|com.cloudgarden.layout.AnchorLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|java.awt.BorderLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|javax.swing.BoxLayout|0|Box Layout - X|n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|javax.swing.BoxLayout|1|Box Layout - Y|n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|java.awt.CardLayout|0||n|"+PaletteComposite.PREF_SEP_2;
                
                if(jiglooPlugin.getActiveEditor() != null  && jiglooPlugin.getActiveEditor().canUseEnfinLayout())
                    codedPalette += "Layout|1|de.gebit.s2j.smalltalk.gui.EnfinLayout|0||n|"+PaletteComposite.PREF_SEP_2;
                
                codedPalette += "Layout|1|java.awt.FlowLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|com.jgoodies.forms.layout.FormLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|java.awt.GridLayout|0||n|"+PaletteComposite.PREF_SEP_2
                        + "Layout|1|java.awt.GridBagLayout|0||n|"+PaletteComposite.PREF_SEP_2;
               
                if(LayoutWrapper.SUPPORT_GROUP_LAYOUT)
                    codedPalette += "Layout|1|org.jdesktop.layout.GroupLayout|0||n|"+PaletteComposite.PREF_SEP_2;
                
                codedPalette +=  "Layout|1|info.clearthought.layout.TableLayout|0||n|"+PaletteComposite.PREF_SEP_2;

                String[] swingClassArray = jiglooPlugin.getCustomSwingClasses();
                for (int i = 0; i < swingClassArray.length; i++) {
                    if (!"".equals(swingClassArray[i]))
                        codedPalette += "Custom|1|" + swingClassArray[i]
                                + "|0||y|"+PaletteComposite.PREF_SEP_2;
                }

            }

            String[] swtClassArray = jiglooPlugin.getCustomSWTClasses();
            for (int i = 0; i < swtClassArray.length; i++) {
                if (!"".equals(swtClassArray[i]))
                    codedPalette += "Custom|2|" + swtClassArray[i]
                            + "|0||y|"+PaletteComposite.PREF_SEP_2;
            }

            if(HarnessManager.ENABLE_ANDROID)
            	codedPalette += PaletteHelper.getPaletteString();
       
            pstore.setDefault(MainPreferencePage.P_PALETTE_CLASSES, codedPalette);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private Vector[] prefsPages = new Vector[6];

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#addField(org.eclipse.jface.preference.FieldEditor)
     */
    protected void addField(int page, FieldEditor editor) {
        if (prefsPages[page] == null)
            prefsPages[page] = new Vector();
        prefsPages[page].add(editor);
        super.addField(editor);
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */

    public void createFieldEditors() {

        GridData gd;
        GridLayout gl = null;

        setTitle("Jigloo GUI Builder - Version: " + jiglooPlugin.getVersion());

        Label lab = new Label(getFieldEditorParent(), SWT.NULL);
        lab.setText("\n\n" +
        		"Cloudgarden's Jigloo SWT/Swing GUI builder\n\nFree for non-commercial use." +
        		"\n\n" +
        		"If you are using Jigloo in the course of paid employment then you should purchase\n" +
        		"a professional license through the Cloudgarden website" +
        		"\n\n" +
        		" - http://www.cloudgarden.com/jigloo" +
        		"\n\n" +
        		"Thank you for using Jigloo." +
        		"\n\n" +
        		"If you have any comments, please post them here:" +
        		"\n\n" +
        		" - http://www.cloudgarden.com/forums.html");

    }

    private Label licLabel;

    private void setEvalLabel() {
        if (licLabel == null)
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

    //	protected void performDefaults() {
    //		super.performDefaults();
    //	}
    
//    protected void performDefaults() {
//        Vector fields = prefsPages[prefsComp.getSelection()];
//        if (fields != null) {
//            for (int i = 0; i < fields.size(); i++) {
//                FieldEditor pe = (FieldEditor) fields.elementAt(i);
//                pe.loadDefault();
//            }
//        }
//        // Force a recalculation of my error state.
//        checkState();
//        updateApplyButton();
//    }

//    public boolean performOk() {
//        boolean val = super.performOk();
//        setEvalLabel();
//        return val;
//    }

//    public void propertyChange(PropertyChangeEvent event) {
//        try {
//            if (event.getSource().equals(codeStyleField1)) {
//                if (event.getNewValue().equals("0")) {
//                    codeStyleField2.setEnabled(false, prefsComp
//                            .getCodeGenGroup());
//                } else {
//                    codeStyleField2.setEnabled(true, prefsComp
//                            .getCodeGenGroup());
//                }
//            }
//            //setEvalLabel();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        super.propertyChange(event);
//    }

    protected void adjustGridLayout() {
        //super.adjustGridLayout();
    }

    private Composite page1;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    /*
     * protected Control createContents(Composite parent) {
     * //parent.setLayout(new FillLayout()); CTabFolder tabFolder = new
     * CTabFolder(parent, SWT.NONE); CTabItem cti1 = new CTabItem(tabFolder,
     * SWT.NONE); page1 = new Composite(tabFolder, SWT.NONE);
     * cti1.setControl(page1); cti1.setText("Page 1"); GridLayout layout = new
     * GridLayout(); layout.numColumns = 1; layout.marginHeight = 0;
     * layout.marginWidth = 0; page1.setLayout(layout);
     * page1.setFont(parent.getFont());
     * 
     * tabFolder.layout(); return tabFolder; }
     */

}