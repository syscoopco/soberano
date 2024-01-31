package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.ShiftClosureExtractor;
import co.syscoop.soberano.domain.tracked.ShiftClosure;

@SuppressWarnings("serial")
public class ShiftClosuresGridModel extends SoberanoAbstractListModel<Object>
{
	public ShiftClosuresGridModel() {
		
		//the set is sorted by shiftClosureId from major to minor (newer ones go first).
		super("shiftClosureId", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new ShiftClosure().getCount();
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
				_cache = new ShiftClosure().getAll(_orderBy == null?"shiftClosureId":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new ShiftClosureExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}