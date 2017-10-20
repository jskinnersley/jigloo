package com.cloudgarden.jigloo.dialog;

import java.util.Vector;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class AnchorPanel extends org.eclipse.swt.widgets.Composite {

	boolean multiSel = false;
	FormComponent selComp;
	FormEditor editor;
	
	public AnchorPanel(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI() {
		try {
            {
                
            }
			preInitGUI();
			this.setSize(180, 155);

			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
            this.setBackground(ColorManager.getColor(255, 255, 255));
            {
                commentArea = new Text(this, SWT.WRAP | SWT.BORDER);
                FormData commentAreaLData = new FormData();
                commentAreaLData.height = 48;
                commentAreaLData.width = 167;
                commentAreaLData.left =  new FormAttachment(0, 1000, 0);
                commentAreaLData.top =  new FormAttachment(0, 1000, 100);
                commentArea.setLayoutData(commentAreaLData);
                commentArea
                    .setText("Anchors this control to the right side of the container");
            }
            {
                leftButton = new AnchorButton(this, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                leftButton.setSide("left");
                FormData leftButtonLData = new FormData();
                leftButtonLData.height = 30;
                leftButtonLData.width = 30;
                leftButtonLData.left =  new FormAttachment(0, 1000, 20);
                leftButtonLData.top =  new FormAttachment(0, 1000, 35);
                leftButton.setLayoutData(leftButtonLData);
            }
            {
                rightButton = new AnchorButton(this, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                rightButton.setSide("right");
                FormData rightButtonLData = new FormData();
                rightButtonLData.height = 30;
                rightButtonLData.width = 30;
                rightButtonLData.left =  new FormAttachment(0, 1000, 130);
                rightButtonLData.top =  new FormAttachment(0, 1000, 35);
                rightButton.setLayoutData(rightButtonLData);
            }
            {
                topButton = new AnchorButton(this, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                topButton.setSide("top");
                FormData topButtonLData = new FormData();
                topButtonLData.height = 30;
                topButtonLData.width = 30;
                topButtonLData.left =  new FormAttachment(0, 1000, 75);
                topButtonLData.top =  new FormAttachment(0, 1000, 10);
                topButton.setLayoutData(topButtonLData);
            }
            {
                bottomButton = new AnchorButton(this, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                bottomButton.setSide("bottom");
                FormData bottomButtonLData = new FormData();
                bottomButtonLData.height = 30;
                bottomButtonLData.width = 30;
                bottomButtonLData.left =  new FormAttachment(0, 1000, 75);
                bottomButtonLData.top =  new FormAttachment(0, 1000, 60);
                bottomButton.setLayoutData(bottomButtonLData);
            }
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
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
								b	* Add your post-init code in here
								*/
	public void postInitGUI() {
		topButton.setSide("top");
		bottomButton.setSide("bottom");
		leftButton.setSide("left");
		rightButton.setSide("right");
		mtl = new MouseTrackAdapter() {
			public void mouseEnter(MouseEvent e) {
				if (e.getSource() instanceof AnchorButton) {
					AnchorButton butt = (AnchorButton) e.getSource();
					String side = butt.getSide();
					commentArea.setText(getCommentText(side, butt.getState()));
				} else {
					commentArea.setText("");
				}
			}
		};
		ml = new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {
				AnchorButton butt = (AnchorButton) e.getSource();
				String side = butt.getSide();
				commentArea.setText(getCommentText(side, butt.getState()));
				okButtonWidgetSelected(null);
			}
			public void mouseUp(MouseEvent e) {}
		};

		topButton.addMouseTrackListener(mtl);
		leftButton.addMouseTrackListener(mtl);
		rightButton.addMouseTrackListener(mtl);
		bottomButton.addMouseTrackListener(mtl);
		topButton.addMouseListener(ml);
		leftButton.addMouseListener(ml);
		rightButton.addMouseListener(ml);
		bottomButton.addMouseListener(ml);
	}

	private MouseTrackListener mtl;
	private MouseListener ml;

	private String getCommentText(String side, int type) {
		String ctrlStr = "control is";
		if (multiSel)
			ctrlStr = "controls are";
		if (type == AnchorButton.ABS_ANCHOR) {
			return "The "
				+ side
				+ " side of the "
				+ ctrlStr
				+ " anchored at a fixed distance from "
				+ side
				+ " of container";
		} else if (type == AnchorButton.REL_ANCHOR) {
			String dirn = "horizontally";
			if (side.equals("top") || side.equals("bottom")) {
				dirn = "verticaly";
			}
			return "The " + side + " side of the " + ctrlStr + " anchored at a fixed relative position " + dirn;
		} else {
			return "The " + side + " side of the " + ctrlStr + " not anchored";
		}
	}

	private ISelectionChangedListener selList = new ISelectionChangedListener() {
        public void selectionChanged(SelectionChangedEvent event) {
            init(editor);
        }
    };

	public void init(FormEditor editor) {
	    this.editor = editor;
		multiSel = editor.getSelectedComponents().size() > 1;
		if(selComp != null && selComp.equals(editor.getSelectedComponent()))
		        return;
		selComp = editor.getSelectedComponent();
		if(selComp == null)
		    return;
		getShell().setText(selComp.getNameInCode());
		setButtonState(selComp, 0, topButton);
		setButtonState(selComp, 1, rightButton);
		setButtonState(selComp, 2, bottomButton);
		setButtonState(selComp, 3, leftButton);
		editor.removeSelectionChangedListener(selList);
		editor.addSelectionChangedListener(selList);
	}

    private AnchorButton bottomButton;
    private AnchorButton topButton;
    private AnchorButton rightButton;
    private AnchorButton leftButton;
    private Text commentArea;

    private int getAnchor(FormComponent comp, int side) {
		if (comp.isSideAnchored(side, true)) {
			return AnchorButton.ABS_ANCHOR;
		} else if (comp.isSideAnchored(side, false)) {
			return AnchorButton.REL_ANCHOR;
		}
		return AnchorButton.NO_ANCHOR;
    }
    
	private void setButtonState(FormComponent comp, int side, AnchorButton button) {
		int state = getAnchor(comp, side);
		button.setState(state);
	}

	/** Auto-generated main method */
	public static void main(String[] args) {
		showGUI();
	}

	/**
		* Auto-generated code - any changes you make will disappear!!!
		* This static method creates a new instance of this class and shows
		* it inside a Shell.
		*/
	public static void showGUI() {
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			AnchorPanel inst = new AnchorPanel(shell, SWT.NULL);
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			Rectangle shellBounds = shell.computeTrim(0, 0, 190, 230);
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
	/** Auto-generated event handler method */
	protected void okButtonWidgetSelected(SelectionEvent evt) {
		Vector sels = editor.getSelectedComponents();
		for (int i = 0; i < sels.size(); i++) {
			FormComponent fc = (FormComponent) sels.elementAt(i);
			handleChange(0, "top", topButton, fc);
			handleChange(1, "right", rightButton, fc);
			handleChange(2, "bottom", bottomButton, fc);
			handleChange(3, "left", leftButton, fc);

		}
		if (sels.size() != 0)
			editor.setDirtyAndUpdate();
//		getShell().dispose();
	}

	private void handleChange(int side, String name, AnchorButton but, FormComponent fc) {
		boolean anch = but.isAnchored();
		boolean abs = but.isAbsolute();
		if( but.getState() == getAnchor(fc, side))
			return;
		fc.anchorSide(side, anch, abs);
		if(fc.isSwing()) {
			//for AnchorLayout
			fc.repairParentConnectionNode();
		} else {
			//for FormLayout
			fc.getLayoutDataWrapper().updateInCode(name);
		}
	}

    public void dispose() {
        if(!isDisposed())
            super.dispose();
        if(editor != null)
            editor.removeSelectionChangedListener(selList);
        editor = null;
    }

}
