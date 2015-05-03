/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author martin
 *
 */
public class DslFieldFactory {

	private static Map<Class<?>, Class<?>> implementationMap;

	static {
		implementationMap = new HashMap<Class<?>, Class<?>>();
		implementationMap.put(List.class, ArrayList.class);
		implementationMap.put(Set.class, HashSet.class);
		implementationMap.put(Collection.class, HashSet.class);
	}

	public static DslField buildDslField(Field field) throws UnsupportedType, ClassNotFoundException {
		Type type = field.getGenericType();
		// Complex type
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        if (pType.getActualTypeArguments().length > 1) {
	        	throw new UnsupportedType(field);
	        }
		    DslFieldComplex dslField = new DslFieldComplex(field);
			dslField.setGeneratorValue(RandomValueGenerator.getGeneratorValue(field));
	        dslField.setTypeParameter(Class.forName(pType.getActualTypeArguments()[0].getTypeName()));
	        dslField.setTypeParameterGenerator(RandomValueGenerator.getGeneratorValue(dslField.getTypeParameter()));
	        dslField.setImplementingClazz(implementationMap.get(dslField.getField().getType()));
			return dslField;
		// Array
	    } else if (field.getType().isArray()) {
		    DslFieldArray dslField = new DslFieldArray(field);
			dslField.setGeneratorValue(RandomValueGenerator.getGeneratorValue(field));
			dslField.setComponentGeneratorValue(RandomValueGenerator.getGeneratorValue(dslField.getComponentType()));
			return dslField;
		// Simple
	    } else {
		    DslField dslField = new DslFieldSimple(field);
			dslField.setGeneratorValue(RandomValueGenerator.getGeneratorValue(field));
			return dslField;
	    }
	}
}
