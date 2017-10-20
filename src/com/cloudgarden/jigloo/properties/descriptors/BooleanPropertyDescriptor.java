package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.outline.FormOutlineLabelProvider;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;

public class BooleanPropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public BooleanPropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
		setLabelProvider(new FormOutlineLabelProvider());
	}

	public BooleanPropertyDescriptor(Object id, String displayName, FormComponent comp, IFormPropertySource propSrc) {
		super(id, displayName, comp, propSrc);
		setLabelProvider(new FormOutlineLabelProvider());
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			CellEditor editor = new BooleanCheckboxCellEditor(parent);
			if (getValidator() != null)
				editor.setValidator(getValidator());
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class BooleanCheckboxCellEditor extends CheckboxCellEditor {

		public BooleanCheckboxCellEditor(Composite parent) {
			super(parent);
		}

		//	protected Control createControl(Composite parent) {
		//		Control ctrl = super.createControl(parent);
		//		if (ctrl instanceof CCombo) {
		//			CCombo cco = (CCombo) ctrl;
		//		}
		//		return ctrl;
		//	}

		private long lastClick = -1;
		private long lastActivate = -1;

		public void activate() {
			if(comp != null)
			comp.selectInCode((String)getId());
			if (jiglooPlugin.getDefault().useDCForBooleans()) {
				long now = System.currentTimeMillis();
				if (now - lastClick < 50 && now - lastActivate > 500) {
					lastActivate = now;
					super.activate();
				}
				lastClick = now;
			} else {
			    if(!CGPropertySheetPage.rightButtonDown)
			    	super.activate();
			}
		}

	}
}
