/**
 * 
 */
package com.cloudgarden.jigloo.properties.sources;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;

public class BasicPropertySource implements IPropertySource {
	/**
	 * 
	 */
	protected FormComponent formComponent;
	protected IPropertyDescriptor[] pds = null;
	
	public BasicPropertySource(FormComponent formComponent) {
		this.formComponent = formComponent;
	}
	
	public void dispose() {
		formComponent = null;
		pds = null;
	}
	
	public Object getEditableValue() {
		return formComponent.getEditableValue();
	}
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if(pds != null)
			return pds;
		pds = formComponent.getPropertyDescriptors();
		Vector npds = new Vector();
		for (int i = 0; i < pds.length; i++) {
			IPropertyDescriptor pd = pds[i];
			if(!formComponent.isHiddenProperty(pd.getId()) && formComponent.isBasicProperty(pd.getId()))
				npds.add(pd);
		}
		pds = new IPropertyDescriptor[npds.size()];
		npds.copyInto(pds);
		return pds;
	}
	public Object getPropertyValue(Object id) {
		return formComponent.getPropertyValue(id);
	}
	public boolean isPropertySet(Object id) {
		return formComponent.isPropertySet(id);
	}
	public void resetPropertyValue(Object id) {
		formComponent.resetPropertyValue(id);
	}
	public void setPropertyValue(Object id, Object value) {
		formComponent.setPropertyValue(id, 	value);
	}
}