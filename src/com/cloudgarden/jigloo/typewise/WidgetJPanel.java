/*
 * Licensed Material - Property of PhilemonWorks B.V.
 * 
 * (c) copyright PhilemonWorks B.V. 2004 - All rights reserved.
 * Use, duplication or disclosure restricted. 
 * See http://www.philemonworks.com
 * 
 * VERSION HISTORY
 * 2004-02-02: created by emicklei
 * 
 * */
package com.cloudgarden.jigloo.typewise;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  This class can be used to preview Terminal Widgets
 */
public class WidgetJPanel extends JPanel {

	private com.philemonworks.typewise.Widget widget = null;
	private com.philemonworks.typewise.tools.WidgetRenderer renderer = new com.philemonworks.typewise.tools.WidgetRenderer();
	boolean showGrid = true;
	private Font currentFont = Font.decode("courier-bold-12");
	/**
	 * Previewer constructor comment.
	 */
	public WidgetJPanel() {
		super();
		initialize();
	}
	
	public int getGridCharWidth() {
		return renderer.charwidth;
	}
	
	public int getGridCharHeight() {
		return renderer.charheight;
	}
	
	public void displayGridOn(java.awt.Graphics g) {
		g.setColor(java.awt.Color.darkGray);
		int width = this.getWidth();
		int height = this.getHeight();
		for (int x = renderer.screenoffsetx; x < width; x = x + renderer.charwidth) {
			g.drawLine(x, renderer.screenoffsety, x, height);
		}
		for (int y = renderer.screenoffsety; y < height; y = y + renderer.charheight) {
			g.drawLine(renderer.screenoffsetx, y, width, y);
		}
	}
	
	public String getAppName() {
		return "WidgetJPanel";
	}
	/**
	 * Called whenever the part throws an exception.
	 * @param exception java.lang.Throwable
	 */
	private void handleException(Throwable exception) {

		/* Uncomment the following lines to print uncaught exceptions to stdout */
		System.out.println("--------- UNCAUGHT EXCEPTION ---------");
		exception.printStackTrace(System.out);
	}
	private void initialize() {
		try {
			renderer.initialize(7, 12, 0,0, 0, 0); // char width, char height, y offset, x offset, ?, ?
			//renderer.initialize(10, 16, 24, 4, 0, -2);
			setName("WidgetJPanel");
//			setPreferredSize(computeDimension());
//			this.setSize(220, 220);
			//setSize(220, renderer.screenoffsety + 220);
			//setBackground(java.awt.Color.black);
			setBackground(java.awt.Color.darkGray);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
	
	public void show(com.philemonworks.typewise.Widget aWidget) {
		try {
			this.widget = aWidget;
			setPreferredSize(computeDimension());
			setVisible(true);
		} catch (Throwable exception) {
			System.err.println("Exception occurred in show(Widget) of WidgetJPanel");
			exception.printStackTrace(System.out);
		}
	}
	
	public Dimension computeDimension() {
		int width = renderer.charwidth * widget.getColumns();
		int height = renderer.charheight * widget.getRows();
		// add space for titlebar and borders
		//height = height + 30;
		//width = width + 10;
		return new Dimension(width, height);
	}
	
	public void showGrid(boolean show) {
		this.showGrid = show;
		this.update(getGraphics());
	}

	/**
	 * Invoked when the mouse button has been moved on a component
	 * (with no buttons no down).
	 */
	public com.philemonworks.typewise.Widget locateWidget(java.awt.event.MouseEvent e) {
		int row = (e.getPoint().y - renderer.screenoffsety) / renderer.charheight + 1;
		int column = (e.getPoint().x - renderer.screenoffsetx) / renderer.charwidth + 1;
		return widget.widgetUnderPoint(row, column);
	}
	
	public void paint(java.awt.Graphics g) {
//	    FontMetrics fm = g.getFontMetrics(currentFont);
//	    Rectangle2D r2D = fm.getMaxCharBounds(g);
//		renderer.initialize((int)r2D.getWidth(), (int)r2D.getHeight(), 0,0, 0, 0); // char width, char height, y offset, x offset, ?, ?
		g.setFont(currentFont);
		renderer.setGraphics(g);
		widget.kindDo(renderer);
		if (showGrid)
			this.displayGridOn(g);
	}
	
	public static void main(String[] args) {
		WidgetJPanel wjp = new WidgetJPanel();
		JFrame frame = new JFrame();
		frame.getContentPane().add(wjp);
		frame.setVisible(true);
		wjp.show(new Calculator().mainScreen());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
