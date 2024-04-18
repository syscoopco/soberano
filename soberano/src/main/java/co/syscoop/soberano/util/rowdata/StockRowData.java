package co.syscoop.soberano.util.rowdata;

import java.math.BigDecimal;

public class StockRowData {
	private String inventoryItemCode = "";
	private String inventoryItemName = "";
	private BigDecimal quantity = new BigDecimal(0.0);
	private String unit = "";
	private BigDecimal unitValue = new BigDecimal(0.0);
	
	public StockRowData() {
	}

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}

	public String getInventoryItemName() {
		return inventoryItemName;
	}

	public void setInventoryItemName(String inventoryItemName) {
		this.inventoryItemName = inventoryItemName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}
}
