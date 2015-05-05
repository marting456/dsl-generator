package org.marting.dslgenerator.data;

import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Short;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.marting.dslgenerator.data.TestClassWithNoArgConstructor;
import org.marting.dslgenerator.data.TestClassWithoutNoArgConstructor;

public class TestDomainModelChildDSL extends AbstractDSL<TestDomainModelChildDSL, TestDomainModelChild> {
          
    // members        
    private int intField = RandomUtils.nextInt(0, 10);
    private double doubleField = RandomUtils.nextDouble(0.0, 10.0);
    private float floatField = RandomUtils.nextFloat(0.0f, 10.0f);
    private long longField = RandomUtils.nextLong(0, 10);
    private short shortField = (short) RandomUtils.nextInt(0, 10);
    private String stringField = RandomStringUtils.randomAlphabetic(10);
    private Integer integerObjField = RandomUtils.nextInt(0, 10);
    private Double doubleObjField = RandomUtils.nextDouble(0.0, 10.0);
    private Float floatObjField = RandomUtils.nextFloat(0.0f, 10.0f);
    private Long longObjField = RandomUtils.nextLong(0, 10);
    private Short shortObjField = (short) RandomUtils.nextInt(0, 10);
    private Date dateObjField = new Date(RandomUtils.nextLong(0 ,((long) 1000 * 60 * 60 * 60 * 24 * 30 * 365 * 30)));
    private List<String> stringList = null;
    private Set<Integer> integerSet = null;
    private Collection<String> stringCollection = null;
    private String[] stringArray = null;
    private TestClassWithNoArgConstructor customFieldWithNoArgConstr = new TestClassWithNoArgConstructor();
    private TestClassWithoutNoArgConstructor customFieldWithoutNoArgConstr = null;
    private List<TestClassWithNoArgConstructor> customFieldWithNoArgConstrList = null;
    private List<TestClassWithoutNoArgConstructor> customFieldWithoutNoArgConstrList = null;
    private TestClassWithNoArgConstructor[] customFieldWithNoArgConstrArray = null;
    private TestClassWithoutNoArgConstructor[] customFieldWithoutNoArgConstrArray = null;
    private int intFieldP = RandomUtils.nextInt(0, 10);
    private double doubleFieldP = RandomUtils.nextDouble(0.0, 10.0);
    private float floatFieldP = RandomUtils.nextFloat(0.0f, 10.0f);
    private long longFieldP = RandomUtils.nextLong(0, 10);
    private short shortFieldP = (short) RandomUtils.nextInt(0, 10);
    private String stringFieldP = RandomStringUtils.randomAlphabetic(10);
    private Integer integerObjFieldP = RandomUtils.nextInt(0, 10);
    private Double doubleObjFieldP = RandomUtils.nextDouble(0.0, 10.0);
    private Float floatObjFieldP = RandomUtils.nextFloat(0.0f, 10.0f);
    private Long longObjFieldP = RandomUtils.nextLong(0, 10);
    private Short shortObjFieldP = (short) RandomUtils.nextInt(0, 10);
    
    // constructor
    private TestDomainModelChildDSL () { };
    
    // methods    
    public static TestDomainModelChildDSL testDomainModelChild() {
        return new TestDomainModelChildDSL();
    }
    
