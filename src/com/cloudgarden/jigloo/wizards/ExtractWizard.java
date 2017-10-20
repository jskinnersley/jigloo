package com.cloudgarden.jigloo.wizards;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

public class ExtractWizard extends NewFormWizard {
	private FormComponent origComp;
	private FormComponent comp;
	private IJavaElement pkg;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public ExtractWizard(FormComponent origComp, FormComponent comp, IJavaProject proj, IJavaElement pkg) {
		super();
		this.origComp = origComp;
		this.comp = comp;
		this.proj = proj;
		this.pkg = pkg;
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		String temp = getTemplateName().toLowerCase();
		if (!jiglooPlugin.canUseSwing() && (temp.startsWith("j") || temp.indexOf("swing") >= 0)) {
			MessageDialog.openInformation(
				getShell(),
				"Unable to use Swing on Mac",
				"Sorry - Jigloo is unable to create Swing GUIs on a Mac");
			return;
		}
		page =
			new ExtractWizardPage(
				new StructuredSelection(pkg),
				getTemplateName(),
				JiglooUtils.capitalize(comp.getNameInCode()),
				comp.getMainClass(), null);
		page.showSuperclass(true);
		page.setTitle("Extract as new java class.");
		page.setMessage("Saves this element as a new java class.");
		addPage(page);
	}

