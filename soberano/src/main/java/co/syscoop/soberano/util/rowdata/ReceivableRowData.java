package co.syscoop.soberano.util.rowdata;

import java.math.BigDecimal;
import java.util.Date;

public class ReceivableRowData {
	private Integer receivableId = 0;
	private Integer entityTypeInstanceId = 0;
	private Integer daysDelayed = 0;	
	private String customer = "";
	private String debtor = "";
	private Integer order = 0;
	private BigDecimal amountDue = new BigDecimal(0);
	private String history = "";	
	private Date recordingDate = null;
	
	public ReceivableRowData(Integer receivableId) {
		this.setReceivableId(receivableId);
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public Integer getReceivableId() {
		return receivableId;
	}

	public void setReceivableId(Integer receivableId) {
		this.receivableId = receivableId;
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

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getDaysDelayed() {
		return daysDelayed;
	}

	public void setDaysDelayed(Integer daysDelayed) {
		this.daysDelayed = daysDelayed;
	}

	public String getDebtor() {
		return debtor;
	}

	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}

	public BigDecimal getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(BigDecimal amountDue) {
		this.amountDue = amountDue;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
