package com.cloudgarden.jigloo.openswing;

import java.awt.Container;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.search.SearchUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.resource.SWTResourceManagerInternal;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class GridControlEditor extends org.eclipse.swt.widgets.Composite {

    private String[] columns = new String[] {
            "columnName", "create",
            "columnType", "order", 
            "visible", "sortable",
            "filterable", "selectable" };
    
    private int[] colWidths = new int[] {
            100, 60, 
            120, 60, 
            60,  60, 
            60, 60};

    private Class[] editorClasses = new Class[] {
            null, CheckboxCellEditor.class, 
            ColumnTypeCellEditor.class, TextCellEditor.class, 
            CheckboxCellEditor.class, CheckboxCellEditor.class, 
            CheckboxCellEditor.class, CheckboxCellEditor.class};

    private CellEditor[] editors;
    
    private Class[] columnTypes = new Class[columns.length];

    private Method[] getMethods = new Method[columns.length];
    private Method[] setMethods = new Method[columns.length];
    
    private String className;
    private boolean isDirty = false;
    
    private Comparator orderComparator = new Comparator() {
        public int compare(Object e1, Object e2) {
            int o1 = ((GridColumn)e1).getOrder();
            if(o1 == -1)
                return 1;
            int o2 = ((GridColumn)e2).getOrder();
            if(o2 == -1)
                return -1;
            return o1 - o2;
        }
        
    };
    
    class LabelProvider implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            try {
                if (columnTypes[columnIndex].equals(boolean.class)) {
                    Object val = getMethods[columnIndex].invoke(element, null);
                    if (val.equals(Boolean.TRUE))
                        return ImageManager.getCheckBoxImage(true);
                    return ImageManager.getCheckBoxImage(false);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            try {
                if (columnTypes[columnIndex].equals(String.class)) {
                    Object val = getMethods[columnIndex].invoke(element, null);
                    if(val == null)
                        return "";
                    return val.toString();
                }
                if (columnTypes[columnIndex].equals(int.class)) {
                    Object val = getMethods[columnIndex].invoke(element, null);
                    return val.toString();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return "";
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return true;
        }

        public void removeListener(ILabelProviderListener listener) {
        }
    }

    class ContentProvider implements IStructuredContentProvider {
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }

        public void dispose() {
        }

        // Return the tasks as an array of Objects
        public Object[] getElements(Object parent) {
            return (GridColumn[]) parent;
        }
    }

    private FormComponent gridControl;

    {
        //Register as a resource user - SWTResourceManager will
        //handle the obtaining and disposing of resources
        SWTResourceManagerInternal.registerResourceUser(this);
    }

    private TableViewer tableViewer1;

    private CLabel cLabel1;

    private CCombo classCombo;
    private Button applyButton;

    private Composite composite2;

    private Composite composite1;

    private Button cancelButton;

    private Button okButton;

    private GridColumn[] gridColumns;

    /**
     * Auto-generated main method to display this
     * org.eclipse.swt.widgets.Composite inside a new Shell.
     */
    public static void main(String[] args) {
        showGUI();
    }

    /**
     * Auto-generated method to display this org.eclipse.swt.widgets.Composite
     * inside a new Shell.
     */
    public static void showGUI() {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        GridControlEditor inst = new GridControlEditor(shell, null);
        Point size = inst.getSize();
        shell.setLayout(new FillLayout());
        shell.layout();
        if (size.x == 0 && size.y == 0) {
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

    public GridControlEditor(org.eclipse.swt.widgets.Composite parent,
            FormComponent grid) {
        super(parent, SWT.NONE);
        this.gridControl = grid;
        initGUI();
    }

    private void initGUI() {
        try {
            GridLayout thisLayout = new GridLayout();
            thisLayout.makeColumnsEqualWidth = true;
            this.setLayout(thisLayout);
            {
                composite2 = new Composite(this, SWT.NONE);
                GridLayout composite2Layout = new GridLayout();
                composite2Layout.numColumns = 2;
                composite2Layout.horizontalSpacing = 0;
                composite2Layout.marginWidth = 0;
                GridData composite2LData = new GridData();
                composite2LData.horizontalAlignment = GridData.FILL;
                composite2LData.grabExcessHorizontalSpace = true;
                composite2.setLayoutData(composite2LData);
                composite2.setLayout(composite2Layout);
                {
                    cLabel1 = new CLabel(composite2, SWT.NONE);
                    cLabel1.setText("ValueObject class: ");
                    GridData cLabel1LData = new GridData();
                    cLabel1.setLayoutData(cLabel1LData);
                }
                {
                    classCombo = new CCombo(composite2, SWT.BORDER);
                    GridData cCombo1LData = new GridData();
                    cCombo1LData.horizontalAlignment = GridData.FILL;
                    cCombo1LData.grabExcessHorizontalSpace = true;
                    classCombo.setLayoutData(cCombo1LData);
                }
            }
            {
                GridData tableViewer1LData = new GridData();
                tableViewer1LData.grabExcessHorizontalSpace = true;
                tableViewer1LData.horizontalAlignment = GridData.FILL;
                tableViewer1LData.verticalAlignment = GridData.FILL;
                tableViewer1LData.grabExcessVerticalSpace = true;
        		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
				SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
                Table table = new Table(this, style);
                tableViewer1 = new TableViewer(table);
                table.setLayoutData(tableViewer1LData);
                table.setHeaderVisible(true);
                table.setLinesVisible(true);
                for (int i = 0; i < columns.length; i++) {
                    String col = columns[i];
                    TableColumn tc = new TableColumn(table, SWT.NONE);
                    tc.setText(col);
                    tc.setWidth(colWidths[i]);
                }
                classCombo.setText("...searching for ValueObject classes...");
                classCombo.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        String className = classCombo.getText();
                        setClassName(className);
                    }

                });

            }
            {
                composite1 = new Composite(this, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.makeColumnsEqualWidth = true;
                composite1Layout.numColumns = 3;
                composite1Layout.horizontalSpacing = 15;
                GridData composite1LData = new GridData();
                composite1LData.horizontalAlignment = GridData.END;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    applyButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    applyButton.setText("Apply");
                    GridData button1LData = new GridData();
                    button1LData.horizontalAlignment = GridData.FILL;
                    button1LData.grabExcessHorizontalSpace = true;
                    applyButton.setLayoutData(button1LData);
                    applyButton.setEnabled(false);
                    applyButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            doApply();
                        }
                    });
                }
                {
                    okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    okButton.setText("OK");
                    GridData okButtonLData = new GridData();
                    okButtonLData.grabExcessHorizontalSpace = true;
                    okButtonLData.horizontalAlignment = GridData.FILL;
                    okButton.setLayoutData(okButtonLData);
                    okButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            doOK();
                        }
                    });
                }
                {
                    cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    cancelButton.setText("Cancel");
                    GridData cancelButtonLData = new GridData();
                    cancelButtonLData.grabExcessHorizontalSpace = true;
                    cancelButtonLData.horizontalAlignment = GridData.FILL;
                    cancelButton.setLayoutData(cancelButtonLData);
                    cancelButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            doCancel();
                        }
                    });
                }
            }

            initViewer();

            this.layout();
            this.setSize(631, 360);

            setClassName((String) gridControl
                    .getRawPropertyValue("valueObjectClassName"));
            
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    try {
                        Vector classes = new Vector();
                        SearchUtils
                                .findImplementors(
                                        "org.openswing.swing.message.receive.java.ValueObject",
                                        gridControl.getEditor()
                                                .getJavaProject(), classes);
                        String[] names = new String[classes.size()];
                        for (int i = 0; i < names.length; i++) {
                            names[i] = ((IType) classes.elementAt(i))
                                    .getFullyQualifiedName();
                        }
                        Arrays.sort(names);
                        classCombo.setItems(names);
                        classCombo.select(classCombo.indexOf((String) gridControl
                                .getRawPropertyValue("valueObjectClassName")));

                    } catch (JavaModelException e) {
                        e.printStackTrace();
                    }
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doApply() {
        String className = classCombo.getText();
        if(!className.equals(gridControl.getRawPropertyValue("valueObjectClassName"))) {
            gridControl.setPropertyValueInternal("valueObjectClassName", className, true, true);
            gridControl.updateInCode("valueObjectClassName");
            isDirty = true;
        }
        if(!isDirty)
            return;
        try {
            isDirty = false;
            applyButton.setEnabled(false);
            FormComponent colCont = getColumnContainer();
            
            Arrays.sort(gridColumns, orderComparator);
            
            Vector fcs = (Vector) getColumnFCs().clone();
            for(int i=0; i<fcs.size(); i++) {
                FormComponent fc = (FormComponent) fcs.elementAt(i);
                if(!doesFCHaveColumn(fc, gridColumns)) {
                    fc.getEditor().removeComponent(fc, true);
                }
            }
            for (int i = 0; i < gridColumns.length; i++) {
                GridColumn gc = gridColumns[i];
                FormComponent fc = getColumnFC(gc.getColumnName());
                if(gc.getCreate()) {
                    className = "org.openswing.swing.table.columns.client."+gc.getColumnType();
                    if(fc == null) {
                        fc = FormComponent.newFormComponent(colCont, className, null, false, null);
                        fc.setExistsInCode(true);
                        fc.setInMainTree(true);
                        fc.addToCode();
                    }
                    if(!className.equals(fc.getClassName())) {
                        fc.setClassName(className);
                        fc.repairInCode(true);
//                        fc.rebuild(true);
                    }
                    
                    fc.dispose();
                    fc.populateComponents((Container) colCont.getComponent(), colCont.getEditor());
                    
                    if(fc.getNonInheritedIndexInParent() != i)
                        fc.moveTo(i);
                    
                    fc.setPropertyValueInternal("columnVisible", new Boolean(gc.isVisible()), true);
                    fc.setPropertyValueInternal("columnSortable", new Boolean(gc.isSortable()), true);
                    fc.setPropertyValueInternal("columnFilterable", new Boolean(gc.isFilterable()), true);
                    fc.setPropertyValueInternal("columnSelectable", new Boolean(gc.isSelectable()), true);
                    fc.setPropertyValueInternal("headerColumnName", "", false);
                    fc.setPropertyValueInternal("columnName", gc.getColumnName(), true);
//                    fc.setPropertyValueInternal("preferredWidth", new Integer(80), true);
                    
                    fc.updateInCode();
                    
                } else {
                    if(fc != null) {
                        fc.getEditor().removeComponent(fc, true);
                    }
                }
            }
            colCont.getEditor().setDirtyAndUpdate();
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    protected void doOK() {
        doApply();
        getShell().dispose();
    }

    protected void doCancel() {
        getShell().dispose();
    }

    /**
     *  
     */
    private void initViewer() {
        Table table = tableViewer1.getTable();
        for (int i = 0; i < columns.length; i++) {
            String propMethName = JiglooUtils.capitalize(columns[i]);
            try {
                Method m = GridColumn.class.getMethod("get" + propMethName, null);
                columnTypes[i] = m.getReturnType();
                getMethods[i] = m;
            } catch (Throwable e) {
                try {
                    Method m = GridColumn.class.getMethod("is" + propMethName, null);
                    columnTypes[i] = m.getReturnType();
                    getMethods[i] = m;
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
            try {
                Method[] ms = GridColumn.class.getMethods();
                for (int j = 0; j < ms.length; j++) {
                    Method m = ms[j];
                    if(m.getName().equals("set"+propMethName))
                        setMethods[i] = m;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        editors = new CellEditor[editorClasses.length];
        for (int i = 0; i < editors.length; i++) {
            if(editorClasses[i] != null) {
                try {
                    if(editorClasses[i].equals(ColumnTypeCellEditor.class))
                        editors[i] = new ColumnTypeCellEditor(table);
                    else
                        editors[i] = (CellEditor) editorClasses[i].getConstructor(
                                new Class[] {Composite.class}).newInstance(new Object[] {table});
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        tableViewer1.setCellEditors(editors);
        tableViewer1.setContentProvider(new ContentProvider());
        tableViewer1.setLabelProvider(new LabelProvider());
    }

    private int getPropIndex(String prop) {
        for(int i=0; i<columns.length; i++)
            if(columns[i].equals(prop))
                return i;
            return -1;
    }
    
    private void setClassName(String className) {
        try {
            if(this.className != null && this.className.equals(className))
                return;
            this.className = className;
            classCombo.setText(className);
            Class cls = gridControl.getEditor().loadClass(className);
            Vector propNames = PropertySourceFactory.findPropertyNames(cls);
            tableViewer1.setColumnProperties(columns);
            gridColumns = new GridColumn[propNames.size()];
            for (int i = 0; i < gridColumns.length; i++) {
                String prop = (String) propNames.elementAt(i);
                Class propType = PropertySourceFactory.getGetter(cls, prop).getReturnType();
                GridColumn gc = new GridColumn(prop, gridControl.getEditor());
                gridColumns[i] = gc;
                FormComponent fc = getColumnFC(prop);
                if(fc != null) {
                    gc.setComp(fc);
                    gc.setCreate(true);
                    gc.setFilterable(fc.getBooleanPropertyValue("columnFilterable"));
                    gc.setSelectable(fc.getBooleanPropertyValue("columnSelectable"));
                    gc.setVisible(fc.getBooleanPropertyValue("columnVisible"));
                    gc.setSortable(fc.getBooleanPropertyValue("columnSortable"));
                    gc.setOrder(fc.getNonInheritedIndexInParent());
                    gc.setColumnType(JiglooUtils.getUnqualifiedName(fc.getClassName()));
                }
                gc.setReturnType(propType);
            }
            tableViewer1.setInput(gridColumns);
            tableViewer1.setCellModifier(new ICellModifier() {

                public boolean canModify(Object element, String property) {
                    if(property.equals("columnName"))
                        return false;
                    return true;
                }

                public Object getValue(Object element, String property) {
                    GridColumn gc = (GridColumn)element;
                    int ind = getPropIndex(property);
                    Method m = getMethods[ind];
                    try {
                        Object val = m.invoke(gc, null);
                        if(property.equals("columnType"))
                            return ((ColumnTypeCellEditor)editors[ind]).convert((String)val);
                        if(columnTypes[ind].equals(int.class))
                            return val.toString();
                        return val;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                public void modify(Object element, String property, Object value) {
                    isDirty = true;
                    applyButton.setEnabled(true);
                    GridColumn gc = (GridColumn) ((TableItem)element).getData();
                    if(property.equals("create")) {
                        if(!gc.getCreate())
                            gc.setVisible(true);
                    }
                    if(property.equals("order")) {
                        int newOrder = (value instanceof Integer) ? ((Integer)value).intValue() : Integer.parseInt((String)value);;
                        int oldOrder = gc.getOrder();
                        for (int i = 0; i < gridColumns.length; i++) {
                            GridColumn gci = gridColumns[i];
                            int gco = gci.getOrder();
                            if(gco == oldOrder)
                                continue;
                            if((gco-oldOrder)*(gco-newOrder) <= 0) {
                                if(oldOrder < newOrder)
                                    gci.setOrder(gco-1);
                                else
                                    gci.setOrder(gco+1);
                            }
                        }
                        gc.setOrder(newOrder);
                        tableViewer1.refresh();
                    } else {
                        int ind = getPropIndex(property);
                        Method m = setMethods[ind];
                        try {
                            if(property.equals("order"))
                                m.invoke(gc, new Object[] {new Integer((String)value)});
                            else if(editors[ind] instanceof ColumnTypeCellEditor) {
                                m.invoke(gc, new Object[] {
                                        ((ColumnTypeCellEditor)editors[ind]).convert((Integer)value)});
                            } else
                                m.invoke(gc, new Object[] {value});
                            tableViewer1.update(gc, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
            });
            
            ViewerSorter orderSorter = new ViewerSorter() {
                public int compare(Viewer viewer, Object e1, Object e2) {
                    return orderComparator.compare(e1, e2);
                }
            };
            
            tableViewer1.setSorter(orderSorter);
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    private boolean doesFCHaveColumn(FormComponent fc, GridColumn[] gcs) {
        String fcCol = (String) fc.getRawPropertyValue("columnName");
        for (int i = 0; i < gcs.length; i++) {
            if(fcCol.equals(gcs[i].getColumnName()))
                return true;
        }
        return false;
    }
    
    private FormComponent getColumnFC(String prop) {
        Vector colFCs = getColumnFCs();
        for(int j=0;j<colFCs.size(); j++) {
            FormComponent fc = (FormComponent) colFCs.elementAt(j);
            if(prop.equals(fc.getRawPropertyValue("columnName"))) {
                return fc;
            }
        }
        return null;
    }

    private Vector getColumnFCs() {
        FormComponent cont = getColumnContainer();
        return cont.getChildren();
    }

    private FormComponent getColumnContainer() {
        return gridControl.getChildByName(gridControl.getName()+".columnContainer");
    }
    
}