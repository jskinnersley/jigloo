package com.cloudgarden.jigloo.util;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Method;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.wrappers.JLayerWrapper;

public class SwingHelper {

	public static void addComponent(Component comp, Component parent) {
		try {
			if (!(parent instanceof Container))
				return;
			if (parent instanceof JInternalFrame) {
				((JInternalFrame) parent).getContentPane().add(comp);
			} else {
				((Container) parent).add(comp);
			}
		} catch(Throwable t) {
			jiglooPlugin.handleError(t);
		}
	}

	public static void addComponent(Component comp, Component parent, int findex) {
		int index = findex;
		if (!(parent instanceof Container))
			return;
		Container pc = (Container) parent;
		
		pc = SwingHelper.getContentPane(parent);
		
		if(parent instanceof JMenu)
			pc = ((JMenu)parent).getPopupMenu();
		
		if (index > pc.getComponentCount()) {
			System.err.println("Tried to add component " + comp + " to parent "
					+ parent + " at illegal position " + index);
			index = pc.getComponentCount();
		}
		pc.add(comp, index);
	}

	public static void addComponent(Component comp, Component fparent,
			Object constraint, int findex) {
		if (!(fparent instanceof Container))
			return;
		if(constraint instanceof JLayerWrapper) {
			constraint = ((JLayerWrapper)constraint).getValue();
		}
		Component parent = fparent;
		int index = findex;
		if (parent instanceof JInternalFrame) {
			((JInternalFrame) parent).getContentPane().add(comp, constraint,
					index);
		} else {
			Container parc = ((Container) parent);
			if(parent instanceof JMenu)
				parent = ((JMenu)parent).getPopupMenu();
			
			if(index > parc.getComponentCount())
				index = parc.getComponentCount();
			parc.add(comp, constraint, index);
		}
	}

	public static void addVectorBetween(Container parent, Component child,
	        Rectangle rec) {
	    if (parent == null || child == null || !parent.isAncestorOf(child))
	        return;
	    Component cp = child;
	    while (cp != null && cp.getParent() != null
	            && !cp.getParent().equals(parent)) {
	        cp = cp.getParent();
	        rec.x += cp.getLocation().x;
	        rec.y += cp.getLocation().y;
	    }
	}

	public static Container getContentPane(Component comp) {
	    
	    if (comp instanceof JInternalFrame)
	        return ((JInternalFrame) comp).getContentPane();
	    
	    if(comp == null)
	        return null;
	    try {
	        Class cls = comp.getClass();
	        Method gcp = cls.getMethod("getContentPane", null);
	        if(gcp != null) {
	            return (Container)gcp.invoke(comp, null);
	        }
	    } catch(Throwable t) {}
	    return (Container)comp;
	}

	public static void subtractVectorBetween(Container parent, Component child,
	        Rectangle rec) {
	
	    Component cp = child;
	    while (cp != null && cp.getParent() != null
	            && !parent.equals(cp.getParent())) {
	        cp = cp.getParent();
	        rec.x -= cp.getLocation().x;
	        rec.y -= cp.getLocation().y;
	    }
	}

}
