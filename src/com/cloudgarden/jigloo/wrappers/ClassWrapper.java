package com.cloudgarden.jigloo.wrappers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClassWrapper implements IWrapper {

	private String compName;
	private FormEditor editor;
	private Class cls;
	private String className;

	public ClassWrapper(String compName, FormComponent comp, Class cls) {
		this.compName = compName;
		this.cls = cls;
		this.className = cls.getName();
		this.editor = comp.getEditor();
	}

	public ClassWrapper(Node node, FormComponent comp, String className) {
		editor = comp.getEditor();
		this.className = className;
		if (editor.isNetbeans()) {
			Node cn = NodeUtils.getChildNodeByName("ComponentRef", node);
			compName = NodeUtils.getAttribute("name", cn);
		} else {
			Node cn = NodeUtils.getChildNodeByName(className, node);
			if (cn == null)
				cn = node;
			compName = NodeUtils.getAttribute("value", cn);
		}
	}

	public void generateXML(Element prop, Document document, String indent1, String indent2) {
		prop.setAttribute("type", className);
		Element loc = document.createElement(className);
		prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
		prop.appendChild(loc);
		loc.setAttribute("value", compName);
		prop.appendChild(document.createTextNode("\n" + indent1));
	}

	public Class getWrapperClass() {
		return cls;
	}

	public IWrapper getCopy(FormComponent comp) {
		return new ClassWrapper(compName, comp, cls);
	}

	public Object getValue(Object obj) {
		FormComponent fc = editor.getComponentByName(compName);
		if (fc == null)
			return null;
		return fc.getNonVisualObject();
	}

	public FormComponent getFormComponent() {
		FormComponent fc = editor.getJavaCodeParser().findFormComponent(compName);
		if(fc != null)
			return fc;
		return editor.getComponentByName(compName);
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		return toString();
	}

	public String toString() {
		int pos = compName.lastIndexOf(".");
		if(pos < 0)
			return compName;
		return compName.substring(pos+1);
	}

}
