package co.syscoop.soberano.util.rowdata;

import java.util.Date;

public class InventoryOperationRowData {
	private Integer inventoryOperationId = 0;
	private String fromWarehouse = "";
	private String toWarehouse = "";
	private String worker = "";
	private String description = "";
	private Date operationDate = null;
	private Date recordingDate = null;
	
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
}
