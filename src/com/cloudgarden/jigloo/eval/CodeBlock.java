/*
 * Created on Jun 7, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTNode;

import com.cloudgarden.jigloo.FormComponent;
import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CodeBlock {

    private HashMap nodes;

    private boolean isMethod;

    private int offset;

    private FormComponent comp;

    private String code, methodName;

    public CodeBlock(FormComponent comp, HashMap nodes, String code,
            int offset, String methodName, boolean isMethod) {
        this.comp = comp;
        this.nodes = nodes;
        this.code = code;
        this.offset = offset;
        this.isMethod = isMethod;
        this.methodName = methodName;
    }

    public String getCode() {
        return code;
    }

    public String getMethodName() {
        return methodName;
    }

    public FormComponent getComp() {
        return comp;
    }

    public HashMap getNodes() {
        return nodes;
    }

    public void removeSurroundingMethodOld(JavaCodeParser jcp) {
        if (!isMethod)
            return;
        String NL = jcp.getNewLine();
        int start = 10000;
        int end = 0;
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object val = nodes.get(key);
            int st, en;
            if (val instanceof ASTNode) {
                ASTNode node = (ASTNode) val;
                st = node.getStartPosition();
                en = st + node.getLength();
            } else {
                Comment node = (Comment) val;
                st = node.startPosition;
                en = st + node.length;
            }
            if (st < start)
                start = st;
            if (en > end)
                end = en;
        }
        if (code.charAt(end) == ';')
            end++;
        end++;
        if (jiglooPlugin.useBraces()) {
            start -= 1 + NL.length();
        }
        it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object val = nodes.get(key);
            if (val instanceof ASTNode) {
                ASTNode node = (ASTNode) val;
                node.setSourceRange(node.getStartPosition() - start, node
                        .getLength());
            } else {
                Comment node = (Comment) val;
                node.setSourceRange(node.startPosition - start, node.length);
            }
        }
        if (jiglooPlugin.useBraces()) {
            start += 1 + NL.length();
        }
        code = code.substring(offset + start, offset + end);
        if (jiglooPlugin.useBraces()) {
            code = "{" + NL + code + NL + "}";
        }
    }

    public void removeSurroundingMethod(JavaCodeParser jcp) {
        String NL = jcp.getNewLine();
        //if (!isMethod)
        //return;
        String nic = comp.getNameInCode();
        String bdStart = jcp.getBlockDelimiterStart(nic);
        boolean usesBlock = jiglooPlugin.useBraces()
                || jiglooPlugin.useTaggedComments();

        int start = 10000;
        int end = 0;
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object val = nodes.get(key);
            int st, en;
            if (val instanceof ASTNode) {
                ASTNode node = (ASTNode) val;
                st = node.getStartPosition();
                en = st + node.getLength();
            } else {
                Comment node = (Comment) val;
                st = node.startPosition;
                en = st + node.length;
            }
            if (st < start)
                start = st;
            if (en > end)
                end = en;
        }

        if (!isMethod) {
            //the block markers always end with a NL, so
            //go just after the first NL (so we don't erase more
            //than one block marker.
            int secLine = code.indexOf(NL);
            if (secLine > 0 && secLine + NL.length() < start) {
                start = secLine + NL.length();
            }
        }

        end += offset;
        if (end < code.length() - 1 && code.charAt(end) == ';')
            end++;
        //        if (end < code.length() - 1)
        //            end++;

        if (usesBlock) {
            start -= bdStart.length();
        } else {
            start -= NL.length();
        }

        it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object val = nodes.get(key);
            if (val instanceof ASTNode) {
                ASTNode node = (ASTNode) val;
                node.setSourceRange(node.getStartPosition() - start, node
                        .getLength());
            } else {
                Comment node = (Comment) val;
                node.setSourceRange(node.startPosition - start, node.length);
            }
        }

        if (usesBlock) {
            start += bdStart.length();
        } else {
            start += NL.length();
        }

        code = code.substring(offset + start, end);
        if (!code.endsWith(NL)) {
            code += NL;
        }
        if (usesBlock) {
            code = bdStart + code + jcp.getBlockDelimiterEnd(nic) + NL;
        } else {
            code = NL + code + NL;
        }
        isMethod = false;

    }

    public void addSurroundingMethod(JavaCodeParser jcp) {
        if (isMethod)
            return;

        String NL = jcp.getNewLine();
        
        int mod = comp.getModifier();
        String acc = "protected ";
        if ((mod & FormComponent.MOD_PRIVATE) != 0)
            acc = "private ";
        if ((mod & FormComponent.MOD_PUBLIC) != 0)
            acc = "public ";
        String start1 = acc + comp.getClassNameForCode() + " get"
                + JiglooUtils.capitalize(comp.getNameInCode()) + "() ";
        String start2 = "{" + NL + "if (" + comp.getNameInCode() + " == null) ";
        int sl = start2.length();
        
        String nic = comp.getNameInCode();
        String bdStart = JavaCodeParser.START_BLOCK+" "+nic;
        String bdEnd = JavaCodeParser.END_BLOCK+" "+nic;
        if(code.startsWith(bdStart)) {
            code = code.substring(bdStart.length(), code.length()-bdEnd.length());
            sl -= bdStart.length();
            code ="{"+code+"}";
            sl++;
        }
        
        code = start1 + start2 + code + NL + "return " + comp.getNameInCode()
                + ";" + NL + "}";
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object val = nodes.get(key);
            if (val instanceof ASTNode) {
                ASTNode node = (ASTNode) val;
                node.setSourceRange(sl + node.getStartPosition(), node
                        .getLength());
            } else {
                Comment node = (Comment) val;
                node.setSourceRange(sl + node.startPosition, node.length);
            }
        }
        isMethod = true;
    }

}