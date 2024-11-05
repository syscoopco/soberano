package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.rowdata.ProductionLineBoardRowData;

public final class ProcessRunOutputAllocationExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> allocationTableData = new ArrayList<Object>();
		ProductionLineBoardRowData productionLineBoardRowData = null;
        while (rs.next()) {
        	productionLineBoardRowData = new ProductionLineBoardRowData();
        	productionLineBoardRowData.setAllocationId(rs.getInt("allocationId"));
        	productionLineBoardRowData.setAllocationQty(rs.getString("allocationQty"));
        	productionLineBoardRowData.setAllocationItem(rs.getString("allocationItem"));
        	productionLineBoardRowData.setAllocationInstructions(rs.getString("allocationInstructions"));
        	productionLineBoardRowData.setAllocationCounter(rs.getString("allocationCounter"));
        	productionLineBoardRowData.setAllocationCounterId(rs.getInt("allocationCounterId"));
        	productionLineBoardRowData.setAllocationOrder(rs.getString("allocationOrder"));
        	productionLineBoardRowData.setProcessRunIdPair(rs.getString("processRunIdPair"));
        	allocationTableData.add(productionLineBoardRowData);
        }
        return allocationTableData;
	}
}