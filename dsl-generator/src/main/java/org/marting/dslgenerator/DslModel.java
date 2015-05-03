package org.marting.dslgenerator;

import java.util.List;
import java.util.Set;

import org.marting.dslgenerator.field.DslField;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class DslModel {

    List<DslField> fields;
    Set<Class<?>> imports;
    Class<?> sourceClass;

    public List<DslField> getFields() {
		return fields;
	}
	public void setFields(List<DslField> fields) {
		this.fields = fields;
	}
	public Set<Class<?>> getImports() {
		return imports;
	}
	public void setImports(Set<Class<?>> imports) {
		this.imports = imports;
	}
	public Class<?> getSourceClass() {
		return sourceClass;
	}
	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}
}
