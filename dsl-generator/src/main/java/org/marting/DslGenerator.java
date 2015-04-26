package org.marting;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class DslGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(DslGenerator.class);
	private static final String USAGE = "java -jar dsl-generator-1.0.jar -c com.example.SomeClass [-d /path/to/class/package]";

	private static CommandLine commands = null;

	private DslGenerator() { }

	public static void main(String[] args) throws ClassNotFoundException {
		LOGGER.debug("Starting...");
		readInputParameters(args);
		String className =  commands.getOptionValue("c");
		DslModel model = new DslModel();
	    model.setSourceClass(loadSourceClass(className));
	    model.setFields(getFields(model.getSourceClass()));
	    model.setImports(getImports(model.getFields()));
        createOutput(model);
        LOGGER.debug("Finished");
	}

	static Class<?> loadSourceClass(String className) throws ClassNotFoundException {

		File file = new File("");
		Class<?> aClass = null;
		URLClassLoader loader = null;

		if (commands != null && commands.hasOption("d")) {
			file = new File(commands.getOptionValue("d"));
		}

		try {
			URL url =  file.toURI().toURL();
			URL[] urls = new URL[] { url };

			// Create a new class loader with the directory
			loader = new URLClassLoader(urls);

			// Load in the class; Class.childclass should be located in
			// the directory file:/c:/class/user/information
			LOGGER.info("urls: " + url.toString());
			LOGGER.info("class: " + className);
			aClass = loader.loadClass(className);
		} catch (MalformedURLException e) {
	        LOGGER.error(e.toString());
	        System.exit(1);
		} catch (ClassNotFoundException e) {
	        LOGGER.error(e.toString());
	        System.exit(1);
		} finally {
			try {
				loader.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}

        LOGGER.debug("aClass.getName() = " + aClass.getName());
        return aClass;
	}

	static List<DslField> getFields(Class<?> aClass) {
		Field[] fields = FieldUtils.getAllFields(aClass);
		List<DslField> dslFields = new ArrayList<DslField>();
		for (Field field : fields) {
			if (!(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))) {
				DslField dslField = new DslField(field);
				dslField.setGeneratorValue(getGeneratorValue(field));
				dslFields.add(dslField);
			}
		}
		return dslFields;
	}

	private static String getGeneratorValue(Field field) {
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class) ||
				field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return "RandomUtils.nextInt(0, 10)";
		}
		if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return "RandomUtils.nextLong(0, 10)";
		}
		if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return "RandomUtils.nextDouble(0.0, 10.0)";
		}
		if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return "RandomUtils.nextFloat(0.0, 10.0)";
		}
		if (field.getType().equals(String.class)) {
			return "RandomStringUtils.randomAlphabetic(10)";
		}
		if (field.getType().equals(Date.class)) {
			return "new Date(RandomUtils.nextLong(0 ,1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30))";
		}
		return "null";
	}

	static Set<Class<?>> getImports(List<DslField> fields) {
		Set<Class<?>> imports  = new TreeSet<Class<?>>(new ClassComparator());
		for (DslField dslField : fields) {
			if (!dslField.getField().getType().isPrimitive()) {
				imports.add(dslField.getField().getType());
			}
		}
		imports.add(RandomUtils.class);
		imports.add(RandomStringUtils.class);
		return imports;
	}

	static void createOutput(DslModel dslModel) {
		//Freemarker configuration object
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(DslGenerator.class, "/");
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("templates/dsl-template.ftl");

            // Build the data-model
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("className", dslModel.getSourceClass().getSimpleName());
            model.put("packageName", dslModel.getSourceClass().getPackage().getName());
            model.put("dslClassName", dslModel.getSourceClass().getSimpleName() + "DSL");
            model.put("dslFields", dslModel.getFields());
            model.put("imports", dslModel.getImports());
            model.put("classObj", Introspector.decapitalize(dslModel.getSourceClass().getSimpleName()));

            // Console output
            PrintWriter fileWriter = new PrintWriter(dslModel.getSourceClass().getSimpleName() + "DSL.java");
            Writer out = new StringWriter();
            template.process(model, out);
            out.flush();
            fileWriter.print(out.toString());
            fileWriter.close();
            LOGGER.debug(out.toString());
            out.close();

            template = cfg.getTemplate("templates/abstract-dsl.ftl");
            out = new StringWriter();
            fileWriter = new PrintWriter("AbstractDSL.java");
            template.process(model, out);
            out.flush();
            fileWriter.print(out.toString());
            fileWriter.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
	}

	static void readInputParameters(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("d", true, "source directory, defaults to current directory.");
		options.addOption("c", "class", true, "fully qualified name of source class ie. com.example.SomeClass.");
		options.addOption("h", false, "print this message");

		try {
			// parse the command line arguments
			commands = parser.parse(options, args);

			if (commands.getOptionValue("c") == null ) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( USAGE, options );
				System.exit(1);
			}
		} catch (ParseException exp) {
			LOGGER.error("Unexpected exception:" + exp.getMessage());
		}
	}

	private static class ClassComparator implements Comparator<Class<?>> {

		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
