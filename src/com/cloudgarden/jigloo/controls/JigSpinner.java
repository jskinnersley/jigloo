package com.cloudgarden.jigloo.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JigSpinner extends org.eclipse.swt.widgets.Composite {
    private Text text1;
    private Button button1;
    private Button button2;
    public int min = 0;

    public JigSpinner(org.eclipse.swt.widgets.Composite parent, int style) {
        super(parent, SWT.NULL);
        initGUI();
    }

    private void initGUI() {
        try {
            GridLayout thisLayout = new GridLayout();
            this.setLayout(thisLayout);
            thisLayout.numColumns = 2;
            thisLayout.verticalSpacing = 0;
            thisLayout.marginHeight = 0;
            thisLayout.marginWidth = 0;
            thisLayout.horizontalSpacing = 0;
            {
                text1 = new Text(this, SWT.BORDER);
                GridData text1LData = new GridData();
                text1LData.verticalSpan = 2;
                text1LData.grabExcessVerticalSpace = true;
                text1LData.verticalAlignment = GridData.FILL;
                text1LData.grabExcessHorizontalSpace = true;
                text1LData.horizontalAlignment = GridData.FILL;
                text1.setLayoutData(text1LData);
            }
            {
                button1 = new Button(this, SWT.ARROW);
                GridData button1LData = new GridData();
                button1LData.widthHint = 12;
                button1LData.heightHint = 10;
                button1.setLayoutData(button1LData);
                button1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        try {
                            int val = Integer.parseInt(text1.getText());
                            text1.setText((val + 1) + "");
                        } catch (Throwable t) {
                        }
                    }
                });
            }
            {
                button2 = new Button(this, SWT.ARROW | SWT.DOWN);
                GridData button2LData = new GridData();
                button2LData.widthHint = 12;
                button2LData.heightHint = 10;
                button2.setLayoutData(button2LData);
                button2.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        try {
                            int val = Integer.parseInt(text1.getText());
                            if(val <= min)
                                return;
                            text1.setText((val - 1) + "");
                        } catch (Throwable t) {
                        }
                    }
                });
            }
            this.layout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text1.getText();
    }

    public void setText(String txt) {
        text1.setText(txt);
        text1.setSelection(txt.length());
    }

    /**
     * @param listener
     */
    public void addModifyListener(ModifyListener listener) {
        text1.addModifyListener(listener);
    }
    
}