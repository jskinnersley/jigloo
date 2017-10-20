package com.cloudgarden.jigloo.properties.descriptors;

import java.beans.PropertyEditor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.CustomPropertyCellEditor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CustomFormPropertyDescriptor extends FormPropertyDescriptor {

	private PropertyEditor propEd;
	
	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public CustomFormPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp, PropertyEditor propEd, IPropertySource propSrc) {
		super(id, displayName, comp, propSrc);
		this.propEd = propEd;
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new CustomPropertyCellEditor(parent, propSrc, (String)getId(), propEd);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}



}
