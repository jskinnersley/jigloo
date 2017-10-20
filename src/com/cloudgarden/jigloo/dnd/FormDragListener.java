package com.cloudgarden.jigloo.dnd;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.outline.TreeParent;
/**
 * Supports dragging gadgets from a structured viewer.
 */
public class FormDragListener extends DragSourceAdapter {
	private StructuredViewer viewer;
	FormEditor editor;
	ISelection selection;
	
	public FormDragListener(StructuredViewer viewer, FormEditor editor) {
		this.viewer = viewer;
		this.editor = editor;
	}
	/**
	 * Method declared on DragSourceListener
	 */
	
	public void dragFinished(DragSourceEvent event) {
		//System.out.println("DRAG FINISHED " + event);
		//if (!event.doit)
		//return;
		//if the gadget was moved, remove it from the source viewer
		//		if (event.detail == DND.DROP_MOVE) {
		//			IStructuredSelection selection =
		//				(IStructuredSelection) viewer.getSelection();
		//			for (Iterator it = selection.iterator(); it.hasNext();) {
		//				((TreeParent) it.next()).setParent(null);
		//			}
		//			viewer.refresh();
		//		}
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragSetData(DragSourceEvent event) {
		//System.err.println("DRAG SET DATA " + event);
		IStructuredSelection selection =
			(IStructuredSelection) viewer.getSelection();
		TreeParent[] gadgets =
			(TreeParent[]) selection.toList().toArray(
				new TreeParent[selection.size()]);
		if (FormTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = gadgets;
		} else if (LocalSelectionTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = gadgets;
//		} else if (
//			PluginTransfer.getInstance().isSupportedType(event.dataType)) {
//			byte[] data = FormTransfer.getInstance().toByteArray(gadgets);
//			event.data =
//				new PluginTransferData(
//					"com.cloudgarden.jigloo.FormDrop",
//					data);
		}
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragStart(DragSourceEvent event) {
		//System.out.println("DRAG START " + event);
		selection = viewer.getSelection();
		event.doit = !selection.isEmpty();
	}
	
	public ISelection getSelection() {
		return selection;
	}
	
}