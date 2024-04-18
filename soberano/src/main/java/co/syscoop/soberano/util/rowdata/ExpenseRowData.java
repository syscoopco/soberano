package co.syscoop.soberano.util.rowdata;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseRowData {
	private Integer expenseId = 0;
	private String conceptName = "";
	private String description = "";
	private String payeeName = "";
	private String reference = "";
	private Date expenseDate = null;
	private Date recordingDate = null;
	private BigDecimal amount = new BigDecimal(0.0);
	private String currency = "";
	
	public ExpenseRowData(Integer expenseId) {
		this.expenseId = expenseId;
	}
	
	public String getConceptName() {
		return conceptName;
	}
	
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
