/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.cloudgarden.jigloo.wizards;

import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewFormWizardPage extends WizardPage {
    private Text containerText;
    private Text fileText;
    private ISelection selection;
    private String fileName, templateName, newClassName, packageName;
    private Class superclass;
    private IJavaProject proj = null;

    /**
     * Constructor for SampleNewWizardPage.
     * 
     * @param pageName
     */
    public NewFormWizardPage(ISelection selection, String templateName,
            Class superclass, String newClassName, String packageName) {
        this(selection, templateName, newClassName, superclass, packageName);
    }

    public NewFormWizardPage(ISelection selection, String templateName,
            String newClassName, Class superclass, String packageName) {
        super("wizardPage");
        setTitle("Create a new " + templateName);
        setDescription("This wizard creates a java file for the generated GUI code");
        this.selection = selection;
        
        this.templateName = templateName;
        this.newClassName = newClassName;
        this.superclass = superclass;
        if (Object.class.equals(superclass))
            showSuperclass = false;
        this.packageName = packageName;
    }

    private NewFormWizardComposite wizardComposite;
    private boolean showSuperclass = true;

    public NewFormWizardComposite getWizardComposite() {
    	return wizardComposite;
    }
    
    public void showSuperclass(boolean show) {
        showSuperclass = show;
        if (wizardComposite != null)
            wizardComposite.showSuperclass(show);
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        wizardComposite = new NewFormWizardComposite(parent, SWT.NULL);

        wizardComposite.getBrowseButton().addSelectionListener(
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent e) {
                        handleBrowse();
                    }
                });

        wizardComposite.getSuperclassBrowseButton()
                .addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent e) {
                        handleSuperclassBrowse();
                    }
                });

        containerText = wizardComposite.getContainerText();
        fileText = wizardComposite.getFileText();

        Object sel = null;
        if(selection instanceof IStructuredSelection)
            sel = ((IStructuredSelection)selection).getFirstElement();
        else
            sel = selection;
        
        if (sel instanceof IPackageFragmentRoot) {
            IPackageFragmentRoot pf = (IPackageFragmentRoot) sel;
            containerText.setText("");
            proj = pf.getJavaProject();
        } else if (sel instanceof JavaProject) {
            JavaProject pf = (JavaProject) sel;
            containerText.setText("");
            proj = pf;
        } else if (sel instanceof IPackageFragment) {
            IPackageFragment pf = (IPackageFragment) sel;
            containerText.setText(pf.getElementName());
            proj = pf.getJavaProject();
        } else if (sel instanceof IJavaElement) {
            IJavaElement jel = (IJavaElement)sel;
            containerText.setText(jel.getParent().getElementName());
            proj = jel.getJavaProject();
        } else if (sel instanceof IFolder) {
            IFolder fol = (IFolder)sel;
            proj = JavaCore.create(fol.getProject());
            containerText.setText(JiglooUtils.getPackageName(fol, proj));
        } else if (sel instanceof IFile) {
            IFile file = (IFile)sel;
            proj = JavaCore.create(file.getProject());
            IContainer con = file.getParent();
            String pkg = "";
            if(con instanceof IFolder) {
                pkg = JiglooUtils.getPackageName((IFolder)con, proj);
            }
            containerText.setText(pkg);
        }
        containerText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        fileText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        wizardComposite.superclassText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        initialize();
        
        setControl(wizardComposite);
        wizardComposite.showSuperclass(showSuperclass);
        if (superclass != null) {
            wizardComposite.superclassText.setText(superclass.getName());
            wizardComposite.superclassText.setEditable(false);
        }
        if(packageName != null)
            wizardComposite.containerText.setText(packageName);
        if(proj == null) {
            updateStatus("No project - cannot create class!" +
            		"\nYou need to create or select a project for this new class");
            containerText.setEnabled(false);
            wizardComposite.getSuperclassBrowseButton().setEnabled(false);
            wizardComposite.browseButton.setEnabled(false);
            wizardComposite.superclassText.setEnabled(false);
            return;
        }
        initExtraButtons();
        dialogChanged();
    }

    protected void initExtraButtons() {
        final Button addMainCB = wizardComposite.getExtraButton1();
        final Button addShowGUICB = wizardComposite.getExtraButton2();

        //want the default always to be with a main method (they'd need to always keep de-selecting it).
        //addMainCB.setSelection(jiglooPlugin.getDefault().getCreateMain());

        addMainCB.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                jiglooPlugin.getDefault().setCreateMain(addMainCB.getSelection());
            }
        });
        addMainCB.setSelection(jiglooPlugin.getDefault().getCreateMain());

        addShowGUICB.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                jiglooPlugin.getDefault().setCreateShowGUI( addShowGUICB.getSelection());
                if(isSWT()) {
                	if (!addShowGUICB.getSelection()) {
                		addMainCB.setSelection(false);
                		addMainCB.setEnabled(false);
                		jiglooPlugin.getDefault().setCreateMain(false);
                	} else {
                		addMainCB.setEnabled(true);
                	}
                }
            }
        });
        addShowGUICB.setSelection(jiglooPlugin.getDefault().getCreateShowGUI());

	}

    private boolean isSWT() {
        Class sc = ((NewFormWizard) getWizard()).getFormSuperclass();
        if(sc != null && sc.getName().startsWith("org.eclipse")) {
        	return true;
        }
        return false;
    }
	/**
     * Tests if the current workbench selection is a suitable container to use.
     */

    private void initialize() {
        if (false && selection != null && selection.isEmpty() == false
                && selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) selection;
//            if (ssel.size() > 1)
//                return;
            Object obj = ssel.getFirstElement();

            if (obj instanceof IResource) {
                //
            } else if (obj instanceof IContainer) {
                IContainer container;
                container = (IContainer) obj;
                containerText.setText(container.getFullPath().toString());
            } else if (obj instanceof IPackageFragment) {
                containerText
                        .setText(((IPackageFragment) obj).getElementName());
            }
        }
        fileText.setText(newClassName);
    }

    private void handleSuperclassBrowse() {
        try {
            SelectionDialog dialog;
            IJavaSearchScope scope = null;
            if (proj != null)
                scope = SearchEngine
                        .createJavaSearchScope(new IJavaElement[] { proj });
            else
                scope = SearchEngine.createWorkspaceScope();
            dialog = JavaUI.createTypeDialog(getShell(),
                    new ProgressMonitorDialog(getShell()), scope,
                    IJavaElementSearchConstants.CONSIDER_CLASSES, false);
            dialog.setInitialSelections(new Object[] { wizardComposite.superclassText.getText() });
            dialog.setTitle("Select new class");
            dialog.setMessage("Select new class");
            if (dialog.open() == IDialogConstants.CANCEL_ID)
                return;
            Object[] types = dialog.getResult();
            dialog.close();
            if (types == null || types.length == 0)
                return;
            IType type = (IType) types[0];
            wizardComposite.superclassText.setText(type.getFullyQualifiedName());
        } catch (JavaModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Uses the standard container selection dialog to choose the new value for
     * the container field.
     */

    private void handleBrowse() {

        SelectionDialog dialog = null;
        try {
            dialog = JavaUI.createPackageDialog(getShell(), proj, SWT.NONE);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (dialog.open() == SelectionDialog.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                IJavaElement selEl = (IJavaElement) result[0];
                containerText.setText(selEl.getElementName());
                dialogChanged();
            }
        }
    }

    /**
     * Ensures that both text fields are set.
     */

    private void dialogChanged() {
        if(proj == null) {
            updateStatus("No project - cannot create class!" +
            		"\nYou need to create or select a project for this new class");
            fileText.setEnabled(false);
            containerText.setEnabled(false);
            wizardComposite.getSuperclassBrowseButton().setEnabled(false);
            wizardComposite.browseButton.setEnabled(false);
            wizardComposite.superclassText.setEnabled(false);
            return;
        }
        
        fileName = fileText.getText();

        NewFormWizard wiz = (NewFormWizard) getWizard();
        String msg = "";

        Class sc = wiz.getFormSuperclass();
        if (sc != null) {
            Vector am = JiglooUtils.getAbstractMethods(sc);
            boolean isAbs = am != null && am.size() > 0;
            boolean isPubAcc = ClassUtils.isPublicAccess(sc);

            if (isAbs) {
                msg = "Superclass is abstract - will add getGUIBuilderInstance and stub methods";
            } else if (!isPubAcc) {
                msg = "Superclass is non-public - will add getGUIBuilderInstance method";
            }
        }
        if(isSWT()) {
        	wizardComposite.getSWTLibButton().setVisible(true);
        	wizardComposite.getExtraButton2().setVisible(true);
        } else {
        	wizardComposite.getSWTLibButton().setVisible(false);
        	wizardComposite.getExtraButton2().setVisible(false);
        }
        wizardComposite.getMsgLabel().setText(msg);

        if (wiz.classExists()) {
            updateStatus("A class with that name already exists - try a different name or package");
            return;
        }

        if (fileName.length() == 0) {
            updateStatus("Class name must be specified");
            return;
        }
        int dotLoc = fileName.indexOf('.');
        if (dotLoc != -1) {
            updateStatus("Illegal character");
            return;
        }
        updateStatus(null);
    }

    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    public String getContainerName() {
        return containerText.getText();
    }

    public String getFileName() {
        fileName = fileText.getText();
        return fileName;
    }

    public String getFinalFormSuperclassName() {
        return wizardComposite.superclassText.getText();
    }

    public boolean addSWTLibs() {
        return wizardComposite.getSWTLibButton().getSelection();
    }

}