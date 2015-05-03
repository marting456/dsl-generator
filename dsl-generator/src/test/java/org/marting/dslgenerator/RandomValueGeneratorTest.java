package org.marting.dslgenerator;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.marting.dslgenerator.RandomValueGenerator;
import org.marting.dslgenerator.data.TestDomainModelChild;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class RandomValueGeneratorTest {

	@Test
	public void shouldGenerateIntValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field1 = FieldUtils.getField(clazz, "intField", true);
		Field field2 = FieldUtils.getField(clazz, "integerObjField", true);
		String result = RandomValueGenerator.getGeneratorValue(field1);
		assertThat(result, startsWith("RandomUtils.nextInt"));
		result = RandomValueGenerator.getGeneratorValue(field2);
		assertThat(result, startsWith("RandomUtils.nextInt"));
	}

	@Test
	public void shouldGenerateStringValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringField", true);
		String result = RandomValueGenerator.getGeneratorValue(field);
		assertThat(result, startsWith("RandomStringUtils.randomAlphabetic"));
	}

	@Test
	public void shouldGenerateDateValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "dateObjField", true);
		String result = RandomValueGenerator.getGeneratorValue(field);
		assertThat(result, startsWith("new Date(RandomUtils.nextLong"));
	}

	@Test
	public void shouldGenerateListValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringList", true);
		String result = RandomValueGenerator.getGeneratorValue(field);
		assertThat(result, equalTo("null"));
	}

	@Test
	public void shouldGenerateArrayValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringArray", true);
		String result = RandomValueGenerator.getGeneratorValue(field);
		assertThat(result, equalTo("null"));
	}
}
