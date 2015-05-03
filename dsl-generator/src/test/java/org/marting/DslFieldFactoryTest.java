package org.marting;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.marting.data.TestDomainModelChild;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class DslFieldFactoryTest {

	@Test
	public void shouldBuildSimple() throws NoSuchFieldException, ClassNotFoundException, UnsupportedType {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringField", true);
		DslField result = DslFieldFactory.buildDslField(field);
		assertThat(result, instanceOf(DslFieldSimple.class));
		assertThat(result.getDeclaredType(), equalTo("String"));
	}

	@Test
	public void shouldBuildComplex() throws NoSuchFieldException, ClassNotFoundException, UnsupportedType {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringList", true);
		DslField result = DslFieldFactory.buildDslField(field);
		assertThat(result, instanceOf(DslFieldComplex.class));
		DslFieldComplex dfc = (DslFieldComplex) result;
		assertThat(dfc.getDeclaredType(), equalTo("List<String>"));
		assertThat(dfc.getImplementingClazz(), equalTo(ArrayList.class));
	}

	@Test
	public void shouldBuildArray() throws NoSuchFieldException, ClassNotFoundException, UnsupportedType {
		Class<TestDomainModelChild> clazz = TestDomainModelChild.class;
		Field field = FieldUtils.getField(clazz, "stringArray", true);
		DslField result = DslFieldFactory.buildDslField(field);
		assertThat(result, instanceOf(DslFieldArray.class));
		assertThat(result.getDeclaredType(), equalTo("String[]"));
	}
}
