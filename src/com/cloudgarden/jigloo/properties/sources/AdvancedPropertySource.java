/**
 * 
 */
package com.cloudgarden.jigloo.properties.sources;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.cloudgarden.jigloo.FormComponent;


public class AdvancedPropertySource extends BasicPropertySource {
	/**
	 * 
	 */
	public AdvancedPropertySource(FormComponent formComponent) {
		super(formComponent);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if(pds != null)
			return pds;
		pds = formComponent.getPropertyDescriptors();
		Vector npds = new Vector();
		for (int i = 0; i < pds.length; i++) {
			IPropertyDescriptor pd = pds[i];
			if(!formComponent.isHiddenProperty(pd.getId()) && !formComponent.isBasicProperty(pd.getId()))
				npds.add(pd);
		}
		pds = new IPropertyDescriptor[npds.size()];
		npds.copyInto(pds);
		return pds;
	}
}