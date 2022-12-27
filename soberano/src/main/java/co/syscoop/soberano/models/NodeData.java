package co.syscoop.soberano.models;

import org.zkoss.zul.Treecell;

public class NodeData {

	private String label = "";	
	private Object value = null;	
	private Treecell nodeCell = null;
	
	public NodeData(String label, Object value) {
		this.label = label;
		this.setValue(value);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Treecell getNodeCell() {
		return nodeCell;
	}

	public void setNodeCell(Treecell nodeCell) {
		this.nodeCell = nodeCell;
	}
}
