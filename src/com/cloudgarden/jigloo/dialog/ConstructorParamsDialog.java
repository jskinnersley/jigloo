package com.cloudgarden.jigloo.dialog;

import java.lang.reflect.Constructor;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.eval.ConstructorManager;
import com.cloudgarden.jigloo.properties.CGPropertySheetPage;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.util.ConversionUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ConstructorParamsDialog extends org.eclipse.swt.widgets.Dialog implements IPropertySource {

	private Shell dialogShell;
	private Label label1;
	private Button cancelButton;
	private Button okButton;
	private Group parameterGroup;
	private CCombo classCombo;
	private IWorkbenchPartSite site;
	private Class newClass;
	private Constructor[] constructors;
	private CGPropertySheetPage propPage;
	private IPropertyDescriptor[] propDescs = new IPropertyDescriptor[0];
	private FormComponent comp;
	private Class[] paramTypes;
	private Object[] parameters;
	private Constructor constructor;
	private String constructorDesc;
	
	public Constructor getConstructor() {
		return constructor;
	}
	
	public String getConstructorDesc() {
		return constructorDesc;
	}

	public ConstructorParamsDialog(Shell parent, int style, IWorkbenchPartSite site, FormComponent comp) {
		super(parent, style);
		this.site = site;
		this.comp = comp;
		this.newClass = comp.getMainClass();
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.setText("Choose constructor and set parameters");
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.width = 74;
				okButtonLData.right =  new FormAttachment(1000, 1000, -101);
				okButtonLData.bottom =  new FormAttachment(1000, 1000, -9);
				okButton.setLayoutData(okButtonLData);
				okButton.setText("OK");
				okButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						okButtonWidgetSelected(evt);
					}
				});
			}
			{
				parameterGroup = new Group(dialogShell, SWT.NONE);
				FillLayout group1Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				parameterGroup.setLayout(group1Layout);
				FormData group1LData = new FormData();
				group1LData.left =  new FormAttachment(0, 1000, 12);
				group1LData.top =  new FormAttachment(0, 1000, 39);
				group1LData.width = 400;
				group1LData.height = 200;
				group1LData.bottom =  new FormAttachment(1000, 1000, -44);
				group1LData.right =  new FormAttachment(1000, 1000, -12);
				parameterGroup.setLayoutData(group1LData);
				parameterGroup.setText("Parameters");
			}
			{
				classCombo = new CCombo(dialogShell, SWT.BORDER);
				FormData cCombo1LData = new FormData();
				cCombo1LData.left =  new FormAttachment(0, 1000, 79);
				cCombo1LData.top =  new FormAttachment(0, 1000, 12);
				cCombo1LData.right =  new FormAttachment(1000, 1000, -12);
				classCombo.setLayoutData(cCombo1LData);
				classCombo.setText("<choose>");
				classCombo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						classComboWidgetSelected(evt);
					}
				});
			}
			{
				label1 = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 14);
				label1.setLayoutData(label1LData);
				label1.setText("Constructor:");
			}
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				cancelButton.setText("Cancel");
				FormData button1LData = new FormData();
				button1LData.width = 74;
				button1LData.right =  new FormAttachment(1000, 1000, -12);
				button1LData.bottom =  new FormAttachment(1000, 1000, -9);
				cancelButton.setLayoutData(button1LData);
				cancelButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						cancelButtonWidgetSelected(evt);
					}
				});
			}
			initForClass();
			dialogShell.layout();
			dialogShell.pack();			
			JiglooUtils.centerShell(dialogShell);
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				try {
					if (!display.readAndDispatch())
						display.sleep();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initForClass() {
		classCombo.removeAll();
		constructors = newClass.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Constructor con = constructors[i];
			String str = ConstructorManager.getDescription(con);
			classCombo.add(str);
		}
		propPage = new CGPropertySheetPage("Params:", parameterGroup, site, false, false);
		parameterGroup.layout();
		propPage.selectionChanged(site.getPage().getActivePart(), new StructuredSelection(this));
		classCombo.select(0);
		classComboWidgetSelected(null);
	}

	private void okButtonWidgetSelected(SelectionEvent evt) {
		dialogShell.dispose();
	}
	
	private void cancelButtonWidgetSelected(SelectionEvent evt) {
		parameters = null;
		dialogShell.dispose();
	}
	
	public Object[] getParameters() {
		return parameters;
	}

	private void classComboWidgetSelected(SelectionEvent evt) {
		constructorDesc = classCombo.getItem(classCombo.getSelectionIndex());
		constructor = constructors[classCombo.getSelectionIndex()];
		paramTypes = constructor.getParameterTypes();
		propDescs = new IPropertyDescriptor[paramTypes.length];
		parameters = new Object[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			Class type = paramTypes[i];
			String paramName;
//			paramName = JiglooUtils.deCapitalize(JiglooUtils.getUnqualifiedName(type.getName()));
			paramName = "param";
			if(i < 10)
				paramName += "0";
			paramName += i;
			type = ConversionUtils.convertPrimitiveClassToObjectClass(type);
			propDescs[i] = PropertySourceFactory.getPropertyDescriptor(paramName, null, type, comp);
			parameters[i] = getInitialPropertyValue(paramName);
		}
		propPage.selectionChanged(site.getPage().getActivePart(), new StructuredSelection(this));
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propDescs;
	}

	private int getParamIndex(String name) {
		return Integer.parseInt(name.substring(name.length()-2));
	}
	
	public Object getPropertyValue(Object id) {
		return parameters[getParamIndex((String) id)];
	}
	
	private Object getInitialPropertyValue(Object id) {
		int paramNum = getParamIndex((String)id);
		Class cls = paramTypes[paramNum];
		cls = ConversionUtils.convertPrimitiveClassToObjectClass(cls);
		if(cls.equals(Integer.class))
			return new Integer(0);
		if(cls.equals(Double.class))
			return new Double(0);
		if(cls.equals(Float.class))
			return new Float(0);
		if(cls.equals(Boolean.class))
			return Boolean.TRUE;
		if(cls.equals(String.class))
			return "";
		return null;
	}

	public boolean isPropertySet(Object id) {
		return true;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
		parameters[getParamIndex((String)id)] = value;
	}

}
