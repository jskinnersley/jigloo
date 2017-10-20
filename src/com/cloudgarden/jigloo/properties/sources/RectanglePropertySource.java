package com.cloudgarden.jigloo.properties.sources;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RectanglePropertySource extends ChangeablePropertySource {

	public static String ID_X = "X";
	public static String ID_Y = "Y";
	public static String ID_WIDTH = "Width";
	public static String ID_HEIGHT = "Height";
	protected static IPropertyDescriptor[] descriptors;

	static {
		descriptors =
			new IPropertyDescriptor[] {
				new TextPropertyDescriptor(ID_X, "x"),
				new TextPropertyDescriptor(ID_Y, "y"),
				new TextPropertyDescriptor(ID_WIDTH, "width"),
				new TextPropertyDescriptor(ID_HEIGHT, "height")};
	}

	protected Rectangle rec = null;
	private FormComponent comp;
	private String propName;

	public RectanglePropertySource(
		Rectangle rec,
		FormComponent comp,
		String propName) {
		this.rec = new Rectangle(rec.x, rec.y, rec.width, rec.height);
		this.comp = comp;
		this.propName = propName;
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
		if (ID_X.equals(propName))
			return new String(new Integer(rec.x).toString());
		if (ID_Y.equals(propName))
			return new String(new Integer(rec.y).toString());
		if (ID_WIDTH.equals(propName))
			return new String(new Integer(rec.width).toString());
		if (ID_HEIGHT.equals(propName))
			return new String(new Integer(rec.height).toString());
		return null;
	}

	public Rectangle getValue() {
		return new Rectangle(rec.x, rec.y, rec.width, rec.height);
	}

	public void setValue(Rectangle rec) {
		this.rec = rec;
	}

	public FormComponent getFormComponent() {
		return comp;
	}

	public String getPropertyName() {
		return propName;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	public boolean isPropertySet(Object propName) {
		if (ID_X.equals(propName)
			|| ID_Y.equals(propName)
			|| ID_WIDTH.equals(propName)
			|| ID_HEIGHT.equals(propName))
			return true;
		return false;
	}

	public void resetPropertyValue(Object propName) {
		comp.resetPropertyValue(propName);
	}

	private int getIntProp(int oldVal, Object value) {
	    try {
			Integer newInt = new Integer(value.toString());
	        if(oldVal != newInt.intValue())
	        	setSourceChanged();
			return newInt.intValue();
	    } catch(Throwable t) {
	    	setSourceChanged();
	        return -1;
	    }
	}
	
	public void setPropertyValue(Object propName, Object value) {
		if (ID_X.equals(propName)) {
			rec.x = getIntProp(rec.x, value);
		} else if (ID_Y.equals(propName)) {
			rec.y = getIntProp(rec.y, value);
		} else if (ID_WIDTH.equals(propName)) {
			rec.width = getIntProp(rec.width, value);
		} else if (ID_HEIGHT.equals(propName)) {
			rec.height = getIntProp(rec.height, value);
		} else
			return;
		firePropertyChanged((String) propName);
	}

	public String toString() {
		return "["
			+ rec.x
			+ ", "
			+ rec.y
			+ ", "
			+ rec.width
			+ ", "
			+ rec.height
			+ "]";
	}

}
