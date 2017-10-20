package com.cloudgarden.jigloo.preferences;

import java.net.URLClassLoader;
import java.util.Vector;

import javax.swing.LookAndFeel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LookAndFeelWrapper;


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
public class LookAndFeelDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text jarPath;
	private Button button3;
	private Button button2;
	private Composite composite1;
	private Label label4;
	private Label label3;
	private Label label2;
	private Text text2;
	private CCombo text1;
	private Button jarPathButton;
	private LookAndFeelWrapper lafw;
	private String jarFile;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			LookAndFeelDialog inst = new LookAndFeelDialog(shell, SWT.NULL, null);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LookAndFeelDialog(Shell parent, int style, LookAndFeelWrapper lafw) {
		super(parent, style);
		this.lafw = lafw;
	}
	
	private URLClassLoader cl;
	private Vector lafClasses;
	
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
            {
                label2 = new Label(dialogShell, SWT.NONE);
                GridData label2LData = new GridData();
                label2LData.horizontalSpan = 2;
                label2.setLayoutData(label2LData);
                label2.setText("Path to LookAndFeel jar file:");
            }
			dialogShellLayout.numColumns = 2;
			dialogShellLayout.verticalSpacing = 4;
            {
                jarPath = new Text(dialogShell, SWT.BORDER);
                GridData jarPathLData = new GridData();
                jarPathLData.horizontalAlignment = GridData.FILL;
                jarPathLData.grabExcessHorizontalSpace = true;
                jarPath.setLayoutData(jarPathLData);
            }
            {
                jarPathButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                GridData jarPathButtonLData = new GridData();
                jarPathButtonLData.widthHint = 35;
                jarPathButton.setLayoutData(jarPathButtonLData);
                jarPathButton.setText("...");
                jarPathButton.setAlignment(SWT.CENTER);
                jarPathButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        FileDialog fd = new FileDialog(dialogShell);
                        String file = fd.open();
                        if(file != null) {
                            setJarFile(file);
//                            IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
//                            IFile res = wsr.getFileForLocation(new Path(file));
//                            IJavaSearchScope scope = 
//                                SearchEngine.createJavaSearchScope(new IResource[] {res});
//                            IJavaSearchResultCollector resCol = new IJavaSearchResultCollector() {
//                                public void aboutToStart() {
//                                }
//                                public void accept(IResource resource, int start, int end, IJavaElement enclosingElement, int accuracy) throws CoreException {
//                                    System.out.println("ACCEPT "+resource+", "+enclosingElement);
//                                }
//                                public void done() {
//                                }
//                                public IProgressMonitor getProgressMonitor() {
//                                    return null;
//                                }
//                                
//                            };
//                            try {
//                                new SearchEngine().search(ResourcesPlugin.getWorkspace(),
//                                        SearchEngine.createSearchPattern(
//                                                "*", 
//                                                IJavaSearchConstants.TYPE,
//                                                IJavaSearchConstants.ALL_OCCURRENCES, 
//                                                true) , scope, resCol);
//                            } catch (JavaModelException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                });
            }
            {
                label3 = new Label(dialogShell, SWT.NONE);
                GridData label3LData = new GridData();
                label3LData.horizontalSpan = 2;
                label3.setLayoutData(label3LData);
                label3.setText("Class name");
            }
            {
                GridData text1LData = new GridData();
                text1LData.horizontalAlignment = GridData.FILL;
                text1LData.horizontalSpan = 2;
                text1 = new CCombo(dialogShell, SWT.READ_ONLY | SWT.BORDER);
                text1.setLayoutData(text1LData);
                text1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        try {
                            LookAndFeel laf = (LookAndFeel) lafClasses.elementAt(text1.getSelectionIndex());
                            text2.setText(laf.getName());
                        } catch(Throwable t) {
                            t.printStackTrace();
                        }
                    }
                });
            }
            {
                label4 = new Label(dialogShell, SWT.NONE);
                GridData label4LData = new GridData();
                label4LData.horizontalSpan = 2;
                label4.setLayoutData(label4LData);
                label4.setText("Look and Feel Name");
            }
            {
                GridData text2LData = new GridData();
                text2LData.horizontalAlignment = GridData.FILL;
                text2LData.horizontalSpan = 2;
                text2 = new Text(dialogShell, SWT.BORDER);
                text2.setLayoutData(text2LData);
            }
            {
                composite1 = new Composite(dialogShell, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.numColumns = 2;
                composite1Layout.horizontalSpacing = 20;
                composite1Layout.makeColumnsEqualWidth = true;
                GridData composite1LData = new GridData();
                composite1LData.horizontalAlignment = GridData.END;
                composite1LData.horizontalSpan = 2;
                composite1LData.grabExcessVerticalSpace = true;
                composite1LData.verticalAlignment = GridData.FILL;
                composite1LData.widthHint = 190;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button2LData = new GridData();
                    button2LData.horizontalAlignment = GridData.FILL;
                    button2.setLayoutData(button2LData);
                    button2.setText("OK");
                    button2.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            close(true);
                        }
                    });
                }
                {
                    button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button3LData = new GridData();
                    button3LData.horizontalAlignment = GridData.FILL;
                    button3.setLayoutData(button3LData);
                    button3.setText("Cancel");
                    button3.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            close(false);
                        }
                    });
                }
            }
            if(lafw != null) {
                if(lafw.path != null) {
                    setJarFile(lafw.path);
                    for (int i = 0; i < text1.getItemCount(); i++) {
                        if(text1.getItem(i).equals(lafw.className))
                            text1.select(i);
                    }
                } else {
                    jarPath.setText("in plugin classpath");
                    jarPath.setEditable(false);
                    jarPathButton.setEnabled(false);
                    text1.setText(lafw.className);
                    text1.setEnabled(false);
                }
                text2.setText(lafw.name);
            }
			dialogShell.layout();
			dialogShell.pack();
			Point sz = dialogShell.getSize();
			dialogShell.setSize(400, sz.y);
			dialogShell.setText("Add/Edit Look and Feel");
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

    /**
     * @param file
     */
    protected void setJarFile(String file) {
        jarPath.setText(file);
        jarFile = file;
        try {
            String[] errors = {""};
            lafClasses = JiglooUtils.getClassesInArchive(file, true, errors);
            String[] items = new String[lafClasses.size()];
            if(items.length > 0) {
                for (int i = 0; i < items.length; i++) {
                    items[i] = ((LookAndFeel)lafClasses.elementAt(i)).getClass().getName();
                }
                text1.setItems(items);
                text1.select(0);
                LookAndFeel laf = (LookAndFeel)lafClasses.elementAt(0);
                text2.setText(laf.getName());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @param b
     */
    protected void close(boolean apply) {
        if(!apply)
            lafw = null;
        else {
            lafw = new LookAndFeelWrapper(text1.getText(), text2.getText(), jarPath.getText());
        }
        dialogShell.dispose();
    }
    
    public LookAndFeelWrapper getLAFWrapper() {
        return lafw;
    }
	
}
