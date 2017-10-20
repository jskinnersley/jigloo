package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TextFormPropertyDescriptor extends FormPropertyDescriptor {

	private IPropertySource propSrc;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public TextFormPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
	}
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FormTextCellEditor(parent, comp, (String)getId());
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}



}
