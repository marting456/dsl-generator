package org.marting;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class Main {

	private Main() { }

	public static void main(String[] args) {
		readInputParameters(args);
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(Main.class, "/");
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

	private static void readInputParameters(String[] args) {
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
				System.out.println("value: " + line.getOptionValue("b"));
			}
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}
	}
}
