/*
 * Created on Mar 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;


/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Pattern {

	private String[] parts;
	
	public Pattern(String[] parts) {
		this.parts = parts;
	}
	
	public boolean matches(String str) {
		int pos = 0;
		String[] matches = new String[parts.length+1];
		for (int i = 0; i < parts.length; i++) {
			pos = str.indexOf(parts[i], pos);
			if(pos < 0)
				return false;
			pos += parts[i].length();
		}
		return true;
	}
}
