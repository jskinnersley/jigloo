/*
 * Created on Feb 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayout;

public class ComponentPlacement {
    public static ComponentPlacement RELATED = new ComponentPlacement(LayoutStyle.RELATED);
    public static ComponentPlacement UNRELATED = new ComponentPlacement(LayoutStyle.UNRELATED);
    public static ComponentPlacement INDENT = new ComponentPlacement(LayoutStyle.INDENT);

    private int val;
    public ComponentPlacement(int val) {
        this.val = val;
    }
    public int getVal() {
        return val;
    }
}
