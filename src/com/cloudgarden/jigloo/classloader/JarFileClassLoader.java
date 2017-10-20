package com.cloudgarden.jigloo.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JarFileClassLoader extends URLClassLoader {

	private File file;
	
	public JarFileClassLoader(URL url, ClassLoader parent) {
		super(new URL[] {url}, parent);
		File file = new File(url.getFile());
		if(file.exists())
			this.file = file;
	}
	
	public synchronized Class loadClass(String name, boolean resolve)
	throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}

}
