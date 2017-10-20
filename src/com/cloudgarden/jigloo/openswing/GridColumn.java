/*
 * Created on Nov 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.openswing;

import java.util.Vector;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.search.SearchUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GridColumn {

    private static Vector columnTypes;
    
    private String columnName;
    private String columnType;
    private int order;
    private boolean createColumn;
    private boolean selectable;
    private boolean sortable;
    private boolean filterable;
    private boolean visible;
    private FormComponent comp;
    private FormEditor editor;
    private Class returnType;
    private String[] colTypes;
    
    /**
     * @param string
     */
    public GridColumn(String colName, FormEditor editor) {
        columnName = colName;
        this.editor = editor;
        order = -1;
    }
    
    public String getColumnName() {
        return columnName;
    }
    public boolean isFilterable() {
        return filterable;
    }
    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }
    public boolean isSelectable() {
        return selectable;
    }
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
    public boolean isSortable() {
        return sortable;
    }
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FormComponent getComp() {
        return comp;
    }
    public void setComp(FormComponent comp) {
        this.comp = comp;
    }
    public boolean getCreate() {
        return createColumn;
    }
    public void setCreate(boolean createColumn) {
        this.createColumn = createColumn;
    }

    public String[] getColumnTypes() {
        return colTypes;
    }

    public void setReturnType(Class returnType) {
        if(columnTypes == null) {
            columnTypes = new Vector();
//            columnTypes.add("ButtonColumn"); //any
//            columnTypes.add("CheckBoxColumn"); //
//            columnTypes.add("CodLookupColumn");
//            columnTypes.add("ComboColumn");
//            columnTypes.add("ComboVOColumn");
//            columnTypes.add("CurrencyColumn");
//            columnTypes.add("DateColumn");
//            columnTypes.add("DateTimeColumn");
//            columnTypes.add("DecimalColumn");
//            columnTypes.add("PercentageColumn");
//            columnTypes.add("FormattedTextColumn");
//            columnTypes.add("ImageColumn");
//            columnTypes.add("IntegerColumn");
//            columnTypes.add("MultiLineTextColumn");
//            columnTypes.add("ProgressBarColumn");
//            columnTypes.add("MultipleTypeColumn");
//            columnTypes.add("TextColumn");
//            columnTypes.add("TimeColumn");
//            Collections.sort(columnTypes);
             try {
                SearchUtils.findImplementors("org.openswing.swing.table.columns.client.Column", 
                        editor.getJavaProject(), columnTypes);
            } catch (JavaModelException e) {
                e.printStackTrace();
            }
       }
        Vector tmpCols = new Vector();
        for(int i=0; i<columnTypes.size(); i++) {
            String clsName = ((IType) columnTypes.elementAt(i)).getElementName();
            if(ColumnDecider.isCompatible(clsName, returnType))
                tmpCols.add(clsName);
        }
        colTypes = new String[tmpCols.size()];
        for (int i = 0; i < colTypes.length; i++) {
            colTypes[i] = (String)tmpCols.elementAt(i);
        }
        if(columnType == null && colTypes.length > 0)
            columnType = colTypes[0];
        this.returnType = returnType;
    }
    
    public Class getReturnType() {
        return returnType;
    }
    
    public String getColumnType() {
        return columnType;
    }
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
