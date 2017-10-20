package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.util.ArrayUtils;
import com.cloudgarden.jigloo.wrappers.DoubleArrayWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DoubleArrayPropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public DoubleArrayPropertyDescriptor(
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
		CellEditor editor = new DoubleArrayCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class DoubleArrayCellEditor extends FormTextCellEditor {

		private DoubleArrayWrapper sps;
		private Point pt;
		private Object id;
		private String oldVal;

		public DoubleArrayCellEditor(Composite parent, FormComponent comp) {
			super(parent, comp, (String)getId());
		}

		protected Object doGetValue() {
			String o = (String) super.doGetValue();
			if (o.equals(oldVal) && sps != null)
				return sps;
			try {
				double[] val = ArrayUtils.stringToDoubleArray(o);
				sps = new DoubleArrayWrapper(val, comp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sps;
		}

		protected void doSetValue(Object value) {
			if(value instanceof DoubleArrayWrapper) {
				sps = (DoubleArrayWrapper) value;
				oldVal = sps.toString();
			} else {
				oldVal = (String)value;
				sps = null;
			}
			super.doSetValue(oldVal);
		}

	}

}
