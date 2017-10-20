package com.cloudgarden.jigloo.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.FontManager;
import com.cloudgarden.jigloo.util.DelayableRunnable;

public class ToolTipManager {

	private static Composite toolTipShell = null;
	private static CLabel toolTip = null;
	private static DelayableRunnable hider = new DelayableRunnable(3000, true) {
		public void run() {
			if(toolTipShell != null && !toolTipShell.isDisposed())
				toolTipShell.setVisible(false);
		}
	};
	
	public static void hideToolTip() {
		if(toolTipShell != null && !toolTipShell.isDisposed())
			toolTipShell.setVisible(false);
	}
	
	public static void showToolTip(String text, Control control) {
		Point pt = control.toDisplay(0, 0);
		Point ptp = control.getShell().toDisplay(0, 0);
		if(toolTipShell == null) {
			toolTipShell = new Composite(control.getShell(), SWT.NULL);
			toolTipShell.setLayout(new FillLayout());
			toolTip = new CLabel(toolTipShell, SWT.NULL);
			Font f = toolTip.getFont();
			FontData fd = f.getFontData()[0];
			toolTip.setFont(FontManager.getFont(fd.getName(), fd.getHeight() - 2, SWT.NORMAL, false, false));
			toolTipShell.setEnabled(false);
		}
		Rectangle b = control.getBounds();
		toolTipShell.moveAbove(null);
		toolTip.setText(text);
		toolTip.setBackground(ColorManager.getColor(250, 250, 200));
		toolTipShell.layout(true);
		toolTipShell.pack(true);
		toolTipShell.setLocation(pt.x -ptp.x + b.width, pt.y - ptp.y + b.height);
		toolTipShell.setVisible(true);
//		hider.trigger();
	}
}
