/*
 * Created on Jul 30, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.actions.FormStyleAction;
import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SWTStyleManager {

	private static final HashMap styleMap = new HashMap();
	static {

		setStylesForClass(new String[][] { { "BAR", "DROP_DOWN", "POP_UP" }, {
				"NO_RADIO_GROUP" }, {
				"LEFT_TO_RIGHT", "RIGHT_TO_LEFT" }
		}, Menu.class);

		setStylesForClass(new String[][] { { "ARROW", "CHECK", "PUSH", "RADIO", "TOGGLE" }, {
				"FLAT" }, {
				"UP", "DOWN", "LEFT", "RIGHT", "CENTER" }
		}, Button.class);

		setStylesForClass(new String[][] { { "MULTI", "SINGLE" }, {
				"CENTER", "LEFT", "RIGHT" }, {
				"READ_ONLY" }, {
				"WRAP" }
		}, Text.class);

		setStylesForClass(new String[][] { { "MULTI", "SINGLE" }, {
				"FULL_SELECTION" }, {
				"READ_ONLY" }, {
				"WRAP" }
		}, StyledText.class);

		setStylesForClass(new String[][] { {
			"SHADOW_ETCHED_IN",
			"SHADOW_ETCHED_OUT",
			"SHADOW_IN",
			"SHADOW_OUT",
			"SHADOW_NONE" }
		}, Group.class);

		setStylesForClass(new String[][] { { "BORDER" }
		}, Control.class);

		setStylesForClass(new String[][] { { "DROP_DOWN", "SIMPLE" }, {
				"READ_ONLY" }
		}, Combo.class);

		setStylesForClass(new String[][] { { "FLAT" }, {
				"READ_ONLY" }
		}, CCombo.class);

		setStylesForClass(new String[][] { { "FLAT" }, {
				"CLOSE" }, {
				"TOP", "BOTTOM" }
		}, CTabFolder.class);

		setStylesForClass(new String[][] { { "NO_RADIO_GROUP" }, { "EMBEDDED" }
		}, Composite.class);

		setStylesForClass(new String[][] { { "NO_BACKGROUND" }, {
				"NO_FOCUS" }, {
				"NO_MERGE_PAINTS" }, {
				"NO_REDRAW_RESIZE" }
		}, Canvas.class);

		setStylesForClass(new String[][] { { "SINGLE", "MULTI" }
		}, List.class);

		setStylesForClass(new String[][] { { "H_SCROLL" }, {
				"V_SCROLL" }
		}, Scrollable.class);

		setStylesForClass(new String[][] { { "SEPARATOR" }, {
				"HORIZONTAL", "VERTICAL" }, {
				"SHADOW_IN", "SHADOW_OUT", "SHADOW_NONE" }, {
				"CENTER", "LEFT", "RIGHT" }, {
				"WRAP" }
		}, Label.class);

		setStylesForClass(new String[][] { { "SHADOW_IN", "SHADOW_OUT", "SHADOW_NONE" }, {
				"CENTER", "LEFT", "RIGHT" }
		}, CLabel.class);

		setStylesForClass(new String[][] { { "HORIZONTAL", "VERTICAL" }
		}, SashForm.class);

		setStylesForClass(new String[][] { { "HORIZONTAL", "VERTICAL" }
		}, Sash.class);

		setStylesForClass(new String[][] { { "HORIZONTAL", "VERTICAL" }
		}, Scale.class);

		setStylesForClass(new String[][] { { "HORIZONTAL", "VERTICAL" }
		}, Slider.class);

		setStylesForClass(new String[][] { { "HORIZONTAL", "VERTICAL" }, {
				"SMOOTH" }, {
				"INDETERMINATE" }
		}, ProgressBar.class);

		setStylesForClass(new String[][] { { "SINGLE", "MULTI" }, {
				"CHECK" }, {
				"FULL_SELECTION" }, {
				"HIDE_SELECTION" }
		}, Table.class);

		setStylesForClass(new String[][] { { "CENTER", "LEFT", "RIGHT" }
		}, TableColumn.class);

		setStylesForClass(new String[][] { { "FLAT" }, {
				"WRAP" }, {
				"RIGHT" }, {
				"SHADOW_OUT" }, {
				"HORIZONTAL", "VERTICAL" }
		}, ToolBar.class);

		setStylesForClass(new String[][] { { "PUSH", "CHECK", "RADIO", "SEPARATOR", "DROP_DOWN" }
		}, ToolItem.class);

		setStylesForClass(new String[][] { { "CASCADE", "CHECK", "PUSH", "RADIO", "SEPARATOR" }
		}, MenuItem.class);

		setStylesForClass(new String[][] { { "DROP_DOWN" }
		}, CoolItem.class);

		setStylesForClass(new String[][] { { "SINGLE", "MULTI" }, {
				"CHECK" }
		}, Tree.class);

		setStylesForClass(new String[][] { { "SINGLE", "MULTI" }, {
				"CHECK" }, {
				"FULL_SELECTION" }
		}, TableTree.class);

	}

	private static void setStylesForClass(String[][] swtStyles, Class widgetClass) {
		Field[][] fields = new Field[swtStyles.length][];
		for (int i = 0; i < swtStyles.length; i++) {
			fields[i] = new Field[swtStyles[i].length];
			for (int j = 0; j < swtStyles[i].length; j++) {
				try {
					fields[i][j] = SWT.class.getField(swtStyles[i][j]);
				} catch (NoSuchFieldException e) {
					if(jiglooPlugin.DEBUG_EXTRA)
						System.err.println("No Such Field SWT." + swtStyles[i][j]);
				}
			}
		}
		styleMap.put(widgetClass, fields);
	}

	public static Field[][] getStyles(Class widgetClass) {
		//TODO add in all fields from superclasses (eg Scrollable for Composite)
		Vector maps = new Vector();
		Field[][] map;
		Class sup = widgetClass;
		while (sup != null) {
			map = (Field[][]) styleMap.get(sup);
			if (map != null) {
				for (int j = 0; j < map.length; j++) {
					maps.add(map[j]);
				}
			}
			sup = sup.getSuperclass();
		}
		Field[][] nmap = new Field[maps.size()][];
		for (int i = 0; i < maps.size(); i++) {
			nmap[i] = (Field[]) maps.elementAt(i);
		}
		return nmap;
		//return (Field[][]) styleMap.get(widgetClass);
	}

	public static String getStyleString(FormComponent comp, boolean includeSWT) {
		if (comp.isSwing())
			return "";
		//		if (comp.getControl() == null) {
		//			if (includeSWT)
		//				return "SWT.NULL";
		//			return "NULL";
		//		}
		int style = comp.getStyle();
		Class widgetClass = comp.getMainClass();
		StringBuffer str = new StringBuffer();
		Field[][] fields = getStyles(widgetClass);
		if (fields == null)
			return "";
		for (int i = 0; i < fields.length; i++) {
			try {
				for (int j = 0; j < fields[i].length; j++) {
					Field field = fields[i][j];
					if (field != null) {
						int fstyle = field.getInt(null);
						if ((fstyle & style) == fstyle) {
							if (str.length() != 0)
								str.append(" | ");
							if (includeSWT)
								str.append("SWT.");
							str.append(field.getName());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (str.length() == 0) {
			if (includeSWT)
				return "SWT.NONE";
			return "";
		}
		return str.toString();
	}

	public static void addStyleMenuItems(FormEditor editor, Class widgetClass, int style, IMenuManager menuMgr) {
		Field[][] fields = getStyles(widgetClass);
		if (fields == null)
			return;
		MenuManager smm = null;

		smm = new MenuManager("Set/Change style...");
		menuMgr.add(smm);
		for (int i = 0; i < fields.length; i++) {
			try {
				Field oldField = null;
				for (int j = 0; j < fields[i].length; j++) {
					Field field = fields[i][j];
					if (field != null) {
						int fstyle = field.getInt(null);
						if ((fstyle & style) == fstyle)
							oldField = field;
					}
				}
				for (int j = 0; j < fields[i].length; j++) {
					Field field = fields[i][j];
					if(field != null)
						smm.add(new FormStyleAction(editor, field, oldField));
				}
				smm.add(new Separator());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
