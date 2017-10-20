/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoableStyleAction extends UndoableAction {

	private FormComponent comp;
	int oldValue, newValue;
	private boolean wasSet;

	public UndoableStyleAction(FormComponent comp, int oldValue, int newValue, boolean wasSet) {
		this.comp = comp;
		this.wasSet = wasSet;
		this.oldValue = oldValue;
		this.newValue = newValue;
		//				System.err.println(
		//					"New UndoablePropertyAction, "
		//						+ id
		//						+ ", "
		//						+ oldValue
		//						+ ", "
		//						+ newValue
		//						+ ", "
		//						+ comp);
	}

	public void undo() {
		comp.setStyle(oldValue, true);
		comp.repairConstructorInCode();
	}

	public void redo() {
		comp.setStyle(newValue, true);
		comp.repairConstructorInCode();
	}

	public String getDisplayName() {
		return "\"Set Style\"";
	}
	public Vector getFormComponents(boolean redo) {
		return null;
	}
	public FormComponent getFormComponent(boolean redo) {
		return comp;
	}
	public FormEditor getFormEditor() {
		return comp.getEditor();
	}
}
