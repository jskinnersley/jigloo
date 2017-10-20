package com.cloudgarden.jigloo.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;

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
public class TabPrefsComposite extends org.eclipse.swt.widgets.Composite {

	private org.eclipse.swt.widgets.TabFolder cTabFolder1;
	private org.eclipse.swt.widgets.TabItem cTabItem1, cTabItem3;
	private org.eclipse.swt.widgets.Composite codeHandlingComp;
	private org.eclipse.swt.widgets.Group group1;
	private org.eclipse.swt.widgets.Group group5;
	private Label label2;
	private Label label1;
	public Group forbiddenClassGroup;
	public Group allowedClassGroup;
	private Composite composite1;
	private TabItem tabItem1;
	private org.eclipse.swt.widgets.Group group4;
	private org.eclipse.swt.widgets.Group group6;
	private org.eclipse.swt.widgets.Group group7;
	private org.eclipse.swt.widgets.Group group3;
	private org.eclipse.swt.widgets.Composite appearanceComp;
	private org.eclipse.swt.custom.CLabel cLabel1;
	private org.eclipse.swt.widgets.Group group2;
	private org.eclipse.swt.widgets.Composite customComp;
	private org.eclipse.swt.widgets.Composite licenseComp;
	private org.eclipse.swt.widgets.TabItem cTabItem4;
	private org.eclipse.swt.widgets.TabItem cTabItem5;
	private org.eclipse.swt.widgets.TabItem cTabItem2;

