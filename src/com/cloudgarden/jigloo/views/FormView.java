/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.cloudgarden.jigloo.views;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.actions.FormAction;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.outline.FormOutlineContentProvider;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.outline.TreeParent;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;
import com.cloudgarden.jigloo.properties.LayoutPropertySheetPage;

/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

//public class FormView extends ViewPart implements ISelectionChangedListener {
public class FormView extends ViewPart implements ISelectionListener, ISelectionChangedListener, ISelectionProvider {

	private static final String FORM_VIEW_MODE = "FORM_VIEW_MODE";
	private boolean refreshing = false;
	private DrillDownAdapter drillDownAdapter;
	private Action showLayoutInfo;
	private Action showProperties;
	private Action showEvents;
	private Action tabPaneAction;
	private Action doubleClickAction;
	private FormEditor editor;
	private PropertySheetPage propertiesPage;
	private PropertySheetPage advPropsPage;
	private PropertySheetPage layoutPage;
	private PropertySheetPage eventsPage;
	private PropertySheetPage propertiesPage2;
	private PropertySheetPage advPropsPage2;
	private PropertySheetPage layoutPage2;
	private PropertySheetPage eventsPage2;
	private Composite propertiesComp;
	private FormComponent selectedComp;
	private FormComponent rootComponent;
	private TreeParent rootNode;
	private FormOutlineContentProvider fcp;
	public final static int MODE_SASH_HORIZ = 1;
	public final static int MODE_SASH_VERT = 2;
	public final static int MODE_TABBED = 3;

	/**
	 * 
	 * The constructor.
	 */
	public FormView() {}

	public boolean isInSwingMode() {
		return editor.isInSwingMode();
	}

	public FormComponent getSelectedComponent() {
		return selectedComp;
	}

	public void setSelected(Object selObj) {
		setSelected(selObj, false);
	}

