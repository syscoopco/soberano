package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class PlayedRoles extends SimpleElement {

	//xml child elements
	private List<Role> roles = new ArrayList<Role>();
	private List<SupertypeMetaRole> supertypeMetaRoles = new ArrayList<SupertypeMetaRole>();
	private List<SubtypeMetaRole> subtypeMetaRoles = new ArrayList<SubtypeMetaRole>();
	
	//methods
	public void addRole(Role role) {roles.add(role);}
	
    public List<Role> getRoles() {return roles;}
    
    public void addSupertypeMetaRole(SupertypeMetaRole supertypeMetaRole) {supertypeMetaRoles.add(supertypeMetaRole);}
	
    public List<SupertypeMetaRole> getSupertypeMetaRoles() {return supertypeMetaRoles;}
    
    public void addSubtypeMetaRole(SubtypeMetaRole subtypeMetaRole) {subtypeMetaRoles.add(subtypeMetaRole);}
	
    public List<SubtypeMetaRole> getSubtypeMetaRoles() {return subtypeMetaRoles;}
}
