package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ValueRestriction extends SimpleElement {

	//xml child elements
	private List<ValueConstraint> valueConstraints = new ArrayList<ValueConstraint>();
			
	//methods
	public void addValueConstraint(ValueConstraint valueConstraint) {valueConstraints.add(valueConstraint);}
	
    public List<ValueConstraint> getValueConstraints() {return valueConstraints;}
    
    public void addRoleValueConstraint(RoleValueConstraint valueConstraint) {valueConstraints.add(valueConstraint);}
}
