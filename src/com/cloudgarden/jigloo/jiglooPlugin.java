package com.cloudgarden.jigloo;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PluginVersionIdentifier;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.cloudgarden.jigloo.dialog.ErrorDialog;
import com.cloudgarden.jigloo.dialog.SwingDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.editors.JiglooSecurityException;
import com.cloudgarden.jigloo.harness.HarnessManager;
import com.cloudgarden.jigloo.harness.PaletteHelper;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.preferences.PaletteComposite;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.CursorManager;
import com.cloudgarden.jigloo.resource.FontManager;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LookAndFeelWrapper;

/**
 * The main plugin class to be used in the desktop.
 */
public class jiglooPlugin extends AbstractUIPlugin {

    public static String getVersion() {
    	if(getDefault().getDescriptor() != null) {
    		return getDefault().getDescriptor().getVersionIdentifier().toString();
    	} else if(getDefault().getBundle() != null) {
    		return getDefault().getBundle().getVersion().toString();
    	}
        return "4.6.7";
    }

	public static final boolean DEBUG_EXTRA = false;
	public static final boolean DEBUG = false;

	//The shared instance.
	private static jiglooPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	private BundleContext bundleContext;
	private String licStr;
	private IPreferenceStore pstore;
	private KeyListener keyListener = null;
	
	private static boolean isMacOS, isLinuxOS, isWindowsOS = false;
	
//	public static final boolean INVOKE_NON_SETTERS = false;
	
	private static final int NUM_TRIAL_NBS = 4;
	private static final int NUM_TRIAL_DAYS = 10;
	private boolean createMain = true;
	private boolean createShowGUI = true;
	private static boolean canUseForms = false;
	private static boolean canUseGroupLayout = false;
	private static boolean canUseCWT = false;
	private static boolean canUseSWT_AWT = false;
	
	public static final boolean
		ACCOMODATE_ALL_CLASS_PROPERTIES = true;

	/**
	 * The constructor.
	 */
	public jiglooPlugin(IPluginDescriptor descriptor) {
	    
	    super(descriptor);
	    
//		System.out.println("ClassLoader="+Thread.currentThread().getContextClassLoader().getClass().getName());
	    try {
	        Display.getDefault().addFilter(SWT.KeyDown, new Listener() {
	            public void handleEvent(Event event) {
	                if(keyListener == null)
	                    return;
	                keyListener.keyPressed(new KeyEvent(event));
	                event.doit = false;
	                event.type = SWT.NONE;
	            }
	        });
	        Display.getDefault().addFilter(SWT.KeyUp, new Listener() {
	            public void handleEvent(Event event) {
	                if(keyListener == null)
	                    return;
	                keyListener.keyReleased(new KeyEvent(event));
	                event.doit = false;
	                event.type = SWT.NONE;
	            }
	        });
	    } catch(Throwable t) {
	        System.err.println("Unable to add key listener to filter");
		}
		init();
	}
	
    //this one isn't called
	public jiglooPlugin() {
		super();
		init();
	}
	
	private Thread shutdownHook = null;
	private boolean normalShutdown = false;
	
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.bundleContext = context;

