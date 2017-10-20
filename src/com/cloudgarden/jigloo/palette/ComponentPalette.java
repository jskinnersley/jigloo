package com.cloudgarden.jigloo.palette;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.harness.HarnessManager;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.preferences.PaletteComposite;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.JiglooUtils;

public class ComponentPalette extends org.eclipse.swt.widgets.Composite {

	private static final int SWING_PALETTE = 1;
	private static final int SWT_PALETTE = 2;
	private static final int ANDROID_PALETTE = 3;
	private static final int CWT_PALETTE = 4;
	
	private CButton lastPressed = null;

	private CTabFolder swtTabFolder;
	private CTabFolder swingTabFolder;
	private CTabFolder cwtTabFolder;
	private CTabFolder androidTabFolder;
	
	private boolean initialized = false;
	
	private HashMap palettes = new HashMap();

	private FormEditor editor;

	public ComponentPalette(Composite parent, int style, FormEditor editor) {
		super(parent, style);
		this.editor = editor;
	}

//	public ComponentPalette(Composite parent, int style) {
//		super(parent, style);
//	}
	
	/**
	 * 
	 * @param name
	 * @param type - 1 for Swing, 2 for SWT, 3 for CWT
	 */
	public void addPalette(String name, int type) {

		initGUI();
		
		CTabFolder cont = null;
	    
	    if(type == SWING_PALETTE) {
	    	if(!jiglooPlugin.canUseSwing())
	    		return;
	    	cont = swingTabFolder;
	    }
	    
	    if(type == SWT_PALETTE)
	        cont = swtTabFolder;
	    else if(type == CWT_PALETTE)
	        cont = cwtTabFolder;
	    else if(type == ANDROID_PALETTE)
	        cont = androidTabFolder;
	    
	    if(cont == null)
	    	return;
	    
	    CTabItem tab = new CTabItem(cont, SWT.NULL);
	    ScrollableToolbar tb = new ScrollableToolbar(cont, SWT.NULL);
	    palettes.put(name+"%^$"+type, tb);
	    
		tab.setControl(tb);
		tab.setText(name);

		cont.setSelection(0);
		tb.layout();
		cont.layout();
	}
	
