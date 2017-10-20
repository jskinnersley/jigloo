/*
 * Created on Sep 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.properties.descriptors;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertySource;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.wrappers.FormComponentWrapper;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SiblingPropertyDescriptor extends ChoicePropertyDescriptor {

    public SiblingPropertyDescriptor(
            Object id,
            String displayName,
            FormComponent comp,
            IPropertySource propSrc, 
            boolean allowNull,
            boolean useWrappers,
            boolean includeSelf) {
        super(id, displayName, comp, propSrc);
        Vector sibs = comp.getParent().getChildren();
        int numObjs = sibs.size();
        if (!sibs.contains(comp))
            numObjs++;
        if(!allowNull)
            numObjs --;
        if(includeSelf)
            numObjs++;
        if(useWrappers)
            objs = new Object[numObjs];
        choices = new String[numObjs];
        int index = 0;
        if(allowNull) {
            index = 1;
            if(useWrappers)
                objs[0] = "null";
            choices[0] = "null";
        }
        for (int j = 0; j < sibs.size(); j++) {
            FormComponent fc = (FormComponent) sibs.elementAt(j);
            if ((includeSelf || !fc.equals(comp)) && index < numObjs) {
                if(useWrappers)
                    objs[index] = new FormComponentWrapper(fc);
                choices[index] = fc.getName();
                index++;
            }
        }
    }
}
