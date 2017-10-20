/*
 * Created on Sep 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.classloader;

import java.net.URL;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChainedClassLoader extends ClassLoader {
    
    private ClassLoader[] loaders;
    
    public ChainedClassLoader(ClassLoader[] loaders) {
        this.loaders = loaders;
    }
    
    public URL getResource(String name) {
        for (int i = 0; i < loaders.length; i++) {
            URL url = loaders[i].getResource(name);
            if (url != null)
                return url;
        }
        return null;
    }
    
    public Class loadClass(String name) throws ClassNotFoundException {
        ClassNotFoundException cnf = null;
        for (int i = 0; i < loaders.length; i++) {
            if(loaders[i] == null)
                continue;
            try {
                Class cls = loaders[i].loadClass(name);
                if (cls != null) {
//                    System.err.println("CLASS FOUND "+name);
                    return cls;
                }
            } catch (ClassNotFoundException e) {
                cnf = e;
            }
        }
//        System.err.println("CLASS NOT FOUND "+name);
        if (cnf != null)
            throw cnf;
        return null;
    }
}
