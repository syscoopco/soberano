package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.ProcessRunOutputAllocationExtractor;
import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;
import co.syscoop.soberano.domain.tracked.ProductionLine;

@SuppressWarnings("serial")
public class ProductionLineBoardGridModel extends SoberanoAbstractListModel<Object>
{
	private Integer productionLineId = 0;
	
	public ProductionLineBoardGridModel(Integer productionLineId, String orderColumn, String sortDirection) {
		
		super(orderColumn, sortDirection.equals("ASC") ? true: false, sortDirection.equals("DESC") ? true: false);
		this.productionLineId = productionLineId;
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new ProcessRunOutputAllocation(productionLineId).getCount();
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
				_cache = new ProductionLine(productionLineId).getAll(_orderBy == null?"allocationId":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new ProcessRunOutputAllocationExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}