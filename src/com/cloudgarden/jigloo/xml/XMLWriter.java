/*
 * Created on Jul 6, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.xml;

import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.sources.BorderPropertySource;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;
import com.cloudgarden.jigloo.properties.sources.RectanglePropertySource;
import com.cloudgarden.jigloo.properties.sources.SizePropertySource;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.EventWrapper;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class XMLWriter {

	public static final String INDENT = "\t";

	public void save(FormComponent fc, IFile formFile) throws Exception {
		DocumentBuilderFactory factory = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
		} catch (Throwable t) {}
		if (factory == null) {
			try {
				Class dbf =
					Class.forName(
						"org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");
				factory = (DocumentBuilderFactory) dbf.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = (Element) document.createElement("Form");
		root.setAttribute("style", fc.isSwing() ? "Swing" : "SWT");
		root.setAttribute("ide", "eclipse");
		root.setAttribute("builder", "jigloo");
		String laf = fc.getEditor().getLookAndFeel();
		if (laf != null)
			root.setAttribute("laf", laf);
		root.setAttribute(
			"version",
			jiglooPlugin.getVersion());
		document.appendChild(root);
		String indent = XMLWriter.INDENT;

		if (fc.isRoot()) {
			FormComponent menuBar = fc.getEditor().getMenuBar();
			if (menuBar != null) {
				Element extras =
					(Element) document.createElement("ExtraComponents");
				root.appendChild(document.createTextNode("\n"));
				root.appendChild(extras);
				append(menuBar, extras, document, indent + INDENT);
			}
			FormComponent nvr = fc.getEditor().getNonVisualRoot();
			if (nvr != null) {
				Element nvcs =
					(Element) document.createElement("NonVisualComponents");
				root.appendChild(document.createTextNode("\n"));
				root.appendChild(nvcs);
				append(nvr, nvcs, document, indent + INDENT);
			}
		}
		append(fc, root, document, indent);
		root.appendChild(document.createTextNode("\n"));

		//root.normalize();

		TransformerFactory tFactory = null;
		try {
			tFactory = TransformerFactory.newInstance();
		} catch (Throwable t) {}
		if (tFactory == null) {
			try {
				Class tf =
					Class.forName(
						"org.apache.xalan.processor.TransformerFactoryImpl");
				tFactory = (TransformerFactory) tf.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);

		//System.out.println("SAVING XML TO " + formFile);
		StreamResult result = null;
		if (formFile == null) {
			result = new StreamResult(System.out);
		} else {
			if (!formFile.exists()) {
				try {
					formFile.create(
						new ByteArrayInputStream("".getBytes()),
						true,
						null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result =
				new StreamResult(new File(formFile.getLocation().toOSString()));
		}
		transformer.transform(source, result);

		//refresh the resource for the form file.
		formFile.refreshLocal(1, null);

	}

	private void append(
		FormComponent child,
		Node node,
		Document document,
		String indent) {

		boolean isSwing = child.isSwing();
		String indent2 = indent + INDENT;
		String indent3 = indent2 + INDENT;
		Element compNode = document.createElement("Component");
		compNode.setAttribute("class", child.getTranslatedClassName());
		compNode.setAttribute("name", child.getName());
		if (!isSwing)
			compNode.setAttribute("style", "" + child.getStyle());
		node.appendChild(document.createTextNode("\n" + indent));
		node.appendChild(compNode);
		/*
		Element constNode = document.createElement("Constraints");
		HashMap constraints = child.getConstraints();
		compNode.appendChild(document.createTextNode("\n" + indent2));
		compNode.appendChild(constNode);
		if (constraints.size() != 0) {
			Iterator it = constraints.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.indexOf("netbeans") < 0) {
					Object con = constraints.get(key);
					if (con instanceof String) {
						String val = (String) con;
						if (val.indexOf("netbeans") < 0) {
							Element cons = document.createElement("Constraint");
							constNode.appendChild(
								document.createTextNode("\n" + indent3));
							constNode.appendChild(cons);
							cons.setAttribute(key, val);
						}
					} else {
						System.err.println(
							"GOT NON-STRING CONSTRAINT "
								+ con
								+ ", "
								+ key
								+ ", "
								+ this);
					}
				}
			}
		}
		LayoutDataWrapper ldw = child.getLayoutDataWrapper();
		if (ldw != null)
			ldw.populateDOMNode(constNode, document, indent3);

		constNode.appendChild(document.createTextNode("\n" + indent2));
*/
		LayoutWrapper lw = child.getLayoutWrapper();
		if (lw.getLayoutType() != null && lw.isSet())
			lw.populateDOMNode(compNode, document, indent2);

		EventWrapper ew = child.getEventWrapper(false);
		if (ew != null)
			ew.populateDOMNode(compNode, document, indent2);

		Vector pns = child.getPropertyNames();
		if (pns != null) {
			Element props = document.createElement("Properties");
			compNode.appendChild(document.createTextNode("\n" + indent2));
			compNode.appendChild(props);
			for (int j = 0; j < pns.size(); j++) {
				String pname = (String) pns.elementAt(j);
				if (!child.isPropertySet(pname))
					continue;
				Element prop = document.createElement("Property");
				prop.setAttribute("name", pname);
				Object val = child.getPropertyValue(pname);
				setElementValue(prop, val, document, indent3);
				props.appendChild(document.createTextNode("\n" + indent3));
				props.appendChild(prop);
			}
			props.appendChild(document.createTextNode("\n" + indent2));
		}

		Vector scs = child.getChildren();
		if (scs.size() != 0) {
			compNode.appendChild(document.createTextNode("\n" + indent2));
			Element subs = document.createElement("SubComponents");
			compNode.appendChild(subs);
			//node.appendChild(document.createTextNode("\n"));
			for (int i = 0; i < scs.size(); i++) {
				FormComponent gchild = (FormComponent) scs.elementAt(i);
				append(gchild, subs, document, indent2 + XMLWriter.INDENT);
			}
			subs.appendChild(document.createTextNode("\n" + indent2));
		}
		compNode.appendChild(document.createTextNode("\n" + indent));
		//node.appendChild(document.createTextNode("\n" + indent));
	}

	public static void setElementValue(
		Element prop,
		Object val,
		Document document,
		String indent) {
		try {
			if (val == null) {
				prop.setAttribute("value", "null");
			} else if (val instanceof ColorWrapper) {
				ColorWrapper cw = (ColorWrapper) val;
				prop.setAttribute("type", "Color");
				Element color = document.createElement("Color");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(color);
				color.setAttribute("red", Integer.toHexString(cw.getRed()));
				color.setAttribute("green", Integer.toHexString(cw.getGreen()));
				color.setAttribute("blue", Integer.toHexString(cw.getBlue()));
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof FontWrapper) {
				FontWrapper fw = (FontWrapper) val;
				prop.setAttribute("type", "Font");
				Element font = document.createElement("Font");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute("name", fw.getName());
				font.setAttribute("size", "" + fw.getSize());
				font.setAttribute("style", "" + fw.getStyle());
				font.setAttribute("strikeout", "" + fw.getStrikeout());
				font.setAttribute("underline", "" + fw.getUnderline());
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof IWrapper) {
				IWrapper lw = (IWrapper) val;
				lw.generateXML(prop, document, indent, INDENT);
			} else if (val instanceof IconWrapper) {
				IconWrapper fw = (IconWrapper) val;
				prop.setAttribute("type", "Icon");
				Element font = document.createElement("Icon");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute("name", fw.getName());
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof ImageWrapper) {
				ImageWrapper fw = (ImageWrapper) val;
				prop.setAttribute("type", "Image");
				Element font = document.createElement("Image");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute("name", fw.getName());
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof SizePropertySource) {
				Point pt = ((SizePropertySource) val).getValue();
				prop.setAttribute("type", "Dimension");
				Element font = document.createElement("Dimension");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute("value", "[" + pt.x + ", " + pt.y + "]");
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof RectanglePropertySource) {
				Rectangle rec = ((RectanglePropertySource) val).getValue();
				prop.setAttribute("type", "Rectangle");
				Element font = document.createElement("Rectangle");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute(
					"value",
					"["
						+ rec.x
						+ ", "
						+ rec.y
						+ ", "
						+ rec.width
						+ ", "
						+ rec.height
						+ "]");
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof InsetsPropertySource) {
				Insets insets = ((InsetsPropertySource) val).getValue();
				prop.setAttribute("type", "Insets");
				Element font = document.createElement("Insets");
				prop.appendChild(
					document.createTextNode("\n" + indent + INDENT));
				prop.appendChild(font);
				font.setAttribute(
					"value",
					"["
						+ insets.top
						+ ", "
						+ insets.left
						+ ", "
						+ insets.bottom
						+ ", "
						+ insets.right
						+ "]");
				prop.appendChild(document.createTextNode("\n" + indent));
			} else if (val instanceof FieldWrapper) {
				Object fv = ((FieldWrapper) val).getValue();
				prop.setAttribute("type", "int");
				prop.setAttribute("value", fv.toString());
			} else if (val instanceof FormComponentWrapper) {
				FormComponent fv =
					((FormComponentWrapper) val).getFormComponent();
				if (fv != null) {
					prop.setAttribute("type", "String");
					prop.setAttribute("value", fv.getName());
				}
			} else if (val instanceof BorderPropertySource) {
				((BorderPropertySource) val).populateDOMNode(
					prop,
					document,
					indent);
			} else if (val instanceof Boolean) {
				prop.setAttribute("type", "boolean");
				prop.setAttribute("value", val.toString());
			} else if (val instanceof Integer) {
				prop.setAttribute("type", "int");
				prop.setAttribute("value", val.toString());
			} else if (val instanceof Float) {
				prop.setAttribute("type", "float");
				prop.setAttribute("value", val.toString());
			} else if (val instanceof Double) {
				prop.setAttribute("type", "double");
				prop.setAttribute("value", val.toString());
			} else if (val instanceof String) {
				prop.setAttribute("type", "String");
				prop.setAttribute("value", val.toString());
			} else {
				System.out.println("XMLWriter: UNABLE TO HANDLE " + val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
