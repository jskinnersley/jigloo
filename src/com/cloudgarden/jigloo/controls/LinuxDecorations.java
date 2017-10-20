/*
 * Created on Feb 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.cloudgarden.jigloo.resource.ColorManager;
import com.cloudgarden.jigloo.resource.FontManager;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LinuxDecorations extends Composite {

    private CLabel label;
    
    public void update() {
    	super.update();
    	label.update();
    }
    
    /**
     * @param parent
     * @param style
     */
    public LinuxDecorations(Composite parent, int style) {
        super(parent, SWT.NULL);
        GridLayout gl = new GridLayout(1, true);
        gl.marginWidth = gl.marginHeight = 4;
        setLayout(gl);
        label = new CLabel(this, SWT.NULL);
        Font f = label.getFont();
        if(f != null) {
            FontData[] fds = f.getFontData();
            if(fds != null && fds.length > 0) {
                FontData fd = fds[0];
                f = FontManager.getFont(fd.getName(), fd.getHeight(), SWT.BOLD, false,	false);
                label.setFont(f);
            }
        }
        GridData ld = new GridData();
        ld.grabExcessHorizontalSpace = true;
        ld.horizontalAlignment = GridData.FILL;
        ld.verticalAlignment = GridData.BEGINNING;
        label.setLayoutData(ld);
        label.setForeground(ColorManager.getColor(255, 255, 255));
        label.setBackground(new Color[] { 
                ColorManager.getColor(0, 20, 100),
                ColorManager.getColor(100, 200, 255)}, 
                new int[] { 100 });
        addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle b = getBounds();
                int w = b.width - 1;
                int h = b.height - 1;
                e.gc.setForeground(ColorManager.getColor(230, 230, 230));
                e.gc.drawLine(0, 0, w, 0);
                e.gc.drawLine(0, 0, 0, h);
                e.gc.setForeground(ColorManager.getColor(100, 100, 100));
                e.gc.drawLine(w, 0, w, h);
                e.gc.drawLine(0, h, w, h);
//                e.gc.setForeground(ColorManager.getColor(180, 180, 180));
//                e.gc.drawLine(1, 1, w-1, 1);
//                e.gc.drawLine(1, 1, 1, h-1);
////                e.gc.setForeground(ColorManager.getColor(150, 150, 150));
//                e.gc.drawLine(w-1, 1, w-1, h-1);
//                e.gc.drawLine(1, h-1, w-1, h-1);
            }});
    }

    public void setText(String text) {
        label.setText(text);
        layout();
    }
    
    public void setImage(Image image) {
        label.setImage(image);
        layout();
    }
}
