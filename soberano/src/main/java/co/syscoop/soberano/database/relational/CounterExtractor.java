package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.CounterRowData;

public final class CounterExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> counterTableData = new ArrayList<Object>();
		CounterRowData counterRowData = null;
        while (rs.next()) {
        	counterRowData = new CounterRowData(rs.getString("counterCode"));
        	counterRowData.setNumberOfReceivers(rs.getInt("numberOfReceivers"));
        	counterRowData.setIsSurcharged(rs.getBoolean("isSurcharged"));
        	counterRowData.setIsEnabled(rs.getBoolean("isEnabled"));
        	counterRowData.setIsFree(rs.getBoolean("isFree"));
        	counterTableData.add(counterRowData);
        }
        return counterTableData;
	}
}