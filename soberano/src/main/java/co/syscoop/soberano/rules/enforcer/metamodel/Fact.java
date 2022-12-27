package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class Fact extends FactElement {

	//xml child elements
	private List<ReadingOrders> readingOrderss = new ArrayList<ReadingOrders>();
	private List<Instances> instancess = new ArrayList<Instances>();
	private DerivationRule derivationRule;
	
	//methods
	 public DerivationRule getDerivationRule() {
			return derivationRule;
	}
	
	public void setDerivationRule(DerivationRule derivationRule) {
		this.derivationRule = derivationRule;
	}
	
	public void addReadingOrders(ReadingOrders readingOrders) {readingOrderss.add(readingOrders);}
	
    public List<ReadingOrders> getReadingOrderss() {return readingOrderss;}
    
    public void addInstances(Instances instances) {instancess.add(instances);}
	
    public List<Instances> getInstancess() {return instancess;}
}
