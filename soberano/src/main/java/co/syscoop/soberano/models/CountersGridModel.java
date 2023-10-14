package co.syscoop.soberano.models;

import java.sql.SQLException;
import co.syscoop.soberano.database.relational.CounterExtractor;
import co.syscoop.soberano.domain.tracked.Counter;

@SuppressWarnings("serial")
public class CountersGridModel extends SoberanoAbstractListModel<Object>
{
	public CountersGridModel() {
		
		//the set is sorted by counterCode asc.
		super("counterCode", true, false);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Counter().getCount();
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
				_cache = new Counter().getAll(_orderBy == null?"counterCode":_orderBy,
											_ascending?false:true, 
											50, 
											_beginOffset, 
											new CounterExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}