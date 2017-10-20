package com.cloudgarden.jigloo.classloader;

import java.beans.Introspector;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.util.DefaultValueManager;

public class ClassLoaderCache {

	private static HashMap classLoaders = new HashMap();

	public static void deleteClassLoader(IProject proj) {
		ClassLoader cl = (ClassLoader) classLoaders.get(proj);
		if(cl instanceof FormClassLoader) {
			((FormClassLoader)cl).dispose();
			classLoaders.remove(proj);
		} else {
			classLoaders.remove(proj);
		}
		clearCaches();
	}

	public static void clearCaches() {
        Introspector.flushCaches();
        BeanHandler.clear();
		DefaultValueManager.clearCache();
     	Cacher.clearMethods();
    	PropertySourceFactory.clearCaches();
	}
	
	public static FormClassLoader getClassLoader(IProject proj, ClassLoader parentCL) {
	    FormClassLoader cl = (FormClassLoader) classLoaders.get(proj);
	    if (cl == null)
	        cl = createClassLoader(proj, parentCL);
	    classLoaders.put(proj, cl);
	    return cl;
	}

	public static Class loadClass(IProject proj, String className,
	    		ClassLoader parentCL, boolean newLoader) {
	    	try {
	    		ClassLoader cl;
	    		if (newLoader)
	    			cl = createClassLoader(proj, parentCL);
	    		else
	    			cl = getClassLoader(proj, parentCL);
	    		classLoaders.put(proj, cl);
	    		return cl.loadClass(className);
	    	} catch(Throwable t) {
	//    		System.err.println("Error loading class "+className+" : "+t);
	    		return null;
	    	}
	}

	public static FormClassLoader createClassLoader(IProject proj,  ClassLoader parentCL) {
	    System.gc();
	    Runtime rt = Runtime.getRuntime();
	    System.out.println("Creating new class loader for "+proj.getName()+" - memory usage:" +
	       		" USED="+ (rt.totalMemory() - rt.freeMemory())/1024L+
	       		", FREE="+ rt.freeMemory()/1024L+
	    		", TOT="+rt.totalMemory()/1024L);
	    Vector urlVec = new Vector();
	    URL[] cpUrls = null;
	    loadURLSForProject(proj, urlVec);
	    try {
	        IProject[] refProjs = proj.getReferencedProjects();
	        for (int i = 0; i < refProjs.length; i++) {
	            IProject refProj = refProjs[i];
	            //System.err.println("GOT REFERENCED PROJ " + refProj);
	            loadURLSForProject(refProj, urlVec);
	        }
	    } catch (Throwable e) {
	        jiglooPlugin.handleError(e);
	    }
	    BeanHandler.addBeanInfoURLS(urlVec);
	    
//	    try {
//	    	System.out.println("ClassLoaderCache: Adding extra folder to class path "+"file:/C:/workspace3.2/jigloo-extra/bin/");
//			URL extraURL = new URL("file:/C:/workspace3.2/jigloo-extra/bin/");
//			urlVec.add(0, extraURL);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
	    
	    cpUrls = new URL[urlVec.size()];
	    urlVec.copyInto(cpUrls);
	    return new FormClassLoader(cpUrls, parentCL);
//	    return FormClassLoader.createClassLoader(cpUrls, parentCL);
	}

