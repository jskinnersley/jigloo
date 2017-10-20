package com.cloudgarden.jigloo.eval;

import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.ThisExpression;

public class EvaluationUtils {

	static Expression removeCast(Expression exp) {
	    boolean ok = false;
	    while (!ok) {
	        if (exp instanceof CastExpression) {
	            exp = ((CastExpression) exp).getExpression();
	        } else if (exp instanceof ParenthesizedExpression) {
	            exp = ((ParenthesizedExpression) exp).getExpression();
	        } else if (exp instanceof FieldAccess) {
	            FieldAccess fa = (FieldAccess) exp;
	            if (fa.getExpression() instanceof ThisExpression) {
	                exp = fa.getName();
	            } else {
	                ok = true;
	            }
	        } else if (exp instanceof SuperFieldAccess) {
	            Name qual = ((SuperFieldAccess) exp).getQualifier();
	            if (qual != null) {
	                exp = qual;
	            } else {
	                ok = true;
	            }
	        } else
	            ok = true;
	    }
	    return exp;
	}

	static String tidyNumberString(Object val) {
	    String str = val.toString();
	    if (str.endsWith("f") || str.endsWith("d") || str.endsWith("L"))
	        return str.substring(0, str.length() - 1);
	    return str;
	}

}
