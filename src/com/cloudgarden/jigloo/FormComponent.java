/*
 * Created on Jun 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import info.clearthought.layout.TableLayoutConstraints;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.TextComponent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.Customizer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.jdom.DOMFactory;
import org.eclipse.jdt.core.jdom.IDOMFactory;
import org.eclipse.jdt.core.jdom.IDOMField;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloudgarden.jigloo.actions.ActionStub;
import com.cloudgarden.jigloo.actions.UndoableLayoutAction;
import com.cloudgarden.jigloo.actions.UndoableLayoutDataAction;
import com.cloudgarden.jigloo.actions.UndoablePropertyAction;
import com.cloudgarden.jigloo.appFramework.ActionManager;
import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.controls.AWTControl;
import com.cloudgarden.jigloo.controls.LinuxDecorations;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.controls.OrderableGroup;
import com.cloudgarden.jigloo.dialog.SwingDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.ArrayHolder;
import com.cloudgarden.jigloo.eval.ConstructorHolder;
import com.cloudgarden.jigloo.eval.ConstructorManager;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.exception.JiglooException;
import com.cloudgarden.jigloo.forms.EclipseFormCall;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.interfaces.IDummyShellSupplier;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.ISWTDisposableWrapper;
import com.cloudgarden.jigloo.interfaces.IWidgetManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.interfaces.NonDefaultConstructor;
import com.cloudgarden.jigloo.interfaces.SyntheticPropertyContainer;
import com.cloudgarden.jigloo.jface.ApplicationWindowManager;
import com.cloudgarden.jigloo.jface.StubDialog;
import com.cloudgarden.jigloo.jface.StubWizDialog;
import com.cloudgarden.jigloo.layoutHandler.EnfinLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.TableLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.XYLayoutHandler;
import com.cloudgarden.jigloo.menu.MenuWindow;
import com.cloudgarden.jigloo.openswing.OpenSwingHelper;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.outline.TreeParent;
import com.cloudgarden.jigloo.properties.AbsoluteLayout;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.properties.descriptors.ChoicePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.ClassPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.CustomEditorDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.TextFormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.AdvancedPropertySource;
import com.cloudgarden.jigloo.properties.sources.BasicPropertySource;
import com.cloudgarden.jigloo.properties.sources.BorderPropertySource;
import com.cloudgarden.jigloo.properties.sources.ChangeablePropertySource;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;
import com.cloudgarden.jigloo.properties.sources.LayoutPropertySource;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.properties.sources.RectanglePropertySource;
import com.cloudgarden.jigloo.properties.sources.SizePropertySource;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.ConversionUtils;
import com.cloudgarden.jigloo.util.DefaultValueManager;
import com.cloudgarden.jigloo.util.FieldManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.util.SWTStyleManager;
import com.cloudgarden.jigloo.util.SWTUtils;
import com.cloudgarden.jigloo.util.SwingHelper;
import com.cloudgarden.jigloo.util.SwingSWTMapper;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.EventWrapper;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.FocusTraversalPolicyWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentArrayWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.JLayerWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;
import com.cloudgarden.jigloo.wrappers.MnemonicWrapper;
import com.cloudgarden.jigloo.wrappers.SWTCursorWrapper;
import com.cloudgarden.jigloo.wrappers.WrapperFactory;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.jgoodies.forms.builder.AbstractFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import com.jgoodies.forms.util.UnitConverter;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormComponent //extends FormPropertySource
        implements IFormPropertySource {

    protected Throwable lastException = null;
    
    protected boolean settingLayoutConstraint = false;
    
    public static final int MOD_FINAL = 1;
    public static final int MOD_STATIC = 2;
    public static final int MOD_PRIVATE = 4;
    public static final int MOD_PUBLIC = 8;
    public static final int MOD_PROTECTED = 16;
    protected int modifier = MOD_PRIVATE;
    protected Boolean usesContentPane = null;
    
    public static final int GRIDBAG_UNDEFINED = -100;

	public static final String INVALID_LAYOUT_CONSTRAINT = "INVALID";

    protected boolean MAKE_JFRAMES = true;

    protected static IDOMFactory domFac = new DOMFactory();

    protected boolean isMethodReturnValue = false;
    protected boolean isFactoryElement = false;
    protected boolean existsInCode = false;
    protected boolean assigned = false;
    protected boolean wasCut = false;
    protected boolean isRoot = false;
    protected boolean isExtraCompRoot = false;
    protected boolean isNonVisualRoot = false;
 
    protected int childCountInCode = 0;

    protected boolean hasParentInCode = false;

    protected Component component;

    protected Object nonVisualObject;

    protected Vector components;

    protected Object control;

    protected int style = SWT.NULL;
    protected String styleString = null;
    
    protected boolean isContentPane = false;
    protected boolean useExistingCP = false;

    protected boolean isVirtual = false;
    protected boolean isParameter = false;

    protected boolean disposed = false;

    protected boolean isBaseComponent = false;

    protected boolean propertiesInited = false;

    protected FormEditor editor;

    protected String name = null;

    protected String className = null;

    protected Class mainClass;
    protected Class convertedMainClass;

    protected String tabTitle = null;

    protected Node node;
    
    protected Vector methodCalls = new Vector();
    protected Vector builderMethodCalls = new Vector();
    
    /**
     * True if this FormComponent is in the main tree (is a child of the root node
     * or the menu bar) or is a property of a FC in the main tree.
     */
    protected boolean isInMainTree = false;

    protected FormComponent parent;
    
    protected FormComponent menuBar;
    protected FormComponent menuBarParent;

    protected FormComponent copiedTo = null;
    protected FormComponent copiedFrom = null;

    protected HashMap properties;

    protected HashMap propTypes, defaultProps;

    protected HashMap extraPropCode;

    //Use propertyStorageNames to preserve order in which properties are set
    protected HashMap propertyStorage = null;

    protected Vector propertyStorageNames = null;

    protected HashMap propertyValueCode = null;

    protected Vector synthProps;

    protected Vector evtNames;

    protected Vector propNames;

    protected Vector fieldNames;

    protected Vector propsNeedingCodeUpdate = new Vector();

    protected Vector setProps = new Vector();

    protected IPropertyDescriptor[] propertyDescriptors;

    protected LayoutWrapper layoutWrapper, oldLW;

    protected LayoutDataWrapper layoutDataWrapper, oldLDW;

    protected EventWrapper eventWrapper;

    protected TreeObject treeObject;

    protected Constructor constructor;
    protected ConstructorHolder cHolder = null;
    
    protected Object[] constructorParams;

    public static final int TYPE_SWING = 1;
    public static final int TYPE_SWT = 2;
    public static final int TYPE_CWT = 3;
    public static final int TYPE_GWT = 4;

    public static final int TYPE_NONE = -1;

    protected int classType = 0;

    protected static final String CTABITEM = CTabItem.class.getName();

    protected static final String TABITEM = TabItem.class.getName();

    protected HashMap beanPropDescs = null;

    protected HashMap beanPropEds = new HashMap();

    protected boolean isImportedBean = false;
    
    protected AbstractFormBuilder builder = null;
    protected Object formToolkit = null;

    protected boolean createGetterMethod = false;

    public static final String DIALOG_BUTTON_BAR = "DIALOG_BUTTON_BAR";

    public static final String DIALOG_BUTTON = "DIALOG_BUTTON";

    protected String specialType = null;

    protected boolean inline = false;
    
    abstract class ControlInit {
        public abstract void initControl(Object control);
    }


    public FormComponent(Node node, FormComponent parent, FormEditor editor) {
        this(node, parent, null, editor);
    }

    public static FormComponent newFormComponent(FormEditor editor, String clsName) {
    	return newFormComponent(editor, clsName, true);
    }
        
    private static FormComponent newFormComponent(FormEditor newEditor,
			String className, FormComponent copiedFrom) {
		return newFormComponent(newEditor, className, true, copiedFrom);
	}

    public static FormComponent newFormComponent(FormEditor editor, String clsName, 
    		boolean getInherited) {
    	return newFormComponent(editor, clsName, getInherited, null);
    }
    
    public static FormComponent newFormComponent(FormEditor editor, String clsName, 
    		boolean getInherited, FormComponent copiedFrom) {
    	FormComponent fc = null;

    	Class cls = null;
    	if(clsName != null) {
        	if(copiedFrom != null) {
        		editor = copiedFrom.getEditor();
        	}
        	cls = editor.loadClass(clsName);
    	}
    	
    	if(cls == null) {
    		fc = new FormComponent();
    	} else if(editor.getHarness() != null) {
    		fc = editor.getHarness().newFormComponent();
    	} else {
    		fc = new FormComponent();
    	}
    	fc.setCopiedFrom(copiedFrom);
    	fc.setEditor(editor);
    	if(clsName != null)
    		fc.setClassName(clsName, getInherited);

    	return fc;
    }
    
    public FormComponent() {
//    	System.out.println("NEW FC");
        //super.setFormComponent(this);
    }

    public static FormComponent newFormComponent(
    		FormComponent parent, 
    		String className, 
    		String fcName,
            boolean inherited, 
            Object obj) {
    	
    	FormComponent fc = newFormComponent(parent.getEditor(), className, false);
    	
        fc.isInherited = inherited;
//        fc.parent = parent;
        if (fcName != null)
            fc.name = fcName;

        fc.setClassName(className, true);
        fc.setParent(parent, true);
        
        if (!fc.isInherited) {
            if (!fc.isVisual()) {
                if(obj == null)
                    fc.populateNonVisualComponents(fc.editor);
                else
                    fc.nonVisualObject = obj;
            } else {
                if (fc.isSwing()) {
                    if(obj == null)
                        fc.populateComponents((Container) parent.getComponent(),
                            fc.editor);
                    else
                        fc.component = (Component)obj;
                } else if (fc.isSWT()) {
                    if(obj == null) {
                        if(parent.getRawControl() != null)
                        	fc.populateControls(parent.getRawControl(), fc.editor, true);
                    } else if(obj instanceof Widget) {
                        fc.control = obj;
                    } else {
                        System.err.println("TRYING TO SET CONTROL TO NON-WIDGET "+obj+" for "+fc);
                        new Exception().printStackTrace();
                    }
                }
            }
            parent.add(fc);
        }
        return fc;
    }

    public FormComponent(Node node, FormComponent parent, String type, FormEditor editor) {
        setEditor(editor);
        if (type != null) {
            if (type.indexOf("JFrame") >= 0) {
                setClassName("javax.swing.JFrame");
            } else if (type.indexOf("JApplet") >= 0) {
                setClassName("javax.swing.JApplet");
            } else if (type.indexOf("JDialog") >= 0) {
                setClassName("javax.swing.JDialog");
            } else if (type.indexOf("JInternalFrame") >= 0) {
                setClassName("javax.swing.JInternalFrame");
            } else {
                setClassName("javax.swing.JPanel");
            }
        }
        this.parent = parent;
        this.node = node;
        if (parent != null)
            setClassType(parent.getClassType());
        try {
            initializeFromNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addComponent(Component comp) {
        SwingHelper.addComponent(comp, component);
    }

    public void addComponent(Component comp, Component parent,
    		Object constraint, FormComponent fc) {
    	if (!(parent instanceof Container))
    		return;
    	String layoutType = fc.getParentSuperLayoutType();
    	if(constraint instanceof JLayerWrapper) {
    		constraint = ((JLayerWrapper)constraint).getValue();
    	}
    	if (parent instanceof JInternalFrame) {
    		Object c2 = validateLayoutConstraint(constraint, layoutType, fc);
    		((JInternalFrame) parent).getContentPane().add(comp, c2);
    	} else {
    		Object c2 = validateLayoutConstraint(constraint, layoutType, fc);
    		((Container) parent).add(comp, c2);
    	}
    }

    public boolean isSyntheticProperty(String propName) {
        if (synthProps == null)
            return false;
        return synthProps.contains(propName);
    }

    public static void removeComponent(Component comp, Component parent) {
    	if (!(parent instanceof Container) || comp == null)
    		return;
    	try {
    		((Container) parent).remove(comp);
    	} catch(Throwable t) {
    		jiglooPlugin.handleError(t);
    	}
    }
    
    public void add(FormComponent comp) {
        add(comp, -1);
    }

    public void add(FormComponent comp, int pos) {
        add(comp, pos, false);
    }

    public void addToChildrenVector(int pos, FormComponent fc) {
        if (components == null)
            components = new Vector();
       if(components.contains(fc))
            return;
        if(pos < 0)
            pos = 0;
        if( pos > components.size())
            pos = components.size();
        components.add(pos, fc);            
    }
    
    public void add(FormComponent comp, int pos, boolean alreadyInited) {
        if (editor != null && !alreadyInited)
            editor.addChildComponent(comp, this);

        if (isChildOf(comp) || equals(comp)) {
            jiglooPlugin.writeToLog("FormComponent.add: Trying to add " + comp + " to " + this
                    + " when " + comp + " is ancestor of " + this);
            return;
        }

        if (components == null)
            components = new Vector();

        if (components.contains(comp))
            components.remove(comp);

        if (isVirtual())
            comp.setVirtual(true);

        boolean addLast = false;
        if (pos < 0) {
            addLast = true;
            pos = getNonInheritedChildCount();
            //pos = components.size();
        }

        addToChildrenVector(pos, comp);

//        System.out.println("ADD CHILD "+comp+" to "+this+", "+comp.isVisual());
        comp.setParent(this);
        if (comp.isVisual()) {
        	addWidgetToThis(comp, pos);
        }

        if (!addLast && !isSwing() && editor != null
        		&& !usesAbsoluteTypeLayout() && !MigLayoutHandler.handlesLayout(this)) {
            comp.rebuildParent(false);
        }

        if (!alreadyInited) {
            comp.getChildren();
            comp.initProperties();
        }

        if (parent != null)
            parent.updateUI();
        
        updateUI();
        comp.bringToFront(false);
    }
    
    protected void addWidgetToThis(FormComponent comp, int pos) {
    	if (isSwing()) {
            addSwingComponentToThis(comp, pos);
        } else if (isCWT()) {
            com.cloudgarden.jigloo.typewise.TypewiseManager.addWidgetTo(
                    this, comp, pos);
        } else if (isSWT()) {
            //comp.setParent(control);
            Object wid = comp.getControl();
            if (control instanceof ScrolledComposite
                    && wid instanceof Control) {
                ((ScrolledComposite) control).setContent((Control) wid);
            }
        }
	}

	private int countJPopupMenus() {
        int cnt = 0;
        for (int i = 0; i < getChildCount(); i++)
            if (getChildAt(i).isJPopupMenu())
                cnt++;
        return cnt;
    }

    private void addSwingComponentToThis(FormComponent comp, int pos) {
        if (comp.isJPopupMenu())
            return;
        if (pos >= 0)
            pos -= countJPopupMenus();
        LayoutDataWrapper ldw = comp.getLayoutDataWrapper();
        Object ld = ldw.getLayoutData();
        if ("Card".equals(getSuperLayoutType())) {
            if (ld == null)
                ldw.setObject(comp.getName());
        } else if ("Border".equals(getSuperLayoutType())) {
            if (ld == null)
            	ldw.setObject(BorderLayout.CENTER);
        } else if (isSubclassOf(JSplitPane.class)) {
            if (ld == null)
            	ldw.setObject(JSplitPane.LEFT);
        } else if (isSubclassOf(JLayeredPane.class)) {
            if (ld == null)
            	ldw.setObject(JLayeredPane.DEFAULT_LAYER);
        }
        
        try {
            Component compo = comp.getComponent();
            
            if (compo instanceof JFrame) {
                compo = ((JFrame) compo).getContentPane();
            } else if (compo instanceof JDialog) {
                compo = ((JDialog) compo).getContentPane();
            } else if (compo instanceof JWindow) {
                compo = ((JWindow) compo).getContentPane();
            }
            
            //compo will be null for "Extra components" FormComponent
            if (compo != null && compo.getParent() == null) {
                if ("Card".equals(getSuperLayoutType())) {
                    ld = validateLayoutConstraint(ld, "Card", comp);
                    ldw.setObject(ld);
                    SwingHelper.addComponent(compo, component, (String) ld, pos);
                } else if ("Border".equals(getSuperLayoutType())) {
                    ld = validateLayoutConstraint(ld, "Border", comp);
                    ldw.setObject(ld);
                    SwingHelper.addComponent(compo, component, (String) ld, pos);
                } else if ("Enfin".equals(getSuperLayoutType()) && editor.canUseEnfinLayout()) {
                    ld = validateLayoutConstraint(ld, "Enfin", comp);
                    ldw.setObject(ld);
                    SwingHelper.addComponent(compo, component, ld, pos);
                } else if ("Table".equals(getSuperLayoutType())) {
                    ld = validateLayoutConstraint(ld, "Table", comp);
                    ldw.setObject(ld);
                    SwingHelper.addComponent(compo, component, (String) ld, pos);
                } else if (isSubclassOf(JSplitPane.class)) {
                    ld = validateLayoutConstraint(ld, "JSplitPane", comp);
                    ldw.setObject(ld);
                    comp.adjustJSplitPaneConstraints();
                    SwingHelper.addComponent(compo, component, (String) ld, pos);
                } else if (isSubclassOf(JLayeredPane.class)) {
                    ld = validateLayoutConstraint(ld, "JLayeredPane", comp);
                    ldw.setObject(ld);
                    SwingHelper.addComponent(compo, component, ((JLayerWrapper) ld).getValue(), pos);
                } else if("Form".equals(getSuperLayoutType())) {
                    Object con = ld;
                    Object vcon = validateLayoutConstraint(ld, "Form", comp);
                    if(vcon != null && !vcon.equals(con))
                        ldw.setObject(vcon);
                    SwingHelper.addComponent(compo, component, vcon, pos);
                } else if("GridBag".equals(getSuperLayoutType())) {
                    Object con = ld;
                    Object vcon = validateLayoutConstraint(con, "GridBag", comp);
                    if(vcon != null && !vcon.equals(con))
                    	ldw.setObject(vcon);
                    SwingHelper.addComponent(compo, component, vcon, pos);
                } else if(ld != null) {
                	try {
                		SwingHelper.addComponent(compo, component, ld, pos);
                	} catch(Exception e) {
                        SwingHelper.addComponent(compo, component, pos);
                	}
                } else {
                    SwingHelper.addComponent(compo, component, pos);
                }
            }
        } catch (Throwable e) {
            jiglooPlugin.handleError(e, "Error adding component " + comp + " at " + pos);
        }
        if (component instanceof JScrollPane) {
            ((JScrollPane) component).setViewportView(comp.getComponent());
        }
        if (component != null)
            component.invalidate();
    }
    
    private static Object validateLayoutConstraint(Object lc, String layoutType,
            FormComponent fc) {
		if(layoutType == null)
			return lc;
        if (layoutType.equals("Border")) {
            if (BorderLayout.NORTH.equals(lc)
                    || BorderLayout.SOUTH.equals(lc)
                    || BorderLayout.EAST.equals(lc)
                    || BorderLayout.WEST.equals(lc)
                    || BorderLayout.CENTER.equals(lc)) {
                return lc;
            } else {
                return BorderLayout.CENTER;
            }
        } else if (fc.isSwing() && layoutType.equals("Enfin") && fc.getEditor().canUseEnfinLayout()) {
            if(lc instanceof String) {
                lc = EnfinLayoutHandler.toFieldValue((String)lc);
            } else if (!(lc instanceof Number)) {
                lc = EnfinLayoutHandler.toFieldValue("HMOVE");
            }
            return lc;
        } else if (fc.isSwing() && layoutType.equals("GridBag")) {
            if(!(lc instanceof GridBagConstraints)) {
                lc = new GridBagConstraints();
            }
            return lc;
        } else if (fc.isSwing() && layoutType.equals("Anchor")) {
            if(!(lc instanceof AnchorConstraint)) {
                lc = new AnchorConstraint();
            }
            return lc;
        } else if (fc.isSwing() && layoutType.equals("Table")) {
            if(!TableLayoutHandler.isValidTableConstraint(lc)) {
                lc = "1, 1";
            }
            return lc;
        } else if (fc.isSwing() && layoutType.equals("Form")) {
            if(!(lc instanceof CellConstraints)) {
            	if(lc instanceof String) {
            		try {
            			lc = new CellConstraints((String)lc);
            		} catch(Exception e) {
            			System.err.println("Error creating CellConstraint with value "+lc);
            			lc = new CellConstraints();
            		}
            	} else {
                    lc = new CellConstraints();
            	}
            }
            CellConstraints cc = (CellConstraints)lc;
            
            if(fc.getParent() == null || fc.getParent().getComponent() == null)
            	return lc;
            
            LayoutManager lm = ((Container)fc.getParent().getComponent()).getLayout();
            if(lm instanceof com.jgoodies.forms.layout.FormLayout) {
                com.jgoodies.forms.layout.FormLayout fl = (com.jgoodies.forms.layout.FormLayout)lm;
                int rows = fl.getRowCount();
                if(cc.gridX < 1)
                    cc.gridX = 1;
                if(cc.gridY < 1)
                    cc.gridY = 1;
                if(cc.gridY+cc.gridHeight > rows+1) {
                    //fix gridY first, then gridHeight
                    if(cc.gridY > rows)
                        cc.gridY = rows;
                    
                    if(cc.gridY+cc.gridHeight > rows+1)
                        cc.gridHeight = rows - cc.gridY+1;
                }
                
                int cols = fl.getColumnCount();
                if(cc.gridX+cc.gridWidth > cols+1) {
                    if(cc.gridX > cols)
                        cc.gridX = cols;
                    
                    if(cc.gridX+cc.gridWidth > cols+1)
                        cc.gridWidth = cols - cc.gridX+1;
                }
            }
        } else if (layoutType.equals("Card")) {
            if (!(lc instanceof String)) {
                //fc.getLayoutDataWrapper().setObject(null);
                return fc.getNameInCode();
            }
        } else if (layoutType.equals("JSplitPane")) {
            if (JSplitPane.RIGHT.equals(lc) 
            		|| JSplitPane.LEFT.equals(lc)
                    || JSplitPane.TOP.equals(lc)
                    || JSplitPane.BOTTOM.equals(lc)) {
                return lc;
            } else {
                return JSplitPane.LEFT;
            }

        }
        return lc;
    }

    public FormComponent getCopiedTo() {
        return copiedTo;
    };

    public FormComponent getCopiedFrom() {
        return copiedFrom;
    };

    public void setCopiedTo(FormComponent copy) {
        copiedTo = copy;
    }

    public void setCopiedFrom(FormComponent orig) {
        copiedFrom = orig;
    }
    
    public FormComponent getCopy(boolean deepCopy, FormEditor newEditor) {

//        System.err.println("GETTING COPY OF "+this);
        
        FormComponent copyComp;
        if(newEditor == null)
            copyComp = FormComponent.newFormComponent(newEditor, getClassName(), this);
        else
        	copyComp = FormComponent.newFormComponent(newEditor, getClassName());
        this.copiedTo = copyComp;
        copyComp.setCopiedFrom(this);

        copyComp.setClassType(classType);
        copyComp.setInherited(isInherited());
        copyComp.setInheritedField(isInheritedField());
        copyComp.setInheritedDeclared(isInheritedDeclared());
        copyComp.setName(getName(), false);
        copyComp.setClassName(getClassName());
        copyComp.setModifier(getModifier());
        copyComp.setInMainTree(isInMainTree());
        copyComp.setWasCut(wasCut());
        copyComp.setInline(isInline());
        
        copyComp.setContentPane(isContentPane, useExistingCP);
        copyComp.setEditor(newEditor);
        copyComp.setStyle(getStyle());
        
        //v4.0M3
        //=====pass in copy of parameters (in case parameters are FCs)
        Object[] cpc = null;
        if(constructorParams != null) {
            cpc =new Object[constructorParams.length];
            for (int i = 0; i < cpc.length; i++) {
                if(constructorParams[i] instanceof FormComponent)
                    cpc[i] = ((FormComponent)constructorParams[i]).getCopy(false, editor);
                else
                    cpc[i] = copyObject(constructorParams[i], null, this);
            }
        }

        copyComp.setConstructor(constructor, cpc,	getConstructorCode());
        
        Iterator it;
        if (extraPropCode != null) {
            it = extraPropCode.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                copyComp.setExtraPropertyCode(key, getExtraPropertyCode(key));
            }
        }

        copyComp.methodCalls = (Vector) methodCalls.clone();
        copyComp.builderMethodCalls = (Vector) builderMethodCalls.clone();
        for(int i=0; i<copyComp.builderMethodCalls.size(); i++) {
        	Object[] call = (Object[])copyComp.builderMethodCalls.get(i);
        	Object[] params = (Object[])call[1];
        	for (int j = 0; j < params.length; j++) {
                if(params[j] instanceof FormComponent) {
                	FormComponent fc = (FormComponent)params[j];
                	params[j] = fc.getCopy(false, editor);
                	fc.setExistsInCode(true);
                	fc.setInMainTree(true);
                }
			}
        }
        
        if (properties != null) {
            HashMap props = new HashMap();
            it = properties.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (!isSwing()) {
                    if (key.equals("parent"))
                        continue;
                    if (key.equals("data"))
                        continue;
                    if (key.equals("layoutData"))
                        continue;
                    if (key.equals("layout"))
                        continue;
                }
                Object obj = properties.get(key);
//                System.err.println("COPYING "+key+", "+obj+", "+getName());
                if(this.equals(obj)) {
                	props.put(key, this); //v4.2.1
                } else {
                	Object copy = copyObject(obj, key, copyComp);
                	props.put(key, copy);
                }
            }
            copyComp.setProperties(props);
            
            if(propertyValueCode != null) {
            	it = propertyValueCode.keySet().iterator();
            	while (it.hasNext()) {
            		Object key = it.next();
            		copyComp.setPropertyValueCode(key, (String) propertyValueCode.get(key));
            	}
            }
            
            Vector setProperties = new Vector();
            for (int i = 0; i < setProps.size(); i++) {
                Object key = setProps.elementAt(i);
//                System.err.println("COPYING SET PROP "+key+",  "+properties.get(key)+", "+getName());
                setProperties.add(key);
            }
            copyComp.setSetProperties(setProperties);
        } //System.out.println("COPY SETTAB "+fc.getEditor());


        copyComp.setTabTitle(getTabTitle());
        if (deepCopy && components != null) {
            Vector subs = new Vector();
            for (int i = 0; i < components.size(); i++) {
                FormComponent sub = getChildAt(i);
                //if (sub.isInherited())
                //continue;
                sub = sub.getCopy(deepCopy, newEditor);
                subs.add(sub);
            }
            copyComp.setSubComponents(subs);
        }

        LayoutWrapper lwCopy = (LayoutWrapper) getLayoutWrapper().getCopy(copyComp);
        //System.err.println(
        //"GOT LAYOUTWRAPPER COPY " + lwCopy + ", " + getName());

        //don't want to copy the name!
        //lwCopy.setName(null);

        //when this FCis pasted, if it needs a new name *then* set LW and LDW
        // names to null
        if(getInheritedCount() !=  0) {
            //don't want to actually change this layout wrapper, since that would
            //involve removing and adding in all inherited elements. Assume
            //that when this method is called it is just setting the layout to what
            //it is in reality
        } else {
            copyComp.setLayoutWrapper(lwCopy);
        }

        LayoutDataWrapper ldwCopy = getLayoutDataWrapper().getCopy(copyComp);
        //ldwCopy.setName(null);

        //System.err.println(
        //"GOT LAYOUTDATAWRAPPER COPY " + ldwCopy + ", " + getName());
        copyComp.setLayoutDataWrapper(ldwCopy);

        //to copy or not the event handlers? copy for now
        EventWrapper ewCopy = getEventWrapper().getCopy(copyComp);
        copyComp.setEventWrapper(ewCopy);

        if (propertyValueCode != null) {
            it = propertyValueCode.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if("layout".equals(key))
                	continue;
                if("layoutData".equals(key))
                	continue;
                copyComp.setPropertyValueCode(key, (String) propertyValueCode
                        .get(key));
            }
        }
        copyComp.setBoundsToRoot(getBoundsRelativeToRoot());
        return copyComp;
    }

	public boolean convertToSWTResourceManager() {
        boolean updated = false;
        if (propNames == null)
            return false;
        for (int i = 0; i < propNames.size(); i++) {
            String name = (String) propNames.elementAt(i);
            if (isPropertySet(name)
                    && ClassUtils.isResource(getPropType(name))) {
                if (propertyValueCode != null)
                    propertyValueCode.remove(name);
                Object val = getPropertyValue(name);
                String guessName = getNameInCode() + name;
                if(isRoot())
                    guessName = getEditor().getClassName()+name;
                getJavaCodeParser().removeResourceFromCode(val,
                        guessName);
                propsNeedingCodeUpdate.add(name);
                updateInCode(name);
                updated = true;
            }
        }
//        if (updated) {
//            repairInCode();
//        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).convertToSWTResourceManager())
                updated = true;
        }
        return updated;
    }

    public static Object copyObject(Object obj, Object id, FormComponent comp) {
        if (obj == null)
            return null;
        //System.out.println("COPYOBJECT " + obj.getClass().getName());
        if (obj instanceof Boolean)
            return obj;
        if (obj instanceof Integer)
            return obj;
        if (obj instanceof Float)
            return obj;
        if (obj instanceof Long)
            return obj;
        if (obj instanceof Character)
            return obj;
        if (obj instanceof Double)
            return obj;
        if (obj instanceof String)
            return obj;

        if (obj instanceof Point) {
            Point pt = (Point) obj;
            return new Point(pt.x, pt.y);
        }
        if (obj instanceof Rectangle) {
            Rectangle rec = (Rectangle) obj;
            return new Rectangle(rec.x, rec.y, rec.width, rec.height);
        }
        if (obj instanceof FontWrapper) {
            FontWrapper fw = (FontWrapper) obj;
            return fw.getCopy(comp);
        }
        if (obj instanceof IconWrapper) {
            IconWrapper fw = (IconWrapper) obj;
            return fw.getCopy(comp);
        }
        if (obj instanceof ImageWrapper) {
            ImageWrapper fw = (ImageWrapper) obj;
            return fw.getCopy(comp);
        }
        if (obj instanceof FieldWrapper) {
            FieldWrapper fw = (FieldWrapper) obj;
            return fw.getCopy();
        }
        if (obj instanceof FormComponentWrapper) {
            FormComponentWrapper fcw = (FormComponentWrapper) obj;
            return fcw.getCopy();
        }
        if (obj instanceof ColorWrapper) {
            ColorWrapper cw = (ColorWrapper) obj;
            return cw.getCopy();
        }
        if (obj instanceof IWrapper) {
            IWrapper lw = (IWrapper) obj;
            return lw.getCopy(comp);
        }
        if (obj instanceof com.jgoodies.forms.layout.FormLayout) {
        	com.jgoodies.forms.layout.FormLayout lw = (com.jgoodies.forms.layout.FormLayout) obj;
        	ColumnSpec[] cspecs = new ColumnSpec[lw.getColumnCount()];
        	for(int i=0; i<lw.getColumnCount();i++)
        		cspecs[i] = lw.getColumnSpec(i+1);
        	RowSpec[] rspecs = new RowSpec[lw.getRowCount()];
        	for(int i=0; i<lw.getRowCount();i++)
        		rspecs[i] = lw.getRowSpec(i+1);
        	return new com.jgoodies.forms.layout.FormLayout(cspecs, rspecs);
        }
        if (obj instanceof BorderPropertySource) {
            BorderPropertySource bps = (BorderPropertySource) obj;
            return bps.getCopy(comp);
        }
        if (obj instanceof SizePropertySource) {
            SizePropertySource sps = (SizePropertySource) obj;
            Point pt = sps.getValue();
            return new SizePropertySource(new Point(pt.x, pt.y), sps
                    .getFormComponent(), sps.getPropertyName());
        }
        if (obj instanceof RectanglePropertySource) {
            RectanglePropertySource sps = (RectanglePropertySource) obj;
            Rectangle pt = sps.getValue();
            return new RectanglePropertySource(new Rectangle(pt.x, pt.y,
                    pt.width, pt.height), sps.getFormComponent(), sps
                    .getPropertyName());
        }
        if (jiglooPlugin.canUseSwing() && obj instanceof InsetsPropertySource) {
            InsetsPropertySource sps = (InsetsPropertySource) obj;
            Insets pt = sps.getValue();
            return new InsetsPropertySource(new Insets(pt.top, pt.left,
                    pt.bottom, pt.right));
        }
        if (obj instanceof FormComponent) {
            FormComponent fc = (FormComponent) obj;
            FormComponent copy = fc.getCopy(true, comp.getEditor());
            //we get here when a property is a FormComponent (eg, model)
            //this is not really correct, but will work when cutting and
            // pasting - will cause problems when copying and pasting a JTable, for
            // instance
            copy.setNonVisualObject(fc.getNonVisualObject());
            return copy;
        }
        //System.err.println("FAILED TO COPY id=" + id + ", obj=" + obj + ", "
        // + obj.getClass());
        return obj;
//        return null;
    }

    public void setProperties(HashMap properties) {
        this.properties = properties;
    }

    public void setSetProperties(Vector setProps) {
        this.setProps = setProps;
    }

    public void setSubComponents(Vector subs) {
        this.components = subs;
        if (subs == null)
            return;
        for (int i = 0; i < subs.size(); i++) {
            ((FormComponent) subs.elementAt(i)).setParent(this);
        }
    }

    public void clearChildren() {
        if(components == null)
            return;
        for(int i=0;i<components.size();i++)
            getChildAt(i).clearChildren();
        components.clear();
    }
    
    public void remove(FormComponent comp) {
        components.remove(comp);
        if(editorMenu != null)
        	editorMenu.dispose();
//        System.err.println("FC: removing " + comp+", "+this);
        setEditorReload(true);
        if (!isVisual())
            return;
        if (isSwing()) {
            removeFromGroupLayout(comp);
       		removeComponent(comp.getComponent(), component);
        } else {
        }
        updateUI();
        if (parent != null)
            parent.updateUI();
    }

    private void removeFromGroupLayout(FormComponent comp) {
    	removeFromGroupLayout(comp, true);
    }

    private void removeFromGroupLayout(FormComponent comp, boolean preserveSpacing) {
        if(layoutWrapper != null)
            layoutWrapper.removeFromGroupLayout(comp, preserveSpacing);
    }

    private FormComponent findChild(String childName) {
        for (int i = 0; i < components.size(); i++) {
            if (childName.equals(getChildAt(i).getName()))
                return getChildAt(i);
        }
        return null;
    }

    public Vector getAllChildFields(boolean includeThis) {
        Vector fields = new Vector();
        return getFields(fields, includeThis);
    }

    public IDOMField getFormField() {
        IDOMField field = domFac.createField();
        field.setName(getName());
        field.setType(getTranslatedClassName());
        return field;
    }

    protected Vector getFields(Vector fields, boolean includeThis) {
        if (includeThis)
            fields.add(getFormField());
        if (components != null) {
            for (int i = 0; i < components.size(); i++) {
                FormComponent comp = (FormComponent) components.elementAt(i);
                comp.getFields(fields, true);
            }
        }
        return fields;
    }

    public boolean hasChildren() {
        if (components == null)
            return false;
        return (components.size() != 0);
    }

    public boolean isFrame() {
        if (!isSwing())
            return false;
        if (isSubclassOf(JFrame.class) || isSubclassOf(JApplet.class)
        		|| isSubclassOf(JDialog.class) || isSubclassOf(JWindow.class))
            //		if (getShortClassName().equals("JFrame")
            //			|| getShortClassName().equals("JApplet")
            //			|| getShortClassName().equals("JDialog"))
            return true;
        return false;
    }

    public Object getBeanDescriptorValue(Class cls, String name) {
        BeanInfo bi;
        while(cls != null && (bi = BeanHandler.getBeanInfo(cls, getEditor().getProject())) != null) {
            BeanDescriptor bd = bi.getBeanDescriptor();
            if(bd == null)
            	return null;
            Object val = bd.getValue(name);
            if(val != null)
                return val;
            cls = cls.getSuperclass();
        }
        return null;
    }
    
    public boolean usesContentPane() {
    	if(usesContentPane == null) {
    		if (!isSwing())
    			usesContentPane = Boolean.FALSE;
    		else if (isSubclassOf(JFrame.class) || isSubclassOf(JApplet.class)
    				|| isSubclassOf(JDialog.class) || isSubclassOf(JWindow.class)
    				|| isSubclassOf(JInternalFrame.class))
    			usesContentPane = Boolean.TRUE;
    		else if("getContentPane".equals(getBeanDescriptorValue(getMainClass(), "containerDelegate")))
    			usesContentPane = Boolean.TRUE;
    		else
    			usesContentPane = Boolean.FALSE;
    	}
    	return usesContentPane.booleanValue();
    }

    public void setContentPane(Container cont) {
        if (!isSwing())
            return;
        Method scp = null;
        boolean ok = false;
        try {
            Class cls = null;
            if(rawComponent != null)
                cls = rawComponent.getClass();
            else if(component != null)
                cls = component.getClass();
            if(cls != null) {
                scp = cls.getMethod("setContentPane", new Class[] {Container.class});
                if(scp != null)
                    scp.invoke(rawComponent != null ? rawComponent : component,
                            new Object[] {cont});
                ok = true;
            }
        } catch(Throwable t) {}

        if(!ok) {
            ((Container) component).setLayout(new BorderLayout(0, 0));
            ((Container) component).add(cont, BorderLayout.CENTER);
        }
        
    }

    public boolean canHaveJMenuBar() {
        if (!isSwing())
            return false;
        if (isSubclassOf(JFrame.class) || isSubclassOf(JApplet.class)
                || isSubclassOf(JDialog.class)
                || isSubclassOf(JInternalFrame.class))
            return true;
        return false;
    }

    public boolean isRoot() {
    	return isRoot;
//        return this.equals(getRootComponent());
    }

    public boolean isNonVisualRoot() {
    	return isNonVisualRoot;
//        if (editor == null)
//            return false;
//        return this.equals(editor.getNonVisualRoot());
    }

    public boolean isExtraCompRoot() {
    	return isExtraCompRoot;
//        if (editor == null)
//            return false;
//        return this.equals(editor.getExtraCompRoot());
    }

    public void setExtraCompRoot(boolean isExtraCompRoot) {
		this.isExtraCompRoot = isExtraCompRoot;
	}

	public void setNonVisualRoot(boolean isNonVisualRoot) {
		this.isNonVisualRoot = isNonVisualRoot;
	}

	private boolean needsStyle() {
        if ( Cacher.isAssignableFrom(Menu.class, getMainClass()) && parent != null)
            return false;
        if ( Cacher.isAssignableFrom(ProgressIndicator.class, getMainClass()))
            return false;
        return true;
    }

    public String getJavaConstructor(IJavaCodeManager jcm) {
        return getJavaCodeForConstructor(jcm, false);
    }

    public String getParentNameInCode() {
        if (isBaseComponent()) {
            return "this";
        }
        FormComponent par = getParent();
        if (par == null) {
            par = getRootComponent();
        }
        if (!SWTUtils.isItem(this) && SWTUtils.isItem(par)) {
            par = par.getParent();
        }
        return par.getNameInCode();

    }

    public String getNonBlockName() {
        return JiglooUtils.getUnqualifiedName(getName());
    }

    /**
     * If propertyValueCode has a value for this property, then return it,
     * otherwise return null.
     */
    public String getPropertyCode(String pName) {
        if (propertyValueCode == null)
            return null;
        return (String) propertyValueCode.get(pName);
    }

	//when a FC is reassigned, originalName is the name it was originally
	//assigned as (eg, when "tabbedPane" is created in a method and then
	//returned from that method and reassigned to "panel", then "tabbedPane"
	//will be originalName, and used as the nameInCode
	private String originalName = null;

	public void setOriginalName(String name) {
		originalName = name;
	}
	
    public Object getOriginalName() {
        return originalName;
    }

    //when an inherited object is created, inheritedName is set (eg, form.body)
    //so that if the inherited element is assigned (eg, Composite body = form.getBody())
    //the original element can still be found via form.getBody()
    private String inheritedName = null;

	public void setInheritedName(String name) {
	    inheritedName = name;
	}
	
    public Object getInheritedName() {
        return inheritedName;
    }
    
    private String declarationInCode;
    
    /**
     * use this when an element is not defined in code but instead is obtained from an
     * expression like "actionMap.getAction("test")"
     * @param nameInCode
     */
    public void setDeclarationInCode(String declarationInCode) {
    	this.declarationInCode = declarationInCode;
    }
    
    public String getDeclarationInCode() {
    	if(declarationInCode != null && !"".equals(declarationInCode)) {
    	    ActionManager.testForActionManagerGetter(editor, declarationInCode);
    		return declarationInCode;
    	}
    	return getNameInCode();
    }
    
    public String getNameInCode() {
    	String nic = getName();
        try {
            if(editor == null)
            	return "none";
            
            if(getEditor().isSingleFrameApplication() && "mainFrame".equals(nic))
                return "getMainFrame()";
            
            if(originalName != null)
                nic = originalName;
            
            nic = JiglooUtils.getUnqualifiedName(nic);
            nic = removeHiddenPart(nic);
            
            if (isInherited && getParent() != null) {
                String superName = getParent().getNameInCode();
                if("this".equals(superName))
                    superName = "super";
                superName = getJavaCodeParser().convertToMethod(getParent().getName(), superName, false);
                if (isInheritedField) {
                    nic = superName + "." + nic;
                } else {
                    nic = superName + ".get"
                    + JiglooUtils.capitalize(nic) + "()";
                }
            }
        } catch(Throwable t) {
            t.printStackTrace();
    	}
        return nic;
    }

    private boolean isValidClass() {
        return !(className.endsWith(";") || className.startsWith("[") 
                || className.startsWith("java.lang."));
    }

    public String getJavaDeclaration(boolean isStatic) {
        String acc = "";
        if((modifier & MOD_PUBLIC) != 0)
            acc = "public ";
        if((modifier & MOD_PROTECTED) != 0)
            acc = "protected ";
        if((modifier & MOD_PRIVATE) != 0 || "".equals(acc))
            acc = "private ";
//        if((modifier & MOD_FINAL) != 0)
//            acc = "final "+acc;
        if(isStatic || (modifier & MOD_STATIC) != 0)
            acc = "static "+acc;
        return acc + getClassNameForCode() + " " + getNameInCode() + ";";
    }

    public String getJavaCodeForConstructor(IJavaCodeManager jcm,
            boolean includeChildren) {
        try {
            if (!isValidClass())
                return "";
            
            if(eclipseFormCall != null) {
                return eclipseFormCall.getJavaCode(this, jcm, name);
            }
            
            //TODO Can't extend Shell, so code should be for a Composite, and
            //should create a Shell internally so it can set it's Menu, and
            // create itself
            //(as a Composite) using that Shell as it's parent.
            StringBuffer code = new StringBuffer();
            String clsName = getClassNameForCode();
            int pos = clsName.indexOf("$");
            if (pos > 0)
                clsName = clsName.substring(pos + 1);

            String name = getNameInCode();

            String storedCode = getConstructorCode();
            if(storedCode != null) {
            	return name+" = "+storedCode+";";
            }
            
            String parentName = getParentNameInCode();

            if(parent != null && parent.equals(editor.getExtraCompRoot()))
            	parentName = "parent";

            if (isRoot()) {
                if (isA(Dialog.class)) {
                    jcm.addField("dialogShell", Shell.class.getName(), false, null);
                    code
                            .append("\t\tShell parent = getParent();\n"
                                    + "\t\tdialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);\n"
                                    + "\t\tdialogShell.setText(getText());\n");
                }

            } else {
                //if (jiglooPlugin.makeComments())
                //code.append("\n\t\t//Initializing " + getName() + "\n");
            	
            	if (isSwingInSwt()) {
            		code.append("\t\t" + name + " = SWT_AWT.new_Frame(" +parentName+");\n");
            		jcm.addImport(SWT_AWT.class.getName());
            	} else if (isSwtInSwing()) {
            		code.append("\t\t" + name + " = SWT_AWT.new_Shell(Display.getDefault(), " +parentName+");\n");
            		jcm.addImport(SWT_AWT.class.getName());
            		jcm.addImport(Display.class.getName());
            	} else if(isSubclassOf(NonDefaultConstructor.class)) {
            		code.append(((NonDefaultConstructor)getObject(true)).getConstructorCode(this, jcm));
            	} else if (isSwing() || !isVisual()) {
                    if (!isRoot() && isJDialog()) {
                        if (getRootComponent().isSubclassOf(JDialog.class)
                                || getRootComponent()
                                        .isSubclassOf(JFrame.class)) {
                            code.append("\t\t" + name + " = new " + clsName
                                    + "(this);\n");
                        } else {
                            code.append("\t\t" + name + " = new " + clsName
                                    + "();\n");
                        }
                    } else {
                        code.append("\t\t" + name + " = new " + clsName
                                + "();\n");
                    }
                } else if (isCWT()) {
                    code.append(com.cloudgarden.jigloo.typewise.TypewiseManager.getJavaCodeForConstructor(this, name, clsName));
                } else {
                    code.append("\t\t" + name + " = new " + clsName + "(");
                    if (parent == null && isA(Menu.class)) {
                        if (getRootComponent().isDialogShell())
                            code.append(getRootComponent().getNameInCode());
                        else
                            code.append("getShell()");
                    } else if(parent.isJFaceViewer()) {
                        code.append(parent.getJFaceViewerControlCode(parentName));
                    } else {
                        code.append(parentName);
                    }
                    if (needsStyle()) {
                        jcm.addImport(SWT.class.getName());
                        code.append(", " + getStyleString());
                    }
                    code.append(");\n");
                }
            }

            if (includeChildren) {
                for (int i = 0; i < components.size(); i++) {
                    FormComponent child = (FormComponent) components
                            .elementAt(i);
                    String ccode = child.getJavaCodeForConstructor(jcm, true);
                    code.append(ccode);
                }
            }

            return code.toString();

        } catch (Throwable t) {
            System.err.println("ERROR GETTING CODE FOR " + this);
            jiglooPlugin.handleError(t);
            return "";
        }
    }

    public Object createSwingModel() {
        Object model = null;
        if(isSubclassOf(JTable.class)) {
            model = new DefaultTableModel(new String[][] { { "One", "Two" },
                    { "Three", "Four" } }, new String[] { "Column 1", "Column 2" });
        } else if(isSubclassOf(JComboBox.class)) {
            model = new DefaultComboBoxModel(new String[] { "Item One",
            "Item Two" });
        } else if(isSubclassOf(JList.class)) {
            model = new DefaultComboBoxModel(new String[] { "Item One",
            "Item Two" });
        } else if(isSubclassOf(JSpinner.class)) {
            model = new SpinnerListModel(new String[] { "Sun", "Mon", "Tue",
                    "Wed", "Thu", "Fri", "Sat" });
        }
        return model;
    }
    
    private void newModelComponent(String modelName, Object model) {
        FormComponent dm = FormComponent.newFormComponent(editor, model.getClass().getName());
        dm.setEditor(editor);
        dm.setName(modelName);
        dm.setClassName(model.getClass().getName());
        dm.setNonVisualObject(model);
        dm.setInMainTree(true);
        editor.addNonVisualComponent(dm);
        setPropertyValueInternal("model", dm, true, false);
    }

    public String getJTableModelDefaultCode(String modelName) {
        Object model = new DefaultTableModel(new String[][] { { "One", "Two" },
                { "Three", "Four" } }, new String[] { "Column 1", "Column 2" });
        newModelComponent(modelName, model);
        if (jiglooPlugin.useImports()) {
            getJavaCodeParser().addImport(TableModel.class.getName());
            getJavaCodeParser().addImport(DefaultTableModel.class.getName());
            return "TableModel "
                    + modelName
                    + " = \n"
                    + "new DefaultTableModel(\n"
                    + "new String[][] { { \"One\", \"Two\" }, { \"Three\", \"Four\" } },\n"
                    + "new String[] { \"Column 1\", \"Column 2\" });";
        } else {
            return "javax.swing.table.TableModel "
                    + modelName
                    + " = \n"
                    + "new javax.swing.table.DefaultTableModel(\n"
                    + "new String[][] { { \"One\", \"Two\" }, { \"Three\", \"Four\" } },\n"
                    + "new String[] { \"Column 1\", \"Column 2\" });";
        }
    }

    public String getJComboBoxModelDefaultCode(String modelName) {
        Object model = new DefaultComboBoxModel(new String[] { "Item One",
                "Item Two" });
        newModelComponent(modelName, model);
        if (jiglooPlugin.useImports()) {
            getJavaCodeParser().addImport(ComboBoxModel.class.getName());
            getJavaCodeParser().addImport(DefaultComboBoxModel.class.getName());
            return "ComboBoxModel " + modelName + " = \n"
                    + "new DefaultComboBoxModel(\n"
                    + "new String[] { \"Item One\", \"Item Two\" });";
        } else {
            return "javax.swing.ComboBoxModel " + modelName + " = \n"
                    + "new javax.swing.DefaultComboBoxModel(\n"
                    + "new String[] { \"Item One\", \"Item Two\" });";

        }
    }

    public String getJSpinnerModelDefaultCode(String modelName) {
        Object model = new SpinnerListModel(new String[] { "Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat" });
        newModelComponent(modelName, model);
        if (jiglooPlugin.useImports()) {
            getJavaCodeParser().addImport(SpinnerListModel.class.getName());
            return "SpinnerListModel "
                    + modelName
                    + " = \n"
                    + "new SpinnerListModel(\n"
                    + "new String[] { \"Sun\", \"Mon\" , \"Tue\" , \"Wed\" , \"Thu\" , \"Fri\" , \"Sat\" });";

        } else {
            return "javax.swing.SpinnerListModel "
                    + modelName
                    + " = \n"
                    + "new javax.swing.SpinnerListModel(\n"
                    + "new String[] { \"Sun\", \"Mon\" , \"Tue\" , \"Wed\" , \"Thu\" , \"Fri\" , \"Sat\" });";
        }
    }

    public String getJListModelDefaultCode(String modelName) {
        Object model = new DefaultComboBoxModel(new String[] { "Item One",
                "Item Two" });
        newModelComponent(modelName, model);
        if (jiglooPlugin.useImports()) {
            getJavaCodeParser().addImport(ListModel.class.getName());
            getJavaCodeParser().addImport(DefaultComboBoxModel.class.getName());
            return "ListModel " + modelName + " = \n"
                    + "new DefaultComboBoxModel(\n"
                    + "new String[] { \"Item One\", \"Item Two\" });";
        } else {
            return "javax.swing.ListModel " + modelName + " = \n"
                    + "new javax.swing.DefaultComboBoxModel(\n"
                    + "new String[] { \"Item One\", \"Item Two\" });";
        }
    }

    private String getContentPaneCode(String parName) {
	    if("this".equals(parName))
	        return "getContentPane()";
	    return parName + ".getContentPane()";
	}
	
    private String getConnectionCode() {
    	return getJCP().getConnectionCode(this);
    }
    
    public FormComponent getSwtInSwingChild() {
        if(components == null)
            return null;
        for(int i=0;i<components.size(); i++) {
            FormComponent c = getChildAt(i);
            if(c.isSwtInSwing())
                return c;
            FormComponent fc2 = c.getSwtInSwingChild();
            if(fc2 != null)
                return fc2;
        }
        return null;
    }

    public String getJavaAddToParentCode(IJavaCodeManager jcm, String name) {

        if(eclipseFormCall != null) {
            return eclipseFormCall.getJavaCode(this, jcm, name);
        }
    	//otherwise, since JPopupMenus are inherited objects in Java 1.5,
    	//addMethod("setComponentPopupMenu") is called. (Ideally, this
    	//method should not be called at all for inherited components, but
    	//that can be dealt with later!).
    	if(isInherited())
    		return null;
    	
    	if(parent != null && parent.usesGroupLayout())
    	    return "";
    	
        //String name = getNameInCode();

        if (getParent() == null) {
            if (isA(Menu.class)) {
                if (getRootComponent().isDialogShell())
                    return getRootComponent().getNameInCode() + ".setMenuBar("
                            + name + ");\n";
                else
                    return "getShell().setMenuBar(" + name + ");\n";
            } else if (getClassName().equals(JMenuBar.class.getName())) {
                if(getEditor().isSingleFrameApplication())
                    return "getMainFrame().setJMenuBar("+name+");\n";
                return "setJMenuBar(" + name + ");\n";
            }
            return null;
        }

        if (isCWT()) {
            return com.cloudgarden.jigloo.typewise.TypewiseManager
                    .getJavaAddToParentCode(this, name);
        }

        String parentName = getParentNameInCode();

        if (isA(Menu.class)) {
            return parentName + ".setMenu(" + name + ");\n";
            
        } else if (isA(JMenuBar.class)) {
            return parentName + ".setJMenuBar(" + name + ");\n";

        } else if (isContentPane()) {
            return parentName + ".setContentPane(" + name + ");\n";

        } else if (isSwingInSwt() || isSwtInSwing()) {
            return null;

        } else if (isJPopupMenu()) {
			String code = ""
					+ "parent.addMouseListener(new java.awt.event.MouseAdapter() {\n"
					+ "public void mousePressed(java.awt.event.MouseEvent e) {\n"
					+ "if(e.isPopupTrigger())\n"
					+ "menu.show(parent, e.getX(), e.getY());\n"
					+ "}\n"
					+ "public void mouseReleased(java.awt.event.MouseEvent e) {\n"
					+ "if(e.isPopupTrigger())\n"
					+ "menu.show(parent, e.getX(), e.getY());\n" + "}\n"
					+ "});\n";
			ASTNode node = jcm
					.addMethod(
							"setComponentPopupMenu",
							code,
							"void",
							new String[] { "final java.awt.Component",
									"final javax.swing.JPopupMenu" },
							new String[] { "parent", "menu" },
							Flags.AccPrivate,
							"\t/**\n\t* Auto-generated method for setting the popup menu for a component\n\t*/\n");
			((JavaCodeParser)jcm).indentNode(node);
			return "setComponentPopupMenu(" + parentName + ", " + name + ");\n";
			//return parentName+ ".setComponentPopupMenu(" + name + ");\n";
        
        } else if (getParent().isJScrollPane()) {
            return parentName + ".setViewportView(" + name + ");\n";
            
		} else {
			if (!parent.isNonVisualRoot()) {
				if (isSwing()) {
					if (parent.isAbstractFormBuilder()) {
						String cc = getConnectionCode();
						if (cc != null) {
							if (cc.indexOf("addGriddedButtons") >= 0) {
								int p1 = cc.indexOf("{");
								int p2 = cc.lastIndexOf("}");
								String nc = cc.substring(0, p1 + 1);
								for (int i = 0; i < parent
										.getNonInheritedChildCount(); i++) {
									if (i != 0)
										nc += ", ";
									FormComponent fc1 = parent
											.getNonInheritedChildAt(i);
									if (fc1.isInline())
										nc += fc1.getJavaConstructor(jcm);
									else
										nc += fc1.getNameInCode();
								}
								nc += cc.substring(p2);
								return nc + ";";
							} else if (cc.indexOf("addLabel") >= 0) {
								int p1 = cc.indexOf("\"");
								int p2 = cc.lastIndexOf("\"");
								String nc = cc.substring(0, p1 + 1);
								nc += getPropertyValue("text");
								nc += cc.substring(p2);
								return nc + ";";
							}
						}
						//                		return cc+"; // TEST";
					}
					StringBuffer code = new StringBuffer();
					if (parent.usesContentPane()) {
                        code.append(getContentPaneCode(parentName));
                    } else {
                        code.append(parentName);
                    }

                    String lt = getParentSuperLayoutType();

                    if (parent.isJTabbedPane()) {
                        String tt = null;
                        if (propertyValueCode != null)
                            tt = (String) propertyValueCode.get("LAYOUT_CONSTRAINT");
                        if (tt == null)
                            tt = "\"" + getTabTitle() + "\"";
                        //put null in place of icon and tooltip
                        code.append(".addTab(" + tt + ", null, " + name + ", null);\n");
                        return code.toString();
                        
                    } else {
                        code.append(".add(" + name);
                    }
                    Object layoutConstraint = layoutDataWrapper.getLayoutData();

                    if (parent.isSubclassOf(JLayeredPane.class)) {
                    	jcm.addImport("javax.swing.JLayeredPane");
                    	if(layoutConstraint instanceof JLayerWrapper) {
                    		code.append(", "+((JLayerWrapper)layoutConstraint).toString());
                    	} else {
                    		code.append(", JLayeredPane.DEFAULT_LAYER");
                    	}
                    } else if (parent.isJSplitPane()) {
                    	if (layoutConstraint != null) {
                    		if (jiglooPlugin.useImports()) {
                    			jcm.addImport("javax.swing.JSplitPane");
                    			if (layoutConstraint.equals("top"))
                    				code.append(", JSplitPane.TOP");
                    			if (layoutConstraint.equals("bottom"))
                    				code.append(", JSplitPane.BOTTOM");
                    			if (layoutConstraint.equals("left"))
                    				code.append(", JSplitPane.LEFT");
                    			if (layoutConstraint.equals("right"))
                    				code.append(", JSplitPane.RIGHT");
                    		} else {
                    			if (layoutConstraint.equals("top"))
                    				code.append(", javax.swing.JSplitPane.TOP");
                    			if (layoutConstraint.equals("bottom"))
                    				code .append(", javax.swing.JSplitPane.BOTTOM");
                    			if (layoutConstraint.equals("left"))
                    				code.append(", javax.swing.JSplitPane.LEFT");
                    			if (layoutConstraint.equals("right"))
                    				code.append(", javax.swing.JSplitPane.RIGHT");
                    		}
                    	}
                    } else if ("Border".equals(lt)) {
                    	if (layoutConstraint != null) {
                    		if (jiglooPlugin.useImports()) {
                    			jcm.addImport(java.awt.BorderLayout.class
                    					.getName());
                    			if (layoutConstraint.equals("North"))
                    				code.append(", BorderLayout.NORTH");
                    			if (layoutConstraint.equals("South"))
                    				code.append(", BorderLayout.SOUTH");
                    			if (layoutConstraint.equals("East"))
                    				code.append(", BorderLayout.EAST");
                    			if (layoutConstraint.equals("West"))
                    				code.append(", BorderLayout.WEST");
                                if (layoutConstraint.equals("Center"))
                                    code.append(", BorderLayout.CENTER");
                            } else {
                                if (layoutConstraint.equals("North"))
                                    code
                                            .append(", java.awt.BorderLayout.NORTH");
                                if (layoutConstraint.equals("South"))
                                    code
                                            .append(", java.awt.BorderLayout.SOUTH");
                                if (layoutConstraint.equals("East"))
                                    code.append(", java.awt.BorderLayout.EAST");
                                if (layoutConstraint.equals("West"))
                                    code.append(", java.awt.BorderLayout.WEST");
                                if (layoutConstraint.equals("Center"))
                                    code
                                            .append(", java.awt.BorderLayout.CENTER");
                            }
                        }
                    } else if("Flow".equals(lt)) {
                        //no constraint, but want to avoid possibility of layoutData != null messing this up
                    } else if ("Card".equals(lt)) {
                        code.append(", \"" + layoutConstraint + "\"");
                    } else if ("Enfin".equals(lt) && editor.canUseEnfinLayout()) {
                        code.append(", "+ EnfinLayoutHandler.getJavaString((String)layoutConstraint, getJavaCodeParser()));
                    } else if ("Table".equals(lt)) {
                        code.append(", \"" + layoutConstraint + "\"");
                    } else if (layoutConstraint instanceof String) {
                        code.append(", \"" + layoutConstraint + "\"");

                    } else if ("GridBag".equals(lt) || "Anchor".equals(lt) || layoutConstraint != null ) {
                    	String gbcCode = layoutDataWrapper.getJavaCode( null, true, jcm);
                    	if (!"".equals(gbcCode))
                    		code.append(", " + gbcCode);

                    }
                    code.append(");\n");
                    return code.toString();
                } else {
                    if (SWTUtils.usesSetControlCall(parent)) {
                        //don't use parentName, since we want the TabItem name,
                        // say, not the TabFolder name
                        return parent.getNameInCode() + ".setControl(" + getNameInCode() + ");\n";
                    }
                    if (parent.isA(ScrolledComposite.class)) {
                        return parentName + ".setContent(" + getNameInCode() + ");\n";
                    }
                }
            }
        }
        return "";
    }

    public boolean isMenuComponent() {
        String cn = getClassName();
        if (Menu.class.getName().equals(cn))
            return true;
        if (MenuItem.class.getName().equals(cn))
            return true;
        if (getParent() != null && getParent().equals(this)) {
            System.err
                    .println("isMenuComponent - PARENT IS OWN CHILD: " + this);
            return false;
        }
        if (isSwing()) {
            if (getParent() != null && getParent().isMenuComponent())
                return true;
            //above handles JSeparator case, plus most other cases
            if (isSubclassOf(JPopupMenu.class))
                return true;
            if (isSubclassOf(JMenuItem.class))
                return true;
            if (isSubclassOf(JMenuBar.class))
                return true;
        }
        return false;
    }

    public void doClean() {
//        System.out.println("CLEANING " + this);
        if (control != null) {
            if (control instanceof Widget)
                ((Widget) control).dispose();
            control = null;
        }
        if (nonVisualObject != null) {
            if (nonVisualObject instanceof Widget)
                ((Widget) nonVisualObject).dispose();
            nonVisualObject = null;
        }
        if (component != null) {
            if(component.getParent() != null)
                component.getParent().remove(component);
            component = null;
        }
        propNames = null;
        fieldNames = null;
        propertyDescriptors = null;
        //awtLayout = null;
        //swtLayout = null;
        //if (components != null) {
        //for (int i = 0; i < components.size(); i++) {
        //((FormComponent) components.elementAt(i)).doClean();
        //}
        //}
    }

    public void clearCopyInfo() {
        setCopiedTo(null);
        if (components != null) {
            for (int i = 0; i < components.size(); i++) {
                ((FormComponent) components.elementAt(i)).clearCopyInfo();
            }
        }
    }

    public void setClassType(Class cls) {
        if (ClassUtils.isSwing(cls))
            setClassType(FormComponent.TYPE_SWING);
        else if (ClassUtils.isSWT(cls))
            setClassType(FormComponent.TYPE_SWT);
        else if (ClassUtils.isCWT(cls))
            setClassType(FormComponent.TYPE_CWT);
        else if (ClassUtils.isGWT(cls))
            setClassType(FormComponent.TYPE_GWT);
        else if (editor.getHarness() != null)
            setClassType(editor.getHarness().getClassID());
       else
           setClassType(FormComponent.TYPE_NONE);
    }

    public int getClassType() {
        if (classType == 0)
            setClassType(getMainClass());
        return classType;
    }

    public void setClassType(int type) {
        classType = type;
    }

    public boolean isSwing() {
        return classType == TYPE_SWING;
    }

    public boolean isGWT() {
        return classType == TYPE_GWT;
    }

    public boolean isCWT() {
        return classType == TYPE_CWT;
    }

    public boolean isCWTScreen() {
        return isCWT() && "Screen".equals(getShortClassName());
    }

    public boolean isCWTApplication() {
        return isCWT() && "Application".equals(getShortClassName());
    }

    public boolean isSWT() {
        return classType == TYPE_SWT;
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }

    public boolean isDisposed() {
        return disposed;
    }

    public boolean isControlDisposed() {
        if (control instanceof Widget) {
            return ((Widget) control).isDisposed();
        }
        return true;
    }

    /**
     * Returns false for children (and children of children) of TabItems since
     * when a TabItem is disposed it attempts to dispose all its children,
     * whether they have been disposed or not. Therefore, we don't dispose
     * children of TabItems individually but let theTabItem do its work.
     */
    private boolean canDisposeForRebuild() {
        FormComponent par = getParent();
        while (par != null) {
            if (par.getControl() instanceof IWidgetManager) {
                return true;
            }
            if (par.isSubclassOf(TabItem.class))
                return false;
            par = par.getParent();
        }
        return true;
    }

    public void dispose() {
        dispose(false);
    }

    public void dispose(boolean complete) {
        if (disposed)
            return;
        disposed = true;
        if(editorMenu != null)
        	editorMenu.dispose();
        
        try {
//            FormEditor.mark(false, "dispose " + this+", "+control+", "+complete);

            for (int i = 0; i < getChildCount(); i++) {
                if (!getChildAt(i).isDisposed())
                    getChildAt(i).dispose(complete);
            }

            if(editor != null)
                getEditor().unselectComponent(this, true);
            
            if(!isAWTControl()) { //don't dispose AWTControl's control
                if (complete || (!isSwing() && !isAWTControl())) {
                    if (control instanceof Control 
                            && !control.equals(getEditor().getRootDecoration(false))) {
                        try {
                            if (canDisposeForRebuild()
                                    && !((Control) control).isDisposed())
                                ((Control) control).dispose();
                        } catch (Exception e) {
                            jiglooPlugin.handleError(e, "Control " + getControl()
                                    + ", " + this
                                    + " (or child) is already disposed");
                        }
                    }
                    control = null;
                }
            } else {
            	if(complete)
            		control = null;
            }
            
            if (isSwing()) {
                if (component != null && component.getParent() != null)
                    component.getParent().remove(component);
                if(component instanceof JComponent) {
                	//helps get rid of GroupLayout
                	((JComponent)component).setLayout(null);
                }
            }

            component = null;
            rawComponent = null; //v4.0.0
            
            mainClass = null;
            
            if(nonVisualObject instanceof FormComponent)
            	((FormComponent)nonVisualObject).dispose(complete);
            nonVisualObject = null;

            if (complete) {

            	if (layoutDataWrapper != null)
            		layoutDataWrapper.dispose();

            	if (layoutWrapper != null)
            		layoutWrapper.dispose();

            	if(advPropSrc != null)
            		advPropSrc.dispose();
            	advPropSrc = null;
            	
            	if(basicPropSrc != null)
            		basicPropSrc.dispose();
            	basicPropSrc = null;
            	
            	if(layoutPropSrc != null)
            		layoutPropSrc.dispose();
            	layoutPropSrc = null;
            	
            	if(eventWrapper != null)
            		eventWrapper.dispose();
            	eventWrapper = null;
            	
                constructor = null;
                constructorParams = null;
                
                cHolder = null;
                
                if (properties != null)
                    properties.clear();
                properties = null;
                
                if (defaultProps != null)
                    defaultProps.clear();
                defaultProps = null;
                //if (propNames != null)
                //propNames.clear();
                propNames = null;
                fieldNames = null;
                if (setProps != null)
                    setProps.clear();
//                setProps = null;
                if (components != null)
                    components.clear();
                components = null;
                parent = null;
                copiedTo = null;
                copiedFrom = null;
                propertyDescriptors = null;
                editor = null;
                if(treeObject != null)
                	treeObject.releaseFormComponent();
                treeObject = null;
                layoutDataWrapper = null;
                layoutWrapper = null;
                layoutSelListener = null;
                node = null;
                oldLDW = null;
                oldLW = null;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void disposeSWT() {
        if (disposed)
            return;
        disposed = true;
        try {
            for (int i = 0; i < getChildCount(); i++) {
                if (!getChildAt(i).isDisposed())
                    getChildAt(i).disposeSWT();
            }

            if(editor != null)
                getEditor().unselectComponent(this, true);
            
            if (control instanceof Control 
                    && !control.equals(getEditor().getRootDecoration(false))
                    ) {
                try {
                    if (canDisposeForRebuild()
                            && !((Control) control).isDisposed())
                        ((Control) control).dispose();
                } catch (Exception e) {
                    jiglooPlugin.handleError(e, "Control " + getControl()
                            + ", " + this
                            + " (or child) is already disposed");
                }
                control = null;
            }
            
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public boolean usesAbsoluteTypeLayout() {
        String lt = getLayoutType();
        return 
        ( isSWT() && ( "Form".equals(lt) || lt == null)) 
		|| "Enfin".equals(lt)
		|| "Anchor".equals(lt)
        || "Absolute".equals(lt)
        || XYLayoutHandler.handlesLayout(this);
    }

    public boolean canSetLayout() {
        if (isSwing())
            return true;
        return usesLayout();
    }

    public boolean usesLayout() {
        if (usesCustomLayout())
            return false;
        if (isSwing()) {
            if (isMenuComponent())
                return false;
            if (isSubclassOf(java.awt.Canvas.class))
                return false;
            return true;
        }
        if (isSubclassOf(Dialog.class))
            return true;
        if (!isSubclassOf(Composite.class))
            return false;
        if (isSubclassOf(CCombo.class))
            return false;
        if (getTranslatedClassName().equals(SashForm.class.getName()))
            return false;
        return true;
    }

    public boolean needsLayout() {
        if (isSwing())
            return false;
        if (isA(Composite.class) || isA(OrderableComposite.class))
            return true;
        if (isA(Group.class) || isA(OrderableGroup.class))
            return true;
        if (isA(Group.class) || isA(OrderableGroup.class))
            return true;
        if (isA(Group.class) || isA(OrderableGroup.class))
            return true;
        if (isA(Group.class) || isA(OrderableGroup.class))
            return true;
        if (isA(Group.class) || isA(OrderableGroup.class))
            return true;
        return false;
    }

    public boolean usesCustomLayout() {
        if (isImportedBean)
            return true;
        if (layoutWrapper == null)
            getLayoutWrapper();
        return !layoutWrapper.isSet() && layoutWrapper.isCustom();
    }

    public String getParentSuperLayoutType() {
        if (getParent() == null)
            return null;
        return getParent().getSuperLayoutType();
    }

    public String getSuperLayoutType() {
        return getLayoutWrapper().getSuperLayoutType();
    }

    public String getParentLayoutType() {
        if (getParent() == null)
            return null;
        return getParent().getLayoutType();
    }

    public String getLayoutType() {
        return getLayoutWrapper().getLayoutType();
    }

    public void setEventWrapper(EventWrapper ew) {
        eventWrapper = ew;
    }

    public EventWrapper getEventWrapper() {
        return getEventWrapper(true);
    }

    public EventWrapper getEventWrapper(boolean createIfNeeded) {
        if (eventWrapper == null && createIfNeeded)
            eventWrapper = new EventWrapper(this, (Class) null);
        return eventWrapper;
    }

    public LayoutDataWrapper getLayoutDataWrapper(boolean createIfNull) {
    	if (layoutDataWrapper == null && !createIfNull)
    		return null;
    	return getLayoutDataWrapper();
    }

    public Object getLayoutProperty(String propName) {
        return getLayoutWrapper().getPropertyValue(propName);
    }
    
    public Object getLayoutDataProperty(String propName) {
        return getLayoutDataWrapper().getPropertyValue(propName);
    }
    
    public LayoutDataWrapper getLayoutDataWrapper() {
        if (layoutDataWrapper == null) {
            layoutDataWrapper = new LayoutDataWrapper(this);
            setOldLDW();
        }
                
        if (oldLDW == null)
            setOldLDW();
        layoutDataWrapper.setFormComponentSimple(this);
        return layoutDataWrapper;
    }

    public LayoutWrapper getLayoutWrapper() {
        if (layoutWrapper == null) {
            layoutWrapper = new LayoutWrapper(this);
            setOldLW();
        }
        
        if (oldLW == null)
            setOldLW();
//        System.err.println("GET LW " + layoutWrapper.getName() + 
//                ", " +layoutWrapper+", "+ this);
        layoutWrapper.setFormComponent(this);
        return layoutWrapper;
    }

    private LayoutPropertySource layoutPropSrc = null;
    
    private ISelectionListener layoutSelListener = null;
    
    /**
     * This is called from NewComponentDialog, to register the 
     * LayoutPropertySheetPage to get selection events to update
     * the layout when it is set
     */
    public void setSelectionListener(ISelectionListener layoutSelListener) {
        this.layoutSelListener = layoutSelListener;
    }

    public LayoutPropertySource getLayoutPropertySource() {
        if (layoutPropSrc == null)
            layoutPropSrc = new LayoutPropertySource(this);
        return layoutPropSrc;
    }

    public void executeSetLayoutWrapper(LayoutWrapper lw) {
        Object newVal = lw;
        if (oldLW != null && oldLW.equals(newVal) && !lw.wasChanged()) {
            return;
        }
//        lw.setName(oldLW.getName());
        lw.setChanged(false);
//        System.err.println("EXEC SET LAYOUT WRAPPER " + layoutWrapper + ", "
//                + layoutWrapper.getName() + ", " + this + ", " + lw.isSet());
//        new Exception().printStackTrace();

//        if(layoutSelListener != null) {
//            setLayoutWrapper(lw);
//            layoutSelListener.selectionChanged(getEditor(), new StructuredSelection(this));
//            return;
//        }
        Vector ldws = new Vector();
        Vector bounds = new Vector();
        for (int i = 0; i < getChildCount(); i++) {
            FormComponent fc = getChildAt(i);
            ldws.add(fc.getLayoutDataWrapper().getCopy(fc));
            bounds.add(fc.getBoundsCopy());
        }
        UndoableLayoutAction upa = new UndoableLayoutAction(this, oldLW
                .getCopy(this), newVal, ldws, bounds, true);
        getEditor().executeUndoableAction(upa);
    }

    public void executeSetLayoutDataWrapper(LayoutDataWrapper lw) {
        Object newVal = lw;
        if (oldLDW != null && oldLDW.equals(newVal)) {
            return;
        }
        UndoableLayoutDataAction upa = new UndoableLayoutDataAction(this,
                oldLDW.getCopy(this), newVal, true);
//            upa.redo();
//            getEditor().setDirtyAndUpdate();
        getEditor().executeUndoableAction(upa);
    }

    public void executeSetLayoutDataWrapperNow(LayoutDataWrapper lw) {
        Object newVal = lw;
        if (oldLDW != null && oldLDW.equals(newVal)) {
            return;
        }
        getEditor().executeUndoableActionNow(
                new UndoableLayoutDataAction(this, oldLDW.getCopy(this), newVal, true));
    }

    public boolean getBooleanPropertyValue(String propName) {
        return Boolean.TRUE.equals(getPropertyValue(propName));
    }

    public int getIntPropertyValue(String propName) {
        Object prop = getPropertyValue(propName);
        if (prop == null)
            return -1;
        if (prop instanceof Integer)
            return ((Integer) prop).intValue();
        if (prop instanceof FieldWrapper)
            return ((Integer) ((FieldWrapper) prop).getValue()).intValue();
        return -1;
    }

    public boolean getBooleanLayoutDataProp(String propName) {
        if (layoutDataWrapper == null)
            return false;
        return Boolean.TRUE
                .equals(layoutDataWrapper.getPropertyValue(propName));
    }

    public void setLayoutWrapper(LayoutWrapper lw) {
        if (lw != null) {
            lw.setFormComponent(this);
            if (layoutWrapper != null) {
                layoutWrapper.setSet(lw.isSet());
            }
        }
        if (isJMenuBar())
            return;
        if (oldLW != null && oldLW.equals(lw)) {
            //System.out.println("OLD LW = NEW LW");
            return;
        }
        String oldType = "";
        if(oldLW != null)
            oldType = oldLW.getLayoutType();
        String newType = "";
        if(lw != null)
            newType = lw.getLayoutType();
//        System.out.println("SET LW, OLDTYPE ="+oldType+", NEW TYPE="+newType);
        if("Group".equals(newType) || isSWT())
            setLayoutWrapper(lw, false, oldType == null || !oldType.equals(newType));
        else
            setLayoutWrapper(lw, true);
        if(layoutSelListener != null) {
            layoutSelListener.selectionChanged(getEditor(), new StructuredSelection(this));
        }
    }

	private void initGridBagDimensions() {
        if (!isSwing())
            return;
        LayoutWrapper lw = getLayoutWrapper();
        if (!"GridBag".equals(getLayoutType()))
            return;
        Container ctnr = (Container) component;
        
        LayoutManager lm = lw.getSwingLayout(ctnr);
        GridBagLayout gbl = (GridBagLayout) lm;

        try {
            ctnr.invalidate();
            ctnr.validate();
            gbl.layoutContainer(ctnr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] dims = gbl.getLayoutDimensions();
        int rows = dims[1].length;
        int cols = dims[0].length;
        
        if(cols > 0) // && !lw.isPropertySet("columnWidths") && !lw.isPropertySet("columnWeights"))
            lw.setGridBagDimensions(cols, true, true);
        
        if(rows != 0) // && !lw.isPropertySet("rowHeights") && !lw.isPropertySet("rowWeights"))
            lw.setGridBagDimensions(rows, true, false);
        
        try {
            ctnr.invalidate();
            ctnr.validate();
            gbl.layoutContainer(ctnr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	private void initGridBagDimensions(boolean setIfNeeded) {
//        if (!isSwing())
//            return;
//        LayoutWrapper lw = getLayoutWrapper();
//        if (!"GridBag".equals(getLayoutType()))
//            return;
//        Container ctnr = (Container) component;
//        
//        ctnr = getContentPane(ctnr);
//        
//        LayoutManager lm = lw.getSwingLayout(ctnr);
//        GridBagLayout gbl = (GridBagLayout) lm;
//
//        try {
//            ctnr.invalidate();
//            ctnr.validate();
//            gbl.layoutContainer(ctnr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int[][] dims = gbl.getLayoutDimensions();
//        int rows = dims[1].length;
//        int cols = dims[0].length;
//
//        int lwCols = cols;
//        int lwRows = rows;
//
//        Integer colInt = (Integer) lw.getPropertyValue("columns");
//        if (lw.isPropertySet("columns") && colInt.intValue() > 0) {
//            lwCols = colInt.intValue();
//        }
//        Integer rowInt = (Integer) lw.getPropertyValue("rows");
//        if (lw.isPropertySet("rows") && rowInt.intValue() > 0) {
//            lwRows = rowInt.intValue();
//        }
//
////        if (lwRows < rows)
////        lwRows = rows;
////        if (lwCols < cols)
////        lwCols = cols;
//
//        if (lwRows == 0) {
//            lwRows = 4;
//        }
//        if (lwCols == 0) {
//            lwCols = 4;
//        }
//
//        //		System.out.println(
//        //			"INIT GBD (2) "
//        //				+ lwRows
//        //				+ ", "
//        //				+ lwCols
//        //				+ ", "
//        //				+ ctnr.getComponentCount()
//        //				+ ", "
//        //				+ this);
//        
//        if(setIfNeeded) {
//            if(!lw.isPropertySet("columnWidths") && !lw.isPropertySet("columnWeights"))
//                lw.setGridBagDimensions(lwCols, true, true);
//            
//            if(!lw.isPropertySet("rowHeights") && !lw.isPropertySet("rowWeights"))
//                lw.setGridBagDimensions(lwRows, true, false);
//        } else {
//            lw.setPropertyValue("columns", new Integer(lwCols), false, true);
//            lw.setPropertyValue("rows", new Integer(lwRows), false, true);
//        }
//        
//        //if (!lw.isPropertySet("columns") || lwCols != cols) {
//        //lw.setPropertyValue("columns", new Integer(cols));
//        //}
//        //if (!lw.isPropertySet("rows") || lwRows != rows) {
//        //lw.setPropertyValue("rows", new Integer(rows));
//        //}
//        try {
//            ctnr.invalidate();
//            ctnr.validate();
//            gbl.layoutContainer(ctnr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

	public Throwable getLastException() {
		if(lastException == null)
			return null;
		Throwable tmp = lastException;
		lastException = null;
		return tmp;
	}
	
	private void setLastException(Throwable e) {
		lastException = e;
	}
	
    public void setLayoutWrapper(LayoutWrapper lw, 
            boolean wrapperChanged) {
        setLayoutWrapper(lw, wrapperChanged, true);
    }
        
    public void setLayoutWrapper(LayoutWrapper lw, boolean wrapperChanged,
			boolean typeChanged) {

    	Vector gridBounds = null;
    	if(usesGridTypeLayout()) {
    		gridBounds = new Vector();
    		for (int i = 0; i < getChildCount(); i++) {
    			FormComponent fc = (FormComponent) getChildAt(i);
    			int gridX = fc.getGridValue(true);
    			int gridY = fc.getGridValue(false);
    			int gridW = fc.getGridCellValue(true);
    			int gridH = fc.getGridCellValue(false);
    			gridBounds.add(new int[] {gridX, gridY, gridW, gridH});
    		}
    	}
		
		layoutWrapper = lw;
		setPropertyValueCode("layout", null);

		setOldLW();

		setEditorReload(true);

		if (lw != null)
			lw.setFormComponent(this);

		if (lw == null || !lw.isSet())
			return;
		Object layout = null;
		if (isSwing()) {
			if (component == null)
				return;
			Container ctnr = (Container) component;
	        ctnr = SwingHelper.getContentPane(ctnr);
			Object newLayout = lw.getSwingLayout(ctnr);
			if (newLayout instanceof AbsoluteLayout) {
				newLayout = null;
			}
			layout = newLayout;
			LayoutManager lm = (LayoutManager) layout;
			try {
			    if(lm instanceof GroupLayout 
			            && !ctnr.equals(((GroupLayout)lm).getContainer())) {
			        System.out.println("setLayout - GroupLayout assigned to a different container!");
			    }
				ctnr.setLayout(lm);
			} catch (Throwable e) {
				setLastException(e);
				System.err.println("FormComponent.setLayout: Error setting layout "+ lm+", "+e);
				return;
			}

			LayoutGroup hg = lw.getHGroup(), vg = lw.getVGroup();
			
			if (typeChanged) {

			    //hide if not or right type GridEdgeMarkers
			    if(getEditor().getAWTControl() != null)
			        getEditor().getAWTControl().handleSelectionChanged(this);

			    if(editor != null)
			    	editor.getGridEdgeManager().showGridEdgeMarkers(this);
			    
			    if (newLayout instanceof GroupLayout) {
			        hg = new LayoutGroup("createParallelGroup", null, lw, false);
			        lw.setHGroup(hg);
			        vg = new LayoutGroup("createParallelGroup", null, lw, true);
			        lw.setVGroup(vg);
			    }
			    
				Vector comps = getChildren();
				if (comps.size() > 0) {
					//only do this for containers designed with Jigloo (ie, comps.size>0)
					//since some components might be custom classes
					ctnr.removeAll();
				}
				//remove and add back in all comps
				//use layoutConstraint, if it is non-null

				for (int i = 0; i < comps.size(); i++) {
					int gridX = -1;
					int gridY = -1;
					int gridW = -1;
					int gridH = -1;
					if(gridBounds != null) {
						int[] bounds = (int[]) gridBounds.get(i);
						gridX = bounds[0];
						gridY = bounds[1];
						gridW = bounds[2];
						gridH = bounds[3];
					}
					FormComponent fc = (FormComponent) comps.elementAt(i);
					//inherited FCs may have null components (eg,
					// nextFocusableComponent )
					if (fc.getComponent() == null || fc.isJPopupMenu())
						continue;
					
					LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
					Object lcon = null;
					
					if (lm instanceof CardLayout) {
						lcon = ldw.getLayoutData();
						if (lcon == null || !(lcon instanceof String)) {
							lcon = fc.getName();
							ldw.setObject(lcon);
						}
					} else if (lm == null) {
					    //"Absolute" layout
						fc.setSetProperty("bounds");

					} else if (lm.getClass().getName().equals("de.gebit.s2j.smalltalk.gui.EnfinLayout")) {
					    //"Enfin" layout
						fc.setSetProperty("bounds");

					} else if (lm instanceof FlowLayout
							|| lm instanceof BoxLayout
							|| lm instanceof java.awt.GridLayout) {
						fc.unsetPropertyValue("bounds");
						fc.getLayoutDataWrapper().setObject(null);

					} else if (lm instanceof BorderLayout) {
						lcon = ldw.getLayoutData();
						if (lcon == null
								|| !(lcon.equals(BorderLayout.NORTH)
										|| lcon.equals(BorderLayout.SOUTH)
										|| lcon.equals(BorderLayout.EAST)
										|| lcon.equals(BorderLayout.WEST) || lcon
										.equals(BorderLayout.CENTER))) {
							if (i == 0) {
								lcon = BorderLayout.CENTER;
							} else if (i == 1) {
								lcon = BorderLayout.NORTH;
							} else if (i == 2) {
								lcon = BorderLayout.WEST;
							} else if (i == 3) {
								lcon = BorderLayout.EAST;
							} else {
								lcon = BorderLayout.SOUTH;
							}
							fc.getLayoutDataWrapper().setObject(null);
							fc.setLayoutData(lcon);
							//fc.setLayoutData(null);
						}
						//                        fc.resetPropertyValue("preferredSize");
						fc.unsetPropertyValue("bounds");

					} else if (lm instanceof TableLayout) {
						lcon = ldw.getLayoutData();
						if (!TableLayoutHandler.isValidTableConstraint(lcon)) {
							if (gridX != -1) {
								lcon = ""+(gridX-1)+", "+(gridY-1)+" "+gridW+" "+gridH;
							} else {
								lcon = ""+(i%4)+", "+(i/4);
							}
							fc.getLayoutDataWrapper().setObject(null);
							fc.setLayoutData(lcon);
						}
						fc.unsetPropertyValue("bounds");

					} else if (lm instanceof GridBagLayout) {
						lcon = ldw.getLayoutData();
						if (!(lcon instanceof GridBagConstraints)) {
							lcon = new GridBagConstraints();
							ldw.setObject(lcon);
							if (gridX != -1) {
								if (gridX >= 0)
									ldw.setPropertyValue("gridx", new Integer(gridX));
								if (gridY >= 0)
									ldw.setPropertyValue("gridy", new Integer(gridY));
								if (gridW > 0)
									ldw.setPropertyValue("gridwidth", new Integer(gridW));
								if (gridH > 0)
									ldw.setPropertyValue("gridheight", new Integer(gridH));
							} else {
								prepNextGridXY(ldw, null);
							}
						}
						//fc.resetPropertyValue("preferredSize", false);
						fc.unsetPropertyValue("bounds");

					} else if (lm instanceof com.jgoodies.forms.layout.FormLayout) {
						lcon = ldw.getLayoutData();
						if (!(lcon instanceof CellConstraints)) {
							lcon = new CellConstraints();
							ldw.setObject(lcon);
							if (gridX != -1) {
								if (gridX >= 0)
									ldw.setPropertyValue("gridX", new Integer(gridX));
								if (gridY >= 0)
									ldw.setPropertyValue("gridY", new Integer(gridY));
								if (gridW > 0)
									ldw.setPropertyValue("gridWidth", new Integer(gridW));
								if (gridH > 0)
									ldw.setPropertyValue("gridHeight", new Integer(gridH));
							} else {
								prepNextGridXY(ldw, null);
							}
							lcon = validateLayoutConstraint(lcon, "Form", fc);
						}
						//fc.resetPropertyValue("preferredSize", false);
						fc.unsetPropertyValue("bounds");

					} else if (lm instanceof AnchorLayout) {
						//lcon = fc.getLayoutData();
						lcon = fc.getLayoutDataWrapper().getLayoutData();
						if (!(lcon instanceof AnchorConstraint)) {
							Rectangle b = fc.getBounds();
							lcon = new AnchorConstraint();
							ldw.setObject(lcon);
							//need to actually set the bounds so the AnchorConstraints
							//are initialized correctly
							fc.setPropertyValueDirect("bounds", b);
						}

					} else if (lm instanceof GroupLayout) {
					    
						fc.getLayoutDataWrapper().setObject(null);

					} else if(PaneLayoutHandler.handlesLayout(this)) {
					    if(ldw != null)
					        lcon = ldw.getLayoutData();
					    fc.unsetPropertyValue("bounds");
					    
					} else if(MigLayoutHandler.handlesLayout(this)) {
						if(gridX != -1) {
							lcon = "cell "+(gridX-1)+" "+(gridY-1)+" "+gridW+" "+gridH;
						} else {
							lcon = "cell "+(i%4)+" "+(i/4);
						}
					    if(ldw != null) {
							if(gridX != -1) {
								lcon = "cell "+(gridX-1)+" "+(gridY-1)+" "+gridW+" "+gridH;
							} else {
								lcon = "cell "+(i%4)+" "+(i/4);
							}
					    	ldw.setObject(lcon);
					    }
					    fc.unsetPropertyValue("bounds");
					    
					} else {
					    ldw.setObject(null);
					    fc.unsetPropertyValue("bounds");
					}

					fc.setNeedsUpdateInCode("bounds");

					if (lcon != null) {
						addComponent(fc.getComponent(), component, lcon, fc);
					} else {
						addComponent(fc.getComponent());
					}
				}
			}

			if (typeChanged && hg != null && lm instanceof GroupLayout) {
			    try {
			        LayoutGroup lg = lw.getHGroup().getCompactGroup(components, true, null, null, null);
			        lw.setHGroup(lg);
			        lg = lw.getVGroup().getCompactGroup(components, true, null, null, null);
			        lw.setVGroup(lg);
			    } catch(Throwable t) {
			        System.err.println("Error in setLayoutWrapper "+t);
			    }
			}
			
			//needed when setting addContainerGaps etc (properties of GroupLayout
			// - should be a better way?
			if(hg != null) {
			    layoutWrapper.refreshGroupLayout();
//			    populateGroupLayout();
			}
			
			try {
				component.validate();
			} catch (Throwable t) {
				System.err.println("Error validating layout for " + this + ", "+ t);
			}

		} else {
			if (control == null)
				return;
			layout = lw.getSWTLayout();
			if (control instanceof Composite) {
				((Composite) control).setLayout((Layout) layout);
				if (typeChanged) {
					Vector subs = getChildren();
					Vector bv = null;
					boolean setBounds = (layout instanceof FormLayout || layout == null);
					if (setBounds)
						bv = new Vector();
					for (int i = 0; i < subs.size(); i++) {
						FormComponent fc = getChildAt(i);
						fc.setSWTLayoutInfo(false, true);
						if (setBounds)
							bv.add(fc.getBoundsCopy());
					}
					if (setBounds) {
						for (int i = 0; i < subs.size(); i++) {
							FormComponent fc = getChildAt(i);
							fc.setPropertyValue("bounds", (Rectangle) bv.elementAt(i), true);
//							fc.setBounds((Rectangle) bv.elementAt(i), false);
						}
					}
					try {
						fixLayoutChildData(this);
						((Composite) control).layout();
					} catch (Exception e) {
						System.err.println("[setLayoutWrapper] Error setting layout "+ layout + " for " + this);
					}
				}
			}
		}
		if (typeChanged && layoutWrapper != null && layoutWrapper.isSet())
			layoutWrapper.initProperties();

		if (editor != null && !getEditor().isParsing()) {
			updateUI();
			notifyListeners(true, wrapperChanged);
		}
	}
        
    public Point getSizeFromBounds() {
        Rectangle b = getBounds(false, false);
        if (b != null) {
            Point sz = new Point(b.width, b.height);
            return sz;
        }
        return getSize();
    }

    public Point getSize() {
        try {
            String id = "size";
            if (isSwing() && propNames.contains("preferredSize"))
                id = "preferredSize";
            if (isJInternalFrame())
                id = "bounds";

            if (!isPropertySet(id))
                id = "bounds";

            if (isPropertySet("size"))
                id = "size";
            else if (isPropertySet("preferredSize"))
                id = "preferredSize";
            else
                id = "bounds";

            Object val;
            val = getPropertyValue(id);
            if (val instanceof FormComponent) {
                //val = ((FormComponent)val).getObject(false);
                val = convertToRawSWTProperty(val);
            }

            if (val instanceof SizePropertySource) {
                SizePropertySource sps = (SizePropertySource) val;
                return sps.getValue();
            }
            if (val instanceof RectanglePropertySource) {
                RectanglePropertySource rps = (RectanglePropertySource) val;
                Rectangle rec = rps.getValue();
                return new Point(rec.width, rec.height);
            }
            if (val instanceof Rectangle) {
                Rectangle rec = (Rectangle)val;
                return new Point(rec.width, rec.height);
            }
            if (val == null) {
                //Dialog doesn't have a size property
                //if(control != null) return ((Control)control).getSize();
                val = new Point(80, 80);
                //setPropertyValue(id, val);
            }
            if (!(val instanceof Point)) {
                System.err.println("GET SIZE " + this + " val is not Point - "
                        + val + ", " + val.getClass());
                return new Point(80, 80);
            }
            return (Point) val;
        } catch (Exception e) {
            System.err.println("Error in getSize for " + this);
            e.printStackTrace();
            return new Point(80, 80);
        }
    }

    //	public Point getActualSize() {
    //		Rectangle b = getBounds();
    //		Point p = new Point(b.width, b.height);
    //		//putProperty("size", p);
    //		return p;
    //	}

    public boolean isDragReorderable() {
        return isSubclassOf(SashForm.class) || isJSplitPane() || isJToolBar()
//		|| isAbstractFormBuilder()
                || getLayoutWrapper().isReorderable();
    }

    public boolean isJFaceApplicationWindow() {
        return isSubclassOf(ApplicationWindow.class);
    }
        
    public boolean isJFaceDialog() {
        return isSubclassOf(org.eclipse.jface.dialogs.Dialog.class);
    }
        
    public boolean isJFaceViewer() {
        return isSubclassOf(Viewer.class);
    }
        
    public boolean isJFaceWizardDialog() {
        return isSubclassOf(WizardDialog.class);
    }
        
    public boolean isJInternalFrame() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JInternalFrame.class, getMainClass());
    }

    public boolean isSingleFrameApplication() {
        return AppUtils.JAVAX_SWING_SFAPP_NAME.equals(getMainClass().getName());
    }

    public boolean isJFrame() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JFrame.class, getMainClass());
    }

    public boolean isJPanel() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JPanel.class, getMainClass());
    }

    public boolean isAbstractFormBuilder() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(AbstractFormBuilder.class, getMainClass());
    }

    public boolean isJSplitPane() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JSplitPane.class, getMainClass());
    }

    public boolean isJScrollPane() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JScrollPane.class, getMainClass());
    }

    public boolean isJToolBar() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JToolBar.class, getMainClass());
    }

    public boolean isJTabbedPane() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JTabbedPane.class, getMainClass());
    }

    public boolean isJPopupMenu() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JPopupMenu.class, getMainClass());
    }

    public boolean isJApplet() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JApplet.class, getMainClass());
    }

    public boolean isButtonGroup() {
        if (!jiglooPlugin.canUseSwing())
            return false;
        Class mc = getMainClass();
        Class bgc = ButtonGroup.class;
        return bgc.equals(mc) ||  Cacher.isAssignableFrom(bgc, mc);
    }

    public boolean isJDialog() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JDialog.class, getMainClass());
    }

    public boolean isJWindow() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JWindow.class, getMainClass());
    }

    public boolean isJMenuBar() {
        if (!isSwing())
            return false;
        return  Cacher.isAssignableFrom(JMenuBar.class, getMainClass());
    }

    public boolean hasAncestor(Vector comps) {
        for (int i = 0; i < comps.size(); i++) {
            FormComponent fc = (FormComponent) comps.elementAt(i);
            if (isChildOf(fc))
                return true;
        }
        return false;
    }

    public boolean isChildOf(FormComponent comp) {
        FormComponent par = getParent();
        while (par != null) {
            if (par.equals(comp))
                return true;
            if (par.equals(par.getParent()))
                return true;
            par = par.getParent();
        }
        return false;
    }

    public boolean hasAncestor(Class compClass) {
        FormComponent par = getParent();
        while (par != null) {
            if (par.isSubclassOf(compClass))
                return true;
            par = par.getParent();
        }
        return false;
    }

    public Rectangle getBoundsCopy() {
        Rectangle b = getBounds(false, true);
        //Rectangle b = getBounds(true, true);
        return new Rectangle(b.x, b.y, b.width, b.height);
    }

    public void resetUnsetPropertiesForAll() {
        resetUnsetProperties();
        if (components == null)
            return;
        for (int i = 0; i < components.size(); i++) {
            FormComponent fc = getChildAt(i);
            if (this.equals(fc)) {
                System.err
                        .println("resetUnsetPropertiesForAll: PARENT IS OWN CHILD "
                                + this);
                components.remove(fc);
                i--;
                continue;
            }
//            if (!fc.isInherited())
                fc.resetUnsetPropertiesForAll();

        }
    }

    public void resetUnsetProperties() {
                
        //if (true) return;
        try {
            if (isNonVisualRoot() || isExtraCompRoot())
                return;
            if(layoutWrapper != null && layoutWrapper.wasSet() && !layoutWrapper.isSet()) {
                resetLayoutWrapper();
            }
//            if(isInherited())
//                return;
            
            if (properties == null || oldSetProps == null || propNames == null)
                return;
            for (int i = 0; i < propNames.size(); i++) {
                String pName = (String) propNames.elementAt(i);
//                System.out.println("RESET UNSET PROPS " + this + ", " + pName
//                    + ", set=" + isPropertySet(pName) + ", "+setProps.contains(pName)+
//                    ", was set="
//                    + oldSetProps.contains(pName));
                if (!isPropertySet(pName)) {
                    if (oldSetProps.contains(pName)) {
                    	//if you reset "label" then you also reset "text" temporarily,
                    	//so if "text" is set, don't reset "label" - for a JButton, JLabel etc
                    	if(pName.equals("label") && isPropertySet("text"))
                    		continue;
                    	
                        if(!pName.equals("layoutData") || !(isVirtual() || isDialogButton()))
                            resetPropertyValue(pName, false);
                    }
                }
            }
            if (!isVirtual() && !isDialogButton() && parent != null && !isSwing() 
                    && layoutDataWrapper == null
                    && control instanceof Control
					&& !isInherited()) {
//                System.out.println("RESET LAYOUT DATA "+this);
                setControlLayoutData(null);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error in resetUnsetProperties for "
                    + this);
        }
    }

    private Vector oldSetProps;

    public int getChildCountInCode() {
        return childCountInCode;
    }
    
    public void invalidateAll() {
        
        setExistsInCode(false);
        setAssigned(false);
        methodCalls.clear();
        if(builder != null) {
            if(component != null && component.getParent() != null)
                component.getParent().remove(component);
            component = null; //v3.8.1 - set component *and* builder to null if builder was not null
            builder = null;
        }
        if(usesGroupLayout()) {
            layoutWrapper = null;
            if(component != null && component.getParent() != null)
                component.getParent().remove(component);
            component = null;
        }
        
        if(!isVirtual() && !isDialogButton())
            layoutDataWrapper = null;
        
        if(layoutWrapper != null) {
            layoutWrapper.setWasSet(layoutWrapper.isSet());
            layoutWrapper.setSet(false);
        }
        
        /*
         * if (layoutWrapper != null) { Object lm =
         * DefaultValueManager.getDefault("layout", mainClass);
         * //System.out.println("GOT DEFAULT LAYOUT "+lm+" for "+this); if (lm
         * instanceof LayoutManager) { LayoutWrapper lw = new
         * LayoutWrapper(this, lm.getClass(), isSwing()); lw.setSet(false);
         * setLayoutWrapper(lw); } }
         */
        if (extraPropCode != null)
            extraPropCode.clear();
        childCountInCode = 0;
        
        if (components != null) {
            for (int i = 0; i < components.size(); i++) {
                FormComponent fc = getChildAt(i);
                if (//!fc.isInherited() && 
                        !fc.equals(this))
                    fc.invalidateAll();
            }
        }
        hasParentInCode = false;
    }

    public void setExistsInCode(boolean exists) {
        //System.out.println("SET EXISTS IN CODE "+exists+", "+this);
        existsInCode = exists;
    }

    public void setAllExistsInCode(boolean exists) {
        //System.out.println("SET EXISTS IN CODE "+exists+", "+this);
        existsInCode = exists;
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).setAllExistsInCode(exists);
    }

    public void clearDirtyPropertiesForAll() {
        propsNeedingCodeUpdate.clear();
        if (components == null)
            return;
        for (int i = 0; i < components.size(); i++) {
            FormComponent fc = getChildAt(i);
            if (!fc.isInherited() && !this.equals(fc))
                fc.clearDirtyPropertiesForAll();
        }
    }

    public void unsetPropertiesForAll() {
        unsetProperties();
        if (components == null)
            return;
        for (int i = 0; i < components.size(); i++) {
            FormComponent fc = getChildAt(i);
            if (
//            		!fc.isInherited() && 
            		!this.equals(fc))
                fc.unsetPropertiesForAll();
        }
    }
    
    public void unsetProperties() {
        if (properties == null)
            return;
       	oldSetProps = (Vector) setProps.clone();
        setProps.clear();
        if(propertyValueCode != null)
            propertyValueCode.clear();
    }

    public Rectangle getBounds() {
        return getBounds(true, true);
    }

    public Rectangle getBounds(boolean includeInBetweens, boolean zeroIfHidden) {
        return getBounds(includeInBetweens, zeroIfHidden, true);
    }

    public Rectangle getBounds(boolean includeInBetweens, boolean zeroIfHidden,
            boolean allowParentOfRoot) {

    	if(isSubclassOf(JPopupMenu.class)) {
    		if(getParent() == null)
    			return new Rectangle(-10, -10, 10, 10);
    		Rectangle pb = getParent().getBounds(includeInBetweens, zeroIfHidden, allowParentOfRoot);
    		return new Rectangle(pb.width/2, pb.height/2, 40, 10);
    	}
        Rectangle b = new Rectangle(-10, -10, 10, 10);
        if (isSWT() && isControlDisposed()) {
            if (control == null) {
                if (jiglooPlugin.DEBUG_EXTRA)
                    System.err.println("GET BOUNDS: CONTROL IS NULL " + this
                            + ", existsInCode=" + existsInCode);
            } else {
                    System.err.println("GET BOUNDS: IS DISPOSED " + this
                            + ", control=" + control + ", existsInCode="
                            + existsInCode);
            }
            return b;
        }
        
       if (isSwing()) {
			if(component == null) {
			    return b;
			}
            if (!isRoot() && zeroIfHidden && 
            		!component.isVisible() 
            ) {
                return b;
            }
            
            java.awt.Rectangle rec;
            if (allowParentOfRoot && canHaveJMenuBar() && !isJInternalFrame()
                    && component.getParent() != null) {
                rec = getComponent().getParent().getBounds();
            } else {
                rec = getComponent().getBounds();
            }

            b = new Rectangle(rec.x, rec.y, rec.width, rec.height);
            if (includeInBetweens) {
                if (getParent() != null && getParent().getComponent() instanceof Container) {
                    SwingHelper.addVectorBetween((Container) getParent().getComponent(),
                            getComponent(), b);
                } else if (isJMenuBar() && getRootComponent() != null
                        && getRootComponent().isJInternalFrame()) {
                    SwingHelper.addVectorBetween((Container) getRootComponent()
                            .getComponent(), getComponent(), b);
                }
            }
            
            if(isContentPane() && getParent() != null 
            		&& !getParent().isJInternalFrame()) {
            	b.x = b.y = 0;
            }
            
            return b;
        } else if (isCWT()) {
            if (isRoot()) {
                b = getEditor().getAWTControl().getBounds();
                b.x = 0;
                b.y = 0;
                //System.out.println("GOT CWT ROOT BOUNDS "+b);
            } else {
                b = com.cloudgarden.jigloo.typewise.TypewiseManager.getBounds(this);
            }
            return b;
        } else if (isSWT()) {
        	return SWTUtils.getSWTBounds(this, zeroIfHidden);
        }
        return b;
    }

    public Container getContentPane() {
        if (!usesContentPane())
            return null;
        
        if (isSubclassOf(JInternalFrame.class) && component instanceof JInternalFrame)
            return ((JInternalFrame) component).getContentPane();
        
        if(isJFrame() || isJApplet() || isJDialog() || isJWindow())
            return (Container) component;

        return SwingHelper.getContentPane(rawComponent != null ? rawComponent : component);
    }
    
    public FormComponent getContentPaneFC() {
        if (getChildCount() == 1 && getChildAt(0).isContentPane())
            return getChildAt(0);
        return this;
    }

    private Rectangle 
    boundsToRoot = null,
    boundsToViewpt = null;
    
    public void setBoundsToRoot(Rectangle bounds) {
        boundsToRoot = bounds;
    }
    
    public Rectangle getInsideBoundsRelativeToRoot() {
    	
    	Rectangle rb1;

        rb1 = getBoundsRelativeToRoot();
        
        Component cp = (Component)getContentPane();
        Component mtComp = (Component) getComponent();
        if(cp != null) {
            rb1.x += cp.getLocation().x;
            rb1.y += cp.getLocation().y;
        } else {
            cp = mtComp;
        }
        if(cp == null)
            return rb1;
        
        rb1 = new Rectangle(rb1.x, rb1.y, cp.getWidth(), cp.getHeight());
        if(mtComp instanceof Container)
            SwingHelper.addVectorBetween((Container) mtComp, cp, rb1);
        
        if(cp instanceof JComponent) {// && !usesJGFormLayout()) {
            Insets ins = ((Container) cp).getInsets();
            rb1.x += ins.left;
            rb1.y += ins.top;
            rb1.width -= (ins.left + ins.right);
            rb1.height -= (ins.top + ins.bottom);
        }
        return rb1;
    }
    
    public Rectangle getBoundsRelativeToRoot() {
        if(boundsToRoot != null)
            return (Rectangle) JiglooUtils.copy(boundsToRoot);
        
        if(editor == null) {
            System.err.println("EDITOR IS NULL FOR "+this+", copied from "+copiedFrom);
            editor = jiglooPlugin.getActiveEditor();
//            new Exception().printStackTrace();
        }
        Rectangle b = getBoundsRelativeTo(getEditor().getMainControl());
        boundsToRoot = b;
        return (Rectangle) JiglooUtils.copy(boundsToRoot);
    }
    
    public Rectangle getBoundsRelativeToViewport() {
    	
    	if(editorMenu != null 
    			&& getParent() != null 
    			&& !getParent().equals(editor.getMenuBar()) 
    			&& !isJMenuBar()
    			&& editorMenu.getBounds() != null) {
    		
    		if(isSubclassOf(JPopupMenu.class)) {
    			Rectangle pb = getParent().getBoundsRelativeToViewport();
    			Rectangle mb = editorMenu.getBounds();
    			return new Rectangle(pb.x+pb.width/2, pb.y+pb.height/2, mb.width, mb.height);
    		}
    		
    		Rectangle r = editorMenu.getBounds();
    		if(editorMenu.isInPopup()) {
    			return new Rectangle(r.x, r.y, r.width, r.height);
    		}
    		if(editor.getMenuBar() != null) {
    			Rectangle mbb = editor.getMenuBar().getBoundsRelativeToViewport();
    			return new Rectangle(mbb.x+r.x, mbb.y+r.y, r.width, r.height);
    		} else if(getRootComponent().isJMenuBar()) {
    			Rectangle mbb = getRootComponent().getBoundsRelativeToViewport();
    			return new Rectangle(mbb.x+r.x, mbb.y+r.y, r.width, r.height);
    		} else {
    			return new Rectangle(r.x, r.y, r.width, r.height);
    		}
    	}
    	
        if(boundsToViewpt != null)
            return (Rectangle) JiglooUtils.copy(boundsToViewpt);
        Rectangle b = getBoundsRelativeTo(getEditor().getViewportControl());
        boundsToViewpt = b;
        return (Rectangle) JiglooUtils.copy(boundsToViewpt);
    }

    public Insets getInsets() {
        if(component instanceof JComponent)
            return ((JComponent)component).getInsets();
        return new Insets(0,0,0,0);
    }
    
    public Rectangle getBoundsRelativeTo(Composite parent) {
        return getBoundsRelativeTo(parent, true);
    }

    public Rectangle getBoundsRelativeTo(Composite parent,
            boolean allowParentOfRoot) {
        return getBoundsRelativeTo(parent, true, allowParentOfRoot);
    }
    
    public void adjustBoundsForBorderedControls(Rectangle b) {
    	if(control instanceof Control) {
    		int bw = ((Control)control).getBorderWidth();
    		if(bw != 0) {
    			b.x -= bw;
    			b.y -= bw;
    		}
    	}
    }

    public Rectangle getBoundsRelativeTo(Composite parent,
            boolean zeroIfHidden,
            boolean allowParentOfRoot) {

        if(getEditor() == null)
            return new Rectangle(0,0,0,0);

        if(isRootShell() && getEditor().getRootDecoration() != null) {
//            System.out.println("This would give a bug in SWT Dialogs");
//        	return getEditor().getRootDecoration().getBounds();
        }
        
        if (parent == null) {
            return getBoundsRelativeToViewport();
        }
        
        if(isSWT()) {
            if(control instanceof Control) {
                Control c = (Control)control;
                if(c.isDisposed())
                    return new Rectangle(0,0,0,0);
                Point p = c.toDisplay(0, 0);
                Point pp = parent.toDisplay(0, 0);
                Rectangle b = c.getBounds();
                b.x = p.x - pp.x;
                b.y = p.y - pp.y;
                adjustBoundsForBorderedControls(b);
                return b;
            }
        }
        //This is for Viewer controls
        //if (isVirtual)
        //return getParent().getBoundsRelativeTo(parent);


        Rectangle rec = getBounds(true, zeroIfHidden, allowParentOfRoot);
        Composite root = (Composite) getRootControl();
        Composite cmp = root;

        if (isSWT() && cmp != null) {
            cmp = cmp.getParent();
        }

        while (cmp != null && !cmp.equals(parent)) {
            if (cmp instanceof Shell) {
                rec.x += 25;
                rec.y += 42;
            } else {
                Point pos = cmp.getLocation();
                rec.x += pos.x;
                rec.y += pos.y;
            }
            cmp = cmp.getParent();
        }

        //Assume for now that all CWT components are relative to root
        if (isCWT())
            return rec;

        FormComponent par = getParent();
        while (par != null) {
            if (!par.isA(TreeItem.class) && !par.isA(TableTreeItem.class)) {
                //location doesn't work for buttons in StubWizardDialog.buttonBar!
                //but bounds doesn't work for toolbar items (and most things in DummyEclipse!)
                if(par.isVirtual()) {
                    Rectangle b = par.getBounds(false, true);
                    rec.x += b.x;
                    rec.y += b.y;
                } else {
	                Point b = par.getLocation();
	                rec.x += b.x;
	                rec.y += b.y;
                }
            }

            if (par.getParent() != null && par.getParent().getComponent() instanceof Container) {
                SwingHelper.addVectorBetween((Container) par.getParent().getComponent(),
                        par.getComponent(), rec);
            } else if (par.isJMenuBar()
                    && getRootComponent().isJInternalFrame()) {
                SwingHelper.addVectorBetween((Container) getRootComponent().getComponent(),
                        par.getComponent(), rec);
            }

            par = par.getParent();
        }
        //System.out.println("GET BNDS REL TO " + this +", " + rec);
        return rec;
    }

    public Rectangle getClientBounds() {
        if (component != null) {
            //java.awt.Rectangle rec = component.getBounds();
            Rectangle rec = getBounds();
            if (component instanceof JScrollPane) {
                int sbh = ((JScrollPane) component).getHorizontalScrollBar()
                        .getHeight();
                int sbw = ((JScrollPane) component).getVerticalScrollBar()
                        .getWidth();
                rec.width -= sbw;
                rec.height -= sbh;
            }
            return new Rectangle(rec.x, rec.y, rec.width, rec.height);
        }
        if (!(getControl() instanceof Composite))
            return getBounds();
        Rectangle r = getBounds();
        Rectangle ca = ((Composite) getControl()).getClientArea();
        ca.x += r.x;
        ca.y += r.y;
        return ca;
    }

    public Component getComponent() {
        return component;
    }

    private Component rawComponent = null;
    
    /**
     * If this element uses a contentPane then component is the contentPane
     * and rawComponent is the "real" component. In other cases, rawComponent
     * equals component.
     * @return
     */
    public Component getRawComponent() {
    	if(rawComponent != null)
    		return rawComponent;
    	return component;
    }

    public void initConstraints() {
        HashMap constraints = new HashMap();
        HashMap extraCons;
        NodeList list = NodeUtils.getChildrenOfNode("Constraints", node);
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node n = (Node) list.item(i);
                NodeUtils.fillAttributes(constraints, n, this, false);
                //this is for netbeans compatability
                Node cn;
                cn = NodeUtils.getChildNodeByName("JLayeredPaneConstraints", n);
                if (cn != null) {
                    extraCons = new HashMap();
                    NodeUtils.fillAttributes(extraCons, cn, this, true);
                    int x = getIntFromMap(extraCons, "x");
                    int y = getIntFromMap(extraCons, "y");
                    int w = getIntFromMap(extraCons, "width");
                    int h = getIntFromMap(extraCons, "height");
                    if (propertiesInited) {
                        setPropertyValueDirect("size", new Point(w, h));
                        setPropertyValueDirect("location", new Point(x, y));
                        setPropertyValueDirect("bounds", new Rectangle(x, y, w,
                                h));
                        //if (editor != null)
                        //getEditor().setDirty(false);
                    }
                    int layer = getIntFromMap(extraCons, "layer");
                    int posn = getIntFromMap(extraCons, "position");
                }
                extractConstraint(n, "JTabbedPaneConstraints", "tabTitle", constraints);
                extractConstraint(n, "BorderConstraints", "direction", constraints);
                extractConstraint(n, "CardConstraints", "CardName", constraints);
                extractConstraint(n, "JSplitPaneConstraints", "position", constraints);
                String lc;
                String plt = getParentSuperLayoutType();
                if ("Border".equals(plt)) {
                    lc = (String) constraints.get("direction");
                    if (lc != null)
                        setLayoutData( lc);
                } else if ("Card".equals(plt)) {
                    lc = (String) constraints.get("CardName");
                    if (lc != null)
                        setLayoutData( lc);
                } else {
                    lc = (String) constraints.get("position");
                    if (lc != null)
                        setLayoutData( lc);
                }
            }
        }
    }

    private int getIntFromMap(HashMap map, String name) {
        try {
            return Integer.parseInt((String) map.get(name));
        } catch (Throwable e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void extractConstraint(Node node, String constr, String name, HashMap constraints) {
        Node cn = NodeUtils.getChildNodeByName(constr, node);
        if (cn != null) {
            HashMap extraCons = new HashMap();
            NodeUtils.fillAttributes(extraCons, cn, this, true);
            String val = (String) extraCons.get(name);
            if (val != null) {
                constraints.put(name, val);
            }
        }
    }

    private void setEditorReload(boolean needsReload) {
        if (editor != null)
            getEditor().setNeedsReload(needsReload);
    }

    public void prepNextGridXY(LayoutDataWrapper ldw, MouseEvent evt) {
        int x = 0;
        int y = 1;
        String gxs= "gridx";
        String gys= "gridy";
        String gws= "gridwidth";
        String ghs= "gridheight";
        int rows = 1000;
        int cols = 4;
        Object ldn = ldw.getLayoutData();
        if(ldn == null)
            return;
        Class ldcls = ldn.getClass();
        boolean isForm = false;
        if(ldn instanceof CellConstraints) {
            isForm = true;
            com.jgoodies.forms.layout.FormLayout fl = 
                (com.jgoodies.forms.layout.FormLayout)getLayoutManager();
            rows = fl.getRowCount();
            cols= fl.getColumnCount();
            gxs="gridX";
            gys="gridY";                
            gws="gridWidth";
            ghs="gridHeight";
        }
        if (evt != null) {
            int ey = evt.y;
            if(isRoot())
            	ey -= getEditor().getMenuBarHeight();
            int[] xy = getGridBagCoords(evt.x, ey, true, true);
            x = xy[0];
            y = xy[1];
            if(isForm) {
                x++;
                y++;
            }
        } else {
            for (int i = 0; i < components.size(); i++) {
                FormComponent fc = getChildAt(i);
                Object ld = fc.getLayoutDataWrapper().getLayoutData();
                if (ld != null &&  Cacher.isAssignableFrom(ldcls, ld.getClass())) {
                    int gx = fc.getLayoutPropAsInt(gxs);
                    int gy = fc.getLayoutPropAsInt(gys);
                    int gw = fc.getLayoutPropAsInt(gws);
                    int gh = fc.getLayoutPropAsInt(ghs);
                    if (gw < 0)
                        gw = 1;
                    if (gh < 0)
                        gh = 1;
                    if (gy + gh > y) {
                        y = gy + gh;
                        x = 0;
                        if(isForm)
                            x++;
                    }
                    if (gx + gw > x && y == gy + gh)
                        x = gx + gw;
                }
            }
            if ((isForm && x > cols) || (!isForm && x >= cols)) {
                if(isForm)
                    x = 1;
                else
                    x = 0;
            } else {
                y--;
            }
            if (isForm) {
                if (x > cols)
                    x = cols;
                if (y > rows)
                    y = rows;
            }
        }

        ldw.setPropertyValue(gxs, new Integer(x));
        ldw.setPropertyValue(gys, new Integer(y));
    }
    
    public void prepNextTableXY(FormComponent child, MouseEvent evt) {
        int x = 0;
        int y = 1;
        if (evt != null) {
            int ey = evt.y;
            if(isRoot())
            	ey -= getEditor().getMenuBarHeight();
            int[] xy = getGridBagCoords(evt.x, ey, true, true);
            x = xy[0];
            y = xy[1];
        } else {
            for (int i = 0; i < components.size(); i++) {
                FormComponent fc = getChildAt(i);
                int gx = fc.getGridValue(true);
                int gy = fc.getGridValue(false);
                if (gy > y) {
                	y = gy;
                	x = 0;
                }
                if (gx > x && y == gy)
                	x = gx;
            }
    		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(parent);
    		int cols = tl.getNumColumn();
            if ( x >= cols) {
                x = 0;
            } else {
                y--;
            }
        }
        child.getLayoutDataWrapper().setObject(x+", "+y);
    }

    public void adjustJSplitPaneConstraints() {
        if(!isSwing())
            return;
        if (isInherited())
            return;
        if (parent != null && parent.isSubclassOf(JSplitPane.class)) { 
        	LayoutDataWrapper ldw = layoutDataWrapper;
            Object ld = ldw.getLayoutData();
            FormComponent sib = getOtherSibling();
            if (sib != null) {
                Object olc = sib.getLayoutDataWrapper().getLayoutData();
                if (ld == null) {
                    ldw.setObject(JSplitPane.LEFT);
                    repairParentConnectionInCode();
                }
                if (olc == null)
                    olc = JSplitPane.LEFT;
                if (ld != null && ld.equals(olc)) {
                    if (olc.equals(JSplitPane.LEFT)) {
                        olc = JSplitPane.RIGHT;
                    } else if (olc.equals(JSplitPane.RIGHT)) {
                        olc = JSplitPane.LEFT;
                    } else if (olc.equals(JSplitPane.TOP)) {
                        olc = JSplitPane.BOTTOM;
                    } else if (olc.equals(JSplitPane.BOTTOM)) {
                        olc = JSplitPane.TOP;
                    }
                    sib.setLayoutData(olc);
                    sib.repairParentConnectionInCode();
                }
            }
        }
    }

    public Object getRawControl() {
        return control;
    }

    public Object getControl() {
        //if (control == null
        //|| (control instanceof Widget && ((Widget) control).isDisposed()))
        //return null;
        return control;
    }

    public void notifyListeners(boolean showView, boolean refresh) {
        if (getEditor() != null && !getEditor().isPopulating()
                && !getEditor().isPreviewing())
            getEditor().notifyListeners(showView, refresh);
    }

    public void selectInCode(String propName) {
        selectInCode(propName, false);
    }
        
    public void selectInCode(String propName, boolean showSourceTab) {
        if (jiglooPlugin.DEBUG)
            System.out.println("SELECT IN CODE " + this + ", " + propName);
        getEditor().selectInCode(this, propName, showSourceTab);
    }

    public FormEditor getEditor() {
        if (editor == null && copiedFrom != null)
            return copiedFrom.getEditor();
        if(editor == null) {
            System.err.println("EDITOR IS NULL FOR "+name);
//            new Exception().printStackTrace();
//            editor = jiglooPlugin.getActiveEditor();
        }
        return editor;
    }

    public Object getEditableValue() {
        //System.out.println("**** getEditableValue " + this);
        return this;
    }

    public Point getLocation() {
    	//otherwise if there is a menu bar, then anything inside
    	//a contentPane is offset
    	if(isContentPane())
    		return new Point(0, 0);
    	
        if (component != null) {
            java.awt.Point pt = component.getLocation();
            return new Point(pt.x, pt.y);
        } else {
            //Rectangle b = getBounds();
            //return new Point(b.x, b.y);
            return getLocation(getControl());
        }
    }

    public static Point getLocation(Object wid) {
        if (wid instanceof Control) {
            Control ctrl = (Control) wid;
            if(ctrl.isDisposed())
                return new Point(0, 0);
            return ctrl.getLocation();
        } else {
            Rectangle rec = null;
            if (wid instanceof TreeItem) {
                rec = ((TreeItem) wid).getBounds();
            } else if (wid instanceof TableTreeItem) {
                TableTreeItem tti = (TableTreeItem) wid;
                rec = tti.getBounds(0);
                //rec = tti.getBounds(tti.indexOf(tti));
            }
            if (rec != null)
                return new Point(rec.x, rec.y);
            return new Point(0, 0);
            //return getParent().getLocation();
        }
    }
    
    private Method getRawMethod(String name, Class[] mtypes, Object[] params)
            throws NoSuchMethodException {
    	if(name == null)
    		return null;
        Method meth = null;
        Object obj = getObjectForMethod(name);
        if(obj == null)
            return null;
        NoSuchMethodException nsme = null;
        meth = Cacher.getMethod(obj.getClass(), name, mtypes);
        return meth;
    }
    
    private Object getObjectForMethod(String methodName) {
        if(methodObject != null)  //v3.8.1
            return methodObject;
        if(isAbstractFormBuilder())  //v3.8.1
            return getBuilder();
        
        if(isJFaceViewer() && jfaceParent != null) {
            //v4.0M2
            try {
                Method[] methods = jfaceParent.getClass().getMethods();
                for (int i = 0; i < methods.length; i++) {
                    if(methodName.equals(methods[i].getName()))
                        return jfaceParent;
                }
            } catch(Throwable t) {}
        }
        if(!isSwing() || !usesContentPane())
            return getObject(false);
        final String[] cpProps = {"Background","Foreground","Size", "PreferredSize"};
        for (int i = 0; i < cpProps.length; i++) {
            String cpp = cpProps[i];
            if(methodName.indexOf(cpp) == 3)
                return getComponent();
        }
        return getRawComponent();
    }

    public Class getConvertedMainClass() {
    	if(convertedMainClass == null)
    		convertedMainClass = getConvertedMainClass(getMainClass(false));
    	return convertedMainClass;
    }

    public Class getConvertedMainClass(Class mc) {
    	if(mc == null)
    		return null;
        if (mc.equals(TableViewer.class)) {
            mc = Table.class;
        } else if (mc.equals(CheckboxTableViewer.class)) {
            mc = Table.class;
        } else if (mc.equals(TableTreeViewer.class)) {
            mc = TableTree.class;
        } else if (mc.equals(CheckboxTreeViewer.class)) {
            mc = Tree.class;
        } else if (mc.equals(TreeViewer.class)) {
            mc = Tree.class;
        } else if (mc.equals(TextViewer.class)) {
            mc = StyledText.class;
        } else if (mc.equals(ListViewer.class)) {
            mc = List.class;
        } else if (isAbstractFormBuilder()) {
            mc = JPanel.class;
        }
        try {
        	if (mc.equals(ComboViewer.class)) {
                mc = Combo.class;
            }
        } catch(Throwable t) {}
        return mc;
    }

    public Class getMainClass() {
    	if(mainClass != null)
    		return mainClass;
    	return getMainClass(false);
    }
        
    public Class getMainClass(boolean useRootClass) {
    	if(mainClass == null)
    		mainClass = getMainClass1(false);
        if(useRootClass && getEditor() != null && mainClass != null && isRoot()) {
        	Class emc = getEditor().getMainClass();
        	if(emc != null && Cacher.isAssignableFrom(mainClass, emc))
        	return emc;
        }
    	return mainClass;
    }

    public Class getMainClass1(boolean useRootClass) {

        if (nonVisualObject != null) {
            return nonVisualObject.getClass();
        }

        //something like this is needed so that the properties
        //of the root's main class will be discovered, and not the class
        //that the root class extends
        if(useRootClass && editor != null && mainClass != null && isRoot()) {
        	Class emc = getEditor().getMainClass();
        	if(emc != null && Cacher.isAssignableFrom(mainClass, emc))
        	return emc;
        }
        
        if (mainClass != null)
            return mainClass;
        if (className != null) {
        	
        	if(getEditor() == null) {
        		System.err.println("GetMainClass editor is null "+name+", "+className);
        		return null;
        	}
        	
            mainClass = getEditor().loadClass(className);
            if (mainClass != null)
                return mainClass;
        }

        if (component != null) {
            return component.getClass();
        }

        if (getControl() != null) {
            return getControl().getClass();
        }
        //System.err.println("NO OBJECT FOR FC " + this);
        return Object.class;
    }

    public static Class getMethodValueClass(Object value) {
        Class cls = value.getClass();
        return getMethodValueClass(cls);
    }

    public static Class getMethodValueClass(Class cls) {
        if (cls.equals(Boolean.class))
            cls = boolean.class;
        if (cls.equals(Integer.class))
            cls = int.class;
        if (cls.equals(Float.class))
            cls = float.class;
        if (cls.equals(Double.class))
            cls = double.class;
        if ( Cacher.isAssignableFrom(Border.class, cls))
            return Border.class;
        if ( Cacher.isAssignableFrom(Color.class, cls))
            return Color.class;
        if ( Cacher.isAssignableFrom(Font.class, cls))
            return Font.class;
        if (jiglooPlugin.canUseSwing()) {
            if ( Cacher.isAssignableFrom(java.awt.Color.class, cls))
                return java.awt.Color.class;
            if ( Cacher.isAssignableFrom(java.awt.Font.class, cls))
                return java.awt.Font.class;
        }
        return cls;
    }

    public void setHasParentInCode(boolean hasPar) {
        hasParentInCode = hasPar;
    }

    public boolean hasParentInCode() {
        return hasParentInCode;
    }

    public void setClassName(String className) {
        setClassName(className, true);
    }

    public void setClassName(String className, boolean getInherited) {
        String oc = SwingSWTMapper.getOrderableClass(className);
        if (oc != null)
            className = oc;
        this.className = className;
        mainClass = null;
        convertedMainClass = null;
        getMainClass();
        setClassType(getMainClass());
        if (getInherited)
            getInheritedElements();
        isVisual = null;
        //initInheritedElements();
    }

    public String getClassName() {
        if (className == null) {
            className = "UNKNOWN";
        }
        return className;
    }

    public String getTranslatedClassName() {
        String cn = getClassName();
        String tc = SwingSWTMapper.getNonOrderableClass(cn);
        if (tc != null)
            return tc;
        if (cn.equals(Shell.class.getName()))
            return Composite.class.getName();
        return cn;
    }

    public String getShortClassName() {
        String clsName = getFullClassName();
        int pos = clsName.indexOf("<");
        if(pos > 0)
        	clsName = clsName.substring(0, pos);
        pos = clsName.lastIndexOf(".") + 1;
        clsName = clsName.substring(pos);
        if(clsName.indexOf("$") >= 0)
        	clsName = clsName.substring(clsName.lastIndexOf("$")+1);
        return clsName;
    }

    public String getClassNameForCode() {
        String fn = getFullClassName();
        //a hack to cope with Box$Filler classes
        if(fn.indexOf("Box$Filler") > 0) {
        	if(isSwing() && isVisual())
        		return "java.awt.Component";
        }
        
        if(fn.indexOf("$") >= 0) {
            return fn.substring(fn.lastIndexOf("$")+1);
        }
        
        if (jiglooPlugin.useImports()) {
            addImport(fn);
            return getShortClassName();
        } else {
            return fn;
        }
    }

    public String getFullClassName() {
        String clsName = getClassName();
        String tc = SwingSWTMapper.getNonOrderableClass(clsName);
        if (tc != null)
            clsName = tc;
        if ("java.lang.Class".equals(clsName)) {
            clsName = ((Class) nonVisualObject).getName();
        }
        if(isA(ActionStub.class)) {
            clsName = AbstractAction.class.getName();
        }
        return clsName;
    }

    private String blockName = null;

    public void setBlockName(String bn) {
        blockName = bn;
    }

    public String getBlockName() {
        if (blockName != null)
            return blockName;
        String n = getName();
        int pos = n.lastIndexOf(".");
        if (pos < 0)
            return "";
        return n.substring(0, pos + 1);
    }

    public String getName() {
        if (name == null) {
            if (isBaseComponent())
                return "this";
            return "unknownName";
        }
        return name;
    }

    public String getDimensions() {
        if (nonVisualObject == null || !nonVisualObject.getClass().isArray())
            return "";
        String dims = "[";
        Object ob = nonVisualObject;
        while (ob != null && ob.getClass().isArray()) {
            if (!"[".equals(dims))
                dims += ",";
            int l = Array.getLength(ob);
            dims += l;
            if (l == 0)
                break;
            ob = Array.get(ob, 0);
        }
        return dims + "]";
    }

    private String removeHiddenPart(String name) {
    	if(name == null)
    		return null;
    	int pos = name.indexOf("::");
    	if(pos < 0)
    		return name;
    	return name.substring(0, pos);
    }
    
    public String getDisplayName() {
        try {
            String name = "";
            String txt = OpenSwingHelper.getDisplayText(this);
            if(txt == null && hasProperty("text") && getPropertyValue("text") instanceof String) {
                if(txt == null)
                    txt = (String)getPropertyValue("text");
                if(txt != null) {
                    if(txt.length() > 10) {
                        txt = txt.substring(0, 10)+"...";
                    }
                }
            }
            if(txt != null)
                name = "\""+txt+"\" ";
            
            if(!isInline()) {
                name += removeHiddenPart(getNonBlockName());
            }
            name += " - " + getShortClassName() + getDimensions();
            
            //for testing!!!
//            name = getName();
            
            if (isContainer()) {
                String layout = getLayoutType();
                if (layout != null) {
                    int pos = layout.lastIndexOf(".") + 1;
                    layout = layout.substring(pos);
                    name += ", " + layout;
                }
            }
            String styleStr = SWTStyleManager.getStyleString(this, false);
            if (!"".equals(styleStr))
                name += ", " + styleStr;
            name += " ";
            Object ld = getLayoutDataWrapper().getLayoutData();
            if (ld instanceof String && !INVALID_LAYOUT_CONSTRAINT.equals(ld))
                name += ", " + ld;
            return name;
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error in getDisplayName for " + name);
            return name;
        }
    }

    public FormComponent getParent() {
        return parent;
    }

    public Vector getPropertyNames() {
        return propNames;
    }

    private static String getConstructorForField(String name, Object o,
            IJavaCodeManager jcm) {

        if (o instanceof ISWTDisposableWrapper) {
            return ((ISWTDisposableWrapper) o).getSWTDeclaration(name, jcm);
        }

        Constructor[] cons = o.getClass().getConstructors();
        for (int i = 0; i < cons.length; i++) {
            Constructor con = cons[i];
            if (con.getModifiers() == Modifier.PUBLIC
                    && con.getParameterTypes().length == 0) {
            }
        }
        String cn = o.getClass().getName();
        return cn + " " + name + " = new " + cn + "();";
    }

    public static String replace(String a, String b, String str) {
        int pos = 0;
        String ret = "";
        while ((pos = str.indexOf(a)) >= 0) {
            ret += str.substring(0, pos) + b;
            str = str.substring(pos + a.length());
        }
        ret += str;
        return ret;
    }

    private void addImport(String imp) {
        if (getEditor().getJavaCodeParser() != null)
            getEditor().getJavaCodeParser().addImport(imp);
    }

    public String getJavaCodeForPropertySetter(String id, IJavaCodeManager jcm) {
        try {
            
            //System.out.println("GET JAVA CODE FOR PROP " + id + ", " + this);
            String elemName = getNameInCode();
            
            if(!OpenSwingHelper.ignoreJavaInitialisationString(this, id)) {
                if(beanPropEds != null && beanPropEds.containsKey(id)) {
                    PropertyEditor ped = getCustomPropertyEditor(id);
                    if(ped != null) {
//                        Object val = getPropertyValue((String)id);
//                        if(val != null)
//                            ped.setValue(val);
                        String code = ped.getJavaInitializationString();
                        if(code != null && !code.equals("???")) {
                            code = elemName+".set"+JiglooUtils.capitalize(id)+"("+code+");";
                            return code;
                        }
                    }
                }
            }
            
            if (usesContentPane() && (id.equals("background") || id.equals("foreground")))
                elemName = getContentPaneCode(elemName);

            elemName = getJFaceViewerControlCode(elemName);

            Object o = null;
            Class objClass = null;
            String preCode = "";
            String postCode = "";
            //if (id.equals("bounds") &&
            // !"Absolute".equals(getParentLayoutType())) {
            //return new String[] { "", "" };
            //}
            if (id.equals("externalizeStrings"))
                return "";
            if (id.equals("label") && isPropertySet("text"))
                return "";
            if (id.equals("preferredSize")
                    && !propNames.contains("preferredSize")
                    && propNames.contains("size")) {
                id = "size";
            }

            o = getCustomPropertyValue(id, false);
            if (o != null) {
                objClass = o.getClass();
                //System.out.println("custom prop "+o+", "+objClass);
            } else {
                o = getPropertyValue(id);
                if (propTypes != null)
                    objClass = getPropType(id, true);
                else if (o != null)
                    objClass = o.getClass();
            }

            boolean externalize = false;
            if (id.equals("text") && getPropertyCode(id) == null && "".equals(o))
            	return "";

            if (o instanceof String) {
                Boolean extTxt = (Boolean) getPropertyValue("externalizeStrings");
                if (isPropertySet("externalizeStrings")
                        && extTxt.booleanValue())
                    externalize = true;
                if (((String) o).startsWith("$EXT$"))
                    externalize = true;
            }

            if (externalize) {
                String value = (String) o;
                if (value.startsWith("$EXT$")) {
                    value = value.substring(5);
                }
                String key = JiglooUtils.replace(value, " ", "_");
                if (getEditor().isCommitting())
                    getEditor().addExternalizedString(key, value);
                createGetExtStringMethod(jcm);
                return "\t\t" + elemName + ".set" + JiglooUtils.capitalize(id)
                                + "(getExternalizedString(\"" + key
                                + "\")); //$NON-NLS-1$\n";
            }

            StringBuffer tmpCode = null;
            
            if (id.equals("buttonGroup")) {
                if (o == null)
                    return "";
                String bgName = o.toString();
                if (o instanceof FormComponentWrapper) {
                    FormComponent fc = ((FormComponentWrapper) o)
                            .getFormComponent();
                    if (fc != null)
                        bgName = fc.getNameInCode();
                }
                return "\t\t" + bgName + ".add(" + elemName + ");\n";
                
            } else if(isJFaceApplicationWindow()) {
                return ApplicationWindowManager.getJavaCodeForProperty(id, o);                
            
            } else {

                String[] codeParts = getJavaCodeForObject(o, objClass, id);
                
                if (!getEditor().updatesJavaCode()) {
                    
                    return  codeParts[0] + "\t\t" + elemName + ".set" + JiglooUtils.capitalize(id)
                    + "(" + codeParts[1] + ");\n";
                    
                } else {
                    
                    String propCode = getPropertyCode(id);
                    if (propCode != null)
                        codeParts[1] = propCode;
                    
                    if(codeParts[1] == null || codeParts[1].equals(""))
                        return "";
                    
                    if(o instanceof FormComponent) {
                        //if codeParts[1] is the field name, convert it, but if it's something like getActionMap().get("cut") then don't
                        codeParts[1] = ((JavaCodeParser)jcm).convertToMethod(codeParts[1], codeParts[1], false);
                    }
                    
                    if(isSubclassOf(AbstractAction.class)) {
                        return ((ActionStub)getObject(true)).getJavaCodeForPropertySetter(this, id, codeParts[1]);
                    }
                    
                    return "\t\t" + elemName + ".set"+ JiglooUtils.capitalize(id) + "("+codeParts[1] + ");\n";
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.writeToLog("Error getting code for "+id);
            jiglooPlugin.handleError(t);
            return "";
        }
    }

    public PropertyEditor getCustomPropertyEditor(Object id) {
        return (PropertyEditor)beanPropEds.get(id);
    }

    /**
     * @param elemName
     * @return
     */
    private String getJFaceViewerControlCode(String elemName) {
        
        if (!isJFaceViewer())
            return elemName;

        try {
        	if (isSubclassOf(ComboViewer.class)) {
        		elemName = elemName + ".getCombo()";
        	}
        } catch(Throwable t) {}
        
        if (isSubclassOf(TableViewer.class)
        		|| isSubclassOf(CheckboxTableViewer.class)) {
            elemName = elemName + ".getTable()";

        } else if (isSubclassOf(TreeViewer.class)) {
            elemName = elemName + ".getTree()";

        } else if (isSubclassOf(TableTreeViewer.class)) {
            elemName = elemName + ".getTableTree()";

        } else if (isSubclassOf(ListViewer.class)) {
            elemName = elemName + ".getList()";

        } else if (isSubclassOf(TextViewer.class)) {
            elemName = elemName + ".getTextWidget()";

        } else {
            elemName += ".getControl()";
        }
        return elemName;
    }

    private void createGetExtStringMethod(IJavaCodeManager jcm) {
        if (!jcm.hasMethod("getExternalizedString", new String[] { "String" })) {
            jcm
                    .addMethod(
                            "getExternalizedString",
                            "\t\ttry {\n"
                                    + "\t\t\treturn java.util.ResourceBundle.getBundle(\""
                                    + jcm.getPackageName()
                                    + "."
                                    + getRootComponent().getName()
                                    + "Messages\").getString(key);\n"
                                    + "\t\t} catch (java.util.MissingResourceException e) {\n"
                                    + "\t\t\treturn '!' + key + '!';\n"
                                    + "\t\t}\n",
                            "String",
                            new String[] { "String" },
                            new String[] { "key" },
                            Flags.AccPublic,
                            "\n\t/**\n"
                                    + "\t* This is an auto-generated method which you can alter,\n"
                                    + "\t* e.g. to point to a different property file, to modify the key by\n"
                                    + "\t* by prefixing the name of this class, etc.\n"
                                    + "\t*\n"
                                    + "\t* By default, it expects a file called \"messages.properties\" to exist in the\n"
                                    + "\t* current package, and returns the value of the property defined\n"
                                    + "\t* in that file for the given key\n\t*/\n"
                                    + "\t");
        }
    }

    public boolean isDialogShell() {
        return "dialogShell".equals(getNameInCode());
    }

    public String getJavaCodeForProperty(String id) {
        Object o = getCustomPropertyValue(id, false);
        Class objClass = null;
        if (o != null) {
            objClass = o.getClass();
            //System.out.println("custom prop "+o+", "+objClass);
        } else {
            o = getPropertyValue(id);
            if (propTypes != null)
                objClass = getPropType(id, true);
            else if (o != null)
                objClass = o.getClass();
        }

    	return getJavaCodeForObject(o, objClass, id)[1];
    }

    public String[] getJavaCodeForObject(Object o, Class propType, String id) {
    	IJavaCodeManager jcm = getJavaCodeParser();
        String tmpCode = "", preCode = "", postCode = "";
        
        if(propType == null && o != null)
            propType = o.getClass();
        
        if(o instanceof ClassWrapper)
        	o = ((ClassWrapper)o).getFormComponent();
        
        if (o == null) {
            tmpCode += "null";
        } else if (o instanceof String) {
            String val = o.toString();
            val = val.replaceAll("\\\\", "\\\\\\\\");
            val = replace("\n", "\\n", val);
            val = replace("\r", "\\r", val);
            val = replace("\t", "\\t", val);
            val = replace("\"", "\\\"", val);
            val = JiglooUtils.encodeUnicodeString(val);
            if ("accelerator".equals("id"))
                tmpCode += val;
            else
                tmpCode += "\"" + val + "\"";

        } else if (o instanceof FieldWrapper) {
            FieldWrapper fw = (FieldWrapper) o;
            if (jiglooPlugin.useImports()) {
                jcm.addImport(fw.getFieldClassName());
                tmpCode += fw.getFieldAsString();
            } else {
                tmpCode += fw.getFullFieldName();
            }
            //tmpCode += fw.getValue().toString();
        } else if (o instanceof IWrapper) {
            tmpCode += ((IWrapper) o).getJavaConstructor(jcm);
        } else if (o instanceof LayoutWrapper) {
            tmpCode += ((LayoutWrapper) o).getNameInCode();
        } else if (o instanceof LayoutDataWrapper) {
        	LayoutDataWrapper ldw = (LayoutDataWrapper) o;
        	if(ldw.getLayoutData() instanceof String)
        		tmpCode += "\""+ldw.getLayoutData()+"\"";
        	else
        		tmpCode += ldw.getNameInCode();
        } else if (o instanceof FormComponent) {
            tmpCode += ((FormComponent) o).getDeclarationInCode();
            
            //put these tests after one for FieldWrapper, since o might be an instance
            //of FieldWrapper, but propType is int.class
        } else if (boolean.class.equals(propType)
                || int.class.equals(propType)
                || double.class.equals(propType)
                || short.class.equals(propType)) {
            tmpCode += o.toString();
        } else if (long.class.equals(propType)) {
            tmpCode += o + "L";
        } else if (float.class.equals(propType)) {
            tmpCode += o + "f";
            
        } else if (Boolean.class.equals(propType)) {
            tmpCode += o.equals(Boolean.TRUE) ? "Boolean.TRUE" : "Boolean.FALSE";
        } else if (Integer.class.equals(propType)) {
            tmpCode += "new Integer("+o+")";
        } else if (Double.class.equals(propType)) {
            tmpCode += "new Double("+o+")";
        } else if (Short.class.equals(propType)) {
            tmpCode += "new Short("+o+")";
        } else if (Long.class.equals(propType)) {
            tmpCode += "new Long("+o+"L)";
        } else if (Float.class.equals(propType)) {
            tmpCode += "new Float("+o+"f)";
            
        } else {
            String pdConst = editor.getPredefinedConstructor(o);
            if (pdConst != null) {
                tmpCode += pdConst;
            } else {
                if (isSwing() || isCWT()) {
                    if (o instanceof BorderPropertySource) {
                        tmpCode += ((BorderPropertySource) o).getJavaCode(jcm);
                    } else if (o instanceof ImageWrapper) {
                        tmpCode += ((ImageWrapper) o).getSwingConstructor(jcm);
                    } else if (o instanceof IconWrapper) {
                        tmpCode += ((IconWrapper) o).getSwingConstructor(jcm);
                    } else if (o instanceof FontWrapper) {
                        FontWrapper fw = (FontWrapper) o;
                        tmpCode += "new java.awt.Font(\"" + fw.getName()
                                + "\"," + fw.getStyle() + "," + fw.getSize()
                                + ")";
                    } else if (o instanceof ColorWrapper) {
                        ColorWrapper cw = (ColorWrapper) o;
                        tmpCode += "new java.awt.Color(" + cw.getRed() + ","
                                + cw.getGreen() + "," + cw.getBlue() + ")";
                    } else if (o instanceof SizePropertySource) {
                        SizePropertySource sps = (SizePropertySource) o;
                        Point p = sps.getValue();
//                        if ((comp.isJFrame() || comp.isJDialog())
//                                && id.equals("size")) {
//                            addWindowDecorationSizes(p);
//                        }
                        if ("size".equals(id)) {
                            tmpCode += p.x + ", " + p.y;
                        } else {
                            if (propType.equals(java.awt.Point.class)) {
                                tmpCode += "new java.awt.Point(";
                            } else {
                                tmpCode += "new java.awt.Dimension(";
                            }
                            tmpCode += p.x + ", " + p.y + ")";
                        }

                    } else if (o instanceof RectanglePropertySource) {
                        RectanglePropertySource sps = (RectanglePropertySource) o;
                        if ((isSwing() || isSWT()) && "bounds".equals(id)) {
                            tmpCode += sps.getValue().x + ", "
                                    + sps.getValue().y + ", "
                                    + sps.getValue().width + ", "
                                    + sps.getValue().height;

                        } else {
                            tmpCode += "new java.awt.Rectangle("
                                    + sps.getValue().x + ", "
                                    + sps.getValue().y + ", "
                                    + sps.getValue().width + ", "
                                    + sps.getValue().height + ")";
                        }
                    } else if (o instanceof InsetsPropertySource) {
                        InsetsPropertySource sps = (InsetsPropertySource) o;
                        //jcm.addImport(Insets.class.getName());
                        tmpCode += "new java.awt.Insets(" + sps.getValue().top
                                + ", " + sps.getValue().left + ", "
                                + sps.getValue().bottom + ", "
                                + sps.getValue().right + ")";
                    } else if (id.equals("layout")) {
                        tmpCode += getLayoutWrapper().getNameInCode();
                    } else {
                    	System.err.println("getJavaCodeForObject - no code for "+getNameInCode()+" property "+id+", val="+o);
                    }
                } else {
                    //SWT
                    if (o instanceof SizePropertySource) {
                        SizePropertySource sps = (SizePropertySource) o;
                        Point pt = sps.getValue();
                        if (id.equals("size")) {
//                            if (isDialogShell()) {
//                                addWindowDecorationSizes(pt);
//                            }
                            pt = adjustSize(pt, getControl());
                            tmpCode += pt.x + ", " + pt.y;
                        } else {
                            tmpCode += "new org.eclipse.swt.graphics.Point("
                                    + pt.x + ", " + pt.y + ")";
                        }
                    } else if (o instanceof RectanglePropertySource) {
                        RectanglePropertySource sps = (RectanglePropertySource) o;
                        if ("bounds".equals(id)) {
                            tmpCode += sps.getValue().x + ", "
                                    + sps.getValue().y + ", "
                                    + sps.getValue().width + ", "
                                    + sps.getValue().height;
                        } else {
                            tmpCode += "new org.eclipse.swt.graphics.Rectangle("
                                    + sps.getValue().x
                                    + ", "
                                    + sps.getValue().y
                                    + ", "
                                    + sps.getValue().width
                                    + ", "
                                    + sps.getValue().height + ")";
                        }
                    } else if (o instanceof ISWTDisposableWrapper) {
                        ISWTDisposableWrapper sdw = (ISWTDisposableWrapper) o;
                        if (editor.updatesJavaCode()) {
                            tmpCode = sdw.getResourceCode();
                            //((JavaCodeParser) jcm)
                            //.addResourceManagerClass();
                            addImport("com.cloudgarden.resource.SWTResourceManager");
                        } else {
                            tmpCode += sdw.getSWTDeclaration(id, jcm);
                        }
                    } else {
                        String name = getNameInCode() + JiglooUtils.capitalize(id);
                        if (id.equals("layoutData")) {
                            tmpCode += getLayoutDataWrapper().getNameInCode();
                        } else if (id.equals("layout")) {
                            tmpCode += getLayoutWrapper().getNameInCode();
                        } else {
                            String resId = o.toString();
                            String resName = editor
                                    .getResourceName(resId, name);
                            if (resName != null) {
                                tmpCode += resName;
                            } else {
                                tmpCode += name;
                                preCode = "\t\t"
                                        + getConstructorForField(name, o, jcm)
                                        + "\n";
                                postCode = "\t\t" + name + ".dispose();\n";
                            }
                        }
                    }
                }
            }
        }
        return new String[] { preCode, tmpCode, postCode };
    }

    public void addWindowDecorationSizes(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
	        p.x += jiglooPlugin.getWindowDecorationWidth(); //7
	        p.y += jiglooPlugin.getWindowDecorationHeight(); //27
        } else if(obj instanceof Dimension) {
            Dimension d = (Dimension) obj;
            d.width += jiglooPlugin.getWindowDecorationWidth();
            d.height += jiglooPlugin.getWindowDecorationHeight();            
        } else if(obj instanceof Rectangle) {
            Rectangle d = (Rectangle) obj;
            d.width += jiglooPlugin.getWindowDecorationWidth();
            d.height += jiglooPlugin.getWindowDecorationHeight();            
        }
    }

    public void subtractWindowDecorationSizes(Object obj) {
        if (obj instanceof Dimension) {
            Dimension d = (Dimension) obj;
            d.width -= jiglooPlugin.getWindowDecorationWidth();
            d.height -= jiglooPlugin.getWindowDecorationHeight();
        } else if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;
            r.width -= jiglooPlugin.getWindowDecorationWidth();
            r.height -= jiglooPlugin.getWindowDecorationHeight();
        } else if (obj instanceof java.awt.Rectangle) {
            java.awt.Rectangle r = (java.awt.Rectangle) obj;
            r.width -= jiglooPlugin.getWindowDecorationWidth();
            r.height -= jiglooPlugin.getWindowDecorationHeight();
        } else if (obj instanceof Point) {
            Point r = (Point) obj;
            r.x -= jiglooPlugin.getWindowDecorationWidth();
            r.y -= jiglooPlugin.getWindowDecorationHeight();
        }
    }

    public FormComponent getRootComponent() {
        if (editor == null) {
            if (getParent() == null)
                return this;
            return getParent().getRootComponent();
        }
        FormComponent rc = getEditor().getRootComponent();
        if(rc == null && getJavaCodeParser() != null)
            rc = getJavaCodeParser().getRootComponent();
        return rc;
    }

    public int getTotalChildCount() {
        int c1 = getNonInheritedChildCount();
        int c2 = c1;
        for (int i = 0; i < getChildCount(); i++) {
        	if(!getChildAt(i).isInherited())
        		c2 += getChildAt(i).getTotalChildCount();
        }
        if(menuBar != null) {
            c2 += menuBar.getNonInheritedChildCount();
        }
        return c2;
    }

    public int getChildCount() {
        if (components == null)
            return 0;
        return components.size();
    }

    public Vector getChildrenByClass(Class cls) {
        if (components == null)
            return null;
        Vector kids = new Vector();
        for (int i = 0; i < components.size(); i++) {
            FormComponent fc = getChildAt(i);
            if (fc != null && fc.isSubclassOf(cls))
                kids.add(fc);
        }
        return kids;
    }

    public FormComponent getChildByName(String name) {
        if (components == null)
            return null;
        String name2 = null;
        String name3 = null;
        if(name.startsWith("this.") || name.startsWith("super.")) {
            name2 = JiglooUtils.switchThisAndSuper(name);
            name3 = JiglooUtils.getUnqualifiedName(name);
        } else {
        	name2 = "this."+name;
        	name3 = "super."+name;
        }
        
        for (int i = 0; i < components.size(); i++) {
            FormComponent fc = getChildAt(i);
            if (name.equals(fc.getName()) 
                    || name.equals(fc.getInheritedName())
                    || name2.equals(fc.getName())
                    || name2.equals(fc.getInheritedName())
                    || name3.equals(fc.getName())
                    || name3.equals(fc.getInheritedName())
                    )
                return fc;
        }
        return null;
    }

    public FormComponent getLastChild() {
    	int cnt = getNonInheritedChildCount();
    	if(cnt == 0)
    		return null;
    	return getChildAt(cnt - 1);
    }
    
    public FormComponent getChildAt(int index) {
        if (index < 0 || index > getChildCount() - 1)
            return null;
        return (FormComponent) getChildren().elementAt(index);
    }

    public FormComponent getNonInheritedChildAt(int index) {
        if (index < 0 || index > getChildCount() - 1)
            return null;
        int ind = 0;
        for (int i = 0; i < getChildCount(); i++) {
            FormComponent fc = getChildAt(i);
            if (!fc.isInherited()) {
                if (ind == index)
                    return fc;
                ind++;
            }
        }
        return null;
    }

    public int getNonInheritedChildCount() {
        int cc = 0;
        for (int i = 0; i < getChildCount(); i++) {
            FormComponent fc = getChildAt(i);
            if (!fc.isInherited()) {
                cc++;
            }
        }
        return cc;
    }

    public Vector getChildren() {
        if (components != null)
            return components;
        components = new Vector();
        if (node != null)
            initSubComponentsFromNode();
        return components;
    }

    public void setChildren(Vector children) {
        components = children;
    }

    public void initSubComponentsFromNode() {
        if (editor.isNetbeans() && equals(editor.getNonVisualRoot())) {
            Vector list = NodeUtils.getChildNodesByName("Component", node);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Node n = (Node) list.elementAt(i);
                    components.add(new FormComponent(n, this, getEditor()));
                }
            }
            list = NodeUtils.getChildNodesByName("Container", node);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Node n = (Node) list.elementAt(i);
                    components.add(new FormComponent(n, this, getEditor()));
                }
            }
            list = NodeUtils.getChildNodesByName("Menu", node);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Node n = (Node) list.elementAt(i);
                    components.add(new FormComponent(n, this, getEditor()));
                }
            }
            return;
        }
        NodeList list = NodeUtils.getChildrenOfNode("SubComponents", node);
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                if (!"#text".equals(n.getNodeName())) {
                    components.add(new FormComponent(n, this, getEditor()));
                }
            }
        }
    }

    /**
     * See setOldLW
     */
    private void setOldLDW() {
        if (layoutDataWrapper == null)
            oldLDW = null;
        else
            oldLDW = layoutDataWrapper.getCopy(this);
    }

    /**
     * Save the *current* value of layoutWrapper so that the *next*
     * time setLayoutWrapper is called we will be able to decide whether
     * it has changed (or one of it's properties has changed).
     */
    private void setOldLW() {
        if (layoutWrapper == null)
            oldLW = null;
        else
            oldLW = (LayoutWrapper) layoutWrapper.getCopy(this);
    }

    public void initializeFromNode() {
        if (node == null)
            return;
        name = NodeUtils.getComponentName(node);
        layoutWrapper = new LayoutWrapper(this, node, isSwing());
        Node constNode = NodeUtils.getChildNodeByName("Constraints", node);
        if (constNode != null) {
            layoutDataWrapper = new LayoutDataWrapper(this, constNode);
        }
        style = NodeUtils.getStyle(node);
        if (className == null) {
            String nodeClass = NodeUtils.getComponentClass(node);
            //for non-visual root in Netbeans form
            if (nodeClass == null)
                nodeClass = Object.class.getName();
            setClassName(nodeClass);
            if (mainClass == null) {
                new Exception().printStackTrace();
                //System.out.println("UNABLE TO FIND " + nodeClass);
                MessageDialog
                        .openError(
                                getShell(),
                                "Unable to load Class \"" + nodeClass + "\"",
                                "Unable to load Class \""
                                        + nodeClass
                                        + " for "
                                        + name
                                        + "\"\n\nThis might be because it is not in the current project, "
                                        + "or has not been compiled yet.");
            }
        }
        eventWrapper = new EventWrapper(this, node, isSwing());
        //initPropertiesFromNode();
    }

    private void initEvents() {
        if (evtNames == null) {
            evtNames = PropertySourceFactory
                    .findEventListenerClasses(getMainClass());
        }
    }

    private void initPropertiesFromNode() {
        if (properties == null)
            properties = new HashMap();
        if (node == null)
            return;
        NodeList list = NodeUtils.getChildrenOfNode("Properties", node);
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node n = (Node) list.item(i);
                String name = NodeUtils.getAttribute("name", n);
                Object prop = NodeUtils.getPropertyFromNode(n, this);
                if (prop != null)
                    setPropertyValueDirect(name, prop);
            }
        }

        if (editor.isNetbeans() && isSwing() && isA(JToolBar.class))
            layoutWrapper = new LayoutWrapper(this);
    }

    public Image getBeanImage() {
        return BeanHandler.getBeanImage(getMainClass(), getEditor().getProject());
        /*
         * if (!jiglooPlugin.canUseSwing()) return null; if (beanImage != null)
         * return beanImage; BeanInfo bi = jiglooPlugin.getBeanInfo(mainClass);
         * if (bi == null) return null; java.awt.Image bimg =
         * bi.getIcon(BeanInfo.ICON_COLOR_16x16); if (bimg == null) bimg =
         * bi.getIcon(BeanInfo.ICON_MONO_16x16); if (bimg == null) return null;
         * return JiglooUtils.getSWTImage(bimg);
         */
    }

    public boolean showCustomizer() {
        Object obj = getObject(false);
        if (obj == null)
            return false;
        try {
            if(OpenSwingHelper.showCustomizer(this))
                return true;
            BeanInfo beanInfo = BeanHandler.getBeanInfo(getMainClass(), getEditor().getProject());
            if (beanInfo == null)
                return false;
            Class cc = beanInfo.getBeanDescriptor().getCustomizerClass();
            if (cc == null)
                return false;
            Customizer cust = (Customizer) ClassUtils.newInstance(cc, null, null);
            cust.setObject(obj);
            final HashMap initialProperties = getProperties(component);
            if (cust == null)
                return false;
            //System.out.println("GOT CUSTOMIZER " + customizer + ", for " + this);
            PropertyChangeListener pcl = new PropertyChangeListener() {
                public void propertyChange(final PropertyChangeEvent evt) {
//                    jiglooPlugin.writeToLog("GOT CUSTOMIZER PROPERTY CHANGE "
//                            + evt.getPropertyName() + ", " + evt.getNewValue());
                    Display.getDefault().asyncExec(new Runnable() {
                        public void run() {
                            try {
                                String id = evt.getPropertyName();
                                if(id.equals("ancestor")) //ignore ancestor property
                                	return;
                                Object value = evt.getNewValue();
                                if (hasProperty(id)) {
                                    setPropertyValue(id, value);
                                } else if ("UPDATE".equals(id)) {
                                    updateInCode();
                                    getEditor().setDirtyAndUpdate();
                                } else {
                                    updateProperties(component, initialProperties);
                                    updateInCode();
                                    getEditor().setDirtyAndUpdate();
                                }
                            } catch (Throwable e) {
                                jiglooPlugin.handleError(e);
                            }
                        }
                    });
                }
            };
            
            cust.addPropertyChangeListener(pcl);
            
            String sd = beanInfo.getBeanDescriptor().getShortDescription();
            String title = "";
            if (sd != null && sd.length() > 0) {
                title = "Customizer: " + sd;
            } else {
                title = "Customizer: " + getMainClass().getName();
            }
            
            if(false && jiglooPlugin.canUseSWT_AWT()) {
                SwingDialog dialog = new SwingDialog(parent.getShell(), SWT.NULL);
                dialog.setText(title);
                dialog.setJPanel((Component) cust);
                dialog.open();
            } else {
            	editor.showCustomizerFrame((Component) cust, title);
            }
            return true;
        } catch (Throwable e) {
        	jiglooPlugin.handleError(getShell(), 
                    "Error showing customizer",
                    "Error showing customizer for "+getMainClass(), e);
        }
        return false;
    }

    private void initCustomProperties() {
    	try {
    		initCustomProperties(false);
    	} catch(Throwable t) {}
    }
    
    private void initCustomProperties(boolean throwErrors) throws Throwable {
        try {
            if(beanPropDescs != null)
            	return;
            beanPropDescs = new HashMap();
            Class mc = getMainClass(true);
            if(getEditor() == null)
            	System.out.println("editor is null?");
            BeanInfo beanInfo = BeanHandler.getBeanInfo(mc, getEditor().getProject(), throwErrors);
            
            if (beanInfo == null)
                return;
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if(pds != null) {
                for (int i = 0; i < pds.length; i++) {
                    PropertyDescriptor pd = pds[i];
                    Class pec = pd.getPropertyEditorClass();
                	String pname = pd.getName();
                	pname = JiglooUtils.deCapitalize(pname);
                    if (pec != null) {
                        if(getCustomPropertyEditor(pname) != null)
                            continue;
                        beanPropDescs.put(pname, pd);
                        PropertyEditor ped = (PropertyEditor) pec.newInstance();
                        setCustomPropertyEditor(pname, ped);
                    }
                }
            }
        } catch (Throwable e) {
            jiglooPlugin.handleError(e);
            if (throwErrors)
                throw e;
        }
    }

    public void addSynthProperty(String propName, Class propClass, Object defVal) {
    	if(propNames == null)
    		return;
        if (synthProps == null)
            synthProps = new Vector();
        if (!propNames.contains(propName))
            propNames.add(propName);
        propTypes.put(propName, propClass);
        defaultProps.put(propName, defVal);
        if (!synthProps.contains(propName)) {
            synthProps.add(propName);
            putProperty(propName, defVal);
        }
    }

    /**
     * Invokes the getter directly of this FC's object
     */
    public Object queryProperty(String propName) {
        Object obj = getObject(false);
        if(obj == null)
            return null;
        try {
            Method m = Cacher.getMethod(obj.getClass(), "get"+JiglooUtils.capitalize(propName), null);
            return m.invoke(obj, null);
        } catch(Throwable t) {
        }
        return null;
    }
    
    private void initProperties(Object obj) {
        if(obj == null)
            return;
        Class cls = obj.getClass();
        Vector pNames = PropertySourceFactory.findPropertyNames(cls);
        for (int i = 0; i < pNames.size(); i++) {
            String pName = (String)pNames.elementAt(i);
            
            if(isPhantomSetProperty(pName))
            	continue;
            
            Object def = DefaultValueManager.getDefault(pName, cls);
            Object val = null;
            try {
                Method m = Cacher.getMethod(cls, "get"+JiglooUtils.capitalize(pName), null);
                val = m.invoke(obj, null);
            } catch(Throwable t) {
            }
            if(val != null && def != null && !val.equals(def)) {
            	if(constructor != null && constructorParams != null) {
            		for (int j = 0; j < constructorParams.length; j++) {
            			Object cp = constructorParams[j];
            			if(cp != null && Cacher.isAssignableFrom(val.getClass(), cp.getClass())) {
            				setPropertyValueSimple(pName, val);
            			}
            		}
//            	} else {
//    				setPropertyValue(pName, val, true);
            	}
            }
        }
    }
    
    private void updateProperties(Object obj, HashMap initialProperties) {
        if(obj == null)
            return;
        Class cls = obj.getClass();
        Vector pNames = PropertySourceFactory.findPropertyNames(cls);
        for (int i = 0; i < pNames.size(); i++) {
            String pName = (String)pNames.elementAt(i);
            if(isPhantomSetProperty(pName))
            	continue;
            Object def = initialProperties.get(pName);
            Object val = null;
            try {
                Method m = Cacher.getMethod(cls, "get"+JiglooUtils.capitalize(pName), null);
                val = m.invoke(obj, null);
            } catch(Throwable t) {        }
            if(val != null && def != null && !val.equals(def)) {
                setPropertyValue(pName, val, true);
                setNeedsUpdateInCode(pName);
                initialProperties.put(pName, val);
            }
        }
    }
    
    private HashMap getProperties(Object obj) {
        if(obj == null)
            return null;
        HashMap props = new HashMap();
        Class cls = obj.getClass();
        Vector pNames = PropertySourceFactory.findPropertyNames(cls);
        for (int i = 0; i < pNames.size(); i++) {
            String pName = (String)pNames.elementAt(i);
            if(pName == null || isPhantomSetProperty(pName))
                continue;
            if(pName.equals("minimumSize") || pName.equals("maximumSize") || pName.equals("preferredSize")) {
                pNames.remove(pName);
                i--;
                continue;
            }
            Object val = null;
            try {
                Method m = Cacher.getMethod(cls, "get"+JiglooUtils.capitalize(pName), null);
                val = m.invoke(obj, null);
                if(val != null)
                    props.put(pName, val);
            } catch(Throwable t) {        }
        }
        return props;
    }
    
    public Object[] getConstructorParams() {
        return constructorParams;
    }
    
    /**
     * Initialises propNames, propTypes and properties
     */
    public void initProperties() {
//        log(null);
    	try {
    		
    	    Class mainClass = getMainClass();
    	    
    		if(FormComponent.class.equals(mainClass))
    			return;
    		
    		boolean firstInit = false;
    		if (properties == null) {
    			firstInit = true;
    			properties = new HashMap();
    		}
    		
    		Class convMainClass = getConvertedMainClass(getMainClass(true));

    		if (propTypes == null) {
    			propTypes = PropertySourceFactory.findPropertyTypes(convMainClass);
    		}
    		if (defaultProps == null) {
    			defaultProps = new HashMap();
    		}
    		
    		propertiesInited = false;
    		boolean fakeNames = false;
    		Object psize = null;
    		if (isPropertySet("preferredSize"))
    			psize = getPropertyValue("preferredSize");
    		
    		Object size = null;
    		
    		if (isPropertySet("size"))
    			size = getPropertyValue("size");

    		if (propNames == null) {
    			propNames = PropertySourceFactory.findPropertyNames(convMainClass);
    			fieldNames = PropertySourceFactory.findFieldNames(convMainClass);
    			propTypes = PropertySourceFactory.findPropertyTypes(convMainClass);
    			if (mainClass.equals(ScrolledComposite.class)) {
    				if (!propNames.contains("minSize"))
    					propNames.add("minSize");
    				if (!propNames.contains("origin"))
    					propNames.add("origin");
    				if (!propNames.contains("expandVertical"))
    					propNames.add("expandVertical");
    				if (!propNames.contains("expandHorizontal"))
    					propNames.add("expandHorizontal");
    				fakeNames = true;
    			}
    			if (mainClass.equals(Dialog.class)) {
    				if (!propNames.contains("size"))
    					propNames.add("size");
    				fakeNames = true;
    			}
    		}
    		if (psize != null && size == null && propNames.contains("size")
    				&& !propNames.contains("preferredSize")) {
    			setPropertyValueDirect("size", psize);
    		}
    		if (size != null && psize == null
    				&& propNames.contains("preferredSize")) {
    			setPropertyValueDirect("preferredSize", size);
    		}
    		
    		addSynthProperties();

    		if (!isSwing())
    			propNames.remove("accelerator");

    		for (int i = 0; i < propNames.size(); i++) {
    			String name = (String) propNames.elementAt(i);
    			Method gm = PropertySourceFactory.getGetter(convMainClass, name);
    			if (gm == null && !fakeNames) {
    				//                System.err.println("Unable to find method " + gmName + " for " + this);
    				continue;
    			}
    			Class retType = null;
    			if (gm != null)
    				retType = gm.getReturnType();
    			
    			if (mainClass.equals(MenuItem.class)) {
    				if (name.equals("accelerator")) {
    					retType = String.class;
    				}
    			}
    			if (mainClass.equals(ScrolledComposite.class)) {
    				if (name.equals("minSize"))
    					retType = Point.class;
    				else if (name.equals("expandVertical"))
    					retType = boolean.class;
    				else if (name.equals("expandHorizontal"))
    					retType = boolean.class;
    			}
    			if (mainClass.equals(Dialog.class)) {
    				if (name.equals("size"))
    					retType = Point.class;
    			}
    			if (retType != null) {
    				if (retType.equals(int.class))
    					retType = Integer.class;
    				if (retType.equals(float.class))
    					retType = Float.class;
    				if (retType.equals(double.class))
    					retType = Double.class;
    				if (retType.equals(boolean.class))
    					retType = Boolean.class;
    				//propTypes.put(name, retType);
    			}
    			
    			//TODO the isCWT condition is just playing it safe - should
    			// probably remove it later!
    			if (!properties.containsKey(name)
    					|| (isCWT() && isSyntheticProperty(name))) {
    				try {
    					Object val = null;
    					if (mainClass.equals(ScrolledComposite.class)) {
    						if (name.equals("minSize"))
    							val = new Point(-1, -1);
    						else if (name.equals("expandVertical"))
    							val = Boolean.FALSE;
    						else if (name.equals("expandHorizontal"))
    							val = Boolean.FALSE;
    					}
    					if (val == null) {
        					val = invokeOnControl(gm.getName(), null);
    					}
    					
    					if (val == null && retType != null) {
    						if (retType.equals(Image.class)
    								|| retType.equals(java.awt.Image.class)) {
    							val = new ImageWrapper("No Image", this);
    						} else if (retType.equals(Cursor.class)) {
    							val = new SWTCursorWrapper(SWT.CURSOR_ARROW, this);
    						} else if (retType.equals(Icon.class)) {
    							val = new IconWrapper("No Icon", this);
    						} else if (retType.equals(String.class)) {
    							val = "";
    						} else if (retType.equals(Integer.class)) {
    							val = new Integer(0);
    						} else if (retType.equals(Boolean.class)) {
    							val = Boolean.FALSE;
    						} else if (jiglooPlugin.canUseSwing()
    								&& retType.equals(Insets.class)) {
    							val = new Insets(0, 0, 0, 0);
    						} else if (jiglooPlugin.canUseSwing()
    								&& retType.equals(java.awt.Color.class)) {
    							val = new java.awt.Color(0,0,0);
    						} else if (jiglooPlugin.canUseSwing()
    								&& retType.equals(Border.class)) {
    							val = new BorderPropertySource(null, this, name,
    									this);
    						} else if (jiglooPlugin.canUseSwing()
    						        && jiglooPlugin.getJavaVersion() > 13
    								&& retType.equals(FocusTraversalPolicy.class)) {
    							val = new FocusTraversalPolicyWrapper((Object)null, this);
    						} else {
    						    //what to do here?
    						}
    					}
    					
    					if (firstInit && isInMainTree()) {
    						Object def = val;

    						def = getDefaultPropertyValue(name, def);
    						
    						if(constructor != null && constructorParams != null) {
    							if(val != null && def != null && !val.equals(def)) {
    								if(isPhantomSetProperty(name))
    									continue;
    								for (int j = 0; j < constructorParams.length; j++) {
    									Object obj = constructorParams[j];
    									if(Cacher.isAssignableFrom(val.getClass(), obj.getClass())) {
    										setSetProperty(name);
    									}
    								}
    							}
    						}
    						
    						if(!isInherited()) {
    							if (def == null) {
    								defaultProps.put(name, "null");
    							} else {
    								defaultProps.put(name, def);
    							}
    						}
    						
    					}
    					
    					//                    System.out.println("initProperties: INVOKED " + gmName + ", val=" +
    					//                     val+", comp="+this);
    					Class propCls = null;
    					if (val instanceof Font) {
    						//if val != null, copy Font, Image or Color so that
    						// when disposing the Font, Color or Image (before a new one is set) we
    						// don't dispose the Font etc being used by other controls.
    						val = new Font(((Widget) control).getDisplay(),
    								((Font) val).getFontData());
    					} else if (val instanceof Image) {
    						val = new Image(((Widget) control).getDisplay(),
    								(Image) val, SWT.IMAGE_COPY);
    					} else if (val instanceof Color) {
    						val = new Color(((Widget) control).getDisplay(),
    								((Color) val).getRGB());
    					}
    					
    					if (val != null) {
    					    val = PropertySourceFactory.convertToPropertySource(val, this, name);
    						val = convertToWrapper(name, val);
    						putProperty(name, val);
    					}
    					
    					try {
    					    if(retType != null) {
    					        //v4.0M3
    					        PropertyEditor ped = PropertySourceFactory.findEditor(retType);
    					        if(ped != null) {
    					            IPropertyDescriptor pd = 
    					                PropertySourceFactory.getPropertyDescriptor(name,
    					                        val, retType, this);
    					            if(pd instanceof ClassPropertyDescriptor) {
    					                //ie, we couldn't find a jigloo-editor to handle this type
    					                setCustomPropertyEditor(name, ped);
    					            }
    					        } 
    					    }
    					} catch(Throwable t) {}
    	    			
    				} catch (Exception e) {
    					//                    System.err.println("ERROR invoking " + gmName + " on " +
    					//                     this +", " + e.toString());
    					//                    					e.printStackTrace();
    				}
    			} else {
    				//don't set layout or layoutData here, since they
    				//will be set by LayoutWrapper/LayoutDataWrapper
    				//(and the property values also may be inconsistent
    				//with the Wrapper values).
    				if (!name.equals("layout") && !name.equals("layoutData")) {
    					if (isPropertySet(name)) {
    						String code = getPropertyCode(name);
    						setPropertyValueDirect(name, properties.get(name));
    						if(code != null)
    							setPropertyValueCode(name, code);
    					} else {
    						putProperty(name, properties.get(name));
    					}
    				}
    			}
				String elemName = null;
				if(propertyStorage != null)
				    elemName = (String)propertyStorage.get("name");
				if(elemName == null)
				    elemName = getName();
    			Object resProp = getEditor().getBundleManager().getProperty(elemName, name, (Class)propTypes.get(name), null, this);
    			if(resProp != null) {
					setPropertyStorageValue(name, resProp);
//					if(propertyStorage != null)
//						propertyStorage.remove(name);
    			}

    		}

    		
    		if (firstInit) {
    			//set any properties here which may have been assigned before
    			//component/control was created (ie, while parsing java file)
    			if (propertyStorage != null) {
    				if (properties == null)
    					properties = new HashMap();
    				for (int i = 0; i < propertyStorageNames.size(); i++) {
    					String pName = (String) propertyStorageNames.elementAt(i);
    					Object val = propertyStorage.get(pName);
    					setPropertyValueDirect(pName, val);
    				}
    				propertyStorage.clear();
    				propertyStorageNames.clear();
    				propertyStorage = null;
    				propertyStorageNames = null;
    			} else {
    				initPropertiesFromNode();
    			}
    		}
    		propertyDescriptors = null;
    		propertiesInited = true;
    		initConstraints();
//    		if(!isInherited())
    		    updateUI();
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
//    	log(this+" FINISHED initProperties");
    }

    /**
	 * If a constructor such as new JButton("TEST") is parsed, then
	 * we want "text" to be the property that is recognized as being
	 * set, but "label" and "actionCommand" will also have non-default
	 * values and appear to be set - so need to recognize that here.
	 */
	private boolean isPhantomSetProperty(String name) {
		return hasProperty("text") && 
				("label".equals(name) || "actionCommand".equals(name));
	}

	private void addSynthProperties() {
        if (isSwing() && isSubclassOf(AbstractButton.class)) {
            addSynthProperty("buttonGroup", ButtonGroup.class, null);
        }
        if (isSwing() && parent != null && parent.isJTabbedPane()) {
            addSynthProperty("tabTitle", String.class, "");
            addSynthProperty("tabIcon", Icon.class, new IconWrapper((Icon)null, this));
        }
        if (isSubclassOf(javax.swing.Action.class)) {
            ActionStub.addSynthProperties(this);
        }
        if (isJFaceWizardDialog()) {
            addSynthProperty("pageSize", Point.class, new Point(-1, -1));
        }
        if (isJFaceApplicationWindow()) {
            ApplicationWindowManager.addSynthProperties(this);
        }

        //getSelection returns a control, but want selection to be an integer
        if(isA(CTabFolder.class)) {
            addSynthProperty("selection", int.class, new Integer(0));
        }
        if(isA(TabFolder.class)) {
            addSynthProperty("selection", int.class, new Integer(0));
        }
        
        if (isCWT()) {
            com.cloudgarden.jigloo.typewise.TypewiseManager
                    .addSynthProperties(this);
        }
    }
	
    public Object invokeOnBuilder(String method, Object[] params) {
    	methodObject = getBuilder();
    	Object obj = invokeOnControl(method, params);
    	methodObject = null;
    	return obj;
    }
    
    public Object invokeOnControl(Object methodObj, String method, Object[] params) {
        methodObject = methodObj;
        Object ret = invokeOnControl(method, params);
        methodObject = null;
        return ret;
    }
    
    public Object invokeOnControl(String method, Object[] params) {
        try {
            if (params == null)
                return invokeOnControl(method, (Class[])null, params);
            Class[] types = new Class[params.length];
            params = (Object[])params.clone();
            for (int i = 0; i < params.length; i++) {
                if(params[i] instanceof FormComponent) {
                    FormComponent fc = (FormComponent)params[i];
                    fc.setInMainTree(true);
                    if(!fc.isVisual()) {
                        Object nvo = fc.getNonVisualObject();
                        if(nvo == null) {
                            fc.populateNonVisualComponents(editor);
                        }
                        nvo = fc.getNonVisualObject();
	                    if(nvo != null)
	                        params[i] = nvo;
                    } else if(fc.isSwing()) {
                        if( isAbstractFormBuilder()) {
                            //this is supposed to be just for children of JGoodies Builders
                            fc.populateComponents((Container)getComponent(), getEditor());
                        }
                    	//this is for cases where a FC is passed in as a parameter
                    	if(fc.getComponent() != null)
                    		params[i] = fc.getComponent();
                    } else if(fc.isSWT()) {
                    	//this is supposed to be just for "children" of FormToolkits
                    	if(fc.getControl() != null)
                    		params[i] = fc.getControl();
                    }
                } else {
                    params[i] = ConversionUtils.convertParamToObject(params[i], false);
                }
                if(params[i] != null) {
               		types[i] = params[i].getClass();
                }
            }
            return invokeOnControl(method, types, params);
        } catch (Throwable e) {
//            if(e instanceof InvocationTargetException)
//                System.err.println("Error invoking "+method+" on "+this+": "
//                        +((InvocationTargetException)e).getCause());
//            else
//                System.err.println("Error invoking "+method+" on "+this+": "+e);
            return null;
        }
    }

    private Object invokeOnControl(String method, Class[] types, Object[] params)
            throws Exception {

        Method meth = null;
        if (isSwing() && method.equals("setSize") && isRoot()) {
            //this will happen if this is the root component, and it is a JFrame, 
        	//or something that doesn't have preferredSize as one of it's
            //properties but in fact the displayed component is a JPanel.
            method = "setPreferredSize";
        }

        try {
            if (method.equals("setAccelerator")) {
                if (params[0] instanceof KeyStroke) {
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("GOT KEYSTROKE " + params[0]
                                + " for setAccelerator " + this);
                } else {
                    types[0] = int.class;
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("BEFORE=" + params[0]);
                    params[0] = new Integer(Action
                            .convertAccelerator((String) params[0]));
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("AFTER=" + params[0]);
                }
            }
            meth = getRawMethod(method, types, params);
            if(meth == null)
                return null;
        } catch (NoSuchMethodException e) {
//            System.err.print("INVOKE ON CONTROL, comp=" + this+", no method " + method+" [ ");
//            if(types != null) {
//                for (int i = 0; i < types.length; i++) {
//                    if(i != 0)
//                        System.err.print(", ");
//                    System.err.print(types[i].getName());
//                }
//            }
//            System.err.println(" ]");
            return null;
        }
        Object o = invokeOnControl(meth, params);
//        if (params != null && params.length > 0)
//        	System.err.println("INVOKED " + meth + ", param=" + params[0]+", "+this);
        return o;
    }

    private Object syncObj = new Object();
    private Object returnValue = null;
    private Method invMethod = null;
    private Thread invThread = null;
    private FormComponent invFC = null;
    private Object[] invParams = null;
    private Exception timeoutException = null;
    
    private Object invokeOnControl(Method method, Object[] params)
    throws InvocationTargetException, IllegalAccessException {
        params = unescape(params);
        if(methodObject != null) {
            return method.invoke(methodObject, params);
        } else if (nonVisualObject != null) {
            //System.out.println("INVOKED ON NVO "+nonVisualObject+",
            // "+method+", "+params);
            return method.invoke(nonVisualObject, params);
        } else if (component != null || getBuilder() != null) {
            if (method.getName().equals("setVisible")
                    && (isJPopupMenu() || isJApplet() || isJDialog() || isJFrame() || isJWindow()))
                return null;
            //            if(isRoot()) {
            //                System.out.println("INVOKED ON CONTROL "+this+","+method+", "+params);
            //            }
            params = ConversionUtils.convertToAWT(params);
            
            if(jiglooPlugin.useTimeouts()) {
                Object rv = null;
                synchronized (syncObj) {
                    try {
                        invMethod = method;
                        invParams = params;
                        invFC = this;
                        timeoutException = new Exception("Timed-out while calling method "+method.getName()+" on "+this);
                        returnValue = timeoutException;
                        
                        if(invThread == null) {
//                            System.out.println("NEW INVOKE THREAD");
                            invThread = new Thread() {
                                public void run() {
                                    while(invThread.equals(this)) {
                                        try {
                                            returnValue = invMethod.invoke(
                                                    invFC.getObjectForMethod(invMethod.getName()), invParams);
                                        } catch (Throwable e) {
                                            returnValue = e;
                                        }
                                        try {
//                                            Method m1 = invMethod;
                                            synchronized (syncObj) {
                                                syncObj.notifyAll();
//                                                System.out.println("STOP THREAD "+this+", "+m1+", "+invMethod);
                                                syncObj.wait();
//                                                System.out.println("GO THREAD "+this+", "+m1+", "+invMethod);
                                            }
                                        } catch (InterruptedException e1) {
//                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            };
                            invThread.start();
                        }
                        
                        syncObj.notifyAll();
//                        System.out.println("STOP MAIN "+invMethod);
                        syncObj.wait(500);
                        rv = returnValue;
//                        System.out.println("GO MAIN "+invMethod);
                        
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if(rv instanceof InvocationTargetException) {
                        throw (InvocationTargetException)rv;
                    } else if(rv instanceof IllegalAccessException) {
                        throw (IllegalAccessException)rv;
                    } else if(timeoutException.equals(rv)) {
                        //kill whatever process is in an infinite loop
                        System.out.println("TIMEOUT!!!!!!!!!!!!!!!"+this+", "+method.getName());
                        invThread.stop();
                        invThread = null;
                        MessageDialog.openWarning(getShell(), "Timeout calling "+method.getName(),
                                "Jigloo timed out while calling the method "+method.getName()+" on "+this+
                                "\n\nPlease check for the possibility of infinite loops." +
                                "\n\nIf needed, you can use java.beans.Beans.isDesignTime() to determine" +
                                "\nwhen Jigloo is calling "+method.getName()+", and act appropriately.");
                        rv = null;
                    }
                }
                return rv;
            } else {
                Object ret = method.invoke(getObjectForMethod(method.getName()), params);
                return ret;
            }
            
        } else if (getControl() != null) {
            String mName = method.getName();
            if (mName.equals("setVisible") && isSubclassOf(Shell.class))
                return null;
            if(isJFaceViewer()) {
//            	if (mName.equals("setInput"))
//        		return null;
//                if(params == null)
//                    return null;
            	if (mName.equals("setInput") 
            	        || mName.equals("setContentProvider")
            	        || mName.equals("setLabelProvider")
            	        )
                    return method.invoke(jfaceParent, params);
                return method.invoke(getControl(), params);
//            		return null;
            }
            try {
                return method.invoke(getControl(), params);
            } catch (Exception e) {
                //				System.err.println(
                //					e.toString()
                //						+ "\nerror invoking "
                //						+ method
                //						+ " on "
                //						+ control);
                //				if (params != null) {
                //					Object p0 = params[0];
                //					System.err.println(
                //						"params[0] = " + p0 + ", " + p0.getClass());
                //				}
                //e.printStackTrace();
            }
        } else { //			System.err.println(
            //				"invokeOnControl - no control/component, "
            //					+ method
            //					+ ", "
            //					+ this);
            //new Exception().printStackTrace();
        }
        return null;
    }

    public Object[] unescape(Object[] params) {
        if (params == null)
            return null;
        Object[] nps = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object o = params[i];
            if (o instanceof String) {
                String str = (String) o;
//                str = JiglooUtils.replace(str, "\\t", "\t");
//                str = JiglooUtils.replace(str, "\\n", "\n");
//                str = JiglooUtils.replace(str, "\\r", "\r");
//                str = JiglooUtils.replace(str, "\\\"", "\"");
//                str = str.replaceAll("\\\\\\\\", "\\\\");
                nps[i] = str;
            } else {
                nps[i] = params[i];
            }
        }
        return nps;
    }

    public boolean isAWTControl() {
        if (getControl() instanceof AWTControl)
            return true;
        else
            return false;
    }

    public boolean isContainer() {
    	if(isRoot())
    		return true;
    	
        if (isCWT()) {
            return com.cloudgarden.jigloo.typewise.TypewiseManager.isContainer(this);
        }
        if(Boolean.FALSE.equals(getBeanDescriptorValue(getMainClass(), "isContainer")))
            return false;
        if(Boolean.TRUE.equals(getBeanDescriptorValue(getMainClass(), "isContainer")))
            return true;

        if (jiglooPlugin.canUseSwing() && isSwing()) {
            if (isSubclassOf(TextComponent.class))
                return false;
            if (isSubclassOf(JTextComponent.class))
                return false;
            if (isSubclassOf(java.awt.Canvas.class))
                return true;
            if (isSubclassOf(Container.class))
                return true;
            if(isAbstractFormBuilder())
            	return true;
            return false;
        }
        if (isA(CCombo.class))
            return false;
        if (isJFaceViewer())
            return true;
        if (isSubclassOf(Display.class))
            return true;
        if (isSubclassOf(Window.class))
            return true;
        if (isSubclassOf(Composite.class))
            return true;
        if (isSubclassOf(Dialog.class))
            return true;
        if (isSubclassOf(Menu.class))
            return true;
        if (isSubclassOf(MenuItem.class))
            return true;
        if (isSubclassOf(CTabItem.class))
            return true;
        if (isA("org.eclipse.swt.widgets.ExpandItem"))
            return true;
        if (isSubclassOf(TabItem.class))
            return true;
        if (isSubclassOf(CoolItem.class))
            return true;
        if (isSubclassOf(TreeItem.class))
            return true;
        if (isSubclassOf(TableTreeItem.class))
            return true;
        if (ClassUtils.isCWTApplicationModel(getMainClass()))
            return true;
        if (ClassUtils.isCWTWidget(getMainClass()))
            return true;
        return false;
    }
    
    public Object getRawPropertyValue(Object id, Class expectedClass) {
        Object val = getRawPropertyValue(id);
        if(val == null)
            return null;
        if(val.getClass().equals(expectedClass))
            return val;
        jiglooPlugin.writeToLog("Expected a "+expectedClass + " for "+id+" but got a "+val.getClass()+" : "+val);
        return null;
    }
    
    public Object getRawPropertyValue(Object id) {
        return convertToRawSWTProperty(getPropertyValue(id));
    }

    public Object convertToRawSWTProperty(Object value) {
        value = ConversionUtils.convertToRawValue(value, getControl());
        value = ConversionUtils.convertToSWT(value);
        return value;
    }

    private Object getCustomPropertyValue(Object id, boolean asText) {
    	initCustomProperties();
        PropertyEditor ped = getCustomPropertyEditor(id);
        if (ped != null) {
            try {
                if (ped == null)
                    return null;
                Object val = queryProperty((String)id);
                //                Object val = ped.getValue();
                if(val == null)
                    return null;
                ped.setValue(val);
                try {
                    if (asText) {
                        return ped.getAsText();
                    }
                } catch(Throwable t) {
                    System.err.println("Error in getCustomPropertyValue getAsText "+id+", error="+t);
                    try {
                        return ped.getValue().toString();
                    } catch(Throwable t2) {     }
                }
                return val;
            } catch (Throwable e) {
                jiglooPlugin.handleError(e);
            }
        }
        return null;
    }

    /**
     * Place to handle synthetic properties. If a synth property is not handled here, return false
     * @param id
     * @return
     */
    private boolean setSynthPropertyValue(Object id, Object value) {
        if (id.equals("tabTitle")) {
            setTabTitle((String) value);
            setSetProperty("tabTitle");
            setPropertyValueCode("LAYOUT_CONSTRAINT", null);
            repairParentConnectionInCode();
            return true;
        }
        if(isSubclassOf(SyntheticPropertyContainer.class)) {
        	return ((SyntheticPropertyContainer)getObject(true)).setSynthProperty(this, (String)id, value);
        }
        return false;
    }
    
    /**
     * Place to handle synthetic properties. If a synth property is not handled here, return null
     * @param id
     * @return
     */
    private Object getSynthPropertyValue(Object id) {
        if ("tabTitle".equals(id)) {
            String tt = getTabTitle();
            if (tt == null)
                return "";
            return tt;
        }
        return null;
    }
    
    public Object getPropertyValue(Object id) {
        if (id == null)
            return null;
        
        if(isSyntheticProperty((String) id)) {
        	Object val = getSynthPropertyValue(id);
        	if(val != null)
        		return val;
        }

        Object ob = getCustomPropertyValue(id, true);
        if (ob != null)
            return ob;
        if (properties == null) {
            if (propertyStorage != null)
                ob = propertyStorage.get(id);
            if (ob == null)
                return null;
        } else {
            ob = properties.get((String) id);
        }
        Object ps = PropertySourceFactory.convertToPropertySource(ob, this, id);
        if (ps != null)
            return ps;
        return null;
    }

    public Class getPropType(Object propName) {
        return getPropType(propName, false);
    }
        
    public Class getPropType(Object propName, boolean allowPrimitive) {
        if (propTypes == null)
            propTypes = PropertySourceFactory.findPropertyTypes(getMainClass());
        Class type = (Class) propTypes.get(propName);
        if(!allowPrimitive && type != null) {
        	type = ConversionUtils.convertPrimitiveClassToObjectClass(type);
        }
        return type;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        try {
            //System.out.println("getPropDescs " + this+", "+propNames);
            //String[] names = getPropertyNames();
            if (propertyDescriptors != null)
                return propertyDescriptors;
            if (propNames == null)
                return new IPropertyDescriptor[0];
            initCustomProperties();
            Vector pds = new Vector();
            int unhandledCnt = 0;
            //System.err.println(
            //"PROP NAMES SIZE = " + propNames.size() + ", " + this);
            FormPropertyDescriptor pd = null;
            for (int i = 0; i < propNames.size(); i++) {
                String name = (String) propNames.elementAt(i);
//                System.err.println("PROP NAME = " + name + ", " + this);
                PropertyEditor ped = getCustomPropertyEditor(name);

                if (ped != null) {
                    PropertyDescriptor bpd = 
                        (PropertyDescriptor) beanPropDescs.get(name);
                    //System.err.println("BEAN PROP DESC = " + bpd + ", " +
                    // this);
                    if (ped != null) {
                        String dname = name;
                        String pname = name;
                        if(bpd != null) {
                            dname = pname = JiglooUtils.deCapitalize(bpd.getName());
                        }
                        String[] tags = ped.getTags();
                        if(ped.supportsCustomEditor()) {
                            pd = new CustomEditorDescriptor(pname, dname, ped, this);
                        } else if (tags != null) {
                            pd = new ChoicePropertyDescriptor(pname, dname,
                                    this, ped, this);
                        } else {
                            pd = new TextFormPropertyDescriptor(pname, dname,
                                    this);
                        }
                        PropertySourceFactory.setCategory(this, pd, name, "Basic");
                    } else {
//                        jiglooPlugin.writeToLog("No property editor for "
//                                + getNameInCode() + "." + name);
                    }
                }

                if (ped == null) {
                    Class type = getPropType(name);
                    Object prop = getPropertyValue(name);
                    pd = PropertySourceFactory.getPropertyDescriptor(name,
                            prop, type, this);
                }
                
                //Don't include PDs for Objects which do not have a specific
                // PD.
                if (pd != null
                        && !pd.getClass().equals(FormPropertyDescriptor.class)) {
                    pds.add(pd);
                } else {
                    //System.out.println("GOT FPD FOR PROP "+name+",
                    // "+getPropertyValue(name)+", "+this);
                }
            }
            propertyDescriptors = new IPropertyDescriptor[pds.size()];
            pds.copyInto(propertyDescriptors);
        } catch (Exception e) {
            System.err.println("Error getting propDescs for " + this);
            e.printStackTrace();
            return null;
        }
        return propertyDescriptors;
    }

//    public void checkParsing() {
//        if (editor != null)
//            editor.checkParsing();
//    }

    public boolean isField(Object id) {
        if (fieldNames == null)
            return false;
        return fieldNames.contains(id);
    }

    public Object getFieldValue(String fieldName) {
        Object obj = null;
        if (nonVisualObject != null)
            obj = nonVisualObject;
        if (component != null)
            obj = component;
        if (control != null)
            obj = control;
        if(obj == null) {
        	obj = getObject(true);
//        	System.out.println("Creating object in getFieldValue "+this+":"+obj);
        }
        return JiglooUtils.getFieldValue(obj, fieldName);
    }

    public void unsetPropertyValue(Object id) {
        if (setProps != null)
            setProps.remove(id);
    }

    public Object getDefaultPropertyValue(Object id) {
    	return getDefaultPropertyValue(id, null);
    }

   	private Object getDefaultPropertyValue(Object id, Object guess) {
        Object def = null;
        if (defaultProps != null)
            def = defaultProps.get(id);
        if("null".equals(def))
            def = null;
        if(isInherited())
        	return def;
        Class mc = getMainClass();
        if(hasUnmakeableSuperClass())
            mc = getRootClass();
        Object def2 = DefaultValueManager.getDefault((String) id,mc, guess);
        if (def2 != null)
            def = def2;
        return def;
    }
    
    public boolean isPropertySet(Object id) {
        return isPropertySet(id, true);
    }
    
    public Vector getSetProperties() {
    	return setProps;
    }
    
    public boolean isPropertySet(Object id, boolean useSetProps) {
    		
        if (id.equals("layout"))
            return getLayoutWrapper().isSet();

        if (id.equals("layoutData")) {
            //if(layoutDataWrapper == null)
            //return false;
            //return layoutDataWrapper.isSet();
            return setProps.contains(id);
        }
        /*
         * if (getParent() == null || isItemWidget() || isMenuComponent())
         * return false; String lt = getParentLayoutType(); if
         * (layoutDataWrapper != null && layoutDataWrapper.getLayoutData() !=
         * null) //if ("Row".equals(lt) || "Grid".equals(lt) ||
         * "Form".equals(lt)) return true; return false; }
         */

        //for a tab folder, say, setSelection(0) should not be treated
        //as default code and removed (even if selection == 0 by default)
        if("selection".equals(id) && (isSubclassOf(CTabFolder.class) ||
                isSubclassOf(TabFolder.class)))
            return true;
        
        if (!hasProperty((String) id)) {
            if (extraPropCode == null)
                return false;
            return extraPropCode.containsKey(id);
        }

        if (propertyValueCode != null && propertyValueCode.containsKey(id))
            return true;

        if (useSetProps) {
            if (setProps == null)
                return false;
            if (!setProps.contains(id))
                return false;
        }

        Object def = null;
        if( id.equals("size") && !isInherited()  && isSubclassOf(Composite.class)) {
            def = DefaultValueManager.getDefault("size", Composite.class);
        } else {
            def = getDefaultPropertyValue(id);
        }
        Object val = getPropertyValue(id);

//        if(isInherited() && id.equals("text")) {
//            System.out.println("IS PROP SET "+def+", "+val+", "+this);
//        }
        if(id.equals("size") && def == null && val != null)
            return true;
        
        if (def == null && val == null)
            return false;
        if(val instanceof FormComponent) {
        	if(def instanceof FormComponent)
        		return def.equals(val);
        	if(def == null)
        		return true;
        }
       	def = ConversionUtils.convertToRawValue(def, control);
        val = ConversionUtils.convertToRawValue(val, control);

        if (def == null && val == null)
            return false;

        if (def != null) {
            return !JiglooUtils.equals(def, val);
        }

        if (setProps == null)
            return false;
        return setProps.contains(id);
    }

    //	public boolean isPropertySet(Object id) {
    //		if (setProps == null)
    //			return false;
    //		if (id.equals("layout"))
    //			return getLayoutWrapper().isSet();
    //		return setProps.contains(id);
    //	}

    public void resetPropertyValue(Object id) {
        resetPropertyValue(id, true);
    }

    public void resetPropertyValue(Object id, boolean setDirtyAndUpdate) {
//        System.err.println("RESET "+id+", "+this);
        if (isCWT()
                && !com.cloudgarden.jigloo.typewise.TypewiseManager
                        .canResetProperty(this, id))
            return;

        if ("layout".equals(id)) {
            if (layoutWrapper != null)
                layoutWrapper.reset();
            if (setProps != null)
                setProps.remove(id);
            return;
        }
        if ("layoutData".equals(id)) {
            if (layoutDataWrapper != null)
                layoutDataWrapper.reset();
            if (setProps != null)
                setProps.remove(id);
            return;
        }

        if (defaultProps == null)
            return;
        try {
            Object def = getDefaultPropertyValue(id);

            if (!isSwing()) {
                if (id.equals("size")) {
                    def = new Point(SWT.DEFAULT, SWT.DEFAULT);
                } else if (id.equals("bounds")) {
                    def = new Rectangle(SWT.DEFAULT, SWT.DEFAULT, SWT.DEFAULT,
                            SWT.DEFAULT);
                }
            }

            Class cls = getPropType((String)id);
//            System.err.println("RESET PROP VALUE " + this +", " + id + ", " + def);
            if (def != null) {

                if (!String.class.equals(cls)) {
                    if ("null".equals(def))
                        def = null;
                    //				if ("No Icon".equals(def.toString())
                    //					|| "No Image".equals(def.toString())
                    //					|| "No Font".equals(def.toString())
                    //					|| "No Border".equals(def.toString()))
                    //					def = null;
                }

                Object existingValue = getRawPropertyValue(id);
                if(id.equals("preferredSize") && isSwing() && component instanceof JComponent) {
                    existingValue = ((JComponent)component).getPreferredSize();
                }
                if (isSwing())
                    existingValue = ConversionUtils.convertToAWT(existingValue, getPropType(id));
                String defVal = "";
                if (def != null)
                    defVal = def.toString();
                boolean alreadyReset = ((existingValue == null && (def == null
                        || "No Icon".equals(defVal)
                        || "No Image".equals(defVal)
                        || "No Font".equals(defVal) || "No Border"
                        .equals(defVal))) || (def != null && propsEqual(def,
                        existingValue)));
                boolean ignoreSwing = (id.equals("debugGraphicsOptions")
                        || id.equals("layout") || id.equals("layoutData")
//                        || id.equals("size") || id.equals("bounds")
                        )
                        && isSwing();
                boolean ignoreSWT = (id.equals("selection")
                        || id.equals("topItem") || id.equals("control"))
                        && !isSwing();
                if (alreadyReset || ignoreSwing || ignoreSWT)
                    return;
//                System.err.println("RESETTING PROP " + def + ", " +
//                 existingValue + ", " + id + ", " + this);
                //new Exception().printStackTrace();
                if (id.equals("preferredSize") || id.equals("size") || id.equals("bounds")) {
                    if (isSwing()) {
                        if(!id.equals("bounds"))
                            setPropertyValueDirect(id, null);
                        putProperty(id, def);
                        if (component instanceof JComponent) {
                            JComponent jc = (JComponent)component;
                            jc.setPreferredSize(null);
                        }

                        setProps.remove(id);
                        if (!propsNeedingCodeUpdate.contains(id))
                            propsNeedingCodeUpdate.add(id);
                        setPropertyValueCode(id, null);
                        updateInCode((String) id);
                        if (setDirtyAndUpdate)
                            editor.setDirtyAndUpdate();
                        return;
                    } else {
                    	if(id.equals("bounds"))
                    		id = "size";
                    	def = new Point(SWT.DEFAULT, SWT.DEFAULT);
                    }
                }

                if (!isRoot() || (!id.equals("size") && !id.equals("bounds"))) {
                    setPropertyValueDirect(id, def);
                }

            }

            if (isRoot() && (id.equals("size") || id.equals("bounds"))) {
//                System.err.println("RESET ROOT BOUNDS");
//                if (isSwing()) {
//                    setPropertyValueDirect(id, null);
//                    putProperty(id, def);
//                    if (component instanceof JComponent) {
//                        Container par = ((JComponent) component).getTopLevelAncestor();
//                        if(par instanceof JFrame)
//                            ((JFrame)par).pack();
//                    }
//                } else {
//                    def = new Point(SWT.DEFAULT, SWT.DEFAULT);
//                }
//                editor.refresh(false);
            }
            
            setProps.remove(id);
            setPropertyValueCode(id, null);
            if (setDirtyAndUpdate) {
                if (!propsNeedingCodeUpdate.contains(id))
                    propsNeedingCodeUpdate.add(id);
                updateInCode((String) id);
                editor.setDirtyAndUpdate();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private boolean propsEqual(Object o1, Object o2) {
        if (o1 instanceof Font && o2 instanceof Font) {
            Font f1 = (Font) o1;
            Font f2 = (Font) o2;
            return f1.getFontData()[0].equals(f2.getFontData()[0]);
        } else {
            if(o1 != null && o2 != null 
                    && !o1.getClass().equals(o2.getClass()))
                return false;
            return o1.equals(o2);
        }
    }

    private Object checkBorderValue(Object id, Object value) {
        if (id.equals("border")) {
            if (value instanceof String) {
                try {
                    value = getEditor()
                            .loadClass("javax.swing.border." + value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (value instanceof Class) {
                BorderPropertySource bps = null;
                Object val = getPropertyValue("border");
                if (val instanceof BorderPropertySource)
                    bps = (BorderPropertySource) val;
                if (bps != null && bps.getValue() != null) {
                    if (value.equals(bps.getValue().getClass()))
                        return null;
                }
                value = BorderPropertySource.createBorder(value);
            }
        }
        return value;
    }

    public void setPropertyValueDirect(Object id, Object value) {
        setPropertyValue(id, value, true);
    }

    public void setPropertyValue(Object id, Object value) {
    	
    	//getEditor will return null if the code has been reparsed and we are trying to set the property
    	//of a "dead" FC.
    	if(editor == null)
    		return;
    	
//        System.err.println("setPropVal (1) " + id + ", " + value);
    	if(editor != null && editor.getFormView() != null) {
    		if(editor.getFormView().isSettingSelection())
    			return;
    	}
        if (editor == null || editor.isProcessing()) {
            setPropertyValue(id, value, true);
        } else {
        	//ChangeablePropertySource caused problems with multi-selections
        	//since the same ChangeablePropertySource could be used for all
        	//selected elements, and wasChanged would return true for 1st only
            if(value instanceof ChangeablePropertySource) {
                ChangeablePropertySource cps = (ChangeablePropertySource)value;
                if(cps.hasBeenChanged(this))
                    return;
                cps.changeApplied(this);
            } else {
            	Object propVal = getPropertyValue(id);
            	
            	if(jiglooPlugin.ACCOMODATE_ALL_CLASS_PROPERTIES
            			&& value instanceof ClassWrapper) {
            		ClassWrapper cw = (ClassWrapper)value;
            		FormComponent nfc = cw.getFormComponent();
            		FormComponent ofc = null;
            		if(propVal instanceof FormComponent)
            			ofc = (FormComponent)propVal;
            		if(ofc == null && nfc == null)
            			return;
            		if(nfc != null && nfc.equals(ofc))
            			return;
            		if(nfc != null)
            			value = nfc;
            		
            	} else {
		            Object oldVal = convertToRawSWTProperty(propVal);
		            Object newVal = convertToRawSWTProperty(value);
		//            System.err.println("set prop "+id+", oldVal " + oldVal + ", newVal " + newVal);
		
		            if (isSwing() && oldVal instanceof Border 
		            		&& newVal instanceof Class) {
		                if(oldVal.getClass().equals(newVal))
		                    return;
		            } else if (value instanceof BorderPropertySource) {
		//            } else if (oldVal instanceof BorderPropertySource) {
		//                 if(!((BorderPropertySource)oldVal).hasChanged())
		//                	return;
		//                ((BorderPropertySource)oldVal).setHasChanged(false);
		            } else {
		                
		                //if this property uses a custom editor, oldVal will be equal to newVal since
		                //getPropertyValue queries the property editor (which will already have
		                //been updated) - so set oldVal to null so we won't bail at this point.
		                if(getCustomPropertyEditor(id) != null) {
		                    oldVal = null;
		                }
		                
		                if(oldVal != null && oldVal.equals(newVal))
		                    return;
		            } 
            	}
            }
//            System.err.println("setPropVal (2) " + id + ", " + value);
            UndoablePropertyAction upa = new UndoablePropertyAction(this,
                    getPropertyValue(id), value, isPropertySet(id), id);
            editor.executeUndoableAction(upa);
            //if a property gets set, discard original property-value code
            //setPropertyValueCode(id, null);
        }
    }

    public boolean needsUpdateInCode(String propName) {
        return propsNeedingCodeUpdate.contains(propName);
    }

    public void setNeedsUpdateInCode(String propName) {
        if(!needsUpdateInCode(propName))
            propsNeedingCodeUpdate.add(propName);
    }

    public void setPropertyValue(Object id, Object value, boolean hardSet) {
//        System.err.println("setPropVal (2) " + id + ", " + value);
    	initCustomProperties();
        if (beanPropEds.containsKey(id)) {
            try {
                PropertyEditor ped = getCustomPropertyEditor(id);
                if (ped != null) {

                    if(value instanceof ArrayHolder)
                        value = ((ArrayHolder) value).getRawArray();
                    
                    try {
                        if (value instanceof String) {
                            ped.setAsText((String) value);
                            Class type = getPropType(id);
                            if(String.class.equals(type) && !value.equals(ped.getAsText())) {
                                ped.setValue(value);
                            }
                        } else {
                            ped.setValue(value);
                        }
                    } catch(Throwable t) {}
                    value = ped.getValue();
                }
                //System.err.println("SETTING PROP VALUE FOR PROPERTY EDITOR");
            } catch (Exception e) {
                jiglooPlugin.handleError(e, "Error setting custom property "
                        + id + " to " + value + ", for " + this);
            }
        }

        String plt = getParentSuperLayoutType();
        if (!propertiesInited) {
            setPropertyValueInternal(id, value, hardSet);
        } else {

            if ("bounds".equals(id)) {
                Point size;
                Rectangle bounds = null;
                bounds = (Rectangle) convertToRawSWTProperty(value);
                size = new Point(bounds.width, bounds.height);
                if ((isSWT() && "Form".equals(plt)) || "Anchor".equals(plt)) {
                    setFormLayoutBounds((Rectangle) convertToRawSWTProperty(value));
                    if (isSwing()) {
                        if (hasProperty("preferredSize")) {
                            setPropertyValueInternal("preferredSize", size,  hardSet, false);
                            unsetProperty("size");
                            unsetProperty("bounds");
                        }
                    } else if (isSWT()) {
                        setPropertyValueInternal("layoutData", size, hardSet);
                        unsetProperty("size");
                        unsetProperty("bounds");
                    }
                    return;
                }

                if(OpenSwingHelper.setBounds(this, size))
                    return;
                
                if (isA(CoolItem.class)) {
                    CoolItem cit = (CoolItem) getControl();
                    size = cit.computeSize(-1, size.y);
                    setPropertyValueInternal("preferredSize", size, hardSet,  false);
                    setPropertyValueInternal("minimumSize", size, hardSet,  false);
                    setPropertyValueInternal("size", size, hardSet, true);
                } else if (getParent().isA("org.eclipse.swt.widgets.ExpandItem")) {
                    getParent().setPropertyValueInternal("height", new Integer(size.y), hardSet,  false);
                } else {
                    if (isSwing()) {
                        if (parent.usesAbsoluteTypeLayout()) {
                            unsetProperty("size");
                            unsetProperty("preferredSize");
                            setPropertyValueInternal("bounds", value, hardSet,  false);
                        } else if (isJFrame() || isJApplet() || isJDialog() || isJWindow()) {
                            boolean boundsSet = isPropertySet("bounds");
                            boolean sizeSet = isPropertySet("size");
                            boolean psizeSet = isPropertySet("preferredSize");
                            if(boundsSet) {
                                setPropertyValueInternal("bounds", value, hardSet);
                            }
                            if( psizeSet && hasProperty("preferredSize")) {
                                setPropertyValueInternal("preferredSize", size, hardSet);
                            }
                            setPropertyValueInternal("size", size, hardSet);
                            //add in setSize so that (even if preferredSize is a property - Java 1.5) then the code is 1.4 compatible
                            if(!sizeSet && boundsSet)
                                setProps.remove("size");
                        } else if (isJInternalFrame()) {
                            setPropertyValueInternal("preferredSize", size, hardSet);
                            setPropertyValueInternal("bounds", value, hardSet);
                        } else {
                            if (hasProperty("preferredSize"))
                                setPropertyValueInternal("preferredSize", size, hardSet);
                            else if (hasProperty("size"))
                                setPropertyValueInternal("size", size, hardSet);
                            else if (hasProperty("bounds"))
                                setPropertyValueInternal("bounds", value, hardSet);
                        }
                    } else if (isCWT()) {
                        setPropertyValueInternal(id, value, hardSet);
                    } else if (isSWT()) {
                        if (isRoot()) {
                            setPropertyValueInternal("size", size, hardSet);
                            unsetProperty("bounds");
                            unsetProperty("layoutData");
                        } else if (plt == null || "Absolute".equals(plt)) {
                            setPropertyValueInternal("bounds", value, hardSet);
                            unsetProperty("size");
                            unsetProperty("layoutData");
                        } else if (MigLayoutHandler.handlesLayout(getParent())) {
                        	MigLayoutHandler.setBounds(this, bounds, false);
                        } else if(layoutDataWrapper.usesSimpleConstraint()){
                        	System.out.println("UNABLE TO SET BOUNDS FOR SIMPLE CONSTRAINT "+value);
                        } else {
                            setPropertyValueInternal("layoutData", size,hardSet);
                            unsetProperty("size");
                            unsetProperty("bounds");
                        }
                    }
                }
            } else {
                setPropertyValueInternal(id, value, hardSet);
            }
        }
        if (isA(Button.class) || isA(Label.class)) {
            if ("text".equals(id) && "".equals(value)) {
                setPropertyValueInternal("image", getPropertyValue("image"),
                        false, true);
            }
        }
    }

    public void unsetProperty(String id) {
        setProps.remove(id);
        setPropertyValueCode(id, null);
        if (!propsNeedingCodeUpdate.contains(id))
            propsNeedingCodeUpdate.add(id);
    }

    public Rectangle getBoundsOnScreen() {
        if (isSwing()) {
            Control root = getEditor().getViewportControl();
            Point loc = root.toDisplay(0, 0);
            Rectangle rb = getBoundsRelativeTo(null);
            rb.x += loc.x;
            rb.y += loc.y;
            return rb;
        } else {
            Point loc = ((Control) control).toDisplay(0, 0);
            Rectangle b = ((Control) control).getBounds();
            b.x = loc.x;
            b.y = loc.y;
            return b;
        }
    }

    private void setFormLayoutBounds(Rectangle bounds) {
        setFormLayoutBounds(bounds, true, -1, true, "null");
    }

    public void setBounds(Rectangle bounds, boolean hardSet) {
        setBounds(bounds, hardSet, false);
    }

    public void setBounds(Rectangle bounds, boolean hardSet,
            boolean convertIfNeeded) {
        setBounds(bounds, hardSet, convertIfNeeded, false);
    }
        
    public void setBounds(Rectangle bounds, boolean hardSet,
            boolean convertIfNeeded, boolean shiftDown) {
    	setBounds(bounds, hardSet, convertIfNeeded, shiftDown, false);
    }

    public void setBounds(Rectangle bounds, boolean hardSet,
                boolean convertIfNeeded, boolean shiftDown, boolean parentChanged) {
        clearCachedBounds();
//        System.err.println("SET BOUNDS " + bounds + ", " + this);
    	
        if (convertIfNeeded && isCWT()) {
            bounds = com.cloudgarden.jigloo.typewise.TypewiseManager
                    .convertToGridBounds(bounds, this);
        }
        String id = "bounds";
        if (isRoot()) {
            bounds.x = 0;
            bounds.y = getEditor().getMenuBarHeight();
        }
        if (isCWT()) {
            //TODO should be a better way than calling repairConstructorInCode
            if (!hardSet)
                setPropertyValue(id, bounds, false);
            else
                setPropertyValue(id, bounds);
            //set these properties *after* bounds, since (inside setPropertyValueInternal)
            //they will set "bounds" and so when bounds is eventually set
            //it will not appear to have been changed, and setDirtyAndUpdate
            //will not be called
            setPropertyValue("rows", new Integer(bounds.width), false);
            setPropertyValue("columns", new Integer(bounds.height), false);
            setPropertyValue("topRow", new Integer(bounds.x), false);
            setPropertyValue("leftColumn", new Integer(bounds.y), false);
            repairConstructorInCode();
            //getEditor().redrawRoot();
            //System.out.println("SET CWT BOUNDS FOR " + this +", " + bounds);
            //getEditor().realignWindowFrames();
            return;
        }
        
        if (isSwing() && !shiftDown && getParent() != null) {
            if ("GridBag".equals(getParentLayoutType())) {
            	if(getParent().gridCoordsChanged() || parentChanged) {
            		LayoutDataWrapper ldw = getLayoutDataWrapper();
            		int gx = ldw.getIntProperty("gridx");
            		int gy = ldw.getIntProperty("gridy");
            		int gw = ldw.getIntProperty("gridwidth");
            		int gh = ldw.getIntProperty("gridheight");
            		ldw = ldw.getCopy(this);
            		if(parentChanged) {
                		ldw.setPropertyValue("gridx", new Integer(getParent().gridValues[0]));
                		ldw.setPropertyValue("gridy", new Integer(getParent().gridValues[1]));
                		ldw.setPropertyValue("gridwidth", new Integer(1));
                		ldw.setPropertyValue("gridheight", new Integer(1));
            		} else {
            			ldw.setPropertyValue("gridx", new Integer(gx
            					+ getParent().gridValues[4]));
            			ldw.setPropertyValue("gridy", new Integer(gy
            					+ getParent().gridValues[5]));
            			if (getParent().gridValues[2] != GRIDBAG_UNDEFINED) {
            				ldw.setPropertyValue("gridwidth", new Integer(gw
            						+ getParent().gridValues[6]));
            				ldw.setPropertyValue("gridheight", new Integer(gh
            						+ getParent().gridValues[7]));
            			}
            		}
            		executeSetLayoutDataWrapper(ldw);
            	} else {
            		editor.realignWindowFrame(this, true);
            	}
                return;
            } else if ("Form".equals(getParentLayoutType())) {
            	if(getParent().gridCoordsChanged() || parentChanged) {
            		LayoutDataWrapper ldw = getLayoutDataWrapper();
            		int gx = ldw.getIntProperty("gridX");
            		int gy = ldw.getIntProperty("gridY");
            		int gw = ldw.getIntProperty("gridWidth");
            		int gh = ldw.getIntProperty("gridHeight");
            		ldw = ldw.getCopy(this);
            		if(parentChanged) {
            			ldw.setPropertyValue("gridX", new Integer(getParent().gridValues[0]+1));
            			ldw.setPropertyValue("gridY", new Integer(getParent().gridValues[1]+1));
            			ldw.setPropertyValue("gridWidth", new Integer(1));
            			ldw.setPropertyValue("gridHeight", new Integer(1));
            		} else {
            			ldw.setPropertyValue("gridX", new Integer(gx
            					+ getParent().gridValues[4]));
            			ldw.setPropertyValue("gridY", new Integer(gy
            					+ getParent().gridValues[5]));
            			if (getParent().gridValues[2] != GRIDBAG_UNDEFINED) {
            				ldw.setPropertyValue("gridWidth", new Integer(gw
            						+ getParent().gridValues[6]));
            				ldw.setPropertyValue("gridHeight", new Integer(gh
            						+ getParent().gridValues[7]));
            			}
            		}
            		executeSetLayoutDataWrapper(ldw);
            	} else {
            		editor.realignWindowFrame(this, true);
            	}
                return;
                
            } else if (PaneLayoutHandler.handlesLayout(getParent())) {
                PaneLayoutHandler.setBounds(this, bounds);
                return;
                
            } else if (MigLayoutHandler.handlesLayout(getParent())) {
            	MigLayoutHandler.setBounds(this, bounds, parentChanged);
                return;
                
            } else if (XYLayoutHandler.handlesLayout(getParent())) {
                XYLayoutHandler.setBounds(this, bounds);
                return;
                
            } else if ("Table".equals(getParentLayoutType())) {
            	if(getParent().gridCoordsChanged() || parentChanged) {
            		TableLayoutHandler.setBounds(this, bounds, parentChanged);
            	} else {
            		editor.realignWindowFrame(this, true);
            	}
                return;
            }
        }

         if (!hardSet)
            setPropertyValue(id, bounds, false);
        else
            setPropertyValue(id, bounds);
    }

    private static final String[] sides = { "top", "right", "bottom", "left" };

    public void toggleAnchorForSide(int side, boolean absolute) {
        anchorSide(side, true, absolute, getBounds());
    }

    public void anchorSide(int side, boolean anchor, boolean absolute) {
        boolean anchored = isSideAnchored(side, absolute);
        boolean toggle = false;
        if (!anchored && anchor)
            toggle = true;
        if (anchored && !anchor)
            toggle = true;
        anchorSide(side, toggle, absolute, getBounds());
    }

    public void anchorSide(int side, Rectangle bounds) {
        anchorSide(side, false, true, bounds);
    }

    public void anchorSide(int side, boolean toggle, boolean absolute,
            Rectangle bounds) {
        boolean anchored = isSideAnchored(side, absolute);
        if (isSWT() && "Form".equals(getParentSuperLayoutType())) {
            if (toggle)
                anchored = !anchored;
            if (!anchored)
                absolute = true;
            setFormLayoutBounds(bounds, absolute, side, anchored, "null");
            if (!anchored) {
                getLayoutDataWrapper().setPropertyValue(sides[side], "null");
            }
        } else if ("Anchor".equals(getParentSuperLayoutType())) {
            LayoutDataWrapper ldw = getLayoutDataWrapper();
            Rectangle b = bounds;
            Rectangle pb = getParent().getBounds();
            if (getParent().isRoot()) {
                pb.height -= getEditor().getMenuHeight();
            }
            if (pb.width == 0)
                pb.width = 1;
            if (pb.height == 0)
                pb.height = 1;
            int val = 0;
//            if (toggle)
//                anchored = !anchored;
            if (!toggle && !anchored)
                return;
            if (toggle && anchored) {
                ldw.setPropertyValue(sides[side] + "Type", new Integer(
                        AnchorConstraint.ANCHOR_NONE));
                return;
            }
            if (absolute) {
                if (side == 0) {
                    val = b.y;
                } else if (side == 1) {
                    val = pb.width - b.width - b.x;
                } else if (side == 2) {
                    val = pb.height - b.height - b.y;
                } else if (side == 3) {
                    val = b.x;
                }
                ldw.setPropertyValue(sides[side], new Integer(val));
                ldw.setPropertyValue(sides[side] + "Type", new Integer(
                        AnchorConstraint.ANCHOR_ABS));
            } else {
                if (side == 0) {
                    val = (int) ((b.y + 0.5) * 1000 / pb.height);
                } else if (side == 1) {
                    val = (int) ((b.x + b.width + 0.5) * 1000 / pb.width);
                } else if (side == 2) {
                    val = (int) ((b.y + b.height + 0.5) * 1000 / pb.height);
                } else if (side == 3) {
                    val = (int) ((b.x + 0.5) * 1000 / pb.width);
                }
                ldw.setPropertyValue(sides[side], new Integer(val));
                ldw.setPropertyValue(sides[side] + "Type", new Integer(
                        AnchorConstraint.ANCHOR_REL));
            }
        }
    }

    public String getAnchorDesc() {
        StringBuffer ret = new StringBuffer();
        if (isSideAnchored(0, true))
            ret.append("T, ");
        else if (isSideAnchored(0, false))
            ret.append("t, ");
        if (isSideAnchored(1, true))
            ret.append("R, ");
        else if (isSideAnchored(1, false))
            ret.append("r, ");
        if (isSideAnchored(2, true))
            ret.append("B, ");
        else if (isSideAnchored(2, false))
            ret.append("b, ");
        if (isSideAnchored(3, true))
            ret.append("L, ");
        else if (isSideAnchored(3, false))
            ret.append("l, ");
        String val = ret.toString();
        if (val.endsWith(", "))
            val = val.substring(0, val.length() - 2);
        return "[" + val + "]";
    }

    public boolean isSideAnchored(int side) {
        return isSideAnchored(side, isSideAbsolute(side));
    }

    /**
     * For FormLayouts - true if this side has the "control" property set
     */
    public boolean isSideAnchoredByControl(int side) {
        LayoutDataWrapper ldw = getLayoutDataWrapper();
        if (ldw == null)
            return false;
        String id = "top";
        if (side == 1)
            id = "right";
        if (side == 2)
            id = "bottom";
        if (side == 3)
            id = "left";
        if(!ldw.isRawPropertySet(id))
        	return false;
        Object obj = ldw.getPropertyValue(id);
        if(!(obj instanceof LayoutDataWrapper))
        	return false;
        Object control = ((LayoutDataWrapper) obj).getPropertyValue("control");
        return (control != null && !control.equals("null"));
    }

    public boolean isSideAnchored(int side, boolean absolute) {
        LayoutDataWrapper ldw = getLayoutDataWrapper();
        if (ldw == null)
            return false;
        String id = "top";
        if (side == 1)
            id = "right";
        if (side == 2)
            id = "bottom";
        if (side == 3)
            id = "left";
        if ("Anchor".equals(getParentLayoutType())) {
            if (ldw.getLayoutData() == null)
                ldw.setObject(new AnchorConstraint());
            int val = JiglooUtils.getIntProperty(ldw, id + "Type");
            if (absolute)
                return (val == AnchorConstraint.ANCHOR_ABS);
            else
                return (val == AnchorConstraint.ANCHOR_REL);
        } else if (isSWT() && "Form".equals(getParentLayoutType())) {
            if (ldw.getLayoutData() == null) {
                ldw.setObject(new FormData());
                JiglooUtils.initFormDataWrapper(ldw, 0, 0, getParent());
            }

            if(!ldw.isRawPropertySet(id))
            	return false;
            
            Object obj = ldw.getPropertyValue(id);
            if (!(obj instanceof LayoutDataWrapper))
                return false;
            LayoutDataWrapper la = (LayoutDataWrapper) obj;
            Object control = la.getPropertyValue("control");
            if (control != null && !control.equals("null"))
                return true;
            int num = JiglooUtils.getIntProperty(la, "numerator");
            int den = JiglooUtils.getIntProperty(la, "denominator");
            int offset = JiglooUtils.getIntProperty(la, "offset");
            boolean absAnc;
            if (side == 0 || side == 3) {
                absAnc = (num == 0);
            } else {
                absAnc = (num == den);
            }
            if (absolute) {
                return absAnc;
            } else {
                if (absAnc) {
                    return false;
                } else {
                    return offset == 0;
                }
            }
        }
        return false;
    }

    public boolean isSideAbsolute(int side) {
        LayoutDataWrapper ldw = getLayoutDataWrapper();
        String id = "top";
        if (side == 1)
            id = "right";
        if (side == 2)
            id = "bottom";
        if (side == 3)
            id = "left";
        if ("Anchor".equals(getParentLayoutType())) {
            if (ldw.getLayoutData() == null)
                ldw.setObject(new AnchorConstraint());
            int val = JiglooUtils.getIntProperty(ldw, id + "Type");
            return (val == AnchorConstraint.ANCHOR_ABS);
        } else if (isSWT() && "Form".equals(getParentLayoutType())) {
            Object obj = ldw.getPropertyValue(id);
            if (!(obj instanceof LayoutDataWrapper))
                return false;
            LayoutDataWrapper la = (LayoutDataWrapper) obj;
            int num = JiglooUtils.getIntProperty(la, "numerator");
            int den = JiglooUtils.getIntProperty(la, "denominator");
            return (num == den) || (num == 0);
        }
        return false;
    }

    private void setFormLayoutBounds(Rectangle bounds, boolean absolute,
            int side, boolean anchor, String comp) {
        try {
            int y = bounds.y;
            int x = bounds.x;

            if (getParentLayoutType().equals("Anchor")) { //setPropertyValue("bounds",
                // bounds);
                LayoutDataWrapper ldw = getLayoutDataWrapper();
                if (side == -1) {
                    anchorSide(0, false, isSideAbsolute(0), bounds);
                    anchorSide(1, false, isSideAbsolute(1), bounds);
                    anchorSide(2, false, isSideAbsolute(2), bounds);
                    anchorSide(3, false, isSideAbsolute(3), bounds);
                } else {
                    anchorSide(side, false, absolute, bounds);
                }
                //setPropertyValueInternal("bounds", bounds, true);
                repairParentConnectionNode();
            } else {
                LayoutWrapper plw = getParent().getLayoutWrapper();
                int mw = ((Integer) plw.getPropertyValue("marginWidth"))
                        .intValue();
                int mh = ((Integer) plw.getPropertyValue("marginHeight"))
                        .intValue();
                Composite parComp = (Composite) getParent().getControl();
                if (parComp == null)
                    return;
                Rectangle pca = parComp.getClientArea();
                Rectangle pb = parComp.getBounds();
                y = y - mh - pca.y;
                x = x - mw - pca.x;
                int width = pca.width - 2 * mw;
                int height = pca.height - 2 * mh;
                LayoutDataWrapper ldw = getLayoutDataWrapper();
                int w = bounds.width + x;
                int h = bounds.height + y;
                if (side == -1) {
                    if (!isSideAnchoredByControl(0))
                        setAttachmentPos(ldw, "top", y, height, isSideAnchored(
                                0, absolute), isSideAbsolute(0), comp);
                    if (!isSideAnchoredByControl(1))
                        setAttachmentPos(ldw, "right", w, width,
                                !isSideAnchored(1, absolute),
                                isSideAbsolute(1), comp);
                    if (!isSideAnchoredByControl(2))
                        setAttachmentPos(ldw, "bottom", h, height,
                                !isSideAnchored(2, absolute),
                                isSideAbsolute(2), comp);
                    if (!isSideAnchoredByControl(3))
                        setAttachmentPos(ldw, "left", x, width, isSideAnchored(
                                3, absolute), isSideAbsolute(3), comp);
                } else if (side == 0) {
                    if (!isSideAnchoredByControl(0))
                        setAttachmentPos(ldw, "top", y, height, anchor,
                                absolute, comp);
                } else if (side == 1) {
                    if (!isSideAnchoredByControl(1))
                        setAttachmentPos(ldw, "right", w, width, !anchor,
                                absolute, comp);
                } else if (side == 2) {
                    if (!isSideAnchoredByControl(2))
                        setAttachmentPos(ldw, "bottom", h, height, !anchor,
                                absolute, comp);
                } else if (side == 3) {
                    if (!isSideAnchoredByControl(3))
                        setAttachmentPos(ldw, "left", x, width, anchor,
                                absolute, comp);
                }
                setPropertyValueInternal("bounds", bounds, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUI();
    }

    private void setAttachmentPos(LayoutDataWrapper ldw, String id, int pos,
            int max, boolean toLower, boolean absolute, String compName) {
        //System.out.println("SET ATTACH POS, id=" + id + ", val= " +
        // ldw.getPropertyValue(id));
        LayoutDataWrapper la = (LayoutDataWrapper) ldw.getPropertyValue(id,
                true);
        if (la == null) {
            la = (LayoutDataWrapper) ldw.getPropertyValue(id);
            ldw.setPropertyValue(id, la);
        }
        int offset = JiglooUtils.getIntProperty(la, "offset");
        int num = JiglooUtils.getIntProperty(la, "numerator");
        String control = JiglooUtils.getStringProperty(la, "control");
        //if (!"null".equals(control))
        //return;
        int DENOM = 1000;
        if (!absolute) {
            la.setPropertyValue("offset", new Integer(0), false);
            //la.setPropertyValue("control", compName, false);
            la.setPropertyValue("numerator", new Integer(
                    (int) (((pos + 0.5) * DENOM) / max)), false);
            la.setPropertyValue("denominator", new Integer(DENOM), false);
        } else {
            if (toLower) {
                la.setPropertyValue("offset", new Integer(pos), false);
                la.setPropertyValue("control", compName, false);
                la.setPropertyValue("numerator", new Integer(0), false);
                la.setPropertyValue("denominator", new Integer(DENOM), false);
            } else {
                la.setPropertyValue("offset", new Integer(pos - max), false);
                la.setPropertyValue("control", compName, false);
                la.setPropertyValue("numerator", new Integer(DENOM), false);
                la.setPropertyValue("denominator", new Integer(DENOM), false);
            }
        }
    }

    public void setPropertyValueInternal(Object id, Object value,
            boolean hardSet) {
        boolean updateUI = true;
        if (editor == null || editor.isPreviewing())
            updateUI = false;
        setPropertyValueInternal(id, value, hardSet, updateUI);
    }

    private int getLayoutPropAsInt(String prop) {
        LayoutDataWrapper ldw = getLayoutDataWrapper();
        
        Object val = ldw.getPropertyValue(prop);
        Integer pval = null;
        try {
            if (val instanceof FieldWrapper) {
                pval = (Integer) ((FieldWrapper) val).getValue();
            } else if (val instanceof Integer) {
                pval = (Integer) val;
            } else if (val instanceof String) {
                if(val.equals(ldw.getLayoutData()))
                	return 0;
                if ("".equals(val))
                    return 0;
                return Integer.parseInt((String) val);
            }
            if (pval == null)
                return 0;
            return pval.intValue();
        } catch (Exception e) {
            System.err
                    .println("Error getting layout prop " + prop + ", " + val);
//            e.printStackTrace();
            return 0;
        }
    }

    public int[] gridValues = new int[8];

    private String lastGBInfo = null;

    public String getGridBagInfo(Rectangle newBounds, boolean calculate) {
       // getGridBagChanges(newBounds, gbc);
        LayoutDataWrapper ldw = getLayoutDataWrapper();
        String gx = "gridx";
        String gy = "gridy";
        String gw = "gridwidth";
        String gh = "gridheight";
        int del = 0;
        if(ldw.getLayoutData() instanceof CellConstraints) {
        	del = 1;
            gx = "gridX";
            gy = "gridY";
            gw = "gridWidth";
            gh = "gridHeight";
        }
        StringBuffer info = new StringBuffer();
        if (calculate && getParent() != null
        		&& getParent().gridValues[0] != GRIDBAG_UNDEFINED) {
            info.append("x , y : " + (del + getParent().gridValues[0]) + " , "
                    + (del + getParent().gridValues[1]) + "\n");
        } else {
            info.append("x , y : " + getGridValue(true) + " , "
                    + getGridValue(false) + "\n");
//            info.append("x , y : " + (getLayoutPropAsInt(gx)) + " , "
//                    + (getLayoutPropAsInt(gy)) + "\n");
        }
        if (calculate && getParent() != null
        		&& getParent().gridValues[2] != GRIDBAG_UNDEFINED) {
            info.append("w , h : " + getParent().gridValues[2] + " , "
                    + getParent().gridValues[3] + "\n");
        } else {
            info.append("w , h : " + getGridCellValue(true) + " , "
                    + getGridCellValue(false) + "\n");
//            info.append("w , h : " + (getLayoutPropAsInt(gw)) + " , "
//                    + (getLayoutPropAsInt(gh)) + "\n");
        }
        String str = info.toString();
        if (!str.equals(lastGBInfo)) {
            lastGBInfo = str; //setBounds(newBounds, true);
        }
        return str;
    }

    public void addToCode() {
        addToCode(null, true);
    }

    public void addToCode(IProgressMonitor pm, boolean addChildren) {
        if (!editor.updatesJavaCode())
            return;
        if (isVirtual)
            return;
        if(getEditor().isPartOfAppFrameworkApplication() && isSubclassOf(AbstractAction.class)) {
           ((ActionStub)getObject(true)).addToCode(this, editor.getJavaCodeParser());
        } else {
            editor.getJavaCodeParser().addToCode(this, pm, addChildren);
        }
    }

    public void repairConstructorInCode() {
        if (!editor.updatesJavaCode())
            return;
        editor.getJavaCodeParser().repairConstructor(this);
    }

    public void repairEventWrapperInCode() {
        if (!editor.updatesJavaCode())
            return;
        editor.getJavaCodeParser().repairEventWrapper(this);
    }

    public void repairInCode() {
        repairInCode(false);
    }

    public void repairInCode(boolean classChanged) {
        if (!editor.updatesJavaCode())
            return;
        editor.getJavaCodeParser().repairInCode(this, classChanged);
    }

    public void repairLayoutConstraintInCode() {
        repairParentConnectionInCode(true);
    }

    public void repairParentConnectionInCode() {
        repairParentConnectionInCode(true);
    }

    public JavaCodeParser getJavaCodeParser() {
        return getEditor().getJavaCodeParser();
    }

    public void repairParentConnectionNode() {
        if (!editor.updatesJavaCode())
            return;
        getJavaCodeParser().repairParentConnectionNode(this);
    }

    public void repairParentConnectionInCode(boolean constraintChanged) {
        if (!editor.updatesJavaCode())
            return;
        if (isVirtual)
            return;
        //layout constraint might be set in creation dialog, before component
        // is actually in code.
        if (!existsInCode())
            return;
        editor.getJavaCodeParser().repairParentConnectionInCode(this,
                constraintChanged, true);
    }

    public void updateInCode(String propName) {
        if (editor == null || !editor.updatesJavaCode() || editor.isPreviewing())
            return;
//        System.err.println("UPDATE IN CODE " + propName + ", " + this+", "+
//                needsUpdateInCode(propName));
        if (!needsUpdateInCode(propName))
            return;
        if (isCWT()
                && ("columns".equals(propName) || "rows".equals(propName)
                        || "leftColumn".equals(propName)
                        || "topRow".equals(propName) || "name".equals(propName) || "bounds"
                        .equals(propName))) {
            editor.getJavaCodeParser().repairConstructor(this);
        } else {
            editor.getJavaCodeParser().updateInCode(this, propName);
        }
        while (propsNeedingCodeUpdate.contains(propName))
            propsNeedingCodeUpdate.remove(propName);
    }

    public void setNeedsTotalUpdate() {
        Vector propNames = getPropertyNames();
        if (propNames != null) {
            for (int n = 0; n < propNames.size(); n++) {
                String pName = (String) propNames.elementAt(n);
                if (!propsNeedingCodeUpdate.contains(pName))
                    propsNeedingCodeUpdate.add(pName);
            }
        }
        if (layoutWrapper != null)
            layoutWrapper.setNeedsTotalUpdate();
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).setNeedsTotalUpdate();
    }

    public void updateAllInCode(IProgressMonitor pm) {
        if (!editor.updatesJavaCode() || propNames == null)
            return;
        try {

            if (pm != null)
                pm.setTaskName("Updating ... " + this);

            if (!isRoot()) {
                if (pm != null)
                    pm.setTaskName("Removing ... " + this);
                editor.getJavaCodeParser().removeFromCode(this);
                if (pm != null)
                    pm.setTaskName("Adding ... " + this);
                addToCode(pm, false);
            } else {
                repairInCode();
                updateInCode();
            }
            if (pm != null) {
                pm.setTaskName("Updated ... " + this);
                //pm.worked(1);
            }

            getLayoutWrapper().repairInCode(true);

            for (int i = 0; i < getChildCount(); i++)
                getChildAt(i).updateAllInCode(pm);

        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error in updateAllInCode for " + this);
        }
    }

    public void updateInCode() {
        if (!editor.updatesJavaCode() || propNames == null)
            return;
        for (int i = 0; i < propNames.size(); i++) {
            String pName = (String) propNames.elementAt(i);
            updateInCode(pName);
        }
    }

//    private int gridx, gridy, gridw, gridh;

    private void setGridBagCoords(FormComponent fc, int gridx, int gridy, int x2, int y2) {
        LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
        int gx = ldw.getIntProperty("gridx");
        int gy = ldw.getIntProperty("gridy");
        int gw = ldw.getIntProperty("gridwidth");
        int gh = ldw.getIntProperty("gridheight");
    	if(!ldw.hasProperty("gridx")) {
    		gx = gy = -1;
    		gw = gh = 1;
    	}
        if(usesJGFormLayout()) {
        	if(ldw.hasProperty("gridX")) {
	            gx = ldw.getIntProperty("gridX")-1;
	            gy = ldw.getIntProperty("gridY")-1;
	            gw = ldw.getIntProperty("gridWidth");
	            gh = ldw.getIntProperty("gridHeight");
        	} else {
        		gx = gy = 0;
        		gw = gh = 1;
        	}
        } else if(usesTableLayout()) {
    		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(this);
    		TableLayoutConstraints con = tl.getConstraints(fc.getComponent());
    		if(con == null) {
        		gx = gy = -1;
        		gw = gh = 1;
    		} else {
    			gx = con.col1;
    			gy = con.row1;
    			gw = con.col2 - gx + 1;
    			gh = con.row2 - gy + 1;
    		}
        } else if(MigLayoutHandler.handlesLayout(this)) {
        	if(!fc.equals(this)) {
        		gx = MigLayoutHandler.getGridValue(fc, true);
        		gy = MigLayoutHandler.getGridValue(fc, false);
        		gw = MigLayoutHandler.getGridCellValue(fc, true);
        		gh = MigLayoutHandler.getGridCellValue(fc, false);
        	} else {
        		gx = gy = -1;
        		gw = gh = 1;
        	}
        }
        gridValues[0] = gridx;
        gridValues[1] = gridy;
        gridValues[4] = gridValues[0] - gx;
        gridValues[5] = gridValues[1] - gy;
        if (x2 != GRIDBAG_UNDEFINED) {
            gridValues[2] = x2 - gridx + 1;
            gridValues[3] = y2 - gridy + 1;
            gridValues[6] = gridValues[2] - gw;
            gridValues[7] = gridValues[3] - gh;
        } else {
            gridValues[2] = GRIDBAG_UNDEFINED;
            gridValues[3] = GRIDBAG_UNDEFINED;            
            gridValues[6] = GRIDBAG_UNDEFINED;
            gridValues[7] = GRIDBAG_UNDEFINED;
        }
    }

    public boolean gridCoordsChanged() {
    	if(gridValues[4] != 0 && gridValues[4] != GRIDBAG_UNDEFINED)
    		return true;
    	if(gridValues[5] != 0 && gridValues[5] != GRIDBAG_UNDEFINED)
    		return true;
    	if(gridValues[6] != 0 && gridValues[6] != GRIDBAG_UNDEFINED)
    		return true;
    	if(gridValues[7] != 0 && gridValues[7] != GRIDBAG_UNDEFINED)
    		return true;
    	return false;
    }
    
    public LayoutManager getLayoutManager() {
        if(usesContentPane()) {
        	Container c = getContentPane();
        	if(c == null)
        		return null;
            return c.getLayout();
        } else {
        	if(component == null)
        		return null;
        	return ((Container) getComponent()).getLayout();       
        }
    }
    
    public boolean usesGridTypeLayout() {
        String lt = getLayoutType();
        return ("GridBag".equals(lt) || (isSwing() && "Form".equals(lt)) || "Table".equals(lt) || "Mig".equals(lt));
    }
    
    /**
     * Returns zero-based coordinates
     */
    public int[] getGridBagCoords(int x, int y, boolean absolute, boolean left) {
//        if(!isSwing())
//            return null;
        String lt = getLayoutType();
        if (!usesGridTypeLayout())
            return null;
        if (getComponent() == null && getControl() == null)
            return new int[] { -1, -1 };

        int[] widths =null;
        int[] heights =null;
        java.awt.Point orig = null;

        Object[] obs = JiglooUtils.getGridOrFormDimensions(this);
        if(obs != null) {
            widths = (int[]) obs[0];
            heights = (int[]) obs[1];
            orig = (java.awt.Point)obs[2];
        } else {
            return new int[] { -1, -1 };            
        }
        
        int bx = 0;
        int by = 0;
        if (absolute) {
            Rectangle b = getInsideBoundsRelativeToRoot();
            bx = b.x;
            by = b.y;
        }

        x -= bx;
        y -= by;
        
        int w = orig.x;
        int xi = GRIDBAG_UNDEFINED, yi = GRIDBAG_UNDEFINED;
        xi = 1;
        for (int i = widths.length-1; i>0; i--) {
            //get the first column with non-zero width, 
            if(widths[i] != 0) {
                xi = i+1;
                break;
            }
        }
        if(x<w)
            xi = 0;
        for (int i = 0; i < widths.length; i++) {
            if (left) {
                if (x >= w && x < w+widths[i])
                    xi = i;
            } else {
                if (x > w && x <= w+widths[i])
                    xi = i;
            }
            w += widths[i];
        }
        int h = orig.y;
        yi = 1;
        for (int i = heights.length-1; i>0 ; i--) {
            //get the first riw with non-zero height, 
            if(heights[i] != 0) {
                yi = i+1;
                break;
            }
        }
        if(y<h)
            yi = 0;
        for (int j = 0; j < heights.length; j++) {
            if (left) {
                if (y >= h && y < h +heights[j])
                    yi = j;
            } else {
                if (y > h && y <= h + heights[j])
                    yi = j;
            }
            h += heights[j];
        }
        return new int[] { xi, yi };
    }

    public void setPropertyValueCode(Object id, String valueCode) {
        if (valueCode == null) {
            if (propertyValueCode != null)
            	propertyValueCode.remove(id);
        } else {
            if (propertyValueCode == null)
                propertyValueCode = new HashMap();
            propertyValueCode.put(id, valueCode);
        }
    }

    /**
     * Extra properties are either ones like "input", for instance if there is a setInput
     * method but not a getInput method, or they may be just method calls, like
     * pack(), in which case id is JavaCodeParser.METHOD_PREFIX+"pack"
     */
    public boolean isExtraProperty(Object id) {
        return extraPropCode != null && extraPropCode.containsKey(id);
    }
    
    public void setExtraPropertyCode(Object id, String value) {
        if (extraPropCode == null)
            extraPropCode = new HashMap();
        extraPropCode.put(id, value);
    }

    public String getExtraPropertyCode(Object id) {
        if (extraPropCode == null)
            extraPropCode = new HashMap();
        return (String) extraPropCode.get(id);
    }

    public HashMap getExtraProperties() {
        return extraPropCode;
    }

    public void putProperty(Object name, Object value) {
	    if(value instanceof ImageWrapper) {
	        ((ImageWrapper)value).setFormComponent(this);
	    }
        properties.put(name, value);
    }
    
    public void setPropertyStorageValue(Object id, Object value) {
        if (propertyStorage == null) {
            propertyStorage = new HashMap();
            propertyStorageNames = new Vector();
        }
        propertyStorageNames.add(id);
        propertyStorage.put(id, value);
    }

    public void setPropertyValueSimple(Object id, Object value) {
//        if(id.equals("text") && "jButton1".equals(getName()))
//            System.out.println("setPropertyValueSimple "+id+", "+value+", "+this);
        if (properties == null) {
            setPropertyStorageValue(id, value);
        } else {
            putProperty(id, value);
            setSetProperty(id);
        }
    }

    public void setSetProperty(Object id) {
        if (!setProps.contains(id))
            setProps.add(id);
    }
    
    public void setConstructor(Constructor constr, Object[] params, String code) {
    	setConstructor(constr, params, code, null);
    }
        
    public ConstructorHolder getConstructorHolder() {
    	return cHolder;
    }
    
    public void setConstructor(Constructor constr, Object[] params, 
    		String code, ConstructorHolder cHolder) {
        this.cHolder = cHolder;
    	boolean changed = false;
        if ((constr == null && constructor != null)
                || (constr != null && constructor == null)
                || (params == null && constructorParams != null)
                || (params != null && constructorParams == null)) {
            changed = true;
        } else if (constr != null) {
            if (!constr.getDeclaringClass().equals(
                    constructor.getDeclaringClass())) {
                changed = true;
            } else if (params != null && constructorParams != null) {
                if (params.length != constructorParams.length) {
                    changed = true;
                } else {
					for (int i = 0; i < params.length; i++) {
					    if(params[i] == null ||
					            !params[i].equals(constructorParams[i])) {
					        changed = true;
					        break;
					    }
					}
                }
            }
        }
//        changed = true;
        this.constructor = constr;
        this.constructorParams = params;
        
        JiglooUtils.checkConstructor(constr, params);
        
        // Handle Swing cases like: new JScrollPane(textArea);
        // ie, textArea should be a child of the scroll pane.
        // and the opposite case new PanelBuilder(layout, panel),
        // where panelBuilder should be the child of panel. However, we
        //want to check that the proposed child doesn't already have a parent
        // For SWT the parameter is the parent of the child, and that case
        // is handled in elementCreated()
        if(isSwing() && params != null) {
	        for (int i = 0; i < params.length; i++) {
				Object p = params[i];
				if(p instanceof FormComponent) {
				    FormComponent fc1 = (FormComponent)p;
				    if(!isJDialog() && !isJFrame() && !isJWindow()) {
				        if(isAbstractFormBuilder()) {
				        	if(getParent() == null)
				        		fc1.addChild(this);
				        } else {
				        	if(fc1.getParent() == null)
				        		addChild(fc1);
				        }
				    }
				}
			}
        }
        
        String anonClassCode = cHolder == null ? null : cHolder.getAnonClassCode();
        
        setConstructorCode(code, anonClassCode);
        
        if (component != null && component.getParent() != null) {
            if (changed && constr != null) {
//                System.err.println("SET CONSTR (1) " + changed + ", " + this
//                        + ", " + component + ", " + constr);
            	
                Container cont = component.getParent();
                //want to replace the existing component with the new component
                //at exactly the same position in the parent container (which
                //might not be equal to the "indexInParent" when the container
                //is in a state of flux.
                int pos = -1;
                for(int i=0;i<cont.getComponentCount(); i++)
                	if(component.equals(cont.getComponent(i)))
                		pos = i;
                
                cont.remove(component);
                try {
                	Class cls = getConvertedMainClass();
                	if(cls.getName().startsWith("java.awt.")) {
                    	String test = "javax.swing.J"+cls.getName().substring(9);

//                    	if(cls.getName().equals("java.awt.Canvas"))
//                    		test = JPanel.class.getName();
                    	
                    	Class swingCls = editor.loadClass(test);
// System.err.println("CREATING SWING CLASS FOR "+this	+", "+cls.getName()+", "+test);
                    	if(swingCls != null) {
                    		cls = swingCls;
                            if(params != null) {
                            	constr = ConstructorManager.findConstructor(swingCls, 
                            			params);
                            }
                    	}
                    }
                	
//                    System.out.println("SET CONSTR " + this + ", " + component
//                            + ", " + constructor);
//                    if (params != null) {
//                        for (int i = 0; i < params.length; i++) {
//                            System.out
//                                    .println("PARAM " + i + " = " + params[i]);
//                        }
//                    }
                	
                	if(isAbstractFormBuilder()) {
                		refreshBuilder();
                	} else {
	                    component = (Component) ClassUtils.newInstance(
	                            cls, constr, params, false);
                	}
                	
                } catch (Throwable e) {
                	System.err.println("ERROR IN SET CONSTRUCTOR "+this+
                			", "+getConvertedMainClass()+", "+constr+", "+params);
                    jiglooPlugin.handleError(e);
                }
                if(component != null)
                	addComponentToParent(cont, pos);
                //                cont.add(component, getLayoutData());
            }
            if(component != null)
            	initProperties(component);
        } else if(control != null) {
            //TODO work out what to do if a control's non-default constructor is changed
        } else if(nonVisualObject != null) {
            if (changed && constr != null) {
                try {
                    nonVisualObject = ClassUtils.newInstance(
                            getConvertedMainClass(), constr, params, false);
                } catch (Throwable e) {
                    jiglooPlugin.handleError(e);
                }
            }
            initProperties(nonVisualObject);
        }
    }

    public void refreshBuilder() {
    	try {
	        builder = (AbstractFormBuilder) ClassUtils.newInstance(
	                getMainClass(), constructor, constructorParams, false);
            if(component != null && component.getParent() != null)
                component.getParent().remove(component);
	        component = null;
	        if(components != null)
	        	components.clear();
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    }
        
    public void setPropertyValueInternal(Object id, Object value,
            boolean hardSet, boolean updateUI) {
        //if setContent is called, it resets the size of the ScrolledComposite's content
        //to {0,0}, so don't call it! - unless some way is found to call methods
        //on FormComponents in the order they are called in code.
        if (isSubclassOf(ScrolledComposite.class) && id.equals("content"))
            return;

        if(id.equals("preferredSize") && getParent() != null && getParent().usesGroupLayout()) {
            //this should be handled by parameters in the group layout's add method
            // - don't want to add it to code since this will not be scale and L&F independent
            if(component instanceof JComponent) {
                Dimension dim = null;
                if(value instanceof SizePropertySource) {
                	value = ((SizePropertySource)value).getValue();
                }
                if(value instanceof Point) {
                    Point pt = (Point)value;
                    dim = new Dimension(pt.x, pt.y);
                } else if(value != null) {
                    dim = (Dimension)value;
                }
                if(dim == null) {
                    ((JComponent)component).setPreferredSize(null);
                    setProps.remove(id);
                } else {
                    ((JComponent)component).setPreferredSize(dim);
                    if(!setProps.contains(id))
                        setProps.add(id);
                }
            }
            return;
        }
        
        if (isJFaceApplicationWindow()) {
            ApplicationWindowManager.handlePropertySet(id, value, this);
        }

        if (value instanceof ImageWrapper) {
            ((ImageWrapper) value).setFormComponent(this);
        }
        
        //if a property gets set, discard original property-value code
        if (editor != null  && !editor.isProcessing())
            setPropertyValueCode(id, null);

        value = checkBorderValue(id, value);
        if (value == null)
            return;
        
        if (isSwing()) {
            if (id.equals("size") && hasProperty("preferredSize")
                    && !hasProperty("size"))
                id = "preferredSize";
            
            if(isSyntheticProperty((String)id)) {
            	if(setSynthPropertyValue(id, value)) {
            		setNeedsUpdateInCode((String) id);
            		setSetProperty(id);
            		return;
            	}
            }
            
            if(id.equals("focusTraversalPolicy")) {
                if(value instanceof FocusTraversalPolicyWrapper) {
                    FocusTraversalPolicyWrapper ftpw = (FocusTraversalPolicyWrapper) value;
                    if(ftpw.getArrayValue() != null)
                        setPropertyValue("focusCycleRoot", Boolean.TRUE, true);
                    else
                        setPropertyValue("focusCycleRoot", Boolean.FALSE, true);
                    updateInCode("focusCycleRoot");
                }
            }
            
        } else {
            if (id.equals("layoutData")) {
                if (value instanceof Point) {
                    setSWTLayoutInfo((Point) value, hardSet);
                }
                setSetProperty("layoutData");
                if (propertiesInited)
                    layoutDataWrapper.repairInCode();
                return;
            }

            if (!isA(CoolItem.class) && id.equals("preferredSize")
                    && propNames != null
                    && !propNames.contains("preferredSize"))
                id = "size";
        }

        if (!isVisual()) {
            if (nonVisualObject == null) {
                setPropertyValueSimple(id, value);
                //System.err.println("****setPropValInternal RETURNING");
                return;
            }
        } else {
            if ((isSwing() && component == null)
                    || (isSWT() && control == null)
                    || (isCWT() && nonVisualObject == null)) {
                setPropertyValueSimple(id, value);
                //System.err.println("****setPropValInternal RETURNING");
                return;
            }
        }
        
        if (editor != null && !editor.isProcessing()
        		&& !propsNeedingCodeUpdate.contains(id))
        	propsNeedingCodeUpdate.add(id);
        
        if(editor != null && editor.useInternalShell() 
        		&& (isRoot() || (!isInherited() && isSubclassOf(Shell.class))   )
        		&& (id.equals("text") || id.equals("title") || id.equals("iconImage") || id.equals("image"))) {
        	final Object fval = value;
        	final Object fid = id;
//        	Display.getDefault().asyncExec(new Runnable() {
//        		public void run() {
        			Composite dec = null;
        			if(editor != null)
        				dec = editor.getRootDecoration();
        			if(dec != null && !dec.isDisposed()) {
        				Object v2 = fval;
        				if(fval instanceof String) {
        					if(dec instanceof Decorations)
        						((Decorations)dec).setText((String)fval);
        					else
        						((LinuxDecorations)dec).setText((String)fval);
        				} else if(fid.equals("iconImage") && jiglooPlugin.canUseSwing()) {
        					if(v2 instanceof ImageWrapper)
        						v2 = ((ImageWrapper)v2).getImage(null);
        					if(v2 instanceof java.awt.Image) {
        						if(dec instanceof Decorations)
        							((Decorations)dec).setImage(JiglooUtils.getSWTImage((java.awt.Image)v2));
        						else
        							((LinuxDecorations)dec).setImage(JiglooUtils.getSWTImage((java.awt.Image)v2));
        					}
        				} else if(fid.equals("image")) {
        					if(v2 instanceof ImageWrapper)
        						v2 = ((ImageWrapper)v2).getImage(dec);
        					if(dec instanceof Decorations)
        						((Decorations)dec).setImage((Image)v2);
        					else
        						((LinuxDecorations)dec).setImage((Image)v2);
        				}
        			}
//        		}});
        } else if(isRootDecoration()) {
        	putProperty(id, value);
        	if(!setProps.contains(id))
        		setProps.add(id);
        	return;
        }
        
        if(value instanceof FormComponent) {
            try {
            	FormComponent fc = (FormComponent)value;
            	fc.setInMainTree(true);  //v3.8.2
            	if(!fc.isVisual()) {
            		if(fc.getNonVisualObject() == null) {
            			fc.populateNonVisualComponents(editor);
            		} else {
            			Object nvo = fc.getNonVisualObject();
            			if (nvo instanceof ImageWrapper)
            				value = nvo;
            		}
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(value instanceof ConstructorHolder) {
            value = ((ConstructorHolder)value).newInstance();
        }
        
        if (isCWT()) {
            //if (isCWT() && !editor.isPopulating() && !editor.isParsing()) {
            com.cloudgarden.jigloo.typewise.TypewiseManager
                    .handleSetBoundingProperty(id, value, this);
        }

        //System.err.println("#### setPropValInternal NOT RETURNING");
        if (id.equals("bounds")) {
            if (parent != null) {
                if (parent.isSubclassOf(Item.class))
                    return;
            }
        }

        if (propNames == null)
            return;
        if (!propNames.contains(id)) { //this allows things to
            // be set that aren't
            // properties, eg
            // JList's listData
            //System.err.println("Attempt to set " + id + " - property does not
            // exist for " + this);
            //return;
        }

        value = convertToWrapper(id, value);
        if (editor != null
                && !editor.isPopulating()
                && !editor.isPreviewing()
                && !(editor.updatesJavaCode() && editor.getJavaCodeParser()
                        .isParsing()) && getMainClass().equals(MenuItem.class)
                && value instanceof ImageWrapper
                && !((ImageWrapper) value).getName().equals("No Image")) {
            if ((getStyle() & SWT.CASCADE) != 0) {
                MessageDialog
                        .openInformation(
                                getShell(),
                                "Warning",
                                "If both an image and text are specified for a\n"
                                        + "CASCADE MenuItem, only the image will show.");
            }
        }

        Object propValue = value;
        
        value = ConversionUtils.convertParamToObject(value, false);
        
        Class propType = getPropType(id);
        //if (propType == null) return;
        
        if(value instanceof ImageWrapper) {
        	((ImageWrapper)value).setFormComponent(this);
        }
        
        if(isSwing() && value instanceof ImageWrapper
                && propType.equals(java.awt.Image.class)) {
            value = ((ImageWrapper)value).getImage(null);
        } else {
            value = convertToRawSWTProperty(value);
        }
        
        if (id.equals("bounds")) {
            if(value == null)
                return;
            if(value instanceof Rectangle) {
                Rectangle b = (Rectangle)value;
                if(b.width == 0 || b.height == 0)
                    return;
            }
        }
        if (id.equals("size")) {
            if(value == null)
                return;
            if(value instanceof Point) {
                Point b = (Point)value;
                if(b.x == 0 || b.y == 0)
                    return;
            }
        }
        
         //if (component != null || isAWTControl()) {
        if (isSwing() || isCWT()) {
            value = ConversionUtils.convertToAWT(value, propType);
        }
        if(isCWT() && id.equals("bounds")) {
            value = com.cloudgarden.jigloo.typewise.TypewiseManager.convertBounds(value);
        }

        if (value == null) { //System.out.println("value = null for "
            // + id);
        } //set property before finding out whether it's setter exists,
        //because property might have been set from a form file
        //for the opposite type (eg SWT from Swing)
        if (properties == null)
            properties = new HashMap();

        //mainly for ButtonGroup, but might be useful for other
        //props, and hopefully won't mess up anything
        if ("null".equals(value) && !(String.class.equals(propType))) {
            setProps.remove(id);
            putProperty(id, value);
            return;
        }
        
        if (value != null && propType != null
                && ! Cacher.isAssignableFrom(propType, value.getClass())) {
            Object convertedValue = ConversionUtils.convertObject(value, propType);
            if (convertedValue == null) {
                if (jiglooPlugin.DEBUG_EXTRA)
                    System.err.println("Attempt to set " + id + " to " + value
                            + ", " + value.getClass() + " - expected "
                            + propType + ", " + this);
                return;
            } else {
                value = convertedValue;
            }
        } 
        
        //for swt MigLayout
        if(value instanceof LayoutDataWrapper) {
        	LayoutDataWrapper ldw = (LayoutDataWrapper) value;
        	if(ldw.getLayoutData() instanceof String)
        		value = ldw.getLayoutData();
        }
        
        //adjust for Swing menu bar
        if (isSwing() && isRoot()) {
            if (id.equals("size") || id.equals("preferredSize")) {
                Dimension size = (Dimension) value;
                size.height -= getEditor().getMenuBarHeight();
            }
            if (id.equals("bounds")) {
                java.awt.Rectangle size = (java.awt.Rectangle) value;
                size.height -= getEditor().getMenuBarHeight();
            }
        }

        putProperty(id, propValue);
        if (hardSet)
            setSetProperty(id);

        try {
            Class[] classes = null;
            if (propType != null)
                classes = new Class[] { getMethodValueClass(propType) };
            else
                classes = new Class[] { getMethodValueClass(value) };
            if (id.equals("text") && "".equals(value)) { //value
                // =
                // null;
            }

            Object[] obs = null;

            if (isSWT() && isRoot() && (id.equals("bounds") || id.equals("size"))) {
                //don't call the setter on the root because FormEditor.refresh will
                //set the actual size, which may differ from the property size (because
                //of menu bar and window decoration sizes)
                return;
            }
            
            obs = new Object[] { value };
            if (value != null || !id.equals("model")) {
                //setting debugGraphicsOptions can freeze the IDE!
                if (!id.equals("debugGraphicsOptions") 
                        //not sure if layoutData should be set for Swing GUIs,
                        //(in FormLayoutDataAction and UndoableLayoutDataAction)
                        //but certainly don't need to try and invoke a non-existant
                        //method
                        && !(isSwing() && id.equals("layoutData"))) { 
                    String mn = (String) id;
                    mn = "set" + mn.substring(0, 1).toUpperCase()
                            + mn.substring(1);
                    invokeOnControl(mn, classes, obs);
                }
            }

            //set SWT layout data *after* invoking setSize so that layout
            //(if there is one) is done properly
            
            if(id.equals("bounds") && !editor.isProcessing()) {
//                if((id.equals("bounds") || id.equals("preferredSize")) && !editor.isProcessing()) {
                //this fixes a problem with EnfinLayout when if multiple components are resized
                //before the parent is updated (and their constraint is H/VRESIZE) then only
                //one component is resized
                if(parent != null)
                    parent.updateUI();
            }
            
            if (isVisual()) {
                if (isSWT() && (id.equals("bounds") || id.equals("size"))) {
                    Composite par = null;
                    if (control instanceof Control)
                        par = ((Control) control).getParent();
                    Point size = null;
                    if (id.equals("size")) {
                        size = (Point) value;
                    } else {
                        Rectangle b = (Rectangle) value;
                        size = new Point(b.width, b.height);
                    }
                    if (isRoot()) {
                        Point smSize = new Point(size.x, size.y);
                        setSWTLayoutInfo(smSize, hardSet);
                        if (par != null)
                            par.setSize(size);
                    } else {
                        setSWTLayoutInfo(size, hardSet);
                    }
                }
                if (id.equals("text") && value instanceof String) {
                    String txt = (String)value;
                    if(hasProperty("mnemonic")) {
                        int pos = txt.indexOf("&");
                        if(pos >= 0 && pos < txt.length() - 1) {
                            try {
                                Field fld = KeyEvent.class.getField("VK_"+txt.substring(pos+1, pos+2).toUpperCase());
                                setPropertyValueInternal("mnemonic", 
                                        new MnemonicWrapper((Integer)fld.get(null), this), false, false);
                            } catch(Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                    if (control instanceof CTabItem || control instanceof TabItem) {
                        setTabTitle((String) value);
                    }
                }
            }
            
            if (isRoot()
                    && (id.equals("size") || id.equals("bounds") || id
                            .equals("preferredSize")) && propertiesInited
                    && editor != null) {

            }

            if (propertiesInited && updateUI) {
                if (editor != null && !editor.isToggling()) {
                    notifyListeners(false, true);
                }
            }

        } catch (Throwable e) {
            if (jiglooPlugin.DEBUG_EXTRA) {
                System.err.println("ERROR setting " + id + ", " + value
                        + ", on " + this + " : " + e);
                try {
               	    if (e instanceof InvocationTargetException) {
                	    System.err.println("Cause: "
                    	        + ((InvocationTargetException) e).getCause());
                	}
                } catch(Throwable t2) {}
                //e.printStackTrace();
            }
        }
        return;
    }

    public Component makeComponent(String clsName) throws Exception {
        try {
            return makeComponent(clsName, false);
        } catch (Throwable e) {
            if (e instanceof Exception)
                throw (Exception) e;
            jiglooPlugin.handleError(e);
        }
        return null;
    }

    private boolean getGUIBuilderInstance = false;
    private boolean unmakeableSuperclass = false;
    private Class actualRootClass = null;
    
    public boolean hasGetGUIBuilderInstance() {
        return getGUIBuilderInstance;
    }
    public boolean hasUnmakeableSuperClass() {
        return unmakeableSuperclass;
    }
    
    public Class getRootClass() {
        if(actualRootClass == null)
            actualRootClass = getMainClass();
        return actualRootClass;
    }
    
    public AbstractFormBuilder getBuilder() {
    	if(builder != null)
    		return builder;
    	if(!isAbstractFormBuilder())
    		return null;
    	Class mc = getMainClass();
    	try {
    	    if(constructorParams != null) {
    	        Object[] tmpParams = new Object[constructorParams.length];
    	        for (int i = 0; i < constructorParams.length; i++) {
    	            Object p = constructorParams[i];
    	            if(p instanceof LayoutWrapper)
    	                p = ((LayoutWrapper)p).getObject();
    	            tmpParams[i] = p;
    	        }
    	        builder = (AbstractFormBuilder)ClassUtils.newInstance(mc, constructor, tmpParams);
    	    } else
    	        builder = (AbstractFormBuilder)ClassUtils.newInstance(mc, constructor, null);
    	    initProperties(); // 3.8.1 - not sure if this is needed (or if it does any harm)
    	    invokeStoredMethodCalls(); //3.8.1 - makes sure (eg) button order is set before adding buttons
    	} catch(Throwable t) {
    		System.err.println("Problem creating builder: "+t);
    	}
    	return builder;
    }

    public Container getBuilderPanel() {
    	if(builder == null)
    		return null;
    	if(builder instanceof PanelBuilder)
    		return ((PanelBuilder)builder).getPanel();
    	else
    		return builder.getContainer();
    }
    
    public boolean isSwtInSwing() {
    	return parent != null && parent.isSwing() && isSWT();
    }
    
    public boolean isSwingInSwt() {
    	return parent != null && isSwing() && parent.isSWT();
    }
    
    public Component makeComponent(String clsName, boolean strict)
            throws Throwable {

    	disposed = false;
        Class clazz1 = getEditor().loadClass(clsName);
        if (clazz1 == null)
            return null;
        
        Constructor struct = null;
        Object[] params = null;
        
        String cn = clsName;
        if ( Cacher.isAssignableFrom(JFrame.class, clazz1)) {
            if (!MAKE_JFRAMES) {
                cn = JPanel.class.getName();
                //don't want to create a JFrame, if constructor was set (by eg, new JFrame("Title") in code
                constructor = null;
            }
        } else if ( Cacher.isAssignableFrom(JApplet.class, clazz1)) {
            cn = JPanel.class.getName();
            constructor = null;
        } else if ( Cacher.isAssignableFrom(JDialog.class, clazz1)) {
            if (!MAKE_JFRAMES) {
                cn = JPanel.class.getName();
                constructor = null;
            }
        } else if(clsName.startsWith("java.awt.")) {
        	String test = "javax.swing.J"+clsName.substring(9);
        	Class swingCls = getEditor().loadClass(test);
        	if(swingCls != null) {
        		cn = test;
                if(constructorParams != null) {
                	struct = ConstructorManager.findConstructor(swingCls, constructorParams);
                }
        	}
        }
        Class clazz = getEditor().loadClass(cn);
        if (clazz != null) {
            Component component = null;
            
            if (isRoot() && isJDialog()) {
                params = new Object[] { new JFrame() };
                if(struct == null)
                    struct = ConstructorManager.findConstructor(clazz, params);
            } else if(constructor == null) {
                if(struct == null) {
                    if(!JiglooUtils.hasGUIBuilderInstanceMethod(clazz, isSwing())) {
                        try {
                        	if(clazz.isMemberClass() && getEditor().getRootObject() != null) {
                                struct = clazz.getDeclaredConstructor(new Class[] {getEditor().getRootObject().getClass()});
                                params = new Object[] {getEditor().getRootObject()};
                        	} else {
                        		struct = clazz.getDeclaredConstructor(null);
                        	}
                        	struct.setAccessible(true);
                        } catch(NoSuchMethodException mne) {
                            throw new JiglooException("There is no default constructor for Swing "+clazz);
                        }
                    }
                }
            } else {
                if(struct == null)
                    struct = constructor;
                params = constructorParams;
            }
            
            try {
                Object obj = null;
                if(getBuilder() != null) {
                    if(component == null) {
                        if(builder instanceof PanelBuilder)
                            obj = ((PanelBuilder)builder).getPanel();
                        else
                            obj = builder.getContainer();
                        component = (Component) obj;
                    }
                } else {
                    //don't write error to log if root, because may be unmakeable superclass
                    obj = ClassUtils.newInstance(clazz, struct, params, true, !isRoot());
                    if(obj instanceof Component)
                        component = (Component) obj;
            	}
                if(component == null) {
                    throw new Exception("Unable to make Swing "+clazz);
                }
            } catch(Throwable t) {
                if(isRoot()) {
                	Class origCls = clazz;
                    clazz = getEditor().getMainClass();
                    unmakeableSuperclass = true;
                    getGUIBuilderInstance = false;
                    actualRootClass = clazz;
                    try {
                    	Method gbm = clazz.getMethod("getGUIBuilderInstance", null);
                        component = (Component)gbm.invoke(null, null);
                        //put this in the default-value cache
                        getGUIBuilderInstance = true;
                        DefaultValueManager.addClassObject(component, false);
                    } catch(Throwable t1) {
                    	System.err.println("Unable to find/invoke method getGUIBuilderInstance");
                    	int mods = origCls.getModifiers();
                        if(Modifier.isPrivate(mods)) {
                        	jiglooPlugin.writeToLog("Error: The "+clazz+" extends the private " +origCls+
                        			" - you should make "+origCls.getName()+" public, or implement the" +
                        					" getGUIBuilderInstance method for "+clazz.getName());
                        } else if(Modifier.isProtected(mods)) {
                        	jiglooPlugin.writeToLog("Error: The "+clazz+" extends the protected " +origCls+
                        			" - you should make "+origCls.getName()+" public, or implement the" +
                        					" getGUIBuilderInstance method for "+clazz.getName());
                        } else if(Modifier.isAbstract(mods)) {
                        	jiglooPlugin.writeToLog("Error: The "+clazz+" extends the abstract " +origCls+
                        			" - you should implement the" +
                        					" getGUIBuilderInstance method for "+clazz.getName());
                        }
                        
                    }
                    if(component == null) {
                    	if(clazz == null)
                    		throw new Exception("Unable to load class "+getEditor().getClassName());
                    	//                    System.err.println("TRYING TO MAKE ROOT CLASS "+clazz+", "+editor.getClassName());
                    	if(params == null) {
                    		struct = clazz.getConstructor(null);
                    	} else {
                    		struct = ConstructorManager.findConstructor(
                    				clazz, params);                        
                    	}
                    	try {
                    		component = (Component) ClassUtils.newInstance(clazz, struct, params);
                    	} catch(Throwable t2) {
                    		component = new JTextField(t2.toString());
                    		System.err.println("Creating JTextField instead of "+clazz.getName()+" :"+t2);
                    	}
                    }
                } else {
                    throw t;
                }
            }
            
            initCustomProperties(strict);

            rawComponent = null;
            
            component = handleWindowComponent(component);

            return component;
        }
        throw new Exception("Class " + clsName + " not found");
    }

    public Object makeNonVisualObject() throws Exception {
        return makeNonVisualObject(getClassName());
    }

    public Object makeNonVisualObject(String clsName) throws Exception {
        disposed = false;
        Class clazz = null;
        try {
        	if(!isInMainTree())
        		return null;
        	
            //Class clazz1 = getEditor().loadClass(clsName);
            String cn = clsName;
            clazz = getEditor().loadClass(cn);
            if (clazz != null) {
                if (!jiglooPlugin.getDefault().canMakeNVClass(clazz))
                    return null;
                
                //this gets thrown when trying to make a JComboBoxModel, for instance
//                if(!JiglooUtils.isVisual(clazz))
//                	throw new Exception("Tryng to make non-visual object, but class is visual "
//                			+clsName);
                
//                System.err.println("MAKE NVO " + clsName+", constr="+constructor);
                Object nvobj = null;
                if (constructor != null) {
                    if (isCWT()) {
                        //for Screen constructor, set second parameter (this) to null
                        if (constructorParams != null
                                && constructorParams[1] instanceof FormComponent)
                            constructorParams[1] = null;
                    }
                    nvobj = ClassUtils.newInstance(clazz, constructor,
                            constructorParams);
                } else {
                    Constructor struct = null;
                    if (isCWT()) {
                        nvobj = com.cloudgarden.jigloo.typewise.TypewiseManager
                                .makeDefaultNonVisualObject(this, clazz);
                    } else {
                    	
                    	if(clazz.equals(Integer.class))
                    		return new Integer(0);
                    	if(clazz.equals(Double.class))
                    		return new Double(0);
                    	if(clazz.equals(Float.class))
                    		return new Float(0);
                    	if(clazz.equals(Long.class))
                    		return new Long(0);
                    	
                        Constructor[] cons = clazz.getConstructors();
                        for (int i = 0; i < cons.length; i++) {
                            Constructor con = cons[i];
                            if (con.getParameterTypes().length == 0) {
                                struct = con;
                                break;
                            }
                        }
                        if (struct != null) {
                            nvobj = ClassUtils.newInstance(clazz, struct, null);
                        } else {
                            nvobj = clazz;
                            System.err.println("makeNonVisualObject: Unable to make class "+clsName+" - returning class instead");
                            /*
                             * MessageDialog.openError(
                             * getShell()(), "Unable to
                             * create " + clazz, "Unable to create " + clazz +
                             * "\nNo-parameter constructor required"); return
                             * null;
                             */
                        }
                    }
                }
                className = clsName;
//                setSwing(getEditor().isInSwingMode());
                initCustomProperties(false);
                return nvobj;
            } else {
                throw new Exception("Class " + className + " not found");
            }
        } catch (Throwable t) {
            //if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("Error in makeNonVisualObject for class "
                        + className + ", " + t);
            //t.printStackTrace();
            return clazz;
        }
    }

    private Object jfaceParent = null;

    public Object getJFaceObject() {
        return jfaceParent;
    }

    public void displayBranch() {
        displayBranch("");
    }
        
    public void displayBranch(String indent) {
        if(isSWT()) {
            Object c = getControl();
            System.err.println(indent+"DISPLAY BRANCH: "+this+", "+c);
            if(c instanceof Control)
                System.err.println(indent+"LayoutData="+((Control)c).getLayoutData());
            if(c instanceof Composite)
                System.err.println(indent+"Layout="+((Composite)c).getLayout());
            for (int i = 0; i < getChildCount(); i++) {
                FormComponent fc = getChildAt(i);
                System.err.println(indent+"CHILD "+i+":");
                fc.displayBranch(indent+"   ");
            }
        } else {
            System.err.println(indent+this);
            for (int i = 0; i < getChildCount(); i++) {
                FormComponent fc = getChildAt(i);
                fc.displayBranch(indent+"   ");
            }
        }
    }
    
    private Control makeJFaceControl(Class clazz, Composite par) {
        try {
            if (isJFaceWizardDialog()) {
                jfaceParent = new StubWizDialog(getEditor().getSite()
                        .getShell());
                control = ((StubWizDialog) jfaceParent).getContents(par);
                //System.out.println("***MAKING NEW WIZ DIALOG " + control);
                setLayoutWrapper(new LayoutWrapper(this));
                return (Control) control;
            } else if (isJFaceDialog()) {
                jfaceParent = new StubDialog(getShell());
                control = ((StubDialog) jfaceParent).getContents(par);
                //System.err.println("***MAKING NEW DIALOG " + control);
                setLayoutWrapper(new LayoutWrapper(this));
                return (Control) control;
            
            } else if (isJFaceApplicationWindow()) {
//                jfaceParent = new StubAppWindow(getShell());
//                ((StubAppWindow)jfaceParent).createAppContents(par);
//                Control aw = ((StubAppWindow)jfaceParent).getContents();
//                System.err.println("***MAKING NEW APP WINDOW " + aw);
//                return aw;
                
//                OrderableComposite grp = new OrderableComposite(par, SWT.NULL);
//                return grp;
                return null;
                
//            } else if (parent != null && parent.isJFaceViewer()) {
//                System.out.println("MAKE VIEWER CONTROL "+ getParent().getControl());
//                return (Control) getParent().getControl();
                
            } else if (isJFaceViewer()) {
                Constructor struct = null;
                Control c = null;
                try {
                    struct = clazz.getConstructor(new Class[] {
                            Composite.class, int.class });
                    jfaceParent = ClassUtils.newInstance(clazz, struct, 
                            new Object[] { par, new Integer(style) });
                    c = ((Viewer) jfaceParent).getControl();
                } catch (NoSuchMethodException e) {
                    struct = clazz.getConstructor(new Class[] { Composite.class });
                    jfaceParent = ClassUtils.newInstance(clazz, struct, new Object[] { par });
                    c = ((Viewer) jfaceParent).getControl();
                }
//                System.out.println("***MAKING NEW VIEWER " + c);
                return c;
            }
        } catch (Throwable e) {
            jiglooPlugin.handleError(e);
        }
        return null;
    }

    private FormComponent getVirtualFC(String name, Class cls, Composite par) {
        FormComponent vfc = findChild(name);
        return getVirtualFC(vfc, name, cls, par);
    }

    private FormComponent getVirtualFC(FormComponent vfc, String name,
            Class cls, Composite par) {
        if (vfc != null) {
            //System.err.println("FC: POPULATING NEW VFC "+name);
            vfc.setVirtual(true);
            vfc.setControl(par);
            vfc.initProperties();
            //vfc.populateControls(par, editor, false);
            if(par != null) {
                vfc.populateControls(par.getParent(), editor, false);
	            vfc.setLayoutWrapper(new LayoutWrapper(vfc));
	            vfc.setLayoutDataWrapper(new LayoutDataWrapper(par.getLayoutData(), vfc ));
            }
            //vfc.getLayoutWrapper().setSet(true);
//            if(name.equals("buttonBar")) {
//                //because in previews, when buttonBar is copied and not created
//                //from parsing code (createButton) then numColumns = 0 for
//                //the bb GridLayout and is not changed (since createButton increments
//                //the column count) so make sure columnCount is non-zero (which
//                //leads to a StackOverflowError
//	            int ncols = vfc.getNonInheritedChildCount();
//	            if(ncols == 0)
//	                ncols = 1;
//	            vfc.getLayoutWrapper().setPropertyValue("numColumns", new Integer(ncols));
//            }
        } else {
            //System.err.println("FC: MAKING NEW VFC "+name);
            vfc = FormComponent.newFormComponent(this, cls.getName(), name, false, par);
            vfc.setVirtual(true);
            if(par != null) {
	            vfc.setLayoutWrapper(new LayoutWrapper(vfc));
	            vfc.setLayoutDataWrapper(new LayoutDataWrapper(par.getLayoutData(), vfc));
            }
            editor.addComponent(vfc);
        }
        return vfc;
    }
    
    private void addJFaceSubControls() {
        Class cls = control.getClass();
        if (isJFaceViewer()) {
            //FormComponent vfc = new FormComponent(this, cls.getName(),
            // "viewerControl");
            //vfc.setVirtual(true);
            //vfc.populateControls(((Control) control).getParent(), editor,
            // false);
        } else if (isJFaceWizardDialog()) {
            StubWizDialog swd = (StubWizDialog) jfaceParent;
            swd.getOrderableComposite().setLayout(new StackLayout());
            FormComponent vfc = getVirtualFC("WizardPages", cls, swd.getOrderableComposite());
            FormComponent vfc2 = vfc.getVirtualFC("firstPage",
                    OrderableComposite.class, null);
            vfc.getLayoutWrapper().setPropertyValue("topControl", 
                    new FormComponentWrapper(vfc2));
            getVirtualFC("buttonBar", cls, (Composite)swd.getButtonBar());
            
        } else if (isJFaceApplicationWindow()) {
            ApplicationWindowManager.addSubControls(this);
            
        } else if (isJFaceDialog()) {
            StubDialog sd = (StubDialog) jfaceParent;       
            FormComponent vfc = null;
            if(editor != null)
                vfc = editor.getComponentByName("dialogArea", true);
            if (vfc != null) {
                this.addChild(vfc);
                getVirtualFC(vfc, "dialogArea", cls, sd.getOrderableComposite());
            } else {
                getVirtualFC("dialogArea", cls, sd.getOrderableComposite());
            }
//            FormComponent bb = getVirtualFC("buttonBar", cls, (Composite)sd.getButtonBar());
            //System.err.println("FC: GOT BUTTON BAR");
            //vfc.getParent().displayBranch();
        }
    }

    public void makeAndSetControl(Object[] info, Object parent,
            boolean translate) throws Throwable {
        Object comp = parent;
        if (parent instanceof CTabItem) {
            comp = ((CTabItem) parent).getParent();
        } else if (parent instanceof CoolItem) {
            comp = ((CoolItem) parent).getParent();
        } else if (parent.getClass().getName().equals("org.eclipse.swt.widgets.ExpandItem")) {
        	comp = (parent.getClass().getMethod("getParent", null)).invoke(parent, null);
//            comp = ((org.eclipse.swt.widgets.ExpandItem) parent).getParent();
        } else if (parent instanceof ToolItem) {
            comp = ((ToolItem) parent).getParent();
        } else if (parent instanceof MenuItem) {
            comp = ((MenuItem) parent).getParent();
        } else if (parent instanceof TabItem) {
            comp = ((TabItem) parent).getParent();
            /*
             * } else { MessageDialog.openInformation(
             * editor.getSite().getShell(), "Error", "Unable to makeControl " +
             * getName()); return;
             */
        }
        Object ctrl = makeControl(info, comp, translate);
        String unknownClass = null;
        
        if (ctrl == null) {
            //probably a compilation error in inherited class
            if (editor != null && component != null
                    && FormEditor.isUpdatingClass())
                return;
            unknownClass = (String) info[0];
            setClassName(Group.class.getName());
            control = makeControl(new Object[] { Group.class.getName(),
                    new Integer(SWT.NULL) }, comp, translate);
            ((Group) getControl()).setText(unknownClass);
            classCreated = false;
        } else {
            classCreated = true;
            control = ctrl;
        }

        if (parent instanceof CTabItem) {
            CTabItem cti = (CTabItem) parent;
            cti.setControl((Control) getControl());
        } else if (parent instanceof TabItem) {
            TabItem ti = (TabItem) parent;
            ti.setControl((Control) getControl());
        } else if (parent.getClass().getName().equals("org.eclipse.swt.widgets.ExpandItem")) {
        	parent.getClass().getMethod("setControl", new Class[] {Control.class}).invoke(parent, new Object[] {getControl()});
//        	org.eclipse.swt.widgets.ExpandItem ti = (org.eclipse.swt.widgets.ExpandItem) parent;
//            ti.setControl((Control) getControl());
        } else if (parent instanceof CoolItem) {
            CoolItem ti = (CoolItem) parent;
            ti.setControl((Control) getControl());
        } else if (parent instanceof ToolItem) {
            ToolItem ti = (ToolItem) parent;
            ti.setControl((Control) getControl());
        } else if (parent instanceof MenuItem) {
            MenuItem ti = (MenuItem) parent;
            ti.setMenu((Menu) getControl());
        }

        addJFaceSubControls();
        if (layoutWrapper == null) {
            layoutWrapper = new LayoutWrapper(this);
            layoutWrapper.setSet(false);
            setOldLW();
        }

        if (control instanceof Control && getParent() != null
                && getParent().getControl() instanceof Composite) {

            Composite pc = (Composite) getParent().getControl();
            if (pc instanceof IWidgetManager) {
                //getChildren re-loads the widgetmanager's children array, so
                //that getPosition will return the correct value.
                pc.getChildren();
                IWidgetManager wm = (IWidgetManager) pc;
                int wmPos = wm.getPosition((Control) control);
                int ip = getIndexInParent();
                if (ip != wmPos) {
                    wm.moveTo((Control) control, ip);
                }
            }
        }
    }

    private boolean paramsMatch(Class[] set1, Class[] set2) {
        if (set1.length != set2.length)
            return false;
        for (int i = 0; i < set1.length; i++) {
            if (! Cacher.isAssignableFrom(set1[i], set2[i]))
                return false;
        }
        return true;
    }

    private void useDummyShell(boolean use) {
        if (editor != null && editor.getFrameComp() != null) {
            ((IDummyShellSupplier) editor.getFrameComp())
                    .useDummyShell(use);
        }
    }

    private void addToLog(String msg) {
    	if(editor != null)
    		editor.addToLog(msg);
    }
    
    private Object makeControl(Object[] info, Object par, boolean translate)
            throws Throwable {
        useDummyShell(true);
        disposed = false;
//        System.err.println("MAKE CONTROL "+this);
        if(isJFaceForm()) {
            if(formToolkit == null) //???
                formToolkit = new org.eclipse.ui.forms.widgets.FormToolkit(Display.getDefault());
            org.eclipse.ui.forms.widgets.Form form
            	= ((org.eclipse.ui.forms.widgets.FormToolkit)formToolkit).createForm((Composite)par);
    	    return form;
        }
        
        if(eclipseFormCall != null) {
            eclipseFormCall.invoke(this, parent);
            return control;
        }
        
        if(getRootComponent().isJFaceForm() && control != null) {
            return control;
        }
        Class clazz = null;
        boolean setStyle = true;
        Integer style = (Integer) info[1];
        //Switch from Shell or Composite to OrderableComposite
        //(just for GUI builder)
        String cn = (String) info[0];
        String oc = SwingSWTMapper.getOrderableClass(cn);

        //the mac cannot render Browser correctly in the form editor,
        //but can in the preview (when editor is null)
        boolean macBrowserFix = cn.equals("org.eclipse.swt.browser.Browser");
        if(editor == null || !jiglooPlugin.isMacOS())
        	macBrowserFix = false;
        
        if (oc != null) {
            cn = oc;
        } else if (cn.equals(Shell.class.getName())) {
            cn = OrderableComposite.class.getName();
        } else if (cn.equals(Dialog.class.getName())) {
            cn = OrderableComposite.class.getName();
        } else if (macBrowserFix) {
            cn = CLabel.class.getName();
        }

        if (translate) {
            if (cn.equals(Menu.class.getName())) {
                cn = OrderableComposite.class.getName();
            }
            if (cn.equals(MenuItem.class.getName())) {
                cn = CLabel.class.getName();
            }
        }
        //this is for a bug in the 3.1 SWT library
        
        if (cn.equals(ScrolledComposite.class.getName())) {
            cn = OrderableComposite.class.getName();
        }
        
        clazz = getEditor().loadClass(cn);
        if (clazz == null) {
            addToLog("Unable to find " + className); 
            useDummyShell(false);
            return null;
        }
        
        if (par instanceof CTabItem)
            par = ((CTabItem) par).getParent();
        else if (par instanceof TabItem)
            par = ((TabItem) par).getParent();
        else if (par.getClass().getName().equals("org.eclipse.swt.widgets.ExpandItem")) {
        	par = (par.getClass().getMethod("getParent", null)).invoke(par, null);
//            par = ((org.eclipse.swt.widgets.ExpandItem) par).getParent();
        } else if (par instanceof CoolItem)
            par = ((CoolItem) par).getParent();
        else if (par instanceof ToolItem)
            par = ((ToolItem) par).getParent();
        else if (par instanceof MenuItem)
            par = ((MenuItem) par).getParent();

        Control c = null;
        
        if(isJFaceApplicationWindow()) {
            clazz = OrderableComposite.class;
            cn = clazz.getName();
        }
        
        initCustomProperties(false);
        
        if (par instanceof Composite) {
            c = makeJFaceControl(clazz, (Composite) par);
            if (c != null) {
                useDummyShell(false);
                return c;
            }
        } 
        
        if (getParent() != null
                && DIALOG_BUTTON_BAR.equals(getParent().getSpecialType())) {
            
            //the buttonBar is created in addJFaceSubControls when the parent's
            //makeAndSetControl method is called
            
            setSpecialType(DIALOG_BUTTON);
            Object rc = getRootComponent().getJFaceObject();
            
            if (rc instanceof StubWizDialog)
                c = ((StubWizDialog) rc).createButton((Composite) par, 0,
                        "BUTTON", false);
            else if (rc instanceof StubDialog)
                c = ((StubDialog) rc).createButton((Composite) par, 0,
                        "BUTTON", false);

            layoutDataWrapper = new LayoutDataWrapper(c.getLayoutData(), this);
            //System.out.println("CREATED BUTTON "+this+", "+getLayoutDataWrapper());
            ((Composite) getParent().getControl()).getParent().layout();
            useDummyShell(false);
            return c;
        }

        ControlInit ci = null;
        if (info.length > 2)
            ci = (ControlInit) info[2];
        Constructor struct = null;
        Object[] params = null;

        Object[] conAndPs = getSWTConstructorAndParams(clazz, par, style, setStyle);
        struct = (Constructor) conAndPs[0];
        params = (Object[]) conAndPs[1];
        
        if(struct == null) {
        	struct = constructor;
        	params = constructorParams;
        }
        
        Object widget = null;
        if (struct == null) {
            jiglooPlugin.writeToLog("No suitable constructor found for " + cn);
        } else {
            try {
            	//4.0.6
            	struct.setAccessible(true);
                widget = ClassUtils.newInstance(clazz, struct, params);
                if(widget == null)
                    throw new Exception("Not allowed to make SWT class "+clazz+", "+struct);
                if (ci != null)
                    ci.initControl(widget);
                    if(isRoot()) {
                        actualRootClass = clazz;
                        unmakeableSuperclass = false;
                    }
            } catch (Throwable t) {
                try {
                    if (isRoot()) {
                        clazz = getEditor().getMainClass();
                        //System.err.println("MAKING CHILD CONTROL "+clazz);
                        unmakeableSuperclass = true;
                        getGUIBuilderInstance = false;
                        actualRootClass = clazz;
                        try {
                            Method gbm = clazz.getMethod("getGUIBuilderInstance", 
                                    new Class[] {Composite.class, int.class});
                            widget = gbm.invoke(null, params);
                            //put this in the default-value cache
                            getGUIBuilderInstance = true;
                            DefaultValueManager.addClassObject(widget, false);
                            //System.err.println("MADE GUI BUILDER INSTANCE "+widget+", "+this);
                        } catch(Throwable t1) {
                            jiglooPlugin.writeToLog("Warning: The class "+clazz.getName()+
                                    " extends an " +
                            		"abstract class and should therefore implement getGUIBuilderInstance");
                        }
                        if(widget == null) {
	                        conAndPs = getSWTConstructorAndParams(clazz, par,
                                    style, setStyle);
                            struct = (Constructor) conAndPs[0];
                            params = (Object[]) conAndPs[1];

                            widget = ClassUtils.newInstance(clazz, struct, params);
                            if (ci != null)
                                ci.initControl(widget);
                        }
                        t = null;
                    }
                } catch (Throwable t2) {
                    t = t2;
                }
                //probably a compilation error in inherited class
                if (editor != null && control != null
                        && FormEditor.isUpdatingClass())
                    return null;
                if(t != null)
                    handleUnableToMakeClassError(className, t);
                //				JiglooUtils.handleError(
                //					editor.getSite().getShell(),
                //					"Error creating " + className,
                //					"Error creating " + className,
                //					t);

                //			} catch (IllegalArgumentException e) {
                //				MessageDialog.openInformation(
                //					editor.getSite().getShell(),
                //					"Error",
                //					"Unable to add a "
                //						+ clazz.getName()
                //						+ " to a "
                //						+ par.getClass().getName());
                //			} catch (InvocationTargetException e) {
                //				String msg = e.getMessage();
                //				if (e.getTargetException() != null) {
                //					msg = e.getTargetException().getMessage();
                //				}
                //				MessageDialog.openInformation(
                //					editor.getSite().getShell(),
                //					"Error constructing " + clazz,
                //					"Error constructing " + clazz + "\n" + msg);
            }
        }
        useDummyShell(false);
		if(isVirtual() || isDialogButton()) {
		        layoutWrapper = new LayoutWrapper(this);
		        layoutDataWrapper = new LayoutDataWrapper(((Control)widget).getLayoutData(), this);
		}
		
		if(macBrowserFix) {
			((CLabel)widget).setText("Unable to render browser control correctly on Mac OSX");
			((CLabel)widget).setAlignment(SWT.CENTER);
		}
		
        return widget;
    }
    
    private Object[] getSWTConstructorAndParams(Class clazz,
            Object par, Integer style, boolean setStyle) {
        Constructor struct = null;
        Object[] params = null;
        if (clazz.equals(FilteredList.class)) {
            struct = clazz.getConstructors()[0];
            params = new Object[] { par, style, null, Boolean.TRUE,
                    Boolean.TRUE, Boolean.TRUE };
        } else {
            Constructor[] cons = clazz.getConstructors();
            //System.err.println("Looking for constructor " + par + ", "
               //     + par.getClass() + ", " + this);
            //System.out.println("found " + cons.length + " constructors");
            Class[] pTypes = null;
            Class[] comp1 = new Class[] { par.getClass() };
            Class[] comp2 = new Class[] { par.getClass(), int.class };
            Class[] comp3 = new Class[] { String.class, String.class,
                    par.getClass() };
            for (int i = 0; i < cons.length; i++) {
                pTypes = cons[i].getParameterTypes();
                //System.err.println(
                //"got constructor, num params = " + pTypes.length);
                if (paramsMatch(pTypes, comp2)) {
                    if (setStyle)
                        params = new Object[] { par, style };
                    else
                        params = new Object[] { par, new Integer(SWT.NULL) };
                    struct = cons[i];
                    //got a styled constructor - good enough!
                    break;
                } else if (paramsMatch(pTypes, comp1)) {
                    params = new Object[] { par };
                    struct = cons[i];
                    //don't give up looking if you just find a no-style
                    // constructor
                } else if (paramsMatch(pTypes, comp3)) {
                    params = new Object[] { "param1", "param2", par };
                    struct = cons[i];
                    break;
                }
            }
        }
        return new Object[] { struct, params };
    }
    
    private static Vector thrownErrorClasses = new Vector();
    
    public static void clearErrors() {
    	thrownErrorClasses.clear();
    }
    
    private void handleUnableToMakeClassError(String className, Throwable t) {
        if(thrownErrorClasses.contains(className))
        	return;
        
        thrownErrorClasses.add(className);
        
    	String cn2 = className;
        if (hasUnmakeableSuperClass() && getRootClass() != null)
            cn2 = getRootClass().getName();
        String msg = "";
        String title = "Problem instantiating element: "+getDisplayName()+": "+cn2;
//        t.printStackTrace();
        if(t instanceof JiglooException) {
        	msg = title+"!\n\n"+t.getMessage();
        } else {
            if(jiglooPlugin.getJavaVersion() >= 14 && t.getCause() != null) {
                msg = t.getCause().toString();
            } else {
                if(t.getMessage() != null)
                    msg= t.getMessage();
                else
                	msg = t.toString();
            }
            msg = title
            + "\nPlease check that it has been compiled and built correctly.\n\n"
            + msg;
//            t.printStackTrace();
        }
        if (editor != null) {
            if(jiglooPlugin.getJavaVersion() >= 14 && t.getCause() != null) {
                t = t.getCause();
            }
        	jiglooPlugin.handleError(editor.getSite().getShell(), title, msg, t);
//            MessageDialog
//                    .openError(
//                            editor.getSite().getShell(),
//                            "Unable to create Class",
//                            msg);
        }
        msg = JiglooUtils.replace(msg, "\n\n", " ");
        msg = JiglooUtils.replace(msg, "\n", " ");
        jiglooPlugin.writeToLog(msg);
    }
    
    public boolean canBeRoot() {
    	if(isSWT()) {
    		return isSubclassOf(Composite.class)
    		|| isSubclassOf(Shell.class)
    		|| isSubclassOf(Dialog.class)
    		;
    	}
    	return true;
    }
    
    public FormComponent getRootOfThis() {
    	if(parent == null)
    		return null;
        if(parent.equals(editor.getExtraCompRoot()) && canBeRoot())
        	return this;
        return parent.getRootOfThis();
    }
    
    public void bringToFront(boolean canBecomeRoot) {
        try {
            if (!isVisual() || isJPopupMenu())
                return;
            
            if(editor != null && !editor.isProcessing()) {
                if(menuBarParent != null) {
                    menuBarParent.bringToFront(canBecomeRoot);
                    return;
                }
                if(canBecomeRoot) {
                	FormComponent rootOfThis = getRootOfThis();
                	if(rootOfThis != null && !rootOfThis.equals(editor.getRootComponent())) {
                        editor.setRootComponent(rootOfThis);
                        rootOfThis.markMainTree();
                        editor.setRootName(rootOfThis.getName());
                        editor.rebuildEditor();
                	}
                }
            }
            
            getRootComponent().clearCachedBounds();
            if (isCWT()) {
                com.cloudgarden.jigloo.typewise.TypewiseManager.bringToFront(this);
                return;
            }
            if (isSwing()) {
                if (component == null) {
                    if (getParent() != null)
                        getParent().bringToFront(canBecomeRoot);
                    if(component == null)
                    	return;
                }
                if(!(component instanceof java.awt.Window))
                	component.setVisible(true);
                getEditor().refreshGrid();
                	
                boolean needsUpdate = false;
                
                Container cont = component.getParent();
                Component lastComp = component;
                if (cont instanceof JTabbedPane) {
                    JTabbedPane tpane = (JTabbedPane) cont;
                    Component lsel = tpane.getSelectedComponent();
                    if (!lastComp.equals(lsel) || getSwtInSwingChild() != null) {
                    	if(lsel != null)
                    		lsel.setVisible(false);
                        tpane.setSelectedComponent(lastComp);
                        lastComp.setVisible(true);
                        needsUpdate = true;
                    }
                }
                if (parent != null && parent.getLayoutWrapper() != null) {
                    Container pc = (Container) parent.getComponent();
                    LayoutManager lm = parent.getLayoutWrapper().getSwingLayout(pc);
                    if (lm instanceof CardLayout) {
                        //need to set component's visible flag to false
                        //or the CardLayout won't think it needs to do anything
                        if(component != null)
                            component.setVisible(false);
                        ((CardLayout) lm).show(pc, (String) getLayoutDataWrapper().getLayoutData());
                        needsUpdate = true;
                    }
                }
//                if (parent != null && parent.isSubclassOf(JLayeredPane.class)) {
//                	JLayeredPane pc = (JLayeredPane) parent.getComponent();
//                	pc.moveToFront(component);
//                	needsUpdate = true;
//                }
                if (needsUpdate) {
                    if (parent != null) {
                        parent.updateUI();
                    } else {
                        updateUI();
                    }
                    editor.redrawRootNow();
                }
            } else {
                if (control instanceof Control
                        && (((Control) control).isDisposed() || ((Control) control).isVisible())) {
                	//v4.0M3
                	if(getParent() != null)
                		getParent().bringToFront(canBecomeRoot);
                    return;
                }
                getEditor().refreshGrid();
                if (getRootControl() != null) {
                    getRootControl().setEnabled(true);
                }
                if (getControl() instanceof CTabItem) {
                    CTabItem cti = (CTabItem) getControl();
                    ((CTabFolder) cti.getParent()).setSelection(cti);
                } else if (getControl() instanceof TabItem) {
                    TabItem ti = (TabItem) getControl();
                    ((TabFolder) ti.getParent())
                            .setSelection(new TabItem[] { ti });
                } else if (getControl() instanceof TableItem) {
                    TableItem ti = (TableItem) getControl();
                    ((Table) ti.getParent())
                            .setSelection(new TableItem[] { ti });
                }
                if (getParent() != null
                        && getParent().getControl() instanceof Composite
                        && getParent().getLayoutWrapper() != null) {
                    Layout layout = getParent().getLayoutWrapper()
                            .getSWTLayout();
                    if (layout instanceof StackLayout) {
                        Control tc = (Control) getControl();
                        if(tc == null || !tc.isDisposed()) {
	                        ((StackLayout) layout).topControl = tc;
	                        ((Composite) getParent().getControl()).layout();
                        }
                    }
                }
                if (getRootControl() != null)
                    getRootControl().setEnabled(false);
            }
            if (getParent() != null) {
                getParent().bringToFront(canBecomeRoot);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Control getRootControl() {
        if (getRootComponent() == null)
            return null;
        Object rc = getRootComponent().getControl();
        if(rc instanceof Control) {
            return (Control) rc;
        } else {
            if(rc != null)
                System.err.println("ROOT CONTROL IS NOT CONTROL! class="+rc+", "+rc.getClass());
            return null;
        }
    }

    public int getIndexInParent() {
        if (getParent() == null)
            return -1;
        Vector subs = getParent().getChildren();
        for (int i = 0; i < subs.size(); i++) {
            if (subs.elementAt(i).equals(this)) {
                return i;
            }
        }
        return -1;
    }

    public int getNonInheritedIndexInParent() {
        if (getParent() == null)
            return -1;
        int pos = 0;
        int cc = getParent().getChildCount();
        for (int i = 0; i < cc; i++) {
            if (!getParent().getChildAt(i).isInherited()) {
                if (getParent().getChildAt(i).equals(this)) {
                    return pos;
                }
                pos++;
            }
        }
        return -1;
    }

    public int getInheritedCount() {
        int c = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).isInherited()) {
                c++;
            }
        }
        return c;
    }

    public void setIndexInParent(int i) {
        if (!isSwing()) {
            if (getControl() != null) {
                if (getControl() instanceof Control) {
                    Control ctrl = (Control) getControl();
                    Composite par = ctrl.getParent();
                    if (par instanceof IWidgetManager) {
                        IWidgetManager widMan = (IWidgetManager) par;
                        widMan.setChildAt(i, ctrl);
                    } else { //
                    }
                    try {
                        fixLayoutChildData(getParent());
                        par.layout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (getParent() != null) {
            Vector subs = getParent().getChildren();
            if (i >= 0 && i < subs.size())
                subs.setElementAt(this, i);
        }
    }

    public boolean isLocalField() {
        if(editor == null)
            return false;
    	return getJavaCodeParser().isLocalField(this);
    }
    
    public boolean canMoveToParent(FormComponent parent) {
    	if(isSWTAWTInternal())
    		return getSWTAWTContainer().canMoveToParent(parent);
        if(parent == null)
            return false;
        if (!isVisual()) {
            if(isSubclassOf(javax.swing.Action.class)) {
                return parent.hasProperty("action");
            }
            return true;
        }
        if (parent.isNonVisualRoot())
            return true;
        if (!parent.isContainer())
            return false;
        if (parent.equals(this))
            return false;
        if (parent.isChildOf(this))
            return false;
        return SwingSWTMapper.canAddTo(parent, mainClass, style);
    }

    public boolean canAddToThis(Class objectClass, int style) { 
    	//System.out.println("CAN ADD TO THIS "+name);
        if (FormEditor.NON_VISUAL_LABEL.equals(name) || "object1".equals(name)) { 
        	//can add custom classes to this node
            if (objectClass == null)
                return true;
            return true;
        } 
        
        //SWT_AWT class is neither swing or swt, so don't want
        //to exclude adding a SWT_AWT to a swing or SWT
        if(objectClass != null) {
            
            if( Cacher.isAssignableFrom(AbstractAction.class, objectClass) && hasProperty("action"))
                return true;
            
        	if (isSwing() && ClassUtils.isSWT(objectClass))
        		return false;
        	if (isSWT() && ClassUtils.isSwing(objectClass))
        		return false;
        }
        
        if (FormEditor.MENU_COMPONENT_LABEL.equals(name)) {
            if (Menu.class.equals(objectClass))
                return true;
            if (isSwing()) {
                if ( Cacher.isAssignableFrom(JPopupMenu.class, objectClass))
                    return true;
                if ( Cacher.isAssignableFrom(JMenuBar.class, objectClass))
                    return true;
            }
            return false;
        }
        if (!isContainer())
            return false;
        return SwingSWTMapper.canAddTo(this, objectClass, style);
    }

    public boolean hasSameNameAs(FormComponent comp) {
        if (comp == null || getName() == null)
            return false;
        return getName().equals(comp.getName());
    }

    public void initLayoutConstraint(FormComponent fc) {
        LayoutDataWrapper ldw = null;
        String parLT = fc.getParentSuperLayoutType();
        if (fc.isSwing()) {
            if ("GridBag".equals(parLT)) {
                Object ld = new GridBagConstraints();
                ldw = new LayoutDataWrapper(ld, fc);
                ldw.setObject(ld);
            } else if ("Table".equals(parLT)) {
                fc.getLayoutDataWrapper().setObject("");
            } else if ("Mig".equals(parLT)) {
                fc.getLayoutDataWrapper().setObject("");
            } else if ("Group".equals(parLT)) {
                fc.getLayoutDataWrapper().setObject(null);
            } else if ("Anchor".equals(parLT)) {
                Object ld = new AnchorConstraint();
                ldw = new LayoutDataWrapper(ld, fc);
                ldw.setObject(ld);
            } else if ("Border".equals(parLT)) {
                fc.setLayoutData(BorderLayout.CENTER);
                fc.getLayoutDataWrapper().setObject(null);
            } else if ("Card".equals(parLT)) {
                fc.setLayoutData(fc.getNameInCode());
                fc.getLayoutDataWrapper().setObject(null);
            } else if (fc.getParent().isSubclassOf(JSplitPane.class)) {
                fc.setLayoutData(JSplitPane.LEFT);
                fc.getLayoutDataWrapper().setObject(null);
            } else if (fc.getParent().isSubclassOf(JLayeredPane.class)) {
                fc.setLayoutData(JLayeredPane.DEFAULT_LAYER);
                fc.getLayoutDataWrapper().setObject(null);
            }
        } else {
            if ("Grid".equals(parLT)) {
                Object ld = new GridData();
                ldw = new LayoutDataWrapper(ld, fc);
                ldw.setObject(ld);
            } else if ("Mig".equals(parLT)) {
            	Object ld = "";
            	ldw = new LayoutDataWrapper(ld, fc);
            	ldw.setObject(ld);
            } else if ("Form".equals(parLT)) {
                Rectangle parBnds = fc.getParent().getBounds();
                int x = parBnds.width / 3;
                int y = parBnds.height / 3;
                Object ld = new FormData();
                ldw = new LayoutDataWrapper(ld, fc);
                ldw.setObject(ld);
                JiglooUtils.initFormDataWrapper(ldw, x, y, fc.getParent());
            }
        }
        if (ldw != null) {
            fc.setLayoutDataWrapperSimple(ldw);
//            fc.setLayoutDataWrapper(ldw);
        }
    }

    public void moveToParent(FormComponent parent, int position) {
    	moveToParent(parent, position, true);
    }
    
    public void moveToParent(FormComponent parent, int position, boolean preserveSpacing) {
        boolean parentChanged = true;
        boolean layoutChanged = false;
        if (position > parent.getChildCount())
            position = parent.getChildCount();
        
        if (getParent() != null) {
            parentChanged = !getParent().equals(parent);
            String lt1 = getParentLayoutType();
            String lt2 = "";
            if (parent != null)
                lt2 = parent.getLayoutType();
            layoutChanged = lt1 == null || !lt1.equals(lt2);
        }

        int thisPos = getIndexInParent();

        if (parent.isJSplitPane() && !parentChanged) {
            String cons = (String) getLayoutDataWrapper().getLayoutData();
            //just make the constraint the opposite of what it was!
            if (JSplitPane.BOTTOM.equals(cons))
                cons = JSplitPane.TOP;
            else if (JSplitPane.TOP.equals(cons))
                cons = JSplitPane.BOTTOM;
            else if (JSplitPane.LEFT.equals(cons))
                cons = JSplitPane.RIGHT;
            else
                cons = JSplitPane.LEFT;

            setLayoutData(cons);
            adjustJSplitPaneConstraints();
            
            return;
        }

        if (!canMoveToParent(parent))
            return;

        if (layoutChanged) {
            //if the layout changes (because the parents have different layouts),
            // then the layout constraint should also (eg, if going from a GridBag
            // to a JTabbedPane) so remove the initial code from the cache.
            setPropertyValueCode("LAYOUT_CONSTRAINT", null);
//            initLayoutConstraint(this);
//            validateLayoutConstraint(getLayoutConstraint(),
//             getParentLayoutType(), this);
        }

        setEditorReload(true);
        boolean needsRebuild = false;
                
        if (position < 0)
            position = parent.getChildCount();
        
        if (!parentChanged && thisPos == position)
            return;
        
        boolean isJTab = parent.isSwing() && parent.isSubclassOf(JTabbedPane.class);
        
        String tab1 = null, tab2 = null;
        if (!parentChanged && thisPos < position)
            position--;
        if (!parentChanged && !isJTab) {
            moveTo(position);
            return;
        }
        Vector tabNames = null;
        Vector tabIcons = null;
        JTabbedPane jtp = null;
        if (isJTab) {
            if (!hasProperty("tabTitle")) {
                addSynthProperty("tabTitle", String.class, "");
                propertyDescriptors = null;
                initProperties();
            }
            setTabTitle(getTabTitle());
//            setTabTitle(getNameInCode());
            tabNames = new Vector();
            tabIcons = new Vector();
            jtp = (JTabbedPane) parent.getComponent();
            if(jtp != null) {
	            for (int i = 0; i < parent.getChildCount(); i++) {
	                tabNames.add(jtp.getTitleAt(i));
	                tabIcons.add(jtp.getIconAt(i));
	            }
            }
        } else {
            if (hasProperty("tabTitle")) {
            	if(properties != null)
            		properties.remove("tabTitle");
                propertyDescriptors = null;
                initProperties();
            }
        }

//        if(layoutChanged)
        if(parentChanged)
        	getLayoutDataWrapper().setObject(null);
        
        if (isSwing()) {
            getParent().removeFromGroupLayout(this, preserveSpacing);
            if (component == null) {
                editor.removeFromOutline(this);
            } else {
                Container par = component.getParent();
                editor.removeFromOutline(this);
                removeComponent(component, par);
            }
            if (getParent() != null)
                getParent().getChildren().remove(this);
            
            parent.addSwingComponentToThis(this, position);
            
        } else if (isCWT()) {
            editor.removeFromOutline(this);
            if (getParent() != null)
                getParent().getChildren().remove(this);
            com.cloudgarden.jigloo.typewise.TypewiseManager.removeWidget(getParent(),
                    this);
            com.cloudgarden.jigloo.typewise.TypewiseManager.addWidgetTo(parent,
                    this, position);
        } else if (isSWT()) {
            needsRebuild = true;
            editor.removeComponent(this, false);
            editor.addComponent(this);
        }

        setAllExistsInCode(true);
        setParent(parent);
        
//        if(layoutChanged) {
        if(parentChanged) {
        	String name = null;
        	if(layoutDataWrapper != null)
        		name = layoutDataWrapper.getName();
        	layoutDataWrapper = new LayoutDataWrapper(this);
        	initLayoutConstraint(this);
        	if(name != null)
        		layoutDataWrapper.setName(name);
        }
        
        //System.out.println(
        //"MTP (2) " + position + ", " + parent.getChildCount());
        if (!parent.getChildren().contains(this))
            parent.addToChildrenVector(position, this);
        if (needsRebuild) {
            if (parent.getControl() instanceof IWidgetManager) {
                parent.populateControls(parent.getWidgetParent(), editor, true);
            } else {
                rebuildParent(false);
            }
        }

        if (jtp != null) {
            try {
                int tns = tabNames.size();
                if (tns >= thisPos && tns > position) {
                    String tn = (String) tabNames.elementAt(thisPos);
                    tabNames.remove(thisPos);
                    tabNames.add(position, tn);
                    Icon ti = (Icon) tabIcons.elementAt(thisPos);
                    tabIcons.remove(thisPos);
                    tabIcons.add(position, ti);
                    for (int i = 0; i < tabNames.size(); i++) {
                        jtp.setTitleAt(i, (String) tabNames.elementAt(i));
                        jtp.setIconAt(i, (Icon) tabIcons.elementAt(i));
                    }
                }
                jtp.setTitleAt(position, getTabTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        if(parentChanged) {
//        	String name = null;
//        	if(layoutDataWrapper != null)
//        		name = layoutDataWrapper.getName();
//        	layoutDataWrapper = new LayoutDataWrapper(this);
//        	initLayoutConstraint(this);
//        	if(name != null)
//        		layoutDataWrapper.setName(name);
//        }
        
        if(!editor.isProcessing()) {
	        String lt = parent.getSuperLayoutType();
			if (parent.usesAbsoluteTypeLayout()
					|| parent.usesGridTypeLayout()) {
				Rectangle bounds = editor.getBoundsFromFrame(this, parent, true);
				if(bounds != null)
					setBounds(bounds, true, true, false, parentChanged);
			}        
        }
        adjustJSplitPaneConstraints();

        //setParent(parent);
//        if (editor != null) {
//            editor.reload();
//        }
    }

    public void moveDown() {
        int ind = getIndexInParent();
        moveTo(ind + 1);
    }

    public void moveUp() {
        int ind = getIndexInParent();
        moveTo(ind - 1);
    }

    public void moveTo(int pos) {
        try {
            if (getParent() == null)
                return;
            setEditorReload(true);
            int ind = getIndexInParent();
            int numc = getParent().getChildCount();
            if (pos < 0 || pos >= numc)
                return;
            boolean needsRebuild = false;
            String tab1 = null, tab2 = null;
            if (isVisual()) {
                if (isCWT()) {
                    needsRebuild = false;
                } else if (isSwing()) {
                    tab1 = getParent().getChildAt(pos).getTabTitle();
                    tab2 = getTabTitle();
                    getParent().removeFromGroupLayout(this);
                    if (getComponent() != null) {
                        Container par = getComponent().getParent();
                        removeComponent(component, par);
                        parent.addSwingComponentToThis(this, pos);
                    }
                } else {
                    Object parWidget = getParent().getControl();
                    if (parWidget instanceof IWidgetManager && getControl() != null) {
                        IWidgetManager widMan = (IWidgetManager) parWidget;
                        widMan.moveTo((Control) getControl(), pos);
                        fixLayoutChildData(getParent());
                        ((Composite) parWidget).layout();
                    } else {
                    	if(getControl() != null)
                    		needsRebuild = true;
                    }
                }
            }
            
            Vector subs = getParent().getChildren();
            subs.remove(this);
            subs.add(pos, this);
            if (needsRebuild && !isInherited()) {
                rebuildParent(false); //getParent().rebuild(false);
            }
            if (isSwing()) {
                if (tab1 != null && getParent() != null
                        && getParent().getChildAt(ind) != null) {
                    getParent().getChildAt(ind).setTabTitle(tab1);
                }
                if (tab2 != null) {
                    setTabTitle(tab2);
                }
            }
            
            //if (propNames != null && hasProperty("visible"))
            //setPropertyValueDirect("visible", getPropertyValue("visible"));

            //updateUI();
            //bringToFront();
            if (editor != null) { //editor.refresh(true);
                //editor.setDirty(true);
                //getParent().updateUI();
                //editor.realignWindowFrame(this);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error moving " + this + " to " + pos
                    + " in " + getParent());
        }
    }

    public boolean canMoveUp() {
        if (getParent() == null)
            return false;
        if (isInherited())
            return false;
        //System.out.println("INDEX IN PARENT = " + getIndexInParent());
        return getNonInheritedIndexInParent() > 0;
    }

    public boolean canMoveDown() {
        if (getParent() == null)
            return false;
        if (isInherited())
            return false;
        int ind = getIndexInParent();
        if (ind == -1)
            return false;
        int numKids = getParent().getChildren().size();
        //System.out.println("NUM KIDS = " + numKids);
        return ind < numKids - 1;
    }

    public Object convertToWrapper(Object name, Object value) {
        //System.out.println("PRECONVERT " + name + ", " + value);
        Object wrapper = WrapperFactory.createWrapper(name, value, this);
        if (wrapper != null)
            return wrapper;

        if (value instanceof Insets) {
            value = new InsetsPropertySource((Insets) value);
        } else if (value instanceof Border) {
            value = new BorderPropertySource((Border) value, this,
                    (String) name, this);
        } else { // if (value instanceof Integer) {
            String[] fieldOpts = FieldManager.getFieldOptions((String) name,
                    getMainClass());
            if (fieldOpts != null) {
                if (value instanceof FieldWrapper) {
                    ((FieldWrapper) value).setFields(fieldOpts);
                } else {
                    value = new FieldWrapper(value, (String) name,
                            fieldOpts, this);
                }
            }
        }
        value = ConversionUtils.convertToSWT(value);
        //System.out.println(
        //"PRECONVERTED to " + value + ", " + value.getClass());
        return value;
    }

    private void addComponentToParent(Container parent) {
        int pos = getNonInheritedIndexInParent();
    	addComponentToParent(parent, pos);
    }

    private void addComponentToParent(Container parent, int pos) {
        try {
            if (isSubclassOf(JPopupMenu.class) && parent instanceof JComponent) {
                final Container fparent = parent;
                parent.addMouseListener(new MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        if (e.isPopupTrigger())
                            ((JPopupMenu) component).show(fparent, e.getX(), e
                                    .getY());
                    }

                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        if (e.isPopupTrigger())
                            ((JPopupMenu) component).show(fparent, e.getX(), e
                                    .getY());
                    }
                });
//                ((JTextComponent)parent).setComponentPopupMenu(component);
                return;
            }
            
            //System.out.println("ADD COMP TO PAR " + this +", pos=" + pos + ", " + parent);
            if(parent instanceof JMenu)
                parent = ((JMenu)parent).getPopupMenu();
            
            Object ld = getLayoutDataWrapper().getLayoutData();
            int ppc = parent.getComponentCount();
            if (pos < 0 || pos > ppc)
                pos = ppc;
            if (parent instanceof JScrollPane) {
                parent.add(component, pos);
                ((JScrollPane) parent).setViewportView(component);
            } else if (parent instanceof JTabbedPane) {
                parent.add(component, pos);
//                String tabTitle = (String) getConstraint("tabTitle");
//                setTabTitle(getTabTitle());
            } else if (parent instanceof JSplitPane) {
                String dirn = (String) ld;
                if ("left".equals(dirn))
                    parent.add(component, JSplitPane.LEFT, pos);
                else if ("right".equals(dirn))
                    parent.add(component, JSplitPane.RIGHT, pos);
                else if ("top".equals(dirn))
                    parent.add(component, JSplitPane.TOP, pos);
                else if ("bottom".equals(dirn))
                    parent.add(component, JSplitPane.BOTTOM, pos);
            } else if (parent.getLayout() instanceof java.awt.BorderLayout) {
                String position = (String) ld;
                if ("South".equals(position) || "Last".equals(position))
                    parent.add(component, java.awt.BorderLayout.SOUTH, pos);
                else if ("North".equals(position) || "First".equals(position))
                    parent.add(component, java.awt.BorderLayout.NORTH, pos);
                else if ("East".equals(position) || "After".equals(position))
                    parent.add(component, java.awt.BorderLayout.EAST, pos);
                else if ("West".equals(position) || "Before".equals(position))
                    parent.add(component, java.awt.BorderLayout.WEST, pos);
                else
                    parent.add(component, java.awt.BorderLayout.CENTER, pos);
            } else if (parent.getLayout() instanceof GridBagLayout) {
                if (ld instanceof GridBagConstraints)
                    parent.add(component, ld, pos);
                else
                    parent.add(component, pos);
            } else if (parent.getLayout() instanceof AnchorLayout) {
                if (!(ld instanceof AnchorConstraint)) {
                	getLayoutDataWrapper(true).getPropertyDescriptors();
                	setLayoutData(getLayoutDataWrapper().getLayoutData());
                }
                parent.add(component, ld, pos);
            } else if (parent.getLayout() instanceof TableLayout) {
                if (ld != null)
                    parent.add(component, ld, pos);
                else
                    parent.add(component, "1, 1", pos);
            } else if ("Enfin".equals(getParentLayoutType()) && editor.canUseEnfinLayout()) {
                if (ld instanceof String)
                    parent.add(component, EnfinLayoutHandler.toFieldValue((String) ld), pos);
                else
                    parent.add(component, pos);
            } else if (parent.getLayout() instanceof GroupLayout) {
                //do nothing, because the GroupLayout will add the components itself
                //If you add them here it results in an IllegalArgumentException for JLayeredPane parents
                //when the GroupLayout.add method is invoked
                //v4.0M2
            } else if (parent.getLayout() instanceof com.jgoodies.forms.layout.FormLayout) {
                if (ld instanceof CellConstraints) {
                	ld = validateLayoutConstraint(ld, "Form", this);
                    parent.add(component, ld, pos);
                } else {
                	ld = new CellConstraints();
                	getLayoutDataWrapper().setObject(ld);
                    parent.add(component, ld, pos);
                }
            } else if (parent.getLayout() instanceof CardLayout) {
                String cardName = (String) ld;
                if (cardName == null) {
                    cardName = getName();
                    getLayoutDataWrapper().setObject(cardName);
                }
                parent.add(component, cardName, pos);
            } else if (parent instanceof JInternalFrame) {
                ((JInternalFrame) parent).getContentPane().add(component); //,getLayoutData(),pos);
            } else {
        		//for custom layouts
                if(ld != null) {
                    try {
                        parent.add(component, ld, pos);
                    } catch(Throwable t) {
                    	jiglooPlugin.handleError(getShell(), "Error adding component ",
                                "Error adding component "+this+", "+ld, t);
                    }
                } else if(ld != null) {
                	//this is for TableLayout constraints
                    try {
                        parent.add(component, ld, pos);
                    } catch(Throwable t) {
                    	parent.add(component, pos);
                    	if(settingLayoutConstraint) {
                    		MessageDialog.openError(getShell(), "Invalid layout constraint", "Invalid layout constraint: "+ld);
                    	} else {
                    		jiglooPlugin.handleError(getShell(), "Error adding component ",
                    				"Error adding component "+this+", "+ld, t);
                    	}
                    }
                } else {
                    parent.add(component, pos);
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.writeToLog("Error adding " + this + " to " + pos
                    + ", " + parent + ", " + getParent()+", "+t);
            t.printStackTrace();
        }
    }

    public void setComponent(Component component) {
        this.component = component;
        if (component != null)
            disposed = false;
    }

    public void setNonVisualObject(Object nvObj) {
        nonVisualObject = nvObj;
        if (nvObj != null)
            disposed = false;
    }

    public Object getNonVisualObject() {
        return nonVisualObject;
    }

    protected Object methodObject = null;
    
    public Object getObject(boolean createIfNecessary) {
    	if(methodObject != null)
    		return methodObject;
    	
    	if (!isVisual()) {
    		if (nonVisualObject == null && createIfNecessary) {
    			setInMainTree(true);
    			populateNonVisualComponents(editor);
    		}
    		return nonVisualObject;
    	}
    	
    	if(isCWT())
    		return nonVisualObject;
    	
    	if (isSwing()) {
    		if(getComponent() != null) {
    			return getComponent();
    		} else if(isBaseComponent()) {
    		    //version 3.9.3
    		    return editor.getRootObject();
//    		    return null;
    		} else if(createIfNecessary) {
    			setInMainTree(true);
    			populateComponents(null, getEditor());
    			return getComponent();
    		}
    	} else if(isSWT()) {
        	if (getControl() != null)
        		return getControl();
        	else if (createIfNecessary) {
        	    //v4.0.0
                setInMainTree(true);
                if(editor.getMainControl() == null || editor.getMainControl().isDisposed()) {
                    FormComponent root = editor.getJavaCodeParser().getRootComponent();
                    editor.setRootComponent(root);
                    root.setName("this");
                    editor.addComponent(root);
                    editor.setEditorMode(editor.getJavaCodeParser());
                    editor.populateRoot();
                }
                if (isRoot())
                    populateControls(editor.getMainControl(), getEditor(), true);
                else
                    populateControls(getParent().getControl(), getEditor(), true);
                return getControl();
        	}
    	}
    	return null;
    }

    public void setControl(Widget control) {
        if(control == null && isJFaceForm()) {
            if(this.control instanceof Control)
                ((Control)this.control).dispose();
            formToolkit = null;
        }
        this.control = control;
        if (control != null && !control.isDisposed())
            disposed = false;
    }

    public void setParent(FormComponent newParent) {
        setParent(newParent, false);
    }
        
    public void setParent(FormComponent newParent, boolean addToChildren) {
//    	System.out.println("SET PARENT "+newParent.getName()+" for "+this.getName());
        if (parent != null && !parent.equals(newParent)) {
            parent.getChildren().remove(this);
        }
        parent = newParent;
//        if (newParent != null && classType == 0)
//            setSwing(newParent.isSwing());
        if(addToChildren) {
            if(!newParent.getChildren().contains(this))
                newParent.getChildren().add(this);
        }
    }

    public boolean hasStyle(int style) {
        return (getStyle() & style) != 0;
    }

    public String getStyleString() {
        if(styleString == null)
            styleString =SWTStyleManager.getStyleString(this, true);
        return styleString;
    }

    public void setStyle(int style, boolean update) {
        try {
            this.style = style;
            styleString = null;
            if (update) {
                setEditorReload(true);
                rebuildParent(false);
//                getEditor().setDirtyAndUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        boolean rebuild = false;
        if (style != this.style)
            rebuild = true;
        setStyle(style, false);
        if (rebuild && control != null)
            rebuildParent(false);
    }

    public boolean isA(Class cls) {
        return getClassName().equals(cls.getName());
    }

    public boolean isA(String cls) {
        return getClassName().equals(cls);
    }

    public boolean hasTextlikeProperty() {
        return (propNames.contains("text") || propNames.contains("label")
        		|| (parent.isSwing() && parent.isA(JTabbedPane.class)));
    }

    public boolean hasSuperClass(String clsName) {
    	Class mc = getMainClass();
    	while(mc != null) {
    		if(mc.getName().equals(clsName))
    			return true;
    		mc = mc.getSuperclass();
    	}
    	return false;
    }
    
    public boolean isSubclassOf(Class cls) {
    	if(className == null || "UNKNOWN".equals(className) || cls == null)
//        if (isNonVisualRoot())
            return false;
        try {
            if (nonVisualObject != null && nonVisualObject.getClass().getName().equals(Class.class.getName())) {
                return Cacher.isAssignableFrom(cls, (Class) nonVisualObject);
            }
            
            if(actualRootClass != null && Cacher.isAssignableFrom(cls, actualRootClass) && isRoot())
                    return true;
            
            return Cacher.isAssignableFrom(cls, getMainClass());
        
        } catch (Exception e) {
            System.err.println("Error in isSubclassOf: " + cls + ", "
                    + getMainClass() + ", " + e);
        }
        return false;
    }

    public void rebuildParent(boolean newClass) {
        
        FormComponent par = getParent();
        if (par == null || par.isRoot()) {
            rebuild(newClass);
        } else if (SWTUtils.isItem(par)) {
            par.getParent().rebuild(newClass);
        } else {
            par.rebuild(newClass);
        }
    }

    public void clearProperties() {
        properties = null;
    }

    public void clearPropertyNames() {
        propertyDescriptors = null;
        propTypes = null;
        propNames = null;
        //eventWrapper = null;
        //layoutWrapper = null;
    }

    public void clearPropertyNamesAndEvents() {
        propertyDescriptors = null;
        propTypes = null;
        propNames = null;
        eventWrapper = null;
        //layoutWrapper = null;
    }

    public boolean hasProperty(String name) {
        if (propNames == null) {
            Vector tempPropNames = PropertySourceFactory
                    .findPropertyNames(getConvertedMainClass(getMainClass(true)));
            //initProperties();
            if (tempPropNames.contains(name))
                return true;
            if (isCWT())
                return com.cloudgarden.jigloo.typewise.TypewiseManager
                        .hasSynthProperty(this, name);
            return false;
        }
        return propNames.contains(name);
    }

    public void rebuild(boolean newClass) {

        if (isInherited()) {
            getParent().rebuild(newClass);
            return;
        }
        if (isA(Menu.class) || isA(MenuItem.class))
            return;
        try {
            if (newClass) {
                clearPropertyNamesAndEvents();
            }
        	
            Vector selected = (Vector) editor.getSelectedComponents().clone();
            
            editor.hideWindowFrames(editor.getSelectedComponents());
            editor.setRebuilding(true);
            
            if (getParent() != null) {
                int pos = getIndexInParent();
                if (getParent().isSwing()) {
                    dispose();
                    Container par = null;
                    if(getParent().getComponent() instanceof Container)
                    	par = (Container) getParent().getComponent();
                    populateComponents(par, editor);

                } else if (getParent().isSWT()) {
                    Object ld = null;
                    LayoutDataWrapper ldw = layoutDataWrapper;
                    layoutDataWrapper = null;
//                    if (control instanceof Control)
//                        ld = ((Control) control).getLayoutData();
                    Composite par = getWidgetParent();
                    dispose();

                    layoutDataWrapper = ldw;
                    getParent().populateControls(getParent().getWidgetParent(), editor, true);
//                    if (ld != null && control instanceof Control)
//                        setControlLayoutData(ld);
//                    setLayoutDataWrapper(ldw);
                    
                    setIndexInParent(pos);
                    
                } else if (isCWT()) {
                    com.cloudgarden.jigloo.typewise.TypewiseManager.rebuild(this);
                    //System.err.println("NEED TO IMPLEMENT REBUILD FOR CWT");
                }
            } else {
                if (isRoot()) {
                    int pos = getIndexInParent();
                    if (isSwing()) {
                        dispose();
                        layoutWrapper = null;
                        Container par = getEditor().getSwingMainPanel();
                        populateComponents(par, editor);
                        if (!isJFrame() && !isJDialog() && !isJWindow()) {
                            par.remove(component);
                            SwingHelper.addComponent(component, par, pos);
                        } else {
                            getEditor().getAWTControl().layoutInFrame();
                        }
                    } else if (isSWT()) {
                        //Widget par = getWidgetParent();
                        FormData fd = (FormData) getRootControl()
                                .getLayoutData();
                        Control ctrl = null;
//                        if(fd != null && fd.top != null)
                            ctrl = fd.top.control;
                        dispose();
                        populateControls(editor.getFrameComp(), editor, true);
//                        if(fd != null && fd.top != null)
                            fd.top.control = ctrl;
                        
                        getRootControl().setLayoutData(fd);
                        editor.getFrameComp().layout(true);
                    } else if (isCWT()) {
                        com.cloudgarden.jigloo.typewise.TypewiseManager
                                .rebuild(this);
                    }
                }
            }
            refresh();
            editor.setRebuilding(false);
            if (getParent() != null)
                getParent().updateUIForAll();
            else
                updateUIForAll();
//            editor.reload();
            editor.setSelectedComponents(selected);
            
//            editor.realignWindowFrame(this);
        } catch (Exception e) {
            jiglooPlugin.handleError(e);
        }
    }

    private static HashMap boundsMap;
    
    public void fillMapWithBounds() {
    	if(boundsMap == null)
    		boundsMap = new HashMap();
    	if(isRoot())
    		boundsMap.clear();
    	Rectangle b = null;
        if(getParent() != null) {
	        String plt = getParentSuperLayoutType();
	        if(getParent().usesAbsoluteTypeLayout()) {
//		        if("Form".equals(plt) || "Anchor".equals(plt) || plt == null || "Absolute".equals(plt)) {
	        	b = getBounds(true, false);
	        	boundsMap.put(getName(), b);
//	        	System.err.println("GOT BOUNDS "+b+" for "+this);
	        }
        }
    	for(int i=0;i<getChildCount(); i++) {
    		getChildAt(i).fillMapWithBounds();
    	}
    }
    
    public void setBoundsFromMap() {
    	if(boundsMap == null)
    		return;
    	Rectangle b = (Rectangle)boundsMap.get(getName());
    	if(b != null) {
    		setBounds(b, true);
//        	System.err.println("SET BOUNDS "+b+" for "+this);
    	}
    	for(int i=0;i<getChildCount(); i++) {
    		getChildAt(i).setBoundsFromMap();
    	}
    }
    
    public void toggle(IProgressMonitor pm) {
        if (isInherited) {
            return;
        }
//        System.err.println("TOGGLING " + this +", SWING=" + isSwing());
        if (pm != null) {
            pm.setTaskName("Toggling ... " + this);
            pm.worked(1);
        }
        String title = getTabTitle();
        LayoutWrapper lw = null;
        String lwName = null;
        boolean toSWT = isSwing();
        if(isRoot())
        	fillMapWithBounds();
        Rectangle b = (Rectangle)boundsMap.get(getName());
        dispose();
        
        try {

            lw = getLayoutWrapper();
            lwName = lw.getName();
            //toggle layout first, because the new layout can depend on the SWT class
            lw.toggle(!toSWT);

            doClean();
            
            setInMainTree(true);

            if (toSWT) {
                Class cls = getMainClass();
                Object border = getPropertyValue("border");
                Object[] info = null;
                if(isRoot()) {
                	info = (Object[]) SwingSWTMapper.get(JPanel.class.getName());
                } else {
	                while (info == null && cls != null) {
	                    info = (Object[]) SwingSWTMapper.get(cls.getName());
	                    cls = cls.getSuperclass();
	                }
                }
                if (info != null) {
                    style = ((Integer) info[1]).intValue();
                    String ncn = (String) info[0];
                    //convert JPanels with titled borders to Groups
                    if (ncn.equals(OrderableComposite.class.getName())
                            && border != null) {
                        if (border instanceof BorderPropertySource) {
                            border = ((BorderPropertySource) border)
                                    .getBorder();
                        }
                        if (border instanceof TitledBorder) {
                            ncn = OrderableGroup.class.getName();
                            putProperty("text", ((TitledBorder) border)
                                    .getTitle());
                            setSetProperty("text");
                        }
                    }
                    setClassName(ncn);
                    //System.err.println("SET CLASS NAME " + info[0]);
                } else if (isA(JSeparator.class)) {
                    //parent will already have been converted to SWT
                    if (getParent().isA(Menu.class)
                            || getParent().isA(MenuItem.class)) {
                        setClassName(MenuItem.class.getName());
                    } else if (getParent().isA(ToolBar.class)) {
                        setClassName(ToolItem.class.getName());
                    } else {
                        setClassName(Label.class.getName());
                    }
                    style = SWT.SEPARATOR;
                } else {
                    System.err.println("toSWT: Class " + className
                            + " not found in SwingSWT map");
                    putProperty("text", className);
                    setSetProperty("text");
                    setClassName(Group.class.getName());
                    style = SWT.NULL;
                }

                Object or = getPropertyValue("orientation");
                if (or != null) {
                    int ior = -1;
                    if (or instanceof Integer)
                        ior = ((Integer) or).intValue();
                    else if (or instanceof FieldWrapper)
                        ior = ((Integer) ((FieldWrapper) or).getValue())
                                .intValue();
                    if (ior == SwingConstants.VERTICAL)
                        setStyle(style | SWT.VERTICAL);
                    else
                        setStyle(style | SWT.HORIZONTAL);
                }

                if (!isSubclassOf(JFrame.class) && !isSubclassOf(JApplet.class)
                        && !isSubclassOf(JDialog.class)
                        && !isSubclassOf(JWindow.class)
                        && !isSubclassOf(JInternalFrame.class)) {
                    Object sz = getPropertyValue("preferredSize");
                    //use setProps.contains instead of hasProperty, since
                    //propertyNames has been cleared
                    if (sz != null && setProps.contains("preferredSize")) {
                        putProperty("size", sz);
                        setSetProperty("size");
                    }
                }
                toggleImageProperty("icon", "image");
                setClassType(TYPE_SWT);
            } else {
                //======= TO SWING ==================
                //System.err.println("LOOKING FOR "+className + "|" + getStyle());
                Class cls = getMainClass();
                String cn = null;
                while (cn == null && cls != null) {
                    String cn1 = cls.getName() + "|";
                    cn = (String) SwingSWTMapper.get(cn1 + getStyle());
                    String cand = null;
                    if (cn == null) {
                        if (jiglooPlugin.DEBUG)
                            System.err.println("Didn't find mapping for " + cn1
                                    + getStyle());
                        Iterator it = SwingSWTMapper.getIterator();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            if (key.startsWith(cn1 + SWT.NULL) && cn == null) {
                                cn = (String) SwingSWTMapper.get(key);
                            }
                            if (key.startsWith(cn1)) {
                                int style = 0;
                                try {
                                    String stStr = key.substring(cn1.length());
                                    style = Integer.parseInt(stStr);
                                } catch (Throwable t) {
                                }
                                if ((style & getStyle()) != 0)
                                    cn = (String) SwingSWTMapper.get(key);
                                else
                                    cand = (String) SwingSWTMapper.get(key);
                            }
                        }
                    }
                    if (cn == null)
                        cn = cand;
                    cls = cls.getSuperclass();
                }

                if (cn == null) {
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("toSwing: Class " + className
                                + " not found in SwingSWT map");
                    putProperty("text", className);
                    setSetProperty("text");
                    setClassName(JLabel.class.getName());
                } else {
                    if ((className.equals(Group.class.getName()) 
                            || className.equals(OrderableGroup.class.getName()))
                            && cn.equals(JPanel.class.getName())) {
                        Object btitle = getPropertyValue("text");
                        if (btitle instanceof String) {
                            BorderPropertySource bps = new BorderPropertySource(
                                    new TitledBorder((String) btitle), this,
                                    "border", this);
                            bps.setPropertyValue("font",
                                    getRawPropertyValue("font"), false);
                            setPropertyValueInternal("border", bps, false);
                        }
                    }
                    if (className.equals(Label.class.getName())
                            && (getStyle() & SWT.SEPARATOR) != 0) {
                        cn = JSeparator.class.getName();
                    }
                    if (className.equals(ToolItem.class.getName())
                            && (getStyle() & SWT.SEPARATOR) != 0) {
                        cn = JSeparator.class.getName();
                    }
                    if ((getStyle() & SWT.VERTICAL) != 0)
                        setPropertyValueInternal("orientation", 
                                new Integer(SwingConstants.VERTICAL), false);
                    if ((getStyle() & SWT.HORIZONTAL) != 0)
                        setPropertyValueInternal("orientation", 
                                new Integer(SwingConstants.HORIZONTAL), false);
                    setClassName(cn);
                }
                if (isRoot() && editor.getMenuBar() != null) {
                    if (!canHaveJMenuBar())
                        setClassName(JFrame.class.getName());
                }
                Object sz = getPropertyValue("size");
                if (sz != null && (setProps.contains("size") )) {
                    putProperty("preferredSize", sz);
                    setSetProperty("preferredSize");
                }
                toggleImageProperty("image", "icon");
                setClassType(TYPE_SWING);
            }

            layoutDataWrapper = null;
            setOldLDW();
        } catch (Exception e) {
            System.err.println("Error toggling " + this + ", " + className);
            jiglooPlugin.handleError(e);
        }

        //don't toggle isSwing because now there is also isSWT and isCWT,
        //and because isSwing is set by setClassName
        //isSwing = !isSwing;
        if (toSWT) {
            if (isA(MenuItem.class) && getChildCount() > 0) { 
                //need to insert a menu here
                FormComponent fcm = FormComponent.newFormComponent(getEditor(), Menu.class.getName());
                fcm.setEditor(getEditor());
                fcm.setClassType(FormComponent.TYPE_SWT);
                fcm.setInMainTree(true);
                fcm.setClassName(Menu.class.getName());
                Vector children = components;
                components = null;
                add(fcm);
                fcm.setExistsInCode(true);
                fcm.addToCode();

                //this sets all the children to "isSwing = false" (from  setParent)
                fcm.setSubComponents(children);
                //so need to reset the children to "isSwing = true" so they
                // will be toggled right
                for (int i = 0; i < children.size(); i++)
                    ((FormComponent) children.elementAt(i)).setClassType(FormComponent.TYPE_SWING);
                fcm.toggleChildren(pm);
                return;
            }
        }

        toggleChildren(pm);
        setTabTitle(title);

        initProperties();

        if (propertyValueCode != null) {
            //the "text" property code might be externalized, so preserve it
            String text = getPropertyCode("text");
            propertyValueCode.clear();
            if (text != null)
                setPropertyValueCode("text", text);
        }
        
        if (lw != null) {
            if ((!toSWT && canSetSwingLayout()) || (toSWT && canSetLayout())) {
                lw.setSet(true);
                setLayoutWrapper(lw, true, true);
            } else {
                lw.setSet(false);
                setProps.remove("layout");
            }
        }
        
        if(getParent() !=null && getParent().isJSplitPane()) {
        	if(getIndexInParent() == 0)
        		getLayoutDataWrapper().setObject(JSplitPane.LEFT);
        	else
        		getLayoutDataWrapper().setObject(JSplitPane.RIGHT);
        }
        
    }

    private void toggleImageProperty(String from, String to) {
        if (!setProps.contains(from))
            return;
        Object fprop = properties.get(from);
        if (fprop instanceof IconWrapper) {
            IconWrapper icw = (IconWrapper) fprop;
            if (icw.getName() == null)
                return;
            fprop = new ImageWrapper(icw.getName(), this);
        } else if (fprop instanceof ImageWrapper) {
            ImageWrapper imw = (ImageWrapper) fprop;
            if (imw.getName() == null)
                return;
            fprop = new IconWrapper(imw.getName(), this);
        }
        if (fprop != null) {
            putProperty(to, fprop);
            setSetProperty(to);
        }
    }

    //	public static boolean isVisual(Class clazz) {
    //		return JiglooUtils.isVisual(clazz))
    //	}

    protected Boolean isVisual = null;
    
    public boolean isVisual() {
    	if(isVisual != null)
            return isVisual.booleanValue();
    	
        if (isNonVisualRoot()) {
            isVisual = Boolean.FALSE;
        } else if (isExtraCompRoot()) {
            isVisual = Boolean.FALSE;
        } else if(jiglooPlugin.canUseSWT_AWT() && isA(SWT_AWT.class)) {
            isVisual = Boolean.TRUE;
        } else  if (nonVisualObject == null) {
            isVisual = new Boolean(ClassUtils.isVisual(getMainClass()));
        } else if (isCWT()) {
            isVisual = Boolean.TRUE;
        } else {
            isVisual = Boolean.FALSE;
        }
        return isVisual.booleanValue();
    }

    protected void toggleChildren(IProgressMonitor pm) {
        if (components != null) {
            if (components.size() == 1) {
                //remove intermediate Menu widget when toggling from SWT to
                // Swing
                FormComponent fc = (FormComponent) components.firstElement();
                if (fc.isA(Menu.class)
                        && (fc.getStyle() == SWT.NULL || (fc.getStyle() & SWT.DROP_DOWN) != 0)) {
                    //System.out.println("TOGGLE CHILDREN REMOVING "+fc);
                    Vector children = fc.getChildren();
                    fc.setExistsInCode(false);
                    getJavaCodeParser().removeFromCode(fc);
                    fc.setSubComponents(null);
                    setSubComponents(children);
                    for (int i = 0; i < children.size(); i++)
                        ((FormComponent) children.elementAt(i)).setClassType(FormComponent.TYPE_SWT);
                    toggleChildren(pm);
                    return;
                }
            }
            for (int i = 0; i < components.size(); i++) {
                FormComponent fc = (FormComponent) components.elementAt(i);
                fc.toggle(pm);
                if (isJSplitPane()) {
                    fc.adjustJSplitPaneConstraints();
                }
            }
        }
    }

    public boolean canSetSwingLayout() {
        return isJPanel() || isJFrame() || isJApplet() || isJDialog() || isJWindow()
                || isJInternalFrame();
    }

    public void populateNonVisualComponents(FormEditor editor) {
//        System.err.println("POP NON-VIS "+this+", "+isInMainTree());
//        new Exception().printStackTrace();
        if(isInMainTree()) {
	        if (!isNonVisualRoot()) {
	            if (nonVisualObject == null && !isInherited()) {
	                setEditor(editor);
	                Object comp = null;
	                if (className != null) {
	                    try {
	                        comp = makeNonVisualObject(className);
	                    } catch (Exception ex) {
	                    	jiglooPlugin.handleError(editor.getSite().getShell(),
	                                "Error creating " + className,
	                                "Error creating " + className, ex);
	                    }
	                }
	                String unknownClass = null;
	                if (comp == null) {
	                    unknownClass = className;
	                    comp = new Object();
	                    setClassName(comp.getClass().getName());
	                    classCreated = false;
	                } else {
	                    classCreated = true;
	                }
	                if(!isVisual())
	                    setNonVisualObject(comp);
	                else if(isSWT() && comp instanceof Widget)
	                    setControl((Widget)comp);
	                else if(isSwing() && comp instanceof Component)
	                    setComponent((Component)comp);
	                
	                //System.out.println("MADE NVOBJ " + comp);
	                initProperties();
	                if (editor != null)
	                    editor.addComponent(this);
	            }
                invokeStoredMethodCalls();
	        }
        }
        if(isA(Display.class))
            return;
         Vector comps = getChildren();
        for (int i = 0; i < comps.size(); i++) {
            FormComponent fc = (FormComponent) comps.elementAt(i);
            
            //so that Shell children of Displays won't be turned into non-visual objects
            if(!fc.isVisual()) {
                fc.setParent(this);
                addToChildrenVector(-1, fc);
            } else if(!fc.isInherited()) {
                //don't move inherited childern of display etc
                if(fc.getParent() == null || !fc.getParent().isVisual()) {
                    fc.setParent(editor.getExtraCompRoot());
                    editor.getExtraCompRoot().addToChildrenVector(-1, fc);
                }
            }
            
            //System.out.println(
            //"POP NON VIS " + fc + "@" + fc.hashCode() + ", " + this +"@" +
            // hashCode() + ", " + fc.getParent());
            if (equals(fc)) {
                jiglooPlugin
                        .writeToLog("CHILD IS PARENT in populateNonVisualComponents "
                                + this);
            } else if (isChildOf(fc)) {
                jiglooPlugin
                        .writeToLog("PARENT IS CHILD OF in populateNonVisualComponents "
                                + this + ", " + fc);
            } else {
                fc.populateNonVisualComponents(editor);
            }
        }
    }

    public void invokeStoredMethodCalls() {
        	if(isAbstractFormBuilder()) {
                for(int i=0;i<methodCalls.size(); i++) {
                    Object[] mc = (Object[])methodCalls.elementAt(i);
            		int before = getBuilderPanel().getComponentCount();
            		invokeOnBuilder((String)mc[0], (Object[]) mc[1]);
            		if(editor != null && mc[2] != null)
            			editor.getJavaCodeParser().deduceFormComponents(before, (Object[]) mc[1], this, (MethodInvocation)mc[2]);
                }
        		return;
        	}
        for(int i=0;i<methodCalls.size(); i++) {
            Object[] mc = (Object[])methodCalls.elementAt(i);
            Object[] params =  (Object[])mc[1];
            for (int j = 0; j < params.length; j++) {
                params[j] = ConversionUtils.convertParamToObject(params[j], false);
            }
            if(mc[0] instanceof String) {
                String methName = (String)mc[0];
                //this is intended to cover non-standard property setters, and
                //calls like TableModel.setDataVector
                //if you don't restrict it, then shell.open() is called, for instance
                //frame.setVisible(true) should be excluded since "visible" is
                //a standard property
                if(methName.startsWith("set") && params != null && params.length > 0) {
                    invokeOnControl(methName, params);
                } else if(jiglooPlugin.getDefault().canCallNonSetter(getMainClass())) { // && !methName.equals("open") ) {
                    //                    System.err.println("Invoking non-setter method "+methName+", "+this);
                    invokeOnControl(methName, params);
                }
            } else {
                Method m = (Method)mc[0];
                try {
                    m.invoke(null, params);
                } catch(Throwable t) {
                    String msg = "Error invoking stored method call "+m;
                    if(params != null) {
                        for (int j = 0; j < params.length; j++) {
                            msg += ", "+params[j];
                        }
                    }
                    msg+="; "+t;
                    jiglooPlugin.writeToLog(msg);
                }
            }
        }
    }
    
    public void ensureUniqueness(Vector existing) {
        if (existing == null)
            existing = new Vector();
        existing.add(this);
        for (int i = 0; i < getChildCount(); i++) {
            FormComponent fc = getChildAt(i);
            if (existing.contains(fc)) {
                //System.out.println("ENSURE UNIQUENESS removing "+fc);
                getChildren().remove(fc);
                i--;
            } else {
                existing.add(fc);
                fc.setParent(this);
                fc.ensureUniqueness(existing);
            }
        }
    }

    public void populateCwtWidgets(Component parent, FormEditor editor) {
        com.cloudgarden.jigloo.typewise.TypewiseManager.populateCwtWidgets(
                this, parent, editor);
    }

    private boolean classCreated = true;
    
    public boolean wasClassCreated() {
        return classCreated;
    }

    private ComponentAdapter swtAwtResizeListener = new ComponentAdapter() {
    	
    	final Runnable r = new Runnable() {
    		public void run() {
    			if(getParent() != null) {
    				/*
    				 * TODO works if SWT parent is AWTControl, but not if the root
    				 * control is *not* an AWTControl (ie, SWT inside Swing inside
    				 * SWT. Need to adjust for this case...
    				 */
    				Rectangle b = getParent().getInsideBoundsRelativeToRoot();
    				if(control != null && !isDisposed()) {
    					((Composite)control).setBounds(b);
    				}
    			}
    		}
    	};
    	
		public void componentMoved(ComponentEvent e) {
			Display.getDefault().asyncExec(r);
		}
		public void componentResized(ComponentEvent e) {
			Display.getDefault().asyncExec(r);
		}
	};
	
	private Component lastSwtAwtVisListComp = null;
	private Component lastSwtAwtResizeListComp = null;
	
	private ComponentAdapter swtAwtVisibilityListener = new ComponentAdapter() {
		final Runnable r2 = new Runnable() {
			public void run() {
				if(!isControlDisposed()) {
					((Composite)control).setVisible(true);
					((Composite)control).moveAbove(null);
				}
			}
		};
		final Runnable r3 = new Runnable() {
			public void run() {
				if(!isControlDisposed()) {
					((Composite)control).setVisible(false);
					((Composite)control).moveBelow(null);
				}
			}
		};
		public void componentHidden(ComponentEvent e) {
			Display.getDefault().asyncExec(r3);
		}
		public void componentShown(ComponentEvent e) {
			Display.getDefault().asyncExec(r2);
		}
	};
	
	private static long now;
	public static void log(String msg) {
	    if(msg != null)
	        System.out.println("LOG: "+msg+"  "+(System.currentTimeMillis() - now));
	    now = System.currentTimeMillis();
	}
	
    public void populateComponents(Container parent, FormEditor editor) {
    	if(editor != null) {
    		editor.setWaitLabelMsg("Creating:\n  "+getNameInCode());
    		setEditor(editor);
    	}
    	//    	if(component != null) 	    return;
    	
        if (isInherited) {
            if(inheritedLayout == null) {
                inheritedLayout = new LayoutWrapper(this);
                inheritedLayout.setName(getLayoutWrapper().getName());
//                System.out.println("STORE INHERITED LO "+inheritedLayout+", "+this);
            }
            if(getLayoutWrapper().isSet()) {
                //if the layout for an inherited component is set in code, create
                //and set the layout manager here
                
                //need to call setLayoutWrapper since some components may already
                //have been added with constraints.
                setLayoutWrapper(getLayoutWrapper(), true, false);
//                Container ctr = (Container)component;
//                ctr.setLayout(getLayoutWrapper().getSwingLayout(ctr));
            } else {
                layoutWrapper = new LayoutWrapper(this);
            }
            return;
        }
        
        if(isInMainTree()) {

        	//handle SWT_AWT.new_Shell
            if(isSWT()) {
            	Component c = getParent().getComponent();
            	if(editor ==  null || editor.isPreviewing()) {
            		component = new JLabel("SWT_AWT.new_Shell");
//            		((java.awt.Canvas)c).add(component);
            		return;
            	}
            	if(c == null) {
            		System.err.println("CANVAS is null for new_Shell for "+this);
            	} else {
            		if(control == null || isDisposed()) {
            			disposed = false;
            			Composite par = getEditor().getAWTControl();
            			if(par == null) {
            				//might be embedding a SWT control inside a Swing object,
            				// inside a SWT control!
            				FormComponent fcpar = getParent();
            				while(fcpar != null && !(fcpar.getControl() instanceof Composite))
            					fcpar = fcpar.getParent();
            				if(fcpar != null)
            					par = (Composite)fcpar.getControl();
            			}
            			control = new OrderableComposite(par, SWT.NULL);

            			//make sure it's on top!!!
            			((Control)control).moveAbove(null);
            			
            			if(lastSwtAwtResizeListComp != null)
            				lastSwtAwtResizeListComp.removeComponentListener(swtAwtResizeListener);
            			c.addComponentListener(swtAwtResizeListener);
            			lastSwtAwtResizeListComp = c;
            			
            			Component cpar = c;
            			while(cpar != null && cpar.getParent() != null 
            					&& !((cpar.getParent() instanceof JTabbedPane)
            							|| cpar.getParent().getLayout() instanceof CardLayout)) {
            				cpar = cpar.getParent();
            			}
            			
            			if(cpar != null && cpar.getParent() != null 
            					&& ((cpar.getParent() instanceof JTabbedPane
            							|| cpar.getParent().getLayout() instanceof CardLayout))) {
                			if(lastSwtAwtVisListComp != null)
                				lastSwtAwtVisListComp.removeComponentListener(swtAwtVisibilityListener);
            				cpar.addComponentListener(swtAwtVisibilityListener);
                            swtAwtVisibilityListener.componentHidden(null);
            				lastSwtAwtVisListComp = cpar;
            			}
            			
            		}
                    if (layoutWrapper != null) {
                        layoutWrapper.init();
                        if (layoutWrapper.isSet()) {
                            Layout swtLayout = layoutWrapper.getSWTLayout();
                            ((Composite)control).setLayout(swtLayout);
                        }
                    }
                    
                    swtAwtResizeListener.componentResized(null);
                    initInheritedElements();
            		for(int i=0;i<components.size(); i++)
            			getChildAt(i).populateControls((Composite)control, editor, true);
            	}
            	return;
            }

            parent = SwingHelper.getContentPane(parent);

            setEditor(editor);
            
            boolean isChildOfBuilder = (getParent() != null 
            		&& getParent().isSubclassOf(AbstractFormBuilder.class));
            
	        Component comp = null;
	        if (component == null) {
	           
	        	if(isAbstractFormBuilder())
	            	invokeBuilderMethodCalls();
	            
	            if (className != null) {
	                try {
	                    if (comp == null) {
//	                    	if(isContentPane) {
	                    	if(useExistingCP) {
	                    		Component prc = getParent().getRawComponent();
	                    		if(prc instanceof JFrame)
	                    			comp = ((JFrame)prc).getContentPane();
	                    		else if(prc instanceof JWindow)
	                    			comp = ((JWindow)prc).getContentPane();
	                    		else if(prc instanceof JDialog)
	                    			comp = ((JDialog)prc).getContentPane();
	                    		else if(prc instanceof JInternalFrame)
	                    			comp = ((JInternalFrame)prc).getContentPane();
	                    		else if(prc instanceof JApplet)
	                    			comp = ((JApplet)prc).getContentPane();
	                    		else
		                    		comp = makeComponent(className);
	            	        } else {
	                    		comp = makeComponent(className);
	                    	}
	                    }
	                } catch (Exception ex) {
	                    //probably a compilation error in inherited class
	                    if (editor != null && component != null
	                            && FormEditor.isUpdatingClass()) {
	                        return;
	                    }
	
	                    handleUnableToMakeClassError(className, ex);
	                 }
	            }
	            String unknownClass = null;
	            if (comp == null) {
	                unknownClass = className;
	                classCreated = false;
	                comp = new JLabel();
	                //v4.0M2
//	                setClassName(comp.getClass().getName());
	            } else {
	                classCreated = true;                
	            }
	            setComponent(comp);
	            //System.out.println("MADE COMPONENT " + component);
	            LayoutManager lm = null;
	            if (component instanceof Container) {
	                if (layoutWrapper == null
	                        || layoutWrapper.getLayoutType() == null) {
	                    layoutWrapper = new LayoutWrapper(this);
	                    lm = LayoutWrapper.getLayoutManager(this);
	                    layoutWrapper.setObject(lm);
	                    layoutWrapper.setSet(false);
	                    setOldLW();
	                } else {
	//                    System.out.println("GOT LAYOUT = "
	//                            +((Container)component).getLayout()+", "+layoutWrapper);
	                    lm = getLayoutWrapper().getSwingLayout((Container) component);
	                }
	            }
	            
	            initProperties();
	            
	            if (unknownClass != null)
	                setPropertyValueDirect("text", unknownClass);
	            
	            if(layoutWrapper == null)
	                getLayoutWrapper();
	            
	            layoutWrapper.init();

	            try {
	                if ((usesContentPane() || layoutWrapper.isSet()) && lm != null) {
	                    if (lm instanceof AbsoluteLayout)
	                        lm = null;
	                    if (component instanceof JInternalFrame) {
	                        ((JInternalFrame) component).getContentPane() .setLayout(lm);
	                    } else if (component instanceof JFrame) {
	                        ((JFrame) component).getContentPane().setLayout(lm);
	                    } else if (component instanceof JDialog) {
	                        ((JDialog) component).getContentPane().setLayout(lm);
	                    } else if (component instanceof JWindow) {
	                        ((JWindow) component).getContentPane().setLayout(lm);
	                    } else if(usesContentPane()){
	                        getContentPane().setLayout(lm);
	                    } else {
	//                        System.out.println("SET LAYOUT "+lm+", "+this+", "+component);
	                        ((Container) component).setLayout(lm);
	                    }
	                }
	            } catch (Throwable e) {
	                System.err.println(e.toString() + "\n"
	                        + "Error setting layout for " + this + " to " + lm);
	            }

	            if (parent != null) {
	            	//if you include the isChildOfBuilder line, preview is empty
	                if (useExistingCP
//   	                if (isContentPane
//	                		|| isChildOfBuilder
							) {
//	                    getParent().setContentPane((Container) component);
	                } else {
	                    if (!(rawComponent instanceof JFrame)
	                            && !(rawComponent instanceof JDialog)
	                            && !(rawComponent instanceof JWindow)) {
	                        addComponentToParent(parent);
	                    }
	                }
	            }
//	            setTabTitle(getTabTitle());
	            if (editor != null)
	                editor.addComponent(this);
	        } else {

	        	component = handleWindowComponent(component);

	        	if(component.getParent() == null) {
	        		//handles cases like Box.createGlue() components
	        		if(!isChildOfBuilder && parent != null)
	        			addComponentToParent(parent);
	        		if (editor != null)
	        			editor.addComponent(this);
	        	} else {
	        		//this is so that the JFrame has it's title and icon set (which are in the Dectorations)
	        		if(isJFrame() || isJDialog())
	        			initProperties();
	        		if(!isRoot() && !isMenuComponent()
	        				&& !component.getParent().equals(parent)
	        				&& !component.equals(parent) //for contentPanes
	        		) {
	        			//	        		System.err.println("TRANSFERRING COMPONENT "+this+" FROM "+
	        			//	        				component.getParent()+" TO "+parent);
	        			component.getParent().remove(component);
	        			if(parent != null)
	        				addComponentToParent(parent);

	        		}
	        	}
	        }

            setTabTitle(getTabTitle());
        }
        
        Vector comps = getChildren();

        if (isContainer()) {

            int num = comps.size();
            int pos = num - 1;
            for (int i = 0; i < num; i++) {
                FormComponent fc = getChildAt(pos);
                fc.setParent(this);

                if (fc.isInherited()) {
                    comps.remove(fc);
                    comps.add(0, fc);
                } else {
                    pos--;
                }
            }

            initInheritedElements();
            for (int i = 0; i < comps.size(); i++) {
//                System.out.println("pop comps: ADDING component " + comps.elementAt(i) + " to " + this);
            	
            	//for SWT_AWT, component can be a Canvas and comps.size == 1
                FormComponent fc = (FormComponent) comps.elementAt(i);
                fc.setInMainTree(true);
            	if(component instanceof Container) {
	                fc.populateComponents((Container) component, editor);
            	} else {
	                fc.populateComponents(null, editor);
            	}
            }
            
        } else if (comps.size() > 0 && !getChildAt(0).isInherited()) {
            System.err.println("ERROR: A non-container has sub-components "
                    + component);
        }
        
        if(menuBar != null && isSubclassOf(JInternalFrame.class)) {
            menuBar.setInMainTree(true);
            menuBar.populateComponents(null, editor);
            ((JInternalFrame)component).setJMenuBar((JMenuBar) menuBar.getComponent());
        }
//        initGridBagDimensions(false);
        
        //invokeStoredMethodCalls is called from FormEditor.populateFormComponents
        //for root after everything has been created
        if(!isRoot())
            invokeStoredMethodCalls();
        
        populateGroupLayout();
        
    }

    private Component handleWindowComponent(Component component) {
    	if (component instanceof JFrame) {
            rawComponent = component;
            getEditor().setAWTControlWindow((JFrame) component);
            component = ((JFrame) component).getContentPane();
        } else if (component instanceof JDialog) {
            rawComponent = component;
            getEditor().setAWTControlWindow((JDialog) component);
            component = ((JDialog) component).getContentPane();
        } else if (component instanceof JWindow) {
            rawComponent = component;
            getEditor().setAWTControlWindow((JWindow) component);
            component = ((JWindow) component).getContentPane();
        }
    	return component;
	}

	/**
     * 
     */
    public void populateGroupLayout() {
        if(usesGroupLayout()) {
            try {
                Object lwo = layoutWrapper.getObject();
                if(!(lwo instanceof GroupLayout))
                    return;
                GroupLayout gl = (GroupLayout)lwo;
//                if(component instanceof Container)
//                    gl.invalidateLayout((Container)component);
                Object grp;
                LayoutGroup lg = null;
                grp = layoutWrapper.getVGroup();
                if(grp instanceof LayoutGroup) {
                    lg = (LayoutGroup)grp;
                    lg.setLayoutWrapper(layoutWrapper);
                    gl.setVerticalGroup(lg.populateGroup());
                    //                lg.dump("");
                }
                grp = layoutWrapper.getHGroup();
                if(grp instanceof LayoutGroup) {
                    lg = (LayoutGroup)grp;
                    lg.setLayoutWrapper(layoutWrapper);
                    gl.setHorizontalGroup(lg.populateGroup());
                    //                lg.dump("");
                }
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private boolean isInherited = false;

    private boolean isInheritedField = false;

    private boolean isInheritedDeclared = false;

    public boolean isInherited() {
        return isInherited; // || isInheritedDeclared || isInheritedField;
    }

    public boolean isInheritedField() {
        return isInheritedField;
    }

    public boolean isInheritedDeclared() {
        return isInheritedDeclared;
    }

    public void setInherited(boolean inherited) {
        isInherited = inherited;
    }

    public void setInheritedField(boolean isField) {
        isInheritedField = isField;
    }

    public void setInheritedDeclared(boolean declared) {
        isInheritedDeclared = declared;
    }

    private void handleInheritedMethods(boolean declared) {
        try {
	        Class mc = getMainClass();
	        Method[] meths = null;
	        if (declared)
	            meths = mc.getDeclaredMethods();
	        else
	            meths = mc.getMethods();
	        for (int i = 0; i < meths.length; i++) {
	            Method m = meths[i];
	            String mn = m.getName();
	            if (mn.startsWith("get") && !"getContentPane".equals(mn)
	                    && !"getLayeredPane".equals(mn)
	                    && !"getGlassPane".equals(mn) && !"getRootPane".equals(mn)
	                    && !"getFocusCycleRootAncestor".equals(mn)
	                    && !"getNextFocusableComponent".equals(mn)
	                    && !"getComponentPopupMenu".equals(mn)
	                    && !"getTopLevelAncestor".equals(mn)
	                    && !"getViewport".equals(mn)
	                    //&& !"getSelection".equals(mn)
	                    && !"getParent".equals(mn) && !"getShell".equals(mn)
	                    && !"getControl".equals(mn) && !"getContent".equals(mn)
	                    && !"getTopComponent".equals(mn)
	                    && !"getBottomComponent".equals(mn)
	                    && !"getLeftComponent".equals(mn)
	                    && !"getRightComponent".equals(mn)
	                    && !"getDesktopIcon".equals(mn)
	                    && m.getParameterTypes().length == 0 && canAccess(m)) {
	                Class cls = m.getReturnType();
	                if (ClassUtils.isVisual(cls)) {
	                    String cName = getName() + "."
	                            + JiglooUtils.propertyFromGetter(mn);
	                    //String cName = "." + JiglooUtils.propertyFromGetter(mn);
	                    FormComponent fc = getChildByName(cName);
	                    if (fc == null) {
	                        fc = FormComponent.newFormComponent(this, cls.getName(), cName, true, null);
	                        fc.setInheritedName(cName);
	                        fc.setInheritedField(false);
	                        fc.setInheritedDeclared(declared);
//	                        System.out.println("ADDED INHERITED METHOD-ELEMENT " + fc);
	                    }
	                }
	            }
	        }
        } catch(Throwable t) {
            jiglooPlugin.handleError(t, "Error getting inherited fields for "+this+", "+getMainClass());
        }
    }

    private boolean canAccess(Object m) {
    	int mods;
        if (m instanceof Method) {
        	mods = ((Method) m).getModifiers();
            if ((mods & Modifier.PUBLIC) != 0)
            	return true;
            if ((mods & Modifier.PROTECTED) != 0) {
            	//4.0.6
            	((Method)m).setAccessible(true);
            	return true;
            }
        } else {
        	mods = ((Field) m).getModifiers();
            if ( (mods & Modifier.PUBLIC) != 0)
            	return true;
            if ((mods & Modifier.PROTECTED) != 0) {
            	//4.0.6
            	((Field)m).setAccessible(true);
            	return true;
            }
        }
        return false;
    }

    private void handleInheritedFields(boolean declared) {
    	try {
    		Class mc = getMainClass();
    		Field[] fields = null;
    		if (declared)
    			fields = mc.getDeclaredFields();
    		else
    			fields = mc.getFields();
    		for (int i = 0; i < fields.length; i++) {
    			Field m = fields[i];
    			String mn = m.getName();
	            if (!"contentPane".equals(mn)
	                    && !"layeredPane".equals(mn)
	                    && !"glassPane".equals(mn) && !"rootPane".equals(mn)
	                    && !"focusCycleRootAncestor".equals(mn)
	                    && !"nextFocusableComponent".equals(mn)
	                    && !"componentPopupMenu".equals(mn)
	                    && !"topLevelAncestor".equals(mn)
	                    && !"viewport".equals(mn)
	                    //&& !"getSelection".equals(mn)
	                    && !"parent".equals(mn) && !"shell".equals(mn)
	                    && !"control".equals(mn) && !"content".equals(mn)
	                    && !"topComponent".equals(mn)
	                    && !"bottomComponent".equals(mn)
	                    && !"leftComponent".equals(mn)
	                    && !"rightComponent".equals(mn)
	                    && !"desktopIcon".equals(mn)
	                    && canAccess(m)) {
    				Class cls = m.getType();
    				if (ClassUtils.isVisual(cls)) {
    					String cName = getName() + "." + m.getName();
    					FormComponent fc = getChildByName(cName);
    					if (fc == null) {
    						fc = FormComponent.newFormComponent(this, cls.getName(), cName, true, null);
	                        fc.setInheritedName(cName);
    						fc.setInheritedField(true);
    						fc.setInheritedDeclared(declared);
//    						System.out.println("ADDED INHERITED FIELD-ELEMENT " + fc);
    					}
    				}
    			}
    		}
    	} catch(Throwable t) {
    		jiglooPlugin.writeToLog("Error in handleInheritedFields for "+this+", "+getMainClass()
    				+", "+t);
    	}
    }

    public void getInheritedElements() {
        try {
//            System.err.println("GET INHER ELEMS FOR "+this);
//            new Exception().printStackTrace();
            //if(true) return;
            if (isCWT())
                return;
            if (isNonVisualRoot() || isExtraCompRoot() || isMenuComponent())
                return;
            if (isInherited)
                return;
    		if(FormComponent.class.equals(getMainClass()))
    			return;
    		
            handleInheritedMethods(true);
            handleInheritedMethods(false);
            handleInheritedFields(true);
            handleInheritedFields(false);
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }

    private LayoutWrapper inheritedLayout = null;

    /**
     * Only sets defaultProps if defaultProps is null (if this is an inherited FC), because
     * defaultProps should only be set first time round if  this is inherited - second time
     * round, properties will already have been "contaminated"
     */
    public void setDefaultProps(HashMap props) {
    	if(defaultProps == null || !isInherited())
    		defaultProps = props;
    }
    
    public void initInheritedElements() {
        //if(true) return;
        if (isNonVisualRoot() || isExtraCompRoot() || isMenuComponent())
            return;
        if (isInherited)
            return;
        Object o = getObject(false);
        if (o == null) {
            return;
        }

        //so, get JFrame and not contentPane
        if (isSwing())
            o = getRawComponent();
        if (o == null)
            return;
        Class mc = o.getClass(); 
        if(isRoot())
        	mc = getMainClass();
        //go backwards so that removing components works
        String pName = null;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            FormComponent fc = getChildAt(i);
            try {
                if (fc.isInherited()) {
                    Object obj = null;
                    pName = fc.getName();
                    int pos = pName.lastIndexOf(".");
                    if (pos > 0)
                        pName = pName.substring(pos + 1);
                    boolean remove = false;
                    try {
                        if (fc.isInheritedField()) {
                        	Field f = null;
                        	try {
                        		f = mc.getDeclaredField(pName);
                        	} catch(Throwable t) {
                        		f = mc.getField(pName);
                        	}
                        	//4.0.6
                            f.setAccessible(true);
                            obj = f.get(o);
                        } else {
                        	Method m = null;
                        	try {
                        		m = mc.getDeclaredMethod("get"+ JiglooUtils.capitalize(pName), null);
                        	} catch(Throwable t) {
                            	m = Cacher.getMethod(mc, "get"+ JiglooUtils.capitalize(pName), null);
                        	}
                        	//4.0.6
                        	m.setAccessible(true);
                        	obj = m.invoke(o, null);
                        }
                        remove = (obj == null) || obj.equals(o);
                        
                        //if this object is already tied to an existing (and different) FormComponent, don't register it as an
                        //inherited FC 
                        //4.0.6
                        if(obj != null && editor != null ) {
                        	Object fce = editor.getComponentWithObject(obj);
                                if(fce != null && !fce.equals(fc))
                                	remove = true;
                        }
                        
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                        if(jiglooPlugin.DEBUG_EXTRA)
                            System.err.println("initInheritedElements: "+ex+" with " + this +", " + pName+", "+o);
                        //if the field or method does not exist, then the element
                        //may have been remved from the inherited class
                        remove = true;
                    }

                    if (obj instanceof Component) {
                        if (obj instanceof Container
                                && o instanceof Component
                                && ((Container) obj)
                                        .isAncestorOf((Component) o))
                            remove = true;
                    } else if (obj instanceof Control) {
                        if (pName.equals("control"))
                            remove = true;
                    } else {
                        remove = true;
                    }
                    
                    if (remove) {
                        //System.out.println("REMOVING INHERITED ELEM " + fc);
                        getChildren().remove(fc);
                        if(editor != null)
                        	editor.removeFields(fc);
                    } else {
                        //childCountInCode++;
                        if (isSwing())
                            fc.setComponent((Component) obj);
                        else if (isSWT())
                            fc.setControl((Widget) obj);
                        else
                            fc.setNonVisualObject(obj);

                        fc.setDefaultProps(JiglooUtils.getInitialProperties(obj));
                        Vector pNames = fc.getPropertyNames();
                        if (pNames == null)
                            pNames = PropertySourceFactory.findPropertyNames(fc.getMainClass());
//                        pNames = PropertySourceFactory.findPropertyNames(mc);
                        if (pNames != null) {
                            for (int p = 0; p < pNames.size(); p++) {
                                String pn = (String) pNames.elementAt(p);
                                if (fc.isPropertySet(pn)) {
//                                    System.out.println(
//                                    "INIT INHER " + pn + ", " +fc+", "+
//                                     fc.getPropertyValue(pn));
                                    fc.setPropertyStorageValue(pn, fc
                                            .getPropertyValue(pn));
                                }
                            }
                        }
                        fc.clearProperties();
                        fc.initProperties();
                        
                        //don't set layoutWrapper here because it interferes with
                        //the inherited layoutWrapper
                        
//                        if(!fc.getLayoutWrapper().isSet()) {
//                            LayoutWrapper nlw = new LayoutWrapper(fc);
////                            System.out.println("INIT INHERITED ELEM "+fc+", "+nlw);
//                            fc.setLayoutWrapper(nlw, true);
//                        } else {
//                            fc.setLayoutWrapper(fc.getLayoutWrapper(), true);                        	
//                        }
                                                
                        for (int i2 = 0; i2 < fc.getChildCount(); i2++) {
                            FormComponent fcc = fc.getChildAt(i2);
                            if (isSwing()) {
                                fcc.populateComponents((Container) fc
                                        .getComponent(), editor);
                            } else if (isSWT()) {
                                fcc.populateControls((Composite) fc.getControl(), editor, true);
                            } else {
                                //
                            }
                        }
                        
                    }
                }
            } catch (Exception e) {
                jiglooPlugin.handleError(e, "Error getting field " + pName
                        + " for " + this + ", o=" + o);
            }
        }
    }

    public Composite getWidgetParent() {
        Object con = getControl();
        try {
            if (con instanceof Control) {
                return ((Control) con).getParent();
            }
            if (con instanceof CTabItem)
                return ((CTabItem) con).getParent();
            if (con instanceof TabItem)
                return ((TabItem) con).getParent();
            if (con instanceof CoolItem)
                return ((CoolItem) con).getParent();
            if (con instanceof ToolItem)
                return ((ToolItem) con).getParent();
        } catch(Throwable t) {
            System.err.println("Error in getWidgetParent "+t+", "+this);
        }
        return null;
    }

    public void redrawWidget() {
        if (isSwing())
            return;
        if (getControl() instanceof Control) {
            ((Control) getControl()).redraw();
        } else if (getParent() != null) {
            getParent().redrawWidget();
        }
    }

    public String getTabTitle() {
        if (tabTitle == null) {
            Object tt = null;
            if (isSwing()) {
                tt = getLayoutDataWrapper().getLayoutData();
            } else {
                tt = getPropertyValue("text");
            }
            if (tt instanceof String) {
                tabTitle = (String) tt;
            } else if (tt instanceof FormComponent) {
                tabTitle = ""+((FormComponent) tt).getObject(false);
            } else if (tt != null) {
            	return null;
            }
            if(tabTitle == null)
                tabTitle = getNameInCode();
        }
        return tabTitle;
    }

    public void setTabTitle(String title) { //System.out.println(
        //"setTabTitle " + title + ", " + this +" ed=" + editor);
        if (title == null)
            return;
        try {
            if (isSwing()) {
            	if (getParent() != null
            			&& getParent().isSubclassOf(JTabbedPane.class)) {
            		JTabbedPane tp = (JTabbedPane) getParent().getComponent();
            		if(tp != null) {
            			int index = getNonInheritedIndexInParent();
            			if (index >= tp.getTabCount())
            				return;
            			if (index != -1)
            				tp.setTitleAt(index, title);
            		}
            		getLayoutDataWrapper().setObject(title);
                    //Component might be null, but still want to set constraint
                }
            } else {
                if (control instanceof CTabItem || control instanceof TabItem) {
                    if (!title.equals(getPropertyValue("text")))
                        setPropertyValueDirect("text", title);
                } else {
                    return;
                }
            }
            if (title.equals(tabTitle))
                return;
            //tabTitle used solely to decide whether to set editor as dirty
            //since tab title in SWT is set in the property page, setTabTitle
            // is
            //not called when in SWT (by a user action).
            tabTitle = title;
            //if (editor != null)
            //editor.setDirtyAndUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        if (components != null) {
            for (int i = 0; i < components.size(); i++) {
                ((FormComponent) components.elementAt(i)).refresh();
            }
        }
        if (layoutDataWrapper != null)
            layoutDataWrapper.refreshControlRefs();
    }

    public void setVisible(boolean visible) {
        if (getControl() instanceof Control) {
            Control ctrl = (Control) getControl();
            ctrl.setVisible(visible);
        }
    }

    public boolean hasChild(FormComponent child) {
    	return components != null && components.contains(child);
    }
    
    /**
     * Tests all the sub-FormComponents to see whether their getObject
     * methods return this Object
    */
    public boolean hasChildObject(Object child) {
    	if(child == null || components == null)
    		return false;
    	for(int i=0;i<getChildCount(); i++) {
    		if(child.equals(getChildAt(i).getObject(false)))
    			return true;
    	}
   		return false;
    }
    
    public void addChild(FormComponent child) {
        addChild(child, true);
    }
        
    public void addChild(FormComponent child, int pos) {
        addChild(child, true, pos);
    }
        
    public void addChild(FormComponent child, boolean canRebuild) {
    	addChild(child, canRebuild, -1);
    }
        
    public void addChild(FormComponent child, boolean canRebuild, int pos) {
        if(child == null)
            return;
        //v4.0M3
        if(child.isJDialog() || child.isJFrame())
                return;
        
        if (isChildOf(child) || equals(child)) {
            jiglooPlugin.writeToLog("FormComponent.addChild: Trying to add " + child + " to " + this
                    + " when " + child + " is ancestor of " + this);
            return;
        }

        FormComponent cpar = child.getParent();
        int cc = 0;
        if (components != null) {
            cc = getNonInheritedChildCount();
        }
        if (childCountInCode > cc) {
            if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("CHILD COUNT IN CODE > CC,  CCIC="
                        + childCountInCode + ", CC=" + cc + ", THIS=" + this
                        + ", CHILD=" + child);
            childCountInCode = cc;
        }

        if(pos < 0)
        	pos = childCountInCode;

        boolean atEnd = pos == childCountInCode;

        child.setEditor(editor);
        if (editor != null && editor.updatesJavaCode()) {
            if (cpar != null) {
                if (!cpar.equals(this)) {
                    child.moveToParent(this, pos);
                } else if (child.getIndexInParent() != pos) {
                    child.moveTo(pos);
                }
            }
            childCountInCode++;
            child.setHasParentInCode(true);
        }

        if (components == null)
            components = new Vector();
        
        addToChildrenVector(pos, child);
        child.setParent(this);
        
        if (cpar == null && isSWT() && control != null 
                && canRebuild && !atEnd) {
            rebuildParent(false);
        }
    }

    public boolean existsInCode() {
        return existsInCode;
    }

    public void populateControls(Object parent, FormEditor editor,
            boolean translate) {
        
        if (isNonVisualRoot())
            return;

        setEditor(editor);
        if(editor != null) {
    		editor.setWaitLabelMsg("Creating:\n  "+getNameInCode());
    	}
        
//        if(isRootShell()) {
//            control = getEditor().getRootDecoration();
//            initProperties();
//            return;
//        }
        try {
            //handle SWT_AWT.new_Frame
            if(isSwing()) {
            	if(parent instanceof Composite
            			&& (((Composite)parent).getStyle() & SWT.EMBEDDED) != 0) {
            		Frame frame = SWT_AWT.new_Frame((Composite)parent);
            		component = frame;
                    initInheritedElements();
            		getChildAt(0).populateComponents(frame, editor);
            		frame.setVisible(true);
            	}
            	return;
            }
            
            if (isInherited) {
                if(isControlDisposed())
                    return;
                if(inheritedLayout == null) {
                    inheritedLayout = new LayoutWrapper(this);
                    inheritedLayout.setName(getLayoutWrapper().getName());
                    //                    System.out.println("STORE INHERITED LO "+inheritedLayout+", "+this);
                }
                if(getLayoutWrapper().isSet()) {
                    //if the layout for an inherited component is set in code, create
                    //and set the layout manager here
                    setLayoutWrapper(getLayoutWrapper(), true);
//                    Composite ctr = (Composite)control;
//                    ctr.setLayout(getLayoutWrapper().getSWTLayout());
                } else {
                    layoutWrapper = new LayoutWrapper(this);
                }
                return;
            }
            
            boolean controlWasNull = false;
            if (isInMainTree()) {
                Layout lo = null;
                if(false && isRootShell()) {
                    control = getEditor().getRootDecoration();
                    initProperties();
                	System.out.println("POPULATE ROOT SHELL");
                } else if (parent == null) {
                    if (jiglooPlugin.DEBUG_EXTRA)
                        System.err.println("populateControls. parent is null: "+ this.getName());
                    //new Exception().printStackTrace();
                    return;
                }
                //        jiglooPlugin.getDefault().enableWindowHiding(true);
                //System.err.println("POP CTRLS "+this+", "+control);
                	if (control == null || isControlDisposed()) {
                    controlWasNull = true;
                    if (className != null) {
                        String oc = SwingSWTMapper.getOrderableClass(className);
                        if (oc != null)
                            setClassName(oc);
                        int _style = getStyle();
                        if (isA(Shell.class))
                            _style = SWT.NONE;
                        Object[] info = new Object[] { className,
                                new Integer(_style) };
                        makeAndSetControl(info, parent, translate);
                        bringToFront(false);
                    } else {
                        if (isRoot()) { //System.out.println(
                            //"SETTING FILL LAYOUT FOR ROOT " + this);
                            //Initial "Form" container
                            control = new OrderableComposite(
                                    (Composite) parent, SWT.NULL);
                            FillLayout layout = new FillLayout();
                            ((Composite) control).setLayout(layout);
                        }
                    }
                    initProperties();
                    if (control instanceof Composite) {
                        if (layoutWrapper == null || !layoutWrapper.isSet()) {
                        	LayoutWrapper test = new LayoutWrapper(this);
                        	if(layoutWrapper == null || !test.equals(layoutWrapper)) {
	                            layoutWrapper = test;
	                            layoutWrapper.setSet(false);
	                            setOldLW();
                        	}
                        }
                    }

                    if (layoutWrapper != null) {
                        layoutWrapper.init();
                        if (layoutWrapper.isSet()) {
                            Layout swtLayout = layoutWrapper.getSWTLayout();
                            if (control instanceof Composite) {
                                Composite cmp = (Composite) control;
                                cmp.setLayout(swtLayout);
                            }
                        }
                    }

                    if (layoutDataWrapper != null) {
                        Object ld = layoutDataWrapper.getLayoutData();
                        if (control instanceof Control) {
                            String plt = getParentLayoutType();
                            if (("Grid".equals(plt) && !(ld instanceof GridData))
                                    || ("Row".equals(plt) && !(ld instanceof RowData))
                                    || ("Fill".equals(plt))
                                    || ("Form".equals(plt) && !(ld instanceof FormData))) {
                                getLayoutDataWrapper().setObject(null);
                                resetPropertyValue("layoutData");
                            } else if(!isMenuComponent()) {
                            	setControlLayoutData(ld);
                            }
                        }

                    }

                    setTabTitle(getTabTitle());
//                    if (editor != null)
//                        editor.addComponent(this);
                }
                if (editor != null)
                    editor.addComponent(this);
            }

            //don't populate the children of a shell because one of the children might call getShell().setMenuBar,
            //which would set Eclipse's menu - BAD!
//            if(isA(Shell.class))
//            	return;
            
            Vector comps = getChildren();
            if (isContainer()) {
            	
                initInheritedElements();
                
                for (int i = 0; i < comps.size(); i++) {
                    FormComponent fc = (FormComponent) comps.elementAt(i);
                    
                    if (fc.isInherited()) {
                        if(fc.isControlDisposed())
                            fc.setControl(null);
                        fc.populateControls(control, editor, translate);
                        continue;
                    }
                    
                    //System.out.println(
                    //"ADDING control " + fc + " to " + this);
                    if (editor != null && editor.updatesJavaCode()
                            && !fc.existsInCode && !editor.isPreviewing()) {
                        if (jiglooPlugin.DEBUG_EXTRA)
                            System.out.println("*** " + fc.getName()
                                    + " DOES NOT EXIST IN CODE");
                        continue;
                    }
                    String itemCN = null;
                    if (control instanceof CTabFolder) {
                        itemCN = CTABITEM;
                    } else if (control instanceof TabFolder) {
                        itemCN = TABITEM;
                    }
                    if (itemCN != null) {
                        FormComponent cti = fc;
                        String cn = fc.getClassName();
                        //this can happen when a form is switched from Swing
                        if (!cn.equals(itemCN)) {
                            boolean reclassItem = (cn
                                    .equals(OrderableComposite.class.getName()) && fc
                                    .getChildren().size() < 2);
                            if (!reclassItem) {
                                cti = FormComponent.newFormComponent(editor, itemCN);
                                cti.setEditor(editor);
                            }
                            comps.setElementAt(cti, i);
                            cti.setClassName(itemCN);
                            cti.setParent(this);
                            cti.setExistsInCode(true);
                            cti.setInMainTree(true);
                            cti.setEditor(editor);
                            if (!reclassItem)
                                cti.add(fc);
                        }
                        cti.populateControls(control, editor, translate);
                        cti.setTabTitle(fc.getTabTitle());
                        if (!fc.equals(cti)) {
                            cti.addToCode();
                        }
                        fc = cti;
                    } else {
                        fc.populateControls(control, editor, translate);
                    }
                    if (control instanceof ScrolledComposite) {
                        ScrolledComposite sc = (ScrolledComposite) control;
                        Control wid = (Control) fc.getControl();
                        Point sz = wid.getSize();
                        sc.setContent(wid);
                        wid.setSize(sz);
                    } else if (mainClass.equals(ScrolledComposite.class) && control instanceof OrderableComposite) {
                    	//we are faking a ScrolledComposite with an OrderableComposite
                        Control wid = (Control) fc.getControl();
                        ((Composite)control).setLayout(new FillLayout());
                        ((Composite)control).layout();
                    } else if (jiglooPlugin.canUseForms()
                            && control instanceof org.eclipse.ui.forms.widgets.Section) {
                        org.eclipse.ui.forms.widgets.Section sc = (org.eclipse.ui.forms.widgets.Section) control;
                        Control wid = (Control) fc.getControl();
                        sc.setClient(wid);
                    }
                }
            } else {
                if (comps.size() != 0 && !getChildAt(0).isInherited()) {
                    System.err
                            .println("ERROR: A non-composite control has sub-components "
                                    + control + ", " + this);
                }
            }

            if (controlWasNull)
                initProperties();
            setSWTLayoutInfo(false, false);
            
            //invokeStoredMethodCalls is called from FormEditor.populateFormComponents
            //after everything has been created
            if(!isRoot())
                invokeStoredMethodCalls();

            if (isJFaceApplicationWindow()) {
                ApplicationWindowManager.initProperties(this);
            }

            if (control instanceof Composite) {
                try {
                    fixLayoutChildData(this);
                    ((Composite) control).layout();
                } catch (Exception e) {
                    System.err
                            .println("[populateControls] Error setting layout "
                                    + layoutWrapper + " for " + this);
                    e.printStackTrace();
                    JiglooUtils.displayBranch((Composite) control);
                }
            }

        } catch (Throwable e) {
            System.err.println("Error in populateControls " + this);
            displayBranch();
            e.printStackTrace();
        }
        //        jiglooPlugin.getDefault().enableWindowHiding(false);
    }

    public void setEditor(FormEditor editor) {
        this.editor = editor;
//        if(editor == null)
//        	System.out.println("SET EDITOR TO NULL FOR "+name);
    }

    public void createGetterMethod(boolean create) {
        createGetterMethod = create;
    }

    public boolean createGetterMethod() {
        return createGetterMethod;
    }

//    private String oldName = null;
//    
//    public void setOldName(String name) {
//    	oldName = name;
//    }
//    
//    public String getOldName() {
//    	if(oldName == null)
//    		return getName();
//    	return oldName;
//    }

    public void setName(String name) {
    	setName(name, true);
    }
    	
    public void setName(String name, boolean rename) {
    	setName(name, rename, null);
    }
    	
    /**
     * Should only be called after setName has been called to rename a field.
     * Returns a map of "oldName->newName" relations for main class handler methods
     */
    public HashMap getRenamedEventHandlers() {
        if(eventWrapper == null)
            return null;
        return eventWrapper.getRenameMap();
    }
    
    public void setName(String name, boolean rename, String oldNameProp) {
    	
        if (eventWrapper != null)
            eventWrapper.getRenameMap().clear();
        
    	if(!rename) {
    		this.name = name;
    		return;
    	}
    	
    	if(this.name != null) {
    	    getEditor().fieldRenamed(this.name, name, oldNameProp);
    	}
    	    
        //System.err.println("FC: SET NAME " + name + ", " + this.name);
        this.name = name;
        
        //need to change all the inherited components' base name
        //(eg, when an element is added to a form the inherited
        //components have names like unknownName.verticalScrollBar
        //since getInheritedElements is called before setName,
        //so need to change the unknownName. part to eg
        //jScrollPane1.verticalScrollBar)
        for (int i = 0; i < getChildCount(); i++) {
            FormComponent fc = getChildAt(i);
            if(fc.isInherited()) {
                String fcn = fc.getName();
                fcn = JiglooUtils.getUnqualifiedName(fcn);
                fcn = name+"."+fcn;
                fc.setName(fcn);
            }
        }
        if (layoutDataWrapper != null) {
        	if(layoutDataWrapper.getName() != null)
        		getJCP().fieldRenamed(layoutDataWrapper.getName(), name+"LData");
        	layoutDataWrapper.setName(name+"LData");
        }
        if (layoutWrapper != null) {
        	if(layoutWrapper.getName() != null)
        		getJCP().fieldRenamed(layoutWrapper.getName(), name+"Layout");
        	layoutWrapper.setName(name+"Layout");
        }
        if (eventWrapper != null)
            eventWrapper.setComponentName(name, null);
    }

    public JavaCodeParser getJCP() {
    	return getEditor().getJavaCodeParser();
    }
    
    public void updateUIForAll() {
        updateUIForAll(false);
        clearCachedBounds();
    }

    public void updateUIForAll(boolean childrenOnly) {
        if (!childrenOnly)
            updateUI();
        if (isA(CoolBar.class))
            return;
        if (components == null)
            return;
        for (int i = 0; i < components.size(); i++) {
            getChildAt(i).updateUIForAll();
        }
    }

    public void clearCachedBounds() {
        //reset these cached bounds so they will be re-calculated
        boundsToViewpt = boundsToRoot = null;
        if (components == null)
            return;
        for (int i = 0; i < components.size(); i++) {
            getChildAt(i).clearCachedBounds();
        }
    }

    public void updateUI() {
        try {
            if (component == null && control == null)
                return;
            
            if ((!isSwtInSwing() && !isSwingInSwt() && !propertiesInited)
                    || (editor != null && (editor.isToggling()
                            || editor.isRebuilding() || editor.isPopulating())))
                return;
            Point size = null;
            if (control != null
                    && (control instanceof Widget && !((Widget) control).isDisposed())) {
                FormComponent par = this;
                if (par != null) {
                    try {
                        Object wid = par.getControl();
                        if (wid instanceof ToolBar) {
                        } else if (wid instanceof CoolBar) {
                            CoolBar comp = (CoolBar) wid;
                            CoolItem[] items = comp.getItems();
                            for (int i = 0; i < items.length; i++) {
                                CoolItem item = items[i];
                                Control control = item.getControl();
                                if (control != null) {
                                    control.pack(true);
                                    Point sz = control.getSize();
                                    sz = control.computeSize(sz.x, sz.y);
                                    control.setSize(sz);
                                    Point sz2;
                                    sz2 = item.computeSize(sz.x, sz.y);
                                    item.setSize(sz2);
                                    FormComponent child = par.getChildAt(i);
                                    if (child != null) {
                                        Point csz = child.getSize();
                                        if (!sz.equals(csz)) {
                                            child.setPropertyValueInternal(
                                                    "size", sz, false, false);
                                            child.setPropertyValueInternal(
                                                    "preferredSize", sz, true,
                                                    false);
                                            child.setPropertyValueInternal(
                                                    "minimumSize", sz, true,
                                                    false);
                                        }
                                    }
                                }
                            }
                            comp.getParent().redraw();
                        } else if (wid instanceof TabItem) {
                            TabItem ti = (TabItem) wid;
                            TabItem[] sel = ti.getParent().getSelection();
                            ti.getParent().setSelection(sel);
                        } else if (wid instanceof CoolItem) {
                        } else if (wid instanceof Composite) {
                            Composite comp = (Composite) wid;
                            fixLayoutChildData(par);
                            comp.layout(true);
                        }
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                        System.err.println("Error in updateUI " + ex + " for " + this);
                    }
                    par = par.getParent();
                }
            }

            if (component != null) {
                try {
                    component.invalidate();
                } catch(Throwable ise) {
                    if(editor != null)
                        editor.setStatus("Error in updateUI: (part1) "+ise);
                    System.err.println("Error in updateUI: (part1) "+ise);
                }
                try {
                    component.validate();
                } catch(Throwable ise) {
                    ise.printStackTrace();
                    System.err.println("Error in updateUI: (part2) "+ise);
                }
//                System.out.println("UPDATE UI "+this);
                Container par = component.getParent();
                while (par != null) {
                    try {
                        par.invalidate();
                    } catch(Throwable t) {
                        System.err.println("Error in updateUI: (part3) "+t);
                    }
                    try {
                        par.validate();
                    } catch(Throwable t) {
                        System.err.println("Error in updateUI: (part4) "+t);
                    }

                    par = par.getParent();
                }
            }

        } catch (Throwable e) {
            System.out.println("Error in updateUI (part5) for " + this+", "+e);
        }
        
        try {
        	if (getParent() != null && getParent().isJSplitPane()) {
        		Component pc = getParent().getComponent();
        		if(getParent().isPropertySet("dividerLocation")) {
        			if(pc instanceof JSplitPane) {
            			Object divLoc = getParent().getRawPropertyValue("dividerLocation");
            			if(divLoc instanceof Integer)
            				((JSplitPane)pc).setDividerLocation(((Integer)divLoc).intValue());
            			else
            				((JSplitPane)pc).setDividerLocation(Float.parseFloat(""+divLoc));
        			}
        		} else {
        			if(pc instanceof JSplitPane)
        				((JSplitPane)pc).resetToPreferredSizes();
        		}
        	}
            if (properties != null) {
                if(!isPropertySet("bounds"))
                    putProperty("bounds", getBounds(false, false));
                
            	if(!isPropertySet("size"))
                    putProperty("size", getSizeFromBounds());
            }
            
            getLayoutWrapper().updateGroupLayoutSizes();
            
        } catch (Throwable e) {
            System.out.println("Error in updateUI for " + this+", "+e);
            e.printStackTrace();
        }
    }

    public void updateGroupLayoutSizesForAll() {
        for(int i=0; i< getNonInheritedChildCount(); i++) {
            getNonInheritedChildAt(i).updateGroupLayoutSizesForAll();
        }
        getLayoutWrapper().updateGroupLayoutSizes();
    }
    
    private static void fixLayoutChildData(FormComponent fc) {
        if (fc == null || !fc.isSWT()
                || !(fc.getControl() instanceof Composite))
            return;
        Composite comp = (Composite) fc.getControl();
        if (comp == null)
            return;
        Layout lo = comp.getLayout();
        Class reqCls = null;
        if (lo instanceof FormLayout) {
            reqCls = FormData.class;
        } else if (lo instanceof GridLayout) {
            reqCls = GridData.class;
        } else if (lo instanceof RowLayout) {
            reqCls = RowData.class;
        } else if (reqCls == null) {
            return;
        }
        Control[] kids = comp.getChildren();
        for (int i = 0; i < kids.length; i++) {
            Control control = kids[i];
            Object ld = control.getLayoutData();
            if (ld != null && !ld.getClass().equals(reqCls)) {
                FormComponent ci = fc.getChildAt(i);
                if (ci != null)
                    ci.setLayoutDataWrapper(null);
                
                System.err.println("WRONG LAYOUT DATA " + fc + ", "
                        + fc.getChildAt(i) + ", REQUIRED: " + reqCls
                        + ", FOUND: " + ld);
            }
        }

    }

    public boolean isVisible() {
        if (control instanceof Control) {
            if(((Control)control).isDisposed())
                return false;
            return ((Control) control).isVisible();
        } else if (component != null) {
            return isComponentVisible();
        }
        return false;
    }
    
    private boolean isComponentVisible() {
        if (component != null ) {
//            if(!component.isVisible())
//                return false;
            if(getParent() != null && !getParent().equals(getEditor().getExtraCompRoot())) {
                Component pcomp = getParent().getComponent();
                if (pcomp instanceof JTabbedPane) {
                    Component lsel = ((JTabbedPane) pcomp).getSelectedComponent();
                    if (!component.equals(lsel))
                        return false;
                }
                if("Card".equals(parent.getLayoutType()) && !parent.getComponent().isVisible())
                    return false;
                return getParent().isComponentVisible();
            } else
                return true;
        }
        return false;
    }

	public void setLayoutDataWrapperSimple(LayoutDataWrapper ldw) {
		layoutDataWrapper = ldw;
		layoutDataWrapper.setFormComponentSimple(this);
        setOldLDW();
	}
	
    public void setLayoutDataWrapper(LayoutDataWrapper data) {
        if(isMenuComponent()) {
        	layoutDataWrapper = null;
        	return;
        }
        
        layoutDataWrapper = data;
        if(data != null) {
        	layoutDataWrapper.setFormComponentSimple(this);
        	setOldLDW();
        }
        
        
        setPropertyValueInternal("layoutData", data, false);
        setPropertyValueCode("layoutData", null);

        setEditorReload(true);
        if (data != null) {
        	setLayoutData(data.getLayoutData());
        } else {
            setLayoutData(null);
        }
        
    }

    public FormComponent getOtherSibling() {
        for (int i = 0; i < parent.getChildCount(); i++) {
            FormComponent fc = parent.getChildAt(i);
            if (fc.isInherited())
                continue;
            if (!fc.equals(this))
                return fc;
        }
        return null;
    }

    public void setLayoutData(Object data) {

        getLayoutDataWrapper().setObject(data);
        
        if (isSwing()) {
            if (parent == null)
                return;
    		adjustJSplitPaneConstraints();
            Container par = (Container) parent.getComponent();
            if ("Group".equals(getParentSuperLayoutType())) {
                if(data != null) {
                    LayoutWrapper lw = parent.getLayoutWrapper();
                    LayoutGroup lg = null;
                    if(data.equals(LayoutGroup.SET_DEFAULT_HEIGHT)) {
                        editor.pauseUpdate(true);
                        resetPropertyValue("preferredSize");
                        resetPropertyValue("maximumSize");
                        resetPropertyValue("minimumSize");
                        lw.getVGroup().resetPreferredSize(this);
                        editor.pauseUpdate(false);
                        lw.updateGroupLayout(getJavaCodeParser());
                    } else if(data.equals(LayoutGroup.SET_DEFAULT_WIDTH)) {
                        editor.pauseUpdate(true);
                        resetPropertyValue("preferredSize");
                        resetPropertyValue("maximumSize");
                        resetPropertyValue("minimumSize");
                        lw.getHGroup().resetPreferredSize(this);
                        editor.pauseUpdate(false);
                        lw.updateGroupLayout(getJavaCodeParser());
                    } else if(data.equals(LayoutGroup.EXPAND_HOR)
                            || data.equals(LayoutGroup.ANCHOR_LEFT)
                            || data.equals(LayoutGroup.ANCHOR_RIGHT)) {
                        lg = lw.getHGroup().getGroupContaining(this);
                        //this might be a new component (so not yet added to a group)
                        if(lg != null)
                            lg.handleLayoutConstraint(this, (String) data, getJavaCodeParser());
                    } else {
                        lg = lw.getVGroup().getGroupContaining(this);
                        //this might be a new component (so not yet added to a group)
                        if(lg != null)
                            lg.handleLayoutConstraint(this, (String) data, getJavaCodeParser());
                    }
                }
                return;
                
            } else if ("Anchor".equals(getParentSuperLayoutType())) {
                AnchorLayout gbl = (AnchorLayout) parent.getLayoutWrapper().getSwingLayout(par);
                try {
                    if (gbl != null && component != null
                            && data instanceof AnchorConstraint)
                        gbl.addLayoutComponent(component, data);
                    if (par != null && par.isAncestorOf(component))
                        par.doLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                if (data == null)
                    return;
            }
            
            if (getParent() != null 
            		&& getParent().usesCustomLayout() ) {
            	getParent().rebuild(false);

            } else {
            	
            	if (par != null && component != null) {
            		par.remove(component);
            		try {
            			if (data != null)
            				addComponent(component, par, data, this);
            			else
            				addComponent(component, par, data, this);
            		} catch (Exception e) {
            			System.err.println("ERROR ADDING COMP " + component + ", "
            					+ par + ", " + data + ", " + this);
            			e.printStackTrace();
            		}

            	}
            }
        } else {
        	setControlLayoutData(data);
//        	layoutDataWrapper.setObject(null);
        }
    }
    
    public void setControlLayoutData(Object layoutData) {
        try {
            if (control == null || !(control instanceof Control))
                return;
            ((Control) getControl()).setLayoutData(layoutData);
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error setting layout data to "
                    + layoutData + " for " + this + ", " + control);
        }
    }
    
    private int sizeX = -1, sizeY = -1;

    private Point adjustedSize;

    public Point adjustSize(Point size, final Object widget) {
        if (layoutWrapper == null)
            return size;
        if (size == null)
            return null;
        if (size.x == -1 && size.y == -1)
            return size;
        
        //for some reason, computeSize gives weird results for Form objects
        if(jiglooPlugin.canUseForms() && widget  instanceof org.eclipse.ui.forms.widgets.Form)
            return size;
        
        if (adjustedSize != null && sizeX == size.x && sizeY == size.y)
            return adjustedSize;
        
        adjustedSize = new Point(size.x, size.y);
        sizeX = size.x;
        sizeY = size.y;
        try {
            if (Display.getCurrent() == null) {
                System.err
                        .println("Executing adjustSize in syncExec - may lead to locking");
                final Point fsize = new Point(size.x, size.y);
                Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                        if (widget instanceof Control) {
                            Point csize = ((Control) widget).computeSize(
                                    fsize.x, fsize.y);
                            if (fsize.x != -1)
                                fsize.x -= csize.x - fsize.x;
                            if (fsize.y != -1)
                                fsize.y -= csize.y - fsize.y;
                            //System.out.println(
                            //"ADJUSTED SIZE to " + fsize + " for " + widget);
                        }
                    }
                });
                adjustedSize.x = fsize.x;
                adjustedSize.y = fsize.y;
            } else {
                if (widget instanceof Control) {
                    Point csize = ((Control) widget)
                            .computeSize(size.x, size.y);
                    if (adjustedSize.x != -1)
                        adjustedSize.x -= csize.x - size.x;
                    if (adjustedSize.y != -1)
                        adjustedSize.y -= csize.y - size.y;
                } //System.out.println(
                //"ADJUSTED (2) SIZE to " + size + " for " + widget);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "adustSize " + this);
        }
        return adjustedSize;
    }

    public void setSWTLayoutInfo(boolean doLayout, boolean force) {
        //if (isInherited) return;
        Object value = getPropertyValue("size");
        Point size = null;
        if (value instanceof SizePropertySource
                && (force || isPropertySet("size"))) {
        	if(isPropertySet("size"))
        		size = ((SizePropertySource) value).getValue();
        	else
        		size = new Point(-1,-1);
            setSWTLayoutInfo(size, doLayout);
        }
    }

    private void setSWTLayoutInfo(Point size, boolean doLayout) {
        //if (isInherited) return;
        if (!(control instanceof Control))
            return;
        try {
            Control ctrl = (Control) getControl();
            size = adjustSize(size, ctrl);
            if (ctrl instanceof AWTControl) {
                AWTControl awtc = (AWTControl) control;
                awtc.setSize(size.x, size.y);
                return;
            }
            Composite cp = getWidgetParent();
            if (cp == null)
                return;
            Object data = getLayoutDataWrapper().getLayoutData();
            
            LayoutDataWrapper ldw = null;
            if (cp.getLayout() instanceof GridLayout) {
                ldw = getLayoutDataWrapper();
                GridData gd = null;
                if (data instanceof GridData)
                    gd = (GridData) data;
                if (gd == null) {
                    gd = new GridData();
                }
                ldw.setObject(gd);
                if (size != null) {
                    //if (isPropertySet("size") && size != null) {
                    ldw.setPropertyValue("heightHint", new Integer(size.y), doLayout);
                    ldw.setPropertyValue("widthHint", new Integer(size.x), doLayout);
                }

                setLayoutDataWrapper(ldw);
            } else if (cp.getLayout() instanceof RowLayout) {
                ldw = getLayoutDataWrapper();
                RowData gd = null;
                if (data instanceof RowData)
                    gd = (RowData) data;
                if (gd == null) {
                    gd = new RowData();
                }
                ldw.setObject(gd);
                if (size != null) {
                    ldw.setPropertyValue("height", new Integer(size.y), doLayout);
                    ldw .setPropertyValue("width", new Integer(size.x), doLayout);
                }
                setLayoutDataWrapper(ldw);
            } else if (MigLayoutHandler.handlesLayout(getParent())) {
                ldw = getLayoutDataWrapper();
                if(!(ldw.getObject() instanceof String))
                	ldw.setObject("");
//                properties.put("layoutData", ldw.getLayoutData());
//                setSetProperty("layoutData");
//                setNeedsUpdateInCode("layoutData");
                setLayoutDataWrapper(ldw);
            } else if (cp.getLayout() instanceof FormLayout) {
                FormData fd = null;
                if (data instanceof FormData)
                    fd = (FormData) data;
                if (isRoot()) {
                    if (fd == null)
                        fd = new FormData();
                    if (size != null) {
                        fd.height = size.y;
                        fd.width = size.x;
                    }
                    setControlLayoutData(fd);
//                    ldw.setObject(fd);
//                    setLayoutDataWrapper(ldw);
                } else {
                	ldw = getLayoutDataWrapper();
                    if (fd == null) {
                        fd = new FormData();
                        ldw.setObject(fd);
                        ldw.setPropertyValue("top", new FormAttachment(30, 0), doLayout);
                        ldw.setPropertyValue("bottom", new FormAttachment(70, 0), doLayout);
                        ldw.setPropertyValue("left", new FormAttachment(30, 0), doLayout);
                        ldw.setPropertyValue("right", new FormAttachment(70, 0), doLayout);
                    } else {
                        ldw.setObject(fd);
                    }
                    if (size != null) {
                        ldw.setPropertyValue("height", new Integer(size.y), doLayout);
                        ldw.setPropertyValue("width", new Integer(size.x), doLayout);
                    }
                    setControlLayoutData(ldw.getLayoutData());
//                    setLayoutDataWrapper(ldw);
                }
            } else {
                setLayoutData(null);
            }
            try {
                if (doLayout) {
                    fixLayoutChildData(getParent());
                    cp.layout(true);
                }
            } catch (Throwable t) {
                System.err
                        .println("Can get an error here when setting the style for a sub-component. "
                                + t);
                t.printStackTrace();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } //System.err.println("New layoutData = " + getLayoutData());
    }

    public String toString() {
        if (name != null)
            return getNameInCode();
        String str = "FormComponent@" + hashCode() + ", " + name + ", "
                + className + " : ";
        return str;
    }

    public TreeObject getTreeObject() {
        if (treeObject == null)
            treeObject = new TreeParent(this);
        return treeObject;
    }

    public void setTreeObject(TreeObject object) {
        treeObject = object;
    }

    public boolean isImportedBean() {
        return isImportedBean;
    }

    public void setImportedBean(boolean b) {
        isImportedBean = b;
    }

    public boolean isContentPane() {
        return isSwing() && isContentPane ;
    }

    public void setContentPane(boolean b, boolean useExistingCP) {
        isContentPane = b;
        this.useExistingCP = useExistingCP;
    }

    public boolean isDialogButton() {
        return DIALOG_BUTTON.equals(getSpecialType());
    }
    public boolean isDialogButtonBar() {
        return DIALOG_BUTTON_BAR.equals(getSpecialType());
    }
    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean b) {
        isVirtual = b;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String string) {
        specialType = string;
    }

    public void setObject(Object obj) {
        throw new RuntimeException("FormComponent.setObject not implemented "
                + this + ", obj=" + obj);
    }

    public int getModifier() {
        return modifier;
    }
    
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
    public boolean isInline() {
        return inline;
    }
    public void setInline(boolean inline) {
        this.inline = inline;
        if(!inline && !nonstandardConstructor) {
        	
//            constructor = null;
//        	constructorParams = null;
        	
//        	cHolder = null;
//        	setConstructorCode(null);
        }
    }

    /**
     * If there is an anonymous class declaration, the "code" parameter will include
     * it - the anonClassCode is provided so that the constructor can be re-made yet
     * the anonymous class can be preserved (eg for an Action definition).
     * @param code
     * @param anonClassCode
     */
    public void setConstructorCode(String code, String anonClassCode) {
    	setPropertyValueCode("CONSTRUCTOR", code);
    	setPropertyValueCode("ANON_CLASS_CODE", anonClassCode);
    	if(code == null)
    		cHolder = null;
    }
    
    public String getConstructorCode() {
    	if(propertyValueCode == null)
    		return null;
    	return (String)propertyValueCode.get("CONSTRUCTOR");
    }
    
    public String getAnonClassCode() {
    	if(propertyValueCode == null)
    		return null;
    	return (String)propertyValueCode.get("ANON_CLASS_CODE");
    }
    
    /**
     * Changes the element's x-y location (depending on layout)
     */
    public void handleKeyPress(int keyCode) {
		int dx= 0;
		int dy = 0;
		if (keyCode == SWT.ARROW_DOWN)
		    dy++;
		if (keyCode == SWT.ARROW_UP)
		    dy--;
		if (keyCode == SWT.ARROW_RIGHT)
		    dx++;
		if (keyCode == SWT.ARROW_LEFT)
		    dx--;
		if(dx == 0 && dy == 0)
		    return;
		if(MigLayoutHandler.handlesLayout(getParent())) {
			dx *= 2;
			dy *= 2;
		}
        if(parent != null && parent.usesGridTypeLayout()) {
            if(dx != 0) {
            	int gx = getGridValue(true) + dx;
            	setGridValue(gx, true);
            }
            if(dy != 0) {
            	int gy = getGridValue(false) + dy;
            	setGridValue(gy, false);
            }
            executeSetLayoutDataWrapper(getLayoutDataWrapper());
        } else {
            Rectangle b = (Rectangle) getRawPropertyValue("bounds");
            if(b == null)
                return;
            b.y += dy;
            b.x += dx;
            setBounds(b, true);
        }
    }
    
    public void markMainTree() {
        //so we don't get in an endless loop
        if(hasSetInMainTree)
            return;
        hasSetInMainTree = true;
        setInMainTree(true);
//        System.err.println("MARK MAIN TREE "+this);
        for(int i=0;i<getChildCount();i++) {
//          System.err.println("MARK MAIN TREE CHILD "+getChildAt(i));
            getChildAt(i).markMainTree();
        }
        HashMap props = properties;
        if(props == null)
            props = propertyStorage;
        if(props == null)
            return;
        Iterator it = props.keySet().iterator();
        while(it.hasNext()) {
            String pName = (String)it.next();
            Object prop = props.get(pName);
            if(pName.equals("buttonGroup")) {
                if( prop instanceof String) {
                    prop = editor.getComponentByName((String)prop, true);
                } else if(prop instanceof ClassWrapper) {
                    prop = ((ClassWrapper)prop).getFormComponent();
                }
            }
//            System.err.println("MARK MAIN TREE "+this+", "+pName+", "+prop);
            FormComponent fc = null;
            if(prop instanceof FormComponent) {
                fc = (FormComponent)prop;
            } else if(prop instanceof FormComponentWrapper) {
                fc = ((FormComponentWrapper)prop).getFormComponent();
            }
            if(fc != null)
                fc.markMainTree();
        }
    }
    
    public boolean isInMainTree() {
//      System.err.println("IS IN MAIN TREE "+isInMainTree+", "+this);
//        if(true)
//            return true;
    	if(isRoot())
    		return true;
        if(isSubclassOf(Shell.class)) // || isSubclassOf(Display.class))
            return true;
        return isInherited() || isInMainTree;
    }
    
    private boolean hasSetInMainTree = false;
    
    public void setInMainTree(boolean isInMainTree) {
//        System.err.println("SET IN MAIN TREE "+isInMainTree+", "+this);
//        new Exception().printStackTrace();
        if(!isInMainTree)
            hasSetInMainTree = false;
        this.isInMainTree = isInMainTree;
    }

    /**
     * 
     */
    public void resetLayoutWrapper() {
        if(inheritedLayout != null) {
//          System.out.println("SET INHERITED LO "+inheritedLayout+", "+this);
          layoutWrapper = (LayoutWrapper)inheritedLayout.getCopy(this);
          layoutWrapper.setSet(true);
          setLayoutWrapper(layoutWrapper);
          layoutWrapper.setSet(false);
      } else {
          Object defLO = getDefaultPropertyValue("layout");
          if(defLO == null) {
              if(isSWT() ) {
                  layoutWrapper = new LayoutWrapper(this, "Absolute", false);
                  layoutWrapper.setSet(true);
                  setLayoutWrapper(layoutWrapper, true);
                  layoutWrapper.setSet(false);
              }
          } else {
              layoutWrapper.setObject(defLO);
              layoutWrapper.setSet(true);
              setLayoutWrapper(layoutWrapper);
              layoutWrapper.setSet(false);
//              System.out.println("SET DEFAULT LO "+layoutWrapper+", "+this);
          }
      }
    }
    
    /**
     * True if this element was assigned in the code (eg, JLabel label; without
     * a "label = new JLabel()" is *not* assigned
     */
    public boolean isAssigned() {
        return assigned;
    }
    
    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
    
    public boolean wasCut() {
        return wasCut;
    }

    private void setWasCut(boolean b) {
        wasCut = b;
    }

    public void setWasCutForAll(boolean b) {
        wasCut = b;
        for(int i=0;i<getChildCount(); i++)
            getChildAt(i).setWasCutForAll(b);
    }

    public void addMethodCall(String methodName, Object[] params) {
        methodCalls.add(new Object[] {methodName, params});
    }
    
    public void addMethodCall(Method method, Object[] params) {
        methodCalls.add(new Object[] {method, params});
    }
    
    /**
     * Holds the style-string defined by parsing the SWT constructor.
     * This string will be used when generating the code for a new
     * constructor call, and will be set to null (requiring a new style-string
     * to be generated from the style field) when the style is changed.
     * @param styleStr
     */
    public void setStyleString(String styleStr) {
        styleString = styleStr;
    }

	/**
	 * When this FormComponent is passed in as a parameter
	 * to a method being parsed, set to true so that any properties
	 * set will not be assigned an ASTNode, so that if you try and
	 * set a property using Jigloo it will not over-write the setter in the
	 * method.
	 */
	public void setIsParameter(boolean b) {
		isParameter = b;
	}
	
	public boolean getIsParameter() {
		return isParameter;
	}

	private boolean nonstandardConstructor = false;
	
	/**
	 * If the element was created by a method like Box.createGlue()
	 * then nonstandardConstructor would be true.
	 */
	public void setNonstandardConstructor(boolean b) {
		nonstandardConstructor = b;
	}

	private boolean childHasLayoutConstraint(String con) {
		if(con == null)
			return false;
		for(int i=0;i<getChildCount();i++) {
			if(con.equals(getChildAt(i).getLayoutDataWrapper().getLayoutData()))
				return true;
		}
		return false;
	}
	
	public String getNextBorderConstraint() {
		if(!childHasLayoutConstraint(BorderLayout.CENTER))
			return BorderLayout.CENTER;
		if(!childHasLayoutConstraint(BorderLayout.NORTH))
			return BorderLayout.NORTH;
		if(!childHasLayoutConstraint(BorderLayout.WEST))
			return BorderLayout.WEST;
		if(!childHasLayoutConstraint(BorderLayout.EAST))
			return BorderLayout.EAST;
		if(!childHasLayoutConstraint(BorderLayout.SOUTH))
			return BorderLayout.SOUTH;
		return BorderLayout.CENTER;
	}

	public boolean isHiddenProperty(Object id) {
		initCustomProperties();
		if(beanPropDescs != null && beanPropDescs.containsKey(id)) {
			PropertyDescriptor pd = (PropertyDescriptor)beanPropDescs.get(id);
			if(pd.isHidden())
				return true;
		}
		return false;
	}
		
	public boolean isPropertyInCategory(String cat, String prop) {
	    return cat.equals(jiglooPlugin.getPropertyCategoryMap().get(prop));
	}
	
	public boolean isBasicProperty(Object id) {
		initCustomProperties();
		if(isSyntheticProperty((String)id))
			return true;
		
		if(beanPropDescs != null && beanPropDescs.containsKey(id)) {
			PropertyDescriptor pd = (PropertyDescriptor)beanPropDescs.get(id);
			if(pd.isExpert())
				return false;
			return true;
		}
		
		if(beanPropEds != null && beanPropEds.containsKey(id)) {
			return true;
		}
		
		if(isPropertyInCategory("Basic", (String) id))
		    return true;
		
		return false;
	}
	
	private BasicPropertySource basicPropSrc = null;
	private AdvancedPropertySource advPropSrc = null;
	
	public IPropertySource getBasicPropertySource() {
		if(!jiglooPlugin.showAdvancedProperties())
			return this;
		if(basicPropSrc == null)
			basicPropSrc = new BasicPropertySource(this);
		return basicPropSrc;
	}

	public IPropertySource getAdvancedPropertySource() {
		if(advPropSrc == null)
			advPropSrc = new AdvancedPropertySource(this);
		return advPropSrc;
	}

	public boolean isBuilderComponent() {
		return getName().indexOf("_bldr::JG") >= 0;
	}

	public Shell getShell() {
		return getEditor().getSite().getShell();
	}
	
	/**
	 * mode == 1 for insert
	 * mode == 2 for delete (not deleting contents)
	 * mode == 3 for delete (deleting contents)
	 */
	public void insertOrDeleteColumnOrRow(int index, int mode, boolean col, boolean spacer) {
		try {
			//TODO need to alter row or col definition for FormLayout, and col or
			//row weights for GridBagLayout
		    int delta = (mode == 1) ? 1 : -1;
		    
		    int width = 20;
		    double weight = 0.1;
		    double tableWidth = -1;
		    String spec = "max(15dlu;pref)";
		    
		    if(spacer) {
		        width = 7;
		        weight = 0.0;
		        tableWidth = 7.0;
		        spec = "5dlu";
		    }
		    
			LayoutWrapper lw = getLayoutWrapper();
			if(usesGridBagLayout()) {
			    initGridBagDimensions();
			    int oldSize = lw.getArrayPropertySize(col ? "columnWidths" : "rowHeights");
			    oldSize += delta;
				lw.setPropertyValue(col ? "columns" : "rows", new Integer(oldSize), false);
				lw.updateDoubleArray(delta, weight, index, col ? "columnWeights" : "rowWeights");
				lw.updateIntegerArray(delta, width, index, col ? "columnWidths" : "rowHeights");
				executeSetLayoutWrapper(lw);
				
			} else if(MigLayoutHandler.handlesLayout(this)) {
				//need to adjust index here so it will be used when moving components (below)
				MigLayoutHandler.insertOrDeleteColumnOrRow(this, (index-1)/2, mode, col);
				
			} else if(usesTableLayout()) {
			    String gapName = col ? "hGap" : "vGap";
			    int gap = ((Integer)lw.getPropertyValue(gapName)).intValue();
			    if(spacer && gap != 0) {
			        boolean res = MessageDialog.openQuestion(editor.getSite().getShell(), 
			                "Confirm insert of spacer row/column",
			                "This TableLayout already has a non-zero "+gapName+
			                " value of "+gap+", which will automatically space the components" +
			                " - are you sure you want to add a spacer "+(col ? "column" : "row")+"?");
			        if(!res)
			            return;
			    }
				//update sizes
				lw.updateDoubleArray(delta,tableWidth, index, col ? "column" : "row");
				
				setLayoutWrapper(lw, true, false);
				//don't call executeSetLayoutWrapper(lw); since this re-sets the
				//table layout for the container and resets the position of the children
				lw.setChanged(true);
				lw.repairInCode(false);
				
			} else if(usesJGFormLayout()) {
				index++;
				com.jgoodies.forms.layout.FormLayout fl = lw.getJGFormLayout();
				if(mode == 1) {
					if(col) {
						int cc = fl.getColumnCount();
						if(index <= cc)
							fl.insertColumn(index, new ColumnSpec(spec));
						else
							fl.appendColumn(new ColumnSpec(spec));
					} else {
						int rc = fl.getRowCount();
						if(index <= rc)
							fl.insertRow(index, new RowSpec(spec));
						else
							fl.appendRow(new RowSpec(spec));
					}
				} else {
					if(mode == 2) {
						FormComponent fc = isColumnOrRowPopulated(index, col);
						if(fc != null) {
							String msg = (col ? "Column" : "Row") +" not empty - "+fc.getNonBlockName();
							MessageDialog.openError(getShell(), msg, msg);
							return;
						}
						if(col) {
							fl.removeColumn(index);
						} else {
							fl.removeRow(index);
						}
					} else {
						if(mode == 3)
							depopulateColumnOrRow(index, col);
						if(col) {
							fl.removeColumn(index);
						} else {
							fl.removeRow(index);
						}
					}
				}
				executeSetLayoutWrapper(lw);
			} else {
				return;
			}
			
			int cellDelta = 1;
			if(MigLayoutHandler.handlesLayout(this))
				cellDelta = 2;
			for (int i = 0; i < components.size(); i++) {
				FormComponent fc = getChildAt(i);
				LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
				if(ldw != null) {
					int gyi = fc.getGridValue(col);
					int ghi = fc.getGridCellValue(col);
					if(mode == 3 && gyi == index) {
						editor.removeComponent(fc, true);
						//decrement i because above line removes fc from the children Vector
						i--;
					} else if(mode >= 2 && gyi >= index) {
						fc.setGridValue(gyi - cellDelta, col);
						fc.executeSetLayoutDataWrapper(ldw);
					} else if(mode >= 2 && gyi+ghi-1 >= index) {
						fc.setGridCellValue(ghi - cellDelta, col);
						fc.executeSetLayoutDataWrapper(ldw);
					} else if(mode == 1 && gyi >= index) {
						fc.setGridValue(gyi + cellDelta, col);
						fc.executeSetLayoutDataWrapper(ldw);
					}
				}
			}
			
			editor.setDirtyAndUpdate();
			
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	public void setGridValue(int val, boolean col) {
		if(parent.usesGridBagLayout()) {
			getLayoutDataWrapper().setPropertyValue(col ? "gridx" : "gridy", new Integer(val));
		} else if(parent.usesJGFormLayout()) {
			getLayoutDataWrapper().setPropertyValue(col ? "gridX" : "gridY", new Integer(val));
		} else if(MigLayoutHandler.handlesLayout(parent)) {
			MigLayoutHandler.setGridValue(this, col, val);
		} else if(parent.usesTableLayout()) {
			TableLayoutHandler.setGridValue(this, col, val);
		}
	}
	
	public void setGridCellValue(int val, boolean col) {
		if(parent.usesGridBagLayout()) {
			getLayoutDataWrapper().setPropertyValue(col ? "gridwidth" : "gridheight", new Integer(val));
		} else if(parent.usesJGFormLayout()) {
			getLayoutDataWrapper().setPropertyValue(col ? "gridWidth" : "gridHeight", new Integer(val));
		} else if(MigLayoutHandler.handlesLayout(parent)) {
			MigLayoutHandler.setGridCellValue(this, col, val);
		} else if(parent.usesTableLayout()) {
			TableLayoutHandler.setGridCellValue(this, col, val);
		}
	}
	
	/**
	 * Returns the x or y value of a cell for GridBag, Table or JGForm layouts
	 */
	public int getGridValue(boolean col) {
		if(parent == null)
			return 0;
		if(parent.usesGridBagLayout()) {
			return getLayoutDataWrapper().getIntProperty(col ? "gridx" : "gridy");
		} else if(parent.usesJGFormLayout()) {
			return getLayoutDataWrapper().getIntProperty(col ? "gridX" : "gridY");			
		} else if(MigLayoutHandler.handlesLayout(parent)) {
			return MigLayoutHandler.getGridValue(this, col);			
		} else if(parent.usesTableLayout()) {
    		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(parent);
    		TableLayoutConstraints con = tl.getConstraints(getComponent());
    		return col ? con.col1 : con.row1;
		}
		return -1;
	}
	
	/**
	 * Returns the width or height of a cell for GridBag, Table or JGForm layouts
	 */
	public int getGridCellValue(boolean col) {
		if(parent == null)
			return 0;
		if(parent.usesGridBagLayout()) {
			return getLayoutDataWrapper().getIntProperty(col ? "gridwidth" : "gridheight");
		} else if(parent.usesJGFormLayout()) {
			return getLayoutDataWrapper().getIntProperty(col ? "gridWidth" : "gridHeight");
		} else if(MigLayoutHandler.handlesLayout(parent)) {
			return MigLayoutHandler.getGridCellValue(this, col);			
		} else if(parent.usesTableLayout()) {
    		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(parent);
    		TableLayoutConstraints con = tl.getConstraints(getComponent());
    		return col ? (con.col2 - con.col1 + 1) : (con.row2 - con.row1 + 1);
		}
		return -1;
	}
	
	private FormComponent isColumnOrRowPopulated(int index, boolean col) {
		String prop = col ? "gridX" : "gridY";
		for (int i = 0; i < components.size(); i++) {
			FormComponent fc = getChildAt(i);
			LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
			if (ldw != null && ldw.getIntProperty(prop) == index) {
				return fc;
			}
		}
		return null;
	}

	private void depopulateColumnOrRow(int index, boolean col) {
		String prop = col ? "gridX" : "gridY";
		for (int i = 0; i < components.size(); i++) {
			FormComponent fc = getChildAt(i);
			LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
			if (ldw != null && ldw.getIntProperty(prop) == index) {
				editor.removeComponent(fc, true);
				i--;
			}
		}
	}
	
	private void moveColumnOrRow(int from, int to, boolean col) {
		try {
			//TODO need to alter row or col definition for FormLayout, and col or
			//row weights for GridBagLayout
			if(usesJGFormLayout()) {
				from++;
				to++;
			} else if(!usesGridTypeLayout()) {
				return;
			}
			
			Vector values = new Vector();
			for (int i = 0; i < components.size(); i++) {
				FormComponent fc = getChildAt(i);
				int val = fc.getGridValue(col);
				if(val >= 0) {
					Integer gy = new Integer( val);
					if(gy != null && !values.contains(gy))
						values.add(gy);
				}
			}
			Collections.sort(values);
			for (int i = 0; i < components.size(); i++) {
				FormComponent fc = getChildAt(i);
				LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
				if(ldw != null) {
					int val = fc.getGridValue(col);
					if(val < 0)
						continue;
					Integer gy = new Integer(val);
					if(val == from) {
						fc.setGridValue(to, col);
						fc.executeSetLayoutDataWrapper(ldw);
					} else {
						if(from < to) {
							if(val > from && val <= to) {
								int pos = values.indexOf(gy);
								if(pos >= 1) {
									fc.setGridValue(((Integer)values.elementAt(pos-1)).intValue(), col);
									fc.executeSetLayoutDataWrapper(ldw);
								}
							}
						} else {
							if(val >= to && val < from) {
								int pos = values.indexOf(gy);
								if(pos >= 0 && pos+1 < values.size()) {
									fc.setGridValue(((Integer)values.elementAt(pos+1)).intValue(), col);
									fc.executeSetLayoutDataWrapper(ldw);
								}
							}
						}
					}
				}
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * For JGoodies FormLayoutsand GridBagLayout
	 * @param i
	 * @param j
	 */
	public void moveColumn(int from, int to) {
		moveColumnOrRow(from, to, true);
	}

	/**
	 * For JGoodies FormLayouts and GridBagLayout
	 * @param i
	 * @param j
	 */
	public void moveRow(int from, int to) {
		moveColumnOrRow(from, to, false);
	}

	public boolean usesJGFormLayout() {
		return (layoutWrapper != null
				&& layoutWrapper.isJGForm());
	}
	
	public boolean usesGridBagLayout() {
		return (layoutWrapper != null
				&& "GridBag".equals(layoutWrapper.getSuperLayoutType()));
	}
	
	public boolean usesGroupLayout() {
		return (layoutWrapper != null
				&& "Group".equals(layoutWrapper.getSuperLayoutType()));
	}
	
	public boolean usesTableLayout() {
		return (layoutWrapper != null
				&& "Table".equals(layoutWrapper.getSuperLayoutType()));
	}
	
	/**
	 * For JGoodies FormLayouts
	 * @param i
	 * @param j
	 */
	public void resizeRow(int row, double size) {
		LayoutWrapper lw = getLayoutWrapper();
		if(usesJGFormLayout()) {
			row++;
			if(size < 3)
				size = 3;
			com.jgoodies.forms.layout.FormLayout fl = lw.getJGFormLayout();
			size = pixelToDLU((int)size, false);
			fl.setRowSpec(row, new RowSpec(((int)size)+"dlu"));
			String spec= lw.getFormLayoutSpec(fl, false);
			lw.setPropertyValue("rowSpecs", spec, true);
			lw.setChanged(true);
			lw.setSet(true);
			executeSetLayoutWrapper(lw);
		} else if(usesGridBagLayout()){
			lw.setIntArrayValue("rowHeights", row, (int) size);
			lw.setDoubleArrayValue("rowWeights", row, 0);
			lw.setChanged(true);
			lw.setSet(true);
			executeSetLayoutWrapper(lw);
		} else if(usesTableLayout()) {
			lw.setDoubleArrayValue("row", row, size);
			setLayoutWrapper(lw, true, false);
			lw.setChanged(true);
			lw.setSet(true);
			lw.repairConstructorInCode();
			editor.setDirtyAndUpdate();
		} else if(MigLayoutHandler.handlesLayout(this)) {
			MigLayoutHandler.resizeColumnOrRow(this, false, row, size);
		} else {
			return;
		}
	}
	/**
	 * For JGoodies FormLayouts
	 * @param i
	 * @param j
	 */
	public void resizeColumn(int col, double size) {
		LayoutWrapper lw = getLayoutWrapper();
		if(usesJGFormLayout()) {
			col++;
			if(size < 3)
				size = 3;
			com.jgoodies.forms.layout.FormLayout fl = lw.getJGFormLayout();
			size = pixelToDLU((int)size, true);
			fl.setColumnSpec(col, new ColumnSpec(((int)size)+"dlu"));
			String spec = lw.getFormLayoutSpec(fl, true);
			lw.setPropertyValue("colSpecs", spec, true, true);
			lw.setChanged(true);
			lw.setSet(true);
			executeSetLayoutWrapper(lw);
		} else if(usesGridBagLayout()) {
			lw.setIntArrayValue("columnWidths", col, (int)size);
			lw.setDoubleArrayValue("columnWeights", col, 0);
			lw.setChanged(true);
			lw.setSet(true);
			executeSetLayoutWrapper(lw);
		} else if(usesTableLayout()) {
			lw.setDoubleArrayValue("column", col, size);
			setLayoutWrapper(lw, true, false);
			lw.setChanged(true);
			lw.setSet(true);
			lw.repairConstructorInCode();
			editor.setDirtyAndUpdate();
		} else if(MigLayoutHandler.handlesLayout(this)) {
			MigLayoutHandler.resizeColumnOrRow(this, true, col, size);
		} else {
			return;
		}
	}
	
	public void setColumnOrRowWeight(int index, double weight, boolean column) {
		LayoutWrapper lw = getLayoutWrapper();
		if(usesGridBagLayout()) {
			if(column)
				lw.setDoubleArrayValue("columnWeights", index, weight);
			else
				lw.setDoubleArrayValue("rowWeights", index, weight);
		} else {
			return;
		}
		lw.setChanged(true);
		lw.setSet(true);
//		lw.repairConstructorInCode();
//		editor.setDirtyAndUpdate();
		executeSetLayoutWrapper(lw);
		redrawGridEdges();
	}
	
	public void setColumnOrRowSize(int index, double weight, boolean column) {
		if(column)
			resizeColumn(index, weight);
		else
			resizeRow(index, weight);
	}
	
	public void setFormSpec(String spec, boolean colspec) {
		LayoutWrapper lw = getLayoutWrapper();
		if(colspec) {
			lw.setPropertyValue("colSpecs", spec);
		} else {
			lw.setPropertyValue("rowSpecs", spec);
		}
		lw.setChanged(true);
		lw.setSet(true);
		executeSetLayoutWrapper(lw);
		redrawGridEdges();
	}
	
	public String getFormSpecAsString(int index, boolean col) {
		LayoutWrapper lw = getLayoutWrapper();
		String spec = "";
		if("GridBag".equals(lw.getLayoutType())) {
			if(col) {
				spec = "[Col "+(index-1)+"] - weight:"
				+lw.getDoubleArrayValue("columnWeights", index - 1)
				+", width:"+lw.getIntArrayValue("columnWidths", index-1);
			} else {
				spec = "[Row "+(index-1)+"] - weight:"
				+lw.getDoubleArrayValue("rowWeights", index-1)
				+", height:"+lw.getIntArrayValue("rowHeights", index-1);
			}
		} else if("Table".equals(lw.getLayoutType())) {
			double val;
			if(col) {
				spec = "[Col "+(index-1)+"]";
				val = lw.getDoubleArrayValue("column", index - 1);
			} else {
				spec = "[Row "+(index-1)+"]";
				val = lw.getDoubleArrayValue("row", index - 1);
			}
			String sval = ""+val;
			if(val == TableLayoutConstants.FILL)
				sval = "FILL";
			if(val == TableLayoutConstants.PREFERRED)
				sval = "PREFERRED";
			if(val == TableLayoutConstants.MINIMUM)
				sval = "MINIMUM";
			spec += " - size:"+sval;
		} else if(lw.isJGForm()) {
			com.jgoodies.forms.layout.FormLayout fl = lw.getJGFormLayout();
			if(col)
				spec = "[Col "+index+"] "+fl.getColumnSpec(index);
			else
				spec =  "[Row "+index+"] "+fl.getRowSpec(index);
		} else if(MigLayoutHandler.handlesLayout(this)) {
			spec = MigLayoutHandler.getRowOrColDesc(this, index, col);
		} else {
			return null;
		}
		return spec;
	}
	
	public int pixelToDLU(int pix, boolean cols) {
		int dlu = pix;
		int pix0 = pix;
		UnitConverter uc = Sizes.getUnitConverter();
		if(cols) {
			pix = uc.dialogUnitXAsPixel(dlu, component);
			if(pix < pix0) {
				while(pix < pix0)
					pix = uc.dialogUnitXAsPixel(++dlu, component);
			} else {
				while(pix > pix0)
					pix = uc.dialogUnitXAsPixel(--dlu, component);
			}
		} else {
			pix = uc.dialogUnitYAsPixel(dlu, component);
			if(pix < pix0) {
				while(pix < pix0)
					pix = uc.dialogUnitYAsPixel(++dlu, component);
			} else {
				while(pix > pix0)
					pix = uc.dialogUnitYAsPixel(--dlu, component);
			}
		}
		return dlu;
	}

	/**
	 * True if this is a Panel inside a SWT_AWT.new_Frame (or a SWT_AWT.new_Frame)
	 * or a Composite inside a SWT_AWT_new_Shell (or a SWT_AWT_new_Shell).
	 * @return
	 */
	public boolean isSWTAWTInternal() {
		return isSwingInSwt() || (parent != null && parent.isSwingInSwt())
		|| isSwtInSwing();
	}

	/**
	 * @return
	 */
	public FormComponent getSWTAWTContainer() {
		if(isSwtInSwing() || isSwingInSwt())
			return getParent();
		if(isSWTAWTInternal())
			return getParent().getParent();
		return null;
	}

	/**
	 * 
	 */
	public void refreshTreeNode() {
		editor.refreshTreeNode(this);
	}

    public FormComponent getChildWithObject(Object obj) {
	    int cc = getChildCount();
	    for(int i=0; i< cc; i++) {
	        FormComponent ch = getChildAt(i);
//	        if(ch.isInherited())
//	            continue;
	        Object co = ch.getControl();
	        if(co == null)
	            co = ch.getComponent();
	        if(co == null)
	            co = ch.getNonVisualObject();
	        if(obj.equals(co)) {
	            return ch;
	        }
	    }
	    return null;
    }
    
	public org.eclipse.ui.forms.widgets.Form getEclipseForm() {
	    if(!jiglooPlugin.canUseForms())
	        return null;
	    FormComponent root = getRootComponent();
	    Object rc = null;
	    if(root != null)
	        rc = root.getControl();
    	if(rc instanceof org.eclipse.ui.forms.widgets.Form)
    		return (org.eclipse.ui.forms.widgets.Form)rc;
    	try {
    	    if(rc == null || editor.getMainControl() == null) {
    	        if(root == null)
    	            root = FormComponent.newFormComponent(getEditor(), org.eclipse.ui.forms.widgets.ScrolledForm.class.getName());
    	        root.setEditor(getEditor());
    	        root.setAssigned(true);
    	        root.setInMainTree(true);
    	        root.setExistsInCode(true);
    	        root.setClassName(org.eclipse.ui.forms.widgets.ScrolledForm.class.getName());
    	        editor.getJavaCodeParser().setRootComponent(root);
    	        editor.setEditorMode(getJCP());
    	        editor.setRootComponent(root);
    	        editor.populateRoot();
        	    editor.initRootControlLayoutData();
        	    root.initProperties();
    	    }
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    	return (org.eclipse.ui.forms.widgets.Form)root.getControl();
    }
	
	private EclipseFormCall eclipseFormCall;
	
	private org.eclipse.ui.forms.widgets.FormToolkit getFormToolkit() {
	    if(!jiglooPlugin.canUseForms())
	        return null;
	    return (org.eclipse.ui.forms.widgets.FormToolkit)getRootComponent().formToolkit;
	}
	
    public Object invokeOnFormToolkit(String method, Object[] params, 
            Expression mic, FormComponent fc) {
	    if(!jiglooPlugin.canUseForms())
	        return null;
        getEclipseForm();
	    setAssigned(true);
//	    System.out.println("INVOKE ON TOOLKIT "+getJavaCodeParser().getCodeForNode(mic));
    	Object obj = invokeOnControl(getFormToolkit(), method, params);
    	if(method.startsWith("create")) {
    	    FormComponent par= (FormComponent)params[0];
    	    Object lastP = params[params.length-1];
    	    int style = 0;
    	    if(lastP instanceof Integer)
    	        style = ((Integer)lastP).intValue();
    	    //need to get inherited elements again first, because eg for
    	    //formToolkit.createSectionSeparator(section), if section had
    	    //initInheritedElements previously called and there was no
    	    //separator, then separator would have been removed, so
    	    //we need to add it back in again with getInheritedElements
    	    par.getInheritedElements();
    	    par.initInheritedElements();
    	    if(fc == null)
    	        fc = par.getChildWithObject(obj);
    	    fc = getJavaCodeParser().createFormComponent(
    	            fc, par, (Control)obj, mic);
    	    fc.eclipseFormCall = new EclipseFormCall(method, params, mic);
    	    fc.setFormToolkitFC(this);
    	    fc.setStyle(style, false);
    	    editor.addComponent(fc);
    	    return fc;
    	}
    	return obj;
    }
    
    public FormComponent getFormBody() {
	    if(!jiglooPlugin.canUseForms())
	        return null;
        org.eclipse.ui.forms.widgets.Form form = getEclipseForm();
        Composite body = form.getBody();
        body.getParent().layout();
        return getRootComponent().getChildWithObject(body);
    }
    
    public Object invokeOnForm(String method, Object[] params, 
            Expression mic, FormComponent fc) {
	    if(!jiglooPlugin.canUseForms())
	        return null;
//	    System.out.println("INVOKE ON FORM "+getJavaCodeParser().getCodeForNode(mic));
	    setAssigned(true);
    	Object obj = invokeOnControl(getEclipseForm(), method, params);
    	if(method.startsWith("get")) {
    	    FormComponent root = getRootComponent();
    	    if(fc == null)
    	        fc = root.getChildWithObject(obj);
    	    if(fc == null) {
    	        fc = FormComponent.newFormComponent(root, obj.getClass().getName(), null, false, null);
    	    }
    	    if(obj instanceof Control)
    	        fc.setControl((Control)obj);
    	    else
    	        fc.setNonVisualObject(obj);
    	    fc.eclipseFormCall = new EclipseFormCall(method, params, mic);
    	    fc.setExistsInCode(true);
    	    fc.setAssigned(true);
    	    fc.setInMainTree(true);
    	    fc.initProperties(obj);
    	    return fc;
    	}
    	return obj;
    }
    
    public Object invokeOnManagedForm(String method, Object[] params, 
            JavaCodeParser jcp, Expression mic) {
//    	methodObject = getManagedForm();
//    	Object obj = invokeOnControl(method, params);
//    	methodObject = null;
//    	if(method.startsWith("get")) {
//    	    FormComponent fc = new FormComponent(this, obj.getClass().getName());
//    	    fc.setObject(obj);
//    	    return fc;
//    	}
//    	return obj;
        return null;
    }
    
    /**
     * @return
     */
    public boolean isJFaceManagedForm() {
        if(!jiglooPlugin.canUseForms())
            return false;
        try {
            return isSubclassOf(org.eclipse.ui.forms.IManagedForm.class);
        } catch(Throwable t) {
            return false;
        }
    }
    public boolean isJFaceForm() {
        if(!jiglooPlugin.canUseForms())
            return false;
         try {
            return isSubclassOf(org.eclipse.ui.forms.widgets.ScrolledForm.class);
        } catch(Throwable t) {
            return false;
        }
    }
    /**
     * @return
     */
    public boolean isJFaceFormToolkit() {
        if(!jiglooPlugin.canUseForms())
            return false;
         try {
            return isSubclassOf(org.eclipse.ui.forms.widgets.FormToolkit.class);
        } catch(Throwable t) {
            return false;
        }
    }

    private FormComponent formToolkitFC = null;
    
    public FormComponent getFormToolkitFC() {
        return formToolkitFC;
    }

    public void setFormToolkitFC(FormComponent formToolkitFC) {
        this.formToolkitFC = formToolkitFC;
    }

    /**
     * @return
     */
    public boolean isEclipseFormElement() {
        return eclipseFormCall != null;
    }

    /**
     * @param name2
     * @return
     */
    public String getPropertyCategory(String name) {
        return (String)jiglooPlugin.getPropertyCategoryMap().get(name);
    }
    
    /**
     * Sets the property descriptors to null so that the PropertySheetViewer will
     * load a new set (with the new categories)
     */
    public void refreshCategories() {
        propertyDescriptors = null;
    }

    /**
     * 
     */
    public void redrawGridEdges() {
    	editor.getGridEdgeManager().redrawMarkers();
    }

    /**
     * @return true if class is direct subclass of AWT, Swing or SWT
     */
    public boolean hasStandardSuperclass() {
        String mc = getClassName();
        if(mc == null)
            return false;
        if(mc.startsWith("java.")
                || mc.startsWith("javax.")
                || mc.startsWith("org.eclipse.")
                )
            return true;
        return false;
    }

    /**
     * @return
     */
    public boolean isRootShell() {
        return equals(getEditor().getRootShell());
    }

    public boolean isRootDecoration() {
        return control != null && editor != null && control.equals(editor.getRootDecoration(false));
    }
    
    private CLabel tabNumberLabel;
    private int tabNumber = -1;
    /**
     * @param show
     */
    public void showTabNumber(boolean show) {
        if(show && tabNumberLabel == null)
            tabNumberLabel = new CLabel(editor.getMainControl(), SWT.BORDER);
        if(show) {
            tabNumberLabel.setBackground(ColorManager.getColor(0, 255, 100));
            tabNumberLabel.setAlignment(SWT.CENTER);
            if(tabNumber == -1)
                tabNumberLabel.setText("...");
            else
                tabNumberLabel.setText(""+tabNumber);
            tabNumberLabel.setVisible(true);
            Rectangle b = getBoundsRelativeToRoot();
            tabNumberLabel.setBounds(b.x+1, b.y+1, 20, 20);
            tabNumberLabel.moveAbove(null);
        } else {
            if(tabNumberLabel != null)
                tabNumberLabel.setVisible(false);
        }
    }

    public void clearTabList() {
        int cc = getNonInheritedChildCount();
        FormComponent[] fcs = new FormComponent[cc];
        int tc = 0;
        for(int i=0;i<cc;i++) {
            FormComponent fc = getNonInheritedChildAt(i);
            fc.setTabNumber(-1);
        }
//        resetPropertyValue("tabList", true);
//        initTabList();
    }
    
    public void fixTabList() {
        int cc = getNonInheritedChildCount();
        FormComponent[] fcs = new FormComponent[cc];
        int tc = 0;
        for(int i=0;i<cc;i++) {
            FormComponent fc = getNonInheritedChildAt(i);
            if(fc.getTabNumber() > 0) {
                fcs[fc.getTabNumber()-1] = fc;
                tc++;
            }
        }
        if(tc == 0) {
            resetPropertyValue("tabList", true);
            resetPropertyValue("focusTraversalPolicy", true);
            return;
        }
        FormComponent[] tcs = new FormComponent[tc];
        tc = 0;
        for(int i=0;i<cc;i++) {
            if(fcs[i] != null) {
                tcs[tc++] = fcs[i];
            }
        }
        if(hasProperty("tabList")) {
            FormComponentArrayWrapper fcaw = new FormComponentArrayWrapper(tcs, this);
            setPropertyValue("tabList", fcaw);
        } else if (hasProperty("focusTraversalPolicy")) {
            FocusTraversalPolicyWrapper fcaw = new FocusTraversalPolicyWrapper(tcs, this);
            setPropertyValue("focusTraversalPolicy", fcaw);
        }
    }
    
    public void initTabList() {
        int cc = getNonInheritedChildCount();
        FormComponent[] fcs;
        int tc = 0;
        for(int i=0;i<cc;i++) {
            FormComponent fc = getNonInheritedChildAt(i);
            fc.setTabNumber(-1);
        }
        if(hasProperty("tabList")) {
            FormComponentArrayWrapper fcaw 
            	= (FormComponentArrayWrapper) getPropertyValue("tabList");
            Object val = fcaw.getValue(null);
            if(val instanceof FormComponent[]) {
                fcs = (FormComponent[])val;
                for (int i = 0; i < fcs.length; i++) {
                    if(this.equals(fcs[i].getParent()))
                        fcs[i].setTabNumber(i+1);
                }
            }
        } else if (hasProperty("focusTraversalPolicy")) {
            FocusTraversalPolicyWrapper fcaw 
            	= (FocusTraversalPolicyWrapper) getPropertyValue("focusTraversalPolicy");
            fcs = fcaw.getArrayValue();
            if(fcs != null) {
                for (int i = 0; i < fcs.length; i++) {
                    if(this.equals(fcs[i].getParent()))
                        fcs[i].setTabNumber(i+1);
                }
            }
        }
    }
    
    /**
     * @param i
     */
    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
        showTabNumber(true);
    }

    /**
     * @return
     */
    public int getTabNumber() {
        return tabNumber;
    }

    /**
     * @return
     */
    public String getNameOrMethodInCode() {
        String nic = getNameInCode();
        nic = getJavaCodeParser().convertToMethod(getName(), nic, false);
        return nic;
    }

    /**
     * @see #setFactoryElement(boolean)
     */
    public boolean isFactoryElement() {
        return isFactoryElement;
    }
    /**
     * True if this element was created by a method call, like ControlFactory.createButton("hello", parent)
     * or even a parsed method in the current class
     */
    public void setFactoryElement(boolean isFactoryElement) {
        this.isFactoryElement = isFactoryElement;
    }
    public boolean containsFactoryElements() {
        if(isFactoryElement)
            return true;
        for(int i=0; i< getChildCount(); i++) {
            if(getChildAt(i).containsFactoryElements())
                return true;
        }
        return false;
    }

    /**
     * @param ped2
     * @param id
     */
    private void setCustomPropertyEditor(Object id, PropertyEditor ped) {
        String pcn = ped.getClass().getName();
        if((pcn.startsWith("com.sun") || pcn.startsWith("sun.beans")) && isSWT())
            return;
        if(pcn.startsWith("org.netbeans")) {
            return;
        }
//        System.out.println("Setting custom property editor for "+id+" "+ped);
        beanPropEds.put(id, ped);
    }

    /**
     * @return
     */
    public JComponent getJComponent() {
        if(component instanceof JComponent)
            return (JComponent)component;
        return null;
    }

    /**
     * @return
     */
    public boolean needsLayoutGrid() {
        if(!isContainer())
            return false;
        if(layoutWrapper == null)
        	return false;
        String lt = layoutWrapper.getSuperLayoutType();
		return "Table".equals(lt) || "GridBag".equals(lt) || "Mig".equals(lt) || usesJGFormLayout();
    }
    
    public FormComponent getMenuBar() {
        return menuBar;
    }
    
    public void setMenuBar(FormComponent menuBar) {
        this.menuBar = menuBar;
        if(menuBar != null)
            menuBar.setMenuBarParent(this);
    }
    
    public void setMenuBarParent(FormComponent menuBarParent) {
        this.menuBarParent = menuBarParent;
    }

    /**
     * @return
     */
    public boolean usesSnapTo() {
        return 
        usesGroupLayout()
        || (isSwing() && usesAbsoluteTypeLayout() && isContainer())
		|| (isSWT() && usesAbsoluteTypeLayout() && isContainer());
    }

	/**
	 * Used to designate that this FC is the main class component (eg, if the main class extends JPanel,
	 * and this FC represents the main class then this is the base component
	 */
	public void setBaseComponent(boolean isBaseComponent) {
		this.isBaseComponent = isBaseComponent;
	}
	
	public boolean isBaseComponent() {
		return isBaseComponent;
	}

    /**
     * @return
     */
    public String getIdentifier() {
        String nameInProps = (String)getPropertyValue("name");
        if(nameInProps != null && !"".equals(nameInProps))
            return nameInProps;
        if(isSingleFrameApplication())
            nameInProps  = "mainFrame";
        nameInProps = getNameInCode();
        if(isSubclassOf(AbstractAction.class))
            nameInProps += ".Action";
        return nameInProps;
    }

    /**
     * @param x
     * @param y
     */
    public void updateGridCoords(int x, int y, int x2, int y2, FormComponent gbc) {
        int[] xy = getGridBagCoords(x, y, true, true);
        int[] xy2 = { FormComponent.GRIDBAG_UNDEFINED,
                FormComponent.GRIDBAG_UNDEFINED };
        if (x2 != FormComponent.GRIDBAG_UNDEFINED
                && y2 != FormComponent.GRIDBAG_UNDEFINED) {
            xy2 = getGridBagCoords(x2, y2, true, false);
        }
        setGridBagCoords(gbc, xy[0], xy[1], xy2[0], xy2[1]);
    }

    /**
     * @param name2
     * @return
     */
    public boolean canUpdateSyntheticProperty(String pName) {
        if("buttonGroup".equals(pName) || "selection".equals(pName) || isJFaceApplicationWindow())
            return true;
        if(isSubclassOf(AbstractAction.class))
            return true;
        return false;
    }

	/**
	 * @param bounds
	 */
	public void subtractInsets(Rectangle bounds) {
		if(!isSwing())
			return;
        JComponent cp = (JComponent)getContentPane();
        JComponent mtComp = (JComponent) getComponent();
        if(cp == null)
        	cp = mtComp;
        Insets ins = cp.getInsets();
        if(ins != null) {
        	bounds.x += ins.left;
        	bounds.y += ins.top;
        	bounds.width -= (ins.left + ins.right);
        	bounds.height -= (ins.top + ins.bottom);
        }
	}
    
	public void setMethodReturnValue(boolean value) {
		isMethodReturnValue = value;
	}
	
	public boolean isMethodReturnValue() {
		return isMethodReturnValue;
	}

	public void setRoot(boolean b) {
		isRoot = b;
	}
	
	public void settingLayoutConstraint(boolean setting) {
		settingLayoutConstraint = setting;
	}

	public void setArrayElement(int arrayIndex, Object val) {
        Object obj = getNonVisualObject();
        if(obj instanceof ArrayHolder) {
        	ArrayHolder ah = (ArrayHolder)obj;
        	if(val instanceof FormComponent)
        		ah.setFCArrayElement(arrayIndex, (FormComponent) val);
        	else {
               	if(arrayIndex < ((Object[])ah.getRawArray()).length)
               		((Object[])ah.getRawArray())[arrayIndex] = val;
        	}
        } else {
        	if(val instanceof FormComponent)
        		val = ((FormComponent)val).getObject(false);

        	if(arrayIndex < ((Object[])obj).length)
        		((Object[])obj)[arrayIndex] = val;
        	
        }
	}

	private MenuWindow editorMenu;

	public void showMenuInEditor() {
		int h = 0;
		if(isA(MenuItem.class) 
				|| isSubclassOf(JMenuItem.class) ) {
			getEditorMenu().show(getBounds().x, 0);
			getEditorMenu().showChildren();
		}
	}

	public MenuWindow getEditorMenu() {
		if(editorMenu == null && isMenuComponent())
			editorMenu = new MenuWindow(this);
		return editorMenu;
	}

	public void showContextMenu() {
		editor.showContextMenu(editor.getViewportControl(), this);
	}

	public void changeClass(String newName) {
        String oldName = getTranslatedClassName();
        try {

            setClassName(newName);
            setLayoutWrapper(null);
            clearPropertyNames();
            setConstructor(null, null, null);

            if (!isSwing()) {
                rebuildParent(true);
            } else {
                rebuild(true);
            }
            
            getLayoutWrapper().setSet(false);

            getJavaCodeParser().repairInlineAssignment(this);
            repairInCode(true);
            
        } catch (Throwable e) {
            setClassName(oldName);
            jiglooPlugin.handleError(e);
        }
	}

	public void addBuilderMethodCall(String methodName, Object[] params,
			MethodInvocation mic) {
//		builderMethodCalls.add(new Object[] {methodName, params, mic});
		if(getBuilderPanel() != null) {
			int before = getBuilderPanel().getComponentCount();
			invokeOnBuilder(methodName, params);
			if(editor != null)
				editor.getJavaCodeParser().deduceFormComponents(before, params, this, (MethodInvocation)mic);
		} else {
			invokeOnBuilder(methodName, params);
		}
	}
	
    public void invokeBuilderMethodCalls() {
    	for(int i=0;i<builderMethodCalls.size(); i++) {
			Object[] mc = (Object[])builderMethodCalls.elementAt(i);
			String methodName = (String)mc[0];
			Object[] params = (Object[]) mc[1];
    		if(getBuilderPanel() != null) {
    			int before = getBuilderPanel().getComponentCount();
    			MethodInvocation mic = (MethodInvocation)mc[2];
    			invokeOnBuilder(methodName, params);
    			if(editor != null && mic != null)
    				editor.getJavaCodeParser().deduceFormComponents(before, params, this, mic);
    		} else {
    			invokeOnBuilder(methodName, params);
    		}
    	}
    }

	public boolean noLayout() {
		return false;
	}
	
}