package com.cloudgarden.jigloo.properties.descriptors;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BoxLayout;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.layoutHandler.EnfinLayoutHandler;
import com.cloudgarden.jigloo.properties.FormComboBoxCellEditor;
import com.cloudgarden.jigloo.properties.sources.LayoutPropertySource;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;
import com.cloudgarden.layout.AnchorLayout;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LayoutPropertyDescriptor extends FormPropertyDescriptor {

	private static Integer[] keys;
	private static Class[] classes;
	private LayoutPropertySource layoutPropSrc;

	private String[] awtItems;
	private Class[] awtClasses ;
	
	static {
    }
		
	static String[] swtItems =
		new String[] { "Absolute", "Fill", "Form", "Grid", "Row", "Stack" };
	static Class[] swtClasses =
		new Class[] { null, FillLayout.class, org.eclipse.swt.layout.FormLayout.class,
	        org.eclipse.swt.layout.GridLayout.class, RowLayout.class, StackLayout.class };

	private String[] items;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public LayoutPropertyDescriptor(
		Object id,
		String displayName,
		FormComponent comp,
		LayoutPropertySource propSrc) {
		super(id, displayName, comp);
		this.layoutPropSrc = propSrc;
		boolean useEnfin = comp.getEditor().canUseEnfinLayout();
//		useEnfin = false;
		
	    if (jiglooPlugin.canUseGroupLayout()
                && LayoutWrapper.SUPPORT_GROUP_LAYOUT
                && useEnfin) {
            awtItems = new String[] { "Absolute", "Anchor", "Border",
                    "Box - X", "Box - Y", "Card", "Enfin", "Flow", "Form", "Grid",
                    "GridBag", "Group", "Table" };
        	awtClasses =
        		new Class[] {
        			null,
        			AnchorLayout.class,
        			BorderLayout.class,
        			BoxLayout.class,
        			BoxLayout.class,
        			CardLayout.class,
        			EnfinLayoutHandler.getLayoutClass(),
        			FlowLayout.class,
        			FormLayout.class,
        			GridLayout.class,
        			GridBagLayout.class,
        			GroupLayout.class,
        			TableLayout.class };
        } else if (jiglooPlugin.canUseGroupLayout()
                && LayoutWrapper.SUPPORT_GROUP_LAYOUT) {
            awtItems = new String[] { "Absolute", "Anchor", "Border",
                    "Box - X", "Box - Y", "Card", "Flow", "Form", "Grid",
                    "GridBag", "Group", "Table" };
        	awtClasses =
        		new Class[] {
        			null,
        			AnchorLayout.class,
        			BorderLayout.class,
        			BoxLayout.class,
        			BoxLayout.class,
        			CardLayout.class,
        			FlowLayout.class,
        			FormLayout.class,
        			GridLayout.class,
        			GridBagLayout.class,
        			GroupLayout.class,
        			TableLayout.class };
        } else if (useEnfin) {
            awtItems = new String[] { "Absolute", "Anchor", "Border",
                    "Box - X", "Box - Y", "Card", "Enfin", "Flow", "Form", "Grid",
                    "GridBag", "Table" };
        	awtClasses =
        		new Class[] {
        			null,
        			AnchorLayout.class,
        			BorderLayout.class,
        			BoxLayout.class,
        			BoxLayout.class,
        			CardLayout.class,
        			EnfinLayoutHandler.getLayoutClass(),
        			FlowLayout.class,
        			FormLayout.class,
        			GridLayout.class,
        			GridBagLayout.class,
        			GroupLayout.class,
        			TableLayout.class };
        } else {
            awtItems = new String[] { "Absolute", "Anchor", "Border",
                    "Box - X", "Box - Y", "Card", "Flow", "Form", "Grid",
                    "GridBag", "Table" };
        	awtClasses =
        		new Class[] {
        			null,
        			AnchorLayout.class,
        			BorderLayout.class,
        			BoxLayout.class,
        			BoxLayout.class,
        			CardLayout.class,
        			FlowLayout.class,
        			FormLayout.class,
        			GridLayout.class,
        			GridBagLayout.class,
        			TableLayout.class };
        }
	}

	public String getDisplayName() {
		if (layoutPropSrc.isPropertySet(getId()))
			return super.getDisplayName() + "*";
		return super.getDisplayName();
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		try {
			if (comp.usesCustomLayout() &&!comp.getLayoutWrapper().isSet())
				return null;
			CellEditor editor = new LayoutCellEditor(parent, comp);
			if (getValidator() != null)
				editor.setValidator(getValidator());
			return editor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	class LayoutCellEditor extends FormComboBoxCellEditor {

		private FormComponent comp;

		public LayoutCellEditor(Composite parent, FormComponent comp) {
			super(parent, awtItems, SWT.NULL);
			items = awtItems;
			classes = awtClasses;
			this.comp = comp;
			if (comp.isSwing()) {
			    Vector scls = comp.getEditor().getCustomSwingLayoutClasses();
			    if(scls.size() != 0) {
			        items = new String[awtItems.length+scls.size()];
			        classes = new Class[awtItems.length+scls.size()];
			        for (int i = 0; i < items.length; i++) {
			            if(i < awtItems.length) {
			                items[i] = awtItems[i];
			                classes[i] = awtClasses[i];
			            } else {
			                classes[i] = (Class)scls.elementAt(i-awtItems.length);
			                items[i] = JiglooUtils.getShortLayoutName(classes[i]);
			            }
			        }
			        setItems(items);
			    }
			} else {
			    Vector scls = comp.getEditor().getCustomSWTLayoutClasses();
			    if(scls.size() != 0) {
			        items = new String[swtItems.length+scls.size()];
			        classes = new Class[swtItems.length+scls.size()];
			        for (int i = 0; i < items.length; i++) {
			            if(i < swtItems.length) {
			                items[i] = swtItems[i];
			                classes[i] = swtClasses[i];
			            } else {
			                classes[i] = (Class)scls.elementAt(i-swtItems.length);
			                items[i] = JiglooUtils.getShortLayoutName(classes[i]);
			            }
			        }
			        setItems(items);
			    } else {
					items = swtItems;
					classes = swtClasses;
					setItems(swtItems);
			    }
			}
			
			if (keys == null) {
				keys = new Integer[100];
				for (int i = 0; i < 100; i++)
					keys[i] = new Integer(i);
			}
		}

		LayoutWrapper lwIn;
		Object setVal;

		protected Object doGetValue() {
			Object o = super.doGetValue();
			if (o.equals(setVal))
				return lwIn;
			String val = null;
			int selIndex = 0;
			for (int i = 0; i < items.length; i++) {
				if (o.equals(keys[i])) {
				    selIndex = i;
				    break;
				}
			}
			val = items[selIndex];
			Class cls = classes[selIndex];
			if (val.startsWith("Box")) {
				LayoutWrapper lw =
					new LayoutWrapper(comp, BoxLayout.class, comp.isSwing());
				if (val.equals("Box - X")) {
					lw.setAttribute("axis", new Integer(0));
				} else {
					lw.setAttribute("axis", new Integer(1));
				}
				return lw;
			} else {
				//System.out.println("LAY CELL ED doGetVal " + val);
			    if(cls == null)
			        return new LayoutWrapper(comp, val, comp.isSwing());
		        return new LayoutWrapper(comp, cls, comp.isSwing());
			}
		}

		protected void doSetValue(Object value) {
			Object val = keys[0];
			comp.selectInCode("LAYOUT");
			LayoutWrapper lw = (LayoutWrapper) value;
			Object man = lw.toString();
			if (man == null)
				return;
			//System.out.println("LAY CELL ED doSetVal " + man);
			for (int i = 0; i < items.length; i++) {
				if (man.equals(items[i])) {
					val = keys[i];
				}
			}
			lwIn = (LayoutWrapper) lw.getCopy(null);
			setVal = val;
			super.doSetValue(val);
		}

	}

}
