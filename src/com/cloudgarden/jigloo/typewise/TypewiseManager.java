/*
 * Created on Jul 6, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.typewise;

import java.awt.Component;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.swing.JLabel;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.ConstructorManager;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.philemonworks.typewise.Bounds;
import com.philemonworks.typewise.CompositeWidget;
import com.philemonworks.typewise.Widget;
import com.philemonworks.typewise.cwt.GroupBox;
import com.philemonworks.typewise.cwt.Panel;
import com.philemonworks.typewise.cwt.Screen;
import com.philemonworks.typewise.cwt.TableColumn;
import com.philemonworks.typewise.cwt.TableList;
import com.philemonworks.typewise.server.ApplicationModel;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TypewiseManager {

    public TypewiseManager() {
        super();
    }

    public static void addConstructorMappings() {
        ConstructorManager.addToMap(Screen.class,
                "String|ApplicationModel|int|int", "name|model|rows|columns");
        ConstructorManager.addToMap(Widget.class, "String|int|int|int|int",
                "name|topRow|leftColumn|rows|columns");
    }

    public static boolean canResetProperty(FormComponent fc, Object propName) {
        if ("bounds".equals(propName))
            return false;
        return true;
    }

    public static void handleScreenMethod(MethodDeclaration mdec,
            FormEditor editor, FormComponent scr) {
        //        System.out.println("TypewiseManager.handleScreenMethod " + scr);
        FormComponent root = editor.getJavaCodeParser().getRootComponent();
        //FormComponent root = editor.getJavaCodeParser().getNonVisualRoot();
        //if(scr.getParent() != null)
        //scr.getParent().getChildren().remove(scr);
        //scr.moveToParent(root, -1);
        //root.getChildren().add(scr);
        if (root.getChildren() == null || !root.getChildren().contains(scr)) {
            root.add(scr);
            scr.setParent(root);
        }
    }

    public static void addSynthProperties(FormComponent fc) {
        if (fc.isSubclassOf(Widget.class)) {
            fc.addSynthProperty("rows", Integer.class, new Integer(1));
            fc.addSynthProperty("columns", Integer.class, new Integer(1));
            fc.addSynthProperty("topRow", Integer.class, new Integer(1));
            fc.addSynthProperty("leftColumn", Integer.class, new Integer(1));
            //            fc.addSynthProperty("background", java.awt.Color.class,
            //                    java.awt.Color.darkGray);
            //            fc.addSynthProperty("foreground", java.awt.Color.class,
            //                    java.awt.Color.white);
        }
        //		if (fc.isSubclassOf(List.class)) {
        //			fc.addSynthProperty("items", ArrayList.class, new ArrayList());
        //		}
    }

    public static boolean hasSynthProperty(FormComponent fc, String id) {
        if (ClassUtils.isCWTWidget(fc.getMainClass())) {
            if ("rows".equals(id))
                return true;
            if ("columns".equals(id))
                return true;
            if ("topRow".equals(id))
                return true;
            if ("leftColumn".equals(id))
                return true;
            //            if ("background".equals(id))
            //                return true;
        }
        return false;
    }

//    public static void fillPalette(ComponentPalette palette, FormEditor editor) {
//        palette.addAction(editor.getFormAddAction(Screen.class), 2, 0);
//        palette.addAction(editor.getFormAddAction(Panel.class), 2, 0);
//        palette.addAction(editor.getFormAddAction(GroupBox.class), 2, 0);
//
//        palette.addAction(editor.getFormAddAction(Button.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(CheckBox.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(RadioButton.class), 2, 1);
//        //		palette.addAction(editor.getFormAddAction(Counter.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(Image.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(Label.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(List.class), 2, 1);
//        //		palette.addAction(editor.getFormAddAction(DropDownList.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(TableList.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(TableColumn.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(Menu.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(MenuBar.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(TextArea.class), 2, 1);
//        palette.addAction(editor.getFormAddAction(TextField.class), 2, 1);
//    }

    public static int X_OFFSET = 0; //4;
    public static int Y_OFFSET = 0; //24;

    public static boolean isContainer(FormComponent fc) {
        return fc.isSubclassOf(Panel.class) || fc.isSubclassOf(Screen.class)
                || fc.isSubclassOf(GroupBox.class)
                || fc.isSubclassOf(TableList.class)
                || fc.isSubclassOf(ApplicationModel.class);
    }

    public static boolean canAddTo(FormComponent parent, Class child) {
        if (parent.isSubclassOf(Panel.class)
                || parent.isSubclassOf(Screen.class)
                || parent.isSubclassOf(GroupBox.class)) {
            return !Screen.class.isAssignableFrom(child)
                    && !TableColumn.class.isAssignableFrom(child);
        }

        if (parent.isSubclassOf(TableList.class))
            return TableColumn.class.isAssignableFrom(child);
        if (parent.isSubclassOf(ApplicationModel.class))
            return Screen.class.isAssignableFrom(child);
        return false;
    }

    public static Rectangle convertBounds(int x, int y, int w, int h,
            FormComponent fc) {
        WidgetHolder wh = getWidgetHolder(fc);
        int gcw = wh.getGridCharWidth();
        int gch = wh.getGridCharHeight();
        Rectangle rec = new Rectangle(x, y, w, h);
        rec.x = X_OFFSET + (x - 1) * gcw;
        rec.height = h * gch;
        rec.y = Y_OFFSET + (y - 1) * gch;
        rec.width = w * gcw;
        return rec;
    }

    public static void bringToFront(FormComponent fc) {
        FormEditor ed = fc.getEditor();
        if (ed == null)
            return;
        WidgetHolder wh = getWidgetHolder(fc);
        if (wh == null)
            return;
        FormComponent sfc = getScreen(fc);
        if (sfc == null)
            return;
        Object scr = sfc.getNonVisualObject();
        if (scr == null || scr.equals(wh.getTopScreen()))
            return;
        wh.showWidget(scr);
        //		System.out.println("CWT BRING TO FRONT " + fc + ", " + scr);
        ed.redrawRoot();
        //		if (fc.isCWTScreen()
        //			&& ed != null
        //			&& ed.getAWTControl() != null
        //			&& ed.getAWTControl().getComponent() != null) {
        //			((WidgetHolder)
        // ed.getAWTControl().getComponent()).showWidget(fc.getNonVisualObject());
        //			ed.redrawRoot();
        //		}
    }

    public static Object handleSetBoundingProperty(Object id, Object value,
            FormComponent fc) {
        //		if ("bounds".equals(id)) {
        //			if(value instanceof RectanglePropertySource)
        //			value = ((RectanglePropertySource)value).getValue();
        //			Rectangle b = (Rectangle)value;
        //			b.width--;
        //			b.height--;
        //			return value;
        //		}
        if ("rows".equals(id) || "columns".equals(id) || "topRow".equals(id)
                || "leftColumn".equals(id)) {
            int rows = fc.getIntPropertyValue("rows");
            int cols = fc.getIntPropertyValue("columns");
            int tr = fc.getIntPropertyValue("topRow");
            int lc = fc.getIntPropertyValue("leftColumn");
            if (id.equals("rows"))
                rows = ((Integer) value).intValue();
            else if (id.equals("columns"))
                cols = ((Integer) value).intValue();
            else if (id.equals("topRow"))
                tr = ((Integer) value).intValue();
            else if (id.equals("leftColumn"))
                lc = ((Integer) value).intValue();
            fc.setPropertyValueInternal("bounds", new Rectangle(tr, lc, rows,
                    cols), false, false);
            //fc.invokeOnControl("setBounds", new Object[] { new
            // java.awt.Rectangle(tr, lc, rows, cols)});
            //fc.getEditor().redrawRoot();
            //fc.getEditor().realignWindowFrames();
        }
        return value;
    }

    //	public static void reconcileBounds(FormComponent fc) {
    //		int rows = fc.getIntPropertyValue("rows");
    //		int cols = fc.getIntPropertyValue("columns");
    //		int tr = fc.getIntPropertyValue("topRow");
    //		int lc = fc.getIntPropertyValue("leftColumn");
    //		fc.setPropertyValue("bounds", new java.awt.Rectangle(tr, lc, rows,
    // cols));
    //	}

    public static Bounds convertBounds(Object o) {
        java.awt.Rectangle b = (java.awt.Rectangle) o;
        //Bounds bt = new Bounds(1,2,3,4);
        //tr,lc,r,c
        //System.err.println("lc="+bt.getLeftColumn()+", tr="+bt.getTopRow()+",
        // c="+bt.getColumns()+", r="+bt.getRows());
        //        System.err.println("Convert Bounds "+b);
        if (b.x < 1)
            b.x = 1;
        if (b.y < 1)
            b.y = 1;
        if (b.width < 1)
            b.width = 1;
        if (b.height < 1)
            b.height = 1;
        return new Bounds(b.x, b.y, b.width, b.height);
    }

    public static Rectangle convertToGridBounds(Rectangle b, FormComponent fc) {
        WidgetHolder wh = (WidgetHolder) fc.getEditor().getAWTControl()
                .getAWTComponent();
        int gcw = wh.getGridCharWidth();
        int gch = wh.getGridCharHeight();
        Rectangle rec = new Rectangle(b.x, b.y, b.width, b.height);
        FormComponent par = fc.getParent();
        int px = 1;
        int py = 1;
        if (par != null && !fc.isSubclassOf(Screen.class)) {
            px = par.getIntPropertyValue("leftColumn");
            py = par.getIntPropertyValue("topRow");
        }
        rec.x = py + (b.y + gch / 2) / gch; //rows
        rec.y = px + (b.x + gcw / 2) / gcw; //columns
        rec.width = ((b.height + gch / 2) / gch); //row
        // height
        rec.height = ((b.width + gcw / 2) / gcw); //column
        // width
        //System.out.println("CONVERTED TO GRID " + b + ", " + rec + ", gcw=" +
        // gcw + ", gch=" + gch);
        return rec;
    }

    private static FormComponent getScreen(FormComponent fc) {
        while (fc != null && !fc.isSubclassOf(Screen.class))
            fc = fc.getParent();
        return fc;
    }

    public static WidgetHolder getWidgetHolder(FormComponent fc) {
        if (fc == null || fc.getEditor() == null
                || fc.getEditor().getAWTControl() == null)
            return null;
        return (WidgetHolder) fc.getEditor().getAWTControl().getAWTComponent();
    }

    public static Object getTopScreen(FormComponent fc) {
        WidgetHolder wh = getWidgetHolder(fc);
        if (wh == null)
            return wh;
        return wh.getTopScreen();
    }

    public static boolean isVisible(FormComponent fc) {
        if (fc == null)
            return false;
        if (fc.isSubclassOf(Screen.class)) {
            if (fc.getNonVisualObject() == null)
                return false;
            return fc.getNonVisualObject().equals(getTopScreen(fc));
        }
        return isVisible(getScreen(fc));
    }

    public static Rectangle getBounds(FormComponent fc) {
        if (!isVisible(fc))
            return new Rectangle(0, 0, 1, 1);
        Object obj = fc.getNonVisualObject();
        Rectangle rec = null;
        if (obj instanceof Widget) {
            Widget wid = (Widget) obj;
            //            Widget par = wid.getParent();
            Bounds wb = wid.getBounds();
            int w = getIntProp(fc, "columns");
            int h = getIntProp(fc, "rows");
            int x = getIntProp(fc, "leftColumn");
            int y = getIntProp(fc, "topRow");
            rec = convertBounds(x, y, w, h, fc);
        } else {
            System.err.println("NVO is not Widget in getBounds " + fc + ", "
                    + obj);
            new Exception().printStackTrace();
        }
        //System.out.println("GET CWT BOUNDS "+fc+", "+rec);
        return rec;
    }

    public static Object makeDefaultNonVisualObject(FormComponent fc,
            Class clazz) throws Exception {
        try {
            Object nvobj = null;
            Constructor struct = null;
            Constructor[] cons = clazz.getConstructors();
            for (int i = 0; i < cons.length; i++) {
                Constructor con = cons[i];
                int plen = con.getParameterTypes().length;
                if (plen == 5 || plen == 4) {
                    struct = con;
                    break;
                }
            }
            if (struct != null) {
                int rows = 2;
                int cols = 15;
                if (Screen.class.isAssignableFrom(clazz)) {
                    FormComponent sfc = fc.getRootComponent().getChildAt(0);
                    rows = getIntProp(sfc, "rows");
                    cols = getIntProp(sfc, "columns");
                    //rows = 30;
                    //cols = 20;
                }
                Integer one = new Integer(1);
                Integer rc = new Integer(rows);
                Integer cc = new Integer(cols);
                if (Screen.class.isAssignableFrom(clazz)) {
                    nvobj = ClassUtils.newInstance(clazz, struct,
                            new Object[] { fc.getName(), null, rc, cc });
                } else {
                    nvobj = ClassUtils.newInstance(clazz, struct,
                            new Object[] { fc.getName(), one, one, rc, cc });
                }
            } else {
                nvobj = clazz;
                /*
                 * MessageDialog.openError( getEditor().getSite().getShell(),
                 * "Unable to create " + clazz, "Unable to create " + clazz +
                 * "\nNo-parameter constructor required"); return null;
                 */
            }
            return nvobj;
        } catch (Throwable t) {
            if (jiglooPlugin.DEBUG_EXTRA)
                System.err.println("Error in makeNonVisualObject for class "
                        + clazz + ", " + t);
            //t.printStackTrace();
            return clazz;
        }
    }

    public static String getJavaCodeForConstructor(FormComponent fc,
            String name, String clsName) {
        if (fc.isCWTScreen()) {
            return "\t\t" + name + " = new " + clsName + "(\""
                    + fc.getPropertyValue("name") + "\", this, "
                    + fc.getPropertyValue("rows") + ", "
                    + fc.getPropertyValue("columns") + ");\n";
        } else {
            return "\t\t" + name + " = new " + clsName + "(\""
                    + fc.getPropertyValue("name") + "\", "
                    + fc.getPropertyValue("topRow") + ", "
                    + fc.getPropertyValue("leftColumn") + ", "
                    + fc.getPropertyValue("rows") + ", "
                    + fc.getPropertyValue("columns") + ");\n";
        }
    }

    public static String getJavaAddToParentCode(FormComponent fc, String name) {
        if (fc.isSubclassOf(Screen.class))
            return "";
        return fc.getParentNameInCode() + ".add(" + name + ");\n";
    }

    private static int getIntProp(FormComponent fc, String prop) {
        Object obj = fc.getPropertyValue(prop);
        if (obj == null)
            return 0;
        return ((Integer) obj).intValue();
    }

    public static void removeWidget(FormComponent parent, FormComponent child) {
        try {
            if (parent == null || child == null)
                return;
            Object pobj = parent.getNonVisualObject();
            if (pobj == null || !(pobj instanceof CompositeWidget))
                return;
//            System.out.println("REMOVING " + child.getNonVisualObject()
//                    + " from " + pobj);
            ((CompositeWidget) pobj).getWidgets().remove(
                    child.getNonVisualObject());
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }

    public static void rebuild(FormComponent fc) {
        populateCWTWidget(fc.getEditor().getSwingMainPanel(), fc.getEditor(),
                fc);
        //par.remove(component);
        //addComponent(component, par, pos);
//        System.err.println("REBUILD FOR CWT");
    }

    public static boolean updateInCode(IFormPropertySource element,
            String pName, JavaCodeParser jcp) {
        if (pName.equals("bounds") || pName.equals("rows")
                || pName.equals("columns") || pName.equals("topRow")
                || pName.equals("leftColumn")) {
            jcp.repairConstructor(element);
            return true;
        }
        return false;
    }

    public static void adjustInitialBounds(FormComponent fc, MouseEvent evt) {
        //b = addedComponent.getBounds();
        //b.x = evt.x;
        //b.y = evt.y;
        if (fc.isSubclassOf(Screen.class))
            return;
        WidgetHolder wh = getWidgetHolder(fc);
        int px = fc.getParent().getIntPropertyValue("leftColumn");
        int py = fc.getParent().getIntPropertyValue("topRow");
        int x = (evt.x - fc.getEditor().BORDER_X) / wh.getGridCharWidth() + 1;
        int y = (evt.y - fc.getEditor().BORDER_Y) / wh.getGridCharHeight() + 1;
        System.out.println("ADDED CWT WID " + x + ", " + evt.x + ", " + y
                + ", " + evt.y);
        fc.setPropertyValueDirect("leftColumn", new Integer(x));
        fc.setPropertyValueDirect("topRow", new Integer(y));
    }

    public static void addWidgetTo(FormComponent parent, FormComponent child,
            int position) {
        Object pnvo = parent.getNonVisualObject();
        Object cnvo = child.getNonVisualObject();
        Widget pwid = null;
        Widget cwid = null;
        Bounds pb = null;
        Bounds cb = null;
        if (pnvo instanceof Widget) {
            pwid = (Widget) pnvo;
            pb = pwid.getBounds();
        }
        if (cnvo instanceof Widget) {
            cwid = (Widget) cnvo;
            if (pb != null) {
                cb = cwid.getBounds();
                int lc = cb.getLeftColumn();
                int cs = cb.getColumns();
                int tr = cb.getTopRow();
                int rs = cb.getRows();
                int plc = pb.getLeftColumn();
                int pcs = pb.getColumns();
                int ptr = pb.getTopRow();
                int prs = pb.getRows();
                if (pwid != null) {
                    if (cs + lc > pcs + plc) {
                        lc = plc - cs;
                    }
                    if (lc < plc)
                        lc = plc;
                    if (cs + lc > pcs + plc) {
                        cs = pcs;
                    }
                    if (rs + tr > prs + ptr) {
                        tr = ptr - rs;
                    }
                    if (tr < ptr)
                        tr = ptr;
                    if (rs + tr > prs + ptr) {
                        rs = prs;
                    }
                    cb = new Bounds(tr, lc, rs, cs);
                    cwid.setBounds(cb);
                }
            }
        }
        //        System.out.println("ADD CWT WID TO " + parent + ", " + child + ", " +
        //         pnvo + ", " + cnvo);
        try {
            if (pnvo instanceof CompositeWidget) {
                ((CompositeWidget) pnvo).add(cwid);
            } else if (pnvo instanceof Widget) {
                cwid.setParent(pwid);
            } else if (pnvo instanceof ApplicationModel
                    || (pnvo != null && pnvo.equals(ApplicationModel.class))) {
                WidgetHolder wh = (WidgetHolder) parent.getEditor()
                        .getAWTControl().getAWTComponent();
                if (cnvo instanceof Screen) {
                    //                    System.out.println("ADD CWT SCREEN " + cnvo);
                    wh.addWidget(cwid);
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error adding " + child + " to "
                    + parent);
        }

    }

    public static void populateCwtWidgets(FormComponent fc, Component parent,
            FormEditor editor) {
        if (fc.isInherited())
            return;
        //System.out.println("POPULATE CWT WIDGETS " + this +", " +
        // nonVisualObject);
        fc.setEditor(editor);

        //TypewiseManager.removeWidget(getParent(), this);
        //nonVisualObject = null;

        if (fc.getNonVisualObject() == null) {
            Object comp = null;
            if (fc.getClassName() != null) {
                try {
                    comp = fc.makeNonVisualObject(fc.getClassName());
                } catch (Exception ex) {
                	jiglooPlugin.handleError(editor.getSite().getShell(),
                            "Error creating " + fc.getClassName(),
                            "Error creating " + fc.getClassName(), ex);
                }
            }
            String unknownClass = null;
            if (comp == null) {
                unknownClass = fc.getClassName();
                comp = new JLabel();
                fc.setClassName(comp.getClass().getName());
            }
            fc.setNonVisualObject(comp);
            //System.out.println("MADE cwt OBJ " + comp);
            fc.initProperties();
        }
        populateCWTWidget(parent, editor, fc);
        if (editor != null)
            editor.addComponent(fc);
        //initInheritedElements();
        Vector comps = fc.getChildren();
        for (int i = 0; i < comps.size(); i++) {
            FormComponent cc = (FormComponent) comps.elementAt(i);
            cc.populateCwtWidgets(parent, editor);
        }
    }

    public static void populateCWTWidget(Component parent, FormEditor editor,
            FormComponent fc) {
        try {
            fc.setDisposed(false);
            Object obj = fc.getNonVisualObject();
            //System.out.println("POP CWT WIDS " + fc);
            WidgetHolder wh = getWidgetHolder(fc);

            if (obj instanceof Screen) {
                //wh.removeWidget((Screen) obj);
                wh.addWidget((Screen) obj);

            } else if (obj instanceof Widget) {
                Widget wid = (Widget) obj;
                Widget par = wid.getParent();
                Object fcPar = fc.getParent().getNonVisualObject();
                if (par == null) {
                    if (fcPar instanceof Screen) {
                        //removeWidget(fc.getParent(), fc);
                        ((Screen) fcPar).add(wid);
                    } else if (fcPar instanceof CompositeWidget) {
                        ((CompositeWidget) fcPar).add(wid);
                    }
                }

            }
        } catch (Throwable t) {
            jiglooPlugin
                    .handleError(t, "Error in populateCWTWidgets for " + fc);
        }
    }
}