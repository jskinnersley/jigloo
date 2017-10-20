package com.cloudgarden.jigloo.preferences;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.bean.BeanHandler;
import com.cloudgarden.jigloo.classloader.ClassLoaderCache;
import com.cloudgarden.jigloo.dialog.EditValueDialog;
import com.cloudgarden.jigloo.dnd.FormTransfer;
import com.cloudgarden.jigloo.images.ImageDisplayer;
import com.cloudgarden.jigloo.images.ImageManager;
import com.cloudgarden.jigloo.palette.ComponentPalette;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class PaletteComposite extends org.eclipse.swt.widgets.Composite {
	private Composite composite1;
	private Button button1;
    private Button button3;
    private TreeViewer treeViewer1;
	private Button button5;
	private Button button2;
	private Vector jars = new Vector();
	
	public static final String PREF_SEP_1 = "|";
	public static final String PREF_SEP_2 = "-#-";
	public static final String PREF_SEP_3 = "-!!!-";
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaletteComposite inst = new PaletteComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	class DragListener extends DragSourceAdapter {
		public DragListener() {		}
		public void dragFinished(DragSourceEvent event) {
		}
		public void dragSetData(DragSourceEvent event) {
		}		
		public void dragStart(DragSourceEvent event) {
		    event.doit = true;
		}
	}

	boolean dropBefore = false;
	boolean dropInto = false;
    private Composite composite2;
    private Composite composite3;
    private Button button6;
    private Button button4;
    private Button beanIconButton;
    private Button beanInfoButton;
    private Text beanIconFolder;
    private Text beanInfoFolder;
	TreeObject dropItem = null;

	private TreeObject[] getSelection() {
        Object[] sel = ((StructuredSelection)treeViewer1.getSelection()).toArray();
	    TreeObject[] tsel = new TreeObject[sel.length];
	    for (int i = 0; i < sel.length; i++) {
	        tsel[i] = (TreeObject) sel[i];
        }
	    return tsel;
	}
	
	class DropAdapter extends ViewerDropAdapter {
        public void drop(DropTargetEvent event) {
//            dropItem = (TreeObject) event.item.getData();
            if(dropItem == null)
                return;
            TreeObject[] sel = getSelection();
            for (int i = 0; i < sel.length; i++) {
                if(dropBefore) {
                    sel[i].moveBefore(dropItem);
                } else if(dropInto) {
                    sel[i].moveTo(dropItem);
                } else {
                    sel[sel.length - i - 1].moveAfter(dropItem);
                }
            }
            treeViewer1.refresh();
        }
        protected DropAdapter(Viewer viewer) {
            super(viewer);
    		setFeedbackEnabled(true);
        }
    	public void dragOver(DropTargetEvent event) {
    		super.dragOver(event);
//    		System.out.println("DRAG OVER "+event.item.getData()
//    		        +", "+event.feedback);
    		if (!(event.item instanceof TreeItem)) {
    			event.detail = DND.DROP_NONE;
    			return;
    		}
    		TreeItem over = (TreeItem)event.item;
    		TreeObject to = (TreeObject)over.getData();
    		TreeObject sel = getFirstSelectedTreeObject();
    		boolean canDrop = false;
		    dropInto = false;
		    dropBefore = false;

    		if ((event.feedback & DND.FEEDBACK_INSERT_AFTER) != 0) {
    		    canDrop = true;
    		    dropBefore = false;
    		    dropInto = false;
    		} else
    		if((event.feedback & DND.FEEDBACK_INSERT_BEFORE) != 0) {
    		    canDrop = true;
    		    dropBefore = true;
    		    dropInto = false;
    		}
//    		else {
    		if((event.feedback & DND.FEEDBACK_SELECT) != 0) {
    		    if(to.isPalette()) {
    		        canDrop = true;
    		        dropInto = true;
    		        dropBefore = false;
    		    } else {
    		        event.feedback = DND.FEEDBACK_NONE;
    		    }
    		}
    		if (canDrop) {
    			event.detail = DND.DROP_MOVE;
    			dropItem = (TreeObject) event.item.getData();
    		} else {
    			event.detail = DND.DROP_NONE;
    			dropItem = null;
    		}
    	}
    	
        private TreeObject getFirstSelectedTreeObject() {
            TreeObject[] sels = getSelection();
            if(sels.length == 0)
                return null;
            return sels[0];
        }
        
        public boolean performDrop(Object data) {
            return true;
        }
        public boolean validateDrop(Object target, int operation, TransferData transferType) {
            return true;
        }
	}

	class TreeObject implements ImageDisplayer {
	    TreeObject parent;
	    TreeObject[] children;
	    Vector tmp = new Vector();
	    String label;
	    String className;
	    Image image;
	    int type;
	    int style;
	    String[] prefs;
		private BeanInfo beanInfo;
	    
	    
	    TreeObject(String[] prefs, TreeObject par) {
            type = par.type;
            setPrefs(prefs);
	    }

	    public boolean equals(Object obj) {
	    	if(type != ((TreeObject)obj).type)
	    		return false;
	    	if(style != ((TreeObject)obj).style)
	    		return false;
	    	if( className != null)
	    		return className.equals(((TreeObject)obj).className);
	    	if(label != null)
	    		return label.equals(((TreeObject)obj).label);
	    	return false;
	    }
	    
	    void setPrefs(String[] prefs) {
            style = 0;
            String ss = prefs[3];
            if(!ss.equals(""))
                style = Integer.parseInt(ss);
		    className = prefs[2];
		    if(className.equals(""))
		        label = "Add custom class";
		    else
		        label = JiglooUtils.getUnqualifiedName(className)
		        	+" - "+JiglooUtils.getQualifier(className);
            if(!"".equals(prefs[4]))
                label += " - "+prefs[4];
            if(!"n".equals(prefs[5]) && !"y".equals(prefs[5]))
                label += " ("+prefs[5]+")";
            this.prefs = prefs;
	    }
	    
	    TreeObject(String label, int type) {
	        this.label = label;
	        this.type = type;
	    }

	    boolean sameType(TreeObject other) {
	        if(type != other.type)
	            return false;
	        if(other.className == null && className == null)
	            return true;
	        if(other.className != null && className != null)
	            return true;
	        return false;
	    }

	    void moveTo(TreeObject target) {
            if(sameType(target))
                return;
            parent.removeChild(this);
            target.addChild(this, 0);
       }

	    void moveAfter(TreeObject target) {
            if(!sameType(target))
                return;
            parent.removeChild(this);
            target.parent.addChild(this, target.parent.indexOf(target)+1);
       }

        void moveBefore(TreeObject target) {
            if(!sameType(target))
                return;
            parent.removeChild(this);
            target.parent.addChild(this, target.parent.indexOf(target));
        }

        int indexOf(TreeObject target) {
            return tmp.indexOf(target);
        }
        
        TreeObject find(String label) {
	        if(this.label.equals(label))
	            return this;
	        if(getChildren() == null)
	            return null;
	        for (int i = 0; i < getChildren().length; i++) {
	            TreeObject f = children[i].find(label);
	            if(f != null)
	                return f;
	        }
	        return null;
	    }
	    
	    void addChild(TreeObject child, int pos) {
	    	if(tmp.contains(child) || child.label.equals(""))
	    		return;
	        if(pos < 0)
	            tmp.add(child);
	        else 
	            tmp.add(pos, child);
	        children = null;
	        child.parent = this;
	        //make sure the child's prefs[0] value gets set to this TreeObject's label
	        if(className == null && child.prefs != null)
	            child.prefs[0] = label;
	    }
	    
	    void removeChild(TreeObject child) {
            tmp.remove(child);
	        children = null;
	    }
	    
	    TreeObject[] getChildren() {
	        if(children != null)
	            return children;
	        if(tmp.size() == 0)
	            return null;
	        children = new TreeObject[tmp.size()];
	        tmp.copyInto(children);
	        return children;
	    }
	    
	    void setChildren(TreeObject[] children) {
	        this.children = children;
	        tmp = new Vector();
	        for (int i = 0; i < children.length; i++) {
                children[i].parent = this;
                tmp.add(children[i]);
            }
	    }
	    
	    void addChildren(TreeObject[] newKids) {
	    	for (int i = 0; i < newKids.length; i++) {
	    		if(newKids[i].label.equals(""))
	    			continue;
				if(!tmp.contains(newKids[i])) {
					tmp.add(newKids[i]);
					newKids[i].parent = this;
			    	children = null;
				}
			}
	    }
	    
	    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return label;
        }

        String getChildrenAsText(boolean addSep) {
            String txt = "";
            if(addSep)
                txt = PREF_SEP_2;
            if(children != null) {
                for (int i = 0; i < children.length; i++) {
                    txt += (i != 0 ? PREF_SEP_2 : "") +children[i].getAsText();
                }
            }
            return txt;
        }
        
        String getAsText() {
            String txt = "";
            for (int i = 0; i < prefs.length; i++)
                txt += (i != 0 ? PREF_SEP_1 : "") +prefs[i];
            return txt;
        }

        public boolean isCustom() {
            return prefs != null && !prefs[5].equals("n");
        }
        
        /**
         * True if a child of the SWT or Swing node
         * @return
         */
        public boolean isPalette() {
            return className == null 
            && parent != null && parent.parent != null 
            && parent.parent.parent == null;
        }
        
        /**
         * True if the SWT or Swing node
         * @return
         */
        public boolean isPaletteRoot() {
            return className == null 
            && parent != null && parent.parent == null;
        }

		/* (non-Javadoc)
		 * @see com.cloudgarden.jigloo.images.ImageDisplayer#setImage(org.eclipse.swt.graphics.Image)
		 */
		public void setImage(Image img) {
			image = img;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					treeViewer1.update(TreeObject.this, null);
				}
			});
		}

		/**
		 * @param bi
		 */
		public void setBeanInfo(BeanInfo bi) {
			beanInfo = bi;
		}

		/**
		 * @return
		 */
		public Image getImage() {
			if(beanInfo != null) {
				java.awt.Image bimg = BeanHandler.getAwtBeanIcon(beanInfo);
				if(bimg == null)
					return ImageManager.getImage("javabean.gif");
				return JiglooUtils.getSWTImage(bimg);
			}
			return ImageManager.getImage("javabean.gif");
		}
	}
	
	private TreeObject root;
	private TreeObject[] widgetRoots;;

	//type = 1 => swing
	//type = 2 => swt
	//type = 3 => android
	private TreeObject find(String name, int type) {
	    return widgetRoots[type - 1].find(name);
	}
	
	public Vector getJars() {
		return jars;
	}
	
	private boolean isSelected(TreeObject obj) {
		StructuredSelection sel = (StructuredSelection)treeViewer1.getSelection();
		Iterator it = sel.iterator();
		while(it.hasNext())
			if(obj.equals(it.next()))
				return true;
		return false;
	}

	private boolean isChildSelected(TreeObject obj) {
		TreeObject[] kids = obj.getChildren();
		if(kids == null)
			return false;
		for (int i = 0; i < kids.length; i++) {
			if(isSelected(kids[i]))
				return true;
		}
		return false;
	}
	
	public String getPrefString(boolean selectAll) {
		
		String pref = "";
		String pref2 = "";

		if(getSelection().length == 0)
			selectAll = true;

		for(int t=0;t<widgetRoots.length;t++) {
			
			boolean rootSelected = isSelected(widgetRoots[t]) || selectAll;

			TreeObject[] obs = widgetRoots[t].getChildren();
			for (int i = 0; i < obs.length; i++) {
				boolean paletteSelected = isSelected(obs[i]);
				boolean childSelected = isChildSelected(obs[i]);
				if(rootSelected || paletteSelected || childSelected)
					pref += obs[i].label+PREF_SEP_1;
				if(obs[i].children != null) {
					for (int j = 0; j < obs[i].children.length; j++) {
						boolean selected = isSelected(obs[i].children[j]);
						if(rootSelected || paletteSelected || selected)
							pref2 += obs[i].children[j].getAsText()+PREF_SEP_2;
					}
				}
			}

			if(t != 2)
				pref += PREF_SEP_2;
		}
	    
	    pref += PREF_SEP_3 + pref2;
	    
	    return pref;
	}

	public void addToPrefs(String val) {
	    String[][][] prefs = ComponentPalette.getPaletteClasses(val);
	    
		for (int i = 0; i < prefs[0].length; i++) {
		    TreeObject[] children = new TreeObject[prefs[0][i].length];
			for (int j = 0; j < prefs[0][i].length; j++) {
		        children[j] = new TreeObject(prefs[0][i][j], i);
			}
			widgetRoots[i].addChildren(children);
        }
		
		for (int i = 0; i < prefs[1].length; i++) {
		    try {
		        if(prefs[1][i].length > 2) {
		            TreeObject par = find(prefs[1][i][0], Integer.parseInt(prefs[1][i][1]));
		            TreeObject child = new TreeObject(prefs[1][i], par);
		            par.addChild(child, -1);
		        }
		    } catch(Throwable t) {
		        t.printStackTrace();
		    }
        }
		treeViewer1.setInput(root);
		
	}
	
	public void loadPalettesFromString(String val) {
	    String[][][] prefs = ComponentPalette.getPaletteClasses(val);
	    
		root = new TreeObject("Root", -1);
		widgetRoots = new TreeObject[prefs[0].length];
		for (int i = 0; i < prefs[0].length; i++) {
			String label = "NONE";
			if(i == 0)
				label = "Swing";
			else if(i == 1)
				label = "SWT";
			else if(i == 2)
				label = "Android";
			widgetRoots[i] = new TreeObject(label, i);
		}
		
		root.setChildren(widgetRoots);
		for (int i = 0; i < prefs[0].length; i++) {
		    TreeObject[] children = new TreeObject[prefs[0][i].length];
			for (int j = 0; j < prefs[0][i].length; j++) {
		        children[j] = new TreeObject(prefs[0][i][j], i);
			}
			widgetRoots[i].setChildren(children);
        }
		for (int i = 0; i < prefs[1].length; i++) {
		    try {
		        if(prefs[1][i].length > 2) {
		            TreeObject par = find(prefs[1][i][0], Integer.parseInt(prefs[1][i][1]));
		            TreeObject child = new TreeObject(prefs[1][i], par);
		            par.addChild(child, -1);
		        }
		    } catch(Throwable t) {
		        t.printStackTrace();
		    }
        }
		treeViewer1.setInput(root);
		
	}
	
	public PaletteComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
		ILabelProvider lp = new LabelProvider() {
		    public Image getImage(Object element) {
		        try {
		            TreeObject to = (TreeObject)element;
		            if(to.className == null)
		                return ImageManager.getImage("palette.gif");
		            if(to.className.equals(""))
		                return ImageManager.getImage("addbean.gif");
		            try {
		                IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
		                
		                if(!to.prefs[5].equals("n")) {
		                    IProject prj = null;
		                    
		                    if(to.prefs[5].equals("y")) {
		                        prj = JiglooUtils.getProjectForClass(to.className);
		                        if(prj != null && prj.exists()) {
			                        to.prefs[5] = prj.getName();
			                        to.setPrefs(to.prefs);
		                        }
		                    } else {
		                    	prj = wsr.getProject(to.prefs[5]);
		                    	if(prj == null || !prj.isOpen())
		                    		prj = JiglooUtils.getProjectForClass(to.className);
		                    }
		                    
		                    if(prj != null && prj.exists()) {
		                    	Class cls = ClassLoaderCache.loadClass(prj, to.className, getClass().getClassLoader(), false);
		                    	if(cls == null) {
		                    		System.err.println("Unable to load palette class "+to.className+" for icon");
		                    	} else {
		                    		Image img = BeanHandler.getBeanImage(cls, prj);
		                    		if(img != null) {
		                    			return img;
		                    		}
		                    	}
		                    } else {
		                    	return to.getImage();
		                    }
		                    return ImageManager.getImage("javabean.gif");
		                }
		            } catch(Throwable t) {
		                t.printStackTrace();
		            }
		            return ImageManager.getImageForClassName(to.className, to.style);
		        } catch(Throwable t) {
		            t.printStackTrace();
                }
                return null;
            }
            public String getText(Object element) {
                return element.toString();
            }
		};
		IContentProvider cp = new ITreeContentProvider() {
            public Object[] getChildren(Object parentElement) {
                return ((TreeObject)parentElement).getChildren();
            }
            public Object getParent(Object element) {
                return ((TreeObject)element).parent;
            }
            public boolean hasChildren(Object element) {
                return ((TreeObject)element).getChildren() != null;
            }
            public Object[] getElements(Object inputElement) {
                return ((TreeObject)inputElement).getChildren();
            }
            public void dispose() {
            }
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
		};
		IDoubleClickListener dbl = new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editSelected(0, 0);
			}
		};
		treeViewer1.setAutoExpandLevel(2);
		treeViewer1.setLabelProvider(lp);
		treeViewer1.setContentProvider(cp);
		treeViewer1.addDoubleClickListener(dbl);
		
		try {
		    int ops = DND.DROP_MOVE;
		    
		    Transfer[] transfers = new Transfer[] { FormTransfer.getInstance()};
		    DragSourceListener dl = new DragListener();
		    treeViewer1.addDragSupport(ops, transfers, dl);
		    transfers = new Transfer[] { FormTransfer.getInstance()};
		    DropAdapter dropAdapter = new DropAdapter(treeViewer1);
		    treeViewer1.addDropSupport(ops, transfers, dropAdapter);
		    
		} catch(Throwable t) {
		    t.printStackTrace();
		}
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
            {
                GridData treeViewer1LData = new GridData();
                treeViewer1LData.grabExcessVerticalSpace = true;
                treeViewer1LData.grabExcessHorizontalSpace = true;
                treeViewer1LData.horizontalAlignment = GridData.FILL;
                treeViewer1LData.verticalAlignment = GridData.FILL;
                treeViewer1LData.verticalSpan = 2;
                treeViewer1 = new TreeViewer(this, SWT.BORDER | SWT.MULTI);
                treeViewer1.getControl().setLayoutData(treeViewer1LData);
            }
            {
                composite1 = new Composite(this, SWT.NONE);
                GridLayout composite1Layout = new GridLayout();
                composite1Layout.makeColumnsEqualWidth = true;
                GridData composite1LData = new GridData();
                composite1LData.verticalAlignment = GridData.BEGINNING;
                composite1LData.horizontalAlignment = GridData.END;
                composite1.setLayoutData(composite1LData);
                composite1.setLayout(composite1Layout);
                {
                    button1 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button1LData = new GridData();
                    button1LData.grabExcessHorizontalSpace = true;
                    button1LData.horizontalAlignment = GridData.FILL;
                    button1.setLayoutData(button1LData);
                    button1.setText("Add (bean or palette)");
                    button1.setAlignment(SWT.CENTER);
                    button1.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            addToSelected(evt.x, evt.y, false);
                        }
                    });
                }
                {
                    button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    button3.setText("Add beans from Archive");
                    GridData button3LData = new GridData();
                    button3LData.horizontalAlignment = GridData.FILL;
                    button3LData.grabExcessHorizontalSpace = true;
                    button3.setLayoutData(button3LData);
                    button3.setAlignment(SWT.CENTER);
                    button3.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            addToSelected(evt.x, evt.y, true);
                        }
                    });
                }
                {
                    button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button2LData = new GridData();
                    button2LData.grabExcessHorizontalSpace = true;
                    button2LData.horizontalAlignment = GridData.FILL;
                    button2.setLayoutData(button2LData);
                    button2.setText("Edit (bean or palette)");
                    button2.setAlignment(SWT.CENTER);
                    button2.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            editSelected(evt.x, evt.y);
                        }
                    });
                }
                {
                    button5 = new Button(composite1, SWT.PUSH | SWT.CENTER);
                    GridData button5LData = new GridData();
                    button5LData.grabExcessHorizontalSpace = true;
                    button5LData.horizontalAlignment = GridData.FILL;
                    button5.setLayoutData(button5LData);
                    button5.setText("Delete (bean or palette)");
                    button5.setAlignment(SWT.CENTER);
                    button5.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            deleteSelected();
                        }
                    });
                }
            }
            {
                composite2 = new Composite(this, SWT.NONE);
                GridLayout composite2Layout = new GridLayout();
                composite2Layout.makeColumnsEqualWidth = true;
                GridData composite2LData = new GridData();
                composite2LData.verticalAlignment = GridData.END;
                composite2LData.horizontalAlignment = GridData.END;
                composite2.setLayoutData(composite2LData);
                composite2.setLayout(composite2Layout);
                {
                    button6 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    button6.setAlignment(SWT.CENTER);
                    button6.setText("Import palette");
                    GridData button6LData = new GridData();
                    button6LData.horizontalAlignment = GridData.FILL;
                    button6.setLayoutData(button6LData);
                    button6.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            importPalette();
                        }
                    });
                }
                {
                    button4 = new Button(composite2, SWT.PUSH | SWT.CENTER);
                    button4.setAlignment(SWT.CENTER);
                    button4.setText("Export palette");
                    GridData button4LData = new GridData();
                    button4LData.horizontalAlignment = GridData.FILL;
                    button4.setLayoutData(button4LData);
                    button4.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            exportPalette();
                        }
                    });
                }
            }
            {
                composite3 = new Composite(this, SWT.NONE);
                GridLayout composite3Layout = new GridLayout();
                composite3Layout.makeColumnsEqualWidth = false;
                composite3Layout.numColumns = 3;
                GridData composite3LData = new GridData();
                composite3LData.verticalAlignment = GridData.END;
                composite3LData.horizontalSpan=2;
                composite3LData.grabExcessHorizontalSpace = true;
                composite3LData.horizontalAlignment = GridData.FILL;
                composite3.setLayoutData(composite3LData);
                composite3.setLayout(composite3Layout);
                {
                    new Label(composite3, SWT.NONE).setText("Bean icon folder:");
                    
                    beanIconFolder = new Text(composite3, SWT.BORDER);
                    beanIconFolder.setText(jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_ICON_FOLDER));
                    GridData data = new GridData();
                    data.grabExcessHorizontalSpace=true;
                    data.horizontalAlignment = GridData.FILL;
                    beanIconFolder.setLayoutData(data);
//                    beanIconFolder.setEditable(false);
                    
                    beanIconButton = new Button(composite3, SWT.PUSH | SWT.CENTER);
                    beanIconButton.setAlignment(SWT.CENTER);
                    beanIconButton.setText("Browse...");
                    data = new GridData();
                    data.horizontalAlignment = GridData.FILL;
                    beanIconButton.setLayoutData(data);
                    beanIconButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            editBeanIconFolder();
                        }
                    });
                }
                {
                    new Label(composite3, SWT.NONE).setText("BeanInfo folder:");
                    
                    beanInfoFolder = new Text(composite3, SWT.BORDER);
                    beanInfoFolder.setText(jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_INFO_FOLDER));
                    GridData data = new GridData();
                    data.grabExcessHorizontalSpace=true;
                    data.horizontalAlignment = GridData.FILL;
                    beanInfoFolder.setLayoutData(data);
