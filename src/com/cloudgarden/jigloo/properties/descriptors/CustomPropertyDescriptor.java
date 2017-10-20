package com.cloudgarden.jigloo.properties.descriptors;

import java.lang.reflect.Constructor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;

public class CustomPropertyDescriptor extends FormPropertyDescriptor {

	private Class editorClass;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public CustomPropertyDescriptor(
		Object id,
		String displayName,
		Class editorClass,
		FormComponent comp) {
		super(id, displayName, comp);
		this.editorClass = editorClass;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			Constructor con =
				editorClass.getConstructor(new Class[] { Composite.class });
			CellEditor editor =
				(CellEditor) con.newInstance(new Object[] { parent });
			if (getValidator() != null)
				editor.setValidator(getValidator());
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}
