package com.cloudgarden.jigloo.properties.descriptors;

import javax.swing.border.Border;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.properties.sources.BorderPropertySource;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BorderPropertyDescriptor extends FormPropertyDescriptor {

	private String[] choices;
	private Object[] objs;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public BorderPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp,
		Object[] objs,
		String[] names,
		IPropertySource propSrc) {
		super(id, displayName, comp, propSrc);
		this.choices = names;
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

		private BorderPropertySource bps;
		private int valIndex = -1;
		private FormComponent fc;

		public ChoiceCellEditor(
			Composite parent,
			FormComponent comp,
			String[] items) {
			super(parent, items, SWT.NULL);
			fc = comp;
		}
		
		protected Object doGetValue() {
		    if(CGPropertySheetPage.rightButtonDown && bps != null)
				return bps;
			Object o = super.doGetValue();
			int index = ((Integer) o).intValue();
			if(index == 0 && fc != null) {
			    fc.resetPropertyValue("border");
			}
			if (objs != null)
				return objs[index];
			return choices[index];
		}

		protected void doSetValue(Object value) {
			if(comp != null)
			    comp.selectInCode((String)getId());
			Integer val = new Integer(0);
			Border border = null;
			if (value instanceof BorderPropertySource) {
				bps = (BorderPropertySource) value;
				border = bps.getValue();
			}
			if (border != null) {
				value = JiglooUtils.getShortClassName(border.getClass());
			}
			if (value != null) {
				for (int i = 0; i < choices.length; i++) {
					if (value.equals(choices[i])
						|| (objs != null && value.equals(objs[i]))) {
						valIndex = i;
						val = new Integer(i);
					}
				}
			}
			super.doSetValue(val);
		}

	}

}
