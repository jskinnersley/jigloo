package com.cloudgarden.jigloo.properties.sources;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author Administrator
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class SizePropertySource extends ChangeablePropertySource {

    public static String ID_WIDTH = "Width"; //$NON-NLS-1$
    public static String ID_HEIGHT = "Height"; //$NON-NLS-1$
    protected static IPropertyDescriptor[] descriptors;

    static {
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(ID_WIDTH, "width"),
                new TextPropertyDescriptor(ID_HEIGHT, "height") };
    }

    protected Point point = null;
    private FormComponent comp;
    private String propName;

    public SizePropertySource(Point point, FormComponent comp, String propName) {
        this.point = new Point(point.x, point.y);
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
        if (ID_WIDTH.equals(propName)) {
            return new String(new Integer(point.x).toString());
        }
        if (ID_HEIGHT.equals(propName)) {
            return new String(new Integer(point.y).toString());
        }
        return null;
    }

    public Point getValue() {
        return new Point(point.x, point.y);
    }

    public void setValue(Point point) {
        this.point = new Point(point.x, point.y);
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
        if (ID_WIDTH.equals(propName) || ID_HEIGHT.equals(propName))
            return true;
        return false;
    }

    public void resetPropertyValue(Object name) {
        comp.resetPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value) {
//        System.err.println("SPS set prop "+propName+", "+value);
        if (ID_WIDTH.equals(propName)) {
            try {
                Integer newInt = new Integer((String) value);
                if(point.x != newInt.intValue())
                	setSourceChanged();
                point.x = newInt.intValue();
            } catch (Exception e) {
                point.x = -1;
                setSourceChanged();
            }
        } else if (ID_HEIGHT.equals(propName)) {
            try {
                Integer newInt = new Integer((String) value);
                if(point.y != newInt.intValue())
                	setSourceChanged();
                point.y = newInt.intValue();
            } catch (Exception e) {
                point.y = -1;
                setSourceChanged();
            }
        } else
            return;
//        firePropertyChanged((String) propName);
    }

    public String toString() {
        return "[" + point.x + ", " + point.y + "]";
    }

}