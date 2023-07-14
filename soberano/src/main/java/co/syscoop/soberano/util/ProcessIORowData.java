package co.syscoop.soberano.util;

import java.math.BigDecimal;

public class ProcessIORowData {
	
	private String itemId = "";
	private String itemName = "";
	private BigDecimal quantity = new BigDecimal(0.0);
	private String unitAcron = "";
	private Integer unitId = 0;
	private Integer weight = 0;
	
	public ProcessIORowData(){};
	
	public ProcessIORowData(String itemId, String itemName, BigDecimal quantity, String unitAcron, Integer unitId, Integer weight) {
		this.setItemId(itemId);
		this.setItemName(itemName);
		this.setQuantity(quantity);
		this.setUnitAcron(unitAcron);
		this.setUnitId(unitId);
		this.setWeight(weight);
	}
	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnitAcron() {
		return unitAcron;
	}

	public void setUnitAcron(String unitAcron) {
		this.unitAcron = unitAcron;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
}
