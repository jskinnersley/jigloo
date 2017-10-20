/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.images;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.FontManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImageManager {

	private static ImageDescriptor jbImgDesc = null;
	private static HashMap imgMap = new HashMap();

	public static void disposeImages() {
		Iterator it = imgMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			//System.out.println("ImageManager, disposing " + key);
			Image img = (Image) imgMap.get(key);
			img.dispose();
		}
	}

	public static ImageDescriptor getImageDesc(String imgName) {
//		return ImageDescriptor.createFromFile(ImageManager.class, imgName);
		return new PluginImageDescriptor(ImageManager.class, imgName);
	}

	public static ImageDescriptor getImageDescForClass(Class clazz, int style) {
		String imgName = null;
		String clsName = null;
		if (clazz == null || clazz.equals(Object.class)) {
			imgName = "javabean.gif";
		} else {
			clsName = JiglooUtils.unconvertUnavailableClassName(clazz.getName());
			imgName = getImageName(clsName, style);
		}
		
		if(clsName != null && clsName.indexOf("android") >= 0) {
			if(clsName.indexOf(".") >= 0)
				clsName = clsName.substring(clsName.lastIndexOf(".")+1);
			String shortName = clsName.substring(0, 1);
			for (int i = 1; i < clsName.length(); i++) {
				char chr = clsName.charAt(i);
				if(chr >= 'A' && chr <= 'Z')
					shortName += clsName.substring(i, i+1);
				if(shortName.length() == 2)
					break;
			}
			if(shortName.length() == 1)
				shortName = clsName.substring(0, 2);
			return new TextImageDescriptor(ImageManager.class, shortName);
		}
		
		ImageDescriptor imgDesc = new PluginImageDescriptor(ImageManager.class, imgName);
		if(imgDesc.getImageData() == null) {
			Class superClass = clazz.getSuperclass();
			if(superClass == null)
				return null;
			return getImageDescForClass(superClass, style);
		}
		return imgDesc;
	}

	public static ImageDescriptor getJavaBeanImgDesc() {
		if (jbImgDesc == null)
			jbImgDesc = new PluginImageDescriptor(ImageManager.class, "javabean.gif");
		return jbImgDesc;
	}

	public static Image getCheckBoxImage(boolean checked) {
		int itemHgt = CGPropertySheetPage.tableItemHeight;
		itemHgt = 2 * (int) (itemHgt / 2);
		if (itemHgt < 12)
			itemHgt = 12;
		if (itemHgt > 16)
			itemHgt = 16;
		//System.out.println("IT HGT= "+itemHgt+", "+checked);
		if (checked)
			return getImage("checkbox_yes_" + itemHgt + ".gif");
		else
			return getImage("checkbox_no_" + itemHgt + ".gif");
	}

	public static Image getImageForClassName(String clsName, int style) {
		String imgName = getImageName(clsName, style);
		Image img = getImage(imgName);
		return img;
	}

	public static Image getGrayScaleImage(String imgName) {
	    Image img = getImage(imgName+"_GS");
	    if(img != null)
	        return img;
	    img = getImage(imgName);
	    if(img == null)
	        return null;
	    img = new Image(Display.getDefault(), img, SWT.IMAGE_GRAY);
		imgMap.put(imgName+"_GS", img);
		return img;
	}
	
	public static void setImage(Image img, String key) {
	    imgMap.put(key, img);
	}
	
	public static Image getImage(Object imgNameOrDesc) {
		PluginImageDescriptor imgDesc = null;
		String key;
		if (imgNameOrDesc instanceof String) {
			key = (String) imgNameOrDesc;
			if (ImageManager.imgMap.containsKey(key))
				return (Image) ImageManager.imgMap.get(key);
			imgDesc = new PluginImageDescriptor(ImageManager.class, (String) imgNameOrDesc);
		} else {
			imgDesc = (PluginImageDescriptor) imgNameOrDesc;
			key = imgDesc.getFileName();
			if (ImageManager.imgMap.containsKey(key))
				return (Image) ImageManager.imgMap.get(key);
		}
		if (imgDesc == null)
			return null;
		Image img = imgDesc.createImage(false);
		if (img == null)
			return null;
		ImageManager.imgMap.put(key, img);
		return img;
	}

	public static Image getTextImage(String text) {
	    try {
	        String key = "TextImage|" + text;
	        if (imgMap.containsKey(key))
	            return (Image) imgMap.get(key);
	        Image img1 = getImage("javabean.gif");
	        int w = img1.getBounds().width;
	        int h = img1.getBounds().height;
	        ImageData bid = img1.getImageData();
	        Image result = new Image(Display.getDefault(), bid.width, bid.height);
	        GC gc = new GC(result);
	        gc.setBackground(ColorManager.getColor(200, 200, 255));
	        gc.fillRectangle(0, 0, w, h);
	        gc.setForeground(ColorManager.getColor(150, 150, 200));
	        gc.drawRoundRectangle(0, 0, w-1, h-1, 3, 3);
//	        gc.drawImage(img1, 0, 0);
	        gc.setForeground(ColorManager.getColor(0, 0, 100));
	        gc.setFont(FontManager.getFont("tahoma", 7, SWT.NORMAL, false, false));
	        gc.drawText(text, 1, 2, SWT.DRAW_TRANSPARENT);
	        gc.dispose();
	        imgMap.put(key, result);
	        return result;
	    } catch(Throwable t) {
	        jiglooPlugin.handleError(t);
	        return null;
	    }
	}
	public static Image getDecoratedImage(String mainImg, String decorator) {
	    try {
	        if(decorator == null)
	            return getImage(mainImg);
	        String key = mainImg + "|" + decorator;
	        if (imgMap.containsKey(key))
	            return (Image) imgMap.get(key);
	        Image img1 = getImage(mainImg);
	        if(img1 == null)
	            img1 = getImage("javabean.gif");
	        Image img2 = getImage(decorator);
	        ImageData bid = img1.getImageData();
	        ImageData did = img2.getImageData();
	        Image result = new Image(Display.getDefault(), bid.width, bid.height);
	        GC gc = new GC(result);
	        gc.drawImage(img1, 0, 0);
	        gc.drawImage(img2, 0, bid.height - did.height - 1);
	        gc.dispose();
	        imgMap.put(key, result);
	        return result;
	    } catch(Throwable t) {
	        jiglooPlugin.handleError(t);
	        return null;
	    }
	}

	public static Image getDecoratedImage(String mainImg, String decorator,
			String decorator2) {
		String key = mainImg + "|" + decorator + "|" + decorator2;
		if (imgMap.containsKey(key))
			return (Image) imgMap.get(key);
		Image img1 = getImage(mainImg);
		if(img1 == null)
			img1 = getImage("javabean.gif");
		ImageData bid = img1.getImageData();
				
		Image result = new Image(Display.getDefault(), bid.width, bid.height);
		GC gc = new GC(result);
		gc.drawImage(img1, 0, 0);
		
		if(decorator != null) {
			Image img2 = getImage(decorator);
			ImageData did = img2.getImageData();
			gc.drawImage(img2, 0, bid.height - did.height - 1);
		}
		if(decorator2 != null) {
			Image img2 = getImage(decorator2);
			ImageData did = img2.getImageData();
			gc.drawImage(img2,0,-bid.height/2);
		}
		gc.dispose();
		imgMap.put(key, result);
		return result;
	}

	public static String getImageName(String clsName, int style) {

		clsName = clsName.replace('.', '/');
		
		if(style > 0) {
			if(clsName.endsWith("/MenuItem") || clsName.endsWith("/Button")) {
				if((style & SWT.RADIO) != 0)
					style = SWT.RADIO;
				else if((style & SWT.CHECK) != 0)
					style = SWT.CHECK;
				else if((style & SWT.PUSH) != 0)
					style = SWT.PUSH;
				else if((style & SWT.TOGGLE) != 0)
					style = SWT.TOGGLE;
				else if((style & SWT.SEPARATOR) != 0)
					style = SWT.SEPARATOR;
				else if((style & SWT.CASCADE) != 0)
					style = SWT.CASCADE;
				else
					style = 0;
			} else {
				style = 0;
			}

			if(style != 0)
				clsName += "_"+style;
		}
		return clsName+".gif";
	}

}
