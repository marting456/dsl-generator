package org.marting.dslgenerator.field;

import java.lang.reflect.Constructor;
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

import org.marting.dslgenerator.RandomValueGenerator;
import org.marting.dslgenerator.exception.UnsupportedTypeException;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class DslFieldFactory {

	private static Map<Class<?>, Class<?>> implementationMap;
	private RandomValueGenerator rvg;

	static {
		implementationMap = new HashMap<Class<?>, Class<?>>();
		implementationMap.put(List.class, ArrayList.class);
		implementationMap.put(Set.class, HashSet.class);
		implementationMap.put(Collection.class, HashSet.class);
	}

	public DslFieldFactory(RandomValueGenerator rvg) {
		this.rvg = rvg;
	}

	public DslField buildDslField(Field field) throws UnsupportedTypeException, ClassNotFoundException {
		Type type = field.getGenericType();
		// Complex type
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) type;
	        if (pType.getActualTypeArguments().length > 1) {
	        	throw new UnsupportedTypeException(field);
	        }
		    DslFieldComplex dslField = new DslFieldComplex(field);
			dslField.setGeneratorValue(rvg.getGeneratorValue(field));
	        dslField.setTypeParameter(Class.forName(pType.getActualTypeArguments()[0].getTypeName()));
	        dslField.setTypeParameterGenerator(rvg.getGeneratorValue(dslField.getTypeParameter()));
	        dslField.setImplementingClazz(implementationMap.get(dslField.getField().getType()));
			if (dslField.getTypeParameterGenerator().equals("null")) {
				dslField.setTypeParameterGenerator(getNoArgsConstructorGenerator(dslField.getTypeParameter()));
			}
			return dslField;
		// Array
	    } else if (field.getType().isArray()) {
		    DslFieldArray dslField = new DslFieldArray(field);
			dslField.setGeneratorValue(rvg.getGeneratorValue(field));
			dslField.setComponentGeneratorValue(rvg.getGeneratorValue(dslField.getComponentType()));
			if (dslField.getComponentGeneratorValue().equals("null")) {
				dslField.setComponentGeneratorValue(getNoArgsConstructorGenerator(dslField.getComponentType()));
			}
			return dslField;
		// Simple
	    } else {
		    DslField dslField = new DslFieldSimple(field);
			dslField.setGeneratorValue(rvg.getGeneratorValue(field));
			// if null try to set a generator with the default constructor
			if (dslField.getGeneratorValue().equals("null")) {
				dslField.setGeneratorValue(getNoArgsConstructorGenerator(field.getType()));
			}
			return dslField;
	    }
	}

	private String getNoArgsConstructorGenerator(Class<?> clazz) {
		for (Constructor<?> constructor : clazz.getConstructors()) {
			if (constructor.getParameterCount() == 0) {
				return "new " + clazz.getSimpleName() + "()";
			}
		}
		return "null";
	}
}
