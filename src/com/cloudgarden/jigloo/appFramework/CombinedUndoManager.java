/*
 * Created on May 30, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.appFramework;

import java.util.Vector;

import com.cloudgarden.jigloo.editors.FormEditor;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CombinedUndoManager {

    private Vector stack = new Vector();
    private int stackPosn = -1;
    private FormEditor editor;
    private boolean isChanging = false;
    
    public CombinedUndoManager(FormEditor editor) {
        this.editor = editor;
    }
    
    public boolean isChanging() {
    	return isChanging;
    }
    
    public void addCommand(boolean codeChanged, int codeCounter, Vector propChange) {
        int cc = codeCounter;
        Object[] cmd = new Object[] {Boolean.valueOf(codeChanged+""), new Integer(cc), propChange};
        stackPosn++;
//        System.out.println("add undo command "+cc+", "+propChange+", "+codeChanged);
        if(stackPosn == stack.size())
            stack.add(cmd);
        else
            stack.set(stackPosn, cmd);
    }
    
    public boolean undo() {
        int cc = editor.getCommandCount();
        if(!editor.isPartOfAppFrameworkApplication()) {
            if(!editor.getCodeUndoManager().undoable())
                return false;
//            System.out.println("call undo command "+cc);
            isChanging = true;
            editor.getCodeUndoManager().undo();
            isChanging = false;
            return true;
        }
        if(stackPosn == -1)
            return false;
        Object[] cmd = (Object[]) stack.get(stackPosn);
        boolean codeChanged = ((Boolean)cmd[0]).booleanValue();
        int scc = ((Integer)cmd[1]).intValue();
//        System.out.println("call undo command "+cc+", "+scc+", "+codeChanged);
        Vector changes = (Vector)cmd[2];
        if(codeChanged && scc == cc) {
            isChanging = true;
            if(changes != null)
                editor.getBundleManager().undoChange(changes);
            editor.getCodeUndoManager().undo();
            stackPosn--;
        } else if(!codeChanged && scc == cc && changes != null) {
            isChanging = true;
            editor.getBundleManager().undoChange(changes);
            stackPosn--;
        } else {
            if(!editor.getCodeUndoManager().undoable()) {
                return false;
            }
            isChanging = true;
            editor.getCodeUndoManager().undo();
            stackPosn--;
        }
        isChanging = false;
        return true;
    }
    
    public boolean redo() {
        int cc = editor.getCommandCount();
        if(!editor.isPartOfAppFrameworkApplication()) {
            if(!editor.getCodeUndoManager().redoable())
                return false;
//            System.out.println("call redo command "+cc);
            isChanging = true;
            editor.getCodeUndoManager().redo();
            isChanging = false;
            return true;
        }
        if(stackPosn == stack.size() - 1)
            return false;
        Object[] cmd = (Object[]) stack.get(stackPosn+1);
        boolean codeChanged = ((Boolean)cmd[0]).booleanValue();
        int scc = ((Integer)cmd[1]).intValue();
//        System.out.println("call redo command "+cc+", "+scc+", "+codeChanged);
        Vector changes = (Vector)cmd[2];
        if(codeChanged && scc == cc+1) {
            isChanging = true;
            if(changes != null)
                editor.getBundleManager().redoChange(changes);
            editor.getCodeUndoManager().redo();
            stackPosn++;
        } else if(!codeChanged && scc == cc) {
            isChanging = true;
            editor.getBundleManager().redoChange(changes);
            stackPosn++;
        } else {
            if(!editor.getCodeUndoManager().redoable())
                return false;
            isChanging = true;
            editor.getCodeUndoManager().redo();            
            stackPosn++;
        }
        isChanging = false;
        return true;
    }
}