		if(shutdownHook == null) {
		    shutdownHook = new Thread() {
		        public void run() {
		            if(!normalShutdown) {
		                Object prob = ClassUtils.getProblemClassOrConstructor();
		        	    String msg = "";
		        	    if(prob == null) {
		        	    	if(getActiveEditor() != null) {
		        	    		msg += "parsing \""+getActiveEditor().getJavaCodeParser().getCurrentStatement()+"\"";
		        	    	}
		        	    } else if(prob instanceof Class)
		        	        msg += "creating "+prob;
		        	    else if(prob instanceof Constructor)
		        	        msg += "calling constructor "+prob;
		        	    else if(prob instanceof Method)
		        	        msg += "calling method "+((Method)prob).getName()
		        	        +" of "+((Method)prob).getDeclaringClass();
		        	    
		        	    msg = "\n*** Abnormal shutdown while "
		        	        + msg
		        	        + " in class "+getActiveEditor().getFullClassName()
		        	        + "\n\nPlease look for calls to System.exit which might be made "
		        	        +msg+" in a blank environment." +
		        	        		"\n\nYou can use java.beans.Beans.isDesignTime(), which will return true when" +
		        	        		" in Jigloo, to determine when a call to System.exit is not necessary.";
		        	    msg += "\n\n"+JiglooUtils.getStackTrace(new Throwable());
//		        	    System.err.println(msg);
		        	    writeToCreationLog(msg);
		        	    writeToLog(msg);
		            }
		        }
		    };
		    Runtime.getRuntime().addShutdownHook(shutdownHook);
		}
	}
	
	public BundleContext getBundleContext() {
		return this.bundleContext;
	}
	
	private void init() {
		plugin = this;
		
		//set the handler package name so that images are actually handled!!!!
		String chp= System.getProperty("java.content.handler.pkgs");
		if(chp == null)
		    chp = "";
		else
		    chp = chp + "|";
		System.setProperty("java.content.handler.pkgs", chp+"sun.net.www.content");
		
		System.setProperty("java.awt.headless", "false");

		String os = System.getProperty("os.name").toLowerCase();
		isMacOS = os.indexOf("mac") >= 0;
		isLinuxOS = os.indexOf("linux") >= 0;
		isWindowsOS = os.indexOf("windows") >= 0;

		if (canUseSwing()) {
            Toolkit.getDefaultToolkit().addAWTEventListener(
                    new AWTEventListener() {
                        public void eventDispatched(final AWTEvent event) {
                            handleAWTEvent(event);
                        }
                    }, AWTEvent.WINDOW_EVENT_MASK );
        }

		try {
			resourceBundle = ResourceBundle.getBundle("com.cloudgarden.jigloo.jiglooPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}

//		preprocess();
		getWindowDecorationHeight();
		try {
		    pstore = getPreferenceStore();
		    MainPreferencePage.init(pstore);
		    
		    String oldVersion = pstore.getString(MainPreferencePage.P_VERSION);
//		    System.out.println("OLD_VERSION="+oldVersion);
		    
	        String paletteClasses = pstore.getString(MainPreferencePage.P_PALETTE_CLASSES);
		    if(oldVersion == null || !oldVersion.equals(getVersion())) {
		        if(paletteClasses.indexOf("javax.swing.JList") < 0) {
		            paletteClasses = JiglooUtils.replace(paletteClasses, 
		                    "javax.swing.JLabel|0||n|"+PaletteComposite.PREF_SEP_2,
		                    "javax.swing.JLabel|0||n|"+PaletteComposite.PREF_SEP_2 +
		                    "Components|1|javax.swing.JList|0||n|"+PaletteComposite.PREF_SEP_2);
		            pstore.setValue(MainPreferencePage.P_PALETTE_CLASSES, paletteClasses);
		        }
		    }

		    if(HarnessManager.ENABLE_ANDROID 
		    		&& paletteClasses.indexOf("android.widget.Button") < 0) {
		    	System.err.println("!!!Forcing android widgets into palette - must take out before release of new version!!!");
	        	String[] part1 = JiglooUtils.split(PaletteComposite.PREF_SEP_3, paletteClasses);
	        	String[] part2 = JiglooUtils.split(PaletteComposite.PREF_SEP_2, part1[0]);
	        	part1[0] = part2[0]+PaletteComposite.PREF_SEP_2+part2[1]+PaletteComposite.PREF_SEP_2+PaletteHelper.getPaletteCategories();
	        	int pos1 = part1[1].lastIndexOf("|1|");
	        	int pos2 = part1[1].lastIndexOf(PaletteComposite.PREF_SEP_2, pos1);
	        	part1[1] = part1[1].substring(0, pos2)+PaletteComposite.PREF_SEP_2+PaletteHelper.getPaletteString();
	        	paletteClasses = part1[0]+PaletteComposite.PREF_SEP_3+part1[1];
	            pstore.setValue(MainPreferencePage.P_PALETTE_CLASSES, paletteClasses);
	        }
		    
		    pstore.setValue(MainPreferencePage.P_VERSION, getVersion());
		    pstore.addPropertyChangeListener(new IPropertyChangeListener() {
		        public void propertyChange(PropertyChangeEvent evt) {
		            if (evt.getProperty().equals(MainPreferencePage.P_DOUBLE_CLICK)) {
		                updateDoubleClick();
		            }
		        }
		    });
		} catch(Throwable t) {
		    t.printStackTrace();
		}
		
		canUseCWT = false;
		try {
			loadClass("com.philemonworks.typewise.Widget");
			canUseCWT = true;
		} catch (Throwable t) {
			canUseCWT = false;
		}
				
		canUseForms = false;
		try {
			loadClass("org.eclipse.ui.forms.ManagedForm");
			canUseForms = true;
		} catch (Throwable t) {
		    canUseForms = false;
		}
		
		canUseGroupLayout = false;
		try {
//			loadClass("org.jdesktop.layout.GroupLayout");
			canUseGroupLayout = true;
		} catch (Throwable t) {
		    canUseGroupLayout = false;
		}
		
		try {
				loadClass("org.eclipse.swt.awt.SWT_AWT");
				canUseSWT_AWT = true;
			} catch (Throwable t) {
		}
		
		updateDoubleClick();

		Beans.setDesignTime(true);
		Beans.setGuiAvailable(true);

		licStr = encryptToHex("Jigloo v3.0.0 Prof", "GH6tfjUtf7");
//		System.err.println("LIC=" + licStr); //E81086713E446D36F62B2AA2A3502B5EB155

		IResourceChangeListener listener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				try {
					if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
						analyseDelta(event.getDelta());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		ResourcesPlugin.getWorkspace().addResourceChangeListener(
		listener,
		IResourceChangeEvent.POST_CHANGE);

	}

	private SwingDialog sd;
	
	private void handleAWTEvent(final AWTEvent event) {
        FormEditor ed = getActiveEditor();
//        System.err.println("AWT Event " + event+", "+event.getSource()+" id="+event.getID()+" Editor="+ed);
        
        if(ed == null || (!ed.isProcessing() && !ed.justStoppedPopulating()))
            return;
        if (event.getID() == WindowEvent.WINDOW_OPENED) {
            Object win = event.getSource();
            
            if(allowedWindows.contains(new Integer(win.hashCode())))
                return;
            
            if(win instanceof Window) {
                ((Window)win).setVisible(false);
            } else {
                return;
            }
            
            if(win != null && win.getClass().getName().equals("sun.awt.windows.WEmbeddedFrame"))
                return;
            if (win instanceof java.awt.Window) {
                final java.awt.Window jwin = (java.awt.Window)win;
                if(false 
                        && canUseSWT_AWT 
                        && (win instanceof javax.swing.JWindow || win instanceof javax.swing.JDialog)) {
                    java.awt.Container pan = null;
                    String title = "";
                    if(win instanceof javax.swing.JFrame) {
                        pan = ((javax.swing.JFrame)win).getContentPane();
                        title = ((javax.swing.JFrame)win).getTitle();
                    } else {
                        pan = ((javax.swing.JDialog)win).getContentPane();
                        title = ((javax.swing.JDialog)win).getTitle();
                    }
                    if(pan instanceof javax.swing.JPanel) {
                        final java.awt.Container fpan = pan;
                        final String ftitle = title;
                        Display.getDefault().syncExec(new Runnable() {
                            public void run() {
                                sd = new SwingDialog(getActiveEditor().getSite().getShell(), SWT.NULL);
                                sd.setText(ftitle);
                                sd.setJPanel((javax.swing.JPanel)fpan);
                                jwin.setVisible(false);
                                sd.open();
                                jwin.addWindowListener(new WindowAdapter() {
                                    public void windowClosed(WindowEvent e) {
                                        if(sd != null && !sd.getParent().isDisposed())
                                            sd.getParent().dispose();
                                    }
                                    public void windowClosing(WindowEvent e) {
                                        if(sd != null && !sd.getParent().isDisposed())
                                            sd.getParent().dispose();
                                    }
                                    public void windowDeactivated(WindowEvent e) {
                                        if(sd != null && !sd.getParent().isDisposed())
                                            sd.getParent().dispose();
                                    }
                                });
                            }
                        });
                    } else {
                        if(jwin.getClass().getName().indexOf("borland") > 0)
                            jwin.toFront();
                        else
                            jwin.setVisible(false);
                        writeToLog("java.awt.Window detected and hidden "+event.getSource());
                    }
                } else {
                    if(jwin.getClass().getName().indexOf("borland") > 0)
                        jwin.toFront();
                    else
                        jwin.setVisible(false);
                    writeToLog("java.awt.Window detected and hidden "+event.getSource());
                }
            }
        }
	}
	
	private static IEditorPart activeEditor = null;
	
	public static void setActiveEditor(IEditorPart part) {
	    activeEditor = part;
	}
	
	public static FormEditor getActiveEditor() {
	    if(activeEditor instanceof FormEditor)
	        return (FormEditor)activeEditor;
	    return null;
	}
	
	public static String getEclipseVersion() {
		WorkbenchPlugin wbp = WorkbenchPlugin.getDefault();
		String wbVer = (String) wbp.getBundle().getHeaders().get(
				Constants.BUNDLE_VERSION);
		if(wbVer == null)
			wbVer = wbp.getDescriptor().getVersionIdentifier().toString();
		return wbVer;
	}
	
	private int getVersionAsInt(String version) {
		String wbVer = JiglooUtils.replace(version, ".", "");
		while(wbVer.length() < 3)
			wbVer+="0";
		return Integer.parseInt(wbVer);
	}
	
	private boolean compareVersions(int eclipseVersion, String jiglooCmnt) {
		int jilooVer = getVersionAsInt(jiglooCmnt.substring(2));
		if(jiglooCmnt.startsWith("eq")) {
			return eclipseVersion == jilooVer;
		} else if(jiglooCmnt.startsWith("gt")) {
			return eclipseVersion > jilooVer;
		} else if(jiglooCmnt.startsWith("ge")) {
			return eclipseVersion >= jilooVer;
		} else if(jiglooCmnt.startsWith("lt")) {
			return eclipseVersion < jilooVer;
		} else if(jiglooCmnt.startsWith("le")) {
			return eclipseVersion <= jilooVer;
		}
		return false;
	}
	
//	private void preprocess() {
//		try {
//			int eclipseVer = getVersionAsInt(getEclipseVersion());
//			IExtensionRegistry reg = InternalPlatform.getDefault().getRegistry();
//			IExtension[] exts = reg.getExtensions("com.cloudgarden.jigloo");
//			//		System.out.println("VERSION="+wbVer);
//			for (int i = 0; i < exts.length; i++) {
//				IExtension ext = exts[i];
//				IConfigurationElement[] elems = ext.getConfigurationElements();
//				for (int j = 0; j < elems.length; j++) {
//					IConfigurationElement elem = elems[j];
//					IConfigurationElement[] elems2 = elem.getChildren();
//					int len = elems2.length;
//					for (int k = 0; k < elems2.length; k++) {
//						IConfigurationElement elem2 = elems2[k];
//						String cmnt = elem2.getAttribute("jiglooCmnt");
//						if(cmnt != null) {
//							ConfigurationElement ce = (ConfigurationElement)elem2;
//							System.out.println("GOT JIGLOO CMNT ATTRIB "+elem2.getAttribute("jiglooCmnt"));
//							if(!compareVersions(eclipseVer, cmnt)) {
//								System.out.println("REMOVING");
//								elems2[k] = null;
//								len--;
//							} else {
//								//remove jiglooCmnt property
//								ConfigurationProperty[] props = ce.getProperties();
//								ConfigurationProperty[] newProps = new ConfigurationProperty[props.length-1];
//								for (int index = 0; index < newProps.length; index++) {
//									newProps[index] = props[index];
//								}
//								ce.setProperties(newProps);
//							}
//						}
//					}
//					if(len != elems2.length) {
//						IConfigurationElement[] newCEs = 
//							new IConfigurationElement[len];
//						int n = 0;
//						for (int m = 0; m < elems2.length; m++) {
//							if(elems2[m] != null)
//								newCEs[n++] = elems2[m];
//						}
//						((ConfigurationElement)elem).setChildren(newCEs);
//					}
//				}
//			}
//		} catch(Throwable t) {
//			t.printStackTrace();
//		}
//	}

	private Vector allowedWindows = new Vector();
	
	public void addAllowedWindow(Object win) {
	    allowedWindows.add(new Integer(win.hashCode()));
	}
	
	public static DataInputStream getResourceAsStream(String resourceName) {
		DataInputStream strm;
		strm = new DataInputStream(getDefault().getClass().getClassLoader().getResourceAsStream(resourceName));
		//System.out.println("got stream " + strm);
		return strm;
	}

	public static Vector getResourceAsLineVector(String resourceName) {
		DataInputStream strm;
		strm = new DataInputStream(getDefault().getClass().getClassLoader().getResourceAsStream(resourceName));
		Vector lines = new Vector();
		String line = "";
		try {
			while ((line = strm.readLine()) != null) {
				System.out.println("READ LINE FROm RES " + resourceName + ", " + line);
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private IFile getOtherFile(IPath path) {
		IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = wsr.getFileForLocation(path.makeAbsolute());
		if (file == null) {
			System.err.println("CAN'T FIND FILE FOR " + path.makeAbsolute());
			return null;
		}
		String name = file.getName();
		IFile other = null;

		int pos = name.lastIndexOf(".form");
		if (pos < 0) {
			pos = name.lastIndexOf(".java");
			name = name.substring(0, pos) + ".form";
		} else {
			name = name.substring(0, pos) + ".java";
		}

		if (file.getParent() instanceof IFolder) {
			other = ((IFolder) file.getParent()).getFile(name);
		} else if (file.getParent() instanceof IProject) {
			other = ((IProject) file.getParent()).getFile(name);
		}
		return other;
	}

	private static Vector allowedClasses = new Vector();
	private static Vector notAllowedClasses = new Vector();

	public void clearAllowedClasses() {
		allowedClasses.clear();
		notAllowedClasses.clear();
	}

	public boolean canMakeNVClass(Class cls) {
		if (cls == null)
			return false;
		//use a HashMap because lookup should be quicker than using a Vector?
		if (allowedClasses.contains(cls.getName()))
			return true;
		if (notAllowedClasses.contains(cls.getName()))
			return false;
		if (canMakeClass(cls)) {
			allowedClasses.add(cls.getName());
			return true;
		} else {
			notAllowedClasses.add(cls.getName());
			System.out.println("Jigloo: not making class "+cls);
		}
		return false;
	}

	public boolean canCallNonSetter(Class cls) {
	    
		String notallowed = getStringPreference(MainPreferencePage.P_NOT_OK_NON_SETTER_CLASSES);
		String[] notoks = JiglooUtils.split(";", notallowed);

		for (int i = 0; i < notoks.length; i++) {
			String notok = notoks[i];
			if ("".equals(notok))
				continue;
			if (ClassUtils.superclassAlmostMatches(cls, notok))
				return false;
		}

		String allowed = getStringPreference(MainPreferencePage.P_OK_NON_SETTER_CLASSES);
		String[] oks = JiglooUtils.split(";", allowed);
		for (int i = 0; i < oks.length; i++) {
			String ok = oks[i];
			if ("".equals(ok))
				continue;
			if (ClassUtils.superclassAlmostMatches(cls, ok))
				return true;
		}

		return false;
	}
		
	private boolean canMakeClass(Class cls) {
	    
		String notallowed = getStringPreference(MainPreferencePage.P_NOT_OK_NV_CLASSES);
		String[] notoks = JiglooUtils.split(";", notallowed);

		for (int i = 0; i < notoks.length; i++) {
			String notok = notoks[i];
			if ("".equals(notok))
				continue;
			if (ClassUtils.superclassAlmostMatches(cls, notok))
				return false;
		}

		String allowed = getStringPreference(MainPreferencePage.P_OK_NV_CLASSES);
		String[] oks = JiglooUtils.split(";", allowed);
		for (int i = 0; i < oks.length; i++) {
			String ok = oks[i];
			if ("".equals(ok))
				continue;
			if (ClassUtils.superclassAlmostMatches(cls, ok))
				return true;
		}

		return false;

		//		if (JiglooUtils.superclassMatches(cls, "javax.swing."))
		//			return true;
		//		if (JiglooUtils.superclassMatches(cls, "java.awt."))
		//			return true;
		//		if (JiglooUtils.superclassMatches(cls, "java.util."))
		//			return true;
		//		if (JiglooUtils.superclassMatches(cls, "org.eclipse.swt."))
		//			return true;

		//		if (JiglooUtils.isVisual(cls))
		//			return true;
		//		if (JiglooUtils.isLayout(cls))
		//			return true;
		//		if (JiglooUtils.isLayoutData(cls))
		//			return true;
		//		if (JiglooUtils.isResource(cls))
		//			return true;
		//		if (canUseSwing()) {
		//			if (ButtonGroup.class.isAssignableFrom(cls))
		//				return true;
		//			if (TableModel.class.isAssignableFrom(cls))
		//				return true;
		//			if (ListModel.class.isAssignableFrom(cls))
		//				return true;
		//			if (EventListener.class.isAssignableFrom(cls))
		//				return true;
		//		}
		//		if (Listener.class.isAssignableFrom(cls))
		//			return true;

		//return false;
	}

	private void analyseDelta(IResourceDelta delta) {
		if (delta.getFlags() != 0) {
		    IResource res = delta.getResource();
		    if(res instanceof IFile && res.getName().equals(".classpath")) {
		        FormEditor.classpathChanged(res.getProject());
		    }
		}

		if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {
		    IResource res = delta.getResource();
//		    if(res instanceof IFile && "java".equals(res.getFileExtension())) {
//		        FormEditor.javaFileChanged(JavaCore.createCompilationUnitFrom((IFile)res));
//		    } 
		    if(res instanceof IFile && "class".equals(res.getFileExtension())) {
		    	String className = JiglooUtils.getClassName(JavaCore.createClassFileFrom((IFile) res));
		        FormEditor.classFileChanged(className, res.getProject());
		    } else if(res instanceof IFile && "properties".equals(res.getFileExtension())) {
		        FormEditor.propertyFileChanged((IFile)res);
		    }
		}
			
		if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
			IResource res = delta.getResource();
			if (res.getType() == IResource.FILE) {
			    FormEditor.resourceMoved(delta.getMovedFromPath(), delta.getResource().getFullPath());
	            FileUtils.resourceMoved(delta.getResource().getProject(), delta.getMovedFromPath(), delta.getResource().getFullPath());
			}
		}		
		IResourceDelta[] deltas = delta.getAffectedChildren();
		for (int i = 0; i < deltas.length; i++) {
			IResourceDelta delta2 = deltas[i];
			analyseDelta(delta2);
		}
	}

	private static File logFile = null;

	private static boolean oomShown = false;
	
	public static void writeToLog(Throwable t) {
		if(t == null)
			return;
		String msg = "Error: "+JiglooUtils.getStackTrace(t, -1);
		writeToLog(msg);
		if(!oomShown && t instanceof OutOfMemoryError) {
			try {
				MessageDialog
						.openWarning(activeEditor.getSite().getShell(),
								"Out of memory!",
								"Out of memory!\n"
										+ "\nPlease allow Eclipse to use more memory by starting it using a command like:"
										+ "\n\n\"eclipse -vmargs -Xmx300m -XX:MaxPermSize=256m\" \n"
										+ "\nThe currently-active editor will now be closed to free up some memory.\n");
				activeEditor.getSite().getPage().closeEditor(activeEditor, true);
			} catch(Throwable t2) {
				t2.printStackTrace();
			}
			return;
		}
	}

	private static String lastLogMsg = null;
	
	public static String getLastLogMessage() {
        String tmp = lastLogMsg;
        lastLogMsg = null;
        return tmp;
    }
	
	public static void writeToStallingLog(String txt) {
	    File sdir = getDefault().getStateLocation().toFile();
	    File plog = new File(sdir, "stalling_log.txt");
	    try {
	        BufferedOutputStream stallOut = new BufferedOutputStream(new FileOutputStream(plog));
	        stallOut.write((txt+"\n").getBytes());
	        stallOut.flush();
	        stallOut.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}
	
	private static BufferedOutputStream clogOut = null;
	
	private static String abnormalShutdownMessage = null;
	private static boolean abnormalShutdownMessageTested = false;
	
	public static String getAbnormalShutdownMessage() {
	    if(abnormalShutdownMessageTested)
	        return null;
	    File sdir = getDefault().getStateLocation().toFile();
	    File plog = new File(sdir, "creation_log.txt");
	    if(!plog.exists())
	        return null;
	    String contents = JiglooUtils.getFileAsString(plog);
	    String fileName = "";
	    int pos = contents.indexOf("Creating elements for");
	    if(pos >= 0) {
	        pos += 22;
	        int pos2 = contents.indexOf("\n", pos);
	        fileName = contents.substring(pos, pos2);
	    }
	    pos = contents.indexOf("*** Abnormal shutdown");
	    if(pos >= 0) {
	        abnormalShutdownMessage = ">>> Jigloo shut down abnormally while opening "
	            +fileName+" the last time Eclipse was run <<<\n\n"
	        +contents.substring(pos+4);
	    }
	    abnormalShutdownMessageTested = true;
	    return abnormalShutdownMessage;
	}
	
	public static void writeToCreationLog(String txt) {
	    File sdir = getDefault().getStateLocation().toFile();
	    File plog = new File(sdir, "creation_log.txt");
	    try {
	        if(clogOut == null)
	            clogOut = new BufferedOutputStream(new FileOutputStream(plog));
	        clogOut.write((txt+"\n").getBytes());
            clogOut.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}
	
	public static void closeCreationLog() {
	    File sdir = getDefault().getStateLocation().toFile();
	    File plog = new File(sdir, "creation_log.txt");
	    try {
	        if(clogOut != null)
	            clogOut.close();
	        clogOut = null;
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}
	
	public static void addToLog(String txt) {
		FormEditor fed = getActiveEditor();
		if(fed != null)
			fed.addToLog(txt);
	}

	public static void writeToPluginLog(String msg) {
		getDefault().getLog().log(new Status(Status.INFO, "com.cloudgarden.jigloo", Status.OK, msg, null));
	}
	
	public static void writeToLog(String msg) {
		System.err.println("Jigloo: " + msg);
		
		addToLog(msg);
		
		if(lastLogMsg != null) {
			lastLogMsg += "\n"+msg;
		} else {
			lastLogMsg = msg;			
		}
		if (logFile == null) {
			IPath logPath = Platform.getLogFileLocation();
			logFile = logPath.toFile();
		}
		try {
			msg = "\r\n[Jigloo Message: " + new Date().toLocaleString() + "]\r\n" + msg + "\r\n";
			FileWriter fw = new FileWriter(logFile.getAbsolutePath(), true);
			fw.write(msg);
			fw.flush();
			fw.close();
		} catch (Throwable e) {
		    System.err.println("Unable to write to log :"+e+"\nInitial error: "+msg);
		}
	}

	private boolean doubleClickForBooleans = false;

	private void updateDoubleClick() {
		doubleClickForBooleans = getBooleanPreference(MainPreferencePage.P_DOUBLE_CLICK);
	}

	public static boolean useTimeouts() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_TIMEOUTS);
	}

	public static boolean useImports() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_USE_IMPORTS);
	}

	public static boolean exportFormFile() {
		return false;
//		return getDefault().getBooleanPreference(MainPreferencePage.P_EXPORT_FORM);
	}

	public static boolean generateLookAndFeelCode() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_LOOKANDFEEL);
	}

	public static boolean parseConstructorsFirst() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_PARSE_CONS);
	}

	public static boolean useBraces() {
		return "1".equals(getDefault().getStringPreference(MainPreferencePage.P_USE_BRACES));
	}

	public static boolean useTaggedComments() {
		return "2".equals(getDefault().getStringPreference(MainPreferencePage.P_USE_BRACES));
	}

	public static boolean useBlankLines() {
		return "3".equals(getDefault().getStringPreference(MainPreferencePage.P_USE_BRACES));
	}

	public static boolean useGetters() {
		return "0".equals(getDefault().getStringPreference(MainPreferencePage.P_GETTERS));
	}

	public static boolean dontUseJava6GroupLayout() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_DONT_USE_JAVA6_GROUP_LAYOUT);
	}

	public static boolean dontUseGetters() {
		return "1".equals(getDefault().getStringPreference(MainPreferencePage.P_GETTERS));
	}

	public static boolean useGettersAuto() {
		return "2".equals(getDefault().getStringPreference(MainPreferencePage.P_GETTERS));
	}

	public boolean useDCForBooleans() {
		return doubleClickForBooleans;
	}

	public static boolean generateStubModel(String pref) {
		return getDefault().getPreferenceStore().getBoolean(pref);
	}

	public static boolean propertyCategoriesEnabled() {
		return getDefault().getBooleanPreference(MainPreferencePage.P_PROPERTY_CATEGORIES_ENABLED);
	}

	public static boolean showAdvancedProperties() {
		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_ADVANCED_PROPS);
	}

	public static boolean parseExternalSetters() {
		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_PARSE_EXT_SETTERS);
	}

	public static boolean dontParseInitComponents() {
		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_DONT_PARSE_INITCOMPS);
	}

