/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class DslGeneratorTest {

	@Test
	public void shouldLoadClassFile() {
		try {
			DslGenerator.loadSourceClass("org.marting.data.TestDomainModel");
		} catch (Exception e) {
			fail("Unbable to load class" + e.toString());
		}
	}

	@Test
	public void shouldReadFields() throws ClassNotFoundException  {
		Class<?> aClass = DslGenerator.loadSourceClass("org.marting.data.TestDomainModel");
		List<Field> fields = DslGenerator.getFields(aClass);
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(int.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(double.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(float.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(long.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(short.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(String.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(Integer.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(Double.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(Float.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(Short.class))));
		assertThat(fields, hasItem(Matchers.<Field>hasProperty("type", equalTo(Long.class))));
	}
}
