/*
 * Created on Oct 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.layoutHandler;

import java.util.HashMap;

import com.cloudgarden.jigloo.interfaces.LayoutHandler;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LayoutHandlerManager {

    private static HashMap handlers = new HashMap();
    
    public void registerHandler(LayoutHandler handler, Class layoutClass) {
        handlers.put(layoutClass, handler);
    }
    
    public static LayoutHandler getHandler(Class layoutClass) {
        return (LayoutHandler) handlers.get(layoutClass);
    }

}
