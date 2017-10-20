package com.cloudgarden.jigloo.preferences;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
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
public class PaletteDialog extends org.eclipse.swt.widgets.Dialog {

    private Shell dialogShell;
    private Button button1;
    private Text text4;
    private Button button3;
    private Button button2;
    private Composite composite1;
    private Label label4;
    private Text text3;
    private Label label3;
    private Text text1;
    private Label label1;

    private String[] value;
    private int type;
    private String prjName = null;
    
    /**
    * Auto-generated main method to display this 
    * org.eclipse.swt.widgets.Dialog inside a new Shell.
    */
    public static void main(String[] args) {
        try {
            Display display = Display.getDefault();
            Shell shell = new Shell(display);
            PaletteDialog inst = new PaletteDialog(shell, SWT.NULL);
            inst.open(10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaletteDialog(Shell parent, int style) {
        super(parent, style);
    }

    public void open(int x, int y) {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
            dialogShell.setLocation(x, y);
            
            GridLayout dialogShellLayout = new GridLayout();
            dialogShell.setLayout(dialogShellLayout);
            dialogShell.setText("Add/Edit palette component");
            dialogShellLayout.numColumns = 3;
            dialogShellLayout.marginHeight = 10;
            dialogShellLayout.marginWidth = 10;
            {
                label1 = new Label(dialogShell, SWT.NONE);
                label1.setText("Class");
            }
            {
                GridData text1LData = new GridData();
                text1LData.horizontalAlignment = GridData.FILL;
                text1LData.grabExcessHorizontalSpace = true;
                text1 = new Text(dialogShell, SWT.BORDER);
                text1.setLayoutData(text1LData);
                text1.setEditable(false);
            }
            {
                button1 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                button1.setText("...");
                button1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        try {
                        IType nt = getNewType();
                        if(nt != null) {
                            String clsName = nt.getFullyQualifiedName();
                            IProject proj = nt.getJavaProject().getProject();
                            prjName = proj.getName();
                            IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
                            Class cls = ClassLoaderCache.loadClass(wsr.getProject(prjName),
                                    clsName, getClass().getClassLoader(), false);
                            if(cls != null) {
                                text1.setText(clsName);
                                BeanInfo bi = BeanHandler.getBeanInfo(cls, proj);
                                if(bi != null) {
                                    BeanDescriptor desc = bi.getBeanDescriptor();
                                    if(desc != null) {
                                        String cmnt = desc.getShortDescription();
                                        if(cmnt != null)
                                            text3.setText("Add "+clsName + " - "+cmnt);
                                    }
                                }
                            }
                        }
                    } catch(Throwable t) {
                        t.printStackTrace();
                    }
                    }
                    
                });
            }
            {
                label3 = new Label(dialogShell, SWT.NONE);
                label3.setText("Tooltip");
            }
            {
                GridData text3LData = new GridData();
                text3LData.horizontalSpan = 2;
                text3LData.widthHint = 300;
                text3 = new Text(dialogShell, SWT.BORDER);
                text3.setLayoutData(text3LData);
            }

            {
                composite1 = new Composite(dialogShell, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.makeColumnsEqualWidth = true;
                composite1Layout.numColumns = 2;
                composite1Layout.horizontalSpacing = 20;
                GridData composite1LData = new GridData();
                composite1LData.horizontalSpan = 3;
                composite1LData.horizontalAlignment = GridData.END;
                composite1LData.widthHint = 202;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button2LData = new GridData();
                    button2LData.horizontalAlignment = GridData.FILL;
                    button2LData.grabExcessHorizontalSpace = true;
                    button2.setLayoutData(button2LData);
                    button2.setText("OK");
                    button2.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            value = new String[] {
                                    text1.getText(),
                                    text3.getText(), 
                                    text4 != null ? text4.getText(): "",
                                    prjName
                                    };
                            dialogShell.dispose();
                        }
                    });
                }
                {
                    button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button3LData = new GridData();
                    button3LData.horizontalAlignment = GridData.FILL;
                    button3LData.grabExcessHorizontalSpace = true;
                    button3.setLayoutData(button3LData);
                    button3.setText("Cancel");
                    button3.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            value = null;
                            dialogShell.dispose();
                        }
                    });
                }
            }
            if(value != null) {
                text1.setText(value[0]);
                text3.setText(value[1]);
                if(text4 != null)
                    text4.setText(value[2]);
            }            
            value = null;
            dialogShell.layout();
            dialogShell.pack();
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
    private IType getNewType() {
		SelectionDialog dialog = null;
		try {
			dialog =
				JavaUI.createTypeDialog(
					dialogShell,
					new ProgressMonitorDialog(dialogShell),
					SearchEngine.createWorkspaceScope(),
					IJavaElementSearchConstants.CONSIDER_CLASSES,
					false);
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
		dialog.setTitle("Select new class");
		dialog.setMessage("Select new class");
		if (dialog.open() == IDialogConstants.CANCEL_ID)
			return null;
		Object[] types = dialog.getResult();
		dialog.close();
		if (types == null || types.length == 0)
			return null;
		return (IType) types[0];
    }
    
    public void setValue(String[] value, int type) {
        this.value = value;
        this.type = type;
    }
    
    public String[] getValue() {
        return value;
    }
}
