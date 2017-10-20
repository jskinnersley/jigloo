/*
 * Created on Oct 3, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.forms;

import org.eclipse.jdt.core.dom.Expression;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EclipseFormCall {

    private String method;
    private Object[] params;
    private Expression exp;
    
    public EclipseFormCall(String method, Object[] params, 
            Expression exp) {
        this.method = method;
        this.params = params;
        this.exp = exp;
    }

    public Object invoke(FormComponent fc, FormComponent parent) {
        if(parent.isJFaceForm())
            return invokeOnForm(fc, parent);
        else
            return invokeOnToolkit(fc, parent);
    }
    
    private Object invokeOnToolkit(FormComponent fc, FormComponent parent) {
        if(params != null && method.startsWith("create"))
            params[0] = parent;
        return fc.getFormToolkitFC().invokeOnFormToolkit(method, params, null, fc);
    }
    private Object invokeOnForm(FormComponent fc, FormComponent parent) {
        return parent.invokeOnForm(method, params, null, fc);
    }

    /**
     * @param jcm
     * @param name
     * @return
     */
    public String getJavaCode(FormComponent fc, IJavaCodeManager jcm, String name) {
        String code = fc.getFormToolkitFC().getNameInCode()+"."+method+"(";
        for (int i = 0; i < params.length; i++) {
            if(i != 0)
                code += ", ";
            Object p = params[i];
            if(p instanceof FormComponent) {
                code += ((FormComponent)p).getNameInCode();
            } else if(p instanceof String) {
                String val = (String)fc.getPropertyValue("text");
                val = JiglooUtils.replace(val, "\t", "\\t");
                val = JiglooUtils.replace(val, "\n", "\\n");
                val = JiglooUtils.replace(val, "\r", "\\r");
                code += "\""+fc.getPropertyValue("text")+"\"";
            } else if(p instanceof Integer) {
                code  += fc.getStyleString();
            } else {
                code+= p.toString();
            }
        }
        code += ");";
        return code;
    }
}
