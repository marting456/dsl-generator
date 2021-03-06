package org.marting.dslgenerator.field;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 *
 * Represents a field that has generic parameters such as List<String>
 */
public class DslFieldComplex extends DslField {

	protected Class<?> typeParameter;
	protected String typeParameterGenerator;
	protected Class<?> implementingClazz;

	public Class<?> getImplementingClazz() {
		return implementingClazz;
	}

	public void setImplementingClazz(Class<?> implementingClazz) {
		this.implementingClazz = implementingClazz;
	}

	public String getTypeParameterGenerator() {
		return typeParameterGenerator;
	}

	public void setTypeParameterGenerator(String typeParameterGenerator) {
		this.typeParameterGenerator = typeParameterGenerator;
	}

	DslFieldComplex(Field field) {
		this.field = field;
	}

	@Override
	public Class<?> getTypeParameter() {
		return typeParameter;
	}

	@Override
	public String getDeclaredType() {
		Type type = field.getGenericType();
		ParameterizedType pType = (ParameterizedType) type;
		String pTypeStr = pType.getActualTypeArguments()[0].getTypeName();
		pTypeStr = pTypeStr.substring(pTypeStr.lastIndexOf(".") + 1);
		return field.getType().getSimpleName() + "<" + pTypeStr + ">";

	}

	public void setTypeParameter(Class<?> typeParameter) {
		this.typeParameter = typeParameter;
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
