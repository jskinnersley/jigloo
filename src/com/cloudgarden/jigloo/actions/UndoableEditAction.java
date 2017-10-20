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
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoableEditAction extends UndoableAction {

	FormComponent deletedComp;
	FormComponent deletedParent;
	FormComponent pastedComp;
	FormComponent pastedParent;
	int deletedPos, pastedPos;
	Rectangle deletedBounds, pastedBounds;

	public UndoableEditAction(
		FormComponent deletedComp,
		FormComponent deletedParent,
		int deletedPos,
		Rectangle deletedBounds,
		FormComponent pastedComp,
		FormComponent pastedParent,
		int pastedPos,
		Rectangle pastedBounds) {
		this.deletedComp = deletedComp;
		this.deletedParent = deletedParent;
		this.deletedPos = deletedPos;
		this.deletedBounds = deletedBounds;
		this.pastedComp = pastedComp;
		this.pastedParent = pastedParent;
		this.pastedPos = pastedPos;
		this.pastedBounds = pastedBounds;
		if (deletedComp != null)
			editor = deletedComp.getEditor();
		else if (pastedComp != null)
			editor = pastedComp.getEditor();
	}

	public void undo() {
		FormComponent dc1 = deletedComp;
		FormComponent pc1 = pastedComp;
		if (deletedComp == null) {
			editor.removeComponent(pastedComp, true);
		} else {
			if (pastedComp == null) {
				FormComponent dc = editor.pasteComponent(deletedParent, deletedComp, deletedPos, false);
				if (dc != null) {
					deletedComp = dc;
					dc.addToCode();
					//dc.repairParentConnectionInCode();
					if (deletedBounds != null)
						deletedComp.setBounds(deletedBounds, false);
				}
			} else {
				boolean parChanged =JiglooUtils.diffParents(deletedComp, pastedComp);
				pastedComp.moveToParent(deletedParent, deletedPos);
				pastedComp.repairParentConnectionInCode();
				if (parChanged && deletedBounds != null)
					pastedComp.setBounds(deletedBounds, false);
			}
		}

	}

	public void redo() {
		if (pastedPos == -1000000) {
			pastedPos = pastedComp.getIndexInParent() - 1;
		} else if (pastedPos == 1000000) {
			pastedPos = pastedComp.getIndexInParent() + 2;
		}
		FormComponent dc1 = deletedComp;
		FormComponent pc1 = pastedComp;
		if (deletedComp == null) {
			FormComponent pc = editor.pasteComponent(pastedParent, pastedComp, pastedPos, false);
			if (pc != null) {
				pastedComp = pc;
				pc.addToCode();
				pc.repairParentConnectionInCode(false);
				if (pastedBounds != null) {
				    pastedComp.setBounds(pastedBounds, false, false, false, true);
				}
			}
		} else {
			if (pastedComp == null) {
				FormComponent dcopy = deletedComp.getCopy(true, editor);
				editor.removeComponent(deletedComp, true);
				deletedComp = dcopy;
			} else {
				boolean parChanged = 
					(deletedParent == null && pastedParent != null)
					|| (deletedParent != null && pastedParent == null)
					|| (deletedParent != null && !deletedParent.equals(pastedParent));
				deletedComp.moveToParent(pastedParent, pastedPos);
				deletedComp.repairParentConnectionInCode();
				if (parChanged && pastedBounds != null) {
					boolean hardSet = false; //deletedComp.getParent().usesAbsoluteTypeLayout();
					deletedComp.setBounds(pastedBounds,hardSet, false, false, parChanged);
				}
			}
		}
	}

	public FormComponent getFormComponent(boolean redo) {
		if (redo)
			return pastedComp;
		else
			return deletedComp;
	}

	public String getDisplayName() {
		if (deletedComp == null) {
			return "\"Add " + pastedComp + "\"";
		} else {
			if (pastedComp == null) {
				return "\"Delete " + deletedComp + "\"";
			} else {
				return "\"Move " + pastedComp + "\"";
			}
		}
	}

	public Vector getFormComponents(boolean redo) {
		return null;
	}

	public FormComponent getPastedComponent() {
	    return pastedComp;
	}
}
