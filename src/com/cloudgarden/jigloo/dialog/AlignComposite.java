package com.cloudgarden.jigloo.dialog;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.actions.FormLayoutDataAction;
import com.cloudgarden.jigloo.actions.Toggle;
import com.cloudgarden.jigloo.controls.JigSpinner;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.jgoodies.forms.layout.CellConstraints;

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
public class AlignComposite extends org.eclipse.swt.widgets.Composite {

    private Group group1;
    private JigSpinner spinner3;
    private CLabel cenButton;
    private CLabel hgButton;
    private JigSpinner spinner4;
    private Label label5;
    private Button button1;
    private CLabel vcButton;
    private Label label16;
    private JigSpinner spinner2;
    private Label label15;
    private JigSpinner spinner1;
    private Label label14;
    private Group group2;
    private CLabel swButton;
    private Label label10;
    private Label label13;
    private CLabel sButton;
    private Label label12;
    private CLabel seButton;
    private Label label11;
    private CLabel vgButton;
    private Label label8;
    private CLabel eButton;
    private Label label6;
    private Label label4;
    private Label label3;
    private Label label2;
    private Label label1;
    private CLabel hcButton;
    private CLabel hfButton;
    private CLabel wButton;
    private CLabel vfButton;
    private CLabel neButton;
    private CLabel nButton;
    private CLabel nwButton;
    
    private FormEditor editor;

    private ISelectionChangedListener selList = new ISelectionChangedListener() {
        public void selectionChanged(SelectionChangedEvent event) {
            setBGColors();
        }
    };
    
    /**
     * Auto-generated main method to display this
     * org.eclipse.swt.widgets.Composite inside a new Shell.
     */
    public static void main(String[] args) {
        showGUI();
    }

