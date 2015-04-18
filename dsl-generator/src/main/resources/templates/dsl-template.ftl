<#list imports as class>
import ${class};
</#list>

public class ${dslClassName}() extends AbstractDSL<AbstractDSL, ${className}> {
          
    // members        
    <#list fields as field>
    private ${field.type.simpleName} ${field.name} = null;
    </#list>
    
    // constructor
    private ${dslClassName} () { };
    
    // methods
    
    public ${className} ${getMethodName}() {
        return new ${className}();
    }
    
    public static ${dslClassName} build() {
        return new ${dslClassName};
    }
    
    
} 