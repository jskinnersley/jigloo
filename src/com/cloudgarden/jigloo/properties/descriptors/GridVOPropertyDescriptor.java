package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.GenericDialog;
import com.cloudgarden.jigloo.openswing.GridControlEditor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class GridVOPropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public GridVOPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new GridVOCellEditor(parent, comp);
		return editor;
	}

	class GridVOCellEditor extends DialogCellEditor {

		private FormComponent comp;

		protected GridVOCellEditor(
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
			    GenericDialog ad =
			        new GenericDialog(
			                cellEditorWindow.getShell()) {
			        public Composite createComposite(Shell dialogShell) {
			            return new GridControlEditor(dialogShell, comp);
			        }
			    };
				ad.open();
				deactivate();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
