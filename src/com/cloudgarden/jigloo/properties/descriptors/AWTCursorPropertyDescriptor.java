package com.cloudgarden.jigloo.properties.descriptors;

import java.awt.Cursor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.wrappers.AWTCursorWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AWTCursorPropertyDescriptor extends FormPropertyDescriptor {

	private static Cursor[] mnems;
	private static String[] names;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public AWTCursorPropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
		if (mnems == null) {
		    Field[] fields = Cursor.class.getFields();
		    Vector vals = new Vector();
		    for (int i = 0; i < fields.length; i++) {
		    	if(!fields[i].getName().equals("CUSTOM_CURSOR"))
		    		vals.add(fields[i]);
            }
			Collections.sort(vals, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Field) o1).getName().compareTo(((Field) o2).getName());
				}
			});
			mnems = new Cursor[vals.size()];
			names = new String[vals.size()];
			for (int i = 0; i < mnems.length; i++) {
			    Field f = (Field)vals.elementAt(i);
			    Integer val = null;
			    try {
                    val = (Integer)f.get(null);
                    mnems[i] = Cursor.getPredefinedCursor(val.intValue());
                } catch (Throwable e) {
                	System.err.println("Error getting pd cursor "+val);
                }
			    names[i] = f.getName();
			}
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			CellEditor editor = new LocaleCellEditor(parent, comp);
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class LocaleCellEditor extends FormComboBoxCellEditor {

		private FormComponent comp;

		public LocaleCellEditor(Composite parent, FormComponent comp) {
			super(parent, names, SWT.NULL);
			this.comp = comp;
		}

		protected Object doGetValue() {
			Object o = super.doGetValue();
			Cursor loc = mnems[((Integer) o).intValue()];
			return loc;
		}

		protected void doSetValue(Object value) {
			if(comp != null)
				comp.selectInCode((String)getId());
			Object val = new Integer(0);
			AWTCursorWrapper lw = (AWTCursorWrapper) value;
			for (int i = 0; i < mnems.length; i++) {
				if (mnems[i] != null && mnems[i].equals(lw.getValue(null))) {
					val = new Integer(i);
				}
			}
			super.doSetValue(val);
		}

	}

}
