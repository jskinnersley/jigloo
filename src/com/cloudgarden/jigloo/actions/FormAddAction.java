/*
 * Created on Jun 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.actions;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.dialog.NewComponentDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.groupLayoutSupport.SnapTo;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.properties.descriptors.EventPropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.FieldManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.util.SwingHelper;
import com.cloudgarden.jigloo.wrappers.EventWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.JLayerWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutDataWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;
import com.cloudgarden.layout.AnchorConstraint;
import com.jgoodies.forms.layout.CellConstraints;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FormAddAction extends ImageAction {
	private FormComponent parentComp;
	private int style;
	private static LayoutWrapper defaultSWTLayout = null;
	private static LayoutWrapper defaultSwingLayout = null;
	private static HashMap defaultSWTLayoutCons = new HashMap();
	private static HashMap defaultSwingLayoutCons = new HashMap();
	private FormComponent addedComponent;
	private boolean customClass = false;
	private boolean classUndefined = false;
	private IEditorSite site;
	FormComponent actionParent = null;

	public FormAddAction(Object editorOrView, Class clazz, int style, String label) {
		this(editorOrView, clazz, style, label, false);
	}

	public FormAddAction(Object editorOrView, Class clazz, int style, String label, boolean isCustom) {
		if (editorOrView instanceof FormEditor) {
			editor = (FormEditor) editorOrView;
			site = (IEditorSite)editor.getSite();
		} else if (editorOrView instanceof IEditorSite) {
			site = (IEditorSite) editorOrView;
		}
		this.clazz = clazz;
		this.style = style;

		String name = "";
		int pos = 0;
		if (clazz != null) {
			name = clazz.getName();
			name = JiglooUtils.unconvertUnavailableClassName(name);
			pos = name.lastIndexOf(".");
			name = name.substring(pos + 1);
			customClass = false;
		} else {
			customClass = true;
			classUndefined = true;
		}
		if (isCustom)
			customClass = true;

		if (label != null) {
			setText(label);
		} else if (style == SWT.NULL) {
			setText(name);
		} else {
			setText(name + " - " + FieldManager.getSWTFieldName(style));
		}

		ImageDescriptor imd = null;
		if(clazz == null) {
		    imd = ImageManager.getImageDesc("addbean.gif");
		} else {
			imd = ImageManager.getImageDescForClass(clazz, style);
			if(imd == null)
			    imd = ImageManager.getJavaBeanImgDesc();
		}
		if (imd != null) {
			setImageDescriptor(imd);
		}
	}

	public FormAddAction(Object editorOrView, Class clazz, int style) {
		this(editorOrView, clazz, style, null);
	}

	public FormAddAction(Object editorOrView, Class clazz) {
		this(editorOrView, clazz, SWT.NULL, null);
	}

	public Class getAddClass() {
		return clazz;
	}

	public int getAddStyle() {
		return style;
	}

	public void run() {
		run(getEditor().getMenuMouseEvent());
	}

	private FormEditor getEditor() {
		if (editor != null)
			return editor;
		return (FormEditor) site.getPage().getActiveEditor();
	}

	private Class loadClass(String className, Shell shell) {
		editor = getEditor();

		Class cls = editor.loadClass(className);
		if (cls == null) {
			MessageDialog.openWarning(
				shell,
				"Class not found",
				"Unable to find class\n   "
					+ className
					+ "\nCheck that it exists in this project and that it has"
					+ " been built (press Ctrl+B to build)");
			return null;
		}
		return cls;
	}

	public boolean isLayoutAction() {
	    return ClassUtils.isLayout(clazz);
	}
	
	public FormComponent getActionParent() {
		return actionParent;
	}
	
	public void run(MouseEvent evt) {
		try {
			editor = getEditor();
			actionParent = null;
			boolean swing = false;
			boolean swt = false;
			boolean cwt = false;
			boolean isSWT_AWT = false;
			
			if (editor != null) {
				parentComp = editor.getSelectedComponent();
				
				if(classUndefined) {
					swing = parentComp.isSwing();
					swt = parentComp.isSWT();
					cwt = parentComp.isCWT();
				} else if(!ClassUtils.isLayout(clazz)) {
					swing = ClassUtils.isSwing(clazz);
					swt = ClassUtils.isSWT(clazz);
					cwt = ClassUtils.isCWT(clazz);
				} else {
					swing = editor.isInSwingMode();
					swt = editor.isInSWTMode();
					cwt = editor.isInCWTMode();
				}
				
				if(clazz != null && ClassUtils.isSWTAWT(clazz)) {
					isSWT_AWT = true;
					if(parentComp.isSWT()) {
						clazz = Composite.class;
						style = SWT.EMBEDDED;
					} else {
						clazz = JPanel.class;
					}
					swing = false;
					swt = false;
					cwt = false;
				}
								
				editor.getJavaCodeParser().setAddActionDialogOpen(true);
			}

			if (classUndefined) {
				SelectionDialog dialog;
				try {

					dialog =
						JavaUI.createTypeDialog(
							site.getShell(),
							new ProgressMonitorDialog(site.getShell()),
							editor.getJavaFile().getProject(),
							IJavaElementSearchConstants.CONSIDER_CLASSES,
							false);
					dialog.setTitle("Select new class");
					dialog.setMessage("Select new class");
					if (dialog.open() == IDialogConstants.CANCEL_ID)
						return;
					Object[] types = dialog.getResult();
					dialog.close();
					if (types == null || types.length == 0) {
						editor.getJavaCodeParser().setAddActionDialogOpen(false);
						return;
					}
					String customClassName = ((IType) types[0]).getFullyQualifiedName();

					clazz = loadClass(customClassName, site.getShell());
					if (clazz == null) {
						editor.getJavaCodeParser().setAddActionDialogOpen(false);
						return;
					}
					if(ClassUtils.isLayout(clazz)) {
						JiglooUtils.addToCustomClasses(clazz.getName(), editor.getMode());
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			} else if (customClass) {
				//reload class every time
				clazz = loadClass(clazz.getName(), site.getShell());
				if (clazz == null) {
					editor.getJavaCodeParser().setAddActionDialogOpen(false);
					return;
				}
			}

			if(ClassUtils.isLayout(clazz)) {
				try {
					for (int i = 0; i < editor.getSelectedComponents().size(); i++) {
						FormComponent fc = editor.getSelectedComponent(i);
						LayoutWrapper lw = new LayoutWrapper(fc, clazz, fc.isSwing());
						fc.executeSetLayoutWrapper(lw);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				editor.getJavaCodeParser().setAddActionDialogOpen(false);
				return;
			}
			
			//newRoot true if this component is added outside of the main bounds - eg, adding a separate JPanel or JDialog
			boolean newRoot = false;
			if(evt != null) {
				Rectangle pbrr = editor.getRootComponent().getBoundsRelativeToRoot();
				if((parentComp == null || parentComp.equals(editor.getRootComponent())) 
						&& !pbrr.contains(x, y)
						&& (clazz == null 
								|| (!JMenuBar.class.isAssignableFrom(clazz) 
										&& !Menu.class.isAssignableFrom(clazz)
										&& !MenuItem.class.isAssignableFrom(clazz))))
					newRoot = true;
			}
			
			addedComponent = FormComponent.newFormComponent(editor, clazz.getName());
//			addedComponent.setEditor(editor);
//			addedComponent.setClassName(clazz.getName());

			if (parentComp == null)
				parentComp = editor.getRootComponent();

			if(addedComponent.isSubclassOf(AbstractAction.class) 
					&& parentComp != null 
					&& parentComp.hasProperty("action")) {
				actionParent = parentComp;
			}
			
			if (!addedComponent.isVisual()) {
				parentComp = editor.getNonVisualRoot();
			} else 	if(newRoot) {
			    parentComp = editor.getExtraCompRoot();
			}
			
			addedComponent.setParent(parentComp);

			boolean prompt = jiglooPlugin.getDefault().getBooleanPreference(MainPreferencePage.P_NAME_PROMPT);

			Vector propNames = PropertySourceFactory.findPropertyNames(clazz);

			String textValue = null;
			LayoutDataWrapper ldw = null;
			LayoutWrapper lw = null;
			ImageWrapper imgWrapper = null;
			String parLT = parentComp.getLayoutType();

			String initName = editor.getNextAvailableName(addedComponent);
			addedComponent.setName(initName);

			Constructor defaultCon = null;
			boolean needsParams = true;
			
			try {
				Constructor[] cons = clazz.getConstructors();
				for (int i = 0; i < cons.length; i++) {
					if(Modifier.isPrivate(cons[i].getModifiers()))
						continue;
					Class[] types = cons[i].getParameterTypes();
					if(types.length == 0) {
						defaultCon = cons[i];
						needsParams = false;
						break;
					} else if(types.length == 2 
							&& Composite.class.isAssignableFrom(types[0]) 
							&& types[1].equals(int.class)) {
						defaultCon = cons[i];
						needsParams = false;
						break;
					}
				}
			} catch(Exception e) {
				
			}
			
			needsParams = true;
			
//			if(needsParams) {
//				ConstructorParamsDialog cpd = 
//					new ConstructorParamsDialog(editor.getSite().getShell(), SWT.NULL, editor.getSite(), addedComponent);
//				cpd.open();
//				Object[] params = cpd.getParameters();
//				if(params == null)
//					return;
//				Constructor con = cpd.getConstructor();
//				String code = "new "+addedComponent.getClassNameForCode()+"(";
//				for (int i = 0; i < params.length; i++) {
//					if(i != 0)
//						code += ", ";
//					if(params[i] == null) {
//						code += "null";
//					} else {
//						String paramCode = addedComponent.getJavaCodeForObject(params[i], con.getParameterTypes()[i], "noid")[1];
//						if("\"null\"".equals(paramCode))
//							paramCode = "null";
//						code += paramCode;
//					}
//				}
//				code += ")";
//				addedComponent.setConstructor(con, params, code);
//			}

			boolean showConstraints = false;

			if (!addedComponent.isVisual()) {
				addedComponent.setInMainTree(true);
				addedComponent.setParent((FormComponent) null);
				Object nvo = addedComponent.makeNonVisualObject(addedComponent.getClassName());
				if (nvo == null) {
					return;
				}
				addedComponent.setNonVisualObject(nvo);

			} else {

				addedComponent.setParent(parentComp);

				if (!customClass) {
					if (swing) {
						Component comp = null;
						try {
							comp = addedComponent.makeComponent(addedComponent.getClassName());
						} catch (Exception e) {
							String msg = "Unable to create component:\n" + clazz.getName() + "\n\n" + e;
							if (e instanceof InstantiationException) {
								msg += "\nInterface or abstract class";
							}
							if (e instanceof InvocationTargetException) {
								msg += "\n" + ((InvocationTargetException) e).getTargetException();
							}
							System.err.println("ERROR CREATING CLASS " + clazz.getName());
							e.printStackTrace();
							MessageDialog.openError(editor.getSite().getShell(), "Unable to create component", msg);
							return;
						}
						addedComponent.setComponent(comp);
						if (addedComponent.isContainer() && !addedComponent.usesCustomLayout()) {
							lw = defaultSwingLayout;
							if (lw == null)
								lw = new LayoutWrapper(addedComponent);
						}
					} else if (cwt) {
						Object nvo = null;
						try {
							nvo = addedComponent.makeNonVisualObject(addedComponent.getClassName());
						} catch (Exception e) {
							String msg = "Unable to create nvo:\n" + clazz.getName() + "\n\n" + e;
							if (e instanceof InstantiationException)
								msg += "\nInterface or abstract class";
							if (e instanceof InvocationTargetException)
								msg += "\n" + ((InvocationTargetException) e).getTargetException();
							System.err.println("ERROR CREATING CLASS " + clazz.getName());
							e.printStackTrace();
							MessageDialog.openError(editor.getSite().getShell(), "Unable to create component", msg);
							return;
						}
						addedComponent.setNonVisualObject(nvo);
					} else if (swt) {
						if (addedComponent.isContainer()
								&& !addedComponent.usesCustomLayout()
								&& (addedComponent.isSwing() || addedComponent.needsLayout())) {
							lw = defaultSWTLayout;
							if (lw == null) {
								lw = new LayoutWrapper(addedComponent, GridLayout.class, false);
								lw.setSet(true);
							}
						}
					}
					if (lw != null)
						addedComponent.setLayoutWrapper(lw);
					if (!addedComponent.usesCustomLayout())
						showConstraints = true;
				}

				if (parLT != null) {
					if (parentComp.isSwing()) {
						ldw = (LayoutDataWrapper) defaultSwingLayoutCons.get(parLT);
						if (ldw == null) {
							if ("GridBag".equals(parLT)) {
								Object ld = new GridBagConstraints();
								ldw = new LayoutDataWrapper(ld, addedComponent);
								ldw.setObject(ld);
							} else if ("Form".equals(parLT)) {
								Object ld = new CellConstraints();
								ldw = new LayoutDataWrapper(ld, addedComponent);
								ldw.setObject(ld);
							} else if ("Anchor".equals(parLT)) {
								Object ld = new AnchorConstraint();
								ldw = new LayoutDataWrapper(ld, addedComponent);
								ldw.setObject(ld);
							} else if ("Border".equals(parLT) || "JRootPane$1".equals(parLT)) {
								String borderCon = parentComp.getNextBorderConstraint();
								addedComponent.getLayoutDataWrapper().setObject(borderCon);
								showConstraints = true;
							} else if ("Table".equals(parLT)) {
								String tableCon = "1, 1";
								addedComponent.getLayoutDataWrapper().setObject(tableCon);
								showConstraints = true;
							} else if ("Enfin".equals(parLT)) {
								String enfinCon = "HMOVE";
								addedComponent.getLayoutDataWrapper().setObject(enfinCon);
								showConstraints = true;
							} else if (PaneLayoutHandler.handlesLayout(parentComp)) {
								ldw = PaneLayoutHandler.getInitialLDWrapper(addedComponent);
							} else if (parentComp.isSubclassOf(JSplitPane.class)) {
								addedComponent.getLayoutDataWrapper().setObject(JSplitPane.LEFT);
								showConstraints = true;
							} else if (parentComp.isSubclassOf(JLayeredPane.class)) {
								addedComponent.getLayoutDataWrapper().setObject(
										new JLayerWrapper("DEFAULT_LAYER", addedComponent));
								showConstraints = true;
							}
						} else {
							ldw = ldw.getCopy(addedComponent);
							ldw.setObject(ldw.getObject());
						}
						if ("GridBag".equals(parLT) || "Form".equals(parLT)) {
							parentComp.prepNextGridXY(ldw, evt);
						} else if("Table".equals(parLT)) {
							parentComp.prepNextTableXY(addedComponent, evt);
						} else if(MigLayoutHandler.handlesLayout(parentComp)) {
							MigLayoutHandler.setXYFromEvent(parentComp, addedComponent, evt);
						}
					} else if (swt) {
						ldw = (LayoutDataWrapper) defaultSWTLayoutCons.get(parLT);
						if (ldw == null) {
							if ("Grid".equals(parLT)) {
								Object ld = new GridData();
								ldw = new LayoutDataWrapper(ld, addedComponent);
								ldw.setObject(ld);
							} else if ("Form".equals(parLT)) {
								Rectangle parBnds = parentComp.getBounds();
								int x = parBnds.width / 3;
								int y = parBnds.height / 3;
								Object ld = new FormData();
								ldw = new LayoutDataWrapper(ld, addedComponent);
								ldw.setObject(ld);
								JiglooUtils.initFormDataWrapper(ldw, x, y, parentComp);
							} else if(MigLayoutHandler.handlesLayout(parentComp)) {
								MigLayoutHandler.setXYFromEvent(parentComp, addedComponent, evt);
							}
						} else {
							ldw = ldw.getCopy(addedComponent);
							ldw.setObject(ldw.getObject());
						}
					}
				}

				if (ldw != null) {
					showConstraints = true;
					//don't call addedComponent.setLayoutDataWrapper
					//because this calls setLayoutData, which will add the
					//component to the parent
					addedComponent.setLayoutDataWrapperSimple(ldw);
				} else {
					if (!showConstraints)
						parLT = null;
				}
			}

			if(prompt) {
				NewComponentDialog ncd =
					new NewComponentDialog(editor.getSite().getShell(), SWT.NULL, editor.getSite(), parLT);

				ncd.setEditor(parentComp.getEditor());
				ncd.showConstraints(showConstraints);
				ncd.setComponent(addedComponent);
				ncd.setConstructor(defaultCon);
				
				if (propNames.contains("text") || (parentComp.isSwing() && parentComp.isA(JTabbedPane.class))) {
					ncd.showTextField(true);
				} else {
					ncd.showTextField(false);
				}
				if (propNames.contains("image") || propNames.contains("icon")) {
					ncd.showImgField(true);
				} else {
					ncd.showImgField(false);
				}
				ncd.open();

				Object[] results = ncd.getResults();

				editor.getJavaCodeParser().setAddActionDialogOpen(false);

				if (results == null)
					return;

				String newName = (String) results[0];
				addedComponent.setName(newName);

				if(!JiglooUtils.isValidJavaName(newName)) {
					editor.displayWarning("Invalid Java identifier",
							"The name you typed is not a valid Java identifier name");            
					return;
				}

				textValue = (String) results[1];
				imgWrapper = (ImageWrapper) results[4];

				boolean setAsDefaultLayout = ((Boolean) results[3]).booleanValue();

				boolean setAsDefaultLayoutConst = ((Boolean) results[2]).booleanValue();

				boolean addGetterMethod = ((Boolean) results[5]).booleanValue();

				addedComponent.createGetterMethod(addGetterMethod);

				lw = addedComponent.getLayoutWrapper();
				ldw = addedComponent.getLayoutDataWrapper();

				if (setAsDefaultLayout) {
					if (swing) {
						defaultSwingLayout = lw;
					} else if (swt) {
						defaultSWTLayout = lw;
					}
				}

				if (setAsDefaultLayoutConst) {
					if (swing) {
						defaultSwingLayoutCons.put(parLT, ldw);
					} else if (swt) {
						defaultSWTLayoutCons.put(parLT, ldw);
					}
				}
			} else {
				textValue = addedComponent.getName();
				editor.getJavaCodeParser().setAddActionDialogOpen(false);
				lw = addedComponent.getLayoutWrapper();
				ldw = addedComponent.getLayoutDataWrapper();
			}

			addedComponent.setEditor(editor);

			if (addedComponent.isVisual()) {

				if (swing) {
					Component comp = null;
					try {
						comp = addedComponent.makeComponent(addedComponent.getClassName(), true);
					} catch (Throwable e) {
						MessageDialog.openError(
								editor.getSite().getShell(),
								"Error creating " + addedComponent.getClassName(),
								"Error creating " + addedComponent.getClassName() + "\n" + e.toString());
						return;
						//e.printStackTrace();
					}
					addedComponent.setComponent(comp);
				} else if (cwt) {
					Object nvo = null;
					try {
						nvo = addedComponent.makeNonVisualObject(addedComponent.getClassName());
					} catch (Exception e) {
						MessageDialog.openError(
								editor.getSite().getShell(),
								"Error creating " + addedComponent.getClassName(),
								"Error creating " + addedComponent.getClassName() + "\n" + e.toString());
						return;
						//e.printStackTrace();
					}
					addedComponent.setNonVisualObject(nvo);
				} else if (swt) {
					if (clazz.equals(Menu.class) && style == SWT.BAR && parentComp.isRoot()) {
						FormComponent root = parentComp.getRootComponent();
						addedComponent.setStyle(SWT.BAR);
						addedComponent.setInMainTree(true);
						if (!root.getEditor().setMenuBar(addedComponent, true))
							return;
					} else {

						addedComponent.setStyle(style);

						if(!newRoot) {
							Object[] info = new Object[] { clazz.getName(), new Integer(style)};
							Object parent = parentComp.getRawControl();
							addedComponent.makeAndSetControl(info, parent, true);

							Object wid = addedComponent.getControl();
							//if there was a problem!!
							if (wid == null) {
								MessageDialog.openError(
										editor.getSite().getShell(),
										"Error creating " + clazz.getName(),
										"Error creating " + clazz.getName());
								//System.err.println("Problem making " + clazz.getName());
								return;
							}
							if (wid instanceof Control) {
								Control ctrl = (Control) wid;
								//ctrl.setSize(80, 40);
							}
						}
					}
				}

				if (customClass) {
					lw = new LayoutWrapper(addedComponent);
					addedComponent.setLayoutWrapper(lw);
					lw.setSet(false);
				}

			}

			addedComponent.setEditor(parentComp.getEditor());
			addedComponent.setInMainTree(true);

			String nameProp = (String) addedComponent.getPropertyValue("name");
			if(nameProp != null && addedComponent.getComponent() != null)
				addedComponent.getComponent().setName(nameProp);

			//TODO streamline adding of components (to set Viewport etc)
			//so that same code is used whether adding "manually" or from form
			if (swing && clazz.equals(JMenuBar.class)) {
				FormComponent root = parentComp.getRootComponent();
				if (parentComp.equals(root) && !root.getEditor().setMenuBar(addedComponent, true)) {
					addedComponent.setInMainTree(false);
					return;
				}
			} else if (clazz.equals(Menu.class)) {
				if (parentComp.isRoot()) {
					// || parentComp.equals(extraRoot)) {
					//FormComponent root = comp.getRootComponent();
					//root.getEditor().setMenuBar(fc, true);
				} else if (
						parentComp.getMainClass().equals(Menu.class) || parentComp.getMainClass().equals(MenuItem.class)) {
					parentComp.add(addedComponent);
				} else {
					jiglooPlugin.handleError(new Exception("ERROR - cannot add Menu to " + parentComp));
					addedComponent.setInMainTree(false);
					return;
				}
			} else if (clazz.equals(MenuItem.class)) {
				if (parentComp.getMainClass().equals(Menu.class)) {
					parentComp.add(addedComponent);
					if(parentComp.getParent() == null) {
						addedComponent.setStyle(SWT.CASCADE, false);
					}
				} else if (parentComp.getMainClass().equals(MenuItem.class)) {
					if(!parentComp.hasStyle(SWT.CASCADE)) {
						parentComp.setStyle(SWT.CASCADE, false);
						parentComp.repairConstructorInCode();
					}
					if(parentComp.getChildCount() == 0) {
						FormComponent menu = FormComponent.newFormComponent(parentComp, Menu.class.getName(), null, false, null);
						menu.setStyle(SWT.DROP_DOWN);
						menu.setExistsInCode(true);
						menu.setInMainTree(true);
						menu.addToCode();
						parentComp.add(menu);
						menu.add(addedComponent);
					} else {
						parentComp.getChildAt(0).add(addedComponent);
					}
				} else {
					jiglooPlugin.handleError(new Exception("ERROR - cannot add MenuItem to " + parentComp));
					addedComponent.setInMainTree(false);
					return;
				}
			} else {
				//if (addedComponent.isVisual())
				parentComp.add(addedComponent);
			}

			//addedComponent.setSwing(swing);
			if (swing)
				addedComponent.setClassType(FormComponent.TYPE_SWING);
			else if (cwt)
				addedComponent.setClassType(FormComponent.TYPE_CWT);
			else if (swt)
				addedComponent.setClassType(FormComponent.TYPE_SWT);

			if ("".equals(textValue))
				textValue = null;

			if (textValue != null) {
				if (propNames.contains("text"))
					addedComponent.setPropertyValueDirect("text", textValue);
			}

			if (imgWrapper != null) {
				String imgName = imgWrapper.getName();
				if (imgName != null && !imgName.equals("")) {
					if (propNames.contains("image"))
						addedComponent.setPropertyValueDirect("image", imgWrapper);
					else if (propNames.contains("icon"))
						addedComponent.setPropertyValueDirect("icon", imgWrapper.toIconWrapper());
				}
			}
			
			if (actionParent != null && actionParent.isA(JMenu.class)) {
				FormComponent menuItem = FormComponent.newFormComponent(actionParent, JMenuItem.class.getName(), null, false, null);
				menuItem.setExistsInCode(true);
				menuItem.setInMainTree(true);
				menuItem.setPropertyValueInternal("text", menuItem.getNameInCode(), true);
				actionParent.populateComponents(null, editor);
				editor.selectComponent(menuItem);
				actionParent = menuItem;
				menuItem.addToCode();
			}

			if (addedComponent.isVisual()) {

				String plt = parentComp.getLayoutType();
				if ("Anchor".equals(plt) || (swt && "Form".equals(plt))) {
					addedComponent.setExistsInCode(true);
					addedComponent.moveTo(0);
				}

				if (parentComp.isSwing() && parentComp.isA(JTabbedPane.class)) {
					if (textValue != null) {
						addedComponent.setTabTitle(textValue);
						addedComponent.setPropertyValueCode("LAYOUT_CONSTRAINT", null);
					}
				}

				if (addedComponent.isJMenuBar()) {
					addedComponent.setPropertyValue("minimumSize", new Point(60, 15), false);
					FormComponent menu = FormComponent.newFormComponent(addedComponent, JMenu.class.getName(), null, false, null);
					menu.setExistsInCode(true);
					menu.setInMainTree(true);
					menu.setPropertyValueInternal("text", menu.getNameInCode(), true);
					addedComponent.populateComponents(null, editor);
					editor.selectComponent(menu);
				}

				if (addedComponent.getParent() != null && !addedComponent.isMenuComponent()) {
					if (addedComponent.getParent().usesAbsoluteTypeLayout()) {
						if (addedComponent.isSwing()) {
						    //v4.0M2
						    Dimension ps = new Dimension(60, 30);
						    if(addedComponent.getComponent() != null)
						        ps = (Dimension) addedComponent.getComponent().getPreferredSize();
						    int w = Math.max(10, ps.width);
						    int h = Math.max(10, ps.height);
							addedComponent.setPropertyValue("bounds", new Rectangle(0, 0, w, h), false);
						} else {
						    if(!"Form".equals(addedComponent.getParentLayoutType())) {
						        Object wid = addedComponent.getParent().getControl();
						        if (!(wid instanceof CTabItem)
						                && !(wid instanceof TabItem)
						                && !(wid instanceof SashForm)) {
						            addedComponent.setPropertyValueDirect("size", new Point(60, 30));
						        }
						    }
						}
					} else {
						Object wid = addedComponent.getControl();
						if (wid instanceof SashForm) {
							addedComponent.setPropertyValueDirect("size", new Point(60, 30));
						}
					}

				}
				if (parentComp.isSWT())
					addedComponent.setSWTLayoutInfo(true, false);

//				addLayoutNodes(evt, cwt, ldw);

			}

			
			if (addedComponent.isA(TableColumn.class)) {
				addedComponent.setPropertyValue("width", new Integer(60));
			}
			try {
				if (addedComponent.isA(TreeColumn.class)) {
					addedComponent.setPropertyValue("width", new Integer(60));
				}
				if (addedComponent.isA(Link.class)) {
					String txt = (String)addedComponent.getPropertyValue("text");
					if(txt.indexOf("<") < 0)
						txt= "<a href=\""+txt+"\">"+txt+"</a>";
					addedComponent.setPropertyValue("text", txt);
				}
			} catch(Throwable t) {
//				t.printStackTrace();
			}
			
			if (isSWT_AWT) {
				if (parentComp.isSwing()) {
					addedComponent.setLayoutWrapper(new LayoutWrapper(addedComponent, "Border", false));
					FormComponent canvas = FormComponent.newFormComponent(addedComponent, Canvas.class.getName(), null, false, null);
					canvas.setExistsInCode(true);
					canvas.setInMainTree(true);
					FormComponent shell = FormComponent.newFormComponent(canvas, Shell.class.getName(), null, false, null);
					shell.setExistsInCode(true);
					shell.setInMainTree(true);
					shell.setLayoutWrapper(new LayoutWrapper(shell, "Grid", false));
					addedComponent.populateComponents((Container) parentComp
							.getComponent(), editor);
					addedComponent.updateUI();
				} else {
					
					//need this for some reason otherwise the child frame and panel
					//don't have the correct bounds
					addedComponent.setStyle(SWT.EMBEDDED, false);

					FormComponent frame = FormComponent.newFormComponent(addedComponent,
							java.awt.Frame.class.getName(), null, false, null);
					frame.setExistsInCode(true);
					frame.setInMainTree(true);
					FormComponent panel = FormComponent.newFormComponent(frame,
							java.awt.Panel.class.getName(), null, false, null);
					panel.setExistsInCode(true);
					panel.setInMainTree(true);
					addedComponent.populateControls(parentComp.getControl(), editor, true);
					addedComponent.updateUI();
				}
			}


			if (addedComponent.isA(CoolBar.class)) {
				FormComponent cb = FormComponent.newFormComponent(addedComponent, CoolItem.class.getName(), null, false, null);
				FormComponent tb = FormComponent.newFormComponent(cb, ToolBar.class.getName(), null, false, null);
				FormComponent ti = FormComponent.newFormComponent(tb, ToolItem.class.getName(), null, false, null);
				cb.setExistsInCode(true);
				cb.setInMainTree(true);
				tb.setExistsInCode(true);
				tb.setInMainTree(true);
				ti.setExistsInCode(true);
				ti.setInMainTree(true);
				addedComponent.populateControls(((Control) addedComponent.getControl()).getParent(), editor, false);
				addedComponent.updateUI();
			}

			if (addedComponent.isA(CoolItem.class)) {
				FormComponent tb = FormComponent.newFormComponent(addedComponent, ToolBar.class.getName(), null, false, null);
				FormComponent ti = FormComponent.newFormComponent(tb, ToolItem.class.getName(), null, false, null);
				tb.setExistsInCode(true);
				tb.setInMainTree(true);
				ti.setExistsInCode(true);
				ti.setInMainTree(true);
				addedComponent.populateControls(
					((Control) addedComponent.getParent().getControl()).getParent(),
					editor,
					false);
				addedComponent.updateUI();
			}

			if (addedComponent.isA(ToolBar.class)) {
				FormComponent ti = FormComponent.newFormComponent(addedComponent, ToolItem.class.getName(), null, false, null);
				ti.setExistsInCode(true);
				ti.setInMainTree(true);
				addedComponent.populateControls(((Control) addedComponent.getControl()).getParent(), editor, false);
				addedComponent.updateUI();
			}

			if (addedComponent.isA(TabFolder.class)) {
				FormComponent ti = FormComponent.newFormComponent(addedComponent, TabItem.class.getName(), null, false, null);
				ti.setInMainTree(true);
				ti.setExistsInCode(true);
				addedComponent.setPropertyValueSimple("selection", new Integer(0));
				addedComponent.populateControls(((Control) addedComponent.getControl()).getParent(), editor, false);
				ti.setPropertyValueInternal("text", ti.getNameInCode(), true);
				addedComponent.updateUI();
			}

			if (addedComponent.isA(TabItem.class)) {
			    if(!addedComponent.getParent().isPropertySet("selection")) {
			        addedComponent.getParent().setPropertyValueDirect("selection", new Integer(0));
			    }
			}
			
			if (addedComponent.isA(CTabFolder.class)) {
				FormComponent ti = FormComponent.newFormComponent(addedComponent, CTabItem.class.getName(), null, false, null);
				ti.setExistsInCode(true);
				ti.setInMainTree(true);
				addedComponent.setPropertyValueSimple("selection", new Integer(0));
				addedComponent.populateControls(((Control) addedComponent.getControl()).getParent(), editor, false);
				ti.setPropertyValueInternal("text", ti.getNameInCode(), true);
				addedComponent.updateUI();
			}
			
			if (addedComponent.isA(CTabItem.class)) {
			    if(!addedComponent.getParent().isPropertySet("selection")) {
			        addedComponent.getParent().setPropertyValueDirect("selection", new Integer(0));
			    }
			}
			
			if (addedComponent.isA(MenuItem.class) && addedComponent.getStyle() == SWT.CASCADE) {
				FormComponent menu = FormComponent.newFormComponent(addedComponent, Menu.class.getName(), null, false, null);
				menu.setExistsInCode(true);
				menu.setInMainTree(true);
				addedComponent.populateControls(((Control) addedComponent.getControl()).getParent(), editor, true);
				editor.selectComponent(menu);
			}

			if(editor.isPartOfAppFrameworkApplication()) {
			    if(addedComponent.isSubclassOf(AbstractAction.class)) {
			        addedComponent.setDeclarationInCode("getAppActionMap().get(\""+addedComponent.getNameInCode()+"\")");
			    }
			}
			
			if (addedComponent.isSubclassOf(JInternalFrame.class)) {
				addedComponent.setPropertyValue("visible", Boolean.TRUE);
			}
			
			addedComponent.addToCode();

			addLayoutNodes(evt, cwt, ldw);

			addInEventHandlers(addedComponent);

			if(actionParent != null) {
				actionParent.setPropertyValueInternal("action", addedComponent, true);
				actionParent.updateInCode("action");
			}

			addedComponent.updateUI();
			
			if(PaneLayoutHandler.handlesLayout(parentComp)) {
			    PaneLayoutHandler.finalise(addedComponent);
			}
						
			if (customClass) {
				JiglooUtils.addToCustomClasses(clazz.getName(), editor.getMode());
			}

			editor.setAddedComponent(addedComponent);
			editor.setDirtyAndUpdate();
			//init inherited elems after component has been layed out
			addedComponent.initInheritedElements();

			//need to re-set clazz to SWT_AWT, since it is currently either a Canvas or a Composite
			if(isSWT_AWT) {
			    try {
			        clazz = SWT_AWT.class;
			    } catch(Throwable t) {}
			}

			tempFC = null;

			//open customizer automatically (if the added component has one)
			addedComponent.showCustomizer();
			
		} catch (Throwable e) {
		    jiglooPlugin.handleError(e);
		}
	}

	private void addLayoutNodes(MouseEvent evt, boolean cwt, LayoutDataWrapper ldw) {
		Rectangle b;
		LayoutWrapper lw;
		if (ldw != null 
				&& !FormComponent.DIALOG_BUTTON.equals(addedComponent.getSpecialType()))
			addedComponent.setLayoutDataWrapper(ldw);

		parentComp.updateUI();

		if (evt != null 
				&& addedComponent != null 
				&& !addedComponent.isMenuComponent()) {
			if (cwt) {
				com.cloudgarden.jigloo.typewise.TypewiseManager.adjustInitialBounds(addedComponent, evt);
			} else if (parentComp.usesAbsoluteTypeLayout()) {
				FormComponent par = addedComponent.getParent();
				b = addedComponent.getBounds();
				SnapTo snapTo = editor.getSnapTo();
				if(snapTo != null) {
				    //v4.0M2
					snapTo.setOrigin(b);
				} else {
				    b.x = evt.x;
				    b.y = evt.y;
				}
				if (par.usesContentPane()) {
					SwingHelper.subtractVectorBetween(
						(Container) par.getComponent(),
						addedComponent.getComponent(),
						b);
					b.y -= editor.getMenuBarHeight();
				} else if(editor.isWorkbenchPart()) {
					Rectangle pb = par.getBoundsRelativeToViewport();
					b.x -= pb.x;
					b.y -= pb.y;
				} else {
					Rectangle pb = par.getBoundsRelativeToRoot();
					b.x -= pb.x;
					b.y -= pb.y;
				}
				
				if(addedComponent.isSWT()) {
					b.y += editor.getMenuHeight();
				}
				editor.snapToGrid(b, addedComponent, false);
				
				addedComponent.setPropertyValueDirect("bounds", b);
				addedComponent.updateInCode("bounds");
			}
		}
		
		lw = addedComponent.getLayoutWrapper();

		if (addedComponent.usesLayout() && !customClass && lw != null && lw.getLayoutType() != null) {
			addedComponent.setLayoutWrapper(lw, true);
		}
		
	}

    /**
     * Sets expected default event handers (possibly Action and Selection handlers
     * for JButtons and Buttons - but does nothing at present because doesn't offer
     * a choice betewwn inline or method handling...)
	 */
	private void addInEventHandlers(FormComponent addedComponent2) {
		if(true)
			return;
		if (addedComponent.isA(Button.class)) {
			EventWrapper ew = addedComponent.getEventWrapper(true);
			ew = ew.getChildEventWrapper("SelectionListener");
			ew.setPropertyValue("widgetSelected", "doesntmatter"+EventPropertyDescriptor.INLINE);
		}
		if(jiglooPlugin.canUseSwing()) {
			if (addedComponent.isA(JButton.class)) {
				EventWrapper ew = addedComponent.getEventWrapper(true);
				ew = ew.getChildEventWrapper("ActionListener");
				ew.setPropertyValue("actionPerformed", "doesntmatter"+EventPropertyDescriptor.INLINE);
			}
		}
	}

    private FormComponent tempFC = null;
	private int x;
	private int y;
    
    public Rectangle getBounds() {
        FormComponent tfc = getTempFormComponent();
        if(tfc == null)
            return null;
        Rectangle b = tfc.getBounds();
        return b;
    }
    
    /**
     * @return
     */
    public FormComponent getTempFormComponent() {
    	if(tempFC != null)
    		return tempFC;
    	
    	Class tmpClass = clazz;

		if(clazz != null &&
				"org.eclipse.swt.awt.SWT_AWT".equals(clazz.getName())) {
			tmpClass = JPanel.class;
		}
		
    	if(!ClassUtils.isVisual(tmpClass) || tmpClass == null)
    		return null;
    	
    	if(editor.isInSwingMode()) {
//        if(JiglooUtils.isSwing(clazz)) {
            if(tempFC == null) {
                tempFC = FormComponent.newFormComponent(editor, tmpClass.getName());
                Component comp = null;
                try {
                	if(tmpClass.isMemberClass())
                    	comp = (Component)ClassUtils.createInnerClass(tmpClass, editor.getRootObject());
                	else
                		comp = (Component) tmpClass.newInstance();
                } catch (Throwable e) {
                	System.out.println("Unable to instantiate class "+tmpClass+", "+e);
                }
                if(comp == null) {
                	return null;
                }
                if(comp instanceof JComponent)
                    ((JComponent)comp).setOpaque(true);
                tempFC.setComponent(comp);
                tempFC.setEditor(editor);
                tempFC.setClassName(tmpClass.getName());
                String initName = editor.getNextAvailableName(tempFC);
                Object model = tempFC.createSwingModel();
                try {
                    Method m = tmpClass.getMethod("setText", new Class[] {String.class});
                    m.invoke(comp, new Object[] {initName});
                } catch (Throwable e1) {   }
                try {
                    if(model != null) {
                        Method m = JiglooUtils.findMethod(tmpClass, "setModel", new Class[] {model.getClass()});
                        if(m != null)
                            m.invoke(comp, new Object[] {model});
                    }
                } catch (Throwable e1) {     }
                comp.invalidate();
                comp.validate();
            }
        }
        return tempFC;
    }

	public void setMousePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
