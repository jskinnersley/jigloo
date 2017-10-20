/*
 * Created on Aug 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayoutSupport;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.groupLayout.Alignment;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayout.GroupLayout.ComponentSpring;
import com.cloudgarden.jigloo.groupLayout.GroupLayout.Group;
import com.cloudgarden.jigloo.groupLayout.GroupLayout.SequentialGroup;
import com.cloudgarden.jigloo.groupLayout.GroupLayout.Spring;
import com.cloudgarden.jigloo.groupLayout.LayoutStyle;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 * Holds all components added to a GroupLayout's horizontal or vertical Group
 */
public class LayoutGroup {

    public static final int EXPANDABLE_SIZE = 3000;
    private static final int END_UNDEFINED = 1000002;
    /**
     * Number of pixels within which a match is allowed (for getCompactGroup)
     */
    private static final int WIGGLE_ROOM = 3;
    
    public static final Integer MIN_GAP_INT = new Integer(6);
    public static final Integer MAX_GAP_INT = new Integer(16);
    public static final Integer INDENT_INT = new Integer(LayoutStyle.INDENT);
    public static final Integer UNRELATED_INT = new Integer(LayoutStyle.UNRELATED);
    public static final Integer RELATED_INT = new Integer(LayoutStyle.RELATED);
    
    public static final int LEAVE_ALIGNMENT = -10;
    public static final int REMOVE_ALIGNMENT = -11;
    
    public static final Integer PREF_SIZE = new Integer(GroupLayout.PREFERRED_SIZE);
    public static final Integer DEF_SIZE = new Integer(GroupLayout.DEFAULT_SIZE);
    public static final Integer MIN_SIZE = new Integer(0);
    public static final Integer MAX_SIZE = new Integer(EXPANDABLE_SIZE);
    
    public static final int SIZE_NOT_SET = 1;
    public static final int SIZE_MAX = 2;
    public static final int SIZE_PREF = 3;

    private String creationMethod;
    private LayoutWrapper lw;
    private Object[] creationArgs;
    private Vector addedElements = new Vector();
    private LayoutGroup parent = null;
    private GroupLayout.Group group = null;
    private GroupLayout groupLayout = null;
    private boolean vertical;
    private int alignment;
    private boolean explicitAlignment;
    private boolean resizable;
    private int indexInParent;
    
    public static final String ANCHOR_LEFT = "ANCHOR_LEFT";
    public static final String ANCHOR_RIGHT = "ANCHOR_RIGHT";
    public static final String ANCHOR_TOP = "ANCHOR_TOP";
    public static final String ANCHOR_BOTTOM = "ANCHOR_BOTTOM";
    public static final String EXPAND_VER = "EXPAND_VER";
    public static final String EXPAND_HOR = "EXPAND_HOR";
    public static final String SET_DEFAULT_HEIGHT = "DEFAULT_HEIGHT";
    public static final String SET_DEFAULT_WIDTH = "DEFAULT_WIDTH";
    
