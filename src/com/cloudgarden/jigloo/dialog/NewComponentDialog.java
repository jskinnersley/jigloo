package com.cloudgarden.jigloo.dialog;

import java.lang.reflect.Constructor;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.ConstructorManager;
import com.cloudgarden.jigloo.properties.ImageFileDialogCellEditor;
import com.cloudgarden.jigloo.properties.LayoutPropertySheetPage;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;

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
public class NewComponentDialog extends org.eclipse.swt.widgets.Dialog {
	Group formLayoutConstraintGrp;
	Button grabExVerSpcRadioButton;
	CLabel cLabel5;
	Button grabExHorSpcRadioButton;
	CLabel cLabel4;
	CLabel cLabel3;
	Shell dialogShell;
	private Constructor defaultCon;
	
	String name;
	String text;
	IWorkbenchPartSite site;
	LayoutPropertySheetPage lps;
	private Button button1;
	private Text text1;
	private Composite composite7;
	private Button cancelButton;
	private Button okButton;
	private Composite composite6;
	private Composite composite1;
	private Button defaultLayoutButton;
	private Button defaultButton;
	private Composite composite4;
	private Composite composite3;
	private Group group1;
	private Button addGetterButton;
	private Composite composite5;
	private Button imgBrowseButton;
	private Text imgText;
	private CLabel cLabel2;
	private Composite imgComp;
	private Text textTextField;
	private CLabel cLabel6;
	private Composite textComp;
	private Text nameTextField;
	private CLabel cLabel1;
	private Composite composite2;
	private Composite mainComposite;
	boolean showText = true;
	boolean showImg = true;
	boolean showConstraints = true;
	boolean cancel = true;
	String parLT = null;
	FormComponent comp;
	FormEditor editor;
	ImageWrapper imgWrapper;

	public NewComponentDialog(Shell parent, int style, IWorkbenchPartSite site, String parentLayoutType) {
		this(parent, style);
		this.site = site;
		parLT = parentLayoutType;
	}

