# dsl-generator

## Introduction

The jar in this project generates a dsl helper class based on a POJO/domain class that can be used to automatically populate test data.

For example, given Customer.class:

```Java
package my.example;

public class Customer {
    private Integer customerId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String dob;
    private String sex;
    private String mobile;
    
    //getters and setters
}
```

it will generate the following CustomerDSL.java:

```java
public class CustomerDSL extends AbstractDSL<CustomerDSL, Customer> {
    private Integer customerId = RandomUtils.nextInt(0, 10);;
    private String firstname = RandomStringUtils.randomAlphabetic(10);
    private String middlename = RandomStringUtils.randomAlphabetic(10);
    private String lastname = RandomStringUtils.randomAlphabetic(10);
    private String dob = RandomStringUtils.randomAlphabetic(10);
    private String sex = RandomStringUtils.randomAlphabetic(10);
    private String mobile = RandomStringUtils.randomAlphabetic(10);
    
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
    }
    
    public CustomerDSL withFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public CustomerDSL withLastname(String lastname) {
        this.lastname = lastname;
    }
    
    // etch with method for all fields
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
## Usage

## Advantages

## Disadvantages
