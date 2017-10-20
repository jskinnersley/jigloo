package com.cloudgarden.jigloo.properties.descriptors;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.wrappers.MnemonicWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MnemonicPropertyDescriptor extends FormPropertyDescriptor {

	private static Integer[] mnems;
	private static String[] names;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public MnemonicPropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
		if (mnems == null) {
		    Field[] fields = KeyEvent.class.getFields();
		    Vector vals = new Vector();
		    for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if(f.getName().startsWith("VK_")) {
                    vals.add(f);
                }
            }
			Collections.sort(vals, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Field) o1).getName().compareTo(((Field) o2).getName());
				}
			});
			mnems = new Integer[vals.size()];
			names = new String[vals.size()];
			for (int i = 0; i < mnems.length; i++) {
			    Field f = (Field)vals.elementAt(i);
			    try {
                    mnems[i] = (Integer)f.get(null);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
			CellEditor editor = new MnemonicCellEditor(parent, comp);
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class MnemonicCellEditor extends TextCellEditor implements KeyListener {

		private FormComponent comp;
		private org.eclipse.swt.events.KeyEvent event;
		private String str;
		boolean shiftDown = false;
		boolean ctrlDown = false;
		boolean altDown = false;
		int key = 0;
		MnemonicWrapper mw;
		
		public MnemonicCellEditor(Composite parent, FormComponent comp) {
			super(parent, SWT.NULL);
			this.comp = comp;
		}
		
		public void activate() {
			super.activate();
			jiglooPlugin.getDefault().setKeyListener(this);
		}

		public void deactivate() {
			super.deactivate();
			jiglooPlugin.getDefault().setKeyListener(null);
		}
		
		public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			int okey = e.keyCode;
			key = (char)(e.keyCode & SWT.KEY_MASK);
			if(key == '\r') {
				doSetValue(mw);
				fireApplyEditorValue();
				return;
			}
//			System.out.println("MNEM KP "+(int)key);
			if((e.keyCode & SWT.ALT) != 0)
				key = KeyEvent.VK_ALT;
			else if((e.keyCode & SWT.SHIFT) != 0)
				key = KeyEvent.VK_SHIFT;
			else if((e.keyCode & SWT.CTRL) != 0)
				key = KeyEvent.VK_CONTROL;
			//function keys
			else if(key >= 10 && key <= 24)
				key += 102;
			else if(okey == SWT.ARROW_UP)
				key = KeyEvent.VK_UP;
			else if(okey == SWT.ARROW_DOWN)
				key = KeyEvent.VK_DOWN;
			else if(okey == SWT.ARROW_LEFT)
				key = KeyEvent.VK_LEFT;
			else if(okey == SWT.ARROW_RIGHT)
				key = KeyEvent.VK_RIGHT;
			else if(okey == SWT.PAGE_UP)
				key = KeyEvent.VK_PAGE_UP;
			else if(okey == SWT.PAGE_DOWN)
				key = KeyEvent.VK_PAGE_DOWN;
			else if(okey == SWT.HOME)
				key = KeyEvent.VK_HOME;
			else if(okey == SWT.END)
				key = KeyEvent.VK_END;
			else if(okey == SWT.INSERT)
				key = KeyEvent.VK_INSERT;
			else if(key == 80)
				key = KeyEvent.VK_ENTER;
			else if(key >= 97 && key <= 122)
				key -= 32;
			else if(key > 64 && key <= 90)
				key -= 32;
			else if(key >= 40 && key <= 43)
				key += 64;
			mw.setValue(new Integer(key));
			doSetValue(mw);
			e.doit = false;
			e.keyCode = 0;
		}
		
		protected Object doGetValue() {
			try {
				return mw; //.getValue(null);
			} catch(Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		protected void doSetValue(Object value) {
			try {
				if(comp != null)
					comp.selectInCode((String)getId());
				if(value instanceof MnemonicWrapper) {
					mw = (MnemonicWrapper) value;
					mw = new MnemonicWrapper((Integer)mw.getValue(null), comp);
				} else {
					mw = new MnemonicWrapper((Integer)value, comp);
				}
				super.doSetValue(mw.toString());
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}

		public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
		}

	}

}
