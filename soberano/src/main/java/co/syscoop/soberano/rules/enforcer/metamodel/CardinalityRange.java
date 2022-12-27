package co.syscoop.soberano.rules.enforcer.metamodel;

public class CardinalityRange extends ModelElement {

	//xml attributes
	private String from;
	private String to;
	
	//methods
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "From":
				from = value;
				break;
			case "To":
				to = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
