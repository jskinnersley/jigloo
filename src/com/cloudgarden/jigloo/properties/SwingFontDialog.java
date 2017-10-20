package com.cloudgarden.jigloo.properties;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.resource.SWTResourceManagerInternal;

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
public class SwingFontDialog extends org.eclipse.swt.widgets.Dialog {
	private List sizeList;
	private List styleList;
	private List fontList;
	private CLabel sampleLabel;
	private Button cancelButton;
	private Button okButton;
	private Composite composite1;
	private Group group4;
	private Group group3;
	private Group group2;
	private Group group1;
	private Shell dialogShell;

	public SwingFontDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open() {
		try {
			preInitGUI();
			Shell parent = getParent();
			dialogShell =
				new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManagerInternal.registerResourceUser(dialogShell);
            }

			dialogShell.setText(getText());
			//Creating group1
			group1 = new Group(dialogShell, SWT.NULL);
			//Creating fontList
			fontList = new List(group1, SWT.SIMPLE
				| SWT.H_SCROLL
				| SWT.V_SCROLL
				| SWT.BORDER);
			fontList.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    fontListWidgetSelected(e);
                }
			    });
			//Creating group2
			group2 = new Group(dialogShell, SWT.NULL);
			//Creating styleList
			styleList = new List(group2, SWT.SIMPLE
				| SWT.H_SCROLL
				| SWT.V_SCROLL
				| SWT.BORDER);
			styleList.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    styleListWidgetSelected(e);
                }
			    });
			//Creating group3
			group3 = new Group(dialogShell, SWT.NULL);
			//Creating sizeList
			sizeList = new List(group3, SWT.SIMPLE
				| SWT.H_SCROLL
				| SWT.V_SCROLL
				| SWT.BORDER);
			sizeList.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    sizeListWidgetSelected(e);
                }
			    });
			//Creating group4
			group4 = new Group(dialogShell, SWT.NULL);
			//Creating sampleLabel
			sampleLabel = new CLabel(group4, SWT.SHADOW_IN | SWT.BORDER);
			//Creating composite1
			composite1 = new Composite(dialogShell, SWT.NULL);
			//Creating okButton
			okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
			//Creating cancelButton
			cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);

			//Setting layout constraints for group1
			GridData group1LData = new GridData();
			group1LData.verticalAlignment = GridData.FILL;
			group1LData.horizontalAlignment = GridData.FILL;
