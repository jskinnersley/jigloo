/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormLayoutAction extends ImageAction {
	private String[] props;
	private String label;

	public FormLayoutAction(
		FormEditor editor,
		Class clazz,
		String[] properties,
		String label) {
		this(editor, clazz);
		props = properties;
		this.label = label;
		setText(label);
		resolveImage();
	}

	public FormLayoutAction(FormEditor editor, Class clazz) {
		this(clazz);
		this.editor = editor;
	}

	private FormLayoutAction(Class clazz) {
		this.clazz = clazz;
		String name = clazz.toString();
		int pos = name.lastIndexOf(".");
		setText(name.substring(pos + 1));
		resolveImage();
	}

	private void resolveImage() {
		if (clazz != null) {
			int style = 0;
			if ("Box Layout - X".equals(label))
				style = 1;
			if ("Box Layout - Y".equals(label))
				style = 2;
			boolean swing = clazz.getName().indexOf("swt") < 0;
			ImageDescriptor imd =
				ImageManager.getImageDescForClass(clazz, style);
			if(imd == null)
			    imd = ImageManager.getJavaBeanImgDesc();
			if (imd != null)
				setImageDescriptor(imd);
		}
	}

	public void run() {
		try {
			for (int i = 0; i < editor.getSelectedComponents().size(); i++) {
				FormComponent fc = editor.getSelectedComponent(i);
				LayoutWrapper lw = new LayoutWrapper(fc, clazz, fc.isSwing());
				if (props != null) {
					lw.setAttribute(props[0], props[1]);
				}
				fc.executeSetLayoutWrapper(lw);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
