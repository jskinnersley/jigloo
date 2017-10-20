package com.cloudgarden.jigloo.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.dialog.EditValueDialog;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.FontManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CGPropertySheetPage extends PropertySheetPage {

    public static int tableItemHeight = 16;

    private boolean advanced = false;
    private boolean isPropsPage = false;
    private boolean disposed = false;
	private IWorkbenchPart mySourcePart;

    private String title;

    private FormComponent selFC;

    private Item selectedItem;

    private TableTree ttree = null;

    private Tree tree = null;

    public static boolean rightButtonDown = false;
    
    private static Vector pages = new Vector();

    /**
     *  
     */
    public CGPropertySheetPage(String title, final Composite parent,
            final IWorkbenchSite site, boolean advanced,
            boolean showPropertiesMenus) {

        super();
        disposed = false;
        pages.add(this);
        
        this.isPropsPage = showPropertiesMenus;
        
        createControl(parent);
        MouseListener mList = new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
            	if(selFC != null && selFC.getEditor() != null)
            		selFC.getEditor().checkParsing();

            	if (e.button == 3)
                    rightButtonDown = true;
                else
                    rightButtonDown = false;

                if (ttree != null) {
                    selectedItem = ttree.getItem(new Point(e.x, e.y));
                } else if (tree != null) {
                    selectedItem = tree.getItem(new Point(e.x, e.y));
                }
            }
        };
        if (getControl() instanceof Tree) {
            tree = (Tree) getControl();
            tree.addMouseListener(mList);
        } else {
            ttree = (TableTree) getControl();
            ttree.getTable().addMouseListener(mList);
        }
        this.advanced = advanced;
        this.title = title;
        ControlAdapter cad = new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                try {
                    if (ttree != null) {
                        tableItemHeight = ttree.getItemHeight();
                        TableColumn[] cols = ttree.getTable().getColumns();
                        int width = ((TableTree) e.widget).getSize().x;
                        cols[0].setWidth(width / 2);
                        cols[1].setWidth(width / 2);
                    } else if (tree != null) {
                        tableItemHeight = tree.getItemHeight();
                        Object[] cols = (Object[]) JiglooUtils.invoke(tree,
                                "getColumns", null);
                        int width = ((Tree) e.widget).getSize().x + 5;
                        // System.out.println("CONTROL RESIZED "+
                        // CGPropertySheetPage.this.title+", width="+width+"
                        // cols="+cols);
                        if (cols != null && cols.length == 2) {
                            JiglooUtils.invoke(cols[0], "setWidth",
                                    new Object[] { new Integer(width / 2) });
                            JiglooUtils.invoke(cols[1], "setWidth",
                                    new Object[] { new Integer(width / 2) });
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        };

        TreeListener treeList =  null;
        if(isPropsPage) {
            treeList = new TreeListener() {
                public void treeExpanded(TreeEvent e) {
                    Item it = (Item) e.item;
                    if (isCategoryItem(it)) {
                        Vector exp = jiglooPlugin.getPropertyCategoriesExpanded();
                        String cat = it.getText();
                        if (!exp.contains(cat))
                            exp.add(cat);
                        jiglooPlugin.setPropertyCategoriesExpanded(exp);
                        fireTreeChanged();
                    }
                }
                
                public void treeCollapsed(TreeEvent e) {
                    Item it = (Item) e.item;
                    if (isCategoryItem(it)) {
                        Vector exp = jiglooPlugin.getPropertyCategoriesExpanded();
                        String cat = it.getText();
                        exp.remove(cat);
                        jiglooPlugin.setPropertyCategoriesExpanded(exp);
                        fireTreeChanged();
                    }
                }
            };
        }
        
        if (tree != null) {
            try {
                tree.addControlListener(cad);
                if(treeList != null)
                    tree.addTreeListener(treeList);
                tableItemHeight = tree.getItemHeight();
                Object[] cols = (Object[]) JiglooUtils.invoke(tree,
                        "getColumns", null);
                if (cols != null && cols.length > 0) {
                    JiglooUtils.invoke(cols[0], "setText",
                            new Object[] { title });
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else if (ttree != null) {
            tableItemHeight = ttree.getItemHeight();
            TableColumn[] cols = cols = ttree.getTable().getColumns();
            cols[0].setText(title);
            ttree.addControlListener(cad);
            if(treeList != null)
                ttree.addTreeListener(treeList);
        }

        if (isPropsPage)
            addMenuListener(parent);

            initPropSrc();
            
        // makeContributions(new MenuManager(), new ToolBarManager(),
        // new StatusLineManager());
    }

    private static void fireTreeChanged() {
        for(int i=0; i< pages.size(); i++)
            ((CGPropertySheetPage)pages.elementAt(i)).handleTreeChanged();
    }
    
    private void handleTreeChanged() {
        setUpCategories(true);
    }
    
	private void refreshCategories() {
		if(selFC != null) {
			selFC.getEditor().refreshCategories();
			selectionChanged(null, new StructuredSelection( selFC));
		}
	}
	
    private void addMenuListener(final Composite parent) {
        final Menu menu = getControl().getMenu();
        menu.addMenuListener(new MenuAdapter() {

            public void menuShown(MenuEvent e) {
                // System.out.println("MENU SHOWN "+e.widget);
                Control ctrl = getControl();
                String catName = null;
                Item sel = getSelectedItem();
                if (sel == null)
                    return;
                if (isCategoryItem(sel))
                    catName = sel.getText();

                if (menu.getItemCount() == 6 && (false || catName == null)) {
                    menu.getItem(3).dispose();
                    menu.getItem(3).dispose();
                    menu.getItem(3).dispose();
                } else if (menu.getItemCount() == 4
                        && (true || catName != null)) {
                    // because the selected item can change, and the menu
                    // item now lists the property name
                    menu.getItem(3).dispose();
                }

                if (menu.getItemCount() == 3) {
                    if (catName != null) {

                        MenuItem catItem = new MenuItem(menu, SWT.NULL);
                        catItem.setText("Change category name");
                        final String fcn = catName;
                        catItem.addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent arg0) {
                                EditValueDialog evd = new EditValueDialog(parent.getShell(), SWT.NULL);
                                evd.initText(fcn,  "Enter new name of property category");
                                evd.open();
                                String cat = evd.getValue();
                                if (cat != null) {
                                    Vector pcns = jiglooPlugin.getPropertyCategoryNames();
                                    if (fcn.equals(jiglooPlugin.getPropertyExpertCategory()))
                                        jiglooPlugin.setPropertyExpertCategory(cat);
                                    pcns.remove(fcn);
                                    pcns.add(cat);
                                    Collections.sort(pcns);
                                    HashMap pcs = jiglooPlugin.getPropertyCategoryMap();
                                    Iterator it = pcs.keySet().iterator();
                                    while (it.hasNext()) {
                                        Object key = it.next();
                                        if (fcn.equals(pcs.get(key)))
                                            pcs.put(key, cat);
                                    }
                                    refreshCategories();
                                }
                            }
                        });

                        catItem = new MenuItem(menu, SWT.NULL);
                        catItem.setText("Delete category");
                        catItem.addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent arg0) {
                                Vector pcns = jiglooPlugin
                                        .getPropertyCategoryNames();
                                pcns.remove(fcn);
                                HashMap pcs = jiglooPlugin
                                        .getPropertyCategoryMap();
                                Iterator it = pcs.keySet().iterator();
                                Vector rems = new Vector();
                                while (it.hasNext()) {
                                    Object key = it.next();
                                    if (fcn.equals(pcs.get(key)))
                                        rems.add(key);
                                }
                                for (int i = 0; i < rems.size(); i++)
                                    pcs.remove(rems.elementAt(i));
                                refreshCategories();
                            }
                        });

                        catItem = new MenuItem(menu, SWT.NULL);
                        catItem
                                .setText("Restore default categories (for all properties)");
                        catItem.addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent arg0) {
                                jiglooPlugin.restoreDefaultPropCategories();
                                refreshCategories();
                            }
                        });

                    } else {
                        MenuItem setCat = new MenuItem(menu, SWT.CASCADE);
                        setCat.setText("Set property category for "
                                + sel.getText());
                        Menu sm = new Menu(setCat);
                        setCat.setMenu(sm);
                        Vector pcns = jiglooPlugin.getPropertyCategoryNames();
                        for (int i = 0; i < pcns.size(); i++) {
                            MenuItem mi = new MenuItem(sm, SWT.NULL);
                            final String cat = (String) pcns.elementAt(i);
                            mi.setText(cat);
                            mi.addSelectionListener(new SelectionAdapter() {
                                public void widgetSelected(SelectionEvent arg0) {
                                    setPropertyCategory(cat);
                                }
                            });
                        }
                        MenuItem mi = new MenuItem(sm, SWT.NULL);
                        mi.setText("Add to new category...");
                        mi.addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent arg0) {
                                EditValueDialog evd = new EditValueDialog(
                                        parent.getShell(), SWT.NULL);
                                evd.initText("NewCategory",
                                        "Enter name of new property category");
                                evd.open();
                                String cat = evd.getValue();
                                if (cat != null) {
                                    Vector pcns = jiglooPlugin .getPropertyCategoryNames();
                                    if (!pcns.contains(cat)) {
                                        pcns.add(cat);
                                        Collections.sort(pcns);
                                    }
                                    setPropertyCategory(cat);
                                    menu.getItem(3).dispose();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean isCategoryItem(Item it) {
        return it != null
        && !it.isDisposed()
        && it.getData() != null
        && it.getData().getClass().getName().indexOf( "PropertySheetCategory") >= 0;
    }

    private Item getSelectedItem() {
        return selectedItem;
    }

    private void setPropertyCategory(String cat) {
        String prop = null;
        Control ctrl = getControl();
        Item sel = getSelectedItem();
        if (sel == null)
            return;
        prop = sel.getText();
        if (prop.endsWith("*"))
            prop = prop.substring(0, prop.length() - 1);
        jiglooPlugin.getPropertyCategoryMap().put(prop, cat);
        refreshCategories();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.PropertySheetPage#makeContributions(org.eclipse.jface.action.IMenuManager,
     *           org.eclipse.jface.action.IToolBarManager,
     *           org.eclipse.jface.action.IStatusLineManager)
     */
    public void makeContributions(IMenuManager menuManager,
            IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
        super.makeContributions(menuManager, toolBarManager, statusLineManager);
    }

    public void dispose() {
    	super.dispose();
    	try {
    		
    		Field field;
    		
    		field = PropertySheetPage.class.getDeclaredField("rootEntry");
    		field.setAccessible(true);
    		Object rootEntry = field.get(this);
    		if(rootEntry != null) {
    			Method meth = rootEntry.getClass().getMethod("setPropertySourceProvider", 
    					new Class[] {IPropertySourceProvider.class});
    			meth.invoke(rootEntry, new Object[] {null});
    			field.set(this, null);
    		}

    		field = PropertySheetPage.class.getDeclaredField("columnsAction");
    		field.setAccessible(true);
    		field.set(this, null);
    		
    		field = PropertySheetPage.class.getDeclaredField("partListener");
    		field.setAccessible(true);
    		try {
    			if(mySourcePart != null) {
    				Object listener =  field.get(this);
    				if(listener != null) {
    					mySourcePart.getSite().getPage().removePartListener((IPartListener)listener);
    				}
    			}
    		} catch(Throwable t) {
    			t.printStackTrace();
    		}
//    		field.set(this, null);
    		
    		field = PropertySheetPage.class.getDeclaredField("provider");
    		field.setAccessible(true);
    		field.set(this, null);
    		
    		field = PropertySheetPage.class.getDeclaredField("cellEditorActivationListener");
    		field.setAccessible(true);
    		field.set(this, null);

    		Field viewerField = PropertySheetPage.class.getDeclaredField("viewer");
    		viewerField.setAccessible(true);
    		Object viewer = viewerField.get(this);
    		 
    		if(viewer != null) {
	    		Field listField = viewer.getClass().getSuperclass().getDeclaredField("selectionChangedListeners");
	    		listField.setAccessible(true);
	    		Object list = listField.get(viewer);
	    		list.getClass().getMethod("clear", null).invoke(list, null);
	    		
	    		listField = viewer.getClass().getDeclaredField("activationListeners");
	    		listField.setAccessible(true);
	    		list = listField.get(viewer);
	    		list.getClass().getMethod("clear", null).invoke(list, null);
    		}
    		
    		viewerField.set(this, null);
    		
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    	pages.remove(this);
    	selFC = null;
    	pages.clear();
    	tree = null;
    	ttree = null;
    	selectedItem = null;
    	disposed = true;
    }
    
    protected void initPropSrc() {
        setPropertySourceProvider(new IPropertySourceProvider() {
            public IPropertySource getPropertySource(Object o) {
                // System.out.println("GET PROP SRC "+o.getClass()+", "+o);
                if (o instanceof TreeObject) {
                    TreeObject tob = (TreeObject) o;
                    FormComponent fc = tob.getFormComponent();
                    if (fc != null && !advanced)
                        return fc.getBasicPropertySource();
                    if (fc != null && advanced)
                        return fc.getAdvancedPropertySource();
                } else if (o instanceof FormComponent) {
                    FormComponent fc = (FormComponent) o;
                    if (!advanced)
                        return fc.getBasicPropertySource();
                    else
                        return fc.getAdvancedPropertySource();
                } else if (o instanceof IPropertySource) {
                    return (IPropertySource) o;
                }
                return null;
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
     *           org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {

    	if(getControl() == null || getControl().isDisposed())
    		return;
        boolean fcChanged = false;

        if (selection instanceof StructuredSelection) {
            StructuredSelection sel = (StructuredSelection) selection;
            Object[] obs = sel.toArray();
            if (!jiglooPlugin.getDefault().canUseProfFeature(getSite(),
                    "Multi-select")
                    && obs.length > 1)
                return;
            if (obs.length == 0)
                return;
            
            if (obs[0] instanceof FormComponent) {
                if (selFC == null)
                    fcChanged = true;
                else if (!selFC.equals(obs[0]))
                    fcChanged = true;
                
                selFC = (FormComponent) obs[0];
                if(selFC.isDisposed()) {
                	System.out.println("Disposed FC is selected - rejecting selection");
                	return;
                }
            }

            if (obs[0] instanceof File)
                return;
        }
        try {
            super.selectionChanged(part, selection);
        } catch (Exception e) {
            System.err.println("CGPropertySheetPage error " + title+"@"+hashCode()+", "+selection+", "+e);
            e.printStackTrace();
        }
        try {
        	Field fld =PropertySheetPage.class.getDeclaredField("sourcePart");
        	fld.setAccessible(true);
        	mySourcePart = (IWorkbenchPart) fld.get(this);
        } catch (Exception e) {
        }

        if(isPropsPage)
            setUpCategories(fcChanged);

    }

    private void setUpCategories(boolean fcChanged) {
        try {
            Control ctrl = getControl();
            Color catBG = ColorManager.getColor(220, 220, 240);
            FontData tfd = ctrl.getFont().getFontData()[0];
            Font catFont = FontManager.getFont(tfd.getName(), tfd.getHeight(),
                    SWT.BOLD, false, true);
            Vector exp = jiglooPlugin.getPropertyCategoriesExpanded();
            if (ttree != null) {
                TableTreeItem[] items = ttree.getItems();
                for (int i = 0; i < items.length; i++) {
                    TableTreeItem item = items[i];
                    if(item != null && item.isDisposed())
                    	System.err.println("Item is disposed!");
                    if (isCategoryItem(item)) {
                        try {
                            item.setFont(catFont);
                        } catch(Throwable t){}
                        try {
                            item.setBackground(catBG);
                        } catch(Throwable t){
                        	t.printStackTrace();
                        }
                        TableTreeItem[] cits = item.getItems();
                        if(cits == null || cits.length == 0)
                            return;
                        for (int j = 0; j < cits.length; j++) {
                            if(cits[j].isDisposed())
                            	System.err.println("Item is disposed!");
                            if(cits[j].getText() == null || "".equals(cits[j].getText()))
                                return;
                        }
                        if (fcChanged && item.getItemCount() != 0) {
                            if (exp.contains(item.getText()))  {
                                if(!item.getExpanded()) {
                                    item.setExpanded(true);
                                }
                            } else {
                                if(item.getExpanded()) {
                                    item.setExpanded(false);
                                }
                            }
                        }
                    }
                }
            } else if (tree != null) {
                TreeItem[] items = tree.getItems();
                for (int i = 0; i < items.length; i++) {
                    TreeItem item = items[i];
                    if(item != null && item.isDisposed())
                    	System.err.println("Item is disposed!");
                    if (isCategoryItem(item)) {
                        try {
                            item.setFont(catFont);
                        } catch(Throwable t){}
                        try {
                            item.setBackground(catBG);
                        } catch(Throwable t){
                        	t.printStackTrace();
                        }
                        TreeItem[] cits = item.getItems();
                        if(cits == null || cits.length == 0)
                            return;
                        for (int j = 0; j < cits.length; j++) {
                            if(cits[j].isDisposed())
                            	System.err.println("Item is disposed!");
                            if(cits[j].getText() == null || "".equals(cits[j].getText()))
                                return;
                        }
                        if (fcChanged && item.getItemCount() != 0) {
                            if (exp.contains(item.getText()))  {
                                if(!item.getExpanded()) {
                                    item.setExpanded(true);
                                }
                            } else {
                                if(item.getExpanded())
                                    item.setExpanded(false);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("CGPropertySheetPage expand categories " + e);
            //		e.printStackTrace();
        }

    }

    protected void initDragAndDrop() {
        // super.initDragAndDrop();
    }

}