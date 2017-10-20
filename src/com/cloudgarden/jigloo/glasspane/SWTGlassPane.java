/*
 * Created on Nov 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.glasspane;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SWTGlassPane {

    private Composite comp;
    private PaintListener pl;
    private boolean opaque = false;
    private Drawer drawer;
    private GC currentGC;
    private Point origin;
    private Color color;
    
    public void setDrawer(Drawer d) {
        drawer = d;
    }
    
    /**
     * @param c1
     */
    public SWTGlassPane() {
        pl = new PaintListener() {
            public void paintControl(PaintEvent e) {
                final PaintEvent fe = e;
//                if(!opaque)
//                    return;
                if(fe.widget instanceof Control) {
                    currentGC = fe.gc;
                    origin = ((Control)fe.widget).toDisplay(0, 0);
                    Point compOrig = comp.toDisplay(0, 0);
                    origin.x = compOrig.x - origin.x;
                    origin.y = compOrig.y - origin.y;
                    try {
                        drawer.draw(getGC(), origin.x, origin.y);
                    } catch(Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        };
    }

    private void setColor(Color c) {
        currentGC.setForeground(c);
    }
    
    public GC getGC() {
    	return currentGC;
    }
    
    public Point getOrigin() {
    	return origin;
    }
    
    private void drawLine(int x, int y, int x2, int y2) {
        currentGC.drawLine(origin.x + x, origin.y + y, origin.x + x2, origin.y + y2);
    }
    
    private void drawDottedLine(int x, int y, int x2, int y2) {
        currentGC.drawLine(origin.x + x, origin.y + y, origin.x + x2, origin.y + y2);
    }
    
    private void drawDashedLine(int x, int y, int x2, int y2) {
        currentGC.drawLine(origin.x + x, origin.y + y, origin.x + x2, origin.y + y2);
    }
    
    public void setControl(Composite c1) {
        if(comp != null)
            removePaintListener(comp, pl);
        comp = c1;
        addPaintListener(comp, pl);
    }
    
    private void addPaintListener(Control con, PaintListener pl) {
    	if(con == null || con.isDisposed())
    		return;
        con.addPaintListener(pl);
        if(con instanceof Composite) {
            Control[] children = ((Composite)con).getChildren();
            for (int i = 0; i < children.length; i++) {
                addPaintListener(children[i], pl);
            }
        }
    }
    
    private void removePaintListener(Control con, PaintListener pl) {
    	if(con == null || con.isDisposed())
    		return;
        con.removePaintListener(pl);
        if(con instanceof Composite) {
            Control[] children = ((Composite)con).getChildren();
            for (int i = 0; i < children.length; i++) {
                removePaintListener(children[i], pl);
            }
        }
    }
    
    /**
     * 
     */
    public void redraw() {
        redraw(comp);
    }

    private void redraw(Control con) {
        if(con.isDisposed()) {
            return;
        }
        con.redraw();
        if(con instanceof Composite) {
            Control[] children = ((Composite)con).getChildren();
            for (int i = 0; i < children.length; i++) {
                redraw(children[i]);
            }
        }
    }
    

    public void setOpaque(boolean opaque) {
    	if(this.opaque == opaque)
    		return;
        this.opaque = opaque;
        redraw();
    }
    
}
