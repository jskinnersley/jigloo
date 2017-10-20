/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.util;
import java.awt.Adjustable;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.layout.AnchorConstraint;
import com.jgoodies.forms.layout.CellConstraints;
/**
 * @author jsk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FieldManager {
	
	static HashMap fieldProperties = new HashMap();
	
	static final String[] hAlignConsts =
		new String[] {
			"SwingConstants.LEFT",
			"SwingConstants.CENTER",
			"SwingConstants.RIGHT",
			"SwingConstants.LEADING",
			"SwingConstants.TRAILING" };
	
	static final String[] vAlignConsts =
		new String[] { "SwingConstants.TOP", "SwingConstants.CENTER", "SwingConstants.BOTTOM" };
	
	static final String[] swtAlignConsts = new String[] { "SWT.LEFT", "SWT.RIGHT", "SWT.CENTER", "SWT.UP", "SWT.DOWN" };
	
	static final String[] swtLayoutAlignConsts = new String[] { "SWT.HORIZONTAL", "SWT.VERTICAL" };
	
	static final String[] hScrollBarPolicyConsts =
		new String[] {
			"JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED",
			"JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS",
			"JScrollPane.HORIZONTAL_SCROLLBAR_NEVER" };
	
	static final String[] vScrollBarPolicyConsts =
		new String[] {
			"JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED",
			"JScrollPane.VERTICAL_SCROLLBAR_ALWAYS",
			"JScrollPane.VERTICAL_SCROLLBAR_NEVER" };
	
	static {

		putFieldProp(Label.class, "alignment", swtAlignConsts);
		putFieldProp(CLabel.class, "alignment", swtAlignConsts);
		putFieldProp(Button.class, "alignment", swtAlignConsts);
		putFieldProp(FillLayout.class, "type", swtLayoutAlignConsts);
		putFieldProp(RowLayout.class, "type", swtLayoutAlignConsts);
		putFieldProp(Widget.class, "orientation", swtLayoutAlignConsts);
		putFieldProp(
			FormAttachment.class,
			"alignment",
			new String[] { "SWT.DEFAULT", "SWT.TOP", "SWT.BOTTOM", "SWT.LEFT", "SWT.RIGHT", "SWT.CENTER" });
		putFieldProp(
			GridData.class,
			"horizontalAlignment",
			new String[] {
				"org.eclipse.swt.layout.GridData.BEGINNING",
				"org.eclipse.swt.layout.GridData.END",
				"org.eclipse.swt.layout.GridData.CENTER",
				"org.eclipse.swt.layout.GridData.FILL" });
		putFieldProp(
			GridData.class,
			"verticalAlignment",
			new String[] {
				"org.eclipse.swt.layout.GridData.BEGINNING",
				"org.eclipse.swt.layout.GridData.END",
				"org.eclipse.swt.layout.GridData.CENTER",
				"org.eclipse.swt.layout.GridData.FILL" });

		if (jiglooPlugin.canUseSwing()) {
			putFieldProp(JScrollPane.class, "horizontalScrollBarPolicy", hScrollBarPolicyConsts);
			putFieldProp(JScrollPane.class, "verticalScrollBarPolicy", vScrollBarPolicyConsts);
			putFieldProp(JComponent.class, "horizontalTextPosition", hAlignConsts);
			putFieldProp(JComponent.class, "horizontalAlignment", hAlignConsts);
			putFieldProp(JComponent.class, "verticalTextPosition", vAlignConsts);
			putFieldProp(JComponent.class, "verticalAlignment", vAlignConsts);
			putFieldProp(
				FlowLayout.class,
				"alignment",
				new String[] { "java.awt.FlowLayout.LEFT", "java.awt.FlowLayout.RIGHT", "java.awt.FlowLayout.CENTER" });
			putFieldProp(
				JComponent.class,
				"debugGraphicsOptions",
				new String[] {
					"DebugGraphics.BUFFERED_OPTION",
					"DebugGraphics.FLASH_OPTION",
					"DebugGraphics.LOG_OPTION",
					"DebugGraphics.NONE_OPTION" });

			if (JiglooUtils.isJava13()) {
				putFieldProp(
					JFrame.class,
					"defaultCloseOperation",
					new String[] {
						"WindowConstants.DO_NOTHING_ON_CLOSE",
						"WindowConstants.HIDE_ON_CLOSE",
						"WindowConstants.DISPOSE_ON_CLOSE" });
			} else {
				putFieldProp(
						JFrame.class,
						"defaultCloseOperation",
						new String[] {
							"WindowConstants.DO_NOTHING_ON_CLOSE",
							"WindowConstants.HIDE_ON_CLOSE",
							"WindowConstants.DISPOSE_ON_CLOSE",
							"WindowConstants.EXIT_ON_CLOSE" });
				putFieldProp(
						JFrame.class,
						"state",
						new String[] {
								"java.awt.Frame.NORMAL",
								"java.awt.Frame.ICONIFIED"});
				putFieldProp(
						JFrame.class,
						"extendedState",
						new String[] {
								"java.awt.Frame.NORMAL",
								"java.awt.Frame.ICONIFIED",
								"java.awt.Frame.MAXIMIZED_HORIZ",
								"java.awt.Frame.MAXIMIZED_VERT",
								"java.awt.Frame.MAXIMIZED_BOTH"
								});
			}

			putFieldProp(
				JInternalFrame.class,
				"defaultCloseOperation",
				new String[] {
					"WindowConstants.DO_NOTHING_ON_CLOSE",
					"WindowConstants.HIDE_ON_CLOSE",
					"WindowConstants.DISPOSE_ON_CLOSE" });

			putFieldProp(
				JOptionPane.class,
				"messageType",
				new String[] {
					"JOptionPane.ERROR_MESSAGE",
					"JOptionPane.INFORMATION_MESSAGE",
					"JOptionPane.WARNING_MESSAGE",
					"JOptionPane.QUESTION_MESSAGE",
					"JOptionPane.PLAIN_MESSAGE" });

			putFieldProp(
				JOptionPane.class,
				"optionType",
				new String[] {
					"JOptionPane.DEFAULT_OPTION",
					"JOptionPane.YES_NO_OPTION",
					"JOptionPane.YES_NO_CANCEL_OPTION",
					"JOptionPane.OK_CANCEL_OPTION" });

			putFieldProp(
				JDialog.class,
				"defaultCloseOperation",
				new String[] {
					"WindowConstants.DO_NOTHING_ON_CLOSE",
					"WindowConstants.HIDE_ON_CLOSE",
					"WindowConstants.DISPOSE_ON_CLOSE" });

			putFieldProp(
				JSplitPane.class,
				"orientation",
				new String[] { "JSplitPane.HORIZONTAL_SPLIT", "JSplitPane.VERTICAL_SPLIT" });
			putFieldProp(
				JScrollBar.class,
				"orientation",
				new String[] { "SwingConstants.HORIZONTAL", "SwingConstants.VERTICAL" });
			putFieldProp(
				JProgressBar.class,
				"orientation",
				new String[] { "SwingConstants.HORIZONTAL", "SwingConstants.VERTICAL" });
			putFieldProp(
				JSeparator.class,
				"orientation",
				new String[] { "SwingConstants.HORIZONTAL", "SwingConstants.VERTICAL" });
			putFieldProp(
				JToolBar.class,
				"orientation",
				new String[] { "SwingConstants.HORIZONTAL", "SwingConstants.VERTICAL" });
			putFieldProp(
				JTabbedPane.class,
				"tabPlacement",
				new String[] { "JTabbedPane.TOP", "JTabbedPane.BOTTOM", "JTabbedPane.LEFT", "JTabbedPane.RIGHT" });
			putFieldProp(
				JTabbedPane.class,
				"tabLayoutPolicy",
				new String[] { "JTabbedPane.WRAP_TAB_LAYOUT", "JTabbedPane.SCROLL_TAB_LAYOUT" });
			putFieldProp(
				Adjustable.class,
				"orientation",
				new String[] {
					"java.awt.Adjustable.NO_ORIENTATION",
					"java.awt.Adjustable.HORIZONTAL",
					"java.awt.Adjustable.VERTICAL" });
			putFieldProp(
					JTable.class,
					"autoResizeMode",
					new String[] { "JTable.AUTO_RESIZE_ALL_COLUMNS", 
					        "JTable.AUTO_RESIZE_LAST_COLUMN" , "JTable.AUTO_RESIZE_NEXT_COLUMN",
					        "JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS", "JTable.AUTO_RESIZE_OFF"});
			putFieldProp(
					JList.class,
					"layoutOrientation",
					new String[] { "JList.HORIZONTAL_WRAP", "JList.VERTICAL", "JList.VERTICAL_WRAP" });
			putFieldProp(
				JList.class,
				"selectionMode",
				new String[] {
					"ListSelectionModel.SINGLE_SELECTION",
					"ListSelectionModel.SINGLE_INTERVAL_SELECTION",
					"ListSelectionModel.MULTIPLE_INTERVAL_SELECTION" });
			putFieldProp(
				TitledBorder.class,
				"titlePosition",
				new String[] {
					"javax.swing.border.TitledBorder.ABOVE_TOP",
					"javax.swing.border.TitledBorder.TOP",
					"javax.swing.border.TitledBorder.BELOW_TOP",
					"javax.swing.border.TitledBorder.ABOVE_BOTTOM",
					"javax.swing.border.TitledBorder.BOTTOM",
					"javax.swing.border.TitledBorder.BELOW_BOTTOM",
					"javax.swing.border.TitledBorder.DEFAULT_POSITION" });
			putFieldProp(
				TitledBorder.class,
				"titleJustification",
				new String[] {
					"javax.swing.border.TitledBorder.LEFT",
					"javax.swing.border.TitledBorder.LEADING",
					"javax.swing.border.TitledBorder.CENTER",
					"javax.swing.border.TitledBorder.TRAILING",
					"javax.swing.border.TitledBorder.RIGHT",
					"javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION" });
			putFieldProp(
				BevelBorder.class,
				"bevelType",
				new String[] { "javax.swing.border.BevelBorder.RAISED", "javax.swing.border.BevelBorder.LOWERED" });
			putFieldProp(
				EtchedBorder.class,
				"etchType",
				new String[] { "javax.swing.border.BevelBorder.RAISED", "javax.swing.border.BevelBorder.LOWERED" });

			String[] anchFields =
				new String[] {
					"com.cloudgarden.layout.AnchorConstraint.ANCHOR_NONE",
					"com.cloudgarden.layout.AnchorConstraint.ANCHOR_ABS",
					"com.cloudgarden.layout.AnchorConstraint.ANCHOR_REL" };
			putFieldProp(AnchorConstraint.class, "topType", anchFields);
			putFieldProp(AnchorConstraint.class, "rightType", anchFields);
			putFieldProp(AnchorConstraint.class, "bottomType", anchFields);
			putFieldProp(AnchorConstraint.class, "leftType", anchFields);

			anchFields =
				new String[] {
					"com.jgoodies.forms.layout.CellConstraints.DEFAULT",
					"com.jgoodies.forms.layout.CellConstraints.TOP",
					"com.jgoodies.forms.layout.CellConstraints.CENTER",
					"com.jgoodies.forms.layout.CellConstraints.BOTTOM",
					"com.jgoodies.forms.layout.CellConstraints.FILL"
					};
			putFieldProp(CellConstraints.class, "vAlign", anchFields);

			anchFields =
				new String[] {
					"com.jgoodies.forms.layout.CellConstraints.DEFAULT",
					"com.jgoodies.forms.layout.CellConstraints.LEFT",
					"com.jgoodies.forms.layout.CellConstraints.CENTER",
					"com.jgoodies.forms.layout.CellConstraints.RIGHT",
					"com.jgoodies.forms.layout.CellConstraints.FILL"};
			putFieldProp(CellConstraints.class, "hAlign", anchFields);

			putFieldProp(
				GridBagConstraints.class,
				"gridwidth",
				new String[] {
					"java.awt.GridBagConstraints.REMAINDER",
					"java.awt.GridBagConstraints.RELATIVE",
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"9",
					"10",
					"11",
					"12",
					"13",
					"14",
					"15",
					"16",
					"17",
					"18",
					"19",
					"20" });
			
			putFieldProp(
				GridBagConstraints.class,
				"gridheight",
				new String[] {
					"java.awt.GridBagConstraints.REMAINDER",
					"java.awt.GridBagConstraints.RELATIVE",
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"9",
					"10",
					"11",
					"12",
					"13",
					"14",
					"15",
					"16",
					"17",
					"18",
					"19",
					"20" });
			putFieldProp(
				GridBagConstraints.class,
				"fill",
				new String[] {
					"java.awt.GridBagConstraints.HORIZONTAL",
					"java.awt.GridBagConstraints.VERTICAL",
					"java.awt.GridBagConstraints.BOTH",
					"java.awt.GridBagConstraints.NONE" });
			
			if (JiglooUtils.isJava13()) {
				putFieldProp(
					GridBagConstraints.class,
					"anchor",
					new String[] {
						"java.awt.GridBagConstraints.NORTH",
						"java.awt.GridBagConstraints.SOUTH",
						"java.awt.GridBagConstraints.WEST",
						"java.awt.GridBagConstraints.EAST",
						"java.awt.GridBagConstraints.NORTHWEST",
						"java.awt.GridBagConstraints.NORTHEAST",
						"java.awt.GridBagConstraints.SOUTHWEST",
						"java.awt.GridBagConstraints.SOUTHEAST",
						"java.awt.GridBagConstraints.CENTER" });
			} else {
				putFieldProp(
					GridBagConstraints.class,
					"anchor",
					new String[] {
						"java.awt.GridBagConstraints.NORTH",
						"java.awt.GridBagConstraints.SOUTH",
						"java.awt.GridBagConstraints.WEST",
						"java.awt.GridBagConstraints.EAST",
						"java.awt.GridBagConstraints.NORTHWEST",
						"java.awt.GridBagConstraints.NORTHEAST",
						"java.awt.GridBagConstraints.SOUTHWEST",
						"java.awt.GridBagConstraints.SOUTHEAST",
						"java.awt.GridBagConstraints.CENTER",
						"java.awt.GridBagConstraints.PAGE_START",
						"java.awt.GridBagConstraints.PAGE_END",
						"java.awt.GridBagConstraints.LINE_START",
						"java.awt.GridBagConstraints.LINE_END",
						"java.awt.GridBagConstraints.FIRST_LINE_START",
						"java.awt.GridBagConstraints.FIRST_LINE_END",
						"java.awt.GridBagConstraints.LAST_LINE_START",
						"java.awt.GridBagConstraints.LAST_LINE_END" });
			}
		}

	};

	public static void putFieldProp(Class clazz, String name, String[] fields) {
		HashMap map = (HashMap) fieldProperties.get(clazz);
		if (map == null)
			map = new HashMap();
		map.put(name, fields);
		fieldProperties.put(clazz, map);
	}
	public static String[] getFieldOptions(String propName, Class mc) {
		//System.out.println("GET FIELD OPTIONS " + mc + ", " + propName);
		while (mc != null) {
			HashMap map = (HashMap) fieldProperties.get(mc);
			if (map != null) {
				String[] names = (String[]) map.get(propName);
				if (names != null)
					return names;
			}
			mc = mc.getSuperclass();
		}
		return null;
	}
	
	private static final Field[] swtFields = SWT.class.getDeclaredFields();
	
	public static String getSWTFieldName(int field) {
		for (int i = 0; i < swtFields.length; i++) {
			try {
				if (swtFields[i].getInt(null) == field)
					return swtFields[i].getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "UNKNOWN";
	}
}
