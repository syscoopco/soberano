package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class SubsetOrExclusionConstraint extends ConstraintElement {
	
	//xml child elements
	private List<RoleSequences> roleSequencess = new ArrayList<RoleSequences>();
	
	//methods
	public void addRoleSequences(RoleSequences roleSequences) {roleSequencess.add(roleSequences);}
	
    public List<RoleSequences> getRoleSequencess() {return roleSequencess;}
}
