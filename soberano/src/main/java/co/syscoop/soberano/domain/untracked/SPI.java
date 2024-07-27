package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;
import java.util.HashMap;

import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;

public class SPI extends BusinessActivityTrackedObject {
	
	private Integer warehouseId = 0;
	private Integer closureId = 0;
	private Integer acquirableMaterialId = 0;
	private BigDecimal quantity = new BigDecimal(0.0);
	private BigDecimal unitValue = new BigDecimal(0.0);
	
	public SPI(Integer closureId, Integer warehouseId, Integer acquirableMaterialId) {
		this.setClosureId(closureId);
		this.setWarehouseId(warehouseId);
		this.setAcquirableMaterialId(acquirableMaterialId);
		getAllQuery = "SELECT * FROM soberano.\"" 
				+ "fn_InventoryOperation_getSPI\"" 
				+ "(:closureId, :warehouseId, :acquirableMaterialId, :lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_InventoryOperation_getSPICount\"(:closureId, :warehouseId, :acquirableMaterialId, :lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("closureId", closureId);	
		getAllQueryNamedParameters.put("warehouseId", warehouseId);	
		getAllQueryNamedParameters.put("acquirableMaterialId", acquirableMaterialId);	
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Integer getClosureId() {
		return closureId;
	}

	public void setClosureId(Integer closureId) {
		this.closureId = closureId;
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
}
