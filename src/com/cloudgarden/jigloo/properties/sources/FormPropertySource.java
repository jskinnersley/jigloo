/*
 * Created on Jun 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties.sources;

import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.LayoutManager;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.eval.ArrayHolder;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.ISWTDisposableWrapper;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.properties.descriptors.ChoicePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.CustomEditorDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.TextFormPropertyDescriptor;
import com.cloudgarden.jigloo.util.ConversionUtils;
import com.cloudgarden.jigloo.util.DefaultValueManager;
import com.cloudgarden.jigloo.util.FieldManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
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
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.StringArrayWrapper;
import com.cloudgarden.jigloo.wrappers.WrapperFactory;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormPropertySource implements IFormPropertySource {

	protected FormComponent comp;

	protected Node node;
	protected Object object;
	protected Class mainClass;
	protected Vector synthProps;
	protected HashMap properties;
	protected HashMap propertyTypes, defaultPropValues;

	protected boolean propertiesInited = false;
	protected IPropertyDescriptor[] propertyDescriptors;
	protected Vector propsNeedingCodeUpdate = new Vector();
	protected Vector propNames;
	protected Vector fieldNames;
	protected Vector allNames;
	protected Vector setProps = new Vector();
	protected String name = null;

	public FormPropertySource() {}

	public FormPropertySource(FormComponent comp) {
		this.comp = comp;
	}

	public FormPropertySource(FormComponent comp, Node node, Object object) {
		this(comp);
		this.node = node;
		setObject(object);
	}

	public FormPropertySource(FormComponent comp, Object object) {
		this(comp);
		setObject(object);
	}

	public void setFormComponent(FormComponent comp) {
		this.comp = comp;
	}

	public FormComponent getFormComponent() {
		return comp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void dispose() {
		mainClass = null;
		object = null;
		comp = null;
		if (properties != null) {
			Iterator it = properties.keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				Object val = properties.get(name);
				if (val instanceof IFormPropertySource) {
					((IFormPropertySource) val).dispose();
				}
			}
			properties.clear();
		}
		propertyDescriptors = null;
	}
	
	public String getNameInCode() {
		String name = getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0)
			name = name.substring(pos + 1);
		return name;
	}

	public FormPropertySource getCopy(FormComponent comp) {
		FormPropertySource fps = new FormPropertySource(comp, object);
		if (properties != null) {
			HashMap atts = new HashMap();
			Iterator it = properties.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				atts.put(key, properties.get(key));
			}
			fps.setProperties(atts);
		}
		return fps;
	}

	public void setMainClass(Class cls) {
	    mainClass = cls;
	}
	
	public Class getMainClass() {
	    if(mainClass != null)
	        return mainClass;
	    return getObject().getClass();
	}
	
	public void setObject(Object o) {
		boolean changed = false;
		if ((o == null && object != null) || (o != null && object == null) || (o != null && !o.equals(object)))
			changed = true;
		setObject(o, changed);
	}

	public void setObject(Object o, boolean changed) {
		if (changed) {
			propNames = null;
			propertyDescriptors = null;
//			setProps.removeAllElements();
		}
		object = o;
		if (object == null)
			return;
		if (changed)
			initProperties();
	}

	public Object getObject() {
		//System.out.println("getObject " + object);
		return object;
	}

	public Object[] convertToAWT(Object[] params) {
		if (params == null)
			return null;
		for (int i = 0; i < params.length; i++) {
			params[i] = convertToAWTValue(params[i]);
		}
		return params;
	}

	public Object convertToAWTValue(Object value) {
		if (value instanceof Point) {
			Point pt = (Point) value;
			value = new Dimension(pt.x, pt.y);
		}
		if (value instanceof Rectangle) {
			Rectangle rec = (Rectangle) value;
			value = new java.awt.Rectangle(rec.x, rec.y, rec.width, rec.height);
		}
		if (value instanceof Font) {
			Font val = (Font) value;
			FontData fd = val.getFontData()[0];
			value = new java.awt.Font(fd.getName(), fd.getStyle(), fd.getHeight());
		}
		if (value instanceof Color) {
			Color val = (Color) value;
			value = new java.awt.Color(val.getRed(), val.getGreen(), val.getBlue());
		}
		return value;
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return getPropertyDescriptors(this);
	}

	public Class getPropType(String propName) {
		if (propertyTypes == null)
			return Object.class;
		return (Class) propertyTypes.get(propName);
	}

	public IPropertyDescriptor[] getPropertyDescriptors(IFormPropertySource propSrc) {
		getObject();
		//in LayoutWrapper, if getObject returns a different class of object 
		//from previous time, properties etc are nulled and need to be re-inited.
		//System.out.println(
		//"getPropDescs " + this.getClass() + ", props=" + properties);
		if(beanPropDescs.size() != 0) {
			propertyDescriptors = new IPropertyDescriptor[beanPropDescs.size()];
			Iterator it = beanPropDescs.keySet().iterator();
			int i=0;
			while(it.hasNext()) {
				String name = (String) it.next();
				IPropertyDescriptor pd = null;
				PropertyEditor ped = null;
//                System.err.println("GET PROP DESC = "+name+", " + this);
                    PropertyDescriptor bpd = (PropertyDescriptor) beanPropDescs.get(name);
//                    System.err.println("BEAN PROP DESC = " + bpd + ", " + this);
                    ped = (PropertyEditor) beanPropEds.get(name);
                    String pname = bpd.getName();
                    if (ped != null) {
                        String[] tags = ped.getTags();
                        if (tags != null) {
                            pd = new ChoicePropertyDescriptor(pname, pname,
                                    comp, tags, this);
                        } else if(jiglooPlugin.canUseSwing() && ped.supportsCustomEditor()) {
                            pd = new CustomEditorDescriptor(pname, pname, ped, comp);
//                            pd = new CustomFormPropertyDescriptor(pname, pname, comp, ped, this);
                        } else {
                            pd = new TextFormPropertyDescriptor(pname, pname,
                                    comp);
                        }
                        propertyDescriptors[i++] = pd;
                    } else {
    					propertyDescriptors[i++] = getPropertyDescriptor(propSrc, name);
//                        jiglooPlugin.writeToLog("No property editor for "
//                                + getNameInCode() + "." + name);
                    }
                }
			return propertyDescriptors;
		}
		Vector names = getPropertyNames();
		if (propertyDescriptors != null)
			return propertyDescriptors;
		int cnt = names.size();
		propertyDescriptors = new IPropertyDescriptor[cnt];
		try {
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.elementAt(i);

				IPropertyDescriptor pd = null;
				PropertyEditor ped = null;

                if(ped == null) {
					propertyDescriptors[i] = getPropertyDescriptor(propSrc, name);
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new IPropertyDescriptor[0];
		}
		return propertyDescriptors;
	}

	private IPropertyDescriptor getPropertyDescriptor(IFormPropertySource propSrc, String pname) {
		Object prop = getPropertyValue(pname);
		Class type = null;
		if (propSrc instanceof FormPropertySource) {
			FormPropertySource fps = (FormPropertySource) propSrc;
			type = fps.getPropType(pname);
		} else {
			type = comp.getPropType(pname);
		}
		return PropertySourceFactory.getPropertyDescriptor(pname, prop, type, comp, propSrc);
	}
	
	public Vector getPropertyNameVector() {
		return allNames;
	}

	public Vector getPropertyNames() {
		//System.out.println("getPropertyNames " + properties);
		if (properties == null)
			initProperties();
		if (allNames == null)
			return new Vector();
		//return new String[0];
		removeHiddenProps();
		addSynthProps();
		return allNames;
		/*
		String[] names = new String[allNames.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = (String) allNames.elementAt(i);
		return names;
		*/

		/*
		Iterator it = properties.keySet().iterator();
		String[] names = new String[properties.size()];
		int i = 0;
		while (it.hasNext()) {
			names[i++] = (String) it.next();
			System.out.println("Got prop name " + names[i - 1]);
		}
		return names;
		*/
	}

	public HashMap getProperties() {
		return properties;
	}

	/**
	 * replaces a with b in str
	 */
	public static String replace(String a, String b, String str) {
		int pos = 0;
		String ret = "";
		while ((pos = str.indexOf(a)) >= 0) {
			ret += str.substring(0, pos) + b;
			str = str.substring(pos + a.length());
		}
		ret += str;
		return ret;
	}

	public boolean isSyntheticProperty(String propName) {
		if (synthProps == null)
			return false;
		return synthProps.contains(propName);
	}

	public void addSynthProps() {}
	
	public void removeHiddenProps() {}

	public void addSynthProperty(String propName, Class propClass, Object defVal) {
		if (synthProps == null)
			synthProps = new Vector();
		
		if(!allNames.contains(propName))
		    allNames.add(propName);
		if(synthProps.contains(propName))
		    return;		
//		if(!synthProps.contains(propName))
		synthProps.add(propName);
		propertyTypes.put(propName, propClass);
		putProperty(propName, defVal);
		defaultPropValues.put(propName, defVal);
	}

	public String getJavaCodeForProps(String objName, boolean isSwing, IJavaCodeManager jcm) {
		if (properties == null || allNames == null) {
			return "";
		}
		String code = "";
		for (int i = 0; i < allNames.size(); i++) {
			String id = (String) allNames.elementAt(i);
			if (isPropertySet(id) && !isSyntheticProperty(id)) {
				Object value = getPropertyValue(id);
				if ("null".equals(value) && !propertyTypes.get(id).equals(String.class)) {
					value = null;
				}
				boolean isField = isField(id);
				code += getJavaCodeForProperty(id, objName, value, isField, isSwing, 
				        false, true, null, jcm);
			}
		}
		return code;
	}

	public static String getJavaCodeForProperty(
		String id,
		String instName,
		Object value,
		boolean isField,
		boolean isSwing,
		boolean isFrame,
		boolean addQuotes,
		Widget widget,
		IJavaCodeManager jcm) {
		try {
			String preCode = "";
			if (isFrame && id.equals("preferredSize")) {
				id = "size";
			}
			String tmpCode = "\t\t";
			if (isField) {
				tmpCode += instName + "." + id + " = ";
			} else {
				tmpCode += instName + ".set" + id.substring(0, 1).toUpperCase() + id.substring(1) + "(";
			}
			if (value == null) {
				tmpCode += "null";
			} else if (value instanceof String) {
				if(addQuotes) {
					String val = value.toString();
					val = replace("\n", "\\n", val);
					val = replace("\t", "\\t", val);
					val = replace("\"", "\\\"", val);
					tmpCode += "\"" + val + "\"";
				} else {
				    tmpCode += (String)value;
				}
			} else if (value instanceof Boolean) {
				tmpCode += value.toString();
			} else if (value instanceof FormComponentWrapper) {
				tmpCode += value.toString();
			} else if (value instanceof Integer || value instanceof Double) {
				tmpCode += value;
			} else if (value instanceof Float) {
				tmpCode += value + "f";
			} else if (value instanceof LayoutDataWrapper) {
				LayoutDataWrapper ldw = (LayoutDataWrapper) value;
				tmpCode += ldw.getJavaCode(null, isSwing, jcm);
			} else if (value instanceof IWrapper) {
				tmpCode += ((IWrapper) value).getJavaConstructor(jcm);

			} else if (value instanceof FieldWrapper) {
				FieldWrapper fw = (FieldWrapper) value;
				if (jiglooPlugin.useImports()) {
					tmpCode += fw.getFieldAsString();
					fw.addRequiredImport(jcm);
				} else {
					tmpCode += fw.getFullFieldName();
				}
			} else {

				if (isSwing) {
					if (value instanceof FontWrapper) {
						FontWrapper fw = (FontWrapper) value;
						tmpCode += "new java.awt.Font(\""
							+ fw.getName()
							+ "\","
							+ fw.getStyle()
							+ ","
							+ fw.getSize()
							+ ")";
					} else if (value instanceof ImageWrapper) {
						tmpCode += ((ImageWrapper) value).getSwingConstructor(jcm);
					} else if (value instanceof IconWrapper) {
						tmpCode += ((IconWrapper) value).getSwingConstructor(jcm);
					} else if (value instanceof ColorWrapper) {
						ColorWrapper cw = (ColorWrapper) value;
						tmpCode += "new java.awt.Color(" + cw.getRed() + "," + cw.getGreen() + "," + cw.getBlue() + ")";
					} else if (value instanceof SizePropertySource) {
						SizePropertySource sps = (SizePropertySource) value;
						tmpCode += "new java.awt.Dimension(" + sps.getValue().x + "," + sps.getValue().y + ")";
					} else if (value instanceof RectanglePropertySource) {
						RectanglePropertySource sps = (RectanglePropertySource) value;
						tmpCode += "new java.awt.Rectangle("
							+ sps.getValue().x
							+ ","
							+ sps.getValue().y
							+ ","
							+ sps.getValue().width
							+ ","
							+ sps.getValue().height
							+ ")";
					} else if (value instanceof InsetsPropertySource) {
						InsetsPropertySource sps = (InsetsPropertySource) value;
						tmpCode += "new java.awt.Insets("
							+ sps.getValue().top
							+ ","
							+ sps.getValue().left
							+ ","
							+ sps.getValue().bottom
							+ ","
							+ sps.getValue().right
							+ ")";
					} else {
						tmpCode += "new " + value.getClass().getName() + "()";
					}
				} else { //SWT
					if (value instanceof SizePropertySource) {
						SizePropertySource sps = (SizePropertySource) value;
						Point pt = sps.getValue();
						if (id.equals("size") && widget != null)
							pt = adjustSize(pt, widget);
						tmpCode += "new org.eclipse.swt.graphics.Point(" + pt.x + "," + pt.y + ")";
					} else if (value instanceof RectanglePropertySource) {
						RectanglePropertySource sps = (RectanglePropertySource) value;
						tmpCode += "new org.eclipse.swt.graphics.Rectangle("
							+ sps.getValue().x
							+ ","
							+ sps.getValue().y
							+ ","
							+ sps.getValue().width
							+ ","
							+ sps.getValue().height
							+ ")";
					} else if (value instanceof ISWTDisposableWrapper) {
						preCode = "\t\t" + ((ISWTDisposableWrapper) value).getSWTDeclaration(instName + id, jcm) + "\n";
						tmpCode += instName + id;
					} else {
						tmpCode += "new " + value.getClass().getName() + "()";
					}
				}
			}
			if (isField) {
				tmpCode += ";\n";
			} else {
				tmpCode += ");\n";
			}
			return preCode + tmpCode;
		} catch (Throwable t) {
			jiglooPlugin.handleError(t);
			return "";
		}
	}

	private static Point adjustSize(Point size, Widget widget) {
		//System.out.println("ADJUST SIZE " + size + " for " + widget);
		if (size == null)
			return null;
		if (widget instanceof Scrollable) {
			size = new Point(size.x, size.y);
			Scrollable scr = (Scrollable) widget;
			Point cSize = scr.computeSize(size.x, size.y);
			size.x -= cSize.x - size.x;
			size.y -= cSize.y - size.y;
		} //System.out.println("ADJUSTED SIZE to " + size + " for " + widget);
		return size;
	}

	public void setProperties(HashMap props) {
		properties = props;
	}

	private Object getCustomPropertyValue(Object id, boolean asText) {
        if (beanPropDescs.containsKey(id)) {
            PropertyEditor ped = null;
            try {
                ped =(PropertyEditor) beanPropEds.get(id);
                if (ped == null)
                    return null;
                if (asText)
                    return ped.getAsText();
                else
                    return ped.getValue();
            } catch (Throwable e) {
                System.err.println("ERROR GETTING CUSTOM PROP "+id+", "+asText+", "+ped);
                jiglooPlugin.handleError(e);
            }
        }
        return null;
    }

	public Object getPropertyValue(Object id) {
	    return getPropertyValue(id, true);
	}

	public Object getPropertyValue(Object id, boolean asText) {
		if (properties == null)
			return null;
		
        Object ob = null;
        //need to get the custom property from the custom editors,
        //because these will convert numbers into fields, for example.
        ob = getCustomPropertyValue(id, asText);
        if (ob != null) {
            return ob;
        }
        
		ob = properties.get((String) id);

		if (ob == null || ob.equals("null")) {
		    
		    Class propType = getPropType((String) id);
		    
			if (ButtonGroup.class.equals(propType)) {
				return new ClassWrapper((String) null, comp, ButtonGroup.class);
			}
			if (double[].class.equals(propType)) {
				return new DoubleArrayWrapper((double[]) null, comp);
			}
			if (String[].class.equals(propType)) {
				return new StringArrayWrapper((String[]) null, comp);
			}
			if (int[].class.equals(propType)) {
				return new IntegerArrayWrapper((int[]) null, comp);
			}
			if (propType != null &&  Cacher.isAssignableFrom(Object[].class, propType)) {
			    return new FormComponentArrayWrapper((int[]) null, comp);
			}
			if (propType != null 
			        && jiglooPlugin.canUseSwing() 
			        && jiglooPlugin.getJavaVersion() > 13
			        &&  Cacher.isAssignableFrom(FocusTraversalPolicy.class, propType)) {
			    return new FocusTraversalPolicyWrapper((FormComponent[]) null, comp);
			}
		}
		Object propSrc = PropertySourceFactory.convertToPropertySource(ob, comp, id);
		return propSrc;
	}

	protected HashMap beanPropEds = new HashMap();
	protected HashMap beanPropDescs = new HashMap();
	
	protected String getJavaCustomPropertyCode(String pName) {
	    PropertyEditor ped = (PropertyEditor) beanPropEds.get(pName);
	    if(ped == null)
	        return null;
	    return ped.getJavaInitializationString();
	}
	
	protected void initCustomProperties(Class mainClass, boolean throwErrors) throws Throwable {
        try {
        	IProject proj = jiglooPlugin.getActiveEditor().getProject();
            BeanInfo beanInfo = BeanHandler.getBeanInfo(mainClass, proj, throwErrors);
            if (beanInfo == null)
                return;
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
//            System.err.println("GOT BEANINFO "+beanInfo+" for "+mainClass+", "+pds);
            if (pds != null) {
                for (int i = 0; i < pds.length; i++) {
                    PropertyDescriptor pd = pds[i];
                    String name = pd.getName();
                    Class pec = pd.getPropertyEditorClass();
                    beanPropDescs.put(name, pd);
//                    System.err.println("ADDING PROPERTY DESC " + pec
//                    + ", " + name + ", " + pd);
                    if (pec != null) {
                        if(!beanPropEds.containsKey(name)) {
	                        PropertyEditor ped = (PropertyEditor) pec.newInstance();
	                        beanPropEds.put(name, ped);
//	                        System.err.println("ADDING PROPERTY EDITOR " + pec
//	                                + ", " + name + ", " + ped);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            jiglooPlugin.handleError(e);
            if (throwErrors)
                throw e;
        }
    }

	protected void initProperties() {
//		System.err.println("initProperties " + properties + ", " + getObject()+", "+hashCode()+", "+isSet());
//		new Exception().printStackTrace();

//		if (object == null
		if (getObject() == null
//		        && !isSet()
		        )
			return;

		if (propertyTypes == null)
			propertyTypes = new HashMap();
		if (defaultPropValues == null)
			defaultPropValues = new HashMap();
		boolean readNodeValues = false;
		if (properties == null) {
			readNodeValues = true;
			properties = new HashMap();
		}
		propertiesInited = false;
		if (propNames == null) {
			propNames = PropertySourceFactory.findPropertyNames(getMainClass());
			fieldNames = PropertySourceFactory.findFieldNames(getMainClass());
			allNames = new Vector();
			if (propNames != null)
				allNames.addAll(propNames);
			if (fieldNames != null)
				allNames.addAll(fieldNames);
		}
		removeHiddenProps();
		addSynthProps();
//		if(hiddenProps != null) {
//			for(int i=0;i<hiddenProps.size();i++) {
//			    allNames.remove(hiddenProps.elementAt(i));
//			}
//		}
		for (int i = 0; i < allNames.size(); i++) {
			String name = (String) allNames.elementAt(i);
			if (isSyntheticProperty(name))
				continue;
			Object[] valType = null;
			try {
				valType = PropertySourceFactory.getPropertyAndType(getObject(), name);
				if (valType == null) {
					System.err.println("Error initing property " + name + " for " + getObject());
					continue;
				}
				Object def = DefaultValueManager.getDefault(name, getObject());
				valType[0] = preConvertPropertyValue(name, valType[0]);
				Object value = valType[0];
				if (value == null)
					value = "null";
				putProperty(name, value);
//                System.err.println("INIT PROP " + name + " to " + value + ", " + this);
				defaultPropValues.put(name, def);
				//System.err.println("GOT DEF VAL "+valType[0]+" for "+name+", this="+this);
				propertyTypes.put(name, valType[1]);
				
				if (beanPropDescs.containsKey(name)) {
		            try {
		                PropertyEditor ped = (PropertyEditor) beanPropEds.get(name);
		                if (ped != null) {
//			                System.err.println("INIT PROP ED VALUE "+value+", "
//			                        +value.getClass()+", "+name+", "+ped);
		                    try {
		                        if (value instanceof String) {
		                            ped.setAsText((String) value);
		                            if(!value.equals(ped.getValue()))
		                                ped.setValue(value);
		                        } else {
		                            ped.setValue(value);
		                        }
		                    } catch(Throwable t) {}
		                    value = ped.getValue();
//			                System.err.println("INIT PROP ED VALUE - GOT - "+ped.getValue());
		                }
		            } catch (Exception e) {
		                jiglooPlugin.handleError(e, "Error setting custom property "
		                        + name + " to " + value + ", for " + this);
		            }
		        }
				
			} catch (Exception e) {
				System.err.println("ERROR IN INIT PROPERTIES " + name);
				e.printStackTrace();
			}
		}
//		addSynthProps();

		//		if (synthProps != null) {
		//			Iterator it = synthProps.keySet().iterator();
		//			while (it.hasNext()) {
		//				Object key = it.next();
		//				allNames.add(key);
		//				propertyTypes.put(key, synthProps.get(key).getClass());
		//				putProperty(key, synthProps.get(key));
		//				defaultPropValues.put(key, synthProps.get(key));
		//			}
		//		}
		if (readNodeValues)
			initPropertiesFromNode();
		propertyDescriptors = null;
		propertiesInited = true;
	}

	/**
	 * Sets the properties HashMap value, but also sets the instance's
	 * field or property value - initProperties reads back this value from
	 * the instance (otherwise value from Node would be lost). Should
	 * find a better way.
	 */
	public void initPropertiesFromNode() {
		//System.out.println("initFromNode " + node + ", " + comp);
		if (properties == null)
			properties = new HashMap();
		if (node == null)
			return;
		NodeList list = NodeUtils.getChildrenOfNode("Properties", node);
		if (list == null)
			list = node.getChildNodes();
		if (list != null) {
			for (int i = 0; i < list.getLength(); i++) {
				Node n = (Node) list.item(i);
				if ("Property".equals(n.getNodeName())) {
					String name = NodeUtils.getAttribute("name", n);
					Object prop = NodeUtils.getPropertyFromNode(n, comp);
					if (prop != null) {
						//System.out.println("setting prop "+ name
						//		+ " to "+ prop+ " "+ prop.getClass()+", "+this);
						setPropertyValue(name, prop);
					}
				}
			}
		}
	}

	public void initPropertiesFromNodeAttributes() {
		//System.out.println("initFromNodeAtts");
		if (properties == null)
			properties = new HashMap();
		if (node == null)
			return;
		NodeList list = node.getChildNodes();
		//NodeList list = NodeUtils.getChildrenOfNode("Properties", node);
		if (list != null) {
			for (int i = 0; i < list.getLength(); i++) {
				Node n = (Node) list.item(i);
				String name = NodeUtils.getAttribute("name", n);
				Object prop = NodeUtils.getPropertyFromNode(n, comp);
				//prop = preConvertPropertyValue(name, prop);
				//System.out.println("setting prop " + name + " to " + prop);
				if (prop != null)
					putProperty(name, prop);
				//setPropertyValue(name, prop);
			}
		}
	}

	public boolean isSet() {
		if (allNames == null)
			return false;
		for (int i = 0; i < allNames.size(); i++)
			if (isPropertySet(allNames.elementAt(i)))
				return true;
		return false;
	}

	public boolean isPropertySet(Object id) {
//		if (true)
		if (defaultPropValues == null)
			return setProps.contains(id);
//		if(!setProps.contains(id))
//		return false;
		//return true;
		Object def = defaultPropValues.get(id);
		Object val = getPropertyValue(id);
		def = ConversionUtils.convertToRawValue(def, null);
		val = ConversionUtils.convertToRawValue(val, null);
		if (def == null && val == null)
			return false;
		//if (def != null)
			return !JiglooUtils.equals(def, val);
		//return setProps.contains(id);
	}

	private Object preConvertPropertyValue(Object name, Object value) {

		Object wrapper = WrapperFactory.createWrapper(name, value, comp);
		if (wrapper != null)
			return wrapper;

		if (value instanceof java.awt.Point) {
			java.awt.Point pt = (java.awt.Point) value;
			value = new Point(pt.x, pt.y);
		} else if (value instanceof java.awt.Rectangle) {
			java.awt.Rectangle rec = (java.awt.Rectangle) value;
			value = new Rectangle(rec.x, rec.y, rec.width, rec.height);
		} else if (value instanceof Dimension) {
			Dimension dim = (Dimension) value;
			value = new Point(dim.width, dim.height);
		} else if (
//		        value instanceof Integer && 
		        object != null) {
			String[] fieldOpts = FieldManager.getFieldOptions((String) name, object.getClass());
			if (fieldOpts != null) {
				if (value instanceof FieldWrapper) {
					((FieldWrapper) value).setFields(fieldOpts);
				} else {
					value = new FieldWrapper(value, (String) name, fieldOpts, comp);
					((FieldWrapper) value).setMainObject(this);
				}
			}
		}
		return value;
	}

	public void reset() {
		if (allNames == null)
			return;
		for (int i = 0; i < allNames.size(); i++) {
			Object id = allNames.elementAt(i);
			resetPropertyValue(id);
		}
	}

	public void resetPropertyValue(Object id) {
		if (!isPropertySet(id))
			return;
		setPropertyValue(id, defaultPropValues.get(id));
		setProps.remove(id);
	}

	public boolean isField(Object propName) {
		if (fieldNames == null)
			return false;
		return fieldNames.contains(propName);
	}

	public static Object convertIfNeeded(Object propName, Object value, Class type, FormComponent comp) {
		if (value == null)
			return null;
		if (type == null)
			return value;
		try {

			if ( Cacher.isAssignableFrom(type, value.getClass()))
				return value;

			if (value instanceof FontWrapper
				&& ( Cacher.isAssignableFrom(java.awt.Font.class, type) ||  Cacher.isAssignableFrom(Font.class, type)))
				return value;

			if (value instanceof ColorWrapper
				&& ( Cacher.isAssignableFrom(java.awt.Color.class, type) ||  Cacher.isAssignableFrom(Color.class, type)))
				return value;

			if (type.equals(Control.class) && value instanceof String) {
				FormComponent fc = comp.getEditor().getComponentByName(value.toString());
				if (fc != null)
					value = new FormComponentWrapper(fc);
				return value;
			}
			if (type.equals(Integer.class) || type.equals(int.class))
				return new Integer(value.toString());
			if (type.equals(Double.class) || type.equals(double.class))
				return new Double(value.toString());
			if (type.equals(Float.class) || type.equals(float.class))
				return new Float(value.toString());
			if (type.equals(Boolean.class) || type.equals(boolean.class))
				return new Boolean(value.toString());
		} catch (Exception e) {
			return value;
		}
		if (value instanceof IWrapper)
			return value;
		if (value instanceof IPropertySource) {
			return value;
		}
		if (value instanceof FormComponentWrapper) {
			return value;
		}
		//		System.err.println(
		//			"Unable to convert "
		//				+ value.getClass()
		//				+ " to "
		//				+ type
		//				+ " for "
		//				+ propName);
		return value;
	}

	public boolean hasProperty(String id) {
		if (properties == null)
			return false;
		if (allNames == null)
			return false;
		//return properties.containsKey(id);
		return allNames.contains(id);
	}

	public void copyPropertiesFrom(FormPropertySource fps) {
		propNames = (Vector) fps.getPropertyNames().clone();
		propertyDescriptors = null;
		HashMap props = fps.getProperties();
		
//		if (props != null && propNames != null) {
//		for(int i=0;i<propNames.size();i++) {
//		Object key = propNames.elementAt(i);

			if (props != null) {
//			    IPropertyDescriptor[] pds = fps.getPropertyDescriptors();
//			    for(int i=0;i<pds.length;i++) {
//			        Object key = pds[i].getId();
			        
			    Object[] keys = props.keySet().toArray();
			    for (int i = 0; i < keys.length; i++) {
			        Object key = keys[i];
		
//				Iterator it = props.keySet().iterator();
//				while (it.hasNext()) {
//				Object key = it.next();
		
				Object val = FormComponent.copyObject(props.get(key), key, comp);
//				System.err.println("copy fp obj "+this+", "+key+", "+val+", "+comp);
				setPropertySet((String) key, fps.isPropertySet(key));
				setPropertyValue(key, val, false);
				if (propNames == null) {
					if (jiglooPlugin.DEBUG_EXTRA)
						System.err.println("PROP NAMES SET TO NULL " + key + ", " + val + ", " + this);
					break;
				}
			}
		}
		beanPropDescs = fps.beanPropDescs;
		beanPropEds = fps.beanPropEds;
	}

	public void setPropertySet(String propName, boolean set) {
		if (set) {
			if (!setProps.contains(propName))
				setProps.add(propName);
		} else {
			setProps.remove(propName);
		}
	}

	public void setPropertyValue(Object propName, Object value) {
		setPropertyValue(propName, value, true);
	}

	public void setPropertyValue(Object propName, Object value, boolean updateCode) {
		if (value == null)
			return;
        
		if (beanPropDescs.containsKey(propName)) {
            try {
                PropertyEditor ped = (PropertyEditor) beanPropEds.get(propName);
                if (ped != null) {
                    Object oldValue = ped.getValue();
                    if(value != null) {// && !value.equals(oldValue)) {
                        try {
                            if (value instanceof String) {
                                ped.setAsText((String) value);
                            } else {
                                ped.setValue(value);
                            }
                        } catch(Throwable t) {}
                        
	                    setChanged(true);
                    }
                    //if the property editor has been closed, it may return
                    //a null value!?
                    if(ped.getValue() != null)
                        value = ped.getValue();
                }
            } catch (Exception e) {
                jiglooPlugin.handleError(e, "Error setting custom property "
                        + propName + " to " + value + ", for " + this);
            }
        }

        //another netbeans fix
		if (object instanceof LayoutManager) {
			if ("horizontalGap".equals(propName))
				propName = "hgap";
			if ("verticalGap".equals(propName))
				propName = "vgap";
		}
		if (propertyTypes != null) {
			Class propType = (Class) propertyTypes.get(propName);
			value = convertIfNeeded(propName, value, propType, comp);
		}
		value = preConvertPropertyValue(propName, value);
		Object propValue = value;
		if (value instanceof SizePropertySource) {
			value = ((SizePropertySource) value).getValue();
		} else if (value instanceof RectanglePropertySource) {
			value = ((RectanglePropertySource) value).getValue();
		} else if (value instanceof InsetsPropertySource) {
			value = ((InsetsPropertySource) value).getValue();
		} else if (value instanceof ArrayHolder) {
			value = ((ArrayHolder) value).getRawArray();
		} else if (value instanceof IWrapper) {
			value = ((IWrapper) value).getValue(null);
		} else if (value instanceof FontWrapper) {
			value = ((FontWrapper) value).getFont((Control) getObject());
		} else if (value instanceof ColorWrapper) {
			value = ((ColorWrapper) value).getColor((Control) getObject());
		} else if (value instanceof FieldWrapper) {
			value = ((FieldWrapper) value).getValue();
		} else if (value instanceof LayoutDataWrapper) {
			LayoutDataWrapper ldw = (LayoutDataWrapper) value;
			//may be setting "FormData.left" to a LayoutDataWrapper
			//for a FormAttachment like (button, 10), ie, with getObject == null
			if (ldw.getObject() != null)
				value = ldw.getObject();
		}

		if (comp != null && comp.isSwing()) {
			value = convertToAWTValue(value);
		}

		if (value == null) {
			//System.out.println("value = null for " + propName);
			return;
		}

		try {
			if (getObject() != null)
				value = PropertySourceFactory.setProperty(object, (String) propName, value);

		} catch (Throwable e) {
//						System.err.println(
//							"ERROR setting "
//								+ propName
//								+ ", "
//								+ getObject()
//								+ ", "
//								+ value
//								+ ", "
//								+ isField(propName)
//								+ ", "
//								+ e);
//			System.err.println("fieldNames = " + fieldNames);
//			e.printStackTrace();
		}
		if (properties == null)
			properties = new HashMap();

		putProperty(propName, propValue);

		if (!setProps.contains(propName))
			setProps.add(propName);

		if (comp != null && comp.getParent() != null) {
			//comp.updateUI();
			//comp.getParent().updateUI();
		}
		return;
	}

	private void putProperty(Object id, Object value) {
		properties.put(id, value);
	}
	
	public String getJavaCodeForPropertySetter(String pName, IJavaCodeManager jcm) {
		return null;
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		return null;
	}

	private String blockName = null;

	public void setBlockName(String bn) {
		blockName = bn;
	}

	public String getBlockName() {
		if (blockName != null)
			return blockName;
		String n = getName();
		int pos = n.lastIndexOf(".");
		if (pos < 0)
			return "";
		return n.substring(0, pos + 1);
	}

	public boolean needsUpdateInCode(String propName) {
		//TODO return correct value
		return true;
	}
    private boolean changed = false;
    
    public void setChanged(boolean b) {
        changed = b;
    }
    
    public boolean wasChanged() {
        return changed;
    }

}
