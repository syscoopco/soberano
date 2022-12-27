package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class FactRoles extends SimpleElement {

	//xml child elements
	private List<Role> roles = new ArrayList<Role>();
	private List<SupertypeMetaRole> supertypeMetaRoles = new ArrayList<SupertypeMetaRole>();
	private List<SubtypeMetaRole> subtypeMetaRoles = new ArrayList<SubtypeMetaRole>();
	private List<RoleProxy> roleProxies = new ArrayList<RoleProxy>();
	
	//this map is used for locating a role by its id
	private HashMap<String, Role> roleLocator = new HashMap<String, Role>();

	//methods
	public HashMap<String, Role> getRoleLocator() {
		return roleLocator;
	}
	
	public void addRole(Role role) {roles.add(role);
									roleLocator.put(role.getId(), role);}
	
    public List<Role> getRoles() {return roles;}
    
    public void addSupertypeMetaRole(SupertypeMetaRole supertypeMetaRole) {supertypeMetaRoles.add(supertypeMetaRole);}

    public List<SupertypeMetaRole> getSupertypeMetaRoles() {return supertypeMetaRoles;}
    
    public void addSubtypeMetaRole(SubtypeMetaRole subtypeMetaRole) {subtypeMetaRoles.add(subtypeMetaRole);}
	
    public List<SubtypeMetaRole> getSubtypeMetaRoles() {return subtypeMetaRoles;}
    
    public void addRoleProxy(RoleProxy roleProxy) {roleProxies.add(roleProxy);}
	
    public List<RoleProxy> getRoleProxys() {return roleProxies;}
}
