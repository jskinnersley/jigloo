/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.layoutHandler.EnfinLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.PaneLayoutHandler;
import com.cloudgarden.jigloo.layoutHandler.XYLayoutHandler;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.properties.descriptors.ChoicePropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.SiblingPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.TextFormPropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.FormPropertySource;
import com.cloudgarden.jigloo.properties.sources.InsetsPropertySource;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.xml.XMLWriter;
import com.cloudgarden.layout.AnchorConstraint;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LayoutDataWrapper implements IFormPropertySource {

	private FormComponent comp;
	private FormPropertySource layoutDataSrc;
	private Object layoutData;
	private String name = null;
	private Class mainClass;
	
	//code that is set when the constraint is parsed - if this constraint is not supported
	//then getJavaCode method will return this code.
	private String constraintCode;

	private boolean updateUI = true;
	private static Integer FILL = new Integer(GridData.FILL);

	public LayoutDataWrapper(FormComponent comp) {
		//System.err.println("NEW LDW (1) " + comp);
		//new Exception().printStackTrace();
        if (comp != null && comp.getParent() != null && comp.isSwing()) {
            Component cmp = comp.getComponent();
            if (cmp != null) {
                Container pcmp = cmp.getParent();
                if (pcmp != null) {
                    LayoutManager lm = pcmp.getLayout();
                    Object obj = null;
                    try {
                        if (lm instanceof GridBagLayout) {
                            obj = ((GridBagLayout) lm).getConstraints(cmp);
                        } else if(lm instanceof FormLayout) {
                            obj = ((FormLayout)lm).getConstraints(cmp);
                        }
                    } catch(Throwable t) {
                        //                        t.printStackTrace();
                    }
                    if (obj != null) {
                        this.layoutData = obj;
                        setFormComponent(comp);
                        mainClass = layoutData.getClass();
                        layoutDataSrc = new FormPropertySource(comp, obj);
                        return;
                    }
                }
            }
        }
		setFormComponent(comp);
	}

	public void setFormComponent(FormComponent comp) {
		setFormComponent(comp, false);
	}

	public void setFormComponentSimple(FormComponent comp) {
		this.comp = comp;
		if(layoutDataSrc != null)
		    layoutDataSrc.setFormComponent(comp);
	}

	public void setFormComponent(FormComponent comp, boolean build) {
	    //System.out.println("LDW: SET FORM COMP "+comp+", "+layoutData);
		if (comp == null) {
			layoutDataSrc = new FormPropertySource(comp, null);
			return;
		}
		this.comp = comp;
		//layoutDataSrc = new FormPropertySource(comp, layoutData);
		if (build) {
			//this is for FormData s
			LayoutDataWrapper top = (LayoutDataWrapper) getPropertyValue("top", true);
			LayoutDataWrapper bottom = (LayoutDataWrapper) getPropertyValue("bottom", true);
			LayoutDataWrapper left = (LayoutDataWrapper) getPropertyValue("left", true);
			LayoutDataWrapper right = (LayoutDataWrapper) getPropertyValue("right", true);
			layoutDataSrc = new FormPropertySource(comp, layoutData);
			updateValue("top", top);
			updateValue("bottom", bottom);
			updateValue("left", left);
			updateValue("right", right);
		} else {
			layoutDataSrc = new FormPropertySource(comp, layoutData);
		}
	}

	private void updateValue(String id, LayoutDataWrapper val) {
		if (val == null)
			return;
		Object oval = layoutDataSrc.getPropertyValue(id);
		if (true || oval == null || oval.equals("null")) {
			if (//val.getObject() == null && 
					FormAttachment.class.equals(val.getMainClass())) {
				System.out.println("layout data wrapper update value "+id+", "+comp);
				val.setFormComponentSimple(comp);
				FormAttachment obj = new FormAttachment(0, 0);
				Object ctrl = val.getPropertyValue("control");
				Object offset = val.getIntegerProp("offset", 0);
				Object alignment = val.getIntegerProp("alignment", SWT.DEFAULT);
				Object numerator = val.getIntegerProp("numerator", 0);
				Object denominator = val.getIntegerProp("denominator", 100);
				val.setObject(obj);
				val.setPropertyValue("control", ctrl);
				val.setPropertyValue("offset", offset);
				val.setPropertyValue("alignment", alignment);
				val.setPropertyValue("numerator", numerator);
				val.setPropertyValue("denominator", denominator);
			}
			setPropertyValue(id, val);
		}
	}

	public LayoutDataWrapper(Object obj, FormComponent comp) {
		//new Exception().printStackTrace();
		this.layoutData = obj;
		setFormComponent(comp);
		if (obj != null)
			mainClass = layoutData.getClass();
		layoutDataSrc = new FormPropertySource(comp, obj);
		//System.err.println(
		//"NEW LDW (2) " + comp + ", " + obj + ", " + layoutDataSrc);
	}

	public LayoutDataWrapper(FormComponent comp, Node node) {
		this(comp);
		//System.err.println("NEW LDW (3) " + comp + ", " + node);
		//new Exception().printStackTrace();
		Node ln = NodeUtils.getChildNodeByName("LayoutConstraint", node);
		String cls = null;
		HashMap properties = null;
		boolean netbeansGBC = false;
		if (ln != null) {
			cls = NodeUtils.getComponentClass(ln);
			if (cls == null)
				return;
		} else {
			ln = NodeUtils.getChildNodeByName("Constraint", node);
			if (ln != null)
				ln = NodeUtils.getChildNodeByName("GridBagConstraints", ln);
			cls = "java.awt.GridBagConstraints";
			netbeansGBC = true;
		}
		if (ln == null)
			return;
		properties = new HashMap();
		NodeUtils.fillAttributes(properties, ln, comp, true);
		//System.out.println("LAYOUTDATA class = "+cls);
		try {
			mainClass = Class.forName(cls);
			layoutData = ClassUtils.newInstance(mainClass, null, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		layoutDataSrc = new FormPropertySource(comp, layoutData);
		Iterator it = properties.keySet().iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			if (name.equals("class"))
				continue;
			Object val = properties.get(name);
			if (netbeansGBC) {
				name = name.toLowerCase();
			}
			if (val instanceof FormAttachment) {
				val = new LayoutDataWrapper(val, comp);
			}
			if (netbeansGBC && name.startsWith("insets")) {
				InsetsPropertySource ival = (InsetsPropertySource) getPropertyValue("insets");
				if (ival == null) {
					Insets ins = new Insets(0, 0, 0, 0);
					ival = new InsetsPropertySource(ins);
				}
				name = name.substring(6);
				ival.setPropertyValue(name, val);
				setPropertyValue("insets", ival);
			} else {
				setPropertyValue(name, val);
			}
		}
		comp.setLayoutDataWrapper(this);
		//comp.setLayoutData(layoutData);
	}

	public FormComponent getFormComponent() {
		return comp;
	}

	public void setObject(Object data) {
//		if(comp.getName().equals("jButton12"))
//		System.err.println("SET LAYOUT DATA " + data + ", " + comp);
//		new Exception().printStackTrace();
		layoutData = data;
		layoutDataSrc = null;
		if(data instanceof String)
			return;
		layoutDataSrc = new FormPropertySource(comp, null);
		if (data != null) {
			mainClass = layoutData.getClass();
			layoutDataSrc.setObject(data);
		}
	}

	private Object getLayoutDataCopy(Object data, FormComponent copyFC) {
		if (data == null)
			return null;
		if (data instanceof FormData)
			return new FormData();
		if (data instanceof GridData)
			return new GridData();
		if (data instanceof RowData)
			return new RowData();
		if (data instanceof FormAttachment)
			return new FormAttachment(0, 0);
		if (data instanceof GridBagConstraints)
			return new GridBagConstraints();
		if (data instanceof AnchorConstraint)
			return new AnchorConstraint();
		if (data instanceof JLayerWrapper)
			return ((JLayerWrapper)data).getCopy(copyFC);
		if (data instanceof String)
			return data;
		if (data instanceof Integer)
			return data;
		if (data instanceof Float)
			return data;
		if (data instanceof Long)
			return data;
		if (data instanceof Double)
			return data;
		if (data instanceof Boolean)
			return data;
		if (data instanceof Short)
			return data;
		try {
		    return ClassUtils.newInstance(data.getClass(),null, null);
		} catch(Throwable t) {
		    t.printStackTrace();
		}
		System.err.println("UNHANDLED LAYOUT DATA " + data.getClass() + ", " + data + ", " + comp);
		return null;
	}

	public LayoutDataWrapper getCopy(FormComponent comp) {
		if (comp == null)
			comp = this.comp;
		//System.err.println(
		//"GET COPY " + layoutDataSrc + ", " + hashCode() + ", " + comp);
		LayoutDataWrapper ldw = new LayoutDataWrapper((Object) null, comp);
		//HashMap properties = layoutDataSrc.getProperties();
		//		if (properties == null)
		//			return lw;
		ldw.setObject(getLayoutDataCopy(layoutData, comp));
        ldw.setMainClass(mainClass);
        ldw.setConstraintCode(constraintCode);
        if (name != null)
            ldw.setName(name);

        if (layoutDataSrc != null) {
            if (true || layoutDataSrc.getPropertyNames().size() != 0) {
                Vector pnames = layoutDataSrc.getPropertyNames();
                for (int i = 0; i < pnames.size(); i++) {
                    Object name = pnames.elementAt(i);
                    Object val = getPropertyValue(name, true);
                    if (val == null)
                        val = "null";
                    if (val instanceof LayoutDataWrapper) {
                        val = ((LayoutDataWrapper) val).getCopy(comp);
                    } else {
                        val = FormComponent.copyObject(val, name, comp);
                    }
                    if (val != null)
                        ldw.setPropertyValue(name, val, false);
//    				System.err.println("LDW: COPYING PROP "+name+", val="+val+", set="+lw.isPropertySet(name));
//    				if(name.equals("widthHint"))
//    				    new Exception().printStackTrace();
                }

            } else if (layoutDataSrc.getProperties() != null) {

                Iterator it = layoutDataSrc.getProperties().keySet().iterator();
                while (it.hasNext()) {
                    Object name = it.next();
                    Object val = getPropertyValue(name, true);
                    if (val == null)
                        val = "null";
                    if (val instanceof LayoutDataWrapper) {
                        val = ((LayoutDataWrapper) val).getCopy(comp);
                    } else {
                        val = FormComponent.copyObject(val, name, comp);
                    }
                    if (val != null)
                        ldw.setPropertyValue(name, val, false);
                }
            }
        }
		return ldw;
	}

	public void dispose() {
		if (layoutData instanceof FormAttachment) {
			((FormAttachment) layoutData).control = null;
		}
		mainClass = null;
		comp = null;
		layoutData = null;
		if(layoutDataSrc != null) {
			layoutDataSrc.dispose();
		}
		pdescs = null;
	}

	public Object getLayoutData() {
		return layoutData;
	}

	public void refreshControlRefs() {
		
		if (layoutDataSrc == null)
			return;
		
		if(!(layoutData instanceof FormData) 
				&& ! (layoutData instanceof FormAttachment))
			return;
		
		Vector pnames = layoutDataSrc.getPropertyNames();
		if (pnames == null || pnames.size() == 0)
			return;
		for (int i = 0; i < pnames.size(); i++) {
			Object id = pnames.elementAt(i);
			Object val = getPropertyValue(id);
			if (val instanceof LayoutDataWrapper) {
				((LayoutDataWrapper) val).refreshControlRefs();
			} else if (id.equals("control") 
					&& (layoutData instanceof FormAttachment)) {
				if (val instanceof FormComponentWrapper) {
					FormComponent fc = ((FormComponentWrapper) val).getFormComponent();
					if (fc == null) {
						((FormAttachment) layoutData).control = null;
					} else {
						//replace with a new FormComponentWrapper so that
						//getFormComponent of the new wrapper will be fixed.
						val = new FormComponentWrapper(fc);
						setPropertyValue(id, val);

						 ((FormAttachment) layoutData).control = (Control) fc.getControl();
					}
				} else if ("null".equals(val)) {
					((FormAttachment) layoutData).control = null;
				}
			}
		}
		if (comp != null)
			comp.updateUI();
	}

	public Object getObject() {
		return layoutData;
	}

	public void setMainClass(Class cls) {
		mainClass = cls;
	}

	public Class getMainClass() {
		return mainClass;
		//if (layoutData == null)
		//return null;
		//return layoutData.getClass();
	}

	private Integer getIntegerProp(String propName) {
	    return getIntegerProp(propName, 0);
	}

	private Integer getIntegerProp(String propName, int defaultVal) {
	    if(layoutDataSrc == null)
	        return null;
	    Object val = layoutDataSrc.getPropertyValue(propName);
	    if(val instanceof Integer)
	        return (Integer)val;
	    return new Integer(defaultVal);
	}
	
	private FieldWrapper getFieldProp(String propName) {
	    if(layoutDataSrc == null)
	        return null;
	    Object val = layoutDataSrc.getPropertyValue(propName);
	    if(val instanceof FieldWrapper)
	        return (FieldWrapper)val;
	    return null;
	}
	
	private String getFormAlignAsString(CellConstraints.Alignment al) {
	    if(al.equals(CellConstraints.TOP))
	        return "CellConstraints.TOP";
	    if(al.equals(CellConstraints.BOTTOM))
	        return "CellConstraints.BOTTOM";
	    if(al.equals(CellConstraints.LEFT))
	        return "CellConstraints.LEFT";
	    if(al.equals(CellConstraints.RIGHT))
	        return "CellConstraints.RIGHT";
	    if(al.equals(CellConstraints.FILL))
	        return "CellConstraints.FILL";
	    if(al.equals(CellConstraints.CENTER))
	        return "CellConstraints.CENTER";
        return "CellConstraints.DEFAULT";
	}
	
	public String getJavaCode(String name, boolean isSwing, IJavaCodeManager jcm) {
		//System.out.println(
		//"GET JAVA CODE " + name + ", " + comp + ", " + layoutData);
		updateUI = false;
		String code = "";
		if (isSwing) {
			if (layoutData instanceof GridBagConstraints) {
				GridBagConstraints gbc = (GridBagConstraints) layoutData;
				jcm.addImport(GridBagConstraints.class.getName());
				jcm.addImport(Insets.class.getName());
				Insets insets = gbc.insets;
				updateUI = true;
				String gw = "" + gbc.gridwidth;
				if (gbc.gridwidth <= 0)
					gw = "GridBagConstraints." + getPropertyValue("gridwidth");

				String gh = "" + gbc.gridheight;
				if (gbc.gridheight <= 0)
					gh = "GridBagConstraints." + getPropertyValue("gridheight");

				return "new GridBagConstraints("
					+ getPropertyValue("gridx")
					+ ", "
					+ getPropertyValue("gridy")
					+ ", "
					+ gw
					+ ", "
					+ gh
					+ ", "
					+ gbc.weightx
					+ ", "
					+ gbc.weighty
					+ ", "
					+ "GridBagConstraints."
					+ getPropertyValue("anchor")
					+ ", "
					+ "GridBagConstraints."
					+ getPropertyValue("fill")
					+ ", new Insets("
					+ insets.top
					+ ", "
					+ insets.left
					+ ", "
					+ insets.bottom
					+ ", "
					+ insets.right
					+ "), "
					+ gbc.ipadx
					+ ", "
					+ gbc.ipady
					+ ")";
			} else if (layoutData instanceof CellConstraints) {
			    CellConstraints cc = (CellConstraints) layoutData;

                jcm.addImport("com.jgoodies.forms.layout.CellConstraints");
                String param = cc.gridX + ", " + cc.gridY + ", " + cc.gridWidth
                        + ", " + cc.gridHeight + ", ";
                
                Insets in = cc.insets;
			    if(in != null && ( in.bottom != 0 || in.top != 0 || in.left != 0 || in.right != 0)) {
	                jcm.addImport("java.awt.Insets");
	                param += getFormAlignAsString(cc.hAlign)+", "
	                	+getFormAlignAsString(cc.vAlign);
	                param+=", new Insets("+in.top+", "+in.left+", "+in.bottom+", "+in.right+")";
			    } else {
			        param = "\""+param+ cc.hAlign + ", " + cc.vAlign+"\"";
			    }
                return "new CellConstraints("+param+")";
			} else if (layoutData instanceof AnchorConstraint) {
				AnchorConstraint ac = (AnchorConstraint) layoutData;
				jcm.addImport("com.cloudgarden.layout.AnchorConstraint");
				updateUI = true;
				return "new AnchorConstraint("
					+ ac.top
					+ ", "
					+ ac.right
					+ ", "
					+ ac.bottom
					+ ", "
					+ ac.left
					+ ", "
					+ "AnchorConstraint."
					+ getPropertyValue("topType")
					+ ", "
					+ "AnchorConstraint."
					+ getPropertyValue("rightType")
					+ ", "
					+ "AnchorConstraint."
					+ getPropertyValue("bottomType")
					+ ", "
					+ "AnchorConstraint."
					+ getPropertyValue("leftType")
				//+ ac.topType
				//+", " + ac.rightType + ", " + ac.bottomType + ", " + ac.leftType 
				+")";
				
			} else if (layoutData != null 
			        && PaneLayoutHandler.handlesLayout(comp.getParent())) {
				updateUI = true;
				return PaneLayoutHandler.getJavaCode(this, jcm);
				
			} else if (layoutData != null 
			        && XYLayoutHandler.handlesLayout(comp.getParent())) {
				updateUI = true;
				return XYLayoutHandler.getJavaCode(this, jcm);
				
			} else if(layoutData != null) {
			    //for custom layouts
			    String pdc = comp.getEditor().getPredefinedConstructor(layoutData);
			    if(pdc != null)
			        return pdc;
			    
			    if(constraintCode != null)
			        return constraintCode;
			    
			    return "CONSTRAINT_NOT_HANDLED";
			}
		}

		Class mc = getMainClass();
		if (mc == null && !comp.isPropertySet("size")) {
			updateUI = true;
			return "";
		}

		String dataName = name + "LData";
		if (layoutData instanceof FormAttachment) {
		    try {
			Integer num = getIntegerProp("numerator");
			Integer denom = getIntegerProp("denominator");
			Integer offset = getIntegerProp("offset");
			FieldWrapper ali = getFieldProp("alignment");
			Object control = layoutDataSrc.getPropertyValue("control");
			jcm.addImport(FormAttachment.class.getName());
			code = " new FormAttachment(";
			if (control != null 
					&& !"null".equals(control.toString()) 
					&& !"".equals(control.toString())) {
				code += control.toString();
				if (offset != null) {
					code += ", " + offset;
					if (ali != null && ((Integer) ali.getValue()).intValue() != -1)
						code += ", " + ali.getFieldAsString();
				}
			} else {
				if (num != null && offset != null) {
					if (denom != null) {
						code += num + ", " + denom + ", " + offset;
					} else {
						code += num + ", " + offset;
					}
				} else {
					updateUI = true;
					return "";
				}
			}
			code += ")";
			updateUI = true;
			return code;
		    } catch(Throwable t) {
		        t.printStackTrace();
		        return "";
		    }
		}

		//use next block to set RowData, since RowData only has width & height
		if (mc != null && !mc.equals(RowData.class)) {

			//			if (comp.isPropertySet("size")) {
			//				Point asize = comp.getAdjustedSize();
			//				if (mc.equals(GridData.class)) {
			//					setPropertyValue("widthHint", new Integer(asize.x));
			//					setPropertyValue("heightHint", new Integer(asize.y));
			//				} else {
			//					setPropertyValue("width", new Integer(asize.x));
			//					setPropertyValue("height", new Integer(asize.y));
			//				}
			//			} else {
			//				resetPropertyValue("widthHint");
			//				resetPropertyValue("heightHint");
			//				resetPropertyValue("width");
			//				resetPropertyValue("height");
			//			}

		    if(layoutDataSrc == null) {
				updateUI = true;
		        return "";
		    }
		    
			String propCode = layoutDataSrc.getJavaCodeForProps(dataName, isSwing, jcm);

			if ("".equals(propCode)) {
				updateUI = true;
				return "";
			}

			String mcName = mc.getName();
			jcm.addImport(mcName);
			mcName = mcName.substring(mcName.lastIndexOf(".") + 1);
			code += "\t\t" + mcName + " " + dataName + " = new " + mcName + "();\n";
			code += propCode;
			code += "\t\t" + name + ".setLayoutData(" + dataName + ");\n";
			updateUI = true;
			return code;
		}

		if (comp.isPropertySet("size")) {
			Point sz = comp.getSize();
			sz = comp.adjustSize(sz, comp.getControl());
			String lt = comp.getParent().getLayoutWrapper().getLayoutType();
			if ("Row".equals(lt)) {
				jcm.addImport(RowData.class.getName());
				code += "\t\tRowData " + dataName + " = new RowData(" + sz.x + ", " + sz.y + ");\n";
			} else if ("Grid".equals(lt)) {
				jcm.addImport(GridData.class.getName());
				code += "\t\tGridData " + dataName + " = new GridData();\n";
				code += "\t\t" + dataName + ".widthHint = " + sz.x + ";\n";
				code += "\t\t" + dataName + ".heightHint = " + sz.y + ";\n";
			} else if ("Form".equals(lt)) {
				jcm.addImport(FormData.class.getName());
				code += "\t\tFormData " + dataName + " = new FormData(" + sz.x + ", " + sz.y + ");\n";
			} else {
				updateUI = true;
				return "";
			}
			code += "\t\t" + name + ".setLayoutData(" + dataName + ");\n";
		}
		updateUI = true;
		return code;
	}

	public String toString() {
		if (comp == null) {
			if (FormAttachment.class.equals(getMainClass()))
				return "Form";
		}

		if (getParent() == null)
			return "No Constraints";
		String lt = getParent().getSuperLayoutType();
		if (getParent().isSwing()) {
			if ("Border".equals(lt)) {
				return "Border";
			} else if ("Enfin".equals(lt)) {
				return "Enfin";
			} else if ("GridBag".equals(lt)) {
				return "GridBag";
			} else if ("Anchor".equals(lt)) {
				return "Anchor";
			} else if ("Card".equals(lt)) {
				return "Card";
			} else if (getParent().getComponent() instanceof JSplitPane) {
				return "JSplitPane";
			}
		} else {
			if ("Grid".equals(lt)) {
				return "Grid";
				//} else if ("Row".equals(lt)) {
				//return "Row";
			} else if ("Form".equals(lt)) {
				return "Form";
			} else if (layoutData != null) {
				return getShortClassName(layoutData);
			}
		}
		//for custom layouts
		if(lt != null)
		    return lt;
		
		return "No Constraints";
	}

	private String getShortClassName(Object obj) {
		String className = obj.getClass().getName();
		int pos = className.lastIndexOf(".");
		return className.substring(pos + 1);
	}

	public Object getEditableValue() {
		return this;
	}

	public FormComponent getParent() {
		if (comp == null)
			return null;
		return comp.getParent();
	}

	private IPropertyDescriptor[] pdescs;
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
	    
	    //this causes FormAttachments to not have any pdescs if
	    //attached to a control - also can cause problems when changing layout types
//	    if(pdescs != null && pdescs.length > 0)
//	        return pdescs;
	    
	    IPropertyDescriptor[] descs = new IPropertyDescriptor[0];
		if (getParent() == null) {
			pdescs = descs;
		    return descs;
		}
		String lt = getParent().getSuperLayoutType();
		
		validate();
		
		if (getParent().isSwing()) {
			if ("GridBag".equals(lt)) {
				if (!(layoutData instanceof GridBagConstraints)) {
					layoutData = new GridBagConstraints();
				}
				if(layoutDataSrc == null)
				    layoutDataSrc = new FormPropertySource(comp, null);
				layoutDataSrc.setObject(layoutData);
				descs = layoutDataSrc.getPropertyDescriptors();
			} else if ("Anchor".equals(lt)) {
				if (!(layoutData instanceof AnchorConstraint)) {
					layoutData = new AnchorConstraint();
				}
				if(layoutDataSrc == null)
				    layoutDataSrc = new FormPropertySource(comp, null);
				layoutDataSrc.setObject(layoutData);
				descs = layoutDataSrc.getPropertyDescriptors();
			} else if ("Border".equals(lt)) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				descs[0] =
					new ChoicePropertyDescriptor(
						"direction",
						"direction",
						comp,
						new String[] {
							BorderLayout.NORTH,
							BorderLayout.SOUTH,
							BorderLayout.EAST,
							BorderLayout.WEST,
							BorderLayout.CENTER },
						this);
			} else if ("Enfin".equals(lt) && getParent().getEditor().canUseEnfinLayout()) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				descs[0] =
				    new ChoicePropertyDescriptor(
				            EnfinLayoutHandler.name,
				            EnfinLayoutHandler.name,
				            comp,
		                    EnfinLayoutHandler.optionNames,
				            this);
			} else if ("Table".equals(lt)) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				descs[0] =
					new TextFormPropertyDescriptor("constraint",
						"constraint", comp);
			} else if ("Card".equals(lt)) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				descs[0] = new TextFormPropertyDescriptor("CardName", "CardName", comp);
			} else if (getParent().isSubclassOf(JSplitPane.class)) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				descs[0] =
					new ChoicePropertyDescriptor(
						"position",
						"position",
						comp,
						new String[] { JSplitPane.LEFT, JSplitPane.RIGHT, JSplitPane.TOP, JSplitPane.BOTTOM },
						this);
				
			} else if (getParent().isSubclassOf(JLayeredPane.class)) {
				layoutDataSrc = null;
				descs = new IPropertyDescriptor[1];
				
				Vector bgs = new Vector();
				Vector nonVis = comp.getEditor().getComponents();
				for (int i = 0; i < nonVis.size(); i++) {
					FormComponent fc = (FormComponent) nonVis.elementAt(i);
					if (!fc.isInherited() && fc.isSubclassOf(Integer.class) && bgs.size() < 10) {
						bgs.add(fc);
					}
				}
				String[] choices = new String[6+bgs.size()];
				choices[0] = "DEFAULT_LAYER";
				choices[1] = "DRAG_LAYER";
				choices[2] = "FRAME_CONTENT_LAYER";
				choices[3] = "MODAL_LAYER";
				choices[4] = "PALETTE_LAYER";
				choices[5] = "POPUP_LAYER";
				for (int i = 6; i < choices.length; i++) {
					choices[i] = ((FormComponent) bgs.get(i-6)).getNameInCode();
				}
				
				descs[0] =
					new ChoicePropertyDescriptor(
						"layer",
						"layer",
						comp,
						choices,
						this);
				
			} else if (PaneLayoutHandler.handlesLayout(getParent())) {
				if(layoutDataSrc == null)
				    layoutDataSrc = new FormPropertySource(comp, null);
	            layoutDataSrc.setObject(layoutData);
	            descs = layoutDataSrc.getPropertyDescriptors(this);
			    PaneLayoutHandler.prepPropertyDescriptors(descs, this);
			    
			} else if ( layoutData != null) {
	            //for custom layouts
				if(layoutDataSrc == null)
				    layoutDataSrc = new FormPropertySource(comp, null);
	            layoutDataSrc.setObject(layoutData);
	            descs = layoutDataSrc.getPropertyDescriptors(this);
			}
		} else if (getParent().isSWT()) {
			if(layoutDataSrc == null)
			    layoutDataSrc = new FormPropertySource(comp, null);
			descs = layoutDataSrc.getPropertyDescriptors(this);
			if (layoutData instanceof FormAttachment) {
				for (int i = 0; i < descs.length; i++) {
					String id = (String) descs[i].getId();
					if (id.equals("control")) {
					    descs[i] = new SiblingPropertyDescriptor(id, id, comp, this, true, true, false);
					}
				}
			} else if ("Grid".equals(lt)) {
				if (!(layoutData instanceof GridData)) {
					layoutData = new GridData();
					layoutDataSrc.setObject(layoutData);
				}
				descs = layoutDataSrc.getPropertyDescriptors(this);
			} else if ("Row".equals(lt)) {
				if (!(layoutData instanceof RowData))
					layoutData = new RowData();
				layoutDataSrc.setObject(layoutData);
				descs = layoutDataSrc.getPropertyDescriptors();
			} else if ("Form".equals(lt)) {
				if (!(layoutData instanceof FormData)) {
					layoutData = new FormData();
				}
				layoutDataSrc.setObject(layoutData);
				descs = layoutDataSrc.getPropertyDescriptors(this);
				for (int i = 0; i < descs.length; i++) {
					String id = (String) descs[i].getId();
					if (id.equals("bottom") || id.equals("top") || id.equals("left") || id.equals("right")) {
						descs[i] = new FormPropertyDescriptor(id, id, comp, this);
					}
				}
			} else if ( layoutData != null) {
	            //for custom layouts
	            layoutDataSrc.setObject(layoutData);
	            descs = layoutDataSrc.getPropertyDescriptors(this);
	        }

		}
		if (layoutData != null)
			mainClass = layoutData.getClass();
		pdescs = descs;
		return descs;
	}

	public void populateDOMNode(Element node, Document document, String indent) {
		populateDOMNode(node, document, indent, true);
	}

	private void populateDOMNode(Element node, Document document, String indent, boolean newNode) {
		Element constNode = node;
		String nind = "\n" + indent;
		String nind2 = nind + XMLWriter.INDENT;
		if (newNode) {
			constNode = document.createElement("LayoutConstraint");
			node.appendChild(document.createTextNode(nind));
			node.appendChild(constNode);
		}
		IPropertyDescriptor[] lprops = getPropertyDescriptors();
		if (lprops == null || lprops.length == 0)
			return;
		if (getMainClass() != null)
			constNode.setAttribute("class", getMainClass().getName());
		for (int i = 0; i < lprops.length; i++) {
			Object id = lprops[i].getId();
			if (isPropertySet(id)) {
				Object val = getPropertyValue(id);
				Element lprop = document.createElement("Property");
				constNode.appendChild(document.createTextNode(nind2));
				constNode.appendChild(lprop);
				lprop.setAttribute("name", (String) id);
				if (val instanceof LayoutDataWrapper) {
					LayoutDataWrapper ldw = (LayoutDataWrapper) val;
					ldw.populateDOMNode(lprop, document, indent + XMLWriter.INDENT, false);
				} else {
					XMLWriter.setElementValue(lprop, val, document, indent);
				}
			}
		}
		constNode.appendChild(document.createTextNode(nind));
	}

	public Object getPropertyValue(Object id) {
	    
	    if(comp != null && comp.getParent() != null && comp.getParent().usesGroupLayout()) {
	        if(id.equals(LayoutGroup.ANCHOR_LEFT)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getHGroup().isAnchored(comp, true));
	        } else if(id.equals(LayoutGroup.ANCHOR_TOP)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getVGroup().isAnchored(comp, true));
	        } else if(id.equals(LayoutGroup.ANCHOR_RIGHT)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getHGroup().isAnchored(comp, false));
	        } else if(id.equals(LayoutGroup.ANCHOR_BOTTOM)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getVGroup().isAnchored(comp, false));
	        } else if(id.equals(LayoutGroup.EXPAND_HOR)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getHGroup().isExpandable(comp));
	        } else if(id.equals(LayoutGroup.EXPAND_VER)) {
	            return new Boolean(comp.getParent().getLayoutWrapper().getVGroup().isExpandable(comp));
	        } else if(id.equals(LayoutGroup.SET_DEFAULT_WIDTH)) {
	            return null;
	        } else if(id.equals(LayoutGroup.SET_DEFAULT_HEIGHT)) {
	            return null;
	        }
	    }
	    //make sure everything is initialized
	    getPropertyDescriptors();
		return getPropertyValue(id, false);
	}
	
	private void validate() {
        if (comp == null)
            return;
        String lt = comp.getParentSuperLayoutType();
        if (lt == null)
            return;
        if (comp.isSwing()) {
            if (lt.equals("GridBag")) {
                if (!(layoutData instanceof GridBagConstraints)) {
                    GridBagConstraints gbc = new GridBagConstraints();
                    int ind = comp.getNonInheritedIndexInParent();
                    if (ind < 0)
                        ind = 0;
                    gbc.gridx = ind % 4;
                    gbc.gridy = ind / 4;
                    layoutData = gbc;
                }
                if (layoutDataSrc == null)
                    layoutDataSrc = new FormPropertySource(comp, null);
                layoutDataSrc.setObject(layoutData);
            } else if (lt.equals("Border")) {
                if (!(layoutData instanceof String)) {
                    int ind = comp.getNonInheritedIndexInParent();
                    if (ind == 0)
                    	layoutData = BorderLayout.NORTH;
                    else if (ind == 1)
                    	layoutData = BorderLayout.WEST;
                    else if (ind == 2)
                    	layoutData = BorderLayout.CENTER;
                    else if (ind == 3)
                    	layoutData = BorderLayout.EAST;
                    else if (ind == 4)
                    	layoutData = BorderLayout.SOUTH;
                    else
                    	layoutData = BorderLayout.CENTER;
                }
                layoutDataSrc = null;
            } else if (lt.equals("Card")) {
                if (!(layoutData instanceof String))
                	layoutData = comp.getNameInCode();
                layoutDataSrc = null;
            } else if (lt.equals("Group")) {
                layoutDataSrc = null;
            } else if (comp.getParent().isJSplitPane()) {
                if (!(layoutData instanceof String)) {
                    int ind = comp.getNonInheritedIndexInParent();
                    if (ind == 0)
                    	layoutData = JSplitPane.LEFT;
                    else
                    	layoutData = JSplitPane.RIGHT;
                }
                layoutDataSrc = null;
            } else if (comp.getParent().isSubclassOf(JLayeredPane.class)) {
//            	if (!(layoutData instanceof Integer)) {
//            		layoutData = JLayeredPane.DEFAULT_LAYER;
//            	}
                layoutDataSrc = null;
            } else if (comp.getParent().isJTabbedPane()) {
                if (!(layoutData instanceof String))
                	layoutData = comp.getNameInCode();
                layoutDataSrc = null;
            }
        }
    }
	
	public int getIntProperty(Object id) {
	    Object val = getPropertyValue(id);
	    if(val == null)
	    	return -1;
	    if(val instanceof FieldWrapper)
	    	val = ((FieldWrapper)val).getValue();
	    if(val instanceof Integer)
	    	return ((Integer)val).intValue();
    	return -1;
	}
	
	public Object getPropertyValue(Object id, boolean allowNulls) {
	    
	    validate();

	    if(layoutData instanceof String) {
	        return layoutData;
	    }
	    
	    if(layoutData instanceof JLayerWrapper) {
	    	return ((JLayerWrapper)layoutData).getDisplayValue();
	    }
	    
	    if(layoutDataSrc == null) {
	    	return null;
	    }
	    
		if (layoutDataSrc.getObject() == null) {
			Object ldsv = layoutDataSrc.getPropertyValue(id);
			if (ldsv != null)
				return ldsv;
		} else {

			if (layoutData instanceof FormData) {

				Object val = layoutDataSrc.getPropertyValue(id);

				if (val != null && !"null".equals(val)) {
					if (val instanceof FormAttachment) {
						val = new LayoutDataWrapper(val, comp);
						setPropertyValue(id, val);
					}
					return val;
				}
				if (allowNulls) {
					return null;
				}
				//handle "val is null" case
				Rectangle b = null;
				if (comp != null)
					b = comp.getBounds();
				else
					b = new Rectangle(0, 0, 100, 60);
				FormData fd = (FormData) layoutData;
				int w = fd.width;
				int h = fd.height;

				FormAttachment fa = null;
				if (id.equals("top")) {
					fa = new FormAttachment(0, 1000, b.y);
				} else if (id.equals("left")) {
					fa = new FormAttachment(0, 1000, b.x);
				} else if (id.equals("bottom")) {
					fa = new FormAttachment(0, 1000, b.y + b.height);
				} else if (id.equals("right")) {
					fa = new FormAttachment(0, 1000, b.x + b.width);
				}

				if (fa != null) {
					LayoutDataWrapper ldw = new LayoutDataWrapper(fa, comp);
					return ldw;
				}

			}

			return layoutDataSrc.getPropertyValue(id);
		}
		return null;
	}

	public boolean isSet() {
		//for MigLayout
//		if(constraint != null)
//			return true;
	    if(layoutData instanceof String)
	        return true;
		return layoutDataSrc != null &&  layoutDataSrc.isSet();
	}

	public boolean isRawPropertySet(Object id) {
	    if(layoutDataSrc == null)
	        return false;
	    
		//if (true)
		//return true;

		//		System.err.println(
		//			"LDW IS SET PROP "
		//				+ id
		//				+ ", obj="
		//				+ layoutDataSrc.getObject()
		//				+ ", "
		//				+ comp
		//				+ ", "
		//				+ layoutDataSrc.isPropertySet(id));
	    
		Object val = layoutDataSrc.getPropertyValue(id);
		
		//if(true)
		   // return (val != null && !"null".equals(val));
		if(val == null || val.equals("null"))
		    return false;
		return true;
	}
	
	public boolean isPropertySet(Object id) {

		if(!isRawPropertySet(id))
			return false;

		if (id.equals("top"))
			return comp.isSideAnchored(0);
		if (id.equals("right"))
			return comp.isSideAnchored(1);
		if (id.equals("bottom"))
			return comp.isSideAnchored(2);
		if (id.equals("left"))
			return comp.isSideAnchored(3);

		//if (id.equals("top") || id.equals("bottom") || id.equals("left") || id.equals("right"))
		//return getPropertyValue(id, true) != null;

		//return true;
		if (layoutDataSrc.getObject() != null) {
			return layoutDataSrc.isPropertySet(id);
		}
		return true;
	}

	public void reset() {
	    if(layoutDataSrc == null)
	        return;
		layoutDataSrc.reset();
	}

	public void resetPropertyValue(Object id) {
	    if(layoutDataSrc == null)
	        return;
		if (layoutDataSrc.getObject() != null) {
			layoutDataSrc.resetPropertyValue(id);
		}
	}

	public void setPropertyValue(Object id, Object value) {
		setPropertyValue(id, value, updateUI);
	}

	public boolean isGBConstraint() {
	    if(layoutDataSrc == null)
	        return false;
		return GridBagConstraints.class.equals(mainClass);
//		return jiglooPlugin.canUseSwing() && layoutDataSrc.getObject() instanceof GridBagConstraints;
	}

	public boolean isAnchorConstraint() {
	    if(layoutDataSrc == null)
	        return false;
		return AnchorConstraint.class.equals(mainClass);
//		return jiglooPlugin.canUseSwing() && layoutDataSrc.getObject() instanceof AnchorConstraint;
	}

	public boolean isCellConstraint() {
	    if(layoutDataSrc == null)
	        return false;
		return CellConstraints.class.equals(mainClass);
//		return jiglooPlugin.canUseSwing() && layoutDataSrc.getObject() instanceof CellConstraints;
	}

	public void setAllPropertiesSet() {
		Vector propNames = getPropertyNames();
		if (propNames != null) {
			for (int n = 0; n < propNames.size(); n++) {
				String pName = (String) propNames.elementAt(n);
				layoutDataSrc.setPropertySet(pName, true);
			}
		}
	}

	public boolean usesSimpleConstraint() {
		if(comp != null
				&& comp.getParent() != null 
				&& MigLayoutHandler.handlesLayout(comp.getParent()))
			return true;
	    if(comp == null || comp.isSWT() || isAnchorConstraint() || isGBConstraint() || isCellConstraint()
	    		//for custom layouts
	    		|| layoutData != null)
	    	return false;
	    return true;
	}

	public void setPropertyValue(Object id, Object value, boolean doUpdateUI) {

		if (value == null)
			return;

		if(comp != null && "Table".equals(comp.getParentLayoutType()) && value instanceof String) {
			try {
				String val = (String)value;
				String lc = (String) comp.getLayoutDataWrapper().getLayoutData();
				if(lc != null) {
					lc = JiglooUtils.replace(lc, " ", "");
					val = val.toLowerCase();
					val = JiglooUtils.replace(val, " ", "");
					String[] parts = JiglooUtils.split(",", lc);
					int len = parts.length;
					boolean is4c = len == 4 && !JiglooUtils.isInteger(parts[2]);
					boolean is4 = len == 4 && !is4c;
					boolean is6 = len == 6;

					if(len < 2)
						lc = "1,1";
					else
						lc = parts[0]+","+parts[1];
					if(is4 || len == 6)
						lc +=","+ parts[2] +","+parts[3];

					boolean setAlign = false;

					if("center-horiz".equals(val)) {
						setAlign = true;
						lc+=",c";
					} else if ("left".equals(val)) {
						setAlign = true;
						lc+=",l";
					} else if ("right".equals(val)) {
						setAlign = true;
						lc+=",r";
					} else if ("fill-horiz".equals(val)) {
						setAlign = true;
						lc+=",f";
					} else {
						if(len == 6)
							lc += ","+parts[4];
						else if(is4c)
							lc += ","+parts[2];
						else
							lc += ",f";
					}

					if("center-vert".equals(val)) {
						setAlign = true;
						lc+=",c";
					} else if ("top".equals(val)) {
						setAlign = true;
						lc+=",t";
					} else if ("bottom".equals(val)) {
						setAlign = true;
						lc+=",b";
					} else if ("fill-vert".equals(val)) {
						setAlign = true;
						lc+=",f";
					} else {
						if(len == 6)
							lc += ","+parts[5];
						else if(is4c)
							lc += ","+parts[3];
						else
							lc += ",f";
					}
					if(setAlign)
						val = lc;
					else {
						val = JiglooUtils.replace(val, " ", "");
						String[] parts2 = JiglooUtils.split(",", val);
						int len2 = parts2.length;
						boolean is4c2 = len2 == 4 && !JiglooUtils.isInteger(parts2[2]);
						boolean is22 = len2 == 2;
						boolean is42 = len2 == 4 && !is4c2;
						boolean is62 = len2 == 6;

						if(!is4c2 && ! is62) {
							if(is4c && (is22 ||  is42))
								val += ","+parts[2]+","+parts[3];
							else if(is6 && (is22 ||  is42))
								val += ","+parts[4]+","+parts[5];
						}
					}
				}
				value = val;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		if (id.equals("control")) {
			FormComponent fc = null;
			if (value instanceof String && comp.getEditor() != null) {
				fc = comp.getEditor().getComponentByName(value.toString());
			} else if (value instanceof FormComponent) {
				fc = (FormComponent) value;
			}
			if (fc != null)
				value = new FormComponentWrapper(fc);
		}

		if (id.equals("horizontalAlignment") && isFill(value)) {
			layoutDataSrc.setPropertyValue("widthHint", new Integer(-1));
		}
		if (id.equals("verticalAlignment") && isFill(value)) {
			layoutDataSrc.setPropertyValue("heightHint", new Integer(-1));
		}

		//TODO include grabExcessVerticalSpace (& Horiz) in determination
		if (id.equals("widthHint")) {
			if (isFill(getPropertyValue("horizontalAlignment"))) {
				value = new Integer(-1);
			}
		}
		if (id.equals("heightHint")) {
			if (isFill(getPropertyValue("verticalAlignment"))) {
				value = new Integer(-1);
			}
		}

	    if(layoutData instanceof JLayerWrapper) {
	    	((JLayerWrapper)layoutData).setValue(value);
	    }
	    
		if(layoutData instanceof String)
			layoutData = value;
		else if(layoutData == null) {
			layoutData = value;
		} else if(layoutDataSrc != null)
			layoutDataSrc.setPropertyValue(id, value);

		if (comp != null && comp.getEditor() != null) {
			if (doUpdateUI) {
				comp.notifyListeners(true, false);
			}
		}
	}

	public boolean equals(Object o) {
		if (o instanceof LayoutDataWrapper) {
			LayoutDataWrapper lw = (LayoutDataWrapper) o;
			//this doesn't work yet!
			//System.out.println("LDW EQUALS "+lw.getConstraint()+", "+getConstraint()+", "+comp+", "+lw.getFormComponent());
			
//			if (lw.getConstraint() != null && !lw.getConstraint().equals(getConstraint()))
//				return false;
			
			Class lwmc = lw.getMainClass();
			//this is the case for Border, JSplitPane constraints
			if (lwmc == null && getMainClass() == null)
				return false;
			if (lwmc == null)
				return getMainClass() == null;
			if (!lwmc.equals(getMainClass())) {
				return false;
			}
			
			//if(lwmc.equals(FormAttachment.class) || lwmc.equals(FormData.class) )
			   // return false;
			if(layoutDataSrc == null)
			    return false;
			
			if(layoutData instanceof JLayerWrapper 
					&& lw.getLayoutData() instanceof JLayerWrapper) {
				return ((JLayerWrapper)layoutData).getDisplayValue().equals(
						((JLayerWrapper)lw.getLayoutData()).getDisplayValue());
			}
			
			Vector pns = getPropertyNames();
			for (int i = 0; i < pns.size(); i++) {
				Object id = pns.elementAt(i);
				Object att = getPropertyValue(id);
				Object att2 = lw.getPropertyValue(id);
//				System.out.println("testing " + id + ":" + att + ":" + att2 + ":");
				if ((att == null && att2 != null))
					return false;
				if ((att2 == null && att != null))
					return false;
				if (att != null && att2 != null) {
				    if(att instanceof LayoutDataWrapper && att2 instanceof LayoutDataWrapper)
				        return false;
//				        return att.equals(att2);
					if (!stringsEqual(att.toString(), att2.toString()))
						return false;
				}
			}
			//System.out.println("LWS EQUAL");
			return true;
		}
		return false;
	}

	private boolean stringsEqual(String s1, String s2) {
		if (s1.equals(s2))
			return true;
		if (s1.equals("[  ]") && s2.equals("null"))
			return true;
		if (s2.equals("[  ]") && s1.equals("null"))
			return true;
		return false;
	}

	private boolean isFill(Object value) {
		if (value.equals(FILL))
			return true;
		if (value.toString().equals("FILL"))
			return true;
		return false;
	}
	private boolean grabHorizontal() {
		return Boolean.TRUE.equals(getPropertyValue("grabExcessHorizontalSpace"));
	}
	private boolean grabVertical() {
		return Boolean.TRUE.equals(getPropertyValue("grabExcessVerticalSpace"));
	}

	public Vector getPropertyNames() {
	    if(layoutDataSrc == null)
	        return null;
		return layoutDataSrc.getPropertyNames();
	}

	public String getJavaCodeForPropertySetter(String pName, IJavaCodeManager jcm) {
		//System.out.println("GET JAVA CODE FOR LAYOUTDATA PROP " + pName);
		Object value = getPropertyValue(pName);
		if ("null".equals(value)) {
			value = null;
		}
		return FormPropertySource.getJavaCodeForProperty(pName, getNameInCode(), 
		        value, true, false, false, true, null, jcm);
		/*
		if (propertyTypes != null && isPropertySet(pName) && !isSyntheticProperty(pName)) {
			Object value = getPropertyValue(pName);
			if ("null".equals(value) && !propertyTypes.get(pName).equals(String.class)) {
				value = null;
			}
			return getJavaCodeForProperty(pName, getNameInCode(), value, isField(pName), isSwing, false, null, jcm);
		}
		return new String[] { "", "" };
		*/
	}

	public String getJavaConstructor(IJavaCodeManager jcm) {
		String code = "";

		if (comp.getParent() == null)
			return "";

		if (layoutData instanceof FormAttachment) {
			Integer num = (Integer) layoutDataSrc.getPropertyValue("numerator");
			Integer denom = (Integer) layoutDataSrc.getPropertyValue("denominator");
			Integer offset = (Integer) layoutDataSrc.getPropertyValue("offset");
			FieldWrapper ali = (FieldWrapper) layoutDataSrc.getPropertyValue("alignment");
			Object control = layoutDataSrc.getPropertyValue("control");
			jcm.addImport(FormAttachment.class.getName());
			code = " new FormAttachment(";
			if (control != null && !"null".equals(control.toString())) {
				code += control.toString();
				if (offset != null) {
					code += ", " + offset;
					if (ali != null && ((Integer) ali.getValue()).intValue() != -1)
						code += ", " + ali.getValue();
				}
			} else {
				if (num != null && offset != null) {
					if (denom != null) {
						code += num + ", " + denom + ", " + offset;
					} else {
						code += num + ", " + offset;
					}
				} else {
					return "";
				}
			}
			code += ")";
			return code;
		}

		String dataName = getNameInCode();
		Class mc = getMainClass();
		String lt = comp.getParent().getLayoutWrapper().getLayoutType();
		
		if("Fill".equals(lt))
		    return "";
		if("Mig".equals(lt))
		    return "";
		
		if(layoutData instanceof JLayerWrapper)
			return "";
		
		if (comp.isPropertySet("size") && ("Row".equals(lt) || "Form".equals(lt))) {
			Point sz = comp.getSize();
			sz = comp.adjustSize(sz, comp.getControl());
			if ("Row".equals(lt)) {
				jcm.addImport(RowData.class.getName());
				code = "RowData " + dataName + " = new RowData(" + sz.x + ", " + sz.y + ");\n";
			} else if ("Form".equals(lt)) {
				jcm.addImport(FormData.class.getName());
				code = "FormData " + dataName + " = new FormData(" + sz.x + ", " + sz.y + ");\n";
			}
		} else if (mc != null) {
			String mcName = mc.getName();
			jcm.addImport(mcName);
			mcName = mcName.substring(mcName.lastIndexOf(".") + 1);
			code = mcName + " " + dataName + " = new " + mcName + "();";
		}
		return code;

		//return layoutDataSrc.getJavaConstructor(jcm);
	}

	/* (non-Javadoc)
	 * @see com.cloudgarden.jigloo.properties.sources.IFormPropertySource#hasProperty(java.lang.String)
	 */
	public boolean hasProperty(String id) {
	    if(layoutDataSrc == null)
	        return false;

		return layoutDataSrc.hasProperty(id);
	}

	public void setName(String name) {
		//System.err.println("LDW: SET NAME "+name+", "+comp.getName()+" @"+hashCode());
		this.name = name;
	}

	public String getNameInCode() {
		String name = getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0)
			name = name.substring(pos + 1);
		return name;
	}

	public String getName() {
		//System.out.println("LW: GET NAME "+super.getName());
		if (name != null)
			return name;
		name = comp.getNonBlockName() + "LData";
		JavaCodeParser jcp = comp.getEditor().getJavaCodeParser();
		if (jcp != null)
			name = jcp.getNextAvailableName(name);
		return name;
	}

	private String blockName = null;

	public void setBlockName(String bn) {
		blockName = bn;
	}

	public String getBlockName() {
		if (blockName != null)
			return blockName;
		String n = getName();
		int pos = n.lastIndexOf(".");
		if (pos < 0)
			return "";
		return n.substring(0, pos + 1);
	}

	public void updateInCode() {
		if (!comp.getEditor().updatesJavaCode())
			return;
		comp.getEditor().getJavaCodeParser().updateInCode(this);
	}

	public void updateInCode(String propName) {
		if (!comp.getEditor().updatesJavaCode())
			return;
		comp.getEditor().getJavaCodeParser().updateInCode(this, propName);
	}

	public void repairInCode() {
		if (!comp.getEditor().updatesJavaCode())
			return;
		comp.getEditor().getJavaCodeParser().repairInCode(this, JavaCodeParser.REPAIR_ALL);
	}

	public boolean needsUpdateInCode(String propName) {
		return true;
	}

	public boolean isSyntheticProperty(String id) {
		return false;
	}

    public void setConstraintCode(String constraintCode) {
        this.constraintCode = constraintCode;
    }
}
