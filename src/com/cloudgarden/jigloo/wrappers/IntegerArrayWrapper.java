/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.util.ArrayUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IntegerArrayWrapper extends ArrayWrapper {

	public IntegerArrayWrapper(Node node, FormComponent comp) {
		super(node, comp);
	}

	public IntegerArrayWrapper(Object array, FormComponent fc) {
		super(array, fc);
	}

	public IWrapper getCopy(FormComponent fc) {
		if (fc == null)
			fc = comp;
		return new IntegerArrayWrapper(array, fc);
	}

	public String getXMLId() {
		return "IntegerArray";
	}

	public Class getArrayClass() {
		return int.class;
	}

	public Object getArrayFromString(String str) {
		return ArrayUtils.stringToIntArray(str);
	}

}
