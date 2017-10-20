package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

public class GridEdgeManager {
	
    private GridEdgeMarker hMarker = null;
    private GridEdgeMarker vMarker = null;
    private Marker greenMarker = null;

	public void redrawMarkers() {
        if(hMarker != null) {
            hMarker.redraw();
            vMarker.redraw();
        }
	}
	
    public void showGridEdgeMarkers(FormComponent fc) {
        if(fc != null && fc.needsLayoutGrid()) {
            if(hMarker == null) {
                hMarker = new GridEdgeMarker(fc.getEditor(), 1);
                vMarker = new GridEdgeMarker(fc.getEditor(), 2);
            }
            hMarker.show(fc);
            hMarker.moveAbove();
            hMarker.redraw();
            vMarker.show(fc);
            vMarker.moveAbove();
            vMarker.redraw();
        } else {
            if(hMarker != null)
                hMarker.hide();
            if(vMarker != null)
                vMarker.hide();
        }
    }
    
    public void clearGreenMarker() {
    	if(greenMarker !=null)
    		greenMarker.hide();
    }
        
    public void disposeMarkers() {
        if(hMarker != null)
            hMarker.dispose();
        hMarker = null;
        
        if(vMarker != null)
            vMarker.dispose();
        vMarker = null;
        
        if(greenMarker != null)
        	greenMarker.dispose();
        greenMarker = null;
        
    }

	private void drawGreenMarker(FormComponent toBeLayedOut) {
		if(greenMarker == null) {
			greenMarker = new Marker(toBeLayedOut.getEditor());
			greenMarker.setBGColor(ColorManager.getColor(0, 200, 0));
			greenMarker.setFGColor(ColorManager.getColor(0, 255, 0));
		}
	    
	    Rectangle r = getSelectedRectangle(toBeLayedOut);
	    if(r != null) {
	        FormEditor ed = toBeLayedOut.getEditor();
	        r.x+= ed.BORDER_X;
	        r.y+= ed.BORDER_Y;
	        if(r.height > 10 && r.width > 10) {
	            r.x+=3;
	            r.y+=3;
	            r.width -= 5;
	            r.height -= 5;
	        }
	        greenMarker.setBounds(r);
	        greenMarker.show();
	    } else {
	        greenMarker.hide();
		}
	}

    private Rectangle getSelectedRectangle(FormComponent toBeLayedOut) {
    	if (!toBeLayedOut.usesGridTypeLayout())
    		return null;
    	
        int[] widths = null;
        int[] heights = null;
        java.awt.Point orig = null;
        Rectangle b = null;

        b = toBeLayedOut.getInsideBoundsRelativeToRoot();
        Object[] obs = JiglooUtils.getGridOrFormDimensions(toBeLayedOut);
        if(obs == null)
        	return null;
        widths = (int[]) obs[0];
        heights = (int[]) obs[1];
        orig = (java.awt.Point)obs[2];

        int gridx = toBeLayedOut.gridValues[0];
        int gridy = toBeLayedOut.gridValues[1];
        int gridw = toBeLayedOut.gridValues[2];
        int gridh = toBeLayedOut.gridValues[3];
        
        int w = orig.x;
        int x0 = 0, y0 = 0, w0 = -1, h0 = -1;
        for (int i = 0; i < widths.length; i++) {
        	w += widths[i];
        	if (gridx == i) {
        		x0 = b.x + w - widths[i];
        		w0 = widths[i];
        	} else if(i > gridx && i < gridx+gridw) {
        		w0 += widths[i];
        	}
        }
        int h = orig.y;
        for (int j = 0; j < heights.length; j++) {
        	h += heights[j];
        	if (gridy == j) {
        		y0 = b.y + h - heights[j];
        		h0 = heights[j];
        	} else if(j > gridy && j < gridy+gridh) {
        		h0 += heights[j];
        	}
        }
        return new Rectangle(x0, y0, w0, h0);
    }

	public void drawGreenMarker(boolean drawMarker, FormComponent toBeLayedOut) {
        if(toBeLayedOut != null) {
        	if(drawMarker) {
        		drawGreenMarker(toBeLayedOut);
        	} else {
        		clearGreenMarker();
        	}
        } else {
    		clearGreenMarker();
        }
	}
    

}
