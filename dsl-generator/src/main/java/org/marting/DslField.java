/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;

import org.apache.commons.lang3.text.WordUtils;

/**
 * @author martin
 *
 */
public class DslField {

	Field field;
	String generatorValue;

	public String getGeneratorValue() {
		return generatorValue;
	}

	public void setGeneratorValue(String generatorValue) {
		this.generatorValue = generatorValue;
	}

	public DslField(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getWithMethod() {
		return "with" + WordUtils.capitalize(field.getName());
	}
	public String getSetterMethod() {
		return "set" + WordUtils.capitalize(field.getName());
	}
	public String getGetterMethod() {
		return "get" + WordUtils.capitalize(field.getName());
	}
}
