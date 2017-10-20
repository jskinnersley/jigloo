/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoableLayoutDataAction extends UndoableAction {

	private FormComponent comp;
	Object oldValue, newValue;
	private boolean wasSet;

	public UndoableLayoutDataAction(FormComponent comp, Object oldValue, Object newValue, boolean wasSet) {
		this.comp = comp;
		this.wasSet = wasSet;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
//	public boolean equals(Object comp) {
//		if (!(comp instanceof UndoableLayoutDataAction))
//			return false;
//		UndoableLayoutDataAction udla = (UndoableLayoutDataAction) comp;
//		return udla.oldValue.equals(oldValue) && udla.newValue.equals(newValue);
//	}

	public void undo() {
	    comp.setEditor(editor);
		LayoutDataWrapper ov = (LayoutDataWrapper) oldValue;
		ov = ov.getCopy(null);
		comp.setLayoutDataWrapper(ov);
		if (ov.isSet())
			comp.setPropertyValueInternal("layoutData", ov, true, false);
		else
			comp.resetPropertyValue("layoutData");
		if (comp.isSwing())
			comp.repairParentConnectionNode();
		else
			ov.repairInCode();
	}

	public void redo() {
	    comp.setEditor(editor);
		final LayoutDataWrapper nv = ((LayoutDataWrapper) newValue).getCopy(null);
		comp.setLayoutDataWrapper(nv);
		if (nv.isSet())
			comp.setPropertyValueInternal("layoutData", nv, true, false);
		else
			comp.resetPropertyValue("layoutData");
		if (comp.isSwing())
			comp.repairParentConnectionNode();
		else {
			comp.updateInCode();
			nv.repairInCode();
		}
	}

	public String getDisplayName() {
		return "\"Set Layout Constraint " + newValue + "\"";
	}
	public Vector getFormComponents(boolean redo) {
		return null;
	}
	public FormComponent getFormComponent(boolean redo) {
	    return comp;
	}
}
