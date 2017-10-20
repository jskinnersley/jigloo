package com.cloudgarden.jigloo.properties.descriptors;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.EventWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EventListenerPropertyDescriptor extends ClassPropertyDescriptor {

	private String[] choices;
	private Object[] objs;
	private Class listenerClass;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public EventListenerPropertyDescriptor(Object id, String displayName, FormComponent comp, Class listenerClass) {
		super(id, displayName, comp, listenerClass);
		this.listenerClass = listenerClass;
	}

	public boolean allowThis() {
		return true;
	}

	public boolean allowAnonymous() {
		return true;
	}

	protected Object createWrapper(String name, FormComponent comp) {
		return new EventWrapper(comp, name);
	}

	//protected void doGetValue(Object value) {
	//System.out.println("DO GET VAL "+value+", "+this);
	//comp.repairEventWrapperInCode();
	//}

}
