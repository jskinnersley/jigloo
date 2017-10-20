/*
 * Created on Sep 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.interfaces;


/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface ISWTDisposableWrapper {

	public String getSWTDeclaration(String fieldName, IJavaCodeManager jcm);
	public String getResourceCode();

}
