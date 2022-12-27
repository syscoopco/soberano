package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ValueRanges extends SimpleElement {

	//xml child elements
	private List<ValueRange> valueRanges = new ArrayList<ValueRange>();
				
	//methods
	public void addValueRange(ValueRange valueRange) {valueRanges.add(valueRange);}
	
    public List<ValueRange> getValueRanges() {return valueRanges;}
}
