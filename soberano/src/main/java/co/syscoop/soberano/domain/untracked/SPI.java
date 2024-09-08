package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;
import java.util.HashMap;

import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;

public class SPI extends BusinessActivityTrackedObject {
	
	private String shiftDateStr = "";
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;
	private BigDecimal quantity = new BigDecimal(0.0);
	private BigDecimal unitValue = new BigDecimal(0.0);
	
	public SPI(String shiftDateStr, 
				Integer warehouseId, 
				Integer acquirableMaterialId,
				Boolean wOpeningStock,
				Boolean wStockOnClosure,
				Boolean wChanges,
				Boolean wSurplus,
				String amNameFilterStr) {
		this.setShiftDateStr(shiftDateStr);
		this.setWarehouseId(warehouseId);
		this.setAcquirableMaterialId(acquirableMaterialId);
		getAllQuery = "SELECT * FROM soberano.\"fn_InventoryOperation_getSPI\"(:shiftDateStr, "
																			+ ":warehouseId, "
																			+ ":acquirableMaterialId, "
																			+ ":wOpeningStock, "
																			+ ":wStockOnClosure, "
																			+ ":wChanges, "
																			+ ":wSurplus, "
																			+ ":amNameFilterStr, "
																			+ ":lang, "
																			+ ":loginname)";
		getCountQuery = "SELECT soberano.\"fn_InventoryOperation_getSPICount\"(:shiftDateStr, "
																			+ ":warehouseId, "
																			+ ":acquirableMaterialId, "
																			+ ":wOpeningStock, "
																			+ ":wStockOnClosure, "
																			+ ":wChanges, "
																			+ ":wSurplus, "
																			+ ":amNameFilterStr, "
																			+ ":lang, "
																			+ ":loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("shiftDateStr", shiftDateStr);	
		getAllQueryNamedParameters.put("warehouseId", warehouseId);	
		getAllQueryNamedParameters.put("acquirableMaterialId", acquirableMaterialId);
		getAllQueryNamedParameters.put("wOpeningStock", wOpeningStock);
		getAllQueryNamedParameters.put("wStockOnClosure", wStockOnClosure);
		getAllQueryNamedParameters.put("wChanges", wChanges);
		getAllQueryNamedParameters.put("wSurplus", wSurplus);
		getAllQueryNamedParameters.put("amNameFilterStr", amNameFilterStr);
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
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

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}

	public String getShiftDateStr() {
		return shiftDateStr;
	}

	public void setShiftDateStr(String shiftDateStr) {
		this.shiftDateStr = shiftDateStr;
	}
}
