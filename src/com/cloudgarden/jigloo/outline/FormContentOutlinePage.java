/*
 * Created on Jul 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.outline;

import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.actions.FormAddAction;
import com.cloudgarden.jigloo.actions.FormEditAction;
import com.cloudgarden.jigloo.actions.FormLayoutAction;
import com.cloudgarden.jigloo.dnd.FormDragListener;
import com.cloudgarden.jigloo.dnd.FormTreeDropAdapter;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.util.DelayableRunnable;

/**
 * @author jsk
 *
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormContentOutlinePage extends ContentOutlinePage {

    private Action runAction, previewAction;
    private Action gotoCodeAction, gridAction;
    private Action toggleAction;
    private Action inheritAction;
    private Action noParseAction;
    private boolean internal = false;
    FormEditor editor;
    IEditorInput input;
    TreeViewer viewer;
    FormOutlineContentProvider fcp;
    DelayableRunnable drun;
    Tree tree;

    boolean allowedToAdd;
    int showInherited = 2;

    private void createActions() {
	toggleAction = new Action() {
	    public void run() {
		editor.doToggle();
	    }
	};
	toggleAction.setImageDescriptor(ImageManager.getImageDesc("toggle.gif"));
	toggleAction.setToolTipText("Switches GUI from Swing to SWT or from SWT to Swing");

	if (noParseAction == null) {
	    noParseAction = editor.getToggleParsingAction();
	}

	inheritAction = new Action() {
	    public void run() {
		toggleShowInherited();
		if (showInherited == 2) {
		    inheritAction.setImageDescriptor(ImageManager.getImageDesc("inherited_all.gif"));
		    inheritAction.setToolTipText("Showing all inherited elements - press to hide inherited elements");
		} else if (showInherited == 1) {
		    inheritAction.setImageDescriptor(ImageManager.getImageDesc("inherited_dec.gif"));
		    inheritAction.setToolTipText("Showing declared inherited elements\n - press to show all inherited elements");
		} else {
		    inheritAction.setImageDescriptor(ImageManager.getImageDesc("inherited_off.gif"));
		    inheritAction.setToolTipText("Not showing inherited elements\n - press to show declared inherited elements");
		}
	    }
	};
	inheritAction.setImageDescriptor(ImageManager.getImageDesc("inherited_all.gif"));
	inheritAction.setToolTipText("Showing all inherited elements - press to hide inherited elements");

	previewAction = new Action() {
	    public void run() {
		editor.doPreview();
	    }
	};
	previewAction.setImageDescriptor(ImageManager.getImageDesc("preview.gif"));
	previewAction.setToolTipText("Pops up a preview of the GUI (non-compiled)");

	runAction = new Action() {
	    public void run() {
		editor.doRun();
	    }
	};
	runAction.setImageDescriptor(ImageManager.getImageDesc("run_exec.gif"));
	runAction.setToolTipText("Builds and runs the generated code");

	gridAction = new Action() {
	    public void run() {
		editor.toggleGrid();
	    }
	};
	gridAction.setImageDescriptor(ImageManager.getImageDesc("gridAction.gif"));
	gridAction.setToolTipText("Toggles snap-grid on and off (use Jigloo preference page to set grid size)");
	// gridAction.setToolTipText("Toggles between no grid, and grids of 5,
	// 10 and 20 pixels");

	gotoCodeAction = new Action() {
	    public void run() {
		editor.showInJavaEditor(null);
	    }
	};
	gotoCodeAction.setImageDescriptor(ImageManager.getImageDesc("gotoCode.gif"));
	gotoCodeAction.setToolTipText("Opens the Java code in an editor");

    }

    private void toggleShowInherited() {
	showInherited++;
	if (showInherited == 3)
	    showInherited = 0;
	reload();
    }

    public void reload() {
	// System.err.println("RELOAD FCOP");
	// new Exception().printStackTrace();
	drun.trigger();
    }

    public FormContentOutlinePage(FormEditor editor, boolean internal) {
	super();
	this.editor = editor;
	this.internal = internal;
	drun = new DelayableRunnable(200, true) {
	    public void run() {
		if (fcp != null) {
		    fcp.reload(getEditor(), getEditor().getSelectedComponents(), showInherited);
		}
	    }
	};
	createActions();
    }

    public FormEditor getEditor() {
	return editor;
    }

    private FormComponent getCurrentFormComponent(MouseEvent e) {
	TreeItem ti = tree.getItem(new Point(e.x, e.y));
	if (ti == null)
	    return null;
	Object data = ti.getData();
	// System.out.println("GOT TREE ITEM " + ti + ", " +
	// ti.getData().getClass());
	if (data instanceof TreeObject) {
	    FormComponent fc = ((TreeObject) data).getFormComponent();
	    if (fc != null)
		return fc;
	}
	return editor.getComponentByName(ti.getText());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.
     * Composite)
     */
    public void createControl(Composite parent) {
	// System.out.println("PARENT = " + parent + ", " +
	// parent.isDisposed());
	if (parent == null || parent.isDisposed())
	    return;
	super.createControl(parent);
	viewer = getTreeViewer();
	tree = (Tree) viewer.getControl();

	viewer.getControl().addMouseListener(new MouseAdapter() {
	    public void mouseDoubleClick(MouseEvent e) {
		getEditor().checkParsing();
		FormComponent sel = getCurrentFormComponent(e);
		editor.selectionChanged(null, getSelection());
		editor.selectInCode(sel, null, true);
		setFocus();
	    }

	    public void mouseDown(MouseEvent e) {
		getEditor().checkParsing();
		FormComponent sel = getCurrentFormComponent(e);
		editor.selectionChanged(null, getSelection());
		// setFocus required because focus can be lost if setSelection
		// causes a new root component to be brought to front
		setFocus();
		// System.out.println("OUTLINE SEL "+getSelection()+", "+sel);
		Action currentAction = editor.getCurrentAction();
		if (currentAction != null) {
		    editor.processCurrentAction(null, allowedToAdd, sel);
		    tree.setCursor(editor.getDefCursor());
		}
	    }
	});

	tree.addKeyListener(getEditor().getFormEditorKeyAdapter());

	tree.addMouseTrackListener(new MouseTrackListener() {
	    public void mouseEnter(MouseEvent e) {
	    }

	    public void mouseExit(MouseEvent e) {
	    }

	    public void mouseHover(MouseEvent e) {
		if (editor.getCurrentAction() == null)
		    return;
		TreeItem it = tree.getItem(new Point(e.x, e.y));
		if (it != null) {
		    viewer.setExpandedState(it.getData(), true);
		    it.setExpanded(true);
		}
	    }
	});

	viewer.getControl().addMouseMoveListener(new MouseMoveListener() {

	    public void mouseMove(MouseEvent e) {
		allowedToAdd = false;
		if (e.button != 0)
		    return;
		Action currentAction = editor.getCurrentAction();
		if (currentAction != null) {

		    FormComponent sel = getCurrentFormComponent(e);
		    if (sel == null) {
			tree.setCursor(editor.getDefCursor());
			return;
		    }

		    if (currentAction instanceof FormAddAction) {
			FormAddAction faa = (FormAddAction) currentAction;
			Class addClass = faa.getAddClass();
			int style = faa.getAddStyle();
			if (sel == null && addClass.equals(Menu.class) && style == SWT.BAR)
			    sel = editor.getRootComponent();
			if (sel != null && sel.canAddToThis(addClass, style)) {
			    tree.setCursor(editor.getXCursor());
			    allowedToAdd = true;
			} else {
			    tree.setCursor(editor.getNoCursor());
			}
		    } else if (currentAction instanceof FormLayoutAction) {
			// if (sel == null)
			// sel = editor.getRootComponent();
			if (sel != null && sel.canSetLayout()) {
			    tree.setCursor(editor.getXCursor());
			    allowedToAdd = true;
			} else {
			    tree.setCursor(editor.getNoCursor());
			}
		    }
		}

	    }
	});
	// viewer.addSelectionChangedListener(editor);

	fcp = new FormOutlineContentProvider(viewer);
	viewer.setContentProvider(fcp);
	fcp.initialize();

	hookContextMenu();
	contributeToActionBars();

	int ops = DND.DROP_COPY | DND.DROP_MOVE;

	// Transfer[] transfers = new Transfer[] { FormTransfer.getInstance(),
	// PluginTransfer.getInstance()};
	Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance() };

	FormDragListener dragListener = new FormDragListener(viewer, editor);
	viewer.addDragSupport(ops, transfers, dragListener);
	transfers = new Transfer[] { LocalSelectionTransfer.getInstance() };
	FormTreeDropAdapter dropAdapter = new FormTreeDropAdapter(viewer, editor, dragListener);
	viewer.addDropSupport(ops, transfers, dropAdapter);

	if (getSite() != null) {
	    Action undoAction = new FormEditAction(getSite(), "Undo", IWorkbenchActionConstants.UNDO);
	    Action redoAction = new FormEditAction(getSite(), "Redo", IWorkbenchActionConstants.REDO);
	    Action copyAction = new FormEditAction(getSite(), "Copy", IWorkbenchActionConstants.COPY);
	    Action cutAction = new FormEditAction(getSite(), "Cut", IWorkbenchActionConstants.CUT);
	    Action pasteAction = new FormEditAction(getSite(), "Paste", IWorkbenchActionConstants.PASTE);
	    // this does nothing in eclipse 2.1, but in eclipse 3 it causes
	    // doDelete to be called twice (the first time is from the
	    // KeyAdapter)
	    // Action deleteAction = new FormEditAction(getSite(), "Delete",
	    // IWorkbenchActionConstants.DELETE);
	}

	// calling drun.run() causes the LayoutWrappers to be created before the
	// components are created, leading to "No Layout"s whene there should
	// be ones (eg, when inherited etc).
	// drun.run();

    }

    private void hookContextMenu() {
	MenuManager menuMgr = new MenuManager("#PopupMenu");
	menuMgr.setRemoveAllWhenShown(true);
	menuMgr.addMenuListener(new IMenuListener() {
	    public void menuAboutToShow(IMenuManager manager) {
		// System.out.println("FV menuAboutToShow");
		// editor.initContextMenuManager(manager);
		editor.setMenuMouseEvent(null);
		editor.initContextMenuManager(manager, viewer.getSelection());
	    }
	});
	Menu menu = menuMgr.createContextMenu(viewer.getControl());
	viewer.getControl().setMenu(menu);
	if (getSite() != null)
	    getSite().registerContextMenu("FormMenu", menuMgr, viewer);
    }

    private void contributeToActionBars() {
	if (getSite() == null)
	    return;
	IActionBars bars = getSite().getActionBars();
	fillLocalToolBar(bars.getToolBarManager());
    }

    public void fillLocalToolBar(IToolBarManager manager) {

	manager.add(gridAction);
	manager.add(previewAction);
	manager.add(runAction);
	manager.add(noParseAction);
	manager.add(inheritAction);
	// manager.add(gotoCodeAction);
	if (jiglooPlugin.canUseSwing()) {
	    manager.add(toggleAction);
	}
    }

    public TreeViewer getTreeViewer() {
	return super.getTreeViewer();
    }

    public void init(IPageSite pageSite) {
	super.init(pageSite);
    }

    public void addChildComponent(FormComponent comp, FormComponent parent) {
	if (fcp != null)
	    fcp.addChildComponent(comp, parent);
    }

    public void removeChildComponent(FormComponent comp, FormComponent parent) {
	if (fcp != null)
	    fcp.removeChildComponent(comp, parent);
    }

    public void setSelected(Vector sel) {
	if (fcp != null)
	    fcp.setSelection(sel);
    }

    public void refreshTreeNode(FormComponent fc) {
	if (fcp != null)
	    fcp.refreshTreeNode(fc);
    }
    // public void selectionChanged(SelectionChangedEvent event) {
    // super.selectionChanged(event);
    // System.err.println(
    // "FCOP selection changed "
    // + event.getSelection()
    // + ", "
    // + event.getSource());
    // }

}
