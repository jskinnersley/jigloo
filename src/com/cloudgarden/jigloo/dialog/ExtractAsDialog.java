package com.cloudgarden.jigloo.dialog;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.cloudgarden.jigloo.jiglooPlugin;
public class ExtractAsDialog extends org.eclipse.swt.widgets.Dialog {
	private Button cancelButton;
	private Button okButton;
	private Composite composite1;
	private Button addShowGUIButton;
	private Button addMainButton;
	private Text pkgText;
	private Label label2;
	private Button browseButton;
	private Text clsText;
	private Label label1;
	private Shell dialogShell;
	private boolean cancel = true;
	private boolean addMain, addShow;
	private String className, pkgName;
	private IJavaProject proj;
	private IJavaElement pkg;

	public ExtractAsDialog(Shell parent, int style, String pkgName, String className, IJavaProject proj, IJavaElement pkg) {
		super(parent, style);
		this.pkgName = pkgName;
		this.pkg = pkg;
		this.className = className;
		this.proj = proj;
	}
	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open() {
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialogShell.setText("Extract element as new class");
			//dialogShell.setSize(new org.eclipse.swt.graphics.Point(334, 128));
			GridLayout dialogShellLayout = new GridLayout(3, false);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.marginWidth = 10;
			dialogShellLayout.marginHeight = 10;
			dialogShellLayout.numColumns = 3;
			dialogShellLayout.verticalSpacing = 6;
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(342, 179);
			//Rectangle bounds = dialogShell.computeTrim(0, 0, 334, 128);
			//dialogShell.setSize(bounds.width, bounds.height);
			{
				label2 = new Label(dialogShell, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2LData.grabExcessVerticalSpace = true;
				label2.setLayoutData(label2LData);
				label2.setText("&Package Name:");
			}
			{
				pkgText = new Text(dialogShell, SWT.SINGLE | SWT.BORDER);
				pkgText.setText(pkgName);
				GridData fileTextLData = new GridData();
				fileTextLData.horizontalAlignment = GridData.FILL;
				fileTextLData.grabExcessVerticalSpace = true;
				fileTextLData.grabExcessHorizontalSpace = true;
				pkgText.setLayoutData(fileTextLData);
			}
			{
				browseButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				browseButton.setText("Browse...");
				GridData browseButtonLData = new GridData();
				browseButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						SelectionDialog dialog = null;
						try {
							dialog = JavaUI.createPackageDialog(dialogShell, proj, SWT.NONE);
							dialog.setInitialSelections(new Object[] {pkg});
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
						if (dialog.open() == SelectionDialog.OK) {
							Object[] result = dialog.getResult();
							if (result.length == 1) {
								IJavaElement selEl = (IJavaElement) result[0];
								pkgText.setText(selEl.getElementName());
							}
						}
					}
				});
				browseButtonLData.horizontalAlignment = GridData.END;
				browseButton.setLayoutData(browseButtonLData);
			}
			{
				label1 = new Label(dialogShell, SWT.NONE);
				label1.setText("&Class Name:");
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessVerticalSpace = true;
				label1LData.heightHint = 13;
				label1.setLayoutData(label1LData);
			}
			{
				clsText = new Text(dialogShell, SWT.SINGLE | SWT.BORDER);
				clsText.setText(className);
				
				GridData text1LData = new GridData();
				clsText.addSelectionListener(new SelectionAdapter() {
						public void widgetDefaultSelected(SelectionEvent evt) {
							okButtonWidgetSelected(null);
						}
				});
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.grabExcessVerticalSpace = true;
				text1LData.horizontalSpan = 2;
				text1LData.grabExcessHorizontalSpace = true;
				clsText.setLayoutData(text1LData);
			}
			{
				addMainButton = new Button(dialogShell, SWT.CHECK | SWT.LEFT);
				GridData addMainButtonLData = new GridData();
				addMainButtonLData.horizontalAlignment = GridData.FILL;
				addMainButtonLData.horizontalSpan = 3;
				addMainButtonLData.grabExcessVerticalSpace = true;
				addMainButton.setLayoutData(addMainButtonLData);
				addMainButton.setText("Add main method");
			}
			{
				addShowGUIButton = new Button(dialogShell, SWT.CHECK | SWT.LEFT);
				GridData addShowGUIButtonLData = new GridData();
				addShowGUIButtonLData.horizontalAlignment = GridData.FILL;
				addShowGUIButtonLData.horizontalSpan = 3;
				addShowGUIButtonLData.heightHint = 16;
				addShowGUIButtonLData.grabExcessVerticalSpace = true;
				addShowGUIButton.setLayoutData(addShowGUIButtonLData);
				addShowGUIButton.setText("Add showGUI method");
				addShowGUIButton.setSize(new org.eclipse.swt.graphics.Point(324, 16));
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout(2, true);
				composite1Layout.marginWidth = 50;
				composite1Layout.marginHeight = 0;
				composite1Layout.numColumns = 2;
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.horizontalSpacing = 50;
				composite1Layout.verticalSpacing = 0;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.heightHint = 23;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData okButtonLData = new GridData();
					okButtonLData.horizontalAlignment = GridData.FILL;
					okButtonLData.grabExcessHorizontalSpace = true;
					okButtonLData.grabExcessVerticalSpace = true;
					okButton.setLayoutData(okButtonLData);
					okButton.setText("OK");
					okButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							okButtonWidgetSelected(evt);
						}
					});
				}
				{
					cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData cancelButtonLData = new GridData();
					cancelButtonLData.horizontalAlignment = GridData.FILL;
					cancelButtonLData.grabExcessHorizontalSpace = true;
					cancelButtonLData.grabExcessVerticalSpace = true;
					cancelButton.setLayoutData(cancelButtonLData);
					cancelButton.setText("Cancel");
					cancelButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							cancelButtonWidgetSelected(evt);
						}
					});
				}
				composite1.layout();
			}
			postInitGUI();
			okButton.setFocus();
			clsText.setFocus();
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
	public void preInitGUI() {}

	public void postInitGUI() {
		addMainButton.setSelection(jiglooPlugin.getDefault().getCreateMain());
		addShowGUIButton.setSelection(jiglooPlugin.getDefault().getCreateShowGUI());
		Point dss = dialogShell.getSize();
		Rectangle sb = dialogShell.getParent().getShell().getBounds();
		//Rectangle sb = Display.getDefault().getBounds();
		dialogShell.setLocation(sb.x + (sb.width - dss.x) / 2, sb.y + (sb.height - dss.y) / 2);
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
			ExtractAsDialog inst = new ExtractAsDialog(shell, SWT.NULL, "pkg", "cls", null, null);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void quit() {
		if (dialogShell.isDisposed())
			return;
		if (cancel) {
			className = null;
			pkgName = null;
		}
		className = clsText.getText();
		pkgName = pkgText.getText();
		addMain = addMainButton.getSelection();
		addShow = addShowGUIButton.getSelection();
		dialogShell.dispose();
	}

	public Object[] getResults() {
		if (cancel)
			return null;
		return new Object[] { pkgName, className, new Boolean(addMain), new Boolean(addShow)};
	}

	protected void okButtonWidgetSelected(SelectionEvent evt) {
		cancel = false;
		quit();
	}

	protected void cancelButtonWidgetSelected(SelectionEvent evt) {
		quit();
	}
}
