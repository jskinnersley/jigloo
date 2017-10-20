package com.cloudgarden.jigloo.properties;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
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
public class MacSWTFontDialog extends org.eclipse.swt.widgets.Dialog {
	private Shell dialogShell;

	public MacSWTFontDialog(Shell parent, int style) {
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
			dialogShell.setText(getText());
			//Setting properties for SwingFontDialog
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(359, 292));

			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			{
				group1 = new Group(dialogShell, SWT.NONE);
				group1.setText("Font Name");
				FillLayout group1Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				GridData group1LData = new GridData();
				group1.setLayout(group1Layout);
				group1LData.verticalAlignment = GridData.FILL;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.grabExcessVerticalSpace = true;
				group1LData.grabExcessHorizontalSpace = true;
				group1.setLayoutData(group1LData);
				{
					fontList = new List(group1, SWT.SINGLE
						| SWT.H_SCROLL
						| SWT.V_SCROLL);
					fontList.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							fontListWidgetSelected(evt);
						}
					});
				}
			}
			{
				group2 = new Group(dialogShell, SWT.NONE);
				group2.setText("Style");
				FillLayout group2Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				GridData group2LData = new GridData();
				group2.setLayout(group2Layout);
				group2LData.verticalAlignment = GridData.FILL;
				group2LData.horizontalAlignment = GridData.CENTER;
				group2LData.grabExcessVerticalSpace = true;
				group2LData.widthHint = 94;
				group2.setLayoutData(group2LData);
				{
					styleList = new List(group2, SWT.SINGLE
						| SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					styleList.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							styleListWidgetSelected(evt);
						}
					});
				}
			}
			{
				group3 = new Group(dialogShell, SWT.NONE);
				group3.setText("Size");
				FillLayout group3Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				GridData group3LData = new GridData();
				group3.setLayout(group3Layout);
				group3LData.verticalAlignment = GridData.FILL;
				group3LData.horizontalAlignment = GridData.CENTER;
				group3LData.grabExcessVerticalSpace = true;
				group3LData.widthHint = 83;
				group3.setLayoutData(group3LData);
				{
					sizeList = new List(group3, SWT.SINGLE
						| SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					sizeList.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							sizeListWidgetSelected(evt);
						}
					});
				}
			}
			{
				group4 = new Group(dialogShell, SWT.NONE);
				group4.setText("Preview");
				FillLayout group4Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				GridData group4LData = new GridData();
				group4.setLayout(group4Layout);
				group4LData.horizontalAlignment = GridData.FILL;
				group4LData.horizontalSpan = 3;
				group4.setLayoutData(group4LData);
				{
					sampleLabel = new CLabel(group4, SWT.SHADOW_IN | SWT.BORDER);
					sampleLabel.setText("ABCDEFabcdef");
					sampleLabel.setAlignment(SWT.CENTER);
				}
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1.setLayout(composite1Layout);
				composite1.layout();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.horizontalAlignment = GridData.END;
				composite1LData.horizontalSpan = 3;
				composite1.setLayoutData(composite1LData);
				{
					okButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
					okButton.setText("OK");
					okButton
						.setSize(new org.eclipse.swt.graphics.Point(65, 23));
					GridData okButtonLData = new GridData();
					okButtonLData.verticalAlignment = GridData.FILL;
					okButtonLData.horizontalAlignment = GridData.FILL;
					okButtonLData.grabExcessHorizontalSpace = true;
					okButtonLData.grabExcessVerticalSpace = true;
					okButton.setLayoutData(okButtonLData);
					okButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							okButtonWidgetSelected(evt);
						}
					});
				}
				{
					cancelButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
					cancelButton.setText("Cancel");
					GridData cancelButtonLData = new GridData();
					cancelButtonLData.verticalAlignment = GridData.FILL;
					cancelButtonLData.horizontalAlignment = GridData.FILL;
					cancelButtonLData.grabExcessHorizontalSpace = true;
					cancelButtonLData.grabExcessVerticalSpace = true;
					cancelButton.setLayoutData(cancelButtonLData);
					cancelButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							cancelButtonWidgetSelected(evt);
						}
					});
				}
			}
			dialogShellLayout.numColumns = 3;
			dialogShell.layout();
			dialogShell.setDefaultButton(okButton);
			Rectangle bounds = dialogShell.computeTrim(0, 0, 359, 292);
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
		FontData[] fonts = Display.getCurrent().getFontList(null, true);
		Arrays.sort(fonts, new Comparator() {
			public int compare(Object o1, Object o2) {
				FontData f1 = (FontData) o1;
				FontData f2 = (FontData) o2;
				return f1.getName().compareToIgnoreCase(f2.getName());
			}
		});
		for (int i = 0; i < fonts.length; i++) {
			FontData font = fonts[i];
			String fam = font.getName();
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
			family = awtFont.getName();
			int pos = fontVec.indexOf(family);
			if (pos < 0)
				pos = fontList.indexOf("Arial");
			fontList.select(pos);
			size = awtFont.getHeight();
			pos = sizeList.indexOf(size + "");
			if (pos < 0)
				pos = sizeList.indexOf("10");
			swtStyle = awtFont.getStyle();
			if ((swtStyle & SWT.NORMAL) != 0) {
			} else if ((swtStyle & SWT.ITALIC) != 0) {
				if ((swtStyle & SWT.BOLD) != 0) {
					styleList.select(3);
				} else {
					styleList.select(1);
				}
			} else if ((swtStyle & SWT.BOLD) != 0) {
				styleList.select(2);
			} else {
				styleList.select(0);
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
			MacSWTFontDialog inst = new MacSWTFontDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int size = 10;
	private int swtStyle = SWT.NORMAL;
	private String family = "Arial";
	private Group group1;
	private List fontList;
	private Button cancelButton;
	private Button okButton;
	private Composite composite1;
	private CLabel sampleLabel;
	private Group group4;
	private List sizeList;
	private Group group3;
	private List styleList;
	private Group group2;
	private org.eclipse.swt.graphics.Font cFont;
	private FontData awtFont = null;

	public void setFontData(FontData font) {
		awtFont = font;
	}

	public FontData getFontData() {
		return awtFont;
	}

	private void redrawSample() {
		String sel = fontList.getItem(fontList.getSelectionIndex());
		if (cFont != null)
			cFont.dispose();
		cFont =
			new org.eclipse.swt.graphics.Font(
				Display.getDefault(),
				family,
				size,
				swtStyle);
		sampleLabel.setFont(cFont);
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
			swtStyle = SWT.NORMAL;
		} else if (ind == 1) {
			swtStyle = SWT.ITALIC;
		} else if (ind == 2) {
			swtStyle = SWT.BOLD;
		} else if (ind == 3) {
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
		awtFont = new FontData(family,size, swtStyle);
		exit();
	}
}
