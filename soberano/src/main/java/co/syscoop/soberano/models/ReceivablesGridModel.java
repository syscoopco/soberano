package co.syscoop.soberano.models;

import java.sql.SQLException;
import co.syscoop.soberano.database.relational.ReceivableExtractor;
import co.syscoop.soberano.domain.tracked.Receivable;

@SuppressWarnings("serial")
public class ReceivablesGridModel extends SoberanoAbstractListModel<Object>
{
	public ReceivablesGridModel() {
		
		//the set is sorted by recordingDate from major to minor (newer ones go first).
		super("daysDelayed", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0) {
				Receivable receivable = new Receivable();				
				if (filterParams != null) receivable.applyQueryAllFilter(filterParams);		
				_size = receivable.getCount();
			}				
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
				Receivable receivable = new Receivable();				
				if (filterParams != null) receivable.applyQueryAllFilter(filterParams);				
				_cache = receivable.getAll(_orderBy == null?"recordingDate":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new ReceivableExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}