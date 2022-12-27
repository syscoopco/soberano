package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class InternalConstraints extends SimpleElement {

	//xml child elements
	private List<MandatoryConstraint> mandatoryConstraints = new ArrayList<MandatoryConstraint>();
	private List<UniquenessConstraint> uniquenessConstraints = new ArrayList<UniquenessConstraint>();	
	
	//methods
	public void addMandatoryConstraint(MandatoryConstraint mandatoryConstraint) {mandatoryConstraints.add(mandatoryConstraint);}
	
    public List<MandatoryConstraint> getMandatoryConstraints() {return mandatoryConstraints;}
    
    public void addUniquenessConstraint(UniquenessConstraint uniquenessConstraint) {uniquenessConstraints.add(uniquenessConstraint);}
	
    public List<UniquenessConstraint> getUniquenessConstraints() {return uniquenessConstraints;}
}
