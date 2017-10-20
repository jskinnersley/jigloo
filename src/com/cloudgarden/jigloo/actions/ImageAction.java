/*
 * Created on Aug 31, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageDisplayer;
import com.cloudgarden.jigloo.images.ImageManager;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImageAction extends Action implements ImageDisplayer {
    
	protected FormEditor editor;
    protected Class clazz;
    protected Image img;
    
	public void setImage(Image img) {
	    this.img = img;
	    firePropertyChange("image", null, img);
	}
	
	public Image getImage() {
//		long now = System.currentTimeMillis();
		if (img != null)
			return img;
		if (clazz != null)
			img = BeanHandler.getBeanImage(clazz, this, editor.getProject());
		if (img == null)
			img = ImageManager.getImage(getImageDescriptor());
		//System.out.println("GET IMAGE "+clazz+", "+(System.currentTimeMillis()-now));
		return img;
	}

    
}
