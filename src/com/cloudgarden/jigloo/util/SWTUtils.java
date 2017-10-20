package com.cloudgarden.jigloo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;

public class SWTUtils {

	static Rectangle ZERO_BOUNDS = new Rectangle(0, 0, 0, 0);

	static Rectangle ZERO_BOUNDS2 = new Rectangle(-10, -10, 10, 10);

	public static Rectangle getBounds(TabFolder tf, int item) {
	        if (jiglooPlugin.isWindows()) {
	//            org.eclipse.swt.internal.win32.RECT itemRect = new org.eclipse.swt.internal.win32.RECT();
	//            org.eclipse.swt.internal.win32.OS.SendMessage(tf.handle,
	//                    org.eclipse.swt.internal.win32.OS.TCM_GETITEMRECT, item,
	//                    itemRect);
	//            Rectangle r = new Rectangle(itemRect.left, itemRect.top,
	//                    itemRect.right - itemRect.left, itemRect.bottom
	//                            - itemRect.top);
	//            return r;
	        }
	        return ZERO_BOUNDS;
	    }

	public static Rectangle getExpandItemBounds(Object expandItem) {
		try {
			Field fx = expandItem.getClass().getDeclaredField("x");
			fx.setAccessible(true);
			int ex = fx.getInt(expandItem);
			Field fy = expandItem.getClass().getDeclaredField("y");
			fy.setAccessible(true);
			int ey = fy.getInt(expandItem);
			Field fw = expandItem.getClass().getDeclaredField("width");
			fw.setAccessible(true);
			int ew = fw.getInt(expandItem);
			Field fh = expandItem.getClass().getDeclaredField("height");
			fh.setAccessible(true);
			int eh = fh.getInt(expandItem)+23; //should be ExpandItem.getHeaderHeight ?
			return new Rectangle(ex, ey, ew, eh);
		} catch(Throwable t) {
			t.printStackTrace();
			return ZERO_BOUNDS;
		}
	}

	public static Rectangle getItemBounds(Item item) {
		try {
			Class cls = item.getClass();
			Method m = cls.getMethod("getBounds", null);
			if(m != null) {
				Object val = m.invoke(item, null);
				if(val instanceof Rectangle)
					return (Rectangle) val;
			}
		} catch(Throwable t) {		}
		return ZERO_BOUNDS;
	}

	public static Rectangle getSWTBounds(FormComponent comp, boolean zeroIfHidden) {
		Object control = comp.getControl();
	    Control ctrl;
	    Rectangle b;
	    String ctrlClass = control != null ? control.getClass().getName() : "";
	    if(control == null || (control instanceof Widget && ((Widget)control).isDisposed())) {
	        b = new Rectangle(-10, -10, 10, 10);
	    } else if (control instanceof TabItem) {
	        TabFolder tabFldr = ((TabItem) control).getParent();
	        TabItem[] sel = tabFldr.getSelection();
	        if (sel == null || sel.length == 0 || !sel[0].equals(control))
	            return ZERO_BOUNDS;
	        b = tabFldr.getBounds();
	        b.x = 0;
	        b.y = 0;
	    } else if (control instanceof CTabItem) {
	        CTabFolder ctabFldr = ((CTabItem) control).getParent();
	        CTabItem csel = ctabFldr.getSelection();
	        if (csel == null || !csel.equals(control)) {
	            return ZERO_BOUNDS;
	        }
	        b = ctabFldr.getBounds();
	        b.x = 0;
	        b.y = 0;
	    } else if (control instanceof TreeItem) {
	        b = ((TreeItem) control).getBounds();
	    } else if (ctrlClass.equals("org.eclipse.swt.widgets.ExpandItem")) {
	        b = getExpandItemBounds(control);
	    } else if (control instanceof CoolItem) {
	        b = ((CoolItem) control).getBounds();
	    } else if (control instanceof ToolItem) {
	        b = ((ToolItem) control).getBounds();
	    } else if (control instanceof TableItem) {
	        b = ((TableItem) control).getBounds(0);
	    } else if (control instanceof TableTreeItem) {
	        b = ((TableTreeItem) control).getBounds(0);
	    } else if (control instanceof TableColumn) {
	        TableColumn tc = (TableColumn) control;
	        Table tab = tc.getParent();
	        int x = 0;
	        TableColumn[] cols = tab.getColumns();
	        for (int i = 0; i < cols.length; i++) {
	            if (cols[i].equals(tc))
	                break;
	            x += cols[i].getWidth();
	        }
	        int w = ((TableColumn) control).getWidth();
	        b = new Rectangle(x, 0, w, tab.getHeaderHeight());
	    } else if (control.getClass().getName().equals("org.eclipse.swt.widgets.TreeColumn")) {
	    	try {
	    		TreeColumn tc = (TreeColumn) control;
	    		Tree tab = tc.getParent();
	    		int x = 0;
	    		TreeColumn[] cols = tab.getColumns();
	    		for (int i = 0; i < cols.length; i++) {
	    			if (cols[i].equals(tc))
	    				break;
	    			x += cols[i].getWidth();
	    		}
	    		int w = ((TreeColumn) control).getWidth();
	    		b = new Rectangle(x, 0, w, tab.getHeaderHeight());
	    	} catch(Throwable t) {
	    		b = ZERO_BOUNDS2;
	    	}
	    } else if (control instanceof Control) {
	        ctrl = (Control) control;
	        if (ctrl.isDisposed() || (zeroIfHidden && !ctrl.isVisible()))
	            return ZERO_BOUNDS;
	        Control cpar = ctrl.getParent();
	        if (comp.isRoot() && cpar != null) {
	            b = cpar.getBounds();
	            b.x = 0;
	            b.y = 0;
	            //b = new Rectangle(0,0,b.width, b.height);
	        } else {
	            b = ctrl.getBounds();
	            //b = new Rectangle(b.x, b.y, b.width, b.height);
	
	            if (comp.getParent() != null && !isItem(comp.getParent())) {
	                //System.out.println("GET BOUNDS "+this+", "+getParent());
	                Object pwid = comp.getParent().getControl();
	                while (cpar != null && !cpar.equals(pwid)) {
	                    Rectangle pb = cpar.getBounds();
	                    //System.out.println("GET BOUNDS "+this+", "+b+", "+pb+", "+cpar);
	                    b.x += pb.x;
	                    b.y += pb.y;
	                    cpar = cpar.getParent();
	                }
	            }
	
	        }
	    } else if (control instanceof Item) {
	    	return getItemBounds((Item)control);
	    } else {
	        //System.out.println("GET BOUNDS UNDEFINED " + this +", " + control);
	        b = ZERO_BOUNDS2;
	    }
	    return b;
	}

	public static boolean isItem(FormComponent comp) {
		Object control = comp.getControl();
        if(control == null)
        	return false;
        
        return comp.isSubclassOf(Item.class);
	}

	public static boolean usesSetControlCall(FormComponent comp) {
		Object control = comp.getControl();
        if(control == null)
        	return false;
    	if (control instanceof CTabItem)
            return true;
        if (control instanceof TabItem)
            return true;
        if (control instanceof CoolItem)
            return true;
        if (control instanceof ToolItem)
            return true;
        String clsName = control.getClass().getName();
        if(clsName.equals("org.eclipse.swt.widgets.ExpandItem"))
        	return true;
        return false;
	}

}
