/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generator;

/**
 * @author Josue Portal
 * 
 * ColumnValueRange represents a value range Object-Role Modeling element to be applied to a relation column.
 */
public class ColumnValueRange {
	private String minValue = "";
	public String getMinValue() {
		return minValue;
	}
	private String maxValue = "";
	public String getMaxValue() {
		return maxValue;
	}
	public ColumnValueRange(String minValue, String maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
}