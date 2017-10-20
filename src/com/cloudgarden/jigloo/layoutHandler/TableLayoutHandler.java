package com.cloudgarden.jigloo.layoutHandler;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import info.clearthought.layout.TableLayoutConstraints;

import java.awt.Container;
import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

public class TableLayoutHandler {

	public static void setGridValue(FormComponent fc, boolean col, int val) {
		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(fc.getParent());
		int nCols = tl.getNumColumn();
		int nRows = tl.getNumRow();
		TableLayoutConstraints con = tl.getConstraints(fc.getComponent());
		int gx = con.col1;
		int gy = con.row1;
		int gx2 = con.col2;
		int gy2 = con.row2;
		int hAlign = con.hAlign;
		int vAlign = con.vAlign;
		if(val < 0)
			val = 0;
		if(col) {
			int w = gx2 - gx;
			if(val > nCols-1-w)
				val = nCols-1-w;
			gx = val;
			con.col1 = gx;
			if(gx2 >= 0) {
				gx2 = gx + w;
				con.col2 = gx2;
			}
		} else {
			int h = gy2 - gy;
			if(val > nRows-1-h)
				val = nRows-1-h;
			gy = val;
			con.row1 = gy;
			if(gy2 >= 0) {
				gy2 = gy + h;
				con.row2 = gy2;
			}
		}
		
		String newCon = gx+", "+gy;
		if(gx2 != gx || gy2 != gy)
			newCon += ", "+gx2+", "+gy2;
		
		newCon += getAlignConstraint(hAlign, vAlign);
		
		fc.getLayoutDataWrapper().setObject(newCon);

	}

	public static void setGridCellValue(FormComponent fc,	boolean col, int val) {
		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(fc.getParent());
		TableLayoutConstraints con = tl.getConstraints(fc.getComponent());
		int gx = con.col1;
		int gy = con.row1;
		int gx2 = con.col2;
		int gy2 = con.row2;
		int hAlign = con.hAlign;
		int vAlign = con.vAlign;

		if(col) {
			gx2 = con.col2 = con.col1+val-1;
		} else {
			gy2 = con.row2 = con.row1+val-1;
		}
		
		String newCon = gx+", "+gy;
		if(gx2 != gx || gy2 != gy)
			newCon += ", "+gx2+", "+gy2;
		
		newCon += getAlignConstraint(hAlign, vAlign);
		
		fc.getLayoutDataWrapper().setObject(newCon);
	}
	
	private static String getAlignConstraint(int hAlign, int vAlign) {
		if(hAlign == TableLayoutConstants.FULL && vAlign == TableLayoutConstants.FULL)
			return "";
		String con = "";
		if(hAlign == TableLayoutConstants.CENTER)
			con += ", c";
		else if(hAlign == TableLayoutConstants.LEFT)
			con += ", l";
		else if(hAlign == TableLayoutConstants.RIGHT)
			con += ", r";
		else
			con += ", f";
		
		if(vAlign == TableLayoutConstants.CENTER)
			con += ", c";
		else if(vAlign == TableLayoutConstants.TOP)
			con += ", t";
		else if(vAlign == TableLayoutConstants.BOTTOM)
			con += ", b";
		else
			con += ", f";
		return con;
	}

	public static void setBounds(FormComponent fc, Rectangle bounds, boolean parentChanged) {
		LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
		FormComponent par = fc.getParent();
		TableLayout tl = (TableLayout)LayoutWrapper.getLayoutManager(par);
		TableLayoutConstraints con = tl.getConstraints(fc.getComponent());
		int gx, gy, gx2, gy2;
		int hAlign, vAlign;
		if(parentChanged || con == null) {
			gx = par.gridValues[0];
			gy = par.gridValues[1];
			gx2 = gx;
			gy2 = gy;
			hAlign = TableLayoutConstants.FULL;
			vAlign = TableLayoutConstants.FULL;
		} else {
			hAlign = con.hAlign;
			vAlign = con.vAlign;
			gx = con.col1 + par.gridValues[4];
			gy = con.row1 + par.gridValues[5];
			gx2 = con.col2 + par.gridValues[4];
			gy2 = con.row2 + par.gridValues[5];
			if (par.gridValues[2] != FormComponent.GRIDBAG_UNDEFINED) {
				gx2 += par.gridValues[6];
				gy2 += par.gridValues[7];
			}
		}
		int numRows = tl.getNumRow();
		int numCols = tl.getNumColumn();
		
		if(gx < 0)
			gx = 0;
		if(gx > numCols-1)
			gx = numCols-1;
		
		if(gx2 < gx)
			gx2 = gx;
		if(gx2 > numCols-1)
			gx2 = numCols-1;
		
		if(gy < 0)
			gy = 0;
		if(gy > numRows-1)
			gy = numRows-1;
		
		if(gy2 < gy)
			gy2 = gy;
		if(gy2 > numRows-1)
			gy2 = numRows-1;
		
		String newCon = gx+", "+gy;
		
		if(gx2 != gx || gy2 != gy)
			newCon += ", "+gx2+", "+gy2;
		
		newCon += getAlignConstraint(hAlign, vAlign);
		
		ldw.setObject(newCon);
		fc.executeSetLayoutDataWrapper(ldw);
	}

	/**
	 * @param lcon
	 * @return
	 */
	public static boolean isValidTableConstraint(Object lcon) {
		if(lcon instanceof String) {
		    String scon = (String)lcon;
		    if("INVALID".equals(lcon))
		        return false;
		    else {
		        int pos = 0;
		        int cnt = 0;
		        while(pos >= 0 && pos+1 < scon.length()) {
		            pos = scon.indexOf(",", pos+1);
		            if(pos >= 0)
		                cnt++;
		        }
		        if(cnt % 2 == 1)
			        return true;
		    }
		}
	    return false;
	}
	
	public static int[] getColumnWidths(TableLayout layout, FormComponent fc) {
		if(fc.getComponent() instanceof Container) {
			invoke(layout, "calculateSize", new Class[] {Container.class}, new Object[] {fc.getComponent()});
		}
		int[][] crSize = (int[][]) getField(layout, "crSize");
		if(crSize != null)
			return crSize[0];
		return null;
	}
	
	public static int[] getRowHeights(TableLayout layout, FormComponent fc) {
		if(fc.getComponent() instanceof Container) {
			invoke(layout, "calculateSize", new Class[] {Container.class}, new Object[] {fc.getComponent()});
		}
		int[][] crSize = (int[][]) getField(layout, "crSize");
		if(crSize != null)
			return crSize[1];
		return null;
	}
	
	public static Point getOrigin(TableLayout layout) {
		int[][] crOffset = (int[][]) getField(layout, "crOffset");
		return new Point(crOffset[0][0], crOffset[1][0]);
	}
	
	private static Object getField(Object obj, String fieldName) {
		try {
			Field f = obj.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Object invoke(Object obj, String method, Class[] paramTypes, Object[] args) {
		try {
			Method m = obj.getClass().getDeclaredMethod(method, paramTypes);
			m.setAccessible(true);
			return m.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
