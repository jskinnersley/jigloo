/*
 * Created on Apr 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ArrayHolder {

	private Object rawArray;
	private FormComponent[] fcArray;
	
	public ArrayHolder(Object rawArray, FormComponent[] fcArray) {
		this.rawArray = rawArray;
		this.fcArray = fcArray;
	}

	public Object getRawArray() {
		return rawArray;
	}
	
	public String toString() {
		if(fcArray == null)
			return "<empty>";
		String str = "|";
		for (int i = 0; i < fcArray.length; i++) {
			str += ((FormComponent)fcArray[i]).getNameInCode()+"|";
		}
		return str;
	}

	/**
	 * @param arrayIndex
	 * @param elem
	 */
	public void setFCArrayElement(int arrayIndex, FormComponent elem) {
		if(arrayIndex >= fcArray.length)
			return;
		fcArray[arrayIndex] = elem;
		((Object[])rawArray)[arrayIndex] = elem.getObject(true);
	}

	/**
	 * @return
	 */
	public FormComponent[] getFCArray() {
		return fcArray;
	}

	public int getLength() {
		return fcArray != null ? fcArray.length : 0;
	}
}
