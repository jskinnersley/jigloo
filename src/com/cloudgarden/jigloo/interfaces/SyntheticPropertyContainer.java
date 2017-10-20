/*
 * Created on May 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.interfaces;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SyntheticPropertyContainer {

	public boolean setSynthProperty(FormComponent fc, String propName, Object value);
	
    /**
     * Place to handle synthetic properties. If a synth property is not handled here, return null
     */
	public Object getSynthProperty(FormComponent fc, String propName);
	
}
