package co.syscoop.soberano.util.rowdata;

import java.math.BigDecimal;

public class SPIRowData {
	private String inventoryItemName = "";
	private String inventoryItemCode = "";
	private String unit = "";
	private Integer unitId = 0;
	private BigDecimal opening = new BigDecimal(0);
	private BigDecimal input = new BigDecimal(0);
	private BigDecimal available = new BigDecimal(0);
	private BigDecimal output = new BigDecimal(0);
	private BigDecimal ending = new BigDecimal(0);
	private BigDecimal losses = new BigDecimal(0);
	private BigDecimal movement = new BigDecimal(0);
	
	public SPIRowData() {
	}

	public String getInventoryItemName() {
		return inventoryItemName;
	}

	public void setInventoryItemName(String inventoryItemName) {
		this.inventoryItemName = inventoryItemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getOpening() {
		return opening;
	}

	public void setOpening(BigDecimal opening) {
		this.opening = opening;
	}

	public BigDecimal getInput() {
		return input;
	}

	public void setInput(BigDecimal input) {
		this.input = input;
	}

	public BigDecimal getAvailable() {
		return available;
	}

	public void setAvailable(BigDecimal available) {
		this.available = available;
	}

	public BigDecimal getOutput() {
		return output;
	}

	public void setOutput(BigDecimal output) {
		this.output = output;
	}

	public BigDecimal getEnding() {
		return ending;
	}

	public void setEnding(BigDecimal ending) {
		this.ending = ending;
	}

	public BigDecimal getLosses() {
		return losses;
	}

	public void setLosses(BigDecimal losses) {
		this.losses = losses;
	}

	public BigDecimal getMovement() {
		return movement;
	}

	public void setMovement(BigDecimal movement) {
		this.movement = movement;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}
}
