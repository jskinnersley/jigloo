/*
 * Created on Jun 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.BufferChangedEvent;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IBufferChangedListener;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.internal.ui.javaeditor.JavaOutlinePage;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.help.WorkbenchHelp;
import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.internal.e4.compatibility.ActionBars;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.PageSite;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.actions.FormAction;
import com.cloudgarden.jigloo.actions.FormAddAction;
import com.cloudgarden.jigloo.actions.FormAlignAction;
import com.cloudgarden.jigloo.actions.FormLayoutAction;
import com.cloudgarden.jigloo.actions.FormLayoutDataAction;
import com.cloudgarden.jigloo.actions.FormLookAndFeelAction;
import com.cloudgarden.jigloo.actions.FormPasteAction;
import com.cloudgarden.jigloo.actions.FormSurroundAction;
import com.cloudgarden.jigloo.actions.OpenJiglooPreferencePageAction;
import com.cloudgarden.jigloo.actions.UndoableAction;
import com.cloudgarden.jigloo.actions.UndoableEditAction;
import com.cloudgarden.jigloo.actions.UndoableGroupAction;
import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.appFramework.BundleManager;
import com.cloudgarden.jigloo.appFramework.CombinedUndoManager;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.classloader.ChainedClassLoader;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
import com.cloudgarden.jigloo.classloader.FormClassLoader;
import com.cloudgarden.jigloo.classloader.NoClass;
import com.cloudgarden.jigloo.classloader.ReloadClass;
import com.cloudgarden.jigloo.controls.AWTControl;
import com.cloudgarden.jigloo.controls.CustomSashForm;
import com.cloudgarden.jigloo.controls.LinuxDecorations;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.dialog.AnchorDialog;
import com.cloudgarden.jigloo.dialog.ViewLogDialog;
import com.cloudgarden.jigloo.draw.DrawUtils;
import com.cloudgarden.jigloo.eval.ConstructorManager;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.frames.AlignToolbar;
import com.cloudgarden.jigloo.frames.GridEdgeManager;
import com.cloudgarden.jigloo.frames.Marker;
import com.cloudgarden.jigloo.frames.MarqueeFrame;
import com.cloudgarden.jigloo.frames.TextWindow;
import com.cloudgarden.jigloo.frames.WindowFrame;
import com.cloudgarden.jigloo.glasspane.Drawer;
import com.cloudgarden.jigloo.glasspane.SWTGlassPane;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.groupLayoutSupport.SnapTo;
import com.cloudgarden.jigloo.harness.IHarness;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.interfaces.IDummyShellSupplier;
import com.cloudgarden.jigloo.interfaces.IWidgetManager;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.menu.MenuWindow;
import com.cloudgarden.jigloo.outline.FormContentOutlinePage;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.outline.TreeParent;
import com.cloudgarden.jigloo.palette.ComponentPalette;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.properties.AbsoluteLayout;
import com.cloudgarden.jigloo.properties.descriptors.EventPropertyDescriptor;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.CursorManager;
import com.cloudgarden.jigloo.resource.FontManager;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.ConversionUtils;
import com.cloudgarden.jigloo.util.DelayableRunnable;
import com.cloudgarden.jigloo.util.ItemManager;
import com.cloudgarden.jigloo.util.JavadocDisplayer;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.util.SWTStyleManager;
import com.cloudgarden.jigloo.util.SWTUtils;
import com.cloudgarden.jigloo.util.SwingHelper;
import com.cloudgarden.jigloo.views.FormView;
import com.cloudgarden.jigloo.wizards.ExtractWizard;
import com.cloudgarden.jigloo.wrappers.EventWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LookAndFeelWrapper;
import com.cloudgarden.jigloo.xml.XMLParser;
import com.cloudgarden.jigloo.xml.XMLWriter;
import com.jgoodies.forms.builder.AbstractFormBuilder;

import net.miginfocom.swt.MigLayout;

/**
 * @author jsk
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormEditor extends JavaEditor implements ISelectionProvider, ISelectionListener, ISelectionChangedListener {

    private boolean checkParsingWhenMouseDown = true;

    private GridEdgeManager gridEdgeManager;

    public static final String MENU_COMPONENT_LABEL = "Menu components";

    public static final String NON_VISUAL_LABEL = "Non-visual components";

    public static final String EXTRA_COMPONENT_LABEL = "Extra components";

    private boolean updatingLastFC = true;

    private Composite internalShell = null;
    private Composite eventCatcher = null;
    private Composite rootDecoration = null;
    private boolean USE_INTERNAL_SHELL = true;
    private boolean activated = false;

    private boolean pauseUpdate = false;
    private SnapTo snapTo = null;
    private SnapTo containerSnapTo = null;

    private Vector listeners = new Vector();

    private StructuredSelection structSel;

    private Label licenseLabel = null;
    private Label viewLogButton;
    private ViewLogDialog viewLogDialog;

    private boolean useJavaCodeParser = true;

    private boolean useXMLParser = false;

    private final boolean updateJavaCode = true;

    private Boolean useJava6GroupLayout = null;

    private final boolean isJavaEditor = true;

    private boolean toggling = false;

    private boolean disposed = true;

    private boolean rebuilding = false;

    private boolean populating = false;

    private boolean previewing = false;

    private Cursor cursor, CURSOR_NO, CURSOR_ARROW, CURSOR_DEF, CURSOR_MOVE, CURSOR_X;

    private JavaCodeParser jcp = null;

    private IBufferChangedListener bufferListener = null;

    private boolean commit;

    private String rootName = "this";

    private CTabFolder formCodeTabFolder;

    private CTabItem formTabItem;

    private CTabItem codeTabItem;

    private CTabItem propTabItem;
    private PropertyFileEditor propertyFileEditor;

    private int WIGGLE_ROOM = 4;

    public static final int EDITOR_MODE_TABBED = 1;
    public static final int EDITOR_MODE_SPLIT_VERT = 2;
    public static final int EDITOR_MODE_SPLIT_HORIZ = 3;

    private int EDITOR_MODE = EDITOR_MODE_SPLIT_VERT;

    public static int MODE_SWT = 1;
    public static int MODE_AWT_SWING = 2;
    public static int MODE_CWT = 3;
    public static int MODE_GWT = 4;
    public static int MODE_ANDROID = 5;

    private int mode = 2;

    public int BORDER_X = 25;
    public int BORDER_Y = 25;

    private Menu swtMenu, swingMenu;

    private MenuManager swtMenuMgr, swingMenuMgr;

    private IFile javaFile;

    private IFile formFile;

    private BundleManager bundleManager;

    private IPropertyListener propChangeList = null;

    private static Vector formEditors = new Vector();

    private ICompilationUnit wcopy;

    private CompilationUnitEditor javaEditor;

    private ScrolledComposite scrollPane;

    private Composite mainControl, frameComp, menuComp;
    private Composite mainComp;

    private AWTControl awtControl;

    private Container swingMainPanel;

    private HashMap actions = new HashMap();
    private HashMap surroundActions = new HashMap();

    private FormAction setSwingTabTitle;

    private Action saveAction, extractAction, externalizeAllStringsAction;

    private Action copyAction, cutAction, deleteAction, pasteAction, undoAction, redoAction, selectAllAction;

    private HashMap codeEditorActions;
    private HashMap formEditorActions;

    private Action moveUpAction, moveDownAction;

    private Action renameAction, showCodeAction, setClassAction, runAction, updateCodeStyleAction, convertToSWTResAction, insertShowGUIAction, openClassAction, reloadEditorAction,
	    insertGetGUIBInstSWTAction, insertGetGUIBInstSwingAction, toggleLocalFieldDecAction, insertGetterAction;

    private Action javadocClassAction, javadocLayoutAction, anchorDialogAction;

    private Action grabHorizAction, grabVertAction;

    private Vector components;

    private Composite internalOutlineContainer = null;
    private JavaOutlinePage internalJavaOutline = null;
    private FormContentOutlinePage internalFormOutline = null;

    private FormView internalFormView = null;

    private SashForm formEditorSash;
    private SashForm formEditorSash2;

    private static int[] internalSashWeights = new int[] { 70, 30 };

    private static final int NUM_ALIGN_BUTTONS = 11;
    private LightButton[] alignButtons = new LightButton[NUM_ALIGN_BUTTONS];

    private boolean inited = false;
    private String laf;
    private Composite primaryComposite;
    private FormComponent menuBar;
    private FormComponent nonVisualRoot;
    private FormComponent extraCompRoot;
    private XMLParser parser;
    private FormContentOutlinePage formOutlinePage;
    private FormEditorKeyAdapter formEditorKeyAdapter;

    private FormComponent root;
    private JFrame customizerFrame = null;

    private Vector selectedComps = new Vector();

    private static Vector clipboardComps = new Vector();

    private Vector windowFramePool = new Vector();
    private static Vector insertedClasses = new Vector();

    private HashMap windowFrames = new HashMap();

    private IPreferenceStore pstore;

    private FormComponent selectedComp = null;

    private FormView view;

    private boolean mouseMoved = false;

    private boolean editorMaximized = false;

    private FileEditorInput javaFileEditorInput;

    private boolean isNetbeans = false;

    private boolean textEditorActive = false;
    private boolean propertyEditorActive = false;
    private boolean formEditorActive = true;

    private Vector removedFields = new Vector();

    private ComponentPalette palette;

    private boolean initialJavaUpdate = false;

    private boolean txtWindowShowing = false;

    private IPartListener partListener;

    private HashMap resourceMap = new HashMap();

    private IPropertyChangeListener prefPropChangeListener;

    private IJavaProject jProj = null;

    private CLabel parsingLabel;

    private Marker marker;

    private Boolean usesAppFramework = null;

    private FormClassLoader cpLoader = null;
    // private ClassLoader origCL;

    private HashMap classCache = new HashMap();

    private static int gridSize = 0;

    private SWTGlassPane swtGlassPane;

    private CombinedUndoManager combinedUndoManager;
    private Runnable postOpenRunnable;

    private IHarness harness = null;

    public String toString() {
	return super.toString() + ": " + javaFile;
    }

    class LightButton extends CLabel {

	private Color bg = null;
	private String imgName;
	private boolean enabled = true;

	public LightButton(Composite parent, String imgName, String ttip) {
	    super(parent, SWT.NULL);
	    this.imgName = imgName;
	    setToolTipText(ttip);
	    setAlignment(SWT.CENTER);
	    setImage(ImageManager.getImage(imgName));
	    GridData gd = new GridData();
	    gd.heightHint = 18;
	    gd.widthHint = 24;
	    setLayoutData(gd);
	    bg = getBackground();
	    addMouseListener(new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
		    if (adapter != null && enabled)
			adapter.widgetSelected(null);
		}
	    });
	    addMouseTrackListener(new MouseTrackAdapter() {
		public void mouseEnter(MouseEvent e) {
		    if (jiglooPlugin.isMacOS())
			ToolTipManager.showToolTip(getToolTipText(), LightButton.this);
		    if (!enabled)
			return;
		    setBackground(ColorManager.getColor(230, 230, 250));
		    redraw();
		    update();
		}

		public void mouseExit(MouseEvent e) {
		    if (jiglooPlugin.isMacOS())
			ToolTipManager.hideToolTip();
		    if (!enabled)
			return;
		    setBackground(bg);
		    redraw();
		    update();
		}
	    });
	}

	SelectionAdapter adapter;

	public void addSelectionListener(SelectionAdapter adapter) {
	    this.adapter = adapter;
	}

	public void setEnabled(boolean enabled) {
	    if (isDisposed())
		return;
	    this.enabled = enabled;
	    if (enabled)
		setImage(ImageManager.getImage(imgName));
	    else {
		setBackground(bg);
		setImage(ImageManager.getGrayScaleImage(imgName));
	    }
	}

    }

    public StyledText getJavaEditorControl() {
	return (StyledText) ((JavaEditor) javaEditor).getEditorControl();
    }

    public void doAction(String actionID) {
	try {
	    if (isDisposed())
		return;
	    createActions();

	    if (propertyEditorActive) {

		getPropertyEditorAction(actionID).run();

	    } else if (actionID.equals(IWorkbenchActionConstants.REDO) || actionID.equals(IWorkbenchActionConstants.UNDO)) {

		boolean needsReload = false;

		if (actionID.equals(IWorkbenchActionConstants.REDO)) {
		    if (combinedUndoManager.redo())
			needsReload = true;
		} else {
		    if (combinedUndoManager.undo())
			needsReload = true;
		}

		if (needsReload) {
		    javaCodeSync.setTemporaryDelay(300);
		    javaCodeSync.extend();
		}
		javaFileChanged = false;
		propertyFileChanged = false;

	    } else if (textEditorActive) {

		getCodeEditorAction(actionID).run();

	    } else {
		if (actionID.equals(IWorkbenchActionConstants.PASTE)) {

		    currentAction = new FormPasteAction(this);

		} else if (actionID.equals(IWorkbenchActionConstants.COPY)) {
		    if (selectedComps.size() == 0)
			return;
		    setWaitCursor();
		    clipboardComps.removeAllElements();
		    copySelectionToClipboard(false);
		    setCursor(getDefCursor());

		} else if (actionID.equals(IWorkbenchActionConstants.DELETE)) {
		    setWaitCursor();
		    doDelete(true);
		    setCursor(getDefCursor());

		} else if (actionID.equals(IWorkbenchActionConstants.CUT)) {

		    if (selectedComps.size() == 0)
			return;
		    setWaitCursor();
		    inCutMode = true;
		    clipboardComps.removeAllElements();
		    copySelectionToClipboard(true);

		    doDelete(false);
		    setCursor(getDefCursor());

		} else if (actionID.equals(IWorkbenchActionConstants.SELECT_ALL)) {
		    Vector cc = new Vector();
		    for (int i = 0; i < root.getNonInheritedChildCount(); i++) {
			FormComponent fc = root.getNonInheritedChildAt(i);
			if (fc.isVisual() && !fc.isInherited() && !fc.isMenuComponent() && !cc.contains(fc)) {
			    cc.add(fc);
			}
		    }
		    setSelectedComponents(cc);
		}
	    }

	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    private boolean propertyActionsInited = false;
    private boolean textActionsInited = false;
    private boolean guiActionsInited = false;

    public void setAction(String actionID, IAction action) {
	super.setAction(actionID, action);
	if (codeEditorActions == null)
	    codeEditorActions = new HashMap();
	codeEditorActions.put(actionID, action);
    }

    public IAction getCodeEditorAction(String actionID) {
	return (IAction) codeEditorActions.get(actionID);
    }

    private IAction getPropertyEditorAction(String actionID) {
	return (IAction) propertyFileEditor.getPropertyEditorAction(actionID);
    }

    public IAction getFormEditorAction(String actionID) {
	if (formEditorActions == null)
	    return null;
	return (IAction) formEditorActions.get(actionID);
    }

    private void unregisterCodeActions(IKeyBindingService kbs) {
	if (codeEditorActions != null) {
	    Iterator it = codeEditorActions.keySet().iterator();
	    while (it.hasNext()) {
		String id = (String) it.next();
		// if(isProtectedAction(id)) {
		// System.out.println("unregister code action "+id);
		kbs.unregisterAction((IAction) codeEditorActions.get(id));
		// }
	    }
	}
    }

    private void registerCodeActions(IKeyBindingService kbs) {
	if (codeEditorActions != null) {
	    Iterator it = codeEditorActions.keySet().iterator();
	    while (it.hasNext()) {
		String id = (String) it.next();
		if (!isProtectedAction(id)) {
		    IAction action = (IAction) codeEditorActions.get(id);
		    // if(id.indexOf("ToggleComment") == 0) {
		    // System.out.println("toggle comment");
		    // }
		    kbs.registerAction(action);
		}
	    }
	}
    }

    public boolean isProtectedAction(String id) {
	// id could be copy or org.eclipse.ui.edit.copy - both should return
	// true.
	if (id.startsWith("org.eclipse.ui.edit."))
	    id = id.substring(id.lastIndexOf(".") + 1);
	id = id.toLowerCase();
	return getFormEditorAction(id) != null;
    }

    private void registerFormActions(IKeyBindingService kbs) {
	if (formEditorActions != null) {
	    Iterator it = formEditorActions.keySet().iterator();
	    while (it.hasNext()) {
		String id = (String) it.next();
		((IAction) formEditorActions.get(id)).setEnabled(true);
		kbs.registerAction((IAction) formEditorActions.get(id));
	    }
	}
    }

    private void unregisterFormActions(IKeyBindingService kbs) {
	if (formEditorActions != null) {
	    Iterator it = formEditorActions.keySet().iterator();
	    while (it.hasNext()) {
		String id = (String) it.next();
		kbs.unregisterAction((IAction) formEditorActions.get(id));
	    }
	}
    }

    private static final String[] javaScope = new String[] { "org.eclipse.jdt.ui.javaEditorScope" };
    private static final String[] textScope = new String[] { "org.eclipse.ui.textEditorScope" };

    private void registerActions(String editorType) {

	try {
	    createActions();

	    // if(!guiActionsInited)
	    registerFormActions(getSite().getKeyBindingService());
	    guiActionsInited = true;

	    if ("Java".equals(editorType)) {

		if (textActionsInited && textEditorActive)
		    return;
		getJavaEditorControl().setEditable(true);
		textEditorActive = true;

		formEditorActive = false;
		propertyEditorActive = false;

		if (copyAction != null) {
		    textActionsInited = textEditorActive;
		    propertyActionsInited = propertyEditorActive;

		    if (codeEditorActions != null) {
			unregisterActions(false);
			IKeyBindingService kbs = getSite().getKeyBindingService();
			registerCodeActions(kbs);
			getSite().getKeyBindingService().setScopes(javaScope);
		    }
		}

	    } else if ("Property".equals(editorType)) {

		getJavaEditorControl().setEditable(false);
		if (propertyActionsInited && propertyEditorActive)
		    return;
		propertyEditorActive = true;
		textEditorActive = false;

		formEditorActive = false;

		if (copyAction != null) {
		    textActionsInited = textEditorActive;
		    propertyActionsInited = propertyEditorActive;

		    if (propertyFileEditor != null) {
			unregisterActions(false);
			IKeyBindingService kbs = getSite().getKeyBindingService();
			propertyFileEditor.registerActions(kbs);
			getSite().getKeyBindingService().setScopes(textScope);
		    }
		}

	    } else if ("Form".equals(editorType)) {
		getJavaEditorControl().setEditable(false);
		if (formEditorActive)
		    return;
		formEditorActive = true;
		textEditorActive = false;

		propertyEditorActive = false;

		if (copyAction != null) {
		    textActionsInited = textEditorActive;
		    propertyActionsInited = propertyEditorActive;
		    getSite().getKeyBindingService().setScopes(javaScope);

		}
	    }
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    private void unregisterActions(boolean full) {
	try {
	    IKeyBindingService kbs = getSite().getKeyBindingService();

	    if (codeEditorActions != null) {
		unregisterCodeActions(kbs);
	    }

	    if (propertyFileEditor != null)
		propertyFileEditor.unregisterActions(kbs);

	    if (full)
		unregisterFormActions(kbs);

	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    private String getEditorType(Object eventSource) {
	String editorType = "Property";
	if (eventSource.equals(getJavaEditorControl()))
	    editorType = "Java";
	else if (eventSource.equals(eventCatcher) || eventSource.equals(mainComp))
	    editorType = "Form";
	return editorType;
    }

    private MouseListener mouseFocusListener = new MouseAdapter() {
	public void mouseDown(MouseEvent e) {
	    registerActions(getEditorType(e.getSource()));
	    showOutlineInternal();
	}
    };

    private FocusListener focusListener = new FocusListener() {
	public void focusGained(FocusEvent e) {
	    registerActions(getEditorType(e.getSource()));
	    activateThis();
	    setFocus();
	}

	public void focusLost(FocusEvent e) {
	}
    };

    MouseMoveListener mmList = new MouseMoveListener() {
	public void mouseMove(MouseEvent e) {
	    handleMouseMove(e);
	}
    };

    MouseTrackListener mtList = new MouseTrackListener() {
	public void mouseEnter(MouseEvent e) {
	}

	public void mouseExit(MouseEvent e) {
	    handleMouseExit(e);
	}

	public void mouseHover(MouseEvent e) {
	}
    };

    public FormEditorKeyAdapter getFormEditorKeyAdapter() {
	return formEditorKeyAdapter;
    }

    public FormComponent getSelectedComponent(int i) {
	if (i < 0 || i > selectedComps.size() - 1)
	    return null;
	return (FormComponent) selectedComps.elementAt(i);
    }

    public void doMoveOrCopy(FormComponent tfc, int pos, FormComponent[] sels, Vector boundVec, boolean copy) {
	UndoableGroupAction gua = new UndoableGroupAction();
	String msg = null;
	for (int i = 0; i < sels.length; i++) {
	    FormComponent fc = sels[i];
	    if (fc == null)
		continue;
	    if (fc.isBaseComponent())
		continue;
	    if (fc.isInherited()) {
		msg = "Inherited elements cannot be moved";
		continue;
	    }

	    if (fc.isSWTAWTInternal()) {
		fc = fc.getSWTAWTContainer();
	    }

	    UndoableEditAction uea;
	    Rectangle fcb = null;
	    Rectangle b = null;
	    String plt = null;
	    if (tfc != null)
		plt = tfc.getSuperLayoutType();
	    if (fc.isCWT() || fc.isPropertySet("preferredSize") || fc.isPropertySet("size") || fc.isPropertySet("bounds") || "Form".equals(plt) || "GridBag".equals(plt) || "Table".equals(plt)
		    || "Mig".equals(plt)) {
		fcb = fc.getBoundsCopy();
		b = fcb;
		if (boundVec != null)
		    b = (Rectangle) boundVec.elementAt(i);
	    }
	    FormComponent target = tfc;
	    if (target == null)
		target = fc.getParent();
	    if (copy) {
		uea = new UndoableEditAction(null, null, -1, null, fc.getCopy(true, fc.getEditor()), target, pos, b);
	    } else {
		int fci = fc.getIndexInParent();
		if (target.equals(fc.getParent()) && fci > pos)
		    fci++;

		uea = new UndoableEditAction(fc, fc.getParent(), fci, fcb, fc, target, pos, b);
	    }
	    gua.addAction(uea);
	}
	if (gua.getActionCount() > 0)
	    executeUndoableAction(gua);
	if (msg != null)
	    displayWarning(msg, msg);
    }

    /*
     * private void executeMoveOrCopy( FormComponent tfc, int pos,
     * FormComponent[] sels, boolean copy) { Vector comps = new Vector(); for
     * (int i = 0; i < sels.length; i++) { FormComponent fc = sels[i]; try { if
     * (copy) { fc = fc.copyToParent(tfc, pos); if (fc != null) comps.add(fc); }
     * else { //System.out.println("MOVE TO "+tfc+", "+pos+", "+fc);
     * fc.moveToParent(tfc, pos); comps.add(fc); } } catch (Throwable t) {
     * jiglooPlugin.handleError(t); } } refreshComponents(comps); refresh(true);
     * setSelectedComponents(comps); setDirty(true); }
     */
    private void moveWindowFramesBy(int x, int y, boolean copy, boolean allowed) {
	for (int i = 0; i < selectedComps.size(); i++) {
	    WindowFrame wf = getWindowFrame(getSelectedComponent(i), false);
	    wf.setMoveMode(true, copy);
	    wf.moveBy(x, y);
	}
	mainComp.update();
    }

    public void adjustEventPoint(MouseEvent pt) {
	adjustEventPoint(pt, false);
    }

    public Composite getMainControl() {
	return mainControl;
    }

    public void adjustEventPoint(MouseEvent pt, boolean bugFix) {
	if (mainControl == null)
	    return;
	if (root.isSwing() && root.getComponent() != null) {
	    java.awt.Point rootLoc = root.getComponent().getLocation();
	    pt.x -= rootLoc.x;
	    pt.y -= rootLoc.y;
	}

	// bugFix = false;
	// bug in version 3 - but only on older GTK versions?
	if (bugFix && jiglooPlugin.isLinux() && jiglooPlugin.isVersion3())
	    return;

	if (pt.getSource().equals(mainComp)) {
	    if (!USE_INTERNAL_SHELL) {
		if (eventCatcher != null) {
		    // isViewPart
		    Point loc1 = mainControl.toDisplay(0, 0);
		    Point loc2 = mainComp.toDisplay(0, 0);
		    pt.x -= (loc1.x - loc2.x);
		    pt.y -= (loc1.y - loc2.y);
		} else {
		    pt.x -= mainControl.getLocation().x;
		    pt.y -= mainControl.getLocation().y;
		}
	    } else {
		pt.x -= eventCatcher.getLocation().x;
		pt.y -= eventCatcher.getLocation().y;
	    }
	}
    }

    public void adjustEventCoords(int[] coords) {
	if (root.isSwing()) {
	    java.awt.Point rootLoc = root.getComponent().getLocation();
	    coords[0] -= rootLoc.x;
	    coords[1] -= rootLoc.y;
	}
	if (!USE_INTERNAL_SHELL) {
	    coords[0] -= mainControl.getLocation().x;
	    coords[1] -= mainControl.getLocation().y;
	} else {
	    coords[0] -= eventCatcher.getLocation().x;
	    coords[1] -= eventCatcher.getLocation().y;
	}
    }

    MouseAdapter mouseAd = new MouseAdapter() {
	public void mouseDoubleClick(MouseEvent e) {
	    handleMouseDoubleClickInFormEditor(e);
	}

	public void mouseDown(MouseEvent e) {
	    handleMouseDownInFormEditor(e);
	}

	public void mouseUp(MouseEvent e) {
	    handleMouseUpInFormEditor(e);
	}

    };

    /**
     * The point where the mouse was last located, or when the frame is "stuck"
     * then it is the location of the mouse when the frame became stuck.
     */
    private Point lastMousePoint;

    private Point mouseDownPoint, mouseDownPoint0;

    private FormComponent moveTarget;

    private boolean canMove = false;

    private Action lastAction;
    private Action currentAction;

    public void setCurrentAction(Action action) {
	currentAction = action;
	registerActions("Form");
	Display.getDefault().asyncExec(new Runnable() {
	    public void run() {
		showOutline();
	    }
	});
    }

    public Action getCurrentAction() {
	if (currentAction != null)
	    return currentAction;
	if (marqueeFrame != null && marqueeFrame.isVisible()) {
	    Rectangle r = marqueeFrame.getBounds();
	    // r.width might be small if shift is held down to re-do the
	    // last action and the mouse jitters while down - this should not
	    // be treated as a marquee selection!
	    if (r.width <= 2 && r.height <= 2 && isShiftDown())
		return lastAction;
	    return null;
	}
	if (isShiftDown() && isCtrlDown())
	    return lastAction;
	return null;
    }

    private Composite tooltipShell;

    private Text ttText;

    private String curTTText;

    public void setToolTipText(String txt) {
	// if(true)
	// return;

	if (tooltipShell == null) {
	    tooltipShell = new Canvas(getViewportControl().getParent(), SWT.NULL);
	    GridLayout gl = new GridLayout();
	    gl.marginHeight = 1;
	    gl.marginWidth = 1;
	    tooltipShell.setLayout(gl);
	    tooltipShell.setBackground(ColorManager.getColor(0, 0, 0));
	    ttText = new Text(tooltipShell, SWT.WRAP);
	    tooltipShell.setEnabled(false);
	}
	if (txt == null) {
	    tooltipShell.setVisible(false);
	} else {
	    if (!txt.equals(curTTText)) {
		ttText.setText(txt);
		tooltipShell.pack();
	    }
	    curTTText = txt;
	    if (!tooltipShell.isVisible()) {
		tooltipShell.moveAbove(null);
		tooltipShell.setVisible(true);
	    }
	    // getViewportControl().setToolTipText(txt);
	    Point cloc = Display.getDefault().getCursorLocation();
	    cloc = tooltipShell.getParent().toControl(cloc);
	    tooltipShell.setLocation(cloc.x + 20, cloc.y + 20);
	}
    }

    private void handleMouseExit(MouseEvent e) {
	if (tooltipShell != null)
	    tooltipShell.setVisible(false);
	scrollDirn = 0;
    }

    private void handleGridBagToolTip(FormComponent gbc, int x, int y, int x2, int y2, boolean mouseDown) {

	// if (gbc != null && !gbc.isSwing())
	// return;

	String plt = null;
	FormComponent tar = gbc;

	boolean parChanged = moveTarget != null && gbc != null && !moveTarget.equals(gbc.getParent());

	if (moveTarget != null)
	    tar = moveTarget;
	else if (gbc != null && gbc.getParent() != null)
	    tar = gbc.getParent();

	if (tar == null) {
	    return;
	}

	if (gbc != null && ((gbc.getComponent() != null && gbc.getComponent().isVisible()) || (gbc.getControl() != null && gbc.isVisible()))) {

	    FormComponent fc = null;

	    plt = tar.getLayoutType();
	    String lt = gbc.getLayoutType();

	    if (mouseDown) {
		if (tar.usesGridTypeLayout())
		    fc = tar;
	    } else {
		if (gbc.usesGridTypeLayout())
		    fc = gbc;
		else if (tar.usesGridTypeLayout())
		    fc = tar;
	    }
	    if (fc != null) {

		if (mouseDown || getCurrentAction() instanceof FormAddAction || getCurrentAction() instanceof FormPasteAction || parChanged) {
		    fc.updateGridCoords(x, y, x2, y2, gbc);
		}

		// gridCoordsChanged only works *after* setGridBagCoords called!
		boolean markerNeeded = mouseDown || getCurrentAction() instanceof FormAddAction || getCurrentAction() instanceof FormPasteAction;

		if (windowFrameResizing && (isShiftDown() || root.equals(selectedComp)))
		    markerNeeded = false;

		if (markerNeeded) {
		    if (awtControl != null)
			awtControl.setComponentToBeLayedOut(fc);
		    boolean drawMarker = parChanged || fc.gridCoordsChanged();
		    getGridEdgeManager().drawGreenMarker(drawMarker, fc);
		}
	    }
	}

	if (gbc != null && tar.usesGridTypeLayout()) {
	    try {
		Rectangle bounds = null;
		boolean hwf = hasWindowFrame(gbc);
		if (hwf)
		    bounds = getFrameRelativeTo(gbc, tar);
		setToolTipText(gbc.getGridBagInfo(bounds, mouseDown));
	    } catch (Throwable ex) {
		jiglooPlugin.handleError(ex);
	    }
	} else {
	    setToolTipText(null);
	}
    }

    private void clearGridBagMarker() {
	getGridEdgeManager().clearGreenMarker();
    }

    // holds position of mouse when an x or y snap point is found
    // When mouse moves more than 10 pixels from snap point, WindowFrame
    // is allowed to move again
    private int snapX = -1;
    private int snapY = -1;
    private MarqueeFrame addFrame = null;
    private HitResult hitResult;
    private DelayableRunnable enterTabDrun = null;

    private boolean isAssignableFrom(Class cls1, Class cls2) {
	return Cacher.isAssignableFrom(cls1, cls2);
    }

    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private void handleMouseMove(MouseEvent e) {

	try {

	    if (lastMouseX == e.x && lastMouseY == e.y)
		return;
	    lastMouseX = e.x;
	    lastMouseY = e.y;

	    if (!initialized || getRootComponent() == null)
		return;

	    // if(swtGlassPane != null && (currentAction != null || mouseDown))
	    // swtGlassPane.setOpaque(true);
	    //
	    if (jiglooPlugin.DEBUG_EXTRA)
		System.out.println("handleMouseMove " + e.getSource() + ", " + e.x + ", " + e.y);

	    if (mouseDown && selectedComp != null) {
		FormComponent selPar = selectedComp.getParent();
		if (selectedComp.isSwingInSwt()) {
		    setSelectedComponent(selPar, true);
		} else if (selectedComp.isSwtInSwing()) {
		    if (selPar.isA(java.awt.Canvas.class) && selPar.getParent() != null && selPar.getParent().isA(JPanel.class))
			setSelectedComponent(selPar.getParent(), true);
		    else
			setSelectedComponent(selPar, true);
		}
	    }

	    if (mouseDown && getSelectedComponents().contains(getRootComponent()))
		unselectComponent(getRootComponent(), true);

	    // get current action now before marqueeFrame's visibility is
	    // changed which might make getCurrentAction() return null
	    Action currAct = getCurrentAction();

	    if (currAct == null) {
		if (addFrame != null)
		    addFrame.setVisible(false);
		// setCursor(CURSOR_DEF);
	    }

	    if (mouseDown && isShiftDown() && marqueeFrame != null) {
		marqueeFrame.setVisible(true);
		int x0 = mouseDownPoint0.x;
		int y0 = mouseDownPoint0.y;
		int mpx = e.x;
		int mpy = e.y;
		if (e.getSource().equals(eventCatcher)) {
		    mpx += eventCatcher.getLocation().x;
		    mpy += eventCatcher.getLocation().y;
		}
		int w = mpx - x0;
		int h = mpy - y0;
		if (w > 0 && h > 0)
		    marqueeFrame.setBounds(x0, y0, w, h);
		else if (h > 0)
		    marqueeFrame.setBounds(mpx, y0, -w, h);
		else if (w > 0)
		    marqueeFrame.setBounds(x0, mpy, w, -h);
		else
		    marqueeFrame.setBounds(mpx, mpy, -w, -h);
		return;
	    }

	    HitResult hr2 = getComponentAt(e, false, true);
	    boolean hitChanged = (!hr2.equals(hitResult));
	    hitResult = hr2;

	    FormComponent sel = hitResult.formComp;

	    if (currAct == null) {
		if (getSelectedComponent() == null || !mouseDown) {
		    setCursor(CURSOR_DEF);
		}
	    } else {
		FormComponent hc = sel;
		if (currAct instanceof FormAddAction) {
		    setCursor(CURSOR_X);
		    FormAddAction faa = (FormAddAction) currAct;
		    Class addClass = faa.getAddClass();
		    int style = faa.getAddStyle();

		    if (sel == null && addClass != null && ((addClass.equals(Menu.class) && style == SWT.BAR) || (isInSwingMode() && isAssignableFrom(addClass, JMenuBar.class))))
			hc = getRootComponent();

		    if (sel == null) {
			// adding a component outside the main class
			setMoveTarget(null);
			setCursor(CURSOR_X);
		    } else if (hc != null && hc.canAddToThis(addClass, style)) {
			setCursor(CURSOR_X);
			setMoveTarget(hc);
		    } else if (hc != null && hc.getParent() != null && hc.getParent().canAddToThis(addClass, style)) {
			setCursor(CURSOR_X);
			setMoveTarget(hc.getParent());
		    } else {
			setCursor(CURSOR_NO);
			setMoveTarget(null);
		    }
		} else if (currAct instanceof FormLayoutAction) {
		    if (sel == null)
			hc = getRootComponent();
		    if (hc != null && hc.canSetLayout()) {
			setCursor(CURSOR_X);
			setMoveTarget(hc);
		    } else {
			setCursor(CURSOR_NO);
			setMoveTarget(null);
		    }
		} else if (currAct instanceof FormPasteAction) {
		    if (sel == null)
			hc = getRootComponent();
		    setCursor(CURSOR_X);
		    setMoveTarget(hc);
		}
	    }

	    FormComponent gbc = sel;
	    if (getSelectedComponent() != null && mouseDown)
		gbc = getSelectedComponent();

	    if (mouseDown || currAct != null) {

		Rectangle spb = scrollPane.getClientArea();
		Point org = scrollPane.getOrigin();
		int scrollX = e.x - org.x;
		int scrollY = e.y - org.y;
		if (USE_INTERNAL_SHELL && e.getSource().equals(eventCatcher)) {
		    scrollX += eventCatcher.getLocation().x;
		    scrollY += eventCatcher.getLocation().y;
		}
		if (scrollX < 0 || scrollY < 0 || scrollX > spb.width || scrollY > spb.height) {
		    scrollDirn = 0;
		} else {
		    boolean doubleSpeed = false;
		    int EDGE = 10;
		    int DS_FAC = 100; // no double speed region
		    boolean scrollLeft = scrollX < EDGE;
		    if (scrollX < EDGE / DS_FAC)
			doubleSpeed = true;
		    boolean scrollRight = (spb.width - scrollX) < EDGE;
		    if ((spb.width - scrollX) < EDGE / DS_FAC)
			doubleSpeed = true;
		    boolean scrollUp = scrollY < EDGE;
		    if (scrollY < EDGE / DS_FAC)
			doubleSpeed = true;
		    boolean scrollDown = (spb.height - scrollY) < EDGE;
		    if ((spb.height - scrollY) < EDGE / DS_FAC)
			doubleSpeed = true;
		    scrollDirn = 0;
		    if (scrollLeft)
			scrollDirn += 1;
		    if (scrollRight)
			scrollDirn += 2;
		    if (scrollUp)
			scrollDirn += 4;
		    if (scrollDown)
			scrollDirn += 8;
		    if (doubleSpeed)
			scrollDirn += 16;
		    scrollFormInDirn();
		}
	    }

	    if (sel != null) {
		if (USE_INTERNAL_SHELL) {
		    Rectangle selB = sel.getBoundsRelativeTo(null, false);
		    Rectangle c2b = eventCatcher.getBounds();
		    setStatus(sel + ", x:" + (e.x + c2b.x - selB.x) + ", y:" + (e.y + c2b.y - selB.y));
		} else {
		    Rectangle selB = sel.getBoundsRelativeTo(null, false);
		    setStatus(sel + ", x:" + (e.x - selB.x) + ", y:" + (e.y - selB.y));
		}
	    }

	    adjustEventPoint(e, true);

	    if (isInSwingMode() && currAct != null)
		e.y += getMenuHeight();

	    if (isInSWTMode() && currAct != null)
		e.y -= getMenuHeight();

	    int x = e.x;
	    int y = e.y;

	    if ((getSelectedComponent() == null || !mouseDown) && currAct == null) {
		return;
	    }

	    // System.out.println("MOUSE MOVE, "+moveTarget);
	    if (lastMousePoint == null) {
		if (mouseDownPoint == null)
		    lastMousePoint = new Point(x, y);
		else
		    lastMousePoint = new Point(mouseDownPoint.x, mouseDownPoint.y);
	    }

	    int dx = x - lastMousePoint.x;
	    int dy = y - lastMousePoint.y;

	    int dx2 = 0;
	    int dy2 = 0;

	    if (mouseDownPoint != null) {
		dx2 = x - mouseDownPoint.x;
		dy2 = y - mouseDownPoint.y;
	    }

	    if (mouseDown && !mouseMoved && dx2 * dx2 < WIGGLE_ROOM * WIGGLE_ROOM && dy2 * dy2 < WIGGLE_ROOM * WIGGLE_ROOM)
		return;

	    if (!checkParsingWhenMouseDown)
		checkParsing();

	    FormComponent selComp = getSelectedComponent();
	    if (currAct instanceof FormAddAction || currAct instanceof FormPasteAction) {
		selComp = null;
	    }

	    FormComponent target = sel;
	    while (target != null && target.getParent() != null && selComp != null && !selComp.canMoveToParent(target))
		target = target.getParent();
	    sel = target;

	    boolean overBackgroundTab = false;
	    // if we are in the process of moving/adding an element and the
	    // mouse hovers
	    // over a tab which isn't the selected tab for more than 2 seconds
	    // then bring
	    // that tab to the front.
	    FormComponent targetPar = moveTarget != null ? moveTarget.getParent() : null;
	    if (targetPar != null) {
		int selIndex = -1;
		if (targetPar.isSubclassOf(JTabbedPane.class))
		    selIndex = ((JTabbedPane) targetPar.getComponent()).getSelectedIndex();
		if (targetPar.isSubclassOf(CTabFolder.class))
		    selIndex = ((CTabFolder) targetPar.getControl()).getSelectionIndex();
		if (selIndex != -1 && selIndex != moveTarget.getNonInheritedIndexInParent())
		    overBackgroundTab = true;
	    }

	    if (overBackgroundTab) {
		if (enterTabDrun == null) {
		    enterTabDrun = new DelayableRunnable(500, true) {
			public void run() {
			    getGridEdgeManager().clearGreenMarker();
			    if (moveTarget != null)
				moveTarget.bringToFront(false);
			}
		    };
		}
		enterTabDrun.trigger();
	    } else {
		if (enterTabDrun != null)
		    enterTabDrun.cancel();
		// enterTabDrun = null;
	    }

	    if (!overBackgroundTab) {
		if (currAct != null) {
		    handleGridBagToolTip(gbc, x, y + (isInSWTMode() ? getMenuHeight() : 0), FormComponent.GRIDBAG_UNDEFINED, FormComponent.GRIDBAG_UNDEFINED, mouseDown);
		} else {
		    handleGridBagToolTip(gbc, x, y + (isInSwingMode() ? getMenuHeight() : 0), FormComponent.GRIDBAG_UNDEFINED, FormComponent.GRIDBAG_UNDEFINED, mouseDown);
		}
	    }

	    int sx = x, sy = y;
	    int sx2 = -1, sy2 = -1;

	    Rectangle pb = getRootComponent().getBoundsRelativeToViewport();

	    Rectangle newBnds = getAboutToBeAddedBounds();
	    int newWidth = 60;
	    int newHeight = 20;
	    if (newBnds != null) {
		newWidth = newBnds.width;
		newHeight = newBnds.height;
	    }

	    sx = lastMousePoint.x - newWidth / 2;
	    sy = lastMousePoint.y - newHeight / 2;
	    if (currAct instanceof FormAddAction || currAct instanceof FormPasteAction) {
		if (snapX != -1) {
		    sx += (x - snapX);
		}
		if (snapY != -1) {
		    sy += (y - snapY);
		}
	    }
	    sx2 = sx + newWidth;
	    sy2 = sy + newHeight;

	    if (moveTarget != null && PaneLayoutHandler.handlesLayout(moveTarget) && hitChanged) {
		awtControl.redraw();
	    }

	    if (target != null && target.usesSnapTo()) {

		Rectangle b = null;
		// b will be bounds of WindowFrames at *last* mouse position
		if (currAct == null && selectedComps.size() > 0)
		    b = JiglooUtils.getSelectedBoundsFromFrames(this);

		if (b != null) {
		    sx = b.x - pb.x;
		    sy = b.y - pb.y;
		    if (snapX != -1)
			sx += (x - snapX);
		    if (snapY != -1)
			sy += (y - snapY);
		    sx2 = sx + b.width;
		    sy2 = sy + b.height;
		}

		int tx = sx;
		int tx2 = sx2;
		int ty = sy;
		int ty2 = sy2;

		// sx, sy etc mark the corners of the window frames where they
		// would be if they weren't snapped in position
		SnapTo tmpSnapTo = SnapTo.calculateSnapTo(tx, ty, tx2, ty2, tx, ty, tx2, ty2, target, selComp, this, SnapTo.MODE_ELEMENT);

		if (!tmpSnapTo.hasNonRelHMatch()) {
		    snapX = -1;
		} else if ((tmpSnapTo.hasNonRelHMatch() && (snapTo == null || !snapTo.hasNonRelHMatch())) || (snapTo != null && !snapTo.equals(tmpSnapTo, 2))) {
		    // tmpSnapTo.dx is change from last mouse position
		    if (snapTo != null && snapTo.hasNonRelHMatch()) {
			snapX = lastMousePoint.x + dx + tmpSnapTo.dx;
			lastMousePoint.x = snapX;
			dx = dx + tmpSnapTo.dx;
		    } else {
			snapX = lastMousePoint.x + tmpSnapTo.dx;
			lastMousePoint.x = snapX;
			dx = tmpSnapTo.dx;
		    }
		    if (currAct == null && mouseDown) {
			moveWindowFramesBy(dx, 0, isInCopyMode(), canMove);
		    }
		}

		if (!tmpSnapTo.hasNonRelVMatch()) {
		    snapY = -1;
		} else if ((tmpSnapTo.hasNonRelVMatch() && (snapTo == null || !snapTo.hasNonRelVMatch())) || (snapTo != null && !snapTo.equals(tmpSnapTo, 1))) {
		    if (snapTo != null && snapTo.hasNonRelVMatch()) {
			snapY = lastMousePoint.y + dy + tmpSnapTo.dy;
			lastMousePoint.y = snapY;
			dy = dy + tmpSnapTo.dy;
		    } else {
			snapY = lastMousePoint.y + tmpSnapTo.dy;
			lastMousePoint.y = snapY;
			dy = tmpSnapTo.dy;
		    }
		    if (currAct == null) {
			moveWindowFramesBy(0, dy, isInCopyMode(), canMove);
		    }
		}

		if (!tmpSnapTo.equals(snapTo)) {
		    // redraw the snap-to grid lines
		    if (awtControl != null && !awtControl.isDisposed()) {
			awtControl.redraw();
		    }
		    if (swtGlassPane != null)
			swtGlassPane.redraw();
		}
		snapTo = tmpSnapTo;

	    } else {
		snapTo = null;
		snapX = snapY = -1;
	    }

	    if ((currAct instanceof FormAddAction && !((FormAddAction) currAct).isLayoutAction()
		    && (ClassUtils.isVisual(((FormAddAction) currAct).getAddClass()) || ClassUtils.isSWTAWT(((FormAddAction) currAct).getAddClass()))
		    && !ClassUtils.isMenuClass(((FormAddAction) currAct).getAddClass())) || currAct instanceof FormPasteAction) {

		if (addFrame == null)
		    addFrame = new MarqueeFrame(getViewportControl(), this);
		FormComponent tempFC = getAboutToBeAddedComponent();
		int afx = sx + pb.x;
		int afy = sy + pb.y;
		if (snapX != -1)
		    afx = snapX - newWidth / 2 + pb.x;
		if (snapY != -1)
		    afy = snapY - newHeight / 2 + pb.y;

		if (tempFC != null) {
		    addFrame.setImageFC(tempFC);
		} else {
		    addFrame.setImageFC(null);
		}
		addFrame.setBounds(afx, afy, newWidth, newHeight);
		addFrame.setVisible(true);
		addFrame.update();
		getViewportControl().update();
		if (rootDecoration != null)
		    rootDecoration.update();
		if (awtControl != null && !awtControl.isDisposed()) {
		    awtControl.update();
		} else {
		    if (mainControl != null && !mainControl.isDisposed())
			mainControl.update();
		}

	    } else {
		if (addFrame != null)
		    addFrame.setVisible(false);
	    }

	    if (lastMousePoint != null) {
		if (snapX == -1)
		    lastMousePoint.x = x;
		if (snapY == -1)
		    lastMousePoint.y = y;
	    }

	    // if (!mouseMoved && dx2 * dx2 < WIGGLE_ROOM * WIGGLE_ROOM
	    // && dy2 * dy2 < WIGGLE_ROOM * WIGGLE_ROOM)
	    // return;

	    if (dx == 0 && dy == 0)
		return;

	    if (!mouseDown)
		return;

	    mouseMoved = true;

	    // selComp = getSelectedComponent();

	    if (sel != null && sel.equals(selComp)) {
		sel = selComp.getParent();
	    }

	    if (getRootComponent().equals(selComp))
		return;

	    if (selComp != null && selComp.isMenuComponent())
		return;

	    canMove = true;

	    if (sel == null) {
		canMove = false;
	    } else {
		for (int i = 0; i < selectedComps.size(); i++) {
		    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
		    if (!fc.canMoveToParent(sel)) {
			canMove = false;
			break;
		    }
		}
	    }

	    // for GroupLayout (only?)
	    if (!canMove && sel != null) {
		sel = sel.getParent();
		canMove = true;
		for (int i = 0; i < selectedComps.size(); i++) {
		    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
		    if (!fc.canMoveToParent(sel)) {
			canMove = false;
			break;
		    }
		}
	    }

	    FormComponent moveTarget = null;

	    if (hitResult.formComp != null && !hitResult.formComp.isContainer())
		moveTarget = hitResult.formComp.getParent();

	    if (hitResult.isOnLeftRightEdge() && sel != null && moveTarget != null && (moveTarget.isDragReorderable())) {
		canMove = true;
		marker.show(hitResult.formComp, selectedComp, hitResult);
		setMoveTarget(moveTarget);
	    } else if (hitResult.isOnBorder() && sel != null && moveTarget != null && (PaneLayoutHandler.handlesLayout(moveTarget))) {
		canMove = true;
		System.out.println("PaneLayout handleMove " + hitResult.formComp + ", " + moveTarget);
		setMoveTarget(moveTarget);
	    } else {
		marker.hide();
		if (canMove) {
		    setMoveTarget(sel);
		    if (sel.isDragReorderable()) {
			int numKids = sel.getChildCount();
			if (numKids != 0) {
			    FormComponent fcAfter = hitResult.getChildAfter();
			    if (fcAfter == null) {
				FormComponent hitFC = hitResult.formComp;
				if (hitFC.getChildCount() != 0) {
				    marker.show(hitFC.getChildAt(hitFC.getChildCount() - 1), selectedComp, HitResult.EAST);
				}
			    } else {
				marker.show(fcAfter, selectedComp, HitResult.WEST);
			    }
			}
		    }
		} else {
		    setMoveTarget(null);
		}
	    }

	    if (selectedComps.contains(sel) || canMove) {
		setCursor(CURSOR_MOVE);
	    } else {
		setCursor(CURSOR_NO);
	    }

	    if (snapX != -1)
		dx = 0;

	    if (snapY != -1)
		dy = 0;

	    if (currAct == null) {
		moveWindowFramesBy(dx, dy, isInCopyMode(), canMove);
	    }

	    // force repaint so that when moving multiple components,
	    // the frame lines are not left for too long befre being cleared
	    if (awtControl != null && !awtControl.isDisposed()) {
		awtControl.getParent().update();
		awtControl.update();
	    }

	} catch (Throwable ex) {
	    jiglooPlugin.handleError(ex);
	}
    }

    /**
     * @return
     */
    private Rectangle getAboutToBeAddedBounds() {
	Action ca = getCurrentAction();
	if (ca instanceof FormAddAction) {
	    return ((FormAddAction) ca).getBounds();
	}
	if (getCurrentAction() instanceof FormPasteAction) {
	    return ((FormPasteAction) ca).getBounds(this);
	}
	return null;
    }

    public Cursor getNoCursor() {
	return CURSOR_NO;
    }

    public Cursor getArrowCursor() {
	return CURSOR_ARROW;
    }

    public Cursor getXCursor() {
	return CURSOR_X;
    }

    public Cursor getDefCursor() {
	return CURSOR_DEF;
    }

    public void handleMouseDoubleClickInFormEditor(MouseEvent e) {

	if (addFrame != null && !addFrame.isDisposed())
	    addFrame.setVisible(false);
	mouseDown = false;
	clearGridBagMarker();
	checkParsing();

	FormComponent sel = getSelectedComponent();
	if (sel.showCustomizer())
	    return;
	if (sel.hasProperty("text") && sel.getPropertyValue("text") instanceof String) {
	    txtWindowShowing = true;
	    Shell shell = getSite().getShell();
	    String init = (String) sel.getPropertyValue("text");
	    init = init.replaceAll("\\\\", "\\\\\\\\");
	    init = JiglooUtils.replace(init, "\n", "\\n");
	    init = JiglooUtils.replace(init, "\t", "\\t");
	    init = JiglooUtils.replace(init, "\r", "\\r");
	    String text = TextWindow.openWindow(shell, init, sel);
	    text = JiglooUtils.replace(text, "\\t", "\t");
	    text = JiglooUtils.replace(text, "\\n", "\n");
	    text = JiglooUtils.replace(text, "\\r", "\r");
	    text = text.replaceAll("\\\\\\\\", "\\\\");
	    txtWindowShowing = false;
	    if (text != null && !text.equals(init)) {
		sel.setPropertyValue("text", text);
	    }
	} else {
	    if (sel.isInherited()) {
		if (sel.getParent().showCustomizer())
		    return;
	    }
	}

    }

    /**
     * Given an id, (say,"[255, 255, 255]", or "Tahoma, 1, 0") will return a
     * resource name (eg, jButton1Color) and if none exists for that id will
     * return null, and add the given resourceName as the resourceName connected
     * to that id.
     */
    public String getResourceName(String id, String resourceName) {
	if (resourceMap.containsKey(id))
	    return (String) resourceMap.get(id);
	resourceMap.put(id, resourceName);
	return null;
    }

    private Rectangle getFrameRelativeTo(FormComponent child, FormComponent parent) {
	Rectangle parb = parent.getBoundsRelativeTo(null);
	parb = new Rectangle(parb.x, parb.y, parb.width, parb.height);
	Rectangle bounds = getWindowFrame(child, false).getBoundsCopy();
	if (bounds == null)
	    return null;
	bounds.x -= parb.x;
	bounds.y -= parb.y;
	return bounds;
    }

    public void showSourceTab() {
	if (isInTabbedMode())
	    formCodeTabFolder.setSelection(codeTabItem);
    }

    private void showFormTab() {
	if (isInTabbedMode()) {
	    formCodeTabFolder.setSelection(formTabItem);
	}
    }

    /**
     * returns the bounds relative to moveTarget, and if moveTarget uses a
     * contentPane, adjusts bounds to be relative to the contentPane
     */
    public Rectangle getBoundsFromFrame(FormComponent fc, FormComponent moveTarget, boolean adjustForContentPane) {
	Rectangle bounds = getFrameRelativeTo(fc, moveTarget);
	if (bounds == null)
	    return null;
	snapToGrid(bounds, fc, false);
	if (adjustForContentPane && moveTarget.usesContentPane()) {
	    SwingHelper.subtractVectorBetween((Container) moveTarget.getComponent(), fc.getComponent(), bounds);
	}
	return bounds;
    }

    private MouseEvent menuMousePoint = null;

    public MouseEvent getMenuMouseEvent() {
	return menuMousePoint;
    }

    public void setMenuMouseEvent(MouseEvent p) {
	if (p != null) {
	    p.x += BORDER_X;
	    p.y += BORDER_Y;
	}
	menuMousePoint = p;
    }

    private void handleMouseUpInFormEditor(MouseEvent e) {

	formEditorKeyAdapter.handleMouseEvent(e);

	root.clearCachedBounds();

	if (swtGlassPane != null)
	    swtGlassPane.setOpaque(false);

	if (!mouseDown) {
	    setSnapTo(null);
	    return;
	}

	if (jiglooPlugin.DEBUG_EXTRA)
	    System.out.println("handleMouseUp " + e.getSource() + ", " + e.x + ", " + e.y);

	if (addFrame != null && !addFrame.isDisposed())
	    addFrame.setVisible(false);
	mouseDown = false;
	clearGridBagMarker();

	if (marqueeFrame != null) {
	    boolean selection = false;
	    if (marqueeFrame.isVisible()) {
		Rectangle r = marqueeFrame.getBounds();
		// r.width might be small if shift is held down to re-do the
		// last action and the mouse jitters while down - this should
		// not
		// be treated as a marquee selection!
		if (r.width > 2 && r.height > 2) {
		    selection = true;
		    boolean cleared = false;
		    Vector tmp = new Vector();
		    for (int i = 0; i < components.size(); i++) {
			FormComponent fc = (FormComponent) components.elementAt(i);
			if (!fc.isRoot() && fc.isVisual() && fc.isVisible() && !fc.isInherited()) {
			    Rectangle b = fc.getBoundsRelativeTo(null);
			    if (r.intersects(b) && !(b.contains(r.x, r.y) && b.contains(r.x + r.width, r.y + r.height))) {
				tmp.add(fc);
			    }
			}
		    }
		    boolean ok = false;
		    while (!ok) {
			ok = true;
			for (int i = 0; i < tmp.size(); i++) {
			    FormComponent fc = (FormComponent) tmp.elementAt(i);
			    if (tmp.contains(fc.getParent())) {
				ok = false;
				tmp.remove(fc);
			    }
			}
		    }
		    for (int i = 0; i < tmp.size(); i++) {
			FormComponent fc = (FormComponent) tmp.elementAt(i);
			if (!cleared)
			    clearSelection();
			cleared = true;
			addSelectedComponent(fc, true);
		    }
		}
		marqueeFrame.setVisible(false);
		if (selection) {
		    setSnapTo(null);
		    return;
		}
	    }
	}

	boolean allowedToAdd = !CURSOR_NO.equals(cursor);

	setCursor(CURSOR_DEF);
	hitResult = getComponentAt(e, false, false);
	FormComponent sel = hitResult.formComp;

	if (getCurrentAction() != null)
	    sel = moveTarget;

	boolean doCopy = isInCopyMode();

	boolean diffParent = false;
	FormComponent sc = getSelectedComponent();

	if (moveTarget != null && !moveTarget.getLayoutWrapper().isSet() && "Group".equals(moveTarget.getLayoutType())) {
	    displayMessage("Unable to move or add object", "Unable to move or add object to parent unless\nthe parent's layout is explicitly set in this class");
	    setMoveTarget(null);
	    setSnapTo(null);
	    currentAction = null;
	    containerSnapTo = null;
	    for (int i = 0; i < selectedComps.size(); i++)
		hideWindowFrame(getSelectedComponent(i));
	    return;
	}

	adjustEventPoint(e);

	if (getCurrentAction() != null) {
	    addedComponent = null;

	    if (isInSwingMode())
		e.y += getMenuHeight();

	    pauseUpdate(true);

	    sel = processCurrentAction(e, allowedToAdd, sel);

	    pauseUpdate(false);

	    if (snapTo != null && addedComponent != null) {
		int stx = snapTo.xLeft + snapTo.dx;
		int sty = snapTo.yTop + snapTo.dy;
		int stw = snapTo.xRight + snapTo.dx - stx;
		int sth = snapTo.yBottom + snapTo.dy - sty;

		// selComps will not contain selComp when adding or pasting a
		// component
		if (selectedComps != null && !selectedComps.contains(addedComponent))
		    selectedComps.add(addedComponent);
		if (addedComponent.getParent() != null && selectedComps != null && selectedComps.contains(addedComponent.getParent()))
		    unselectComponent(addedComponent.getParent(), true);

		addedComponent.setBoundsToRoot(new Rectangle(stx, sty, stw, sth));

		if (snapTo.handleMouseUp(FormEditor.this, addedComponent, getSelectedComponents(), null, true)) {
		}
	    }

	    setDirtyAndUpdate(true, true);

	    setCursor(getDefCursor());
	    marker.hide();
	    return;
	}

	int x = e.x;
	int y = e.y;

	if (mouseDownPoint == null) {
	    mouseDownPoint = new Point(x, y);
	}

	if (mouseMoved) {
	    for (int i = 0; i < selectedComps.size(); i++) {
		FormComponent fc = (FormComponent) selectedComps.elementAt(i);
		if (fc.isInherited()) {
		    displayMessage("Unable to move inherited object", "Unable to move inherited object");
		    getWindowFrame(fc, false).realign();
		    setMoveTarget(null);
		    setSnapTo(null);
		    containerSnapTo = null;
		    return;
		}
	    }

	    if (moveTarget != null) {
		if (moveTarget.isContainer()) {
		    if (!moveTarget.hasAncestor(getSelectedComponents())) {

			int position = -1;
			int relPos = -1;
			if (marker.isVisible()) {
			    FormComponent relComp = marker.getRelativeComponent();
			    boolean before = marker.insertBefore();
			    position = relComp.getNonInheritedIndexInParent();
			    if (position != -1 && !before)
				position++;
			    relPos = position;
			}

			if (position != -1) {
			    position += moveTarget.getInheritedCount();
			}

			if (!moveTarget.equals(sc.getParent()))
			    diffParent = true;

			if (PaneLayoutHandler.handleMouseUp(this, moveTarget, hitResult)) {
			    setDirtyAndUpdate(true, true);
			    setMoveTarget(null);
			    return;
			}

			if (moveTarget.usesGroupLayout() && snapTo != null) {
			    for (int i = 0; i < getSelectedComponents().size(); i++) {
				FormComponent selFC = getSelectedComponent(i);
				if (!moveTarget.equals(selFC.getParent())) {
				    selFC.moveToParent(moveTarget, -1);
				    selFC.repairParentConnectionInCode();
				}
			    }
			    LayoutGroup.updateSelectedCompBoundsFromFrames(this);
			    if (snapTo.handleMouseUp(FormEditor.this, getSelectedComponent(), getSelectedComponents(), null, true)) {
				setDirtyAndUpdate(true, true);
				setMoveTarget(null);
				setSnapTo(null);
				return;
			    }
			}

			String lt = moveTarget.getSuperLayoutType();
			if (!diffParent && !doCopy && (moveTarget.usesAbsoluteTypeLayout() || moveTarget.usesGridTypeLayout())) {
			    for (int i = 0; i < selectedComps.size(); i++) {
				FormComponent fc = getSelectedComponent(i);
				Rectangle bounds = getBoundsFromFrame(fc, moveTarget, true);
				if (!fc.isSWT())
				    bounds.y -= getMenuHeight(); // 4.6.1
				fc.setBounds(bounds, true, true);
			    }

			} else {

			    FormComponent[] sels = new FormComponent[selectedComps.size()];
			    Vector boundVec = new Vector();
			    for (int i = 0; i < selectedComps.size(); i++) {
				FormComponent fc = (FormComponent) selectedComps.elementAt(i);

				FormComponent relativeFrame = moveTarget;
				if (moveTarget.isCWT())
				    relativeFrame = fc.getParent();

				Rectangle bounds = getBoundsFromFrame(fc, relativeFrame, true);
				if (fc.isCWT()) {
				    bounds = com.cloudgarden.jigloo.typewise.TypewiseManager.convertToGridBounds(bounds, fc);
				}
				sels[i] = fc;
				boundVec.add(bounds);
			    }
			    int selPos = -1;
			    if (getSelectedComponent() != null)
				selPos = getSelectedComponent().getNonInheritedIndexInParent();
			    boolean moveDown = relPos < selPos;
			    JiglooUtils.sortComponents(sels, moveDown);
			    doMoveOrCopy(moveTarget, position, sels, boundVec, doCopy);

			}

		    }
		}
	    }
	    if (moveTarget != null) {
		setMoveTarget(null);
		marker.hide();
		setSnapTo(null);
		return;
	    }
	}

	if (!mouseMoved && sel != null && !isMultiSelect())
	    setSelectedComponent(sel, true);

	if (moveTarget != null) {
	    setMoveTarget(null);
	}

	if (marker != null)
	    marker.hide();

	setSnapTo(null);

    }

    private boolean isMultiSelect() {
	if (jiglooPlugin.isMacOS())
	    return isCmdDown();
	return isCtrlDown();
    }

    private boolean isCtrlDown() {
	return formEditorKeyAdapter.isCtrlDown();
    }

    private boolean isCmdDown() {
	return formEditorKeyAdapter.isCmdDown();
    }

    private boolean isAltDown() {
	return formEditorKeyAdapter.isAltDown();
    }

    public boolean isShiftDown() {
	return formEditorKeyAdapter.isShiftDown();
    }

    public FormComponent processCurrentAction(MouseEvent e, boolean allowedToAdd, FormComponent sel) {
	if (sel == null)
	    sel = getRootComponent();
	setSelectedComponent(sel, true);
	if (allowedToAdd) {
	    if (getCurrentAction() instanceof FormAddAction) {
		hideWindowFrames(selectedComps);
		FormAddAction faa = (FormAddAction) getCurrentAction();
		if (e != null)
		    faa.setMousePoint(e.x, e.y);
		if (addFrame != null) {
		    Rectangle b = addFrame.getBounds();
		    if (sel.usesGridBagLayout() || sel.usesTableLayout() || sel.usesJGFormLayout() || PaneLayoutHandler.handlesLayout(sel)) {
			if (sel.isRoot() && e != null) {
			    Rectangle rb = sel.getBoundsRelativeToRoot();
			    e.y += rb.y;
			    e.x += rb.x;
			}
		    } else {
			if (e != null) {
			    e.x -= b.width / 2;
			    e.y -= b.height / 2;
			}
		    }
		    if (e != null)
			e.y += getMenuBarHeight();
		}
		faa.run(e);

	    } else if (getCurrentAction() instanceof FormPasteAction) {

		hideWindowFrames(selectedComps);
		((FormPasteAction) getCurrentAction()).run(this, e.x, e.y);

	    } else if (getCurrentAction() instanceof FormLayoutAction) {
		FormLayoutAction fla = (FormLayoutAction) getCurrentAction();
		fla.run();
	    }
	}
	if (palette.release()) { // && !shiftDown) {
	    lastAction = getCurrentAction();
	    currentAction = null;
	    setCursor(getDefCursor());
	}
	return sel;
    }

    public void setCursor(Cursor cursor) {
	if (cursor == null)
	    return;
	if (cursor.equals(this.cursor))
	    return;
	getViewportControl().setCursor(cursor);
	this.cursor = cursor;
    }

    private boolean mouseDown = false;

    private boolean isEOL(String str) {
	return "\n".equals(str) || "\r".equals(str);
    }

    int lastCaretLine = -1;

    boolean reverseOutline = false;

    private StyledText javaEditorControl;

    private DelayableRunnable mdieDRunnable;

    private void handleCursorMovedInEditor(MouseEvent e) {
	// textEditorActive = true;
	if (e != null && (e.stateMask & SWT.ALT) != 0) {
	    if (isInTabbedMode()) {
		parsingEnabled = true;
		handleCursorMovedInEditorNow();
		parsingEnabled = false;
	    }
	    reverseOutline = true;
	} else {
	    reverseOutline = false;
	}
	if (mdieDRunnable == null) {
	    mdieDRunnable = new DelayableRunnable(200, true) {
		public void run() {
		    handleCursorMovedInEditorNow();
		}
	    };
	}
	mdieDRunnable.trigger();
    }

    public void activateThis() {
	getSite().getPage().activate(this);
    }

    private void handleCursorMovedInEditorNow() {

	if (!parsingEnabled()) {
	    // make sure the correct outline is shown when the code editor is
	    // showing
	    showOutlineInternal();
	    return;
	}

	if (!this.equals(getSite().getPage().getActiveEditor())) {
	    activateThis();
	    setFocus();
	}

	if (jcp != null) {
	    try {
		String line = null;
		int offset = -1;
		if (jiglooPlugin.isVersion3Plus()) {
		    // subtract 1 so that we are not on a NL char - if we are at
		    // the
		    // start of a line and move one char back, then
		    // getComponentInCode will call getStartOfLine which will
		    // bring us back to the start of the line.
		    offset = ((JavaEditor) javaEditor).getModelOffset() - 1;
		} else {
		    offset = javaEditorControl.getCaretOffset();
		    int lineNum = javaEditorControl.getLineAtOffset(offset);
		    if (lineNum == lastCaretLine)
			return;
		    lastCaretLine = lineNum;
		    if (offset < 0)
			return;
		    int len = javaEditorControl.getCharCount();
		    if (offset == len)
			offset--;
		    while (offset > 0 && isEOL(javaEditorControl.getTextRange(offset, 1)))
			offset--;
		    int start = offset;
		    int end = offset;
		    while (start > 0 && !isEOL(javaEditorControl.getTextRange(start, 1)))
			start--;
		    start++;
		    while (end < len && !isEOL(javaEditorControl.getTextRange(end, 1)))
			end++;
		    end--;
		    line = javaEditorControl.getText(start, end);
		}
		FormComponent fc = jcp.getComponentInCode(line, offset);
		if (fc != null && fc.isChildOf(root)) {
		    allowMoveCursor = false;
		    selectComponent(fc);
		    allowMoveCursor = true;
		    scrollFormTo(fc);
		}
	    } catch (Throwable ex) {
		jiglooPlugin.handleError(ex);
	    }
	}
    }

    private MarqueeFrame marqueeFrame = null;

    private void handleMouseDownInFormEditor(MouseEvent e) {
	try {

	    formEditorKeyAdapter.handleMouseEvent(e);

	    if (testForCustomizer())
		return;

	    if (jiglooPlugin.DEBUG_EXTRA)
		System.out.println("handleMouseDown " + e.getSource() + ", " + e.x + ", " + e.y);

	    if (checkParsingWhenMouseDown) {
		checkParsing();
	    } else {
		if (getCurrentAction() != null)
		    checkParsing();
	    }

	    scrollDirn = 0;
	    mouseMoved = false;

	    boolean altDown = isAltDown();
	    boolean doCopy = isInCopyMode();
	    boolean multiSel = isMultiSelect();
	    boolean button3 = (e.button == 3);

	    if (jiglooPlugin.isMacOS()) {
		if (isCtrlDown())
		    button3 = true;
		if (altDown)
		    doCopy = true;
	    }

	    if (!button3)
		mouseDown = true;

	    if (button3 && selectedComp != null) {
		showContextMenu(selectedComp);
	    }

	    registerActions("Form");
	    showInternalOutline(false);

	    if (isShiftDown() && mouseDown) {
		if (marqueeFrame == null)
		    marqueeFrame = new MarqueeFrame(getViewportControl(), this);
		int mpx = e.x;
		int mpy = e.y;
		if (e.getSource().equals(eventCatcher)) {
		    mpx += eventCatcher.getLocation().x;
		    mpy += eventCatcher.getLocation().y;
		}

		mouseDownPoint0 = new Point(mpx, mpy);
		marqueeFrame.setBounds(mpx, mpy, 0, 0);
		return;
	    } else {
		if (marqueeFrame != null)
		    marqueeFrame.setVisible(false);
	    }

	    hitResult = getComponentAt(e, true, false);

	    if (mainComp.equals(e.getSource())) {
		// v4.0M3
		FormComponent shell = getRootComponent();
		if (shell != null && hitResult.formComp == null) {
		    hitResult.formComp = shell;
		}
	    }

	    FormComponent fc = hitResult.formComp;

	    if (button3 && !fc.equals(selectedComp)) {
		showContextMenu(fc);
	    }

	    adjustEventPoint(e);
	    int x = e.x;
	    int y = e.y;

	    lastMousePoint = null;
	    mouseDownPoint = new Point(x, y);
	    FormComponent sel = fc;
	    if (sel == null) {
		return;
	    }

	    if (multiSel && canMultiSelect()) {
		if (!button3) {
		    if (!doCopy && selectedComps.contains(sel)) {
			unselectComponent(sel, true);
		    } else {
			addSelectedComponent(sel, true);
		    }
		} else {
		    addSelectedComponent(sel, true);
		}
	    } else {
		if (!sel.equals(moveTarget)) {
		    if (!selectedComps.contains(sel))
			setSelectedComponent(sel, true);
		    // selectedComp = sel;
		}
	    }

	    if (button3) {
		setMenuMouseEvent(e);
	    }

	    showOutlineInternal();

	    if (useJavaCodeParser && !textEditorActive) {
		jcp.selectInCode(selectedComp);
	    }

	    if (!multiSel && sel != null && (!button3 && !mouseMoved)) {
		// setSelectedComponent(sel, true);
	    }

	    if (multiSel && !mouseMoved && sel != null && !sel.equals(lastAddedComp) && !button3 && selectedComps.contains(sel)) {
		unselectComponent(sel, true);
	    }

	} catch (Throwable ex) {
	    jiglooPlugin.handleError(ex);
	}
    }

    private boolean isInCopyMode() {
	if (jiglooPlugin.isMacOS())
	    return isAltDown();
	return isCtrlDown();
    }

    /**
     * @return
     */
    public FormComponent getRootShell() {
	// if(true)
	// return null;

	Vector shells = getExtraCompRoot().getChildrenByClass(Shell.class);
	if (shells == null)
	    return null;
	FormComponent fc = null;
	for (int i = 0; i < shells.size(); i++) {
	    fc = (FormComponent) shells.elementAt(i);
	    if (fc.getNonInheritedChildCount() >= 1)
		return fc;
	}
	return fc;
    }

    private void setMoveTarget(FormComponent target) {
	if (target != null && target.equals(moveTarget))
	    return;

	if (moveTarget != null)
	    hideWindowFrame(moveTarget);

	if (target != null) {
	    WindowFrame wf = getWindowFrame(target, false);
	    showWindowFrame(target);
	    wf.setMoveTargetColor(true);
	}
	moveTarget = target;
	if (swtGlassPane != null)
	    swtGlassPane.redraw();
    }

    public ClassLoader getClassLoader() {
	if (cpLoader == null)
	    createClassLoader();
	return cpLoader;
    }

    private void updateCodeToCurrentStyle(FormComponent root, boolean remove, IProgressMonitor pm) {
	if (root == null)
	    return;
	int cc = root.getChildCount();
	if (remove) {
	    for (int i = 0; i < cc; i++) {
		FormComponent fc = root.getChildAt(i);
		// System.out.println("TESTING "+fc+", "+fc.isSwing()+",
		// "+fc.isButtonGroup());
		if (fc.isButtonGroup() || (fc.getParent() != null && !fc.getParent().equals(getNonVisualRoot()))) {
		    fc.setAllExistsInCode(false);
		    // System.out.println("REMOVING "+fc);
		    getJavaCodeParser().removeFromCode(fc, false, false, pm);
		}
	    }
	} else {
	    for (int i = 0; i < cc; i++) {
		FormComponent fc = root.getChildAt(i);
		if (!fc.existsInCode()) {
		    fc.setWasCutForAll(true);
		    getJavaCodeParser().addToCode(fc, pm, true);
		}
	    }
	}
    }

    public void convertToSWTResMan() {
	boolean updated = false;
	if (getRootComponent().convertToSWTResourceManager())
	    updated = true;
	if (getNonVisualRoot().convertToSWTResourceManager())
	    updated = true;
	if (getMenuBar() != null && getMenuBar().convertToSWTResourceManager())
	    updated = true;
	if (updated) {
	    setDirtyAndUpdate();
	}
    }

    public void updateCodeToCurrentStyle() {
	try {
	    boolean res = MessageDialog.openConfirm(getSite().getShell(), "Confirm code re-arrangement",
		    "This action will rearrange your code to the current GUI coding style\n" + "(eg, using getters, code blocks etc). In most cases this will work without\n"
			    + "problem, but in some cases it may create errors in your code. If the\n" + "errors are not easily corrected you can easily undo the change in the\n"
			    + "source editor (eg, by choosing \"Edit->Undo\" or hitting \"Ctrl+Z\".)");
	    if (!res)
		return;
	    ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
	    pmd.open();
	    IProgressMonitor pm = pmd.getProgressMonitor();
	    FormComponent rc = getRootComponent();
	    FormComponent mb = getMenuBar();
	    FormComponent nvr = getNonVisualRoot();
	    int n = rc.getTotalChildCount() + nvr.getTotalChildCount();
	    if (mb != null)
		n += mb.getTotalChildCount();
	    n *= 2;
	    pm.beginTask("Updating code...", n);
	    inCutMode = true;

	    updateCodeToCurrentStyle(nvr, true, pm);
	    updateCodeToCurrentStyle(rc, true, pm);
	    updateCodeToCurrentStyle(mb, true, pm);

	    updateCodeToCurrentStyle(nvr, false, pm);
	    updateCodeToCurrentStyle(rc, false, pm);
	    updateCodeToCurrentStyle(mb, false, pm);
	    pmd.close();
	    inCutMode = false;
	    setDirtyAndUpdate();
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    public void clearClassCache() {
	classCache.clear();
    }

    public boolean dependsOnClass(String clsName) {
	try {
	    if (!created || components == null)
		return false;
	    if (jcp == null || jcp.getFullClassName() == null)
		return false;
	    String editorClassName = jcp.getFullClassName();
	    if (clsName.startsWith(editorClassName + "$"))
		return true;

	    if (editorClassName.equals(clsName))
		return false;
	    if (root != null && root.hasSuperClass(clsName))
		return true;

	    for (int i = 0; i < components.size(); i++) {
		FormComponent fc = (FormComponent) components.elementAt(i);
		Class rc = fc.getRootClass();
		if (clsName.equals(fc.getClassName()) || (rc != null && clsName.equals(rc.getName()))) {
		    return true;
		}
	    }

	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
	return false;
    }

    /**
     * This is called when a class file for this project has been changed.
     * Signals that if any Java classes were changed that this editor depends
     * on, then the ClassLoader should be re-created and the form reparsed.
     */
    public void handleClassCompiled(String clsName) {
	if (!dependsOnClass(clsName))
	    return;

	if (isActiveEditor()) {
	    javaCodeSync.setTemporaryDelay(100);
	    javaCodeSync.trigger();
	} else {
	    needsReparse = true;
	}
    }

    public void handleClassPathChanged(IProject proj) {
	if (!proj.equals(getProject()))
	    return;
	clearClassLoader();
	needsReparse = true;
	if (isActiveEditor()) {
	    javaCodeSync.setTemporaryDelay(1000);
	    javaCodeSync.trigger();
	}
    }

    public Class loadClass(String className) {
	if (className == null || "UNKNOWN".equals(className))
	    return null;

	// if that didn't work, do it properly!
	if (className.equals("double"))
	    return double.class;
	if (className.equals("int"))
	    return int.class;
	if (className.equals("long"))
	    return long.class;
	if (className.equals("float"))
	    return float.class;
	if (className.equals("boolean"))
	    return boolean.class;

	if (className.indexOf("<") > 0) {
	    className = className.substring(0, className.indexOf("<"));
	}

	// first do a quick-and-dirty search through the cache for either a
	// matching Class or NoClass
	Object cc = classCache.get(className);
	if (cc instanceof Class)
	    return (Class) cc;
	if (cc instanceof NoClass)
	    return null;

	String convClassName = ConversionUtils.convertUnavailableClassName(className);

	if (!convClassName.equals(className)) {

	    className = convClassName;

	    cc = classCache.get(className);

	    if (cc instanceof Class)
		return (Class) cc;

	    if (cc instanceof NoClass)
		return null;
	}

	boolean reload = cc != null && cc.getClass().getName().equals(ReloadClass.class.getName());

	Class cls = null;

	createClassLoader();

	if (reload) {
	    ClassLoader tmp = ClassLoaderCache.createClassLoader(getProject(), cpLoader);
	    try {
		cls = ((FormClassLoader) tmp).findClass(className);
	    } catch (Throwable e) {
		e.printStackTrace();
	    }
	}
	try {
	    if (cls == null)
		cls = cpLoader.loadClass(className);
	} catch (OutOfMemoryError e) {
	    jiglooPlugin.writeToLog(e);
	} catch (UnsupportedClassVersionError e) {
	    String msg = "Error: Unable to load class " + className + " - compiled with incompatible java version. " + e;
	    System.out.println(msg);
	    jiglooPlugin.addToLog(msg);
	} catch (Throwable e) {
	    // System.out.println("Unable to load class "+className+", "+e);
	}

	try {
	    if (cls == null) {
		cls = getClass().getClassLoader().loadClass(className);
	    }
	    if (cls == null)
		classCache.put(className, new NoClass());
	    else
		classCache.put(className, cls);
	    // System.err.println("LOADED CLASS "+className);
	    return cls;
	} catch (Throwable e) {
	    classCache.put(className, new NoClass());
	    if (jiglooPlugin.DEBUG_EXTRA)
		jiglooPlugin.writeToLog("Unable to load Class: " + className);
	    // jiglooPlugin.handleError(e, "UNABLE TO LOAD Class: " +
	    // className);
	    // jiglooPlugin.handleError(e);
	}
	return null;
    }

    private IWindowListener wlistener = new IWindowListener() {
	public void windowActivated(IWorkbenchWindow w) {
	}

	public void windowClosed(IWorkbenchWindow w) {
	}

	public void windowDeactivated(IWorkbenchWindow w) {
	}

	public void windowOpened(IWorkbenchWindow w) {
	    // showFormView();
	}
    };

    public IProject getProject() {
	return javaFile.getProject();
    }

    private IJavaProject javaProject;

    public IJavaProject getJavaProject() {
	if (javaProject == null)
	    javaProject = JavaCore.create(getProject());
	return javaProject;
    }

    public static IJavaProject getJavaProject(IProject proj) {
	return JavaCore.create(proj);
    }

    public String getJavaProjectName() {
	return getJavaProject().getElementName();
    }

    public IFile getJavaFile() {
	return javaFile;
    }

    public void clearSelection() {
	if (selectedComps == null)
	    return;
	hideWindowFrames(selectedComps);
	selectedComps.clear();
	setAlignButtonStates();
	selectedComp = null;
	// structSel = null;
    }

    private boolean canMultiSelect() {
	// return false;
	return jiglooPlugin.getDefault().canUseProfFeature(getSite(), "Multi-select");
    }

    public void snapToGrid(Rectangle rec, FormComponent comp, boolean absolute) {
	if (gridSize <= 0)
	    return;
	Rectangle br;
	if (!absolute) {
	    rec.x = round(rec.x, gridSize);
	    rec.y = round(rec.y, gridSize);
	} else {
	    if (comp.getParent() != null) {
		br = comp.getParent().getBoundsRelativeTo(null);
		rec.x = round(rec.x - br.x, gridSize) + br.x;
		rec.y = round(rec.y - br.y, gridSize) + br.y;
	    } else {
		rec.x = round(rec.x - BORDER_X, gridSize) + BORDER_X;
		rec.y = round(rec.y - BORDER_Y, gridSize) + BORDER_Y;
	    }
	}
	rec.width = round(rec.width, gridSize);
	rec.height = round(rec.height, gridSize);
    }

    private static int round(int val, int step) {
	if (val > 0)
	    val += step / 2;
	else
	    val -= step / 2;
	return step * (int) (val / step);
    }

    private boolean windowFrameResizing = false;

    public void handleWindowFrameResizing(WindowFrame wframe, Rectangle bounds, boolean moving) {

	windowFrameResizing = true;

	if (swtGlassPane != null)
	    swtGlassPane.setOpaque(true);

	FormComponent fc = wframe.getFormComponent();

	boolean usesSnap = (fc != null && fc.getParent() != null && fc.getParent().usesSnapTo());

	int bx = bounds.x;
	int by = bounds.y;
	int bw = bounds.width;
	int bh = bounds.height;

	if (isInSwingMode())
	    by += getMenuHeight();

	if (usesSnap && isInSWTMode()) {
	    by -= getMenuHeight();
	}

	int[] xy = { bx, by };
	adjustEventCoords(xy);
	int[] xy2 = { bx + bw, by + bh };
	if (moving) {
	    xy2[0] = FormComponent.GRIDBAG_UNDEFINED;
	    xy2[1] = FormComponent.GRIDBAG_UNDEFINED;
	} else {
	    adjustEventCoords(xy2);
	}

	int x1 = xy[0];
	int x2 = x1 + bw;
	int y1 = xy[1];
	int y2 = y1 + bh;
	// use fcb to determine which side(s) of the windowframe have moved
	Rectangle fcb = fc.getBoundsRelativeToRoot();

	if (usesSnap && isInSWTMode())
	    fcb.y -= getMenuHeight();

	boolean leftSide = (x1 != fcb.x);
	boolean rightSide = (x2 != fcb.x + fcb.width);
	boolean topSide = (y1 != fcb.y);
	boolean bottomSide = (y2 != fcb.y + fcb.height);

	boolean childSnapTo = false;

	if (usesSnap) {

	    childSnapTo = true;

	    SnapTo tmpSnapTo = SnapTo.calculateSnapTo(leftSide ? x1 : SnapTo.UNCHANGED_SIDE, topSide ? y1 : SnapTo.UNCHANGED_SIDE, rightSide ? x2 : SnapTo.UNCHANGED_SIDE,
		    bottomSide ? y2 : SnapTo.UNCHANGED_SIDE, x1, y1, x2, y2, fc.getParent(), fc, this, SnapTo.MODE_ELEMENT);
	    if (!tmpSnapTo.equals(snapTo)) {
		if (awtControl != null && !awtControl.isDisposed()) {
		    awtControl.redraw();
		}
		if (swtGlassPane != null) {
		    swtGlassPane.redraw();
		}
	    }
	    if (tmpSnapTo.dx != 0) {
		if (leftSide) {
		    bounds.width -= tmpSnapTo.dx;
		    bounds.x += tmpSnapTo.dx;
		} else if (rightSide) {
		    bounds.width += tmpSnapTo.dx;
		}
	    }
	    if (tmpSnapTo.dy != 0) {
		if (topSide) {
		    bounds.height -= tmpSnapTo.dy;
		    bounds.y += tmpSnapTo.dy;
		} else if (bottomSide) {
		    bounds.height += tmpSnapTo.dy;
		}
	    }
	    snapTo = tmpSnapTo;
	}

	if (!childSnapTo && fc.usesSnapTo()) { // container's edges are being
					       // moved
	    SnapTo tmpSnapTo = SnapTo.calculateSnapTo(leftSide ? x1 : SnapTo.UNCHANGED_SIDE, topSide ? y1 : SnapTo.UNCHANGED_SIDE, rightSide ? x2 : SnapTo.UNCHANGED_SIDE,
		    bottomSide ? y2 : SnapTo.UNCHANGED_SIDE, x1, y1, x2, y2, fc, fc, this, SnapTo.MODE_CONTAINER);

	    if (tmpSnapTo != null) {
		if (!tmpSnapTo.equals(snapTo) && awtControl != null && !awtControl.isDisposed()) {
		    awtControl.update();
		}
		if (tmpSnapTo.dx != 0) {
		    if (leftSide) {
			bounds.width -= tmpSnapTo.dx;
			bounds.x += tmpSnapTo.dx;
		    } else if (rightSide) {
			bounds.width += tmpSnapTo.dx;
		    }
		}
		if (tmpSnapTo.dy != 0) {
		    if (topSide) {
			bounds.height -= tmpSnapTo.dy;
			bounds.y += tmpSnapTo.dy;
		    } else if (bottomSide) {
			bounds.height += tmpSnapTo.dy;
		    }
		}
		containerSnapTo = tmpSnapTo;
	    }
	}

	if (awtControl != null && !awtControl.isDisposed()) {
	    getViewportControl().update();
	    awtControl.update();
	}
	handleGridBagToolTip(fc, xy[0], xy[1], xy2[0], xy2[1], true);

	Rectangle wfb = wframe.getBounds();
	Rectangle delta = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
	delta.x -= wfb.x;
	delta.y -= wfb.y;
	delta.width -= wfb.width;
	delta.height -= wfb.height;
	for (int i = 0; i < selectedComps.size(); i++) {
	    WindowFrame wf = getWindowFrame(getSelectedComponent(i), false);
	    if (!wf.equals(wframe)) {
		Rectangle wb = wf.getBoundsCopy();
		wb.x += delta.x;
		wb.y += delta.y;
		wb.width += delta.width;
		wb.height += delta.height;
		wf.setBounds(wb);
	    }
	}
	wframe.setBounds(bounds);
	mainComp.update();

    }

    public void handleWindowFrameResized(WindowFrame frame, Rectangle bounds, FormComponent comp, boolean shiftDown) {

	// root.clearCachedBounds();

	if (comp.isMenuComponent())
	    return;

	if (swtGlassPane != null)
	    swtGlassPane.setOpaque(false);
	windowFrameResizing = false;
	clearGridBagMarker();

	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (fc.isInherited()) {
		displayMessage("Unable to resize inherited object", "Unable to resize inherited object");
		frame.realign();
		setMoveTarget(null);
		setSnapTo(null);
		containerSnapTo = null;
		return;
	    }
	}

	if (snapTo != null || containerSnapTo != null)
	    pauseUpdate(true);

	// bounds is relative to parent element
	snapToGrid(bounds, comp, false);

	if (comp.isRoot() && (comp.isJFrame() || comp.isJDialog() || comp.isA(Shell.class))) {
	    comp.addWindowDecorationSizes(bounds);
	    if (comp.isSWT())
		bounds.height += getMenuHeight();
	}

	boolean multiSel = selectedComps.size() > 1;
	for (int i = 0; i < selectedComps.size(); i++) {

	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);

	    if (fc.isSWTAWTInternal())
		fc = fc.getSWTAWTContainer();

	    Rectangle b2 = bounds;
	    if (multiSel) {
		b2 = fc.getBoundsCopy();
		b2.width = bounds.width;
		b2.height = bounds.height;
	    }

	    FormComponent fcp = fc.getParent();
	    if (fc.isA(TableColumn.class)) {
		fc.setPropertyValue("width", new Integer(b2.width));
	    } else if ("org.eclipse.swt.widgets.TreeColumn".equals(fc.getClassName())) {
		fc.setPropertyValue("width", new Integer(b2.width));
	    } else {
		if (fcp != null && fcp.usesContentPane()) {
		    SwingHelper.subtractVectorBetween((Container) fcp.getComponent(), fc.getComponent(), b2);
		}
		fc.setBounds(b2, true, true, shiftDown);
	    }
	}

	if (snapTo != null || containerSnapTo != null) {
	    LayoutGroup.updateSelectedCompBoundsFromFrames(this);
	    flushActions();
	    pauseUpdate(false);

	    if (containerSnapTo != null) {
		if (snapTo == null) {
		    if (containerSnapTo.hAlignIsHorizontal() || containerSnapTo.vAlignIsVertical() || isCtrlDown()) {
			containerSnapTo.handleMouseUp(FormEditor.this, null, null, null, true);
		    }
		}
		containerSnapTo = null;
	    }

	    if (snapTo != null) {
		snapTo.handleMouseUp(FormEditor.this, getSelectedComponent(), getSelectedComponents(), null, true);
		setSnapTo(null);
	    }
	    setDirtyAndUpdate();
	    setMoveTarget(null);
	}
    }

    public void removeFromOutline(FormComponent comp) {
	try {
	    if (formOutlinePage != null) {
		if (comp.getParent() == null) {
		    if (jiglooPlugin.DEBUG_EXTRA)
			System.err.println("Error in removeFromOutline: " + comp + " has null parent");
		} else {
		    formOutlinePage.removeChildComponent(comp, comp.getParent());
		    if (internalFormOutline != null)
			internalFormOutline.removeChildComponent(comp, comp.getParent());
		}
	    }
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    public boolean hasComponent(FormComponent comp) {
	return components.contains(comp);
    }

    public void removeComponent(FormComponent comp, boolean repairInCode) {
	try {

	    if (components == null || !components.contains(comp))
		return;

	    setNeedsReload(true);
	    if (comp.isSubclassOf(AbstractAction.class)) {
		for (int i = 0; i < components.size(); i++) {
		    FormComponent fc = (FormComponent) components.elementAt(i);
		    if (fc.hasProperty("action") && comp.equals(fc.getPropertyValue("action"))) {
			fc.setPropertyValue("action", "null");
			if (fc.isPropertySet("text"))
			    fc.setPropertyValue("text", fc.getPropertyValue("text"));
			if (fc.isPropertySet("icon"))
			    fc.setPropertyValue("icon", fc.getPropertyValue("icon"));
		    }
		}
	    }

	    if (comp.isContentPane()) {
		comp.setComponent(null);
	    }

	    boolean needsRebuild = false;
	    FormComponent parent = null;
	    if (comp.equals(getMenuBar()) && comp.getParent() == null) {
		extraCompRoot.remove(comp);
		setMenuBar(null, false);
	    } else {
		parent = comp.getParent();
		// if (parent == null)
		// return;
		if (comp.isVisual()) {
		    if (comp.isSwing()) {
			Component cmp = comp.getComponent();
			// cmp will be null if child of non-visible dialog, say
			if (cmp != null && cmp.getParent() != null)
			    cmp.getParent().remove(cmp);
		    } else if (comp.isCWT()) {
			com.cloudgarden.jigloo.typewise.TypewiseManager.removeWidget(parent, comp);
		    } else if (comp.isSWT()) {
			// Control cmp = comp.getComposite();
			Object wid = comp.getControl();
			Composite cmps = null;
			if (wid instanceof Control && !((Control) wid).isDisposed()) {
			    cmps = ((Control) wid).getParent();
			    if (cmps instanceof IWidgetManager) {
				IWidgetManager par = (IWidgetManager) cmps;
				par.hide((Control) wid);
				try {
				    par.layout(true);
				} catch (Throwable t) {
				    jiglooPlugin.handleError(t);
				}
			    } else {
				needsRebuild = true;
			    }
			} else {
			    needsRebuild = true;
			}
		    }
		}
		if (parent != null)
		    parent.remove(comp);
		removeFromOutline(comp);
	    }

	    comp.setAllExistsInCode(false);

	    removeFields(comp);
	    hideWindowFrame(comp);
	    selectedComps.remove(comp);
	    setAlignButtonStates();
	    if (repairInCode)
		comp.repairInCode();
	    comp.dispose();
	    setDirty(true);
	    if (needsRebuild && parent != null) {
		if (parent.isSubclassOf(Item.class)) {
		    parent.rebuildParent(false);
		} else {
		    comp.rebuildParent(false);
		}
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void removeFields(FormComponent comp) {
	if (components == null)
	    return;
	components.remove(comp);
	removedFields.add(comp.getName());
	for (int i = 0; i < comp.getChildCount(); i++) {
	    removeFields(comp.getChildAt(i));
	}
    }

    public void addChildComponent(FormComponent comp, FormComponent parent) {

	addComponent(comp);
	// view.addChildComponent(comp, parent);
	if (formOutlinePage != null)
	    formOutlinePage.addChildComponent(comp, parent);
	if (internalFormOutline != null)
	    internalFormOutline.addChildComponent(comp, parent);
    }

    public FormComponent getComponentByName(String name) {
	return getComponentByName(name, false);
    }

    public FormComponent getComponentByName(String name, boolean ignoreBlock) {
	if (name == null || components == null)
	    return null;
	if (name.equals(EXTRA_COMPONENT_LABEL)) {
	    return nonVisualRoot;
	}
	if (name.equals(NON_VISUAL_LABEL)) {
	    return nonVisualRoot;
	}
	if (name.equals(MENU_COMPONENT_LABEL)) {
	    return extraCompRoot;
	}
	int ind = name.indexOf(" ");
	if (ind != -1)
	    name = name.substring(0, ind);
	if (ignoreBlock) {
	    ind = name.lastIndexOf(".");
	    if (ind != -1)
		name = name.substring(ind + 1);
	}
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    String fcn = fc.getName();
	    if (ignoreBlock) {
		ind = fcn.lastIndexOf(".");
		if (ind != -1)
		    fcn = fcn.substring(ind + 1);
	    }
	    if (name.equals(fcn))
		return fc;
	}
	return null;
    }

    public String getNextAvailableName(FormComponent comp) {
	String name = comp.getShortClassName();
	name = name.substring(0, 1).toLowerCase() + name.substring(1);
	int cnt = 1;
	while (getComponentByName(name + cnt, true) != null)
	    cnt++;
	// System.err.println("GOT NEXT NAME FOR "+comp+", "+(name+cnt));
	// new Exception().printStackTrace();
	return name + cnt;
    }

    /**
     * @param obj
     * @param components
     * @return
     */
    public FormComponent getComponentWithObject(Object obj) {
	if (obj == null || components == null)
	    return null;
	for (int i = 0; i < components.size(); i++) {
	    Object f = components.get(i);
	    if (f instanceof FormComponent) {
		FormComponent fc = (FormComponent) f;
		if (obj.equals(fc.getObject(false)))
		    return fc;
	    }
	}
	return null;
    }

    public void addComponent(FormComponent comp) {
	if (components == null)
	    components = new Vector();

	comp.setEditor(this);

	// System.err.println("*** FED: ADD COMP "+comp+"@"+comp.hashCode()+",
	// par="+comp.getParent()+", contains="+components.contains(comp)+",
	// "+javaFile);
	// new Exception().printStackTrace();
	// System.err.println("COMPS="+components);

	if (components.contains(comp))
	    return;
	boolean needName = false;
	String name = comp.getName();
	if (name == null || name.equals("unknownName")) {
	    needName = true;
	} else {
	    FormComponent fc = getComponentByName(name, false);
	    if (fc != null) {
		needName = true;
	    }
	    // System.err.println("*** FED: ADD found " + fc + ", " + comp);
	}
	if (needName) {
	    // System.err.println("*** NEEDS NEW NAME " + name + ", " + comp +
	    // ", contains=" + components.contains(comp));
	    name = getNextAvailableName(comp);
	    comp.setName(name, false);
	    comp.getLayoutWrapper().setName(null);
	    comp.getLayoutDataWrapper().setName(null);
	    EventWrapper ew = comp.getEventWrapper(false);
	    if (ew != null)
		ew.resetName();
	}

	comp.getLayoutDataWrapper().setFormComponentSimple(comp);
	comp.getLayoutWrapper().setFormComponent(comp);

	// System.err.println("ADDING COMPONENT: NAME " + comp.getName());
	components.add(comp);
	Vector subs = comp.getChildren();
	for (int i = 0; i < subs.size(); i++) {
	    FormComponent sub = (FormComponent) subs.elementAt(i);
	    addComponent(sub);
	}
    }

    public boolean setMenuBar(FormComponent menuBar, boolean dontForce) {
	try {
	    if (this.menuBar != null && dontForce) {
		displayWarning("Can't add MenuBar", "A menu bar has already been set");
		return false;
	    }
	    if (isInSwingMode()) {
		if (!root.canHaveJMenuBar()) {
		    displayError("Error", "Can't add a JMenuBar to this component");
		    return false;
		}
	    } else {

	    }
	    if (menuBar == null) {
		if (isInSwingMode()) {
		    Component rp = (Component) root.getComponent();
		    if (rp != null && rp.getParent() != null && this.menuBar != null) {
			rp.getParent().remove(this.menuBar.getComponent());
		    }
		    root.updateUI();
		} else {
		    ((FormData) menuComp.getLayoutData()).height = 0;
		    menuComp.layout();
		    frameComp.layout();
		}
		selectedComps.remove(this.menuBar);
		setAlignButtonStates();
		this.menuBar = null;
		// reload();
		return false;
	    }
	    this.menuBar = menuBar;

	    getRootComponent().setMenuBar(menuBar);

	    if (isInSwingMode()) {
		menuBar.populateComponents(null, this);
	    } else if (isInSWTMode()) {
		menuBar.populateControls(menuComp, this, true);
	    }
	    addComponent(menuBar);
	    menuBar.setParent((FormComponent) null);
	    menuBar.getChildren();
	    menuBar.initProperties();
	    if (isInSwingMode()) {
		if (root.isJInternalFrame()) {
		    ((JInternalFrame) root.getComponent()).setJMenuBar((JMenuBar) menuBar.getComponent());
		} else if (root.isJFrame()) {
		    ((JFrame) root.getRawComponent()).setJMenuBar((JMenuBar) menuBar.getComponent());
		} else if (root.isJDialog()) {
		    ((JDialog) root.getRawComponent()).setJMenuBar((JMenuBar) menuBar.getComponent());
		} else {
		    Container jpan = root.getComponent().getParent();
		    jpan.add(menuBar.getComponent(), java.awt.BorderLayout.NORTH);
		}
	    } else {
		((FormData) menuComp.getLayoutData()).height = 20;
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		rl.marginBottom = 0;
		rl.marginLeft = 0;
		rl.marginRight = 0;
		rl.marginTop = 0;
		rl.spacing = 0;
		rl.wrap = false;
		((Composite) menuBar.getControl()).setLayout(rl);
		menuComp.layout();
		frameComp.layout();
	    }

	    // if (menuBar != null) {
	    // menuBar.setParent(getExtraCompRoot(), true);
	    // }

	    // make sure the menu bar is layed out, so has non-zero height
	    // redrawRootNow();

	    // then re-calculate the root size
	    // if (root.isPropertySet("size"))
	    // root.setPropertyValueDirect("size",
	    // root.getPropertyValue("size"));

	    return true;
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	return false;
    }

    public FormComponent getMenuBar() {
	return menuBar;
    }

    public FormComponent getNonVisualRoot() {
	if (nonVisualRoot == null) {
	    nonVisualRoot = FormComponent.newFormComponent(this, Object.class.getName());
	    nonVisualRoot.setNonVisualRoot(true);
	    // nonVisualRoot.setEditor(this);
	    nonVisualRoot.setName(NON_VISUAL_LABEL);
	    // nonVisualRoot.setClassName(Object.class.getName());
	}
	return nonVisualRoot;
    }

    public FormComponent getExtraCompRoot() {
	if (extraCompRoot == null) {
	    extraCompRoot = FormComponent.newFormComponent(this, Object.class.getName());
	    extraCompRoot.setExtraCompRoot(true);
	    // extraCompRoot.setEditor(this);
	    extraCompRoot.setName(EXTRA_COMPONENT_LABEL);
	    // extraCompRoot.setClassName(Object.class.getName());
	}
	return extraCompRoot;
    }

    public void realignWindowFrame(FormComponent fc) {
	realignWindowFrame(fc, false);
    }

    public void realignWindowFrame(FormComponent fc, boolean resetMode) {
	if (mouseDown || fc == null || !hasWindowFrame(fc) || isToggling())
	    return;
	if (resetMode)
	    getWindowFrame(fc, false).setMode(WindowFrame.MODE_IDLE);
	// System.err.println("realignWindowFrame "+fc+", "+fc.getBounds());
	showWindowFrame(fc);
    }

    public void realignWindowFrames() {
	if (mouseDown || isToggling())
	    return;
	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (!hasWindowFrame(fc) || isToggling())
		continue;
	    showWindowFrame(fc);
	}
    }

    public int getMenuHeight() {
	// if(true)
	// return 0;
	if (menuBar == null)
	    return 0;
	if (!isInSwingMode())
	    return 20;
	if (root.isJInternalFrame())
	    return 0;
	return menuBar.getBounds().height;
    }

    public int getMenuBarHeight() {
	if (true)
	    return 0;
	if (menuBar == null)
	    return 0;
	if (!isInSwingMode())
	    return 20;
	if (root.isJInternalFrame())
	    return 0;
	return menuBar.getBounds().height;
    }

    public Composite getViewportControl() {
	return mainComp;
    }

    public Color getBGColor() {
	try {
	    String rgbStr = pstore.getString(MainPreferencePage.P_BGCOL);
	    if (rgbStr == null)
		rgbStr = pstore.getDefaultString(MainPreferencePage.P_BGCOL);
	    if (rgbStr != null) {
		RGB rgb = JiglooUtils.getRGB(rgbStr);
		if (rgb == null)
		    return ColorManager.getColor(190, 190, 190);
		return ColorManager.getColor(rgb.red, rgb.green, rgb.blue);
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	return ColorManager.getColor(190, 190, 190);
    }

    private Composite partParent;

    private boolean initialized = false;

    public void createPartControl(Composite parent) {
	// the init method is called before this one

	if (jiglooPlugin.DEBUG)
	    System.err.println("CREATING FORM EDITOR FOR " + javaFile);
	partParent = parent;
	disposed = false;
	setPopulating(true);
	darkGray = ColorManager.getColor(80, 80, 80);
	white = ColorManager.getColor(255, 255, 255);
	black = ColorManager.getColor(0, 0, 0);
	CURSOR_MOVE = CursorManager.getCursor(SWT.CURSOR_SIZEALL);
	CURSOR_DEF = CursorManager.getCursor(SWT.CURSOR_ARROW);
	CURSOR_X = CursorManager.getCursor(CursorManager.ADD_CURSOR);
	CURSOR_NO = CursorManager.getCursor(SWT.CURSOR_NO);
	CURSOR_ARROW = CursorManager.getCursor(SWT.CURSOR_ARROW);

	pstore = jiglooPlugin.getDefault().getPreferenceStore();

	javaFileEditorInput = new FileEditorInput(javaFile);
	formEditorKeyAdapter = new FormEditorKeyAdapter(this);

	if (isJavaEditor) {
	    javaEditor = this;
	}

	if (EDITOR_MODE != 0) {
	    if (!isJavaEditor) {
		javaEditor = new JavaEditor();
	    }
	    // javaEditor.init((IEditorSite) getSite(),
	    // javaFileEditorInput);
	}

	addListeners();

	try {
	    createEditorComposites(partParent);
	    createPartControlInternal(partParent);
	    addMaximizeListener(partParent);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}

	final String abnormalShutdown = jiglooPlugin.getAbnormalShutdownMessage();
	if (abnormalShutdown != null) {
	    MessageDialog.openInformation(getSite().getShell(), "Abnormal shutdown detected", abnormalShutdown);
	}

	combinedUndoManager = new CombinedUndoManager(this);

	// System.out.println("DONE CREATING ED FOR "+javaFile);
	// createAndParse();
    }

    /**
    * 
    */
    public void createPropertyFileEditor(boolean createControl) {
	propertyFileEditor = new PropertyFileEditor();
	try {
	    propertyFileEditor.init(this, propTabItem);
	    bundleManager.setPropertyFileEditor(propertyFileEditor);
	    if (createControl)
		propertyFileEditor.createPartControl(propTabItem.getParent());
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    private boolean created = false;

    private void createAndParse() {

	if (created)
	    return;
	if (!parsingEnabled)
	    return;

	created = true;
	jiglooPlugin.clearErrors();

	if (rootDecoration != null && !rootDecoration.isDisposed()) {
	    rootDecoration.dispose();
	}
	rootDecoration = null;
	if (awtControl != null && !awtControl.isDisposed()) {
	    awtControl.dispose();
	}
	awtControl = null;
	if (eventCatcher != null && !eventCatcher.isDisposed()) {
	    eventCatcher.dispose();
	}
	eventCatcher = null;
	if (frameComp != null && !frameComp.isDisposed()) {
	    frameComp.dispose();
	}
	frameComp = null;
	mainControl = null;

	try {
	    if (jiglooPlugin.DEBUG)
		System.err.println("CREATE AND PARSE " + javaFile);

	    // installLookAndFeel("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
	    // installLookAndFeel("com.jgoodies.plaf.plastic.PlasticLookAndFeel");
	    // installLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");

	    // clear any existing L&F
	    // setLookAndFeel(null);
	    // setLookAndFeelNow();

	    // just to be safe, if on Linux, do almost everything now so that
	    // mainControl will be disabled (otherwise mainControl can be
	    // enabled and the mouseListener on mainComp gets nuttin.

	    setParsing(true);
	    if (!jiglooPlugin.isWindows()) {
		createPartControlInternal2(partParent);
	    }

	    Display.getDefault().asyncExec(new Runnable() {
		public void run() {
		    try {

			try {
			    Class.forName("org.w3c.dom.Node");
			} catch (ClassNotFoundException t) {
			    MessageDialog.openError(getSite().getShell(), "Required class not found",
				    "A required class was not found.\n\n" + "Please download xml.jar from\n" + "http://cloudgarden.soft-gems.net/xml.jar\n" + "or\n"
					    + "http://www.cloudgarden.com/jigloo/xml.jar\n" + "and place it in the eclipse/plugins/com.cloudgarden.jigloo\n" + "folder, and then restart Eclipse.");
			}

			if (jiglooPlugin.isWindows())
			    createPartControlInternal2(partParent);

			if (newRoot != null) {
			    newRoot.setName("this");
			    newRoot.setLayoutDataWrapper(null);
			    newRoot.unsetProperty("layoutData");
			    newRoot.setPropertyValueCode("layout", null);
			    setRootComponent(newRoot);
			    jcp.setRootComponent(newRoot);
			    newRoot.setAllExistsInCode(true);
			}

			if (root == null)
			    return;

			root.markMainTree();

			// set L&F before populating components, because
			// l2fprod's
			// JButtonBar causes an error otherwise
			if (useJavaCodeParser) {
			    setLookAndFeel(jcp.getLookAndFeel());
			    setLookAndFeelNow();
			}

			try {
			    populateFormComponents();
			} catch (Throwable t) {
			    jiglooPlugin.handleError(t);
			}

			setParsing(false);

			if (newRoot != null) {
			    newRoot.addToCode();
			    setDirtyAndUpdate(true, true, true);
			    doSave(getProgressMonitor());
			}

			newRoot = null;

			waitLabel.setVisible(false);
			root.updateUI();
			root.clearDirtyPropertiesForAll();

			redrawRootNow();

			boolean hasGUIInstanceMethod = (isInSWTMode() && jcp.hasMethod("getGUIBuilderInstance_Composite,int", null))
				|| (isInSwingMode() && jcp.hasMethod("getGUIBuilderInstance_", null));

			if (root.hasUnmakeableSuperClass() && !root.hasGetGUIBuilderInstance() && !hasGUIInstanceMethod) {
			    boolean insert = MessageDialog.openConfirm(getSite().getShell(), "Automatically insert a getGUIBuilderInstance method?",
				    "This class extends an abstract, private, or package-protected class,\n" + "and so Jigloo cannot instantiate the parent class.\n\n"
					    + "In cases like this, Jigloo looks for a getGUIBuilderInstance method for\n" + "this class, which returns an instance of this class which has *not* had\n"
					    + "it's GUI initialized. Jigloo can automatically insert a getGUIBuilderInstance\n" + "method now.\n\n"
					    + "Automatically create a getGUIBuilderInstance method now?");
			    if (insert) {
				if (isInSwingMode())
				    jcp.insertGetGUIBuilderSwingMethod();
				else
				    jcp.insertGetGUIBuilderSWTMethod();

				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
					jcp.updateWCBufferNow();
				    }
				});

				setDirty(true);
				final ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
				pmd.setBlockOnOpen(false);
				pmd.setCancelable(true);
				pmd.create();
				final IProgressMonitor monitor = pmd.getProgressMonitor();
				pmd.open();

				doSave(monitor);
				doBuild(monitor);
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
					pmd.close();
					reparseJavaCode(false);
				    }
				});

			    }

			}

		    } catch (Throwable e) {
			jiglooPlugin.handleError(e, "Error in createPartControl for " + javaFileEditorInput);
		    }

		    showOutline();

		    notifyListeners(true, true);
		    initialized = true;
		    
		    new DelayableRunnable(5, true) {
			public void run() {
			    setSelectedComponent(root, true);
//			    root.clearCachedBounds();
//			    realignWindowFrame(root);
			};
		    }.trigger();

		    jiglooPlugin.closeCreationLog();
		    jiglooPlugin.showErrors();
		    jiglooPlugin.showNagPopup(getSite().getShell());

		    AppUtils.updateImports(jcp);

		    if (postOpenRunnable != null) {
			final Runnable fpor = postOpenRunnable;
			// postOpenRunnable.run();
			postOpenRunnable = null;
			new DelayableRunnable(500, true) {
			    public void run() {
				fpor.run();
			    };
			}.trigger();
		    }

		}
	    });

	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private void doReloadEditor() {
	try {
	    if (bundleManager != null)
		bundleManager.refreshPropFilePath();
	    javaCodeSync.cancel();

	    ClassLoaderCache.deleteClassLoader(getProject());
	    clearClassLoader(); // force all classes to be reloaded!!!

	    reparseJavaCode(true);

	    FormComponent root = getRootComponent();
	    if (root != null && root.hasUnmakeableSuperClass() && !root.hasGetGUIBuilderInstance())
		reparseJavaCode(false);

	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    private boolean doUpdates = true;

    private int msgCnt = 0;

    private void setNextWaitLabelMsg(String msg) {
	setWaitLabelMsg(msg);
	// setWaitLabelMsg(msg+(msgCnt++));
    }

    public void setWaitLabelMsg(final String msg) {
	Display.getDefault().asyncExec(new Runnable() {
	    public void run() {
		if (waitLabel != null && !waitLabel.isDisposed()) {
		    waitLabel.setText("\n  Loading Jigloo GUI Editor.\n\n  " + msg);
		    waitLabel.redraw();
		    // System.out.println("SET WAIT MSG "+msg);
		    if (doUpdates)
			waitLabel.update();
		}
	    }
	});
    }

    private void createPartControlInternal(Composite parent) {

	try {
	    if (javaFile == null)
		return;

	    Composite waitParent = primaryComposite.getParent();
	    StackLayout sl = (StackLayout) waitParent.getLayout();
	    sl.topControl = primaryComposite;
	    waitParent.layout();

	    GridLayout gl = new GridLayout(2, false);
	    gl.marginWidth = 2;
	    gl.marginHeight = 2;
	    gl.horizontalSpacing = 2;
	    gl.verticalSpacing = 2;
	    primaryComposite.setLayout(gl);

	    GridData gld;

	    if (usePalette()) {
		palette = new ComponentPalette(primaryComposite, SWT.NULL, this);
		gld = new GridData();
		gld.grabExcessHorizontalSpace = true;
		gld.horizontalSpan = 2;
		gld.horizontalAlignment = GridData.FILL;
		// gld.verticalAlignment = GridData.FILL;
		palette.setLayoutData(gld);
		gld.heightHint = 0;
	    }

	    createVerticalToolbar(primaryComposite);

	    scrollPane = new ScrolledComposite(primaryComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	    gld = new GridData();
	    gld.grabExcessHorizontalSpace = true;
	    gld.grabExcessVerticalSpace = true;
	    gld.horizontalAlignment = GridData.FILL;
	    gld.verticalAlignment = GridData.FILL;
	    scrollPane.setLayoutData(gld);
	    // scrollPane.setBackground(getBGColor());

	    mainComp = new Composite(scrollPane, SWT.NULL);
	    // mainComp = new FocusableComposite(scrollPane, SWT.NULL);
	    mainComp.setBackground(getBGColor());
	    scrollPane.setContent(mainComp);
	    scrollPane.setExpandHorizontal(true);
	    scrollPane.setExpandVertical(true);

	    viewLogButton = new Label(mainComp, SWT.BORDER | SWT.SHADOW_IN);
	    viewLogButton.setFont(FontManager.getFont("Arial", 8, SWT.NORMAL, false, false));
	    viewLogButton.setText("View Log");
	    Point sz1 = viewLogButton.computeSize(-1, -1);
	    int rightPos = BORDER_X - 3 + sz1.x + 10;
	    viewLogButton.setBounds(BORDER_X - 3, 2, sz1.x + 10, sz1.y);
	    viewLogButton.setAlignment(SWT.CENTER);
	    viewLogButton.setToolTipText("Click to pop up a report of Jigloo's progress parsing\n" + "the java file and constructing the GUI editor. May contain\n"
		    + "useful information in diagnosing problems rendering the GUI.");
	    viewLogButton.addMouseListener(new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
		    viewParseLog();
		}
	    });

	    parsingLabel = new CLabel(mainComp, SWT.SHADOW_IN);
	    parsingLabel.setText("Parse when needed.............................");
	    Color bgCol = getBGColor();
	    Color bgInv = ColorManager.getColor(255 - bgCol.getRed(), 255 - bgCol.getGreen(), 255 - bgCol.getBlue());
	    parsingLabel.setBackground(bgCol);
	    parsingLabel.setForeground(bgInv);
	    sz1 = parsingLabel.computeSize(-1, -1);
	    parsingLabel.setBounds(rightPos + 5, 2, sz1.x + 5, sz1.y);
	    rightPos = rightPos + 5 + sz1.x + 5;
	    // parsingLabel.setBounds(2, 2, 120, 18);
	    parsingLabel.setVisible(false);

	    boolean licLabVis = false;
	    if (!jiglooPlugin.getDefault().licenseValid()) {
		licLabVis = true;
	    }
	    licenseLabel = new Label(mainComp, SWT.NULL);
	    licenseLabel.setVisible(licLabVis);
	    licenseLabel.setFont(FontManager.getFont("Arial", 11, SWT.BOLD, false, false));
	    licenseLabel.setText("Non-Commercial Version of Jigloo");
	    licenseLabel.setAlignment(SWT.CENTER);
	    Point sz = licenseLabel.computeSize(-1, -1);
	    licenseLabel.setBackground(ColorManager.getColor(50, 50, 50));
	    licenseLabel.setForeground(ColorManager.getColor(250, 250, 250));
	    // rightPos = 130;
	    licenseLabel.setBounds(rightPos + 5, 2, sz.x + 4, sz.y);

	    waitLabel = new Label(mainComp, SWT.BORDER);
	    waitLabel.setBounds(BORDER_X, BORDER_Y, 200, 100);
	    waitLabel.setText("\n  Click here to start parsing...");
	    waitLabel.setAlignment(SWT.LEFT);
	    waitLabel.setBackground(ColorManager.getColor(250, 250, 250));

	    // primaryComposite.layout();
	    scrollPane.layout();

	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    /**
     * @param primaryComposite2
     */
    private void createVerticalToolbar(Composite primaryComposite2) {
	Composite tb = new Composite(primaryComposite, SWT.FLAT);
	GridLayout gl = new GridLayout(1, false);
	gl.marginWidth = 1;
	gl.marginHeight = 1;
	gl.horizontalSpacing = 0;
	gl.verticalSpacing = 1;
	tb.setLayout(gl);

	LightButton ti;

	ti = new LightButton(tb, "prefs.gif", "Open Jigloo preferences editor");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new OpenJiglooPreferencePageAction().run(null);
	    }
	});

	ti = new LightButton(tb, "help_icon.gif", "Display Jigloo documentation");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		BusyIndicator.showWhile(null, new Runnable() {
		    public void run() {
			WorkbenchHelp.getHelpSupport().displayHelp("/com.cloudgarden.jigloo/toc.xml");
		    }
		});
	    }
	});

	ti = new LightButton(tb, "alignTop.gif", "Align tops of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_TOP).run();
	    }
	});
	int i = 0;

	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignVCenter.gif", "Align baselines of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_V_CENTER).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignBottom.gif", "Align bottoms of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_BOTTOM).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignLeft.gif", "Align left sides of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_LEFT).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignHCenter.gif", "Align centers of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_H_CENTER).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignRight.gif", "Align right sides of selected elements");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_RIGHT).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "sameHeights.gif", "Make selected elements same height");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_SAME_HEIGHT).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "sameWidths.gif", "Make selected elements same widths");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_SAME_WIDTH).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "spaceVert.gif", "Space selected elements evenly vertically");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_SPACE_VERT).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "spaceHoriz.gif", "Space selected elements evenly horizontally");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_SPACE_HORIZ).run();
	    }
	});
	alignButtons[i++] = ti;

	ti = new LightButton(tb, "alignToGrid.gif", "Align to grid");
	ti.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new FormAlignAction(FormEditor.this, FormAlignAction.ALIGN_TO_GRID).run();
	    }
	});
	alignButtons[i++] = ti;

	GridData gld = new GridData();
	gld.grabExcessHorizontalSpace = false;
	gld.grabExcessVerticalSpace = true;
	gld.horizontalAlignment = GridData.BEGINNING;
	gld.verticalAlignment = GridData.FILL;
	tb.setLayoutData(gld);
    }

    public void setEditorMode(JavaCodeParser jcp) {
	int type = jcp.getRootComponent().getClassType();
	if (type == FormComponent.TYPE_SWING) {
	    mode = MODE_AWT_SWING;
	} else if (type == FormComponent.TYPE_SWT) {
	    mode = MODE_SWT;
	} else if (harness != null && type == harness.getClassID()) {
	    mode = MODE_ANDROID;
	} else if (type == FormComponent.TYPE_CWT) {
	    mode = MODE_CWT;
	} else if (type == FormComponent.TYPE_GWT) {
	    mode = MODE_GWT;
	}
    }

    private Composite outlineComposite = null;

    private void captureInternalSashWeights() {
	if (!editorMaximized)
	    return;
	Control[] ctrls = formEditorSash.getChildren();
	int h1, h2;
	if (ctrls != null && ctrls.length >= 2) {
	    if (formEditorSash.getOrientation() == SWT.VERTICAL) {
		h1 = ctrls[0].getSize().y;
		h2 = ctrls[1].getSize().y;
	    } else {
		h1 = ctrls[0].getSize().x;
		h2 = ctrls[1].getSize().x;
	    }
	    if (h1 + h2 != 0) {
		internalSashWeights[0] = (int) 100 * h1 / (h1 + h2);
		internalSashWeights[1] = (int) 100 * h2 / (h1 + h2);
	    }
	}
    }

    private void createPartControlInternal2(Composite parent) {
	try {
	    if (javaFile == null)
		return;

	    if (mainComp == null || mainComp.isDisposed())
		return;

	    // System.out.println("INSERT SWT RES="+insertSWTResource+",
	    // "+javaFile);
	    if (insertSWTResource) {
		String pkg = getPackageName();
		insertedClasses.remove(pkg + ";com/cloudgarden/resource/SWTResourceManager");
		insertClass(getPackageName(), "com/cloudgarden/resource/SWTResourceManager");
		doBuild(null);
		insertSWTResource = false;
	    }

	    mainComp.redraw();
	    mainComp.update();

	    if (!useXMLParser) {
		setWaitLabelMsg("Parsing code...");
		jcp = new JavaCodeParser(this);
		if (parsingEnabled)
		    parseJavaCode();
		if (updateCodeStyleAction != null)
		    updateCodeStyleAction.setText(getUpdateActionText());
		setWaitLabelMsg("Parsed code...");
		setEditorMode(jcp);
		flashViewLogButton();
	    } else {
		FileEditorInput input = (FileEditorInput) getEditorInput();
		IFile inFile = formFile;

		java.io.File file = new java.io.File(inFile.getLocation().toOSString());
		if (!file.exists()) {
		    displayError("Unable to find file", "Unable to find file " + file);
		}

		parser = new XMLParser();
		parser.parse(file, this);
		if ("SWT".equals(parser.getStyle()))
		    mode = MODE_SWT;
		else
		    mode = MODE_AWT_SWING;
		jcp = new JavaCodeParser(this);
		parseJavaCode();
	    }

	    if (parser != null && parser.isNetbeans()) {
		setDirty(true);
		initialJavaUpdate = true;
	    }

	    if (useJavaCodeParser && !updateJavaCode)
		initialJavaUpdate = true;

	    if (usePalette()) {
		setWaitLabelMsg("Filling component palette...");
		palette.setMode(mode);
		fillComponentPalette();
		palette.layout();
		setWaitLabelMsg("Filled component palette...");
	    }

	    // parseProgress = new ProgressBar(mainComp, SWT.INDETERMINATE);
	    // parseProgress.setBounds(20, 4, 100, 12);
	    // parseLabel = new Label(mainComp, SWT.NULL);
	    // parseLabel.setBounds(130, 4, 100, 12);
	    // parseLabel.setBackground(bgColor);
	    // parseLabel.setText("Parsing...");

	    // mainComp.setBackground(getBGColor());

	    prefPropChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
		    handlePreferenceChange(evt);
		}
	    };
	    pstore.addPropertyChangeListener(prefPropChangeListener);
	    // DON'T set a layout for the container of the root composite
	    // (as the WindowFrame windows are children of that composite
	    // and will be layed-out by it's layout).
	    // scrollPane.setContent(mainComp);
	    // scrollPane.setExpandHorizontal(true);
	    // scrollPane.setExpandVertical(true);

	    if (!useXMLParser) {
		setWaitLabelMsg("Getting components...");
		root = jcp.getRootComponent();
		menuBar = jcp.getMenuBar();
		nonVisualRoot = jcp.getNonVisualRoot();
		addParentlessFields();
	    } else {
		root = parser.getRootFormComponent();
		isNetbeans = parser.isNetbeans();
	    }

	    // System.out.println("OPENING FORM " + inFile.getName());
	    root.setName(getRootName(), false);

	    removeUnusedComponents();

	    mainComp.addMouseListener(mouseAd);
	    mainComp.addMouseMoveListener(mmList);
	    mainComp.addMouseTrackListener(mtList);
	    mainComp.addKeyListener(formEditorKeyAdapter);

	    if (useXMLParser) {
		menuBar = parser.getMenuBar();
		nonVisualRoot = parser.getNonVisualRoot();
		setLookAndFeel(parser.getLookAndFeel());
	    }

	    // populateFormComponents();

	    // create internalOutline *after* populating form components,
	    // since creating the internalOutline causes the labels to be
	    // created
	    // which causes getLayoutWrapper to be called *before* the
	    // components
	    // exist, so that any default layouts are not seen

	    if (formEditorSash != null) {
		setWaitLabelMsg("Creating form editor...");
		try {
		    int sstyle = SWT.VERTICAL | SWT.BORDER;
		    if (EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ) {
			sstyle = SWT.HORIZONTAL | SWT.BORDER;
		    }
		    setNextWaitLabelMsg("Creating form editor...");
		    SashForm sideSash = new SashForm(formEditorSash, sstyle);
		    formEditorSash.setWeights(new int[] { 100, 0 });

		    formEditorSash.layout(true);

		    createInternalOutlines(sideSash);

		    internalFormView = new FormView();
		    internalFormView.createPartControl(sideSash);
		    internalFormView.setFormEditor(this);

		    if (editorMaximized)
			formEditorSash.setWeights(internalSashWeights);
		    else
			formEditorSash.setWeights(new int[] { 100, 0 });

		    Control[] children = formEditorSash.getChildren();
		    for (int i = 0; i < children.length; i++) {
			Control control = children[i];
			if (control instanceof Sash) {
			    control.setEnabled(editorMaximized);
			}
		    }

		    // use tabbed property editor if editor mode is vertical
		    // split or tabbed panes
		    internalFormView.showTabs(EDITOR_MODE == EDITOR_MODE_SPLIT_VERT || isInTabbedMode());

		    if (EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ)
			sideSash.setWeights(new int[] { 25, 75 });

		    setNextWaitLabelMsg("Creating form editor...");
		    // addMaximizeListener(parent);

		    internalFormOutline.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
			    captureInternalSashWeights();
			}
		    });
		} catch (Throwable ex) {
		    jiglooPlugin.handleError(ex);
		}
	    }

	    setNextWaitLabelMsg("Creating form editor...");
	    createActions();

	    if (EDITOR_MODE != 0) {
		mainComp.addFocusListener(focusListener);
		mainComp.addMouseListener(mouseFocusListener);

		getJavaEditorControl().addFocusListener(focusListener);
		getJavaEditorControl().addMouseListener(mouseFocusListener);

		// javaEdSelProv = super.getSelectionProvider();

	    } else {
		getSite().setSelectionProvider(this);
	    }

	    setNextWaitLabelMsg("Creating form editor...");
	    getSite().getPage().addSelectionListener(this);

	    if (EDITOR_MODE != 0 && javaEditor != null && !isJavaEditor) {
		javaEditor.addPropertyListener(propChangeList);
	    }

	    setWaitLabelMsg("Updating working copy...");
	    if (initialJavaUpdate) {
		isSaving = true;
		commitWorkingCopy(getProgressMonitor());
		isSaving = false;
	    } else {
		updateWorkingCopyInternal();
	    }

	    if (javaEditor.isDirty()) {
		setDirty(true);
	    }

	    setWaitLabelMsg("Filling form...");

	    if (parser != null) {
		parser.dispose();
		parser = null;
	    }

	    if (jcp != null && jcp.shouldUseSWTResMan()) {
		MessageDialog.openInformation(getSite().getShell(), "Convert to using SWTResourceManager?",
			"This class does not appear to use the SWTResourceManager to\n" + "set fonts, colors or images. It is recommended that you convert\n"
				+ "to using the SWTResourceManager, which caches resources and\n" + "disposes of them when they are no longer required.\n\n"
				+ "To do the conversion, right-click anywhere on the form editor and\n" + "choose:\n\n" + "Source->Convert to using SWTResourceManager\n\n");
	    }
	    setNeedsReload(true);
	    reloadOutline();

	    registerActions("Form");
	    showInternalOutline(false);

	    marker = new Marker(this);

	    // setLookAndFeelNow();

	} catch (Throwable e) {
	    e.printStackTrace();
	    jiglooPlugin.handleError(getSite().getShell(), "Error initializing FormEditor for " + javaFile, "Error initializing FormEditor for " + javaFile, e);
	    jiglooPlugin.handleError(e);
	}
    }

    private void createInternalOutlines(SashForm sideSash) {
	int tbs = SWT.HORIZONTAL | SWT.FLAT;
	int nCols = 1;
	int gds = GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL;
	if (EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ) {
	    tbs = SWT.VERTICAL | SWT.FLAT;
	    gds = GridData.FILL_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL;
	    nCols = 2;
	}

	Composite c1 = new Composite(sideSash, SWT.NULL);
	c1.setLayout(new GridLayout());
	if (false) {
	    GridLayout grl = new GridLayout(nCols, false);
	    grl.marginHeight = grl.marginWidth = grl.verticalSpacing = grl.horizontalSpacing = 0;
	    c1.setLayout(grl);

	    internalFormOutline = new FormContentOutlinePage(this, true);
	    ToolBar itb = new ToolBar(c1, tbs);
	    ToolBarManager internalTBM = new ToolBarManager(itb);
	    setNextWaitLabelMsg("Creating form editor...");
	    itb = internalTBM.createControl(c1);

	    internalFormOutline.createControl(c1);
	    internalFormOutline.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	    internalFormOutline.fillLocalToolBar(internalTBM);

	    internalTBM.update(true);
	    internalFormOutline.reload();

	    GridData gd = new GridData(gds);
	    if (EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ)
		gd.widthHint = 23;
	    itb.setLayoutData(gd);
	    c1.layout();

	} else {

	    // TODO: remove duplicate code
	    GridData gd = new GridData(gds);
	    if (EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ)
		gd.widthHint = 23;

	    internalOutlineContainer = new Composite(c1, SWT.NONE);
	    StackLayout outlineLayout = new StackLayout();
	    internalOutlineContainer.setLayout(outlineLayout);
	    internalOutlineContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

	    IViewSite viewSite = (IViewSite) getSite().getPage().getViewReferences()[0].getView(true).getSite();
	    // IViewSite viewSite = (IViewSite)
	    // getSite().getPage().getViews()[0].getSite();

	    Composite internalFormContainer = new Composite(internalOutlineContainer, SWT.NONE);
	    GridLayout grl = new GridLayout(nCols, false);
	    grl.marginHeight = grl.marginWidth = grl.verticalSpacing = grl.horizontalSpacing = 0;
	    internalFormContainer.setLayout(grl);

	    internalFormOutline = new FormContentOutlinePage(this, true);

	    final ToolBarManager internalTBM = new ToolBarManager();
	    ToolBar itb = internalTBM.createControl(internalFormContainer);
	    itb.setLayoutData(gd);

	    // IPageSite pageSite = new PageSite(viewSite) {
	    // @Override
	    // public IActionBars getActionBars() {
	    // SubActionBars subBars = new SubActionBars(super.getActionBars())
	    // {
	    // @Override
	    // public IToolBarManager getToolBarManager() {
	    // return internalTBM;
	    // }
	    // };
	    // return subBars;
	    // }
	    // };

	    // internalFormOutline.init(pageSite);
	    internalFormOutline.createControl(internalFormContainer);
	    internalFormOutline.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	    internalFormOutline.fillLocalToolBar(internalTBM);
	    internalTBM.update(true);

	    Composite internalJavaContainer = new Composite(internalOutlineContainer, SWT.NONE);
	    GridLayout grl2 = new GridLayout(nCols, false);
	    grl2.marginHeight = grl2.marginWidth = grl2.verticalSpacing = grl2.horizontalSpacing = 0;
	    internalJavaContainer.setLayout(grl2);
	    internalJavaContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

	    final ToolBarManager internalTBM2 = new ToolBarManager();
	    ToolBar itb2 = internalTBM2.createControl(internalJavaContainer);
	    itb2.setLayoutData(gd);

	    internalJavaOutline = new JavaOutlinePage("", this);
	    IPageSite pageSite2 = new PageSite(viewSite) {
		@Override
		public IActionBars getActionBars() {
		    SubActionBars subBars = new SubActionBars(super.getActionBars()) {
			@Override
			public IToolBarManager getToolBarManager() {
			    return internalTBM2;
			}
		    };
		    return subBars;
		}
	    };
	    internalJavaOutline.init(pageSite2);
	    internalJavaOutline.createControl(internalJavaContainer);
	    internalJavaOutline.setInput(getInputJavaElement());
	    internalJavaOutline.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	    internalTBM2.update(true);
	}
    }

    // brings either the internalFormOutline or the internalJavaOutline to the
    // top
    private void showInternalOutline(boolean javaOutline) {
	if (internalOutlineContainer == null) {
	    return;
	}
	if (javaOutline) {
	    ((StackLayout) internalOutlineContainer.getLayout()).topControl = internalJavaOutline.getControl().getParent();
	} else {
	    ((StackLayout) internalOutlineContainer.getLayout()).topControl = internalFormOutline.getControl().getParent();
	}
	internalOutlineContainer.layout();
    }

    private void addParentlessFields() {
	Vector pf = jcp.getParentlessFields();
	for (int i = 0; i < pf.size(); i++) {
	    FormComponent fc = (FormComponent) pf.elementAt(i);
	    try {
		if (true || fc.isInMainTree()) {
		    getExtraCompRoot().add(fc);
		}
	    } catch (Throwable t) {
		jiglooPlugin.handleError(t);
	    }
	}
    }

    private void addMaximizeListener(Composite parent) {

	parent.addControlListener(new ControlAdapter() {
	    public void controlResized(ControlEvent e) {
		try {
		    IEditorReference[] refs = getEditorSite().getPage().getEditorReferences();
		    for (int i = 0; i < refs.length; i++) {
			IEditorReference ref = refs[i];
			if (FormEditor.this.equals(ref.getEditor(false))) {
			    if (getEditorSite().getPage().getPartState(ref) == IWorkbenchPage.STATE_MAXIMIZED) {
				editorMaximized = true;
			    } else {
				editorMaximized = false;
			    }
			}
		    }
		    if (formEditorSash.getChildren().length == 3) {
			if (editorMaximized) {
			    formEditorSash.setWeights(internalSashWeights);
			} else {
			    formEditorSash.setWeights(new int[] { 100, 0 });
			}
		    }
		    Control[] children = formEditorSash.getChildren();
		    for (int i = 0; i < children.length; i++) {
			Control control = children[i];
			if (control instanceof Sash) {
			    control.setEnabled(editorMaximized);
			}
		    }
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		}
	    }
	});
    }

    private StringBuffer[] logText = new StringBuffer[] { new StringBuffer(), new StringBuffer(), new StringBuffer() };

    public void clearLogText() {
	logText[0].delete(0, logText[0].length());
	logText[1].delete(0, logText[1].length());
	logText[2].delete(0, logText[2].length());
	if (viewLogDialog != null)
	    viewLogDialog.updateLogText("");
    }

    public void addToLog(String txt) {
	// System.out.println("JIGLOO LOG: "+txt);
	if (txt.startsWith("Error") || txt.startsWith("error") || txt.startsWith("Warning") || txt.startsWith("warning")) {
	    logText[0].append("*** " + txt + "\n");
	} else if (txt.startsWith("Instantiated")) {
	    logText[2].append(txt + "\n");
	} else if (txt.startsWith("GUI initialization method")) {
	    logText[1].insert(0, txt + "\n\n");
	} else {
	    logText[1].append(txt + "\n");
	}
	if (viewLogDialog != null)
	    viewLogDialog.updateLogText(getLogText());
    }

    private String getLogText() {
	String txt = "";
	if (logText[0].length() != 0)
	    txt += "\nERRORS...\n\n" + logText[0];
	if (logText[1].length() != 0)
	    txt += "\nParsing...\n\n" + logText[1];
	if (logText[2].length() != 0)
	    txt += "\nInstantiating...\n\n" + logText[2];
	return txt;
    }

    private Label waitLabel;

    private void createEditorComposites(Composite parent) throws Exception {

	showOutline();

	Composite waitParent = null;
	if (EDITOR_MODE == 0) {
	    primaryComposite = new Composite(waitParent, SWT.NULL);

	} else if (isInTabbedMode()) {

	    formEditorSash = new SashForm(parent, SWT.HORIZONTAL);
	    formEditorSash.setOrientation(SWT.HORIZONTAL);

	    formCodeTabFolder = new CTabFolder(formEditorSash, SWT.BOTTOM);

	    formTabItem = new CTabItem(formCodeTabFolder, SWT.NULL);
	    formTabItem.setText("GUI Editor");

	    codeTabItem = new CTabItem(formCodeTabFolder, SWT.NULL);
	    codeTabItem.setText("Source");

	    propTabItem = new CTabItem(formCodeTabFolder, SWT.NULL);
	    propTabItem.setText("Property file");

	    waitParent = new Composite(formCodeTabFolder, SWT.NULL);
	    formTabItem.setControl(waitParent);
	    primaryComposite = new Composite(waitParent, SWT.NULL);

	    formCodeTabFolder.setSelection(1);
	    reverseOutline = false;

	    // don't call registerActions here because it calls all kinds of
	    // errors
	    textEditorActive = true;
	    propertyEditorActive = formEditorActive = false;

	    // showOutlineInternal();
	    showOutline();
	    parsingEnabled = false;

	} else {

	    formCodeTabFolder = new CTabFolder(parent, SWT.BOTTOM);
	    parent = formCodeTabFolder;

	    formTabItem = new CTabItem(formCodeTabFolder, SWT.NULL);
	    formTabItem.setText("GUI/Java Editor");

	    propTabItem = new CTabItem(formCodeTabFolder, SWT.NULL);
	    propTabItem.setText("Property file");

	    if (EDITOR_MODE == EDITOR_MODE_SPLIT_VERT) {
		formEditorSash = new SashForm(parent, SWT.HORIZONTAL);
		formEditorSash2 = new CustomSashForm(formEditorSash, SWT.VERTICAL);
	    } else {
		formEditorSash = new SashForm(parent, SWT.VERTICAL);
		formEditorSash2 = new CustomSashForm(formEditorSash, SWT.HORIZONTAL);
	    }
	    formTabItem.setControl(formEditorSash);
	    waitParent = new Composite(formEditorSash2, SWT.NULL);
	    primaryComposite = new Composite(waitParent, SWT.NULL);
	    primaryComposite.addControlListener(new ControlListener() {
		public void controlMoved(ControlEvent e) {
		}

		public void controlResized(ControlEvent e) {
		    int[] wts = formEditorSash2.getWeights();
		    jiglooPlugin.getDefault().setPreference(MainPreferencePage.P_SASH_WEIGHT, wts[0] * 100 / (wts[0] + wts[1]));
		}

	    });
	    formCodeTabFolder.setSelection(0);

	}

	if (formCodeTabFolder != null) {
	    formCodeTabFolder.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
		    if (e.item != null) {
			if (e.item.equals(codeTabItem))
			    handleEditorTabSelected("Java");
			else if (e.item.equals(formTabItem))
			    handleEditorTabSelected("Form");
			else if (e.item.equals(propTabItem))
			    handleEditorTabSelected("Property");
			else if (e.item instanceof CTabItem) {
			    handleEditorTabSelected(((CTabItem) e.item).getText());
			}
		    }
		}
	    });
	}

	bundleManager = new BundleManager(this);
	bundleManager.createPropertyFileIfNeeded();

	try {
	    if (bundleManager.getPropertyFile().exists()) {
		createPropertyFileEditor(true);
		bundleManager.reload();
	    }
	} catch (Throwable t) {
	    t.printStackTrace();
	}

	StackLayout sl = new StackLayout();
	waitParent.setLayout(sl);
	Composite waitBG = new Composite(waitParent, SWT.NONE);
	waitBG.setBackground(getBGColor());
	if (isInTabbedMode()) {
	    if (isJavaEditor) {
		FormEditor.super.createPartControl(formCodeTabFolder);
	    } else {
		javaEditor.createPartControl(formCodeTabFolder);
	    }
	    int cc = formCodeTabFolder.getChildren().length;
	    codeTabItem.setControl(formCodeTabFolder.getChildren()[cc - 1]);
	} else if (EDITOR_MODE == EDITOR_MODE_SPLIT_VERT || EDITOR_MODE == EDITOR_MODE_SPLIT_HORIZ) {
	    if (isJavaEditor) {
		FormEditor.super.createPartControl(formEditorSash2);
	    } else {
		javaEditor.createPartControl(formEditorSash2);
	    }
	    int wt = jiglooPlugin.getIntPreference(MainPreferencePage.P_SASH_WEIGHT, 100, 0);
	    formEditorSash2.setWeights(new int[] { wt, 100 - wt });
	}

	javaEditorControl = getJavaEditorControl();

	javaEditorControl.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		// handleJavaCodeChanged(true, null);
	    }
	});
	javaEditorControl.addMouseListener(new MouseAdapter() {
	    public void mouseDown(MouseEvent e) {
		registerActions("Java");
		showInternalOutline(true);
		handleCursorMovedInEditor(e);
	    }
	});
	javaEditorControl.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		handleKeyPressInTextEditor(e);
		if (e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_LEFT || e.keyCode == SWT.ARROW_RIGHT || e.keyCode == SWT.ARROW_UP) {
		    handleCursorMovedInEditor(null);
		}
	    }
	});

    }

    public CTabFolder getFormCodeTabFolder() {
	return formCodeTabFolder;
    }

    private static boolean updatingClass = false;

    public static boolean isUpdatingClass() {
	return updatingClass;
    }

    public void handleEscape() {
	setCurrentAction(null);
	if (marker != null)
	    marker.hide();
	setMoveTarget(null);
	setWindowFramesMoveMode(false);
	if (addFrame != null)
	    addFrame.setVisible(false);
	setCursor(getDefCursor());
	mouseDown = false;
	realignWindowFrames();
	hitResult = null;
	if (awtControl != null && !awtControl.isDisposed()) {
	    awtControl.createImage();
	    awtControl.redraw();
	}
	getGridEdgeManager().clearGreenMarker();
    }

    private void addListeners() {
	registerFormEditor(this);

	partListener = new IPartListener() {

	    public void partActivated(IWorkbenchPart part) {
		// System.out.println("PART ACTIVATED "+part);
		if (!part.equals(FormEditor.this)) {
		    if ((part instanceof IEditorPart) && !(part instanceof FormEditor))
			deactivated();
		    return;
		}
		// activator.trigger();
		activated();
		getFormView();
		structSel = null;
		notifyNow(false);
	    }

	    public void partBroughtToTop(IWorkbenchPart part) {
		// System.out.println("PART TO TOP "+part);
		if (!part.equals(FormEditor.this)) {
		    return;
		}
		// an editor can be created, and when it is selected in
		// Package Explorer it will be brought to top, but unless
		// createAndParse is called, the form editor will be blank.

		// createAndParse();
		// activator.trigger();
		activated();
	    }

	    public void partClosed(IWorkbenchPart part) {
		// System.out.println("PART CLOSED "+part);
		if (!part.equals(FormEditor.this))
		    return;

		hideFormViewNow();
		// pass the focus back to the outline view so that the next
		// editor will be activated
		getSite().getPage().activate(getSite().getPage().findView("org.eclipse.ui.views.ContentOutline")); // v4.0.0

	    }

	    public void partDeactivated(IWorkbenchPart part) {
		if (!part.equals(FormEditor.this))
		    return;
		// hideFormViewNow();
	    }

	    public void partOpened(IWorkbenchPart part) {
		// System.out.println("PART OPENED "+part);
		if (!part.equals(FormEditor.this))
		    return;
		activated();
		// if (isDirty() && extractJavaEditor(part)) {
		// updateWorkingCopy(false);
		// }
		// activator.trigger();
	    }

	};
	getEditorSite().getPage().addPartListener(partListener);

	if (propChangeList == null) {
	    propChangeList = new IPropertyListener() {
		public void propertyChanged(Object source, int propId) {
		    if (propId == PROP_DIRTY) {
			if (!javaEditor.isDirty())
			    return;
			firePropertyChange(PROP_DIRTY);
		    }
		}
	    };
	}

    }

    private void handlePreferenceChange(PropertyChangeEvent evt) {
	String prop = evt.getProperty();
	if (prop.equals(MainPreferencePage.P_BGCOL)) {
	    Color bgCol = getBGColor();
	    mainComp.setBackground(bgCol);
	    parsingLabel.setBackground(bgCol);
	    Color bgInv = ColorManager.getColor(255 - bgCol.getRed(), 255 - bgCol.getGreen(), 255 - bgCol.getBlue());
	    parsingLabel.setForeground(bgInv);
	}
	if (prop.equals(MainPreferencePage.P_ADVANCED_PROPS)) {
	    if (view != null) {
		((FormView) view).rebuild();
		internalFormView.rebuild();
		notifyListeners(true, true);
	    }
	    showFormViewNow();
	}
	if (prop.equals(MainPreferencePage.P_PARSE_DELAY)) {
	    javaCodeSync.setDelay(getParseDelay());
	}
	if (prop.equals(MainPreferencePage.P_PALETTE)) {
	    updatePaletteSize();
	}
	if (prop.equals(MainPreferencePage.P_OK_NV_CLASSES) || prop.equals(MainPreferencePage.P_NOT_OK_NV_CLASSES)) {
	    jiglooPlugin.getDefault().clearAllowedClasses();
	}
	if (prop.equals(MainPreferencePage.P_GETTERS) || prop.equals(MainPreferencePage.P_USE_BRACES)) {
	    updateCodeStyleAction.setText(getUpdateActionText());
	}
	if (prop.equals(MainPreferencePage.P_LICENSE_CODE)) {
	    if (jiglooPlugin.getDefault().licenseValid())
		licenseLabel.setVisible(false);
	    else
		licenseLabel.setVisible(true);
	}
	if (prop.equals(MainPreferencePage.P_PALETTE_CLASSES)) {
	    try {
		palette.clear();
		paletteClasses = null;
		fillComponentPalette();
		palette.updateUI();
		palette.setMode(mode);
	    } catch (Throwable t) {
		jiglooPlugin.handleError(t);
	    }
	}
    }

    private String getUpdateActionText() {
	if (jcp != null) {
	    if (jcp.usesGetters(null)) {
		return "Re-arrange code in current coding style (use getters)";
	    } else {
		if (jiglooPlugin.useTaggedComments())
		    return "Re-arrange code in current coding style (in tagged blocks)";
		else if (jiglooPlugin.useBraces())
		    return "Re-arrange code in current coding style (in bracketed blocks)";
		else
		    return "Re-arrange code in current coding style (in blocks)";
	    }
	}
	return "Re-arrange code in current coding style";
    }

    public void setRootName(String rootName) {
	this.rootName = rootName;
    }

    public String getRootName() {
	if (rootName != null)
	    return rootName;
	String name = ((FileEditorInput) getEditorInput()).getName();
	int pos = name.lastIndexOf(".");
	if (pos < 0) {
	    setRootName(name);
	} else {
	    setRootName(name.substring(0, pos));
	}
	return rootName;
    }

    private void setParsing(boolean parsing) {
	if (parsing) {
	    setCursor(CursorManager.getCursor(SWT.CURSOR_WAIT));
	    // parseProgress.setVisible(true);
	    if (parsingLabel != null) {
		parsingLabel.setText("Parsing...");
		parsingLabel.setImage(ImageManager.getImage("parsing_now.gif"));
		parsingLabel.setVisible(true);
		parsingLabel.redraw();
		if (doUpdates)
		    parsingLabel.update();
	    }
	} else {
	    setCursor(CursorManager.getCursor(SWT.CURSOR_ARROW));
	    if (parsingEnabled) {
		if (parsingLabel != null) {
		    parsingLabel.setVisible(false);
		    parsingLabel.redraw();
		    if (doUpdates)
			parsingLabel.update();
		}
	    } else {
		// update the label
		enableParsing(false);
	    }
	}
    }

    private boolean needsReparse = false;

    public void reparseJavaCode(boolean reloadRoot) {

	clearCachedBounds();

	if (!activated) {
	    needsReparse = true;
	    return;
	}

	useJava6GroupLayout = null;
	boolean wasTextEdActive = textEditorActive;

	bundleManager.reload();
	// v4.0M3
	hideWindowFrames(selectedComps);
	String selCompName = null;
	String selCompParName = null;
	if (getSelectedComponent() != null) {
	    selCompName = getSelectedComponent().getName();
	    if (getSelectedComponent().getParent() != null)
		selCompParName = getSelectedComponent().getParent().getName();
	}

	selectedComp = null;
	setMoveTarget(null);

	// FORCE A ROOT RELOAD v4.0.0
	reloadRoot = true;

	if (!parsingEnabled || (isInTabbedMode() && !"Form".equals(activeEditorType))) {
	    setStatus("Detected code change, but parsing disabled");
	    getWorkingCopy();
	    needsReparse = true;
	    return;
	}

	// JiglooUtils.setTimeMarker();
	// JiglooUtils.printTimeLapse("reparse code");

	needsReparse = false;
	if (jcp != null && root != null) {

	    setWaitCursor();

	    setParsing(true);

	    jcp.setRootComponent(null);

	    if (reloadRoot) {
		disposeComponents();
		String ctp = jcp.getClassNameToParse();
		jcp.dispose();
		jcp = new JavaCodeParser(this);
		jcp.setClassNameToParse(ctp);
		setRootName("this");
		rootObject = null;
	    }

	    // this is just so that calls to setDirty are not accepted
	    jcp.setParsing(true);

	    if (reloadRoot) {
		if (frameComp != null)
		    frameComp.dispose();
		frameComp = null;

		if (awtControl != null)
		    awtControl.dispose();
		awtControl = null;
		if (eventCatcher != null)
		    eventCatcher.dispose();
		eventCatcher = null;
	    }

	    boolean needsReUpdate = false;
	    if (root != null)
		root.unsetPropertiesForAll();
	    setStatus("Parsing Java code");
	    boolean failed = false;
	    final ImageDescriptor pid = ImageManager.getImageDesc("parsing_on.gif");
	    final ImageDescriptor npid = ImageManager.getImageDesc("parsing_now.gif");
	    toggleParseAction.setImageDescriptor(npid);
	    failed = !parseJavaCode();

	    setStatus("Finished parsing Java code");
	    // JiglooUtils.printTimeLapse("parsed");

	    while (needsReUpdate) {
		failed = !parseJavaCode();
		needsReUpdate = false;
	    }

	    if (reloadRoot) {
		jcp.setParsing(true);

		root = jcp.getRootComponent();

		menuBar = jcp.getMenuBar();
		nonVisualRoot = jcp.getNonVisualRoot();
		root.setName(getRootName(), false);

		Class sc = jcp.getSuperClass();
		if (sc != null && !Dialog.class.equals(sc) && !isAssignableFrom(WorkbenchPart.class, sc) && !isAppFrameworkApplication() && root.isSubclassOf(sc))
		    root.setClassName(sc.getName());

		setStatus("Populating elements...");

		populateFormComponents();
		setStatus("Finished populating elements...");

		// JiglooUtils.printTimeLapse("populated components");

		addParentlessFields();
		removeUnusedComponents();

		jcp.setParsing(false);
	    } else {
		// v4.0.0
		root = jcp.getRootComponent();
		setStatus("Populating elements...");
		populateFormComponents();
		setStatus("Finished populating elements...");
	    }

	    setEditorMode(jcp);

	    if (failed) {
		redrawRootNow();
		setCursor(getDefCursor());
		return;
	    }

	    setNeedsReload(true);

	    if (root.isSwing() && jiglooPlugin.canUseSwing()) {
		try {
		    root.updateUIForAll(true);
		    root.updateUI();
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		}
	    } else {
		root.updateUIForAll();
	    }

	    setLookAndFeelNow();

	    redrawRootNow();

	    if (selCompName != null) {
		FormComponent selComp2 = getComponentByName(selCompName);
		if (selComp2 == null)
		    selComp2 = getComponentByName(selCompParName);
		if (selComp2 != null)
		    selectComponent(selComp2);
	    }

	    if (awtControl != null)
		awtControl.update();

	    // JiglooUtils.printTimeLapse("redrawn");

	    setCursor(getDefCursor());

	    flashViewLogButton();
	    jiglooPlugin.closeCreationLog();

	    toggleParseAction.setImageDescriptor(pid);
	    setParsing(false);

	}

	// JiglooUtils.printTimeLapse("all done");
    }

    /**
     * @return
     */
    private boolean parseJavaCode() {
	boolean ret = jcp.parse(getWorkingCopy());
	return ret;
    }

    public void rebuildEditor() {
	if (jcp != null && root != null) {

	    createClassLoader();

	    hideWindowFrames(getSelectedComponents());

	    FormComponent selComp = getSelectedComponent();

	    rootObject = null;

	    if (frameComp != null)
		frameComp.dispose();
	    frameComp = null;

	    if (rootDecoration != null)
		rootDecoration.dispose();
	    rootDecoration = null;

	    if (awtControl != null)
		awtControl.dispose();
	    awtControl = null;

	    if (eventCatcher != null)
		eventCatcher.dispose();
	    eventCatcher = null;
	    if (root != null) {
		if (root.isSWT())
		    root.disposeSWT();
		// root.unsetPropertiesForAll();
	    }

	    menuBar = root.getMenuBar();
	    root.setName(getRootName(), false);

	    populateFormComponents();

	    Object rc = root.getRawComponent();
	    if (rc instanceof JFrame) {
		setAWTControlWindow((JFrame) rc);
	    } else if (rc instanceof JDialog) {
		setAWTControlWindow((JDialog) rc);
	    } else if (rc instanceof JWindow) {
		setAWTControlWindow((JWindow) rc);
	    } else if (rc instanceof JComponent) {
		// v4.0M3
		setAWTControlWindow(new JFrame());
		awtControl.setComponent((JComponent) rc);
	    }

	    setNeedsReload(true);
	    // populateFormComponents();
	    // System.out.println("JCS 10");
	    if (root.isSwing() && jiglooPlugin.canUseSwing()) {
		try {
		    root.updateUIForAll(true);
		    root.updateUI();
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		}
	    } else {
		root.updateUIForAll();
	    }
	    if (selComp != null) {
		selComp = getComponentByName(selComp.getName());
		selectComponent(selComp);
	    }
	    setLookAndFeelNow();

	    redrawRootNow();

	    jiglooPlugin.closeCreationLog();
	}
    }

    public IHarness getHarness() {
	return harness;
    }

    private DelayableRunnable javaCodeSync = new DelayableRunnable(getParseDelay(), true) {
	public void run() {
	    allowMoveCursor = false;
	    reparseJavaCode(false);
	    allowMoveCursor = true;
	}
    };

    private boolean isSaving = false;

    private boolean parsingEnabled = true;

    private Action toggleParseAction = null;

    public void toggleParsing() {
	enableParsing(!parsingEnabled);
    }

    public void enableParsing(boolean enable) {
	enableParsing(enable, false);
    }

    public void enableParsing(boolean enable, boolean force) {
	try {
	    if (parsingEnabled == enable && !force)
		return;
	    parsingEnabled = enable;
	    if (parsingEnabled && needsReparse) {
		javaCodeSync.cancel();
	    }
	    getToggleParsingAction();
	    if (parsingEnabled) {
		parsingLabel.setText("Parsing...");
		if (needsReparse) {
		    parsingLabel.setVisible(true);
		    parsingLabel.setImage(ImageManager.getImage("parsing_now.gif"));
		    parsingLabel.setBackground(getBGColor());
		    parsingLabel.redraw();
		    if (doUpdates)
			parsingLabel.update();
		    reparseJavaCode(false);
		} else {
		    parsingLabel.setVisible(false);
		    parsingLabel.redraw();
		    if (doUpdates)
			parsingLabel.update();
		}
		mainComp.setBackground(getBGColor());
		toggleParseAction.setImageDescriptor(ImageManager.getImageDesc("parsing_on.gif"));
		toggleParseAction.setToolTipText("Will parse code changes whenever code changed (after short delay)");
	    } else {
		parsingLabel.setText("Parse when needed");
		parsingLabel.setVisible(true);
		parsingLabel.setBackground(ColorManager.getColor(255, 0, 100));
		toggleParseAction.setImageDescriptor(ImageManager.getImageDesc("parsing_off.gif"));
		parsingLabel.setToolTipText("Will parse code changes only when Form editor has focus");
		toggleParseAction.setToolTipText("Will parse code changes only when Form editor has focus");
	    }
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    /**
     * if the editor is parsing, do nothing, otherwise enable parsing , and
     * parse the code
     */
    public void checkParsing() {
	boolean wasParsing = parsingEnabled;
	if (needsReparse) {
	    enableParsing(true, true);
	    if (!wasParsing)
		enableParsing(false);
	}
    }

    public Action getToggleParsingAction() {
	if (toggleParseAction == null) {
	    toggleParseAction = new Action() {
		public void run() {
		    toggleParsing();
		}
	    };
	    toggleParseAction.setImageDescriptor(ImageManager.getImageDesc("parsing_on.gif"));
	    toggleParseAction.setToolTipText("Will parse code changes - press to STOP parsing");
	}
	if (!parsingEnabled) {
	    toggleParseAction.setImageDescriptor(ImageManager.getImageDesc("parsing_off.gif"));
	    toggleParseAction.setToolTipText("Will NOT parse code changes - press to START parsing");
	}
	return toggleParseAction;
    }

    public boolean parsingEnabled() {
	return parsingEnabled;
    }

    private boolean ignorePropChanges = false;

    private boolean javaFileChanged = false;
    private boolean propertyFileChanged = false;

    /**
     * @param b
     */
    public void setPropertyFileChanged(boolean changed) {
	if (changed) {
	    firePropertyChange(PROP_DIRTY);
	}
	propertyFileChanged = changed;
	if (!propertyFileChanged && !javaFileChanged)
	    needsReparse = false;
	else if (propertyFileChanged)
	    needsReparse = true;
    }

    public void handlePropertyFileChanged(IFile file) {
	if (isSaving)
	    return;
	if (bundleManager != null) {
	    if (bundleManager.isChanging())
		return;
	    if (bundleManager.getPropertyFile().equals(file))
		setPropertyFileChanged(true);
	    handleChange(false);
	}
    }

    private void handleChange(boolean codeChanged) {
	if (combinedUndoManager == null || combinedUndoManager.isChanging())
	    return;
	Vector changes = bundleManager.getChanges();
	int cc = ((JavaEditor) javaEditor).getCommandCount();
	combinedUndoManager.addCommand(codeChanged, cc, changes);
	bundleManager.clearChanges();
    }

    public void handleJavaCodeChanged(boolean setDirty, IBuffer buffer) {

	if (updateJavaCode) {
	    // if jcp is the cause of the modification of the code, return
	    if (jcp == null || jcp.isChangingCode() || isSaving)
		return;
	    if (javaCodeSync.isRunning()) {
		return;
	    }
	    javaFileChanged = true;
	    needsReparse = true;
	    javaCodeSync.trigger();
	    handleChange(true);
	}

    }

    public boolean isInTabbedMode() {
	return EDITOR_MODE == EDITOR_MODE_TABBED;
    }

    private void handleKeyPressInTextEditor(KeyEvent e) {
	int keyCode = e.keyCode;
	if (keyCode == SWT.ESC && !isInTabbedMode())
	    handleEscape();

	// version 3.9.3 - for some reason, arrow keys keep being sent to
	// text editor even when form editor has focus, so need to forward
	// them to form editor handler
	if (formEditorActive)
	    getFormEditorKeyAdapter().keyPressed(e);

	if (updateJavaCode) {
	    if (jcp == null || jcp.isChangingCode())
		return;
	    javaCodeSync.extend();
	}
    }

    private boolean usePalette() {
	return jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_PALETTE);
    }

    private static int getParseDelay() {
	return jiglooPlugin.getDefault().getIntPreference(MainPreferencePage.P_PARSE_DELAY);
    }

    private void updatePaletteSize() {
	GridData gld = (GridData) palette.getLayoutData();
	if (usePalette())
	    gld.heightHint = 45;
	else
	    gld.heightHint = 0;
	palette.getParent().layout();
    }

    private Vector customSwingClasses = null;
    private Vector customSWTClasses = null;

    public Vector getCustomSWTLayoutClasses() {
	Vector v = new Vector();
	// for(int i=0;i<customSWTClasses.size();i++) {
	// Class cls = (Class)customSWTClasses.elementAt(i);
	// if(JiglooUtils.isLayout(cls))
	// v.add(cls);
	// }
	return v;
    }

    public Vector getCustomSwingLayoutClasses() {
	Vector v = new Vector();
	// for(int i=0;i<customSwingClasses.size();i++) {
	// Class cls = (Class)customSwingClasses.elementAt(i);
	// if(JiglooUtils.isLayout(cls))
	// v.add(cls);
	// }
	return v;
    }

    private String[][][] paletteClasses = null;

    private void fillComponentPalette() {
	if (paletteClasses == null)
	    paletteClasses = ComponentPalette.getPaletteClasses(null);

	String[][] pns = paletteClasses[0];
	for (int i = 0; i < pns.length; i++) {
	    for (int j = 0; j < pns[i].length; j++) {
		palette.addPalette(pns[i][j], i + 1);
	    }
	}

	pns = paletteClasses[1];
	for (int i = 0; i < pns.length; i++) {
	    try {
		String[] parts = pns[i];
		if (parts.length >= 5) {
		    String clsName = parts[2];
		    String lbl = parts[4];
		    String cust = parts[5];
		    int style = 0;
		    if (!parts[3].equals(""))
			style = Integer.parseInt(parts[3]);
		    String par = parts[0];

		    if ("Custom".equals(par) && ClassUtils.isLayout(loadClass(clsName)))
			par = "Layout";

		    int parType = Integer.parseInt(parts[1]);
		    Action act = getPaletteAction(clsName, style, lbl, !"n".equals(cust));
		    if (act != null)
			palette.addToPalette(par, parType, act);
		}
	    } catch (Throwable t) {
		jiglooPlugin.handleError(t);
	    }
	}

	palette.updateUI();
    }

    private Action getPaletteAction(String clsName, int style, String lbl, boolean custom) {
	Action act = null;
	Class cls = null;
	if (!clsName.equals("")) {
	    cls = loadClass(clsName);
	    if (cls == null) {
		// System.err.println("Unable to load palette class "+clsName);
		return null;
	    }
	}
	if (cls == null) {
	    act = getFormAddAction(null, 0, "Add custom class or layout...");
	} else {
	    boolean isLayout = ClassUtils.isLayout(cls);

	    if (custom) {
		if (!isLayout) {
		    String tt = "Add " + clsName;
		    if (!lbl.equals(""))
			tt = lbl;
		    return getFormAddActionCustom(cls, 0, tt);
		} else {
		    return getFormLayoutAction(cls);
		    // String tt = "Custom: " + clsName;
		    // if(!lbl.equals(""))
		    // tt = lbl;
		    // return new FormAddAction(this, cls, 0, tt, true);
		}
	    }

	    // parts[5] is "user-defined"
	    if (isLayout) {
		if (clsName.equals("javax.swing.BoxLayout")) {
		    if (style == 0)
			act = getFormLayoutAction(BoxLayout.class, new String[] { "axis", "0" }, "Box Layout - X");
		    else
			act = getFormLayoutAction(BoxLayout.class, new String[] { "axis", "1" }, "Box Layout - Y");
		} else {
		    act = getFormLayoutAction(cls);
		}
	    } else {
		if (lbl.equals("") && style == 0)
		    act = getFormAddAction(cls);
		else if (lbl.equals(""))
		    act = getFormAddAction(cls, style);
		else
		    act = getFormAddAction(cls, style, lbl);
	    }
	}
	return act;
    }

    public void showPalette(int mode) {
	palette.setMode(mode);
    }

    private SecurityManager secMan;
    private SecurityManager newSecMan;

    private void resetSecurityManager() {
	System.setSecurityManager(secMan);
	// System.out.println("reset SecurityManager to "+secMan);
    }

    private void setSecurityManager() {

	if (newSecMan == null) {
	    newSecMan = new JiglooSecurityManager();
	}

	if (!(System.getSecurityManager() instanceof JiglooSecurityManager)) {
	    secMan = System.getSecurityManager();
	    System.setSecurityManager(newSecMan);
	}
    }

    public void populateFormComponents() {

	final boolean KILL_EXTRA_THREADS = false;
	ThreadGroup tg = null;
	Thread[] threads = null;
	if (KILL_EXTRA_THREADS)
	    tg = Thread.currentThread().getThreadGroup();

	if (tg != null) {
	    threads = new Thread[tg.activeCount()];
	    tg.enumerate(threads);
	}

	Menu eclipseMenuBar = getSite().getShell().getMenuBar();

	setPopulating(true);

	populateRoot();

	if (menuBar != null) {
	    setMenuBar(menuBar, false);
	    menuBar.clearCachedBounds();
	}

	if (nonVisualRoot != null)
	    nonVisualRoot.populateNonVisualComponents(this);

	FormComponent rs = getRootShell();
	if (rs != null)
	    rs.populateControls(null, this, false);

	setPopulating(false);
	if (awtControl != null)
	    awtControl.layoutInFrame();
	root.invokeStoredMethodCalls();

	if (!eclipseMenuBar.equals(getSite().getShell().getMenuBar()))
	    getSite().getShell().setMenuBar(eclipseMenuBar);

	if (tg != null) {
	    // if populating the components created any extra threads, kill them
	    // here!
	    int newThreadCount = tg.activeCount();
	    Thread[] newThreads = new Thread[newThreadCount];
	    tg.enumerate(newThreads);
	    for (int i = 0; i < newThreadCount; i++) {
		boolean found = false;
		for (int j = 0; j < threads.length; j++) {
		    if (newThreads[i] != null && newThreads[i].equals(threads[j])) {
			found = true;
			break;
		    }
		}
		if (!found && newThreads[i] != null) {
		    try {
			newThreads[i].stop();
		    } catch (Throwable t) {
		    }
		}
	    }
	}

    }

    private void contributeToActionBars() {
	IActionBars bars = getEditorSite().getActionBars();
	fillLocalPullDown(bars.getMenuManager());
	fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalPullDown(IMenuManager manager) {
	manager.add(showLayoutInfo);
	manager.add(new Separator());
    }

    private void fillContextMenu(IMenuManager manager) {
	manager.add(showLayoutInfo);
	manager.add(new Separator());
	// drillDownAdapter.addNavigationActions(manager);
	// Other plug-ins can contribute there actions here
	manager.add(new Separator("Additions"));
    }

    private void fillLocalToolBar(IToolBarManager manager) {
	manager.add(showLayoutInfo);
	manager.add(new Separator()); // drillDownAdapter.addNavigationActions(manager);
    }

    Action showLayoutInfo;

    private void makeActions() {
	try {
	    showLayoutInfo = new Action(null, Action.AS_RADIO_BUTTON) {
		public void run() {
		    if (!showLayoutInfo.isChecked()) { // showLayoutInfo.setChecked(true);
		    }
		}
	    };
	    showLayoutInfo.setText("Show Layout Info");
	    showLayoutInfo.setToolTipText("Show Layout Info");
	    showLayoutInfo.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	    showLayoutInfo.setAccelerator(SWT.CTRL | 'T');
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private FormAddAction getFormAddActionCustom(Class clazz, int style, String label) {
	String key = clazz + "-" + style + label + "-Custom";
	if (actions.containsKey(key))
	    return (FormAddAction) actions.get(key);
	FormAddAction faa = new FormAddAction(this, clazz, style, label, true);
	actions.put(key, faa);
	return faa;
    }

    public FormAddAction getFormAddAction(Class clazz, int style, String label) {
	String key = clazz + "-" + style + label;
	if (actions.containsKey(key))
	    return (FormAddAction) actions.get(key);
	FormAddAction faa = new FormAddAction(this, clazz, style, label);
	actions.put(key, faa);
	return faa;
    }

    private FormAddAction getFormAddAction(Class clazz, int style) {
	String key = clazz + "-" + style;
	if (actions.containsKey(key))
	    return (FormAddAction) actions.get(key);
	FormAddAction faa = new FormAddAction(this, clazz, style);
	actions.put(key, faa);
	return faa;
    }

    public FormSurroundAction getFormSurroundAction(Class clazz) {
	// System.out.println("GET FORM ADD ACTION");
	if (surroundActions.containsKey(clazz))
	    return (FormSurroundAction) surroundActions.get(clazz);
	FormSurroundAction faa = new FormSurroundAction(this, clazz);
	surroundActions.put(clazz, faa);
	return faa;
    }

    public FormAlignAction getFormAlignAction(int dirn) {
	Object action = actions.get("ALIGN-" + dirn);
	if (action instanceof FormLookAndFeelAction) {
	    return (FormAlignAction) action;
	} else if (action != null)
	    return null;
	FormAlignAction faa = new FormAlignAction(this, dirn);
	actions.put("ALIGN-" + dirn, faa);
	return faa;
    }

    public FormLookAndFeelAction getFormLookAndFeelAction(String className, String lafName) {
	Object action = actions.get(className);
	if (action instanceof FormLookAndFeelAction) {
	    return (FormLookAndFeelAction) action;
	} else if (action != null)
	    return null;
	FormLookAndFeelAction faa = new FormLookAndFeelAction(this, className, lafName);
	actions.put(className, faa);
	return faa;
    }

    public FormAddAction getFormAddAction(Class clazz) {
	// System.out.println("GET FORM ADD ACTION");
	Object action = actions.get(clazz);
	if (action instanceof FormAddAction) {
	    return (FormAddAction) action;
	} else if (action != null)
	    return null;
	FormAddAction faa = new FormAddAction(this, clazz);
	actions.put(clazz, faa);
	return faa;
    }

    public FormLayoutAction getFormLayoutAction(Class clazz) {
	Object action = actions.get(clazz);
	if (action instanceof FormLayoutAction) {
	    return (FormLayoutAction) action;
	} else if (action != null)
	    return null;
	FormLayoutAction fla = new FormLayoutAction(this, clazz);
	actions.put(clazz, fla);
	return fla;
    }

    private FormLayoutAction getFormLayoutAction(Class clazz, String[] props, String label) {
	if (actions.containsKey(label))
	    return (FormLayoutAction) actions.get(label);
	FormLayoutAction fla = new FormLayoutAction(this, clazz, props, label);
	actions.put(label, fla);
	return fla;
    }

    public FormLayoutDataAction getFormLayoutDataAction(String propName, Object value, String label, String id) {
	if (actions.containsKey(id))
	    return (FormLayoutDataAction) actions.get(id);
	FormLayoutDataAction fla = new FormLayoutDataAction(this, propName, value, label);
	actions.put(id, fla);
	return fla;
    }

    boolean settingRootSize = false;

    private Object getRootSize(boolean swtMode, boolean nullOK) {
	if (root == null)
	    return null;

	Control comp = (Control) root.getControl();

	if (swtMode) {
	    Point size = null;

	    if (root.isPropertySet("size")) {
		Object rs = root.getRawPropertyValue("size");
		if (rs instanceof Point) {
		    size = (Point) rs;
		} else {
		    System.err.println("ROOT SIZE IS NOT POINT " + rs);
		}

	    } else if (!root.hasStandardSuperclass() && root.isPropertySet("size", false)) {
		// this is for case where superclass size is set, but subclass
		// size is not set.
		Object defVal = root.getDefaultPropertyValue("size");
		defVal = ConversionUtils.convertToRawValue(defVal, null);
		defVal = ConversionUtils.convertToSWT(defVal);
		Point rec = null;
		if (defVal instanceof Point)
		    rec = (Point) defVal;
		if (rec != null && rec.y > 10) {
		    size = rec;
		}
	    }

	    if (root.isPropertySet("preferredSize")) {
		size = (Point) root.getRawPropertyValue("preferredSize", Point.class);
	    }

	    if (root.isPropertySet("bounds")) {
		Rectangle bounds = (Rectangle) root.getRawPropertyValue("bounds", Rectangle.class);
		if (bounds != null && bounds.width > 0 && bounds.height > 0) {
		    size = new Point(bounds.width, bounds.height);
		}
	    }

	    if (size != null) {
		size = new Point(size.x, size.y);
		if (size.x < 100)
		    size.x = 100;
		if (size.y < 100)
		    size.y = 100;
	    }

	    if (nullOK)
		return size;

	    if (size == null) {
		try {
		    if (!isInAndroidMode() && isInSwingMode()) {
			Component cmpt = root.getComponent();
			if (root.usesContentPane())
			    cmpt = root.getContentPane().getParent();
			if (cmpt == null)
			    cmpt = root.getContentPane();
			cmpt.validate();
			size = new Point(cmpt.getSize().width, cmpt.getSize().height);
			if (root.isJFrame() || root.isJDialog())
			    root.addWindowDecorationSizes(size);
			// System.out.println("SWING ROOT SIZE=" + size);
		    } else {
			// try and calculate the size the Shell would have -
			// doesn't really work
			size = comp.computeSize(-1, -1);
		    }
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		    size = new Point(100, 100);
		}
	    }
	    if (size != null) {
		size = new Point(size.x, size.y);
		if (size.x < 100)
		    size.x = 100;
		if (size.y < 100)
		    size.y = 100;
	    }
	    return size;

	} else {

	    FormComponent rc = getRootComponent();
	    if (rc.isPropertySet("bounds")) {
		Rectangle rec = (Rectangle) rc.getRawPropertyValue("bounds");
		rec.height -= getMenuHeight();
		return new Dimension(rec.width, rec.height);
	    } else if (rc.isPropertySet("preferredSize")) {
		Point rec = (Point) rc.getRawPropertyValue("preferredSize");
		rec.y -= getMenuHeight();
		return new Dimension(rec.x, rec.y);
		// return null;
	    } else if (rc.isPropertySet("size")) {
		Point rec = (Point) rc.getRawPropertyValue("size");
		rec.y -= getMenuHeight();
		return new Dimension(rec.x, rec.y);
	    } else if (rc.isPropertySet("size", false)) {
		// this is for case where superclass size is set, but subclass
		// size is not set.
		Object defVal = rc.getDefaultPropertyValue("size");
		defVal = ConversionUtils.convertToRawValue(defVal, null);
		defVal = ConversionUtils.convertToSWT(defVal);
		Point rec = null;
		if (defVal instanceof Point)
		    rec = (Point) defVal;
		if (rec != null && rec.y > 10) {
		    rec.y -= getMenuHeight();

		    // if (rc.isJFrame() || rc.isJDialog())
		    // rc.subtractWindowDecorationSizes(rec);

		    // this is if superclass size is not set, but is different
		    // from default value
		    return new Dimension(rec.x, rec.y);
		} else {
		    return null;
		}
	    } else {
		return null;
	    }

	}
    }

    public void refresh(boolean reloadViewer) {
	if (settingRootSize)
	    return;
	try {
	    if (reloadViewer) {
		reloadOutline();
	    }
	    if (root == null)
		return;
	    Point size = null;
	    Control comp = (Control) root.getControl();

	    if (comp != null) {
		size = (Point) getRootSize(true, false);
		if (root.isA(Shell.class)) {
		    // for a Dialog (dialogShell)
		    root.subtractWindowDecorationSizes(size);
		    size.y -= getMenuHeight();
		}

		Object ld = comp.getLayoutData();
		FormData data2 = null;
		if (!(ld instanceof FormData)) {
		    data2 = new FormData();
		} else {
		    data2 = (FormData) comp.getLayoutData();
		}
		if (data2.top == null)
		    data2.top = new FormAttachment(menuComp);
		data2.width = size.x;
		data2.height = size.y;

	    } else {
		throw new NullPointerException("root (" + root + ") control is null");
	    }
	    mainComp.setSize(size.x, size.y);
	    settingRootSize = true;
	    Rectangle r = new Rectangle(0, 0, size.x, size.y);
	    // Rectangle r = new Rectangle(BORDER_X, BORDER_Y, size.x, size.y);

	    if (root.isCWT())
		r = com.cloudgarden.jigloo.typewise.TypewiseManager.convertToGridBounds(r, root);

	    root.setPropertyValueInternal("bounds", r, false);

	    settingRootSize = false;
	    if (frameComp != null) {

		if (isInSWTMode())
		    frameComp.setSize(size.x, size.y + getMenuHeight());
		else
		    frameComp.setSize(size.x, size.y);

		frameComp.layout();
	    }
	    if (internalShell != null) {
		internalShell.setSize(size.x, size.y);
	    }

	    root.updateGroupLayoutSizesForAll();

	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    private void updateScrollPaneSize() {
	Point size = ((Control) root.getControl()).getSize();
	// Point spsz = scrollPane.getSize();
	int hinc = size.x;
	int vinc = size.y;
	if (hinc < 10)
	    hinc = 100;
	if (vinc < 10)
	    vinc = 100;
	scrollPane.getHorizontalBar().setPageIncrement(hinc);
	scrollPane.getHorizontalBar().setIncrement(50);
	scrollPane.getVerticalBar().setPageIncrement(vinc);
	scrollPane.getVerticalBar().setIncrement(50);

	// whatever size the main control is supposed to be, set the scroll pane
	// to
	// be bigger than it's actual size
	scrollPane.setMinSize(size.x + BORDER_X * 2, size.y + BORDER_Y * 2);
    }

    private void initContextMenus(FormComponent fc) {
	if (swingMenu == null) {
	    swingMenuMgr = new MenuManager("#SwingPopupMenu");
	    swingMenu = swingMenuMgr.createContextMenu(getViewportControl());
	    swtMenuMgr = new MenuManager("#SWTPopupMenu");
	    swtMenu = swtMenuMgr.createContextMenu(getViewportControl());
	    setSwingTabTitle = new FormAction(this) {
		public void run() {
		    FormComponent actComp = getActionComponent().getParent();
		    if (actComp.getComponent() instanceof JTabbedPane) {
			JTabbedPane tp = (JTabbedPane) actComp.getComponent();
			int i = tp.getSelectedIndex();
			String title = tp.getTitleAt(i);
			if (title == null || title.equals(""))
			    title = "tab";
			InputDialog id = new InputDialog(getSite().getShell(), "Enter tab name", "Enter tab name", title, null);
			id.open();
			title = id.getValue();
			if (title == null)
			    return;
			id.close();
			FormComponent tabComp = (FormComponent) actComp.getChildren().elementAt(i);
			tabComp.setTabTitle(title);
			tabComp.setPropertyValueCode("LAYOUT_CONSTRAINT", null);
			tabComp.repairParentConnectionInCode();
			setDirtyAndUpdate();
			// actComp.updateUI();
		    }
		}
	    };
	    setSwingTabTitle.setId("SetTabName");
	    setSwingTabTitle.setText("Set Tab name");
	}
	if (!fc.isSwing()) {
	    initSwtMenuManager(swtMenuMgr, fc);
	} else {
	    initSwingMenuManager(swingMenuMgr, fc);
	}
    }

    private Rectangle scrollRec;

    private Point scrollOrigin;

    private DelayableRunnable scroller = new DelayableRunnable(200, false) {
	public void run() {
	    int x = scrollRec.x - 50;
	    if (x < 0)
		x = 0;
	    int y = scrollRec.y - 50;
	    if (y < 0)
		y = 0;
	    int STEP = 6;
	    double dx = (x - scrollOrigin.x) / STEP;
	    double dy = (y - scrollOrigin.y) / STEP;
	    if (dx != 0 || dy != 0) {
		for (int i = 0; i < STEP; i++) {
		    x = scrollRec.x - 50;
		    if (x < 0)
			x = 0;
		    y = scrollRec.y - 50;
		    if (y < 0)
			y = 0;
		    dx = (x - scrollOrigin.x) / STEP;
		    dy = (y - scrollOrigin.y) / STEP;
		    final int x0 = scrollOrigin.x + (int) (i * dx);
		    final int y0 = scrollOrigin.y + (int) (i * dy);
		    Display.getDefault().syncExec(new Runnable() {
			public void run() {
			    // System.out.println("SCROLL SET ORG " + x0 + ", "
			    // + y0);
			    scrollPane.setOrigin(x0, y0);
			}
		    });
		    try {
			Thread.sleep(30);
		    } catch (Throwable e) {
		    }
		}
	    }
	    final int fx = x;
	    final int fy = y;
	    Display.getDefault().asyncExec(new Runnable() {
		public void run() {
		    scrollPane.setOrigin(fx, fy);
		}
	    });
	}
    };

    private Runnable scrollerRel = new Runnable() {
	public void run() {
	    int i = 0;
	    while (scrollDirn != 0) {
		int dx = 0;
		int dy = 0;
		int SCROLL_SPEED = 5;
		if ((scrollDirn & 1) != 0)
		    dx = -SCROLL_SPEED;
		if ((scrollDirn & 2) != 0)
		    dx = SCROLL_SPEED;
		if ((scrollDirn & 4) != 0)
		    dy = -SCROLL_SPEED;
		if ((scrollDirn & 8) != 0)
		    dy = SCROLL_SPEED;
		if ((scrollDirn & 16) != 0) {
		    dx *= 2;
		    dy *= 2;
		}
		final int fdx = dx;
		final int fdy = dy;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
			if (!scrollPane.isDisposed()) {
			    scrollOrigin = scrollPane.getOrigin();
			    int x0 = scrollOrigin.x + fdx;
			    int y0 = scrollOrigin.y + fdy;
			    scrollPane.setOrigin(x0, y0);
			}
		    }
		});
		try {
		    Thread.sleep(30);
		} catch (Throwable e) {
		}
		i++;
	    }
	    scrollThread = null;
	}
    };

    private Thread scrollThread = null;

    private int scrollDirn = 0;

    public void scrollFormInDirn() {
	if (scrollDirn == 0)
	    return;
	if (scrollThread == null) {
	    scrollThread = new Thread(scrollerRel);
	    scrollThread.start();
	}
    }

    public void scrollFormTo(FormComponent fc) {
	if (fc == null)
	    return;

	scrollRec = fc.getBoundsRelativeTo(null);
	final Rectangle r = scrollPane.getClientArea();
	scrollOrigin = scrollPane.getOrigin();
	if (r.contains(scrollRec.x - scrollOrigin.x, scrollRec.y - scrollOrigin.y))
	    return;
	if (r.contains(scrollRec.x + scrollRec.width - scrollOrigin.x, scrollRec.y - scrollOrigin.y))
	    return;
	if (r.contains(scrollRec.x + scrollRec.width - scrollOrigin.x, scrollRec.y + scrollRec.height - scrollOrigin.y))
	    return;
	if (r.contains(scrollRec.x - scrollOrigin.x, scrollRec.y + scrollRec.height - scrollOrigin.y))
	    return;
	scroller.trigger();

    }

    public void initSwtMenuManager(IMenuManager menuMgr, final FormComponent comp) {

	menuMgr.removeAll();
	MenuManager smm;

	boolean isMenuComp = comp.isMenuComponent();

	if (!isMenuComp) {
	    smm = new MenuManager("Surround by container...");
	    menuMgr.add(smm);
	    smm.add(getFormSurroundAction(Group.class));
	    smm.add(getFormSurroundAction(Composite.class));
	    smm.add(getFormSurroundAction(ScrolledComposite.class));
	    smm.add(getFormSurroundAction(SashForm.class));
	    smm.add(getFormSurroundAction(CTabFolder.class));
	    smm.add(getFormSurroundAction(TabFolder.class));
	}

	boolean addMenuOpts = jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_CONTEXT_MENU);

	if (!ItemManager.addAddAction(menuMgr, comp.getClassName(), this) && addMenuOpts) {
	    addSWTMenuActions(menuMgr, comp);
	}

	if (!isMenuComp) {
	    menuMgr.add(new Separator("setClassAction"));
	    menuMgr.add(setClassAction);
	}

	menuMgr.add(new Separator("Additions"));

	if (!isMenuComp) {
	    Object wid = comp.getControl();
	    if (wid instanceof Composite && !(wid instanceof TabFolder) && !(wid instanceof CTabFolder) && !(wid instanceof Table) && !(wid instanceof TableTree) && !(wid instanceof Tree)
		    && !(wid instanceof CoolBar) && !(wid instanceof ToolBar)) {
		smm = new MenuManager("Set Layout");
		menuMgr.add(smm);
		smm.add(new FormLayoutAction(this, AbsoluteLayout.class));
		smm.add(getFormLayoutAction(GridLayout.class));
		smm.add(getFormLayoutAction(RowLayout.class));
		smm.add(getFormLayoutAction(FormLayout.class));
		smm.add(getFormLayoutAction(FillLayout.class));
		smm.add(getFormLayoutAction(MigLayout.class));
		smm.add(getFormLayoutAction(StackLayout.class));
		String[] customClassArray = jiglooPlugin.getCustomSWTClasses();
		for (int i = 0; i < customClassArray.length; i++) {
		    String cstCls = customClassArray[i];
		    try {
			if (cstCls.equals(""))
			    continue;
			Class cls = loadClass(cstCls);
			if (cls != null) {
			    if (ClassUtils.isLayout(cls)) {
				smm.add(new FormAddAction(this, cls, 0, "Custom: " + cls, true));
			    }
			}
		    } catch (Throwable t) {
			jiglooPlugin.handleError(getSite().getShell(), "Error loading custom class " + cstCls, "Error loading class", t);
		    }
		}
	    }
	    String plt = comp.getParentSuperLayoutType();
	    if (comp.isSWT() && "Grid".equals(plt)) {
		MenuManager smm1 = new MenuManager("Align...");
		menuMgr.add(smm1);
		smm1.add(grabHorizAction);
		grabHorizAction.setChecked(comp.getBooleanLayoutDataProp("grabExcessHorizontalSpace"));
		smm = new MenuManager("Horiz alignment...");
		smm1.add(smm);
		String prop = "horizontalAlignment";
		Integer BEG = new Integer(GridData.BEGINNING);
		Integer FILL = new Integer(GridData.FILL);
		Integer END = new Integer(GridData.END);
		Integer CEN = new Integer(GridData.CENTER);
		smm.add(getFormLayoutDataAction(prop, BEG, "BEGINNING", "HABEG"));
		smm.add(getFormLayoutDataAction(prop, CEN, "CENTER", "HACEN"));
		smm.add(getFormLayoutDataAction(prop, END, "END", "HAEND"));
		smm.add(getFormLayoutDataAction(prop, FILL, "FILL", "HAFIL"));
		smm = new MenuManager("Horiz span...");
		smm1.add(smm);
		prop = "horizontalSpan";
		smm.add(getFormLayoutDataAction(prop, new Integer(1), "1", "HSP1"));
		smm.add(getFormLayoutDataAction(prop, new Integer(2), "2", "HSP2"));
		smm.add(getFormLayoutDataAction(prop, new Integer(3), "3", "HSP3"));
		smm.add(getFormLayoutDataAction(prop, new Integer(4), "4", "HSP4"));
		smm.add(getFormLayoutDataAction(prop, new Integer(5), "5", "HSP5"));
		smm.add(getFormLayoutDataAction(prop, new Integer(6), "6", "HSP6"));
		smm1.add(grabVertAction);
		grabVertAction.setChecked(comp.getBooleanLayoutDataProp("grabExcessVerticalSpace"));
		smm = new MenuManager("Vert alignment...");
		smm1.add(smm);
		prop = "verticalAlignment";
		smm.add(getFormLayoutDataAction(prop, BEG, "BEGINNING", "VABEG"));
		smm.add(getFormLayoutDataAction(prop, CEN, "CENTER", "VACEN"));
		smm.add(getFormLayoutDataAction(prop, END, "END", "VAEND"));
		smm.add(getFormLayoutDataAction(prop, FILL, "FILL", "VAFIL"));
		smm = new MenuManager("Vert span...");
		smm1.add(smm);
		prop = "verticalSpan";
		smm.add(getFormLayoutDataAction(prop, new Integer(1), "1", "VSP1"));
		smm.add(getFormLayoutDataAction(prop, new Integer(2), "2", "VSP2"));
		smm.add(getFormLayoutDataAction(prop, new Integer(3), "3", "VSP3"));
		smm.add(getFormLayoutDataAction(prop, new Integer(4), "4", "VSP4"));
		smm.add(getFormLayoutDataAction(prop, new Integer(5), "5", "VSP5"));
		smm.add(getFormLayoutDataAction(prop, new Integer(6), "6", "VSP6"));
		// menuMgr.add(new Separator());
	    }

	    if ("Form".equals(plt)) {
		menuMgr.add(anchorDialogAction);
	    }

	    if ("Form".equals(plt) || "Absolute".equals(plt)) {
		boolean addSep = false;
		if (getSelectedComponents().size() > 1 || gridSize > 0) {
		    smm = new MenuManager("Align controls...");
		    menuMgr.add(smm);
		    if (getSelectedComponents().size() > 1) {
			smm.add(getFormAlignAction(3));
			smm.add(getFormAlignAction(4));
			smm.add(getFormAlignAction(1));
			smm.add(new Separator());
			smm.add(getFormAlignAction(0));
			smm.add(getFormAlignAction(5));
			smm.add(getFormAlignAction(2));
			smm.add(new Separator());
			smm.add(getFormAlignAction(7));
			smm.add(getFormAlignAction(8));
			smm.add(new Separator());
			smm.add(getFormAlignAction(9));
			smm.add(getFormAlignAction(10));
			addSep = true;
		    }
		    if (gridSize > 0) {
			if (addSep)
			    smm.add(new Separator());
			smm.add(getFormAlignAction(6));
		    }
		}
	    }
	}

	SWTStyleManager.addStyleMenuItems(this, comp.getMainClass(), comp.getStyle(), menuMgr);

	addEditOptions(menuMgr, comp);
    }

    private void addSWTMenuActions(IMenuManager menuMgr, FormComponent comp) {
	String cls = comp.getClassName();

	if (comp.isRoot()) {
	    menuMgr.add(getFormAddAction(Menu.class, SWT.BAR, "Add MenuBar"));
	    menuMgr.add(new Separator());
	}
	if (!comp.isContainer())
	    return;
	MenuManager smm1 = new MenuManager("Add ...");
	menuMgr.add(smm1);
	fillAddMenu(smm1, false, false);
    }

    public void initContextMenuManager(IMenuManager menuMgr, ISelection selection) {
	if (selection instanceof StructuredSelection) {
	    StructuredSelection ss = (StructuredSelection) selection;
	    if (ss == null)
		return;
	    Object[] sels = ss.toArray();
	    if (sels == null || sels.length == 0)
		return;
	    boolean selChanged = false;
	    for (int i = 0; i < sels.length; i++) {
		if (sels[i] instanceof TreeObject) {
		    TreeObject to = (TreeObject) sels[i];
		    // System.err.println("got tree obj "+to.getDisplayName());
		    if (to.getDisplayName().equals(EXTRA_COMPONENT_LABEL)) {
			setSelectedComponent(getRootComponent(), true);
			if (getRootComponent().isSwing()) {
			    initSwingMenuManager(menuMgr, getRootComponent());
			} else {
			    initSwtMenuManager(menuMgr, getRootComponent());
			}
			return;
		    }
		}
	    }
	}
	if (selectedComp == null)
	    selectionChanged(null, selection);
	initContextMenuManager(menuMgr);
    }

    public void initContextMenuManager(IMenuManager menuMgr) {
	if (selectedComp.isSwing()) {
	    initSwingMenuManager(menuMgr, selectedComp);
	} else {
	    initSwtMenuManager(menuMgr, selectedComp);
	}
    }

    public String getLookAndFeel() {
	return laf;
    }

    private static void updateComponentTreeUI(Component c) {
	Component[] children = null;
	if (c instanceof JMenu) {
	    children = ((JMenu) c).getMenuComponents();
	} else if (c instanceof Container) {
	    children = ((Container) c).getComponents();
	}
	if (children != null) {
	    for (int i = 0; i < children.length; i++) {
		updateComponentTreeUI(children[i]);
	    }
	}
	try {
	    if (c instanceof JComponent) {
		((JComponent) c).updateUI();
		((JComponent) c).invalidate();
		((JComponent) c).validate();
		((JComponent) c).repaint();
	    }
	} catch (Throwable t) {
	}
    }

    private boolean lafChanged = false;

    public void setLookAndFeel(String laf) {
	// if(true)
	// return;
	if (!jiglooPlugin.canUseSwing() || !isInSwingMode())
	    return;
	if (laf == null && this.laf != null)
	    return;
	try {
	    if (laf == null) {
		laf = bundleManager.getApplicationLookAndFeel();
	    }
	    if (laf.startsWith("Reset")) {
		laf = jiglooPlugin.getDefaultLAFClassName();
	    }
	    if (laf.startsWith("System")) {
		laf = UIManager.getSystemLookAndFeelClassName();
	    }
	    laf = LookAndFeelWrapper.convertGoodiesLnF(laf);
	    if (laf == null || !laf.equals(this.laf))
		lafChanged = true;
	    this.laf = laf;
	} catch (Throwable t) {
	    System.err.println("Unable to load L&F " + laf + ", " + t);
	    lafChanged = false;
	}
    }

    private static HashMap uiDefaults;
    private static LookAndFeel origLAF;

    public void setLookAndFeelNow() {

	if (!jiglooPlugin.canUseSwing() || !isInSwingMode())// || !lafChanged)
							    // //v4.0.0
	    return;

	if (uiDefaults == null) {
	    UIDefaults tmp = UIManager.getDefaults();
	    origLAF = UIManager.getLookAndFeel();
	    ;
	    if (tmp.size() > 5) {
		Enumeration en = tmp.keys();
		while (en.hasMoreElements()) {
		    try {
			Object key = en.nextElement();
			// System.out.println("found ui value "+key+",
			// "+tmp.get(key)+", "+tmp.size());
			if (tmp.get(key) != null) {
			    if (uiDefaults == null)
				uiDefaults = new HashMap();
			    uiDefaults.put(key, tmp.get(key));
			}
		    } catch (Throwable t) {
			t.printStackTrace();
		    }
		}
	    }
	}

	LookAndFeel oldLAF = UIManager.getLookAndFeel();

	// if you set the L&F unnecessarily, then non-opaque objects
	// become opaque again! Maybe need something to re-set
	// the opaque property if the L&F is changed
	lafChanged = false;
	// System.out.println("SET L&F NOW " + laf);
	try {
	    Class lafCls = null;
	    LookAndFeel lafInst = null;
	    if (laf == null) {
		lafInst = jiglooPlugin.getDefaultLAF();
		if (lafInst != null) {
		    lafCls = lafInst.getClass();
		} else {
		    laf = UIManager.getCrossPlatformLookAndFeelClassName();
		    lafCls = loadClass(laf);
		}
	    } else {
		LookAndFeelWrapper lafw = null;
		Vector lafs = jiglooPlugin.getLookAndFeels(false);
		for (int i = 0; i < lafs.size(); i++) {
		    lafw = (LookAndFeelWrapper) lafs.elementAt(i);
		    LookAndFeel inst = lafw.getLookAndFeel();
		    String lafID = inst == null ? null : inst.getID();
		    String lafName = inst == null ? null : inst.getName();
		    if (laf.equals(lafw.getClassName()) || laf.equals(lafID) || laf.equals(lafName))
			break;
		    else
			lafw = null;
		}
		if (lafw != null) {
		    lafInst = lafw.getLookAndFeel();
		    if (lafInst != null) {
			lafCls = lafInst.getClass();
		    } else {
			displayError("Unable to set Look and Feel", "Unable to set Look and Feel\nClass not found\n");
			return;
		    }
		}
	    }

	    Iterator it = uiDefaults.keySet().iterator();
	    while (it.hasNext()) {
		Object key = it.next();
		if (lafCls != null && lafCls.getName().equals(origLAF.getClass().getName())) {
		    UIManager.getDefaults().put(key, uiDefaults.get(key));
		} else {
		    UIManager.getDefaults().put(key, null);
		}
	    }

	    if (lafCls != null) {
		// System.err.println("SET L&F NOW (class loaded) " + laf);
		UIManager.put("ClassLoader", new ChainedClassLoader(new ClassLoader[] { lafCls.getClassLoader(), cpLoader, ClassLoader.getSystemClassLoader() }));
		if (lafInst == null)
		    lafInst = (LookAndFeel) lafCls.newInstance();
		if (oldLAF != null && oldLAF.getClass().getName().equals(lafInst.getClass().getName()))
		    return;
		UIManager.setLookAndFeel(lafInst);
	    } else {
		if (oldLAF != null && oldLAF.getClass().getName().equals(laf))
		    return;
		UIManager.setLookAndFeel(laf);
	    }

	    // it = uiDefaults.keySet().iterator();
	    // while(it.hasNext()) {
	    // Object key = it.next();
	    // Object obj1 = UIManager.get(key);
	    // Object obj2 = uiDefaults.get(key);
	    // if(!obj2.equals(obj1))
	    // System.out.println("DIFFERENT: "+key+"..."+obj2+"..."+obj1);
	    // }

	    try {
		Runnable r = new Runnable() {
		    // SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			// System.out.println("SET L&F NOW STARTED");
			try {
			    // System.out.println("SET L&F NOW " + laf);
			    if (awtControl != null && awtControl.getContentPane() != null) {
				SwingUtilities.updateComponentTreeUI(awtControl.getContentPane().getParent());
			    }
			} catch (Throwable t) {
			    jiglooPlugin.writeToLog("Error setting LookAndFeel " + laf + ", " + t);
			}
			// System.out.println("SET L&F NOW DONE");
		    }
		};
		// SwingUtilities.invokeLater(r);
		JiglooUtils.invokeSwing(r);
		if (getRootComponent() != null) {
		    getRootComponent().updateUIForAll();
		}
	    } catch (Throwable e) {
		jiglooPlugin.handleError(e);
	    }
	} catch (Throwable e) {
	    displayError("Unable to set Look and Feel", "Unable to set Look and Feel\n" + e);
	    // e.printStackTrace();
	    jiglooPlugin.handleError(e, "Unable to set Look and Feel");
	}

    }

    private void fillAddMenu(IMenuManager mm, boolean swing, boolean layout) {
	if (paletteClasses == null)
	    paletteClasses = ComponentPalette.getPaletteClasses(null);
	String[][] pns = paletteClasses[0];
	int index = swing ? 0 : 1;
	for (int j = 0; j < pns[index].length; j++) {
	    String menuName = pns[index][j];
	    String menuLabel = "Add " + menuName;
	    if (layout)
		menuLabel = "Set " + menuName;
	    MenuManager mm2 = new MenuManager(menuLabel);
	    mm.add(mm2);
	    String[][] items = paletteClasses[1];
	    for (int i = 0; i < items.length; i++) {
		try {
		    String[] parts = items[i];
		    if (parts.length >= 5) {
			String par = parts[0];
			if (layout)
			    par = "Layout";
			boolean isSwing = parts[1].equals("1");
			if (par.equals(menuName) && (swing == isSwing)) {
			    String clsName = parts[2];
			    String lbl = parts[4];
			    int pos = lbl.indexOf("\n");
			    if (pos > 0)
				lbl = lbl.substring(0, pos);
			    String cust = parts[5];
			    int style = 0;
			    if (!parts[3].equals(""))
				style = Integer.parseInt(parts[3]);
			    int parType = Integer.parseInt(parts[1]);
			    Action act = getPaletteAction(clsName, style, lbl, !"n".equals(cust));
			    if ((act instanceof FormAddAction && !layout) || (act instanceof FormLayoutAction && layout))
				mm2.add(act);
			}
		    }
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		}
	    }
	}
    }

    public void initSwingMenuManager(IMenuManager menuMgr, final FormComponent comp) {
	menuMgr.removeAll();
	MenuManager smm;
	boolean addMenuOpts = jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_CONTEXT_MENU);
	smm = new MenuManager("Set Look and Feel");
	menuMgr.add(smm);

	Vector lafs = jiglooPlugin.getLookAndFeels(false);
	for (int i = 0; i < lafs.size(); i++) {
	    LookAndFeelWrapper lafw = (LookAndFeelWrapper) lafs.elementAt(i);
	    smm.add(getFormLookAndFeelAction(lafw.getClassName(), lafw.getName()));
	}
	smm.add(getFormLookAndFeelAction("System Look and Feel", "System Look and Feel"));
	smm.add(getFormLookAndFeelAction("Reset to default", "Reset to default"));
	menuMgr.add(new Separator("addSwingThings"));

	if (comp.isMenuComponent()) {
	    if (comp.isSubclassOf(JMenuBar.class)) {
		menuMgr.add(getFormAddAction(JMenu.class, 0, "Add JMenu"));
	    }
	    if (comp.isSubclassOf(JMenu.class) || comp.isSubclassOf(JPopupMenu.class)) {
		menuMgr.add(getFormAddAction(JMenu.class, 0, "Add JMenu"));
		menuMgr.add(getFormAddAction(JMenuItem.class, 0, "Add JMenuItem"));
		menuMgr.add(getFormAddAction(JCheckBoxMenuItem.class, 0, "Add JCheckBoxMenuItem"));
		menuMgr.add(getFormAddAction(JRadioButtonMenuItem.class, 0, "Add JRadioButtonMenuItem"));
		menuMgr.add(getFormAddAction(JSeparator.class, 0, "Add JSeparator"));
	    }
	} else {
	    smm = new MenuManager("Surround by container");
	    menuMgr.add(smm);
	    smm.add(getFormSurroundAction(JScrollPane.class));
	    smm.add(getFormSurroundAction(JSplitPane.class));
	    smm.add(getFormSurroundAction(JTabbedPane.class));
	    smm.add(getFormSurroundAction(JPanel.class));
	    if (addMenuOpts) {
		MenuManager smm1 = new MenuManager("Add ...");
		menuMgr.add(smm1);
		fillAddMenu(smm1, true, false);
	    }
	}

	menuMgr.add(new Separator("setClassAction"));
	menuMgr.add(setClassAction);

	menuMgr.add(new Separator("Additions"));
	FormComponent parent = null;
	if (comp != null && comp.getParent() != null)
	    parent = comp.getParent();
	try {
	    if (parent != null && parent.getComponent() instanceof JTabbedPane && setSwingTabTitle != null) {
		menuMgr.add(setSwingTabTitle);
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}

	if (!comp.isMenuComponent()) {
	    if (comp != null && (comp.getLayoutWrapper().isSet() || !comp.usesCustomLayout())) {
		// add "set layout" options
		fillAddMenu(menuMgr, true, true);
	    }
	}

	String plt = comp.getParentSuperLayoutType();
	if ("Anchor".equals(plt)) {
	    menuMgr.add(anchorDialogAction);
	}

	if ("Anchor".equals(plt) || "Absolute".equals(plt)) {
	    boolean addSep = false;
	    if (getSelectedComponents().size() > 1 || gridSize > 0) {
		smm = new MenuManager("Align components...");
		menuMgr.add(smm);
		if (getSelectedComponents().size() > 1) {
		    smm.add(getFormAlignAction(3));
		    smm.add(getFormAlignAction(4));
		    smm.add(getFormAlignAction(1));
		    smm.add(new Separator());
		    smm.add(getFormAlignAction(0));
		    smm.add(getFormAlignAction(5));
		    smm.add(getFormAlignAction(2));
		    smm.add(new Separator());
		    smm.add(getFormAlignAction(7));
		    smm.add(getFormAlignAction(8));
		    smm.add(new Separator());
		    smm.add(getFormAlignAction(9));
		    smm.add(getFormAlignAction(10));
		    addSep = true;
		}
		if (gridSize > 0) {
		    if (addSep)
			smm.add(new Separator());
		    smm.add(getFormAlignAction(6));
		}
	    }
	} else if ("Border".equals(plt)) {
	    smm = new MenuManager("Set layout constraint...");
	    menuMgr.add(smm);
	    String prop = "direction";
	    addLayoutDataOption(smm, prop, BorderLayout.NORTH);
	    addLayoutDataOption(smm, prop, BorderLayout.SOUTH);
	    addLayoutDataOption(smm, prop, BorderLayout.EAST);
	    addLayoutDataOption(smm, prop, BorderLayout.WEST);
	    addLayoutDataOption(smm, prop, BorderLayout.CENTER);
	} else if ("Table".equals(plt)) {
	    smm = new MenuManager("Set horizontal alignment...");
	    menuMgr.add(smm);
	    String prop = "alignment";
	    addLayoutDataOption(smm, prop, "left");
	    addLayoutDataOption(smm, prop, "center-horiz", "center");
	    addLayoutDataOption(smm, prop, "right");
	    addLayoutDataOption(smm, prop, "fill-horiz", "fill");
	    smm = new MenuManager("Set vertical alignment...");
	    menuMgr.add(smm);
	    addLayoutDataOption(smm, prop, "top");
	    addLayoutDataOption(smm, prop, "center-vert", "center");
	    addLayoutDataOption(smm, prop, "bottom");
	    addLayoutDataOption(smm, prop, "fill-vert", "fill");
	} else if ("GridBag".equals(plt)) {
	    smm = new MenuManager("Set fill type...");
	    menuMgr.add(smm);
	    String prop = "fill";
	    addLayoutDataOption(smm, prop, GridBagConstraints.NONE, "NONE");
	    addLayoutDataOption(smm, prop, GridBagConstraints.BOTH, "BOTH");
	    addLayoutDataOption(smm, prop, GridBagConstraints.HORIZONTAL, "HORIZONTAL");
	    addLayoutDataOption(smm, prop, GridBagConstraints.VERTICAL, "VERTICAL");
	    smm = new MenuManager("Set anchor type...");
	    menuMgr.add(smm);
	    prop = "anchor";
	    addLayoutDataOption(smm, prop, GridBagConstraints.CENTER, "CENTER");
	    addLayoutDataOption(smm, prop, GridBagConstraints.EAST, "EAST");
	    addLayoutDataOption(smm, prop, GridBagConstraints.WEST, "WEST");
	    addLayoutDataOption(smm, prop, GridBagConstraints.NORTH, "NORTH");
	    addLayoutDataOption(smm, prop, GridBagConstraints.SOUTH, "SOUTH");
	    addLayoutDataOption(smm, prop, GridBagConstraints.NORTHEAST, "NORTHEAST");
	    addLayoutDataOption(smm, prop, GridBagConstraints.NORTHWEST, "NORTHWEST");
	    addLayoutDataOption(smm, prop, GridBagConstraints.SOUTHEAST, "SOUTHEAST");
	    addLayoutDataOption(smm, prop, GridBagConstraints.SOUTHWEST, "SOUTHWEST");
	} else if (parent != null && parent.isSubclassOf(JSplitPane.class)) {
	    smm = new MenuManager("Set layout constraint...");
	    menuMgr.add(smm);
	    String prop = "position";
	    if (parent.getIntPropertyValue("orientation") == JSplitPane.VERTICAL_SPLIT) {
		addLayoutDataOption(smm, prop, JSplitPane.TOP);
		addLayoutDataOption(smm, prop, JSplitPane.BOTTOM);
	    } else {
		addLayoutDataOption(smm, prop, JSplitPane.LEFT);
		addLayoutDataOption(smm, prop, JSplitPane.RIGHT);
	    }
	}

	addEditOptions(menuMgr, comp);
    }

    public void addLayoutDataOption(MenuManager smm, String prop, String constraint) {
	smm.add(getFormLayoutDataAction(prop, constraint, constraint, prop + "_" + constraint));
    }

    public void addLayoutDataOption(MenuManager smm, String prop, Object constraint, String label) {
	smm.add(getFormLayoutDataAction(prop, constraint, label, prop + "_" + constraint));
    }

    public void addLayoutDataOption(MenuManager smm, String prop, int constraint, String label) {
	smm.add(getFormLayoutDataAction(prop, new Integer(constraint), label, prop + "_" + label + "_" + constraint));
    }

    private void showContextMenu(FormComponent fc) {
	showContextMenu(getViewportControl(), fc);
    }

    public void showContextMenu(Control control, FormComponent fc) {
	initContextMenus(fc);
	if (fc.isSwing()) {
	    control.setMenu(swingMenu);
	    swingMenu.setVisible(true);
	} else {
	    control.setMenu(swtMenu);
	    swtMenu.setVisible(true);
	}
    }

    private boolean canRedo() {
	return true;
	// return actionStack.size() > actionStackPos + 1;
    }

    private boolean canUndo() {
	return true;
	// return actionStackPos >= 0;
    }

    private boolean allStringsExternalized = false;

    private void externalizeAllStrings() {
	allStringsExternalized = !allStringsExternalized;
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    fc.setPropertyValue("externalizeStrings", new Boolean(allStringsExternalized));
	}
    }

    public String getPackageName() {
	String packageName = "";
	IJavaElement ele = getWorkingCopy();
	while (ele.getParent() != null && ele.getParent().getElementType() == IJavaModel.PACKAGE_FRAGMENT) {
	    if ("".equals(packageName))
		packageName = ele.getParent().getElementName();
	    else
		packageName = ele.getParent().getElementName() + "." + packageName;
	    ele = ele.getParent();
	}
	return packageName;
    }

    protected void createActions() {
	if (undoAction != null)
	    return;

	undoAction = new Action("Undo") {
	    public void run() {
		doAction(IWorkbenchActionConstants.UNDO);
	    }
	};

	redoAction = new Action("Redo") {
	    public void run() {
		doAction(IWorkbenchActionConstants.REDO);
	    }
	};

	saveAction = new Action("Save") {
	    public void run() {
		doAction(IWorkbenchActionConstants.SAVE);
	    }
	};

	copyAction = new Action("Copy\tCtrl+C") {
	    public void run() {
		doAction(IWorkbenchActionConstants.COPY);
	    }
	};

	pasteAction = new Action("Paste\tCtrl+V") {
	    public void run() {
		doAction(IWorkbenchActionConstants.PASTE);
	    }
	};

	cutAction = new Action("Cut\tCtrl+X") {
	    public void run() {
		doAction(IWorkbenchActionConstants.CUT);
	    }
	};

	selectAllAction = new Action("Select All\tCtrl+A") {
	    public void run() {
		doAction(IWorkbenchActionConstants.SELECT_ALL);
	    }
	};

	deleteAction = new Action("Delete\tDelete") {
	    public void run() {
		doAction(IWorkbenchActionConstants.DELETE);
	    }
	};

	if (isJavaEditor) {
	    // if(getAction(IWorkbenchActionConstants.UNDO) == null) {
	    super.createActions();
	    // }
	}

	// if(propertyFileEditor != null)
	// propertyFileEditor.createActions();

	// clear *all* actions now - before formEditorActions is filled up, so
	// that the code and property undo/redo etc actions are unregistered
	undoAction.setActionDefinitionId("org.eclipse.ui.edit.undo");
	redoAction.setActionDefinitionId("org.eclipse.ui.edit.redo");
	saveAction.setActionDefinitionId("org.eclipse.ui.edit.save");
	cutAction.setActionDefinitionId("org.eclipse.ui.edit.cut");
	copyAction.setActionDefinitionId("org.eclipse.ui.edit.copy");
	pasteAction.setActionDefinitionId("org.eclipse.ui.edit.paste");
	// deleteAction.setActionDefinitionId("Delete");
	deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete");
	selectAllAction.setActionDefinitionId("org.eclipse.ui.edit.selectAll");

	unregisterActions(false);

	formEditorActions = new HashMap();

	formEditorActions.put(IWorkbenchActionConstants.UNDO, undoAction);
	formEditorActions.put(IWorkbenchActionConstants.REDO, redoAction);
	formEditorActions.put(IWorkbenchActionConstants.SAVE, saveAction);
	formEditorActions.put(IWorkbenchActionConstants.CUT, cutAction);
	formEditorActions.put(IWorkbenchActionConstants.COPY, copyAction);
	formEditorActions.put(IWorkbenchActionConstants.PASTE, pasteAction);
	formEditorActions.put(IWorkbenchActionConstants.DELETE, deleteAction);
	formEditorActions.put(IWorkbenchActionConstants.SELECT_ALL, selectAllAction);

	grabHorizAction = new Action("Grab Horiz Space", SWT.CHECK) {
	    public void run() {
		doGrabSpace(true);
	    }
	};
	grabVertAction = new Action("Grab Vert Space", SWT.CHECK) {
	    public void run() {
		doGrabSpace(false);
	    }
	};
	anchorDialogAction = new Action("Anchor...") {
	    public void run() {
		AnchorDialog ad = new AnchorDialog(getSite().getShell(), SWT.APPLICATION_MODAL);
		ad.setFormEditor(FormEditor.this);
		ad.open(10, 10);
	    }
	};

	extractAction = new Action("Extract to new form...") {
	    public void run() {
		FormComponent copy = selectedComp.getCopy(true, FormEditor.this);
		ExtractWizard ew = new ExtractWizard(selectedComp, copy, getJavaProject(), getWorkingCopy().getParent());
		ew.open(getSite().getShell());

	    }
	};

	javadocClassAction = new Action("Show Javadoc") {
	    public void run() {
		showJavadocForClass();
	    }
	};
	javadocLayoutAction = new Action("Show Javadoc") {
	    public void run() {
		showJavadocForLayout();
	    }
	};

	runAction = new Action("Run") {
	    public void run() {
		doRun();
	    }
	};
	moveUpAction = new Action("Move Up") {
	    public void run() {
		doMoveUp();
	    }
	};
	moveDownAction = new Action("Move Down") {
	    public void run() {
		doMoveDown();
	    }
	};
	renameAction = new Action("Rename") {
	    public void run() {
		doRename();
	    }
	};
	setClassAction = new Action("Change Class...") {
	    public void run() {
		doSetClass();
	    }
	};
	updateCodeStyleAction = new Action(getUpdateActionText()) {
	    public void run() {
		updateCodeToCurrentStyle();
	    }
	};
	openClassAction = new Action("Open class in new Editor") {
	    public void run() {
		openInNewEditor(getSelectedComponent().getTranslatedClassName());
	    }
	};
	reloadEditorAction = new Action("Reload Form Editor") {
	    public void run() {
		doReloadEditor();
	    }
	};
	convertToSWTResAction = new Action("Convert to using SWTResourceManager") {
	    public void run() {
		convertToSWTResMan();
	    }
	};
	insertShowGUIAction = new Action("Insert new showGUI method") {
	    public void run() {
		getJavaCodeParser().insertShowGUIMethod();
		setDirtyAndUpdate();
	    }
	};
	insertGetterAction = new Action("Create getter method for this element") {
	    public void run() {
		getJavaCodeParser().createSimpleGetterMethods(getSelectedComponents());
		setDirtyAndUpdate();
	    }
	};
	toggleLocalFieldDecAction = new Action("Toggle local field declaration") {
	    public void run() {
		getJavaCodeParser().toggleLocalFieldDec(getSelectedComponents());
		setDirtyAndUpdate();
	    }
	};
	insertGetGUIBInstSwingAction = new Action("Insert new getGUIBuilderInstance method") {
	    public void run() {
		getJavaCodeParser().insertGetGUIBuilderSwingMethod();
		setDirtyAndUpdate();
	    }
	};
	insertGetGUIBInstSWTAction = new Action("Insert new getGUIBuilderInstance method") {
	    public void run() {
		getJavaCodeParser().insertGetGUIBuilderSWTMethod();
		setDirtyAndUpdate();
	    }
	};
	showCodeAction = new Action("Goto Java Code") {
	    public void run() {
		showInJavaEditor(null);
	    }
	}; // setAction(ITextEditorActionConstants.CUT, cutAction);
    }

    public void addEditOptions(IMenuManager mgr, FormComponent fc) {

	MenuManager smm;

	if (fc != null && !fc.isMenuComponent()) {
	    openClassAction.setText("Open class \"" + fc.getShortClassName() + "\" in new Editor");
	    mgr.add(openClassAction);
	}

	mgr.add(new Separator("source"));

	mgr.add(toggleLocalFieldDecAction);

	smm = new MenuManager("Source...");

	mgr.add(smm);
	smm.add(updateCodeStyleAction);
	if (fc != null && fc.isSWT()) {
	    smm.add(new Separator("source1"));
	    smm.add(convertToSWTResAction);
	    if (useJavaCodeParser && getRootComponent().isSubclassOf(Composite.class)) {
		smm.add(new Separator("source2"));
		smm.add(insertGetterAction);
		smm.add(insertShowGUIAction);
		smm.add(insertGetGUIBInstSWTAction);
	    }
	} else if (fc != null && fc.isSwing()) {
	    smm.add(insertGetGUIBInstSwingAction);
	}

	if (fc != null) {

	    // createActions();
	    mgr.add(new Separator("editActions"));
	    smm = new MenuManager("Edit...");
	    mgr.add(smm);

	    smm.add(cutAction);
	    smm.add(copyAction);
	    smm.add(pasteAction);
	    if (clipboardComps.size() != 0)
		pasteAction.setEnabled(true);
	    else
		pasteAction.setEnabled(false);
	    smm.add(deleteAction);
	    smm.add(new Separator("moveActions"));
	    smm.add(moveUpAction);
	    if (fc.canMoveUp())
		moveUpAction.setEnabled(true);
	    else
		moveUpAction.setEnabled(false);
	    smm.add(moveDownAction);
	    if (fc.canMoveDown())
		moveDownAction.setEnabled(true);
	    else
		moveDownAction.setEnabled(false);
	    smm.add(new Separator("undoRedoActions"));
	    smm.add(undoAction);
	    if (canUndo()) {
		undoAction.setEnabled(true);
		undoAction.setText("Undo ");
		// + ((UndoableAction) actionStack
		// .elementAt(actionStackPos)).getDisplayName());
	    } else {
		undoAction.setText("Undo ");
		undoAction.setEnabled(false);
	    }
	    smm.add(redoAction);
	    if (canRedo()) {
		redoAction.setEnabled(true);
		redoAction.setText("Redo ");
		// + ((UndoableAction) actionStack
		// .elementAt(actionStackPos + 1))
		// .getDisplayName());
	    } else {
		redoAction.setText("Redo ");
		redoAction.setEnabled(false);
	    }

	    mgr.add(new Separator("renameAction"));
	    mgr.add(renameAction);
	    smm = new MenuManager("Show Javadoc for...");
	    mgr.add(new Separator("showJavadoc"));
	    mgr.add(smm);
	    javadocClassAction.setText(fc.getTranslatedClassName());
	    smm.add(javadocClassAction);
	    String lt = fc.getLayoutType();
	    if (lt != null && !lt.equals("Absolute") && fc.getLayoutWrapper().getMainClass() != null && !fc.usesCustomLayout()) {
		javadocLayoutAction.setText(fc.getLayoutWrapper().getMainClass().getName());
		smm.add(javadocLayoutAction);
	    }
	}

	EventWrapper ew = fc.getEventWrapper(false);
	if (ew != null) {
	    Vector handlers = new Vector();
	    ew.getHandlers(handlers);
	    if (handlers.size() > 0) {
		smm = new MenuManager("Goto event handler...");
		mgr.add(smm);
		for (int i = 0; i < handlers.size(); i++) {
		    String label = (String) handlers.elementAt(i);
		    if (label.endsWith(EventPropertyDescriptor.HANDLER_METHOD))
			label = label.substring(0, label.length() - EventPropertyDescriptor.HANDLER_METHOD.length());
		    else if (label.endsWith(EventPropertyDescriptor.INLINE))
			label = label.substring(0, label.length() - EventPropertyDescriptor.INLINE.length());
		    final String handler = label;
		    smm.add(new Action(label) {
			public void run() {
			    showInJavaEditor(handler);
			}
		    });
		}
	    }
	}

	mgr.add(new Separator("extraActions"));
	if (EDITOR_MODE == 0)
	    mgr.add(showCodeAction);
	mgr.add(extractAction);

	mgr.add(reloadEditorAction);

	// mgr.add(externalizeAllStringsAction);
	// mgr.add(runAction);
    }

    public boolean isToggling() {
	return toggling;
    }

    public boolean isRebuilding() {
	return rebuilding;
    }

    public boolean isParsing() {
	return updatesJavaCode() && getJavaCodeParser() != null && getJavaCodeParser().isParsing();
    }

    public void setRebuilding(boolean rebuilding) {
	this.rebuilding = rebuilding;
    }

    public void doToggle() {
	if (!canUseProfFeature("Toggle"))
	    return;
	if (!MessageDialog.openConfirm(getSite().getShell(), "Confirm toggle", "Really toggle between Swing and SWT?"))
	    return;

	ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
	pmd.open();
	FormComponent rc = getRootComponent();
	FormComponent mb = getMenuBar();
	int n = rc.getTotalChildCount();
	if (mb != null)
	    n += mb.getTotalChildCount();
	n *= 2;
	IProgressMonitor pm = pmd.getProgressMonitor();
	pm.beginTask("Toggling...", n);

	toggling = true;

	hideWindowFrames(selectedComps);
	if (isInSwingMode()) {
	    mode = MODE_SWT;
	} else {
	    mode = MODE_AWT_SWING;
	}
	if (swingMenu != null) {
	    swingMenu.dispose();
	    swtMenu.dispose();
	}
	swingMenu = null;
	swtMenu = null;

	// TODO problem - when toggling, properties are set before
	// component or control has been created, so methods to actually
	// set those properties on control/component are not invoked.
	// Maybe toggle method should create control/component and then
	// init properties (replacing the populateRoot call).

	root.toggle(pm);

	if (awtControl != null && !awtControl.isDisposed())
	    awtControl.dispose();
	awtControl = null;

	if (frameComp != null && !frameComp.isDisposed())
	    frameComp.dispose();
	frameComp = null;

	if (eventCatcher != null && !eventCatcher.isDisposed())
	    eventCatcher.dispose();
	eventCatcher = null;

	if (menuBar != null)
	    menuBar.toggle(pm);

	populateFormComponents();

	// toggling = false;
	root.setBoundsFromMap();
	// toggling = true;

	jcp.repairConstructor(root, true);
	jcp.toggle(!isInSwingMode());
	root.updateAllInCode(pm);
	if (menuBar != null) {
	    jcp.repairConstructor(menuBar, true);
	    menuBar.updateAllInCode(pm);
	}

	// if (view != null)
	// view.refreshMode();

	toggling = false;
	pmd.close();
	// root.updateUI();
	palette.setMode(mode);
	// setDirty(true);
	setDirtyAndUpdate(true);
	new DelayableRunnable(200, true) {
	    public void run() {
		reparseJavaCode(false);
	    }
	}.trigger();
    }

    /**
     * Displays labels with numbers at the top-left of the children of this
     * parent.
     */
    public void showTabNumbers(FormComponent parent, boolean show) {
	if (parent == null)
	    return;
	int cnt = parent.getNonInheritedChildCount();
	for (int i = 0; i < cnt; i++) {
	    parent.getNonInheritedChildAt(i).showTabNumber(show);
	}
    }

    public boolean isInSwingMode() {
	if (root != null) {
	    return root.isSwing();
	}
	return mode == MODE_AWT_SWING;
    }

    public boolean isInSWTMode() {
	if (root != null)
	    return root.isSWT();
	return mode == MODE_SWT;
    }

    public boolean isInCWTMode() {
	return mode == MODE_CWT;
    }

    public boolean isInAndroidMode() {
	return mode == MODE_ANDROID;
    }

    public boolean isInGWTMode() {
	return mode == MODE_GWT;
    }

    public void doPreview() {
	previewing = true;
	try {
	    if (isInSwingMode()) {

		FormComponent copy = root.getCopy(true, null);

		FormComponent mbc = null;
		if (getMenuBar() != null) {
		    mbc = getMenuBar().getCopy(true, null);
		    mbc.populateComponents(null, null);
		}

		Class rootClass = root.getMainClass();
		Window win = null;
		Container cont = null;

		if (isAssignableFrom(JDialog.class, rootClass)) {
		    try {
			Constructor con = ConstructorManager.findConstructor(rootClass, new Class[] { Frame.class });
			if (con != null)
			    win = (Window) con.newInstance(new Object[] { new JFrame() });
		    } catch (Throwable t) {
			jiglooPlugin.handleError(t);
		    }
		    if (win == null)
			win = new JDialog();
		    cont = ((JDialog) win).getContentPane();
		    // cont.setLayout(new BorderLayout(0, 0));
		} else if (isAssignableFrom(JInternalFrame.class, rootClass)) {
		    win = new JFrame();
		    cont = new JDesktopPane();
		    ((JFrame) win).setContentPane(cont);
		    Point ps = (Point) copy.getRawPropertyValue("preferredSize");
		    ((JDesktopPane) cont).setPreferredSize(new Dimension(ps.x, ps.y));
		} else if (isAssignableFrom(JFrame.class, rootClass) || isAssignableFrom(JApplet.class, rootClass) || isAssignableFrom(JWindow.class, rootClass)
			|| isAssignableFrom(Container.class, rootClass) || isAssignableFrom(AbstractFormBuilder.class, rootClass)) {
		    win = new JFrame();
		    cont = ((JFrame) win).getContentPane();
		    // cont.setLayout(new BorderLayout(0, 0));
		}

		copy.populateComponents(cont, null);

		if (isAssignableFrom(JInternalFrame.class, rootClass))
		    copy.getComponent().setVisible(true);

		if (root.isJFrame()) {
		    win = awtControlWindow;
		    cont = ((JFrame) win).getContentPane();
		}
		if (root.isJDialog()) {
		    win = awtControlWindow;
		    cont = ((JDialog) win).getContentPane();
		}
		if (root.isJWindow()) {
		    win = awtControlWindow;
		    cont = ((JWindow) win).getContentPane();
		}

		Component cc = copy.getComponent();
		JComponent jc = (JComponent) cc;
		Dimension sz = (Dimension) getRootSize(false, false);
		if (win instanceof JFrame) {
		    ((JFrame) win).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    if (root.isJFrame() || root.isJDialog())
			root.subtractWindowDecorationSizes(sz);
		} else if (win instanceof JDialog) {
		    ((JDialog) win).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    if (root.isJFrame() || root.isJDialog())
			root.subtractWindowDecorationSizes(sz);
		}

		jc.setPreferredSize(sz);

		// System.out.println("DO PREVIEW SET SIZE "+sz);

		String title = (String) copy.getPropertyValue("title");
		if (win instanceof JFrame) {
		    ((JFrame) win).setTitle(title);
		} else if (win instanceof JDialog) {
		    ((JDialog) win).setTitle(title);
		}

		if (mbc != null) {
		    JMenuBar mb = (JMenuBar) mbc.getComponent();
		    if (isAssignableFrom(JInternalFrame.class, rootClass)) {
			((JInternalFrame) jc).setJMenuBar(mb);
		    } else if (isAssignableFrom(JApplet.class, rootClass)) {
			((JFrame) win).setJMenuBar(mb);
		    } else if (win instanceof JFrame) {
			((JFrame) win).setJMenuBar(mb);
		    } else if (win instanceof JDialog) {
			((JDialog) win).setJMenuBar(mb);
		    }
		}

		// root.clearCopyInfo();
		final Window frame = win;
		jiglooPlugin.getDefault().addAllowedWindow(frame);
		try {
		    frame.pack();
		} catch (Throwable t) {
		    t.printStackTrace();
		}
		JiglooUtils.centerWindow(frame, getSite().getShell());
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			frame.setVisible(true);
			frame.toFront();
		    }
		});
		new DelayableRunnable(500) {
		    public void run() {
			frame.toFront();
		    }
		}.trigger();
	    } else {
		Shell shell = new Shell(getSite().getShell().getDisplay());
		FormComponent copy = root.getCopy(true, null);
		shell.setLayout(new FillLayout());
		copy.populateControls(shell, null, false);
		copy.refresh();
		Point sz = (Point) getRootSize(true, true);
		try {
		    if (getMenuBar() != null) {
			FormComponent mbc = getMenuBar().getCopy(true, null);
			mbc.populateControls(shell, this, false);
			shell.setMenuBar((Menu) mbc.getControl());
		    }
		} catch (Throwable e) {
		    jiglooPlugin.handleError(e);
		}
		root.clearCopyInfo();
		((Control) copy.getControl()).setEnabled(true);

		if (sz != null) {
		    shell.pack();
		    if (root.isA(Shell.class))
			shell.setSize(sz.x, sz.y);
		    else {
			Rectangle sz2 = shell.computeTrim(0, 0, sz.x, sz.y - getMenuBarHeight());
			shell.setSize(sz2.width, sz2.height);
		    }
		    shell.layout(true);
		} else {
		    shell.pack();
		}

		shell.open();
		JiglooUtils.centerShell(shell, getSite().getShell());
		shell.setVisible(true);

	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	previewing = false;
    }

    public Composite getFrameComp() {
	return frameComp;
    }

    public Container getSwingMainPanel() {
	return swingMainPanel;
    }

    public Composite getRootDecoration() {
	return getRootDecoration(true);
    }

    public Composite getRootDecoration(boolean createIfNeeded) {
	if (!createIfNeeded)
	    return rootDecoration;
	if (rootDecoration == null || rootDecoration.isDisposed()) {
	    if (jiglooPlugin.isLinux() || jiglooPlugin.isMacOS())
		rootDecoration = new LinuxDecorations(mainComp, SWT.SHELL_TRIM);
	    else
		rootDecoration = new Decorations(mainComp, SWT.SHELL_TRIM);
	}
	return rootDecoration;
    }

    private ControlListener contAd;

    public boolean isWorkbenchPart() {
	Class mc = getMainClass();
	if (mc == null) {
	    mc = jcp.getSuperClass();
	}
	if (mc != null && isAssignableFrom(WorkbenchPart.class, mc)) {
	    return true;
	}
	return false;
    }

    public void populateRoot() {
	// System.out.println(
	// "Scroll pane has " + scrollPane.getChildren().length + " children");
	// root.setSwing(isInSwingMode());
	root.clearCachedBounds();
	root.setEditor(this);

	boolean isWorkbenchPart = isWorkbenchPart();
	USE_INTERNAL_SHELL = true;

	if (isInSWTMode()) {

	    if (!root.isSubclassOf(Dialog.class) && !root.isSubclassOf(Composite.class))
		USE_INTERNAL_SHELL = false;

	    if (isWorkbenchPart)
		USE_INTERNAL_SHELL = false;

	    if (USE_INTERNAL_SHELL) {
		if (eventCatcher == null) {
		    eventCatcher = new Composite(mainComp, SWT.NULL);
		    eventCatcher.setLayout(new FillLayout());
		    eventCatcher.setEnabled(true);
		    eventCatcher.addMouseListener(mouseAd);
		    eventCatcher.addMouseMoveListener(mmList);
		    eventCatcher.addMouseTrackListener(mtList);
		    eventCatcher.addKeyListener(formEditorKeyAdapter);
		    if (EDITOR_MODE != 0) {
			eventCatcher.addFocusListener(focusListener);
			eventCatcher.addMouseListener(mouseFocusListener);
		    }
		    eventCatcher.setVisible(true);
		}
		if (frameComp == null) {
		    frameComp = new OrderableComposite(eventCatcher, SWT.NULL);
		    menuComp = new OrderableComposite(frameComp, SWT.NULL);
		    frameComp.setEnabled(false);
		}

	    } else if (isWorkbenchPart) {
		if (eventCatcher == null) {
		    eventCatcher = new CTabFolder(mainComp, SWT.CLOSE | SWT.BORDER);
		    eventCatcher.setEnabled(false);
		    CTabFolder ctf = (CTabFolder) eventCatcher;
		    try {
			ctf.setSimple(false);
			ctf.setMaximizeVisible(true);
			ctf.setMinimizeVisible(true);
		    } catch (Throwable t) {
		    }
		    // ctf.setBackground(getBGColor());
		    CTabItem cti = new CTabItem(ctf, SWT.CLOSE);
		    cti.setText(JiglooUtils.getUnqualifiedName(getClassName()));
		    frameComp = new OrderableComposite(ctf, SWT.NULL);
		    menuComp = new OrderableComposite(frameComp, SWT.NULL);
		    cti.setControl(frameComp);
		    ctf.setVisible(true);
		    ctf.setSelection(0);
		    ctf.layout();
		}
	    } else {
		if (frameComp == null) {
		    frameComp = new OrderableComposite(mainComp, SWT.NULL);
		    menuComp = new OrderableComposite(frameComp, SWT.NULL);
		}
	    }
	    ((IDummyShellSupplier) frameComp).useDummyShell(true);

	    frameComp.setLayout(new FormLayout());
	    menuComp.setLayout(new FillLayout(SWT.HORIZONTAL));
	    FormData data1 = new FormData();
	    data1.left = new FormAttachment(0, 0);
	    data1.right = new FormAttachment(100, 0);
	    data1.top = new FormAttachment(0, 0);
	    data1.height = 0;
	    menuComp.setLayoutData(data1);

	    frameComp.setVisible(true);
	    root.populateControls(frameComp, this, true);

	    root.refresh();

	    initRootControlLayoutData();

	    Point size = root.getSize();
	    if (root.isA(Shell.class)) {
		// for a Dialog (dialogShell)
		root.subtractWindowDecorationSizes(size);
		size.y -= getMenuHeight();
	    }
	    frameComp.setSize(size);
	    frameComp.layout();

	} else if (isInSwingMode()) {

	    if (!root.isSubclassOf(JFrame.class) && !root.isSubclassOf(JDialog.class))
		USE_INTERNAL_SHELL = false;

	    if (USE_INTERNAL_SHELL) {
		if (eventCatcher == null) {
		    eventCatcher = new Composite(mainComp, SWT.NULL);
		    eventCatcher.setLayout(new FillLayout());
		    eventCatcher.setEnabled(true);
		    eventCatcher.addMouseListener(mouseAd);
		    eventCatcher.addMouseMoveListener(mmList);
		    eventCatcher.addMouseTrackListener(mtList);
		    eventCatcher.addKeyListener(formEditorKeyAdapter);
		    if (EDITOR_MODE != 0) {
			eventCatcher.addFocusListener(focusListener);
			eventCatcher.addMouseListener(mouseFocusListener);
		    }
		    eventCatcher.setVisible(true);
		    awtControl = new AWTControl(eventCatcher, SWT.NULL, root);
		    awtControl.setEnabled(false);
		}
	    } else {
		if (awtControl == null)
		    awtControl = new AWTControl(mainComp, SWT.NULL, root);
	    }

	    if (harness != null) {
		harness.populateRoot(this);
	    }

	    awtControl.setVisible(true);
	    swingMainPanel = awtControl.getContentPane();
	    awtControl.setComponent(swingMainPanel);
	    root.setControl(awtControl);
	    if (root.isBaseComponent() && isSingleFrameApplication()) {
		String title = getBundleManager().getApplicationTitle();
		if (title != null) {
		    root.setPropertyValueDirect("title", title);
		    root.setPropertyValueDirect("text", title);
		}
	    }

	    if (harness != null) {

		root.setComponent(swingMainPanel);
		harness.populateFormComponents(root, null);
		root.initProperties();
	    } else {
		root.populateComponents(swingMainPanel, this);
	    }

	    if (root.isJInternalFrame()) {
		root.setPropertyValueDirect("visible", Boolean.TRUE);
	    }

	} else if (isInCWTMode()) {
	    if (awtControl == null) {
		awtControl = new AWTControl(mainComp, SWT.NULL, root);
		awtControl.setVisible(true);
		swingMainPanel = new com.cloudgarden.jigloo.typewise.WidgetHolder();
		awtControl.setComponent(swingMainPanel);
		root.setControl(awtControl);
		root.populateCwtWidgets(swingMainPanel, this);
	    }
	} else if (isInGWTMode()) {
	    if (awtControl == null) {
		// gwtControl = new BrowserWidget();
		awtControl.setVisible(true);
		swingMainPanel = new com.cloudgarden.jigloo.typewise.WidgetHolder();
		awtControl.setComponent(swingMainPanel);
		root.setControl(awtControl);
		root.populateCwtWidgets(swingMainPanel, this);
	    }
	}

	if (isInSWTMode())
	    mainControl = frameComp;
	else
	    mainControl = awtControl;

	if (isInSWTMode()) {
	    if (swtGlassPane == null)
		swtGlassPane = new SWTGlassPane();

	    swtGlassPane.setControl(mainControl);
	    swtGlassPane.setOpaque(false);
	    swtGlassPane.setDrawer(new Drawer() {
		public void draw(GC gc, int x, int y) {
		    if (snapTo != null) {
			snapTo.drawLines(gc, x, y + getMenuHeight());
		    }
		    if (isInSWTMode()) {
			FormComponent sel = moveTarget;
			if (sel == null)
			    sel = getSelectedComponent();
			if (sel != null) {
			    if (MigLayoutHandler.handlesLayout(sel)) {
				DrawUtils.drawGrid(gc, sel, x, y);
			    } else if (sel.getParent() != null && MigLayoutHandler.handlesLayout(sel.getParent())) {
				DrawUtils.drawGrid(gc, sel.getParent(), x, y);
			    }
			}
		    }
		}
	    });
	}

	// System.out.println("comp has " + cc + " children");
	// mainControl.setLocation(BORDER_X, BORDER_Y);

	// v4.0M3
	BORDER_X = 25;
	BORDER_Y = 25;

	if (USE_INTERNAL_SHELL) {

	    rootDecoration = getRootDecoration();
	    rootDecoration.setEnabled(false);

	    final int decH = jiglooPlugin.getWindowDecorationHeight();
	    final int decW = jiglooPlugin.getWindowDecorationWidth();
	    BORDER_X = 25 + decW / 2;
	    BORDER_Y = 25 + decH;
	    ControlListener contAd = new ControlAdapter() {
		public void controlResized(ControlEvent e) {
		    Rectangle b = mainControl.getBounds();
		    if (eventCatcher != null && !eventCatcher.isDisposed())
			eventCatcher.setBounds(BORDER_X, BORDER_Y, b.width, b.height);
		    // System.out.println("SET ROOT SIZE "+(b.width+decW)+",
		    // "+(b.height+decH));
		    if (rootDecoration != null && !rootDecoration.isDisposed()) {
			rootDecoration.setBounds(BORDER_X - decW / 2, BORDER_Y - decH + decW / 2, b.width + decW, b.height + decH);
		    }
		    if (eventCatcher != null && !eventCatcher.isDisposed())
			eventCatcher.layout();
		}
	    };
	    mainControl.addControlListener(contAd);
	    mainControl.setLocation(BORDER_X, BORDER_Y);
	    if (!isInSwingMode())
		contAd.controlResized(null);
	    mainControl.setEnabled(false);
	    rootDecoration.moveBelow(null);
	    rootDecoration.setVisible(true);

	} else if (isWorkbenchPart) {

	    contAd = new ControlAdapter() {
		public void controlResized(ControlEvent e) {
		    Rectangle b = mainControl.getBounds();
		    if (eventCatcher != null) {
			mainControl.removeControlListener(contAd);
			b = eventCatcher.computeTrim(0, 0, b.width, b.height);
			eventCatcher.setBounds(BORDER_X, BORDER_Y, b.width, b.height);
			mainControl.addControlListener(contAd);
		    }
		}
	    };
	    mainControl.addControlListener(contAd);
	    // mainControl is child of c2's CTabItem, so DON'T setLocation
	    contAd.controlResized(null);
	    mainControl.setEnabled(false);
	} else {
	    mainControl.setLocation(BORDER_X, BORDER_Y);
	    mainControl.setEnabled(false);
	}

	// root.refresh();

	mainComp.addPaintListener(new PaintListener() {
	    public void paintControl(final PaintEvent pe) {
		paintEditor(pe);
	    }
	});
    }

    /**
     * @return
     */
    public boolean isSingleFrameApplication() {
	String scn = jcp.getSuperclassName();
	return AppUtils.JAVAX_SWING_SFAPP_NAME.equals(scn);
    }

    public boolean isAppFrameworkApplication() {
	if (jcp == null)
	    return false;
	String scn = jcp.getSuperclassName();
	return AppUtils.JAVAX_SWING_APP_NAME.equals(scn) || AppUtils.JAVAX_SWING_SFAPP_NAME.equals(scn);
    }

    public boolean isPartOfAppFrameworkApplication() {
	if (jcp == null || !isInSwingMode())
	    return false;
	if (usesAppFramework == null) {
	    usesAppFramework = Boolean.FALSE;
	    if (isAppFrameworkApplication()) {
		usesAppFramework = Boolean.TRUE;
	    } else if (jcp.hasImportLike(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME)) {
		usesAppFramework = Boolean.TRUE;
	    } else if (true || getResourceFolder().exists()) {
		try {
		    if (cpLoader.findClass(AppUtils.JAVAX_SWING_APP_NAME) != null)
			usesAppFramework = Boolean.TRUE;
		} catch (Throwable t) {
		}
	    }
	}
	return usesAppFramework.booleanValue();
    }

    /**
    * 
    */
    public void initRootControlLayoutData() {
	Point rootSize = root.getSize();
	FormData data2 = new FormData();
	data2.top = new FormAttachment(menuComp);
	data2.left = new FormAttachment(0, 0);
	data2.right = new FormAttachment(100, 0);
	data2.bottom = new FormAttachment(100, 0);
	((Control) root.getControl()).setLayoutData(data2);
    }

    private static Color darkGray, white, black;

    private DelayableRunnable redrawDrun;

    public void redrawRoot() {
	if (redrawDrun == null) {
	    redrawDrun = new DelayableRunnable(100, true) {
		public void run() {
		    redrawRootNow();
		}
	    };
	}
	redrawDrun.trigger();
    }

    public void redrawRootNow() {
	try {
	    // System.out.println("REDRAW ROOT NOW");
	    if (isParsing()) {
		redrawRoot();
		return;
	    }

	    if (getRootComponent() == null)
		return;
	    Object robj = getRootComponent().getRootControl();
	    // if (!(robj instanceof Composite)) {
	    // jiglooPlugin.handleError(new RuntimeException(),
	    // "ROOT CONTROL NOT COMPOSITE " + robj + ", CWT-ENABLED="
	    // + jiglooPlugin.canUseCWT());
	    // return;
	    // }
	    Control root = (Control) robj;
	    if (root instanceof AWTControl) {
		// allow the AWTControl to resize itself if none of the size
		// properties is set
		AWTControl awtc = (AWTControl) root;
		FormComponent rc = getRootComponent();

		// System.out.println("AWTC GOT COMP SIZE (1)
		// "+awtc.getComponent().getSize());
		if (rc.isCWT()) {
		    awtc.setNeedsRepaint(true);
		    awtc.createImage();
		} else {

		    if (root instanceof Composite)
			((Composite) root).layout();

		    awtc.setNeedsRepaint(true);
		    Point rs = rc.getSize();

		    try {
			awtc.getComponent().validate();
		    } catch (IllegalStateException t1) {
			System.err.println("Error in redrawRoot " + t1);
		    } catch (Throwable t) {
			jiglooPlugin.handleError(t);
		    }
		    awtc.layoutInFrame();
		}
	    }

	    refresh(true);
	    if (awtControl != null)
		awtControl.createImage();

	    if (root != null) {
		getViewportControl().redraw();
		if (doUpdates)
		    getViewportControl().update();
	    }

	    // refresh(true);
	    // if (awtControl != null)
	    // awtControl.createImage();

	    updateScrollPaneSize();
	    realignWindowFrames();

	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    public AWTControl getAWTControl() {
	return awtControl;
    }

    private Window awtControlWindow = null;

    public void setAWTControlWindow(Window win) {
	if (isPreviewing())
	    awtControlWindow = win;
	else if (awtControl == null)
	    System.out.println("AWTCONTROL IS NULL");
	else
	    awtControl.setWindow(win);
    }

    private void paintEditor(PaintEvent pe) {
	if (root == null)
	    return;
	try {
	    Rectangle rb = root.getBounds();
	    pe.gc.setForeground(darkGray);
	    if (gridSize > 0) {
		int w = mainComp.getSize().x;
		int h = mainComp.getSize().y;
		for (int i = BORDER_X - 4 * gridSize; i < w; i += gridSize) {
		    pe.gc.drawLine(i, 0, i, h);
		}
		for (int j = BORDER_Y - 4 * gridSize; j < h; j += gridSize) {
		    pe.gc.drawLine(0, j, w, j);
		}
	    }

	    if (eventCatcher != null)
		return;

	    pe.gc.setForeground(black);
	    pe.gc.drawRectangle(rb.x + BORDER_X - 1, rb.y + BORDER_Y - 1, rb.width + 1, rb.height + 1);
	    pe.gc.setForeground(white);
	    pe.gc.drawRectangle(rb.x + BORDER_X - 2, rb.y + BORDER_Y - 2, rb.width + 3, rb.height + 3);
	    pe.gc.setForeground(black);
	    pe.gc.drawRectangle(rb.x + BORDER_X - 3, rb.y + BORDER_Y - 3, rb.width + 5, rb.height + 5);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public boolean isDisposed() {
	return disposed;
    }

    public void dispose() {
	try {
	    disposed = true;

	    super.dispose();

	    if (hitResult != null)
		hitResult.dispose();
	    hitResult = null;

	    editorMaximized = false;
	    if (formEditorSash != null && !formEditorSash.isDisposed()) {
		formEditorSash.setWeights(new int[] { 100, 0 });
	    }
	    resetSecurityManager();
	    unregisterFormEditor(this);
	    // unregisterActions(true);

	    if (wcopy != null)
		wcopy.getBuffer().removeBufferChangedListener(bufferListener);
	    bufferListener = null;
	    wcopy = null;

	    try {
		if (isDirty()) {
		    if (wcopy != null)
			wcopy.restore();
		    if (propertyFileEditor != null)
			propertyFileEditor.doRevertToSaved();
		}
	    } catch (Throwable e1) {
		System.err.println("Unable to restore working copy " + javaFile + ", " + e1);
	    }

	    if (view != null) {
		try {
		    getSite().getPage().hideView(view);
		} catch (Throwable e2) {
		    // e2.printStackTrace();
		}
	    }

	    try {
		if (javaEdPart != null)
		    javaEdPart.dispose();

		PlatformUI.getWorkbench().removeWindowListener(wlistener);
		wlistener = null;

		if (getEditorSite() != null && getEditorSite().getPage() != null)
		    getEditorSite().getPage().removePartListener(partListener);
		partListener = null;

		if (pstore != null && prefPropChangeListener != null) {
		    try {
			pstore.removePropertyChangeListener(prefPropChangeListener);
		    } catch (Throwable t) {
			jiglooPlugin.handleError(t);
		    }
		    prefPropChangeListener = null;
		}

		if (EDITOR_MODE != 0) {
		    if (primaryComposite != null && !primaryComposite.isDisposed())
			primaryComposite.removeFocusListener(focusListener);
		    Control jec = getJavaEditorControl();
		    if (jec != null && !jec.isDisposed()) {
			jec.removeFocusListener(focusListener);
			jec.removeMouseListener(mouseFocusListener);
		    }
		}
		if (mainComp != null && !mainComp.isDisposed()) {
		    mainComp.removeFocusListener(focusListener);
		    mainComp.removeMouseListener(mouseAd);
		    mainComp.removeMouseListener(mouseFocusListener);
		    mainComp.removeMouseTrackListener(mtList);
		    mainComp.removeMouseMoveListener(mmList);
		    mainComp.removeKeyListener(formEditorKeyAdapter);
		}
		if (eventCatcher != null && !eventCatcher.isDisposed()) {
		    eventCatcher.removeFocusListener(focusListener);
		    eventCatcher.removeMouseListener(mouseAd);
		    eventCatcher.removeMouseListener(mouseFocusListener);
		    eventCatcher.removeMouseTrackListener(mtList);
		    eventCatcher.removeMouseMoveListener(mmList);
		    eventCatcher.removeKeyListener(formEditorKeyAdapter);
		}
		focusListener = null;
		mouseFocusListener = null;
		mtList = null;
		mmList = null;
		mouseAd = null;
		formEditorKeyAdapter = null;
		scrollerRel = null;
		try {
		    javaEditor.removePropertyListener(propChangeList);
		} catch (Throwable t) {
		    jiglooPlugin.handleError(t);
		}
		propChangeList = null;

		UIManager.put("ClassLoader", null);

		getSite().getPage().removeSelectionListener(this);

	    } catch (Throwable e) {
		jiglooPlugin.handleError(e);
	    }

	    disposeComponents();

	    if (swingMenuMgr != null) {
		swingMenuMgr.removeAll();
		swingMenuMgr.dispose();
	    }

	    if (swtMenuMgr != null) {
		swtMenuMgr.removeAll();
		swtMenuMgr.dispose();
	    }

	    if (actions != null)
		actions.clear();
	    actions = null;

	    for (int i = 0; i < windowFramePool.size(); i++) {
		((WindowFrame) windowFramePool.elementAt(i)).dispose();
	    }
	    windowFramePool.clear();
	    Iterator it = windowFrames.keySet().iterator();
	    while (it.hasNext()) {
		((WindowFrame) windowFrames.get(it.next())).dispose();
	    }
	    windowFrames.clear();

	    if (gridEdgeManager != null)
		gridEdgeManager.disposeMarkers();

	    if (awtControl != null && !awtControl.isDisposed())
		awtControl.dispose();
	    awtControl = null;

	    if (eventCatcher != null && !eventCatcher.isDisposed())
		eventCatcher.dispose();
	    eventCatcher = null;

	    if (bundleManager != null)
		bundleManager.dispose();
	    bundleManager = null;

	    if (propertyFileEditor != null)
		propertyFileEditor.dispose();
	    propertyFileEditor = null;

	    if (harness != null)
		harness.dispose();
	    harness = null;

	    combinedUndoManager = null;
	    codeUndoManager = null;

	    if (primaryComposite != null && !primaryComposite.isDisposed()) {
		primaryComposite.dispose();
		primaryComposite = null;
	    }

	    if (EDITOR_MODE != 0) {
		if (javaEditor != null && !isJavaEditor) {
		    javaEditor.dispose();
		    javaEditor = null;
		}
	    }

	    if (fOutlinePage != null) {
		fOutlinePage.dispose();
		fOutlinePage = null;
	    }

	    if (formOutlinePage != null) {
		formOutlinePage.dispose();
		formOutlinePage = null;
	    }

	    if (internalFormOutline != null) {
		internalFormOutline.dispose();
		internalFormOutline = null;
	    }

	    if (internalJavaOutline != null) {
		internalJavaOutline.dispose();
		internalJavaOutline = null;
	    }

	    if (internalFormView != null) {
		internalFormView.dispose();
		internalFormView = null;
	    }

	    if (view != null) {
		// view.dispose();
		view = null;
	    }

	    structSel = null;
	    view = null;
	    javaFile = null;
	    jcp = null;
	    lastAddedComp = null;
	    formEditorSash = null;

	    mdieDRunnable = null;
	    activatedDrun = null;
	    grpActionDrun = null;
	    hider = null;
	    updateDrun = null;
	    notifier = null;
	    javaCodeSync = null;
	    scroller = null;
	    outlineDrun = null;
	    partListener = null;
	    marker = null;

	    resourceMap.clear();
	    resourceMap = null;
	    swingMainPanel = null;
	    genCode = null;
	    parser = null;
	    palette = null;
	    selectedComp = null;
	    selectedComps.clear();
	    selectedComps = null;

	    alignButtons = null;

	    javaEdPart = null;

	    if (formEditorSash2 != null && !formEditorSash2.isDisposed()) {
		formEditorSash2.dispose();
	    }
	    formEditorSash2 = null;

	    if (frameComp != null && !frameComp.isDisposed())
		frameComp.dispose();
	    frameComp = null;

	    if (mainComp != null && !mainComp.isDisposed())
		mainComp.dispose();
	    mainComp = null;

	    if (mainControl != null && !mainControl.isDisposed())
		mainControl.dispose();
	    mainControl = null;

	    menuComp = null;
	    swtGlassPane = null;
	    javaEditor = null;
	    toggleParseAction = null;
	    toggleLocalFieldDecAction = null;
	    extractAction = externalizeAllStringsAction = null;

	    copyAction = cutAction = deleteAction = pasteAction = undoAction = redoAction = selectAllAction = null;

	    codeEditorActions.clear();
	    codeEditorActions = null;

	    saveAction = moveUpAction = moveDownAction = renameAction = showCodeAction = setClassAction = runAction = updateCodeStyleAction = convertToSWTResAction = insertShowGUIAction = insertGetGUIBInstSwingAction = insertGetGUIBInstSWTAction = openClassAction = reloadEditorAction = javadocClassAction = javadocLayoutAction = anchorDialogAction = grabHorizAction = grabVertAction = null;

	    javaEditor = null;
	    newSecMan = null;
	    gridEdgeManager = null;

	    if (formEditorActions != null) {
		formEditorActions.clear();
		formEditorActions = null;
	    }

	    focusListener = null;
	    insertGetterAction = null;
	    listeners.clear();
	    listeners = null;
	    // memberClassesChanged.clear();

	    if (removedFields != null) {
		removedFields.clear();
		removedFields = null;
	    }

	    if (surroundActions != null) {
		surroundActions.clear();
		surroundActions = null;
	    }

	    if (windowFramePool != null) {
		windowFramePool.clear();
		windowFramePool = null;
	    }

	    if (windowFrames != null) {
		windowFrames.clear();
		windowFrames = null;
	    }

	    if (classCache != null) {
		clearClassCache();
		classCache = null;
	    }

	    dcAction = null;
	    if (flashLogButtonThread != null)
		flashLogButtonThread.interrupt();
	    flashLogButtonThread = null;
	    formFile = null;

	    cpLoader = null;

	    try {
		// releasing the working copy can cause an exception to be
		// thrown by the Java Outline Page
		releaseWorkingCopy();
		javaFileEditorInput = null;
	    } catch (Throwable e1) {
		System.err.println("Unable to release working copy " + javaFile + ", " + e1);
	    }

	    // JiglooUtils.stopAWT();
	    // System.gc();

	    // System.out.println("DISPOSED " + this +", " + javaFile);
	} catch (Throwable t) {
	    System.err.println("Error disposing " + this + ", " + getClassName());
	    jiglooPlugin.handleError(t);
	}
    }

    /**
     * 
     */
    private void disposeComponents() {

	if (harness != null)
	    harness.clear();

	if (root != null) {
	    root.dispose(true);
	    root.setRoot(false);
	}
	root = null;

	if (menuBar != null)
	    menuBar.dispose(true);
	menuBar = null;

	if (nonVisualRoot != null) {
	    nonVisualRoot.setDisposed(false);
	    nonVisualRoot.dispose(true);
	}
	nonVisualRoot = null;

	if (extraCompRoot != null) {
	    extraCompRoot.setDisposed(false);
	    extraCompRoot.dispose(true);
	}
	extraCompRoot = null;

	if (components != null) {
	    for (int i = 0; i < components.size(); i++) {
		FormComponent fc = (FormComponent) components.elementAt(i);
		fc.dispose(true);
	    }
	    components.clear();
	    components = null;
	}

	// cpLoader = null;

	// System.gc();
    }

    public FormComponent[] getSelectedComponentsAsArray() {
	FormComponent[] fcs = new FormComponent[selectedComps.size()];
	for (int i = 0; i < selectedComps.size(); i++) {
	    fcs[i] = getSelectedComponent(i);
	}
	return fcs;
    }

    public void doMoveUp() {
	if (selectedComps.size() == 0)
	    return;
	FormComponent[] fcs = getSelectedComponentsAsArray();
	JiglooUtils.sortComponents(fcs, false);
	doMoveOrCopy(null, -1000000, fcs, null, false);
    }

    public void doMoveDown() {
	if (selectedComps.size() == 0)
	    return;
	FormComponent[] fcs = getSelectedComponentsAsArray();
	JiglooUtils.sortComponents(fcs, false);
	doMoveOrCopy(null, 1000000, fcs, null, false);
	// JiglooUtils.sortComponents(selectedComps, true);
	// for (int i = 0; i < selectedComps.size(); i++) {
	// ((FormComponent) selectedComps.elementAt(i)).moveDown();
	// }
    }

    public void doGrabSpace(boolean horiz) {
	if (selectedComps.size() == 0)
	    return;
	String prop = "grabExcessHorizontalSpace";
	if (!horiz)
	    prop = "grabExcessVerticalSpace";
	boolean grabbed = getSelectedComponent().getBooleanLayoutDataProp(prop);
	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (fc.isSwing() || !"Grid".equals(fc.getParentSuperLayoutType()))
		continue;
	    LayoutDataWrapper ldw = fc.getLayoutDataWrapper().getCopy(fc);
	    ldw.setPropertyValue(prop, new Boolean(!grabbed));
	    fc.executeSetLayoutDataWrapper(ldw);
	}
    }

    private void copySelectionToClipboard(boolean cut) {
	String msg = null;
	// if(selectedComps.contains(getRootComponent()))
	// unselectComponent(getRootComponent(), true);

	FormComponent par = selectedComp.getParent(); // getCommonParent(selectedComps);
	if (selectedComps.contains(par))
	    unselectComponent(par, true);

	boolean group = false;
	if (par != null && par.usesGroupLayout())
	    group = true;

	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (fc.isBaseComponent()) {
		continue;
	    }
	    if (fc.isInherited()) {
		msg = "Inherited elements cannot be copied";
	    } else {
		FormComponent copy = fc.getCopy(true, this);
		if (cut)
		    fc.setWasCutForAll(true);

		clipboardComps.add(copy);
	    }
	}

	if (group) {
	    LayoutGroup hCopy = par.getLayoutWrapper().getHGroup().getSubGroup(clipboardComps);
	    LayoutGroup vCopy = par.getLayoutWrapper().getVGroup().getSubGroup(clipboardComps);
	    clipboardComps.clear();
	    clipboardComps.add(hCopy);
	    clipboardComps.add(vCopy);
	}

	if (msg != null)
	    displayWarning(msg, msg);
    }

    public static FormComponent getCommonParent(Vector comps) {
	FormComponent par = null;
	for (int i = 0; i < comps.size(); i++) {
	    FormComponent fc = (FormComponent) comps.elementAt(i);
	    if (!fc.isInherited()) {
		if (par == null) {
		    par = fc.getParent();
		} else if (!fc.getParent().equals(par)) {
		    par = null;
		    break;
		}
	    }
	}
	return par;
    }

    private boolean inCutMode = false;

    public boolean isInCutMode() {
	return inCutMode;
    }

    public void doDelete(boolean prompt) {
	doDelete(prompt, null);
    }

    public void doDelete(boolean prompt, ISelection selection) {
	if (textEditorActive) {
	    createActions();
	    getCodeEditorAction(IWorkbenchActionConstants.DELETE).run();
	    return;
	}
	// System.out.println("DO DELETE");
	if (selectedComps.size() == 0)
	    return;
	int cnt = selectedComps.size();
	boolean valid = false;
	for (int i = 0; i < cnt; i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (components.contains(fc))
		valid = true;
	}
	if (!valid)
	    return;
	if (prompt) {
	    String msg = "Really delete these " + cnt + " components?";
	    if (cnt == 1)
		msg = "Really delete " + getSelectedComponent().getNameInCode() + "?";
	    if (!MessageDialog.openConfirm(getSite().getShell(), "Confirm delete", msg))
		return;
	}

	if (selection != null) {
	    Vector treeObjs = JiglooUtils.selectionToVector(selection, false);
	    for (int i = 0; i < treeObjs.size(); i++) {
		TreeParent tp = (TreeParent) treeObjs.elementAt(i);
		FormComponent fc = tp.getFormComponent();
		if (fc != null && fc.isSubclassOf(AbstractAction.class)) {
		    FormComponent par = tp.getParent().getFormComponent();
		    if (par != null && par.hasProperty("action")) {
			par.setPropertyValue("action", "null");
			selectedComps.remove(fc);
		    }
		}
	    }

	    if (selectedComps.size() == 0)
		return;
	}

	UndoableGroupAction gua = new UndoableGroupAction();
	FormComponent selPar = null;
	JiglooUtils.sortComponents(selectedComps, true);
	String msg = null;
	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    if (fc.isBaseComponent())
		continue;
	    if (fc.isInherited()) {
		msg = "Inherited elements cannot be deleted";
	    } else {
		FormComponent par = fc.getParent();
		if (par != null && selPar == null)
		    selPar = par;
		if (par == null)
		    par = getRootComponent();
		UndoableAction ua = new UndoableEditAction(fc, par, fc.getIndexInParent(), fc.getBoundsCopy(), null, null, -1, null);
		gua.addAction(ua);
	    }
	}
	// v4.0M3
	selectedComps.clear();
	setSelectedComponent(selPar, false);
	if (gua.getActionCount() > 0)
	    executeUndoableActionNow(gua);
	if (msg != null)
	    displayWarning(msg, msg);
    }

    private boolean extractJavaEditor(IWorkbenchPart part) {
	if (EDITOR_MODE != 0)
	    return false;
	if (!(part instanceof IEditorPart))
	    return false;
	IEditorInput input = ((IEditorPart) part).getEditorInput();
	if (input instanceof FileEditorInput) {
	    FileEditorInput fin = (FileEditorInput) input;
	    if (fin.getFile().equals(javaFile)) {
		javaEditor = (CompilationUnitEditor) part;
		return true;
	    }
	}
	return false;
    }

    public ITextEditor getJavaEditor() {
	if (EDITOR_MODE != 0)
	    return javaEditor;
	IEditorReference[] eds = getSite().getPage().getEditorReferences();
	for (int i = 0; i < eds.length; i++) {
	    if (eds[i] != null && extractJavaEditor(eds[i].getEditor(false)))
		return javaEditor;
	}
	return null;
    }

    public String getFullClassName() {
	return getWorkingCopy().getType(getClassName()).getFullyQualifiedName();
    }

    public String getClassName() {
	String className = javaFile.getName();
	className = className.substring(0, className.length() - 5);
	return className;
    }

    public Class getMainClass() {
	Class mc = jcp.getMainClass();
	// if(mc == null)
	// mc = jcp.getSuperClass();
	return mc;
    }

    public void selectInJavaEditor(String methodName) {
	ASTNode mn = jcp.getMethodNode(methodName);
	if (isInTabbedMode())
	    showSourceTab();
	if (mn != null)
	    jcp.selectInCode(mn.getStartPosition());
    }

    public boolean showInJavaEditor(String methodName) {
	try {
	    ICompilationUnit icu = getWorkingCopy();
	    if (isInTabbedMode())
		showSourceTab();
	    if (EDITOR_MODE == 0) {
		JavaUI.openInEditor(icu);
	    }
	    if (methodName != null) {
		IType main = icu.getType(getClassName());
		IMethod[] meths = main.getMethods();
		for (int i = 0; i < meths.length; i++) {
		    if (methodName.equals(meths[i].getElementName())) {
			JavaUI.revealInEditor(javaEditor, (IJavaElement) meths[i]);
			return true;
		    }
		}
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	return false;
    }

    private static HashMap preDefinedConstructors;

    long lastModPDCFile = -1;

    private String getPreDefinedConstructorTemplate(String className) {
	loadPreDefinedConstructorTemplate(className, ".constructors");
	loadPreDefinedConstructorTemplate(className, "jigloo_constructors");
	if (preDefinedConstructors == null)
	    return null;
	return (String) preDefinedConstructors.get(className);
    }

    private void loadPreDefinedConstructorTemplate(String className, String fileName) {
	IFile conFile = getProjectFile(fileName);
	if (!conFile.exists())
	    return;
	java.io.File jfile = new java.io.File(conFile.getLocation().toOSString());
	long lastMod = jfile.lastModified();
	if (lastMod > lastModPDCFile || preDefinedConstructors == null) {
	    preDefinedConstructors = new HashMap();
	    Vector vec = getProjectFileAsVector(conFile.getName());
	    if (vec != null) {
		for (int i = 0; i < vec.size(); i++) {
		    String line = (String) vec.elementAt(i);
		    int pos = line.indexOf(";");
		    String cname = line.substring(0, pos);
		    preDefinedConstructors.put(cname, line.substring(pos + 1));
		}
	    }
	    lastModPDCFile = lastMod;
	}
    }

    public String getPredefinedConstructor(Object o) {
	try {
	    String clsName = o.getClass().getName();
	    String def = getPreDefinedConstructorTemplate(clsName);
	    if (def == null)
		return null;
	    if (jcp != null) {
		if (jiglooPlugin.useImports()) {
		    jcp.addImport(clsName);
		    clsName = JiglooUtils.getUnqualifiedName(clsName);
		}
	    }
	    String code = "new " + clsName + "(";
	    String[] parts = JiglooUtils.split(";", def);
	    for (int i = 0; i < parts.length; i++) {
		String name = "get" + JiglooUtils.capitalize(parts[i]);
		Method meth;
		try {
		    meth = o.getClass().getMethod(name, null);
		} catch (Exception e) {
		    meth = o.getClass().getMethod(parts[i], null);
		}
		if (i != 0)
		    code += ", ";
		Object res = meth.invoke(o, null);
		if (res instanceof String) {
		    code += "\"" + res + "\"";
		} else {
		    code += res.toString();
		}
	    }
	    code += ")";
	    return code;
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
	return null;
    }

    public IFile getProjectFile(String fileName) {
	return getProject().getFile(fileName);
    }

    public Vector getProjectFileAsVector(String fileName) {
	try {
	    Vector vec = new Vector();
	    IFile file = getProjectFile(fileName);
	    InputStream in = file.getContents();
	    DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
	    while (dis.available() > 0) {
		vec.add(dis.readLine());
	    }
	    return vec;
	} catch (Throwable e) {
	    System.err.println("Unable to read file " + fileName + ", " + e);
	}
	return null;
    }

    public void displayMessage(String title, String msg) {
	MessageDialog.openInformation(getSite().getShell(), title, msg);
    }

    public void displayWarning(String title, String msg) {
	MessageDialog.openWarning(getSite().getShell(), title, msg);
    }

    public void displayError(String title, String msg) {
	MessageDialog.openError(getSite().getShell(), title, msg);
    }

    public boolean renameField(Shell shell, String oldName, String newName) {
	try {
	    ICompilationUnit wcopy = getWorkingCopy();
	    IType[] types = wcopy.getTypes();
	    IField field = types[0].getField(oldName);
	    RenameSupport rs = RenameSupport.create(field, newName, RenameSupport.UPDATE_JAVADOC_COMMENTS | RenameSupport.UPDATE_REGULAR_COMMENTS | RenameSupport.UPDATE_SETTER_METHOD
		    | RenameSupport.UPDATE_GETTER_METHOD | RenameSupport.UPDATE_STRING_LITERALS | RenameSupport.UPDATE_REFERENCES);
	    rs.perform(shell, new ProgressMonitorDialog(shell));
	    wcopy.reconcile();
	    // System.out.println("RENAMED " + oldName + ", as " + newName);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	    return false;
	}
	return true;
    }

    public boolean renameMethod(Shell shell, String oldName, String newName) {
	try {
	    ICompilationUnit wcopy = getWorkingCopy();
	    IType[] types = wcopy.getTypes();
	    IMethod[] meths = types[0].getMethods();
	    IMethod meth = null;
	    for (int i = 0; i < meths.length; i++) {
		IMethod method = meths[i];
		if (method.getElementName().equals(oldName)) {
		    meth = method;
		    break;
		}
	    }
	    if (meth == null)
		return false;
	    RenameSupport rs = RenameSupport.create(meth, newName, RenameSupport.UPDATE_REFERENCES);
	    rs.perform(shell, new ProgressMonitorDialog(shell));
	    wcopy.reconcile();
	    // System.out.println("RENAMED " + oldName + ", as " + newName);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	    return false;
	}
	return true;
    }

    public void doRename() {
	FormComponent sel = getSelectedComponent();
	if (sel == null)
	    return;
	if (sel.isInherited()) {
	    displayWarning("Cannot perform operation", "Inherited elements cannot be renamed");
	    return;
	}
	String oldName = sel.getNameInCode();
	InputDialog id = new InputDialog(getSite().getShell(), "Enter new name", "Enter new name", oldName, null);
	id.open();
	String newName = id.getValue();
	if (newName == null)
	    return;
	if (!JiglooUtils.isValidJavaName(newName)) {
	    displayWarning("Invalid Java identifier", "The name you typed is not a valid Java identifier name");
	    return;
	}
	id.close();
	if (useJavaCodeParser) {
	    try {
		String oldFCName = oldName;
		// if(sel.isPropertySet("name")) {
		// oldFCName = (String) sel.getPropertyValue("name");
		// sel.setPropertyValue("name", newName);
		// flushActions();
		// }
		if (renameField(getSite().getShell(), oldName, newName)) {
		    sel.setName(newName, true, oldFCName);

		    HashMap eventRM = sel.getRenamedEventHandlers();
		    Iterator it = eventRM.keySet().iterator();
		    while (it.hasNext()) {
			String oldEHName = (String) it.next();
			String newEHName = (String) eventRM.get(oldEHName);
			renameMethod(getSite().getShell(), oldEHName, newEHName);
		    }
		    bundleManager.updateProperties(true);
		    bundleManager.doSave();
		    javaCodeSync.cancel();
		    reparseJavaCode(false);
		} else {
		    // if(oldFCName != null) {
		    // sel.setPropertyValue("name", oldFCName);
		    // flushActions();
		    // }
		}
	    } catch (Throwable e) {
		jiglooPlugin.handleError(e);
	    }
	}

    }

    public void doSetClass() {
	if (!canUseProfFeature("Set Class"))
	    return;
	FormComponent sel = getSelectedComponent();
	if (sel == null)
	    return;
	if (sel.isInherited()) {
	    displayWarning("Cannot perform operation", "Inherited elements cannot be re-classed");
	    return;
	}

	Vector selCopy = (Vector) selectedComps.clone();

	try {
	    setCursor(CursorManager.getCursor(SWT.CURSOR_WAIT));
	    SelectionDialog dialog;
	    dialog = JavaUI.createTypeDialog(getSite().getShell(), new ProgressMonitorDialog(getSite().getShell()), javaFile.getProject(), IJavaElementSearchConstants.CONSIDER_CLASSES, false);
	    dialog.setTitle("Select new class");
	    dialog.setMessage("Select new class");
	    if (dialog.open() == IDialogConstants.CANCEL_ID)
		return;
	    Object[] types = dialog.getResult();
	    dialog.close();
	    if (types == null || types.length == 0)
		return;
	    String newName = ((IType) types[0]).getFullyQualifiedName();

	    Class clazz = loadClass(newName);
	    if (clazz == null) {
		displayWarning("Class not found", "Unable to find class\n   " + newName + "\nCheck that it exists in this project and that it has" + " been built (press Ctrl+B to build)");
		return;
	    }

	    for (int i = 0; i < selectedComps.size(); i++) {
		sel = getSelectedComponent(i);
		if (sel.isInherited()) {
		    displayWarning("Cannot perform operation", "Inherited elements cannot be re-classed - " + sel.getNameInCode());
		    continue;
		}

		sel.changeClass(newName);
	    }

	    setSelectedComponents(selCopy);

	    setDirtyAndUpdate();
	    // makes sure GroupLayout is rendered correctly
	    reparseJavaCode(false);

	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private boolean ignoreChanges = false;

    private UndoableGroupAction grpUndoAction = null;

    private DelayableRunnable grpActionDrun = new DelayableRunnable(50, true) {
	public void run() {
	    if (grpUndoAction == null)
		return;
	    try {
		grpUndoAction.setEditor(FormEditor.this);
		grpUndoAction.redo();
		grpUndoAction = null;
		setDirtyAndUpdate();
	    } catch (Throwable t) {
		jiglooPlugin.handleError(t);
	    }
	}
    };

    private static long timestamp = -1;

    public static void mark(boolean reset, String msg) {
	if (reset)
	    timestamp = System.currentTimeMillis();
	long now = System.currentTimeMillis();
	System.out.println((now - timestamp) + ": " + msg);
	timestamp = now;
    }

    public void executeUndoableAction(UndoableAction ua) {
	if (ignoreChanges)
	    return;

	// DON'T call executeUndoableActionNow since if multiple proprties are
	// set (eg, setting
	// bounds in a multi-element move) then setDirtyAndUpdate will be called
	// multiple times
	// and will actually negate the moves on all but the first selected
	// element

	if (grpUndoAction == null)
	    grpUndoAction = new UndoableGroupAction();
	grpUndoAction.addAction(ua);
	grpActionDrun.trigger();
    }

    public void executeUndoableActionNow(UndoableAction ua) {
	if (ignoreChanges)
	    return;
	try {
	    // make sure that the editor is parsing, and has just parsed the
	    // code if it wasn't parsing already- this ensures that any changes
	    // made to the code while parsing was disabled are not discarded
	    ua.setEditor(this);
	    ua.redo();
	    setDirtyAndUpdate();
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    private boolean allowMoveCursor = true;

    public void selectInCode(FormComponent fc, String propName, boolean showSourceTab) {
	// System.out.println("SELECT IN CODE " + fc + ", " + propName);
	if (showSourceTab && isInTabbedMode()) {
	    showSourceTab();
	}
	if (!allowMoveCursor)
	    return;
	if (jcp != null)
	    jcp.selectInCode(fc, propName);
    }

    public void setDirty(boolean dirty) {
	try {
	    if (populating || previewing || isParsing() || isUpdatingClass())
		return;
	    if (dirty) {
		updateDrun.trigger();
	    }
	    firePropertyChange(PROP_DIRTY);
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    public void setDirtyAndUpdate() {
	setDirtyAndUpdate(true);
    }

    public void setDirtyAndUpdate(boolean notify) {
	setDirtyAndUpdate(notify, true);
    }

    public void setDirtyAndUpdate(boolean notify, boolean updateUI) {
	setDirtyAndUpdate(notify, updateUI, true);
    }

    public void setDirtyAndUpdate(boolean notify, boolean updateUI, boolean activate) {
	if (populating || previewing || isParsing() || isUpdatingClass() || pauseUpdate)
	    return;

	if (harness != null)
	    harness.updateFiles();

	setSnapTo(null);

	checkLicenseComment();

	// set pauseUpdate while flushing actions because flushActions can
	// itself
	// cause setDirtyAndUpdate to be called!
	pauseUpdate = true;
	flushActions();
	pauseUpdate = false;

	boolean propChanged = false;
	boolean codeChanged = false;

	if (swtGlassPane != null)
	    swtGlassPane.setControl(mainControl);

	if (bundleManager != null && bundleManager.propertyChanged()) {
	    propChanged = true;
	    bundleManager.updateProperties(true);
	    bundleManager.addInjectComponentsCall(jcp);
	}

	ignoreChanges = true;
	structSel = new StructuredSelection(selectedComps);

	// setting the view selection can cause an endless loop, if this
	// method was called as a result of a property being set in
	// the form view.
	if (view != null && notify)
	    view.selectionChanged(FormEditor.this, structSel);

	if (jcp != null && jcp.bufferChanged()) {
	    codeChanged = true;

	    checkLicenseComment();
	    if (jcp != null)
		jcp.updateWCBuffer();
	    try {
		getCodeUndoManager().commit();
	    } catch (Throwable t) {
		t.printStackTrace();
	    }
	}

	if (codeChanged || propChanged) {
	    setDirty(true);
	    handleChange(codeChanged);
	}

	// notifyListeners(true, true);
	if (root == null) {
	    ignoreChanges = false;
	    return;
	}

	if (updateUI) {
	    root.updateUIForAll();
	    if (menuBar != null)
		menuBar.updateUIForAll();

	    setWindowFramesMoveMode(false);

	    // redrawRootNow will be called when refresh(true) is called, since
	    // that calls root.setPropertyValueInternal("bounds", r, false);

	    needsReload = true;

	    if (root != null && root.isDisposed()) {
		FormComponent bc = getBaseComponent();
		bc.bringToFront(true);
		setRootComponent(bc);
	    }

	    redrawRootNow();

	}

	ignoreChanges = false;
	inCutMode = false;

	if (activate) {
	    // send the focus back to the main editor (if a property has been
	    // changed in the property
	    // editor, say, so that ctrl+Z will work, for instance.
	    activateThis();
	    setFocus();
	}

	setCursor(getDefCursor());
	clearCachedBounds();

	if (selectedComp != null) {
	    if (!components.contains(selectedComp) || !selectedComps.contains(selectedComp)) {
		hideWindowFrame(selectedComp);
		selectedComp = null;
	    }
	}

	if (addedComponent != null)
	    setSelectedComponent(addedComponent, true);
	addedComponent = null;

	if (currentAction instanceof FormAddAction && ((FormAddAction) currentAction).getActionParent() != null) {
	    setSelectedComponent(((FormAddAction) currentAction).getActionParent(), true);
	}

	setMoveTarget(null);

	if (selectedComp != null && selectedComp.isMenuComponent())
	    selectedComp.getEditorMenu().show();

	if (selectedComp != null) {
	    if (awtControl != null) {
		awtControl.handleSelectionChanged(selectedComp);
		awtControl.update();
	    }
	    showWindowFrame(selectedComp);
	}

	getGridEdgeManager().showGridEdgeMarkers(selectedComp);
	if (swtGlassPane != null)
	    swtGlassPane.redraw();

	scrollFormTo(getSelectedComponent());
    }

    private void clearCachedBounds() {
	if (root != null)
	    root.clearCachedBounds();
	if (menuBar != null)
	    menuBar.clearCachedBounds();
    }

    private boolean canUseProfFeature(String action) {
	return jiglooPlugin.getDefault().canUseProfFeature(getSite(), action);
    }

    public void doRun() {
	if (!canUseProfFeature("Run"))
	    return;
	ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
	pmd.setBlockOnOpen(false);
	pmd.setCancelable(true);
	pmd.create();
	try {
	    IProgressMonitor monitor = pmd.getProgressMonitor();
	    if (isDirty() && MessageDialog.openQuestion(getSite().getShell(), "Save?", "Save " + getClassName() + " before building and running?")) {
		pmd.open();
		monitor.setTaskName("Saving");
		doSave(monitor);
	    } else {
		pmd.open();
	    }
	    doBuild(monitor);
	    monitor.setTaskName("Launching");
	    ICompilationUnit cu = getWorkingCopy();
	    String className = cu.getType(getClassName()).getFullyQualifiedName();
	    if (root.isSubclassOf(Dialog.class)) {
		Class cls = loadClass(className);
		Class[] types = new Class[] { String[].class };
		Object[] params = new Object[] { new String[] {} };
		pmd.close();
		cls.getMethod("main", types).invoke(null, params);
		System.out.println("INVOKED MAIN ON " + cls);
		return;
	    }
	    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
	    ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
	    ILaunchConfigurationWorkingCopy wc;
	    wc = type.newInstance(null, getClassName());
	    String projName = javaFile.getProject().getName();
	    // System.err.println("RUN " + projName + ", " + className);
	    wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projName);
	    wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, className);
	    String vmArgs = "-Djava.library.path=\"" + JiglooUtils.getProjectBase(javaFile.getProject()) + "\"";
	    // System.out.println(
	    // "Running " + getClassName() + ", VM_ARGS=" + vmArgs);
	    wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmArgs);
	    ILaunchConfiguration config = wc.doSave();
	    pmd.close();
	    config.launch(ILaunchManager.RUN_MODE, null);
	    // wc.launch(ILaunchManager.RUN_MODE, monitor);
	    return;
	} catch (Throwable e) {
	    jiglooPlugin.handleError(getSite().getShell(), "Error in launch", "Error launchng class", e);
	} finally {
	    pmd.close();
	}
    }

    public boolean isPopulating() {
	return populating;
    }

    private long endOfPopulating = -1;

    public boolean justStoppedPopulating() {
	long now = new Date().getTime();
	return now - endOfPopulating < 3000L;
    }

    private void setPopulating(boolean pop) {
	populating = pop;
	if (!pop)
	    endOfPopulating = new Date().getTime();
    }

    public boolean isPreviewing() {
	return previewing;
    }

    public void refreshComponents(Vector comps) {
	for (int i = 0; i < comps.size(); i++) {
	    ((FormComponent) comps.elementAt(i)).refresh();
	}
    }

    public FormComponent pasteComponent(FormComponent target, FormComponent fc, int position, boolean copy) {

	if (!fc.isVisual()) {
	    target = nonVisualRoot;
	}

	if (fc.isSwing()) {
	    if (fc.isSubclassOf(JDialog.class) || fc.isSubclassOf(JPopupMenu.class))
		target = nonVisualRoot;
	}

	if (extraCompRoot.equals(target)) {
	    fc.setParent(target);
	    target.add(fc, position);
	    fc.getInheritedElements();
	    return fc;
	}

	if (!fc.canMoveToParent(target)) {
	    displayError("Can't add to " + target, "Can't add a\n" + fc.getTranslatedClassName() + "\ninto a\n" + target.getTranslatedClassName());
	    return null;
	}

	setNeedsReload(true);
	try {
	    if (copy)
		fc = fc.getCopy(true, this);
	    fc.setAllExistsInCode(true);
	    if ((fc.isJMenuBar() || fc.isA(Menu.class)) && target.equals(getRootComponent())) {
		setMenuBar(fc, true);
		return fc;
	    } else {
		addComponent(fc);
	    }

	    if (position < 0)
		position = target.getChildCount();
	    target.getChildren().add(position, fc);
	    fc.setParent(target);
	    fc.initLayoutConstraint(fc);
	    // TODO here...
	    target.add(fc, position);
	    fc.getInheritedElements();
	    if (!fc.isVisual()) {
		fc.populateNonVisualComponents(this);
	    } else {
		if (fc.isSwing()) {
		    fc.populateComponents((Container) target.getComponent(), this);
		} else if (fc.isCWT()) {
		    fc.populateCwtWidgets((Container) target.getComponent(), this);
		} else if (fc.isSWT()) {
		    fc.populateControls(target.getRawControl(), this, true);
		}
	    }
	    // TODO ...or here (first doesn't cause error, but orig posn is
	    // here).
	    // target.add(fc, position);
	    fc.setVisible(true);
	    return fc;
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	return null;
    }

    private boolean needsReload = false;

    public void setNeedsReload(boolean needsReload) {
	this.needsReload = needsReload;
    }

    public void reloadOutline() {
	if (!needsReload)
	    return; // new Exception().printStackTrace();
	if (formOutlinePage != null)
	    formOutlinePage.reload();
	if (internalFormOutline != null)
	    internalFormOutline.reload();
	needsReload = false;
    }

    public boolean isNetbeans() {
	return isNetbeans;
    }

    public void doSave(IProgressMonitor monitor) { // System.out.println("DO_SAVE");

	if (!isDirty() || isSaving)
	    return;
	if (isNetbeans && !jiglooPlugin.getDefault().canSaveNBFile(getSite().getShell()))
	    return;
	// after being saved, it is no longer in netbeans format.
	isNetbeans = false;

	isSaving = true;
	try {
	    ProgressMonitorDialog pmd = null;
	    if (monitor == null) {
		monitor = getProgressMonitor();
	    }

	    boolean pe = parsingEnabled;
	    checkParsing();
	    checkLicenseComment();

	    if (propertyFileEditor != null)
		propertyFileEditor.doSave(monitor);

	    if (harness != null)
		harness.doSave(monitor);

	    monitor.beginTask("Saving java code", 2);
	    commitWorkingCopy(monitor);
	    monitor.worked(1);
	    if (jiglooPlugin.exportFormFile() || !updateJavaCode) {
		saveToFile(root, formFile, false);
	    }

	    String pName = getJavaProjectName();
	    String cName = getFullClassName();

	    monitor.done();
	    if (pmd != null)
		pmd.close();
	    enableParsing(pe);
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
	isSaving = false;
    }

    private boolean checkLicenseComment() {
	if (jcp != null && !jiglooPlugin.getDefault().licenseValid() && !jcp.hasJiglooComment()) {
	    jcp.insertJiglooComment();
	    // updateWorkingCopy(true);
	    // setDirty(false);
	    return true;
	}
	return false;
    }

    public static void registerFormEditor(FormEditor fed) {
	if (formEditors.contains(fed))
	    return;
	formEditors.add(fed);
    }

    public static void unregisterFormEditor(FormEditor fed) {
	if (!formEditors.contains(fed))
	    return;
	formEditors.remove(fed);
	// if(formEditors.size() == 0) {
	// ClassLoaderCache.clearClassLoaders();
	// }
	jiglooPlugin.editorClosed(fed);
    }

    // public static void classUpdated(FormEditor editor, String projName,
    // String cls) {
    // for (int i = 0; i < formEditors.size(); i++) {
    // FormEditor fed = (FormEditor) formEditors.elementAt(i);
    // fed.handleClassUpdated(editor, projName, cls);
    // }
    // }

    public static void resourceMoved(IPath fromPath, IPath toPath) {
	for (int i = 0; i < formEditors.size(); i++) {
	    FormEditor cul = (FormEditor) formEditors.elementAt(i);
	    cul.handleResourceMoved(fromPath, toPath);
	}
    }

    private boolean resourceMoved = false;

    public void handleResourceMoved(IPath fromPath, IPath toPath) {
	if (javaFile.getFullPath().equals(fromPath)) {
	    resourceMoved = true;
	}
	if (propertyFileEditor != null && ((FileEditorInput) propertyFileEditor.getEditorInput()).getFile().getFullPath().equals(fromPath)) {
	    IFile propFile = getProject().getWorkspace().getRoot().getFile(toPath);
	    bundleManager.setPropertyFile(propFile);
	}
	resourceMoved = true;
    }

    public static void propertyFileChanged(IFile file) {
	for (int i = 0; i < formEditors.size(); i++) {
	    FormEditor cul = (FormEditor) formEditors.elementAt(i);
	    cul.handlePropertyFileChanged(file);
	}
    }

    public static void classpathChanged(IProject proj) {
	ClassLoaderCache.deleteClassLoader(proj);
	for (int i = 0; i < formEditors.size(); i++) {
	    FormEditor cul = (FormEditor) formEditors.elementAt(i);
	    cul.handleClassPathChanged(proj);
	}
    }

    /**
     * @param file
     */
    public static void classFileChanged(String className, IProject proj) {
	// do a first pass to clear the class loaders from cache and editor only
	// if needed
	for (int i = 0; i < formEditors.size(); i++) {
	    FormEditor fed = (FormEditor) formEditors.elementAt(i);
	    if ((fed.getProject().equals(proj) || ClassLoaderCache.isProjectReferenced(fed.getJavaProject(), proj)) && fed.dependsOnClass(className)) {
		ClassLoaderCache.deleteClassLoader(fed.getProject());
		fed.clearClassLoader();
	    }
	}
	// second pass to reload editors that need to be reloaded.
	// will create a new class loader (once per project) if needed
	for (int i = 0; i < formEditors.size(); i++) {
	    FormEditor fed = (FormEditor) formEditors.elementAt(i);
	    // if (fed.getProject().equals(proj))
	    fed.handleClassCompiled(className);
	}
    }

    @Override
    protected boolean isActivePart() {
	if (editorMaximized) {
	    // return false when maximized so that the doSelectionChanged method
	    // of JavaEditor will move the cursor
	    return false;
	} else {
	    return super.isActivePart();
	}
    }

    protected void doSetInput(IEditorInput input) throws CoreException {
	boolean inputChanged = false;
	IEditorInput oldInput = getEditorInput();
	if ((oldInput == null && input != null) || (oldInput != null && input == null) || (!oldInput.equals(input))) {
	    inputChanged = true;
	    releaseWorkingCopy();
	}
	super.doSetInput(input);
	if (inputChanged) {
	    // System.out.println("doSetInput - reload "+getEditorInput()+", "+
	    // ((FileEditorInput)getEditorInput()).getFile());
	    try {
		javaFile = ((FileEditorInput) getEditorInput()).getFile();
		getWorkingCopy();
		if (oldInput != null)
		    reparseJavaCode(true);
		resourceMoved = false;
	    } catch (Throwable t) {
		t.printStackTrace();
	    }
	}
    }

    public void saveToFile(FormComponent comp, String xmlFile, boolean checkExists) {
	IFile eif = ((IFileEditorInput) getEditorInput()).getFile();
	IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
	if (eif.getParent() instanceof IFolder) {
	    eif = ((IFolder) eif.getParent()).getFile(xmlFile);
	} else if (eif.getParent() instanceof IProject) {
	    eif = ((IProject) eif.getParent()).getFile(xmlFile);
	}
	saveToFile(comp, eif, checkExists);
    }

    public void saveToFile(FormComponent comp, IFile xmlFile, boolean checkExists) {
	try {
	    if (checkExists && xmlFile.exists()) {
		if (!MessageDialog.openConfirm(getSite().getShell(), "Overwrite file?", "The file " + xmlFile + " exists.\nOverwrite it?"))
		    return;
	    }
	    // IEditorPart ed =
	    // new NewFormWizard().saveAndOpen(xmlFile, null, "pkg", "NewClass",
	    // "javax.swing.JPanel", "JPanel");
	    // FormEditor fed = (FormEditor) ed;
	    // FormComponent copy = comp.getCopy(true, fed);
	    // copy.setAllExistsInCode(true);
	    // fed.setNewRoot(copy);
	    // fed.populateFormComponents();
	    // copy.addToCode();
	    new XMLWriter().save(comp, xmlFile);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(getSite().getShell(), "Error saving form file", "Error saving form file", e);
	    jiglooPlugin.handleError(e);
	}
    }

    private FormComponent newRoot = null;

    public void setNewRoot(FormComponent newRoot) {
	this.newRoot = newRoot;
    }

    public void setRootAndUpdate(FormComponent newRoot) {
	removeComponent(root, true);
	newRoot.setName("this");
	newRoot.setLayoutDataWrapper(null);
	newRoot.unsetProperty("layoutData");
	newRoot.setPropertyValueCode("layout", null);
	// newRoot.setName(null);
	setRootComponent(newRoot);
	jcp.setRootComponent(newRoot);
	newRoot.setAllExistsInCode(true);

	root.markMainTree();

	try {
	    populateFormComponents();
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}

	setParsing(false);

	newRoot.addToCode();
	setDirtyAndUpdate(true, true, false);
	doSave(getProgressMonitor());
    }

    private void doBuild(IProgressMonitor monitor) {
	try {
	    // BuildAction ba = new BuildAction(getSite().getShell(),
	    // IncrementalProjectBuilder.INCREMENTAL_BUILD);
	    // ba.selectionChanged(new
	    // StructuredSelection(javaFile.getProject()));
	    // ba.run();

	    if (monitor != null)
		monitor.setTaskName("Building");

	    IProject proj = javaFile.getProject();
	    proj.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);

	} catch (Throwable t) {
	    t.printStackTrace();
	    // jiglooPlugin.handleError(t);
	}
    }

    public boolean updatesJavaCode() {
	return updateJavaCode;
    }

    public JavaCodeParser getJavaCodeParser() {
	return jcp;
    }

    public ICompilationUnit getWorkingCopy() {
	getJavaEditor();
	if (wcopy != null)
	    return wcopy;
	javaFileEditorInput = (FileEditorInput) getEditorInput();
	IWorkingCopyManager manager = JavaUI.getWorkingCopyManager();
	try {
	    manager.connect(javaFileEditorInput);
	    wcopy = manager.getWorkingCopy(javaFileEditorInput);
	    if (bufferListener == null) {
		bufferListener = new IBufferChangedListener() {
		    public void bufferChanged(BufferChangedEvent event) {
			handleJavaCodeChanged(true, null);
		    }
		};
	    }
	    wcopy.getBuffer().addBufferChangedListener(bufferListener);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
	return wcopy;
    }

    DelayableRunnable updateDrun = new DelayableRunnable(200, true) {
	public void run() {
	    updateWorkingCopyInternal();
	}
    };

    private void showJavadocForClass() {
	if (!canUseProfFeature("Javadoc"))
	    return;
	ICompilationUnit wc = getWorkingCopy();
	try {
	    IType type = getJavaProject(javaFile.getProject()).findType(getSelectedComponent().getTranslatedClassName());
	    JavadocDisplayer jd = new JavadocDisplayer();
	    jd.run(type, getSite().getShell());
	    // OpenExternalJavadocAction oejd =
	    // new OpenExternalJavadocAction(getSite());
	    // oejd.update(new StructuredSelection(type));
	    // oejd.run();
	} catch (JavaModelException e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void openInNewEditor(String className) {
	try {
	    IType type = getJavaProject(javaFile.getProject()).findType(className);
	    final IEditorInput ein = EditorUtility.getEditorInput(type);
	    if (ein == null) {
		MessageDialog.openInformation(getSite().getShell(), "Unable to open", "Unable to open class " + className);
		return;
	    }
	    if (!(ein instanceof FileEditorInput)) {
		EditorUtility.openInEditor(type, true);
		return;
	    }
	    Display.getDefault().asyncExec(new Runnable() {
		public void run() {
		    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		    try {
			IEditorPart editor = page.openEditor(ein, "com.cloudgarden.jigloo.editors.FormEditor");
		    } catch (PartInitException e) {
			e.printStackTrace();
		    }
		}
	    });
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private void showJavadocForLayout() {
	if (!canUseProfFeature("Javadoc"))
	    return;
	ICompilationUnit wc = getWorkingCopy();
	try {
	    IType type = getJavaProject(javaFile.getProject()).findType(getSelectedComponent().getLayoutWrapper().getMainClass().getName());
	    JavadocDisplayer jd = new JavadocDisplayer();
	    jd.run(type, getSite().getShell());
	} catch (JavaModelException e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private String genCode;

    private boolean needsReconcile = false;

    public boolean isCommitting() {
	return commit;
    }

    private void printDiff(String str1, String str2) {
	int pos = 0;
	String common = "";
	while (pos < str1.length() && pos < str2.length() && str1.substring(0, pos).equals(str2.substring(0, pos)))
	    pos++;
	System.out.println("POS=" + pos + ", DIFFERENCE STARTS WITH " + str1.substring(pos) + "\n=====\n" + str2.substring(pos));
    }

    private void updateWorkingCopyInternal() {
	this.commit = false;
	if (root == null)
	    return;
	genCode = null;
	resourceMap.clear();
	try {
	    wcopy = getWorkingCopy();
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private void commitWorkingCopy(IProgressMonitor monitor) {
	commit = true;
	genCode = null;
	resourceMap.clear();
	try {
	    wcopy = getWorkingCopy();
	    if (updateJavaCode) {
		if (javaEditor != null) {
		    if (isJavaEditor) {
			super.doSave(monitor);
			setDirty(false);
		    } else {
			javaEditor.doSave(monitor);
		    }
		} else {
		    wcopy.commit(false, null);
		}
		commit = false;
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	} finally {
	    releaseWorkingCopy();
	}
    }

    private static boolean insertSWTResource = false;

    public static void insertSWTResource() {
	insertSWTResource = true;
    }

    private boolean fileExists(String classPath) {
	IContainer srcRoot = JiglooUtils.getSourceContainer(this);
	String[] parts = JiglooUtils.split("/", classPath);
	String className = parts[parts.length - 1];
	IFolder folder = null;
	for (int i = 0; i < parts.length - 1; i++) {
	    if (i == 0)
		folder = srcRoot.getFolder(new Path(parts[0]));
	    else
		folder = folder.getFolder(parts[i]);
	    if (!folder.exists())
		return false;
	}
	IFile jfile = folder.getFile(className + ".java");
	if (jfile == null)
	    return false;
	return jfile.exists();
    }

    public void insertClass(String packageName, String classPath) throws Exception {
	insertClass(packageName, classPath, true);
    }

    public void insertClass(String packageName, String classPath, boolean force) throws Exception {
	try {

	    // make sure we don't re-insert the same class more than once per
	    // session!
	    String key = packageName + ";" + classPath;

	    // if the file doesn't exist, then force it to be created
	    if (!fileExists(classPath))
		insertedClasses.remove(key);

	    if (insertedClasses.contains(key))
		return;
	    insertedClasses.add(key);

	    try {
		FormClassLoader tmp = ClassLoaderCache.createClassLoader(getProject(), cpLoader);
		String className = classPath.replaceAll("/", ".");
		if (tmp.findClass(className) != null) {
		    System.out.println("already loaded class " + className);
		    return;
		}
	    } catch (Throwable e) {
	    }

	    IContainer srcRoot = JiglooUtils.getSourceContainer(this);
	    String[] parts = JiglooUtils.split("/", classPath);
	    String className = parts[parts.length - 1];
	    IFolder folder = null;
	    for (int i = 0; i < parts.length - 1; i++) {
		if (i == 0)
		    folder = srcRoot.getFolder(new Path(parts[0]));
		else
		    folder = folder.getFolder(parts[i]);
		if (!folder.exists())
		    folder.create(true, true, null);
	    }
	    IFile jfile = folder.getFile(className + ".java");
	    if (jfile.exists() && !force)
		return;
	    InputStream input;
	    input = getClass().getClassLoader().getResourceAsStream(classPath + ".txt");
	    if (input == null) {
		System.err.println("Can't find resource " + classPath + ".txt");
	    }
	    if (!jfile.exists()) {
		jfile.create(input, true, null);
	    } else {
		jfile.setContents(input, true, false, null);

	    }
	    jfile.setLocal(true, 0, null);
	    srcRoot.refreshLocal(4, null);
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    public void addExternalizedString(String key, String value) {
	String name = root.getName() + "Messages.properties";
	// IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
	IFile eif = ((FileEditorInput) getEditorInput()).getFile();
	IFile msgFile = null;
	if (eif.getParent() instanceof IFolder) {
	    msgFile = ((IFolder) eif.getParent()).getFile(name);
	} else if (eif.getParent() instanceof IProject) {
	    msgFile = ((IProject) eif.getParent()).getFile(name);
	}
	try {
	    key += "=";
	    if (msgFile.exists()) {
		InputStream in = msgFile.getContents();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = br.readLine()) != null) {
		    if (line.startsWith(key))
			return;
		}
	    }
	    key += value + "\n";
	    InputStream bin = new ByteArrayInputStream(key.getBytes());
	    if (msgFile.exists()) {
		msgFile.appendContents(bin, false, true, null);
	    } else {
		msgFile.create(bin, false, null);
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private String removeNetbeansComments(String code) {
	code = removeComments(code, "//GEN-BEGIN");
	code = removeComments(code, "//GEN-END");
	code = removeComments(code, "//GEN-FIRST");
	code = removeComments(code, "//GEN-LAST");
	return code;
    }

    private String removeComments(String code, String commentStart) {
	int pos = 0;
	while ((pos = code.indexOf(commentStart)) >= 0) {
	    int pos2 = code.indexOf("\n", pos);
	    if (pos2 > 0)
		code = code.substring(0, pos) + code.substring(pos2);
	}
	return code;
    }

    private void releaseWorkingCopy() {
	if (javaFileEditorInput == null)
	    return;
	try {
	    if (wcopy != null)
		wcopy.getBuffer().removeBufferChangedListener(bufferListener);
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	IWorkingCopyManager manager = JavaUI.getWorkingCopyManager();
	manager.disconnect(javaFileEditorInput);
	wcopy = null;
    }

    public void clearMainTree() {
	if (components == null)
	    return;
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    // System.out.println(fc+" IN MAIN TREE = "+fc.isInMainTree());
	    fc.setInMainTree(false);
	}
    }

    public void invalidate() {
	clearMainTree();
	if (root != null)
	    root.invalidateAll();
	if (nonVisualRoot != null)
	    nonVisualRoot.invalidateAll();
	if (menuBar != null)
	    menuBar.invalidateAll();

	if (components != null) {
	    for (int i = 0; i < components.size(); i++) {
		FormComponent fc = (FormComponent) components.elementAt(i);
		if (fc.getName().indexOf("::RA") >= 0) {
		    components.remove(fc);
		    extraCompRoot.getChildren().remove(fc);
		    i--;
		}
	    }
	    // components.clear();
	}
    }

    public void displayInfoFor(String compName, String msg) {
	System.out.println("displayInfoFor:" + compName + ", " + msg);
	FormComponent fc = getComponentByName(compName);
	if (fc == null) {
	    System.out.println("displayInfoFor: No component " + compName);
	    return;
	}
	if (fc.getControl() instanceof Control) {
	    Control ctrl = (Control) fc.getControl();
	    System.out.println("displayInfoFor: " + compName + ", size=" + ctrl.getSize() + ", bounds=" + ctrl.getBounds());
	}
    }

    public void reconcileCodeChanges() {
	if (root != null) {
	    setPopulating(true);
	    root.resetUnsetPropertiesForAll();
	    root.clearDirtyPropertiesForAll();
	    menuBar = jcp.getMenuBar();
	    if (menuBar != null) {
		menuBar.resetUnsetPropertiesForAll();
		menuBar.clearDirtyPropertiesForAll();
	    }
	    nonVisualRoot = jcp.getNonVisualRoot();
	    if (nonVisualRoot != null) {
		nonVisualRoot.resetUnsetPropertiesForAll();
		nonVisualRoot.clearDirtyPropertiesForAll();
	    }
	    if (isInSwingMode()) {
		root.setControl(awtControl);
		root.populateComponents(swingMainPanel, this);
	    } else if (isInCWTMode()) {
		root.setControl(awtControl);
		root.populateCwtWidgets(swingMainPanel, this);
	    } else {
		root.populateControls(frameComp, this, true);
	    }
	    if (menuBar != null) {
		setMenuBar(menuBar, false);
	    }

	    if (nonVisualRoot != null)
		nonVisualRoot.populateNonVisualComponents(this);

	    removeUnusedComponents();

	    performFinalPostParseTasks();

	    setPopulating(false);
	    if (awtControl != null)
		awtControl.layoutInFrame();
	    root.updateUIForAll();
	}
    }

    private void removeUnusedComponents() {
	if (!updatesJavaCode())
	    return;
	moveToCorrectRoot(getRootComponent());
	if (components == null)
	    return;
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    // System.err.println("*** removeUnusedComponents: FOUND " + fc+
	    // ", "+fc.existsInCode());
	    if (// fc.isRoot() ||
	    fc.isNonVisualRoot() || fc.isExtraCompRoot() || fc.isInherited() || fc.isVirtual()) {
		// do nothing
	    } else {
		if (!fc.isRoot() && (!fc.existsInCode() || !fc.isAssigned())) {
		    // System.err.println("*** REMOVING UNUSED " + fc);
		    removeComponent(fc, false);
		    // need to step back i by one, since components has
		    // decreased in size
		    i--;
		} else {
		    moveToCorrectRoot(fc);
		}
	    }
	}
    }

    /**
     * @param fc
     */
    private void moveToCorrectRoot(FormComponent fc) {
	FormComponent par = fc.getParent();
	FormComponent ecr = getExtraCompRoot();
	if (fc.equals(jcp.getMenuBar()) || (par != null && !ecr.equals(par) && !nonVisualRoot.equals(par)))
	    return;

	if (fc.hasParentInCode() || fc.equals(jcp.getMenuBar())
	// || fc.equals(jcp.getRootComponent())
	)
	    return;
	if (fc.getParent() == null) {
	    if (fc.isVisual()) {
		fc.setParent(getExtraCompRoot(), true);
		// pasteComponent(getExtraCompRoot(), fc, 0, false);
	    } else {
		fc.setParent(nonVisualRoot, true);
		// pasteComponent(nonVisualRoot, fc, 0, false);
	    }
	} else {
	    if (fc.isVisual()) {
		if (!fc.getParent().equals(getExtraCompRoot())) {
		    fc.setParent(getExtraCompRoot(), true);
		    // fc.moveToParent(getExtraCompRoot(), 0);
		}
	    } else {
		if (!fc.getParent().equals(nonVisualRoot)) {
		    fc.setParent(nonVisualRoot, true);
		    // fc.moveToParent(nonVisualRoot, 0);
		}
	    }
	}
    }

    public void performFinalPostParseTasks() {
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    if (FormComponent.DIALOG_BUTTON_BAR.equals(fc.getSpecialType())) {
		// because in previews, when buttonBar is copied and not created
		// from parsing code (createButton) then numColumns = 0 for
		// the bb GridLayout and is not changed (since createButton
		// increments
		// the column count) so make sure columnCount is non-zero (which
		// leads to a StackOverflowError
		int ncols = fc.getNonInheritedChildCount();
		if (ncols == 0)
		    ncols = 1;
		fc.getLayoutWrapper().setPropertyValue("numColumns", new Integer(ncols));
		// layout the parent composite of the buttonBar (which is
		// different
		// from fc.getParent().getControl())
		((Composite) fc.getControl()).getParent().layout();
	    }
	}
    }

    public void setStatus(final String str) {
	if (Display.getCurrent() != null) {
	    setStatusSync(str);
	} else {
	    Display.getDefault().asyncExec(new Runnable() {
		public void run() {
		    setStatusSync(str);
		}
	    });
	}
    }

    private void setStatusSync(final String str) {
	// System.err.println("Status: " + str);
	parsingLabel.setText(str);
	parsingLabel.redraw();
	parsingLabel.update();
	IStatusLineManager slm = null;
	if (formOutlinePage != null) {
	    IPageSite site = formOutlinePage.getSite();
	    slm = site.getActionBars().getStatusLineManager();
	    slm.setMessage(str);
	    slm.update(true);
	}
	slm = getEditorSite().getActionBars().getStatusLineManager();
	slm.setMessage(str);
	slm.update(true);
    }

    public void doSaveAs() {
	System.out.println("DO_SAVE_AS");
    }

    public HitResult getComponentAt(MouseEvent evt, boolean includeRoot, boolean bugFix) {

	HitResult hr = null;
	int x0 = evt.x;
	int y0 = evt.y;
	// adjustEventPoint is called, but it's effects are un-done when this
	// method returns
	adjustEventPoint(evt, bugFix);
	int x = evt.x;
	int y = evt.y;
	// replace event x and y so evt is unchanged after method call
	evt.x = x0;
	evt.y = y0;
	Point pt = new Point(x, y);
	java.awt.Point rootLoc = null;
	if (root.isSwing() && root.getComponent() != null) {
	    // TODO un-adjust location - is there a better way?
	    rootLoc = root.getComponent().getLocation();
	    pt.x += rootLoc.x;
	    pt.y += rootLoc.y;
	}

	FormComponent fc = null;
	FormComponent mb = getMenuBar();
	if (root.isJInternalFrame()) {
	    if (mb != null) {
		Rectangle rec = mb.getBoundsRelativeTo(null);
		if (rec.contains(x0, y0)) {
		    hr = getComponentAt(mb, x0 - rec.x, y0 - rec.y, true);
		    fc = hr.formComp;
		    if (fc == null) {
			hr.setResult(mb, x0, y0, moveTarget);
			fc = mb;
		    }
		}
	    }
	} else {
	    hr = getComponentAt(mb, pt.x, pt.y, true);
	    fc = hr.formComp;
	}

	if (fc == null) {
	    if (root.isSWT()) {
		Point pt2 = root.getLocation();
		x -= pt2.x;
		y -= pt2.y;
	    }
	    hr = getComponentAt(root, x, y, !includeRoot);
	    fc = hr.formComp;
	}

	if (fc != null && fc.getComponent() instanceof JTabbedPane) {
	    JTabbedPane tp = (JTabbedPane) fc.getComponent();
	    Rectangle b = null;
	    if (USE_INTERNAL_SHELL)
		b = fc.getBoundsRelativeTo(eventCatcher);
	    else
		b = fc.getBoundsRelativeTo(mainComp);
	    int ind = -1;
	    for (int i = 0; i < tp.getTabCount(); i++) {
		java.awt.Rectangle tpb = tp.getBoundsAt(i);
		if (tpb != null && tpb.contains(x0 - b.x, y0 - b.y)) {
		    ind = i;
		}
	    }
	    // ind = tp.indexAtLocation(x0 - b.x, y0 - b.y);
	    if (ind != -1) {
		FormComponent tab = fc.getNonInheritedChildAt(ind); // tab.bringToFront();
		fc = tab;
		if (hr == null)
		    hr = new HitResult();
		hr.setResult(fc, x0 - b.x, y0 - b.y, moveTarget);
		return hr;
	    }
	}
	if (hr == null)
	    hr = new HitResult();
	return hr;
    }

    public HitResult getComponentAt(FormComponent parent, int x, int y, boolean strict) {
	HitResult hr = new HitResult();
	if (parent == null)
	    return hr;
	String parClass = parent.getMainClass().getName();

	Vector children = parent.getChildren();
	Rectangle r;
	// inherited components are at the end, and we want to test them last
	for (int i = children.size() - 1; i >= 0; i--) {
	    FormComponent fc = (FormComponent) children.elementAt(i);
	    r = fc.getBounds();
	    // if c is a Shell inside a Canvas
	    if (fc.isSwtInSwing())
		r.x = r.y = 0;

	    Object ctrl = fc.getControl();
	    String ctrlClass = ctrl != null ? ctrl.getClass().getName() : "";

	    // System.err.println(
	    // "Got bounds " + r + " for " + c + ", " + x + ", " + y);
	    if (r.contains(x, y)) {
		if (fc.isSubclassOf(JScrollPane.class) && onBorder(r, x, y)) {
		    hr.setResult(fc, x, y, moveTarget);
		    return hr;
		}
		if (!fc.isContainer()) {
		    hr.setResult(fc, x, y, moveTarget);
		    return hr;
		}

		Rectangle cr = fc.getClientBounds();
		// if c is a Shell inside a Canvas
		if (fc.isSwtInSwing())
		    cr.x = cr.y = 0;

		if (ctrl != null && cr != null && !cr.contains(x, y)) {
		    if (ctrlClass.equals("org.eclipse.swt.custom.CTabFolder")) {
			// check all the CTabItems here
			Vector ch2 = fc.getChildren();
			for (int j = 0; j < ch2.size(); j++) {
			    FormComponent fc2 = (FormComponent) ch2.elementAt(j);
			    CTabItem cti = (CTabItem) fc2.getControl();
			    if (cti != null && cti.getBounds().contains(x - r.x, y - r.y)) {
				hr.setResult(fc2, x - r.x, y - r.y, moveTarget);
				return hr;
			    }
			}
		    } else if (ctrlClass.equals("org.eclipse.swt.widgets.TabFolder")) {
			// check all the TabItems here
			TabFolder tf = (TabFolder) fc.getControl();
			Vector ch2 = fc.getChildren();
			for (int j = 0; j < ch2.size(); j++) {
			    FormComponent fc2 = (FormComponent) ch2.elementAt(j);
			    if (SWTUtils.getBounds(tf, j).contains(x - r.x, y - r.y)) {
				hr.setResult(fc2, x - r.x, y - r.y, moveTarget);
				return hr;
			    }
			}
		    }
		    // System.err.println("Not contained by clientbounds " +
		    // cr);
		    hr.setResult(fc, x, y, moveTarget);
		    return hr;
		}

		FormComponent c2;
		HitResult hitRes;

		if (ctrlClass.equals("org.eclipse.swt.widgets.CoolItem") || ctrlClass.equals("org.eclipse.swt.widgets.ExpandItem")) {
		    hitRes = getComponentAt(fc, x, y, strict);
		} else if (fc.isCWT()) {
		    hitRes = getComponentAt(fc, x, y, strict);
		} else if (harness != null) {
		    hitRes = harness.getComponentAt(fc, x, y, strict);
		} else {
		    hitRes = getComponentAt(fc, x - r.x, y - r.y, strict);
		}

		if (hitRes.formComp != null && !hitRes.formComp.equals(fc))
		    return hitRes;
		hr.setResult(fc, x, y, moveTarget);
		return hr;

	    } else if (ctrlClass.equals("org.eclipse.swt.widgets.TreeItem") || ctrlClass.equals("org.eclipse.swt.custom.TableTreeItem")) {
		FormComponent c2;
		HitResult hitRes = getComponentAt(fc, x, y, strict);
		if (hitRes.formComp != null)
		    return hitRes;
	    }
	}
	if (parClass.equals("org.eclipse.swt.widgets.TreeItem") || parClass.equals("org.eclipse.swt.custom.TableTreeItem"))
	    return hr;
	if (strict && !parent.getBounds().contains(x, y))
	    return hr;
	hr.setResult(parent, x, y, moveTarget);
	return hr;
    }

    /**
     * @param r
     *            - rectangle containing (x,y)
     * @param x
     * @param y
     * @return
     */
    private boolean onBorder(Rectangle r, int x, int y) {
	int BORDER = 6;
	if (x - r.x < BORDER - 1)
	    return true;
	if (y - r.y < BORDER - 1)
	    return true;
	if (r.x + r.width - x < BORDER + 1)
	    return true;
	if (r.y + r.height - y < BORDER + 1)
	    return true;
	return false;
    }

    private Object rootObject = null;

    public Object getRootObject() {
	if (isAppFrameworkApplication())
	    return null;
	FormComponent jcpRoot = jcp.getRootComponent();
	if (rootObject != null)
	    return rootObject;
	try {
	    Class mc = getMainClass();
	    rootObject = ClassUtils.newInstance(mc, null, null);
	} catch (Throwable e) {
	    System.err.println("Unable to create new root object " + e);
	    e.printStackTrace();
	}
	return rootObject;
    }

    private FormComponent getBaseComponent() {
	for (int i = 0; i < components.size(); i++) {
	    FormComponent fc = (FormComponent) components.elementAt(i);
	    if (fc.isBaseComponent()) {
		return fc;
	    }
	}
	return null;
    }

    public FormComponent getRootComponent() {
	return root;
    }

    public void setRootComponent(FormComponent root) {
	if (this.root != null)
	    this.root.setRoot(false);
	this.root = root;
	root.setRoot(true);
    }

    public String getTitle() {
	if (getEditorInput() != null)
	    return getEditorInput().getName();
	return super.getTitle();
    }

    public void gotoMarker(IMarker marker) {
	try {
	    if (isJavaEditor) {
		super.gotoMarker(marker);
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
	if (inited)
	    return;
	inited = true;
	if (!(input instanceof IFileEditorInput))
	    throw new PartInitException("Invalid Input: Must be IFileEditorInput");

	IFile eif = ((FileEditorInput) input).getFile();
	String name = eif.getName();

	boolean formFileOpened = false;

	try {
	    IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();

	    int pos;

	    pos = name.lastIndexOf(".properties");
	    if (pos >= 0) {
		name = name.substring(0, pos) + ".java";
		if (eif.getParent() instanceof IFolder && eif.getParent().getName().equals("resources")) {
		    javaFile = eif.getParent().getParent().getFile(new Path(name));
		} else {
		    javaFile = eif.getParent().getFile(new Path(name));
		}
		input = new FileEditorInput(javaFile);
	    } else {
		pos = name.lastIndexOf(".form");
		if (pos < 0) {
		    pos = name.lastIndexOf(".java");
		    String ffname = name.substring(0, pos) + ".form";
		    if (eif.getParent() instanceof IFolder) {
			formFile = ((IFolder) eif.getParent()).getFile(ffname);
		    } else if (eif.getParent() instanceof IProject) {
			formFile = ((IProject) eif.getParent()).getFile(ffname);
		    }
		} else {
		    name = name.substring(0, pos) + ".java";
		    formFileOpened = true;
		    formFile = eif;
		}

		if (eif.getParent() instanceof IFolder) {
		    javaFile = ((IFolder) eif.getParent()).getFile(name);
		} else if (eif.getParent() instanceof IProject) {
		    javaFile = ((IProject) eif.getParent()).getFile(name);
		} else {
		    jiglooPlugin.writeToLog("[FormEditor.init NOT HANDLED] name: " + name + ", parent class: " + eif.getParent().getClass() + ", parent: " + eif.getParent());
		}
	    }

	} catch (Throwable ex) {
	    jiglooPlugin.handleError(ex);
	}

	if (!javaFile.exists()) {
	    initialJavaUpdate = true;
	    try {
		javaFile.create(new ByteArrayInputStream("".getBytes()), true, null);
		javaFile.refreshLocal(1, null);
		// updateWorkingCopyInternal(true);
	    } catch (Throwable e) {
		jiglooPlugin.handleError(e);
	    }
	}

	String edLayout = jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_EDITOR_LAYOUT);
	try {
	    EDITOR_MODE = Integer.parseInt(edLayout);
	    // EDITOR_MODE = 0;
	} catch (Throwable e) {
	    EDITOR_MODE = EDITOR_MODE_SPLIT_VERT;
	}

	if (EDITOR_MODE == 0)
	    EDITOR_MODE = EDITOR_MODE_SPLIT_VERT;

	// formEditorInput = input;

	if (formFileOpened)
	    input = new FileEditorInput(javaFile);

	if (isJavaEditor) {
	    super.init(site, input);
	} else {
	    setInput(input);
	    setSite(site);
	}
	// jiglooPlugin.writeToLog("opening "+javaFile);
	// System.out.println("FORM ED INIT " + input+", "+javaFile);

    }

    public String getSuperclassFromJavaFile(ICompilationUnit icu) {
	try {
	    IType main = icu.findPrimaryType();
	    if (main == null)
		return null;
	    String sup = main.getSuperclassName();
	    String[][] rtype = main.resolveType(sup);
	    if (rtype == null || rtype.length == 0)
		return sup;
	    sup = rtype[0][0];
	    if (rtype[0].length > 1)
		sup += "." + rtype[0][1];
	    return sup;
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	    return null;
	}
    }

    public boolean isDirty() {
	if (propertyFileEditor != null && propertyFileEditor.isDirty())
	    return true;
	return super.isDirty();
    }

    public boolean isSaveAsAllowed() {
	return false;
    }

    private IJavaElement getMainElement(IJavaElement type) {
	IJavaElement elem = type;
	while (type.getParent() != null)
	    type = type.getParent();
	return type;
    }

    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	try {
	    if (this.equals(part))
		return;
	    IEditorPart ae = getSite().getPage().getActiveEditor();
	    if (ae == null || (!ae.equals(this))) {
		return;
	    }
	    if (selection instanceof StructuredSelection) {
		Object ssel = ((StructuredSelection) selection).getFirstElement();
		if (ssel instanceof IType) {
		    IType type = (IType) ssel;
		    if (type.getCompilationUnit() == null || !type.getCompilationUnit().equals(jcp.getCompUnit()))
			return;
		    if (getMainClass() != null && !type.getFullyQualifiedName().equals(getMainClass().getName()) && ClassUtils.isVisual(getMainClass())) {
			jcp.setClassNameToParse(type.getFullyQualifiedName());
			reparseJavaCode(true);
			rebuildEditor();
		    }
		    return;
		} else if (ssel instanceof ICompilationUnit) {
		    ICompilationUnit cu = (ICompilationUnit) ssel;
		    if (!cu.equals(jcp.getCompUnit()) || cu.getTypes().length == 0)
			return;
		    IType type = cu.getTypes()[0];
		    if (getMainClass() != null && !type.getFullyQualifiedName().equals(getMainClass().getName())) {
			jcp.setClassNameToParse(type.getFullyQualifiedName());
			reparseJavaCode(true);
			rebuildEditor();
		    }
		    return;
		}
		Vector comps = JiglooUtils.selectionToVector(selection, true);
		if (!JiglooUtils.vectorsDiffer(comps, selectedComps))
		    return;
		if (comps.size() == 0)
		    return;
		if (comps.size() != 0)
		    clearSelection();

		for (int i = 0; i < comps.size(); i++) {
		    FormComponent comp = (FormComponent) comps.elementAt(i);
		    addSelectedComponent(comp, false, i != comps.size() - 1);
		}

		if (view != null)
		    view.selectionChanged(this, selection);
	    }
	} catch (Throwable t) {
	    jiglooPlugin.handleError(t);
	}
    }

    boolean javaEdActivated = false;

    public void setFocus() {
	if (testForCustomizer())
	    return;

	// System.out.println("SET FOCUS " + textEditorActive);
	try {
	    if (primaryComposite == null || textEditorActive) {
		super.setFocus();
	    } else {
		if (!mainComp.isFocusControl())
		    mainComp.setFocus();
	    }
	} catch (JiglooSecurityException t) {
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    public Vector getSelectedComponents() {
	return selectedComps;
    }

    public FormComponent getSelectedComponent() {
	if (selectedComps == null || selectedComps.size() == 0)
	    return null;
	return selectedComp; // return (FormComponent)
	// selectedComps.lastElement();
    }

    private boolean hasWindowFrame(FormComponent comp) {
	if (comp == null)
	    return false;
	return windowFrames.containsKey(comp);
    }

    /**
     * This is synchronized in case windowFrames.put(comp, winFrame) is called
     * twice and the first WindowFrame is orphaned
     */
    public synchronized WindowFrame getWindowFrame(FormComponent comp, boolean createIfNeeded) {
	WindowFrame winFrame = (WindowFrame) windowFrames.get(comp);
	// if (createIfNeeded && winFrame == null) {
	if (winFrame == null) {
	    if (windowFramePool.size() == 0) {
		winFrame = new WindowFrame(getViewportControl(), this);
		// System.out.println("NEW WIN FRM " + winFrame + " FOR " +
		// comp);
	    } else {
		for (int i = 0; i < windowFramePool.size(); i++) {
		    WindowFrame cand = (WindowFrame) windowFramePool.elementAt(i);
		    if (comp.equals(cand.getFormComponent())) {
			winFrame = cand;
			break;
		    }
		}
		if (winFrame == null)
		    winFrame = (WindowFrame) windowFramePool.elementAt(0);
		windowFramePool.remove(winFrame);
	    }
	    winFrame.setFormComponent(comp);
	    windowFrames.put(comp, winFrame);
	}
	return winFrame;
    }

    public void showWindowFrame(FormComponent comp) {
	if (comp == null || !comp.isVisual())
	    return;
	WindowFrame winFrame = getWindowFrame(comp, true);
	winFrame.setVisible(true);
	winFrame.setMoveTargetColor(false);
	winFrame.realign();
    }

    public void hideWindowFrames(Vector comps) {
	for (int i = 0; i < comps.size(); i++) {
	    hideWindowFrame((FormComponent) comps.elementAt(i));
	}
    }

    private void setWindowFramesMoveMode(boolean set) {
	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent fc = (FormComponent) selectedComps.elementAt(i);
	    WindowFrame winFrame = getWindowFrame(fc, false);
	    if (winFrame == null)
		continue;
	    winFrame.setMoveMode(set, false);
	}
    }

    public void hideWindowFrame(FormComponent comp) {
	if (getViewportControl() == null)
	    return;
	if (comp == null)
	    return;
	WindowFrame winFrame = getWindowFrame(comp, false);
	if (winFrame == null) {
	    return;
	}
	if (!windowFramePool.contains(winFrame)) {
	    windowFramePool.add(winFrame);
	    windowFrames.remove(comp);
	}
	winFrame.setVisible(false);
    }

    private FormComponent lastAddedComp;
    private AlignToolbar halignToolbar, valignToolbar;

    public void addSelectedComponent(FormComponent comp, boolean internal) {
	addSelectedComponent(comp, internal, false);
    }

    /**
     * adding is true when we are adding multiple components and this is not the
     * last call to this method
     */
    public void addSelectedComponent(FormComponent comp, boolean internal, boolean adding) {
	try {

	    if (comp == null || components == null) // || comp.isDisposed())
		return;
	    if (!this.equals(comp.getEditor()))
		return;

	    if (formEditorKeyAdapter.isParentSelected(comp)) {
		setStatus(comp.getName() + "\'s parent has been selected instead of " + comp.getNameInCode() + ". To un-do this, hit 'P' ");
		comp = comp.getParent();
	    }

	    if (comp != null && !comp.isMenuComponent()) {
		MenuWindow.hideAll();
	    }

	    if (comp.getParent() != null && selectedComps.contains(comp.getParent()))
		unselectComponent(comp.getParent(), true);
	    for (int i = 0; i < comp.getChildCount(); i++) {
		if (selectedComps.contains(comp.getChildAt(i)))
		    return;
	    }

	    if (!internal && !adding)
		scrollFormTo(comp);

	    comp.bringToFront(true);
	    if (comp.getEditorMenu() != null)
		comp.getEditorMenu().show();

	    if (!adding) {
		if (comp.isSwing())
		    showPalette(MODE_AWT_SWING);
		if (comp.isSWT())
		    showPalette(MODE_SWT);
	    }

	    // selectedComp is the last-selected component (eg, the one the
	    // context menu appears over).
	    selectedComp = comp;

	    if (useJavaCodeParser && !internal && !adding) {
		selectInCode(comp, null, false);
	    }

	    if (selectedComps.contains(comp)) {
		lastAddedComp = null;
		return;
	    }

	    lastAddedComp = comp;

	    // for(int i=0;i<selectedComps.size();i++)
	    // getWindowFrame(getSelectedComponent(i), false).setMoveMode(true,
	    // false);

	    selectedComps.add(comp);

	    if (awtControl != null) {
		awtControl.handleSelectionChanged(comp);
	    }
	    getGridEdgeManager().showGridEdgeMarkers(comp);

	    showWindowFrame(comp);

	    if (!adding) {
		setAlignButtonStates();
		structSel = new StructuredSelection(selectedComps);
		notifyListeners(true, internal);
		// TODO this is for GroupLayouts - should do it more efficiently
		if (awtControl != null && !awtControl.isDisposed())
		    awtControl.redraw();

		if (swtGlassPane != null)
		    swtGlassPane.redraw();
	    }

	} catch (Throwable e) {
	    jiglooPlugin.handleError(e, "Error in: FormEditor.addSelectedComponent, " + comp);
	}
    }

    /**
    * 
    */
    private void setAlignButtonStates() {
	if (selectedComps.size() <= 1) {
	    enableAlignButtons(false);
	} else {
	    enableAlignButtons(true);
	}
    }

    private void enableAlignButtons(boolean enable) {
	try {
	    for (int i = 0; i < NUM_ALIGN_BUTTONS - 1; i++)
		alignButtons[i].setEnabled(enable);

	    if (enable) {

		alignButtons[6].setToolTipText("Make selected elements same height");
		alignButtons[6].setImage(ImageManager.getImage("sameHeights.gif"));

		alignButtons[7].setToolTipText("Make selected elements same width");
		alignButtons[7].setImage(ImageManager.getImage("sameWidths.gif"));

	    } else {

		alignButtons[6].setToolTipText("Unlink selected element's height from all other elements");
		alignButtons[6].setEnabled(selectedComps.size() == 1);
		alignButtons[6].setImage(ImageManager.getImage("unlinkHeight.gif"));

		alignButtons[7].setToolTipText("Unlink selected element's width from all other elements");
		alignButtons[7].setEnabled(selectedComps.size() == 1);
		alignButtons[7].setImage(ImageManager.getImage("unlinkWidth.gif"));
	    }
	} catch (Throwable t) {
	    System.err.println("Error in enableAlignButtons " + t);
	}
    }

    public void unselectComponent(FormComponent comp, boolean internal) {
	try {
	    if (comp == null)
		return;
	    if (!selectedComps.contains(comp))
		return;
	    selectedComps.remove(comp);
	    if (!isDisposed())
		setAlignButtonStates();
	    structSel = new StructuredSelection(selectedComps);
	    notifyListeners(true, internal);
	    // comp.bringToFront();
	    hideWindowFrame(comp);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private void focusOnOutline() {
	// if (true)
	// return;

	if (isJavaEditor && textEditorActive)
	    return;
	try {
	    if (!txtWindowShowing && formOutlinePage != null && formOutlinePage.getControl() != null && !formOutlinePage.getControl().isDisposed()) {
		// System.err.println("FOCUS ON OUTLINE");
		// primaryComposite.setFocus();
		formOutlinePage.setSelected(selectedComps);
		formOutlinePage.setFocus();
	    }
	} catch (Throwable e) {
	    System.err.println("Error in focusOnOutline - " + e);
	}
    }

    private DelayableRunnable notifier = new DelayableRunnable(50, true) {
	public void run() {
	    notifyNow(false);
	}
    };

    public void notifyListeners(boolean showFormView, boolean refreshSelection) {
	notifier.trigger();
	if (formOutlinePage != null)
	    formOutlinePage.setSelected(selectedComps);
	if (internalFormOutline != null)
	    internalFormOutline.setSelected(selectedComps);
	if (structSel != null) {
	    SelectionChangedEvent sce = new SelectionChangedEvent(this, structSel);
	    for (int i = 0; i < listeners.size(); i++) {
		((ISelectionChangedListener) listeners.elementAt(i)).selectionChanged(sce);
	    }
	}
	// notifyNow();
	// System.out.println("NOTIFY LISTENERS");
	// new Exception().printStackTrace();
    }

    private void notifyNow(boolean showFormView) {
	if (showFormView)
	    showFormViewNow();
	/*
	 * if (fOutlinePage != null) fOutlinePage.setSelected(selectedComps); if
	 * (internalOutline != null) internalOutline.setSelected(selectedComps);
	 */
	if (selectedComps == null || selectedComps.size() == 0)
	    return;
	if (structSel == null)
	    structSel = new StructuredSelection(selectedComps);
	if (view != null)
	    view.selectionChanged(FormEditor.this, structSel);
	if (internalFormView != null)
	    internalFormView.selectionChanged(FormEditor.this, structSel);
    }

    public void setSelectedComponent(FormComponent comp, boolean internal) {
	if (selectedComps.size() == 1 && selectedComp != null && selectedComp.equals(comp))
	    return;
	// selectedComp = comp;
	if (!internal && selectedComps.size() == 1 && selectedComps.contains(comp)) {
	    if (formOutlinePage != null)
		formOutlinePage.setSelected(selectedComps);
	    if (internalFormOutline != null)
		internalFormOutline.setSelected(selectedComps);
	    return;
	}
	clearSelection();
	addSelectedComponent(comp, internal);
    }

    public void setSelectedComponents(Vector selected) {
	setSelectedComponents(selected, false);
    }

    public void setSelectedComponents(Vector selected, boolean internal) {
	for (int i = 0; i < selectedComps.size(); i++) {
	    FormComponent sel = (FormComponent) selected.elementAt(i);
	    if (!selected.contains(sel)) {
		hideWindowFrame(sel);
		selectedComps.remove(sel);
		i--;
	    }
	}
	for (int i = 0; i < selected.size(); i++) {
	    FormComponent sel = (FormComponent) selected.elementAt(i);
	    if (selectedComps.contains(sel)) {
		selected.remove(sel);
		i--;
	    }
	}
	for (int i = 0; i < selected.size(); i++) {
	    FormComponent sel = (FormComponent) selected.elementAt(i);
	    addSelectedComponent(sel, internal, i != selected.size() - 1);
	}
	setAlignButtonStates();
	notifyListeners(true, true);
    }

    public void selectComponent(final FormComponent comp) {
	clearSelection();
	addSelectedComponent(comp, true);
    }

    Action dcAction = null;

    private DelayableRunnable activatedDrun = new DelayableRunnable(100, true) {
	public void run() {
	    activated1();
	}
    };

    public void activated() {
	if (testForCustomizer())
	    return;

	setSecurityManager();

	// The 20 ms time delay (or possibly just the asyncExec call)
	// allows the classes to be loaded if Eclipse is opened
	// with a Jigloo window on top.
	activatedDrun.trigger();
    }

    /**
     * Called when a different editor part is brought to top
     */
    private void deactivated() {
	if (!activated)
	    return;
	activated = false;
	hideFormViewNow();
	resetSecurityManager();
	// enableParsing(false);

	// if(origCL != null)
	// Thread.currentThread().setContextClassLoader(origCL);

	Iterator it = windowFrames.keySet().iterator();
	while (it.hasNext()) {
	    ((WindowFrame) windowFrames.get(it.next())).deactivate();
	}
    }

    public void createClassLoader() {
	if (cpLoader != null)
	    return;
	cpLoader = ClassLoaderCache.getClassLoader(getProject(), getClass().getClassLoader());
    }

    private void activated1() {
	try {
	    waitLabel.setText("\n  Loading Jigloo GUI Editor.\n\n  Please wait...");
	    // System.out.println("FormEditor.activated "+this+", "+javaFile);
	    if (javaFile == null)
		return;

	    if (testForCustomizer())
		return;

	    activated = true;
	    jiglooPlugin.setActiveEditor(this);

	    createAndParse();

	    if (!isInTabbedMode() || "Form".equals(activeEditorType)) {
		checkParsing();
		// reloadChangedClasses();
	    }

	    showOutline();

	    // Below is a workaround for not being able to do this in the
	    // plugin.xml
	    // file and have it work for eclipse 3.0 and 3.1 (etc)
	    if (dcAction == null) {
		dcAction = new Action() {
		    public void run() {
			IEditorActionDelegate del = null;
			try {
			    Class cls = Class.forName("org.eclipse.debug.ui.actions.RulerToggleBreakpointActionDelegate");
			    del = (IEditorActionDelegate) cls.newInstance();
			} catch (Throwable t) {
			    // System.err.println("Error making
			    // RulerToggleBreakpointActionDelegate "+t);
			    try {
				Class cls = Class.forName("org.eclipse.jdt.internal.debug.ui.actions.ManageBreakpointRulerActionDelegate");
				del = (IEditorActionDelegate) cls.newInstance();
			    } catch (Throwable t2) {
				System.err.println("Error making ManageBreakpointRulerActionDelegate " + t2);
			    }
			}
			if (del != null) {
			    del.setActiveEditor(dcAction, FormEditor.this);
			    del.run(dcAction);
			}
		    }
		};

		setAction("RulerDoubleClick", dcAction);
	    }

	} catch (Throwable t) {
	    jiglooPlugin.writeToLog(t);
	}
    }

    private DelayableRunnable outlineDrun = null;

    public void showOutline() {
	if (disposed)
	    return;
	if (outlineDrun == null) {
	    outlineDrun = new DelayableRunnable(100, true) {
		public void run() {
		    showOutlineInternal();
		    // focusOnOutline();
		}
	    };
	}
	outlineDrun.trigger();
    }

    private JavaEditorPart javaEdPart;

    private void showOutlineInternal() {
	if (disposed)
	    return;

	try {
	    if (textEditorActive || propertyEditorActive) {
		hideFormViewNow();
	    } else {
		showFormViewNow();
	    }
	    IViewReference[] refs = getSite().getPage().getViewReferences();
	    IViewPart pe = null;
	    for (int i = 0; i < refs.length; i++) {
		if (refs[i].getId() != null && refs[i].getId().equals("org.eclipse.ui.views.ContentOutline")) {
		    pe = refs[i].getView(true);
		}
	    }
	    if (pe != null) {
		if (isJavaEditor) {
		    if (!reverseOutline && !formEditorActive) {
			if (javaEdPart == null) {

			    IContentOutlinePage cop = (IContentOutlinePage) super.getAdapter(IContentOutlinePage.class);
			    javaEdPart = new JavaEditorPart(cop);
			}
			if (pe instanceof PageBookView)
			    ((PageBookView) pe).partActivated(javaEdPart);
		    } else {
			if (pe instanceof PageBookView)
			    ((PageBookView) pe).partActivated(this);
		    }
		} else {
		    if (textEditorActive) {
			((PageBookView) pe).partActivated(javaEditor);
		    } else if (propertyEditorActive) {
			((PageBookView) pe).partActivated(propertyFileEditor);
		    } else {
			((PageBookView) pe).partActivated(this);
		    }
		}
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void closeOutlineViews() {
	try {
	    IViewReference[] refs = getSite().getPage().getViewReferences();
	    for (int i = 0; i < refs.length; i++) {
		if (refs[i].getId() != null && refs[i].getId().equals("org.eclipse.ui.views.ContentOutline")) {
		    IViewPart pe = refs[i].getView(true);
		    if (pe == null)
			return;
		    if (!(pe instanceof PageBookView))
			return;
		    if (javaEdPart != null)
			((PageBookView) pe).partClosed(javaEdPart);
		    ((PageBookView) pe).partClosed(this);
		}
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void hideOutline() { // System.err.println("HIDE
	// OUTLINE 1 " + disposed);
	if (disposed)
	    return;
	try {
	    IViewReference[] refs = getSite().getPage().getViewReferences();
	    // System.err.println("HIDE OUTLINE 2 "+refs.length);
	    for (int i = 0; i < refs.length; i++) {
		if (refs[i].getId() != null && refs[i].getId().equals("org.eclipse.ui.views.ContentOutline")) {
		    IViewPart pe = refs[i].getView(true);
		    if (pe == null)
			return;
		    ((ContentOutline) pe).partClosed(javaEditor);
		    ((PageBookView) pe).partClosed(this);
		}
	    }
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    private DelayableRunnable hider = new DelayableRunnable(1000, true) {
	public void run() {
	    hideFormViewNow();
	}
    };

    public FormView getFormView() {
	// if(view == null)
	view = (FormView) getSite().getPage().findView("com.cloudgarden.jigloo.views.FormView");
	return view;
    }

    private void hideFormViewNow() {
	try {
	    if (getFormView() != null && !view.isDisposed() && !view.isMinimized() && getSite().getPage().isPartVisible(view) && !getSite().getShell().isDisposed())
		getSite().getPage().hideView(view);
	    view = null;
	} catch (Throwable t) {
	    System.err.println("error hiding form view " + t);
	}
    }

    private void showFormViewNow() {
	// if(true)
	// return;
	// System.out.println("SHOW VIEW " + formEditorActive);
	// if (!formEditorActive)
	// return;
	if (editorMaximized || disposed)
	    return;
	try {
	    if (getFormView() == null)
		view = (FormView) getSite().getPage().showView("com.cloudgarden.jigloo.views.FormView");
	    if (view.isMinimized())
		return;
	    if (!getSite().getPage().isPartVisible(view)) {
		getSite().getPage().activate(view);
	    }

	    // in case the form view has stolen focus from the java editor
	    // - only problem is that the code below can result in flip-flop
	    // between two FormEditors
	    // if activate(this) is called - but if
	    // activate(jiglooPlugin.getActiveEditor()) is called
	    // then no flip-flop
	    if (created)
		getSite().getPage().activate(jiglooPlugin.getActiveEditor());

	    return;
	} catch (Throwable ex) { // jiglooPlugin.handleError(ex);
	}
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
	if (!listeners.contains(listener))
	    listeners.add(listener);
    }

    public void removeSelectionChangedListener(ISelectionChangedListener listener) {
	if (listeners.contains(listener))
	    listeners.remove(listener);
    }

    public ISelectionProvider getSelectionProvider() {
	// System.err.println("FormEditor: getSelectionProvider");
	// return selProvider;
	return super.getSelectionProvider();
    }

    public ISelection getSelection() {
	if (structSel == null)
	    structSel = new StructuredSelection(selectedComps);
	return structSel;
    }

    public void setSelection(ISelection selection) {
	System.out.println("FE setSelection " + selection);
    }

    public Object getAdapter(Class required) {
	if (IContentOutlinePage.class.equals(required)) {
	    if (formOutlinePage == null) {
		formOutlinePage = new FormContentOutlinePage(this, false);
	    }
	    return formOutlinePage;
	}
	return super.getAdapter(required);
    }

    public void selectionChanged(SelectionChangedEvent event) { // not used
	try {
	    System.out.println("FE selectionChanged (2) " + event.getSelection() + ", " + event.getSelectionProvider());
	    selectionChanged(null, event.getSelection());
	    // notifyListeners(false);
	} catch (Throwable e) {
	    jiglooPlugin.handleError(e);
	}
    }

    public void toggleGrid() {
	if (gridSize == 0)
	    gridSize = jiglooPlugin.getGridSize();
	else
	    gridSize = 0;

	// gridSize += 5;
	// if (gridSize == 25)
	// gridSize = 0;
	mainComp.redraw();
    }

    public void showGrid(boolean show) {
	if (show)
	    gridSize = jiglooPlugin.getGridSize();
	else
	    gridSize = 0;
	mainComp.redraw();
    }

    public Image getTitleImage() {
	if (isJavaEditor)
	    return ImageManager.getImage("form.gif");
	else
	    return super.getTitleImage();
    }

    /**
     * @return
     */
    public Vector getComponents() {
	return components;
    }

    /**
     * @return
     */
    public boolean isProcessing() {
	return (isPopulating() || isPreviewing() || isToggling() || isRebuilding() || isParsing());
    }

    /**
     * @param dm
     */
    public void addNonVisualComponent(FormComponent dm) {
	Vector nvc = getNonVisualRoot().getChildren();
	if (!nvc.contains(dm))
	    nvc.add(dm);
	if (components != null && !components.contains(dm))
	    components.add(dm);
    }

    private String activeEditorType = "Java";

    private void handleEditorTabSelected(String type) {
	activeEditorType = type;
	if ("Java".equals(type)) {
	    // enableParsing(false);
	    registerActions(type);
	    reverseOutline = false;
	    showOutline();
	} else if ("Property".equals(type)) {
	    // enableParsing(false);
	    registerActions(type);
	    reverseOutline = false;
	    showOutline();
	} else if ("Form".equals(type)) {
	    if (!created)
		enableParsing(true);
	    createAndParse();
	    checkParsing();
	    registerActions(type);
	    showOutline();
	    // if(needsReparse)
	    // reparseJavaCode(false);
	    handleCursorMovedInEditorNow();
	}
    }

    /**
     * 
     */
    public void toggleEditors() {
	if (formCodeTabFolder == null)
	    return;
	if (formCodeTabFolder.getSelection().equals(codeTabItem)) {
	    showFormTab();
	    handleEditorTabSelected("Form");
	} else {
	    showSourceTab();
	    handleEditorTabSelected("Java");
	}
    }

    /**
     * @param component
     */
    public void refreshTreeNode(FormComponent component) {
	if (internalFormOutline != null)
	    internalFormOutline.refreshTreeNode(component);
	if (formOutlinePage != null)
	    formOutlinePage.refreshTreeNode(component);
    }

    public SnapTo getSnapTo() {
	return snapTo;
    }

    public void setSnapTo(SnapTo snapTo) {
	this.snapTo = snapTo;
	if (snapTo == null) {
	    snapX = snapY = -1;
	}
    }

    public FormComponent getMoveTarget() {
	return moveTarget;
    }

    private FormComponent addedComponent;

    /**
     * @param addedComponent
     *            - the FC just added by a FormAddAction
     */
    public void setAddedComponent(FormComponent addedComponent) {
	this.addedComponent = addedComponent;
    }

    /**
     * @param component
     * @param name
     */
    public void fieldRenamed(String oldName, String newName, String oldNameProp) {
	if (oldName != null && newName != null && oldName.equals(newName))
	    return;
	if (getJavaCodeParser() == null)
	    System.out.println("java code parser is null in fieldRenamed");
	getJavaCodeParser().fieldRenamed(oldName, newName);
	bundleManager.renameField(oldNameProp, newName);
    }

    /**
     * Refreshes the property descriptors for the FormComponents, and saves the
     * property-category list to the preferences store
     */
    public void refreshCategories() {
	for (int i = 0; i < components.size(); i++) {
	    ((FormComponent) components.elementAt(i)).refreshCategories();
	}
	jiglooPlugin.savePropCategories();
    }

    /**
     * @param start
     * @param i
     * @param newCode
     */
    public void resetVisibleRegion() {
	try {
	    // getSourceViewer().getDocument().replace(start, i, newCode);
	    getSourceViewer().resetVisibleRegion();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.texteditor.AbstractTextEditor#setHighlightRange(int,
     * int, boolean)
     */
    public void setHighlightRange(int offset, int length, boolean moveCursor) {
	try {
	    if (// parsingEnabled &&
	    allowMoveCursor) {
		// getSourceViewer().setRangeIndication(offset, length,
		// moveCursor);
		super.setHighlightRange(offset, length, moveCursor);
	    }
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    private void viewParseLog() {
	flashLogButton = false;
	viewLogDialog = new ViewLogDialog(getSite().getShell(), SWT.DIALOG_TRIM | SWT.RESIZE);
	viewLogDialog.initText(getLogText(), "Log for " + javaFile.getName());
	viewLogDialog.open();
	viewLogDialog = null;
    }

    private boolean flashLogButton = false;
    private Thread flashLogButtonThread = null;

    private void flashViewLogButton() {
	String logText = getLogText();
	if (logText.indexOf("ERRORS...") >= 0) {
	    if (flashLogButton)
		return;
	    flashLogButton = true;
	} else {
	    flashLogButton = false;
	}

	flashLogButtonThread = new Thread() {
	    public void run() {
		int cnt = 0;
		while (!disposed && flashLogButton) {
		    try {
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
				if (viewLogButton.isDisposed()) {
				    flashLogButton = false;
				} else {
				    viewLogButton.setBackground(ColorManager.getColor(255, 0, 0));
				}
			    }
			});
			Thread.sleep(300);
		    } catch (Exception e) {
		    }

		    try {
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
				if (viewLogButton.isDisposed()) {
				    flashLogButton = false;
				} else {
				    viewLogButton.setBackground(ColorManager.getColor(255, 255, 255));
				}
			    }
			});
			Thread.sleep(300);
		    } catch (Exception e) {
		    }
		    if (cnt++ > 5)
			flashLogButton = false;
		}
	    }
	};
	flashLogButtonThread.start();
    }

    /**
     * @return
     */
    public boolean isMouseDragged() {
	return windowFrameResizing || (mouseMoved && mouseDown);
    }

    public boolean isMouseDown() {
	return mouseDown;
    }

    /**
     * @return
     */
    public FormComponent getAboutToBeAddedComponent() {
	if (getCurrentAction() instanceof FormAddAction) {
	    return ((FormAddAction) getCurrentAction()).getTempFormComponent();
	}
	return null;
    }

    /**
     * @return
     */
    public static Vector getClipboardComps() {
	return clipboardComps;
    }

    /**
     * @return
     */
    public MarqueeFrame getAddFrame() {
	return addFrame;
    }

    /**
     * @param b
     */
    public void pauseUpdate(boolean pause) {
	pauseUpdate = pause;
    }

    /**
    * 
    */
    public void flushActions() {
	grpActionDrun.cancel();
	grpActionDrun.run();
    }

    /**
     * @return
     */
    public BundleManager getBundleManager() {
	return bundleManager;
    }

    /**
     * @return
     */
    public IFolder getResourceFolder() {
	return ((FileEditorInput) getEditorInput()).getFile().getParent().getFolder(new Path("resources"));
    }

    public void showCustomizerFrame(Component cust, String title) {
	if (customizerFrame != null && customizerFrame.isShowing())
	    return;
	if (customizerFrame == null) {
	    customizerFrame = new JFrame();
	    jiglooPlugin.getDefault().addAllowedWindow(customizerFrame);
	}
	customizerFrame.setTitle(title);
	customizerFrame.getContentPane().removeAll();
	customizerFrame.getContentPane().add(cust);
	customizerFrame.pack();
	customizerFrame.setLocationRelativeTo(null);
	customizerFrame.setVisible(true);
	customizerFrame.toFront();
	customizerFrame.requestFocus();
    }

    private boolean testForCustomizer() {
	if (customizerFrame != null && customizerFrame.isVisible()) {
	    customizerFrame.toFront();
	    customizerFrame.requestFocus();
	    return true;
	}
	return false;
    }

    /**
     * @return
     */
    public boolean useJava6GroupLayout() {

	if (jiglooPlugin.dontUseJava6GroupLayout())
	    return false;

	if (useJava6GroupLayout == null) {
	    try {
		if (jcp != null && jcp.hasImport("org.jdesktop.layout.GroupLayout")) {
		    useJava6GroupLayout = Boolean.FALSE;
		} else {
		    // go straight to cpLoader, otherwise GroupLayout name is
		    // converted
		    FormClassLoader cl = (FormClassLoader) ClassLoaderCache.getClassLoader(getProject(), null);
		    if (cl.findResource("javax/swing/GroupLayout.class") != null)
			useJava6GroupLayout = Boolean.TRUE;
		    else
			useJava6GroupLayout = Boolean.FALSE;
		}
	    } catch (Throwable t) {
		t.printStackTrace();
		useJava6GroupLayout = Boolean.FALSE;
	    }
	}
	return useJava6GroupLayout.booleanValue();
    }

    /**
     * Internally we use non-Java6 format, which has properties autocreate... so
     * this method converts to autoCreate... when needed (or vice-versa)
     * 
     * @param propName
     * @param toCode
     * @return
     */
    public String convertGroupLayoutProp(String propName, boolean toCode) {
	boolean use6 = useJava6GroupLayout();
	if (use6) {
	    if (toCode) {
		return JiglooUtils.replace(propName, "autocreate", "autoCreate");
	    } else {
		return JiglooUtils.replace(propName, "autoCreate", "autocreate");
	    }
	} else
	    return propName;
    }

    /**
     * returns false if there is a multi-selection and we are updating (setting
     * the layout data) a FC other than the last in the selectedComponents
     * Vector. Otherwise returns true.
     */
    public boolean isUpdatingLastFC() {
	return updatingLastFC;
    }

    /**
     * see #isUpdatingLastFC
     */
    public void setUpdatingLastFC(boolean updatingLastFC) {
	this.updatingLastFC = updatingLastFC;
    }

    /**
    * 
    */
    public void setWaitCursor() {
	setCursor(CursorManager.getCursor(SWT.CURSOR_WAIT));
    }

    /**
     * @return
     */
    public HitResult getHitResult() {
	return hitResult;
    }

    /**
     * @return windowFrameResizing
     */
    public boolean isResizing() {
	return windowFrameResizing;
    }

    public GridEdgeManager getGridEdgeManager() {
	if (gridEdgeManager == null)
	    gridEdgeManager = new GridEdgeManager();
	return gridEdgeManager;
    }

    public void refreshGrid() {
	if (awtControl != null)
	    awtControl.setComponentToBeLayedOut(null);
	if (swtGlassPane != null)
	    swtGlassPane.redraw();
    }

    private Boolean canUseEnfinLayout = null;

    /**
     * @return
     */
    public boolean canUseEnfinLayout() {
	if (canUseEnfinLayout == null) {
	    canUseEnfinLayout = Boolean.FALSE;
	    try {
		if (loadClass("de.gebit.s2j.smalltalk.gui.EnfinLayout") != null)
		    canUseEnfinLayout = Boolean.TRUE;
	    } catch (Throwable t) {
	    }
	}
	return canUseEnfinLayout.equals(Boolean.TRUE);
    }

    private Boolean canUseOpenSwing = null;

    /**
     * @return
     */
    public boolean canUseOpenSwing() {
	if (canUseOpenSwing == null) {
	    try {
		if (loadClass("org.openswing.swing.client.GridControl") != null) {
		    canUseOpenSwing = Boolean.TRUE;
		} else {
		    canUseOpenSwing = Boolean.FALSE;
		}
	    } catch (Throwable t) {
		canUseOpenSwing = Boolean.FALSE;
	    }
	}
	return canUseOpenSwing.equals(Boolean.TRUE);
    }

    public boolean useInternalShell() {
	return USE_INTERNAL_SHELL;
    }

    public void setPostOpenRunnable(Runnable r) {
	postOpenRunnable = r;
    }

    public void clearClassLoader() {
	// ClassLoaderCache.deleteClassLoader(getProject());
	clearClassCache();
	cpLoader = null;
    }

    public int getMode() {
	return mode;
    }

    public void setHarness(IHarness harness) {
	this.harness = harness;
    }

    public FormComponent getRoot() {
	return root;
    }

}