/*
 * Created on Oct 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.frames;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.AlignComposite;
import com.cloudgarden.jigloo.dialog.AlignDialog;
import com.cloudgarden.jigloo.dialog.AnchorDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.layoutHandler.EnfinLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
 * @author jonathan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MenuButton extends Composite {

    private static AlignDialog alignDialog = null;
    private static AnchorDialog anchorDialog = null;
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        public void mouseDown(MouseEvent e) {
            handleMouseDown(e);
        }
    };
    private FormEditor editor;
    private MenuManager mm;
    private FormComponent fc;

    private PaintListener paintListener = new PaintListener() {
        public void paintControl(PaintEvent pe) {
            Control con = (Control) pe.getSource();
            int w = con.getSize().x;
            int h = con.getSize().y;
            pe.gc.setBackground(ColorManager.getColor(255, 255, 255));
            pe.gc.fillRectangle(0, 0, w, h);
            pe.gc.setForeground(ColorManager.getColor(0, 0, 0));
            pe.gc.drawRectangle(0, 0, w - 1, h - 1);
            pe.gc.setBackground(ColorManager.getColor(0, 0, 0));
            int[] pts = new int[] { 2, 3, 9, 3, 5, 7, 2, 3 };
            pe.gc.fillPolygon(pts);
        }
    };

    public MenuButton(Composite parent, FormEditor editor) {
        super(parent, SWT.NULL);
//        setToolTipText("Component alignment assistance");
        setSize(11, 10);
        this.editor = editor;
        addMouseListener(mouseAdapter);
        addKeyListener(editor.getFormEditorKeyAdapter());
        addPaintListener(paintListener);
    }

    public void dispose() {
        removeKeyListener(fc.getEditor().getFormEditorKeyAdapter());
        removeMouseListener(mouseAdapter);
        removePaintListener(paintListener);
        super.dispose();
    }
    
    private boolean showDialog() {
        String plt = fc.getParentLayoutType();
        return  (
                fc.isSWT() 
                && "Grid".equals(plt))
                || (fc.isSwing() 
                        && ("GridBag".equals(plt) || "Form".equals(plt)));
    }
    
    private boolean showAnchorDialog() {
        String plt = fc.getParentLayoutType();
        return  (fc.isSwing()  && "Anchor".equals(plt))
        || (fc.isSWT()  && "Form".equals(plt));
    }
    
    /**
     * @param e
     */
    protected void handleMouseDown(MouseEvent e) {
        if(showDialog()) {
            if(alignDialog == null || alignDialog.getAlignComp().isDisposed()) {
                alignDialog = new AlignDialog(getShell(), SWT.DIALOG_TRIM);
                Point mp = toDisplay(0, 0);
                alignDialog.open(mp.x, mp.y + 10);
                AlignComposite ac = alignDialog.getAlignComp();
                ac.setActions(fc.getEditor());
            }
            return;
        } else if(showAnchorDialog()) {
            if(anchorDialog == null || anchorDialog.getAnchorPanel().isDisposed()) {
                anchorDialog = new AnchorDialog(getShell(), SWT.DIALOG_TRIM);
                anchorDialog.setFormEditor(fc.getEditor());
                Point mp = toDisplay(0, 0);
                anchorDialog.open(mp.x, mp.y + 10);
            }
            return;
        }
        setFormComponent(fc);
        Point mp = toDisplay(0, 0);
        if(mm != null) {
            Menu menu = mm.createContextMenu(MenuButton.this);
            menu.setLocation(mp.x, mp.y + 10);
            menu.setVisible(true);
        }
    }

    public void setVisible(boolean vis) {
        if(isDisposed())
            return;
        if(vis) {
            boolean canShow = (mm!= null) || showDialog() || showAnchorDialog();
            if(!canShow)
                return;
        }
        super.setVisible(vis);
        if(vis)
            moveAbove(null);
    }
    
    /**
     * @param parent
     */
    public void setFormComponent(FormComponent fc) {
        this.fc = fc;
        FormComponent par = fc.getParent();
        String plt = fc.getParentLayoutType();
        mm = null;
        boolean visible  = false;
        if(showDialog() || showAnchorDialog()) {
            visible = true;
        } else if (par != null) {
            if (par.isSWT()) {
            	if (MigLayoutHandler.handlesLayout(par)) {
                    mm = new MenuManager();
                    editor.addLayoutDataOption(mm, "customLayoutConstraint", null);
                    visible = true;
                }
            } else if (par.isSwing()) {
                if ("Border".equals(plt)) {
                    mm = new MenuManager();
                    String prop = "direction";
                    editor.addLayoutDataOption(mm, prop, BorderLayout.NORTH);
                    editor.addLayoutDataOption(mm, prop, BorderLayout.SOUTH);
                    editor.addLayoutDataOption(mm, prop, BorderLayout.EAST);
                    editor.addLayoutDataOption(mm, prop, BorderLayout.WEST);
                    editor.addLayoutDataOption(mm, prop, BorderLayout.CENTER);
                    visible = true;
                } else if ("Enfin".equals(plt) && editor.canUseEnfinLayout()) {
                    mm = new MenuManager();
                    String prop = "alignment";
                    String[] opts = EnfinLayoutHandler.optionNames;
                    for (int i = 0; i < opts.length; i++) {
                        editor.addLayoutDataOption(mm, prop, opts[i]);
                    }
                    visible = true;
                } else if ("Table".equals(plt)) {
                    mm = new MenuManager();
                    String prop = "alignment";
                    editor.addLayoutDataOption(mm, prop, "left", "align left");
                    editor.addLayoutDataOption(mm, prop, "center-horiz",
                            "align center (horiz)");
                    editor
                            .addLayoutDataOption(mm, prop, "right",
                                    "align right");
                    editor.addLayoutDataOption(mm, prop, "fill-horiz",
                            "fill (horiz)");
                    editor.addLayoutDataOption(mm, prop, "top", "align top");
                    editor.addLayoutDataOption(mm, prop, "center-vert",
                            "align center (vert)");
                    editor.addLayoutDataOption(mm, prop, "bottom",
                            "align bottom");
                    editor.addLayoutDataOption(mm, prop, "fill-vert",
                            "fill (vert)");
                    visible = true;
                } else if ("Group".equals(plt)) {
                    mm = new MenuManager();
                    editor.addLayoutDataOption(mm, LayoutGroup.EXPAND_HOR, LayoutGroup.EXPAND_HOR, "Expands Horizontally");
                    editor.addLayoutDataOption(mm, LayoutGroup.EXPAND_VER, LayoutGroup.EXPAND_VER, "Expands Vertically");
                    editor.addLayoutDataOption(mm, LayoutGroup.ANCHOR_LEFT, LayoutGroup.ANCHOR_LEFT, "Anchor LEFT");
                    editor.addLayoutDataOption(mm, LayoutGroup.ANCHOR_RIGHT, LayoutGroup.ANCHOR_RIGHT, "Anchor RIGHT");
                    editor.addLayoutDataOption(mm, LayoutGroup.ANCHOR_TOP, LayoutGroup.ANCHOR_TOP, "Anchor TOP");
                    editor.addLayoutDataOption(mm, LayoutGroup.ANCHOR_BOTTOM, LayoutGroup.ANCHOR_BOTTOM, "Anchor BOTTOM");
                    editor.addLayoutDataOption(mm, LayoutGroup.SET_DEFAULT_HEIGHT, LayoutGroup.SET_DEFAULT_HEIGHT, "Reset to default height");
                    editor.addLayoutDataOption(mm, LayoutGroup.SET_DEFAULT_WIDTH, LayoutGroup.SET_DEFAULT_WIDTH, "Reset to default width");
                    visible = true;
                } else if (par.isSubclassOf(JSplitPane.class)) {
                    mm = new MenuManager();
                    String prop = "position";
                    if (par.getIntPropertyValue("orientation") == JSplitPane.VERTICAL_SPLIT) {
                        editor.addLayoutDataOption(mm, prop, JSplitPane.TOP);
                        editor.addLayoutDataOption(mm, prop, JSplitPane.BOTTOM);
                    } else {
                        editor.addLayoutDataOption(mm, prop, JSplitPane.LEFT);
                        editor.addLayoutDataOption(mm, prop, JSplitPane.RIGHT);
                    }
                    visible = true;
                } else if ("Mig".equals(plt)) {
                    mm = new MenuManager();
                    editor.addLayoutDataOption(mm, "customLayoutConstraint", null);
                    visible = true;
                }
            }
        }
        setVisible(visible);

    }

    /**
     * 
     */
    public void deactivate() {
        if(anchorDialog != null && !anchorDialog.getAnchorPanel().isDisposed())
            anchorDialog.dispose();
        anchorDialog = null;
        if(alignDialog != null && !alignDialog.getAlignComp().isDisposed())
            alignDialog.dispose();
        alignDialog = null;
    }
}