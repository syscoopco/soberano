package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class UniquenessConstraint extends UniquenessOrMandatoryConstraint {

	//xml attributes
	private String isInternal;
	
	//xml child elements
	private List<PreferredIdentifierFor> preferredIdentifierFors = new ArrayList<PreferredIdentifierFor>();
	
	//methods
	public String getIsInternal() {
		return isInternal;
	}
	
	public void addPreferredIdentifierFor(PreferredIdentifierFor preferredIdentifierFor) {preferredIdentifierFors.add(preferredIdentifierFor);}
	
    public List<PreferredIdentifierFor> getPreferredIdentifierFors() {return preferredIdentifierFors;}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "IsInternal":
				isInternal = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
