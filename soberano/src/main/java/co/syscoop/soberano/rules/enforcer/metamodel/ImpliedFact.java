package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ImpliedFact extends Fact {
	
	//xml child elements
	private List<ImpliedByObjectification> impliedByObjectifications = new ArrayList<ImpliedByObjectification>();
	
	//methods
	public void addImpliedByObjectification(ImpliedByObjectification impliedByObjectification) {impliedByObjectifications.add(impliedByObjectification);}
	
    public List<ImpliedByObjectification> getImpliedByObjectifications() {return impliedByObjectifications;}
}
