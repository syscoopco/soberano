package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ExclusionConstraint extends SubsetOrExclusionConstraint {

	//xml child elements
	private List<ExclusiveOrMandatoryConstraint> exclusiveOrMandatoryConstraints = new ArrayList<ExclusiveOrMandatoryConstraint>();
	
	//methods
	public void addExclusiveOrMandatoryConstraint(ExclusiveOrMandatoryConstraint exclusiveOrMandatoryConstraint) {exclusiveOrMandatoryConstraints.add(exclusiveOrMandatoryConstraint);}
	
    public List<ExclusiveOrMandatoryConstraint> getExclusiveOrMandatoryConstraints() {return exclusiveOrMandatoryConstraints;}
}
