/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import static org.junit.Assert.fail;

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
}
