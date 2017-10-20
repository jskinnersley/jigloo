package com.cloudgarden.jigloo.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class SwingDialog extends org.eclipse.swt.widgets.Dialog {
	
    private Shell dialogShell;
	private Component jPanel;
	
	public SwingDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(){
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.RESIZE | SWT.DIALOG_TRIM  | SWT.APPLICATION_MODAL
			        );
			dialogShell.setText(getText());
			dialogShell.setLayout(new FillLayout());
			Composite comp = new Composite(dialogShell, SWT.EMBEDDED);
			comp.setLayout(new FillLayout());
			java.awt.Frame frame = SWT_AWT.new_Frame(comp);
			frame.setLayout(new BorderLayout(10, 10));
			frame.add(jPanel, "Center");
			frame.pack();
			Dimension sz = frame.getPreferredSize();
			dialogShell.setSize(sz.width+10, sz.height+30);
//			dialogShell.layout();
			dialogShell.open();
			Rectangle sb = parent.getBounds();
			dialogShell.setLocation(sb.x + (sb.width - dialogShell.getSize().x) / 2, 
			        sb.y + (sb.height - dialogShell.getSize().y) / 2);
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setJPanel(Component jPanel) {
	    this.jPanel = jPanel;
	}
	
	public void exit() {
	    Display.getDefault().asyncExec(new Runnable() {
	        public void run() {
	    	    dialogShell.dispose();
	        }
	    });
	}

}
