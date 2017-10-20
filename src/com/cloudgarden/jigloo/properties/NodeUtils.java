/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties;

import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.ui.views.properties.IPropertySource;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.sources.BorderPropertySource;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.DoubleArrayWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.IntegerArrayWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LocaleWrapper;
import com.cloudgarden.jigloo.wrappers.StringArrayWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NodeUtils {

	public static String getComponentClass(Node node) {
		return getAttribute("class", node);
	}

	public static String getComponentName(Node node) {
		return getAttribute("name", node);
	}

	public static int getStyle(Node node) {
		String val = getAttribute("style", node);
		if (val == null)
			return SWT.NULL;
		return Integer.parseInt(val);
	}

	public static String getAttribute(String att, Node node) {
		if (node == null)
			return null;
		NamedNodeMap map = node.getAttributes();
		if (map != null) {
			for (int i = 0; i < map.getLength(); i++) {
				if (att.equals(map.item(i).getNodeName())) {
					return map.item(i).getNodeValue();
				}
			}
		}
		return null;
	}

	public static void fillAttributes(HashMap attributes, Node node, FormComponent comp, boolean includeSubnodes) {
		NamedNodeMap map = node.getAttributes();
		if (map != null) {
			for (int i = 0; i < map.getLength(); i++) {
				attributes.put(map.item(i).getNodeName(), map.item(i).getNodeValue());
			}
		}
		if (includeSubnodes)
			fillAttributesFromSubnodes(attributes, node, comp);
	}

	public static void fillAttributesFromSubnodes(HashMap attributes, Node node, FormComponent comp) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			try {
				Node n = (Node) list.item(i);
				Object val = getPropertyFromNode(n, comp);
				String name = getAttribute("name", n);

				//for netbeans quirkiness
				String propName = getAttribute("PropertyName", n);
				if (propName != null) {
					name = propName;
				}

				//val =PropertySourceFactory.convertToPropertySource(val,comp,name);
				if (val != null) {
					attributes.put(name, val);
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public static Object getPropertyFromNode(Node node, FormComponent comp) {
		//System.out.println("GET PROP FROM NODE "+node);
		Object prop = null;
		String type = getAttribute("type", node);
		String className = getAttribute("class", node);
		String val = getAttribute("value", node);
		String pname = getAttribute("name", node);
		//this is for a property of a netbeans border
		String propName = getAttribute("PropertyName", node);
		if (propName != null) {
			pname = propName;
			if ("color".equals(propName)) {
				type = "java.awt.Color";
			} else if ("font".equals(propName)) {
				type = "java.awt.Font";
			}
		}
		try {
			if (type == null) {
				if (className != null) {
					//System.out.println("MAKING INSTANCE " + className);
					Object inst = null;
					if (className.equals(FormAttachment.class.getName())) {
						inst = new LayoutDataWrapper(new FormAttachment(0, 0), comp);
					} else if (className.indexOf("Border") >= 0 && className.startsWith("javax.swing.border.")) {
						Class cls = Class.forName(className);
						Border bord = BorderPropertySource.createBorder(cls);
						inst = new BorderPropertySource(bord, comp, pname, comp);
					} else {
						try {
							inst = Class.forName(className).newInstance();
						} catch (Exception e) {
							System.err.println("NodeUtils Error " + e);
							return null;
						}
					}
					HashMap props = new HashMap();
					fillAttributesFromSubnodes(props, node, comp);
					Iterator it = props.keySet().iterator();
					while (it.hasNext()) {
						String name = (String) it.next();
						Object value = props.get(name);
						//						System.out.println(
						//							"SETTING PROP "+ name
						//								+ ", "+ value+ ", "+ value.getClass());
						if (name.equals("control")) {
							if (comp.getEditor() == null) {
								throw new RuntimeException("Editor cannot be null");
							}
							if ("null".equals(value.toString())) {
								value = null;
							} else {
								value = new FormComponentWrapper(value.toString(), comp.getEditor());
							}
						}
						if (inst instanceof IPropertySource) {
							((IPropertySource) inst).setPropertyValue(name, value);
						} else {
							//PropertySourceFactory.setProperty(
							//inst,
							//name,
							//value);
						}
					}
					return inst;
				}
				return null;
			}
			if (type.indexOf("Border") >= 0) {
				return getNetbeansBorder(node, comp);
			}
			if (type.indexOf("String") >= 0) {
				return val;
			}
			if (type.equals("int")) {
				return new Integer(val);
			}
			if (type.equals("double")) {
				return new Double(val);
			}
			if (type.equals("float")) {
				return new Float(val);
			}
			if (type.indexOf("boolean") >= 0) {
				return new Boolean(val);
			}
			if (type.indexOf("Color") >= 0) {
				return new ColorWrapper(node, comp);
			}
			if (type.indexOf("Locale") >= 0) {
				return new LocaleWrapper(node, comp);
			}
			if (type.indexOf("DoubleArray") >= 0) {
				return new DoubleArrayWrapper(node, comp);
			}
			if (type.indexOf("StringArray") >= 0) {
				return new StringArrayWrapper(node, comp);
			}
			if (type.indexOf("ButtonGroup") >= 0) {
				return new ClassWrapper(node, comp,"ButtonGroup");
			}
			if (type.indexOf("IntegerArray") >= 0) {
				return new IntegerArrayWrapper(node, comp);
			}
			if (type.indexOf("Image") >= 0) {
				return new ImageWrapper(node, comp);
			}
			if (type.indexOf("Icon") >= 0) {
				return new IconWrapper(node, comp);
			}
			if (type.indexOf("Font") >= 0) {
				return new FontWrapper(node, comp);
			}
			if (type.indexOf("Dimension") >= 0) {
				Node cn = getChildNodeByName("Dimension", node);
				String dval = getAttribute("value", cn);
				if (dval == null) {
					System.err.println("Dimension value is null for " + node);
					return null;
				}
				int sep = dval.indexOf(",");
				int xi = Integer.parseInt(dval.substring(1, sep));
				int yi = Integer.parseInt(dval.substring(sep + 2, dval.length() - 1));
				return new Point(xi, yi);
				//return new Dimension(xi, yi);
			}
			if (type.indexOf("Point") >= 0) {
				Node cn = getChildNodeByName("Point", node);
				String dval = getAttribute("value", cn);
				if (dval == null) {
					System.err.println("Point value is null for " + node);
					return null;
				}
				int sep = dval.indexOf(",");
				int xi = Integer.parseInt(dval.substring(1, sep));
				int yi = Integer.parseInt(dval.substring(sep + 2, dval.length() - 1));
				return new Point(xi, yi);
			}
			if (type.indexOf("Rectangle") >= 0) {
				Node cn = getChildNodeByName("Rectangle", node);
				String dval = getAttribute("value", cn);
				if (dval == null) {
					System.err.println("Rectangle value is null for " + node);
					return null;
				}
				int sep = dval.indexOf(",");
				int sep2 = dval.indexOf(",", sep + 1);
				int sep3 = dval.indexOf(",", sep2 + 1);
				String s1 = dval.substring(1, sep);
				String s2 = dval.substring(sep + 2, sep2);
				String s3 = dval.substring(sep2 + 2, sep3);
				String s4 = dval.substring(sep3 + 2, dval.length() - 1);
				int xi = Integer.parseInt(s1);
				int yi = Integer.parseInt(s2);
				int w = Integer.parseInt(s3);
				int h = Integer.parseInt(s4);
				return new Rectangle(xi, yi, w, h);
				//return new java.awt.Rectangle(xi, yi, w, h);
			}
			if (type.indexOf("Insets") >= 0) {
				Node cn = getChildNodeByName("Insets", node);
				String dval = getAttribute("value", cn);
				if (dval == null) {
					System.err.println("Insets value is null for " + node);
					return null;
				}
				int sep = dval.indexOf(",");
				int sep2 = dval.indexOf(",", sep + 1);
				int sep3 = dval.indexOf(",", sep2 + 1);
				String s1 = dval.substring(1, sep);
				String s2 = dval.substring(sep + 2, sep2);
				String s3 = dval.substring(sep2 + 2, sep3);
				String s4 = dval.substring(sep3 + 2, dval.length() - 1);
				int xi = Integer.parseInt(s1);
				int yi = Integer.parseInt(s2);
				int w = Integer.parseInt(s3);
				int h = Integer.parseInt(s4);
				return new Insets(xi, yi, w, h);
			}
			if (type.indexOf("Border") >= 0) {
				Node cn = getChildNodeByName("Border", node);
				return BorderFactory.createEmptyBorder();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "UNKNOWN " + type + ", " + val;
	}

	public static Object getNetbeansBorder(Node node, FormComponent comp) {
		String pname = getAttribute("name", node);
		if (pname == null)
			pname = getAttribute("PropertyName", node);
		if (pname.equals("inside"))
			pname = "insideBorder";
		if (pname.equals("outside"))
			pname = "outsideBorder";
		Node bNode = getChildNodeByName("Border", node);
		if (bNode == null)
			bNode = node;
		String info = getAttribute("info", bNode);
		Class cls = null;
		if (info.indexOf("Compound") >= 0)
			cls = CompoundBorder.class;
		else if (info.indexOf("SoftBevel") >= 0)
			cls = SoftBevelBorder.class;
		else if (info.indexOf("Bevel") >= 0)
			cls = BevelBorder.class;
		else if (info.indexOf("Etched") >= 0)
			cls = EtchedBorder.class;
		else if (info.indexOf("Empty") >= 0)
			cls = EmptyBorder.class;
		else if (info.indexOf("Titled") >= 0)
			cls = TitledBorder.class;
		else if (info.indexOf("Line") >= 0)
			cls = LineBorder.class;
		else if (info.indexOf("Matte") >= 0)
			cls = MatteBorder.class;
		Border bord = BorderPropertySource.createBorder(cls);
		BorderPropertySource bps = new BorderPropertySource(bord, comp, pname, comp);
		HashMap props = new HashMap();
		if (cls.equals(CompoundBorder.class)) {
			bNode = getChildNodeByPartialName("Border", bNode);
			Node inside = getChildNodeByAttrValue("PropertyName", "inside", bNode);
			Node outside = getChildNodeByAttrValue("PropertyName", "outside", bNode);
			Object insideBorder = getNetbeansBorder(inside, comp);
			Object outsideBorder = getNetbeansBorder(outside, comp);
			bps.setPropertyValue("insideBorder", insideBorder);
			bps.setPropertyValue("outsideBorder", outsideBorder);
		} else {
			bNode = getChildNodeByPartialName("Border", bNode);
			fillAttributes(props, bNode, comp, true);
			Iterator it = props.keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				Object value = props.get(name);
				bps.setPropertyValue(name, value);
			}
		}
		return bps;
	}

	public static Node getChildNodeByPartialName(String partialName, Node node) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			if (n.getNodeName().indexOf(partialName) >= 0) {
				return n;
			}
		}
		return null;
	}

	public static Node getChildNodeByName(String nodeName, Node node) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			if (nodeName.equals(n.getNodeName())) {
				return n;
			}
		}
		return null;
	}

	public static Vector getChildNodesByName(String nodeName, Node node) {
		Vector nodes = new Vector();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			if (nodeName.equals(n.getNodeName())) {
				nodes.add(n);
			}
		}
		return nodes;
	}

	public static Node getChildNodeByClassName(String className, Node node) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			String nodeClassName = getAttribute("class", n);
			if (nodeClassName != null && nodeClassName.indexOf(className) >= 0) {
				return n;
			}
		}
		return null;
	}

	public static Node getChildNodeByAttrValue(String attr, String attrVal, Node node) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			String attrValue = getAttribute(attr, n);
			if (attrValue != null && attrValue.equals(attrVal)) {
				return n;
			}
		}
		return null;
	}

	public static NodeList getChildrenOfNode(String nodeName, Node node) {
		if (node == null)
			return null;
		Node scNode = getChildNodeByName(nodeName, node);
		if (scNode == null)
			return null;
		return scNode.getChildNodes();
	}

}
