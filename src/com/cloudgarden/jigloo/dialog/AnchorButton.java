/*
 * Created on Jan 22, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.dialog;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.images.ImageManager;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AnchorButton extends Button {

	final Image anchorImg = ImageManager.getImage("anchor.gif");
	final Image noAnchorImg = ImageManager.getImage("anchor_no.gif");
	final Image relAnchorImg = ImageManager.getImage("anchor_rel.gif");

	int state = 0;
	public static int NO_ANCHOR = 0;
	public static int ABS_ANCHOR = 1;
	public static int REL_ANCHOR = 2;
	String side = "";

	public AnchorButton(Composite parent, int style) {
		super(parent, style);
		addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {
				state++;
				if (state == 3)
					state = 0;
				setState(state);
			}
			public void mouseUp(MouseEvent e) {}
		});
	}

	public void setState(int state) {
		this.state = state;
		if (state == NO_ANCHOR) {
			setImage(noAnchorImg);
		} else if (state == ABS_ANCHOR) {
			setImage(anchorImg);
		} else if (state == REL_ANCHOR) {
			setImage(relAnchorImg);
		}
	}
	public int getState() {
		return state;
	}
	public void setNoAnchor() {
		setState(NO_ANCHOR);
	}
	public void setRelAnchor() {
		setState(REL_ANCHOR);
	}
	public void setAbsAnchor() {
		setState(ABS_ANCHOR);
	}

	public boolean isAnchored() {
		return state != NO_ANCHOR;
	}

	public boolean isAbsolute() {
		return state == ABS_ANCHOR;
	}

	protected void checkSubclass() {
		//super.checkSubclass();
	}

	public String getSide() {
		return side;
	}
	public void setSide(String string) {
		side = string;
	}

}
