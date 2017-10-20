/*
 * Created on Jul 9, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.classloader;

import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class ClassUpdateListener {

	public abstract void classUpdated(FormEditor editor, String projectName, String cls);

}
