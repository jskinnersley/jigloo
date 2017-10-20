/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Vector;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UndoablePropertyAction extends UndoableAction {

	private FormComponent comp;
	private Object propName, oldValue, newValue;
	private boolean wasSet;

	public UndoablePropertyAction(
		FormComponent comp,
		Object oldValue,
		Object newValue,
		boolean wasSet,
		Object id) {
		this.comp = comp;
		this.propName = id;
		this.wasSet = wasSet;
		this.oldValue = oldValue;
		this.newValue = newValue;
//				System.err.println(
//					"New UndoablePropertyAction, "
//						+ id
//						+ ", "
//						+ oldValue
//						+ ", "
//						+ newValue
//						+ ", "
//						+ comp);
	}

	public void undo() {
	    comp.setEditor(editor);
	    comp.setPropertyValue(propName, oldValue, true);
	    comp.updateInCode();
	}

	public void redo() {
	    //comp may have been disposed if a property is changed but parsing is disabled,
	    //so that the code is reparsed (and all components are disposed) before the
	    //property change is applied. In this case, comp's editor will be null, so make
	    //sure it is set here
	    comp.setEditor(editor);
	    comp.setPropertyValue(propName, newValue, true);
	    
	    if(comp.isRoot() 
	    		&& comp.getEditor().isShiftDown() 
	    		&& propName.equals("bounds")) {
	    	//if SHIFT is down and this is the root component, 
	    	//remove the "setSize" or "setPreferredSize" and insert a "pack" if needed
	    	comp.getJavaCodeParser().removePropSetter(comp, "size");
	    	try {
	    		comp.getMainClass().getMethod("pack", null);
		    	if(!comp.getJavaCodeParser().hasMethodCall(comp, "pack")) {
		    		comp.getJavaCodeParser().insertMethodAtEndOfInitGUI("pack();", comp, "pack");
		    	}
	    	} catch(Exception e) {}
	    	return;
	    }
	    
	    comp.updateInCode();
	}

	public String getDisplayName() {
		return "\"Set " + propName + "\"";
	}
	
	public Vector getFormComponents(boolean redo) {
		return null;
	}

	public FormComponent getFormComponent(boolean redo) {
		return comp;
	}

}
