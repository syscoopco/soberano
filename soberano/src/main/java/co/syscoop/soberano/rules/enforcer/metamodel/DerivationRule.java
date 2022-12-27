package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

import co.syscoop.soberano.helper.xml.SimpleElement;

public class DerivationRule extends SimpleElement {
	
	private DerivationExpression derivationExpression;
	
	//xml child elements
	private List<FactTypeDerivationPath> factTypeDerivationPaths = new ArrayList<FactTypeDerivationPath>();
	
	//methods
	public void addFactTypeDerivationPath(FactTypeDerivationPath factTypeDerivationPath) {factTypeDerivationPaths.add(factTypeDerivationPath);}
	
    public List<FactTypeDerivationPath> getFactTypeDerivationPaths() {return factTypeDerivationPaths;}
    
    public DerivationExpression getDerivationExpression() {
		return derivationExpression;
	}
	
	public void setDerivationExpression(DerivationExpression derivationExpression) {
		this.derivationExpression = derivationExpression;
	}
}
