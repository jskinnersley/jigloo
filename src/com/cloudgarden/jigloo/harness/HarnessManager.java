package com.cloudgarden.jigloo.harness;

import java.util.HashMap;
import java.util.Iterator;

import com.cloudgarden.jigloo.editors.FormEditor;

public class HarnessManager {

	public static final boolean ENABLE_ANDROID = false;
	
	private static HashMap harnesses = new HashMap();
	
//	static {
//		registerHarness(Activity.class, AndroidHarness.class);
//		registerHarness(View.class, AndroidHarness.class);
//	}
	
	public static void registerHarness(Class cls, Class harnessClass) {
		harnesses.put(cls, harnessClass);
	}
	
	public static IHarness getHarnessForClass(Class cls, FormEditor editor) {
		Iterator it = harnesses.keySet().iterator();
		while(it.hasNext()) {
			Class testClass = (Class)it.next();
			if(testClass.isAssignableFrom(cls)) {
				Class harnessClass = (Class) harnesses.get(testClass);
				try {
					IHarness harness = (IHarness) harnessClass.newInstance();
					harness.setEditor(editor);
					return harness;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
