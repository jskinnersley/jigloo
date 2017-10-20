package com.cloudgarden.jigloo.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.jiglooPlugin;
public class FormViewComposite extends org.eclipse.swt.widgets.Composite {
	Composite propsComposite;
	Composite advPropsComposite;
	Composite layoutComposite;
	Composite eventComposite;
	CTabItem eventTabItem;
	CTabItem layoutTabItem;
	CTabItem propTabItem;
	CTabItem advPropTabItem;
	CTabFolder cTabFolder;
	SashForm sashForm;

	public FormViewComposite(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	public Composite getPropertiesTab() {
		return propsComposite;
	}

	public Composite getAdvPropertiesTab() {
		return advPropsComposite;
	}

	public Composite getLayoutTab() {
		return layoutComposite;
	}

	public Composite getEventsTab() {
		return eventComposite;
	}
	public SashForm getSashForm() {
		return sashForm;
	}

	public void showTabPane() {
		StackLayout layout = (StackLayout) getLayout();
		layout.topControl = cTabFolder;
		layout();
	}

	public void showSashForm() {
		StackLayout layout = (StackLayout) getLayout();
		layout.topControl = sashForm;
		layout();
	}

	/**
	* Auto-generated code - any changes you make will disappear!!!
	*/
	public void initGUI() {
		try {
			preInitGUI();
			sashForm = new SashForm(this, 0);
			cTabFolder = new CTabFolder(this, 0);
			propTabItem = new CTabItem(cTabFolder, 0);
			propsComposite = new Composite(cTabFolder, 0);
			if(jiglooPlugin.showAdvancedProperties()) {
				advPropTabItem = new CTabItem(cTabFolder, 0);
				advPropsComposite = new Composite(cTabFolder, 0);
			}
			layoutTabItem = new CTabItem(cTabFolder, 0);
			layoutComposite = new Composite(cTabFolder, 0);
			eventTabItem = new CTabItem(cTabFolder, 0);
			eventComposite = new Composite(cTabFolder, 0);
			this.setSize(new org.eclipse.swt.graphics.Point(336, 162));
			this.setBounds(
				new org.eclipse.swt.graphics.Rectangle(0, 0, 336, 162));
			sashForm.setSize(new org.eclipse.swt.graphics.Point(60, 30));
			sashForm.setVisible(true);
			sashForm.setLayout(null);
			sashForm.layout();
			cTabFolder.setSize(new org.eclipse.swt.graphics.Point(60, 10));
			cTabFolder.setVisible(true);
			
			propTabItem.setText("Properties");
			propsComposite.setVisible(true);
			propTabItem.setControl(propsComposite);
			FillLayout propsCompositeLayout = new FillLayout(256);
			propsComposite.setLayout(propsCompositeLayout);
			propsCompositeLayout.type = 256;
			propsComposite.layout();
			
			if(advPropTabItem != null) {
				advPropTabItem.setText("Advanced");
				advPropsComposite.setVisible(true);
				advPropTabItem.setControl(advPropsComposite);
				FillLayout apropsCompositeLayout = new FillLayout(256);
				advPropsComposite.setLayout(apropsCompositeLayout);
				apropsCompositeLayout.type = 256;
				advPropsComposite.layout();
			}
			
			layoutTabItem.setText("Layout");
			layoutComposite.setVisible(true);
			layoutTabItem.setControl(layoutComposite);
			FillLayout layoutCompositeLayout = new FillLayout(256);
			layoutComposite.setLayout(layoutCompositeLayout);
			layoutCompositeLayout.type = 256;
			layoutComposite.layout();
			eventTabItem.setText("Events");
			eventComposite.setVisible(true);
			eventTabItem.setControl(eventComposite);
			FillLayout eventCompositeLayout = new FillLayout(256);
			eventComposite.setLayout(eventCompositeLayout);
			eventCompositeLayout.type = 256;
			eventComposite.layout();
			cTabFolder.setLayout(null);
			cTabFolder.layout();
			cTabFolder.setSelection(0);
			StackLayout thisLayout = new StackLayout();
			this.setLayout(thisLayout);
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.topControl = cTabFolder;
			this.layout();
			postInitGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Auto-generated main method */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI() {
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			FormViewComposite inst = new FormViewComposite(shell, SWT.NULL);
			shell.setLayout(new org.eclipse.swt.layout.FillLayout());
			Point size = inst.getSize();
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			if (shell.getMenuBar() != null)
				shellBounds.height -= 22;
			shell.setSize(shellBounds.width, shellBounds.height);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* Add your pre-init code in here
	*/
	public void preInitGUI() {}
	/**
	* Add your post-init code in here
	*/
	public void postInitGUI() {}
}
