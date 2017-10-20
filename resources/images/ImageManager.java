/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.images;

import java.io.DataInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;
import com.cloudgarden.jigloo.util.FileUtils;
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
		ImageDescriptor imgDesc = new PluginImageDescriptor(ImageManager.class, imgName);
		return imgDesc;
	}

	public static ImageDescriptor getImageDescForClass(Class clazz, boolean isSwing, int style) {
		String imgName = null;
		if (clazz == null) {
			imgName = "javabean.gif";
		} else {
			imgName = getImageName(JiglooUtils.unconvertUnavailableClassName(clazz.getName()), isSwing, style);
		}
		ImageDescriptor imgDesc = new PluginImageDescriptor(ImageManager.class, imgName);
		if(imgDesc.getImageData() == null) {
			imgDesc = new PluginImageDescriptor(null, "/beanIcons/"+imgName);
			if(imgDesc.getImageData() == null)
				return null;
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

	public static Image getImageForClassName(String clsName, boolean isSwing, int style) {
		String imgName = getImageName(clsName, isSwing, style);
		return getImage(imgName);
	}

	public static Image getGrayScaleImage(String imgName) {
	    Image img = getImage(imgName+"_GS");
	    if(img != null)
	        return img;
	    img = getImage(imgName);
	    if(img == null)
	        return null;
	    img = new Image(Display.getDefault(), img, SWT.IMAGE_GRAY);
//	    ImageData id = img.getImageData();
//	    int[] pix = new int[id.width];
//	    int fac = 1 << (id.depth/3);
//	    if(id.depth == 16)
//	        fac = 32;
//	    if(id.depth == 32)
//	        fac = 256;
//	    for(int y = 0; y < id.height; y++) {
//	        id.getPixels(0, y, pix.length, pix, 0);
//	        for (int i = 0; i < pix.length; i++) {
//	            int val = pix[i];
//	            if(val != id.transparentPixel) {
//	                int alp = val & (255 * 256 * 256 * 256);
//	                int red = val & (fac-1);
//	                val = val / fac;
//	                int grn = val & (fac-1);
//	                val = val / fac;
//	                int blu = val & (fac-1);
//	                red = (red+grn+blu)/3;
////	                red = (red+grn+blu)/6 + fac/3;
//	                if(red > fac-1)
//	                    red = fac-1;
//	                pix[i] = alp | ( ( ( red * fac) | red) * fac) | red;
//	            }
//            }
//		    id.setPixels(0, y, pix.length, pix, 0);
//	    }
//	    img = new Image(Display.getDefault(), id);
		imgMap.put(imgName+"_GS", img);
		return img;
	}
	
	public static void setImage(Image img, String key) {
	    imgMap.put(key, img);
	}
	
	public static Image getImage(Object imgNameOrDesc) {
		if (ImageManager.imgMap.containsKey(imgNameOrDesc))
			return (Image) ImageManager.imgMap.get(imgNameOrDesc);
		ImageDescriptor imgDesc = null;
		if (imgNameOrDesc instanceof String) {
			imgDesc = new PluginImageDescriptor(ImageManager.class, (String) imgNameOrDesc);
		} else {
			imgDesc = (ImageDescriptor) imgNameOrDesc;
		}
		if (imgDesc == null)
			return null;
		Image img = imgDesc.createImage(false);
		if (img == null)
			return null;
		ImageManager.imgMap.put(imgNameOrDesc, img);
		return img;
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
	        //gc.drawImage(img2, bid.width - did.width - 1, bid.height - did.height - 1);
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

	public static String getImageName(String clsName, boolean isSwing, int style) {
		String objName = clsName;
		int pos = objName.lastIndexOf(".") + 1;
		objName = objName.substring(pos);
		if (objName.endsWith("Layout")) {
			objName = objName.toLowerCase();
			if (!isSwing && "gridlayout".equals(objName))
				objName = "gridbaglayout";
			if (style == 1)
				objName = "boxlayout_x";
			if (style == 2)
				objName = "boxlayout_y";
			return objName + ".gif";
		}
		if (isSwing) {
			if (objName.startsWith("J"))
				objName = objName.substring(1);
		} else {

			//if (objName.equals("CTabFolder"))
			//objName = "tabbedpane";

			//			if (objName.startsWith("C")) {
			//				String c2 = objName.substring(1, 2);
			//				if (c2.toUpperCase().equals(c2))
			//					objName = objName.substring(1);
			//			}

			if (objName.equals("Text")) {
				if ((style & SWT.MULTI) != 0)
					objName = "textarea";
				else
					objName = "textfield";
			}
			if (objName.equals("Button")) {
				if ((style & SWT.RADIO) != 0)
					objName = "radiobutton";
				else if ((style & SWT.CHECK) != 0)
					objName = "checkbox";
				else if ((style & SWT.TOGGLE) != 0)
					objName = "togglebutton";
			}
			if (objName.equals("Menu")) {
				if ((style & SWT.BAR) != 0)
					objName = "menubar";
			}
			if (objName.equals("MenuItem")) {
				if ((style & SWT.RADIO) != 0)
					objName = "radiobuttonmenuitem";
				else if ((style & SWT.CASCADE) != 0)
					objName = "cascademenuitem";
				else if ((style & SWT.CHECK) != 0)
					objName = "checkboxmenuitem";
				else if ((style & SWT.SEPARATOR) != 0)
					objName = "separator";
			}
			if (objName.equals("ProgressIndicator"))
				objName = "progressbar";
			if (objName.equals("TableColumn"))
				objName = "tableheader";
			if (objName.equals("StyledText"))
				objName = "textpane";
			if (objName.equals("StyledText"))
				objName = "textpane";
			if (objName.equals("FilteredList"))
				objName = "list";
			if (objName.equals("SashForm"))
				objName = "splitpane";
			if (objName.equals("ScrolledComposite"))
				objName = "scrollpane";
			if (objName.equals("Composite"))
				objName = "panel";
		}
		objName = objName.toLowerCase() + "_obj.gif";
		return objName;
	}
}
