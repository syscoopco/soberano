package co.syscoop.soberano.rules.enforcer.metamodel;

public class UniquenessOrMandatoryConstraint extends ConstraintElement {
	
	//xml attributes
	private String ref;
	
	//methods
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "ref":
				ref = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
	
	public String getRef() {
		return ref;
	}
}
