# dsl-generator
## Introduction

The jar built by this project (see releases) generates a dsl helper class based on a POJO/domain class that can be used to automatically populate test data.

For example, given Customer.class:

```Java
package my.example;

public class Customer {
    private Integer customerId;
    private String firstname;
    private String middlename;
    private String lastname;
    private Date dob;
    private String sex;
    private String mobile;
    
    //getters and setters
}
```

it will generate the following CustomerDSL.java:

```java
package my.example;

public class CustomerDSL extends AbstractDSL<CustomerDSL, Customer> {

    private static final long THIRTY_YEARS_IN_MILLS = (long) 1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30;
    private static final Integer DEFAULT_MAX_INTEGER_VALUE = 10;
    private static final int DEFAULT_MAX_STRING_LENGTH = 10;
    
    private Integer customerId = RandomUtils.nextInt(0, DEFAULT_MAX_INTEGER_VALUE);
    private String firstname = RandomStringUtils.randomAlphabetic(DEFAULT_MAX_STRING_LENGTH);
    private String middlename = RandomStringUtils.randomAlphabetic(DEFAULT_MAX_STRING_LENGTH);
    private String lastname = RandomStringUtils.randomAlphabetic(DEFAULT_MAX_STRING_LENGTH);
    private Date dob = new Date(RandomUtils.nextLong(0 ,THIRTY_YEARS_IN_MILLS));
    private String sex = RandomStringUtils.randomAlphabetic(DEFAULT_MAX_STRING_LENGTH);
    private String mobile = RandomStringUtils.randomAlphabetic(DEFAULT_MAX_STRING_LENGTH);
    
    private CustomerDSL() { };
    
    public static CustomerDSL Customer() {
        return new CustomerDSL();
    }
    
    public Customer build() {
        Customer customer = new Customer();        
        customer.setFirstname(this.firstname); 
        customer.setLastname(this.lastname); 
        customer.setMiddlename(this.middlename); 
        // etc. for all fields
        return customer;
    }
    
    public CustomerDSL withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }
    
    public CustomerDSL withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    
    // etch with method for all fields
}
```
Where AbstractDSL is
```java
package my.example;

public abstract class AbstractDSL<T , S> {

	abstract S build();
}
```

and then it can be used in a test case as follows:

```java
@Test
public void shouldCreateCustomerInRepo() {
    Customer customer = CustomerDSL.customer().withFirstname("martin").build();
    customerRepo.save(customer);
    Customer savedCustomer = customerRepo.findOneByMobile(customer.getMobile());
    assertThat(savedCustomer.getFirstname(), equalTo(customer.getFirstname());
    assertThat(savedCustomer.getLastname(), equalTo(customer.getLastname());
    // etc for all fields
}
```
note that without CustomerDSL the test case would have had to set up its test data:
```java
@Test
public void shouldCreateCustomerInRepo() {
    Customer customer = new Customer();
    customer.setCustomerId(123456);
    customer.setFirstname("testFirstname");
    customer.setMiddlename("testMiddlename");
    customer.setLastname("testLastname");
    customer.setDob(new Date());
    customer.setSex("M");
    customer.setMobile("0404123123");
    customerRepo.save(customer);
    Customer savedCustomer = customerRepo.findOneByMobile(customer.getMobile());
    assertThat(savedCustomer.getFirstname(), equalTo(customer.getFirstname());
    assertThat(savedCustomer.getLastname(), equalTo(customer.getLastname());
    // etc for all fields
}
```

## Usage
<pre>
usage: java -jar dsl-generator-1.0.jar -c com.example.SomeClass [options]
 -c,--class <arg>          fully qualified name of source class ie. com.example.SomeClass.
 -d <arg>                  the directory where the root package is located, ie
                           {root-package-dir}/com/example/SomeClass. defaults to current directory.
 -ga,--generate-abstract   generate abstract base class
 -h                        print this message
 -v,--verbose              print debugging information.
</pre>

## Dependencies
All dependencies are included in the generated jar but the generated DSL will have the following dependencies:
* org.apache.commons:commons-lang3:3.4 (or above)
* AbstractDSL (can be generated with the -ga option)

## Supported field types
All types are supported. The following classes will be initialized with RanodmUtils:
* java.lang.Double
* java.lang.Float
* java.lang.Integer
* java.lang.Long
* java.lang.Short
* java.lang.String
* java.util.Date

All other types will be initialized by a default constructor if exists. To null otherwise.

## Configuration
Default constant values can be set in constants.properties.
