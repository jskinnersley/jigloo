/*
 * Created on Mar 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayoutSupport;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupLayoutConstraints {

    private boolean expands = false;
    private boolean anchorLeading = false;
    private FormComponent fc;
    
    public GroupLayoutConstraints(FormComponent fc, LayoutGroup lg) {
        this.fc = fc;
        expands = lg.isExpandable(fc);
        anchorLeading = lg.isAnchored(fc, true);
    }
    
    public void prep(LayoutGroup lg0) {
        LayoutGroup lg = lg0.getGroupContaining(fc);
        if(lg == null)
            return;
        GroupElement elem = lg.getElement(lg.getIndexOf(fc));
        if(elem != null) {
            elem.setAnchorFlag(anchorLeading ? LayoutGroup.FLAG_ANCHOR_LEADING : LayoutGroup.FLAG_ANCHOR_TRAILING);
            elem.setExpandFlag(expands ? LayoutGroup.FLAG_EXPAND : LayoutGroup.FLAG_NO_EXPAND);
        }
    }
    
    public void applyAnchor(LayoutGroup lg0) {
        LayoutGroup lg = lg0.getGroupContaining(fc);
        if(lg == null)
            return;
        lg.setAnchored(fc, true, anchorLeading, true);
    }
    
    public void applyExpand(LayoutGroup lg0) {
        LayoutGroup lg = lg0.getGroupContaining(fc);
        if(lg == null)
            return;
        lg.setExpand(fc, expands);
    }
    
	public void setAnchorLeading(boolean anchorLeading) {
		this.anchorLeading = anchorLeading;
	}
	
	public void setExpands(boolean expands) {
		this.expands = expands;
	}

	public FormComponent getFC() {
		return fc;
	}
}
