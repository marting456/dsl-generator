package org.marting.dslgenerator.data;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class TestClassWithoutNoArgConstructor {

	private String str;

	public TestClassWithoutNoArgConstructor(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
