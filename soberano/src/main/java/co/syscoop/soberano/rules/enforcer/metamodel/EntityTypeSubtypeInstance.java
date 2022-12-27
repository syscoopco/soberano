package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class EntityTypeSubtypeInstance extends ModelElement {

	//xml child elements
	private List<SupertypeInstance> supertypeInstances = new ArrayList<SupertypeInstance>();
				
	//methods
	public void addSupertypeInstance(SupertypeInstance supertypeInstance) {supertypeInstances.add(supertypeInstance);}
	
    public List<SupertypeInstance> getSupertypeInstances() {return supertypeInstances;}
}
