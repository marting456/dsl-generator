package org.marting;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class DslGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(DslGenerator.class);

	private DslGenerator() { }

	public static void main(String[] args) throws ClassNotFoundException {
		LOGGER.info("Starting...");
		readInputParameters(args);
		String className = "org.marting.data.TestDomainModel";
	    Class<?> aClass = loadSourceClass(className);
	    getFields(aClass);
        createOutput();
        LOGGER.debug("Finished");
	}

	static Class<?> loadSourceClass(String className) throws ClassNotFoundException {
		ClassLoader classLoader = DslGenerator.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass(className);
        LOGGER.info("aClass.getName() = " + aClass.getName());
        return aClass;
	}

	static List<Field> getFields(Class<?> aClass) {
		Field[] fields = FieldUtils.getAllFields(aClass);
		return Arrays.asList(fields);
	}

	static void createOutput() {
		//Freemarker configuration object
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(DslGenerator.class, "/");
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("templates/dsl-template.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("dslClassName", "TestDSL");

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
	}

	static void readInputParameters(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("a", "all", false, "do not hide entries starting with .");
		options.addOption("A", "almost-all", false, "do not list implied . and ..");
		options.addOption("b", "escape", true, "print octal escapes for nongraphic ");

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// validate that block-size has been set
			if (line.hasOption("b")) {
				// print the value of block-size
				LOGGER.info("value: " + line.getOptionValue("b"));
			}
		} catch (ParseException exp) {
			LOGGER.error("Unexpected exception:" + exp.getMessage());
		}
	}
}
