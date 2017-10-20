/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.awt.GridBagConstraints;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.dialog.EditValueDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormLayoutDataAction extends Action {
	private FormComponent comp;
	private FormEditor editor;
	private String propName;
	private Object cValue;
	private boolean isCustom = false;

	public static String[] tableConstraints = {
	        "center-horiz","c", "left", "l","right","r","fill-horiz", "f",
	        "center-vert", "c", "top", "t","bottom", "b", "fill-vert", "f"};
	
	public FormLayoutDataAction(
		FormEditor editor,
		String propName,
		Object value,
		String label) {
	    super(label, Action.AS_CHECK_BOX);
		this.editor = editor;
		this.propName = propName;
		this.cValue = value;
		if(value == null)
			isCustom = true;
		if(label == null)
			label = "Edit constraint";
		setText(label);
	}

	public Object getValue() {
	    return cValue;
	}
	
	public Object getPropertyValue() {
	    FormComponent sel = editor.getSelectedComponent();
	    if(sel == null)
	        return null;
	    return sel.getLayoutDataWrapper().getPropertyValue(propName);
	}

	public boolean isEnabled() {
		try {
			FormComponent sel = editor.getSelectedComponent();
			if (sel == null)
				return false;
			if (sel.getParent() != null && sel.getParent().usesGroupLayout()) {
				return LayoutGroup.isEnabled(cValue, sel);
			}
			return super.isEnabled();
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}
    
    public boolean isChecked() {
        return isOn();
    }
    
	public boolean isOn() {
		try {
	    FormComponent sel = editor.getSelectedComponent();
	    if(sel == null)
	        return false;
		String modifier = null;
		String pn2 = propName;
		if(propName.endsWith("-"))
		    modifier = "-";
		if(propName.endsWith("+"))
		    modifier = "+";
		if(modifier != null) {
		    pn2 = propName.substring(0, propName.length() - modifier.length());
		}
	    Object val = sel.getLayoutDataWrapper().getPropertyValue(pn2);

	    if(sel.getParent() != null && sel.getParent().usesGroupLayout()) {
	        if(val instanceof Boolean)
	            return ((Boolean)val).booleanValue();
	        //this is for DEFAULT_SIZE
	        return false;
	    }
	    
	    String plt = sel.getParentLayoutType();
	    if(cValue instanceof Toggle) {
	        return Boolean.TRUE.equals(val);
	    }
	    if(val instanceof String && "Table".equals(plt)) {
	        String trim = JiglooUtils.replace((String)val, " ", "");
	        String[] parts = JiglooUtils.split(",", trim);
	        for (int i = 0; i < tableConstraints.length; i+=2) {
                String con = tableConstraints[i];
                if(con.equals(cValue)) {
                    int offset = 0;
                    if(i >= 8)
                        offset = 1;
                    for(int j=offset;j<parts.length;j+=2) {
                        if(parts[j].equals(tableConstraints[i+1])
                                || parts[j].startsWith(tableConstraints[i+1]))
                            return true;
                    }
                }
            }
	        return false;
	    } else if(val instanceof FieldWrapper) {
	        Object vv = ((FieldWrapper)val).getValue();
	        if(pn2.equals("fill") && modifier != null) {
	            int v1 = ((Integer)vv).intValue();
	            int v2 = ((Integer)cValue).intValue();
	            if(v1 == GridBagConstraints.BOTH) {
                    return true;
	            }
	            if(v1 == GridBagConstraints.NONE) {
	                return false;
	            }
	            if(v1 == v2)
	                return true;
	            return false;
	        }
	        if(vv != null)
	            return vv.equals(cValue);
	    }
	    if(cValue != null && cValue.equals(val))
	        return  true;
		} catch(Throwable t) {
			t.printStackTrace();
		}
	    return false;
	}
	
	public void run() {
	    run(cValue);
	}
		
	public void run(Object newValue) {
		try {
			FormComponent sel = editor.getSelectedComponent();
			if(isCustom) {
				EditValueDialog evd = new EditValueDialog(editor.getSite().getShell(), SWT.DIALOG_TRIM);
				Object con = sel.getLayoutDataWrapper().getLayoutData();
				evd.initText(con == null ? "" : con.toString(), "Edit layout constraint");
				evd.open();
				newValue = evd.getValue();
				if(newValue == null)
					return;
			}
		    boolean changed = false;
			LayoutDataWrapper lwSel = sel.getLayoutDataWrapper();
			for (int i = 0; i < editor.getSelectedComponents().size(); i++) {
				
				boolean lastFC = i == editor.getSelectedComponents().size()-1;
				editor.setUpdatingLastFC(lastFC);
				
				FormComponent fc = editor.getSelectedComponent(i);
				LayoutDataWrapper lw = fc.getLayoutDataWrapper();
				Object setVal = newValue;
				String modifier = null;
				if(propName.endsWith("-"))
				    modifier = "-";
				if(propName.endsWith("+"))
				    modifier = "+";
				String pn2 = propName;
				Object val = null;
				if(modifier != null) {
				    //gridbag constraint
				    pn2 = propName.substring(0, propName.length() - modifier.length());
				    val = lwSel.getPropertyValue(pn2);
				    if(val instanceof FieldWrapper) {
				        val = ((FieldWrapper)val).getValue();
				    }
				    if(val instanceof Integer && newValue instanceof Integer) {
				        int v1 = ((Integer)val).intValue();
				        int v2 = ((Integer)newValue).intValue();
				        
				        if(v1 == GridBagConstraints.BOTH) {
				            if(v2 == GridBagConstraints.VERTICAL) {
				                setVal = new Integer(GridBagConstraints.HORIZONTAL);
				            } else if(v2 == GridBagConstraints.HORIZONTAL) {
				                setVal = new Integer(GridBagConstraints.VERTICAL);
				            }
				        } else if(v1 == GridBagConstraints.VERTICAL && v2 == GridBagConstraints.HORIZONTAL) {
				            setVal = new Integer(GridBagConstraints.BOTH);
				        } else if(v1 == GridBagConstraints.HORIZONTAL && v2 == GridBagConstraints.VERTICAL) {
				            setVal = new Integer(GridBagConstraints.BOTH);
				        } else if(v1 == v2) {
				            setVal = new Integer(GridBagConstraints.NONE);
				        } else if(v1 == GridBagConstraints.NONE) {
				            setVal = new Integer(v2);
				        }
				        
				    }
				} else {
				    val = lwSel.getPropertyValue(pn2);
				}
				
				if(
//				        (!lw.usesSimpleConstraint() && !lw.hasProperty(pn2))
//				        || 
				        (val != null && val.equals(setVal))
				)
				    continue;
				
				if(newValue instanceof Toggle) {
				    if(((Boolean)val).booleanValue())
				        val = Boolean.FALSE;
				    else
				        val = Boolean.TRUE;
					lw.setPropertyValue(pn2, val);
				} else {
				    if(setVal != null)
				        lw.setPropertyValue(pn2, setVal);
				}
				
				fc.settingLayoutConstraint(true);
				fc.setLayoutDataWrapper(lw);
				fc.settingLayoutConstraint(false);
				
				if (lw.isSet())
				    fc.setPropertyValueInternal("layoutData", lw, true, false);
				else
				    fc.resetPropertyValue("layoutData");
				
				if (fc.isSwing()) {
				    fc.repairParentConnectionNode();
				} else {
					fc.updateInCode();
//					fc.repairInCode();
				}

				changed = true;
			}
			if(changed) {
                editor.setDirtyAndUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		editor.setUpdatingLastFC(true);
	}

    public String getPropName() {
        return propName;
    }
    
    public void setPropName(String propName) {
        this.propName = propName;
    }
}
