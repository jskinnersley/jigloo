/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.controls;

import org.eclipse.swt.widgets.Composite;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FocusableComposite extends Composite {

	public FocusableComposite(Composite parent, int style) {
		super(parent, style);
//		addKeyListener(new KeyListener() {
//
//			public void keyPressed(KeyEvent e) {
//				System.err.println("FocusableComposite: KEY PRESSED "+e);
//			}
//
//			public void keyReleased(KeyEvent e) {
//			}});
	}

	public boolean setFocus() {
		return super.setFocus();
		//System.out.println("FocusableComposite SET FOCUS "+this);
		//return super.forceFocus();
	}

}
