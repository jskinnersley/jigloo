/*
 * Created on Nov 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.openswing;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ColumnTypeCellEditor extends ComboBoxCellEditor {

    public ColumnTypeCellEditor(Composite parent) {
        super(parent, new String[] {}, SWT.NONE);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ComboBoxCellEditor#getItems()
     */
    public String[] getItems() {
        try {
            ((CCombo)getControl()).setVisibleItemCount(10);
        } catch(Throwable t) {}
        GridColumn gc = (GridColumn) ((Table)getControl().getParent()).getSelection()[0].getData();
        setItems(gc.getColumnTypes());
        return gc.getColumnTypes();
    }
    
    /**
     * @param integer
     * @return
     */
    public String convert(Integer index) {
        int ind = index.intValue();
        if(ind < 0)
            ind = 0;
        return getItems()[ind];
    }

    /**
     * @param string
     * @return
     */
    public Integer convert(String value) {
        String[] items = getItems();
        if(value == null)
            return new Integer(0);
        for (int i = 0; i < items.length; i++) {
            if(value.equals(items[i]))
                return new Integer(i);
        }
        return new Integer(0);
    }
}
