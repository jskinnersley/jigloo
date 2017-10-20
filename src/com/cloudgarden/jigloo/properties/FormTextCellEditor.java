/*
 * Created on Jan 24, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormTextCellEditor extends TextCellEditor {

	private FormComponent comp;
	private String id;
	private Button button;
	private Control contents;

	class DialogCellLayout extends Layout {
		
		public void layout(Composite editor, boolean force) {
			Rectangle bounds = editor.getClientArea();
			Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
			if (contents != null)
				contents.setBounds(0, 0, bounds.width-size.x, bounds.height);
			button.setBounds(bounds.width-size.x, 0, size.x, bounds.height);
		}
		public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT)
				return new Point(wHint, hHint);
			Point contentsSize = contents.computeSize(SWT.DEFAULT, SWT.DEFAULT, force); 
			Point buttonSize =  button.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
			// Just return the button width to ensure the button is not clipped
			// if the label is long.
			// The label will just use whatever extra width there is
			Point result = new Point(buttonSize.x,
							        Math.max(contentsSize.y, buttonSize.y));
			return result;			
		}
	}

	public FormTextCellEditor(Composite parent, FormComponent comp, String id) {
		super(parent);
		this.comp = comp;
		this.id = id;
	}

	public void setFocus() {
		if (jiglooPlugin.isLinux()) {
			return;
		}
		super.setFocus();
	}

    protected void focusLost() {
//        System.out.println("FOCUS LOST");
//        super.focusLost();
    }
	protected void doSetValue(Object value) {
		comp.selectInCode(id);
		try {
		    super.doSetValue(value);
		} catch (Throwable t) {
			System.err.println("Error in FormTextCellEditor.doSetValue " + comp + ", " + id + ", " + value+", "+t);
		}
	}

	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setText("..."); //$NON-NLS-1$
		return result;
	}

    protected Control createControl(Composite parent) {
    	Font font = parent.getFont();
    	Color bg = parent.getBackground();

    	parent = new Composite(parent, SWT.NULL);
    	parent.setLayout(new DialogCellLayout());
    	parent.setBackground(bg);
    	parent.setFont(font);

    	contents = super.createControl(parent);
    	
    	button = createButton(parent);
    	button.setFont(font);
    	
    	button.addKeyListener(new KeyAdapter() {
    		public void keyReleased(KeyEvent e) {
    			if (e.character == '\u001b') { // Escape
    				fireCancelEditor();
    			}
    		}
    	});

    	final Shell shell = parent.getShell();
    	
    	button.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent event) {
    			createDialog(shell);
    		}
    	});

    	setValueValid(true);
        return parent;
    }

	protected void createDialog(Shell shell) {
		if("colSpecs".equals(id) || "rowSpecs".equals(id)) {
			Object lm = comp.getLayoutWrapper().getObject();
			if(lm instanceof FormLayout) {
				JGFormLayoutDialog jgfd = new JGFormLayoutDialog(shell, SWT.NULL);
				jgfd.setFormLayout((FormLayout)lm, "colSpecs".equals(id));
				jgfd.open();
				String newSpec = jgfd.getFormSpecString();
				if (newSpec != null) {
					markDirty();
					doSetValue(newSpec);
					fireApplyEditorValue();
				}
				return;
			}
		}
	    StringDialog dialog = new StringDialog(shell, SWT.NULL);
    	Text text = (Text)contents;
    	String txt = text.getText();
    	txt = txt.replaceAll("\\\\\\\\", "\\\\");
    	txt = JiglooUtils.replace(txt, "\\n", "\n");
    	txt = JiglooUtils.replace(txt, "\\r", "\r");
    	txt = JiglooUtils.replace(txt, "\\t", "\t");
    	dialog.setValue(txt);
	    dialog.open();
		String newValue = dialog.getValue();
		if (newValue != null) {
//		    newValue = JiglooUtils.replace(newValue, "\r", "");
//		    newValue = JiglooUtils.replace(newValue, "\n", "\\n");
//		    newValue = JiglooUtils.replace(newValue, "\t", "\\t");
			markDirty();
			doSetValue(newValue);
			fireApplyEditorValue();
		}
	}
}
