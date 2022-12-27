package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class PathedRoles extends SimpleElement {

	//xml child elements
	private List<PathedRole> pathedRoles = new ArrayList<PathedRole>();
			
	//methods
	public void addPathedRole(PathedRole pathedRole) {pathedRoles.add(pathedRole);}
		
	public List<PathedRole> getPathedRoles() {return pathedRoles;}
}
