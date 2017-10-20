/*
 * Created on Jul 28, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.editors;

import java.util.Vector;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HitResult {

	public FormComponent formComp;
	public FormComponent moveTarget;
	private int position;
	public static final int NORTH = 1;
	public static final int NORTH_EAST = 2;
	public static final int EAST = 3;
	public static final int SOUTH_EAST = 4;
	public static final int SOUTH = 5;
	public static final int SOUTH_WEST = 6;
	public static final int WEST = 7;
	public static final int NORTH_WEST = 8;
	public static final int CENTER = 9;

	private int x, y;

	public HitResult() {}

	public void dispose() {
		formComp = null;
		moveTarget = null;
	}
	
	public int getPosition() {
		return position;
	}

	public boolean isLeadingEdge() {
		return
		//position == NORTH || 
		position == NORTH_WEST || position == WEST || position == SOUTH_WEST;
	}

	public boolean isOnLeftRightEdge() {
		return position == NORTH_WEST
			|| position == WEST
			|| position == SOUTH_WEST
			|| position == SOUTH_EAST
			|| position == EAST
			|| position == NORTH_EAST;
	}

	public boolean isTrailingEdge() {
		return
		//position == SOUTH || 
		position == SOUTH_EAST || position == EAST || position == NORTH_EAST;
	}

	public void setResult(FormComponent fc, int x, int y, FormComponent moveTarget) {
		formComp = fc;
		this.moveTarget = moveTarget;
		Rectangle rec = fc.getBounds();
		int w = rec.width;
		int h = rec.height;
		//System.err.println("HitResult.setResult " + fc + ", " + x + ", " + y + ", " + w + ", " + h);
		//new Exception().printStackTrace();
		x -= rec.x;
		y -= rec.y;
		this.x = x;
		this.y = y;
		int vedge = h / 3;
//		if (vedge > 10)
//			vedge = 10;
		int hedge = w / 3;
//		if (hedge > 10)
//			hedge = 10;
		if (y < vedge) {
			if (x < hedge) {
				position = NORTH_WEST;
			} else if (x > w - hedge) {
				position = NORTH_EAST;
			} else {
				position = NORTH;
			}
		} else if (y > h - vedge) {
			if (x < hedge) {
				position = SOUTH_WEST;
			} else if (x > w - hedge) {
				position = SOUTH_EAST;
			} else {
				position = SOUTH;
			}
		} else {
			if (x < hedge) {
				position = WEST;
			} else if (x > w - hedge) {
				position = EAST;
			} else {
				position = CENTER;
			}
		}
	}

	public FormComponent getChildAfter() {
		Vector kids = formComp.getChildren();
		if (kids == null || kids.size() == 0)
			return null;
		Rectangle lb = null;
		FormComponent lk = null;
		FormComponent after = null;
		for (int i = 0; i < kids.size(); i++) {
			FormComponent k = formComp.getChildAt(i);
			Rectangle b = k.getBounds();
			if (i == kids.size() - 1) {
				//if this is the last child and mouse is to right and below it then
				//there is no "after" element
				if (x > b.x && y > b.y) {
					after = null;
					break;
				}
			}

			if (lk == null) {
				if (x < b.x + b.width && y <= b.y + b.height) {
					after = k;
					break;
				}
			} else {
				if (b.x > lb.x + lb.width) {
					//next child to right of previous one

					boolean ok = x > lb.x + lb.width / 2 && x <= b.x + b.width / 2;
					//System.out.println("LR- "+lk+", "+k+", "+ok+", y="+y +", b.y="+ b.y+", "+after);

					if (ok && ((y > lb.y && y < lb.y + lb.height) || (y > b.y && y < b.y + b.height))) {
						after = k;
						break;
					}

					if (after != null && ok) {
						ok = y > b.y;
					}

					if (ok)
						after = k;

				} else {
					//(assume) next child below previous one

					if (toRightOf(lb, x, y) || toLeftOf(b, x, y)) {
						after = k;
						break;
					}

					boolean ok = false;
					if (after == null) {
						ok = y > lb.y + lb.height / 2 && y <= b.y + lb.height / 2;
					} else {
						ok = y > lb.y + lb.height && y <= b.y + lb.height / 2;
					}
					if (ok && ((x > lb.x && x < lb.x + lb.width) || (x > b.x && x < b.x + b.width))) {
						after = k;
						break;
					}

					//if (after != null && ok)
					//ok = x > b.x;
					if (ok)
						after = k;

				}
			}
			lk = k;
			lb = b;
		}
		//System.out.println("GOT CHILD AFTER " + after + ", target=" + formComp);
		return after;
	}

	private boolean toLeftOf(Rectangle b, int x, int y) {
		return (x < b.x + b.width / 2 && y > b.y && y < b.y + b.height);
	}

	private boolean toRightOf(Rectangle b, int x, int y) {
		return (x > b.x + b.width / 2 && y > b.y && y < b.y + b.height);
	}

	private boolean toTopOf(Rectangle b, int x, int y) {
		return (y < b.y + b.height / 2 && x > b.x && x < b.x + b.width);
	}

	private boolean toBottomOf(Rectangle b, int x, int y) {
		return (y > b.y + b.height / 2 && x > b.x && x < b.x + b.width);
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String dirn = null;
        if(position == NORTH)
            dirn = "N";
        if(position == NORTH_EAST)
            dirn = "NE";
        if(position == NORTH_WEST)
            dirn = "NW";
        if(position == SOUTH)
            dirn = "S";
        if(position == SOUTH_EAST)
            dirn = "SE";
        if(position == SOUTH_WEST)
            dirn = "SW";
        if(position == EAST)
            dirn = "E";
        if(position == WEST)
            dirn = "W";
        return formComp.getName()+":"+dirn;
    }

    /**
     * @return
     */
    public boolean isNorthEdge() {
        return position == HitResult.NORTH 
        || position == HitResult.NORTH_EAST
        || position == HitResult.NORTH_WEST;
    }
    public boolean isSouthEdge() {
        return position == HitResult.SOUTH 
        || position == HitResult.SOUTH_EAST
        || position == HitResult.SOUTH_WEST;
    }
    public boolean isEastEdge() {
        return position == HitResult.EAST; 
    }
    public boolean isWestEdge() {
        return position == HitResult.WEST; 
    }
    
    public boolean equals(Object obj) {
        if(obj instanceof HitResult) {
            HitResult hr = (HitResult)obj;
            return 
            hr.position == position
            && formComp != null 
            && formComp.equals(hr.formComp)
            && moveTarget != null 
            && moveTarget.equals(hr.moveTarget);
        }
        return false;
    }

    /**
     * @return
     */
    public boolean isOnBorder() {
        return position != CENTER;
    }
}
