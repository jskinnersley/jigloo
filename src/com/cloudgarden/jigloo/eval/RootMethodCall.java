/*
 * Created on Feb 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;

import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * A class which holds a method call on the root object when parsing, until the
 * root component is created.
 */
public class RootMethodCall {

    private String methodName;
    private Class returnClass;
    private Object[] args;
    private FormEditor editor;
    
    /**
     * @param mdec
     * @param args
     */
    public RootMethodCall(String methodName, Object[] args, Class returnClass, FormEditor editor) {
        this.methodName = methodName;
        this.returnClass = returnClass;
        this.args = args;
        this.editor = editor;
    }
    /**
     * @param rootComponent
     * @return
     */
    public Object invoke() {
        return editor.getRootComponent().invokeOnControl(editor.getRootObject(), methodName, args);
    }
    /**
     * @return
     */
    public Class getReturnClass() {
        return returnClass;
    }

}
