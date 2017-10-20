/*
 * Created on Jun 16, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.controls;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.cloudgarden.jigloo.interfaces.IWidgetManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OrderableGroup extends Group implements IWidgetManager {

	private WidgetManager widMan;

	public OrderableGroup(Composite parent, int style) {
		super(parent, style);
		widMan = new WidgetManager(parent);
		//initialize WidgetManager's children array
		widMan.getChildren(super.getChildren());
	}

	public void hide(Control control) {
		widMan.hide(control);
	}

	public void moveTo(Control control, int pos) {
		widMan.moveTo(control, pos);
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
	public void clearAll() {
		widMan.clearAll();
	}
	public int getPosition(Control control) {
		return widMan.getPosition(control);
	}
	protected void checkSubclass() {}

}