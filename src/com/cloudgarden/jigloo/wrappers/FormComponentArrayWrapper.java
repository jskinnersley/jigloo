/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.Component;

import org.eclipse.swt.widgets.Control;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.util.ArrayUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormComponentArrayWrapper extends ArrayWrapper {

	public FormComponentArrayWrapper(Node node, FormComponent comp) {
		super(node, comp);
	}

	public FormComponentArrayWrapper(Object array, FormComponent fc) {
		super(array, fc);
	}

	public IWrapper getCopy(FormComponent fc) {
		if (fc == null)
			fc = comp;
		return new FormComponentArrayWrapper(array, fc);
	}

	public String getXMLId() {
		return "FormComponentArray";
	}

	public Class getArrayClass() {
	    if(comp.isSwing())
	        return Component.class;
	    else
	        return Control.class;
	}
	
	public String getJavaConstructor(IJavaCodeManager jcm) {
	    if(array == null)
	        return null;
	    if(array instanceof Control[])
	        return null;
		String code = "new " + getArrayClass().getName() + "[] {";
		if (array != null) {
			code += JiglooUtils.toString(array, false);
		}
		return code + "}";
	}

	public Object getArrayFromString(String str) {
		return ArrayUtils.stringToFormComponentArray(str, comp.getEditor().getJavaCodeParser());
	}

}
