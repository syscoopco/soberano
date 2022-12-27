package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ObjectifiedType extends EntityType {
	
	//xml attributes
	private String isIndependent;
	
	//xml child elements
	private List<NestedPredicate> nestedPredicates = new ArrayList<NestedPredicate>();
	
	//methods
	public void addNestedPredicate(NestedPredicate nestedPredicate) {nestedPredicates.add(nestedPredicate);}
	
    public List<NestedPredicate> getNestedPredicates() {return nestedPredicates;}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "IsIndependent":
				isIndependent = value;
				break;
			default:
				super.setAttributeValue(name, value);;
		}
    }

	public String getIsIndependent() {
		return isIndependent;
	}
}
