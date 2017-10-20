package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.util.ArrayUtils;
import com.cloudgarden.jigloo.wrappers.IntegerArrayWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IntegerArrayPropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public IntegerArrayPropertyDescriptor(
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
		CellEditor editor = new IntegerArrayCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class IntegerArrayCellEditor extends FormTextCellEditor {

		private IntegerArrayWrapper sps;
		private Point pt;
		private Object id;
		private String oldVal;

		public IntegerArrayCellEditor(Composite parent, FormComponent comp) {
			super(parent, comp, (String)getId());
		}

		protected Object doGetValue() {
			String o = (String) super.doGetValue();
			if (o.equals(oldVal))
				return sps;
			try {
				int[] val = ArrayUtils.stringToIntArray(o);
				sps = new IntegerArrayWrapper(val, comp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sps;
		}

		protected void doSetValue(Object value) {
			if(comp != null)
				comp.selectInCode((String)getId());
			sps = (IntegerArrayWrapper) value;
			oldVal = sps.toString();
			super.doSetValue(sps.toString());
		}

	}

}