//			group1LData.heightHint = 300;
			group1LData.grabExcessHorizontalSpace = true;
			group1LData.grabExcessVerticalSpace = true;
			group1.setLayoutData(group1LData);
			//Setting properties for group1
			group1.setText("Font Name");

			//Setting properties for fontList
			//Setting Absolute layout for fontList
			//Setting Fill layout for group1
			FillLayout group1Layout = new FillLayout(256);
			group1.setLayout(group1Layout);
			group1Layout.type = SWT.HORIZONTAL;
			group1.layout();

			//Setting layout constraints for group2
			GridData group2LData = new GridData();
			group2LData.verticalAlignment = GridData.FILL;
			group2LData.horizontalAlignment = GridData.CENTER;
			group2LData.grabExcessVerticalSpace = true;
			group2LData.widthHint = 83;
			group2.setLayoutData(group2LData);
			//Setting properties for group2
			group2.setText("Style");

			//Setting properties for styleList
			//Setting Absolute layout for styleList
			//Setting Fill layout for group2
			FillLayout group2Layout = new FillLayout(256);
			group2.setLayout(group2Layout);
			group2Layout.type = SWT.HORIZONTAL;

			//Setting layout constraints for group3
			GridData group3LData = new GridData();
			group3LData.verticalAlignment = GridData.FILL;
			group3LData.horizontalAlignment = GridData.CENTER;
			group3LData.grabExcessVerticalSpace = true;
			group3LData.widthHint = 65;
			group3.setLayoutData(group3LData);
			//Setting properties for group3
			group3.setText("Size");

			//Setting properties for sizeList
			//Setting Absolute layout for sizeList
			//Setting Fill layout for group3
			FillLayout group3Layout = new FillLayout(256);
			group3.setLayout(group3Layout);
			group3Layout.type = SWT.HORIZONTAL;

			//Setting layout constraints for group4
			GridData group4LData = new GridData();
			group4LData.horizontalAlignment = GridData.FILL;
			group4LData.horizontalSpan = 3;
			group4LData.verticalAlignment = GridData.FILL;
            group4LData.grabExcessVerticalSpace = true;
			group4.setLayoutData(group4LData);
			//Setting properties for group4
			group4.setText("Preview");

			//Setting properties for sampleLabel
			sampleLabel.setText("ABCDEFabcdef");
			sampleLabel.setAlignment(SWT.CENTER);
			//Setting Fill layout for group4
			FillLayout group4Layout = new FillLayout(256);
			group4.setLayout(group4Layout);
			group4Layout.type = SWT.HORIZONTAL;

			//Setting layout constraints for composite1
			GridData composite1LData = new GridData();
			composite1LData.verticalAlignment = GridData.FILL;
			composite1LData.horizontalAlignment = GridData.END;
			composite1LData.horizontalSpan = 3;
			composite1.setLayoutData(composite1LData);

			//Setting layout constraints for okButton
			GridData okButtonLData = new GridData();
			okButtonLData.verticalAlignment = GridData.FILL;
			okButtonLData.horizontalAlignment = GridData.FILL;
			okButtonLData.grabExcessHorizontalSpace = true;
			okButton.setLayoutData(okButtonLData);
			//Setting properties for okButton
			okButton.setText("OK");
            okButton.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt) {
                    okButtonWidgetSelected(evt);
                }
            });

			//Setting layout constraints for cancelButton
			GridData cancelButtonLData = new GridData();
			cancelButtonLData.verticalAlignment = GridData.FILL;
			cancelButtonLData.horizontalAlignment = GridData.FILL;
			cancelButtonLData.grabExcessHorizontalSpace = true;
			cancelButton.setLayoutData(cancelButtonLData);
			//Setting properties for cancelButton
			cancelButton.setText("Cancel");
            cancelButton.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt) {
                    cancelButtonWidgetSelected(evt);
                }
            });
			//Setting Grid layout for composite1
			GridLayout composite1Layout = new GridLayout(2, true);
			composite1.setLayout(composite1Layout);
			composite1Layout.marginWidth = 5;
			composite1Layout.marginHeight = 2;
			composite1Layout.numColumns = 2;
			composite1Layout.makeColumnsEqualWidth = true;
			composite1Layout.horizontalSpacing = 5;
			composite1Layout.verticalSpacing = 5;
			//Setting Grid layout for dialogShell
			GridLayout dialogShellLayout = new GridLayout(3, true);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.marginWidth = 5;
			dialogShellLayout.marginHeight = 5;
			dialogShellLayout.numColumns = 3;
			dialogShellLayout.makeColumnsEqualWidth = false;
			dialogShellLayout.horizontalSpacing = 5;
			dialogShellLayout.verticalSpacing = 5;
			dialogShell.layout();
			dialogShell.setDefaultButton(okButton);
			dialogShell.setSize(400, 300);
			postInitGUI();
	    	JiglooUtils.centerShellOnCursor(dialogShell);
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Add your pre-init code in here 	*/
	public void preInitGUI() {}

	private HashMap fontMap;
	private Vector fontVec;

	/** Add your post-init code in here 	*/
	public void postInitGUI() {
		fontMap = new HashMap();
		fontVec = new Vector();
		Font[] fonts =
			GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		Arrays.sort(fonts, new Comparator() {
			public int compare(Object o1, Object o2) {
				Font f1 = (Font) o1;
				Font f2 = (Font) o2;
				return f1.getFamily().compareToIgnoreCase(f2.getFamily());
			}
		});
		for (int i = 0; i < fonts.length; i++) {
			Font font = fonts[i];
			String fam = font.getFamily();
			fam = fam.substring(0, 1).toUpperCase() + fam.substring(1);
			Vector fontVec1 = (Vector) fontMap.get(fam);
			if (!fontMap.containsKey(fam)) {
				fontVec1 = new Vector();
				fontList.add(fam);
				fontVec.add(fam);
				fontMap.put(fam, fontVec1);
			}
			fontVec1.add(font);
		}

		styleList.add("Plain");
		styleList.add("Italic");
		styleList.add("Bold");
		styleList.add("Bold Italic");

		sizeList.add("6");
		sizeList.add("7");
		sizeList.add("8");
		sizeList.add("9");
		sizeList.add("10");
		sizeList.add("11");
		sizeList.add("12");
		sizeList.add("14");
		sizeList.add("16");
		sizeList.add("18");
		sizeList.add("20");
		sizeList.add("22");
		sizeList.add("24");
		sizeList.add("26");
		sizeList.add("28");
		sizeList.add("36");
		sizeList.add("48");
		sizeList.add("72");
		//setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
		if(awtFont != null) {
			family = awtFont.getFamily();
			int pos = fontVec.indexOf(family);
			if (pos < 0)
				pos = fontList.indexOf("Arial");
			size = awtFont.getSize();
			pos = sizeList.indexOf(size + "");
			if (pos < 0)
				pos = sizeList.indexOf("10");
			style = awtFont.getStyle();
			if ((style & Font.PLAIN) != 0) {
				swtStyle = SWT.NORMAL;
			} else if ((style & Font.ITALIC) != 0) {
				if ((style & Font.BOLD) != 0) {
					styleList.select(3);
					swtStyle = SWT.BOLD | SWT.ITALIC;
				} else {
					styleList.select(1);
					swtStyle = SWT.ITALIC;
				}
			} else if ((style & Font.BOLD) != 0) {
				styleList.select(2);
				swtStyle = SWT.BOLD;
			} else {
				styleList.select(0);
				swtStyle = SWT.NORMAL;
			}
			redrawSample();
		}
	}

	/** Auto-generated main method */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a Shell.
	*/
	public static void showGUI() {
		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			SwingFontDialog inst = new SwingFontDialog(shell, SWT.DIALOG_TRIM);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int size = 10;
	private int style = Font.PLAIN;
	private int swtStyle = SWT.NORMAL;
	private String family = "Arial";
	private org.eclipse.swt.graphics.Font cFont;
	private Font awtFont = null;

	public void setFont(java.awt.Font font) {
		awtFont = font;
	}

	public java.awt.Font getFont() {
		return awtFont;
	}

	private void redrawSample() {
		if (cFont != null)
			cFont.dispose();
		cFont =
			new org.eclipse.swt.graphics.Font(
				Display.getDefault(),
				family,
				size,
				swtStyle);
		sampleLabel.setFont(cFont);
		sampleLabel.redraw();
	}

	/** Auto-generated event handler method */
	protected void fontListWidgetSelected(SelectionEvent evt) {
		family = fontList.getItem(fontList.getSelectionIndex());
		redrawSample();
		//		Vector fVec = (Vector) fnames.get(family);
		//		styleList.removeAll();
		//		for (int i = 0; i < fVec.size(); i++) {
		//			Font f = (Font) fVec.elementAt(i);
		//			styleList.add(f.getStyle() + ", " + f.getName());
		//		}
	}

	/** Auto-generated event handler method */
	protected void styleListWidgetSelected(SelectionEvent evt) {
		int ind = styleList.getSelectionIndex();
		if (ind == 0) {
			style = Font.PLAIN;
			swtStyle = SWT.NORMAL;
		} else if (ind == 1) {
			style = Font.ITALIC;
			swtStyle = SWT.ITALIC;
		} else if (ind == 2) {
			style = Font.BOLD;
			swtStyle = SWT.BOLD;
		} else if (ind == 3) {
			style = Font.BOLD | Font.ITALIC;
			swtStyle = SWT.BOLD | SWT.ITALIC;
		}
		redrawSample();
	}

	/** Auto-generated event handler method */
	protected void sizeListWidgetSelected(SelectionEvent evt) {
		String ssize = sizeList.getItem(sizeList.getSelectionIndex());
		try {
			size = Integer.parseInt(ssize);
		} catch (Exception e) {
			size = 10;
		}
		redrawSample();
	}

	private void exit() {
		if (cFont != null)
			cFont.dispose();
		dialogShell.dispose();
	}

	/** Auto-generated event handler method */
	protected void cancelButtonWidgetSelected(SelectionEvent evt) {
		awtFont = null;
		exit();
	}

	/** Auto-generated event handler method */
	protected void okButtonWidgetSelected(SelectionEvent evt) {
		awtFont = new Font(family, style, size);
		exit();
	}
}
