package com.cloudgarden.jigloo.wizards;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * This is the Wizard from which all Jigloo Wizards are derived. It contains
 * protected methods which should be overwritten to define the location
 * and name of the template file or files etc. The templates should take this
 * general form (the initGUI method is not necessary):
 * 
<pre>
%PKG_NAME%

public class %CLASS_NAME% extends %SUPERCLASS_NAME% {

	public static void main(String[] args) {
		%CLASS_NAME% inst = new %CLASS_NAME%();
		// do any default operations on inst...
	}
	
	public %CLASS_NAME%() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
		// ... do default initialization
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
</pre>
 * %PKG_NAME% should *always* be present, and will be automatically replaced.
 * <BR>
 * %CLASS_NAME% will be replaced by the user-defined class name (which they
 * will enter in the wizard when creating the class).
 * <BR>
 * %SUPERCLASS_NAME% is defined by overwriting NewFormWizard.getFormSuperclassName()
 * <P>
 * Template files should live in the com/cloudgarden/jigloo/wizards/templates folder, but
 * may live in a subfolder of this folder, provided the getTemplatePackage points to this
 * subfolder. For example, NewSWTInheritanceExampleWizard overrides getTemplatePackage
 * to return "swt/" and it's templates live in the com/cloudgarden/jigloo/wizards/templates/swt
 * folder.
 */

public abstract class NewFormWizard extends Wizard implements INewWizard {
    protected NewFormWizardPage page;
    protected ISelection selection;
    protected IJavaProject proj;
    protected IWorkbench workbench;
    protected IEditorPart editor;

    /**
     * Constructor for SampleNewWizard.
     */
    public NewFormWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    /**
     * Adding the page to the wizard.
     */

    public void addPages() {
        String temp = getTemplateName().toLowerCase();
        if (!jiglooPlugin.canUseSwing()
                && (temp.startsWith("j") || temp.indexOf("swing") >= 0)) {
            MessageDialog.openInformation(getShell(),
                    "Unable to use Swing on Mac",
                    "Sorry - Jigloo is unable to create Swing GUIs on a Mac");
            return;
        }
        Class sc = Object.class;
        if (!isExample()) {
            try {
                sc = Class.forName(getFormSuperclassName());
            } catch (ClassNotFoundException e) {
            	System.err.println("NewFormWizard - unable to find superclass "+e);
            }
        }
        if (!isExample())
            page = new NewFormWizardPage(selection, getTemplateName(), sc,
                    "New" + getTemplateName(), null);
        else
            page = new NewFormWizardPage(selection, getTemplateName(), sc,
                    getTemplateName(), getPackageName());
        addPage(page);
    }

    boolean classExists() {
        try {
            String containerName = page.getContainerName();
            String fileName = page.getFileName() + ".java";
            IFile file = getFile(containerName, fileName, false);
            if (file == null)
                return false;
            if (file.exists())
                return true;
            //			String fileName = page.getFileName() + ".form";
            //			IFile file = getFile(containerName, fileName);
            //			if (file.exists())
            //				return true;
            //			int pos = fileName.indexOf(".form");
            //			if (pos != -1) {
            //				fileName = fileName.substring(0, pos) + ".java";
            //				file = getFile(containerName, fileName);
            //				if (file.exists())
            //					return true;
            //			}
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish() {

        final String containerName = page.getContainerName();
        final String fileName = page.getFileName() + ".java";
        try {

            final IFile file = getFile(containerName, fileName);
            if (file.exists()) {
                MessageDialog.openInformation(getShell(), "Class Exists",
                        "A class with that name already exists");
                return false;
            }
            IRunnableWithProgress op = new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    try {
                        doFinish(file, monitor);
                    } catch (CoreException e) {
                        throw new InvocationTargetException(e);
                    } finally {
                        monitor.done();
                    }
                }
            };
            getContainer().run(false, false, op);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable realException = e.getTargetException();
            MessageDialog.openError(getShell(), "Error", realException.getMessage());
            return false;
        } catch (Throwable e) {
            e.printStackTrace();
            MessageDialog.openError(getShell(), "Error", e.getMessage());
            return false;
        }
        return true;
    }

    protected void updateProjectClassPath() {
	}

	private IFile getFile(String containerName, String fileName)
            throws CoreException {
        return getFile(containerName, fileName, true);
    }

