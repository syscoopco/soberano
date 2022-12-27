package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ProjectedFrom extends SimpleElement {

	//xml child elements
	private List<PathedRole> pathedRoles = new ArrayList<PathedRole>();
	private List<PathRoot> pathRoots = new ArrayList<PathRoot>();
			
	//methods
	public void addPathedRole(PathedRole pathedRole) {pathedRoles.add(pathedRole);}
		
	public List<PathedRole> getPathedRoles() {return pathedRoles;}
	
	public void addPathRoot(PathRoot pathRoot) {pathRoots.add(pathRoot);}
	
	public List<PathRoot> getPathRoots() {return pathRoots;}
}
