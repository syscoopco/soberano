package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.InventoryOperationExtractor;
import co.syscoop.soberano.domain.tracked.InventoryOperation;

@SuppressWarnings("serial")
public class InventoryOperationsGridModel extends SoberanoAbstractListModel<Object>
{
	public InventoryOperationsGridModel() {
		
		//the set is sorted by operationId from major to minor (newer ones go first).
		super("operationId", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new InventoryOperation().getCount();
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
				_cache = new InventoryOperation().getAll(_orderBy == null?"operationId":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new InventoryOperationExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}