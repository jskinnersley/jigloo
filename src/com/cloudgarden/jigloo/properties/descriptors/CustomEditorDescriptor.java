package com.cloudgarden.jigloo.properties.descriptors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.dialog.SwingDialog;
import com.cloudgarden.jigloo.properties.FormTextCellEditor;
import com.cloudgarden.jigloo.resource.CursorManager;
import com.cloudgarden.jigloo.util.DelayableRunnable;

public class CustomEditorDescriptor extends FormPropertyDescriptor {

	private PropertyEditor propEd;

	/**
	 * Creates an property descriptor with the given id and display name.
	 * For PropertyEditors which support custom editors
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public CustomEditorDescriptor(
		Object id,
		String displayName,
		PropertyEditor propEd,
		FormComponent comp) {
		super(id, displayName, comp);
		this.propEd = propEd;
	}

    private JFrame frame = null;
    private SwingDialog sd = null;
    private Object initVal = null;
    
    private void setPropEdValue(Object value) {
        try {
            if (value instanceof String) {
                propEd.setAsText((String) value);
                if(!value.equals(propEd.getValue()))
                    propEd.setValue(value);
            } else {
                propEd.setValue(value);
            }
        } catch(Throwable t) {}
    }
    
    private boolean showCustomizer(Composite parent) {
        try {
//            UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
            try {
                initVal = comp.getPropertyValue(getId());
                if(initVal != null) {
                    setPropEdValue(initVal);
                }
            } catch(Throwable t) {
                jiglooPlugin.handleError(t, "Error setting value for property editor "+comp+", "+getId());
            }
            Component edComp = propEd.getCustomEditor();
//            edComp.addPropertyChangeListener(new PropertyChangeListener() {
//                public void propertyChange(final PropertyChangeEvent evt) {
//                    jiglooPlugin.writeToLog("GOT CUSTOM EDITOR PROPERTY CHANGE "
//                            + evt.getPropertyName() + ", " + evt.getNewValue());
//                    Display.getDefault().asyncExec(new Runnable() {
//                        public void run() {
//                            try {
//                                String id = evt.getPropertyName();
//                                Object value = evt.getNewValue();
//                                boolean updateCode = false;
//                                if (comp.hasProperty((String)getId())) {
//                                    comp.setPropertyValue((String)getId(), propEd.getValue());
//                                    updateCode = true;
//                                }
//                                if ("UPDATE".equals(id)) {
//                                    updateCode = true;
//                                }
//                                
//                                if(updateCode) {
//                                    comp.setNeedsUpdateInCode((String)getId());
//                                    comp.updateInCode((String)getId());
//                                    comp.getEditor().setDirtyAndUpdate();
//                                }
//                            } catch (Throwable e) {
//                                jiglooPlugin.handleError(e);
//                            }
//                        }
//                    });
//                }
//            });
            JPanel panel = new JPanel();
            JPanel buttonBar = new JPanel();
            JPanel buttonBar2 = new JPanel();
            buttonBar2.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonBar2.add(buttonBar);
            
            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelled = false;
                    if(frame != null)
                        frame.dispose();
                    else if(sd != null)
                        sd.exit();
                }
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(initVal != null) {
                        setPropEdValue(initVal);
                    }
                    cancelled = true;

                    if(frame != null)
                        frame.dispose();
                    else if(sd != null)
                        sd.exit();
                }
            });
            
            buttonBar.setLayout(new GridLayout(1, 2, 10, 5));
            buttonBar.add(ok);
            buttonBar.add(cancel);
            
            panel.setLayout(new BorderLayout(10, 10));
            panel.add((Component) edComp, "Center");
            panel.add(buttonBar2, "South");
			
            if(jiglooPlugin.canUseSWT_AWT()) {
                sd = new SwingDialog(parent.getShell(), SWT.NULL);
                sd.setText("Edit property: " + getDisplayName()+" for "+comp.getDisplayName());
                sd.setJPanel(panel);
                sd.open();
            } else {
    			Rectangle sb = parent.getShell().getBounds();
                frame = new JFrame();
                sd.setText("Edit property: " + getDisplayName()+" for "+comp.getDisplayName());
                frame.setContentPane(panel);
                frame.pack();
                jiglooPlugin.getDefault().addAllowedWindow(frame);
    			frame.setLocation(sb.x + (sb.width - frame.getSize().width) / 2, 
    			        sb.y + (sb.height - frame.getSize().height) / 2);
    			frame.show();
                DelayableRunnable tf = new DelayableRunnable(1000) {
                    public void run() {
                        frame.toFront();
                    }
                };
                tf.trigger();
            }
            
            return true;
        } catch (Throwable e) {
        	jiglooPlugin.handleError(parent.getShell(), 
                    "Error showing customizer",
                    "Error showing customizer for "+comp, e);
        }
        return false;
    }

	private boolean cancelled = true;
	
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(final Composite parent) {
		try {
			CellEditor cellEditor = new FormTextCellEditor(parent, comp, (String)getId()) {
				
			    Object value = null;
				
			    protected void createDialog(Shell shell) {
				    cancelled = false;
				    parent.setCursor(CursorManager.getCursor(SWT.CURSOR_WAIT));
				    Object initVal = propEd.getValue();
				    showCustomizer(parent);
				    
				    if(cancelled) {
					    value = null;
					    propEd.setValue(initVal);
				        fireCancelEditor();
				    } else {
					    value = propEd.getValue();
				        fireApplyEditorValue();
				    }
				    parent.setCursor(CursorManager.getCursor(SWT.CURSOR_ARROW));
				}
                
				protected Object doGetValue() {
				    if(value != null) {
//				        super.doSetValue(value);
				        Object val2 = value;
				        value = null;
				        return val2;
				    }
				    return super.doGetValue();
                }
                
			};
			return cellEditor;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}
