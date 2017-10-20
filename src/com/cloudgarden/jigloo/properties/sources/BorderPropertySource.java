package com.cloudgarden.jigloo.properties.sources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.interfaces.IJavaCodeManager;
import com.cloudgarden.jigloo.properties.descriptors.BorderPropertyDescriptor;
import com.cloudgarden.jigloo.properties.descriptors.FormPropertyDescriptor;
import com.cloudgarden.jigloo.util.FieldManager;
import com.cloudgarden.jigloo.util.JiglooUtils;
import com.cloudgarden.jigloo.wrappers.ColorWrapper;
import com.cloudgarden.jigloo.wrappers.FieldWrapper;
import com.cloudgarden.jigloo.wrappers.FontWrapper;
import com.cloudgarden.jigloo.wrappers.IconWrapper;
import com.cloudgarden.jigloo.wrappers.ImageWrapper;
import com.cloudgarden.jigloo.xml.XMLWriter;

public class BorderPropertySource implements IPropertySource {

    private static Vector borderClasses = new Vector();
    private static HashMap descMap = new HashMap();
    static {
        borderClasses.add(BevelBorder.class);
        borderClasses.add(CompoundBorder.class);
        borderClasses.add(EmptyBorder.class);
        borderClasses.add(EtchedBorder.class);
        borderClasses.add(LineBorder.class);
        borderClasses.add(MatteBorder.class);
        borderClasses.add(SoftBevelBorder.class);
        borderClasses.add(TitledBorder.class);

        descMap.put(BevelBorder.class, new Object[][] { { "bevelType",
                Integer.class, "highlightOuterColor", Color.class,
                "highlightInnerColor", Color.class, "shadowOuterColor",
                Color.class, "shadowInnerColor", Color.class },
                { "bevelType",
                    Integer.class, "highlightOuterColor", Color.class,
                   "shadowOuterColor",
                    Color.class },
                    { "bevelType",
                        Integer.class}});
        descMap.put(CompoundBorder.class, new Object[][] { { "outsideBorder",
                Border.class, "insideBorder", Border.class } });
        descMap.put(EmptyBorder.class, new Object[][] { { "borderInsets",
                Insets.class } });
        descMap.put(EtchedBorder.class, new Object[][] { { "etchType",
                Integer.class, "highlightColor", Color.class, "shadowColor",
                Color.class },
                { "etchType",
                    Integer.class, "highlightColor", Color.class, "shadowColor",
                    Color.class },
                    { "highlightColor", Color.class, "shadowColor",
                        Color.class },
                        { "etchType",
                            Integer.class }
                });
        descMap.put(LineBorder.class, new Object[][] { { "lineColor",
                Color.class, "thickness", Integer.class, "roundedCorners",
                boolean.class } });
        descMap.put(MatteBorder.class, new Object[][] { { "borderInsets",
                Insets.class, "matteColor", Color.class
        //,"tileIcon", Image.class
                } });
        descMap
                .put(TitledBorder.class, new Object[][] {
                { "border", Border.class, "title", String.class,
                        "titleJustification", Integer.class, "titlePosition",
                        Integer.class, "titleFont", Font.class, "titleColor",
                        Color.class },
                { "border", Border.class, "title", String.class,
                        "titleJustification", Integer.class, "titlePosition",
                        Integer.class, "titleFont", Font.class },
                { "border", Border.class, "title", String.class,
                        "titleJustification", Integer.class, "titlePosition",
                        Integer.class },
//                { "border", Border.class },
                { "title", String.class } });
        descMap.put(SoftBevelBorder.class, new Object[][] { { "bevelType",
                Integer.class, "highlightOuterColor", Color.class,
                "highlightInnerColor", Color.class, "shadowOuterColor",
                Color.class, "shadowInnerColor", Color.class } });
        descMap.put(Border.class, new Object[][] { { "TYPE", Border.class } });
    }

    private boolean hasChanged = false;
    protected IPropertyDescriptor[] descriptors;
    private IPropertyDescriptor borderDesc;
    private IPropertySource parent;
    protected Border border = null;
    private FormComponent comp;
    private String thisPropertyName;
    private Object borderType = null;
    private Vector setProps = new Vector();
    private HashMap defaultProps = new HashMap();
    private HashMap properties = new HashMap();

