package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class ValueType extends ObjectTypeElement {
	
	//xml attributes
	private String isImplicitBooleanValue;
	
	//xml child elements
	private List<ConceptualDataType> conceptualDataTypes = new ArrayList<ConceptualDataType>();
	private List<ValueRestriction> valueRestrictions = new ArrayList<ValueRestriction>();
	private List<Instances> instancess = new ArrayList<Instances>();
		
	//methods
	public void addInstances(Instances instances) {instancess.add(instances);}
	
    public List<Instances> getInstancess() {return instancess;}
	
	public void addConceptualDataType(ConceptualDataType conceptualDataType) {conceptualDataTypes.add(conceptualDataType);}
	
    public List<ConceptualDataType> getConceptualDataTypes() {return conceptualDataTypes;}
    
    public void addValueRestriction(ValueRestriction valueRestriction) {valueRestrictions.add(valueRestriction);}
	
    public List<ValueRestriction> getValueRestrictions() {return valueRestrictions;}
    
    @Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "IsImplicitBooleanValue":
				isImplicitBooleanValue = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }

	public String getIsImplicitBooleanValue() {
		return isImplicitBooleanValue;
	}
}
