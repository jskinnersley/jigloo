package com.cloudgarden.jigloo.layoutHandler;

import java.util.Vector;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.IDEUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.EditValueDialog;
import com.cloudgarden.jigloo.frames.GridEdgeMarker;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

public class MigLayoutHandler {

    public static final double CONSTRAINT_GROW = -2;
    public static final double CONSTRAINT_SHRINK = -3;
    public static final double CONSTRAINT_FILL = -4;

	public static boolean handlesLayout(FormComponent sel) {
		if(sel == null)
			return false;
        return "Mig".equals(sel.getLayoutType());
    }

	public static void setBounds(FormComponent fc, Rectangle bounds, boolean parentChanged) {
		FormComponent par = fc.getParent();
    	if(par.gridCoordsChanged() || parentChanged) {
    		CC cc = getConstraint(fc);
    		int gx = cc.getCellX();
    		int gy = cc.getCellY();
    		int gw = cc.getSpanX();
    		int gh = cc.getSpanY();
    		int gwOld = gw;
    		int ghOld = gh;
    		if(gw < 1)
    			gw = 1;
    		if(gh < 1)
    			gh = 1;
    		gx = 2*gx+1;
    		gy = 2*gy+1;
    		gw = 2*gw-1;
    		gh = 2*gh-1;
    		if(parentChanged) {
    			gx = par.gridValues[0];
    			gy = par.gridValues[1];
    			gw = 1;
    			gh = 1;
    		} else {
    			gx = gx + par.gridValues[4];
    			gy =gy + par.gridValues[5];
    			if (par.gridValues[2] != fc.GRIDBAG_UNDEFINED) {
    				gw = gw + par.gridValues[6];
    				gh = gh + par.gridValues[7];
    			}
    		}
    		gx = (gx-1)/2;
    		gy = (gy-1)/2;
    		gw = (gw+1)/2;
    		gh = (gh+1)/2;
    		cc.setCellX(gx);
    		cc.setCellY(gy);
    		cc.setSpanX(gw);
    		cc.setSpanY(gh);
    		if(gw > gwOld)
    			cc.growX();
    		if(gh > ghOld)
    			cc.growY();
    		String str = IDEUtil.getConstraintString(cc, false);
            LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
    		ldw.setObject(str);
    		fc.executeSetLayoutDataWrapper(ldw);
    	} else {
    		fc.getEditor().realignWindowFrame(fc, true);
    	}
	}

	public static CC getConstraint(FormComponent fc) {
		String str = "";
		Object data = fc.getLayoutDataWrapper().getLayoutData();
		if(data instanceof String)
			str = (String) data;
		if(str == null)
			str = "";
		try {
			return ConstraintParser.parseComponentConstraint(str);
		} catch(Throwable t) {
			return new CC();
		}
	}

	public static Object[] getGridDimensions(FormComponent fc) {
		int yoff = 0;
		int xoff = 0;
		if(fc.getControl() instanceof Composite) {
			Rectangle cb = ((Composite)fc.getControl()).getClientArea();
			if(cb != null) {
				xoff = cb.x;
				yoff = cb.y;
			}
		}
		
    	java.awt.Point orig = new java.awt.Point(xoff, yoff);
    	Object obj = fc.getObject(false);
    	if(fc.usesContentPane())
    		obj = fc.getContentPane();
    	int[][] css = IDEUtil.getColumnSizes(obj);
        int[] w;
        if(css != null)
        	w =  css[1];
        else
        	w = new int[0];
        int[][] rss = IDEUtil.getRowSizes(obj);
        int[] h;
        if(rss != null)
        	h = rss[1];
        else
        	h = new int[0];
        return new Object[] {w, h, orig };
	}

	public static String getRowOrColDesc(FormComponent fc, int index, boolean isCol) {
		index--; //index is 1-based
		if(isCol) {
			int[][] css = IDEUtil.getColumnSizes(fc.getObject(false));
			if(css != null)
				return "[Col "+index+"] "+css[1][index];
//			return "[Col "+index+"] "+css[1][1+index*2];
			return "[Col "+index+"] undefined size";
		} else {
			int[][] rss = IDEUtil.getRowSizes(fc.getObject(false));
			if(rss != null)
				return "[Row "+index+"] "+rss[1][index];
//			return "[Row "+index+"] "+rss[1][1+index*2];
			return "[Row "+index+"] undefined size";
		}
	}

