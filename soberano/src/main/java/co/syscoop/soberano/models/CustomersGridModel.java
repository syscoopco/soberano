package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.CustomerExtractor;
import co.syscoop.soberano.domain.tracked.Customer;

@SuppressWarnings("serial")
public class CustomersGridModel extends SoberanoAbstractListModel<Object>
{
	private String nameFilterStr = "";
	
	public CustomersGridModel() {
		
		//the set is sorted by customerName alphabetically (ascending).
		super("domainObjectName", true, false);
	}
	
	public CustomersGridModel(String nameFilterStr) {
		
		//the set is sorted by inventoryItemName alphabetically (ascending).
		super("domainObjectName", true, false);		
		this.setNameFilterStr(nameFilterStr);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Customer(nameFilterStr).getCount();
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
				_cache = (new Customer(nameFilterStr)).getAll(_orderBy == null?"domainObjectName":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new CustomerExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}

	public String getNameFilterStr() {
		return nameFilterStr;
	}

	public void setNameFilterStr(String nameFilterStr) {
		this.nameFilterStr = nameFilterStr;
	}
}