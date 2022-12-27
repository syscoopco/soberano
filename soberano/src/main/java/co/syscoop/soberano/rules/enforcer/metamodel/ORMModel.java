package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Josue Portal
 *
 * ORMModel Represents the structure (XML elements) of an XML file created with NORMA tool.
 */
public class ORMModel extends NamedElement {
	
	//xml child elements
	private List<Objects> objectss = new ArrayList<Objects>();
	private List<Facts> factss = new ArrayList<Facts>();
	private List<Constraints> constraintss = new ArrayList<Constraints>();
	private List<DataTypes> dataTypess = new ArrayList<DataTypes>();
	private List<CustomReferenceModes> customReferenceModess = new ArrayList<CustomReferenceModes>();
	private List<Notes> notess = new ArrayList<Notes>();
	private List<ModelNotes> modelNotess = new ArrayList<ModelNotes>();
	private List<ReferenceModeKinds> referenceModeKindss = new ArrayList<ReferenceModeKinds>();
		
	//methods
	public void updateSupertypeMetaroleLocator() {
		
		for (SubtypeFact stf : this.getFactss().get(0).getSubtypeFacts()) {
			getFactss().get(0).addSuperTypeMetaroleToLocator(stf.getFactRoless().get(0).getSubtypeMetaRoles().get(0).getId(),
																stf.getFactRoless().get(0).getSupertypeMetaRoles().get(0));
			
			getFactss().get(0).addSubTypeMetaroleToLocator(stf.getFactRoless().get(0).getSupertypeMetaRoles().get(0).getId(),
					stf.getFactRoless().get(0).getSubtypeMetaRoles().get(0));
        }
	}
	
	//Update Facts.functionalFactTypeLocator. This method must be invoked after parsing the model.
	public void updateFunctionalFactLocators() {
		
		for (Fact fact : this.getFactss().get(0).getFacts()) {
			
			Boolean candidateFunctionalFact = false; 
			
			//if it isn't an objectified fact type, and it is binary, so it is binary and with internal uniqueness constraints spanning over only one role
			if (!getObjectss().get(0).getObjectifiedTypeLocator().containsKey(fact.getId()) &&
					fact.getReadingOrderss().get(0).getReadingOrders().get(0).getRoleSequences().get(0).getRoles().size() == 2) {
				
				candidateFunctionalFact = true; 
				
				//for each role
				for (Role role : fact.getFactRoless().get(0).getRoles()) {
					
					//if the role belongs to a preferred identifier
					if (getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId())) {
						candidateFunctionalFact = false;
						break;
					}
				}
			}
			if (candidateFunctionalFact) {
				getFactss().get(0).addFunctionalFactTypeLocator(fact.getId(), fact);
			}
		}
	}
	
	//Update Facts.objectifiedFactTypeLocator. This method must be invoked after parsing the model.
	public void updateObjectifiedFactTypeLocator() {
	
		//for each objectified type
		for (ObjectifiedType objectifiedType : getObjectss().get(0).getObjectifiedTypes()) {
			
			//if it is an objectified fact type
			if (objectifiedType.getNestedPredicates().get(0).getIsImplied() == null) {
				String nestedPredicateId = objectifiedType.getNestedPredicates().get(0).getRef();
				Fact fact = getFactss().get(0).getFactTypeLocator().get(nestedPredicateId);
				//add the fact to the locator and to the list of objectified fact types
				getFactss().get(0).getObjectifiedFactTypeLocator().put(fact.getId(), fact);
				getFactss().get(0).getObjectifiedFacts().add(fact);
			}
		}
	}
	
	public void addObjects(Objects objects) {objectss.add(objects);}
	
    public List<Objects> getObjectss() {return objectss;}
	
	public void addFacts(Facts facts) {factss.add(facts);}
	
    public List<Facts> getFactss() {return factss;}
	
   	public void addConstraints(Constraints constraints) {constraintss.add(constraints);}
   	
    public List<Constraints> getConstraintss() {return constraintss;}
	
   	public void addDataTypes(DataTypes dataTypes) {dataTypess.add(dataTypes);}
   	
    public List<DataTypes> getDataTypess() {return dataTypess;}
	
   	public void addCustomReferenceModes(CustomReferenceModes customReferenceModes) {customReferenceModess.add(customReferenceModes);}
   	
    public List<CustomReferenceModes> getCustomReferenceModess() {return customReferenceModess;}
	
   	public void addNotes(Notes notes) {notess.add(notes);}
   	
    public List<Notes> getNotess() {return notess;}
    
    public void addModelNotes(ModelNotes modelNotes) {modelNotess.add(modelNotes);}
   	
    public List<ModelNotes> getModelNotess() {return modelNotess;}
	
   	public void addReferenceModeKinds(ReferenceModeKinds referenceModeKinds) {referenceModeKindss.add(referenceModeKinds);}
   	
    public List<ReferenceModeKinds> getReferenceModeKindss() {return referenceModeKindss;}
}