/*
 * Created on May 1, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.jface;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.images.ImageManager;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StubAppWindow extends ApplicationWindow {

    public StubAppWindow(Shell parentShell) {
        super(parentShell);
    }

    Composite c = null;
    Composite mainComp = null;

    public Control getContents() {
        return mainComp;
    }

    public Composite getMainComp() {
        return c;
    }

    public void createAppContents(Composite shell) {
        shell = new OrderableComposite(shell, SWT.NULL);
        mainComp = shell;
        Layout layout = getLayout();
        //    	layout= new GridLayout(2,true);
        if (layout != null)
            shell.setLayout(layout);

        addMenuBar();
        boolean makeCoolBar = true;
        if (makeCoolBar)
            addCoolBar(SWT.FLAT | SWT.WRAP);
        else
            addToolBar(SWT.FLAT | SWT.WRAP);
        addStatusLine();

        //		if (menuBarManager != null) {
        //			menuBarManager.updateAll(true);
        //			shell.setMenuBar(menuBarManager.createMenuBar((Decorations)shell));
        //		}

        if (showTopSeperator()) //$NON-NLS-1$
            seperator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);

        // will create either a cool bar or a tool bar
        if (makeCoolBar)
            createCoolBarControl(shell);
        else
            createToolBarControl(shell);
        _createStatusLine(shell);

        createContents(shell);

        shell.layout();

        // JiglooUtils.displayBranch(shell);

    }

    protected void _createStatusLine(Composite shell) {
        if (getStatusLineManager() != null) {
            getStatusLineManager().createControl(shell, SWT.NONE);
        }
    }

    public Control getStatusLineControl() {
        return getStatusLineManager().getControl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        c = new OrderableComposite(parent, SWT.NULL);
        return c;
    }

    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();
        MenuManager fileMenu = new MenuManager("&File");
        fileMenu.add(_changeColorAction);
        fileMenu.add(_exitAction);
        menuManager.add(fileMenu);
        return menuManager;
    }

    protected ToolBarManager createToolBarManager(int style) {
        ToolBarManager toolBarManager = new ToolBarManager(style);
        toolBarManager.add(_exitAction);
        toolBarManager.add(_changeColorAction);
        return toolBarManager;
    }

    protected CoolBarManager createCoolBarManager(int style) {
        CoolBarManager coolBarManager = new CoolBarManager(style);
        coolBarManager.add(createToolBarManager(style));
        return coolBarManager;
    }

    private ExitAction _exitAction = new ExitAction(this);

    private class ExitAction extends Action {
        ApplicationWindow _window;

        public ExitAction(ApplicationWindow window) {
            setText("E&xit@Ctrl+X");
            setImageDescriptor(ImageDescriptor.createFromFile(ImageManager.class, "table_obj.gif"));
        }

        public void run() {
        }
    }

    private ChangeColorAction _changeColorAction = new ChangeColorAction();

    private class ChangeColorAction extends Action {

        public ChangeColorAction() {
            setText("Change C&olor@Alt+C");
            setImageDescriptor(ImageDescriptor.createFromFile(ImageManager.class, "panel_obj.gif"));
        }

        public void run() {
            setStatus("Hello");
        }
    }

    private boolean showCoolBar = true;
    
    public void showCoolBar(boolean show) {
        if(!show && getCoolBarControl() !=null)
            getCoolBarControl().setVisible(false);
        showCoolBar = show;
        if(show && getCoolBarControl() !=null)
            getCoolBarControl().setVisible(true);
        ((Composite) getContents()).layout();
    }
    
    protected Control getCoolBarControl() {
        if(!showCoolBar)
            return null;
        return super.getCoolBarControl();
    }

    public CoolBarManager getCoolBarManager() {
        if(!showCoolBar)
            return null;
        return super.getCoolBarManager();
    }
}