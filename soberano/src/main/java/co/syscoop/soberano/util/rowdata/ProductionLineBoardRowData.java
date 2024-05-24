package co.syscoop.soberano.util.rowdata;

import org.zkoss.zul.Row;

import co.syscoop.soberano.renderers.ActionRequested;

public class ProductionLineBoardRowData {
	private Integer allocationId = 0;
	private String allocationQty = "";
	private String allocationItem = "";
	private String allocationInstructions = "";
	private String allocationCounter = "";
	private Integer allocationCounterId = 0;
	private String allocationOrder = "";
	private ActionRequested actionRequested = ActionRequested.NONE;
	private boolean confirmationRequested = false;
	
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

	public ActionRequested getActionRequested() {
		return actionRequested;
	}

	public void setActionRequested(ActionRequested actionRequested) {
		this.actionRequested = actionRequested;
	}

	public boolean isConfirmationRequested() {
		return confirmationRequested;
	}

	public void setConfirmationRequested(boolean confirmationRequested) {
		this.confirmationRequested = confirmationRequested;
	}
	
	public void requestConfirmation(Row row, ActionRequested actionRequested) {
		this.actionRequested = actionRequested;
		confirmationRequested = true;
		row.setStyle("background-color:yellow;");
	}
	
	public void restoreRowDefaultStyle(Row row) {
		actionRequested = ActionRequested.NONE;
		confirmationRequested = false;
		row.setStyle("background-color:transparent;");
	}
}
