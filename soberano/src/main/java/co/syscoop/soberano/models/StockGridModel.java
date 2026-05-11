package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.StockExtractor;
import co.syscoop.soberano.domain.untracked.Stock;

@SuppressWarnings("serial")
public class StockGridModel extends SoberanoAbstractListModel<Object>
{
	private Integer warehouseId = 0;
	private Integer acquirableMaterialId = 0;
	private String amNameFilterStr = "";
	
	public StockGridModel() {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
	}
	
	public StockGridModel(Integer warehouseId,
						Integer acquirableMaterialId,
						String amNameFilterStr) {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
		
		this.warehouseId = warehouseId;
		this.setAcquirableMaterialId(acquirableMaterialId);
		this.setAmNameFilterStr(amNameFilterStr);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Stock(warehouseId, acquirableMaterialId, amNameFilterStr).getCount();
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
				_cache = (new Stock(warehouseId, acquirableMaterialId, amNameFilterStr)).getAll(_orderBy == null?"inventoryItemName":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new StockExtractor());
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

	public String getAmNameFilterStr() {
		return amNameFilterStr;
	}

	public void setAmNameFilterStr(String amNameFilterStr) {
		this.amNameFilterStr = amNameFilterStr;
	}
}