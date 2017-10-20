/*
 * Created on May 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.appFramework;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jface.dialogs.MessageDialog;

import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.util.FileUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AppUtils {
//    public static final String JAVAX_SWING_APP_PACKAGE_NAME = "application";
    public static final String JAVAX_SWING_APP_PACKAGE_NAME = "org.jdesktop.application";

    public static final String JAVAX_SWING_APP_NAME = JAVAX_SWING_APP_PACKAGE_NAME+".Application";
    public static final String JAVAX_SWING_SFAPP_NAME = JAVAX_SWING_APP_PACKAGE_NAME+".SingleFrameApplication";
    
    private static boolean setDirty = false;

    public static final String APPFRAMEWORK_JAR = "appFramework-1.0.jar";
    /**
     * @param jcp
     */
    public static void updateImports(JavaCodeParser jcp) {
        setDirty = false;
        updateImport(jcp, "application.Application");
        updateImport(jcp, "application.Action");
        updateImport(jcp, "application.ActionManager");
        updateImport(jcp, "application.SingleFrameApplication");
        updateImport(jcp, "application.ApplicationContext");
        updateImport(jcp, "application.ApplicationAction");
        updateImport(jcp, "application.ApplicationActionManager");
        updateImport(jcp, "application.ApplicationActionMap");
        updateImport(jcp, "application.ResourceManager");
        updateImport(jcp, "application.ResourceMap");
        if(setDirty) {
			if(jcp.getMethodNode("getAppActionMap") != null) {
			    jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".ApplicationActionMap");
			    jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".Application");
			    jcp.removeMethodFromCode("getAppActionMap");
			    jcp.addMethod("getAppActionMap", "\t\treturn Application.getInstance().getContext().getActionMap(this);\n",
			            "ApplicationActionMap", null, null, Flags.AccPrivate, 
			            "\n    * Returns the action map used by this application.\n     * Actions defined using the Action annotation\n" +
			            "     * are returned by this method");
			}    	        
            jcp.getEditor().setDirtyAndUpdate();
            jcp.getEditor().doSave(null);
            jcp.getEditor().reparseJavaCode(true);
            MessageDialog.openInformation(jcp.getEditor().getSite().getShell(), "App Framework updated", 
                    "Your code was using an old version of the Swing Application Framework,\n" +
                    "so it has been updated by renaming import statements, updating the\n" +
                    "getAppActionMap method if necessary, and adding appFramework-1.0.jar\n" +
                    "to your project.");
        }
    }
    
    private static void updateImport(JavaCodeParser jcp, String oldImport) {
        if(jcp.hasImport(oldImport)) {
            setDirty = true;
            jcp.removeImport(oldImport);
            String newImport = JiglooUtils.replace(oldImport, "application.", JAVAX_SWING_APP_PACKAGE_NAME+".");
            jcp.addImport(newImport);
            FileUtils.addJarsToClassPath(jcp.getEditor().getSite().getShell(), jcp.getEditor().getJavaProject(), 
            		new String[] {"appFramework-1.0.jar", "jnlp.jar"}, AppUtils.JAVAX_SWING_APP_NAME);

        }
    }
}
