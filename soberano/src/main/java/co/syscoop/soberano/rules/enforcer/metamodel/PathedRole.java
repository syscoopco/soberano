package co.syscoop.soberano.rules.enforcer.metamodel;

public class PathedRole extends ModelElement {

	//xml attributes
	private String ref;
	private String purpose;
	
	//methods
	public String getPurpose() {
		return purpose;
	}
	
	public String getRef() {
		return ref;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "Purpose":
				purpose = value;
				break;
			case "ref":
				ref = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }

}
