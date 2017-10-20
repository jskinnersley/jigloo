/*
 * Created on Apr 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.Expression;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.appFramework.AppUtils;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.interfaces.NonDefaultConstructor;
import com.cloudgarden.jigloo.interfaces.SyntheticPropertyContainer;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.KeyStrokeWrapper;
import com.cloudgarden.jigloo.wrappers.MnemonicWrapper;

/**
 * v4.0M3
 */
public class ActionStub extends AbstractAction implements NonDefaultConstructor, SyntheticPropertyContainer {

	IconWrapper iconWrapper = null;
	
	public ActionStub() {
		super("action");
	}
	
	public ActionStub(String actionName) {
		super(actionName);
	}
	
	public ActionStub(String label, Icon icon) {
		super(label, icon);
	}
	
	public void actionPerformed(ActionEvent e) {
	}

	public Integer getMnemonic() {
		return (Integer) getValue(MNEMONIC_KEY);
	}
	public String getAccelerator() {
		return (String) getValue(ACCELERATOR_KEY);
	}
	public void setAccelerator(KeyStroke accelerator) {
		putValue(ACCELERATOR_KEY, accelerator);
	}
	public String getShortDescription() {
		return (String) getValue(SHORT_DESCRIPTION);
	}
	public void setShortDescription(String desc) {
		putValue(SHORT_DESCRIPTION, desc);
	}
	public Icon getLargeIcon() {
		return (Icon) getValue("SwingLargeIconKey");
	}
	public void setLargeIcon(Icon icon) {
		putValue("SwingLargeIconKey", icon);
	}
	public String getText() {
		return (String) getValue(NAME);
	}
	public Icon getIcon() {
		return (Icon) getValue(SMALL_ICON);
	}
	
	public void setText(String name) {
		putValue(NAME, name);
	}
	public void setMnemonic(Integer mnem) {
		putValue(MNEMONIC_KEY, mnem);
	}
	public void setIcon(Icon icon) {
		putValue(SMALL_ICON, icon);
	}

	public String getConstructorCode(FormComponent fc, IJavaCodeManager jcm) {
		String anonCode = fc.getAnonClassCode();
		if(anonCode == null) {
			jcm.addImport("java.awt.event.ActionEvent");
			anonCode = "{\npublic void actionPerformed(ActionEvent evt) {\n}\n}";
		}
        return  fc.getNameInCode() + " = new "+fc.getClassNameForCode()
                + "("+fc.getJavaCodeForProperty("text")+", "
				+fc.getJavaCodeForProperty("icon")+") "
				+anonCode+";\n";
	}

	public boolean setSynthProperty(FormComponent fc, String propName, Object value) {
		
		if(value instanceof FormComponent)
			value = ((FormComponent)value).getObject(true);
		
	    if (propName.equals("text")) {
			setText((String)value);
			fc.putProperty("text", value);
			
		} else if (propName.equals("accelerator")) {
		    KeyStroke ks = null;
			if(value instanceof String) {
				if("delete".equals(value) || "insert".equals(value))
					value = ((String)value).toUpperCase();
				ks = KeyStroke.getKeyStroke((String)value);
			} else if(value instanceof KeyStroke)
				ks = (KeyStroke)value;
			else if(value instanceof KeyStrokeWrapper)
				ks = ((KeyStrokeWrapper)value).getKeyStroke();
			if(ks != null) {
				setAccelerator(ks);
				fc.putProperty("accelerator", new KeyStrokeWrapper(ks, fc));
			}			
		} else if (propName.equals("shortDescription")) {
			setShortDescription((String)value);
			fc.putProperty("shortDescription", value);
			
		} else if (propName.equals("icon")) {
			if(value instanceof IconWrapper)
				setIcon(((IconWrapper)value).getIcon());
			else
				setIcon((Icon)value);
			fc.putProperty("icon", value);
			
		} else if (propName.equals("mnemonic")) {
			if(value instanceof Integer)
				value = new MnemonicWrapper((Integer)value, fc);
			setMnemonic((Integer)((MnemonicWrapper)value).getValue(null));
			fc.putProperty("mnemonic", value);
			
		} else if (propName.equals("largeIcon")) {
			if(value instanceof IconWrapper)
				setLargeIcon(((IconWrapper)value).getIcon());
			else
			    setLargeIcon((Icon)value);
			fc.putProperty("largeIcon", value);
			
		} else {
			return false;
		}
		if(fc.getEditor() != null && !fc.getEditor().isProcessing()) {
		    if(fc.getEditor().getBundleManager().setProperty(fc, propName, value))
		        return true;
			fc.setConstructorCode(null, fc.getAnonClassCode());
			fc.repairConstructorInCode();
		}
		return true;
	}

	public Object getSynthProperty(FormComponent fc, String propName) {
		return null;
	}

    /**
     * @param component
     */
    public void addToCode(FormComponent fc,JavaCodeParser jcp) {
		jcp.addImport(AppUtils.JAVAX_SWING_APP_PACKAGE_NAME+".Action");
        jcp.addMethod(fc.getNameInCode(), "", "void", null, null , Flags.AccPublic, "@Action");
    }

    /**
     * @param component
     */
    public static void addSynthProperties(FormComponent fc) {
        fc.addSynthProperty("text", String.class, "");
        fc.addSynthProperty("accelerator", KeyStroke.class, new KeyStrokeWrapper(KeyStroke.getKeyStroke(""), fc));
        fc.addSynthProperty("mnemonic", int.class, new MnemonicWrapper(new Integer(0), fc));
        fc.addSynthProperty("shortDescription", String.class, "");
        fc.addSynthProperty("icon", Icon.class, new IconWrapper((Icon)null, fc));
        fc.addSynthProperty("largeIcon", Icon.class, new IconWrapper((Icon)null, fc));
    }

    /**
     * @param id
     * @param jcm
     * @return
     */
    public String getJavaCodeForPropertySetter(FormComponent fc, String id, String value) {
        if(value == null)
        	return null;
        String elemName = fc.getNameInCode();
        String key = null;
        if(id.equals("shortDescription"))
            key = "javax.swing.Action.SHORT_DESCRIPTION"; 
        else if(id.equals("largeIcon"))
            key = "javax.swing.Action.LARGE_ICON"; 
        else if(id.equals("mnemonic")) {
            key = "javax.swing.Action.MNEMONIC_KEY"; 
            value = "new Integer("+value+")";
        } else if(id.equals("accelerator")) {
            key = "javax.swing.Action.ACCELERATOR_KEY"; 
            if(value.indexOf(".getKeyStroke") < 0)
            	value = "javax.swing.KeyStroke.getKeyStroke("+value+")";
        } else
            return null;
        return elemName+".putValue("+key+", "+value+");\n";
    }

	/**
	 * Handles params passed in while parsing the putValue method calls.
	 */
	public void handlePropertySetter(Object[] params, FormComponent fc, 
			JavaCodeParser jcp, Expression mic) {
		String propName = null;
    	if(Action.ACCELERATOR_KEY.equals(params[0]))
    		propName = "accelerator";
    	else if(Action.MNEMONIC_KEY.equals(params[0]))
    		propName = "mnemonic";
    	else if(Action.SHORT_DESCRIPTION.equals(params[0]))
    		propName = "shortDescription";
    	else if("SwingLargeIconKey".equals(params[0]))
    		propName = "largeIcon";
    	else
    		return;
		fc.setPropertyValue(propName, params[1]);
    	jcp.getPropMap(fc.getName()).put(propName, mic);
	}

}
