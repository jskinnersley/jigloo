package com.cloudgarden.jigloo.properties;
/*

import java.text.MessageFormat;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.jiglooPlugin;

public class FormComboBoxCellEditor extends CellEditor {

	private String[] items;
	long lastFocus = -1;
	private Combo comboBox = null;

	private int selection;

	public FormComboBoxCellEditor() {
		setStyle(SWT.FLAT);
	}

	public FormComboBoxCellEditor(Composite parent, String[] items) {
		this(parent, items, SWT.FLAT);
	}

	public FormComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, style);
		setItems(items);
	}

	public void setFocus() {
		System.out.println("setFocus ");
		if (!jiglooPlugin.isVersion3() && jiglooPlugin.isLinux())
			return;
		long now = System.currentTimeMillis();
		//if(now-lastFocus < 100) return;
		lastFocus = now;
		super.setFocus();
	}

	protected void focusLost() {
		System.out.println("focusLost");
		long now = System.currentTimeMillis();
		//if(now-lastFocus < 100) return;
		lastFocus = now;
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	public void activate() {
		System.out.println("activate");
		super.activate();
	}

	public void deactivate() {
		System.out.println("deactivate");
		super.deactivate();
		focusLost();
		getControl().getParent().setFocus();
	}

	protected void valueChanged(boolean oldValidState, boolean newValidState) {
		System.out.println("valueChanged " + oldValidState + ", " + newValidState);
		super.valueChanged(oldValidState, newValidState);
	}

	protected Control createControl(Composite parent) {

		comboBox = new Combo(parent, getStyle());
		comboBox.setFont(parent.getFont());

		comboBox.addKeyListener(new KeyAdapter() {
			// hook key pressed - see PR 14201  
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});

		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}
		});

		comboBox.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});

		comboBox.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				FormComboBoxCellEditor.this.focusLost();
			}
		});
		populateComboBoxItems();
		return comboBox;
	}

	protected Object doGetValue() {
		return new Integer(selection);
	}

	protected void doSetFocus() {
		if(comboBox != null)
			comboBox.setFocus();
	}

	protected void doSetValue(Object value) {
		selection = ((Integer) value).intValue();
		if (comboBox != null)
			comboBox.select(selection);
	}

	public String[] getItems() {
		return items;
	}

	public LayoutData getLayoutData() {
		LayoutData layoutData = super.getLayoutData();
		if ((comboBox == null) || comboBox.isDisposed())
			layoutData.minimumWidth = 60;
		else {
			// make the comboBox 10 characters wide
			GC gc = new GC(comboBox);
			layoutData.minimumWidth = (gc.getFontMetrics().getAverageCharWidth() * 10) + 10;
			gc.dispose();
		}
		return layoutData;
	}

	public void setItems(String[] items) {
		this.items = items;
		populateComboBoxItems();
	}

	private void populateComboBoxItems() {
		if (comboBox != null && items != null) {
			comboBox.removeAll();
			for (int i = 0; i < items.length; i++)
				comboBox.add(items[i], i);

			setValueValid(true);
			if (selection >= items.length)
				selection = 0;
			comboBox.select(selection);
		}
	}
	
	private void applyEditorValueAndDeactivate() {
		//	must set the selection before getting value
		if(comboBox != null)
			selection = comboBox.getSelectionIndex();
		Object newValue = doGetValue();
		markDirty();
		boolean isValid = isCorrect(newValue);
		setValueValid(isValid);
		if (!isValid) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { items[selection] }));
		}
		fireApplyEditorValue();
		deactivate();
	}

	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == '\u001b') { // Escape character
			fireCancelEditor();
		} else if (keyEvent.character == '\t') { // tab key
			applyEditorValueAndDeactivate();
		}
	}
}

*/

import java.text.MessageFormat;

