/*
 * Created on May 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.appFramework;

import java.util.HashMap;

import javax.swing.AbstractAction;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ActionManager {
	
	private FormEditor editor;
	private HashMap actionMap = new HashMap();
	private BundleManager bundleManager;
	
	public ActionManager(FormEditor editor) {
	    this.editor = editor;
		bundleManager = editor.getBundleManager();
	}
	
	public void reload() {
	    actionMap.clear();
	    if(!editor.isPartOfAppFrameworkApplication())
	        return;
		createAction("cut");
		createAction("copy");
		createAction("paste");
		createAction("delete");
	}
	
	public void addAction(MethodDeclaration mdec) {
		String name = mdec.getName().getIdentifier();
		createAction(name);
	}
	
	public FormComponent createAction(String name) {
		FormComponent action = FormComponent.newFormComponent(editor, AbstractAction.class.getName());
		action.setName(name);
//		action.setEditor(editor);
//		action.setClassName(AbstractAction.class.getName());
		action.setInline(true);
		action.setAssigned(true);
		action.setExistsInCode(true);
		
		action.setPropertyValue("name",action.getIdentifier());
		
		action.setPropertyValue("text", bundleManager.getProperty(action, "text", name));
		
		Object iconProp = bundleManager.getProperty(action, "icon", null);
		if(iconProp != null) {
			action.getObject(true);
			action.setPropertyValue("icon", iconProp);
		}
		
		editor.getJavaCodeParser().putField(name, action);
		actionMap.put(name, action);
		action.setDeclarationInCode("getAppActionMap().get(\""+name+"\")");
		return action;
	}

	/**
	 * @param code
	 * @param object
	 * @return
	 */
	public FormComponent getAction(String actionName, String code) {
		FormComponent action = (FormComponent) actionMap.get(actionName);
		if(action != null)
			action.setDeclarationInCode(code);
		return action;
	}

    /**
     * @param editor
     * @param declarationInCode
     */
    public static void testForActionManagerGetter(FormEditor editor, String declarationInCode) {
        JavaCodeParser jcp = editor.getJavaCodeParser();
	    if(declarationInCode.startsWith("getAppActionMap()") && editor.isPartOfAppFrameworkApplication()) {
			if(jcp.getMethodNode("getAppActionMap") == null) {
			    jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".ApplicationActionMap");
			    jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".Application");
			    
			    jcp.addMethod("getAppActionMap", "\t\treturn Application.getInstance().getContext().getActionMap(this);\n",
			            "ApplicationActionMap", null, null, Flags.AccPrivate, 
			            "\n    * Returns the action map used by this application.\n     * Actions defined using the Action annotation\n" +
			            "     * are returned by this method");
			}    	        
	    }
    }

}
