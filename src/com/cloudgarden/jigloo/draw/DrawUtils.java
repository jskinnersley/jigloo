package com.cloudgarden.jigloo.draw;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

public class DrawUtils {

	public static void drawGrid(GC gc, FormComponent toBeLayedOut, int xOffset, int yOffset) {
	    try {
	        int[] widths = null;
	        int[] heights = null;
	        java.awt.Point orig = null;
	        Rectangle b = null;
	        
	        if (toBeLayedOut.usesGridTypeLayout()) {
	            b = toBeLayedOut.getInsideBoundsRelativeToRoot();
	            Object[] obs = JiglooUtils.getGridOrFormDimensions(toBeLayedOut);
	            if(obs == null)
	                return;
	            widths = (int[]) obs[0];
	            heights = (int[]) obs[1];
	            orig = (java.awt.Point)obs[2];
	        }
	        
	        if(orig != null) {
	            gc.setForeground(ColorManager.getColor(100, 100, 200));
	            int w = orig.x;
	            gc.setLineStyle(SWT.LINE_DOT);
	            int bx = b.x + xOffset;
	            int by = b.y + yOffset;
	            
	            gc.drawLine(bx + w, by, bx + w, by + b.height);
	            for (int i = 0; i < widths.length; i++) {
	                w += widths[i];
	                gc.drawLine(bx + w, by, bx + w, by + b.height);
	            }
	            int h = orig.y;
	            gc.drawLine(bx, by + h, bx + b.width, by + h);
	            for (int j = 0; j < heights.length; j++) {
	                h += heights[j];
	                gc.drawLine(bx, by + h, bx + b.width, by + h);
	            }
	        }
	    } catch(Throwable t) {
	        jiglooPlugin.handleError(t);
	    }
	}

}
