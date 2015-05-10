package org.marting.dslgenerator;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.marting.dslgenerator.data.TestDomainModelChild;
import org.marting.dslgenerator.field.Constant;
import org.marting.dslgenerator.field.DslFieldFactory;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class RandomValueGeneratorTest {

	private RandomValueGenerator rvg;

	@Before
	public void init() throws IOException {
		Properties properties = new Properties();
		properties.load(DslFieldFactory.class.getClassLoader().getResourceAsStream("constants.properties"));
		rvg = new RandomValueGenerator();
	}

	@Test
	public void shouldGenerateIntValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field1 = FieldUtils.getField(clazz, "intField", true);
		Field field2 = FieldUtils.getField(clazz, "integerObjField", true);
		String result = rvg.getGeneratorValue(field1);
		assertThat(result, startsWith("RandomUtils.nextInt"));
		result = rvg.getGeneratorValue(field2);
		assertThat(result, startsWith("RandomUtils.nextInt"));
		assertThat(rvg.getConstantsUsed().size(), is(1));
		assertThat(rvg.getConstantsUsed().iterator().next().getName(), is("DEFAULT_MAX_INTEGER_VALUE"));
		assertThat(rvg.getConstantsUsed().iterator().next().getType(), is("Integer"));
		assertThat(rvg.getConstantsUsed().iterator().next().getValue(), is("10"));
	}

	@Test
	public void shouldGenerateStringValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringField", true);
		String result = rvg.getGeneratorValue(field);
		assertThat(result, startsWith("RandomStringUtils.randomAlphabetic"));
	}

	@Test
	public void shouldGenerateDateValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "dateObjField", true);
		String result = rvg.getGeneratorValue(field);
		assertThat(result, startsWith("new Date(RandomUtils.nextLong"));
	}

	@Test
	public void shouldGenerateListValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringList", true);
		String result = rvg.getGeneratorValue(field);
		assertThat(result, equalTo("null"));
	}

	@Test
	public void shouldGenerateArrayValue() throws NoSuchFieldException, SecurityException {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringArray", true);
		String result = rvg.getGeneratorValue(field);
		assertThat(result, equalTo("null"));
	}

	@Test
	public void shouldContainConstants() {
		Map<String, Constant> constants =  rvg.getConstants();
		assertThat(constants.keySet().size(), is(7));
		assertThat(constants.containsKey("String"), is(true));
		assertThat(constants.containsKey("Integer"), is(true));
		assertThat(constants.containsKey("Double"), is(true));
		assertThat(constants.containsKey("Date"), is(true));
		assertThat(constants.get("String").getType(), is("int"));
		assertThat(constants.get("String").getName(), is("DEFAULT_MAX_STRING_LENGTH"));
		assertThat(constants.get("String").getValue(), is("10"));
		assertThat(constants.get("Double").getType(), is("Double"));
		assertThat(constants.get("Double").getName(), is("DEFAULT_MAX_DOUBLE_VALUE"));
		assertThat(constants.get("Double").getValue(), is("10.0"));
	}
}
