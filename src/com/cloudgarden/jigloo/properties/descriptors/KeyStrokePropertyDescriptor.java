package com.cloudgarden.jigloo.properties.descriptors;

import javax.swing.KeyStroke;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.wrappers.KeyStrokeWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KeyStrokePropertyDescriptor extends FormPropertyDescriptor {

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public KeyStrokePropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			CellEditor editor = new KeyStrokeCellEditor(parent, comp);
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class KeyStrokeCellEditor extends TextCellEditor implements KeyListener {

		private FormComponent comp;
		private String str;
		boolean shiftDown = false;
		boolean ctrlDown = false;
		boolean altDown = false;
		boolean metaDown = false;
		char key = 0;
		
		public void activate() {
			super.activate();
			jiglooPlugin.getDefault().setKeyListener(this);
		}

		public void deactivate() {
			super.deactivate();
			jiglooPlugin.getDefault().setKeyListener(null);
		}
		
		public boolean isPasteEnabled() {
			return false;
		}
		
		public KeyStrokeCellEditor(Composite parent, FormComponent comp) {
			super(parent, SWT.NULL);
			this.comp = comp;
		}

		public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			char tmpKey = (char)(e.keyCode & SWT.KEY_MASK);
			if(tmpKey == '\r') {
				KeyStrokeCellEditor.super.doSetValue(str);
				fireApplyEditorValue();
				return;
			}
			if((e.keyCode & SWT.COMMAND) != 0)
				metaDown = true;
			if((e.keyCode & SWT.ALT) != 0)
				altDown = true;
			if((e.keyCode & SWT.SHIFT) != 0)
				shiftDown = true;
			if((e.keyCode & SWT.CTRL) != 0)
				ctrlDown = true;
			str = (ctrlDown ? "ctrl " : "") + (metaDown ? "meta " : "") + (altDown ? "alt " : "") +(shiftDown ? "shift " : "");
			if(tmpKey != 0)
				key = tmpKey;
			if(key != 0)
				str += new String(new char[] {key}).toUpperCase();
			KeyStrokeCellEditor.super.doSetValue(str);
		}
		
		public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
			if((e.keyCode & SWT.ALT) != 0)
				altDown = false;
			if((e.keyCode & SWT.SHIFT) != 0)
				shiftDown = false;
			if((e.keyCode & SWT.CTRL) != 0)
				ctrlDown = false;
			if((e.keyCode & SWT.COMMAND) != 0)
				metaDown = false;
		}
		
		protected Object doGetValue() {
			try {
				return KeyStroke.getKeyStroke(str);
			} catch(Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		protected void doSetValue(Object value) {
			try {
				if(comp != null)
					comp.selectInCode((String)getId());
				KeyStrokeWrapper lw = (KeyStrokeWrapper) value;
				if(value != null) {
					super.doSetValue(""+lw.getKeyStroke());
					return;
				}
				super.doSetValue("");
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}

	}

}
