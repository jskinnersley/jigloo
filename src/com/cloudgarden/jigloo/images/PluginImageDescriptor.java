package com.cloudgarden.jigloo.images;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.util.FileUtils;


/**
 * An image descriptor that loads its image information from a file.
 */
class PluginImageDescriptor extends ImageDescriptor {

	/**
	 * The class whose resource directory contain the file, or <code>null</code>
	 * if none.
	 */
	protected Class location;

	/**
	 * The name of the file.
	 */
	protected String name;

	protected ImageData data;
	
	/**
	 * Creates a new file image descriptor. The file has the given file name and
	 * is located in the given class's resource directory. If the given class is
	 * <code>null</code>, the file name must be absolute.
	 * <p>
	 * Note that the file is not accessed until its <code>getImageDate</code>
	 * method is called.
	 * </p>
	 * 
	 * @param clazz
	 *            class for resource directory, or <code>null</code>
	 * @param filename
	 *            the name of the file
	 */
	PluginImageDescriptor(Class clazz, String filename) {
		this.location = clazz;
		this.name = filename;
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof PluginImageDescriptor)) {
			return false;
		}
		PluginImageDescriptor other = (PluginImageDescriptor) o;
		if (location != null) {
			if (!location.equals(other.location)) {
				return false;
			}
		} else {
			if (other.location != null) {
				return false;
			}
		}
		return name.equals(other.name);
	}

	/**
	 * @see org.eclipse.jface.resource.ImageDescriptor#getImageData() The
	 *      FileImageDescriptor implementation of this method is not used by
	 *      {@link ImageDescriptor#createImage(boolean, Device)} as of version
	 *      3.4 so that the SWT OS optimised loading can be used.
	 */
	public ImageData getImageData() {
		if(data != null)
			return data;
		InputStream in = getStream();
		if (in != null) {
			try {
				data = new ImageData(in);
			} catch (SWTException e) {
				if (e.code != SWT.ERROR_INVALID_IMAGE) {
					throw e;
					// fall through otherwise
				}
			} finally {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return data;
	}

	/**
	 * Returns a stream on the image contents. Returns null if a stream could
	 * not be opened.
	 * 
	 * @return the buffered stream on the file or <code>null</code> if the
	 *         file cannot be found
	 */
	private InputStream getStream() {
		InputStream stream = null;
		
		if (location != null) {
//			stream = location.getResourceAsStream(name);
			if(stream == null)
				stream = location.getResourceAsStream("/com/cloudgarden/jigloo/images/beanIcons/"+name);
			if(stream == null)
				stream = location.getResourceAsStream("/com/cloudgarden/jigloo/images/icons/"+name);
			if(stream != null)
				stream = new BufferedInputStream(stream);
		}
		
		if(stream == null) {
			String dir = jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_ICON_FOLDER);
			if(!"".equals(dir)) {
				try {
					stream = new BufferedInputStream(new FileInputStream(dir+File.separator+name));
				} catch (FileNotFoundException e) {
				}
			}
		}
		
		if(stream == null) {
			stream = FileUtils.getPluginResourceAsStream("/beanIcons/"+name);
		}
		
		if (stream == null) {
//			System.out.println("UNABLE TO FIND IMAGE "+name);
			return null;
		}
		return stream;

	}

	public void writeToFile(String fileName) {
		try {
			FileOutputStream os = new FileOutputStream(fileName);
			InputStream is = getStream();
			byte[] data = new byte[is.available()];
			is.read(data);
			os.write(data);
			is.close();
			os.close();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	public int hashCode() {
		int code = name.hashCode();
		if (location != null) {
			code += location.hashCode();
		}
		return code;
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	/**
	 * The <code>FileImageDescriptor</code> implementation of this
	 * <code>Object</code> method returns a string representation of this
	 * object which is suitable only for debugging.
	 */
	public String toString() {
		return "FileImageDescriptor(location=" + location + ", name=" + name + ")";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}
	
	public Image createImage(boolean returnMissingImageOnError, Device device) {
		InputStream in = getStream();
		if (in == null) {
			return createDefaultImage(returnMissingImageOnError, device);
		}
		try {			
			return new Image(device, in);
		} catch (SWTException exception) {
			//if we fail try the default way using a stream
		}
		return super.createImage(returnMissingImageOnError, device);
	}

	/**
	 * Return default image if returnMissingImageOnError is true.
	 * 
	 * @param device
	 * @return Image or <code>null</code>
	 */
	private Image createDefaultImage(boolean returnMissingImageOnError,
			Device device) {
		try {
			if (returnMissingImageOnError)
				return new Image(device, DEFAULT_IMAGE_DATA);
		} catch (SWTException nextException) {
			return null;
		}
		return null;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return name;
	}

}