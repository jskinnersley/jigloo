package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;

public class NumberPropertyDescriptor extends FormPropertyDescriptor {

	Class numberClass;

	public NumberPropertyDescriptor(
		Object id,
		String displayName,
		Class number,
		FormComponent comp) {
		super(id, displayName, comp);
		numberClass = number;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			CellEditor editor = new NumberCellEditor(parent, numberClass);
			if (getValidator() != null)
				editor.setValidator(getValidator());
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class NumberCellEditor extends TextCellEditor {

		Class numberClass;

		public NumberCellEditor(Composite parent, Class numberClass) {
			super(parent, SWT.NULL);
			this.numberClass = numberClass;
		}

		protected Object doGetValue() {
			Object o = super.doGetValue();
			if (o == null)
				return null;
			try {
				if (numberClass.equals(Double.class))
					return new Double((String) o);
				if (numberClass.equals(Long.class))
					return new Long((String) o);
				if (numberClass.equals(Short.class))
					return new Short((String) o);
				if (numberClass.equals(Float.class))
					return new Float((String) o);
				return new Integer((String) o);
			} catch (NumberFormatException e) {
				o = "0";
				if (numberClass.equals(Double.class))
					return new Double((String) o);
				if (numberClass.equals(Long.class))
					return new Long((String) o);
				if (numberClass.equals(Short.class))
					return new Short((String) o);
				if (numberClass.equals(Float.class))
					return new Float((String) o);
				return new Integer((String) o);
			}
		}

		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			super.doSetValue(value.toString());
		}
	}
}
