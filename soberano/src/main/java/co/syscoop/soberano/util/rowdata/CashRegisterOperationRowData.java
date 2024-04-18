package co.syscoop.soberano.util.rowdata;

import java.util.Date;

public class CashRegisterOperationRowData {
	private Integer cashRegisterOperationId = 0;
	private Integer entityTypeInstanceId = 0;
	private String operation = "";
	private String worker = "";
	private String description = "";
	private Date recordingDate = null;
	
	public CashRegisterOperationRowData(Integer cashRegisterOperationId) {
		this.setCashRegisterOperationId(cashRegisterOperationId);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Integer getCashRegisterOperationId() {
		return cashRegisterOperationId;
	}

	public void setCashRegisterOperationId(Integer cashRegisterOperationId) {
		this.cashRegisterOperationId = cashRegisterOperationId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Integer getEntityTypeInstanceId() {
		return entityTypeInstanceId;
	}

	public void setEntityTypeInstanceId(Integer entityTypeInstanceId) {
		this.entityTypeInstanceId = entityTypeInstanceId;
	}
}
