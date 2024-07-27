package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.SPIExtractor;
import co.syscoop.soberano.domain.untracked.SPI;

@SuppressWarnings("serial")
public class SPIGridModel extends SoberanoAbstractListModel<Object>
{
	private Integer warehouseId = 0;
	private Integer closureId = 0;
	private Integer acquirableMaterialId = 0;
	
	public SPIGridModel() {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
	}
	
	public SPIGridModel(Integer closureId, Integer warehouseId, Integer acquirableMaterialId) {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("inventoryItemName", true, false);
		
		this.warehouseId = warehouseId;
		this.closureId = closureId;
		this.setAcquirableMaterialId(acquirableMaterialId);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new SPI(closureId, warehouseId, acquirableMaterialId).getCount();
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
				_cache = (new SPI(closureId, warehouseId, acquirableMaterialId)).getAll(_orderBy == null?"inventoryItemName":_orderBy,
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

	public Integer getClosureId() {
		return closureId;
	}

	public void setClosureId(Integer closureId) {
		this.closureId = closureId;
	}

	public Integer getAcquirableMaterialId() {
		return acquirableMaterialId;
	}

	public void setAcquirableMaterialId(Integer acquirableMaterialId) {
		this.acquirableMaterialId = acquirableMaterialId;
	}
}