/*
 * Created on Jul 20, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.wrappers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.editors.FormEditor;
import com.cloudgarden.jigloo.eval.JavaCodeParser;
import com.cloudgarden.jigloo.interfaces.IFormPropertySource;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.NodeUtils;
import com.cloudgarden.jigloo.properties.descriptors.EventListenerPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.EventPropertyDescriptor;
import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.xml.XMLWriter;

/**
 * @author jsk
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EventWrapper implements IFormPropertySource {

    FormComponent comp;
    Vector listClasses;
    IPropertyDescriptor[] descs;
    boolean isSwing;
    HashMap setEventMap = new HashMap();
    HashMap codeMap = new HashMap();
    HashMap methodMap = new HashMap();
    HashMap renameMap = new HashMap();

    private Class listClass;
    private HashMap childMap;
    private EventWrapper parent;
    private String listenerElement;

    public EventWrapper(FormComponent comp, String listenerElement) {
        this.comp = comp;
        this.listenerElement = listenerElement;
        isSwing = comp.isSwing();
    }

    public EventWrapper(FormComponent comp, Class listenerClass) {
        this.comp = comp;
        listClass = listenerClass;
        isSwing = comp.isSwing();
        initialize(true);
    }

    public void dispose() {
    	comp = null;
        if (childMap != null && childMap.size() > 0) {
            Iterator it = childMap.keySet().iterator();
            while (it.hasNext()) {
                EventWrapper ew = (EventWrapper) childMap.get(it.next());
                ew.dispose();
            }
        }
    }
    
    public EventWrapper(FormComponent comp, Node node, boolean isSwing) {
        this(comp, (Class) null);
        this.isSwing = isSwing;
        NodeList list = NodeUtils.getChildrenOfNode("Events", node);
        if (list == null)
            return;
        for (int i = 0; i < list.getLength(); i++) {
            Node event = list.item(i);
            String evtName = NodeUtils.getAttribute("event", event);
            String listener = NodeUtils.getAttribute("listener", event);
            String handler = NodeUtils.getAttribute("handler", event);
            if (listener != null) {
                listener = getShortClassName(listener);
                EventWrapper ew = (EventWrapper) childMap.get(listener);
                if (ew != null) {
                    ew.setHandler(evtName, handler, "", "evt");
                } else {
                    System.err.println("Listener class " + listener
                            + " not recognized");
                }
            }
            String listElem = NodeUtils.getAttribute("listenerElement", event);
            if (listElem != null) {
                EventWrapper ew = (EventWrapper) childMap.get(listener);
                ew.setListenerElement(listElem);
            }
        }
    }

    public FormComponent getFormComponent() {
        return comp;
    }
    
    public void setHandler(String listener, String event, String handler,
            String code, String argName) {
        listener = getShortClassName(listener);
        EventWrapper ew = (EventWrapper) childMap.get(listener);
        if (ew != null) {
            ew.setHandler(event, handler, code, argName);
        }
    }

    protected void addListenerWrapper(String name, EventWrapper ew) {
        childMap.put(name, ew);
    }

    public EventWrapper getCopy(FormComponent newComp) {
        EventWrapper ew1 = new EventWrapper(newComp, listClass);

        if (!representsListener()) {
            if (childMap != null && childMap.size() > 0) {
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    EventWrapper ew = (EventWrapper) childMap.get(name);
                    ew1.addListenerWrapper(name, ew.getCopy(newComp));
                }
            }
            return ew1;
        }

        Iterator it = setEventMap.keySet().iterator();
        while (it.hasNext()) {
            String eventMethod = (String) it.next();
            String eventHandler = (String) setEventMap.get(eventMethod);
            String[] code = (String[]) codeMap.get(eventMethod);
            if(code != null)
            	ew1.setHandler(eventMethod, eventHandler, code[0], code[1]);
            else
            	ew1.setHandler(eventMethod, eventHandler, "", "evt");
        }
        if (listenerElement != null)
            ew1.setListenerElement(listenerElement);
        return ew1;
    }

    public HashMap getRenameMap() {
        return renameMap;
    }
    
    public void setComponentName(String componentName, HashMap renameMap1) {
        if (listClass == null) {
            renameMap.clear();
            if (childMap != null && childMap.size() > 0) {
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    EventWrapper ew = (EventWrapper) childMap.get(it.next());
                    ew.setComponentName(componentName, renameMap);
                }
            }
            initialize(false);
        } else {
            Iterator it = setEventMap.keySet().iterator();
            while (it.hasNext()) {
                String eventMethod = (String) it.next();
                String eventHandler = (String) setEventMap.get(eventMethod);
                String newHandler = getHandlerName(eventMethod, componentName);
                renameMap1.put(eventHandler.substring(0, 
                        eventHandler.length() - EventPropertyDescriptor.HANDLER_METHOD.length()), 
                        newHandler);
                setEventMap.put(eventMethod, newHandler);
            }
            initialize(false);
        }
    }

    public void populateDOMNode(Element node, Document document, String indent) {
        if (listClass == null) {
            if (childMap != null && childMap.size() > 0) {
                Element events = document.createElement("Events");
                node.appendChild(document.createTextNode("\n" + indent));
                node.appendChild(events);
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    EventWrapper ew = (EventWrapper) childMap.get(it.next());
                    ew.populateDOMNode(events, document, indent
                            + XMLWriter.INDENT);
                }
                events.appendChild(document.createTextNode("\n" + indent));
            }
            return;
        }

        Iterator it = setEventMap.keySet().iterator();
        while (it.hasNext()) {
            String eventMethod = (String) it.next();
            String eventHandler = (String) setEventMap.get(eventMethod);
            if (eventMethod == null && eventHandler == null)
                continue;
            Method meth = (Method) methodMap.get(eventMethod);
            if (meth == null) {
                System.err.println("NO METHOD FOUND FOR " + eventMethod + ", "
                        + eventHandler);
                continue;
            }
            Class[] params = meth.getParameterTypes();
            String[] paramTypes = new String[1];
            paramTypes[0] = getShortClassName(params[0], null);

            Element evtHandler = document.createElement("EventHandler");
            node.appendChild(document.createTextNode("\n" + indent));
            node.appendChild(evtHandler);
            evtHandler.setAttribute("event", eventMethod);
            evtHandler.setAttribute("listener", listClass.getName());
            evtHandler.setAttribute("handler", eventHandler);
        }
        if (listenerElement != null) {
            Element ehc = document.createElement("EventHandlerClass");
            node.appendChild(document.createTextNode("\n" + indent));
            node.appendChild(ehc);
            ehc.setAttribute("listener", listClass.getName());
            ehc.setAttribute("listenerElement", listenerElement);
        }

    }

    public void setHandler(String eventName, String handler, String code, String argName) {
        setEventMap.put(eventName, handler);
        if(code != null)
        	codeMap.put(eventName, new String[] {code, argName});
    }

    public Object getEditableValue() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descs != null
                && ((isSwing && comp.isSwing()) || (!isSwing && !comp.isSwing())))
            return descs;
        isSwing = comp.isSwing();
        //initialize();
        return descs;
    }

    public boolean representsListener() {
        return listClass != null;
    }

    private void initialize(boolean full) {
        if (!representsListener()) {
            Class cls = comp.getConvertedMainClass();
            listClasses = PropertySourceFactory.findEventListenerClasses(cls);
            if (full)
                childMap = new HashMap();
            descs = new IPropertyDescriptor[listClasses.size()];
            for (int i = 0; i < descs.length; i++) {
                Class listClass = (Class) listClasses.elementAt(i);
                String name = getShortClassName(listClass, null);
                descs[i] = new EventListenerPropertyDescriptor(name, name,
                        comp, listClass);
                //descs[i] = new FormPropertyDescriptor(name, name, comp,
                // this);
                if (full)
                    childMap.put(name, new EventWrapper(comp, listClass));
            }
        } else {
            Method[] meths = listClass.getMethods();
            descs = new IPropertyDescriptor[meths.length];
            if (full)
                methodMap = new HashMap();
            for (int i = 0; i < descs.length; i++) {
                String methodName = meths[i].getName();
                if (full)
                    methodMap.put(methodName, meths[i]);
                String compName = comp.getName();
                //TODO get a unique name for the root component - use
                // addComponent to
                //determine a unique name like jFrame1 etc.
                if (comp.isRoot())
                    compName = "root";
                String defName = getHandlerName(methodName, compName);
                descs[i] = new EventPropertyDescriptor(methodName, methodName,
                        this, defName);
            }
        }
    }

    public static String getHandlerName(String methodName, String componentName) {
        return componentName + methodName.substring(0, 1).toUpperCase()
                + methodName.substring(1);
    }

    public void getHandlers(Vector handlers) {
        if (listClass == null) {
            if (childMap != null && childMap.size() > 0) {
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    EventWrapper ew = (EventWrapper) childMap.get(it.next());
                    ew.getHandlers(handlers);
                }
            }
            return;
        }

        Iterator it = setEventMap.keySet().iterator();
        while (it.hasNext()) {
            String eventMethod = (String) it.next();
            String eventHandler = (String) setEventMap.get(eventMethod);
            handlers.add(eventHandler);
        }
    }

    public Class getListenerClass() {
        return listClass;
    }

    public HashMap getChildEventWrappers() {
        return childMap;
    }

    public EventWrapper getChildEventWrapper(String listenerName) {
        return (EventWrapper)childMap.get(listenerName);
    }

    public String getJavaCode(String parent, boolean isSwing,
            IJavaCodeManager jcm) {

        String code = "";
        if (listClass == null) {
            //this is the top EventWrapper
            if (childMap != null && childMap.size() > 0) {
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    EventWrapper ew = (EventWrapper) childMap.get(it.next());
                    code += ew.getJavaCode(parent, isSwing, jcm);
                }
            }
            return code;
        }

        if (listenerElement != null || setEventMap.size() > 0) {
            Class adapter = getAdapter();
            code += "\t\t";
            //            if (comp.isRoot()) {
            //                code += "this";
            //            } else {
            code += comp.getNameInCode();
            //            }
            if (listenerElement != null
                    && !listenerElement.equals("<anonymous>")) {
                code += ".add" + getShortClassName(listClass, null) + "("
                        + getNameInCode() + ");\n";
            } else {
                code += ".add" + getShortClassName(listClass, null) + "(new ";
                if (adapter != null) {
                    code += getShortClassName(adapter, jcm);
                } else {
                    code += getShortClassName(listClass, jcm);
                }
                code += "() {\n";
                code += "\t\t});\n";
            }
        }
        return code;
    }

    public Vector getHandlers() {
        Vector listVec = new Vector();
        Class adapter = getAdapter();
        Method[] meths = listClass.getMethods();
        for (int i = 0; i < meths.length; i++) {
            if (adapter == null || setEventMap.containsKey(meths[i].getName())) {
                listVec.add(meths[i]);
            }
        }
        return listVec;
    }

    public Vector getAllHandlers() {
        Vector listVec = new Vector();
        Method[] meths = listClass.getMethods();
        for (int i = 0; i < meths.length; i++)
            listVec.add(meths[i]);
        return listVec;
    }

    public String getJavaCodeForHandler(String methodName, IJavaCodeManager jcm) {
        try {
            Method[] meths = listClass.getMethods();
            Method meth = null;
            for (int i = 0; i < meths.length; i++) {
                Method m = meths[i];
                if (m.getName().equals(methodName))
                    meth = m;
            }
            if (meth == null)
                return "";
            String val = (String) setEventMap.get(methodName);
            
            //   		System.out.println("GET JAVA CODE FOR HANDLER "+methodName+", "+val);
            
            String eventType = null;
            String[][] eventParams = new String[2][];
            
            if(meth.getParameterTypes().length == 1) {
                eventType = getShortClassName(meth.getParameterTypes()[0], jcm);
                eventParams[0] = new String[] { eventType };
                eventParams[1] = new String[] { "evt" };
            }
            
            String code = "\t\t\tpublic void " + methodName + "(";

            String[] codes = (String[]) codeMap.get(methodName);
            
            if(eventType != null) {
            	if(codes != null)
                    code += eventType + " "+codes[1]+") {";
            	else
            		code += eventType + " evt) {";
            } else
                code += ") {";
            
            if (codes != null) {
                code += codes[0];
            } else {
                String HM = EventPropertyDescriptor.HANDLER_METHOD;
                if (val != null && val.endsWith(HM)) {
                    String handlerMethod = val.substring(0, val.length() - HM.length());
                    handlerMethod = JiglooUtils.getUnqualifiedName(handlerMethod);
                    handlerMethod = JiglooUtils.replace(handlerMethod, ".", "_");
                    code += "\n\t\t\t\t" + handlerMethod + "(evt);\n\t\t\t";
                    String body = "System.out.println(\""
                            + comp.getNameInCode() + "." + methodName
                            + ", event=\"+evt);"
                            + "\n//TODO add your code for "
                            + comp.getNameInCode() + "." + methodName + "\n";
                    if(jcm instanceof JavaCodeParser) {
                    	JavaCodeParser jcp = (JavaCodeParser)jcm;
                        MethodDeclaration md = jcp.addMethod(handlerMethod, body, "void",
                                eventParams[0], eventParams[1],
                                Flags.AccPrivate, "", null, true);
                        jcp.indentNode(md);
                    } else {
                        jcm.addMethod(handlerMethod, body, "void",
                                eventParams[0], eventParams[1],
                                Flags.AccPrivate, "");
                    }
                } else {
                    code += "\n\t\t\t\tSystem.out.println(\""
                            + comp.getNameInCode() + "." + methodName
                            + ", event=\"+evt);";
                    code += "\n\t\t\t\t//TODO add your code for "
                            + comp.getNameInCode() + "." + methodName
                            + "\n\t\t\t";
                }
            }
            code += "}\n";
            return code;
        } catch (Throwable t) {
            jiglooPlugin.handleError(t, "Error getting code for "+this+", "+methodName);
            return "";
        }
    }

    public Class getAdapter() {
        String name = listClass.getName();
        int pos = name.lastIndexOf("Listener");
        if (pos > 0) {
            name = name.substring(0, pos) + "Adapter";
        } else {
            System.err.println("EventWrapper: Error getting adapter for class "
                    + listClass + ", " + this);
            return null;
        }
        try {
            return comp.getEditor().loadClass(name);
        } catch (Throwable e) {
            return null;
        }
    }

    private String getShortClassName(Class clazz, IJavaCodeManager jcm) {
        String name = clazz.getName();
        if (jcm != null)
            jcm.addImport(name);
        return getShortClassName(name);
    }

    private String getShortClassName(String name) {
        int pos = name.lastIndexOf(".");
        return name.substring(pos + 1);
    }

    public Object getRawPropertyValue(Object id) {
        return setEventMap.get(id);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object id) {
        String val = null;
        if (listClass != null) {
            val = (String)setEventMap.get(id);
        } else {
            return childMap.get(id);
        }
        if(val == null)
            val = "not handled";
        if(val.endsWith(EventPropertyDescriptor.NOT_HANDLED))
            return EventPropertyDescriptor.NOT_HANDLED;
        if(val.endsWith(EventPropertyDescriptor.HANDLER_METHOD))
            return EventPropertyDescriptor.HANDLER_METHOD;
        if(val.endsWith(EventPropertyDescriptor.INLINE))
            return EventPropertyDescriptor.INLINE;
        return val;
    }

    public boolean hasSetProperty() {
        return setEventMap.size() > 0;
    }

    public boolean usesListenerElement() {
        if (listenerElement == null)
            return false;
        if (listenerElement.equals("<anonymous>"))
            return false;
        if (listenerElement.equals("<none>"))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object id) {
        if (listClass != null) {
//            if (getAdapter(listClass) == null)
//                return true;
            return setEventMap.containsKey(id);
        } else {
            return ((EventWrapper) childMap.get(id)).hasSetProperty();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object id) {
        setEventMap.remove(id);
        comp.getEditor().setDirty(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
     *           java.lang.Object)
     */        
    public void setPropertyValue(Object id, Object value) {
        if (value == null)
            value = "";
//        		System.out.println(
//        		hashCode() + " SET EVT PROP " + id + ", " + value + ", " +
//         value.getClass() + ", " + childMap);
        if (childMap != null) {
            EventWrapper cew = (EventWrapper) childMap.get(id);
            Object newVal = value;
            if (value instanceof EventWrapper) {
                newVal = ((EventWrapper) value).toString();
            } else {
                listenerElement = (String) value;
                newVal = listenerElement;
            }
            if ("".equals(value) || "null".equals(value)
                    || "<none>".equals(value))
                newVal = null;
            if (cew != null) {
                //System.out.println("REPAIR EW IN CODE GOT CEW " + cew);
                String oldVal = cew.getListenerElement();
                if ("".equals(oldVal) || "null".equals(oldVal)
                        || "<none>".equals(oldVal))
                    oldVal = null;
                if (oldVal == null && cew.hasSetProperty())
                    oldVal = "<anonymous>";
                if (oldVal == null && newVal == null)
                    return;
                if (oldVal != null && oldVal.equals(newVal))
                    return;
                cew.setListenerElement((String) newVal);
                //System.out.println("REPAIR EW IN CODE");
                comp.repairEventWrapperInCode();
//                comp.getEditor().getJavaCodeParser().updateWCBuffer();
//                comp.getEditor().setDirty(true);
                comp.getEditor().setDirtyAndUpdate(true, false);
                return;
            }
        }
        if (value.equals("") || value == null || 
                value.toString().endsWith(EventPropertyDescriptor.NOT_HANDLED)) {
            /*
             * System.out.println("MAP (1) = "+eventMap.get(id)); if
             * (eventMap.get(id) != null) { boolean yes =
             * MessageDialog.openConfirm( comp.getEditor().getSite().getShell(),
             * "Confirm delete", "Really delete the " + eventMap.get(id) + "
             * event handler (and it's code)?"); System.out.println("RES =
             * "+yes); if (!yes) return; }
             */
            
            //don't do anything if not changed
            if(!setEventMap.containsKey(id))
                return;
            
            setEventMap.remove(id);
            resetPropertyValue(id);
            setNeedsUpdateInCode((String)id, true);
            comp.repairEventWrapperInCode();
            setNeedsUpdateInCode((String)id, false);
            comp.getEditor().setDirtyAndUpdate(true, false);
            codeMap.remove((String)id);
            //			comp.getEditor().getJavaCodeParser().updateWCBuffer();
            return;
        }
        //don't do anything if not changed
        if(value.equals(setEventMap.get(id)))
            return;
        
        setEventMap.put(id, value);
        resetName();

        setNeedsUpdateInCode((String)id, true);
        comp.repairEventWrapperInCode();
        setNeedsUpdateInCode((String)id, false);
        
        //		comp.getEditor().getJavaCodeParser().updateWCBuffer();

    	FormEditor ed = comp.getEditor();
    	
        if (value instanceof String) {
            String mn = (String) value;
            String HM = EventPropertyDescriptor.HANDLER_METHOD;
            String IL = EventPropertyDescriptor.INLINE;
            if (mn.endsWith(HM)) {
            	mn = mn.substring(0, mn.length() - HM.length());
            	JavaCodeParser jcp = ed.getJavaCodeParser();
            	ASTNode mnode = jcp.getMethodNode(mn);
            	if(mnode != null) {
            		ed.showSourceTab();
            		jcp.setCodeSelectPosition(mnode.getStartPosition());
            	}
            } else if (mn.endsWith(IL)) {
                //		        mn = mn.substring(0, mn.length() - IL.length());
                //		        comp.getEditor().setMethodToShow(mn);
            }
        }
        
        ed.setDirtyAndUpdate(true, false);

    }

    public void setListenerElement(String listener, String le) {
        listener = getShortClassName(listener);
        EventWrapper ew = (EventWrapper) childMap.get(listener);
        if (ew != null)
            ew.setListenerElement(le);
    }

    public String getListenerElement() {
        return listenerElement;
    }

    public void setListenerElement(String le) {
        if ("".equals(le) || "null".equals(le) || "<none>".equals(le))
            le = null;
        if (le == null)
            setEventMap.clear();
        listenerElement = le;
    }

    public String toString() {
        if (listenerElement != null)
            return getNameInCode();
        if (hasSetProperty())
            return "<anonymous>";
        return "<none>";
    }

    public Vector getPropertyNames() {
        return null;
    }

    public String getJavaCodeForPropertySetter(String pName, IJavaCodeManager jcm) {
        return null;
    }

    public String getJavaConstructor(IJavaCodeManager jcm) {
        return null;
    }

    public boolean hasProperty(String id) {
        return false;
    }

    public String getName() {
        return null;
    }

    public String getNameInCode() {
        if (listenerElement == null)
            return null;
        int pos = listenerElement.indexOf(".");
        if (pos < 0)
            return listenerElement;
        return listenerElement.substring(pos + 1);
    }

    public void setName(String name) {
    }

    /**
     * Resets the names of any existing property handler methods to the
     * ones related to the parent component's name. Also (if handler methods are used)
     * resets their method names too so that component related ones are used.
     * This is needed, for instance when setting event handler methods for multiple
     * elements at once, or when pasting an element in that is a copy of one that
     * uses handler methods.
     */
    public void resetName() {
        if (listClass == null) {
            if (childMap != null && childMap.size() > 0) {
                Iterator it = childMap.keySet().iterator();
                while (it.hasNext()) {
                    EventWrapper ew = (EventWrapper) childMap.get(it.next());
                    if(ew != null)
                    	ew.resetName();
                }
            }
        } else {
            Iterator it = setEventMap.keySet().iterator();
            while (it.hasNext()) {
                String eventMethod = (String) it.next();
                String eventHandler = (String) setEventMap.get(eventMethod);
                String newHandler = getHandlerName(eventMethod, comp.getName());
                if(eventHandler.endsWith(EventPropertyDescriptor.HANDLER_METHOD)) {
                	newHandler += EventPropertyDescriptor.HANDLER_METHOD;
                	codeMap.remove(eventMethod);
                } else if(eventHandler.endsWith(EventPropertyDescriptor.INLINE))
                	newHandler += EventPropertyDescriptor.INLINE;
                setEventMap.put(eventMethod, newHandler);
            }
        }
    }
    
    public String getBlockName() {
        return null;
    }

    public void setBlockName(String name) {
    }

    public void setObject(Object obj) {
    }

    private Vector needsUpdate = new Vector();

    public boolean needsUpdateInCode(String propName) {
        return needsUpdate.contains(propName);
    }

    public boolean isSyntheticProperty(String id) {
        return false;
    }

    public void setNeedsUpdateInCode(String name, boolean needs) {
        if (needs) {
            if (!needsUpdate.contains(name))
                needsUpdate.add(name);
        } else {
            needsUpdate.remove(name);
        }
    }

}