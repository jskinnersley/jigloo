/*
 * Created on Mar 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayoutSupport;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.groupLayout.Alignment;
import com.cloudgarden.jigloo.groupLayout.ComponentPlacement;
import com.cloudgarden.jigloo.groupLayout.GroupLayout.Spring;
import com.cloudgarden.jigloo.groupLayout.LayoutStyle;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupElement {

    private Object[] args;
    private String addMethod;
    private int size;
    private Spring spring;
    private LayoutGroup group;
    private boolean protectSize = false;
    
    public GroupElement(String addMethod, Object[] args) {
        if(addMethod.equals("addComponent")) {
            addMethod = "add";
            if(args.length > 1 && args[1] instanceof Alignment) {
                Object tmp = args[0];
                args[0] = new Integer(((Alignment)args[1]).getVal());
                args[1] = tmp;
            }
        }
        if(addMethod.equals("addGroup")) {
            addMethod = "add";
            if(args.length > 1 && args[0] instanceof Alignment) {
                args[0] = new Integer(((Alignment)args[0]).getVal());
            }
        }
        if(addMethod.equals("addPreferredGap")) {
            if(args[0] instanceof ComponentPlacement) {
                args[0] = new Integer(((ComponentPlacement)args[0]).getVal());
            } else if(args.length > 2 && args[2] instanceof ComponentPlacement) {
                args[2] = new Integer(((ComponentPlacement)args[2]).getVal());
                if(args.length == 5) {
                    args[3] = Boolean.TRUE;
                    Object[] nargs = new Object[4];
                    for (int i = 0; i < nargs.length; i++) {
                        nargs[i] = args[i];
                    }
                    args = nargs;
                }
            }
        }
        if(addMethod.equals("addGap")) {
            addMethod = "add";
        }
        this.addMethod = addMethod;
        this.args = args;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String txt = "";
        if(addMethod.equals("addContainerGap"))
            txt+="CG";
        else if(addMethod.equals("addPreferredGap"))
            txt+="PG";
        if(args != null) {
            txt +=" [";
            for (int i = 0; i < args.length; i++) {
                if(i != 0)
                    txt += ",";
                txt += args[i];
            }
            int sz = -1;
            Spring sp = getSpring();
            if(sp != null)
                sz = sp.getSize();
            txt+="("+sz+")";
        }
        txt += "]";
        return txt; //+", size="+size;
    }
    
    public String getAddMethod() {
        return addMethod;
    }
    public void setAddMethod(String addMethod) {
        this.addMethod = addMethod;
    }
    public Object[] getArgs() {
        return args;
    }
    public void setArgs(Object[] args) {
        this.args = args;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Spring getSpring() {
        return group.getSpring(getIndexInParent());
//        return spring;
    }
    public void setSpring(Spring spring) {
        this.spring = spring;
    }

    public boolean isFixedPreferredGap() {
        return "addPreferredGap".equals(addMethod) 
        && args != null 
        && (args.length == 1 
                || (args.length == 3 
                        && LayoutGroup.MIN_GAP_INT.equals(args[1])
                        && (LayoutGroup.MIN_GAP_INT.equals(args[2]))));
    }
    
    public boolean isPreferredGap() {
        return "addPreferredGap".equals(addMethod);
    }
    
    public boolean isContainerGap() {
        return "addContainerGap".equals(addMethod);
    }
    
    public boolean isGap() {
        return getGroupOrFC() == null;
    }

    public boolean isGroup() {
        return getGroup() != null;
    }

    public void changeGapSize(int change) {
        if(!isGap())
            return;
        setGapSize(getSize()+change	);
    }
    
    public int getGapSize() {
        if(args != null) {
            if(args.length == 1 && args[0] instanceof Integer)
                return ((Integer)args[0]).intValue();
            if(args.length == 3 && args[1] instanceof Integer)
                return ((Integer)args[1]).intValue();
        }
        return -1;
    }
    
    public void setGapSize(int space) {
        if(!isGap())
            return;
        if(space == SnapTo.GAP_RELATED || space == SnapTo.GAP_UNRELATED) {
            addMethod = "addPreferredGap";
        }
        if(space == SnapTo.GAP_CONTAINER) {
            addMethod = "addContainerGap";
        }
        if(space < 0)
            space = 0;
        Integer gap = new Integer(space);
        if(getAddMethod().equals("addPreferredGap")) {
        	if(space == 0)
        		setArgs(new Object[] {new Integer(LayoutStyle.RELATED)} );
        	else
        		setArgs(new Object[] {new Integer(LayoutStyle.RELATED),gap, gap} );
        } else if(getAddMethod().equals("addContainerGap")) {
            if(space == 0)
                setArgs(null);
            else
                setArgs(new Object[] {gap, gap} );
        } else {
            setArgs(new Object[] {gap, gap, gap} );
        }
        setSize(space);
    }

    public void fixPreferredGapBug() {
//        addPreferredGap(RELATED, 5, 16)
//        if(isFixedPreferredGap() && args.length == 1) {
//            setArgs(new Object[] {
//                    LayoutGroup.RELATED_INT, LayoutGroup.MIN_GAP_INT, LayoutGroup.MAX_GAP_INT});
//        }
    }
    
    public boolean matchesGap(GroupElement elem) {
        if(elem == null || !isGap() || !elem.isGap())
            return false;
        if(!elem.getAddMethod().equals(addMethod))
            return false;
        if(elem.getArgs() == null && args == null)
            return true;
        if(elem.getArgs() == null && args != null)
            return false;
        if(elem.getArgs() != null && args == null)
            return false;
        if(elem.getArgs().length != args.length)
            return false;
        for(int i=0; i<args.length; i++) {
            if(args[i] != null && !args[i].equals(elem.getArgs()[i]))
                return false;
        }
        return true;
    }
    /**
     * @return
     */
    public Object getGroupOrFC() {
        //make sure we don't include addPreferredSize(JComponent,...) etc
        if(!"add".equals(addMethod))
            return null;
        if(args == null)
            return null;
        for (int j = 0; j < args.length; j++) {
            Object obj = args[j];
            if(obj instanceof FormComponent)
                return obj;
            else if(obj instanceof LayoutGroup)
                return obj;
        }
        return null;
    }


    public LayoutGroup getGroup() {
        //make sure we don't include addPreferredSize(JComponent,...) etc
        if(!"add".equals(addMethod))
            return null;
        if(args == null)
            return null;
        for (int j = 0; j < args.length; j++) {
            Object obj = args[j];
            if(obj instanceof LayoutGroup)
                return (LayoutGroup) obj;
        }
        return null;
    }

    public FormComponent getFC() {
        //make sure we don't include addPreferredSize(JComponent,...) etc
        if(!"add".equals(addMethod))
            return null;
        if(args == null)
            return null;
        for (int j = 0; j < args.length; j++) {
            Object obj = args[j];
            if(obj instanceof FormComponent)
                return (FormComponent) obj;
        }
        return null;
    }
    
    public int getAlignment() {
        if(args == null)
            return -1;
        
        if(args.length > 0 && (args[0] instanceof LayoutGroup || args[0] instanceof FormComponent)) {
            return group.getAlignment();
        } else if(args.length > 1 &&
                (args[1] instanceof LayoutGroup || args[1] instanceof FormComponent)) {
            return ((Integer)args[0]).intValue();
        }
        return -1;
    }

    /**
     * @param newAlignment
     */
    public void setAlignment(int newAlignment) {
        if(args == null)
            return;
        
        if(group == null || group.isSequential())
            newAlignment = LayoutGroup.REMOVE_ALIGNMENT;
        
        if(args.length > 0 && (args[0] instanceof LayoutGroup || args[0] instanceof FormComponent)) {
            if(newAlignment != LayoutGroup.REMOVE_ALIGNMENT) {
                Object[] nargs = new Object[args.length+1];
                nargs[0] = new Integer(newAlignment);
                for (int i = 1; i < nargs.length; i++) {
                    nargs[i] = args[i-1];
                }
                args = nargs;
            }
        } else if(args.length > 1 &&
                (args[1] instanceof LayoutGroup || args[1] instanceof FormComponent)) {
            if(newAlignment == LayoutGroup.REMOVE_ALIGNMENT) {
                Object[] nargs = new Object[args.length-1];
                for (int i = 0; i < nargs.length; i++)
                    nargs[i] = args[i+1];
                args = nargs;
            } else {
                args[0] = new Integer(newAlignment);
            }
        }
    }


    /**
     * @return
     */
    public GroupElement getCopy() {
        GroupElement elem = new GroupElement(addMethod, args);
        elem.setSize(size);
        return elem;
    }


    /**
     * @return
     */
    public boolean isFC() {
        return getFC() != null;
    }

    /**
     * @return
     */
    public boolean isExpandableGap() {
        return isGap() && isExpandable();
    }

    /**
     * @param group
     */
    public void setLayoutGroup(LayoutGroup group) {
        this.group = group;
    }

    public LayoutGroup getLayoutGroup() {
        return group;
    }

    public GroupElement getPreviousElement() {
        int pos = getIndexInParent();
        if(pos <= 0 || group.isParallel()) {
            if(group.getParent() != null)
                return group.getElement().getPreviousElement();
            return null;
        }
        LayoutGroup lg = group.getGroup(pos-1);
        if(lg != null && lg.isSequential()) {
            return lg.getLastElement();
        }
        return group.getElement(pos-1);
    }

    public GroupElement getNextElement() {
        int pos = getIndexInParent();
        if(pos >= group.getElementCount() - 1) {
            if(group.getParent() != null)
                return group.getElement().getNextElement();
            return null;
        }
        if(group.isParallel()) {
            if(group.getParent() != null)
                return group.getElement().getNextElement();
            return null;
        }
        LayoutGroup lg = group.getGroup(pos+1);
        if(lg != null && lg.isSequential()) {
            return lg.getFirstElement();
        }
        return group.getElement(pos+1);
    }

    public int getIntValue(int argNum) {
        if(args == null || argNum > args.length-1)
            return -10000;
        Object arg=  args[argNum];
        if(arg instanceof Integer)
            return ((Integer)arg).intValue();
        if(arg instanceof Short)
            return ((Short)arg).intValue();
        return -10000;
    }
    
    /**
     * @return
     */
    public boolean isExpandable() {
        if(isGroup()) {
            return getGroup().isExpandable();
        } else {
            if(expandFlag != -1)
                return expandFlag == LayoutGroup.FLAG_EXPAND;
            int lastArg = -1;
            if(args != null && args.length > 0)
                lastArg = getIntValue(args.length - 1);
            else
                return false;
            return lastArg >= LayoutGroup.EXPANDABLE_SIZE;
        }
    }
    
    public void setExpandable(boolean expandable) {
        Integer sz = new Integer(getSize());
        expandFlag = expandable ? LayoutGroup.FLAG_EXPAND : LayoutGroup.FLAG_NO_EXPAND;
        if(isContainerGap()) {
            if(args == null || args.length == 0) {
                if(expandable)
                    args = new Object[] {LayoutGroup.DEF_SIZE, LayoutGroup.MAX_SIZE};
            } else {
                if(expandable)
                    args = new Object[] {args[0], LayoutGroup.MAX_SIZE};
                else {
                    if(args[0].equals(LayoutGroup.DEF_SIZE))
                        args = null;
                    else
                        args = new Object[] {args[0], args[0]};
                }
            }
            return;
        }
        if(isPreferredGap()) {
            
            //indent gap - do nothing
            if(args.length == 3 && args[0] instanceof FormComponent)
                return;
            
            if(expandable) {
                if(args.length == 1) {
                    args = new Object[] {args[0], args[0], LayoutGroup.MAX_SIZE};
//                    args = new Object[] {LayoutGroup.RELATED_INT, sz, LayoutGroup.MAX_SIZE};
                } else if(args.length == 3) {
                    args = new Object[] {args[0], args[1], LayoutGroup.MAX_SIZE};
//                    args = new Object[] {LayoutGroup.RELATED_INT, args[1], LayoutGroup.MAX_SIZE};
                }
            } else {
                if(args.length == 3) {
//                    if(args[1].equals(LayoutGroup.MIN_GAP_INT))
//                        args = new Object[] {LayoutGroup.RELATED_INT, LayoutGroup.MIN_GAP_INT, LayoutGroup.MAX_GAP_INT};
//                    else
//                    args = new Object[] {LayoutGroup.RELATED_INT, args[1], args[1]};
                    args = new Object[] {args[0], args[1], LayoutGroup.PREF_SIZE};
                }
            }
            return;
        }
        if(isGap()) {
            if(expandable) {
                if(args.length == 1) {
                    args = new Object[] {LayoutGroup.MIN_SIZE, sz, LayoutGroup.MAX_SIZE};
                } else if(args.length == 3) {
                    args = new Object[] {LayoutGroup.MIN_SIZE, args[1], LayoutGroup.MAX_SIZE};
                }
            } else {
                if(args.length == 3) {
                	if(args[1] instanceof Integer && ((Integer)args[1]).intValue() == 0) {
                        //a zero gap - will be removed inside cleanupExtraGaps
                		args = new Object[] {args[0], args[1], new Integer(0)};
                	} else {
                		args = new Object[] {args[0], args[1], LayoutGroup.PREF_SIZE};
                	}
                }
            }
            return;
        }
        if(isGroup()) {
            LayoutGroup lg = getGroup();
            for(int i=0; i< lg.getElementCount(); i++)
                lg.getElement(i).setExpandable(expandable);
            return;
        }
        if(isFC()) {
            FormComponent fc = getFC();
            if(!protectSize && expandable)
                group.addTrailingGap(fc);
            Integer prefI = LayoutGroup.getPreferredSize(fc, group.isVertical());
            
//            if(expandable)
//                prefI=  LayoutGroup.PREF_SIZE;
            
            if(protectSize) {
                if(args.length <= 2)
                    prefI = LayoutGroup.PREF_SIZE;
                else if(args.length == 4)
                    prefI = (Integer) args[2];
                else if(args.length == 5)
                    prefI = (Integer) args[3];
                protectSize = false;
            }
            
            if(expandable) {
                if(args.length == 1) {
                    args = new Object[] {args[0], LayoutGroup.MIN_SIZE, prefI, LayoutGroup.MAX_SIZE};
                } else if(args.length == 2) {
                    args = new Object[] {args[0], args[1], LayoutGroup.MIN_SIZE, prefI, LayoutGroup.MAX_SIZE};
                } else if(args.length == 4) {
                    args = new Object[] {args[0], LayoutGroup.MIN_SIZE, prefI, LayoutGroup.MAX_SIZE};
                } else if(args.length == 5) {
                    args = new Object[] {args[0], args[1], LayoutGroup.MIN_SIZE, prefI, LayoutGroup.MAX_SIZE};
                }
                getLayoutGroup().fixGaps(this, true);
                getLayoutGroup().fixGaps(this,false);
            } else {
                if(prefI.equals(LayoutGroup.DEF_SIZE)) {
                    if(args.length == 4) {
//                        args = new Object[] {args[0]};
                        args = new Object[] {args[0], LayoutGroup.PREF_SIZE, LayoutGroup.PREF_SIZE, LayoutGroup.PREF_SIZE};
                    } else if(args.length == 5) {
                        args = new Object[] {args[0], args[1], LayoutGroup.PREF_SIZE, LayoutGroup.PREF_SIZE, LayoutGroup.PREF_SIZE};
                    }
                } else {
                    if(args.length == 4) {
                        args = new Object[] {args[0], LayoutGroup.PREF_SIZE, prefI, LayoutGroup.PREF_SIZE};
                    } else if(args.length == 5) {
                        args = new Object[] {args[0], args[1], LayoutGroup.PREF_SIZE, prefI, LayoutGroup.PREF_SIZE};
                    }
                }
            }
            return;
        }
    }

    private int anchored = 0;
    private int expandFlag = -1;
    
    /**
     * @param leading
     */
    public void setAnchorFlag(int anchored) {
        this.anchored = anchored;
    }

    /**
     * @return
     */
    public int getAnchorFlag() {
        return anchored;
    }

    /**
     * @param newGrp
     */
    public void moveTo(LayoutGroup newGrp) {
        group.remove(this, false);
        newGrp.add(-1, this);
    }

    public boolean isAnchored(boolean leading) {
        if(isFC()) {
            return group.isAnchored(getFC(), leading);
        } else if(isGroup()) {
            return getGroup().isAnchored(leading);
        }
        return false;
    }
    
    /**
     * @return
     */
    public boolean isInExpandableGroup() {
        if(isGroup() && getGroup().isExpandable())
            return true;
        GroupElement ge = group.getElement();
        if(ge == null)
            return false;
        if(ge.isExpandable())
            return true;
        LayoutGroup par = group.getParent();
        if(par == null || par.getElement() == null)
            return false;
        return par.getElement().isInExpandableGroup();
    }

    /**
     * 
     */
    public void resetPreferredSize() {
        expandFlag = 0;
        if(args.length == 4 || args.length == 1) {
            args = new Object[] {args[0], LayoutGroup.PREF_SIZE, LayoutGroup.DEF_SIZE, LayoutGroup.PREF_SIZE};
        } else if(args.length == 5 || args.length == 2) {
            args = new Object[] {args[0], args[1], LayoutGroup.PREF_SIZE, LayoutGroup.DEF_SIZE, LayoutGroup.PREF_SIZE};
        }
    }

    public void setExpandFlag(int expand) {
        expandFlag = expand;
    }

    public int getExpandFlag() {
        return expandFlag;
    }

    /**
     * 
     */
    public void protectSize() {
        protectSize = true;
    }

    /**
     * 
     */
    public void repairSize() {
        Integer prefI = LayoutGroup.getPreferredSize(getFC(), group.isVertical());
        setSizeInGroup(prefI.intValue());
    }

    public void setSizeInGroup(int size) {
        Integer sizeI = new Integer(size);
        if(args.length == 4) {
            args[2] = sizeI;
        } else if(args.length == 5) {
            args[3] = sizeI;
        }
        if(sizeI != LayoutGroup.PREF_SIZE) {
            if(args.length == 1) {
                args = new Object[] {args[0], LayoutGroup.DEF_SIZE, sizeI, LayoutGroup.PREF_SIZE};
            } else if(args.length == 2) {
                args = new Object[] {args[0], args[1], LayoutGroup.DEF_SIZE, sizeI, LayoutGroup.PREF_SIZE};
            }
        }
    }

    /**
     * @param selGrp
     */
    public void addAfter(LayoutGroup grp) {
        group.add(getIndexInParent()+1, grp);
    }

    public void addBefore(LayoutGroup grp) {
        group.add(getIndexInParent(), grp);
    }

    /**
     * @return
     */
    int getIndexInParent() {
        return group.getIndexOf(this);
    }

    /**
     * @param selGrp
     * @param gap_related
     */
    public void addGapAfter(int gap) {
        group.addGap(getIndexInParent()+1, gap);
    }
    public void addGapBefore(int gap) {
        group.addGap(getIndexInParent(), gap);
    }

    /**
     * @return
     */
    public boolean isZeroGap() {
        if(isGap() && (getIntValue(0) == 0 
                && getIntValue(1) == 0 
                && getIntValue(2) == 0))
        return true;
        return false;
    }
}
