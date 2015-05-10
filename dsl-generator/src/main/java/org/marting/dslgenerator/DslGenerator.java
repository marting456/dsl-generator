package org.marting.dslgenerator;

import java.beans.Introspector;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.marting.dslgenerator.exception.UnsupportedTypeException;
import org.marting.dslgenerator.field.DslField;
import org.marting.dslgenerator.field.DslFieldArray;
import org.marting.dslgenerator.field.DslFieldComplex;
import org.marting.dslgenerator.field.DslFieldFactory;
import org.marting.dslgenerator.freemarker.IsTypeOf;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public final class DslGenerator {

	public static final String ABSTRACT_DSL_NAME = "AbstractDSL";
	private static final Logger LOGGER = Logger.getLogger(DslGenerator.class);

	private DslModel dslModel;
	private Configuration cfg;
	private String dslClassName;
	private RandomValueGenerator rvg;

	public DslGenerator() throws IOException {
		//Freemarker configuration object
        this.cfg = new Configuration();
        this.cfg.setClassForTemplateLoading(DslGenerator.class, "/");
        this.rvg = new RandomValueGenerator();

	}

	public String generateDSL(String className) throws ClassNotFoundException, IOException, TemplateException, UnsupportedTypeException {
		dslModel = new DslModel();
	    dslModel.setSourceClass(loadSourceClass(className));
	    dslModel.setFields(getFields(dslModel.getSourceClass()));
	    dslModel.setImports(getImports(dslModel.getFields()));
	    this.dslClassName = dslModel.getSourceClass().getSimpleName() + "DSL";
	    return createOutput(dslModel);
	}

	public String getDslClassName() {
		return dslClassName;
	}

	Class<?> loadSourceClass(String className) throws ClassNotFoundException, IOException {

		Class<?> aClass = null;
		ClassLoader loader = ClassLoader.getSystemClassLoader();

		// Create a new class loader with the directory
		LOGGER.debug("class: " + className);
		aClass = loader.loadClass(className);
        LOGGER.debug("loaded class:  " + aClass.getName());
        return aClass;
	}

	List<DslField> getFields(Class<?> aClass) throws UnsupportedTypeException, ClassNotFoundException, IOException {
		Field[] fields = FieldUtils.getAllFields(aClass);
		List<DslField> dslFields = new ArrayList<DslField>();
		DslFieldFactory dslFieldFactory = new DslFieldFactory(rvg);
		for (Field field : fields) {
			if (!(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))) {
				DslField dslField = dslFieldFactory.buildDslField(field);
				dslFields.add(dslField);
			}
		}
		return dslFields;
	}

	Set<Class<?>> getImports(List<DslField> fields) throws ClassNotFoundException {
		Set<Class<?>> imports  = new TreeSet<Class<?>>(new ClassComparator());
		for (DslField dslField : fields) {
			if (!dslField.getField().getType().isPrimitive()) {
				if (dslField instanceof DslFieldComplex) {
					imports.add(dslField.getTypeParameter());
					imports.add(dslField.getField().getType());
					imports.add(((DslFieldComplex) dslField).getImplementingClazz());
				} else if (dslField instanceof DslFieldArray) {
					imports.add(dslField.getField().getType().getComponentType());
				} else {
					imports.add(dslField.getField().getType());
				}
			}
		}
		imports.add(RandomUtils.class);
		imports.add(RandomStringUtils.class);
		return imports;
	}

	String createOutput(DslModel dslModel) throws IOException, TemplateException {
		String result = null;

        //Load template from source folder
        Template template = cfg.getTemplate("templates/dsl-template.ftl");

        // Build the data-model
        SimpleHash model = new SimpleHash();
        model.put("className", dslModel.getSourceClass().getSimpleName());
        model.put("packageName", dslModel.getSourceClass().getPackage().getName());
        model.put("dslClassName", this.dslClassName);
        model.put("dslFields", dslModel.getFields());
        model.put("imports", dslModel.getImports());
        model.put("classObj", Introspector.decapitalize(dslModel.getSourceClass().getSimpleName()));
        model.put("isTypeOf", new IsTypeOf());
        model.put("constants", rvg.getConstantsUsed());

        Writer out = new StringWriter();
        template.process(model, out);
        out.flush();
        result = out.toString();
        LOGGER.debug("\n" + result);
        out.close();
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