	public void setSelected(Object selObj, boolean internal) {
		if (selObj == null)
			return;
		FormComponent fc = (FormComponent) selObj;
		FormEditor fed = fc.getEditor();
		//System.err.println("FV SET SELECTED "+internal+", "+fed+", "+editor);
		if (internal || !fed.equals(editor)) {
			if (!internal) {
				setRootComponent(fed.getRootComponent(), fed);
			} else {
				//editor.setSelectedComponent(fc, false);
				//editor.showWindowFrame();
			}
		}
		if (fc != null && !fc.equals(selectedComp)) {
			selectedComp = fc;
			if (!internal) {
				//fcp.setSelection(selectedComp);
			}
		}

		//THIS IS THE BIT THAT RETURNS FOCUS TO THE FORM EDITOR!!!
		//if (internal) {
		//getSite().getPage().activate(fed);
		//getSite().getShell().setFocus();
		//}

		/*
		try {
			PropertySheet props =
				(PropertySheet) getSite().getPage().showView(
					"org.eclipse.ui.views.PropertySheet");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	public void addChildComponent(FormComponent comp, FormComponent parent) {
		//fcp.addChildComponent(comp, parent);
	}

	public void removeChildComponent(FormComponent comp, FormComponent parent) {
		//fcp.removeChildComponent(comp, parent);
	}

	public void reload() {
		//fcp.reload(rootComponent.getEditor(), selectedComp);
	}

	public void setRootComponent(FormComponent comp, FormEditor fed) {
		try {
			//System.out.println("Got root component " + comp);
			if (fed.equals(editor) || comp == null)
				return;
			editor = fed;
			rootComponent = comp;
			reload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CGPropertySheetPage createPropertiesPage(Composite parent, String name,
			boolean advanced) {
		return new CGPropertySheetPage(name, parent, getSite(),	advanced, true);
	}

	private IPropertySourceProvider eventPropertySourceProvider = new IPropertySourceProvider() {
		public IPropertySource getPropertySource(Object o) {
			//System.out.println("EVENTS PAGE get prop src " + o);
			if (o instanceof TreeObject) {
				TreeObject tob = (TreeObject) o;
				FormComponent fc = tob.getFormComponent();
				if (fc != null)
					return fc.getEventWrapper();
			} else if (o instanceof FormComponent) {
				return ((FormComponent) o).getEventWrapper();
			} else if (o instanceof IPropertySource) {
				return (IPropertySource) o;
			}
			return null;
		}
	};
	
	private CGPropertySheetPage createEventsPage(Composite parent, String name) {
		CGPropertySheetPage page = new CGPropertySheetPage(name, parent, getSite(),
				false, false);
		page.setPropertySourceProvider(eventPropertySourceProvider);
		return page;
	}

	public boolean isMinimized() {
	    try {
	        return getViewSite().getPage().isPartVisible(this);
	    } catch(Throwable t) {
	    }
        return true;
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		try {

			fvc = new FormViewComposite(parent, SWT.NULL);

			propertiesPage = createPropertiesPage(fvc.getSashForm(), "Properties", false);
			if(jiglooPlugin.showAdvancedProperties())
				advPropsPage = createPropertiesPage(fvc.getSashForm(), "Advanced", true);
			layoutPage = new LayoutPropertySheetPage(fvc.getSashForm(), "Layout", getSite());
			eventsPage = createEventsPage(fvc.getSashForm(), "Event Name");

			propertiesPage2 = createPropertiesPage(fvc.getPropertiesTab(), "Properties", false);
			if(jiglooPlugin.showAdvancedProperties())
				advPropsPage2 = createPropertiesPage(fvc.getAdvPropertiesTab(), "Advanced", true);
			layoutPage2 = new LayoutPropertySheetPage(fvc.getLayoutTab(), "Layout", getSite());
			eventsPage2 = createEventsPage(fvc.getEventsTab(), "Event Name");

			createActions();
			contributeToActionBars();

			mode = jiglooPlugin.getDefault().getIntPreference(FORM_VIEW_MODE);

			if (mode == 0 || mode == MODE_SASH_HORIZ) {
				fvc.showSashForm();
				tabPaneAction.setChecked(false);
				mode = MODE_SASH_HORIZ;
			} else if (mode == MODE_TABBED) {
				fvc.showTabPane();
				tabPaneAction.setChecked(true);
			}

			if (propertiesComp != null)
				propertiesComp.layout();

			IEditorPart ed = null;
			if (getSite() != null)
				ed = getSite().getPage().getActiveEditor();

			if (ed instanceof FormEditor) {
				setFormEditor((FormEditor) ed);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void setFormEditor(FormEditor fed) {
		setRootComponent(fed.getRootComponent(), fed);
	}

	private int mode = MODE_SASH_HORIZ;

	public void showTabs(boolean tabs) {
		if (!tabs) {
			fvc.showSashForm();
			mode = MODE_SASH_HORIZ;
		} else {
			fvc.showTabPane();
			mode = MODE_TABBED;
		}
	}

	private void createActions() {
		tabPaneAction = new Action("Show as tabbed pane", SWT.TOGGLE) {
			public void run() {
				try {
					if (mode == MODE_TABBED) {
						fvc.showSashForm();
						mode = MODE_SASH_HORIZ;
					} else if (mode == MODE_SASH_HORIZ) {
						fvc.showTabPane();
						mode = MODE_TABBED;
					}
					jiglooPlugin.getDefault().setPreference(FORM_VIEW_MODE, mode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		tabPaneAction.setText("Show as tabbed pane");
		tabPaneAction.setToolTipText("Toggles between showing \"GUI properties\" as tabbed pane and sash pane");
		tabPaneAction.setImageDescriptor(ImageDescriptor.createFromFile(FormAction.class, "formViewLayout.gif"));
	}

//	MenuManager menuMgr;
//	Menu menu;
//
	public void refreshMode() {
//		menu = null;
		//initContextMenu();
	}


//	private void hookContextMenu() {
//		MenuManager menuMgr = new MenuManager("#PopupMenu");
//		menuMgr.setRemoveAllWhenShown(true);
//		menuMgr.addMenuListener(new IMenuListener() {
//			public void menuAboutToShow(IMenuManager manager) {
//				//System.out.println("FV menuAboutToShow");
//				editor.initContextMenuManager(manager);
//				//FormView.this.fillContextMenu(manager);
//			}
//		});
//		Menu menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
//	}
//
//	private void initContextMenu() {
//		if (viewer == null || editor == null || menu != null)
//			return;
//		menuMgr = new MenuManager("#PopupMenu");
//		menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
//		editor.initContextMenuManager(menuMgr);
//	}

	private void contributeToActionBars() {
		if (getViewSite() == null)
			return;
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(tabPaneAction);
		//manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(tabPaneAction);
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator("Additions"));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(tabPaneAction);
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		try {
			System.out.println("FV selectionChanged " + event.getSelection() + ", " + event.getSelectionProvider());
			handleSelection(this, event.getSelection());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private ISelection selection = null;

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		try {
			//						System.out.println(
			//							"FV selectionChanged (2) "
			//								+ selection
			//								+ ", part="
			//								+ part
			//								+ ", "
			//								+ this);

			if (part.equals(this))
				return;
			if (selection == null)
				return;
			if (this.selection != null && selection.hashCode() == this.selection.hashCode())
				return;
			this.selection = selection;

			//Set FormView Title
			StringBuffer selStr = new StringBuffer("[ ");
			if (selection instanceof StructuredSelection) {
				StructuredSelection ssel = (StructuredSelection) selection;
				for (Iterator iter = ssel.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					if (obj instanceof FormComponent) {
						FormComponent fc = (FormComponent) obj;
						if (selStr.length() != 2)
							selStr.append("; ");
						selStr.append(fc.getNonBlockName() + ":" + fc.getShortClassName());
					} else {
						break;
					}
				}
				selStr.append(" ]");
				if (selStr.length() != 4)
					setTitle("GUI Properties " + selStr.toString());
			}

			if (propertiesPage != null) {
				final IWorkbenchPart fpart = part;
				final ISelection fselection = selection;
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
					    try {
							if (propertiesPage != null && !propertiesPage.getControl().isDisposed()) {
								settingSelection = true;
								propertiesPage.selectionChanged(fpart, fselection);
								if(advPropsPage != null)
									advPropsPage.selectionChanged(fpart, fselection);
								eventsPage.selectionChanged(fpart, fselection);
								layoutPage.selectionChanged(fpart, fselection);
								propertiesPage2.selectionChanged(fpart, fselection);
								if(advPropsPage2 != null)
									advPropsPage2.selectionChanged(fpart, fselection);
								eventsPage2.selectionChanged(fpart, fselection);
								layoutPage2.selectionChanged(fpart, fselection);
								settingSelection = false;
							}
					    } catch(Throwable t) {
					        jiglooPlugin.handleError(t);
					    }
					}
				});
			}
			//handleSelection(part, selection);
			//notifyListeners(selection);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private boolean settingSelection = false;
	
	public boolean isSettingSelection() {
		return settingSelection;
	}
	
	public void handleSelection(IWorkbenchPart part, ISelection selection) {
		try {
			if (selection instanceof StructuredSelection) {
				StructuredSelection sel = (StructuredSelection) selection;
				Object[] obs = sel.toArray();
				if (obs.length == 0) {
					System.out.println("FV selectionChanged no selection");
					return;
				}
				//System.out.println("FV selectionChanged " + obs[0]);
				FormEditor fed = null;
				if (obs[0] instanceof FormEditor) {
					fed = (FormEditor) obs[0];
					if (!fed.equals(editor)) {
						setRootComponent(fed.getRootComponent(), fed);
					}
					return;
				}
				if (part instanceof FormEditor) {
					fed = (FormEditor) part;
				} else {
					IEditorPart ed = getSite().getPage().getActiveEditor();
					fed = (FormEditor) ed;
				}
				if (fed == null) {
					return;
				}
				if (!fed.equals(editor)) {
					setRootComponent(fed.getRootComponent(), fed);
				}
				if (obs[0] instanceof FormComponent) {
					//don't do anything, since FormEditor calls setSelected
					FormComponent fc = (FormComponent) obs[0];
					if (fc.equals(selectedComp))
						return;
					if (!fed.equals(editor)) {
						setRootComponent(fed.getRootComponent(), fed);
					}
					setSelected(obs[0]);
				} else if (obs[0] instanceof TreeObject) {
					TreeObject tob = (TreeObject) obs[0];
					//System.out.println(
					//"GOT TREE OBJ " + tob + ", " + tob.getFormComponent());
					setSelected(tob.getFormComponent(), true);
				}
			}

			if (propertiesComp != null)
				propertiesComp.layout();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//ISelectionProvider methods
	private Vector listeners = new Vector();
	private StructuredSelection structSel;

	private FormViewComposite fvc;

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	public ISelection getSelection() {
		//System.out.println(this +" getSelection " + selectedComp);
		if (selectedComp == null)
			return new StructuredSelection();
		return structSel;
	}

	//	public void notifyListeners(ISelection sel) {
	//		for (int i = 0; i < listeners.size(); i++) {
	//			(
	//				(ISelectionChangedListener) listeners.elementAt(
	//					i)).selectionChanged(
	//				new SelectionChangedEvent(this, sel));
	//		}
	//	}

	public void setSelection(ISelection selection) {
		//System.out.println("FV setSelection " + selection);
	}

	private void disposePages() {
		if (propertiesPage != null) {
		    if(getSite() != null && getSite().getPage() != null) {
				getSite().getPage().removeSelectionListener(propertiesPage);
				if(advPropsPage != null)
					getSite().getPage().removeSelectionListener(advPropsPage);
				getSite().getPage().removeSelectionListener(eventsPage);
				getSite().getPage().removeSelectionListener(layoutPage);
		    }
			propertiesPage.dispose();
			if(advPropsPage != null)
				advPropsPage.dispose();
			layoutPage.dispose();
			eventsPage.dispose();
		    if(getSite() != null && getSite().getPage() != null) {
				getSite().getPage().removeSelectionListener(propertiesPage2);
				if(advPropsPage2 != null)
					getSite().getPage().removeSelectionListener(advPropsPage2);
				getSite().getPage().removeSelectionListener(eventsPage2);
				getSite().getPage().removeSelectionListener(layoutPage2);
		    }
			propertiesPage2.dispose();
			if(advPropsPage2 != null)
				advPropsPage2.dispose();
			layoutPage2.dispose();
			eventsPage2.dispose();
		}
		propertiesPage2 = null;
		advPropsPage2 = null;
		eventsPage2 = null;
		layoutPage2 = null;
		
		propertiesPage = null;
		advPropsPage = null;
		eventsPage = null;
		layoutPage = null;
		
		rootComponent = null;
		if(rootNode != null)
			rootNode.dispose();
		
		rootNode = null;
		if(fcp != null)
			fcp.dispose();
		fcp = null;
		if(fvc != null)
			fvc.dispose();
		fvc = null;
		selectedComp = null;
		selection = null;
		tabPaneAction = showLayoutInfo = showProperties = showEvents = 
		    tabPaneAction = doubleClickAction = null;
		editor = null;
		
	}

	public boolean isDisposed() {
	    return fvc == null || fvc.isDisposed();
	}
	
	public void dispose() {
		super.dispose();
		disposePages();
		editor = null;
		eventPropertySourceProvider = null;
	}

	public void rebuild() {
		int tm = mode;
		Composite par = fvc.getParent();
		disposePages();
		editor = null;
//		super.dispose();
		createPartControl(par);
		par.layout();
		mode = tm;
		showTabs(mode == MODE_TABBED);
	}

	public void setFocus() {
		//System.err.println("SET FOCUS " + this);
	}

}