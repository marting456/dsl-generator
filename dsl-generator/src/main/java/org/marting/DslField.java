/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.text.WordUtils;

/**
 * @author martin
 *
 */
public class DslField {

	Field field;
	String generatorValue;

	public Class<?> getTypeParameter() throws ClassNotFoundException {
		Type type = field.getGenericType();
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        return Class.forName(pType.getActualTypeArguments()[0].getTypeName());
	    } else {
	        return null;
	    }
	}

	public String getType() throws UnsupportedType {
		Type type = field.getGenericType();
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        String pTypeStr = pType.getActualTypeArguments()[0].getTypeName();
	        pTypeStr = pTypeStr.substring(pTypeStr.lastIndexOf(".") + 1);
	        return field.getType().getSimpleName() + "<" + pTypeStr + ">";
	    } else {
	        return field.getType().getSimpleName();
	    }
	}

	public String getGeneratorValue() {
		return generatorValue;
	}

	public void setGeneratorValue(String generatorValue) {
		this.generatorValue = generatorValue;
	}

	public DslField(Field field) throws UnsupportedType {
		Type type = field.getGenericType();
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        if (pType.getActualTypeArguments().length > 1) {
	        	throw new UnsupportedType(field);
	        }
	    }
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
