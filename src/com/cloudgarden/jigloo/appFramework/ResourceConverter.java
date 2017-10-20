/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

package com.cloudgarden.jigloo.appFramework;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A base class for converting arbitrary types to and from Strings, as well as a
 * registry of ResourceConverter implementations.
 * <p>
 * The <tt>supportsType</tt> method defines what types a ResourceConverter
 * supports. By default it returns true for classes that are equal to the
 * constructor's <tt>type</tt> argument. The <tt>parseType</tt> methods
 * converts a string the ResourceConverter's supported type, and the
 * <tt>toString</tt> does the inverse, it converts a supported type to a
 * String. Concrete ResourceConverter subclasses must override
 * <tt>parseType()</tt> and, in most cases, the <tt>toString</tt> method as
 * well.
 * <p>
 * This class maintains a registry of ResourceConverters. The <tt>forType</tt>
 * method returns the first ResourceConverter that supports a particular type,
 * new ResourceConverters can be added with <tt>register()</tt>. A small set
 * of generic ResourceConverters are registered by default. They support the
 * following types:
 * <ul>
 * <li><tt>Boolean</tt></li>
 * <li><tt>Integer</tt></li>
 * <li><tt>Float</tt></li>
 * <li><tt>Double</tt></li>
 * <li><tt>Long</tt></li>
 * <li><tt>Short</tt></li>
 * <li><tt>Byte</tt></li>
 * <li><tt>MessageFormat</tt></li>
 * </ul>
 * <p>
 * The Boolean ResourceConverter returns true for "true", "on", "yes", false
 * otherwise. The other primitive type ResourceConverters rely on the
 * corresponding static parse <i>Type </i> method, e.g.
 * <tt>Integer.parseInt()</tt>. The MessageFormat ResourceConverter just
 * creates MessageFormat object with the string as its constructor argument.
 * 
 * @author Hans Muller (Hans.Muller@Sun.COM)
 * @see ResourceMap
 */
public abstract class ResourceConverter {

	protected final Class type;

	public abstract Object parseString(String s)
			throws ResourceConverterException;

	public String toString(Object obj) {
		return (obj == null) ? "null" : obj.toString();
	}

	protected ResourceConverter(Class type) {
		if (type == null) {
			throw new IllegalArgumentException("null type");
		}
		this.type = type;
	}

	private ResourceConverter() {
		type = null;
	} // not used

	public boolean supportsType(Class testType) {
		return type.equals(testType);
	}

	public static class ResourceConverterException extends Exception {
		private final String badString;

		private String maybeShorten(String s) {
			int n = s.length();
			return (n < 128) ? s : s.substring(0, 128) + "...[" + (n - 128)
					+ " more characters]";
		}

		public ResourceConverterException(String message, String badString,
				Throwable cause) {
			super(message, cause);
			this.badString = maybeShorten(badString);
		}

		public ResourceConverterException(String message, String badString) {
			super(message);
			this.badString = maybeShorten(badString);
		}

		public String toString() {
			StringBuffer sb = new StringBuffer(super.toString());
			sb.append(" string: \"");
			sb.append(badString);
			sb.append("\"");
			return sb.toString();
		}
	}

	public static void register(ResourceConverter resourceConverter) {
		if (resourceConverter == null) {
			throw new IllegalArgumentException("null resourceConverter");
		}
		resourceConverters.add(resourceConverter);
	}

	public static ResourceConverter forType(Class type) {
		if (type == null) {
			throw new IllegalArgumentException("null type");
		}
		for (int i = 0; i < resourceConverters.size(); i++) {
			ResourceConverter sc = (ResourceConverter) resourceConverters
					.get(i);
			if (sc.supportsType(type)) {
				return sc;
			}
		}
		return null;
	}

	private static ResourceConverter[] resourceConvertersArray = {
			new BooleanResourceConverter(new String[] { "true", "on", "yes" }),
			new IntegerResourceConverter(),
			new MessageFormatResourceConverter(), new FloatResourceConverter(),
			new DoubleResourceConverter(), new LongResourceConverter(),
			new ShortResourceConverter(), new ByteResourceConverter() };

	private static List resourceConverters = new ArrayList(Arrays
			.asList(resourceConvertersArray));

	private static class BooleanResourceConverter extends ResourceConverter {
		private final String[] trueStrings;

		BooleanResourceConverter(String[] trueStrings) {
			super(Boolean.class);
			this.trueStrings = trueStrings;
		}

		public Object parseString(String s) {
			s = s.trim();
			for (int i = 0; i < trueStrings.length; i++) {
				if (s.equalsIgnoreCase(trueStrings[i])) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}

		public boolean supportsType(Class testType) {
			return testType.equals(Boolean.class)
					|| testType.equals(boolean.class);
		}
	}

	private static abstract class NumberResourceConverter extends
			ResourceConverter {
		private final Class primitiveType;

		NumberResourceConverter(Class type, Class primitiveType) {
			super(type);
			this.primitiveType = primitiveType;
		}

//		protected abstract Number parseString(String s)
//				throws NumberFormatException;

		public Object parseString(String s)
				throws ResourceConverterException {
			try {
				return parseString(s);
			} catch (NumberFormatException e) {
				throw new ResourceConverterException("invalid "
						+ type.getName(), s, e);
			}
		}

		public boolean supportsType(Class testType) {
			return testType.equals(type) || testType.equals(primitiveType);
		}
	}

	private static class FloatResourceConverter extends NumberResourceConverter {
		FloatResourceConverter() {
			super(Float.class, float.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Float(s);
		}
	}

	private static class DoubleResourceConverter extends
			NumberResourceConverter {
		DoubleResourceConverter() {
			super(Double.class, double.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Double(s);
		}
	}

	private static class ByteResourceConverter extends NumberResourceConverter {
		ByteResourceConverter() {
			super(Byte.class, byte.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Byte(s);
		}
	}

	private static class IntegerResourceConverter extends
			NumberResourceConverter {
		IntegerResourceConverter() {
			super(Integer.class, int.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Integer(s);
		}
	}

	private static class LongResourceConverter extends NumberResourceConverter {
		LongResourceConverter() {
			super(Long.class, long.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Long(s);
		}
	}

	private static class ShortResourceConverter extends NumberResourceConverter {
		ShortResourceConverter() {
			super(Short.class, short.class);
		}

		public Object parseString(String s) throws NumberFormatException {
			return new Short(s);
		}
	}

	private static class MessageFormatResourceConverter extends
			ResourceConverter {
		MessageFormatResourceConverter() {
			super(MessageFormat.class);
		}

		public Object parseString(String s) {
			return new MessageFormat(s);
		}
	}
}