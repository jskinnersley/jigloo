/*
 * Created on Mar 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayoutSupport;

import java.util.Vector;

import javax.swing.SwingConstants;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.actions.FormAlignAction;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayout.LayoutStyle;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupLayoutUtils {
    private static LayoutStyle LS = LayoutStyle.getSharedInstance();

    public static void handleGroupLayoutMultiAlign(int side, FormEditor editor) {
        if(side == FormAlignAction.ALIGN_BOTTOM) {
            alignElements(GroupLayout.TRAILING, true, editor);
        } else if(side == FormAlignAction.ALIGN_H_CENTER) {
            alignElements(GroupLayout.CENTER, false, editor);
        } else if(side == FormAlignAction.ALIGN_V_CENTER) {
            alignElements(GroupLayout.BASELINE, true, editor);
        } else if(side == FormAlignAction.ALIGN_TOP) {
            alignElements(GroupLayout.LEADING, true, editor);
        } else if(side == FormAlignAction.ALIGN_LEFT) {
            alignElements(GroupLayout.LEADING, false, editor);
        } else if(side == FormAlignAction.ALIGN_RIGHT) {
            alignElements(GroupLayout.TRAILING, false, editor);
        } else if(side == FormAlignAction.ALIGN_SAME_HEIGHT) {
            alignSizes(true, editor);
        } else if(side == FormAlignAction.ALIGN_SAME_WIDTH) {
            alignSizes(false, editor);
        }
    }

    public static void alignSizes(boolean verticalLayout, FormEditor editor) {
        FormComponent sel0 = editor.getSelectedComponent();
        Rectangle b = sel0.getBoundsRelativeToRoot();
        int size = verticalLayout ? b.height : b.width;
        LayoutWrapper lw = sel0.getParent().getLayoutWrapper();
        LayoutGroup lg0 = null;
        if(verticalLayout) {
            lg0 = lw.getVGroup();
        } else {
            lg0 = lw.getHGroup();
        }
        
        Vector fcs = editor.getSelectedComponents();
        FormComponent[] linked = new FormComponent[fcs.size()];
        for (int i = 0; i < linked.length; i++) {
            linked[i] = (FormComponent) fcs.elementAt(i);
        }
        lw.linkSize(verticalLayout ? SwingConstants.VERTICAL : SwingConstants.HORIZONTAL,
                linked, true);
        updateGroup(lg0, editor, verticalLayout, lw);
    }
    
    public static void alignElements(int align, boolean verticalLayout, FormEditor editor) {
	    try {
	        FormComponent sel0 = editor.getSelectedComponent();
	        LayoutWrapper lw = sel0.getParent().getLayoutWrapper();
	        LayoutGroup lg0 = null;
	        LayoutGroup lg = null;
	        if(verticalLayout) {
	            lg0 = lw.getVGroup();
	        } else {
	            lg0 = lw.getHGroup();
	        }
	        
	        lg = lg0.getGroupContaining(sel0);
	        
            if (lg.isSequential()) {
                int pos = lg.getIndexOf(sel0);
                LayoutGroup lg2 = lg.addGroup(pos, true, align);
                GroupElement selElem = lg.remove(sel0);
	            lg2.add(0, selElem, align, 0);
	            lg = lg2;
            }
	        Vector fcs = editor.getSelectedComponents();
	        for(int i=0; i< fcs.size(); i++) {
	            FormComponent  sel = editor.getSelectedComponent(i);
	            if(!sel.equals(sel0)) {
	                LayoutGroup lg2 = lg0.getGroupContaining(sel);
	                if(lg2 != null && !lg.equals(lg2)) {
	                    lg.add(-1, lg2.remove(sel), align, 0);
	                }
	            }
                lg.setAlignment(sel, align);
	        }

	        lg0.cleanup();

	        updateGroup(lg0, editor, verticalLayout, lw);
	        
	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	}

    public static void updateGroup(LayoutGroup lg0, FormEditor editor, boolean verticalLayout, LayoutWrapper lw) {
//    	lw.recreateLayoutManager();
//    	GroupLayout gl = (GroupLayout) lw.getObject();
//    	lw.getVGroup().clear();
//    	lw.getVGroup().populateGroup();
//    	lw.getHGroup().clear();
//    	lw.getHGroup().populateGroup();
    	
    	lw.recreateLayoutManager();
    	lw.refreshGroupLayout();
        lg0.clear();
        GroupLayout gl = (GroupLayout) lw.getObject();
        if(verticalLayout)
	        gl.setVerticalGroup(lg0.populateGroup());
        else
            gl.setHorizontalGroup(lg0.populateGroup());

        JavaCodeParser jcp = editor.getJavaCodeParser();
        lg0.repairInCode(jcp);

        editor.setDirtyAndUpdate(true, true);
    }

	public static boolean isGroupLayoutGroup(Class cls) {
		return "org.jdesktop.layout.GroupLayout$Group".equals(cls.getName())
	    || "javax.swing.GroupLayout$Group".equals(cls.getName()) 
	    || "com.cloudgarden.jigloo.groupLayout.GroupLayout$Group".equals(cls.getName());
	}

	public static String convertUnavailableClassName(String clsName) {
		if(clsName == null)
			return null;
	    if(clsName.startsWith("org.jdesktop.layout")) {
	        clsName = JiglooUtils.replace(clsName, "org.jdesktop.layout","com.cloudgarden.jigloo.groupLayout");
	    }
	    
	    if(clsName.equals("javax.swing.LayoutStyle$ComponentPlacement")
	    		|| clsName.equals("LayoutStyle$ComponentPlacement")) {
	        clsName =  "com.cloudgarden.jigloo.groupLayout.ComponentPlacement";
	    }
	    if(clsName.equals("javax.swing.GroupLayout$Alignment")
	    		|| clsName.equals("GroupLayout$Alignment")) {
	        clsName =  "com.cloudgarden.jigloo.groupLayout.Alignment";
	    }
	    if(clsName.equals("javax.swing.GroupLayout")) {
	        clsName =  "com.cloudgarden.jigloo.groupLayout.GroupLayout";
	    }
	    if(clsName.equals("javax.swing.LayoutStyle")) {
	        clsName =  "com.cloudgarden.jigloo.groupLayout.LayoutStyle";
	    }
	    return clsName;
	}

	public static String unconvertUnavailableClassName(String clsName) {
		if(clsName == null)
			return null;
        if(clsName.equals("com.cloudgarden.jigloo.groupLayout.ComponentPlacement")) {
            clsName =  "javax.swing.LayoutStyle$ComponentPlacement";
        } else if(clsName.equals("com.cloudgarden.jigloo.groupLayout.Alignment")) {
            clsName =  "javax.swing.GroupLayout$Alignment";
        } else if(clsName.equals("com.cloudgarden.jigloo.groupLayout.GroupLayout")) {
            clsName =  "javax.swing.GroupLayout";
        } else if(clsName.equals("com.cloudgarden.jigloo.groupLayout.LayoutStyle")) {
            clsName =  "javax.swing.LayoutStyle";
        } else if(clsName.startsWith("com.cloudgarden.jigloo.groupLayout")) {
            clsName = JiglooUtils.replace(clsName, "com.cloudgarden.jigloo.groupLayout", "org.jdesktop.layout");
        }
		return clsName;
	}
            

}
