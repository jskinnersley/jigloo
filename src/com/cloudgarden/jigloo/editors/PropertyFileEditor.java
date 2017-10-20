/*
 * Created on May 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.editors;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.cloudgarden.jigloo.appFramework.DefaultUndoManager;

/**
 * @author jonathan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PropertyFileEditor extends TextEditor {

    private HashMap propertyEditorActions;
    private FormEditor formEditor;
    private DefaultUndoManager um;
    private CTabItem propTabItem;

    public IAction getPropertyEditorAction(String actionID) {
        return (IAction) propertyEditorActions.get(actionID);
    }

	public void dispose() {
		super.dispose();
		um.disconnect();
		um = null;
		formEditor = null;
		propertyEditorActions.clear();
		propertyEditorActions = null;
	}
	
    public void init(FormEditor editor, CTabItem propTabItem) throws PartInitException {
        init((IEditorSite)editor.getSite(), new FileEditorInput(editor.getBundleManager().getPropertyFile()));
        this.formEditor = editor;
        this.propTabItem = propTabItem;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        if(propTabItem != null) {
            Composite propComp = new Composite(propTabItem.getParent(), SWT.NULL);
            propComp.setLayout(new FillLayout());
            propTabItem.setControl(propComp);
            super.createPartControl(propComp);
            um = new DefaultUndoManager(25);
            getSourceViewer().setUndoManager(um);
    		um.connect(getSourceViewer());
    		um.reset();
        } else {
            super.createPartControl(parent);
        }
    }

    public void setAction(String actionID, IAction action) {
        super.setAction(actionID, action);
        if (propertyEditorActions == null)
            propertyEditorActions = new HashMap();
        propertyEditorActions.put(actionID, action);
    }

    public void createActions() {
        if(getAction(IWorkbenchActionConstants.UNDO) != null)
            return;
        super.createActions();
    }

    public void unregisterActions(IKeyBindingService kbs) {
        if (propertyEditorActions != null) {
            Iterator it = propertyEditorActions.keySet().iterator();
            while(it.hasNext()) {
                String id = (String) it.next();
//                if(!formEditor.isProtectedAction(id))
                    kbs.unregisterAction(getPropertyEditorAction(id));
            }
        }
    }

    public void registerActions(IKeyBindingService kbs) {
        if (propertyEditorActions != null) {
            Iterator it = propertyEditorActions.keySet().iterator();
            while(it.hasNext()) {
                String id = (String) it.next();
                if(!formEditor.isProtectedAction(id))
                    kbs.registerAction(getPropertyEditorAction(id));
            }
        }
    }

}