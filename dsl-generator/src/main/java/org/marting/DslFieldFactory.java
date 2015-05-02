/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author martin
 *
 */
public class DslFieldFactory {

	public static DslField buildDslField(Field field) throws UnsupportedType, ClassNotFoundException {
		Type type = field.getGenericType();
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        if (pType.getActualTypeArguments().length > 1) {
	        	throw new UnsupportedType(field);
	        }
		    DslFieldComplex dslField = new DslFieldComplex(field);
			dslField.setGeneratorValue(RandomValueGenerator.getGeneratorValue(field));
	        dslField.setTypeParameter(Class.forName(pType.getActualTypeArguments()[0].getTypeName()));
			return dslField;
	    } else {
		    DslField dslField = new DslFieldSimple(field);
			dslField.setGeneratorValue(RandomValueGenerator.getGeneratorValue(field));
			return dslField;
	    }
	}
}
