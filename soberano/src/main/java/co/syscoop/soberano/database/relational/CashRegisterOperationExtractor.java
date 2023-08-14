package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.CashRegisterOperationRowData;

public final class CashRegisterOperationExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> cashRegisterOperationTableData = new ArrayList<Object>();
		CashRegisterOperationRowData cashRegisterOperationRowData = null;
        while (rs.next()) {
        	cashRegisterOperationRowData = new CashRegisterOperationRowData(rs.getInt("operationId"));
        	cashRegisterOperationRowData.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
        	cashRegisterOperationRowData.setOperation(rs.getString("operation"));
        	cashRegisterOperationRowData.setWorker(rs.getString("worker"));
        	cashRegisterOperationRowData.setDescription(rs.getString("description"));
        	cashRegisterOperationRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	cashRegisterOperationTableData.add(cashRegisterOperationRowData);
        }
        return cashRegisterOperationTableData;
	}
}