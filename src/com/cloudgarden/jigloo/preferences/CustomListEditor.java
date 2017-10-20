/*
 * Created on Jun 6, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.preferences;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.cloudgarden.jigloo.util.JiglooUtils;

class CustomListEditor extends ListEditor {

	private boolean textInput = false;

	public CustomListEditor() {
		super();
	}

	public CustomListEditor(String name, String labelText, Composite parent, boolean textInput) {
		super(name, labelText, parent);
		this.textInput = textInput;
	}

	protected String createList(String[] items) {
		String ret = "";
		for (int i = 0; i < items.length; i++) {
			if (i != 0)
				ret += ";";
			ret += items[i];
		}
		return ret;
	}

	protected String getNewInputObject() {
		if (textInput) {
			InputDialog id =
				new InputDialog(
					getShell(),
					"Input class pattern",
					"Type in a pattern for a class name (* is a wildcard)",
					"",
					null);
			id.open();
			return id.getValue();
		} else {
			SelectionDialog dialog = null;
			try {
				dialog =
					JavaUI.createTypeDialog(
						getShell(),
						new ProgressMonitorDialog(getShell()),
						SearchEngine.createWorkspaceScope(),
						IJavaElementSearchConstants.CONSIDER_CLASSES,
						false);
			} catch (JavaModelException e) {
				e.printStackTrace();
				return null;
			}
			dialog.setTitle("Select new class");
			dialog.setMessage("Select new class");
			if (dialog.open() == IDialogConstants.CANCEL_ID)
				return null;
			Object[] types = dialog.getResult();
			dialog.close();
			if (types == null || types.length == 0)
				return null;
			return ((IType) types[0]).getFullyQualifiedName();
		}
	}

	protected String[] parseString(String stringList) {
		return JiglooUtils.split(";", stringList);
	}
}