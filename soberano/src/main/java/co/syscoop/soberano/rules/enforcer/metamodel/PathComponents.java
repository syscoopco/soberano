package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class PathComponents extends SimpleElement {

	//xml child elements
	private List<RolePath> rolePaths = new ArrayList<RolePath>();
	
	//methods
	public void addRolePath(RolePath rolePath) {rolePaths.add(rolePath);}
	
    public List<RolePath> getRolePaths() {return rolePaths;}
}
