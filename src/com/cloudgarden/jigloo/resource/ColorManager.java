/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.resource;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ColorManager {

	static HashMap colorMap = new HashMap();

	public static void disposeColors() {
		Iterator it = colorMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Color color = (Color) colorMap.get(key);
			color.dispose();
		}
	}

	public static void putColor(Color color, int red, int green, int blue) {
		String name = red + "|" + green + "|" + blue;
		if (colorMap.containsKey(name))
			return;
		colorMap.put(name, color);
	}

	public static Color getColor(int red, int green, int blue) {
		String name = red + "|" + green + "|" + blue;
		if (colorMap.containsKey(name)) {
			Color c = (Color) colorMap.get(name);
		    if(c != null && !c.isDisposed())
		        return c;
		}
		Color color = new Color(Display.getDefault(), red, green, blue);
		colorMap.put(name, color);
		return color;
	}

}
