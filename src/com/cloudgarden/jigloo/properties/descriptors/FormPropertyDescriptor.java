package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FormPropertyDescriptor extends PropertyDescriptor {

	protected FormComponent comp;
	protected IPropertySource propSrc;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public FormPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName);
		this.comp = comp;
	}

	public FormPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp,
		IPropertySource propSrc) {
		this(id, displayName, comp);
		this.propSrc = propSrc;
	}

	public void setPropertySource(IPropertySource propSrc) {
		this.propSrc = propSrc;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#getDisplayName()
	 */
	public String getDisplayName() {
		//System.out.println("getDisplayName "+getId()+", "+comp+", "+propSrc);
		if (propSrc != null) {
			if (propSrc.isPropertySet(getId()))
				return super.getDisplayName() + "*";
			//return "*"+super.getDisplayName();
		} else {
			if (comp.isPropertySet(getId()))
				return super.getDisplayName() + "*";
			//return "*"+super.getDisplayName();
		}
		return super.getDisplayName();
	}
	
	/**
	 * Intended to be overridden to detect changes made by the CellEditor
	 * @param newValue
	 */
	protected void valueChanged(Object newValue) {
	}


	/*
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new TextCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
	*/
}
