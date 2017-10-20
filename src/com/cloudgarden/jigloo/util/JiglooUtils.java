/*
 * Created on Aug 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICodeFormatter;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchResultCollector;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.ArrayHolder;
import com.cloudgarden.jigloo.frames.WindowFrame;
import com.cloudgarden.jigloo.groupLayoutSupport.GroupLayoutUtils;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.TableLayoutHandler;
import com.cloudgarden.jigloo.palette.ComponentPalette;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.preferences.PaletteComposite;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JiglooUtils {

    static PaletteData palData = new PaletteData(0xFF0000, 0xFF00, 0xFF);

    public static RGB getRGB(String value) {
        try {
            int pos = value.indexOf(",");
            int red = Integer.parseInt(value.substring(0, pos));
            int pos2 = value.indexOf(",", pos + 1);
            int grn = Integer.parseInt(value.substring(pos + 1, pos2));
            int blu = Integer.parseInt(value.substring(pos2 + 1));
            return new RGB(red, grn, blu);
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap getInitialProperties(Object obj) {
        HashMap props = new HashMap();
        Class cls = obj.getClass();
        Vector pNames = PropertySourceFactory.findPropertyNames(cls);
        Vector fNames = PropertySourceFactory.findFieldNames(cls);
        Object val = null;
        for (int i = 0; i < pNames.size(); i++) {
            String pName = (String) pNames.elementAt(i);
            try {
                Method m = Cacher.getMethod(cls, "get" + JiglooUtils.capitalize(pName), null);
                val = m.invoke(obj, null);
            } catch (Throwable t) {
                continue;
            }
            if (val != null)
                props.put(pName, val);
        }
        for (int i = 0; i < fNames.size(); i++) {
            String fName = (String) fNames.elementAt(i);
            try {
                val = cls.getField(fName).get(obj);
            } catch (Throwable t) {
                continue;
            }
            if (val != null)
                props.put(fName, val);
        }
        return props;
    }

    public static Vector getClassesInArchive(String file, boolean lafs, String[] errors) {
        Vector classes = new Vector();
        try {
            URLClassLoader cl = new URLClassLoader(new URL[] { new File(file)
                    .toURL() });
            ZipFile jfile = new ZipFile(file);
            JarFile jarFile = new JarFile(file);
            Manifest mf = jarFile.getManifest();
            if(mf != null) {
                Map entries = mf.getEntries();
                Iterator it = entries.keySet().iterator();
                while(it.hasNext()) {
                    String classFile = (String) it.next();
                    Attributes atts = (Attributes)entries.get(classFile);
                    Object isBean = atts.getValue("Java-Bean");
                    if(isBean != null && "true".equals(isBean.toString().toLowerCase())) {
//                        System.out.println("GOT BEAN "+classFile);
                        String name = JiglooUtils.replace(classFile, "/", ".");
                        try {
                            if(name.endsWith(".class"))
                                name = name.substring(0, name.length()-6);
                            Class cls = null;
                            try {
                                cls = cl.loadClass(name);
                            } catch(Throwable t) {
                                String err = "Error loading class "+name+", "+t;
                                errors[0] += err+"\n";
                                jiglooPlugin.writeToLog(err);
                            }
                            if(lafs) {
                                if (cls != null &&  Cacher.isAssignableFrom(LookAndFeel.class, cls)) {
                                    classes.add(cls.newInstance());
                                }
                            } else {
                                if(cls != null)
                                    classes.add(cls);
                                else
                                    classes.add(name);
                            }
                        } catch (Throwable t1) {
                            jiglooPlugin.writeToLog("Error loading class "+name+", "+t1);
                        }
                    }
                }
                if(classes.size() > 0) {
                    Collections.sort(classes, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            if(o1 instanceof Class)
                                o1 = ((Class)o1).getName();
                            if(o2 instanceof Class)
                                o2 = ((Class)o2).getName();
                            return ((String)o1).compareToIgnoreCase((String)o2);
                        }
                    });
                    return classes;
                }
            }
            Enumeration en = jfile.entries();
            while (en.hasMoreElements()) {
                ZipEntry jen = (ZipEntry) en.nextElement();
                String name = jen.getName();
                name = name.replace('/', '.');
                if (name.startsWith("."))
                    name = name.substring(1);
                if (name.endsWith(".class") && name.indexOf("$") < 0) {
                    name = name.substring(0, name.length() - 6);
                    try {
                        Class cls = cl.loadClass(name);
                        if(lafs) {
                            if ( Cacher.isAssignableFrom(LookAndFeel.class, cls)) {
                                classes.add(cls.newInstance());
                            }
                        } else {
                            classes.add(cls);
                        }
                    } catch (Throwable t1) {
                        if(!lafs)
                            classes.add(name);
//                        System.err.println("Error loading class "+name+", "+t1);
                    }
                }
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
        Collections.sort(classes, new Comparator() {
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Class)
                    o1 = ((Class)o1).getName();
                else
                	o1 = o1.getClass().getName();
                if(o2 instanceof Class)
                    o2 = ((Class)o2).getName();
                else
                	o2 = o2.getClass().getName();
                return ((String)o1).compareToIgnoreCase((String)o2);
            }
        });
        return classes;
    }

    public static String propertyFromGetter(String methodName) {
        if (methodName.startsWith("get") && methodName.length() > 3) {
            methodName = deCapitalize(methodName.substring(3));
        }
        if (methodName.endsWith("()"))
            methodName = methodName.substring(0, methodName.length() - 2);
        return methodName;
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        if(obj == null)
            return null;
        Field field = null;
        if(obj instanceof ArrayHolder && "length".equals(fieldName))
        	return new Integer(((ArrayHolder)obj).getLength());
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String encodeUnicodeString(String str) {
        byte[] bytes = str.getBytes();
        String enc = "";
        for (int i = 0; i < str.length(); i++) {
            int c = (int) str.charAt(i);
            if (c > 256) {
                String hs = Integer.toHexString(c);
                if (hs.length() == 3)
                    hs = "0" + hs;
                enc += "\\u" + hs;
            } else {
                enc += str.charAt(i);
            }
        }
        return enc;
    }

    public static String getTemplateAsString(String templateName, String NL) {
        DataInputStream strm;
        strm = new DataInputStream(jiglooPlugin.getDefault().getClass()
                .getClassLoader().getResourceAsStream(
                        "com/cloudgarden/jigloo/wizards/templates/"
                                + templateName + ".txt"));
        String cont = "";
        String line;
        try {
            while ((line = strm.readLine()) != null) {
                cont += line + NL;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cont;
    }

    /**
     * Returns true if like.toString() is contained by str
     */
    public static boolean isLike(String str, Object like) {
        if (like == null || str == null)
            return false;
        boolean val = str.toLowerCase().indexOf(like.toString().toLowerCase()) >= 0;
        if (val)
            return true;
        return like.toString().toLowerCase().indexOf(str.toLowerCase()) >= 0;
    }

    public static void centerShell(Shell shell, Shell parent) {
        Point dss = shell.getSize();
        if(parent != null) {
            Rectangle sb = parent.getBounds();
            shell.setLocation(sb.x + (sb.width - dss.x) / 2, sb.y
                    + (sb.height - dss.y) / 2);
        } else {
            Rectangle sb = shell.getDisplay().getBounds();
            shell.setLocation(sb.x + (sb.width - dss.x) / 2, sb.y
                    + (sb.height - dss.y) / 2);
        }
    }
    
    public static void centerShell(Shell shell) {
        Point dss = shell.getSize();
        if(shell.getParent() != null) {
            Rectangle sb = shell.getParent().getShell().getBounds();
            shell.setLocation(sb.x + (sb.width - dss.x) / 2, sb.y
                    + (sb.height - dss.y) / 2);
        } else {
            Rectangle sb = shell.getDisplay().getBounds();
            shell.setLocation(sb.x + (sb.width - dss.x) / 2, sb.y
                    + (sb.height - dss.y) / 2);
        }
    }

    //    private static HashMap origSpecs = new HashMap();
    //    
    //    public static boolean putFormSpecs(FormComponent fc) {
    //        if(origSpecs.containsKey(fc))
    //            return false;
    //        FormLayout fl = (FormLayout) fc.getLayoutManager();
    //        Object[] obs = getFormLayoutDims(fc, false);
    //        int[] widths = (int[])obs[0];
    //        int[] heights = (int[])obs[1];
    //        Object[][] specs = new Object[2][];
    //        specs[0] = new ColumnSpec[fl.getColumnCount()];
    //        specs[1] = new RowSpec[fl.getRowCount()];
    //        for (int i = 0; i < specs[0].length; i++) {
    //            specs[0][i] = fl.getColumnSpec(i+1);
    //        }
    //        for (int i = 0; i < specs[1].length; i++) {
    //            specs[1][i] = fl.getRowSpec(i+1);
    //        }
    //        origSpecs.put(fc, specs);
    //        for (int i = 0; i < widths.length; i++) {
    //            if (widths[i] == 0)
    //                fl.setColumnSpec(i + 1, new ColumnSpec("6px"));
    //        }
    //        for (int i = 0; i < heights.length; i++) {
    //            if (heights[i] == 0)
    //                fl.setRowSpec(i + 1, new RowSpec("6px"));
    //        }
    //        return true;
    //    }
    //    
    //    public static boolean restoreFormSpecs(FormComponent fc) {
    //        if(!origSpecs.containsKey(fc))
    //            return false;
    //        Object lm = fc.getLayoutManager();
    //        if(!(lm instanceof FormLayout))
    //            return false;
    //        FormLayout fl = (FormLayout) lm;
    //        Object[][] specs = (Object[][])origSpecs.get(fc);
    //        for (int i = 0; i < specs[0].length; i++) {
    //            fl.setColumnSpec(i+1, (ColumnSpec)specs[0][i]);
    //        }
    //        for (int i = 0; i < specs[1].length; i++) {
    //            fl.setRowSpec(i+1, (RowSpec)specs[1][i]);
    //        }
    //        fl.layoutContainer((Container)fc.getComponent());
    //        origSpecs.remove(fc);
    //        return true;
    //    }

    /**
     * Returns the rectangle which contains all of the WindowFrames
     * activated on the editor's selected components
     */
    public static Rectangle getSelectedBoundsFromFrames(FormEditor editor) {
        Vector sels = editor.getSelectedComponents();
        Rectangle selBounds = null;
        for(int i=0; i< sels.size(); i++) {
            FormComponent sel = (FormComponent) sels.elementAt(i);
            WindowFrame wf = editor.getWindowFrame(sel, false);
            Rectangle wfb = wf.getBoundsCopy();
            if(selBounds == null)
                selBounds = wfb;
            else
                selBounds.add(wfb);
        }
        return selBounds;
    }
    
    /**
     * Returns the rectangle which contains all of the selected components
     */
    public static Rectangle getSelectedBounds(FormEditor editor) {
        Vector sels = editor.getSelectedComponents();
        Rectangle selBounds = null;
        for(int i=0; i< sels.size(); i++) {
            FormComponent sel = (FormComponent) sels.elementAt(i);
            Rectangle wfb = sel.getBoundsRelativeTo(null);
            if(selBounds == null)
                selBounds = wfb;
            else
                selBounds.add(wfb);
        }
        return selBounds;
    }

    public static Image createImage(Component drawable, Display display) {
        try {
            if (drawable == null)
                return null;
            int MAX_WIDTH = 400;
            int MAX_HEIGHT = 400;
            
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
            
            //            System.out.println("AWTControl createImage " + w + ", " + h);
            if (w == 0 || h == 0)
                return null;
            Dimension dim = drawable.getSize();
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
            
            drawable.validate();
            BufferedImage bim = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            ImageData imd = new ImageData(w, h, 24, palData);
            int[] pix = new int[w * h];
            Graphics g2 = bim.createGraphics();
            drawable.paint(g2);
            PixelGrabber pg = new PixelGrabber(bim, 0, 0, w, h, pix, 0, w);
            int status = ImageObserver.SOMEBITS;
            int cnt = 0;
            while (cnt++ < 20 && status == ImageObserver.SOMEBITS) {
                pg.grabPixels();
                status = pg.getStatus();
            }
            for (int i = 0; i < h; i++) {
                imd.setPixels(0, i, w, pix, i * w);
            }
            return new Image(display, imd);
        } catch (Throwable ex) {
            jiglooPlugin.handleError(ex);
        }
        return null;
    }

    public static Object[] getGridOrFormDimensions(FormComponent fc) {
        try {
        	Insets ins = fc.getInsets();
        	java.awt.Point orig = new java.awt.Point(0, 0);
        	if(ins != null) {
        		orig.y = ins.top;
        		orig.x = ins.left;
        	}
            if (fc == null || (fc.isSWT() && !MigLayoutHandler.handlesLayout(fc)))
                return null;
            String lt = fc.getLayoutType();
            Object lm = fc.getLayoutManager();
            if (fc.usesGridTypeLayout()) {
                if ("GridBag".equals(lt) && lm instanceof GridBagLayout) {
                    GridBagLayout gbl = (GridBagLayout) lm;
                    int[][] dims = gbl.getLayoutDimensions();
                    orig.x = gbl.getLayoutOrigin().x - orig.x;
                    orig.y = gbl.getLayoutOrigin().y - orig.y;
                    return new Object[] { dims[0], dims[1], orig };
//                    return new Object[] { dims[0], dims[1], gbl.getLayoutOrigin() };
                } else if ("Form".equals(lt) && lm instanceof FormLayout) {
                    if(fc.getComponent() == null)
                        return null;
                    FormLayout fl = (FormLayout) lm;
                    Container cont = (Container) fc.getComponent();
                    Object[] obs = new Object[3];
                    FormLayout.LayoutInfo info = fl.getLayoutInfo(cont);
                    obs[0] = originsToDeltas(info.columnOrigins);
                    obs[1] = originsToDeltas(info.rowOrigins);
                    orig.x = info.getX() - orig.x;
                    orig.y = info.getY() - orig.y;
                    obs[2] = orig;
//                    obs[2] = new java.awt.Point(info.getX(), info.getY());
                    return obs;
                    
                } else if ("Table".equals(lt) && lm instanceof TableLayout) {
                    TableLayout tl = (TableLayout) lm;
                    int[] w =  TableLayoutHandler.getColumnWidths(tl, fc);
                    int[] h = TableLayoutHandler.getRowHeights(tl, fc);
                    for(int i=0;i<w.length;i++) {
                        if(i == 0 || i == w.length-1)
                            w[i]+=tl.getHGap()/2;
                        else
                            w[i]+=tl.getHGap();
                    }
                    for(int i=0;i<h.length;i++) {
                        if(i == 0 || i == h.length-1)
                            h[i]+=tl.getVGap()/2;
                        else
                            h[i]+=tl.getVGap();
                    }
                    java.awt.Point tlOrig = TableLayoutHandler.getOrigin(tl);
                    orig.x = tlOrig.x - orig.x;
                    orig.y = tlOrig.y - orig.y;
                    return new Object[] {w, h, orig };

                } else if (MigLayoutHandler.handlesLayout(fc)) {
                	return MigLayoutHandler.getGridDimensions(fc);
                }
            }
        } catch(Throwable t) {
            jiglooPlugin.handleError(t);
        }
        return null;
    }

    private static int[] originsToDeltas(int[] origins) {
        int[] deltas = new int[origins.length - 1];
        for (int i = 0; i < origins.length - 1; i++) {
            deltas[i] = origins[i + 1] - origins[i];
        }
        return deltas;
    }

    private static void adjustSizes(int[] vals) {
        int extra = 0;
        int bigSpace = 0;
        int numBigCols = 0;
        for (int i = 0; i < vals.length; i++) {
            int val = vals[i];
            if (val < 8) {
                extra += (8 - val);
                vals[i] = 8;
            } else if (val > 8) {
                numBigCols++;
                bigSpace += val;
            }
        }
        //		if(extra > 0) {
        //			for (int i = 0; i < vals.length; i++) {
        //				int val = vals[i];
        //				if(val > 8) {
        //					vals[i] -= val*extra/bigSpace;
        //				}
        //			}
        //		}
    }

    public static boolean vectorsDiffer(Vector v1, Vector v2) {
        if (v1.size() != v2.size()) {
            return true;
        } else {
            for (int i = 0; i < v1.size(); i++) {
                Object o = v1.elementAt(i);
                if (!v2.contains(o))
                    return true;
            }
        }
        return false;
    }

    public static Vector selectionToVector(ISelection selection, boolean convertToFCs) {
        Vector comps = new Vector();
        if (selection instanceof StructuredSelection) {
            StructuredSelection ss = (StructuredSelection) selection;
            if (ss == null)
                return comps;
            Object[] sels = ss.toArray();
            if (sels == null || sels.length == 0)
                return comps;
            for (int i = 0; i < sels.length; i++) {
                if(convertToFCs) {
                    if (sels[i] instanceof IAdaptable) {
                        IAdaptable iad = (IAdaptable) sels[i];
                        FormComponent comp = (FormComponent) iad
                        .getAdapter(FormComponent.class);
                        if (comp != null && !comps.contains(comp))
                            comps.add(comp);
                    }
                } else {
                    comps.add(sels[i]);
                }
            }
        }
        return comps;
    }

    public static String toString(Object o, boolean includeBraces) {
        String val = "";
        if (includeBraces)
            val = "[";
        if (o instanceof String[]) {
            String[] darray = (String[]) o;
            for (int i = 0; i < darray.length; i++) {
                if (i != 0)
                    val += ",";
                val += "\"" + darray[i] + "\"";
            }
        } else if (o instanceof double[]) {
            double[] darray = (double[]) o;
            for (int i = 0; i < darray.length; i++) {
                if (i != 0)
                    val += ", ";
                val += darray[i];
            }
        } else if (o instanceof int[]) {
            int[] darray = (int[]) o;
            for (int i = 0; i < darray.length; i++) {
                if (i != 0)
                    val += ", ";
                val += darray[i];
            }
        } else if (o instanceof FormComponent[]) {
            FormComponent[] array = (FormComponent[]) o;
            for (int i = 0; i < array.length; i++) {
                if (i != 0)
                    val += ", ";
                val += ((FormComponent)array[i]).getNameInCode();
            }
        } else if (o != null && o.getClass().isArray()) {
            Object[] array = (Object[]) o;
            if(array.length == 0)
                val += "";
            else
                val += ""+array.length;
        } else {
            if (o == null)
                return "null";
            return o.toString();
        }
        if (includeBraces)
            return val + "]";
        else
            return val;
    }

    private static ICodeFormatter codeFormatter = null;

    public static String formatCode(String code) {
        if (codeFormatter == null)
            codeFormatter = ToolFactory.createCodeFormatter();
        return codeFormatter.format(code, 0, null, "\n");
    }

    public static Method findMethod(Class cls, String methodName, Class[] types) {
//        Method[] meths = cls.getMethods();
        Method[] meths = cls.getDeclaredMethods();
        for (int i = 0; i < meths.length; i++) {
            Method m = meths[i];
            if (m.getName().equals(methodName)) {
                Class[] ts = m.getParameterTypes();
                if (types == null && (ts == null || ts.length == 0)) {
                	m.setAccessible(true);
                    return m;
                }
                boolean ok = true;
                if (ts.length == types.length) {
                    for (int j = 0; j < ts.length; j++) {
                        Class t = ts[j];
                            if (types[j] != null && !(ClassUtils.isAssignableFrom(t, types[j]) 
                                    || ClassUtils.isAssignableFrom( types[j], t))) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                    	m.setAccessible(true);
                        return m;
                    }
                }
            }
        }
        if(cls.getSuperclass() != null)
        	return findMethod(cls.getSuperclass(), methodName, types);
        return null;
    }

    public static int getIntValue(Object value) {
        if (value instanceof String)
            value = new Integer((String) value);
        if (value instanceof Integer)
            return ((Integer) value).intValue();
        return -100;
    }

    public static Object invoke(Object obj, String method, Object[] params) {
        Object ret = null;
        try {
            Class[] types = null;
            if (params != null) {
                types = new Class[params.length];
                for (int i = 0; i < types.length; i++) {
                    if(params[i] == null)
                        continue;
                    types[i] = params[i].getClass();
                    if (types[i].equals(Integer.class))
                        types[i] = int.class;
                    if (types[i].equals(Float.class))
                        types[i] = float.class;
                    if (types[i].equals(Double.class))
                        types[i] = double.class;
                    if (types[i].equals(Boolean.class))
                        types[i] = boolean.class;
                }
            }
            Method m = Cacher.getMethod(obj.getClass(), method, types);
            return m.invoke(obj, params);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return ret;
    }

    public static void initFormDataWrapper(LayoutDataWrapper ldw, int x, int y,
            FormComponent parent) {
        Rectangle pb = parent.getBounds();
        ldw.setPropertyValue("top", new FormAttachment(0, y), false);
        ldw.setPropertyValue("left", new FormAttachment(0, x), false);
    }

    public static Image getSWTImage(java.awt.Image image) {
        try {
            int w = -1;
            int h = -1;
            int cnt = 0;
            while (cnt++ < 10 && w == -1) {
                w = image.getWidth(null);
                h = image.getHeight(null);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                }
            }
            if (w == -1)
                return null;
            BufferedImage bim = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_ARGB);
            ImageData imd = new ImageData(w, h, 24, palData);
            ImageData mask = new ImageData(w, h, 1, palData);
            int[] pix = new int[w * h];
            byte[] alp = new byte[w * h];
            Graphics g2 = bim.createGraphics();
            g2.drawImage(image, 0, 0, null);
            PixelGrabber pg = new PixelGrabber(bim, 0, 0, w, h, pix, 0, w);
            int status = ImageObserver.SOMEBITS;
            cnt = 0;
            try {
                while (cnt++ < 20 && status == ImageObserver.SOMEBITS) {
                    pg.grabPixels();
                    status = pg.getStatus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            for (int i = 0; i < alp.length; i++) {
                alp[i] = (byte) (pix[i] >> 24);
            }

            for (int i = 0; i < h; i++) {
                imd.setPixels(0, i, w, pix, i * w);
                mask.setPixels(0, i, w, alp, i * w);
            }

            return new Image(Display.getDefault(), imd, mask);
        } catch (Exception e) {
            jiglooPlugin.handleError(e);
        }
        return null;
    }

    public static String capitalize(String str) {
        if (str.length() <= 1)
            return str.toUpperCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String deCapitalize(String str) {
        if (str.length() <= 1)
            return str.toLowerCase();
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * Pushes string q on to the end of string array q, returning the resulting
     * string array.
     */
    public static String[] push(String[] p, String q) {
        int len = 0;
        if (p != null)
            len = p.length;
        String[] tmp = new String[len + 1];
        if (len != 0)
            System.arraycopy(p, 0, tmp, 0, len);
        tmp[len] = q;
        return tmp;
    }

    /**
     * Splits String s at all occurences of the String 'split' and returns an
     * array of the pieces. If s starts with 'split' then the first element of
     * the returned array will be an empty String.
     */
    public static String[] split(String split, String s) {
        return split(split, s, false);
    }

    public static String[] split(String split, String s, boolean allowEmpties) {
        String[] ret = null;
        int ind, spl = split.length();
        while (s != null && (ind = s.indexOf(split)) >= 0) {
            if (allowEmpties || ind != 0)
                ret = push(ret, s.substring(0, ind));
            s = s.substring(ind + spl);
        }
        if (s != null)
            ret = push(ret, s);
        return ret;
    }

    public static boolean diffParents(FormComponent fc1, FormComponent fc2) {
        if (fc1 == null || fc2 == null)
            return true;
        FormComponent par1 = fc1.getParent();
        FormComponent par2 = fc2.getParent();
        return (par1 == null && par2 != null) || (par1 != null && par2 == null)
                || (par1 != null && !par1.equals(par2));
    }

    public static int getIntProperty(IPropertySource psrc, String propName) {
        Object val = psrc.getPropertyValue(propName);
        if (val == null)
            return 0;
        if (val instanceof FieldWrapper) {
            val = ((FieldWrapper) val).getValue();
        }
        if (val instanceof Integer) {
            return ((Integer) val).intValue();
        } else {
            System.err.println("getIntProperty - property " + propName
                    + " is a " + val.getClass() + ", " + val);
            return 0;
        }
    }

    public static boolean isJava13() {
        return System.getProperty("java.version").startsWith("1.3");
    }

    public static String getStringProperty(IPropertySource psrc, String propName) {
        return psrc.getPropertyValue(propName).toString();
    }

    public static double getDoubleValue(Object value) {
        if (value instanceof String)
            value = new Double((String) value);
        if (value instanceof Double)
            return ((Double) value).doubleValue();
        return -100.0;
    }

    public static boolean getBoolValue(Object value) {
        if (value instanceof String)
            value = new Boolean((String) value);
        if (value instanceof Boolean)
            return ((Boolean) value).booleanValue();
        return false;
    }

    public static String getShortClassName(Class clazz) {
        String name = clazz.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getShortLayoutName(Class clazz) {
        String name = getShortClassName(clazz);
        if (name.endsWith("Layout"))
            name = name.substring(0, name.length() - 6);
        return name;
    }

    public static int getStartMatchLength(String str1, String str2) {
        StringBuffer match = new StringBuffer(str1.substring(0, 1));
        int i = 1;
        while (str2.startsWith(match.toString())) {
            match.append(str1.charAt(i++));
        }
        return i - 1;
    }

    public static String getProjectBaseForEditor(FormEditor editor) {
        return getOSFileName(editor.getJavaFile().getProject().getLocation()
                .toOSString());
    }

    public static String getProjectBase(IProject proj) {
        return proj.getLocation().toOSString();
    }

    public static String getIconBaseForEditor(FormEditor editor) {
    	String cont = getContainerForEditor(editor);
    	String res = cont+File.separator+"resources";
    	String icons = res+File.separator+"icons";
    	if (new File(icons).exists())
    		return getOSFileName(icons);
    	if (new File(res).exists())
    		return getOSFileName(res);
    	
    	if(editor.isPartOfAppFrameworkApplication())
    		return getOSFileName(cont);
    	
    	String src = getSourceBaseForEditor(editor);
    	icons = src + File.separator + "icons";
    	if (new File(icons).exists())
    		return getOSFileName(icons);
    	return getOSFileName(src);
    }

    public static IContainer getSourceContainer(FormEditor editor) {
        try {
            IFile ei = editor.getJavaFile();
            IJavaElement jel = JavaCore.create(ei);
            while (jel.getElementType() != IJavaElement.PACKAGE_FRAGMENT_ROOT) {
                jel = jel.getParent();
            }
            IPackageFragmentRoot pfr = (IPackageFragmentRoot) jel;
            IResource res = pfr.getCorrespondingResource();
            if (res instanceof IContainer)
                return (IContainer) res;
        } catch (Exception e) {
            jiglooPlugin.handleError(e);
        }
        return null;
    }

    public static boolean isSourceFolder(IFolder fol, IJavaProject proj) {
        try {
            IPackageFragmentRoot pfr = proj.findPackageFragmentRoot(fol
                    .getFullPath());
            if (pfr != null)
                return true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    public static String getPackageName(IFolder fol, IJavaProject proj) {
        String pkg = fol.getName();
        while (fol.getParent() instanceof IFolder) {
            fol = (IFolder) fol.getParent();
            if (isSourceFolder(fol, proj))
                break;
            pkg = fol.getName() + "." + pkg;
        }
        return pkg;
    }

    public static String getContainerForEditor(FormEditor editor) {
        IFile ei = editor.getJavaFile();
        IJavaElement jel = JavaCore.create(ei).getParent();
        try {
            IPath projPath = null;
            IResource res = jel.getCorrespondingResource();
            if (res instanceof IProject) {
                projPath = ((IProject) res).getLocation();
            } else if (res instanceof IFolder) {
                projPath = ((IFolder) res).getLocation();
            }
            String proj = projPath.toOSString();
            if (new File(proj).exists())
                return getOSFileName(proj);
            String src = proj + File.separator
                    + jel.getResource().getProjectRelativePath().toOSString();
            if (new File(src).exists())
                return getOSFileName(src);
            return getOSFileName(proj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSourceBaseForEditor(FormEditor editor) {
        IFile ei = editor.getJavaFile();
        IJavaElement jel = JavaCore.create(ei);
        while (jel.getElementType() != IJavaElement.PACKAGE_FRAGMENT_ROOT) {
            jel = jel.getParent();
        }
        IPackageFragmentRoot pfr = (IPackageFragmentRoot) jel;
        try {
            IPath projPath = null;
            IResource res = pfr.getCorrespondingResource();
            if (res instanceof IProject) {
                projPath = ((IProject) res).getLocation();
            } else if (res instanceof IFolder) {
                projPath = ((IFolder) res).getLocation();
            }
            String proj = projPath.toOSString();
            if (new File(proj).exists())
                return getOSFileName(proj);
            String src = proj + File.separator
                    + pfr.getResource().getProjectRelativePath().toOSString();
            if (new File(src).exists())
                return getOSFileName(src);
            return getOSFileName(proj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getOSFileName(String fileName) {
        if (fileName == null)
            return null;
        fileName = replaceRetry(fileName, "//", "/");
        fileName = replaceRetry(fileName, "\\\\", "\\");
        fileName = replace(fileName, "/", File.separator);
        fileName = replace(fileName, "\\", File.separator);
        //System.out.println("GOT OS FN "+fileName);
        return fileName;
    }

    public static String trim(String str) {
        while (str.startsWith(" ") || str.startsWith("\t") || str.startsWith("\n") || str.startsWith("\r"))
            str = str.substring(1);
        while (str.endsWith(" ") || str.endsWith("\t") || str.endsWith("\n") || str.endsWith("\r"))
            str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * Replaces all occurences of String a with String b in the String s.
     */
    public static String replace(String s, String a, String b) {
        if (s == null)
            return s;
        int m, n = a.length();
        String tmp = s;
        StringBuffer ret = new StringBuffer("");
        while ((m = tmp.indexOf(a)) >= 0) {
            ret.append(tmp.substring(0, m));
            ret.append(b);
            tmp = tmp.substring(m + n);
        }
        ret.append(tmp);
        return ret.toString();
    }

    public static String replaceRetry(String s, String a, String b) {
        while ((s = replace(s, a, b)).indexOf(a) >= 0)
            ;
        return s;
    }

    public static ISelection getPackageExplorerSelection() {
        IViewPart pe = getPackageExplorer();
        if (pe == null || pe.getSite() == null
                || pe.getSite().getSelectionProvider() == null)
            return null;
        return pe.getSite().getSelectionProvider().getSelection();
    }

    public static IViewPart getPackageExplorer() {
        try {
            IWorkbenchPage ap = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage();
            if (ap == null)
                return null;
            IViewReference[] refs = ap.getViewReferences();
            for (int i = 0; i < refs.length; i++) {
                if (refs[i].getId() != null
                        && refs[i].getId().equals("org.eclipse.jdt.ui.PackageExplorer")) {
                    IViewPart pe = refs[i].getView(true);
                    if (pe == null)
                        return null;
                    return pe;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean setPackageExplorerFilter() {
        try {
            //System.err.println("setPackageExplorerFilter");
            IViewPart pe = getPackageExplorer();
            if (pe == null)
                return false;
            TreeViewer tv = ((IPackagesViewPart) pe).getTreeViewer();
            if (tv == null || tv.getSorter() instanceof FormSorter)
                return false;
            FormSorter jes = new FormSorter();
            tv.setSorter(jes);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getFontString(Font font) {
        if (font == null || font.getFontData() == null
                || font.getFontData().length == 0)
            return "";
        return font.getFontData()[0].toString();
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null && o2 == null)
            return true;
        if (o1 == null && o2 != null)
            return false;
        if (o1 != null && o2 == null)
            return false;
        if (!o1.getClass().equals(o2.getClass()))
            return false;
        if (o1.equals(o2))
            return true;
        if (o1 instanceof Font) {
            if (!(o2 instanceof Font))
                return false;
            return getFontString((Font) o1).equals(getFontString((Font) o2));
        }
        if (o1 instanceof Color) {
            if (!(o2 instanceof Color))
                return false;
            return o1.toString().equals(o2.toString());
        }
        if (o1 instanceof Image) {
            if (!(o2 instanceof Image))
                return false;
            return o1.toString().equals(o2.toString());
        }
        return false;
    }

    private static Comparator fcCompAsc = new Comparator() {
        public int compare(Object o1, Object o2) {
            FormComponent fc1 = (FormComponent) o1;
            FormComponent fc2 = (FormComponent) o2;
            return fc2.getIndexInParent() - fc1.getIndexInParent();
        }
    };
    private static Comparator fcCompDesc = new Comparator() {
        public int compare(Object o1, Object o2) {
            FormComponent fc1 = (FormComponent) o1;
            FormComponent fc2 = (FormComponent) o2;
            return fc1.getIndexInParent() - fc2.getIndexInParent();
        }
    };

    /**
     * Sort the components vector according to indexInParent. This is done
     * before moving components.
     */
    public static void sortComponents(Vector components, boolean asc) {
        if (asc)
            Collections.sort(components, fcCompAsc);
        else
            Collections.sort(components, fcCompDesc);
    }

    /**
     * Sort the components vector according to indexInParent. This is done
     * before moving components.
     */
    public static void sortComponents(FormComponent[] components, boolean asc) {
        if (asc)
            Arrays.sort(components, fcCompAsc);
        else
            Arrays.sort(components, fcCompDesc);
    }

    public static String getQualifier(String fieldType) {
        int pos = fieldType.lastIndexOf(".");
        if (pos < 0)
            return null;
        return fieldType.substring(0, pos);
    }

    public static String getUnqualifiedName(String fieldType) {
        int pos = fieldType.lastIndexOf(".");
        return fieldType.substring(pos + 1);
    }

    public static boolean isValidTypeForElement(String type) {
        if (type.equals("int") 
        		|| type.equals("double") 
        		|| type.equals("float")
                || type.equals("boolean") 
                || type.equals("long") )
            return false;
        return true;
    }

    public static String chop(String str) {
        if (str.length() == 0)
            return str;
        return str.substring(0, str.length() - 1);
    }

    public static boolean isInteger(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static void invokeSwing(final Runnable r) {
        try {
        	//invokeSwing causes crashes on Mac!!!
            if(true || SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else if(Display.getCurrent() != null) {
            	SwingUtilities.invokeAndWait(r);
            } else {
                SwingUtilities.invokeAndWait(r);
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        }
    }
    
    public static String getImplementor(Method m) {
        String code = "\t";
        int mods = m.getModifiers();
        if ((mods & Modifier.PUBLIC) != 0)
            code += "public ";
        if ((mods & Modifier.PROTECTED) != 0)
            code += "protected ";
        if ((mods & Modifier.PRIVATE) != 0)
            code += "private ";
        Class ret = m.getReturnType();
        if (ret != null) {
            String rn = ret.getName();
            if (rn.startsWith("java.lang."))
                rn = rn.substring("java.lang.".length());
            code += rn + " ";
        } else {
            code += "void ";
        }
        code += m.getName() + "(";
        Class[] pts = m.getParameterTypes();
        for (int i = 0; i < pts.length; i++) {
            if (i != 0)
                code += ", ";
            Class pt = pts[i];
            code += pt.getName() + " arg" + i;
        }
        code += ") ";
        Class[] ex = m.getExceptionTypes();
        for (int i = 0; i < ex.length; i++) {
            if (i != 0)
                code += ", ";
            else
                code += "throws ";
            Class pt = pts[i];
            code += pt.getName();
        }
        code += " {\n\t\t//TODO add code for " + m.getName() + " here\n";
        if (ret != null) {

            if (ret.equals(int.class) || ret.equals(long.class)
                    || ret.equals(float.class) || ret.equals(double.class)) {
                code += "\t\treturn 0;\n";
            } else if (ret.equals(void.class)) {
                //do nothing! Yes, void is a class
            } else if (ret.equals(boolean.class)) {
                code += "\t\treturn false;\n";
            } else {
                code += "\t\treturn null;\n";
            }
        }
        code += "\t}\n\n";
        return code;
    }

    public static Vector getAbstractMethods(Class cls) {
        Vector v = null;
        Method[] meths = cls.getMethods();
        if (meths == null || meths.length == 0)
            return null;
        for (int i = 0; i < meths.length; i++) {
            Method m = meths[i];
            if ((m.getModifiers() & Modifier.ABSTRACT) != 0) {
                if (v == null)
                    v = new Vector();
                boolean match = false;
                for (int vi = 0; vi < v.size(); vi++) {
                    Method vm = (Method) v.elementAt(vi);
                    if (vm.getName().equals(m.getName())) {
                        Class[] vmt = vm.getParameterTypes();
                        Class[] mt = m.getParameterTypes();
                        if (mt == null && vmt == null) {
                            match = true;
                            break;
                        } else if (mt != null && vmt != null
                                && mt.length == vmt.length) {
                            for (int j = 0; j < mt.length; j++) {
                                if (!mt[j].equals(vmt[j]))
                                    break;
                            }
                            match = true;
                            break;
                        }
                    }
                    if (match)
                        break;
                }
                if (!match)
                    v.add(m);
            }
        }
        return v;
    }

    public static void printStackTrace() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new Exception().printStackTrace(new PrintStream(out));
        String str = out.toString();
        if (str.length() > 700)
            str = str.substring(0, 700) + "...";
        System.err.println(str);
    }

    public static boolean hasGUIBuilderInstanceMethod(Class cls, boolean swing) {
        if (swing) {
            try {
                cls.getMethod("getGUIBuilderInstance", null);
                return true;
            } catch (Throwable t) {
            }
        } else {
            try {
                cls.getMethod("getGUIBuilderInstance", new Class[] {
                        Composite.class, int.class });
                return true;
            } catch (Throwable t) {
            }
        }
        return false;
    }

    public static String resolveBasicParentName(IType type) {
        if (type == null)
            return null;
        String qn = type.getFullyQualifiedName();
        IJavaProject proj = type.getJavaProject();
        try {
            while (qn != null) {
                if (qn.startsWith("javax.swing"))
                    return JiglooUtils.getUnqualifiedName(qn);
                if (qn.startsWith("org.eclipse.swt.widget"))
                    return JiglooUtils.getUnqualifiedName(qn);
                if (qn.startsWith("org.eclipse.swt.custom"))
                    return JiglooUtils.getUnqualifiedName(qn);
                String scn = type.getSuperclassName();
                if (scn == null)
                    return null;
                if (scn.indexOf(".") < 0) {
                    String[][] fqn = type.resolveType(scn);
                    if (fqn != null && fqn.length > 0)
                        scn = fqn[0][0] + "." + fqn[0][1];
                }
                type = proj.findType(scn);
                if (type != null)
                    qn = type.getFullyQualifiedName();
                else
                    qn = null;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static boolean isValidJavaName(String str) {
        if (str == null)
            return false;
        if (!Character.isJavaIdentifierStart(str.charAt(0)))
            return false;
        for (int i = 1; i < str.length(); i++) {
            if (!Character.isJavaIdentifierPart(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static String switchThisAndSuper(String name) {
        if (name.startsWith("super."))
            name = "this." + name.substring(6);
        else if (name.startsWith("this."))
            name = "super." + name.substring(5);
        else
            name = "this." + name;
        return name;
    }

    public static void displayBranch(Control comp) {
        displayBranch(comp, "");
    }

    public static void displayBranch(Control comp, String indent) {
        System.err.println(indent + "DISPLAY BRANCH: " + comp + ", "
                + comp.hashCode());
        Object ld = comp.getLayoutData();
        System.err.println(indent + "LayoutData=" + ld);
        System.err.println(indent + "Size=" + comp.getSize());
        System.err.println(indent + "Bounds=" + comp.getBounds());
        if (ld instanceof GridLayout) {
            GridData gd = (GridData) ld;
            System.err.println(indent + "GRIDDATA " + gd.horizontalSpan + ", "
                    + gd.verticalSpan);
        }
        if (comp instanceof Composite) {
            Layout lo = ((Composite) comp).getLayout();
            System.err.println(indent + "Layout=" + lo);
            if (lo instanceof GridLayout) {
                GridLayout gl = (GridLayout) lo;
                System.err.println(indent + "GRID LO, cols = " + gl.numColumns);
            }
            for (int i = 0; i < ((Composite) comp).getChildren().length; i++) {
                System.err.println(indent + "CHILD " + i + ":");
                displayBranch(((Composite) comp).getChildren()[i], indent
                        + "   ");
            }
        }
    }

    /**
     * @param code
     * @param oldCode
     * @return
     */
    public static boolean equalsIgnoreWhiteSpace(String code, String oldCode) {
        if (code == null || oldCode == null)
            return false;
        code = replace(code, "\t", "");
        code = replace(code, " ", "");
        code = replace(code, "\n", "");
        code = replace(code, "\r", "");
        oldCode = replace(oldCode, "\t", "");
        oldCode = replace(oldCode, " ", "");
        oldCode = replace(oldCode, "\n", "");
        oldCode = replace(oldCode, "\r", "");
        return code.equals(oldCode);
    }

    /**
     * @param constr
     * @param params
     */
    public static void checkConstructor(Constructor constr, Object[] params) {
        int pl = 0;
        if (params != null)
            pl = params.length;
        int pl2 = 0;
        if (constr != null && constr.getParameterTypes() != null)
            pl2 = constr.getParameterTypes().length;
        if (pl != pl2)
            throw new RuntimeException(
                    "Parameter number does not match constructor");
    }

    public static String getStackTrace(Throwable t) {
        return getStackTrace(t, 1200);
    }
    
    public static String getStackTrace(Throwable t, int limit) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(os);
        t.printStackTrace(pw);
        pw.flush();
        pw.close();
        try {
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        String ss = os.toString();
        if(limit > 0 && ss.length() > limit)
            ss = ss.substring(0, limit-1)+" ...";
        return ss;
    }

    public static int getTabSize() {
        String tabSizeStr = JavaCore.getOption(JavaCore.FORMATTER_TAB_SIZE);
        int tabSize = 4;
        try {
            tabSize = Integer.parseInt(tabSizeStr);
        } catch (Throwable e) {
        }
        if (tabSize <= 0)
            tabSize = 1;
        return tabSize;
    }

    public static boolean spacesForTabs() {
        String tabChar = JavaCore.getOption(JavaCore.FORMATTER_TAB_CHAR);
        return "space".equals(tabChar);
    }

    private static String tabString = null;

    public static String getTabString() {
        if (tabString != null)
            return tabString;
        if (!spacesForTabs()) {
            tabString = "\t";
        } else {
            String str = "";
            int num = JiglooUtils.getTabSize();
            for (int i = 0; i < num; i++)
                str += " ";
            tabString = str;
        }
        return tabString;
    }

    public static String getFileAsString(File plog) {
        String contents = "";
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(plog)));
            int numLines = 0;
            while (numLines++ < 50 && dis.available() > 0) {
                contents += dis.readLine() + "\n";
            }
        } catch (Throwable t) {
            jiglooPlugin.handleError(t);
        } finally {
        	try {
        		if(dis != null)
        			dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return contents;
    }

    /**
     * @param frame
     */
    public static void centerWindow(java.awt.Window frame, Shell parent) {
        Dimension dss = frame.getSize();
        Rectangle sb = parent.getBounds();
        frame.setLocation(sb.x + (sb.width - dss.width) / 2, sb.y
                + (sb.height - dss.height) / 2);
    }
    
    /**
     * @param clsName
     * @return
     */
    public static String unconvertUnavailableClassName(String clsName) {
    	clsName = GroupLayoutUtils.unconvertUnavailableClassName(clsName); 
        if(clsName.equals("com.cloudgarden.jigloo.actions.ActionStub")) {
            clsName =  "javax.swing.AbstractAction";
        }
    	return clsName;
    }
        
    /**
     * @param obj -object to copy - currently supports swt Rectangle
     * @return
     */
    public static Object copy(Object obj) {
        if(obj instanceof Rectangle) {
            Rectangle r = (Rectangle)obj;
            return new Rectangle(r.x, r.y, r.width, r.height);
        } else if(obj instanceof Vector) {
            return (Vector)((Vector)obj).clone();
        }
        return obj;
    }

    /**
     * returns the rectangle bounding the passed-in Vector of FormComponents,
     * excluding any inherited ones.
     */
	public static Rectangle getBoundingRectangle(Vector fcs) {
	    int x = 100000, y=100000, x2=0, y2=0;
	    for(int i=0;i<fcs.size();i++) {
	        FormComponent fc = (FormComponent)fcs.elementAt(i);
//	        if(fc.isInherited())
//	        	continue;
	        if(!fc.isVisual())
	        	continue;
	        Rectangle b = fc.getBoundsRelativeToRoot();
	        if(b.x == -10 && b.y == -10 
	        		&& fc.getRawPropertyValue("bounds") != null)
	        	b = (Rectangle) fc.getRawPropertyValue("bounds");
	        if(b.x < x)
	            x = b.x;
	        if(b.y < y)
	            y = b.y;
	        if(b.x+b.width > x2)
	            x2 = b.x+b.width;
	        if(b.y+b.height > y2)
	            y2 = b.y+b.height;
	    }
	    if(x2 == 0 || y2 == 0)
	        return new Rectangle(x, y, 0, 0);
	    return new Rectangle(x, y, x2-x, y2-y);
	}

    public static FormComponent getFormComponentWithObject(Object obj, HashMap comps) {
        if (obj == null)
            return null;
        Iterator it = comps.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object f = comps.get(key);
            if (f instanceof FormComponent) {
                FormComponent fc = (FormComponent) f;
                if (obj.equals(fc.getObject(false)))
                    return fc;
            }
        }
        return null;
    }

	public static void centerShellOnCursor(Shell shell) {
		Point pt = Display.getCurrent().getCursorLocation();
		Rectangle sb = Display.getCurrent().getBounds();
		Rectangle b = shell.getBounds();
		int x = pt.x-b.width/2;
		if(x < 0)
			x = 0;
		if(x + b.width > sb.width)
			x = sb.width - b.width;
		
		int y =  pt.y-b.height/2;
		if(y < 0)
			y = 0;
		if(y + b.height > sb.height)
			y = sb.height - b.height;
		
		shell.setLocation(x,y);
	}

	private static long timeMarker;
	
	public static void setTimeMarker() {
		timeMarker = System.currentTimeMillis();
	}

	public static void printTimeLapse(String msg) {
		long now = System.currentTimeMillis();
		System.out.println(((now-timeMarker)/1000.0)+"s : "+msg);
		timeMarker = now;
	}
	
	
	private static IProject classProject;
	private static IProject candidateProject;
	
    /**
     * Tries to return an open project, but if the only project found is closed, return that
     * @param className
     */
    public static IProject getProjectForClass(String className) {
    	classProject = null;
    	candidateProject = null;
        SearchEngine se = new SearchEngine();
        IJavaSearchResultCollector col = new IJavaSearchResultCollector() {
            public void aboutToStart() {
            }

            public void accept(IResource resource, int start, int end,
                    IJavaElement ee, int accuracy) throws CoreException {
                if (resource != null && resource.getProject() != null)
                    candidateProject = resource.getProject();
                else if (ee != null && ee.getJavaProject() != null
                        && ee.getJavaProject().getProject() != null)
                	candidateProject = ee.getJavaProject().getProject();
                if(candidateProject != null && candidateProject.isOpen())
                	classProject = candidateProject;
            }

            public void done() {
            }

            public IProgressMonitor getProgressMonitor() {
                return null;
            }
        };
        try {
            se.search(ResourcesPlugin.getWorkspace(), className,
                    IJavaSearchConstants.TYPE,
                    IJavaSearchConstants.DECLARATIONS, SearchEngine
                            .createWorkspaceScope(), col);
        } catch (JavaModelException e) {
            jiglooPlugin.handleError(e);
        }
        if(classProject == null)
        	classProject = candidateProject;
        return classProject;
    }

	/**
	 * 
	 */
	public static void addToCustomClasses(String className, int mode ) {
		String classes = jiglooPlugin.getDefault().getStringPreference(
		        MainPreferencePage.P_PALETTE_CLASSES);
		
		className = unconvertUnavailableClassName(className);
		
		if(classes.indexOf(
		        PaletteComposite.PREF_SEP_1
		        +className
		        +PaletteComposite.PREF_SEP_1) >= 0)
		    return;
		
		String[][][] prefs = ComponentPalette.getPaletteClasses(null);
		String[] palettes = null;
		String type = null;
		if (mode == FormEditor.MODE_AWT_SWING) {
			type = "1";
	        palettes = prefs[0][0];
	    } else if (mode == FormEditor.MODE_SWT) {
			type = "2";
	        palettes = prefs[0][1];
	    } else if (mode == FormEditor.MODE_ANDROID) {
			type = "3";
	        palettes = prefs[0][2];
	    }
	    int index = 0;
	    for (int i = 0; i < palettes.length; i++) {
	        if(palettes[i] != null 
	                && palettes[i].toLowerCase().indexOf("custom") >= 0)
	            index = i;
	    }
	    classes+= PaletteComposite.PREF_SEP_2+
	    palettes[index]+
	    PaletteComposite.PREF_SEP_1+type+
	    PaletteComposite.PREF_SEP_1+className+
	    PaletteComposite.PREF_SEP_1+"0"+
	    PaletteComposite.PREF_SEP_1+
	    PaletteComposite.PREF_SEP_1+"y"+
	    PaletteComposite.PREF_SEP_1;
		jiglooPlugin.getDefault().setPreference(MainPreferencePage.P_PALETTE_CLASSES, classes);
	}

	public static void stopAWT() {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Method meth;
			//    	meth = toolkit.getClass().getDeclaredMethod("quitSecondaryEventLoop", null);
			//    	meth.setAccessible(true);
			//    	meth.invoke(toolkit, null);
			meth = toolkit.getClass().getDeclaredMethod("shutdown", null);
			meth.setAccessible(true);
			meth.invoke(toolkit, null);
//			meth = toolkit.getClass().getDeclaredMethod("finalize", null);
//			meth.setAccessible(true);
//			meth.invoke(toolkit, null);
		} catch(Throwable t) {
			t.printStackTrace();
		}

	}

	public static String getClassName(IClassFile classFile) {
    	String className = "";
    	IJavaProject jproj = classFile.getJavaProject();
    	try {
			IPath opLoc = jproj.getOutputLocation();
			String[] rootLoc = opLoc.segments();
			String[] classSegs = classFile.getPath().segments();
			int pos = 0;
			while(pos < rootLoc.length && rootLoc[pos].equals(classSegs[pos])) {
				pos++;
			}
			if(pos != rootLoc.length)
				return null;
			for (int i = pos; i < classSegs.length; i++) {
				if(i != pos)
					className += ".";
				className += classSegs[i];
			}
	    	if(className.endsWith(".class"))
	    		className = className.substring(0, className.length()-6);
	    	return className;
    	} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}
}