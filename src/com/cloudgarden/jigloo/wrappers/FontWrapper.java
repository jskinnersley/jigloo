/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.ISWTDisposableWrapper;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.resource.FontManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FontWrapper implements ISWTDisposableWrapper {

	private Font font;
	private java.awt.Font awtFont;
	private FontData fd;
	private String name;
	private int style, size;
	private FormComponent comp;
	boolean strikeout = false;
	boolean underline = false;

	public FontWrapper(
		String name,
		int size,
		int style,
		FontData fontData,
		FormComponent comp) {
		this(name, size, style, comp);
		fd = fontData;
		extractWin32(fd);
	}

	private void extractWin32(FontData fd) {
		if (fd != null && jiglooPlugin.isWindows()) {
//			org.eclipse.swt.internal.win32.LOGFONT lf = fd.data;
//			strikeout = lf.lfStrikeOut != 0;
//			underline = lf.lfUnderline != 0;
		} else {
			strikeout = false;
			underline = false;
		}
	}

	public FontWrapper(
		String name,
		int size,
		int style,
		boolean strikeout,
		boolean underline,
		FormComponent comp) {
		this(name, size, style, comp);
		this.strikeout = strikeout;
		this.underline = underline;
	}

	public FontWrapper(String name, int size, int style, FormComponent comp) {
		this.name = name;
		this.size = size;
		this.style = style;
		this.comp = comp;
	}

	/**
	 * name_style_size should be in form fontName-style-size eg Courier-PLAIN-12
	 */
	public FontWrapper(String name_style_size, FormComponent comp) {

		this.comp = comp;
		
	    awtFont = java.awt.Font.decode(name_style_size);
		name = awtFont.getName();
		size = awtFont.getSize();
		style =awtFont.getStyle();
		
	}

	public FontWrapper(Node node, FormComponent comp) {
		Node cn = NodeUtils.getChildNodeByName("Font", node);
		try {
			if (cn == null)
				cn = node;
			name = NodeUtils.getAttribute("name", cn);
			String fstyle = NodeUtils.getAttribute("style", cn);
			String fsize = NodeUtils.getAttribute("size", cn);
			String fstrikeout = NodeUtils.getAttribute("strikeout", cn);
			String funderline = NodeUtils.getAttribute("underline", cn);
			size = Integer.parseInt(fsize);
			style = Integer.parseInt(fstyle);
			if (style == java.awt.Font.BOLD)
				style = SWT.BOLD;
			else if (style == java.awt.Font.PLAIN)
				style = SWT.NORMAL;
			else if (style == java.awt.Font.ITALIC)
				style = SWT.ITALIC;
			if ("true".equals(fstrikeout))
				strikeout = true;
			if ("true".equals(funderline))
				underline = true;
		} catch (Exception e) {
			name = "Verdana";
			style = SWT.NORMAL;
			size = 10;
		}
		this.comp = comp;
	}

	public FontWrapper(Font font, FormComponent comp) {
		//this.font = font;
		fd = font.getFontData()[0];
		name = fd.getName();
		size = fd.getHeight();
		style = fd.getStyle();
		extractWin32(fd);
		FontManager.putFont(font, name, size, style, strikeout, underline);
		this.comp = comp;
	}

	public FontWrapper getCopy(FormComponent comp) {
		return new FontWrapper(name, size, style, strikeout, underline, comp);
	}

	public FontWrapper(java.awt.Font font, FormComponent comp) {
		this.awtFont = font;
		name = font.getName();
		size = font.getSize();
		style = font.getStyle();
		this.comp = comp;
	}

	public Object getFont(Object ctrl) {
		if (font != null)
			return font;
		if (ctrl == null) {
			if (awtFont == null)
				awtFont = new java.awt.Font(name, style, size);
			return awtFont;
		}
		font = FontManager.getFont(name, size, style, strikeout, underline);
		return font;
	}

	public void dispose() {
		//if (font != null && !font.isDisposed())
		//font.dispose();
		//font = null;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public int getStyle() {
		return style;
	}

	public boolean getStrikeout() {
		return strikeout;
	}

	public boolean getUnderline() {
		return underline;
	}

	public String getSWTDeclaration(String fieldName, IJavaCodeManager jcm) {
		jcm.addImport(Font.class.getName());

		if (strikeout || underline) {
			jcm.addImport(FontData.class.getName());
			String fdfn = "FD" + fieldName;
			String code =
				"FontData "
					+ fdfn
					+ " = new FontData(\""
					+ getName()
					+ "\","
					+ getSize()
					+ ","
					+ getStyle()
					+ ");\n"
					+ "\t\t"
					+ "if(System.getProperty(\"os.name\").toLowerCase().indexOf(\"windows\")>=0) {\n";
			if (strikeout)
				code += "\t\t\t((org.eclipse.swt.internal.win32.LOGFONT)"
					+ fdfn
					+ ".data).lfStrikeOut = 1;\n";
			if (underline)
				code += "\t\t\t((org.eclipse.swt.internal.win32.LOGFONT)"
					+ fdfn
					+ ".data).lfUnderline = 1;\n";

			code += "\t\t}\n"
				+ "\t\t"
				+ "final Font "
				+ fieldName
				+ " = new Font(Display.getDefault(),"
				+ fdfn
				+ ");\n";
			return code;
		}

		return "final Font "
			+ fieldName
			+ " = new Font(Display.getDefault(),\""
			+ getName()
			+ "\","
			+ getSize()
			+ ","
			+ getStyle()
			+ ");";
	}

	public FontData getFontData() {
		if (fd == null) {
			fd = new FontData(name, size, style);
			if (jiglooPlugin.isWindows() && (underline || strikeout)) {
//				org.eclipse.swt.internal.win32.LOGFONT lf = fd.data;
//				if (strikeout)
//					lf.lfStrikeOut = 1;
//				else
//					lf.lfStrikeOut = 0;
//				if (underline)
//					lf.lfUnderline = 1;
//				else
//					lf.lfUnderline = 0;
			}
		}
		return fd;
	}

	private String getStyleString() {
		String ss = "";
		if (comp.isSwing()) {
			if ((style & java.awt.Font.BOLD) != 0) {
				ss = "Bold";
			}
			if ((style & java.awt.Font.PLAIN) != 0 || style == 0) {
				ss += "Plain";
			}
			if ((style & java.awt.Font.ITALIC) != 0) {
				ss += "Italic";
			}
		} else {
			if ((style & SWT.BOLD) != 0) {
				ss += "Bold";
			}
			if ((style & SWT.NORMAL) != 0 || style == 0) {
				ss += "Normal";
			}
			if ((style & SWT.ITALIC) != 0) {
				ss += "Italic";
			}
			if (strikeout) {
				if (!"".equals(ss))
					ss += ", ";
				ss += "Strikeout";
			}
			if (underline) {
				if (!"".equals(ss))
					ss += ", ";
				ss += "Underline";
			}
		}
		return ss;
	}

	public String toString() {
		return name + ", " + size + ", " + getStyleString();
	}

	public String getResourceCode() {
		return "SWTResourceManager.getFont(\""
			+ getName()
			+ "\", "
			+ getSize()
			+ ", "
			+ getStyle()
			+ ", "
			+ getStrikeout()
			+ ", "
			+ getUnderline()
			+ ")";
	}

    /**
     * @return
     */
    public String getAFValue() {
        return name+"-"+getStyleString().toUpperCase()+"-"+getSize();
    }

}
