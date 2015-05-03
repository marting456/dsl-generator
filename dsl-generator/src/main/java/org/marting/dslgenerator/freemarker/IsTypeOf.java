package org.marting.dslgenerator.freemarker;

import java.util.List;

import org.marting.dslgenerator.field.DslField;
import org.marting.dslgenerator.field.DslFieldArray;
import org.marting.dslgenerator.field.DslFieldComplex;
import org.marting.dslgenerator.field.DslFieldSimple;

import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class IsTypeOf implements TemplateMethodModelEx {

    @Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        if (! (args.get(0) instanceof BeanModel)) {
            throw new TemplateModelException("First argument must be a BeanModel. Was: " + args.get(0).getClass());
        }
        if (! (((BeanModel) args.get(0)).getWrappedObject() instanceof DslField)) {
            throw new TemplateModelException("First argument must be a DslField. Was: " + args.get(0).getClass());
        }
        DslField field = (DslField) ((BeanModel) args.get(0)).getWrappedObject();
        String type = args.get(1).toString();

        if (field instanceof DslFieldSimple && type.equals("simple")) {
        	return new Boolean(true);
        }
        if (field instanceof DslFieldComplex && type.equals("complex")) {
        	return new Boolean(true);
        }
        if (field instanceof DslFieldArray && type.equals("array")) {
        	return new Boolean(true);
        }
        return new Boolean(false);
    }
}
