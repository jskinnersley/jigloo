package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.outline.FormOutlineLabelProvider;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.EventWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EventPropertyDescriptor extends ChoicePropertyDescriptor { // BooleanPropertyDescriptor { //TextPropertyDescriptor {

	static Integer zero = new Integer(0);
	static Integer one = new Integer(1);
	public static final String INLINE = "inline";
    public static final String HANDLER_METHOD = "handler method";
    public static final String NOT_HANDLED = "not handled";

	private EventWrapper propSrc;
	private String defName;
	private FormOutlineLabelProvider labProv = null;
	private String label = "inline";

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public EventPropertyDescriptor(Object id, String displayName, 
	        EventWrapper propSrc, String defName) {
//		super(id, displayName, null, propSrc);
		super(id, displayName, propSrc.getFormComponent(), items, propSrc);
		this.propSrc = propSrc;
		this.defName = defName;
//		labProv = new FormOutlineLabelProvider("inline");
//		setLabelProvider(labProv);
	}

	public String getDisplayName() {
		//if (propSrc.isPropertySet(getId()))
			//return super.getDisplayName() + "*";
		return super.getDisplayName();
	}

//	public CellEditor createPropertyEditor(Composite parent) {
//		try {
//			if(propSrc instanceof FormComponent) {
//				FormComponent fc = (FormComponent)propSrc;
//				fc.selectInCode((String)getId());
//			}
//			CellEditor editor = new EventCellEditor(parent);
//			if (getValidator() != null)
//				editor.setValidator(getValidator());
//			return editor;
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	static String[] items = { NOT_HANDLED, INLINE, HANDLER_METHOD };
	
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ChoiceCellEditor(parent, comp, items);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

	class ChoiceCellEditor extends FormComboBoxCellEditor {

		public ChoiceCellEditor(
			Composite parent,
			FormComponent comp,
			String[] items) {
			super(parent, items, SWT.NULL);
		}

		private String handlerMethod= null;
		
		protected Object doGetValue() {
			Object o = super.doGetValue();
			int index = ((Integer) o).intValue();
			if(index == 2 && handlerMethod != null)
			    return handlerMethod;
			if (objs != null)
				return objs[index];
			return defName+choices[index];
		}

		protected void doSetValue(Object value) {
		    handlerMethod = null;
			if (comp != null) {
                String propVal = (String) propSrc.getRawPropertyValue(getId());
//                System.out.println("GOT PROP VAL "+propVal+", "+getId());
                if (propVal != null && propVal.endsWith(HANDLER_METHOD)) {
                    handlerMethod = propVal;
                    propVal = propVal.substring(0, propVal.length()
                            - HANDLER_METHOD.length());
                    propVal = JiglooUtils.replace(propVal, ".", "_");
                    comp.getEditor().selectInJavaEditor(propVal);
                } else {
                    comp.selectInCode((String)getId(), true);
                }
            }
			if(handlerMethod != null) {
			    super.doSetValue(new Integer(2));
			    return;
			}
			Integer val = new Integer(0);
			if (value != null) {
				for (int i = 0; i < choices.length; i++) {
					if (value.equals(choices[i])
						|| (objs != null && value.equals(objs[i])))
						val = new Integer(i);
				}
			}
			super.doSetValue(val);
		}

		protected void doSetFocus() {
			super.doSetFocus();
		}

	}

	class EventCellEditor extends CheckboxCellEditor {

        public EventCellEditor(Composite parent) {
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
			if (jiglooPlugin.getDefault().useDCForBooleans()) {
				long now = System.currentTimeMillis();
				if (now - lastClick < 50 && now - lastActivate > 500) {
					lastActivate = now;
					super.activate();
				}
				lastClick = now;
			} else {
				super.activate();
			}
		}

		/*
		class EventCellEditor extends ComboBoxCellEditor {
		
			public EventCellEditor(Composite parent) {
				super(parent, items, SWT.NULL);
			}
		*/

		protected Object doGetValue() {
			Object o = super.doGetValue();
			if (o.equals(Boolean.FALSE) && label.equals(INLINE))
				return null;
			return defName+label;
		}

		protected void doSetValue(Object value) {
			Object val = value;
			if(val == null) {
			    val = Boolean.FALSE;
			} else {
				if(label.equals(INLINE)) {
				    label = HANDLER_METHOD;
					val = Boolean.FALSE;
				} else 	if(label.equals(HANDLER_METHOD)) {
					label = INLINE;
					val = Boolean.TRUE;
				}
				labProv.setLabel(label);
			}
			super.doSetValue(val);
		}

	}

}
