package co.syscoop.soberano.rules.enforcer.metamodel;

public class ValueComparisonConstraint extends ConstraintElement{

	//xml attributes
	private String operator;
	
	//methods
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "Operator":
				operator = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
	
	public String getOperator() {
		return operator;
	}
}
