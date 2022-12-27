package co.syscoop.soberano.rules.enforcer.metamodel;

import co.syscoop.soberano.helper.xml.SimpleElement;

public class RefElement extends SimpleElement{

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
