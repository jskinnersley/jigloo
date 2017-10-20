/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.groupLayoutSupport;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayout.Baseline;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayout.LayoutStyle;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SnapTo {

    private FormComponent target;
    public FormComponent hFC;
    public FormComponent vFC;
    
    /**
     * isResize is true if one of x, x2, y or y2 is < 0 (in calculateSnapTo),
     * indicating that an edge has been dragged, as opposed to the element being
     * dragged. This is important since it indicates that the neighbouring
     * elements should remain the same after the SnapTo has been executed.
     */
    private boolean isResize = false;
    private boolean westSideChange = false;
    private boolean eastSideChange = false;
    private boolean northSideChange = false;
    private boolean southSideChange = false;
    
    /** if a gap occurs before after relFC and the size of this gap is at least
     * ALLOWABLE_GAP_FACTOR times the size of the element to be inserted,
     * then insertion is allowed
     */
    private static final double ALLOWABLE_GAP_FACTOR = 0.6;
    
    //the matching side for the target
    public int hMatch;
    //the matching side fot hFC - ie, if target is matched to the right of hFC then
    //hMatch will be LEFT and h2Match will be RIGHT
    public int h2Match;
    
    public int vMatch;
    public int v2Match;
    
    public int xLeft, xRight, yTop, yBottom;
    
    public int x0, y0;
    public int dx, dy;
    public int xRel, yRel;
    public int hfcdx, ySep; //the distance from hFC
    public int xSep, vfcdy; //the distance from vFV
    int CONT_GAP_NORTH, CONT_GAP_SOUTH, CONT_GAP_EAST, CONT_GAP_WEST;
    
    public static final int MODE_ELEMENT = 1;
    public static final int MODE_CONTAINER = 2;
    
    private int mode;
    
    private static JButton stubComponent = new JButton("stub");
    public static final int UNCHANGED_SIDE = -10000;
    public static final int INSERT_BEFORE = -1;
    public static final int INSERT_AFTER = 1;
    
    //true if match is flush with container edge
    boolean hContEdge = false;
    boolean vContEdge = false;
    
    int cGapLt;
    int cGapRt;
    int cGapTp;
    int cGapBt;
    
    int insertX = 0;
    int insertY = 0;

    public static final int BASELINE = 1;
    public static final int LEADING_MATCH = 2;
    public static final int TRAILING_MATCH = 3;
    public static final int INDENT = 6;
    public static final int RELATIVE = 7;

    public static final int GAP_RELATED = -10;
    public static final int GAP_UNRELATED = -11;
    public static final int GAP_CONTAINER = -12;
    public static final int GAP_INDENT = -14;
    public static final int GAP_NOT_DIRECTLY_LINKED = -15;

    private static final int WIGGLE = 5;

    public FormComponent getTarget() {
        return target;
    }
    
    public boolean isRelativeMatch(boolean vertical) {
        if(vertical) {
            return vMatch == RELATIVE;
        } else {
            return hMatch == RELATIVE;
        }
    }

    /**
     * if before == true and vertical == false, returns true if hFC is
     * to the left of target
     */
    public boolean isSequentialMatch(boolean vertical, boolean before) {
        if(vertical) {
            if(before)
                return vMatch == TRAILING_MATCH && v2Match == LEADING_MATCH;
            else
                return vMatch == LEADING_MATCH && v2Match == TRAILING_MATCH;
        } else {
            if(before)
                return (hMatch == LEADING_MATCH && h2Match == TRAILING_MATCH) || hMatch == INDENT;
            else
                return hMatch == TRAILING_MATCH && h2Match == LEADING_MATCH;
        }
    }

    public boolean isParallelMatch(boolean vertical, boolean before) {
        if(vertical) {
            if(vMatch == BASELINE)
                return true;
            if(before)
                return vMatch == LEADING_MATCH && v2Match == LEADING_MATCH;
            else
                return vMatch == TRAILING_MATCH && v2Match == TRAILING_MATCH;
        } else {
            if(before)
                return hMatch == LEADING_MATCH && h2Match == LEADING_MATCH;
            else
                return hMatch == TRAILING_MATCH && h2Match == TRAILING_MATCH;
        }
    }

    public static boolean hAlignIsHorizontal(int hMatch, int h2Match) {
        return (hMatch == LEADING_MATCH && h2Match == TRAILING_MATCH)
                || (h2Match == LEADING_MATCH && hMatch == TRAILING_MATCH);
    }

    public static boolean vAlignIsVertical(int vMatch, int v2Match) {
        return (vMatch == LEADING_MATCH && v2Match == TRAILING_MATCH)
                || (v2Match == LEADING_MATCH && vMatch == TRAILING_MATCH);
    }

    public boolean hAlignIsRelative() {
        return hMatch == RELATIVE;
    }
    
    public boolean vAlignIsRelative() {
        return vMatch == RELATIVE;
    }
    
    public boolean hAlignIsHorizontal() {
        return hAlignIsHorizontal(hMatch, h2Match);
    }
    
    public boolean vAlignIsVertical() {
        return vAlignIsVertical(vMatch, v2Match);
    }
    
    /**
     * xa, ya, xa2, ya2 are relative to editor's viewport
     * mode must be either MODE_ELEMENT (default) of MODE_CONTAINER, which
     * assumes the element being resized is the container.
     * @return
     */
    public static SnapTo calculateSnapTo(
            int x, int y, int x2, int y2,
            int xa, int ya, int xa2, int ya2,
            FormComponent moveTarget, FormComponent selected, 
			FormEditor editor, int mode) {
        
    	//if the windowFrame is dragged vertically by it's edge, and not resized, then
    	//x and x2 will both be UNCHANGED_SIDE but this will not be a resize
    	boolean isResize = (
                (x == UNCHANGED_SIDE && x2 != UNCHANGED_SIDE)
                || (x2 == UNCHANGED_SIDE && x != UNCHANGED_SIDE)
                || (y == UNCHANGED_SIDE && y2 != UNCHANGED_SIDE)
                || (y2 == UNCHANGED_SIDE&& y != UNCHANGED_SIDE) );
    	
    	if(mode == MODE_CONTAINER && !isResize) {
    		return null;
    	}
    
        Vector selComps = null;
        FormComponent newFC = editor.getAboutToBeAddedComponent();
        
        if(editor.getCurrentAction() == null)
            selComps = editor.getSelectedComponents();
        
        SnapTo st = new SnapTo();
        
        st.xLeft = xa;
        st.xRight = xa2;
        st.yTop = ya;
        st.yBottom = ya2;
        
        st.westSideChange = x != UNCHANGED_SIDE;
        st.eastSideChange = x2 != UNCHANGED_SIDE;
        st.northSideChange = y != UNCHANGED_SIDE;
        st.southSideChange = y2 != UNCHANGED_SIDE;
        
        st.isResize = isResize;
        
        st.mode = mode;
        
        try {
            st.target = moveTarget;
            st.hfcdx = st.ySep = st.xSep = st.vfcdy = 1000001;
            int selBL = 0;
            if (selected != null) {
                selBL = getBaseline(selected, ya2 - ya);
            } else {
                selBL = getBaseline(newFC, ya2 - ya) ;
            }
            
            Rectangle rb = moveTarget.getRootComponent().getBoundsRelativeToRoot();
            Rectangle rb1;
            
            if(mode == MODE_CONTAINER) {
                rb1 = JiglooUtils.getBoundingRectangle(moveTarget.getChildren());
                if(rb1 == null) //moveTarget contains no children!
                    return null;
                moveTarget.subtractInsets(rb1);
            } else {
                rb1 = moveTarget.getInsideBoundsRelativeToRoot();
                Rectangle trb = moveTarget.getRootComponent().getBoundsRelativeToRoot();
                rb1.x -= trb.x;
                rb1.y -= trb.y;
            }
            
            int cc = moveTarget.getChildCount();
            boolean match;
            int dyt, dxt;
            
        	JComponent selComp = null;
        	JComponent parComp = null;
        	
            if(moveTarget.isSwing()) {
            	selComp = stubComponent;
            	parComp = (JComponent)moveTarget.getComponent();
            	if(selected != null && selected.getComponent() instanceof JComponent)
            		selComp = (JComponent)selected.getComponent();
            	LayoutStyle LS = LayoutStyle.getSharedInstance();
            	
            	st.CONT_GAP_NORTH = LS.getContainerGap(selComp, SwingConstants.NORTH, parComp);
            	st.CONT_GAP_SOUTH = LS.getContainerGap(selComp, SwingConstants.SOUTH, parComp);
            	st.CONT_GAP_EAST = LS.getContainerGap(selComp, SwingConstants.EAST, parComp);
            	st.CONT_GAP_WEST = LS.getContainerGap(selComp, SwingConstants.WEST, parComp);
            } else {
            	st.CONT_GAP_NORTH = 12;
            	st.CONT_GAP_SOUTH = 12;
            	st.CONT_GAP_EAST = 12;
            	st.CONT_GAP_WEST = 12;
            }
            
            LayoutWrapper lw = moveTarget.getLayoutWrapper();
            LayoutGroup hlg, hlg0 = lw.getHGroup();
            LayoutGroup vlg, vlg0 = lw.getVGroup();
            
            boolean hConMatch = false;
            boolean vConMatch = false;

            //v4.0M3
            //container gaps - x direction
            int gap, gap2;
            if(mode == MODE_ELEMENT)
                gap = Math.abs(dxt = x - rb1.x - st.CONT_GAP_WEST);
            else
            	gap = Math.abs(dxt = x - rb1.x + st.CONT_GAP_WEST);
            if(gap < WIGGLE) {
//            	System.out.println("gap="+gap+", x="+x+", rb1.x="+rb1.x);
                st.hMatch = LEADING_MATCH;
                st.h2Match = TRAILING_MATCH;
                st.ySep = 0;
                st.xRel = x - rb1.x ;
                st.hFC = null;
                st.dx = -dxt;
                st.y0 = ya;
                st.hContEdge = false;
                hConMatch = true;
            }
            gap2 = Math.abs(dxt = x - rb1.x);
            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.hMatch = LEADING_MATCH;
                st.h2Match = TRAILING_MATCH;
                st.ySep = 0;
                st.xRel = x - rb1.x ;
                st.hFC = null;
                st.dx = -dxt;
                st.y0 = ya;
                st.hContEdge = true;
                hConMatch = true;
            }
            
            if(mode == MODE_ELEMENT)
                gap2 = Math.abs(dxt = rb1.x + rb1.width - x2 - st.CONT_GAP_EAST);
            else
            	gap2 = Math.abs(dxt = rb1.x + rb1.width - x2 + st.CONT_GAP_EAST);
            
            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.hMatch = TRAILING_MATCH;
                st.h2Match = LEADING_MATCH;
                st.ySep = 0;
                st.xRel = x - rb1.x ;
                st.hFC = null;
                st.dx = dxt;
                st.y0 = ya;
                st.hContEdge = false;
                hConMatch = true;
            }
            gap2 = Math.abs(dxt = rb1.x + rb1.width - x2);
            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.hMatch = TRAILING_MATCH;
                st.h2Match = LEADING_MATCH;
                st.ySep = 0;
                st.xRel = x - rb1.x ;
                st.hFC = null;
                st.dx = dxt;
                st.y0 = ya;
                st.hContEdge = true;
                hConMatch = true;
            }
            if(!hConMatch) {
                if (x > 0 && betterMatch(st, false, x, RELATIVE, RELATIVE)) {
                    dxt = 0;
                    st.ySep = 0;
                    st.xRel = x - rb1.x ;
                } else if (x2 > 0 && betterMatch(st, false, x2, RELATIVE, RELATIVE)) {
                    dxt = 0;
                    st.ySep = 0;
                    st.xRel = x2 - rb1.x ;
                }
            }
            
            //container gaps - y direction
            if(mode == MODE_ELEMENT)
            	gap = Math.abs(dyt = y - rb1.y - st.CONT_GAP_NORTH);
            else
            	gap = Math.abs(dyt = y - rb1.y + st.CONT_GAP_NORTH);

            if(gap < WIGGLE) {
                st.vMatch = LEADING_MATCH;
                st.v2Match = TRAILING_MATCH;
                st.xSep = 0;
                st.yRel = y -  rb1.y;
                st.vFC = null;
                st.dy = -dyt;
                st.x0 = xa;
                st.vContEdge = false;
                vConMatch = true;
            }
            gap2 = Math.abs(dyt = y - rb1.y);
            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.vMatch = LEADING_MATCH;
                st.v2Match = TRAILING_MATCH;
                st.xSep = 0;
                st.yRel = y - rb1.y;
                st.vFC = null;
                st.dy = -dyt;
                st.x0 = xa;
                st.vContEdge = true;
                vConMatch = true;
            }
            
            if(mode == MODE_ELEMENT)
                gap2 = Math.abs(dyt = rb1.y + rb1.height - y2 - st.CONT_GAP_SOUTH);
            else
            	gap2 = Math.abs(dyt = rb1.y + rb1.height - y2 + st.CONT_GAP_SOUTH);

            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.vMatch = TRAILING_MATCH;
                st.v2Match = LEADING_MATCH;
                st.xSep = 0;
                st.yRel = y - rb1.y;
                st.vFC = null;
                st.dy = dyt;
                st.x0 = xa;
                st.vContEdge = false;
                vConMatch = true;
            }
            gap2 = Math.abs(dyt = rb1.y + rb1.height - y2);
            if(gap2 < gap && gap2 < WIGGLE) {
                gap = gap2;
                st.vMatch = TRAILING_MATCH;
                st.v2Match = LEADING_MATCH;
                st.xSep = 0;
                st.yRel = y - rb1.y;
                st.vFC = null;
                st.dy = dyt;
                st.x0 = xa;
                st.vContEdge = true;
                vConMatch = true;
            }
            if(!vConMatch) {
                if (y > 0 && betterMatch(st, true, y, RELATIVE, RELATIVE)) {
                    dyt = 0;
                    st.xSep = 0;
                    st.yRel = y - rb1.y;
                } else if (y2 > 0 && betterMatch(st, true, y2, RELATIVE, RELATIVE)) {
                    dyt = 0;
                    st.xSep = 0;
                    st.yRel = y2 - rb1.y;
                }
            }
            
