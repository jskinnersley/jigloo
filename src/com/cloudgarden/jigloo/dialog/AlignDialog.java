package com.cloudgarden.jigloo.dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;



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
public class AlignDialog extends org.eclipse.swt.widgets.Dialog {

    private Shell dialogShell;
    private AlignComposite alignComposite1;

    /**
    * Auto-generated main method to display this 
    * org.eclipse.swt.widgets.Dialog inside a new Shell.
    */
    public static void main(String[] args) {
        try {
            Display display = Display.getDefault();
            Shell shell = new Shell(display);
            AlignDialog inst = new AlignDialog(shell, SWT.NULL);
            inst.open(100,100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlignDialog(Shell parent, int style) {
        super(parent, style);
    }

    public AlignComposite getAlignComp() {
        return alignComposite1;
    }
    
    public Shell getShell() {
        return dialogShell;
    }
    
	public void dispose() {
	    if(!dialogShell.isDisposed())
	        dialogShell.dispose();
        alignComposite1.dispose();
	}

    public void open(int x, int y) {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM);
            dialogShell.moveAbove(null);

            GridLayout gl =new GridLayout();
            gl.marginWidth = 0;
            gl.marginHeight = 0;
            dialogShell.setLayout(gl);
            dialogShell.setText("Alignment Dialog");
            {
                alignComposite1 = new AlignComposite(dialogShell, SWT.NONE);
                GridData alignComposite1LData = new GridData();
                alignComposite1LData.grabExcessHorizontalSpace = true;
                alignComposite1LData.horizontalAlignment = GridData.FILL;
                alignComposite1LData.grabExcessVerticalSpace = true;
                alignComposite1LData.verticalAlignment = GridData.FILL;
                alignComposite1.setLayoutData(alignComposite1LData);
            }
            dialogShell.layout();
            dialogShell.pack();
            Point sz = dialogShell.getSize();
            Rectangle db = Display.getDefault().getBounds();
            if(x+sz.x > db.width)
                x = db.width - sz.x;
            if(y+sz.y > db.height)
                y = db.height - sz.y;
            dialogShell.setLocation(x, y);
            dialogShell.open();
            Display display = Display.getDefault();
            dialogShell.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    alignComposite1.dispose();
                }
            });
            
//			while (!dialogShell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
//		    if(!alignComposite1.isDisposed())
//		        alignComposite1.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
