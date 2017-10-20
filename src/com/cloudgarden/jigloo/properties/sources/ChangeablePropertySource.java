/*
 * Created on Jan 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.properties.sources;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ChangeablePropertySource implements IPropertySource {

	private boolean sourceChanged = false;
	
	public void setSourceChanged() {
		sourceChanged = true;
		changed.clear();
	}
	
    Vector changed = new Vector();

    public boolean hasBeenChanged(FormComponent fc) {
    	if(!sourceChanged)
    		return false;
        return changed.contains(fc);
    }

    public void changeApplied(FormComponent fc) {
        changed.add(fc);
    }
    
}
