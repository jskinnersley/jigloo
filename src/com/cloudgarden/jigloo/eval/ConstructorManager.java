/*
 * Created on Jun 16, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.awt.Component;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.controls.OrderableCTabFolder;
import com.cloudgarden.jigloo.controls.OrderableComposite;
import com.cloudgarden.jigloo.controls.OrderableGroup;
import com.cloudgarden.jigloo.controls.OrderableSashForm;
import com.cloudgarden.jigloo.controls.OrderableToolBar;
import com.cloudgarden.jigloo.interfaces.IWrapper;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.wrappers.LayoutWrapper;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConstructorManager {

    private static HashMap propMap = new HashMap();

    static {
        if (jiglooPlugin.canUseSwing()) {
            addToMap(JFrame.class, "String", "title");
            addToMap(JPanel.class, "LayoutManager", "layout");
            addToMap(JTree.class, "TreeModel", "model");

            addToMap(JTable.class, "int|int", "rowCount|columnCount");
            addToMap(JTable.class, "TableModel", "model");
            addToMap(JTable.class, "TableModel|TableColumnModel",
                    "model|columnModel");
            addToMap(JTable.class,
                    "TableModel|TableColumnModel|ListSelectionModel",
                    "model|columnModel|selectionModel");
            
//            addToMap(JTable.class, "Vector|Vector", "rowData|columnNames");
//            addToMap(JTable.class, "Object[]|Object[]", "rowData|columnNames");

//            addToMap(JList.class, "Object[]", "listData");

            if(jiglooPlugin.getJavaVersion() > 13) {
                addToMap(JSpinner.class, "SpinnerModel", "model");
            }
            
            addToMap(JScrollPane.class, "Component", "viewport");
            addToMap(JScrollPane.class, "Component|int|int",
                    "viewport|verticalScrollBarPolicy|horizontalScrollBarPolicy");
            addToMap(JScrollPane.class, "int|int",
                    "verticalScrollBarPolicy|horizontalScrollBarPolicy");

            addToMap(JComboBox.class, "ComboBoxModel", "model");
            addToMap(JList.class, "ListModel", "model");

            addToMap(JProgressBar.class, "int", "orientation");
            addToMap(JProgressBar.class, "int|int", "mininum|maximum");
            addToMap(JProgressBar.class, "int|int|int",
                    "orientation|mininum|maximum");
            addToMap(JSlider.class, "int", "orientation");
            addToMap(JSlider.class, "int|int", "mininum|maximum");
            addToMap(JSlider.class, "int|int|int", "mininum|maximum|value");
            addToMap(JSlider.class, "int|int|int|int",
                    "orientation|mininum|maximum|value");
            addToMap(JSeparator.class, "int", "orientation");

            addToMap(JSplitPane.class, "int", "orientation");
            addToMap(JSplitPane.class, "int|boolean",
                    "orientation|continuousLayout");
            addToMap(JSplitPane.class, "int|boolean|Component|Component",
                    "orientation|continuousLayout|leftComponent|rightComponent");
            addToMap(JSplitPane.class, "int|Component|Component",
                    "orientation|leftComponent|rightComponent");

            addToMap(JTabbedPane.class, "int", "tabPlacement");
            addToMap(JTabbedPane.class, "int|int",
                    "tabPlacement|tabLayoutPolicy");

            addToMap(JLabel.class, "Icon", "icon");
            addToMap(JLabel.class, "Icon|int", "icon|horizontalAlignment");
            addToMap(JLabel.class, "String", "text");
            addToMap(JLabel.class, "String|int", "text|horizontalAlignment");
            addToMap(JLabel.class, "String|Icon|int",
                    "text|icon|horizontalAlignment");

            addToMap(AbstractAction.class, "Icon", "icon");
            addToMap(AbstractAction.class, "String", "text");
            addToMap(AbstractAction.class, "String|Icon", "text|icon");
            
            addToMap(JButton.class, "Icon", "icon");
            addToMap(JButton.class, "Action", "action");
            addToMap(JButton.class, "String", "text");
            addToMap(JButton.class, "String|Icon", "text|icon");

            addToMap(JToggleButton.class, "Icon", "icon");
            addToMap(JToggleButton.class, "Icon|boolean", "icon|selected");
            addToMap(JToggleButton.class, "String|boolean", "text|selected");
            addToMap(JToggleButton.class, "Action", "action");
            addToMap(JToggleButton.class, "String", "text");
            addToMap(JToggleButton.class, "String|Icon", "text|icon");
            addToMap(JToggleButton.class, "String|Icon|boolean",
                    "text|icon|selected");

            addToMap(JMenuItem.class, "Icon", "icon");
            addToMap(JMenuItem.class, "Action", "action");
            addToMap(JMenuItem.class, "String", "text");
            addToMap(JMenuItem.class, "String|Icon", "text|icon");
            addToMap(JMenuItem.class, "String|int", "text|mnemonic");

            addToMap(JTextField.class, "int", "columns");
            addToMap(JTextField.class, "String", "text");
            addToMap(JTextField.class, "String|int", "text|columns");

            addToMap(JTextArea.class, "int", "columns");
            addToMap(JTextArea.class, "String", "text");
            addToMap(JTextArea.class, "String|int", "text|columns");

            addToMap(FormAttachment.class, "Control", "control");
            addToMap(FormAttachment.class, "Control|int", "control|offset");
            addToMap(FormAttachment.class, "Control|int|int", "control|offset|alignment");

            if (jiglooPlugin.canUseCWT()) {
                com.cloudgarden.jigloo.typewise.TypewiseManager
                        .addConstructorMappings();
            }
        }
    }

    public static void addToMap(Class cls, String types, String props) {
        HashMap map = (HashMap) propMap.get(cls);
        if (map == null) {
            map = new HashMap();
            propMap.put(cls, map);
        }
        map.put(types, props);
    }

    private static String truncate(String val) {
        int pos = val.lastIndexOf(".");
        if (pos < 0)
            return val;
        if (val.endsWith("Wrapper"))
            val = val.substring(0, val.length() - "Wrapper".length());
        if (val.endsWith(";"))
            val = val.substring(0, val.length() - 1);
        return val.substring(pos + 1);
    }

    public static String[] getProperties(Class cls, Object[] params) {
        Class[] types = getTypes(params);
        return getProperties(cls, params, types);
    }
    
    public static String[] getProperties(Class cls, Object[] params, Class[] types) {
        if (cls == null)
            return null;
        HashMap map = null;
        map = (HashMap) propMap.get(cls);
        while (map == null && cls != null) {
            cls = cls.getSuperclass();
            map = (HashMap) propMap.get(cls);
            if (map != null)
                break;
        }
        if (cls == null)
            return null;
        
        Constructor cons = findConstructor(cls, types);
        //maybe the constructor didn't match the given types, so
        //re-get types from constructor?
        if(cons == null)
        	return null;
        types = cons.getParameterTypes();
        String key = "";
        for (int i = 0; i < types.length; i++) {
            if (i != 0)
                key += "|";
            key += truncate(types[i].getName());
            if (types[i].isArray())
                key += "[]";
        }
        //System.out.println("ConstructorManager looking for " + key + ", " +
        // cls);
        String val = (String) map.get(key);
        String[] props = JiglooUtils.split("|", val);
        //if (props != null && props.length > 0)
        //System.out.println("ConstructorManager found " + props[0]);
        return props;
    }

    public static Constructor findConstructor(Class cls, Object[] params) {
        return findConstructor(cls, getTypes(params));
    }

    /**
     * This will return a constructor based on the parameter types, but if no
     * match is found, will return based on parameter count. Should always
     * return a constructor.
     */
    public static Constructor findConstructor(Class cls, Class[] types) {
        if (types == null) {
            try {
                Constructor con = cls.getDeclaredConstructor(null);
                con.setAccessible(true);
                return con;
            } catch (Throwable t) {
                return null;
            }
        }
        Constructor[] cons = cls.getDeclaredConstructors();
        Constructor guess0 = null;
        Constructor guess = null;
        for (int i = 0; i < cons.length; i++) {
            Constructor con = cons[i];
            Class[] ctypes = con.getParameterTypes();
            if (ctypes.length == 0) {
                guess0 = con;
            }

            if (ctypes.length != types.length)
                continue;

            boolean matched = true;
            boolean possMatched = false;
            for (int j = 0; j < types.length; j++) {
                Class tcls = types[j];
                if (tcls == null) {
                    possMatched = false;
                    continue;
                }
                //the argument passed in may be a FormComponent,or the name of
                //a FormComponent (a String). If it's a FormComponent, then
                // treat
                //as if it is a Component - if it's a String, treat as a
                // possible match,
                //but wait and see if another constructor is an exact match
                // first.
                if (jiglooPlugin.canUseSwing()
                        && ctypes[j].equals(Component.class)) {
                    if (tcls.equals(String.class)) {
                        possMatched = true;
                        continue;
                    }
                    if (tcls.equals(FormComponent.class)) {
                        possMatched = true;
                        continue;
                    }
                }
                if (!ClassUtils.isAssignableFrom(ctypes[j], tcls)) {
                    possMatched = false;
                    matched = false;
                    break;
                }
                if (!ctypes[j].equals(tcls)) {
                	//if the classes don't match, but ctypes[i] isAssignableFrom tcls,
                	//then don't count this as a match, but as a possible match
                	//(for example, JFormattedTextField has a (Object) and a (Format)
                	//constructor, and this allows the Object constructor to be passed
                	//over if we give it a Format parameter
                	possMatched = true;
                    matched = false;
                }
            }
            if (possMatched) {
                guess = con;
            } else if (matched) {
            	con.setAccessible(true);
                return con;
            }
        }
        if (guess != null) {
        	guess.setAccessible(true);
            return guess;
        }
        if (guess0 != null) {
        	guess0.setAccessible(true);
            return guess0;
        }
        return null;
    }

    public static Class[] getTypes(Object[] params) {
        if (params == null)
            return null;
        Class[] pTypes = new Class[params.length];
        for (int i = 0; i < pTypes.length; i++) {
            Object param = params[i];
            if (param == null)
                continue;
            Class cls = param.getClass();
            if (param instanceof FormComponent) {
                FormComponent fc = (FormComponent) param;
                Object nvo = fc.getNonVisualObject();
                if(nvo instanceof ArrayHolder && ((ArrayHolder)nvo).getRawArray() != null) {
                	cls = ((ArrayHolder)nvo).getRawArray().getClass();
                } else {
                	cls = fc.getMainClass();
                }
                if (cls.equals(OrderableGroup.class))
                    cls = Group.class;
                else if (cls.equals(OrderableComposite.class))
                    cls = Composite.class;
                else if (cls.equals(OrderableCTabFolder.class))
                    cls = CTabFolder.class;
                else if (cls.equals(OrderableSashForm.class))
                    cls = SashForm.class;
                else if (cls.equals(OrderableToolBar.class))
                    cls = ToolBar.class;
                else if (cls.equals(IconWrapper.class))
                    cls = Icon.class;
                else if (cls.equals(ImageWrapper.class)) {
                	if(fc.isSwing())
                		cls = java.awt.Image.class;
                	else
                		cls = Image.class;
                }
            } else if (param instanceof ConstructorHolder) {
                cls = ((ConstructorHolder)param).getConstructor().getDeclaringClass();
            } else if (param instanceof ArrayHolder) {
                ArrayHolder ah = (ArrayHolder)param;
                if(ah.getRawArray() != null)
                    cls = ah.getRawArray().getClass();
            } else if (param instanceof RootMethodCall) {
                cls = ((RootMethodCall)param).getReturnClass();
            } else if (IWrapper.class.isAssignableFrom(cls)) {
                IWrapper wr = (IWrapper) param;
                cls = wr.getValue(null).getClass();
            } else if (param instanceof LayoutWrapper) {
                LayoutWrapper lw = (LayoutWrapper) param;
                cls = lw.getMainClass();
            } else if (IconWrapper.class.isAssignableFrom(cls)) {
                cls = Icon.class;
            } else if (cls.equals(Boolean.class)) {
                cls = boolean.class;
            } else if (cls.equals(Integer.class)) {
                cls = int.class;
            } else if (cls.equals(Float.class)) {
                cls = float.class;
            } else if (cls.equals(Double.class)) {
                cls = double.class;
            }
            pTypes[i] = cls;
        }
        return pTypes;
    }

	public static String getDescription(Constructor con) {
		String str = con.getName()+"(";
		Class[] params = con.getParameterTypes();
		for (int j = 0; j < params.length; j++) {
			if(j != 0)
				str += ", ";
			str += JiglooUtils.getUnqualifiedName(params[j].getName());
		}
		str += ")";
		return str;
	}
}