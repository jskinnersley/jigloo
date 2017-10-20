/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import org.eclipse.jface.action.Action;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class UndoableAction extends Action {

	protected FormEditor editor;

	public void setEditor(FormEditor editor) {
		this.editor = editor;
	}

	public void undo() {}

	public void redo() {}
	
	public String getDisplayName() {
		return "";
	}

	public FormEditor getFormEditor() {
		return editor;
	}
	
	public abstract FormComponent getFormComponent(boolean redo);
	public abstract Vector getFormComponents(boolean redo);

}
