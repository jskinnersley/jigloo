package com.cloudgarden.jigloo.properties.descriptors;

import java.lang.reflect.Field;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FieldPropertyDescriptor extends FormPropertyDescriptor {

	private FieldWrapper fieldWrapper;
	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public FieldPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp,
		FieldWrapper fieldWrapper) {
		super(id, displayName, comp);
		this.fieldWrapper = fieldWrapper;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			CellEditor editor = new FieldCellEditor(parent, comp, fieldWrapper);
			if (getValidator() != null)
				editor.setValidator(getValidator());
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getNonQualFieldName(String name) {
		int pos = name.lastIndexOf(".");
		if (pos < 0)
			return name;
		return name.substring(pos + 1);
	}

	public static String getSemiQualFieldName(String name) {
		int pos = name.lastIndexOf(".");
		if (pos < 0)
			return name;
		pos = name.substring(0, pos).lastIndexOf(".");
		if (pos < 0)
			return name;
		return name.substring(pos + 1);
	}

	public static String getFullClassName(String className) {
		if (className.startsWith("java")
			|| className.startsWith("com")
			|| className.startsWith("org"))
			return className;
		if (className.startsWith("SWT")) {
			className = "org.eclipse.swt." + className;
		} else {
			className = "javax.swing." + className;
		}
		return className;
	}

	class FieldCellEditor extends FormComboBoxCellEditor { //CGComboBoxCellEditor {

		private FormComponent comp;

		private Integer[] keys;
		private FieldWrapper fieldWrapper;
		private String[] fields;
		private Object[] fieldValues;

		public FieldCellEditor(
			Composite parent,
			FormComponent comp,
			FieldWrapper fieldWrapper) {
			super(parent, fieldWrapper.getShortFields(), SWT.NULL);
			//			super(parent, fieldWrapper.getFields(), SWT.NULL);
			fields = fieldWrapper.getFields();
			this.comp = comp;
			this.fieldWrapper = fieldWrapper;
			if (keys == null) {
				keys = new Integer[fields.length];
				fieldValues = new Object[fields.length];
				for (int i = 0; i < keys.length; i++) {
					keys[i] = new Integer(i);
					int pos = fields[i].lastIndexOf(".");
					if (pos < 0) {
						fieldValues[i] = new Integer(fields[i]);
					} else {
						String className = fields[i].substring(0, pos);
						String fieldName = fields[i].substring(pos + 1);
						className = getFullClassName(className);
						Field field = null;
						try {
							Class clazz = jiglooPlugin.loadClass(className);
							field = clazz.getField(fieldName);
							fieldValues[i] = field.get(null);
						} catch (Exception e) {
							System.err.println(
								"Error getting field "
									+ fieldName
									+ ", "
									+ field
									+ ", "
									+ e.toString());
							//e.printStackTrace();
						}
					}
					//static field assumed

				}
			}
		}

		Object setVal;
		FieldWrapper inFW;

		protected Object doGetValue() {
			Object o = super.doGetValue();
			//String val = doGetTextValue();
			//if(val.equals(setVal))
			//return inFW;
			//System.err.println("doGetVal " + o);
			//try {
				//fieldWrapper.setValue(new Integer(val));
				//fieldWrapper = new FieldWrapper(fieldWrapper);
				//return fieldWrapper;
			//} catch (Exception e) {}
			for (int i = 0; i < keys.length; i++) {
				if (o.equals(keys[i])) {
					Object newVal = fieldValues[i];
					//must return a new Object, otherwise eclipse doesn't think the value has changed
					fieldWrapper = new FieldWrapper(fieldWrapper);
					fieldWrapper.setValue(newVal);
					return fieldWrapper;
				}
			}
			return inFW;
		}

		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			inFW = (FieldWrapper) value;
			Object val = inFW.getValue();
			if (val == null)
				return;
			//System.err.println("doSetVal " + val+", "+this);
			for (int i = 0; i < keys.length; i++) {
				if (val.equals(fieldValues[i])) {
					super.doSetValue(keys[i]);
					return;
				}
			}
			super.doSetValue(keys[0]);
			//setVal = val.toString();
			//super.doSetTextValue(val.toString());
		}

	}
}
