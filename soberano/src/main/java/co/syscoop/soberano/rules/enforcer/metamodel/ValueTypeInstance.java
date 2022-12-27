package co.syscoop.soberano.rules.enforcer.metamodel;

public class ValueTypeInstance extends ModelElement {

	//xml attributes
	private String value;
	private String invariantValue;
	
	//methods
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getInvariantValue() {
		return invariantValue;
	}
	
	public void setInvariantValue(String invariantValue) {
		this.invariantValue = invariantValue;
	}
}
