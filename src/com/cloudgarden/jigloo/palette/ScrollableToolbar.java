package com.cloudgarden.jigloo.palette;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.actions.ImageAction;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.util.Timer;
import com.cloudgarden.jigloo.util.TimerListener;
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
public class ScrollableToolbar extends org.eclipse.swt.widgets.Composite {

	private CButton leftArrow;
	private CButton rightArrow;
	private OrderableComposite toolbar;
	private Composite composite1;
	private int actionCnt = 0;
	private final int minScrollInterval = 5;
	private final int maxScrollInterval = 30;
	private final int timeInterval = 30;
	private int scrollInterval = 5;
	
	private TimerListener leftScroller = new TimerListener(timeInterval) {
		public void doAction() {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					scrollLeft();
					if(scrollInterval < maxScrollInterval)
						scrollInterval += 1;
				};
			});
		}
	};
	private TimerListener rightScroller = new TimerListener(timeInterval) {
		public void doAction() {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					scrollRight();
					if(scrollInterval < maxScrollInterval)
						scrollInterval += 1;
				};
			});
		}
	};

	public ScrollableToolbar(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	public void clear() {
		toolbar.clearAll();
	}

	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI() {
		try {
			preInitGUI();
//			this.setSize(new org.eclipse.swt.graphics.Point(329, 25));
			final org.eclipse.swt.graphics.Image leftArrowimage = ImageManager.getImage("left_arrow.gif");
			final org.eclipse.swt.graphics.Image rightArrowimage = ImageManager.getImage("right_arrow.gif");
			GridLayout thisLayout = new GridLayout(3, true);
			this.setLayout(thisLayout);
            {
                leftArrow = new CButton(this, SWT.NONE);
                leftArrow.setImage(leftArrowimage);
//                leftArrow.setSize(new org.eclipse.swt.graphics.Point(14, 25));
                GridData leftArrowLData = new GridData();
                leftArrow.addMouseListener(new MouseAdapter() {
                    public void mouseDown(MouseEvent evt) {
                        leftArrowMouseDown(evt);
                    }
                    public void mouseUp(MouseEvent evt) {
                        leftArrowMouseUp(evt);
                    }
                });
                leftArrow.setLayout(null);
                leftArrowLData.verticalAlignment = GridData.FILL;
                leftArrowLData.horizontalAlignment = GridData.BEGINNING;
                leftArrowLData.widthHint = 14;
                leftArrowLData.heightHint = -1;
                leftArrowLData.horizontalIndent = 0;
                leftArrowLData.horizontalSpan = 1;
                leftArrowLData.verticalSpan = 1;
                leftArrowLData.grabExcessHorizontalSpace = false;
                leftArrowLData.grabExcessVerticalSpace = true;
                leftArrow.setLayoutData(leftArrowLData);
            }
            {
                composite1 = new Composite(this, SWT.NONE);
//                composite1.setSize(new org.eclipse.swt.graphics.Point(301, 25));
                GridData composite1LData = new GridData();
                composite1LData.verticalAlignment = GridData.FILL;
                composite1LData.horizontalAlignment = GridData.FILL;
                composite1LData.widthHint = -1;
//                composite1LData.heightHint = 25;
                composite1LData.horizontalIndent = 0;
//                composite1LData.verticalIndent = 0;
                composite1LData.horizontalSpan = 1;
                composite1LData.verticalSpan = 1;
                composite1LData.grabExcessHorizontalSpace = true;
                composite1LData.grabExcessVerticalSpace = true;
                composite1.setLayoutData(composite1LData);
                {
                    toolbar = new OrderableComposite(composite1, SWT.NONE);
//                    toolbar.setSize(new org.eclipse.swt.graphics.Point(7, 25));
                    RowLayout toolbarLayout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
                    toolbarLayout.spacing = 0;
                    toolbarLayout.wrap = false;
                    toolbarLayout.marginLeft = 0;
                    toolbarLayout.marginTop = 0;
                    toolbarLayout.marginRight = 0;
                    toolbarLayout.marginBottom = 0;
                    toolbar.setLayout(toolbarLayout);
                    toolbar.clearAll();
                    toolbar.layout();
                }
            }
            {
                rightArrow = new CButton(this, SWT.NONE);
                rightArrow.setImage(rightArrowimage);
//                rightArrow.setSize(new org.eclipse.swt.graphics.Point(14, 25));
                GridData rightArrowLData = new GridData();
                rightArrow.addMouseListener(new MouseAdapter() {
                    public void mouseDown(MouseEvent evt) {
                        rightArrowMouseDown(evt);
                    }
                    public void mouseUp(MouseEvent evt) {
                        rightArrowMouseUp(evt);
                    }
                });
                rightArrow.setLayout(null);
                rightArrowLData.verticalAlignment = GridData.FILL;
                rightArrowLData.horizontalAlignment = GridData.BEGINNING;
                rightArrowLData.widthHint = 14;
                rightArrowLData.heightHint = -1;
                rightArrowLData.horizontalIndent = 0;
                rightArrowLData.horizontalSpan = 1;
                rightArrowLData.verticalSpan = 1;
                rightArrowLData.grabExcessHorizontalSpace = false;
                rightArrowLData.grabExcessVerticalSpace = true;
                rightArrow.setLayoutData(rightArrowLData);
            }
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.numColumns = 3;
			thisLayout.makeColumnsEqualWidth = false;
			thisLayout.horizontalSpacing = 0;
			thisLayout.verticalSpacing = 0;
			this.layout();

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
	public void postInitGUI() {}

	/** Auto-generated main method */
	public static void main(String[] args) {
		showGUI();
	}
//	$hide>>$

	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI() {
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			ScrollableToolbar inst = new ScrollableToolbar(shell, SWT.NULL);
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			Rectangle shellBounds = shell.computeTrim(0, 0, 329, 25);
			shell.setSize(shellBounds.width, shellBounds.height);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void leftArrowMouseDown(MouseEvent evt) {
		Timer.addListener(rightScroller);
	}

	protected void leftArrowMouseUp(MouseEvent evt) {
		Timer.removeListener(rightScroller);
		scrollInterval = minScrollInterval;
	}

	protected void rightArrowMouseDown(MouseEvent evt) {
		Timer.addListener(leftScroller);
	}
	
	protected void rightArrowMouseUp(MouseEvent evt) {
		Timer.removeListener(leftScroller);
		scrollInterval = minScrollInterval;
	}

	public CButton addAction(Action action, boolean radio) {
	    actionCnt++;
		int style = SWT.PUSH;
		if (radio)
			style = SWT.RADIO;
		final CButton cb = new CButton(toolbar, style);
		if (action instanceof ImageAction) {
			cb.setImage(((ImageAction) action).getImage());
			action.addPropertyChangeListener(new IPropertyChangeListener() {
			    public void propertyChange(PropertyChangeEvent event) {
			        if("image".equals(event.getProperty()) && event.getNewValue() != null) {
			            final Image img = (Image)event.getNewValue();
//				        System.err.println("PROP CHANGE DETECTED "+event.getNewValue());
			            Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                if(!img.isDisposed() && !cb.isDisposed()) {
                                    cb.setImage(img);
                                    updateUI();
                                }
                            }
			            });
			        }
			    }});
		} else {
			cb.setImage(ImageManager.getImage(action.getImageDescriptor()));
		}
		cb.setToolTipText(action.getText());
		return cb;
	}

	public void updateUI() {
		toolbar.pack(true);
		Point sz;
		if(actionCnt == 0)
		    sz = toolbar.computeSize(-1, 23);
		else
		    sz = toolbar.computeSize(-1, -1);
		toolbar.setSize(sz);
	}

	public void scrollLeft() {
		Rectangle b = toolbar.getParent().getBounds();
		Rectangle tbb = toolbar.getBounds();
		int x = tbb.x - scrollInterval;
		if (x + tbb.width < b.width) {
			if (tbb.width > b.width)
				x = b.width - tbb.width;
			else
				x = 0;
		}
		toolbar.setLocation(x, 0);
	}

	public void scrollRight() {
		Rectangle b = toolbar.getParent().getBounds();
		Rectangle tbb = toolbar.getBounds();
		int x = tbb.x + scrollInterval;
		if (x > 0)
			x = 0;
		toolbar.setLocation(x, 0);
	}

}
//$hide<<$
