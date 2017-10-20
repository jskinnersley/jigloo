/*
 * Created on Sep 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.NodeUtils;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IconWrapper extends ImageWrapper {

	public IconWrapper(Node node, FormComponent comp) {
		super(node, comp);
		Node cn = NodeUtils.getChildNodeByName("Icon", node);
		//for Netbeans
		if (cn == null)
			cn = NodeUtils.getChildNodeByName("Image", node);
		name = NodeUtils.getAttribute("name", cn);
	}

	public IconWrapper(String fileName, FormEditor editor) {
		super(fileName, editor);
	}

	public IconWrapper(String fileName, FormComponent comp) {
		super(fileName, comp);
	}

	public IconWrapper(java.awt.Image img, FormComponent comp) {
		super(img, comp);
	}

	public IconWrapper(Icon icon, FormComponent comp) {
		super((Image) null, comp);
		if (icon instanceof ImageIcon)
			setAWTImage(((ImageIcon) icon).getImage());
		else
			setName("No Icon");
	}

	public ImageWrapper getCopy(FormComponent copy) {
		IconWrapper iw = null;
		if (name == null)
			iw = new IconWrapper((java.awt.Image) awtImage, copy);
		else
			iw = new IconWrapper(name, copy);
		iw.setEditor(editor);
		return iw;
	}

	public Icon getIcon() {
		if ("No Icon".equals(name) || name == null)
			return null;
		Image img = (Image) getImage(null);
		if(img == null) {
		    System.err.println("Error getting image for IconWrapper "+name+", "+comp);
		    return null;
		}
		return new ImageIcon(img);
	}

	public String getSwingConstructor(IJavaCodeManager jcm) {
		if("No Icon".equals(name))
			return "null";
		jcm.addImport(ImageIcon.class.getName());
		if (!useImgFileInCode())
			return "new ImageIcon(getClass().getClassLoader().getResource(\""
				+ getNameAsURL()
				+ "\"))";
		else
			return "new ImageIcon(\"" + getNameAsURL() + "\")";
	}

}
