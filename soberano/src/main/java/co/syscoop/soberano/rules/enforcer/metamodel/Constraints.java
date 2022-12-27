package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Constraints extends SimpleElement {

	//xml child elements
	private List<MandatoryConstraint> mandatoryConstraints = new ArrayList<MandatoryConstraint>();
	private List<UniquenessConstraint> uniquenessConstraints = new ArrayList<UniquenessConstraint>();
	private List<SubsetConstraint> subsetConstraints = new ArrayList<SubsetConstraint>();
	private List<RingConstraint> ringConstraints = new ArrayList<RingConstraint>();
	private List<ExclusionConstraint> exclusionConstraints = new ArrayList<ExclusionConstraint>();
	private List<EqualityConstraint> equalityConstraints = new ArrayList<EqualityConstraint>();
	private List<ValueComparisonConstraint> valueComparisonConstraints = new ArrayList<ValueComparisonConstraint>();
	
	//this map stores the ids of uniqueness constraints that are preferred identifiers. the keys are the ids of the roles
	//spanned by the constraints.
	private HashMap<String, String> preferredIdentifierUniquenessConstraints = new HashMap<String, String>();
	
	//this map stores the ids of uniqueness constraints. the ids are the ids of the roles spanned by the constraints
	private HashMap<String, String> uniquenessConstraintByRoleLocator = new HashMap<String, String>();

	//this list is used for iterating the constraints
	private List<ConstraintElement> constraints = new ArrayList<ConstraintElement>();
	
	//this map are used for rapidly locating a constraint by its id
	private HashMap<String, ConstraintElement> constraintLocator = new HashMap<String, ConstraintElement>();
	
	//this map is  used for rapidly locating an internal uniqueness constraint. the key is the id of the constraint.
	private HashMap<String, UniquenessConstraint> internalConstraintLocator = new HashMap<String, UniquenessConstraint>();

	//methods
	public HashMap<String, String> getUniquenessConstraintByRoleLocator() {
		return uniquenessConstraintByRoleLocator;
	}
	
	public HashMap<String, UniquenessConstraint> getInternalConstraintLocator() {
		return internalConstraintLocator;
	}
	
	public HashMap<String, String> getPreferredIdentifierUniquenessConstraints() {
		return preferredIdentifierUniquenessConstraints;
	}
	
	public List<ConstraintElement> getConstraints() {
		return constraints;
	}
	
	public HashMap<String, ConstraintElement> getConstraintLocator() {
		return constraintLocator;
	}
	
	private void updateAuxiliaryCollections(ConstraintElement constraintElement) {
		constraints.add(constraintElement);
		constraintLocator.put(constraintElement.getId(), constraintElement);
		
		//if it is a uniqueness constraint,
		if (constraintElement.getClass().getName().contains("UniquenessConstraint")) {
			
			//for each role spanned by the constraint
			for (Role role : ((UniquenessConstraint) constraintElement).getRoleSequences().get(0).getRoles()) {
				uniquenessConstraintByRoleLocator.put(role.getRef(), constraintElement.getId());
			}
			
			//if the constraint is internal and it has not been registered in the locator
			if (((UniquenessConstraint) constraintElement).getIsInternal() != null &&
					((UniquenessConstraint) constraintElement).getIsInternal().equals("true") &&
					!internalConstraintLocator.containsKey(constraintElement.getId())) {
				internalConstraintLocator.put(constraintElement.getId(), (UniquenessConstraint) constraintElement);
			}
			
			//if the constraint is a preferred identifier
			if (((UniquenessConstraint) constraintElement).getPreferredIdentifierFors().size() > 0) {
				
				//for each role spanned by the constraint
				for (Role role : ((UniquenessConstraint) constraintElement).getRoleSequences().get(0).getRoles()) {
					preferredIdentifierUniquenessConstraints.put(role.getRef(), constraintElement.getId());
				}
			}
		}
	}
	
	public void addValueComparisonConstraint(ValueComparisonConstraint valueComparisonConstraint) {valueComparisonConstraints.add(valueComparisonConstraint); updateAuxiliaryCollections(valueComparisonConstraint);}
	
    public List<ValueComparisonConstraint> getValueComparisonConstraints() {return valueComparisonConstraints;}
	
	public void addEqualityConstraint(EqualityConstraint equalityConstraint) {equalityConstraints.add(equalityConstraint); updateAuxiliaryCollections(equalityConstraint);}
	
    public List<EqualityConstraint> getEqualityConstraints() {return equalityConstraints;}
	
	public void addRingConstraint(RingConstraint ringConstraint) {ringConstraints.add(ringConstraint); updateAuxiliaryCollections(ringConstraint);}
	
    public List<RingConstraint> getRingConstraints() {return ringConstraints;}
	
	public void addMandatoryConstraint(MandatoryConstraint mandatoryConstraint) {mandatoryConstraints.add(mandatoryConstraint); updateAuxiliaryCollections(mandatoryConstraint);}
	
    public List<MandatoryConstraint> getMandatoryConstraints() {return mandatoryConstraints;}
    
    public void addUniquenessConstraint(UniquenessConstraint uniquenessConstraint) {uniquenessConstraints.add(uniquenessConstraint); updateAuxiliaryCollections(uniquenessConstraint);}
	
    public List<UniquenessConstraint> getUniquenessConstraints() {return uniquenessConstraints;}
    
    public void addSubsetConstraint(SubsetConstraint subsetConstraint) {subsetConstraints.add(subsetConstraint); updateAuxiliaryCollections(subsetConstraint);}
	
    public List<SubsetConstraint> getSubsetConstraints() {return subsetConstraints;}
    
    public void addExclusionConstraint(ExclusionConstraint exclusionConstraint) {exclusionConstraints.add(exclusionConstraint); updateAuxiliaryCollections(exclusionConstraint);}
	
    public List<ExclusionConstraint> getExclusionConstraints() {return exclusionConstraints;}
}
