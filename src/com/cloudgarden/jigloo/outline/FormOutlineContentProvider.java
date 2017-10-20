/*
 * Created on Jul 29, 2003
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.outline;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.JiglooUtils;

public class FormOutlineContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	private TreeParent invisibleRoot;
	private TreeViewer viewer;
	boolean extCompsPrepped = false;
	boolean inited = false;

	public FormOutlineContentProvider(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public TreeParent getInvisibleRoot() {
		return invisibleRoot;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
//	    System.out.println("Input changed");
	}

	public void dispose() {
		if(invisibleRoot != null)
			invisibleRoot.dispose();
		invisibleRoot = null;
//		if(viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed())
//			viewer.getControl().dispose();
		viewer = null;
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(ResourcesPlugin.getWorkspace())) {
			if (invisibleRoot == null)
				initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent) parent).hasChildren();
		return false;
	}

	public void initialize() {
		//	    extCompsPrepped = false;
		invisibleRoot = new TreeParent((FormComponent) null);
		if (inited)
			return;
		inited = true;
		viewer.setLabelProvider(new FormOutlineLabelProvider());
		viewer.setSorter(null); //so the nodes appear in their
											 // correct order
	}

	private TreeObject findTreeObject(FormComponent comp, TreeObject parent) {
		if (comp == null)
			return null;

		if (parent == null)
			return null;

		if (parent.getFormComponent() != null
				&& comp.getName().equals(parent.getFormComponent().getName())) {
			//the FormComponent held by the selected TreeParent might have been disposed,
			//so set it to the comp we are being passed in
			parent.comp = comp;
			return parent;
		}
		if (!(parent instanceof TreeParent))
			return null;
		TreeObject[] children = ((TreeParent) parent).getChildren();
		TreeObject obj = null;
		for (int i = 0; i < children.length; i++) {
			TreeObject child = children[i];
			TreeObject obj2 = findTreeObject(comp, children[i]);
			if (obj2 != null) {
				return obj2;
			}
		}
		if (obj != null)
			return obj;
		return null;
	}

	public void addChildComponent(FormComponent comp, FormComponent parent) {

		if (true)
			return;

		//		TreeObject obj;
		//		obj = findTreeObject(parent, invisibleRoot);
		//		if (obj instanceof TreeParent) {
		//			TreeParent tp = (TreeParent) obj;
		//			TreeObject treeObj = comp.getTreeObject();
		//			tp.addChild(treeObj);
		//		}
		//		if (viewer.getControl() != null && !viewer.getControl().isDisposed())
		// {
		//            viewer.refresh();
		//        }
	}

	public void refreshTreeNode(FormComponent fc) {
		if (true)
			return;

		//		TreeObject obj = findTreeObject(fc, invisibleRoot);
		//		if(obj != null)
		//			viewer.refresh(obj, true);
	}

	public void removeChildComponent(FormComponent comp, FormComponent parent) {

		if (true)
			return;

		//this should work, but haven't tested it!
		//		TreeObject par = parent.getTreeObject();
		//		if (par == null)
		//			par = findTreeObject(parent, invisibleRoot);
		//		TreeObject obj = comp.getTreeObject();
		//		if (obj == null) {
		//			obj = findTreeObject(comp, invisibleRoot);
		//		} else {
		//			if(obj.getParent() != null)
		//				par = obj.getParent();
		//		}

		//		TreeObject par = findTreeObject(parent, invisibleRoot);
		//		TreeObject obj = findTreeObject(comp, invisibleRoot);
		//		if (obj == null) {
		//			//new Exception().printStackTrace();
		//			if(jiglooPlugin.DEBUG_EXTRA)
		//				System.err.println("***TreeObject is null for " + comp);
		//		}
		//		if (par instanceof TreeParent) {
		//			TreeParent tp = (TreeParent) par;
		//			tp.removeChild(obj);
		//			viewer.refresh(tp);
		//		}
	}

	public void setSelection(ISelection selection) {
		viewer.setSelection(selection, true);
	}

	public void setSelection(Vector comps) {
		if(viewer == null)
			return;
		ISelection sel = viewer.getSelection();
		//System.out.println("FOCP SET SELECTION "+comps);
		Vector sels1 = JiglooUtils.selectionToVector(sel, true);
		if (!JiglooUtils.vectorsDiffer(comps, sels1)) {
			return;
		}
		Vector sels = new Vector();
		FormComponent comp;
		TreeObject ob = null;
		//System.out.println("FOCP SET SELECTION (2) "+comps);

		//don't know why, but this allows an element in "Extra components" to
		// be expanded
		TreeObject ext = invisibleRoot.getChild(0);
		if (!extCompsPrepped && ext != null && !viewer.getExpandedState(ext)) {
			viewer.expandToLevel(ext, 1);
			viewer.collapseToLevel(ext, 1);
			extCompsPrepped = true;
		}

		for (int i = 0; i < comps.size(); i++) {
			comp = (FormComponent) comps.elementAt(i);
			ob = findTreeObject(comp, invisibleRoot);
			//			System.out.println("FOCP SET SELECTION "+comp+", "+ob);

			if (ob != null) {
				sels.add(ob);
			}
		}
		if (viewer.getControl() != null && !viewer.getControl().isDisposed()) {
			viewer.setSelection(new StructuredSelection(sels), true);
			if (ob != null)
				viewer.reveal(ob);
		}
	}

	public Vector getExpanded() {
		Object[] sel = viewer.getExpandedElements();
		if (sel == null)
			return null;
		Vector sels = new Vector();
		for (int i = 0; i < sel.length; i++)
			sels.add(((TreeObject) sel[i]).getFormComponent());
		return sels;
	}

	public void setExpanded(Vector sels) {
		for (int i = 0; i < sels.size(); i++) {
			FormComponent fc = (FormComponent) sels.elementAt(i);
			TreeObject to = findTreeObject(fc, invisibleRoot);
			if (to != null)
				viewer.setExpandedState(to, true);
		}
	}

	private void fillChildNodes(FormComponent comp, TreeParent parent, int showInherited) {

	    if (comp == null)
			return;

		parent.removeAll();
		
		Object action = comp.getPropertyValue("action");
		if(action instanceof FormComponent) {
		    parent.addChild(new TreeParent((FormComponent)action));
		}		

		if(!comp.isContainer())
		    return;

		TreeObject node;
		if (comp.getMenuBar() != null) {
			node = comp.getMenuBar().getTreeObject();
			parent.addChild(node);
			fillChildNodes(comp.getMenuBar(), (TreeParent) node, showInherited);
		}
		
		Vector children = comp.getChildren();
		for (int i = 0; i < children.size(); i++) {
			FormComponent fc = (FormComponent) children.elementAt(i);
			if (fc.isInherited()) {
				if (showInherited == 0)
					continue;
				if (showInherited == 1 && !fc.isInheritedDeclared())
					continue;
			}
			String name = (String) fc.getDisplayName();
			if (name == null)
				name = "UNKNOWN";
			node = fc.getTreeObject();

			parent.addChild(node);
			if (!fc.equals(comp))
				fillChildNodes(fc, (TreeParent) node, showInherited);
		}
	}

	public void reload(FormEditor editor, Vector selectedComps,
			int showInherited) {

		if (viewer == null || viewer.getControl() == null
				|| viewer.getControl().isDisposed())
			return;

		//		System.err.println("RELOAD OUTLINE");

		boolean test = true;

		TreeParent oldRoot = null;
		if (test) {
			invisibleRoot.removeAll();
			oldRoot = invisibleRoot;
		}

		Vector expanded = getExpanded();
		initialize();
		TreeParent extraRoot = new TreeParent(FormEditor.EXTRA_COMPONENT_LABEL);
		invisibleRoot.addChild(extraRoot);

		TreeParent nvRoot = new TreeParent(FormEditor.NON_VISUAL_LABEL);
		invisibleRoot.addChild(nvRoot);

		//        if (editor.getRootComponent() != null) {
		//            TreeParent rootNode = (TreeParent)
		// editor.getRootComponent().getTreeObject();
		//            invisibleRoot.addChild(rootNode);
		//            fillChildNodes(editor.getRootComponent(), rootNode, showInherited);
		//        }

		FormComponent ecr = editor.getExtraCompRoot();
		if (ecr != null) {
			sortChildren(ecr.getChildren(), false);
			for (int i = 0; i < ecr.getChildCount(); i++) {
				FormComponent fc = ecr.getChildAt(i);
				if (fc != null) {
					if(fc.isMethodReturnValue())
						continue;

					if (fc.isMenuComponent()) {
						extraRoot.addChild(fc.getTreeObject());
					} else {
						invisibleRoot.addChild(fc.getTreeObject());
					}

					fillChildNodes(fc, (TreeParent) fc.getTreeObject(),
							showInherited);
				}
			}
		}

		FormComponent nvr = editor.getNonVisualRoot();
		if (nvr != null) {
			sortChildren(nvr.getChildren(), true);
			for (int i = 0; i < nvr.getChildCount(); i++) {
				FormComponent fc = nvr.getChildAt(i);
				if(fc.isMethodReturnValue())
					continue;
				if (fc.isVisual()) {
					extraRoot.addChild(fc.getTreeObject());
				} else {
					nvRoot.addChild(fc.getTreeObject());
				}
				fillChildNodes(fc, (TreeParent) fc.getTreeObject(),
						showInherited);
			}
		}

		if (!test) {
			viewer.setInput(invisibleRoot);
		} else {
			Object ip = viewer.getInput();
			if (!oldRoot.equals(ip))
				viewer.setInput(oldRoot);
			oldRoot.removeAll();
			for (int i = 0; i < invisibleRoot.getChildCount(); i++) {
				oldRoot.addChild(invisibleRoot.getChild(i));
			}
			invisibleRoot.removeAll();
			invisibleRoot = oldRoot;

			viewer.refresh(true);
		}

		if (selectedComps != null)
			setSelection(selectedComps);
		if (expanded != null)
			setExpanded(expanded);

	}

	public void sortChildren(Vector components, final boolean className) {
		if (components == null)
			return;
		Collections.sort(components, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (o1 == null || o2 == null)
					return 0;
				if (o1 instanceof FormComponent && o2 instanceof FormComponent) {
					FormComponent fc1 = (FormComponent) o1;
					FormComponent fc2 = (FormComponent) o2;
					//v4.0M2 - want "this" to appear at top of list
					if ("this".equals(fc1.getName()))
						return -1;
					if ("this".equals(fc2.getName()))
						return 1;
					if (className) {
					    o1 = JiglooUtils.getUnqualifiedName(fc1.getClassName());
					    o2 = JiglooUtils.getUnqualifiedName(fc2.getClassName());
					} else {
					    o1 = fc1.getNameInCode();
					    o2 = fc2.getNameInCode();
					}
				}
				return o1.toString().compareTo(o2.toString());
			}
		});
	}

}