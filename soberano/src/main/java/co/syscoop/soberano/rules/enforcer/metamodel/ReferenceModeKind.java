package co.syscoop.soberano.rules.enforcer.metamodel;

public class ReferenceModeKind extends ModelElement {

	//xml attributes
	private String formatString;
	private String referenceModeType;
	
	//methods
	public String getFormatString() {
		return formatString;
	}
	
	public String getReferenceModeType() {
		return referenceModeType;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "FormatString":
				formatString = value;
				break;
			case "ReferenceModeType":
				referenceModeType = value;
				break;
			default:
				super.setAttributeValue(name, value);;
		}
    }
}
