/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
package org.marting;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Before;
import org.junit.Test;

import freemarker.template.TemplateException;

public class DslGeneratorTest {

	private static final String TEST_CLASS_NAME = "org.marting.data.TestDomainModelChild";

	private Class<?> aClass;
	private DslGenerator dslGenerator = new DslGenerator();

	@Before
	public void init() {
		try {
			aClass = dslGenerator.loadSourceClass(TEST_CLASS_NAME, "");
		} catch (Exception e) {
			fail("Unbable to load class" + e.toString());
		}
	}

	@Test
	public void shouldReadFields() throws ClassNotFoundException  {
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
	}

	@Test
	public void shouldReadParentFields() throws ClassNotFoundException  {
		List<DslField> dslFields = dslGenerator.getFields(aClass);
		List<Field> fields = dslFields.stream().map(s -> s.getField()).collect(Collectors.toList());
		assertThat(fields.stream().filter(s -> s.getName().equals("intFieldP")).count(), equalTo(1L));
		assertThat(fields.stream().filter(s -> s.getName().equals("stringFieldP")).count(), equalTo(1L));
	}

	@Test
	public void shouldCompileGeneratedClass() throws ClassNotFoundException, IOException, TemplateException {
		String dslSourceCode = dslGenerator.generateDSL(TEST_CLASS_NAME, "");
		String absDslSourceCode = dslGenerator.generateAbstractDSL();
		assertThat(dslSourceCode, notNullValue());
        File file1 = new File(dslGenerator.getDslClassName() + ".java");
        File file2 = new File("AbstractDSL.java");
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

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(filesToCompile));
        boolean compilationResult = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        // assert no compilation errors
        assertThat(compilationResult, is(true));
        assertThat(diagnostics.getDiagnostics().size(), is(0));
        File file1Result = new File(dslGenerator.getDslClassName() + ".class");
        File file2Result = new File("AbstractDSL.class");
        file1Result.delete();
        file2Result.delete();
	}
}
