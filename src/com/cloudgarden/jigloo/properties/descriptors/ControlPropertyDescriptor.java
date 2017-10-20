package com.cloudgarden.jigloo.properties.descriptors;

import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ControlPropertyDescriptor extends FormPropertyDescriptor {

	private String[] choices;
	private Object[] objs;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ControlPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
		Vector sibs = comp.getChildren();
		Object[] objs = new Object[sibs.size()];
		String[] choices = new String[objs.length];
		int index = 1;
		objs[0] = "null";
		choices[0] = "null";
		for (int j = 0; j < sibs.size(); j++) {
			FormComponent fc = (FormComponent) sibs.elementAt(j);
			if (!fc.equals(comp)) {
				objs[index] = new FormComponentWrapper(fc);
				choices[index] = fc.getName();
				index++;
			}
		}
		this.choices = choices;
		this.objs = objs;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ChoiceCellEditor(parent, comp, choices);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class ChoiceCellEditor extends FormComboBoxCellEditor {

		public ChoiceCellEditor(
			Composite parent,
			FormComponent comp,
			String[] items) {
			super(parent, items, SWT.NULL);
		}

		protected Object doGetValue() {
			Object o = super.doGetValue();
			int index = ((Integer) o).intValue();
			if (objs != null)
				return objs[index];
			return choices[index];
		}

		protected void doSetValue(Object value) {
			if(comp != null)
				comp.selectInCode((String)getId());
			Integer val = new Integer(0);
			if (value != null) {
				for (int i = 0; i < choices.length; i++) {
					if (value.equals(choices[i])
						|| (objs != null && value.equals(objs[i])))
						val = new Integer(i);
				}
			}
			super.doSetValue(val);
		}

	}

}
