package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.AnchorDialog;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AnchorPropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public AnchorPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new AnchorDialogCellEditor(parent, comp);
		return editor;
	}

	class AnchorDialogCellEditor extends DialogCellEditor {

		private FormComponent comp;

		protected AnchorDialogCellEditor(
			Composite parent,
			FormComponent comp) {
			super(parent);
			this.comp = comp;
		}
		
		protected void doSetValue(Object value) {
			if(comp != null)
				comp.selectInCode((String)getId());
			super.doSetValue(value);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			try {
				AnchorDialog ad =
					new AnchorDialog(
						cellEditorWindow.getShell(),
						SWT.APPLICATION_MODAL);
				ad.setFormEditor(comp.getEditor());
				Point pt = getControl().toDisplay(0, 0);
				ad.open(pt.x, pt.y-100);
				deactivate();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
