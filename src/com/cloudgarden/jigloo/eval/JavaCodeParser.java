/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICodeFormatter;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.actions.ActionStub;
import com.cloudgarden.jigloo.appFramework.ActionManager;
import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupElement;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupLayoutUtils;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.harness.HarnessManager;
import com.cloudgarden.jigloo.harness.IHarness;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.jface.ApplicationWindowManager;
import com.cloudgarden.jigloo.layoutHandler.EnfinLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.openswing.OpenSwingHelper;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.properties.descriptors.EventPropertyDescriptor;
import com.cloudgarden.jigloo.util.ArrayUtils;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.ConversionUtils;
import com.cloudgarden.jigloo.util.DelayableRunnable;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.ItemManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.EventWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.JLayerWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;
import com.cloudgarden.jigloo.wrappers.SWTCursorWrapper;
import com.jgoodies.forms.layout.CellConstraints;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JavaCodeParser implements IJavaCodeManager {

    private static boolean USE_NEW_FORMATTER = false;

    static {
        try {
            ToolFactory.createCodeFormatter(null);
            USE_NEW_FORMATTER = true;
            //            System.out.println("Using new code formatter");
        } catch (Throwable t) {
            //initializes formatter now so that next time code is formatted
            //it is much quicker.
            ToolFactory.createCodeFormatter().format("test", 0, null, "\n");
        }
    }

    private boolean bufferChanged = false;
    /**
     * name of class to parse - if null is set to main class
     */
    private String classNameToParse;
    
    private ActionManager actionManager;

    private AST ast;

    public static String START_BLOCK = "//START >> ";

    public static String END_BLOCK = "//END << ";

    private static final String EVENT_HANDLER = "_EVT_HAND_";

    private static final String EVENT_LISTENER = "_EVT_LIST_";

    public static final String METHOD_PREFIX = "_METHOD_";

    //repair only the constructor (when changing SWT style)
    public static final int REPAIR_CONSTRUCTOR = 1;

    //repair parent connection (in Swing, this is the "add" etc method
    // invocation,
    //in SWT the constructor) PLUS, move all properties and layout/data
    //to their correct positions.
    public static final int REPAIR_PAR_CONNECTION = 2;

    //when class is changed - repair constructor and properties (and
    //delete layout if necessary (eg if changing from Group to Button)
    public static final int REPAIR_TYPE = 4;

    public static final int REPAIR_ALL = -1;

    private static final int CODE_GEN_FIELD_GETTERS = 1;

    private static final int CODE_GEN_FIELDS_IN_INIT = 2;

    private static final int CODE_GEN_FIELDS_AUTO = 3;

    private int codeGenMode = CODE_GEN_FIELDS_AUTO;

    private boolean PARSE_EVT_HANDLERS = false;

    private boolean inVEMode = false;

    private boolean bufferChecked = false;

    private FormEditor editor;

    private FormComponent root, rootCandidate;

    private FormComponent nvRoot = null;

    private FormComponent menuBar;

    private Name superClass;

    private String superClassName;

    private ICompilationUnit compUnit;

    private String packageName;

    private Vector hiddenBlocks = new Vector();

    private Vector protectedBlocks = new Vector();

    private IHarness harness = null;
    
    private boolean usingSWTResMan = false;

    private boolean usingResourceWrappers = false;

    //map of FormComponents, keys are component names
    private HashMap fields = new HashMap();

    private Vector basicFieldNames = new Vector();

    private HashMap fieldDecs = new HashMap();

    private HashMap setPropMethods = new HashMap();

    private HashMap elementAssignments = new HashMap();

    private HashMap methodMap = new HashMap();

    private HashMap methodCode = new HashMap();

    private HashMap blockCode = new HashMap();

    private HashMap methodReturns = new HashMap();

    private HashMap valueCache = new HashMap();

    private HashMap getters = new HashMap();

    private HashMap elementConnections = new HashMap();

    private HashMap comments = new HashMap();

    private HashMap imports = new HashMap();

    private Vector eventHandlers = new Vector();

    private Vector cutNodes = new Vector();

    private PackageDeclaration pkgNode = null;

    private TypeDeclaration mainType = null;

    private TypeDeclaration resManDec = null;

    private MethodDeclaration initGUIMethod = null;

    private MethodDeclaration initComponentsMethod = null;

    private boolean needsReparse = false;

    boolean USE_INITGUI = true;

    private int newBlockNum = 0;

    public JavaCodeParser(FormEditor editor) {
        this.editor = editor;
        actionManager = new ActionManager(editor);
    }

    public void reparse() {
        parse(compUnit);
    }

    private boolean parsing = false;

    private boolean addActionDialogOpen = false;

    private boolean needsResourceManager = false;

    private IBuffer wcbuff;

    private StringBuffer buff;

    private String buffString = null;

    //private String NL = System.getProperty("line.separator");
    private String NL = "\n";

    private String getParamString(List params) {
        String str = "";
        for (int i = 0; i < params.size(); i++) {
            if (i != 0)
                str += ",";
            SingleVariableDeclaration svd = (SingleVariableDeclaration) params.get(i);
            str += getUnqualifiedName(svd.getType().toString());
        }
        return str;
    }

    private String getParamString(String[] paramTypes) {
        String str = "";
        if (paramTypes == null)
            return str;
        for (int i = 0; i < paramTypes.length; i++) {
            if (i != 0)
                str += ",";
            String uq = getUnqualifiedName(paramTypes[i]);
            str += uq;
        }
        return str;
    }

    private static String getUnqualifiedName(String str) {
        if (str == null)
            return null;
        int pos = str.lastIndexOf(".");
        if (pos < 0) {
            pos = str.lastIndexOf("$");
            if (pos < 0)
                return str;
        }
        return str.substring(pos + 1);
    }

    private boolean parseWithErrors() {
        return jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_PARSE_ERRORS);
    }

    private String removeCommentStart(String cmt) {
        if (cmt.startsWith("//"))
            return cmt.substring(2);
        return cmt;
    }

    public static String[] jiglooComment = { "This code was edited or generated using CloudGarden's Jigloo",
            "SWT/Swing GUI Builder, which is free for non-commercial",
            "use. If Jigloo is being used commercially (ie, by a corporation,",
            "company or business for any purpose whatever) then you",
            "should purchase a license for each developer using Jigloo.",
            "Please visit www.cloudgarden.com for details.",
            "Use of Jigloo implies acceptance of these licensing terms.",
            "A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR", "THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED",
            "LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE." };

    boolean foundJiglooComment = false;

    private Comment jiglooPossComment = null;

    private boolean isDialog() {
        if (getSuperClass() == null)
            return false;
        return  Cacher.isAssignableFrom(Dialog.class, getSuperClass());
    }

    private boolean isJFaceDialog() {
        if (getSuperClass() == null)
            return false;
        return  Cacher.isAssignableFrom(org.eclipse.jface.dialogs.Dialog.class, getSuperClass());
    }

    private boolean isJFaceWindow() {
        if (getSuperClass() == null)
            return false;
        return  Cacher.isAssignableFrom(org.eclipse.jface.window.Window.class, getSuperClass());
    }

    private Vector parsedMethods = new Vector();

    private String getMethodNameKey(String methodName, List args) {
        if (args == null)
            return methodName + "_";

        Object[] params = getParams(args);
        String[] types = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            if (p instanceof FormComponent) {
                types[i] = "" + ((FormComponent) p).getMainClass();
            } else if (p != null) {
                types[i] = p.getClass().getName();
            }
            //System.out.println("GOT ARG " + p + ", " + types[i] + ", " + i);
        }
        return methodName + "_" + getParamString(types);
    }

    private String getMethodNameKey(MethodDeclaration mdec) {
        String methName = mdec.getName().toString();
        methName += "_" + getParamString(mdec.parameters());
        return methName;
    }

    private String getMethodNameKey(Expression mic) {
        String methName = null;
        IMethodBinding mb = null;
        if (mic instanceof MethodInvocation) {
            methName = ((MethodInvocation) mic).getName().toString();
            mb = ((MethodInvocation) mic).resolveMethodBinding();
        } else if (mic instanceof SuperMethodInvocation) {
            methName = ((SuperMethodInvocation) mic).getName().toString();
            mb = ((SuperMethodInvocation) mic).resolveMethodBinding();
        }
        if (mb == null)
            return methName + "_";
        ITypeBinding[] typebs = mb.getParameterTypes();
        String[] types = new String[typebs.length];
        for (int i = 0; i < typebs.length; i++) {
            types[i] = resolveName(getFullClassName(typebs[i]));
        }
        methName += "_" + getParamString(types);
        return methName;
    }

    private int indexOf(String str, int pos) {
        if (jiglooPlugin.getJavaVersion() > 13)
            return buff.indexOf(str, pos);
        else
            return buffString.indexOf(str, pos);
    }

    private int indexOf(String str) {
        if (jiglooPlugin.getJavaVersion() > 13)
            return buff.indexOf(str);
        else
            return buffString.indexOf(str);
    }

    public String getClassNameToParse() {
        return classNameToParse;
    }
    
    public void setClassNameToParse(String className) {
        classNameToParse = className;
    }
    
    public String getMainClassName() {
        String mainClassName = null;
        try {
            mainClassName = compUnit.getPrimaryElement().getElementName();
        } catch (Throwable t) {
            mainClassName = compUnit.getElementName();
        }
        if (mainClassName.endsWith(".java"))
            mainClassName = mainClassName.substring(0, mainClassName.length() - 5);
        return mainClassName;
    }
    
    public boolean parse(ICompilationUnit compUnit) {
        try {
            parsing = true;
            parsingDepth = 0;
            needsResourceManager = false;
            blockNum = 0;
            blockStr = "";
            newBlockNum = 0;
            inVEMode = false;
            usingSWTResMan = false;
            usingResourceWrappers = false;
            failedClasses.clear();
            editor.clearLogText();
            jiglooPlugin.writeToCreationLog("Creating elements for " + compUnit.getElementName() + "\n");
            //            addToLog("Parsing "+compUnit.getElementName());

            FormComponent.clearErrors();

            if (jiglooPlugin.DEBUG_EXTRA)
                System.out.println("***PARSING*** " + compUnit.getElementName());

            try {
                wcbuff = editor.getWorkingCopy().getBuffer();
                buff = new StringBuffer(wcbuff.getContents());
                if (jiglooPlugin.getJavaVersion() < 14)
                    buffString = buff.toString();

                int pos = 0;
                while (pos < getLength() && !isEOL(pos))
                    pos++;
                int pos2 = pos + 1;
                while (pos2 < getLength() && isEOL(pos2) && getChar(pos2) != getChar(pos))
                    pos2++;
                NL = getText(pos, pos2 - pos);
                //System.out.println("GOT NL, length=" + NL.length());

                if (!bufferChecked && !checkBuffer()) {
//                    String cont = wcbuff.getContents();
//                    String code = fixNewLines(cont);
//                    if (!code.equals(cont)) {
//                        System.out.println("SETTING WCBUFFER CONTENTS " + code);
//                        wcbuff.setContents(code);
//                    }
//                    buff = new StringBuffer(code);
//                    if (jiglooPlugin.getJavaVersion() < 14)
//                        buffString = buff.toString();

                    //always need to check buffer on reparsing because buffer
                    // may have
                    //been "reverted" to original, so don't set
                    // bufferChecked=true
                    //bufferChecked = true;
                }

            } catch (Throwable e) {
                jiglooPlugin.handleError(editor.getSite().getShell(), "Error parsing code", "Error parsing code", e);
                return false;
            }
            //            System.err.println("***PARSING 2 ***");
            editor.setStatus("Parsing Java code ...");

            CompilationUnit cu = AST.parseCompilationUnit(compUnit, true);

            ast = cu.getAST();

            //            System.err.println("***PARSING 3 ***");
            int errs = 0;
            //System.out.println("PARSED - GOT CU=" + cu + ", " + cu.getFlags()
            // + ", " + ASTNode.MALFORMED);
            if (0 != (cu.getFlags() & ASTNode.MALFORMED)) {
                addToLog("Error in code");
                editor.setStatus("Malformed Java Code");
                errs++;
                //return;
            }

            IProblem[] msgs = cu.getProblems();
            for (int i = 0; i < msgs.length; i++) {
                IProblem msg = msgs[i];
                if (msg.isError()) {
                    errs++;
                }
            }

            String msg = "Code parsed";
            if (errs != 0) {
                if (!parseWithErrors()) {
                    editor.setStatus("Errors in code - not parsing until errors fixed");
                    return false;
                } else {
                    msg = "Warning: " + errs + " error(s) in Java code - form may not be displayed correctly";
                }
            }

            if (msg != null)
                addToLog(msg);

            editor.setStatus(msg);
            editor.invalidate();

            //FormComponent shellRoot = findFormComponent("shell");

            //remove all FormComponents without assignment nodes
            //(eg, panel.add(new JTextField())

            valueCache.clear();
            eventHandlers.clear();
            fields.clear();
            basicFieldNames.clear();
            fieldDecs.clear();
            setPropMethods.clear();
            elementAssignments.clear();

            methodReturns.clear();
            elementConnections.clear();
            imports.clear();
            comments.clear();
            methodMap.clear();
            getters.clear();
            hiddenBlocks.clear();
            protectedBlocks.clear();
            parsedMethods.clear();

            FormComponent edRoot = editor.getRootComponent();
            if (edRoot != null && (edRoot.isJFaceForm() || edRoot.containsFactoryElements())) {
                edRoot.dispose(true);

                //set editor so we don't get NPEs
                edRoot.setEditor(editor);

            }

            laf = null;
            lafNode = null;

            resManDec = null;
            pkgNode = null;
            mainType = null;
            mainClass = null;
            initGUIMethod = null;
            initComponentsMethod = null;
            regResUserNode = null;

            this.compUnit = compUnit;

            List idecs = cu.imports();

            pkgNode = cu.getPackage();
            if (pkgNode != null) {
                packageName = pkgNode.getName().toString();
            } else {
                packageName = "";
            }

            for (Iterator iter = idecs.iterator(); iter.hasNext();) {
                ImportDeclaration idec = (ImportDeclaration) iter.next();
                String imp = idec.getName().toString();
                //System.out.println("GOT IMPORT "+imp);
                if (imp.equals("com.cloudgarden.resource.SWTResourceManager"))
                    editor.insertClass(packageName, "com/cloudgarden/resource/SWTResourceManager", false);
                imports.put(imp, idec);
            }
            List types = cu.types();
            
            String classNameToParse = getClassNameToParse();
            if(classNameToParse == null)
                classNameToParse = getMainClassName();

            for (Iterator iter = types.iterator(); iter.hasNext();) {
                TypeDeclaration tdec = (TypeDeclaration) iter.next();

                if (isHidden(tdec))
                    continue;
                if (JiglooUtils.getUnqualifiedName(getMainClassName()).equals(
                        JiglooUtils.getUnqualifiedName(tdec.getName().getIdentifier()))) {
                    mainType = tdec;
                } else {
                    continue;
                }

                //===========================
                //parse comments - start
                //===========================
                int pos = 0;
                int lastHideStartPos = -1;
                int lastProtStartPos = -1;
                String pst = removeCommentStart(getProtectStartTag());
                String pet = removeCommentStart(getProtectEndTag());
                String plt = removeCommentStart(getProtectLineTag());
                String hst = removeCommentStart(getHiddenStartTag());
                String het = removeCommentStart(getHiddenEndTag());
                String hlt = removeCommentStart(getHiddenLineTag());

                while (pos < buff.length()) {
                    int p1 = indexOf("//", pos);
                    //System.out.println("PARSING COMMENTS (1) pos=" + pos + ",
                    // p1=" + p1);
                    if (p1 < 0)
                        break;
                    int p2 = getEndOfLine(p1);
                    if (!getText(p1, START_BLOCK.length(), false).equals(START_BLOCK)
                            && !getText(p1, END_BLOCK.length(), false).equals(END_BLOCK)) {
                        String code = getText(p1, p2 - p1 + 1);

                        if (code.indexOf(hst) >= 0)
                            lastHideStartPos = p1;
                        if (code.indexOf(het) >= 0) {
                            if (lastHideStartPos > -1)
                                hiddenBlocks.add(new int[] { lastHideStartPos, p1 });
                            lastHideStartPos = -1;
                        }
                        if (code.indexOf(hlt) >= 0)
                            hiddenBlocks.add(new int[] { getStartOfLine(p1), p1 });

                        if (code.indexOf(pst) >= 0)
                            lastProtStartPos = p1;
                        if (code.indexOf(pet) >= 0) {
                            if (lastProtStartPos > -1)
                                protectedBlocks.add(new int[] { lastProtStartPos, p1 });
                            lastProtStartPos = -1;
                        }
                        if (code.indexOf(plt) >= 0)
                            protectedBlocks.add(new int[] { getStartOfLine(p1), p1 });

                        Comment c = new Comment(p1, code);
                        comments.put(c, c);
                    }
                    pos = p2;
                }

                pos = 0;
                jiglooPossComment = null;
                foundJiglooComment = false;

                //get condensed jiglooComment
                String jc = "";
                for (int i = 0; i < jiglooComment.length; i++) {
                    jc += jiglooComment[i];
                }
                jc = JiglooUtils.replace(jc, "*", "");
                jc = JiglooUtils.replace(jc, " ", "");
                jc = JiglooUtils.replace(jc, "\t", "");
                jc = JiglooUtils.replace(jc, "\n", "");
                jc = JiglooUtils.replace(jc, "\r", "");
                jc = jc.toLowerCase();

                while (pos < buff.length()) {
                    int p1 = indexOf("/*", pos);
                    //System.out.println("PARSING COMMENTS (2) pos=" + pos + ",
                    // p1=" + p1);
                    if (p1 < 0)
                        break;
                    // a line starting //* is a single-line comment!
                    if (p1 > 0 && buff.charAt(p1 - 1) == '/') {
                        pos = p1 + 1;
                        continue;
                    }
                    int p2 = indexOf("*/", p1);
                    if (p2 < 0)
                        break;
                    Comment c = new Comment(p1, getText(p1, p2 - p1 + 1));
                    comments.put(c, c);
                    boolean test = true;
                    String cmt = getCodeForNode(c);
                    //clean up comment so we don't match against a /*****
                    //comment, and so we replace /n with " " in case
                    // line-breaks
                    //have changed
                    cmt = JiglooUtils.replace(cmt, "*", "");
                    cmt = JiglooUtils.replace(cmt, "\t", " ");
                    cmt = JiglooUtils.replace(cmt, "\n", " ");
                    cmt = JiglooUtils.replace(cmt, "\r", " ");
                    //replace double spaces with single spaces
                    cmt = JiglooUtils.replace(cmt, "  ", " ");
                    cmt = cmt.toLowerCase();

                    for (int i = 0; i < jiglooComment.length; i++) {
                        String line = jiglooComment[i];
                        if (cmt.indexOf(line.toLowerCase()) >= 0) {
                            jiglooPossComment = c;
                        }
                    }
                    //then remove all spaces so we can compare
                    cmt = JiglooUtils.replace(cmt, " ", "");
                    if (cmt.indexOf(jc) >= 0)
                        foundJiglooComment = true;
                    pos = p2 + 2;
                }
                //===========================
                //parse comments - end
                //===========================

                TypeDeclaration[] innerClasses = tdec.getTypes();
                String ctp = JiglooUtils.replace(classNameToParse, "$", ".");
                for (int i = 0; i < innerClasses.length; i++) {
                    TypeDeclaration ic = innerClasses[i];
                    if (ic.getName().getIdentifier().equals("ResourceManager"))
                        resManDec = ic;
                    ITypeBinding tb = ic.getName().resolveTypeBinding();
                    if (tb != null && ctp.equals(getFullClassName(tb))) {
                        mainClassName = classNameToParse;
                        mainClass = editor.loadClass(classNameToParse);
                        tdec = ic;
                        mainType = tdec;
                    }
                }

                setSuperClass(mainType.getSuperclass());

                actionManager.reload();

                if (jiglooPlugin.DEBUG)
                    System.out.println("SUPERCLASS = " + superClass);
                
                FieldDeclaration[] fdecs = tdec.getFields();
                for (int i = 0; i < fdecs.length; i++) {
                    FieldDeclaration fdec = fdecs[i];
                    if (isHidden(fdec))
                        continue;
                    handleFieldDec(fdec.getType(), fdec.fragments(), fdec, true, fdec.getModifiers());
                }

                List bds = tdec.bodyDeclarations();
                for (int i = 0; i < bds.size(); i++) {
                    if (bds.get(i) instanceof Initializer) {
                        Initializer ini = (Initializer) bds.get(i);
                        if (isHidden(ini))
                            continue;
                        handleBlock((ini).getBody());
                    }
                }

                Vector constrs = new Vector();

                String mcKey = "";

                if (getMainClass() != null) {
                    mcKey = JiglooUtils.getUnqualifiedName(getMainClass().getName()) + "_";
                } else if (mainType != null) {
                    SimpleName nm = mainType.getName();
                    mcKey = nm + "_";
                }

                String igMeths = jiglooPlugin.getInitGUIMethods();
                igMeths = JiglooUtils.replace(igMeths, " ", "");
                igMeths = JiglooUtils.replace(igMeths, ";", ",");
                igMeths = JiglooUtils.replace(igMeths, "\t", "");
                igMeths = JiglooUtils.replace(igMeths, "\r", "");
                igMeths = JiglooUtils.replace(igMeths, "\n", "");
                igMeths += ",main_String[]";
                if (editor.isAppFrameworkApplication()) {
                    igMeths = "startup," + igMeths;
                }
                String[] initMeths = JiglooUtils.split(",", igMeths);
                for (int i = 0; i < initMeths.length; i++) {
                    String im = initMeths[i];
                    if (im.indexOf("_") < 0)
                        initMeths[i] = initMeths[i] + "_";
                    if (im.startsWith("create"))
                        initMeths[i] += "Composite";
                }

                MethodDeclaration[] meths = tdec.getMethods();
                for (int i = 0; i < meths.length; i++) {
                    MethodDeclaration mdec = meths[i];
                    if (isHidden(mdec))
                        continue;
                    String methName = getMethodNameKey(mdec);

                    //get all the constructors and the main method, with the
                    // aim of looking
                    //for calls to initialize the GUI

                    //make sure constructors come first,
                    //since if main is parsed first and the main class
                    //has been forbidden from being created, then the
                    //root component will probably not be the main class,
                    //but if a constructor is parsed first and initializes the
                    //GUI, then the main class will be the root component
                    if (methName.startsWith(mcKey))
                        constrs.add(0, mdec);

                    setMethodNode(methName, mdec);
                    if (editor.isSingleFrameApplication())
                        putMethodReturn("getMainFrame", getRootComponent());
                    
                    if (imports.containsKey(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".Action")) {
                        String code = getCodeForNode(mdec);
                        if (code.startsWith("/*"))
                            code = code.substring(code.indexOf("*/") + 2);
                        code = JiglooUtils.trim(code);
                        if (code.startsWith("@Action") || "@Action".equals(getTokenBeforeNode(mdec))) {
//                            System.err.println("adding action from code "+code);
                            actionManager.addAction(mdec);
                        }
                    }

                    if (jiglooPlugin.DEBUG)
                        System.out.println("ADDING METHOD " + methName + ", " + mcKey);
                }

                if (!jiglooPlugin.parseConstructorsFirst()) {
                    for (int i = 0; i < initMeths.length; i++) {
                        String mn = initMeths[i];
                        if (mn.startsWith("main_"))
                            continue;
                        MethodDeclaration mdec = getClosestMethod(mn);
                        if (mdec != null) {
                            initGUIMethod = mdec;

                            addToLog("GUI initialization method detected: " + initGUIMethod.getName());

                            parseMethod(mdec, mn);
                            break;
                        }
                    }
                }

                Iterator it;

                if (isCWT()) {
                    //now call analyseBlockForReturnObj from getValue (ie, only when
                    //a method is called) - but need to keep it for CWT for just now

                    //search for getter methods
                    it = methodMap.keySet().iterator();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        MethodDeclaration mdec = (MethodDeclaration) methodMap.get(key);
                        String methName = mdec.getName().toString();
                        analyseBlockForReturnObj(mdec.getBody(), methName);
                    }
                }

                if (initGUIMethod == null) {
                    parseMethod("main_String[]");
                    //parse all the constructors and the main method
                    for (int i = 0; i < constrs.size(); i++) {
                        MethodDeclaration mdec = (MethodDeclaration) constrs.elementAt(i);
                        String key = getMethodNameKey(mdec);
                        parseMethod(mdec, key);

                        if (initGUIMethod == null) {
                            //if one of the above "initGUI" methods has been parsed (as a
                            // result of being called from a constructor or main method) then we will
                            // find it here and set initGUIMethod to it.
                            for (int j = 0; j < initMeths.length; j++) {
                                String mn = initMeths[j];
                                if (mn.startsWith("main_"))
                                    continue;
                                mdec = getClosestMethod(mn);
                                if (parsedMethods.contains(mdec)) {
                                    initGUIMethod = mdec;
                                    addToLog("GUI initialization method detected: " + initGUIMethod.getName());
                                    break;
                                }
                            }
                        }
                        if (initGUIMethod != null)
                            break;
                    }
                }

                //if we were unable to find an initGUI method (which means that
                // none of the above methods was called) then look down the list
                // for one of the above methods in the code.
                if (initGUIMethod == null) {
                    for (int i = 0; i < initMeths.length; i++) {
                        String mn = initMeths[i];
                        MethodDeclaration mdec = getClosestMethod(mn);
                        if (mdec != null) {
                            if (jiglooPlugin.DEBUG)
                                System.out.println("GOT INIT GUI METH (2) " + mn);
                            if (mn.equals("main_String[]")) {
                                //if there is a constructor, set initGUIMethod to that before
                                //setting it to the main method
                                if (constrs.size() > 0)
                                    initGUIMethod = (MethodDeclaration) constrs.elementAt(0);
                                else
                                    initGUIMethod = mdec;
                            } else {
                                initGUIMethod = mdec;
                            }
                            if (initGUIMethod != null)
                                addToLog("GUI initialization method detected: " + initGUIMethod.getName());
                            parseMethod(mdec, mn);
                            break;
                        }
                    }
                }

                //                if(root != null && root.getTotalChildCount() == 0) {
                //                	initGUIMethod = null;
                //                }

                if (isSWT()) {
                    //parse showGUI just to get the parent shell
                    MethodDeclaration showGUI = (MethodDeclaration) methodMap.get("showGUI_");
                    if (showGUI != null) {
                        parseMethod(showGUI, "showGUI_");
                    }
                }

                //if none of the recognized initGUI methods was found, just
                // parse everything that hasn't been parsed already!
                //...also, just parse everything to get any extra dialogs, panels etc which might be
                //auxilliary to the main class

                if (true || //jiglooPlugin.parseEverything() ||
                        root == null || initGUIMethod == null || isCWT() || isJFaceWindow()) {

                    it = methodMap.keySet().iterator();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        MethodDeclaration mdec = (MethodDeclaration) methodMap.get(key);

                        //set tmpBS before handleBlock is called (which will
                        // change blockNum)
                        String tmpBS = "%" + blockNum + "." + blockStr;

                        if (parsedMethods.contains(mdec))
                            continue;

                        String mn = mdec.getName().toString() + "_";
                        parseMethod(mdec, mn);

                        if (isCWT()) {
                            ITypeBinding tb = resolveTypeBinding(mdec.getReturnType());

                            if (tb != null && getFullClassName(tb) != null) {
                                if (getFullClassName(tb).toString().equals("com.philemonworks.typewise.cwt.Screen")) {
                                    FormComponent scr = (FormComponent) getReturnValue(mn);
                                    com.cloudgarden.jigloo.typewise.TypewiseManager.handleScreenMethod(mdec, editor,
                                            scr);
                                }
                            }
                        }

                    }
                }

                //System.out.println("TDEC=" + tdec);
            }
            //System.out.println("***PARSING 4 ***");

            verifyGetterMethods();

            //force root to be re-calculated
            //            root = null;

            //System.out.println("***PARSING 5 ***");
            root = getRootComponent();

            if (initGUIMethod == null && root != null) {
                ASTNode node = getLastNode(root);
                if (node != null)
                    initGUIMethod = getEnclosingMethod(node.getStartPosition());
            }

            if (root != null)
                root.markMainTree();
            if (getMenuBar() != null)
                getMenuBar().markMainTree();

            editor.reconcileCodeChanges();

            if (root != null)
                root.markMainTree();

            editor.setRootComponent(root);

            //            getNonVisualRoot();

            //System.out.println("***PARSING 6.2 ***");
            root.setName(editor.getRootName(), false);
            //System.out.println("***PARSING 6.3 ***");
            if (needsResourceManager) {
                addResourceManagerClass();
            }
            editor.setLookAndFeel(laf);
            //System.out.println("***PARSING 6.6 ***");
            String lastLogMsg = jiglooPlugin.getLastLogMessage();
            if (lastLogMsg != null)
                editor.setStatus(lastLogMsg);
            else
                editor.setStatus(msg + ". Form constructed");

            //getNonVisualRoot().ensureUniqueness(null);
            //getRootComponent().ensureUniqueness(null);

            //System.out.println("***PARSING 7 ***");
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
            return false;
        }
        if (!isSwing())
            codeGenMode = CODE_GEN_FIELDS_IN_INIT;
        parsing = false;
        needsReparse = false;

        if (jiglooPlugin.DEBUG)
            System.out.println("PARSED CODE - IN VE MODE = " + inVEMode);
        //inVEMode = false;
        return true;
    }

    private void setSuperClass(Name superClass) {
		this.superClass = superClass;
        if (superClass == null)
            return;
        ITypeBinding tb = resolveTypeBinding(superClass);
        if (tb != null) {
            superClassName = getFullClassName(tb);
        }
        Object[] vals = new Object[2];
        loadClass(superClassName, vals);
        superCls = (Class) vals[0];
        harness = HarnessManager.getHarnessForClass(superCls, editor);
        editor.setHarness(harness);
	}

	/**
     * @param mn
     * @return
     */
    private IFormPropertySource getReturnValue(String methodName) {
        Object val = methodReturns.get(methodName);
        if ("null".equals(val))
            return null;
        return (IFormPropertySource) val;
    }

    private void putMethodReturn(String methodKey, Object value) {
        methodReturns.put(methodKey, value);
        //        System.out.println("Added  method return for "+methodKey);
    }

    private void removeMethodReturn(String methodKey) {
        methodReturns.remove(methodKey);
    }

    private void addToLog(String txt) {
        editor.addToLog(txt);
    }

    public String getCurrentStatement() {
        if (currentStatement == null)
            return null;
        return currentStatement.toString();
    }

    private void addToLog(Expression exp) {
        addToLog("Info: Unable to handle: " + JiglooUtils.getUnqualifiedName(exp.getClass().getName()) + ": " + exp
                + " in current statement " + currentStatement);
    }

    public void fieldRenamed(String oldName, String newName) {
        if (oldName != null && newName != null) {
            renameNode(elementAssignments, oldName, newName);
            renameNode(elementConnections, oldName, newName);
            renameNode(setPropMethods, oldName, newName);
        }
    }

    public boolean shouldUseSWTResMan() {
        return (isSWT() && usingResourceWrappers && !usingSWTResMan);
    }

    public void insertShowGUIMethod() {
        insertMethodFromTemplate("showGUI");
    }

    public void insertGetGUIBuilderSWTMethod() {
        insertMethodFromTemplate("getGUIBInstSWT");
    }

    public void insertGetGUIBuilderSwingMethod() {
        insertMethodFromTemplate("getGUIBInstSwing");
    }

    public void insertSwtAwtSpecialHandlers() {
        //find out which method is the "initGUI-like" method, and find all
        //the calls to it... and then convert them to initSwtAwtGUI calls.
        //Also, change the "initGUI" call inside DisplayThread if appropriate...
        String str = "initGUI";
        if (initGUIMethod != null) {
            str = initGUIMethod.getName().toString();
        }
        boolean convert = MessageDialog.openConfirm(editor.getSite().getShell(), "Confirm conversion to initSwtAwtGUI",
                "In order for SWT controls to run correctly inside Swing containers, " + "calls to the " + str
                        + "() method must be replaced by calls to the " + "auto-generated initSwtAwtGUI() method.\n\n"
                        + "Do you want the " + str + "() calls to be changed to initSwtAwtGUI() calls "
                        + "automatically now?");
        str += "();";
        if (convert) {
            int pos = indexOf(str);
            while (pos >= 0) {
                replaceText("initSwtAwtGUI();", pos, str.length(), true);
                pos = indexOf(str, pos + 1);
            }
        }
        int pos = getPositionAfterLastMethod();
        String cont = JiglooUtils.getTemplateAsString("DisplayThread", NL);
        cont = JiglooUtils.replace(cont, "%INIT_GUI_CALL%", str);
        insertText(cont, pos);
    }

    public int getPositionAfterLastMethod() {
        ASTNode lmn = getLastMethodNode();
        if (lmn != null) {
            int pos = lmn.getStartPosition() + lmn.getLength();
            return getStartOfNextLine(pos);
        } else {
            return getStartOfLine(mainType.getStartPosition() + mainType.getLength());
        }
    }

    public void insertMethodFromTemplate(String templateName) {
        int pos = getPositionAfterLastMethod();
        String cont = JiglooUtils.getTemplateAsString(templateName, NL);
        cont = JiglooUtils.replace(cont, "%CLASS_NAME%", editor.getClassName());
        insertText(cont, pos);
    }

    public void insertJiglooComment() {
        String cmnt = "/**" + NL;
        for (int i = 0; i < jiglooComment.length; i++) {
            cmnt += "* " + jiglooComment[i] + NL;
        }
        cmnt += "*/";
        cmnt = prepCode(cmnt, null);
        if (jiglooPossComment != null) {
            int st = jiglooPossComment.startPosition;
            replaceText(cmnt, st, jiglooPossComment.length + 1, true, st + 1, true);
        } else {
            if (mainType == null)
                insertText(NL + cmnt + NL, 0);
            else
                insertText(NL + cmnt + NL, mainType.getStartPosition());
        }
        foundJiglooComment = true;
//        editor.setDirtyAndUpdate();
    }

    public boolean hasJiglooComment() {
        return foundJiglooComment;
    }

    /**
     * Analyses a method from the newly-updated StringBuffer (as if parsing the
     * original ICompilationUnit)
     */
    private void analyseMergedMethod(String methodName) {
        parsing = true;
        char[] cha = buff.toString().toCharArray();
        CompilationUnit cu = AST.parseCompilationUnit(cha, compUnit.getElementName(), compUnit.getJavaProject());
        List types = cu.types();
        for (Iterator iter = types.iterator(); iter.hasNext();) {
            TypeDeclaration tdec = (TypeDeclaration) iter.next();
            MethodDeclaration[] meths = tdec.getMethods();
            for (int i = 0; i < meths.length; i++) {
                MethodDeclaration mdec = meths[i];
                String mn = mdec.getName().toString();
                if (mn.equals(methodName)) {
                    handleBlock(mdec.getBody());
                }
            }
        }
        cha = null;
        cu = null;
        types = null;
        parsing = false;
    }

    public void addResourceManagerClass() {
        if (true)
            return;
        if (resManDec != null)
            return;
        resManDec = ast.newTypeDeclaration();
        addImport("org.eclipse.swt.graphics.Color");
        addImport("org.eclipse.swt.graphics.Font");
        addImport("org.eclipse.swt.graphics.Image");
        addImport("org.eclipse.swt.graphics.FontData");
        addImport("org.eclipse.swt.widgets.Display");
        addImport("java.util.HashMap");

        String code = "static class ResourceManager {\n" + "\t\tstatic HashMap resources = new HashMap();\n";

        code += "\t\tstatic Color getFont(String name, int size, int style) {\n"
                + "\t\t\treturn getFont(name, size, style, false, false);\n" + "\t\t}\n";
        code += "\t\tstatic Font getFont(String name, int size, int style) {\n"
                + "\t\t\treturn getFont(name, size, style, false, false);\n" + "\t\t}\n"
                + "\t\tstatic Font getFont(String name, int size, int style,boolean strikeout, boolean underline) {\n"
                + "\t\t\tString fontName =\n"
                + "\t\t\t\tname + \"|\" + size + \"|\" + style + \"|\" + strikeout + \"|\" + underline;\n"
                + "\t\t\tif (resources.containsKey(fontName))\n" + "\t\t\t\treturn (Font) resources.get(fontName);\n"
                + "\t\t\tFontData fd = new FontData(name, size, style);\n";

        if (jiglooPlugin.isWindows())
            code += "\t\t\tif (strikeout || underline) {\n"
                    + "\t\t\t\torg.eclipse.swt.internal.win32.LOGFONT lf = fd.data;\n" + "\t\t\t\tif (lf != null) {\n"
                    + "\t\t\t\t\tif (strikeout) lf.lfStrikeOut = 1;\n"
                    + "\t\t\t\t\tif (underline) lf.lfUnderline = 1;\n" + "\t\t\t\t}\n";

        code += "\t\t\t}\n" + "\t\t\tFont font = new Font(Display.getDefault(), fd);\n"
                + "\t\t\tresources.put(fontName, font);\n" + "\t\t\treturn font;\n" + "\t\t}\n" + "\t}\n";

        addNode(resManDec, (ASTNode) mainType.bodyDeclarations().get(0), code, null, "RES_MAN", false, false, NL
                + "\t//ResourceManager class - generated by Jigloo." + NL, "");
    }

    private void handleEventListener(Expression exp, String listener, FormComponent par) {
        try {
            if (par == null)
                return;
            if (isHidden(exp))
                return;
            if (exp instanceof ClassInstanceCreation) {
                ClassInstanceCreation cic = (ClassInstanceCreation) exp;
                AnonymousClassDeclaration acd = cic.getAnonymousClassDeclaration();
                if (acd == null) {
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("Error in handleEventListener - anon class def missing");
                    return;
                }
                List bds = acd.bodyDeclarations();
                HashMap pmap = getPropMap(par.getName());
                for (int i = 0; i < bds.size(); i++) {
                    Object bd = bds.get(i);
                    if (bd instanceof MethodDeclaration) {
                        MethodDeclaration md = (MethodDeclaration) bd;

                        String argName = null;
                        if (md.parameters().size() == 1 && md.parameters().get(0) instanceof SingleVariableDeclaration)
                            argName = ((SingleVariableDeclaration) md.parameters().get(0)).getName().getIdentifier();

                        String mName = md.getName().getIdentifier();
                        Block mdb = md.getBody();
                        String code = getCodeForNode(mdb);
                        int pos = code.indexOf("{");
                        if (pos >= 0) {
                            int pos2 = code.lastIndexOf("}");
                            if (pos2 > 0) {
                                code = code.substring(pos + 1, pos2);
                            }
                        }
                        String handler = null;
                        List mdst = mdb.statements();
                        if (mdst.size() == 1) {
                            if (mdst.get(0) instanceof ExpressionStatement) {
                                ExpressionStatement stmt = (ExpressionStatement) mdst.get(0);
                                if (stmt.getExpression() instanceof MethodInvocation) {
                                    MethodInvocation mic = (MethodInvocation) stmt.getExpression();
                                    if (mic.getExpression() == null || mic.getExpression() instanceof ThisExpression) {
                                        handler = mic.getName().getIdentifier()
                                                + EventPropertyDescriptor.HANDLER_METHOD;
                                    }
                                }
                            }
                        }

                        if (handler == null)
                            handler = resolveName(par.getName()) + JiglooUtils.capitalize(mName)
                                    + EventPropertyDescriptor.INLINE;

                        par.getEventWrapper().setHandler(listener, mName, handler, code, argName);
                        //put the mouseMoved() {..} method declaration
                        //in the button's property node-map
                        pmap.put(getUnqualifiedName(listener) + EVENT_HANDLER + mName, md);

                        //call this method if you want to parse code inside
                        // event handler
                        if (PARSE_EVT_HANDLERS)
                            handleBlock(md.getBody());
                    }
                }
            } else {
                //                if (jiglooPlugin.DEBUG_EXTRA)
                addToLog("Error handling EventListener - " + exp);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }

    public void selectInCode(FormComponent comp) {
        selectInCode(comp, null);
    }

    public ASTNode getClosestNode(IFormPropertySource fps, String propName) {
        String name = fps.getName();
        ASTNode node = null;
        if (propName != null) {
            if (propName.equals("LAYOUT") && fps instanceof FormComponent)
                return getClosestNode(((FormComponent) fps).getLayoutWrapper(), null);
            node = getScrollToNode(fps, propName);
            if (node == null && fps instanceof FormComponent) {
                FormComponent fc = (FormComponent) fps;
                node = getScrollToNode(fc.getLayoutWrapper(), propName);
                if (node == null)
                    node = getScrollToNode(fc.getLayoutDataWrapper(), propName);
            }
        }
        if (node == null)
            node = getAssignmentNode(name);
        if (node == null)
            node = (ASTNode) getPropSetter(fps, false);
        if (node == null)
            node = getConnectionNode(name);
        if (node == null)
            node = getMethodNode(name);
        if (node == null)
            node = (ASTNode) fieldDecs.get(name);
        return node;
    }

    public void selectInCode(FormComponent comp, String propName) {
    	if(comp == null)
    		return;
        try {
            ASTNode node = getClosestNode(comp, propName);
            if (node == null) {
                return;
            }
            int start = node.getStartPosition();
            selectInCode(start);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    class CodeSelector extends DelayableRunnable {
        public CodeSelector(int pause, boolean inDisplayThread) {
            super(pause, inDisplayThread);
        }

        int codePos = 0;

        public void setCodePos(int codePos) {
            this.codePos = codePos;
        }

        public void run() {
            try {
                codePos = getStartOfLine(codePos);
                if (codePos < 0 || codePos > getLength() - 2)
                    return;
                if (!editor.isDisposed())
                    editor.setHighlightRange(codePos, 1, true);
                //                System.out.println("SELECT IN CODE "+codePos);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    CodeSelector codeSelector = new CodeSelector(50, true);

    public void selectInCode(int start) {
        codeSelector.setCodePos(start);
        Display.getDefault().syncExec(codeSelector);
        //        codeSelector.trigger();
    }

    public void dispose() {
    	buff = null;
    	buffString = null;
    	editor = null;
    	ast = null;
    	basicFieldNames.clear();
    	blockCode.clear();
    	root = null;
    	valueCache.clear();
    	fieldDecs.clear();
    	fields.clear();
    	comments.clear();
    	codeSelector = null;
    	compUnit = null;
    	elementAssignments.clear();
    	elementConnections.clear();
    }
    
    private void handleFieldDec(Type type, List frgs, ASTNode node, boolean isFieldDec, int modifiers) {
        try {
            currentStatement = node;
            if (isHidden(node))
                return;
            ITypeBinding tb = resolveTypeBinding(type);
            if (tb == null) {
                //                if (jiglooPlugin.DEBUG_EXTRA)
                addToLog("Unable to resolve " + type);
                return;
            }
            String fqType = getFullClassName(tb);
            
            if (fqType == null) {
                if (jiglooPlugin.DEBUG_EXTRA)
                    System.err.println("Unable to resolve (2) " + type);
                return;
            }
            String fieldName = null;
            String[] inits = null;
            for (Iterator it = frgs.iterator(); it.hasNext();) {
                VariableDeclarationFragment vdf = (VariableDeclarationFragment) it.next();
                currentStatement = vdf;
                fieldName = vdf.getName().toString();

                fieldName = blockStr + fieldName;

                FormComponent fc = elementCreated(fieldName, vdf.getInitializer(), fqType, true, modifiers, node);

                if (fc != null) {
                    fieldName = fc.getName();
                }

                if (isFieldDec) {
                    setFieldDecNode(fieldName, node);
                } else {
                    setAssignmentNode(fieldName, node);
                }
            }

        } catch (Throwable e) {
            addToLog("Error handling Field Declaration " + getCodeForNode(node));
            e.printStackTrace();
        }
    }

    private ASTNode getConnectionNode(String name) {
        return (ASTNode) elementConnections.get(name);
    }

    private ASTNode getAssignmentNode(String name) {
        return (ASTNode) elementAssignments.get(name);
    }

    private void setFieldDecNode(String name, ASTNode node) {
    	if(!fieldDecs.containsValue(node)) {
    		fieldDecs.put(name, node);
    	} else {
    		System.out.println("field dec node already in map for "
    				+name+" as "+getKeyForValue(fieldDecs, node));
    	}
    }

    private void setConnectionNode(String name, ASTNode node) {
    	if(!elementConnections.containsValue(node)) {
    		elementConnections.put(name, node);
    	} else {
    		System.out.println("connection node already in map for "
    				+name+" as "+getKeyForValue(elementConnections, node));
    	}
    }

    private void setMethodNode(String name, ASTNode node) {
    	if(!methodMap.containsValue(node)) {
    		methodMap.put(name, node);
    	} else {
    		System.out.println("method node already in map for "
    				+name+" as "+getKeyForValue(methodMap, node));
    	}
    }

    private void setAssignmentNode(String name, ASTNode node) {
    	if(!elementAssignments.containsValue(node)) {
    		elementAssignments.put(name, node);
    	} else {
    		Object key = getKeyForValue(elementAssignments, node);
    		if(!key.equals(name)) {
    			System.out.println("assignment node already in map for "+name+" as "+key);
    		}
    	}
    }

    private Vector failedClasses = new Vector();

    private Class loadClass(String className, Object[] returnVals) {
        return loadClass(className, returnVals, true);
    }

    private Class loadClass(String className, Object[] returnVals, boolean addWarning) {
    	if(className.indexOf("<") > 0) {
    		className = className.substring(0, className.indexOf("<"));
    	}
        Class cls = editor.loadClass(className);
        if (cls == null) {
            int pos = className.lastIndexOf(".");
            if (pos != -1) {
                String innerCN = className.substring(0, pos) + "$" + className.substring(pos + 1);
                cls = editor.loadClass(innerCN);
                if (cls == null && !failedClasses.contains(className)) {
                    failedClasses.add(className);
                    if (addWarning)
                        addToLog("Warning: unable to load " + className);
                }
            }
        }
        if (returnVals != null) {
            returnVals[0] = cls;
            returnVals[1] = className;
        }
        return cls;
    }

    public FormComponent getComponentInCode(String line, int pos) {
        if (line != null) {
            int p1 = indexOf(line, pos);
            if (p1 >= 0) {
                int i = 0;
                while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'))
                    i++;
                if (jiglooPlugin.DEBUG)
                    System.out.println("getComponentInCode " + pos + ", " + (p1 + i));
                pos = p1 + i;
            }
        } else {
            pos = getStartOfLine(pos);
            while (isWhiteSpace(pos))
                pos++;
        }
        Object key = getEnclosingNodeKey(elementAssignments, pos);
        if (key == null)
            key = getEnclosingNodeKey(elementConnections, pos);
        if (key == null)
            key = getEnclosingNodeKey(fieldDecs, pos);
        if (key == null) {
            Iterator it = setPropMethods.keySet().iterator();
            while (it.hasNext()) {
                String fcName = (String) it.next();
                //System.out.println("CHECKING MICS FOR " + fcName);
                HashMap map = (HashMap) setPropMethods.get(fcName);
                key = getEnclosingNodeKey(map, pos);
                if (key != null) {
                    key = fcName;
                    break;
                }
            }
        }

        if (key != null) {
            return findFormComponent((String) key);
        }
        return null;
    }

    public String getSuperclassName() {
        return superClassName;
    }

    private Class superCls = null;

    public Class getSuperClass() {
        return superCls;
    }

    private String mainClassName = null;

    private String fullClassName = null;

    private Class mainClass = null;

    public String getFullClassName() {
        if (fullClassName == null && mainType != null) {
            ITypeBinding tb = resolveTypeBinding(mainType.getName());
            if (tb != null) {
                fullClassName = getFullClassName(tb);
            }
        }
        return fullClassName;
    }

    public Class getMainClass() {
        if (mainClass != null)
            return mainClass;
        if (mainType == null)
            return null;
        ITypeBinding tb = resolveTypeBinding(mainType.getName());
        if (tb != null) {
            String className = getFullClassName(tb);
            if (className == null)
                return null;
            Object[] vals = new Object[2];
            loadClass(className, vals);
            mainClass = (Class) vals[0];
            mainClassName = (String) vals[1];
            return mainClass;
        }
        return null;
    }

    public void setRootComponent(FormComponent root) {
        this.root = root;
    }

	/**
	 * @return
	 */
    public FormComponent getRootComponent() {
        if (harness != null) {
        	root = harness.getRootComponent(root);
        }
        
        if (editor.isSingleFrameApplication()) {
            if (root != null && !"mainFrame".equals(root.getName())) {
            	if(root != null)
            		root.setRoot(false);
                root = null;
            }
            if (root == null) {
                root = FormComponent.newFormComponent(editor, "javax.swing.JFrame");
        		root.setRoot(true);
//                root.setEditor(editor);
                editor.setRootName("mainFrame");
                root.setPropertyValueSimple("name", "mainFrame");
                root.setName("mainFrame");
                root.setBaseComponent(true);
//                root.setClassName("javax.swing.JFrame");
                root.setClassType(FormComponent.TYPE_SWING);
            }
        }
        if (root == null || root.getClassName() == null || "UNKNOWN".equals(root.getClassName())
                || Dialog.class.getName().equals(root.getClassName())) {

        	if(root != null)
        		root.setRoot(false);
            FormComponent fc = null;
            FormComponent cand = null;
            String className = null;
            Class cls = null;
            Object[] vals = null;

            if (superClass != null) {
                ITypeBinding tb = resolveTypeBinding(superClass);
                if (tb != null) {
                    className = getFullClassName(tb);
                    if (className != null) {
                        vals = new Object[2];
                        loadClass(className, vals);
                        cls = (Class) vals[0];

                        //maybe the superclass is a WindowAdapter etc
                        if (cls != null && !ClassUtils.isVisual(cls) && !ClassUtils.isRCP(cls)) {
                            setSuperClass(null);
                            cls = null;
                        }

                    }
                }
            }

            if (cls == null || !ClassUtils.isVisual(cls)) {
                int max = -1;
                int tcc = 0;
                int cnt = 0;
                Iterator it = fields.keySet().iterator();
                while (it.hasNext()) {
                    Object id = it.next();
                    Object val = fields.get(id);
                    if (val instanceof FormComponent) {
                        FormComponent ffc = (FormComponent) val;
                        if (!ffc.canBeRoot())
                            continue;
                        if (ffc.isVisual() && (ffc.getParent() == null || !ffc.getParent().isVisual())) {
                            tcc = 0;
                            //really don't want a menu component to be the root
                            //component if there is any alternative
                            if (!ffc.isMenuComponent()) {
                                tcc = ffc.getTotalChildCount();
                            }
                            if (tcc > max) {
                                max = tcc;
                                cand = ffc;
                            }
                        }
                    }
                }
                if (cand != null) {
                    if (jiglooPlugin.DEBUG)
                        System.out.println("GUESSING ROOT COMP " + cand);
                    editor.setRootName(cand.getName());
                }
            }

            fc = findFormComponent("dialogShell");
            if (fc != null) {
                editor.setRootName(fc.getName());
            }

            if (superClass == null) {
                //SWT_AWT_TEST
                if (fc == null && cand == null) {
                    fc = findFormComponent("shell");
                    if (fc != null)
                        editor.setRootName(fc.getName());
                }
            }

            if (fc != null) {
                root = fc;
                root.setClassType(FormComponent.TYPE_SWT);
            } else {
                if (cand != null) {
                	
                	if(rootCandidate != null)
                		rootCandidate.setRoot(false);
                	cand.setRoot(true);
                	rootCandidate = cand;
                    return cand;
                
                } else {
                    root = FormComponent.newFormComponent(editor, vals == null ? null : (String) vals[1]);
                }
                root.setEditor(editor);
                root.setName(editor.getRootName());
                root.setBaseComponent(true);
                if (cls != null) {
                    boolean isJComponent = false;
                    if (jiglooPlugin.canUseSwing())
                        isJComponent =  Cacher.isAssignableFrom(Component.class, cls);
                    root.setClassName((String) vals[1]);
                    if(isJComponent)
                        root.setClassType(FormComponent.TYPE_SWING);
                } else {
                    root.setClassType(FormComponent.TYPE_SWT);
                }
            }
        }
        root.setRoot(true);
        return root;
    }

    public FormComponent getNonVisualRoot() {
        if (nvRoot == null)
            nvRoot = new FormComponent(null, null, editor);
        fillNonVisRoot();
        return nvRoot;
    }

    private void fillNonVisRoot() {
        Iterator it = fields.keySet().iterator();
        Vector nonVisVect = new Vector();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object nv = fields.get(key);
            if (nv != null) {
                Class cls = nv.getClass();
                FormComponent fc = null;
                if (nv instanceof FormComponent) {
                    fc = (FormComponent) nv;
                    if (!fc.isVisual()) { //&& fc.isInMainTree()) {
                        nonVisVect.add(nv);
                        nvRoot.add(fc);
                    }
                } else {
                    if (!ClassUtils.isVisual(cls) && !ClassUtils.isResource(cls) && !ClassUtils.isLayout(cls)
                            && !ClassUtils.isLayoutData(cls) && editor.getComponentByName(key) == null) {
                        fc = FormComponent.newFormComponent(editor, nv.getClass().getName());
                        fc.setName(key);
                        fc.setNonVisualObject(nv);
                        fc.setClassName(nv.getClass().getName());
                        nonVisVect.add(fc);
                        nvRoot.add(fc);
                    }
                }
            }
        }
        nvRoot.setName(FormEditor.NON_VISUAL_LABEL);
        nvRoot.setChildren(nonVisVect);
    }

    public Vector getParentlessFields() {
        Iterator it = fields.keySet().iterator();
        Vector parentLess = new Vector();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object nv = fields.get(key);
            if (nv != null) {
                if (nv instanceof FormComponent) {
                    FormComponent fc = (FormComponent) nv;
                    if (fc.getParent() == null) {
                        parentLess.add(nv);
                        continue;
                    }
                    if (fc.isA(Shell.class) && fc.getParent().isA(Display.class)) {
                        parentLess.add(nv);
                        continue;
                    }
                }
            }
        }
        return parentLess;
    }

    public FormComponent getMenuBar() {
        FormComponent rc = getRootComponent();
        if (rc != null)
            menuBar = rc.getMenuBar();

        if (menuBar != null && menuBar.getParent() != null) {
            menuBar.getParent().getChildren().remove(menuBar);
            menuBar.setParent(null);
        }
        return menuBar;
    }

    public String getNextAvailableName(String name) {
        String qual = JiglooUtils.getQualifier(name);
        String qname = name;
        name = JiglooUtils.getUnqualifiedName(name);
        while (basicFieldNames.contains(name) 
        		|| fieldDecs.containsKey(name) 
        		|| fields.containsKey(qname)) {
            name = incrementEndDigit(name);
            qname = incrementEndDigit(qname);
        }
        if (qual != null)
            name = qual + "." + name;
        return name;
    }

    private String incrementEndDigit(String str) {
        int off = str.length()-1;
        int num = 0;
        int pwr = 1;
        while (off >= 0) {
        	int dig = str.charAt(off) - '0';
        	if(dig >= 0 && dig <= 9) {
        		num += pwr*dig;
        		pwr *= 10;
        		off--;
        	} else {
        		off++;
        		break;
        	}
        }
        num++;
        return str.substring(0, off) + num;
    }

    public boolean isSwing() {
        return getRootComponent().isSwing();
    }

    public boolean isSWT() {
        return getRootComponent().isSWT();
    }

    public boolean isCWT() {
        return getRootComponent().isCWT();
    }

    private int toInt(Object o) {
        if (o instanceof Integer)
            return ((Integer) o).intValue();
        return 0;
    }

    private boolean toBool(Object o) {
        if (o instanceof Boolean)
            return ((Boolean) o).booleanValue();
        return false;
    }

    private int[] convertToIntArray(Object[] oa) {
        int[] ia = new int[oa.length];
        for (int i = 0; i < oa.length; i++) {
            if (oa[i] instanceof Integer)
                ia[i] = ((Integer) oa[i]).intValue();
        }
        return ia;
    }

    private Object getWrapper(Class cls, Object obj, String id, FormComponent comp) {
        try {
            if (obj == null)
                return null;

            //id = resolveName(id);
            IFormPropertySource lw = null;

            if (obj instanceof IFormPropertySource) {
                if (obj instanceof LayoutDataWrapper) {
                    LayoutDataWrapper ldw = (LayoutDataWrapper) obj;
                    //System.out.println("GET WRAPPER, LDW=" + ldw + ", " +
                    // ldw.getPropertyValue("control"));
                    ldw.setFormComponent(comp, true);
                }

                if (obj instanceof LayoutWrapper) {
                    //this should maybe apply to not just LayoutWrapper,
                    //but only LayoutWrapper seems to be broken

                    //putField(id, obj);
                    lw = (IFormPropertySource) obj;
                    lw.setName(id);
                }

                return obj;
            }

            if (ClassUtils.isLayout(obj.getClass())) {
                boolean swing = false;
                if (comp != null)
                    swing = comp.isSwing();
                lw = new LayoutWrapper(comp, obj.getClass(), swing);
            } else if (ClassUtils.isLayoutData(obj.getClass())) {
                lw = new LayoutDataWrapper(obj, comp);
            }

            if (lw != null) {
                lw.setObject(obj);
                lw.setName(id);
                if (id != null)
                    putField(id, lw);
                return lw;
            }

            //color
            if (obj instanceof ColorWrapper || obj instanceof FontWrapper || obj instanceof ImageWrapper) {
                if (obj instanceof ImageWrapper) {
                    ((ImageWrapper) obj).setFormComponent(comp);
                }
                putField(id, obj);
                return obj;
            }

            Object wrapper = null;
            //System.err.println("GET WRAPPER OBJ=" + obj + ", " +
            // obj.getClass());
            if (ClassUtils.isColor(cls)) {
                if (obj instanceof Object[])
                    obj = convertToIntArray((Object[]) obj);
                if (obj instanceof int[]) {
                    int[] cols = (int[]) obj;
                    wrapper = new ColorWrapper(cols[0], cols[1], cols[2], comp);
                } else if (jiglooPlugin.canUseSwing() && obj instanceof java.awt.Color) {
                    wrapper = new ColorWrapper((java.awt.Color) obj, comp);
                }
            } else if (ClassUtils.isFont(cls)) {
                if (obj instanceof Object[]) {
                    Object[] obs = (Object[]) obj;
                    if (obs.length == 5) {
                        wrapper = new FontWrapper((String) obs[0], toInt(obs[1]), toInt(obs[2]), toBool(obs[3]),
                                toBool(obs[4]), comp);
                    } else if (obs.length == 3) {
                        wrapper = new FontWrapper((String) obs[0], toInt(obs[1]), toInt(obs[2]), comp);
                    }
                } else if (jiglooPlugin.canUseSwing() && obj instanceof java.awt.Font) {
                    wrapper = new FontWrapper((java.awt.Font) obj, comp);
                }
            } else if (Cursor.class.equals(cls)) {
                if (obj instanceof Object[]) {
                    Object[] obs = (Object[]) obj;
                    if (obs.length == 1) {
                        wrapper = new SWTCursorWrapper(toInt(obs[0]), comp);
                    }
                } else if (jiglooPlugin.canUseSwing() && obj instanceof java.awt.Font) {
                    wrapper = new FontWrapper((java.awt.Font) obj, comp);
                }
            } else if (ClassUtils.isIcon(cls) && obj instanceof String) {
                wrapper = new IconWrapper((String) obj, comp);
            } else if (ClassUtils.isImage(cls) && obj instanceof String) {
                wrapper = new ImageWrapper((String) obj, comp);
            }
            if (wrapper != null) {
                if (id != null)
                    putField(id, wrapper);
                return wrapper;
            }
        } catch (Throwable e) {
            System.err.println("Error in getWrapper " + cls + ", " + id + ", " + comp);
            e.printStackTrace();
        }
        return obj;
    }

    /*
     * private Object getWrapper(Class cls, String[] inits) { if (isImage(cls) ||
     * isIcon(cls)) { System.out.println("GET WRAPPER " + cls); for (int i = 0;
     * i < inits.length; i++) { String str = inits[i]; if (str.indexOf("\"") >=
     * 0) { str = str.substring(str.indexOf("\"") + 1); str = str.substring(0,
     * str.indexOf("\"")); System.out.println("GOT WRAPPER " + cls + ", " +
     * str); return str; } } System.out.println("GET WRAPPER FAILED " + cls);
     * return "icons/sample.gif"; } else if (Color.class.isAssignableFrom(cls)) {
     * return new int[] { 255, 0, 0 }; } return null; }
     */

    //private void surroundByBraces(ASTNode node) {
    //insertText("{", pos);
    //}
    /*
     * private boolean isSurroundedByBraces(ASTNode node) { int pos =
     * node.getStartPosition(); while (pos > 0 && (isWhiteSpace(pos) ||
     * isEOL(pos))) pos--; while (pos > 0 && !isEOL(pos)) pos--; while
     * (isWhiteSpace(pos)) pos++; if (buff.getChar(pos) != '{') return false;
     * pos = node.getStartPosition(); while (pos < buff.getLength() &&
     * (isWhiteSpace(pos) || isEOL(pos))) pos++; if (buff.getChar(pos) != '}')
     * return false; return true; }
     */

    private String resolveName(String name) {
        //System.out.println(">>>RESOLVING NAME " + name + ", " + blockStr + ",
        // " + fields);
        if (name == null)
            return null;

        while (name.startsWith("%")) {
            if (fields.containsKey(name)) {
                //System.out.println("<<<RESOLVED NAME "+name+", "+blockStr);
                return name;
            }
            int pos = name.indexOf(".");
            name = name.substring(pos + 1);
        }

        String bs = blockStr;
        while (!bs.equals("")) {
            //System.out.println("TRYING TO FIND OBJECT " + name + ", " + bs);
            if (fields.containsKey(bs + name)) {
                //                System.err.println("RESOLVED NAME AFTER ADDING BLOCK STRING " + name + ", " + blockStr);
                return bs + name;
            }
            int pos = bs.indexOf(".");
            bs = bs.substring(pos + 1);
        }

        if (fields.containsKey(name)) {
            //System.out.println("<<<RESOLVED NAME "+name+", "+blockStr);
            return name;
        }

        //System.out.println("<<<DID NOT RESOLVE NAME "+name+", "+blockStr);
        return name;
    }

    private Object findClosestObject(HashMap map, String name) {
        if (name == null)
            return null;
        Object o = findObject(map, name);
        if (o != null)
            return o;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.endsWith(name))
                return map.get(key);
        }
        return null;
    }

    private String findFieldName(String name) {
        if (name == null)
            return null;

        while (name.startsWith("%")) {
            if (fields.containsKey(name)) {
                return name;
            }
            int pos = name.indexOf(".");
            if (pos < 0)
                break;
            name = name.substring(pos + 1);
        }

        String bs = blockStr;
        while (!bs.equals("")) {
            //System.out.println("TRYING TO FIND OBJECT " + name + ", " + bs);
            if (fields.containsKey(bs + name))
                return bs + name;
            int pos = bs.indexOf(".");
            if (pos < 0)
                break;
            bs = bs.substring(pos + 1);
        }

        if (fields.containsKey(name))
            return name;
            
        return null;
    }

    private Object findObject(HashMap map, String name) {
        if (name == null)
            return null;

        while (name.startsWith("%")) {
            if (map.containsKey(name)) {
                return map.get(name);
            }
            int pos = name.indexOf(".");
            if (pos < 0)
                break;
            name = name.substring(pos + 1);
        }

        String bs = blockStr;
        while (!bs.equals("")) {
            //System.out.println("TRYING TO FIND OBJECT " + name + ", " + bs);
            if (map.containsKey(bs + name))
                return map.get(bs + name);
            int pos = bs.indexOf(".");
            if (pos < 0)
                break;
            bs = bs.substring(pos + 1);
        }

        Object o = map.get(name);
        //if (o == null) {
        //System.out.println("UNABLE TO FIND OBJ " + name + ", " + blockStr);
        //}
        return o;
    }

    private Object convertToFormComponent(Object key) {
        if (key instanceof String) {
            FormComponent fc = findFormComponent((String) key);
            if (fc != null)
                return fc;
        }
        return key;
    }

    public FormComponent findFormComponent(String name) {
        if (name == null)
            return null;
        Object obj = findObject(fields, name);

        //a field or getter might be accessed either by this.group1
        //or super.group1, so the name coming in might be
        //super.group1, but internally it is named <parent>.group1,
        //which becomes this.group1
        if (obj == null) {
            obj = findObject(fields, JiglooUtils.switchThisAndSuper(name));
        }

        if (obj instanceof FormComponent) {
            return (FormComponent) obj;
        } else {
            int pos = name.lastIndexOf(".");
            if (pos >= 0) {
                String fn = name;
                name = name.substring(0, pos);
                String name2 = fn.substring(pos + 1);
                if (name.equals("this") || name.equals("super")) {
                    obj = findObject(fields, name2);
                    if (obj instanceof FormComponent)
                        return (FormComponent) obj;
                }
                FormComponent par = null;
                if (name.equals("this") || name.equals("super") || name.equals("dialogShell")
                        || (superClass == null && name.equals("shell"))) {
                    par = getRootComponent();
                } else {
                    par = findFormComponent(name);
                }
                if (par != null) {
                    String pName = JiglooUtils.propertyFromGetter(name2);
                    return par.getChildByName(par.getName() + "." + pName);
                }
            } else {
                if (root != null)
                    return root.getChildByName(name);
            }
            return null;
        }
    }

    private String class2fieldName(String className, boolean inline, String suffix) {
        className = JiglooUtils.getUnqualifiedName(className);
        className = JiglooUtils.replace(className, ";", "");
        className = JiglooUtils.deCapitalize(className);
        if (inline)
            className += suffix;
        className = getNextAvailableName(className);
        return className;
    }

    /**
     * Converts from Object[][] to <className>[][]
     * @param val - an Object of type Object[][] (though eventually could be any array
     * @param className - the class name of the desired array
     * @return - an array of for example, double[][], if className = "double"
     */
    private Object convertArrayObjects(Object val, String className) {
        if (val instanceof Object[]) {
            Object[] v1 = (Object[]) val;
            Object ret = ArrayUtils.convertToPrimitiveArray(v1, className, editor);
            if (ret != null) {
//            	System.out.println("Got primitive array "+ret);
                return ret;
            }

            //this returns an array of, say JButtons, but then we lose sight
            //of the FormComponents - but we also have a JButton[] instead
            //of a Object[]
            Class cls = editor.loadClass(className);
            if (cls == null)
                return val;

            int[] dimArray = ArrayUtils.getDims(val);
            Object a = Array.newInstance(cls, dimArray);

            for (int i = 0; i < v1.length; i++) {
                if (v1[i] != null && v1[i].getClass().isArray()) {
                    Object con = convertArrayObjects(v1[i], className);
                    if (con instanceof ArrayHolder) {
                        ArrayHolder hol = (ArrayHolder) con;
                        Array.set(a, i, hol.getRawArray());
                        Array.set(v1, i, hol.getFCArray());
                    }
                } else {
                    if (v1[i] instanceof FormComponent) {
                        Array.set(a, i,  ((FormComponent) v1[i]).getObject(true));
                    } else if (v1[i] instanceof ConstructorHolder) {
                        ConstructorHolder ch = (ConstructorHolder) v1[i];
                        FormComponent fc = createInlineFormComponent(null, ch);
                        v1[i] = fc;
                        setAssignmentNode(fc.getName(), ch.getExpression());
                        Array.set(a, i, fc.getObject(true));
                    } else {
                    	Array.set(a, i,  v1[i]);
                        if (v1[i] != null) {
                            System.err.println("convertArrayObjects: creating new FC for " + v1[i]);
                            v1[i] = FormComponent.newFormComponent(
                            		editor.getExtraCompRoot(), 
                            		v1[i].getClass().getName(), 
                            		"unknown",
                                    false, 
                                    v1[i]);
                        }
                    }
                }
            }
            if (!(v1 instanceof FormComponent[])) {
//                System.err.println("converting array " + v1 + " to a FC array");
                FormComponent[] tmp = new FormComponent[v1.length];
                for (int i = 0; i < v1.length; i++) {
                    if (v1[i] instanceof FormComponent)
                        tmp[i] = (FormComponent) v1[i];
                }
                v1 = tmp;
            }
            return new ArrayHolder(a, (FormComponent[]) v1);
        }
        return val;
    }

    private FormComponent elementCreated(String fieldName, Expression exp, String fqType, boolean isFieldDec,
            int modifiers, ASTNode node) {

        if (fqType == null)
            return null;

        exp = EvaluationUtils.removeCast(exp);

        String className = fqType;
        while (className.endsWith("[]")) {
            className = className.substring(0, className.length() - 2);
        }

        if (!JiglooUtils.isValidTypeForElement(className)) {
            Object val = getValue(exp);
            val = convertArrayObjects(val, className);
            fields.put(fieldName, val);
            return null;
        }

        Object[] vals = new Object[2];
        loadClass(className, vals);
        Class cls = (Class) vals[0];
        if (!jiglooPlugin.getDefault().canMakeNVClass(cls))
            return null;
        className = (String) vals[1];
        if (cls == null) {
            if (jiglooPlugin.DEBUG)
                System.err.println("elementCreated: Unable to load class " + className);
            return null;
        } else {
            if (jiglooPlugin.DEBUG)
                System.out.println("elementCreated: loaded class " + className);
        }

        FormComponent fc = null;
        boolean reassigned = false;
        if (fc == null) {
            if (!isFieldDec) {
                fc = findFormComponent(fieldName);
                if (fc != null && fc.isAssigned() 
                		&& !elementAssignments.containsValue(node) 
                		&& !fc.isInline()) {
                    //TODO really need to do this?
                    fc.setComponent(null);
                    setFormComponentName(fc, getNextAvailableName(fieldName + "::RA"));
                    fc = null;
                    reassigned = true;
                }
            }
        }
        if (fc == null && !reassigned) {
            fc = editor.getComponentByName(fieldName);
        }

        int mod = 0;
        if (Modifier.isPublic(modifiers))
            mod |= FormComponent.MOD_PUBLIC;
        if (Modifier.isProtected(modifiers))
            mod |= FormComponent.MOD_PROTECTED;
        if (Modifier.isPrivate(modifiers))
            mod |= FormComponent.MOD_PRIVATE;
        if (Modifier.isStatic(modifiers))
            mod |= FormComponent.MOD_STATIC;
        if (Modifier.isFinal(modifiers))
            mod |= FormComponent.MOD_FINAL;

        if (!ClassUtils.isVisual(cls)) {
            try {
                if (jiglooPlugin.canUseSwing()
                        && jiglooPlugin.canUseGroupLayout()
                        && (GroupLayoutUtils.isGroupLayoutGroup(cls))) {
                    Object val = getValue(exp, false);
                    putField(fieldName, val);
                } else if (ClassUtils.isResource(cls) || ClassUtils.isLayout(cls) || ClassUtils.isLayoutData(cls)) {

                    Object val = getValue(exp, false);
                    if (ClassUtils.isLayoutData(cls))
                        val = getWrapper(cls, val, fieldName, null);
                    putField(fieldName, val);

                } else {
                    Object val = getValue(exp, true);
                    Constructor con = null;
                    Object[] params = null;
                    String code = null;
                    ConstructorHolder ch = null;
                    if (val instanceof ConstructorHolder) {
                        ch = (ConstructorHolder) val;
                        con = ch.getConstructor();
                        params = ch.getParams();
                        code = ch.getCode();
                        cls = con.getDeclaringClass();
                        className = cls.getName();
                    }

                    if (fc == null)
                        fc = (FormComponent) fields.get(fieldName);
                    if (fc == null) {
                        fc = FormComponent.newFormComponent(editor, className);
                    }

                    fc.setEditor(editor);

                    if (modifiers != 0)
                        fc.setModifier(mod);

                    fc.setBlockName(blockStr);

                    fc.setName(fieldName);
                    fc.setExistsInCode(true);
                    if (className != null) {
                        if (!className.equals(fc.getClassName()))
                            fc.setClassName(className);
                    }
                    fc.setClassType(cls);

                    putField(fieldName, fc);

                    if (con != null) {
                        fc.setConstructor(con, params, code, ch);
                        setAssignmentNode(fieldName, ch.getExpression());
                        fc.setAssigned(true);
                    } else {
                        if (val != null) {
                            if (val instanceof FormComponent) {
                                reassignAs((FormComponent) val, fc, exp);
                            } else {
                                fc.setAssigned(true);
                                fc.setNonVisualObject(val);
                            }
                        }
                    }
                    if (ch != null) {
                        createInlineChildren(fc, ch.getParams());
                    }
                    String[] props = ConstructorManager.getProperties(cls, params);
                    if (props != null) {
                        for (int i = 0; i < props.length; i++) {
                            String pName = props[i];
                            fc.setPropertyValue(pName, params[i]);
                        }
                        //                        fc.setConstructor(null, null, null);
                    }
                }
            } catch (Throwable e) {
                jiglooPlugin.handleError(e, "ERROR creating " + cls + " for " + fieldName);
                //e.printStackTrace();
            }
            return fc;
        }

        //now handle visual case

        if (fc == null) {
            fc = FormComponent.newFormComponent(editor, className);
        }

        if (modifiers != 0)
            fc.setModifier(mod);

        fc.setEditor(editor);

        //if(isFieldDec)
        fc.setBlockName(blockStr);

        fc.setName(fieldName);

        if (className != null && !className.equals(fc.getClassName())) {
            fc.setClassName(className);
        }

        //        System.out.println("CREATING VISUAL FC " + fc.getName() + " " + cls);

        putField(fieldName, fc);

        fc.setClassType(cls);

        fc.setExistsInCode(true);

        fc.setAssigned(false);

        if (exp instanceof ClassInstanceCreation) {
            ClassInstanceCreation cic = (ClassInstanceCreation) exp;
            //check if the constructor is of a different class from the declaration
            //(eg, Component jsp = new JScrollPane();
            ITypeBinding tb = resolveTypeBinding(cic.getName());
            if (tb != null) {
                String cn2 = getFullClassName(tb);
                Class cls2 = editor.loadClass(cn2);
                if (cls2 != null) {
                    if (cn2 != null && !cn2.equals(fc.getClassName())) {
                        cls = cls2;
                        className = cn2;
                        fc.setClassName(className);
                        fc.setClassType(cls);
                    }
                }
            }
            fc.setAssigned(true);
            List args = cic.arguments();
            
            if (fc.isSwing() || fc.isCWT()) {
            
            	if (args.size() > 0) {
                    Object[] params = getParams(args);

                    createInlineChildren(fc, params);

                    Constructor constr = ConstructorManager.findConstructor(cls, params);
                    //version 3.9.3
                    if (constr == null)
                        params = null;

                    String[] props = ConstructorManager.getProperties(cls, params);
                    if (props != null) {
                        for (int i = 0; i < props.length; i++) {
                            String pName = props[i];
                            if (pName.equals("layout")) {
                                LayoutWrapper lw = new LayoutWrapper(fc);
                                lw.setObject(params[i]);
                                lw.setSet(true);
                                fc.setLayoutWrapper(lw);
                            } else if (pName.equals("viewport")) {
                                FormComponent fcarg = getFormComponentFromParam(params[i]);
                                if (fcarg != null)
                                    fc.addChild(fcarg);
                            } else if (pName.equals("leftComponent")) {
                                FormComponent fcarg = getFormComponentFromParam(params[i]);
                                if (fcarg != null) {
                                    fc.addChild(fcarg);
                                    fcarg.getLayoutDataWrapper().setObject(JSplitPane.LEFT);
                                }
                            } else if (pName.equals("rightComponent")) {
                                FormComponent fcarg = getFormComponentFromParam(params[i]);
                                if (fcarg != null) {
                                    fc.addChild(fcarg);
                                    fcarg.getLayoutDataWrapper().setObject(JSplitPane.RIGHT);
                                }
                            } else {
                                fc.setPropertyValue(pName, params[i]);
                                fc.setSetProperty(pName);
                                fc.setPropertyValueCode(pName, getCodeForNode(args.get(i)));
                            }
                        }
                        if (fc.isCWT()) {
                            fc.setConstructor(constr, params, getCodeForNode(exp));
                        } else {
                            //use the default constructor if we have parameters
                            //since we have assimilated the parameters and
                            // don't need to use them. Also, don't save constructor
                            //code.
                            fc.setConstructor(null, null, null);
                        }

                    } else {
                        fc.setConstructor(constr, params, getCodeForNode(exp));
                    }
                }
            } else {
                //SWT widget created
                if (cic.arguments().size() > 0 
                		&& (ClassUtils.isSWTWidget(cls) || ClassUtils.isJFace(cls))) {
                    FormComponent par = null;
                    String styleStr = "SWT.NULL";

                    Object styleVal = null;

                    if (cic.arguments().size() > 1) {
                        styleStr = cic.arguments().get(1).toString();

                        styleVal = getValue((Expression) cic.arguments().get(1));
                        if (styleVal instanceof FormComponent)
                            styleVal = ((FormComponent) styleVal).getObject(true);

                    }

                    String parName = cic.arguments().get(0).toString();

                    //handles tableViewer.getTable() - but not well!!!
                    if (parName.indexOf(".get") > 0) {
                        String parName1 = JiglooUtils.getQualifier(parName);
                        par = findFormComponent(parName1);
                        if (par != null && par.isJFaceViewer())
                            parName = parName1;
                    }

                    //this will create the root component's inherited elements
                    if (parName.equals("this") || parName.equals("super")) {
                        par = getRootComponent();
                    } else {
                        par = findFormComponent(parName);
                    }

                    int style = SWT.NULL;
                    if (styleVal instanceof Integer)
                        style = ((Integer) styleVal).intValue();
                    else
                        style = getStyle(styleStr);

                    if (par == null) {
                        if (jiglooPlugin.DEBUG_EXTRA)
                            System.out.println("PARENT " + parName + " not declared yet");
                    } else {
                        boolean isChild = true;

                        if (fc.isSubclassOf(Dialog.class))
                            isChild = false;
                        if(ItemManager.isNotChild(par, fc))
                        	isChild = false;
                        if (fc.isSubclassOf(Menu.class) && style == SWT.BAR)
                            isChild = false;

                        if (fc.isSubclassOf(Shell.class) && par.isSubclassOf(Shell.class))
                            isChild = false;

                        if (isChild) {
                            par.addChild(fc);
                        }

                    }
                    fc.setStyle(style);
                    fc.setStyleString(styleStr);
                    FormComponent rootFC = getRootComponent();
                    if (rootFC != null && rootFC.isJFaceForm()) {
                        fc.setInMainTree(true);
                        fc.setAssigned(true);
                        fc.setExistsInCode(true);
                        fc.populateControls(par.getControl(), editor, false);
                    }
                    if(args.size() > 2) {
                        Object[] params = getParams(args);
                        Constructor constr = ConstructorManager.findConstructor(cls, params);
                        if (constr != null)
                        	fc.setConstructor(constr, params, getCodeForNode(exp));
                    }
                }
            }
        } else if (exp instanceof MethodInvocation) {
            //handle getViewport, getContentPane etc
            handleMethodInvocation((MethodInvocation) exp, fieldName);
            FormComponent fct = findFormComponent(fieldName);
            if (fct != null) {
                fct.setFactoryElement(true);
                fc = fct;
            }
            fc.setAssigned(true);
        } else if (exp instanceof SuperMethodInvocation) {
            //handle getViewport, getContentPane etc
            handleSuperMethodInvocation((SuperMethodInvocation) exp, fieldName);
            fc.setAssigned(true);
        } else if (exp instanceof NullLiteral) {
            fc.setAssigned(false);
        } else if (exp != null) {
            addToLog(exp);
        }
        if (!isFieldDec && fc.isAbstractFormBuilder()) {
            fc.refreshBuilder();
        }

        if (fc.isJFaceForm()) {
            fc.getEclipseForm();
            fc = getRootComponent();
        }
        return fc;
        //System.out.println("ADDED FIELD " + fc);
    }

    private void renameNode(HashMap map, String oldName, String newName) {
        Object node = map.get(oldName);
        if (node != null) {
            map.remove(oldName);
            map.put(newName, node);
        }
    }

    /**
     * @param fc
     * @param newName
     */
    private void setFormComponentName(FormComponent fc, String newName) {
        String oldName = fc.getName();
        if (oldName != null) {
            renameNode(elementAssignments, oldName, newName);
            renameNode(elementConnections, oldName, newName);
            renameNode(setPropMethods, oldName, newName);
            renameNode(fields, oldName, newName);
        }
        fc.setName(newName, oldName != null);
    }

    /**
     * @param fc
     * @param params
     */
    private void createInlineChildren(FormComponent fc, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                Object p = params[i];
                if (p instanceof ConstructorHolder) {
                    ConstructorHolder ch = (ConstructorHolder) p;
                    //v4.0M2
                    //sometimes arguments are layouts - don't want to create FCs for layouts!
                    if (!ch.isLayout()) {
                        FormComponent fc2 = createInlineFormComponent(fc, ch);
                        setAssignmentNode(fc2.getName(), ch.getExpression());
                        //                	setConnectionNode(fc2.getName(), ch.getExpression());
                        fc.addChild(fc2);
                    }
                }
            }
        }
    }

    private FormComponent getFormComponentFromParam(Object param) {
        FormComponent fcarg = null;
        if (param instanceof FormComponent) {
            fcarg = (FormComponent) param;
        } else if (param instanceof String) {
            fcarg = findFormComponent((String) param);
        }
        return fcarg;
    }

    private int getStyle(String styleStr) {
        styleStr = JiglooUtils.replace(styleStr, " ", "");
        String[] styles = JiglooUtils.split("|", styleStr);
        int style = 0;
        for (int i = 0; i < styles.length; i++) {
            int pstyle = 0;
            try {
                styles[i] = JiglooUtils.replace(styles[i], "org.eclipse.swt.SWT.", "");
                styles[i] = JiglooUtils.replace(styles[i], "SWT.", "");
                Field fld = SWT.class.getField(styles[i]);
                pstyle = fld.getInt(null);
            } catch (Throwable e) {
                try {
                    pstyle = Integer.parseInt(styles[i]);
                } catch (Throwable e2) {
                    System.err.println("No SWT style " + styles[i]);
                    //                    e.printStackTrace();
                    continue;
                }
            }
            style |= pstyle;
        } //System.out.println("GOT STYLE "+style+" for "+styleStr);
        return style;
    }

    private void analyseBlockForReturnObj(Block block, String methodName) {
        analyseBlockForReturnObj(block, methodName, null, null);
    }

    private void analyseBlockForReturnObj(Block block, String methodName, List args, Expression mic) {
        if (block == null)
            return;
        //System.out.println("ANALYSING " + methodName);
        List stmts = block.statements();

        //look for return statement first
        for (Iterator it = stmts.iterator(); it.hasNext();) {
            Statement stmt = (Statement) it.next();

            if (stmt instanceof VariableDeclarationStatement) {

            }

            if (stmt instanceof ReturnStatement) {
                ReturnStatement ret = (ReturnStatement) stmt;
                Expression exp = ret.getExpression();
                if (exp instanceof NullLiteral)
                    continue;
                if (exp instanceof SimpleName) {
                    //                    System.out.println("analyseBlockForReturnObj: FOUND " + methodName+", "+exp);
                    String sn = exp.toString();
                    if (isCWT()) {
                        Object obj = findObject(fields, sn);
                        putMethodReturn(methodName, obj);
                        return;
                    }
                    if (args == null) {
                        parseMethod(methodName + "_");
                    } else {
                        parseMethod(getMethodNameKey((MethodInvocation) mic), args, mic);
                    }
                    sn = "%" + (blockNum - 1) + "." + blockStr + sn;
                    sn = resolveName(sn);
                    if (fields.containsKey(sn)) {
                        putMethodReturn(methodName, fields.get(sn));
                    }
                    return;
                } else if (exp instanceof ClassInstanceCreation) {
                    if (args == null) {
                        parseMethod(methodName + "_");
                    } else {
                        parseMethod(getMethodNameKey((MethodInvocation) mic), args, mic);
                        //                		parseMethod(getMethodNameKey(methodName, args), args, mic);
                    }
                    Object val = getValue(exp, true);
                    if (val instanceof ConstructorHolder) {
                        ConstructorHolder ch = (ConstructorHolder) val;
                        FormComponent fc = createInlineFormComponent(null, ch);
                        String sn = fc.getName();
                        if (fields.containsKey(sn)) {
                            putMethodReturn(methodName, fields.get(sn));
                        }
                    }
                    return;
                } else if (exp instanceof MethodInvocation) {
                    if (args == null) {
                        parseMethod(methodName + "_");
                    } else {
                        parseMethod(getMethodNameKey((MethodInvocation) mic), args, mic);
                    }

                    //parse exp in the block-context of this method
                    String oldBS = blockStr;
                    blockStr = "%" + (blockNum - 1) + "." + blockStr;
                    Object val = getValue(exp);
                    blockStr = oldBS;

                    if (val instanceof String) {
                        String sn = (String) val;
                        if (fields.containsKey(sn)) {
                            putMethodReturn(methodName, fields.get(sn));
                        }
                    }
                    return;
                }
            }
        }
        //if not found, look inside any enclosing blocks
        for (Iterator it = stmts.iterator(); it.hasNext();) {
            Statement stmt = (Statement) it.next();
            if (stmt instanceof TryStatement) {
                analyseBlockForReturnObj(((TryStatement) stmt).getBody(), methodName);
            } else if (stmt instanceof Block) {
                analyseBlockForReturnObj((Block) stmt, methodName);
            }
        }
    }

    private void analyseBlockForReturnFC(Block block) {
        if (block == null)
            return;
        //System.out.println("ANALYSING " + methodName);
        List stmts = block.statements();
        
        //look for return statement first
        for (Iterator it = stmts.iterator(); it.hasNext();) {
        	Statement stmt = (Statement) it.next();
        	if (stmt instanceof ReturnStatement) {
        		handleStatement(stmt);
        	} else if (stmt instanceof TryStatement) {
        		analyseBlockForReturnFC(((TryStatement) stmt).getBody());
        	} else if (stmt instanceof Block) {
        		analyseBlockForReturnFC((Block) stmt);
        	}
        }
    }

    /**
     * Checks that the objects returned by the getter methods are
     * actually assigned in those methods. If so, adds them to the
     * getters HashMap.
     */
    private void verifyGetterMethods() {
        Iterator it = methodReturns.keySet().iterator();
        while (it.hasNext()) {
            String mn = (String) it.next();
            MethodDeclaration md = (MethodDeclaration) getMethodNode(mn);
            IFormPropertySource fps = getReturnValue(mn);
            if (fps == null)
                continue;
            String objName = fps.getName();
            ASTNode assNode = getAssignment(fps);

            if (md != null && assNode != null) {
                int mstrt = md.getStartPosition();
                int mend = mstrt + md.getLength();
                int astrt = assNode.getStartPosition();
                if (astrt < mstrt || astrt > mend) {
                } else {
                    getters.put(mn, objName);
                    if (fps instanceof FormComponent) {
                        inVEMode = true;
                    }
                }
            } else {
                if (jiglooPlugin.DEBUG)
                    System.out.println("NODES NOT FOUND FOR " + mn);
            }
        }
    }

    public void removeField(String name) {
        if (name == null)
            return;
        fields.remove(name);
        name = JiglooUtils.getUnqualifiedName(name);
        basicFieldNames.remove(name);
    }

    public void putField(String fieldName, Object field) {
        if (fieldName == null)
            return;
        fields.put(fieldName, field);
        fieldName = JiglooUtils.getUnqualifiedName(fieldName);
        if (!basicFieldNames.contains(fieldName))
            basicFieldNames.add(fieldName);
    }

    private static Object getKeyForValue(HashMap map, Object value) {
        if (map.containsValue(value)) {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (map.get(key).equals(value)) {
                	return key;
                }
            }
        }
        return null;
    }
    
    public String convertToMethod(String fieldName, String nameInCode, boolean nullOK) {
    	String key = (String) getKeyForValue(getters, fieldName);
    	if(key != null) {
            if (key.endsWith("_"))
                key = key.substring(0, key.length() - 1);
            if (nullOK)
                return key;
            return key + "()";
    	}
        if (nullOK)
            return null;
        return nameInCode;
    }

    private void handleBlock(Block block) {
        if (block == null)
            return;
        if (isHidden(block))
            return;
        String oldBS = blockStr;
        blockStr = "%" + blockNum + "." + blockStr;
        //System.out.println("+++ANALYSING BLOCK " + blockStr);
        int tmpBN = blockNum;
        blockNum = 0;
        List stmts = block.statements();
        for (Iterator it = stmts.iterator(); it.hasNext();) {
            handleStatement((Statement) it.next());
        }
        blockStr = oldBS;
        blockNum = tmpBN + 1; //System.out.println("===ANALYSING
        // BLOCK - END " + blockStr);
    }

    private int blockNum = 0;

    private String blockStr = "";

    private Object getRootMethodObject(Expression mic) {
        mic = EvaluationUtils.removeCast(mic);
        Expression exp = null;
        if (mic instanceof MethodInvocation)
            exp = ((MethodInvocation) mic).getExpression();
        //        else if(mic instanceof SuperMethodInvocation)
        //            return rootObject;
        if (exp instanceof MethodInvocation)
            return getRootMethodObject((MethodInvocation) exp);
        if (exp instanceof SimpleName)
            return findObject(fields, exp.toString());
        return null;
    }

    /**
     * Returns the Object whose method is being called in the given expression
     */
    private FormComponent getMethodObject(Expression exp) {
        exp = EvaluationUtils.removeCast(exp);
        if (exp instanceof MethodInvocation) {
            MethodInvocation mi = (MethodInvocation) exp;
            Expression e2 = mi.getExpression();
            if (e2 instanceof MethodInvocation) {
                e2 = ((MethodInvocation) e2).getExpression();
            } else if (e2 instanceof SuperMethodInvocation) {
                //eg, exp = super.getJPanel().getSize(), e2 = super.getJPanel()
                return getThisComponent();
            }
            if (e2 == null)
                return getThisComponent();
            String mo = e2.toString();
            if(e2 instanceof MethodInvocation && mo.endsWith("()")) {
                //if exp is a property getter method, method might not yet have been
                //parsed for a return value, so parse it now so that the following
                //getFormComponent(mo) will not miss a value
                String mname = mo.substring(0, mo.length()-2);
                MethodDeclaration mdec = getMethodDeclaration(mname+"_");
                if(!parsedMethods.contains(mdec)) {
                    parseMethod(mdec, mname);
                }
            }
            return getFormComponent(mo);
        } else if (exp instanceof SuperMethodInvocation) {
            return getThisComponent();
        } else {
            return getThisComponent();
        }
    }

    public FormComponent getThisComponent() {
    	FormComponent fc = getRootComponent();
    	if(fc.getName().equals("this"))
    		return fc;
		Object thisObj = findObject(fields, "this");
		if(thisObj instanceof FormComponent)
			return (FormComponent) thisObj;
		return null;
	}

	private Class getReturnType(String methodName) {
        MethodDeclaration mdec = getMethodDeclaration(methodName + "_");
        if (mdec == null)
            return null;
        ITypeBinding tb = resolveTypeBinding(mdec.getReturnType());
        if (tb != null) {
            return editor.loadClass(getFullClassName(tb));
        }
        return null;
    }

    private ASTNode currentStatement = null;

    private void handleStatement(Statement stmt) {
        try {
            currentStatement = stmt;
            if (stmt == null || isHidden(stmt))
                return;
//            System.out.println("PARSING STMT " +  stmt.toString().replaceAll("\n", " ") + ", " + stmt.getClass());

            String msg = stmt.toString();
            
            if (msg.length() > 30)
                msg = msg.substring(0, 30) + "...";
            editor.setWaitLabelMsg("Parsing:\n  " + msg);

            if (stmt instanceof ReturnStatement) {
                Expression exp = ((ReturnStatement) stmt).getExpression();
                if (exp instanceof NullLiteral || exp == null)
                    return;
                FormComponent fc = null;
                if (exp instanceof SimpleName) {
                    fc = findFormComponent(((SimpleName) exp).getIdentifier());
                }
                if (fc == null) {
                    Object val = getValue(exp, true);
                    if (val instanceof String) {
                        fc = findFormComponent((String) val);
                    } else if (val instanceof ConstructorHolder) {
                        fc = createInlineFormComponent(null, (ConstructorHolder) val);
                    } else if (val instanceof FormComponent) {
                        fc = (FormComponent) val;
                    } else if (val instanceof Component) {
                        fc = createFormComponent(null, (Component) val, getCodeForNode(exp));
                    }
                    if (fc == null && val != null) {
                        fc = createFormComponent(val, getCodeForNode(exp));
                        fc.setMethodReturnValue(true);
                    }
                }
                if (fc == null) {
                    Class retType = getReturnType(methodBeingParsed);
                    if (retType != null && isSwing() &&  Cacher.isAssignableFrom(Component.class, retType)) {
                        JLabel lab = new JLabel("Error parsing: " + stmt + " in " + methodBeingParsed);
                        fc = createFormComponent(null, lab, getCodeForNode(exp));
                    }
                }

                if (fc != null) {
                    //ver 3.9.5 - since we want to only take the first return value parsed - ideally
                    //we would stop parsing the method as soon as a return statement is parsed...
                    if (!methodReturns.containsKey(methodBeingParsed))
                        putMethodReturn(methodBeingParsed, fc);
                } else {
                    if (!methodReturns.containsKey(methodBeingParsed))
                        putMethodReturn(methodBeingParsed, "null");
                    addToLog("Info: Unable to handle return: " + stmt + ", for method " + methodBeingParsed);
                }
                return;

            } else if (stmt instanceof TryStatement) {
                TryStatement tstmt = (TryStatement) stmt;
                handleBlock(tstmt.getBody());
            } else if (stmt instanceof Block) {
                handleBlock((Block) stmt);
            } else if (stmt instanceof IfStatement) {
                Object val = getValue(((IfStatement) stmt).getExpression());
                if (val == null || val.equals(Boolean.TRUE)) {
                    handleStatement(((IfStatement) stmt).getThenStatement());
                } else {
                    handleStatement(((IfStatement) stmt).getElseStatement());
                }
            } else if (stmt instanceof ForStatement) {
            	handleForStatement((ForStatement)stmt);
            } else if (stmt instanceof WhileStatement) {
            	handleWhileStatement((WhileStatement)stmt);
            } else if (stmt instanceof ExpressionStatement) {
                ExpressionStatement estmt = (ExpressionStatement) stmt;
                //System.out.println("GOT EXPRESSION " + estmt + ", "
                //     + estmt.getExpression().getClass());
                Expression exp = estmt.getExpression();
                handleExpression(exp);
            } else if (stmt instanceof VariableDeclarationStatement) {
                VariableDeclarationStatement vdec = (VariableDeclarationStatement) stmt;
                handleFieldDec(vdec.getType(), vdec.fragments(), stmt, false, vdec.getModifiers());
            } else if (stmt instanceof Block) {
                Block blk = (Block) stmt;
                handleBlock(blk);
                //} else if (stmt instanceof ForStatement) {
                //ForStatement fs = (ForStatement)stmt;
                //System.out.println("GOT FOR STMT
                // "+fs.initializers().get(0)+", "+fs.getExpression()+",
                // "+fs.updaters().get(0)+", "+fs.getBody());
                //handleStatement(fs.getBody());
            } else if (stmt instanceof SuperConstructorInvocation) {
                SuperConstructorInvocation sci = (SuperConstructorInvocation) stmt;
                Object[] params = getParams(sci.arguments());
                String[] props = ConstructorManager.getProperties(getSuperClass(), params);
                if (props != null) {
                    FormComponent rc = getRootComponent();
                    for (int i = 0; i < props.length; i++) {
                        String pName = props[i];
                        if (pName.equals("layout")) {
                            LayoutWrapper lw = new LayoutWrapper(rc);
                            lw.setObject(params[i]);
                            lw.setSet(true);
                            rc.setLayoutWrapper(lw);
                        } else {
                            rc.setPropertyValue(pName, params[i]);
                        }
                    }
                }
            } else {
                //                if (jiglooPlugin.DEBUG_EXTRA)
                addToLog("Statement not handled " + stmt + ", " + stmt.getClass());
            }
        } catch (Throwable e) {
            addToLog("Error analysing " + stmt);
            e.printStackTrace();
            addToLog(JiglooUtils.getStackTrace(e));
        }
    }

    private void handleForStatement(ForStatement forStmt) {
    	Expression init = (Expression) forStmt.initializers().get(0);
    	Expression updater = (Expression) forStmt.updaters().get(0);
    	Expression cond = forStmt.getExpression();
    	Object val;
    	if(init instanceof VariableDeclarationExpression) {
    		VariableDeclarationExpression vde = (VariableDeclarationExpression) init;
            handleFieldDec(vde.getType(), vde.fragments(), vde, true, vde.getModifiers());
    	}
    	val = getValue(cond);
    	Object newVal = null;
    	int count = 0;
    	while(Boolean.TRUE.equals(val)) {
    		handleBlock((Block) forStmt.getBody());
    		handleExpression(updater);
    		newVal = getValue(cond);
    		if(newVal == null || count > 20)
    			break;
    		count++;
    		val = newVal;
    	}
	}
    
    private void handleWhileStatement(WhileStatement forStmt) {
    	Expression cond = forStmt.getExpression();
    	Object val = getValue(cond);
    	Object newVal = null;
    	int count = 0;
    	while(Boolean.TRUE.equals(val)) {
    		handleBlock((Block) forStmt.getBody());
    		newVal = getValue(cond);
    		if(newVal == null || count > 20)
    			break;
    		count++;
    		val = newVal;
    	}
	}

	private void handleExpression(Expression exp) {
    	exp = EvaluationUtils.removeCast(exp);
    	if (exp instanceof MethodInvocation) {
    		MethodInvocation mic = (MethodInvocation) exp;
    		handleMethodInvocation(mic, null);
    	} else if (exp instanceof SuperMethodInvocation) {
    		SuperMethodInvocation mic = (SuperMethodInvocation) exp;
    		handleSuperMethodInvocation(mic, null);
    	} else if (exp instanceof Assignment) {
    		Assignment ass = (Assignment) exp;
    		handleAssignment(ass);
    	} else if (exp instanceof PostfixExpression) {
    		handlePostfixExpression((PostfixExpression)exp);
    	} else if (exp instanceof ClassInstanceCreation) {
    		handleClassInstanceCreation((ClassInstanceCreation) exp, null, true);
    	} else {
    		addToLog("Expression not handled " + exp + ", " + exp.getClass());
    	}
    }

	private Object handlePostfixExpression(PostfixExpression exp) {
		PostfixExpression pfe = (PostfixExpression) exp;
		Object op = pfe.getOperand();
		if (op instanceof SimpleName) {
			String name = blockStr + op.toString();
			name = resolveName(name);
			Object field = findObject(fields, name);
			if (field != null) {
				Object origField = field;
				if (field instanceof Integer) {
					if (pfe.getOperator().equals(PostfixExpression.Operator.INCREMENT)) {
						field = new Integer(((Integer) field).intValue() + 1);
					} else if (pfe.getOperator().equals(PostfixExpression.Operator.DECREMENT)) {
						field = new Integer(((Integer) field).intValue() - 1);
					}
				}
				fields.put(name, field);
				return origField;
			}
		}
		return null;
	}

	private ITypeBinding resolveTypeBinding(Name typeName) {
        ITypeBinding tb = typeName.resolveTypeBinding();
        if (tb == null)
            tb = new SimpleTypeBinding(typeName.toString(), imports);
        return tb;
    }

    private ITypeBinding resolveTypeBinding(Type typeName) {
        ITypeBinding tb = null;
        try {
            tb = typeName.resolveBinding();
        } catch (Throwable t) {
            System.err.println("Error (1) in resolveTypeBinding " + typeName + ", " + t);
        }
        try {
            if (tb == null) {
                tb = new SimpleTypeBinding(typeName.toString(), imports);
            }
        } catch (Throwable t) {
            System.err.println("Error (2) in resolveTypeBinding " + typeName + ", " + t);
        }
        return tb;
    }

    private void handleAssignment(Assignment ass) {
        if (isHidden(ass))
            return;

        Expression lhs = ass.getLeftHandSide();
        lhs = EvaluationUtils.removeCast(lhs);
        
        Expression rhs = ass.getRightHandSide();
        rhs = EvaluationUtils.removeCast(rhs);
        
        String lhsKey = null;
        FormComponent lhsFC = null;
        int arrayIndex = -1;

        if (lhs instanceof SimpleName) {

            lhsKey = ((SimpleName) lhs).getIdentifier();

        } else if (lhs instanceof ArrayAccess) {

            ArrayAccess aa = (ArrayAccess) lhs;
            if (aa.getArray() instanceof SimpleName) {
                Object index = getValue(aa.getIndex());
                if (index instanceof Integer) {
                    arrayIndex = ((Integer) index).intValue();
                    lhsKey = ((SimpleName) aa.getArray()).getIdentifier();
                }
            }
        }


        if (lhsKey != null) {

            lhsFC = findFormComponent(lhsKey);

            if (rhs instanceof MethodInvocation) {
                handleMethodInvocation((MethodInvocation) rhs, lhsKey);
                return;
            }

            if (rhs instanceof SuperMethodInvocation) {
                handleSuperMethodInvocation((SuperMethodInvocation) rhs, lhsKey);
                return;
            }

            if (rhs.toString().indexOf("getContentPane()") >= 0) {
                //TODO - a better way?
                if (lhsFC != null) {
                    FormComponent par = getMethodObject(rhs);
                    par.addChild(lhsFC);
                    lhsFC.setContentPane(true, true);
                }
            } else if (rhs.toString().indexOf("getViewport()") >= 0) {
                //TODO - a better way?
                if (lhsFC != null) {
                    FormComponent par = getMethodObject(rhs);
                    par.addChild(lhsFC);
                }
            } else if (rhs.toString().indexOf("getLayout()") >= 0) {
                //TODO - a better way?
                LayoutWrapper lw = (LayoutWrapper) getValue(rhs);
                if (lw != null) {
                    String lwName = blockStr + lhs;
                    lw.setName(lwName);
                    putField(lwName, lw);
                }
                return;
            } else if (rhs instanceof ClassInstanceCreation) {
                ClassInstanceCreation cic = (ClassInstanceCreation) rhs;
                Name typeName = cic.getName();
                ITypeBinding tb = resolveTypeBinding(typeName);
                if (tb == null) {
                    System.err.println("Unable to resolve " + typeName + " in assignment " + ass);
                } else {
                    String fieldName = blockStr + lhsKey;
                    fieldName = resolveName(fieldName);
                    if (arrayIndex != -1)
                        fieldName += "[" + arrayIndex + "]";
                    FormComponent elem = elementCreated(fieldName, rhs, getFullClassName(tb), false, 0, ass);
                    if (elem != null)
                        fieldName = elem.getName();
                    //System.out.println("ADDING ELEM ASSIGN " + fieldName + ", " + ass);
                    setAssignmentNode(fieldName, ass);
                    if (arrayIndex != -1) {
                    	lhsFC.setArrayElement(arrayIndex, elem);
                    }
                }
            } else {
                Object val = getValue(rhs, false, true); //v3.8.1
                if (val != null) {
                    if (lhsFC != null) {
                        if (arrayIndex != -1) {
                        	lhsFC.setArrayElement(arrayIndex, val);
                        } else {
                        	if (val instanceof FormComponent) {
                        		reassignAs(lhsFC, (FormComponent) val, ass);
                        	} else {
                        		lhsFC.setNonVisualObject(val);
                        	}
                        }
                    } else {
                    	String fieldName = findFieldName(lhsKey);
                    	if(fieldName == null) {
                    		System.out.println("Unable to find element " + lhsKey);
                    	} else {
                    		handleAssignmentOperator(fieldName, ass, val);
                    	}
                    }
                }
            }

        } else {
            if (lhs instanceof QualifiedName) {
                QualifiedName qn = (QualifiedName) lhs;
                String qual = blockStr + qn.getQualifier().toString();
                qual = resolveName(qual);
                String fieldName = qn.getName().toString();
                Object fieldVal = getValue(rhs);
                Object field = findObject(fields, qual);
                if (field != null) {
                    if (field instanceof IFormPropertySource) {
                        ((IFormPropertySource) field).setPropertyValue(fieldName, fieldVal);
                    } else {
                        setField(field, fieldName, fieldVal);
                    }
                }

                HashMap map = getPropMap(qual);
                map.put(fieldName, ass);
                //System.out.println("ADDING FIELD SETTER " + qual + "." +
                // fieldName);
            } else {
                if (jiglooPlugin.DEBUG_EXTRA) {
                    System.out.println("LHS=" + lhs + ", " + lhs.getClass());
                    System.out.println("RHS=" + rhs + ", " + rhs.getClass());
                }
            }
        }
    }

    private void handleAssignmentOperator(String fieldName, Assignment ass, Object val) {
		Object field = fields.get(fieldName);
		if(field != null) {
			Operator op = ass.getOperator();
			Class fcls = field.getClass();
			if(op.equals(Assignment.Operator.PLUS_ASSIGN) && fcls.equals(String.class)) {
				field = (String)val+(String)field;
			} else {
				double vnum = getNumber(val, 0.0);
				double fnum = getNumber(field, 0.0);
				if(op.equals(Assignment.Operator.PLUS_ASSIGN)) {
					fnum += vnum;
				} else if(op.equals(Assignment.Operator.MINUS_ASSIGN)) {
					fnum -= vnum;
				} else if(op.equals(Assignment.Operator.TIMES_ASSIGN)) {
					fnum *= vnum;
				} else if(op.equals(Assignment.Operator.DIVIDE_ASSIGN)) {
					fnum /= vnum;
				} else if(op.equals(Assignment.Operator.REMAINDER_ASSIGN)) {
					fnum %= vnum;
				} else {
					System.out.println("Need to handle Assignment "+ass);
				}
				if(fcls.equals(Integer.class))
					field = new Integer((int)fnum);
				else if(fcls.equals(Double.class))
					field = new Double(fnum);
				else if(fcls.equals(Float.class))
					field = new Float(fnum);
				else if(fcls.equals(Long.class))
					field = new Long((long)fnum);
				else if(fcls.equals(Short.class))
					field = new Short((short)fnum);
			}
		}
		fields.put(fieldName, field);
	}

	private static void setField(Object obj, String fName, Object value) {
        try {

            //            if(obj instanceof ConstructorHolder)
            //                obj = ((ConstructorHolder)obj).newInstance();

            Class cls = obj.getClass();
            Field fld = cls.getField(fName);
            fld.set(obj, value);
        } catch (Throwable e) {
            System.err.println("ERROR setting field " + fName + ", " + value + ", " + value.getClass() + ", " + obj);
        }
    }

    private static Object getField(Class cls, String fName) {
        try {
            Field fld = cls.getField(fName);
            Object val = fld.get(null);
            return val;
        } catch (Throwable e) {
            if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("ERROR getting field " + fName + " for class " + cls + " : " + e);
            //e.printStackTrace();
        }
        return null;
    }

    private Object[] getParams(List args) {
        return getParams(args, true);
    }

    private Object[] getParams(List args, boolean returnCon) {
        Object[] ps = new Object[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Expression ex = (Expression) args.get(i);
            ps[i] = getValue(ex, returnCon);
        }
        return ps;
    }

    public Object getValue(Expression exp) {
        return getValue(exp, false, false);
    }

    private Object getValue(Expression exp, boolean returnConstructor) {
        return getValue(exp, returnConstructor, false);
    }

    public Object getValue(Expression exp, boolean returnConstructor, boolean createIfNeeded) {
        //        returnConstructor = false;
//    	createIfNeeded = true;
        try {
            currentStatement = exp;

            if (exp == null) {
                return null;
            }

            exp = EvaluationUtils.removeCast(exp);
            if (exp instanceof NumberLiteral) {
                NumberLiteral nlit = (NumberLiteral) exp;
                String lit = nlit.getToken();
                if (lit.endsWith("f")) {
                    return new Float(EvaluationUtils.tidyNumberString(lit));
                } else if (lit.endsWith("L")) {
                    return new Long(EvaluationUtils.tidyNumberString(lit));
                } else if (lit.indexOf(".") >= 0) {
                    return new Double(EvaluationUtils.tidyNumberString(lit));
                } else {
                    return Integer.decode(EvaluationUtils.tidyNumberString(lit));
                }
            } else if (exp instanceof NullLiteral) {
                return null;
            } else if (exp instanceof StringLiteral) {
                StringLiteral nlit = (StringLiteral) exp;
                return nlit.getLiteralValue();
            } else if (exp instanceof BooleanLiteral) {
                BooleanLiteral nlit = (BooleanLiteral) exp;
                return new Boolean(nlit.toString());
            } else if (exp instanceof PrefixExpression) {
                PrefixExpression pe = (PrefixExpression) exp;
                PrefixExpression.Operator op = pe.getOperator();
                if (op == PrefixExpression.Operator.PLUS)
                    return getValue(pe.getOperand());
                else if (op == PrefixExpression.Operator.MINUS) {
                    Object val = getValue(pe.getOperand());
                    if (val instanceof Integer)
                        return new Integer(-((Integer) val).intValue());
                    else if (val instanceof Double)
                        return new Double(-((Double) val).doubleValue());
                    else if (val instanceof Float)
                        return new Float(-((Float) val).floatValue());
                    else if (val instanceof Short)
                        return new Short((short) -((Short) val).shortValue());
                    else if (val instanceof Long)
                        return new Long(-((Long) val).longValue());
                    else {
                        addToLog(exp);
                    }
                } else if (op == PrefixExpression.Operator.NOT) {
                    Object val = getValue(pe.getOperand());
                    if (val == null)
                        return null;
                    if (val.equals(Boolean.TRUE))
                        return Boolean.FALSE;
                    else if (val.equals(Boolean.FALSE))
                        return Boolean.TRUE;
                    else
                        addToLog(exp);
                } else {
                    addToLog(exp);
                }
            } else if (exp instanceof SimpleName) {
                String sn = blockStr + ((SimpleName) exp).getIdentifier();
                Object obj = findObject(fields, sn);
                if (obj == null) {
                    if (root != null) {
                        //in case the simple name is the name of an inherited object
                        obj = root.getChildByName(((SimpleName) exp).getIdentifier());
                    }
                    if (obj == null) {
                        String className = getFullClassName(resolveTypeBinding((SimpleName) exp));
                        Class cls = null;
                        if (className != null) {
                            cls = loadClass(className, null, false);
                        }
                        if (cls != null)
                            return cls;
                    }
                }
                if (jiglooPlugin.DEBUG)
                    System.out.println("GET VAL: SIMPLE NAME " + sn + " =  " + obj + " "
                            + (obj != null ? obj.getClass().getName() : ""));

                if (obj instanceof FormComponent) {
                    FormComponent fc = (FormComponent) obj;
                    if (fc.getNonVisualObject() != null)
                        return fc.getNonVisualObject();
                }

                if (obj != null)
                    return obj;
                if (jiglooPlugin.DEBUG)
                    System.err.println("UNABLE TO FIND SIMPLE NAME " + sn + ", in " + fields);
            } else if (exp instanceof QualifiedName || exp instanceof FieldAccess) {
                String fName = null;
                String qual = null;
                String className = null;
                if (exp instanceof QualifiedName) {
                    QualifiedName nlit = (QualifiedName) exp;
                    fName = nlit.getName().toString();
                    qual = nlit.getQualifier().toString();
                    Class cls = null;
                    className = getFullClassName(resolveTypeBinding(nlit.getQualifier()));
                    if (className != null) {
                        cls = loadClass(className, null, false);
                    }
                    if (cls == null) {
                        className = nlit.getQualifier() + "." + nlit.getName();
                        cls = loadClass(className, null, false);
                        if (cls != null)
                            return cls;
                    }
                } else {
                    FieldAccess nlit = (FieldAccess) exp;
                    fName = nlit.getName().toString();
                    qual = nlit.getExpression().toString();
                }
                if ("this".equals(qual) || "super".equals(qual)) {
                    String sn = blockStr + fName;
                    Object obj = findObject(fields, sn);
                    if (jiglooPlugin.DEBUG)
                        System.out.println("GET VAL: this.SIMPLE NAME " + sn + " =  " + obj);
                    if (obj != null)
                        return obj;
                }
                qual = resolveName(blockStr + qual);
                Object f = findObject(fields, qual);
                if (f == null)
                    f = findFormComponent(qual);
                if (f instanceof FormComponent) {
                    return ((FormComponent) f).getFieldValue(fName);
                } else if (f != null) {
                    return JiglooUtils.getFieldValue(f, fName);
                }
                Class cls = null;
                if (className != null)
                    cls = loadClass(className, null);
                if (jiglooPlugin.DEBUG)
                    System.out.println("GOT FIELD " + fName + ", " + cls + ", " + exp);
                if (cls != null) {
                    return getField(cls, fName);
                } else {
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("CAN'T HANDLE QN EXP: " + fName + ", " + cls + ", " + exp);
                }
            } else if (exp instanceof MethodInvocation) {
                MethodInvocation mic = (MethodInvocation) exp;
                String micStr = mic.toString();
                Expression mexp = mic.getExpression();
                mexp = EvaluationUtils.removeCast(mexp);

                List args = mic.arguments();
                //if invoking a method, don't want params to be ConstructorHolders
                //(or should we pass in the value of returnConstructor?)
                Object[] params = getParams(args, false);
                Class[] types = ConstructorManager.getTypes(params);
                String methodName = mic.getName().getIdentifier();
                Class methodClass = getClassForMethod(mic);

                Object mObj = null;

                //handle ResourceBundles but don't call getValue(mexp); for every mexp because
                //for GroupLayouts this can cause a *lot* of extra work!!!
                if (methodName.equals("getString") || methodName.equals("getBundle")) {
                    if (methodClass != null 
                            && methodClass.getName().equals(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".ResourceMap")
                            && params[0] instanceof String)
                        return editor.getBundleManager()
                                .getProperty(null, (String) params[0], String.class, null, null);
                    else
                        mObj = getValue(mexp);
                }
                
                if(harness != null) {
                	FormComponent parFC = null;
                	if(mexp != null) {
                		Object val = getValue(mexp);
                		if(val instanceof FormComponent)
                			parFC = (FormComponent) val;
                	}
                	Object ret = harness.handleMethod(this, parFC, methodName, methodClass, args);
                	if(ret != null)
                		return ret;
                }
                
                if (mObj instanceof Class) {
                    try {
                        if (mObj.equals(ResourceBundle.class) && methodName.equals("getBundle")
                                && params[0] instanceof String) {
                            return ResourceBundle.getBundle((String) params[0], Locale.getDefault(), editor
                                    .getClassLoader());
                        }
                        //static method call
                        Method m = JiglooUtils.findMethod((Class) mObj, methodName, types);
                        //System.out.println("GET EXT STR "+m);
                        if (m != null) {
                            return invokeMethod(m, (Object)null, params);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                } else if (mObj instanceof ResourceBundle && methodName.equals("getString")) {
                    return ((ResourceBundle) mObj).getString((String) params[0]);
                } else if (mObj != null && !(mObj instanceof FormComponent)) {
                    try {
                        Method m = JiglooUtils.findMethod(mObj.getClass(), methodName, types);
                        if (m != null) {
                            return invokeMethod(m, mObj, params);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

                if (mexp instanceof ClassInstanceCreation) {
                    Object ciObj = handleClassInstanceCreation((ClassInstanceCreation) mexp, null, false);
                    if (ciObj instanceof Class) {
                        jiglooPlugin.writeToLog("Error evaluating " + mexp);
                    } else if (ciObj != null) {
                        Object ciVal = invokeMethod(ciObj, methodName, params, types);
                        if (ciVal != null)
                            return ciVal;
                    } else {
                        jiglooPlugin.writeToLog("Error evaluating " + mexp);
                    }
                }

                if (methodName.startsWith("getResource") && mic.arguments().size() != 0) {
                    Object val = getValue((Expression) args.get(0));
                    if (val instanceof FormComponent) {
                        val = ((FormComponent) val).getNonVisualObject();
                    }
                    if (val instanceof String) {
                        return (String) val;
                        //                		return new IconWrapper((String)val, null);
                    }
                    //return args.get(0).toString();

                } else if (methodName.equals("getSystemColor") && params.length == 1 && params[0] instanceof Integer) {
                    return Display.getDefault().getSystemColor(((Integer) params[0]).intValue());

                } else if (methodName.equals("getImage") && mic.arguments().size() == 0) {
                    //hack for new ImageIcon(...).getImage()
                    String fileName = extractStringLiteral(mic.toString());
                    if (fileName != null)
                        return new ImageWrapper(fileName, editor);

                } else if (methodName.startsWith("get")) {
                    Object mexpVal = null;
                    FormComponent fc = null;
                    if (mexp == null) {
                        fc = getThisComponent();
                    } else {
                        fc = findFormComponent(mexp.toString());
                        //                        if(fc == null)
                        //                            mexpVal = getValue(mexp);
                    }
                    if (methodName.equals("get") && methodClass != null
                            &&  Cacher.isAssignableFrom(ActionMap.class, methodClass)) {
                        if (params[0] instanceof String)
                            return actionManager.getAction((String) params[0], getCodeForNode(exp));

                    } else if (methodName.equals("getLayout") && fc != null) {
                        return fc.getLayoutWrapper();

                    } else if ((methodName.equals("getName") || methodName.equals("getSimpleName")) 
                    		&& Class.class.equals(methodClass)) {
                        Object val = getValue(mexp);
                        if (val instanceof Class) {
                            if(methodName.equals("getSimpleName"))
                            	return JiglooUtils.getUnqualifiedName(((Class)val).getName());
                        	return ((Class) val).getName();
                        }
                        
                    } else if (methodName.equals("getClass") && fc != null) {
                    	if(fc.getName().equals("this"))
                    		return getMainClass();
                        return fc.getMainClass();

                    } else if ((methodName.equals("getPanel") || methodName.equals("getContainer")) && fc != null
                            && fc.isAbstractFormBuilder()) {
                        return fc;

                    } else if (mexp != null && mexp.toString().equals("ResourceManager")) {
                        needsResourceManager = true;

                    } else if (!methodName.equals("get")) {
                        String propName = JiglooUtils.deCapitalize(methodName.substring(3));
                        if (fc != null) {
                            if (fc.hasProperty(propName)) {
                                return fc.getRawPropertyValue(propName);
                            } else {
                                //this pre-empts getter methods - would need to call it after
                                //getters have been tested for!!
                                //                                return invokeMethod(fc.getObject(false), methodName, null, null);
                            }
                        } else if (mexpVal != null) {
                            //                            return invokeMethod(mexpVal, methodName, null, null);
                        }

                        //                        addToLog("Unable to handle method: "+exp);
                        if (jiglooPlugin.DEBUG_EXTRA)
                            System.err
                                    .println("UNABLE TO HANDLE METHOD " + methodName + ", fc=" + fc + ", " + propName);
                    }
                }

                //here when we are parsing eg "getJPanel1(String arg)" - returns
                //the String "jPanel1", not the FormComponent
                String methodKey = getMethodNameKey((MethodInvocation) mic);
                MethodDeclaration mdec = getMethodDeclaration(methodKey);
                if (mdec != null) {

                    if (parseMethod(mdec, methodName, args, mic)) {
                        if (methodReturns.containsKey(methodName)) {
                            return getReturnValue(methodName);
                        }
                        addToLog("Info: Method did not return a value " + mic);
                        return null;
                    } else {
                        addToLog("Info: Didn't parse method " + mic);
                    }
                }

                if (methodName.equals("getExternalizedString")) {
                    //System.out.println("GET EXT STR "+mic);
                    String key = (String) getValue((Expression) args.get(0));
                    Method m = JiglooUtils.findMethod(getMainClass(), methodName, new Class[] { String.class });
                    //System.out.println("GET EXT STR "+m);
                    if (m != null) {
                        return invokeMethod(m, (Object)null, new Object[] { key });
                    }
                } else if (methodName.equals("computeTrim")) {
                    try {
                        int x = ((Integer) getValue((Expression) args.get(0))).intValue();
                        int y = ((Integer) getValue((Expression) args.get(1))).intValue();
                        int w = ((Integer) getValue((Expression) args.get(2))).intValue();
                        int h = ((Integer) getValue((Expression) args.get(3))).intValue();
                        return new Rectangle(x, y, w, h);
                    } catch (Throwable e) {
                        //System.err.println("Problem processing computeTrim
                        // call");
                        return new Rectangle(0, 0, 100, 100);
                    }
                } else if (mexp instanceof QualifiedName || mexp instanceof SimpleName) {

                    //just use toString in case SWTResourceManager or
                    // ResourceManager can't
                    //be resolved.
                    String mexpStr = mexp.toString();
                    if (mexpStr.indexOf("ResourceManager") >= 0) {
                        if (mexpStr.indexOf("SWTResourceManager") >= 0)
                            usingSWTResMan = true;
                        if (methodName.equals("getImage")) {
                            return new ImageWrapper(extractStringLiteral(micStr), editor);
                            //return extractStringLiteral(micStr);
                        } else if (methodName.equals("getColor")) {
                            return getParams(args);
                        } else if (methodName.equals("getFont")) {
                            return getParams(args);
                        } else if (methodName.equals("getCursor")) {
                            return getParams(args);
                        }
                    }

                    ITypeBinding tb = null;
                    FormComponent fc = null;
                    Object field = null;
                    if (mexp instanceof QualifiedName) {
                        tb = resolveTypeBinding(((QualifiedName) mexp));
                    } else if (mexp instanceof SimpleName) {
                        SimpleName sn = (SimpleName) mexp;
                        String name = resolveName(blockStr + sn);
                        fc = findFormComponent(name);
                        if (fc == null) {
                            field = fields.get(name);
                            if (ClassUtils.isGroupLayout(field) || field instanceof LayoutGroup) {

                                if (valueCache.containsKey(exp))
                                    return valueCache.get(exp);
                                Object val = handleGroupLayoutMethodCall(mic, methodName, field);
                                if (val != null) {
                                    valueCache.put(exp, val);
                                    return val;
                                }

                            } else if (field != null) {
                                if (methodName.equals("toString"))
                                    return field.toString();
                                //                            	else {
                                //                            		addToLog(exp);
                                //                            	}
                            }
                        }
                        if (fc == null) {
                            tb = resolveTypeBinding(sn);
                        }
                    }

                    if (fc != null) {
                        Object res = null;
                        boolean invoked = false;
                        if (fc.isJFaceFormToolkit()) {
                            res = fc.invokeOnFormToolkit(methodName, params, mic, null);
                            invoked = true;
                        }
                        if (fc.isJFaceForm()) {
                            res = fc.invokeOnForm(methodName, params, mic, null);
                            invoked = true;
                        }
                        if (res instanceof FormComponent) {
                            FormComponent resFC = (FormComponent) res;
                            System.out.println("FORM COMP CREATED " + resFC + ", " + getCodeForNode(mic));
                            setConnectionNode(resFC.getName(), mic);
                        }
                        if (invoked)
                            return res;
                    }

                    if (tb != null && getFullClassName(tb) != null) {
                        String cn = getFullClassName(tb).toString();
                        if (jiglooPlugin.DEBUG)
                            System.out.println("GOT MIC, CN=" + cn + ", " + methodName + ", args=" + args);
                        Class cls = editor.loadClass(cn);
                        if (cls != null) {
                            //don't want to execute System.exit (nor any other System methods)
                            //JOptionPane.show... methods will cause hang
                            if (cls.equals(System.class)
                                    || (jiglooPlugin.canUseSwing() &&  Cacher.isAssignableFrom(JOptionPane.class, cls)))
                                return null;
                            try {
                                if (methodName.equals("getBundle") && cls.equals(ResourceBundle.class)) {
                                    return ResourceBundle.getBundle((String) params[0], Locale.getDefault(), editor
                                            .getClassLoader());
                                }

                                if (jiglooPlugin.getDefault().canMakeNVClass(cls)) {
                                    //check class can be created before calling static method on it
                                    if (cls.getName().equals("org.eclipse.swt.awt.SWT_AWT")) {
                                        FormComponent nfc = null;
                                        FormComponent par = null;
                                        FormComponent oldChild = null;
                                        if (methodName.equals("new_Frame")) {
                                            par = (FormComponent) params[0];
                                            oldChild = par.getNonInheritedChildAt(0);
                                            if (oldChild != null) {
                                                par.remove(oldChild);
                                                oldChild.setParent(null);
                                                oldChild.setExistsInCode(false);
                                                oldChild.setAssigned(false);
                                            }
                                            //                                			nfc = new FormComponent(par, "java.awt.Frame");
                                            //see comment below
                                            String nname = getNextAvailableName("frame:SWTAWT");
                                            nfc = editor.getComponentByName(nname);
                                            if (nfc == null)
                                                nfc = FormComponent.newFormComponent(editor, "java.awt.Frame");
                                            nfc.setName(nname);
//                                            nfc.setEditor(editor);
                                            nfc.setParent(par, true);
//                                            nfc.setClassName("java.awt.Frame");
                                            editor.addComponent(nfc);
                                        } else if (methodName.equals("new_Shell")) {
                                            par = (FormComponent) params[1];
                                            oldChild = par.getNonInheritedChildAt(0);
                                            if (oldChild != null) {
                                                par.remove(oldChild);
                                                oldChild.setParent(null);
                                                oldChild.setExistsInCode(false);
                                                oldChild.setAssigned(false);
                                                oldChild.setInMainTree(false);
                                            }
                                            //                                			nfc = createFormComponent(Shell.class, getCodeForNode(exp));
                                            //                                			nfc = new FormComponent(par, "org.eclipse.swt.widgets.Shell");
                                            //do the below instead - otherwise new FormComponent will call add(FormComponent)
                                            //and FormEditor.getNextAvailableName in editor.addChildComponent
                                            //which will assign a name, which is not good in a case like
                                            // Shell shell2 = SWT_AWT.new_Shell(...)

                                            String nname = getNextAvailableName("shell:SWTAWT");
                                            nfc = editor.getComponentByName(nname);
                                            if (nfc == null)
                                                nfc = FormComponent.newFormComponent(editor, "org.eclipse.swt.widgets.Shell");
                                            nfc.setName(nname);
                                            nfc.setEditor(editor);
                                            nfc.setParent(par, true);
                                            nfc.setClassName("org.eclipse.swt.widgets.Shell");
                                            editor.addComponent(nfc);
                                        }
                                        if (nfc != null) {
                                            nfc.setAssigned(true);
                                            nfc.setExistsInCode(true);
                                            return nfc;
                                        }
                                    }
                                    Method m = JiglooUtils.findMethod(cls, methodName, types);
                                    //                                    System.err.println("JCP INVOKING "+m+", "+exp);
                                    return getFormComponentFromMethod(m, field, params, exp);
                                } else {
                                    return null;
                                }

                            } catch (InvocationTargetException e) {
                                //                                if (jiglooPlugin.DEBUG_EXTRA)
                                System.err.println("Error invoking method " + methodName + " for " + cls + ", field="+field + ", mic="
                                        + mic + ", " + e + ": target= "
                                        + ((InvocationTargetException) e).getTargetException());
                            } catch (Throwable e) {
                                //                                if (jiglooPlugin.DEBUG_EXTRA)
                                System.err.println("Error invoking method " + methodName + " for " + cls + ", field="+field+", mic="
                                        + mic + ", " + e);
//                                e.printStackTrace();
                            }
                        } else {
                            //                            System.out.println("Unable to load class "+cn);
                        }
                    } else if (fc != null) {
                        Class cls = fc.getMainClass();
                        try {
                            if (cls.equals(Class.class)) {
                                if (fc.getNonVisualObject() instanceof Class)
                                    cls = (Class) fc.getNonVisualObject();
                                else if (fc.getNonVisualObject() != null)
                                    cls = fc.getNonVisualObject().getClass();
                            }
                            Method m = JiglooUtils.findMethod(cls, methodName, types);

                            //                            createIfNeeded = true; //v3.8.1
                            if (createIfNeeded)
                                fc.setInMainTree(true);
                            Object obj = fc.getObject(createIfNeeded);
                            
                            if(fc.isAbstractFormBuilder())
                            	obj = fc.getBuilder();
                            
                            if (m != null)
                                return invokeMethod(m, obj, params);
                            
                        } catch (Throwable e) {
                            if ( Cacher.isAssignableFrom(ResourceBundle.class, cls)) {
                                if (methodName.equals("getString"))
                                    return "$" + params[0] + "$";
                            }
                            if (jiglooPlugin.DEBUG_EXTRA)
                                System.err.println("Error invoking method " + methodName + " on FC " + fc + ": "
                                        + fc.getMainClass() + ": nvo=" + fc.getNonVisualObject() + ", mic=" + mic
                                        + ", " + e);
                            //e.printStackTrace();
                        }
                    }
                } else {
                    Object field = getRootMethodObject(mic);
                    if (ClassUtils.isGroupLayout(field) || field instanceof LayoutGroup) {
                        if (valueCache.containsKey(exp))
                            return valueCache.get(exp);
                        Object val = handleGroupLayoutMethodCall(mic, methodName, field);
                        if (val != null)
                            valueCache.put(exp, val);
                        return val;
                    }
                    if (mexp == null && mObj == null) {
                        System.out.println("Using root object to evaluate " + exp);
                        mObj = editor.getRootObject();
                        if (mObj != null) {
                            try {
                                Method m = JiglooUtils.findMethod(mObj.getClass(), methodName, types);
                                if (m != null) {
                                    return invokeMethod(m, mObj, params);
                                }
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }

                    addToLog(exp);
                }

            } else if (exp instanceof PostfixExpression) {
            	return handlePostfixExpression((PostfixExpression)exp);
            } else if (exp instanceof InfixExpression) {
            	return handleInfixExpression(exp);
            } else if (exp instanceof ParenthesizedExpression) {
                ParenthesizedExpression pe = (ParenthesizedExpression) exp;
                return getValue(pe.getExpression());
            } else if (exp instanceof ArrayInitializer) {
                ArrayInitializer ai = (ArrayInitializer) exp;
                if (jiglooPlugin.DEBUG)
                    System.out.println("GOT ARRAY INIT " + ai + ", " + ai.expressions().size());
                List exps = ai.expressions();
                if (exps.size() == 0)
                    return null;
                Object val = getValue((Expression) exps.get(0), returnConstructor);
                Object[] array = null;

                if (val instanceof Object[][][][])
                    array = new Object[exps.size()][][][][];
                else if (val instanceof Object[][][])
                    array = new Object[exps.size()][][][];
                else if (val instanceof Object[][])
                    array = new Object[exps.size()][][];
                else if (val instanceof Object[])
                    array = new Object[exps.size()][];
                else
                    array = new Object[exps.size()];

                int i = 0;
                for (Iterator iter = exps.iterator(); iter.hasNext();) {
                    Expression ex = (Expression) iter.next();
                    array[i++] = getValue(ex, returnConstructor);
                }
                return array;
            } else if (exp instanceof ArrayCreation) {
                ArrayCreation ac = (ArrayCreation) exp;
                ArrayInitializer ai = ac.getInitializer();
                Object val = null;

                //get constructors for array contents
                if (ai != null)
                    val = getValue(ai, true);

                Type et = ac.getType().getElementType();
                String tName = et.toString();
                if (et != null)
                    tName = getFullClassName(et.resolveBinding());

                if (jiglooPlugin.DEBUG)
                    System.out.println("GOT ARRAY CREATION " + tName + ", " + val + ", " + et);

                List dimList = ac.dimensions();
                int[] dims = new int[dimList.size()];
                for (int i = 0; i < dims.length; i++) {
                    Object d = getValue((Expression) dimList.get(i));
                    int d2 = 1;
                    try {
                    	if(d != null)
                    		d2 = Integer.parseInt(d.toString());
                    } catch (Throwable e) {
                    }
                    dims[i] = d2;
                }
                if (dims.length == 0)
                    dims = new int[] { 0 };
                if (val == null) {
                    //TODO handle multi-dimension arrays
                    Class cls = editor.loadClass(tName);
                    if (cls != null) {
                        Object a = Array.newInstance(cls, dims);
                        //System.out.println("ARRAY CREATION " + a);
                        a = convertArrayObjects(a, tName);
                        return a;
                    }
                }

                if (val instanceof Object[]) {
                    Object ret = convertArrayObjects(val, tName);
                    if (ret != null)
                        return ret;

                }

            } else if (exp instanceof ClassInstanceCreation) {
                return handleClassInstanceCreation((ClassInstanceCreation) exp, null, returnConstructor);
            
            } else if (exp instanceof ThisExpression) {
                //handles evaluation of "this"
            	if(harness != null) {
                	return editor.getRootObject();
            	}
            	
            	return getRootComponent();
                
            } else if (exp instanceof TypeLiteral) {
                TypeLiteral tl = (TypeLiteral) exp;
                ITypeBinding tb = resolveTypeBinding(tl.getType());
                String qName = getFullClassName(tb);
                if (qName != null) {
                    Class cls = loadClass(qName, null);
                    return cls;
                }
            } else if (exp instanceof CharacterLiteral) {
                CharacterLiteral cl = (CharacterLiteral) exp;
                return new Character(getCharValue(cl));
            } else if (exp instanceof ConditionalExpression) {
                ConditionalExpression ce = (ConditionalExpression) exp;
                Object val = getValue(ce.getExpression());
                if (Boolean.FALSE.equals(val)) {
                    return getValue(ce.getElseExpression());
                } else {
                    return getValue(ce.getThenExpression());
                }
            } else if (exp instanceof ArrayAccess) {
                ArrayAccess aa = (ArrayAccess) exp;
                Object array = getValue(aa.getArray());
                Object ind = getValue(aa.getIndex());
                if (array instanceof Object[]) {
                    int index = 0;
                    if (ind instanceof Integer)
                        index = ((Integer) ind).intValue();
                    Object[] arr = (Object[]) array;
                    if (arr.length > index)
                        return arr[index];
                    addToLog("Array index out of bounds: "+index+" >= "+arr.length+" in \""+exp+"\"");
                    return null;
                } else if(array instanceof ArrayHolder) {
                    int index = 0;
                    if (ind instanceof Integer)
                        index = ((Integer) ind).intValue();
                    Object[] arr = ((ArrayHolder) array).getFCArray();
                    if (arr.length > index)
                        return arr[index];
                    addToLog("Array index out of bounds: "+index+" >= "+arr.length+" in \""+exp+"\"");
                    return null;
                }
            }

            addToLog(exp);
            if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("getValue: Unable to handle " + exp + ", " + exp.getClass());

            return null;
            //return exp.toString();
        } catch (Throwable e) {
            addToLog("Error getting value for " + exp + ": " + exp.getClass() + ", " + e);
            System.err.println("Error getting value for " + exp + ", " + e);
            e.printStackTrace();
            return exp.toString();
        }
    }

    private Object handleInfixExpression(Expression exp) {
        InfixExpression iex = (InfixExpression) exp;
        Object left = getValue(iex.getLeftOperand());
        int opType = 0;
        InfixExpression.Operator op = iex.getOperator();
        if (op.equals(InfixExpression.Operator.PLUS))
            opType = 1;
        if (op.equals(InfixExpression.Operator.TIMES))
            opType = 2;
        if (op.equals(InfixExpression.Operator.MINUS))
            opType = 3;
        if (op.equals(InfixExpression.Operator.DIVIDE))
            opType = 4;
        if (op.equals(InfixExpression.Operator.AND))
            opType = 5;
        if (op.equals(InfixExpression.Operator.OR))
            opType = 6;
        if (op.equals(InfixExpression.Operator.EQUALS))
            opType = 7;
        if (op.equals(InfixExpression.Operator.LESS))
            opType = 8;
        if (op.equals(InfixExpression.Operator.LESS_EQUALS))
            opType = 9;
        if (op.equals(InfixExpression.Operator.GREATER))
            opType = 10;
        if (op.equals(InfixExpression.Operator.GREATER_EQUALS))
            opType = 11;
        int type = 0;

        FormComponent lfc = null;
        if (left instanceof FormComponent) {
            lfc = (FormComponent) left;
            if (lfc.isVisual())
                left = lfc.getObject(false); //v3.8.2
            else
                left = lfc.getObject(true);
        }

        if (left instanceof Integer)
            type = 1;
        if (left instanceof Long)
            type = 2;
        if (left instanceof Float)
            type = 3;
        if (left instanceof Double)
            type = 4;
        if (left instanceof Short)
            type = 5;

        Expression ro = iex.getRightOperand();

        //v4.0.0
        if (opType >= 7 && opType <= 11) {
            if (opType == 7 && ro instanceof NullLiteral) {
                //for getters (eg, if(JButton1 == null) {...} ) want to make
                //sure we return true so that the block is evaluated, but does it work
                //if we do the test properly?
                if (lfc != null)
                    return Boolean.TRUE;
                //problem with below was that getters for non-visal components (eg, AbstractActions)
                //were not evaluated, so above done instead
                //            	        if(lfc != null && lfc.isVisual())
                //            	            return Boolean.TRUE;
                //            	        return new Boolean(left == null);
            } else {
                Object rval = getValue(ro);
                if (rval instanceof FormComponent) {
                	rval = ((FormComponent) rval).getObject(true);
                }
                Number lnum = null, rnum = null;
            	if(left instanceof Number && rval instanceof Number) {
            		lnum = (Number)left;
            		rnum = (Number)rval;
            	}
                if(opType == 7) {
                	if (rval == null && left == null)
                		return Boolean.TRUE;
                	if (rval == null && left != null)
                		return Boolean.FALSE;
                	if (rval != null && left == null)
                		return Boolean.FALSE;
                	return new Boolean(rval.equals(left));
                } else if(lnum != null && rnum != null) {
                	if(opType == 8) {
                		return new Boolean(lnum.doubleValue() < rnum.doubleValue());
                	} else if(opType == 9) {
                		return new Boolean(lnum.doubleValue() <= rnum.doubleValue());
                	} else if(opType == 10) {
                		return new Boolean(lnum.doubleValue() > rnum.doubleValue());
                	} else if(opType == 11) {
                		return new Boolean(lnum.doubleValue() >= rnum.doubleValue());
                	}
                } else {
                	return Boolean.FALSE;
                }
            }
        }

        if (type > 0) {
            double val = getNumber(left, 1.0);
            if (opType > 0) {

                Object eval = getValue(ro);
                if (opType == 1)
                    val += getNumber(eval, 0.0);
                else if (opType == 2)
                    val *= getNumber(eval, 1.0);
                else if (opType == 3)
                    val -= getNumber(eval, 0.0);
                else if (opType == 4)
                    val /= getNumber(eval, 1.0);
                else if (opType == 5)
                    val = (int) val & (int) getNumber(eval, 0.0);
                else if (opType == 6)
                    val = (int) val | (int) getNumber(eval, 0.0);
                List eos = iex.extendedOperands();
                for (Iterator iter = eos.iterator(); iter.hasNext();) {
                    Expression nexp = (Expression) iter.next();
                    eval = getValue(nexp);
                    if (opType == 1)
                        val += getNumber(eval, 0.0);
                    else if (opType == 2)
                        val *= getNumber(eval, 1.0);
                    else if (opType == 3)
                        val -= getNumber(eval, 0.0);
                    else if (opType == 4)
                        val /= getNumber(eval, 1.0);
                    else if (opType == 5)
                        val = (int) val & (int) getNumber(eval, 0.0);
                    else if (opType == 6)
                        val = (int) val | (int) getNumber(eval, 0.0);
                }
                //                        System.out.println("GOT IEX VAL "+val+", "+iex);
                if (type == 1)
                    return new Integer((int) val);
                if (type == 2)
                    return new Long((long) val);
                if (type == 3)
                    return new Float((float) val);
                if (type == 4)
                    return new Double(val);
                if (type == 5)
                    return new Short((short) val);
            }
        } else if (left instanceof String) {
            List eos = iex.extendedOperands();
            StringBuffer val = new StringBuffer((String) left);
            Object rval = getValue(iex.getRightOperand());
            if (rval != null) {
                if(rval instanceof FormComponent)
                	rval = ""+((FormComponent)rval).getNonVisualObject();
            	val.append(rval+"");
            }
            for (Iterator iter = eos.iterator(); iter.hasNext();) {
                Expression nexp = (Expression) iter.next();
                Object eval = getValue(nexp);
                if (eval == null)
                    eval = nexp;
                val.append(eval.toString());
            }
            return val.toString();
        }
        addToLog(exp);
        return null;
	}

	/**
     * @param mexp
     * @return
     */
    private static boolean startsWithClass(Expression mexp) {
        if (mexp instanceof SimpleName || mexp instanceof FieldAccess || mexp instanceof QualifiedName)
            return true;
        if (mexp instanceof MethodInvocation)
            return startsWithClass(((MethodInvocation) mexp).getExpression());
        return false;
    }

    private boolean methodReturnsValue(Method m, Object obj) {
        if (m == null)
            return false;
        if (m.getReturnType() == null || m.getReturnType().equals(void.class))
            return false;
        if (obj == null && !java.lang.reflect.Modifier.isStatic(m.getModifiers()))
            return false;
        return true;
    }
    
    /**
     * Handles calls like Box.createVerticalStrut(6) - in which case object
     * will be null, or new MyFactory().createObject()
     * @param m - method
     * @param object - if null, this is a static method call
     * @param params
     * @return
     */
    private Object getFormComponentFromMethod(Method m, Object object, Object[] params, Expression exp)
            throws Exception {
    	if(!methodReturnsValue(m, object))
            return null;
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof FormComponent) {
                params[i] = ((FormComponent) params[i]).getObject(true);
            }
        }
        Object val = invokeMethod(m, object, params);
        if(val == null)
        	return null;
        if (val.getClass().isPrimitive() || val instanceof Long || val instanceof Short || val instanceof Boolean
        		|| val instanceof Integer || val instanceof Float || val instanceof Double || val instanceof Byte
				|| val instanceof Character || val instanceof String || val instanceof Border)
        	return val;
        FormComponent fcv = createFormComponent(val, getCodeForNode(exp)); //3.8.1
        fcv.setFactoryElement(true);
        fcv.initProperties();
        setAssignmentNode(fcv.getName(), exp);
        if (val instanceof Control) {
        	//v4.0.0
        	FormComponent par = getFormComponentWithObject(((Control) val).getParent());
        	if (par != null) {
        		par.addChild(fcv);
        	}
        }
        return fcv;
    }

    /**
     * @param ciObj
     * @param methodName
     * @param params
     * @param types
     * @return
     */
    private Object invokeMethod(Object obj, String methodName, Object[] params, Class[] types) {
        if (obj == null)
            return null;
        Method m = JiglooUtils.findMethod(obj.getClass(), methodName, types);
        return invokeMethod(m, obj, params);
    }

    private Object invokeMethod(Method m, Object obj, Object[] params) {
    	if(!methodReturnsValue(m, obj))
    		return null;
        Class cls = m.getDeclaringClass();
        if( Cacher.isAssignableFrom(MessageDialog.class, cls) 
        		||  Cacher.isAssignableFrom(System.class, cls) 
				||  Cacher.isAssignableFrom(JOptionPane.class, cls))
        	return null;
        try {
        	return m.invoke(obj, params);
        } catch (Throwable t) {
        	jiglooPlugin.writeToLog("Error invoking " + m + ", " + t+", "+currentStatement);
        	return null;
        }
    }
    
    /**
     * @param rhs
     * @param i
     * @return
     */
    private double getNumber(Object o, double defVal) {
        if (o instanceof Double)
            return ((Double) o).doubleValue();
        if (o instanceof Integer)
            return ((Integer) o).doubleValue();
        if (o instanceof Float)
            return ((Float) o).doubleValue();
        if (o instanceof Long)
            return ((Long) o).doubleValue();
        if (o instanceof Short)
            return ((Short) o).doubleValue();
        return defVal;
    }

    /**
     * Fixes a bug in Eclipse 3.1
     */
    private static char getCharValue(CharacterLiteral cl) {
        String s = cl.getEscapedValue();
        int len = s.length();
        if (len < 2 || s.charAt(0) != '\'' || s.charAt(len - 1) != '\'') {
            throw new IllegalArgumentException();
        }
        char c = s.charAt(1);
        if (c == '\'') {
            throw new IllegalArgumentException();
        }
        if (c == '\\') {
            if (len == 4) {
                char nextChar = s.charAt(2);
                switch (nextChar) {
                case 'b':
                    return '\b';
                case 't':
                    return '\t';
                case 'n':
                    return '\n';
                case 'f':
                    return '\f';
                case 'r':
                    return '\r';
                case '\"':
                    return '\"';
                case '\'':
                    return '\'';
                case '\\':
                    return '\\';
                case '0':
                    return '\0';
                case '1':
                    return '\1';
                case '2':
                    return '\2';
                case '3':
                    return '\3';
                case '4':
                    return '\4';
                case '5':
                    return '\5';
                case '6':
                    return '\6';
                case '7':
                    return '\7';
                default:
                    throw new IllegalArgumentException("illegal character literal");//$NON-NLS-1$
                }
            } else if (len == 8) {
                //handle the case of unicode.
                int currentPosition = 2;
                int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
                if (s.charAt(currentPosition++) == 'u') {
                    if ((c1 = Character.getNumericValue(s.charAt(currentPosition++))) > 15 || c1 < 0
                            || (c2 = Character.getNumericValue(s.charAt(currentPosition++))) > 15 || c2 < 0
                            || (c3 = Character.getNumericValue(s.charAt(currentPosition++))) > 15 || c3 < 0
                            || (c4 = Character.getNumericValue(s.charAt(currentPosition++))) > 15 || c4 < 0) {
                        throw new IllegalArgumentException("illegal character literal");//$NON-NLS-1$
                    } else {
                        return (char) (((c1 * 16 + c2) * 16 + c3) * 16 + c4);
                    }
                } else {
                    throw new IllegalArgumentException("illegal character literal");//$NON-NLS-1$
                }
            } else {
                throw new IllegalArgumentException("illegal character literal");//$NON-NLS-1$
            }
        }
        return c;

    }

    public HashMap getPropMap(String parName) {
        parName = resolveName(parName);
        HashMap map = (HashMap) setPropMethods.get(parName);
        if (map == null) {
            map = new HashMap();
            setPropMethods.put(parName, map);
        }
        return map;
    }

    private FormComponent getFormComponent(String nameOrMethod) {
        if (nameOrMethod.equals("this") || nameOrMethod.equals("super"))
            return getRootComponent();
        if (nameOrMethod.endsWith("()")) {
            nameOrMethod = nameOrMethod.substring(0, nameOrMethod.length() - 2);
        }
        Object fc = getReturnValue(nameOrMethod);
        if (fc instanceof FormComponent)
            return (FormComponent) fc;
        fc = findFormComponent(nameOrMethod);
        if(fc == null && nameOrMethod.equals(methodBeingParsed)) {
        	//if we are inside a getter method and the method itself is being called eg:
        	// public JPanel getMainPanel() {
        	// if(mainPanel == null) {
        	//   mainPanel = new JPanel();
        	//   getMainPanel().add(getButton());
        	//  }
        	// return mainPanel;
        	//}
        	MethodDeclaration md = getMethodNode(nameOrMethod);
        	if(md != null)
        		analyseBlockForReturnFC(md.getBody());
        }
        return (FormComponent)fc;
    }

    private MethodDeclaration getMethodDeclaration(String methodKey) {
        MethodDeclaration mdec = (MethodDeclaration) methodMap.get(methodKey);
        return mdec;
    }

    private MethodDeclaration getClosestMethod(String methodKey) {
            MethodDeclaration mdec = (MethodDeclaration) methodMap.get(methodKey);
            if (mdec != null)
                return mdec;
        
        MethodDeclaration bestGuess = null;
        Iterator it = methodMap.keySet().iterator();
        while (it.hasNext()) {
        	String key = (String) it.next();
        	if (methodKeysAlmostMatch(methodKey, key))
        		return (MethodDeclaration) methodMap.get(key);
        	if(methodKey.startsWith(key) || key.startsWith(methodKey))
        		bestGuess = (MethodDeclaration) methodMap.get(key);
        }
        return bestGuess;
    }

    private boolean methodKeysAlmostMatch(String m1, String m2) {
        int p1 = m1.indexOf("_");
        int p2 = m2.indexOf("_");
        if (p1 != p2)
            return false;
        if (p1 < 0)
            return m1.equals(m2);
        if (!m1.substring(0, p1).equals(m2.substring(0, p2)))
            return false;
        if (countChars(m1, ',') != countChars(m2, ','))
            return false;
        //TODO could extend this to check if the parameter classes can
        //be assigned from each other
        return true;
    }

    private int countChars(String str, char chr) {
        int pos = 0;
        int cnt = 0;
        while (pos >= 0) {
            pos = str.indexOf(chr, pos);
            if (pos >= 0) {
                cnt++;
                pos++;
            }
        }
        return cnt;
    }

    /**
     * The value corresponding to the given key might occur more than once
     * in the given map (eg, the same node is parsed twice, but with different
     * blockStr values).
     */
    private void removeFromMap(HashMap map, Object key) {
    	Object val = map.get(key);
    	map.remove(key);
    	if (val == null)
    		return;
    	while ((key = getKeyForValue(map, val)) != null) {
    		map.remove(key);
    		System.out.println("REMOVING SECONDARY MAP VALUE " + key + ", " + val);
    	}
    }

    private String methodBeingParsed = null;

    private boolean parseMethod(String methodKey, List args, Expression mic) {
        MethodDeclaration mdec = getMethodDeclaration(methodKey);
        return parseMethod(mdec, methodKey, args, mic);
    }

    private List getArgs(Expression exp) {
        if (exp instanceof MethodInvocation)
            return ((MethodInvocation) exp).arguments();
        if (exp instanceof SuperMethodInvocation)
            return ((SuperMethodInvocation) exp).arguments();
        return null;
    }

    private boolean parseMethod(Expression mic) {
        String mkey = getMethodNameKey(mic);
        MethodDeclaration mdec = getMethodDeclaration(mkey);
        return parseMethod(mdec, mkey, getArgs(mic), mic);
    }

    private boolean parseMethod(String methodKey) {
        MethodDeclaration mdec = getMethodDeclaration(methodKey);
        if (mdec == null)
            return false;
        parseMethod(mdec, methodKey);
        return true;
    }

    private boolean parseMethod(MethodDeclaration mdec, String methodKey) {
        return parseMethod(mdec, methodKey, null, null);
    }

    private int parsingDepth;

    private boolean parseMethod(MethodDeclaration mdec, String methodKey, List args, Expression mic) {
        if (mdec == null)
            return false;
        
        if(isProtected(mdec) || isHidden(mdec))
            return false;
        
        if (mic != null) {
            if (parsedMethods.contains(mic))
                return true;
            parsedMethods.add(mic);
        } else {
            if (parsedMethods.contains(mdec))
                return true;
        }

        //don't return here because this method might create a new object each time it is called
        //    	if(methodReturns.containsKey(methodKey) && (args == null || args.size() == 0))
        //    	    return true;

        String msg = "Parsing method: " + mdec.getName();
        for (int i = 0; i < parsingDepth; i++)
            msg = "  > " + msg;
        addToLog(msg);

        parsingDepth++;

        parsedMethods.add(mdec);
        if (jiglooPlugin.DEBUG)
            System.out.println("PARSING METHOD (2) " + methodKey + ", " + mic + ", " + blockStr);
        String oldMBP = methodBeingParsed;
        methodBeingParsed = methodKey;
        Object[] params = null;
        String[] nameCache = null;
        if (methodKey.startsWith("createPartControl_") || methodKey.startsWith("createContents_")
                || methodKey.startsWith("createControl_") || methodKey.startsWith("createDialogArea_")) {
            if (mdec.parameters().size() == 1) {
                SingleVariableDeclaration param1 = (SingleVariableDeclaration) mdec.parameters().get(0);
                String pName = param1.getName().getIdentifier();
                FormComponent fc = findFormComponent(pName);
                if (fc == null) {
                    if (root != null)
                        fc = root;
                    else
                        fc = FormComponent.newFormComponent(editor, Composite.class.getName());
                }
                root = fc;
                editor.setRootName(pName);
                fc.setEditor(editor);
                fc.setAssigned(true);
                fc.setExistsInCode(true);
                fc.setName(pName);
                fc.setClassName(Composite.class.getName());
                fc.setClassType(Composite.class);
                LayoutWrapper lw = new LayoutWrapper(fc, "Fill", false);
                lw.setSet(true);
                lw.setExplicitlySet(false);
                fc.setLayoutWrapper(lw, false);
                putField(pName, fc);
            }
        } else {
            //pass in parameters to method call - need to change names
            //temporarily to match mdec's parameter names
            if (args != null) {
                params = getParams(args);
                if (params != null && params.length == mdec.parameters().size()) {
                    nameCache = new String[params.length];
                    for (int i = 0; i < params.length; i++) {
                        SingleVariableDeclaration param = (SingleVariableDeclaration) mdec.parameters().get(i);
                        String pName = blockStr + param.getName().getIdentifier();
                        FormComponent fcp = null;

                        if (params[i] instanceof FormComponent) {
                            fcp = (FormComponent) params[i];
                        }

                        if (params[i] instanceof ConstructorHolder) {
                            fcp = createInlineFormComponent(null, (ConstructorHolder) params[i]);
                        }

                        if (fcp != null) {
                            //        					System.out.println("REPLACING "+fcp.getName()+" with "+pName);
                            nameCache[i] = fcp.getName();
                            fcp.setName(pName);
                            fcp.setIsParameter(true);
                            putField(pName, fcp);
                        } else {

                            //if the field exists already, don't remove it later
                            if (!fields.containsKey(pName))
                                nameCache[i] = pName;

                            putField(pName, params[i]);
                        }
                    }
                }
            }
        }

        //ver 3.9.5
        removeMethodReturn(methodBeingParsed);

        handleBlock(mdec.getBody());
        
        if (nameCache != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof FormComponent) {
                    FormComponent fcp = (FormComponent) params[i];
                    fcp.setName(nameCache[i]);
                    fcp.setIsParameter(false);
                } else {
                    if (nameCache[i] != null)
                        removeField(nameCache[i]);
                }
            }
        }
        Object ret = getReturnValue(methodBeingParsed);
        FormComponent fc = null;
        boolean isGetter = false;
        
        if (ret instanceof FormComponent && mic != null) {
        	fc = (FormComponent) ret;
            fc.setDeclarationInCode(getCodeForNode(mic));
            if(!fc.isInline() && fc.getName().indexOf("%") < 0)
            	isGetter = true;
        }

        methodBeingParsed = oldMBP;

        if (mic != null && (args.size() > 0 || !isGetter)) {
            parsedMethods.remove(mic);
        }
        
        parsingDepth--;
        return true;

    }

    private boolean parsingMethod(String methodName) {
        return (methodBeingParsed != null && methodBeingParsed.startsWith(methodName + "_"));
    }

    public void deduceFormComponents(int before, Object param, 
    		FormComponent par, MethodInvocation mic) {

    	Container bp = par.getBuilderPanel();
    	int after = bp.getComponentCount();
        FormComponent fcc = null;
        FormComponent fc = null;
        Object[] fcs = null;

        if (param instanceof FormComponent)
            fc = (FormComponent) param;
        else if (param instanceof Object[])
            fcs = (Object[]) param;

        if (fcs != null && fcs.length == 1) {
            if (fcs[0] instanceof Object[])
                fcs = (Object[]) fcs[0];
            else if (fcs[0] instanceof FormComponent)
                fc = (FormComponent) fcs[0];
        }

        if (after - before > 0) {
            Component fcComp = null;
            for (int i = before; i < after; i++) {
                fcc = null;
                if (i >= bp.getComponentCount())
                    break;
                Component last = bp.getComponent(i);
                boolean newComp = true;
                if (fcs != null) {
                    for (int j = 0; j < fcs.length; j++) {
                        fcComp = null;
                        fc = null;
                        if (fcs[j] instanceof FormComponent) {
                            fc = (FormComponent) fcs[j];
                            fcComp = fc.getRawComponent();
                            if (last != null && last.equals(fcComp)) {
                                if (!par.hasChildObject(fcComp)) {
                                    setConnectionNode(fc.getName(), mic);
                                    fc.setParent(par, true);
                                    //									par.addChild(fc);
                                }
                            }
                        } else if (fcs[j] instanceof Component) {
                            fcComp = (Component) fcs[j];
                            if (last != null && last.equals(fcComp)) {
                                if (!par.hasChildObject(fcComp)) {
                                    FormComponent fc2 = createFormComponent(null, fcComp, getCodeForNode(mic));
                                    fields.put(fc2.getName(), fc2);
                                    setConnectionNode(fc2.getName(), mic);
                                    //								System.out.println("CREATING FC FROM PARAM "+fc.getName()+", "+mic);
                                    fc2.initProperties();
                                    par.addChild(fc2);
                                }
                            }
                        }
                        if (last != null && last.equals(fcComp)) {
                            newComp = false;
                        }
                    }
                    if (newComp) {
                        fcc = createFormComponent(null, last, getCodeForNode(mic), "_bldr::JG",
                                FormComponent.TYPE_SWING);
                        setConnectionNode(fcc.getName(), mic);
                    }
                } else if (fc != null) {
                    fcComp = fc.getRawComponent();
                    if (last != null && !last.equals(fcComp)) {
                        fcc = createFormComponent(null, last, getCodeForNode(mic), "_bldr::JG",
                                FormComponent.TYPE_SWING);
                        setConnectionNode(fcc.getName(), mic);
                    }
                }
                if (fcc != null) {
                    fcc.setParent(par, true);
                    setConnectionNode(fcc.getName(), mic);
                    fcc.setInMainTree(true);
                    fcc.initProperties();
                }
            }
        }
    }

    private void handleMethodInvocation(MethodInvocation mic, String lhs) {
        handleMethodInvocation(mic, mic.getExpression(), mic.getName(), mic.arguments(), lhs);
    }

    /**
     * Handles cases like super.setSize(), but not cases like
     * super.getJPanel1().setSize(), which would be a normal MethodInvocation,
     * with a SuperMethodInvocation as it's expression.
     */
    private void handleSuperMethodInvocation(SuperMethodInvocation mic, String lhs) {
        handleMethodInvocation(mic, null, mic.getName(), mic.arguments(), lhs);
    }

    private boolean isMethodForClass(String testMethod, Class testClass, String actualMethod, String actualClassName) {
        Class actualClass = editor.loadClass(actualClassName);
        return actualClass != null && testMethod.equals(actualMethod) &&  Cacher.isAssignableFrom(testClass, actualClass);
    }

    private boolean isMethodForClass(String testMethod, String testClass, String actualMethod, String actualClass) {
        return actualClass != null && testMethod.equals(actualMethod) && testClass.equals(actualClass);
    }

    private Class getClassForMethod(MethodInvocation mInv) {
        IMethodBinding methBinding = mInv.resolveMethodBinding();
        if (methBinding == null || methBinding.getDeclaringClass() == null)
            return null;
        String clsName = getFullClassName(methBinding.getDeclaringClass());
        return loadClass(clsName, null);
    }

    private void handleMethodInvocation(Expression mic, Expression micExp, SimpleName mName, List args, String lhs) {
        try {
            if (isHidden(mic))
                return;
            //            System.err.println("HANDLE MIC "+mic);
            String methodName = mName.getIdentifier();
            FormComponent par = null;
            String parName = null;
            micExp = EvaluationUtils.removeCast(micExp);
            MethodInvocation mInv = null;
            IMethodBinding methBinding = null;
            if (mic instanceof MethodInvocation) {
                mInv = (MethodInvocation) mic;
                methBinding = mInv.resolveMethodBinding();
            } else if (mic instanceof SuperMethodInvocation) {
                methBinding = ((SuperMethodInvocation) mic).resolveMethodBinding();
            }

            if (lhs != null) {
                parName = lhs.toString();
                //TODO - a better way?
                FormComponent lhsFC = editor.getComponentByName(parName);
                if (lhsFC == null)
                    lhsFC = findFormComponent(parName);

                if (parsingMethod("createButtonsForButtonBar") && methodName.equals("createButton")) {
                    String bName = lhs.toString();
                    //this button may have been declared already (eg, "Button okButton;")
                    //so, to avoid creating two okButtons, first check to see if it has been
                    //declared.
                    lhsFC = handleCreateButton(args, lhsFC);
                    lhsFC.setName(bName);
                    setAssignmentNode(parName, mic);
                    editor.addComponent(lhsFC);

                } else if (mic.toString().indexOf("getContentPane()") >= 0) {
                    if (lhsFC != null) {
                        par = getMethodObject(mic);
                        if (!par.hasChild(lhsFC))
                            par.addChild(lhsFC);
                        lhsFC.setContentPane(true, true);
                        lhsFC.setExistsInCode(true);
                        lhsFC.setAssigned(true);
                    }
                } else if (mic.toString().indexOf("getViewport()") >= 0) {
                    if (jiglooPlugin.DEBUG) {
                        System.out.println("GET VIEWPORT: MIC= " + mic);
                        System.out.println("GET VIEWPORT: FC=" + lhsFC);
                    }
                    if (lhsFC != null) {
                        par = getMethodObject(mic);
                        if (jiglooPlugin.DEBUG)
                            System.out.println("GET VIEWPORT: PAR=" + par);
                        par.addChild(lhsFC);
                    }
                } else if (mic.toString().indexOf("getLayout()") >= 0) {
                    //TODO - a better way?
                    LayoutWrapper lw = (LayoutWrapper) getValue(mic);
                    if (lw != null) {
                        String lwName = blockStr + lhs;
                        lw.setName(lwName);
                        putField(lwName, lw);
                    }
                    return;
                    //handle calls like "new <MAIN_CLASS>().createGUI()"
                } else if (micExp instanceof ClassInstanceCreation) {
                    ClassInstanceCreation cic = (ClassInstanceCreation) micExp;
                    getMainClass();
                    String cicName = cic.getName().toString();
                    if (mainClassName != null && mainClassName.endsWith(cicName)) {
                        MethodDeclaration mdec = getMethodDeclaration(methodName + "_");
                        if (mdec != null) {
                            parseMethod(mdec, methodName, args, mic);
                        }
                        FormComponent ret = (FormComponent) getReturnValue(methodName);
                        if (ret != null) {
                            removeMethodReturn(methodName);
                            ret.setOriginalName(ret.getName());
                            reassignAs(ret, lhsFC, mic, true);
                        }
                        return;
                    }
                } else {
                    par = getMethodObject(mic);
                    if (par != null && par.equals(getRootComponent())) {
                        //TODO should set lhs to the result of parsing this method
                        String mKey = getMethodNameKey(mic);
                        MethodDeclaration mdec = getMethodDeclaration(mKey);
                        parseMethod(mdec, methodName, args, mic);
                        if (lhsFC != null) {
                            if (mdec == null) {
                                Object val = getValue(mic);
                                if (jiglooPlugin.canUseSwing() && val instanceof Component) {
                                    FormComponent fc2 = createFormComponent(lhsFC, (Component) val, getCodeForNode(mic));
                                    reassignAs(fc2, lhsFC, mic.getParent());
                                } else if (val instanceof FormComponent) {
                                    //for things like form.getBody()
                                    FormComponent nfc = (FormComponent) val;
                                    reassignAs(nfc, lhsFC, mic.getParent());
                                }
                            } else {
                                FormComponent ret = (FormComponent) getReturnValue(methodName);
                                if (ret != null) {
                                    if (lhsFC.isAssigned()) //so we need to re-calculate methodName's return value
                                        removeMethodReturn(methodName);
                                    ret.setOriginalName(ret.getName());
                                    reassignAs(ret, lhsFC, mic.getParent());
                                }
                            }
                        }
                    } else {
                        Object val = getValue(mic);
                        if (lhsFC != null) {
                            if (val instanceof Component) {
                                //for things like Box components (spacers, glue)
                                createFormComponent(lhsFC, (Component) val, getCodeForNode(mic));
                                setConnectionNode(lhsFC.getName(), mic);
                            } else if (val instanceof FormComponent) {
                                //for things like SWT_AWT.new_Frame(composite), new_Shell
                                FormComponent nfc = (FormComponent) val;
                                nfc.setAssigned(true);
                                nfc.setExistsInCode(true);
                                reassignAs(nfc, lhsFC, mic.getParent());
                            }
                        }
                    }
                }
                return;
            }

            String methodClassName = null;

            if (methBinding != null) {
                methodClassName = getFullClassName(methBinding.getDeclaringClass());
            }

            if (jiglooPlugin.canUseSwing()
            		&& isMethodForClass("setDefaultLookAndFeelDecorated", JFrame.class, methodName, methodClassName))
            	return;

            if(harness != null) {
            	if(mic instanceof MethodInvocation) {
            		FormComponent obj = getMethodObject(mic);
            		Class methodClass = getClassForMethod((MethodInvocation) mic);
            		if(harness.handleMethod(this, obj, methodName, methodClass, args) != null)
            			return;
            	}
            }
                
            if (micExp != null) {
                String micStr = mic.toString();
                if (micStr.indexOf("UIManager.setLookAndFeel(") >= 0) {
                    if (args.size() == 1) {
                        Object arg = args.get(0);
                        if (arg instanceof StringLiteral) {
                            laf = ((StringLiteral) arg).getLiteralValue();
                            lafNode = mic;
                        } else {
                            Object arg2 = getValue((Expression) arg);
                            if (arg2 instanceof String) {
                                laf = (String) arg2;
                                lafNode = mic;
                            }
                        }
                    }
                    return;
                }
                if (micStr.indexOf("SWTResourceManager.registerResourceUser") >= 0) {
                    regResUserNode = mic;
                    return;
                }

                if (jiglooPlugin.DEBUG)
                    System.out.println("HANDLE MIC: MIC.EXP=" + micExp + ", " + micExp.getClass() + ", " + mic);

                List micArgs = null;
                String method2Name = null;
                Expression mic2Exp = null;

                if (micExp instanceof SuperMethodInvocation) {
                    //eg, super.getJPanel()
                    SuperMethodInvocation mic2 = (SuperMethodInvocation) micExp;
                    method2Name = mic2.getName().getIdentifier();
                    micArgs = mic2.arguments();
                    //par = getRootComponent();
                }

                if (micExp instanceof MethodInvocation) {
                    //eg, scroller.getViewport().setBackground(...)
                    MethodInvocation mic2 = (MethodInvocation) micExp;
                    method2Name = mic2.getName().getIdentifier();
                    micArgs = mic2.arguments();
                    mic2Exp = mic2.getExpression();
                }

                if (method2Name != null) {
                    if (micArgs.size() == 0) {
                        if (par == null) {
                            if (method2Name.equals("getViewport")) {
                                Object val = getValue(mic2Exp);
                                if (val instanceof FormComponent)
                                    par = (FormComponent) val;
                            } else if (method2Name.equals("getControl") || method2Name.equals("getTable")
                                    || method2Name.equals("getTableTree") || method2Name.equals("getTree")
                                    || method2Name.equals("getCombo") || method2Name.equals("getList")) {
                                Object val = getValue(mic2Exp);
                                if (val instanceof FormComponent) {
                                    FormComponent fc = (FormComponent) val;
                                    if (fc.isJFaceViewer())
                                        par = fc;
                                }
                            } else if (method2Name.startsWith("get")) {
                                //search for access of inherited children
                                String pName = JiglooUtils.propertyFromGetter(method2Name);
                                par = getMethodObject(mic);
                                if (par != null) {
                                    par = par.getChildByName(par.getName() + "." + pName);
                                }
                            }
                        }
                        if (par == null) {
                            Object obj = getReturnValue(method2Name);
                            if (obj instanceof FormComponent) {
                                par = (FormComponent) obj;
                            }
                        }
                    }
                }

                //handle calls like "new <MAIN_CLASS>().createGUI()"
                if (micExp instanceof ClassInstanceCreation) {
                    ClassInstanceCreation cic = (ClassInstanceCreation) micExp;
                    getMainClass();
                    String cicName = cic.getName().toString();
                    if (mainClassName != null && mainClassName.endsWith(cicName)) {
                        parseMethod(mic);
                        //                		parseMethod(methodName);
                        return;
                    } else {
                        Object val = getValue(micExp, true);
                        if (val instanceof ConstructorHolder) {
                            par = createInlineFormComponent(null, (ConstructorHolder) val);
                            setConnectionNode(par.getName(), mic);
                        }
                    }
                }
                if (par == null) {
                    parName = micExp.toString();
                    if(micExp instanceof ArrayAccess) {
                    	parName = getArrayElementName((ArrayAccess)micExp);
                    }
                    String fieldName = null;
                    String gcp1 = "getContentPane()";
                    String gcp2 = ".getContentPane()";
                    if (parName.endsWith(gcp2)) {
                        parName = parName.substring(0, parName.length() - 17);
                        if (parName.equals("this") || parName.equals("super"))
                            par = getRootComponent();
                        else
                            par = getFormComponent(parName);
                        par = par.getContentPaneFC();
                    } else if (parName.equals(gcp1)) {
                        par = getRootComponent().getContentPaneFC();
                    } else {
                        int pos = parName.indexOf(".");
                        if (pos > 0) {
                            fieldName = parName.substring(pos + 1);
                            parName = parName.substring(0, pos);
                        }
                        if (fieldName != null) {
                            pos = fieldName.indexOf(".");
                            if (pos > 0 && (parName.equals("this") || parName.equals("super"))) {
                                //covers cases like this.flowerPanel1.quantityField.setText("...")
                                parName = fieldName.substring(0, pos);
                                fieldName = fieldName.substring(pos + 1);
                            }
                        }
                        parName = resolveName(blockStr + parName);
                        if (parName.equals("this") || parName.equals("super") || parName.equals("dialogShell")
                                || (superClass == null && parName.equals("shell"))) {
                            par = getRootComponent();
                        } else {
                            par = getFormComponent(parName);
                            if (par == null && mInv != null) {
                                Object field = getRootMethodObject(mic);
                                if (ClassUtils.isGroupLayout(field) || field instanceof LayoutGroup) {
                                    handleGroupLayoutMethodCall(mInv, methodName, field);
                                }
                            }
                        }
                        if (fieldName != null && par != null) {
                            String pn = par.getName();
                            par = par.getChildByName(pn + "." + fieldName);

                            //System.out.println("GET FIELD
                            // "+pn+"."+fieldName+", "+par);
                            //par = par.getChildByName("." + fieldName);
                        }
                    }
                    if (jiglooPlugin.DEBUG) {
                        System.out.println("handleMethodInvocation parName=" + parName + ", par=" + par + ", " + mic);
                        if (par != null)
                            System.out.println("PAR.class=" + par.getMainClass() + ", " + getMainClass());
                    }

                    if (par != null) {
                        Class mc = getMainClass();
                        Class pmc = par.getMainClass();
                        if (pmc.equals(Class.class))
                            pmc = (Class) par.getObject(false);

                        if (mc != null && mc.equals(pmc)) {
                            //TODO use method call parameters to handle cases
                            // like createBrowser(String)
                            String mkey = getMethodNameKey(mic);
                            if (jiglooPlugin.DEBUG_EXTRA)
                                System.out.println("GOT SAME CLASS METHOD CALL " + mkey + ", for " + par);
                            if (parseMethod(mkey, args, mic))
                                return;
                        }
                    }
                }
            } else {
                if (methodName.equals("addPopup") || methodName.equals("setComponentPopupMenu")) {
                    Object[] params = getParams(args);
                    if (params != null && params.length == 2) {
                        params[0] = convertToFormComponent(params[0]);
                        params[1] = convertToFormComponent(params[1]);
                        if (params[0] instanceof FormComponent && params[1] instanceof FormComponent) {
                            FormComponent cmp = (FormComponent) params[0];
                            FormComponent pop = (FormComponent) params[1];
                            cmp.addChild(pop);
                            setConnectionNode(pop.getName(), mic);
                            return;
                        }
                    }
                }

                //handle expressions like "createBrowser()"
                if(!(mic instanceof SuperMethodInvocation)) {
                	//don't parse methods from superclass
                	String mkey = getMethodNameKey(mic);
                	if (jiglooPlugin.DEBUG)
                		System.out.println("GOT THIS METHOD CALL " + mkey);
                	//                MethodDeclaration mdec = getClosestMethod(mkey);
                	MethodDeclaration mdec = (MethodDeclaration) methodMap.get(mkey);
                	if (mdec == null)
                		mdec = getMethodDeclaration(mkey);
                	if (mdec != null) {
                		parseMethod(mdec, mkey, args, mic);
                		return;
                	}
                }
                
                par = getRootComponent();
                parName = getRootComponent().getName();
            }

            if (jiglooPlugin.DEBUG)
                System.out.println("HANDLE METHOD " + methodName + ", " + par);

            if (par != null && par.isJFaceForm()) {
                par.getEclipseForm();
                par = getRootComponent();
            }

            if (parsingMethod("createButtonsForButtonBar") && methodName.equals("createButton")) {
                FormComponent bb = handleCreateButton(args, null);
                setAssignmentNode(bb.getName(), mic);
                editor.addComponent(bb);
                return;
            }

            if (methodName.equals("insertTab")) {
                Object[] params = getParams(args);
                if (params != null && params.length == 5 && par != null && par.isSubclassOf(JTabbedPane.class)) {
                    FormComponent child = convertToFormComponent(params[2], (Expression) args.get(2));

                    if (child != null) {
                        par.addChild(child);
                        child.setIndexInParent(((Integer) params[4]).intValue());
                        child.setTabTitle((String) params[0]);
                        child.setPropertyValueCode("LAYOUT_CONSTRAINT", args.get(0).toString());
                        setConnectionNode(child.getName(), mic);
                    }
                }

            } else if (methodName.equals("addTab")) {
                Object[] params = getParams(args);
                if (params != null && params.length > 1 && par != null && par.isSubclassOf(JTabbedPane.class)) {
                    FormComponent child = null;
                    int childParamPos = 1;
                    if (params.length > 2) {
                        childParamPos = 2;
                    }
                    Object childParam = params[childParamPos];
                    child = convertToFormComponent(childParam, (Expression) args.get(childParamPos));

                    if (child != null) {
                        par.addChild(child);

                        if (child.getComponent() != null) {
                            child.initProperties();
                            child.setTabTitle((String) params[0]);
                        } else {
                            child.setTabTitle((String) params[0]);
                        }
                        child.setPropertyValueCode("LAYOUT_CONSTRAINT", args.get(0).toString());
                        setConnectionNode(child.getName(), mic);
                    }
                }
            } else if (isMethodForClass("show", AppUtils.JAVAX_SWING_SFAPP_NAME, methodName, methodClassName)) {

                Object child = getValue((Expression) args.get(0));
                if (child instanceof FormComponent)
                    getRootComponent().addChild((FormComponent) child);
                else
                    System.err.println("Unable to handle method " + mic);

            } else if (methodName.equals("add") || methodName.equals("setLeftComponent")
                    || methodName.equals("setRightComponent") || methodName.equals("setTopComponent")
                    || methodName.equals("setBottomComponent") || methodName.equals("setContentPane")
                    || methodName.equals("setComponentPopupMenu") || methodName.equals("setViewportView")
                    || methodName.equals("append")) {

                Expression arg1 = (Expression) args.get(0);
                Object constraint = null;
                String constraintCode = null;
                int arg1Pos = 0;

                Object[] params = new Object[args.size()];
                
                if (args.size() > 1 && !methodName.equals("append")) {
                    //builders have append(String, Component) - but first argument is NOT a constraint
                    if (arg1 instanceof StringLiteral) {
                        //this is, eg add("North", panel1)
                        constraint = getValue(arg1);
                        params[0] = constraint;
                        constraintCode = arg1.toString();
                        arg1 = (Expression) args.get(1);
                        arg1Pos = 1;
                    } else {
                        //this is, eg add(panel1, "TabTitle")
                        Expression arg2 = (Expression) args.get(1);
                        constraintCode = arg2.toString();
                        constraint = getValue(arg2, false, true);
                        params[1] = constraint;
                        //                        System.out.println("GOT LAYOUT CONSTRAINT "+constraint);
                        if (constraint instanceof Cloneable) {
                            try {
                                Object clon = constraint.getClass().getMethod("clone", null).invoke(constraint, null);
                                constraint = clon;
                            } catch (Throwable t) {
                                System.err.println("Unable to clone layout constraint " + constraint + ", " + t);
                            }
                        }
                        if (constraint instanceof LayoutDataWrapper) {
                            Object cob = ((LayoutDataWrapper) constraint).getLayoutData();
                            if (cob instanceof GridBagConstraints) {
                                GridBagConstraints gbc = (GridBagConstraints) cob;
                                cob = new GridBagConstraints(gbc.gridx, gbc.gridy, gbc.gridwidth, gbc.gridheight,
                                        gbc.weightx, gbc.weighty, gbc.anchor, gbc.fill, gbc.insets, gbc.ipadx,
                                        gbc.ipady);
                                constraint = cob;
                                //	                          System.out.println("SET GB LAYOUT CONSTRAINT "+constraint);
                            }
                        }
                    }
                }
                Object val = getValue(arg1, true);
                params[arg1Pos] = val;
                
                if (val == null)
                    return;
                boolean inline = false;
                if (val instanceof ConstructorHolder) {
                    inline = true;
                }
                FormComponent fc = null;
                if (val instanceof String)
                    fc = findFormComponent((String) val);
                else if (val instanceof FormComponent)
                    fc = (FormComponent) val;
                
                if (fc == null && inline) {
                    fc = createInlineFormComponent(par, (ConstructorHolder) val);
                    setConnectionNode(fc.getName(), mic);
                }

                if (jiglooPlugin.canUseSwing() && fc == null && val instanceof Component) {
                    fc = createFormComponent(null, (Component) val, getCodeForNode(arg1));
                }

                if(fc != null)
                	params[arg1Pos] = fc;
                
                if (par != null && fc != null) {

                    if (methodName.equals("setLeftComponent"))
                        fc.getLayoutDataWrapper().setObject(JSplitPane.LEFT);
                    else if (methodName.equals("setRightComponent"))
                        fc.getLayoutDataWrapper().setObject(JSplitPane.RIGHT);
                    else if (methodName.equals("setTopComponent"))
                        fc.getLayoutDataWrapper().setObject(JSplitPane.TOP);
                    else if (methodName.equals("setBottomComponent"))
                        fc.getLayoutDataWrapper().setObject(JSplitPane.BOTTOM);

                    boolean isSwing = fc.isSwing();

                    if (isSwing && par.isSubclassOf(ButtonGroup.class)) {
                        fc.setPropertyValueInternal("buttonGroup", new ClassWrapper(par.getName(), fc,
                                ButtonGroup.class), true, false);
                        HashMap map = getPropMap(fc.getName());
                        map.put("buttonGroup", mic);

                        map = getPropMap(par.getName());
                        //put the bg.add(button) methods in the bg propMap so that
                        //they can be removed if bg is removed
                        map.put(METHOD_PREFIX + "add+_" + fc.getName(), mic);
                        //and return now so that this node is not added to the
                        //elementConnections node for the button (since we do not want
                        //ANY node to appear in more than one node-map)
                        return;
                    } else {
                        //System.out.println("***SET CONSTRAINT "+constraint+", "+fc);
                        par.addChild(fc);
                        String plt = fc.getParentSuperLayoutType();
                        if ("Enfin".equals(plt) && editor.canUseEnfinLayout()) {
                            //the EnfinLayout can have a null constraint
                            fc.getLayoutDataWrapper().setObject(EnfinLayoutHandler.toFieldName(constraint));
                        } else if ((methodName.equals("add")
                        		|| methodName.equals("append")) && constraint != null) {
                            if ("Border".equals(plt)) {
                                fc.getLayoutDataWrapper().setObject(constraint);
                            } else if ("Table".equals(plt)) {
                                fc.getLayoutDataWrapper().setObject(constraint);
                            } else if ("Card".equals(plt)) {
                                fc.getLayoutDataWrapper().setObject(constraint);
                            } else if (isSwing && "Form".equals(plt) && (constraint instanceof String)) {
                                //the constraint of an element added to a FormLayout
                                //can be a String, but convert it here to a CellConstraint
                                try {
                                    constraint = new CellConstraints((String) constraint);
                                    fc.getLayoutDataWrapper().setObject(constraint);
                                } catch (Throwable t) {
                                    addToLog("warning - error making CellConstraints(" + constraint + ") " + t);
                                }
                            } else if (isSwing && par.isSubclassOf(JSplitPane.class)) {
                                fc.getLayoutDataWrapper().setObject(constraint);
                            } else if (isSwing && par.isSubclassOf(JTabbedPane.class)) {
                                fc.setTabTitle((String) constraint);
                            } else if (constraint instanceof LayoutDataWrapper) {
                                LayoutDataWrapper ldw = (LayoutDataWrapper) constraint;
                                ldw.setFormComponent(fc, true);
                                fc.setLayoutDataWrapper(ldw);
                            } else {
                                //System.out.println( "***GOT CONSTRAINT " + constraint + ", " + fc);
                            	if(fc.getParent().isSubclassOf(JLayeredPane.class)) {
                            		Object ival = constraint;
                            		FormComponent integerFC = getFormComponent(constraintCode);
                            		if(integerFC != null)
                            			ival = integerFC.getNameInCode();
                            		fc.getLayoutDataWrapper().setObject(new JLayerWrapper(ival, fc));
                            	} else {
                            		fc.getLayoutDataWrapper().setObject(constraint);
                            	}
                                fc.getLayoutDataWrapper().setConstraintCode(constraintCode);
                            }
                        }
                        if (constraintCode != null)
                            fc.setPropertyValueCode("LAYOUT_CONSTRAINT", constraintCode);

                        if (methodName.equals("setContentPane")) {
                            if (!par.isChildOf(fc) && !par.equals(fc)) {
                                //replace any existing children of par with fc
                                par.getChildren().clear();
                                par.getChildren().add(fc);
                                fc.setContentPane(true, false);
                            } else {
                                fc.setContentPane(false, false);
                                System.err.println("Parse error: setContentPane: Trying to add " + fc + " to " + par
                                        + " when " + par + " is a child of " + fc);
                            }
                        } else {
                            fc.setContentPane(false, false);
                        }

                        MethodInvocation ec = (MethodInvocation) elementConnections.get(fc.getName());
                        if (ec != null) {
                            //if an elementConnection node already exists for this node,
                            //move it to the property map, and replace it with mic if
                            //"add" or "setComponentPopupMenu", otherwise put
                            //mic in the property map
                            if (!ec.equals(mic)) {
                                HashMap map = getPropMap(fc.getName());
                                if (methodName.equals("add") || methodName.equals("setComponentPopupMenu")) {
                                    String mn = ec.getName().getIdentifier();
                                    if (mn.startsWith("set")) {
                                        String pName = JiglooUtils.deCapitalize(mn.substring(3));
                                        map.put(pName, ec);
                                    }
                                    setConnectionNode(fc.getName(), mic);
                                } else {
                                    String pName = JiglooUtils.deCapitalize(methodName.substring(3));
                                    map.put(pName, mic);
                                }
                            }
                        } else {
                            setConnectionNode(fc.getName(), mic);
                        }
                    }

                }
                if (par != null && par.isAbstractFormBuilder()) {
                    //initialize builder panel
                    par.getBuilder();
                    if (par.getBuilderPanel() != null) {
//                        for (int i = 0; i < params.length; i++) {
//                        	if(params[i] == null) {
//                        		params[i] = getValue((Expression) args.get(i));
//                            }
//                        }
                    	fillInParams(params, args);
                        par.addBuilderMethodCall(methodName, params, (MethodInvocation) mic);
                    }
                }

            } else if (methodName.equals("setTitleAt")) {
                int pos = 0;
                try {
                    pos = getIntValue((Expression) args.get(0));
                    //                    pos = Integer.parseInt(args.get(0).toString());
                } catch (Throwable t) {
                    System.err.println("Error parsing " + mic + ", " + t);
                }
                String val = getStringValue((Expression) args.get(1));
                //                String val = extractString(args.get(1).toString());
                par.getChildAt(pos).setTabTitle(val);
                //TODO save this node as a METHOD node, and delete it when
                //an element is moved inside par.
                HashMap map = getPropMap(parName);
                String key = METHOD_PREFIX + "setTitleAt-" + pos;
                map.put(key, mic);
            } else if (methodName.equals("setJMenuBar") || methodName.equals("setMenuBar")) {
                Object mb = getValue((Expression) args.get(0));
                if (mb instanceof FormComponent) {
                    menuBar = (FormComponent) mb;
                } else if (mb instanceof String) {
                    menuBar = findFormComponent((String) mb);
                }
                if (menuBar != null)
                    setConnectionNode(menuBar.getName(), mic);
                if (par != null)
                    par.setMenuBar(menuBar);
                else
                    getRootComponent().setMenuBar(menuBar);

            } else if (methodName.equals("setControl") || methodName.equals("setContent")
                    || methodName.equals("setMenu")) {
                //                String childName = args.get(0).toString();
                //                FormComponent fc = findFormComponent(childName);
                Object val = getValue((Expression) args.get(0));
                FormComponent fc = null;
                if (val instanceof FormComponent)
                    fc = (FormComponent) val;
                if (fc == null)
                    return;
                par.addChild(fc);
                setConnectionNode(fc.getName(), mic);

            } else if (methodName.startsWith("add") && methodName.endsWith("Listener")) {
                if (par != null) {
                    Expression arg0 = (Expression) args.get(0);
                    Object val = getValue(arg0, true);
                    String listener = methodName.substring(3);
                    //System.out.println(
                    //"***GOT LISTENER ARG " + val + ", " + args.get(0));
                    if (val instanceof FormComponent) {
                        FormComponent le = (FormComponent) val;
                        par.getEventWrapper().setListenerElement(listener, le.getName());
                    } else if (arg0 instanceof SimpleName) {
                        par.getEventWrapper().setListenerElement(listener, ((SimpleName) arg0).getIdentifier());
                    } else if (arg0 instanceof ThisExpression) {
                        par.getEventWrapper().setListenerElement(listener, "this");
                    } else {
                        //add to eventHandlers *before* calling
                        // handleEventHandler,
                        //so that methods can be detected as being inside event
                        // handler
                        if (!eventHandlers.contains(arg0)) {
                            if (jiglooPlugin.DEBUG)
                                System.out.println("ADDED EVT HANDLER " + arg0);
                            eventHandlers.add(arg0);
                        }
                        handleEventListener(arg0, listener, par);
                    }
                    if (!par.getIsParameter()) {
                        HashMap pmap = getPropMap(par.getName());
                        //put the button.addMouseListener(...); method invocation
                        //in the button's property node-map
                        pmap.put(EVENT_LISTENER + getUnqualifiedName(listener), arg0.getParent());
                    }
                }

            } else if (methodName.startsWith("set") && !methodName.equals("set") && args.size() >= 1) {

                String propName = JiglooUtils.deCapitalize(methodName.substring(3));
                if (jiglooPlugin.DEBUG)
                    System.out.println("SET PROP " + propName + ", par=" + par + ", parName=" + parName + ", " + mic);

                if (par == null) {
                    //TODO don't assume nonVis just because par==null
                    Object oo = findObject(fields, parName);
                    HashMap map = getPropMap(parName);
                    map.put(propName, mic);
                    if (jiglooPlugin.DEBUG)
                        System.out.println("SET PROP " + propName + ", oo==" + oo + ", map=" + map);
                    if (oo != null) {
                        setProperty(oo, propName, getParams(args, false));
                    }

                } else {
                    Object propVal = null;
                    Class propType = par.getPropType(propName);
                    Expression arg0 = (Expression) args.get(0);
                    if (arg0 instanceof ClassInstanceCreation) {
                        propVal = handleClassInstanceCreation((ClassInstanceCreation) arg0, par);
                        //                        (ClassInstanceCreation) arg0, par, true);  //v3.8.1 - NO!!! if use true, then setLayout(new BorderLayout()) doesn't work!
                        propVal = getWrapper(propType, propVal, null, par);
                        if (propVal != null && propVal instanceof IFormPropertySource) {
                            IFormPropertySource fps = (IFormPropertySource) propVal;
                            fps.setName(blockStr + fps.getName());
                            //prop created and set on the fly (eg,
                            // "setLayout(new FlowLayout())" )
                            setAssignmentNode(fps.getName(), arg0);
                        }
                    } else {
                        propVal = "";

                        String key = null;

                        if (args.size() == 1) {
                            if (arg0 instanceof SimpleName) {
                                key = resolveName(blockStr + ((SimpleName) arg0).getIdentifier());
                            } else if (arg0 instanceof QualifiedName) {
                                propVal = getValue(arg0);
                            } else if (arg0 instanceof FieldAccess) {
                                propVal = getValue(arg0);
                            }

                        }

                        if (args.size() > 1) {
                            propType = par.getPropType(propName);
                            if (args.size() == 4 && propType != null && propType.getName().equals("java.awt.Rectangle")) {
                                int x = getIntValue((Expression) args.get(0));
                                int y = getIntValue((Expression) args.get(1));
                                int w = getIntValue((Expression) args.get(2));
                                int h = getIntValue((Expression) args.get(3));
                                propVal = new java.awt.Rectangle(x, y, w, h);
                            } else if (args.size() == 2 && propType != null
                                    && propType.getName().equals("java.awt.Point")) {
                                int x = getIntValue((Expression) args.get(0));
                                int y = getIntValue((Expression) args.get(1));
                                propVal = new java.awt.Point(x, y);
                            } else if (args.size() == 2 && propType != null
                                    && propType.getName().equals("java.awt.Dimension")) {
                                int x = getIntValue((Expression) args.get(0));
                                int y = getIntValue((Expression) args.get(1));
                                propVal = new java.awt.Dimension(x, y);
                            } else if (args.size() == 2 && propType != null
                                    && propType.getName().equals("org.eclipse.swt.graphics.Point")) {
                                int x = getIntValue((Expression) args.get(0));
                                int y = getIntValue((Expression) args.get(1));
                                propVal = new Point(x, y);
                            } else if (args.size() == 4 && propType != null
                                    && propType.getName().equals("org.eclipse.swt.graphics.Rectangle")) {
                                int x = getIntValue((Expression) args.get(0));
                                int y = getIntValue((Expression) args.get(1));
                                int w = getIntValue((Expression) args.get(2));
                                int h = getIntValue((Expression) args.get(3));
                                propVal = new Rectangle(x, y, w, h);
                            } else {
                                //                                par.addMethodCall(methodName, getParams(args));
                                //                                return;
                                //	                            for (int i = 0; i < args.size(); i++) {
                                //	                                if (i != 0)
                                //	                                    propVal = propVal + ",";
                                //	                                propVal = propVal + args.get(i).toString();
                                //	                            }
                            }
                            //System.err.println("ARGS SIZE > 1 - not handled
                            // properly " + propVal);
                        } else {
                            if (arg0 instanceof NullLiteral) {
                                propVal = "null";
                            } else {
                                propVal = getValue(arg0, false);
                            }
                        }

                        if (par != null && par.equals(getRootComponent())) {
                            if (propName.equals("bounds") && propVal instanceof Rectangle) {
                                Rectangle b = (Rectangle) propVal;
                                if (b.width == 0 || b.height == 0)
                                    return;
                            }
                            if (propName.equals("size") && propVal instanceof Point) {
                                Point b = (Point) propVal;
                                if (b.x == 0 || b.y == 0)
                                    return;
                            }
                            if (jiglooPlugin.canUseSwing()) {
                                if (propName.equals("bounds") && propVal instanceof java.awt.Rectangle) {
                                    java.awt.Rectangle b = (java.awt.Rectangle) propVal;
                                    if (b.width == 0 || b.height == 0)
                                        return;
                                }
                                if (propName.equals("size") && propVal instanceof Dimension) {
                                    Dimension b = (Dimension) propVal;
                                    if (b.width == 0 || b.height == 0)
                                        return;
                                }
                            }
                        }
                        //                        System.out.println("SET FC PROP "+propName+", "+par+", "+propVal+", "+mic);
                        Object nvo = null;
                        //if(propVal instanceof String)
                        //nvo = findObject(fields, (String)propVal);
                        if (nvo != null) {
                            propVal = nvo;
                            propType = nvo.getClass();
                            //} else if ("null".equals(propVal)) {
                            //propVal = null;
                        } else {
                            propType = par.getPropType(propName);
                            Object resource = null;

                            if (key == null && propVal instanceof FormComponent) {
                                //even if key is null (eg, button.setAction(actionMap.get"test")) )
                                //then see if propVal is a field in the FormEditor
                                key = ((FormComponent) propVal).getName();
                            }

                            if (key != null) {
                                resource = findObject(fields, key);
                            }

                            if (resource != null) {
                                propVal = getWrapper(propType, resource, key, par);
                                if (propVal != null && ClassUtils.isResource(propVal.getClass())) {
                                    usingResourceWrappers = true;
                                }
                            } else {
                                propVal = convertProp(propVal, propType);
                            }
                        }
                    }

                    //if propVal is null this means we couldn't extract a value
                    //from the method call, so don't set property (or record node)
                    if (propVal == null)
                        return;

                    if ("null".equals(propVal))
                        propVal = null;

                    try {
                        if (propName.equals("layout")) {
                            //might be setLayout(null), so need to allow for
                            // AbsoluteLayouts
                            if (propVal == null || propVal instanceof LayoutWrapper) {
                                LayoutWrapper lw = (LayoutWrapper) propVal;
                                if (propVal == null)
                                    lw = new LayoutWrapper(par, "Absolute", par.isSwing());
                                lw.setSet(true);
                                par.setLayoutWrapper(lw);
                                par.getLayoutWrapper().setName(lw.getName());
                                par.setPropertyValueCode(propName, args.get(0).toString());
                                //par.setPropertyValue(propName, propVal);
                                //System.out.println("SET LAYOUT " +
                                // lw.getName() + " for " + par);
                            }
                        } else if (propName.equals("layoutData")) {
                            if (propVal instanceof LayoutDataWrapper) {
                                LayoutDataWrapper ldw = (LayoutDataWrapper) propVal;
//                                String ldwName = ldw.getName();
                                par.setLayoutDataWrapperSimple(ldw);
//                                ldw.setName(ldwName);
                                par.setPropertyValueInternal(propName, propVal, true, false);
                                par.setPropertyValueCode(propName, args.get(0).toString());
                            } else if(propVal instanceof String) {
                            	//for MigLayout, layoutData is a String
                            	par.getLayoutDataWrapper().setObject(propVal);
                            }
                        } else {
                            if (par.isJDialog() || par.isJFrame() || "dialogShell".equals(parName)) {
                                //                                if (propName.equals("size")
                                //                                        || propName.equals("bounds")) {
                                //                                    par.subtractWindowDecorationSizes(propVal);
                                //                                }
                            }
                            propVal = getWrapper(propType, propVal, propName, par);
                            if (jiglooPlugin.DEBUG)
                                System.out.println("SET FC PROP " + par + ", " + propName + ", " + propVal);

                            if (!par.hasProperty(propName)) {
                            	if(par.isAbstractFormBuilder()) {
                            		par.addBuilderMethodCall(methodName, getParams(args), null);
                            	} else {
                            		par.addMethodCall(methodName, getParams(args));
                            		String code = getCodeForNode(mic);
                            		if (code != null && parName != null)
                            			code = JiglooUtils.replace(code, parName + ".", "#%NAME%#.");
                            		if (jiglooPlugin.DEBUG)
                            			System.out.println("SET FC PROP - NO PROP " + propName + " for " + par + ", code="
                            					+ code);
                            		par.setExtraPropertyCode(propName, code);
                            	}
                            } else {
                                //set property for a FormComponent
                                par.setPropertyValueInternal(propName, propVal, true, false);
                                String pcode = "";
                                for (int i = 0; i < args.size(); i++) {
                                    if (i != 0)
                                        pcode += ", ";
                                    pcode += args.get(i).toString();
                                }
                                par.setPropertyValueCode(propName, pcode);
                            }

                        }

                        if (!par.getIsParameter()) {
                            HashMap map = getPropMap(par.getName());
                            map.put(propName, mic);
                        } else {
                            //this still doesn't work quite right
                            //                        	par.unsetProperty(propName);
                        }

                    } catch (Throwable e) {
                        System.err.println("Error setting " + propName + " to " + propVal + " for " + par + ": " + e);
                        e.printStackTrace();
                    }
                }

            } else {

                //handle all "extra" methods

                FormComponent fc = getMethodObject(mic);

                getMainClass();

                //want to parse methods like this.open() but *not* parse methods like
                //dialogShell.open() (since dialogShell is the rootComponent)
                if (fc != null && fc.getClassName().equals(mainClassName)) {
                    String mnkey = getMethodNameKey(mic);

                    MethodDeclaration mdec = (MethodDeclaration) methodMap.get(mnkey);

                    //want to be able to parse this method multiple times, so remove
                    //from parsedMethods
                    if (args.size() > 0) {
                        parsedMethods.remove(mdec);
                    }
                    
                    boolean parsed = parseMethod(mdec, mnkey, args, mic);
                    if (parsed) {
                        //                	System.err.println("PARSED METHOD "+mnkey);
                        return;
                    }
                }

                if (fc != null && fc.isAbstractFormBuilder()) {
                	fc.getBuilder();
                	if(fc.getBuilderPanel() != null) {
                		Object[] params = new Object[args.size()];
                		fillInParams(params, args);
                		fc.addBuilderMethodCall(methodName, params, (MethodInvocation)mic);
                	}
                    return;
                }

                if (fc != null) {
                    Object[] params = getParams(args);
                    //v4.0M3
                    if (methodName.equals("put")) {
                    	if (fc.isA(HashMap.class)) {
                    		HashMap map = (HashMap) fc.getObject(true);
                    		if (map != null) {
                    			params[0] = ConversionUtils.convertParamToObject(params[0], true);
                    			params[1] = ConversionUtils.convertParamToObject(params[1], true);
                    			map.put(params[0], params[1]);
                    		}
                    	} else if (fc.isA(Hashtable.class)) {
                    		Hashtable table = (Hashtable) fc.getObject(true);
                    		if (table != null) {
                    			params[0] = ConversionUtils.convertParamToObject(params[0], true);
                    			params[1] = ConversionUtils.convertParamToObject(params[1], true);
                    			table.put(params[0], params[1]);
                    		}
                    	}
                    }
                    if (fc.isSubclassOf(AbstractAction.class) && methodName.equals("putValue")) {
                    	ActionStub stub = (ActionStub) fc.getObject(true);
                    	if (stub != null) {
                    		stub.handlePropertySetter(params, fc, this, mic);
                    	}
                    }
                    Object res = null;
                    if (fc.isJFaceFormToolkit()) {
                        res = fc.invokeOnFormToolkit(methodName, params, mic, null);
                    }
                    if (fc.isJFaceForm()) {
                        res = fc.invokeOnForm(methodName, params, mic, null);
                    }
                    if (fc.isJFaceManagedForm()) {
                        res = fc.invokeOnManagedForm(methodName, params, this, mic);
                    }
                    if (res instanceof FormComponent) {
                        FormComponent resFC = (FormComponent) res;
                        System.out.println("FORM COMP CREATED (2) " + resFC + ", " + getCodeForNode(mic));
                        setConnectionNode(resFC.getName(), mic);
                    }
                }

                if (jiglooPlugin.DEBUG)
                    System.out.println("HANDLING METHOD " + methodName + " for par=" + par + ", " + parName + ", mic="
                            + mic);
                if (par != null) {
                    String key = METHOD_PREFIX + methodName;
                    if (!par.getIsParameter()) {
                        HashMap map = getPropMap(parName);
                        map.put(key, mic);
                    }
                    String code = getCodeForNode(mic);
                    if (code != null && parName != null)
                        code = JiglooUtils.replace(code, parName + ".", "#%NAME%#.");
                    par.setExtraPropertyCode(key, code);
                    //                    if(jiglooPlugin.INVOKE_NON_SETTERS) {
                    //                        System.err.println("Adding non-setter method "+methodName+", "+par);
                    par.addMethodCall(methodName, getParams(args));
                    //                    }
                }
            }

            if (methBinding != null && (methBinding.getModifiers() & Modifier.STATIC) != 0 && mInv != null) {
                //handle static method calls
                try {
                    String clsName = getFullClassName(methBinding.getDeclaringClass());
                    String smName = mInv.getName().toString();
                    
                    if(OpenSwingHelper.isForbiddenMethod(clsName, smName))
                        return;
                    
                    Class cls = loadClass(clsName, null);
                    //                    System.err.println("Processing static method call "+clsName+", "+smName+", "+mic);
                    if (cls != null) {
                        if (cls.equals(System.class))
                            return;
                        if ( Cacher.isAssignableFrom(JOptionPane.class, cls))
                            return;
                        if ( Cacher.isAssignableFrom(MessageDialog.class, cls))
                            return;
                        FormComponent fc = null;
                        boolean invokeAsStoredMethod = false;
                        Object[] params = getParams(mInv.arguments());
                        Class[] types = ConstructorManager.getTypes(params);
                        for (int i = 0; i < params.length; i++) {
                            if (params[i] instanceof FormComponent) {
                                fc = (FormComponent) params[i];
                                types[i] = fc.getMainClass();
                                params[i] = fc.getObject(true); //v4.0.0
                                //                                params[i] = fc.getObject(false);
                                if (params[i] == null) {
                                    params[i] = fc;
                                    invokeAsStoredMethod = true;
                                }
                            }
                        }
                        Method m = JiglooUtils.findMethod(cls, smName, types);
                        if (invokeAsStoredMethod) {
                            getRootComponent().addMethodCall(m, params);
                        } else {
                            if (m != null) {
                                Object obj = getFormComponentFromMethod(m, null, params, mic);
                                if (obj instanceof FormComponent) {
                                    FormComponent mfc = (FormComponent) obj;
                                }
                            } else if(cls != null 
                            		&& (EventQueue.class.equals(cls) || SwingUtilities.class.equals(cls))
                            		&& ("invokeLater".equals(smName) || "invokeAndWait".equals(smName))) {
                            	if(args != null && args.size() == 1 && args.get(0) instanceof ClassInstanceCreation) {
                            		ClassInstanceCreation cic = (ClassInstanceCreation) args.get(0);
                            		AnonymousClassDeclaration acd = cic.getAnonymousClassDeclaration();
                            		if(acd != null) {
                            			List bd = acd.bodyDeclarations();
                            			for(int i=0; i<bd.size(); i++) {
                            				parseMethod((MethodDeclaration)bd.get(i), "SwingUtilities.run");
                            			}
                            		}
                            	}
                            } else {
                                System.err.println("handleMethodInvocation: No method found " + cls + ", " + smName);
                            }
                        }
                        return;
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        } catch (Throwable e) {
            //if (jiglooPlugin.DEBUG_EXTRA) {
            System.err.println("Error in handleMethodInvocation " + mic + ", " + e);
            e.printStackTrace();
            //}
        }
    }

    private String getFullClassName(ITypeBinding tb) {
    	if(tb.isMember())
    		return tb.getBinaryName();
		return tb.getQualifiedName();
	}

	private void fillInParams(Object[] params, List args) {
    	for (int i = 0; i < params.length; i++) {
    		Expression aexp = (Expression) args.get(i);
    		if(params[i] == null) {
    			params[i] = getValue(aexp, true, true);
    			if (params[i] instanceof ConstructorHolder) {
    				params[i] = createInlineFormComponent(null, (ConstructorHolder) params[i]);
    				setConnectionNode(((FormComponent) params[i]).getName(), aexp);
    			}
    		}
    	}
	}

	private String getArrayElementName(ArrayAccess micExp) {
    	ArrayAccess aa = (ArrayAccess) micExp;
    	return aa.getArray().toString()+"["+getValue(aa.getIndex())+"]";
	}

	private int getIntValue(Expression exp) {
        Object obj = getValue(exp);
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    private String getStringValue(Expression exp) {
        Object obj = getValue(exp);
        if (obj == null)
            return "null";
        return obj.toString();
    }

    /**
     * @param micExp
     * @param lw
     */
    private Object handleGroupLayoutMethodCall(MethodInvocation mic, String methodName, Object obj) {
        LayoutWrapper lw = null;
        LayoutGroup lg = null;
        if (obj instanceof LayoutGroup) {
            lg = (LayoutGroup) obj;
            lw = lg.getLayoutWrapper();
        } else {
            lw = (LayoutWrapper) obj;
        }
        List args = mic.arguments();
        if (methodName.equals("setHorizontalGroup")) {
            Object val = getValue((Expression) args.get(0));
            lw.setHGroup((LayoutGroup) val);
        } else if (methodName.equals("setVerticalGroup")) {
            Object val = getValue((Expression) args.get(0));
            lw.setVGroup((LayoutGroup) val);
        } else if (methodName.equals("linkSize")) {
            Object arg0 = getValue((Expression) args.get(0));
            Object arg1 = args.size() > 1 ? getValue((Expression) args.get(1)) : null;
            ArrayHolder ah = null;
            String key = "linkSize";
            if (arg1 != null) {
                if (arg1 instanceof Integer) {
                    ah = (ArrayHolder) arg0;
                    int orient = ((Integer) arg1).intValue();
                    lw.linkSize(ah.getFCArray(), orient);
                    key += (orient == GroupLayout.VERTICAL ? "|1" : "|2") + ah.toString();
                } else {
                    //java6 GL
                    ah = (ArrayHolder) arg1;
                    int orient = ((Integer) arg0).intValue();
                    lw.linkSize(orient, ah.getFCArray(), false);
                    key += (orient == SwingConstants.VERTICAL ? "|1" : "|2") + ah.toString();
                }
            } else {
                ah = (ArrayHolder) arg0;
                lw.linkSize(ah.getFCArray());
                key += "|3" + ah.toString();
            }
            key = METHOD_PREFIX + key;
            getPropMap(lw.getName()).put(key, mic);
        } else if (methodName.startsWith("add")) {
            Expression exp = mic.getExpression();
            Object val = null;
            if (exp instanceof MethodInvocation) {
                //even if lg is passed in, if exp is a MethodInvocation, need to
                //evaluate it.
                val = getValue(exp);
            } else {
                //exp is (probably) instanceof SimpleName, and lg will be passed in
                val = lg;
            }
            if (val instanceof LayoutGroup) {
            	Object[] params = getParams(mic.arguments());
            	if(!methodName.equals("addPreferredGap") ) {
            		for (int i = 0; i < params.length; i++) {
            			Object p = params[i];
            			if(p instanceof FormComponent) {
            				FormComponent fc = (FormComponent)p;
            				fc.setInMainTree(true);
            				FormComponent par = ((LayoutGroup)val).getLayoutWrapper().getFormComponent();
            				if(par != null)
            					fc.setParent(par, true);
            			}
            		}
            	}
                ((LayoutGroup) val).add(new GroupElement(methodName, params));
                return val;
            }
        } else if (methodName.equals("createSequentialGroup")) {
            return new LayoutGroup("createSequentialGroup", getParams(mic.arguments()), lw, false);
        } else if (methodName.equals("createParallelGroup")) {
            return new LayoutGroup("createParallelGroup", getParams(mic.arguments()), lw, false);
        }
        return null;
    }

    /**
     * Reassign lhsFC as rhsFC
     * @param ret
     * @param fc
     */
    private void reassignAs(FormComponent rhsFC, FormComponent lhsFC, ASTNode mic) {
        reassignAs(rhsFC, lhsFC, mic, true);
    }

    /**
     * Reassign rhsFC as lhsFC, ie: lhsFC = rhsFC
     */
    private void reassignAs(FormComponent rhsFC, FormComponent lhsFC, ASTNode mic, boolean makeNewName) {
        //eg, a method defines a JTabbedPane tabbedPane1,
        //then returns that and panel is assigned to it, so we
        //want to remove tabbedPane as a field
        //		rhsFC.setOriginalName(rhsFC.getName());
        fields.remove(rhsFC.getName());
        String fcName = lhsFC.getName();

        //		rhsFC.setInherited(lhsFC.isInherited());
        if (lhsFC.isAssigned()) {
            String newName = fcName;
            if (makeNewName) {
                newName = getNextAvailableName(fcName + "::RA");
            }
            setFormComponentName(lhsFC, newName);
        } else {
            if (editor.getComponents() != null)
                editor.getComponents().remove(lhsFC);
            if (lhsFC.getParent() != null)
                lhsFC.getParent().remove(lhsFC);
            lhsFC.setParent(null);
        }
        rhsFC.setInline(false);
        rhsFC.setName(fcName);
        fields.put(fcName, rhsFC);
        setAssignmentNode(fcName, mic);
    }

    /**
     * @param par
     * @param val
     * @param inline
     * @return
     */
    public FormComponent createInlineFormComponent(FormComponent par, ConstructorHolder ch) {
        FormComponent fc;
        Constructor con = ch.getConstructor();
        Object[] params = ch.getParams();
        Class cls = con.getDeclaringClass();

        String fn = class2fieldName(cls.getName(), true, "_IL");

        fc = editor.getComponentByName(fn);
        if (fc == null)
            fc = findFormComponent(fn);

        if (fc == null) {
            fc = FormComponent.newFormComponent(editor, cls.getName());
        } else {
            //			System.out.println("CREATE IL FC REASSIGNING "+fc);
        }

        fc.setEditor(editor);
        fc.setName(fn);

        if (par == null && isSWT()) {
            fc.setClassType(FormComponent.TYPE_SWT);
            if (params != null && params.length > 1) {
                if (params[0] instanceof String) {
                    par = findFormComponent((String) params[0]);
                } else if (params[0] instanceof FormComponent) {
                    par = (FormComponent) params[0];
                }
                if (par != null) {
                    par.addChild(fc);
                }
                if (params[1] instanceof Integer) {
                    fc.setStyle(((Integer) params[1]).intValue(), false);
                }
            }
        } else {
            if (isSwing())
                fc.setClassType(FormComponent.TYPE_SWING);
            else if (isSWT())
                fc.setClassType(FormComponent.TYPE_SWT);
            else if (isCWT())
                fc.setClassType(FormComponent.TYPE_CWT);
        }

        fc.setClassName(cls.getName());
        fc.setConstructor(con, params, ch.getCode(), ch);
        setAssignmentNode(fc.getName(), ch.getExpression());

        fc.setExistsInCode(true);
        fc.setInline(true);
        fc.setAssigned(true);
        putField(fn, fc);

        createInlineChildren(fc, ch.getParams());

        return fc;
    }

    private FormComponent convertToFormComponent(Object childParam, Expression exp) {
        if (childParam instanceof String)
            return findFormComponent((String) childParam);
        else if (childParam instanceof FormComponent)
            return (FormComponent) childParam;
        else if (childParam instanceof Component) {
            return createFormComponent(null, (Component) childParam, getCodeForNode(exp));
        } else if (childParam instanceof ConstructorHolder) {
            FormComponent fc = createInlineFormComponent(null, (ConstructorHolder) childParam);
            setConnectionNode(fc.getName(), exp);
            return fc;
        } else {
            String cfn = getCodeForNode(exp);
            JTextArea errorTA = new JTextArea("Error parsing: " + cfn + "\n" + childParam + "\n"
                    + jiglooPlugin.getLastLogMessage());
            errorTA.setLineWrap(true);
            errorTA.setWrapStyleWord(true);
            return createFormComponent(null, errorTA, cfn);
        }
    }

    public FormComponent getFormComponentWithObject(Object obj) {
        if (obj == null)
            return null;
        FormComponent fc = JiglooUtils.getFormComponentWithObject(obj, fields);
        if(fc != null)
            return fc;
        FormComponent root = getRootComponent();
        if (root != null && obj.equals(root.getObject(false)))
            return root;

        return null;
    }

    /**
     * Creates a FormComponent given a Component - used primarily for lines line
     * panel.add(Box.createGlue()) - Box.createGlue() would be the component passed in.
     */
    private FormComponent createFormComponent(FormComponent fc, Component comp, String code) {
        return createFormComponent(fc, comp, code, "_IL", FormComponent.TYPE_SWING);
    }

    public FormComponent createFormComponent(FormComponent fc, FormComponent par, Widget control, ASTNode mic) {
        String cfn = null;
        if (mic != null)
            cfn = getCodeForNode(mic);
        fc = createFormComponent(fc, control, cfn, "_IL", FormComponent.TYPE_SWT);
        fields.put(fc.getName(), fc);
        if (mic != null)
            setConnectionNode(fc.getName(), mic);
        //								System.out.println("CREATING FC FROM PARAM "+fc.getName()+", "+mic);
        fc.initProperties();
        if (fc.getParent() == null || !fc.getParent().equals(par))
            par.addChild(fc);
        return fc;
    }

    private FormComponent createFormComponent(FormComponent fc, Object comp, String code, String suffix, int type) {
        FormComponent fco = getFormComponentWithObject(comp);
        if (fco != null)
            return fco;
        String clsName = comp.getClass().getName();
        String fn = class2fieldName(clsName, true, suffix);

        if (fc == null)
            fc = editor.getComponentByName(fn);

        if (fc != null && fc.isAssigned()) {
            System.out.println("CREATE FORM COMP REASSIGNING " + fc);
        }

        if (fc == null)
            fc = FormComponent.newFormComponent(editor, clsName);

        fc.setEditor(editor);
        fc.setName(fn);

        fc.setClassType(type);
        if (fc.getParent() != null && !fc.getParent().isAbstractFormBuilder() && fc.getComponent() != null) {
            Component fcc = fc.getComponent();
            if (fcc.getParent() != null)
                fcc.getParent().remove(fcc);
        }
        fc.setConstructorCode(code, null);

        if (type == FormComponent.TYPE_SWING)
            fc.setComponent((Component) comp);
        else if (type == FormComponent.TYPE_SWT)
            fc.setControl((Widget) comp);

        fc.setClassName(clsName);
        fc.setInline(true);
        fc.setNonstandardConstructor(true);
        fc.setExistsInCode(true);
        fc.setAssigned(true);
        putField(fn, fc);
        return fc;
    }

    /**
     * Creates an inline FormComponent given a non=-visual object.
     */
    private FormComponent createFormComponent(Object obj, String code) {
        Class cls = null;
        String clsName = null;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            cls = obj.getClass();
        }
        clsName = cls.getName();
        String fn = class2fieldName(clsName, true, "_IL");

        FormComponent fc = editor.getComponentByName(fn);

        if (fc != null && fc.isAssigned()) {
            System.out.println("CREATE FORM COMP REASSIGNING " + fc);
        }

        if (fc == null)
            fc = FormComponent.newFormComponent(editor, clsName);

        fc.setEditor(editor);
        fc.setName(fn);

        fc.setConstructorCode(code, null);
        if (!(obj instanceof Class)) {
            //v4.0.0
            if (obj instanceof Widget) {
                fc.setControl((Widget) obj);
            } else if (jiglooPlugin.canUseSwing() && obj instanceof Component) {
                fc.setComponent((Component) obj);
            } else {
                fc.setNonVisualObject(obj);
            }
        }
        fc.setClassName(clsName);
        fc.setInline(true);
        fc.setNonstandardConstructor(true);
        fc.setExistsInCode(true);
        fc.setAssigned(true);
        putField(fn, fc);
        if (fc.isAbstractFormBuilder()) {
            fc.getBuilder(); //3.8.1
            try {
                fc.makeComponent(fc.getClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            fc.setNonVisualObject(null);
        }
        return fc;
    }

    private FormComponent handleCreateButton(List args, FormComponent button) {
        Object[] params = getParams(args);
        //        System.err.println("CREATE BUTTON " + params[1]+ ", " + params[2] + ", "+ params[3]);
        if (params.length == 4 //&& (params[0] instanceof
                // Composite
                //|| params[0] instanceof String
                //|| params[0] instanceof FormComponent)
                && params[1] instanceof Integer && params[2] instanceof String && params[3] instanceof Boolean) {

            FormComponent bb = findFormComponent("buttonBar");
            //System.err.println("FOUND BB (1) "+bb);
            if (bb == null) {
                bb = editor.getComponentByName("buttonBar");
                //System.err.println("FOUND BB (2) "+bb);
                if (bb == null)
                    bb = FormComponent.newFormComponent(editor, OrderableComposite.class.getName());
                bb.setExistsInCode(true);
                //bb.setBlockName(blockStr);
                bb.setClassType(FormComponent.TYPE_SWT);
                bb.setSpecialType(FormComponent.DIALOG_BUTTON_BAR);
                bb.setName("buttonBar");
                getRootComponent().addChild(bb, false);
                bb.setClassName(OrderableComposite.class.getName());
                putField("buttonBar", bb);
                editor.addComponent(bb);
            }

            if (button == null) {
                int ccic = bb.getChildCountInCode();
                if (ccic < bb.getNonInheritedChildCount()) {
                    button = bb.getNonInheritedChildAt(ccic);
                } else {
                    button = FormComponent.newFormComponent(editor, Button.class.getName());
                }
            }
            button.setClassType(FormComponent.TYPE_SWT);
            button.setSpecialType(FormComponent.DIALOG_BUTTON);
            bb.addChild(button, false);
            button.setExistsInCode(true);
            button.setClassName(Button.class.getName());
            button.setPropertyValue("text", params[2]);
            //button.setPropertyValueSimple("text", params[2]);

            //System.err.println("BUTTON BAR!!!");
            //bb.displayBranch();
        }
        return button;
    }

    public String getText(int start, int length) {
        return getText(start, length, true);
    }

    public String getText(int start, int length, boolean alert) {
        if (start < 0 || length < 0 || start + length > getLength()) {
            if (alert)
                jiglooPlugin.writeToLog("ERROR: TRYING TO GET TEXT PAST END OF BUFFER, " + start + ", "
                        + (start + length) + ", " + getLength());
            return "???";
        }
        return buff.substring(start, start + length);
    }

    private boolean isValid(int pos) {
        return (pos >= 0 && pos <= getLength() - 1);
    }

    private char getChar(int pos) { //		if (pos < 0 || pos >=
        // getLength()-1)
        if (!isValid(pos))
            return '?';
        return buff.charAt(pos); //return wcbuff.getChar(pos);
    }

    private char match(char chr, String chrs) {
        for (int i = 0; i < chrs.length(); i++) {
            if (chr == chrs.charAt(i))
                return chr;
        }
        return 0;
    }

    private Comment getEnclosingComment(int pos) {
        return (Comment) getEnclosingNodeKey(comments, pos);
    }

    private Comment getCommentBetween(int start, int end) {
        Iterator it = comments.keySet().iterator();
        while (it.hasNext()) {
            Comment obj = (Comment) it.next();
            if (obj.isBetween(start, end))
                return obj;
        }
        return null;
    }

    private Object getEnclosingNodeKey(HashMap map, int pos) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Comment) {
                if (((Comment) obj).encloses(pos))
                    return obj;
            } else {
                ASTNode c = (ASTNode) map.get(obj);
                if (c.getStartPosition() <= pos && c.getStartPosition() + c.getLength() >= pos) {
                    return obj;
                }
            }
        }
        return null;
    }

    private FormComponent getLastElementInside(HashMap map, int start, int end) {
        ASTNode last = null;
        FormComponent lastFC = null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Comment) {
                continue;
            } else {
                ASTNode c = (ASTNode) map.get(obj);
                if (c.getStartPosition() >= start && c.getStartPosition() + c.getLength() <= end) {
                    if (last == null || c.getStartPosition() > last.getStartPosition()) {
                        last = c;
                        lastFC = findFormComponent((String) obj);
                    }
                }
            }
        }
        return lastFC;
    }

    private int findNextNonWhite(int pos) {
        while (isWhiteSpace(pos))
            pos++;
        return pos;
    }

    private int findNextChar(String txt, int pos) {
        while (pos < txt.length() && (isWhiteSpace(txt.charAt(pos)) || isEOL(txt.charAt(pos))))
            pos++;
        if (pos == txt.length())
            return -1;
        return pos;
    }

    private int findNextMatch(int pos, String match) {
        while (pos < getLength()) {
            pos = indexOf(match, pos);
            if (pos < 0)
                return pos;
            if (isHidden(pos)) {
                pos++;
            } else {
                Comment c = getEnclosingComment(pos);
                if (c != null) {
                    pos = c.getEndPosition();
                } else {
                    return pos;
                }
            }
        }
        return -1;
    }

    private int findNextChar(int pos, String chrs, boolean forwards) {
        if (forwards) {
            while (pos < getLength()) {
                if (match(getChar(pos), chrs) == 0) {
                    pos++;
                } else {
                    Comment c = getEnclosingComment(pos);
                    if (c != null) {
                        pos = c.getEndPosition();
                    } else {
                        return pos;
                    }
                }
            }
            return -1;
        } else {
            while (pos > 0) {
                if (match(getChar(pos), chrs) == 0) {
                    pos--;
                } else {
                    Comment c = getEnclosingComment(pos);
                    if (c != null) {
                        pos = c.startPosition - 1;
                    } else {
                        return pos;
                    }
                }
            }
            return -1;
        }
    }

    private int getLength() {
        return buff.length(); //return wcbuff.getLength();
    }

    public String getTokenBeforeNode(ASTNode node) {
        int pos = node.getStartPosition() - 1;
        String token = "";
        while (pos > 0 && (isWhiteSpace(pos) || isEOL(pos)))
            pos--;
        while (pos > 0 && !isWhiteSpace(pos) && !isEOL(pos)) {
            token = buff.substring(pos, pos + 1) + token;
            pos--;
        }
        return token;
    }

    public String getCodeForNode(Object nodeObj) {
        try {
            if (nodeObj instanceof ASTNode) {
                return getText(((ASTNode) nodeObj).getStartPosition(), ((ASTNode) nodeObj).getLength());
            } else {
                return getText(((Comment) nodeObj).startPosition, ((Comment) nodeObj).length);
            }
        } catch (Throwable e) {
            System.err.println("No code for node " + nodeObj);
            if (jiglooPlugin.DEBUG) {
                new Exception().printStackTrace();
            }
            return "";
        }
    }

    public void removeMethodFromCode(String methodName) {
        MethodDeclaration md = (MethodDeclaration) getMethodNode(methodName);
        if (md == null) {
            System.err.println("***ERROR in removeMethodFromCode " + methodName + " not found");
            return;
        }
        if (isProtected(md))
            return;

        if (jiglooPlugin.DEBUG)
            System.err.println("REMOVE METHOD FROM CODE " + methodName);

        int pos = md.getStartPosition();
        removeNode(md);
        removeMethodReturn(methodName);
        methodMap.remove(methodName + "_");
        //removeExcessNewlines(pos);
    }

    public void removeResourceFromCode(Object res, String guessedFieldName) {
        try {
            String name = null;
            //System.out.println("REM RESFR CODE " + res + ", "
            //     + guessedFieldName);
            name = (String) getKeyForValue(fields, res);
            if (name == null)
                name = guessedFieldName;
            ASTNode node = (ASTNode) findClosestObject(elementAssignments, name);
            if (node != null)
                removeNode(node);
            //System.out.println("REM RESFR CODE node=" + node);
            int pos = 0; //indexOf(name + ".dispose();");
            name = JiglooUtils.getUnqualifiedName(name);
            while (true) {
                pos = indexOf(" " + name + ".");
                if (pos < 0)
                    pos = indexOf("\t" + name + ".");
                if (pos < 0)
                    break;
                pos = getStartOfLine(pos);
                int end = getStartOfNextLine(pos);
                //System.out.println("REMOVING " + getText(pos, (end - pos)));
                replaceText("", pos, end - pos, true);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }

    private MethodDeclaration getMethodContainingAssignment(String elementName) {
        ASTNode assNode = getAssignmentNode(elementName);
        if (assNode == null)
            assNode = getConnectionNode(elementName);
        if (assNode != null)
            return getEnclosingMethod(assNode.getStartPosition());
        return null;
    }

    private boolean areInSameMethod(ASTNode node1, ASTNode node2) {
        ASTNode m1 = getEnclosingMethod(node1.getStartPosition());
        ASTNode m2 = getEnclosingMethod(node2.getStartPosition());
        if (m1 != null) {
            if (m2 == null)
                return false;
            return (m1.equals(m2));
        } else {
            if (m2 == null)
                return true;
            return false;
        }
    }

    public void removeFromCode(IFormPropertySource element) {
        removeFromCode(element, false, true, null);
    }

    public void removeFromCode(IFormPropertySource element, boolean copy, boolean removeChildrenFromEditor,
            IProgressMonitor pm) {

        FormComponent fc = null;
        if (element instanceof FormComponent) {
            fc = (FormComponent) element;
        }
        if (fc != null) {
            editor.getBundleManager().remove(fc);
            //remove children first, so that if code is blocked in braces then
            // the container will have the children CodeBlocks removed first
            //so that it's code in its CodeBlock will not contain the
            // childrens' code.
            Vector comps = fc.getChildren();
            if (comps != null) {
                for (int i = 0; i < comps.size(); i++)
                    removeFromCode((FormComponent) comps.elementAt(i), copy, removeChildrenFromEditor, pm);
            }
            if (pm != null) {
                pm.setTaskName("Updating " + fc.getNameInCode() + "...");
                pm.worked(1);
            }
        }

        String name = element.getName();

        String mn = null;

        int lastPos = -1;
        boolean isInSB = false;

        ASTNode assNode = null;
        //find out which method contains this element's assignment
        //(so we can avoid removing nodes related to this element
        //which occur in different methods)

        MethodDeclaration assMethod = getMethodContainingAssignment(name);

        CodeBlock cb = null;

        boolean isAFAction = false;
        MethodDeclaration getterMethodDec = null;
        
        if (fc != null) {
            int[] blockPosns = getBlockPositions(fc);
            isInSB = blockPosns != null;
            
            if(editor.isPartOfAppFrameworkApplication() && fc.isSubclassOf(AbstractAction.class)) {
                isAFAction = true;
                mn = fc.getNameInCode();
            } else {
                mn = convertToMethod(name, name, true);
            }
            HashMap methodNodeMap = null;

            copy = editor.isInCutMode();

            if (mn != null) {
                //store old method code
                getterMethodDec = (MethodDeclaration) getMethodNode(mn);
                if (getterMethodDec != null) {
                    int bs = getterMethodDec.getBody().getStartPosition();
                    int be = bs + getterMethodDec.getBody().getLength();
                    int offset = bs - getterMethodDec.getStartPosition();

                    //get code *before* calling getContainedNodes (which will remove nodes)
                    String code = getCodeForNode(getterMethodDec);
                    //fill methodNodeMap, and remove nodes
                    methodNodeMap = getContainedNodes(bs, be, true);
                    if (copy) {
                        cb = new CodeBlock(fc, methodNodeMap, code, offset, mn, true);
                        methodCode.put(fc.getName(), cb);
                        if (jiglooPlugin.DEBUG)
                            System.out.println("SAVING CODE FOR METHOD " + mn + ", " + getCodeForNode(getterMethodDec));
                    }
                }
            } else if (isInSB) {
                //store old code for element's block
                assNode = getAssignmentNode(name);
                int start = blockPosns[0];
                int end = blockPosns[1] + 1;
                String code = getText(start, end - start);
                //fill methodNodeMap, and remove nodes
                methodNodeMap = getContainedNodes(start, end, true);
                if (copy) {
                    cb = new CodeBlock(fc, methodNodeMap, code, 0, mn, false);
                    if (jiglooPlugin.DEBUG)
                        System.out.println("REMOVE NODE: " + name + " ASSNODE=" + assNode + ", " + name);
                    blockCode.put(name, cb);
                    if (jiglooPlugin.DEBUG)
                        System.out.println("SAVING CODE FOR " + name + " BLOCK " + code);
                }
                start = getStartOfLine(start);
                end = getStartOfNextLine(end);
                replaceText("", start, end - start, true);
            }

            removeNonChildFormComponents(methodNodeMap, fc, removeChildrenFromEditor);

            /**
             * Turns out that a layoutWrapper might be entered in the setPropMap
             * map twice, under jPanel1Layout and %1.%2.jPanel1Layout, so that
             * when getContainedNodes removes nodes from one map it leaves them
             * in the other, so we use  methodNodeMap (which contains the removed
             * nodes) to make sure we remove the other set of nodes
             */
            if (methodNodeMap != null) {
                HashMap layoutMap = getPropMap(fc.getLayoutWrapper().getName());
                Iterator it = layoutMap.keySet().iterator();
                Vector rem = new Vector();
                while (it.hasNext()) {
                    Object key = it.next();
                    Object node = layoutMap.get(key);
                    if (methodNodeMap.containsValue(node))
                        rem.add(key);
                }
                for (int i = 0; i < rem.size(); i++)
                    layoutMap.remove(rem.elementAt(i));
            }

            removeFromCode(fc.getLayoutWrapper(), copy, removeChildrenFromEditor, pm);
            removeFromCode(fc.getLayoutDataWrapper(), copy, removeChildrenFromEditor, pm);
            //the event wrapper code is removed when the properties are deleted
            ASTNode connNode = getConnectionNode(name);
            if (connNode != null && !isProtected(connNode)) {
                removeNode(connNode);
                removeFromMap(elementConnections, name);
            }
        }

        if (jiglooPlugin.DEBUG)
            System.out.println("REMOVE NODE: " + name + " setPropMethods = " + setPropMethods);

        HashMap map = getPropMap(name);
        Iterator it = map.keySet().iterator();
        Vector rem = new Vector();

        //remove property nodes
        while (it.hasNext()) {
            String key = (String) it.next();
            ASTNode mic = (ASTNode) map.get(key);

            if (!isProtected(mic)) {
                if (key.indexOf(EVENT_HANDLER) < 0) {
                    //don't remove event handler methods since they will be
                    // removed when the anonymous listener class is removed

                    if (jiglooPlugin.DEBUG)
                        System.err.println("REMOVE FROM CODE " + key + ", " + mic);

                    if (mic != null) {
                        //assMethod will be null if this is an inherited element.
                        if (assMethod == null || isContainedBy(mic, assMethod)) {
                            lastPos = removeNode(mic);
                        }
                    }
                }
                rem.add(key);
            }
        }
        for (int i = 0; i < rem.size(); i++)
            map.remove(rem.elementAt(i));

        //the last property setter may be in a block by itself (eg. setMenu in old code)
        if (lastPos != -1) {
            if (usesGetters(fc) || isInSB) {
                removeSurroundingBraces(lastPos, element);
            }
        }

        //assign assNode *after* getContainedNodes is called (which may have removed it from elementAssignments map).
        assNode = getAssignmentNode(name);

        if(isAFAction && getterMethodDec != null) {
            assNode = getterMethodDec;
            System.err.println("Removing action method dec\n"+getCodeForNode(getterMethodDec));
        }
        
        if (assNode != null && !isProtected(assNode)) {
            //remove assignment node last (and set lastPos) since this *should* be inside braces
            if (jiglooPlugin.DEBUG)
                System.err.println("REMOVE ASS NODE " + name + ", " + getCodeForNode(assNode));
            lastPos = removeNode(assNode);
            removeFromMap(elementAssignments, name);
        }

        if (lastPos != -1) {
            if (usesGetters(fc) || isInSB) {
                removeSurroundingBraces(lastPos, element);
            }
        }

        if (mn != null && getters.containsKey(mn)) {
            removeMethodFromCode(mn);
            getters.remove(mn);
        }

        ASTNode decNode = (ASTNode) fieldDecs.get(name);
        if (decNode != null && !isProtected(decNode)) {
            removeFromMap(fieldDecs, name);
            removeNode(decNode);
        }
        removeField(name);

    }

    private boolean isInEventHandler(ASTNode node) {
        for (int i = 0; i < eventHandlers.size(); i++) {
            ASTNode ehn = (ASTNode) eventHandlers.elementAt(i);
            if (isContainedBy(node, ehn))
                return true;
        }
        return false;
    }

    private boolean isContainedBy(ASTNode node, ASTNode container) {
        if (container == null || node == null)
            return false;
        return isContainedBy(node.getStartPosition(), container);
    }

    private boolean isContainedBy(int pos, ASTNode container) {
        if (container == null)
            return false;
        int st = container.getStartPosition();
        int end = st + container.getLength();
        return pos > st && pos < end;
    }

    private int removeExcessNewlines(int pos) {
        if (!isEOL(pos) && !isWhiteSpace(pos))
            return pos;
        int start = pos;
        int numNLs = 0;
        while (isEOL(start) || isWhiteSpace(start)) {
            if (isEOL(start))
                numNLs++;
            start--;
        }
        start++;

        int end = pos;
        while (isEOL(end) || isWhiteSpace(end)) {
            if (isEOL(end))
                numNLs++;
            end++;
        }
        end = getStartOfLine(end);
        if (end <= start)
            return pos;

        if (NL.length() == 2)
            numNLs /= 2;

        if (numNLs >= 2) {
            if (jiglooPlugin.DEBUG)
                System.out.println("REMOVE EXCESS NLS " + showControls(getText(start - 20, end - start + 40)));
            replaceText(NL + NL, start, end - start, true);
            return start;
        }

        return pos;

    }

    private boolean removeSurroundingBraces(int pos, IFormPropertySource elem) {
        String startTag = "{";
        String endTag = "}";
        if (!jiglooPlugin.useBlankLines()) {
            startTag = getBlockDelimiterStart(elem.getNameInCode(), false);
            endTag = getBlockDelimiterEnd(elem.getNameInCode(), false);
        }
        int endBrace = pos;
        while (endBrace < getLength() && (isEOL(endBrace) || isWhiteSpace(endBrace)))
            endBrace++;
        if (jiglooPlugin.DEBUG)
            System.out.println("removeSurroundingBraces: context = " + showControls(getText(endBrace, endTag.length()))
                    + ", " + startTag + ", " + endTag);
        if (getText(endBrace, endTag.length()).equals(endTag)) {
            endBrace += endTag.length() - 1;
            int lastPos = pos;
            while (lastPos > 0 && (isWhiteSpace(lastPos) || isEOL(lastPos)))
                lastPos--;
            lastPos = lastPos - startTag.length() + 1;
            if (getText(lastPos, startTag.length()).equals(startTag)) {
                lastPos--;
                while (lastPos > 0 && isWhiteSpace(lastPos))
                    lastPos--;
                if (isEOL(lastPos)) {
                    while (lastPos > 0 && isEOL(lastPos))
                        lastPos--;
                } else {
                    endBrace++;
                    while (endBrace < getLength() && isWhiteSpace(endBrace))
                        endBrace++;
                    if (isEOL(endBrace)) {
                        while (endBrace < getLength() && isEOL(endBrace))
                            endBrace++;
                    }
                }
                //lastPos is one char *before* the one we want to delete from
                //endBrace is one char *after* one we wan to delete to.
                if (endBrace - lastPos == 0)
                    return false;
                
                if(isTryBlock(lastPos + 2, endBrace - lastPos - 2))
                	return false;
                
                if(isMethodBlock(lastPos + 2, endBrace - lastPos - 2))
                	return false;
                
                replaceText("", lastPos + 1, endBrace - lastPos - 1, true);
                return true;
            }
        }
        return false;
    }

    private String removeWhiteSpace(String str) {
        str = JiglooUtils.replace(str, " ", "");
        str = JiglooUtils.replace(str, "\t", "");
        return str;
    }

    private String getLineFor(int pos) {
        if (pos < 0)
            return "";
        int start = getStartOfLine(pos);
        int end = getEndOfLine(pos);
        if (start > end)
            return "";
        Comment c = getCommentBetween(start, end);
        if (c != null) {
            if (c.startPosition < start && c.getEndPosition() > end)
                return "";
            else if (c.startPosition < start)
                start = c.getEndPosition() + 1;
            else
                end = c.startPosition - 1;
        }
        if (end < start)
            return "";
        while (end > start && isWhiteSpace(end))
            end--;
        return getText(start, end - start + 1);
    }

    private void repairJTabbedPaneParentConns(FormComponent fc) {
        if (!jiglooPlugin.canUseSwing() || !fc.isSwing())
            return;
        FormComponent par = fc.getParent();

        if (par == null || !par.isSubclassOf(JTabbedPane.class))
            return;

        for (int i = 0; i < par.getChildCount(); i++) {

            FormComponent child = par.getChildAt(i);

            if (child.equals(fc))
                continue;

            String cn = child.getName();
            ASTNode connNode = (ASTNode) findObject(elementConnections, cn);
            boolean repConNode = false;

            String key = METHOD_PREFIX + "setTitleAt-" + i;
            ASTNode setTab = getPropSetter(par, key);
            if (setTab != null && !isProtected(setTab)) {
                //System.out.println("REMOVING TAB TITLE " +
                // getCodeForNode(setTab));
                removeNode(setTab);
                HashMap pMap = getPropMap(par.getName());
                if (pMap != null)
                    pMap.remove(key);
                repConNode = true;
            }

            if (connNode != null && !isProtected(connNode)) {
                String cncode = getCodeForNode(connNode);
                if (cncode != null && cncode.indexOf("insertTab(") >= 0)
                    repConNode = true;

                if (repConNode) {
                    String code = getAddToParentCode(child);
                    updateNodeBody(connNode, code);
                }
            }
        }
    }

    private String getProtectStartTag() {
        return jiglooPlugin.getProtectStartTag();
    }

    private String getProtectEndTag() {
        return jiglooPlugin.getProtectEndTag();
    }

    private String getProtectLineTag() {
        return jiglooPlugin.getProtectLineTag();
    }

    private String getHiddenStartTag() {
        return jiglooPlugin.getHiddenStartTag();
    }

    private String getHiddenEndTag() {
        return jiglooPlugin.getHiddenEndTag();
    }

    private String getHiddenLineTag() {
        return jiglooPlugin.getHiddenLineTag();
    }

    public boolean isProtected(ASTNode node) {
        if (node == null)
            return false;
        return isInBlocks(node.getStartPosition(), protectedBlocks);
        //return isMarkedBy(node.getStartPosition(), getProtectStartTag(),
        // getProtectEndTag(), getProtectLineTag());
    }

    public boolean isHidden(ASTNode node) {
        if (node == null)
            return false;
        return isHidden(node.getStartPosition());
    }

    public boolean isHidden(int pos) {
        //        if(initComponentsMethod != null && initGUIMethod != null) {
        //            if(isContainedBy(pos, initComponentsMethod))
        //                return true;
        //        }
        return isInBlocks(pos, hiddenBlocks);
    }

    public boolean isInBlocks(int pos, Vector blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            int[] block = (int[]) blocks.elementAt(i);
            if (pos >= block[0] && pos < block[1])
                return true;
        }
        return false;
    }

    public boolean isMarkedBy(int pos, String startTag, String endTag, String lineTag) {
        if (startTag.startsWith("//"))
            startTag = startTag.substring(2);
        if (endTag.startsWith("//"))
            endTag = endTag.substring(2);
        if (startTag.startsWith("//"))
            lineTag = lineTag.substring(2);

        Iterator it = comments.keySet().iterator();
        Comment pre = null, post = null;
        int eol = getEndOfLine(pos);
        while (it.hasNext()) {
            Comment c = (Comment) it.next();
            String cmt = c.code;
            //System.out.println("CHECKING CMT " + cmt + ", " + c.startPosition
            // + ", " + pos + ", " + eol);
            if (cmt.indexOf(lineTag) >= 0 && c.startPosition < eol && c.startPosition > pos) {
                return true;
            }
            if (cmt.indexOf(startTag) >= 0 || cmt.indexOf(endTag) >= 0) {
                if (c.startPosition > pos && (post == null || c.startPosition < post.startPosition)) {
                    post = c;
                }
                if (c.startPosition < pos && (pre == null || c.startPosition > pre.startPosition)) {
                    pre = c;
                }
            }
        }
        if (pre == null || post == null)
            return false;
        String preCmt = pre.code;
        String postCmt = post.code;
        if (preCmt.indexOf(startTag) >= 0 && postCmt.indexOf(endTag) >= 0) {
            return true;
        }
        return false;
        //		int p1 = indexOf(lineTag, pos);
        //		int p2 = indexOf("\n", pos);
        //		if (p1 >= 0 && p1 < p2)
        //			return true;
        //		int pe = indexOf(endTag, pos);
        //		if (pe < 0)
        //			return false;
        //		int ps = indexOf(startTag, pos);
        //		if (ps >= 0 && ps < pe)
        //			return false;
        //		ps = indexOf(startTag);
        //		if (ps < 0 || ps > pos)
        //			return false;
        //		while (ps >= 0) {
        //			p2 = indexOf(startTag, ps + 1);
        //			if (p2 >= 0 && p2 < pos)
        //				ps = p2;
        //			else
        //				break;
        //		}
        //		pe = indexOf(endTag, ps);
        //		if (pe < 0 || pe < pos)
        //			return false;
        //		return true;
    }

    private void updateInlineAssignmentPosition(FormComponent fc) {
        if (!fc.isInline())
            return;
        ASTNode connNode = getConnection(fc);
        String connCode = getCodeForNode(connNode);
        String constCode = fc.getConstructorCode();
        int pos = connCode.indexOf(constCode);
        if (pos >= 0) {
            ASTNode assNode = getAssignment(fc);
            if (assNode != null)
                assNode.setSourceRange(connNode.getStartPosition() + pos, constCode.length());
        } else {
            System.err.println("Error getting location of " + constCode + " in " + connCode);
        }
    }

    public void repairParentConnectionInCode(FormComponent fc) {
        repairParentConnectionInCode(fc, true, true);
    }

    public String getConnectionCode(FormComponent fc) {
        ASTNode node = getConnectionNode(fc.getName());
        if (node == null)
            return null;
        return getCodeForNode(node);
    }

    public boolean repairInlineAssignment(FormComponent fc) {
        return repairInlineAssignment(fc, false);
    }

    private boolean repairInlineAssignment(FormComponent fc, boolean force) {
        //for the case when an element is created in the "add" method
        //eg panel.add(new JLabel("Hi"));
        if (!fc.isInline())
            return false;
        if (fc.isBuilderComponent())
            return false;

        if (!force && fc.getSetProperties() != null && fc.getSetProperties().size() == 1
                && "text".equals(fc.getSetProperties().elementAt(0))) {
            ASTNode ass = getAssignment(fc);
            if (ass != null) {
                String cc = "new " + fc.getClassNameForCode() + "(\"" + fc.getPropertyValue("text") + "\")";
                fc.setConstructorCode(cc, null);
                updateNodeBody(ass, cc);
                return true;
            }
        }

        String name = fc.getName();
        ASTNode anode = getAssignmentNode(name);

        if (anode != null) {
            fc.setInline(false);
            removeFromMap(elementAssignments, name);

            //keep anode in elementAssignments, but under a temporary
            //name, so that it will be re-positioned correctly by the
            //addToCode method.
            String tmpName = name + "%%%%%%%";
            setAssignmentNode(tmpName, anode);

            fc.setAssigned(false);
            addToCode(fc, null, false, false);

            //need to update anode *after* addToCode called in case
            // getters are being used
            updateNodeBody(anode, convertToMethod(name, fc.getNameInCode(), false));
            removeFromMap(elementAssignments, tmpName);

        } else {
            ASTNode cnode = getConnectionNode(name);
            if (cnode == null)
                return false;
            int pos = getStartOfLine(cnode.getStartPosition());
            removeNode(cnode, true);
            removeSurroundingBraces(pos, fc);
            removeFromMap(elementConnections, name);
            removeFromMap(elementAssignments, name);

            fields.remove(name);
            fc.setInline(false);
            //            String newName = getNextAvailableName(
            //            		JiglooUtils.deCapitalize(fc.getShortClassName()));
            //            fc.setName(newName);

            addToCode(fc);
        }

        //    	editor.setNeedsReload(true);
        //    	editor.reload();
        return true;
    }

    public void repairParentConnectionNode(FormComponent fc) {
        if (parsing || addActionDialogOpen)
            return;
        if (fc.getParent() == null || fc.getParent().usesGroupLayout()) {
            return;
        }
        ASTNode node = getConnection(fc);
        if (node == null)
            return;
        String code = getAddToParentCode(fc);
        if (code == null)
            return;

        //if connection-node code is "", return now!
        if ("".equals(code)) {
            if (node != null && !isDeleted(node)) {
                removeNode(node);
                removeFromMap(elementConnections, fc.getName());
            }
            return;
        }

        updateNodeBody(node, code);
        updateInlineAssignmentPosition(fc);
        //        indentNode(node);
    }

    private boolean isInGetter(FormComponent fc) {
        ASTNode assNode = getAssignmentNode(fc.getName());
        if (getters == null || assNode == null)
            return false;
        String gmn = convertToMethod(fc.getName(), fc.getName(), true);
        if (gmn == null)
            return false;
        ASTNode getter = (ASTNode) getMethodNode(gmn);
        if (getter == null)
            return false;
        if (isContainedBy(assNode, getter))
            return true;
        return false;
    }

    /**
     * returns true if node1 is before node2
     */
    private boolean isBefore(ASTNode node1, ASTNode node2) {
        if (node1 == null || node2 == null)
            return false;
        return node1.getStartPosition() < node2.getStartPosition();
    }

    public void repairParentConnectionInCode(FormComponent fc, boolean constraintChanged, boolean moveElement) {
        if (parsing)
            return;
        try {
            if (!editor.hasComponent(fc)) {
                removeFromCode(fc);
                return;
            }
            if (!fc.isVisual())
                return;

            if (!fc.isMenuComponent() 
                    && (fc.getParent() == null || fc.getParent().equals(editor.getExtraCompRoot())))
                return;

            if(fc.equals(getRootComponent()))
            	return;
            
            String code = null;
            if (constraintChanged) {
                code = getAddToParentCode(fc);
                if (code == null && !(fc.isSwingInSwt() || fc.isSwtInSwing()))
                    return;
            }

            boolean useGetter = false; //usesGetters();
            if (isInGetter(fc))
                useGetter = true;

            if (usesGetters(fc))
                useGetter = true;

            if (!isInGetter(fc))
                useGetter = false;

            //			if fc uses a getter, don't repair the body, but only move the
            // connection node

            if (fc.isSwing())
                repairJTabbedPaneParentConns(fc);

            //repair layout data code in case we changed layouts when
            //changing parents
            if (fc.isSWT()) {
                repairInCode(fc.getLayoutDataWrapper(), REPAIR_ALL);
                updateInCode(fc, "layoutData");
                repairConstructor(fc);
            }

            FormComponent fcPar = fc.getParent();

            boolean noParent = false;
            if (fcPar == null) {
                noParent = true;
                fcPar = getRootComponent();
            }

            ASTNode parAssNode = null;
            FormComponent codePar = getCodeParent(fcPar);
            
            if (codePar != null) {
                parAssNode = getAssignment(codePar);
            }

            int pos = fc.getNonInheritedIndexInParent();
            if (pos < 0)
                pos = 0;

            FormComponent sib = null;
            FormComponent sib2 = null;
            ASTNode sibAss = null, sib2Ass = null;
            boolean after = true;

            int pcc = fcPar.getNonInheritedChildCount();

            if (pos >= 0 && pcc > pos && !noParent) {
                sib2 = fcPar.getNonInheritedChildAt(pos + 1);
                if (sib2 != null) {
                    sib2Ass = (ASTNode) findObject(elementAssignments, sib2.getName());
                    ASTNode tmp = (ASTNode) findObject(elementConnections, sib2.getName());
                    if (tmp != null)
                        sib2Ass = tmp;
                }
            }

            if (pos > 0) {
                sib = fcPar.getNonInheritedChildAt(pos - 1);
                if (sib != null) {
                    sibAss = (ASTNode) findObject(elementAssignments, sib.getName());
                    ASTNode tmp = (ASTNode) findObject(elementConnections, sib.getName());
                    if (tmp != null)
                        sibAss = tmp;
                }
            }

            //refNode is the node after which the assignment block will be
            // positioned
            ASTNode refNode = null;

            int toPos = -1;

            //if useGetter, don't use refNode, since won't be moving element,
            // only connection node

            //refNode must be *after* the parent's assignment, if it exists

            int lastPos = -1;
            if (sib2Ass != null)
                lastPos = sib2Ass.getStartPosition();

            if (pos == 0) {

                ASTNode lns = getLastNode(fcPar, lastPos);
                if(lns == null)
                    lns = getLastNode(codePar, lastPos);
                
                if (parAssNode != null || lns != null) {
                    if (parAssNode != null)
                        refNode = parAssNode;
                    if (lns != null)
                        refNode = lns;
                    after = true;
                }
            }

            if (refNode == null) {
                if (sibAss != null) {
                    //sib may be non-null, but may not yet exist in code!
                    //(if pasting a component with several children, and sib
                    //being the sibling after this one)

                    ASTNode lns = getLastNode(sib, lastPos);
                    //and if there is no sib2, then use lns as the refNode
                    if (areInSameMethod(lns, sibAss)) {
                        refNode = lns;
                        after = true;
                    } else {
                        refNode = sibAss;
                        after = true;
                    }

                } else if (sib2Ass != null) {
                    refNode = sib2Ass;
                    after = false;
                }
            }

            if (refNode == null) {
                if (initGUIMethod == null)
                    addInitGUIMethod();
                if (refNode == null) {
                    toPos = getStartOfInitGUI();
                    after = false;
                }
            }

            //System.out.println("REP PAR CONN " + fc + ", sib=" + sib + "
            // refNode=" + getCodeForNode(refNode));

            if (!editor.isToggling() && fc.isSwtInSwing()) {
                toPos = getFirstPosAfterLastChild(root);
                refNode = null;
                after = true;

            }

            if (jiglooPlugin.DEBUG)
                System.out.println("REP PAR CONN, REF NODE=" + getCodeForNode(refNode));

            String name = fc.getName();

            //set toPos *after* repairConstructor called, since that can change
            //the value of refNode.getStartPosition !!!
            //and make sure you don't modify code before toPos is used
            if (toPos < 0) {
                if (refNode == null) {
                    System.out.println("REF NODE == null");
                }
                toPos = refNode.getStartPosition();
                if (after)
                    toPos += refNode.getLength();
            }

            if (jiglooPlugin.DEBUG)
                System.out.println("ADDING NODE, context(0)=" + getText(toPos, 40));

            //repairInCode(fc.getLayoutDataWrapper(), REPAIR_ALL);
            //repairInCode(fc);

            if (refNode != null) {
                //if refNode *is* null, then toPos should have been set
                // correctly already
                if (after) {
                    //this is intended to handle the case where we have a block
                    //which contains the connectionNode, but we don't want to
                    //place the new code inside that block, so we place it directly
                    //after the block
                    if (!useGetter)
                        toPos = getOutsideOfBlock(false, sib, toPos);
                    toPos = getStartOfNextLine(toPos);
                } else {
                    if (!useGetter)
                        toPos = getOutsideOfBlock(true, sib2, toPos);
                    toPos = getStartOfLine(toPos);
                }
            }

            if (jiglooPlugin.DEBUG)
                System.out.println("ADDING NODE, context(1)=" + getText(toPos, 40));

            if (moveElement && (isSWT() || !useGetter)) {
                ASTNode node = getAssignment(fc);
                if (node != null && !noParent) {
                    int nodeStart = node.getStartPosition();
                    int p1 = toPos;
                    int p2 = nodeStart;
                    if (p2 < p1) {
                        p2 = toPos;
                        p1 = nodeStart + node.getLength();
                    }

                    if (getBlockPositions(fc) != null) {
                        moveBlock(nodeStart, toPos);
                    } else {
                        moveElement(fc, toPos, useGetter, false);
                    }
                }
            }

            ASTNode connNode = getConnection(fc);
            //if connection-node code is "", return now!
            if ("".equals(code)) {
                if (connNode != null && !isDeleted(connNode)) {
                    removeNode(connNode);
                    //                    elementConnections.remove(name);
                    removeFromMap(elementConnections, name);
                }
                return;
            }

            boolean connAfter = true;
            // connRefNode is commonly .add for Swing
            // and .setControl for SWT (but in this case, sib will be null,
            // since
            // setControl is called only for the single child of, say, a
            // CTabItem)
            ASTNode connRefNode = null;
            if (isSWT()) {
                //add setControl etc after this element's assignment
                connRefNode = getAssignmentNode(fc.getName());
                connAfter = true;
            } else {
                if (useGetter) {
                    //if useGetter is true, put ahead of next sibling,
                    // otherwise put after
                    //last prop-setter node (in getter method body) of parent.
                    if (pos == 0) {
                        ASTNode sib2Con = null;
                        if (sib2 != null)
                            sib2Con = getConnection(sib2);
                        if (sib2Con != null) {
                            connRefNode = sib2Con;
                            connAfter = false;
                        } else {
                            if (fcPar == null || fcPar.isInherited())
                                connRefNode = getLastNode(getRootComponent());
                            else
                                connRefNode = getLastNode(fcPar);
                            connAfter = true;
                        }
                    } else {
                        if (sib != null)
                            connRefNode = getConnectionNode(sib.getName());
                    }

                    if (connRefNode == null && fcPar != null) {
                        connRefNode = getLastNode(fcPar);
                        connAfter = true;
                    }
                } else {
                    //want parent.add(this) to go after this assignment,
                    //if the previous sibling's connection node occurs *before*
                    // this assignment, or the place where this element will end up
                    // (refNode)

                    ASTNode canRef = getAssignment(fc);
                    connAfter = true;
                    connRefNode = canRef;

                    ASTNode sibCon = null;
                    if (sib != null)
                        sibCon = getConnection(sib);
                    ASTNode cmeth = getMethodContainingAssignment(fc.getName());
                    if (!isContainedBy(sibCon, cmeth))
                        sibCon = null;

                    if (sibCon == null) {
                        connRefNode = canRef;
                    } else if (canRef != null && isBefore(canRef, sibCon)) {
                        connRefNode = sibCon;
                    } else {
                        connRefNode = canRef;
                    }
                }
            }

            //otherwise we need to place (or create) connection node correctly
            if (jiglooPlugin.DEBUG)
                System.out.println("ADDING NODE, context(2)=" + getText(toPos, 40));

            if (connRefNode != null)
                refNode = connRefNode;

            if (fc.isInline()) {
                refNode = connNode;
            }

            if (refNode != null) {
                if (connAfter)
                    toPos = getStartOfNextLine(refNode.getStartPosition() + refNode.getLength());
                else
                    toPos = getStartOfLine(refNode.getStartPosition());
            }

            boolean connNodeReal = true;

            if (connNode == null) {
                connNode = getAssignmentNode(name);
                if (connNode == null) {
                    //                    return;
                }
                connNodeReal = false;
            } else {
                if (!constraintChanged)
                    code = getCodeForNode(connNode) + ";";
            }

            //move connNode
            if (!connNodeReal || !isProtected(connNode)) {
                if (jiglooPlugin.DEBUG)
                    System.err.println("REPAIRING PAR CONN - code=" + code + ", par=" + fc.getParent());

                if (code != null) {
                    MethodInvocation nmic = ast.newMethodInvocation();
                    addNode(nmic, toPos, code, elementConnections, name, "", "");

                    if (jiglooPlugin.DEBUG)
                        System.out.println("ADDED NODE " + getCodeForNode(nmic) + "\nrefNode="
                                + getCodeForNode(refNode));

                    boolean needNewAssNode = false;
                    int insPos = -1;
                    ASTNode assNode = getAssignment(fc);
                    if (connNodeReal) {
                        if (!fc.isInline() && isContainedBy(assNode, connNode)) {
                            needNewAssNode = true;
                            insPos = getStartOfLine(connNode.getStartPosition());
                        }
                        removeNode(connNode, false);
                    }

                    if (needNewAssNode) {
                        String consCode = fc.getJavaConstructor(this);
                        insertText(consCode + NL, insPos);
                        assNode.setSourceRange(insPos, consCode.length());
                        indentNode(assNode);
                    } else {
                        updateInlineAssignmentPosition(fc);
                    }

                }
            }
            //          System.err.println("REP PAR CON FOR "+fc+", "+constraintChanged);
            //          System.err.println("CODE=>>>>>>>>>>>>\n"+buff+"\n<<<<<<<<<<<<");
            //          new Exception().printStackTrace();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * If fcPar is an inherited object it will  not be assigned in code, and
     * may not have any properties set in code, so look for and return it's non-inherited parent.
     * If fcPar is not inherited, return fcPar.
     */
    private FormComponent getCodeParent(FormComponent fcPar) {
        if(fcPar == null)
            return null;
        while(fcPar.isInherited() && fcPar.getParent() != null)
            fcPar = fcPar.getParent();
        return fcPar;
    }

    private FormComponent getLastElementInMethod(MethodDeclaration mdec) {
        int start = mdec.getStartPosition();
        int end = mdec.getLength() + start;
        FormComponent last = getLastElementInside(elementAssignments, start, end);
        if (last == null)
            last = getLastElementInside(elementConnections, start, end);
        return last;
    }

    /**
     * @param start
     * @param sib2
     * @param toPos
     * @return
     */
    private int getOutsideOfBlock(boolean start, FormComponent fc, int toPos) {
        if (fc != null) {
            int[] blockPosns = getBlockPositions(fc);
            if (blockPosns != null && toPos > blockPosns[0] && toPos < blockPosns[1]) {
                toPos = blockPosns[start ? 0 : 1];
            }
        }
        return toPos;
    }

    private String getAddToParentCode(FormComponent fc) {
        String name = fc.getNameInCode();
        name = convertToMethod(fc.getName(), name, false);
        if (fc.isInline()) {
            String cc = fc.getConstructorCode();
            if (cc != null)
                name = cc;
        }
        String code = fc.getJavaAddToParentCode(this, name);
        //code = JiglooUtils.replace(code, "(" + name, "(" +
        // convertToMethod(name, false));
        return code;
    }

    private int getStartOfLineAfter(ASTNode node) {
        int ep = node.getStartPosition() + node.getLength();
        return getStartOfNextLine(ep);
    }

    private void moveElement(IFormPropertySource fps, int toPos, boolean useGetter, boolean repairParCon) {

        String elementName = fps.getName();

        ASTNode node = getAssignment(fps);

        if (fps instanceof FormComponent && (
        //                ((FormComponent)fps).isSwingInSwt() ||
                //                ((FormComponent)fps).isSwtInSwing() ||
                ((FormComponent) fps).isInline()))
            node = getConnection(fps);

        if (node == null)
            return;

        //        System.err.println("MOVE ELEMENT "+fps);
        //        new Exception().printStackTrace();

        Vector vec = getSortedRelatedNodes(fps);

        if (!useGetter) {
            //this is to prevent case where an element with children is moved
            //(but in repairParentConnection, moveElement is called before the
            //connection node is moved, so when the children of the element
            //are moved, they will use the connection node as a reference,
            //then after the children have been moved, the connection node is
            //moved
            ASTNode connNode = getConnection(fps);
            if (connNode != null && !vec.contains(connNode))
                vec.add(connNode);
        }

        int sp = node.getStartPosition();
        int ep = sp + node.getLength();
        int st = getStartOfLine(sp);
        int end = getStartOfNextLine(ep);
        moveCode(st, toPos, end - st);
        sp = node.getStartPosition();
        ep = sp + node.getLength();
        toPos = getStartOfNextLine(ep);

        ASTNode assNode = node;

        for (int i = 0; i < vec.size(); i++) {
            node = (ASTNode) vec.elementAt(i);
            sp = node.getStartPosition();
            ep = sp + node.getLength();
            st = getStartOfLine(sp);
            end = getStartOfNextLine(ep);

            moveCode(st, toPos, end - st);
            sp = node.getStartPosition();
            ep = sp + node.getLength();
            toPos = getStartOfNextLine(ep);
        }

        if (fps instanceof FormComponent) {

            toPos = getStartOfLineAfter(assNode);
            FormComponent fc = (FormComponent) fps;

            moveElement(fc.getLayoutDataWrapper(), toPos, false, repairParCon);

            toPos = getStartOfLineAfter(assNode);

            moveElement(fc.getLayoutWrapper(), toPos, false, repairParCon);

            //since we have now moved layout and layoutdata nodes, need to find
            //last node again
            ASTNode lastNode = node;
            for (int i = 0; i < vec.size(); i++) {
                node = (ASTNode) vec.elementAt(i);
                if (lastNode == null || lastNode.getStartPosition() < node.getStartPosition())
                    lastNode = node;
            }

            sp = assNode.getStartPosition();
            ep = getStartOfLineAfter(lastNode);

            if (jiglooPlugin.useBraces()) {
                surroundByBlock(sp, ep, fps.getNameInCode());
            } else {
                if (!useGetter) {
                    int add = insertText(NL, sp);
                    sp += add;
                    ep += add;
                    ep += insertText(NL, ep);
                }
                indentCode(sp, ep);
                removeExcessNewlines(getStartOfLine(assNode.getStartPosition()));
                removeExcessNewlines(getStartOfNextLine(node.getStartPosition()));
            }

            if (!useGetter || repairParCon) {
                //                if (!useGetter && repairParCon) {
                for (int i = 0; i < fc.getChildCount(); i++) {
                    FormComponent child = fc.getChildAt(i);
                    //only repair parent connection if child exists in code
                    // already
                    //(sometimes parent may be moved but child doesn't exist in
                    //the code yet, which can lead to an extra setLayoutData
                    //line caused by updateInCode(fc, "layoutData") in the
                    //repairParentConnectionInCode method
                    if (findObject(elementAssignments, child.getName()) != null)
                        repairParentConnectionInCode(child, false, true);
                }
            }
        }

    }

    private Vector getSortedRelatedNodes(IFormPropertySource fps) {
        Vector v = new Vector();
        return getSortedRelatedNodes(fps, v);
    }

    private Vector getSortedRelatedNodes(IFormPropertySource fps, Vector v) {
        String parentName = fps.getName();
        MethodDeclaration assMeth = getMethodContainingAssignment(parentName);
        return getSortedRelatedNodes(fps, v, assMeth);
    }

    private Vector getSortedRelatedNodes(IFormPropertySource fps, Vector v, MethodDeclaration assMeth) {

        if (fps instanceof FormComponent) {
            //add in property-setters for any inherited components!, and use the
            //assignment-method of the parent FC
            FormComponent fc = (FormComponent) fps;
            int cc = fc.getChildCount();
            for (int i = 0; i < cc; i++) {
                FormComponent fcc = fc.getChildAt(i);
                if (fcc.isInherited())
                    getSortedRelatedNodes(fcc, v, assMeth);
            }
        }

        String parentName = fps.getName();

        HashMap spm = getPropMap(parentName);
        Iterator it = spm.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            //            if ("model".equals(key)) {
            Object val = fps.getPropertyValue(key);
            if (val instanceof FormComponent) {
                FormComponent fc = (FormComponent) val;
                ASTNode node = (ASTNode) findObject(elementAssignments, fc.getName());
                if (node != null && isContainedBy(node, assMeth)) {
                    v.add(node);
                }
                //                }
            }
            //don't move event handler method nodes, since they
            //will be moved inside the listener class declaration
            if (key.indexOf(EVENT_HANDLER) < 0) {
                ASTNode node = (ASTNode) spm.get(key);
                //exclude property-setter nodes which occur outside of
                //the method in which this element is assigned.
                if (isContainedBy(node, assMeth)) {
                    v.add(node);
                }
            }
        }
        //TODO ? add in connection nodes too! - for all the children of this
        // node
        sortNodes(v);
        //Object assNode = findObject(elementAssignments, parentName);
        //if (assNode != null)
        //v.add(0, assNode);
        return v;
    }

    private void sortNodes(Vector nodes) {
        Collections.sort(nodes, new Comparator() {
            public int compare(Object o1, Object o2) {
                return getStartPos(o1) - getStartPos(o2);
            }
        });
    }

    public String getNewLine() {
        return NL;
    }

    public String getBlockDelimiterStart(String name) {
        return getBlockDelimiterStart(name, true);
    }

    public String getBlockDelimiterStart(String name, boolean includeNL) {
        if (jiglooPlugin.useBlankLines())
            return NL;
        if (includeNL) {
            if (jiglooPlugin.useBraces())
                return "{" + NL;
            return START_BLOCK + " " + name + NL;
        } else {
            if (jiglooPlugin.useBraces())
                return "{";
            return START_BLOCK + " " + name;
        }
    }

    public String getBlockDelimiterEnd(String name) {
        return getBlockDelimiterEnd(name, true);
    }

    public String getBlockDelimiterEnd(String name, boolean includeNL) {
        if (jiglooPlugin.useBlankLines())
            return "";
        if (includeNL) {
            if (jiglooPlugin.useBraces())
                return "}" + NL;
            return END_BLOCK + " " + name + NL;
        } else {
            if (jiglooPlugin.useBraces())
                return "}";
            return END_BLOCK + " " + name;
        }
    }

    private void surroundByBlock(int start, int end, String name) {
        start = getStartOfLine(start);
        //        end = getStartOfLine(end);
        String indent = getPrevIndent(start);
        end += insertText(indent + getBlockDelimiterStart(name), start);
        end += insertText(indent + getBlockDelimiterEnd(name), end);
        indentCode(start, end);
    }

    private boolean isTryBlock(int start, int end) {
        String nwt = getNonWhiteText(start, 4, false);
        if (nwt.equals("try{"))
            return true;
        return false;
    }

    public String getNonWhiteText(int pos, int length, boolean forwards) {
        String txt = "";
        if (forwards) {
            while (pos < getLength() && txt.length() < length) {
                if (!isEOL(pos) && !isWhiteSpace(pos) && !isHidden(pos)) {
                    Comment c = getEnclosingComment(pos);
                    if (c != null) {
                        pos = c.getEndPosition();
                    } else {
                        txt += getText(pos, 1);
                    }
                }
                pos++;
            }
        } else {
            while (pos > 0 && txt.length() < length) {
                if (!isEOL(pos) && !isWhiteSpace(pos) && !isHidden(pos)) {
                    Comment c = getEnclosingComment(pos);
                    if (c != null) {
                        pos = c.startPosition;
                    } else {
                        txt = getText(pos, 1) + txt;
                    }
                }
                pos--;
            }
        }
        return txt;
    }

    private boolean isMethodBlock(int start, int end) {
        Iterator it = methodMap.keySet().iterator();
        while (it.hasNext()) {
            MethodDeclaration m = (MethodDeclaration) methodMap.get(it.next());
            Block b = m.getBody();
            //System.out.println("IS METH BLOCK: meth=:" + getCodeForNode(m) +
            // ":");
            //System.out.println("IS METH BLOCK: body=:" + getCodeForNode(b) +
            // ":");
            if (b != null) {
                int ms = b.getStartPosition();
                int me = ms + b.getLength() - 1;
                while (me > 0 && getChar(me) != '}')
                    me--;
                while (end > 0 && getChar(end) != '}')
                    end--;
                //System.out.println("IS METH BLOCK: COMPARING " + ms + " and "
                // + start + ", " + me + " and " + end);
                if (ms == start && me == end)
                    return true;
            }
        }
        return false;
    }

    private MethodDeclaration getEnclosingMethod(int pos) {
        Object key = getEnclosingNodeKey(methodMap, pos);
        if (key != null)
            return (MethodDeclaration) methodMap.get(key);
        return null;
    }

    /**
     * Finds the first non - white, non - EOL char before the given start posn,
     * and if it matches chr, returns the position, otherwise returns - 1.
     */
    private int matchPrevChar(int start, char chr) {
        //System.out.println("MATCH PREV CHAR "+buff.getText(start-10, 20)+",
        // "+chr);
        while (start > 0 && (isEOL(start) || isWhiteSpace(start))) {
            start--;
            Comment c = getEnclosingComment(start);
            if (c != null)
                start = c.startPosition - 1;
        }
        if (getChar(start) == chr)
            return start;
        return -1;
    }

    private ASTNode getLastNode(IFormPropertySource element) {
        return getLastNode(element, -1);
    }

    private ASTNode getLastNode(IFormPropertySource element, int lastPos) {
        if (element == null)
            return null;

        String name = element.getName();
        //    	if(element instanceof FormComponent)
        //    		name = ((FormComponent)element).getOldName();

        ASTNode node = getAssignmentNode(name);
        ASTNode node2 = getConnectionNode(name);

        int pos = -1;
        MethodDeclaration md = null;
        if (node != null) {
            pos = node.getStartPosition();
            md = getEnclosingMethod(pos);
        }
        if (node2 != null && node2.getStartPosition() > pos && (lastPos < 0 || node2.getStartPosition() < lastPos)
                && (md == null || isContainedBy(node2, md))) {
            node = node2;
            pos = node2.getStartPosition();
        }
        ASTNode node3 = null;
        //if there is no method containing the element assignment,
        //look for last node in initGUI method (if it exists) first, and
        //if nothing there, look in all other methods.
        if (md == null && initGUIMethod != null) {
            node3 = getPropSetter(element, true, initGUIMethod, lastPos);
        }
        if (node3 == null)
            node3 = getPropSetter(element, true, md, lastPos);
        if (node3 != null && node3.getStartPosition() > pos) {
            node = node3;
            pos = node3.getStartPosition();
        }

        return node;
    }

    private ASTNode getLastNode(IFormPropertySource element, ASTNode mdec) {
        if (element == null)
            return null;

        String name = element.getName();
        //    	if(element instanceof FormComponent)
        //    		name = ((FormComponent)element).getOldName();

        ASTNode node = getAssignmentNode(name);
        ASTNode node2 = getConnectionNode(name);
        ASTNode node3 = getPropSetter(element, true, mdec, mdec.getStartPosition() + mdec.getLength());

        if (!isContainedBy(node, mdec))
            node = null;
        if (!isContainedBy(node2, mdec))
            node2 = null;

        ASTNode ln = node;
        if (ln == null)
            ln = node2;
        if (ln == null)
            ln = node3;

        if (node2 != null && isBefore(ln, node2))
            ln = node2;
        if (node3 != null && isBefore(ln, node3))
            ln = node3;
        return ln;

    }

    private ASTNode getPropSetter(IFormPropertySource element, boolean last) {
        return getPropSetter(element, last, null, -1);
    }

    private ASTNode getPropSetter(IFormPropertySource element, boolean last, ASTNode container, int lastPos) {

        FormComponent fc = null;
        if (element instanceof FormComponent)
            fc = (FormComponent) element;

        String name = element.getName();
        HashMap map = getPropMap(name);
        ASTNode node = null;
        int pos = 0;
        Iterator it = map.keySet().iterator();
        int contStart = -1, contEnd = -1;
        if (container != null) {
            contStart = container.getStartPosition();
            contEnd = contStart + container.getLength();
        }
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.indexOf(EVENT_HANDLER) >= 0 || key.indexOf(EVENT_LISTENER) >= 0 || key.indexOf(METHOD_PREFIX) >= 0)
                continue;
            //            if(element instanceof FormComponent 
            //                    && isTailProperty((FormComponent)element, key))
            //                continue;

            //dont include setSize for JFrame or JDialog, since the setSize
            //call should occur at the end of the initGUI method (after pack)
            if (fc != null && (fc.isJFrame() || fc.isJDialog()) && "size".equals(key))
                continue;

            ASTNode mic = (ASTNode) map.get(key);
            int st = mic.getStartPosition();
            //choose the end position, since some nodes (eg, event handler
            // methods)
            //might be contained by other nodes (eg, addEventHandler nodes)
            if (last)
                st += mic.getLength();

            if (lastPos > 0 && st > lastPos)
                continue;

            if (contStart == -1 || (st > contStart && st < contEnd)) {

                if (node == null || (last && st > pos) || (!last && st < pos)) {
                    node = mic;
                    pos = st;
                }

            }
        }
        //include prop-setters for inherited elements
        if (fc != null) {
            for (int i = 0; i < fc.getChildCount(); i++) {
                FormComponent child = fc.getChildAt(i);
                if (child.isInherited()) {
                    ASTNode lcn = getPropSetter(child, last, container, lastPos);
                    if (lcn != null) {
                        int st = lcn.getStartPosition();
                        if (last)
                            st += lcn.getLength();
                        if (node == null || (last && st > pos) || (!last && st < pos)) {
                            node = lcn;
                            pos = st;
                        }
                    }
                }
            }
        }
        return node;
    }

    public void removePropSetter(IFormPropertySource element, String propName) {
        ASTNode pSetter = getPropSetter(element, propName);
        if (pSetter != null && !isProtected(pSetter)) {
            int pos = getStartOfLine(pSetter.getStartPosition());
            removeNode(pSetter);
            removeSurroundingBraces(pos, element);
        }
    }

    private ASTNode getScrollToNode(IFormPropertySource element, String propName) {
        try {
            ASTNode node = getPropSetter(element, propName);
            if (node != null)
                return node;
            if (element.hasProperty(propName)) {
                if (element instanceof LayoutDataWrapper && isSwing()) {
                    node = (ASTNode) elementConnections.get(((LayoutDataWrapper) element).getFormComponent().getName());
                    if (node != null)
                        return node;
                }
                String name = element.getName();
                node = (ASTNode) findObject(elementAssignments, name);
                if (node != null)
                    return node;
                return (ASTNode) findObject(elementConnections, name);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
        return null;
    }

    private ASTNode getPropSetter(IFormPropertySource element, String propName) {
        String name = element.getName();
        HashMap map = getPropMap(name);
        ASTNode node = (ASTNode) map.get(propName);
        if (node != null)
            return node;
        int pos = 0;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.indexOf(EVENT_HANDLER) >= 0 || key.indexOf(EVENT_LISTENER) >= 0) {
                if (key.endsWith(propName))
                    return (ASTNode) map.get(key);
            }
        }
        return null;
    }

    /**
     * searches for prop nodes with names starting with propName (for instance, "_METHOD_linkSize")
     * and returns the first match found which is not in the exclude vector (if it's not null). If it finds
     * a match and exclude is not null, adds the matched key to exclude.
     */
    private ASTNode getPropSetterLike(IFormPropertySource element, String propName, Vector exclude) {
        String name = element.getName();
        HashMap map = getPropMap(name);
        ASTNode node = (ASTNode) map.get(propName);
        if (node != null)
            return node;
        int pos = 0;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith(propName)) {
                if (exclude != null) {
                    if (exclude.contains(key))
                        continue;
                    exclude.add(key);
                }
                return (ASTNode) map.get(key);
            }
        }
        return null;
    }

    public void repairConstructor(IFormPropertySource element) {
        repairConstructor(element, false);
    }

    public void toggleLocalFieldDec(Vector fcs) {
        for (int i = 0; i < fcs.size(); i++) {
            try {
                FormComponent fc = (FormComponent) fcs.elementAt(i);
                ASTNode ass = getAssignment(fc);
                String assCode = getCodeForNode(ass);
                if (isLocalField(fc)) {
                    fc.setModifier(FormComponent.MOD_PRIVATE);
                    addField(fc.getName(), fc.getClassNameForCode(), isStatic(fc), fc);
                    String fcName = fc.getNameInCode();
                    int pos = assCode.indexOf(fcName);
                    assCode = assCode.substring(pos);
                    updateNodeBody(ass, assCode);
                } else {
                    ASTNode fdec = getFieldDec(fc);
                    int st = getStartOfLine(fdec.getStartPosition());
                    int end = getStartOfLineAfter(fdec);
                    replaceText("", st, end - st, true);
                    fieldDecs.remove(fc.getName());
                    String code = "final " + fc.getClassNameForCode() + " " + assCode;
                    updateNodeBody(ass, code);
                }
                fc.refreshTreeNode();
            } catch (Throwable t) {
                jiglooPlugin.handleError(t);
            }
        }
    }

    /**
     * Does anything extra necessary to convert between Swing & SWT (eg, remove
     * SWTResourceManager call if going to Swing)
     */
    public void toggle(boolean isInSwingMode) {
        if (!isInSwingMode) {
            if (regResUserNode != null) {
                removeNode(regResUserNode, true);
                regResUserNode = null;
            }
        }
    }

    public void repairConstructor(IFormPropertySource element, boolean classChanged) {
        try {
            FormComponent fc = null;

            if (element instanceof FormComponent) {
                fc = (FormComponent) element;
                if (!fc.isEclipseFormElement() && fc.isInherited())
                    return;
            }

            String name = element.getName();

            if (classChanged && fc != null) {
                String clsName = fc.getFullClassName();
                //import is added (if needed) by call to fc.getJavaDeclaration
                //addImport(clsName);
                if (fc.isRoot() && superClass != null) {
                    if (jiglooPlugin.DEBUG)
                        System.out.println("UPDATING SUPERCLASS " + getCodeForNode(superClass) + " with " + clsName);
                    updateNodeBody(superClass, clsName);
                } else {
                    ASTNode decNode = (ASTNode) fieldDecs.get(name);
                    if (decNode != null)
                        updateNodeBody(decNode, fc.getJavaDeclaration(isStatic(fc)) + NL);
                }

                String methName = convertToMethod(name, name, true);
                if (methName != null) {
                    MethodDeclaration m = (MethodDeclaration) getMethodNode(methName);
                    if (m != null) {
                        Type tp = m.getReturnType();
                        ITypeBinding tb = resolveTypeBinding(tp);
                        if (!clsName.equals(getFullClassName(tb))) {
                            String oldClass = getFullClassName(tb);
                            if (jiglooPlugin.useImports())
                                clsName = fc.getShortClassName();
                            int st = m.getStartPosition();
                            int end = st + m.getLength();
                            int pos = findNextMatch(st, oldClass);
                            if (pos == -1 || pos > end) {
                                int dot = oldClass.lastIndexOf(".");
                                if (dot > 0)
                                    oldClass = oldClass.substring(dot + 1);
                                pos = findNextMatch(st, oldClass);
                            }
                            if (pos >= 0 && pos < end) {
                                replaceText(clsName, pos, oldClass.length(), true, false);
                            }
                        }
                    }
                }

            }

            String constCode = element.getJavaConstructor(this);
            if ("".equals(constCode)) {
                removeFromCode(element);
                return;
            }

            ASTNode node = (ASTNode) findObject(elementAssignments, name);
            if (node == null) {
                node = (ASTNode) findObject(fieldDecs, name);
            }
            if (node == null) {
                if (jiglooPlugin.DEBUG)
                    System.err.println("repairConstructor: Unable to find node for " + element);
                return;
            }
            String oldCode = getCodeForNode(node);
            String elemName = element.getName();
            int pos = elemName.lastIndexOf(".");
            elemName = elemName.substring(pos + 1);
            if (!oldCode.startsWith(elemName)) {
                pos = oldCode.indexOf(elemName);
                if (pos > 0) {
                    int pos2 = constCode.indexOf(elemName);
                    if (pos2 > 0)
                        constCode = constCode.substring(pos2);
                    constCode = oldCode.substring(0, pos) + constCode;
                }
            }

            if (jiglooPlugin.DEBUG)
                System.err.println("REPAIRING " + name + "@" + element.hashCode() + ", " + node + ", " + constCode);
            if (node != null) {
                String seg = getCodeForNode(node);
                while (constCode.startsWith("\t") || constCode.startsWith(" "))
                    constCode = constCode.substring(1);
                if (jiglooPlugin.DEBUG)
                    System.err.println("REPAIRING (2) " + name + ", " + seg + ", " + constCode);
                if (seg.startsWith("new") && !constCode.startsWith("new")) {
                    //original code had layout, say, created on the fly- need to
                    //replace constructor with layout name, and insert layout
                    //definition before node. But don't mess with image constructors,
                    //say, which are not defined as fields (and so constCode starts
                    //with "new").
                    ASTNode pp = node.getParent();
                    VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                    FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                    ASTNode cn = addNode(fdec, pp, constCode, elementAssignments, name, false, false, "", "");
                    //node *was* the assignment node, so indent cn *after* updating
                    //node, because after addNode(..., elementAssignments,...) is called,
                    //node is no longer in elementAssignments and so indentNode will node
                    //update it's position
                    updateNodeBody(node, name);
                    indentNode(cn);
                } else {
                    updateNodeBody(node, constCode);
                    indentNode(node);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes any method calls from code which set properties which no longer
     * exist (because the LayoutWrapper's main class has been changed, for
     * example) and updates any existing method calls.
     */
    public void repairInCode(IFormPropertySource element) {
        repairInCode(element, false);
    }

    public void repairInCode(IFormPropertySource element, boolean classChanged) {
        if (parsing || addActionDialogOpen)
            return;
        if (element instanceof LayoutWrapper) {
            throw new RuntimeException("Usage error: repairInCode called with LayoutWrapper argument " + element);
        }
        try {
            if (element instanceof FormComponent) {
                FormComponent fc = (FormComponent) element;
                if (!editor.hasComponent(fc)) {
                    removeFromCode(fc);
                    return;
                }
            }

            repairConstructor(element, classChanged);
            String name = element.getName();
            HashMap map = getPropMap(name);
            Iterator it = map.keySet().iterator();
            Vector rem = new Vector();
            while (it.hasNext()) {
                String key = (String) it.next();
                //System.out.println("TESTING PROP "+key+",
                // "+element.hasProperty(key));

                //v4.0M2 - propMap may contain "extraProps" - see FormComponent
                //so don't want to remove a node if it isn't a "property"
                if (element.hasProperty(key) && !element.isPropertySet(key)) {
                    ASTNode mic = (ASTNode) map.get(key);
                    //System.out.println("MIC FOR PROP "+key+", "+mic);

                    if (mic != null) {
                        //don't remove the event handler methods, since they
                        //will have been removed when the anonymous class was
                        //removed
                        if (key.indexOf(EVENT_HANDLER) < 0 && key.indexOf(EVENT_LISTENER) < 0 //v4.0.0
                                && !isProtected(mic)) {
                            removeNode(mic);
                        }
                        rem.add(key);
                    } else {
                        rem.add(key);
                    }
                } else {
                    if (element.needsUpdateInCode(key))
                        updateInCode(element, key);
                }
            }
            for (int i = 0; i < rem.size(); i++)
                map.remove(rem.elementAt(i));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void repairEventWrapper(FormComponent fc) {
        EventWrapper ew = fc.getEventWrapper(false);
        if (ew != null) {
            repairInlineAssignment(fc, true);
            String code = "";
            HashMap childMap = ew.getChildEventWrappers();
            if (childMap != null && childMap.size() > 0) {
                String parName = fc.getName();
                HashMap pmap = getPropMap(parName);
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    String listClass = (String) it.next();
                    EventWrapper cew = (EventWrapper) childMap.get(listClass);
                    code = cew.getJavaCode(fc.getNameInCode(), fc.isSwing(), this);
                    code = fixNewLines(code);
                    MethodInvocation mic = (MethodInvocation) pmap.get(EVENT_LISTENER + listClass);
                    if (!"".equals(code)) {
                        if (jiglooPlugin.DEBUG)
                            System.out.println("REPAIR EVT WR: " + parName + ", " + listClass + ", " + mic + ", "
                                    + pmap);
                        boolean codeChanged = true;

                        if (mic != null) {
                            String oldCode = getCodeForNode(mic);
                            int pos = code.indexOf("{");
                            int opos = oldCode.indexOf("{");
                            if (pos > 0 && opos > 0) {
                                String start = code;
                                if (pos >= 0)
                                    start = start.substring(0, pos);
                                //while (start.startsWith("\t"))
                                //start = start.substring(1);
                                String oldStart = oldCode;
                                if (opos >= 0)
                                    oldStart = oldStart.substring(0, opos);

                                codeChanged = !oldStart.equals(start);

                                if (jiglooPlugin.DEBUG)
                                    System.out.println("CODE CHANGED\n" + start + "\n" + oldCode + "\n" + oldStart);
                                if (codeChanged) {
                                    code = JiglooUtils.replace(oldCode, oldStart, start);
                                }
                            }
                        }

                        if (codeChanged) {
                            repairInlineAssignment(fc);
                            code = fixNewLines(code);
                            boolean indent = (mic == null);
                            addOrUpdate(mic, code, pmap, EVENT_LISTENER + listClass, fc);
                            mic = (MethodInvocation) pmap.get(EVENT_LISTENER + listClass);
                            if (indent)
                                indentCode(mic.getStartPosition(), mic.getStartPosition() + mic.getLength());
                        }

                        Vector allHandVec = cew.getAllHandlers();
                        Class adapter = cew.getAdapter();
                        for (int i = 0; i < allHandVec.size(); i++) {
                            Method meth = (Method) allHandVec.elementAt(i);
                            String name = meth.getName();
                            String key = listClass + EVENT_HANDLER + name;
                            ASTNode node = (ASTNode) pmap.get(key);
                            //                            System.out.println("REPAIR EW: "+name+", "+node+", "+cew.isPropertySet(name));
                            if ((adapter == null || cew.isPropertySet(name)) && !cew.usesListenerElement()) {
                                if (node == null) {
                                    code = cew.getJavaCodeForHandler(name, this);
                                    if (code == null)
                                        continue;
                                    code = fixNewLines(code);
                                    MethodDeclaration md = ast.newMethodDeclaration();
                                    addNode(md, mic, code, pmap, key, true, false, "", "");
                                    indentNode(md);
                                } else {
                                    if (cew.needsUpdateInCode(name)) {
                                        code = cew.getJavaCodeForHandler(name, this);
                                        cew.setNeedsUpdateInCode(name, false);
                                        code = fixNewLines(code);
                                        updateNodeBody(node, code);
                                        indentNode(node);
                                    }
                                    //	                                System.out.println("REPAIR EW: UPDATE CODE "+name+", "+node+", "+code);
                                }
                            } else {
                                if (node != null && !isProtected(node)) {
                                    //if cew.usesListenerElement() then the
                                    // node will
                                    //have been removed already
                                    if (!cew.usesListenerElement())
                                        removeNode(node);
                                    pmap.remove(key);
                                }
                            }
                        }

                    } else if (mic != null && !isProtected(mic)) {
                        //also remove handler methods (and nodes) from all maps
                        removeNode(mic);
                        pmap.remove(EVENT_LISTENER + listClass);
                        Vector allHandVec = cew.getAllHandlers();
                        for (int i = 0; i < allHandVec.size(); i++) {
                            Method meth = (Method) allHandVec.elementAt(i);
                            String name = meth.getName();
                            String key = listClass + EVENT_HANDLER + name;
                            pmap.remove(key);
                        }
                    }
                }
            }

        }
    }

    public MethodDeclaration getInitGUIMethod() {
    	return initGUIMethod;
    }
    
    private int getStartOfInitGUI() {
        if (initGUIMethod == null)
            addInitGUIMethod();
        int start = initGUIMethod.getStartPosition();
        int end = start + initGUIMethod.getLength();
        int pos = findNextMatch(start, "{");
        int tp = findNextMatch(start, "try");
        if (tp > start && tp < end) {
            tp = findNextMatch(tp, "{") + 1;
        }
        if (tp > start && tp < end) {
            pos = tp;
        }
        int sol = getStartOfNextLine(pos);
        int cb = findNextMatch(pos, "}");
        if (sol < cb)
            pos = sol;
        else
            pos++;

        return pos;
    }

    private boolean isTailProperty(IFormPropertySource par, String propName) {
        Class cls = null;
        if (par instanceof FormComponent)
            cls = ((FormComponent) par).getMainClass();
        else if (par instanceof LayoutWrapper)
            cls = ((LayoutWrapper) par).getMainClass();
        if (cls == null)
            return false;
        if (jiglooPlugin.canUseSwing()) {
            if (jiglooPlugin.canUseGroupLayout()
                    && ("org.jdesktop.layout.GroupLayout".equals(cls.getName())
                            || "javax.swing.GroupLayout".equals(cls.getName()) || "com.cloudgarden.jigloo.groupLayout.GroupLayout"
                            .equals(cls.getName()))
                    //                    &&  Cacher.isAssignableFrom(GroupLayout.class, cls) 
                    && ("horizontalGroup".equals(propName) || "verticalGroup".equals(propName)))
                return true;
            if ( Cacher.isAssignableFrom(Container.class, cls)
                    && ("focusTraversalPolicy".equals(propName) || "focusCycleRoot".equals(propName)))
                return true;
        }
        if ( Cacher.isAssignableFrom(Composite.class, cls) && "tabList".equals(propName))
            return true;
        if ( Cacher.isAssignableFrom(SashForm.class, cls) && "weights".equals(propName))
            return true;
        if ( Cacher.isAssignableFrom(TabFolder.class, cls) && "selection".equals(propName))
            return true;
        if ( Cacher.isAssignableFrom(CTabFolder.class, cls) && "selection".equals(propName))
            return true;
        return false;
    }

    private ASTNode getPropSetterRefNode(IFormPropertySource par, String propName) {
        ASTNode pnode = null;
        Object propVal = par.getPropertyValue(propName);
        FormComponent fwc = null;
        if (propVal instanceof FormComponentWrapper) {
            fwc = ((FormComponentWrapper) propVal).getFormComponent();
        }
        if (propVal instanceof FormComponent) {
            fwc = (FormComponent) propVal;
        }
        if (propVal instanceof ClassWrapper) {
            fwc = ((ClassWrapper) propVal).getFormComponent();
        }
        if (fwc != null) {
            ASTNode node = getAssignment(fwc);
            if (node != null) {
                ASTNode meth1 = getMethodContainingAssignment(fwc.getName());
                ASTNode meth2 = getMethodContainingAssignment(par.getName());
                if ((meth1 != null && meth1.equals(meth2)) || (meth1 == null && meth2 == null)) {
                    if (isBefore(getAssignment(par), node))
                        return node;
                }
            }
        }

        FormComponent fc = null;
        if (par instanceof FormComponent) {
            fc = (FormComponent) par;
        } else if (par instanceof LayoutWrapper) {
            fc = ((LayoutWrapper) par).getFormComponent();
        }

        if (fc != null && isTailProperty(par, propName) && fc.getChildCount() > 0) {
            MethodDeclaration mdec = getMethodContainingAssignment(par.getName());
            if (mdec == null)
                mdec = initGUIMethod;
            FormComponent lastFC = getLastElementInMethod(mdec);
            ASTNode node = getLastNode(lastFC);
            if (node != null)
                return node;
        }

        if (par instanceof FormComponent) {
            if ("layoutData".equals(propName))
                propVal = fc.getLayoutDataWrapper();
            if ("layout".equals(propName))
                propVal = fc.getLayoutWrapper();

            if (fc.isRoot() && "size".equals(propName)) {
                ASTNode packNode = getPropSetter(fc, METHOD_PREFIX + "pack");
                if (packNode != null)
                    return packNode;
            }
            if (fc.isInherited()) {
                FormComponent pfc = fc.getParent();
                ASTNode node = getPropSetter(pfc, true);
                if (node == null)
                    node = getAssignment(pfc);
                if (node != null)
                    return node;
            }

            if (pnode == null) {
                pnode = getLastNode(par);
            }

            boolean propIsLW = propVal instanceof LayoutWrapper;
            boolean propIsLDW = propVal instanceof LayoutDataWrapper;

            if (pnode == null || propIsLW || propIsLDW) {
                LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
                LayoutWrapper lw = fc.getLayoutWrapper();
                ASTNode parAss = getAssignment(par);
                ASTNode ldwNode = getLastNode(ldw); //getAssignment(ldw);
                ASTNode lwNode = getLastNode(lw); // getAssignment(lw);
                if (propIsLW) {
                    if (lwNode != null && (parAss == null || isBefore(parAss, lwNode))) {
                        pnode = lwNode;
                    }
                } else if (propIsLDW) {
                    if (ldwNode != null && (parAss == null || isBefore(parAss, ldwNode))) {
                        pnode = ldwNode;
                    }
                } else {
                    if (lwNode != null) {
                        if (ldwNode != null) {
                            if (lwNode.getStartPosition() > ldwNode.getStartPosition())
                                pnode = lwNode;
                            else
                                pnode = ldwNode;
                        } else {
                            pnode = lwNode;
                        }
                    } else if (ldwNode != null) {
                        pnode = ldwNode;
                    }
                }
            }
        }

        if (pnode == null) {
            pnode = getLastNode(par);
        }
        if (pnode == null && par instanceof LayoutWrapper) {
            //would be here if we are updating a property of a layout which
            //has not been created explicitly (eg, getLayout().setAlignment(LEFT) )
            pnode = getLastNode(fc);
        }
        return pnode;
    }

    private ASTNode getFieldDec(IFormPropertySource fps) {
        String name = fps.getName();
        return (ASTNode) findObject(fieldDecs, name);
    }

    private ASTNode getAssignment(IFormPropertySource fps) {
        String name = fps.getName();
        return (ASTNode) findObject(elementAssignments, name);
    }

    private ASTNode getConnection(IFormPropertySource fps) {
        String name = fps.getName();
        return (ASTNode) findObject(elementConnections, name);
    }

    public void repairLayoutInCode(LayoutWrapper lw, boolean changedType) {
        if (parsing || addActionDialogOpen)
            return;
        try {
            FormComponent par = lw.getFormComponent();
            //layout might be set in creation dialog, but component not yet in
            // code.
            if (!par.isRoot() && !par.existsInCode() && !par.isInherited())
                return;

            if (changedType) {
                repairInlineAssignment(par);
                for (int i = 0; i < par.getChildCount(); i++) {
                    FormComponent fc = par.getChildAt(i);
                    if (!par.usesGroupLayout()) {
                        if (fc.isSwing()) {
                            ASTNode node = getConnection(fc);
                            if (node != null) {
                                repairParentConnectionNode(fc);
                            } else {
                                repairParentConnectionInCode(fc, true, false);
                            }
                        } else if (fc.isSWT()) {
                            repairInCode(fc.getLayoutDataWrapper(), REPAIR_ALL);
                        }
                        //update fc because the bounds property may need updating,
                        //and if an fc is inline, need to repair the property *after* the
                        //repairInlineAssignment method has been called by 
                        //repairParentConnectionInCode
                        if (!fc.isInline())
                            fc.updateInCode();
                    } else {
                        ASTNode node = getConnection(fc);
                        if (node != null) {
                            removeNode(node);
                            elementConnections.remove(fc.getName());
                        }
                    }
                }
            }

            String parName = par.getName();
            HashMap map = getPropMap(parName);

            FormComponent child1 = par.getChildAt(0);
            ASTNode parAss = (ASTNode) findObject(elementAssignments, parName);
            ASTNode child1Conn = null;
            ASTNode parMethod = null;
            if (parAss != null)
                parMethod = getEnclosingMethod(parAss.getStartPosition());
            if (child1 != null) {
                child1Conn = getConnection(child1);
                if (child1Conn == null)
                    child1Conn = getAssignment(child1);
            }

            //don't consider first child node if it isn't in the same method as
            //the parent FormComponent's assignment
            if (parMethod != null && child1Conn != null && !isContainedBy(child1Conn, parMethod))
                child1Conn = null;

            String name = lw.getName();
            boolean isAbs = "Absolute".equals(lw.getLayoutType());

            String constCode = lw.getJavaConstructor(this);
            ASTNode layoutAssNode = (ASTNode) findObject(elementAssignments, name);

            if (isAbs && layoutAssNode != null) {
                //this would be where the layout was set on the fly, eg,
                //node = "new GridLayout()" - want to change this to "null"
                //for an Absolute layout
                if (getCodeForNode(layoutAssNode).startsWith("new"))
                    updateNodeBody(layoutAssNode, "null");
                else {
                    removeFromCode(lw);
                }
                removeFromMap(elementAssignments, name);
            }

            if (!lw.isSet() && layoutAssNode != null) {
                removeFromCode(lw);
                removeFromMap(elementAssignments, name);
            }

            if (layoutAssNode == null) {
                layoutAssNode = (ASTNode) findObject(fieldDecs, name);
                if ((!lw.isSet() || isAbs) && layoutAssNode != null) {
                    removeNode(layoutAssNode);
                    removeFromMap(fieldDecs, name);
                }
            }

            if (!isAbs && lw.isSet()) {
                if (layoutAssNode != null) {
                    String seg = getCodeForNode(layoutAssNode);
                    if (jiglooPlugin.DEBUG)
                        System.err.println("REPAIRING (2) " + name + ", " + seg);
                    if (seg.startsWith("new") && !constCode.startsWith("new")) {
                        //original code had layout, say, created on the fly- need to
                        //replace constructor with layout name, and insert  layout
                        //definition before node. But don't mess with image  constructors,
                        //say, which are not defined as fields (and so  constCode starts
                        //with "new").
                        ASTNode pp = layoutAssNode.getParent();
                        VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                        FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                        ASTNode newLANode = addNode(fdec, pp, constCode, elementAssignments, name, false, false, "", "");
                        updateNodeBody(layoutAssNode, name);
                        layoutAssNode = newLANode;
                    } else {
                        updateNodeBody(layoutAssNode, constCode);
                    }
                    indentNode(layoutAssNode);

                    //if the layout assignment occurs *after* the first child's connection
                    //node, move it directly after the container's assignment, or directly
                    //before the first child's connection
                    if (child1Conn != null && isBefore(child1Conn, layoutAssNode)) {
                        int pos = -1;
                        if (parAss != null) {
                            pos = getStartOfLineAfter(parAss);
                        } else {
                            pos = getLastPosBetweenParAndChild1(child1, child1Conn, par);
                        }
                        moveNode(layoutAssNode, pos);
                    }

                } else {
                    int pos = -1;
                    ASTNode pnode = parAss;
                    if (pnode == null) {
                        if (child1Conn != null) {
                            pnode = child1Conn;
                            pos = getLastPosBetweenParAndChild1(child1, child1Conn, par);
                        }
                    } else {
                        //try and place layout declaration after parent assignment
                        pos = getStartOfLineAfter(pnode);
                    }

                    if (pnode == null) {
                        if (map != null)
                            pnode = (ASTNode) map.get("layout");
                        if (pnode != null) {
                            //put layout declaration before setLayout call
                            pos = getStartOfLine(pnode.getStartPosition());
                        } else {
                            pnode = getPropSetterRefNode(par, null);
                            if (pnode != null) {
                                pos = getStartOfLine(pnode.getStartPosition());
                            }
                        }
                    }

                    if (pnode == null) {
                        if (initGUIMethod == null)
                            addInitGUIMethod();
                        pnode = initGUIMethod;
                        pos = getStartOfInitGUI();
                    }

                    if ("".equals(lw.getBlockName()) || lw.getBlockName() == null) {
                        String newName = "%" + (newBlockNum++) + ".%X." + lw.getName();
                        fieldRenamed(lw.getName(), newName);
                        lw.setName(newName);
                        if (jiglooPlugin.DEBUG)
                            System.out.println("SETTING NAME FOR NEWLY CREATED LAYOUT " + lw.getName());
                    }

                    //name may have been changed (by addition of block name) so
                    // get name again
                    name = lw.getName();

                    putField(name, lw);
                    VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                    FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                    layoutAssNode = addNode(fdec, pos, constCode, elementAssignments, name, "", "");
                    indentNode(layoutAssNode);
                }
            }

            String loStr = par.getPropertyCode("layout");
            if (loStr == null) {
                if (isAbs) {
                    loStr = "null";
                } else {
                    loStr = lw.getNameInCode();
                }
            }
            MethodInvocation setLayoutNode = (MethodInvocation) map.get("layout");
            if (!lw.isSet()) {
                map.remove("layout");
                removeNode(setLayoutNode);
            } else {
                String code = par.getNameInCode();
                if (par.usesContentPane()) {
                    if ("this".equals(code))
                        code = "getContentPane().setLayout(" + loStr + ");";
                    else
                        code = code + ".getContentPane().setLayout(" + loStr + ");";
                } else {
                    code = code + ".setLayout(" + loStr + ");";
                }
                addOrUpdate(setLayoutNode, code, map, "layout", par);
                setLayoutNode = (MethodInvocation) map.get("layout");
            }

            if (child1Conn != null && layoutAssNode != null && setLayoutNode != null
                    && isBefore(child1Conn, setLayoutNode)) {
                //make sure setLayout node is between parent and first child
                int pos = getLastPosBetweenParAndChild1(child1, child1Conn, par);
                moveNode(setLayoutNode, pos);
            }

            if (layoutAssNode != null && setLayoutNode != null && isBefore(setLayoutNode, layoutAssNode)) {
                //make sure layout assignment is before setLayout
                moveNode(setLayoutNode, getStartOfLineAfter(layoutAssNode));
            }

            Vector rem = new Vector();
            map = getPropMap(name);
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                //                System.out.println("TESTING PROP " + key + ", " +
                //                 lw.hasProperty(key)+", "+lw.isPropertySet(key));
                key = editor.convertGroupLayoutProp(key, false);
                if (!lw.hasMethod(key) 
                		&& (!lw.hasProperty(key) || !lw.isPropertySet(key) || lw.isSyntheticProperty(key)) 
                		&& !lw.isHiddenProperty(key)) {
//                    if (!lw.hasMethod(key) && (!lw.hasProperty(key) || !lw.isPropertySet(key)) && !lw.isHiddenProperty(key)) {
                    ASTNode node = (ASTNode) map.get(key);
                    //System.out.println("MIC FOR PROP " + key + ", " + node);
                    if (node != null && !isProtected(node)) {
                        removeNode(node);
                        rem.add(key);
                    }
                }
            }
            for (int i = 0; i < rem.size(); i++)
                map.remove(rem.elementAt(i));

            Vector pNames = lw.getPropertyNames();
            for (int i = 0; i < pNames.size(); i++) {
                String key = (String) pNames.elementAt(i);
                ASTNode node = (ASTNode) map.get(key);
                //System.out.println("TESTING PROP "+key+",
                // "+lw.hasProperty(key)+
                //", "+lw.needsUpdateInCode(key));
                if (!lw.isSyntheticProperty(key) && !lw.isHiddenProperty(key) && lw.isPropertySet(key)
                        && (lw.needsUpdateInCode(key) || node == null || isDeleted(node))) {
                    updateInCode(lw, key);
                }
            }

            if ("Group".equals(lw.getLayoutType())) {
                repairLayoutGroup(lw, true);
                repairLayoutGroup(lw, false);
            }

            //            indentNode(layoutAssNode);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param layoutWrapper
     * @param vertical
     */
    public void repairLayoutGroup(LayoutWrapper layoutWrapper, boolean vertical) {
        blockIndent = true;

        String groupProp = vertical ? "verticalGroup" : "horizontalGroup";
        ASTNode node = getPropSetter(layoutWrapper, groupProp);
        String indent = "\t\t\t\t";
        if (node != null)
            indent = getPrevIndent(node.getStartPosition()) + "\t";
        else {
            ASTNode ass = getAssignment(layoutWrapper);
            if (ass != null)
                indent = getPrevIndent(ass.getStartPosition()) + "\t";
        }

        String code = null;
        if (!vertical) {
            code = layoutWrapper.getNameInCode() + ".setHorizontalGroup(";
            code += layoutWrapper.getHGroup().getJavaCode(this, indent) + ")";
        } else {
            code = layoutWrapper.getNameInCode() + ".setVerticalGroup(";
            code += layoutWrapper.getVGroup().getJavaCode(this, indent) + ")";
        }
        code += ";";
        HashMap map = getPropMap(layoutWrapper.getName());
        addOrUpdate(node, code, map, groupProp, layoutWrapper);
        repairLinkSizes(layoutWrapper, vertical);

        blockIndent = false;
    }

    public void repairLinkSizes(LayoutWrapper element, boolean vertical) {
        HashMap map = getPropMap(element.getName());
        ASTNode node;
        Vector exclude = new Vector();
        String key = METHOD_PREFIX + "linkSize|" + (vertical ? "1|" : "2|");
        do {
            node = getPropSetterLike(element, key, exclude);
            if (node != null) {
                removeNode(node);
                map.remove(exclude.get(0));
                exclude.clear();
            }
        } while (node != null);

        boolean updateOtherDirn = false;
        key = METHOD_PREFIX + "linkSize|3|";
        do {
            node = getPropSetterLike(element, key, exclude);
            if (node != null) {
                removeNode(node);
                updateOtherDirn = true;
                map.remove(exclude.get(0));
                exclude.clear();
            }
        } while (node != null);

        if (updateOtherDirn)
            repairLinkSizes(element, !vertical);

        Vector linked = element.getLinkedElements(vertical);
        if (linked != null) {
            for (int i = 0; i < linked.size(); i++) {
                Vector linkVec = (Vector) linked.elementAt(i);
                String code = element.getLinkSizeCode(this, linkVec, vertical);
                boolean java6 = editor.useJava6GroupLayout();
                key = METHOD_PREFIX + "linkSize|" + (vertical ? "1|" : "2|");
                for (int j = 0; j < linkVec.size(); j++) {
                    key += ((FormComponent) linkVec.elementAt(j)).getNameInCode() + "|";
                }
                addOrUpdate(null, code, map, key, element);
            }
        }
    }

    private void moveNode(ASTNode node, int toPos) {
        int startPos = getStartOfLine(node.getStartPosition());
        int endPos = getStartOfLineAfter(node);
        moveCode(startPos, toPos, endPos - startPos);
    }

    private int getLastPosBetweenParAndChild1(FormComponent child1, ASTNode child1Conn, FormComponent par) {
        int pos = child1Conn.getStartPosition();
        int[] posns = getBlockPositions(child1);
        if (posns != null)
            pos = posns[0];
        pos = getStartOfLine(pos);
        ASTNode lastParProp = getLastNode(par, pos);
        if (lastParProp != null) {
            int lastParPropPos = lastParProp.getStartPosition();
            if (lastParPropPos < pos)
                pos = getStartOfLine(lastParPropPos);
        }
        return pos;
    }

    private int getFirstPosAfterLastChild(FormComponent par) {
        int[] posns = getBlockPositions(par);
        if (posns != null)
            return getStartOfLine(posns[1]);

        ASTNode mdec = getMethodContainingAssignment(par.getName());
        if (mdec == null)
            mdec = initGUIMethod;
        int cc = par.getNonInheritedChildCount();
        int lastPos = -1;
        FormComponent lastChild = null;
        ASTNode lastNode = null;
        ASTNode node;
        for (int i = 0; i < cc; i++) {
            FormComponent child = par.getNonInheritedChildAt(i);
            node = getLastNode(child, mdec);
            if (node != null) {
                int pos = node.getStartPosition();
                if (pos > lastPos) {
                    lastChild = child;
                    lastPos = pos;
                    lastNode = node;
                }
            }
        }
        if (lastChild == null)
            return getStartOfInitGUI();

        posns = getBlockPositions(lastChild);
        if (posns != null && isContainedBy(posns[0], mdec) && isContainedBy(posns[1], mdec))
            return getStartOfNextLine(posns[1]);

        if (lastNode == null)
            return -1;
        return getStartOfLineAfter(lastNode);
    }

    private boolean checkMode(int mode, int test) {
        return (mode & test) != 0;
    }

    public void repairInCode(IFormPropertySource element, int mode) {
        if (parsing || addActionDialogOpen)
            return;
        try {

            String constCode = element.getJavaConstructor(this);

            FormComponent fc = null;
            LayoutWrapper lw = null;
            LayoutDataWrapper ldw = null;
            String propName = null;

            boolean isSWTMigLayoutData = false;
            
            if (element instanceof LayoutDataWrapper) {
                propName = "layoutData";
                ldw = (LayoutDataWrapper) element;
                fc = ldw.getFormComponent();
                //root component shouldn't have layoutData set
                if (fc != null && fc.getParent() == null) {
                    removeFromCode(element);
                    return;
                }
                //for swt MigLayout
                if (fc.isSwing() || fc.isCWT()) // || ldw.usesSimpleConstraint())
                    return;
                
                isSWTMigLayoutData = MigLayoutHandler.handlesLayout(fc.getParent());
                
                if (jiglooPlugin.DEBUG)
                    System.out.println("REPAIRING LDW (1) " + element + ", fc=" + fc + ", fc.exists="
                            + fc.existsInCode());
            }

            if (element instanceof LayoutWrapper) {
                propName = "layout";
                lw = (LayoutWrapper) element;
                fc = lw.getFormComponent();
                if (jiglooPlugin.DEBUG)
                    System.out.println("REPAIRING LW (1) " + element + ", fc=" + fc + ", fc.exists="
                            + fc.existsInCode());
            }

            if (checkMode(mode, REPAIR_CONSTRUCTOR) 
            		&& !isSWTMigLayoutData
            		&& "".equals(constCode)) {
                removeFromCode(element);
                if (fc != null) {
                    fc.unsetPropertyValue(propName);
                    removePropSetter(fc, propName);
                }
                return;
            }

            if (fc != null) {

                if (!fc.isPropertySet(propName)) {
                    removeFromCode(element);
                    removePropSetter(fc, propName);
                    return;
                }

                //layout might be set in creation dialog, but component not yet
                // in code. Want to allow setting of inherited elements layoutData
                if (!fc.isRoot() && !fc.existsInCode() && !fc.isInherited()) {
                    return;
                }
            }

            if (fc != null)
                repairInlineAssignment(fc);

            String name = element.getName();

            if (jiglooPlugin.DEBUG)
                System.out.println("REPAIRING " + name);
            ASTNode node = null;
            
            boolean nodeWasNull = true;
            
            if (checkMode(mode, REPAIR_CONSTRUCTOR)) {
                node = (ASTNode) findObject(elementAssignments, name);
                if (node == null)
                    node = (ASTNode) findObject(fieldDecs, name);
                if (node != null && !isDeleted(node) && !isHidden(node)) {
                	nodeWasNull = false;
                    String seg = getCodeForNode(node);
                    if (jiglooPlugin.DEBUG)
                        System.out.println("REPAIRING (2) " + name + ", " + seg);
                    if (seg.startsWith("new") && !constCode.startsWith("new")) {
                        //original code had layout, say,  created on the fly- need  to
                        //replace constructor with layout name, and insert  layout
                        //definition before node. But don't mess with image
                        // constructors, say, which are not defined as fields (and so
                        // constCode starts with "new").
                        ASTNode pp = node.getParent();
                        VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                        FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                        addNode(fdec, pp, constCode, elementAssignments, name, false, false, "", "");
                        updateNodeBody(node, name);
                    } else {
                    	if("".equals(constCode))
                    		removeNode(node);
                    	else
                    		updateNodeBody(node, constCode);
                    }

                } else {
                    ASTNode pnode = getPropSetterRefNode(fc, null);
                    boolean asChild = false;
                    if (pnode == null) {
                        pnode = getPropSetter(fc, false);
                        if (pnode == null) {
                            if (initGUIMethod == null)
                                addInitGUIMethod();
                            pnode = initGUIMethod;
                            asChild = true;
                        }
                    }
                    if ("".equals(element.getBlockName()) || element.getBlockName() == null) {
                        element.setName("%" + (newBlockNum++) + ".%X." + element.getName());
                        if (jiglooPlugin.DEBUG)
                            System.out.println("SETTING NAME FOR NEWLY CREATED ELEMENT " + element.getName());
                    }

                    //name might have changed (by addition of block name) so get name again
                    name = element.getName();

                    putField(name, element);
                    VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                    FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                    addNode(fdec, pnode, constCode, elementAssignments, name, asChild, false, "", "");
                }
            }

            if (fc != null) {
                String parName = fc.getName();
                HashMap map = getPropMap(parName);
                ASTNode mic = (ASTNode) map.get(propName);
                String fcName = fc.getNameInCode();
                if (fc.isSubclassOf(Viewer.class)) {
                	fcName += ".getControl()";
                }

                if(nodeWasNull 
                		&& !"".equals(constCode) 
                		&& mic != null && propName.equals("layoutData"))
                	removeNode(mic);
                
                if(isSWTMigLayoutData) {
                	addOrUpdate(mic, fcName + ".setLayoutData(\"" 
                			+ ((LayoutDataWrapper)element).getLayoutData()
                			+ "\");", map, propName, fc);
                } else if (!"".equals(constCode)) {
                	addOrUpdate(mic, fcName + ".set" + JiglooUtils.capitalize(propName) + "(" + element.getNameInCode()
                			+ ");", map, propName, fc);
                }
            }

            updateInCode(element);
            HashMap map = getPropMap(name);
            Iterator it = map.keySet().iterator();
            Vector rem = new Vector();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (!element.hasProperty(key) || !element.isPropertySet(key)) {
                    node = (ASTNode) map.get(key);
                    if (node != null && !isProtected(node)) {
                        removeNode(node);
                        rem.add(key);
                    }
                }
            }
            for (int i = 0; i < rem.size(); i++)
                map.remove(rem.elementAt(i));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void addInitGUIMethod() {
        if (initGUIMethod != null)
            return;
        addMethod("initGUI", "try {" + NL + "} catch(Exception e) {" + NL + "e.printStackTrace();" + NL + "}"+NL, "void",
                null, null, Flags.AccPrivate, "");
        initGUIMethod = (MethodDeclaration) methodMap.get("initGUI_");
        TryStatement ts = ast.newTryStatement();
        int p1 = indexOf("try {", initGUIMethod.getStartPosition());
        int p2 = indexOf("}", p1);
        int p3 = indexOf("}", p2 + 1);
        Block tb = ast.newBlock();
        tb.setSourceRange(p1 + 4, p2);
        ts.setSourceRange(p1, p3);
        p1 = initGUIMethod.getStartPosition();
        p2 = p1 + initGUIMethod.getLength();
        indentCode(p1, p2);
        if (jiglooPlugin.DEBUG)
            System.out.println("CREATED INIT GUI " + getCodeForNode(initGUIMethod));
    }

    private void moveCode(int from, int to, int length) {
        if (from <= to && from + length >= to)
            return;
        if (from == to)
            return;
        int endPos = -1;
        //        System.err.println("MOVE CODE " + getText(from, length) + ", from=" +
        //         from + ", to=" + to + ", length=" + length);
        //TODO if moving code inside an existing block, need to indent it
        // correctly
        String code = getText(from, length);
        int exitPos = from;
        //important not to assume that insertedLength == length, because tabs
        //can be changed into spaces in replaceText
        if (from > to) {
            exitPos = getLength() - from - length;
            replaceText("", from, length, false);
            replaceText(code, to, 0, false, false);
            endPos = to;
        } else {
            replaceText(code, to, 0, false, false);
            replaceText("", from, length, false);
            endPos = to - length;
        }
        textInserted(from, length, to);
        indentCode(endPos, endPos + length);
        if (from > to)
            removeExcessNewlines(getLength() - exitPos);
        else
            removeExcessNewlines(exitPos);
    }

    private static ICodeFormatter codeFormatter = null;

    private int getStartPos(Object node) {
        if (node == null)
            return -1;
        if (node instanceof ASTNode) {
            return ((ASTNode) node).getStartPosition();
        } else {
            return ((Comment) node).startPosition;
        }
    }

    private int getEndPos(Object node) {
        if (node instanceof ASTNode) {
            return ((ASTNode) node).getStartPosition() + ((ASTNode) node).getLength();
        } else {
            return ((Comment) node).startPosition + ((Comment) node).length;
        }
    }

    private int getLength(Object node) {
        if (node instanceof ASTNode) {
            return ((ASTNode) node).getLength();
        } else {
            return ((Comment) node).length;
        }
    }

    private void indentCodeNew(int start, int end) {

    	//add in some characters on either end or sometimes the format doesn't work
//    	int start2 = getStartOfLine(start);
//    	if(start2 < start)
//    		start = start2;
    	
    	while(isWhiteSpace(start-1) || isEOL(start-1))
    		start --;
        
    	//end = getEndOfLine(end);
//        while (isEOL(end) || isWhiteSpace(end))
//            end--;
        
        //System.err.println("INDENTING (1) " + showControls(getText(start, end
        // - start)));

        end += 1;
        String code = getText(start, end - start);

        Vector nodes = getContainedNodes(start, end);

        sortNodes(nodes);
        int[] posns = new int[nodes.size() * 2];
        for (int i = 0; i < nodes.size(); i++) {
            Object obj = nodes.elementAt(i);
            if (jiglooPlugin.DEBUG_EXTRA)
                System.out.println("INDENTING NODE " + getStartPos(obj) + ", " + getLength(obj) + ", "
                        + getCodeForNode(obj));
            posns[2 * i] = getStartPos(obj) - start;
            posns[2 * i + 1] = posns[2 * i] + getLength(obj);
        }

        String newCode = null;

        if (USE_NEW_FORMATTER) {

//            System.out.println("indent old code:"+getText(0, getLength()).substring(start, end));
            newCode = format(getText(0, getLength()), NL, start, end - start);
//            System.out.println("got new code:"+newCode);
        } else {

            int nnw = findNextNonWhite(start);

            int tabSize = JiglooUtils.getTabSize();
            boolean useSpaces = JiglooUtils.spacesForTabs();

            String indent = getPrevIndent(start);
            int numSpaces = 0;
            int numTabs = 0;
            for (int i = 0; i < indent.length(); i++) {
                if (indent.charAt(i) == ' ')
                    numSpaces++;
                if (indent.charAt(i) == '\t')
                    numTabs++;
            }
            int lev = numTabs + numSpaces / tabSize;

            //System.err.println("INDENT LEV=" + lev);
            //replaceText(indent, start, nnw - start, true);

            codeFormatter = ToolFactory.createCodeFormatter();

            if (matchPrevChar(start, '{') > -1) {
                lev++;
            }

            newCode = codeFormatter.format(code, lev, null, NL);
        }

        updateFormattedPositions(start, posns, nodes, newCode);

        monitorPositions = false;
        replaceText(newCode, start, end - start, true, false);
        monitorPositions = true;

        for (int i = 0; i < nodes.size(); i++) {
            Object obj = nodes.elementAt(i);
            if (isCutNode(obj)) {
                continue;
            }
            if (obj instanceof ASTNode) {
                ASTNode node = (ASTNode) obj;
                node.setSourceRange(start + posns[2 * i], posns[2 * i + 1] - posns[2 * i]);
                if (jiglooPlugin.DEBUG_EXTRA)
                    System.out.println("INDENTED NODE " + node.getStartPosition() + ", " + node.getLength() + ", "
                            + getCodeForNode(node));
            } else if (obj instanceof Comment) {
                Comment node = (Comment) obj;
                node.setSourceRange(start + posns[2 * i], posns[2 * i + 1] - posns[2 * i]);
            }
        }

    }

    private static CodeFormatter formatter = null;
    
    private String format(String code, String NL, int offset, int length) {
        if(true)
            return Indenter.format(code, NL, offset, length);
        if(true) {
            String newCode = Indenter.format(code, NL, offset, length);
            length += newCode.length() - length;
            code = code.substring(0, offset) + newCode + code.substring(offset+length);
        }
        
//        if(true)
//        	return code.substring(offset, offset + length);
        
    	IDocument document = new Document(code);
        if(formatter == null) {
        	Map prefs = editor.getJavaProject().getOptions(true);
        	formatter = ToolFactory.createCodeFormatter(prefs);
        }
        final TextEdit edit = formatter.format(
        		CodeFormatter.K_STATEMENTS,
//        		CodeFormatter.K_COMPILATION_UNIT, 
				code, offset, length, 0, NL);
        try {
            if (edit != null)
                edit.apply(document);
            String newCode = document.get();
            return newCode.substring(offset, newCode.length() - (code.length() - offset - length));
        } catch (BadLocationException t) {
            jiglooPlugin.handleError(t);
        }
        return code;
    }

    private boolean updateFormattedPositions(int startPos, int[] posns, Vector nodes, String newCode) {
        int pos2 = 0;
        int[] ps = new int[4];
        for (int i = 0; i < nodes.size(); i++) {
            Object obj = nodes.elementAt(i);
            String cfn = getCodeForNode(obj);
            if ("".equals(cfn))
                continue;
            while (pos2 < newCode.length() && !matchStrings(cfn, newCode, ps, 0, pos2))
                pos2++;
            if (pos2 == newCode.length())
                return false;
            //System.err.println("SET POSNS FOR "+cfn+", "+ps[1]+", "+ps[3]);
            boolean stepBack = false;
            //if the next node's start position is less than the previous
            // node's end
            //position, don't continue on from the last node's last match
            // position,
            //but carry on from the last node's *first* match position
            if (i < nodes.size() - 1 && posns[2 * i + 2] < posns[2 * i + 1])
                stepBack = true;
            posns[2 * i] = ps[1];
            posns[2 * i + 1] = ps[3];
            pos2 = ps[3] + 1;
            if (stepBack) {
                pos2 = ps[1];
            }
            //pos2 = posns[2 * i + 2];
            //pos2 = 0;
        }
        return true;
    }

    private boolean matchStrings(String str1, String str2, int[] posns, int start1, int start2) {
        int p1 = 0, p2 = 0;
        p1 = findNextChar(str1, start1);
        p2 = findNextChar(str2, start2);
        if (p1 < 0 || p2 < 0) {
            //System.err.println("MATCH FAILED (0) " + p1 + ", " + p2 + ", " +
            // str1);
            return false;
        }
        posns[0] = p1;
        posns[1] = p2;
        while (p1 >= 0 && p2 >= 0) {
            if (str1.charAt(p1) != str2.charAt(p2)) {
                //System.err.println("MATCH FAILED " + str1.substring(0, p1 +
                // 1) + ", " + str2.substring(0, p2 + 1));
                return false;
            }
            p1 = findNextChar(str1, p1 + 1);
            p2 = findNextChar(str2, p2 + 1);
            //if (p1 < 0 && p2 < 0) {
            if (p1 < 0) {
                //System.err.println("MATCHED " + posns[1] + ", " + posns[3] +
                // ", " + str2);
                return true;
            }
            posns[2] = p1 + 1;
            posns[3] = p2 + 1;
        }
        //System.err.println(
        //"MATCH FAILED " + p1 + ", " + p2 + ", " + str1 + ", " + str2 + ", " +
        // start1 + ", " + start2);
        return false;
    }

    public void indentNode(ASTNode node) {
        if (node == null)
            return;
        int st = node.getStartPosition();
        int end = st + node.getLength();
        indentCode(st, end);
    }

    private boolean blockIndent = false;

    private void indentCode(int from, int to) {
//        System.out.println("INDENT "+from+", "+(to-from)+", "+blockIndent);
        if (blockIndent)
            return;

//        if(true)
//    		return;
        
        //indentCodeOld(from, to);
        indentCodeNew(from, to);
    }

    private void indentCodeOld(int from, int to) {
        from = getStartOfLine(from);
        String indent = getPrevIndent(from);
        //get previous line
        int prev = from;
        while (prev > 0 && !isEOL(prev))
            prev--;
        if (prev > 0)
            prev -= NL.length();
        String pline = "";
        if (!isEOL(prev))
            pline = getLineFor(prev);
        while (from < to) {
            String cline = getLineFor(from);
            int nnw = findNextNonWhite(from);
            if (!isEOL(nnw)) {
                int[] pic = getIndentChanges(pline);
                int[] cic = getIndentChanges(cline);
                int change = pic[1] - cic[0];
                //System.out.println("INDENT PLINE=" + pline);
                //System.out.println("INDENT CLINE=" + cline);
                //System.out.println("INDENT +=" + pic[1] + ", -=" + cic[0]);
                for (int i = 0; i < change; i++)
                    indent += "\t"; //getTabString();
                for (int i = 0; i < -change; i++)
                    indent = JiglooUtils.chop(indent);
                to += replaceText(indent, from, nnw - from, true, false);
            }
            pline = cline;
            from = getStartOfNextLine(from);
        }
    }

    /**
     */
    private int[] getIndentChanges(String line) {
        int startCloseBraces = 0;
        int secondHalf = 0;
        boolean secHalfStarted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ')' || c == '}') {
                if (!secHalfStarted)
                    startCloseBraces++;
                else
                    secondHalf--;
            } else if (c == '(' || c == '{') {
                secHalfStarted = true;
                secondHalf++;
            } else if (c != ' ' && c != '\t') {
                secHalfStarted = true;
            }
        }
        return new int[] { startCloseBraces, secondHalf };
    }

    private void moveBlock(int aboutPos, int to) {
        int from = getStartOfBlock(aboutPos) - 1;
        int end = getEndOfBlock(aboutPos) + 1;
        //don't just go to start of line, since there may be a "{" before the
        //start of this block on the same line
        //from = getStartOfLine(from);
        //end = getStartOfNextLine(end);
        if (isWhiteSpace(from)) {
            while (isWhiteSpace(from))
                from--;
            from++;
        }
        if (isWhiteSpace(end)) {
            while (isWhiteSpace(end))
                end++;
        }
        if ("//".equals(getText(end, 2)) || isNewLine(end)) {
            end = getStartOfNextLine(end);
        }
        int length = end - from;
        moveCode(from, to, length);
    }

    //	private String getCodeForBlock(int aboutPos) {
    //		int start = getStartOfLine(getStartOfBlock(aboutPos));
    //		int end = getStartOfNextLine(getEndOfBlock(aboutPos));
    //		int length = end - start;
    //		return getText(start, length);
    //	}

    private boolean isStatic(FormComponent fc) {
        if (fc.getParent() == null)
            return false;
        ASTNode parAss = (ASTNode) findObject(elementAssignments, fc.getParent().getName());
        if (parAss == null)
            return false;
        boolean isStatic = false;
        MethodDeclaration meth = getEnclosingMethod(parAss.getStartPosition());
        if (meth != null && ((meth.getModifiers() & Modifier.STATIC) != 0))
            return true;
        return false;
    }

    public boolean isLocalField(FormComponent fc) {
        if (getFieldDec(fc) != null)
            return false;
        ASTNode ass = getAssignment(fc);
        if (ass == null)
            return false;
        if (getEnclosingMethod(ass.getStartPosition()) == null)
            return false;
        return true;
    }

    public boolean usesGetters(FormComponent fc) {
        if (fc != null) {
        	if(fc.equals(fc.getRootComponent()))
        		return false;
            //so that JDialogs, say, will be defined inside their own method, even if
            //the overall option is not to use getters
            if (fc.getParent() == null) {
            	if(fc.isMenuComponent())
                	return false;
            	return true;
            }
            if(fc.getParent().equals(editor.getNonVisualRoot())
            		|| fc.getParent().equals(editor.getExtraCompRoot()))
                return true;
            if (fc.isRoot())
                return false;
            if (fc.isSWT())
                return false;
        }
        if (isSWT())
            return false;
        if (inVEMode && jiglooPlugin.useGettersAuto())
            return true;
        return jiglooPlugin.useGetters();
        //return (inVEMode && codeGenMode == CODE_GEN_FIELDS_AUTO) ||
        // (codeGenMode == CODE_GEN_FIELD_GETTERS);
    }

    private CodeBlock getCodeBlock(FormComponent element, boolean wantMethod) {
        CodeBlock cb = (CodeBlock) blockCode.get(element.getName());
        if (cb == null) {
            // if blockCode does not contain anything for this element,
            // then maybe methodCode will, since we may have deleted
            // a getter method and be pasting in code as a block.

            //        	String mn = "get" + JiglooUtils.capitalize(element.getNameInCode());
            //            while (methods.containsKey(mn + "_"))
            //                mn = mn + "x";
            //            mn = mn + "_";
            //          cb = (CodeBlock) methodCode.get(mn);
            //          methodCode.remove(mn);
            cb = (CodeBlock) methodCode.get(element.getName());
            methodCode.remove(element.getName());

        } else {
            blockCode.remove(element.getName());
        }
        if (cb != null) {
            if (wantMethod)
                cb.addSurroundingMethod(this);
            else
                cb.removeSurroundingMethod(this);
        }
        return cb;
    }

    public void addToCode(FormComponent element) {
        addToCode(element, null, true);
    }

    public void addToCode(FormComponent element, IProgressMonitor pm, boolean addChildren) {
        addToCode(element, pm, addChildren, true);
    }

    public void createSimpleGetterMethods(Vector fcs) {
    	for(int i=0; i<fcs.size(); i++) {
    		FormComponent element = (FormComponent) fcs.elementAt(i);
    		String body = null;
    		int flags = Flags.AccPublic;
    		String nic = element.getNameInCode();
    		String mn = "get" + JiglooUtils.capitalize(nic);
    		
    		if(methodMap.containsKey(mn+"_"))
    			return;
    		
    		body = "\t\treturn " + nic + ";" + NL;
    		
    		String retType = element.getClassNameForCode();
    		
    		addMethod(mn, body, retType, null, null, flags, "", null, false);
    		
    		MethodDeclaration md = (MethodDeclaration) methodMap.get(mn + "_");
    		indentCode(md.getStartPosition(), md.getStartPosition() + md.getLength());
    	}
    }
    
    private void addToCode(FormComponent element, IProgressMonitor pm, boolean addChildren, boolean repairParCon) {

        //if (parsing || element.isInherited())
        if (parsing)
            return;
        try {
            if (pm != null) {
                pm.worked(1);
                pm.setTaskName("Updating " + element.getNameInCode() + "...");
            }
            String name = element.getName();
            boolean isStatic = isStatic(element);
            String nic = element.getNameInCode();
            element.setExistsInCode(true);
            element.setNeedsTotalUpdate();
            ASTNode assNode = null;

            //this is important when pasting, since the layout may have
            //been set before cutting
            if (element.isPropertySet("layout")) {
                putField(element.getLayoutWrapper().getName(), element.getLayoutWrapper());
            }
            //...and the same goes for layout data
            if (element.isPropertySet("layoutData")) {
                putField(element.getLayoutDataWrapper().getName(), element.getLayoutDataWrapper());
            }

            //the element may have a field dec, but not be assigned!
            if (!element.isInherited() && (!element.isAssigned() || !fields.containsKey(name))) {
                FormComponent par = element.getParent();
                if (par == null)
                    par = getRootComponent();
                element.setBlockName(par.getBlockName() + "%" + newBlockNum + ".");
                newBlockNum++;
                String parName = par.getName();
                if (jiglooPlugin.DEBUG)
                    System.out.println("ADD TO CODE " + name + ", par=" + parName);
                if (!element.isRoot())
                    addField(nic, element.getClassNameForCode(), isStatic, element);
                //addField(nic, element.getShortClassName(), isStatic);
                //addImport(element.getTranslatedClassName());

                boolean wasCached = false;
                wasCached = element.wasCut();

                element.setInline(false);

                String constCode = element.getJavaConstructor(this);
                constCode = prepCode(constCode, null);
                int[] blockPosns = null;
                putField(element.getName(), element);

                if (usesGetters(element) || element.createGetterMethod()) {

                    String body = null;
                    int flags = Flags.AccPrivate;
                    if (element.createGetterMethod())
                        flags = Flags.AccPublic;

                    //may have both usesGetters and createGetterMethod
                    if (!usesGetters(element)) {
                        body = "\t\treturn " + nic + ";" + NL;
                    } else {
                        body = "\t\tif(" + nic + " == null) {" + NL + "\t\t\t" + constCode + NL + "\t\t}" + NL
                                + "\t\treturn " + nic + ";" + NL;
                    }

                    String mn = "get" + JiglooUtils.capitalize(nic);

                    CodeBlock cb = getCodeBlock(element, true);
                    if (cb != null && cb.getMethodName() != null)
                        mn = cb.getMethodName();

                    //hack fix so we don't over-ride the getContentPane method
                    if (mn.equals("getContentPane"))
                        mn = "getContentPane1";

                    while (methodMap.containsKey(mn + "_")) {
                        mn = mn + "x";
                    }

                    //don't indent code here
                    String retType = element.getClassNameForCode();

                    //a bit of a hack, but quick and easy
                    if (isStatic)
                        retType = "static " + retType;

                    String[] paramNames = null;
                    String[] paramTypes = null;
                    
//                    if(isSWT()) {
//                    	paramNames = new String[] {"parent"};
//                    	paramTypes = new String[] {"Composite"};
//                    	addImport("org.eclipse.swt.widgets.Composite");
//                    }
                    
                    addMethod(mn, body, retType, paramTypes, paramNames, flags, "", cb, false);

                    MethodDeclaration getterMethDec = (MethodDeclaration) getMethodNode(mn);
                    
                    Block b = getterMethDec.getBody();
                    if (cb != null) {
                        incorporateCodeBlock(cb, b.getStartPosition());
                        //                        indentCode(md.getStartPosition(), md.getStartPosition()
                        //                                + md.getLength());
                    } else {
                        if (usesGetters(element)) {

                            //so that we call the getter, and don't put any constructor
                            //code, if it exists, in there
                            //                        	element.setConstructorCode(null);

                            Assignment ass = ast.newAssignment();
                            int start = indexOf(constCode, b.getStartPosition());
                            if (start < 0) {
                                System.err.println("UNABLE TO FIND " + constCode + " in "
                                        + buff.substring(b.getStartPosition()));
                            } else {
                                ass.setSourceRange(start, constCode.length());
                            }
                            setAssignmentNode(name, ass);
                        }
                    }

                    putMethodReturn(mn, element);
                    getters.put(mn, element.getName());

                    element.createGetterMethod(false);

                    indentCode(getterMethDec.getStartPosition(), getterMethDec.getStartPosition() + getterMethDec.getLength());

                }

                if (!usesGetters(element)) {

                    CodeBlock cb = getCodeBlock(element, false);
                    int pos = -1;
                    ASTNode node = null;

                    if (!element.isVisual()) {
                        FormComponent root = getRootComponent();
                        if (root.getChildCount() > 0) {
                            FormComponent fc = root.getChildAt(0);
                            node = getAssignmentNode(fc.getName());
                            if (node != null) {
                                pos = getStartOfLine(node.getStartPosition());
                            }
                        }
                        if (pos < 0) {
                            if (initGUIMethod == null)
                                addInitGUIMethod();
                            node = initGUIMethod;
                            pos = getStartOfInitGUI();
                        }
                    } else {
                        int parInd = element.getIndexInParent();
                        FormComponent sib = null;
                        boolean after = true;
                        if (parInd > 0) {
                            sib = par.getChildAt(parInd - 1);
                            FormComponent sib2 = par.getChildAt(parInd + 1);
                            if (sib2 != null) {
                                node = getAssignmentNode(sib2.getName());
                                if (node != null)
                                    pos = getStartOfLine(node.getStartPosition());
                            }

                            if (node == null) {
                                node = getLastNode(sib);
                                if (node != null)
                                    pos = getStartOfNextLine(node.getStartPosition() + node.getLength());
                            }
                        }
                        if (parInd <= 0 || node == null) {
                            //try and add after last setter for the parent,
                            // since want to add after parent's layout is set.
                            FormComponent parElem = getCodeParent(element.getParent());
                            
                            if (element.isMenuComponent())
                                parElem = getRootComponent();

                            if (parElem != null)
                                node = getLastNode(parElem);

                            if (node == null) {
                                if (initGUIMethod == null)
                                    addInitGUIMethod();
                                node = initGUIMethod;
                                pos = getStartOfInitGUI();
                            } else {
                                pos = node.getStartPosition() + node.getLength();
                                pos = getStartOfNextLine(pos);
                            }
                        } else {
                            //sib = par.getChildAt(parInd - 1);
                            if (node == null)
                                node = getAssignmentNode(sib.getName());
                            pos = node.getStartPosition() + node.getLength();
                            blockPosns = getBlockPositions(sib);
                            if (blockPosns != null) {
                                pos = getStartOfNextLine(blockPosns[1]);
                            } else {
                                pos = getStartOfNextLine(pos);
                            }
                        }
                    }

                    if (node == null) {
                        node = getConnectionNode(element.getName());
                        pos = getStartOfLine(node.getStartPosition());
                    }

                    if (node != null) {
                        if (cb != null) {
                            Block b = ast.newBlock();
                            addNode(b, pos, cb.getCode(), null, name, "", "");
                            incorporateCodeBlock(cb, b.getStartPosition());
                            //                            wasCached = true;
                        } else {
                            //the element assignment may already have been
                            // created
                            //when pasting a block of code!
                            if (!elementAssignments.containsKey(name)) {
                                VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                                FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                                String pre = getBlockDelimiterStart(element.getNameInCode());
                                String post = getBlockDelimiterEnd(element.getNameInCode());
                                ASTNode newNode = null;
                                if(!constCode.equals("")) 
                                	newNode = addNode(fdec, pos, constCode, elementAssignments, name, pre, post);

                                ASTNode connNode = getConnectionNode(element.getName());
                                if (connNode != null) {
                                    //when converting an inline component (and maybe at other times)
                                    //the connection node will be outside the block just created - need
                                    //to move it inside block.
                                    moveNode(connNode, getStartOfLineAfter(newNode));
                                }
                            }
                        }
                    } //repairParentConnectionInCode(element);
                }

                assNode = getAssignment(element);
                if (assNode != null) {
                    if (!wasCached && jiglooPlugin.canUseSwing()) {
                        String code = null;
                        String modelName = element.getNameInCode() + "Model";
                        if (jiglooPlugin.generateStubModel(MainPreferencePage.P_GENERATE_STUB_MODEL_JTABLE)
                                && element.isSubclassOf(JTable.class)) {
                            code = element.getJTableModelDefaultCode(modelName);
                        } else if (jiglooPlugin.generateStubModel(MainPreferencePage.P_GENERATE_STUB_MODEL_JCOMBO)
                                && element.isSubclassOf(JComboBox.class)) {
                            code = element.getJComboBoxModelDefaultCode(modelName);
                        } else if (jiglooPlugin.generateStubModel(MainPreferencePage.P_GENERATE_STUB_MODEL_JLIST)
                                && element.isSubclassOf(JList.class)) {
                            code = element.getJListModelDefaultCode(modelName);
                        } else if (jiglooPlugin.getJavaVersion() > 13
                                && jiglooPlugin.generateStubModel(MainPreferencePage.P_GENERATE_STUB_MODEL_JSPINNER)
                                && element.isSubclassOf(JSpinner.class)) {
                            code = element.getJSpinnerModelDefaultCode(modelName);
                        }
                        if (code != null) {
                            VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
                            FieldDeclaration fdec = ast.newFieldDeclaration(vdf);
                            int pos = getStartOfLine(assNode.getStartPosition());
                            addNode(fdec, pos, code, elementAssignments, modelName, "", "");
                            element.updateInCode("model");
                        }

                    }
                }
            }
            updateInCode(element);

            if (repairParCon)
                repairParentConnectionInCode(element);

            if (assNode != null) {
                int[] bps = getBlockPositions(element);
                if (bps != null) {
                    indentCode(bps[0], bps[1]);
                }
                //int sob = getStartOfBlock(assNode.getStartPosition());
                //int eob = getEndOfBlock(assNode.getStartPosition());
                //indentCode(sob, eob);
            }

            if (addChildren) {
                //and do the same for any children
                for (int i = 0; i < element.getChildCount(); i++) {
                    addToCode(element.getChildAt(i), pm, addChildren);
                }
            }

            if (element.getLayoutWrapper().isSet()) {
                element.getLayoutWrapper().repairInCode(false);
                updateInCode(element, "layout");
            }

            //if (element.getLayoutDataWrapper().isSet())
            //element.getLayoutDataWrapper().repairInCode();

            if (element.getEventWrapper(false) != null)
                repairEventWrapper(element);

            assNode = getAssignment(element);
            if (assNode != null) {
                int toPos = getStartOfLine(assNode.getStartPosition());
                ASTNode assMethod = getEnclosingMethod(toPos);

                if (element.getConstructorParams() != null) {
                    Object[] cps = element.getConstructorParams();
                    for (int i = 0; i < cps.length; i++) {
                        Object cp = cps[i];
                        if (cp instanceof FormComponent) {
                            FormComponent fc = (FormComponent) cp;
                            ASTNode cpAss = (ASTNode) findObject(elementAssignments, fc.getName());
                            if (cpAss != null && isContainedBy(cpAss, assMethod) && !isBefore(cpAss, assNode))
                                moveElement(fc, toPos, false, true);
                        }
                    }
                }

                Vector pn = element.getPropertyNames();
                if (pn != null) {
                    for (int i = 0; i < pn.size(); i++) {
                        Object prop = element.getPropertyValue(pn.elementAt(i));
                        if (prop instanceof FormComponent) {
                            FormComponent fc = (FormComponent) prop;
                            ASTNode cpAss = (ASTNode) findObject(elementAssignments, fc.getName());
                            if (cpAss != null && isContainedBy(cpAss, assMethod) && !isBefore(cpAss, assNode))
                                moveElement(fc, toPos, false, true);
                            //	                    System.err.println("MOVING PROPERTY "+fc+" of "+element);
                        }
                    }
                }
            }
            if (element.isA(CTabItem.class) || element.isA(TabItem.class)) {
                element.getParent().setNeedsUpdateInCode("selection");
                element.getParent().updateInCode("selection");
            }
            if (element.isSwtInSwing()) {
                if (methodExists("initSwtAwtGUI_"))
                    return;
                insertSwtAwtSpecialHandlers();
                setMethodNode("initSwtAwtGUI_", ast.newMethodDeclaration());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public boolean methodExists(String methodName) {
        if (methodName.indexOf("_") < 0)
            methodName += "_";
        return methodMap.containsKey(methodName);
    }

    private int getEndOfBlock(int pos) {
        int num = 0;
        //System.out.println("GET END OF BLOCK "+getText(pos, 20));
        while ((pos = findNextChar(pos, "{}/", true)) < getLength()) {
            if (pos < 0) {
                return -1;
            } else if (getText(pos, 1).equals("}")) {
                if (num == 0)
                    return pos;
                num--;
                pos++;
            } else if (getText(pos, END_BLOCK.length()).equals(END_BLOCK)) {
                if (num == 0) {
                    int eol = getEndOfLine(pos);
                    return eol;
                }
                num--;
                pos += 2;
            } else if (getText(pos, 1).equals("{")) {
                num++;
                pos++;
            } else if (getText(pos, START_BLOCK.length()).equals(START_BLOCK)) {
                num++;
                pos += 2;
            } else {
                pos++;
            }
        }
        return pos;
    }

    private int getStartOfBlock(int pos) {
        int num = 0;
        while ((pos = findNextChar(pos, "{}/", false)) > 0) {
            if (pos < 0) {
                return -1;
            } else if (getText(pos, 1).equals("}")) {
                num--;
                pos--;
            } else if (getText(pos - 1, END_BLOCK.length()).equals(END_BLOCK)) {
                num--;
                pos -= 2;
            } else if (getText(pos, 1).equals("{")) {
                num++;
                if (num == 1)
                    return pos;
                pos--;
            } else if (getText(pos - 1, START_BLOCK.length()).equals(START_BLOCK)) {
                num++;
                if (num == 1)
                    return pos - 1;
                pos -= 2;
            } else {
                pos--;
            }
        }
        return pos;
    }

    public int getStartOfLine(int pos) {
        //think about what happens if pos enters as the position of
        //the *first* character of a 2-char NL - unless the isNewLine
        //condition is used, we may return the position of the 2nd
        //NL character
        while (pos > 0 && !isNewLine(pos))
            pos--;
        if (isNewLine(pos))
            return pos + NL.length();
        return pos;
        //don't do the below!
        //while (pos > 0 && !isEOL(pos))
        //pos--;
        //pos++;
        //return pos;
    }

    private int getEndOfLine(int pos) {
        while (pos < getLength() && !isNewLine(pos))
            pos++;
        if (isNewLine(pos))
            return pos - 1;
        return pos;
    }

    private int getStartOfNextLine(int pos) {
        while (pos < getLength() && !isEOL(pos))
            pos++;
        if (isNewLine(pos))
            return pos + NL.length();
        if (pos < getLength())
            pos++;
        //might have started in the middle of a 2-char NL, so increase
        //pos and try again
        if (isNewLine(pos))
            return pos + NL.length();
        if (pos != getLength()) {
            System.err.println("SHOULDN'T BE HERE "
                    + showControls("NL=" + NL + ", char=" + getText(pos, 1) + ", context=" + getText(pos - 10, 20)));
            //new Exception().printStackTrace();
        }
        while (pos < getLength() && isEOL(pos))
            pos++;
        return pos;
    }

    private String getBlockName(IFormPropertySource fps) {
        String name = fps.getName();
        int pos = name.lastIndexOf(".");
        return name.substring(0, pos + 1);
    }

    private int[] getBlockPositions(FormComponent child) {
        if (!child.isVisual())
            return null;
        ASTNode cn = getAssignment(child);

        //        if(child.isSwingInSwt())
        //            cn = getConnection(child);

        if (jiglooPlugin.DEBUG)
            System.out.println("IS IN SUB BLOCK " + child + ", cn=" + cn);
        if (cn == null || isDeleted(cn)) {
            return null;
        }
//        System.out.println("get block posns "+cn.getStartPosition()+", "+getCodeForNode(cn));

        //		ASTNode pn = getAssignmentNode(parent.getName());
        //		System.out.println("IS IN SUB BLOCK " + child + ", " + parent + ",
        // pn=" + pn);
        //		if (pn == null)
        //			return false;

        int start = cn.getStartPosition();
        int end = getEndOfBlock(start);
        start = getStartOfBlock(start);
        if (jiglooPlugin.DEBUG)
            System.out.println("GOT BLOCK FOR " + child + " " + getText(start, end - start + 1));
        Iterator it = elementAssignments.keySet().iterator();
        //see if any elementAssignments inside this block belong
        //to FormComponents which are *not* children of this FormComponent
        while (it.hasNext()) {
            Object key = it.next();
            ASTNode ea = getAssignmentNode((String) key);
            if (ea.getStartPosition() > start && ea.getStartPosition() + ea.getLength() < end) {
                FormComponent fc = findFormComponent((String) key);
                if (fc != null && fc.isVisual() && !fc.equals(child) && child.isChildOf(fc)) {
                    if (jiglooPlugin.DEBUG)
                        System.out.println("IS IN SUB BLOCK: FOUND PARENT " + fc.getName() + " inside "
                                + child.getName());
                    return null;
                }
            }
        }

        if (isMethodBlock(start, end)) {
            if (jiglooPlugin.DEBUG)
                System.out.println("IS METHOD BLOCK " + start + ", " + end);
            return null;
        }
        if (isTryBlock(start, end)) {
            if (jiglooPlugin.DEBUG)
                System.out.println("IS TRY BLOCK " + start + ", " + end + ", "
                        + getText(start - 20, end - start + 40));
            return null;
        }
        
        return new int[] { start, end };
    }

    public void updateInCode(IFormPropertySource element, String pName) {
        if (parsing || addActionDialogOpen)
            return;
        //update constructor for CWT "bounds" property
        if (element instanceof FormComponent) {
            FormComponent fc = (FormComponent) element;
            if (fc.isCWT() && com.cloudgarden.jigloo.typewise.TypewiseManager.updateInCode(fc, pName, this)) {
                return;
            }
            if (fc.isBuilderComponent() && pName.equals("text")) {
                repairParentConnectionNode(fc);
                return;
            }
            repairInlineAssignment(fc);
            //if repairing the assignment *didn't* make it not inline,
            //then our job is done (since it would have changed the
            //constructor code instead.
            if (fc.isInline())
                return;
        }

        String code = "";

        boolean propSet = element.isPropertySet(pName);

        if (propSet
                && (!(element instanceof FormComponent) || 
                        !(editor.isPartOfAppFrameworkApplication() &&
                          editor.getBundleManager().canSetProperty((FormComponent) element, pName))) ) {
            code = element.getJavaCodeForPropertySetter(pName, this);
            if(code == null)
            	code = "";
        }
        Object propVal = element.getPropertyValue(pName);

        if (propVal instanceof FormComponentWrapper) {
            //remove it so it will be added in correct position
            ASTNode tcNode = getPropSetter(element, pName);
            removeNode(tcNode);
        }

        if (element instanceof FormComponent) {
            if (editor != null 
            		&& editor.isPartOfAppFrameworkApplication()
            		&& editor.getBundleManager().canSetProperty((FormComponent) element, pName)) {
                if (propSet)
                    editor.getBundleManager().setProperty((FormComponent) element, pName, propVal);
                else
                    editor.getBundleManager().removeProperty((FormComponent) element, pName);
            }
        }

        updateInCode(element, pName, code);
    }

    private ASTNode updateInCode(IFormPropertySource element, String pName, String code) {
        if (parsing || addActionDialogOpen)
            return null;

        FormComponent fc = null;
        if (element instanceof FormComponent) {
            fc = (FormComponent) element;
            //            repairInlineAssignment(fc);

            if (fc.isBuilderComponent() && pName.equals("text")) {
                repairParentConnectionNode(fc);
                return null;
            }

            if (fc.isSWT() && (pName.equals("layout") || pName.equals("menu")))
                return null;

            if (fc.isSyntheticProperty(pName) && !fc.canUpdateSyntheticProperty(pName))
                return null;

        }

        String name = element.getName();
        //        if (jiglooPlugin.DEBUG)
        //            System.out.println("UPDATE PROP IN CODE " + name + ", " + pName);
        name = resolveName(name);
        //System.out.println("UPDATE PROP IN CODE (2) " + name + ", " + pName);

        if (fc != null) {
            if (fc.isJFaceApplicationWindow()) {
                pName = ApplicationWindowManager.translatePropName(pName);
            }
        }

        HashMap map = getPropMap(name);

        Expression mic;
        if (element instanceof LayoutWrapper)
            mic = (Expression) map.get(editor.convertGroupLayoutProp(pName, true));
        else
            mic = (Expression) map.get(pName);

        //        System.out.println("UPDATE PROP IN CODE " + name + ", " + pName+", "+mic);

        if (isProtected(mic))
            return null;
        if (!element.isPropertySet(pName)) {
            if (jiglooPlugin.DEBUG)
                System.out.println("UPDATE IN CODE - NOT SET " + name + ", " + pName);
            if (mic != null) {
                int pos = getStartOfLine(mic.getStartPosition());
                removeNode(mic);
                removeSurroundingBraces(pos, element);
            }
            map.remove(pName);
        } else {
            if (pName.equals("buttonGroup")) {
                int pos = code.indexOf(".");
                if (pos >= 0) {
                    String bgName = code.substring(0, pos);
                    bgName = JiglooUtils.replace(bgName, "\t", "");
                    bgName = JiglooUtils.replace(bgName, " ", "");
                    String mn = convertToMethod(bgName, bgName, false);
                    if (mn != null)
                        code = JiglooUtils.replace(code, bgName + ".", mn + ".");
                }
            }
            if (fc != null && (fc.isA(CTabFolder.class) || fc.isA(TabFolder.class)) && "selection".equals(pName)) {
                //remove the setSelection call, so that it will be added back in the
                //correct place (after the last child).
                removeNode(mic);
            }
            addOrUpdate(mic, code, map, pName, element);
        }

        if (fc != null && (pName.equals("size") || pName.equals("bounds") || pName.equals("layoutData"))) {
            repairInCode(fc.getLayoutDataWrapper(), REPAIR_ALL);
        }
        return mic;
    }

    /**
     * Add or update a property setter (with name propName) for the given element
     * @param mic
     * @param code
     * @param map
     * @param propName
     * @param element
     */
    private void addOrUpdate(ASTNode mic, String code, HashMap map, String propName, IFormPropertySource element) {

        code = prepCode(code, null);
        code = fixTabs(code);
        boolean needsIndent = code.indexOf(NL) >= 0;
        int refPos = -1;
        if (mic == null || isDeleted(mic)) {

            //if the node doesn't exist and the code is empty, then we really
            //shouldn't be here and we certainly don't want to create a node
            if (code == null || "".equals(code))
                return;

            boolean after = false;
            String pref = "", post = "";

            ASTNode refNode = getPropSetterRefNode(element, propName);
            after = true;
            if (refNode == null) {
                if (initGUIMethod == null)
                    addInitGUIMethod();
                refNode = initGUIMethod.getBody();
                refPos = getStartOfInitGUI();
                String enic = element.getNameInCode();
                pref = getBlockDelimiterStart(enic);
                post = getBlockDelimiterEnd(enic);
            } else {
                refPos = getStartOfNextLine(refNode.getStartPosition() + refNode.getLength());
            }

            if (jiglooPlugin.DEBUG)
                System.out.println("***ADD NODE " + propName + ", after=" + after + ", " + element + ", "
                        + getCodeForNode(refNode));
            if (refNode == null) {
                System.err.println("REF NODE == NULL in addOrUpdate " + propName + ", " + element);
                return;
            }
            mic = ast.newMethodInvocation();
            addNode(mic, refPos, code, map, propName, pref, post);
        } else {
            updateNodeBody(mic, code);
        }

        if (isTailProperty(element, propName)) {
            FormComponent fc = null;

            if (element instanceof FormComponent) {

                fc = (FormComponent) element;
                int refPos2 = getFirstPosAfterLastChild(fc);
                if (refPos2 >= 0)
                    moveNode(mic, refPos2);

            } else if (element instanceof LayoutWrapper) {

                fc = ((LayoutWrapper) element).getFormComponent();
                int refPos2 = getFirstPosAfterLastChild(fc);

                ASTNode assNode = getPropSetter(fc, "layout");
                if (assNode == null)
                    assNode = getAssignment(element);
                int refPos3 = getStartOfLineAfter(assNode);
                MethodDeclaration assMeth = getEnclosingMethod(refPos3);
                MethodDeclaration refMeth = getEnclosingMethod(refPos2);

                //v4.0M3 - make sure the refPos we choose is latest in the code (but also,
                //presumably, in the same method)
//                if (refMeth.equals(assMeth) && refPos2 > refPos && refPos2 > refPos3) {
                if (refMeth != null && refMeth.equals(assMeth) && refPos2 > refPos3) {
                    moveNode(mic, refPos2);
//                    } else if (refPos3 > refPos && refPos3 > refPos2) {
                } else {
                    moveNode(mic, refPos3);
                }
                //v4.0M3 - added in check for being in a local block before moving
                //to just before pack (or setSize) call.
                //            	int[] bps = getBlockPositions(fc);
                //            	if(bps != null) {
                //            		refPos = getStartOfLine(bps[1]);
                //                } else {
                //                	ASTNode meth = null;
                //                	if(assNode != null)
                //                		meth = getEnclosingMethod(assNode.getStartPosition());
                //                    ASTNode packNode = null;
                //                	if (fc != null)
                //                		packNode = getPropSetter(fc, METHOD_PREFIX + "pack");
                //                	if (packNode != null && meth != null && isContainedBy(packNode, meth)) {
                //                		refPos = getStartOfLine(packNode.getStartPosition());
                //                	} else {
                //                		if (fc != null)
                //                			packNode = getPropSetter(fc, "size");
                //                		if (packNode != null && meth != null && isContainedBy(packNode, meth)) {
                //                			refPos = getStartOfLine(packNode.getStartPosition());
                //                		} else if(assNode != null) {
                //                			int end = getEndOfBlock(assNode.getStartPosition());
                //                			refPos = getStartOfLine(end);
                //                		}
                //                	}
                //                }
                //                if(refPos >= 0)
                //                	moveNode(mic, refPos);
            }
        }

        if (needsIndent && mic != null)
            indentCode(mic.getStartPosition(), mic.getStartPosition() + mic.getLength());
    }

    private int getStartMatchLength(int wclen, int sblen) {
        int start = 0;
        while (start < sblen && start < wclen && wcbuff.getChar(start) == buff.charAt(start))
            start++;
        return start;
    }

    private int getEndMatchLength(int wclen, int sblen, int start) {
        int end = 0;
        while (end < sblen && end < wclen && wclen - 1 - end >= start && sblen - 1 - end >= start
                && wcbuff.getChar(wclen - 1 - end) == buff.charAt(sblen - 1 - end))
            end++;
        return end;
    }

    /**
     * For *some* reason, calling updateWCBufferNow from a new Thread
     * prevents IllegalStateExceptions!!!
     */
    public void updateWCBuffer() {
        //        try {
        //            JavaCore.run(new IWorkspaceRunnable() {
        //                public void run(IProgressMonitor monitor) throws CoreException {
        //                    try {
        //                        updateWCBufferNow();
        //                    } catch(Throwable t) {
        //                        t.printStackTrace();
        //                    }
        //                }
        //            }, null);
        //        } catch(Throwable t) {
        //            t.printStackTrace();
        //        }

        //    	new Thread() {
        //    		public void run() {
        //    			updateWCBufferNow();
        //    		}
        //    	}.start();

        //        Display.getDefault().asyncExec(new Runnable() {
        //            public void run() {
        //                updateWCBufferNow();
        //            }
        //        });

        updateWCBufferNow();
    }

    public boolean bufferChanged() {
        return bufferChanged;
    }

    public void updateWCBufferNow() {
    	
//    	indentCodeNew(0, getLength()-1);
    	
        bufferChanged = false;
        updating = true;
        int wclen = wcbuff.getLength();
        int sblen = buff.length();
        int start = getStartMatchLength(wclen, sblen);
        if (wclen == sblen && start == sblen) {
            updating = false;
            return;
        }

        int end = getEndMatchLength(wclen, sblen, start);

        //		end--;
        //end++;
        //if the last char differs, end will be 0, so sbend=sblen-end;

        int sbend = sblen - end;
        final int wcend = wclen - end;

        //System.out.println("UPDATE WCBUFF start=" + start + ", wcend=" +
        // wcend + ", sbend=" + sbend);

        //		indentCodeNew(start, sbend);
        //		sblen = buff.length();
        //		sbend = sblen - end;

        final String newCode = buff.substring(start, sbend);

        if (start < 0 || wcend > wcbuff.getLength() || wcend - start < 0) {
            System.out.println("ERROR UPDATING CODE: START=" + start + ", len=" + (wcend - start) + ", buffLen="
                    + wcbuff.getLength());
            System.out.println("NEW CODE=" + showControls(newCode));
            System.out.println("SB=\n" + showControls(buff.toString()));
            System.out.println("WC=\n" + showControls(wcbuff.getContents()));
            //String st = showControls(wcbuff.getText(0, start));
            //String mid = showControls(wcbuff.getText(start, wcend - start));
            //String ed = showControls(wcbuff.getText(wcend, end));
            start = -1;
        } else {
            try {

                //wcbuff.setContents(buff.toString());
                //OR
                wcbuff.replace(start, wcend - start, newCode);

                editor.resetVisibleRegion();

            } catch (Throwable t) {
                t.printStackTrace();
                System.out.println("ERROR (2) UPDATING CODE: START=" + start + ", len=" + (wcend - start)
                        + ", buffLen=" + wcbuff.getLength());
                System.out.println("NEW CODE=" + showControls(newCode));
                System.out.println("SB=\n" + showControls(buff.toString()));
                System.out.println("WC=\n" + showControls(wcbuff.getContents()));
            }
        }
        //System.out.println("UPDATED CODE=" + wcbuff.getContents() + ",
        // START=" + start + ", end=" + end);
        //buff.replace(0, buff.length(), wcbuff.getContents());
        updating = false;
        if (needsReparse)
            reparse();
        compareBufferLengths();

        int pos = -1;

        //        pos = start;

        if (codeSelectPos != -1)
            pos = codeSelectPos;

        if (pos < 0) {
            if (lastNodeChanged != null)
                pos = lastNodeChanged.getStartPosition();
            else if (editor.getSelectedComponent() != null) {
                ASTNode node = getClosestNode(editor.getSelectedComponent(), null);
                if (node != null)
                    pos = node.getStartPosition();
            }
        }

        if (pos != -1) {
            //        		pos = getStartOfLine(pos);
            if (pos >= 0 && pos <= getLength() - 2) {
                if (!editor.isDisposed()) {

                    //                		System.err.println("HIGHLIGHT POS="+pos);
                    selectInCode(pos);

                }
            }
        }
        codeSelectPos = -1;
        lastNodeChanged = null;
    }

    private int codeSelectPos = -1;

    /**
     * Sets a position that will be selected in the code *after* the next
     * call to setDirtyAndUpdate
     */
    public void setCodeSelectPosition(int pos) {
        codeSelectPos = pos;
    }

    private void compareBufferLengths() {
        int wclen = wcbuff.getLength();
        int sblen = buff.length();
        if (wclen != sblen) {
            System.err.println("Mismatch in buffer lengths, wcLen=" + wcbuff.getLength() + ", sbLen=" + buff.length());
            String s1 = showControls(buff.toString());
            String s2 = showControls(wcbuff.getContents());
            System.out.println("SB, " + s1.length() + "\n" + s1);
            System.out.println("WC, " + s2.length() + "\n" + s2);
            //start = getStartMatchLength(wclen, sblen);
            //System.out.println("START MATCH UP TO '" + buff.substring(0,
            // start) + "'");
            //System.out.println("BUFF ='" + buff.substring(start) + "'");
            //System.out.println("WCBUFF ='" + wcbuff.getText(start, wclen -
            // start) + "'");
            //reparse();
        }
    }

    private String showControls(String str) {
        str = JiglooUtils.replace(str, "\t", ".");
        str = JiglooUtils.replace(str, " ", "_");
        str = JiglooUtils.replace(str, "\n", "$");
        str = JiglooUtils.replace(str, "\r", "#");
        return str;
    }

    public void updateInCode(IFormPropertySource element) {

        if (parsing || addActionDialogOpen)
            return;
        Vector propNames = element.getPropertyNames();
        if (propNames == null)
            return;
        if (jiglooPlugin.DEBUG)
            System.out.println("UPDATE IN CODE " + element);
        for (int n = 0; n < propNames.size(); n++) {
            String pName = (String) propNames.elementAt(n);
            //System.out.println("UPDATE IN CODE "+element+", "+pName+",
            // "+element.needsUpdateInCode(pName));
            if (element.needsUpdateInCode(pName)) {
                updateInCode(element, pName);
            }
        }
        if (element instanceof FormComponent) {
            FormComponent fc = (FormComponent) element;
            HashMap eps = fc.getExtraProperties();
            if (eps != null) {
                Iterator it = eps.keySet().iterator();
                if (jiglooPlugin.DEBUG)
                    System.out.println("UPDATE IN CODE " + element + ", EXTRA PROPS=" + eps);
                while (it.hasNext()) {
                    String id = (String) it.next();
                    if (!fc.hasProperty(id)) {
                        String code = (String) eps.get(id);
                        code = JiglooUtils.replace(code, "#%NAME%#.", element.getNameInCode() + ".");
                        if (!code.endsWith(";") && !code.endsWith("\n") && !code.endsWith("\r")) {
                            code += ";";
                        }
                    }
                }
            }
        }
    }

    private boolean isCutNode(Object node) {
        return cutNodes.contains(node);
    }

    private void addCutNode(Object node) {
        if (!isCutNode(node))
            cutNodes.add(node);
    }

    private void removeCutNode(Object node) {
        cutNodes.remove(node);
    }

    private void incorporateCodeBlock(CodeBlock cb, int startPos) {
        HashMap nodes = cb.getNodes();
        //System.err.println("INCORPORATING "+cb.getCode());
        HashMap propMap = null;
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            String key = null;
            String skey = null;
            String fcName = null;
            FormComponent fc = null;
            if (obj instanceof String) {
                key = (String) obj;
                skey = key.substring(key.indexOf("_") + 1);
                int dot = skey.indexOf("&");
                if (dot > 0) {
                    fcName = skey.substring(0, dot);
                    fc = findFormComponent(fcName);
                    skey = skey.substring(dot + 1);
                    propMap = getPropMap(fcName);
                }
            }
            Object node = nodes.get(obj);
            ASTNode astNode = null;
            Comment cmnt = null;
            if (node instanceof ASTNode) {
                astNode = (ASTNode) node;
                astNode.setSourceRange(astNode.getStartPosition() + startPos, astNode.getLength());
                if (jiglooPlugin.DEBUG)
                    System.err.println("INCORPORATED " + fcName + ", " + skey + ", " + key + ", "
                            + getCodeForNode(astNode));
            } else if (node instanceof Comment) {
                cmnt = (Comment) node;
                cmnt.setSourceRange(cmnt.startPosition + startPos, cmnt.length);
            }

            removeCutNode(node);

            if (obj instanceof Comment) {
                comments.put(cmnt, cmnt);
            } else if (key.startsWith("^PROP_")) {
                propMap.put(skey, astNode);
                if (skey.indexOf(EVENT_HANDLER) > 0 && fc != null) {
                    //handleEventListener((Expression) ((MethodInvocation)
                    // astNode).arguments().get(0), fcName, fc);
                    /*
                     * String code = getCodeForNode(astNode); int pos =
                     * code.indexOf("{"); if (pos >= 0) { int pos2 =
                     * code.lastIndexOf("}"); if (pos2 > 0) { code =
                     * code.substring(pos + 1, pos2); } }
                     * fc.getEventWrapper().setHandler( listener, mName,
                     * resolveName(fc.getName()) +
                     * JiglooUtils.capitalize(mName), code); //put the
                     * mouseMoved() {..} method declaration //in the button's
                     * property node-map pmap.put(getUnqualifiedName(listener) +
                     * EVENT_HANDLER + mName, md);
                     */
                }
            } else if (key.startsWith("^CONN_")) {
                setConnectionNode(skey, astNode);
            } else if (key.startsWith("^FDEC_")) {
                setFieldDecNode(skey, astNode);
            } else if (key.startsWith("^ASS_")) {
                setAssignmentNode(skey, astNode);
            } else if (key.startsWith("^METH_")) {
                setMethodNode(skey, astNode);
            }
        }
    }

    private void removeNonChildFormComponents(HashMap nodes, FormComponent parent, boolean removeChildrenFromEditor) {
        if (nodes == null)
            return;
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof String) {
                String name = (String) next;
                if (name.startsWith("^ASS_")) {
                    name = name.substring(5);
                    //don't remove self
                    if (!name.equals(parent.getName())) {
                        FormComponent fc = findFormComponent(name);
                        if (fc != null && !fc.isChildOf(parent) && removeChildrenFromEditor) {
                            editor.removeComponent(fc, false);
                        }
                    }
                }
            }
        }
    }

    private Object getSurroundingNode(int pos, HashMap map) {
        if (map == null) {
            Object o = null;
            Iterator it = setPropMethods.keySet().iterator();
            while (it.hasNext()) {
                String fcName = (String) it.next();
                HashMap pmap = (HashMap) setPropMethods.get(fcName);
                o = getSurroundingNode(pos, pmap);
                if (o != null)
                    return o;
            }
            o = getSurroundingNode(pos, elementConnections);
            if (o != null)
                return o;
            o = getSurroundingNode(pos, fieldDecs);
            if (o != null)
                return o;
            o = getSurroundingNode(pos, elementAssignments);
            if (o != null)
                return o;
            //            o = getSurroundingNode(pos, methods);
            //            if(o != null)
            //                return o;
            //            o = getSurroundingNode(pos, comments);
            //            if(o != null)
            //                return o;
        } else {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                Object obj = map.get(key);

                if (isCutNode(obj))
                    continue;

                int st = getStartPos(obj);
                int end = getEndPos(obj);
                if (st <= pos && end >= pos)
                    return obj;
            }
        }
        return null;
    }

    private Vector getContainedNodes(int start, int end, Vector nodes, HashMap map) {
        if (nodes == null) {
            nodes = new Vector();
            Iterator it = setPropMethods.keySet().iterator();
            while (it.hasNext()) {
                String fcName = (String) it.next();
                HashMap pmap = (HashMap) setPropMethods.get(fcName);
                getContainedNodes(start, end, nodes, pmap);
            }
            getContainedNodes(start, end, nodes, elementConnections);
            getContainedNodes(start, end, nodes, fieldDecs);
            getContainedNodes(start, end, nodes, elementAssignments);
            getContainedNodes(start, end, nodes, methodMap);
            getContainedNodes(start, end, nodes, comments);
        } else {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                Object obj = map.get(key);

                if (isCutNode(obj))
                    continue;

                if (obj instanceof ASTNode) {
                    ASTNode node = (ASTNode) obj;
                    if (node.getStartPosition() >= start && node.getStartPosition() < end) {
                        nodes.add(node);
                        //this part is special to this method, since we want to
                        // update the
                        //position of the method body as well as the method
                        // when we indent
                        if (node instanceof MethodDeclaration) {
                            Block body = ((MethodDeclaration) node).getBody();
                            if (body != null && body.getStartPosition() >= start && body.getStartPosition() < end)
                                nodes.add(body);
                        }
                    }
                } else if (obj instanceof Comment) {
                    Comment node = (Comment) obj;
                    if (node.startPosition >= start && node.startPosition < end) {
                        nodes.add(node);
                    }
                }
            }
        }
        return nodes;
    }

    private Vector getContainedNodes(int start, int end) {
        return getContainedNodes(start, end, null, null);
    }

    private HashMap getContainedNodes(int start, int end, boolean remove) {
        return getContainedNodes(start, end, remove, true);
    }

    private HashMap getContainedNodes(int start, int end, boolean remove, boolean changePosns) {
        HashMap finalMap = new HashMap();
        getContainedNodes(start, end, "", null, finalMap, remove, changePosns);
        return finalMap;
    }

    private void getContainedNodes(int start, int end, String mapID, HashMap map, HashMap finalMap, boolean remove,
            boolean changePosns) {
        if (map == null) {
            Iterator it = setPropMethods.keySet().iterator();
            while (it.hasNext()) {
                String fcName = (String) it.next();
                HashMap pmap = (HashMap) setPropMethods.get(fcName);
                getContainedNodes(start, end, "^PROP_" + fcName + "&", pmap, finalMap, remove, changePosns);
            }
            getContainedNodes(start, end, "^CONN_", elementConnections, finalMap, remove, changePosns);
            getContainedNodes(start, end, "^FDEC_", fieldDecs, finalMap, remove, changePosns);
            getContainedNodes(start, end, "^ASS_", elementAssignments, finalMap, remove, changePosns);
            getContainedNodes(start, end, "^METH_", methodMap, finalMap, remove, changePosns);
            getContainedNodes(start, end, "^CMNT_", comments, finalMap, remove, changePosns);
        } else {
            Iterator it = map.keySet().iterator();
            Vector rem = new Vector();
            while (it.hasNext()) {
                Object key = it.next();
                Object obj = map.get(key);
                if (obj instanceof ASTNode) {
                    ASTNode node = (ASTNode) obj;
                    if (node.getStartPosition() >= start && node.getStartPosition() < end) {
                        finalMap.put(mapID + key, node);
                        //make node's start position relative to the start of
                        // the block
                        //(incorporateCodeBlock will make it abolute again)
                        //This is because the block may be moved after this
                        // method
                        //is called (when deleting multiple components).
                        if (changePosns) {
                            addCutNode(node);
                            node.setSourceRange(node.getStartPosition() - start, node.getLength());
                        }
                        if (remove) {
                            rem.add(key);
                        }
                    }
                } else if (obj instanceof Comment) {
                    Comment node = (Comment) obj;
                    if (node.startPosition >= start && node.startPosition < end) {
                        finalMap.put(key, node);
                        if (changePosns) {
                            addCutNode(node);
                            node.setSourceRange(node.startPosition - start, node.length);
                        }
                        if (remove) {
                            rem.add(key);
                        }
                    }
                }
            }
            if (remove) {
                for (int i = 0; i < rem.size(); i++)
                    map.remove(rem.elementAt(i));
            }
        }
    }

    private boolean rangeContainsAssignment(int start, int end) {
        Iterator it = elementAssignments.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object obj = getAssignmentNode((String) key);
            ASTNode node = (ASTNode) obj;
            if (node.getStartPosition() > start && node.getStartPosition() < end) {
                return true;
            }
        }
        return false;
    }

    //use a HashMap instead of a Vector for fast containsKey method
    //(at least, I hope it's faster than Vector.contains
    private HashMap nodesMoved = new HashMap();

    private void textInserted(int start, int length, int end) {
        if (length == 0)
            return;
        nodesMoved.clear();
        // update comments first, so that findNextChar (and
        // getEnclosingComment) will work correctly
        textInserted(start, length, end, comments);
        Iterator it = setPropMethods.keySet().iterator();
        while (it.hasNext()) {
            String fcName = (String) it.next();
            //System.err.println("CHECKING MICS FOR " + fcName);
            HashMap map = (HashMap) setPropMethods.get(fcName);
            textInserted(start, length, end, map);
        }

        textInserted(start, length, end, protectedBlocks);
        textInserted(start, length, end, hiddenBlocks);
        textInserted(start, length, end, regResUserNode);
        textInserted(start, length, end, mainType);
        textInserted(start, length, end, superClass);
        textInserted(start, length, end, lafNode);
        textInserted(start, length, end, elementConnections);
        textInserted(start, length, end, fieldDecs);
        textInserted(start, length, end, elementAssignments);
        textInserted(start, length, end, imports);
        textInserted(start, length, end, methodMap);
        //System.out.println("TEXT INSERTED " + start + ", " + length);
        //System.out.println("INIT GUI=" + getCodeForNode(initGUIMethod));
    }

    private void textInserted(int start, int length, int end, HashMap map) {
        Iterator it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            Object propName = it2.next();
            //System.err.println("CHECKING " + propName+",
            // "+getCodeForNode(map.get(propName)));
            textInserted(start, length, end, map.get(propName));
        }
    }

    private void textInserted(int start, int length, int end, Vector vec) {
        for (int i = 0; i < vec.size(); i++) {
            int[] block = (int[]) vec.elementAt(i);
            if (end <= 0) {
                if (start > block[0] && start <= block[1]) {
                    block[1] += length;
                    System.err.println("Text inserted/removed in protected/hidden block, start, end, len = " + start
                            + ", " + end + ", " + length);
                } else if (start < block[0]) {
                    block[0] += length;
                    block[1] += length;
                }
            } else {
                if (start < block[0] && end > block[1]) {
                    block[0] -= length;
                    block[1] -= length;
                } else if (start > block[1] && end < block[0]) {
                    block[0] += length;
                    block[1] += length;
                } else if (start > block[1] && end > block[1]) {
                } else if (start < block[0] && end < block[0]) {
                } else if (start > block[0] && start < block[1]) {
                    if (end < block[0]) {
                        block[0] += length;
                    } else if (end > block[1]) {
                        block[1] -= length;
                    }
                    System.err.println("Text inserted/removed in protected/hidden block, start, end, len = " + start
                            + ", " + end + ", " + length);
                } else if (end > block[0] && end < block[1]) {
                    if (start < block[0]) {
                        block[0] -= length;
                    } else if (start > block[1]) {
                        block[1] += length;
                    }
                    System.err.println("Text inserted/removed in protected/hidden block, start, end, len = " + start
                            + ", " + end + ", " + length);
                }
            }
        }
    }

    private ASTNode lastNodeChanged = null;

    private boolean monitorPositions = true;

    /**
     * if end != 0 then a block of length "length" has been moved from "start" to "end"
     * if end == 0 then text has either been inserted (if length > 0) or deleted (length < 0)
     * at position "start" 
     */
    private void textInserted(int start, int length, int end, Object nodeObj) {
        if (nodeObj == null)
            return;
        if (isCutNode(nodeObj))
            return;

        if (isDeleted(nodeObj))
            return;

        //ensure that a node is not moved more than once when
        //some text is inserted/deleted
        if (nodesMoved.containsKey(nodeObj))
            return;
        nodesMoved.put(nodeObj, "");

        ASTNode node = null;
        Comment cmnt = null;
        int srcStart = -1;
        int srcLen = -1;
        int blockOffsetSt = -1;
        int blockOffsetEnd = -1;

        try {
            if (nodeObj instanceof ASTNode)
                node = (ASTNode) nodeObj;
            else if (nodeObj instanceof Comment)
                cmnt = (Comment) nodeObj;
            int micst = -1;
            int miclen = -1;
            if (node != null) {
                micst = node.getStartPosition();
                miclen = node.getLength();
            } else {
                micst = cmnt.startPosition;
                miclen = cmnt.length;
            }

            if (node instanceof MethodDeclaration) {
                MethodDeclaration md = (MethodDeclaration) node;
                Block body = md.getBody();
                if (body != null) {
                    blockOffsetSt = body.getStartPosition() - md.getStartPosition();
                    blockOffsetEnd = md.getStartPosition() + md.getLength() - body.getStartPosition()
                            - body.getLength();
                }
            }

            int micend = micst + miclen;
            srcStart = micst;
            srcLen = miclen;
            if (end > 0) {
                int pos1, pos2;
                if (start > end) {
                    pos1 = end;
                    pos2 = start;
                } else {
                    pos1 = start + length;
                    pos2 = end;
                }
                //text block moved
                //                if (micst > start && micend <= start + length) {
                if (micst >= start && micend <= start + length) {
                    //inside block moved
                    if (start < end) {
                        srcStart = micst + (end - start - length);
                    } else {
                        srcStart = micst + (end - start);
                    }
                    //                } else if (micst > pos1 && micst <= pos2) {
                } else if (micst >= pos1 && micst <= pos2) {
                    if (micend <= pos2) {
                        //move code outside block (but between start and end)
                        // the opposite way
                        if (start < end) {
                            srcStart -= length;
                        } else {
                            srcStart += length;
                        }
                    } else {
                        if (start < end) {
                            srcStart -= length;
                            srcLen += length;
                        } else {
                            srcStart += length;
                            srcLen -= length;
                        }
                    }
                } else if (micend >= pos1 && micend <= pos2) {
                    if (micst <= pos1) {
                        if (start < end) {
                            srcLen -= length;
                        } else {
                            srcLen += length;
                        }
                    }
                }
            } else {
                if (length < 0 && (micst > start && micend < start - length)) { //node removed
                    if (jiglooPlugin.DEBUG)
                        System.err.println("NODE ALREADY DELETED " + length + ", " + start + ", " + micst + ", "
                                + miclen + ", " + node);
                    if (node != null)
                        node.setSourceRange(0, 0);
                    if (cmnt != null)
                        cmnt.setSourceRange(0, 0);
                    return;
                }

                if (micend > start) {
                    if (micst > start) {
                        srcStart += length;
                    } else if (micst == start) {
                        if (length < 0)
                            srcLen += length; //v3.9.0
                        else
                            srcStart += length;
                    } else {
                        srcLen += length;
                    }
                }
            }
            Block b = null;
            if (srcStart != micst || srcLen != miclen) {
                if (node != null) {
                    if (srcLen < 0)
                        throw new RuntimeException("srcLen<0 " + srcLen);
                    node.setSourceRange(srcStart, srcLen);
                    //                    if (monitorPositions && !isEOL(srcStart - 1)
                    //                            && !isWhiteSpace(srcStart - 1)
                    //                            && getChar(srcStart - 1) != '(') {
                    //                        throw new RuntimeException(
                    //                                "Node doesn't start with whitespace ");
                    //                    }
                } else {
                    cmnt.setSourceRange(srcStart, srcLen);
                    //                    if (monitorPositions && !isEOL(srcStart - 1)
                    //                            && !isWhiteSpace(srcStart - 1)
                    //                            && getChar(srcStart - 1) != '(') {
                    //                        throw new RuntimeException(
                    //                                "Comment doesn't start with whitespace ");
                    //                    }
                }
                if (node instanceof MethodDeclaration) {
                    MethodDeclaration md = (MethodDeclaration) node;
                    b = md.getBody();
                    if (b != null && blockOffsetSt >= 0) {
                        int bst = md.getStartPosition() + blockOffsetSt;
                        int bend = md.getStartPosition() + md.getLength() - blockOffsetEnd;
                        try {
                            b.setSourceRange(bst, bend - bst);
                            //							if(!isEOL(bst-1) && !isWhiteSpace(bst-1) &&
                            // getChar(bst-1) != '(') {
                            //							    throw new RuntimeException("Method doesn't start
                            // with whitespace ");
                            //							}

                            //							int bst = findNextChar(md.getStartPosition(),
                            // "{", true);
                            //							int bend = findNextChar(md.getStartPosition() +
                            // md.getLength() + 1, "}", false);
                            //							//System.err.println("BODY=" + bst + ", " +
                            // md.getStartPosition() + ", " + bend);
                            //							//System.err.println("TXT1=" +
                            // getText(md.getStartPosition(), md.getLength() +
                            // 1));
                            //							//System.err.println("TXT=" + getText(bst, bend -
                            // bst + 1));
                            //							if (bst >= 0 && bend > bst) {
                            //								b.setSourceRange(bst, bend - bst + 1);
                            //							} else {
                            //								System.err.println("ERROR (1) SETTING BODY " +
                            // bst + ", " + (bend - bst + 1));
                            //								bst = indexOf("{", md.getStartPosition());
                            //								bend = md.getStartPosition() + md.getLength();
                            //								while (getChar(bend) != '}')
                            //									bend--;
                            //								if (bst >= 0 && bend > bst) {
                            //									b.setSourceRange(bst, bend - bst + 1);
                            //								} else {
                            //									System.err.println("ERROR SETTING BODY " + bst +
                            // ", " + (bend - bst + 1));
                            //									System.err.println("MD=" +
                            // getText(md.getStartPosition(), md.getLength()));
                            //									//new Exception().printStackTrace();
                            //								}
                            //							}
                        } catch (Throwable e) {
                            System.err.println("SET SRC RANGE " + bst + ", " + (bend - bst) + ", "
                                    + getCodeForNode(node));
                            e.printStackTrace();
                        }
                        //System.err.println("MOVED BODY " +
                        // getCodeForNode(b));
                    }
                }
                //System.err.println("MOVED NODE " + getCodeForNode(node));
            }

        } catch (Throwable e) {
            System.err.println("ERROR IN TEXT INSERTED " + getCodeForNode(node) + ", " + start + ", " + length + ", "
                    + end + ", " + srcStart + ", " + srcLen + ", " + e);
            e.printStackTrace();
            //			Iterator it = fieldDecs.keySet().iterator();
            //			while (it.hasNext()) {
            //				Object key = it.next();
            //				ASTNode n = (ASTNode) fieldDecs.get(key);
            //				System.err.println("GOT FIELD DEC " + key + ", " +
            // getCodeForNode(n));
            //			}
        }
    }

    private String prepCode(String code, String oldCode) {
        return prepCode(code, oldCode, true);
    }

    private String prepCode(String code, String oldCode, boolean fixNLs) {
        boolean removeSemiColon = false;
        if (oldCode != null && !"".equals(oldCode) && !oldCode.endsWith(";") && !oldCode.endsWith("\n")
                && !oldCode.endsWith("\r") && !oldCode.endsWith("\t") && !oldCode.endsWith(" "))
            removeSemiColon = true;
        while (code.startsWith("\t"))
            code = code.substring(1);
        while (code.endsWith("\n") || code.endsWith("\r"))
            code = JiglooUtils.chop(code);
        if (removeSemiColon) {
            while (code.endsWith(";"))
                code = JiglooUtils.chop(code);
        }
        if (fixNLs) {
            code = fixNewLines(code);
        }
        //System.out.println("PREPPED
        // CODE '" + code +
        // "'");
        return code;
    }

    private void updateNodeBody(ASTNode node, String body) {
        if (!editor.updatesJavaCode()) {
            System.err.println("***UPDATING JAVA CODE WHEN NOT SUPPOSED TO");
            new Exception().printStackTrace();
            return;
        }
        if (isProtected(node))
            return;
        if (body == null)
            return;
        String oldCode = getCodeForNode(node);
        body = prepCode(body, oldCode);
        body = fixTabs(body);
        if (jiglooPlugin.DEBUG)
            System.out.println("***updateNodeBody " + oldCode + ", " + body);
        if (body.equals(oldCode))
            return;
        int st = node.getStartPosition();
        int blen = body.length();
        int len = node.getLength();
        if (st == 0 && len == 0) {
            System.err.println("UPDATING DELETED NODE " + body);
            new Exception().printStackTrace();
            return;
        }

        monitorPositions = false;
        replaceText(body, st, len, true, false);
        monitorPositions = true;

        //System.out.println(
        //"INSERTED TEXT, " + (blen - len) + ", " + (len2 - len1));
        node.setSourceRange(st, blen);
        lastNodeChanged = node;
    }

    private boolean updating = false;

    public boolean isChangingCode() {
        return updating;
    }

    private boolean isDeleted(Object node) {
    	if(isCutNode(node))
    		return true;
        return getStartPos(node) == 0 && getLength(node) == 0;
    }

    private int removeNode(ASTNode node) {
        return removeNode(node, true);
    }

    private int removeNode(ASTNode node, boolean zeroOut) {
        if (!editor.updatesJavaCode()) {
            System.err.println("***UPDATING JAVA CODE WHEN NOT SUPPOSED TO");
            new Exception().printStackTrace();
            return -1;
        }

        if (node == null || isDeleted(node)) // || isCutNode(node))
            return 0;

        if (isHidden(node) || isProtected(node))
            return 0;

        //String cont = buff.getContents();

        int start = getStartOfLine(node.getStartPosition());

        int pos = getStartOfNextLine(node.getStartPosition() + node.getLength());

        int len = pos - start;
        if (jiglooPlugin.DEBUG)
            System.err.println("***REMOVE NODE: " + getCodeForNode(node) + ", start=" + start + ", len=" + len);

        //do this so that we will not attempt to move this node
        //(since this can cause an error if the "phantom" node
        //gets moved across the area it used to be, causing a
        //negative source length for it.
        if (zeroOut)
            node.setSourceRange(0, 0);

        //System.out.println("***REMOVE NODE " + start + ", " + len + ", " +
        // buff.getText(start, len));
        replaceText("", start, len, true);
        start = removeExcessNewlines(start);

        return start;
    }

    private int insertText(String txt, int pos) {
        //System.out.println("INSERT TEXT '" + txt + "'");
        return replaceText(txt, pos, 0, true, false);
    }

    private int replaceText(String txt, int pos, int replace, boolean updatePosns) {
        return replaceText(txt, pos, replace, updatePosns, pos, true);
    }

    private int replaceText(String txt, int pos, int replace, boolean updatePosns, boolean fixText) {
        return replaceText(txt, pos, replace, updatePosns, pos, fixText);
    }

    /**
     * startPos is usually the same as pos, but if text is being replaced inside
     * a node, then startPos should be *inside* the node, otherwise the node
     * will be moved instead of lengthened/shortened
     */
    private int replaceText(String txt, int pos, int replace, boolean updatePosns, int startPos, boolean fixText) {
        updating = true;
        try {

            //updatePosns is false when moving code, and wedon't want to
            //change the length of txt when moving code because this messes
            //everything up!!!

            if (fixText)
                txt = fixNewLines(txt);

            if (txt.indexOf("\n") >= 0 && txt.indexOf(NL) < 0) {
                System.err.println("NON-NL inserted " + txt);
            }
            if (pos > 0 && getText(pos - 1, 2).equals(NL)) {
                System.err.println("INSERTING TEXT INSIDE NL " + txt);
                new Exception().printStackTrace();
            }

            if (txt.length() == 0 && replace == 0)
                return 0;
            int len1 = getLength();

//            System.out.println(
//                    "REPLACING TEXT "
//                    + pos
//                    + ", "
//                    + (pos + replace + 1)
//                    + ", old txt='"
//                    + showControls(getText(pos, replace))
//                    + ", new txt='"
//                    + showControls(txt)
//                    + "', txt.len="
//                    + txt.length()
//                    + ", len="
//                    + getLength());
            
            if (txt.equals(getText(pos, replace)))
                return 0;
            if (pos + replace <= buff.length() && pos >= 0)
                buff.replace(pos, pos + replace, txt);
            else {
                System.out.println("TRYING TO REPLACE OFF END OF BUFFER " + pos + ", " + replace + ", " + buff.length()
                        + ", " + txt);
                updating = false;
                return 0;
            }

            if (jiglooPlugin.getJavaVersion() < 14)
                buffString = buff.toString();

            bufferChanged = true;

            int len2 = getLength();

            if (updatePosns) {
                textInserted(startPos, len2 - len1, -1);
            }
            //checkBuffer();

            //just for testing
            //            updateWCBuffer();

            //            System.err.println("UPDATED TEXT "+buff);
            //            new Exception().printStackTrace();

            updating = false;
            return len2 - len1;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        updating = false;
        return 0;
    }

    private boolean checkBuffer() {
        if (NL.length() == 1)
            return true;
        String nl1 = NL.substring(0, 1);
        String nl2 = NL.substring(1);
        for (int i = 0; i < wcbuff.getLength() - 1; i++) {
            String str = wcbuff.getText(i, 2);
            if ((str.startsWith(nl1) || str.endsWith(nl2)) && !str.equals(NL)) {
                int start = i - 10;
                if (start < 0)
                    start = 0;
                int end = i + 20;
                if (end > wcbuff.getLength() - 1)
                    end = wcbuff.getLength() - 1;
                System.err.println("INCONSISTENT LINE DELIMITERS - expected \\r\\n but found \\r");
                return false;
            }
        }
        return true;
    }

    private boolean isWhiteSpace(int pos) {
        return isWhiteSpace(getChar(pos));
    }

    private boolean isWhiteSpace(char chr) {
        return chr == '\t' || chr == ' ';
    }

    private boolean isEOL(char chr) {
        return chr == '\n' || chr == '\r';
    }

    private boolean isEOL(int pos) {
        return isEOL(getChar(pos));
    }

    private boolean isNewLine(int pos) {
        return indexOf(NL, pos) == pos;
    }

    private String getPrevIndent(int pos) {
        while (pos > 0 && !isEOL(pos)) {
            pos--; //find first non-white, non-eol char
        }
        //        boolean looking = true;
        //        while(looking) {
        //            if (pos <= 0 || (!isWhiteSpace(pos) || !isEOL(pos))) {
        //                looking = false;
        //            } else {
        //                Comment c = getEnclosingComment(pos);
        //                if (c != null) {
        //                    pos = c.startPosition;
        //                } else {
        //                    pos--;
        //                }
        //            }
        //        }

        while (pos > 0 && (isWhiteSpace(pos) || isEOL(pos))) {
            pos--;
        }

        Object o = getSurroundingNode(pos, null);
        if (o != null)
            pos = getStartPos(o);

        pos = getStartOfLine(pos);
        int start = pos;
        while (isWhiteSpace(pos)) {
            pos++;
        }

        //        if(getChar(pos) == '.')
        //            return getPrevIndent(start);

        int len = pos - start;
        if (len <= 0)
            return "";
        String indent = getText(start, len);
        for (int i = 0; i < indent.length(); i++) {
            char chr = indent.charAt(i);
            if (chr != '\t' && chr != ' ') {
                System.err.println("GOT NON-WHITE INDENT " + indent);
                return "\t";
            }
        } //System.out.println("***GOT INDENT " + pos + ", " + len + ", '" +
        // indent + "'");
        return indent;
    }

    private ASTNode addNode(ASTNode node, ASTNode refNode, String body, HashMap hmap, 
            String pName, boolean asChild, boolean after, String prefix, String postfix) {
        if (!editor.updatesJavaCode()) {
            System.err.println("***UPDATING JAVA CODE WHEN NOT SUPPOSED TO");
            new Exception().printStackTrace();
            return null;
        }
        if ("".equals(body))
            return null;
        if (refNode == null) {
            System.err.println("***REF NODE = null " + pName + ", " + body);
            return null;
        }

        int pos = refNode.getStartPosition();
        if (asChild) {
        	if(refNode.equals(initGUIMethod)) {
        		pos = getStartOfInitGUI();
        	} else {
        		pos = findNextChar(pos, "{", true);
        		int sol = getStartOfNextLine(pos);
        		int cb = findNextChar(pos, "}", true);
        		if (sol < cb)
        			pos = sol;
        		else
        			pos++;
        	}
        } else {
            if (after) {
                pos = getStartOfNextLine(refNode.getStartPosition() + refNode.getLength());
            } else {
                pos = getStartOfLine(pos);
            }
        }
        return addNode(node, pos, body, hmap, pName, prefix, postfix);
    }

    private boolean isStartOfLine(int pos) {
        if (pos == 0)
            return true;
        return isEOL(pos - 1);
    }

    private ASTNode addNode(ASTNode node, int insert, String body, HashMap hmap, 
            String pName, String prefix, String postfix) {

        String prevIndent = getPrevIndent(insert);
        body = prepCode(body, null);
        body += NL;

        if (matchPrevChar(insert, '{') > -1) {
            prevIndent += "\t";
        }

        if (prefix.indexOf('{') > -1 && prefix.indexOf('\t') < 0) {
            prefix += "\t";
        }

        prefix = fixTabs(prefix);
        postfix = fixTabs(postfix);
        prevIndent = fixTabs(prevIndent);

        if (prefix != null && !"".equals(prefix)) {
            insert += insertText(prevIndent + prefix, insert);
        }
        insert += insertText(prevIndent, insert);

        int inserted = insertText(body, insert);
        int blen = inserted;
        while (blen > 0 && isWhiteSpace(getChar(insert + blen - 1)))
            blen--;
        while (blen > 0 && isEOL(getChar(insert + blen - 1)))
            blen--;
        while (blen > 0 && isWhiteSpace(getChar(insert + blen - 1)))
            blen--;
        while (blen > 0 && getChar(insert + blen - 1) == ';')
            blen--;
        node.setSourceRange(insert, blen);
        if(blen == 0)
        	System.out.println("Something's wrong - node has 0 length!");

        int lastPos = insert + inserted;
        if (postfix != null && !"".equals(postfix))
            lastPos += insertText(prevIndent + postfix, lastPos);
        removeExcessNewlines(lastPos);

        if (jiglooPlugin.DEBUG)
            System.out.println("ADDED CODE " + getCodeForNode(node) + ", " + (insert + prevIndent.length()) + ", " + blen
                    + ", " + body + ", " + body.length());
        if (hmap != null)
            hmap.put(pName, node);
        lastNodeChanged = node;
        return node;
    }

    private String extractStringLiteral(String str) {
        if (str.indexOf("\"") >= 0) {
            str = str.substring(str.indexOf("\"") + 1);
            str = str.substring(0, str.indexOf("\""));
            //if (!str.startsWith("\\"))
            //str = "\\" + str;
            return str;
        }
        return null;
    }

    private Object handleClassInstanceCreation(ClassInstanceCreation cic, FormComponent comp) {
        return handleClassInstanceCreation(cic, comp, false);
    }

    private Object handleClassInstanceCreation(ClassInstanceCreation cic, FormComponent comp, boolean returnConstructor) {
    	Object[] params = null;
    	Object[] vals = null;
    	Constructor con = null;
        try {
            if (isHidden(cic))
                return null;
            IMethodBinding mb = cic.resolveConstructorBinding();
            String qName = null;
            if (mb != null) {
                qName = getFullClassName(mb.getDeclaringClass());
            }
            if (qName == null || "".equals(qName)) {
                ITypeBinding tb = resolveTypeBinding(cic.getName());
                qName = getFullClassName(tb);
            }
            if (qName == null)
                return null;
            List args = cic.arguments();
            params = getParams(args);
            vals = new Object[2];
            loadClass(qName, vals);
            Class ccls = (Class) vals[0];
            if (ccls == null) {
                if (jiglooPlugin.DEBUG)
                    System.err.println("UNABLE TO LOAD CLASS '" + qName + "' " + cic);
                return null;
            }
            if (ccls.equals(Display.class)) {
                return ccls;
            }
            qName = (String) vals[1];
            if (qName.equals("javax.swing.BoxLayout")) {
                LayoutWrapper lw = new LayoutWrapper(comp, "Box", true);
                int axis = getIntValue((Expression) args.get(1));
                if (jiglooPlugin.getJavaVersion() > 13) {
                    if (axis == BoxLayout.PAGE_AXIS)
                        axis = BoxLayout.Y_AXIS;
                }
                lw.setAttribute("axis", new Integer(axis));
                return lw;
            }

            if (qName.equals("org.jdesktop.layout.GroupLayout") || qName.equals("javax.swing.GroupLayout")
                    || qName.equals("com.cloudgarden.jigloo.groupLayout.GroupLayout")) {
                LayoutWrapper lw = new LayoutWrapper(comp, "Group", true);
                return lw;
            }

            if (ClassUtils.isImage(ccls) && args.size() > 0) { //TODO do this properly!
                Object val = getValue((Expression) args.get(0));
                if (val != null && val instanceof String) {
                    return new ImageWrapper((String) val, editor);
                }
                String str = extractStringLiteral(args.toString());
                if (str != null)
                    return new ImageWrapper(str, editor);
            }

            if (ClassUtils.isIcon(ccls) && args.size() > 0) { //TODO do this properly!
                Object val = getValue((Expression) args.get(0));
                if (val != null && val instanceof String) {
                    return new IconWrapper((String) val, editor);
                }
                String str = extractStringLiteral(args.toString());
                if (str != null)
                    return new IconWrapper(str, editor);
            }

            Class[] types = ConstructorManager.getTypes(params);
            con = null;
            try {
                if (types == null || types.length == 0)
                    con = ccls.getDeclaredConstructor(null);
                else
                    con = ccls.getDeclaredConstructor(types);
            } catch (NoSuchMethodException e) {
                if (types == null && !ccls.isInterface()) {
                    String msg = "No constructor, cic=" + cic + ", " + ccls;
                    jiglooPlugin.writeToLog(msg);
                }
            } catch (Throwable e) {
                String msg = "Error finding constructor, cic=" + cic + ", " + ccls + ", " + e;
                if (types != null) {
                    for (int i = 0; i < types.length; i++) {
                        msg += ", " + types[i];
                    }
                }
                jiglooPlugin.writeToLog(msg);
                //                e.printStackTrace();
            }
            if (con == null) {
                con = ConstructorManager.findConstructor(ccls, types);
                if (ccls.equals(FormAttachment.class)) {
                    //get here if an FormAttachment is being constructed
                    //with parameters which aren't all ints
                    LayoutDataWrapper ldw = new LayoutDataWrapper(comp);
                    ldw.setMainClass(ccls);
                    ldw.setObject(new FormAttachment());
                    String[] props = ConstructorManager.getProperties(ccls, params, types);
                    if (props != null) {
                        for (int i = 0; i < props.length; i++) {
                            String pName = props[i];
                            ldw.setPropertyValue(pName, params[i]);
                        }
                    }
                    return ldw;
                }

            }

            if (con == null) {
                if (ccls != null && !ccls.isInterface()) {
                    jiglooPlugin.writeToLog("Unable to instantiate " + cic);
                    //	        		String msg = "Unable to instantiate " + cic+", "+ccls;
                    //	        		if(types != null) {
                    //	        			for (int i = 0; i < types.length; i++) {
                    //	        				msg+= ", "+types[i];
                    //	        			}
                    //	        		}
                    //	        		jiglooPlugin.writeToLog(msg+", returning Class instead of object");
                }
                return ccls;
            }

            if (ccls.equals(String.class) || ccls.equals(Integer.class) || ccls.equals(Float.class)
                    || ccls.equals(Double.class) || ccls.equals(Character.class) || ccls.equals(Byte.class)
                    || ccls.equals(Long.class) || ccls.equals(Short.class) || ccls.equals(Boolean.class)) {
                Object prim = ClassUtils.newInstance(ccls, con, params);
                //                System.out.println("HANDLE CIC PRIMITIVE TYPE "+cic+", "+prim);
                return prim;
            }

            if (returnConstructor) {
                AnonymousClassDeclaration acd = cic.getAnonymousClassDeclaration();
                String anonCode = null;
                if (acd != null)
                    anonCode = getCodeForNode(acd);
                return new ConstructorHolder(con, params, getCodeForNode(cic), cic, anonCode);
            }

            params = getRawParams(params);

            //            System.err.println("JCP handling "+cic);
            //            for (int i = 0; i < params.length; i++) {
            //                System.err.println("PARAM "+params[i]);                
            //            }
            Object obj = ClassUtils.newInstance(ccls, con, params, true, false);
            //            Object obj = con.newInstance(params);
            //          DefaultValueManager.addClassObject(obj);

            if (jiglooPlugin.canUseSwing() && obj instanceof JFrame) {
                System.err.println("********\n***MADE NEW JFRAME " + obj);
                new Exception().printStackTrace();
            }

            return obj;
        } catch (Throwable e1) {
            addToLog("warning - error making class " + cic + ": " + e1);
            //            if (jiglooPlugin.DEBUG_EXTRA)
            System.err.println("ERROR in handleClassInstanceCreation " + cic + ", " + e1+", "+params+", "+vals+", "+con);
            //            e1.printStackTrace();
            return null;
        }
    }

    private Object[] getRawParams(Object[] params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null)
                continue;
            Object obj = params[i];
            if (obj instanceof FormComponent) {
                FormComponent fc = (FormComponent) params[i];
                if (jiglooPlugin.DEBUG_EXTRA)
                    System.out.println("GOT PARAM " + obj + ", " + fc.getComponent() + ", " + fc.getControl());
                if (fc.getComponent() != null)
                    params[i] = fc.getComponent();
                else if (fc.getControl() != null)
                    params[i] = fc.getControl();
                else if (fc.getNonVisualObject() != null)
                    params[i] = fc.getNonVisualObject();
            } else if (obj instanceof RootMethodCall) {
                params[i] = ((RootMethodCall) obj).invoke();
            }
        }
        return params;
    }

    private void setProperty(Object obj, String pName, Object[] params) {
        if (obj instanceof IPropertySource) {
            ((IPropertySource) obj).setPropertyValue(pName, params[0]);
            if (obj instanceof LayoutWrapper) {
                ((LayoutWrapper) obj).refreshFormComponent();
            }
            return;
        }
        pName = "set" + JiglooUtils.capitalize(pName);
        invokeMethod(obj, pName, params);
    }

    private void invokeMethod(Object obj, String methodName, Object[] params) {
        Class[] pTypes = ConstructorManager.getTypes(params);
        if (jiglooPlugin.canUseSwing() && (obj instanceof JFrame || obj instanceof JDialog)
                && methodName.equals("setVisible"))
            return;
        try {
            if (obj == null) {
                System.out.println("SET PROPERTY, OBJ=NULL, " + methodName);
                return;
            }
            Method meth = Cacher.getMethod(obj.getClass(), methodName, pTypes);
            if (meth != null)
                meth.invoke(obj, params);
            else {
                addToLog("Error: method not found " + methodName);
                System.err.println("Method not found " + methodName + ", " + pTypes);
            }
        } catch (Throwable e) {
            if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("Error finding method " + methodName + ", " + e); //e.printStackTrace();
        }
    }

    private String extractString(String str) {
        if (str.startsWith("\""))
            str = str.substring(1);
        if (str.endsWith("\""))
            str = str.substring(0, str.length() - 1);
        return str;
    }

    private Object convertProp(Object val, Class type) {
        if (val == null)
            return null;
        if (type == null) { //System.err.println("TYPE == NULL for VAL
            // " + val);
            return val;
        }
        if (val.getClass().equals(type))
            return val;

        if (val instanceof FormComponent)
            val = ((FormComponent) val).getObject(false);

        if (type.equals(String.class)) {
            val = extractString(val.toString());
        } else if (type.equals(Integer.class)) {
            try {
                val = new Integer(val.toString());
            } catch (NumberFormatException e) {
                //convert  field constant (eg SWT.BORDER etc)  to number or handle non-numeric expressions
            }
        } else if (type.equals(Boolean.class)) {
            val = new Boolean(val.toString());
        } else if (type.equals(Float.class)) {
            val = new Float(val.toString());
        } else if (type.equals(Point.class)) {
            int[] args = getIntsFromString(val.toString());
            if (args == null)
                return null;
            val = new Point(args[0], args[1]);
        } else if (type.equals(Rectangle.class)) {
            int[] args = getIntsFromString(val.toString());
            if (args == null)
                return null;
            val = new Rectangle(args[0], args[1], args[2], args[3]);
        } else if (jiglooPlugin.canUseSwing()) {
            if (type.equals(Dimension.class)) {
                int[] args = getIntsFromString(val.toString());
                if (args == null)
                    return null;
                val = new Dimension(args[0], args[1]);
            } else if (type.equals(java.awt.Point.class)) {
                int[] args = getIntsFromString(val.toString());
                if (args == null)
                    return null;
                val = new java.awt.Point(args[0], args[1]);
            } else if (type.equals(java.awt.Rectangle.class)) {
                int[] args = getIntsFromString(val.toString());
                if (args == null)
                    return null;
                val = new java.awt.Rectangle(args[0], args[1], args[2], args[3]);
            }
        }
        return val;
    }

    private static int[] getIntsFromString(String val) {
        try {
            int pos = val.indexOf("(");
            if (pos >= 0)
                val = val.substring(pos + 1);
            pos = val.indexOf(")");
            if (pos >= 0)
                val = val.substring(0, pos);
            val = JiglooUtils.replace(val, " ", "");
            String[] args = JiglooUtils.split(",", val);
            int[] vals = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                vals[i] = Integer.parseInt(args[i]);
            }
            return vals;
        } catch (Throwable e) {
            return null; //return new int[] { 20, 20, 20, 20 };
        }
    }

    private String laf = null;

    private ASTNode lafNode = null;

    private ASTNode regResUserNode = null;

    public String getLookAndFeel() {
        return laf;
    }

    public void updateLookAndFeel(String laf) {
        if (parsing || addActionDialogOpen)
            return;
        if (!jiglooPlugin.generateLookAndFeelCode())
            return;

        this.laf = laf;

        if (laf != null && laf.startsWith("Reset")) {
            laf = jiglooPlugin.getDefaultLAFClassName();
        }

        if (laf != null && laf.startsWith("System")) {
            laf = "system";
        }

		if(laf.startsWith("com.jgoodies.looks")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"looks-2.1.4.jar"}, laf);
		}
		
        if (editor.isPartOfAppFrameworkApplication()) {
            editor.getBundleManager().setProperty("Application.lookAndFeel", laf);
            return;
        }

        String code = "";
        if (laf == null) { //code = "";
            code = "javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());";
        } else if(laf.equals("system")){
            code = "javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());";
        } else {
            code = "javax.swing.UIManager.setLookAndFeel(\"" + laf + "\");";
        } //System.out.println("******* SET L&F "+laf);
        if (lafNode == null) {
            ASTNode ref = (ASTNode) mainType;
            int pos = ref.getStartPosition();
            pos = findNextChar(pos, "{", true);
            pos = getStartOfNextLine(pos);
            pos += insertText(NL + "\t{" + NL + "\t\t//Set Look & Feel" + NL + "\t\ttry {" + NL, pos);
            lafNode = ast.newMethodInvocation();
            int start = pos + 3;
            pos += insertText("\t\t\t" + code + NL, pos);
            int len = code.length();
            lafNode.setSourceRange(start, len);
            insertText("\t\t} catch(Exception e) {" + NL + "\t\t\te.printStackTrace();" + NL + "\t\t}" + NL + "\t}"
                    + NL + NL, pos);
        } else {
            updateNodeBody(lafNode, code);
        }
    }

    public void removeConstructor(String[] paramTypes) {
    }

    public void addField(String name, String type, boolean isStatic, FormComponent fc) {
        if (fieldDecs.containsKey(name))
            return;
        ASTNode parNode = null;
        boolean asChild = false;
        Iterator it = fieldDecs.keySet().iterator();
        if (!it.hasNext()) {
        	parNode = mainType;
            asChild = true;
        } else {
            parNode = (ASTNode) fieldDecs.get(it.next());
        }
        VariableDeclarationFragment newVdf = ast.newVariableDeclarationFragment();
        FieldDeclaration newFd = ast.newFieldDeclaration(newVdf);
        if (fc != null) {
            addNode(newFd, parNode, fc.getJavaDeclaration(isStatic) + NL, fieldDecs, name, asChild, true, "", "");
        } else {
            if (isStatic) {
                addNode(newFd, parNode, "private static " + type + " " + name + ";" + NL, 
                		fieldDecs, name, asChild,  true, "", "");
            } else {
                addNode(newFd, parNode, "private " + type + " " + name + ";" + NL, 
                		fieldDecs, name, asChild, true, "",  "");
            }
        }
    }

    public void removeImport(String name) {
        //if(true) return;
        if (name == null)
            return;
        try {
            Iterator it = imports.keySet().iterator();
            ASTNode imp = null;
            while (it.hasNext()) {
                String key = (String) it.next();
                if (name.equals(key)) {
                    imp = (ASTNode) imports.get(key);
                    imports.remove(key);
                    break;
                }
            }
            if (imp != null && !isProtected(imp))
                removeNode(imp);
        } catch (Throwable e) {
            jiglooPlugin.handleError(e, "Error removing import " + name);
        }
    }

    public void addImport(String name) {
    	if(name.indexOf(".") < 0)
    		return;
    	
        if (name.equals("layout.TableLayout"))
            name = "info.clearthought.layout.TableLayout";

        if (name == null || name.startsWith("java.lang."))
            return;
        try {
            String pn = getPackageName();

            if (pn != null && pn.equals(JiglooUtils.getQualifier(name)))
                return;

            if (name.equals("com.cloudgarden.layout.AnchorLayout")) {
                editor.insertClass(packageName, "com/cloudgarden/layout/AnchorConstraint");
                editor.insertClass(packageName, "com/cloudgarden/layout/AnchorLayout");
            }
            if (name.equals("com.cloudgarden.resource.ArrayFocusTraversalPolicy")) {
                editor.insertClass(packageName, "com/cloudgarden/resource/ArrayFocusTraversalPolicy");
            }
            if (name.equals("com.cloudgarden.resource.SWTResourceManager")) {
                editor.insertClass(packageName, "com/cloudgarden/resource/SWTResourceManager");
                if (regResUserNode == null) {
                    int pos = -1;
                    ASTNode ref = null;
                    String resUser = "this";
                    if (superClass == null || superClass.toString().equals(Dialog.class.getName())) {
                        FormComponent rfc = getRootComponent();
                        ref = (ASTNode) findObject(elementAssignments, rfc.getName());
                        if (ref != null) {
                            pos = ref.getStartPosition() + ref.getLength();
                            resUser = rfc.getNameInCode();
                        }
                    } else if (editor.isInSwingMode()) {
                        FormComponent rfc = getSwtInSwingComponent();
                        if (rfc != null) {
                            ref = (ASTNode) findObject(elementAssignments, rfc.getName());
                            if (ref != null) {
                                pos = ref.getStartPosition() + ref.getLength();
                                resUser = rfc.getNameInCode();
                            }
                        }
                    } else if (superClass.toString().equals("org.eclipse.ui.forms.editor.FormPage")) {
                        resUser = "getPartControl()";
                    }

                    if (ref == null) {
                        ref = (ASTNode) mainType;
                        pos = ref.getStartPosition();
                        pos = findNextChar(pos, "{", true);
                    }
                    pos = getStartOfNextLine(pos);
                    int codeStart = pos;
                    pos += insertText(NL + "{" + NL + "//Register as a resource user - SWTResourceManager will" + NL
                            + "//handle the obtaining and disposing of resources" + NL, pos);
                    regResUserNode = ast.newMethodDeclaration();
                    int start = pos + 3;
                    String code = "SWTResourceManager.registerResourceUser(" + resUser + ");";
                    pos += insertText(code + NL, pos);
                    int len = code.length();
                    regResUserNode.setSourceRange(start, len);
                    pos += insertText("}" + NL + NL, pos);
                    indentCode(codeStart, pos);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        ImportDeclaration idec = null;
        Iterator it = imports.keySet().iterator();
        boolean after = false;
        String pkgName = JiglooUtils.getQualifier(name);
        while (it.hasNext()) {
            String key = (String) it.next();
            if (name.equals(key) || key.equals(pkgName))
                return;
            ImportDeclaration in = (ImportDeclaration) imports.get(key);
            if ((idec == null || idec.getName().toString().compareTo(key) <= 0) && key.compareTo(name) <= 0) {
                idec = in;
                after = true;
            }
        }
        
        ImportDeclaration newIdec = null;
        if (idec == null) {
            if (mainType != null) {
                newIdec = ast.newImportDeclaration();
                if (pkgNode != null) {
                    addNode(newIdec, pkgNode, "import " + name + ";", imports, name, false, true, "", "");
                } else {
                    addNode(newIdec, 0, "import " + name + ";", imports, name, "", "");
                }
            }
        } else {
            newIdec = ast.newImportDeclaration();
            addNode(newIdec, idec, "import " + name + ";", imports, name, false, after, "", "");
        }
        try {
            String[] parts = JiglooUtils.split(".", name);
            newIdec.setName(ast.newName(parts));
        } catch (Throwable e) {
            e.printStackTrace();
        }
		if(name.equals("org.jdesktop.layout.GroupLayout")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"swing-layout-1.0.jar"}, "org.jdesktop.layout.GroupLayout");
		}
		if(name.equals("com.jgoodies.forms.layout.FormLayout")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"forms-1.1.0.jar"}, "com.jgoodies.forms.layout.FormLayout");
		}

		if(name.equals("info.clearthought.layout.TableLayout")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"TableLayout.jar"}, "info.clearthought.layout.TableLayout");
		}

		if(name.equals("net.miginfocom.swing.MigLayout")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"miglayout-3.5.5.jar"}, "net.miginfocom.swing.MigLayout");
		}

		if(name.equals("net.miginfocom.swt.MigLayout")) {
			FileUtils.addJarsToClassPath(editor.getSite().getShell(), editor.getJavaProject(), 
					new String[] {"miglayout-3.5.5.jar"}, "net.miginfocom.swt.MigLayout");
		}

        //System.err.println("ADD IMPORT "+name);
        //new Exception().printStackTrace();
    }

    /**
     * @return
     */
    private FormComponent getSwtInSwingComponent() {
        return getRootComponent().getSwtInSwingChild();
    }

    private ASTNode getFirstAssignmentInNode(ASTNode node) {
        Iterator it = elementAssignments.keySet().iterator();
        int start = node.getStartPosition();
        int end = start + node.getLength();
        int firstPos = end;
        ASTNode firstNode = null;
        while (it.hasNext()) {
            ASTNode n = getAssignmentNode((String) it.next());
            if (n.getStartPosition() < firstPos) {
                firstPos = n.getStartPosition();
                firstNode = n;
            }
        }
        return firstNode;
    }

    private ASTNode getLastMethodNode() {
        Iterator it = methodMap.keySet().iterator();
        int lastPos = -1;
        ASTNode lastNode = null;
        while (it.hasNext()) {
            ASTNode node = (ASTNode) methodMap.get(it.next());
            if (node.getStartPosition() > lastPos) {
                if (!isProtected(node)) {
                    lastPos = node.getStartPosition();
                    lastNode = node;
                }
            }
        }
        return lastNode;
    }

    private ASTNode getFirstMethodNode() {
        Iterator it = methodMap.keySet().iterator();
        int lastPos = 100000000;
        ASTNode lastNode = null;
        while (it.hasNext()) {
            ASTNode node = (ASTNode) methodMap.get(it.next());
            if (node.getStartPosition() < lastPos) {
                lastPos = node.getStartPosition();
                lastNode = node;
            }
        }
        return lastNode;
    }

    private String fixTabs(String code) {
        if (JiglooUtils.spacesForTabs()) {
            code = JiglooUtils.replace(code, "\t", JiglooUtils.getTabString());
        }
        return code;
    }

    private String fixNewLines(String code) {
        if (NL.length() == 1)
            return code;
        int pos = 0;
        while (pos < code.length()) {
            pos = code.indexOf("\r", pos);
            if (pos < 0)
                break;
            pos++;
            if (code.charAt(pos) != '\n') {
                code = code.substring(0, pos) + "\n" + code.substring(pos);
                pos++;
            }
        }
        pos = 0;
        while (pos < code.length()) {
            pos = code.indexOf("\n", pos);
            if (pos < 0)
                break;
            pos--;
            if (pos < 0) {
                pos++;
                code = "\r" + code;
            } else if (code.charAt(pos) != '\r') {
                pos++;
                code = code.substring(0, pos) + "\r" + code.substring(pos);
            }
            pos += 2;
        } //System.out.println("FIX NEW LINES (2) " + code.length() + ", " +
        // showControls(code));
        return code;
    }

    public MethodDeclaration addMethod(String name, String body, String returnType, String[] paramTypes,
            String[] paramNames, int flags, String comment) {
        return addMethod(name, body, returnType, paramTypes, paramNames, flags, comment, null, true);
    }

    public MethodDeclaration getMethodNode(String methodName) {
    	
    	if(methodMap.containsKey(methodName))
    		return (MethodDeclaration)methodMap.get(methodName);
    	
        methodName += "_";
        Iterator it = methodMap.keySet().iterator();
        while (it.hasNext()) {
            String mn = (String) it.next();
            if (mn.startsWith(methodName))
                return (MethodDeclaration) methodMap.get(mn);
        }
        return null;
    }

    public MethodDeclaration addMethod(String name, String body, String returnType, String[] paramTypes,
            String[] paramNames, int flags, String comment, CodeBlock codeBlock, boolean indent) {
        String key = name + "_" + getParamString(paramTypes);
        if (codeBlock == null && methodMap.containsKey(key))
            return null;

        String oldCode = null;
        if (codeBlock != null)
            oldCode = codeBlock.getCode();
        //methodCode.remove(key);
        boolean asChild = false;
        //        boolean indent = true;
        MethodDeclaration md = null;
        ASTNode refNode = getLastMethodNode();
        //ASTNode refNode = getFirstMethodNode();
        if (refNode == null) {
            if (mainType.bodyDeclarations().size() > 0)
                refNode = (ASTNode) mainType.bodyDeclarations().get(0);
            else
                refNode = mainType;
            asChild = true;
        }
        md = ast.newMethodDeclaration();
        md.setName(ast.newSimpleName(name));
        Block mb = ast.newBlock();
        md.setBody(mb);
        String code = null;
        if (oldCode == null) {
            body = fixNewLines(body);
            comment = fixNewLines(comment);
            if (comment != null && !comment.equals("")) {
                if (comment.startsWith("@")) {
                    comment = NL + "\t" + comment + NL;
                } else if (comment.indexOf("/*") < 0) {
                    comment = NL + "    /**" + comment + NL + "     */" + NL;
                } else {
                    comment = NL + comment;
                }
            } else {
                comment = NL;
            }
            comment = fixTabs(comment);
            String params = "";
            if (paramNames != null) {
                for (int i = 0; i < paramNames.length; i++) {
                    params += paramTypes[i] + " " + paramNames[i];
                    if (i != paramNames.length - 1)
                        params += ", ";
                }
            }
            if (flags == Flags.AccPrivate)
                code = "\tprivate ";
            else
                code = "\tpublic ";
            code += returnType + " " + name + "(" + params + ") {" + NL + body + "\t}" + NL + NL;
            code = fixTabs(code);
        } else {
            //don't indent because that would upset incorporateCodeBlock.
            //indent later
            indent = false;
            code = oldCode;
        }

        indent = false;
        addNode(md, refNode, comment+code, methodMap, key, asChild, true, "", "");

        int st = md.getStartPosition();
        code = getCodeForNode(md);
        int pos = code.indexOf("{");
        st += pos;
        int pos2 = code.lastIndexOf("}");
        mb.setSourceRange(st, pos2 - pos);
        if (indent)
            indentCode(md.getStartPosition(), md.getStartPosition() + md.getLength());
        return md;
    }

    public boolean renameField(Shell shell, String oldName, String newName) {
        return false;
    }

    public boolean hasImportLike(String packageName) {
        packageName += ".";
        Iterator it = imports.keySet().iterator();
        while(it.hasNext()) {
            if(((String)it.next()).startsWith(packageName))
                return true;
        }
        return false;
    }
    
    public boolean hasImport(String importName) {
    	return imports.containsKey(importName);
    }
    
    public boolean hasMethod(String methodKey, String[] params) {
        return methodMap.containsKey(methodKey);
    }

    public String getPackageName() {
        Class mc = getMainClass();
        if (mc != null)
            return JiglooUtils.getQualifier(mc.getName());
        return null;
    }

    public void renameMethod(String oldHandler, String eventHandler, String[] paramTypes) {
    }

    public void setParsing(boolean parsing) {
        this.parsing = parsing;
    }

    public boolean isParsing() {
        return parsing;
    }

    public void clear() {
        fields.clear();
        basicFieldNames.clear();
    }

    public boolean isAddActionDialogOpen() {
        return addActionDialogOpen;
    }

    public void setAddActionDialogOpen(boolean addActionDialogOpen) {
        this.addActionDialogOpen = addActionDialogOpen;
    }

    /**
     * @return
     */
    public String getNL() {
        return NL;
    }

    /**
     * @return
     */
    public FormEditor getEditor() {
        return editor;
    }

    public void insertMethodAtEndOfInitGUI(String code, IFormPropertySource element, String methodName) {
    	code += getNL();
        int pos = getStartOfInitGUI();
        pos = getEndOfBlock(pos);
        pos = getStartOfLine(pos);
        code = fixNewLines(code);
        int len = insertText(code, pos);
        ASTNode mic = initGUIMethod.getAST().newMethodInvocation();
        getPropMap(element.getName()).put(METHOD_PREFIX+methodName, mic);
        mic.setSourceRange(pos, len);
        indentCode(pos, pos+len);
    }

    public void insertAtEndOfInitGUI(String code) {
    	code += getNL();
        int pos = getStartOfInitGUI();
        pos = getEndOfBlock(pos);
        pos = getStartOfLine(pos);
        code = fixNewLines(code);
        int len = insertText(code, pos);
        indentCode(pos, pos+len);
    }

    public ICompilationUnit getCompUnit() {
        return compUnit;
    }

	public boolean hasMethodCall(IFormPropertySource comp, String methodName) {
    	return getPropSetter(comp, METHOD_PREFIX+methodName) != null;
	}
}