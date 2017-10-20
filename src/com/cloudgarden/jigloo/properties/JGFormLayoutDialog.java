package com.cloudgarden.jigloo.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.util.JiglooUtils;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;


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
public class JGFormLayoutDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite2;
	private Button okButton;
	private Button cancelButton;
	private Composite composite5;
	private Button button4;
	private Button button3;
	private Composite composite4;
	private Composite composite3;
	private TableColumn tableColumn5;
	private TableColumn tableColumn7;
	private TableColumn tableColumn3;
	private TableColumn tableColumn6;
	private TableColumn tableColumn4;
	private TableColumn tableColumn2;
	private TableColumn tableColumn1;
	private TableColumn tableColumn8;
	private Button button1;
	private Table table1;
	private Text text1;
	private Label label1;


	private String[] calis = new String[] { "left", "center", "right", "fill" };

	private String[] ralis = new String[] { "top", "center", "bottom", "fill" };

	private String[] units = new String[] { "px", "pt", "dlu", "in", "mm", "cm" };

	private String[] bounds = new String[] { "none", "max", "min" };

	private String[] csize = new String[] { "min", "default", "pref" };

	private String[] resizes = new String[] { "none", "grow", "grow()" };

	private TableItem edItem;

	private int edCol;

	private Text textEditor;

	private CCombo editor;

	boolean columns = false;

	static final int COL_ROW_CNT = 0;
	static final int COL_ALIGNS = 1;
	static final int COL_SIZE = 2;
	static final int COL_UNITS = 3;
	static final int COL_BOUNDS = 4;
	static final int COL_CSIZE = 5;
	static final int COL_RESIZE = 6;
	static final int COL_RESIZE_WT = 7;


	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			JGFormLayoutDialog inst = new JGFormLayoutDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JGFormLayoutDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

			dialogShell.setLayout(new GridLayout());
			{
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2.setLayout(composite2Layout);
				composite2.setLayoutData(composite2LData);
				{
					label1 = new Label(composite2, SWT.NONE);
					label1.setText("Col Spec String:");
				}
				{
					GridData text1LData = new GridData();
					text1LData.verticalAlignment = GridData.FILL;
					text1LData.horizontalAlignment = GridData.FILL;
					text1LData.grabExcessHorizontalSpace = true;
					text1LData.grabExcessVerticalSpace = true;
					text1 = new Text(composite2, SWT.BORDER);
					text1.setLayoutData(text1LData);
				}
			}
			{
				table1 = new Table(dialogShell, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				table1.setHeaderVisible(true);
				GridData table1LData = new GridData();
				table1LData.verticalAlignment = GridData.FILL;
				table1LData.horizontalAlignment = GridData.FILL;
				table1LData.grabExcessHorizontalSpace = true;
				table1LData.grabExcessVerticalSpace = true;
				table1.setLayoutData(table1LData);
				table1.setLinesVisible(true);
				{
					tableColumn8 = new TableColumn(table1, SWT.NONE);
					tableColumn8.setText("Col/Row");
					tableColumn8.setWidth(52);
				}
				table1.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent evt) {
						table1MouseDown(evt);
					}
				});
				{
					tableColumn1 = new TableColumn(table1, SWT.NONE);
					tableColumn1.setText("Alignment");
					tableColumn1.setWidth(77);
				}
				{
					tableColumn2 = new TableColumn(table1, SWT.NONE);
					tableColumn2.setText("Size");
					tableColumn2.setWidth(62);
				}
				{
					tableColumn4 = new TableColumn(table1, SWT.NONE);
					tableColumn4.setText("Units");
					tableColumn4.setWidth(57);
				}
				{
					tableColumn5 = new TableColumn(table1, SWT.NONE);
					tableColumn5.setText("Bounded");
					tableColumn5.setWidth(62);
				}
				{
					tableColumn6 = new TableColumn(table1, SWT.NONE);
					tableColumn6.setText("Component Size");
					tableColumn6.setWidth(93);
				}
				{
					tableColumn3 = new TableColumn(table1, SWT.NONE);
					tableColumn3.setText("Resize");
					tableColumn3.setWidth(65);
				}
				{
					tableColumn7 = new TableColumn(table1, SWT.NONE);
					tableColumn7.setText("Resize Wt");
					tableColumn7.setWidth(70);
				}
			}
			{
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				composite3Layout.makeColumnsEqualWidth = true;
				composite3.setLayout(composite3Layout);
				composite3.setLayoutData(composite3LData);
				{
					composite4 = new Composite(composite3, SWT.NONE);
					GridLayout composite4Layout = new GridLayout();
					composite4Layout.numColumns = 3;
					composite4Layout.makeColumnsEqualWidth = true;
					composite4.setLayout(composite4Layout);
					{
						button3 = new Button(composite4, SWT.PUSH
							| SWT.FLAT
							| SWT.CENTER);
						GridData button3LData = new GridData();
						button3LData.widthHint = 70;
						button3.setLayoutData(button3LData);
						button3.setText("Add after");
						button3.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								addAfterButtonWidgetSelected(evt);
							}
						});
					}
					{
						button1 = new Button(composite4, SWT.PUSH | SWT.FLAT | SWT.CENTER);
						GridData button1LData1 = new GridData();
						button1LData1.widthHint = 70;
						button1.setLayoutData(button1LData1);
						button1.setText("Add before");
						button1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								addBeforeButtonWidgetSelected(evt);
							}
						});
					}
					{
						button4 = new Button(composite4, SWT.PUSH
							| SWT.FLAT
							| SWT.CENTER);
						GridData button4LData = new GridData();
						button4LData.widthHint = 70;
						button4.setLayoutData(button4LData);
						button4.setText("Delete");
						button4.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								button4WidgetSelected(evt);
							}
						});
					}
				}
				{
					GridData composite5LData = new GridData();
					composite5LData.horizontalAlignment = GridData.END;
					composite5 = new Composite(composite3, SWT.NONE);
					GridLayout composite5Layout = new GridLayout();
					composite5Layout.numColumns = 2;
					composite5Layout.makeColumnsEqualWidth = true;
					composite5.setLayout(composite5Layout);
					composite5.setLayoutData(composite5LData);
					{
						okButton = new Button(composite5, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.widthHint = 80;
						okButton.setLayoutData(button1LData);
						okButton.setText("OK");
						okButton.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								okButtonWidgetSelected(evt);
							}
						});
					}
					{
						cancelButton = new Button(composite5, SWT.PUSH | SWT.CENTER);
						GridData button2LData = new GridData();
						button2LData.widthHint = 80;
						cancelButton.setLayoutData(button2LData);
						cancelButton.setText("Cancel");
						cancelButton.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								cancelButtonWidgetSelected(evt);
							}
						});
					}
				}
			}
			if(formLayout != null) {
				initFromLayout(formLayout, columns);
			} else {
				initFromLayout(new FormLayout(
						"right:max(50dlu;pref), 4dlu, max(100dlu;min)",
						"p, 3dlu, p, 3dlu, fill:200dlu:grow"), true);
			}
			if(initialRow != -1)
				table1.select(initialRow);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(580, 322);
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

	private void addBeforeButtonWidgetSelected(SelectionEvent evt) {
		int sel = table1.getSelectionIndex();
		TableItem ti;
		if(sel == -1)
			sel = 0;
		ti = new TableItem(table1, SWT.NULL, sel);
		
		sel++;
		
		if (columns)
			ti.setText(new String[] { ""+sel, "fill", "10", "px", "max", "pref",
					"none", "0.0" });
		else
			ti.setText(new String[] { ""+sel, "center", "10", "px", "max", "pref",
					"none", "0.0" });
		updateSpecString();
	}

	int initialRow = -1;
	
	public void selectTableRow(int row) {
		initialRow = row;
	}
	
	private TableItem getSelectedItem() {
		TableItem[] sel= table1.getSelection();
		if(sel == null || sel.length == 0)
			return null;
		return sel[0];
	}
	
	private void addAfterButtonWidgetSelected(SelectionEvent evt) {
		int sel = table1.getSelectionIndex();
		TableItem ti;
		if(sel == -1)
			sel = table1.getItemCount()-1;
		ti = new TableItem(table1, SWT.NULL, sel+1);
			
		sel++;
			
		if (columns)
			ti.setText(new String[] { ""+sel, "fill", "10", "px", "max", "pref",
					"none", "0.0" });
		else
			ti.setText(new String[] { ""+sel, "center", "10", "px", "max", "pref",
					"none", "0.0" });
		updateSpecString();
	}

	private void button4WidgetSelected(SelectionEvent evt) {
		TableItem[] its = table1.getSelection();
		for (int i = 0; i < its.length; i++) {
			TableItem item = its[i];
			if (item != null && !item.isDisposed())
				item.dispose();
		}
		updateSpecString();
	}

	private CCombo createCombo(int row, int col) {
		if (col >= table1.getColumnCount())
			return null;
		final CCombo c = new CCombo(table1, SWT.NULL);
		c.setEditable(false);
		int x = 0;
		int y = table1.getHeaderHeight() + row * table1.getItemHeight();
		for (int i = 0; i < col; i++) {
			x += table1.getColumn(i).getWidth();
		}
		c.setBounds(x, y + 1, table1.getColumn(col).getWidth(), table1
				.getItemHeight());
		c.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateEditorItem();
				updateSpecString();
			}
		});
		c.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateEditorItem();
				updateSpecString();
				c.dispose();
			}
		});
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == '\r' || e.character == '\n') {
					text1.setFocus();
				}
			}
		});
		c.setFocus();
		return c;
	}

	private String extractNum(String size) {
		int pos = 0;
		while (pos < size.length()) {
			if (size.charAt(pos) < '0' || size.charAt(pos) > '9')
				return size.substring(0, pos);
			pos++;
		}
		return size;
	}

	private Text createText(int row, int col) {
		if (col >= table1.getColumnCount())
			return null;
		final Text c = new Text(table1, SWT.FLAT);
		int x = 0;
		int y = table1.getHeaderHeight() + row * table1.getItemHeight();
		for (int i = 0; i < col; i++) {
			x += table1.getColumn(i).getWidth();
		}
		c.setBounds(x, y + 1, table1.getColumn(col).getWidth(), table1
				.getItemHeight());
		c.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateEditorItem();
				updateSpecString();
				c.dispose();
			}
		});
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == '\r' || e.character == '\n') {
					text1.setFocus();
				}
			}
		});
		c.setFocus();
		return c;
	}

	private String getSizeSpec(TableItem it) {
		return getSizeSpec(it, false);
	}

	private String getSizeSpec(TableItem it, boolean forceAbs) {
		String size = it.getText(COL_SIZE);
		String unit = it.getText(COL_UNITS);
		if (!size.equals("")) {
			if(unit.equals("")) {
				unit = "px";
				it.setText(COL_UNITS, "px");
			}
			return size + unit;
		}
		if (forceAbs) {
			it.setText(COL_SIZE, "10");
			it.setText(COL_UNITS, "px");
			return "10px";
		}
		String csize = it.getText(COL_CSIZE);
		return csize;
	}

	private void initFromLayout(com.jgoodies.forms.layout.FormLayout fl,
			boolean cols) {
		columns = cols;
		if (cols) {
			label1.setText("Col Spec String:");
			tableColumn8.setText("Column");
		} else {
			label1.setText("Row Spec String:");
			tableColumn8.setText("Row");
		}
		
		int cc = fl.getColumnCount();
		if (!cols)
			cc = fl.getRowCount();
		String[] txt = new String[7];
		for (int i = 0; i < cc; i++) {
			FormSpec cs = null;
			if (cols) {
				cs = fl.getColumnSpec(i + 1);
				if(cs == null) {
					cs = FormFactory.DEFAULT_COLSPEC;
				}
			} else {
				cs = fl.getRowSpec(i + 1);
				if(cs == null)
					cs = FormFactory.DEFAULT_ROWSPEC;
			}
			String tmp = "";
			txt[0] = "" + (i+1);
			txt[1] = cs.getDefaultAlignment().toString();
			String size = cs.getSize().toString();
			//            System.out.println("GOT SIZE "+size);
			if (size.startsWith("max(") || size.startsWith("min")) {
				txt[4] = size.substring(0, 3);
				size = size.substring(4);
				int pos = size.indexOf(";");
				if (size.startsWith("p"))
					txt[5] = "pref";
				else if (size.startsWith("m"))
					txt[5] = "min";
				else if (size.startsWith("d"))
					txt[5] = "default";
				size = size.substring(pos + 1);
				size = JiglooUtils.replace(size, ")", "");
			} else {
				txt[4] = "none";
			}
			String sizeNum = extractNum(size);
			txt[2] = sizeNum;
			if (size.indexOf("dlu") >= 0)
				txt[3] = "dlu";
			else if (size.startsWith("p"))
				txt[5] = "pref";
			else if (size.startsWith("m"))
				txt[5] = "min";
			else if (size.startsWith("d"))
				txt[5] = "default";
			else
				txt[3] = size.substring(sizeNum.length());
			double wt = cs.getResizeWeight();
			if (wt == 0) {
				txt[6] = "none";
			} else if (wt == 1) {
				txt[6] = "grow";
			} else {
				txt[6] = "grow(" + wt + ")";
			}
			new TableItem(table1, SWT.NULL).setText(txt);
		}
		updateSpecString();
	}

	private String getSpec() {
		String fullSpec = "";
		for (int i = 0; i < table1.getItemCount(); i++) {
			String spec = "";
			if (i != 0)
				fullSpec += ", ";
			TableItem it = table1.getItem(i);
			String ali = it.getText(COL_ALIGNS);
			if (columns) {
				if (!ali.equals("fill"))
					spec += ali;
			} else {
				if (!ali.equals("center"))
					spec += ali;
			}
			String bound = it.getText(COL_BOUNDS);
			if (bound.equals("none")) {
				String units = it.getText(COL_UNITS);
				if (units.equals("default")) {
					if (spec.equals(""))
						spec = "default";
				} else {
					if (!spec.equals(""))
						spec += ":";
					spec += getSizeSpec(it);
				}
			} else {
				if (!spec.equals(""))
					spec += ":";
				String csize = it.getText(COL_CSIZE);
				if (csize.equals(""))
					csize = "pref";
				spec += bound + "(" + getSizeSpec(it, true) + ";" + csize + ")";
			}
			String resize = it.getText(COL_RESIZE);
			String resizeWt = it.getText(COL_RESIZE_WT);
			if (resizeWt.equals("")) {
				it.setText(COL_RESIZE_WT, "0.0");
				resizeWt = "0.0";
			}
			if (!resize.equals("none")) {
				if (!spec.equals(""))
					spec += ":";
				if (resize.equals("grow"))
					spec += resize;
				else
					spec += "grow(" + resizeWt + ")";
			}
			fullSpec += spec;
		}
		return fullSpec;
	}

	private void table1MouseDown(MouseEvent evt) {
		int x = evt.x;
		int y = evt.y;
		int col = 0;
		for (int i = 0; i < table1.getColumnCount(); i++) {
			x -= table1.getColumn(i).getWidth();
			if (x < 0)
				break;
			col++;
		}
		int row = (y - table1.getHeaderHeight()) / table1.getItemHeight();
		//		System.out.println("table1.mouseDown, row, col=" + row+", "+col);
	
		if (editor != null && !editor.isDisposed()) {
			updateEditorItem();
			editor.dispose();
		}
		if (textEditor != null && !textEditor.isDisposed()) {
			updateEditorItem();
			textEditor.dispose();
		}
		//		if(edItem != null)
		//			edItem.setGrayed(false);
		edItem = table1.getItem(new Point(5, y));
		if (edItem == null)
			return;
		//		edItem.setGrayed(true);
		edCol = col;
		String edTxt = edItem.getText(col);
		if (col == COL_SIZE || col == COL_RESIZE_WT) {
			textEditor = createText(row, col);
			textEditor.setText(edTxt);
			textEditor.selectAll();
			editor = null;
			return;
		} else {
			editor = createCombo(row, col);
			textEditor = null;
		}
		if (editor == null)
			return;
		if (col == COL_ALIGNS) {
			if (columns)
				editor.setItems(calis);
			else
				editor.setItems(ralis);
		} else if (col == COL_UNITS)
			editor.setItems(units);
		else if (col == COL_BOUNDS)
			editor.setItems(bounds);
		else if (col == COL_CSIZE)
			editor.setItems(csize);
		else if (col == COL_RESIZE)
			editor.setItems(resizes);
		int sel = 0;
		for (int i = 0; i < editor.getItems().length; i++) {
			String it = editor.getItems()[i];
			if (it.equals(edTxt)) {
				sel = i;
				break;
			}
		}
		editor.select(sel);
		updateSpecString();
	}

	private void updateSpecString() {
		String spec = getSpec();
		text1.setText(spec);
		try {
			if (columns)
				new FormLayout(spec, "p");
			else
				new FormLayout("p", spec);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected void updateEditorItem() {
		if (editor != null) {
			int sel = editor.getSelectionIndex();
			if (sel != -1 && edItem != null)
				edItem.setText(edCol, editor.getItem(sel));
		} else {
			edItem.setText(edCol, textEditor.getText());
		}
	}

	FormLayout formLayout = null;
	
	public void setFormLayout(FormLayout lm, boolean cols) {
		formLayout = lm;
		columns = cols;
	}
	
	private String specString= null;
	
	public String getFormSpecString() {
		return specString;
	}

	private void okButtonWidgetSelected(SelectionEvent evt) {
		specString = getSpec();
//		specString = text1.getText();
		dialogShell.dispose();
	}
	
	private void cancelButtonWidgetSelected(SelectionEvent evt) {
		dialogShell.dispose();
	}

}
