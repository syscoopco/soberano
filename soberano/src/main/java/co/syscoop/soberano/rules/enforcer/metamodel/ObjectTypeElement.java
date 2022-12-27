package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ObjectTypeElement extends NamedElement {

	//xml child elements
	private List<PlayedRoles> playedRoless = new ArrayList<PlayedRoles>();
	
	//methods
	public void addPlayedRoles(PlayedRoles playedRoles) {playedRoless.add(playedRoles);}
	
    public List<PlayedRoles> getPlayedRoless() {return playedRoless;}
}
