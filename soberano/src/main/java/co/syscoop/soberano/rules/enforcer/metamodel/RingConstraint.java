package co.syscoop.soberano.rules.enforcer.metamodel;

public class RingConstraint extends ConstraintElement {
	
	//xml attributes
	private String type;

	//methods
	public String getType() {
		return type;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "Type":
				type = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
