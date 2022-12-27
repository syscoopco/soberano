package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class CardinalityRestriction extends SimpleElement {

	//xml child elements
	private List<CardinalityConstraint> cardinalityConstraints = new ArrayList<CardinalityConstraint>();
			
	//methods
	public void addCardinalityConstraint(CardinalityConstraint cardinalityConstraint) {cardinalityConstraints.add(cardinalityConstraint);}
	
    public List<CardinalityConstraint> getCardinalityConstraints() {return cardinalityConstraints;}
}