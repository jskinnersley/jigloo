package com.cloudgarden.jigloo.images;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;


/**
 * An image descriptor that loads its image information from a file.
 */
class TextImageDescriptor extends PluginImageDescriptor {

	private Image img;
	
	TextImageDescriptor(Class clazz, String filename) {
		super(clazz, filename);
	}

	public boolean equals(Object o) {
		if (!(o instanceof TextImageDescriptor)) {
			return false;
		}
		TextImageDescriptor other = (TextImageDescriptor) o;
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
		createImage();
		return img.getImageData();
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
		if(img == null)
			img = ImageManager.getTextImage(name);
		return img;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return name;
	}

}