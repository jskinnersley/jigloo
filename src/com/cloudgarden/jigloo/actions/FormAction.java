/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import org.eclipse.jface.action.Action;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormAction extends Action {
	protected FormEditor editor;

	public FormAction(FormEditor editor) {
		this.editor = editor;
	}
	public FormEditor getActionEditor() {
		return editor;
	}

	public FormComponent getActionComponent() {
		return editor.getSelectedComponent();
	}
}
