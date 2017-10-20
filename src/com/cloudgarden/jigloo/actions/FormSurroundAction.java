/*
 * Created on Mar 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.actions;

import java.awt.Container;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.resource.CursorManager;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormSurroundAction extends FormAction {
	private Class clazz;
	private boolean isSwing;
	
    public FormSurroundAction(FormEditor editor, Class clazz) {
        super(editor);
        this.clazz = clazz;
        setText(clazz.getName());
        isSwing = ClassUtils.isSwing(clazz);
		ImageDescriptor imd = ImageManager.getImageDescForClass(clazz,	0);
		if(imd == null)
		    imd = ImageManager.getJavaBeanImgDesc();
		if (imd != null)
			setImageDescriptor(imd);
    }
    
    public void run() {
        try {
            
            editor.getSelectedComponents().remove(editor.getRootComponent());
            
        	//need to clone selection here because moveTo calls moveToParent which calls
        	// editor.removeComponent which removes the element from the selection
            Vector sels = (Vector) editor.getSelectedComponents().clone();
            if (sels.size() == 0)
                return;
            editor.setCursor(CursorManager.getCursor(SWT.CURSOR_WAIT));
            Rectangle bounds = JiglooUtils.getBoundingRectangle(sels);
            editor.pauseUpdate(true);
            FormComponent sel = editor.getSelectedComponent(0);
            FormComponent par = sel.getParent();
            int pos = sel.getNonInheritedIndexInParent();
            FormComponent newPar = FormComponent.newFormComponent(sel.getParent(), clazz.getName(), null, false, null);
            newPar.setExistsInCode(true);
			newPar.setInMainTree(true);
            if (newPar.hasProperty("text"))
            	newPar.setPropertyValueSimple("text", newPar.getNameInCode());
            
            if(!par.usesGroupLayout()) 
            	newPar.setBounds(bounds, true);
            newPar.addToCode();
			
            newPar.moveTo(pos);
            
            LayoutDataWrapper ldw = sel.getLayoutDataWrapper(false);
            if(ldw != null) {
            	ldw = ldw.getCopy(newPar);
            	ldw.setName(null);
            	newPar.setLayoutDataWrapper(ldw);
            	newPar.setSetProperty("layoutData");
            	ldw.repairInCode();
            }
            
            LayoutWrapper parLW = par.getLayoutWrapper();
            LayoutWrapper newParLW = null;
            
            boolean canSetLayout = 
                (!isSwing && !newPar.usesCustomLayout())
                || (isSwing && newPar.isA(JPanel.class));
            if (canSetLayout) {
                if(isSwing && par.usesGroupLayout()) {
                	newParLW = new LayoutWrapper(newPar, GroupLayout.class, true);
                	newParLW.setSet(true);
                	newPar.setLayoutWrapper(newParLW);
                } else if(!isSwing) {
                	newParLW = new LayoutWrapper(newPar, GridLayout.class, false);
                	newParLW.setSet(true);
                	newPar.setLayoutWrapper(newParLW);
                }
            }
            
            if(par.usesGroupLayout()) {
            	//need to set boundsToRoot because this is what LayoutGroup.getPreferredSize looks at
            	newPar.setBoundsToRoot(bounds);
            	Vector children = (Vector) par.getChildren().clone();
            	for(int i=0; i<sels.size(); i++)
            	    children.remove(sels.elementAt(i));
            	LayoutGroup lg = parLW.getHGroup().getCompactGroup(children, true, null, null, null);
            	parLW.setHGroup(lg);
            	lg = parLW.getVGroup().getCompactGroup(children, true, null, null, null);
            	parLW.setVGroup(lg);
            	parLW.refreshGroupLayout();
            	parLW.repairInCode(true);
            	if(newParLW != null) {
            	    newParLW.setHGroup(parLW.getHGroup().getCompactGroup(sels, false, null, null, null));
            	    newParLW.setVGroup(parLW.getVGroup().getCompactGroup(sels, false, null, null, null));
            	}
            }
            
        	if(newParLW != null)
        	    newParLW.repairInCode(true);
        	
			FormComponent parent = newPar;
			
            if(!isSwing) {
            	Class itemClass = null;
            	if(clazz.equals(CTabFolder.class)) {
            		itemClass = CTabItem.class;
            	} else if(clazz.equals(TabFolder.class)) {
            		itemClass = TabItem.class;
            	}
            	if(itemClass != null) {
            		FormComponent ti = FormComponent.newFormComponent(newPar, itemClass.getName(), null, false, null);
            		ti.setExistsInCode(true);
            		ti.setInMainTree(true);
            		newPar.setPropertyValueSimple("selection", new Integer(0));
            		ti.setPropertyValueInternal("text", ti.getNameInCode(), true);
            		ti.addToCode();
            		parent = ti;
            	}
            }
           
            newPar.repairParentConnectionInCode();
            for(int i=0; i<sels.size(); i++) {
            	FormComponent child = (FormComponent)sels.elementAt(i);
            	child.moveToParent(parent, i, false);
            	if (clazz.equals(JTabbedPane.class)) {
            		child.setTabTitle(child.getNameInCode());
            		child.setPropertyValueCode("LAYOUT_CONSTRAINT", null);
            	}
            	child.repairParentConnectionInCode();
            }
            if(isSwing) {
            	newPar.populateComponents((Container)newPar.getParent().getComponent(), editor);
            } else {
            	newPar.populateControls(null, editor, false);
            }

			newPar.updateUI();
			editor.flushActions();
			editor.pauseUpdate(false);
			editor.setDirtyAndUpdate(true);
			
			editor.setSelectedComponents(sels);
			editor.getSelectedComponent().bringToFront(false);
			
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
}
