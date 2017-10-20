package com.cloudgarden.jigloo.wrappers;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormComponentWrapper {

	FormComponent comp;
	String compName;
	FormEditor editor;
	//set to true, this signifies that comp.copiedTo() is the FormComponent
	//which should be returned by getFormComponent()
	boolean isCopy = false;

	public FormComponentWrapper(FormComponent comp) {
		this.comp = comp;
		editor = comp.getEditor();
	}

	public FormComponentWrapper(String compName, FormEditor editor) {
		this.compName = compName;
		this.editor = editor;
	}

	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public FormComponentWrapper getCopy() {
		FormComponentWrapper fcw = null;
		if (comp == null) {
			fcw = new FormComponentWrapper(compName, editor);
			//comp = editor.getComponentByName(compName);
		} else {
			fcw = new FormComponentWrapper(comp);
		}
		fcw.setCopy(true);
		return fcw;
	}

	public FormComponent getFormComponent() {
		if (comp == null) {
			if (editor != null)
				comp = editor.getComponentByName(compName);
		}
		FormComponent cc = comp;
		if (isCopy) {
			while (cc.getCopiedTo() != null)
				cc = cc.getCopiedTo();
			//System.err.println(
			//"FCW IS COPY " + comp.getName() + ", copied to " + cc);
			if (cc != null)
				return cc;
		}
		return comp;
	}

	public String toString() {
		FormComponent fc = getFormComponent();
		if (fc == null)
			return compName;
		return fc.getName();
	}

	public boolean equals(Object o) {
		if (o instanceof FormComponentWrapper) {
			return toString().equals(((FormComponentWrapper) o).toString());
		}
		return false;
	}

}
