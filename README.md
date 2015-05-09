# dsl-generator
* auto-gen TOC:
{:toc}
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
    private Integer customerId = RandomUtils.nextInt(0, 10);
    private String firstname = RandomStringUtils.randomAlphabetic(10);
    private String middlename = RandomStringUtils.randomAlphabetic(10);
    private String lastname = RandomStringUtils.randomAlphabetic(10);
    private Date dob = new Date(RandomUtils.nextLong(0 ,((long) 1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30)));
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

## Features

## Dependencies

## Advantages

## Disadvantages
