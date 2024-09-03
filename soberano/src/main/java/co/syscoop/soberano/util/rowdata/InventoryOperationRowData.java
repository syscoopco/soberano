package co.syscoop.soberano.util.rowdata;

import java.util.Date;

import org.zkoss.zul.Row;

import co.syscoop.soberano.renderers.ActionRequested;

public class InventoryOperationRowData {
	private Integer inventoryOperationId = 0;
	private String fromWarehouse = "";
	private String toWarehouse = "";
	private String worker = "";
	private String description = "";
	private Date operationDate = null;
	private Date recordingDate = null;
	private ActionRequested actionRequested = ActionRequested.NONE;
	private boolean confirmationRequested = false;
	
	public InventoryOperationRowData(Integer inventoryOperationId) {
		this.setInventoryOperationId(inventoryOperationId);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public String getFromWarehouse() {
		return fromWarehouse;
	}

	public void setFromWarehouse(String fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
	}

	public String getToWarehouse() {
		return toWarehouse;
	}

	public void setToWarehouse(String toWarehouse) {
		this.toWarehouse = toWarehouse;
	}

	public Integer getInventoryOperationId() {
		return inventoryOperationId;
	}

	public void setInventoryOperationId(Integer inventoryOperationId) {
		this.inventoryOperationId = inventoryOperationId;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
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
