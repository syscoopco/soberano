package co.syscoop.soberano.rules.enforcer.metamodel;

public class ConceptualDataType extends ModelElement {
	
	//xml attributes
	private String ref;
	private String scale;
	private String length;
	
	//methods
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "ref":
				ref = value;
				break;
			case "Scale":
				scale = value;
				break;
			case "Length":
				length = value;
				break;	
			default:
				super.setAttributeValue(name, value);
		}
    }

	public String getRef() {
		return ref;
	}

	public String getScale() {
		return scale;
	}

	public String getLength() {
		return length;
	}
}
