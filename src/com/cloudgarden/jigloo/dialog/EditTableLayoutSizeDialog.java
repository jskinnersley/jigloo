package com.cloudgarden.jigloo.dialog;

import info.clearthought.layout.TableLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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

import com.cloudgarden.jigloo.util.JiglooUtils;

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
public class EditTableLayoutSizeDialog extends org.eclipse.swt.widgets.Dialog {
	private Shell dialogShell;
	Button minButton;
	private Button cancelButton;
	private Button okButton;
	private Composite composite2;
	private Composite composite1;
	private Text sizeText;
	private Button fillButton;
	private Button prefButton;
	private Button sizeButton;

	String value = "", title = "";
	private Label label3;
	private Label label1;
	private Label label2;

	public EditTableLayoutSizeDialog(Shell parent, int style) {
		super(parent, style);
	}

	private String initValue;
	
	public void initText(String value, String title) {
		this.value = value;
		this.initValue = value;
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
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | 
					SWT.APPLICATION_MODAL);
			dialogShell.setText(getText());
			//Creating composite2
			composite2 = new Composite(dialogShell,SWT.NULL);
			GridLayout composite2Layout = new GridLayout();
			composite2.setLayout(composite2Layout);
			//Creating sizeButton
			//Creating sizeText
			{
				prefButton = new Button(composite2, SWT.RADIO);
				GridData button1LData = new GridData();
				button1LData.grabExcessHorizontalSpace = true;
				button1LData.horizontalAlignment = GridData.FILL;
				prefButton.setLayoutData(button1LData);
				prefButton.setText("PREFERRED");
			}
			{
				label1 = new Label(composite2, SWT.NONE);
				label1.setText(" ");
			}
			{
				fillButton = new Button(composite2, SWT.RADIO);
				GridData button2LData = new GridData();
				button2LData.grabExcessHorizontalSpace = true;
				button2LData.horizontalAlignment = GridData.FILL;
				fillButton.setLayoutData(button2LData);
				fillButton.setText("FILL");
			}
			{
				label3 = new Label(composite2, SWT.NONE);
				GridData label3LData = new GridData();
				label3LData.widthHint = 5;
				label3LData.heightHint = 13;
				label3.setLayoutData(label3LData);
				label3.setText(" ");
			}
			{
				minButton = new Button(composite2, SWT.RADIO);
				GridData button3LData = new GridData();
				button3LData.grabExcessHorizontalSpace = true;
				button3LData.horizontalAlignment = GridData.FILL;
				minButton.setLayoutData(button3LData);
				minButton.setText("MINIMUM");
			}
			{
				label2 = new Label(composite2, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.widthHint = 5;
				label2LData.heightHint = 13;
				label2.setLayoutData(label2LData);
				label2.setText(" ");
			}
			{
				sizeButton = new Button(composite2, SWT.RADIO);
				GridData cLabel1LData = new GridData();
				cLabel1LData.grabExcessHorizontalSpace = true;
				cLabel1LData.horizontalAlignment = GridData.FILL;
				sizeButton.setLayoutData(cLabel1LData);
				sizeButton.setText("size (pixels or fraction)");
			}
			{
				sizeText = new Text(composite2, SWT.BORDER);
				GridData nameTextLData = new GridData();
				nameTextLData.horizontalAlignment = GridData.FILL;
				nameTextLData.grabExcessHorizontalSpace = true;
				sizeText.setLayoutData(nameTextLData);
				sizeText.addTraverseListener(new TraverseListener() {
					public void keyTraversed(TraverseEvent evt) {
						nameTextKeyTraversed(evt);
					}
				});
			}
			//Creating cLabel2
			//Creating textText
			//Creating composite1
			composite1 = new Composite(composite2,SWT.NULL);
			GridLayout composite1Layout = new GridLayout();
			composite1.setLayout(composite1Layout);
			//Creating okButton
			okButton = new Button(composite1,SWT.PUSH| SWT.CENTER);
			//Creating cancelButton
			cancelButton = new Button(composite1,SWT.PUSH| SWT.CENTER);
	
			//Setting properties for TextComponentDialog
			dialogShell.setText("Enter name and text for new component");
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(402,129));

			//Setting layout constraints for sizeText
			//Setting properties for sizeText

			//Setting layout constraints for cLabel2
			//Setting properties for cLabel2
			//Setting Absolute layout for cLabel2

			//Setting layout constraints for textText
			//Setting properties for textText

			//Setting layout constraints for composite1
			GridData composite1LData = new GridData();
			composite1LData.horizontalAlignment = GridData.END;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1LData.widthHint = 147;
			composite1LData.verticalAlignment = GridData.END;
			composite1LData.grabExcessVerticalSpace = true;
			composite1LData.horizontalSpan = 2;
			composite1.setLayoutData(composite1LData);
			//Setting properties for composite1

			//Setting layout constraints for okButton
			GridData okButtonLData = new GridData();
			okButtonLData.verticalAlignment = GridData.CENTER;
			okButtonLData.horizontalAlignment = GridData.FILL;
			okButtonLData.widthHint = -1;
			okButtonLData.heightHint = 22;
			okButtonLData.horizontalIndent = 0;
			okButtonLData.horizontalSpan = 1;
			okButtonLData.verticalSpan = 1;
			okButtonLData.grabExcessHorizontalSpace = false;
			okButtonLData.grabExcessVerticalSpace = false;
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
			cancelButtonLData.verticalAlignment = GridData.CENTER;
			cancelButtonLData.horizontalAlignment = GridData.FILL;
			cancelButtonLData.widthHint = -1;
			cancelButtonLData.heightHint = 22;
			cancelButtonLData.horizontalIndent = 0;
			cancelButtonLData.horizontalSpan = 1;
			cancelButtonLData.verticalSpan = 1;
			cancelButtonLData.grabExcessHorizontalSpace = false;
			cancelButtonLData.grabExcessVerticalSpace = false;
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
			composite1Layout.horizontalSpacing = 15;
			composite1.layout();
			//Setting Grid layout for composite2
			composite2Layout.marginWidth = 10;
			composite2Layout.marginHeight = 10;
			composite2Layout.numColumns = 2;
			composite2.layout();
			//Setting Fill layout for dialogShell
			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.type = SWT.HORIZONTAL;
			dialogShell.layout();
			Rectangle bounds = dialogShell.computeTrim(0, 0, 402,129);
			dialogShell.setSize(272, 174);
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
	
	public String getValue() {
		return value;
	}
	
	public void preInitGUI() {}

	public void postInitGUI() {
		dialogShell.setText(title);
		if((""+TableLayout.PREFERRED).equals(value))
			prefButton.setSelection(true);
		else if((""+TableLayout.FILL).equals(value))
			fillButton.setSelection(true);
		else if((""+TableLayout.MINIMUM).equals(value))
			minButton.setSelection(true);
		else {
			sizeButton.setSelection(true);
			sizeText.setText(value);
		}
		sizeText.setSelection(0, value.length());
		sizeText.setFocus();
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
			EditTableLayoutSizeDialog inst = new EditTableLayoutSizeDialog(shell, SWT.NULL);
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
			if(prefButton.getSelection())
				value = ""+TableLayout.PREFERRED;
			else if(minButton.getSelection())
				value = ""+TableLayout.MINIMUM;
			else if(fillButton.getSelection())
				value = ""+ TableLayout.FILL;
			else
				value = sizeText.getText();
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