	public static void resizeColumnOrRow(FormComponent fc, boolean isCol, int colIndex, double size) {
		try {
			LayoutWrapper lw = fc.getLayoutWrapper();
			String prop = isCol ? "columnConstraints" : "rowConstraints";
			String con = (String) lw.getPropertyValue(prop);
			Vector parts = getGapsAndDefs(fc, isCol);
//			colIndex = 2*colIndex+1;
			while(parts.size() < colIndex+1) {
				parts.add("");
			}
			if(size == CONSTRAINT_GROW)
				parts.set(colIndex, "grow");
			else if(size == CONSTRAINT_FILL)
				parts.set(colIndex, "fill");
			else if(size == CONSTRAINT_SHRINK)
				parts.set(colIndex, "shrink");
			else
				parts.set(colIndex, ""+size);
			con = combineGapsAndDefs(parts);
			lw.setPropertyValue(prop, con);
			fc.setLayoutWrapper(lw, true, false);
			lw.setChanged(true);
			lw.setSet(true);
			lw.repairConstructorInCode();
			fc.getEditor().setDirtyAndUpdate();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	protected static void addMenuItem(Menu menu, String name, final Runnable action, String imgName) {
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(name);
		if(imgName != null)
		    item.setImage(ImageManager.getImage(imgName+".gif"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				action.run();
			}
		});
	}

	public static Menu createEdgeMenu(final GridEdgeMarker edge) {
		Menu menu = new Menu(edge.getControl());
		final boolean isCol = edge.isColumn();
		final String rowCol = isCol ? "Column" : "Row";
		final FormComponent fc = edge.getFormComponent();
		
		addMenuItem(menu, "Insert "+rowCol+" before", new Runnable() {
			public void run() {
				fc.insertOrDeleteColumnOrRow(edge.getSelectedSection(), 1, isCol, false);
			}
		}, "insert"+rowCol+"Before");
		
		addMenuItem(menu, "Insert "+rowCol+" after", new Runnable() {
			public void run() {
				//note "+2" here because gaps are included as columns
				fc.insertOrDeleteColumnOrRow(edge.getSelectedSection()+2, 1, isCol, false);
			}
		}, "insert"+rowCol+"After");

		new MenuItem(menu, SWT.SEPARATOR);
		
		addMenuItem(menu, "Grow", new Runnable() {
			public void run() {
				resizeColumnOrRow(fc, isCol, edge.getSelectedSection(), CONSTRAINT_GROW);
			}
		}, null);
		
		addMenuItem(menu, "Fill", new Runnable() {
			public void run() {
				resizeColumnOrRow(fc, isCol, edge.getSelectedSection(), CONSTRAINT_FILL);
			}
		}, null);
		
		addMenuItem(menu, "Shrink", new Runnable() {
			public void run() {
				resizeColumnOrRow(fc, isCol, edge.getSelectedSection(), CONSTRAINT_SHRINK);
			}
		}, null);
		
		new MenuItem(menu, SWT.SEPARATOR);
		addMenuItem(menu, "Delete "+rowCol, new Runnable() {
			public void run() {
				fc.insertOrDeleteColumnOrRow(edge.getSelectedSection(), 2, isCol, false);
			}
		}, "delete");
		new MenuItem(menu, SWT.SEPARATOR);
		addMenuItem(menu, "Edit "+rowCol+" constraint", new Runnable() {
			public void run() {
				try {
//					int sel = edge.getSelectedSection()*2+1;
					int sel = edge.getSelectedSection();
					int index = edge.getSelectedSection();
					LayoutWrapper lw = fc.getLayoutWrapper();
					String prop = isCol ? "columnConstraints" : "rowConstraints";
					Vector defs = getGapsAndDefs(fc, isCol);
					while(defs.size() < sel+1) {
						defs.add("");
					}
					String con = (String)defs.get(sel);
					EditValueDialog evd = new EditValueDialog(fc.getShell(), SWT.DIALOG_TRIM);
					evd.initText(""+con, "Edit constraint for "+rowCol+" "+index);
					evd.open();
					String val = evd.getValue();
					if(val == null)
						return;
					defs.set(sel, val);
					con = combineGapsAndDefs(defs);
					lw.setPropertyValue(prop, con);
					fc.setLayoutWrapper(lw, true, false);
					lw.setChanged(true);
					lw.setSet(true);
					lw.repairConstructorInCode();
					fc.getEditor().setDirtyAndUpdate();
				} catch(Throwable t) {
					t.printStackTrace();
				}
			}
		}, null);

		return menu;
	}

	private static String combineGapsAndDefs(Vector parts) {
		String con = "";
		for (int i = 0; i < parts.size(); i++) {
			if(i % 2 == 0 ) {
				con += parts.get(i);
			} else {
				con += "["+parts.get(i)+"]";
			}
		}
		return con;
	}
	
	private static Vector getGapsAndDefs(FormComponent fc, boolean isCol) {
		LayoutWrapper lw = fc.getLayoutWrapper();
		String prop = isCol ? "columnConstraints" : "rowConstraints";
		String constraint = (String) lw.getPropertyValue(prop);
		Vector parts = new Vector();
		int pos;
		pos = constraint.indexOf("[");
		if(pos == 0)
			parts.add(""); //leading gap
		else if(pos > 0)
			parts.add(constraint.substring(0, pos));
		while(pos >= 0) {
			//add col/row defn
			int pos2 = constraint.indexOf("]", pos);
			String def = constraint.substring(pos+1, pos2);
			parts.add(def);
			pos = constraint.indexOf("[", pos2);
			//add gap defn
			if(pos >= 0) {
				def = constraint.substring(pos2+1, pos);
				parts.add(def);
			} else {
				parts.add("");
			}
		}
		return parts;
	}

	/**
	 * index is 0-based row/col index
	 * @param fc
	 * @param index
	 * @param mode
	 * @param isCol
	 */
	public static void insertOrDeleteColumnOrRow(FormComponent fc, int index, int mode, boolean isCol) {
		LayoutWrapper lw = fc.getLayoutWrapper();
		String prop = isCol ? "columnConstraints" : "rowConstraints";
		Vector defs = getGapsAndDefs(fc, isCol);
		if(mode == 1) {
			while(defs.size() < index*2) {
				defs.add("");
			}
			defs.add(2*index, "");
			defs.add(2*index, "");
		} else if(mode == 2) {
			if(defs.size() >= 2*index+2) {
				defs.remove(2*index);
				defs.remove(2*index);
			}
		}
		String con = combineGapsAndDefs(defs);
		lw.setPropertyValue(prop, con);
		fc.setLayoutWrapper(lw, true, false);
		lw.setChanged(true);
		lw.setSet(true);
		lw.repairInCode(false);
	}

	/**
	 * Should return value as in displayed grid - if gaps are displayed then need to
	 * change value (2* + 1)
	 * @param fc
	 * @param col
	 * @return
	 */
	public static int getGridValue(FormComponent fc, boolean col) {
		if(col)
			return 2*getConstraint(fc).getCellX() + 1;
		return 2*getConstraint(fc).getCellY() + 1;
	}

	public static int getGridCellValue(FormComponent fc, boolean col) {
		if(col)
			return 2*getConstraint(fc).getSpanX() - 1;
		return 2*getConstraint(fc).getSpanY() - 1;
	}

	public static void setGridValue(FormComponent fc, boolean col, int val) {
		val = (val - 1)/2;
		CC cc = getConstraint(fc);
		if(col)
			cc.setCellX(val);
		else
			cc.setCellY(val);
		String str = IDEUtil.getConstraintString(cc, false);
        LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
		ldw.setObject(str);
	}

	public static void setGridCellValue(FormComponent fc, boolean col, int val) {
		val = (val + 1)/2;
		CC cc = getConstraint(fc);
		if(col)
			cc.setSpanX(val);
		else
			cc.setSpanY(val);
		String str = IDEUtil.getConstraintString(cc, false);
        LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
		ldw.setObject(str);
	}

	public static void setXYFromEvent(FormComponent par, FormComponent child, MouseEvent evt) {
		setGridValue(child, true, par.gridValues[0]);
		setGridValue(child, false, par.gridValues[1]);
//		int ey = evt.y;
//		if(par.isRoot())
//			ey -= par.getEditor().getMenuBarHeight();
//		int[] xy = par.getGridBagCoords(evt.x, ey, true, true);
//		setGridValue(child, true, xy[0]);
//		setGridValue(child, false, xy[1]);
	}

}
