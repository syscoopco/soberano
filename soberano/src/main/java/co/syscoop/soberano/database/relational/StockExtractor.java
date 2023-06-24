package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.StockRowData;

public final class StockExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> stockTableData = new ArrayList<Object>();
		StockRowData stockRowData = null;
        while (rs.next()) {
        	stockRowData = new StockRowData();
        	stockRowData.setInventoryItemCode(rs.getString("inventoryItemCode"));
        	stockRowData.setInventoryItemName(rs.getString("inventoryItemName"));
        	stockRowData.setQuantity(rs.getBigDecimal("quantity"));
        	stockRowData.setUnit(rs.getString("unit"));
        	stockRowData.setUnitValue(rs.getBigDecimal("unitValue"));
        	stockTableData.add(stockRowData);
        }
        return stockTableData;
	}
}