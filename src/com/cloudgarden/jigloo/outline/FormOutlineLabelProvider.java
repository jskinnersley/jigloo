/*
 * Created on Jul 29, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.outline;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.util.SwingSWTMapper;

public class FormOutlineLabelProvider extends LabelProvider {

	private String label = null;

	public FormOutlineLabelProvider() {
		super();
	}

	public FormOutlineLabelProvider(String label) {
		super();
		this.label = label;
	}
	
	public String getText(Object obj) {
	    if (label != null)
	        return label;
	    return obj.toString();
	}
	
	public Image getImage(Object obj) {
	    try {
	        String imageKey = ISharedImages.IMG_OBJ_FOLDER;
	        if (obj instanceof TreeParent) {
	            FormComponent fc = ((TreeParent) obj).getFormComponent();
	            if (fc != null) {
	            	String clsName = fc.getClassName();
	            	String clsName2 = SwingSWTMapper.getNonOrderableClass(clsName);
	            	if(clsName2 != null)
	            		clsName = clsName2;
	                String imgName = ImageManager.getImageName(clsName, fc.getStyle());
	                Image bimg = fc.getBeanImage();
	                if (bimg != null) {
	                    ImageManager.setImage(bimg, imgName);
	                }
	                Image img = null;
	                if (fc.isInheritedDeclared()) {
	                    img = ImageManager.getDecoratedImage(imgName, "inheritedDec.gif");
	                } else if (fc.isInherited()) {
	                    img = ImageManager.getDecoratedImage(imgName, "inherited.gif");
	                } else if (fc.isRoot()) {
	                    img = ImageManager.getDecoratedImage(imgName, "root.gif");
	                } else {
	                    String img2 = null;
	                    if(fc.isLocalField())
	                        img2 = "local.gif";
	                    img = ImageManager.getDecoratedImage(imgName, img2);
	                }
	                if (img == null) {
	                    img = ImageManager.getImage("javabean.gif");
	                }
	                if (img != null)
	                    return img;
	                if (!fc.isContainer())
	                    imageKey = ISharedImages.IMG_OBJ_ELEMENT;
	            }
	        } else if (obj instanceof Boolean) {
	            boolean val = ((Boolean) obj).booleanValue();
	            return ImageManager.getCheckBoxImage(val);
	        } else {
	            return ImageManager.getCheckBoxImage(obj != null);
	        }
	        return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	    } catch(Throwable t) {
	        t.printStackTrace();
	        return null;
	    }
	}
		
	public void setLabel(String label) {
	    this.label = label;
	}
}