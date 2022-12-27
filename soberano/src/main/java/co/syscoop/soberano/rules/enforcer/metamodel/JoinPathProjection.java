package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class JoinPathProjection extends ModelElement {

	//xml attributes
	private String ref;
	
	//xml child elements
	private List<ConstraintRoleProjection> constraintRoleProjections = new ArrayList<ConstraintRoleProjection>();
	
	//methods
	public void addConstraintRoleProjection(ConstraintRoleProjection constraintRoleProjection) {constraintRoleProjections.add(constraintRoleProjection);}
	
    public List<ConstraintRoleProjection> getConstraintRoleProjections() {return constraintRoleProjections;}
	
	public String getRef() {
		return ref;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "ref":
				ref = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