//	public static boolean showGridAutomatically() {
//		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_GRID_SHOW_AUTO);
//	}

//	public static boolean parseEverything() {
//		return getDefault().getPreferenceStore().getBoolean(PreferencePage.P_PARSE_EVERYTHING);
//	}

	public static boolean makeComments() {
		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_COMMENTS);
	}

	public static boolean deleteNetBeansComments() {
		return getDefault().getPreferenceStore().getBoolean(MainPreferencePage.P_NETBEANS_CMNT);
	}

	public static String getInitGUIMethods() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_INITGUI_METHODS);
	}

	public static String getPropertyCategories() {
	    String cats = getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROPERTY_CATEGORIES);
	    int pos = cats.indexOf("|action");
	    if(pos < 0) {
	        cats = JiglooUtils.replace(cats, "Basic|", "Basic|action|");
        	getDefault().setPreference(MainPreferencePage.P_PROPERTY_CATEGORIES, cats);
	    }
		return cats;
	}

	public static String getPropertyExpertCategory() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROP_CATS_EXPERT);
	}

	public static void setPropertyExpertCategory(String name) {
		getDefault().getPreferenceStore().setValue(MainPreferencePage.P_PROP_CATS_EXPERT, name);
	}

	public static Vector getPropertyCategoriesExpanded() {
		String coded = getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROP_CATS_EXPANDED);
		String[] expanded = JiglooUtils.split("|", coded);
		Vector v = new Vector();
		for (int i = 0; i < expanded.length; i++) {
		    v.add(expanded[i]);
        }
		return v;
	}

	public static void setPropertyCategoriesExpanded(Vector expanded) {
	    String coded = "";
	    for(int i=0;i<expanded.size();i++) {
	        if(i != 0)
	            coded += "|";
	        coded += expanded.elementAt(i);
	    }
		getDefault().getPreferenceStore().setValue(MainPreferencePage.P_PROP_CATS_EXPANDED, coded);
	}

	public static Vector getLookAndFeels(boolean justDefaults) {
	    UIManager.LookAndFeelInfo[] installed = 
	        UIManager.getInstalledLookAndFeels();
	    Vector items = new Vector();
	    if(!justDefaults) {
	        String lafClasses =
	            getDefault().getPreferenceStore().getString(MainPreferencePage.P_LAF_CLASSES);
	        String[] vals = JiglooUtils.split(";", lafClasses);
	        for (int i = 0; i < vals.length; i++) {
	            LookAndFeelWrapper lafw = new LookAndFeelWrapper(vals[i]);
		        if(!items.contains(lafw))
		            items.add(lafw);
	        }
	    }
	    for (int i = 0; i < installed.length; i++) {
	        LookAndFeelWrapper lafw = 
	            new LookAndFeelWrapper(installed[i].getClassName(), installed[i].getName());
	        if(!items.contains(lafw))
	            items.add(lafw);
	    }
	    return items;
    }

	public static LookAndFeel getDefaultLAF() {
		String lafClasses =
		    getDefault().getPreferenceStore().getString(MainPreferencePage.P_LAF_CLASSES);
        String[] vals = JiglooUtils.split(";", lafClasses);
        for (int i = 0; i < vals.length; i++) {
            LookAndFeelWrapper lafw = new LookAndFeelWrapper(vals[i]);
            if(lafw.isDefault())
                return lafw.getLookAndFeel();
        }
        return null;
	}

	public static String getProtectStartTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROTECT_START_TAG);
	}

	public static String getProtectEndTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROTECT_END_TAG);
	}

	public static String getProtectLineTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_PROTECT_LINE_TAG);
	}

	public static String getHiddenStartTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_HIDE_START_TAG);
	}

	public static String getHiddenEndTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_HIDE_END_TAG);
	}

	public static String getHiddenLineTag() {
		return getDefault().getPreferenceStore().getString(MainPreferencePage.P_HIDE_LINE_TAG);
	}

	public static int getGridSize() {
	    return getIntPreference(MainPreferencePage.P_GRID_SIZE, 2000, 2);
	}
	
	public static int getIntPreference(String pref, int max, int min) {
	    int val = getDefault().getPreferenceStore().getInt(pref);
	    if(val < min)
	        return min;
	    if(val > max)
	        return max;
	    return val;
	}
	
	public static int getMaxFormWidth() {
	    return getIntPreference(MainPreferencePage.P_MAX_WIDTH, 2500, 200);
//		try {
//			return Integer.parseInt(getDefault().getPreferenceStore().getString(
//					MainPreferencePage.P_MAX_WIDTH));
//		} catch(Throwable t) {
//			return 2500;
//		}
	}

	public static int getMaxFormHeight() {
	    return getIntPreference(MainPreferencePage.P_MAX_HEIGHT, 2500, 200);
//		try {
//			return Integer.parseInt(getDefault().getPreferenceStore().getString(
//					MainPreferencePage.P_MAX_HEIGHT));
//		} catch(Throwable t) {
//			return 2500;
//		}
	}

	public final long msLeft() {
		if (true)
			return 1000;
		//trial1 = install time
		long trial1 = pstore.getLong("jigloo_trial1");
		//trial2 = last-used time
		long trial2 = pstore.getLong("jigloo_trial2");
		long now = System.currentTimeMillis();
		if (trial1 == 0) {
			trial1 = now;
			pstore.setValue("jigloo_trial1", trial1);
		}
		if (trial2 == 0)
			trial2 = now;
		if (now < trial2) { //someone's been resetting the clock!
			return -1;
		} else {
			trial2 = now;
			pstore.setValue("jigloo_trial2", trial2);
			return (NUM_TRIAL_DAYS * 24 * 3600 * 1000) - (now - trial1);
		}
	}

	public final int numNbUsed() {
		return 0; //return pstore.getInt("jigloo_nbcount");
	}

	public final boolean canSaveNBFile(Shell shell) {
		if (licenseValid())
			return true;
		int num = pstore.getInt("jigloo_nbcount");
		if (num >= NUM_TRIAL_NBS) {
			MessageDialog.openInformation(
				shell,
				"Unable to save Netbeans/Sun One file",
				"Unable to save Netbeans/Sun One file - the maximum number of\n"
					+ "files ("
					+ NUM_TRIAL_NBS
					+ ") has been saved for this evaluation version - to continue\n"
					+ "translating Netbeans/Sun One files, a professional license is required.\n"
					+ "Visit www.cloudgarden.com to purchase a license.");
			return false;
		}
		pstore.setValue("jigloo_nbcount", num + 1);
		return true;
	}

	public final boolean canUseProfFeature(IWorkbenchSite site, String action) {
		if (licenseValid())
			return true;
		if (msLeft() < 0) {
			if (site != null) {
				//new Exception().printStackTrace();
				MessageDialog.openInformation(
					site.getShell(),
					"Trial period expired",
					"\""
						+ action
						+ "\" - this is a feature of the professional version which you could\n"
						+ "access during your "
						+ NUM_TRIAL_DAYS
						+ " day trial period.\n\n"
						+ "Your trial has now expired, and if you wish to continue to use this\n"
						+ "feature a professional license is required.\n\n"
						+ "Visit www.cloudgarden.com to purchase a license.");
			}
			return false;
		}
		return true;
	}

	public final boolean licenseValid() {
		//return true;
		String licenseCode = pstore.getString(MainPreferencePage.P_LICENSE_CODE);
		if (licenseCode == null)
			return false;
		return licenseCode.equals(licStr);
	}

	public String getStringPreference(String pref) {
		return pstore.getString(pref);
	}

	public boolean getBooleanPreference(String pref) {
		return pstore.getBoolean(pref);
	}

	public int getIntPreference(String pref) {
		return pstore.getInt(pref);
	}

	public void setPreference(String pref, String value) {
		pstore.setValue(pref, value);
		savePluginPreferences();
	}

	public void setPreference(String pref, boolean value) {
		pstore.setValue(pref, value);
		savePluginPreferences();
	}

	public void setPreference(String pref, int value) {
		pstore.setValue(pref, value);
		savePluginPreferences();
	}

	private byte[] encrypt1(byte[] data, byte[] pass, boolean rev) {
		byte[] en = new byte[data.length];
		System.arraycopy(data, 0, en, 0, data.length);
		if (!rev) {
			for (int i = 0; i < en.length; i++) {
				int j = i % pass.length;
				if (i > 0)
					en[i] = (byte) (en[i] + en[i - 1] + pass[j]);
				else
					en[i] = (byte) (en[i] + pass[j]);
			}
		} else {
			for (int i = en.length - 1; i >= 0; i--) {
				int j = i % pass.length;
				if (i != en.length - 1)
					en[i] = (byte) (en[i + 1] + en[i] + pass[j]);
				else
					en[i] = (byte) (en[i] + pass[j]);
			}
		}
		return en;
	}

	private byte[] encrypt(byte[] data, byte[] pass) {
		return encrypt1(encrypt1(data, pass, false), pass, true);
	}

	private static final String[] hex =
		{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private String bytesToHex(byte[] bytes) {
		String rep = "";
		for (int i = 0; i < bytes.length; i++) {
			int b = (int) bytes[i];
			if (b < 0)
				b += 256;
			rep += hex[b / 16] + hex[b % 16];
			if (i % 20 == 19)
				rep += "\n";
		}
		return rep;
	}

	private String encryptToHex(String str, String key) {
		return bytesToHex(encrypt(str.getBytes(), key.getBytes()));
	}
	
	/**
	 * Returns the shared instance.
	 */
	public static jiglooPlugin getDefault() {
		return plugin;
	}

	private static boolean nagged  = false;
	
    /**
     * 
     */
    public static void showNagPopup(Shell parent) {
    	try {
    	    if(nagged)
    	        return;
    	    nagged = true;
    	    if(getDefault().licenseValid())
    	        return;
    	    MessageDialog.openInformation(parent, "Jigloo message: Non-commercial use only!", 
    	            "A commercial license has not yet been purchased for Jigloo.\n\n" +
    	            "Only non-commercial use is allowed!\n\n" +
    	            "To purchase a commercial license, please visit www.cloudgarden.com");
    	} catch(JiglooSecurityException t) {
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    }

	private static ErrorDialog errorDialog = null;

    public static void clearErrors() {
    	try {
    		if(errorDialog != null)
    			errorDialog.clear();
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    }
    
    public static void showErrors() {
    	try {
    		if(errorDialog != null && errorDialog.hasErrors())
    			errorDialog.open();
    	} catch(Throwable t) {
    		t.printStackTrace();
    	}
    }
    
    public static void handleError(Shell shell, String title, String msg,
            Throwable ex) {
        if (shell != null) {
        	if(errorDialog == null || errorDialog.isDisposed())
        		errorDialog = new ErrorDialog(shell, SWT.NULL);
        	errorDialog.addError(title, msg, ex);
        }
        jiglooPlugin.handleError(ex, msg);
    }

	public static void handleError(Throwable t, String msg) {
		writeToLog("Error: "+msg);
		writeToLog(t);
	}

	public static void handleError(Throwable t) {
		writeToLog(t);
	}

	public static boolean isMacOS() {
	    return isMacOS;
	}

	public static boolean isLinux() {
	    return isLinuxOS;
	}

	private static int versionNum = 0;

	public static boolean isVersion3Plus() {
		isVersion3();
		return versionNum >= 300;
	}
		
	public static boolean isVersion3() {
		if (versionNum == 0) {
		    String version = ResourcesPlugin.getPlugin().getBundle().getHeaders().get("Bundle-Version");
		    versionNum = Integer.parseInt(version.replace(".", "").substring(0, 3));
//			PluginVersionIdentifier vid = ResourcesPlugin.getPlugin().getDescriptor().getVersionIdentifier();
//			versionNum = vid.getMajorComponent() * 100 + vid.getMinorComponent() * 10 + vid.getServiceComponent();
//			System.out.println("GOT VERSION " + vid + ", " + vid.getQualifierComponent() + ", " + versionNum);
		}
		return versionNum == 300 ||  versionNum == 301;
	}

	private static int javaVersion = -1;
	
	/**
	 * Returns 13 if Java 1.3, 14 if Java 1.4 and 15 if Java 1.5
	 * otherwise returns -1.
	 */
	public static int getJavaVersion() {
		if(javaVersion > 0)
			return javaVersion;
	    try {
	        String jv = System.getProperty("java.version");
	        jv = JiglooUtils.replace(jv, ".", "");
	        jv = jv.substring(0, 2);
	        javaVersion = Integer.parseInt(jv);
	        return javaVersion;
	    } catch(Throwable t) {
	        System.err.println("Error getting java version from "+System.getProperty("java.version"));
	        t.printStackTrace();
	    }
	    return 14;
	}

	public static boolean isWindows() {
	    return isWindowsOS;
	}

	public static boolean canUseSwing() {
		return true; //!isMacOS();
	}

	public static boolean canUseCWT() {
		return canUseCWT;
	}
	
	public static boolean canUseForms() {
		return canUseForms;
	}
	
	public static boolean canUseGroupLayout() {
		return canUseGroupLayout;
	}
	
	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	} /**
																																																 * Returns the string from the plugin's resource bundle,
																																																 * or 'key' if not found.
																																																 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = jiglooPlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	} /**
																																															 * Returns the plugin's resource bundle,
																																															 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void stop(BundleContext context) throws Exception {
		ImageManager.disposeImages();
		FontManager.disposeFonts();
		ColorManager.disposeColors();
		CursorManager.disposeCursors();
		normalShutdown = true;
		super.stop(context);
	}

	public boolean getCreateMain() {
		return createMain;
	}

	public boolean getCreateShowGUI() {
		return createShowGUI;
	}

	public void setCreateMain(boolean b) {
		createMain = b;
	}

	public void setCreateShowGUI(boolean b) {
		createShowGUI = b;
	}

    public static String[] getCustomSWTClasses() {
        String swtClasses = getDefault().getStringPreference(
                MainPreferencePage.P_SWT_CUSTOMS);
        return JiglooUtils.split(";", swtClasses);
    }

    public static String[] getCustomSwingClasses() {
        String swtClasses = getDefault().getStringPreference(
                MainPreferencePage.P_SWING_CUSTOMS);
        return JiglooUtils.split(";", swtClasses);
    }
    
    public static String trimEnd(String str, String tag) {
    	if(str.endsWith(tag))
    		str = str.substring(0, str.length()-tag.length());
    	return str;
    }
    
    private static int decHeight = -1;
    private static int decWidth = -1;

	public static int getWindowDecorationHeight() {
		if (decHeight != -1)
			return decHeight;
		
		Runnable r = new Runnable() {
			public void run() {
				Shell shell = new Shell(Display.getDefault(), SWT.TITLE
						| SWT.RESIZE);
				shell.setBounds(10, 10, 300, 100);
				shell.setLayout(new FillLayout());
				Label lab = new Label(shell, SWT.NULL);
				shell.layout();
				org.eclipse.swt.graphics.Rectangle ca = lab.getBounds();
				org.eclipse.swt.graphics.Rectangle b = shell.getBounds();
				decHeight = b.height - ca.height;
				decWidth = b.width - ca.width;
				shell.dispose();
				shell = null;
			}
		};
		if(Display.getDefault().getThread().equals(Thread.currentThread()))
			r.run();
		else
			Display.getDefault().syncExec(r);
//        System.out.println("jiglooPlugin: decorationDimensions: " + decWidth + ", " + decHeight);
		return decHeight;
	}

	public static int getWindowDecorationWidth() {
		if (decWidth != -1)
			return decWidth;
		jiglooPlugin.getWindowDecorationHeight();
		return decWidth;
	}

    /**
     * @return
     */
    public static String getDefaultLAFClassName() {
        LookAndFeel laf = getDefaultLAF();
        if(laf == null)
            return UIManager.getCrossPlatformLookAndFeelClassName();
        else
            return laf.getClass().getName();
    }

    public static boolean canUseSWT_AWT() {
        return canUseSWT_AWT;
    }

    private static HashMap propertyCategories = null;
    private static Vector propertyCategoryNames = null;
    
    public static Vector getPropertyCategoryNames() {
        if(propertyCategoryNames == null)
            getPropertyCategoryMap();
        return propertyCategoryNames;
    }

    public static HashMap getPropertyCategoryMap() {
        if(propertyCategories != null)
            return propertyCategories;
        
        propertyCategories = new HashMap();
        propertyCategoryNames = new Vector();
        String codedProps = getPropertyCategories();
        
		String[] cats = JiglooUtils.split("#", codedProps);
		for (int j = 0; j < cats.length; j++) {
		    String propStr = cats[j];
		    String[] names = JiglooUtils.split("|",propStr);
		    String cat = names[0];
		    if(cat != null && cat.length() > 0) {
		        propertyCategoryNames.add(cat);
		        for (int i = 1; i < names.length; i++) {
		            if(names[i] != null && names[i].length() > 0)
		                propertyCategories.put(names[i], cat);
		        }
		    }
		}
		String expert = getPropertyExpertCategory();
		if(!propertyCategoryNames.contains(expert))
			propertyCategoryNames.add(expert);
		return propertyCategories;
    }

    /**
     * 
     */
    public static void restoreDefaultPropCategories() {
        propertyCategories = null;
        propertyCategoryNames = null;
    	getDefault().getPreferenceStore().setToDefault(MainPreferencePage.P_PROPERTY_CATEGORIES);
    	getPropertyCategoryMap();
    }

    public static void savePropCategories() {
        HashMap cats = new HashMap();
        Iterator it = propertyCategories.keySet().iterator();
        while(it.hasNext()) {
            Object key = it.next();
            Object cat = propertyCategories.get(key);
            String catStr = (String)cats.get(cat);
            if(catStr == null) {
                catStr = "";
            } else {
                catStr += "|";
            }
            catStr += key;
            cats.put(cat, catStr);
        }
        String coded = "";
        it = cats.keySet().iterator();
        while(it.hasNext()) {
            Object key = it.next();
            Object cat = cats.get(key);
            coded += key+"|"+cat+"#";
        }
    	getDefault().getPreferenceStore().setValue(MainPreferencePage.P_PROPERTY_CATEGORIES, coded);
    	getPropertyCategoryMap();
    }

	public void setKeyListener(KeyListener kl) {
		keyListener = kl;
	}

	/**
	 * @param cul
	 */
	public static void editorClosed(FormEditor ed) {
		if(activeEditor != null && activeEditor.equals(ed))
			activeEditor = null;
	}

    /**
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadClass(String className) throws ClassNotFoundException {
		Class clazz = null;
        FormEditor ed = jiglooPlugin.getActiveEditor();
		if(ed != null)
		    clazz = ed.loadClass(className);
		if(clazz == null)
		    clazz = Class.forName(className);
		return clazz;
    }

}
