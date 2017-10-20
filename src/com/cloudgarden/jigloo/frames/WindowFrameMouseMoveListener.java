/*
 * Created on Jan 18, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.ConversionUtils;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WindowFrameMouseMoveListener implements MouseMoveListener {

	public static int MODE_MOVE = WindowFrame.MODE_MOVE;
	public static int MODE_RESIZE_L = WindowFrame.MODE_RESIZE_L;
	public static int MODE_RESIZE_R = WindowFrame.MODE_RESIZE_R;
	public static int MODE_RESIZE_T = WindowFrame.MODE_RESIZE_T;
	public static int MODE_RESIZE_B = WindowFrame.MODE_RESIZE_B;
	public static int MODE_RESIZE_TL = WindowFrame.MODE_RESIZE_TL;
	public static int MODE_RESIZE_TR = WindowFrame.MODE_RESIZE_TR;
	public static int MODE_RESIZE_BL = WindowFrame.MODE_RESIZE_BL;
	public static int MODE_RESIZE_BR = WindowFrame.MODE_RESIZE_BR;

	private int resizeMode = 0;

	private WindowFrame wframe;
	private int side;
	private Control ctrl;
	private FormEditor editor;

	public WindowFrameMouseMoveListener(
		WindowFrame wframe,
		Control ctrl,
		int side,
		FormEditor editor) {
		this.wframe = wframe;
		this.side = side;
		this.ctrl = ctrl;
		this.editor = editor;
	}

	public void mouseMove(MouseEvent e) {
		int len;
		int pos;
		if (side == 1 || side == 3) {
			pos = e.y;
			len = ctrl.getSize().y / 5;
		} else {
			pos = e.x;
			len = ctrl.getSize().x / 5;
		}
		ConversionUtils.convertToDisplayCoords(e);
		if (wframe.isMouseDown()) {
			Point mousePoint = wframe.getMousePoint();
			Rectangle bounds = wframe.getOrigBoundsCopy();
			int dx = e.x - mousePoint.x;
			int dy = e.y - mousePoint.y;

			if (resizeMode == MODE_RESIZE_BL) {
				bounds.x += dx;
				bounds.width -= dx;
				bounds.height += dy;
			} else if (resizeMode == MODE_RESIZE_TL) {
				bounds.x += dx;
				bounds.y += dy;
				bounds.width -= dx;
				bounds.height -= dy;
			} else if (resizeMode == MODE_RESIZE_TR) {
				bounds.y += dy;
				bounds.width += dx;
				bounds.height -= dy;
			} else if (resizeMode == MODE_RESIZE_BR) {
				bounds.width += dx;
				bounds.height += dy;
			} else if (resizeMode == MODE_RESIZE_L) {
				bounds.x += dx;
				bounds.width -= dx;
			} else if (resizeMode == MODE_RESIZE_T) {
				bounds.y += dy;
				bounds.height -= dy;
			} else if (resizeMode == MODE_RESIZE_R) {
				bounds.width += dx;
			} else if (resizeMode == MODE_RESIZE_B) {
				bounds.height += dy;
			} else {
				bounds.x += dx;
				bounds.y += dy;
			}
			
			editor.handleWindowFrameResizing(wframe, bounds, resizeMode == MODE_MOVE);
			
		} else if(!resizeModeFixed) {
			if (pos < len) {
				if (side == 0 || side == 3) {
					ctrl.setCursor(wframe.getNWCursor());
					resizeMode = MODE_RESIZE_TL;
				} else if (side == 1) {
					ctrl.setCursor(wframe.getSWCursor());
					resizeMode = MODE_RESIZE_TR;
				} else if (side == 2) {
					ctrl.setCursor(wframe.getSWCursor());
					resizeMode = MODE_RESIZE_BL;
				}
			} else if (pos > 4 * len) {
				if (side == 3) {
					ctrl.setCursor(wframe.getSWCursor());
					resizeMode = MODE_RESIZE_BL;
				} else if (side == 1 || side == 2) {
					ctrl.setCursor(wframe.getNWCursor());
					resizeMode = MODE_RESIZE_BR;
				} else if (side == 0) {
					ctrl.setCursor(wframe.getSWCursor());
					resizeMode = MODE_RESIZE_TR;
				}
			} else if (pos > 2 * len && pos < 3 * len) {
				if (side == 0) {
					ctrl.setCursor(wframe.getUDCursor());
					resizeMode = MODE_RESIZE_T;
				} else if (side == 1) {
					ctrl.setCursor(wframe.getLRCursor());
					resizeMode = MODE_RESIZE_R;
				} else if (side == 2) {
					ctrl.setCursor(wframe.getUDCursor());
					resizeMode = MODE_RESIZE_B;
				} else if (side == 3) {
					ctrl.setCursor(wframe.getLRCursor());
					resizeMode = MODE_RESIZE_L;
				}
			} else {
				ctrl.setCursor(wframe.getMoveCursor());
				resizeMode = MODE_MOVE;
			}
		}
		wframe.setMode(resizeMode);
	}

	private boolean resizeModeFixed = false;
	
	public void setResizeMode(int mode, Cursor cursor) {
		ctrl.setCursor(cursor);
		resizeModeFixed = true;
		resizeMode = mode;
	}
}
