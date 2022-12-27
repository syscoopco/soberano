package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class RoleProxy extends ModelElement {
	
	//xml child elements
	private List<Role> roles = new ArrayList<Role>();
	
	//methods
	public void addRole(Role role) {roles.add(role);}
		
	public List<Role> getRoles() {return roles;}
}
