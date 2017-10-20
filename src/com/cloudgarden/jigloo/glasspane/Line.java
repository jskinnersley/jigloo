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
public class Line implements GPDrawable {
    
    protected int x, y, x2, y2;
    protected Color color;
    
    public Line(int x, int y, int x2, int y2, Color c) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.color = c;
    }
    
    public void draw(GC gc, Point origin) {
        gc.setForeground(color);
        gc.drawLine(origin.x + x, origin.y + y, origin.x + x2, origin.y + y2);
    }

    public void setColor(Color c) {
        this.color = c;
    }

}
