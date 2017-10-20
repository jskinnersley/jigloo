/*
 * Created on Jul 3, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.outline;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;

/*
 * The content provider class is responsible for
 * providing objects to the view. It can wrap
 * existing objects in adapters or simply return
 * objects as-is. These objects may be sensitive
 * to the current input of the view, or ignore
 * it and always show the same content 
 * (like Task List, for example).
 */

public class TreeObject implements IAdaptable {
	protected String name;
	private TreeParent parent;
	protected FormComponent comp;

	public TreeObject(String name) {
		this.name = name;
	}

	public TreeObject(FormComponent comp) {
		this.comp = comp;
		if (comp != null) {
			name = comp.getDisplayName();
		}
		if (name == null)
			name = "No Name";
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public FormComponent getFormComponent() {
		return comp;
	}

	public String getDisplayName() {
		if (comp == null)
			return name;
		name = comp.getDisplayName();
		if (name == null)
			name = "No Name";
		return name;
	}

	public String getName() {
		if (comp == null)
			return name;
		return comp.getName();
	}

	protected boolean needsRefresh;

	public boolean needsRefresh() {
	    return needsRefresh;
	}

	public void setNeedsRefresh(boolean b) {
        needsRefresh = b;
    }

	public void setNeedsRefresh(TreeObject comparedTo) {
	    if(comparedTo == null) {
	        if(parent != null)
	            parent.setNeedsRefresh(true);
	        else
	            setNeedsRefresh(true);
	        return;
	    }
        if(parent != null)
            parent.setNeedsRefresh(false);
        else
            setNeedsRefresh(false);
	    String dn = getDisplayName();
	    String dn2 = comparedTo.getDisplayName();
	    if(!dn.equals(dn2)) {
	        if(parent != null)
	            parent.setNeedsRefresh(true);
	        else
	            setNeedsRefresh(true);
	        return;
	    }
	}
	
	public boolean hasParent(TreeParent treeParent) {
		if (parent == null)
			return false;
		if (parent.equals(treeParent))
			return true;
		return parent.hasParent(treeParent);
	}

	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getDisplayName();
	}
	public Object getAdapter(Class key) {
		if (key == IPropertySource.class || key == FormComponent.class) {
			return comp;
		}
		return null;
	}

	/**
	 * @param object
	 */
	public void releaseFormComponent() {
		comp = null;
	}
}