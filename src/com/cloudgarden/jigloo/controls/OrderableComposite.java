/*
 * Created on Jun 16, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.controls;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.interfaces.IDummyShellSupplier;
import com.cloudgarden.jigloo.interfaces.IWidgetManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OrderableComposite extends Composite
	implements IWidgetManager, IDummyShellSupplier {

	private WidgetManager widMan;
	private boolean useDummyShell = false;

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		if(!isDisposed())
			super.dispose();
		if(widMan != null)
			widMan.dispose();
		widMan = null;
	}
	
	public OrderableComposite(Composite parent, int style) {
		super(parent, style);
		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}});
		widMan = new WidgetManager(parent);
		widMan.getChildren(super.getChildren());
	}

	public void useDummyShell(boolean use) {
		useDummyShell = use;
	}

	public void hide(Control control) {
		widMan.hide(control);
	}

	private static Shell dummyShell;

	/**
	 * this stops a new instance of a class created inside Jigloo
	 * from setting Eclipse's menu bar (via getShell().setMenuBar()
	 */
	public Shell getShell() {
	    
	    if(true)	    //v3.8.1 - otherwise this crashes Eclipse 3.2M4
	        return super.getShell();
	    
		if (!useDummyShell)
			return super.getShell();
		System.out.println("Using dummy shell for "+this);
		if (dummyShell == null)
			dummyShell = new Shell();
		return dummyShell;
	}

	public void clearAll() {
		widMan.clearAll();
	}

	public void moveUp(Control control) {
		widMan.moveUp(control);
	}
	public void moveDown(Control control) {
		widMan.moveDown(control);
	}
	public void setChildAt(int pos, Control control) {
		widMan.setChildAt(pos, control);
	}

	public boolean isHidden(Control control) {
		return widMan.isHidden(control);
	}

	public Control[] getChildren() {
		return widMan.getChildren(super.getChildren());
	}

	public void moveTo(Control control, int pos) {
		widMan.moveTo(control, pos);
	}

	public int getPosition(Control control) {
		return widMan.getPosition(control);
	}
}