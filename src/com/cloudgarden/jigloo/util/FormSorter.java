/*
 * Created on Sep 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import java.text.Collator;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.ui.JavaElementSorter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class FormSorter extends ViewerSorter {

	private JavaElementSorter jes = null;

	public FormSorter() {
		jes = new JavaElementSorter();
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		try {
			String name1 = null;
			String name2 = null;
			//System.err.println(
			//"COMPARE " + e1.getClass() + ", " + e2.getClass());
			if (e1 instanceof CompilationUnit)
				name1 = ((CompilationUnit) e1).getElementName();
			if (e1 instanceof File)
				name1 = ((File) e1).getName();
			if (e2 instanceof CompilationUnit)
				name2 = ((CompilationUnit) e2).getElementName();
			if (e2 instanceof File)
				name2 = ((File) e2).getName();

			if (name1 != null && name2 != null) {
				int p1 = name1.lastIndexOf(".");
				int p2 = name2.lastIndexOf(".");
				if (p1 >= 0 && p2 >= 0) {
					String n1 = name1.substring(0, p1);
					String n2 = name2.substring(0, p2);
					String ext1 = name1.substring(p1 + 1);
					String ext2 = name2.substring(p2 + 1);
					boolean e1fj = ext1.equals("java") || ext1.equals("form");
					boolean e2fj = ext2.equals("java") || ext2.equals("form");

					if (ext1.equals("java"))
						n1 += "A";
					else if (ext1.equals("form"))
						n1 += "B";
					else
						n1 = "z" + n1;

					if (ext2.equals("java"))
						n2 += "A";
					else if (ext2.equals("form"))
						n2 += "B";
					else
						n2 = "z" + n2;

					return n1.compareToIgnoreCase(n2);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return super.compare(viewer, e1, e2);
		return jes.compare(viewer, e1, e2);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerSorter#getCollator()
	 */
	public Collator getCollator() {
		if (collator == null) {
			collator = Collator.getInstance();
		}
		return collator;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerSorter#isSorterProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isSorterProperty(Object element, String property) {
		return true;
	}

}