	public static boolean isProjectReferenced(IJavaProject jproj, IProject referenced) {
		if (!jproj.isOpen()) {
			return false;
		}
		IClasspathEntry[] cps = null;
		try {
			cps = jproj.getResolvedClasspath(true);
		} catch (JavaModelException e1) {
			return false;
		}
		for (int i = 0; i < cps.length; i++) {
			try {
				cps[i] = JavaCore.getResolvedClasspathEntry(cps[i]);
				if (cps[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					if(referenced.getFullPath().equals(cps[i].getPath()))
						return true;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static void loadURLSForProject(IProject proj, Vector urlVec) {
	    	try {
	    		if (DEBUG_CLASS_LOADER)
	    			jiglooPlugin.writeToLog("GET URLS FOR PROJECT " + proj.getName());
	    		IJavaProject jproj = JavaCore.create(proj);
	    		int attempt = 0;
	    		while(!proj.isOpen() && attempt++ < 10) {
	    			//if the project is being rebuilt - eg, when 
	    			try {
	    				Thread.currentThread().sleep(400);
	    			} catch(Throwable t) {}
	    		}
	    		if (!proj.isOpen()) {
	//    			if (DEBUG_CLASS_LOADER)
	    			System.err.println("PROJECT IS CLOSED " + proj.getName());
	    			return;
	    		}
	    		IClasspathEntry[] cps = null;
	    		try {
	    			cps = jproj.getResolvedClasspath(true);
	    		} catch (JavaModelException e1) {
	    			e1.printStackTrace();
	    			return;
	    		}
	    		if (DEBUG_CLASS_LOADER) {
	    			if(cps != null)
	    				System.out.println("FOUND "+cps.length+" CP ENTRIES");
	    			else
	    				System.out.println("FOUND NO CP ENTRIES");
	    		}
	    		IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
	    		for (int i = 0; i < cps.length; i++) {
	    			try {
	    				IPath projPath = null;
	
	    				if (jproj.getCorrespondingResource() != null)
	    					projPath = jproj.getCorrespondingResource().getLocation();
	
	    				cps[i] = JavaCore.getResolvedClasspathEntry(cps[i]);
	
	    				if (DEBUG_CLASS_LOADER)
	    					jiglooPlugin.writeToLog("GOT RESOLVED CLASSPATH ENTRY " + cps[i]);
	    				if (cps[i] == null) {
	    					if (DEBUG_CLASS_LOADER)
	    						jiglooPlugin.writeToLog("GOT UN-RESOLVED CLASSPATH ENTRY " + cps[i]);
	    					continue;
	    				}
	
	    				IPath p;
	    				java.io.File f = null;
	
	    				if (cps[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
	    				}
	
	    				if (cps[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
	    					p = cps[i].getOutputLocation();
	    					if (p != null) {
	
	    						int match = p.matchingFirstSegments(jproj.getPath());
	    						if (match == p.segmentCount()) {
	    							p = proj.getLocation();
	    						} else {
	    							p = p.removeFirstSegments(match);
	    							p = proj.getFolder(p).getLocation();
	    						}
	
	    					}
	    					if (DEBUG_CLASS_LOADER)
	    						jiglooPlugin.writeToLog("GOT SOURCE CP ENTRY " + cps[i] + ", OPLOC = " + p);
	    					if (p == null) {
	    						p = jproj.getOutputLocation();
	    						int match = p.matchingFirstSegments(jproj.getPath());
	    						if (match == p.segmentCount()) {
	    							p = proj.getLocation();
	    						} else {
	    							p = p.removeFirstSegments(match);
	    							p = proj.getFolder(p).getLocation();
	    						}
	    					}
	    					if (p != null) {
	    						f = p.toFile();
	    						if (f == null || !f.exists()) {
	    							f = wsr.getLocation().append(p).toFile();
	    							if (DEBUG_CLASS_LOADER)
	    								jiglooPlugin.writeToLog("TRYING FILE (4) " + f);
	    						}
	    						if (f != null && f.exists()) {
	    							urlVec.add(f.toURL());
	    							if (DEBUG_CLASS_LOADER)
	    								jiglooPlugin.writeToLog("ADDING URL " + f.toURL());
	
	    							//if we found an output location, don't add the source location
	    							//to the urls since the source location might contain old class files
	    							continue;
	    						}
	    					}
	    				}
	
	    				p = cps[i].getPath();
	    				if (p != null) {
	    					IProject[] projs = wsr.getProjects();
	    					boolean found = false;
	    					for (int j = 0; j < projs.length; j++) {
	    						IProject pr = projs[j];
	    						IResource res = pr.getWorkspace().getRoot().findMember(p);
	    						if(res != null && res.exists()) {
	    							urlVec.add(res.getLocation().toFile().toURL());
	    							found = true;
	    							break;
	    						}
	    					}
	    					
	    					if(!found) {

	    						f = projPath.append(p.removeFirstSegments(1)).toFile();
	    						//                    f = projPath.append(p.lastSegment()).toFile();
	    						if (DEBUG_CLASS_LOADER)
	    							jiglooPlugin.writeToLog("TRYING FILE (1) " + projPath + " + " + p.removeFirstSegments(1));

	    						if (f == null || !f.exists())
	    							f = p.makeAbsolute().toFile();

	    						if (DEBUG_CLASS_LOADER)
	    							jiglooPlugin.writeToLog("TRYING FILE (1.1) " + p.makeAbsolute());

	    						if (f == null || !f.exists())
	    							f = proj.getFolder(p).getLocation().toFile();

	    						if (DEBUG_CLASS_LOADER)
	    							jiglooPlugin.writeToLog("TRYING FILE (1.5) "+ proj.getFolder(p) + ","
	    									+ proj.getFolder(p).getLocation());

	    						if (f == null || !f.exists()) {
	    							f = wsr.getLocation().append(p).toFile();
	    							if (DEBUG_CLASS_LOADER)
	    								jiglooPlugin.writeToLog("TRYING FILE (2) " + f);
	    						}
	    						if (f != null && f.exists()) {
	    							urlVec.add(f.toURL());
	    							if (DEBUG_CLASS_LOADER)
	    								jiglooPlugin.writeToLog("ADDING URL " + f.toURL());
	    						} else {
	    							System.out.println("Unable to resolve project build path entry "+cps[i]);
	    						}
	    					}
	    				} else {
	    					if (DEBUG_CLASS_LOADER)
	    						jiglooPlugin.writeToLog("P == NULL");
	    				}
	
	    			} catch (Throwable e2) {
	    				e2.printStackTrace();
	    			} 
	    			//            if(urlVec.size() > 0)
	    			//            	System.err.println("ADDED URL " + urlVec.lastElement());
	    		}
	    	} catch(Throwable t) {
	    		t.printStackTrace();
	    	}
	    }

	public static final boolean DEBUG_CLASS_LOADER = false;

}
