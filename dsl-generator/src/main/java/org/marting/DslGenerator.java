package org.marting;

import java.beans.Introspector;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
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

	private DslGenerator() { }

	public static void main(String[] args) throws ClassNotFoundException {
		LOGGER.debug("Starting...");
		readInputParameters(args);
		String className = "org.marting.data.TestDomainModelChild";
		DslModel model = new DslModel();
	    model.setSourceClass(loadSourceClass(className));
	    model.setFields(getFields(model.getSourceClass()));
	    model.setImports(getImports(model.getFields()));
        createOutput(model);
        LOGGER.debug("Finished");
	}

	static Class<?> loadSourceClass(String className) throws ClassNotFoundException {
		ClassLoader classLoader = DslGenerator.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass(className);
        LOGGER.debug("aClass.getName() = " + aClass.getName());
        return aClass;
	}

	static List<DslField> getFields(Class<?> aClass) {
		Field[] fields = FieldUtils.getAllFields(aClass);
		List<DslField> dslFields = new ArrayList<DslField>();
		for (Field field : fields) {
			DslField dslField = new DslField(field);
			dslField.setGeneratorValue(getGeneratorValue(field));
			dslFields.add(dslField);
		}
		return dslFields;
	}

	private static String getGeneratorValue(Field field) {
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class) ||
				field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return "RandomUtils.nextInt(10)";
		}
		if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return "RandomUtils.nextLong(10)";
		}
		if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return "RandomUtils.nextDouble(10)";
		}
		if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return "RandomUtils.nextFloat(10)";
		}
		if (field.getType().equals(String.class)) {
			return "RandomStringUtils.randomAlphabetic(10)";
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
            model.put("dslClassName", dslModel.getSourceClass().getSimpleName() + "DSL");
            model.put("dslFields", dslModel.getFields());
            model.put("imports", dslModel.getImports());
            model.put("classObj", Introspector.decapitalize(dslModel.getSourceClass().getSimpleName()));

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(model, out);
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

	private static class ClassComparator implements Comparator<Class<?>> {

		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
