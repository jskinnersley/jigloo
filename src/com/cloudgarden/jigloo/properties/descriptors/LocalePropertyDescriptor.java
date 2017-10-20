package com.cloudgarden.jigloo.properties.descriptors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocalePropertyDescriptor extends FormPropertyDescriptor {

	private static Locale[] locales;
	private static String[] names;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public LocalePropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
		if (locales == null) {
			locales = Locale.getAvailableLocales();
			names = new String[locales.length];
			Arrays.sort(locales, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Locale) o1).getDisplayName().compareTo(((Locale) o2).getDisplayName());
				}
			});
			for (int i = 0; i < locales.length; i++) {
				Locale loc = locales[i];
				names[i] = loc.getDisplayName();
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
			Locale loc = locales[((Integer) o).intValue()];
			return loc;
		}

		protected void doSetValue(Object value) {
			Object val = new Integer(0);
			IWrapper lw = (IWrapper) value;
			for (int i = 0; i < locales.length; i++) {
				if (lw.getValue(null).equals(locales[i])) {
					val = new Integer(i);
				}
			}
			super.doSetValue(val);
		}

	}

}