    private HashMap containers = new HashMap();
    
    private IResource getContainer(String containerName, boolean createIfNeeded)  throws CoreException {
        
        Object con = containers.get(containerName);
        if(!createIfNeeded && "null".equals(con))
            return null;
        if(con instanceof IResource)
            return (IResource)con;
        
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IPackageFragment[] frags = proj.getPackageFragments();
        IJavaElement frag = null;
        for (int i = 0; i < frags.length; i++) {
            //			System.err.println("LOOKING FOR "+ frags[i].getElementName()	+ ", "+ containerName);
            if (frags[i].getElementName().equals(containerName)) {
                frag = frags[i];
                break;
            }
        }
        if (frag == null || frag.isReadOnly()) {
            IContainer cont = root.getContainerForLocation(proj.getResource()
                    .getLocation());
            IPackageFragmentRoot[] pfrs = proj.getPackageFragmentRoots();
            IPackageFragmentRoot pfr = null;
            for (int i = 0; i < pfrs.length; i++) {
                pfr = pfrs[i];
                if (pfr.getKind() == IPackageFragmentRoot.K_SOURCE)
                    break;
                pfr = null;
            }
            if (pfr != null) {
                if (createIfNeeded) {
                    frag = pfr.createPackageFragment(containerName, false, null);
                } else {
                    frag = pfr.getPackageFragment(containerName);
                    if (!frag.exists())
                        return null;
                }
            } else {
                frag = proj;
            }
        }
        IResource res = frag.getCorrespondingResource();
        if(res == null)
            containers.put(containerName, "null");
        else
            containers.put(containerName, res);
        return res;
    }
    
    private IFile getFile(String containerName, String fileName,
            boolean createIfNeeded) throws CoreException {
        IFile file = null;
        IResource res = getContainer(containerName, createIfNeeded);
        if (res instanceof IFolder) {
            file = ((IFolder) res).getFile(fileName);
        } else {
            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
                    .getRoot();
            file = workspaceRoot.getFileForLocation(proj.getResource()
                    .getLocation().addTrailingSeparator().append(fileName));
        }
        return file;
    }

    private IFolder getFolder(IFile guiFile, String containerName, String fileName)
            throws CoreException {
        IResource res = getContainer(containerName, false);
        IFolder file = null;
        if (res instanceof IFolder) {
        	if("".equals(fileName))
        		return (IFolder) res;
            file = ((IFolder) res).getFolder(fileName);
        } else {
            IWorkspaceRoot workspaceRoot = guiFile.getWorkspace().getRoot();
            IContainer[] conts = workspaceRoot.findContainersForLocation(guiFile.getParent().getLocation()
            		.addTrailingSeparator().append(containerName).addTrailingSeparator().append(fileName));
            file = (IFolder) conts[0];
        }
        return file;
    }

    /**
     * If this method returns true then this Wizard produces a single file, which
     * should be named by overwriting the getTemplateName method.
     * <P>
     * If this method returns false then this Wizard produces multiple files, which
     * should be named by overwriting the getTemplateNames method.
     * @see #getTemplateName, #getTemplateNames 
     */
    protected boolean isSingleFile() {
        return true;
    }

    /**
     * Overwrite this method to return true if the class being generated is an example
     * rather than a standard GUI class. Default is false.
     */
    protected boolean isExample() {
        return false;
    }

    /**
     * Overwrite this method to return false if you *don't* want an abstract methods in the
     * template class to be automatically generated as stub methods. Default is true.
     */
    protected boolean addAbstractMethods() {
        return true;
    }

    /**
     * Overwrite this method to return true if this class needs the SWTResourceManager
     * class to be generated in the project. Default is false.
     */
    protected boolean needsSWRResMan() {
        return false;
    }

