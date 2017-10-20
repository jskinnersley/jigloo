/*
 * Created on Jun 18, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.controls;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JWindow;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.draw.DrawUtils;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.editors.HitResult;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.groupLayoutSupport.SnapTo;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.properties.sources.SizePropertySource;
import com.cloudgarden.jigloo.util.DelayableRunnable;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AWTControl extends Composite {

    private final boolean SHOW_FRAME = false;
    private int PAINT_WAIT_TIME = 1000;
    
    private Window frame = new JWindow();
//    private Window frame = new JFrame();

    private Component awtComp;
    private BufferedImage bim;
    private int[] pix;
    private PaletteData palData = new PaletteData(0xFF0000, 0xFF00, 0xFF);
    private Graphics2D g2;
    private PixelGrabber pg;
    private ImageData imd;
    private Image im;
    private FormComponent formComponent;
    private int width;
    private int height;
    private boolean needsRepaint = false;
    private PaintListener paintList;
    private FormComponent toBeLayedOut;
    private boolean redrawGridOnly = false;
    private FormComponent selectedComp = null;
    private HitResult lastHit;
    private FormEditor editor;
    
    public static void main(String[] args) {
        Display display = null;
        try {
            JFrame frame = new JFrame();
            display = new Display();
            Shell shell = new Shell(display);
            FillLayout fillLayout = new FillLayout();
            shell.setLayout(fillLayout);
            JButton butt = new JButton("HELLO");
            frame.getContentPane().add(butt);
            frame.pack();
            frame.show();
            AWTControl comp = new AWTControl(shell, SWT.NULL, null);
            comp.setComponent(butt);
            shell.pack();
            shell.open();
            comp.createImage();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            frame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            display.dispose();
        }
    }

    public AWTControl(Composite parent, int style, FormComponent formComponent) {
        super(parent, SWT.NO_MERGE_PAINTS | SWT.NO_BACKGROUND);
        if(MAX_HEIGHT == 0) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            MAX_WIDTH=jiglooPlugin.getMaxFormWidth();
            MAX_HEIGHT=jiglooPlugin.getMaxFormHeight();
        }
        this.formComponent = formComponent;
        this.editor = formComponent.getEditor();
        paintList = new PaintListener() {
            public void paintControl(PaintEvent e) {
                try {
                    paintSwingComponent(e);
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        };
        addPaintListener(paintList);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                doDispose();
            }
        });
    }

    private Throwable currThrowable = null;

    private final DelayableRunnable errorDrun = new DelayableRunnable(1000, true) {
        public void run() {
            if(currThrowable == null)
                return;
        	jiglooPlugin.handleError(getShell(), "Error painting Form Editor",
                    "Error painting Form Editor", currThrowable);
            currThrowable = null;
            MessageDialog.openError(getShell(), "Error painting Form Editor", 
                    "There was an error rendering the Form Editor - this can sometimes be caused" +
                    " by problems with the Look and Feel, so changing the Look and Feel can sometimes help with this problem." +
                    "\n\nYou should also make sure that custom components" +
                    " cannot throw errors in their paint methods." +
                    "\n\n Please click on the " +
                    "\"View Log\" button for more information");
        }
    };

    public int MAX_WIDTH = 0;
    public int MAX_HEIGHT = 0;
    private boolean newImageCreated = false;
    
    public void createImage() {
        try {
//    		System.out.println("AWTControl.createImage");
            Component drawable = getComponent();
            
            if (drawable == null)
                return;
            int w = drawable.getWidth();
            int h = drawable.getHeight();

            if (w < 10)
                w = 10;
            if (h < 10)
                h = 10;
            if(w > MAX_WIDTH)
                w = MAX_WIDTH;
            if(h > MAX_HEIGHT)
                h = MAX_HEIGHT;
            
//            jiglooPlugin.writeToLog("create image, w,h = "+w+", "+h);
            if (w == 0 || h == 0)
                return;
            if (needsRepaint || im == null || w != width || h != height) {
                needsRepaint = false;
                Dimension dim = getComponent().getSize();
                w = dim.width;
                h = dim.height;
                if (w < 10)
                    w = 10;
                if (h < 10)
                    h = 10;
                if(w > MAX_WIDTH)
                    w = MAX_WIDTH;
                if(h > MAX_HEIGHT)
                    h = MAX_HEIGHT;

                setSize(w, h);
                getParent().setSize(w, h);
                
                try {
                    getComponent().validate();
                } catch (IllegalStateException isex) {
                    jiglooPlugin.handleError(isex);
                } catch (Exception ex) {
                    jiglooPlugin.handleError(ex);
                }
                if (width != w || height != h) {
                    bim = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    imd = new ImageData(w, h, 24, palData);
                    pix = new int[w * h];
                    if (g2 != null)
                        g2.dispose();
                    g2 = null;
                    g2 = bim.createGraphics();
                }
                width = w;
                height = h;
                try {
                    drawable.paint(g2);
                } catch (Exception e1) {
                    if (currThrowable == null)
                        currThrowable = e1;
                    errorDrun.trigger();
                    jiglooPlugin.handleError(e1);
                }
                pg = new PixelGrabber(bim, 0, 0, w, h, pix, 0, w);
                boolean grabbed = pg.grabPixels(PAINT_WAIT_TIME);
                if(!grabbed) {
                    jiglooPlugin.writeToLog("ERROR: Failed grabbing bits from AWT image!!!");
                }
                for (int i = 0; i < height; i++) {
                    imd.setPixels(0, i, width, pix, i * width);
                }
                if (im != null && !im.isDisposed())
                    im.dispose();
                im = new Image(getDisplay(), imd);
                
                if(backImg != null)
                    backImg.dispose();
                backImg = new Image(Display.getDefault(), im, SWT.IMAGE_COPY);
            }
            
            newImageCreated = true;
            redraw();
        } catch (Throwable ex) {
            jiglooPlugin.handleError(ex);
        }
    }

    private void paintSwingComponent(PaintEvent e) {
        if (im == null)
            return;
//        System.out.println("REPAINT AWTC "+e.x+", "+e.y+", "+e.width+", "+e.height+
//                ", "+selectedComp+", "+toBeLayedOut);
        
//        jiglooPlugin.writeToLog("paintSwingComponent, e.w, e.h="+e.width+", "+e.height);
        if(e.width == 0 || e.height == 0)
            return;
        
        SnapTo snapTo = editor.getSnapTo();
        boolean drawGrid = false;
        
        if(newImageCreated) {
            drawGrid = true;
            newImageCreated = false;
        } else if(snapTo == null) {
            FormComponent sel = editor.getSelectedComponent();
            if(selectedComp == null || !selectedComp.equals(sel)) {
                drawGrid = true;
            }
            selectedComp = sel;
        }
        
        if(drawGrid)
            addGridToImage();
        
        HitResult hit = editor.getHitResult();
        
        if(editor.getMoveTarget() != null 
                && PaneLayoutHandler.handlesLayout(editor.getMoveTarget())
                && (editor.isMouseDragged() || editor.getCurrentAction() != null)
                && (hit == null || !hit.equals(lastHit)) ) {
            lastHit = hit;
            if(im != null)
                im.dispose();
            im = new Image(Display.getDefault(), backImg, SWT.IMAGE_COPY);
            GC gc = new GC(im);
            PaneLayoutHandler.draw(gc, editor.getSelectedComponent(), hit);
            gc.dispose();
        }
        
        
        Rectangle imb = im.getBounds();
        if (width > imb.width)
            width = imb.width;
        if (height > imb.height)
            height = imb.height;

        if (e.x + e.width > width)
            e.width = width - e.x;
        if (e.y + e.height > height)
            e.height = height - e.y;
        if (e.width < 0)
            e.width = 0;
        if (e.height < 0)
            e.height = 0;
        e.gc.drawImage(im, e.x, e.y, e.width, e.height, e.x, e.y, e.width, e.height);

        if(snapTo != null) {
            snapTo.drawLines(e.gc, 0, 0);
            selectedComp = null;
        }
//        System.out.println("PAINT AWT FINISHED "+drawGrid);
    }

    private Image backImg = null;
    
    public void addGridToImage() {
        if(im == null)
            return;
        
        FormComponent sel = editor.getSelectedComponent();
//      System.out.println("ADD GRID TO IMAGE sel="+sel+", TBLO="+toBeLayedOut);

        if(im != null)
            im.dispose();
        
        im = new Image(Display.getDefault(), backImg, SWT.IMAGE_COPY);

        GC gc = null;
        
        if(sel != null && sel.getParent() != null && sel.getParent().usesGroupLayout()) {
        	if(gc == null)
        		gc = new GC(im);
            drawGroupLayoutLines(gc, sel.getParent(), sel);
        }

        if (toBeLayedOut != null && toBeLayedOut.getComponent() != null) {
        	if(gc == null)
        		gc = new GC(im);
        	DrawUtils.drawGrid(gc, toBeLayedOut, 0, 0);
        	editor.getGridEdgeManager().redrawMarkers();
        }

        if(gc != null)
        	gc.dispose();
    }
    
    /**
     * Draw GroupLayout lines on the given GC
     * @param gc
     * @param moveTarget
     * @param sel
     * @return
     */
    private boolean drawGroupLayoutLines(GC gc, 
            FormComponent moveTarget, FormComponent sel) {
        boolean drawn = false;
        try {
            if(moveTarget == null && sel != null)
                moveTarget = sel.getParent();
            
            if(sel !=null && !moveTarget.getEditor().isMouseDragged()) {
                LayoutWrapper lw = moveTarget.getLayoutWrapper();
                LayoutGroup grp = lw.getVGroup();
                if(grp != null) {
                    grp = grp.getGroupContaining(sel);
                    if(grp != null) {
                        grp.drawGroup(gc, sel);
                        drawn = true;
                    }
                }
                
                grp = lw.getHGroup();
                if(grp != null) {
                    grp = grp.getGroupContaining(sel);
                    if(grp != null) {
                        grp.drawGroup(gc, sel);
                        drawn = true;
                    }
                }
            }        
        } catch(IllegalStateException ise) {
            System.err.println("Error in drawGroupLayoutLines: "+ise);
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return drawn;
    }
    
    public void handleSelectionChanged(FormComponent fc) {
        if(fc != null && fc.needsLayoutGrid()) {
            setComponentToBeLayedOut(fc);
        } else {
            if(fc != null && fc.getParent() != null && fc.getParent().needsLayoutGrid())
                setComponentToBeLayedOut(fc.getParent());
            else
                setComponentToBeLayedOut(null);
        }
        editor.getGridEdgeManager().clearGreenMarker();
    }
    
    public void setComponentToBeLayedOut(FormComponent fc) {
    	selectedComp = null;
        boolean redraw = false;
        if ((fc == null && toBeLayedOut != null)
                || (fc != null && !fc.equals(toBeLayedOut))) {
            redraw = true;
        }
        toBeLayedOut = fc;
        if (redraw) {
        	redraw();
        }
    }

    public Component getComponent() {
        if (frame instanceof JWindow)
            return ((JWindow) frame).getComponent(0);
        if (frame instanceof JFrame)
            return ((JFrame) frame).getComponent(0);
        if (frame instanceof JDialog)
            return ((JDialog) frame).getComponent(0);
        return awtComp;
    }

    public Component getAWTComponent() {
        return awtComp;
    }

    public void setWindow(Window win) {
        this.frame = win;
        if(win != null)
            awtComp = getContentPane();
    }

    public Class getComponentClass() {
        if (isJFrame())
            return JFrame.class;
        if (isJApplet())
            return JApplet.class;
        if (isJWindow())
            return JWindow.class;
        if (isJDialog())
            return JDialog.class;
        if (isJInternalFrame())
            return JInternalFrame.class;
        return awtComp.getClass();
    }

    public void setNeedsRepaint(boolean needsRepaint) {
        this.needsRepaint = needsRepaint;
    }

    public void layoutInFrame() {
        try {

            Runnable r = new Runnable() {
                public void run() {
                    try {
                        if (SHOW_FRAME) {
                            if (frame instanceof JDialog) {
                                ((JDialog) frame).setModal(false);
                            }
                            frame.setVisible(true);
                        }

                        if (frame == null || getContentPane() == null)
                            return;

                        Container cp = getContentPane();
                        if (!cp.equals(awtComp) && cp.getComponentCount() == 0) {
                            cp.removeAll();
//                            cp.setLayout(new BorderLayout());
                            cp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                            if (awtComp != null)
                                cp.add(awtComp);
                        }

//                        System.err.println("awtcomp LAYOUT (-1) "
//                                + frame.getBounds() + ", AWTCOMP BOUNDS="
//                                + awtComp.getBounds());

                        if (!formComponent.isPropertySet("size")
                                && !formComponent
                                        .isPropertySet("preferredSize")
                                && !formComponent.isPropertySet("bounds")) {
                            frame.pack();
                        } else {
                            Dimension sz = null;
                            if (formComponent.isPropertySet("size")) {
                                Point pt = (Point) formComponent
                                        .getRawPropertyValue("size");
                                if(pt != null)
                                	sz = new Dimension(pt.x, pt.y);
                            }
                            if (formComponent.isPropertySet("bounds")) {
                                Rectangle pt = (Rectangle) formComponent
                                        .getRawPropertyValue("bounds");
                                if(pt != null)
                                	sz = new Dimension(pt.width, pt.height);
                            }
                            if (formComponent.isPropertySet("preferredSize")
//                                    && !formComponent.isExtraProp("preferredSize")
                                    ) {
                                Point pt = (Point) formComponent.getRawPropertyValue("preferredSize");
                                if(pt != null) {
                                    sz = new Dimension(pt.x, pt.y);
                                }
                            }
                            if ((formComponent.isJFrame()
                            		|| formComponent.isJDialog() || formComponent.isJWindow()) && sz != null) {
                                formComponent.subtractWindowDecorationSizes(sz);
                                sz.height -= formComponent.getEditor().getMenuHeight();
                                ((JComponent) awtComp).setPreferredSize(sz);
                            }
                            frame.pack();
                        }
                        frame.setLocation(10, 10);
                        frame.setVisible(false);
                        
//                         System.err.println("awtcomp LAYOUT (0) "
//                                 + frame.getBounds() + ", AWTCOMP BOUNDS="
//                                 + awtComp.getBounds());
  
                        java.awt.Rectangle dim = awtComp.getBounds();
                        java.awt.Rectangle fdim = frame.getBounds();

                        if (fdim.width > MAX_WIDTH || fdim.height > MAX_HEIGHT) {
                            //this can happen if something goes badly wrong
                            //parsing the code - resulting in an awtComp size
                            // of 10000x10000
                            if (fdim.width > MAX_WIDTH)
                                fdim.width = MAX_WIDTH;
                            if (fdim.height > MAX_HEIGHT)
                                fdim.height = MAX_HEIGHT;
                            frame.setBounds(fdim);
                            frame.setSize(fdim.width, fdim.height);
                            frame.validate();
                            System.err.println("Got HUGE Frame bounds " + frame.getBounds()
                                    + ", AWTComp bounds=" + awtComp.getBounds());
                        }
                        
                        dim = awtComp.getBounds();
                        fdim = frame.getBounds();
                         
                        if (dim.width > fdim.width || dim.height > fdim.height) {
                            //this can happen if something goes badly wrong
                            //parsing the code - resulting in an awtComp size
                            // of 10000x10000
                            if (dim.width > fdim.width)
                                dim.width = fdim.width;
                            if (dim.height > fdim.height)
                                dim.height = fdim.height;
                            awtComp.setBounds(dim);
                            awtComp.setSize(dim.width, dim.height);
                            awtComp.validate();
//                            System.err.println("GOT HUGE FRAME BOUNDS (2) "
//                                    + frame.getBounds() + ", AWTCOMP BOUNDS="
//                                    + awtComp.getBounds());
                        }
                    } catch (Throwable e) {
                        System.err.println("AWTControl.layoutInFrame: error (1) " + e);
                        e.printStackTrace();
                    }
                }
            };
            //need to invoke inside Swing event loop, or Eclipse 3 stalls (sometimes!)
//            JiglooUtils.invokeSwing(r);
//          SwingUtilities.invokeLater(r);
            r.run();

            //v4.0M3
//            if(needsRepaint)
//            	createImage();
            
        } catch (Throwable e) {
            System.err.println("AWTControl.layoutInFrame: error " + e);
            e.printStackTrace();
        }
    }

    public Container getContentPane() {
        if (frame instanceof JWindow)
            return ((JWindow) frame).getContentPane();
        if (frame instanceof JFrame)
            return ((JFrame) frame).getContentPane();
        else if (frame instanceof JDialog)
            return ((JDialog) frame).getContentPane();
        return null;
    }

    public void setComponent(Container awtComponent) {
        if (!awtComponent.equals(getContentPane())) {
            if (frame instanceof JFrame)
                ((JFrame) frame).setContentPane(awtComponent);
            else if (frame instanceof JWindow)
                ((JWindow) frame).setContentPane(awtComponent);
            else if (frame instanceof JDialog)
                ((JDialog) frame).setContentPane(awtComponent);
        }
        this.awtComp = awtComponent;
    }

    public void doDispose() {
        if (im != null && !im.isDisposed())
            im.dispose();
        im = null;
        
        if (frame != null) {
            //below line causes DBTable to hang when editor is closed
            //        				getContentPane().removeAll();
            frame.dispose();
            frame = null;
        }

        awtComp = null;
        if (bim != null)
            bim.flush();
        bim = null;
        if (g2 != null) {
            g2.dispose();
            g2.finalize();
        }
        g2 = null;
        pg = null;
        imd = null;
        pix = null;
        palData = null;
        selectedComp = null;
        formComponent = null;
        
        removePaintListener(paintList);
        paintList = null;
    }

    public boolean isJFrame() {
        String cn = formComponent.getClassName();
        return cn.equals(JFrame.class.getName());
    }

    public boolean isJApplet() {
        String cn = formComponent.getClassName();
        return cn.equals(JApplet.class.getName());
    }

    public boolean isJWindow() {
        String cn = formComponent.getClassName();
        return cn.equals(JWindow.class.getName());
    }

    public boolean isJDialog() {
        String cn = formComponent.getClassName();
        return cn.equals(JDialog.class.getName());
    }

    public boolean isJInternalFrame() {
        String cn = formComponent.getClassName();
        return cn.equals(JInternalFrame.class.getName());
    }

    public Point getSize() {
        return new Point(width, height); //return
        // super.getSize();
    }

    public Point computeSize(int wHint, int hHint) {
        return computeSize(width, height, false);
    }

    public Point computeSize(int wHint, int hHint, boolean changed) { //FormComponent
        // fc =
        // ((FormComposite)
        // getParent()).getFormComponent();
        if (formComponent == null)
            return new Point(200, 200);
        SizePropertySource ps = (SizePropertySource) formComponent
                .getPropertyValue("preferredSize");
//        if (ps == null) {
//            Rectangle b = (Rectangle)formComponent.getRawPropertyValue("bounds");
//            return new Point(b.width, b.height);
//        }
        if (ps == null) {
            ps = (SizePropertySource) formComponent.getPropertyValue("size");
        }
//        System.out.println("computeSize " + this +" fc=" + formComponent + " ps=" + ps);
        if (ps == null)
            return new Point(width, height);
        Point pt = ps.getValue();
        //pt = formComponent.getSizeFromBounds();
        width = pt.x;
        height = pt.y;
        return new Point(width, height);
    }

    public void setSize(int width, int height) {
        awtComp.setSize(width, height);
        super.setSize(width, height);
    }

    public void setBounds(int x, int y, int width, int height) {
        awtComp.setSize(width, height);
        super.setBounds(x, y, width, height);
    }

    public void setBounds(Rectangle rect) {
        setBounds(rect.x, rect.y, rect.width, rect.height);
    }

	public FormComponent getComponentToBeLayedOut() {
		return toBeLayedOut;
	}
}