/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author  martin
 */
public class RandomValueGenerator {

	public static String getGeneratorValue(Field field) {
		return getGeneratorValue(field.getType());
	}

	public static String getGeneratorValue(Class<?> clazz) {
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return "RandomUtils.nextInt(0, 10)";
		}
		if (clazz.equals(short.class) || clazz.equals(Short.class)) {
			return "(short) RandomUtils.nextInt(0, 10)";
		}
		if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return "RandomUtils.nextLong(0, 10)";
		}
		if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return "RandomUtils.nextDouble(0.0, 10.0)";
		}
		if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			return "RandomUtils.nextFloat(0.0f, 10.0f)";
		}
		if (clazz.equals(String.class)) {
			return "RandomStringUtils.randomAlphabetic(10)";
		}
		if (clazz.equals(Date.class)) {
			return "new Date(RandomUtils.nextLong(0 ,1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30))";
		}
		return "null";
	}
}