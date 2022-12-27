/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generator;

import java.util.ArrayList;

/**
 * @author Josue Portal
 *
 * ColumnValueConstraint represents a value constraint Object-Role Modeling element to be applied to a relation column.
 */
public class ColumnValueConstraint {
	private String constraintName = "";
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getConstraintName() {
		return constraintName;
	}
	private ArrayList<ColumnValueRange> columnValueRanges = new ArrayList<ColumnValueRange>();
	public ArrayList<ColumnValueRange> getColumnValueRanges() {
		return columnValueRanges;
	}
	public ColumnValueConstraint(String constraintName) {
		this.constraintName = constraintName;
	}
}
