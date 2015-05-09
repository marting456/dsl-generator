package org.marting.dslgenerator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.marting.dslgenerator.field.Constant;
import org.marting.dslgenerator.field.DslFieldFactory;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class RandomValueGenerator {

	static final String CONSTANTS_FILE = "constants.properties";
	private Map<String, Constant> constants = new HashMap<String, Constant>();
	private Set<Constant> constantsUsed = new HashSet<Constant>();

	public RandomValueGenerator() throws IOException {
		Properties props = new Properties();
		props.load(DslFieldFactory.class.getClassLoader().getResourceAsStream(CONSTANTS_FILE));
		for (Entry<Object, Object> e : props.entrySet()) {
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			String type = key.substring(0, key.indexOf("."));
			Constant constant = null;
			if (constants.containsKey(type)) {
				constant = constants.get(type);
			} else {
				constant = new Constant(type);
				constants.put(type, constant);
			}
			if (key.endsWith("max.value")) {
				constant.setValue(value);
			} else if (key.endsWith("max.name")) {
				constant.setName(value);
			} else if (key.endsWith("max.type")) {
				constant.setType(value);
			}
		}
	}

	Map<String, Constant> getConstants() {
		return constants;
	}

	void setConstants(Map<String, Constant> constants) {
		this.constants = constants;
	}

	public String getGeneratorValue(Field field) {
		return getGeneratorValue(field.getType());
	}

	public String getGeneratorValue(Class<?> clazz) {
		Constant constant = null;
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			constant = constants.get(Integer.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "RandomUtils.nextInt(0, " + name + ")";
		}
		if (clazz.equals(short.class) || clazz.equals(Short.class)) {
			constant = constants.get(Short.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "(short) RandomUtils.nextInt(0, " + name + ")";
		}
		if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			constant = constants.get(Long.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "RandomUtils.nextLong(0, " + name + ")";
		}
		if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			constant = constants.get(Double.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "RandomUtils.nextDouble(0.0, " + name + ")";
		}
		if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			constant = constants.get(Float.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "RandomUtils.nextFloat(0.0f, " + name + ")";
		}
		if (clazz.equals(String.class)) {
			constant = constants.get(String.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "RandomStringUtils.randomAlphabetic(" + name + ")";
		}
		if (clazz.equals(Date.class)) {
			constant = constants.get(Date.class.getSimpleName());
			String name = constant.getName();
			constantsUsed.add(constant);
			return "new Date(RandomUtils.nextLong(0 ," + name + "))";
		}
		return "null";
	}

	public void resetConstantsUsed() {
		this.constantsUsed = new HashSet<Constant>();
	}

	public Set<Constant> getConstantsUsed() {
		return constantsUsed;
	}

	public void setConstantsUsed(Set<Constant> constantsUsed) {
		this.constantsUsed = constantsUsed;
	}
}