/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LocaleWrapper implements IWrapper {

	private Locale locale;
	private FormComponent comp;

	public LocaleWrapper(Node node, FormComponent comp) {
		Node cn = NodeUtils.getChildNodeByName("Locale", node);
		if (cn == null)
			cn = node;
		try {
			String c = NodeUtils.getAttribute("country", cn);
			String l = NodeUtils.getAttribute("language", cn);
			String v = NodeUtils.getAttribute("variant", cn);
			if (c == null)
				c = "";
			if (l == null)
				l = "";
			if (v == null)
				v = "";
			locale = new Locale(l, c, v);
		} catch (NumberFormatException e) {}
		this.comp = comp;
	}

	public LocaleWrapper(Locale locale, FormComponent comp) {
		this.locale = locale;
		this.comp = comp;
	}

	public void generateXML(
		Element prop,
		Document document,
		String indent1,
		String indent2) {
		prop.setAttribute("type", "Locale");
		Element loc = document.createElement("Locale");
		prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
		prop.appendChild(loc);
		loc.setAttribute("country", locale.getCountry());
		loc.setAttribute("language", locale.getLanguage());
		loc.setAttribute("variant", locale.getVariant());
		prop.appendChild(document.createTextNode("\n" + indent1));
	}

	public IWrapper getCopy(FormComponent fc) {
		if (fc == null)
			fc = comp;
		return new LocaleWrapper(locale, fc);
	}

	public Object getValue(Object obj) {
		return locale;
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		//jcm.addImport(Locale.class.getName());
		if (locale.getVariant().equals("")) {
			if (locale.getCountry().equals("")) {
				return "new java.util.Locale(\"" + locale.getLanguage() + "\")";
			}
			return "new java.util.Locale(\""
				+ locale.getLanguage()
				+ "\", \""
				+ locale.getCountry()
				+ "\")";
		}
		return "new java.util.Locale(\""
			+ locale.getLanguage()
			+ "\", \""
			+ locale.getCountry()
			+ "\", \""
			+ locale.getVariant()
			+ "\")";
	}

	public String toString() {
		return locale.getDisplayName();
	}

}