//            System.out.println("SnapTo:" +
//            		"\n hMatch="+(st.hMatch == LEADING_MATCH ? "LEADING" : "")+(st.hMatch == TRAILING_MATCH ? "TRAILING" : "")+
//					"\n h2Match="+(st.h2Match == LEADING_MATCH ? "LEADING" : "")+(st.h2Match == TRAILING_MATCH ? "TRAILING" : "")+
//					"\n dx="+st.dx+", hFC="+st.hFC+
//					"\n CONT_GAP_WEST="+st.CONT_GAP_WEST);
            
            if(mode == MODE_ELEMENT) {
            	
            	for (int i = 0; i < cc; i++) {
            		FormComponent fc = moveTarget.getChildAt(i);
            		if (selComps != null && selComps.contains(fc))
            			continue;
            		
            		int COMP_GAP_INDENT = getPreferredGap(fc, selComp,
            				LayoutStyle.INDENT, SwingConstants.EAST, parComp);
            		int COMP_GAP_WEST_REL = getPreferredGap(fc, selComp,
            				LayoutStyle.RELATED, SwingConstants.WEST, parComp);
            		int COMP_GAP_WEST_UNREL = getPreferredGap(fc, selComp,
            				LayoutStyle.UNRELATED, SwingConstants.WEST, parComp);
            		int COMP_GAP_EAST_REL = getPreferredGap(fc, selComp,
            				LayoutStyle.RELATED, SwingConstants.EAST, parComp);
            		int COMP_GAP_EAST_UNREL = getPreferredGap(fc, selComp,
            				LayoutStyle.UNRELATED, SwingConstants.EAST, parComp);
            		int COMP_GAP_NORTH_REL = getPreferredGap(fc, selComp,
            				LayoutStyle.RELATED, SwingConstants.NORTH, parComp);
            		int COMP_GAP_SOUTH_REL = getPreferredGap(fc, selComp,
            				LayoutStyle.RELATED, SwingConstants.SOUTH, parComp);
            		int COMP_GAP_NORTH_UNREL = getPreferredGap(fc, selComp,
            				LayoutStyle.UNRELATED, SwingConstants.NORTH, parComp);
            		int COMP_GAP_SOUTH_UNREL = getPreferredGap(fc, selComp,
            				LayoutStyle.UNRELATED, SwingConstants.SOUTH, parComp);
            		
            		Rectangle fcBnds = fc.getBoundsRelativeToRoot();
            		fcBnds.x -= rb.x;
            		fcBnds.y -= rb.y;
            		
            		if(!LayoutGroup.canHaveRelatedGap(fcBnds, xa, xa2, ya, ya2, true)) {
            			COMP_GAP_NORTH_REL = COMP_GAP_SOUTH_REL = 
            				COMP_GAP_NORTH_UNREL = COMP_GAP_SOUTH_UNREL = 100000;
            		}
            		if(!LayoutGroup.canHaveRelatedGap(fcBnds, xa, xa2, ya, ya2, false)) {
            			COMP_GAP_EAST_REL = COMP_GAP_WEST_REL = 
            				COMP_GAP_EAST_UNREL = COMP_GAP_WEST_UNREL = 100000;
            		}
            		//                System.out.println("EAST GAP="+COMP_GAP_EAST+", WEST_GAP="+COMP_GAP_WEST+", "+fc);
            		
            		hlg = hlg0.getGroupContaining(fc);
            		vlg = vlg0.getGroupContaining(fc);
            		
            		int hoff = 0;
            		if(hlg != null && hlg.isParallel())
            			hoff = hlg.getLimits()[0];
            		else
            			hoff = fcBnds.x;
            		
            		int voff = 0;
            		if(vlg != null && vlg.isParallel())
            			voff = vlg.getLimits()[0];
            		else
            			voff = fcBnds.y;
            		
            		int bl = getBaseline(fc);
            		int xSep = getSep(fcBnds.x, fcBnds.x + fcBnds.width, xa, xa2);
            		int ySep = getSep(fcBnds.y, fcBnds.y + fcBnds.height, ya, ya2);
            		int xRel = xa - hoff;
            		int yRel = ya - voff;
            		int mx = (xa + xa2) / 2;
            		int my = (ya + ya2) / 2;
            		
            		int fcY1 = fcBnds.y;
            		int fcY2 = fcY1 + fcBnds.height;
            		int fcX1 = fcBnds.x;
            		int fcX2 = fcX1 + fcBnds.width;
            		
            		boolean xOver = overlap(xa, xa2, fcX1, fcX2);
            		boolean yOver = overlap(ya, ya2, fcY1, fcY2);
            		
            		int dx = (xa+xa2)/2 - (fcX1+fcX2)/2;
            		int dy = (ya+ya2)/2 - (fcY1+fcY2)/2;
            		
            		int insertX = 0;
            		int insertY = 0;
            		
            		if(xOver && yOver) {
            			if(Math.abs(dy*(fcX2-fcX1)) > Math.abs(dx*(fcY2-fcY1))) {
            				if(dy > 0)
            					insertY = 1;
            				else
            					insertY = -1;
            			} else {
            				if(dx > 0)
            					insertX = 1;
            				else
            					insertX = -1;
            			}
            		}
            		
//            		System.out.println("XOVER="+xOver+", YOVER="+yOver+", XINSERT="+insertX+
//            				", YINSERT="+insertY+", fc="+fc);
            		match = false;
            		
            		//v4.0M3
            		if(st.insertX == 0) {
            			if ( insertX != 0) {
            				st.insertX = insertX;
            				st.hMatch = RELATIVE;
            				st.h2Match = RELATIVE;
            				st.dx = 0;
            				st.xRel = xRel;
            				match = true;
            			} else {
            				gap = 10000;
            				if (yOver) {
            					//========related matches===============
            					gap = Math.abs(dxt = x - (fcBnds.x + fcBnds.width + COMP_GAP_EAST_REL));
            					if(gap < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						st.dx = -dxt;
            						st.cGapRt = COMP_GAP_EAST_REL;
            						match = true;
            					}
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x - COMP_GAP_WEST_REL));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = COMP_GAP_WEST_REL;
            						match = true;
            					}
            					//========unrelated matches===============
            					gap2 = Math.abs(dxt = x - (fcBnds.x + fcBnds.width + COMP_GAP_EAST_UNREL));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapRt = COMP_GAP_EAST_UNREL;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x - COMP_GAP_WEST_UNREL));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = COMP_GAP_WEST_UNREL;
            						match = true;
            					} 
            					//========flush matches===============
            					gap2 = Math.abs(dxt = x - (fcBnds.x + fcBnds.width));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapRt = 0;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = 0;
            						match = true;
            					} 
            				}
            				if(!match) {
            					gap2 = Math.abs(dxt = x - fcBnds.x);
            					if(gap2 < gap && gap2 < WIGGLE 
            							&& betterMatch(st, false, ySep, LEADING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x + fcBnds.width));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x - (fcBnds.x + COMP_GAP_INDENT));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, INDENT, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = COMP_GAP_INDENT;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x - (fcBnds.x + fcBnds.width + COMP_GAP_EAST_REL));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapRt = COMP_GAP_EAST_REL;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x - COMP_GAP_WEST_REL));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = COMP_GAP_WEST_REL;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x - (fcBnds.x + fcBnds.width + COMP_GAP_EAST_UNREL));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapRt = COMP_GAP_EAST_UNREL;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x - COMP_GAP_WEST_UNREL));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = COMP_GAP_WEST_UNREL;
            						match = true;
            					}
            					//==========flush (butting) matches==========
            					gap2 = Math.abs(dxt = x - (fcBnds.x + fcBnds.width));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapRt = 0;
            						match = true;
            					} 
            					gap2 = Math.abs(dxt = x2 - (fcBnds.x));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, false, ySep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dx = -dxt;
            						st.cGapLt = 0;
            						match = true;
            					}
            				}
            				if(!match) {
            					if(st.insertX == 0 && 
            							betterMatch(st, false, xRel, RELATIVE, RELATIVE) && xRel > 0 ) {
            						st.hMatch = RELATIVE;
            						st.h2Match = RELATIVE;
            						st.dx = 0;
            						st.xRel = xRel;
            						match = true;
            					}
            				}
            			}
            			
            			if (match) {
            				//don't set st.dt = -dxt here because dxt gets changed in every "if" clause above
            				//don't want both insertX and insertY to be non-zero
            				if(!isResize || st.eastSideChange || st.westSideChange) {
            					if(insertX != 0)
            						st.insertY = 0;
            				}
            				
            				st.insertX = insertX;
            				st.hFC = fc;
            				st.y0 = my;
            				st.ySep = ySep;
            			}
            		}
            		
            		match = false;
            		
            		boolean allowEdgeMatch = true;
            		//don't allow match with baseline element if it is not at edge of group
            		if(vlg != null && vlg.getAlignment(vlg.getIndexOf(fc)) == GroupLayout.BASELINE) {
            			if(!vlg.matchesEndBoundary(fc, false))
            				allowEdgeMatch = false;
            		}
            		
            		if(st.insertY == 0) {
            			if (insertY != 0) {
            				st.insertY = insertY;
            				st.vMatch = RELATIVE;
            				st.v2Match = RELATIVE;
            				st.dy = 0;
            				st.yRel = yRel;
            				match = true;
            			} else {
            				gap = 100000;
            				gap2 = Math.abs(dyt = (y + selBL) - (fcBnds.y + bl));
            				if (allowBaselineMatch(fc)
            						&& allowBaselineMatch(selected)
									&& gap2 < WIGGLE
									&& betterMatch(st, true, xSep, BASELINE, BASELINE)) {
            					gap = gap2;
            					st.dy = -dyt;
            					match = true;
            				} else if(!allowEdgeMatch) {
            					match = false;
            					continue;
            				}
            				if(!match) {
            					gap2 = Math.abs(dyt = y - fcBnds.y);
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, LEADING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y2 - (fcBnds.y + fcBnds.height));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, TRAILING_MATCH, TRAILING_MATCH)) {
            						st.dy = -dyt;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y2 - (fcBnds.y - COMP_GAP_NORTH_REL));
            					if (gap2 < gap &&  gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapTp = COMP_GAP_NORTH_REL;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y2 - (fcBnds.y - COMP_GAP_NORTH_UNREL));
            					if (gap2 < gap &&  gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapTp = COMP_GAP_NORTH_UNREL;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y - (fcBnds.y + fcBnds.height + COMP_GAP_SOUTH_REL));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapBt = COMP_GAP_SOUTH_REL;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y - (fcBnds.y + fcBnds.height + COMP_GAP_SOUTH_UNREL));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapBt = COMP_GAP_SOUTH_UNREL;
            						match = true;
            					}
            					//==========flush (butting) matches==========
            					gap2 = Math.abs(dyt = y - (fcBnds.y + fcBnds.height));
            					if (gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, LEADING_MATCH, TRAILING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapBt = 0;
            						match = true;
            					}
            					gap2 = Math.abs(dyt = y2 - (fcBnds.y));
            					if(gap2 < gap && gap2 < WIGGLE
            							&& betterMatch(st, true, xSep, TRAILING_MATCH, LEADING_MATCH)) {
            						gap = gap2;
            						st.dy = -dyt;
            						st.cGapTp = 0;
            						match = true;
            					}
            					
            				}
            				if (!match) {
            					if(st.insertY == 0 && 
            							betterMatch(st, true, yRel, RELATIVE, RELATIVE) && yRel > 0) {
            						st.vMatch = RELATIVE;
            						st.v2Match = RELATIVE;
            						st.dy = 0;
            						st.yRel = yRel;
            						match = true;
            					}
            				}
            			}
            			
            			if (match) {
            				//don't want both insertX and insertY to be non-zero
            				if(!isResize || st.northSideChange || st.southSideChange) {
            					if(insertY != 0)
            						st.insertX = 0;
            				}

            				st.insertY = insertY;
            				st.vFC = fc;
            				st.x0 = mx;
            				st.xSep = xSep;
            			}
            		}
            	}  
            }
