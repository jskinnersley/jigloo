/*
 * Created on Jan 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.properties;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;

public class ImageFileDialogCellEditor extends DialogCellEditor {

	private FormComponent comp;
	private FormEditor editor;
	private String id;
	private boolean forIcon;
	
	/**
	 * Creates a new Font dialog cell editor parented under the given control.
	 * The cell editor value is <code>null</code> initially, and has no 
	 * validator.
	 *
	 * @param parent the parent control
	 */
	public ImageFileDialogCellEditor(Composite parent, FormComponent comp, String id) {
		super(parent);
		this.id = id;
		this.comp = comp;
	}

	public void setEditor(FormEditor editor) {
		this.editor = editor;
	}

	protected void doSetValue(Object value) {
		comp.selectInCode(id);
		super.doSetValue(value);
	}
	
	public void setForIcon(boolean forIcon) {
		this.forIcon = forIcon;
	}

	/**
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(Control)
	 */
	public Object openDialogBox(Control cellEditorWindow) {
		try {
			FileDialog ftDialog = new FileDialog(cellEditorWindow.getShell());

			ImageWrapper oldWrapper = (ImageWrapper) getValue();
			String value = null;
			if (editor == null)
				editor = comp.getEditor();
			String baseDir = JiglooUtils.getSourceBaseForEditor(editor);
			String iconDir = JiglooUtils.getIconBaseForEditor(editor);
			if (oldWrapper != null) {
				value = oldWrapper.toString();
				if ("No Image".equals(value) || "No Icon".equals(value)) {
					ftDialog.setFilterPath(iconDir);
				} else {
					String fullName =
						JiglooUtils.getOSFileName(
							baseDir + File.separator + value);
					//System.out.println("FULL NAME = " + fullName);
					if (!new File(fullName).exists()) {
						ftDialog.setFilterPath(iconDir);
					} else {
						int pos = fullName.lastIndexOf(File.separator);
						String file = fullName.substring(pos + 1);
						String dir = fullName.substring(0, pos);
						ftDialog.setFilterPath(dir);
						ftDialog.setFileName(file);
					}
				}
			} else {
				ftDialog.setFilterPath(iconDir);
			}
			String fileName = ftDialog.open();
			if (fileName == null)
				return oldWrapper;
			File imgFile = new File(fileName);
			File srcDir = new File(baseDir);
			String relFileName = "";
			while (imgFile != null && imgFile.exists()) {
				if (imgFile.equals(srcDir))
					break;
				relFileName = File.separator + imgFile.getName() + relFileName;
				imgFile = imgFile.getParentFile();
			}
			//System.err.println(
			//"GOT FILE " + fileName + ", BASE=" + baseDir);
			if (imgFile == null
				|| !imgFile.equals(srcDir)
				&& !"No Image".equals(fileName)
				&& !"No Icon".equals(value)) {
				MessageDialog.openError(
					cellEditorWindow.getShell(),
					"Invalid file location " + fileName,
					"Image file must be in a sub-folder of " + baseDir);
				return oldWrapper;
			}
			//fileName = fileName.substring(baseDir.length());
			fileName = relFileName;
			deactivate();
			if (fileName != null) {
//				if (oldWrapper != null)
//					oldWrapper.dispose();
				if (forIcon || oldWrapper instanceof IconWrapper) {
					return new IconWrapper(fileName, comp);
				} else {
					return new ImageWrapper(fileName, comp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}