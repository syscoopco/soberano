package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.StockExtractor;
import co.syscoop.soberano.domain.untracked.Stock;

@SuppressWarnings("serial")
public class StockGridModel extends SoberanoAbstractListModel<Object>
{
	private Integer warehouseId = 0;
	
	public StockGridModel() {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
	}
	
	public StockGridModel(Integer warehouseId) {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
		
		this.warehouseId = warehouseId;
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Stock(warehouseId).getCount();
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
				_cache = (new Stock(warehouseId)).getAll(_orderBy == null?"inventoryItemName":_orderBy,
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
}