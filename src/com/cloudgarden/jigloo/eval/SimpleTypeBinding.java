/*
 * Created on Jun 17, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.util.HashMap;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SimpleTypeBinding implements ITypeBinding {

	private String name;
	private String qualName;
	private static HashMap map = new HashMap();
	private static boolean warningMade = false;

	public SimpleTypeBinding(String name, HashMap imports) {
//	    System.err.println("New SimpleTypeBinding "+name);
		this.name = name;
		int pos = name.lastIndexOf(".");
		if (pos < 0) {

			Class cls = null;

			if (map.containsKey(name))
				cls = (Class) map.get(name);

			if (cls == null)
				cls = findClass("org.eclipse.swt.widgets." + name);
			if (cls == null)
				cls = findClass("org.eclipse.swt.custom." + name);
			if (cls == null)
				cls = findClass("com.jgoodies.forms.factories." + name);
			
			if (cls == null
			        && imports != null && imports.containsKey("com.jgoodies.forms.layout." + name))
				cls = findClass("com.jgoodies.forms.layout." + name);
			if (cls == null)
				cls = findClass("org.eclipse.swt.layout." + name);
			
			if (cls == null)
				cls = findClass("org.eclipse.swt.events." + name);
			if (cls == null)
				cls = findClass("org.eclipse.swt.dnd." + name);
			if (cls == null)
				cls = findClass("org.eclipse.swt.graphics." + name);
			if (cls == null)
				cls = findClass("org.eclipse.swt." + name);
			if (cls == null)
				cls = findClass("com.philemonworks.typewise." + name);
			if (cls == null)
				cls = findClass("com.philemonworks.typewise.server." + name);
			if (cls == null)
				cls = findClass("com.philemonworks.typewise.widget." + name);
			if (cls == null)
				cls = findClass("com.cloudgarden.jigloo.groupLayout." + name);
			if (cls != null)
				qualName = cls.getName();
		} else {
			qualName = name;
			name = name.substring(pos + 1);
		}
	}

	private Class findClass(String name) {
		if (map.containsKey(name))
			return (Class) map.get(name);
//		System.err.println("FIND CLASS "+name);
		Class cls = null;
		try {
			cls = Class.forName(name);
		} catch (Throwable t) {}
		try {
			if (cls == null)
				cls = getClass().getClassLoader().loadClass(name);
			map.put(name, cls);
			//			if (!warningMade) {
			//				warningMade = true;
			//				MessageDialog.openWarning(
			//					Display.getDefault().getActiveShell(),
			//					"SWT Library file needed",
			//					"Warning! Guessing SWT class names. "
			//						+ "\n\nThis may result in incorrect behaviour "
			//						+ "of the editor, and should be corrected by including the swt.jar file"
			//						+ " in your projct's build path.");
			//			}
//			System.err.println("FOUND CLASS "+cls+" for "+name);
			return cls;
		} catch (Throwable t) {
//		    System.err.println("Error in SimpleTypeBinding.findClass "+name+", "+t);    
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isPrimitive()
	 */
	public boolean isPrimitive() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isNullType()
	 */
	public boolean isNullType() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isArray()
	 */
	public boolean isArray() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getElementType()
	 */
	public ITypeBinding getElementType() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDimensions()
	 */
	public int getDimensions() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isClass()
	 */
	public boolean isClass() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isInterface()
	 */
	public boolean isInterface() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getPackage()
	 */
	public IPackageBinding getPackage() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDeclaringClass()
	 */
	public ITypeBinding getDeclaringClass() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getSuperclass()
	 */
	public ITypeBinding getSuperclass() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getInterfaces()
	 */
	public ITypeBinding[] getInterfaces() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#getModifiers()
	 */
	public int getModifiers() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDeclaredModifiers()
	 */
	public int getDeclaredModifiers() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isTopLevel()
	 */
	public boolean isTopLevel() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isNested()
	 */
	public boolean isNested() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isMember()
	 */
	public boolean isMember() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isLocal()
	 */
	public boolean isLocal() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isAnonymous()
	 */
	public boolean isAnonymous() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDeclaredTypes()
	 */
	public ITypeBinding[] getDeclaredTypes() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDeclaredFields()
	 */
	public IVariableBinding[] getDeclaredFields() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getDeclaredMethods()
	 */
	public IMethodBinding[] getDeclaredMethods() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isFromSource()
	 */
	public boolean isFromSource() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getQualifiedName()
	 */
	public String getQualifiedName() {
		return qualName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#getKind()
	 */
	public int getKind() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#isDeprecated()
	 */
	public boolean isDeprecated() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#isSynthetic()
	 */
	public boolean isSynthetic() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.IBinding#getKey()
	 */
	public String getKey() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getBinaryName()
	 */
	public String getBinaryName() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isEnum()
	 */
	public boolean isEnum() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isAnnotation()
	 */
	public boolean isAnnotation() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getTypeParameters()
	 */
	public ITypeBinding[] getTypeParameters() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isTypeVariable()
	 */
	public boolean isTypeVariable() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getTypeBounds()
	 */
	public ITypeBinding[] getTypeBounds() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isParameterizedType()
	 */
	public boolean isParameterizedType() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getTypeArguments()
	 */
	public ITypeBinding[] getTypeArguments() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getErasure()
	 */
	public ITypeBinding getErasure() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isRawType()
	 */
	public boolean isRawType() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isWildcardType()
	 */
	public boolean isWildcardType() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#getBound()
	 */
	public ITypeBinding getBound() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ITypeBinding#isUpperbound()
	 */
	public boolean isUpperbound() {

		return false;
	}

	//======================================
	
	public boolean isAssignmentCompatible(ITypeBinding type) {

		return false;
	}

	public boolean isCastCompatible(ITypeBinding type) {

		return false;
	}

	public boolean isGenericType() {

		return false;
	}

	public ITypeBinding getTypeDeclaration() {

		return null;
	}

	public IMethodBinding getDeclaringMethod() {

		return null;
	}

	public boolean isSubTypeCompatible(ITypeBinding type) {

		return false;
	}

	public IJavaElement getJavaElement() {

		return null;
	}

	public boolean isEqualTo(IBinding binding) {

		return false;
	}

	public ITypeBinding getWildcard() {

		return null;
	}

	public boolean isCapture() {

		return false;
	}
	
	 // Eclipse 3.2 methods
	public ITypeBinding getComponentType() {
		return null;
	}

//	  Eclipse 3.2.1 methods
	public org.eclipse.jdt.core.dom.IAnnotationBinding[] getAnnotations() {
		return null;
	}

	public ITypeBinding createArrayType(int dimension) {
		return null;
	}

	public boolean isRecovered() {
		// TODO Auto-generated method stub
		return false;
	}
	
//3.5 methods
	public ITypeBinding getGenericTypeOfWildcardType() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IBinding getDeclaringMember() {
		// TODO Auto-generated method stub
		return null;
	}

	public IMethodBinding getFunctionalInterfaceMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	public IAnnotationBinding[] getTypeAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIntersectionType() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