//            System.out.println("HMATCH = "+st.hMatch+" VMATCH = "+st.vMatch+
//            		", moveTarget="+st.target+
//            		", HFC=" +st.hFC+", VFC="+st.vFC+", insertX="+st.insertX+", insertY="+st.insertY
//            		+", xRel="+st.xRel+", yRel="+st.yRel+", yTop="+st.yTop);

        } catch(Throwable t) {
            jiglooPlugin.handleError(t);
        }
        return st;
    }

    /**
     * @param selected
     * @param fc
     * @return
     */
    public static boolean allowBaselineMatch(FormComponent fc) {
        if(fc != null && (
                fc.isA(JTabbedPane.class) 
                || fc.isSubclassOf(JPanel.class)
                || fc.isSubclassOf(JScrollPane.class)
                || fc.isSubclassOf(JList.class)
                || fc.isSubclassOf(JTextArea.class)
                || fc.isSubclassOf(JEditorPane.class)
                || fc.isSubclassOf(JTree.class)
                || fc.isSubclassOf(JTable.class)
                || fc.isSubclassOf(JTextPane.class)
                ))
            return false;
        return true;
    }

    /**
     * @param fc
     * @param selComp
     * @param indent2
     * @param east
     * @param parComp
     * @return
     */
    static int getPreferredGap(FormComponent fc, JComponent selComp, int style, 
    		int direction, JComponent parComp) {
    	if(fc.getComponent() instanceof JComponent && selComp != null) {
    		return LayoutStyle.getSharedInstance().getPreferredGap(
    				(JComponent)fc.getComponent(),
					selComp, 
					style,
					direction, 
					parComp);
    	} else {
    		if(style == LayoutStyle.INDENT)
    			return 12;
    		if(style == LayoutStyle.UNRELATED)
    			return 12;
    		if(style == LayoutStyle.RELATED)
    			return 6;
    	}
    	return 6;
    }

    /**
     * @param xa
     * @param xa2
     * @param fcX1
     * @param fcX2
     * @return
     */
    public static boolean overlap(int xa, int xa2, int xb, int xb2) {
    	if(xa < xb && xa2 <= xb)
    		return false;
    	if(xa >= xb2 && xa2 > xb2)
    		return false;
    	
    	if(xb < xa && xb2 <= xa)
    		return false;
    	if(xb >= xa2 && xb2 > xa2)
    		return false;
    	return true;
    	
//        if(xa > xb && xa < xb2)
//            return true;
//        if(xa2 > xb && xa2 < xb2)
//            return true;
//        if(xb > xa && xb < xa2)
//            return true;
//        if(xb2 > xa && xb2 < xa2)
//            return true;
//        return false;
    }

    private static boolean betterMatch(SnapTo st, boolean vertical, int sep,
            int match, int match2) {
        int bm = compareMatch(st, vertical, match, match2);
        if (bm == -1)
            return false;
        if (vertical) {
            if (bm == 0) {
                if(match == RELATIVE) {
                    if(Math.abs(sep) > Math.abs(st.yRel))
                        return false;
                } else {
                    if(sep > st.xSep)
                        return false;
                }
            }
            st.vMatch = match;
            st.v2Match = match2;
        } else {
            if (bm == 0) {
                if(match == RELATIVE) {
                    if(Math.abs(sep) > Math.abs(st.xRel))
                        return false;
                } else {
                    if(sep > st.ySep)
                        return false;
                }
            }
            st.hMatch = match;
            st.h2Match = match2;
        }
        return true;
    }

    private static int compareMatch(SnapTo st, boolean vertical, int match,
            int match2) {
        boolean a1, a2;
        if (vertical) {
            if (st.vMatch == 0)
                return 1;
            if (match == RELATIVE) {
                if (st.vMatch == RELATIVE)
                    return 0;
                return -1;
            }
            if (st.vMatch == RELATIVE)
                return 1;
            if (st.vMatch == BASELINE) {
                if (match == BASELINE)
                    return 0;
                return -1;
            }
            if (match == BASELINE)
                return 1;
            if(true)
                return 0;
            a1 = st.vAlignIsVertical();
            a2 = vAlignIsVertical(match, match2);
            if (!a1 && a2)
                return -1;
            if (a1 && a2)
                return 0;
            if (!a1 && !a2)
                return 0;
            return 1;
        } else {
            if (st.hMatch == 0)
                return 1;
            if (match == RELATIVE) {
                if (st.hMatch == RELATIVE)
                    return 0;
                return -1;
            }
            if (st.hMatch == RELATIVE)
                return 1;
            if(true)
                return 0;
            a1 = st.hAlignIsHorizontal();
            a2 = hAlignIsHorizontal(match, match2);
            if (!a1 && a2)
                return 1;
            if (a1 && a2)
                return 0;
            if (!a1 && !a2)
                return 0;
            return -1;
        }
    }

    public static int getBaseline(FormComponent fc) {
        return getBaseline(fc, -1);
    }
    
    private static int getBaseline(FormComponent fc, int newHeight) {
        if (fc == null || fc.getComponent() == null || !(fc.getComponent() instanceof JComponent))
            return 0;
        try {
            JComponent comp = (JComponent) fc.getComponent();
            Dimension sz = comp.getSize();
            int bl = 0;
            if(sz.height > 0)
                bl = Baseline.getBaseline(comp, sz.width, newHeight != -1 ? newHeight : sz.height);
            else
                bl = Baseline.getBaseline(comp);
            if(bl == -1) {
                bl = comp.getHeight()/2;
                if(bl == 0)
                    bl = comp.getPreferredSize().height/2;
            }
//            if(newHeight != -1) {
//                bl = bl * newHeight/comp.getHeight();
//            }
            return bl;
        } catch(Throwable t) {
            return 0;
        }
    }

    private static int getSep(int a, int a2, int b, int b2) {
        
        //if overlap, return 0 separation
        if(a > b && a < b2)
            return 0;
        if(a2 > b && a2 < b2)
            return 0;
        
        if(true)
            return Math.abs((a+a2)/2 - (b+b2)/2);
        
        if (a2 < b)
            return b - a2;
        if (b2 < a)
            return a - b2;
        return 0;
    }

    public boolean equals(Object obj) {
        return equals(obj, 0);
    }
    
    /**
     * @param dirn if dirn == 1, compares vertically, if dirn == 2, compares horizontally,
     * if dirn == 0, compares both directions
     * @return
     */
    public boolean equals(Object obj, int dirn) {
        if (!(obj instanceof SnapTo))
            return false;
        SnapTo st = (SnapTo) obj;
        if(dirn == 0 || dirn == 1) {
            if (st.vFC == null && vFC != null)
                return false;
            if (st.vFC != null && vFC == null)
                return false;
            if (st.vFC != null && vFC != null
                    && (!st.vFC.equals(vFC) || st.vMatch != vMatch))
                return false;
            if(st.vMatch != vMatch)
                return false;
            //v4.0M3
            if(st.cGapBt != cGapBt)
                return false;
            if(st.cGapTp != cGapTp)
                return false;
        }
        if(dirn == 0 || dirn == 2) {
            if (st.hFC == null && hFC != null)
                return false;
            if (st.hFC != null && hFC == null)
                return false;
            if (st.hFC != null && hFC != null
                    && (!st.hFC.equals(hFC) || st.hMatch != hMatch))
                return false;
            if(st.hMatch != hMatch)
                return false;
            //v4.0M3
            if(st.cGapLt != cGapLt)
                return false;
            if(st.cGapRt != cGapRt)
                return false;
        }
        return true;
    }

    private void drawLine(GC gc, int origX, int origY, int x1, int y1, int x2, int y2) {
    	gc.drawLine(origX+x1, origY+y1, origX+x2, origY+y2);
    }
    
    public void drawLines(GC gc, int origX, int origY) {
        
    	if(mode == MODE_CONTAINER)
    		return;
    	
        int x, y;
        
        gc.setLineStyle(SWT.LINE_DOT);
        gc.setForeground(ColorManager.getColor(250, 50, 50));
        
        int OVERHANG = 30;
        
        Rectangle tb;

        tb = target.getInsideBoundsRelativeToRoot();

        Rectangle trb = target.getRootComponent().getBoundsRelativeToRoot();
        tb.x -= trb.x;
        tb.y -= trb.y;
        
        Rectangle rb = target.getRootComponent().getBoundsRelativeToViewport();
        
        if(!hAlignIsRelative()) {
            if (hFC == null) {
                x = 20;
                
                if (hMatch == LEADING_MATCH) {
                    x = tb.x + (hContEdge ? 0 : CONT_GAP_WEST);
                } else if (hMatch == TRAILING_MATCH) {
                    x = tb.x + tb.width - (hContEdge ? 0 : CONT_GAP_EAST);
                } else {
//                    System.out.println("REL MATCHBUT HMATCH WEIRD");
                }
                if(hMatch != 0)
                    drawLine(gc, origX, origY, x, tb.y, x, tb.y  + tb.height);
            } else {
                Rectangle b = hFC.getBoundsRelativeToViewport();
                x = b.x - rb.x;
                y = b.y - rb.y;
                //            gc.drawString("H", x + 12, y + 12);
                if (hMatch == LEADING_MATCH) {
                    if (h2Match == TRAILING_MATCH) {
                        x += b.width+cGapRt;
                    }
                } else if (hMatch == INDENT) {
                        x += cGapLt;
                } else if (hMatch == TRAILING_MATCH) {
                    if (h2Match == LEADING_MATCH)
                        x -= cGapLt;
                    else
                        x += b.width;
                }
//                System.out.println("DRAW LINES "+hFC+", "+x+", hMatch="+hMatch+", h2="+h2Match);
                if (y0 > y) {
                    drawLine(gc, origX, origY,x, y - OVERHANG, x, y0 + OVERHANG);
                } else {
                    drawLine(gc, origX, origY, x, y0 - OVERHANG, x, y + OVERHANG);
                }
            }
        }
        
        if(!vAlignIsRelative()) {
            if (vFC == null) {
                y = 100;
                if (vMatch == LEADING_MATCH) {
                    y = tb.y + (vContEdge ? 0 : CONT_GAP_NORTH);
                } else if (vMatch == TRAILING_MATCH) {
                    y = tb.y + tb.height - (vContEdge ? 0 : CONT_GAP_SOUTH);
                } else {
//                    System.out.println("REL MATCH BUT VMATCH WEIRD");
                }
                if(vMatch != 0)
                    drawLine(gc, origX, origY, tb.x, y, tb.x + tb.width, y);
            } else {
                Rectangle b = vFC.getBoundsRelativeToViewport();
                x = b.x - rb.x;
                y = b.y - rb.y;
                //            gc.drawString("V", x + 2, y + 2);
                if (vMatch == BASELINE) {
                    y += getBaseline(vFC);
                } else if (vMatch == LEADING_MATCH) {
                    if (v2Match == TRAILING_MATCH)
                        y += b.height+cGapBt;
                } else if (vMatch == TRAILING_MATCH) {
                    if (v2Match == LEADING_MATCH)
                        y -= cGapTp;
                    else
                        y += b.height;
                }
                
                if (x0 > x) {
                    drawLine(gc, origX, origY, x - OVERHANG, y, x0 + OVERHANG, y);
                } else {
                    drawLine(gc, origX, origY, x0 - OVERHANG, y, x + OVERHANG, y);
                }
            }
        }
    }
    
    /**
     * returns the gap between target and hFC (or vFC, depending on the value of
     * vertical). If these elements are not separated by exaclty one or no gap then returns
     * GAP_NOT_DIRECTLY_LINKED
     */
    private int getGapBetween(FormComponent selComp, boolean vertical, boolean before) {
        Component tarComp = selComp.getComponent();
        java.awt.Rectangle tarb = tarComp.getBounds();
        FormComponent relFC = (vertical ? vFC : hFC);
        int gap = GAP_NOT_DIRECTLY_LINKED;
        if(relFC == null) {
            if(vertical) {
                gap = before ? tarb.y : (tarComp.getParent().getBounds().height - tarb.y - tarb.height);
                if(gap < 0)
                    return GAP_NOT_DIRECTLY_LINKED;
                if((gap == CONT_GAP_NORTH && before) 
                        || (gap == CONT_GAP_SOUTH && !before))
                    return GAP_CONTAINER;
            } else {
                gap = before ? tarb.x : (tarComp.getParent().getBounds().width - tarb.x - tarb.width);
                if(gap < 0)
                    return GAP_NOT_DIRECTLY_LINKED;
                if((gap == CONT_GAP_WEST && before) 
                        || (gap == CONT_GAP_EAST && !before))
                    return GAP_CONTAINER;
            }
            return gap;
        }
        Component relComp = relFC.getComponent();
        java.awt.Rectangle relb = relComp.getBounds();
        if(vertical) {
            gap = before ? tarb.y - (relb.y+relb.height) : relb.y - (tarb.y + tarb.height);
            if(gap < 0)
                return GAP_NOT_DIRECTLY_LINKED;
            if(gap == cGapTp && before)
                return GAP_RELATED;
            if(gap == cGapBt && !before)
                return GAP_RELATED;
            return gap;
        } else {
            gap = before ? tarb.x - (relb.x+relb.width) : relb.x - (tarb.x + tarb.width);
            if(gap < 0)
                return GAP_NOT_DIRECTLY_LINKED;

            if(gap == cGapLt && before) {
                if(hMatch == INDENT)
                    return GAP_INDENT;
                return GAP_RELATED;
            }
            if(gap == cGapRt && !before)
                return GAP_RELATED;
            return gap;
        }
            
    }
    
    /**
     *  lgSel is a pasted group (when pasting multiple components - otherwise it is null
     */
    public boolean handleMouseUp(FormEditor ed, FormComponent selComp, 
            Vector selComps, LayoutGroup lgSel, boolean finalize) {
        
        try {
            if(!target.usesGroupLayout())
                return false;
            
            boolean vertical = true;
            boolean horizontal = true;
            if(lgSel != null) {
                if( lgSel.isVertical()) {
                    vertical = true;
                    horizontal = false;
                } else {
                    vertical = false;
                    horizontal = true;
                }
            }
            
            LayoutWrapper lw = target.getLayoutWrapper();

            LayoutGroup hg = lw.getHGroup();
            if(horizontal && (!isResize || insertX == 0)) {
                hg = hg.getCompactGroup(target.getChildren(), true, this, lgSel, selComp);
                lw.setHGroup(hg);
            } else {
                hg.repairSizes(selComps);
            }
            
            LayoutGroup vg = lw.getVGroup();
            if(vertical && (!isResize || insertY == 0)) {
                vg = vg.getCompactGroup(target.getChildren(), true, this, lgSel, selComp);
                lw.setVGroup(vg);
            } else {
                vg.repairSizes(selComps);
            }

            boolean updateCode = false;
            
            if(lgSel == null && finalize) {
            	finalizeChange(false);
            	updateCode = true;
            }
            
            if(selComp != null) {
    			vg = vg.getGroupContaining(selComp);
    			hg = hg.getGroupContaining(selComp);
    			if(isResize) {
    				if(vg != null) {
    					if((v2Match == LEADING_MATCH && vg.isAnchored(selComp, true))
    							|| (vMatch == LEADING_MATCH && vg.isAnchored(selComp, false))) {
    						vg.setExpand(selComp, true);
    						vg.getConstraint(selComp).setExpands(true);
    						updateCode = true;
    					}
    				}
    				if(hg != null) {
    					if((h2Match == LEADING_MATCH && hg.isAnchored(selComp, true))
    							|| (hMatch == LEADING_MATCH && hg.isAnchored(selComp, false))) {
    						hg.setExpand(selComp, true);
    						hg.getConstraint(selComp).setExpands(true);
    						updateCode = true;
    					}
    				}
            	} else {
            		if(vg != null) {
            			if(v2Match == LEADING_MATCH && vMatch == TRAILING_MATCH) {
            				vg.setAnchored(selComp, true, false, true);
    						vg.getConstraint(selComp).setAnchorLeading(false);
            				updateCode = true;
            			} else if(vMatch == LEADING_MATCH && v2Match == TRAILING_MATCH) {
            				vg.setAnchored(selComp, true, true, true);
    						vg.getConstraint(selComp).setAnchorLeading(true);
    						updateCode = true;
            			}
            		}
            		if(hg != null) {
            			if(h2Match == LEADING_MATCH && hMatch == TRAILING_MATCH) {
            				hg.setAnchored(selComp, true, false, true);
    						hg.getConstraint(selComp).setAnchorLeading(false);
    						updateCode = true;
            			} else if(hMatch == LEADING_MATCH && h2Match == TRAILING_MATCH) {
            				hg.setAnchored(selComp, true, true, true);
    						hg.getConstraint(selComp).setAnchorLeading(true);
    						updateCode = true;
            			}
            		}
            	}
            }
            
            if(updateCode) {
            	if(lw.isSet())
            		lw.updateGroupLayout(target.getEditor().getJavaCodeParser());
            }
            
        } catch (Throwable t) {
            t.printStackTrace();
            ed.setSnapTo(null);
            return false;
        }
        return true;
    }

    public void protectElements(Vector fcs) {
        LayoutWrapper lw = target.getLayoutWrapper();
        for(int i=0;i<fcs.size();i++) {
            FormComponent fc = (FormComponent) fcs.elementAt(i);
            lw.getHGroup().protectSize(fc);
            lw.getVGroup().protectSize(fc);
        }
    }
    /**
     * 
     */
    public void finalizeChange(boolean updateCode) {
        LayoutWrapper lw = target.getLayoutWrapper();
        lw.getHGroup().cleanup();
        lw.getVGroup().cleanup();
        
        lw.getHGroup().fixGroups();
        lw.getVGroup().fixGroups();
        
        lw.getHGroup().prepConstraints();
        lw.getVGroup().prepConstraints();
        
        lw.getHGroup().applyExpandConstraints();
        lw.getVGroup().applyExpandConstraints();
        
        lw.getHGroup().fixEndGaps();
        lw.getVGroup().fixEndGaps();
        
        lw.getVGroup().removePreferredSizesFromCode();
        lw.getHGroup().removePreferredSizesFromCode();
        
        lw.refreshGroupLayout();
        
        target.updateUIForAll();
        
        lw.getHGroup().applyAnchorConstraints();
        lw.getVGroup().applyAnchorConstraints();
        
        lw.getHGroup().clearAnchors();
        lw.getVGroup().clearAnchors();
        
    	//v4.0M3 take out these property setters (if they exist) when updating code
        //since they confuse things and the form will have been layed out properly
        lw.setPropertyValue("autocreateGaps", Boolean.FALSE, true);
        lw.setPropertyValue("autocreateContainerGaps", Boolean.FALSE, true);
        
        if(updateCode)
        	lw.updateGroupLayout(target.getEditor().getJavaCodeParser());
    }

    private int getPreferredGap(FormComponent selComp, FormComponent relFC, boolean vertical) {
        if(relFC == null || selComp == null)
            return 0;
        return LayoutStyle.getSharedInstance().getPreferredGap(
                (JComponent)selComp.getComponent(), 
                (JComponent)relFC.getComponent(),
                LayoutStyle.RELATED,
                vertical ? SwingConstants.NORTH : SwingConstants.WEST,
                (JComponent)target.getComponent());
    }
      
    public boolean hasNonRelHMatch() {
        return hMatch == LEADING_MATCH || hMatch == TRAILING_MATCH || hMatch == INDENT;
//        return hFC != null && hMatch != RELATIVE;
    }

    public boolean hasNonRelVMatch() {
        return vMatch == LEADING_MATCH || vMatch == TRAILING_MATCH || vMatch == BASELINE;
//        return vFC != null && vMatch != RELATIVE;
    }

    /**
     * Sets the x,y coordinates of the given Rectangle to be the top-left point of
     * the visual element
     */
    public void setOrigin(Rectangle b) {
	    b.x = xLeft+dx;
	    b.y = yTop+dy;
    }
    
}