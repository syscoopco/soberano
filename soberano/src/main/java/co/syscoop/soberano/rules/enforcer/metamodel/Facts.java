package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

import co.syscoop.soberano.helper.xml.SimpleElement;

public class Facts extends SimpleElement {

	//xml child elements
	private List<Fact> facts = new ArrayList<Fact>();
	private List<SubtypeFact> subtypeFacts = new ArrayList<SubtypeFact>();
	private List<ImpliedFact> impliedFacts = new ArrayList<ImpliedFact>();
	
	//this map is used for rapidly locating a super type metarole. the key is the id of the corresponding co sub type metarole
	private HashMap<String, SupertypeMetaRole> superTypeMetaroleIdLocator = new HashMap<String, SupertypeMetaRole>();
	
	//this map is used for rapidly locating a sub type metarole. the key is the id of the corresponding co super type metarole
	private HashMap<String, SubtypeMetaRole> subTypeMetaroleIdLocator = new HashMap<String, SubtypeMetaRole>();
	
	//this map is used for rapidly locating a fact type by the id of one of its roles
	private HashMap<String, Fact> factByRoleLocator = new HashMap<String, Fact>();
	
	//this map is used for rapidly locating a functional fact type by its id
	private HashMap<String, Fact> functionalFactTypeLocator = new HashMap<String, Fact>();
	
	//this list is used for iterating the unary fact types
	private List<Fact> unaryFacts = new ArrayList<Fact>();
	
	//this map is used for rapidly locating an unary fact type. the key is its id
	private HashMap<String, Fact> unaryFactTypeLocator = new HashMap<String, Fact>();
	
	//this map is used for rapidly locating an objectified fact type. the key is fact type's id
	private HashMap<String, Fact> objectifiedFactTypeLocator = new HashMap<String, Fact>();
	
	//this list is used for iterating the objectified fact types
	private List<Fact> objectifiedFacts = new ArrayList<Fact>();
	
	//this map is used for rapidly locating a fact type by its id
	private HashMap<String, Fact> factTypeLocator = new HashMap<String, Fact>();
	
	//methods
	public HashMap<String, Fact> getFunctionalFactTypeLocator() {
		return functionalFactTypeLocator;
	}
	
	public HashMap<String, Fact> getFactTypeLocator() {
		return factTypeLocator;
	}
	
	public List<Fact> getObjectifiedFacts() {
		return objectifiedFacts;
	}
	
	public HashMap<String, Fact> getObjectifiedFactTypeLocator() {
		return objectifiedFactTypeLocator;
	}
	
	public HashMap<String, Fact> getUnaryFactTypeLocator() {
		return unaryFactTypeLocator;
	}
	
	public List<Fact> getUnaryFacts() {
		return unaryFacts;
	}
	
	public HashMap<String, Fact> getFactByRoleLocator() {
		return factByRoleLocator;
	}
	
	public HashMap<String, SupertypeMetaRole> getSuperTypeMetaroleLocator() {
		return superTypeMetaroleIdLocator;
	}
	
	public HashMap<String, SubtypeMetaRole> getSubTypeMetaroleLocator() {
		return subTypeMetaroleIdLocator;
	}
	
	public void addSuperTypeMetaroleToLocator(String subtypeMetaroleId, SupertypeMetaRole superTypeMetarole) {
		superTypeMetaroleIdLocator.put(subtypeMetaroleId, superTypeMetarole);
	}
	
	public void addSubTypeMetaroleToLocator(String supertypeMetaroleId, SubtypeMetaRole subTypeMetarole) {
		subTypeMetaroleIdLocator.put(supertypeMetaroleId, subTypeMetarole);
	}
	
	public void addFunctionalFactTypeLocator(String factId, Fact binaryFact) {
		functionalFactTypeLocator.put(factId, binaryFact);
	}
	
	public void addFact(Fact fact) {facts.add(fact);
									
									//add the fact to the fact locator
									factTypeLocator.put(fact.getId(), fact);

									//for each role of the fact type
									for (Role role : fact.getFactRoless().get(0).getRoles()) {
										factByRoleLocator.put(role.getId(), fact);}
									
									//if the fact is unary
									if (fact.getReadingOrderss().get(0).getReadingOrders().get(0).getRoleSequences().get(0).getRoles().size() == 1) {
										unaryFacts.add(fact);
										unaryFactTypeLocator.put(fact.getId(), fact);
									}
								}
	
    public List<Fact> getFacts() {return facts;}
    
    public void addSubtypeFact(SubtypeFact subtypeFact) {subtypeFacts.add(subtypeFact);}
	
    public List<SubtypeFact> getSubtypeFacts() {return subtypeFacts;}
    
    public void addImpliedFact(ImpliedFact impliedfact) {impliedFacts.add(impliedfact);}
	
    public List<ImpliedFact> getImpliedFacts() {return impliedFacts;}
}
