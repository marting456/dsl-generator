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
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
			return "RandomUtils.nextInt(0, 10)";
		}
		if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return "(short) RandomUtils.nextInt(0, 10)";
		}
		if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return "RandomUtils.nextLong(0, 10)";
		}
		if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return "RandomUtils.nextDouble(0.0, 10.0)";
		}
		if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return "RandomUtils.nextFloat(0.0f, 10.0f)";
		}
		if (field.getType().equals(String.class)) {
			return "RandomStringUtils.randomAlphabetic(10)";
		}
		if (field.getType().equals(Date.class)) {
			return "new Date(RandomUtils.nextLong(0 ,1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30))";
		}
		return "null";
	}
}