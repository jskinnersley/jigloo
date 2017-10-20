package com.cloudgarden.jigloo.util;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;

public class ItemManager {

	public static boolean isNotChild(FormComponent par, FormComponent fc) {
        if (par.isSubclassOf(CTabFolder.class) && !fc.isSubclassOf(CTabItem.class))
            return true;
        if (par.isSubclassOf(TabFolder.class) && !fc.isSubclassOf(TabItem.class))
        	return true;
        if (par.isSubclassOf(CoolBar.class) && !fc.isSubclassOf(CoolItem.class))
        	return true;
        if (par.isSubclassOf(ToolBar.class) && !fc.isSubclassOf(ToolItem.class))
        	return true;
        if (par.isA("org.eclipse.swt.widgets.ExpandBar") && !fc.isA("org.eclipse.swt.widgets.ExpandItem"))
        	return true;

		return false;
	}

	public static boolean addAddAction(IMenuManager menuMgr, String cls, FormEditor editor) {
        if (cls.equals(CTabFolder.class.getName())) {
            menuMgr.add(editor.getFormAddAction(CTabItem.class, SWT.NULL,
                    "Add CTabItem"));
            return true;
        }
        if (cls.equals(TabFolder.class.getName())) {
            menuMgr.add(editor.getFormAddAction(TabItem.class, SWT.NULL,
                            "Add TabItem"));
            return true;
        }
        if (cls.equals(Menu.class.getName())
        		|| cls.equals(MenuItem.class.getName())) {
//            menuMgr.add(editor.getFormAddAction(MenuItem.class, SWT.CASCADE,
//                    "Add MenuItem - CASCADE"));
            menuMgr.add(editor.getFormAddAction(MenuItem.class, SWT.PUSH,
                    "Add MenuItem - PUSH"));
            menuMgr.add(editor.getFormAddAction(MenuItem.class, SWT.CHECK,
                    "Add MenuItem - CHECK"));
            menuMgr.add(editor.getFormAddAction(MenuItem.class, SWT.RADIO,
                    "Add MenuItem - RADIO"));
            menuMgr.add(editor.getFormAddAction(MenuItem.class, SWT.SEPARATOR,
                    "Add MenuItem - SEPARATOR"));
            return true;
        }
        
        if (cls.equals(MenuItem.class.getName())) {
            menuMgr
                    .add(editor.getFormAddAction(Menu.class, SWT.DROP_DOWN, "Add Menu"));
            return true;
        }
        if (cls.equals(ToolBar.class.getName())) {
            menuMgr.add(editor.getFormAddAction(ToolItem.class, SWT.NULL,
                    "Add ToolItem"));
            return true;
        }
        if (cls.equals(Tree.class.getName())
                || cls.equals(TreeItem.class.getName())) {
            menuMgr.add(editor.getFormAddAction(TreeItem.class, SWT.NULL,
                    "Add TreeItem"));
            return true;
        }
        if (cls.equals(TableTree.class.getName())
                || cls.equals(TableTreeItem.class.getName())) {
            menuMgr.add(editor.getFormAddAction(TableTreeItem.class, SWT.NULL,
                    "Add TableTreeItem"));
            return true;
        }
        if (cls.equals(CoolBar.class.getName())) {
            menuMgr.add(editor.getFormAddAction(CoolItem.class, SWT.NULL,
                    "Add CoolItem"));
            return true;
        }
        
        if (cls.equals("org.eclipse.swt.widgets.ExpandBar")) {
            menuMgr.add(editor.getFormAddAction(org.eclipse.swt.widgets.ExpandItem.class, SWT.NULL,
                    "Add ExpandItem"));
            return true;
        }
        
        if (cls.equals(CoolItem.class.getName())) {
            menuMgr.add(editor.getFormAddAction(ToolBar.class, SWT.NULL, "Add ToolBar"));
            return true;
        }
        
        if (cls.equals(Table.class.getName())) {
            menuMgr.add(editor.getFormAddAction(TableColumn.class, SWT.NULL,
                    "Add TableColumn"));
            menuMgr.add(editor.getFormAddAction(TableItem.class, SWT.NULL,
                    "Add TableItem"));
            return true;
        }
        return false;
	}

}