    public LayoutGroup(String creationMethod, Object[] creationArgs, 
            LayoutWrapper layout, boolean vertical) {
        this.creationMethod = creationMethod;
        this.creationArgs = creationArgs;
        this.lw = layout;
        this.vertical = vertical;
        
        alignment = GroupLayout.LEADING; //default value
        explicitAlignment = false;
        resizable = true; //default value
        
        if(creationArgs != null) {
            if(creationArgs.length > 0) {
                if(creationArgs[0] instanceof Integer) {
                    alignment = ((Integer)creationArgs[0]).intValue();
                    explicitAlignment = true;
                }
                //v4.0M3
                if(creationArgs[0] instanceof Alignment) {
                    alignment = ((Alignment)creationArgs[0]).getVal();
                    explicitAlignment = true;
                }
            }
            if(creationArgs.length > 1) {
                if(creationArgs[1] instanceof Boolean)
                    resizable = ((Boolean)creationArgs[1]).booleanValue();
            }
        }
    }
    
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        for (int i = 0; i < addedElements.size(); i++) {
            Object obj = getGroupOrFC(i);
            if(obj instanceof LayoutGroup)
                ((LayoutGroup)obj).setVertical(vertical);
        }
    }
    
    public void add(int pos, LayoutGroup lg) {
        lg.setParent(this, pos);
        add(pos, new GroupElement("add", new Object[]{lg}));
    }
    
    public void add(GroupElement elem) {
        add(-1, elem);
    }
        
    public void add(int pos, GroupElement elem) {
        add(pos, elem, LEAVE_ALIGNMENT);
    }

    public void add(int pos, GroupElement elem, int alignment) {
        add(pos, elem, alignment, 0);
    }
        
    public void add(int pos, GroupElement elem, int alignment, int gap) {
        add(pos, elem, alignment, gap, null, null);
    }
    
    public void add(int pos, GroupElement elem, int alignment, int gap, 
            FormComponent fc1, FormComponent fc2) {
        if(pos == -1)
            pos = getElementCount();
        if(addGap(pos, gap, fc1, fc2))
            pos++;
        if(isSequential()) {
            setAlignment(elem, REMOVE_ALIGNMENT);
        } else {
            setAlignment(elem, alignment);
        }
        if(pos < 0 || pos > addedElements.size())
            pos = addedElements.size();
        LayoutGroup lg = extractGroup(elem);
        if(lg != null)
            lg.setParent(this, pos);
        addedElements.add(pos, elem);
        elem.setLayoutGroup(this);
    }
    
    public void setElement(int pos, GroupElement elem) {
        if(pos == -1)
            pos = getElementCount();
        if(isSequential()) {
            setAlignment(elem, REMOVE_ALIGNMENT);
        }
        LayoutGroup lg = extractGroup(elem);
        if(lg != null)
            lg.setParent(this, pos);
        addedElements.set(pos, elem);
        elem.setLayoutGroup(this);
    }
    
    private void setParent(LayoutGroup lg, int indexInParent) {
        parent = lg;
        this.indexInParent = indexInParent;
        if(parent != null && !lw.equals(parent.getLayoutWrapper())) {
//            System.out.println("LG: SET PARENT LWs not equal ");
            lw = parent.getLayoutWrapper();
        }
    }
    
    LayoutGroup getGroup(int i) {
        Object o = getGroupOrFC(i);
        if(o instanceof LayoutGroup)
            return (LayoutGroup) o;
        return null;
    }
    
    boolean isGap(int pos) {
        if(pos < 0 || pos >= addedElements.size())
            return false;
        return getGroupOrFC(pos) == null;
    }
    
    private boolean isExpandableGap(int pos) {
        return getElement(pos).isExpandableGap();
    }
    
    private Object getGroupOrFC(int i) {
        if(i < 0 || i >= addedElements.size())
            return null;
        return getGroupOrFC(getElement(i));
    }
    
    private Object getGroupOrFC(GroupElement elem) {
            if(elem == null)
                return null;
        return elem.getGroupOrFC();
    }
    
    private LayoutGroup extractGroup(GroupElement elem) {
        Object obj = getGroupOrFC(elem);
        if(obj instanceof LayoutGroup)
            return (LayoutGroup) obj;
        return null;
    }
    
    private FormComponent extractFC(GroupElement elem) {
        Object obj = getGroupOrFC(elem);
        if(obj instanceof FormComponent)
            return (FormComponent) obj;
        return null;
    }
    
    public GroupElement getElement(int elem) {
        if(elem < 0)
            return getLastElement();
        return (GroupElement)addedElements.elementAt(elem);
    }
    
    private Object[] getArgs(int elem) {
        return getElement(elem).getArgs();
    }
    
    private String getAddMethod(int elem) {
        return getElement(elem).getAddMethod();
    }
    
    public LayoutGroup getGroupContaining(Object fc) {
        for (int i = 0; i < addedElements.size(); i++) {
            Object fco = getGroupOrFC(i);
            if(fc.equals(fco)) {
                return this;
            } else if(getElement(i).equals(fco)) {
                return this;
            } else if(fco instanceof LayoutGroup) {
                LayoutGroup grp = ((LayoutGroup)fco).getGroupContaining(fc);
                if(grp != null)
                    return grp;
            }
        }
        return null;
    }

    public LayoutGroup getGroupContaining(GroupElement elem) {
        for (int i = 0; i < addedElements.size(); i++) {
            if(elem.equals(getElement(i))) {
                    return this;
            } else if(getGroup(i) != null) {
                LayoutGroup grp = getGroup(i).getGroupContaining(elem);
                if(grp != null)
                    return grp;
            }
        }
        return null;
    }

    public void updateParents() {
        for(int i=0; i< getElementCount(); i++) {
            LayoutGroup lg = getGroup(i);
            if(lg != null) {
                lg.setParent(this, i);
                lg.updateParents();
            }
        }
    }
    
    public void makeParallel() {
        changeAndInsert("createParallelGroup");
    }
    
    public void makeSequential() {
        changeAndInsert("createSequentialGroup");
    }
    
    public void changeAndInsert(String newCreationMethod) {
        LayoutGroup lg = new LayoutGroup(creationMethod, creationArgs, lw, vertical);
        lg.startPos = startPos;
        lg.setParent(this, 0);
        lg.addedElements = addedElements;
        creationMethod = newCreationMethod;
        creationArgs = null;
        addedElements = new Vector();
        add(new GroupElement("add", new Object[] {lg}));
        updateParents();
    }
        
    public boolean isSequential() {
        return creationMethod.equals("createSequentialGroup");
    }
    
    public boolean isBaseline() {
        return isParallel() && 
        getElementCount() > 0 && 
        getElement(0).getAlignment() == GroupLayout.BASELINE;
    }
    
    public boolean isParallel() {
        return creationMethod.equals("createParallelGroup");
    }
    
    public void replaceFormComponent(FormComponent fc1, FormComponent fc2) {
        for(int i=0; i<getElementCount(); i++) {
            Object[] args = getArgs(i);
            if(args != null) {
                for (int j = 0; j < args.length; j++) {
                    if(args[j] instanceof FormComponent
                            && ((FormComponent)args[j]).getName().equals(fc1.getName()))
//                    if(args[j].equals(fc1))
                        args[j] = fc2;
                }
            }
            LayoutGroup grp = getGroup(i);
            if(grp != null)
                grp.replaceFormComponent(fc1, fc2);
        }
    }
    
    public void getAllFormComponents(Vector comps) {
        for(int i=0; i<getElementCount(); i++) {
            FormComponent fc = getFormComponent(i);
            LayoutGroup grp = getGroup(i);
            if(fc != null)
                comps.add(fc);
            else if(grp != null)
                grp.getAllFormComponents(comps);
        }
    }
    
    private FormComponent getFormComponent(int i) {
        Object fco = getGroupOrFC(i);
        if(fco instanceof FormComponent) {
            return (FormComponent)fco;
        }
        return null;
    }
    
    public int getIndexOf(Object fc) {
        return getIndexOf(fc, true);
    }

    public int getIndexOf(Object fc, boolean searchChildren) {
        if(fc == null)
            return -1;
        int sc = addedElements.size();
        for(int i=0 ; i < sc; i++) {
            if(fc.equals(getElement(i)))
                return i;
            if(fc.equals(getGroupOrFC(i)))
                return i;
        }
        int pos = -1;
        if(searchChildren) {
            LayoutGroup lg = getGroupContaining(fc);
            while(lg != null && !equals(lg.getParent())) {
                lg = lg.getParent();
            }
            if(lg != null)
                pos =  lg.getIndexInParent();
        }
        return pos;
    }
    
    private static Rectangle getBounds(FormComponent fc) {
        return fc.getBoundsRelativeToRoot();
    }
    
    private Rectangle getBounds(Object fc) {
        if(fc instanceof FormComponent)
            return getBounds((FormComponent)fc);
        else if(fc instanceof LayoutGroup)
            return ((LayoutGroup)fc).getBounds();
        return null;
    }
    
    public Rectangle getBounds() {
        return JiglooUtils.getBoundingRectangle(getContainedFCs());
    }

    public int[] getPerpendiculars(int child) {
        Object gFC = getGroupOrFC(child);
        if(gFC instanceof LayoutGroup) {
            LayoutGroup lg = (LayoutGroup)gFC;
            if((lg.vertical && !vertical) || (!lg.vertical && vertical)) {
                return lg.getPerpendicularsSwitched();
            }
            return lg.getPerpendiculars();
        }
        else if(gFC instanceof FormComponent) {
            Rectangle b = getBounds((FormComponent)gFC);
            if(vertical)
                return new int[] {b.x, b.x+b.width};
            return new int[] {b.y,  b.y+b.height};
        }
        return null;
    }
        
    public int[] getPerpendiculars() {
        Vector fcs = getContainedFCs();
        Rectangle b = null;
        b = JiglooUtils.getBoundingRectangle(fcs);
        if(fcs.size() == 0) {
            return new int[] {10, 20};
        }
        if(b == null)
            return new int[] {10, 20};
        if(vertical)
            return new int[] {b.x, b.x+b.width};
        return new int[] {b.y,  b.y+b.height};
    }
    
    public int[] getPerpendicularsSwitched() {
        Vector fcs = getContainedFCs();
        Rectangle b = null;
        b = JiglooUtils.getBoundingRectangle(fcs);
        if(fcs.size() == 0) {
            return new int[] {10, 20};
        }
        if(b == null)
            return new int[] {10, 20};
        if(!vertical)
            return new int[] {b.x, b.x+b.width};
        return new int[] {b.y,  b.y+b.height};
    }
    
    public int[] getBoundsInContainer() {
        int p0 = 0;
        int p1 = 0;
        LayoutGroup p = this;
        LayoutGroup c = null;
        int inp = getSpringCount();
        while(p != null) {
            if(p.isSequential()) {
                for(int i=0;i<inp;i++) {
                    int sz = p.getSize(i);
                    if(c != null)
                        p0 += sz;
                    p1 += sz;
                }
            }
            inp = getIndexInParent();
            c = p;
            p = p.getParent();
        }
        return new int[] {p0, p1};
    }
    
    public int[] getLimits() {
        int x = 0;
        if(parent != null)
            return parent.getBounds(indexInParent);
        
        if(group == null)
            populateGroup();
        
        if(group.parent instanceof Group) {
            //if parent is null and this group is inside a parent group, then
            //the elder sibling of this group will be the container spacing
            Group s = (Group)group.parent;
            if(s.getSprings().size() > 1) {
                x = getSize((Spring)s.getSprings().get(0));
            }
        }
        Rectangle b = getFC().getInsideBoundsRelativeToRoot();
        if(vertical)
            x += b.y;
        else
            x += b.x;
        
        return new int[] {x, x+getSize(group)};
    }

    private static int getSize(Spring g) {
        int sz = g.getSize();
        if(sz >= 0)
            return sz;
        sz = 0;
        int orig = 0;
        if(g instanceof Group) {
            List ss = ((Group)g).getSprings();
            for(int i=0; i<ss.size(); i++) {
                Spring s = (Spring)ss.get(i);
                if(g instanceof SequentialGroup) {
                    sz += getSize(s);
                } else {
                    if(s instanceof ComponentSpring) {
                        ComponentSpring cs = (ComponentSpring)s;
                        orig = cs.getOrigin();
                    }
                }
            }
        }
        return sz + orig;
    }
    
    private static int[] getLimits(Spring g) {
        int sz = g.getSize();
        int orig = 0;
        if(g instanceof ComponentSpring) {
            ComponentSpring cs = (ComponentSpring)g;
            orig = cs.getOrigin();
        }
        return new int[] {orig, sz};
    }
    
    public int getSize(int pos) {
        return getElement(pos).getSize();
    }
    
    /**
     * @return the root FormComponent for this LayoutGroup's LayoutWrapper
     */
    private FormComponent getFC() {
        return lw.getFormComponent();
    }
    
    private int[] getBounds(int child) {
        int[] b = getLimits();

//        int orig = b[0];
        b[1] = b[0];
        
        int i1 = 0;
        int i2 = child;
        
        if(isParallel())
            i1 = child;
            
        for(int i = i1 ; i <= i2 ;  i++) {
            Spring s = getSpring(i);
            b[0] = b[1];
            if(s != null)
                b[1] = b[0] + getSize(s);
        }
        return b;
    }
    
    public int getSpringCount() {
        if(group == null)
            return 0;
        return group.getSprings().size();
    }
    
    public Spring getSpring(int child) {
        if(child < 0 || child >= getSpringCount())
            return null;
        return (Spring)group.getSprings().get(child);
    }
    
    /**
     * returns the absolute position (relative to the component's parent)
     * of the baseline of this group
     */
    private int getBaseLine() {
        if(isSequential() && vertical)
            return -1;
        if(!isSequential() && !vertical)
            return -1;
        for(int i=0;i<addedElements.size();i++) {
            Spring s = getSpring(i);
            if(s instanceof GroupLayout.ComponentSpring) {
                GroupLayout.ComponentSpring cs = (GroupLayout.ComponentSpring)s;
                Component comp = cs.getComponent();
                int bl = cs.getBaseline();
                java.awt.Rectangle b = comp.getBounds();
                if(bl < 0)
                    bl = b.height/2;
                Rectangle pb = getFC().getInsideBoundsRelativeToRoot();
                return b.y + bl + pb.y;
            }
        }        
        return -1;
    }
    
    public GroupElement getNextSibling() {
        if(parent == null)
            return null;
        int pos = getIndexInParent();
        if(pos == parent.getElementCount()-1)
            return null;
        return parent.getElement(pos+1);
    }
    
    public GroupElement getPreviousSibling() {
        if(parent == null)
            return null;
        int pos = getIndexInParent();
        if(pos == 0)
            return null;
        return parent.getElement(pos-1);
    }
    
    public boolean isContainerGap(GroupElement elem) {
        if(elem == null)
            return false;
        return "addContainerGap".equals(elem.getAddMethod());
    }
    
    public boolean isContainerGap(int pos) {
        if(pos < 0 || pos >= getElementCount())
            return false;
        return "addContainerGap".equals(getAddMethod(pos));
    }
    
    public boolean isNonPreferredGap(int pos) {
        if(pos < 0 || pos >= getElementCount())
            return false;
        return "add".equals(getAddMethod(pos)) && getGroupOrFC(pos) == null;
    }
    
    public boolean isPreferredGap(int pos) {
        if(pos < 0 || pos >= getElementCount())
            return false;
        return "addPreferredGap".equals(getAddMethod(pos));
    }
    
    public boolean isFixedPreferredGap(int pos) {
        if(pos < 0 || pos >= getElementCount())
            return false;
        return getElement(pos).isFixedPreferredGap();
    }
    
    public GroupElement getLastElement() {
        return getLastElement(false);
    }

    public GroupElement getLastElement(boolean global) {
        //TODO not sure if this is correct!!!
        if(!global || (parentIsSequential() && getNextSibling() == null) ) {
            if(getElementCount() == 0)
                return null;
            return getElement(getElementCount()-1);
        } else {
            if(parent == null)
                return getLastElement(false);
            return parent.getLastElement(true);
        }
    }
    
    public GroupElement getFirstElement() {
        return getFirstElement(false);
    }

    public GroupElement getFirstElement(boolean global) {
        //TODO not sure if this is correct!!!
        if(!global) {
            if(getElementCount() == 0)
                return null;
            return getElement(0);
        } else {
            if(parent == null)
                return getFirstElement(false);
            return parent.getFirstElement(true);
        }
    }
    
    public boolean canBeTrailingEdgeElement(Object grpOrFC) {
        if(isParallel())
            return getNextSibling() == null;
        return isAtEnd(grpOrFC) 
        && (parent == null || parent.canBeTrailingEdgeElement(this));
    }
    
    public boolean canBeLeadingEdgeElement(Object grpOrFC) {
        if(isParallel())
            return getPreviousSibling() == null;
        return isAtStart(grpOrFC)
        && (parent == null || parent.canBeLeadingEdgeElement(this));
    }
    
    public boolean endsWithContainerGap(int pos) {
        if(getElementCount() == 0)
            return false;
        if(isSequential()) {
            return (pos == getElementCount() - 2) 
            	&& isContainerGap(getLastElement());
        } else {
            return isContainerGap(getNextSibling());
        }
    }
    
    public boolean startsWithContainerGap(int pos) {
        if(getElementCount() == 0)
            return false;
        if(isSequential()) {
            return (pos == 1) 
            && isContainerGap(getFirstElement());
        } else {
            return isContainerGap(getPreviousSibling());
        }
    }
    
    private void drawSolidLine(GC gc, int x1, int y1, int x2, int y2) {
        gc.setLineStyle(SWT.LINE_SOLID);
        gc.drawLine(x1, y1, x2, y2);
    }
    
    private void drawDottedLine(GC gc, int x1, int y1, int x2, int y2) {
        if(y1 == y2) {
            if(x1 % 2 != 0)
                x1++;
            for(int x=x1; x< x2; x+=2) {
//                gc.drawPoint(x, y1);
                gc.drawLine(x, y1, x, y1);
            }
        } else {
            if(y1 % 2 != 0)
                y1++;
            for(int y=y1; y< y2; y+=2) {
//                gc.drawPoint(x1, y);
                gc.drawLine(x1, y, x1, y);
            }
        }
    }
    
    public void drawGroup(GC gc, FormComponent fc) {
        int pos = getIndexOf(fc);
        if(isExpandable(fc)) {
            drawGroup(gc, pos, true);
            drawGroup(gc, pos, false);
        } else {
            drawGroup(gc, pos, !isAnchored(fc, true));
        }
    }
    
    private void drawGroup(GC gc, int index, boolean trailingEdge) {
        
        if(groupLayout == null)
            populateGroup();
        
        if(vertical) {
            gc.setForeground(ColorManager.getColor(150, 150, 250));
            gc.setBackground(ColorManager.getColor(150, 150, 250));
        } else {
            gc.setForeground(ColorManager.getColor(250, 150, 150));
            gc.setBackground(ColorManager.getColor(250, 150, 150));
        }
        
        int OVERHANG = 4;
        
        int[] b = getLimits();
        
        int[] r = getPerpendiculars();
        r[0] -= OVERHANG;
        r[1] += OVERHANG;
        
        if(isSequential()) {
            int pos0 = b[0];
            if(trailingEdge)
                pos0 = b[1];
            int pos1 = -1, pos2 = -1;
            int nextMid = -1;
            int ind1, ind2, dirn;
            if(trailingEdge) {
                ind1 = getElementCount() - 1;
                ind2 = index > -1 ? index - 1  : -1;
                dirn = -1;
            } else {
                ind1 = 0;
                ind2 = index > -1 ? index + 1 : getElementCount();
                dirn = 1;
            }
            for(int i = ind1; i != ind2; i += dirn) {
                Object s = getGroupOrFC(i);
                int[] rb = null;
                int[] p = null;
                int midPt = (r[0]+r[1])/2;
                if(s != null) {
                    rb = getBounds(i);
                    p = getPerpendiculars(i);
                    midPt = (p[0]+p[1])/2;
                }
                Object nextObj = null;
                if(trailingEdge)
                    nextObj = getPreviousObject(i, false);
                else
                    nextObj = getNextObject(i, false);
                
                if(nextObj != null) {
                    int ni = getIndexOf(nextObj);
                    int[] np = getPerpendiculars(ni);
                    if(np != null)
                        nextMid = (np[0]+np[1])/2;
                }
                int p0 = p != null ? p[0] : -1;
                int p1 = p != null ? p[1] : -1;
                if(nextMid != -1 && p != null) {
                    if(nextMid < p[0])
                        p0 = nextMid;
                    if(nextMid > p[1])
                        p1 = nextMid;
                }
                if(s instanceof LayoutGroup) {
                    LayoutGroup lg = (LayoutGroup)s;
                    if(lg.isParallel()
//                            && lg.getAlignment() == GroupLayout.BASELINE
                    ) {
                        if(lg.getAlignmentFromElements() == GroupLayout.LEADING) {
//                            rb[1] = rb[0];
                        } else if(lg.getAlignmentFromElements() == GroupLayout.BASELINE) {
                            rb[0] = rb[1] = lg.getBaseLine();
                        } else {
//                            rb[0] = rb[1];
                        }
                        if(i != index) {
                            if(vertical) {
                                drawDottedLine(gc, p0, rb[0], p1, rb[0]);
                                drawDottedLine(gc, p0, rb[1], p1, rb[1]);
                            } else {
                                drawDottedLine(gc, rb[0], p0, rb[0], p1);
                                drawDottedLine(gc, rb[1], p0, rb[1], p1);
                            }
                        }
                    } else {
                        if(i != index) {
                            if(vertical) {
                                drawDottedLine(gc, p[0], rb[0], p[1], rb[0]);
                                drawDottedLine(gc, p0, rb[1], p1, rb[1]);
                            } else {
                                drawDottedLine(gc, rb[0], p[0], rb[0], p[1]);
                                drawDottedLine(gc, rb[1], p0, rb[1], p1);
                            }
                        }
                    }
                    if(trailingEdge) {
                        pos2 = rb[0];
                        pos1 = rb[1];
                    } else {
                        pos1 = rb[0];
                        pos2 = rb[1];
                    }
                }
                if(s instanceof FormComponent) {
                    if(i != index) {
                        if(vertical) {
                            drawDottedLine(gc, p[0], rb[0], p[1], rb[0]);
                            drawDottedLine(gc, p0, rb[1], p1, rb[1]);
                        } else {
                            drawDottedLine(gc, rb[0], p[0], rb[0], p[1]);
                            drawDottedLine(gc, rb[1], p0, rb[1], p1);
                        }
                    }
                    if(trailingEdge) {
                        pos2 = rb[0];
                        pos1 = rb[1];
                    } else {
                        pos1 = rb[0];
                        pos2 = rb[1];
                    }
                }
                if(rb != null && pos0 != pos1) {
                    gc.setLineStyle(SWT.LINE_SOLID);
                    if(vertical) {
                        if(trailingEdge) {
                            drawSolidLine(gc, midPt, pos1, midPt, pos0);
                            gc.fillPolygon(new int[] {midPt-3, pos0-4, midPt+4, pos0-4, midPt, pos0});
                        } else {
                            drawSolidLine(gc, midPt, pos0, midPt, pos1);
                            gc.fillPolygon(new int[] {midPt-4, pos0+4, midPt+5, pos0+4, midPt, pos0});
                        }
                    } else {
                        if(trailingEdge) {
                            drawSolidLine(gc, pos1, midPt, pos0, midPt);
                            gc.fillPolygon(new int[] {pos0-4, midPt-4, pos0, midPt, pos0-4, midPt+4});
                        } else {
                            drawSolidLine(gc, pos0, midPt, pos1, midPt);
                            gc.fillPolygon(new int[] {pos0+4, midPt-4, pos0, midPt, pos0+4, midPt+4});
                        }
                    }
                }
                if(pos2 != -1)
                    pos0 = pos2;
                nextMid = midPt;
            }
        }
        
        if(vertical) {
            if(getAlignmentFromElements() == GroupLayout.BASELINE) {
                int y = getBaseLine();
                drawDottedLine(gc, r[0], y, r[1], y);
            } else if(!trailingEdge && getAlignmentFromElements() == GroupLayout.LEADING)
                drawDottedLine(gc, r[0], b[0], r[1], b[0]);
            else
                drawDottedLine(gc, r[0], b[1], r[1], b[1]);
            
        } else {
            if(!trailingEdge && getAlignmentFromElements() == GroupLayout.LEADING)
                drawDottedLine(gc, b[0], r[0], b[0],  r[1]);
            else
                drawDottedLine(gc, b[1], r[0], b[1],  r[1]);
        }
        if(parent != null) {
            parent.drawGroup(gc, getIndexInParent(), trailingEdge);
        }
    }
    
    public Vector getContainedFCs() {
        Vector fcs = new Vector();
        for (int i = 0; i < addedElements.size(); i++) {
            Object fco = getGroupOrFC(i);
            if(fco instanceof FormComponent) {
                fcs.add(fco);
            } else if(fco instanceof LayoutGroup) {
                fcs.addAll(((LayoutGroup)fco).getContainedFCs());
            }
        }
        return fcs;
    }
    
    public GroupLayout.Group getGroup() {
        return group;
    }
    
    public GroupLayout.Group populateGroup() {
        try {
//            System.out.println("POPULATING GROUP "+this);
//            FormEditor.mark(false, "POPULATING GROUP "+this);
            groupLayout = (GroupLayout) lw.getObject(true);
            group = null;
            if(isSequential())
                group = groupLayout.createSequentialGroup();
            else {
                try {
                    group = groupLayout.createParallelGroup(alignment, resizable);
                } catch(Throwable t) {
                    t.printStackTrace();
                }
                if(group == null)
                    group = groupLayout.createParallelGroup();
            }
            FormComponent par = lw.getFormComponent();
            for (int i = 0; i < addedElements.size(); i++) {
                Object[] args1 = getArgs(i);
                Object[] args = null;
                Class[] types = null;
                if(args1 != null) {
                    args = new Object[args1.length];
                    types = new Class[args.length];
                }
                if(args != null) {
                    for (int j = 0; j < args.length; j++) {
                        Object obj = args1[j];
                        args[j] = args1[j];
                        if(args[j] == null) {
                            System.err.println("ARGS["+j+"] is null for "+this);
                        }
                        if(obj instanceof FormComponent) {
                            FormComponent fc = (FormComponent)obj;
                            
                            if(fc.getComponent() == null)
                                fc.populateComponents((Container)par.getComponent(), par.getEditor());
                            else
                                fc.populateGroupLayout();
                            
                            args[j] = fc.getObject(true);
                        } else if(obj instanceof LayoutGroup) {
                            LayoutGroup lg = (LayoutGroup)obj;
                            lg.setParent(this, i);
                            args[j] = lg.populateGroup();
                        }
                        if(args[j] == null) {
                            System.err.println("ARGS["+j+"] is null for "+this);
                        }
                        types[j] = args[j].getClass();
                        if(types[j].equals(Integer.class))
                            types[j] = int.class;
                        if(types[j].equals(Short.class))
                            types[j] = int.class;
                        if(types[j].equals(Boolean.class))
                            types[j] = boolean.class;
                        if(getAddMethod(i).equals("addPreferredGap") 
                                &&  Cacher.isAssignableFrom(JComponent.class, types[j]))
                            types[j] = JComponent.class;
                        else if( Cacher.isAssignableFrom(Component.class, types[j]))
                            types[j] = Component.class;
                        if( Cacher.isAssignableFrom(GroupLayout.Group.class, types[j]))
                            types[j] = GroupLayout.Group.class;
                    }
                }
                try {
                    group.getClass().getMethod(getAddMethod(i), types).invoke(group, args);
                } catch (Throwable e) {
                    System.err.println("ERROR INVOKING GROUP LAYOUT METH "+getAddMethod(i)
                            +", "+this+", "+e+", "+e.getCause());
                    for (int j = 0; j < args.length; j++) {
                        Object arg = args[j];
                        System.err.println("ARG "+j+" = "+arg);
                    }
                }
            }
            
            //now link sizes
            if(parent == null) {
                Vector linkedObjects = lw.getLinkedElements(vertical);
                if(linkedObjects != null) {
                    for (int i = 0; i < linkedObjects.size(); i++) {
                        Vector elems = (Vector) linkedObjects.elementAt(i);
                        Component[] comps = new Component[elems.size()];
                        for (int j = 0; j < elems.size(); j++) {
                            if(elems.elementAt(j) instanceof FormComponent) {
                                comps[j] = ((FormComponent)elems.elementAt(j)).getComponent();
                            } else if(elems.elementAt(j) instanceof Component) {
                                comps[j] = (Component)elems.elementAt(j);
                            }
                        }
                        groupLayout.linkSize(comps, vertical ? GroupLayout.VERTICAL : GroupLayout.HORIZONTAL);
                    }
                }
            }
            return group;
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
    
    public LayoutGroup addGroup(int pos, boolean parallel) {
      return addGroup(pos, parallel, -1);  
    }
    
    public LayoutGroup addGroup(int pos, boolean parallel, int alignment) {
        LayoutGroup lg;
        if(parallel) {
            Object[] args = null;
//            if(alignment > 0)
//                args = new Object[] {new Integer(alignment)};
//            else
                args = new Object[]{};
            lg = new LayoutGroup("createParallelGroup", args, getLayoutWrapper(), vertical);
        } else {
            lg = new LayoutGroup("createSequentialGroup", null, getLayoutWrapper(), vertical);
        }
        add(pos, new GroupElement("add", new Object[] {lg}));
        lg.setParent(this, pos);
        return lg;
    }
    
    public void add(FormComponent fc, int pos, int gap) {
        add(fc, pos, gap, -1);
    }

    public void add(int pos, int gap) {
        if(gap < 0) {
            System.err.println("Gap is negative: "+gap);
            new Exception().printStackTrace();
            gap = SnapTo.GAP_RELATED;
        }
        if(gap < 6) {
            System.err.println("Gap is less than 6: "+gap);
            new Exception().printStackTrace();
            gap = 6;
        }
        add(pos, new GroupElement("add", new Object[] {new Integer(gap) }));
    }

    public void add(FormComponent fc, int pos, int gap, int alignment) {
        
        //add a gap before component, unless this is the first element
        if(gap != 0 && addGap(pos, gap) && pos != -1)
            pos++;
        
        Integer prefI = getPreferredSize(fc, isVertical());
        
        Object[] args = null;
//        if(alignment < 0) {
//        	if (prefI.equals(PREF_SIZE)) {
//        		args = new Object[] { fc };
//        	} else {
//        		args = new Object[] { fc };
//        	}
//        } else {
//            if(prefI.equals(PREF_SIZE)) {
//                args = new Object[] {new Integer(alignment), fc};
//            } else {
//                args = new Object[] {new Integer(alignment), fc};
//            }
//        }
        if(alignment < 0) {
        	if (prefI.equals(PREF_SIZE)) {
        		args = new Object[] { fc, PREF_SIZE, DEF_SIZE, PREF_SIZE };
        	} else {
        		args = new Object[] { fc, PREF_SIZE, prefI, PREF_SIZE };
        	}
        } else {
            if(prefI.equals(PREF_SIZE)) {
                args = new Object[] {new Integer(alignment), fc, PREF_SIZE, DEF_SIZE, PREF_SIZE};
            } else {
                args = new Object[] {new Integer(alignment), fc, PREF_SIZE, prefI, PREF_SIZE};
            }
        }
        
        add(pos, new GroupElement("add", args));
        
    }

    /**
     * @param fc
     * @return
     */
    public static Integer getPreferredSize(FormComponent fc, boolean vertical) {
//        int pref0 = GroupLayout.PREFERRED_SIZE;
        int pref0 = GroupLayout.DEFAULT_SIZE;
        int pref = pref0;
        Dimension dim = null;
        boolean prefSet = fc.isPropertySet("preferredSize");
        if(fc.getComponent() != null) {
        	dim = fc.getComponent().getPreferredSize();
        } else if(prefSet) {
        	Point ps = (Point) fc.getRawPropertyValue("preferredSize");
        	dim = new Dimension(ps.x, ps.y);
        }
        boolean hasValidPrefSize = SnapTo.allowBaselineMatch(fc);
        Rectangle b = fc.getBoundsRelativeToRoot();
        if(vertical) {
            pref = b.height;
            if(hasValidPrefSize && !prefSet && dim != null && pref == dim.height)
                pref = pref0;
        } else {
            pref = b.width;
            if(hasValidPrefSize && !prefSet && dim != null && pref == dim.width)
                pref = pref0;
        }
        return new Integer(pref);
    }

    /**
     * Creates and returns a sequential group, first cutting out the element
     * at position "pos", replacing it with a sequential group, and inserting
     * the cut element in the new group
     */
    public LayoutGroup createSequentialGroup(int pos) {
        GroupElement elem = remove(pos, false);
        LayoutGroup seq = addGroup(pos, false);
        seq.add(0, elem);
        return seq;
    }
    
    /**
     * If this is a sequential group, creates and returns a parallel group, first cutting out the element
     * at position "pos" (or if includeAfter is true, all the elements after pos too)
     * replacing it with a parallel group, and inserting the cut element(s) in the new group
     */
    public LayoutGroup createParallelGroup(int pos, boolean includeAfter) {
//        if(isParallel())
//            return null;
        if(includeAfter) {
            LayoutGroup cut = getGroupFrom(pos);
            LayoutGroup seq = addGroup(pos, true);
            seq.add(0, cut);
            return seq;
        } else {
            GroupElement elem = remove(pos, false);
            LayoutGroup seq = addGroup(pos, true);
            seq.add(0, elem);
            return seq;
        }
    }
            
    public void addContainerGap(int pos, int min, int max) {
        if(min < 0 || max < 0) {
            System.err.println("Illegal container gap size "+min+", "+max);
            min = max = 0;
        }
        if(max == 0)
            add(pos, new GroupElement("addContainerGap" , null));
        else
            add(pos, new GroupElement("addContainerGap", new Object[]{new Integer(min), new Integer(max)} ));
    }
    
    public void addGap(int pos, int min, int pref, int max) {
        if(min < 0)
            min = 0;
        if(pref < min)
            pref = min;
        if(max < pref)
            max = pref;
        GroupElement gap = new GroupElement("add", new Object[] {
                new Integer(min), new Integer(pref), new Integer(max) });
        gap.setSize(pref);
        add(pos, gap);    
    }
    
    public boolean addGap(int pos, int gap) {
        return addGap(pos, gap, null, null);
    }
       
    public boolean addGap(int pos, int gap, FormComponent fc1, FormComponent fc2) {
//        if(gap == 0 || addedElements.size() == 0)
        if(gap == 0)
            return false;
        if(gap == SnapTo.GAP_UNRELATED) {
            add(pos, new GroupElement("addPreferredGap", 
                    new Object[] {UNRELATED_INT }));
        } else if(gap == SnapTo.GAP_INDENT) {
            add(pos, new GroupElement("addPreferredGap", 
                    new Object[] { fc1, fc2, INDENT_INT }));
        } else if(gap == SnapTo.GAP_CONTAINER) {
            add(pos, new GroupElement("addContainerGap", null ));
        } else if(gap == SnapTo.GAP_RELATED || gap < 6) {
            //need to add the "6, 6" because of what appears to be a bug in GroupLayout
            add(pos, new GroupElement("addPreferredGap", 
                  new Object[] {RELATED_INT }));
//                  new Object[] {RELATED_INT, RELATED_INT, RELATED_INT }));
//                    new Object[] {RELATED_INT, MIN_GAP_INT, MIN_GAP_INT }));
        } else {
            if(gap < 0)
                return false;
            GroupElement gapElem = new GroupElement("add", new Object[] {new Integer(gap) });
            gapElem.setSize(gap);
            add(pos, gapElem);
        }
        return true;
    }
    
    /**
     * Removes the element containing the given LayoutGroup or FormComponent
     * and returns it after changing the gap (if one exists) beside it to preserve
     * the rest of the group's elements positions
     */
    public GroupElement remove(Object lgOrFc) {
        return remove(lgOrFc, true);
    }
    
    public GroupElement remove(Object lgOrFc, boolean preserveSpacing) {
        int pos = getIndexOf(lgOrFc);
        if(pos == -1 && lgOrFc instanceof FormComponent) {
            LayoutGroup lg = getGroupContaining((FormComponent) lgOrFc);
            if(lg == null)
                throw new RuntimeException("Unable to find FC "+lgOrFc);
            return lg.remove(lgOrFc, preserveSpacing);
        }
        if(lgOrFc instanceof LayoutGroup)
            ((LayoutGroup)lgOrFc).setParent(null, -1);
        return remove(pos, preserveSpacing);
    }

    public void removePreferredGap(int pos) {
        if(isPreferredGap(pos)) {
            addedElements.remove(pos);
        } else if(pos > 0 && isPreferredGap(pos-1)) {
            addedElements.remove(pos-1);
        }
    }
    
    /**
     * Returns the first FC or LG in this LG after pos
     */
    public Object getNextObject(int pos, boolean stayInGroup) {
        if(pos+1 >= getElementCount()) {
            if(!stayInGroup && parent != null) {
                return parent.getNextObject(getIndexInParent(), stayInGroup);
            }
            return null;
        }
        Object obj = getGroupOrFC(pos+1);
        if(obj != null)
            return obj;
        return getNextObject(pos+1, stayInGroup);
    }
    
    /**
     * Returns the first FC or LG in this LG after pos
     */
    public Object getPreviousObject(int pos, boolean stayInGroup) {
        if(pos <= 0) {
            if(!stayInGroup && parent != null) {
                return parent.getPreviousObject(getIndexInParent(), stayInGroup);
            }
            return null;
        }
        Object obj = getGroupOrFC(pos-1);
        if(obj != null)
            return obj;
        return getPreviousObject(pos-1, stayInGroup);
    }
    
    int getSpringSize(int pos) {
        Spring s = getSpring(pos);
        if(s == null) {
            if(group != null)
            System.out.println("No Spring at position "+pos+", numElems="+getElementCount()
                    +", numSprings="+getSpringCount());
            return 0;
        }
        return s.getSize();
    }

    /*
	 * If preserveSpacing is true, then if there is a non-preferred gap next to the
	 * removed form component (if the removed object is a FC) then change it's
	 * size to preserve the gap after the removal. Also need to ensure that
	 * resizing only occurs if a FC is removed from this group and not just
	 * resized or moved inside this group
	 */
    public GroupElement remove(int pos, boolean preserveSpacing) {
        GroupElement elem = (GroupElement) addedElements.get(pos);
        if(!preserveSpacing) {
            addedElements.remove(pos);
            return elem;
        }
        int size = elem.getSize();
        addedElements.remove(pos);
        int pos2 = pos;
        LayoutGroup lg = this;
        if(isParallel()) {
            if(getElementCount() > 0)
                return elem;
            if(parent != null) {
                pos = getIndexInParent();
                pos2 = pos+1;
                lg = parent;
            }
        }
        if(lg.isGap(pos2)) {
            if (pos2 != lg.getElementCount() - 1 )
                lg.changeGapSize(pos2, size);
        } else if (lg.isGap(pos-1)) {
            if (pos - 1 != lg.getElementCount() - 1 )
                lg.changeGapSize(pos-1, size);
        } else {
            if (pos != lg.getElementCount() )
                lg.addGap(pos, size);
        }
        return elem;
    }

    public void changeGapSize(int pos, int space) {
        setGapSize(pos, getElement(pos).getSize() + space);
    }

    public void setGapSize(int pos, int space) {
        getElement(pos).setGapSize(space);
    }

    private void setArgs(int pos, Object[] args) {
        GroupElement elem = getElement(pos);
        elem.setArgs(args);
    }
    
    /**
     * true if adding a container gap as the first element makes sense
     */
    public boolean isAtStartOfChain() {
        if(getParent() == null)
            return true;
        if(parent.isSequential() && getIndexInParent() != 0)
            return false;
        return parent.isAtStartOfChain();
    }
    
    /**
     * true if adding a container gap as the last element makes sense
     */
    public boolean isAtEndOfChain() {
        if(getParent() == null)
            return true;
        if(parent.isSequential() && getIndexInParent() != getParent().getElementCount() - 1)
            return false;
        return parent.isAtEndOfChain();
    }
    
    public void cleanup() {

        int nonGapCnt = 0;
        int grpCnt = 0;
        
        for(int i=0; i < getElementCount(); i++) {
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                grpCnt++;
                LayoutGroup g = (LayoutGroup) o;
                if(!equals(g.getParent())) {
                    System.err.println("PARENT WRONG for "+g+
                            "\nPAR="+g.getParent()+
                            "\nPAR should be "+this);
                    g.setParent(this, i);
                }
                g.cleanup();
            }
            
            //since the individual elements' alignments should be set, don't set group's alignment
            //to trailing since this can lead to unexpected movement when elements are moved
//            if(
                    //grpCnt != 0 && 
//                    isParallel() && alignment == GroupLayout.TRAILING)
//                setAlignment(GroupLayout.LEADING);
            
        }
        for(int i=0; i < getElementCount(); i++) {
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                if(g.getElementCount() == 0) {
                    addedElements.remove(i);
                    i--;
                    g = null;
                    continue;
                } else if(g.getElementCount() == 1) {
                    LayoutGroup gg = g.getGroup(0);
                    FormComponent fc = g.getFormComponent(0);
                    int align = getAlignment(i);
                    if(gg != null) {
                        //move this group to replace it's parent group
                        setElement(i, g.getElement(0));
                        g = gg;
                    } else if(fc != null) {
                        //move this FC to replace it's parent group
                        GroupElement elem = g.getElement(0);
                        if(isSequential()) {
                            setAlignment(elem, REMOVE_ALIGNMENT);
                        } else {
                            //is this going to work? is it useful?
                            if(align != -1)
                            setAlignment(elem, align);
                        }
                        setElement(i, elem);
                    }
                    if(align != -1 && isParallel())
                        getElement(i).setAlignment(align);
                }
                if((isSequential() && g.isSequential())) {
                    //absorb group
                    addedElements.remove(i);
                    int gc = g.getElementCount();
                    for(int j=0; j < gc; j++)
                        add(i+j, g.getElement(j));
                    i += gc-1;
                }
//              if((isSequential() && g.isSequential())
//              || (isParallel() && g.isParallel() && getAlignment() == g.getAlignment())
//          ) {
//          //absorb group
//          creationMethod = g.creationMethod;
//          creationArgs = g.creationArgs;
//          addedElements.remove(i);
//          int gc = g.getElementCount();
//          for(int j=0; j < gc; j++)
//              add(i+j, g.getElement(j));
//          i += gc-1;
//      }
                nonGapCnt++;
            } else if(o instanceof FormComponent) {
                nonGapCnt++;
            } else {
                //gap
                if(isParallel()) {
                    addedElements.remove(i);
                    i--;
                }
            }
//            if(i == getElementCount() - 1
//                    &&
//                    isSequential() 
//                    && parent != null && parent.isParallel()
//                    && (isPreferredGap(i) || isNonPreferredGap(i))) {
//                addedElements.remove(i);
//                i--;
//            }

        }
        
        if(nonGapCnt == 0) {
            //if it's a group (seq or par) made up of gaps, remove it
            addedElements.clear();
        }

        if(getElementCount() == 1) {
            LayoutGroup g = getGroup(0);
            if(g != null) {
//                    && ((isSequential() && g.isSequential()) || 
//                            (isParallel() && g.isParallel() && getAlignment() == g.getAlignment()))) {
                addedElements.clear();
                creationMethod = g.creationMethod;
                creationArgs = g.creationArgs;
                int gc = g.getElementCount();
                for(int j=0; j < gc; j++)
                    add(j, g.getElement(j));
            }
        }

        cleanupExtraGaps();
    }
    
    public boolean parentIsParallel() {
        return parent != null && parent.isParallel();
    }
    
    public boolean parentIsSequential() {
        return parent != null && parent.isSequential();
    }
    
    public void cleanupExtraGaps() {
        if(isSequential() && isFixedPreferredGap(0)) {
            //for the situation <prefGap>{Par grp {Seq grp <pref gap> ...}}
            // - second pref gap does nothing and can even mess up layout
//            GroupElement prev = getElement(0).getPreviousElement();
//            if(prev != null && prev.isFixedPreferredGap())
//                addedElements.remove(0);
        }
        
        //remove gaps at ends of seq groups inside par groups
//        if(isSequential() && isFinalGroup()) {
//            while(getElementCount() > 0
//                    && getLastElement().isGap() && !getLastElement().isContainerGap()) {
//                addedElements.remove(getElementCount()-1);
//            }
//        }
        
        if(isParallel() && parentIsSequential()) {
            GroupElement startGap = null, endGap = null;
            boolean startMatch = true, endMatch = true;
            for(int i=0; i<getElementCount();i++) {
                
                LayoutGroup lg = getGroup(i);
                if(lg == null || !lg.isSequential()) {
                    startMatch = endMatch = false;
                    break;
                }
                if(startMatch && lg.getFirstElement().isGap()) {
                    if(startGap == null) {
                        startGap = lg.getFirstElement();
                    } else {
                        if(!startGap.matchesGap(lg.getFirstElement()))
                            startMatch = false;
                    }
                } else {
                    startMatch = false;
                }
                if(endMatch && lg.getLastElement().isGap()) {
                    if(endGap == null) {
                        endGap = lg.getLastElement();
                    } else {
                        if(!endGap.matchesGap(lg.getLastElement()))
                            endMatch = false;
                    }
                } else {
                    endMatch = false;
                }
            }
            if(startMatch || endMatch) {
                for(int i=0; i<getElementCount();i++) {
                    LayoutGroup lg = getGroup(i);
                    if(startMatch) {
                        GroupElement gap = lg.remove(0, false);
                        if(i == 0)
                            parent.add(getIndexInParent(), gap);
                    }
                    if(endMatch) {
                        GroupElement gap = lg.remove(lg.getElementCount()-1, false);
                        if(i == 0)
                            parent.add(getIndexInParent()+1, gap);
                    }
                }
            }
        }
        for(int i=0; i<getElementCount();i++) {

            GroupElement elem = getElement(i);
            if(elem.isZeroGap()) {
                addedElements.remove(i);
                i--;
                continue;
            }
            
            if(isFixedPreferredGap(i))
                elem.fixPreferredGapBug();
            
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.cleanupExtraGaps();
            }
            if(isGap(i) && isGap(i+1)) {
                boolean expands = false;
                if(isExpandableGap(i) || isExpandableGap(i+1))
                    expands = true;
                if(isContainerGap(i)) {
                    //                setGapSize(i, getElement(i+1).getSize());
                    changeGapSize(i, getElement(i+1).getSize());
                    addedElements.remove(i+1);
                    i--;
                } else if(isPreferredGap(i)) {
                    changeGapSize(i, getElement(i+1).getSize());
                    addedElements.remove(i+1);
                    i--;
                } else if(isContainerGap(i+1)) {
                    //                setGapSize(i+1, getElement(i).getSize());
                    changeGapSize(i+1, getElement(i).getSize());
                    addedElements.remove(i);
                    i--;
                } else if(isPreferredGap(i+1)) {
                    changeGapSize(i+1, getElement(i).getSize());
                    addedElements.remove(i);
                    i--;
                } else {
                    changeGapSize(i, getElement(i+1).getSize());
                    addedElements.remove(i+1);
                    i--;
                }
                getElement(i+1).setExpandable(expands);
            }
        }
    }
    
    /**
     * @return true if this group has nothing following it
     */
    private boolean isFinalGroup() {
        if(parentIsSequential() && !isLastInParent())
            return false;
        if(parent == null)
            return true;
        return parent.isFinalGroup();
    }

    public void cleanupOutsideGaps() {
//        if(true)
//            return;
        for(int i=0; i<getElementCount();i++) {
            if(isGap(i)) {
                addedElements.remove(i);
                i--;
            } else
                break;
        }
        for(int i=getElementCount()-1; i >= 0; i--) {
            if(isGap(i)) {
                addedElements.remove(i);
            } else
                break;
        }
        for(int i=getElementCount()-1; i >= 0; i--) {
            LayoutGroup lg = getGroup(i);
            if(lg != null) {
                lg.cleanupOutsideGaps();
            }
        }
    }

    public void cleanupContainerGaps() {
        if(true)
            return;
        for(int i=0; i<getElementCount();i++) {
            Object o = getGroupOrFC(i);
            if(o == null && i == 1 && isContainerGap(0)) {
                addedElements.remove(i);
                i--;
                continue;
            }
            if(o == null && i == getElementCount()-2 && isContainerGap(getLastElement())) {
                addedElements.remove(i);
                i--;
                continue;
            }
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.cleanupContainerGaps();
            }
            if(isSequential() && isContainerGap(getElement(i)) ) {
                boolean atStart = isAtStartOfChain();
                boolean atEnd = isAtEndOfChain();
                if(i != 0 && i != getElementCount() - 1 
                        && ((atEnd && atStart) || (!atEnd && !atStart))) {
                    //container gap needs to be removed
                    addedElements.remove(i);
                    i--;
                } else {
                    //if it is both at the end and at the start, do nothing!
                    //check for container gap in wrong place!
                    if(atEnd && !atStart) {
                        if( i != getElementCount()-1) {
                            if(!isContainerGap(getLastElement())) {
                                add((GroupElement) addedElements.remove(i));
                                i--;
                            } else {
                                addedElements.remove(i);
                                i--;
                            }
                        }
                    } else if(atStart && !atEnd) {
                        if( i != 0) {
                            if(!isContainerGap(getFirstElement())) {
                                add((GroupElement) addedElements.remove(i));
                            } else {
                                addedElements.remove(i);
                                i--;
                            }
                        }
                    }
                }
            }
        }
    }
    
