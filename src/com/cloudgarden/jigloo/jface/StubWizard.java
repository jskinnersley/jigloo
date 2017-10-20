/*
 * Created on May 2, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.jface;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.controls.OrderableComposite;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StubWizard extends Wizard {

	private OrderableComposite page;

	public boolean performFinish() {
		return false;
	}

	public IWizardContainer getContainer() {
		return super.getContainer();
	}

	public OrderableComposite getContentComposite() {
		return page;
	}

	public IWizardPage getStartingPage() {
		IWizardPage wp = new WizardPage("TEST") {
			public void createControl(Composite arg0) {
				page = new OrderableComposite(arg0, SWT.NULL);
				page.setLayout(new StackLayout());
				setControl(page);
			}
		};
		return wp;
	}

}
