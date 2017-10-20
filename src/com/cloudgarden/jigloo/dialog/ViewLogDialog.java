package com.cloudgarden.jigloo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class ViewLogDialog extends org.eclipse.swt.widgets.Dialog {
	private Shell dialogShell;
	Button button3;
	private Button okButton;
	private Composite composite2;
	private Text logTextArea;
	private Label label1;

	String value = "", title = "";

	public ViewLogDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void initText(String value, String title) {
		this.value = value;
		this.title = title;
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(){
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM 
//					| SWT.APPLICATION_MODAL 
					| SWT.RESIZE | SWT.MAX);
			dialogShell.setText(getText());
			dialogShell.moveAbove(null);
			//Creating composite2
			composite2 = new Composite(dialogShell,SWT.NULL);
			GridLayout composite2Layout = new GridLayout();
			composite2.setLayout(composite2Layout);
			{
				label1 = new Label(composite2, SWT.NONE);
				label1.setText("Jigloo Parsing Log");
			}
			//Creating logTextArea
			logTextArea = new Text(composite2, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			{
				okButton = new Button(composite2, SWT.PUSH | SWT.CENTER);
				okButton.setText("OK");
				GridData okButtonLData = new GridData();
				okButtonLData.heightHint = 22;
				okButtonLData.horizontalAlignment = GridData.END;
				okButtonLData.widthHint = 92;
				okButton.setLayoutData(okButtonLData);
				okButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						okButtonWidgetSelected(evt);
					}
				});
			}
			//Creating cLabel2
			//Creating textText
			//Creating composite1
			//Creating okButton
			//Creating cancelButton

			//Setting properties for TextComponentDialog
			dialogShell.setText("Enter name and text for new component");
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(402,129));

			//Setting layout constraints for logTextArea
			GridData nameTextLData = new GridData();
			nameTextLData.horizontalAlignment = GridData.FILL;
			nameTextLData.grabExcessHorizontalSpace = true;
			nameTextLData.grabExcessVerticalSpace = true;
			nameTextLData.verticalAlignment = GridData.FILL;
			logTextArea.setLayoutData(nameTextLData);

			composite2Layout.marginWidth = 10;
			composite2Layout.marginHeight = 10;
			composite2.layout();
			
			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.type = SWT.HORIZONTAL;
			dialogShell.layout();
			Rectangle bounds = dialogShell.computeTrim(0, 0, 402,129);
			dialogShell.setSize(452, 348);
			postInitGUI();
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getValue() {
		return value;
	}
	public void preInitGUI() {}

	public void postInitGUI() {
		dialogShell.setText(title);
		logTextArea.setText(value);
	}
	
	public void updateLogText(String txt) {
		if(logTextArea != null && !logTextArea.isDisposed())
			logTextArea.setText(txt);
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
	public static void showGUI(){
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			ViewLogDialog inst = new ViewLogDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Auto-generated event handler method */
	public void okButtonWidgetSelected(SelectionEvent evt) {
		exit(false);
	}

	/** Auto-generated event handler method */
	public void cancelButtonWidgetSelected(SelectionEvent evt) {
		exit(true);
	}

	private void exit(boolean cancel) {
		if (cancel) {
			value = null;
			title = null;
		} else {
			value = logTextArea.getText();
		}
		Runnable r = new Runnable() {
			public void run() {
				composite2.getParent().dispose();
			}
		};
		Display.getDefault().asyncExec(r);
	}

}