import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A cell editor that presents a list of items in a combo box.
 * The cell editor's value is the zero-based index of the selected
 * item.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class FormComboBoxCellEditor extends CellEditor {

	/**
	 * The list of items to present in the combo box.
	 */
	private String[] items;

	/**
	 * The zero-based index of the selected item.
	 */
	private int selection;

	/**
	 * The custom combo box control.
	 */

	/**
	 * Default ComboBoxCellEditor style
	 */
	private static final int defaultStyle = SWT.FLAT;

	/**
	 * Creates a new cell editor with no control and no  st of choices. Initially,
	 * the cell editor has no cell validator.
	 * 
	 * @since 2.1
	 * @see #setStyle
	 * @see #create
	 * @see #setItems
	 * @see #dispose
	 */
	public FormComboBoxCellEditor() {
		setStyle(defaultStyle);
	}

	/**
	 * Creates a new cell editor with a combo containing the given 
	 * list of choices and parented under the given control. The cell
	 * editor value is the zero-based index of the selected item.
	 * Initially, the cell editor has no cell validator and
	 * the first item in the list is selected. 
	 *
	 * @param parent the parent control
	 * @param items the list of strings for the combo box
	 */
	public FormComboBoxCellEditor(Composite parent, String[] items) {
		this(parent, items, defaultStyle);
	}

	/**
	 * Creates a new cell editor with a combo containing the given 
	 * list of choices and parented under the given control. The cell
	 * editor value is the zero-based index of the selected item.
	 * Initially, the cell editor has no cell validator and
	 * the first item in the list is selected. 
	 *
	 * @param parent the parent control
	 * @param items the list of strings for the combo box
	 * @param style the style bits
	 * @since 2.1
	 */
	public FormComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, style);
		setItems(items);
	}

	/**
	 * Returns the list of choices for the combo box
	 *
	 * @return the list of choices for the combo box
	 */
	public String[] getItems() {
		return this.items;
	}

	/**
	 * Sets the list of choices for the combo box
	 *
	 * @param items the list of choices for the combo box
	 */
	public void setItems(String[] items) {
		Assert.isNotNull(items);
		this.items = items;
		populateComboBoxItems();
	}

	boolean keyPressed = false;

	private CCombo comboBox;

	protected Control createControl(Composite parent) {
		comboBox = new CCombo(parent, getStyle());
		try {
		    comboBox.setVisibleItemCount(10);
		} catch(Throwable t) {}
		comboBox.setFont(parent.getFont());

		comboBox.addKeyListener(new KeyAdapter() {
			// hook key pressed - see PR 14201  
			public void keyPressed(KeyEvent e) {
				keyPressed = true;
				keyReleaseOccured(e);
			}
		});

		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//System.out.println("SELECTED " + event.detail + ", " + event.doit);

				//this is the bit that for Eclipse 3.0 deactivates the control
				//if the mouse was clicked to select an item
				if (!keyPressed)
					applyEditorValueAndDeactivate();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}
		});

		comboBox.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				keyPressed = true;
				if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});

		comboBox.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				FormComboBoxCellEditor.this.focusLost();
			}
		});
		return comboBox;
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of
	 * this <code>CellEditor</code> framework method returns
	 * the zero-based index of the current selection.
	 *
	 * @return the zero-based index of the current selection wrapped
	 *  as an <code>Integer</code>
	 */
	protected Object doGetValue() {
		return new Integer(selection);
	}

	/* (non-Javadoc)
	 * Method declared on CellEditor.
	 */
	protected void doSetFocus() {
		comboBox.setFocus();
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of
	 * this <code>CellEditor</code> framework method sets the 
	 * minimum width of the cell.  The minimum width is 10 characters
	 * if <code>comboBox</code> is not <code>null</code> or <code>disposed</code>
	 * eles it is 60 pixels to make sure the arrow button and some text is visible.
	 * The list of CCombo will be wide enough to show its longest item.
	 */
	public LayoutData getLayoutData() {
		LayoutData layoutData = super.getLayoutData();
		if ((comboBox == null) || comboBox.isDisposed())
			layoutData.minimumWidth = 60;
		else {
			// make the comboBox 10 characters wide
			GC gc = new GC(comboBox);
			layoutData.minimumWidth = (gc.getFontMetrics().getAverageCharWidth() * 10) + 10;
			gc.dispose();
		}
		return layoutData;
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of
	 * this <code>CellEditor</code> framework method
	 * accepts a zero-based index of a selection.
	 *
	 * @param value the zero-based index of the selection wrapped
	 *   as an <code>Integer</code>
	 */
	protected void doSetValue(Object value) {
		selection = ((Integer) value).intValue();
		comboBox.select(selection);
		keyPressed = false;
	}

	/**
	 * Updates the list of choices for the combo box for the current control.
	 */
	private void populateComboBoxItems() {
		if (comboBox != null && items != null) {
			comboBox.removeAll();
			for (int i = 0; i < items.length; i++)
				comboBox.add(items[i], i);

			setValueValid(true);
			selection = 0;
		}
	}
	/**
	 * Applies the currently selected value and deactiavates the cell editor
	 */
	private void applyEditorValueAndDeactivate() {
		//	must set the selection before getting value
	    int sel = comboBox.getSelectionIndex();
	    String txt = comboBox.getText();
        String[] its = comboBox.getItems();
	    if(sel < 0) {
	        for (int i = 0; i < its.length; i++) {
                if(its[i].equals(txt)) {
                    sel = i;
                    break;
                }
            }
	    }
	    if(sel < 0) {
	        for (int i = 0; i < its.length; i++) {
                if(its[i].equalsIgnoreCase(txt)) {
                    sel = i;
                    break;
                }
            }	        
	    }
//	    System.out.println("GOT COMBO TEXT "+txt+", "+sel);
		selection = sel;
		Object newValue = doGetValue();
		markDirty();
		boolean isValid = isCorrect(newValue);
		setValueValid(isValid);
		if (!isValid) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { items[selection] }));
		}
		fireApplyEditorValue();
		deactivate();
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#focusLost()
	 */
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
	 */
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == '\u001b') { // Escape character
			fireCancelEditor();
		} else if (keyEvent.character == '\t') { // tab key
			applyEditorValueAndDeactivate();
		}
	}

}
