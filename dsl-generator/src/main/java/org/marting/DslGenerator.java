package org.marting;

import java.beans.Introspector;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		LOGGER.debug("Starting...");
		readInputParameters(args);
		String className = "org.marting.data.TestDomainModelChild";
	    Class<?> aClass = loadSourceClass(className);
	    List<DslField> fields = getFields(aClass);
	    Set<Class<?>> imports = getImports(fields);
        createOutput(aClass, imports, fields);
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
			dslFields.add(new DslField(field));
		}
		return dslFields;
	}

	static Set<Class<?>> getImports(List<DslField> fields) {
		Set<Class<?>> imports  = new HashSet<Class<?>>();
		for (DslField dslField : fields) {
			if (!dslField.getField().getType().isPrimitive()) {
				imports.add(dslField.getField().getType());
			}
		}
		return imports;
	}

	static void createOutput(Class<?> aClass, Set<Class<?>> imports, List<DslField> dslFields) {
		//Freemarker configuration object
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(DslGenerator.class, "/");
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("templates/dsl-template.ftl");

            // Build the data-model
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("className", aClass.getSimpleName());
            model.put("dslClassName", aClass.getSimpleName() + "DSL");
            model.put("dslFields", dslFields);
            model.put("imports", imports);
            model.put("classObj", Introspector.decapitalize(aClass.getSimpleName()));

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
}
