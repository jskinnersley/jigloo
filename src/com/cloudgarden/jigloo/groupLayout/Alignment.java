/*
 * Created on Feb 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayout;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Alignment {
    public static Alignment LEADING = new Alignment(GroupLayout.LEADING);
    public static Alignment TRAILING = new Alignment(GroupLayout.TRAILING);
    public static Alignment CENTER = new Alignment(GroupLayout.CENTER);
    public static Alignment BASELINE = new Alignment(GroupLayout.BASELINE);

    private int val;
    public Alignment(int val) {
        this.val = val;
    }
    public int getVal() {
        return val;
    }
    public String toString() {
    	if(val == GroupLayout.LEADING)
    		return "LEADING";
    	if(val == GroupLayout.TRAILING)
    		return "TRAILING";
    	if(val == GroupLayout.CENTER)
    		return "CENTER";
    	if(val == GroupLayout.BASELINE)
    		return "BASELINE";
    	return "UNKNOWN :"+val;
    }
}
