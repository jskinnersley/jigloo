package com.cloudgarden.jigloo.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.properties.ImageFileDialogCellEditor;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ImagePropertyDescriptor extends FormPropertyDescriptor {

	private boolean forIcon = false;
	
	/**
	 * Creates an property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ImagePropertyDescriptor(Object id, String displayName, FormComponent comp) {
		super(id, displayName, comp);
	}

	public void setForIcon(boolean forIcon) {
		this.forIcon = forIcon;
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		ImageFileDialogCellEditor editor = new ImageFileDialogCellEditor(parent, comp, (String)getId());
		editor.setForIcon(forIcon);
		editor.setEditor(comp.getEditor());
		
		ICellEditorValidator val = new ICellEditorValidator() {
			public String isValid(Object obj) {
				if (obj instanceof ImageWrapper) {
					ImageWrapper iw = (ImageWrapper) obj;
					String fileName = iw.getName();
					//System.err.println("GOT FILE " + fileName);
				}
				return null;
			}
		};
		//editor.setValidator(val);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
/*
	class ImageFileDialogCellEditor extends DialogCellEditor {

		public ImageFileDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected void doSetValue(Object value) {
			comp.selectInCode((String)getId());
			super.doSetValue(value);
		}

		public Object openDialogBox(Control cellEditorWindow) {
			try {
				FileDialog ftDialog = new FileDialog(cellEditorWindow.getShell());

				ImageWrapper oldWrapper = (ImageWrapper) getValue();
				String value = null;
				String baseDir = JiglooUtils.getSourceBaseForEditor(comp.getEditor());
				String iconDir = JiglooUtils.getIconBaseForEditor(comp.getEditor());
				if (oldWrapper != null) {
					value = oldWrapper.toString();
					if ("No Image".equals(value) || "No Icon".equals(value)) {
						ftDialog.setFilterPath(iconDir);
					} else {
						String fullName = JiglooUtils.getOSFileName(baseDir + File.separator + value);
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
//					if (oldWrapper != null)
//						oldWrapper.dispose();
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
*/
}
