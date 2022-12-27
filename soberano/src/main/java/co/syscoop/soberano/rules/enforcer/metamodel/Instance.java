package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class Instance extends ModelElement {
	
	//xml child elements
	private List<RoleInstances> roleInstancess = new ArrayList<RoleInstances>();
				
	//methods
	public void addRoleInstances(RoleInstances roleInstances) {roleInstancess.add(roleInstances);}
	
    public List<RoleInstances> getRoleInstancess() {return roleInstancess;}
}
