/*
 * Created on Jul 29, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.frames;

import info.clearthought.layout.TableLayout;

import java.awt.Point;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.dialog.EditValueDialog;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.layoutHandler.MigLayoutHandler;
import com.cloudgarden.jigloo.preferences.MainPreferencePage;
import com.cloudgarden.jigloo.properties.JGFormLayoutDialog;
import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.CursorManager;
import com.cloudgarden.jigloo.util.DelayableRunnable;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GridEdgeMarker {

	private final int RESIZE_MARGIN=2;
	private static final int MARKER_WIDTH = 10;
	private int[] widths = null;
	private int[] heights = null;
	private Point orig = null;
	
	private static final int VERTICAL = 1;
	private Control control;
	private FormComponent relComp;
	private int direction = 1;
	private int selectedSection = -1;
	
	private boolean inside = false;
	private boolean mouseDown = false;
	private int selectionStart = -1;
	private int newSize = -1;
	private int currentPos = -1;

	private boolean resizeMode = false;
	private boolean moveMode = false;
	private boolean inResizeArea = false;
	private Menu jgFormMenu;
	private Menu gbMenu, tableMenu;
	private String rowCol = "Column";
	private Runnable insertBeforeRunnable;
	private Runnable insertSpaceBeforeRunnable;
	private Runnable insertAfterRunnable;
	private Runnable insertSpaceAfterRunnable;
	private Runnable deleteRowCol;
	private Runnable deleteRowColAndContents;
	private IPropertyChangeListener propListener;
	
	public GridEdgeMarker(FormEditor editor, int dirn) {
		control = initSide(editor);
		this.direction = dirn;
		
		if(!isColumn()) {
			rowCol= "Row";
		} else {
			rowCol = "Column";
		}
		
		insertBeforeRunnable = new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection, 1, direction != VERTICAL, false);
			}
		};
		insertSpaceBeforeRunnable = new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection, 1, direction != VERTICAL, true);
			}
		};
		insertAfterRunnable  =new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection+1, 1, direction != VERTICAL, false);
			}
		};
		insertSpaceAfterRunnable  =new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection+1, 1, direction != VERTICAL, true);
			}
		};
		deleteRowCol = new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection, 2, direction != VERTICAL, false);
			}
		};
		deleteRowColAndContents = new Runnable() {
			public void run() {
				relComp.insertOrDeleteColumnOrRow(selectedSection, 3, direction != VERTICAL, false);
			}
		};
		
		createJGoodiesFormMenu();
		
		createGridBagMenu();
		
		createTableMenu();
		
		propListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getProperty().equals(MainPreferencePage.P_DEFAULT_GRID_SIZES)) {
					createGridBagMenu();
					createJGoodiesFormMenu();
					createTableMenu();
					if(relComp != null) {
						if(relComp.usesTableLayout()) {
							control.setMenu(tableMenu);
						} else if(relComp.usesJGFormLayout()) {
							control.setMenu(jgFormMenu);
						} else if(relComp.usesGridBagLayout()) {
							control.setMenu(gbMenu);
						}
					}
				}
			}
		};
	    jiglooPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(propListener);

	}

	private void createTableMenu() {
		tableMenu = new Menu(control);
		addMenuItem(tableMenu, "Insert "+rowCol+" before", insertBeforeRunnable, "insert"+rowCol+"Before");
		addMenuItem(tableMenu, "   Insert Spacer "+rowCol+" before", insertSpaceBeforeRunnable, "insertSp"+rowCol+"Before");
		addMenuItem(tableMenu, "Insert "+rowCol+" after", insertAfterRunnable, "insert"+rowCol+"After");
		addMenuItem(tableMenu, "   Insert Spacer "+rowCol+" after", insertSpaceAfterRunnable, "insertSp"+rowCol+"After");

		new MenuItem(tableMenu, SWT.SEPARATOR);
		
		addMenuItem(tableMenu, "Size: PREFERRED", new Runnable() {
			public void run() {
				relComp.setColumnOrRowSize(selectedSection, TableLayout.PREFERRED, isColumn());
			}
		}, null);
		
		addMenuItem(tableMenu, "Size: FILL", new Runnable() {
			public void run() {
				relComp.setColumnOrRowSize(selectedSection, TableLayout.FILL, isColumn());
			}
		}, null);
		
		addMenuItem(tableMenu, "Size: MINIMUM", new Runnable() {
			public void run() {
				relComp.setColumnOrRowSize(selectedSection, TableLayout.MINIMUM, isColumn());
			}
		}, null);
		
		String gridSizes = jiglooPlugin.getDefault().getStringPreference(MainPreferencePage.P_DEFAULT_GRID_SIZES);
		String[] sizes = JiglooUtils.split(",", gridSizes);
		for (int i = 0; i < sizes.length; i++) {
			try {
				final double size = Double.parseDouble(JiglooUtils.trim(sizes[i]));
				addMenuItem(tableMenu, "Size: "+size+" px", new Runnable() {
					public void run() {
						relComp.setColumnOrRowSize(selectedSection, size, isColumn());
					}
				}, null);
			} catch(Throwable t) {}
		}
		
		new MenuItem(tableMenu, SWT.SEPARATOR);
		addMenuItem(tableMenu, "Delete "+rowCol, deleteRowCol, "delete");
		addMenuItem(tableMenu, "Delete "+rowCol+" and contents", deleteRowColAndContents, "delete");
		new MenuItem(tableMenu, SWT.SEPARATOR);
		addMenuItem(tableMenu, "Edit "+rowCol+" size", new Runnable() {
			public void run() {
				String prop = "row";
				if(isColumn())
					prop ="column";
				double wt = relComp.getLayoutWrapper().getDoubleArrayValue(
						prop, selectedSection);
				EditValueDialog evd = new EditValueDialog(relComp.getShell(), SWT.DIALOG_TRIM);
				evd.initText(""+wt, "Edit size (pixels or fraction) for "+rowCol+" "+selectedSection);
				evd.open();
				String val = evd.getValue();
				double newVal = 0;
				if(val == null)
					return;
				try {
					newVal = Double.parseDouble(val);
				} catch(Throwable t) {
					return;
				}
				relComp.setColumnOrRowSize(selectedSection, newVal, isColumn());
			}
		}, null);
	}

	private void createJGoodiesFormMenu() {
		jgFormMenu = new Menu(control);
		addMenuItem(jgFormMenu, "Insert "+rowCol+" before", insertBeforeRunnable, "insert"+rowCol+"Before");
		addMenuItem(jgFormMenu, "   Insert Spacer "+rowCol+" before", insertSpaceBeforeRunnable, "insertSp"+rowCol+"Before");
		addMenuItem(jgFormMenu, "Insert "+rowCol+" after", insertAfterRunnable, "insert"+rowCol+"After");
		addMenuItem(jgFormMenu, "   Insert Spacer "+rowCol+" after", insertSpaceAfterRunnable, "insertSp"+rowCol+"After");
		
		new MenuItem(jgFormMenu, SWT.SEPARATOR);
		String gridSizes = jiglooPlugin.getDefault().getStringPreference(MainPreferencePage.P_DEFAULT_GRID_SIZES);
		String[] sizes = JiglooUtils.split(",", gridSizes);
		for (int i = 0; i < sizes.length; i++) {
			try {
				final double size = Double.parseDouble(JiglooUtils.trim(sizes[i]));
				addMenuItem(jgFormMenu, "Size: "+size+" px (converted to dlu)", new Runnable() {
					public void run() {
						relComp.setColumnOrRowSize(selectedSection, size, isColumn());
					}
				}, null);
			} catch(Throwable t) {}
		}
		new MenuItem(jgFormMenu, SWT.SEPARATOR);
		
		addMenuItem(jgFormMenu, "Delete "+rowCol, deleteRowCol, "delete");
		addMenuItem(jgFormMenu, "Delete "+rowCol+" and contents", deleteRowColAndContents, "delete");
		addMenuItem(jgFormMenu, "Edit "+rowCol+"s", new Runnable() {
			public void run() {
				Object lm = relComp.getLayoutWrapper().getObject();
				if(lm instanceof FormLayout) {
					JGFormLayoutDialog jgfd = new JGFormLayoutDialog(
							relComp.getEditor().getSite().getShell(), SWT.NULL);
					jgfd.setFormLayout((FormLayout)lm, direction != VERTICAL);
					jgfd.selectTableRow(selectedSection);
					jgfd.open();
					String newSpec = jgfd.getFormSpecString();
					if (newSpec != null) {
						relComp.setFormSpec(newSpec, direction != VERTICAL);
					}
					return;
				}

			}
		}, null);
	}

	private void createGridBagMenu() {
		gbMenu = new Menu(control);
		addMenuItem(gbMenu, "Insert "+rowCol+" before", insertBeforeRunnable, "insert"+rowCol+"Before");
		addMenuItem(gbMenu, "   Insert Spacer "+rowCol+" before", insertSpaceBeforeRunnable, "insertSp"+rowCol+"Before");
		addMenuItem(gbMenu, "Insert "+rowCol+" after", insertAfterRunnable, "insert"+rowCol+"After");
		addMenuItem(gbMenu, "   Insert Spacer "+rowCol+" after", insertSpaceAfterRunnable, "insertSp"+rowCol+"After");
		new MenuItem(gbMenu, SWT.SEPARATOR);
		String gridSizes = jiglooPlugin.getDefault().getStringPreference(MainPreferencePage.P_DEFAULT_GRID_SIZES);
		String[] sizes = JiglooUtils.split(",", gridSizes);
		for (int i = 0; i < sizes.length; i++) {
			try {
				final double size = Double.parseDouble(JiglooUtils.trim(sizes[i]));
				addMenuItem(gbMenu, "Size: "+size+" px", new Runnable() {
					public void run() {
						relComp.setColumnOrRowSize(selectedSection, size, isColumn());
					}
				}, null);
			} catch(Throwable t) {}
		}
		new MenuItem(gbMenu, SWT.SEPARATOR);
		addMenuItem(gbMenu, "Delete "+rowCol, deleteRowCol, "delete");
		addMenuItem(gbMenu, "Delete "+rowCol+" and contents", deleteRowColAndContents, "delete");
		addMenuItem(gbMenu, "Edit "+rowCol+" weight", new Runnable() {
			public void run() {
				String prop = "rowWeights";
				if(isColumn())
					prop ="columnWeights";
				double wt = relComp.getLayoutWrapper().getDoubleArrayValue(
						prop, selectedSection);
				EditValueDialog evd = new EditValueDialog(relComp.getShell(), SWT.DIALOG_TRIM);
				evd.initText(""+wt, "Edit weight for "+rowCol+" "+selectedSection);
				evd.open();
				String val = evd.getValue();
				double newVal = 0;
				if(val == null)
					return;
				try {
					newVal = Double.parseDouble(val);
				} catch(Throwable t) {
					return;
				}
				relComp.setColumnOrRowWeight(selectedSection, newVal, isColumn());
			}
		}, null);
	}

	public boolean isColumn() {
		return direction != VERTICAL;
	}
	
	private void addMenuItem(Menu menu, String name, final Runnable action, String imgName) {
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(name);
		if(imgName != null)
		    item.setImage(ImageManager.getImage(imgName+".gif"));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				action.run();
			}
		});
	}
	
	DelayableRunnable hider = new DelayableRunnable(100, true) {
		public void run() {
			if(!inside)
				control.setVisible(false);
		}
	};
	
	public void hide() {
		control.setVisible(false);
//		hider.trigger();
	}

	public boolean isVisible() {
		return control.isVisible();
	}
	
	public void redraw() {
	    if(isVisible()) {
//	        show(relComp);
	        if(control != null && !control.isDisposed())
	            control.redraw();
	    }
	}
	
	public void moveAbove() {
		control.moveAbove(null);
	}
	
	public void show(FormComponent target) {
		relComp = target;
		if(target.usesGridBagLayout()) {
			control.setMenu(gbMenu);
		} else if(target.usesTableLayout()) {
			createTableMenu();
			control.setMenu(tableMenu);
		} else if(MigLayoutHandler.handlesLayout(target)) {
			control.setMenu(MigLayoutHandler.createEdgeMenu(this));
		} else {
			control.setMenu(jgFormMenu);
		}
		Object[] obs= JiglooUtils.getGridOrFormDimensions(target);
		if(obs != null) {
			widths = (int[])obs[0];
			heights = (int[])obs[1];
			orig = (Point)obs[2];
		}
		Rectangle rec = target.getRootComponent().getBoundsRelativeToViewport();
		Rectangle b = target.getInsideBoundsRelativeToRoot();
		
		if(target.isSWT() )
			b.y -= target.getEditor().getMenuHeight();

		if (direction == VERTICAL) {
			rec.x += b.x - MARKER_WIDTH;
			rec.width = MARKER_WIDTH;
			rec.y += b.y;
			rec.height = b.height;
		} else {
			rec.y += b.y-MARKER_WIDTH;
			rec.height = MARKER_WIDTH;
			rec.x += b.x;
			rec.width = b.width;
		}
		setBounds(rec);
		show();
	}

	public void show() {
		control.setVisible(true);
//		control.moveAbove(null);
	}
	
	public void setBounds(Rectangle bounds) {
		control.setBounds(bounds);
	}
	
	private Color bgColor = ColorManager.getColor(255, 255, 255);
	private Color fgColor = ColorManager.getColor(0, 0, 0);
	private Color fgColor2 = ColorManager.getColor(100, 200, 240);

	public void setBGColor(Color bgColor) {
		this.bgColor = bgColor;
		if(control != null && !control.isDisposed())
			control.setBackground(bgColor);
	}
	
	public void setFGColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	public void dispose() {
		if(control != null && !control.isDisposed())
			control.dispose();
		control = null;
		relComp = null;
		jiglooPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(propListener);
	}
	
	public Control getControl() {
		return control;
	}
	
	public boolean mouseInside() {
		return inside;
	}
	
	private Control initSide(FormEditor editor) {
		Composite parent = editor.getViewportControl();
		Control shell = new Canvas(parent, SWT.NO_BACKGROUND);
		shell.setEnabled(true);
		shell.setBackground(bgColor);
		shell.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
			}
			public void mouseDown(MouseEvent e) {
				if(e.button == 1) {
					calcSelectedSection(e);
					mouseDown = true;
					selectionStart = selectedSection;
					if(inResizeArea) {
						resizeMode = true;
						moveMode = false;
					} else {
						resizeMode = false;
						moveMode = true;
					}
				} else {
					resizeMode = false;
					moveMode = false;
					mouseDown = false;
					selectionStart = -1;
				}
			}
			
			public void mouseUp(MouseEvent e) {
				if(mouseDown && e.button == 1) {
					if(moveMode) {
						
						if(selectionStart != -1 
								&& selectedSection != selectionStart) {
							if(direction == VERTICAL) {
								relComp.moveRow(selectionStart, selectedSection);
							} else {
								relComp.moveColumn(selectionStart, selectedSection);
							}
						}
					} else if(resizeMode) {
						if(direction == VERTICAL) {
							relComp.resizeRow(selectionStart, newSize);
						} else {
							relComp.resizeColumn(selectionStart, newSize);
						}
					}
				}
				
				mouseDown = false;
				moveMode = false;
				resizeMode = false;
				selectionStart = -1;
			}});
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				inside = true;
				handleMouseMove(e);
			}});
		shell.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseEnter(MouseEvent e) {
				inside = true;
			}
			public void mouseExit(MouseEvent e) {
				inside = false;
				control.redraw();
			}
		});
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				doPaint(e);
			}
		});
		shell.addKeyListener(editor.getFormEditorKeyAdapter());
		return shell;
	}

	public int getSelectedSection() {
		return selectedSection;
	}

	private void calcSelectedSection(MouseEvent e) {
		int xp = e.x, yp = e.y;
		int z = xp;
		int[] sizes = widths;
		int y = orig.x;
		if(direction == VERTICAL) {
			y = orig.y;
			z = yp;
			sizes = heights;
		}
		for (int i = 0; i < sizes.length; i++) {
			int y0 = y;
			if(z > y0 + RESIZE_MARGIN)
				selectedSection = i;
			y+= sizes[i];
		}		
	}
	
	protected void handleMouseMove(MouseEvent e) {
		calcSelectedSection(e);
		int xp = e.x, yp = e.y;
		int z = xp;
		int[] sizes = widths;
		int y = orig.x;
		if(direction == VERTICAL) {
			y = orig.y;
			z = yp;
			sizes = heights;
		}
		int cursor = SWT.CURSOR_SIZEE;
		if(direction == VERTICAL) {
			cursor = SWT.CURSOR_SIZES;
		}
		currentPos = z;
		String msg = null;
		boolean col = direction != VERTICAL;
		String rowColTxt = "row";
		if(col)
			rowColTxt = "column";

		int start = selectionStart;
		int to = selectedSection;
		boolean isForm = relComp.usesJGFormLayout();
		
		if(isForm) {
			start++;
			to++;
		}
		
		for (int i = 0; i < sizes.length; i++) {
			int y0 = y;
			y+= sizes[i];
			if(i == selectionStart)
				newSize = z - y0;
			boolean redraw = false;

			if(mouseDown) {
				if(moveMode && z > y0) {
					msg = "Move "+rowColTxt+" "+start+" to "+to;
				}
				
				if(resizeMode) {
					if(relComp.usesJGFormLayout())
						msg = "Resize "+rowColTxt+" "+start+" [ "+relComp.pixelToDLU(newSize, col)+" dlu ]";
					else
						msg = "Resize "+rowColTxt+" "+start+" [ "+newSize+" pixels ]";
				}
				redraw = true;
			} else {
				if(Math.abs(z-y) < RESIZE_MARGIN) {
					control.setCursor(CursorManager.getCursor(cursor));
//					if(moveMode || selectedSection != i)
						redraw = true;
					msg = "Drag to resize "+rowColTxt;
					inResizeArea = true;
				} else if(z > y0 + RESIZE_MARGIN && z < y - RESIZE_MARGIN) {
					control.setCursor(CursorManager.getCursor(SWT.CURSOR_SIZEALL));
					if(!moveMode || selectedSection != i)
						redraw = true;
					msg = relComp.getFormSpecAsString(i+1, direction != VERTICAL);
					msg += "\nDrag "+rowColTxt+" to new position\nRight-click for more options.";
					inResizeArea = false;
				}
			}
			if(redraw)
				control.redraw();
		}
		setToolTipText(msg);
	}

	private void setToolTipText(String msg) {
		if(msg != null //) {
			&& !msg.equals(control.getToolTipText())) {
//			System.out.println("SET TOOL TIP "+msg);
			control.setToolTipText(msg);
		}
	}
	
	private void doPaint(PaintEvent e) {
		e.gc.setForeground(fgColor);
		e.gc.setBackground(bgColor);
		Rectangle b = control.getBounds();
		if(widths != null) {
			if(direction == VERTICAL) {
				int y = orig.y;
				if(y != 0) {
					e.gc.fillRectangle(0, 0, MARKER_WIDTH, y);
					e.gc.drawLine(0, y, MARKER_WIDTH, y);
				}
				int startPos = -1;
				for (int i = 0; i < heights.length; i++) {
					int y0 = y;
					y+= heights[i];
					boolean normal = false;
					if(selectedSection == i) {
						if(!mouseDown) {
							e.gc.setBackground(ColorManager.getColor(220,220,220));
							e.gc.fillRectangle(0, y0+1, MARKER_WIDTH, heights[i]-1);
							e.gc.setBackground(bgColor);
							if(inResizeArea) {
								e.gc.setForeground(ColorManager.getColor(255, 0, 0));
								e.gc.setLineWidth(3);
							}
							e.gc.drawLine(0, y, b.width - 1, y);
							e.gc.setLineWidth(1);
							e.gc.setForeground(fgColor);
						} else if(mouseDown && !moveMode) {
							normal = true;
						} else if(moveMode && mouseDown) {
							e.gc.setBackground(ColorManager.getColor(0,250,100));
							e.gc.fillRectangle(0, y0+1, MARKER_WIDTH, heights[i]-1);
							e.gc.setBackground(bgColor);
							e.gc.drawLine(0, y, b.width - 1, y);
						}
					} else if(selectionStart == i) {
						if(moveMode && mouseDown) {
							e.gc.setBackground(ColorManager.getColor(220,220,220));
							e.gc.fillRectangle(0, y0+1, MARKER_WIDTH, heights[i]-1);
							e.gc.setBackground(bgColor);
						} else {
							normal = true;
						}
					} else if(selectionStart - 1 == i) {
						if(mouseDown && !moveMode) {
							e.gc.fillRectangle(0, y0+1, MARKER_WIDTH, heights[i]-1);
							e.gc.setForeground(ColorManager.getColor(100, 100, 100));
							e.gc.setLineWidth(3);
							e.gc.drawLine(0, y, b.width - 1, y);
							startPos = y;
							heights[i+1] = currentPos-y;
							e.gc.setLineWidth(1);
							e.gc.setForeground(fgColor);
						} else {
							normal = true;
						}
					} else {
						normal = true;
					}
					
					if(normal) {
						e.gc.fillRectangle(0, y0+1, MARKER_WIDTH, heights[i]-1);
						e.gc.drawLine(0, y, b.width - 1, y);
					}
				}
				e.gc.fillRectangle(0, y+1, MARKER_WIDTH, b.height-y);
				if(mouseDown && !moveMode) {
					e.gc.setForeground(ColorManager.getColor(255,0,0));
					e.gc.setLineWidth(3);
					e.gc.drawLine(0, currentPos, b.width - 1, currentPos);
					e.gc.setBackground(ColorManager.getColor(0,240,100));
					e.gc.fillRectangle(0, startPos+1, MARKER_WIDTH, currentPos-startPos-2);
					e.gc.setBackground(bgColor);
					e.gc.setForeground(fgColor);
					e.gc.setLineWidth(1);
				}
			} else {
				int x = orig.x;
				if(x != 0) {
					e.gc.fillRectangle(0, 0, x, MARKER_WIDTH);
					e.gc.drawLine(x, 0, x, MARKER_WIDTH);
				}
				int w = 0;
				int startPos = -1;
				for (int i = 0; i < widths.length; i++) {
					boolean normal = false;
					int x0 = x;
					w = widths[i];
					x+= w;
					if(selectedSection == i) {
						if(!mouseDown) {
							e.gc.setBackground(ColorManager.getColor(220,220,220));
							e.gc.fillRectangle(x0+1, 0, w-1, MARKER_WIDTH);
							e.gc.setBackground(bgColor);
							if(inResizeArea) {
								e.gc.setForeground(ColorManager.getColor(255, 0, 0));
								e.gc.setLineWidth(3);
							}
							e.gc.drawLine(x, 0, x, MARKER_WIDTH);
							e.gc.setLineWidth(1);
							e.gc.setForeground(fgColor);
						} else if(mouseDown && !moveMode) {
							normal = true;
						} else if(moveMode && mouseDown) {
							e.gc.setBackground(ColorManager.getColor(0,250,100));
							e.gc.fillRectangle(x0+1, 0, w-1, MARKER_WIDTH);
							e.gc.setBackground(bgColor);
							e.gc.drawLine(x, 0, x, MARKER_WIDTH);
						}
					} else if(selectionStart == i) {
						if(moveMode && mouseDown) {
							e.gc.setBackground(ColorManager.getColor(220,220,220));
							e.gc.fillRectangle(x0+1, 0, w-1, MARKER_WIDTH);
							e.gc.setBackground(bgColor);
							e.gc.drawLine(x, 0, x, MARKER_WIDTH);
						} else {
							normal = true;
						}
					} else if(selectionStart - 1 == i) {
						if(mouseDown && !moveMode) {
							e.gc.fillRectangle(x0+1, 0, w -1, MARKER_WIDTH);
							e.gc.setForeground(ColorManager.getColor(100, 100, 100));
							e.gc.setLineWidth(3);
							e.gc.drawLine(x, 0, x, MARKER_WIDTH);
							startPos = x;
							widths[i+1] = currentPos-x;
							e.gc.setLineWidth(1);
							e.gc.setForeground(fgColor);
						} else {
							normal = true;
						}
					} else {
						normal = true;
					}
					
					if(normal) {
						e.gc.fillRectangle(x0+1, 0, w - 1, MARKER_WIDTH);
						e.gc.drawLine(x, 0, x, MARKER_WIDTH);
					}
				}
				e.gc.fillRectangle(x+1, 0, b.width - x, MARKER_WIDTH);
				if(mouseDown && !moveMode) {
					e.gc.setForeground(ColorManager.getColor(255,0,0));
					e.gc.setLineWidth(3);
					e.gc.drawLine(currentPos, 0, currentPos, b.height - 1);
					e.gc.setBackground(ColorManager.getColor(0,240,100));
					e.gc.fillRectangle(startPos+1, 0, currentPos-startPos-2, MARKER_WIDTH);
					e.gc.setBackground(bgColor);
					e.gc.setForeground(fgColor);
					e.gc.setLineWidth(1);
				}
			}
		}
		e.gc.setForeground(fgColor2);
		e.gc.drawRectangle(0, 0, b.width - 1, b.height - 1);
	}

	public FormComponent getFormComponent() {
		return relComp;
	}

}
