/*
 * Created on Jun 21, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties.sources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.descriptors.AnchorPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.LayoutPropertyDescriptor;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LayoutPropertySource implements IPropertySource {

	LayoutWrapper lw;
	LayoutDataWrapper ldw;
	String layoutType;
	FormComponent comp;
	IPropertyDescriptor layoutDesc;
	IPropertyDescriptor layoutDataDesc;
	IPropertyDescriptor anchorDesc;

	public LayoutPropertySource(FormComponent comp) {
		this.comp = comp;
		layoutDesc = new LayoutPropertyDescriptor("Layout", "Layout", comp, this);
		layoutDataDesc = new FormPropertyDescriptor("Constraints", "Constraints", comp, this);
		anchorDesc = new AnchorPropertyDescriptor("Anchor", "Anchor", comp);
		lw = (LayoutWrapper) comp.getLayoutWrapper();
		ldw = comp.getLayoutDataWrapper();
		//System.err.println("LAYOUT PROP SRC ldw = " + ldw.hashCode());
	}

	public Object getEditableValue() {
		return this;
	}

	public void dispose() {
		comp = null;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descs = null;
		String plt = comp.getParentLayoutType();
		if ((comp.isSWT() && "Form".equals(plt)) || "Anchor".equals(plt)) {
			descs = new IPropertyDescriptor[3];
			descs[0] = layoutDesc;
			descs[1] = layoutDataDesc;
			descs[2] = anchorDesc;
		} else {
			descs = new IPropertyDescriptor[2];
			descs[0] = layoutDesc;
			descs[1] = layoutDataDesc;
		}
		return descs;
	}

	public Object getPropertyValue(Object id) {
		if ("Layout".equals(id)) {
			lw = comp.getLayoutWrapper();
		    return lw;
		}
		if ("Constraints".equals(id)) {
			ldw = comp.getLayoutDataWrapper();
		    return ldw;
		}
		if ("Anchor".equals(id))
			return comp.getAnchorDesc();
		return null;
	}

	public boolean isPropertySet(Object id) {
		if ("Layout".equals(id)) {
			if (lw == null)
				return false;
			return lw.isSet();
		}
		if ("Constraints".equals(id))
			return false;
		return false;
	}

	public void resetPropertyValue(Object id) {
		if (id.equals("Layout")) {
		    comp.resetLayoutWrapper();
		    lw = comp.getLayoutWrapper();
		    lw.repairInCode(true);
			comp.getEditor().setDirtyAndUpdate();
		}
	}

	public void setPropertyValue(Object id, Object value) {
		if (id.equals("Layout")) {
			lw = (LayoutWrapper) value;
			LayoutWrapper clw = comp.getLayoutWrapper();
			comp.executeSetLayoutWrapper(lw);
		}
		if (id.equals("Constraints")) {
			ldw = (LayoutDataWrapper) value;
			comp.executeSetLayoutDataWrapper(ldw);
		}
	}

}
