/*
 * Created on May 1, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.jface;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.controls.OrderableComposite;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StubWizDialog extends WizardDialog {

	OrderableComposite oc;
	StubWizard sw;

	public StubWizDialog(Shell parentShell) {
		super(parentShell, new StubWizard());
		sw = (StubWizard) getWizard();
	}

	public Control getContents(Composite par) {
		Control c = super.getContents();
		if (c == null) {
			OrderableComposite cmp = new OrderableComposite(par, SWT.NULL);
			c = createContents(cmp);
			return cmp;
		}
		return c;
	}

	public OrderableComposite getOrderableComposite() {
		return sw.getContentComposite();
	}

	public Control getButtonBar() {
		return bb;
		//return super.getButtonBar();
	}

	/**
	 * override update since we are not displaying inside a dialog Shell
	 */
	protected void update() {
		//super.update();
	}

	OrderableComposite bb;
	
	public Control createButtonBar(Composite parent) {
		if(bb != null)
			return bb;
		bb = new OrderableComposite(parent, SWT.NULL);

		//below copied from superclass code
		GridLayout layout = new GridLayout();
		layout.numColumns = 0; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth =
			convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight =
			convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing =
			convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing =
			convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);

		bb.setLayout(layout);

		GridData data =
			new GridData(
				GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
		bb.setLayoutData(data);

		bb.setFont(parent.getFont());

		return bb;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}

	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}

	protected Control createDialogArea(Composite parent) {
		OrderableComposite oc = new OrderableComposite(parent, SWT.NULL);
		oc.setFont(parent.getFont());
		GridLayout gl = new GridLayout();
		gl.marginHeight = gl.marginWidth = 0;
		oc.setLayout(gl);
		GridData gd = new GridData();
		gd.widthHint = 400;
		oc.setLayoutData(gd);
		super.createDialogArea(oc);
		return oc;
		//return super.createDialogArea(parent);
	}

	public IWizardPage getCurrentPage() {
		return super.getCurrentPage();
	}

	public Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return super.createButton(parent, id, label, defaultButton);
	}

}
