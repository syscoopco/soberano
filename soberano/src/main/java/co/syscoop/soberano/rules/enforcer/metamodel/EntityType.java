package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class EntityType extends ObjectTypeElement {
	
	//xml attributes
	private String _ReferenceMode;
	
	//xml child elements
	private List<PreferredIdentifier> preferredIdentifiers = new ArrayList<PreferredIdentifier>();
	private List<SubtypeDerivationRule> subtypeDerivationRules = new ArrayList<SubtypeDerivationRule>();
	private List<Instances> instancess = new ArrayList<Instances>();
	private List<CardinalityRestriction> cardinalityRestrictions = new ArrayList<CardinalityRestriction>();
				
	//methods
	public void addCardinalityRestriction(CardinalityRestriction cardinalityRestriction) {cardinalityRestrictions.add(cardinalityRestriction);}
	
    public List<CardinalityRestriction> getCardinalityRestrictions() {return cardinalityRestrictions;}
	
	public void addInstances(Instances instances) {instancess.add(instances);}
	
    public List<Instances> getInstancess() {return instancess;}
	
	public void addPreferredIdentifier(PreferredIdentifier preferredIdentifier) {preferredIdentifiers.add(preferredIdentifier);}
	
    public List<PreferredIdentifier> getPreferredIdentifiers() {return preferredIdentifiers;}
    
    public void addSubtypeDerivationRule(SubtypeDerivationRule subtypeDerivationRule) {subtypeDerivationRules.add(subtypeDerivationRule);}
	
    public List<SubtypeDerivationRule> getSubtypeDerivationRules() {return subtypeDerivationRules;}
	
	public String get_ReferenceMode() {
		return _ReferenceMode;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "_ReferenceMode":
				_ReferenceMode = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
