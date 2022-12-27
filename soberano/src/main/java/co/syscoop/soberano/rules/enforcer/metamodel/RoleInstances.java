package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class RoleInstances extends SimpleElement {

	//xml child elements
	private List<FactTypeRoleInstance> factTypeRoleInstances = new ArrayList<FactTypeRoleInstance>();
	private List<EntityTypeRoleInstance> entityTypeRoleInstances = new ArrayList<EntityTypeRoleInstance>();
	
	//methods
	public void addEntityTypeRoleInstance(EntityTypeRoleInstance entityTypeRoleInstance) {entityTypeRoleInstances.add(entityTypeRoleInstance);}
	
    public List<EntityTypeRoleInstance> getEntityTypeRoleInstances() {return entityTypeRoleInstances;}
    
    public void addFactTypeRoleInstance(FactTypeRoleInstance factTypeRoleInstance) {factTypeRoleInstances.add(factTypeRoleInstance);}
	
    public List<FactTypeRoleInstance> getFactTypeRoleInstances() {return factTypeRoleInstances;}
}
