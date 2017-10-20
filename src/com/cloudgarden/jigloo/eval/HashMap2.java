/*
 * Created on Mar 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HashMap2 extends HashMap {

	public Vector getKeysLike(Pattern pattern) {
		Vector keys = new Vector();
		Iterator it = keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(pattern.matches(key))
				keys.add(key);
		}
		return keys;
	}
}
