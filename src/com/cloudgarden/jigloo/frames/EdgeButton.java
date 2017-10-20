/*
 * Created on Jul 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EdgeButton extends Canvas {

	
	private boolean reverseBlackAndGray = false;
	
	private Color black = ColorManager.getColor(0, 0, 0);
	private Color gray = ColorManager.getColor(200, 200, 200);
	private Color darkGreen = ColorManager.getColor(50, 150, 50);
	private Color darkRed = ColorManager.getColor(200, 100, 100);
	private Color red = ColorManager.getColor(255, 0, 0);
	private Color white = ColorManager.getColor(250, 250, 250);
	private Color green = ColorManager.getColor(0, 255, 0);
	private Color orange = ColorManager.getColor(255, 100, 0);
	private int mode;
	WindowFrameMouseMoveListener lis;
	WindowFrame wf;
	
	private PaintListener pl2 = new PaintListener() {
		public void paintControl(PaintEvent pe) {
			Control con = (Control) pe.getSource();
			int x = pe.x;
			int y = pe.y;
			int w = con.getSize().x;
			int h = con.getSize().y;
			Color fg1 = black, fg2 = gray, bg = red;
			if(mode == WindowFrame.MODE_MOVE) {
			    
			    if(reverseBlackAndGray) {
					fg2 = black;
			        fg1 = gray;
			    }
			    
			    if(wf.isMoveTarget)
			        fg1 = fg2 = green;
			    
			    pe.gc.setForeground(fg1);
			    
			    if(w > h)
					pe.gc.drawLine(0, 1, w, 1);
				else
					pe.gc.drawLine(1, 0, 1, h);

			    pe.gc.setForeground(fg2);
				
				if(w > h)
					pe.gc.drawLine(0, 0, w, 0);
				else
					pe.gc.drawLine(0, 0, 0, h);
			} else {
				if(wf.isMoveTarget)
					pe.gc.setBackground(green);
				else
					pe.gc.setBackground(red);
				pe.gc.fillRectangle(0, 0, w, h);
				pe.gc.setForeground(white);
				pe.gc.drawRectangle(0, 0, w-1, h-1);
				if(wf.isMoveTarget)
					pe.gc.setForeground(darkGreen);
				else
					pe.gc.setForeground(darkRed);
				pe.gc.drawLine(1, 1, w-2, 1);
				pe.gc.drawLine(w-2, 1, w-2, h-2);
			}
		}
	};
	
	MouseAdapter mouseClicker = new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
			wf.handleMouseDown(e);
		}
		public void mouseUp(MouseEvent e) {
			wf.handleMouseUp(e);
		}
	};
	
	public EdgeButton(Composite arg0, WindowFrame wf, FormEditor editor, 
	        int mode, Cursor cursor) {
	    this(arg0, wf, editor, mode, cursor, false);
	}

	public EdgeButton(Composite arg0, WindowFrame wf, FormEditor editor, 
		        int mode, Cursor cursor, boolean reverseBlackAndGray) {
	    //The NO_FOCUS is very important so that the WF doesn't steal focus
	    //from the FormEditor's mainComp!!!
		super(arg0, SWT.NO_MERGE_PAINTS | SWT.NO_FOCUS | SWT.NO_BACKGROUND);
	    this.reverseBlackAndGray = reverseBlackAndGray;
		setEnabled(true);
		this.mode = mode;
		this.wf = wf;
		lis = new WindowFrameMouseMoveListener(wf, this, 0, editor);
		lis.setResizeMode(mode, cursor);
		addMouseMoveListener(lis);
		addMouseListener(mouseClicker);
		addPaintListener(pl2);
		moveAbove(null);
	}
	
	public void dispose() {
		super.dispose();
		removePaintListener(pl2);
		removeMouseListener(mouseClicker);
		removeMouseMoveListener(lis);
	}

	public void setBounds(int arg0, int arg1, int arg2, int arg3) {
		super.setBounds(arg0, arg1, arg2, arg3);
//		moveAbove(null);
		update();
	}

	public void setLocation(int arg0, int arg1) {
		super.setLocation(arg0, arg1);
//		moveAbove(null);
		update();
	}
	
}