	/**
	* Auto-generated main method to display this 
	* Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		org.eclipse.swt.widgets.Display display =
			org.eclipse.swt.widgets.Display.getDefault();
		org.eclipse.swt.widgets.Shell shell =
			new org.eclipse.swt.widgets.Shell(display);
		TabPrefsComposite inst =
			new TabPrefsComposite(shell, org.eclipse.swt.SWT.NULL);
		shell.setLayout(new org.eclipse.swt.layout.FillLayout());
		//shell.pack();
		org.eclipse.swt.graphics.Point size = inst.getSize();
		org.eclipse.swt.graphics.Rectangle shellBounds =
			shell.computeTrim(0, 0, size.x, size.y);
		if (shell.getMenuBar() != null)
			shellBounds.height -= 22;
		shell.setSize(shellBounds.width, shellBounds.height);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public TabPrefsComposite(
		org.eclipse.swt.widgets.Composite parent,
		int style) {
		super(parent, style);
		initGUI();
	}

	public void setJiglooVersion(String version) {
	    label2.setText("Jigloo version: "+version);
	}
	
	public int getSelection() {
	    //0 = General
	    //1 = Parsing
	    //2 = Generation
	    //3 = Custom
	    //4 = Creation
	    //5 = License
	    return cTabFolder1.getSelectionIndex();
	}
	
	public void initGUI() {
		try {
			this.setLayout(new org.eclipse.swt.layout.GridLayout());
			{
				cTabFolder1 =
					new org.eclipse.swt.widgets.TabFolder(this, SWT.FLAT);
				GridData cTabFolder1LData = new GridData();
				cTabFolder1LData.verticalAlignment = GridData.FILL;
				cTabFolder1LData.horizontalAlignment = GridData.FILL;
				cTabFolder1LData.grabExcessHorizontalSpace = true;
				cTabFolder1LData.grabExcessVerticalSpace = true;
				cTabFolder1.setLayoutData(cTabFolder1LData);
				{
					cTabItem1 =
						new org.eclipse.swt.widgets.TabItem(
							cTabFolder1,
							SWT.NONE);
					cTabItem1.setText("General");
					{
						appearanceComp =
							new org.eclipse.swt.widgets.Composite(
								cTabFolder1,
								SWT.NONE);
						cTabItem1.setControl(appearanceComp);
						org.eclipse.swt.layout.GridLayout composite2Layout =
							new org.eclipse.swt.layout.GridLayout();
						appearanceComp.setLayout(composite2Layout);
                        {
                            label2 = new Label(appearanceComp, SWT.NONE);
                            label2.setText("Jigloo version: ");
                        }
						{
							group3 =
								new org.eclipse.swt.widgets.Group(
									appearanceComp,
									SWT.NONE);
							org.eclipse.swt.layout.GridLayout group3Layout =
								new org.eclipse.swt.layout.GridLayout();
							group3Layout.makeColumnsEqualWidth = true;
							GridData group3LData = new GridData();
							group3LData.horizontalAlignment = GridData.FILL;
							group3LData.grabExcessHorizontalSpace = true;
							group3.setLayoutData(group3LData);
							group3.setLayout(group3Layout);
							group3.setText("Appearance");
						}
						{
							group4 =
								new org.eclipse.swt.widgets.Group(
									appearanceComp,
									SWT.NONE);
							GridData group4LData = new GridData();
							org.eclipse.swt.layout.GridLayout group4Layout =
								new org.eclipse.swt.layout.GridLayout();
							group4Layout.makeColumnsEqualWidth = true;
							group4.setLayout(group4Layout);
							group4LData.grabExcessHorizontalSpace = true;
							group4LData.horizontalAlignment = GridData.FILL;
							group4.setLayoutData(group4LData);
							group4.setText("Behaviour");
						}
					}
				}
				{
					cTabItem3 =
						new org.eclipse.swt.widgets.TabItem(
							cTabFolder1,
							SWT.NONE);
					cTabItem3.setText("Parsing");
					{
						Composite codeHandlingComp1 =
							new org.eclipse.swt.widgets.Composite(
								cTabFolder1,
								SWT.NONE);
						cTabItem3.setControl(codeHandlingComp1);
						org.eclipse.swt.layout.GridLayout composite4Layout =
							new org.eclipse.swt.layout.GridLayout();
						codeHandlingComp1.setLayout(composite4Layout);
						{
							group6 =
								new org.eclipse.swt.widgets.Group(
									codeHandlingComp1,
									SWT.NONE);
							GridData group6LData = new GridData();
							org.eclipse.swt.layout.GridLayout group6Layout =
								new org.eclipse.swt.layout.GridLayout();
							group6.setLayout(group6Layout);
							group6LData.grabExcessHorizontalSpace = true;
							group6LData.horizontalAlignment = GridData.FILL;
							group6.setLayoutData(group6LData);
							group6.setText("Code Parsing");
						}
					}
				}

				{
					cTabItem2 =
						new org.eclipse.swt.widgets.TabItem(
							cTabFolder1,
							SWT.NONE);
					cTabItem2.setText("Code Generation");
					{
						codeHandlingComp =
							new org.eclipse.swt.widgets.Composite(
								cTabFolder1,
								SWT.NONE);
						cTabItem2.setControl(codeHandlingComp);
						org.eclipse.swt.layout.GridLayout composite4Layout =
							new org.eclipse.swt.layout.GridLayout();
						codeHandlingComp.setLayout(composite4Layout);
						{
							group5 =
								new org.eclipse.swt.widgets.Group(
									codeHandlingComp,
									SWT.NONE);
							org.eclipse.swt.layout.GridLayout group5Layout =
								new org.eclipse.swt.layout.GridLayout();
							GridData group5LData = new GridData();
							group5LData.horizontalAlignment = GridData.FILL;
							group5LData.grabExcessHorizontalSpace = true;
							group5.setLayoutData(group5LData);
							group5.setLayout(group5Layout);
							group5.setText("Code Generation");
						}
					}
				}

				{
					cTabItem4 =
						new org.eclipse.swt.widgets.TabItem(
							cTabFolder1,
							SWT.NONE);
					cTabItem4.setText("Custom Classes");
					{
						customComp =
							new org.eclipse.swt.widgets.Composite(
								cTabFolder1,
								SWT.NONE);
						cTabItem4.setControl(customComp);
						org.eclipse.swt.layout.GridLayout composite1Layout =
							new org.eclipse.swt.layout.GridLayout();
						customComp.setLayout(composite1Layout);
						{
							cLabel1 =
								new org.eclipse.swt.custom.CLabel(
									customComp,
									SWT.NONE);
							cLabel1.setText(
								"Swing Custom classes must have a no-argument constructor.\n" +
								"SWT custom classes must have a (Composite, int) constructor.\n");
						}
						{
							group1 =
								new org.eclipse.swt.widgets.Group(
									customComp,
									SWT.NONE);
							org.eclipse.swt.layout.FillLayout group1Layout =
								new org.eclipse.swt.layout.FillLayout();
							GridData group1LData = new GridData();
							group1LData.grabExcessHorizontalSpace = true;
							group1LData.grabExcessVerticalSpace = true;
							group1LData.verticalAlignment = GridData.FILL;
							group1LData.horizontalAlignment = GridData.FILL;
							group1.setLayoutData(group1LData);
							group1.setLayout(group1Layout);
							group1.setText("Swing custom classes");
						}
						{
							group2 =
								new org.eclipse.swt.widgets.Group(
									customComp,
									SWT.NONE);
							org.eclipse.swt.layout.FillLayout group2Layout =
								new org.eclipse.swt.layout.FillLayout();
							GridData group2LData = new GridData();
							group2LData.grabExcessHorizontalSpace = true;
							group2LData.grabExcessVerticalSpace = true;
							group2LData.verticalAlignment = GridData.FILL;
							group2LData.horizontalAlignment = GridData.FILL;
							group2.setLayoutData(group2LData);
							group2.setLayout(group2Layout);
							group2.setText("SWT custom classes");
						}
					}
				}
				{
					tabItem1 = new TabItem(cTabFolder1, SWT.NONE);
					tabItem1.setText("Class Creation");
					{
						composite1 = new Composite(cTabFolder1, SWT.NONE);
						tabItem1.setControl(composite1);
						GridLayout composite1Layout1 = new GridLayout();
						composite1.setLayout(composite1Layout1);
						{
							label1 = new Label(composite1, SWT.WRAP);
							GridData label1LData = new GridData();
							label1LData.widthHint = 419;
							label1.setText(
								"This page controls which classes will and will not be instantiated by Jigloo as it "
									+ "parses the java code. Jigloo will normally instantiate classes which extend a Swing" +
											" or SWT visual class, or a class which is used as a property for a visual class." +
											" However, if you wish to restrict this even further then you can add classes to the" +
											" \"Forbidden\" list, or replace the \"*\" in the \"Allowed\" list.");
							label1.setLayoutData(label1LData);
						}
						{
							allowedClassGroup = new Group(composite1, SWT.NONE);
							GridLayout group8Layout = new GridLayout();
							GridData group8LData = new GridData();
							group8LData.horizontalAlignment = GridData.FILL;
							group8LData.grabExcessHorizontalSpace = true;
							allowedClassGroup.setLayout(group8Layout);
							allowedClassGroup.setText("Allowed Superclasses");
							allowedClassGroup.setLayoutData(group8LData);
						}
						{
							forbiddenClassGroup = new Group(composite1, SWT.NONE);
							GridLayout group9Layout = new GridLayout();
							GridData group9LData = new GridData();
							group9LData.horizontalAlignment = GridData.FILL;
							group9LData.grabExcessHorizontalSpace = true;
							forbiddenClassGroup.setLayout(group9Layout);
							forbiddenClassGroup.setText("Forbidden Superclasses");
							forbiddenClassGroup.setLayoutData(group9LData);
						}
					}
				}
				{
					cTabItem5 =
						new org.eclipse.swt.widgets.TabItem(
							cTabFolder1,
							SWT.NONE);
					cTabItem5.setText("Licensing");
					{
						licenseComp =
							new org.eclipse.swt.widgets.Composite(
								cTabFolder1,
								SWT.NONE);
						cTabItem5.setControl(licenseComp);
						GridLayout composite5Layout = new GridLayout();
						licenseComp.setLayout(composite5Layout);
						{
							group7 =
								new org.eclipse.swt.widgets.Group(
									licenseComp,
									SWT.NONE);
							org.eclipse.swt.layout.GridLayout group5Layout =
								new org.eclipse.swt.layout.GridLayout();
							group7.setLayoutData(
								new GridData(GridData.FILL_BOTH));
							group7.setLayout(group5Layout);
							group7.setText("Licensing");
						}
					}
				}
			}
			cTabFolder1.setSelection(0);
			cTabFolder1.layout();
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getAppearanceGroup() {
		return group3;
	}

	public Group getBehaviourGroup() {
		return group4;
	}

	public Group getSwingCustomGroup() {
		return group1;
	}

	public Group getSWTCustomGroup() {
		return group2;
	}
	public Group getLicenseGroup() {
		return group7;
	}

	public Group getCodeGenGroup() {
		return group5;
	}

	public Group getCodeParseGroup() {
		return group6;
	}

}
