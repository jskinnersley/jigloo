/*
 * Created on Sep 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.preferences;

import java.util.Vector;

import javax.swing.UIManager;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LookAndFeelWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LookAndFeelEditor extends FieldEditor {

    LookAndFeelComposite control;
    
    /**
     * @param p_laf_classes
     * @param prefsComp
     */
    public LookAndFeelEditor(String name, Composite parent) {
    	init(name, "");
    	control = getControl(parent);
    	doFillIntoGrid(parent, 1);
    }

    private LookAndFeelComposite getControl(Composite parent) {
        if(control == null)
            control = new LookAndFeelComposite(parent, SWT.NULL);
        return control;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
     */
    protected void adjustForNumColumns(int numColumns) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite, int)
     */
    protected void doFillIntoGrid(Composite parent, int numColumns) {
        GridData gd = new GridData(GridData.FILL_BOTH);
    	gd.horizontalSpan = numColumns;
    	getControl(parent).setLayoutData(gd);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doLoad()
     */
    protected void doLoad() {
        String val = getPreferenceStore().getString(getPreferenceName());
        load(val);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
     */
    protected void doLoadDefault() {
        String val = getPreferenceStore().getDefaultString(getPreferenceName());
        load(val);
    }

    private void load(String val) {
        if(!jiglooPlugin.canUseSwing())
            return;
        
        UIManager.LookAndFeelInfo[] installed = 
            UIManager.getInstalledLookAndFeels();
        String[] vals = JiglooUtils.split(";", val);
        Vector items = new Vector();
        for (int i = 0; i < vals.length; i++) {
            items.add(new LookAndFeelWrapper(vals[i]));
        }
        for (int i = 0; i < installed.length; i++) {
            for (int j = 0; j < items.size(); j++) {
                LookAndFeelWrapper lafw = (LookAndFeelWrapper) items.elementAt(j);
                if(installed[i] != null
                        && installed[i].getClassName().equals(lafw.className))
                    installed[i] = null;
            }
            if(installed[i] != null)
                items.add(new LookAndFeelWrapper(installed[i].getClassName(), 
                        installed[i].getName()));
        }
        control.setLafs(items);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doStore()
     */
    protected void doStore() {
		String val = "";
		Vector items = control.getItems();
		for (int i = 0; i < items.size(); i++) {
			if (i != 0)
				val += ";";
			val += ((LookAndFeelWrapper)items.elementAt(i)).serialize();
		}
    	if (val != "") {
    		getPreferenceStore().setValue(getPreferenceName(), val);
    	}
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
     */
    public int getNumberOfControls() {
        return 1;
    }

}
