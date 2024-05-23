package co.syscoop.soberano.util.rowdata;

public class ProductionLineBoardRowData {
	private Integer allocationId = 0;
	private String allocationQty = "";
	private String allocationItem = "";
	private String allocationInstructions = "";
	private String allocationCounter = "";
	private Integer allocationCounterId = 0;
	private String allocationOrder = "";
	
	public Integer getAllocationId() {
		return allocationId;
	}
	
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}

	public String getAllocationItem() {
		return allocationItem;
	}

	public void setAllocationItem(String allocationItem) {
		this.allocationItem = allocationItem;
	}

	public String getAllocationInstructions() {
		return allocationInstructions;
	}

	public void setAllocationInstructions(String allocationInstructions) {
		this.allocationInstructions = allocationInstructions;
	}

	public String getAllocationCounter() {
		return allocationCounter;
	}

	public void setAllocationCounter(String allocationCounter) {
		this.allocationCounter = allocationCounter;
	}

	public String getAllocationOrder() {
		return allocationOrder;
	}

	public void setAllocationOrder(String allocationOrder) {
		this.allocationOrder = allocationOrder;
	}

	public Integer getAllocationCounterId() {
		return allocationCounterId;
	}

	public void setAllocationCounterId(Integer allocationCounterId) {
		this.allocationCounterId = allocationCounterId;
	}

	public String getAllocationQty() {
		return allocationQty;
	}

	public void setAllocationQty(String allocationQty) {
		this.allocationQty = allocationQty;
	}
}
