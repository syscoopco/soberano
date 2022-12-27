package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Instances extends SimpleElement {

	//xml child elements
	private List<ValueTypeInstance> valueTypeInstances = new ArrayList<ValueTypeInstance>();
	private List<EntityTypeSubtypeInstance> entityTypeSubtypeInstances = new ArrayList<EntityTypeSubtypeInstance>();
	private List<EntityTypeInstance> entityTypeInstances = new ArrayList<EntityTypeInstance>();
	private List<FactTypeInstance> factTypeInstances = new ArrayList<FactTypeInstance>();
			
	//methods
	public void addEntityTypeInstance(EntityTypeInstance entityTypeInstance) {entityTypeInstances.add(entityTypeInstance);}
	
    public List<EntityTypeInstance> getEntityTypeInstances() {return entityTypeInstances;}
    
    public void addFactTypeInstance(FactTypeInstance factTypeInstance) {factTypeInstances.add(factTypeInstance);}
	
    public List<FactTypeInstance> getFactTypeInstances() {return factTypeInstances;}
	
	public void addValueTypeInstance(ValueTypeInstance valueTypeInstance) {valueTypeInstances.add(valueTypeInstance);}
	
    public List<ValueTypeInstance> getValueTypeInstances() {return valueTypeInstances;}
	
    public void addEntityTypeSubtypeInstance(EntityTypeSubtypeInstance entityTypeSubtypeInstance) {entityTypeSubtypeInstances.add(entityTypeSubtypeInstance);}
	
    public List<EntityTypeSubtypeInstance> getEntityTypeSubtypeInstances() {return entityTypeSubtypeInstances;}
}
