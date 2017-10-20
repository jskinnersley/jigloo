package com.cloudgarden.jigloo.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.WorkbenchPart;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.eval.ArrayHolder;
import com.cloudgarden.jigloo.eval.ConstructorHolder;
import com.cloudgarden.jigloo.eval.RootMethodCall;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.wrappers.ClassWrapper;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;
import com.jgoodies.forms.builder.AbstractFormBuilder;

public class ClassUtils {

    static Vector extraVisualClasses = new Vector();
    static Vector visualClasses = new Vector();
    static Vector nonVisualClasses = new Vector();
    
    public static void addVisualClass(Class cls) {
    	if(!extraVisualClasses.contains(cls))
    		extraVisualClasses.add(cls);
    }
    
	public static boolean isVisual(Class cls) {
	    //if cls is null assume we are adding a custom component
	    if (cls == null)
	        return false;
	    if(visualClasses.contains(cls.getName()))
	        return true;
	    if(nonVisualClasses.contains(cls.getName()))
	        return false;
	    boolean vis = 
	    	ClassUtils.isSWTWidget(cls)
	    	|| ClassUtils.isJComponent(cls) 
	    	|| ClassUtils.isJFace(cls)
	    	|| ClassUtils.isCWTWidget(cls)
	    	|| ClassUtils.isCWTApplicationModel(cls)
	    	|| ClassUtils.isBuilder(cls);
	    if(!vis) {
	    	for(int i=0; i<extraVisualClasses.size();i++) {
	    		Class ecls = (Class) extraVisualClasses.get(i);
	    		if(ecls.isAssignableFrom(cls)) {
	    			vis = true;
	    			break;
	    		}
	    	}
	    }
	    if(vis) {
	        visualClasses.add(cls.getName());
	    } else {
	        nonVisualClasses.add(cls.getName());
	    }
	    return vis;
	    //an RCP class is *not* visual - it is the "parent" composite that is
	    // visual
	}

	static boolean isBuilder(Class cls) {
	    if (!jiglooPlugin.canUseSwing())
	        return false;
	    return  Cacher.isAssignableFrom(AbstractFormBuilder.class, cls);
	}

	public static boolean isJFace(Class cls) {
	    return  Cacher.isAssignableFrom(Window.class, cls)
	            ||  Cacher.isAssignableFrom(Viewer.class, cls);
	}

	public static boolean isRCP(Class cls) {
	        return  Cacher.isAssignableFrom(WorkbenchPart.class, cls);
	//        return  Cacher.isAssignableFrom(ViewPart.class, cls);
	    }

	public static boolean isCWTApplicationModel(Class cls) {
	    try {
	        Class cwtWid = Class
	                .forName("com.philemonworks.typewise.server.ApplicationModel");
	        if (cwtWid == null)
	            return false;
	        return  Cacher.isAssignableFrom(cwtWid, cls);
	    } catch (Throwable e) {
	        return false;
	    }
	}

	public static boolean isSWTWidget(Class cls) {
	    return  Cacher.isAssignableFrom(Widget.class, cls)
	    //||  Cacher.isAssignableFrom(Display.class, cls)
	            ||  Cacher.isAssignableFrom(Dialog.class, cls);
	    //        return isAssignableFrom2(Widget.class, cls)
	    //                || isAssignableFrom2(Dialog.class, cls);
	}

	public static boolean isAWT(Class cls) {
	    if (!jiglooPlugin.canUseSwing())
	        return false;
	    return cls.getName().indexOf("java.awt") == 0;
	}

	public static boolean isJComponent(Class cls) {
	    if (!jiglooPlugin.canUseSwing())
	        return false;
	    return  Cacher.isAssignableFrom(Component.class, cls)
	            ||  Cacher.isAssignableFrom(java.awt.Window.class, cls);
	}

