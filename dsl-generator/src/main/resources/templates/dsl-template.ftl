package ${packageName};

<#list imports as class>
import ${class.name};
</#list>

public class ${dslClassName}() extends AbstractDSL<${dslClassName}, ${className}> {
          
    // members        
    <#list dslFields as dslField>
    private ${dslField.field.type.simpleName} ${dslField.field.name} = ${dslField.generatorValue};
    </#list>
    
    // constructor
    private ${dslClassName} () { };
    
    // methods    
    public ${dslClassName} ${classObj}() {
        return new ${dslClassName}();
    }
    
    public static ${className} build() {
        ${className} ${classObj} = new ${className}();        
        <#list dslFields as dslField>
        ${classObj}.${dslField.setterMethod}(this.${dslField.field.name});
        </#list>
        return ${classObj};
    }
    
    <#list dslFields as dslField>
    public static ${dslClassName} ${dslField.withMethod}(${dslField.field.type.simpleName} ${dslField.field.name}){
        this.${dslField.field.name} = ${dslField.field.name};
        return this;
    }
    </#list>    
} 