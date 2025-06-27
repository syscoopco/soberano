package co.syscoop.soberano.domain.untracked.helper;

import java.math.BigDecimal;

public class OrderItem {

	private Integer processRunId;
	private String inventoryItemCode;
	private String productName;
	private BigDecimal productQuantity;
	private String productUnit;
	private String description;
	private BigDecimal orderedRuns;
	private BigDecimal canceledRuns;
	private BigDecimal discountedRuns;
	private BigDecimal endedRuns;
	private String currency;
	private BigDecimal oneRunQuantity = new BigDecimal(0);
	private Integer thisIsAnAdditionOf = null;
	private String workerColor = "";
	private String workerLoginName = "";
	
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

	public BigDecimal getOrderedRuns() {
		return orderedRuns;
	}

	public void setOrderedRuns(BigDecimal orderedRuns) {
		this.orderedRuns = orderedRuns;
	}

	public BigDecimal getCanceledRuns() {
		return canceledRuns;
	}

	public void setCanceledRuns(BigDecimal canceledRuns) {
		this.canceledRuns = canceledRuns;
	}

	public BigDecimal getDiscountedRuns() {
		return discountedRuns;
	}

	public void setDiscountedRuns(BigDecimal discountedRuns) {
		this.discountedRuns = discountedRuns;
	}

	public BigDecimal getEndedRuns() {
		return endedRuns;
	}

	public void setEndedRuns(BigDecimal endedRuns) {
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

	public BigDecimal getOneRunQuantity() {
		return oneRunQuantity;
	}

	public void setOneRunQuantity(BigDecimal oneRunQuantity) {
		this.oneRunQuantity = oneRunQuantity;
	}

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}

	public Integer getThisIsAnAdditionOf() {
		return thisIsAnAdditionOf;
	}

	public void setThisIsAnAdditionOf(Integer thisIsAnAdditionOf) {
		this.thisIsAnAdditionOf = thisIsAnAdditionOf;
	}

	public String getWorkerColor() {
		return workerColor;
	}

	public void setWorkerColor(String workerColor) {
		this.workerColor = workerColor;
	}

	public String getWorkerLoginName() {
		return workerLoginName;
	}

	public void setWorkerLoginName(String workerLoginName) {
		this.workerLoginName = workerLoginName;
	}
}
