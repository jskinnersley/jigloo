/*
 * Created on Feb 2, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cloudgarden.jigloo.eval;

import java.lang.reflect.Constructor;
import java.util.Vector;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.eval.EvaluationManager;
import org.eclipse.jdt.debug.eval.IAstEvaluationEngine;
import org.eclipse.jdt.debug.eval.ICompiledExpression;
import org.eclipse.jdt.debug.eval.IEvaluationListener;

import com.cloudgarden.jigloo.properties.sources.PropertySourceFactory;

/**
 * @author jonathan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EvaluatorTester {

	public static void getRoot(
		IJavaProject project,
		IJavaDebugTarget target,
		String code) {
		IJavaObject object = null;
		IEvaluationListener listener = null;
		IJavaThread thread = null;
		IAstEvaluationEngine eng =
			EvaluationManager.newAstEvaluationEngine(project, target);
		ICompiledExpression exp;
		try {
			exp = eng.getCompiledExpression(code, object);
			eng.evaluateExpression(exp, object, thread, listener, 0, false);
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//analyse(MIASpin.class);
	}

	public static void analyse(Class clazz) {
		Constructor[] cons = clazz.getConstructors();
		try {
			Object o = cons[0].newInstance(null);
			Vector propNames = PropertySourceFactory.findPropertyNames(clazz);
			for (int i = 0; i < propNames.size(); i++) {
				String prop = (String) propNames.elementAt(i);
				System.out.println("Got prop " + prop);
				Object[] pnt = PropertySourceFactory.getPropertyAndType(o, prop);
				System.out.println("GOT VALUE "+pnt[0]+", "+pnt[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public EvaluatorTester() {}

}
