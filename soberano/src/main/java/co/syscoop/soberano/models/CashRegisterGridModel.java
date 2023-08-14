package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.CashRegisterOperationExtractor;
import co.syscoop.soberano.domain.tracked.CashRegister;

@SuppressWarnings("serial")
public class CashRegisterGridModel extends SoberanoAbstractListModel<Object>
{
	public CashRegisterGridModel() {
		
		//the set is sorted by operationId from major to minor (newer ones go first).
		super("operationId", false, true);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new CashRegister().getCount();
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
				_cache = new CashRegister().getAll(_orderBy == null?"operationId":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new CashRegisterOperationExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}