package com.cloudgarden.jigloo.properties.descriptors;

import java.beans.PropertyEditor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ChoicePropertyDescriptor extends FormPropertyDescriptor {

	protected String[] choices;
	protected Object[] objs;
	protected PropertyEditor propEd;
	
	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ChoicePropertyDescriptor(
			Object id,
			String displayName,
			FormComponent comp,
			IPropertySource propSrc) {
			super(id, displayName, comp, propSrc);
		}

	public ChoicePropertyDescriptor(
			Object id,
			String displayName,
			FormComponent comp,
			String[] choices,
			IPropertySource propSrc) {
			super(id, displayName, comp, propSrc);
			this.choices = choices;
		}

	public ChoicePropertyDescriptor(
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
     * @param pname
     * @param dname
     * @param component
     * @param ped
     * @param component2
     */
    public ChoicePropertyDescriptor(		
            Object id,
    		String displayName,
    		FormComponent comp,
    		PropertyEditor propEd,
    		IPropertySource propSrc) {
    		super(id, displayName, comp, propSrc);
    		this.propEd = propEd;
    		choices = new String[] {};
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
		    try {
		        Object o = super.doGetValue();
		        int index = ((Integer) o).intValue();
		        Object returnVal = null;
		        if(index >= 0) {
		            if (objs != null)
		                returnVal = objs[index];
		            else
		                returnVal = choices[index];
		        }
		        ChoicePropertyDescriptor.this.valueChanged(returnVal);
		        return returnVal;
		    } catch(Throwable t) {
		        t.printStackTrace();
		        return null;
		    }
		}

		protected void doSetValue(Object value) {
		    try {
		        if(comp != null)
		            comp.selectInCode((String)getId());
		        
		        if(propEd != null) {
		            Object val = comp.queryProperty((String)getId());
		            propEd.setValue(val);
		            String[] tags = propEd.getTags();
		            setItems(tags);
		            choices = tags;
		        }
		        
		        Integer val = new Integer(0);;
		        if (value != null) {
		            for (int i = 0; i < choices.length; i++) {
		                Object obj = null;
		                if(objs != null 
		                        && objs.length > i
		                        && objs[i].getClass().equals(value.getClass())) {
		                    obj = objs[i];
		                }
		                if (choices[i].equals(value) || value.equals(obj))
		                    val = new Integer(i);
		            }
		        }
		        super.doSetValue(val);
		    } catch(Throwable t) {
		        t.printStackTrace();
		    }
		}

		protected void doSetFocus() {
			super.doSetFocus();
		}

	}

}
