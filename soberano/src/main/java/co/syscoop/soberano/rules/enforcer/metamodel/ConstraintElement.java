package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class ConstraintElement extends NamedElement {

	//xml child elements
	private List<RoleSequence> roleSequences = new ArrayList<RoleSequence>();
	
	//methods
	public void addRoleSequence(RoleSequence roleSequence) {roleSequences.add(roleSequence);}
	
    public List<RoleSequence> getRoleSequences() {return roleSequences;}		
}