//    private void absorbGroup(int pos) {
//        LayoutGroup lg = getGroup(pos);
//        Vector elems = lg.addedElements;
//        addedElements.remove(pos);
//        for(int i=0; i<elems.size(); i++) {
//            LayoutGroup o = lg.getGroup(i);
//            if(o != null)
//                o.setParent(this, pos+i);
//            GroupElement elem = (GroupElement)elems.elementAt(i);
//            add(pos + i, elem);
//        }
//    }
        
    public int getElementCount() {
        return addedElements.size();
    }

    public int getIndexInParent() {
        if(parent == null)
            return 0;
        for(int i=0;i<parent.addedElements.size(); i++) {
            Object lg = parent.getGroupOrFC(i);
            if(this.equals(lg))
                return i;
        }
        return -1;
    }
    
    private boolean hasBaselineElement() {
        if(!isParallel())
            return false;
        for(int i=0; i<getElementCount(); i++) {
            if(getElement(i).getAlignment() == GroupLayout.BASELINE)
                return true;
        }
        return false;
    }
    
    /**
     * Returns the alignment (if this is a parallel group) of the element
     * at position.
     */
    public int getAlignment(int pos) {
        return getElement(pos).getAlignment();
//        Object[] args = getArgs(pos);
//        if(args.length > 0 && args[0] instanceof Integer)
//            return ((Integer)args[0]).intValue();
//        return -1;
    }
    
    public int getAlignmentFromElements() {
        if(!explicitAlignment && getElementCount() > 0)
            return getAlignment(0);
        return alignment;
    }
    
    private void setAlignment(GroupElement elem, int newAlignment) {
        if(isSequential())
            newAlignment = REMOVE_ALIGNMENT;

        if(newAlignment == LEAVE_ALIGNMENT)
            return;
        
//        int 
        alignment = newAlignment;
        if(newAlignment == REMOVE_ALIGNMENT)
            alignment = GroupLayout.LEADING;
        elem.setAlignment(newAlignment);
    }
    
    public void repairInCode(JavaCodeParser jcm) {
        jcm.repairLayoutGroup(getLayoutWrapper(), vertical);
    }
    
    public String getJavaCode(JavaCodeParser jcm, String indent) {
        boolean useJava6GL = lw.getFormComponent().getEditor().useJava6GroupLayout();
        if(useJava6GL) {
            jcm.addImport("javax.swing.LayoutStyle");
            jcm.addImport("javax.swing.GroupLayout");
            jcm.removeImport("org.jdesktop.layout.LayoutStyle");
            jcm.removeImport("org.jdesktop.layout.GroupLayout");
        } else {
            jcm.removeImport("javax.swing.LayoutStyle");
            jcm.removeImport("javax.swing.GroupLayout");
            jcm.addImport("org.jdesktop.layout.LayoutStyle");
            jcm.addImport("org.jdesktop.layout.GroupLayout");
        }
        String code = "";
        String loName = lw.getNameInCode();
        code = loName+"."+creationMethod+"(";
        
        if(creationArgs != null) {
            for (int i = 0; i < creationArgs.length; i++) {
                Object arg = creationArgs[i];
                if(i != 0)
                    code += ", ";
                code += getField(arg, null, -1, null, jcm);
            }
        }
        code += ")";
        for (int i = 0; i < addedElements.size(); i++) {
            Object[] elem = getArgs(i);
            String addMeth = getAddMethod(i);
            if(useJava6GL) {
                boolean switchFirstTwo = false;
                if(isAddPreferredGap(i)) {
                    code += jcm.getNL()+indent+".addPreferredGap(";
                } else if(isAddComponent(i)) {
                    code += jcm.getNL()+indent+".addComponent(";
                    if(elem != null && elem.length >= 2 && elem[0] instanceof Integer)
                        switchFirstTwo = true;
                } else if(isAddGroup(i)) {
                    code += jcm.getNL()+indent+".addGroup(";
                } else if(addMeth.equals("add")) {
                    code += jcm.getNL()+indent+".addGap(";
                } else {
                    code += jcm.getNL()+indent+"."+addMeth+"(";
                }
                //TODO switch elements if needed!
                if(elem != null) {
                    for (int j = 0; j < elem.length; j++) {
                        Object ob = elem[j];
                        if(switchFirstTwo) {
                            if(j == 0)
                                ob = elem[1];
                            if(j == 1)
                                ob = elem[0];
                        }
                        if(j != 0)
                            code += ", ";
                        if(ob instanceof FormComponent) {
                            String nic = ((FormComponent)ob).getNameInCode();
                            nic = jcm.convertToMethod(((FormComponent)ob).getName(), nic, false);
                            code += nic;
                        } else if(ob instanceof LayoutGroup)
                            code += ((LayoutGroup)ob).getJavaCode(jcm, indent+"    ");
                        else
                            code += getField(ob, elem, j, addMeth, jcm);
                    }
                }
            } else {
                code += jcm.getNL()+indent+"."+addMeth+"(";
                if(elem != null) {
                    for (int j = 0; j < elem.length; j++) {
                        Object ob = elem[j];
                        if(j != 0)
                            code += ", ";
                        if(ob instanceof FormComponent) {
                            String nic = ((FormComponent)ob).getNameInCode();
                            nic = jcm.convertToMethod(((FormComponent)ob).getName(), nic, false);
                            code += nic;
                        } else if(ob instanceof LayoutGroup)
                            code += ((LayoutGroup)ob).getJavaCode(jcm, indent+"    ");
                        else
                            code += getField(ob, elem, j, addMeth, jcm);
                    }
                }
            }
            code += ")";
        }
        return code;
    }
    
    /**
     * @param i
     * @return
     */
    private boolean isAddGroup(int i) {
        return ("add".equals(getAddMethod(i)) && getGroup(i) != null);
    }

    /**
     * @param i
     * @return
     */
    private boolean isAddComponent(int i) {
        return ("add".equals(getAddMethod(i)) && getFormComponent(i) != null);
    }

    /**
     * @param i
     * @return
     */
    private boolean isAddPreferredGap(int i) {
        return "addPreferredGap".equals(getAddMethod(i));
    }

    private String getAlignmentString(boolean useJava6GL, int val) {
        if(val == GroupLayout.BASELINE && vertical) {
            if(useJava6GL)
                return "GroupLayout.Alignment.BASELINE";
            return "GroupLayout.BASELINE";
        }
        if(val == GroupLayout.CENTER) {
            if(useJava6GL)
                return "GroupLayout.Alignment.CENTER";
            return "GroupLayout.CENTER";
        }
        if(val == GroupLayout.LEADING) {
            if(useJava6GL)
                return "GroupLayout.Alignment.LEADING";
            return "GroupLayout.LEADING";
        }
        if(val == GroupLayout.TRAILING) {
            if(useJava6GL)
                return "GroupLayout.Alignment.TRAILING";
            return "GroupLayout.TRAILING";
        }
        return null;
    }
    
    private String getField(Object obj, Object[] args, int pos, String meth, JavaCodeParser jcm) {
        boolean useJava6GL = lw.getFormComponent().getEditor().useJava6GroupLayout();
        int val = -1;
        if(obj instanceof Integer)
            val = ((Integer)obj).intValue();
        else if(obj instanceof Short)
            val = ((Short)obj).intValue();
        else if(obj instanceof Alignment) {
            val = ((Alignment)obj).getVal();
            String code = getAlignmentString(useJava6GL, val);
            if(code != null)
                return code;
        }

        if(val == Integer.MAX_VALUE)
            return "Integer.MAX_VALUE";
        if(val == MAX_SIZE.intValue())
            return "Short.MAX_VALUE";
        if(val == Short.MAX_VALUE)
            return "Short.MAX_VALUE";
        if(val == GroupLayout.DEFAULT_SIZE)
            return "GroupLayout.DEFAULT_SIZE";
        if(val == GroupLayout.PREFERRED_SIZE)
            return "GroupLayout.PREFERRED_SIZE";

        if("addPreferredGap".equals(meth)) {
            if(args[0] instanceof FormComponent) {
                if(pos > 2)
                    return val+"";
            } else {
                if(pos > 0)
                    return val+"";
            }
            if(val == LayoutStyle.RELATED) {
                if(useJava6GL) {
                    jcm.addImport("javax.swing.LayoutStyle");
                    jcm.removeImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.ComponentPlacement.RELATED";
                } else {
                    jcm.addImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.RELATED";
                }
            }
            if(val == LayoutStyle.UNRELATED) {
                if(useJava6GL) {
                    jcm.addImport("javax.swing.LayoutStyle");
                    jcm.removeImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.ComponentPlacement.UNRELATED";
                } else {
                    jcm.addImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.UNRELATED";
                }
            }
            if(val == LayoutStyle.INDENT) {
                if(useJava6GL) {
                    jcm.addImport("javax.swing.LayoutStyle");
                    jcm.removeImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.ComponentPlacement.INDENT";
                } else { 
                    jcm.addImport("org.jdesktop.layout.LayoutStyle");
                    return "LayoutStyle.INDENT";
                }
            }
        }   
        
        if(val > 0 && args != null) {
            if(useJava6GL) {
                if(meth.equals("addComponent") && pos != 1)
                    return obj.toString();
                if(meth.equals("addGroup") && pos != 0)
                    return obj.toString();
            } else {
                if(pos != 0)
                    return obj.toString();
            }
            if(args.length < 2)
                return obj.toString();
            if(!(args[1] instanceof FormComponent) && !(args[1] instanceof LayoutGroup))
                return obj.toString();
        }
        
        String code = getAlignmentString(useJava6GL, val);
        if(code != null)
            return code;

        return val+"";
    }
    
    /**
     * @return
     */
    public LayoutWrapper getLayoutWrapper() {
        return lw;
    }

    public void setLayoutWrapper(LayoutWrapper lw) {
        this.lw = lw;
        for (int i = 0; i < addedElements.size(); i++) {
            Object[] args = getArgs(i);
            if(args != null) {
                for (int j = 0; j < args.length; j++) {
                    Object obj = args[j];
                    if(obj instanceof LayoutGroup) {
                        LayoutGroup lg = (LayoutGroup)obj;
                        lg.setLayoutWrapper(lw);
                    }
                }
            }
        }

    }

    /**
     * 
     */
    public void dump(String indent) {
        try {
            FormComponent par = lw.getFormComponent();
            System.out.println(indent+"Dumping LayoutGroup for "+par+", vertical="+vertical);
            System.out.println(indent + (isSequential() ? "Sequential" : "Parallel"));
            for (int i = 0; i < addedElements.size(); i++) {
                Object[] elem = getArgs(i);
                for (int j = 0; j < elem.length; j++) {
                    Object obj = elem[j];
                    if(obj instanceof FormComponent) {
                        FormComponent fc = (FormComponent)obj;
                        System.out.println(indent+"ELEM ["+i+"] = "+fc);
                    } else if(obj instanceof LayoutGroup) {
                        System.out.println(indent+"ELEM ["+i+"] is LayoutGroup... ");
                        ((LayoutGroup)obj).dump(indent+"    ");
                    }
                }
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 
     */
    public void clear() {
        try {
            group = null;
            for (int i = 0; i < addedElements.size(); i++) {
                Object[] args = getArgs(i);
                if(args != null) {
                    for (int j = 0; j < args.length; j++) {
                        Object obj = args[j];
                        if(obj instanceof LayoutGroup) {
                            LayoutGroup lg = (LayoutGroup)obj;
                            lg.clear();
                        }
                    }
                }
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void setAlignment(FormComponent sel, int align) {
        int groupAlignment = -2;
        for (int i = 0; i < addedElements.size(); i++) {
            int al = getAlignment(i);
            if(al == -1)
                groupAlignment = -1;
            else if(groupAlignment == -2)
                groupAlignment = al;
            else if(groupAlignment != -1 && groupAlignment != al)
                groupAlignment = -1;
            Object obj = getGroupOrFC(i);
            if(sel.equals(obj)) {
                GroupElement elem = getElement(i);
                setAlignment(elem, align);
            }
        }
        if(groupAlignment == align)
            setAlignment(align);
    }

    /**
     * @param fps
     * @return
     */
    public LayoutGroup getCopy(LayoutWrapper fps) {
        return getCopy(fps, true);
    }
    
    public LayoutGroup getCopy(LayoutWrapper fps, boolean useFCCopies) {
        
        LayoutGroup lgc = new LayoutGroup(creationMethod, creationArgs, fps, vertical);
        if(addedElements != null) {
            for (int i = 0; i < addedElements.size(); i++) {
                String addm = getAddMethod(i);
                Object[] args = getArgs(i);
                int size = getSize(i);
                Object[] newArgs = null;
                if(args != null) {
                    newArgs = new Object[args.length];
                    for (int j = 0; j < args.length; j++) {
                        Object ob = args[j];
                        if(ob instanceof FormComponent) {
                            if(useFCCopies) {
                                FormComponent fc = ((FormComponent)ob).getCopiedTo();
                                if(fc != null)
                                    ob = fc;
                            }
                        } else if(ob instanceof LayoutGroup) {
                            LayoutGroup g = (LayoutGroup)ob;
                            ob = g.getCopy(fps, useFCCopies);
                            ((LayoutGroup)ob).setParent(lgc, j);
                        }
                        newArgs[j] = ob;
                    }
                }
                GroupElement elem = new GroupElement(addm, newArgs);
                elem.setSize(size);
                lgc.add(elem);
            }
        }
        return lgc;
    }

    public void setAlignmentAll(int alignment) {
        setAlignment(alignment);
        for(int i=0; i< getElementCount(); i++)
            setAlignment(getElement(i), alignment);
    }
    
    public void setAlignment(int alignment) {
        if(!isParallel())
            return;
        this.alignment = alignment;
        if(creationArgs == null || creationArgs.length == 0)
            creationArgs = new Object[] {new Integer(alignment)};
        else
            creationArgs[0] = new Integer(alignment);
    }
    
    public void toggleAlignment(FormComponent sel) {
        
        if(isSequential())
            return;
        
        int pos = getIndexOf(sel);
        GroupElement elem = getElement(pos);
        Object[] args = getArgs(pos);
        int align = GroupLayout.TRAILING;
        if(args[0] instanceof FormComponent) {
            Object[] na = new Object[args.length+1];
            for(int i=0; i< args.length; i++)
                na[i+1] = args[i];
            args = na;
        } else {
            align = ((Integer)args[0]).intValue();
        }
        
        if(align == GroupLayout.LEADING)
            align = GroupLayout.CENTER;
        //baseline == center == 3
        else if(align == GroupLayout.CENTER)
            align = GroupLayout.TRAILING;
        else if(align == GroupLayout.TRAILING)
            align = GroupLayout.LEADING;
        
        args[0] = new Integer(align);
        
        elem.setArgs(args);
    }

    public void toggleSize(FormComponent sel) {
        int pos = getIndexOf(sel);
        GroupElement elem = getElement(pos);
        Object[] args = getArgs(pos);
        int size = deduceSize(sel);
        int fcPos = 0;
        if(args.length > 1 && args[1] instanceof FormComponent)
            fcPos = 1;
        
        Object[] na = new Object[fcPos+4];
        for(int i=0; i< fcPos+1; i++)
            na[i] = args[i];
        na[fcPos+1] = MIN_GAP_INT;
        na[fcPos+2] = PREF_SIZE;
        
        if(size == SIZE_MAX || size == SIZE_NOT_SET)
            na[fcPos+3] = PREF_SIZE;
        else
            na[fcPos+3] = MAX_SIZE;
        args = na;
        
        elem.setArgs(args);
    }
    
    public int deduceSize(FormComponent sel) {
        int pos = getIndexOf(sel);
        Object[] args = getArgs(pos);
        
        int fcPos = 0;

        if(args.length > 1 && args[1] instanceof FormComponent)
            fcPos = 1;

        if(args.length == fcPos + 4) {
            if(((Integer)args[fcPos+3]).intValue() > 0) {
                return SIZE_MAX;
            } else {
                return SIZE_PREF;
            }
        }
        return SIZE_NOT_SET;
    }
    
    /**
     * Remove all elements of this group that are NOT part
     * of the vector of FormComponents passed in as the
     * argument to this method
     */
    private void purge(Vector fcs) {
        Object nextObj = null;
        Object obj = null;
        for(int i=addedElements.size()-1; i >= 0; i--) {
            obj = getGroupOrFC(i);
            if(obj instanceof FormComponent) {
                FormComponent fc = (FormComponent)obj;
                if(!fcs.contains(fc) && !fcs.contains(fc.getCopiedFrom())) {
                    remove(i, true);
                }
            } else if(obj instanceof LayoutGroup) {
                ((LayoutGroup)obj).purge(fcs);
            }
        }
    }
    
    public boolean isVertical() {
        return vertical;
    }
    
    /**
     * Returns the skeleton group, initially a copy of this
     * group, but purged till it contains just the FormComponents
     * in the vector parameter (with accompanying gap springs)
     */
    public LayoutGroup getSubGroup(Vector fcs) {
        
        if(true)
            return getCompactGroup(fcs, false, null, null, null);
        
        LayoutGroup copy = getCopy(lw);
        copy.purge(fcs);
        copy.cleanup();
        copy.cleanupOutsideGaps();
        return copy;
    }
    
    private Vector constraints;
    
    private void setConstraints(Vector cons) {
        constraints = cons;
    }
    
    public GroupLayoutConstraints getConstraint(FormComponent fc) {
        if(parent != null) {
            return parent.getConstraint(fc);
        }
        for(int i=0; i< constraints.size(); i++) {
            GroupLayoutConstraints con = (GroupLayoutConstraints)constraints.elementAt(i);
            if(fc.equals(con.getFC()))
            	return con;
        }
        return null;
    }
    
    public void prepConstraints() {
        if(constraints == null)
            return;
        for(int i=0; i< constraints.size(); i++) {
            GroupLayoutConstraints con = (GroupLayoutConstraints)constraints.elementAt(i);
            con.prep(this);
        }
    }
        
    public void applyAnchorConstraints() {
            if(constraints == null)
                return;
        for(int i=0; i< constraints.size(); i++) {
            GroupLayoutConstraints con = (GroupLayoutConstraints)constraints.elementAt(i);
            con.applyAnchor(this);
        }
    }
    
    public void applyExpandConstraints() {
        if(constraints == null)
            return;
        for(int i=0; i< constraints.size(); i++) {
            GroupLayoutConstraints con = (GroupLayoutConstraints)constraints.elementAt(i);
            con.applyExpand(this);
        }
    }
    
    /**
     * Attempt to construct a new group based on positions of existing elements
     * Need to be able to decide whether vertical groups are aligned with baselines, and
     * also whether horizontal elements are indented.
     */
    public LayoutGroup getCompactGroup(Vector fcs, 
    		boolean addOffset, SnapTo snapTo,
            LayoutGroup selGrp, FormComponent proxy) {
        
        fcs = (Vector)JiglooUtils.copy(fcs);
        
        if(fcs.size() == 0)
            return newParallelGroup();

        Vector constraints = new Vector();
        for(int i=0;i<fcs.size();i++) {
            FormComponent fc = (FormComponent)fcs.elementAt(i);
            constraints.add(new GroupLayoutConstraints(fc, this));
        }
        
        int insert = 0;
        
        if(snapTo != null) {
            insert = vertical ? snapTo.insertY : snapTo.insertX;
            FormEditor ed = snapTo.getTarget().getEditor();
            Vector sels = null;
            if(selGrp != null && proxy != null) {
                sels = selGrp.getContainedFCs();
                for(int i=0;i<sels.size();i++) {
                    if(!sels.elementAt(i).equals(proxy))
                        fcs.remove(sels.elementAt(i));
                }
            } else {
                sels = (Vector) JiglooUtils.copy(ed.getSelectedComponents());
                sels.remove(snapTo.getTarget());
            }
            if(insert != 0) {
                if(selGrp == null)
                    selGrp = getCompactGroup(sels, false,	null, null, null);
                for(int i=0;i<sels.size();i++) {
                    fcs.remove(sels.elementAt(i));
                }
            }
        }
        
        FormComponent fcPar = null;
        Vector posns = new Vector();
        for(int i=0; i<fcs.size();i++) {
            FormComponent fc = (FormComponent)fcs.elementAt(i);
            fcPar = fc.getParent();
            Rectangle b = fc.getBoundsRelativeToRoot();
            Integer pos1 = new Integer( vertical ? b.y : b.x);
            Integer pos2 = new Integer( pos1.intValue() + (vertical ? b.height : b.width));
            if(!posns.contains(pos1))
                posns.add(pos1);
            if(!posns.contains(pos2))
                posns.add(pos2);
        }
        Collections.sort(posns, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer)o1).intValue() - ((Integer)o2).intValue();
            }
        });
        if(posns.size() == 0)
        	System.out.println("Error in getCompactGroup - no positions");
        int offset = ((Integer)posns.elementAt(0)).intValue();
        LayoutGroup lg = newSequentialGroup();
        
        int cGap = 0;
        for(int i=0; i<fcs.size();i++) {
            FormComponent fc = (FormComponent)fcs.elementAt(i);
            Rectangle b = fc.getBoundsRelativeToRoot();
            if(vertical) {
                if(b.y == offset) {
                    cGap = getContainerGap(fc, fcPar, SwingConstants.NORTH);
                    break;
                }
            } else {
                if(b.x == offset) {
                    cGap = getContainerGap(fc, fcPar, SwingConstants.WEST);
                    break;
                }
            }
        }
        
        int relOffset = offset;
        Rectangle parBnds = null;
        
        if(addOffset) {
        	parBnds = fcPar.getInsideBoundsRelativeToRoot();
            relOffset = offset - (vertical ? parBnds.y : parBnds.x );
            if(snapTo != null && 
                    ((vertical && Math.abs(relOffset - cGap) <= WIGGLE_ROOM)
                    || ( !vertical && Math.abs(relOffset - cGap) <= WIGGLE_ROOM))) {
                lg.addContainerGap(-1, 0, 0);
            } else {
                if(relOffset > 0) {
                    if(snapTo != null && relOffset < cGap)
                        lg.addGap(-1, relOffset);
                    else
                        lg.addContainerGap(-1, relOffset, relOffset);
                }
            }
        }
        FormComponent prevComp = null;
        Vector processed = new Vector();
        for(int i=0; i<posns.size(); i++) {
            
            int pos = ((Integer)posns.elementAt(i)).intValue();
            
            for(int j=0; j<fcs.size(); j++) {
                LayoutGroup newLG = newParallelGroup();
                FormComponent fc = (FormComponent)fcs.elementAt(j);
                Rectangle b = fc.getBoundsRelativeToRoot();
                int pos1 = vertical ? b.y : b.x;
                if(processed.contains(fc) || pos != pos1)
                    continue;
                
                //pos1 equals pos
                
                if(vertical)
                    processParallelMatches(fcs, processed, newLG, fc, GroupLayout.BASELINE);
                processParallelMatches(fcs, processed, newLG, fc, GroupLayout.LEADING);
                //                processParallelMatches(fcs, processed, newLG, fc, GroupLayout.TRAILING);
                
                
                if(processed.contains(fc)) {
                    Vector pfcs = new Vector();
                    newLG.getAllFormComponents(pfcs);
                    b = JiglooUtils.getBoundingRectangle(pfcs);
                }
                
                LayoutGroup relGrp = lg.getPreceedingGroup(pos1, false);
                
                //TODO need to possibly add in other groups which end before (or equal)
                //with the end of relGrp
                //TODO - and TRAILING matches!!!
                if(relGrp != null && relGrp.isFinalGroup() ) {
                    if(relGrp.isSequential() && relGrp.parentIsParallel()) {
                        relGrp = relGrp.createMaximalGroup();
                    }
                    int gap = pos1 - relGrp.getEndPosition();
                    int start = relGrp.getEndPosition();
                    if(gap < 0)
                        gap = 0;
                    
                    if(relGrp.isParallel()) {
                        if(relGrp.parentIsSequential())
                            relGrp = relGrp.getParent();
                        else
                            relGrp.makeSequential();
                    }
                    
                    //relGrp is now sequential
                    boolean canHaveRelatedGap = false;
                    if(processed.contains(fc))
                            canHaveRelatedGap = canHaveRelatedGap(relGrp.getContainedFCs(), newLG.getContainedFCs(), vertical);
                    else
                        canHaveRelatedGap = canHaveRelatedGap(relGrp.getContainedFCs(), fc, vertical);

                    if(canHaveRelatedGap) {
                        int related = -1;
                        int unrelated = -1;
//                        if(prevComp != null) {
                            if(fc.getComponent() instanceof JComponent
                                    && prevComp.getComponent() instanceof JComponent) {
                                related = getPreferredGap(fc, relGrp,
                                        LayoutStyle.RELATED, vertical, 
                                        lw.getFormComponent());
                                unrelated = getPreferredGap(fc, relGrp,
                                        LayoutStyle.UNRELATED, vertical, 
                                        lw.getFormComponent());
//                            }
                        if(Math.abs(gap - related) <= WIGGLE_ROOM)
                            gap = SnapTo.GAP_RELATED;
                        if(Math.abs(gap - unrelated) <= WIGGLE_ROOM)
                            gap = SnapTo.GAP_UNRELATED;
                        }
                    }
                    
                    if(gap != 0) {
                        relGrp.addExactGap(-1, gap);
                    }
                    if(processed.contains(fc)) {
                        relGrp.add(-1, newLG);
                    } else {
                        LayoutGroup sel = newSequentialGroup();
                        sel.add(fc, -1, 0);
                        relGrp.add(-1, sel);
                    }
                    
                
                } else {
                    int indent = -1;
                    if (!vertical && prevComp != null) {
						if (prevComp.getComponent() instanceof JComponent
								&& lw.getFormComponent().getComponent() instanceof JComponent) {
							indent = SnapTo.getPreferredGap(fc,
									(JComponent) prevComp.getComponent(),
									LayoutStyle.INDENT, SwingConstants.EAST,
									(JComponent) lw.getFormComponent()
											.getComponent());
						} else {
							indent = 0;
						}
					}
                    relGrp = lg.getPreceedingGroup(pos1, true);
                    
                    int gap = 0;
                    
                    LayoutGroup seq = null;
                    if(relGrp == null) {
                        seq = lg;
                        lg.startPos = offset - relOffset;
                    } else {
                        seq = newSequentialGroup();
                        gap = pos1 - relGrp.getStartPosition();
                                
                        seq.startPos = relGrp.getStartPosition();
                        
                        if(gap < 0)
                            gap = 0;
                        if(!relGrp.isFinalGroup()) {
                            LayoutGroup par = relGrp.getParent();
                            LayoutGroup lg2 = par.getGroupFrom(relGrp.getIndexInParent());
                            par = par.addGroup(-1, true);
                            par.add(0, lg2);
                            relGrp = par;
                        } else {
                            if(relGrp.isSequential() || relGrp.isBaseline()) {
                                if(relGrp.parentIsParallel() && !relGrp.getParent().isBaseline())
                                    relGrp = relGrp.getParent();
                                else
                                    relGrp.makeParallel();
                            }
                        }
                    }
                    
                    if(indent != -1 && Math.abs(gap - indent) <= WIGGLE_ROOM) {
                        seq.addGap(-1, SnapTo.GAP_INDENT, prevComp, fc);
                    } else {
                        if(gap != 0)
                            seq.addExactGap(-1, gap);
                    }
                    if(processed.contains(fc))
                        seq.add(-1, newLG);
                    else {
                        LayoutGroup seq2 = newSequentialGroup();
                        seq2.add(fc, -1, 0);
                        seq.add(-1, seq2);
                    }
                    
                    if(relGrp != null)
                        relGrp.add(-1, seq);
                }
                
                prevComp = fc;
                
                if(!processed.contains(fc))
                    processed.add(fc);
            }
        }
        if(selGrp != null) {
            if(proxy != null && insert == 0) {
                //substitute selGrp for the proxy selComp
            	Vector sels = selGrp.getContainedFCs();
            	//make sure all the added comps have the same bounds as
            	//the proxy, so that fixEndGaps won't insert wrong gaps
            	for(int i=0; i<sels.size(); i++) {
            		FormComponent fc = (FormComponent) sels.elementAt(i);
            		if(!fc.equals(proxy))
            			fc.setBoundsToRoot(proxy.getBoundsRelativeToRoot());
            	}
                LayoutGroup lgSub = lg.getGroupContaining(proxy);
                int pos = lgSub.getIndexOf(proxy);
                lgSub.setElement(pos, new GroupElement("add", new Object[] {selGrp}));
            } else {
                int match1 = SnapTo.LEADING_MATCH;
                if(insert == -1)
                    match1 = SnapTo.TRAILING_MATCH;
                FormComponent relFC = vertical ? snapTo.vFC : snapTo.hFC;
                LayoutGroup relGrp = lg.getGroupContaining(relFC);
                relGrp = relGrp.getMaximalGroup(match1, relFC);
                int pos = relGrp.getIndexOf(relFC, true);
                if(relGrp.isParallel()) {
                    relGrp = relGrp.createSequentialGroup(pos);
                    pos = 0;
                }
                GroupElement elem = relGrp.getElement(pos);
                if(insert == SnapTo.INSERT_AFTER) {
                    elem = elem.getNextElement();
                    if(elem == null) {
                        relGrp.addGap(pos+1, SnapTo.GAP_RELATED);
                        relGrp.add(pos+2, selGrp);
                        relGrp.addGap(pos+3, SnapTo.GAP_CONTAINER);
                    } else if(!elem.isGap()) {
                        relGrp.addGap(pos+1, SnapTo.GAP_RELATED);
                        relGrp.add(pos+2, selGrp);
                    } else {
                        int sz = elem.getGapSize();
                        Rectangle b = JiglooUtils.getBoundingRectangle(selGrp.getContainedFCs());
                        sz -= ((vertical ? b.height : b.width )+6);
                        if(sz < 6)
                            sz = SnapTo.GAP_RELATED;
                        elem.setGapSize(SnapTo.GAP_RELATED);
                        elem.addGapAfter(sz);
                        elem.addAfter(selGrp);
                    }
                } else if(insert == SnapTo.INSERT_BEFORE) {
                    elem = elem.getPreviousElement();
                    if(elem == null) {
                        relGrp.addGap(pos, SnapTo.GAP_RELATED);
                        relGrp.add(pos, selGrp);
                    } else if(!elem.isGap()) {
                        relGrp.addGap(pos, SnapTo.GAP_RELATED);
                        relGrp.add(pos, selGrp);
                    } else {
                        int sz = elem.getGapSize();
                        Rectangle b = JiglooUtils.getBoundingRectangle(selGrp.getContainedFCs());
                        sz -= ((vertical ? b.height : b.width )+5);
                        if(sz < 6)
                            sz = SnapTo.GAP_RELATED;
                        elem.setGapSize(SnapTo.GAP_RELATED);
                        elem.addGapBefore(sz);
                        elem.addBefore(selGrp);
                    }
                }
            }
        }
        
        if(addOffset) {
            for(int i=0; i< fcs.size(); i++) {
                FormComponent fc = (FormComponent) fcs.elementAt(i);
                LayoutGroup fcGrp = lg.getGroupContaining(fc);
                if(lg.isSequential() &&  lg.getIndexOf(fc) != lg.getElementCount() - 1)
                    continue;
                fcGrp = fcGrp.getMaxSequentialGroup();
                if(fcGrp != null) {
                    cGap = getContainerGap(fc, fcPar, vertical ? SwingConstants.SOUTH : SwingConstants.EAST);
                    Rectangle grpBnds = fcGrp.getBounds();
                    if(vertical) {
                        relOffset = parBnds.y + parBnds.height - grpBnds.y - grpBnds.height;
                    } else {
                        relOffset = parBnds.x + parBnds.width - grpBnds.x - grpBnds.width;
                    }
                    if(snapTo != null && 
                            ((vertical && Math.abs(relOffset - cGap) <= WIGGLE_ROOM)
                            || ( !vertical && Math.abs(relOffset - cGap) <= WIGGLE_ROOM))) {
                        if(!fcGrp.endsWithGap())
                            fcGrp.addContainerGap(-1, 0, 0);
                    } else {
                        if(relOffset > 0) {
                            if(!fcGrp.endsWithGap()) {
                                if(snapTo != null && relOffset < cGap)
                                    fcGrp.addGap(-1, relOffset);
                                else
                                    fcGrp.addContainerGap(-1, relOffset, relOffset);
                            } else {
                                fcGrp.setGapSize(-1, relOffset);
                            }
                        }
                    }
                }
            }
        }
        
        lg.setConstraints(constraints);
        lg.fixGroups();
        
        return lg;
    }
    
    /**
     * @param fc
     * @param relGrp
     * @param style
     * @param vertical
     * @param parent
     * @return
     */
    private int getPreferredGap(FormComponent fc, LayoutGroup relGrp, int style, 
            boolean vertical, FormComponent par) {
        int dirn = vertical ? SwingConstants.SOUTH : SwingConstants.EAST;
        Vector comps = relGrp.getContainedFCs();
        int maxPG = -1;
        int minSep = 1000;
        Rectangle fcB = fc.getBoundsRelativeToRoot();
        int fcPos = vertical ? fcB.y : fcB.x;
        for(int i=0; i<comps.size(); i++) {
            FormComponent relFC = (FormComponent) comps.elementAt(i);
            if(canHaveRelatedGap(fc, relFC, vertical)) {
                Rectangle relB = relFC.getBoundsRelativeToRoot();
                int relPos = fcPos - (vertical ? relB.y : relB.x);
                if(relPos <= minSep) {
                	//if this relFC is closer to fc, then discard previous maxPG
                	if(relPos < minSep)
                		maxPG = -1;
                	minSep = relPos;
                	int pg = 0;
                	if(relFC.getComponent() instanceof JComponent
                			&& par.getComponent() instanceof JComponent) {
                		pg = SnapTo.getPreferredGap(fc, (JComponent)relFC.getComponent(), 
                				style, dirn, (JComponent)par.getComponent());
                	}
                	if(pg > maxPG)
                		maxPG = pg;
                }
            }
        }
        if(maxPG < 0) {
//        	System.out.println("Can't have related gap "+fc+", "+relGrp+", "+(vertical ? "vert" : "horiz"));
//        	System.out.println("maxPG < 0");
        }
        return maxPG;
    }

    /**
     * @param i
     * @param gap
     */
    private void addExactGap(int pos, int gap) {
        if(gap < 0)
            addGap(pos, gap);
        else if(gap <= 6)
        	addGap(pos, SnapTo.GAP_RELATED);
        else
        	add(pos, new GroupElement("add", new Object[]{new Integer(gap)}));
    }

    /**
     * @param fc
     * @param fcPar
     * @param north
     * @return
     */
    private int getContainerGap(FormComponent fc, FormComponent fcPar, int direction) {
        try {
            return LayoutStyle.getSharedInstance().getContainerGap(
                    fc.getJComponent(), 
                    direction, 
                    fcPar.getJComponent());
        } catch(Throwable t) {
            return 12;
        }
    }

    /**
     * @param selGrp
     * @return
     */
    private GroupElement getElement(Object obj) {
        return getElement(getIndexOf(obj));
    }

    /**
     * Collects all siblings of this group (if the parent is a parallel group)
     * and if there are some whose end boundary is less than the end
     * boundary of this group, (but not all siblings) then moves those siblings
     * (and this group) to a new parallel group in this group's parent group.
     * If all siblings end before this group, returns the parent group.
     */
    private LayoutGroup createMaximalGroup() {
//        if(true)
//            return this;
        if(parentIsSequential())
            return this;
        LayoutGroup par = getParent();
        Vector elems = new Vector();
        for(int i=0; i<par.getElementCount(); i++) {
            if(i == getIndexInParent())
                continue;
            if(par.getElement(i).isGroup() 
                    &&  par.getGroup(i).getEndPosition() <= getEndPosition())
                elems.add(par.getElement(i));
        }
        if(elems.size() == par.getElementCount() - 1)
            return par;
        if(elems.size() == 0)
            return this;
        LayoutGroup newGrp = par.createParallelGroup(getIndexInParent(), false);
        for(int i=0; i< elems.size(); i++) {
            ((GroupElement)elems.elementAt(i)).moveTo(newGrp);
        }
        newGrp.startPos = par.startPos;
        return newGrp;
    }

    /**
     * @return
     */
    private boolean endsWithGap() {
        GroupElement last = getLastElement();
        return last != null && last.isGap();
    }

    /**
     * @return
     */
    private LayoutGroup getMaxSequentialGroup() {
        LayoutGroup max = null;
        if(isSequential())
            max = this;
        if(parent == null)
            return max;
        LayoutGroup test = parent.getMaxSequentialGroup();
        if(test != null
//                 && test.getIndexOf(max) != test.getElementCount()-1
                )
            return test;
        return max;
    }

    /**
     * @param fc
     * @return
     */
    private boolean isLastElement(Object fc) {
        return getIndexOf(fc) == getElementCount() - 1;
    }

    private boolean isLastInParent() {
        return getParent() == null || 
        parentIsParallel() ||
        (getParent().isSequential() && getIndexInParent() == getParent().getElementCount()-1);
    }

    private int startPos = -1, endPos = -1;
    
    static final int FLAG_ANCHOR_TRAILING = -1;
    static final int FLAG_ANCHOR_LEADING = 1;
    static final int FLAG_EXPAND = 1;
    static final int FLAG_NO_EXPAND = 0;
    
    /**
     * @return
     */
    private int getEndPosition() {
        if(endPos != -1)
            return endPos;
        Rectangle b = getBounds();
        if(b == null)
            return END_UNDEFINED;
        if(vertical)
            return b.y+b.height;
        return b.x+b.width;
    }
    
    private int getStartPosition() {
        if(startPos != -1)
            return startPos;
        Rectangle b = getBounds();
        if(b == null)
            return 100000;
        if(vertical)
            return b.y;
        return b.x;
    }

    public LayoutGroup getPreceedingGroup(int pos, boolean checkStart) {
        LayoutGroup best = null;
        int bestPos = -1;
        int testPos = checkStart ? getStartPosition() : getEndPosition();
        if(testPos <= pos) {
            if(best == null || testPos > bestPos) {
                best = this;
                bestPos = testPos;
            }
        }
        for(int i=0;i<getElementCount(); i++) {
            LayoutGroup g = getGroup(i);
            if(g != null) {
                LayoutGroup test = g.getPreceedingGroup(pos, checkStart);
                if(test != null) {
                    testPos = checkStart ? test.getStartPosition() : test.getEndPosition();
                    if(best == null || testPos > bestPos) {
                        best = test;
                        bestPos = testPos;
                    }
                } else {
                }
            }
        }
        return best;
    }
    
    private boolean baselineMatch(FormComponent fc1, FormComponent fc2) {
        if(!SnapTo.allowBaselineMatch(fc1))
            return false;
        if(!SnapTo.allowBaselineMatch(fc2))
            return false;
        Rectangle b1 = fc1.getBoundsRelativeToRoot();
        Rectangle b2 = fc2.getBoundsRelativeToRoot();
        boolean val = Math.abs((b1.y+SnapTo.getBaseline(fc1)) -  (b2.y+SnapTo.getBaseline(fc2))) <= WIGGLE_ROOM;
        return val;
    }
    
    private boolean leadingMatch(FormComponent fc1, FormComponent fc2) {
        Rectangle b1 = fc1.getBoundsRelativeToRoot();
        Rectangle b2 = fc2.getBoundsRelativeToRoot();
        if(vertical)
            return Math.abs(b1.y - b2.y) <= WIGGLE_ROOM;
        return Math.abs(b1.x - b2.x) <= WIGGLE_ROOM;
    }
    
    private boolean trailingMatch(FormComponent fc1, FormComponent fc2) {
        Rectangle b1 = fc1.getBoundsRelativeToRoot();
        Rectangle b2 = fc2.getBoundsRelativeToRoot();
        if(vertical)
            return Math.abs((b1.y+b1.height) - (b2.y+b2.height)) <= WIGGLE_ROOM;
        return Math.abs((b1.x+b1.width) - (b2.x+b2.width)) <= WIGGLE_ROOM;
    }

    private void processParallelMatches(Vector fcs, Vector processed, LayoutGroup newLG, FormComponent fc, int align) {
        if(processed.contains(fc))
            return;
        for(int k=0; k<fcs.size(); k++) {
            FormComponent fc2 = (FormComponent)fcs.elementAt(k);
            if(fc2.equals(fc) || processed.contains(fc2))
                continue;
            if((align == GroupLayout.BASELINE && baselineMatch(fc, fc2))
                    || (align == GroupLayout.LEADING && leadingMatch(fc, fc2))
                    || (align == GroupLayout.TRAILING && trailingMatch(fc, fc2))
            ) {
                if(!processed.contains(fc)) {
                    if(align == GroupLayout.LEADING) {
                        LayoutGroup seq = newSequentialGroup();
                        seq.add(fc, -1, 0, align);
                        newLG.add(-1, seq);
                    } else {
                        newLG.add(fc, -1, 0, align);
                    }
                    processed.add(fc);
                }
                if(align == GroupLayout.LEADING) {
                    LayoutGroup seq = newSequentialGroup();
                    seq.add(fc2, -1, 0, align);
                    newLG.add(-1, seq);
                } else {
                    newLG.add(fc2, -1, 0, align);
                }
                processed.add(fc2);
            }
        }
    }
    
    /**
     * Sets all the gaps before (or after) the given element to *not* expandable
     */
    void fixGaps(GroupElement elem0, boolean before) {
        GroupElement elem;
        if(before)
            elem = elem0.getPreviousElement();
        else
            elem = elem0.getNextElement();
        while(elem != null) {
            if(elem.isGap())
                elem.setExpandable(false);
            else if(elem.isGroup()) {
                LayoutGroup grp = elem.getGroup();
                if(grp.isParallel()) {
                    for(int i=0; i<grp.getElementCount(); i++)
                        fixGaps(grp.getElement(i), before);
                } else {
                    for(int i=0; i<grp.getElementCount(); i++) {
                        GroupElement elem2 = grp.getElement(i);
                        fixGaps(elem2, before);
                    }
                }
            }
            if(before)
                elem = elem.getPreviousElement();
            else
                elem = elem.getNextElement();
        }
    }
    
    public void setExpand(FormComponent component, boolean expand) {
        if(component == null)
            return;
        int pos = getIndexOf(component, false);
        if(pos < 0) {
            LayoutGroup lg = getGroupContaining(component);
            if(lg != null)
                lg.setExpand(component, expand);
            return;
        }
        GroupElement elem0 = getElement(getIndexOf(component));
        elem0.setExpandable(expand);
    }
      
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements including and after fc, removing those
     * elements from this group. If parallel, returns a sequential group
     * containing only fc, removing fc from this group, unless the
     * alignment is LEADING, when it returns parent.getGroupFrom(pos)
     * where pos is the position of this group
     */
    public LayoutGroup getGroupFrom(FormComponent fc) {
        return getGroupFrom(fc, true);
    }

    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements including and after fc, removing those
     * elements from this group. If parallel, returns a sequential group
     * containing only fc, removing fc from this group, unless the
     * alignment is LEADING, when it returns parent.getGroupFrom(pos)
     * where pos is the position of this group
     */
    public LayoutGroup getGroupFrom(FormComponent fc, boolean elemOnly) {
        int pos = getIndexOf(fc, true);
        if(pos < 0)
            return null;
        return getGroupFrom(pos, elemOnly);
    }
    
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements including and after pos, removing those
     * elements from this group without inserting gaps. 
     * If parallel, returns a sequential group
     * containing only fc, removing fc from this group, unless the
     * alignment is LEADING, when it returns parent.getGroupFrom(pos)
     * where pos is the position of this group
     */
    private LayoutGroup getGroupFrom(int pos) {
        return getGroupFrom(pos, false);
    }
    
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements including and after pos, removing those
     * elements from this group without inserting gaps. 
     * If parallel, returns a sequential group
     * containing only fc, removing fc from this group, unless the
     * alignment is LEADING, when it returns parent.getGroupFrom(pos)
     * where pos is the position of this group
     */
    private LayoutGroup getGroupFrom(int pos, boolean elemOnly) {
        if(pos < 0)
            return null;
        LayoutGroup lg = new LayoutGroup("createSequentialGroup", null, lw, vertical);
        if(isParallel()) {
            lg.add(0, getElement(pos));
            addedElements.remove(pos);
            return lg;
        }
        for(int i=pos; i< getElementCount(); i++) {
            lg.add(i-pos, getElement(i));
        }
        for(int i=pos; i< getElementCount(); i++) {
            addedElements.remove(i);
            i--;
        }
        return lg;
    }
            
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements after fc, removing those
     * elements from this group.
     */
    public LayoutGroup getGroupAfter(FormComponent fc) {
        if(isParallel())
            return null;
        int pos = getIndexOf(fc);
        if(pos < 0)
            return null;
        return getGroupAfter(pos);
    }
    
    public LayoutGroup newSequentialGroup() {
        return new LayoutGroup("createSequentialGroup", null, lw, vertical);
    }
    
    public LayoutGroup newParallelGroup() {
        return new LayoutGroup("createParallelGroup", null, lw, vertical);
    }
    
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements after pos, removing those
     * elements from this group.
     */
    public LayoutGroup getGroupAfter(int pos) {
        if(isParallel())
            return null;
        //move to next element
        pos++;
        
        LayoutGroup lg = newSequentialGroup();
        for(int i=pos; i< getElementCount(); i++) {
            lg.add(i - pos, getElement(i));
        }
        for(int i=pos; i< getElementCount(); i++) {
            addedElements.remove(i);
            i--;
        }
        return lg;
    }
    
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements up to and including grpOrFC, removing those
     * elements from this group. If it's a parallel group, returns a new
     * sequential group containing just this element
     */
    public LayoutGroup getGroupUpTo(Object grpOrFC, boolean including) {
        int pos = getIndexOf(grpOrFC);
        if(pos < 0)
            return null;
        return getGroupUpTo(pos, including);
    }
    
    /**
     * If this is a sequential group, returns a new sequential group
     * containing all elements up to and including pos, removing those
     * elements from this group. If it's a parallel group, returns a new
     * sequential group containing just this element
     */
    public LayoutGroup getGroupUpTo(int pos, boolean including) {
        LayoutGroup lg = newSequentialGroup();
        int pos1 = 0;
        if(isParallel()) {
            if(!including)
                return lg;
            pos1 = pos;
        } else {
            if(!including)
                pos--;
        }
        for(int i=pos1; i<= pos; i++) {
            lg.add(i, getElement(i).getCopy());
        }
        for(int i=pos; i>= pos1; i--) {
            remove(i, true);
        }
        return lg;
    }
        
    /**
     * @return
     */
    public LayoutGroup getParent() {
        return parent;
    }

    /**
     * @param selComp
     * @return true if only gaps exist between the selComp
     * and one of the group's ends
     */
    public boolean isAtOneEnd(FormComponent selComp) {
        if(isParallel())
            return true;
        int pos = getIndexOf(selComp);
        if(pos < 0)
            return false;
        if(pos == 0 || pos == getElementCount() - 1)
            return true;
        for(int i=0; i< pos - 1; i++)
            if(getGroupOrFC(i) != null)
                return false;
        for(int i=pos+1; i< getElementCount(); i++)
            if(getGroupOrFC(i) != null)
                return false;
        return true;
    }

    public boolean isAtStart(Object grpOrFC) {
        if(isParallel())
            return true;
        int pos = getIndexOf(grpOrFC);
        if(pos < 0)
            return false;
        if(pos == 0)
            return true;
        for(int i=0; i< pos - 1; i++)
            if(getGroupOrFC(i) != null)
                return false;
        return true;
    }

    public boolean isAtEnd(Object grpOrFC) {
        if(isParallel())
            return true;
        int pos = getIndexOf(grpOrFC);
        if(pos < 0)
            return false;
        if(pos == getElementCount() - 1)
            return true;
        for(int i=pos+1; i< getElementCount(); i++)
            if(getGroupOrFC(i) != null)
                return false;
        return true;
    }

    /**
     * 
     */
    public void updateSizes() {
        for(int i=0; i < getElementCount(); i++) {
            GroupElement elem = getElement(i);
            int sz = getSpringSize(i);
            if(sz < 0) {
//                System.err.println("Update size, size < 0: "+elem+", sz="+sz);
                sz = 0;
            }
            elem.setSize(sz);
//            System.out.println("update size "+elem+", sz="+sz);
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.updateSizes();
//            } else if(o instanceof FormComponent) {
//                //remove any "setPreferredSize" lines from code
//                ((FormComponent)o).resetPropertyValue("preferredSize", false);
            }
        }
    }

    public void removePreferredSizesFromCode() {
        for(int i=0; i < getElementCount(); i++) {
            GroupElement elem = getElement(i);
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.removePreferredSizesFromCode();
            } else if(o instanceof FormComponent) {
                //remove any "setPreferredSize" lines from code
                ((FormComponent)o).resetPropertyValue("preferredSize", false);
            }
        }
    }

    public void clearAnchors() {
        for(int i=0; i < getElementCount(); i++) {
            GroupElement elem = getElement(i);
            elem.setAnchorFlag(0);
            elem.setExpandFlag(-1);
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.clearAnchors();
            }
        }
    }

    public void fixGroups() {
        if(hasBaselineElement()) {
            setAlignment(GroupLayout.BASELINE);
        }
        int ec = getElementCount();
        for(int i=0; i < ec; i++) {
            GroupElement elem = getElement(i);
            elem.setLayoutGroup(this);
            Object o = getGroupOrFC(i);
            if(o instanceof LayoutGroup) {
                LayoutGroup g = (LayoutGroup) o;
                g.fixGroups();
            }
        }
    }

    public void fixEndGaps() {
    	int ec = getElementCount();
    	for(int i=0; i < ec; i++) {
    		GroupElement elem = getElement(i);
    		//want to add in expandable gaps even at trailing edge of leading-aligned components since
    		//if the anchor direction is changed we want there to exist an expandable gap
    		//which can be set to a fixed size (eg, if a button is at the leading edge of a group
    		//and it's anchor is changed to TRAILING then we want it to maintain it's distance from
    		//the trailing edge of the group - so that's why the following lines are commented-out!!!
//    		if(elem.getAnchorFlag() != FLAG_ANCHOR_TRAILING)
    		//    			continue;

    		Object o = getGroupOrFC(i);
    		if(o instanceof LayoutGroup) {
    			LayoutGroup g = (LayoutGroup) o;
    			g.fixEndGaps();
    			if(g.isSequential() && isParallel() && !g.endsWithGap()) {
    				int gap = getEndPosition() - g.getEndPosition();
    				if(gap > WIGGLE_ROOM && gap < END_UNDEFINED/2)
    					g.addGap(-1, gap);
    			}
    		} else if(
    				o instanceof FormComponent 
    				&& isParallel() 
    				&& elem.getAlignment() != GroupLayout.BASELINE) {
    			Rectangle b =  getBounds((FormComponent)o);
    			int gap = getEndPosition() - (vertical ? b.y + b.height : b.x + b.width);
    			if(gap > WIGGLE_ROOM && gap < END_UNDEFINED/2) {
    				LayoutGroup lg = createSequentialGroup(i);
    				lg.addGap(-1, gap);
    			}
    		}
    	}
    }

    /**
     * @return
     */
    private boolean containsTrailingElement() {
        for(int i=0; i<getElementCount(); i++) {
            if(getElement(i).getAnchorFlag() == FLAG_ANCHOR_TRAILING)
                return true;
        }
        return false;
    }

    /**
     * @param i
     * @param gap
     */
    private void addExpandableGap(int pos, int gap) {
        addGap(pos, 0, gap, EXPANDABLE_SIZE);
    }

    private int getIndent() {
        LayoutGroup lg = this;
        int indent = 0;
        while(lg.getParent() != null) {
            indent++;
            lg = lg.getParent();
        }
        return indent;
    }
    
    public String toString() {
        int indent = getIndent();
        String indentStr = "";
        for(int i=0; i<indent; i++)
            indentStr+="    ";
        String str = "LG"+(vertical ? "V" : "H")+(isParallel() ? " Par" : " Seq")+" ["+getAlignAsStr(alignment)+"] {\n"+indentStr+"    ";
        try {
            for (int i = 0; i < addedElements.size(); i++) {
                str+=getElement(i).toString();
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return str+"\n"+indentStr+"} ";
    }

    /**
	 * @param alignment2
	 * @return
	 */
	private String getAlignAsStr(int alignment) {
		if(alignment == GroupLayout.BASELINE)
			return "BL";
		if(alignment == GroupLayout.CENTER)
			return "CN";
		if(alignment == GroupLayout.LEADING)
			return "LD";
		if(alignment == GroupLayout.TRAILING)
			return "TR";
		return "?";
	}

	public GroupElement getNearestGap(int pos, boolean before) {
        if(before) {
            if(pos == 0) {
                if(getParent() != null && getParent().isSequential()) {
                    GroupElement elem = getParent().getLastElement();
                    if(elem != null && elem.isGap())
                        return elem;
                }
            } else if(isGap(pos-1)) {
                return getElement(pos-1);
            }
        } else {
            if(pos == getElementCount()-1) {
                if(getParent() != null && getParent().isSequential()) {
                    GroupElement elem = getParent().getFirstElement();
                    if(elem != null && elem.isGap())
                        return elem;
                }
            } else if(isGap(pos+1)) {
                return getElement(pos+1);
            }
        }
        return null;
    }
    
    /**
     * Returns the distance from the trailing edge of this group to the leading edge of this FC
     */
    public int getDistanceTo(int rel) {
        int[] lim = getLimits();
        return rel - lim[0];
    }
    
    public void changeNearestGap(int pos, int change, boolean before) {
        GroupElement gap = getNearestGap(pos, before);
        if(gap != null) {
            int gs = gap.getSize();
            if(change < 0 && gs < -change) {
                gap.changeGapSize(-gs);
                change += gs;
                LayoutGroup gg = getGroupContaining(gap);
                gg.changeNearestGap(gg.getIndexOf(gap), change, before);
            } else {
                gap.changeGapSize(change);
            }
        }
    }
    
    /**
     * @param relFC
     * @return true if the given FC's leading edge matches the trailing edge of this group
     */
    public boolean matchesStartBoundary(FormComponent relFC) {
        if(relFC == null)
            return false;
        Rectangle relBnds = getBounds(relFC);
        if(getParent() != null) {
            int[] b = parent.getBounds(getIndexInParent());
            if(vertical)
                return relBnds.y == b[0];
            return relBnds.x == b[0];
        }
        Rectangle bnds = getBounds();
        if(vertical)
            return relBnds.y == bnds.y;
        return relBnds.x == bnds.x;
//        int[] bnds = getBoundsInContainer();
//        if(vertical)
//            return relBnds.y == bnds[0];
//        return relBnds.x == bnds[0];
    }

    /**
     * @param relFC
     * @return true if the given FC's trailing edge matches the trailing edge of this group
     */
    public boolean matchesEndBoundary(Object relFC, boolean loose) {
        if(relFC == null)
            return false;
        Rectangle relBnds = getBounds(relFC);
        if(getParent() != null) {
            int[] b = parent.getBounds(getIndexInParent());
            if(loose) {
                if(vertical)
                    return relBnds.y + relBnds.height <= b[1];
                return relBnds.x + relBnds.width <= b[1];
            } else {
                if(vertical)
                    return relBnds.y + relBnds.height == b[1];
                return relBnds.x + relBnds.width == b[1];
            }
        }
        Rectangle bnds = getBounds();
        if(loose) {
            if(vertical)
                return relBnds.y + relBnds.height <= bnds.y + bnds.height;
            return relBnds.x + relBnds.width <= bnds.x + bnds.width;
        } else {
            if(vertical)
                return relBnds.y + relBnds.height == bnds.y + bnds.height;
            return relBnds.x + relBnds.width == bnds.x + bnds.width;
        }
    }

    /**
     * Cuts this LG out of it's parent and moves it to lg
     */
    public void moveTo(int pos, LayoutGroup lg) {
        int pos1 = getParent().getIndexInParent();
        getParent().remove(this);
        lg.add(pos, this);
    }
    
    /**
     * @param selComp
     * @param relFC
     * @return
     */
    public LayoutGroup findCommonAncestor(FormComponent selComp, FormComponent relFC) {
        LayoutGroup lgSel = getGroupContaining(selComp);
        while(lgSel != null) {
            if(lgSel.contains(relFC))
                return lgSel;
            lgSel = lgSel.getParent();
        }
        return null;
    }

    public boolean contains(FormComponent fc) {
        for(int i=0; i<getElementCount(); i++) {
            Object obj = getGroupOrFC(i);
            if(fc.equals(obj))
                return true;
            if(obj instanceof LayoutGroup) {
                if(((LayoutGroup)obj).contains(fc))
                    return true;
            }
        }
        return false;
    }

    /**
     * returns a sequential group containing all elements from fc back to the element in
     * the parent group par
     * @param anc
     * @param selComp
     * @return
     */
    public LayoutGroup getGroupBetween(LayoutGroup cont, FormComponent fc, boolean changeGap) {
        if(this.equals(cont)) {
            LayoutGroup lg = newSequentialGroup();
            lg.add(0, remove(fc, false));
            return lg;
        }
        LayoutGroup lg = cont.getGroupContaining(fc);
        LayoutGroup par = lg.getParent();
        int pos = lg.getIndexInParent()-1;
        lg = lg.getGroupUpTo(fc, true);
        while(!par.equals(cont)) {
            if(par.isSequential()) {
                for(int i=pos; i>= 0; i--)
                    lg.add(0, par.getElement(i).getCopy());
                for(int i=pos; i>= 0; i--)
                    par.remove(i, changeGap);
            } else {
//                lg.add(0, par.remove(pos, false));
            }
            pos = par.getIndexInParent()-1;
            par = par.getParent();
        }
//        lg.add(0, par.remove(pos, false));
        return lg;
    }

    /**
     * returns the index in this group of the element that eventually
     * leads to fc
     * @param selComp
     * @return
     */
    public int getPositionOf(FormComponent fc) {
        LayoutGroup lg = getGroupContaining(fc);
        if(this.equals(lg))
            return getIndexOf(fc);
        LayoutGroup par = lg.getParent();
        int pos = lg.getIndexInParent();
        while(!par.equals(this)) {
            pos = par.getIndexInParent();
            par = par.getParent();
        }
        return pos;
    }

    /**
     * @return
     */
    public int getSize() {
        return getParent().getSize(getIndexInParent());
    }

    /**
     * Will be leading-anchored if there are no expandable gaps or components in the chain preceding
     * the given FC.
     * <P>
     * Will be trailing-anchored if there are one or more expandable gaps or components in the chain
     * preceding the given FC and non in the chain following the given FC.
     * <P>
     * If this is a parallel group and fc has a LEADING alignment and there is an expandable element
     * in the group then this is equivalent to there being an expandable element after fc.
     * <P>
     * If this is a parallel group and fc has a TRAILING alignment and there is an expandable element
     * in the group then this is equivalent to there being an expandable element before fc.
     * <P>
     * If this is a parallel group and fc has a BASELINE alignment and there is an expandable element
     * in the group then this is equivalent to there being an expandable element before and after fc.
     */
    public boolean isAnchored(FormComponent fc, boolean leading) {
        LayoutGroup lg = getGroupContaining(fc);
        if(lg == null)
            return leading;
        GroupElement elem = lg.getElement(lg.getIndexOf(fc));
        
        //If the element is expandable then consider it anchored in both directions
        if(elem.isExpandable())
            return true;
//        return false;
        
        int anchored = elem.getAnchorFlag();
        if(anchored == 0) {
            
            if(isAnchoredInternal(leading, elem))
                anchored = leading ? FLAG_ANCHOR_LEADING : FLAG_ANCHOR_TRAILING;
            else
                anchored = leading ? FLAG_ANCHOR_TRAILING : FLAG_ANCHOR_LEADING;
            
            elem.setAnchorFlag(anchored);
        }
        return (anchored == FLAG_ANCHOR_LEADING && leading) || (anchored == FLAG_ANCHOR_TRAILING && !leading);
    }
        
    /**
     * If the element is expandable then consider it anchored in both directions
     */
    private boolean isAnchoredInternal(boolean leading, GroupElement elem) {
        try {
            
            if(elem.isExpandable())
                return true;
            
            if(leading) {
                if(elem.isExpandable())
                    return false;
                elem = elem.getPreviousElement();
                while(elem != null) {
                    if(elem.isExpandable()) {
                        return false;
                    }
                    elem = elem.getPreviousElement();
                }
                return true;
            } else {
                if(elem.isExpandable())
                    return true;
                GroupElement elem0 = elem;
                elem = elem.getPreviousElement();
                boolean expandableBefore = false;
                while(elem != null) {
                    if(elem.isExpandable()) {
                        expandableBefore = true;
                        break;
                    }
                    elem = elem.getPreviousElement();
                }
                if(!expandableBefore)
                    return false;
                elem = elem0;
                elem = elem.getNextElement();
                while(elem != null) {
                    if(elem.isExpandable()) {
                        return false;
                    }
                    elem = elem.getNextElement();
                }
                return true;
            }
            
        } catch(Throwable t) {
            t.printStackTrace();
            return false;
        }
    }
    
    /**
     * returns true if *any* child element of this group is anchored in the
     * given direction - note a group can be anchored in both directions
     */
    boolean isAnchored(boolean leading) {
        for(int i=0; i<getElementCount(); i++) {
            FormComponent fc = getFormComponent(i);
            if(fc != null && isAnchored(fc, leading))
                return true;
            LayoutGroup lg = getGroup(i);
            if(lg != null && lg.isAnchored(leading))
                return true;
        }
        return false;
    }
    
    public boolean isFlaggedExpandable(FormComponent fc) {
        LayoutGroup lg = getGroupContaining(fc);
        GroupElement elem = lg.getElement(fc);
        return elem.getExpandFlag() == FLAG_EXPAND;
    }
    
    public boolean isFlaggedAnchored(FormComponent fc, boolean leading) {
        LayoutGroup lg = getGroupContaining(fc);
        GroupElement elem = lg.getElement(fc);
        if(leading)
            return elem.getAnchorFlag() == FLAG_ANCHOR_LEADING;
        return elem.getAnchorFlag() == FLAG_ANCHOR_TRAILING;
    }
    
    public boolean isExpandable(FormComponent fc) {
        LayoutGroup lg = getGroupContaining(fc);
        if(lg == null)
            return false;
        GroupElement elem = lg.getElement(lg.getIndexOf(fc));
        Object[] args = elem.getArgs();
        if(args == null || args.length < 4)
            return false;
        return elem.getIntValue(args.length-1) >= EXPANDABLE_SIZE;
    }
    
    /**
     * if rel is past the trailing edge of the component at position pos then
     * returns that positive number (distance past trailing edge) but if it
     * is just past the leading edge it returns a negative number equal to minus
     * the distance past the leading edge
     * @param i
     * @param selComp
     * @return
     */
    public int getDistanceBetween(int pos, int rel) {
        Object gfc = getGroupOrFC(pos);
        if(gfc instanceof LayoutGroup) {
            LayoutGroup lg = (LayoutGroup)gfc;
            int[] lim = lg.getLimits();
            int val = rel - lim[1];
            if(val < 0)
                val = -(rel - lim[0]);
            return val;
//            return lg.getDistanceTo(rel);
        }
        if(gfc instanceof FormComponent) {
//            Rectangle b1 = ((FormComponent)gfc).getBoundsRelativeToViewport();
            Rectangle b1 = ((FormComponent)gfc).getBounds();
            int val = vertical ? rel - b1.y - b1.height : rel - b1.x - b1.width;
            if(val < 0)
                val = vertical ? b1.y - rel : b1.x - rel;
            return val;
        }
        return 0;
    }

    public LayoutGroup getRoot() {
        if(parent == null)
            return this;
        return parent.getRoot();
    }
    
    /**
     * @param fc
     * @return
     */
    public LayoutGroup findGroupContaining(FormComponent fc) {
        LayoutGroup grp = getGroupContaining(fc);
        if(grp != null)
            return grp;
        return getRoot().getGroupContaining(fc);
    }

    /**
     * @param layoutConstraint
     */
    public void handleLayoutConstraint(FormComponent fc, String layoutConstraint, JavaCodeParser jcp) {
        FormEditor ed = fc.getEditor();
    	FormComponent sel = ed.getSelectedComponent();
        //don't want to just toggle - but apply the same expansion to all FCs as to the
        //selected component. This requires that the selected component be toggled last!
        if(layoutConstraint.equals(EXPAND_HOR) || layoutConstraint.equals(EXPAND_VER)) {
        	setExpand(fc, !isExpandable(fc));
//            setExpand(fc, !isFlaggedExpandable(sel));
        } else if(layoutConstraint.equals(ANCHOR_BOTTOM) || layoutConstraint.equals(ANCHOR_RIGHT)) {
            setAnchored(fc, !isAnchored(sel, false), false, false);
//            setAnchored(fc, !isFlaggedAnchored(sel, false), false, false);
        } else if(layoutConstraint.equals(ANCHOR_TOP) || layoutConstraint.equals(ANCHOR_LEFT)) {
            setAnchored(fc, !isAnchored(sel, true), true, false);
//            setAnchored(fc, !isFlaggedAnchored(sel, true), true, false);
        }
        
        if(ed.isUpdatingLastFC()) {
        	//if you don't do this just once then isFlaggedAnchored returns
        	//the wrong result since it is changed by the following
        	lw.getHGroup().clearAnchors();
        	lw.getVGroup().clearAnchors();
        	lw.updateGroupLayout(jcp);
        }
    }

    /**
     * To anchor to the trailing edge, first ensure that all trailing elements (and gaps) are not
     * expandable. Then go up the chain and find the first element which is anchored to the
     * leading edge and make the gap after it expandable (or insert expandable gap)
     * <P>
     * To anchor to the leading edge, do the opposite.
     * @param fc
     * @param anchor
     * @param leading
     */
    public void setAnchored(FormComponent fc, boolean anchor, boolean leading, boolean force) {
        if(!anchor) {
            //not anchoring in one direction is equivalent to anchoring in the opposite direction
            setAnchored(fc, true, !leading, force);
            return;
        }
        if(!force && isAnchored(fc, leading))
            return;
//        System.out.println("ANCHOR "+fc+", "+(leading ? "LEADING" : "TRAILING") +", "+ (vertical ? "V" : "H"));
        GroupElement targetElement = getElement(getIndexOf(fc));
        targetElement.setAnchorFlag(leading ? FLAG_ANCHOR_LEADING : FLAG_ANCHOR_TRAILING);

        GroupElement elem;
        if(getElement() != null)
            getElement().setAlignment(GroupLayout.LEADING);
        if(leading) {
//            elem0.setAlignment(GroupLayout.LEADING);
            elem = targetElement.getPreviousElement();
            while(elem != null) {
                if(elem.isExpandable())
                    elem.setExpandable(false);
                elem = elem.getPreviousElement();
            }
            if(targetElement.isExpandable())
                return;
            //make elements (especially gaps) on trailing edge expandable/contractable so that the
            //targetElement has room to be it's preferred size
            elem = targetElement.getNextElement();
            while(elem != null) {
                if(elem.isExpandable())
                    return;
                if(elem.isAnchored(leading))
                    return;
                GroupElement elem2 = elem.getNextElement();
                if(elem.isFC() || elem.isGroup() || elem2 == null) {
                    LayoutGroup elg = targetElement.getLayoutGroup();
                    int pos = targetElement.getIndexInParent();
                    if(elg.isParallel() && elg.getAlignmentFromElements() != GroupLayout.BASELINE) {
                        elg = elg.createSequentialGroup(pos);
                        pos = 1;
                    }
                    if(pos < elg.getElementCount()-1 && elg.getElement(pos+1).isGap()) {
                        elg.getElement(pos+1).setExpandable(true);
                    } else {
                        elg.add(pos+1, 
                                new GroupElement("add", new Object[] {new Integer(0), new Integer(0), MAX_SIZE}));
                    }
                    return;
                }
                elem = elem2;
            }
        } else {
//            elem0.setAlignment(GroupLayout.TRAILING);
            elem = targetElement.getNextElement();
            LayoutGroup lg = getGroupContaining(fc);
            lg.addTrailingGap(fc);
            while(elem != null) {
                if(elem.isExpandable())
                    elem.setExpandable(false);
                elem = elem.getNextElement();
            }
            if(targetElement.isExpandable())
                return;
            elem = targetElement.getPreviousElement();
            while(elem != null) {
                if(elem.isExpandable())
                    return;
                if(elem.isAnchored(leading))
                    return;
                GroupElement elem2 = elem.getPreviousElement();
                if(elem.isFC() || elem.isGroup() || elem2 == null) {
                    LayoutGroup elg = targetElement.getLayoutGroup();
                    int pos = targetElement.getIndexInParent();
                    if(elg.isParallel()) {
                        if(elg.getAlignmentFromElements() == GroupLayout.BASELINE) {
                           if(elg.getParent() != null) {
                               int pp = elg.getIndexInParent();
                               if(elg.parentIsParallel()) {
                                   elg = elg.getParent().createSequentialGroup(pp);
                                   pos = 0;
                               } else {
                                   elg = elg.getParent();
                                   pos = pp;
                               }
                           }
                        } else {
                            elg = elg.createSequentialGroup(pos);
                            pos = 0;
                        }
                    }
                    if(pos > 0 && elg.getElement(pos-1).isGap()) {
                        elg.getElement(pos-1).setExpandable(true);
                    } else {
                        elg.add(pos, 
                                new GroupElement("add", new Object[] {new Integer(0), new Integer(0), MAX_SIZE}));
                    }
                    return;
                }
                elem = elem2;
            }
        }
    }
    
    /**
     * Adds a fixed trailing gap after this element if there isn't one there already
     * @param elem0
     */
    void addTrailingGap(FormComponent fc) {
        int pos = getIndexOf(fc);
        if(getAlignment(pos) == GroupLayout.BASELINE)
            return;
        if(isParallel()) {
            //add in a fixed trailing gap (inside a new sequential group)
            Rectangle b =  getBounds(fc);
            int gap = getEndPosition() - (vertical ? b.y + b.height : b.x + b.width);
            if(gap > 1 && gap < END_UNDEFINED/2) { 
            	//gap > 1 and not gap > 0 because the container gap can
                //differ by 1 pixel between say a jbutton and a jtabbedpane, so need
                //to allow for a bit of leeway
                LayoutGroup lg = createSequentialGroup(pos);
                lg.addGap(-1, gap);
            } else {
                if(parent != null)
                    parent.addTrailingGap(fc);
            }
        } else {
            if(pos == getElementCount()-1 && parentIsParallel()) {
                Rectangle b =  getBounds(fc);
                int gap = parent.getEndPosition() - (vertical ? b.y + b.height : b.x + b.width);
                if(gap > 1 && gap < END_UNDEFINED/2) {
                    addGap(pos+1, gap);
                }
            }
        }
    }

    public boolean isExpandable() {
        for(int i=0; i< getElementCount(); i++)
            if(getElement(i).isExpandable())
                return true;
        return false;
    }

    public static void updateSelectedCompBoundsFromFrames(FormEditor ed) {
    	Vector sels = ed.getSelectedComponents();
        for(int i=0; i<sels.size(); i++) {
            FormComponent selFC = ed.getSelectedComponent(i);
            FormComponent par = selFC.getParent();
            Rectangle fcb = ed.getBoundsFromFrame(selFC, par, false);
            Rectangle pb = par.getBoundsRelativeToRoot();
            fcb.x += pb.x;
            fcb.y += pb.y;
            selFC.setBoundsToRoot(fcb);
            if(selFC.getComponent() != null)
            	selFC.getComponent().setSize(fcb.width, fcb.height);

            resizeLinkedElements(sels, selFC, par, fcb, true);
            resizeLinkedElements(sels, selFC, par, fcb, false);
            
        }
    }

	private static void resizeLinkedElements(Vector sels, FormComponent selFC,
			FormComponent par, Rectangle fcb, boolean vertical) {
		Vector vlinked = par.getLayoutWrapper().getLinkedElements(vertical);
		if(vlinked == null)
			return;
		for(int j=0;j<vlinked.size();j++) {
			Vector v = (Vector)vlinked.get(j);
			if(!v.contains(selFC))
				continue;
			for(int k=0; k<v.size(); k++) {
				FormComponent linked = (FormComponent)v.get(k);
				if(linked.equals(selFC) || sels.contains(linked))
					continue;
				Rectangle lb = linked.getBoundsRelativeToRoot();
				if(vertical)
					lb.height = fcb.height;
				else
					lb.width = fcb.width;
				linked.setBoundsToRoot(lb);
		        if(linked.getComponent() != null)
		        	linked.getComponent().setSize(lb.width, lb.height);
			}
		}
	}

    /**
     * returns the biggest group whose boundary (determined by match1) matches relFC
     */
    public LayoutGroup getMaximalGroup(int match1, FormComponent relFC) {
        LayoutGroup relGrp = this;
        while(relGrp.getParent() != null &&
                 ((match1 == SnapTo.LEADING_MATCH && relGrp.getParent().matchesEndBoundary(relFC, false))
                        || (match1 == SnapTo.TRAILING_MATCH && relGrp.getParent().matchesStartBoundary(relFC)))) {
            relGrp = relGrp.getParent();
        }
        return relGrp;
    }

    /**
     * @return
     */
    public GroupElement getElement() {
        if(parent == null)
            return null;
        return getParent().getElement(getIndexInParent());
    }

    /**
     * @return
     */
    public int getAlignment() {
        if(isParallel())
        return alignment;
        if(getElement() != null)
            return getElement().getAlignment();
        return GroupLayout.LEADING;
    }

    /**
     * @param component
     */
    public void resetPreferredSize(FormComponent fc) {
        LayoutGroup lg = getGroupContaining(fc);
        if(lg == null)
            return;
        int pos = lg.getIndexOf(fc);
        GroupElement elem = lg.getElement(pos);
        elem.resetPreferredSize();
    }

    /**
     * @param fc
     */
    public void protectSize(FormComponent fc) {
        LayoutGroup lg = getGroupContaining(fc);
        if(lg == null)
            return;
        GroupElement elem = lg.getElement(fc);
        elem.protectSize();
    }

    /**
     * @param fc
     * @return
     */
    public GroupElement getElement(FormComponent fc) {
        return getElement(getIndexOf(fc));
    }

    /**
     * @param selComps
     */
    public void repairSizes(Vector selComps) {
        if(selComps == null)
            return;
        for(int i=0;i<selComps.size(); i++) {
            FormComponent fc = (FormComponent) selComps.elementAt(i);
            LayoutGroup lg = getGroupContaining(fc);
            GroupElement elem = lg.getElement(fc);
            elem.repairSize();
        }
        lw.updateGroupLayout(getFC().getEditor().getJavaCodeParser());
    }

    public static boolean canHaveRelatedGap(Rectangle b1, int x, int x2, int y, int y2, boolean vertical) {
        //note, vertical does *not* mean compare y axis!!!
        if(vertical) {
            return SnapTo.overlap(b1.x, b1.x+b1.width, x, x2);
        } else {
            return SnapTo.overlap(b1.y, b1.y+b1.height, y, y2);
        }
    }
    public static boolean canHaveRelatedGap(FormComponent fc1, FormComponent fc2, boolean vertical) {
        Rectangle b1 = fc1.getBoundsRelativeToRoot();
        Rectangle b2 = fc2.getBoundsRelativeToRoot();
        //note, vertical does *not* mean compare y axis!!!
        if(vertical) {
            return SnapTo.overlap(b1.x, b1.x+b1.width, b2.x, b2.x+b2.width);
        } else {
            return SnapTo.overlap(b1.y, b1.y+b1.height, b2.y, b2.y+b2.height);
        }
    }
    
    public static boolean canHaveRelatedGap(Vector fcs, FormComponent fc2, boolean vertical) {
        for(int i=0;i<fcs.size(); i++) {
            FormComponent fc1 = (FormComponent)fcs.elementAt(i);
            if(canHaveRelatedGap(fc1, fc2, vertical))
                return true;
        }
        return false;
    }
    
    public static boolean canHaveRelatedGap(Vector fcs, Vector fcs2, boolean vertical) {
        for(int i=0;i<fcs2.size(); i++) {
            FormComponent fc1 = (FormComponent)fcs2.elementAt(i);
            if(canHaveRelatedGap(fcs, fc1, vertical))
                return true;
        }
        return false;
    }
    
    /**
     * @param value
     * @param sel
     * @return
     */
    public static boolean isEnabled(Object constraint, FormComponent sel) {
        if(constraint.equals(ANCHOR_BOTTOM)
                || constraint.equals(ANCHOR_TOP)) {
            LayoutGroup lg = sel.getParent().getLayoutWrapper().getVGroup().getGroupContaining(sel);
            if(lg == null || lg.isExpandable(sel))
                return false;
        } else if(constraint.equals(ANCHOR_LEFT)
                || constraint.equals(ANCHOR_RIGHT)) {
            LayoutGroup lg = sel.getParent().getLayoutWrapper().getHGroup().getGroupContaining(sel);
            if(lg == null || lg.isExpandable(sel))
                return false;
        }
        return true;
    }

}
