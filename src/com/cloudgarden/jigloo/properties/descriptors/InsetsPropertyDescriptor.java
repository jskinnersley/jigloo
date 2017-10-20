package com.cloudgarden.jigloo.properties.descriptors;

import java.awt.Insets;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InsetsPropertyDescriptor extends FormPropertyDescriptor {

	private Object id;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public InsetsPropertyDescriptor(
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
		CellEditor editor = new InsetsCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class InsetsCellEditor extends FormTextCellEditor {

		private InsetsPropertySource rps;
		private Insets rec;
		private Object id;

		public InsetsCellEditor(Composite parent, FormComponent comp) {
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
				int pos1 = o.indexOf(",");
				int pos2 = o.indexOf(",", pos1 + 1);
				int pos3 = o.indexOf(",", pos2 + 1);
				int x = getInt(o.substring(0, pos1));
				int y = getInt(o.substring(pos1 + 1, pos2));
				int w = getInt(o.substring(pos2 + 1, pos3));
				int h = getInt(o.substring(pos3 + 1));
				if (x == rec.top
					&& y == rec.left
					&& w == rec.bottom
					&& h == rec.right)
					return rps;
				Insets r = new Insets(x, y, w, h);
				rps = new InsetsPropertySource(r);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			return rps;
		}

		protected void doSetValue(Object value) {
			if(value instanceof String) {
				super.doSetValue(value);
			} else {
				rps = (InsetsPropertySource) value;
				rec = rps.getValue();
				super.doSetValue(rps.toString());
			}
		}

	}

}
