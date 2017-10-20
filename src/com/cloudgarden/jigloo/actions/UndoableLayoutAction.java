/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoableLayoutAction extends UndoableAction {

	private FormComponent comp;
	private Object oldValue, newValue;
	private boolean wasSet;
	Vector layoutDataWrappers;
	Vector bounds;

	public UndoableLayoutAction(
		FormComponent comp,
		Object oldValue,
		Object newValue,
		Vector layoutDataWrappers,
		Vector bounds,
		boolean wasSet) {
		this.comp = comp;
		this.wasSet = wasSet;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.layoutDataWrappers = layoutDataWrappers;
		this.bounds = bounds;
	}

	public void undo() {
	    comp.setEditor(editor);
		LayoutWrapper olw = (LayoutWrapper) oldValue;
		String lt = comp.getLayoutType();
		String olt = olw.getLayoutType();
		boolean changed = lt != olt;
		comp.setLayoutWrapper((LayoutWrapper) olw.getCopy(comp));
		for (int i = 0; i < comp.getChildCount(); i++) {
			FormComponent fc = comp.getChildAt(i);
			LayoutDataWrapper ldw =
				(LayoutDataWrapper) layoutDataWrappers.elementAt(i);
			fc.setLayoutDataWrapper(ldw.getCopy(fc));
			Rectangle b = (Rectangle) bounds.elementAt(i);
			fc.setPropertyValueDirect("bounds", b);
		}
		olw.repairInCode(changed);
	}

	public void redo() {
	    comp.setEditor(editor);
		LayoutWrapper nlw = (LayoutWrapper) newValue;
		LayoutWrapper oldVal = comp.getLayoutWrapper();
		boolean changed = nlw.isDifferentType(oldVal);
		String lwn = oldVal.getName();
		nlw = (LayoutWrapper) nlw.getCopy(comp);
		nlw.setName(lwn);
		comp.setLayoutWrapper(nlw);
		Throwable le = comp.getLastException();
		if(le != null) {
			comp.setLayoutWrapper(oldVal);
			return;
		}
		nlw.initGridBagDimensions(4, 4);
		nlw.repairInCode(changed);
	}

	public String getDisplayName() {
		return "\"Set Layout " + newValue + "\"";
	}
	public Vector getFormComponents(boolean redo) {
		return null;
	}
	public FormComponent getFormComponent(boolean redo) {
		return comp;
	}

}
