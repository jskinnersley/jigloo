/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.lang.reflect.Field;
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
public class FormStyleAction extends Action {
	private FormEditor editor;
	private Field styleField;
	private Field oldField = null;
	private boolean addStyle;

	public FormStyleAction(
		FormEditor editor,
		Field styleField,
		boolean addStyle,
		boolean selected) {
		this.editor = editor;
		this.styleField = styleField;
		this.addStyle = addStyle;
		setText(styleField.getName());
	}

	public FormStyleAction(
		FormEditor editor,
		Field styleField,
		Field oldField) {
		this.editor = editor;
		this.styleField = styleField;
		this.oldField = oldField;
		String name = styleField.getName();
		if (styleField.equals(oldField))
			name = "*" + name;
		setText(name);
	}

	public void run() {

		Vector sels = editor.getSelectedComponents();
		for (int i = 0; i < sels.size(); i++) {
			try {
				FormComponent comp = (FormComponent) sels.elementAt(i);
				int oldStyle = comp.getStyle();
				int newStyle = oldStyle;
				if (oldField != null) {
					newStyle &= ~oldField.getInt(null);
					if (!styleField.equals(oldField))
					newStyle |= styleField.getInt(null);
				} else {
					newStyle |= styleField.getInt(null);
				}
				UndoableStyleAction usa =
					new UndoableStyleAction(comp, oldStyle, newStyle, true);
				editor.executeUndoableAction(usa);

//				Object wid = comp.getControl();
//				int style = comp.getStyle();
//				if (oldField != null) {
//					style &= ~oldField.getInt(null);
//					if (!styleField.equals(oldField))
//						style |= styleField.getInt(null);
//				} else {
//					style |= styleField.getInt(null);
//				}
//				comp.setStyle(style);
//				comp.getEditor().setDirty(true);
//				//comp.rebuild(false);
//				comp.rebuildParent(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
