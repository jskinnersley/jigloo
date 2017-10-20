/*
 * Created on Apr 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.interfaces;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IJavaCodeManager {
	public abstract void removeField(String name);
	public abstract void removeConstructor(String[] paramTypes);
	public abstract void addField(String name, String type, boolean isStatic, FormComponent fc);
	public abstract void addImport(String name);
	public abstract MethodDeclaration addMethod(
		String name,
		String body,
		String returnType,
		String[] paramTypes,
		String[] paramNames,
		int flags,
		String comment);
	public abstract boolean renameField(
		Shell shell,
		String oldName,
		String newName);
	public abstract boolean hasMethod(String string, String[] strings);
	public abstract String getPackageName();
	public abstract void renameMethod(String oldHandler, String eventHandler, String[] paramTypes);
}