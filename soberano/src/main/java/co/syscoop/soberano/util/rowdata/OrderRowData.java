package co.syscoop.soberano.util.rowdata;

import java.util.Date;

public class OrderRowData {
	private Integer objectType = 0; //0: the row is related to an order. 1: the row is related to a process run.
	private Integer orderId = 0;
	private Integer entityTypeInstanceId = 0;
	private String label = "";
	private String customer = "";
	private String counter = "";
	private String Stage = "";
	private String description = "";
	private String history = "";
	private Date recordingDate = null;
	
	public OrderRowData(Integer orderId) {
		this.setOrderId(orderId);
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getEntityTypeInstanceId() {
		return entityTypeInstanceId;
	}

	public void setEntityTypeInstanceId(Integer entityTypeInstanceId) {
		this.entityTypeInstanceId = entityTypeInstanceId;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getStage() {
		return Stage;
	}

	public void setStage(String stage) {
		Stage = stage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
}
