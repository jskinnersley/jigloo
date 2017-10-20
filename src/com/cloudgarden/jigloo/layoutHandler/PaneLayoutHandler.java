/*
 * Created on Sep 15, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.layoutHandler;

import java.awt.LayoutManager;
import java.util.Vector;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.editors.HitResult;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.descriptors.ChoicePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.SiblingPropertyDescriptor;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaneLayoutHandler {
    
    public static final String LAYOUT_NAME = 
        "com.borland.jbcl.layout.PaneLayout";
    public static final String CONSTRAINT_NAME = 
        "com.borland.jbcl.layout.PaneConstraints";

    /**
     * @param component
     * @param bounds
     */
    public static void setBounds(FormComponent fc, Rectangle b) {
        try {
            String pos = fc.getLayoutDataProperty("position").toString();
            boolean vertical = "Top".equals(pos) || "Bottom".equals(pos);
            int gap = 2*((Integer)fc.getParent().getLayoutProperty("gap")).intValue();
            Rectangle fcb = getFullBounds(fc, b, vertical);
            
            String splitName = (String)fc.getLayoutDataProperty("splitComponentName");
            FormComponent fc2 = fc.getParent().getChildByName(splitName);
            if(fc2 == null) {
                System.err.println("PaneLayoutHandler.setBounds - unable to find child "+splitName+" of "+fc.getParentNameInCode());
                return;
            }
            Rectangle fullb1 = getFullBounds(fc2, null, vertical).union(fcb);
            //		Rectangle fullb1 = fc2.getBounds().union(fcb);
            
            if(!pos.equals("Root"))
                setProportion(fc, fcb, fullb1, vertical, false, gap);
            
            vertical = true;
            fc2 = getNeighbour(fc, "Top");
            if(fc2 != null)
                setProportion(fc2, getFullBounds(fc, fc2, b, vertical), getFullBounds(fc2, null, vertical), vertical, true, gap);
            
            fc2 = getNeighbour(fc, "Bottom");
            if(fc2 != null)
                setProportion(fc2, getFullBounds(fc, fc2, b, vertical), getFullBounds(fc2, null, vertical), vertical, true, gap);
            
            vertical = false;
            fc2 = getNeighbour(fc, "Left");
            if(fc2 != null)
                setProportion(fc2, getFullBounds(fc, fc2, b, vertical), getFullBounds(fc2, null, vertical), vertical, true, gap);
            
            fc2 = getNeighbour(fc, "Right");
            if(fc2 != null)
                setProportion(fc2, getFullBounds(fc, fc2, b, vertical), getFullBounds(fc2, null, vertical), vertical, true, gap);
        } catch(Throwable t) {
            t.printStackTrace();
		}
    }

    private static Rectangle getFullBounds(
            FormComponent fc, Rectangle b , boolean vertical) {
        return getFullBounds(fc, null, b, vertical);
    }
        
    private static Rectangle getFullBounds(
            FormComponent fc, FormComponent excFC, Rectangle b , boolean vertical) {
        if(b == null)
            b = fc.getBounds();
        FormComponent fc2;
        int iip = excFC == null ? fc.getIndexInParent() : excFC.getIndexInParent();
        if(vertical) {
            fc2 = getNeighbour(fc, "Top");
            if(fc2 != null && !fc2.equals(excFC) && fc2.getIndexInParent() > iip )
                b = b.union(getFullBounds(fc2, null, vertical));
            fc2 = getNeighbour(fc, "Bottom");
            if(fc2 != null && !fc2.equals(excFC) && fc2.getIndexInParent() > iip )
                b = b.union(getFullBounds(fc2, null, vertical));
        } else {
            fc2 = getNeighbour(fc, "Left");
            if(fc2 != null && !fc2.equals(excFC) && fc2.getIndexInParent() > iip )
                b = b.union(getFullBounds(fc2, null, vertical));
            fc2 = getNeighbour(fc, "Right");
            if(fc2 != null && !fc2.equals(excFC) && fc2.getIndexInParent() > iip )
                b = b.union(getFullBounds(fc2, null, vertical));
        }
        return b;
    }
    
    /**
     * @param fc2
     * @param fcb
     * @param b
     */
    private static void setProportion(FormComponent fc, 
            Rectangle b1, Rectangle b2, 
            boolean vertical, boolean remainder, int gap) {
        int len1;
        int len2;
        if(remainder) {
//    		b1 = getFullBounds(fc, b1, vertical);
            b2 = b2.union(b1);
        }
        
        if(!vertical) {
            len2 = b2.width;
            if(remainder)
                len1 = len2 - b1.width;
            else
                len1 = b1.width;
        } else {
            len2 = b2.height;
            if(remainder)
                len1 = len2 - b1.height;
            else
                len1 = b1.height;
        }
        if(remainder) {
            len1 -= gap;
            len2 -= gap;
        } else {
            len2 -= gap;
        }
        LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
		float prop = (float)len1/(float)len2;
		ldw.setPropertyValue("proportion", new Float(prop));
		fc.executeSetLayoutDataWrapper(ldw);
    }

    private static void setProportion(FormComponent fc, int len1, int len2) {
       LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
		float prop = (float)len1/(float)len2;
		ldw.setPropertyValue("proportion", new Float(prop));
		fc.executeSetLayoutDataWrapper(ldw);
    }
    
    private static void setProportions(FormComponent fc1, FormComponent fc2, 
            Rectangle b, boolean vertical) {
		LayoutDataWrapper ldw = fc1.getLayoutDataWrapper();
		int gap = ((Integer)fc1.getParent().getLayoutWrapper()
		        .getPropertyValue("gap")).intValue();
		Rectangle b1 = fc1.getBounds();
		Rectangle b2 = fc2.getBounds();
		int len = vertical ? b.height : b.width;
		int len1 = vertical ? b1.height : b1.width;
		int len2 = vertical ? b2.height : b2.width;
		float prop1 = len*1.0f/(len1+gap+len2);
		float prop2 = 1.0f - prop1;
		ldw.setPropertyValue("proportion", new Float(prop1));
		fc1.executeSetLayoutDataWrapper(ldw);
    }
    
    /**
     * If pos is null, return first neighbour encountered
     * @param fc
     * @param pos
     * @return
     */
    private static FormComponent getNeighbour(FormComponent fc, String pos) {
        FormComponent par = fc.getParent();
        String rootName = (String) fc.getPropertyValue("name");
        for(int i=0; i<par.getChildCount(); i++) {
            FormComponent fc2 = par.getChildAt(i);
		    String splitName = (String)fc2.getLayoutDataProperty("splitComponentName");
			String splitPos = fc2.getLayoutDataProperty("position").toString();
		    if(splitName.equals(rootName) && (pos == null || pos.equals(splitPos))) {
		        return fc2;
		    }
        }
        return null;
    }

    /**
     * @param descs
     * @param wrapper
     */
    public static void prepPropertyDescriptors(IPropertyDescriptor[] descs, 
            final LayoutDataWrapper ldw) {
		for (int i = 0; i < descs.length; i++) {
			String id = (String) descs[i].getId();
			if (id.equals("splitComponentName")) {
			    descs[i] = new SiblingPropertyDescriptor(id, id, 
			            ldw.getFormComponent(), ldw, false, false, true) {
                    protected void valueChanged(Object newValue) {
                        ensureCorrectOrder(ldw.getFormComponent().getParent());
                    }
			    };
			} else if(id.equals("position")) {
				descs[i] =
					new ChoicePropertyDescriptor(
						"position",
						"position",
						ldw.getFormComponent(),
						new String[] { "Top", "Bottom", "Left", "Right", "Root"},
						ldw) {
				    
                    protected void valueChanged(Object newValue) {
                        if(newValue.equals("Root")) {
                            setSplit(ldw.getFormComponent(), ldw, "Root", ldw.getFormComponent(), true);
                            ensureCorrectOrder(ldw.getFormComponent().getParent());
                        }
                    }
				};
			}
		}
    }

    /**
     * @param editor
     * @param hr
     * @return
     */
    public static boolean handleMouseUp(FormEditor editor,
            FormComponent moveTarget, HitResult hr) {
        
        if(handlesLayout(moveTarget)) {
            FormComponent relFC = hr.formComp;
            FormComponent sel = editor.getSelectedComponent();
            LayoutDataWrapper ldw = sel.getLayoutDataWrapper();
            String pos = null;
            if(hr.isNorthEdge()) {
                pos = "Top";
            } else if(hr.isSouthEdge()) {
                pos = "Bottom";
            } else if(hr.getPosition() == HitResult.WEST ) {
                pos = "Left";
            } else {
                pos = "Right";
            }
//            System.out.println("PaneLayout: "+sel.getName()+" moved to "+relFC.getName()+":"+pos);
            setSplit(sel, ldw, pos, relFC, true);
            sel.executeSetLayoutDataWrapper(ldw);
            
		    ensureCorrectOrder(moveTarget);
    		FormComponent fb = moveTarget.getChildAt(0);
    		ldw = fb.getLayoutDataWrapper();
    		pos = ldw.getPropertyValue("position").toString();
    		if(!pos.equals("Root")) {
    		    //    		System.out.println("PaneLayout: "+fb.getName()+" position "+ldw.getPropertyValue("position"));
                setSplit(fb, ldw, "Root", fb, true);
    		    fb.executeSetLayoutDataWrapper(ldw);
    		}
            return true;
        }
        return false;
    }

    public static void ensureCorrectOrder(FormComponent parent) {
        Vector moved = new Vector();
        for(int i=0; i<parent.getNonInheritedChildCount(); i++) {
            FormComponent fc = parent.getNonInheritedChildAt(i);

            LayoutDataWrapper ldw = fc.getLayoutDataWrapper();
            String pos = ldw.getPropertyValue("position").toString();
            String split = ldw.getPropertyValue("splitComponentName").toString();
            if("Root".equals(pos)) {
                if(!split.equals(fc.getName()))
                    System.out.println("Error - root comp is not it's own split "+fc.getName());
            } else {
                if(split.equals(fc.getName()))
                    System.out.println("Error - non-root comp is it's own split "+fc.getName());
            }
            if("Root".equals(pos) && i != 0 && !moved.contains(fc)) {
        		parent.getChildren().remove(fc);
        		parent.getChildren().add(0, fc);
        		fc.repairParentConnectionInCode();
            } else if(!"Root".equals(pos)) {
                FormComponent rel = parent.getChildByName(split);
                //rel will be null if the component split has not yet been added to parent
                if(rel != null && !moved.contains(fc)) {
                    if(moveAfter(fc, rel, false)) {
                        moved.add(fc);
                        i--;
                    }
                }
            }
        }
//        if(moved.size() > 0)
        refreshLayout(parent);

    }
    
    private static boolean moveAfter(FormComponent fc, FormComponent rel, boolean rightAfter) {
        if(rel == null) {
            FormComponent parent = fc.getParent();
            parent.getChildren().remove(fc);
            parent.getChildren().add( fc);
            fc.repairParentConnectionInCode();
            return true;
        }
        int relPos = rel.getNonInheritedIndexInParent();
        int pos = fc.getNonInheritedIndexInParent();
        if(rightAfter && pos == relPos+1)
            return false;
        if(!rightAfter && pos > relPos)
            return false;
        int offset = pos > relPos ? 1 : 0;
//        System.out.println("move "+fc+":"+pos+" after "+rel+":"+relPos);
        FormComponent parent = fc.getParent();
        parent.getChildren().remove(fc);
        parent.getChildren().add(relPos+offset, fc);
        fc.repairParentConnectionInCode();
        return true;
    }
    
    /**
     * @return
     */
    public static LayoutDataWrapper getInitialLDWrapper(FormComponent fc) {
        try {
            LayoutManager lm = (LayoutManager) fc.getParent().getLayoutWrapper().getObject();
            Class cls = lm.getClass().getClassLoader().loadClass(CONSTRAINT_NAME);
            //the PaneConstraint must be loaded by the same loader as the PaneLayout class
            //otherwise the condition in PaneLayout "instanceof PaneConstraint" returns false
            Object ld = ClassUtils.newInstance(cls, null, null);
            LayoutDataWrapper ldw = new LayoutDataWrapper(ld, fc);
            fc.setLayoutDataWrapperSimple(ldw);
			ldw.setObject(ld);
			ldw.setPropertyValue("name", fc.getName());
			FormComponent par = fc.getParent();
			
			int cc = par.getNonInheritedChildCount();
		    //if fc has been pasted it will already have been added to the end of the parent's children Vector
		    //if fc has been added, it won't - basically, don't want to include fc in count
			boolean added = par.hasChild(fc);
			if(added)
			    cc--;
			String pos;
			FormComponent split;
			
			if(cc == 0) {
			    split = fc;
			    pos = "Root";
			} else {
				HitResult hr = fc.getEditor().getHitResult();
				if(hr.formComp != null) {
				    split = hr.formComp;
				    if(hr.isSouthEdge())
				        pos = "Bottom";
				    else if(hr.isWestEdge())
				        pos = "Left";
				    else if(hr.isNorthEdge())
				        pos = "Top";
				    else
				        pos = "Right";
				} else {
				    split = par.getNonInheritedChildAt(cc-1);
				    pos = "Right";
				}
			}
			
            setSplit(fc, ldw, pos, split, added);
            if(added)
                ensureCorrectOrder(fc.getParent());

			return ldw;
        } catch(Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private static Image speckle = ImageManager.getImage("speckle.gif");
    /**
     * @param gc
     * @param sel
     * @param hitResult
     */
    public static void draw(GC gc, FormComponent sel, HitResult hitResult) {
        FormComponent fc = hitResult.formComp;
        if(fc == null)
            return;
        FormComponent mt = fc.getEditor().getMoveTarget();
        if(fc == null 
                || (fc.getEditor().getCurrentAction() == null && fc.equals(sel)) 
                || fc.getParent() == null
                || !fc.getParent().equals(mt)
                )
            return;
        Rectangle b = fc.getBoundsRelativeToRoot();
        int pos = hitResult.getPosition();
        gc.setForeground(ColorManager.getColor(100, 100, 100));
        int x = -1, y = -1, w = -1, h = -1;
        int[] points = null;
        int S = 5;
        if(hitResult.isNorthEdge()) {
            w = b.width;
            h = Math.min(20, b.height/3);
            x = b.x;
            y = b.y;
            int xc = x+w/2;
            int yc = y+h/2;
            points = new int[] {
                    xc-S, yc+S, 
                    xc+S, yc+S,
                    xc+S, yc,
                    xc+2*S, yc,
                    xc, yc-2*S,
                    xc-2*S, yc,
                    xc-S, yc};
        } else if(hitResult.isSouthEdge()) {
            w = b.width;
            h = Math.min(20, b.height/3);
            x = b.x;
            y = b.y +b.height - h;
            int xc = x+w/2;
            int yc = y+h/2;
            points = new int[] {
                    xc-S, yc-S, 
                    xc+S, yc-S,
                    xc+S, yc,
                    xc+2*S, yc,
                    xc, yc+2*S,
                    xc-2*S, yc,
                    xc-S, yc};
        } else if(hitResult.isWestEdge()) {
            w = Math.min(20, b.width/3);
            h = b.height;
            x = b.x;
            y = b.y;
            int xc = x+w/2;
            int yc = y+h/2;
            points = new int[] {
                    xc+S, yc+S, 
                    xc+S, yc-S,
                    xc, yc-S,
                    xc, yc-2*S,
                    xc-2*S, yc,
                    xc, yc+2*S,
                    xc, yc+S};
        } else if(hitResult.isEastEdge()) {
            w = Math.min(20, b.width/3);
            h = b.height;
            x = b.x + b.width - w;
            y = b.y;
            int xc = x+w/2;
            int yc = y+h/2;
            points = new int[] {
                    xc-S, yc+S, 
                    xc-S, yc-S,
                    xc, yc-S,
                    xc, yc-2*S,
                    xc+2*S, yc,
                    xc, yc+2*S,
                    xc, yc+S};
        }
        if(x >= 0) {
            for(int i=0; i < w-1; i+=2) {
                for(int j = i%2; j < h-1; j+=2) {
                    gc.drawPoint(x+i, y+j);
                }                
            }
        }
        if(points != null) {
            gc.setBackground(ColorManager.getColor(250, 0, 0));
            gc.fillPolygon(points);
            gc.setForeground(ColorManager.getColor(0, 0, 0));
            gc.drawPolygon(points);
        }
    }

    private static String opposite(String pos) {
        if(pos.equals("Top"))
            return "Bottom";
        if(pos.equals("Bottom"))
            return "Top";
        if(pos.equals("Left"))
            return "Right";
        if(pos.equals("Right"))
            return "Left";
        return pos;
    }
    
    private static void setSplit(
            FormComponent fc, LayoutDataWrapper ldw, 
            String pos, FormComponent split, boolean added) {
        String splitPos = split.getLayoutDataProperty("position").toString();
        
        //get neighbour before setting pos
        FormComponent nei;
        
        nei = getNeighbour(fc, null);
        if(nei != null && added) {
            LayoutDataWrapper nldw = nei.getLayoutDataWrapper();
            nldw.setPropertyValue("splitComponentName", ldw.getPropertyValue("splitComponentName"));
            nldw.setPropertyValue("position",  ldw.getPropertyValue("position"));
            nei.executeSetLayoutDataWrapper(nldw);
        }
        
        nei = getNeighbour(split, pos);
        
        ldw.setPropertyValue("splitComponentName", split.getName());
		ldw.setPropertyValue("position", pos);
		
		if(!added)
		    return;
		
//        moveAfter(fc, split, true);
        moveAfter(fc, null, true);

        if(nei != null && !nei.equals(fc)) {
            ldw = nei.getLayoutDataWrapper();
            ldw.setPropertyValue("splitComponentName", fc.getName());
            moveAfter(nei, fc, true);
            nei.executeSetLayoutDataWrapper(ldw);
        }

    }

    /**
     * @param addedComponent
     */
    public static void refreshLayout(FormComponent parent) {
//	    parent.setLayoutWrapper(new LayoutWrapper(parent));
	    parent.setLayoutWrapper(parent.getLayoutWrapper(), true);
    }

    /**
     * @param sel
     * @return
     */
    public static boolean handlesLayout(FormComponent sel) {
        return "Pane".equals(sel.getLayoutType());
    }

    /**
     * @param addedComponent
     */
    public static void finalise(FormComponent fc) {
		fc.setPropertyValue("name", fc.getName());
	    refreshLayout(fc.getParent());
//		fc.setPropertyValueSimple("name", fc.getName());
//		fc.setSetProperty("name");
//		fc.setNeedsUpdateInCode("name");
    }

    /**
     * @param wrapper
     * @return
     */
    public static String getJavaCode(LayoutDataWrapper ldw, IJavaCodeManager jcm) {
	    jcm.addImport(CONSTRAINT_NAME);
		
		String code = "new PaneConstraints("+
		"\""+ldw.getPropertyValue("name")+"\", "+
		"\""+ldw.getPropertyValue("splitComponentName")+"\", "+
		"PaneConstraints."+ldw.getPropertyValue("position").toString().toUpperCase()+", "+
		ldw.getPropertyValue("proportion")+"F)";
		return code;
    }
}
