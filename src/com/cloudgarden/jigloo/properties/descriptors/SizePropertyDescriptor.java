package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.properties.sources.SizePropertySource;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SizePropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public SizePropertyDescriptor(
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
		CellEditor editor = new SizeCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class SizeCellEditor extends FormTextCellEditor {

		private SizePropertySource sps;
		private Point pt;
		private Object id;

		public SizeCellEditor(Composite parent, FormComponent comp) {
			super(parent, comp, (String)getId());
		}

		private int getInt(String str) {
			while (str.startsWith(" ") || str.startsWith("["))
				str = str.substring(1);
			while (str.endsWith(" ") || str.endsWith("]"))
				str = str.substring(0, str.length() - 1);
			return Integer.parseInt(str);
		}

		protected Object doGetValue() {
			String o = (String) super.doGetValue();
			try {
				Point p = parseString(o);
				if (p.x == pt.x && p.y == pt.y)
					return sps;
				//need to make a new SizePropSource otherwise change is not registered
				sps = new SizePropertySource(p, comp, (String) getId());
			} catch (Exception e) {
				//e.printStackTrace();
			}
			return sps;
		}

		private Point parseString(String val) {
			try {
				int pos = val.indexOf(",");
				int x = getInt(val.substring(0, pos));
				int y = getInt(val.substring(pos + 1));
				return new Point(x, y);
			} catch (Exception e) {
				return new Point(0, 0);
			}
		}
		
		protected void doSetValue(Object value) {
		    try {
		        if(comp != null)
		            comp.selectInCode((String)getId());
		        if(value instanceof SizePropertySource) {
		            sps = (SizePropertySource) value;
		            pt = sps.getValue();
		            super.doSetValue(sps.toString());
		        } else {
		            pt = parseString(value.toString());
		            super.doSetValue(value.toString());
		        }
		    } catch(Throwable t) {
		        System.err.println("Error in doSetValue id="+id+": "+value+" for "+this);
		    }
		}

	}

}
