/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoableGroupAction extends UndoableAction {

	Vector actions;
	Vector fcs;

	public UndoableGroupAction() {
		actions = new Vector();
	}
	public UndoableGroupAction(Vector actions) {
		this.actions = actions;
	}

	public void setEditor(FormEditor editor) {
		this.editor = editor;
//		if (actions != null) {
//			for (int i = 0; i < actions.size(); i++)
//				 ((UndoableAction) actions.elementAt(i)).setEditor(editor);
//		}
	}

	public Vector getFormComponents(boolean redo) {
		//if (fcs != null)
		//return fcs;
		if (actions == null || actions.size() == 0)
			return null;
		Object o = actions.elementAt(0);
		if (o instanceof UndoableAction) {
			fcs = new Vector();
			UndoableAction uea = (UndoableAction) o;
			for (int i = actions.size() - 1; i >= 0; i--) {
				o = actions.elementAt(i);
				FormComponent fc = ((UndoableAction) o).getFormComponent(redo);
				if (fc != null) {
					fcs.add(fc);
				}
			}
		}
		//		if (o instanceof UndoableEditAction) {
		//			fcs = new Vector();
		//			UndoableEditAction uea = (UndoableEditAction) o;
		//			editor = uea.getFormEditor();
		//			editor.clearSelection();
		//			for (int i = actions.size() - 1; i >= 0; i--) {
		//				o = actions.elementAt(i);
		//				FormComponent fc =
		//					((UndoableEditAction) o).getFormComponent(redo);
		//				if (fc != null) {
		//					fcs.add(fc);
		//				}
		//			}
		//		}
		return fcs;
	}

	public void undo() {
		if (editor != null)
			editor.clearSelection();
		for (int i = actions.size() - 1; i >= 0; i--) {
            UndoableAction ua = (UndoableAction) actions.elementAt(i);
            ua.setEditor(editor);
            ua.undo();
		}
		getFormComponents(false);
		if (editor != null) {
			editor.clearSelection();
			editor.setSelectedComponents(fcs);
		}
	}

	public void redo() {
	    try {
	        Vector sels = editor.getSelectedComponents();
	        sels = (Vector)sels.clone();
	        boolean setSels = false;
	        for (int i = 0; i < actions.size(); i++) {
	            UndoableAction ua = (UndoableAction) actions.elementAt(i);
	            ua.setEditor(editor);
	            ua.redo();
	            if(ua instanceof UndoableEditAction)
	                setSels = true;
	        }
	        if (editor != null) {
	            editor.setSelectedComponents(sels, !setSels);
	        }
	    } catch(Throwable t) {
	        jiglooPlugin.handleError(t);
	    }
	}

	public void addAction(UndoableAction action) {
		if (actions == null)
			actions = new Vector();
		if (action instanceof UndoableGroupAction) {
			UndoableGroupAction uga = (UndoableGroupAction) action;
			Vector acts = uga.getActions();
			for (int i = 0; i < acts.size(); i++)
				addAction((UndoableAction) acts.elementAt(i));
			return;
		}
		if (action == null || actions.contains(action))
			return;
		actions.add(action);
	}

	public Vector getActions() {
		return actions;
	}

	public int getActionCount() {
		if (actions == null)
			return 0;
		return actions.size();
	}

	public String getDisplayName() {
		if (actions == null || actions.size() == 0)
			return super.getDisplayName();
		return ((UndoableAction) actions.get(0)).getDisplayName();
	}

	public FormComponent getFormComponent(boolean redo) {
		return null;
	}

	public FormEditor getFormEditor() {
		return null;
	}

}
