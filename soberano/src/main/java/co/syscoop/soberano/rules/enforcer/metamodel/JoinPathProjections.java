package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class JoinPathProjections extends SimpleElement {

	//xml child elements
	private List<JoinPathProjection> joinPathProjections = new ArrayList<JoinPathProjection>();
	
	//methods
	public void addJoinPathProjection(JoinPathProjection joinPathProjection) {joinPathProjections.add(joinPathProjection);}
	
    public List<JoinPathProjection> getJoinPathProjections() {return joinPathProjections;}
}
