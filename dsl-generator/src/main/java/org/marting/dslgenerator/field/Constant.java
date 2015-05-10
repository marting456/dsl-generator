package org.marting.dslgenerator.field;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class Constant {

	private String type; // Integer/String/etc
	private String value = "0";
	private String name; // SOME_CONSTANT_NAME

	public Constant(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name).build();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constant other = (Constant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
