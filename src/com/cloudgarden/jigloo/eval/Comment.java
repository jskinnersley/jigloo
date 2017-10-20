/*
 * Created on May 21, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

public class Comment {

	public int startPosition;
	public int length;
	public String code;

	public Comment(int start, String code) {
		this.code = code;
		startPosition = start;
		length = code.length();
	}

	public void setSourceRange(int start, int length) {
		this.startPosition = start;
		this.length = length;
	}

	public int getEndPosition() {
		return startPosition + length;
	}

	public boolean encloses(int pos) {
		return pos >= startPosition && pos < getEndPosition();
	}

	public boolean isBetween(int start, int end) {
		return start <= startPosition && end >= getEndPosition();
	}

}