	public NewComponentDialog(Shell parent, int style) {
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
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | 
					SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setText(getText());
			dialogShell.setText("Enter/edit basic properties for new component");
//			dialogShell.setSize(new org.eclipse.swt.graphics.Point(450, 376));
//			mainComposite.setSize(new org.eclipse.swt.graphics.Point(437, 375));
//			composite2.setSize(new org.eclipse.swt.graphics.Point(416, 23));
//			cLabel1.setSize(new org.eclipse.swt.graphics.Point(132, 19));
//			nameTextField.setSize(new org.eclipse.swt.graphics.Point(221, 13));
//			textComp.setSize(new org.eclipse.swt.graphics.Point(416, 23));
			FillLayout dialogShellLayout = new FillLayout(256);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.type = SWT.HORIZONTAL;
			Rectangle bounds = dialogShell.computeTrim(0, 0, 450, 376);
			dialogShell.setSize(bounds.width, bounds.height);
			{
				mainComposite = new Composite(dialogShell, SWT.NULL);
				GridLayout mainCompositeLayout = new GridLayout();
				mainCompositeLayout.numColumns = 2;
				mainCompositeLayout.makeColumnsEqualWidth = true;
				mainComposite.setLayout(mainCompositeLayout);
				{
					composite7 = new Composite(mainComposite, SWT.NONE);
					GridLayout composite7Layout = new GridLayout();
					composite7Layout.numColumns = 3;
					composite7Layout.marginHeight = 0;
					composite7Layout.verticalSpacing = 0;
					composite7.setLayout(composite7Layout);
					GridData composite7LData = new GridData();
					composite7LData.horizontalAlignment = GridData.FILL;
					composite7LData.grabExcessHorizontalSpace = true;
					composite7LData.horizontalSpan = 2;
					composite7.setLayoutData(composite7LData);
					composite7.setVisible(true);
					{
						cLabel3 = new CLabel(composite7, SWT.NONE);
						cLabel3.setText("Constructor:");
						GridData cLabel3LData = new GridData();
						cLabel3LData.verticalAlignment = GridData.FILL;
						cLabel3LData.widthHint = 132;
						cLabel3.setLayoutData(cLabel3LData);
						cLabel3.setVisible(true);
						cLabel3.setLayout(null);
					}
					{
						text1 = new Text(composite7, SWT.BORDER);
						text1.setEditable(false);
						GridData text1LData = new GridData();
						text1LData.horizontalAlignment = GridData.FILL;
						text1LData.grabExcessHorizontalSpace = true;
						text1.setLayoutData(text1LData);
						text1.setVisible(true);
					}
					{
						button1 = new Button(composite7, SWT.PUSH | SWT.CENTER);
						button1.setText("Change...");
						GridData button1LData = new GridData();
						button1LData.widthHint = -1;
						button1LData.verticalAlignment = GridData.FILL;
						button1LData.grabExcessVerticalSpace = true;
						button1.setLayoutData(button1LData);
						button1.setVisible(true);
						button1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								openConstructorDialog(evt);
							}
						});
					}
				}
				{
					GridData composite2LData = new GridData();
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.horizontalSpan = 2;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2 = new Composite(mainComposite, SWT.NULL);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.numColumns = 2;
					composite2Layout.marginHeight = 0;
					composite2Layout.verticalSpacing = 0;
					composite2.setLayout(composite2Layout);
					composite2.setLayoutData(composite2LData);
					composite2.setVisible(true);
					{
						cLabel1 = new CLabel(composite2, SWT.NULL);
						GridData cLabel1LData = new GridData();
						cLabel1LData.verticalAlignment = GridData.FILL;
						cLabel1LData.widthHint = 132;
						cLabel1.setLayoutData(cLabel1LData);
						cLabel1.setText("Component Name:");
						cLabel1.setVisible(true);
						cLabel1.setLayout(null);
					}
					{
						nameTextField = new Text(composite2, SWT.BORDER);
						GridData nameTextFieldLData = new GridData();
						nameTextFieldLData.horizontalAlignment = GridData.FILL;
						nameTextFieldLData.grabExcessHorizontalSpace = true;
						nameTextField.setLayoutData(nameTextFieldLData);
						nameTextField.setVisible(true);
						nameTextField
							.addTraverseListener(new TraverseListener() {
							public void keyTraversed(TraverseEvent evt) {
								nameTextFieldKeyTraversed(evt);
							}
							});
						nameTextField.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent evt) {
								nameTextFieldModifyText(evt);
							}
						});
					}
				}
				{
					GridData textCompLData = new GridData();
					textCompLData.verticalAlignment = GridData.FILL;
					textCompLData.horizontalAlignment = GridData.FILL;
					textCompLData.horizontalSpan = 2;
					textCompLData.grabExcessHorizontalSpace = true;
					textComp = new Composite(mainComposite, SWT.NULL);
					GridLayout textCompLayout = new GridLayout();
					textCompLayout.numColumns = 2;
					textCompLayout.marginHeight = 0;
					textCompLayout.verticalSpacing = 0;
					textComp.setLayout(textCompLayout);
					textComp.setLayoutData(textCompLData);
					textComp.setVisible(true);
					{
						cLabel6 = new CLabel(textComp, SWT.NULL);
						cLabel6.setText("Text:");
						GridData cLabel6LData = new GridData();
						cLabel6LData.verticalAlignment = GridData.FILL;
						cLabel6LData.widthHint = 132;
						cLabel6.setLayoutData(cLabel6LData);
						cLabel6.setVisible(true);
						cLabel6.setLayout(null);
					}
					{
						textTextField = new Text(textComp, SWT.BORDER);
						GridData textTextFieldLData = new GridData();
						textTextFieldLData.horizontalAlignment = GridData.FILL;
						textTextFieldLData.grabExcessHorizontalSpace = true;
						textTextField.setLayoutData(textTextFieldLData);
						textTextField.setVisible(true);
						textTextField
							.addTraverseListener(new TraverseListener() {
							public void keyTraversed(TraverseEvent evt) {
								textTextFieldKeyTraversed(evt);
							}
							});
						textTextField.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent evt) {
								textTextFieldFocusLost(evt);
							}
						});
						textTextField.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent evt) {
								textTextFieldModifyText(evt);
							}
						});
					}
				}
				{
					imgComp = new Composite(mainComposite, SWT.NULL);
					GridLayout imgCompLayout = new GridLayout();
					imgCompLayout.numColumns = 3;
					imgCompLayout.marginHeight = 0;
					imgCompLayout.verticalSpacing = 0;
					imgComp.setLayout(imgCompLayout);
					GridData imgCompLData = new GridData();
					imgCompLData.verticalAlignment = GridData.FILL;
					imgCompLData.horizontalAlignment = GridData.FILL;
					imgCompLData.horizontalSpan = 2;
					imgCompLData.grabExcessHorizontalSpace = true;
					imgComp.setLayoutData(imgCompLData);
					imgComp.setVisible(true);
					{
						cLabel2 = new CLabel(imgComp, SWT.NULL);
						cLabel2.setText("Image:");
						GridData cLabel2LData = new GridData();
						cLabel2LData.verticalAlignment = GridData.FILL;
						cLabel2LData.widthHint = 132;
						cLabel2.setLayoutData(cLabel2LData);
						cLabel2.setVisible(true);
						cLabel2.setLayout(null);
					}
					{
						imgText = new Text(imgComp, SWT.BORDER);
						imgText.setEditable(false);
						GridData imgTextLData = new GridData();
						imgTextLData.horizontalAlignment = GridData.FILL;
						imgTextLData.grabExcessHorizontalSpace = true;
						imgText.setLayoutData(imgTextLData);
						imgText.setEnabled(false);
						imgText.setVisible(true);
					}
					{
						imgBrowseButton = new Button(imgComp, SWT.PUSH
							| SWT.CENTER);
						imgBrowseButton.setText("Browse...");
						GridData imgBrowseButtonLData = new GridData();
						imgBrowseButtonLData.verticalAlignment = GridData.FILL;
						imgBrowseButtonLData.widthHint = -1;
						imgBrowseButtonLData.grabExcessVerticalSpace = true;
						imgBrowseButton.setLayoutData(imgBrowseButtonLData);
						imgBrowseButton.setVisible(true);
						imgBrowseButton
							.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								imgBrowseButtonWidgetSelected(evt);
							}
							});
					}
				}
				{
					composite5 = new Composite(mainComposite, SWT.NULL);
					GridLayout composite5Layout = new GridLayout();
					composite5Layout.numColumns = 2;
					composite5Layout.marginHeight = 0;
					composite5Layout.verticalSpacing = 0;
					composite5.setLayout(composite5Layout);
					GridData composite5LData = new GridData();
					composite5LData.verticalAlignment = GridData.FILL;
					composite5LData.horizontalAlignment = GridData.FILL;
					composite5LData.horizontalSpan = 2;
					composite5LData.grabExcessHorizontalSpace = true;
					composite5.setLayoutData(composite5LData);
					composite5.setVisible(true);
					{
						addGetterButton = new Button(composite5, SWT.CHECK
							| SWT.LEFT);
						GridData addGetterButtonLData = new GridData();
						addGetterButton.setLayoutData(addGetterButtonLData);
						addGetterButton
							.setText("Add public getter method for component");
					}
				}
				{
					group1 = new Group(mainComposite, SWT.H_SCROLL
						| SWT.V_SCROLL);
					GridLayout group1Layout = new GridLayout();
					group1Layout.makeColumnsEqualWidth = true;
					group1Layout.marginHeight = 2;
					group1.setLayout(group1Layout);
					group1.setText("Layout Parameters");
					GridData group1LData = new GridData();
					group1LData.verticalAlignment = GridData.FILL;
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.horizontalSpan = 2;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.grabExcessVerticalSpace = true;
					group1.setLayoutData(group1LData);
					group1.setVisible(true);
					{
						composite3 = new Composite(group1, SWT.NULL);
						FillLayout composite3Layout = new FillLayout(
							org.eclipse.swt.SWT.HORIZONTAL);
						composite3.setLayout(composite3Layout);
						GridData composite3LData = new GridData();
						composite3LData.horizontalAlignment = GridData.FILL;
						composite3LData.grabExcessHorizontalSpace = true;
						composite3LData.grabExcessVerticalSpace = true;
						composite3LData.heightHint = 150;
						composite3.setLayoutData(composite3LData);
						composite3.setVisible(true);
					}
					{
						composite4 = new Composite(group1, SWT.NULL);
						GridLayout composite4Layout = new GridLayout();
						composite4Layout.makeColumnsEqualWidth = true;
						composite4Layout.marginWidth = 0;
						composite4Layout.marginHeight = 0;
						composite4Layout.horizontalSpacing = 0;
						composite4Layout.verticalSpacing = 4;
						composite4.setLayout(composite4Layout);
						GridData composite4LData = new GridData();
						composite4LData.horizontalAlignment = GridData.FILL;
						composite4LData.grabExcessHorizontalSpace = true;
						composite4.setLayoutData(composite4LData);
						composite4.setVisible(true);
						{
							defaultButton = new Button(composite4, SWT.CHECK
								| SWT.LEFT);
							defaultButton
								.setText("Set as default for Grid Constraints");
							GridData defaultButtonLData = new GridData();
							defaultButtonLData.grabExcessVerticalSpace = true;
							defaultButtonLData.grabExcessHorizontalSpace = true;
							defaultButtonLData.horizontalAlignment = GridData.FILL;
							defaultButton.setLayoutData(defaultButtonLData);
							defaultButton.setVisible(false);
						}
						{
							defaultLayoutButton = new Button(
								composite4,
								SWT.CHECK | SWT.LEFT);
							defaultLayoutButton
								.setText("Set as default Layout");
							GridData defaultLayoutButtonLData = new GridData();
							defaultLayoutButtonLData.grabExcessVerticalSpace = true;
							defaultLayoutButtonLData.horizontalAlignment = GridData.FILL;
							defaultLayoutButtonLData.grabExcessHorizontalSpace = true;
							defaultLayoutButton.setLayoutData(defaultLayoutButtonLData);
							defaultLayoutButton.setVisible(true);
						}
					}
				}
				{
					composite1 = new Composite(mainComposite, SWT.NULL);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.numColumns = 2;
					composite1Layout.marginWidth = 0;
					composite1Layout.horizontalSpacing = 0;
					composite1.setLayout(composite1Layout);
					GridData composite1LData = new GridData();
					composite1LData.verticalAlignment = GridData.FILL;
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.horizontalSpan = 2;
					composite1LData.grabExcessHorizontalSpace = true;
					composite1.setLayoutData(composite1LData);
					composite1.setVisible(true);
					{
						composite6 = new Composite(composite1, SWT.NONE);
						GridLayout composite6Layout = new GridLayout();
						composite6Layout.makeColumnsEqualWidth = true;
						composite6Layout.numColumns = 2;
						GridData composite6LData = new GridData();
						composite6LData.horizontalAlignment = GridData.END;
						composite6LData.horizontalSpan = 2;
						composite6LData.grabExcessHorizontalSpace = true;
						composite6LData.verticalAlignment = GridData.FILL;
						composite6.setLayoutData(composite6LData);
						composite6.setLayout(composite6Layout);
						{
							okButton = new Button(composite6, SWT.PUSH
								| SWT.CENTER);
							okButton.setText("OK");
							GridData okButtonLData = new GridData();
							okButtonLData.horizontalAlignment = GridData.FILL;
							okButtonLData.grabExcessHorizontalSpace = true;
							okButton.setLayoutData(okButtonLData);
							okButton.setVisible(true);
							okButton
								.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									okButtonWidgetSelected(evt);
								}
								});
						}
						{
							cancelButton = new Button(composite6, SWT.PUSH
								| SWT.CENTER);
							cancelButton.setText("Cancel");
							GridData cancelButtonLData = new GridData();
							cancelButtonLData.horizontalAlignment = GridData.FILL;
							cancelButtonLData.grabExcessHorizontalSpace = true;
							cancelButton.setLayoutData(cancelButtonLData);
							cancelButton.setVisible(true);
							cancelButton
								.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									cancelButtonWidgetSelected(evt);
								}
								});
						}
					}
				}
			}
			dialogShell.layout();
			postInitGUI();
			JiglooUtils.centerShell(dialogShell);
			dialogShell.open();
			dialogShell.setDefaultButton(okButton);
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

	public void showTextField(boolean showText) {
		this.showText = showText;
	}

	public void showImgField(boolean showImg) {
		this.showImg = showImg;
	}

	public void showConstraints(boolean showConstraints) {
		this.showConstraints = showConstraints;
	}

	public void postInitGUI() {
		if (site != null) {
			lps = new LayoutPropertySheetPage(composite3, "Layout", site);
			//site.getPage().addSelectionListener(lps);
			lps.selectionChanged(site.getPage().getActivePart(), new StructuredSelection(comp));
			comp.setSelectionListener(lps);
			
			composite3.layout();

			name = comp.getName();
			nameTextField.setText(name);
			nameTextField.setSelection(0, name.length());
			nameTextField.setFocus();
			if (parLT != null)
				defaultButton.setText("Set as default for " + parLT + " constraint");
			else
				defaultButton.setVisible(false);

			if (showText) {
				text = comp.getName();
				textTextField.setText(text);
			} else {
				textComp.setVisible(false);
				GridData gd = new GridData();
				gd.heightHint = 0;
				gd.widthHint = 10;
				textComp.setLayoutData(gd);
			}

			if (!showImg) {
				imgComp.setVisible(false);
				GridData gd = new GridData();
				gd.heightHint = 0;
				gd.widthHint = 10;
				imgComp.setLayoutData(gd);
			}

			if (!showConstraints) {
				group1.setVisible(false);
				GridData gd = new GridData();
				gd.heightHint = 0;
				gd.widthHint = 10;
				group1.setLayoutData(gd);
				dialogShell.setSize(dialogShell.computeSize(450, -1));
			}

			dialogShell.layout(true);
			dialogShell.setSize(dialogShell.computeSize(450, -1));
			
			setConstructor(defaultCon, null, null);
			
			//			TableTree tt = ((TableTree) lps.getControl());
			//			TableTreeItem[] its = tt.getItems();
			//			for (int i = 0; i < its.length; i++) {
			//				TableTreeItem item = its[i];
			//				item.setExpanded(true);
			//			}
			//			lps.refresh();
		}
	}

	public void setEditor(FormEditor editor) {
		this.editor = editor;
	}

	public void setComponent(FormComponent comp) {
		this.comp = comp;
	}

	public void okButtonWidgetSelected(SelectionEvent evt) {
		cancel = false;
		quit();
	}

	public void cancelButtonWidgetSelected(SelectionEvent evt) {
		quit();
	}

	public void textTextFieldFocusLost(FocusEvent evt) {}

	boolean defaultLayout = false;
	boolean defaultConstraints = false;
	boolean addGetter = false;

	private void quit() {
		if (dialogShell.isDisposed())
			return;
		if (cancel) {
			name = null;
			text = null;
		}
		name = nameTextField.getText();
		text = textTextField.getText();
		defaultLayout = defaultLayoutButton.getSelection();
		defaultConstraints = defaultButton.getSelection();
		addGetter = addGetterButton.getSelection();
		dialogShell.dispose();
		site.getPage().removeSelectionListener(lps);
		comp.setSelectionListener(null);
	}

	/** Auto-generated event handler method */
	public void textTextFieldKeyTraversed(TraverseEvent evt) {
		if (!evt.doit)
			return;
		if (evt.detail == SWT.TRAVERSE_RETURN) {
			cancel = false;
			quit();
		}
	}

	/** Auto-generated event handler method */
	public void nameTextFieldKeyTraversed(TraverseEvent evt) {
		if (!evt.doit)
			return;
		if (!textComp.isVisible()) {
			cancel = false;
			quit();
			return;
		}
		if (!textTextField.isDisposed()) {
			textTextField.setSelection(0, textTextField.getText().length());
			textTextField.setFocus();
		}
	}

	public void nameTextFieldModifyText(ModifyEvent evt) {
		//name = nameTextField.getText();
	}

	public void textTextFieldModifyText(ModifyEvent evt) {
		//text = textTextField.getText();
	}

	public Object[] getResults() {
		if (cancel)
			return null;
		return new Object[] {
			name,
			text,
			new Boolean(defaultConstraints),
			new Boolean(defaultLayout),
			imgWrapper,
			new Boolean(addGetter)};
	}

	public void imgBrowseButtonWidgetSelected(SelectionEvent evt) {
		String propName = "image";
		if (comp.hasProperty("icon"))
			propName = "icon";
		ImageFileDialogCellEditor imgDialog = new ImageFileDialogCellEditor(imgComp, comp, propName);
		imgDialog.setEditor(editor);
		imgWrapper = (ImageWrapper) imgDialog.openDialogBox(imgBrowseButton);
		if (imgWrapper != null) {
			imgWrapper.setEditor(editor);
			imgText.setText(imgWrapper.toString());
		} else {
			imgText.setText("");
		}
	}
	
	public Button getOkButton() {
		return okButton;
	}
	
	private void openConstructorDialog(SelectionEvent evt) {
		ConstructorParamsDialog cpd = 
			new ConstructorParamsDialog(editor.getSite().getShell(), SWT.NULL, editor.getSite(), comp);
		cpd.open();
		Object[] params = cpd.getParameters();
		if(params == null)
			return;
		setConstructor(cpd.getConstructor(), cpd.getConstructorDesc(), params);
	}
	
	private void setConstructor(Constructor con, String desc, Object[] params) {
		if(con == null) {
			Class cls = comp.getMainClass();
			try {
				con = cls.getConstructor(null);
			} catch (Exception e) {
			}
			if(con == null) {
				text1.setText("...no default constructor - pick alternative please...");
				return;
			}
			desc = con.getName()+"()";
			params = new Object[0];
		} else {
			if(desc == null)
				desc = ConstructorManager.getDescription(con);
			if(params == null) {
				text1.setText(desc);
				return;
			}
		}
		
		String code = "new "+comp.getClassNameForCode()+"(";
		for (int i = 0; i < params.length; i++) {
			if(i != 0)
				code += ", ";
			if(params[i] == null) {
				code += "null";
			} else {
				String paramCode = comp.getJavaCodeForObject(params[i], con.getParameterTypes()[i], "no-id")[1];
				if("\"null\"".equals(paramCode))
					paramCode = "null";
				code += paramCode;
			}
		}
		code += ")";
		text1.setText(desc);
		comp.setConstructor(con, params, code);
	}

	public void setConstructor(Constructor defaultCon) {
		this.defaultCon = defaultCon;
	}
}
