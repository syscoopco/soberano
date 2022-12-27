package co.syscoop.soberano.rules.enforcer.metamodel;

public class DerivationExpression extends ModelElement {
	
	//xml attributes
	private String body;
	private String derivationStorage;
	
	//methods
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getDerivationStorage() {
		return derivationStorage;
	}
	
	public void setDerivationStorage(String derivationStorage) {
		this.derivationStorage = derivationStorage;
	}
}
