package co.syscoop.soberano.rules.enforcer.metamodel;

public class ValueRange extends ModelElement {

	//xml attributes
	private String minValue;
	private String maxValue;
	private String minInclusion;
	private String maxInclusion;
	
	//methods
	public String getMinValue() {
		return minValue;
	}
	
	public String getMaxValue() {
		return maxValue;
	}
	
	public String getMinInclusion() {
		return minInclusion;
	}
	
	public String getMaxInclusion() {
		return maxInclusion;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "MinValue":
				minValue = value;
				break;
			case "MaxValue":
				maxValue = value;
				break;
			case "MinInclusion":
				minInclusion = value;
				break;
			case "MaxInclusion":
				maxInclusion = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