    public boolean classExists() {
		try {
			String containerName = page.getContainerName();
			String fileName = page.getFileName() + ".form";
			IFile file = getFile(containerName, fileName);
			if (file.exists())
				return true;
			int pos = fileName.indexOf(".form");
			if (pos != -1) {
				fileName = fileName.substring(0, pos) + ".java";
				file = getFile(containerName, fileName);
				if (file.exists())
					return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {

		final String containerName = page.getContainerName();
		final String fileName = page.getFileName() + ".java";
		try {
			final IFile file = getFile(containerName, fileName);
			if (file.exists()) {
				MessageDialog.openInformation(getShell(), "Class Exists", "A class with that name already exists");
				return false;
			}
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
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
		} catch (CoreException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	private IFile getFile(String containerName, String fileName) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPackageFragment[] frags = proj.getPackageFragments();
		IJavaElement frag = null;
		for (int i = 0; i < frags.length; i++) {
			if (frags[i].getElementName().equals(containerName)) {
				frag = frags[i];
				break;
			}
		}
		if (frag == null) {
			System.err.println("FRAG == null");
			frag = proj;
		}
		IFile file = null;
		IResource res = frag.getCorrespondingResource();
		if (res instanceof IFolder) {
			file = ((IFolder) res).getFile(fileName);
		} else {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			file =
				workspaceRoot.getFileForLocation(
					proj.getResource().getLocation().addTrailingSeparator().append(fileName));
		}
		return file;
	}

	protected String getTemplateName() {
		if (jiglooPlugin.canUseSwing()) {
			if (comp.isSubclassOf(JPanel.class)) {
				return "JPanel";
			} else if (comp.isSubclassOf(JFrame.class)) {
				return "JFrame";
			} else if (comp.isSubclassOf(JApplet.class)) {
				return "JApplet";
			} else if (comp.isSubclassOf(JInternalFrame.class)) {
				return "JInternalFrame";
			} else if (comp.isSubclassOf(JComponent.class)) {
				return "JComponent";
			}
		}
		if(comp.isSubclassOf(Composite.class))
			return "Composite";
		return "Control";
	}

	protected String getTemplatePackage() {
		if (jiglooPlugin.canUseSwing() && comp.isSwing())
			return "swing/";
		return "swt/";
	}

	public void open(Shell parent) {
		try {
			WizardDialog dialog = new WizardDialog(parent, this);
			dialog.setBlockOnOpen(true);
			dialog.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doFinish(final IFile file, IProgressMonitor monitor) throws CoreException {
		final boolean changeClass = page.getWizardComposite().getExtraButton1().getSelection();
		final boolean saveAsCustom = page.getWizardComposite().getExtraButton2().getSelection();
		
		String pkgName = page.getContainerName();
		String clsName = page.getFileName();
		final String fullClassName = pkgName+"."+clsName;
		String superName = comp.getTranslatedClassName();
		String template = getTemplateName();

		IEditorPart ed = saveAndOpen(file, monitor, pkgName, clsName, superName, template);
		final FormEditor fed = (FormEditor) ed;
		final FormComponent copy = comp.getCopy(true, fed);
		copy.setAllExistsInCode(true);
		fed.setNewRoot(copy);
		
		final Runnable r = new Runnable() {
			public void run() {
				FormEditor origEd = origComp.getEditor();
				if(changeClass) {
					for(int i=origComp.getChildCount()-1; i >= 0 ;i--) {
						origEd.removeComponent(origComp.getChildAt(i), true);
					}
					LayoutDataWrapper ldw = origComp.getLayoutDataWrapper();
					boolean ldwSet = ldw.isSet();
					origComp.setLayoutDataWrapper(null);
					origComp.getJavaCodeParser().removeFromCode(origComp.getLayoutWrapper());
					origComp.resetLayoutWrapper();
					origComp.unsetProperties();
					origComp.changeClass(fullClassName);
					if(ldwSet) {
						origComp.setLayoutDataWrapper(ldw);
						origComp.setSetProperty("layoutData");
						origComp.repairInCode();
					}
					origEd.setSelectedComponent(origComp, false);
					origEd.setDirtyAndUpdate(true, true, true);
					
					FormEditor.classFileChanged(fullClassName, origEd.getProject());
//	            	ClassLoaderCache.deleteClassLoader(origEd.getProject());
//	            	origEd.clearClassLoader();
//	            	origEd.handleClassCompiled(fullClassName);
				}
				
				if(saveAsCustom) {
					JiglooUtils.addToCustomClasses(fullClassName, origEd.getMode());
				}
			}
		};
		
		fed.setPostOpenRunnable(r);
		
	}

	public IEditorPart saveAndOpen(
		final IFile file,
		IProgressMonitor monitor,
		String pkgName,
		String clsName,
		String superName,
		String templateName)
		throws CoreException {
		if (monitor != null)
			monitor.beginTask("Creating " + file, 2);
		try {
			DataInputStream stream;// = getResourceAsStream(templateName);
			stream = FileUtils.getTemplateResourceAsStream(getClass(), getTemplatePackage()+templateName+".txt");
			String c = "";
			String line;
			while ((line = stream.readLine()) != null)
				c += line + "\n";

			if (!"".equals(pkgName))
				pkgName = "package " + pkgName + ";";
			c = JiglooUtils.replace(c, "%PKG_NAME%", pkgName);
			c = JiglooUtils.replace(c, "%CLASS_NAME%", clsName);
			c = JiglooUtils.replace(c, "%SUPERCLASS_NAME%", superName);
			ByteArrayInputStream sbis = new ByteArrayInputStream(c.getBytes());
			file.create(sbis, true, monitor);
			stream.close();
		} catch (IOException e) {}
		if (monitor != null) {
			monitor.worked(1);
			monitor.setTaskName("Opening file for editing...");
		}
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					editor = page.openEditor(new FileEditorInput(file), "com.cloudgarden.jigloo.editors.FormEditor");
				} catch (PartInitException e) {}
			}
		});
		if (monitor != null)
			monitor.worked(1);
		return editor;
	}

	private DataInputStream getResourceAsStream(String templateName) {
		DataInputStream strm;
		strm =
			new DataInputStream(
				getClass().getClassLoader().getResourceAsStream(
					"com/cloudgarden/jigloo/wizards/templates/" + templateName + ".txt"));
		return strm;
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		StructuredSelection ssel = (StructuredSelection) selection;
		proj = null;
		Object[] sels = ssel.toArray();
		for (int i = 0; i < sels.length; i++) {
			Object sel = sels[i];
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
		if (proj != null)
			return;
		try {
			IJavaModel model = JavaCore.create(ResourcesPlugin.getWorkspace().getRoot());
			IJavaProject[] projs = model.getJavaProjects();
			if (projs != null && projs.length > 0)
				proj = projs[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getFormSuperclassName() {
		return null;
	}

}