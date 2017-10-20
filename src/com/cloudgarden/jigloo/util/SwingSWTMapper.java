package com.cloudgarden.jigloo.util;

import java.awt.Container;
import java.awt.Panel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
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
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JWindow;

import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.part.DrillDownComposite;
import org.eclipse.ui.part.PageBook;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.controls.OrderableGroup;
import com.cloudgarden.jigloo.controls.OrderableSashForm;

public class SwingSWTMapper {

	static HashMap swtRelationships = new HashMap();
	static Vector swtContainers = new Vector();
	static HashMap swingMap = new HashMap();
	static Vector swingContainers = new Vector();
	static HashMap orderableMap = new HashMap();
	static HashMap nonOrderableMap = new HashMap();

    static {
    	
        addToOrderableMap(Composite.class, OrderableComposite.class);
        addToOrderableMap(Group.class, OrderableGroup.class);
        //addToOrderableMap(CTabFolder.class, OrderableCTabFolder.class);
        addToOrderableMap(SashForm.class, OrderableSashForm.class);

        swtRelationships.put(TabFolder.class, TabItem.class);
        swtRelationships.put(CTabFolder.class, CTabItem.class);
        try {
            swtRelationships.put(Tree.class, new Class[] { TreeItem.class,
                    TreeColumn.class });
            swtRelationships.put(TreeViewer.class, TreeColumn.class);
        } catch(Throwable t) {
            swtRelationships.put(Tree.class, TreeItem.class);
        }
        
        try {
            swtRelationships.put(org.eclipse.swt.widgets.ExpandBar.class, org.eclipse.swt.widgets.ExpandItem.class);
        } catch(Throwable t) {}
        
        swtRelationships.put(TreeItem.class, TreeItem.class);
        swtRelationships.put(Menu.class, MenuItem.class);
        //it's a HashMap, remember, so can't have two lines starting with
        // "Table"
        swtRelationships.put(Table.class, new Class[] { TableItem.class,
                TableColumn.class });
        swtRelationships.put(TableViewer.class, TableColumn.class);

        swtRelationships.put(TableTree.class, TableTreeItem.class);
        swtRelationships.put(TableTreeItem.class, TableTreeItem.class);
        swtRelationships.put(ToolBar.class, ToolItem.class);
        swtRelationships.put(CoolBar.class, CoolItem.class);

        try {
        	swtContainers.add(org.eclipse.swt.widgets.ExpandItem.class);
        } catch(Throwable t) {}
        
        swtContainers.add(CoolItem.class);
        swtContainers.add(CTabItem.class);
        swtContainers.add(TabItem.class);
        swtContainers.add(Composite.class);
        swtContainers.add(OrderableComposite.class);
        swtContainers.add(Group.class);
        swtContainers.add(OrderableGroup.class);
        //swtContainers.add(SashForm.class);
        swtContainers.add(OrderableSashForm.class);
        swtContainers.add(ScrolledComposite.class);
        swtContainers.add(ViewForm.class);
        swtContainers.add(CLabel.class);
        swtContainers.add(Canvas.class);
        swtContainers.add(Dialog.class);
        swtContainers.add(Shell.class);

        if (jiglooPlugin.canUseSwing()) {
            swingContainers.add(JPanel.class);
            swingContainers.add(JDesktopPane.class);
            swingContainers.add(JLayeredPane.class);
            swingContainers.add(JDialog.class);
            swingContainers.add(JFrame.class);
            swingContainers.add(JWindow.class);
//            swingContainers.add(JLabel.class);
            swingContainers.add(JInternalFrame.class);
            swingContainers.add(JScrollPane.class);
            swingContainers.add(JSplitPane.class);
            swingContainers.add(JTabbedPane.class);
            swingContainers.add(JToolBar.class);
            swingContainers.add(JApplet.class);

            addToSwingMap(JSlider.class, Scale.class);
            addToSwingMap(JButton.class, Button.class);
            addToSwingMap(JCheckBox.class, Button.class, SWT.CHECK);
            addToSwingMap(JRadioButton.class, Button.class, SWT.RADIO);
            addToSwingMap(JLabel.class, CLabel.class);
            addToSwingMap(JEditorPane.class, StyledText.class, SWT.MULTI);
            addToSwingMap(JTable.class, Table.class, SWT.MULTI);
            addToSwingMap(JTree.class, Tree.class, SWT.MULTI);
            addToSwingMap(JTabbedPane.class, CTabFolder.class);
            addToSwingMap(JTextArea.class, Text.class, SWT.MULTI);
            addToSwingMap(JTextField.class, Text.class);
            addToSwingMap(JToolBar.class, ToolBar.class);
            addToSwingMap(JSplitPane.class, OrderableSashForm.class);
            addToSwingMap(JProgressBar.class, ProgressIndicator.class);
            addToSwingMap(JComboBox.class, CCombo.class);
            addToSwingMap(java.awt.Canvas.class, Canvas.class);
            addToSwingMap(JMenuBar.class, Menu.class, SWT.BAR);
            addToSwingMap(JMenu.class, MenuItem.class, SWT.CASCADE);
            addToSwingMap(JMenuItem.class, MenuItem.class, SWT.PUSH);
            addToSwingMap(JCheckBoxMenuItem.class, MenuItem.class, SWT.CHECK);
            addToSwingMap(JRadioButtonMenuItem.class, MenuItem.class, SWT.RADIO);
            addToSwingMap(JFrame.class, OrderableComposite.class);
            addToSwingMap(JPanel.class, OrderableComposite.class);
            if(jiglooPlugin.getJavaVersion() > 13) {
                addToSwingMap(JSpinner.class, CCombo.class);
            }
            addSwing2SWTMapping(JDialog.class, Dialog.class);
            addSwing2SWTMapping(JApplet.class, OrderableComposite.class);
            addSwing2SWTMapping(JDesktopPane.class, CTabFolder.class);
            addSwing2SWTMapping(JInternalFrame.class, CTabItem.class);

            //TODO addSWTMapping should include a style param
            //addSWTMapping(OrderableComposite.class, JPanel.class);
            addSWT2SwingMapping(ViewForm.class, JPanel.class);
            addSWT2SwingMapping(TableTree.class, JTree.class);
            addSWT2SwingMapping(ProgressBar.class, JProgressBar.class);
            addSWT2SwingMapping(DrillDownComposite.class, JPanel.class);
            addSWT2SwingMapping(PageBook.class, JPanel.class);
            addSWT2SwingMapping(Group.class, JPanel.class);
            addSWT2SwingMapping(Composite.class, JPanel.class);

            addSWT2SwingMapping(CTabItem.class, JPanel.class);
            addSWT2SwingMapping(TabItem.class, JPanel.class);
            addSWT2SwingMapping(CoolBar.class, JPanel.class);
            addSWT2SwingMapping(ToolItem.class, JButton.class);
            addSWT2SwingMapping(CoolItem.class, JPanel.class);
            addSWT2SwingMapping(TabFolder.class, JTabbedPane.class);

            addSWT2SwingMapping(Label.class, JLabel.class);
            addSWT2SwingMapping(Menu.class, JMenu.class);
            addSWT2SwingMapping(Combo.class, JComboBox.class);
            addSWT2SwingMapping(OrderableGroup.class, JPanel.class);
            addSWT2SwingMapping(FilteredList.class, JList.class);
            addSWT2SwingMapping(MenuItem.class, SWT.SEPARATOR, JSeparator.class);
            addSWT2SwingMapping(Label.class, SWT.SEPARATOR, JSeparator.class);

            addToSwingMap(JScrollPane.class, ScrolledComposite.class,
                    SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        }

    };

	static void addSwing2SWTMapping(Class swingClass, Class swtClass) {
	    swingMap.put(swingClass.getName(), new Object[] { swtClass.getName(),
	            new Integer(SWT.NULL) });
	}

	static void addToSwingMap(Class swingClass, Class swtClass) {
	    addToSwingMap(swingClass, swtClass, SWT.NULL);
	}

	static void addSWT2SwingMapping(Class swtClass, int style,
	        Class swingClass) {
	    swingMap.put(swtClass.getName() + "|" + style, swingClass.getName());
	}

	static void addSWT2SwingMapping(Class swtClass, Class swingClass) {
	    swingMap.put(swtClass.getName() + "|" + SWT.NULL, swingClass.getName());
	}

	static void addToOrderableMap(Class swtClass, Class orderableClass) {
	    orderableMap.put(swtClass.getName(), orderableClass.getName());
	    nonOrderableMap.put(orderableClass.getName(), swtClass.getName());
	}

	static void addToSwingMap(Class swingClass, Class swtClass,
	        int style) {
	    swingMap.put(swingClass.getName(), new Object[] { swtClass.getName(),
	            new Integer(style) });
	    //System.err.println("ADDING TO MAP "+swtClass.getName() + "|" +
	    // style);
	    swingMap.put(swtClass.getName() + "|" + style, swingClass.getName());
	}

	public static boolean canAddTo(FormComponent parent, Class child, int style) {
	
	    if (!ClassUtils.isVisual(child))
	        return true;
	
	    if (child == null)
	        return true;
	
	    //SWT_AWT class is neither swing or swt, so don't want
	    //to exclude adding a SWT_AWT to a swing or SWT
	    if (parent.isSwing() && ClassUtils.isSWT(child))
			return false;
	    if (parent.isSWT() && ClassUtils.isSwing(child))
			return false;
	
	    if (parent.isSWT()) {
	    	
	        if (child.equals(Menu.class)) {
	            if ((style & SWT.BAR) != 0 && parent.isRoot())
	                return true;
	            if (parent.isA(MenuItem.class))
	                return true;
	            return false;
	        }
	        Iterator iter = swtRelationships.keySet().iterator();
	        boolean specialChild = false;
	        boolean specialParent = false;
	        boolean foundChild;
	        boolean foundParent;
	        while (iter.hasNext()) {
	            Class pclass = (Class) iter.next();
	            Object cclass = swtRelationships.get(pclass);
	            foundParent = parent.isA(pclass);
	            if (foundParent)
	                specialParent = true;
	            if (cclass instanceof Class[]) {
	                Class[] cclasses = (Class[]) cclass;
	                for (int i = 0; i < cclasses.length; i++) {
	                    Class class1 = cclasses[i];
	                    foundChild = child.equals(class1);
	                    if (foundChild)
	                        specialChild = true;
	                    if (foundChild && foundParent)
	                        return true;
	                }
	            } else {
	                foundChild = child.equals((Class) cclass);
	                if (foundChild)
	                    specialChild = true;
	                if (foundChild && foundParent)
	                    return true;
	            }
	        }
	        if (specialChild || specialParent)
	            return false;
	        for (int i = 0; i < swtContainers.size(); i++) {
	            if (parent.isSubclassOf((Class) swtContainers.elementAt(i)))
	                return true;
	        }
	        //if this is a standard swt component, return false
	        if(parent.getClassName().startsWith("org.eclipse."))
	            return false;
	        //otherwise, assume it's a custom comp
	        if(parent.isSubclassOf(Composite.class))
	            return true;
	        return false;
	
	    } else if (parent.isSwing()) {
	    	
	        if( Cacher.isAssignableFrom(JPopupMenu.class, child))
	            return true;
	        
	        if (parent.isSubclassOf(JSplitPane.class)) {
	            if (parent.getNonInheritedChildCount() >= 2)
	                return false;
	            return true;
	        }
	        if (parent.isSubclassOf(JScrollPane.class)) {
	            if (parent.getNonInheritedChildCount() >= 1)
	                return false;
	            return true;
	        }
	        if (parent.isA(JMenuBar.class)) {
	            if ( Cacher.isAssignableFrom(JMenu.class, child))
	                return true;
	            return false;
	        }
	        if ( Cacher.isAssignableFrom(JPopupMenu.class, child)) {
	            return true;
	        }
	        if(parent.isAbstractFormBuilder())
	        	return true;
	        
	        if (parent.isA(JMenu.class) || parent.isA(JPopupMenu.class)) {
	            if ( Cacher.isAssignableFrom(JMenu.class, child))
	                return true;
	            if ( Cacher.isAssignableFrom(JMenuItem.class, child))
	                return true;
	            if ( Cacher.isAssignableFrom(JCheckBoxMenuItem.class, child))
	                return true;
	            if ( Cacher.isAssignableFrom(JRadioButtonMenuItem.class, child))
	                return true;
	            if ( Cacher.isAssignableFrom(JSeparator.class, child))
	                return true;
	            return false;
	        }
	        if ( Cacher.isAssignableFrom(JMenuBar.class, child)) {
	            if (parent.isJFrame() || parent.isJDialog() || parent.isJApplet() || parent.isJInternalFrame())
	                return true;
	            return false;
	        }
	        for (int i = 0; i < swingContainers.size(); i++) {
	            if (parent.isSubclassOf((Class) swingContainers.elementAt(i)))
	                return true;
	        }
	        if(parent.isContentPane())
	        	return true;
	        if(parent.isA(Panel.class))
	            return true;
	        //if this is a standard java swing or awt component, return false
	        if(parent.getClassName().startsWith("java"))
	            return false;
	        //otherwise, assume it's a custom comp
	        if(parent.isSubclassOf(Container.class))
	            return true;
	        return false;
	    } else if (parent.isCWT()) {
	        return com.cloudgarden.jigloo.typewise.TypewiseManager.canAddTo(
	                parent, child);
	    }
	    return true;
	}

	public static String getOrderableClass(String swtClass) {
	    return (String) orderableMap.get(swtClass);
	}

	public static String getNonOrderableClass(String orderableClass) {
	    return (String) nonOrderableMap.get(orderableClass);
	}

	public static Object get(String name) {
		return swingMap.get(name);
	}

	public static Iterator getIterator() {
		return swingMap.keySet().iterator();
	}

}
