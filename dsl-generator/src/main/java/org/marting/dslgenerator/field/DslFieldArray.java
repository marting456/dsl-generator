package org.marting.dslgenerator.field;

import java.lang.reflect.Field;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class DslFieldArray extends DslField {

	protected String componentGeneratorValue;

	DslFieldArray(Field field) {
		this.field = field;
	}

	public Class<?> getComponentType() {
		return field.getType().getComponentType();
	}

	@Override
	public Class<?> getTypeParameter() {
		return null;
	}

	@Override
	public String getDeclaredType() {
	        return field.getType().getSimpleName();
	}

	public String getComponentGeneratorValue() {
		return componentGeneratorValue;
	}

	public void setComponentGeneratorValue(String componentGeneratorValue) {
		this.componentGeneratorValue = componentGeneratorValue;
	}
}
