/*
 * Created on Jun 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.xml;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.properties.NodeUtils;

public class XMLParser {
	// Global value so it can be ref'd by the tree-adapter
	private Document document;
	private FormComponent root, menuBar, nvRoot;
	private FormEditor editor;
	private String type, style, ide, laf;
	private boolean netbeans;

	public void parse(File file, FormEditor editor) {
		this.editor = editor;
		System.getProperties().put("", "");
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
				MessageDialog.openError(
					editor.getSite().getShell(),
					"Missing or corrupt xml.jar file",
					"Looks like the xml.jar file is missing or corrupt\n"
						+ " - please see documentation at www.cloudgarden.com/jigloo");
				return;
			}
		}
		//factory.setValidating(true);   
		//factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
			root = null;
			getRootFormComponent();
			menuBar = null;
			getMenuBar();
			nvRoot = null;
			getNonVisualRoot();
			//displayAll();
			//displayForm();
		} catch (SAXException sxe) {
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		if (builder != null) {
			builder = null;
		}
		factory = null;
	}

	public void dispose() {
		editor = null;
		menuBar = null;
		root = null;
		document = null;
	}

	public String getStyle() {
		return style;
	}

	public boolean isNetbeans() {
		return netbeans;
	}

	public String getLookAndFeel() {
		return laf;
	}

	public FormComponent getRootFormComponent() {
		if (root != null)
			return root;
		NodeList list = document.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = (Node) list.item(i);
			if (node.getNodeName().equals("Form")) {
				type = NodeUtils.getAttribute("type", node);
				style = NodeUtils.getAttribute("style", node);
				ide = NodeUtils.getAttribute("ide", node);
				laf = NodeUtils.getAttribute("laf", node);
				if ("eclipse".equals(ide)) {
					node = NodeUtils.getChildNodeByName("Component", node);
					root = new FormComponent(node, null, editor);
					netbeans = false;
				} else {
					root = new FormComponent(node, null, type, editor);
					//comp.initializeFromNode();
					netbeans = true;
				}
			}
		}
		if ("SWT".equals(style))
			root.setClassType(FormComponent.TYPE_SWT);
		else
			root.setClassType(FormComponent.TYPE_SWING);

		return root;
	}

	public FormComponent getMenuBar() {
		if (menuBar != null)
			return menuBar;
		NodeList list = document.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = (Node) list.item(i);
			if (node.getNodeName().equals("Form")) {
				if ("eclipse".equals(ide)) {
					node =
						NodeUtils.getChildNodeByName("ExtraComponents", node);
					if (node == null)
						return null;
					Node mbNode =
						NodeUtils.getChildNodeByClassName("JMenuBar", node);
					if (mbNode == null)
						mbNode =
							NodeUtils.getChildNodeByClassName("Menu", node);
					if (mbNode == null)
						return null;
					menuBar = new FormComponent(mbNode, null, editor);
					break;
				} else {
					node =
						NodeUtils.getChildNodeByName(
							"NonVisualComponents",
							node);
					if (node == null)
						return null;
					Vector menus = NodeUtils.getChildNodesByName("Menu", node);
					if (menus != null) {
						for (int j = 0; j < menus.size(); j++) {
							Node n = (Node) menus.elementAt(j);
							String cn = NodeUtils.getComponentClass(n);
							if (JMenuBar.class.getName().equals(cn)) {
								menuBar = new FormComponent(n, null, editor);
								break;
							}
						}
					}
					if (menuBar == null)
						return null;
					//node = NodeUtils.getChildNodeByName("Menu", node);
					//if (node == null)
					//return null;
					//menuBar = new FormComponent(node, null, editor);
					break;
				}
			}
		}
		if ("SWT".equals(style))
			menuBar.setClassType(FormComponent.TYPE_SWT);
		else
			menuBar.setClassType(FormComponent.TYPE_SWING);
		return menuBar;
	}

	public FormComponent getNonVisualRoot() {
		if (nvRoot != null)
			return nvRoot;
		NodeList list = document.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = (Node) list.item(i);
			if (node.getNodeName().equals("Form")) {
				if ("eclipse".equals(ide)) {
					node =
						NodeUtils.getChildNodeByName(
							"NonVisualComponents",
							node);
					if (node == null)
						return null;
					Node nvNode =
						NodeUtils.getChildNodeByClassName(
							"java.lang.Object",
							node);
					if (nvNode == null)
						nvNode =
							NodeUtils.getChildNodeByClassName(
								"javax.swing.JLabel",
								node);
					if (nvNode == null)
						return null;
					nvRoot = new FormComponent(nvNode, null, editor);
					break;
				} else {
					node =
						NodeUtils.getChildNodeByName(
							"NonVisualComponents",
							node);
					if (node == null)
						return null;
					nvRoot = new FormComponent(node, null, editor);
					break;
				}
			}
		}
		if (nvRoot == null)
			return null;
		if ("SWT".equals(style))
			nvRoot.setClassType(FormComponent.TYPE_SWT);
		else
			nvRoot.setClassType(FormComponent.TYPE_SWING);
		return nvRoot;
	}

	public void displayForm() {
		FormComponent root = getRootFormComponent();
		System.out.println("FORM=\n" + root.toString());
	}

	public void displayAll() {
		NodeList list = document.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = (Node) list.item(i);
			display(node, "");
		}
	}

	public void display(Node node, String indent) {
		String name = node.getNodeName();
		String val = node.getNodeValue();
		if (name != null && !name.equals("#text")) {
			System.out.println(indent + "node =" + name + ", " + val);
		}
		NamedNodeMap map = node.getAttributes();
		if (map != null) {
			System.out.print(indent);
			for (int i = 0; i < map.getLength(); i++) {
				System.out.print(
					map.item(i).getNodeName()
						+ " = "
						+ map.item(i).getNodeValue()
						+ " ");
			}
			System.out.println();
		}
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			display(n, indent + "  ");
		}

	}
}
