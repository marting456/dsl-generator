package org.marting;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class DslGenerator {

	public static final String ABSTRACT_DSL_NAME = "AbstractDSL";
	private static final Logger LOGGER = LoggerFactory.getLogger(DslGenerator.class);

	private DslModel dslModel;
	private Configuration cfg;
	private String dslClassName;

	public DslGenerator() {
		//Freemarker configuration object
        this.cfg = new Configuration();
        this.cfg.setClassForTemplateLoading(DslGenerator.class, "/");
	}

	public String generateDSL(String className, String dir) throws ClassNotFoundException {
		dslModel = new DslModel();
	    dslModel.setSourceClass(loadSourceClass(className, dir));
	    dslModel.setFields(getFields(dslModel.getSourceClass()));
	    dslModel.setImports(getImports(dslModel.getFields()));
	    this.dslClassName = dslModel.getSourceClass().getSimpleName() + "DSL";
	    return createOutput(dslModel);
	}

	public String getDslClassName() {
		return dslClassName;
	}

	Class<?> loadSourceClass(String className, String dir) throws ClassNotFoundException {

		File file = new File(dir);
		Class<?> aClass = null;
		URLClassLoader loader = null;

		try {
			URL url =  file.toURI().toURL();
			URL[] urls = new URL[] { url };
			// Create a new class loader with the directory
			loader = new URLClassLoader(urls);
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

	List<DslField> getFields(Class<?> aClass) {
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

	String getGeneratorValue(Field field) {
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
			return "RandomUtils.nextInt(0, 10)";
		}
		if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return "(short) RandomUtils.nextInt(0, 10)";
		}
		if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return "RandomUtils.nextLong(0, 10)";
		}
		if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return "RandomUtils.nextDouble(0.0, 10.0)";
		}
		if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return "RandomUtils.nextFloat(0.0f, 10.0f)";
		}
		if (field.getType().equals(String.class)) {
			return "RandomStringUtils.randomAlphabetic(10)";
		}
		if (field.getType().equals(Date.class)) {
			return "new Date(RandomUtils.nextLong(0 ,1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30))";
		}
		return "null";
	}

	Set<Class<?>> getImports(List<DslField> fields) {
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

	String createOutput(DslModel dslModel) {
		String result = null;
        try {

            //Load template from source folder
            Template template = cfg.getTemplate("templates/dsl-template.ftl");

            // Build the data-model
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("className", dslModel.getSourceClass().getSimpleName());
            model.put("packageName", dslModel.getSourceClass().getPackage().getName());
            model.put("dslClassName", this.dslClassName);
            model.put("dslFields", dslModel.getFields());
            model.put("imports", dslModel.getImports());
            model.put("classObj", Introspector.decapitalize(dslModel.getSourceClass().getSimpleName()));

            Writer out = new StringWriter();
            template.process(model, out);
            out.flush();
            result = out.toString();
            LOGGER.debug(result);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
		return result;
	}

	public String generateAbstractDSL() throws IOException, TemplateException {
		String result = null;
		Map<String, Object> model = new HashMap<String, Object>();
		Template template = cfg.getTemplate("templates/abstract-dsl.ftl");
		Writer out = new StringWriter();
		model.put("packageName", dslModel.getSourceClass().getPackage().getName());
		template.process(model, out);
		out.flush();
		result = out.toString();
		out.close();
		return result;
	}

	private static class ClassComparator implements Comparator<Class<?>> {

		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
