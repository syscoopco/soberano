package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.OrderExtractor;
import co.syscoop.soberano.domain.tracked.Order;

@SuppressWarnings("serial")
public class OrdersGridModel extends SoberanoAbstractListModel<Object>
{
	public OrdersGridModel() {
		
		//the set is sorted by stage from major to minor (ongoing ones go first).
		super("stage", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Order().getCount();
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
				_cache = new Order().getAll(_orderBy == null?"stage":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new OrderExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}