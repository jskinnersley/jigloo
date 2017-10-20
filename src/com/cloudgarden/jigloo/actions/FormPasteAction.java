/*
 * Created on Mar 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.groupLayoutSupport.SnapTo;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormPasteAction extends FormAction {

    public FormPasteAction(FormEditor editor) {
        super(editor);
    }
    
    public Rectangle getBounds(FormEditor ed) {
        Vector ccs = FormEditor.getClipboardComps();
        if(ccs.size() == 0)
            return null;
        if(ccs.elementAt(0) instanceof LayoutGroup) {
            LayoutGroup grp = (LayoutGroup)ccs.elementAt(0);
            return grp.getBounds();
        } else {
            return JiglooUtils.getBoundingRectangle(ccs);
        }
    }
    
    public void run(FormEditor ed, int x, int y) {
        try {
            Vector ccs = FormEditor.getClipboardComps();
            FormComponent par = ed.getSelectedComponent();
            if (par == null || ccs.size() == 0)
                return;
            
            ed.setWaitCursor();
            
            Rectangle bndRec;
            Vector pastedFCs = null;
            
            boolean groupCopy = (ccs.elementAt(0) instanceof LayoutGroup);
            
            if(!groupCopy && par.usesGroupLayout()) {
            	for(int i=0; i<ccs.size(); i++) {
            		FormComponent fc = (FormComponent)ccs.elementAt(i);
            		fc.setBoundsToRoot((Rectangle) fc.getRawPropertyValue("bounds"));
            		fc.setLayoutDataWrapper(null);
            	}
            	LayoutGroup hg = par.getLayoutWrapper().getHGroup().getCompactGroup(ccs, false, null, null, null);
            	LayoutGroup vg = par.getLayoutWrapper().getVGroup().getCompactGroup(ccs, false, null, null, null);
            	ccs.clear();
            	ccs.add(hg);
            	ccs.add(vg);
            	groupCopy = true;
            }
            
            if(groupCopy) {
            	bndRec = JiglooUtils.getBoundingRectangle(
            			((LayoutGroup)ccs.elementAt(0)).getContainedFCs());
            } else {
            	bndRec = JiglooUtils.getBoundingRectangle(ccs);
            	pastedFCs = (Vector) ccs.clone();
            }
            
            int newWidth = 60;
            int newHeight = 20;
            if(ed.getAddFrame() != null) {
                newWidth = ed.getAddFrame().getBounds().width;
                newHeight = ed.getAddFrame().getBounds().height;
            }
            x -= newWidth/2;
            y -= newHeight/2;
            
            SnapTo thisSnapTo = ed.getSnapTo();
            if(thisSnapTo != null) {
                Rectangle rec = new Rectangle(x, y, 10,10);
                thisSnapTo.setOrigin(rec);
                x = rec.x;
                y = rec.y;
            }
            Rectangle pb = par.getBoundsRelativeToRoot();
            x -= pb.x;
            y -= pb.y;

            
            int x0 = x;
            int y0 = y;
            HashMap transfer = new HashMap();

            for (int i = 0; i < ccs.size(); i++) {
                
                FormComponent fc = null;
                LayoutGroup grp = null;
                
                Object cc = ccs.elementAt(i);

                int cnt=1;
                
                if(!groupCopy) {
                    fc = (FormComponent) cc;
                    if (fc.isRoot())
                        continue;
                    if(i == 0)
                    	cnt = ccs.size();
                    else
                    	cnt = 0;
                } else {
                    grp = (LayoutGroup)cc;
                    if(i == 0) {
                        pastedFCs = new Vector(); 
                        grp.getAllFormComponents(pastedFCs);
                        cnt = pastedFCs.size();
                    } else {
                    	cnt = 0;
                    }
                    //if there are LayoutGroups in the clipboard, there will be 2 of them,
                    //and we only want to add FCs in for the first one, since both LGs
                    //will contain the same set of FCs
                }                
                
                while(cnt > 0) {
                    
                    cnt--;
                    
                    if(pastedFCs != null)
                        fc = (FormComponent)pastedFCs.elementAt(cnt);
                    
                    Rectangle fcb = null;
                    
                    if(!par.usesGroupLayout()) {
                        Object b = fc.getRawPropertyValue("size");
                        if(b == null)
                            b = fc.getRawPropertyValue("preferredSize");
                        int w = 60;
                        int h = 40;
                        if(b instanceof Point) {
                            Point rb = (Point)b;
                            if(rb.x > 10 && rb.y > 10) {
                                w = rb.x;
                                h = rb.y;
                            }
                        }
                        Rectangle bnds = (Rectangle) fc.getRawPropertyValue("bounds");
                        if(bnds != null) {
                        	x = x0 + bnds.x - bndRec.x;
                        	y = y0 + bnds.y - bndRec.y;
                        }
                        fcb = new Rectangle(x, y, w, h);
                        x+= w+7;
                        if(i % 2 == 1) {
                            x = x0;
                            y += h+7;
                        }
                    }
                    
                    FormComponent pfc = fc.getCopy(true, fc.getEditor());

                    UndoableEditAction uea = new UndoableEditAction(null, null, -1,  null, pfc, par, -1, fcb);
                    ed.pauseUpdate(true);
                    //in case ed is different from the editor which the elements were
                    //copied or cut from.
                    uea.setEditor(ed);
                    uea.redo();
                    ed.flushActions();
                    ed.pauseUpdate(false);
                    
                    FormComponent pasted = uea.getPastedComponent();
                    if(pasted.isPropertySet("name"))
                        pasted.setPropertyValue("name", pasted.getName());
                    
                    if(PaneLayoutHandler.handlesLayout(pasted.getParent())) {
                        pasted.setLayoutDataWrapper(PaneLayoutHandler.getInitialLDWrapper(pasted));
                    }
                    
                    transfer.put(fc, pasted);
                    
                }
                
                if(pastedFCs != null)
                	pastedFCs.clear();
                
                if(par.usesGroupLayout() && thisSnapTo != null) {
                	if(grp != null)
                		grp = grp.getCopy(grp.getLayoutWrapper(), false);
                	Iterator it = transfer.keySet().iterator();
                	while(it.hasNext()) {
                		FormComponent orig = (FormComponent) it.next();
                		FormComponent tfc = (FormComponent)transfer.get(orig);
                		if(grp != null)
                			grp.replaceFormComponent(orig, tfc);
                		pastedFCs.add(tfc);
                	}
                	int stx = thisSnapTo.xLeft + thisSnapTo.dx;
                	int sty = thisSnapTo.yTop + thisSnapTo.dy;
                	int stw = thisSnapTo.xRight + thisSnapTo.dx - stx;
                	int sth = thisSnapTo.yBottom + thisSnapTo.dy - sty;
                	
                	FormComponent proxy = (FormComponent) pastedFCs.elementAt(0);
                	proxy.setBoundsToRoot(new Rectangle(stx, sty, stw, sth));
                	
                	if(ed.getSelectedComponents() != null 
                			&& !ed.getSelectedComponents().contains(proxy))
                		ed.getSelectedComponents().add(proxy);
                	
                	ed.unselectComponent(proxy.getParent(), true);
                	
                	if(!groupCopy && fc != null) {
                		thisSnapTo.handleMouseUp(ed, proxy, ed.getSelectedComponents(), null, true);
                	} else	if(grp != null) {
                		thisSnapTo.handleMouseUp(ed, proxy, null, grp,false);
                	}
                	ed.setSnapTo(null);
                }
            }
            
            if(par.usesGroupLayout() && thisSnapTo != null) {
            	if(pastedFCs != null)
            		thisSnapTo.protectElements(pastedFCs);
            	thisSnapTo.finalizeChange(true);
            }
            
            ed.setDirtyAndUpdate();
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
    /**
     * @return
     */
    public Rectangle getBounds() {
        // TODO Auto-generated method stub
        return null;
    }

}
