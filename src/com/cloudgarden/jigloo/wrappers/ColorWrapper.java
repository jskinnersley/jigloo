/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.ISWTDisposableWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ColorWrapper implements ISWTDisposableWrapper {

	private int red, grn, blu;
	private Color color;
	private Object acol;

	public ColorWrapper(Node node, FormComponent comp) {
		Node cn = NodeUtils.getChildNodeByName("Color", node);
		if (cn == null)
			cn = node;
		try {
			red = Integer.parseInt(NodeUtils.getAttribute("red", cn), 16);
			grn = Integer.parseInt(NodeUtils.getAttribute("green", cn), 16);
			blu = Integer.parseInt(NodeUtils.getAttribute("blue", cn), 16);
		} catch (NumberFormatException e) {}
	}

	public ColorWrapper(int red, int grn, int blu, FormComponent comp) {
		this.red = red;
		this.grn = grn;
		this.blu = blu;
	}

	public ColorWrapper(String rgb, FormComponent comp) {
		rgb = JiglooUtils.replace(rgb, " ", "");
		String[] parts = JiglooUtils.split(",", rgb);
		this.red = Integer.parseInt(parts[0]);
		this.grn = Integer.parseInt(parts[1]);
		this.blu = Integer.parseInt(parts[2]);
	}

	public ColorWrapper(Color color, FormComponent comp) {
		this.color = color;
		red = color.getRed();
		grn = color.getGreen();
		blu = color.getBlue();
	}

	public ColorWrapper(java.awt.Color color, FormComponent comp) {
		this.acol = color;
		red = color.getRed();
		grn = color.getGreen();
		blu = color.getBlue();
	}

	public ColorWrapper getCopy() {
		return new ColorWrapper(red, grn, blu, null);
	}

	public void dispose() {
		if (color != null && !color.isDisposed())
			color.dispose();
		color = null;
	}

	public Object getColor(Object ctrl) {
		if (color != null)
			return color;
		if (ctrl == null)
			return getColor();
		Display display = ((Widget) ctrl).getDisplay();
		color = new Color(display, red, grn, blu);
		return color;
	}

	public java.awt.Color getColor() {
		if (acol != null)
			return (java.awt.Color) acol;
		acol = new java.awt.Color(red, grn, blu);
		return (java.awt.Color) acol;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return grn;
	}

	public int getBlue() {
		return blu;
	}

	public String getSWTDeclaration(String fieldName, IJavaCodeManager jcm) {
		//jcm.addImport(Color.class.getName());
		return "final org.eclipse.swt.graphics.Color "
			+ fieldName
			+ " = new org.eclipse.swt.graphics.Color(Display.getDefault(),"
			+ red
			+ ","
			+ grn
			+ ","
			+ blu
			+ ");";
	}

	public String toString() {
		return "[ " + red + ", " + grn + ", " + blu + " ]";
	}

	public String getResourceCode() {
		return "SWTResourceManager.getColor("
			+ getRed()
			+ ", "
			+ getGreen()
			+ ", "
			+ getBlue()
			+ ")";
	}

    /**
     * @return "red, green, blue" for appFramework properties file
     */
    public String getAFValue() {
        return getRed()
		+ ", "
		+ getGreen()
		+ ", "
		+ getBlue();
    }

}
