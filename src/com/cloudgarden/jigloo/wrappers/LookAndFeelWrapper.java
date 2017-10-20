/*
 * Created on Sep 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.wrappers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LookAndFeelWrapper {

    LookAndFeel laf;
    public String name;
    public String className;
    public String path;
    public boolean isDefault;
    
//    public LookAndFeelWrapper(LookAndFeel laf) {
//        this.laf = laf;
//        name = laf.getName();
//        className = laf.getClass().getName();
//    }
    
    public LookAndFeelWrapper(String string) {
        String[] vals = JiglooUtils.split("~!", string);
        if(vals == null || vals.length != 4) {
            name = "INVALID";  
        } else {
	        className = vals[0];
	        name = vals[1];
	        path = vals[2];
	        if(path.equals("null"))
	            path = null;
	        isDefault = vals[3].equals("Y");
        }
        init();
    }

    public LookAndFeelWrapper(String className, String name) {
        this.className = className;
        this.name = name;
        init();
    }

    public LookAndFeelWrapper(String className, String name, String path) {
        this.className = className;
        this.name = name;
        this.path = path;
        init();
    }

    private void init() {
        LookAndFeel instLaf = UIManager.getLookAndFeel();
        if(instLaf != null && instLaf.getClass().getName().equals(className))
            laf = instLaf;
    }
    
    public boolean equals(Object obj) {
        if(!(obj instanceof LookAndFeelWrapper))
            return false;
        LookAndFeelWrapper lafw = (LookAndFeelWrapper) obj;
        if(className != null && className.equals(lafw.className))
            return true;
        return false;
    }
    
    public String serialize() {
        return className+"~!"+name
        +"~!"+path+"~!"+(isDefault ? "Y" : "N");
    }

    public String toString() {
        return name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public static String convertGoodiesLnF(String lnf) {
        if(lnf.startsWith("com.jgoodies.plaf.")) {
            String lnf2 = JiglooUtils.replace(lnf, "com.jgoodies.plaf.", "com.jgoodies.looks.");
            try {
                jiglooPlugin.getDefault().getDescriptor().getPluginClassLoader().loadClass(lnf2);
                return lnf2;
            } catch(Throwable t) {
            }
        }
        return lnf;
    }
        
    public String getClassName() {
        className = convertGoodiesLnF(className);
        return className;
    }
    
    public String getName() {
        return name;
    }
    
    public void setLookAndFeel(LookAndFeel laf) {
        this.laf = laf;
    }
    
    public LookAndFeel getLookAndFeel() {
        if(laf == null) {
            Class cls;
            try {
                if(path != null) {
                    URLClassLoader cl = new URLClassLoader(new URL[] {new File(path).toURL()});
                    cls = cl.loadClass(className);
                    laf = (LookAndFeel) cls.newInstance();
                } else {
                    String cn2 = getClassName();
//                    FormEditor ed = jiglooPlugin.getActiveEditor();
//                    cls = ed.loadClass(cn2);
                    cls = getClass().getClassLoader().loadClass(cn2);
//                    cls = jiglooPlugin.getDefault().getDescriptor().getPluginClassLoader().loadClass(cn2);
                    laf = (LookAndFeel) cls.newInstance();
                }
            } catch(Throwable t) {
                System.err.println("Unable to find L&F "+getClassName()+", "+path+": "+t);
//                t.printStackTrace();
            }
        }
       return laf;
    }
}
