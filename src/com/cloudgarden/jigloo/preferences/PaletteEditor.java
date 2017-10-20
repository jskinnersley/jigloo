/*
 * Created on Sep 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.preferences;

import java.io.File;
import java.util.Vector;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.FileUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaletteEditor extends FieldEditor {

    PaletteComposite control;
    FormEditor editor;
    
    /**
     * Editor for custom classes
     * @param name is MainPreferencePage.P_PALETTE_CLASSES
     */
    public PaletteEditor(String name, Composite parent) {
    	init(name, "");
    	control = getControl(parent);
    	doFillIntoGrid(parent, 1);
    }

    private PaletteComposite getControl(Composite parent) {
        if(control == null)
            control = new PaletteComposite(parent, SWT.NULL);
        return control;
    }
    
    protected void adjustForNumColumns(int numColumns) {
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
        load(getPreferenceStore().getString(getPreferenceName()));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
     */
    protected void doLoadDefault() {
        load(getPreferenceStore().getDefaultString(getPreferenceName()));
        control.setBeanInfoPath(getPreferenceStore().getDefaultString(MainPreferencePage.P_BEAN_INFO_FOLDER));
        control.setBeanIconPath(getPreferenceStore().getDefaultString(MainPreferencePage.P_BEAN_ICON_FOLDER));
    }

    private void load(String val) {
        control.loadPalettesFromString(val);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#doStore()
     */
    protected void doStore() {
        try {
        	if(jiglooPlugin.getActiveEditor() != null) {
        		Vector jars = control.getJars();
        		for(int i=0; i<jars.size(); i++)
        			FileUtils.addJarToClassPath(jiglooPlugin.getActiveEditor().getJavaProject(), new File((String)jars.get(i)));
        	}

            String val = control.getPrefString(true);
            //        System.out.println("STORING "+val);
            getPreferenceStore().setValue(getPreferenceName(), val);
            
            val = control.getBeanInfoPath();
            getPreferenceStore().setValue(MainPreferencePage.P_BEAN_INFO_FOLDER, val);

            val = control.getBeanIconPath();
            getPreferenceStore().setValue(MainPreferencePage.P_BEAN_ICON_FOLDER, val);
        } catch(Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
     */
    public int getNumberOfControls() {
        return 1;
    }

}
