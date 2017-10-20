/*
 * Created on Apr 10, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.interfaces;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertySource;


/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IFormPropertySource extends IPropertySource {

	public Vector getPropertyNames();
	public String getJavaCodeForPropertySetter(String pName, IJavaCodeManager jcm);
	public String getJavaConstructor(IJavaCodeManager jcm);
	public boolean hasProperty(String id);
	public boolean isSyntheticProperty(String id);
	public boolean needsUpdateInCode(String propName);
	public String getName();
	public String getNameInCode();
	public void setName(String name);
	public String getBlockName();
	public void setBlockName(String name);
	public void setObject(Object obj);
	public void dispose();

}