    public BorderPropertySource(Border border, FormComponent comp,
            String propName, IPropertySource parent) {
        this.border = border;
        this.parent = parent;
        this.comp = comp;
        this.thisPropertyName = propName;
        if (border instanceof CompoundBorder) {
            CompoundBorder cb = (CompoundBorder) border;
            Border ib = cb.getInsideBorder();
            Border ob = cb.getOutsideBorder();
            properties.put("insideBorder", new BorderPropertySource(ib, comp,
                    "insideBorder", this));
            properties.put("outsideBorder", new BorderPropertySource(ob, comp,
                    "outsideBorder", this));
        }
    }

    public BorderPropertySource getCopy(FormComponent copy) {
        //TODO make a copy of the border
    	Border b = border;
    	if (border instanceof TitledBorder) {
        	TitledBorder tb = (TitledBorder) border;
        	b = new TitledBorder(tb.getBorder(), 
        			tb.getTitle(), tb.getTitleJustification(), tb.getTitlePosition(), 
					tb.getTitleFont(), tb.getTitleColor());
        } else if (border instanceof CompoundBorder) {
            CompoundBorder cb = (CompoundBorder) border;
            Border ib = cb.getInsideBorder();
            Border ob = cb.getOutsideBorder();
        	b = new CompoundBorder(ib, ob);
        }
        return new BorderPropertySource(b, copy, thisPropertyName, copy);
    }

    private Object[] getFirstFromDescMap(Class cls) {
        Object[][] descInfoArray = getArrayFromDescMap(cls);
        if (descInfoArray == null)
            return null;
        return descInfoArray[0];
    }

    private Object[][] getArrayFromDescMap(Class cls) {
        Object[][] descInfoArray = null;
        while ((descInfoArray = (Object[][]) descMap.get(cls)) == null
                && cls != null) {
            cls = cls.getSuperclass();
        }
        if (descInfoArray == null)
            return null;
        return descInfoArray;
    }

