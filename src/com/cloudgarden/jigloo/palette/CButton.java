/*
 * Created on Jan 20, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.palette;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.ToolTipManager;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CButton extends CLabel {

	static Color alive, asleep, down, locked;
	boolean isToggle = false;
	boolean isRadio = false;
	boolean isDown = false;
	boolean isLocked = false;

	public CButton(Composite parent, int style) {
		super(parent, style);
		if (style == SWT.TOGGLE)
			isToggle = true;
		if (style == SWT.RADIO)
			isRadio = true;
		Color bgcol = getBackground();
		asleep = ColorManager.getColor(bgcol.getRed(), bgcol.getGreen(), bgcol.getBlue());
		alive = ColorManager.getColor(240, 240, 240);
		down = ColorManager.getColor(130, 130, 130);
		locked = ColorManager.getColor(150, 150, 200);

		addMouseTrackListener(new MouseTrackListener() {
			public void mouseEnter(MouseEvent e) {
				if(jiglooPlugin.isMacOS())
					ToolTipManager.showToolTip(getToolTipText(), CButton.this);
				if (isRadio && isDown)
					return;
				if (isToggle && isDown)
					return;
				setBackground(alive);
			}
			public void mouseExit(MouseEvent e) {
				if(jiglooPlugin.isMacOS())
					ToolTipManager.hideToolTip();
				if (isRadio && isDown)
					return;
				if (isToggle && isDown)
					return;
				setBackground(asleep);
			}
			public void mouseHover(MouseEvent e) {
			}
		});
		addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
				if (isLocked) {
					isLocked = false;
					isDown = false;
					setBackground(alive);
					return;
				}
				mouseDown(e);
				setBackground(locked);
				isLocked = true;
			}

			public void mouseDown(MouseEvent e) {
				if (isLocked)
					return;
				setBackground(down);
				if (isRadio)
					isDown = true;
				else if (isToggle)
					isDown = !isDown;
			}
			public void mouseUp(MouseEvent e) {
				if (isLocked)
					return;
				if (isRadio && isDown)
					return;
				if (isToggle && isDown)
					return;
				setBackground(alive);
			}
		});
	}

	public boolean isLocked() {
		return isLocked;
	}

	public boolean isDown() {
		return isDown;
	}

	public void push() {
		if (!isDisposed())
			setBackground(down);
		redraw();
		update();
		isDown = true;
	}

	public boolean release() {
		if (!isDisposed())
			setBackground(asleep);
		isDown = false;
		isLocked = false;
		return true;
	}

}