	public static boolean isAbstract(Class cls) {
	    try {
	    	return Modifier.isAbstract(cls.getModifiers());
	    	
	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	    return false;
	}

	public static boolean isBorder(Class cls) {
	    if (cls == null)
	        return false;
	    if (jiglooPlugin.canUseSwing() && ClassUtils.isSubclassOf(cls, Border.class))
	        return true;
	    return false;
	}

	static boolean isSubclassOf(Class cls1, Class cls2) {
	    return  Cacher.isAssignableFrom(cls2, cls1);
	}

	public static boolean isColor(Class cls) {
	    if (cls == null)
	        return false;
	    if (isSubclassOf(cls, Color.class))
	        return true;
	    if (jiglooPlugin.canUseSwing()
	            && isSubclassOf(cls, java.awt.Color.class))
	        return true;
	    return false;
	}

	public static boolean isFont(Class cls) {
	    if (cls == null)
	        return false;
	    if (isSubclassOf(cls, Font.class))
	        return true;
	    if (jiglooPlugin.canUseSwing()
	            && isSubclassOf(cls, java.awt.Font.class))
	        return true;
	    return false;
	}

	public static boolean isImage(Class cls) {
	    if (cls == null)
	        return false;
	    if (isSubclassOf(cls, Image.class))
	        return true;
	    if (jiglooPlugin.canUseSwing()
	            && isSubclassOf(cls, java.awt.Image.class))
	        return true;
	    return false;
	}

	public static boolean isIcon(Class cls) {
	    if (cls == null)
	        return false;
	    if (jiglooPlugin.canUseSwing()
	            && isSubclassOf(cls, javax.swing.Icon.class))
	        return true;
	    return false;
	}

	public static boolean superclassMatches(Class cls, String prefix) {
	    while (cls != null) {
	        if (cls.getName().equals(prefix))
	            return true;
	        cls = cls.getSuperclass();
	    }
	    return false;
	}

	public static boolean superclassAlmostMatches(Class cls, String prefix) {
	    if (superclassMatches(cls, prefix))
	        return true;
	    Class[] ints = cls.getInterfaces();
	    for (int i = 0; i < ints.length; i++) {
	        if (superclassAlmostMatches(ints[i], prefix))
	            return true;
	    }
	    while (cls != null) {
	        // System.out.println("SUP ALM MAT "+cls+", "+prefix);
	        if (prefix.endsWith("*")
	                && cls.getName().startsWith(
	                        prefix.substring(0, prefix.length() - 1)))
	            return true;
	        if (prefix.startsWith("*")
	                && cls.getName().endsWith(prefix.substring(1)))
	            return true;
	        if (prefix.startsWith("*")
	                && prefix.endsWith("*")
	                && cls.getName().indexOf(
	                        prefix.substring(1, prefix.length() - 1)) >= 0)
	            return true;
	        cls = cls.getSuperclass();
	    }
	    return false;
	}

	public static boolean isCWTWidget(Class cls) {
	    try {
	        Class cwtWid = Class.forName("com.philemonworks.typewise.Widget");
	        if (cwtWid == null)
	            return false;
	        return  Cacher.isAssignableFrom(cwtWid, cls);
	    } catch (Throwable e) {
	        return false;
	    }
	}

	/**
	 * @param field
	 * @return true if field is a LayoutWrapper for a GroupLayout
	 */
	public static boolean isGroupLayout(Object field) {
	    return (field instanceof LayoutWrapper && "Group"
	            .equals(((LayoutWrapper) field).getLayoutType()));
	}

	public static boolean isAssignableFrom(Class cls1, Class cls2) {
	    if ( Cacher.isAssignableFrom(cls1, cls2))
	        return true;
	    if(cls1 != null 
	            && jiglooPlugin.canUseSwing() 
	            &&  Cacher.isAssignableFrom(AbstractFormBuilder.class, cls2)
	            &&  Cacher.isAssignableFrom(cls1, JPanel.class))
	        return true;
	    if(cls1.equals(Object.class))
	        return true;
	    
	    //this is so the <Constructor>(Boolean) constructor can be found when
	    //looking for a <Constructor>(boolean) constructor. This is a hack,
	    //caused by the hack in ConstructorManager.getTypes that converts
	    //Boolean.class to boolean.class
	    if(cls1.equals(Boolean.class) && cls2.equals(boolean.class))
	    	return true;
	    
	    if (!cls1.isPrimitive())
	        return false;
	    
	    if(  (
	            cls2.equals(double.class) 
	            || cls2.equals(float.class)
	            || cls2.equals(int.class)
	    ) && (
	            cls1.equals(double.class) 
	            || cls1.equals(float.class)
	            || cls1.equals(int.class)
	    ) )
	        return true;
	    
	    if (cls2.equals(Character.class) || cls2.equals(Byte.class)
	            || cls2.equals(Long.class) || cls2.equals(Float.class)
	            || cls2.equals(Double.class) || cls2.equals(Short.class)
	            || cls2.equals(Integer.class) || cls2.equals(Boolean.class))
	        return true;
	    return false;
	}

	public static boolean isCWT(Class cls) {
	    return (isCWTWidget(cls) || isCWTApplicationModel(cls));
	}

	public static boolean isSwing(Class cls) {
	    return (isJComponent(cls) || isBuilder(cls) ||  Cacher.isAssignableFrom(AbstractAction.class, cls));
	}

	public static boolean isSWT(Class cls) {
	    return (isSWTWidget(cls) || isJFace(cls) || isRCP(cls));
	}

	public static boolean isSWTAWT(Class cls) {
		if(cls == null)
			return false;
		return 
	    "org.eclipse.swt.awt.SWT_AWT".equals(cls.getName());
	}

	public static boolean isPublicAccess(Class clazz) {
	    if ((clazz.getModifiers() & Modifier.PUBLIC) != 0)
	        return true;
	    return false;
	}

	public static boolean isGWTWidget(Class cls) {
	    try {
	        Class gwtWid = jiglooPlugin.loadClass("com.google.gwt.user.client.ui.UIObject");
	        if (gwtWid == null)
	            return false;
	        return  Cacher.isAssignableFrom(gwtWid, cls);
	    } catch (Throwable e) {
	        return false;
	    }
	}

	public static boolean isGWTEntryPoint(Class cls) {
	    try {
	        Class ep = jiglooPlugin.loadClass("com.google.gwt.core.client.EntryPoint");
	        if (ep == null)
	            return false;
	        return  Cacher.isAssignableFrom(ep, cls);
	    } catch (Throwable e) {
	        return false;
	    }
	}

	public static boolean isGWT(Class cls) {
	    return (isGWTWidget(cls) || isGWTEntryPoint(cls));
	}

	public static boolean isMenuClass(Class cls) {
	    if(cls == null)
	        return false;
	    if ( Cacher.isAssignableFrom(Menu.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(MenuItem.class, cls))
	        return true;
	    if (jiglooPlugin.canUseSwing()) {
	        if ( Cacher.isAssignableFrom(JPopupMenu.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(JMenuBar.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(JMenuItem.class, cls))
	            return true;
	    }
	    return false;
	}

	public static boolean isLayoutData(Class cls) {
	    if (jiglooPlugin.canUseSwing()) {
	        if ( Cacher.isAssignableFrom(GridBagConstraints.class, cls))
	            return true;
	    }
	    if ( Cacher.isAssignableFrom(GridData.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(RowData.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(FormData.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(FormAttachment.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(LayoutDataWrapper.class, cls))
	        return true;
	    return false;
	}

	public static boolean isLayout(Class cls) {
	    if (cls == null)
	        return false;
	    String cName = cls.getName();
	    if (jiglooPlugin.canUseSwing()) {
	        if ( Cacher.isAssignableFrom(java.awt.LayoutManager.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(java.awt.LayoutManager2.class, cls))
	            return true;
	    }
	    if ( Cacher.isAssignableFrom(Layout.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(LayoutWrapper.class, cls))
	        return true;
	    return false;
	}

	public static Object newInstance(Class cls, Constructor con,
	        Object[] params, boolean addToDefMgr, boolean logError)
	        throws Throwable {
	    if(jiglooPlugin.canUseSwing() && jiglooPlugin.getJavaVersion() > 13) {
	        try {
	            JDialog.setDefaultLookAndFeelDecorated(false);
	            JFrame.setDefaultLookAndFeelDecorated(false);
	        } catch(Throwable t) {}
	    }
	
	    if (con != null)
	        ClassUtils.objVec.add(con);
	    else
	        ClassUtils.objVec.add(cls);
	    String txt = "\n" + ClassUtils.getIndent() + ">>>  about to create " + cls;
	    if (con != null)
	        txt += ", constructor=" + con;
	
	    jiglooPlugin.writeToCreationLog(txt);
	    
	    if (cls.getName().equals("org.jdesktop.layout.GroupLayout")
	            || cls.getName().equals("javax.swing.GroupLayout")) {
	        cls = GroupLayout.class;
	        if (con != null) {
	            con = cls.getConstructor(con.getParameterTypes());
	        }
	    }
	
	    Constructor initCon = con;
	    Object[] initParams = null;
	    if(params != null) {
	    	initParams = new Object[params.length];
	    	for (int i = 0; i < initParams.length; i++) {
				initParams[i] = params[i];
			}
	    }
	    try {
	
	        boolean canMake = jiglooPlugin.getDefault().canMakeNVClass(cls);
	        if (!canMake) {
	            jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                    + "<<< not allowed to make " + cls);
	            if (ClassUtils.objVec.size() > 0)
	                ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	            return null;
	        }
	
	        Method m = null;
	        Object obj = null;
	
	        if (jiglooPlugin.canUseSwing()) {
	            try {
	                m = cls.getMethod("getGUIBuilderInstance", null);
	                obj = m.invoke(null, null);
	                if (obj instanceof Component) {
	                    System.out.println("CREATED GUI BLDR INST " + obj
	                            + ", " + cls);
	                    jiglooPlugin
	                            .writeToCreationLog(ClassUtils.getIndent()
	                                    + "<<< called getGUIBuilderInstance for "
	                                    + cls);
	                    if (ClassUtils.objVec.size() > 0)
	                        ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	                    if (addToDefMgr)
	                        DefaultValueManager.addClassObject(obj, false);
	                    return obj;
	                }
	            } catch (Throwable t) {
	            }
	        }
	        if (m == null) {
	            try {
	                m = cls.getMethod("getGUIBuilderInstance", new Class[] {
	                        Composite.class, int.class });
	                obj = m.invoke(null, initParams);
	                if (obj instanceof Composite) {
	                    if (addToDefMgr)
	                        DefaultValueManager.addClassObject(obj, false);
	                    jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                                    + "<<< called getGUIBuilderInstance for "+ cls);
	                    System.out.println("CREATED GUI BLDR INST " + obj
	                            + ", " + cls + ", " + initParams);
	                    if (ClassUtils.objVec.size() > 0)
	                        ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	                    return obj;
	                }
	            } catch (Throwable t) {
	            }
	        }
	
	        if (con != null) {
	            Class ccls = con.getDeclaringClass();
	            if (ccls.isInterface() || isAbstract(ccls)) {
	                jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                        + "<<< not making interface/abstract " + cls);
	                if (ClassUtils.objVec.size() > 0)
	                    ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	                return null;
	            }
	        } else {
	            if (cls.isInterface() || isAbstract(cls)) {
	                jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                        + "<<< not making interface/abstract " + cls);
	                if (ClassUtils.objVec.size() > 0)
	                    ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	                return null;
	            }
	        }
	
	        if (con == null) {
	        	con = cls.getConstructor(null);
	        }
	        
	        if (params != null && con != null
	                && params.length != con.getParameterTypes().length) {
	            //use the no-parameter constructor if the number of parameters
	            // is not right
	            con = cls.getConstructor(null);
	            params = null;
	        }
	        
	        if (initParams != null) {
	            Class[] ctypes = con.getParameterTypes();
	            for (int i = 0; i < initParams.length; i++) {
	                Object p = initParams[i];
	                //don't do "else if" since p might be a FormComponent whose
	                // nonVisualObject is an ImageWrapper!
	                if (p instanceof FormComponent) {
	                    FormComponent fc = (FormComponent) p;
//	                    if (!fc.isRoot() || 
//	                            (fc.isSwing() &&  Cacher.isAssignableFrom(AbstractFormBuilder.class, cls)))
	                        p = fc.getObject(true);
	                }
	                if (p instanceof ArrayHolder) {
	                    p = ((ArrayHolder) p).getRawArray();
	                }
	                if (p instanceof ConstructorHolder) {
	                    p = ((ConstructorHolder) p).newInstance();
	                }
	                if (p instanceof RootMethodCall) {
	                    p = ((RootMethodCall) p).invoke();
	                }
	                if (p instanceof IconWrapper) {
	                    p = ((IconWrapper) p).getIcon();
	                } else if (p instanceof ImageWrapper) {
	                    p = ((ImageWrapper) p).getImage(null);
	                } else if (p instanceof ClassWrapper) {
	                    p = ((ClassWrapper) p).getFormComponent().getObject(true);
	                }
	                initParams[i] = p;
	                
	                if (initParams[i] instanceof Double
	                        && ctypes[i].equals(float.class))
	                    initParams[i] = new Float(((Double)initParams[i]).floatValue());
	
	                //stop null being passed in for a String parameter - a null
	                //argument for ImageIcon, for example, causes a NPE in
	                //the display thread and freezes eclipse!
	                if (initParams[i] == null && ctypes[i].equals(String.class))
	                    initParams[i] = "";
	                //                    System.err.println("Param " + i + " = " + initParams[i]);
	            }
	        }
	        try {
	            obj = con.newInstance(initParams);
	        } catch (Throwable t) {
	            if(t instanceof InvocationTargetException) {
	                InvocationTargetException ite = (InvocationTargetException)t;
	                if(jiglooPlugin.getJavaVersion() > 13 && ite.getCause() != null) {
	                    System.err.println("Error creating new "+cls+" : "+ite.getCause());
	                    ite.getCause().printStackTrace();
	                } else {
	                    System.err.println("Error creating new "+cls+" : "+t);
	                }
	            }
	            String msg = "warning - error creating " + cls + ", " + t
	                    + ", constructor=" + con + " cls="+cls;
	            if(cls != null)
	                msg += " ClassLoader="+cls.getClassLoader();
	            if(cls != null && cls.getClassLoader() != null)
	                msg += "@"+cls.getClassLoader().hashCode();
	            if (params != null) {
	                msg += ", params";
	                for (int i = 0; i < params.length; i++) {
	                    msg += " : " + params[i];
	                    if (params[i] != null) {
	                        msg += ", " + params[i].getClass().getName(); 
	                        if(params[i].getClass() != null && params[i].getClass().getClassLoader() != null)
	                            msg+=" ClassLoader@"+params[i].getClass().getClassLoader().hashCode();;
	                    }
	                }
	            }
	            if (logError)
	                jiglooPlugin.writeToLog(msg);
	            
	            if (params != null) {
	                //try default constructor (unless params == null, since  that
	                //means we have already called the default constructor)!
	                try {
	                    obj = cls.newInstance();
	                } catch (Throwable t2) {
	                    jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                            + "<<< error trying to make " + cls + ", " + t);
	                    if (ClassUtils.objVec.size() > 0)
	                        ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	                    throw t;
	                }
	            }
	            jiglooPlugin.writeToCreationLog(ClassUtils.getIndent()
	                    + "<<< error trying to make " + cls + ", " + t);
	            if (ClassUtils.objVec.size() > 0)
	                ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	            throw t;
	        }
	        
	        boolean newInst = params != null && params.length > 0;
	        if (addToDefMgr) {
	        	DefaultValueManager.addClassObject(obj, newInst);
	        	StringBuffer msg = new StringBuffer("Instantiated "+ cls.getName());
	        	if (initParams != null) {
	        		msg.append(" (");
	        		for (int i = 0; i < initParams.length; i++) {
	        			if (i != 0)
	        				msg.append(",");
	        			msg.append("" + initParams[i]);
	        		}
	        		msg.append(")");
	        	}
	        	jiglooPlugin.addToLog(msg.toString());
	        }
	
	        jiglooPlugin.writeToCreationLog(ClassUtils.getIndent() + "<<< created " + cls);
	        if (ClassUtils.objVec.size() > 0)
	            ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	        return obj;
	        
	    } catch (NoSuchMethodException t) {
	        jiglooPlugin.writeToCreationLog(ClassUtils.getIndent() + "<<< error creating "
	                + cls + ", " + t);
	        if (ClassUtils.objVec.size() > 0)
	            ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	        //System.err.println("No no-param constructor for "+cls);
	        throw t;
	    } catch (Throwable t) {
	        String clsName = cls.getName();
	        jiglooPlugin.writeToCreationLog(ClassUtils.getIndent() + "<<< error creating "
	                + cls + ", " + t);
	        if (ClassUtils.objVec.size() > 0)
	            ClassUtils.objVec.remove(ClassUtils.objVec.lastElement());
	        throw t;
	    }
	}

	static String getIndent() {
	    String indent = "";
	    for (int i = 0; i < (ClassUtils.objVec.size() - 1); i++) {
	        indent += "    ";
	    }
	    return indent;
	}

	public static Object getProblemClassOrConstructor() {
	    if(ClassUtils.objVec.size() > 0)
	        return ClassUtils.objVec.lastElement();
	    return null;
	}

	public static Vector objVec = new Vector();

	public static Object newInstance(Class cls, Constructor con,
	        Object[] params, boolean addToDefMgr) throws Throwable {
	    return newInstance(cls, con, params, addToDefMgr, true);
	}

	public static Object newInstance(Class cls, Constructor con, Object[] params)
	        throws Throwable {
	    return newInstance(cls, con, params, true);
	}

	public static boolean isResource(Class cls) {
	    if (cls == null)
	        return false;
	    String cName = cls.getName();
	    if (jiglooPlugin.canUseSwing()) {
	        if ( Cacher.isAssignableFrom(java.awt.Color.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(java.awt.Font.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(java.awt.Image.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(javax.swing.Icon.class, cls))
	            return true;
	        if ( Cacher.isAssignableFrom(java.awt.Cursor.class, cls))
	            return true;
	    }
	
	    if ( Cacher.isAssignableFrom(ColorWrapper.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(FontWrapper.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(ImageWrapper.class, cls))
	        return true;
	
	    if ( Cacher.isAssignableFrom(Color.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(Font.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(Image.class, cls))
	        return true;
	    if ( Cacher.isAssignableFrom(Cursor.class, cls))
	        return true;
	    return false;
	}

	public static Object createInnerClass(Class innerClass, Object enclosingInstance) {
        if(enclosingInstance != null) {
            Constructor[] cons = innerClass.getConstructors();
        	for (int i = 0; i < cons.length; i++) {
        		try {
        			Class[] ptypes = cons[i].getParameterTypes();
        			if(ptypes.length == 1 && ptypes[0].isAssignableFrom(enclosingInstance.getClass())) {
        				return cons[i].newInstance(new Object[] {enclosingInstance});
        			}
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		}
        	}
        }
		return null;
	}

}
