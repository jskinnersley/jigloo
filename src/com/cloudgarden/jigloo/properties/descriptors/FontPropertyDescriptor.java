package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.FontDialog;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.properties.MacSWTFontDialog;
import com.cloudgarden.jigloo.properties.SwingFontDialog;
import com.cloudgarden.jigloo.wrappers.FontWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FontPropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public FontPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp) {
		super(id, displayName, comp);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FontDialogCellEditor(parent, comp);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class FontDialogCellEditor extends DialogCellEditor {

		private FormComponent comp;
		/**
		 * Creates a new Font dialog cell editor parented under the given control.
		 * The cell editor value is <code>null</code> initially, and has no 
		 * validator.
		 *
		 * @param parent the parent control
		 */
		protected FontDialogCellEditor(Composite parent, FormComponent comp) {
			super(parent);
			this.comp = comp;
		}

		/**
		 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(Control)
		 */
		protected Object openDialogBox(Control cellEditorWindow) {
			try {
				FontWrapper oldWrapper = (FontWrapper) getValue();

				if (comp.isSwing()) {
					SwingFontDialog sfd =
						new SwingFontDialog(
							cellEditorWindow.getShell(),
							SWT.NULL);
					java.awt.Font oldFont = null;
					if(oldWrapper != null) {
					    oldFont = (java.awt.Font) oldWrapper.getFont(null);
					    sfd.setFont(oldFont);
					}
					sfd.open();
					deactivate();
					java.awt.Font newFont = sfd.getFont();
					if (newFont != null && !newFont.equals(oldFont)) {
						return new FontWrapper(newFont, comp);
					}
					return null;
				} else {
					Dialog ftDialog = null;
					if (jiglooPlugin.isMacOS()) {
						ftDialog =
							new MacSWTFontDialog(cellEditorWindow.getShell(), 0);
					} else {
						ftDialog = new FontDialog(cellEditorWindow.getShell());
					}

					FontData fd = null;
					if(oldWrapper != null)
					    fd = oldWrapper.getFontData();

					if (jiglooPlugin.isMacOS()) {
						((MacSWTFontDialog) ftDialog).setFontData(fd);
					} else {
						((FontDialog) ftDialog).setFontData(fd);
					}

					try {
						if (jiglooPlugin.isMacOS()) {
							((MacSWTFontDialog) ftDialog).open();
						} else {
							((FontDialog) ftDialog).open();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					FontData fData = null;
					if (jiglooPlugin.isMacOS()) {
						fData = ((MacSWTFontDialog) ftDialog).getFontData();
					} else {
						fData = ((FontDialog) ftDialog).getFontData();
					}
					deactivate();
					if (fData != null) {
						if (oldWrapper != null)
							return new FontWrapper(
								fData.getName(),
								fData.getHeight(),
								fData.getStyle(),
								fData,
								comp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			super.doSetValue(value);
		}

	}

}
