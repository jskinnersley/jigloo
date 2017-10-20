/*
 * Created on Jan 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.eval;

import java.lang.reflect.Constructor;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

import com.cloudgarden.jigloo.jiglooPlugin;
import com.cloudgarden.jigloo.util.ClassUtils;
import com.cloudgarden.jigloo.util.JiglooUtils;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstructorHolder {

	private String code;
	private String anonClassCode;
    private Constructor con;
    private Object[] params;
    private Expression exp;
    
    public ConstructorHolder(Constructor con, Object[] params, String code, ClassInstanceCreation exp, String anonClassCode) {
        this.con= con;
        this.code = code;
        this.anonClassCode = anonClassCode;
        this.exp = exp;
        this.params = params;
        JiglooUtils.checkConstructor(con, params);
//        System.out.println("NEW CONSTR HOLDER "+code+", "+con);
    }
    
    public Expression getExpression() {
        return exp;
    }
    public Constructor getConstructor() {
        return con;
    }
    public Object[] getParams() {
        return params;
    }

    public Object newInstance() {
        try {
//            System.err.println(">>>CH: NEW "+con);
            return ClassUtils.newInstance(con.getDeclaringClass(), con, params);
        } catch (Throwable e) {
            jiglooPlugin.handleError(e);
            return null;
        }
    }

	public String getCode() {
		return code;
	}

	public String getAnonClassCode() {
		return anonClassCode;
	}

    /**
     * @return
     */
    public boolean isLayout() {
        return con != null && ClassUtils.isLayout(con.getDeclaringClass());
    }

}
