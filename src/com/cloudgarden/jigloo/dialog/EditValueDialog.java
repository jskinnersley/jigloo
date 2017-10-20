package com.cloudgarden.jigloo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.util.JiglooUtils;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class EditValueDialog extends org.eclipse.swt.widgets.Dialog {
	private Shell dialogShell;
	Button button3;
	private Button cancelButton;
	private Button okButton;
	private Composite composite2;
	private Composite composite1;
	private Text nameText;
	private Label label1;
	private CLabel cLabel1;

	String value = "", title = "", label = null;

	public EditValueDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void initText(String value, String title) {
	    initText(value, title, null);
	}

	public void initText(String value, String title, String label) {
		this.value = value;
		this.title = title;
		this.label = label;
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(){
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | 
					SWT.APPLICATION_MODAL);
			dialogShell.setText(getText());
			//Creating composite2
			composite2 = new Composite(dialogShell,SWT.NULL);
			GridLayout composite2Layout = new GridLayout();
			composite2.setLayout(composite2Layout);
			//Creating cLabel1
            GridData cLabel1LData = new GridData();
            cLabel1LData.horizontalAlignment = GridData.FILL;
            cLabel1LData.grabExcessHorizontalSpace = true;
			cLabel1 = new CLabel(composite2,SWT.NULL);
            cLabel1.setLayoutData(cLabel1LData);
			//Creating nameText
			nameText = new Text(composite2,SWT.BORDER);
			{
				label1 = new Label(composite2, SWT.NONE);
				GridData label1LData = new GridData();
				label1LData.widthHint = -1;
				label1LData.heightHint = -1;
				label1.setLayoutData(label1LData);
				label1.setText(" ");
			}
			//Creating cLabel2
			//Creating textText
			//Creating composite1
			composite1 = new Composite(composite2, SWT.NULL);
			GridLayout composite1Layout = new GridLayout();
			composite1.setLayout(composite1Layout);
			//Creating okButton
			okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
			//Creating cancelButton
			cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
	
			//Setting properties for TextComponentDialog
			dialogShell.setText("Enter name and text for new component");
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(402,129));
	
			cLabel1.setText("Edit value:");
	
			//Setting layout constraints for nameText
			GridData nameTextLData = new GridData();
			nameTextLData.horizontalAlignment = GridData.FILL;
			nameTextLData.grabExcessHorizontalSpace = true;
			nameText.setLayoutData(nameTextLData);
			//Setting properties for nameText
			nameText.addTraverseListener( new TraverseListener() {
				public void keyTraversed(TraverseEvent evt) {
					nameTextKeyTraversed(evt);
				}
			});
	
			//Setting layout constraints for cLabel2
			//Setting properties for cLabel2
			//Setting Absolute layout for cLabel2

			//Setting layout constraints for textText
			//Setting properties for textText

			//Setting layout constraints for composite1
			GridData composite1LData = new GridData();
			composite1LData.horizontalAlignment = GridData.END;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1LData.grabExcessVerticalSpace = true;
			composite1LData.verticalAlignment = GridData.END;
			composite1.setLayoutData(composite1LData);
			//Setting properties for composite1

			//Setting layout constraints for okButton
			GridData okButtonLData = new GridData();
			okButtonLData.horizontalAlignment = GridData.FILL;
			okButtonLData.grabExcessHorizontalSpace = true;
			okButton.setLayoutData(okButtonLData);
			//Setting properties for okButton
			okButton.setText("OK");
			okButton.setSize(new org.eclipse.swt.graphics.Point(76,22));
			okButton.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					okButtonWidgetSelected(evt);
				}
			});

			//Setting layout constraints for cancelButton
			GridData cancelButtonLData = new GridData();
			cancelButtonLData.horizontalAlignment = GridData.FILL;
			cancelButtonLData.grabExcessHorizontalSpace = true;
			cancelButton.setLayoutData(cancelButtonLData);
			//Setting properties for cancelButton
			cancelButton.setText("Cancel");
			cancelButton.setSize(new org.eclipse.swt.graphics.Point(76,22));
			cancelButton.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					cancelButtonWidgetSelected(evt);
				}
			});
			//Setting Grid layout for composite1
			composite1Layout.marginWidth = 0;
			composite1Layout.marginHeight = 0;
			composite1Layout.numColumns = 2;
			composite1Layout.makeColumnsEqualWidth = true;
			//Setting Grid layout for composite2
			composite2Layout.marginWidth = 10;
			composite2Layout.marginHeight = 10;
			composite2Layout.numColumns = 2;
			composite2.layout();
			//Setting Fill layout for dialogShell
		    if(label != null)
		        cLabel1.setText(label);
			dialogShell.setText(title);
			nameText.setText(value);
//			nameText.setSelection(0, value.length());
			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.type = SWT.HORIZONTAL;
			dialogShell.pack();
			JiglooUtils.centerShellOnCursor(dialogShell);
			value = null;
			dialogShell.open();
			nameText.setFocus();
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
			EditValueDialog inst = new EditValueDialog(shell, SWT.NULL);
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
			value = nameText.getText();
		}
		Runnable r = new Runnable() {
			public void run() {
				composite2.getParent().dispose();
			}
		};
		Display.getDefault().asyncExec(r);
	}

	/** Auto-generated event handler method */
	public void nameTextKeyTraversed(TraverseEvent evt) {
		if (!evt.doit)
			return;
		if (evt.detail == SWT.TRAVERSE_RETURN)
			exit(false);
	}

}
