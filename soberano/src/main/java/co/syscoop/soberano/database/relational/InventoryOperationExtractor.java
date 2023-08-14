package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.InventoryOperationRowData;

public final class InventoryOperationExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> inventoryOperationTableData = new ArrayList<Object>();
		InventoryOperationRowData inventoryOperationRowData = null;
        while (rs.next()) {
        	inventoryOperationRowData = new InventoryOperationRowData(rs.getInt("operationId"));
        	inventoryOperationRowData.setFromWarehouse(rs.getString("fromWarehouse"));
        	inventoryOperationRowData.setToWarehouse(rs.getString("toWarehouse"));
        	inventoryOperationRowData.setWorker(rs.getString("worker"));
        	inventoryOperationRowData.setDescription(rs.getString("description"));
        	inventoryOperationRowData.setOperationDate(rs.getTimestamp("operationDate"));
        	inventoryOperationRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	inventoryOperationTableData.add(inventoryOperationRowData);
        }
        return inventoryOperationTableData;
	}
}