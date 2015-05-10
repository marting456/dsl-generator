package org.marting.dslgenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.marting.dslgenerator.field.Constant;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class ConstantTest {

	@Test
	public void shouldEqualTwoConstantsWithSameName() {
		Constant constant1 = new Constant("String");
		constant1.setName("TEST_CONST");
		constant1.setValue("10");
		Constant constant2 = new Constant("String");
		constant2.setName("TEST_CONST");
		constant2.setValue("10");
		assertThat(constant1, equalTo(constant2));
	}

	@Test
	public void shouldNotEqualTwoConstantsWithDifferentName() {
		Constant constant1 = new Constant("String");
		constant1.setName("TEST_CONST_DIFFERENT");
		constant1.setValue("10");
		Constant constant2 = new Constant("String");
		constant2.setName("TEST_CONST");
		constant2.setValue("10");
		assertThat(constant1, not(equalTo(constant2)));
	}
}
