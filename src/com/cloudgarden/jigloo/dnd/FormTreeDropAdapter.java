package com.cloudgarden.jigloo.dnd;

import java.util.Vector;

import javax.swing.Action;
import javax.swing.JMenu;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.outline.TreeParent;
import com.cloudgarden.jigloo.util.JiglooUtils;
/**
 * Supports dropping gadgets into a tree viewer.
 */
public class FormTreeDropAdapter extends ViewerDropAdapter {

	FormEditor editor;
	FormDragListener dragListener;
	DropTargetEvent currentEvent;
	int lastOperation = 0;
	int lastFeedback = 0;

	public FormTreeDropAdapter(TreeViewer viewer, FormEditor editor, FormDragListener dragListener) {
		super(viewer);
		this.editor = editor;
		this.dragListener = dragListener;
		setFeedbackEnabled(true);
		setScrollExpandEnabled(true);
	}

	private boolean isInsertAfter(DropTargetEvent event) {
		TreeItem ti = (TreeItem) event.item;
		Rectangle b = ti.getBounds();
		int tiy = ti.getParent().toDisplay(0, b.y).y;
		if (event.y > tiy + b.height * 3 / 4) {
			return true;
		}
		return false;
	}

	private boolean isInsertBefore(DropTargetEvent event) {
		TreeItem ti = (TreeItem) event.item;
		Rectangle b = ti.getBounds();
		int tiy = ti.getParent().toDisplay(0, b.y).y;
		if (event.y < tiy + b.height * 1 / 4) {
			return true;
		}
		return false;
	}

	private FormComponent getFormComponent(DropTargetEvent event) {
		TreeItem ti = (TreeItem) event.item;
		if (isInsertAfter(event)) {
			event.feedback = DND.FEEDBACK_INSERT_AFTER;
		} else if (isInsertBefore(event)) {
			event.feedback = DND.FEEDBACK_INSERT_BEFORE;
		} else {
			event.feedback = DND.FEEDBACK_SELECT;
		}
		lastFeedback = event.feedback;
		return getFromTreeItem(ti);
	}

	private FormComponent getFromTreeItem(TreeItem ti) {
		String name = ti.getText();
		Object data = ti.getData();
		if(data instanceof TreeObject) {
			return ((TreeObject)data).getFormComponent();
		} else {
			System.err.println("TreeItem's data is not a TreeObject "+data+", name="+name);
		}
		return editor.getComponentByName(name);
	}

	private TreeItem lastItem = null;
	
	public void dragOver(DropTargetEvent event) {
		
		//System.out.println("DRAG OVER "+event.item);
	
		if(event.item == null)
			event.item = lastItem;
		else if(event.item instanceof TreeItem)
			lastItem = (TreeItem) event.item;
		
		super.dragOver(event);
		
		if (!(event.item instanceof TreeItem)) {
			event.detail = DND.DROP_NONE;
			event.detail = DND.FEEDBACK_NONE;
			lastOperation = DND.DROP_NONE;
			return;
		}
		
		FormComponent target = getFormComponent(event);
		
		if (event.feedback == DND.FEEDBACK_INSERT_AFTER
			|| event.feedback == DND.FEEDBACK_INSERT_BEFORE) {
			target = target.getParent();
		}
		boolean canDrop = true;
		//Object[] sels =
		//((StructuredSelection) getViewer().getSelection()).toArray();
		Vector sels = editor.getSelectedComponents();
		for (int i = 0; i < sels.size(); i++) {
			FormComponent fc = (FormComponent) sels.elementAt(i);
			if (!fc.canMoveToParent(target)) {
				canDrop = false;
				break;
			}
		}
		if (canDrop) {
			event.detail = getCurrentOperation();
		} else {
			event.detail = DND.DROP_NONE;
		}
		lastOperation = event.detail;
	}

	/**
	  * Method declared on ViewerDropAdapter
	  */
	public boolean performDrop(Object data) {
		return true;
	}
	/**
	 * Method declared on ViewerDropAdapter
	 */
	public boolean validateDrop(Object target, int op, TransferData type) {
		return FormTransfer.getInstance().isSupportedType(type)
			|| PluginTransfer.getInstance().isSupportedType(type)
			|| LocalSelectionTransfer.getInstance().isSupportedType(type);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop(DropTargetEvent event) {
		super.drop(event);
		TreeParent target = (TreeParent) getCurrentTarget();
		TreeViewer viewer = (TreeViewer) getViewer();
		IStructuredSelection selection =
			(IStructuredSelection) dragListener.getSelection();

		TreeParent[] sels =
			(TreeParent[]) selection.toList().toArray(
				new TreeParent[selection.size()]);
		//cannot drop a gadget onto itself or a child

		for (int i = 0; i < sels.length; i++) {
			if (sels[i].equals(target) || target.hasParent(sels[i])) {
				return;
			}
		}
		FormComponent tfc = target.getFormComponent();
		int pos = 0;
		if (isInsertAfter(event)) {
			pos = tfc.getIndexInParent() + 1;
			tfc = tfc.getParent();
		} else if (isInsertBefore(event)) {
			pos = tfc.getIndexInParent();
			tfc = tfc.getParent();
		}

		boolean copy = lastOperation == DND.DROP_COPY;
		if (!copy && lastOperation != DND.DROP_MOVE)
			return;
		FormComponent[] fcs = new FormComponent[sels.length];
		int actionsAdded = 0;
		for (int i = 0; i < sels.length; i++) {
			fcs[i] = sels[i].getFormComponent();
			if(fcs[i].isSubclassOf(Action.class)) {
			    if(tfc.isA(JMenu.class)) {
			        FormComponent menuItem = FormComponent.newFormComponent(editor, "javax.swing.JMenuItem");
//			        menuItem.setEditor(editor);
//			        menuItem.setClassName("javax.swing.JMenuItem");
			        menuItem.setExistsInCode(true);
			        menuItem.setAssigned(true);
			        
			        menuItem.setParent(tfc);
			        tfc.add(menuItem);
			        
			        menuItem.addToCode();
			        menuItem.setPropertyValue("action", fcs[i]);
			    } else if(tfc.hasProperty("action")) {
			        tfc.setPropertyValue("action", fcs[i]);
			    }
		        fcs[i] = null;
		        actionsAdded ++;
			}
		}
		if(actionsAdded != 0) {
	        editor.flushActions();
	        editor.setDirtyAndUpdate(true);
	        return;
		}
		JiglooUtils.sortComponents(fcs, true);
		editor.doMoveOrCopy(tfc, pos, fcs, null, copy);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dropAccept(event);
	}

	public void dragEnter(DropTargetEvent event) {
		super.dragEnter(event);
//		System.out.println("DRAG ENTER "+event.detail+", "+event.feedback);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent event) {
		super.dragLeave(event);
//		System.out.println("DRAG LEAVE "+event.detail+", "+event.feedback);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragOperationChanged(event);
	}

}