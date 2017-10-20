package com.cloudgarden.jigloo.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
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
public class NewFormWizardComposite extends org.eclipse.swt.widgets.Composite {

	Button extraButton2;
	Label label3;
	Button extraButton1;
	Text fileText;
	Label label2;
	private CLabel msgLabel;
	Button browseButton;
	Text containerText;
	private Button superclassBrowseButton;
	private Button button1;
	private Label label7;
	public Text superclassText;
	Label label1;
	
	public NewFormWizardComposite(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI() {
		try {
			preInitGUI();
			{
				{
					label1 = new Label(this, SWT.NONE);
					GridData label1LData = new GridData();
					label1LData.horizontalAlignment = GridData.FILL;
					label1.setLayoutData(label1LData);
					label1.setText("&Package:");
				}
				{
					containerText = new Text(this, SWT.BORDER);
					GridData containerTextLData = new GridData();
					containerTextLData.horizontalAlignment = GridData.FILL;
					containerTextLData.grabExcessHorizontalSpace = true;
					containerText.setLayoutData(containerTextLData);
					//containerText.setEditable(false);
					containerText.setEnabled(true);
				}
				{
					browseButton = new Button(this, SWT.PUSH | SWT.CENTER);
					browseButton.setText("Browse...");
				}
				label2 = new Label(this, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2.setLayoutData(label2LData);
				label2.setText("&Class Name:");
			}
			{
				fileText = new Text(this, SWT.BORDER);
				GridData fileTextLData = new GridData();
				fileTextLData.horizontalAlignment = GridData.FILL;
				fileTextLData.widthHint = 200;
				fileTextLData.grabExcessHorizontalSpace = true;
				fileText.setLayoutData(fileTextLData);
			}
			{
				label3 = new Label(this, SWT.NONE);
				label3.setText("    ");
			}
			{
				label7 = new Label(this, SWT.NONE);
				label7.setText("Superclass:");
				GridData label7LData = new GridData();
				label7LData.horizontalAlignment = GridData.FILL;
				label7.setLayoutData(label7LData);
			}
			{
				superclassText = new Text(this, SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.grabExcessHorizontalSpace = true;
				superclassText.setLayoutData(text1LData);
			}
			{
				superclassBrowseButton = new Button(this, SWT.PUSH | SWT.CENTER);
				superclassBrowseButton.setText("Browse...");
			}
            {
                msgLabel = new CLabel(this, SWT.NONE);
                GridData msgLabelLData = new GridData();
                msgLabelLData.horizontalAlignment = GridData.FILL;
                msgLabelLData.horizontalSpan = 3;
                msgLabel.setLayoutData(msgLabelLData);
            }
            {
            	button1 = new Button(this, SWT.CHECK | SWT.LEFT);
            	GridData button1LData = new GridData();
            	button1LData.horizontalAlignment = GridData.FILL;
            	button1LData.grabExcessHorizontalSpace = true;
            	button1LData.horizontalSpan = 3;
            	button1.setLayoutData(button1LData);
            	button1.setText("add SWT libraries to project (if needed)");
            	button1.setSelection(jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_ADD_SWT_LIBS));
            }
			{
				extraButton1 = new Button(this, SWT.CHECK | SWT.LEFT);
				GridData addMainButtonLData = new GridData();
//				extraButton1.setVisible(false);
				addMainButtonLData.horizontalAlignment = GridData.FILL;
				addMainButtonLData.horizontalSpan = 3;
				extraButton1.setLayoutData(addMainButtonLData);
				extraButton1.setText("Add main method");
			}
			{
				extraButton2 = new Button(this, SWT.CHECK | SWT.LEFT);
				GridData addShowGUIButtonLData = new GridData();
//				extraButton2.setVisible(false);
				addShowGUIButtonLData.horizontalAlignment = GridData.FILL;
				addShowGUIButtonLData.horizontalSpan = 3;
				extraButton2.setLayoutData(addShowGUIButtonLData);
				extraButton2.setText("Add showGUI method");
			}
//			this.setSize(421, 249);
			GridLayout thisLayout = new GridLayout(3, true);
			this.setLayout(thisLayout);
			thisLayout.marginWidth = 10;
			thisLayout.marginHeight = 5;
			thisLayout.numColumns = 3;
			thisLayout.makeColumnsEqualWidth = false;
			thisLayout.horizontalSpacing = 5;
			thisLayout.verticalSpacing = 9;
			layout();
			pack();
			postInitGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* Add your pre-init code in here
	*/
	public void preInitGUI() {}
	/**
	* Add your post-init code in here
	*/
	public void postInitGUI() {}

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
			NewFormWizardComposite inst = new NewFormWizardComposite(shell, SWT.NULL);
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			Rectangle shellBounds = shell.computeTrim(0, 0, 418, 223);
			shell.setSize(shellBounds.width, shellBounds.height);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getExtraButton1() {
		return extraButton1;
	}

	public Button getExtraButton2() {
		return extraButton2;
	}

	public Button getBrowseButton() {
		return browseButton;
	}

	public Text getContainerText() {
		return containerText;
	}

	public Text getFileText() {
		return fileText;
	}
	
	public void showSuperclass(boolean show) {
		if(!show) {
			label7.setVisible(false);
			superclassBrowseButton.setVisible(false);
			superclassText.setVisible(false);
			((GridData)label7.getLayoutData()).heightHint = 0;
			((GridData)superclassBrowseButton.getLayoutData()).heightHint = 0;
			((GridData)superclassText.getLayoutData()).heightHint = 0;
		}
	}
	public Button getSWTLibButton() {
		return button1;
	}
	public Button getSuperclassBrowseButton() {
		return superclassBrowseButton;
	}
	public CLabel getMsgLabel() {
		return msgLabel;
	}

}
