/*
 * Created on Jul 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.interfaces;

import org.eclipse.swt.widgets.Control;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IWidgetManager {
	public abstract int getPosition(Control control);
	public abstract void hide(Control control);
	public abstract void moveTo(Control control, int pos);
	public abstract void moveUp(Control control);
	public abstract void moveDown(Control control);
	public abstract void setChildAt(int pos, Control control);
	public abstract boolean isHidden(Control control);
	public abstract void layout(boolean refresh);
	public abstract void clearAll();
}