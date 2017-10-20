package com.cloudgarden.jigloo.menu;

import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.cloudgarden.jigloo.FormComponent;

public class MenuWindow {

	private MenuLabel button;
	private static FormComponent lastMenuComp = null;
	
	FormComponent fc;
	int x;
	int y;
	boolean active = false;

	class MenuLabel extends Composite {
		
		CustLabel label0;
		Label label;
		CustLabel label2;
		Color origColor;
		
		class CustLabel extends Composite {
			String text;
			public CustLabel(Composite parent, int style) {
				super(parent, style);
				addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						if(text.equals("o")) {
							e.gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
							e.gc.fillOval(1, 2, 7,7);
						}
						if(text.equals(">")) {
							e.gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
							e.gc.fillPolygon(new int[] {1,1,6,6,1,11,1,1});
						}
						if(text.equals("x")) {
							e.gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
							e.gc.fillPolygon(new int[] {0,4,4,7,10,0,12, 0, 4,10,0,5});
						}
					}});
			}
			String getText() {
				return text;
			}
			void setText(String text) {
//				if(text != null && text.equals(this.text))
//					return;
				this.text = text;
				if(text == null || "".equals(text))
					setLayoutData(new RowData(0,0));
				else
					setLayoutData(new RowData(10, 12));
				getParent().layout(true);
				redraw();
			}
		}
		
		public MenuLabel(Composite parent, int style) {
			super(parent, style);
			RowLayout layout = new RowLayout(SWT.HORIZONTAL);
			layout.fill = true;
			setLayout(layout);
			if(fc.hasStyle(SWT.SEPARATOR) || fc.isA(JSeparator.class) ) {
				label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
			} else {
				label0 = new CustLabel(this, SWT.NONE);
				label = new Label(this, SWT.NONE);
				label2 = new CustLabel(this, SWT.NONE);
			}
			setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
			origColor = label.getBackground();
			addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					int w = getBounds().width-1;
					int h = getBounds().height-1;
					e.gc.drawLine(0, 0, 0, h);
					e.gc.drawLine(w, 0, w, h);
					if(fc.getIndexInParent() == 0)
						e.gc.drawLine(0, 0, w, 0);
					if(fc.getParent() != null &&
							fc.getIndexInParent() == fc.getParent().getChildCount()-1)
						e.gc.drawLine(0, h, w, h);
				}});
		}

		public void addKeyListener(KeyListener listener) {
			super.addKeyListener(listener);
			label.addKeyListener(listener);
			if(label2 != null)
				label2.addKeyListener(listener);
			if(label0 != null)
				label0.addKeyListener(listener);
		}
		
		public void addMouseTrackListener(MouseTrackListener listener) {
			super.addMouseTrackListener(listener);
			label.addMouseTrackListener(listener);
			if(label2 != null)
				label2.addMouseTrackListener(listener);
			if(label0 != null)
				label0.addMouseTrackListener(listener);
		}
		
		public void addMouseListener(MouseListener listener) {
			super.addMouseListener(listener);
			label.addMouseListener(listener);
			if(label2 != null)
				label2.addMouseListener(listener);
			if(label0 != null)
				label0.addMouseListener(listener);
		}
		
		public void setText() {
			boolean changed = false;
			String text = null;

			if((text == null  || "".equals(text)) && fc.getPropertyValue("action") != null)
				text = (String) ((FormComponent) fc.getPropertyValue("action")).getPropertyValue("text");
			if(text == null || "".equals(text))
				text = (String) fc.getPropertyValue("text");
			if(text == null || "".equals(text))
				text = (String) fc.getPropertyValue("label");

			if(fc.isSubclassOf(JPopupMenu.class) && text == null || "".equals(text))
				text = "POPUP MENU";
			
			if(label2 != null) {
				if(text != null) {
					if(label.getText() == null || !label.getText().equals(text)) {
						changed = true;
						label.setText(text);
					}
				}

				String text0 = "";
				label0.setLayoutData(new RowData(-1, -1));
				if(fc.hasStyle(SWT.RADIO) || fc.isA(JRadioButtonMenuItem.class)) {
					text0 = "o";
				} else if(fc.hasStyle(SWT.CHECK) || fc.isA(JCheckBoxMenuItem.class)) {
					text0 = "x";
					label0.setText("x");
				} else {
					label0.setLayoutData(new RowData(0,0));
				}
				if(label0.getText() == null || !label0.getText().equals(text0)) {
					changed = true;
				}
				label0.setText(text0);
				
				String text2 = "";
				if(y != 0 && fc.hasStyle(SWT.CASCADE)) {
					text2 = ">";
				} else	 if(y != 0 && fc.isA(JMenu.class)) {
					text2 = ">";
				}
				if(label2.getText() == null || !label2.getText().equals(text2)) {
					changed = true;
				}
				label2.setText(text2);
			}
			
			
			if(fc.getParent() != null 
					&& fc.getParent().isSubclassOf(JMenuBar.class)) {
				Rectangle b = fc.getBounds();
				setSize(b.width, b.height);
			} else {
				if(changed || getHeight() <= 0)
					pack();
			}
		}
		
		public void setBackground(Color color) {
			super.setBackground(color);
			label.setBackground(color);
			update();
			label.update();
			if(label2 != null) {
				label2.setBackground(color);
				label2.update();
			}
			if(label0 != null) {
				label0.setBackground(color);
				label0.update();
			}
		}
	}
	
	public MenuWindow(FormComponent fc) {
		this.fc = fc;
	}

	public boolean isInMenuBar() {
		return fc.getParent() != null 
		&& fc.getParent().equals(getMenuRoot())
		&& !fc.getParent().isJPopupMenu();
	}
	
	protected void hideInactive() {
		if(!anySiblingsActive() && !parentActive())
			hideButton();
		for(int i=0; i<fc.getChildCount(); i++)
			fc.getChildAt(i).getEditorMenu().hideInactive();
	}

	public int getWidth() {
		createButton();
		return button.getBounds().width;
	}
	
	public int getHeight() {
		createButton();
		return button.getBounds().height;
	}
	
	public Rectangle getBounds() {
		if(button == null)
			return null;
		return new Rectangle(x, y, getWidth(), getHeight());
	}
	
	public void setWidth(int w) {
		button.setSize(w, button.getSize().y);
	}
	
	public boolean isInPopup() {
		return getMenuRoot().isSubclassOf(JPopupMenu.class);
	}
	
	private FormComponent getMenuRoot() {
		FormComponent mr = fc;
		while(mr.getParent() != null && mr.getParent().isMenuComponent())
			mr = mr.getParent();
		return mr;
	}
	
	private void createButton() {
		if(fc.isA(Menu.class))
			return;
		if(button == null) {
			button = new MenuLabel(fc.getEditor().getViewportControl(), SWT.FLAT);
			
			button.addKeyListener(fc.getEditor().getFormEditorKeyAdapter());
			
			button.addMouseListener(new MouseListener() {
				public void mouseDoubleClick(MouseEvent e) {
					fc.getEditor().handleMouseDoubleClickInFormEditor(e);
				}
				public void mouseDown(MouseEvent e) {
					setActive(true);
					fc.getEditor().selectComponent(fc);
					fc.selectInCode(null);
					if(e.button == 3) {
						fc.showContextMenu();
					} else if(e.button == 1) {
						if(fc.getEditor().getCurrentAction() != null) {
							fc.getEditor().processCurrentAction(e, true, fc);
						}
					}
				}
				public void mouseUp(MouseEvent arg0) {
				}});
			
			button.addMouseTrackListener(new MouseTrackListener() {
				public void mouseEnter(MouseEvent arg0) {
					if(fc.getEditor().getCurrentAction() != null) {
						button.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
						fc.getEditor().getGridEdgeManager().clearGreenMarker();
					}
				}

				public void mouseExit(MouseEvent arg0) {
					if(fc.getEditor().getCurrentAction() != null) {
						button.setBackground(button.origColor);
					}
				}
				public void mouseHover(MouseEvent arg0) {
				}});
		}
	}
	
	public void show(int x, int y) {
		if(fc.isA(Menu.class))
			return;
		createButton();
		button.setText();
		Rectangle b = getMenuRoot().getBoundsRelativeToViewport();
		if(getMenuRoot().isSubclassOf(JPopupMenu.class)) {
			if(fc.equals(getMenuRoot())) {
				this.x = x;
				this.y = y;
				button.setLocation(b.x+x, b.y+y);
			} else {
				if(fc.getParent().equals(getMenuRoot())) {
					this.x = b.x+x;
					this.y = b.y+y;
				} else {
					this.x = x;
					this.y = y;
				}
				button.setLocation(this.x, this.y);
			}
		} else {
			this.x = x;
			this.y = y;
			button.setLocation(b.x+x, b.y+y);
		}
		button.setVisible(true);
		button.moveAbove(null);
	}

	public void showChildren() {
		int w = 0;
		int yoff = 0;
		int xoff = 0;
		FormComponent menu = null;
		if(fc.isA(MenuItem.class) && fc.getChildCount() > 0) {
			menu = fc.getChildAt(0);
		}
		if(fc.isSubclassOf(JMenuItem.class)) {
			menu = fc;
		}
		if(fc.isSubclassOf(JPopupMenu.class)) {
			menu = fc;
		}
		
		if(menu != null) {
			if(y == 0)
				yoff += getHeight();
			xoff = getWidth();
			for (int i = 0; i < menu.getChildCount(); i++) {
				MenuWindow mi = menu.getChildAt(i).getEditorMenu();
				mi.show(y == 0 ? x : x+xoff, y+yoff);
				yoff += mi.getHeight();
				if(mi.getWidth() > w)
					w = mi.getWidth();
			}
			for (int i = 0; i < menu.getChildCount(); i++) {
				MenuWindow mi = menu.getChildAt(i).getEditorMenu();
				mi.setWidth(w);
			}
		}
	}
	
	public boolean anySiblingsActive() {
		if(active || anyChildrenActive())
			return true;
		if(fc.getParent() == null)
			return false;
		if(isInMenuBar())
			return false;
		FormComponent par = fc.getParent();
		for (int i = 0; i < par.getChildCount(); i++) {
			FormComponent ch = par.getChildAt(i);
			if(ch.getEditorMenu() != null) {
				if(ch.getEditorMenu().isActive() || ch.getEditorMenu().anyChildrenActive())
					return true;
			}
		}
		return false;
	}
	
	public boolean parentActive() {
		if(fc.getParent() == null 
				|| fc.getParent().getEditorMenu() == null)
			return false;
		return fc.getParent().getEditorMenu().isActive();
	}
	
	private boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		if(this.active == active)
			return;
		this.active = active;
		if(active) {
			if(lastMenuComp != null) {
				lastMenuComp.getEditorMenu().setActive(false);
				lastMenuComp.getEditorMenu().getMenuRoot().getEditorMenu().hideInactive();
			}
			lastMenuComp = fc;
		}
		if(button != null) {
			if(active) {
				button.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
			} else {
				button.setBackground(button.origColor);
			}
		}
	}

	public boolean anyChildrenActive() {
		if(active)
			return true;
		for (int i = 0; i < fc.getChildCount(); i++) {
			if(fc.getChildAt(i).getEditorMenu().anyChildrenActive())
				return true;
		}
		return false;
	}
	
	public void clearActive() {
		setActive(false);
		for (int i = 0; i < fc.getChildCount(); i++) {
			fc.getChildAt(i).getEditorMenu().clearActive();
		}
	}
	
	public void hideButton() {
		if(button != null && button.isVisible()) {
			button.setVisible(false);
			fc.getEditor().hideWindowFrame(fc);
		}
	}

	public void dispose() {
		if(button != null)
			button.dispose();
		button = null;
	}

	public void show() {
//		if(active)
//			return;
		createButton();
		setActive(true);
//		getMenuRoot().getEditorMenu().hideInactive();
		Vector path = new Vector();
		FormComponent mi = fc;
		path.add(fc);
		while(mi.getParent() != null && mi.getParent().isMenuComponent()) {
			mi = mi.getParent();
			path.add(mi);
		}
//		if(path.size() > 0 && ((FormComponent)path.get(path.size()-1)).isA(Menu.class))
		if(path.size() > 0) {
			FormComponent fc = (FormComponent)path.get(path.size()-1);
			if(fc.isA(Menu.class) || fc.isA(JMenuBar.class))
				path.remove(path.size()-1);
		}
		
		if(path.size() > 0) {
			FormComponent menuRoot = ((FormComponent)path.get(path.size()-1));
			if(menuRoot.isSubclassOf(JPopupMenu.class))
				menuRoot.getEditorMenu().show(0, 0);
			else
				menuRoot.getEditorMenu().show(menuRoot.getBounds().x, 0);
			for(int i=path.size()-1; i>= 0; i--) {
				MenuWindow win = ((FormComponent)path.get(i)).getEditorMenu();
				if(win == null)
					System.out.println("WIN IS NULL!");
				else
					win.showChildren();
			}
		}
		showChildren();
	}

	public static void hideAll() {
		if(lastMenuComp != null) {
			lastMenuComp.getEditorMenu().getMenuRoot().getEditorMenu().clearActive();
			lastMenuComp.getEditorMenu().getMenuRoot().getEditorMenu().hideInactive();
		}
		lastMenuComp = null;
	}
	
}
