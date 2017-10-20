/*
 * Created on Jul 6, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.typewise;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

/**
* Designed primarily to hold multiple Screens in a CardLayout
 */
public class WidgetHolder extends JPanel {

	private CardLayout layout;
	private WidgetJPanel refWJP;
	private Vector screens = new Vector();
	private HashMap wjps = new HashMap();

	public WidgetHolder() {
		super();
		layout = new CardLayout();
		setLayout(layout);
	}

	public int getGridCharWidth() {
		return refWJP.getGridCharWidth();
	}

	public int getGridCharHeight() {
		return refWJP.getGridCharHeight();
	}

	public void addWidget(com.philemonworks.typewise.Widget screen) {
		if (screens.contains(screen))
			return;
		screens.add(screen);
//		if(wjps.size() == 0)
//		    screen.setBackground(Color.red);
//		if(wjps.size() == 1)
//		    screen.setBackground(Color.green);
//		if(wjps.size() == 2)
//		    screen.setBackground(Color.blue);
		WidgetJPanel wjp = new WidgetJPanel();
		if (refWJP == null)
			refWJP = wjp;
		String lbl = "Screen" + screen.hashCode();
		add(wjp, lbl);
//		System.out.println("ADD "+lbl);
		wjps.put(screen, wjp);
		wjp.show(screen);
		showWidget(screen);
	}

	public void removeWidget(Object screen) {
		if (!screens.contains(screen))
			return;
		screens.remove(screen);
		remove((WidgetJPanel) wjps.get(screen));
	}

	private Object topScreen = null;

	public void showWidget(Object screen) {
		if(screen == null)
			return;
		layout.next(this);
//		System.out.println("SHOW "+"Screen" + screen.hashCode());
		layout.show(this, "Screen" + screen.hashCode());
		topScreen = screen;
	}

	public Object getTopScreen() {
		return topScreen;
	}

}
