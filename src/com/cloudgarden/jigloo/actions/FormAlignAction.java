/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupLayoutUtils;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormAlignAction extends Action {
    private FormEditor editor;
    private int side;

    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_BOTTOM = 2;
    public static final int ALIGN_LEFT = 3;
    public static final int ALIGN_H_CENTER = 4;
    public static final int ALIGN_V_CENTER = 5;
    public static final int ALIGN_TO_GRID = 6;
    public static final int ALIGN_SPACE_VERT = 7;
    public static final int ALIGN_SPACE_HORIZ = 8;
    public static final int ALIGN_SAME_WIDTH = 9;
    public static final int ALIGN_SAME_HEIGHT = 10;
    
    public FormAlignAction(FormEditor editor, int side) {
        this.editor = editor;
        this.side = side;
        String lbl;
        if (side == ALIGN_TOP)
            setText("Align Top");
        if (side == ALIGN_RIGHT)
            setText("Align Right");
        if (side == ALIGN_BOTTOM)
            setText("Align Bottom");
        if (side == ALIGN_LEFT)
            setText("Align Left");
        if (side == ALIGN_H_CENTER)
            setText("Align Center (horiz)");
        if (side == ALIGN_V_CENTER)
            setText("Align Center (vert)");
        if (side == ALIGN_TO_GRID)
            setText("Snap to grid");
        if (side == ALIGN_SPACE_VERT)
            setText("Even space vertical");
        if (side == ALIGN_SPACE_HORIZ)
            setText("Even space horizontal");
        if (side == ALIGN_SAME_WIDTH)
            setText("Make same width");
        if (side == ALIGN_SAME_HEIGHT)
            setText("Make same height");
    }

    public void run() {
        FormComponent sel = editor.getSelectedComponent();
        if(sel == null || sel.getParent() == null)
            return;
        
        if(sel.getParent().usesGroupLayout()) {
            GroupLayoutUtils.handleGroupLayoutMultiAlign(side, editor);
            return;
        }
        
        Vector sels = editor.getSelectedComponents();
        sels = (Vector) sels.clone();
        int pos1 = -1, pos2 = -1;
        if (side == ALIGN_SPACE_VERT) {
            Collections.sort(sels, new Comparator() {
                public int compare(Object o1, Object o2) {
                    FormComponent fc1 = (FormComponent) o1;
                    FormComponent fc2 = (FormComponent) o2;
                    return fc1.getBounds().y - fc2.getBounds().y;
                }
            });
            pos1 = ((FormComponent) sels.firstElement()).getBounds().y;
            pos2 = ((FormComponent) sels.lastElement()).getBounds().y;
        } else if (side == ALIGN_SPACE_HORIZ) {
            Collections.sort(sels, new Comparator() {
                public int compare(Object o1, Object o2) {
                    FormComponent fc1 = (FormComponent) o1;
                    FormComponent fc2 = (FormComponent) o2;
                    return fc1.getBounds().x - fc2.getBounds().x;
                }
            });
            pos1 = ((FormComponent) sels.firstElement()).getBounds().x;
            pos2 = ((FormComponent) sels.lastElement()).getBounds().x;
        }
        Rectangle b0 = sel.getBounds();
        for (int i = 0; i < sels.size(); i++) {
            try {
                FormComponent comp = (FormComponent) sels.elementAt(i);

                Rectangle b = comp.getBounds();

                if (side == ALIGN_TO_GRID) {
                    editor.snapToGrid(b, comp, false);
                    comp.setBounds(b, true);
                    continue;
                } else if (side == ALIGN_SPACE_VERT) {
                    if (i == 0 || i == sels.size() - 1)
                        continue;
                    b.y = pos1 + i * (pos2 - pos1) / (sels.size() - 1);
                    comp.setBounds(b, true);
                    continue;
                } else if (side == ALIGN_SPACE_HORIZ) {
                    if (i == 0 || i == sels.size() - 1)
                        continue;
                    b.x = pos1 + i * (pos2 - pos1) / (sels.size() - 1);
                    comp.setBounds(b, true);
                    continue;
                }

                if (comp.equals(sel))
                    continue;

                if (side == ALIGN_TOP) {
                    b.y = b0.y;
                } else if (side == ALIGN_RIGHT) {
                    b.x = b0.x + b0.width - b.width;
                } else if (side == ALIGN_BOTTOM) {
                    b.y = b0.y + b0.height - b.height;
                } else if (side == ALIGN_LEFT) {
                    b.x = b0.x;
                } else if (side == ALIGN_H_CENTER) {
                    b.x = b0.x + (b0.width - b.width) / 2;
                } else if (side == ALIGN_V_CENTER) {
                    b.y = b0.y + (b0.height - b.height) / 2;
                } else if (side == ALIGN_SAME_WIDTH) {
                    b.width = b0.width;
                } else if (side == ALIGN_SAME_HEIGHT) {
                    b.height = b0.height;
                }
                comp.setBounds(b, true);
            } catch (Exception e) {
                jiglooPlugin.handleError(e);
            }
        }
        //		sel.getEditor().setSelectedComponents(sels);
    }
	
//    protected void tweakElements(boolean resize, boolean verticalLayout) {
//	    try {
//	        FormComponent  sel0 = editor.getSelectedComponent();
//	        LayoutWrapper lw = sel0.getParent().getLayoutWrapper();
//	        Vector fcs = editor.getSelectedComponents();
//	        for(int i=0; i< fcs.size(); i++) {
//	            FormComponent  sel = editor.getSelectedComponent(i);
//		        LayoutGroup lg = null;
//		        
//		        if(verticalLayout)
//		            lg = lw.getVGroup();
//		        else
//		            lg = lw.getHGroup();
//		        
//		        lg = lg.getGroupContaining(sel);
//		        if(lg != null) {
//		            if(resize)
//		                lg.toggleSize(sel);
//		            else
//		                lg.toggleAlignment(sel);
//		        }
//	        }
//	        updateGroups(lw);
//	        
//	    } catch(Throwable t) {
//	        t.printStackTrace();
//	    }
//	}

	
}