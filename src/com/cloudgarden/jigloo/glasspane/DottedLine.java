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
public class DottedLine extends Line {

    public DottedLine(int x, int y, int x2, int y2, Color c) {
        super(x, y, x2, y2, c);
    }
    
    public void draw(GC gc, Point origin) {
        int del = x2-x;
        if(y2-y > del)
            del = y2-y;
        gc.setForeground(color);
        int dx = x, dy = y;
        for(int i = 0; i< del; i+=2) {
            if(x2 != x)
                dx = x+(x2-x)*i/del;
            if(y2 != y)
                dy = y+(y2-y)*i/del;
            gc.drawPoint(origin.x + dx, origin.y + dy);
        }
    }


}
