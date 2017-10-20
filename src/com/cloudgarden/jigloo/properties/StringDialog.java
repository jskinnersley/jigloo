package com.cloudgarden.jigloo.properties;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
public class StringDialog extends org.eclipse.swt.widgets.Dialog {
	private Button cancelButton;
	private Button okButton;
	private Composite composite1;
	private Shell dialogShell;

	public StringDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open() {
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell =
				new Shell(parent, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
			dialogShell.setText(getText());
            {
                text1 = new Text(dialogShell, SWT.MULTI | SWT.WRAP | SWT.BORDER);
                GridData text1LData = new GridData();
                text1LData.grabExcessHorizontalSpace = true;
                text1LData.grabExcessVerticalSpace = true;
                text1LData.horizontalAlignment = GridData.FILL;
                text1LData.verticalAlignment = GridData.FILL;
                text1.setLayoutData(text1LData);
            }
			composite1 = new Composite(dialogShell, SWT.NULL);

			//Creating okButton
			okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
			//Creating cancelButton
			cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);

			GridData composite1LData = new GridData();
			composite1LData.verticalAlignment = GridData.FILL;
			composite1LData.horizontalSpan = 3;
			composite1LData.horizontalAlignment = GridData.END;
			composite1.setLayoutData(composite1LData);
			//Setting properties for composite1

			//Setting layout constraints for okButton
			GridData okButtonLData = new GridData();
			okButtonLData.verticalAlignment = GridData.FILL;
			okButtonLData.horizontalAlignment = GridData.FILL;
			okButtonLData.grabExcessVerticalSpace = true;
			okButtonLData.grabExcessHorizontalSpace = true;
			okButton.setLayoutData(okButtonLData);
			//Setting properties for okButton
			okButton.setText("OK");
			okButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					okButtonWidgetSelected(evt);
				}
			});

			//Setting layout constraints for cancelButton
			GridData cancelButtonLData = new GridData();
			cancelButtonLData.verticalAlignment = GridData.FILL;
			cancelButtonLData.horizontalAlignment = GridData.FILL;
			cancelButtonLData.grabExcessVerticalSpace = true;
			cancelButtonLData.grabExcessHorizontalSpace = true;
			cancelButton.setLayoutData(cancelButtonLData);
			//Setting properties for cancelButton
			cancelButton.setText("Cancel");
			cancelButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					cancelButtonWidgetSelected(evt);
				}
			});
			//Setting Grid layout for composite1
			GridLayout composite1Layout = new GridLayout(2, true);
			composite1.setLayout(composite1Layout);
			composite1Layout.marginWidth = 5;
			composite1Layout.marginHeight = 2;
			composite1Layout.numColumns = 2;
			composite1Layout.makeColumnsEqualWidth = true;
			composite1Layout.horizontalSpacing = 5;
			composite1Layout.verticalSpacing = 5;
			//Setting Grid layout for dialogShell
			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.numColumns = 3;
			dialogShellLayout.horizontalSpacing = 0;
			dialogShell.layout();
			Rectangle bounds = dialogShell.computeTrim(0, 0, 359, 292);
			dialogShell.setSize(bounds.width, bounds.height);
			dialogShell.setDefaultButton(okButton);
			postInitGUI();
	    	JiglooUtils.centerShellOnCursor(dialogShell);
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
	/** Add your pre-init code in here 	*/
	public void preInitGUI() {}

	private HashMap fontMap;
	private Vector fontVec;

	/** Add your post-init code in here 	*/
	public void postInitGUI() {
	    text1.setText(value);
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
			StringDialog inst = new StringDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Text text1;
	private String value = null;

	public void setValue(String val) {
	    value = val;
	}

	public String getValue() {
		return value;
	}

	private void exit() {
		dialogShell.dispose();
	}

	/** Auto-generated event handler method */
	protected void cancelButtonWidgetSelected(SelectionEvent evt) {
		value = null;
		exit();
	}

	/** Auto-generated event handler method */
	protected void okButtonWidgetSelected(SelectionEvent evt) {
	    value = text1.getText();
		exit();
	}
}
