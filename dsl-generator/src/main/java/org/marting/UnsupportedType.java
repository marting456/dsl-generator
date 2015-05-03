package org.marting;

import java.lang.reflect.Field;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class UnsupportedType extends Exception {
	private static final long serialVersionUID = 254104070069756615L;

	public UnsupportedType(Field field) {
		super("The field '" + field.getName() + "' has too many generic types. Only single generic type is supported.");
	}
}