    public String getJavaCode(IJavaCodeManager jcm) {
        if (border == null)
            return "null";
        //jcm.addImport(border.getClass().getName());
        //String code = "new " +
        // JiglooUtils.getShortClassName(border.getClass()) + "(";
        jcm.addImport(BorderFactory.class.getName());
        String code = null;
        if (border instanceof LineBorder || border instanceof SoftBevelBorder) {
            if (jiglooPlugin.useImports()) {
                jcm.addImport(border.getClass().getName());
                code = "new "
                        + JiglooUtils.getShortClassName(border.getClass())
                        + "(";
            } else {
                code = "new " + border.getClass().getName() + "(";
            }
        } else {
            code = "BorderFactory.create"
                    + JiglooUtils.getShortClassName(border.getClass()) + "(";
        }
        Object[][] descInfoArray = getArrayFromDescMap(border.getClass());
        if (descInfoArray == null)
            return "null";
        Object[] descInfo;
        int lastOK = 0;
        for (int i = 1; i < descInfoArray.length; i++) {
            Object[] di = descInfoArray[i];
            boolean ok = true;
            for (int k = 0; k < setProps.size(); k++) {
                String setProp = (String) setProps.elementAt(k);
                boolean found = false;
                for (int j = 0; j < di.length; j += 2) {
                    String name = (String) di[j];
                    if (name.equals(setProp)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ok = false;
                    break;
                }
            }
            if (ok)
                lastOK = i;
        }
        descInfo = descInfoArray[lastOK];
        for (int i = 0; i < descInfo.length; i += 2) {
            String name = (String) descInfo[i];
            Class cls = (Class) descInfo[i + 1];
            Object val = getPropertyValue(name);
            if (i != 0)
                code += ", ";
            if (cls.equals(Border.class) && !(border instanceof TitledBorder)) {
                code += "\n" + ((BorderPropertySource) val).getJavaCode(jcm);
            } else if (cls.equals(Insets.class)) {
                Insets ins = ((InsetsPropertySource) val).getValue();
                code += ins.top + ", " + ins.left + ", " + ins.bottom + ", "
                        + ins.right;
            } else {
                
                if(cls.equals(Integer.class))
                    cls = int.class;
                else if(cls.equals(Boolean.class))
                    cls = boolean.class;
                
                code += comp.getJavaCodeForObject(val, cls, null)[1];
            }
        }
        code += ")";
        return code;
    }

    public void populateDOMNode(Element node, Document document, String indent) {
        populateDOMNode(node, document, indent, false);
    }

    private void populateDOMNode(Element node, Document document,
            String indent, boolean newNode) {
        Element constNode = node;
        String nind = "\n" + indent;
        String nind2 = nind + XMLWriter.INDENT;
        if (newNode) {
            constNode = document.createElement("Property");
            node.appendChild(constNode);
            node.appendChild(document.createTextNode(nind));
        }
        IPropertyDescriptor[] lprops = getPropertyDescriptors();
        if (lprops == null || lprops.length == 0)
            return;
        if (border != null)
            constNode.setAttribute("class", border.getClass().getName());
        for (int i = 0; i < lprops.length; i++) {
            Object id = lprops[i].getId();
            //if (isPropertySet(id)) {
            if ("TYPE".equals(id))
                continue;
            Object val = getPropertyValue(id);
            Element lprop = document.createElement("Property");
            constNode.appendChild(document.createTextNode(nind2));
            constNode.appendChild(lprop);
            lprop.setAttribute("name", (String) id);
            if (val instanceof BorderPropertySource) {
                BorderPropertySource ldw = (BorderPropertySource) val;
                ldw.populateDOMNode(lprop, document, indent + XMLWriter.INDENT,
                        false);
            } else {
                XMLWriter.setElementValue(lprop, val, document, indent);
            }
            //}
        }
        constNode.appendChild(document.createTextNode(nind));
    }

    protected void firePropertyChanged(String propName) {
    }

    public Object getEditableValue() {
        return this;
        //return toString();
        //if(border == null) return "No Border";
        //return "HELLO";
        //return border.getClass();
    }

    public void setParent(IPropertySource parent) {
        this.parent = parent;
    }

    private Class getPropClass(String pname) {
        Class cls = border.getClass();
        Object[] descInfo = getFirstFromDescMap(cls);
        if (descInfo == null)
            return null;
        for (int i = 0; i < descInfo.length; i += 2) {
            String name = (String) descInfo[i];
            if (name.equals(pname))
                return (Class) descInfo[i + 1];
        }
        return null;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descriptors != null)
            return descriptors;
        if (borderDesc == null)
            borderDesc = getBorderDesc("TYPE", this);
        if (border == null) {
            return new IPropertyDescriptor[] {};
            //return new IPropertyDescriptor[] { borderDesc };
        }
        Object[] descInfo = getFirstFromDescMap(border.getClass());
        if (descInfo == null) {
            return new IPropertyDescriptor[] {};
            //return new IPropertyDescriptor[] { borderDesc };
        }
        descriptors = new IPropertyDescriptor[descInfo.length / 2];
        //descriptors[0] = borderDesc;
        for (int i = 0; i < descInfo.length; i += 2) {
            String name = (String) descInfo[i];
            Object val = getPropertyValue(name);
            if (val != null) {
                descriptors[i / 2] = PropertySourceFactory
                        .getPropertyDescriptor(name, val, null, comp, this);
            } else {
                Class clazz = (Class) descInfo[i + 1];
                if (clazz.equals(Color.class))
                    clazz = ColorWrapper.class;
                if (clazz.equals(Font.class))
                    clazz = FontWrapper.class;
                descriptors[i / 2] = PropertySourceFactory
                        .getPropertyDescriptor(name, clazz, comp, this);
            }
        }
        return descriptors;
    }

