package com.cloudgarden.jigloo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.editors.FormEditor;
public class AnchorDialog extends org.eclipse.swt.widgets.Dialog {
	AnchorPanel anchorPanel1;
	Shell dialogShell;
	FormEditor editor;

	public AnchorDialog(Shell parent, int style) {
		super(parent, style);
	}
	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(int x, int y) {
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell =
				new Shell(parent, SWT.DIALOG_TRIM);
			dialogShell.moveAbove(null);
			dialogShell.setText(getText());
			anchorPanel1 = new AnchorPanel(dialogShell, SWT.NULL);

			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.type = SWT.HORIZONTAL;
			dialogShell.pack();

			postInitGUI();
            Point sz = dialogShell.getSize();
            Rectangle db = Display.getDefault().getBounds();
            if(x+sz.x > db.width)
                x = db.width - sz.x;
            if(y+sz.y > db.height)
                y = db.height - sz.y;
			dialogShell.setLocation(x, y);
			dialogShell.open();
            dialogShell.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    anchorPanel1.dispose();
                }
            });

//			Display display = dialogShell.getDisplay();
//			while (!dialogShell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
//	        anchorPanel1.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
	    if(!dialogShell.isDisposed())
	        dialogShell.dispose();
	    if(!anchorPanel1.isDisposed())
	        anchorPanel1.dispose();
	}
	
	public void setFormEditor(FormEditor editor) {
		this.editor = editor;
	}

	/**
		* Add your pre-init code in here
		*/
	public void preInitGUI() {}
	/**
		* Add your post-init code in here
		*/
	public void postInitGUI() {
		if (editor != null) {
			anchorPanel1.init(editor);
		}
	}

	/** Auto-generated main method */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI() {
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			AnchorDialog inst = new AnchorDialog(shell, SWT.NULL);
			inst.open(10,10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public AnchorPanel getAnchorPanel() {
        return anchorPanel1;
    }
}
