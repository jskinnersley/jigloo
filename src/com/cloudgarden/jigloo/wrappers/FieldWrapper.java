/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.descriptors.FieldPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.FormPropertySource;
import com.cloudgarden.jigloo.util.FieldManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FieldWrapper {

	private static HashMap valueMap = new HashMap();

	private Class mainClass;
	protected Object value;
	private String propName;
	private String fieldName = null;
	protected String[] fieldNames;
	private FormComponent comp;
	private boolean isSwing;
	private int selectedIndex = 0;
	private Object mainObject;

	public FieldWrapper(Object value, String propName, String[] fieldNames, FormComponent comp) {
		this.value = value;
		this.fieldNames = fieldNames;
		this.propName = propName;
		this.comp = comp;
		mainObject = comp;
		if (comp != null)
			isSwing = comp.isSwing();
	}

	public FieldWrapper(FieldWrapper wrapper) {
		this.comp = wrapper.getFormComponent();
		this.value = wrapper.getValue();
		this.fieldNames = wrapper.getFields();
		this.propName = wrapper.getPropName();
		mainObject = wrapper.getMainObject();
		if (comp != null)
			isSwing = comp.isSwing();
	}

	public FieldWrapper getCopy() {
		return new FieldWrapper(this);
		//return new FieldWrapper(value, propName, fieldNames, comp);
	}

	public FormComponent getFormComponent() {
		return comp;
	}

	public Object getMainObject() {
		return mainObject;
	}

	public void setMainObject(Object object) {
		mainObject = object;
	}

	public String[] getFields() {
		checkFields();
		return fieldNames;
	}

	public String[] getShortFields() {
		String[] fields = getFields();
		String[] shorts = new String[fields.length];
		//turn "java.awt.Layout.CENTER" into just "Layout.CENTER"
		for (int i = 0; i < fields.length; i++) {
			shorts[i] = FieldPropertyDescriptor.getNonQualFieldName(fields[i]);
		}
		return shorts;
	}

	public String getPropName() {
		return propName;
	}

	public void setFields(String[] fieldOpts) {
		fieldNames = fieldOpts;
	}

	public Class getMainClass() {
		if (mainObject == null)
			return null;
		if (mainObject instanceof FormComponent) {
			FormComponent fc = (FormComponent) mainObject;
			mainClass = fc.getMainClass();
			if(mainClass == null)
				return null;
			//catch case where fc.nonVisObj cannot be created and is
			//set to a class instead
			if (mainClass.equals(Class.class)) {
				mainClass = (Class) fc.getNonVisualObject();
			}
		} else if (mainObject instanceof LayoutWrapper) {
			mainClass = ((LayoutWrapper) mainObject).getMainClass();
		} else if (mainObject instanceof LayoutDataWrapper) {
			mainClass = ((LayoutDataWrapper) mainObject).getMainClass();
		} else if (mainObject instanceof FormPropertySource) {
			Object ob = ((FormPropertySource) mainObject).getObject();
			if (ob == null)
				return null;
			mainClass = ob.getClass();
		} else {
			mainClass = mainObject.getClass();
		}
		return mainClass;
	}

	public void checkFields() {
		if (comp == null)
			return;
		if ((comp.isSwing() && !isSwing)
			|| (!comp.isSwing() && isSwing)
			|| fieldName == null
			|| fieldNames == null
			|| mainClass == null
			|| !mainClass.equals(getMainClass())) {
			if(mainClass == null)
				return;
			fieldNames = FieldManager.getFieldOptions(propName, mainClass);
			//System.out.println(
			//"GET FIELD OPTIONS "
			//+ propName
			//+ ", "
			//+ getMainClass()
			//+ ", "
			//+ fieldNames);
			isSwing = comp.isSwing();
			fieldName = getFieldName(value, fieldNames);
			//fieldName = null;
			if (fieldName != null)
				value = getValueAsObject(fieldName);
		}
	}

	public Object getValue() {
		checkFields();
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		fieldName = getFieldName(value, fieldNames);
	}

	private String getFieldName(Object value, String[] names) {
		//if(fieldName != null) return fieldName;
		//System.out.println("fieldName = " + fieldName + " names = " + names);
		if (names == null) {
			if (jiglooPlugin.DEBUG_EXTRA)
				System.err.println(
					"FIELD WRAPPER NAMES==NULL "
						+ getMainClass()
						+ ", "
						+ value
						+ ", "
						+ getPropName()
						+ ", "
						+ getFormComponent());
			return null;
		}
		for (int i = 0; i < names.length; i++) {
			try {
			    Object val = getValueAsObject(names[i]);
				if (val.equals(value)) {
					selectedIndex = i;
					fieldName = names[i];
					return fieldName;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (selectedIndex >= names.length) {
			//return value.toString();
			selectedIndex = 0;
		}
		//TODO handle this setPropertyValue better - should be independent
		//of comp (especially since it may need to apply to a LayoutWrapper
		//instead).

		//		comp.setPropertyValue(
		//			propName,
		//			getValueAsInteger(names[selectedIndex]),
		//			false);

		fieldName = names[selectedIndex];
		//value = getValueAsInteger(fieldName);
		return names[selectedIndex];
	}

	private Object getValueAsObject(String name) {
	    Object val = valueMap.get(name);
		if (val != null)
			return val;
		String className = null;
		String fieldName = null;
		Class clazz = null;
		try {
			int pos = name.lastIndexOf(".");
			if (pos < 0) {
				val = new Integer(name); //name is a String integer, eg "2".
			} else {
				className = name.substring(0, pos);
				fieldName = name.substring(pos + 1);
				className = FieldPropertyDescriptor.getFullClassName(className);
				clazz = jiglooPlugin.loadClass(className);
				Field field = clazz.getField(fieldName);
				val = field.get(null); //static field assumed
			}
		} catch (Exception e) {
			System.out.println("Error getting field " + className + "." + fieldName);
			e.printStackTrace();
			val = new Integer(0);
		}
		valueMap.put(name, val);
		return val;
	}

	public String getFieldClassName() {
		fieldName = getFieldName(value, getFields());
		int pos = fieldName.lastIndexOf(".");
		if (pos > 0) {
			String className = fieldName.substring(0, pos);
			className = FieldPropertyDescriptor.getFullClassName(className);
			return className;
		}
		return null;
	}

	public String getFieldAsString() {
		return FieldPropertyDescriptor.getSemiQualFieldName(getFullFieldName());
	}

	public String getFullFieldName() {
		fieldName = getFieldName(value, getFields());
		return FieldPropertyDescriptor.getFullClassName(fieldName);
	}

	public void addRequiredImport(IJavaCodeManager jcm) {
		jcm.addImport(getFieldClassName());
	}

	public String toString() {
		try {
			String name = getFieldName(value, getFields());
			if (name == null) {
				System.err.println("*** FieldWrapper - name = null, value=" + value + ", " + comp + ", " + propName);
				return "FieldWrapper - name = null, value=" + value;
			}
			//System.out.println("FieldWrapper - toString " + name);
			return FieldPropertyDescriptor.getNonQualFieldName(name);
		} catch (Throwable t) {
			t.printStackTrace();
			return t.toString();
		}
	}

	public FormPropertyDescriptor createPropertyDescriptor(String name, FormComponent comp) {
		return new FieldPropertyDescriptor(name, name, comp, this);
	}

}