    public static FormPropertyDescriptor getBorderDesc(String name,
            IPropertySource propSrc) {
        Object[] objs = new Object[borderClasses.size() + 1];
        String[] choices = new String[objs.length];
        objs[0] = "<default>";
        choices[0] = "<default>";
        for (int i = 0; i < borderClasses.size(); i++) {
            Class bc = (Class) borderClasses.elementAt(i);
            objs[i + 1] = bc;
            choices[i + 1] = JiglooUtils.getShortClassName(bc);
        }
        FormComponent fc = null;
        if (propSrc instanceof FormComponent)
            fc = (FormComponent) propSrc;
        return new BorderPropertyDescriptor(name, name, fc, objs, choices,
                propSrc);
    }

    public Object getPropertyValue(Object propName) {
        try {
            if ("TYPE".equals(propName)) {
                if (border == null)
                    return "null";
                return JiglooUtils.getShortClassName(border.getClass());
            } else {
                String pname = (String) propName;
                Object pval = properties.get(pname);
                if (pval != null) {
                    return pval;
                }
                initDefaultValue(pname);

                Object val = defaultProps.get(pname);
                val = convertToPropSource(val, pname);

                if (val instanceof BorderPropertySource) {
                    ((BorderPropertySource) val).setParent(this);
                }

                properties.put(pname, val);
                return val;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object queryProperty(String pname) {
        Object val = null;
        try {
            String gmName = pname;
            gmName = "get" + gmName.substring(0, 1).toUpperCase()
                    + gmName.substring(1);
            Method gm = border.getClass().getMethod(gmName, null);
            val = gm.invoke(border, null);
            //System.err.println("QUERY PROP " + pname + ", " + val);
        } catch (Exception e) {
            System.err.println("BPS ERROR querying " + pname + ", " + e);
            //e.printStackTrace();
        }
        return val;
    }

    private void setProperty(String pname, Object value, Class pclass) {
        Object val = null;
        try {
            String smName = pname;
            smName = "set" + smName.substring(0, 1).toUpperCase()
                    + smName.substring(1);
            Method sm = border.getClass().getMethod(smName,
                    new Class[] { pclass });
            sm.invoke(border, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Border getValue() {
        return border;
    }

    /*
     * public void setValue(Border border) { this.border = border; }
     */
    public FormComponent getFormComponent() {
        return comp;
    }

    public String getPropertyName() {
        return thisPropertyName;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
     */
    public boolean isPropertySet(Object propName) {
        return setProps.contains(propName);
        /*
         * if ("type".equals(propName)) return true; return false;
         */
    }

    //TODO change RectanglePropertySource etc, to handle resetPropertyValue
    //like this - needs changes to conscructors etc.
    public void resetPropertyValue(Object name) {
        System.err.println("RESET " + name + ", " + defaultProps.get(name));
        setPropertyValue(name, defaultProps.get(name));
        setProps.remove(name);
        //comp.resetPropertyValue(propName);
    }

    public void setBorder(Border border) {
        boolean needsDescs = true;

        if (border == null
                || (border != null && this.border != null && this.border
                        .getClass().equals(border.getClass())))
            descriptors = null;
        this.border = border;
    }

    public Border getBorder() {
        return border;
    }

    private Object convertToPropSource(Object value, String pname) {

        Class propCls = getPropClass(pname);
        if (propCls == null) {
            System.err.println("Class unknown for " + pname + ", " + this);
        }

        if (propCls != null && propCls.equals(Border.class)) {
            if (!(value instanceof BorderPropertySource)) {
                value = createBorder(value);
                BorderPropertySource bps = (BorderPropertySource) properties
                        .get(pname);
                if (bps == null)
                    bps = new BorderPropertySource((Border) value, comp, pname,
                            this);
                bps.setBorder((Border) value);
                value = bps;
            }
        }

//        if (value instanceof Integer) {
            String[] fieldOpts = FieldManager.getFieldOptions(pname, border
                    .getClass());
            if (fieldOpts != null) {
                if (value instanceof FieldWrapper) {
                    ((FieldWrapper) value).setFields(fieldOpts);
                } else {
                    value = new FieldWrapper(value, pname, fieldOpts,
                            comp);
                    ((FieldWrapper) value).setMainObject(border);
                }
            }
//        }
        value = PropertySourceFactory.convertToPropertySource(value, comp,
                pname);

        if (value == null) {
            if (propCls != null && propCls.equals(Border.class)) {
                value = new BorderPropertySource(null, comp, pname, this);
            }
        }

        if (value instanceof BorderPropertySource) {
            ((BorderPropertySource) value).setParent(this);
        }

        return value;
    }

    private void initDefaultValue(String pname) {
        try {
            if (!properties.containsKey(pname)) {
                Object val = queryProperty(pname);
                defaultProps.put(pname, val);
                //System.err.println("set def value " + pname + ", " + val);
            }
        } catch (Exception e) {
            System.err.println("Error getting default value for " + pname
                    + ", " + e);
        }
    }

    public String guessForNetbeans(String pname) {
        Object[] descInfo = getFirstFromDescMap(border.getClass());
        if (descInfo == null)
            return pname;
        String bestMatch = pname;
        for (int i = 0; i < descInfo.length; i += 2) {
            String name = (String) descInfo[i];
            if (name.equals(pname))
                return pname;
            if (JiglooUtils.isLike(name, pname))
                bestMatch = name;
        }
        System.err.println("GUESSING " + bestMatch + " for " + pname);
        return bestMatch;
    }

    public void setPropertyValue(Object propName, Object value) {
        setPropertyValue(propName, value, true);
    }

    public void setPropertyValue(Object propName, Object value, boolean update) {
        try {
                        
//            System.err.println("SET PROP BPS " + propName + ", " + value + ", " + value.getClass());

            String pname = (String) propName;

            //another netbeans fix
            InsetsPropertySource ips = (InsetsPropertySource) properties
                    .get("borderInsets");
            if (pname.equals("top") || pname.equals("bottom")
                    || pname.equals("left") || pname.equals("right")) {
                Insets insets1 = null;
                if (ips != null)
                    insets1 = ips.getValue();
                if (insets1 == null)
                    insets1 = new Insets(0, 0, 0, 0);
                if (pname.equals("top"))
                    insets1.top = JiglooUtils.getIntValue(value);
                else if (pname.equals("bottom"))
                    insets1.bottom = JiglooUtils.getIntValue(value);
                else if (pname.equals("left"))
                    insets1.left = JiglooUtils.getIntValue(value);
                else if (pname.equals("right"))
                    insets1.right = JiglooUtils.getIntValue(value);
                value = insets1;
                pname = "borderInsets";
            }

            pname = guessForNetbeans(pname);

            propName = pname;

            initDefaultValue(pname);

            Object defValue = defaultProps.get(pname);

            if (defValue != null) {
                Class propType = defValue.getClass();
                value = FormPropertySource.convertIfNeeded(pname, value,
                        propType, comp);
            }

            if("TYPE".equals(propName)) {
                Border tmpBorder = createBorder(value);
                if (tmpBorder != null && border != null
                        && tmpBorder.getClass().equals(border.getClass()))
                    return;
                setHasChanged(true);
                border = tmpBorder;
                descriptors = null;
            } else {
                Class cls = getPropClass(pname);
                if (cls == null) {
                    System.err.println(pname + " not found in "
                            + border.getClass() + " props");
                    return;
                }
                setHasChanged(true);
                if (!setProps.contains(pname)) {
                    setProps.add(pname);
                }

                value = convertToPropSource(value, pname);

                if (value instanceof BorderPropertySource) {
                    ((BorderPropertySource) value).setParent(this);
                }

                if ("null".equals(value) && !cls.equals(String.class))
                    value = null;

                properties.put(propName, value);

                if (value instanceof BorderPropertySource) {
                    value = ((BorderPropertySource) value).getBorder();
                }
                if (value instanceof ColorWrapper) {
                    value = ((ColorWrapper) value).getColor();
                }
                if (value instanceof FontWrapper) {
                    value = ((FontWrapper) value).getFont(null);
                }
                if (value instanceof FieldWrapper) {
                    value = ((FieldWrapper) value).getValue();
                }
                if (value instanceof ImageWrapper) {
                    value = ((ImageWrapper) value).getImage(null);
                }
                if (value instanceof IconWrapper) {
                    value = ((IconWrapper) value).getIcon();
                }
                if (value instanceof InsetsPropertySource) {
                    value = ((InsetsPropertySource) value).getValue();
                }

                if (border instanceof CompoundBorder) {
                    CompoundBorder cb = (CompoundBorder) border;
                    Border ib = cb.getInsideBorder();
                    Border ob = cb.getOutsideBorder();
                    if (propName.equals("insideBorder"))
                        ib = (Border) value;
                    if (propName.equals("outsideBorder"))
                        ob = (Border) value;
                    border = new CompoundBorder(ob, ib);
                } else if (border instanceof MatteBorder) {
                    MatteBorder eb = (MatteBorder) border;
                    Insets insets = eb.getBorderInsets();
                    Color color = eb.getMatteColor();
                    Icon icon = eb.getTileIcon();
                    if (propName.equals("matteColor"))
                        color = (Color) value;
                    if (propName.equals("tileIcon"))
                        icon = new ImageIcon((Image) value);
                    if (propName.equals("borderInsets"))
                        insets = (Insets) value;
                    if (color != null)
                        border = new MatteBorder(insets, color);
                    else
                        border = new MatteBorder(insets, icon);
                } else if (border instanceof SoftBevelBorder) {
                    SoftBevelBorder bb = (SoftBevelBorder) border;
                    int type = bb.getBevelType();
                    Color hic = bb.getHighlightInnerColor();
                    Color hoc = bb.getHighlightOuterColor();
                    Color sic = bb.getShadowInnerColor();
                    Color soc = bb.getShadowOuterColor();
                    if (propName.equals("bevelType"))
                        type = JiglooUtils.getIntValue(value);
                    if (propName.equals("highlightInnerColor"))
                        hic = (Color) value;
                    if (propName.equals("highlightOuterColor"))
                        hoc = (Color) value;
                    if (propName.equals("shadowInnerColor"))
                        sic = (Color) value;
                    if (propName.equals("shadowOuterColor"))
                        soc = (Color) value;
                    border = new SoftBevelBorder(type, hoc, hic, soc, sic);
                } else if (border instanceof BevelBorder) {
                    BevelBorder bb = (BevelBorder) border;
                    int type = bb.getBevelType();
                    Color hic = bb.getHighlightInnerColor();
                    Color hoc = bb.getHighlightOuterColor();
                    Color sic = bb.getShadowInnerColor();
                    Color soc = bb.getShadowOuterColor();
                    if (propName.equals("bevelType"))
                        type = JiglooUtils.getIntValue(value);
                    if (propName.equals("highlightInnerColor"))
                        hic = (Color) value;
                    if (propName.equals("highlightOuterColor"))
                        hoc = (Color) value;
                    if (propName.equals("shadowInnerColor"))
                        sic = (Color) value;
                    if (propName.equals("shadowOuterColor"))
                        soc = (Color) value;
                    border = new BevelBorder(type, hoc, hic, soc, sic);
                } else if (border instanceof EtchedBorder) {
                    EtchedBorder bb = (EtchedBorder) border;
                    int type = bb.getEtchType();
                    Color hc = bb.getHighlightColor();
                    Color sc = bb.getShadowColor();
                    if (propName.equals("etchType"))
                        type = JiglooUtils.getIntValue(value);
                    if (propName.equals("highlightColor"))
                        hc = (Color) value;
                    if (propName.equals("shadowColor"))
                        sc = (Color) value;
                    border = new EtchedBorder(type, hc, sc);
                } else if (border instanceof LineBorder) {
                    LineBorder eb = (LineBorder) border;
                    boolean round = eb.getRoundedCorners();
                    Color col = eb.getLineColor();
                    int thickness = eb.getThickness();
                    if (propName.equals("thickness"))
                        thickness = JiglooUtils.getIntValue(value);
                    if (propName.equals("roundedCorners"))
                        round = JiglooUtils.getBoolValue(value);
                    if (propName.equals("lineColor"))
                        col = (Color) value;
                    border = new LineBorder(col, thickness, round);
                } else if (border instanceof EmptyBorder) {
                    EmptyBorder eb = (EmptyBorder) border;
                    Insets insets = eb.getBorderInsets();
                    if (propName.equals("borderInsets"))
                        insets = (Insets) value;
                    border = new EmptyBorder(insets);
                } else if (border instanceof TitledBorder) {
                    TitledBorder eb = (TitledBorder) border;
                    Border bor = eb.getBorder();
                    String title = eb.getTitle();
                    Color col = eb.getTitleColor();
                    Font font = eb.getTitleFont();
                    int pos = eb.getTitlePosition();
                    int jus = eb.getTitleJustification();
                    if (propName.equals("title"))
                        title = (String) value;
                    if (propName.equals("titleJustification"))
                        jus = JiglooUtils.getIntValue(value);
                    if (propName.equals("titlePosition"))
                        pos = JiglooUtils.getIntValue(value);
                    //if (propName.equals("border"))
                    //bor = (Border) value;
                    if (propName.equals("titleColor"))
                        col = (Color) value;
                    if (propName.equals("titleFont"))
                        font = (Font) value;
                    border = new TitledBorder(bor, title, jus, pos, font, col);
                } else {
                    setProperty(pname, value, getPropClass(pname));
                }
            }
            if (parent != null) {
                //System.err.println("setting parent prop " + thisPropertyName
                // + ", " + border + ", " + parent);
                if (parent instanceof FormComponent) {
                    ((FormComponent) parent).setPropertyValue(thisPropertyName,
                            this);
                    //,true);
                } else {
                    parent.setPropertyValue(thisPropertyName, this);
                }
                //((JComponent) comp.getComponent()).setBorder(border);
            }
            //comp.updateUI();
            //comp.getEditor().redrawRoot();
            //comp.getEditor().setDirty(true);
            if (update)
                comp.getEditor().setDirtyAndUpdate(false);
            //firePropertyChanged((String) propName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static Border createBorder(Object type) {
        try {
            if (type == null || type.equals("null"))
                return null;
            //		if (type instanceof String) {
            //			try {
            //				type = Class.forName("javax.swing.border." + type);
            //			} catch (Exception e) {
            //				e.printStackTrace();
            //			}
            //		}
            if (type instanceof BorderPropertySource) {
                throw new RuntimeException(
                        "Shouldn't pass a BorderPropertySource to createBorder "
                                + type);
                //BorderPropertySource bps = (BorderPropertySource) type;
                //return bps.getValue();
            }
            if (type.equals(BevelBorder.class)) {
                return BorderFactory.createBevelBorder(BevelBorder.LOWERED);
            } else if (type.equals(EmptyBorder.class)) {
                return BorderFactory.createEmptyBorder();
            } else if (type.equals(CompoundBorder.class)) {
                return BorderFactory.createCompoundBorder();
            } else if (type.equals(EtchedBorder.class)) {
                return BorderFactory.createEtchedBorder();
            } else if (type.equals(EmptyBorder.class)) {
                return BorderFactory.createEmptyBorder();
            } else if (type.equals(LineBorder.class)) {
                return BorderFactory.createLineBorder(Color.black);
            } else if (type.equals(MatteBorder.class)) {
                return BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
            } else if (type.equals(SoftBevelBorder.class)) {
                return new SoftBevelBorder(SoftBevelBorder.LOWERED);
            } else if (type.equals(TitledBorder.class)) {
                return new TitledBorder("");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean equals(Object o) {
        //if (true)
        //return false;

        if (properties == null)
            return false;
        if (o instanceof BorderPropertySource) {
            BorderPropertySource bps = (BorderPropertySource) o;
            Iterator it = properties.keySet().iterator();
            while (it.hasNext()) {
                Object id = it.next();
                Object p = bps.getPropertyValue(id);
                if (p == null && getPropertyValue(id) == null)
                    continue;
                if (p != null && p.equals(getPropertyValue(id)))
                    continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        if (border == null)
            return "No Border";
        return JiglooUtils.getShortClassName(border.getClass());
    }

    public boolean hasChanged() {
        return hasChanged;
    }
    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
}