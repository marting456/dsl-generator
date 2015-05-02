package ${packageName};

<#list imports as class>
import ${class.name};
</#list>

public class ${dslClassName} extends AbstractDSL<${dslClassName}, ${className}> {
          
    // members        
    <#list dslFields as dslField>
    private ${dslField.declaredType} ${dslField.field.name} = ${dslField.generatorValue};
    </#list>
    
    // constructor
    private ${dslClassName} () { };
    
    // methods    
    public ${dslClassName} ${classObj}() {
        return new ${dslClassName}();
    }
    
    public ${className} build() {
        ${className} ${classObj} = new ${className}();        
        <#list dslFields as dslField>
        <#if isTypeOf(dslField, "simple")>
        ${classObj}.${dslField.setterMethod}(this.${dslField.field.name}); 
        </#if>
        </#list>
        
        <#list dslFields as dslField>
        <#if isTypeOf(dslField, "array")>
        if (this.${dslField.field.name} == null) {
            ${dslField.declaredType} ${dslField.field.name} = new ${dslField.componentType.simpleName}[10];
            for (int i=0; i < ${dslField.field.name}.length; i++) {
                ${dslField.field.name}[i] = ${dslField.componentGeneratorValue};
            }
            ${classObj}.${dslField.setterMethod}(${dslField.field.name}); 
        } else {
            ${classObj}.${dslField.setterMethod}(this.${dslField.field.name});
        }
        </#if>
        </#list>
        return ${classObj};
    }
    
    <#list dslFields as dslField>
    public ${dslClassName} ${dslField.withMethod}(${dslField.declaredType} ${dslField.field.name}) {
        this.${dslField.field.name} = ${dslField.field.name};
        return this;
    }
    </#list>    
} 