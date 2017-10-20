/*
 * Created on Jun 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.cache.Cacher;
import com.cloudgarden.jigloo.eval.ConstructorHolder;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.groupLayout.GroupLayout;
import com.cloudgarden.jigloo.groupLayoutSupport.LayoutGroup;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.AbsoluteLayout;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.properties.descriptors.ChoicePropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.FormPropertySource;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.DefaultValueManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.util.SwingHelper;
import com.cloudgarden.jigloo.xml.XMLWriter;
import com.cloudgarden.layout.AnchorLayout;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LayoutWrapper extends FormPropertySource {

	public static final String FORM_LO_DEF_SPEC = 
		"max(5dlu;p),max(5dlu;p),max(5dlu;p),max(5dlu;p)";

	private String layoutType;

	private HashMap attributes = new HashMap();

	private Class layoutClass;

	private boolean isSwing;

	private Container container = null;

	private boolean inited = false;

	private boolean isCustom = false;

	//indicates if the LW was set before re-parsing
	private boolean wasSet = false;

	private boolean isSet = false;

	private boolean isExplicitlySet = true;
	
	private LayoutGroup hGroup = null;
	private LayoutGroup vGroup = null;
	private Vector hLinkedElements = null;
	private Vector vLinkedElements = null;
	
	public static final boolean SUPPORT_GROUP_LAYOUT =true;

	private String[] swingTypes = { null, null, "Anchor", "Box", "Border", "Flow",
            "Form", "Grid", "GridBag", "Card", "Absolute", "Table", "Mig" };
	
	private static String[] swtTypes = { "Fill", "Form", "Grid", "Row", "Grid",
			"Stack", "Absolute", "Mig" };

	public LayoutWrapper(FormComponent comp) {
		super(comp);
        if (SUPPORT_GROUP_LAYOUT)
            swingTypes[0] = "Group";
        if (jiglooPlugin.getActiveEditor() != null && jiglooPlugin.getActiveEditor().canUseEnfinLayout())
            swingTypes[1] = "Enfin";
		this.isSwing = comp.isSwing();
		try {
			init();
			//call initProperties, otherwise "columnWidths" etc doesn't get set for inherited layout
			initProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LayoutWrapper(FormComponent comp, String layoutType, boolean isSwing) {
		super(comp);
		this.isSwing = isSwing;
        if (SUPPORT_GROUP_LAYOUT)
            swingTypes[0] = "Group";
        if (jiglooPlugin.getActiveEditor() != null && jiglooPlugin.getActiveEditor().canUseEnfinLayout())
            swingTypes[1] = "Enfin";
		setLayoutType(layoutType);
		inited = true;
	}

	public LayoutWrapper(FormComponent comp, Class layoutClass, boolean isSwing) {
		super(comp);
		this.isSwing = isSwing;
        if (SUPPORT_GROUP_LAYOUT)
            swingTypes[0] = "Group";
        if (jiglooPlugin.getActiveEditor() != null && jiglooPlugin.getActiveEditor().canUseEnfinLayout())
            swingTypes[1] = "Enfin";
		setLayoutClass(layoutClass);
		inited = true;
	}

	public LayoutWrapper(FormComponent comp, Node node, boolean isSwing) {
		super(comp);
		this.isSwing = isSwing;
        if (SUPPORT_GROUP_LAYOUT)
            swingTypes[0] = "Group";
        if (jiglooPlugin.getActiveEditor() != null && jiglooPlugin.getActiveEditor().canUseEnfinLayout())
            swingTypes[1] = "Enfin";
		Node ln = NodeUtils.getChildNodeByName("Layout", node);
		this.node = ln;
		layoutType = null;
		if (ln != null) {
			//initPropertiesFromNodeAttributes();
			//HashMap props = new HashMap();
			//NodeUtils.fillAttributesFromSubnodes(props, ln, comp);
			String cls = NodeUtils.getComponentClass(ln);
			for (int i = 0; i < swingTypes.length; i++) {
				String type = swingTypes[i];
				if (type != null && like(type, cls)) {
					layoutType = type;
					break;
				}
			}
			if (layoutType == null) {
				for (int i = 0; i < swtTypes.length; i++) {
					String type = swtTypes[i];
					if (cls.indexOf(type) >= 0) {
						layoutType = type;
						break;
					}
				}
			}

		}
		setLayoutType(layoutType);
		if (layoutType != null)
			inited = true;
	}

    public void setName(String name) {
        super.setName(name);
    }
    
	public void setLayoutType(String type) {
		isCustom = false;
		layoutType = type;
		if (layoutType != null)
			setSet(true);

		if (layoutType != null && layoutType.startsWith("JRootPane")) {
		    layoutType = "Border";
			return;
		}
		for (int i = 0; i < swingTypes.length; i++) {
			if (type != null && type.equals(swingTypes[i]))
				return;
		}
		for (int i = 0; i < swtTypes.length; i++) {
			String st = swtTypes[i];
			if (st.equals(type))
				return;
		}

		if(comp.isSubclassOf(JSplitPane.class)
				|| comp.isSubclassOf(JTabbedPane.class)
				|| comp.isSubclassOf(JLayeredPane.class)) {
			isCustom = false;
		} else 	if (layoutType != null) {
			isCustom = true;
		}

	}

	public void setLayoutClass(Class layoutClass) {
		this.layoutClass = layoutClass;
		setLayoutType(getLayoutName());
		//        try {
		//            initCustomProperties(layoutClass, false);
		//        } catch (Throwable e) {
		//            e.printStackTrace();
		//        }
	}

	public static LayoutManager getLayoutManager(FormComponent fc) {
		LayoutManager lm = null;
		Component jcomp = fc.getComponent();
		if (jcomp == null)
			return null;

		if (fc.usesContentPane()) {
			//if (fc.isSubclassOf(JFrame.class) ||
			// fc.isSubclassOf(JDialog.class)) {
			//			if (fc.isSubclassOf(JDialog.class)) {
			//				lm = new BorderLayout();
			//				((Container) jcomp).setLayout(lm);
			//			} else {
			lm = fc.getContentPane().getLayout();
			//}
		} else {
			lm = ((Container) jcomp).getLayout();
		}
		//System.out.println("GOT LAYOUT "+lm+", "+fc);
		return lm;
	}

	public boolean isReorderable() {
		Class mc = getMainClass();
		if (mc == null)
			return false;
		if (jiglooPlugin.canUseSwing()) {
			if ( Cacher.isAssignableFrom(BoxLayout.class, mc))
				return true;
			if ( Cacher.isAssignableFrom(FlowLayout.class, mc))
				return true;
			if ( Cacher.isAssignableFrom(java.awt.GridLayout.class, mc))
				return true;
		}
		if ( Cacher.isAssignableFrom(GridLayout.class, mc))
			return true;
		if ( Cacher.isAssignableFrom(RowLayout.class, mc))
			return true;
		if ( Cacher.isAssignableFrom(FillLayout.class, mc))
			return true;
		return false;
		//return like("Box") || like("Grid") || like("Flow") || like("Row") ||
		// like("Fill");
	}

	public void init() {
		//        if(comp.toString().toLowerCase().indexOf("jpanel1") >= 0) {
		//            System.err.println("LW INIT "+inited+", "+comp.getComponent()+", "+
		//                    getLayoutManager(comp)+", "+comp);
		//        }
		if (inited)
			return;
		inited = true;
		isSet = false;
		if (isSwing) {
			Component jcomp = comp.getComponent();
			if (jcomp != null && jcomp instanceof Container) {
				LayoutManager lm = getLayoutManager(comp);
				if (lm != null) {

					//set the object so that getSwingLayout will return this layout manager
					//(which is important when the FormComponent is inherited)
				    //also, ensures that properties of the layout manager are correctly
				    //deduced (eg, columnWidth for an inherited GBLayout).
					object = lm;
				    
					layoutClass = lm.getClass();
					//System.out.println("GOT LAYOUT CLASS " + layoutClass + ",
					// " + comp);
					//if ( Cacher.isAssignableFrom(BorderLayout.class, layoutClass))
					if (BorderLayout.class.equals(layoutClass))
						layoutType = "Border";
					else if (layoutClass.getName().startsWith("javax.swing.JRootPane$"))
						layoutType = "Border";
					else if (FlowLayout.class.equals(layoutClass))
						layoutType = "Flow";
					else if (com.jgoodies.forms.layout.FormLayout.class
							.equals(layoutClass))
						layoutType = "Form";
					else if (GridBagLayout.class.equals(layoutClass))
						layoutType = "GridBag";
					else if (java.awt.GridLayout.class.equals(layoutClass))
						layoutType = "Grid";
					else if (BoxLayout.class.equals(layoutClass)) {
						layoutType = "Box";
						//there is no way t determine whether this BoxLayout is
						// an X or Y axis layout!
					} else if (CardLayout.class.equals(layoutClass))
						layoutType = "Card";
					else if (AnchorLayout.class.equals(layoutClass))
						layoutType = "Anchor";
					else if (layoutClass.getName().indexOf("MigLayout") >= 0)
						layoutType = "Mig";
					else if (GroupLayout.class.equals(layoutClass))
						layoutType = "Anchor";
					else if(comp.isSubclassOf(JTabbedPane.class)
							|| comp.isSubclassOf(JSplitPane.class)
							|| comp.isSubclassOf(JLayeredPane.class)) {
						//not custom
					} else {
						layoutType = getLayoutName();
						isCustom = true;
					}
				} else {
					layoutType = "Absolute";
				}
			} else {
				layoutType = null;
			}
			//			if (like("GridBag")) {
			//				if (!isPropertySet("columns"))
			//					setPropertyValue("columns", new Integer(4));
			//				if (!isPropertySet("rows"))
			//					setPropertyValue("rows", new Integer(4));
			//			}
		} else {
			Object wid = comp.getRawControl();
			Layout lo = null;
			if (wid != null && wid instanceof Composite) {
				lo = ((Composite) wid).getLayout();
				if (lo != null) {
					setObject(lo);
					isSet = false;

				} else {
					//System.err.println(
					//"SETTING ABSOLUTE LAYOUT BY DEFAULT " + comp.getName());
					//layoutType = "Absolute";
					layoutType = null;
				}
				//System.out.println("LW INIT: layoutType="+layoutType+",
				// wid="+wid+", lo="+lo);
			}
		}
	}

	public void toggle(boolean toSwing) {
	    
	    //sometimes when toggling layout, the layout depends on the class of component
	    if (toSwing) {
	        if(comp != null) {
	            if(comp.isA(ToolBar.class) 
	                    || comp.isA(CoolBar.class) 
	                    || comp.isA(CoolItem.class)) {
	                layoutType="Box";
	                if((comp.getStyle() & SWT.VERTICAL) != 0) {
	                    setAttribute("axis", new Integer(BoxLayout.Y_AXIS));
	                } else {
	                    setAttribute("axis", new Integer(BoxLayout.X_AXIS));
	                }
	            }
	            if(comp.isA(TabItem.class) 
	                    || comp.isA(CTabItem.class)) {
	                layoutType="Border";
	            }
	        }
	    }
	    
		if (layoutType == null)
			return;
		
		//        System.out.println("TOGGLE LAYOUT " + toSwing + ", " + layoutType);
		if (toSwing) {
			if (like("Form"))
				layoutType = "Anchor";
			if (like("Row"))
				layoutType = "Flow";
			if (like("Fill"))
				layoutType = "Box";
			if (like("Stack"))
				layoutType = "Card";

			if (getAttributeAsInt("type") == SWT.VERTICAL) {
				setAttribute("axis", new Integer(BoxLayout.Y_AXIS));
			} else {
				setAttribute("axis", new Integer(BoxLayout.X_AXIS));
			}

			if (like("Grid")) {
				removeImport("org.eclipse.swt.layout.GridLayout");
				int num = comp.getChildCount();
				if (getColumns() == 1) {
					layoutType = "Box";
					setAttribute("axis", new Integer(BoxLayout.Y_AXIS));
				} else if (getColumns() == num) {
					layoutType = "Box";
					setAttribute("axis", new Integer(BoxLayout.X_AXIS));
				} else {
					int cols = getAttributeAsInt("numColumns");
					setAttribute("columns", new Integer(cols));
					if (cols != 0)
						setAttribute("rows", new Integer(num / cols));
				}
			}
		} else {
			if (like("Flow"))
				layoutType = "Row";
			else if (like("GridBag"))
				layoutType = "Row";
			else if (like("Grid")) {
				removeImport("java.awt.GridLayout");
				layoutType = "Grid";
			} else if (like("Box"))
				layoutType = "Fill";
			else if (like("Border"))
				layoutType = "Grid";
			else if (like("Table"))
				layoutType = "Grid";
			else if (like("Group"))
				layoutType = "Grid";
			else if (like("Card"))
				layoutType = "Stack";
			else if (like("Anchor"))
				layoutType = "Form";
			else if (like("Absolute"))
				layoutType = "Absolute";
			else
				layoutType = null;

			if (getAttributeAsInt("axis") == BoxLayout.Y_AXIS) {
				setAttribute("type", new Integer(SWT.VERTICAL));
			} else {
				setAttribute("type", new Integer(SWT.HORIZONTAL));
			}
			int num = comp.getChildCount();
			int rows = getAttributeAsInt("rows");
			int cols = getAttributeAsInt("columns");
			if (rows > 0)
				cols = num / rows;
			if (cols == 0)
				cols = 1;
			setAttribute("numColumns", new Integer(cols));

		}
		isSwing = toSwing;
		boolean set = isSet;
		object = null;
		//        System.out.println("TOGGLED LAYOUT (1) " + toSwing + ", " +
		// layoutType+", "+object+", "+isSet);
		getObject();
		isSet = set;
		//        System.out.println("TOGGLED LAYOUT " + toSwing + ", " + layoutType+",
		// "+object+", "+isSet);
	}

	public void setSwing(boolean swing) {
		isSwing = swing;
	}

	//	protected void initProperties() {
	//		if (like("GridBag")) {
	//			synthProps = new HashMap();
	//			synthProps.put("columns", new Integer(4));
	//			synthProps.put("rows", new Integer(4));
	//		}
	//		super.initProperties();
	//	}

	private int getArrayPropSize(String id) {
		Object val = getPropertyValue(id);
		if (val instanceof IntegerArrayWrapper) {
			IntegerArrayWrapper iaw = (IntegerArrayWrapper) val;
			if (iaw.array == null)
				return -1;
			return ((int[]) iaw.array).length;
		} else if (val instanceof DoubleArrayWrapper) {
			DoubleArrayWrapper iaw = (DoubleArrayWrapper) val;
			if (iaw.array == null)
				return -1;
			return ((double[]) iaw.array).length;
		}
		return -1;
	}

	public boolean isHiddenProperty(String prop) {
		if (comp.isSwing() && like("Form")) {
		    if(prop.equals("rowGroups") 
		            || prop.equals("columnGroups"))
		        return true;
		}
		if (comp.isSwing() && like("Group")) {
		    if(prop.equals("horizontalGroup") 
		            || prop.equals("verticalGroup")
		            || prop.equals("container")
		            )
		        return true;
		}
	    return false;
	}
	
	public void removeHiddenProps() {
	    if(comp == null)
	        return;
		if (comp.isSwing() && like("Form")) {
		    removeHiddenProp("rowGroups");
		    removeHiddenProp("columnGroups");
		}
		if (comp.isSwing() && like("Group")) {
		    removeHiddenProp("horizontalGroup");
		    removeHiddenProp("verticalGroup");
		    removeHiddenProp("container");
		}
	}
	
	private void removeHiddenProp(String propName) {
		allNames.remove(propName);
		propNames.remove(propName);
		fieldNames.remove(propName);
	}
	
	public void addSynthProps() {
		if (like("GridBag")) {
			int cols = getArrayPropSize("columnWeights");
			if (cols < 0)
				cols = getArrayPropSize("columnWidths");
			if (cols < 0)
				cols = 0;
			addSynthProperty("columns", Integer.class, new Integer(cols));
			
			int rows = getArrayPropSize("rowWeights");
			if (rows < 0)
				rows = getArrayPropSize("rowHeights");
			if (rows < 0)
				rows = 0;
			addSynthProperty("rows", Integer.class, new Integer(rows));
			
		} else if (comp != null && comp.isSwing() && like("Form")) {
			addSynthProperty("colSpecs", String.class, FORM_LO_DEF_SPEC);
			addSynthProperty("rowSpecs", String.class, FORM_LO_DEF_SPEC);
		}
	}

	public void initProperties() {
		try {
			initCustomProperties(getMainClass(), false);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		super.initProperties();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (layoutType == null)
			return new IPropertyDescriptor[0];

		//        try {
		//            initCustomProperties(getMainClass(), false);
		//        } catch (Throwable e) {
		//            e.printStackTrace();
		//        }
		IPropertyDescriptor[] descs = super.getPropertyDescriptors();
		try {
			for (int i = 0; i < descs.length; i++) {
				String id = (String) descs[i].getId();
				if (id.equals("topControl")) {
					Vector sibs = comp.getChildren();
					Object[] objs = new Object[sibs.size() + 1];
					String[] choices = new String[objs.length];
					int index = 1;
					objs[0] = "null";
					choices[0] = "null";
					for (int j = 0; j < sibs.size(); j++) {
						FormComponent fc = (FormComponent) sibs.elementAt(j);
						objs[index] = new FormComponentWrapper(fc);
						choices[index] = fc.getName();
						index++;
					}
					descs[i] = new ChoicePropertyDescriptor(id, id, comp, objs,
							choices, this);
				}
			}
		} catch (Throwable e) {
			jiglooPlugin.handleError(e);
			return new IPropertyDescriptor[0];
		}

		return descs;
	}

	public void setSetProps(Vector setProps) {
		this.setProps = (Vector) setProps.clone();
	}

	public FormPropertySource getCopy(FormComponent comp) {
		//isSet can be changed by a call to fps.copyPropertiesFrom
		boolean set = isSet;
		if (comp == null)
			comp = this.comp;
		boolean swing = isSwing;
		LayoutWrapper fps = null;
		if (layoutClass != null)
			fps = new LayoutWrapper(comp, layoutClass, swing);
		else
			fps = new LayoutWrapper(comp, layoutType, swing);

		try {
			if (isCustom() && set && layoutClass != null) {
				fps.setObject(ClassUtils.newInstance(layoutClass, null, null));
			}
		} catch (Throwable e) {
			System.err.println("Error copying layout "+e);
//			e.printStackTrace();
		}
		
		if (getObject() instanceof GroupLayout)
			fps.setObject(getGroupLayoutCopy(comp));
		
		fps.copyPropertiesFrom(this);

		if (getObject() instanceof com.jgoodies.forms.layout.FormLayout)
			fps.setObject(getFormLayoutCopy());

		fps.setHGroup(getHGroup() == null ? null : getHGroup().getCopy(fps));
		fps.setVGroup(getVGroup() == null ? null : getVGroup().getCopy(fps));
		setSet(set);
		fps.setSet(set);
		fps.setWasSet(wasSet);

		if (swing) {
		    Component cmp = comp.getComponent();
		    if(cmp instanceof Container)
		        fps.getSwingLayout((Container)cmp);
		    else {
		        if(cmp != null)
		            System.err.println("Component is not container in LayoutWrapper.getCopy "+comp+", "+this);
		    }
		} else {
			fps.getSWTLayout();
		}
		if (name != null)
			fps.setName(name);
		return fps;
	}
	
    private void setContainer(Container container) {
        this.container = container;
    }

    public String getMainClassName() {
        Class cls = getMainClass();
        if(cls == null)
            return "No Layout";
        if(GroupLayout.class.equals(cls)) {
            if(comp.getEditor().useJava6GroupLayout())
                return "javax.swing.GroupLayout";
            return "org.jdesktop.layout.GroupLayout";
        }
        return cls.getName();
    }
    
    public Class getMainClass() {
		Class cls = null;
		if (layoutClass != null) {
			cls = layoutClass;
		} else {
			if (getObject() == null)
				return null;
			cls = getObject().getClass();
		}
		
		return cls;
	}
	
	public boolean equals(Object o) {
		try {
			//hack to force setting of layout
			//if(true) return false;

			if (o instanceof LayoutWrapper) {
				LayoutWrapper lw = (LayoutWrapper) o;
				if(super.equals(lw))
					return true;
				if (lw.getLayoutType() == null && layoutType == null)
					return true;
				if (lw.getLayoutType() == null)
					return false;
				if (!lw.getLayoutType().equals(layoutType))
					return false;

				if (layoutType.equals("Box") && isSwing) {
					return lw.getAxis() == getAxis();
				}

				if (layoutType.equals("Form") && isSwing) {
					com.jgoodies.forms.layout.FormLayout fl1 = getJGFormLayout();
					com.jgoodies.forms.layout.FormLayout fl2 = lw
							.getJGFormLayout();
					if (fl1 == null || fl2 == null)
						return false;
					if (fl1.getRowCount() != fl2.getRowCount()
							|| fl1.getColumnCount() != fl2.getColumnCount())
						return false;
					for (int i = 1; i <= fl1.getColumnCount(); i++) {
						if (fl1.getColumnSpec(i) == null
								|| !fl1.getColumnSpec(i).toString().equals(
										fl2.getColumnSpec(i).toString()))
							return false;
					}
					for (int i = 1; i <= fl1.getRowCount(); i++) {
						if (fl1.getRowSpec(i) == null
								|| !fl1.getRowSpec(i).toString().equals(
										fl2.getRowSpec(i).toString()))
							return false;
					}
					return true;
				}

				if (lw.getAttributeCount() != getAttributeCount())
					return false;
				
//				if(!equals(hGroup, lw.hGroup))
//				    return false;
//				if(!equals(vGroup, lw.vGroup))
//				    return false;
				
				//allNames might be null (if getObject() returns null), but properties might be non-null
				if (getAttributeCount() == 0 && properties != null && properties.size() == 0)
					return true;

				if(properties == null)
				    return false;
				
				Iterator it = properties.keySet().iterator();
				while (it.hasNext()) {
					Object id = it.next();
					Object att = properties.get(id);
					Object att2 = lw.getPropertyValue((String) id, false);
//					                System.out.println(
//					                "testing " + id + ":" + att + ":" + att2 + ":");
					if ((att == null && att2 != null))
						return false;
					if ((att2 == null && att != null))
						return false;
					if (att != null && att2 != null) {
						if (!stringsEqual(att.toString(), att2.toString()))
							return false;
					}
				}
				//System.out.println("LWS EQUAL");
				return true;
			}
		} catch (Throwable t) {
			jiglooPlugin.handleError(t);
		}
		return false;
	}

	/**
     * @param group
     * @param group2
     * @return
     */
    private boolean equals(Object o1, Object o2) {
        if(o1 == null && o2 == null)
            return true;
        if(o1 == null && o2 != null)
            return false;
        if(o1 != null && o2 == null)
            return false;
        return o1.equals(o2);
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

	private String getShortClassName() {
		String name = getMainClass().getName();
		int pos = name.lastIndexOf(".");
		return name.substring(pos + 1);
	}

	private String getLayoutName() {
		String name = getShortClassName();
		int pos2 = name.lastIndexOf("Layout");
		if (pos2 < 0)
			return name;
		return name.substring(0, pos2);
	}

	private boolean like(String layout) {
		if (layoutType == null)
			return false;
		return layoutType.equals(layout);
		//return layoutType.indexOf(layout) >= 0;
	}

	private boolean like(String layout, String className) {
		return className.equals(layout)
				|| className.endsWith(layout + "Layout")
				|| (className.toLowerCase().indexOf("nebeans") >= 0 && className
						.indexOf(layout) >= 0);
	}

	public int getColumns() {
		String cols = null;
		if (isSwing) {
			cols = getAttribute("columns");
		} else {
			cols = getAttribute("numColumns");
		}
		if (cols == null)
			return 1;
		int icols = Integer.parseInt(cols);
		if (icols < 1)
			icols = 1;
		return icols;
	}

	public int getRows() {
		String rows = getAttribute("rows");
		if (rows == null)
			return 1;
		return Integer.parseInt(rows);
	}

	public boolean isDifferentType(LayoutWrapper lw) {
		String lt = getLayoutType();
		String nlt = lw.getLayoutType();
		if (!lt.equals(nlt))
			return true;
		if (!"Box".equals(lt) || !isSwing)
			return false;
		return lw.getAxis() != getAxis();
	}

	public int getAxis() {
		int axis = 0;
		if (isSwing) {
			if (getAttribute("axis") == null)
				return BoxLayout.X_AXIS;
			axis = getAttributeAsInt("axis");
			if (axis != BoxLayout.X_AXIS && axis != BoxLayout.Y_AXIS)
				axis = BoxLayout.X_AXIS;
		} else {
			if (getAttribute("type") == null)
				return SWT.HORIZONTAL;
			axis = getAttributeAsInt("type");
			if (axis != SWT.HORIZONTAL && axis != SWT.VERTICAL)
				axis = SWT.HORIZONTAL;
		}
		return axis;
	}

	public Layout getSWTLayout() {
		if (layoutType == null)
			return null;
		isSwing = false;

		if (object != null && object instanceof Layout)
			return (Layout) object;

        HashMap propBak = null;
        boolean wasNull = (object == null);
        if(wasNull && properties != null)
            propBak = (HashMap)properties.clone();
        
		//properties = null;
		propertyDescriptors = null;
		//System.err.println("GET SWT LAYOUT PROP DESCS=NULL " + this);
		propNames = null;

		setObject(null);

		if (like("Absolute")) {
			return null;
		} else if (like("Fill")) {
			FillLayout fl = new FillLayout(getAxis());
			setObject(fl);
		} else if (like("Row"))
			setObject(new RowLayout());
		else if (like("Grid"))
			setObject(new GridLayout(getColumns(), true));
		else if (like("Form"))
			setObject(new FormLayout());
		else if (like("Stack"))
			setObject(new StackLayout());
		else if (like("Mig"))
			setObject(new MigLayout());
//		else if (layoutClass != null)
//			try {
//				setObject(layoutClass.newInstance());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		//else if (like("Anchor"))
		//setObject(new AnchorLayout());
		//System.out.println("LAYOUT = " + object + " type=" + layoutType);
        if(propBak != null) {
            Iterator it = propBak.keySet().iterator();
            while(it.hasNext()) {
                Object key = it.next();
                setPropertyValue(key, propBak.get(key), false, true);
            }
        }
		return (Layout) object;
	}

	public LayoutManager getSwingLayout(Container cont) {
	    
	    cont = SwingHelper.getContentPane(cont);
	    
		boolean isSetOrig = isSet();
		if (layoutType == null || (cont == null && like("Box")))
			return null;
		isSwing = true;
		
		if(object instanceof GroupLayout) {
		    GroupLayout gl = (GroupLayout)object;
		    if(!gl.getContainer().equals(cont))
		        System.out.println("OOPS - GroupLayout has wrong container");
		    gl.setContainer(cont);
		}
		
		if (object != null
				&& object instanceof LayoutManager
				&& (!(object instanceof BoxLayout) 
						|| (cont != null && cont.equals(container))))
			return (LayoutManager) object;

		boolean initFromNode = false;
		if (properties == null) {
			initPropertiesFromNode();
			initFromNode = true;
		}

		container = cont;
		//properties = null;
		propertyDescriptors = null;
		propNames = null;

        HashMap propBak = null;
        boolean wasNull = (object == null);
        if(wasNull)
            propBak = (HashMap)properties.clone();
        
		if (like("Absolute"))
			setObject(new AbsoluteLayout());
		else if (like("Border"))
			setObject(new BorderLayout());
		else if (like("Flow")) {
			setObject(new FlowLayout());
		} else if (like("Form")) {
		    com.jgoodies.forms.layout.FormLayout fl =
		        new com.jgoodies.forms.layout.FormLayout(
						FORM_LO_DEF_SPEC, FORM_LO_DEF_SPEC);
			setObject(fl);
		} else if (like("GridBag")) {
		    GridBagLayout gbl = new GridBagLayout();
			setObject(gbl);
		} else if (like("Card"))
			setObject(new CardLayout());
		else if (like("Grid")) {
		    java.awt.GridLayout gl =
		        new java.awt.GridLayout(getRows(), getColumns());
		    gl.setHgap(5);
		    gl.setVgap(5);
			setObject(gl);
		} else if (like("Box"))
			setObject(new BoxLayout(cont, getAxis()));
		else if (like("Group")) {
		    if(cont != null) {
	            setObject(new GroupLayout((JComponent)cont));
		    }
		} else if (like("Anchor"))
			setObject(new AnchorLayout());
		
		else if (like("Table")) {
			
		    DefaultValueManager.addClassObject(new TableLayout(new double[][]{{-1}, {-1}}), false);
		    TableLayout tl = new TableLayout(new double[][]{{-1,-1,-1,-1}, {-1,-1,-1,-1}});
		    tl.setHGap(5);
		    tl.setVGap(5);
			setObject(tl);

		} else if (isSet() && layoutClass != null) {
			try {
				setObject(ClassUtils.newInstance(layoutClass, null, null));
				//                System.out.println("SET LW OBJECT "+layoutClass);
			} catch (Throwable e) {
//				if (layoutClass != null && !layoutClass.getName().startsWith("java"))
//					System.err.println("Unable to create layout " + layoutClass + ", " + e);
//					return null;
			}
		} else {
			if(cont != null)
				object = cont.getLayout(); //v4.2.1
			if (object != null) {
				if (!(object instanceof LayoutManager)) {
					System.err.println("***Unrecognized layout object "
							+ getLayoutType() + ", " + object);
				}
			}
		}
		
        if(wasNull) {
            Iterator it = propBak.keySet().iterator();
            while(it.hasNext()) {
                Object key = it.next();
                setPropertyValue(key, propBak.get(key), false, true);
            }
        }
        
		if (initFromNode)
			initPropertiesFromNode();
		setSet(isSetOrig);
		if (object instanceof LayoutManager)
			return (LayoutManager) object;
		else {
			if (object != null && comp.isSwing())
				System.err.println("Got layout object " + object + " for "
						+ this + ", " + comp + ", " + layoutClass + ", "
						+ layoutType);
			return null;
		}
	}

	public void setObject(Object o) {
		if (o instanceof ConstructorHolder) {
			o = ((ConstructorHolder) o).newInstance();
		}
		//        super.setObject(o);
		if (o != null) {
			isSet = true;
			setLayoutClass(o.getClass());
		}
		super.setObject(o);
	}

	public String getFormLayoutSpec(com.jgoodies.forms.layout.FormLayout fl,
			boolean cols) {
		return getFormLayoutSpec(fl, cols, false);
	}

	public static String getFormLayoutSpec(
			com.jgoodies.forms.layout.FormLayout fl, boolean cols,
			boolean addSpaces) {
		int cc = fl.getColumnCount();
		if (!cols)
			cc = fl.getRowCount();
		String cd = "";
		for (int i = 0; i < cc; i++) {
			FormSpec cs = null;
			if (cols)
				cs = fl.getColumnSpec(i + 1);
			else
				cs = fl.getRowSpec(i + 1);
			if (cs == null) {
				if (cols) {
					cs = FormFactory.DEFAULT_COLSPEC;
					fl.setColumnSpec(i+1, FormFactory.DEFAULT_COLSPEC);
				} else {
					cs = FormFactory.DEFAULT_ROWSPEC;
					fl.setRowSpec(i+1, FormFactory.DEFAULT_ROWSPEC);
				}
			}
			if (!cd.equals("")) {
				if (addSpaces)
					cd += ", ";
				else
					cd += ",";
			}
			String tmp = "";
			String defA = cs.getDefaultAlignment().toString();
			if (cols) {
				if (!"fill".equals(defA))
					tmp += defA;
			} else {
				if (!"center".equals(defA))
					tmp += defA;
			}
			if (!tmp.equals(""))
				tmp += ":";
			String size = cs.getSize().toString();
			size = JiglooUtils.replace(size, "dluX", "dlu");
			size = JiglooUtils.replace(size, "dluY", "dlu");
			tmp += size;
			double wt = cs.getResizeWeight();
			if (wt == 1) {
				tmp += ":grow";
			} else if (wt != 0) {
				tmp += ":grow(" + wt + ")";
			}
			//            cd+=cs.toShortString();
			cd += tmp;
		}
		//        System.out.println("GOT SPEC " + cd);
		return cd;
	}

	private Object getFormLayoutCopy() {
		com.jgoodies.forms.layout.FormLayout fl = (com.jgoodies.forms.layout.FormLayout) getObject();
		ColumnSpec[] css = new ColumnSpec[fl.getColumnCount()];
		for (int i = 0; i < css.length; i++)
			css[i] = fl.getColumnSpec(i + 1);
		RowSpec[] rss = new RowSpec[fl.getRowCount()];
		for (int i = 0; i < rss.length; i++)
			rss[i] = fl.getRowSpec(i + 1);
		com.jgoodies.forms.layout.FormLayout fl2 = new com.jgoodies.forms.layout.FormLayout(
				css, rss);
		return fl2;
	}

	private Object getGroupLayoutCopy(FormComponent comp) {
		GroupLayout fl = (GroupLayout) getObject();
		Container cont = (Container) comp.getComponent();
		if(!(cont instanceof JComponent))
		    return null;
		cont = SwingHelper.getContentPane(cont);
		GroupLayout fl2 = new GroupLayout(cont);
		fl2.setAutocreateContainerGaps(fl.getAutocreateContainerGaps());
		fl2.setAutocreateGaps(fl.getAutocreateGaps());
//		fl2.setVerticalGroup(fl.getVerticalGroup());
//		fl2.setHorizontalGroup(fl.getHorizontalGroup());
		return fl2;
	}

	public Object getObject() {
		return getObject(true);
	}
	
	public Object getObject(boolean createIfNecessary) {
		if (object != null)
			return object;
		if(!createIfNecessary)
			return null;
		if(comp.noLayout())
			return null;
		if (isSwing) {
			getSwingLayout(container);
		} else {
			getSWTLayout();
		} //System.out.println("LW getObject "+object+", "+comp.isSwing());
		return object;
	}

	public String getClassNameForCode() {
		String fn = getMainClassName();
		if (jiglooPlugin.useImports() && comp.getEditor().updatesJavaCode()) {
			getJCP().addImport(fn);
			return getShortClassName();
		} else {
			return fn;
		}
	}

	private JavaCodeParser getJCP() {
	    return comp.getEditor().getJavaCodeParser();
	}
	
	private String getContentPaneCode(String parName) {
	    if("this".equals(parName))
	        return "getContentPane()";
	    return parName + ".getContentPane()";
	}
	
	public String getJavaConstructor(IJavaCodeManager jcm) {
		if (layoutType == null
		//                || isCustom
				|| !isSet() || like("Absolute"))
			return "";
		//        System.err.println("LW: GET JCON FOR "+this+", "+layoutClass);
		//        new Exception().printStackTrace();
		String container = comp.getNameInCode();
		if (comp.usesContentPane()) {
		    container = getContentPaneCode(container);
		}
		String layoutName = getNameInCode();
		String code = "\t\t" + getClassNameForCode() + " " + layoutName + " = ";
		//String code = "\t\t" + layoutType + "Layout " + layoutName + " = ";
		if (!isSwing) {
			if (!like("Absolute")) {
				code += "new " + getClassNameForCode() + "(";
				if (like("Row") || like("Fill")) {
					if (getAxis() == SWT.VERTICAL)
						code += "org.eclipse.swt.SWT.VERTICAL";
					else
						code += "org.eclipse.swt.SWT.HORIZONTAL";
				} else if (like("Grid")) {
					//code += getColumns() + ", true";
				}
				code += ")";
			} else {
				code += "null";
			}
		} else {

			//            if (like("Form"))
			//                layoutType = "Flow";
			if (like("Row"))
				layoutType = "Flow";

			if (!like("Absolute")) {
				code += "new " + getClassNameForCode() + "(";
				if (like("Box")) {
					if (getAxis() == BoxLayout.X_AXIS) {
						code += container + ", javax.swing.BoxLayout.X_AXIS";
					} else {
						code += container + ", javax.swing.BoxLayout.Y_AXIS";
					}
				} else if (like("Group")) {
				    getJCP().addImport(JComponent.class.getName());
					code += "(JComponent)"+container;
				} else if (like("Grid")) {
					code += getRows() + ", " + getColumns();
				} else if (like("Table")) {
					TableLayout tl = (TableLayout) object;
					code += "new double[][] {{";
					double[] vals = tl.getColumn();
					for (int i = 0; i < vals.length; i++) {
						if(i != 0)
							code+= ", ";
						if(vals[i] == TableLayout.FILL)
							code += "TableLayout.FILL";
						else if(vals[i] == TableLayout.PREFERRED)
							code += "TableLayout.PREFERRED";
						else if(vals[i] == TableLayout.MINIMUM)
							code += "TableLayout.MINIMUM";
						else
							code += vals[i];
					}
					code += "}, {";
					vals = tl.getRow();
					for (int i = 0; i < vals.length; i++) {
						if(i != 0)
							code+= ", ";
						if(vals[i] == TableLayout.FILL)
							code += "TableLayout.FILL";
						else if(vals[i] == TableLayout.PREFERRED)
							code += "TableLayout.PREFERRED";
						else if(vals[i] == TableLayout.MINIMUM)
							code += "TableLayout.MINIMUM";
						else
							code += vals[i];
					}
					code += "}}";
				} else if (isJGForm()) {
					com.jgoodies.forms.layout.FormLayout fl = getJGFormLayout();
					code += "\n\"" + getFormLayoutSpec(fl, true, true) + "\", \n\""
							+ getFormLayoutSpec(fl, false, true) + "\"";
				}
				code += ")";
			} else {
				code += "null";
			}
		}
		code += ";\n";
		return code;
	}

	public String getJavaCode(String container, String parentName,
			boolean isSwing, IJavaCodeManager jcm) {

		String cmnt = "";
		//		if (jiglooPlugin.makeComments())
		//			cmnt =
		//				"\t\t//Setting "
		//					+ layoutType
		//					+ " layout for "
		//					+ container
		//					+ "\n";
		if (like("Absolute"))
			return cmnt + "\t\t" + container + ".setLayout(null);\n";
		if (layoutType == null || isCustom || !isSet())
			return "";
		String layoutName = getName(); //parentName +
		// "Layout";
		String code = getJavaConstructor(jcm);
		code += "\t\t" + container + ".setLayout(" + layoutName + ");\n";
		if (!like("Box")) {
			code += getJavaCodeForProps(layoutName, isSwing, jcm);
		}
		return code;
	}

	public String getJavaCodeForPropertySetter(String pName, IJavaCodeManager jcm) {
		//        System.out.println("GET JAVA CODE FOR LAYOUT PROP " + pName + ", " +
		//                isPropertySet(pName));
	    if(like("Table") && ("column".equals(pName) || "row".equals(pName)))
	    		return "";
		if (propertyTypes != null && isPropertySet(pName)
				&& !isSyntheticProperty(pName)) {
			Object value = null;
			String cc = getJavaCustomPropertyCode(pName);
			boolean addQuotes = true;
			if (cc != null) {
				value = cc;
				addQuotes = false;
			} else {
				value = getPropertyValue(pName, false);
			}
			if ("null".equals(value)
					&& !propertyTypes.get(pName).equals(String.class)) {
				value = null;
			}
			String nic = getNameInCode();
			if (!isSet()) {
				nic = "((" + layoutClass.getName() + ")" + comp.getNameInCode()
						+ ".getLayout())";
			}
			
			pName = comp.getEditor().convertGroupLayoutProp(pName, true);

			return getJavaCodeForProperty(pName, nic, value, isField(pName),
					isSwing, false, addQuotes, null, jcm);
		}
		return "";
	}

	public boolean isCustom() {
		if (isSwing) {
			return isCustom;
		} else {
			if (comp.isA(TabFolder.class))
				return true;
			if (comp.isA(CTabFolder.class))
				return true;
			if (comp.isA(Table.class))
				return true;
			if (comp.isA(TableTree.class))
				return true;
			if (comp.isA(Tree.class))
				return true;
			if (comp.isA(CoolBar.class))
				return true;
			if (comp.isA(ToolBar.class))
				return true;
			if (comp.isSubclassOf(Composite.class))
				return false;
			if (comp.isSubclassOf(Dialog.class))
				return false;
			return true;
		}
	}

	/**
	 * Returns false if the layout has not been explicitly set in the code (eg,
	 * a JPanel starts out with a FlowLayout, so if no layout has been
	 * explicitly set besides the FlowLayout, then isExplicitlySet will be
	 * false).
	 */
	public boolean isExplicitlySet() {
		return isExplicitlySet;
	}

	public void setExplicitlySet(boolean set) {
		isExplicitlySet = set;
	}

	public boolean isSet() {
		if (layoutType == null) // ||
			// layoutType.equals("Absolute"))
			return false;
		return isSet;
	}

	public void setSet(boolean set) {
		isSet = set;
	}

	public String getLayoutType() {
		//return getSuperLayoutType();
		return layoutType;
	}
	
	public int getIntArrayValue(String propName, int index) {
		IntegerArrayWrapper wtw = 
			(IntegerArrayWrapper)getPropertyValue(propName);
		int[] wts = (int[])wtw.getValue(null);
		if(wts == null || index > wts.length-1)
			return 0;
		return wts[index];
	}

	public double getDoubleArrayValue(String propName, int index) {
		DoubleArrayWrapper wtw = 
			(DoubleArrayWrapper)getPropertyValue(propName);
		double[] wts = (double[])wtw.getValue(null);
		if(wts == null || index > wts.length-1)
			return 0;
		return wts[index];
	}

	public int getArrayPropertySize(String propName) {
	    Object prop = getPropertyValue(propName);
	    if(prop instanceof DoubleArrayWrapper) {
	        DoubleArrayWrapper wtw = (DoubleArrayWrapper)prop;
	        double[] wts = (double[])wtw.getValue(null);
	        if(wts != null)
	            return wts.length;
	    } else if(prop instanceof IntegerArrayWrapper) {
	        IntegerArrayWrapper wtw = (IntegerArrayWrapper)prop;
	        int[] wts = (int[])wtw.getValue(null);
	        if(wts != null)
	            return wts.length;
	    }
	    return 0;
	}

	public void setIntArrayValue(String propName, int index, int value) {
		IntegerArrayWrapper wtw = 
			(IntegerArrayWrapper)getPropertyValue(propName);
		int[] wts = (int[])wtw.getValue(null);
		if(wts == null || index > wts.length-1) {
			updateIntegerArray(index+1, 0, -1, propName);
			wtw = (IntegerArrayWrapper)getPropertyValue(propName);
			wts = (int[])wtw.getValue(null);
		}
		wts[index] = value;
		setPropertyValue(propName, wtw, true, true);
	}

	public void setDoubleArrayValue(String propName, int index, double value) {
		DoubleArrayWrapper wtw = 
			(DoubleArrayWrapper)getPropertyValue(propName);
		double[] wts = (double[])wtw.getValue(null);
		if(wts == null || index > wts.length-1) {
			updateDoubleArray(index+1, 0, -1, propName);
			wtw = (DoubleArrayWrapper)getPropertyValue(propName);
			wts = (double[])wtw.getValue(null);
		}
		wts[index] = value;
		setPropertyValue(propName, wtw, true, true);
	}

	private double getSafeVal(double[] array, int pos, double def) {
		if(array == null)
			return def;
		if(array.length <= pos)
			return def;
		return array[pos];
	}
	
	private int getSafeVal(int[] array, int pos, int def) {
		if(array == null)
			return def;
		if(array.length <= pos)
			return def;
		return array[pos];
	}
		
	/**
	 * 
	 * @param size - either new size of array (if pos == -1) or +1 or -1 (in which case,
	 *  pos indicates the insert or delete position.
	 * @param val - the value to be inserted
	 * @param pos - the insert or delete position (-1, implies insertion at the end of the array).
	 * @param propName
	 */
	public void updateIntegerArray(int size, int val, int pos, String propName) {
		int[] vals = null;
		IntegerArrayWrapper iaw = (IntegerArrayWrapper)getPropertyValue(propName);
		if (iaw != null)
			vals = (int[]) iaw.getValue(null);
		boolean insert = (size == 1 && pos != -1);
		boolean delete = (size == -1 && pos != -1);
		if(vals == null || pos >= vals.length) {
			if(insert)
				size = pos+1;
			else if(delete)
				size = pos;
		} else if(pos != -1) {
			size += vals.length;
		}
		if (vals == null || vals.length != size) {
			int[] nvals = new int[size];
		    for (int i = size-1; i >= 0; i--) {
		    	if(insert) {
		    		if(i < pos)
		    			nvals[i] = getSafeVal(vals, i, 0);
		    		else if(i == pos)
		    			nvals[i] = val;
		    		else
		    			nvals[i] = getSafeVal(vals, i-1, 0);
		    	} else if(delete) {
		    		if(i < pos)
		    			nvals[i] = getSafeVal(vals, i, 0);
		    		else if(i >= pos)
		    			nvals[i] = getSafeVal(vals, i+1, 0);
		    	} else {
		    		if(vals != null && i < vals.length)
		    			nvals[i] = vals[i];
		    		else
		    			nvals[i] = val;
		    	}
		    }
		    vals = nvals;
		    setPropertyValue(propName, new IntegerArrayWrapper(vals, comp));
		}
	}

	/**
	 * 
	 * @param size - either new size of array (if pos == -1) or +1 or -1 (in which case,
	 *  pos indicates the insert or delete position.
	 * @param val - the value to be inserted
	 * @param pos - the insert or delete position (-1, implies insertion at the end of the array).
	 * @param propName
	 */
	public void updateDoubleArray(int size, double val, int pos, String propName) {
		double[] vals = null;
		DoubleArrayWrapper daw = (DoubleArrayWrapper) getPropertyValue(propName);
		if (daw != null)
		    vals = (double[]) daw.getValue(null);
		boolean insert = (size == 1 && pos != -1);
		boolean delete = (size == -1 && pos != -1);
		if(vals == null || pos >= vals.length) {
			if(insert)
				size = pos+1;
			else if(delete)
				size = pos;
		} else if(pos != -1) {
			size += vals.length;
		}
		if (vals == null || vals.length != size) {
			double[] nvals = new double[size];
		    for (int i = size-1; i >= 0; i--) {
		    	if(insert) {
		    		if(i < pos) {
	    				nvals[i] = getSafeVal(vals, i, 0);
		    		} else if(i == pos) {
		    			nvals[i] = val;
		    		} else {
	    				nvals[i] = getSafeVal(vals, i-1, 0);
		    		}
		    	} else if(delete) {
		    		if(i < pos)
		    			nvals[i] = getSafeVal(vals, i, 0);
		    		else if(i >= pos)
		    			nvals[i] = getSafeVal(vals, i+1, 0);
		    	} else {
		    		if(vals != null && i < vals.length)
		    			nvals[i] = vals[i];
		    		else
		    			nvals[i] = val;
		    	}
		    }
		    vals = nvals;
		    setPropertyValue(propName, new DoubleArrayWrapper(vals, comp));
		}
	}
	
	public String getSuperLayoutType() {
		if (layoutClass != null) {
			if (jiglooPlugin.canUseSwing()) {
				if ( Cacher.isAssignableFrom(BorderLayout.class, layoutClass))
					return "Border";
				if ( Cacher.isAssignableFrom(java.awt.GridLayout.class, layoutClass))
					return "Grid";
				if ( Cacher.isAssignableFrom(FlowLayout.class, layoutClass))
					return "Flow";
				if ( Cacher.isAssignableFrom(com.jgoodies.forms.layout.FormLayout.class, layoutClass))
					return "Form";
				if ( Cacher.isAssignableFrom(GridBagLayout.class, layoutClass))
					return "GridBag";
				if ( Cacher.isAssignableFrom(BoxLayout.class, layoutClass))
					return "Box";
				if ( Cacher.isAssignableFrom(CardLayout.class, layoutClass))
					return "Card";
			}
			if ( Cacher.isAssignableFrom(RowLayout.class, layoutClass))
				return "Row";
			if ( Cacher.isAssignableFrom(GridLayout.class, layoutClass))
				return "Grid";
			if ( Cacher.isAssignableFrom(FillLayout.class, layoutClass))
				return "Fill";
			if ( Cacher.isAssignableFrom(StackLayout.class, layoutClass))
				return "Stack";
			if ( Cacher.isAssignableFrom(FormLayout.class, layoutClass))
				return "Form";
		}
		return layoutType;
	}

	public void populateDOMNode(Element node, Document document, String indent) {

		if (layoutType == null || isCustom || !isSet())
			return;
		Element layout = document.createElement("Layout");
		String nind = "\n" + indent;
		String nind2 = nind + XMLWriter.INDENT;
		node.appendChild(document.createTextNode(nind));
		node.appendChild(layout);
		layout.setAttribute("class", getLayoutType());
		//don't use getPropertyNames since that will hide "fake" properties
		//like BoxLayout's "axis"
		//HashMap lprops = getProperties();
		//if (lprops != null && lprops.size() > 0) {
		Vector names = getPropertyNames();
		for (int i = 0; i < names.size(); i++) {
			String key = (String) names.elementAt(i);
			Object val = getPropertyValue(key);
			//Iterator it = lprops.keySet().iterator();
			//while (it.hasNext()) {
			//String key = (String) it.next();
			//Object val = lprops.get(key);
			Element lprop = document.createElement("Property");
			layout.appendChild(document.createTextNode(nind2));
			layout.appendChild(lprop);
			lprop.setAttribute("name", key);
			XMLWriter.setElementValue(lprop, val, document, indent);
			//}
			//}
		}
		if ("Box".equals(getLayoutType())) {
			Element lprop = document.createElement("Property");
			layout.appendChild(document.createTextNode(nind2));
			layout.appendChild(lprop);
			lprop.setAttribute("name", "axis");
			XMLWriter.setElementValue(lprop, getPropertyValue("axis"),
					document, indent);
		}
		layout.appendChild(document.createTextNode(nind));
	}

	public void setAttribute(String name, Object value) {
		//System.out.println("LW setAtt " + name + ", " + value);
		if (properties == null)
			properties = new HashMap();
		properties.put(name, value);
	}

	public int getAttributeCount() {
		if (properties == null || allNames == null)
			return 0;
		//using allNames takes into account synth and hidden props
		return allNames.size();
		//        return properties.size();
	}

	public String getAttribute(String name) {
		Object obj = getPropertyValue(name);
		//System.out.println("LW getAtt " + name + ", " + obj);
		if (obj == null)
			return null;
		return obj.toString();
	}

	public int getAttributeAsInt(String name) {
		try {
			Object prop = getPropertyValue(name);
			if (prop instanceof FieldWrapper) {
				FieldWrapper fw = (FieldWrapper) prop;
				return ((Integer) fw.getValue()).intValue();
			}
			return Integer.parseInt(getAttribute(name));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public String toString() {
		if (layoutType == null)
			return "No Layout";
		//if (isCustom())
		//return "Custom Layout";
		if ("Box".equals(layoutType)) {
			if (getAxis() == 0)
				return "Box - X";
			else
				return "Box - Y";
		}
		return layoutType; //+", "+name;
	}

	public void refreshFormComponent() {
		if (comp != null && !like("Group"))
			comp.setLayoutWrapper(this);
	}

	public Object getPropertyValue(Object id) {
		try {
			if (object instanceof com.jgoodies.forms.layout.FormLayout) {
				com.jgoodies.forms.layout.FormLayout fl = (com.jgoodies.forms.layout.FormLayout) object;
				if ("rowSpecs".equals(id))
					return getFormLayoutSpec(fl, false);
				if ("colSpecs".equals(id))
					return getFormLayoutSpec(fl, true);
			}
			Object o = super.getPropertyValue(id);
			//        System.out.println("GOT LW PROP " + id + ", " + o + ", " +
			//         hashCode());
			return o;
		} catch (Throwable t) {
			jiglooPlugin.handleError(t);
		}
		return null;
	}

	//public void setPropertyValue(Object propName, Object value) {
	//System.err.println("LAY WRAP SET PROP (direct) " + propName + ", " +
	// value + ", " + hashCode());
	//new Exception().printStackTrace();
	//super.setPropertyValue(propName, value);
	//}

	public boolean isJGForm() {
		return object instanceof com.jgoodies.forms.layout.FormLayout;
	}

	public com.jgoodies.forms.layout.FormLayout getJGFormLayout() {
		return (com.jgoodies.forms.layout.FormLayout) object;
	}

	public void resetPropertyValue(Object id) {
		if (!isPropertySet(id))
			return;
		setPropertyValue(id, defaultPropValues.get(id), true, true);
		setProps.remove(id);
		comp.getEditor().setDirtyAndUpdate();
		//		propsNeedingCodeUpdate.add(id);
		//		updateInCode((String)id);
		//		repairInCode(false);
	}

	public void setPropertyValue(Object propName, Object value) {
		super.setPropertyValue(propName, value);
		if (like("GridBag")) {
		    if (propName.equals("columns"))
		        setGridBagDimensions(getColumns(), false, true);
		    if (propName.equals("rows"))
		        setGridBagDimensions(getRows(), false, false);
		}
	}

	public void setPropertyValue(Object propName, Object value,
			boolean updateCode) {
		setPropertyValue(propName, value, updateCode, false);
	}

	public void setPropertyValue(Object propName, Object value,
			boolean updateCode, boolean force) {
		Object oldVal = getPropertyValue(propName);
		if (!force && (oldVal != null && oldVal.equals(value)))
			return;
//        System.err.println("LAY WRAP SET PROP " + propName + ", " + value
//				+ ", " + oldVal + ", " + getLayoutType() + ", " + hashCode()
//				+ ", update=" + updateCode+", force="+force);
		//      new Exception().printStackTrace();

		if (isJGForm()) {
			if (propName.equals("colSpecs")) {
				object = new com.jgoodies.forms.layout.FormLayout(
						(String) value, getFormLayoutSpec(getJGFormLayout(),
								false));
			} else if (propName.equals("rowSpecs")) {
				object = new com.jgoodies.forms.layout.FormLayout(
						getFormLayoutSpec(getJGFormLayout(), true),
						(String) value);
			} else {
				return;
			}
			if (updateCode)
				repairInCode(false);
			return;
		}
		if(like("Group")) {
			if(comp == null)
				System.out.println("LW comp is null for "+this);
			propName = comp.getEditor().convertGroupLayoutProp((String)propName, false);
		    if(propName.equals("verticalGroup")) {
		        vGroup = (LayoutGroup) value;
		        return;
		    }
		    if(propName.equals("horizontalGroup")) {
		        hGroup = (LayoutGroup) value;
		        return;
		    }
		}
		super.setPropertyValue(propName, value, updateCode);
		if (comp != null && comp.getEditor() != null
				&& comp.getEditor().isParsing()) {
			if (propName.equals("columnWeights")
					|| propName.equals("columnWidths")) {
				int cols = getArrayPropSize((String) propName);
				properties.put("columns", new Integer(cols));
			}
			if (propName.equals("rowWeights") || propName.equals("rowHeights")) {
				int cols = getArrayPropSize((String) propName);
				properties.put("rows", new Integer(cols));
			}
		}
		if (updateCode) {
			propsNeedingCodeUpdate.add(propName);
			updateInCode((String) propName);
		}
	}

	//    public void setName(String name) {
	//        System.out.println("LW: SET NAME "+name+", "+comp);
	//        super.setName(name);
	//    }

	public String getName() {
//		System.out.println("LW: GET NAME "+name+", "+comp);
		if (name != null)
			return name;
		name = comp.getNonBlockName() + "Layout";
		JavaCodeParser jcp = getJCP();
		if (jcp != null)
			name = jcp.getNextAvailableName(name);
		return name;
	}

	public void updateInCode(String propName) {
		if (isSyntheticProperty(propName))
			return;
		
		//don't update TableLayout properties, since these are in constructor
		if(like("Table") && !("hGap".equals(propName) || "vGap".equals(propName)))
			return;
		
		if (comp == null 
		        || comp.getEditor().isPopulating()
				|| !comp.getEditor().updatesJavaCode()
				|| !propsNeedingCodeUpdate.contains(propName))
			return;
		JavaCodeParser jcp = getJCP();
		if (!isSet()) {
			setSet(true);
			jcp.repairLayoutInCode(this, true);
		}
		jcp.updateInCode(this, propName);
		propsNeedingCodeUpdate.remove(propName);
	}

//	public void updateInCode() {
//		if (!comp.getEditor().updatesJavaCode())
//			return;
//		for (int i = 0; i < propNames.size(); i++) {
//			String pName = (String) propNames.elementAt(i);
//			if (!propsNeedingCodeUpdate.contains(pName))
//				propsNeedingCodeUpdate.add(pName);
//			updateInCode(pName);
//		}
//	}

	public void repairConstructorInCode() {
		if (!comp.getEditor().updatesJavaCode())
			return;
		getJCP().repairConstructor(this, false);
	}

	public void repairInCode(boolean changedType) {
		if (!comp.getEditor().updatesJavaCode())
			return;
		getJCP().repairLayoutInCode(this,
				changedType);
	}
	
	public void setNeedsTotalUpdate() {
		Vector propNames = getPropertyNames();
		if (propNames != null) {
			for (int n = 0; n < propNames.size(); n++) {
				String pName = (String) propNames.elementAt(n);
				if (!propsNeedingCodeUpdate.contains(pName))
					propsNeedingCodeUpdate.add(pName);
			}
		}
	}

	public void setAllPropertiesSet() {
		Vector propNames = getPropertyNames();
		if (propNames != null) {
			for (int n = 0; n < propNames.size(); n++) {
				String pName = (String) propNames.elementAt(n);
				if (!setProps.contains(pName))
					setProps.add(pName);
			}
		}
	}

	public void removeImport(String impName) {
		if (!comp.getEditor().updatesJavaCode())
			return;
		getJCP().removeImport(impName);
	}

	public boolean hasMethod(String id) {
		if(!id.startsWith(JavaCodeParser.METHOD_PREFIX))
			return false;
		id = id.substring(JavaCodeParser.METHOD_PREFIX.length());
		if(like("Group") && id.startsWith("linkSize"))
			return true;
		return false;
	}

	public boolean isPropertySet(Object id) {
		//return true;
		//        return setProps != null && setProps.contains(id);
		return super.isPropertySet(id);
	}

	public boolean needsUpdateInCode(String propName) {
		return propsNeedingCodeUpdate.contains(propName);
	}

	public boolean wasSet() {
		return wasSet;
	}

	public void setWasSet(boolean wasSet) {
		this.wasSet = wasSet;
	}

    public LayoutGroup getHGroup() {
        if(hGroup == null) {
            hGroup = new LayoutGroup("createSequentialGroup", null, this, false);
        }
        return hGroup;
    }
    
    public void setHGroup(LayoutGroup group) {
        hGroup = group;
//        if(!group.getLayoutWrapper().equals(this)) {
            group.setLayoutWrapper(this);
//        }
        if(group != null)
            group.setVertical(false);
        else
            System.err.println("h group set to null for "+this);
    }
    
    public LayoutGroup getVGroup() {
        if(vGroup == null) {
            vGroup = new LayoutGroup("createSequentialGroup", null, this, true);
        }
        return vGroup;
    }
    
    public void setVGroup(LayoutGroup group) {
        vGroup = group;
//        if(!group.getLayoutWrapper().equals(this)) {
            group.setLayoutWrapper(this);
//        }
        if(group != null)
            group.setVertical(true);
        else
            System.err.println("v group set to null for "+this);
    }

    public boolean isSubclassOf(Class clazz) {
        return  Cacher.isAssignableFrom(clazz, getMainClass());
    }

    public void setGridBagDimensions(int num, boolean setRowsCols, boolean cols) {
        try {
            String prop1 = "columnWeights";
            String prop2 = "columnWidths";
            String prop3 = "columns";
            if(!cols) {
                prop1 = "rowWeights";
                prop2 = "rowHeights";
                prop3 = "rows";
            }
            if (num > 0) {
                updateDoubleArray(num, 0.1, -1, prop1);
                updateIntegerArray(num, 7, -1, prop2);
                comp.redrawGridEdges();
                if(setRowsCols)
                    setPropertyValue(prop3, new Integer(num));
            }
        } catch (Throwable e) {
            jiglooPlugin.handleError(e);
        }
    }
    
    public void initGridBagDimensions(int rows, int cols) {
        if(like("GridBag")) {
            if(!isPropertySet("rowHeights") && !isPropertySet("rowWeights"))
                setGridBagDimensions(rows, true, false);
            if(!isPropertySet("columnWidths") && !isPropertySet("columnWeights"))
                setGridBagDimensions(cols, true, true);
        }
    }

    public void recreateLayoutManager() {
    	if(comp.isSwing()) {
        	setObject(null);
          Object obj = getObject(true);
          ((Container)comp.getComponent()).setLayout((LayoutManager) obj);
    	}
    }
    
    public void refreshGroupLayout() {
        Object obj = getObject();
        if(!(obj instanceof GroupLayout))
            return;
        GroupLayout gl = (GroupLayout) obj;
        
        getHGroup().cleanup();
        getVGroup().cleanup();
        
        hGroup.clearAnchors();
        vGroup.clearAnchors();

        hGroup.setLayoutWrapper(this);
        vGroup.setLayoutWrapper(this);

        hGroup.clear();
        vGroup.clear();
        
        gl.setHorizontalGroup(hGroup.populateGroup());
        gl.setVerticalGroup(vGroup.populateGroup());

    }

    /**
     * 
     */
    public void updateGroupLayout(JavaCodeParser jcp) {
        refreshGroupLayout();
        getHGroup().repairInCode(jcp);
        getVGroup().repairInCode(jcp);
    }

    public LayoutGroup getGroup(boolean vertical) {
        return vertical ? getVGroup() : getHGroup();
    }

    /**
     * 
     */
    public void updateGroupLayoutSizes() {
        if(!like("Group"))
            return;
        try {
            comp.getComponent().invalidate();
            comp.getComponent().validate();
            comp.getComponent().doLayout();
            if(hGroup != null)
                hGroup.updateSizes();
            if(vGroup != null)
                vGroup.updateSizes();
        } catch(Throwable t) {
            System.err.println("Error validating "+comp+" GroupLayout");
        }
    }

    /**
     * For pre-Java 6 GroupLayout
     * @param objects
     * @param direction
     */
    public void linkSize(Object[] objects, int direction) {
        if(direction == GroupLayout.HORIZONTAL)
            linkSize(SwingConstants.HORIZONTAL, objects, false);
        else
            linkSize(SwingConstants.VERTICAL, objects, false);
    }
    /**
     * For Java 6 GroupLayout. unlinkOthers optimises the linked groups, and is true when
     * this method is called from GroupLayoutUtils.alignSizes, when someone manually aligns
     * sizes - it removes any of the given objects from other linked sets.
     * @param direction
     * @param objects
     */
    public void linkSize(int direction, Object[] objects, boolean unlinkOthers) {
    	Vector linkedVec;
    	Vector objectVec = new Vector();
    	for (int i = 0; i < objects.length; i++) {
			objectVec.add(objects[i]);
		}
    	
        if(direction == SwingConstants.HORIZONTAL) {
            if(hLinkedElements == null)
                hLinkedElements = new Vector();
            linkedVec = hLinkedElements;
        } else {
            if(vLinkedElements == null)
                vLinkedElements = new Vector();
            linkedVec = vLinkedElements;
        }
        if(unlinkOthers) {
        	for(int i=0; i< linkedVec.size(); i++) {
        		Vector linked = (Vector)linkedVec.elementAt(i);
        		for(int j=0; j< objectVec.size(); j++) {
        			linked.remove(objectVec.elementAt(j));
        		}
        		if(linked.size() < 2) {
        			i--;
        			linkedVec.remove(linked);
        		}
        	}
        	//the way to unlink an object from any other objects is to call
        	//this method with a single object in the objects array.
        	if(objectVec.size() == 1)
        		return;
        }
        linkedVec.add(objectVec);
    }

    /**
     * For GroupLayout only
     * @param objects
     */
    public void linkSize(Object[] objects) {
        linkSize(SwingConstants.HORIZONTAL, objects, false);
        linkSize(SwingConstants.VERTICAL, objects, false);
    }
    
    public Vector getLinkedElements(boolean vertical) {
        return vertical ? vLinkedElements : hLinkedElements;
    }
    
    public String getLinkSizeCode(JavaCodeParser jcp, Vector linked, boolean vertical) {
    	jcp.addImport("javax.swing.SwingConstants");
    	jcp.addImport("java.awt.Component");
    	boolean java6 = comp.getEditor().useJava6GroupLayout();
    	String code = getNameInCode()+".linkSize(";
    	if(java6)
    		code += (vertical ? "SwingConstants.VERTICAL, " : "SwingConstants.HORIZONTAL, ");
    	code += "new Component[] {";
    	for(int i=0; i<linked.size(); i++) {
    		if(i != 0)
    			code += ", ";
    		FormComponent fc = (FormComponent)linked.elementAt(i);
    		code += fc.getNameOrMethodInCode();
    	}
    	code += "}";
    	if(!java6)
    		code += (vertical ? ", GroupLayout.VERTICAL" : ", GroupLayout.HORIZONTAL");
    	code += ");";
    	return code;
    }

    /**
     * @param comp
     */
    public void removeFromGroupLayout(FormComponent comp, boolean preserveSpacing) {
        if(like("Group")) {
            LayoutGroup lg = hGroup;
            lg = lg.getGroupContaining(comp);
            boolean update = false;
            if(lg != null) {
                lg.remove(comp, preserveSpacing);
                update = true;
            }
            lg = vGroup;
            lg = lg.getGroupContaining(comp);
            if(lg != null) {
                lg.remove(comp, preserveSpacing);
                update = true;
            }
            if(hLinkedElements != null) {
                for(int i=0; i< hLinkedElements.size(); i++) {
                    Vector linked = (Vector)hLinkedElements.elementAt(i);
                    for(int j=0; j< linked.size(); j++) {
                        if(linked.remove(comp))
                            update = true;
                    }
                    if(linked.size() < 2) {
                        i--;
                        hLinkedElements.remove(linked);
                    }
                }
            }
            if(vLinkedElements != null) {
                for(int i=0; i< vLinkedElements.size(); i++) {
                    Vector linked = (Vector)vLinkedElements.elementAt(i);
                    for(int j=0; j< linked.size(); j++) {
                        if(linked.remove(comp))
                            update = true;
                    }
                    if(linked.size() < 2) {
                        i--;
                        vLinkedElements.remove(linked);
                    }
                }
            }
            if(update) {
                recreateLayoutManager();
                refreshGroupLayout();
                repairInCode(false);
            }
        }
    }

	/**
	 * 
	 */
	public void dispose() {
		super.dispose();
		hGroup = null;
		vGroup = null;
		layoutClass = null;
		if(vLinkedElements != null)
			vLinkedElements.clear();
		if(hLinkedElements != null)
			hLinkedElements.clear();
		if(attributes != null)
			attributes.clear();
		container = null;
	}

}