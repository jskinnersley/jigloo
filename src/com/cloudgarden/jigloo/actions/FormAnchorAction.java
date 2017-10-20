/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormAnchorAction extends Action {
	private FormEditor editor;
	private int side;
	boolean absolute;
	private static final ImageDescriptor anchorImgDesc =
		ImageDescriptor.createFromFile(ImageManager.class, "anchor.gif");
	
	public FormAnchorAction(FormEditor editor, int side, boolean absolute) {
		this.editor = editor;
		this.side = side;
		this.absolute = absolute;
		FormComponent sel = editor.getSelectedComponent();
		String lbl = "  ";
		if (sel.isSideAnchored(side, absolute)) {
			setImageDescriptor(anchorImgDesc);
		}
		if (absolute) {
			if (side == 0)
				setText(lbl + "TOP (abs)");
			if (side == 1)
				setText(lbl + "RT   (abs)");
			if (side == 2)
				setText(lbl + "BOT (abs)");
			if (side == 3)
				setText(lbl + "LT   (abs)");
		} else {
			if (side == 0)
				setText(lbl + "TOP (rel)");
			if (side == 1)
				setText(lbl + "RT   (rel)");
			if (side == 2)
				setText(lbl + "BOT (rel)");
			if (side == 3)
				setText(lbl + "LT   (rel)");
		}
	}

	public void run() {
		Vector sels = editor.getSelectedComponents();
		for (int i = 0; i < sels.size(); i++) {
			try {
				FormComponent comp = (FormComponent) sels.elementAt(i);
				Object wid = comp.getControl();
				int style = comp.getStyle();
				comp.toggleAnchorForSide(side, absolute);
				comp.updateUI();
				comp.getEditor().setDirty(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
