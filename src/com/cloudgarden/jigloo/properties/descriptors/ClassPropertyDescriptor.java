package com.cloudgarden.jigloo.properties.descriptors;

import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.util.DefaultValueManager;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ClassPropertyDescriptor extends FormPropertyDescriptor {

	private String[] choices;
	private Object[] objs;
	private Class cls;
	private Object id;
	private FormComponent comp;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ClassPropertyDescriptor(Object id, String displayName, FormComponent comp, Class cls) {
		super(id, displayName, comp);
		this.cls = cls;
		this.id = id;
		this.comp = comp;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		Vector nonVis = comp.getEditor().getComponents();
		Vector bgs = new Vector();
		for (int i = 0; i < nonVis.size(); i++) {
			FormComponent fc = (FormComponent) nonVis.elementAt(i);
			if (!fc.isInherited() && fc.isSubclassOf(getDesiredClass())) {
				bgs.add(fc);
			}
		}
		int EXTRAS = 1;
		if (allowThis())
			EXTRAS++;
		if (allowAnonymous())
			EXTRAS++;
		Object[] objs = new Object[bgs.size() + EXTRAS];
		String[] choices = new String[objs.length];
		int index = EXTRAS;

		objs[0] = DefaultValueManager.getDefault((String) id, comp.getMainClass());
		if (objs[0] == null)
			objs[0] = "null";

		//System.out.println("GOT DEFAULT " + objs[0] + " for " + id + ", " + comp.getMainClass());
		
		choices[0] = "<none>";
		if (allowAnonymous()) {
			objs[1] = "<anonymous>";
			choices[1] = "<anonymous>";
		}
		if (allowThis()) {
			objs[2] = "this";
			choices[2] = "this";
		}
		for (int j = 0; j < bgs.size(); j++) {
			FormComponent fc = (FormComponent) bgs.elementAt(j);
			//objs[index] = createWrapper(fc.getNonBlockName(), comp);
			objs[index] = createWrapper(fc.getName(), comp);
			String name = fc.getName();
			if(name.indexOf("%") >= 0)
				name = fc.getNonBlockName();
			choices[index] = name;
			index++;
		}
		this.choices = choices;
		this.objs = objs;
		CellEditor editor = new ChoiceCellEditor(parent, comp, choices);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	public boolean allowThis() {
		return false;
	}

	public boolean allowAnonymous() {
		return false;
	}

	protected Class getDesiredClass() {
		return cls;
	}

	protected Object createWrapper(String name, FormComponent comp) {
		return new ClassWrapper(name, comp, cls);
	}

	protected void doGetValue(Object value) {}

	class ChoiceCellEditor extends FormComboBoxCellEditor {

		public ChoiceCellEditor(Composite parent, FormComponent comp, String[] items) {
			super(parent, items, SWT.NULL);
		}

		private Object initValue = null;

		protected Object doGetValue() {
			Object o = super.doGetValue();
			int index = ((Integer) o).intValue();
			Object val = null;
			if (objs != null)
				val = objs[index];
			else
				val = choices[index];
			//			if(!val.equals(initValue))
			//			ClassPropertyDescriptor.this.doGetValue(val);
			return val;
		}

		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			initValue = value;
			//ClassPropertyDescriptor.this.doGetValue(value);
			Integer val = new Integer(0);
			if (value != null) {
				for (int i = 0; i < choices.length; i++) {
					if (value.toString().equals(choices[i]) || (objs != null && value.equals(objs[i])))
						val = new Integer(i);
				}
			}
			super.doSetValue(val);
		}

	}

}
