/*
 * Created on May 30, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.appFramework;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertyChange {
    public String key;
    public String oldValue, newValue;
    
    /**
     * @param key
     * @param oldVal
     * @param newVal
     */
    public PropertyChange(String key, String oldVal, String newVal) {
        this.key = key;
        oldValue = oldVal;
        newValue = newVal;
    }

    public static Vector getChanges(Properties oldProps, Properties newProps) {
        Vector changes = new Vector();
        Iterator it;
        if(oldProps != null) {
            it = oldProps.keySet().iterator();
            while(it.hasNext()) {
                String key = (String)it.next();
                String oldVal = (String) oldProps.get(key);
                String newVal = (String) newProps.get(key);
                if(newVal == null || oldVal != newVal)
                    changes.add(new PropertyChange(key, oldVal, newVal));
            }
        }
        it = newProps.keySet().iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            String oldVal = null;
            if(oldProps != null)
                oldVal = (String) oldProps.get(key);
            String newVal = (String) newProps.get(key);
            if(oldVal == null)
                changes.add(new PropertyChange(key, oldVal, newVal));
        }
        return changes;
    }
    
    public void undo(Properties propMap) {
        if(oldValue == null)
            propMap.remove(key);
        else
            propMap.setProperty(key, oldValue);
    }

    public void redo(Properties propMap) {
        if(newValue == null)
            propMap.remove(key);
        else
            propMap.setProperty(key, newValue);
    }
    
    public static void undo(Vector changes, Properties propMap) {
        for(int i=0; i< changes.size(); i++)
            ((PropertyChange)changes.elementAt(i)).undo(propMap);
    }

    public static void redo(Vector changes, Properties propMap) {
        for(int i=0; i< changes.size(); i++)
            ((PropertyChange)changes.elementAt(i)).redo(propMap);
    }
}
