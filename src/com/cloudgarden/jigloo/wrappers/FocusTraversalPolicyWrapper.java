/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.resource.ArrayFocusTraversalPolicyInternal;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FocusTraversalPolicyWrapper implements IWrapper  {

    private FocusTraversalPolicy ftp;
    private Component[] list;
    private FormComponent comp;
    private FormComponent[] array;
    
	public FocusTraversalPolicyWrapper(Node node, FormComponent comp) {
	    throw new RuntimeException("Not supported");
//		Node cn = NodeUtils.getChildNodeByName(getXMLId(), node);
//		if (cn == null)
//			cn = node;
//		try {
//			String val = NodeUtils.getAttribute("value", cn);
//			array = getArrayFromString(val);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.comp = comp;
	}

	public FocusTraversalPolicyWrapper(Object focusList, FormComponent comp) {
	    if(focusList instanceof FormComponent[]) {
	        array = (FormComponent[]) focusList;
	        list = new Component[array.length];
	        for (int i = 0; i < list.length; i++) {
                list[i] = array[i].getComponent();
            }
	        ftp = new ArrayFocusTraversalPolicyInternal(list);
	    } else if(focusList instanceof FocusTraversalPolicy) {
	        this.ftp = (FocusTraversalPolicy)focusList;
	        if(ftp instanceof ArrayFocusTraversalPolicyInternal) {
	            list = ((ArrayFocusTraversalPolicyInternal)ftp).getComponentArray();
	        } else {
		        Vector compv = new Vector();
		        Container cont = (Container)comp.getComponent();
		        Component c1 = ftp.getFirstComponent(cont);
		        Component c2 = ftp.getLastComponent(cont);
		        if(c1 != null)
		            compv.add(c1);
		        while(c1 != null && !c1.equals(c2)) {
		            c1 = ftp.getComponentAfter(cont, c1);
		            if(!compv.contains(c1))
		                compv.add(c1);
		            else
		                break;
		        }
		        list = new Component[compv.size()];
		        for (int i = 0; i < list.length; i++) {
		            list[i] = (Component) compv.elementAt(i);
	            }
	        }
	        if(list == null)
	            list = new Component[0];
	        array = new FormComponent[list.length];
	        for (int i = 0; i < list.length; i++) {
	            FormComponent fc = comp.getEditor().getJavaCodeParser().getFormComponentWithObject(list[i]);
	            array[i] = fc;
	        }
	    }
		this.comp = comp;
	}

	public void generateXML(
		Element prop,
		Document document,
		String indent1,
		String indent2) {
		prop.setAttribute("type", getXMLId());
		Element loc = document.createElement(getXMLId());
		prop.appendChild(document.createTextNode("\n" + indent1 + indent2));
		prop.appendChild(loc);
		loc.setAttribute("value", JiglooUtils.toString(ftp, true));
		prop.appendChild(document.createTextNode("\n" + indent1));
	}

	public Object getValue(Object obj) {
		return ftp;
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		jcm.addImport(ArrayFocusTraversalPolicyInternal.class.getName());
		String code = "new ArrayFocusTraversalPolicy(new java.awt.Component[] {";
		if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (i != 0)
                    code += ", ";
                code += ((FormComponent)array[i]).getNameOrMethodInCode();
            }
		}
		return code + "})";
	}

	public String toString() {
		if (ftp == null)
			return "[  ]";
		return JiglooUtils.toString(array, true);
	}

	public IWrapper getCopy(FormComponent fc) {
		if (fc == null)
			fc = comp;
		return new FocusTraversalPolicyWrapper(new ArrayFocusTraversalPolicyInternal(list), fc);
	}

	public String getXMLId() {
		return "FocusTraversalPolicy";
	}

	public FormComponent[] getArrayValue() {
	    return array;
	}

}
