package com.cloudgarden.jigloo.properties.sources;

import java.awt.Insets;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InsetsPropertySource extends ChangeablePropertySource {

	public static String ID_LEFT = "left";
	public static String ID_RIGHT = "right";
	public static String ID_TOP = "top";
	public static String ID_BOT = "bottom";
	protected static IPropertyDescriptor[] descriptors;

	static {
		descriptors =
			new IPropertyDescriptor[] {
				new TextPropertyDescriptor(ID_LEFT, ID_LEFT),
				new TextPropertyDescriptor(ID_RIGHT, ID_RIGHT),
				new TextPropertyDescriptor(ID_TOP, ID_TOP),
				new TextPropertyDescriptor(ID_BOT, ID_BOT)};
	}

	protected Insets insets = null;

	public InsetsPropertySource(Insets insets) {
		this.insets = (Insets) insets.clone();
	}

	protected void firePropertyChanged(String propName) {
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	public Object getPropertyValue(Object propName) {
		if (ID_TOP.equals(propName))
			return new String(new Integer(insets.top).toString());
		if (ID_RIGHT.equals(propName))
			return new String(new Integer(insets.right).toString());
		if (ID_BOT.equals(propName))
			return new String(new Integer(insets.bottom).toString());
		if (ID_LEFT.equals(propName))
			return new String(new Integer(insets.left).toString());
		return null;
	}

	public Insets getValue() {
		return (Insets) insets.clone();
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	public boolean isPropertySet(Object propName) {
		if (ID_TOP.equals(propName)
			|| ID_BOT.equals(propName)
			|| ID_LEFT.equals(propName)
			|| ID_RIGHT.equals(propName))
			return true;
		return false;
	}

	public void resetPropertyValue(Object propName) {
	}

	private int getIntProp(int oldVal, Object value) {
	    try {
			Integer newInt = new Integer(value.toString());
	        if(oldVal != newInt.intValue())
	            setSourceChanged();
			return newInt.intValue();
	    } catch(Throwable t) {
	    	setSourceChanged();
	        return 0;
	    }
	}
	
	public void setPropertyValue(Object propName, Object value) {
		if (ID_TOP.equals(propName)) {
			insets.top = getIntProp(insets.top, value);
		} else if (ID_BOT.equals(propName)) {
			insets.bottom = getIntProp(insets.bottom, value);
		} else if (ID_LEFT.equals(propName)) {
			insets.left = getIntProp(insets.left, value);
		} else if (ID_RIGHT.equals(propName)) {
			insets.right = getIntProp(insets.right, value);
		} else
			return;
		firePropertyChanged((String) propName);
	}

	public String toString() {
		return "["
			+ insets.top
			+ ", "
			+ insets.left
			+ ", "
			+ insets.bottom
			+ ", "
			+ insets.right
			+ "]";
	}

}
