package com.cloudgarden.jigloo.dialog;

/**
 */
public class ExtractDialog extends org.eclipse.swt.widgets.Dialog {

	private org.eclipse.swt.widgets.Shell dialogShell;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			org.eclipse.swt.widgets.Display display = new org.eclipse.swt.widgets.Display();
			org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
			ExtractDialog inst = new ExtractDialog(shell, org.eclipse.swt.SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExtractDialog(org.eclipse.swt.widgets.Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			org.eclipse.swt.widgets.Shell parent = getParent();
			dialogShell = new org.eclipse.swt.widgets.Shell(parent, org.eclipse.swt.SWT.DIALOG_TRIM | org.eclipse.swt.SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new org.eclipse.swt.layout.GridLayout());
			dialogShell.layout();

			org.eclipse.swt.graphics.Rectangle bounds = dialogShell.computeTrim(0, 0, 291,127);
			dialogShell.setSize(bounds.width, bounds.height);
			dialogShell.open();
			org.eclipse.swt.widgets.Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI(){
		try {
			org.eclipse.swt.widgets.Display display = new org.eclipse.swt.widgets.Display();
			org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
			ExtractDialog inst = new ExtractDialog(shell, org.eclipse.swt.SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
