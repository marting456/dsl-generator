/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;


/**
 * @author martin
 *
 */
public class Main {

	private static Logger LOGGER;
	private static final String USAGE = "java -jar dsl-generator-1.0.jar -c com.example.SomeClass [-d /path/to/class/package]";

	public static void main(String[] args) throws ClassNotFoundException, IOException, TemplateException {

		CommandLine commands = readInputParameters(args);
		if (commands.hasOption("v")) {
			System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "debug");
		}
		LOGGER = LoggerFactory.getLogger(Main.class);
		LOGGER.debug("Starting...");
		DslGenerator dslGenerator = new DslGenerator();
		String className =  commands.getOptionValue("c");
		String dir = ".";
		if (commands != null && commands.hasOption("d")) {
			dir = commands.getOptionValue("d");
		}
		String resultDsl = dslGenerator.generateDSL(className, dir);
		String dslClassName = dslGenerator.getDslClassName();
        PrintWriter fileWriter = new PrintWriter(dslClassName + "DSL.java");
        fileWriter.print(resultDsl);
        fileWriter.flush();
        fileWriter.close();
		if (commands.hasOption("ga")) {
			String abstractDsl = dslGenerator.generateAbstractDSL();
	        fileWriter = new PrintWriter(DslGenerator.ABSTRACT_DSL_NAME + ".java");
	        fileWriter.print(abstractDsl);
	        fileWriter.flush();
	        fileWriter.close();
		}

        LOGGER.debug("Finished");
	}

	private static CommandLine readInputParameters(String[] args) {
		CommandLineParser parser = new BasicParser();
		CommandLine commands = null;
		Options options = new Options();
		options.addOption("d", true, "the directory where the root package is located, ie {root-package-dir}/com/example/SomeClass. defaults to current directory.");
		options.addOption("c", "class", true, "fully qualified name of source class ie. com.example.SomeClass.");
		options.addOption("h", false, "print this message");
		options.addOption("ga", "generate-abstract", false, "generate abstract base class");
		options.addOption("v", "verbose", false, "print debugging information.");
		HelpFormatter formatter = new HelpFormatter();

		try {
			// parse the command line arguments
			commands = parser.parse(options, args);

			if (commands.getOptionValue("c") == null ) {
				formatter.printHelp( USAGE, options );
				System.exit(1);
			}
		} catch (ParseException exp) {
			formatter.printHelp( USAGE, options );
			System.exit(1);
		}
		return commands;
	}

}
