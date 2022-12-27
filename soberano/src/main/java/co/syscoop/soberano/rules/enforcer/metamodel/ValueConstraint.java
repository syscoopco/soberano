package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class ValueConstraint extends NamedElement {

	//xml child elements
	private List<ValueRanges> valueRangess = new ArrayList<ValueRanges>();
				
	//methods
	public void addValueRanges(ValueRanges valueRanges) {valueRangess.add(valueRanges);}
	
    public List<ValueRanges> getValueRangess() {return valueRangess;}
}
