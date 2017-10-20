/*
 * Created on Jun 23, 2003
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties.sources;

import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Insets;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.properties.descriptors.AWTCursorPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.BooleanPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.ClassPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.ColorPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.DoubleArrayPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FocusTraversalPolicyPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FontPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormComponentArrayPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.GridVOPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.ImagePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.InsetsPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.IntegerArrayPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.KeyStrokePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.LocalePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.MnemonicPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.NumberPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.RectanglePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.SWTCursorPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.SizePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.StringArrayPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.TextFormPropertyDescriptor;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.AWTCursorWrapper;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.DoubleArrayWrapper;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.FocusTraversalPolicyWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentArrayWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.IntegerArrayWrapper;
import com.cloudgarden.jigloo.wrappers.KeyStrokeWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LocaleWrapper;
import com.cloudgarden.jigloo.wrappers.MnemonicWrapper;
import com.cloudgarden.jigloo.wrappers.SWTCursorWrapper;
import com.cloudgarden.jigloo.wrappers.StringArrayWrapper;
import com.cloudgarden.jigloo.wrappers.WrapperFactory;

public class PropertySourceFactory {

    private static HashMap nameMap = new HashMap();
    private static HashMap typeMap = new HashMap();
    private static HashMap fieldMap = new HashMap();
    private static HashMap eventMap = new HashMap();
    private static HashMap getterMap = new HashMap();
    private static HashMap propEdMap = new HashMap();

    public static void clearCaches() {
    	nameMap.clear();
    	typeMap.clear();
    	fieldMap.clear();
    	eventMap.clear();
    	propEdMap.clear();
    	getterMap.clear();
    }
    
    public static Method getGetter(Class clazz, String propName) {
        HashMap getters = (HashMap)getterMap.get(clazz.getName());
        Method getter = null;
        if(getters == null) {
            getters = new HashMap();
            getterMap.put(clazz.getName(), getters);
        } else {
        	Object val = getters.get(propName);
        	if(val instanceof Method)
        		return (Method)val;
        	if("NO_VALUE".equals(val))
                return null;
        }
        String cpn = JiglooUtils.capitalize(propName);
        String gm = "get"+cpn;
        try {
            getter = clazz.getMethod(gm, null);
        } catch(NoSuchMethodException e) {
            try {
                gm = "is"+cpn;
                getter = clazz.getMethod(gm, null);
            } catch(NoSuchMethodException e2) {
            }
        }
        if(getter != null) {
            getters.put(propName, getter);
        } else {
        	getters.put(propName, "NO_VALUE");
        }
        return getter;
    }

    public static HashMap findPropertyTypes(Class clazz) {
        if (typeMap.containsKey(clazz.getName()))
            return (HashMap) typeMap.get(clazz.getName());
        findPropertyNames(clazz);
        return (HashMap) typeMap.get(clazz.getName());
    }

    public static Vector findPropertyNames(Class clazz) {

        if (nameMap.containsKey(clazz.getName()))
            return (Vector) nameMap.get(clazz.getName());

        Vector propNames = new Vector();
        HashMap propTypes = new HashMap();

        typeMap.put(clazz.getName(), propTypes);
        nameMap.put(clazz.getName(), propNames);

        if (clazz == null)
            return propNames;

        try {
            Method[] meths = clazz.getMethods();
            for (int i = 0; i < meths.length; i++) {
                String name = meths[i].getName();
                if (name.startsWith("set")) {
                    Class[] params = meths[i].getParameterTypes();
                    if (Modifier.isPublic(meths[i].getModifiers())
                            && (params.length == 1) //&&
                                                                     // !params[0].isArray()
                    ) {
                        String getMethodName = "g" + name.substring(1);
                        name = name.substring(3, 4).toLowerCase()
                                + name.substring(4);
                        Method gm = null;
                        try {
                            gm = clazz.getMethod(getMethodName, null);
                        } catch (NoSuchMethodException e) {
                            getMethodName = "is" + getMethodName.substring(3);
                            try {
                                gm = clazz.getMethod(getMethodName, null);
                            } catch (NoSuchMethodException e2) {
                                //System.out.println("Failed to get method
                                // "+getMethodName);
                            }
                        }
                        if (gm != null && name != null
                                && !propNames.contains(name)) {
                            propNames.add(name);
                            Class retType = gm.getReturnType();
                            propTypes.put(name, retType);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.writeToLog("Error finding property names for " + clazz
                    + ", " + t);
        }

        if ( Cacher.isAssignableFrom(Control.class, clazz) 
        		&& !propNames.contains("cursor")) {
            //because getCursor does not exist, but setCursor does!
            propNames.add("cursor");
            propTypes.put("cursor", Cursor.class);
        }

        return propNames;
    }

    public static Vector findEventListenerClasses(Class clazz) {
        if (clazz == null)
            return new Vector();
        if (eventMap.containsKey(clazz.getName()))
            return (Vector) eventMap.get(clazz.getName());
        Vector listClasses = new Vector();
        eventMap.put(clazz.getName(), listClasses);
        
        try {
            Method[] meths = clazz.getMethods();
            for (int i = 0; i < meths.length; i++) {
                String name = meths[i].getName();
                Class[] params = meths[i].getParameterTypes();
                if (name.startsWith("add") && !name.equals("add")
                        && name.endsWith("Listener")
                        && Modifier.isPublic(meths[i].getModifiers())
                        && (params.length == 1) && !params[0].isArray() //&&
                //  Cacher.isAssignableFrom(EventListener.class, params[0])
                ) {
                    //&& ( Cacher.isAssignableFrom(EventListener.class, params[0]) ||
                    // name.indexOf("Listener") > 0)) {
                    //System.out.println(
                    //"Got EventListener " + params[0] + ", meth=" + name);
                    if (!listClasses.contains(params[0])) {
                        listClasses.add(params[0]);
                    }
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error finding event listeners for "
                    + clazz);
        }
        return listClasses;
    }

    public static Vector findFieldNames(Class clazz) {
    	String clazzName = clazz.getName();
        if (fieldMap.containsKey(clazzName))
            return (Vector) fieldMap.get(clazzName);
        Vector fieldNames = new Vector();
        fieldMap.put(clazzName, fieldNames);
        
        try {
            Field[] fields = clazz.getFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                if (Modifier.isPublic(fields[i].getModifiers())
                        && !Modifier.isStatic(fields[i].getModifiers())) {
                    fieldNames.add(name);
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.writeToLog("Error in findFieldNames for " + clazz
                    + ", " + t);
        }
        return fieldNames;
    }

//    public static Class getPropertyType(Object object, String propertyName) {
//        Field field = null;
//        try {
//            field = object.getClass().getField(propertyName);
//        } catch (Exception e) {
//        }
//        if (field != null)
//            return field.getType();
//        return null;
//    }

    /**
     * returns the modified value (eg, if value was given as String but actually
     * the field or method required an Integer, then an Integer would be
     * returned).
     */
    public static Object setProperty(Object object, String propertyName, Object value) throws Exception {
    	Object origValue = value;
        if (value instanceof FormComponentWrapper) {
            FormComponent fc = ((FormComponentWrapper) value).getFormComponent();
            if (fc == null)
                return value;
            value = fc.getControl();
        }
        if (value instanceof LayoutDataWrapper) {
            value = ((LayoutDataWrapper) value).getObject();
        }
        Field field = null;
        try {
            field = object.getClass().getField(propertyName);
        } catch (Exception e) {
        }
        if (field != null) {
            Class fieldType = field.getType();
            //if (value != null && !fieldType.equals(value.getClass())) {
            if (value != null && ! Cacher.isAssignableFrom(fieldType, value.getClass())) {
                if (fieldType.equals(int.class)) {
                    value = new Integer(value.toString());
                } else if (fieldType.equals(short.class)) {
                    value = new Short(value.toString());
                } else if (fieldType.equals(long.class)) {
                    value = new Long(value.toString());
                } else if (fieldType.equals(double.class)) {
                    value = new Double(value.toString());
                } else if (fieldType.equals(float.class)) {
                    value = new Float(value.toString());
                } else if (fieldType.equals(boolean.class)) {
                    value = new Boolean(value.toString());
                } else { //					System.err.println(
                    //						"Unable to convert field "
                    //							+ propertyName
                    //							+ ", "
                    //							+ value.getClass()
                    //							+ " to "
                    //							+ fieldType);
                }
            }
            if ("null".equals(value) && !field.getType().equals(String.class))
                value = null;
            //System.err.println("SETTING FIELD "+field.getName()+" to
            // "+value);
            field.set(object, value);
        } else {

            String methodName = "set"
                    + propertyName.substring(0, 1).toUpperCase()
                    + propertyName.substring(1);
            Method meth = Cacher.getMethod(object.getClass(), methodName, new Class[] { getMethodValueClass(value) });
            meth.invoke(object, new Object[] { value });
        }
        
        if (origValue instanceof FormComponentWrapper) {
        	return origValue;
        }
        
        return value;
    }

    private static Class getMethodValueClass(Object value) {
        Class cls = value.getClass();
        if (cls.equals(Boolean.class))
            cls = boolean.class;
        if (cls.equals(Integer.class))
            cls = int.class;
        if (cls.equals(Long.class))
            cls = long.class;
        if (cls.equals(Short.class))
            cls = short.class;
        if (cls.equals(Float.class))
            cls = float.class;
        if (cls.equals(Double.class))
            cls = double.class;
        return cls;
    }

    public static PropertyEditor findEditor(Class clazz) {
        Object ped = propEdMap.get(clazz.getName());
        if(ped != null) {
            if(ped.equals("NULL"))
                return null;
            return (PropertyEditor) ped;
        }
        ped = PropertyEditorManager.findEditor(clazz);
        if(ped == null) {
            propEdMap.put(clazz.getName(), "NULL");
            return null;
        }            
        propEdMap.put(clazz.getName(), ped);
        return (PropertyEditor) ped;
    }

    public static Object[] getPropertyAndType(Object object, String propertyName)
            throws Exception {
        Field field = null;
        try {
            field = object.getClass().getField(propertyName);
            if (field != null)
                return new Object[] { field.get(object), field.getType() };
        } catch (NoSuchFieldException e) { //do nothing - it's not a field!
        } catch (Exception e) {
            System.err.println("ERROR GETTING FIELD " + propertyName + ", "
                    + object);
            e.printStackTrace();
            return null;
        }

        Method meth = getGetter(object.getClass(), propertyName);
        if(meth == null)
        	return null;
        return new Object[] { meth.invoke(object, null), meth.getReturnType() };
    }

    public static Object convertToPropertySource(Object ob, FormComponent comp,
            Object id) {
        if (ob == null)
            return null;
        if (ob instanceof String || ob instanceof Integer 
                || ob instanceof Long  || ob instanceof Short
                || ob instanceof Boolean || ob instanceof Double
                || ob instanceof Float)
            return ob;
        Object wrapper = WrapperFactory.createWrapper(id, ob, comp);
        if (wrapper != null)
            return wrapper;
        if (jiglooPlugin.canUseSwing()) {
            if (ob instanceof Dimension) {
                Dimension dim = (Dimension) ob;
                Point p = new Point(dim.width, dim.height);
                return new SizePropertySource(p, comp, (String) id);
            }
            if (ob instanceof Insets) {
                Insets insets = (Insets) ob;
                return new InsetsPropertySource(insets);
            }
            if (ob instanceof Border) {
                Border border = (Border) ob;
                return new BorderPropertySource(border, comp, (String) id, comp);
            }
            if (ob instanceof java.awt.Rectangle) {
                java.awt.Rectangle arec = (java.awt.Rectangle) ob;
                Rectangle rec = new Rectangle(arec.x, arec.y, arec.width,
                        arec.height);
                return new RectanglePropertySource(rec, comp, (String) id);
            }
        }

        if (ob instanceof Point) {
            Point p = (Point) ob;
            return new SizePropertySource(p, comp, (String) id);
        }
        if (ob instanceof Rectangle) {
            Rectangle rec = (Rectangle) ob;
            return new RectanglePropertySource(rec, comp, (String) id);
        }

        return ob;
    }

    public static FormPropertyDescriptor getPropertyDescriptor(String name,
            Object prop, Class type, FormComponent comp) {
        return getPropertyDescriptor(name, prop, type, comp, comp);
    }

    public static FormPropertyDescriptor getPropertyDescriptor(String name,
            Object prop, Class type, FormComponent comp, IPropertySource propSrc) {
        
        FormPropertyDescriptor pd = null;

        if (prop instanceof FieldWrapper) {
            FieldWrapper fw = (FieldWrapper) prop;
            pd = fw.createPropertyDescriptor(name, comp);
            pd.setPropertySource(propSrc);
        } else if (name.equals("cursor")) {
            if (Cursor.class.equals(type))
                pd = new SWTCursorPropertyDescriptor("cursor", "cursor", comp);
        } else if (prop == null) {
            if (type != null) {
                if (Icon.class.equals(type))
                    type = IconWrapper.class;
                pd = getPropertyDescriptor(name, type, comp, propSrc);
            } else {
                pd = new FormPropertyDescriptor(name, name, comp, propSrc);
            }
        } else if (prop instanceof FormComponent) {
            type = ((FormComponent) prop).getMainClass();
        } else if (prop instanceof ClassWrapper) {
            type = ((ClassWrapper) prop).getWrapperClass();
            pd = new ClassPropertyDescriptor(name, name, comp, type);
        } else if (prop != null) {
            type = prop.getClass();
        }
        if(pd == null)
            pd = getPropertyDescriptor(name, type, comp, propSrc);
        setCategory(comp, pd, name, null);
        return pd;
    }

    public static FormPropertyDescriptor getPropertyDescriptor(String name,
            Class prop, FormComponent comp, IPropertySource propSrc) {

        FormPropertyDescriptor pd;
        String displayName = name;
        if (prop.equals(FontWrapper.class)
                || (jiglooPlugin.canUseSwing() && prop
                        .equals(java.awt.Font.class))) {
            pd = new FontPropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(KeyStrokeWrapper.class)) {
            pd = new KeyStrokePropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(LocaleWrapper.class)) {
            pd = new LocalePropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(ColorWrapper.class)) {
            pd = new ColorPropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(ImageWrapper.class)) {
            pd = new ImagePropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(IconWrapper.class)) {
            pd = new ImagePropertyDescriptor(name, displayName, comp);
            ((ImagePropertyDescriptor)pd).setForIcon(true);
        } else if (prop.equals(InsetsPropertySource.class)) {
            pd = new InsetsPropertyDescriptor(name, displayName, comp, propSrc);
        } else if (prop.equals(SizePropertySource.class)) {
            pd = new SizePropertyDescriptor(name, displayName, comp, propSrc);
        } else if (prop.equals(RectanglePropertySource.class)) {
            pd = new RectanglePropertyDescriptor(name, displayName, comp,
                    propSrc);
        } else if (jiglooPlugin.canUseSwing()
                && prop.equals(AWTCursorWrapper.class)) {
            pd = new AWTCursorPropertyDescriptor(name, displayName, comp);
        } else if (jiglooPlugin.canUseSwing()
                && prop.equals(MnemonicWrapper.class)) {
            pd = new MnemonicPropertyDescriptor(name, displayName, comp);
            
        } else if ("valueObjectClassName".equals(name) 
                && comp.getClassName().equals("org.openswing.swing.client.GridControl")) {
              pd = new GridVOPropertyDescriptor(name, displayName, comp);
              
        } else if (jiglooPlugin.canUseSwing()
                && (prop.equals(Border.class) || prop
                        .equals(BorderPropertySource.class))) {
            pd = BorderPropertySource.getBorderDesc(name, comp);
        } else if (prop.equals(Boolean.class)) {
            pd = new BooleanPropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(String.class)) {
            pd = new TextFormPropertyDescriptor(name, displayName, comp);

        } else if (prop.equals(StringArrayWrapper.class)
                || prop.equals(String[].class)) {
            pd = new StringArrayPropertyDescriptor(name, displayName, comp,
                    comp);

        } else if (prop.equals(DoubleArrayWrapper.class)
                || prop.equals(double[].class)) {
            pd = new DoubleArrayPropertyDescriptor(name, displayName, comp,
                    comp);
        } else if (prop.equals(IntegerArrayWrapper.class)
                || prop.equals(int[].class)) {
            pd = new IntegerArrayPropertyDescriptor(name, displayName, comp,
                    comp);
        } else if (prop.equals(FormComponentArrayWrapper.class)
                || prop.equals(FormComponent[].class)
                || prop.equals(Control[].class)  ) {
            pd = new FormComponentArrayPropertyDescriptor(name, displayName, comp, comp);

        } else if (jiglooPlugin.canUseSwing() 
                && jiglooPlugin.getJavaVersion() > 13
                && ( Cacher.isAssignableFrom(FocusTraversalPolicy.class, prop)
                        || prop.equals(FocusTraversalPolicyWrapper.class)) ) {
            pd = new FocusTraversalPolicyPropertyDescriptor(name, displayName, comp, comp);
        
        } else if ("mnemonic".equals(name)) {
//          if(prop.equals(Integer.class))
              pd = new MnemonicPropertyDescriptor(name, displayName, comp);
              
        } else if (prop.equals(SWTCursorWrapper.class)) {
            pd = new SWTCursorPropertyDescriptor(name, displayName, comp);
        } else if (jiglooPlugin.canUseSwing() && prop.equals(KeyStroke.class)) {
            pd = new KeyStrokePropertyDescriptor(name, displayName, comp);
        } else if (prop.equals(Integer.class) || prop.equals(Float.class)
                || prop.equals(Short.class)  || prop.equals(Long.class)
                || prop.equals(Double.class)) {
            pd = new NumberPropertyDescriptor(name, displayName, prop, comp);

        } else if (jiglooPlugin.ACCOMODATE_ALL_CLASS_PROPERTIES
                && !"layout".equals(name)
                && !"layoutData".equals(name)
                //we don't want user to be setting menu, control
                //or parent for SWT widgets
                && !(comp.isSWT() && ("menu".equals(name)
                        || "parent".equals(name) || "control".equals(name)))
        //          		&& !Object.class.equals(prop)
        ) {
            pd = new ClassPropertyDescriptor(name, displayName, comp, prop);

        } else {
            pd = new FormPropertyDescriptor(name, displayName, comp);
        }
        
        setCategory(comp, pd, name, null);
        
        pd.setPropertySource(propSrc);
        return pd;
    }

    /**
     * @param comp
     * @param pd
     * @param name
     */
    public static void setCategory(FormComponent comp, FormPropertyDescriptor pd, String name, String defaultCat) {
        
        if(pd == null || !jiglooPlugin.propertyCategoriesEnabled())
            return;
        
        if(pd.getCategory() != null)
            return;
        
        if(comp != null) {
            String cat = comp.getPropertyCategory(name);
            if(cat == null) {
                if(defaultCat != null)
                    cat = defaultCat;
                else
                    cat = jiglooPlugin.getPropertyExpertCategory();
            }
            pd.setCategory(cat);
        }
    }

}