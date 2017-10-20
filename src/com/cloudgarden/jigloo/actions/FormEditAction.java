/*
 * Created on Jan 10, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.IPageSite;

import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormEditAction extends Action {
	IPageSite site;
	String type;

	public FormEditAction(IPageSite site, String text, String type) {
		this.site = site;
		this.type = type;
		setText(text);
		site.getActionBars().setGlobalActionHandler(type, this);
		site.getActionBars().updateActionBars();
	}

	public void run() {
		IEditorPart activeEditorPart = site.getPage().getActiveEditor();
		if (activeEditorPart instanceof FormEditor) {
			FormEditor fed = (FormEditor) activeEditorPart;
			fed.doAction(type);
		}
	}

}
