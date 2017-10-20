/*
 * Created on Nov 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.search;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchResultCollector;
import org.eclipse.jdt.core.search.SearchEngine;

import com.cloudgarden.jigloo.jiglooPlugin;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchUtils {

    static class ProjectCollector implements IJavaSearchResultCollector {
        
        IProject classProject = null;
        
        public IProject getProject() {
            return classProject;
        }
        
        public void aboutToStart() {
        }

        public void accept(IResource resource, int start, int end,
                IJavaElement ee, int accuracy) throws CoreException {
            if (resource != null && resource.getProject() != null)
                classProject = resource.getProject();
            else if (ee != null && ee.getJavaProject() != null
                    && ee.getJavaProject().getProject() != null)
                classProject = ee.getJavaProject().getProject();
        }

        public void done() {
        }

        public IProgressMonitor getProgressMonitor() {
            return null;
        }
    }
    
    static class ClassCollector implements IJavaSearchResultCollector {
        
        private Collection classes;
        
        /**
         * @param typesFound
         */
        public ClassCollector(Collection typesFound) {
            classes = typesFound;
        }

        public void aboutToStart() {
        }

        public void accept(IResource resource, int start, int end,
                IJavaElement ee, int accuracy) throws CoreException {
            classes.add((IType)ee);
        }

        public void done() {
        }

        public IProgressMonitor getProgressMonitor() {
            return null;
        }
    }
    
    /**
     * @param className
     */
    public static IProject getProjectForClass(String className) {
        SearchEngine se = new SearchEngine();
        ProjectCollector col = new ProjectCollector();
        try {
            se.search(ResourcesPlugin.getWorkspace(), className,
                    IJavaSearchConstants.TYPE,
                    IJavaSearchConstants.DECLARATIONS, SearchEngine
                            .createWorkspaceScope(), col);
        } catch (JavaModelException e) {
            jiglooPlugin.handleError(e);
        }
        return col.getProject();
    }

    /**
     * @param className
     * @param proj
	 * @param style  one of
	 * <ul>
	 * 		<li><code>IJavaSearchConstants.CLASS</code> if searching for classes only</li>
	 * 		<li><code>IJavaSearchConstants.INTERFACE</code> if searching for interfaces only</li>
	 * 		<li><code>IJavaSearchConstants.TYPE</code> if searching for both classes and interfaces</li>
	 * </ul>
     * @param monitor
     * @param typesFound
     * @throws JavaModelException
     */
    public static void findImplementors(String className, IJavaProject proj, Vector typesFound)
    throws JavaModelException {
        IType type = proj.findType(className);
        ITypeHierarchy hier = type.newTypeHierarchy(proj, null);
        IType[] subs = hier.getAllSubtypes(type);
        for(int i=0; i < subs.length; i++)
            typesFound.add(subs[i]);
        Collections.sort(typesFound, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((IType)o1).getElementName().compareTo(((IType)o2).getElementName());
            }
        });
        
//        IJavaSearchScope scope = null;
//        if (proj != null)
//            scope = SearchEngine.createHierarchyScope(proj.findType(className));
//        else
//            scope = SearchEngine.createWorkspaceScope();
//        char[] pack, cls;
//        int pos = className.lastIndexOf(".");
//        pack = className.substring(0, pos).toCharArray();
//        cls = className.substring(pos + 1).toCharArray();
//        ISearchPattern pat = SearchEngine.createSearchPattern("*", IJavaSearchConstants.TYPE,
//                IJavaSearchConstants.DECLARATIONS, true);
//        new SearchEngine().search(ResourcesPlugin.getWorkspace(), pat, scope,
//                new ClassCollector(typesFound));
    }
    
//
//private TypeInfo findType(String className) throws JavaModelException {
//ArrayList searchResult = new ArrayList(1);
//IJavaElement[] elems = new IJavaElement[1];
//elems[0] = getJavaProject(javaFile.getProject());
//char[] pack, cls;
//int pos = className.lastIndexOf(".");
//pack = className.substring(0, pos).toCharArray();
//cls = className.substring(pos + 1).toCharArray();
//
//new SearchEngine().searchAllTypeNames(ResourcesPlugin.getWorkspace(),
//      pack, cls, IJavaSearchConstants.PATTERN_MATCH,
//      IJavaSearchConstants.CASE_INSENSITIVE,
//      IJavaSearchConstants.TYPE, SearchEngine
//              .createJavaSearchScope(elems), new TypeInfoRequestor(
//              searchResult),
//      IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, null);
//
//return (TypeInfo) searchResult.get(0);
//}

}
