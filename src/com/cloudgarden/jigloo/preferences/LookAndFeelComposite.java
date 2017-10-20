package com.cloudgarden.jigloo.preferences;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.cloudgarden.jigloo.wrappers.LookAndFeelWrapper;


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
public class LookAndFeelComposite extends org.eclipse.swt.widgets.Composite {
	private Table table1;
	private TableColumn tableColumn1;
	private Button button4;
	private Button button3;
	private Button button2;
	private Composite composite1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		LookAndFeelComposite inst = new LookAndFeelComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public LookAndFeelComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
            {
                table1 = new Table(this, SWT.CHECK);
                table1.setHeaderVisible(true);
                GridData table1LData = new GridData();
                table1LData.horizontalAlignment = GridData.FILL;
                table1LData.grabExcessHorizontalSpace = true;
                table1LData.verticalAlignment = GridData.FILL;
                table1LData.grabExcessVerticalSpace = true;
                table1.setLayoutData(table1LData);
                table1.setLinesVisible(true);
                table1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        table1WidgetSelected(evt);
                    }
                });
                {
                    tableColumn1 = new TableColumn(table1, SWT.NONE);
                    tableColumn1.setText("Look And Feel");
                    tableColumn1.setWidth(300);
                }
            }
            {
                composite1 = new Composite(this, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.makeColumnsEqualWidth = true;
                GridData composite1LData = new GridData();
                composite1LData.verticalAlignment = GridData.BEGINNING;
                composite1LData.widthHint = 89;
                composite1LData.heightHint = 89;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button2LData = new GridData();
                    button2LData.grabExcessHorizontalSpace = true;
                    button2LData.horizontalAlignment = GridData.FILL;
                    button2.setLayoutData(button2LData);
                    button2.setText("Add");
                    button2.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            editLAF(-1);
                        }
                    });
                }
                {
                    button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button3LData = new GridData();
                    button3LData.grabExcessHorizontalSpace = true;
                    button3LData.horizontalAlignment = GridData.FILL;
                    button3.setLayoutData(button3LData);
                    button3.setText("Edit");
                    button3.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            if(table1.getSelectionIndex() < 0)
                                return;
                            editLAF(table1.getSelectionIndex());
                        }
                    });
                }
                {
                    button4 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button4LData = new GridData();
                    button4LData.grabExcessHorizontalSpace = true;
                    button4LData.horizontalAlignment = GridData.FILL;
                    button4.setLayoutData(button4LData);
                    button4.setText("Remove");
                    button4.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            if(table1.getSelectionIndex() == -1)
                                return;
                            items.remove(table1.getSelectionIndex());
                            setLafs(items);
                        }
                    });
                }
            }
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Table getTable() {
	    return table1;
	}
	
	private void editLAF(int pos) {
	    LookAndFeelWrapper lafw = null;
	    if(pos != -1)
	        lafw = (LookAndFeelWrapper) items.elementAt(pos);
	    LookAndFeelDialog dialog = new LookAndFeelDialog(getShell(), 
	            SWT.DIALOG_TRIM | SWT.RESIZE, lafw);
	    dialog.open();
	    lafw = dialog.getLAFWrapper();
	    if(lafw != null) {
	        if(pos == -1)
	            items.add(lafw);
	        else
	            items.set(pos, lafw);
	        setLafs(items);
	        
	        
	    }
	}
	
	private void table1WidgetSelected(SelectionEvent evt) {
	    TableItem[] its = table1.getItems();
	    TableItem sel = (TableItem)evt.item;
	    if(!sel.getChecked())
	        sel = null;
	    for (int i = 0; i < its.length; i++) {	
	        TableItem it = its[i];
	        LookAndFeelWrapper lafw = (LookAndFeelWrapper)items.elementAt(i);
	        if(sel == null && it.getChecked()) {
                lafw.isDefault = true;
                it.setText(lafw.toString()+"    (default)");
	        } else if(sel != null && it.equals(sel)) {
                lafw.isDefault = true;
                it.setText(lafw.toString()+"    (default)");
	        } else {
                lafw.isDefault = false;
                it.setChecked(false);
                it.setText(lafw.toString());
            }
        }
	}

	private Vector items;
	
    /**
     * @param items
     */
    public void setLafs(Vector items) {
        this.items = items;
        int itc = table1.getItemCount();
        if(itc > items.size()) {
            table1.remove(items.size(), itc-1);
        }
//        table1.clearAll();
        for (int i = 0; i < items.size(); i++) {
            LookAndFeelWrapper lafw = (LookAndFeelWrapper)items.elementAt(i);
            TableItem ti = null;
            if(i >= itc)
                ti = new TableItem(table1, SWT.NULL);
            else
                ti = table1.getItem(i);
            if(lafw.isDefault) {
                ti.setText(lafw.toString()+"    (default)");
                ti.setChecked(true);
            } else {
                ti.setText(lafw.toString());
                ti.setChecked(false);
            }
        }
    }

    /**
     * @return
     */
    public Vector getItems() {
        return items;
    }
}
