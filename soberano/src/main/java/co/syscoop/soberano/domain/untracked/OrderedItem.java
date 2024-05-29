package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;

public class OrderedItem {
	
	private int orderId = 0;
	private int processRunId = 0;
	private String productName = "";
	private BigDecimal orderedQuantity = new BigDecimal(0);
	private String instructions = "";
	private BigDecimal servedQuantity = new BigDecimal(0);
	private String unit = "";
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProcessRunId() {
		return processRunId;
	}

	public void setProcessRunId(int processRunId) {
		this.processRunId = processRunId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(BigDecimal orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public BigDecimal getServedQuantity() {
		return servedQuantity;
	}

	public void setServedQuantity(BigDecimal servedQuantity) {
		this.servedQuantity = servedQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
