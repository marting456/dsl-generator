package org.marting;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class Main {

	private Main() { }

	public static void main(String[] argv) {

		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("a", "all", false, "do not hide entries starting with .");
		options.addOption("A", "almost-all", false, "do not list implied . and ..");
		options.addOption("b", "escape", true, "print octal escapes for nongraphic ");

		String[] args = new String[] {"-b", "xyz"};

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// validate that block-size has been set
			if (line.hasOption("b")) {
				// print the value of block-size
				System.out.println("value: " + line.getOptionValue("b"));
			}
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}
	}
}
