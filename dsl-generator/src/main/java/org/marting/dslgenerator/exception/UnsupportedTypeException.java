package org.marting.dslgenerator.exception;

import java.lang.reflect.Field;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class UnsupportedTypeException extends Exception {
	private static final long serialVersionUID = 254104070069756615L;

	public UnsupportedTypeException(Field field) {
		super("The field '" + field.getName() + "' has too many generic types. Only single generic type is supported.");
	}
}