    /**
     * Auto-generated method to display this org.eclipse.swt.widgets.Composite
     * inside a new Shell.
     */
    public static void showGUI() {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        AlignComposite inst = new AlignComposite(shell, SWT.NULL);
        Point size = inst.getSize();
        shell.setLayout(new FillLayout());
        shell.layout();
        if (size.x == 0 && size.y == 0) {
            inst.pack();
            shell.pack();
        } else {
            Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
            shell.setSize(shellBounds.width, shellBounds.height);
        }
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    public AlignComposite(org.eclipse.swt.widgets.Composite parent, int style) {
        super(parent, style);
        initGUI();
    }

    private FormLayoutDataAction nwAction, nAction, neAction, eAction,
            seAction, sAction, swAction, wAction, vcAction, hcAction, vfAction,
            hfAction, hgAction, vgAction, cenAction,
            spinner1Action, spinner2Action, spinner4Action, spinner3Action;

    private Color butBG = null;

    public void setActions(FormEditor editor) {
        this.editor = editor;
        FormComponent sel = editor.getSelectedComponent();
        if(sel != null)
            getShell().setText(sel.getNameInCode());
        String plt = sel.getParentLayoutType();
        
        if (sel.isSWT() && "Grid".equals(plt)) {
            setButtonsVisible();
            nwButton.setVisible(false);
            swButton.setVisible(false);
            neButton.setVisible(false);
            seButton.setVisible(false);
            cenButton.setVisible(false);
            nAction = editor.getFormLayoutDataAction("verticalAlignment",
                    new Integer(GridData.BEGINNING), "", "VABEG");
            sAction = editor.getFormLayoutDataAction("verticalAlignment",
                    new Integer(GridData.END), "", "VAEND");
            wAction = editor.getFormLayoutDataAction("horizontalAlignment",
                    new Integer(GridData.BEGINNING), "", "HABEG");
            eAction = editor.getFormLayoutDataAction("horizontalAlignment",
                    new Integer(GridData.END), "", "HAEND");
            vcAction = editor.getFormLayoutDataAction("verticalAlignment",
                    new Integer(GridData.CENTER), "", "VACEN");
            hcAction = editor.getFormLayoutDataAction("horizontalAlignment",
                    new Integer(GridData.CENTER), "", "HACEN");
            vfAction = editor.getFormLayoutDataAction("verticalAlignment",
                    new Integer(GridData.FILL), "", "VAFILL");
            vfButton.setToolTipText("Fill Vertically");
            hfAction = editor.getFormLayoutDataAction("horizontalAlignment",
                    new Integer(GridData.FILL), "", "HAFILL");
            hfButton.setToolTipText("Fill Horizontally");
            hgAction = editor.getFormLayoutDataAction(
                    "grabExcessHorizontalSpace", new Toggle(), "", "HAGRAB");
            vgAction = editor.getFormLayoutDataAction(
                    "grabExcessVerticalSpace", new Toggle(), "", "VAGRAB");
            final LayoutDataWrapper ldw = sel.getLayoutDataWrapper();
            spinner1Action = editor.getFormLayoutDataAction(
                    "horizontalSpan", "", "", "HASPAN");
            spinner2Action = editor.getFormLayoutDataAction(
                    "verticalSpan", "", "", "VASPAN");
            spinner3Action = editor.getFormLayoutDataAction(
                    "horizontalIndent", "", "", "HAINDENT");
            label5.setVisible(false);
            spinner1.min = 1;
            spinner2.min = 1;
            spinner4.setVisible(false);
            
        } else if (sel.isSwing() && "GridBag".equals(plt)) {
            
            setButtonsVisible();
            label14.setText("Top Inset:");
            label15.setText("Right Inset:");
            label16.setText("Bottom Inset:");
            spinner1.min = 0;
            spinner2.min = 0;
            label5.setVisible(true);
            spinner1Action = editor.getFormLayoutDataAction(
                    "insets", "top_inset", "", "GBINSETST");
            spinner2Action = editor.getFormLayoutDataAction(
                    "insets", "right_inset", "", "GBINSETSR");
            spinner3Action = editor.getFormLayoutDataAction(
                    "insets", "bottom_inset", "", "GBINSETSB");
            spinner4Action = editor.getFormLayoutDataAction(
                    "insets", "left_inset", "", "GBINSETSL");
            spinner4.setVisible(true);
            hgButton.setVisible(false);
            vgButton.setVisible(false);
            hcButton.setVisible(false);
            vcButton.setVisible(false);
            nAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.NORTH), "", "GBNORTH");
            sAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.SOUTH), "", "GBSOUTH");
            wAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.WEST), "", "GBWEST");
            eAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.EAST), "", "GBEAST");
            nwAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.NORTHWEST), "", "GBNW");
            swAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.SOUTHWEST), "", "GBSW");
            neAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.NORTHEAST), "", "GBNE");
            seAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.SOUTHEAST), "", "GBSE");
            cenAction = editor.getFormLayoutDataAction("anchor",
                    new Integer(GridBagConstraints.CENTER), "", "GBHCEN");
            vfAction = editor.getFormLayoutDataAction("fill+",
                    new Integer(GridBagConstraints.VERTICAL), "", "GBVFILL+");
            vfButton.setToolTipText("Toggle vertical fill");
            hfAction = editor.getFormLayoutDataAction("fill+",
                    new Integer(GridBagConstraints.HORIZONTAL), "", "GBHFILL+");
            hfButton.setToolTipText("Toggle horizontal fill");
            
        } else if (sel.isSwing() && "Form".equals(plt)) {
            
            setButtonsVisible();
            label14.setText("Top Inset:");
            label15.setText("Right Inset:");
            label16.setText("Bottom Inset:");
            spinner1.min = 0;
            spinner2.min = 0;
            spinner3.min = 0;
            label5.setVisible(true);
            spinner1Action = editor.getFormLayoutDataAction(
                    "insets", "top_inset", "", "JGFINSETST");
            spinner2Action = editor.getFormLayoutDataAction(
                    "insets", "right_inset", "", "JGFINSETSR");
            spinner3Action = editor.getFormLayoutDataAction(
                    "insets", "bottom_inset", "", "JGFINSETSB");
            spinner4Action = editor.getFormLayoutDataAction(
                    "insets", "left_inset", "", "JGFINSETSL");
            spinner4.setVisible(true);
            hgButton.setVisible(false);
            vgButton.setVisible(false);
            nwButton.setVisible(false);
            neButton.setVisible(false);
            swButton.setVisible(false);
            seButton.setVisible(false);
            cenButton.setVisible(false);
            nAction = editor.getFormLayoutDataAction("vAlign",
                    CellConstraints.TOP, "", "JGFTOP");
            sAction = editor.getFormLayoutDataAction("vAlign",
                    CellConstraints.BOTTOM, "", "JGFBOT");
            wAction = editor.getFormLayoutDataAction("hAlign",
                    CellConstraints.LEFT, "", "JGFLEFT");
            eAction = editor.getFormLayoutDataAction("hAlign",
                    CellConstraints.RIGHT, "", "JGFRIGHT");
            vfAction = editor.getFormLayoutDataAction("vAlign",
                    CellConstraints.FILL, "", "JGFVFILL");
            vfButton.setToolTipText("Fill Vertically");
            hfAction = editor.getFormLayoutDataAction("hAlign",
                    CellConstraints.FILL, "", "JGFHFILL");
            hfButton.setToolTipText("Fill Horizontally");
            hcAction = editor.getFormLayoutDataAction("hAlign",
                    CellConstraints.CENTER, "", "JGFHCEN");
            vcAction = editor.getFormLayoutDataAction("vAlign",
                    CellConstraints.CENTER, "", "JGFVCEN");
        }
        
        butBG = nButton.getBackground();
        addMouseListener(nwButton, nwAction);
        addMouseListener(nButton, nAction);
        addMouseListener(hfButton, hfAction);
        addMouseListener(vgButton, vgAction);
        addMouseListener(neButton, neAction);
        addMouseListener(vfButton, vfAction);
        addMouseListener(wButton, wAction);
        addMouseListener(hcButton, hcAction);
        addMouseListener(eButton, eAction);
        addMouseListener(vcButton, vcAction);
        addMouseListener(hgButton, hgAction);
        addMouseListener(swButton, swAction);
        addMouseListener(sButton, sAction);
        addMouseListener(seButton, seAction);
        addMouseListener(seButton, seAction);
        addMouseListener(cenButton, cenAction);

        addModifyListener(spinner1, spinner1Action);
        addModifyListener(spinner2, spinner2Action);
        addModifyListener(spinner3, spinner3Action);
        addModifyListener(spinner4, spinner4Action);
        
        setBGColors();
        editor.removeSelectionChangedListener(selList);
        editor.addSelectionChangedListener(selList);
    }

    private boolean modifyingTextInternally = false;
    /**
     * @param text12
     * @param string
     * @param ldw
     */
    private void addModifyListener(final JigSpinner text, final FormLayoutDataAction act) {
        if (act == null)
            return;
        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if(modifyingTextInternally)
                    return;
                try {
                    int ival = new Integer(text.getText()).intValue();
                    Object val = act.getPropertyValue();
                    Object value = act.getValue();
                    Insets insets = null;
                    if(val instanceof InsetsPropertySource)
                        insets = ((InsetsPropertySource)val).getValue();
                    else if(val instanceof Insets)
                        insets = (Insets)val;
                    if(insets != null
                            && value instanceof String && ((String)value).indexOf("inset") > 0) {
                        if("top_inset".equals(value))
                            insets.top = ival;
                        else if("right_inset".equals(value))
                            insets.right = ival;
                        else if("bottom_inset".equals(value))
                            insets.bottom = ival;
                        else if("left_inset".equals(value))
                            insets.left = ival;
                        act.run(insets);
                    } else {
                        act.run(new Integer(text.getText()));
                    }
                } catch(Throwable t) {}
            }});
    }

    /**
     * @param vgButton2
     * @param vgAction2
     */
    private void addMouseListener(final CLabel lab,
            final FormLayoutDataAction act) {
        if (act == null)
            return;
        lab.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent evt) {
                act.run();
                setBGColors();
            }
        });
    }

    /**
     * 
     */
    private void setButtonsVisible() {
        nwButton.setVisible(true);
        nButton.setVisible(true);
        hfButton.setVisible(true);
        vgButton.setVisible(true);
        neButton.setVisible(true);
        vfButton.setVisible(true);
        wButton.setVisible(true);
        hcButton.setVisible(true);
        eButton.setVisible(true);
        vcButton.setVisible(true);
        hgButton.setVisible(true);
        swButton.setVisible(true);
        sButton.setVisible(true);
        seButton.setVisible(true);
        cenButton.setVisible(true);
}

    private void setBGColors() {
        setBGColor(nwButton, nwAction);
        setBGColor(nButton, nAction);
        setBGColor(hfButton, hfAction);
        setBGColor(vgButton, vgAction);
        setBGColor(neButton, neAction);
        setBGColor(vfButton, vfAction);
        setBGColor(wButton, wAction);
        setBGColor(hcButton, hcAction);
        setBGColor(eButton, eAction);
        setBGColor(vcButton, vcAction);
        setBGColor(hgButton, hgAction);
        setBGColor(swButton, swAction);
        setBGColor(sButton, sAction);
        setBGColor(seButton, seAction);
        setBGColor(cenButton, cenAction);
        setText(spinner1, spinner1Action);
        setText(spinner2, spinner2Action);
        setText(spinner3, spinner3Action);
        setText(spinner4, spinner4Action);
        FormComponent sel = editor.getSelectedComponent();
        if(sel != null) {
            try {
                getShell().setText(sel.getNameInCode());
                group1.setText(sel.getParentLayoutType()+" Alignment");
            } catch(Throwable t) {
                dispose();
            }
        }
    }

    private void setBGColor(Control but, FormLayoutDataAction act) {
        if(but == null || but.isDisposed())
            return;
        if (act != null && act.isOn())
            but.setBackground(ColorManager.getColor(0, 250, 0));
        else
            but.setBackground(butBG);
    }

    private void setText(JigSpinner text, FormLayoutDataAction act) {
        if(text == null || text.isDisposed())
            return;
        modifyingTextInternally = true;
        if (act != null) {
            Object val = act.getPropertyValue();
            Object value = act.getValue();
            Insets insets = null;
            if(val instanceof InsetsPropertySource)
                insets = ((InsetsPropertySource)val).getValue();
            else if(val instanceof Insets)
                insets = (Insets)val;
            if(insets != null &&
                    value instanceof String && ((String)value).indexOf("inset") > 0) {
                if("top_inset".equals(value))
                    val = insets.top+"";
                else if("right_inset".equals(value))
                    val = insets.right+"";
                else if("bottom_inset".equals(value))
                    val = insets.bottom+"";
                else if("left_inset".equals(value))
                    val = insets.left+"";
            }
            text.setText(val+"");
        } else {
            text.setText("");
        }
        modifyingTextInternally = false;
    }

    private void initGUI() {
        try {
            GridLayout thisLayout = new GridLayout();
            this.setLayout(thisLayout);
            this.setBackground(ColorManager.getColor(255, 255, 255));
            thisLayout.numColumns = 2;
            {
                group1 = new Group(this, SWT.NONE);
                GridLayout group1Layout = new GridLayout();
                group1Layout.numColumns = 5;
                group1.setLayout(group1Layout);
                GridData group1LData = new GridData();
                group1LData.horizontalAlignment = GridData.FILL;
                group1LData.grabExcessHorizontalSpace = true;
                group1LData.verticalAlignment = GridData.FILL;
                group1.setLayoutData(group1LData);
                group1.setText("Alignment");
                group1
                        .setBackground(ColorManager.getColor(255, 255,
                                255));
                {
                    nwButton = new CLabel(group1, SWT.NULL);
                    nwButton.setImage(ImageManager.getImage("align_NW.gif"));
                    nwButton.setToolTipText("Align North West");
                }
                {
                    label1 = new Label(group1, SWT.NONE);
                }
                {
                    nButton = new CLabel(group1, SWT.NULL);
                    GridData button2LData = new GridData();
                    button2LData.horizontalAlignment = GridData.CENTER;
                    nButton.setLayoutData(button2LData);
                    nButton.setImage(ImageManager.getImage("align_N.gif"));
                    nButton.setToolTipText("Align North");
                }
                {
                    vgButton = new CLabel(group1, SWT.NULL);
                    GridData button9LData = new GridData();
                    button9LData.horizontalAlignment = GridData.CENTER;
                    vgButton.setLayoutData(button9LData);
                    vgButton.setImage(ImageManager.getImage("align_VG.gif"));
                    vgButton.setToolTipText("Grab Vertical Space");
                }
                {
                    neButton = new CLabel(group1, SWT.NULL);
                    GridData button3LData = new GridData();
                    button3LData.horizontalAlignment = GridData.END;
                    neButton.setLayoutData(button3LData);
                    neButton.setImage(ImageManager.getImage("align_NE.gif"));
                    neButton.setToolTipText("Align North East");
                }
                {
                    label4 = new Label(group1, SWT.NONE);
                }
                {
                    label6 = new Label(group1, SWT.NONE);
                }
                {
                    vfButton = new CLabel(group1, SWT.NULL);
                    GridData button4LData = new GridData();
                    button4LData.horizontalAlignment = GridData.CENTER;
                    vfButton.setLayoutData(button4LData);
                    vfButton.setImage(ImageManager.getImage("align_VF.gif"));
                    vfButton.setToolTipText("Fill Vertically");
                }
                {
                    label2 = new Label(group1, SWT.NONE);
                }
                {
                    label3 = new Label(group1, SWT.NONE);
                }
                {
                    wButton = new CLabel(group1, SWT.NULL);
                    wButton.setImage(ImageManager.getImage("align_W.gif"));
                    wButton.setToolTipText("Align West");
                }
                {
                    hcButton = new CLabel(group1, SWT.NULL);
                    GridData button7LData = new GridData();
                    button7LData.horizontalAlignment = GridData.CENTER;
                    hcButton.setLayoutData(button7LData);
                    hcButton.setImage(ImageManager.getImage("align_HC.gif"));
                    hcButton.setToolTipText("Center Horizontally");
                }
                {
                    cenButton = new CLabel(group1, SWT.NONE);
                    GridData cLabel1LData = new GridData();
                    cLabel1LData.horizontalAlignment = GridData.CENTER;
                    cenButton.setLayoutData(cLabel1LData);
                    cenButton.setImage(ImageManager.getImage("align_CEN.gif"));
                    cenButton.setToolTipText("Align Center");
                }
                {
                    hfButton = new CLabel(group1, SWT.NULL);
                    GridData button6LData = new GridData();
                    button6LData.horizontalAlignment = GridData.CENTER;
                    hfButton.setLayoutData(button6LData);
                    hfButton.setImage(ImageManager.getImage("align_HF.gif"));
                    hfButton.setToolTipText("Fill Horizontally");
                }
                {
                    eButton = new CLabel(group1, SWT.NULL);
                    GridData button8LData = new GridData();
                    button8LData.horizontalAlignment = GridData.END;
                    eButton.setLayoutData(button8LData);
                    eButton.setImage(ImageManager.getImage("align_E.gif"));
                    eButton.setToolTipText("Align East");
                }
                {
                    label11 = new Label(group1, SWT.NONE);
                }
                {
                    label10 = new Label(group1, SWT.NONE);
                }
                {
                    vcButton = new CLabel(group1, SWT.NULL);
                    GridData button13LData = new GridData();
                    button13LData.horizontalAlignment = GridData.CENTER;
                    vcButton.setLayoutData(button13LData);
                    vcButton.setImage(ImageManager.getImage("align_VC.gif"));
                    vcButton.setToolTipText("Center Vertically");
                }
                {
                    label8 = new Label(group1, SWT.NONE);
                }
                {
                    hgButton = new CLabel(group1, SWT.NULL);
                    GridData button14LData = new GridData();
                    button14LData.horizontalAlignment = GridData.END;
                    hgButton.setLayoutData(button14LData);
                    hgButton.setImage(ImageManager.getImage("align_HG.gif"));
                    hgButton.setToolTipText("Grab Horizontal Space");
                }
                {
                    swButton = new CLabel(group1, SWT.NULL);
                    swButton.setImage(ImageManager.getImage("align_SW.gif"));
                    swButton.setToolTipText("Align South West");
                }
                {
                    label13 = new Label(group1, SWT.NONE);
                }
                {
                    sButton = new CLabel(group1, SWT.NULL);
                    GridData button11LData = new GridData();
                    button11LData.horizontalAlignment = GridData.CENTER;
                    sButton.setLayoutData(button11LData);
                    sButton.setImage(ImageManager.getImage("align_S.gif"));
                    sButton.setToolTipText("Align South");
                }
                {
                    label12 = new Label(group1, SWT.NONE);
                }
                {
                    seButton = new CLabel(group1, SWT.NULL);
                    GridData button10LData = new GridData();
                    button10LData.horizontalAlignment = GridData.END;
                    seButton.setLayoutData(button10LData);
                    seButton.setImage(ImageManager.getImage("align_SE.gif"));
                    seButton.setToolTipText("Align South East");
                }
            }
            {
                group2 = new Group(this, SWT.NONE);
                GridLayout group2Layout = new GridLayout();
                group2.setLayout(group2Layout);
                GridData group2LData = new GridData();
                group2LData.grabExcessHorizontalSpace = true;
                group2LData.horizontalAlignment = GridData.FILL;
                group2LData.grabExcessVerticalSpace = true;
                group2LData.verticalAlignment = GridData.FILL;
                group2LData.verticalSpan = 2;
                group2.setLayoutData(group2LData);
                group2.setText("Other");
                group2
                        .setBackground(ColorManager.getColor(255, 255,
                                255));
                {
                    label14 = new Label(group2, SWT.NONE);
                    label14.setText("Horiz Span:");
                    label14.setBackground(ColorManager.getColor(255, 255,
                            255));
                }
                {
                    GridData text1LData = new GridData();
                    text1LData.widthHint = 59;
                    spinner1 = new JigSpinner(group2, SWT.BORDER);
                    spinner1.setLayoutData(text1LData);
                }
                {
                    label15 = new Label(group2, SWT.NONE);
                    label15.setText("Vert Span:");
                    label15.setBackground(ColorManager.getColor(255, 255,
                            255));
                }
                {
                    GridData text2LData = new GridData();
                    text2LData.widthHint = 59;
                    spinner2 = new JigSpinner(group2, SWT.BORDER);
                    spinner2.setLayoutData(text2LData);
                }
                {
                    label16 = new Label(group2, SWT.NONE);
                    label16.setText("Horiz Indent:");
                    label16.setBackground(ColorManager.getColor(255, 255,
                            255));
                }
                {
                    spinner3 = new JigSpinner(group2, SWT.BORDER);
                    GridData text4LData = new GridData();
                    text4LData.widthHint = 59;
                    spinner3.setLayoutData(text4LData);
                }
                {
                    label5 = new Label(group2, SWT.NONE);
                    label5.setText("Left Inset:");
                    label5.setBackground(ColorManager.getColor(255,255,255));
                }
                {
                    GridData jigSpinner1LData = new GridData();
                    jigSpinner1LData.widthHint = 59;
                    spinner4 = new JigSpinner(group2, SWT.BORDER);
                    spinner4.setLayoutData(jigSpinner1LData);
                }
            }
            {
                button1 = new Button(this, SWT.PUSH | SWT.CENTER);
                GridData button1LData = new GridData();
                button1LData.horizontalAlignment = GridData.FILL;
                button1.setLayoutData(button1LData);
                button1.setText("Close");
                button1.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        getShell().dispose();
                    }
                });
            }
            this.layout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dispose() {
        try {
            if(editor != null)
                editor.removeSelectionChangedListener(selList);
            editor = null;
            if(!isDisposed())
                super.dispose();
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
}