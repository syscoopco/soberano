package co.syscoop.soberano.rules.enforcer.metamodel;

public class NamedElement extends ModelElement {

	//xml attributes
	private String name;
	
	//methods
	public String getName() {
		return name;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
		
			case "Name":
				this.name = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
