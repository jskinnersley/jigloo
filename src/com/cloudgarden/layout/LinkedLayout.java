/*
 * Created on Oct 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LinkedLayout implements LayoutManager2 {

	int preferredWidth, preferredHeight, minHeight, minWidth;
	int numChildren = -1;
	HashMap constraintMap = new HashMap();
	boolean invalid = true;

	public LinkedLayout() {
		super();
	}

	void initialize(Container parent) {
		Component[] children = parent.getComponents();
		if (!invalid)
			return;
		preferredWidth = 10000;
		preferredHeight = 10000;
		minWidth = 0;
		minHeight = 0;
		numChildren = children.length;
		Rectangle pb = parent.getBounds();
		for (int i = 0; i < children.length; i++) {
			Component cont = children[i];
			if (cont != null) {
				Object ld = constraintMap.get(cont);
				LinkedConstraint lc = null;
				if (ld instanceof LinkedConstraint) {
				    lc = (LinkedConstraint)ld;
				} else {
				    throw new RuntimeException(
				            "Constraint must be LinkedConstraint - insead it is "+ld+
				            " for child "+i+", "+cont);
				}
				Rectangle b = cont.getBounds();
				Dimension pref = cont.getPreferredSize();
				Dimension min = cont.getMaximumSize();
				if (pref == null)
					pref = cont.getSize();
				if (min == null)
					min = cont.getSize();
				int minX = b.x + b.width;
				int minY = b.y + b.height;
				int maxX = b.x + b.width;
				int maxY = b.y + b.height;
			}
		}
	}
				    
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    public void layoutContainer(Container parent) {
    }

	public void addLayoutComponent(String name, Component comp) {}

	public void removeLayoutComponent(Component comp) {
		constraintMap.remove(comp);
	}

	public Dimension preferredLayoutSize(Container parent) {
		initialize(parent);
		return new Dimension(preferredWidth, preferredHeight);
	}

	public Dimension minimumLayoutSize(Container parent) {
		initialize(parent);
		return new Dimension(minWidth, minHeight);
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		constraintMap.put(comp, constraints);
	}

	public Dimension maximumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	public void invalidateLayout(Container target) {
		invalid = true;
	}
}
