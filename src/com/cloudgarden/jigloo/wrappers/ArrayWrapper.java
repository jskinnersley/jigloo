/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class ArrayWrapper implements IWrapper {

	protected Object array;
	protected FormComponent comp;

	public abstract String getXMLId();

	public abstract Class getArrayClass();

	public abstract Object getArrayFromString(String str);

	public abstract IWrapper getCopy(FormComponent fc);

	public ArrayWrapper(Node node, FormComponent comp) {
		Node cn = NodeUtils.getChildNodeByName(getXMLId(), node);
		if (cn == null)
			cn = node;
		try {
			String val = NodeUtils.getAttribute("value", cn);
			array = getArrayFromString(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.comp = comp;
	}

	public ArrayWrapper(Object array, FormComponent comp) {
		this.array = array;
		this.comp = comp;
	}

	public void generateXML(
		Element prop,
		Document document,
		String indent1,
		String indent2) {
		prop.setAttribute("type", getXMLId());
		Element loc = document.createElement(getXMLId());
		prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
		prop.appendChild(loc);
		loc.setAttribute("value", JiglooUtils.toString(array, true));
		prop.appendChild(document.createTextNode("\n" + indent1));
	}

	public Object getValue(Object obj) {
		return array;
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		String code = "new " + getArrayClass().getName() + "[] {";
		if (array != null) {
			code += JiglooUtils.toString(array, false);
		}
		return code + "}";
	}

	public String toString() {
		if (array == null)
			return "[  ]";
		return JiglooUtils.toString(array, true);
	}

}
