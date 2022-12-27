package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class FactElement extends _NamedElement {

	//xml child elements
	private List<FactRoles> factRoless = new ArrayList<FactRoles>();
	private List<InternalConstraints> internalConstraintss = new ArrayList<InternalConstraints>();
	private List<DerivationRule> derivationRules = new ArrayList<DerivationRule>();
	
	//methods
	public void addFactRoles(FactRoles factRoles) {factRoless.add(factRoles);}
	
    public List<FactRoles> getFactRoless() {return factRoless;}
    
    public void addInternalConstraints(InternalConstraints internalConstraints) {internalConstraintss.add(internalConstraints);}
	
    public List<InternalConstraints> getInternalConstraintss() {return internalConstraintss;}
    
    public void addDerivationRule(DerivationRule derivationRule) {derivationRules.add(derivationRule);}
	
    public List<DerivationRule> getDerivationRules() {return derivationRules;}
}
