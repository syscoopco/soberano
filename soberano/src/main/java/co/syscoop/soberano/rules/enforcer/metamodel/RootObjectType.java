package co.syscoop.soberano.rules.enforcer.metamodel;

public class RootObjectType extends ModelElement {

	//xml attributes
	private String ref;
	
	//methods
	public String getRef() {
		return ref;
	}
	
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

}
