/*
 * Created on May 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.appFramework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.KeyStrokeWrapper;
import com.cloudgarden.jigloo.wrappers.MnemonicWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BundleManager {

	private FormEditor editor;
	private Properties2 propMap;
	private Properties2 oldProps;
	private Properties appProps;
	private String editorPath;
	private File appPropsDir;
	private IFile propertyFile;
	private Boolean propFileExists = null;
	private TextEditor propertyFileEditor;
	private boolean propertyChanged = false;
	private Vector changes;
    private IDocumentListener docListener;
    
	public BundleManager(FormEditor editor) {
		this.editor = editor;
		propMap = new Properties2();
		editorPath = editor.getPackageName();
		editorPath = JiglooUtils.replace(editorPath, ".", "/");
		editorPath = "/"+editorPath;
		refreshPropFilePath();
	}
	
	public void refreshPropFilePath() {
		propertyFile = FileUtils.getResourceFile(editor.getClassName()+".properties", editor);
//		setPropertyFile(propertyFile);
	}
	
	public void dispose() {
		if(docListener != null)
			getDocument().removeDocumentListener(docListener);
		editor = null;
		docListener = null;
		propertyFileEditor = null;
	}
	
	public boolean propertyChanged() {
	    return propertyChanged;
	}
	
	public void addInjectComponentsCall(JavaCodeParser jcp) {
		if(jcp.getEditor().isSingleFrameApplication())
			return;
		MethodDeclaration initGUI = jcp.getInitGUIMethod();
		if(jcp.getCodeForNode(initGUI).indexOf(".injectComponents(") > 0)
			return;
		FormComponent fc = jcp.getRootComponent();
		String par = "this";
		if(fc.isJFrame() || fc.isJDialog() || fc.isJWindow())
			par = "getContentPane()";
		jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".Application");
		String code = "\t\tApplication.getInstance().getContext().getResourceMap(getClass()).injectComponents("+par+");";
		jcp.insertAtEndOfInitGUI(code);
	}
	
	private boolean isChanging = false;
	
	public boolean isChanging() {
		return isChanging;
	}
	
	public void updateProperties(boolean saveChanges) {
	    
		createPropertyFileIfNeeded();
		
	    if(propFileExists == null || !propFileExists.booleanValue())
	        return;
	    if(!propertyChanged)
	        return;
	    if(saveChanges)
	        changes = PropertyChange.getChanges(oldProps, propMap);

	    oldProps = (Properties2) propMap.clone();
	    propertyChanged = false;
	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        propMap.save(baos, "Properties updated by Jigloo");
		    
	        isChanging = true;
	        getDocument().set(baos.toString());
	        isChanging = false;
            
	    } catch (Throwable e) {
	        e.printStackTrace();
	    }
	}

	public void clearChanges() {
	    changes = null;
	}
	
	public Vector getChanges() {
	    return changes;
	}
	
	public void reload() {
		
		//force app props to be reloaded
		appProps = null;
		
	    if(propFileExists != null && !propFileExists.booleanValue())
	        return;
		
		propFileExists = new Boolean(propertyFile != null && propertyFile.exists());
	    
	    propMap.clear();
		String fileName = editor.getFullClassName();
		InputStream in = getPropertiesAsStream();
		try {
			if(in != null)
				propMap.load(in);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	    oldProps = (Properties2) propMap.clone();
	}
	
	public String getApplicationProperty(String propName) {
		if(appProps == null) {
			appProps = new Properties();
			try {
				String path = 
					JiglooUtils.replace(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME, ".", "/")
					+"/resources/Application.properties";
				InputStream in = editor.getClassLoader().getResourceAsStream(path);
				if(in != null)
					appProps.load(in);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return (String) appProps.get(propName);
	}
	
    private InputStream getPropertiesAsStream() {
		try {
		    if(propertyFileEditor != null) {
		        StringBufferInputStream in = new StringBufferInputStream(getDocument().get());
		        return in;
			} else {
				System.err.println("Unable to find resource bundle for "+editor.getJavaFile());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private String getQualifiedProperty(String propName) {
    	String val = propMap.getProperty(propName);
    	if(val == null)
    		val = getApplicationProperty(propName);
    	val = replaceTags(val);
    	return val;
    }
    
    public boolean canSetProperty(FormComponent fc, String pName) {
        if(pName.equals("name") || fc.isSWT() || fc.isInherited())
            return false;
        Class pType = fc.getPropType(pName);
        if(String.class.equals(pType)
        		|| Icon.class.equals(pType)  
        		|| Image.class.equals(pType) 
                || Color.class.equals(pType) 
                || Boolean.class.equals(pType) 
                || Number.class.isAssignableFrom(pType)
                || Font.class.equals(pType))
            return true;
        return false;
    }
    
    public void setProperty(String fullPropName, String val) {
        propMap.setProperty(fullPropName, val);
        propertyChanged = true;
    }
        
    public void removeProperty(String fullPropName) {
        propMap.remove(fullPropName);
        propertyChanged = true;
    }
        
    public boolean setProperty(FormComponent fc, String propName, Object val) {
        if(!editor.isPartOfAppFrameworkApplication())
            return false;
        if(editor.isProcessing())
            return false;
        if(fc.isInherited())
            return false;
        if(propName.equals("name"))
            return false;
        if(val instanceof FormComponent)
            val = ((FormComponent)val).getObject(true);
        
        Class pType = fc.getPropType(propName);
        
        if(val instanceof ImageWrapper) {
            val = ((ImageWrapper)val).getAFName();
        } else if(val instanceof String) {
            if(!pType.equals(String.class) && val.equals("null"))
                val = null;
        } else if(val instanceof Boolean 
        		|| val instanceof Number) {
            val = val.toString();
        } else if(val instanceof ColorWrapper) {
            val = ((ColorWrapper)val).getAFValue();
        } else if(val instanceof FontWrapper) {
            val = ((FontWrapper)val).getAFValue();
        } else if(val instanceof KeyStroke) {
            val = ((KeyStroke)val).toString();
        } else if(val instanceof KeyStrokeWrapper) {
            val = ((KeyStrokeWrapper)val).getKeyStroke().toString();
        } else if(val instanceof MnemonicWrapper) {
            val = ((MnemonicWrapper)val).getValue(null);
        } else {
            return false;
        }
        if(val == null || "".equals(val)) {
            removeProperty(fc.getIdentifier()+"."+propName);
        } else {
            setProperty(fc.getIdentifier()+"."+propName, val.toString());
            if(!fc.isPropertySet("name"))
                fc.setPropertyValue("name", fc.getIdentifier());
        }
        return true;
    }
    
    private String replaceTags(String orig) {
        if(orig == null)
            return null;
        String newStr = "";
        int pos = orig.indexOf("${");
        if(pos < 0)
            return orig;
        while(pos >= 0) {
            int pos2 = orig.indexOf("}", pos);
            newStr += getQualifiedProperty(orig.substring(pos+2, pos2));
            pos = pos2+1;
            int newPos = orig.indexOf("${", pos);
            if(newPos > 0) {
                newStr += orig.substring(pos, newPos);
                pos = newPos;
            } else
                break;
        }
        return newStr + orig.substring(pos);
    }
    
	public Object getProperty(String elemName, String propName, Class cls, Object defVal, FormComponent comp) {
		String val;
		if(elemName != null)
			val = getQualifiedProperty(elemName+"."+propName);
		else
			val = getQualifiedProperty(propName);
		if(val == null)
			return defVal;
		
		Object conVal = val;
		if(cls.equals(Icon.class)) {
//		    IFile file = FileUtils.getResourceFile(val, editor);
//		    val = FileUtils.getRelativePath(file, JiglooUtils.getSourceContainer(editor));
		    conVal = new IconWrapper(val, editor);
		} else if(cls.equals(Image.class)) {
//		    IFile file = FileUtils.getResourceFile(val, editor);
//		    val = FileUtils.getRelativePath(file, JiglooUtils.getSourceContainer(editor));
		    conVal = new ImageWrapper(val, editor);
		} else if(cls.equals(Color.class)) {
			conVal = new ColorWrapper(val, comp);
		} else if(cls.equals(Font.class)) {
			conVal = new FontWrapper(val, comp);
		} else {
			ResourceConverter rc = ResourceConverter.forType(cls);
			if(rc != null)
				try {
					conVal = rc.parseString(val);
				} catch (Throwable e) {
					e.printStackTrace();
				}
		}
		return conVal != null ? conVal : defVal;
	}

	public Object getProperty(FormComponent fc, String propName, Object defVal) {
	    return getProperty(fc.getIdentifier(), propName, fc.getPropType(propName), defVal, fc);
	}
	
	/**
	 * Saves any set text and icon properties of the given fc in the properties file (creating it if needed
	 * in the FormEditor.setDirtyAndUpdate method). 
	 * and removes the property setter code from the class file.
	 * @param fc
	 * @param propName
	 */
	public void extractProperties(FormComponent fc) {
	    Vector propNames = fc.getPropertyNames();
	    for(int i=0; i<propNames.size(); i++) {
	        String pName = (String)propNames.elementAt(i);
	        if(!fc.isPropertySet(pName))
	            continue;
	        Class pType = fc.getPropType(pName);
	        Object val = fc.getPropertyValue(pName);
	        if(val instanceof FormComponent)
	            val = ((FormComponent)val).getObject(true);
	        
	        if(String.class.equals(pType) && val instanceof String) {
	            setProperty(pName, (String)val);
	        } else	if(Boolean.class.equals(pType) && val instanceof Boolean) {
	            setProperty(pName, val.toString());
	        } else	if(Number.class.isAssignableFrom(pType) && val instanceof Number) {
	            setProperty(pName, val.toString());
	        } else if(Icon.class.equals(pType) && val instanceof IconWrapper) {
	            IconWrapper iw = (IconWrapper)val;
	            String afName = iw.getAFName();
	            setProperty(pName, afName);
	            iw.setName(afName);
	        }
	    }
	}
	
	/**
	 * looks for Application.lookAndFeel - if null returns the defaultLAFClassName
	 */
	public String getApplicationLookAndFeel() {
		String laf = getQualifiedProperty("Application.lookAndFeel");
		if(laf == null || "default".equals(laf))
			laf = jiglooPlugin.getDefaultLAFClassName();
		if("system".equals(laf))
			laf = UIManager.getSystemLookAndFeelClassName();
		return laf;
	}
	
	public String getApplicationTitle() {
	    String title = getQualifiedProperty("mainFrame.title");
	    if(title == null)
	        title = getQualifiedProperty("Application.title");
        return title;
	}

    /**
     * @return
     */
    public IFile getPropertyFile() {
        return propertyFile;
    }

    public IDocument getDocument() {
        return propertyFileEditor.getDocumentProvider().getDocument(propertyFileEditor.getEditorInput());
    }
    
        /**
         * @param propertyFileEditor
         */
        public void setPropertyFileEditor(TextEditor propertyFileEditor) {
        	this.propertyFileEditor = propertyFileEditor;
        	if(docListener == null) {
        		docListener = new IDocumentListener() {
        			public void documentAboutToBeChanged(DocumentEvent event) {
        			}
        			public void documentChanged(DocumentEvent event) {
        				editor.handlePropertyFileChanged(propertyFile);
        			}};
        	}
        	getDocument().addDocumentListener(docListener);
        }

    /**
     * @param fc
     */
    public void remove(FormComponent fc) {
        String id = fc.getIdentifier()+".";
        Vector toRemove = new Vector();
        Iterator it = propMap.keySet().iterator();
        while(it.hasNext()) {
            String key = (String) it.next();
            if(key.startsWith(id)) {
                toRemove.add(key);
            }
        }
        for(int i=0; i< toRemove.size(); i++) {
            removeProperty((String)toRemove.elementAt(i));
        }
    }

    /**
     * @param component
     * @param name
     */
    public void removeProperty(FormComponent fc, String pName) {
        String key = fc.getIdentifier()+"."+pName;
        if(propMap.getProperty(key) != null)
            removeProperty(key);
    }

    /**
     * @param oldName
     * @param newName
     */
    public void renameField(String oldName, String newName) {
        oldName += ".";
        newName += ".";
        Vector toRemove = new Vector();
        Iterator it = propMap.keySet().iterator();
        while(it.hasNext()) {
            String key = (String) it.next();
            if(key.startsWith(oldName)) {
                toRemove.add(key);
            }
        }
        for(int i=0; i< toRemove.size(); i++) {
            String key = (String) toRemove.elementAt(i);
            String val = (String) propMap.get(key);
            key = JiglooUtils.replace(key, oldName, newName);
            setProperty(key, val);
            removeProperty((String)toRemove.elementAt(i));
        }
    }

    /**
     * 
     */
    public void doSave() {
        if(propertyFileEditor != null)
            propertyFileEditor.doSave(null);
    }

    public void redoChange(Vector change) {
        PropertyChange.redo(change, propMap);
        propertyChanged = true;
        updateProperties(false);
        //if the property editor is changed, need to update the editor's dirtiness
        editor.setDirty(editor.isDirty());
    }

    public void undoChange(Vector change) {
        PropertyChange.undo(change, propMap);
        propertyChanged = true;
        updateProperties(false);
        //if the property editor is changed, need to update the editor's dirtiness
        editor.setDirty(editor.isDirty());
    }

	/**
	 * 
	 */
	public void createPropertyFileIfNeeded() {
	    if(propertyFile != null && !propertyFile.exists() 
	            && editor.isPartOfAppFrameworkApplication()) {
		    try {
		    	if(!propertyFile.getParent().exists())
		    		((IFolder)propertyFile.getParent()).create(true, true, null);
                propertyFile.create(new StringBufferInputStream("# Properties file created by Jigloo GUI builder"), true, null);
                editor.createPropertyFileEditor(true);
                propFileExists = Boolean.TRUE;
            } catch (CoreException e) {
                e.printStackTrace();
            }
		}
		
	}

    /**
     * @param propFile
     */
    public void setPropertyFile(IFile propFile) {
    	getDocument().removeDocumentListener(docListener);
        this.propertyFile = propFile;
        propertyFileEditor.setInput(new FileEditorInput(propFile));
    	getDocument().addDocumentListener(docListener);
    }

}
