package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ConstraintRoleProjection extends ModelElement {

	//xml attributes
	private String ref;
	
	//xml child elements
	private List<ProjectedFrom> projectedFroms = new ArrayList<ProjectedFrom>();
	
	//methods
	public void addProjectedFrom(ProjectedFrom projectedFrom) {projectedFroms.add(projectedFrom);}
	
    public List<ProjectedFrom> getProjectedFroms() {return projectedFroms;}
	
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
