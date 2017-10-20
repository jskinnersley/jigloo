/*
 * Created on Jul 29, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupElement;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AlignToolbar extends Composite {

    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 3;
    private static final int BOTTOM = 4;
    private static final int HCENTER = 5;
    private static final int VCENTER = 6;
    
    private FormEditor editor;
    private boolean verticalLayout;
    private boolean multi = false;
    private Button alignTLButton, alignCenterButton, alignBRButton;
    private Button resizeButton, realignButton, sameSizeButton;
    private FormComponent target;
    private GridLayout gl;
    private int SPACING = 1;
    
	public AlignToolbar(FormEditor editor, boolean vert) {
	    super(editor.getViewportControl(), SWT.NULL);
	    this.editor = editor;
	    this.verticalLayout = vert;
	    if(verticalLayout) {
	        gl = new GridLayout(2, false);
	        alignTLButton = newButton("alignTop.gif");
	        resizeButton = newButton("vSize.gif");
	        alignCenterButton = newButton("alignVCenter.gif");
	        realignButton = newButton("vAlign.gif");
	        alignBRButton = newButton("alignBottom.gif");
	        sameSizeButton = newButton("sameHeights.gif");
	        
	        alignTLButton.setToolTipText("Align tops of selected elements");
	        alignCenterButton.setToolTipText("Align centers of selected elements");
	        alignBRButton.setToolTipText("Align bottoms of selected elements");
	        realignButton.setToolTipText("Change vertical alignment of selected element(s)");
	        resizeButton.setToolTipText("Change height of selected element(s)");
	        sameSizeButton.setToolTipText("Make selected elements same height");
	    } else {
	        gl = new GridLayout(3, false);
	        alignTLButton = newButton("alignLeft.gif");
	        alignCenterButton = newButton("alignHCenter.gif");
	        alignBRButton = newButton("alignRight.gif");
	        resizeButton = newButton("hSize.gif");
	        realignButton = newButton("hAlign.gif");
	        sameSizeButton = newButton("sameWidths.gif");
	        
	        alignTLButton.setToolTipText("Align left sides of selected elements");
	        alignCenterButton.setToolTipText("Align baselines of selected elements");
	        alignBRButton.setToolTipText("Align right sides of selected elements");
	        realignButton.setToolTipText("Change horizontal alignment of selected element(s)");
	        resizeButton.setToolTipText("Change width of selected element(s)");
	        sameSizeButton.setToolTipText("Make selected elements same width");
	    }
	    resizeButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                tweakElements(true, verticalLayout);
            }
        });
	    realignButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                tweakElements(false, verticalLayout);
            }
        });
	    alignTLButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                alignElements(GroupLayout.LEADING, verticalLayout);
            }
        });
	    alignBRButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                alignElements(GroupLayout.TRAILING, verticalLayout);
            }
        });
	    alignCenterButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                alignElements(!verticalLayout ? GroupLayout.CENTER : GroupLayout.BASELINE, verticalLayout);
            }
        });
        gl.marginHeight = gl.marginWidth = 0;
        gl.verticalSpacing = gl.horizontalSpacing = SPACING;
        setBackground(ColorManager.getColor(50, 50, 50));
        setLayout(gl);
        alignTLButton.setLayoutData(newGD(13));
        alignCenterButton.setLayoutData(newGD(13));
        alignBRButton.setLayoutData(newGD(13));
        resizeButton.setLayoutData(newGD(13));
        realignButton.setLayoutData(newGD(13));
        sameSizeButton.setLayoutData(newGD(13));
	    layout();
	}

	/**
     * 
     */
	protected void tweakElements(boolean resize, boolean verticalLayout) {
	    try {
	        FormComponent  sel0 = editor.getSelectedComponent();
	        LayoutWrapper lw = sel0.getParent().getLayoutWrapper();
	        Vector fcs = editor.getSelectedComponents();
	        for(int i=0; i< fcs.size(); i++) {
	            FormComponent  sel = editor.getSelectedComponent(i);
		        LayoutGroup lg = null;
		        
		        if(verticalLayout)
		            lg = lw.getVGroup();
		        else
		            lg = lw.getHGroup();
		        
		        lg = lg.getGroupContaining(sel);
		        if(lg != null) {
		            if(resize)
		                lg.toggleSize(sel);
		            else
		                lg.toggleAlignment(sel);
		        }
	        }
	        updateGroups(lw);
	        
	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	}

    private void alignElements(int align, boolean verticalLayout) {
	    try {
	        FormComponent sel0 = editor.getSelectedComponent();
	        LayoutWrapper lw = sel0.getParent().getLayoutWrapper();
	        LayoutGroup lg0 = null;
	        LayoutGroup lg = null;
	        LayoutGroup lg0Perp = null;
	        LayoutGroup lgPerp = null;
	        if(verticalLayout) {
	            lg0 = lw.getVGroup();
	            lg0Perp = lw.getHGroup();
	        } else {
	            lg0 = lw.getHGroup();
	            lg0Perp = lw.getVGroup();
	        }
	        
	        lg = lg0.getGroupContaining(sel0);
	        lgPerp = lg0Perp.getGroupContaining(sel0);
	        
            if (lg.isSequential()) {
                int pos = lg.getIndexOf(sel0);
                LayoutGroup lg2 = lg.addGroup(pos, true, align);
                GroupElement selElem = lg.remove(sel0);
	            lg2.add(0, selElem, align, 0);
	            lg = lg2;
            }
            if (lgPerp.isParallel()) {
                int pos = lgPerp.getIndexOf(sel0);
                LayoutGroup lg2 = lgPerp.addGroup(pos, false, align);
                GroupElement selElem = lgPerp.remove(sel0);
                lg2.add(0, selElem, align, 0);
                lgPerp = lg2;
            }
	        Vector fcs = editor.getSelectedComponents();
	        for(int i=0; i< fcs.size(); i++) {
	            FormComponent  sel = editor.getSelectedComponent(i);
	            if(!sel.equals(sel0)) {
	                LayoutGroup lg2 = lg0.getGroupContaining(sel);
	                if(lg2 != null && !lg.equals(lg2)) {
	                    lg.add(-1, lg2.remove(sel), align, 0);
	                } else {
	                    lg.setAlignment(sel, align);
	                }
	                LayoutGroup lg2Perp = lg0Perp.getGroupContaining(sel);
	                if(lg2Perp != null && !lgPerp.equals(lg2Perp)) {
	                    lgPerp.add(-1, lg2Perp.remove(sel));
	                }
	            }
	        }

	        updateGroups(lw);

	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	}
	
    private void updateGroups(LayoutWrapper lw) {
        
        lw.getHGroup().cleanup();
        lw.getVGroup().cleanup();
        
        lw.getHGroup().clear();
        lw.getVGroup().clear();

        GroupLayout gl = (GroupLayout) lw.getObject();
        gl.setHorizontalGroup(lw.getHGroup().populateGroup());
        gl.setVerticalGroup(lw.getVGroup().populateGroup());
        JavaCodeParser jcp = editor.getJavaCodeParser();
        lw.getHGroup().repairInCode(jcp);
        lw.getVGroup().repairInCode(jcp);

        editor.setDirtyAndUpdate(true, true);
    }
    
	private Button newButton(String image) {
        Button b = new Button(this, SWT.PUSH);
        b.setImage(ImageManager.getImage(image));
        return b;
	}
	
	private GridData newGD(int size) {
        GridData gd = new GridData();
        gd.widthHint = gd.heightHint = size;
        return gd;
	}
	
	public void setMultiSelected(boolean ms) {
	    multi = ms;
	    if(ms) {
	        alignTLButton.setLayoutData(newGD(13));
	        alignCenterButton.setLayoutData(newGD(13));
	        alignBRButton.setLayoutData(newGD(13));
	        sameSizeButton.setLayoutData(newGD(13));
	    } else {
	        alignTLButton.setLayoutData(newGD(0));
	        alignCenterButton.setLayoutData(newGD(0));
	        alignBRButton.setLayoutData(newGD(0));
	        sameSizeButton.setLayoutData(newGD(0));
	    }
	    if(!verticalLayout) {
	        gl.horizontalSpacing = ms ? SPACING : 0;
	        gl.verticalSpacing = SPACING;
	    } else {
	        gl.verticalSpacing = ms ? SPACING : 0;
	        gl.horizontalSpacing = SPACING;
	    }
	    layout();
	    Point sz = computeSize(-1, -1);
	    setSize(sz);
	}
	
	private boolean isVertical() {
		return verticalLayout;
	}
	
	public void show(FormComponent fc) {
	    setVisible(true);
	    setMultiSelected(multi);
	    moveAbove(null);
	    Point sz = computeSize(-1, -1);
	    setSize(sz);
	    layout();
	    target = fc;
	    Rectangle b = fc.getBoundsRelativeTo(null);
	    if(verticalLayout)
	        setLocation(b.x - sz.x - 2, b.y);
	    else
		    setLocation(b.x, b.y - sz.y - 2);
	}
	
}
