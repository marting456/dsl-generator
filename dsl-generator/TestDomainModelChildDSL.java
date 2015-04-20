package org.marting.data;

import class java.lang.Double;
import class java.lang.Float;
import class java.lang.Integer;
import class java.lang.Long;
import class java.lang.Short;
import class java.lang.String;
import class java.util.Date;
import class org.apache.commons.lang3.RandomStringUtils;
import class org.apache.commons.lang3.RandomUtils;

public class TestDomainModelChildDSL() extends AbstractDSL<TestDomainModelChildDSL, TestDomainModelChild> {
          
    // members        
    private int intField = RandomUtils.nextInt(0, 10);
    private double doubleField = RandomUtils.nextDouble(0.0, 10.0);
    private float floatField = RandomUtils.nextFloat(0.0, 10.0);
    private long longField = RandomUtils.nextLong(0, 10);
    private short shortField = RandomUtils.nextInt(0, 10);
    private String stringField = RandomStringUtils.randomAlphabetic(10);
    private Integer integerObjField = RandomUtils.nextInt(0, 10);
    private Double doubleObjField = RandomUtils.nextDouble(0.0, 10.0);
    private Float floatObjField = RandomUtils.nextFloat(0.0, 10.0);
    private Long longObjField = RandomUtils.nextLong(0, 10);
    private Short shortObjField = RandomUtils.nextInt(0, 10);
    private Date dateObjField = new Date(RandomUtils.nextLong(0 ,1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30));
    private int intFieldP = RandomUtils.nextInt(0, 10);
    private double doubleFieldP = RandomUtils.nextDouble(0.0, 10.0);
    private float floatFieldP = RandomUtils.nextFloat(0.0, 10.0);
    private long longFieldP = RandomUtils.nextLong(0, 10);
    private short shortFieldP = RandomUtils.nextInt(0, 10);
    private String stringFieldP = RandomStringUtils.randomAlphabetic(10);
    private Integer integerObjFieldP = RandomUtils.nextInt(0, 10);
    private Double doubleObjFieldP = RandomUtils.nextDouble(0.0, 10.0);
    private Float floatObjFieldP = RandomUtils.nextFloat(0.0, 10.0);
    private Long longObjFieldP = RandomUtils.nextLong(0, 10);
    private Short shortObjFieldP = RandomUtils.nextInt(0, 10);
    
    // constructor
    private TestDomainModelChildDSL () { };
    
    // methods    
    public TestDomainModelChildDSL testDomainModelChild() {
        return new TestDomainModelChildDSL();
    }
    
    public static TestDomainModelChild build() {
        TestDomainModelChild testDomainModelChild = new TestDomainModelChild();        
        testDomainModelChild.setIntField(this.intField);
        testDomainModelChild.setDoubleField(this.doubleField);
        testDomainModelChild.setFloatField(this.floatField);
        testDomainModelChild.setLongField(this.longField);
        testDomainModelChild.setShortField(this.shortField);
        testDomainModelChild.setStringField(this.stringField);
        testDomainModelChild.setIntegerObjField(this.integerObjField);
        testDomainModelChild.setDoubleObjField(this.doubleObjField);
        testDomainModelChild.setFloatObjField(this.floatObjField);
        testDomainModelChild.setLongObjField(this.longObjField);
        testDomainModelChild.setShortObjField(this.shortObjField);
        testDomainModelChild.setDateObjField(this.dateObjField);
        testDomainModelChild.setIntFieldP(this.intFieldP);
        testDomainModelChild.setDoubleFieldP(this.doubleFieldP);
        testDomainModelChild.setFloatFieldP(this.floatFieldP);
        testDomainModelChild.setLongFieldP(this.longFieldP);
        testDomainModelChild.setShortFieldP(this.shortFieldP);
        testDomainModelChild.setStringFieldP(this.stringFieldP);
        testDomainModelChild.setIntegerObjFieldP(this.integerObjFieldP);
        testDomainModelChild.setDoubleObjFieldP(this.doubleObjFieldP);
        testDomainModelChild.setFloatObjFieldP(this.floatObjFieldP);
        testDomainModelChild.setLongObjFieldP(this.longObjFieldP);
        testDomainModelChild.setShortObjFieldP(this.shortObjFieldP);
        return testDomainModelChild;
    }
    
    public static TestDomainModelChildDSL withIntField(int intField){
        this.intField = intField;
        return this;
    }
    public static TestDomainModelChildDSL withDoubleField(double doubleField){
        this.doubleField = doubleField;
        return this;
    }
    public static TestDomainModelChildDSL withFloatField(float floatField){
        this.floatField = floatField;
        return this;
    }
    public static TestDomainModelChildDSL withLongField(long longField){
        this.longField = longField;
        return this;
    }
    public static TestDomainModelChildDSL withShortField(short shortField){
        this.shortField = shortField;
        return this;
    }
    public static TestDomainModelChildDSL withStringField(String stringField){
        this.stringField = stringField;
        return this;
    }
    public static TestDomainModelChildDSL withIntegerObjField(Integer integerObjField){
        this.integerObjField = integerObjField;
        return this;
    }
    public static TestDomainModelChildDSL withDoubleObjField(Double doubleObjField){
        this.doubleObjField = doubleObjField;
        return this;
    }
    public static TestDomainModelChildDSL withFloatObjField(Float floatObjField){
        this.floatObjField = floatObjField;
        return this;
    }
    public static TestDomainModelChildDSL withLongObjField(Long longObjField){
        this.longObjField = longObjField;
        return this;
    }
    public static TestDomainModelChildDSL withShortObjField(Short shortObjField){
        this.shortObjField = shortObjField;
        return this;
    }
    public static TestDomainModelChildDSL withDateObjField(Date dateObjField){
        this.dateObjField = dateObjField;
        return this;
    }
    public static TestDomainModelChildDSL withIntFieldP(int intFieldP){
        this.intFieldP = intFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withDoubleFieldP(double doubleFieldP){
        this.doubleFieldP = doubleFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withFloatFieldP(float floatFieldP){
        this.floatFieldP = floatFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withLongFieldP(long longFieldP){
        this.longFieldP = longFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withShortFieldP(short shortFieldP){
        this.shortFieldP = shortFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withStringFieldP(String stringFieldP){
        this.stringFieldP = stringFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withIntegerObjFieldP(Integer integerObjFieldP){
        this.integerObjFieldP = integerObjFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withDoubleObjFieldP(Double doubleObjFieldP){
        this.doubleObjFieldP = doubleObjFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withFloatObjFieldP(Float floatObjFieldP){
        this.floatObjFieldP = floatObjFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withLongObjFieldP(Long longObjFieldP){
        this.longObjFieldP = longObjFieldP;
        return this;
    }
    public static TestDomainModelChildDSL withShortObjFieldP(Short shortObjFieldP){
        this.shortObjFieldP = shortObjFieldP;
        return this;
    }
} 