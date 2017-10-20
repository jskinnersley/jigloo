/*
 * Created on Jan 24, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

import javax.swing.JFrame;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CustomPropertyCellEditor extends TextCellEditor {

    private IPropertySource propSrc;
    private String id;
    private Button button;
    private Control contents;
    private PropertyEditor propEd;

    private class DialogCellLayout extends Layout {
        public void layout(Composite editor, boolean force) {
            Rectangle bounds = editor.getClientArea();
            Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            if (contents != null)
                contents.setBounds(0, 0, bounds.width - size.x, bounds.height);
            button.setBounds(bounds.width - size.x, 0, size.x, bounds.height);
        }

        public Point computeSize(Composite editor, int wHint, int hHint,
                boolean force) {
            if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT)
                return new Point(wHint, hHint);
            Point contentsSize = contents.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                    force);
            Point buttonSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                    force);
            // Just return the button width to ensure the button is not clipped
            // if the label is long.
            // The label will just use whatever extra width there is
            Point result = new Point(buttonSize.x, Math.max(contentsSize.y,
                    buttonSize.y));
            return result;
        }
    }

    public CustomPropertyCellEditor(Composite parent, IPropertySource comp,
            String id, PropertyEditor propEd) {
        super(parent);
        this.propSrc = comp;
        this.propEd = propEd;
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
        //		comp.selectInCode(id);
        try {
            super.doSetValue(value);
        } catch (Throwable t) {
            System.err.println("Error in CustomPropertyCellEditor.doSetValue "
                    + propSrc + ", " + id + ", " + value);
        }
    }

    protected Button createButton(Composite parent) {
        Button result = new Button(parent, SWT.DOWN);
        result.setText("..."); //$NON-NLS-1$
        return result;
    }

    class SwitchablePropertyChangeListener implements PropertyChangeListener {
        private boolean isOn = true;

        public void propertyChange(final PropertyChangeEvent evt) {
            //          System.err.println("PROP CHANGED "+evt.getPropertyName()
            //                  +", "+evt.getNewValue()+", "+evt.getOldValue());
            if (!isOn)
                return;
            applyValue();
        }

        public void turnOn(boolean on) {
            //            System.err.println("TURN ON "+on);
            //            new Exception().printStackTrace();
            isOn = on;
        }
    }

    private SwitchablePropertyChangeListener propCL = null;

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

        button.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                //                propCL.turnOn(false);
            }
        });

        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {

                Component custEd = propEd.getCustomEditor();

                final JFrame jf = new JFrame();

                jf.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        propCL.turnOn(false);
                        propEd.removePropertyChangeListener(propCL);
//                        System.err.println("WINDOW CLOSED");
                     }
                    public void windowOpened(WindowEvent e) {
                        propCL.turnOn(true);
                        propEd.addPropertyChangeListener(propCL);
//                        System.err.println("WINDOW OPENED");
                     }
                });

                if (propSrc instanceof LayoutWrapper) {
                    //since some layouts have properties changed after
                    //components are added to their containers
                    //    		        ((LayoutWrapper)propSrc).initProperties();
                }

                if (propCL == null) {
                    propCL = new SwitchablePropertyChangeListener();
                }
//                propEd.addPropertyChangeListener(propCL);

                jf.getContentPane().add(custEd);
                jf.pack();
                Point pt = button.toDisplay(0, 0);
                pt.x -= jf.getSize().width;
                pt.y -= jf.getSize().height;
                jf.setLocation(pt.x, pt.y);
                jf.show();
                new Thread() {
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                        }
                        jf.toFront();
                    }
                }.start();
            }
        });

        setValueValid(true);
        return parent;
    }

    private void applyValue() {
//        System.err.println("APPLY VALUE " + propEd.getValue() + ", " + propEd);
        //        new Exception().printStackTrace();
        final Object fpv = propEd.getValue();
        if (fpv == null)
            return;
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                try {
                    if (propSrc instanceof LayoutWrapper) {
                        LayoutWrapper lw = (LayoutWrapper) propSrc;
                        lw = (LayoutWrapper) lw.getCopy(lw.getFormComponent());
                        propSrc = lw;
                        FormComponent fc = lw.getFormComponent();
                        //                        System.err.println("SETTING CUSTOM PROP "+id+",
                        // "+propEd.getValue());
                        //disable listener so applyChanges isn't called over
                        // and over
                        propCL.turnOn(false);
//                        lw.setPropertyValue(id, propEd.getValue(), true, false);
                        
                        //need to force setting of value, because setPropertyValue
                        //compares old value with new value, but it gets the old value
                        //from the property editor itself (getCustomPropertyValue)
                        //which is the same as the new value.
                        lw.setPropertyValue(id, fpv, true, true);
                        fc.setLayoutWrapper(lw, true, false);
                        fc.getEditor().setDirtyAndUpdate();
                        propCL.turnOn(true);
                    } else {
//                        propSrc.setPropertyValue(id, propEd.getValue());
                        propSrc.setPropertyValue(id, fpv);
                    }
                } catch (Throwable e) {
                    jiglooPlugin.handleError(e);
                }
            }
        });
    }
}