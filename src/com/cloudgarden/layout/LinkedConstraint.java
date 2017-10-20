/*
 * Created on Oct 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.layout;

import java.awt.Component;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LinkedConstraint {

    public static final int BEGINNING = 1;
    public static final int END = 2;
    public static final int RELATED = 3;
    public static final int UNRELATED = 4;
    public static final int INDENTED = 5;
    public static final int EXPANDABLE = 6;
    
    private Component topComponent;
    private Component leftComponent;
    private int topAlign;
    private int leftAlign;
    
    public LinkedConstraint(Component topComponent, int topAlign,
            Component leftComponent, int leftAlign) {
        this.topComponent = topComponent;
        this.topAlign = topAlign;
        this.leftComponent = leftComponent;
        this.leftAlign = leftAlign;
    }
    
}
