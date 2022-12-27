package co.syscoop.soberano.rules.enforcer.metamodel;

public class NestedPredicate extends ModelElement {

	//xml attributes
	private String ref;
	private String isImplied;
	
	//methods
	public String getRef() {
		return ref;
	}
	
	public String getIsImplied() {
		return isImplied;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "ref":
				ref = value;
				break;
			case "IsImplied":
				isImplied = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
