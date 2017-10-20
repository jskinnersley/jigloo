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
public class DoubleArrayWrapper extends ArrayWrapper {

	public DoubleArrayWrapper(Node node, FormComponent comp) {
		super(node, comp);
	}

	public DoubleArrayWrapper(Object array, FormComponent fc) {
		super(array, fc);
	}

	public IWrapper getCopy(FormComponent fc) {
		if (fc == null)
			fc = comp;
		return new DoubleArrayWrapper(array, fc);
	}

	public String getXMLId() {
		return "DoubleArray";
	}

	public Class getArrayClass() {
		return double.class;
	}

	public Object getArrayFromString(String str) {
		return ArrayUtils.stringToDoubleArray(str);
	}

}
