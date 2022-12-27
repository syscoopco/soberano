package co.syscoop.soberano.rules.enforcer.metamodel;

public class SubtypeFact extends FactElement {

	//xml attributes
	private String preferredIdentificationPath;
	
	//methods
	public String getPreferredIdentificationPath() {
		return preferredIdentificationPath;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "PreferredIdentificationPath":
				preferredIdentificationPath = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
