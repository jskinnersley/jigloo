/*
 * Created on Jun 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import java.awt.Component;
import java.awt.Dimension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MarqueeFrame {

	private boolean stippled = false;
	private Control leftWin, rightWin, topWin, botWin;
	private Composite parent;
	private int B = 2;
	private Rectangle bounds;
	private boolean visible = false;
	private boolean disposed = false;
	private int resizeMode = 0;
	private int mode = 0;
	private int bgCol = SWT.COLOR_RED;
	private Cursor def;
	private Image img = null;
	private Control imgWin = null;
	private FormComponent imgComp = null;

	private static Image stipple;

	private PaintListener pl = new PaintListener() {
		public void paintControl(PaintEvent pe) {
			Control con = (Control) pe.getSource();
			int x = pe.x;
			int y = pe.y;
			int w = con.getSize().x;
			int h = con.getSize().y;
			//System.out.println("paint " + x + ", " + y + ", " + w + ", " + h);

			if (w > h) {
//				pe.gc.setBackground(resizeCol);
				for (int i = 0; i < 5; i += 2) {
					pe.gc.fillRectangle(i * w / 5, 0, w / 5, h);
				}
				//for (int i = 0; i < w / 16 + 1; i++) {
				//pe.gc.drawImage(stipple, i * 16, 0);
				//}
			} else {
//				pe.gc.setBackground(resizeCol);
				for (int i = 0; i < 5; i += 2) {
					pe.gc.fillRectangle(0, i * h / 5, w, h / 5);
				}
				//for (int i = 0; i < h / 16 + 1; i++) {
				//pe.gc.drawImage(stipple, 0, i * 16);
				//}
			}
		}
	};

	public MarqueeFrame(Composite parent, FormEditor editor) {
		disposed = false;
		def = new Cursor(parent.getDisplay(), SWT.CURSOR_ARROW);
		this.parent = parent;
		leftWin = initSide(parent);
		rightWin = initSide(parent);
		topWin = initSide(parent);
		botWin = initSide(parent);

	}

	private Control initSide(Composite parent) {
		Control shell = new Canvas(parent, SWT.NULL);
		shell.setEnabled(false);
		shell.setBackground(parent.getDisplay().getSystemColor(bgCol));
		return shell;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (topWin.isDisposed())
			return;
		if(!visible)
			imgComp = null;
		if(visible) {
		    topWin.moveAbove(null);
		    botWin.moveAbove(null);
		    leftWin.moveAbove(null);
		    rightWin.moveAbove(null);
			if(imgWin != null)
			    imgWin.moveAbove(null);
		}
		if (visible != topWin.isVisible()) {
			topWin.setVisible(visible);
			leftWin.setVisible(visible);
			rightWin.setVisible(visible);
			botWin.setVisible(visible);
			if(imgWin != null)
			    imgWin.setVisible(visible);
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void moveBy(int dx, int dy) {
	    setBounds(bounds.x+dx, bounds.y+dy, bounds.width, bounds.height);
	}
	
	public void setImageFC(FormComponent newImgComp) {
	    if(newImgComp == null) {
	        if(imgWin != null)
	            imgWin.dispose();
	        imgWin = null;
	        if(img != null)
	            img.dispose();
	        img = null;
	        return;
	    }
	    if(newImgComp.equals(imgComp))
	        return;
	    imgComp = newImgComp;
	    Component comp = newImgComp.getComponent();
	    if(comp == null)
	    	return;
        Dimension pSize = comp.getPreferredSize();
        comp.setSize(pSize);
	    if(img != null)
	        img.dispose();
	    img = JiglooUtils.createImage(comp, Display.getDefault());
	    if(imgWin == null) {
	        imgWin = initSide(parent);
	        imgWin.addPaintListener(new PaintListener() {
	            public void paintControl(PaintEvent pe) {
	            	Rectangle b = img.getBounds();
	            	if(pe.x >= b.width || pe.y >= b.height)
	            		return;
	            	int w = Math.min(b.width - pe.x, pe.width);
	            	int h = Math.min(b.height - pe.y, pe.height);
	                pe.gc.drawImage(img, pe.x, pe.y, w, h, pe.x, pe.y, pe.width, pe.height);
	            }
	        });
	    }
	}
	
	public void setBounds(int x, int y, int w, int h) {
		if (leftWin.isDisposed())
			return;
		if (bounds == null) {
			bounds = new Rectangle(x, y, w, h);
		} else {
			if(bounds.x == x && bounds.y == y && bounds.width == w && bounds.height == h) {
				return;
			}
			bounds.x = x;
			bounds.y = y;
			bounds.width = w;
			bounds.height = h;
		}
		leftWin.setBounds(x - B, y, B, h);
		rightWin.setBounds(x + w, y, B, h);
		topWin.setBounds(x - B, y - B, w + 2 * B, B);
		botWin.setBounds(x - B, y + h, w + 2 * B, B);
		if(imgWin != null) {
		    imgWin.setBounds(x, y, w, h);
		}
	}

	Thread waiter = null;

	public void update() {
		leftWin.update();
		rightWin.update();
		topWin.update();
		botWin.update();
		if(imgWin != null) {
		    imgWin.update();
		}
	}

	public void dispose() {
		leftWin.dispose();
		rightWin.dispose();
		topWin.dispose();
		botWin.dispose();
		def.dispose();
		if(imgWin != null) {
		    imgWin.dispose();
		    img.dispose();
		}
		disposed = true;
	}

	public boolean isDisposed() {
		return topWin.isDisposed();
	}

	public boolean isVisible() {
		return visible;
	}

}
