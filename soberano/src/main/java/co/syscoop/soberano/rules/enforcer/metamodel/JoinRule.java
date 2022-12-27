package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class JoinRule extends SimpleElement {

	//xml child elements
	private List<JoinPath> joinPaths = new ArrayList<JoinPath>();
	
	//methods
	public void addJoinPath(JoinPath joinPath) {joinPaths.add(joinPath);}
	
    public List<JoinPath> getJoinPaths() {return joinPaths;}
}