    public TestDomainModelChild build() {
        // Set simple fields
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
        testDomainModelChild.setCustomFieldWithNoArgConstr(this.customFieldWithNoArgConstr); 
        testDomainModelChild.setCustomFieldWithoutNoArgConstr(this.customFieldWithoutNoArgConstr); 
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
        // Set array type fields
        if (this.stringArray == null) {
            String[] stringArray = new String[10];
            for (int i=0; i < stringArray.length; i++) {
                stringArray[i] = RandomStringUtils.randomAlphabetic(10);
            }
            testDomainModelChild.setStringArray(stringArray); 
        } else {
            testDomainModelChild.setStringArray(this.stringArray);
        }
        if (this.customFieldWithNoArgConstrArray == null) {
            TestClassWithNoArgConstructor[] customFieldWithNoArgConstrArray = new TestClassWithNoArgConstructor[10];
            for (int i=0; i < customFieldWithNoArgConstrArray.length; i++) {
                customFieldWithNoArgConstrArray[i] = new TestClassWithNoArgConstructor();
            }
            testDomainModelChild.setCustomFieldWithNoArgConstrArray(customFieldWithNoArgConstrArray); 
        } else {
            testDomainModelChild.setCustomFieldWithNoArgConstrArray(this.customFieldWithNoArgConstrArray);
        }
        if (this.customFieldWithoutNoArgConstrArray == null) {
            TestClassWithoutNoArgConstructor[] customFieldWithoutNoArgConstrArray = new TestClassWithoutNoArgConstructor[10];
            for (int i=0; i < customFieldWithoutNoArgConstrArray.length; i++) {
                customFieldWithoutNoArgConstrArray[i] = null;
            }
            testDomainModelChild.setCustomFieldWithoutNoArgConstrArray(customFieldWithoutNoArgConstrArray); 
        } else {
            testDomainModelChild.setCustomFieldWithoutNoArgConstrArray(this.customFieldWithoutNoArgConstrArray);
        }
        // Set complex fields ie List<String>, List<Customer>
        if (this.stringList == null) {
            List<String> stringList = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                stringList.add(RandomStringUtils.randomAlphabetic(10));
            }
            testDomainModelChild.setStringList(stringList); 
        } else {
            testDomainModelChild.setStringList(this.stringList);
        }
        if (this.integerSet == null) {
            Set<Integer> integerSet = new HashSet<Integer>();
            for (int i = 0; i < 10; i++) {
                integerSet.add(RandomUtils.nextInt(0, 10));
            }
            testDomainModelChild.setIntegerSet(integerSet); 
        } else {
            testDomainModelChild.setIntegerSet(this.integerSet);
        }
        if (this.stringCollection == null) {
            Collection<String> stringCollection = new HashSet<String>();
            for (int i = 0; i < 10; i++) {
                stringCollection.add(RandomStringUtils.randomAlphabetic(10));
            }
            testDomainModelChild.setStringCollection(stringCollection); 
        } else {
            testDomainModelChild.setStringCollection(this.stringCollection);
        }
        if (this.customFieldWithNoArgConstrList == null) {
            List<TestClassWithNoArgConstructor> customFieldWithNoArgConstrList = new ArrayList<TestClassWithNoArgConstructor>();
            for (int i = 0; i < 10; i++) {
                customFieldWithNoArgConstrList.add(new TestClassWithNoArgConstructor());
            }
            testDomainModelChild.setCustomFieldWithNoArgConstrList(customFieldWithNoArgConstrList); 
        } else {
            testDomainModelChild.setCustomFieldWithNoArgConstrList(this.customFieldWithNoArgConstrList);
        }
        if (this.customFieldWithoutNoArgConstrList == null) {
            List<TestClassWithoutNoArgConstructor> customFieldWithoutNoArgConstrList = new ArrayList<TestClassWithoutNoArgConstructor>();
            for (int i = 0; i < 10; i++) {
                customFieldWithoutNoArgConstrList.add(null);
            }
            testDomainModelChild.setCustomFieldWithoutNoArgConstrList(customFieldWithoutNoArgConstrList); 
        } else {
            testDomainModelChild.setCustomFieldWithoutNoArgConstrList(this.customFieldWithoutNoArgConstrList);
        }
        return testDomainModelChild;
    }
    
    public TestDomainModelChildDSL withIntField(int intField) {
        this.intField = intField;
        return this;
    }
    public TestDomainModelChildDSL withDoubleField(double doubleField) {
        this.doubleField = doubleField;
        return this;
    }
    public TestDomainModelChildDSL withFloatField(float floatField) {
        this.floatField = floatField;
        return this;
    }
    public TestDomainModelChildDSL withLongField(long longField) {
        this.longField = longField;
        return this;
    }
    public TestDomainModelChildDSL withShortField(short shortField) {
        this.shortField = shortField;
        return this;
    }
    public TestDomainModelChildDSL withStringField(String stringField) {
        this.stringField = stringField;
        return this;
    }
    public TestDomainModelChildDSL withIntegerObjField(Integer integerObjField) {
        this.integerObjField = integerObjField;
        return this;
    }
    public TestDomainModelChildDSL withDoubleObjField(Double doubleObjField) {
        this.doubleObjField = doubleObjField;
        return this;
    }
    public TestDomainModelChildDSL withFloatObjField(Float floatObjField) {
        this.floatObjField = floatObjField;
        return this;
    }
    public TestDomainModelChildDSL withLongObjField(Long longObjField) {
        this.longObjField = longObjField;
        return this;
    }
    public TestDomainModelChildDSL withShortObjField(Short shortObjField) {
        this.shortObjField = shortObjField;
        return this;
    }
    public TestDomainModelChildDSL withDateObjField(Date dateObjField) {
        this.dateObjField = dateObjField;
        return this;
    }
    public TestDomainModelChildDSL withStringList(List<String> stringList) {
        this.stringList = stringList;
        return this;
    }
    public TestDomainModelChildDSL withIntegerSet(Set<Integer> integerSet) {
        this.integerSet = integerSet;
        return this;
    }
    public TestDomainModelChildDSL withStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
        return this;
    }
    public TestDomainModelChildDSL withStringArray(String[] stringArray) {
        this.stringArray = stringArray;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithNoArgConstr(TestClassWithNoArgConstructor customFieldWithNoArgConstr) {
        this.customFieldWithNoArgConstr = customFieldWithNoArgConstr;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithoutNoArgConstr(TestClassWithoutNoArgConstructor customFieldWithoutNoArgConstr) {
        this.customFieldWithoutNoArgConstr = customFieldWithoutNoArgConstr;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithNoArgConstrList(List<TestClassWithNoArgConstructor> customFieldWithNoArgConstrList) {
        this.customFieldWithNoArgConstrList = customFieldWithNoArgConstrList;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithoutNoArgConstrList(List<TestClassWithoutNoArgConstructor> customFieldWithoutNoArgConstrList) {
        this.customFieldWithoutNoArgConstrList = customFieldWithoutNoArgConstrList;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithNoArgConstrArray(TestClassWithNoArgConstructor[] customFieldWithNoArgConstrArray) {
        this.customFieldWithNoArgConstrArray = customFieldWithNoArgConstrArray;
        return this;
    }
    public TestDomainModelChildDSL withCustomFieldWithoutNoArgConstrArray(TestClassWithoutNoArgConstructor[] customFieldWithoutNoArgConstrArray) {
        this.customFieldWithoutNoArgConstrArray = customFieldWithoutNoArgConstrArray;
        return this;
    }
    public TestDomainModelChildDSL withIntFieldP(int intFieldP) {
        this.intFieldP = intFieldP;
        return this;
    }
    public TestDomainModelChildDSL withDoubleFieldP(double doubleFieldP) {
        this.doubleFieldP = doubleFieldP;
        return this;
    }
    public TestDomainModelChildDSL withFloatFieldP(float floatFieldP) {
        this.floatFieldP = floatFieldP;
        return this;
    }
    public TestDomainModelChildDSL withLongFieldP(long longFieldP) {
        this.longFieldP = longFieldP;
        return this;
    }
    public TestDomainModelChildDSL withShortFieldP(short shortFieldP) {
        this.shortFieldP = shortFieldP;
        return this;
    }
    public TestDomainModelChildDSL withStringFieldP(String stringFieldP) {
        this.stringFieldP = stringFieldP;
        return this;
    }
    public TestDomainModelChildDSL withIntegerObjFieldP(Integer integerObjFieldP) {
        this.integerObjFieldP = integerObjFieldP;
        return this;
    }
    public TestDomainModelChildDSL withDoubleObjFieldP(Double doubleObjFieldP) {
        this.doubleObjFieldP = doubleObjFieldP;
        return this;
    }
    public TestDomainModelChildDSL withFloatObjFieldP(Float floatObjFieldP) {
        this.floatObjFieldP = floatObjFieldP;
        return this;
    }
    public TestDomainModelChildDSL withLongObjFieldP(Long longObjFieldP) {
        this.longObjFieldP = longObjFieldP;
        return this;
    }
    public TestDomainModelChildDSL withShortObjFieldP(Short shortObjFieldP) {
        this.shortObjFieldP = shortObjFieldP;
        return this;
    }
} 