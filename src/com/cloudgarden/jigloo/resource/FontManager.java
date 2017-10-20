/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.resource;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.jiglooPlugin;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FontManager {

	static HashMap fontMap = new HashMap();

	public static void disposeFonts() {
		Iterator it = fontMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			//System.out.println("FontManager, disposing " + key);
			Font font = (Font) fontMap.get(key);
			font.dispose();
		}
	}

	public static void putFont(
		Font font,
		String name,
		int size,
		int style,
		boolean strikeout,
		boolean underline) {
		String fontName =
			name + "|" + size + "|" + style + "|" + strikeout + "|" + underline;
		if (fontMap.containsKey(fontName))
			return;
		fontMap.put(fontName, font);
	}

	public static Font getFont(
		String name,
		int size,
		int style,
		boolean strikeout,
		boolean underline) {
		String fontName =
			name + "|" + size + "|" + style + "|" + strikeout + "|" + underline;
		if (fontMap.containsKey(fontName)) {
			Font f = (Font) fontMap.get(fontName);
			if(f != null && !f.isDisposed())
			    return f;
		}
		FontData fd = new FontData(name, size, style);
		if ((strikeout || underline) && jiglooPlugin.isWindows()) {
//			org.eclipse.swt.internal.win32.LOGFONT lf = fd.data;
//			if (lf != null) {
//				if (strikeout)
//					lf.lfStrikeOut = 1;
//				if (underline)
//					lf.lfUnderline = 1;
//			}
		}
		Font font = new Font(Display.getDefault(), fd);
		fontMap.put(fontName, font);
		return font;
	}

}
