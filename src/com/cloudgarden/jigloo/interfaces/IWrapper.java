/*
 * Created on Feb 20, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.interfaces;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IWrapper {
	public abstract void generateXML(
		Element prop,
		Document document,
		String indent1,
		String indent2);
	public abstract IWrapper getCopy(FormComponent comp);
	public abstract Object getValue(Object obj);
	public abstract String getJavaConstructor(IJavaCodeManager jcm);
	public abstract String toString();
}