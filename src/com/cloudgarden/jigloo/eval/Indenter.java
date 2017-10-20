/*
 * Created on Aug 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;

import org.eclipse.jdt.internal.ui.text.JavaHeuristicScanner;
import org.eclipse.jdt.internal.ui.text.JavaIndenter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

/**
 * @author jonathan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Indenter {

    static String code;

    private static int getStartOfNextLine(int pos, String code, String NL) {
        while (code.indexOf(NL, pos) != pos) {
            pos++;
            if (pos == code.length())
                return -1;
        }
        return pos+NL.length();
    }

    private static void setIndent(int pos, int end, IDocument doc, String indent) {
        int start = pos;
        while (pos < doc.getLength() - 1) {
            char chr = doc.get().charAt(pos);
            if (chr == '\t' || chr == ' ')
                pos++;
            else
                break;
        }
        if(pos > end)
            return;
        try {
            doc.replace(start, pos-start, indent);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static String format(String code, String NL, int offset, int length) {
        int start = offset;
        int end = offset + length;
        IDocument doc = new Document(code);
        int origLen = doc.getLength();
        JavaIndenter indenter = new JavaIndenter(doc, new JavaHeuristicScanner(doc));
        StringBuffer indent;
        
        while (code.indexOf(NL, offset) == offset)
            offset += NL.length();

        while (offset < end && offset != -1) {
            indent = indenter.computeIndentation(offset);
            if(indent != null && isStartOfLine(offset, doc.get(), NL)) {
                setIndent(offset, end, doc, indent.toString());
            }
            int added = doc.getLength() - origLen;
            end += added;
            origLen = doc.getLength();
            offset = getStartOfNextLine(offset + NL.length(), doc.get(), NL);
        }
        String fmt = doc.get().substring(start, end);
//        System.out.println("formatted:\n"+fmt+"\ncontext:\n"+doc.get().substring(start-10, end+20));
        return fmt;
    }

    private static boolean isStartOfLine(int offset, String sb, String NL) {
        if(offset < NL.length())
            return true;
        return sb.indexOf(NL, offset - NL.length()) == offset - NL.length();
    }

}