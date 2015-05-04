package org.marting.dslgenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.marting.dslgenerator.exception.InvalidUsageException;


/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class MainTest {

	@Test(expected = InvalidUsageException.class)
	public void shouldThrowExceptionforNoArgs() throws InvalidUsageException, ParseException {
		String[] args = {};
		Main.readInputParameters(args, Main.createOptions());
	}

	@Test(expected = InvalidUsageException.class)
	public void shouldThrowExceptionforUnknownArgs() throws InvalidUsageException, ParseException {
		String[] args = {"xyz"};
		Main.readInputParameters(args, Main.createOptions());
	}

	@Test(expected = InvalidUsageException.class)
	public void shouldThrowExceptionforMissingClass() throws InvalidUsageException, ParseException {
		String[] args = {"-c"};
		Main.readInputParameters(args, Main.createOptions());
	}

	@Test
	public void shouldParseArgs() throws InvalidUsageException, ParseException {
		String[] args = {"-c", "org.some.Class", "-d", "someDir", "-ga", "-v"};
		CommandLine commands = Main.readInputParameters(args, Main.createOptions());
		assertThat(commands.hasOption("c"), is(true));
		assertThat(commands.hasOption("d"), is(true));
		assertThat(commands.hasOption("ga"), is(true));
		assertThat(commands.hasOption("v"), is(true));
		assertThat(commands.getOptionValue("c"), equalTo("org.some.Class"));
		assertThat(commands.getOptionValue("d"), equalTo("someDir"));
	}

	@Test
	public void shouldGenerateDsl() throws InvalidUsageException, ParseException, IOException {
		String[] args = {"-c", "org.marting.dslgenerator.data.TestDomainModelChild"};
		Main.main(args);
		BufferedReader reader = new BufferedReader(new FileReader("TestDomainModelChildDSL.java"));
		assertThat(reader.readLine(), is("package org.marting.dslgenerator.data;"));
		File file = new File("TestDomainModelChildDSL.java");
		reader.close();
		file.delete();
	}

	@Test
	public void shouldGenerateAbastractDsl() throws InvalidUsageException, ParseException, IOException {
		String[] args = {"-c", "org.marting.dslgenerator.data.TestDomainModelChild", "-ga"};
		Main.main(args);
		BufferedReader reader = new BufferedReader(new FileReader("TestDomainModelChildDSL.java"));
		assertThat(reader.readLine(), is("package org.marting.dslgenerator.data;"));
		File file = new File("TestDomainModelChildDSL.java");
		file.delete();
		reader.close();

		reader = new BufferedReader(new FileReader("AbstractDSL.java"));
		assertThat(reader.readLine(), is("package org.marting.dslgenerator.data;"));
		file = new File("AbstractDSL.java");
		file.delete();
		reader.close();
	}
}