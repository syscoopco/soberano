package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class DerivationPath extends NamedElement {

	//xml attributes
	private String derivationStorage;
	private String externalDerivation;
	private String derivationCompleteness;
	
	//xml child elements
	private List<InformalRule> informalRules = new ArrayList<InformalRule>();
	
	//methods
	public void addInformalRule(InformalRule informalRule) {informalRules.add(informalRule);}
	
    public List<InformalRule> getInformalRules() {return informalRules;}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "DerivationStorage":
				derivationStorage = value;
				break;
			case "ExternalDerivation":
				externalDerivation = value;
				break;
			case "DerivationCompleteness":
				derivationCompleteness = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }

	public String getDerivationStorage() {
		return derivationStorage;
	}

	public String getExternalDerivation() {
		return externalDerivation;
	}
	
	public String getDerivationCompleteness() {
		return derivationCompleteness;
	}
}
