package com.cloudgarden.jigloo.harness;

import java.awt.Window;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.editors.HitResult;
import com.cloudgarden.jigloo.eval.JavaCodeParser;

public interface IHarness {

	public FormComponent getRootComponent(FormComponent root);

	public Object handleMethod(JavaCodeParser javaCodeParser,
			FormComponent parFC, String methodName, Class methodClass, List args);

	public Window getFrame();

	public void populateRoot(FormEditor formEditor);

	public void populateFormComponents(FormComponent root, Object object);

	public void dispose();

	public void clear();

	public void updateFiles();

	public void doSave(IProgressMonitor monitor);

	public void bringToFront(FormComponent formComponent, boolean canBecomeRoot);

	public FormComponent newFormComponent();

	public int getClassID();

	public HitResult getComponentAt(FormComponent fc, int x, int y,
			boolean strict);

	public void setEditor(FormEditor editor);

}
