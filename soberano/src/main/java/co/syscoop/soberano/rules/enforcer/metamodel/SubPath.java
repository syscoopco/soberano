package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class SubPath extends ModelElement {

	//xml child elements
	private List<PathedRoles> pathedRoless = new ArrayList<PathedRoles>();
			
	//methods
	public void addPathedRoles(PathedRoles pathedRoles) {pathedRoless.add(pathedRoles);}
		
	public List<PathedRoles> getPathedRoless() {return pathedRoless;}
}
