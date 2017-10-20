package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.util.ArrayUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.StringArrayWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringArrayPropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public StringArrayPropertyDescriptor(Object id, String displayName, FormComponent comp, IPropertySource propSrc) {
		super(id, displayName, comp, propSrc);
		this.id = id;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new StringArrayCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class StringArrayCellEditor extends FormTextCellEditor {

		private Object sps;
		private Point pt;
		private Object id;
		private String oldVal;

		public StringArrayCellEditor(Composite parent, FormComponent comp) {
			super(parent, comp, (String)getId());
		}

		protected Object doGetValue() {
			if (!(sps instanceof StringArrayWrapper))
				return sps;
			String o = (String) super.doGetValue();
			if (o.equals(oldVal))
				return sps;
			try {
				String[] val = ArrayUtils.stringToStringArray(o);
				sps = new StringArrayWrapper(val, comp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sps;
		}
		
		protected void doSetValue(Object value) {
		    try {
		        if(comp != null)
		            comp.selectInCode((String)getId());
		        if (!(value instanceof StringArrayWrapper)) {
		            if(value instanceof String) {
		                value = new StringArrayWrapper(JiglooUtils.split(",", (String)value), comp);
		            } else {
			            if (value != null)
			                System.err.println("StringArrayCellEditor.doSetValue: " + value + ", " + value.getClass());
		            }
		        }
		        sps = value;
		        oldVal = sps.toString();
		        super.doSetValue(JiglooUtils.replace(sps.toString(), "\"", ""));
		    } catch(Throwable t) {
		        System.err.println("Error in doSetValue id="+id+": "+value+" for "+this);
		    }
		}

	}

}
