package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class CardinalityConstraint extends NamedElement {

	//xml child elements
	private List<Ranges> rangess = new ArrayList<Ranges>();
				
	//methods
	public void addRanges(Ranges ranges) {rangess.add(ranges);}
	
    public List<Ranges> getRangess() {return rangess;}
}
