package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ColorPropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ColorPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ColorDialogCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class ColorDialogCellEditor extends DialogCellEditor {

		private FormComponent comp;

		protected ColorDialogCellEditor(Composite parent, FormComponent comp) {
			super(parent);
			this.comp = comp;
		}

		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			super.doSetValue(value);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			try {
				ColorDialog colDialog =
					new ColorDialog(cellEditorWindow.getShell());

				ColorWrapper cw = null;
				
				cw = (ColorWrapper) getValue();

				if (cw != null) {
					colDialog.setRGB(
						new RGB(cw.getRed(), cw.getGreen(), cw.getBlue()));
				}
				RGB rgb = colDialog.open();

				deactivate();
				if (rgb != null) {
					if (cw != null)
						cw.dispose();
					return new ColorWrapper(rgb.red, rgb.green, rgb.blue, comp);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
