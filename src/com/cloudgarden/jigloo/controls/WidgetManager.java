/*
 * Created on Jul 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.controls;

import java.util.Vector;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import com.cloudgarden.jigloo.interfaces.IWidgetManager;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WidgetManager implements IWidgetManager {

	private Control[] children;
	private Vector hidden = new Vector();
	private Widget widget;

	public WidgetManager(Widget widget) {
		this.widget = widget;
		widget.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}});
	}

	public int getPosition(Control control) {
		int i = 0;
		for (i = 0; i < children.length; i++) {
			if (children[i].equals(control))
				return i;
		}
		return -1;
	}

	public void hide(Control control) {
		if (!hidden.contains(control)) {
			hidden.add(control);
			control.setVisible(false);
		}
	}

//	public Control getChildAt(int pos) {
//		if (pos < 0 || pos >= children.length)
//			return null;
//		return children[pos];
//	}

	public void setChildAt(int pos, Control control) {
		if (pos < 0 || pos > children.length - 1)
			return;
		int index = getPosition(control);
		if (index <= 0 || index == pos)
			return;
		if (index < pos) {
			for (int i = index; i < pos; i++)
				children[i] = children[i + 1];
		} else {
			for (int i = index; i > pos; i--)
				children[i] = children[i - 1];
		}
		children[pos] = control;
	}

	public void moveTo(Control control, int pos) {
		int index = getPosition(control);
		if (index < 0) {
			System.err.println("WidgetManager.moveTo " + control + ", " + pos + ": not in parent " + this);
			return;
		}
		if (pos < 0 || pos > children.length - 1)
			return;
		if (pos == index)
			return;
		Control ctrl = children[index];
		if (pos <= index) {
			//moving up the order
			for (int i = index; i > pos; i--) {
				children[i] = children[i - 1];
			}
			ctrl.moveAbove(children[pos + 1]);
		} else {
			//moving down
			for (int i = index; i < pos; i++) {
				children[i] = children[i + 1];
			}
			ctrl.moveBelow(children[pos - 1]);
		}
		children[pos] = ctrl;
	}

	public void moveUp(Control control) {
		int index = getPosition(control);
		if (index <= 0)
			return;
		Control tmp = children[index - 1];
		children[index - 1] = children[index];
		children[index] = tmp;
	}

	public void moveDown(Control control) {
		int index = getPosition(control);
		if (index < 0 || index + 1 > children.length - 1)
			return;

		Control tmp = children[index + 1];
		children[index + 1] = children[index];
		children[index] = tmp;
	}

	public boolean isHidden(Control control) {
		if (control == null)
			return false;
		if (control.isDisposed())
			return true;
		for (int i = 0; i < hidden.size(); i++) {
			Control ctrl = (Control) hidden.elementAt(i);
			if (ctrl.handle == control.handle)
				return true;
		}
		return false;
	}

	public Control[] getChildren(Control[] kids) {

		Vector extras = null;
		if (children == null) {
			children = kids;
		} else {
			int numNew = 0;
			for (int i = 0; i < kids.length; i++) {
				if (!isHidden(kids[i])) {
					int j = 0;
					for (j = 0; j < children.length; j++) {
						if (kids[i].equals(children[j]))
							break;
					}
					if (j == children.length) {
						if (extras == null)
							extras = new Vector();
						extras.add(kids[i]);
						numNew++;
					}
				}
			}
			int old = 0;
			for (int i = 0; i < children.length; i++) {
				if (isHidden(children[i])) {
					children[i] = null;
					old++;
				} else {
					int j = 0;
					for (j = 0; j < kids.length; j++) {
						if (kids[j].equals(children[i]))
							break;
						if (j == kids.length) {
							children[i] = null;
							old++;
						}
					}
				}
			}
			if (numNew + old + hidden.size() != 0) {
				Control[] newKids = new Control[children.length - old + numNew];
				int ind = 0;
				for (int i = 0; i < children.length; i++) {
					if (children[i] != null) {
						newKids[ind++] = children[i];
					}
				}
				for (int i = 0; i < numNew; i++) {
					newKids[ind + i] = (Control) extras.elementAt(i);
				}
				children = newKids;
			}
		}
		return children;
	}

	public void clearAll() {
		for (int i = 0; i < children.length; i++) {
			Control wid = children[i];
			if (wid != null && !wid.isDisposed())
				wid.dispose();
		}
		hidden.clear();
		children = new Control[0];
	}

	public void layout(boolean refresh) {}

	public void dispose() {
		widget = null;
		children = null;
		if(hidden != null)
			hidden.clear();
		hidden = null;
	}

}
