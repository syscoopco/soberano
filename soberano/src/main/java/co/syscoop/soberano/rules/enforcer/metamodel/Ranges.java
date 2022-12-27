package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Ranges extends SimpleElement {

	//xml child elements
	private List<CardinalityRange> cardinalityRanges = new ArrayList<CardinalityRange>();
				
	//methods
	public void addCardinalityRange(CardinalityRange cardinalityRange) {cardinalityRanges.add(cardinalityRange);}
	
    public List<CardinalityRange> getCardinalityRanges() {return cardinalityRanges;}
}
