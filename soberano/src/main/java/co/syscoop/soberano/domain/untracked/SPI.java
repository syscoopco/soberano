package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;
import java.util.HashMap;

import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;

public class SPI extends BusinessActivityTrackedObject {
	
	private Integer warehouseId = 0;
	private String iventoryItemCode = "";
	private BigDecimal quantity = new BigDecimal(0.0);
	private BigDecimal unitValue = new BigDecimal(0.0);
	
	public SPI(Integer warehouseId) {
		this.setWarehouseId(warehouseId);
		getAllQuery = "SELECT * FROM soberano.\"" 
				+ "fn_InventoryOperation_getSPI\"" 
				+ "(:warehouseId, :lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_InventoryOperation_getSPICount\"(:warehouseId, :lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("warehouseId", warehouseId);	
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getIventoryItemCode() {
		return iventoryItemCode;
	}

	public void setIventoryItemCode(String iventoryItemCode) {
		this.iventoryItemCode = iventoryItemCode;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}
}
