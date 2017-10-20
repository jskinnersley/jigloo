/*
 * Created on Jan 10, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import org.eclipse.jface.action.Action;

import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormLookAndFeelAction extends Action {
	String laf;
	FormEditor editor;

	public FormLookAndFeelAction(FormEditor editor, String laf, String label) {
		this.laf = laf;
		this.editor = editor;
		setText(label);
	}

	public void run() {
		editor.setLookAndFeel(laf);
		editor.setLookAndFeelNow();
		if (editor.updatesJavaCode())
			editor.getJavaCodeParser().updateLookAndFeel(laf);
		editor.setDirtyAndUpdate();
	}

}
