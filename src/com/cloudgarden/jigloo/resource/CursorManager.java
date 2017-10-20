/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.resource;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.images.ImageManager;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CursorManager {

	static HashMap cursorMap = new HashMap();
	static Image addCursorImg = ImageManager.getImage("addCursor.gif");
	static Image addCursorMask = ImageManager.getImage("addCursor_mask.gif");
	public static final int ADD_CURSOR = 1000;
	
	public static void disposeCursors() {
		Iterator it = cursorMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Cursor c = (Cursor) cursorMap.get(key);
			c.dispose();
		}
	}

	private static Cursor getAddCursor() {
		//Create a source ImageData of depth 1 (monochrome)
		Display display = Display.getDefault();
		Color white = display.getSystemColor (SWT.COLOR_WHITE);
		Color black = display.getSystemColor (SWT.COLOR_BLACK);
		PaletteData palette = new PaletteData (new RGB [] {white.getRGB(), black.getRGB(),});
		ImageData sourceData = new ImageData (16, 16, 1, palette);
		int[] srcData = {
				1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,
				1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,
				1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
				1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,
				1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
				1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,
				1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,
				1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,
				1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,
				1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,
				1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,0,
				1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,
				1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				};
		int[] mskData = {
				1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,
				1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,
				1,1,1,1,0,0,0,0,1,0,0,1,0,0,1,0,
				1,1,1,1,1,0,0,0,1,0,1,1,1,0,1,0,
				1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,
				1,1,1,1,1,1,1,0,1,0,0,0,0,0,1,0,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
				1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,
				1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,
				1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,
				1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,
				1,1,1,0,1,1,1,1,1,0,0,0,0,0,1,0,
				1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,
				1,0,0,0,0,1,1,0,1,0,1,0,1,0,1,0,
				0,0,0,0,0,0,0,1,0,1,0,1,0,1,0,1,
				};
		for (int i = 0; i < 16; i ++) {
			for (int j = 0; j < 16; j++) {
				sourceData.setPixel(i, j, 1 - srcData[i+16*j]);
			}
		}
				//Create a mask ImageData of depth 1 (monochrome)
		palette = new PaletteData (new RGB [] {white.getRGB(), black.getRGB(),});
		ImageData maskData = new ImageData (16, 16, 1, palette);
		for (int i = 0; i < 16; i ++) {
			for (int j = 0; j < 16; j++) {
				maskData.setPixel(i, j, mskData[i+16*j]);
			}
		}
		//Set mask
		sourceData.maskData = maskData.data;
		sourceData.maskPad = maskData.scanlinePad;
		//Create cursor
		return new Cursor(display, sourceData, 0, 2);
	}
	
	public static Cursor getCursor(int type) {
		Integer id = new Integer(type);
		if (cursorMap.containsKey(id)) {
			Cursor c= (Cursor) cursorMap.get(id);
			if(c != null && !c.isDisposed())
			    return c;
		}
		Cursor c = null;
		if(type == ADD_CURSOR) {
		    try {

				//the "hot spot" doesn't seem to be accurate in eclipse 3.0 on windows
//		    	c = new Cursor(Display.getDefault(), SWT.CURSOR_CROSS);

//		    	c = getAddCursor();
		        ImageData imd = addCursorImg.getImageData();
		        ImageData mask = addCursorMask.getImageData();
				Display display = Display.getDefault();
				Color white = display.getSystemColor (SWT.COLOR_WHITE);
				Color black = display.getSystemColor (SWT.COLOR_BLACK);
				PaletteData pal = new PaletteData (new RGB [] {white.getRGB(), black.getRGB() });
		        ImageData src2 = new ImageData(42, 58, 1, pal);
		        ImageData mask2 = new ImageData(42, 58, 1, pal);
		        for(int i=0; i<42;i++) {
		            for(int j=0; j<58;j++) {
		                if(mask.getPixel(i, j) != 0)
			                mask2.setPixel(i, j, 0);
		                else
		                    mask2.setPixel(i, j, 1);
		                if(imd.getPixel(i, j) != 0)
			                src2.setPixel(i, j, 0);
		                else
		                    src2.setPixel(i, j, 1);
		            }
		        }
//		        c = new Cursor(Display.getDefault(),  mask2, src2, 21, 29);
		        src2.maskData = mask2.data;
		        src2.maskPad = mask2.scanlinePad;
		        c = new Cursor(Display.getDefault(), src2, 21, 29);
//		        c = new Cursor(Display.getDefault(), imd, 21, 29);

		    } catch(Throwable t) {
//		        t.printStackTrace();
				c = new Cursor(Display.getDefault(), SWT.CURSOR_CROSS);
		    }
		} else {
			c = new Cursor(Display.getDefault(), type);
		}
		cursorMap.put(id, c);
		return c;
	}

}
