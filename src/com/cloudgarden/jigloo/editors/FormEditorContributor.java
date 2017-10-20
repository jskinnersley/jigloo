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
package com.cloudgarden.jigloo.editors;

import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditorActionContributor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.internal.EditorActionBars;

import com.cloudgarden.jigloo.images.ImageManager;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class FormEditorContributor extends CompilationUnitEditorActionContributor {
	//EditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private Action selectAllAction;
	private Action saveAction;
	private Action copyAction, cutAction, pasteAction, deleteAction;
	private Action undoAction, redoAction;
	private Action runAction, previewAction;
	private Action gotoCodeAction, gridAction;
	private Action toggleAction;

	/**
	 * Creates a multi-page contributor.
	 */
	public FormEditorContributor() {
		super();
		//System.out.println("CREATE " + this);
		createActions();
	}
	
	//protected IAction getAction(ITextEditor editor, String actionID) {
		//System.out.println("GET ACTION " + actionID + ", " + this);
		//return (editor == null ? null : editor.getAction(actionID));
	//}


	private void createActions() {
		toggleAction = new Action() {
			public void run() {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.doToggle();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		toggleAction.setImageDescriptor(
			ImageManager.getImageDesc("toggle.gif"));

		previewAction = new Action() {
			public void setEnabled(boolean enabled) {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.doPreview();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		previewAction.setImageDescriptor(
			ImageManager.getImageDesc("preview.gif"));
		
		runAction = new Action() {
			public void run() {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.doRun();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		runAction.setImageDescriptor(ImageManager.getImageDesc("run_exec.gif"));

		gridAction = new Action() {
			public void run() {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.toggleGrid();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		gridAction.setImageDescriptor(
			ImageManager.getImageDesc("gridAction.gif"));

		gotoCodeAction = new Action() {
			public void run() {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.showInJavaEditor(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		gotoCodeAction.setImageDescriptor(
			ImageManager.getImageDesc("gotoCode.gif"));

		saveAction = new Action() {
			public void run() {
				try {
					if (activeEditorPart instanceof FormEditor) {
						FormEditor fed = (FormEditor) activeEditorPart;
						fed.doSave(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		//saveAction.setAccelerator(SWT.CTRL | 'S');

		selectAllAction = new Action() {
			public void run() {
				if (activeEditorPart instanceof FormEditor) {
					FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.SELECT_ALL);
				}
			}
		};
		undoAction = new Action() {
			public void run() {
				if (activeEditorPart instanceof FormEditor) {
					FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.UNDO);
				}
			}
		};
		redoAction = new Action() {
			public void run() {
				if (activeEditorPart instanceof FormEditor) {
					FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.REDO);
				}
			}
		};
		copyAction = new Action() {
			public void run() {
				if (activeEditorPart instanceof FormEditor) {
					FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.COPY);
				}
			}
		};
		cutAction = new Action() {
		    public void run() {
		        if (activeEditorPart instanceof FormEditor) {
		            FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.CUT);
		        }
		    }
		};
		pasteAction = new Action() {
		    public void run() {
		        if (activeEditorPart instanceof FormEditor) {
		            FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.PASTE);
		        }
		    }
		};
		
		deleteAction = new Action() {
		    public void run() {
		        if (activeEditorPart instanceof FormEditor) {
		            FormEditor fed = (FormEditor) activeEditorPart;
					fed.doAction(IWorkbenchActionConstants.DELETE);
		        }
		    }
		};

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IEditorPart targetEditor) {
//		System.err.println("***SET ACTIVE EDITOR " + targetEditor+", "+activeEditorPart);
		try {

			super.setActiveEditor(targetEditor);
		} catch(Throwable t) {
		    System.err.println("Error in FormEditorContributor.setActiveEditor");
		    t.printStackTrace();
		}
		if(activeEditorPart == targetEditor)
		    return;
		

			activeEditorPart = targetEditor;
			if (targetEditor instanceof FormEditor) {
				FormEditor fed = (FormEditor) targetEditor;
				fed.activated();
			}

			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(
						IWorkbenchActionConstants.SELECT_ALL,
						selectAllAction);
				actionBars.setGlobalActionHandler(
						IWorkbenchActionConstants.PASTE,
						pasteAction);
				actionBars.setGlobalActionHandler(
					IWorkbenchActionConstants.CUT,
					cutAction);
				actionBars.setGlobalActionHandler(
					IWorkbenchActionConstants.COPY,
					copyAction);
				actionBars.setGlobalActionHandler(
				    IWorkbenchActionConstants.DELETE,
					deleteAction);
				actionBars.setGlobalActionHandler(
					IWorkbenchActionConstants.UNDO,
					undoAction);
				actionBars.setGlobalActionHandler(
						IWorkbenchActionConstants.REDO,
						redoAction);
				actionBars.updateActionBars();
			}
	}

    public void dispose() {
        super.dispose();
        activeEditorPart = null;
        
        toggleAction = saveAction=copyAction= 
        	cutAction= pasteAction= deleteAction
			=undoAction= redoAction = runAction= 
				previewAction = gotoCodeAction
				= selectAllAction
				= gridAction = null;
        
        IActionBars actionBars = getActionBars();
        if (actionBars != null) {
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.SELECT_ALL,
					selectAllAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.PASTE,
					pasteAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.CUT,
					cutAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.COPY,
					copyAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.DELETE,
					deleteAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.UNDO,
					undoAction);
        	actionBars.setGlobalActionHandler(
        			IWorkbenchActionConstants.REDO,
					redoAction);
        	actionBars.updateActionBars();
        }
        setActiveEditor(null);
        ((EditorActionBars)actionBars).setEditorContributor(null);
    }
}
