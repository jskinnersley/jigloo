/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.editors;

import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.JavaOutlinePage;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.appFramework.DefaultUndoManager;
import com.cloudgarden.jigloo.outline.FormContentOutlinePage;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JavaEditor extends CompilationUnitEditor {

    protected DefaultUndoManager codeUndoManager;
    
	/**
	 * 
	 */
	public JavaEditor() {
		super();
	}

	public DefaultUndoManager getCodeUndoManager() {
	    return codeUndoManager;
	}
	
	protected void setSelection(ISourceReference reference, boolean moveCursor) {
		try {
			if (getSelectionProvider() == null)
				return;
			super.setSelection(reference, moveCursor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getModelOffset() {
		if(jiglooPlugin.isVersion3Plus() &&
				getSourceViewer() instanceof  org.eclipse.jface.text.ITextViewerExtension5) {
			org.eclipse.jface.text.ITextViewerExtension5 tv = 
				(org.eclipse.jface.text.ITextViewerExtension5)getSourceViewer();
			return tv.widgetOffset2ModelOffset(getSourceViewer().getTextWidget().getCaretOffset());
		}
		return -1;
	}
	
	/**
	 * returns the current command count from the undo manager
	 */
	public int getCommandCount() {
	    return codeUndoManager.getCommandCount();
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        codeUndoManager = new DefaultUndoManager(50);
        getSourceViewer().setUndoManager(codeUndoManager);
		codeUndoManager.connect(getSourceViewer());
		codeUndoManager.reset();
    }
    
    public void dispose() {
    	super.dispose();
    	codeUndoManager.disconnect();
    	codeUndoManager = null;
    }
    
	public StyledText getEditorControl() {
		if (getSourceViewer() == null)
			return null;
		return getSourceViewer().getTextWidget();
	}
	
	public Object getAdapter(Class required) {
	    //over-ride method in org.eclipse.jdt.internal.ui.javaeditor.JavaEditor which returns null because isCalledByOutline() returns false
		if (IContentOutlinePage.class.equals(required)) {
			if (fOutlinePage == null && getSourceViewer() != null)
				fOutlinePage= createOutlinePage();
			return fOutlinePage;
		}
		
		return super.getAdapter(required);
	}

}
