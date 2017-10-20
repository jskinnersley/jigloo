package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.TabListDialog;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FocusTraversalPolicyPropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public FocusTraversalPolicyPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp,
		IPropertySource propSrc) {
		super(id, displayName, comp, propSrc);
		this.id = id;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FormComponentArrayCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class FormComponentArrayCellEditor extends DialogCellEditor {

		private FormComponent comp;

		protected FormComponentArrayCellEditor(
			Composite parent,
			FormComponent comp) {
			super(parent);
			this.comp = comp;
		}
		
		private TabListDialog ad;
		
        public void deactivate() {
			if(comp != null && ad == null) {
				comp.getEditor().showTabNumbers(comp, false);
			}
            super.deactivate();
        }

		protected void doSetValue(Object value) {
			if(comp != null) {
				comp.selectInCode((String)getId());
				comp.initTabList();
			}
			super.doSetValue(value);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			try {
				ad =
					new TabListDialog(
						cellEditorWindow.getShell(), comp);
				Point pt = getControl().toDisplay(0, 0);
				ad.open(pt.x, pt.y-100);
				ad = null;
				deactivate();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
