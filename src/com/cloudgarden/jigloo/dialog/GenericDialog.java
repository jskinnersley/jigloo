package com.cloudgarden.jigloo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.editors.FormEditor;
public abstract class GenericDialog extends org.eclipse.swt.widgets.Dialog {
	Composite composite;
	Shell dialogShell;
	FormEditor editor;

	public GenericDialog(Shell parent) {
		super(parent, SWT.APPLICATION_MODAL);
	}
	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell =
				new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.moveAbove(null);
			dialogShell.setText(getText());
			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			composite = createComposite(dialogShell);
			Point sz = composite.getSize();
			dialogShell.pack();
            dialogShell.setSize(sz.x, sz.y);
            Rectangle db = Display.getDefault().getBounds();
            int x = (db.width - sz.x)/2;
            int y = (db.height - sz.y)/2;
			dialogShell.setLocation(x, y);
			dialogShell.open();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public abstract Composite createComposite(Shell dialogShell);
    
}
