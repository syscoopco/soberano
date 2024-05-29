package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.domain.tracked.Order;

@SuppressWarnings("serial")
public class CounterOrdersListModel extends SoberanoAbstractListModel<Object> {
	
	public CounterOrdersListModel() {		
		super("operationId", true, false);
	}
	
   @Override
   public int getSize() {

	   try {
		   if (_size < 0)
			   _size = new Order().getCurrentOrdersOnCounterCount();
		   return _size;
	   }
	   catch (SQLException e) 
	   {
		   return 0;
	   } catch (Exception e) {
		   e.printStackTrace();
		   e.fillInStackTrace();
		   return 0;
	   }	   
   }
   
   @Override
   public Object getElementAt(int index) {
	   
	   try {
		   if (_cache == null) {
			   _cache = (new Order()).getCurrentOrdersOnCounter();			   
		   }
		   return _cache.get(index);
	   }
	   catch (SQLException e) 
	   {
		   e.printStackTrace();
		   e.fillInStackTrace();
		   return null;
	   }
   }
}