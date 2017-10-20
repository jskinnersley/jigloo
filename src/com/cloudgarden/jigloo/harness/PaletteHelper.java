package com.cloudgarden.jigloo.harness;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.cloudgarden.jigloo.classloader.JarFileClassLoader;
import com.cloudgarden.jigloo.preferences.PaletteComposite;

public class PaletteHelper {

	public static String getPaletteCategories() {
		return  "Layouts|Containers|Widgets|Custom";
	}

	public static String getPaletteString() {

		String str = "Custom|3|||Add custom class or layout...|n|"+PaletteComposite.PREF_SEP_2;

		for (int i = 0; i < containers.length; i++) {
            str += "Containers|3|"+containers[i]+"|0||n|"+PaletteComposite.PREF_SEP_2;
		}

		for (int i = 0; i < layouts.length; i++) {
            str += "Layouts|3|"+layouts[i]+"|0||n|"+PaletteComposite.PREF_SEP_2;
		}

		for (int i = 0; i < widgets.length; i++) {
            str += "Widgets|3|"+widgets[i]+"|0||n|"+PaletteComposite.PREF_SEP_2;
		}
        return str;
	}


	public final static String[] layouts = {
		  "android.widget.AbsoluteLayout",
		  "android.widget.FrameLayout",
		  "android.widget.LinearLayout",
		  "android.widget.RelativeLayout",
		  "android.widget.TableLayout"
		};
		public final static String[] containers = {
		  "android.widget.DatePicker",
		  "android.widget.DialerFilter",
		  "android.widget.ExpandableListView",
		  "android.widget.Gallery",
		  "android.widget.GridView",
		  "android.widget.HorizontalScrollView",
		  "android.widget.ImageSwitcher",
		  "android.widget.ListView",
		  "android.widget.MediaController",
		  "android.widget.RadioGroup",
		  "android.widget.ScrollView",
		  "android.widget.SlidingDrawer",
		  "android.widget.Spinner",
		  "android.widget.TabHost",
		  "android.widget.TabWidget",
		  "android.widget.TableRow",
		  "android.widget.TextSwitcher",
		  "android.widget.TimePicker",
		  "android.widget.TwoLineListItem",
		  "android.widget.ViewAnimator",
		  "android.widget.ViewFlipper",
		  "android.widget.ViewSwitcher",
		  "android.widget.ZoomControls"
		};
		public final static String[] widgets = {
		  "android.view.SurfaceView",
		  "android.view.View",
		  "android.view.ViewStub",
		  "android.widget.AnalogClock",
		  "android.widget.AutoCompleteTextView",
		  "android.widget.Button",
		  "android.widget.CheckBox",
		  "android.widget.CheckedTextView",
		  "android.widget.Chronometer",
		  "android.widget.DigitalClock",
		  "android.widget.EditText",
		  "android.widget.ImageButton",
		  "android.widget.ImageView",
		  "android.widget.MultiAutoCompleteTextView",
		  "android.widget.ProgressBar",
		  "android.widget.QuickContactBadge",
		  "android.widget.RadioButton",
		  "android.widget.RatingBar",
		  "android.widget.SeekBar",
		  "android.widget.TextView",
		  "android.widget.ToggleButton",
		  "android.widget.VideoView",
		  "android.widget.ZoomButton"
		};

	public static void main(String[] args) {
		Vector containers = new Vector();
		Vector layouts = new Vector();
		Vector widgets = new Vector();
		try {
			JarFile android = new JarFile(new File("C:/android-sdk-r07/platforms/android-8/android.jar"));
			ClassLoader loader = new JarFileClassLoader(new File("C:/android-sdk-r07/platforms/android-8/android.jar").toURL(), null);
			Class viewCls = loader.loadClass("android.view.View");
			Class viewGrpCls = loader.loadClass("android.view.ViewGroup");
			Enumeration list = android.entries();
			while(list.hasMoreElements()) {
				JarEntry next = (JarEntry) list.nextElement();
//				System.out.println(next);
				if(next.toString().endsWith(".class")) {
					String className = next.toString();
					className = className.replace(".class", "").replace("/", ".");
					try {
						Class cls = loader.loadClass(className);
						int mods = cls.getModifiers();
						if(Modifier.isAbstract(mods) 
								|| Modifier.isPrivate(mods)
								|| !(className.startsWith("android.widget") || className.startsWith("android.view")) )
							continue;
						if(viewGrpCls.isAssignableFrom(cls)) {
							if(className.endsWith("Layout"))
								layouts.add(className);
							else
								containers.add(className);
						} else if(viewCls.isAssignableFrom(cls)) {
							widgets.add(className);
						}
					} catch (Throwable e) {
						System.err.println(e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(layouts);
		Collections.sort(containers);
		Collections.sort(widgets);
		
		System.out.println("public final static String[] layouts = {");
		for (int i = 0; i < layouts.size(); i++) {
			if(i != layouts.size()-1) {
				System.out.println("  \""+layouts.get(i)+"\",");
			} else {
				System.out.println("  \""+layouts.get(i)+"\"");
			}
		}
		System.out.println("};");

		System.out.println("public final static String[] containers = {");
		for (int i = 0; i < containers.size(); i++) {
			if(i != containers.size()-1) {
				System.out.println("  \""+containers.get(i)+"\",");
			} else {
				System.out.println("  \""+containers.get(i)+"\"");
			}
		}
		System.out.println("};");

		System.out.println("public final static String[] widgets = {");
		for (int i = 0; i < widgets.size(); i++) {
			if(i != widgets.size()-1) {
				System.out.println("  \""+widgets.get(i)+"\",");
			} else {
				System.out.println("  \""+widgets.get(i)+"\"");
			}
		}
		System.out.println("};");

	}
}
