package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class EntityTypeInstance extends Instance {

	//xml child elements
	private List<ObjectifiedInstance> objectifiedInstances = new ArrayList<ObjectifiedInstance>();
				
	//methods    
    public void addObjectifiedInstance(ObjectifiedInstance objectifiedInstance) {objectifiedInstances.add(objectifiedInstance);}
	
    public List<ObjectifiedInstance> getObjectifiedInstances() {return objectifiedInstances;}
}