//                    beanInfoFolder.setEditable(false);
                    
                    beanInfoButton = new Button(composite3, SWT.PUSH | SWT.CENTER);
                    beanInfoButton.setAlignment(SWT.CENTER);
                    beanInfoButton.setText("Browse...");
                    data = new GridData();
                    data.horizontalAlignment = GridData.FILL;
                    beanInfoButton.setLayoutData(data);
                    beanInfoButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            editBeanInfoFolder();
                        }
                    });
                }
            }
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 
     */
    protected void exportPalette() {
        String paletteCode = getPrefString(false);
        FileDialog fd = new FileDialog(getShell(), SWT.SAVE);
        fd.setFileName(System.getProperty("user.home")+File.separator+"jigloo_palette.txt");
        String path = fd.open();
        if(path == null)
            return;
        File file = new File(path);
        if(file.exists()) {
        	boolean cont = MessageDialog.openConfirm(getShell(), "Confirm overwrite", 
        			"File "+path+" already exists.\nOverwrite with new palette?");
        	if(!cont)
        		return;
        }
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(paletteCode);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    protected void importPalette() {
        FileDialog fd = new FileDialog(getShell());
        fd.setFileName(System.getProperty("user.home")+File.separator+"jigloo_palette.txt");
        String path = fd.open();
        if(path == null)
            return;
        File file = new File(path);
        try {
            FileReader fr = new FileReader(file);
            char[] buffer = new char[1000];
            String paletteCode = "";
            int numRead = -1;
            while((numRead = fr.read(buffer)) > 0) {
                paletteCode += new String(buffer, 0, numRead);
            }
            fr.close();
            addToPrefs(paletteCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void editBeanIconFolder() {
        DirectoryDialog fd = new DirectoryDialog(getShell(), SWT.OPEN);
        fd.setFilterPath(jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_ICON_FOLDER));
        String path = fd.open();
        if(path == null)
            return;
        beanIconFolder.setText(path);
        jiglooPlugin.getDefault().getPreferenceStore().setValue(MainPreferencePage.P_BEAN_ICON_FOLDER, path);
        jiglooPlugin.getDefault().getPreferenceStore().setValue(MainPreferencePage.P_BEAN_INFO_FOLDER, path);
    }

    protected void editBeanInfoFolder() {
    	DirectoryDialog fd = new DirectoryDialog(getShell());
        fd.setFilterPath(jiglooPlugin.getDefault().getPreferenceStore().getString(MainPreferencePage.P_BEAN_INFO_FOLDER));
        String path = fd.open();
        if(path == null)
            return;
        beanInfoFolder.setText(path);
    }

    protected void deleteSelected() {
        try {
            TreeObject[] sel = getSelection();
            String msg = "";
            if(sel.length == 1) {
                if(sel[0].isPaletteRoot())
                    return;
                else if(sel[0].isPalette())
                    msg = sel[0].label;
                else
                    msg = sel[0].className;
            } else
                msg = "these "+sel.length+" elements";
            if(!MessageDialog.openConfirm(getShell(), 
                    "Confirm delete", "Really delete "+msg+"?"))
                return;
            for (int i = 0; i < sel.length; i++) {
                if(sel[i].isPaletteRoot())
                    continue;
                if(sel[i].isPalette() || sel[i].parent.isPalette())
                    sel[i].parent.removeChild(sel[i]);
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
        treeViewer1.refresh();
    }

    /**
     * 
     */
    protected void editSelected(int x, int y) {
        try {
            TreeObject[] sel = getSelection();
            if(sel.length == 0 || sel[0].isPaletteRoot()) {
                MessageDialog.openError(getShell(), 
                        "Invalid selection", "You must select a component or palette");
                return;
            }
            if(sel[0].isPalette()) {
                EditValueDialog evd = new EditValueDialog(getShell(), SWT.NULL);
                evd.initText(sel[0].label, "Edit palette name", "Palette name:");
                evd.open();
                String name = evd.getValue();
                if(name != null) {
                    sel[0].label = name;
                    treeViewer1.refresh();
                }
                return;
            }
            PaletteDialog pd = new PaletteDialog(getShell(), SWT.NULL);
            pd.setValue(
            		new String[] {sel[0].className, sel[0].prefs[4], ""},
                    sel[0].type);
            pd.open(x, y);
            String[] val = pd.getValue();
            if(val !=null) {
                String prj = sel[0].prefs[5];
                if(val[3] != null)
                    prj = val[3];
                sel[0].setPrefs(new String[] {
                                sel[0].parent.label, 
                                (sel[0].type+1)+"", 
                                val[0], 
                                (sel[0].type == 1) ? val[2] : "0", 
                                val[1], 
                                prj});
                treeViewer1.refresh();
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    protected void addToSelected(int x, int y, boolean archive) {
        try {
            final TreeObject[] sel = getSelection();
            if(sel.length == 0 
                    || !(sel[0].isPalette() || sel[0].isPaletteRoot()) ) {
                MessageDialog.openError(getShell(), 
                        "Invalid selection", 
                        "You must select a palette before you can add a component");
                return;
            }
            if(!archive) {
                if(sel[0].isPaletteRoot()) {
                    EditValueDialog evd = new EditValueDialog(getShell(), SWT.NULL);
                    evd.initText("", "Add new component palette", "Palette name:");
                    evd.open();
                    String name = evd.getValue();
                    if(name != null) {
                        TreeObject palette = new TreeObject(name, sel[0].type);
                        sel[0].addChild(palette, -1);
                        treeViewer1.refresh();
                        treeViewer1.setSelection(new StructuredSelection(palette));
                    }
                    return;
                }
                PaletteDialog pd = new PaletteDialog(getShell(), SWT.NULL);
                pd.setValue(new String[] {"browse for class using button...", "", ""}, sel[0].type);
                pd.open(x, y);
                String[] val = pd.getValue();
                if(val !=null) {
                    sel[0].addChild(new TreeObject(
                            new String[] {
                                    sel[0].label, 
                                    (sel[0].type+1)+"", 
                                    val[0], 
                                    (sel[0].type == 1) ? val[2] : "0", 
                                    val[1], 
                                    val[3]}, sel[0]), -1);
                    treeViewer1.refresh();
                }
            } else {
                if(sel.length == 0 
                        || !sel[0].isPalette()) {
                    MessageDialog.openError(getShell(), 
                            "Invalid selection", 
                            "You must select a palette before you can add from an archive");
                    return;
                }
                ArchiveDialog pd = new ArchiveDialog(getShell(), SWT.NULL);
                pd.open(x, y);
                final Object[] val = pd.getValue();
                if(val !=null) {
                    final Vector classes = (Vector)val[1];
                    if(classes != null && classes.size() > 0) {
                    	ProgressMonitorDialog d = new ProgressMonitorDialog(getShell());
                    	IRunnableWithProgress rp = new IRunnableWithProgress() {
    						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
    							monitor.beginTask("Retrieving BeanInfo", classes.size());
		                        String jarFile = (String)val[0];
		                        URLClassLoader cl = null;
		                        try {
		                        	cl = new URLClassLoader(new URL[] {new File(jarFile).toURL()});
		                        } catch(Throwable t) {
		                        	t.printStackTrace();
		                        }
		                        //4.0.6
		                        jars.add(jarFile);
		                        for(int i=0; i<classes.size(); i++) {
		                        	monitor.worked(1);
		                            Class cls = null;
		                            Object elem = classes.elementAt(i);
		                            if(elem instanceof Class)
		                                cls = (Class)elem;
		                            String clsName;
		                            if(cls != null)
		                                clsName = cls.getName();
		                            else
		                                clsName = elem.toString();
		                            String cmnt = "Add "+clsName;
		                            BeanInfo bi = null;
		                            if(cl != null) {
		                            	try {
		                            		Class beanInfoClass = cl.loadClass(clsName+"BeanInfo");
		                            		if(beanInfoClass != null)
		                            			bi = (BeanInfo) beanInfoClass.newInstance();
		                            	} catch(Throwable t) {
//		                            		System.out.println("Unable to load "+clsName+"BeanInfo - "+t);;
		                            	}
		                            }
		                            if(bi != null) {
		                                BeanDescriptor desc = bi.getBeanDescriptor();
		                                if(desc != null) {
		                                    String sd = desc.getShortDescription();
		                                    if(sd != null  && !sd.equals(clsName))
		                                        cmnt = cmnt + " - "+sd;
		                                }
		                            }
		                            TreeObject child = new TreeObject(
		                                    new String[] {
		                                            sel[0].label, 
		                                            (sel[0].type+1)+"",
		                                            clsName, 
		                                            "0", 
		                                            cmnt, 
		                                            "y" }, 
		                                            sel[0]);
		                            child.setBeanInfo(bi);
		                            sel[0].addChild(child, -1);
		                        }
		                        Display.getDefault().syncExec(new Runnable() {
									public void run() {
				                        treeViewer1.refresh();
				                        treeViewer1.expandToLevel(sel[0], 1);
									}});
    						}
                    	};
                    	d.run(true, true, rp);
                    }
                }
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

	public String getBeanIconPath() {
		return beanIconFolder.getText();
	}

	public String getBeanInfoPath() {
		return beanInfoFolder.getText();
	}

	public void setBeanIconPath(String defaultString) {
		beanIconFolder.setText(defaultString);
	}

	public void setBeanInfoPath(String defaultString) {
		beanInfoFolder.setText(defaultString);
	}

}
