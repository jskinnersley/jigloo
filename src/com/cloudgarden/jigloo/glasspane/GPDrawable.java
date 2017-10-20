/*
 * Created on Nov 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.glasspane;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;


public interface GPDrawable {
    
    public void draw(GC gc, Point origin);
    public void setColor(Color c);
    
}