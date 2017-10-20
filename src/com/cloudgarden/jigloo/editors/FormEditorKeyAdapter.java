package com.cloudgarden.jigloo.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.cloudgarden.jigloo.FormComponent;

public class FormEditorKeyAdapter extends KeyAdapter {
	
	private FormEditor editor;
	private int keyCode = -1;
	private boolean ctrlDown = false;
	private boolean shiftDown = false;
	private boolean cmdDown = false;
	private boolean altDown = false;
	private HashMap selectedParents = new HashMap();
	
	public FormEditorKeyAdapter(FormEditor editor) {
		this.editor = editor;
	}
	
	public void handleMouseEvent(MouseEvent e) {
		ctrlDown = (e.stateMask & SWT.CTRL) != 0;
		altDown = (e.stateMask & SWT.ALT) != 0;
		cmdDown = (e.stateMask & SWT.COMMAND) != 0;
		shiftDown = (e.stateMask & SWT.SHIFT) != 0;
	}
	
	public void keyReleased(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0)
			ctrlDown = false;
		if ((e.stateMask & SWT.ALT) != 0)
			altDown = false;
		if ((e.stateMask & SWT.COMMAND) != 0)
			cmdDown = false;
		if ((e.stateMask & SWT.SHIFT) != 0)
			shiftDown = false;
		keyCode = -1;
	}
	
	public void keyPressed(KeyEvent e) {

		//the auto-repeat functionality from sending lots of "keyPressed" events
		//if a key is held down - only want the first one.
		if(e.keyCode == keyCode)
			return;
		
		keyCode = e.keyCode;
		
		if ((e.stateMask & SWT.CTRL) != 0 || e.keyCode == SWT.CTRL)
			ctrlDown = true;
		if ((e.stateMask & SWT.ALT) != 0 || e.keyCode == SWT.ALT)
			altDown = true;
		if ((e.stateMask & SWT.COMMAND) != 0 || e.keyCode == SWT.COMMAND)
			cmdDown = true;
		if ((e.stateMask & SWT.SHIFT) != 0 || e.keyCode == SWT.SHIFT)
			shiftDown = true;
		
		e.doit = false;
		
		int keyCode = e.keyCode;
		
		if (keyCode == SWT.ESC) {
			editor.handleEscape();
			return;
		}

		if (editor.getSelectedComponent() == null)
			return;

		editor.checkParsing();

		if(e.character == 'p' || e.character == 'P' ) {
			toggleParentSelection();
		}
		
		if (keyCode == SWT.DEL || keyCode == SWT.BS) {
			editor.doAction(IWorkbenchActionConstants.DELETE);
		} else {
			if (ctrlDown) {
				FormComponent newSel = null;
				FormComponent par = editor.getSelectedComponent().getParent();
				int ind = editor.getSelectedComponent().getIndexInParent();
				if (keyCode == SWT.ARROW_UP) {
					if (par != null) {
						if (ind == 0)
							ind = par.getChildCount();
						newSel = par.getChildAt(ind - 1);
					}
				} else if (keyCode == SWT.ARROW_DOWN) {
					if (par != null) {
						if (ind == par.getChildCount() - 1)
							ind = -1;
						newSel = par.getChildAt(ind + 1);
					}
				} else if (keyCode == SWT.ARROW_LEFT) {
					if (par != null)
						newSel = par;
				} else if (keyCode == SWT.ARROW_RIGHT) {
					newSel = editor.getSelectedComponent().getChildAt(0);
				}
				if (newSel != null)
					editor.setSelectedComponent(newSel, true);
				return;
			}

			if (editor.getSelectedComponent().getParent() != null) {
				String plt = editor.getSelectedComponent().getParentSuperLayoutType();
				if ("Flow".equals(plt) || "Grid".equals(plt) || "Row".equals(plt) || "Box".equals(plt)
						|| editor.getSelectedComponent().isMenuComponent()) {
					if (keyCode == SWT.ARROW_UP || keyCode == SWT.ARROW_LEFT)
						editor.doMoveUp();
					if (keyCode == SWT.ARROW_DOWN || keyCode == SWT.ARROW_RIGHT)
						editor.doMoveDown();
					return;
				}
			}

			Vector sels = editor.getSelectedComponents();
			for (Iterator iter = sels.iterator(); iter.hasNext();) {
				FormComponent fc = (FormComponent) iter.next();
				fc.handleKeyPress(keyCode);
			}

		}
	}

	public int getKeyCode() {
		return keyCode;
	}

	public boolean isCtrlDown() {
		return ctrlDown;
	}

	public boolean isShiftDown() {
		return shiftDown;
	}

	public boolean isCmdDown() {
		return cmdDown;
	}

	public boolean isAltDown() {
		return altDown;
	}

	public boolean isParentSelected(FormComponent comp) {
		return selectedParents.containsValue(comp);
	}
	
	public void toggleParentSelection() {
		FormComponent sel = editor.getSelectedComponent();
		if(selectedParents.containsKey(sel)) {
			editor.unselectComponent(sel, true);
			FormComponent child = (FormComponent) selectedParents.remove(sel);
			editor.addSelectedComponent(child, true);
		} else {
			if(sel.getParent() != null && sel.getParent().isVisual()) {
				selectedParents.put(sel.getParent(), sel);
				editor.unselectComponent(sel, true);
				editor.addSelectedComponent(sel.getParent(), true);
			}
		}
	}


}