	public void addToPalette(String name, int type, final Action action) {
		if(action == null)
			return;
		try {
		    ScrollableToolbar tb = (ScrollableToolbar)palettes.get(name+"%^$"+type);
		    if(tb == null) {
		        System.err.println("Unable to find palette "+name+"%^$"+type);
		        return;
		    }
		    CButton cb = tb.addAction(action, true);
		    cb.addMouseListener(new MouseAdapter() {
		        public void mouseDoubleClick(MouseEvent e) {
		            CButton but = (CButton) e.getSource();
		            if (!but.isLocked())
		                editor.setCurrentAction(null);
		        }
		        public void mouseDown(MouseEvent e) {
		            CButton but = (CButton) e.getSource();
		            editor.setCurrentAction(action);
		            if (lastPressed != null && !lastPressed.equals(but))
		                lastPressed.release();
		            lastPressed = but;
		        }
		    });
		} catch(Throwable t) {
		    t.printStackTrace();
		}
	}
	
	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI() {
		try {
			if(initialized)
				return;
			initialized = true;
			
			preInitGUI();

			StackLayout thisLayout = new StackLayout();
			this.setLayout(thisLayout);
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			
			if(jiglooPlugin.canUseSwing())
			    swingTabFolder = new CTabFolder(this, SWT.NULL);
			
			swtTabFolder = new CTabFolder(this, SWT.NULL);
			
			if(HarnessManager.ENABLE_ANDROID)
				androidTabFolder = new CTabFolder(this, SWT.NULL);
						
			postInitGUI();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void preInitGUI() {}

	public void postInitGUI() {
		Color darkblue = ColorManager.getColor(120, 120, 250);
		Color blue = ColorManager.getColor(180, 180, 250);
		Color grey = ColorManager.getColor(200, 200, 250);
		Color white = ColorManager.getColor(255, 255, 255);
		try {
		    swtTabFolder.setSelectionBackground(new Color[] { darkblue, blue, grey }, new int[] { 50, 100 }, true);
			//swtTabFolder.setSelectionForeground(white);
			if(swingTabFolder != null) {
			    swingTabFolder.setSelectionBackground(new Color[] { darkblue, blue, grey }, new int[] { 50,100 }, true);
			    //swingTabFolder.setSelectionForeground(white);
			}
			if(androidTabFolder != null) {
				androidTabFolder.setSelectionBackground(new Color[] { darkblue, blue, grey }, new int[] { 50, 100 }, true);
				//androidTabFolder.setSelectionForeground(white);
			}
		} catch(Throwable t) {
			swtTabFolder.setBackground(blue);
			swtTabFolder.setForeground(white);
			if(swingTabFolder != null)  {
				swingTabFolder.setBackground(blue);
				swingTabFolder.setForeground(white);
			}
		}
	}

	public void setMode(int mode) {
		initGUI();
		StackLayout sl = (StackLayout) getLayout();
		if (mode == FormEditor.MODE_AWT_SWING)
			sl.topControl = swingTabFolder;
		else if (mode == FormEditor.MODE_SWT)
			sl.topControl = swtTabFolder;
		else if (mode == FormEditor.MODE_ANDROID)
			sl.topControl = androidTabFolder;
		else if (mode == FormEditor.MODE_CWT && cwtTabFolder != null)
			sl.topControl = cwtTabFolder;
		layout();
	}

	public void updateUI() {

	    Iterator it = palettes.keySet().iterator();
	    while(it.hasNext()) {
	        ScrollableToolbar tb = (ScrollableToolbar) palettes.get(it.next());
	        tb.updateUI();
	    }
	    
	    pack(true);
	    Point sz= computeSize(-1, -1);
	    ((GridData)getLayoutData()).heightHint = sz.y;
	    ((GridData)getLayoutData()).verticalAlignment = GridData.BEGINNING;
	    getParent().layout();
	    
	}

	public boolean release() {
	    boolean locked = lastPressed != null && lastPressed.isLocked();
		if (!locked) {
			if(lastPressed != null)
			    lastPressed.release();
			lastPressed = null;
		}
		return !locked;
	}

    /**
     * Empty everything!
     */
    public void clear() {
        swtTabFolder.dispose();
        if(androidTabFolder != null)
        	androidTabFolder.dispose();
		if(swingTabFolder != null)
		    swingTabFolder.dispose();
        palettes.clear();
        initialized = false;
        initGUI();
    }

	public static String[][][] getPaletteClasses(String coded) {
	
		boolean paletteChanged = false;
		if(coded == null) {
			coded = jiglooPlugin.getDefault().getStringPreference(MainPreferencePage.P_PALETTE_CLASSES);
	
			if(coded.indexOf("org.jdesktop.layout.GroupLayout") < 0) {
				coded = JiglooUtils.replace(coded, "java.awt.GridBagLayout|0||n|"+PaletteComposite.PREF_SEP_2, 
						"java.awt.GridBagLayout|0||n|"+PaletteComposite.PREF_SEP_2+
						"Layout|1|org.jdesktop.layout.GroupLayout|0||n|"+PaletteComposite.PREF_SEP_2);
				paletteChanged = true;
			}
			if(coded.indexOf("net.miginfocom.swing.MigLayout") < 0) {
				coded = JiglooUtils.replace(coded, "org.jdesktop.layout.GroupLayout|0||n|"+PaletteComposite.PREF_SEP_2, 
						"org.jdesktop.layout.GroupLayout|0||n|"+PaletteComposite.PREF_SEP_2+
						"Layout|1|net.miginfocom.swing.MigLayout|0||n|"+PaletteComposite.PREF_SEP_2);
				paletteChanged = true;
			}
			if(coded.indexOf("net.miginfocom.swt.MigLayout") < 0) {
				coded = JiglooUtils.replace(coded,
						"org.eclipse.swt.layout.GridLayout|0||n|"+PaletteComposite.PREF_SEP_2, 
						"org.eclipse.swt.layout.GridLayout|0||n|"+PaletteComposite.PREF_SEP_2+
						"Layout|2|net.miginfocom.swt.MigLayout|0||n|"+PaletteComposite.PREF_SEP_2);
				paletteChanged = true;
			}
			if(coded.indexOf("javax.swing.AbstractAction") < 0) {
				coded = JiglooUtils.replace(coded, "javax.swing.JFormattedTextField|0||n|"+PaletteComposite.PREF_SEP_2, 
						"javax.swing.AbstractAction|0||n|"+PaletteComposite.PREF_SEP_2+
						"More Components|1|javax.swing.JFormattedTextField|0||n|"+PaletteComposite.PREF_SEP_2);
				paletteChanged = true;
			}
			if(coded.indexOf("javax.swing.JFrame") < 0) {
				coded = JiglooUtils.replace(coded, "javax.swing.JDialog|0||n|"+PaletteComposite.PREF_SEP_2, 
						"javax.swing.JDialog|0||n|"+PaletteComposite.PREF_SEP_2+
						"Containers|1|javax.swing.JFrame|0||n|"+PaletteComposite.PREF_SEP_2);
				paletteChanged = true;
			}
							
	        if(paletteChanged)
	        	jiglooPlugin.getDefault().setPreference(MainPreferencePage.P_PALETTE_CLASSES, coded);
		}
		
	    String[] mparts = JiglooUtils.split(PaletteComposite.PREF_SEP_3, jiglooPlugin.trimEnd(coded, PaletteComposite.PREF_SEP_3));
	
	    String[] p1 = JiglooUtils.split(PaletteComposite.PREF_SEP_2, jiglooPlugin.trimEnd(mparts[0], PaletteComposite.PREF_SEP_2));
		
	    String[][] p2 = new String[p1.length][];
	    for (int i = 0; i < p1.length; i++) {
	        String[] pp = JiglooUtils.split(PaletteComposite.PREF_SEP_1, jiglooPlugin.trimEnd(p1[i], PaletteComposite.PREF_SEP_1));
	        p2[i] = pp;
	    }
	    String[][][] decoded = new String[2][][];
	    decoded[0] = p2;
	    p1 = JiglooUtils.split(PaletteComposite.PREF_SEP_2, jiglooPlugin.trimEnd(mparts[1], PaletteComposite.PREF_SEP_2));
	    p2 = new String[p1.length][];
	    for (int i = 0; i < p1.length; i++) {
	        String[] pp = JiglooUtils.split(PaletteComposite.PREF_SEP_1, p1[i], true);
	        p2[i] = pp;
	    }
	    decoded[1] = p2;
	    return decoded;
	}
}