    private void doFinish(final IFile file, IProgressMonitor monitor)
            throws CoreException {
    	
    	updateProjectClassPath();
    	FileUtils.createFolder(file.getParent());
    	
        String pkgName = page.getContainerName();
        String clsName = page.getFileName();
        String superName = page.getFinalFormSuperclassName();
        
//        if(!getFormSuperclassName().equals(superName)) {
//        	//TODO - test that new superclass is a Swing or SWT widget?
//        }
        
        jiglooPlugin.getDefault().setPreference(MainPreferencePage.P_ADD_SWT_LIBS, page.addSWTLibs());
        
        if(needsSWRResMan())
            FormEditor.insertSWTResource();
        
        String template = getTemplateName();
        if (isSingleFile()) {
            if (!superName.startsWith("javax.swing")
                    && !superName.startsWith("org.eclipse.swt")) {
                String tmp = JiglooUtils.resolveBasicParentName(proj.findType(superName));
                if (tmp != null)
                    template = tmp;
            }
            
        	if(page.addSWTLibs() && superName.startsWith("org.eclipse."))
        		FileUtils.addSWTToClassPath(proj);
        	
            saveAndOpen(file, monitor, pkgName, clsName, superName, template+".txt",
                    false, doBuildAtEnd());
        } else {
            String[] templates = getTemplateNames();
            String containerName = page.getContainerName();
            for (int i = 0; i < templates.length; i++) {
                String tmp = templates[i];
                String fileName = tmp;
                fileName = JiglooUtils.replace(fileName, template, clsName);
                if(fileName.indexOf(".") < 0) {
                    if (tmp.startsWith("icons")) {
                        fileName += ".gif";
                        tmp += ".gif";
                    } else {
                        fileName += ".java";
                        tmp += ".txt";
                    }
                }
                IFile tfile = null;
                int pos = tmp.lastIndexOf("/");
                if(pos > 0) {
                	if(tmp.startsWith("resources/") && tmp.endsWith(".properties")) {
                		tfile = FileUtils.getResourceFile(fileName.substring("resources/".length()), 
                				proj.getProject(), containerName, file.getParent());
                		FileUtils.createFolder(tfile.getParent());
                	} else {
                		IFolder fldr1 = getFolder(file, containerName, tmp.substring(0, pos));
                		FileUtils.createFolder(fldr1);
                		tfile = getFile(containerName, fileName);
                	}
                } else {
                	tfile = getFile(containerName, fileName);
                }
                if (i != templates.length - 1) {
                    saveAndOpen(tfile, monitor, pkgName, clsName, null, tmp,
                            true, false);
                } else {
                	
                	if(page.addSWTLibs() && superName.startsWith("org.eclipse."))
                		FileUtils.addSWTToClassPath(proj);
                	
                    saveAndOpen(file, monitor, pkgName, clsName, null, tmp,
                            false, doBuildAtEnd());
                }
            }
//            Display.getDefault().asyncExec(new Runnable() {
//                public void run() {
//                    IWorkbenchPage page = PlatformUI.getWorkbench()
//                            .getActiveWorkbenchWindow().getActivePage();
//                    try {
//                        editor = page.openEditor(new FileEditorInput(file),
//                                "com.cloudgarden.jigloo.editors.FormEditor");
//                    } catch (PartInitException e) {
//                    }
//                }
//            });
        }
    }

    protected void build(IFile javaFile, IProgressMonitor monitor) {
        try {
            //System.out.println("BUILD "+javaFile);
            monitor.setTaskName("Building workspace");
            IProject proj = javaFile.getProject();
            proj.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
        } catch (OperationCanceledException can) {
        	System.err.println("Build cancelled");
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error building "+javaFile.getName());
        }
    }

    public Class getFormSuperclass() {
    	Class sc = null;
    	try {
    		sc = ClassLoaderCache.loadClass(proj.getProject(), page
    				.getFinalFormSuperclassName(), getClass().getClassLoader(), false);
    	} catch (Throwable t) {
    		//t.printStackTrace();
    	}
    	return sc;
    }
    
    protected boolean doBuildAtEnd() {
    	return true;
    }
    
