/*
 * Created on Jun 26, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OrderedMap extends HashMap {

	private Vector keys= new Vector();

	public OrderedMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public OrderedMap(int initialCapacity) {
		super(initialCapacity);
	}

	public OrderedMap() {
		super();
	}

	public OrderedMap(Map m) {
		super(m);
	}
	

	public Object put(Object key, Object value) {
		if(!keys.contains(key))
			keys.add(key);
		return super.put(key, value);
	}

	public Object remove(Object key) {
		keys.remove(key);
		return super.remove(key);
	}

	public Object getKey(int pos) {
		return keys.elementAt(pos);
	}
}
