/*
 * Created on May 21, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.FocusTraversalPolicy;
import java.util.Locale;

import javax.swing.KeyStroke;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Control;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.eval.ArrayHolder;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WrapperFactory {

	public static Object createWrapper(Object name, Object obj, FormComponent comp) {
		try {
			if (obj == null)
				return null;
			Object w = null;
			if (jiglooPlugin.canUseSwing()) {
				
				if (obj instanceof java.awt.Color) {
					w = new ColorWrapper((java.awt.Color) obj, comp);
				
				} else if (obj instanceof java.awt.Font) {
					w = new FontWrapper((java.awt.Font) obj, comp);
				
				} else if (obj instanceof java.awt.Image) {
					w = new ImageWrapper((java.awt.Image) obj, comp);
				
				} else if (obj instanceof javax.swing.Icon) {
					w = new IconWrapper((javax.swing.Icon) obj, comp);
					
				} else if (obj instanceof java.awt.Cursor) {
					w = new AWTCursorWrapper((java.awt.Cursor) obj, comp);
					
	            } else if ("mnemonic".equals(name)) {
	                if(obj instanceof Integer)
	                    w = new MnemonicWrapper((Integer) obj, comp);
	
	            } else if (obj instanceof KeyStroke) {
	                w = new KeyStrokeWrapper((KeyStroke) obj, comp);

				} else if (jiglooPlugin.getJavaVersion() >= 14
				        && obj instanceof FocusTraversalPolicy) {
					w = new FocusTraversalPolicyWrapper((FocusTraversalPolicy) obj, comp);
				}
	            
			}
			
			if(comp != null && obj != null && name.equals("control")) {
				//wrap a Control in a FormComponentWrapper as part of a FormAttachment
				//for a FormData
				FormComponent fcRel = comp.getEditor().getComponentWithObject(obj);
				if(fcRel != null) {
					System.out.println("WrapperFactory creating FormComponentWrapper for FC "+fcRel+", for "+comp+"."+name);
					w = new FormComponentWrapper(fcRel);
				}
			}
			
			if (obj instanceof FormAttachment) {
				w = new LayoutDataWrapper(obj, comp);
			
			} else if (obj instanceof Color) {
				w = new ColorWrapper((Color) obj, comp);
				
			} else if (obj instanceof Cursor) {
				w = new SWTCursorWrapper((Cursor) obj, comp);
				
			} else if (obj instanceof Font) {
				w = new FontWrapper((Font) obj, comp);
			
			} else if (obj instanceof Image) {
				w = new ImageWrapper((Image) obj, comp);
			
			} else if (obj instanceof Locale) {
				w = new LocaleWrapper((Locale) obj, comp);
			
			} else if (obj instanceof double[]) {
				w = new DoubleArrayWrapper((double[]) obj, comp);
			
			} else if (obj instanceof String[]) {
				w = new StringArrayWrapper((String[]) obj, comp);
			
			} else if (obj instanceof int[]) {
				w = new IntegerArrayWrapper((int[]) obj, comp);

			} else if (obj instanceof Control[]) {
				w = new FormComponentArrayWrapper((Control[]) obj, comp);
				
			} else if (obj instanceof ArrayHolder) {
				FormComponent[] array = ((ArrayHolder)obj).getFCArray();
				w = new FormComponentArrayWrapper(array, comp);			        
			}
			return w;
		} catch (Exception e) {
			jiglooPlugin.handleError(e);
		}
		return null;
	}

}
