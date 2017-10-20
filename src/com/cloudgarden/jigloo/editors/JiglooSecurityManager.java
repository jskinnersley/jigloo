/*
 * Created on Oct 6, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.editors;

import java.security.Permission;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JiglooSecurityManager extends SecurityManager {
    public void checkExit(int status) {
        throw new JiglooSecurityException("System.exit called while populating Jigloo elements");
    }
    public void checkPermission(Permission perm, Object context) {
    }
    public void checkPermission(Permission perm) {
    }
    
}

