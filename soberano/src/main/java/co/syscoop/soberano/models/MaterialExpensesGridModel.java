package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.ExpenseExtractor;
import co.syscoop.soberano.domain.tracked.MaterialExpense;

@SuppressWarnings("serial")
public class MaterialExpensesGridModel extends SoberanoAbstractListModel<Object>
{
	public MaterialExpensesGridModel() {
		
		//the set is sorted by recordingDate from major to minor (newer ones go first).
		super("recordingDate", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new MaterialExpense().getCount();
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
				_cache = new MaterialExpense().getAll(_orderBy == null?"recordingDate":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new ExpenseExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}