    private void saveAndOpen(final IFile file, final IProgressMonitor monitor,
    		String pkgName, String clsName, String superName,
			String templateName, boolean saveOnly, boolean build)
    throws CoreException {
    	
    	if (monitor != null)
    		monitor.beginTask("Creating " + file, 2);
    	
    	try {
    		DataInputStream stream = null;
    		
    		if (templateName.endsWith("gif")) {
    			stream = FileUtils.getTemplateResourceAsStream(getClass(), getTemplatePackage()+templateName);
    			if (!file.exists())
    				file.create(stream, true, monitor);
    			else
    				file.setContents(stream, true, true, monitor);
    		} else if (templateName.endsWith("png")) {
    			stream = FileUtils.getTemplateResourceAsStream(getClass(), getTemplatePackage()+templateName);
    			if (!file.exists())
    				file.create(stream, true, monitor);
    			else
    				file.setContents(stream, true, true, monitor);
    		} else {
    			stream = FileUtils.getTemplateResourceAsStream(getClass(), getTemplatePackage()+templateName);
    			String c = "";
    			String line;
    			while ((line = stream.readLine()) != null)
    				c += line + "\n";
    			
    			try {
    				if (superName != null && addAbstractMethods()) {
    					Class sc = null;
    					sc = ClassLoaderCache.loadClass(proj.getProject(),
    							superName, getClass().getClassLoader(), false);
    					Vector am = JiglooUtils.getAbstractMethods(sc);
    					if (!ClassUtils.isPublicAccess(sc) || am != null) {
    						int insert = c.lastIndexOf("}");
    						String start = c.substring(0, insert);
    						String end = c.substring(insert);
    						//System.out.println("LOADED CLASS "+sc+", "+cmp+",
    						// "+cmp.isAssignableFrom(sc));
    						
    						if (Composite.class.isAssignableFrom(sc))
    							start += JiglooUtils.getTemplateAsString(
    									"getGUIBInstSWT", "\n");
    						else
    							start += JiglooUtils.getTemplateAsString(
    									"getGUIBInstSwing", "\n");
    						
    						if (am != null) {
    							for (int i = 0; i < am.size(); i++) {
    								Method m = (Method) am.elementAt(i);
    								start += JiglooUtils.getImplementor(m);
    							}
    						}
    						c = start + end;
    					}
    				}
    			} catch (Throwable e) {
    				System.err.println("NewFormWizard: Error trying to load class "
    						+ superName + ", " + e);
    				//e.printStackTrace();
    			}
    			
    			String icons = "";
    			if (!"".equals(pkgName))
    				icons = JiglooUtils.replace(pkgName, ".", "/") + "/";
    			icons += "icons";
    			c = JiglooUtils.replace(c, "%ICONS%", icons);
    			
    			if (clsName != null) {
    				if (!"".equals(pkgName))
    					c = JiglooUtils.replace(c, "%FULL_CLASS_NAME%", pkgName+"."+clsName);
    				else
    					c = JiglooUtils.replace(c, "%FULL_CLASS_NAME%", clsName);
    			}
    			
    			if (!"".equals(pkgName))
    				pkgName = "package " + pkgName + ";";
    			c = JiglooUtils.replace(c, "%PKG_NAME%", pkgName);
    			if (clsName != null)
    				c = JiglooUtils.replace(c, "%CLASS_NAME%", clsName);
    			if (superName != null)
    				c = JiglooUtils.replace(c, "%SUPERCLASS_NAME%", superName);
    			
    			if(JiglooUtils.spacesForTabs()) {
    				c = JiglooUtils.replace(c, "\t", JiglooUtils.getTabString());
    			}
    			
    			if (!isExample()) {
    				boolean createShowGUI = jiglooPlugin.getDefault().getCreateShowGUI();
    				boolean createMain = jiglooPlugin.getDefault().getCreateMain();
    				if (!createMain) {
    					int mainSt = c.indexOf("public static void main");
    					if(mainSt >= 0) {
    						int mainEnd = getClosingBrace(c, mainSt);
    						if(mainEnd > mainSt) {
    							c = c.substring(0, mainSt) + c.substring(mainEnd + 1);
    						}
    					}
    				}
    				if (!createShowGUI) {
    					int mainSt = c.indexOf("public static void showGUI");
    					if(mainSt > 0) {
    						int mainEnd = getClosingBrace(c, mainSt);
        					if(mainEnd > mainSt) {
        						c = c.substring(0, mainSt) + c.substring(mainEnd + 1);
        					}
    					}
    				}
    			}
    			
    			ByteArrayInputStream sbis = new ByteArrayInputStream(c.getBytes());
    			if (!file.exists())
    				file.create(sbis, true, monitor);
    			else
    				file.setContents(sbis, true, true, monitor);
    		}
    		stream.close();
    	} catch (IOException e) {
    	}
    	
    	if (build)
    		build(file, monitor);
    	
    	if (saveOnly)
    		return;
    	
    	if (monitor != null) {
    		monitor.worked(1);
    		monitor.setTaskName("Opening file for editing...");
    	}
    	Display.getDefault().asyncExec(new Runnable() {
    		public void run() {
    			IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
    			try {
    				editor = page.openEditor(new FileEditorInput(file),
    				"com.cloudgarden.jigloo.editors.FormEditor");
    			} catch (Exception e) {
    				jiglooPlugin.handleError(e);
    			}
    		}
    	});
    	
    }

