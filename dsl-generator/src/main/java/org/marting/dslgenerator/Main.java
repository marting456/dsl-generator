package org.marting.dslgenerator;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.marting.dslgenerator.exception.InvalidUsageException;
import org.marting.dslgenerator.exception.UnsupportedTypeException;

import freemarker.template.TemplateException;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class);
	private static final String USAGE = "java -cp dsl-generator-1.0.jar;path/to/dependent/classes org.marting.dslgenerator.Main -c com.example.SomeClass [options]";
	private static final int USAGE_WIDTH= 100;

	public static void main(String[] args) {

		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(USAGE_WIDTH);
		CommandLine commands = null;
		Options options = createOptions();
		try {
			commands = readInputParameters(args, options);
			if (commands.hasOption("h")) {
				formatter.printHelp(USAGE, options);
				return;
			}
			if (commands.hasOption("v")) {
				Logger.getRootLogger().setLevel(Level.DEBUG);
				LOGGER = Logger.getLogger(Main.class);
			}
			LOGGER.debug("Starting...");
			DslGenerator dslGenerator = new DslGenerator();
			String className = commands.getOptionValue("c");
			String resultDsl = dslGenerator.generateDSL(className);
			String dslClassName = dslGenerator.getDslClassName();
			PrintWriter fileWriter = new PrintWriter(dslClassName + ".java");
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
		} catch (InvalidUsageException e) {
			LOGGER.error(e.getMessage());
			formatter.printHelp(USAGE, options);
		} catch (UnsupportedTypeException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException | IOException | TemplateException e) {
			LOGGER.error(e.toString());
		}
	}

	static CommandLine readInputParameters(String[] args, Options options) throws InvalidUsageException {
		CommandLineParser parser = new BasicParser();
		CommandLine commands = null;

		// parse the command line arguments
		try {
			commands = parser.parse(options, args);
		} catch (ParseException e) {
			throw new InvalidUsageException("Invalid usage.");
		}

		if (commands.getOptionValue("c") == null) {
			throw new InvalidUsageException("Missing input class name.");
		}
		return commands;
	}

	static Options createOptions() {
		Options options = new Options();
		options.addOption("c", "class", true, "fully qualified name of source class ie. com.example.SomeClass.");
		options.addOption("h", false, "print this message");
		options.addOption("ga", "generate-abstract", false, "generate abstract base class");
		options.addOption("v", "verbose", false, "print debugging information.");
		return options;
	}
}
