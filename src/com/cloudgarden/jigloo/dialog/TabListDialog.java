package com.cloudgarden.jigloo.dialog;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;


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
public class TabListDialog extends org.eclipse.swt.widgets.Dialog
implements ISelectionChangedListener {

    private Shell dialogShell;
    private Button button1;
    private Button button3;
    private Label label1;
    private Button button2;
    private FormComponent comp;
    private int cc;

    /**
    * Auto-generated main method to display this 
    * org.eclipse.swt.widgets.Dialog inside a new Shell.
    */
    public static void main(String[] args) {
        try {
            Display display = Display.getDefault();
            Shell shell = new Shell(display);
            TabListDialog inst = new TabListDialog(shell, null);
            inst.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TabListDialog(Shell parent, FormComponent comp) {
        super(parent, SWT.NULL);
        this.comp = comp;
        if(comp != null)
            cc = comp.getNonInheritedChildCount();
    }

	public void open() {
	    open(0,0);
	}
	
	public void open(int x, int y) {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM);

            GridLayout dialogShellLayout = new GridLayout();
            dialogShellLayout.numColumns = 2;
            dialogShell.setLayout(dialogShellLayout);
            {
                label1 = new Label(dialogShell, SWT.WRAP);
                GridData label1LData = new GridData();
                label1LData.grabExcessVerticalSpace = true;
                label1LData.verticalSpan = 3;
                label1LData.verticalAlignment = GridData.FILL;
                label1LData.grabExcessHorizontalSpace = true;
                label1LData.horizontalAlignment = GridData.FILL;
                label1.setLayoutData(label1LData);
                label1.setText("Click on the controls (which must be children of "+comp.getNameInCode()+")" +
                		"\nin the order you wish them to be in the tab list.\n\n" +
                		"The green numbers by the controls indicate the tab order." +
                		"\n\"...\" indicates that the control is not in the tab list." +
                		"\n\nThen hit:" +
                		"\n - \"Apply\" if the new order is correct or" +
                		"\n - \"Clear\" to clear the tab order");
            }
            {
                button1 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                GridData button1LData = new GridData();
                button1LData.horizontalAlignment = GridData.FILL;
                button1.setLayoutData(button1LData);
                button1.setText("Apply");
                button1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        comp.fixTabList();
                        exit();
                    }
                });
            }
            {
                button2 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                GridData button2LData = new GridData();
                button2LData.verticalAlignment = GridData.BEGINNING;
                button2LData.horizontalAlignment = GridData.FILL;
                button2.setLayoutData(button2LData);
                button2.setText("Clear");
                button2.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        comp.clearTabList();
                        currentTabNum = 1;
//                        exit();
                    }
                });
            }
            {
                button3 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                GridData button3LData = new GridData();
                button3LData.verticalAlignment = GridData.BEGINNING;
                button3LData.horizontalAlignment = GridData.FILL;
                button3.setLayoutData(button3LData);
                button3.setText("Cancel");
                button3.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        exit();
                    }
                });
            }
            dialogShell.layout();
            dialogShell.pack();
//            dialogShell.setSize(336, 156);
            Point sz = dialogShell.getSize();
            Rectangle db = Display.getDefault().getBounds();
            if(x+sz.x > db.width)
                x = db.width - sz.x;
            if(y+sz.y > db.height)
                y = db.height - sz.y;
			dialogShell.setLocation(x, y);
			if(comp != null) {
			    dialogShell.setText("Tab Order for "+comp.getNameInCode());
			    comp.initTabList();
//				comp.getEditor().showTabNumbers(comp, true);
				comp.getEditor().addSelectionChangedListener(this);
			}
            dialogShell.open();
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            if(comp.getEditor() != null) {
                comp.getEditor().setSelectedComponent(comp, false);
                comp.getEditor().showTabNumbers(comp, false);
                comp.getEditor().removeSelectionChangedListener(this);
            }
            comp = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private FormComponent lastSel = null;
	private int currentTabNum = 1;
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection sel = event.getSelection();
        if(sel instanceof StructuredSelection) {
            StructuredSelection ssel = (StructuredSelection) sel;
            Object sel1 = ssel.getFirstElement();
            if(sel1 instanceof FormComponent) {
                FormComponent fc = (FormComponent) sel1;
                if(lastSel == null)
                    comp.clearTabList();
                if(lastSel == null || !lastSel.equals(fc)) {
                    if(currentTabNum > cc) {
                        int tab = fc.getTabNumber();
                        for(int i=0;i<cc;i++) {
                            FormComponent child = comp.getNonInheritedChildAt(i);
                            if(child.equals(fc))
                                continue;
                            int tabno = child.getTabNumber();
                            if(tabno > 0 && tabno > tab) {
                                tabno--;
                                child.setTabNumber(tabno);
                            }
                        }
                        currentTabNum = cc;
                    }
                    if(comp.hasChild(fc))
                        fc.setTabNumber(currentTabNum++);
                }
                lastSel = fc;
            }
        }
    }
    private void exit() {
        dialogShell.dispose();
    }

}
