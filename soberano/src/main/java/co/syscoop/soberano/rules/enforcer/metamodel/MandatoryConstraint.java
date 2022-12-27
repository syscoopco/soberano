package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class MandatoryConstraint extends UniquenessOrMandatoryConstraint {

	//xml attributes
	private String isSimple;
	private String isImplied;
	
	//xml child elements
	private List<ImpliedByObjectType> impliedByObjectTypes = new ArrayList<ImpliedByObjectType>();
	private List<ExclusiveOrExclusionConstraint> exclusiveOrExclusionConstraints = new ArrayList<ExclusiveOrExclusionConstraint>();
		
	//methods
	public String getIsSimple() {
		return isSimple;
	}
	
	public String getIsImplied() {
		return isImplied;
	}
	
	public void addImpliedByObjectType(ImpliedByObjectType impliedByObjectType) {impliedByObjectTypes.add(impliedByObjectType);}
	
    public List<ImpliedByObjectType> getImpliedByObjectTypes() {return impliedByObjectTypes;}
    
    public void addExclusiveOrExclusionConstraint(ExclusiveOrExclusionConstraint exclusiveOrExclusionConstraint) {exclusiveOrExclusionConstraints.add(exclusiveOrExclusionConstraint);}
	
    public List<ExclusiveOrExclusionConstraint> getExclusiveOrExclusionConstraints() {return exclusiveOrExclusionConstraints;}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "IsSimple":
				isSimple = value;
				break;
			case "IsImplied":
				isImplied = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
