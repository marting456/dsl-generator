package org.marting.dslgenerator.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.marting.dslgenerator.dataplus.TestDomainInterface;

/**
 * @author Martin Gercsak - mgercsak@yahoo.com.au
 */
public class TestDomainModelChild extends TestDomainModelParent implements TestDomainInterface {

	public List<String> getStringList() {
		return stringList;
	}
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	public static final String STATIC_FINAL_FIELD = "staticFinalField";
	private int intField;
	private double doubleField;
	private float floatField;
	private long longField;
	private short shortField;
	private String stringField;
	private Integer integerObjField;
	private Double doubleObjField;
	private Float floatObjField;
	private Long longObjField;
	private Short shortObjField;
	private Date dateObjField;

	private List<String> stringList;
	private Set<Integer> integerSet;
	private Collection<String> stringCollection;
	private String[] stringArray;

	public String[] getStringArray() {
		return stringArray;
	}
	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}
	public Set<Integer> getIntegerSet() {
		return integerSet;
	}
	public void setIntegerSet(Set<Integer> integerSet) {
		this.integerSet = integerSet;
	}
	public Collection<String> getStringCollection() {
		return stringCollection;
	}
	public void setStringCollection(Collection<String> stringCollection) {
		this.stringCollection = stringCollection;
	}
	public int getIntField() {
		return intField;
	}
	public void setIntField(int intField) {
		this.intField = intField;
	}
	public double getDoubleField() {
		return doubleField;
	}
	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}
	public float getFloatField() {
		return floatField;
	}
	public void setFloatField(float floatField) {
		this.floatField = floatField;
	}
	public long getLongField() {
		return longField;
	}
	public void setLongField(long longField) {
		this.longField = longField;
	}
	public short getShortField() {
		return shortField;
	}
	public void setShortField(short shortField) {
		this.shortField = shortField;
	}
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	public Integer getIntegerObjField() {
		return integerObjField;
	}
	public void setIntegerObjField(Integer integerObjField) {
		this.integerObjField = integerObjField;
	}
	public Double getDoubleObjField() {
		return doubleObjField;
	}
	public void setDoubleObjField(Double doubleObjField) {
		this.doubleObjField = doubleObjField;
	}
	public Float getFloatObjField() {
		return floatObjField;
	}
	public void setFloatObjField(Float floatObjField) {
		this.floatObjField = floatObjField;
	}
	public Long getLongObjField() {
		return longObjField;
	}
	public void setLongObjField(Long longObjField) {
		this.longObjField = longObjField;
	}
	public Short getShortObjField() {
		return shortObjField;
	}
	public void setShortObjField(Short shortObjField) {
		this.shortObjField = shortObjField;
	}
	public Date getDateObjField() {
		return dateObjField;
	}
	public void setDateObjField(Date dateObjField) {
		this.dateObjField = dateObjField;
	}
}
