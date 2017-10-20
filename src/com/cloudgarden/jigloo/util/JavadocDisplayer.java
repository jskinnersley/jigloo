/*
 * Created on Oct 12, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.actions.OpenBrowserUtil;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JavadocDisplayer {

	public void run(IJavaElement element, Shell shell) {
		if (element == null)
			return;
		try {

			String labelName = element.getElementName();
//			String labelName = 
//				org.eclipse.jdt.internal.ui.viewsupport.JavaElementLabels.getElementLabel(
//					element,
//					org.eclipse.jdt.internal.ui.viewsupport.JavaElementLabels.M_PARAMETER_TYPES);
//			System.out.println("GOT LABEL NAME "+labelName+", "+element);
			
			URL baseURL = JavaUI.getJavadocBaseLocation(element);
			if (baseURL == null) {
				IPackageFragmentRoot root =
					JavaModelUtil.getPackageFragmentRoot(element);
				if (root != null
				        && root.getKind() == IPackageFragmentRoot.K_BINARY) {
				    String message = "The documentation location for ''{0}'' has not been configured." +
				    " For elements from libraries specify the Javadoc location URL on the properties page of the parent JAR (''{1}'')";
//				    try {
//				        message = ActionMessages.getString("OpenExternalJavadocAction.libraries.no_location"); //$NON-NLS-1$
//				    } catch(Throwable t) {}
				    showMessage(
				            shell,
				            MessageFormat.format(
				                    message,
				                    new String[] { labelName, root.getElementName()}),
				                    false);
				} else {
				    IJavaElement annotatedElement = element.getJavaProject();
				    String message = "The documentation location for ''{0}'' has not been configured. " +
				    "For elements from source specify the Javadoc location URL on the properties page of the parent project (''{1}'')";
//				    try {
//				        message = ActionMessages.getString("OpenExternalJavadocAction.source.no_location"); //$NON-NLS-1$
//				    } catch(Throwable t) {}
				    showMessage(
				            shell,
				            MessageFormat.format(
				                    message,
				                    new String[] {
				                            labelName,
				                            annotatedElement.getElementName()}),
				                            false);
				}
				return;
			}
			if ("file".equals(baseURL.getProtocol())) { //$NON-NLS-1$
				URL noRefURL = JavaUI.getJavadocLocation(element, false);
				if (!(new File(noRefURL.getFile())).isFile()) {
					String message = "The documentation does not contain an entry for ''{0}''.\n(File ''{1}'' does not exist.)";
//					try {
//					    message = ActionMessages.getString("OpenExternalJavadocAction.source.no_entry"); //$NON-NLS-1$
//					} catch(Throwable t) {}
					showMessage(
						shell,
						MessageFormat.format(
							message,
							new String[] {
								labelName,
								noRefURL.toExternalForm()}),
						false);
					return;
				}
			}

			URL url = JavaUI.getJavadocLocation(element, true);
			if (url != null) {
				Class obu = OpenBrowserUtil.class;
				Method open;
				try {
					open =
						obu.getMethod(
							"open",
							new Class[] {
								URL.class,
								Shell.class,
								String.class });
					open.invoke(null, new Object[] { url, shell, getTitle()});
				} catch (Exception e1) {
//					System.err.println("Unable to find Eclipse 2 javadoc opener method - trying Eclipse 3 method");
					//e1.printStackTrace();
					//Eclipse 3 change
					try {
						open =
							obu.getMethod(
								"open",
								new Class[] {
									URL.class,
									Display.class,
									String.class });
						open.invoke(
							null,
							new Object[] {
								url,
								shell.getDisplay(),
								getTitle()});
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				//OpenBrowserUtil.open(url, shell, getTitle());
			}
		} catch (CoreException e) {
			JavaPlugin.log(e);
			showMessage(shell, "Opening Javadoc failed. See log for details", true); //$NON-NLS-1$
		}
	}

	private static void showMessage(
		final Shell shell,
		final String message,
		final boolean isError) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (isError) {
					MessageDialog.openError(shell, getTitle(), message); //$NON-NLS-1$
				} else {
					MessageDialog.openInformation(shell, getTitle(), message); //$NON-NLS-1$
				}
			}
		});
	}
	private static String getTitle() {
		return "Javadoc";
	}

}
