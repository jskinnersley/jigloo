package com.cloudgarden.jigloo.dialog;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
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
public class ErrorDialog extends org.eclipse.swt.widgets.Dialog {
	private Button button1;
	private Button button3;
	private Button button2;
	private Composite composite1;
	private Text text1;
	private Label cLabel1;
	private Shell dialogShell;
	private String msg, title;
	private Throwable error;
	private Vector errors = new Vector();
	private Label errorCountLabel;

	public ErrorDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void addError(String title, String msg, Throwable t) {
		errors.add(new Object[] {t, msg, title});
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(){
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL
					| SWT.RESIZE | SWT.MAX);
			dialogShell.setText(getText());
			cLabel1 = new Label(dialogShell, SWT.NONE);
            text1 = new Text(dialogShell, SWT.MULTI
                | SWT.WRAP
                | SWT.H_SCROLL
                | SWT.V_SCROLL
                | SWT.BORDER);
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(413,245));
			GridData cLabel1LData = new GridData();
			cLabel1LData.horizontalAlignment = GridData.FILL;
			cLabel1LData.verticalAlignment = GridData.BEGINNING;
			cLabel1.setLayoutData(cLabel1LData);
			FormLayout cLabel1Layout = new FormLayout();
			cLabel1Layout.marginWidth = 0;
			cLabel1Layout.marginHeight = 0;
			GridData text1LData = new GridData();
			text1LData.verticalAlignment = GridData.FILL;
			text1LData.horizontalAlignment = GridData.FILL;
			text1LData.grabExcessHorizontalSpace = true;
			text1LData.grabExcessVerticalSpace = true;
			text1.setLayoutData(text1LData);
			text1.setEditable(false);
			text1.setEnabled(true);
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData button2LData = new GridData();
					button2LData.widthHint = 94;
					button2LData.heightHint = 23;
					button2.setLayoutData(button2LData);
					button2.setText("<< Previous");
					button2.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							previous();
						}
					});
				}
				{
					button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData button3LData = new GridData();
					button3LData.widthHint = 94;
					button3LData.heightHint = 23;
					button3.setLayoutData(button3LData);
					button3.setText("Next >>");
					button3.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							next();
						}
					});
				}
				{
					errorCountLabel = new Label(composite1, SWT.NONE);
					errorCountLabel.setText("Error 1 of 2");
				}
				{
					button1 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.widthHint = 94;
					button1LData.heightHint = 23;
					button1LData.horizontalAlignment = GridData.END;
					button1LData.grabExcessHorizontalSpace = true;
					button1.setLayoutData(button1LData);
					button1.setText("OK");
					button1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							button1WidgetSelected(evt);
						}
					});
				}
			}
			GridLayout dialogShellLayout = new GridLayout(1, true);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.marginWidth = 15;
			dialogShellLayout.marginHeight = 15;
			dialogShellLayout.numColumns = 1;
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShellLayout.horizontalSpacing = 5;
			dialogShellLayout.verticalSpacing = 10;
			dialogShell.pack();
			postInitGUI();
			JiglooUtils.centerShell(dialogShell);
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

	/** Add your post-init code in here 	*/
	public void postInitGUI() {
		next();
		JiglooUtils.centerShell(dialogShell);
		button1.setFocus();
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
			ErrorDialog inst = new ErrorDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Auto-generated event handler method */
	protected void button1WidgetSelected(SelectionEvent evt) {
		dialogShell.dispose();
	}
	
	int currentError = -1;
	
	private void next() {
		if(currentError >= errors.size()-1)
			return;
		currentError ++;
		setError();
	}
	
	private void previous() {
		if(currentError == 0)
			return;
		currentError --;
		setError();
	}
	
	private void setError() {
		Object[] o = (Object[])errors.get(currentError);
		error = (Throwable)o[0];
		msg = (String)o[1];
		msg = JiglooUtils.replace(msg, "\n\n", "\n");
		while(msg.startsWith("\n") || msg.startsWith("\r") || msg.startsWith(" ") || msg.startsWith("\t"))
			msg = msg.substring(1);
		title = (String)o[2];
		if (title != null)
			dialogShell.setText(title);
		if (msg != null) {
		    if(error == null) {
		        cLabel1.setText(title);
		        text1.setText(msg);
		    } else {
		        cLabel1.setText(msg);
		        GridData gd = (GridData)cLabel1.getLayoutData();
		        gd.heightHint = cLabel1.computeSize(cLabel1.getSize().x, -1).y+5;
		        cLabel1.getParent().layout();
		    }
		}
		if (error != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(os);
			error.printStackTrace(pw);
			pw.flush();
			pw.close();
			text1.setText(os.toString());
		}
		if(currentError >= errors.size() -1)
			button3.setEnabled(false);
		else
			button3.setEnabled(true);

		if(currentError > 0)
			button2.setEnabled(true);
		else
			button2.setEnabled(false);

		errorCountLabel.setText("Error "+(currentError+1)+ " of "+errors.size());
		dialogShell.pack();
		int width = dialogShell.getSize().x;
		if(width > 800)
			width = 800;
		dialogShell.setSize(width, 400);
//		dialogShell.pack();
	}
	
	public void clear() {
		currentError = -1;
		errors.clear();
	}

	public boolean isDisposed() {
		return dialogShell != null && dialogShell.isDisposed();
	}

	/**
	 * @return
	 */
	public boolean hasErrors() {
		return errors.size() != 0;
	}
	
}
