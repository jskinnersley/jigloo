/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.ISWTDisposableWrapper;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImageWrapper implements ISWTDisposableWrapper {

	protected Image image;
	protected Object awtImage;
	protected String name;
	protected URL url;
	protected FormComponent comp;
	protected FormEditor editor;

	private ImageWrapper() {
	}
	
	public ImageWrapper(Node node, FormComponent comp) {
		Node cn = NodeUtils.getChildNodeByName("Image", node);
		name = NodeUtils.getAttribute("name", cn);
		name = JiglooUtils.getOSFileName(name);
		//url = getClass().getClassLoader().getResource(name);
		setFormComponent(comp);
	}

	public ImageWrapper(String fileName, FormEditor editor) {
		this(fileName, (FormComponent)null);
		this.editor = editor;
	}

	public ImageWrapper(String fileName, FormComponent comp) {
		this.name = fileName;
		setFormComponent(comp);

		File file = new File(name);
		String resName = file.getName();
		URL testUrl = null;
		while (testUrl == null && file.getParent() != null) {
			resName = file.getParentFile().getName() + "/" + resName;
			testUrl = getClass().getClassLoader().getResource("/" + resName);
			//System.out.println("SEARCHING for " + resName + ", url=" + testUrl);
			file = file.getParentFile();
		}
		if (testUrl == null) {
			url = getClass().getClassLoader().getResource(name);
		} else {
			//System.out.println("FOUND RESOURCE " + resName);
			name = resName;
			url = testUrl;
		}
	}

	public ImageWrapper(Image image, FormComponent comp) {
		this.image = image;
		setFormComponent(comp);
		if (image != null)
			name = image.toString();
	}

	public ImageWrapper(java.awt.Image image, FormComponent comp) {
		this.awtImage = image;
		setFormComponent(comp);
//		this.comp = comp;
		if (awtImage != null)
			name = awtImage.toString();
	}

	public void setFormComponent(FormComponent comp) {
		if (comp == null)
			return;
		this.comp = comp;
		editor = comp.getEditor();
	}

	public IconWrapper toIconWrapper() {
		return new IconWrapper(name, comp);
	}

	public void setEditor(FormEditor editor) {
		this.editor = editor;
	}

	public ImageWrapper getCopy(FormComponent copy) {
		ImageWrapper iw = new ImageWrapper(name, copy);
		iw.setEditor(editor);
		return iw;
	}

	public void setAWTImage(java.awt.Image image) {
		this.awtImage = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void dispose() {
	    editor = null;
	    comp = null;
		if (image != null && !image.isDisposed())
			image.dispose();
	}

	public Object getImage(Object ctrl) {
		if ("No Image".equals(name))
			return null;
		try {
			if(editor == null)
				editor = comp.getEditor();
			String baseDir = JiglooUtils.getSourceBaseForEditor(editor);
			String fileName = JiglooUtils.getOSFileName(baseDir + File.separator + name);
			
			if (ctrl == null && jiglooPlugin.canUseSwing()) {
				if (awtImage == null && fileName != null) {
				    String edClass = editor.getFullClassName();
				    int pos = edClass.lastIndexOf(".");
				    if(pos >= 0)
				        edClass = edClass.substring(0, pos+1);
				    edClass = JiglooUtils.replace(edClass, ".", "/");
				    URL url = editor.getClassLoader().getResource(edClass+name);
				    if(url == null)
				        url = editor.getClassLoader().getResource(edClass+"resources/"+name);
				    if(url == null)
				        url = editor.getClassLoader().getResource(JiglooUtils.replace(
				                "/"+AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+"/", ".", "/")+name);
				    if(url == null)
				        url = editor.getClassLoader().getResource(JiglooUtils.replace(
				                "/"+AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+"/resources/", ".", "/")+name);
				    if(url == null) {
				        System.err.println("Unable to locate image for "+name);
				        return null;
				    }
					awtImage = new ImageIcon(url).getImage();
				}
				return awtImage;
			}
			
			if (image != null && !image.isDisposed())
				return image;
			Display display = ((Widget) ctrl).getDisplay();
			File file = new File(fileName);
			//System.err.println("GOT IMAGE FILE " + file + ", " + baseDir + ", " + name);
			url = file.toURL();
			image = ImageDescriptor.createFromURL(url).createImage();
		
		} catch (Exception e) {
			jiglooPlugin.writeToLog("Error getting image: "+name+": "+e);
			e.printStackTrace();
			if(ctrl == null) {
				URL url = ImageManager.class.getResource("./form.gif");
				return new ImageIcon(url).getImage();
			} else {
				return ImageManager.getImage("form.gif");
			}
		}
		return image;
	}

	protected boolean useImgFileInCode() {
		return jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_IMG_FILES);
	}

	public String getSwingConstructor(IJavaCodeManager jcm) {
		jcm.addImport(ImageIcon.class.getName());
		if (!useImgFileInCode())
			return "new ImageIcon(getClass().getClassLoader().getResource(\"" + getNameAsURL() + "\")).getImage()";
		else
			return "new ImageIcon(\"" + getNameAsURL() + "\").getImage()";
		//return "new ImageIcon(\"." + getEscapedName() + "\").getImage()";
	}

	public String getSWTDeclaration(String fieldName, IJavaCodeManager jcm) {
		//jcm.addImport(Image.class.getName());
		String imgLoc = "\"" + getNameAsURL() + "\"";
		if (!useImgFileInCode())
			imgLoc = "getClass().getClassLoader().getResourceAsStream(" + imgLoc + ")";
		String code =
			"final org.eclipse.swt.graphics.Image "
				+ fieldName
				+ " = new org.eclipse.swt.graphics.Image(Display.getDefault(), "
				+ imgLoc
				+ ");";
		if (comp.isA(Button.class) || comp.isA(CLabel.class) || comp.isA(Label.class)) {
			code += "\n\t\t" + fieldName + ".setBackground(" + comp.getName() + ".getBackground());";
		}
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEscapedName() {
		if (name == null)
			return null;
		return JiglooUtils.replace(name, "\\", "\\\\");
	}

	public String getNameAsURL() {
		if (name == null)
			return null;
		String url = JiglooUtils.replace(name, "\\", "/");
		if (url.startsWith("/"))
			url = url.substring(1);
		return url;
	}

	public String toString() {
		if (name == null)
			return "No Icon";
		if(editor.isPartOfAppFrameworkApplication())
		    return getAFName();
		return JiglooUtils.getOSFileName(name);
	}
	public String getResourceCode() {
		return "SWTResourceManager.getImage(\"" + getNameAsURL() + "\")";
	}
	
    /**
     * Get appFramework name - returns resources/icons/unqualified_name
     */
    public String getAFName() {
        String afName = getNameAsURL();
        if(afName.startsWith("/"))
            afName = afName.substring(1);
        String edClass = editor.getFullClassName();
        edClass = JiglooUtils.replace(edClass, ".", "/");
        int pos = edClass.lastIndexOf("/");
        if(pos >= 0)
            edClass = edClass.substring(0, pos+1);
        afName = JiglooUtils.replace(afName, edClass, "");
        if(afName.startsWith("resources"))
            afName = afName.substring(10);
        return afName;
    }

}
