/*
 * Created on Jul 29, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.editors.HitResult;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Marker {

	private Control control;
	private FormComponent relComp;
	private boolean insertBefore;

	public Marker(FormEditor editor) {
		control = initSide(editor.getViewportControl());
	}

	public void hide() {
		control.setVisible(false);
	}

	public void show(FormComponent fc, FormComponent moving, HitResult hr) {
		FormComponent par = fc.getParent();
		int pos = HitResult.EAST;
		//if (par == null || par.getChildCount() < 2) {
		if (hr.isLeadingEdge()) {
			pos = HitResult.WEST;
		}
		//}
		show(fc, moving, pos);
	}

	public boolean insertBefore() {
		return insertBefore;
	}

	public FormComponent getRelativeComponent() {
		return relComp;
	}

	public boolean isVisible() {
		return control.isVisible();
	}

	private static final int MARKER_WIDTH = 5;

	public void show(FormComponent target, FormComponent moving, int direction) {
		relComp = target;
		insertBefore = false;
		Rectangle rec = target.getBoundsRelativeTo(null);
		Rectangle rec2 = moving.getBounds();
		rec = new Rectangle(rec.x, rec.y, rec.width, rec.height);
		if (direction == HitResult.WEST) {
			insertBefore = true;
			rec.x -= MARKER_WIDTH;
			rec.width = MARKER_WIDTH;
			rec.height = rec2.height;
		} else if (direction == HitResult.EAST) {
			rec.x += rec.width;
			rec.width = MARKER_WIDTH;
			rec.height = rec2.height;
		} else if (direction == HitResult.SOUTH) {
			rec.y -= MARKER_WIDTH;
			rec.height = MARKER_WIDTH;
			rec.width = rec2.width;
		} else if (direction == HitResult.NORTH) {
			insertBefore = true;
			rec.y += rec.height;
			rec.height = MARKER_WIDTH;
			rec.width = rec2.width;
		}
		setBounds(rec);
		show();
	}

	public void show() {
		if(!control.isVisible()) {
			control.setVisible(true);
		}
		control.moveAbove(null);
	}
	
	public void setBounds(Rectangle bounds) {
		control.setBounds(bounds);
	}
	
	private Color bgColor = ColorManager.getColor(255, 0, 0);
	private Color fgColor = ColorManager.getColor(0, 0, 0);

	public void setBGColor(Color bgColor) {
		this.bgColor = bgColor;
		if(control != null && !control.isDisposed())
			control.setBackground(bgColor);
	}
	
	public void setFGColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	public void dispose() {
		if(control != null && !control.isDisposed())
			control.dispose();
		control = null;
	}
	
	
	private Control initSide(Composite parent) {
		Control shell = new Canvas(parent, SWT.NULL);
//		shell.setEnabled(true);
		shell.setEnabled(false);
		shell.setBackground(bgColor);
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(fgColor);
				Rectangle b = control.getBounds();
				e.gc.drawRectangle(0, 0, b.width - 1, b.height - 1);
			}
		});
		return shell;
	}

}
