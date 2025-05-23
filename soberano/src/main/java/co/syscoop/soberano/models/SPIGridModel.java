package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.SPIExtractor;
import co.syscoop.soberano.domain.untracked.SPI;

@SuppressWarnings("serial")
public class SPIGridModel extends SoberanoAbstractListModel<Object>
{
	private String shiftDateStr = "";
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;
	private Boolean wOpeningStock = false;
	private Boolean wStockOnClosure = false;
	private Boolean wChanges = false;
	private Boolean wSurplus = false;
	private String amNameFilterStr = "";
	
	public SPIGridModel() {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
	}
	
	public SPIGridModel(String shiftDateStr, 
						Integer warehouseId, 
						Integer acquirableMaterialId,
						Boolean wOpeningStock,
						Boolean wStockOnClosure,
						Boolean wChanges,
						Boolean wSurplus,
						String amNameFilterStr) {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
		
		this.shiftDateStr = shiftDateStr;
		this.warehouseId = warehouseId;		
		this.setAcquirableMaterialId(acquirableMaterialId);
		this.setwOpeningStock(wOpeningStock);
		this.setwStockOnClosure(wStockOnClosure);
		this.setwChanges(wChanges);
		this.setwSurplus(wSurplus);
		this.setAmNameFilterStr(amNameFilterStr);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new SPI(shiftDateStr, warehouseId, acquirableMaterialId, wOpeningStock, wStockOnClosure, wChanges, wSurplus, amNameFilterStr).getCount();
			return _size;
		} 
		catch (SQLException e) 
		{
			return 0;
		}
	}
   
	@Override
	public Object getElementAt(int index) {
	   
		if (_cache == null || index < _beginOffset || index >= _beginOffset + _cache.size()) {
			try {
				_beginOffset = index;
				_cache = (new SPI(shiftDateStr, warehouseId, acquirableMaterialId, wOpeningStock, wStockOnClosure, wChanges, wSurplus, amNameFilterStr)).getAll(_orderBy == null?"inventoryItemName":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new SPIExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}

	public Boolean getwOpeningStock() {
		return wOpeningStock;
	}

	public void setwOpeningStock(Boolean wOpeningStock) {
		this.wOpeningStock = wOpeningStock;
	}

	public Boolean getwStockOnClosure() {
		return wStockOnClosure;
	}

	public void setwStockOnClosure(Boolean wStockOnClosure) {
		this.wStockOnClosure = wStockOnClosure;
	}

	public Boolean getwChanges() {
		return wChanges;
	}

	public void setwChanges(Boolean wChanges) {
		this.wChanges = wChanges;
	}

	public Boolean getwSurplus() {
		return wSurplus;
	}

	public void setwSurplus(Boolean wSurplus) {
		this.wSurplus = wSurplus;
	}
	
	public String getAmNameFilterStr() {
		return amNameFilterStr;
	}

	public void setAmNameFilterStr(String amNameFilterStr) {
		this.amNameFilterStr = amNameFilterStr;
	}

	public String getShiftDateStr() {
		return shiftDateStr;
	}

	public void setShiftDateStr(String shiftDateStr) {
		this.shiftDateStr = shiftDateStr;
	}
}