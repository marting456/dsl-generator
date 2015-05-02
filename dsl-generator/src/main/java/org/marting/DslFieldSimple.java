/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;

/**
 * @author martin
 *
 */
public class DslFieldSimple extends DslField {

	DslFieldSimple(Field field) {
		this.field = field;
	}

	@Override
	public Class<?> getTypeParameter() {
		return null;
	}

	@Override
	public String getDeclaredType() {
	        return field.getType().getSimpleName();
	}

	@Override
	public String getGeneratorValue() {
		return generatorValue;
	}

	@Override
	public void setGeneratorValue(String generatorValue) {
		this.generatorValue = generatorValue;
	}
}
