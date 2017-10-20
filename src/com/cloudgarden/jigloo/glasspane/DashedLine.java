/*
 * Created on Jun 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.glasspane;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DashedLine extends Line {

    /**
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @param c
     */
    public DashedLine(int x, int y, int x2, int y2, Color c) {
        super(x, y, x2, y2, c);
    }
    
    public void draw(GC gc, Point origin) {
        int del = x2-x;
        if(y2-y > del)
            del = y2-y;
        gc.setForeground(color);
        int dx, dx2;
        int dy, dy2;
        int i=0;
        dx = dx2 = x;
        dy = dy2 = y;
        while( i< del) {
            if(x2 != x) {
                dx = x+(x2-x)*i/del;
                dx2 = x+(x2-x)*(i+3)/del;
            }
            if(y2 != y) {
                dy = y+(y2-y)*i/del;
                dy2 = y+(y2-y)*(i+3)/del;
            }
            gc.drawLine(origin.x + dx, origin.y + dy, origin.x + dx2, origin.y + dy2);
            i += 5;
        }
    }

}
