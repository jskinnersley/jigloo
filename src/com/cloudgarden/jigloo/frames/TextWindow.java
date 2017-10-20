package com.cloudgarden.jigloo.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.FormComponent;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class TextWindow extends org.eclipse.swt.widgets.Composite {
	private Text text1;
	private Composite composite1;
	Shell shell;
	KeyListener keyList;
	public TextWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	Text getText() {
		return text1;
	}

	String getResult() {
		return result;
	}

	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI(){
		try {
			preInitGUI();
			//Creating composite1
			composite1 = new Composite(this,SWT.NULL);
			//Creating text1
			text1 = new Text(composite1,SWT.NULL);
	
			//Setting properties for TextWindow
			this.setSize(new org.eclipse.swt.graphics.Point(93,23));
			final Color TextWindowbackground = new Color(Display.getDefault(),0,0,0);
			this.setBackground(TextWindowbackground);
	
			//Setting layout constraints for composite1
			GridData composite1LData = new GridData();
			composite1LData.verticalAlignment = GridData.FILL;
			composite1LData.horizontalAlignment = GridData.FILL;
			composite1LData.widthHint = -1;
			composite1LData.heightHint = -1;
			composite1LData.horizontalIndent = 0;
			composite1LData.horizontalSpan = 1;
			composite1LData.verticalSpan = 1;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1LData.grabExcessVerticalSpace = true;
			composite1.setLayoutData(composite1LData);
			//Setting properties for composite1
			final Color composite1background = new Color(Display.getDefault(),255,255,255);
			composite1.setBackground(composite1background);
	
			//Setting layout constraints for text1
			GridData text1LData = new GridData();
			text1LData.verticalAlignment = GridData.FILL;
			text1LData.horizontalAlignment = GridData.FILL;
			text1LData.widthHint = -1;
			text1LData.heightHint = -1;
			text1LData.horizontalIndent = 0;
			text1LData.horizontalSpan = 1;
			text1LData.verticalSpan = 1;
			text1LData.grabExcessHorizontalSpace = true;
			text1LData.grabExcessVerticalSpace = true;
			text1.setLayoutData(text1LData);
			//Setting properties for text1
			text1.setText("text1");
			text1.setSize(new org.eclipse.swt.graphics.Point(81,17));
			//Setting Grid layout for composite1
			GridLayout composite1Layout = new GridLayout(1, true);
			composite1.setLayout(composite1Layout);
			composite1Layout.marginWidth = 2;
			composite1Layout.marginHeight = 2;
			composite1Layout.numColumns = 1;
			composite1Layout.makeColumnsEqualWidth = true;
			composite1Layout.horizontalSpacing = 5;
			composite1Layout.verticalSpacing = 5;
			composite1.layout();
			//Setting Grid layout for this
			GridLayout thisLayout = new GridLayout(1, true);
			this.setLayout(thisLayout);
			thisLayout.marginWidth = 1;
			thisLayout.marginHeight = 1;
			thisLayout.numColumns = 1;
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.horizontalSpacing = 1;
			thisLayout.verticalSpacing = 1;
			this.layout();
			addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					TextWindowbackground.dispose();
					composite1background.dispose();
				}
			});
	
			postInitGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
			* Add your pre-init code in here
			*/
	public void preInitGUI() {}
	/**
			* Add your post-init code in here
			*/
	public void postInitGUI() {
		shell = getShell();
		addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				//System.err.println("TRAVERSED " + e.detail);
				//if (!e.doit)
				//return;
				if (!shell.isDisposed()) {
					if (e.detail == SWT.TRAVERSE_ESCAPE) {
						result = null;
					} else {
						result = text1.getText();
					}
					shell.dispose();
				}
			}
		});
		keyList = new KeyListener() {
			public void keyPressed(KeyEvent e) {
					//System.err.println("keyPressed");
	if (!shell.isDisposed() && !text1.isDisposed()) {
					result = text1.getText();
				}
			}
			public void keyReleased(KeyEvent e) {}
		};
		//getText().addKeyListener(keyList);
		getText().addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {}
			public void focusLost(FocusEvent e) {
				//System.err.println("FOCUS LOST");
				if (!shell.isDisposed()) {
					if (!text1.isDisposed()) {
						result = text1.getText();
						//getText().removeKeyListener(keyList);
					}
					shell.dispose();
				}
			}
		});

	}

	/** Auto-generated main method */
	public static void main(String[] args) {
		//showGUI();
		System.out.println(openWindow(null, "Test", null));
		System.exit(0);
	}

	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI(){
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			TextWindow inst = new TextWindow(shell, SWT.NULL);
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			Rectangle shellBounds = shell.computeTrim(0,0,93,23);
			shell.setSize(shellBounds.width, shellBounds.height);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	String result = "";

	public static String openWindow(
		Shell parent,
		String init,
		FormComponent fcomp) {

		TextWindow inst = null;
		int x = 0;
		int y = 0;
		if (fcomp != null) {
//			Point loc = parent.getLocation();
//			Rectangle rec = fcomp.getBoundsRelativeTo(parent);
//			x = loc.x + rec.x;
//			y = loc.y + rec.y + 22;
//			if (!jiglooPlugin.isMacOS()) {
//				x += 5;
//				y += 22;
//			}
			Rectangle b = fcomp.getBoundsRelativeToViewport();
			Point b1 = fcomp.getEditor().getViewportControl().toDisplay(0, 0);
			x = b1.x+b.x;
			y = b1.y+b.y;
		}
		try {
			Display display;
			Shell shell;
			if (parent == null) {
				display = new Display();
				shell = new Shell(display, SWT.NO_TRIM);
			} else {
				display = parent.getDisplay();
				shell = new Shell(parent, SWT.NO_TRIM);
			}
			inst = new TextWindow(shell, SWT.NULL);
			inst.getText().setText(init);
			inst.getText().setFocus();
			inst.getText().setSelection(0, init.length());
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			shell.pack();
			Point size = shell.getSize();
			if (size.x < 40)
				size.x = 40;
			shell.setSize(size);
			if (fcomp != null) {
				Rectangle b = fcomp.getBounds();
				if(b.x != 0) {
					x += (b.width - size.x) / 2;
					y += (b.height - size.y) / 2;
				}
			}
			shell.setLocation(x, y);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		if (inst == null)
			return null;
		return inst.getResult();
	}
}
