package com.cloudgarden.jigloo.preferences;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.dialog.ErrorDialog;
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
public class ArchiveDialog extends org.eclipse.swt.widgets.Dialog {

    private Shell dialogShell;
    private TableColumn tableColumn1;
    private Table table1;
    private Button button4;
    private Text text2;
    private Label label2;
    private Group group2;
    private Button button3;
    private Button button2;
    private Composite composite1;

    private Object[] value;
    private Button button7;
    private Button button6;
    private Composite composite2;
    private Button button5;
    private Button button1;
    private String archive;
    private Vector classes;
    
    /**
    * Auto-generated main method to display this 
    * org.eclipse.swt.widgets.Dialog inside a new Shell.
    */
    public static void main(String[] args) {
        try {
            Display display = Display.getDefault();
            Shell shell = new Shell(display);
            ArchiveDialog inst = new ArchiveDialog(shell, SWT.NULL);
            inst.open(10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArchiveDialog(Shell parent, int style) {
        super(parent, style);
    }

    public void open(int x, int y) {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL  | SWT.RESIZE);
            dialogShell.setLocation(x, y);
            
            GridLayout dialogShellLayout = new GridLayout();
            dialogShell.setLayout(dialogShellLayout);
            dialogShell.setText("Add from archive...");
            dialogShellLayout.marginHeight = 10;
            dialogShellLayout.marginWidth = 10;
            dialogShellLayout.numColumns = 2;
            {
                group2 = new Group(dialogShell, SWT.NONE);
                GridLayout group2Layout = new GridLayout();
                group2Layout.numColumns = 3;
                group2.setLayout(group2Layout);
                GridData group2LData = new GridData();
                group2LData.grabExcessHorizontalSpace = true;
                group2LData.horizontalAlignment = GridData.FILL;
                group2LData.horizontalSpan = 2;
                group2LData.grabExcessVerticalSpace = true;
                group2LData.verticalAlignment = GridData.FILL;
                group2.setLayoutData(group2LData);
                group2.setText("Add Beans from Archive");
                {
                    label2 = new Label(group2, SWT.NONE);
                    label2.setText("Archive");
                }
                {
                    text2 = new Text(group2, SWT.BORDER);
                    GridData text2LData = new GridData();
                    text2LData.horizontalAlignment = GridData.FILL;
                    text2LData.grabExcessHorizontalSpace = true;
                    text2.setLayoutData(text2LData);
                    text2.setEditable(false);
                    text2.setText("Select archive (zip or jar file) using browse button");
                }
                {
                    button4 = new Button(group2, SWT.PUSH | SWT.CENTER);
                    button4.setText("Browse...");
                    button4.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            try {
                                FileDialog fd = new FileDialog(dialogShell);
                                String file = fd.open();
                                if(file != null) {
                                    text2.setText(file);
                                    archive = file;
                                    String[] errors = {""};
                                    classes = JiglooUtils.getClassesInArchive(file, false, errors);
                                    if(!errors[0].equals("")) {
                                        String msg = 
                                        "Jigloo was unable to load some classes from the archive.\n \n" +
                                        "This may be because they reference other classes outside" +
                                        " of the archive, or for some other reason.\n \n" +
                                        "This might not actually cause a problem if the required classes exist in your project," +
                                        " but if you are unable to see the added classes " +
                                        "in your component palette then the errors listed below may help " +
                                        "diagnose the problem:\n \n"+errors[0];
                                        ErrorDialog ed = new ErrorDialog(dialogShell, SWT.NULL);
                                        ed.addError( "Warning: Unable to load classes", msg, null);
                                        ed.open();
//                                        MessageDialog.openWarning(dialogShell, "Unable to load classes", msg);
                                    }
                                    int itc = table1.getItemCount();
                                    if(itc > classes.size()) {
                                        table1.remove(classes.size(), itc-1);
                                    }
                                    for (int i = 0; i < classes.size(); i++) {
                                        Object cls = classes.elementAt(i);
                                        String name;
                                        if(cls instanceof Class)
                                            name = ((Class)cls).getName();
                                        else
                                            name = cls.toString();
                                       TableItem ti = null;
                                        if(i >= itc)
                                            ti = new TableItem(table1, SWT.NULL);
                                        else
                                            ti = table1.getItem(i);
                                         ti.setText(name);
                                        if(!name.endsWith("PropertyEditor") 
                                                && !name.endsWith("BeanInfo")
                                                && !name.endsWith("BeanDescriptor")
                                                && !name.endsWith("MethodDescriptor")
                                                && !name.endsWith("PropertyDescriptor"))
                                            ti.setChecked(true);
                                    }
                                }
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    });
                }
                {
                    GridData list1LData = new GridData();
                    list1LData.horizontalAlignment = GridData.FILL;
                    list1LData.horizontalSpan = 3;
                    list1LData.grabExcessVerticalSpace = true;
                    list1LData.verticalAlignment = GridData.FILL;
                    list1LData.grabExcessHorizontalSpace = true;
                    list1LData.heightHint = 200;
                    table1 = new Table(group2, SWT.MULTI | SWT.CHECK);
                    table1.setLayoutData(list1LData);
                    table1.setLinesVisible(true);
                    table1.setHeaderVisible(true);
                    {
                        tableColumn1 = new TableColumn(table1, SWT.NONE);
                        tableColumn1.setText("Bean Class");
                        tableColumn1.setWidth(435);
                    }
                }
            }
            {
                composite2 = new Composite(dialogShell, SWT.NONE);
                GridLayout composite2Layout = new GridLayout();
                composite2Layout.makeColumnsEqualWidth = true;
                composite2Layout.numColumns = 2;
                composite2Layout.horizontalSpacing = 20;
                GridData composite2LData = new GridData();
                composite2LData.widthHint = -1;
                composite2LData.heightHint = -1;
                composite2.setLayoutData(composite2LData);
                composite2.setLayout(composite2Layout);
                {
                    button3 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    GridData button3LData = new GridData();
                    button3LData.horizontalAlignment = GridData.FILL;
                    button3LData.grabExcessHorizontalSpace = true;
                    button3.setLayoutData(button3LData);
                    button3.setText("Check All");
                    button3.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            TableItem[] items = table1.getItems();
                            for (int i = 0; i < items.length; i++) {
                                TableItem item = items[i];
                                item.setChecked(true);
                            }
                        }
                    });
                }
                {
                    button2 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    GridData button2LData = new GridData();
                    button2LData.horizontalAlignment = GridData.FILL;
                    button2LData.grabExcessHorizontalSpace = true;
                    button2.setLayoutData(button2LData);
                    button2.setText("Uncheck All");
                    button2.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            TableItem[] items = table1.getItems();
                            for (int i = 0; i < items.length; i++) {
                                TableItem item = items[i];
                                item.setChecked(false);
                            }
                        }
                    });
                }
                {
                    button6 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    GridData button6LData = new GridData();
                    button6LData.horizontalAlignment = GridData.FILL;
                    button6LData.grabExcessHorizontalSpace = true;
                    button6.setLayoutData(button6LData);
                    button6.setText("Check Selected");
                    button6.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            TableItem[] items = table1.getSelection();
                            for (int i = 0; i < items.length; i++) {
                                TableItem item = items[i];
                                item.setChecked(true);
                            }
                        }
                    });
                }
                {
                    button7 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    GridData button7LData = new GridData();
                    button7LData.horizontalAlignment = GridData.FILL;
                    button7LData.grabExcessHorizontalSpace = true;
                    button7.setLayoutData(button7LData);
                    button7.setText("Uncheck Selected");
                    button7.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            TableItem[] items = table1.getSelection();
                            for (int i = 0; i < items.length; i++) {
                                TableItem item = items[i];
                                item.setChecked(false);
                            }
                        }
                    });
                }
            }
            {
                composite1 = new Composite(dialogShell, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.makeColumnsEqualWidth = true;
                composite1Layout.numColumns = 2;
                composite1Layout.horizontalSpacing = 20;
                GridData composite1LData = new GridData();
                composite1LData.horizontalAlignment = GridData.END;
                composite1LData.widthHint = -1;
                composite1LData.heightHint = -1;
                composite1LData.verticalAlignment = GridData.END;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    button1 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button1LData = new GridData();
                    button1LData.horizontalAlignment = GridData.FILL;
                    button1LData.grabExcessHorizontalSpace = true;
                    button1.setLayoutData(button1LData);
                    button1.setText("OK");
                    button1.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            TableItem[] items = table1.getItems();
                            for (int i = items.length - 1; i >= 0; i--) {
                                TableItem item = items[i];
                                if(!item.getChecked())
                                    classes.remove(i);
                            }
                            value = new Object[] { archive, classes };
                            dialogShell.dispose();
                        }
                    });
                }
                {
                    button5 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button5LData = new GridData();
                    button5LData.horizontalAlignment = GridData.FILL;
                    button5LData.grabExcessHorizontalSpace = true;
                    button5.setLayoutData(button5LData);
                    button5.setText("Cancel");
                    button5.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            value = null;
                            dialogShell.dispose();
                        }
                    });
                }
            }

            value = null;
            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setSize(542, 359);
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
    
    public Object[] getValue() {
        return value;
    }
}
