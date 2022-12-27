package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class RoleSequence extends ModelElement {
	
	//xml child elements
	private List<Role> roles = new ArrayList<Role>();
	private List<JoinRule> joinRules = new ArrayList<JoinRule>();
	
	//methods
	public void addRole(Role role) {roles.add(role);}
	
    public List<Role> getRoles() {return roles;}
	
	public void addJoinRule(JoinRule joinRule) {joinRules.add(joinRule);}
	
    public List<JoinRule> getJoinRules() {return joinRules;}
}
