package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.SPIRowData;

public final class SPIExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> spiTableData = new ArrayList<Object>();
		SPIRowData spiRowData = null;
        while (rs.next()) {
        	spiRowData = new SPIRowData();
        	spiRowData.setInventoryItemName(rs.getString("inventoryItemName"));
        	spiRowData.setUnit(rs.getString("unit"));
        	spiRowData.setOpening(rs.getBigDecimal("opening"));
        	spiRowData.setInput(rs.getBigDecimal("input"));
        	spiRowData.setAvailable(rs.getBigDecimal("available"));
        	spiRowData.setOutput(rs.getBigDecimal("output"));
        	spiRowData.setEnding(rs.getBigDecimal("ending"));
        	spiTableData.add(spiRowData);
        }
        return spiTableData;
	}
}