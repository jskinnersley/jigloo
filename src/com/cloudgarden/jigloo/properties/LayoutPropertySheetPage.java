/*
 * Created on Dec 30, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.outline.TreeObject;
import com.cloudgarden.jigloo.resource.ColorManager;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LayoutPropertySheetPage extends CGPropertySheetPage {

	static Color lightGray;

	public LayoutPropertySheetPage(
		Composite parent,
		String title,
		IWorkbenchPartSite site) {
		
		super(title, parent, site, false, false);

		lightGray = ColorManager.getColor(230, 230, 250);
		Control ctrl = (Control) getControl();
		ctrl.setBackground(lightGray);

	}
	
	protected void initPropSrc() {
		setPropertySourceProvider(new IPropertySourceProvider() {
			public IPropertySource getPropertySource(Object o) {
				//System.out.println("GET PROP SRC "+o.getClass()+", "+o);
				if (o instanceof TreeObject) {
					TreeObject tob = (TreeObject) o;
					FormComponent fc = tob.getFormComponent();
					if (fc != null && fc.getLayoutWrapper() != null)
						return fc.getLayoutPropertySource();
				} else if (o instanceof FormComponent) {
					FormComponent fc = (FormComponent) o;
					if (fc != null && fc.getLayoutWrapper() != null && fc.getEditor() != null)
						return fc.getLayoutPropertySource();
				} else if (o instanceof IPropertySource) {
					return (IPropertySource) o;
				}
				return null;
			}
		});
	}

}
