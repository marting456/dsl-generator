# dsl-generator

The jar in this project generates a dsl helper class based on a POJO/domain class that can be used to automatically populate test data.

For example, given the class:

```
package my.example;

public class Customer {
    private Integer customerId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String dob;
    private String sex;
    private String mobile;
}
'''
