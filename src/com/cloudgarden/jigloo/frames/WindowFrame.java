/*
 * Created on Jun 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.CursorManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WindowFrame {

	private boolean stippled = false;
//	private Control leftWin, rightWin, topWin, botWin;
	private Control tlWin, tcWin, trWin, mlWin, mrWin, blWin, bcWin, brWin;
	private Control 
	tlmWin, 
//	trmWin, 
	ltmWin, 
//	lbmWin, 
	rtmWin, 
//	rbmWin, 
	blmWin; 
//	brmWin;
	private MenuButton menuWin;
	private int B = 6;
	private Rectangle bounds;
	private boolean visible = false;
	private boolean disposed = false;
	private int resizeMode = 0;
	private int mode = 0;
	private int bgCol = SWT.COLOR_BLUE;
	private Cursor cursor, move, def, UD, LR, NW, SW, NO;
	private Composite root;
	private boolean mouseDown = false;
	private boolean isMoving = false;
	private boolean isAllowed = true;
	boolean isMoveTarget = false;
	private Point mousePoint, mouseDownPoint;
	private FormComponent target;
	private Rectangle origBounds, obCopy;

	public static int MODE_INVISIBLE = 0;
	public static int MODE_IDLE = 1;
	public static int MODE_MOVE = 2;
	public static int MODE_RESIZE_L = 3;
	public static int MODE_RESIZE_R = 4;
	public static int MODE_RESIZE_T = 5;
	public static int MODE_RESIZE_B = 6;
	public static int MODE_RESIZE_TL = 7;
	public static int MODE_RESIZE_TR = 8;
	public static int MODE_RESIZE_BL = 9;
	public static int MODE_RESIZE_BR = 10;

	long lastMove = -1;

	private static Image stipple;
	private Color resizeCol = ColorManager.getColor(0, 0, 0);
	
	private PaintListener pl = new PaintListener() {
		public void paintControl(PaintEvent pe) {
			if (isMoveTarget)
				return;
			Control con = (Control) pe.getSource();
			int x = pe.x;
			int y = pe.y;
			int w = con.getSize().x;
			int h = con.getSize().y;
			//System.out.println("paint " + x + ", " + y + ", " + w + ", " + h);

			if (w > h) {
				pe.gc.setBackground(resizeCol);
				for (int i = 0; i < 5; i += 2) {
					pe.gc.fillRectangle(i * w / 5, 0, w / 5, h);
				}

			} else {
				pe.gc.setBackground(resizeCol);
				for (int i = 0; i < 5; i += 2) {
					pe.gc.fillRectangle(0, i * h / 5, w, h / 5);
				}

			}
		}
	};
	

	public WindowFrame(Composite parent, final FormEditor editor) {
		disposed = false;
		root = parent;
		if (editor.isInCWTMode())
			resizeCol = ColorManager.getColor(80, 80, 80);
		
		move = CursorManager.getCursor(SWT.CURSOR_SIZEALL);
		def = CursorManager.getCursor(SWT.CURSOR_ARROW);
		NO = CursorManager.getCursor(SWT.CURSOR_NO);
		UD = CursorManager.getCursor(SWT.CURSOR_SIZENS);
		LR = CursorManager.getCursor(SWT.CURSOR_SIZEWE);
		NW = CursorManager.getCursor(SWT.CURSOR_SIZENWSE);
		SW = CursorManager.getCursor(SWT.CURSOR_SIZENESW);

		tlmWin = new EdgeButton(parent, this, editor, MODE_MOVE, move);
		ltmWin = new EdgeButton(parent, this, editor, MODE_MOVE, move);
		rtmWin = new EdgeButton(parent, this, editor, MODE_MOVE, move, true);
		blmWin = new EdgeButton(parent, this, editor, MODE_MOVE, move, true);

		tlWin = new EdgeButton(parent, this, editor, MODE_RESIZE_TL, NW);
		tcWin = new EdgeButton(parent, this, editor, MODE_RESIZE_T, UD);
		trWin = new EdgeButton(parent, this, editor, MODE_RESIZE_TR, SW);
		blWin = new EdgeButton(parent, this, editor, MODE_RESIZE_BL, SW);
		bcWin = new EdgeButton(parent, this, editor, MODE_RESIZE_B, UD);
		brWin = new EdgeButton(parent, this, editor, MODE_RESIZE_BR, NW);
		mlWin = new EdgeButton(parent, this, editor, MODE_RESIZE_L, LR);
		mrWin = new EdgeButton(parent, this, editor, MODE_RESIZE_R, LR);
		
		menuWin = new MenuButton(parent, editor);
		
		bounds = new Rectangle(0, 0, 10, 10);
		
	}
	
	public void deactivate() {
	    if(menuWin != null)
	        menuWin.deactivate();
	}

	public Point getMousePoint() {
		return mousePoint;
	}

	public boolean isMouseDown() {
		return mouseDown;
	}

	public Cursor getNWCursor() {
		return NW;
	}
	public Cursor getSWCursor() {
		return SW;
	}
	public Cursor getUDCursor() {
		return UD;
	}
	public Cursor getLRCursor() {
		return LR;
	}
	public Cursor getMoveCursor() {
		return move;
	}

	void handleMouseDown(MouseEvent e) {
		mouseDown = true;
		//mousePoint = new Point(e.x, e.y);
		Control ctrl = (Control) e.widget;
		if ((e.stateMask & SWT.CTRL) != 0)
			ctrl.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		mouseDownPoint = ctrl.toDisplay(e.x, e.y);
		mousePoint = ctrl.toDisplay(e.x, e.y);
		origBounds = getBoundsCopy();
	}
	
	void handleMouseUp(MouseEvent e) {
	    if(target.getEditor() == null)
	        return;
	    if(!mouseDown)
	        return;
		mouseDown = false;
		Control ctrl = (Control) e.widget;
		Point mup = ctrl.toDisplay(e.x, e.y);
        boolean shiftDown = (e.stateMask & SWT.SHIFT) != 0;

		if (mup.x == mouseDownPoint.x && mup.y == mouseDownPoint.y)
			return;

		int x = bounds.x;
		int y = bounds.y;
		int w = bounds.width;
		int h = bounds.height;

		FormComponent par = target.getParent();

		if (par != null) {
			if (par.isA(CoolItem.class))
				par = par.getParent();
			Rectangle pb = par.getBoundsRelativeTo(target.getEditor().getViewportControl());
			
			int dx = pb.x;
			int dy = pb.y;

			x -= dx;
			y -= dy;
		}

		Rectangle rec2 = new Rectangle(x, y, w, h);

		//set mode to idle, so that the frame can be re-aligned
		mode = MODE_IDLE;
		
		target.getEditor().handleWindowFrameResized(WindowFrame.this, rec2, target, shiftDown);
	}

	public FormComponent getFormComponent() {
		return target;
	}

	/**
	 * re-aligns the WindowFrame over the given FormComponent,
	 * but only if the editor isn't toggling
	 * @param parent
	 */
	public void setFormComponent(FormComponent parent) {
	    //System.out.println("WF setFormComponent " + parent);
	    try {
	        if(tcWin != null && tcWin.isDisposed())
	        	return;
	        
	        mouseDown = false;
	        target = parent;
	        menuWin.setFormComponent(parent);
	        if(parent.isRoot()) {
	            setToolTipText("hold 'SHIFT' down to resize form WITHOUT setting it's size in code");
	        } else if(parent.getParent() != null && parent.getParent().usesGridTypeLayout()) {
	            setToolTipText("hold 'SHIFT' down to set preferred size");
	        } else {
	            setToolTipText(null);
	        }
	    } catch(Throwable t) {
	        t.printStackTrace();
	    }

	}

	private void setToolTipText(String text) {
        tcWin.setToolTipText(text);
        trWin.setToolTipText(text);
        tlWin.setToolTipText(text);
        brWin.setToolTipText(text);
        blWin.setToolTipText(text);
        bcWin.setToolTipText(text);
        mlWin.setToolTipText(text);
        mrWin.setToolTipText(text);
	}
	public void realign() {
	    //		System.err.println("WF REALIGN " + target + ", " + mode);
	    
	    if (mode == MODE_MOVE
	            || mode == MODE_RESIZE_B
	            || mode == MODE_RESIZE_BL
	            || mode == MODE_RESIZE_BR
	            || mode == MODE_RESIZE_T
	            || mode == MODE_RESIZE_TL
	            || mode == MODE_RESIZE_TR
	            || mode == MODE_RESIZE_L
	            || mode == MODE_RESIZE_R)
	        return;
	    
	    if(target == null && target.isDisposed())
	        return;
	    try {
	        Rectangle rec = target.getBoundsRelativeToViewport();
	        if(false)
	        	setCornerVisibility(!target.isRootShell());
	        setBounds(rec);
	    } catch(Throwable t) {
	        jiglooPlugin.handleError(t);
		}
	}

	private Control initSide(Composite parent) {
		//		Control shell = new Shell(parent, SWT.NO_TRIM);
	    
	    //The NO_FOCUS is very important so that the WF doesn't steal focus
	    //from the FormEditor's mainComp!!!
		Control shell = new Canvas(parent, SWT.NULL | SWT.NO_FOCUS);
		shell.setEnabled(true);
		shell.setBackground(parent.getDisplay().getSystemColor(bgCol));
		//if (stippled)
		shell.addPaintListener(pl);
		return shell;
	}

	public void setMoveTargetColor(boolean set) {
		if (set) {
			setColor(SWT.COLOR_DARK_GREEN);
		} else {
			setColor(SWT.COLOR_BLUE);
		}
		isMoveTarget = set;
		tlmWin.redraw();
		blmWin.redraw();
		rtmWin.redraw();
		ltmWin.redraw();
		setCornerVisibility(!isMoveTarget);
	    tlWin.redraw();
	    tcWin.redraw();
	    trWin.redraw();
	    mlWin.redraw();
	    mrWin.redraw();
	    blWin.redraw();
	    bcWin.redraw();
	    brWin.redraw();
	}

	/**
     * @param c
     */
    private void setCornerVisibility(boolean visible) {
		if(visible) {
			tlWin.setVisible(true);
			tcWin.setVisible(true);
			trWin.setVisible(true);
			mlWin.setVisible(true);
			mrWin.setVisible(true);
			blWin.setVisible(true);
			bcWin.setVisible(true);
			brWin.setVisible(true);
		} else {
			tlWin.setVisible(false);
			tcWin.setVisible(false);
			trWin.setVisible(false);
			mlWin.setVisible(false);
			mrWin.setVisible(false);
			blWin.setVisible(false);
			bcWin.setVisible(false);
			brWin.setVisible(false);
//			menuWin.setVisible(false);
		}
    }

    private void setColor(int col) {
		if (col == bgCol)
			return;
		Color c = null;
//		if(topWin != null) {
//			c = topWin.getDisplay().getSystemColor(col);
//			topWin.setBackground(c);
//			botWin.setBackground(c);
//			leftWin.setBackground(c);
//			rightWin.setBackground(c);
//		}
		bgCol = col;
	}

	public void setVisible(boolean visible) {
//		if(this.visible == visible)
//			return;
		
		if (tlWin != null) {
			if(tlWin.isDisposed())
				return;
			if (visible != tlmWin.isVisible()) {
				tlWin.setVisible(visible);
				tcWin.setVisible(visible);
				trWin.setVisible(visible);
				mlWin.setVisible(visible);
				mrWin.setVisible(visible);
				blWin.setVisible(visible);
				bcWin.setVisible(visible);
				brWin.setVisible(visible);
				tlmWin.setVisible(visible);
				blmWin.setVisible(visible);
				ltmWin.setVisible(visible);
				rtmWin.setVisible(visible);
			}
		}
		if(visible) {
			if(menuWin != null && !menuWin.isDisposed())
				menuWin.setFormComponent(target);
			tlmWin.moveAbove(null);
			blmWin.moveAbove(null);
			ltmWin.moveAbove(null);
			rtmWin.moveAbove(null);
			tlWin.moveAbove(null);
			tcWin.moveAbove(null);
			trWin.moveAbove(null);
			mlWin.moveAbove(null);
			mrWin.moveAbove(null);
			blWin.moveAbove(null);
			bcWin.moveAbove(null);
			brWin.moveAbove(null);
			menuWin.moveAbove(null);
		} else {
		    menuWin.setVisible(false);
			mode = MODE_INVISIBLE;
		}
		this.visible = visible;
	}

	/**
	 * Returns the bounds relative to the editor's viewport control
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	public Rectangle getOrigBoundsCopy() {
		return new Rectangle(origBounds.x, origBounds.y, origBounds.width, origBounds.height);
	}

	public Rectangle getBoundsCopy() {
		if(bounds == null)
			return new Rectangle(0, 0, 10, 10);
		return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public void setBounds(Rectangle rec) {
//		System.err.println("WF setBounds " + rec+", "+target);
//		new Exception().printStackTrace();

		if (rec == null || target.getEditor() == null || rec == null)
			return;
		bounds = rec;
//		if (topWin != null && topWin.isDisposed())
//			return;
		if (tlWin != null && tlWin.isDisposed())
			return;
		Rectangle nb = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
		
		target.getEditor().snapToGrid(nb, target, true);

		if(nb.width < B)
		    nb.width = B;
		if(nb.height < B)
		    nb.height = B;

		if(tlWin != null) {

			tlmWin.setBounds(nb.x, nb.y-2, nb.width, 2);
			blmWin.setBounds(nb.x, nb.y+nb.height, nb.width, 2);
			ltmWin.setBounds(nb.x-2, nb.y, 2, nb.height);
			rtmWin.setBounds(nb.x+nb.width, nb.y, 2, nb.height);

			tcWin.setBounds(nb.x+(nb.width - B)/2, nb.y-B/2, B, B);
			mlWin.setBounds(nb.x - B/2, nb.y+(nb.height-B)/2, B, B);
			mrWin.setBounds(nb.x + nb.width-B/2,nb.y+(nb.height-B)/2, B, B);
			bcWin.setBounds(nb.x+(nb.width - B)/2, nb.y+nb.height-B/2, B, B);

			tlWin.setBounds(nb.x - B/2, nb.y-B/2, B, B);
			trWin.setBounds(nb.x + nb.width-B/2, nb.y-B/2, B, B);
			blWin.setBounds(nb.x - B/2, nb.y+nb.height-B/2, B, B);
			brWin.setBounds(nb.x + nb.width-B/2, nb.y+nb.height-B/2, B, B);
			
			menuWin.setBounds(nb.x + nb.width - B/2 - 11, nb.y-5, 11, 10);
//			if(menuWin.isVisible())
//			    menuWin.moveAbove(null);
			
		}
	}

	Thread waiter = null;

	private void setLocation(int x, int y) {
	    if(target.getEditor() == null)
	        return;
	    
//		System.err.println("WF set location "+x+", "+y+", "+target);
		bounds.x = x;
		bounds.y = y;
		if (lastMove < 0)
			lastMove = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		//if(now - lastMove < 50) return;
		lastMove = now;
		Rectangle nb = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
		target.getEditor().snapToGrid(nb, target, true);
		
//		if(topWin !=null) {
//			leftWin.setLocation(nb.x - B, nb.y);
//			rightWin.setLocation(nb.x + nb.width, nb.y);
//			topWin.setLocation(nb.x - B, nb.y - B);
//			botWin.setLocation(nb.x - B, nb.y + nb.height);
//		}
		
		if(tlWin !=null) {

			tlmWin.setLocation(nb.x, nb.y-2);
			blmWin.setLocation(nb.x, nb.y+nb.height);
			ltmWin.setLocation(nb.x-2, nb.y);
			rtmWin.setLocation(nb.x+nb.width, nb.y);
			
			tcWin.setLocation(nb.x+(nb.width - B)/2, nb.y-B/2);
			mlWin.setLocation(nb.x - B/2, nb.y+(nb.height-B)/2);
			mrWin.setLocation(nb.x + nb.width-B/2,nb.y+(nb.height-B)/2);
			bcWin.setLocation(nb.x+(nb.width - B)/2, nb.y+nb.height-B/2);

			tlWin.setLocation(nb.x - B/2, nb.y-B/2);
			trWin.setLocation(nb.x + nb.width-B/2, nb.y-B/2);
			blWin.setLocation(nb.x - B/2, nb.y+nb.height-B/2);
			brWin.setLocation(nb.x + nb.width-B/2, nb.y+nb.height-B/2);

			menuWin.setLocation(nb.x + nb.width - B/2 - 11, nb.y-5);
//			if(menuWin.isVisible())
//			menuWin.moveAbove(null);

		}
	}

	public void moveBy(int x, int y) {
//		System.err.println("MOVE BY " +x+", "+y+", "+ target);
		setLocation(x + bounds.x, y + bounds.y);
	}

	public void dispose() {
	    if(disposed)
	        return;
	    try {

	    	if(menuWin != null && !menuWin.isDisposed())
	    	    menuWin.dispose();
	    	
		    if(tlWin != null && !tlWin.isDisposed()) {
		    	tlWin.dispose();
		    	tcWin.dispose();
		    	trWin.dispose();
		    	mlWin.dispose();
		    	mrWin.dispose();
		    	blWin.dispose();
		    	tcWin.dispose();
		    	trWin.dispose();
		    	ltmWin.dispose();
		    	rtmWin.dispose();
		    	tlmWin.dispose();
		    	blmWin.dispose();
		    }
			disposed = true;
	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	}

	public boolean isDisposed() {
		if(tlWin != null)
			return tlWin.isDisposed();
		return true;
	}

	public boolean isVisible() {
		return visible;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setMoveMode(boolean set, boolean copy) {
		int col;
		if (set) {
			//if (isMoving) return;
			if (copy) {
				col = SWT.COLOR_RED;
			} else {
				col = SWT.COLOR_DARK_GRAY;
			}
			hideMenuWin();
		} else {
			mode = MODE_IDLE;
			col = SWT.COLOR_BLUE;
			showMenuWin();
		}
		isMoving = set;
		setColor(col);
	}

    /**
     * 
     */
	private void hideMenuWin() {
	    if(menuWin != null && !menuWin.isDisposed())
	        menuWin.setVisible(false);
	    if(tcWin != null && !tcWin.isDisposed()) {
	        tcWin.setVisible(false);
	        mlWin.setVisible(false);
	        mrWin.setVisible(false);
	        bcWin.setVisible(false);
	        
	        tlWin.setVisible(false);
	        trWin.setVisible(false);
	        blWin.setVisible(false);
	        brWin.setVisible(false);
	    }
    }

    /**
     * 
     */
    private void showMenuWin() {
        if(menuWin != null && !menuWin.isDisposed())
            menuWin.setFormComponent(target);
        
	    if(tcWin != null && !tcWin.isDisposed()) {
	        tcWin.setVisible(true);
	        mlWin.setVisible(true);
	        mrWin.setVisible(true);
	        bcWin.setVisible(true);
	        
	        tlWin.setVisible(true);
	        trWin.setVisible(true);
	        blWin.setVisible(true);
	        brWin.setVisible(true);
	    }
    }

}