    private int getClosingBrace(String str, int start) {
    	start = str.indexOf("{", start);
    	if(start == -1)
    		return -1;
    	start++;
    	int count = 1;
    	while(count > 0 && start < str.length()) {
    		if(str.charAt(start) == '{')
    			count++;
    		if(str.charAt(start) == '}')
    			count--;
    		start++;
    	}
    	if(count == 0)
    		return start;
    	return -1;
    }
    
    /**
     * If isSingleFile() returns true, this method will be used to get the name
     * of the template file which will be used to generate the class for this wizard.
     * If isSingleFile() returns false, then this method should return the *main* class
     * of the set of classes (this is the class that the user will be able to name using
     * the wizard).
     */
    protected abstract String getTemplateName();
    
    /**
     * Overwrite this method to return the additional path inside the
     * com/cloudgarden/jigloo/wizards/templates folder where the template
     * or templates live. The value must end in a "/", eg "swt/".
     */
    protected String getTemplatePackage() {
        return "";
    }
    
    /**
     * If isSingleFile() returns false, this method will be used to get the names
     * of all the template files which will be used to generate classes for this wizard.
     */
    protected String[] getTemplateNames() {
        return null;
    }

    /**
     * If overwritten will be used to suggest a superclass to the user when they
     * use the wizard to create a new class (the user may then change this value
     * to extend a different class).
     */
    protected abstract String getFormSuperclassName();

    /**
     * We will initialize file contents with a sample text.
     */
    private InputStream openContentStream() {
        InputStream strm;
        strm = getClass().getClassLoader()
                .getResourceAsStream(
                        "com/cloudgarden/jigloo/wizards/" + getTemplateName()
                                + ".form");
        //System.out.println("got stream " + strm);
        return strm;
    }

    private void throwCoreException(String message) throws CoreException {
        IStatus status = new Status(IStatus.ERROR, "com.cloudgarden.jigloo",
                IStatus.OK, message, null);
        throw new CoreException(status);
    }

    /**
     * We will accept the selection in the workbench to derive the project and
     * package that the class or classes should be generated in.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
//        ISelection pes = JiglooUtils.getPackageExplorerSelection();
//        if (pes instanceof IStructuredSelection)
//            selection = (IStructuredSelection) pes;
        this.selection = selection;
        this.workbench = workbench;
//      System.out.println("GOT SEL "+selection+", "+selection.getClass());
        StructuredSelection ssel = (StructuredSelection) selection;
        proj = null;
        Object[] sels = ssel.toArray();
        if(sels.length == 0) {
            ssel = (StructuredSelection)JiglooUtils.getPackageExplorerSelection();
            if(ssel == null) {
            	MessageDialog.openError(getShell(), "Error initializing wizard", 
            			"Please check that you have created a Java project.");
            	return;
            }
//            System.err.println("SEL==NULL - using "+ssel);
            sels = ssel.toArray();
        }
        this.selection = ssel;
        
        for (int i = 0; i < sels.length; i++) {
            Object sel = sels[i];
//            System.out.println("GOT SEL "+sel+", "+sel.getClass());
            if (sel instanceof IJavaProject) {
                proj = (IJavaProject) sel;
            } else if (sel instanceof IJavaElement) {
                proj = ((IJavaElement) sel).getJavaProject();
                return;
            } else if (sel instanceof IFolder) {
                proj = JavaCore.create(((IFolder)sel).getProject());
            } else if (sel instanceof IFile) {
                proj = JavaCore.create(((IFile)sel).getProject());
            }
        }
//      System.out.println("GOT PROJ "+proj.getElementName());
        if (proj != null)
            return;
        try {
            IJavaModel model = JavaCore.create(ResourcesPlugin.getWorkspace()
                    .getRoot());
            IJavaProject[] projs = model.getJavaProjects();
            if (projs != null && projs.length > 0)
                proj = projs[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overwrite to return a default package name for the generated class (eg,
     * "examples.swing.slidergame). Default is null, which will use the package name
     * of the currently-selected package in Eclipse.
     */
    protected String getPackageName() {
        return null;
    }
    
}