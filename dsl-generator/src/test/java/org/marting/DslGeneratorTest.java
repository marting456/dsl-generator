/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class DslGeneratorTest {

	private static final String TEST_CLASS_NAME = "org.marting.data.TestDomainModelChild";

	private Class<?> aClass;
	private DslGenerator dslGenerator = new DslGenerator();

	@Before
	public void init() {
		try {
			aClass = dslGenerator.loadSourceClass(TEST_CLASS_NAME, "");
		} catch (Exception e) {
			fail("Unbable to load class" + e.toString());
		}
	}

	@Test
	public void shouldReadFields() throws ClassNotFoundException  {
		List<DslField> dslFields = dslGenerator.getFields(aClass);
		List<Field> fields = dslFields.stream().map(s -> s.getField()).collect(Collectors.toList());
		assertThat(fields.stream().filter(s -> s.getType().equals(Long.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Short.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Float.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(String.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Double.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Integer.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(long.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(short.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(float.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(double.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(int.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Date.class)).count(), equalTo(1L));
	}

	@Test
	public void shouldReadParentFields() throws ClassNotFoundException  {
		List<DslField> dslFields = dslGenerator.getFields(aClass);
		List<Field> fields = dslFields.stream().map(s -> s.getField()).collect(Collectors.toList());
		assertThat(fields.stream().filter(s -> s.getName().equals("intFieldP")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringFieldP")).count(), equalTo(1L));
	}

	@Test
	public void shouldCompileGeneratedClass() {

	}
}
