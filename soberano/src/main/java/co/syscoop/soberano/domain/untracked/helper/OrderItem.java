package co.syscoop.soberano.domain.untracked.helper;

import java.math.BigDecimal;

public class OrderItem {

	private Integer processRunId;
	private String productName;
	private BigDecimal productQuantity;
	private String productUnit;
	private String description;
	private Integer orderedRuns;
	private Integer canceledRuns;
	private Integer discountedRuns;
	private Integer endedRuns;
	private String currency;	
	
	public Integer getProcessRunId() {
		return processRunId;
	}
	
	public void setProcessRunId(Integer processRunId) {
		this.processRunId = processRunId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderedRuns() {
		return orderedRuns;
	}

	public void setOrderedRuns(Integer orderedRuns) {
		this.orderedRuns = orderedRuns;
	}

	public Integer getCanceledRuns() {
		return canceledRuns;
	}

	public void setCanceledRuns(Integer canceledRuns) {
		this.canceledRuns = canceledRuns;
	}

	public Integer getDiscountedRuns() {
		return discountedRuns;
	}

	public void setDiscountedRuns(Integer discountedRuns) {
		this.discountedRuns = discountedRuns;
	}

	public Integer getEndedRuns() {
		return endedRuns;
	}

	public void setEndedRuns(Integer endedRuns) {
		this.endedRuns = endedRuns;
	}

	public BigDecimal getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(BigDecimal productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
