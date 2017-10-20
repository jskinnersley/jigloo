/*
 * Created on Jul 3, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.outline;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;

import com.cloudgarden.jigloo.FormComponent;

public class TreeParent extends TreeObject {
	private ArrayList children;

	public TreeParent(FormComponent comp) {
		super(comp);
		children = new ArrayList();
	}

	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
	}

	public TreeParent getCopy() {
	    TreeParent copy = new TreeParent(getDisplayName());
	    for(int i=0;i<getChildCount();i++) {
	        copy.addChild(((TreeParent)getChild(i)).getCopy());
	    }
	    return copy;
	}
	
	public void dispose() {
		children.clear();
		children = null;
	}
	
	public void setNeedsRefresh(TreeObject comparedTo) {
	    super.setNeedsRefresh(comparedTo);
	    if(comparedTo instanceof TreeParent) {
	        if(((TreeParent)comparedTo).getChildCount() != getChildCount()) {
	            needsRefresh = true;
	            return;
	        }
	        for(int i=0;i<getChildCount();i++) {
	            ((TreeParent)children.get(i)).setNeedsRefresh(((TreeParent)comparedTo).getChild(i));
	        }
	    }
	}

	public void refreshWhereNeeded(TreeViewer viewer) {
	    if(needsRefresh) {
	        viewer.refresh(this, true);
//	        System.out.println("REFRESHING NODE "+getDisplayName());
	    }
        for(int i=0;i<getChildCount();i++) {
            ((TreeParent)getChild(i)).refreshWhereNeeded(viewer);
        }
    }

	public int getChildCount() {
	    return children.size();
	}
	
	public void addChild(
		TreeObject sibling,
		TreeObject child,
		boolean before) {
		int pos = children.indexOf(sibling);
		if (before && pos != 0)
			pos--;
		if (!before && pos != children.size() - 1)
			pos++;
		children.add(pos, child);
		child.setParent(this);
	}
	public void addChild(TreeObject child) {
		if (child == null)
			return;
		children.add(child);
		child.setParent(this);
	}
	public void removeAll() {
		children.clear();
	}
	public void removeChild(TreeObject child) {
		if (child == null)
			return;
		children.remove(child);
		child.setParent(null);
	}
	
	public TreeObject getChild(int pos) {
	    if(pos < 0 || pos > children.size()-1)
	        return null;
		return (TreeObject) children.get(pos);
	}
	
	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}

//    public String toString() {
//        String str = super.toString() +"[";
//        for(int i=0;i<children.size();i++)
//            str+=getChild(i).toString()+";";
//        return str+"]";
//    }
}