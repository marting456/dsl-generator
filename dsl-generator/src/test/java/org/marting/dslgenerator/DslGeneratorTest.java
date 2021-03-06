package org.marting.dslgenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.marting.dslgenerator.data.TestClassWithNoArgConstructor;
import org.marting.dslgenerator.data.TestDomainModelChild;
import org.marting.dslgenerator.exception.UnsupportedTypeException;
import org.marting.dslgenerator.field.DslField;

import freemarker.template.TemplateException;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class DslGeneratorTest {

	private static final Logger LOGGER = Logger.getLogger(DslGeneratorTest.class);

	private static final String TEST_CLASS_NAME = "org.marting.dslgenerator.data.TestDomainModelChild";
	private static final String TEST_CLASS_NAME_UNSUPPORTED = "org.marting.dslgenerator.data.TestDomainModelChildUnsupported";
	private static final String TEST_PACKAGE_DIR = "org/marting/dslgenerator/data/";

	private Class<?> aClass;
	private DslGenerator dslGenerator;

	@Before
	public void init() throws IOException {
		this.dslGenerator = new DslGenerator();
		try {
			aClass = dslGenerator.loadSourceClass(TEST_CLASS_NAME);
		} catch (Exception e) {
			fail("Unbable to load class" + e.toString());
		}
	}

	@Test
	public void shouldReadFields() throws ClassNotFoundException, UnsupportedTypeException, IOException  {
		List<DslField> dslFields = dslGenerator.getFields(aClass);
		List<Field> fields = dslFields.stream().map(s -> s.getField()).collect(Collectors.toList());
		assertThat(fields.stream().filter(s -> s.getType().equals(Long.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Short.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Float.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(String.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Double.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Integer.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(long.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(short.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(float.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(double.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(int.class)).count(), equalTo(2L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Date.class)).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getType().equals(TestClassWithNoArgConstructor.class)).count(), equalTo(1L));
	}

	@Test
	public void shouldReadParentFields() throws ClassNotFoundException, UnsupportedTypeException, IOException  {
		List<DslField> dslFields = dslGenerator.getFields(aClass);
		List<Field> fields = dslFields.stream().map(s -> s.getField()).collect(Collectors.toList());
		assertThat(fields.stream().filter(s -> s.getName().equals("intFieldP")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringFieldP")).count(), equalTo(1L));
	}

	@Test
	public void shouldCompileGeneratedClass() throws ClassNotFoundException,
		IOException, TemplateException, UnsupportedTypeException, IllegalAccessException, IllegalArgumentException,
		InvocationTargetException, NoSuchMethodException, SecurityException {

		String dslSourceCode = dslGenerator.generateDSL(TEST_CLASS_NAME);
		String absDslSourceCode = dslGenerator.generateAbstractDSL();
		assertThat(dslSourceCode, notNullValue());
        File[] filesToCompile = prepareCompilationUnits(dslSourceCode, absDslSourceCode);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(filesToCompile));
        boolean compilationResult = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        // assert no compilation errors
        for (Diagnostic<?> message : diagnostics.getDiagnostics()) {
        	LOGGER.error(message.getMessage(null));
        }
        assertThat(compilationResult, is(true));
        assertThat(diagnostics.getDiagnostics().size(), is(0));
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File(".").toURI().toURL() });
        LOGGER.debug(Arrays.deepToString(classLoader.getURLs()));
        Class<?> clazz = classLoader.loadClass(TEST_CLASS_NAME + "DSL");

        List<Field> fields = Arrays.asList(FieldUtils.getAllFields(clazz));
		assertFields(fields);

		List<Method> methods = Arrays.asList(clazz.getMethods());
		assertMethods(methods);

		Method method = clazz.getMethod("testDomainModelChild");
		Object dslObj = method.invoke(null);

		method = dslObj.getClass().getMethod("build");
		TestDomainModelChild tdmc = (TestDomainModelChild) method.invoke(dslObj);

		assertThat(tdmc.getStringList().size(), is(10));
		assertThat(tdmc.getStringList().get(0), not(isEmptyString()));
		assertThat(tdmc.getStringList().get(9), not(isEmptyString()));

		assertThat(tdmc.getStringArray().length, is(10));
		assertThat(tdmc.getStringArray()[0], not(isEmptyString()));
		assertThat(tdmc.getStringArray()[9], not(isEmptyString()));

		assertThat(tdmc.getCustomFieldWithoutNoArgConstr(), nullValue());
		assertThat(tdmc.getCustomFieldWithNoArgConstr(), notNullValue());

		assertThat(tdmc.getCustomFieldWithoutNoArgConstrList().size(), is(10));
		assertThat(tdmc.getCustomFieldWithoutNoArgConstrList().get(0), nullValue());
		assertThat(tdmc.getCustomFieldWithoutNoArgConstrList().get(9),  nullValue());

		assertThat(tdmc.getCustomFieldWithNoArgConstrList().size(), is(10));
		assertThat(tdmc.getCustomFieldWithNoArgConstrList().get(0), notNullValue());
		assertThat(tdmc.getCustomFieldWithNoArgConstrList().get(9),  notNullValue());

		File root = new File("org");
        FileUtils.deleteDirectory(root);
	}

	private void assertMethods(List<Method> methods) {
		assertThat(methods.stream().filter(s -> s.getName().equals("withIntFieldP")).count(), equalTo(1L));
		assertThat(methods.stream().filter(s -> s.getName().equals("withStringFieldP")).count(), equalTo(1L));
		assertThat(methods.stream().filter(s -> s.getName().equals("withIntField")).count(), equalTo(1L));
		assertThat(methods.stream().filter(s -> s.getName().equals("withStringField")).count(), equalTo(1L));
		assertThat(methods.stream().filter(s -> s.getName().equals("withStringList")).count(), equalTo(1L));
	}

	private void assertFields(List<Field> fields) {
		assertThat(fields.stream().filter(s -> s.getName().equals("intFieldP")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringFieldP")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("intField")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringField")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringList")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("integerSet")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringArray")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getType().equals(String[].class)).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getType().equals(List.class)).count(), equalTo(3L));
		assertThat(fields.stream().filter(s -> s.getType().equals(Collection.class)).count(), equalTo(1L));
	}

	@Test(expected = UnsupportedTypeException.class)
	public void shouldThrowExceptionForUnsupportedClass() throws ClassNotFoundException, IOException, TemplateException, UnsupportedTypeException {
		dslGenerator.generateDSL(TEST_CLASS_NAME_UNSUPPORTED);
	}

	private File[] prepareCompilationUnits(String dslSourceCode, String absDslSourceCode) throws FileNotFoundException {
		File file1 = new File(TEST_PACKAGE_DIR + dslGenerator.getDslClassName() + ".java");
        File file2 = new File(TEST_PACKAGE_DIR + DslGenerator.ABSTRACT_DSL_NAME + ".java");
        file1.getParentFile().mkdirs();
        file2.getParentFile().mkdirs();
        file1.deleteOnExit();
        file2.deleteOnExit();
        PrintWriter writer1 = new PrintWriter(file1);
        writer1.write(dslSourceCode);
        writer1.flush();
        writer1.close();
        PrintWriter writer2 = new PrintWriter(file2);
        writer2.write(absDslSourceCode);
        writer2.flush();
        writer2.close();
        File[] filesToCompile = { file1, file2};
		return filesToCompile;
	}
}
